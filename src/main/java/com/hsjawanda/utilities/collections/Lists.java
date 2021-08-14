/**
 *
 */
package com.hsjawanda.utilities.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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

	public static <T> T get(List<T> list) {
		return get(list, 0, null);
	}

	public static <T> T get(List<T> list, int position) {
		return get(list, position, null);
	}

	public static <T> T get(List<T> list, int position, T defaultValue) {
		if (null != list && position >= 0 && position < list.size())
			return list.get(position);
		return defaultValue;
	}

	public static boolean isEmpty(List<?> list) {
		return null == list || list.isEmpty();
	}

	public static boolean isNotEmpty(List<?> list) {
		return !isEmpty(list);
	}

	public static <T> T last(List<T> list) {
		return last(list, null);
	}

	public static <T> T last(List<T> list, T defaultValue) {
		int size = size(list);
		return size == 0 ? defaultValue : list.get(size - 1);
	}

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
