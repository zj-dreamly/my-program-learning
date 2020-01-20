package com.github.zj.dreamly.guava.utils;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.List;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Precondition
 * Objects
 * assert key word(statement)
 * @author 苍海之南
 */
public class PreconditionsTest {

	@Test(expected = NullPointerException.class)
	public void testCheckNotNull() {
		checkNotNull();
	}

	@Test
	public void testCheckNotNullWithMessage() {
		try {
			checkNotNullWithMessage(null);
		} catch (Exception e) {
			assertThat(e.getMessage(), equalTo("The list should not be null"));
		}
	}

	@Test
	public void testCheckNotNullWithFormatMessage() {
		try {
			checkNotNullWithFormatMessage(null);
		} catch (Exception e) {
			assertThat(e.getMessage(), equalTo("The list should not be null and the size must be 2"));
		}
	}

	@Test
	public void testCheckArguments() {
		final String type = "A";
		try {
			Preconditions.checkArgument(type.equals("B"));
		} catch (Exception ignored) {
		}
	}

	@Test
	public void testCheckState() {
		try {
			final String state = "A";
			Preconditions.checkState(state.equals("B"), "The state is illegal.");
			fail("should not process to here.");
		} catch (Exception e) {
		}
	}

	@Test
	public void testCheckIndex() {
		try {
			List<String> list = ImmutableList.of();
			Preconditions.checkElementIndex(10, list.size());
		} catch (Exception e) {
		}
	}

	@Test(expected = NullPointerException.class)
	public void testByObjects() {
		Objects.requireNonNull(null);
	}

	@Test(expected = AssertionError.class)
	public void testAssert() {
		List<String> list = null;
		assert list != null;
	}

	@Test
	public void testAssertWithMessage() {
		try {
			List<String> list = null;
			assert false : "The list should not be null.";
		} catch (Error e) {
			assertThat(e.getMessage(), equalTo("The list should not be null."));
		}
	}

	private void checkNotNull() {
		Preconditions.checkNotNull((List<String>) null);
	}

	private void checkNotNullWithMessage(final List<String> list) {
		Preconditions.checkNotNull(list, "The list should not be null");
	}

	private void checkNotNullWithFormatMessage(final List<String> list) {
		Preconditions.checkNotNull(list, "The list should not be null and the size must be %s", 2);
	}
}
