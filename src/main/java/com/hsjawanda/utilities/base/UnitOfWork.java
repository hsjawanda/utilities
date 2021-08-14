package com.hsjawanda.utilities.base;

import java.util.concurrent.Callable;

/**
 * Essentially the same as a {@link Callable} (a {@code Callable} by another name).
 *
 * @author Harshdeep S Jawanda <hsjawanda@gmail.com>
 *
 * @see Callable
 *
 * @param <T>
 */
public interface UnitOfWork<T> {

	T execute() throws Exception;

}