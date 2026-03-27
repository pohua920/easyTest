package cn.com.sinosoft.ims.user.vo;


public class UtiIUserVO{
	private String comCode;
	private String comCName;
	private String upperComCode;
	//是否包含此机构
	private String checked;
	//该机构是否包含所有下属机构
	private String incluSubChecked;
	//是否拥有该机构的授权范围
	private String hasPower;
	private String hasSubPower;
	
	
	
	public String getComCode() {
		return comCode;
	}
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}
	public String getComCName() {
		return comCName;
	}
	public void setComCName(String comCName) {
		this.comCName = comCName;
	}
	public String getUpperComCode() {
		return this.upperComCode;
	}

	public void setUpperComCode(String upperComCode) {
		this.upperComCode = upperComCode;
	}
	
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getIncluSubChecked() {
		return incluSubChecked;
	}
	public void setIncluSubChecked(String incluSubChecked) {
		this.incluSubChecked = incluSubChecked;
	}
	
	public String getHasPower() {
		return hasPower;
	}
	public void setHasPower(String hasPower) {
		this.hasPower = hasPower;
	}
	public String getHasSubPower() {
		return hasSubPower;
	}
	public void setHasSubPower(String hasSubPower) {
		this.hasSubPower = hasSubPower;
	}

}
