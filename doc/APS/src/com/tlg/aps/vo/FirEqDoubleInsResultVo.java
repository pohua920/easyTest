package com.tlg.aps.vo;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Result")
@XmlAccessorType(XmlAccessType.FIELD)
public class FirEqDoubleInsResultVo {
	@XmlElement(name="Code")
	private String code;
	@XmlElement(name="ErrorMessage")
	private String errorMessage;
	@XmlElement(name="CountNum")
	private String countNum;
	@XmlElement(name="DIInsNo")
	private String dIInsNo;
	@XmlElement(name="QryBeginDate")
	private String qryBeginDate;
	@XmlElement(name="QryEndDate")
	private String qryEndDate;
	@XmlElement(name="QryAddr")
	private String qryAddr;
	@XmlElement(name="DINo")
	private String dINo;
	@XmlElement(name="DIRecord")
	ArrayList<FirEqDoubleInsResultDtlVo> firEqDoubleInsResultDtlVoList;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getCountNum() {
		return countNum;
	}

	public void setCountNum(String countNum) {
		this.countNum = countNum;
	}

	public String getdIInsNo() {
		return dIInsNo;
	}

	public void setdIInsNo(String dIInsNo) {
		this.dIInsNo = dIInsNo;
	}

	public String getQryBeginDate() {
		return qryBeginDate;
	}

	public void setQryBeginDate(String qryBeginDate) {
		this.qryBeginDate = qryBeginDate;
	}

	public String getQryEndDate() {
		return qryEndDate;
	}

	public void setQryEndDate(String qryEndDate) {
		this.qryEndDate = qryEndDate;
	}

	public String getQryAddr() {
		return qryAddr;
	}

	public void setQryAddr(String qryAddr) {
		this.qryAddr = qryAddr;
	}

	public String getdINo() {
		return dINo;
	}

	public void setdINo(String dINo) {
		this.dINo = dINo;
	}

	public ArrayList<FirEqDoubleInsResultDtlVo> getFirEqDoubleInsResultDtlVoList() {
		return firEqDoubleInsResultDtlVoList;
	}

	public void setFirEqDoubleInsResultDtlVoList(
			ArrayList<FirEqDoubleInsResultDtlVo> firEqDoubleInsResultDtlVoList) {
		this.firEqDoubleInsResultDtlVoList = firEqDoubleInsResultDtlVoList;
	}

}