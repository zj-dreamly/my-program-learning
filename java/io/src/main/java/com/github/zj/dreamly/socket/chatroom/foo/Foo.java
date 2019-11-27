package com.github.zj.dreamly.socket.chatroom.foo;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * <h2>Foo</h2>
 *
 * @author: 苍海之南
 * @since: 2019-10-29 17:14
 **/
public class Foo {
	private static final String CACHE_DIR = "cache";

	public static File getCacheDir(String dir) {
		String path = System.getProperty("user.dir") + (File.separator + CACHE_DIR + File.separator + dir);
		File file = new File(path);
		if (!file.exists()) {
			if (!file.mkdirs()) {
				throw new RuntimeException("Create path error:" + path);
			}
		}
		return file;
	}

	public static File createRandomTemp(File parent) {
		String string = UUID.randomUUID().toString() + ".tmp";
		File file = new File(parent, string);
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
}
