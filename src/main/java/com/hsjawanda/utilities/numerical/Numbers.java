/**
 *
 */
package com.hsjawanda.utilities.numerical;

import static com.hsjawanda.utilities.base.Check.checkArgument;
import static com.hsjawanda.utilities.repackaged.commons.lang3.StringUtils.defaultString;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Optional;
import java.util.logging.Logger;

import javax.annotation.CheckForNull;
import javax.annotation.Nullable;

/**
 * @author Harshdeep S Jawanda <hsjawanda@gmail.com>
 *
 */
public final class Numbers {

	private static final NumberFormat FORMATTER = NumberFormat.getInstance();

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(Numbers.class.getName());

	private static final DecimalFormat PLAIN_FMT = new DecimalFormat();

	static {
		PLAIN_FMT.setGroupingUsed(false);
		PLAIN_FMT.setDecimalSeparatorAlwaysShown(false);
		PLAIN_FMT.setMaximumFractionDigits(10);
		PLAIN_FMT.setRoundingMode(RoundingMode.HALF_UP);
	}

	private Numbers() {}

	/**
	 * Ensure a {@code long} value falls within a closed range.
	 *
	 * @param lowerInclusiveBound
	 *            the lowest allowed value
	 * @param upperInclusiveBound
	 *            the highest allowed value
	 * @param actualValue
	 *            the value to constrain to within the closed range
	 * @return a range-checked value. If {@code actualValue} is less than {@code lowerInclusiveBound},
	 *         {@code lowerInclusiveBound} is returned. If {@code actualValue} is greater than
	 *         {@code upperInclusiveBound}, {@code upperInclusiveBound} is returned. Otherwise, {@code actualValue}
	 *         itself is returned.
	 * @throws IllegalArgumentException
	 *             If {@code lowerInclusiveBound > upperInclusiveBound}.
	 */
	public static long constrainToRange(long lowerInclusiveBound, long upperInclusiveBound, long actualValue)
			throws IllegalArgumentException {
		checkArgument(lowerInclusiveBound <= upperInclusiveBound, "The lower bound must be <= upper bound.");
		if (actualValue < lowerInclusiveBound)
			return lowerInclusiveBound;
		else if (actualValue > upperInclusiveBound)
			return upperInclusiveBound;
		return actualValue;
	}

	@CheckForNull
	public static Long asLong(Object obj) throws IllegalArgumentException {
		if (null == obj)
			return null;
		checkArgument(obj instanceof Number, "Argument '%s' couldn't be read as a valid Number", obj);
		return Long.valueOf(((Number) obj).longValue());
	}

	@Nullable
	public static Long longValueOf(@Nullable String idStr) {
		try {
			return Long.valueOf(defaultString(idStr));
		} catch (NumberFormatException | NullPointerException e) {
			return null;
		}
	}

	public static String maxFrequencyFragment(long seconds) throws IllegalArgumentException {
		checkArgument(seconds > 0, "seconds must be > 0");
		return Long.toString(System.currentTimeMillis() / (seconds * 1000));
	}

	public static int paddingRequiredFor(long maxNumber) {
		return (int) Math.floor(Math.log10(maxNumber)) + 1;
	}

	public static Optional<Number> parse(@Nullable String number) {
		return parse(number, null);
	}

	public static Optional<Number> parse(@Nullable String number, @Nullable NumberFormat formatter) {
		formatter = null == formatter ? FORMATTER : formatter;
		try {
			return Optional.of(formatter.parse(number));
		} catch (ParseException | NullPointerException e) {
			return Optional.empty();
		}
	}

	public static BigDecimal percentOf(long value, long percent, RoundingMode rm) {
		return BigDecimal.valueOf(value * percent).divide(BigDecimal.valueOf(100), rm);
	}

	public static long randomizeByPercent(final long value, final long percent) throws IllegalArgumentException {
		if (percent < 0 || percent > 100)
			throw new IllegalArgumentException("percent must be in the range [0, 100]");
		long randomizationAmount = value * percent / 100;
		return ((long) Math.random() * 2 * randomizationAmount) + value - randomizationAmount;
	}

//	public static String formatIndian(long number) {
//		return formatIndian(String.valueOf(number));
//	}

	public static String formatIndian(double number) {
		return formatIndian(PLAIN_FMT.format(number));
	}

	public static String formatIndian(BigDecimal number) {
		return formatIndian(PLAIN_FMT.format(number));
	}

	private static String formatIndian(final String input) {
//		LOG.info("input: " + input);
		boolean decimalCrossed = input.contains(".") ? false : true;
		StringBuilder sb = new StringBuilder(input.length() + 10);
		for (int i = input.length() - 1, j = 1; i >= 0 ; i--, j++) {
			char c = input.charAt(i);
			if (c == '.') {
				decimalCrossed = true;
				j = 0;
			}
			if (decimalCrossed && Character.isDigit(c) && j > 3 && j % 2 == 0) {
				sb.append(',');
			}
			sb.append(c);
		}
		return sb.reverse().toString();
	}

}
