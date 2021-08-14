/**
 *
 */
package com.hsjawanda.utilities.base;

import static com.hsjawanda.utilities.base.Check.checkState;
//import static com.google.common.base.Preconditions.checkState;
import static com.hsjawanda.utilities.repackaged.commons.lang3.StringUtils.EMPTY;
import static com.hsjawanda.utilities.repackaged.commons.lang3.StringUtils.defaultString;
import static com.hsjawanda.utilities.repackaged.commons.lang3.StringUtils.isNotBlank;
import static com.hsjawanda.utilities.repackaged.commons.lang3.StringUtils.leftPad;
import static com.hsjawanda.utilities.repackaged.commons.lang3.StringUtils.repeat;
import static com.hsjawanda.utilities.repackaged.commons.lang3.StringUtils.rightPad;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.hsjawanda.utilities.base.TimingData.DataLine;

//import com.google.common.base.Stopwatch;
//import com.google.common.collect.Lists;

//import lombok.AccessLevel;
//import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.experimental.Accessors;


/**
 * @author Harshdeep Jawanda <hsjawanda@gmail.com>
 *
 */
public class SegmentTimer {

	private static final ChronoUnit DEF_CHRONO_UNIT = ChronoUnit.MILLIS;

	private static final String NL = System.lineSeparator();

	private TimingData data = new TimingData();

	private boolean finalStop = false;

	private List<Instant> timers = new ArrayList<>();

	private ChronoUnit timeUnit;

	private String timeUnitStr;

	public SegmentTimer() {
		timeUnit(DEF_CHRONO_UNIT);
	}

	public SegmentTimer(ChronoUnit cu) {
		timeUnit(null != cu ? cu : DEF_CHRONO_UNIT);
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

	public SegmentTimer next() {
		next(null);
		return this;
	}

	public SegmentTimer next(@Nullable String segmentLabel) throws IllegalStateException {
		int size = this.timers.size();
		checkState(size >= 1, "Timer isn't running yet, you can't start next segment");
		checkState(!this.finalStop, "Timer has been stopped, you can't start next segment");
//		Stopwatch lastTimer = this.timers.get(size - 1);
//		lastTimer.stop();
		this.timers.add(Instant.now());
		this.data.add(segmentLabel, displayDuration());
		return this;
	}

	public long prevSegmentTime() {
		return this.timers.size() > 2
				? duration(this.timers.get(this.timers.size() - 2), this.timers.get(this.timers.size() - 3))
				: 0;
	}

	public SegmentTimer start() throws IllegalStateException {
		start(null);
		return this;
	}

//	public SegmentTimer pauseSegment() throws IllegalStateException {
//		int size = this.timers.size();
//		checkState(size >= 1, "Timer isn't running yet, you can't pause a segment");
//		checkState(!this.finalStop, "Timer has been stopped, you can't pause segment");
//		this.timers.get(size).stop();
//		return this;
//	}

//	public SegmentTimer restartSegment() throws IllegalStateException {
//		int size = this.timers.size();
//		checkState(size >= 1, "Timer isn't running yet, you can't restart a segment");
//		checkState(!this.finalStop, "Timer has been stopped, you can't restart a segment");
//		this.timers.get(size).start();
//		return this;
//	}

//	public SegmentTimer resetSegment() throws IllegalStateException {
//		int size = this.timers.size();
//		checkState(size >= 1, "Timer isn't running yet, you can't reset a segment");
//		checkState(!this.finalStop, "Timer has been stopped, you can't reset a segment");
//		this.timers.get(size).reset();
//		return this;
//	}

	public SegmentTimer start(@Nullable String mesg) {
		checkState(this.timers.isEmpty(), "The timer is already running, it can't be re-started");
		checkState(!this.finalStop, "Timer has been stopped, it can't be started again.");
		this.timers.add(Instant.now());
		this.data.add("Started timer" + (isNotBlank(mesg) ? ": " + mesg : EMPTY), displayDuration());
		return this;
	}

	public String stop() {
		return stop(null);
	}

	public String stop(String stopMessage) {
		this.timers.add(Instant.now());
		checkState(this.timers.size() >= 2, "Timer isn't running yet, you can't stop it.");
		this.finalStop = true;
		this.data.add(stopMessage, displayDuration());
		return report();
	}

	private String displayDuration() {
		if (this.timers.size() < 2)
			return EMPTY;
		return time(duration(this.timers.get(this.timers.size() - 1), this.timers.get(this.timers.size() - 2)));
	}

	private long duration(Instant end, Instant start) {
		Duration dur = Duration.between(end, start).abs();
		long retVal;
		switch (this.timeUnit) {
		case MILLIS:
			retVal = dur.toMillis();
			break;
		case NANOS:
		case SECONDS:
			retVal = dur.get(this.timeUnit);
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

	private String report() {
		List<DataLine> lines = this.data.lines();
		int longestText = 0, longestTime = 0;
		for (DataLine line : lines) {
			longestText = Math.max(longestText, line.text != null ? line.text.length() : 0);
			longestTime = Math.max(longestTime, line.elapsed != null ? line.elapsed.length() : 0);
		}
		int count = 1;
		StringBuilder report = new StringBuilder(200);
		for (DataLine line : lines) {
			report.append(NL).append(leftPad(Integer.toString(count), 3)).append(". ")
					.append(rightPad(defaultString(line.text), longestText + 2))
					.append(leftPad(line.elapsed, longestTime));
			count++;
		}
		long totalTime = duration(this.timers.get(this.timers.size() -1 ), this.timers.get(0));
		return report.append(NL).append(repeat('-', 5 + longestText + 3 + longestTime)).append(NL)
				.append(leftPad("Total time: ", 5 + longestText + 1)).append(leftPad(time(totalTime), longestTime + 1))
				.toString();
	}

	private String time(long timeValue) {
		return Long.toString(timeValue) + this.timeUnitStr;
	}

	private SegmentTimer timeUnit(ChronoUnit tu) {
		if (null != tu) {
			this.timeUnit = tu;
			timeUnitStr(tu);
		}
		return this;
	}

	private SegmentTimer timeUnitStr(ChronoUnit tu) {
		this.timeUnitStr = null != tu ? display(tu) : EMPTY;
		return this;
	}

}
