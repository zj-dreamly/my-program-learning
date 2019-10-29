package com.github.zj.dreamly.guava.collections;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * <h2>MultimapsExampleTest</h2>
 *
 * @author: 苍海之南
 * @since: 2019-10-29 14:46
 **/
public class MultiMapsExampleTest {
	@Test
	public void testBasic()
	{
		LinkedListMultimap<String, String> multipleMap = LinkedListMultimap.create();
		HashMap<String, String> hashMap = Maps.newHashMap();
		hashMap.put("1", "1");
		hashMap.put("1", "2");
		assertThat(hashMap.size(), equalTo(1));


		multipleMap.put("1", "1");
		multipleMap.put("1", "2");
		assertThat(multipleMap.size(), equalTo(2));
		System.out.println(multipleMap.get("1"));
	}
}
