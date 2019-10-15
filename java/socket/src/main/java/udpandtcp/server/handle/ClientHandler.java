package udpandtcp.server.handle;

import udpandtcp.clink.net.qiujuer.clink.utils.CloseUtils;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientHandler {
	private final Socket socket;
	private final ClientReadHandler readHandler;
	private final ClientWriteHandler writeHandler;
	private final CloseNotify closeNotify;

	public ClientHandler(Socket socket, CloseNotify closeNotify) throws IOException {
		this.socket = socket;
		this.readHandler = new ClientReadHandler(socket.getInputStream());
		this.writeHandler = new ClientWriteHandler(socket.getOutputStream());
		this.closeNotify = closeNotify;
		System.out.println("新客户端连接：" + socket.getInetAddress() +
			" P:" + socket.getPort());
	}

	public void exit() {
		readHandler.exit();
		writeHandler.exit();
		CloseUtils.close(socket);
		System.out.println("客户端已退出：" + socket.getInetAddress() +
			" P:" + socket.getPort());
	}

	public void send(String str) {
		writeHandler.send(str);
	}

	public void readToPrint() {
		readHandler.start();
	}

	private void exitBySelf() {
		exit();
		closeNotify.onSelfClosed(this);
	}

	public interface CloseNotify {
		void onSelfClosed(ClientHandler handler);
	}

	class ClientReadHandler extends Thread {
		private boolean done = false;
		private final InputStream inputStream;

		ClientReadHandler(InputStream inputStream) {
			this.inputStream = inputStream;
		}

		@Override
		public void run() {
			super.run();
			try {
				// 得到输入流，用于接收数据
				BufferedReader socketInput = new BufferedReader(new InputStreamReader(inputStream));

				do {
					// 客户端拿到一条数据
					String str = socketInput.readLine();
					if (str == null) {
						System.out.println("客户端已无法读取数据！");
						// 退出当前客户端
						ClientHandler.this.exitBySelf();
						break;
					}
					// 打印到屏幕
					System.out.println(str);
				} while (!done);
			} catch (Exception e) {
				if (!done) {
					System.out.println("连接异常断开");
					ClientHandler.this.exitBySelf();
				}
			} finally {
				// 连接关闭
				CloseUtils.close(inputStream);
			}
		}

		void exit() {
			done = true;
			CloseUtils.close(inputStream);
		}
	}

	class ClientWriteHandler {
		private boolean done = false;
		private final PrintStream printStream;
		private final ExecutorService executorService;

		ClientWriteHandler(OutputStream outputStream) {
			this.printStream = new PrintStream(outputStream);
			this.executorService = Executors.newSingleThreadExecutor();
		}

		void exit() {
			done = true;
			CloseUtils.close(printStream);
			executorService.shutdownNow();
		}

		void send(String str) {
			executorService.execute(new WriteRunnable(str));
		}

		class WriteRunnable implements Runnable {
			private final String msg;

			WriteRunnable(String msg) {
				this.msg = msg;
			}

			@Override
			public void run() {
				if (ClientWriteHandler.this.done) {
					return;
				}

				try {
					ClientWriteHandler.this.printStream.println(msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
