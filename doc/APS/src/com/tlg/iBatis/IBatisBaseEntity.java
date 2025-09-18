package com.tlg.iBatis;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tlg.util.DateUtils;

public class IBatisBaseEntity<K extends Serializable> implements Serializable{
	
	private static final long serialVersionUID = -6676722649078183207L;

	public Date convertRocDate(String dateString) {
		if(dateString != null && !dateString.isEmpty()) {
			return DateUtils.convertRocDateTime(dateString);
		}
		return null;
	}
	
	public String convertRocDate(Date date) {
		if(date != null) {
			return DateUtils.convertDateTimeTwo(date, "/").toString();
		}
		return null;
	}
	@JsonIgnore
	private boolean mark;
	
	@XmlTransient
	public boolean isMark() {
		return mark;
	}

	public void setMark(boolean mark) {
		this.mark = mark;
	}

	
	
	
}
