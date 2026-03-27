package com.tlg.commons.util.api.soap.aml.entity;

import javax.xml.bind.annotation.XmlRootElement;

/*
mantis： OTH0065，處理人員：Sam，需求單編號：OTH0065--- start
新AML串接
*/

@XmlRootElement
public class AmlInsured {

	private String serialNo;//序號
	
	private String id;//身分證字號/統編
	
	private String name;//姓名/公司名稱
	
	private String enName;//英文姓名/名稱
	
	private String birthday;//生日	yyyy-MM-dd 	法人及理賠業務時可不填寫
	
	private String gender;//性別		M - 男		F - 女	法人及理賠業務時可不填寫

	private String nationCode;//國籍代碼/註冊地		例如 TW	自然人 - 輸入國籍代碼，法人 - 輸入註冊地，理賠業務時可不填寫
	
	private String insuredType;//身分別	1 - 自然人	2 - 法人

	private String insuredFlag;//身分類型	0 - 其他，1 - 被保人，2 - 要保人，3 - 銀行，4 - 船名，5 - 飛機，6 - 國家，7 - 收貨人，8 - 理賠-賠付對像，9 - 受益人

	private String estDate;//公司成立日期 	yyyy-MM-dd	自然人及理賠業務時可不填

	private String dangerOccupation;//是否為高風險職業/是否為高風險行業	Y - 是，N - 否	理賠業務時可不填

	private String listedCabinetCompany;//上市櫃公司(公開訊息)		Y - 是，N - 否	自然人及理賠業務時可不填

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNationCode() {
		return nationCode;
	}

	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}

	public String getInsuredType() {
		return insuredType;
	}

	public void setInsuredType(String insuredType) {
		this.insuredType = insuredType;
	}

	public String getInsuredFlag() {
		return insuredFlag;
	}

	public void setInsuredFlag(String insuredFlag) {
		this.insuredFlag = insuredFlag;
	}

	public String getEstDate() {
		return estDate;
	}

	public void setEstDate(String estDate) {
		this.estDate = estDate;
	}

	public String getDangerOccupation() {
		return dangerOccupation;
	}

	public void setDangerOccupation(String dangerOccupation) {
		this.dangerOccupation = dangerOccupation;
	}

	public String getListedCabinetCompany() {
		return listedCabinetCompany;
	}

	public void setListedCabinetCompany(String listedCabinetCompany) {
		this.listedCabinetCompany = listedCabinetCompany;
	}

	@Override
	public String toString() {
		return "AmlInsured [serialNo=" + serialNo + ", id=" + id + ", name="
				+ name + ", enName=" + enName + ", birthday=" + birthday
				+ ", gender=" + gender + ", nationCode=" + nationCode
				+ ", insuredType=" + insuredType + ", insuredFlag="
				+ insuredFlag + ", estDate=" + estDate + ", dangerOccupation="
				+ dangerOccupation + ", listedCabinetCompany="
				+ listedCabinetCompany + "]";
	}
	
}
