package com.tlg.aps.vo;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FirAddressCheckVo {

	private String zip;
	private String address;
	private String structure;
	private String floors;

	private String queryResult;
	private String errMsg;
	private String compareResult;
	private String buildyears; //mantis：FIR0613，處理人員：DP0706，需求單編號：FIR0613_住火_火險地址檢核_呼叫WS程式新增傳入年份參數

	private ArrayList<FirAddressCompareVo> firAddressCompareVoList;

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStructure() {
		return structure;
	}

	public void setStructure(String structure) {
		this.structure = structure;
	}

	public String getFloors() {
		return floors;
	}

	public void setFloors(String floors) {
		this.floors = floors;
	}

	public String getQueryResult() {
		return queryResult;
	}

	public void setQueryResult(String queryResult) {
		this.queryResult = queryResult;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getCompareResult() {
		return compareResult;
	}

	public void setCompareResult(String compareResult) {
		this.compareResult = compareResult;
	}

	public ArrayList<FirAddressCompareVo> getFirAddressCompareVoList() {
		return firAddressCompareVoList;
	}

	public void setFirAddressCompareVoList(
			ArrayList<FirAddressCompareVo> firAddressCompareVoList) {
		this.firAddressCompareVoList = firAddressCompareVoList;
	}

	//mantis：FIR0613，處理人員：DP0706，需求單編號：FIR0613_住火_火險地址檢核_呼叫WS程式新增傳入年份參數START
	public String getBuildyears() {
		return buildyears;
	}

	public void setBuildyears(String buildyears) {
		this.buildyears = buildyears;
	}
	//mantis：FIR0613，處理人員：DP0706，需求單編號：FIR0613_住火_火險地址檢核_呼叫WS程式新增傳入年份參數END
}
