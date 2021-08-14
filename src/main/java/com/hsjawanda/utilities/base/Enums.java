/**
 *
 */
package com.hsjawanda.utilities.base;

import static com.hsjawanda.utilities.repackaged.commons.lang3.StringUtils.trimToEmpty;

import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Harshdeep S Jawanda <hsjawanda@gmail.com>
 *
 */
public final class Enums {

	private Enums() {
	}

	public static <T extends Enum<T>> T from(@Nonnull Class<T> cls, @Nullable String value)
			throws NullPointerException {
		Objects.requireNonNull(cls);
		return from(cls, value, null);
	}

	public static <T extends Enum<T>> T from(@Nonnull Class<T> cls, @Nullable String value, @Nullable T defaultValue)
			throws NullPointerException {
		Objects.requireNonNull(cls);
		try {
			return Enum.valueOf(cls, trimToEmpty(value).toUpperCase());
		} catch (IllegalArgumentException e) {
			return defaultValue;
		}
	}

}
