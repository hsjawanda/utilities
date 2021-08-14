/**
 *
 */
package com.hsjawanda.utilities.collections;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.logging.Logger;

import javax.annotation.Nonnull;

/**
 * Meant for single-threaded use.
 *
 * @author Harshdeep S Jawanda <hsjawanda@gmail.com>
 */
public final class MapBuilder<K, V> {

	private static Logger LOG;

	private Map<K, V> map;

	private boolean skipNullValues;

	private MapBuilder() {
	}

	public static <K, V> MapBuilder<K, V> inOrder() {
		return new MapBuilder<K, V>().setMap(new LinkedHashMap<>());
	}

	public static <K, V> MapBuilder<K, V> sorted() {
		return new MapBuilder<K, V>().setMap(new TreeMap<>());
	}

	public static <K, V> MapBuilder<K, V> unordered() {
		return new MapBuilder<K, V>().setMap(new HashMap<>());
	}

	@SuppressWarnings("unused")
	private static Logger log() {
		if (null == LOG) {
			LOG = Logger.getLogger(MapBuilder.class.getName());
		}
		return LOG;
	}

	public void clear() {
		this.map.clear();
	}

	public boolean containsKey(K key) {
		return this.map.containsKey(key);
	}

	public boolean containsValue(V value) {
		return this.map.containsValue(value);
	}

	/**
	 * A {@link Map<K, V>} view of the key-value pairs.
	 *
	 * @return a unmodifiable {@code Map} view
	 */
	public Map<K, V> map() {
		return Collections.unmodifiableMap(this.map);
	}

	public Map<K, V> modifiableMap() {
		return this.map;
	}

	public MapBuilder<K, V> put(@Nonnull K key, V value) {
		Objects.requireNonNull(key, "key can't be null");
		if (!this.skipNullValues) {
			Objects.requireNonNull(value, "skipNullValues is false, therefore null values can't be added.");
		}
		this.map.put(key, value);
		return this;
	}

	public MapBuilder<K, V> skipNullValues(boolean skipNullValues) {
		this.skipNullValues  = skipNullValues;
		return this;
	}

	private MapBuilder<K, V> setMap(Map<K, V> theMap) {
		this.map = theMap;
		return this;
	}

}
