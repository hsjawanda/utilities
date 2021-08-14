/**
 *
 */
package com.hsjawanda.utilities.base;

import static com.hsjawanda.utilities.repackaged.commons.lang3.StringUtils.trimToEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.hsjawanda.utilities.collections.Lists;

public class Enums {

	private static final String DELIMITER = ", ";

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

	public static <T extends Enum<T>> boolean isValidValue(Class<T> enumCls, String enumValue) {
		try {
			Enum.valueOf(enumCls, enumValue);
			return true;
		} catch (IllegalArgumentException | NullPointerException e) {
			return false;
		}
	}

	public static <T extends Enum<T>> boolean isNotValidValue(Class<T> enumCls, String enumValue) {
		return !isValidValue(enumCls, enumValue);
	}

	public static <T extends Enum<T>> T valueOfOrDefault(Class<T> enumCls, String enumValue, T defaultValue) {
		try {
			return Enum.valueOf(enumCls, enumValue);
		} catch (IllegalArgumentException | NullPointerException e) {
			return defaultValue;
		}
	}

	public static <T extends Enum<T>> List<T> valuesOf(@Nonnull Class<T> enumCls, List<String> enumValues,
			boolean prepValues) throws NullPointerException, IllegalArgumentException {
		Objects.requireNonNull(enumCls, "enumCls can't be null");
		List<T> retVal = new ArrayList<>();
		List<String> badVals = new ArrayList<>();
		if (Lists.isEmpty(enumValues))
			return Lists.empty();
		for (String enumVal : enumValues) {
			try {
				retVal.add(Enum.valueOf(enumCls, prepValues ? trimToEmpty(enumVal).toUpperCase() : enumVal));
			} catch (IllegalArgumentException | NullPointerException e) {
				badVals.add(String.valueOf(enumVal));
			}
		}
		if (Lists.isNotEmpty(badVals)) {
			throw new IllegalArgumentException(String.format("List contained values invalid for enum %s: %s",
					enumCls.getSimpleName(), String.join(DELIMITER, badVals)));
		}
		return retVal;
	}

	public static <T extends Enum<T>> List<T> valuesOf(@Nonnull Class<T> enumCls, List<String> enumValues)
			throws NullPointerException, IllegalArgumentException {
		return valuesOf(enumCls, enumValues, false);
	}

}
