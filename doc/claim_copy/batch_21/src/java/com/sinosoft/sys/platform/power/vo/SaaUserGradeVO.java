package com.sinosoft.sys.platform.power.vo;

import java.util.Date;

public class SaaUserGradeVO {
	private String gradeCode;

	private String gradeName;
	private Date endDate;

	private boolean checked;

	private String systemTypeName;

	public String getGradeCode() {
		return gradeCode;
	}

	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getSystemTypeName() {
		return systemTypeName;
	}

	public void setSystemTypeName(String systemTypeName) {
		this.systemTypeName = systemTypeName;
	}

}
