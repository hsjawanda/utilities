/**
 *
 */
package com.hsjawanda.utilities.test;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import com.hsjawanda.utilities.numerical.Numbers;


/**
 * @author Harshdeep S Jawanda <hsjawanda@gmail.com>
 *
 */
public class NumbersTests {

	@Test
	public void indianFormatting() {
		assertEquals("-1,00,00,000.34569", Numbers.formatIndian(-1_00_00_000.34569));
		assertEquals("-10,00,000.15", Numbers.formatIndian(-10_00_000.15));
		assertEquals("1,00,00,00,000", Numbers.formatIndian(1_00_00_00_000));
		assertEquals("10.159", Numbers.formatIndian(BigDecimal.valueOf(10159, 3)));
		assertEquals("1,015.9", Numbers.formatIndian(BigDecimal.valueOf(10159, 1)));
	}

}
