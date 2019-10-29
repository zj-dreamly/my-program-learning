package com.github.zj.dreamly.guava.eventbus.monitor;

/**
 * @author 苍海之南
 */
public interface TargetMonitor {

	void startMonitor() throws Exception;

	void stopMonitor() throws Exception;
}
