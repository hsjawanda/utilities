/**
 *
 */
package com.hsjawanda.utilities.base;

import static com.hsjawanda.utilities.repackaged.commons.lang3.StringUtils.EMPTY;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Nonnull;

import com.hsjawanda.utilities.repackaged.commons.lang3.RandomStringUtils;

/**
 * @author Harshdeep Jawanda <hsjawanda@gmail.com>
 *
 */
public final class Holdall {

	public static final String DEF_CODE_GEN_CHARS = "abcdefghijklmnopqrstuvwxyz0123456789";

	private static final String INDENT = "    ";

	private static Logger LOG;

	private Holdall() {
	}

	public static boolean alwaysFalse() {
		return false;
	}

	public static boolean alwaysTrue() {
		return true;
	}

	public static String genRandomString(int count) {
		return genRandomString(count, DEF_CODE_GEN_CHARS);
	}

	public static String genRandomString(int count, String charsToUse) {
		return RandomStringUtils.random(count, charsToUse);
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

	public static String removeJSessionId(String origUri) {
		if (null == origUri)
			return EMPTY;
		return origUri.replaceAll("(?i);JSESSIONID=.*", EMPTY);
	}

	public static String showCause(Throwable throwable) {
		if (null == throwable)
			return EMPTY;
		StringBuilder msg = new StringBuilder(150);
		showCause(throwable, msg, 0);
		return msg.toString();
	}

	public static String showException(Throwable throwable) {
		if (null == throwable)
			return EMPTY;
		return new StringBuilder(50).append(throwable.getClass().getName()).append(": ")
				.append(throwable.getMessage()).append(".").toString();
	}

	public static void sleep(long millis) {
		if (millis < 1)
			return;
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			log().warning("Sleep of " + millis + " ms interrupted.");
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

	public static Optional<String> urlEncode(String plainStr) {
		if (null != plainStr) {
			try {
				return Optional.of(URLEncoder.encode(plainStr, Constants.UTF_8));
			} catch (UnsupportedEncodingException e) {
				// 01/10/2018
				log().log(Level.WARNING, "Error encoding plain string to URL-safe. Stacktrace:", e);
			}
		}
		return Optional.empty();
	}

	private static Logger log() {
		if (null == LOG) {
			LOG = Logger.getLogger(Holdall.class.getName());
		}
		return LOG;
	}

	private static StringBuilder nextLineIndent(StringBuilder sb, int level) {
		if (level >= 0) {
			sb.append('\n');
			for (int i = 0; i < level; i++) {
				sb.append(INDENT);
			}
		}
		return sb;
	}

	private static void showCause(Throwable throwable, @Nonnull StringBuilder msg, int level) {
		if (null == msg) {
			log().warning("Can't generate exception message if msg is null");
			return;
		}
		if (null == throwable)
			return;
		if (level > 0) {
			nextLineIndent(msg, level).append("Caused by: ");
		}
		StackTraceElement ste = throwable.getStackTrace()[0];
		msg.append(throwable.getClass().getName()).append(": ").append(throwable.getMessage());
		nextLineIndent(msg, level).append("at ").append(ste.getClassName()).append('.').append(ste.getMethodName())
				.append('(').append(ste.getFileName()).append(':').append(ste.getLineNumber()).append(")]");
//		if (level > 0) {
//			msg.append(')');
//		}
		showCause(throwable.getCause(), msg, ++level);
	}

}
