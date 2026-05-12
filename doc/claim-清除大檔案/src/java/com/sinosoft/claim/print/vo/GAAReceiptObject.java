/**
 * 2014-6-10
 */
package com.sinosoft.claim.print.vo;

/**
 * 工程险  賠款同意書暨領款收據  数据对象 
 * @author 中科軟
 */
public class GAAReceiptObject {
	private String claimNo;
	private String policyNo;
	private String insuredName;
	/**承保地址*/
	private String address;
	private String appliIdentifyNumber;
	private String linkPhone;
	public String getClaimNo() {
		return claimNo;
	}
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
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
	public String getLinkPhone() {
		return linkPhone;
	}
	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}
}
