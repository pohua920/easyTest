package com.tlg.prpins.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.tlg.iBatis.IBatisBaseEntity;

@SuppressWarnings("serial")
public class AmlQueryObjDetail extends IBatisBaseEntity<BigDecimal>{
	/* mantis：OTH0065，處理人員：BJ085，需求單編號：OTH0065 建置AML洗錢查詢畫面 start */
	
	private BigDecimal oid;
	
	private BigDecimal oidAmlQueryObjMain;
	/**
	 * 序號
	 */
	private String serialNo;
	/**
	 * 身分證字號
	 */
	private String id;
	/**
	 * 身分別
	 * 1.自然人 2.法人
	 */
	private String insuredType;
	/**
	 * 	身分類型
	 * 1.被保人 2.要保人 9.受益人
	 */
	private String insuredFlag;
	/**
	 * 狀態
	 * 000:正常;
	 * 001:註銷;
	 */
	private String status;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * enName
	 */
	private String enName;
	/**
	 * 性別
	 * 男  M 
	 * 女  F  法人不需填
	 */
	private String gender;
	/**
	 * 生日
	 * yyyy-MM-dd 法人不需填
	 */
	private String birthday;
	/**
	 * 國籍代碼
	 */
	private String nationCode;
	/**
	 * 高風險職業 Y/N
	 */
	private String dangerOccupation;
	/**
	 * 公司成立日期，自然人不用填
	 */
	private String estDate;
	/**
	 * 上市櫃公司Y/N，自然人不用填，若沒有值就是""
	 * EXT_OPEN_FLAG 
	 */
	private String listedCabinetCompany;
	
	private Date createtime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getNationCode() {
		return nationCode;
	}

	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}

	public String getDangerOccupation() {
		return dangerOccupation;
	}

	public void setDangerOccupation(String dangerOccupation) {
		this.dangerOccupation = dangerOccupation;
	}

	public String getEstDate() {
		return estDate;
	}

	public void setEstDate(String estDate) {
		this.estDate = estDate;
	}

	public String getListedCabinetCompany() {
		return listedCabinetCompany;
	}

	public void setListedCabinetCompany(String listedCabinetCompany) {
		this.listedCabinetCompany = listedCabinetCompany;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public BigDecimal getOid() {
		return oid;
	}

	public void setOid(BigDecimal oid) {
		this.oid = oid;
	}

	public BigDecimal getOidAmlQueryObjMain() {
		return oidAmlQueryObjMain;
	}

	public void setOidAmlQueryObjMain(BigDecimal oidAmlQueryObjMain) {
		this.oidAmlQueryObjMain = oidAmlQueryObjMain;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

}
