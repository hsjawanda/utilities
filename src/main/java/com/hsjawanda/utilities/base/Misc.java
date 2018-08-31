/**
 *
 */
package com.hsjawanda.utilities.base;

import java.util.logging.Logger;

/**
 * @author Harshdeep S Jawanda <hsjawanda@gmail.com>
 *
 */
public final class Misc {

	private static Logger LOG;

	private Misc() {
	}

	public static boolean alwaysFalse() {
		return false;
	}

	public static boolean alwaysTrue() {
		return true;
	}

	@SuppressWarnings("unused")
	private static Logger log() {
		if (null == LOG) {
			LOG = Logger.getLogger(Misc.class.getName());
		}
		return LOG;
	}

}
