/**
 *
 */
package com.hsjawanda.utilities.numerical;

import static com.hsjawanda.utilities.base.Check.checkArgument;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import com.hsjawanda.utilities.base.Base64;
import com.hsjawanda.utilities.base.Base64.Decoder;
import com.hsjawanda.utilities.base.Base64.Encoder;
import com.hsjawanda.utilities.repackaged.commons.lang3.RandomStringUtils;

/**
 * @author Harshdeep S Jawanda (hsjawanda@gmail.com)
 *
 */
public class RandomIdGenerator {

	public static final Decoder B64_URL_SAFE_DEC = Base64.getUrlDecoder();

	public static final Encoder B64_URL_SAFE_ENC = Base64.getUrlEncoder().withoutPadding();

	public static final char[] CHAR_POOL = new char[60];

	public static final int MAX_BITS = 128;

	public static final int MAX_PIN_CHARS = 20;

	public static final int MAX_RADIX = 64;

	public static final int MIN_BITS = 2;

	public static final int MIN_PIN_CHARS = 2;

	public static final int MIN_RADIX = 2;

	public static final String SIMPLE_PIN_CHARS = "abcdefghijklmnpqrstuvwxyz0123456789";

	public static final int UID_RADIX = 36;

	private static final Map<Integer, byte[]> BYTE_ARRAYS = new HashMap<>();

	private static final Map<Integer, ByteBuffer> BYTE_BUFFERS = new HashMap<>();

	private static BaseX CONVERTER = BaseX.URL_SAFE_BASE64;

	private static final int INSTANT_BYTES = 12;

	private static SecureRandom RANDOMIZER = new SecureRandom();

	private static final java.util.Base64.Encoder URL_SAFE_STD_B64_ENC = java.util.Base64.getUrlEncoder()
			.withoutPadding();

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

	public static String instantRandom() {
		return instantRandom(3, Instant.now());
	}

	public static String instantRandom(Instant instant) {
		return instantRandom(3, instant);
	}

	public static synchronized String instantRandom(int numRandomBytes, Instant instant)
			throws IllegalArgumentException {
		checkArgument(numRandomBytes > 0 && numRandomBytes % 3 == 0, "numRandomBytes must be a positive multiple of 3");
		byte[] timeEncoded = instantRandomBytes(numRandomBytes, instant);
		return B64_URL_SAFE_ENC.encodeToString(timeEncoded);
	}

	public static byte[] instantRandomBytes(int numRandomBytes, Instant instant) {
		byte[] rndBuff = getByteArray(numRandomBytes);
		RANDOMIZER.nextBytes(rndBuff);
		ByteBuffer bb = (ByteBuffer) getByteBuffer(numRandomBytes).clear();
		byte[] timeEncoded = bb.putLong(instant.getEpochSecond()).putInt(instant.getNano()).put(rndBuff).array();
		return timeEncoded;
	}

	public static String milliRandom(long timeInMillis, int numRandomBytes) throws IllegalArgumentException {
		checkArgument(numRandomBytes > 0 && (numRandomBytes - 4) % 3 == 0,
				"(numRandomBytes - 4) must be a positive multiple of 3");
		byte[] rndBuff = getByteArray(numRandomBytes);
		RANDOMIZER.nextBytes(rndBuff);
		byte[] timeMilliEncoded = ByteBuffer.allocate(8 + numRandomBytes).putLong(timeInMillis).put(rndBuff).array();
		return B64_URL_SAFE_ENC.encodeToString(timeMilliEncoded);
	}

	/**
	 * Generate a random, non-negative {@code long}. Produces the full range of non-negative {@code long}s.
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

	public static String string(int numBits) throws IllegalArgumentException {
		checkArgument(numBits >= MIN_BITS, "numBits must be >= %d", MIN_BITS);
		checkArgument(numBits <= MAX_BITS, "numBits must be <= %d", MAX_BITS);
		checkArgument(numBits % 8 == 0, "numBits (%d) doesn't completely divide by 8. Choose another number", numBits);
		byte [] bytes = new byte[numBits / 8];
		RANDOMIZER.nextBytes(bytes);
		return URL_SAFE_STD_B64_ENC.encodeToString(bytes);
	}

	private static byte[] getByteArray(int size) {
		Integer sz = Integer.valueOf(size);
		if (!BYTE_ARRAYS.containsKey(sz)) {
			BYTE_ARRAYS.put(sz, new byte[size]);
		}
		return BYTE_ARRAYS.get(sz);
	}

	private static ByteBuffer getByteBuffer(int size) {
		Integer sz = Integer.valueOf(size);
		if (!BYTE_BUFFERS.containsKey(sz)) {
			BYTE_BUFFERS.put(sz, ByteBuffer.allocate(INSTANT_BYTES + size));
		}
		return BYTE_BUFFERS.get(sz);
	}

}
