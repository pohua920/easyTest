package com.tlg.prpins.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.tlg.iBatis.IBatisBaseEntity;

@SuppressWarnings("serial")
public class Uwblacklist extends IBatisBaseEntity<BigDecimal>{
	private BigDecimal oid;
	private String blacklisttype;
	private String blacklistcode;
	private String blacklistlevel;
	private String insuredtype;
	private String insuredcode;
	private String insuredname;
	private String identifytype;
	private String identifynumber;
	private String cheatmeans;
	private Date cheatdate;
	private String makecom;
	private String operatorcode;
	private Date inputdate;
	private String phonenumber;
	private String remark;
	private String flag;
	private String insuredename;
	private String email;
	private String mobile;
	private String addressename;
	private String addresscname;
	private String sex;
	private String linkaddress;
	private String postcode;
	private String bankcode1;
	private String bankcode2;
	private String bank;
	private String account;
	private String insuredidvnote;
	private String riskcode;
	
	public String getBlacklisttype() {
		return blacklisttype;
	}
	public void setBlacklisttype(String blacklisttype) {
		this.blacklisttype = blacklisttype;
	}
	public String getBlacklistcode() {
		return blacklistcode;
	}
	public void setBlacklistcode(String blacklistcode) {
		this.blacklistcode = blacklistcode;
	}
	public String getBlacklistlevel() {
		return blacklistlevel;
	}
	public void setBlacklistlevel(String blacklistlevel) {
		this.blacklistlevel = blacklistlevel;
	}
	public String getInsuredtype() {
		return insuredtype;
	}
	public void setInsuredtype(String insuredtype) {
		this.insuredtype = insuredtype;
	}
	public String getInsuredcode() {
		return insuredcode;
	}
	public void setInsuredcode(String insuredcode) {
		this.insuredcode = insuredcode;
	}
	public String getInsuredname() {
		return insuredname;
	}
	public void setInsuredname(String insuredname) {
		this.insuredname = insuredname;
	}
	public String getIdentifytype() {
		return identifytype;
	}
	public void setIdentifytype(String identifytype) {
		this.identifytype = identifytype;
	}
	public String getIdentifynumber() {
		return identifynumber;
	}
	public void setIdentifynumber(String identifynumber) {
		this.identifynumber = identifynumber;
	}
	public String getCheatmeans() {
		return cheatmeans;
	}
	public void setCheatmeans(String cheatmeans) {
		this.cheatmeans = cheatmeans;
	}
	public Date getCheatdate() {
		return cheatdate;
	}
	public void setCheatdate(Date cheatdate) {
		this.cheatdate = cheatdate;
	}
	public String getMakecom() {
		return makecom;
	}
	public void setMakecom(String makecom) {
		this.makecom = makecom;
	}
	public String getOperatorcode() {
		return operatorcode;
	}
	public void setOperatorcode(String operatorcode) {
		this.operatorcode = operatorcode;
	}
	public Date getInputdate() {
		return inputdate;
	}
	public void setInputdate(Date inputdate) {
		this.inputdate = inputdate;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getInsuredename() {
		return insuredename;
	}
	public void setInsuredename(String insuredename) {
		this.insuredename = insuredename;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAddressename() {
		return addressename;
	}
	public void setAddressename(String addressename) {
		this.addressename = addressename;
	}
	public String getAddresscname() {
		return addresscname;
	}
	public void setAddresscname(String addresscname) {
		this.addresscname = addresscname;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getLinkaddress() {
		return linkaddress;
	}
	public void setLinkaddress(String linkaddress) {
		this.linkaddress = linkaddress;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getBankcode1() {
		return bankcode1;
	}
	public void setBankcode1(String bankcode1) {
		this.bankcode1 = bankcode1;
	}
	public String getBankcode2() {
		return bankcode2;
	}
	public void setBankcode2(String bankcode2) {
		this.bankcode2 = bankcode2;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getInsuredidvnote() {
		return insuredidvnote;
	}
	public void setInsuredidvnote(String insuredidvnote) {
		this.insuredidvnote = insuredidvnote;
	}
	public String getRiskcode() {
		return riskcode;
	}
	public void setRiskcode(String riskcode) {
		this.riskcode = riskcode;
	}
	public BigDecimal getOid() {
		return oid;
	}
	public void setOid(BigDecimal oid) {
		this.oid = oid;
	}
}