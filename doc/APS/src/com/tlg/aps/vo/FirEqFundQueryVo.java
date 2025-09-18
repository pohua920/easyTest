package com.tlg.aps.vo;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 查詢地震基金傳入物件
 * SOURCE_TYPE '查詢來源類型 ';
 * SOURCE_CUSTNO '查詢來源客戶代號';
 * SOURCE_USERID '查詢來源使用者';
 * START_DATE '保單生效日';
 * END_DATE '保單到期日';
 * POSTCODE '郵遞區號';
 * ADDRESS '地址';
 * REPEAT_POLICYNO '複保險查詢序號';
 * REPEAT_CODE '複保險查詢回傳值';
 * REPEAT_MSG '複保險查詢回傳訊息';
 * @author bi086
 * 
 */
@XmlRootElement
public class FirEqFundQueryVo {

	private String sourceType;
	private String sourceCustno;
	private String sourceUserid;
	private String startDate;
	private String endDate;
	private String postcode;
	private String address;
	private String repeatPolicyno;
	private String repeatCode;
	private String repeatMsg;

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getSourceCustno() {
		return sourceCustno;
	}

	public void setSourceCustno(String sourceCustno) {
		this.sourceCustno = sourceCustno;
	}

	public String getSourceUserid() {
		return sourceUserid;
	}

	public void setSourceUserid(String sourceUserid) {
		this.sourceUserid = sourceUserid;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRepeatPolicyno() {
		return repeatPolicyno;
	}

	public void setRepeatPolicyno(String repeatPolicyno) {
		this.repeatPolicyno = repeatPolicyno;
	}

	public String getRepeatCode() {
		return repeatCode;
	}

	public void setRepeatCode(String repeatCode) {
		this.repeatCode = repeatCode;
	}

	public String getRepeatMsg() {
		return repeatMsg;
	}

	public void setRepeatMsg(String repeatMsg) {
		this.repeatMsg = repeatMsg;
	}
}
