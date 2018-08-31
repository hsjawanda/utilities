/**
 *
 */
package com.hsjawanda.utilities.numerical;

import static com.hsjawanda.utilities.base.Check.checkArgument;

import java.math.BigInteger;
import java.security.SecureRandom;

import com.hsjawanda.utilities.repackaged.commons.lang3.RandomStringUtils;

/**
 * @author Harshdeep S Jawanda (hsjawanda@gmail.com)
 *
 */
public class RandomIdGenerator {

	public static final char[] CHAR_POOL = new char[60];

	public static final int MAX_BITS = 128;

	public static final int MAX_PIN_CHARS = 20;

	public static final int MAX_RADIX = 64;

	public static final int MIN_BITS = 2;

	public static final int MIN_PIN_CHARS = 2;

	public static final int MIN_RADIX = 2;

	public static final String SIMPLE_PIN_CHARS = "abcdefghijklmnpqrstuvwxyz0123456789";

	public static final int UID_RADIX = 36;

	private static BaseX CONVERTER = BaseX.URL_SAFE_BASE64;

	private static SecureRandom RANDOMIZER = new SecureRandom();

	static {
		int i = 0;
		char allowed;
		for (allowed = '0'; allowed <= '9'; allowed++) {
			CHAR_POOL[i++] = allowed;
		}
		for (allowed = 'a'; allowed <= 'z'; allowed++) {
			CHAR_POOL[i++] = allowed;
		}
		for (allowed = 'A'; allowed <= 'Z'; allowed++) {
			if (allowed == 'O' || allowed == 'I') {
				continue;
			}
			CHAR_POOL[i++] = allowed;
		}
	}

	private RandomIdGenerator() {
	}

	public static String custom(int numBits) throws IllegalArgumentException {
		return CONVERTER.encode(customNumber(numBits), MAX_RADIX);
	}

	public static BigInteger customNumber(int numBits) throws IllegalArgumentException {
		checkArgument(numBits >= MIN_BITS, "numBits must be >= ", MIN_BITS);
		checkArgument(numBits <= MAX_BITS, "numBits must be <= ", MAX_BITS);
		return new BigInteger(numBits, RANDOMIZER);
	}

	/**
	 * Generate a random, positive {@code long}. Produces the full range of positive {@code long}s.
	 *
	 * @return
	 */
	public static long nextLong() {
		return new BigInteger(63, RANDOMIZER).longValue();
	}

	public static String simplePin(int numChars) {
		return simplePin(numChars, SIMPLE_PIN_CHARS);
	}

	public static String simplePin(int numChars, String charsToUse) throws IllegalArgumentException {
		checkArgument(numChars >= MIN_PIN_CHARS, "numChars must be >= %d", MIN_PIN_CHARS);
		checkArgument(numChars <= MAX_PIN_CHARS, "numChars must be <= %d", MAX_PIN_CHARS);
		int start = 0, end = null != charsToUse ? charsToUse.length() : 0;
		boolean letters = true, numbers = true;
		char[] chars = null != charsToUse ? charsToUse.toCharArray() : null;
		return RandomStringUtils.random(numChars, start, end, letters, numbers, chars, RANDOMIZER);
	}
}
