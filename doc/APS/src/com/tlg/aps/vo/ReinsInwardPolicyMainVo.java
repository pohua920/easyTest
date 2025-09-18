package com.tlg.aps.vo;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ReinsInwardPolicyMainVo {

	private String isExist;
	
	private BigDecimal oid;

	private String policyNo;

	private String endorseNo;

	private String classcode;

	private String cmnpNo;

	private String broker;

	private String inwardNo;

	private String startDate;

	private String endorseStartDate;

	private String endDate;

	private String endorseEndDate;

	private String applicantName;

	private String applicantId;

	private String insuredName;

	private String insuredId;

	private String objInfo;

	private ArrayList<ReinsInwardPolicyInsVo> insList;

	public BigDecimal getOid() {
		return oid;
	}

	public void setOid(BigDecimal oid) {
		this.oid = oid;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getEndorseNo() {
		return endorseNo;
	}

	public void setEndorseNo(String endorseNo) {
		this.endorseNo = endorseNo;
	}

	public String getClasscode() {
		return classcode;
	}

	public void setClasscode(String classcode) {
		this.classcode = classcode;
	}

	public String getCmnpNo() {
		return cmnpNo;
	}

	public void setCmnpNo(String cmnpNo) {
		this.cmnpNo = cmnpNo;
	}

	public String getBroker() {
		return broker;
	}

	public void setBroker(String broker) {
		this.broker = broker;
	}

	public String getInwardNo() {
		return inwardNo;
	}

	public void setInwardNo(String inwardNo) {
		this.inwardNo = inwardNo;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndorseStartDate() {
		return endorseStartDate;
	}

	public void setEndorseStartDate(String endorseStartDate) {
		this.endorseStartDate = endorseStartDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getEndorseEndDate() {
		return endorseEndDate;
	}

	public void setEndorseEndDate(String endorseEndDate) {
		this.endorseEndDate = endorseEndDate;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getApplicantId() {
		return applicantId;
	}

	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
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

	public String getObjInfo() {
		return objInfo;
	}

	public void setObjInfo(String objInfo) {
		this.objInfo = objInfo;
	}

	public ArrayList<ReinsInwardPolicyInsVo> getInsList() {
		return insList;
	}

	public void setInsList(ArrayList<ReinsInwardPolicyInsVo> insList) {
		this.insList = insList;
	}

	public String getIsExist() {
		return isExist;
	}

	public void setIsExist(String isExist) {
		this.isExist = isExist;
	}
}
