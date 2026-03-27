package com.sinosoft.app.webservice.server.schema.model.regist.vo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種
 */
@XmlRootElement
public class RiskA{
	
	private String handleUnit;
	private String manageType;
	private String relationship;
	List<ThirdParty> thirdPartyList;
	List<PersonTrace> personTraceList;
	List<Driver> driverList;
	
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	public List<ThirdParty> getThirdPartyList() {
		return thirdPartyList;
	}
	public void setThirdPartyList(List<ThirdParty> thirdPartyList) {
		this.thirdPartyList = thirdPartyList;
	}
	public List<PersonTrace> getPersonTraceList() {
		return personTraceList;
	}
	public void setPersonTraceList(List<PersonTrace> personTraceList) {
		this.personTraceList = personTraceList;
	}
	public List<Driver> getDriverList() {
		return driverList;
	}
	public void setDriverList(List<Driver> driverList) {
		this.driverList = driverList;
	}
	public String getHandleUnit() {
		return handleUnit;
	}
	public void setHandleUnit(String handleUnit) {
		this.handleUnit = handleUnit;
	}
	public String getManageType() {
		return manageType;
	}
	public void setManageType(String manageType) {
		this.manageType = manageType;
	}
	
	
}
