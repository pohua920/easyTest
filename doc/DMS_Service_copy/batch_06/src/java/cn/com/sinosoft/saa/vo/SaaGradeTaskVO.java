package cn.com.sinosoft.saa.vo;

public class SaaGradeTaskVO {
	private String taskCode;

	private String taskParentCode;

	private String taskCName;

	private String value;
	private String hasPower;
	private String intranetValue;
	private String internetValue;

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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getTaskCName() {
		return taskCName;
	}

	public void setTaskCName(String taskCName) {
		this.taskCName = taskCName;
	}

	public String getIntranetValue() {
		return intranetValue;
	}

	public void setIntranetValue(String intranetValue) {
		this.intranetValue = intranetValue;
	}

	public String getInternetValue() {
		return internetValue;
	}

	public void setInternetValue(String internetValue) {
		this.internetValue = internetValue;
	}

	public String getHasPower() {
		return hasPower;
	}

	public void setHasPower(String hasPower) {
		this.hasPower = hasPower;
	}
}
