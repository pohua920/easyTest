package com.sinosoft.sys.platform.power.vo;

public class SaaAuthTaskVO {
	private String taskCode;
	private String taskParentCode;
	private String taskCName;
	private String checked;
	private String hasPower;
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getTaskParentCode() {
		return taskParentCode;
	}
	public void setTaskParentCode(String taskParentCode) {
		this.taskParentCode = taskParentCode;
	}
	public String getTaskCName() {
		return taskCName;
	}
	public void setTaskCName(String taskCName) {
		this.taskCName = taskCName;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getHasPower() {
		return hasPower;
	}
	public void setHasPower(String hasPower) {
		this.hasPower = hasPower;
	}
}
