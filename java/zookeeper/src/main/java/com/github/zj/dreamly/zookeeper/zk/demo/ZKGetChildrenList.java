package com.github.zj.dreamly.zookeeper.zk.demo;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.EventType;

/**
 * @Description: zookeeper 获取子节点数据的demo演示
 */
public class ZKGetChildrenList implements Watcher {

	private ZooKeeper zookeeper = null;

	public static final String zkServerPath = "192.168.1.110:2181";
	public static final Integer timeout = 5000;

	public ZKGetChildrenList() {}

	public ZKGetChildrenList(String connectString) {
		try {
			zookeeper = new ZooKeeper(connectString, timeout, new ZKGetChildrenList());
		} catch (IOException e) {
			e.printStackTrace();
			if (zookeeper != null) {
				try {
					zookeeper.close();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	private static CountDownLatch countDown = new CountDownLatch(1);

	public static void main(String[] args) throws Exception {

		ZKGetChildrenList zkServer = new ZKGetChildrenList(zkServerPath);

		/**
		 * 参数：
		 * path：父节点路径
		 * watch：true或者false，注册一个watch事件
		 */
//		List<String> strChildList = zkServer.getZookeeper().getChildren("/imooc", true);
//		for (String s : strChildList) {
//			System.out.println(s);
//		}

		// 异步调用
		String ctx = "{'callback':'ChildrenCallback'}";
//		zkServer.getZookeeper().getChildren("/imooc", true, new ChildrenCallBack(), ctx);
		zkServer.getZookeeper().getChildren("/imooc", true, new Children2CallBack(), ctx);

		countDown.await();
	}

	@Override
	public void process(WatchedEvent event) {
		try {
			if(event.getType()==EventType.NodeChildrenChanged){
				System.out.println("NodeChildrenChanged");
				ZKGetChildrenList zkServer = new ZKGetChildrenList(zkServerPath);
				List<String> strChildList = zkServer.getZookeeper().getChildren(event.getPath(), false);
				for (String s : strChildList) {
					System.out.println(s);
				}
				countDown.countDown();
			} else if(event.getType() == EventType.NodeCreated) {
				System.out.println("NodeCreated");
			} else if(event.getType() == EventType.NodeDataChanged) {
				System.out.println("NodeDataChanged");
			} else if(event.getType() == EventType.NodeDeleted) {
				System.out.println("NodeDeleted");
			}
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public ZooKeeper getZookeeper() {
		return zookeeper;
	}
	public void setZookeeper(ZooKeeper zookeeper) {
		this.zookeeper = zookeeper;
	}

}

