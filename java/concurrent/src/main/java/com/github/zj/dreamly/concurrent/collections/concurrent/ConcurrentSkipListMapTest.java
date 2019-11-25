package com.github.zj.dreamly.concurrent.collections.concurrent;

import org.junit.Test;

import java.util.concurrent.ConcurrentSkipListMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author 苍海之南
 */
public class ConcurrentSkipListMapTest {

	/**
	 * 1.4 round--->1
	 * 1.5 round--->2
	 * <p>
	 * 1.1 ceiling ---->2
	 * 1.9 ceiling ---->2
	 * <p>
	 * 1.1 floor ---->1
	 * 1.9 floor ---->1
	 */
	@Test
	public void testCeiling() {
		ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();

		map.put(1, "Scala");
		map.put(5, "Java");
		map.put(10, "Clojure");

		assertThat(map.size(), equalTo(3));
		assertThat(map.ceilingKey(2), equalTo(5));
		assertThat(map.ceilingEntry(2).getValue(), equalTo("Java"));
		assertThat(map.ceilingEntry(5).getValue(), equalTo("Java"));
	}

	@Test
	public void testFloor() {
		ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();

		map.put(1, "Scala");
		map.put(5, "Java");
		map.put(10, "Clojure");

		assertThat(map.size(), equalTo(3));
		assertThat(map.floorKey(2), equalTo(1));
		assertThat(map.floorEntry(2).getValue(), equalTo("Scala"));
		assertThat(map.floorEntry(1).getValue(), equalTo("Scala"));
	}

	@Test
	public void testFirst() {
		ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();

		map.put(1, "Scala");
		map.put(5, "Java");
		map.put(10, "Clojure");

		assertThat(map.size(), equalTo(3));
		assertThat(map.firstKey(), equalTo(1));
		assertThat(map.firstEntry().getValue(), equalTo("Scala"));
	}

	@Test
	public void testLast() {
		ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();

		map.put(1, "Scala");
		map.put(5, "Java");
		map.put(10, "Clojure");

		assertThat(map.size(), equalTo(3));
		assertThat(map.lastKey(), equalTo(10));
		assertThat(map.lastEntry().getValue(), equalTo("Clojure"));
	}

	@Test
	public void testMerge() {
		ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();

		map.put(1, "Scala");
		map.put(5, "Java");
		map.put(10, "Clojure");

		//ov= original value
		//v = specify value.
		String result = map.merge(1, "C++", (ov, v) -> {
			assertThat(ov, equalTo("Scala"));
			assertThat(v, equalTo("C++"));
			return ov + v;
		});
		assertThat(result, equalTo("ScalaC++"));
		assertThat(map.get(1), equalTo("ScalaC++"));

	}

	@Test
	public void testCompute() {
		ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();

		map.put(1, "Scala");
		map.put(5, "Java");
		map.put(10, "Clojure");

		String result = map.compute(1, (k, v) -> {
			assertThat(k, equalTo(1));
			assertThat(v, equalTo("Scala"));
			return "Hello";
		});

		assertThat(result, equalTo("Hello"));
		assertThat(map.get(1), equalTo("Hello"));

	}
}
