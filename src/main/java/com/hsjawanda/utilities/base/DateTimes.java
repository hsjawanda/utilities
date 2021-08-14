/**
 *
 */
package com.hsjawanda.utilities.base;

import static com.hsjawanda.utilities.repackaged.commons.lang3.StringUtils.isBlank;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.Date;

import javax.annotation.CheckForNull;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Harshdeep S Jawanda <hsjawanda@gmail.com>
 *
 */
public final class DateTimes {

	private static Logger LOG;

	private DateTimes() {
	}

	@CheckForNull
	public static Date from(@Nullable String offsetDateTime) throws DateTimeParseException {
		if (isBlank(offsetDateTime))
			return null;
		return Date.from(OffsetDateTime.parse(offsetDateTime).toInstant());
	}

	@SuppressWarnings("unused")
	private static Logger log() {
		if (null == LOG) {
			LOG = LoggerFactory.getLogger(DateTimes.class.getName());
		}
		return LOG;
	}

}
