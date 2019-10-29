package com.github.zj.dreamly.guava.io;

import com.google.common.io.ByteSource;
import com.google.common.io.Closer;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * <h2>CloserTest</h2>
 *
 * @author: 苍海之南
 * @since: 2019-10-29 11:31
 **/
public class CloserTest {
	private final String filePath = CloserTest.class.getResource("/").getPath();

	@Test
	public void testCloser() throws IOException {
		ByteSource byteSource = Files.asByteSource(new File(filePath + "/io/files.PNG"));
		Closer closer = Closer.create();
		try {
			InputStream inputStream = closer.register(byteSource.openStream());
		} catch (Throwable e) {
			throw closer.rethrow(e);
		} finally {
			closer.close();
		}
	}

	@Test(expected = RuntimeException.class)
	public void testTryCatchFinally() {
		try {

			System.out.println("work area.");
			throw new IllegalArgumentException();
		} catch (Exception e) {
			System.out.println("exception area");
			throw new RuntimeException();
		} finally {
			System.out.println("finally area");
		}
	}

	@Test
	public void testTryCatch() {
		Throwable t = null;
		try {
			throw new RuntimeException("1");
		} catch (Exception e) {
			t = e;
			throw e;
		} finally {
			try {
				//close
				throw new RuntimeException("2");
			} catch (Exception e) {
				t.addSuppressed(e);
			}
		}
	}
}
