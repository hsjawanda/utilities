package com.hsjawanda.utilities.numerical;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class NumbersTests {

	@Test
	public void test() {
		Integer num = Integer.valueOf(20);
		assertTrue("Integer is not an instance of Number", num instanceof Number);
		assertTrue(Number.class.isAssignableFrom(Integer.class));
		assertFalse(Integer.class.isAssignableFrom(Number.class));
	}

	@Test
	public void parseIntegerAsLong() {
		final int input = 38;
		long num = Numbers.asLong(Integer.valueOf(input));
		assertEquals(input, num);
	}

	@Test
	public void parseLongAsLong() {
		final long input = 38;
		long num = Numbers.asLong(Long.valueOf(input));
		assertEquals(input, num);
	}

	@Test(expected = IllegalArgumentException.class)
	public void parseStringAsLong() {
		Numbers.asLong("a string");
	}

}
