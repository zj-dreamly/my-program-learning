package com.github.zj.dreamly.socket.chatroom.library.impl;

import com.github.zj.dreamly.socket.chatroom.library.core.IoProvider;
import com.github.zj.dreamly.socket.chatroom.library.utils.CloseUtils;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 苍海之南
 */
public class IoSelectorProvider implements IoProvider {
	private final AtomicBoolean isClosed = new AtomicBoolean(false);

	/**
	 * 是否处于某个过程
	 */
	private final AtomicBoolean inRegInput = new AtomicBoolean(false);
	private final AtomicBoolean inRegOutput = new AtomicBoolean(false);

	private final Selector readSelector;
	private final Selector writeSelector;

	private final HashMap<SelectionKey, Runnable> inputCallbackMap = new HashMap<>();
	private final HashMap<SelectionKey, Runnable> outputCallbackMap = new HashMap<>();

	private final ExecutorService inputHandlePool;
	private final ExecutorService outputHandlePool;

	public IoSelectorProvider() throws IOException {
		readSelector = Selector.open();
		writeSelector = Selector.open();

		inputHandlePool = Executors.newFixedThreadPool(4,
			new IoProviderThreadFactory("IoProvider-Input-Thread-"));
		outputHandlePool = Executors.newFixedThreadPool(4,
			new IoProviderThreadFactory("IoProvider-Output-Thread-"));

		// 开始输出输入的监听
		startRead();
		startWrite();
	}

	private void startRead() {
		Thread thread = new Thread("Clink IoSelectorProvider ReadSelector Thread") {
			@Override
			public void run() {
				while (!isClosed.get()) {
					try {
						if (readSelector.select() == 0) {
							waitSelection(inRegInput);
							continue;
						}

						Set<SelectionKey> selectionKeys = readSelector.selectedKeys();
						for (SelectionKey selectionKey : selectionKeys) {
							if (selectionKey.isValid()) {
								handleSelection(selectionKey, SelectionKey.OP_READ, inputCallbackMap, inputHandlePool);
							}
						}
						selectionKeys.clear();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		};
		thread.setPriority(Thread.MAX_PRIORITY);
		thread.start();
	}

	private void startWrite() {
		Thread thread = new Thread("Clink IoSelectorProvider WriteSelector Thread") {
			@Override
			public void run() {
				while (!isClosed.get()) {
					try {
						if (writeSelector.select() == 0) {
							waitSelection(inRegOutput);
							continue;
						}

						Set<SelectionKey> selectionKeys = writeSelector.selectedKeys();
						for (SelectionKey selectionKey : selectionKeys) {
							if (selectionKey.isValid()) {
								handleSelection(selectionKey, SelectionKey.OP_WRITE, outputCallbackMap, outputHandlePool);
							}
						}
						selectionKeys.clear();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		};
		thread.setPriority(Thread.MAX_PRIORITY);
		thread.start();
	}

	@Override
	public boolean registerInput(SocketChannel channel, HandleInputCallback callback) {
		return registerSelection(channel, readSelector, SelectionKey.OP_READ, inRegInput,
			inputCallbackMap, callback) != null;
	}

	@Override
	public boolean registerOutput(SocketChannel channel, HandleOutputCallback callback) {
		return registerSelection(channel, writeSelector, SelectionKey.OP_WRITE, inRegOutput,
			outputCallbackMap, callback) != null;
	}

	@Override
	public void unRegisterInput(SocketChannel channel) {
		unRegisterSelection(channel, readSelector, inputCallbackMap);
	}

	@Override
	public void unRegisterOutput(SocketChannel channel) {
		unRegisterSelection(channel, writeSelector, outputCallbackMap);
	}

	@Override
	public void close() {
		if (isClosed.compareAndSet(false, true)) {
			inputHandlePool.shutdown();
			outputHandlePool.shutdown();

			inputCallbackMap.clear();
			outputCallbackMap.clear();

			readSelector.wakeup();
			writeSelector.wakeup();

			CloseUtils.close(readSelector, writeSelector);
		}
	}

	private static void waitSelection(final AtomicBoolean locker) {
		//noinspection SynchronizationOnLocalVariableOrMethodParameter
		synchronized (locker) {
			if (locker.get()) {
				try {
					locker.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static SelectionKey registerSelection(SocketChannel channel, Selector selector,
												  int registerOps, AtomicBoolean locker,
												  HashMap<SelectionKey, Runnable> map,
												  Runnable runnable) {

		//noinspection SynchronizationOnLocalVariableOrMethodParameter
		synchronized (locker) {
			// 设置锁定状态
			locker.set(true);

			try {
				// 唤醒当前的selector，让selector不处于select()状态
				selector.wakeup();

				SelectionKey key = null;
				if (channel.isRegistered()) {
					// 查询是否已经注册过
					key = channel.keyFor(selector);
					if (key != null) {
						key.interestOps(key.readyOps() | registerOps);
					}
				}

				if (key == null) {
					// 注册selector得到Key
					key = channel.register(selector, registerOps);
					// 注册回调
					map.put(key, runnable);
				}

				return key;
			} catch (ClosedChannelException e) {
				return null;
			} finally {
				// 解除锁定状态
				locker.set(false);
				try {
					// 通知
					locker.notify();
				} catch (Exception ignored) {
				}
			}
		}
	}

	private static void unRegisterSelection(SocketChannel channel, Selector selector,
											Map<SelectionKey, Runnable> map) {
		if (channel.isRegistered()) {
			SelectionKey key = channel.keyFor(selector);
			if (key != null) {
				// 取消监听的方法
				key.cancel();
				map.remove(key);
				selector.wakeup();
			}
		}
	}

	private static void handleSelection(SelectionKey key, int keyOps,
										HashMap<SelectionKey, Runnable> map,
										ExecutorService pool) {
		// 重点
		// 取消继续对keyOps的监听
		key.interestOps(key.readyOps() & ~keyOps);

		Runnable runnable = null;
		try {
			runnable = map.get(key);
		} catch (Exception ignored) {

		}

		if (runnable != null && !pool.isShutdown()) {
			// 异步调度
			pool.execute(runnable);
		}
	}

	static class IoProviderThreadFactory implements ThreadFactory {
		private final ThreadGroup group;
		private final AtomicInteger threadNumber = new AtomicInteger(1);
		private final String namePrefix;

		IoProviderThreadFactory(String namePrefix) {
			SecurityManager s = System.getSecurityManager();
			this.group = (s != null) ? s.getThreadGroup() :
				Thread.currentThread().getThreadGroup();
			this.namePrefix = namePrefix;
		}

		@Override
		public Thread newThread(Runnable runnable) {
			Thread t = new Thread(group, runnable,
				namePrefix + threadNumber.getAndIncrement(),
				0);
			if (t.isDaemon()) {
				t.setDaemon(false);
			}
			if (t.getPriority() != Thread.NORM_PRIORITY) {
				t.setPriority(Thread.NORM_PRIORITY);
			}
			return t;
		}
	}
}
