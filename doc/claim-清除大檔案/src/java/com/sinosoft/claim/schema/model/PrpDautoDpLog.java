package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。
import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


/**
 * mantis：CLM0265，處理人員：DP0713，需求單編號：新核心-DP自動化功能
 * POJO类PrpDautoDpLog
 */
@Entity
@Table(name = "PRPDAUTODPLOG")
public class PrpDautoDpLog implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	/** 軌跡ID **/
	private PrpDautoDpLogId id;
	/** 模組 **/
	private String module;//PRPINS/UNDWRT/CLAIM
	/** 險種代碼 **/
	private String riskCode;
	/** 修改功能名稱 **/
	private String functionName;
	/** 保單號碼 **/
	private String policyNo;
	/** 要保單號 **/
	private String proposalNo;
	/** 批單號 **/
	private String endorseNo;
	/** 業務號 **/
	private String businessNo;
	/** 報案號碼 **/
	private String registNo;
	/** 立案號碼 **/
	private String claimNo;
	/** 計算書號 **/
	private String compensateNo;
	/** 序號 **/
	private String serialNo;
	/** 其他類型 **/
	private String certiType;
	/** 修改的欄位(放入資料庫欄位名) **/
	private String columnCName;
	/** 欄位數值(修改前) **/
	private String beforeValue;
	/** 欄位數值(修改後) **/
	private String value;
	/** 輸入日期 **/
	private Date inputDate;
	/** 操作人員（當前系統使用者） **/
	private String inputUser;
	/** 操作人員最高核保等級 **/
	private String inputNodeNo;
	/** 操作人員IP **/
	private String inputIp;
	/** 覆審輸入日期 **/
	private Date reviewInputDate;
	/** 覆審操作人員 **/
	private String reviewInputUser;
	/** 覆審操作人員IP **/
	private String reviewInputIp;
	/** 狀態(-1:退回 /1:待審核/2:審核通過)  **/
	private String inputStatus;
	/** SQL描述 **/
	private String sqlExpress;
	/** 備註 **/
	private String remark;

	/**
	 * 类PrpDautoDpLog的默认构造方法
	 */
	public PrpDautoDpLog() {
	}
	
	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "logId", column = @Column(name = "LOGID")), @AttributeOverride(name = "columnName", column = @Column(name = "COLUMNNAME")) })
	public PrpDautoDpLogId getId() {
		return this.id;
	}

	public void setId(PrpDautoDpLogId id) {
		this.id = id;
	}

	@Column(name = "MODULE")
	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	@Column(name = "FUNCTIONNAME")
	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	@Column(name = "POLICYNO")
	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	@Column(name = "PROPOSALNO")
	public String getProposalNo() {
		return proposalNo;
	}

	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	@Column(name = "ENDORSENO")
	public String getEndorseNo() {
		return endorseNo;
	}

	public void setEndorseNo(String endorseNo) {
		this.endorseNo = endorseNo;
	}

	@Column(name = "BUSINESSNO")
	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	@Column(name = "REGISTNO")
	public String getRegistNo() {
		return registNo;
	}

	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}

	@Column(name = "CLAIMNO")
	public String getClaimNo() {
		return claimNo;
	}

	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	@Column(name = "SERIALNO")
	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	@Column(name = "CERTITYPE")
	public String getCertiType() {
		return certiType;
	}

	public void setCertiType(String certiType) {
		this.certiType = certiType;
	}

	@Column(name = "COLUMNCNAME")
	public String getColumnCName() {
		return columnCName;
	}

	public void setColumnCName(String columnCName) {
		this.columnCName = columnCName;
	}

	@Column(name = "BEFOREVALUE")
	public String getBeforeValue() {
		return beforeValue;
	}

	public void setBeforeValue(String beforeValue) {
		this.beforeValue = beforeValue;
	}

	@Column(name = "VALUE")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INPUTDATE")
	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	@Column(name = "INPUTUSER")
	public String getInputUser() {
		return inputUser;
	}

	public void setInputUser(String inputUser) {
		this.inputUser = inputUser;
	}

	@Column(name = "INPUTNODENO")
	public String getInputNodeNo() {
		return inputNodeNo;
	}

	public void setInputNodeNo(String inputNodeNo) {
		this.inputNodeNo = inputNodeNo;
	}

	@Column(name = "INPUTIP")
	public String getInputIp() {
		return inputIp;
	}

	public void setInputIp(String inputIp) {
		this.inputIp = inputIp;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REVIEWINPUTDATE")
	public Date getReviewInputDate() {
		return reviewInputDate;
	}

	public void setReviewInputDate(Date reviewInputDate) {
		this.reviewInputDate = reviewInputDate;
	}

	@Column(name = "REVIEWINPUTUSER")
	public String getReviewInputUser() {
		return reviewInputUser;
	}

	public void setReviewInputUser(String reviewInputUser) {
		this.reviewInputUser = reviewInputUser;
	}

	@Column(name = "REVIEWINPUTIP")
	public String getReviewInputIp() {
		return reviewInputIp;
	}

	public void setReviewInputIp(String reviewInputIp) {
		this.reviewInputIp = reviewInputIp;
	}

	@Column(name = "INPUTSTATUS")
	public String getInputStatus() {
		return inputStatus;
	}

	public void setInputStatus(String inputStatus) {
		this.inputStatus = inputStatus;
	}


	@Column(name = "SQLEXPRESS")
	public String getSqlExpress() {
		return sqlExpress;
	}

	public void setSqlExpress(String sqlExpress) {
		this.sqlExpress = sqlExpress;
	}
	


	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "COMPENSATENO")
	public String getCompensateNo() {
		return compensateNo;
	}

	public void setCompensateNo(String compensateNo) {
		this.compensateNo = compensateNo;
	}

	

}
