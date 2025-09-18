package com.tlg.util;

import java.text.MessageFormat;
import java.util.Collection;

public class ValidationUtils {

	/**
	 * throws {@link IllegalArgumentException} if the value is null
	 *
	 * @param paramName
	 *            the parameter name of the value
	 * @param value
	 *            the value to validate
	 */
	public static void throwIllegalArgumentIfNull(String paramName, Object value) {
		if (value == null) {
			throw new IllegalArgumentException(MessageFormat.format(
					"parameter {0} can not be null", paramName));
		}
	}

	/**
	 * throws {@link IllegalArgumentException} if the collection value is empty
	 * (or null)
	 *
	 * @param paramName
	 *            the parameter name of the value
	 * @param value
	 *            the value to validate
	 */
	public static void throwIllegalArgumentIfEmpty(String paramName,
			Collection<?> value) {
		throwIllegalArgumentIfNull(paramName, value);
		if (value.isEmpty()) {
			throw new IllegalArgumentException(MessageFormat.format(
					"parameter {0} can not be empty", paramName));
		}
	}

	/**
	 * throw {@link IllegalArgumentException} if specified condition evaluated
	 * as TRUE
	 * 
	 * @param condition
	 * @param message
	 *            the exception message
	 * @param params
	 *            the exception message parameters
	 */
	public static void throwIllegalArgumentIfTrue(boolean condition,
			String message, Object... params) {
		if (condition) {
			throw new IllegalArgumentException(MessageFormat.format(message,
					params));
		}
	}

	/**
	 * throws {@link IllegalStateException} if the value is null
	 * 
	 * @param paramName
	 *            the parameter name of the value
	 * @param value
	 *            the value to validate
	 */
	public static void throwIllegalStateIfNull(String paramName, Object value) {
		if (value == null) {
			throw new IllegalStateException(MessageFormat.format(
					"parameter {0} can not be null", paramName));
		}
	}
}
