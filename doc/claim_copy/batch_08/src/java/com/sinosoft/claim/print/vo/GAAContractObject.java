/**
 * 2014-6-13
 */
package com.sinosoft.claim.print.vo;

/**
 * 工程险  債權讓與契約暨通知書  数据对象 
 * @author 中科軟
 */
public class GAAContractObject{
	private String className;
	private String policyNo;
	private String insuredName;
	/**承保地址*/
	private String address;
	private String appliIdentifyNumber;
	/**出險日期*/
	private String damageStartDate;
	 
	public String getDamageStartDate() {
		return damageStartDate;
	}
	public void setDamageStartDate(String damageStartDate) {
		this.damageStartDate = damageStartDate;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public String getInsuredName() {
		return insuredName;
	}
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAppliIdentifyNumber() {
		return appliIdentifyNumber;
	}
	public void setAppliIdentifyNumber(String appliIdentifyNumber) {
		this.appliIdentifyNumber = appliIdentifyNumber;
	}
	 

}
