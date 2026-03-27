package cn.com.sinosoft.dms.vo;

// ���ù��� Hibernate Tools 3.2.4.GA (sinosoft version) ��ɣ������ֹ��޸ġ�

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.com.sinosoft.dms.model.PrpDtreatyRetenId;

/**
 * POJO��prpdtreatyreten
 */
@Entity
@Table(name = "PRPDTREATYRETEN")
public class PrpDtreatyReten implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** ������̶����ʶ */
	private PrpDtreatyRetenId id;

	/** ����riskclass */
	private String riskClass;

	/** ����riskclassdesc */
	private String riskClassDesc;

	/** ����risklevel */
	private String riskLevel;

	/** ����riskleveldesc */
	private String riskLevelDesc;

	/** ����business */
	private String business;

	/** ����businessdesc */
	private String businessDesc;

	/** ����upperlimit */
	private BigDecimal upperLimit;

	/** ����lowerlimit */
	private BigDecimal lowerLimit;

	/** ����grade */
	private String grade;

	/** ���Աұ���� */
	private String currency;

	/** ����retentionvalue */
	private BigDecimal retentionValue;

	/** ����retentionrate */
	private BigDecimal retentionRate;

	/** ����limitvalue */
	private BigDecimal limitValue;

	/** ���������� */
	private Date startDate;

	/** �����ձ����� */
	private Date endDate;

	/** ����remarks */
	private String remarks;

	/** ����retenflag */
	private String retenFlag;

	/** ���Ա�־λ */
	private String flag;

	/**
	 * ��prpdtreatyreten��Ĭ�Ϲ��췽��
	 */
	public PrpDtreatyReten() {
	}

	/**       
	 * ������̶����ʶ��getter����
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "uwYear", column = @Column(name = "uwYear")),
			@AttributeOverride(name = "classCode", column = @Column(name = "classCode")),
			@AttributeOverride(name = "riskCode", column = @Column(name = "riskCode")),
			@AttributeOverride(name = "serialNo", column = @Column(name = "serialno")) })
	public PrpDtreatyRetenId getId() {
		return this.id;
	}

	/**       
	 * ������̶����ʶ��setter����
	 */
	public void setId(PrpDtreatyRetenId id) {
		this.id = id;
	}

	/**       
	 * ����riskclass��getter����
	 */

	@Column(name = "RISKCLASS")
	public String getRiskClass() {
		return this.riskClass;
	}

	/**       
	 * ����riskclass��setter����
	 */
	public void setRiskClass(String riskClass) {
		this.riskClass = riskClass;
	}

	/**       
	 * ����riskclassdesc��getter����
	 */

	@Column(name = "RISKCLASSDESC")
	public String getRiskClassDesc() {
		return this.riskClassDesc;
	}

	/**       
	 * ����riskclassdesc��setter����
	 */
	public void setRiskClassDesc(String riskClassDesc) {
		this.riskClassDesc = riskClassDesc;
	}

	/**       
	 * ����risklevel��getter����
	 */

	@Column(name = "RISKLEVEL")
	public String getRiskLevel() {
		return this.riskLevel;
	}

	/**       
	 * ����risklevel��setter����
	 */
	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}

	/**       
	 * ����riskleveldesc��getter����
	 */

	@Column(name = "RISKLEVELDESC")
	public String getRiskLevelDesc() {
		return this.riskLevelDesc;
	}

	/**       
	 * ����riskleveldesc��setter����
	 */
	public void setRiskLevelDesc(String riskLevelDesc) {
		this.riskLevelDesc = riskLevelDesc;
	}

	/**       
	 * ����business��getter����
	 */

	@Column(name = "BUSINESS")
	public String getBusiness() {
		return this.business;
	}

	/**       
	 * ����business��setter����
	 */
	public void setBusiness(String business) {
		this.business = business;
	}

	/**       
	 * ����businessdesc��getter����
	 */

	@Column(name = "BUSINESSDESC")
	public String getBusinessDesc() {
		return this.businessDesc;
	}

	/**       
	 * ����businessdesc��setter����
	 */
	public void setBusinessDesc(String businessDesc) {
		this.businessDesc = businessDesc;
	}

	/**       
	 * ����upperlimit��getter����
	 */

	@Column(name = "UPPERLIMIT")
	public BigDecimal getUpperLimit() {
		return this.upperLimit;
	}

	/**       
	 * ����upperlimit��setter����
	 */
	public void setUpperLimit(BigDecimal upperLimit) {
		this.upperLimit = upperLimit;
	}

	/**       
	 * ����lowerlimit��getter����
	 */

	@Column(name = "LOWERLIMIT")
	public BigDecimal getLowerLimit() {
		return this.lowerLimit;
	}

	/**       
	 * ����lowerlimit��setter����
	 */
	public void setLowerLimit(BigDecimal lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	/**       
	 * ����grade��getter����
	 */

	@Column(name = "GRADE")
	public String getGrade() {
		return this.grade;
	}

	/**       
	 * ����grade��setter����
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}

	/**       
	 * ���Աұ�����getter����
	 */

	@Column(name = "CURRENCY")
	public String getCurrency() {
		return this.currency;
	}

	/**       
	 * ���Աұ�����setter����
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**       
	 * ����retentionvalue��getter����
	 */

	@Column(name = "RETENTIONVALUE")
	public BigDecimal getRetentionValue() {
		return this.retentionValue;
	}

	/**       
	 * ����retentionvalue��setter����
	 */
	public void setRetentionValue(BigDecimal retentionValue) {
		this.retentionValue = retentionValue;
	}

	/**       
	 * ����retentionrate��getter����
	 */

	@Column(name = "RETENTIONRATE")
	public BigDecimal getRetentionRate() {
		return this.retentionRate;
	}

	/**       
	 * ����retentionrate��setter����
	 */
	public void setRetentionRate(BigDecimal retentionRate) {
		this.retentionRate = retentionRate;
	}

	/**       
	 * ����limitvalue��getter����
	 */

	@Column(name = "LIMITVALUE")
	public BigDecimal getLimitValue() {
		return this.limitValue;
	}

	/**       
	 * ����limitvalue��setter����
	 */
	public void setLimitValue(BigDecimal limitValue) {
		this.limitValue = limitValue;
	}

	/**       
	 * ���������ڵ�getter����
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "STARTDATE")
	public Date getStartDate() {
		return this.startDate;
	}

	/**       
	 * ���������ڵ�setter����
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**       
	 * �����ձ����ڵ�getter����
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "ENDDATE")
	public Date getEndDate() {
		return this.endDate;
	}

	/**       
	 * �����ձ����ڵ�setter����
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**       
	 * ����remarks��getter����
	 */

	@Column(name = "REMARKS")
	public String getRemarks() {
		return this.remarks;
	}

	/**       
	 * ����remarks��setter����
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**       
	 * ����retenflag��getter����
	 */

	@Column(name = "RETENFLAG")
	public String getRetenFlag() {
		return this.retenFlag;
	}

	/**       
	 * ����retenflag��setter����
	 */
	public void setRetenFlag(String retenFlag) {
		this.retenFlag = retenFlag;
	}

	/**       
	 * ���Ա�־λ��getter����
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**       
	 * ���Ա�־λ��setter����
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
