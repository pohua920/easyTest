package com.sinosoft.claim.schema.model;


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 给付类别表
 * @author 中科软
 *
 */
@Entity
@Table(name = "PrpLpaymentType")
public class PrpLpaymentType implements java.io.Serializable{
	
	/** 序号*/
	private static final long serialVersionUID = 1L;
	/** 属性id */
	private PrpLpaymentTypeId id;
	/** 给付类型1 */
	private String type1;
	/** 给付类型2 */
	private String type2;
	/**  给付说明  */
	private String content;
	/** 承保范围*/
	private String contractingScope;
	/** 残疾等级*/
	private String injuryGrade;
	/** 残疾说明 */
	private String injuryScopeDesc;
	/** 给付比例 */
	private Double paymentRate;
	/** 是否有效，1有效，0-无效 */
	private String validStatus;
	/** 属性标志 */
	private String flag;
	/** 属性标志 */
	private String remark;
	
	public PrpLpaymentType(){
		id = new PrpLpaymentTypeId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "type", column = @Column(name = "type")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpLpaymentTypeId getId() {
		return this.id;
	}

	public void setId(PrpLpaymentTypeId id) {
		this.id = id;
	}
	@Column(name="type1")
	public String getType1() {
		return type1;
	}

	public void setType1(String type1) {
		this.type1 = type1;
	}
	@Column(name="type2")
	public String getType2() {
		return type2;
	}

	public void setType2(String type2) {
		this.type2 = type2;
	}
	@Column(name="content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	@Column(name="contractingScope")
	public String getContractingScope() {
		return contractingScope;
	}

	public void setContractingScope(String contractingScope) {
		this.contractingScope = contractingScope;
	}
	@Column(name="injuryGrade")
	public String getInjuryGrade() {
		return injuryGrade;
	}

	public void setInjuryGrade(String injuryGrade) {
		this.injuryGrade = injuryGrade;
	}
	@Column(name="injuryScopeDesc")
	public String getInjuryScopeDesc() {
		return injuryScopeDesc;
	}

	public void setInjuryScopeDesc(String injuryScopeDesc) {
		this.injuryScopeDesc = injuryScopeDesc;
	}
	@Column(name="paymentRate")
	public Double getPaymentRate() {
		return paymentRate;
	}

	public void setPaymentRate(Double paymentRate) {
		this.paymentRate = paymentRate;
	}
	@Column(name="validStatus")
	public String getValidStatus() {
		return validStatus;
	}

	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}
	@Column(name="flag")
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Column(name="remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
