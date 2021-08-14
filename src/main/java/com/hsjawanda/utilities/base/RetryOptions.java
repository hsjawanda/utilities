package com.hsjawanda.utilities.base;

import static com.hsjawanda.utilities.base.Check.checkArgument;

import java.util.Collections;
import java.util.List;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

public class RetryOptions {

	public static final int MAX_DOUBLINGS_ALLOWED = 20;

	public static final int MIN_DOUBLINGS_ALLOWED = 1;

	private static ThreadLocal<Integer> NUM_DOUBLINGS = new ThreadLocal<Integer>() {
		@Override
		protected Integer initialValue() {
			return Integer.valueOf(0);
		}
	};

	private static ThreadLocal<Long> WAIT_MILLIS = new ThreadLocal<Long>() {
		@Override
		protected Long initialValue() {
			return null;
		}
	};

	public final boolean debug;

	/**
	 * <p>
	 * The list of {@link Exception}s that should be retried.
	 *
	 * <p>
	 * If the list has at least one element, Exceptions not mentioned in this list will not be retried. If the list is
	 * empty all exceptions will be retried.
	 *
	 * <p>
	 * The list is unmodifiable. Any attempts to modify this list will throw an {@link UnsupportedOperationException}.
	 */
	public final List<Exception> exceptionsToRetry;

	public final long initialWaitMillis;

	public final int maxDoublings;

	public final int maxTries;

	@Generated("SparkTools")
	private RetryOptions(Builder builder) {
		this.exceptionsToRetry = builder.exceptionsToRetry;
		this.debug = builder.debug;
		this.initialWaitMillis = builder.initialWaitMillis;
		this.maxTries = builder.maxTries;
		this.maxDoublings = builder.maxDoublings;
	}

	/**
	 * Creates builder to build {@link RetryOptions}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Creates a builder to build {@link RetryOptions} and initialize it with the given object.
	 * @param retryOptions to initialize the builder with
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builderFrom(RetryOptions retryOptions) {
		return new Builder(retryOptions);
	}

	public void backoff() {
		if (null == WAIT_MILLIS.get()) {
			WAIT_MILLIS.set(this.initialWaitMillis);
		}
		try {
			Thread.sleep(WAIT_MILLIS.get().longValue());
		} catch (InterruptedException e) {
			// Do nothing
		}
		if (NUM_DOUBLINGS.get().intValue() < this.maxDoublings) {
			WAIT_MILLIS.set(Long.valueOf(WAIT_MILLIS.get().longValue() << 1));
			NUM_DOUBLINGS.set(Integer.valueOf(NUM_DOUBLINGS.get().intValue() + 1));
		}
	}

	public void reset() {
		WAIT_MILLIS.set(null);
		NUM_DOUBLINGS.set(Integer.valueOf(0));
	}

	/**
	 * Builder to build {@link RetryOptions}.
	 */
	@Generated("SparkTools")
	public static final class Builder {

		private boolean debug;

		private List<Exception> exceptionsToRetry = Collections.emptyList();

		private long initialWaitMillis = 100;

		private int maxDoublings = 5;

		private int maxTries = 3;

		private Builder() {
		}

		private Builder(RetryOptions retryOptions) {
			this.exceptionsToRetry = retryOptions.exceptionsToRetry;
			this.debug = retryOptions.debug;
			this.initialWaitMillis = retryOptions.initialWaitMillis;
			this.maxTries = retryOptions.maxTries;
			this.maxDoublings = retryOptions.maxDoublings;
		}

		@Nonnull
		public RetryOptions build() {
			return new RetryOptions(this);
		}

		@Nonnull
		public Builder debug(boolean debug) {
			this.debug = debug;
			return this;
		}

		@Nonnull
		public Builder exceptionsToRetry(List<Exception> exceptionsToRetry) {
			if (null != exceptionsToRetry) {
				this.exceptionsToRetry = Collections.unmodifiableList(exceptionsToRetry);
			}
			return this;
		}

		@Nonnull
		public Builder initialWaitMillis(long initialWaitMillis) {
			this.initialWaitMillis = initialWaitMillis;
			return this;
		}

		public Builder maxDoublings(int maxDoublings) throws IllegalArgumentException {
			checkArgument(maxDoublings >= MIN_DOUBLINGS_ALLOWED, "maxDoublings can't be less than %d.",
					MIN_DOUBLINGS_ALLOWED);
			checkArgument(maxDoublings <= MAX_DOUBLINGS_ALLOWED, "maxDoublings can't be more than %d.",
					MAX_DOUBLINGS_ALLOWED);
			this.maxDoublings = maxDoublings;
			return this;
		}

		@Nonnull
		public Builder maxTries(int maxTries) {
			this.maxTries = maxTries;
			return this;
		}
	}

}