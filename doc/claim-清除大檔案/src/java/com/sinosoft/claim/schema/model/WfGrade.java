package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * POJO类WfGrade
 */
@Entity
@Table(name = "WFGRADE")
public class WfGrade implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private WfGradeId id;

	/** 属性模版号 */
	private int modelNo;

	/** 属性当前节点号 */
	private int nodeNo;

	/** 属性操作人代码 */
	private String operatorCode;

	/** 属性操作人名称 */
	private String operatorName;

	/** 属性操作时间 */
	private Date operatorTime;

	/** 属性业务类型 */
	private String businessType;

	/** 属性业务号码 */
	private String businessNo;

	/** 属性业务级别代码 */
	private String gradeCode;

	/** 属性业务级别分值 */
	private Double gradeValue;

	/** 属性最大可用费用率 */
	private Double maxUsableRate;

	/** 属性经纪人佣金率 */
	private Double brokerRate;

	/** 属性代理手续费用率 */
	private Double agentRate;

	/** 属性营销组织利益率 */
	private Double orgRate;

	/** 属性基准销售费用率 */
	private Double breakevenRate;

	/** 属性EXTRATE1 */
	private Double extRate1;

	/** 属性EXTRATE2 */
	private Double extRate2;

	/** 属性EXTRATE3 */
	private Double extRate3;

	/**
	 * 类WfGrade的默认构造方法
	 */
	public WfGrade() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "flowId", column = @Column(name = "FLOWID")), @AttributeOverride(name = "logNo", column = @Column(name = "LOGNO")), @AttributeOverride(name = "gradeMode", column = @Column(name = "GRADEMODE")) })
	public WfGradeId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(WfGradeId id) {
		this.id = id;
	}

	/**
	 * 属性模版号的getter方法
	 */

	@Column(name = "MODELNO")
	public int getModelNo() {
		return this.modelNo;
	}

	/**
	 * 属性模版号的setter方法
	 */
	public void setModelNo(int modelNo) {
		this.modelNo = modelNo;
	}

	/**
	 * 属性当前节点号的getter方法
	 */

	@Column(name = "NODENO")
	public int getNodeNo() {
		return this.nodeNo;
	}

	/**
	 * 属性当前节点号的setter方法
	 */
	public void setNodeNo(int nodeNo) {
		this.nodeNo = nodeNo;
	}

	/**
	 * 属性操作人代码的getter方法
	 */

	@Column(name = "OPERATORCODE")
	public String getOperatorCode() {
		return this.operatorCode;
	}

	/**
	 * 属性操作人代码的setter方法
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * 属性操作人名称的getter方法
	 */

	@Column(name = "OPERATORNAME")
	public String getOperatorName() {
		return this.operatorName;
	}

	/**
	 * 属性操作人名称的setter方法
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	/**
	 * 属性操作时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "OPERATORTIME")
	public Date getOperatorTime() {
		return this.operatorTime;
	}

	/**
	 * 属性操作时间的setter方法
	 */
	public void setOperatorTime(Date operatorTime) {
		this.operatorTime = operatorTime;
	}

	/**
	 * 属性业务类型的getter方法
	 */

	@Column(name = "BUSINESSTYPE")
	public String getBusinessType() {
		return this.businessType;
	}

	/**
	 * 属性业务类型的setter方法
	 */
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	/**
	 * 属性业务号码的getter方法
	 */

	@Column(name = "BUSINESSNO")
	public String getBusinessNo() {
		return this.businessNo;
	}

	/**
	 * 属性业务号码的setter方法
	 */
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	/**
	 * 属性业务级别代码的getter方法
	 */

	@Column(name = "GRADECODE")
	public String getGradeCode() {
		return this.gradeCode;
	}

	/**
	 * 属性业务级别代码的setter方法
	 */
	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}

	/**
	 * 属性业务级别分值的getter方法
	 */

	@Column(name = "GRADEVALUE")
	public Double getGradeValue() {
		return this.gradeValue;
	}

	/**
	 * 属性业务级别分值的setter方法
	 */
	public void setGradeValue(Double gradeValue) {
		this.gradeValue = gradeValue;
	}

	/**
	 * 属性最大可用费用率的getter方法
	 */

	@Column(name = "MAXUSABLERATE")
	public Double getMaxUsableRate() {
		return this.maxUsableRate;
	}

	/**
	 * 属性最大可用费用率的setter方法
	 */
	public void setMaxUsableRate(Double maxUsableRate) {
		this.maxUsableRate = maxUsableRate;
	}

	/**
	 * 属性经纪人佣金率的getter方法
	 */

	@Column(name = "BROKERRATE")
	public Double getBrokerRate() {
		return this.brokerRate;
	}

	/**
	 * 属性经纪人佣金率的setter方法
	 */
	public void setBrokerRate(Double brokerRate) {
		this.brokerRate = brokerRate;
	}

	/**
	 * 属性代理手续费用率的getter方法
	 */

	@Column(name = "AGENTRATE")
	public Double getAgentRate() {
		return this.agentRate;
	}

	/**
	 * 属性代理手续费用率的setter方法
	 */
	public void setAgentRate(Double agentRate) {
		this.agentRate = agentRate;
	}

	/**
	 * 属性营销组织利益率的getter方法
	 */

	@Column(name = "ORGRATE")
	public Double getOrgRate() {
		return this.orgRate;
	}

	/**
	 * 属性营销组织利益率的setter方法
	 */
	public void setOrgRate(Double orgRate) {
		this.orgRate = orgRate;
	}

	/**
	 * 属性基准销售费用率的getter方法
	 */

	@Column(name = "BREAKEVENRATE")
	public Double getBreakevenRate() {
		return this.breakevenRate;
	}

	/**
	 * 属性基准销售费用率的setter方法
	 */
	public void setBreakevenRate(Double breakevenRate) {
		this.breakevenRate = breakevenRate;
	}

	/**
	 * 属性EXTRATE1的getter方法
	 */

	@Column(name = "EXTRATE1")
	public Double getExtRate1() {
		return this.extRate1;
	}

	/**
	 * 属性EXTRATE1的setter方法
	 */
	public void setExtRate1(Double extRate1) {
		this.extRate1 = extRate1;
	}

	/**
	 * 属性EXTRATE2的getter方法
	 */

	@Column(name = "EXTRATE2")
	public Double getExtRate2() {
		return this.extRate2;
	}

	/**
	 * 属性EXTRATE2的setter方法
	 */
	public void setExtRate2(Double extRate2) {
		this.extRate2 = extRate2;
	}

	/**
	 * 属性EXTRATE3的getter方法
	 */

	@Column(name = "EXTRATE3")
	public Double getExtRate3() {
		return this.extRate3;
	}

	/**
	 * 属性EXTRATE3的setter方法
	 */
	public void setExtRate3(Double extRate3) {
		this.extRate3 = extRate3;
	}

}
