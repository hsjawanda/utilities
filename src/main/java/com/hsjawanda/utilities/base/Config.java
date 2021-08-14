/**
 *
 */
package com.hsjawanda.utilities.base;

import static com.hsjawanda.utilities.repackaged.commons.lang3.StringUtils.EMPTY;
import static com.hsjawanda.utilities.repackaged.commons.lang3.StringUtils.isBlank;

import java.util.HashSet;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.hsjawanda.utilities.collections.NonNullList;


/**
 * @author Harsh.Deep
 *
 */
public class Config {

	protected static Set<String> bundleFilenames;

	protected static List<ResourceBundle> bundles = NonNullList.empty(2);

	protected static final String KEYS_FILENAME = "keys";

	protected static String keysFailureReason = EMPTY;

	protected static boolean loaded = false;

	protected static final Logger log = Logger.getLogger(Config.class.getName());

	protected static final String PROP_FILENAME = "config";

	protected static String propFailureReason = EMPTY;

	protected static ResourceBundle rbKeys = null;

	protected static ResourceBundle rbProp = null;

	static {
		bundleFilenames = new HashSet<>(2);
		bundleFilenames.add(KEYS_FILENAME);
		bundleFilenames.add(PROP_FILENAME);
		for (String bundleFilename : bundleFilenames) {
			try {
				bundles.add(ResourceBundle.getBundle(bundleFilename));
				loaded = true;
			} catch (MissingResourceException e) {
				log.info("Couldn't find resource bundle: " + bundleFilename);
			}
		}
	}

	public static Optional<String> get(String key) {
		if (isBlank(key))
			return Optional.empty();
		String retVal = null;
		if (loaded) {
			for (ResourceBundle rb : bundles) {
				try {
					retVal = rb.getString(key);
					break;
				} catch (MissingResourceException e) {
					// Do nothing
				} catch (Exception e) {
					log.log(Level.WARNING, "Error getting value for key '" + key + "'", e);
				}
			}
		}
		return Optional.ofNullable(retVal);
	}

//	public static <T extends Enum<?>> Optional<String> get(T key) {
//		if (null == key)
//			return Optional.empty();
//		CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, key.name());
//		return get(key.name());
//	}

	public static Boolean getAsBoolean(String key) {
		return Strings.getAsBoolean(get(key).orElse(null));
	}

//	public static <T extends Enum<?>> String getOrEmpty(T key) {
//		return get(key).or(EMPTY);
//	}

//	public static <T extends Enum<?>> Boolean getAsBoolean(T key) {
//		return Strings.getAsBoolean(get(key).orElse(null));
//	}

//	public static <T extends Enum<?>> String getOrNull(T key) {
//		return get(key).orElse(null);
//	}

//	public static <T extends Enum<?>> Long getAsLong(T key) {
//		return Strings.getAsLong(get(key).orElse(null));
//	}

	public static int getAsInt(String key, int defaultValue) {
		return Strings.getAsInt(get(key).orElse(null), defaultValue);
	}

	public static Integer getAsInteger(String key) {
		return Strings.getAsInteger(get(key).orElse(null));
	}

	public static Long getAsLong(String key) {
		return Strings.getAsLong(get(key).orElse(null));
	}

	public static long getAsLong(String key, long defaultValue) {
		return Strings.getAsLong(get(key).orElse(null), defaultValue);
	}

	/**
	 * Get the value corresponding to {@code key}.
	 *
	 * @param key
	 *            the key to get value for
	 * @return the value, or the empty string if no value was found
	 */
	public static String getOrEmpty(String key) {
		return get(key).orElse(EMPTY);
	}

	/**
	 * Get the value corresponding to {@code key}.
	 *
	 * @param key
	 *            the {@code key} to get value for
	 * @return the value, or {@code null} if no value was found
	 */
	public static String getOrNull(String key) {
		return get(key).orElse(null);
	}
}
