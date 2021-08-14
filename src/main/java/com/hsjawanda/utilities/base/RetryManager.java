/**
 *
 */
package com.hsjawanda.utilities.base;

import java.util.logging.Logger;

import com.hsjawanda.utilities.collections.Collections;

/**
 * Manage retries on any {@link UnitOfWork} according to the parameters set while building {@code RetryManager}. Any
 * {@code Exception} thrown by the {@code UnitOfWork} is interpreted as a failure, and the {@code UnitOfWork} is
 * retried.
 *
 * @author Harshdeep S Jawanda <hsjawanda@gmail.com>
 *
 */
public class RetryManager<T> {

	private static Logger LOG;

	public static <T> T process(RetryOptions options, UnitOfWork<T> computation) throws RuntimeException {
		long waitMillis = options.initialWaitMillis;
		int i;
		T retVal = null;
		Exception thrownException = null;
		for (i = 0; i < options.maxTries; i++) {
			try {
				retVal = computation.execute();
				break;
			} catch (Exception e) {
				if (options.debug) {
					log().warning(String.format("Error in attempt #%2d: %s", (i + 1), e.getMessage()));
				}
				if (Collections.isEmpty(options.exceptionsToRetry) || (Collections.isNotEmpty(options.exceptionsToRetry)
						&& options.exceptionsToRetry.contains(e))) {
					try {
						Thread.sleep(waitMillis);
					} catch (InterruptedException e1) {
						// Do nothing;
					} finally {
						if (waitMillis < 900_000) { // max wait time: 30 mins (1,800,000 milliseconds)
							waitMillis = waitMillis << 1;
						}
					}
				} else {
					thrownException = e;
					break;
				}
			}
		}
		if (i > 1 && options.debug) {
			log().info("It took " + i + " tries for computation to succeed.");
		}
		if (null != thrownException)
			throw new RuntimeException("There was an exception executing the UnitOfWork", thrownException);
		return retVal;
	}

	private static Logger log() {
		if (null == LOG) {
			LOG = Logger.getLogger(RetryManager.class.getName());
		}
		return LOG;
	}

}
