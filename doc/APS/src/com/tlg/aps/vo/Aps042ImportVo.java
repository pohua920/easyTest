package com.tlg.aps.vo;

import java.util.Date;

/* mantis：FIR0565，處理人員：CC009，需求單編號：FIR0565 住火_複保險通知轉檔作業 */
public class Aps042ImportVo {
	private String isEndor;
	private String applyName;
	private String insuredName;
	private String insuredId;
	private String applyPostcode;
	private String applyAddress;
	private String addressCode;
	private String addressdetailinfo;
	private String mortgageepeople;
	private String loansdepartment;
	private String contactNumber;
	private Date startdate;
	private Date enddate;
	
	public String getIsEndor() {
		return isEndor;
	}
	public void setIsEndor(String isEndor) {
		this.isEndor = isEndor;
	}
	public String getApplyName() {
		return applyName;
	}
	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}
	public String getInsuredName() {
		return insuredName;
	}
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}
	public String getInsuredId() {
		return insuredId;
	}
	public void setInsuredId(String insuredId) {
		this.insuredId = insuredId;
	}
	public String getApplyPostcode() {
		return applyPostcode;
	}
	public void setApplyPostcode(String applyPostcode) {
		this.applyPostcode = applyPostcode;
	}
	public String getApplyAddress() {
		return applyAddress;
	}
	public void setApplyAddress(String applyAddress) {
		this.applyAddress = applyAddress;
	}
	public String getAddressCode() {
		return addressCode;
	}
	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
	}
	public String getAddressdetailinfo() {
		return addressdetailinfo;
	}
	public void setAddressdetailinfo(String addressdetailinfo) {
		this.addressdetailinfo = addressdetailinfo;
	}
	public String getMortgageepeople() {
		return mortgageepeople;
	}
	public void setMortgageepeople(String mortgageepeople) {
		this.mortgageepeople = mortgageepeople;
	}
	public String getLoansdepartment() {
		return loansdepartment;
	}
	public void setLoansdepartment(String loansdepartment) {
		this.loansdepartment = loansdepartment;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
}
