package com.hsjawanda.utilities.collections;

import java.util.Collection;

public final class Collections {

	private Collections() {
	}

	public static boolean isEmpty(Collection<?> collection) {
		return null == collection || collection.isEmpty();
	}

	public static boolean isNotEmpty(Collection<?> collection) {
		return !isEmpty(collection);
	}

	public static int size(Collection<?> collection) {
		return null == collection ? 0 : collection.size();
	}

}
