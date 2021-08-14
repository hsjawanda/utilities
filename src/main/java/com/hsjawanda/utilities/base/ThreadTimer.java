/**
 *
 */
package com.hsjawanda.utilities.base;

import static com.hsjawanda.utilities.base.Check.checkArgument;
import static com.hsjawanda.utilities.base.Check.checkState;
import static com.hsjawanda.utilities.repackaged.commons.lang3.StringUtils.EMPTY;
import static com.hsjawanda.utilities.repackaged.commons.lang3.StringUtils.defaultString;
import static java.time.temporal.ChronoUnit.MILLIS;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.NANOS;
import static java.time.temporal.ChronoUnit.SECONDS;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.logging.Logger;

import com.hsjawanda.utilities.base.PrintableTable.Justification;
import com.hsjawanda.utilities.base.TimingData.DataLine;
import com.hsjawanda.utilities.collections.Lists;

/**
 * @author Harshdeep S Jawanda <hsjawanda@gmail.com>
 *
 */
public final class ThreadTimer {

	private static final ThreadLocal<TimingData> DATA = new ThreadLocal<TimingData>() {
		@Override
		protected TimingData initialValue() {
			return new TimingData();
		}
	};

	private static final ThreadLocal<Boolean> DEBUG = new ThreadLocal<Boolean>() {
		@Override
		protected Boolean initialValue() {
			return Boolean.FALSE;
		}
	};

	private static Logger LOG;

	private static final ThreadLocal<State> STATE = new ThreadLocal<State>() {
		@Override
		protected State initialValue() {
			return State.NOT_STARTED;
		}
	};

	private static final ThreadLocal<List<Instant>> TIMES = new ThreadLocal<List<Instant>>() {
		@Override
		protected List<Instant> initialValue() {
			return Lists.newArrayListOf(Instant.now());
		}
	};

	private static final ThreadLocal<ChronoUnit> UNIT = new ThreadLocal<ChronoUnit>() {
		@Override
		protected ChronoUnit initialValue() {
			return MILLIS;
		}
	};

	private static final ThreadLocal<String> UNIT_STR = new ThreadLocal<String>() {
		@Override
		protected String initialValue() {
			return display(UNIT.get());
		}
	};

	private ThreadTimer() {
	}

	public static String display(ChronoUnit unit) {
		switch (unit) {
		case NANOS:
			return " ns";
		case MICROS:
			return " \u03bcs"; // μs
		case MILLIS:
			return " ms";
		case SECONDS:
			return " s";
		case MINUTES:
			return " min";
		case HOURS:
			return " h";
		case DAYS:
			return " d";
		default:
			return EMPTY;
		}
	}

	public static void mark(String mesg) throws IllegalStateException {
		checkState(STATE.get() == State.RUNNING, "The timer is currently in State %s & therefore time can't be marked.",
				STATE.get());
		markTime(mesg);
	}

	public static void mark(String format, Object... args) {
		mark(String.format(format, args));
	}

	public static int numTimers() {
		return TIMES.get().size();
	}

	public static void setDebug(boolean debug) {
		DEBUG.set(Boolean.valueOf(debug));
	}

	public static void setUnit(ChronoUnit unit) {
		checkArgument(unit == NANOS || unit == MILLIS || unit == SECONDS || unit == MINUTES,
				"Only units %s, %s, %s and %s are supported.", NANOS, MILLIS, SECONDS, MINUTES);
		UNIT.set(unit);
		UNIT_STR.set(display(unit));
	}

	public static void start() throws IllegalStateException {
		start(null, false);
	}

	public static void start(String mesg) throws IllegalStateException {
		start(mesg, false);
	}

	public static void start(String mesg, boolean forced) throws IllegalStateException {
		if (!forced) {
			checkState(STATE.get() == State.NOT_STARTED,
					"The timer is currently in State %s & therefore can't be started.", STATE.get());
		}
		STATE.set(State.RUNNING);
		TIMES.get().clear();
		DATA.get().clear();
		markTime(mesg);
	}

	public static void start(String format, Object... args) {
		start(String.format(format, args), false);
	}

	public static String stop(String mesg) throws IllegalStateException {
		checkState(STATE.get() == State.RUNNING, "The timer is currently in State %s & therefore can't be stopped.",
				STATE.get());
		STATE.set(State.ENDED);
		markTime(mesg);
		return report();
	}

	public static void tryMark(String mesg) {
		if (STATE.get() == State.RUNNING) {
			mark(mesg);
		} else {
			log().info(ThreadTimer.class.getSimpleName() + " is not currently running, so didn't mark any time.");
		}
	}

	public static void tryMark(String format, Object... args) {
		tryMark(String.format(format, args));
	}

	private static String displayDuration() {
		if (TIMES.get().size() < 2)
			return EMPTY;
		return time(duration(TIMES.get().get(TIMES.get().size() - 1), TIMES.get().get(TIMES.get().size() - 2)));
	}

	private static long duration(Instant end, Instant start) {
		Duration dur = Duration.between(start, end).abs();
		long retVal;
		switch (UNIT.get()) {
		case MILLIS:
			retVal = dur.toMillis();
			break;
		case NANOS:
		case SECONDS:
			retVal = dur.get(UNIT.get());
			break;
		case MICROS:
			retVal = dur.toNanos() / 1000;
			break;
		case MINUTES:
			retVal = dur.toMinutes();
			break;
		default:
			retVal = dur.toMillis();
			break;
		}
		return retVal;
	}

	private static Logger log() {
		if (null == LOG) {
			LOG = Logger.getLogger(ThreadTimer.class.getName());
		}
		return LOG;
	}

	private static void markTime(String mesg) throws IllegalStateException {
		TIMES.get().add(Instant.now());
		DATA.get().add(defaultString(mesg), displayDuration());
	}

	private static String report() {
		PrintableTable rpt = PrintableTable.builder().hasHeaderRow(true).separator(" | ").useSerialNum(true).build();
		rpt.add("Message", Justification.CENTRE).add("Time", Justification.CENTRE);
		for (DataLine line : DATA.get().lines()) {
			rpt.startRow().add(line.text).add(line.elapsed, Justification.RIGHT);
		}
		long totalTime = duration(TIMES.get().get(TIMES.get().size() -1 ), TIMES.get().get(0));
		rpt.startRow().add("Total time:", Justification.RIGHT).add(time(totalTime), Justification.RIGHT);
		return rpt.print("Request timing:");
	}

	private static String time(long timeValue) {
		return Long.toString(timeValue) + UNIT_STR.get();
	}

	public enum State {
		ENDED, NOT_STARTED, RUNNING;
	}

}
