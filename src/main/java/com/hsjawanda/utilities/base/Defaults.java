/**
 *
 */
package com.hsjawanda.utilities.base;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

/**
 * @author Harshdeep S Jawanda <hsjawanda@gmail.com>
 *
 */
public final class Defaults {

	private static Logger LOG;

	private Defaults() {
	}

	@CheckForNull
	public static Boolean asBoolean(String boolString) {
		return null == boolString ? null : Boolean.valueOf(boolString);
	}

	public static boolean asBoolean(String boolString, boolean defaultVal) {
		return null != boolString ? Boolean.valueOf(boolString).booleanValue() : defaultVal;
	}

	public static double asDouble(Double value, double defaultVal) {
		return null == value ? defaultVal : value.doubleValue();
	}

	public static Double asDouble(String doubleStr, double defaultVal) {
		try {
			return Double.parseDouble(doubleStr);
		} catch (Exception e) {
			if (e instanceof NumberFormatException) {
				log().warning(e.getMessage());
			} else {
				log().log(Level.WARNING, "Couldn't parse '" + doubleStr + "' as a valid double. Returned default.",
						e);
			}
		}
		return defaultVal;
	}

	public static int asInt(Integer input, int defaultVal) {
		return null == input ? defaultVal : input.intValue();
	}

	public static int asInt(Object obj, int defaultValue) {
		if (null != obj) {
			try {
				return ((Integer) obj).intValue();
			} catch (Exception e) {
				log().log(Level.WARNING, "Error getting int value from Object. Returning default.", e);
			}
		}
		return defaultValue;
	}

	public static int asInt(String intString, int defaultVal) {
		if (null != intString) {
			try {
				return Integer.parseInt(intString);
			} catch (Exception e) {
				if (e instanceof NumberFormatException) {
					log().warning(e.getMessage());
				} else {
					log().log(Level.WARNING, "Couldn't parse '" + intString + "' as a valid number. Returned default.",
							e);
				}
			}
		}
		return defaultVal;
	}

	public static long asLong(Long value, long defaultVal) {
		return null == value ? defaultVal : value.longValue();
	}

	public static long asLong(String longString, long defaultVal) {
		if (null != longString) {
			try {
				return Long.parseLong(longString);
			} catch (Exception e) {
				if (e instanceof NumberFormatException) {
					log().warning(e.getMessage());
				} else {
					log().log(Level.WARNING, "Couldn't parse '" + longString + "' as a valid number. Returned default.",
							e);
				}
			}
		}
		return defaultVal;
	}

	/**
	 * Return a default value if the input object is {@code null}.
	 *
	 * @param obj
	 *            the input object
	 * @param defaultValue
	 *            the default value to use
	 * @return {@code obj} if it is not-null, {@code defaultValue} otherwise
	 */
	@Nonnull
	public static <T> T or(T obj, @Nonnull T defaultValue) {
		return null == obj ? defaultValue : obj;
	}

	private static Logger log() {
		if (null == LOG) {
			LOG = Logger.getLogger(Defaults.class.getName());
		}
		return LOG;
	}
}
