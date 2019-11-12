package com.github.zj.dreamly.concurrent.atomic;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @author 苍海之南
 */
public class FailedAtomicIntegerFieldUpdaterTest {

	/**
	 * Can't access the private field of object
	 */
	@Test(expected = RuntimeException.class)
	public void testPrivateFieldAccessError() {
		AtomicIntegerFieldUpdater<TestMe> updater = AtomicIntegerFieldUpdater.newUpdater(TestMe.class, "i");
		TestMe me = new TestMe();
		updater.compareAndSet(me, 0, 1);
	}

	@Test
	public void testTargetObjectIsNull() {
		AtomicIntegerFieldUpdater<TestMe> updater = AtomicIntegerFieldUpdater.newUpdater(TestMe.class, "i");
		updater.compareAndSet(null, 0, 1);
	}

	@Test
	public void testFieldNameInvalid() {
		AtomicIntegerFieldUpdater<TestMe> updater = AtomicIntegerFieldUpdater.newUpdater(TestMe.class, "i1");
		updater.compareAndSet(null, 0, 1);
	}

	@Test
	public void testFieldTypeInvalid() {
		AtomicReferenceFieldUpdater<TestMe2, Long> updater = AtomicReferenceFieldUpdater.newUpdater(TestMe2.class, Long.class, "i");
		TestMe2 me = new TestMe2();

		updater.compareAndSet(me, null, 1L);
	}

	@Test
	public void testFieldIsNotVolatile() {
		AtomicReferenceFieldUpdater updater = AtomicReferenceFieldUpdater.newUpdater(TestMe2.class, Integer.class, "i");
		TestMe me = new TestMe();

		updater.compareAndSet(me, null, 1);
	}

	static class TestMe2 {
		volatile Integer i;
	}

}
