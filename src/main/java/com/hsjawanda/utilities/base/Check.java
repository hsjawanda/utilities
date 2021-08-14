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

	public static void checkCondition(boolean condition, String message) throws RuntimeException {
		if (!condition)
			throw new RuntimeException(message);
	}

	public static void checkCondition(boolean condition, String format, Object... args) throws RuntimeException {
		checkCondition(condition, String.format(format, args));
	}

	public static void checkOperationSupported(boolean condition, String message) throws UnsupportedOperationException {
		if (!condition)
			throw new UnsupportedOperationException(message);
	}

	public static void checkOperationSupported(boolean condition, String format, Object... args)
			throws UnsupportedOperationException {
		if (!condition)
			throw new UnsupportedOperationException(String.format(format, args));
	}

	public static void checkState(boolean condition, String message) throws IllegalStateException {
		if (!condition)
			throw new IllegalStateException(message);
	}

	public static void checkState(boolean condition, String format, Object... args) throws IllegalStateException {
		checkState(condition, String.format(format, args));
	}

}
