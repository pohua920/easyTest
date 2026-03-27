package cn.com.sinosoft.saa.vo;

public class SaaUserPowerVO {
	private String userCode;
	private String gradeId;
	private String permitComCodes;
	private String exceptComCodes;
	private String permitProductCodes;
	public String getPermitComCodes() {
		return permitComCodes;
	}
	public void setPermitComCodes(String permitComCodes) {
		this.permitComCodes = permitComCodes;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getGradeId() {
		return gradeId;
	}
	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}
	public String getPermitProductCodes() {
		return permitProductCodes;
	}
	public void setPermitProductCodes(String permitProductCodes) {
		this.permitProductCodes = permitProductCodes;
	}
	public String getExceptComCodes() {
		return exceptComCodes;
	}
	public void setExceptComCodes(String exceptComCodes) {
		this.exceptComCodes = exceptComCodes;
	}
}
