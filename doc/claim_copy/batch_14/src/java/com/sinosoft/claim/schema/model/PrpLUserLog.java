package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。
// DP0713 手動產出該檔案

import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * DP0713 仿製來源  POJO类PrpLDocArchiveLog
 * mantis：CLM0125，處理人員：DP0713，需求單編號：新功能理賠人員資料維護
 */
@Entity
@Table(name = "PRPLUSERLOG")
public class PrpLUserLog implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLUserLogId id;

	/** 属性 工作地點*/
	private String workPlaceNm;
	/** 属性 歸屬單位*/
	private String comcode;
	/** 属性 員工車資上限金額(元)*/
	private int feeQuota;
	/** 属性 修改原因備註*/
	private String updateRec;
	/** 属性 建立人員*/
	private String creatUser;
//	/** 属性 建立時間*/
	private Date creatTime;

	/**
	 * 类PrpLUserLog的默认构造方法
	 */
	public PrpLUserLog() {
		id = new PrpLUserLogId();
	}

	public PrpLUserLog(PrpLUserLog PrpLUserLog) {
		this.id = new PrpLUserLogId();
		id.setUserCode(PrpLUserLog.getId().getUserCode());
		id.setOid(PrpLUserLog.getId().getOid());
		
		this.workPlaceNm = PrpLUserLog.workPlaceNm;
		this.comcode = PrpLUserLog.comcode;
		this.feeQuota = PrpLUserLog.feeQuota;
		this.updateRec = PrpLUserLog.updateRec;
		this.creatUser = PrpLUserLog.creatUser;
		this.creatTime = PrpLUserLog.creatTime;
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "userCode", column = @Column(name = "USERCODE")), @AttributeOverride(name = "oid", column = @Column(name = "oid")) })
	public PrpLUserLogId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLUserLogId id) {
		this.id = id;
	}

	@Column(name = "WORKPLACENM")
	public String getWorkPlaceNm() {
		return workPlaceNm;
	}

	public void setWorkPlaceNm(String workPlaceNm) {
		this.workPlaceNm = workPlaceNm;
	}

	@Column(name = "COMCODE")
	public String getComcode() {
		return comcode;
	}

	public void setComcode(String comcode) {
		this.comcode = comcode;
	}

	@Column(name = "FEEQUOTA")
	public int getFeeQuota() {
		return feeQuota;
	}

	public void setFeeQuota(int feeQuota) {
		this.feeQuota = feeQuota;
	}

	@Column(name = "UPDATEREC")
	public String getUpdateRec() {
		return updateRec;
	}

	public void setUpdateRec(String updateRec) {
		this.updateRec = updateRec;
	}

	@Column(name = "CREATUSER")
	public String getCreatUser() {
		return creatUser;
	}

	public void setCreatUser(String creatUser) {
		this.creatUser = creatUser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATTIME")
	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}
	
}
