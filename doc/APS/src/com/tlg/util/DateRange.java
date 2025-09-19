package com.tlg.util;

import java.text.MessageFormat;
import java.util.Date;

/**
 * 表示一個時間區間
 * 
 * @author morel
 * 
 */
public class DateRange {

	private Date start;
	private Date end;

	/**
	 * construct with start date and end date which is used to represent a range
	 * of time span
	 * <p>
	 * start date can not be after end date
	 * </P>
	 * 
	 * @param start
	 * @param end
	 */
	public DateRange(Date start, Date end) {
		super();
		ValidationUtils.throwIllegalArgumentIfNull("start", start);
		ValidationUtils.throwIllegalArgumentIfNull("end", end);
		if (start.after(end)) {
			throw new IllegalArgumentException(MessageFormat.format(
					"start date:{0} can not be after end date:{1}", start, end));
		}
		this.start = start;
		this.end = end;
	}

	/**
	 * get the start date of the range
	 * 
	 * @return
	 */
	public Date getStart() {
		return start;
	}

	/**
	 * get the end date of the range
	 * 
	 * @return
	 */
	public Date getEnd() {
		return end;
	}

	/**
	 * get the range time in millisecond
	 * 
	 * @return
	 */
	public long getSpan() {
		return end.getTime() - start.getTime();
	}

	/**
	 * test if the specified date is between the date range
	 * <p>
	 * it's true if the date is equals to start of end date of the range.
	 * </p>
	 * 
	 * @param date
	 * @return
	 */
	public boolean contains(Date date) {
		ValidationUtils.throwIllegalArgumentIfNull("date", date);
		if ((start.equals(date) || start.before(date))
				&& (end.equals(date) || end.after(date))) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final DateRange other = (DateRange) obj;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return MessageFormat.format("DateRange[start:{0}, end:{1}, span:{2}({3}-{4})]",
				start, end, getSpan(), start.getTime(), end.getTime());
	}
	
}
