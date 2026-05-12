package com.sinosoft.claim.schema.model;

import java.util.ArrayList;
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


@Entity
@Table(name = "PrpLcarInsurance")
public class PrpLcarInsurance implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	 
	/** 属性id */
	private PrpLcarInsuranceId id;
	/** 立案号码 */
	private String claimNo;
	/** 险别 */
	private String classCode;
	/** 险种 */
	private String riskCode;
	/** 保单号码 */
	private String policyNo;
	/** 输入日期 */
	private Date inputDate;
	/** 車體險估價單有無當事人簽署 */
	private String writtenEstimate;
	/** 自負額發票號 */
	private String deductibleInvoice;
	/** 開立者統編 */
	/* private String uniformNo; #083 第三次修改 需求变更 刪除開立者統編 */
	/** 單一車輛自行碰撞事故統計代碼 */
	private String collisionCount;
	/** 修理廠營利事業統一編號或負責人身份證字號 */
	private String repairUniformNo;
	/** 发票签收日期 */
	private Date invoiceDate;
	/** 理賠經辦人員代碼   */
	private String handlerCode;
	/** 理賠經辦人員代碼  */
	private String handlerName;
	/** 备注 */
	private String remark;
	/** 标志位 */
	private String flag;
	/** 憑證類型  0:非發票, 1:發票 */ /* #083 第三次修改 需求变更 增加憑證類型 */
	private String certificateType;

	private List<PrpLcarInsurance> prpLcarInsuranceList = new ArrayList<PrpLcarInsurance>();

	/**
	 * 类PrpLpayObjectInfo的默认构造方法
	 */
	public PrpLcarInsurance() {
		id = new PrpLcarInsuranceId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "compensateNo", column = @Column(name = "COMPENSATENO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpLcarInsuranceId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLcarInsuranceId id) {
		this.id = id;
	}

	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	@Column(name = "claimNo")
	public String getClaimNo() {
		return claimNo;
	}

	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}
	@Column(name = "classCode")
	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	@Column(name = "policyNo")
	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "inputDate")
	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
	@Column(name = "writtenEstimate")
	public String getWrittenEstimate() {
		return writtenEstimate;
	}

	public void setWrittenEstimate(String writtenEstimate) {
		this.writtenEstimate = writtenEstimate;
	}
	@Column(name = "deductibleInvoice")
	public String getDeductibleInvoice() {
		return deductibleInvoice;
	}

	public void setDeductibleInvoice(String deductibleInvoice) {
		this.deductibleInvoice = deductibleInvoice;
	}
	/** #083 第三次修改 需求变更 刪除開立者統編 
	@Column(name = "uniformNo")
	public String getUniformNo() {
		return uniformNo;
	}

	public void setUniformNo(String uniformNo) {
		this.uniformNo = uniformNo;
	}
	**/
	@Column(name = "collisionCount")
	public String getCollisionCount() {
		return collisionCount;
	}

	public void setCollisionCount(String collisionCount) {
		this.collisionCount = collisionCount;
	}
	@Column(name = "repairUniformNo")
	public String getRepairUniformNo() {
		return repairUniformNo;
	}

	public void setRepairUniformNo(String repairUniformNo) {
		this.repairUniformNo = repairUniformNo;
	}
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFlag() {
		return flag;
	}
	@Column(name = "flag")
	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "invoiceDate")
	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	@Column(name = "handlerCode")
	public String getHandlerCode() {
		return handlerCode;
	}

	public void setHandlerCode(String handlerCode) {
		this.handlerCode = handlerCode;
	}
	@Column(name = "handlerName")
	public String getHandlerName() {
		return handlerName;
	}

	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}

	@Column(name = "CERTIFICATETYPE")
	public String getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	@Transient
	public List<PrpLcarInsurance> getPrpLcarInsuranceList() {
		return prpLcarInsuranceList;
	}

	public void setPrpLcarInsuranceList(List<PrpLcarInsurance> prpLcarInsuranceList) {
		this.prpLcarInsuranceList = prpLcarInsuranceList;
	}
	
}
