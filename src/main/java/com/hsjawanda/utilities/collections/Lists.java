/**
 *
 */
package com.hsjawanda.utilities.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nonnull;

/**
 * @author Harshdeep S Jawanda <hsjawanda@gmail.com>
 *
 */
public final class Lists {

	private static Logger LOG;

	private static final List<?> EMPTY = new ArrayList<>();

	private Lists() {
	}

	public static <T> List<T> empty() {
		@SuppressWarnings("unchecked")
		List<T> retVal = (List<T>) EMPTY;
		return retVal;
	}

	/**
	 * Return the first element in a list if possible, {@code null} otherwise.
	 * Performs {@code null}-checking and {@code List} size checking.
	 *
	 * @param list the {@code List} to return the element from.
	 * @return the first element or {@code null} (if the {@code List} is
	 *         {@code null} or empty).
	 */
	public static <T> T get(List<T> list) {
		return get(list, 0, null);
	}

	/**
	 * Return the element at {@code index} if possible, {@code null} otherwise.
	 *
	 * @param list  the {@code List} to return the element from
	 * @param index the index in the {@code List} from where to return the element
	 * @return the element at {@code index} or {@code null} (if the {@code List} is
	 *         {@code null} or {@code index} is out of bounds)
	 */
	public static <T> T get(List<T> list, int index) {
		return get(list, index, null);
	}

	public static <T> boolean isEmpty(List<T> list) {
		return null == list || list.size() == 0;
	}

	public static <T> boolean isNotEmpty(List<T> list) {
		return !isEmpty(list);
	}

	/**
	 * Return the element at index {@code index} if possible, {@code defaultValue}
	 * otherwise.
	 *
	 * @param list         the {@code List} to return the element from
	 * @param index        the index in the {@code List} from where to return the
	 *                     element
	 * @param defaultValue the value to return in case the element at {@code index}
	 *                     can't be retrieved.
	 * @return the element at {@code index} or {@code defaultValue} (if the
	 *         {@code List} is {@code null} or the {@code index} is out of bounds.)
	 */
	public static <T> T get(List<T> list, int index, T defaultValue) {
		if (null != list && index >= 0 && index < list.size())
			return list.get(index);
		return defaultValue;
	}

	public static <T> T getLast(List<T> list) {
		return get(list, Collections.size(list) - 1);
	}

	/**
	 * Instantiate and return a new {@link ArrayList} containing the elements passed
	 * as arguments. The elements appear in the same order as the arguments.
	 *
	 * @param elements the elements to add to the {@code List}. Can be empty or
	 *                 {@code null}
	 * @return the newly instantiated array (never {@code null})
	 */
	@SafeVarargs
	@Nonnull
	public static <T> ArrayList<T> newArrayListOf(T... elements) {
		ArrayList<T> retList = new ArrayList<>(null != elements ? Math.max(10, elements.length) : 10);
		if (null != elements) {
			for (T element : elements) {
				retList.add(element);
			}
		}
		return retList;
	}

	/**
	 * Instantiate and return a new {@link ArrayList} containing the elements passed
	 * as arguments. The elements appear in the same order as the arguments.
	 *
	 * @param elements the elements to add to the {@code List}. Can be empty or
	 *                 {@code null}
	 * @return the newly instantiated array (never {@code null})
	 */
	@SafeVarargs
	@Nonnull
	public static <T> ArrayList<T> newListOf(int minCapacity, T... elements) {
		ArrayList<T> retList = new ArrayList<>(null != elements ? Math.max(minCapacity, elements.length) : minCapacity);
		if (null != elements) {
			for (T element : elements) {
				retList.add(element);
			}
		}
		return retList;
	}

	public static <T> T last(List<T> list) {
		return last(list, null);
	}

	public static <T> T last(List<T> list, T defaultValue) {
		int size = size(list);
		return size == 0 ? defaultValue : list.get(size - 1);
	}

	/**
	 * Safely get the size of a {@code List}. {@code null} safe.
	 *
	 * @param list the list whose size is to be found
	 * @return the size of the {@code List} or 0 is {@code list} is null
	 */
	@Deprecated
	public static int size(List<?> list) {
		return null == list ? 0 : list.size();
	}

	@SuppressWarnings("unused")
	private static Logger log() {
		if (null == LOG) {
			LOG = Logger.getLogger(Lists.class.getName());
		}
		return LOG;
	}

}
