package com.github.zj.dreamly.zookeeper.zk.demo;


import org.apache.zookeeper.AsyncCallback.VoidCallback;

public class DeleteCallBack implements VoidCallback {
	@Override
	public void processResult(int rc, String path, Object ctx) {
		System.out.println("删除节点" + path);
		System.out.println((String)ctx);
	}

}
