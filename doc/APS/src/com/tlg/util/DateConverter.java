package com.tlg.util;

/**
 * @author Jason.Chiang
 *
 */
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.time.DateUtils;

public class DateConverter implements Converter {

	private boolean useDefault = true;
	private Object defaultValue = null;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

	public DateConverter() {
		this.defaultValue = null;
		this.useDefault = false;
	}

	public DateConverter(Object defaultValue) {
		this.defaultValue = defaultValue;
		this.useDefault = true;
	}

	@SuppressWarnings("unchecked")
	public Object convert(Class type, Object value) {
		if (value == null) {
			if (useDefault) {
				return (defaultValue);
			} else {
				throw new ConversionException("No value specified");
			}
		}

		if (value instanceof Date) {
			return (value);
		}

		if (value instanceof Long) {
			Long longValue = (Long) value;
			return new Date(longValue.longValue());
		}
		if (value instanceof String) {
			Date endTime = null;
			try {
				if(value != null && !"".equals(value)){
					if(value.toString().length() >= 24){
						value = value.toString().substring(0,23);
					}
					
//					endTime = sdf.parse(value.toString());
					endTime = DateUtils.parseDate(value.toString(), new String[] {
						"yyyy-MM-dd HH:mm:ss.SSS","yyyy-MM-dd HH:mm:ss:SSSS","yyyy-MM-dd HH:mm:ss:SSS","yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm" });
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return endTime;
		}

		try {
			return Timestamp.valueOf(value.toString());
		} catch (Exception e) {
			if (useDefault) {
				return (defaultValue);
			} else {
				throw new ConversionException(e);
			}
		}
	}
}
