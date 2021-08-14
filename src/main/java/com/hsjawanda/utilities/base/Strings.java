/**
 *
 */
package com.hsjawanda.utilities.base;

import static com.hsjawanda.utilities.repackaged.commons.lang3.StringUtils.EMPTY;
import static com.hsjawanda.utilities.repackaged.commons.lang3.StringUtils.isNotBlank;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Nonnull;


/**
 * @author Harshdeep S Jawanda (hsjawanda@gmail.com)
 *
 */
public class Strings {

	public static final String NULL = "null";

	private static final Logger log = Logger.getLogger(Strings.class.getName());

	private Strings() {
	}

//	@Nonnull
//	public static String toString(InternetAddress addr) {
//		return String.format("%s <%s>", defaultString(addr.getPersonal(), NULL),
//				defaultString(addr.getAddress(), NULL));
//	}

	/**
	 * Modified, improved of basic idea given at:
	 * <a href="http://helpdesk.objects.com.au/java/converting-large-byte-array-to-binary-string">here</a>.
	 *
	 * @param arr
	 * @return
	 */
	public static String asBinary(byte[] arr) {
		StringBuilder binary = new StringBuilder(arr.length * 8);
		for (int i = 0; i < arr.length; i++) {
			binary.append("00000000");
			for (int j = 0; j < 8; j++) {
				if (((arr[i] >> j) & 1) > 0) {
					binary.setCharAt((i * 8) + (7 - j), '1');
				}
			}
		}
		return binary.toString();
	}

	public static boolean endsWith(StringBuilder sb, String suffix) {
		if (null == sb || null == suffix)
			return false;
		final int idx = sb.lastIndexOf(suffix);
		if (idx < 0)
			return false;
		else
			return (idx + suffix.length() == sb.length());
	}

	public static Boolean getAsBoolean(String stringValue) {
		if (null == stringValue)
			return null;
		return Boolean.valueOf(stringValue);
	}

	public static Double getAsDouble(String stringValue) {
		if (null == stringValue)
			return null;
		try {
			return Double.valueOf(stringValue);
		} catch (NumberFormatException e) {
			log.warning("Exception converting '" + stringValue + "' to Double: "
					+ System.lineSeparator() + e.getMessage());
		} catch (Exception e) {
			log.log(Level.WARNING, "Exception converting '" + stringValue + "' to Double...", e);
		}
		return null;
	}

	public static int getAsInt(String stringValue, int defaultValue) {
		if (isNotBlank(stringValue)) {
			try {
				return Integer.parseInt(stringValue);
			} catch (NumberFormatException e) {
				log.warning(
						"Exception converting '" + stringValue + "' to int: " + e.getMessage());
			} catch (Exception e) {
				log.log(Level.WARNING,
						"Exception converting '" + stringValue + "' to int. Stacktrace:", e);
			}
		}
		return defaultValue;
	}

	public static Integer getAsInteger(String stringValue) {
		if (isNotBlank(stringValue)) {
			try {
				return Integer.valueOf(stringValue);
			} catch (NumberFormatException e) {
				log.warning(
						"Exception converting '" + stringValue + "' to Integer: " + e.getMessage());
			} catch (Exception e) {
				log.log(Level.WARNING,
						"Exception converting '" + stringValue + "' to Integer. Stacktrace:", e);
			}
		}
		return null;
	}

	public static Long getAsLong(String stringValue) {
		if (null == stringValue)
			return null;
		try {
			return Long.valueOf(stringValue);
		} catch (NumberFormatException e) {
			log.warning("Exception converting '" + stringValue + "' to Long: "
					+ System.lineSeparator() + e.getMessage());
		} catch (Exception e) {
			log.log(Level.WARNING, "Exception converting '" + stringValue + "' to Long...", e);
		}
		return null;
	}

	public static long getAsLong(String stringValue, long defaultValue) {
		if (isNotBlank(stringValue)) {
			try {
				return Long.parseLong(stringValue);
			} catch (NumberFormatException e) {
				log.warning(
						"Exception converting '" + stringValue + "' to long: " + e.getMessage());
			} catch (Exception e) {
				log.log(Level.WARNING,
						"Exception converting '" + stringValue + "' to long. Stacktrace:", e);
			}
		}
		return defaultValue;
	}

	public static String toString(byte[] input) {
		return null == input ? NULL : new String(input);
	}

	public static String toString(char[] input) {
		return null == input ? EMPTY : new String(input);
	}

	@Nonnull
	public static String toString(Object obj) {
		String retVal = null == obj ? EMPTY : obj.toString();
		return null == retVal ? EMPTY : retVal;
	}

	public static String toString(String input) {
		return null == input ? NULL : input;
	}

}
