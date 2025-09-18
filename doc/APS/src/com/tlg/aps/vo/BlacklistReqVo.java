package com.tlg.aps.vo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name ="blacklistRequestVo" )
public class BlacklistReqVo {
	
	private String iinstype;//險別(CR：汽車險，MT：機車險，TV：旅平險，BD：住火險)
	private String identifyNumber;//身份證字號
	private String licensePlateNumber;//車牌號碼
	private String engineNumber;//引擎號碼
	private String modelCode;//廠牌代碼
	private String carKindCode;//車種代碼
	private String businessNature;//業源代碼
	private String lpnAndId;//車牌號碼,身份證字號
	public String getIinstype() {
		return iinstype;
	}
	public void setIinstype(String iinstype) {
		this.iinstype = iinstype;
	}
	public String getIdentifyNumber() {
		return identifyNumber;
	}
	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = identifyNumber;
	}
	public String getLicensePlateNumber() {
		return licensePlateNumber;
	}
	public void setLicensePlateNumber(String licensePlateNumber) {
		this.licensePlateNumber = licensePlateNumber;
	}
	public String getEngineNumber() {
		return engineNumber;
	}
	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
	}
	public String getModelCode() {
		return modelCode;
	}
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}
	public String getCarKindCode() {
		return carKindCode;
	}
	public void setCarKindCode(String carKindCode) {
		this.carKindCode = carKindCode;
	}
	public String getBusinessNature() {
		return businessNature;
	}
	public void setBusinessNature(String businessNature) {
		this.businessNature = businessNature;
	}
	public String getLpnAndId() {
		return lpnAndId;
	}
	public void setLpnAndId(String lpnAndId) {
		this.lpnAndId = lpnAndId;
	}

		

}
