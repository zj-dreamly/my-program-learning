package com.github.zj.dreamly.concurrent.classloader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author 苍海之南
 */
public final class EncryptUtils {

	public static final byte ENCRYPT_FACTOR = (byte) 0xff;

	private EncryptUtils() {
		//empty
	}

	public static void doEncrypt(String source, String target) {
		try (FileInputStream fis = new FileInputStream(source);
			 FileOutputStream fos = new FileOutputStream(target)) {
			int data;
			while ((data = fis.read()) != -1) {
				fos.write(data ^ ENCRYPT_FACTOR);
			}
			fos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
