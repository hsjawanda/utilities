/**
 *
 */
package com.hsjawanda.utilities.numerical;

import static com.hsjawanda.utilities.base.Check.checkArgument;
import static java.util.Objects.requireNonNull;

import java.math.BigInteger;
import java.util.Objects;

import com.hsjawanda.utilities.base.Constants;

/**
 * @author Harshdeep S Jawanda <hsjawanda@gmail.com>
 *
 */
public class BaseX {

	public static final int MAX_RADIX = 64;

	public static final int MIN_RADIX = 2;

	public static final BaseX URL_SAFE_BASE64 = get("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_-");

	private String charset;

	private int length;

	private BaseX(String charset) {
		this.charset = charset;
		this.length = this.charset.length();
	}

	public static BaseX get(String charset) throws NullPointerException, IllegalArgumentException {
		Objects.requireNonNull(charset, "charset" + Constants.NOT_NULL);
		checkArgument(charset.length() >= 10, "charset must have a minimum length of 10.");
		checkArgument(charset.length() <= 128, "charset can have a maximum length of 128.");
		return new BaseX(charset);
	}

	public long decode10(String number) {
		requireNonNull(number, "number" + Constants.NOT_NULL);
		long retVal = 0;
		for (int i = 0; i < number.length(); i++) {
			int index = this.charset.indexOf(number.charAt(i));
			if (index == -1)
				throw new IllegalArgumentException("This number wasn't generated using this encoder.");
			retVal = retVal * this.length + index;
		}
		return retVal;
	}

	public String encode(BigInteger bigInt, int radix) throws IllegalArgumentException {
		checkArgument(radix <= this.length, "The value of radix (%d) has to be <= charset length (%d).", radix,
				this.length);
		BigInteger radixBigInt = BigInteger.valueOf(radix);
		BigInteger[] divAndRem;
		StringBuilder sb = new StringBuilder(30);
		bigInt = bigInt.abs();
		while (bigInt.compareTo(BigInteger.ZERO) > 0) {
			divAndRem = bigInt.divideAndRemainder(radixBigInt);
			sb.append(this.charset.charAt(divAndRem[1].intValue()));
			bigInt = divAndRem[0];
		}
		return sb.reverse().toString();
	}

	public String encode10(long number) throws IllegalArgumentException {
		checkArgument(number >= 0, "Only non-negative numbers can be encoded");
		StringBuilder encoded = new StringBuilder(20);
		if (number == 0)
			return encoded.append(this.charset.charAt(0)).toString();
		encode10(number, encoded);
		return encoded.reverse().toString();
	}

	private void encode10(long number, StringBuilder encoded) {
		while (number > 0) {
			long remainder = number % this.length;
			number = number / this.length;
			encoded.append(this.charset.charAt((int) remainder));
		}
	}

}
