package com.hsjawanda.utilities.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.hsjawanda.utilities.numerical.RandomIdGenerator;

public class RandomIdGeneratorTests {

	@Test
	public void testRandomStringIds() {
		final int idsToGen = 1_000;
		Set<String> uniqueIds = new HashSet<>((idsToGen * 75) / 100);
		for (int i = 0; i < idsToGen; i++) {
			String rndId = RandomIdGenerator.string(120);
			assertTrue(uniqueIds.add(rndId));
//			System.out.format("%3d. Random ID: %s\t num chars: %d\n", (i + 1), rndId, rndId.length());
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testStringIdBitsGreaterThanMax() {
		RandomIdGenerator.string(RandomIdGenerator.MAX_BITS + 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testStringIdBitsIndivisibleBy8() {
		RandomIdGenerator.string(53);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testStringIdBitsLessThanMin() {
		RandomIdGenerator.string(RandomIdGenerator.MIN_BITS - 1);
	}

	@Test
	public void testStringIdLength() {
		String randomId;

		randomId = RandomIdGenerator.string(56);
		System.out.println("Random ID: " + randomId);
		assertEquals(10, randomId.length());

		randomId = RandomIdGenerator.string(96);
		System.out.println("Random ID: " + randomId);
		assertEquals(16, randomId.length());

		randomId = RandomIdGenerator.string(48);
		System.out.println("Random ID: " + randomId);
		assertEquals(8, randomId.length());

		randomId = RandomIdGenerator.string(64);
		System.out.println("Random ID: " + randomId);
		assertEquals(11, randomId.length());
}

}
