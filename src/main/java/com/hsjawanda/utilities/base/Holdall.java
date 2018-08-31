/**
 *
 */
package com.hsjawanda.utilities.base;

import static com.hsjawanda.utilities.base.Check.checkArgument;
import static com.hsjawanda.utilities.repackaged.commons.lang3.StringUtils.EMPTY;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.annotation.Nonnull;

/**
 * @author Harshdeep Jawanda <hsjawanda@gmail.com>
 *
 */
public final class Holdall {

	private static Logger LOG = Logger.getLogger(Holdall.class.getName());

	private Holdall() {
	}

	/**
	 * Ensure a {@code long} value falls within a closed range.
	 *
	 * @param lower
	 *            the lowest allowed value
	 * @param upper
	 *            the highest allowed value
	 * @param value
	 *            the value to constrain to within the closed range
	 * @return a range-checked value. If {@code value} is less than {@code lower}, {@code lower} is returned. If
	 *         {@code value} is greater than {@code upper}, {@code upper} is returned. Otherwise, {@code value} itself
	 *         is returned.
	 * @throws IllegalArgumentException
	 *             If {@code lower > upper}.
	 */
	public static long constrainToRange(long lower, long upper, long value) throws IllegalArgumentException {
		checkArgument(lower <= upper, "The lower bound must be <= upper bound.");
		if (value < lower)
			return lower;
		else if (value > upper)
			return upper;
		return value;
	}

	@Nonnull
	public static <T> T get(Optional<T> presetVar) {
		return presetVar.get();
	}

	/**
	 * Convert a stack trace to a {@code String}.
	 *
	 * @param e
	 *            the {@code Exception} whose stacktrace requires conversion
	 * @return the stacktrace as a {@code String}
	 */
	public static String getStacktrace(Exception e) {
		StringWriter sw = new StringWriter(500);
		e.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}

	public static <T> String printableList(List<T> alist) {
		if (null != alist) {
			final String nl = System.lineSeparator();
			int counter = 1;
			StringBuilder sb = new StringBuilder();
			for (T element : alist) {
				sb.append(String.format("%4d. ", counter++)).append(element).append(nl);
			}
			return sb.toString();
		}
		return EMPTY;
	}

	/**
	 * Print a {@code List<T>} to {@code System.out} with index numbers.
	 *
	 * @param alist
	 *            the {@code List<T>} to print
	 */
	public static <T> void printList(List<T> alist) {
		if (null != alist) {
			int counter = 0;
			for (T element : alist) {
				System.out.println(String.format("%5d: %s", counter++, element));
			}
		}
	}

	public static String removeJSessionId(String origUri) {
		if (null == origUri)
			return EMPTY;
		return origUri.replaceAll("(?i);JSESSIONID=.*", EMPTY);
	}

//	public static <T extends Comparable<T>> T constrainToRange(Range<T> range, T value) {
//		T retVal = value;
//		if (value.compareTo(range.lowerEndpoint()) < 0) {
//			retVal = range.lowerEndpoint();
//		} else if (value.compareTo(range.upperEndpoint()) > 0) {
//			retVal = range.upperEndpoint();
//		}
//		return retVal;
//	}

//	public static Integer constrainToRange(Range<Integer> range, String strVal,
//			@Nonnull Integer defaultValue) {
//		Integer value = Defaults.or(Ints.tryParse(defaultString(strVal)), defaultValue);
//		return constrainToRange(range, value);
//	}

	public static String showException(Throwable throwable) {
		if (null == throwable)
			return EMPTY;
		return new StringBuilder(50).append(throwable.getClass().getName()).append(" (").append(throwable.getMessage())
				.append(").").toString();
	}

	public static void sleep(long millis) {
		if (millis < 1)
			return;
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			LOG.warning("Sleep of " + millis + " ms interrupted.");
		}
	}

	public static Optional<String> urlDecode(String encodedStr) {
		if (null == encodedStr)
			return Optional.empty();
		try {
			return Optional.of(URLDecoder.decode(encodedStr, Constants.UTF_8));
		} catch (UnsupportedEncodingException e) {
			return Optional.empty();
		}
	}

}
