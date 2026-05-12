/**
 * 2014-6-10
 */
package com.sinosoft.claim.print.vo;

/**
 * 责任险 賠款同意書暨領款收據 数据对象
 * @author 中科軟
 */
public class LiabReceiptObject {
	/** 賠案號碼 */
	private String claimNo;
	/** 保單號碼 */
	private String policyNo;
	/** 被保险人 */
	private String insuredName;
	/** 地址 */
	private String address;
	/** 身份证号码 */
	private String identifyNumber;
	/** 电话 */
	private String phone;

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

	public String getIdentifyNumber() {
		return identifyNumber;
	}

	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = identifyNumber;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
