package com.hsjawanda.utilities.numerical;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;


public class RandomIdGeneratorTests {

	@Test
	public void testMilliRandomCollisions() {
		DecimalFormat fmt = new DecimalFormat();
		long now = System.currentTimeMillis();
		int numToGen = 10_000_000, collisions = 0;
		Set<String> ids = new HashSet<>(numToGen, 1.0f);

		for (int i = 0; i < numToGen; i++) {
			if (!ids.add(RandomIdGenerator.milliRandom(now, 7))) {
				collisions++;
			}
		}
		System.out.println(
				"There were " + collisions + " collisions generating " + fmt.format(numToGen) + " milliRandom() IDs.");
	}

}
