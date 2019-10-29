package com.github.zj.dreamly.guava.eventbus.monitor;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

/**
 * @author 苍海之南
 */
public class FileChangeEvent {

	private final Path path;

	private final WatchEvent.Kind<?> kind;

	public FileChangeEvent(Path path, WatchEvent.Kind<?> kind) {
		this.path = path;
		this.kind = kind;
	}

	public Path getPath() {
		return path;
	}

	public WatchEvent.Kind<?> getKind() {
		return kind;
	}
}
