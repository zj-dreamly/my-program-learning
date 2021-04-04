package com.github.zj.dreamly.zookeeper.zk.demo;


import org.apache.zookeeper.AsyncCallback.StringCallback;

public class CreateCallBack implements StringCallback {

	@Override
	public void processResult(int rc, String path, Object ctx, String name) {
		System.out.println("创建节点: " + path);
		System.out.println((String)ctx);
	}

}
