package cn.com.sinosoft.dms.vo;

// ���ù��� Hibernate Tools 3.2.4.GA (sinosoft version) ��ɣ������ֹ��޸ġ�

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import cn.com.sinosoft.dms.model.PrpDriskClauseKindId;

/**
 * POJO��PrpDriskClauseKind
 */
@Entity
@Table(name = "prpdriskclausekind")
public class PrpDriskClauseKind implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** ������̶����ʶ */
	private PrpDriskClauseKindId id;

	/** ������ϼ���������� */
	private String riskCName;

	/** �������������� */
	private String clauseKindID;

	/** �������δ��� */
	private String kindCode;

	/** ���Ծ����δ��� */
	private String oldKindCode;

	/** ����������� */
	private String kindName;

	/** ����������ַ������ */
	private String clauseClassCode;

	/** �����������ַ������ */
	private String kindClassCode;

	/** ���Ա�Ĵ��� */
	private String itemCode;

	/** �������α�־����Ҫ���Ρ��������Σ� */
	private String kindAttribute;

	/** ���Է��ʱ�������(0���ѡ�1����) */
	private String type;

	/** ���Է��ʵ�λ(1-100,0-1000) */
	private String rateUnit;

	/** �������޼���� */
	private String lowerOperator;

	/** �������޼���� */
	private String upperOperator;

	/** ���Աұ� */
	private String currency;

	/** �������� */
	private BigDecimal upper;

	/** �������� */
	private BigDecimal lower;

	/** ����ֵ */
	private BigDecimal value;

	/** ����claculatefag */
	private String claculateFag;

	/** ������Ч���� */
	private Date validDate;

	/** ����ʧЧ���� */
	private Date invalidDate;

	/** ������Ч��־ */
	private String validInd;

	/** ����Ԥ���ֶ�1 */
	private String tcol1;

	/** ����Ԥ���ֶ�2 */
	private String tcol2;

	/** ����Ԥ���ֶ�3 */
	private String tcol3;

	/** ���Ա�ע */
	private String remark;

	/** ���Ա�־�ֶ� */
	private String flag;

	/** ���Լ����־λ */
	private String calculateFlag;

	/** ����claimtype */
	private String claimType;

	/** ����endupdatercode */
	private String endUpdaterCode;

	/** ����operatetimeforhis */
	private Date operateTimeForHis;
	
	//modify begin mod by guyanqing 2011-07-14
	private String kindLevel;
	
	private String upperKindCode;
	
	private String upperKindName;
	
	private BigDecimal kindRatio;
	
	//modify end mod by guyanqing 2011-07-14
	
	//modify begin mod by zhupeng 20110712 reason:增加补贴等字段
	/** 是否补贴类型 */
	private String allowance;
	/** 日补贴金额 */
	private String allowanceAmount;
	/** 日补贴天数 */
	private String allowanceDays;
	/** 日补贴总金额 */
	private String allowanceSumAmount;
	//modify end mod by zhupeng 20110712 reason:增加补贴等字段
	//add by zhangjiabao 需求112:新增屬性
	private String policyCategory;//保單類別
	private String policyClassification;//保單分類
	private String insuranceClassification;//險種分類
	private String commodityRiskGrade;//商品風險等級
	/**
	 * ��PrpDriskClauseKind��Ĭ�Ϲ��췽��
	 */
	public PrpDriskClauseKind() {
	}

	/**       
	 * ������̶����ʶ��getter����
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "riskCode", column = @Column(name = "riskcode")),
			@AttributeOverride(name = "riskKCSerialNo", column = @Column(name = "riskkcserialno")),
			@AttributeOverride(name = "clauseCode", column = @Column(name = "clausecode")) })
	public PrpDriskClauseKindId getId() {
		return this.id;
	}

	/**       
	 * ������̶����ʶ��setter����
	 */
	public void setId(PrpDriskClauseKindId id) {
		this.id = id;
	}

	/**       
	 * ������ϼ���������Ƶ�getter����
	 */

	@Column(name = "riskcname")
	public String getRiskCName() {
		return this.riskCName;
	}

	/**       
	 * ������ϼ���������Ƶ�setter����
	 */
	public void setRiskCName(String riskCName) {
		this.riskCName = riskCName;
	}

	/**       
	 * �������������ŵ�getter����
	 */

	@Column(name = "clausekindid")
	public String getClauseKindID() {
		return this.clauseKindID;
	}

	/**       
	 * �������������ŵ�setter����
	 */
	public void setClauseKindID(String clauseKindID) {
		this.clauseKindID = clauseKindID;
	}

	/**       
	 * �������δ����getter����
	 */

	@Column(name = "kindcode")
	public String getKindCode() {
		return this.kindCode;
	}

	/**       
	 * �������δ����setter����
	 */
	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}

	/**       
	 * ���Ծ����δ����getter����
	 */

	@Column(name = "oldkindcode")
	public String getOldKindCode() {
		return this.oldKindCode;
	}

	/**       
	 * ���Ծ����δ����setter����
	 */
	public void setOldKindCode(String oldKindCode) {
		this.oldKindCode = oldKindCode;
	}

	/**       
	 * ����������Ƶ�getter����
	 */

	@Column(name = "kindname")
	public String getKindName() {
		return this.kindName;
	}

	/**       
	 * ����������Ƶ�setter����
	 */
	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	/**       
	 * ����������ַ�������getter����
	 */

	@Column(name = "clauseclasscode")
	public String getClauseClassCode() {
		return this.clauseClassCode;
	}

	/**       
	 * ����������ַ�������setter����
	 */
	public void setClauseClassCode(String clauseClassCode) {
		this.clauseClassCode = clauseClassCode;
	}

	/**       
	 * �����������ַ�������getter����
	 */

	@Column(name = "kindclasscode")
	public String getKindClassCode() {
		return this.kindClassCode;
	}

	/**       
	 * �����������ַ�������setter����
	 */
	public void setKindClassCode(String kindClassCode) {
		this.kindClassCode = kindClassCode;
	}

	/**       
	 * ���Ա�Ĵ����getter����
	 */

	@Column(name = "itemcode")
	public String getItemCode() {
		return this.itemCode;
	}

	/**       
	 * ���Ա�Ĵ����setter����
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**       
	 * �������α�־����Ҫ���Ρ��������Σ���getter����
	 */

	@Column(name = "kindattribute")
	public String getKindAttribute() {
		return this.kindAttribute;
	}

	/**       
	 * �������α�־����Ҫ���Ρ��������Σ���setter����
	 */
	public void setKindAttribute(String kindAttribute) {
		this.kindAttribute = kindAttribute;
	}

	/**       
	 * ���Է��ʱ�������(0���ѡ�1����)��getter����
	 */

	@Column(name = "type")
	public String getType() {
		return this.type;
	}

	/**       
	 * ���Է��ʱ�������(0���ѡ�1����)��setter����
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**       
	 * ���Է��ʵ�λ(1-100,0-1000)��getter����
	 */

	@Column(name = "rateunit")
	public String getRateUnit() {
		return this.rateUnit;
	}

	/**       
	 * ���Է��ʵ�λ(1-100,0-1000)��setter����
	 */
	public void setRateUnit(String rateUnit) {
		this.rateUnit = rateUnit;
	}

	/**       
	 * �������޼�����getter����
	 */

	@Column(name = "loweroperator")
	public String getLowerOperator() {
		return this.lowerOperator;
	}

	/**       
	 * �������޼�����setter����
	 */
	public void setLowerOperator(String lowerOperator) {
		this.lowerOperator = lowerOperator;
	}

	/**       
	 * �������޼�����getter����
	 */

	@Column(name = "upperoperator")
	public String getUpperOperator() {
		return this.upperOperator;
	}

	/**       
	 * �������޼�����setter����
	 */
	public void setUpperOperator(String upperOperator) {
		this.upperOperator = upperOperator;
	}

	/**       
	 * ���Աұ��getter����
	 */

	@Column(name = "currency")
	public String getCurrency() {
		return this.currency;
	}

	/**       
	 * ���Աұ��setter����
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**       
	 * �������޵�getter����
	 */

	@Column(name = "upper")
	public BigDecimal getUpper() {
		return this.upper;
	}

	/**       
	 * �������޵�setter����
	 */
	public void setUpper(BigDecimal upper) {
		this.upper = upper;
	}

	/**       
	 * �������޵�getter����
	 */

	@Column(name = "lower")
	public BigDecimal getLower() {
		return this.lower;
	}

	/**       
	 * �������޵�setter����
	 */
	public void setLower(BigDecimal lower) {
		this.lower = lower;
	}

	/**       
	 * ����ֵ��getter����
	 */

	@Column(name = "value")
	public BigDecimal getValue() {
		return this.value;
	}

	/**       
	 * ����ֵ��setter����
	 */
	public void setValue(BigDecimal value) {
		this.value = value;
	}

	/**       
	 * ����claculatefag��getter����
	 */

	@Column(name = "claculatefag")
	public String getClaculateFag() {
		return this.claculateFag;
	}

	/**       
	 * ����claculatefag��setter����
	 */
	public void setClaculateFag(String claculateFag) {
		this.claculateFag = claculateFag;
	}

	/**       
	 * ������Ч���ڵ�getter����
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "validdate")
	public Date getValidDate() {
		return this.validDate;
	}

	/**       
	 * ������Ч���ڵ�setter����
	 */
	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	/**       
	 * ����ʧЧ���ڵ�getter����
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "invaliddate")
	public Date getInvalidDate() {
		return this.invalidDate;
	}

	/**       
	 * ����ʧЧ���ڵ�setter����
	 */
	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}

	/**       
	 * ������Ч��־��getter����
	 */

	@Column(name = "validind")
	public String getValidInd() {
		return this.validInd;
	}

	/**       
	 * ������Ч��־��setter����
	 */
	public void setValidInd(String validInd) {
		this.validInd = validInd;
	}

	/**       
	 * ����Ԥ���ֶ�1��getter����
	 */

	@Column(name = "tcol1")
	public String getTcol1() {
		return this.tcol1;
	}

	/**       
	 * ����Ԥ���ֶ�1��setter����
	 */
	public void setTcol1(String tcol1) {
		this.tcol1 = tcol1;
	}

	/**       
	 * ����Ԥ���ֶ�2��getter����
	 */

	@Column(name = "tcol2")
	public String getTcol2() {
		return this.tcol2;
	}

	/**       
	 * ����Ԥ���ֶ�2��setter����
	 */
	public void setTcol2(String tcol2) {
		this.tcol2 = tcol2;
	}

	/**       
	 * ����Ԥ���ֶ�3��getter����
	 */

	@Column(name = "tcol3")
	public String getTcol3() {
		return this.tcol3;
	}

	/**       
	 * ����Ԥ���ֶ�3��setter����
	 */
	public void setTcol3(String tcol3) {
		this.tcol3 = tcol3;
	}

	/**       
	 * ���Ա�ע��getter����
	 */

	@Column(name = "remark")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "org.springframework.orm.hibernate3.support.ClobStringType")
	public String getRemark() {
		return this.remark;
	}

	/**       
	 * ���Ա�ע��setter����
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**       
	 * ���Ա�־�ֶε�getter����
	 */

	@Column(name = "flag")
	public String getFlag() {
		return this.flag;
	}

	/**       
	 * ���Ա�־�ֶε�setter����
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**       
	 * ���Լ����־λ��getter����
	 */

	@Column(name = "calculateflag")
	public String getCalculateFlag() {
		return this.calculateFlag;
	}

	/**       
	 * ���Լ����־λ��setter����
	 */
	public void setCalculateFlag(String calculateFlag) {
		this.calculateFlag = calculateFlag;
	}

	/**       
	 * ����claimtype��getter����
	 */

	@Column(name = "claimtype")
	public String getClaimType() {
		return this.claimType;
	}

	/**       
	 * ����claimtype��setter����
	 */
	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}

	/**       
	 * ����endupdatercode��getter����
	 */

	@Column(name = "endupdatercode")
	public String getEndUpdaterCode() {
		return this.endUpdaterCode;
	}

	/**       
	 * ����endupdatercode��setter����
	 */
	public void setEndUpdaterCode(String endUpdaterCode) {
		this.endUpdaterCode = endUpdaterCode;
	}

	/**       
	 * ����operatetimeforhis��getter����
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "operatetimeforhis")
	public Date getOperateTimeForHis() {
		return this.operateTimeForHis;
	}

	/**       
	 * ����operatetimeforhis��setter����
	 */
	public void setOperateTimeForHis(Date operateTimeForHis) {
		this.operateTimeForHis = operateTimeForHis;
	}

	@Column(name="kindlevel")
	public String getKindLevel() {
		return kindLevel;
	}

	public void setKindLevel(String kindLevel) {
		this.kindLevel = kindLevel;
	}
	@Column(name="upperkindcode")
	public String getUpperKindCode() {
		return upperKindCode;
	}

	public void setUpperKindCode(String upperKindCode) {
		this.upperKindCode = upperKindCode;
	}
	@Column(name="upperkindname")
	public String getUpperKindName() {
		return upperKindName;
	}

	public void setUpperKindName(String upperKindName) {
		this.upperKindName = upperKindName;
	}
	
	
	@Column(name="kindratio")
	public BigDecimal getKindRatio() {
		return kindRatio;
	}

	public void setKindRatio(BigDecimal kindRatio) {
		this.kindRatio = kindRatio;
	}
	//modify begin mod by zhupeng 20110712 reason:需求变更 增加补贴等字段
	@Column(name="allowance")
	public String getAllowance() {
		return allowance;
	}

	public void setAllowance(String allowance) {
		this.allowance = allowance;
	}
	@Column(name="allowanceAmount")
	public String getAllowanceAmount() {
		return allowanceAmount;
	}

	public void setAllowanceAmount(String allowanceAmount) {
		this.allowanceAmount = allowanceAmount;
	}
	@Column(name="allowanceDays")
	public String getAllowanceDays() {
		return allowanceDays;
	}

	public void setAllowanceDays(String allowanceDays) {
		this.allowanceDays = allowanceDays;
	}
	@Column(name="allowanceSumAmount")
	public String getAllowanceSumAmount() {
		return allowanceSumAmount;
	}

	public void setAllowanceSumAmount(String allowanceSumAmount) {
		this.allowanceSumAmount = allowanceSumAmount;
	}

	//modify end mod by zhupeng 20110712 reason:需求变更 增加补贴等字段
	@Column(name = "policyCategory")
	public String getPolicyCategory() {
		return policyCategory;
	}

	public void setPolicyCategory(String policyCategory) {
		this.policyCategory = policyCategory;
	}
	@Column(name = "policyClassification")
	public String getPolicyClassification() {
		return policyClassification;
	}

	public void setPolicyClassification(String policyClassification) {
		this.policyClassification = policyClassification;
	}
	@Column(name = "insuranceClassification")
	public String getInsuranceClassification() {
		return insuranceClassification;
	}

	public void setInsuranceClassification(String insuranceClassification) {
		this.insuranceClassification = insuranceClassification;
	}
	@Column(name = "commodityRiskGrade")
	public String getCommodityRiskGrade() {
		return commodityRiskGrade;
	}

	public void setCommodityRiskGrade(String commodityRiskGrade) {
		this.commodityRiskGrade = commodityRiskGrade;
	}
}
