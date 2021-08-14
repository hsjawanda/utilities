package com.hsjawanda.utilities.test;

import static com.hsjawanda.utilities.repackaged.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.hsjawanda.utilities.base.Normalize;

public class NormalizeTests {

	@Test
	public void testTagEmptyString() {
		String tag = Normalize.get().tag(EMPTY);
		assertNull(tag);
	}

	@Test
	public void testTagMultipleNonAlphanumeric() {
		String input = "  THIS WILL&&&BE a   tag";
		String output = Normalize.get().tag(input);
		assertEquals("this-will-be-a-tag", output);
	}

	@Test
	public void testTagMultipleSpaces() {
		String input = "  THIS WILL BE a   tag";
		String output = Normalize.get().tag(input);
		assertEquals("this-will-be-a-tag", output);
	}

	@Test
	public void testTagNull() {
		String tag = Normalize.get().tag(null);
		assertNull(tag);
	}

}
