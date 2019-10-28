package com.github.zj.dreamly.guava.utils.splitter;

import com.google.common.base.Splitter;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * <h2>SplitterTest</h2>
 *
 * @author: 苍海之南
 * @since: 2019-10-28 08:48
 **/
public class SplitterTest {
	@Test
	public void testSplitOnSplit() {
		List<String> result = Splitter.on("|").splitToList("hello|world");
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(2));
		assertThat(result.get(0), equalTo("hello"));
		assertThat(result.get(1), equalTo("world"));
	}

	@Test
	public void testSplitOnSplitOmitEmpty() {
		List<String> result = Splitter.on("|").splitToList("hello|world|||");
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(5));

		result = Splitter.on("|").omitEmptyStrings().splitToList("hello|world|||");
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(2));
	}

	@Test
	public void testSplitOnSplitOmitEmptyTrimResult() {
		List<String> result = Splitter.on("|").omitEmptyStrings().splitToList("hello | world|||");
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(2));
		assertThat(result.get(0), equalTo("hello "));
		assertThat(result.get(1), equalTo(" world"));

		result = Splitter.on("|").trimResults().omitEmptyStrings().splitToList("hello | world|||");
		assertThat(result.get(0), equalTo("hello"));
		assertThat(result.get(1), equalTo("world"));
	}

	/**
	 * aaaabbbbccccdddd
	 */
	@Test
	public void testSplitFixLength() {
		List<String> result = Splitter.fixedLength(4).splitToList("aaaabbbbccccdddd");
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(4));
		assertThat(result.get(0), equalTo("aaaa"));
		assertThat(result.get(3), equalTo("dddd"));
	}

	@Test
	public void testSplitOnSplitLimit() {
		List<String> result = Splitter.on("#").limit(3).splitToList("hello#world#java#google#scala");
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(3));
		assertThat(result.get(0), equalTo("hello"));
		assertThat(result.get(1), equalTo("world"));
		assertThat(result.get(2), equalTo("java#google#scala"));
	}

	@Test
	public void testSplitOnPatternString() {
		List<String> result = Splitter.onPattern("\\|").trimResults().omitEmptyStrings().splitToList("hello | world|||");
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(2));
		assertThat(result.get(0), equalTo("hello"));
		assertThat(result.get(1), equalTo("world"));
	}

	@Test
	public void testSplitOnPattern() {
		String regular = "\\|";
		List<String> result = Splitter.on(Pattern.compile(regular)).trimResults().omitEmptyStrings().splitToList("hello | world|||");
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(2));
		assertThat(result.get(0), equalTo("hello"));
		assertThat(result.get(1), equalTo("world"));
	}

	@Test
	public void testSplitOnSplitToMap() {
		String regular = "\\|";
		Map<String, String> result = Splitter.on(Pattern.compile(regular)).trimResults()
			.omitEmptyStrings().withKeyValueSeparator("=").split("hello=HELLO| world=WORLD|||");
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(2));
		assertThat(result.get("hello"), equalTo("HELLO"));
		assertThat(result.get("world"), equalTo("WORLD"));

	}
}
