package com.tlg.aps.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DIRecord")
@XmlAccessorType(XmlAccessType.FIELD)
public class FirEqDoubleInsResultDtlVo {
	@XmlElement(name = "HCompany")
	private String hCompany;
	@XmlElement(name = "Branch")
	private String branch;
	@XmlElement(name = "PolicyNo")
	private String policyNo;
	@XmlElement(name = "AddressSeq")
	private String addressSeq;
	@XmlElement(name = "BeginDate")
	private String beginDate;
	@XmlElement(name = "EndDate")
	private String endDate;
	@XmlElement(name = "SendDate")
	private String sendDate;
	@XmlElement(name = "SignDate")
	private String signDate;
	@XmlElement(name = "Insured")
	private String insured;
	@XmlElement(name = "IDNo")
	private String iDNo;
	@XmlElement(name = "Bank")
	private String bank;
	@XmlElement(name = "Bank2")
	private String bank2;
	@XmlElement(name = "Bank3")
	private String bank3;
	@XmlElement(name = "Addr")
	private String addr;

	public String gethCompany() {
		return hCompany;
	}

	public void sethCompany(String hCompany) {
		this.hCompany = hCompany;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getAddressSeq() {
		return addressSeq;
	}

	public void setAddressSeq(String addressSeq) {
		this.addressSeq = addressSeq;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public String getSignDate() {
		return signDate;
	}

	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}

	public String getInsured() {
		return insured;
	}

	public void setInsured(String insured) {
		this.insured = insured;
	}

	public String getiDNo() {
		return iDNo;
	}

	public void setiDNo(String iDNo) {
		this.iDNo = iDNo;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBank2() {
		return bank2;
	}

	public void setBank2(String bank2) {
		this.bank2 = bank2;
	}

	public String getBank3() {
		return bank3;
	}

	public void setBank3(String bank3) {
		this.bank3 = bank3;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

}
