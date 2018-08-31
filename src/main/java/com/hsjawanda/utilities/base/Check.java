/**
 *
 */
package com.hsjawanda.utilities.base;

/**
 * @author Harshdeep S Jawanda <hsjawanda@gmail.com>
 *
 */
public final class Check {

	private Check() {
	}

	public static void checkArgument(boolean condition, String message) throws IllegalArgumentException {
		if (!condition)
			throw new IllegalArgumentException(message);
	}

	public static void checkArgument(boolean condition, String format, Object... args) throws IllegalArgumentException {
		checkArgument(condition, String.format(format, args));
	}

}
