package com.sinosoft.claim.schema.model;

import javax.persistence.Column;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
//mantis：CLM0125，處理人員：DP0713，需求單編號：新功能理賠人員資料維護  
import javax.persistence.Temporal;
//mantis：CLM0125，處理人員：DP0713，需求單編號：新功能理賠人員資料維護 
import javax.persistence.TemporalType;

/**
 * mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核
 */
@Entity
@Table(name = "PRPLUSER")
public class PrpLuser implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
private String userCode;
	
	private String userName;
	
	private String id;
	
	private String comcode;
	
	private String workPlaceNm;
	
	private String userFlag;
	
	private String createUser;
	
	private Date createTime;
	
	private String updateUser;
	
	private Date updateTime;
	
	//mantis：CLM0150，處理人員：DP0706，需求單編號：.新核心-車資費用人員階級管控
	private Double feeQuota;
	
	//mantis：CLM0178，處理人員：DP0713，需求單編號：新核心-功能理賠人員資料維護新增區塊鏈驗證欄位 START
	private String email;
	private String tel;
	private String ext;
	//mantis：CLM0178，處理人員：DP0713，需求單編號：新核心-功能理賠人員資料維護新增區塊鏈驗證欄位 END
		
	public PrpLuser() {
		super();
	}
	
	//mantis：CLM0125，處理人員：DP0713，需求單編號：新功能理賠人員資料維護 
	//@Id
	@Column(name = "ID")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	//mantis：CLM0125，處理人員：DP0713，需求單編號：新功能理賠人員資料維護 
	@Id
	@Column(name = "USERCODE")
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	@Column(name = "USERNAME")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(name = "COMCODE")
	public String getComcode() {
		return comcode;
	}

	public void setComcode(String comcode) {
		this.comcode = comcode;
	}

	@Column(name = "WORKPLACENM")
	public String getWorkPlaceNm() {
		return workPlaceNm;
	}

	public void setWorkPlaceNm(String workPlaceNm) {
		this.workPlaceNm = workPlaceNm;
	}

	@Column(name = "USERFLAG")
	public String getUserFlag() {
		return userFlag;
	}

	public void setUserFlag(String userFlag) {
		this.userFlag = userFlag;
	}

	@Column(name = "CREATUSER")
	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	//mantis：CLM0125，處理人員：DP0713，需求單編號：新功能理賠人員資料維護 
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATTIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATEUSER")
	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	//mantis：CLM0125，處理人員：DP0713，需求單編號：新功能理賠人員資料維護 
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATETIME")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	//mantis：CLM0150，處理人員：DP0706，需求單編號：.新核心-車資費用人員階級管控START
	@Column(name = "FEEQUOTA")
	public Double getFeeQuota() {
		return feeQuota;
	}

	public void setFeeQuota(Double feeQuota) {
		this.feeQuota = feeQuota;
	}
	//mantis：CLM0150，處理人員：DP0706，需求單編號：.新核心-車資費用人員階級管控END

	//mantis：CLM0178，處理人員：DP0713，需求單編號：新核心-功能理賠人員資料維護新增區塊鏈驗證欄位 START
	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "TEL")
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "EXT")
	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}
	//mantis：CLM0178，處理人員：DP0713，需求單編號：新核心-功能理賠人員資料維護新增區塊鏈驗證欄位 END
	
}
