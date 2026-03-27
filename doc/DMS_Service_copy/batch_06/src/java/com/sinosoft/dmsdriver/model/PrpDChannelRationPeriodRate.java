package com.sinosoft.dmsdriver.model;

import java.math.BigDecimal;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="PRPDCHANNELRATIONPERIODRATE")
public class PrpDChannelRationPeriodRate implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	/** ���Ա���/���������� */
	private PrpDChannelRationPeriodRateId id;
	/** ������������ */
    private PrpDChannelRationClauseKind prpDChannelRationClauseKind;
	/**  ���ִ��� */
	private String riskCode;
	/**  ������� */
	private String clauseCode;
	/**  ���δ��� */
	private String kindCode;
	//private String getSerialNo;
	/** �������� */
	private String rateType;
	/**  �����ڼ����� */
	private BigDecimal originalPerioLower;
	
	/**  �����ڼ����� */
	private BigDecimal priginalPerioUpper;
	/** ����/�������� */
	private BigDecimal premiumOrRateUpper;
	/** ����/�������� */
	private BigDecimal premiumOrRateLower;
	/** ����/����Ĭ��ֵ*/
	private BigDecimal defaultValue;
	/** �����������*/
	private String channelCode;
	/** ����������� */
	private String channelName;
	/** Ԥ���ֶ� */
	private String tcol1;
	/** Ԥ���ֶ� */
	private String tcol2;
	/** Ԥ���ֶ� */
	private String tcol3;
	/** ��ʶ�ֶ� */
	private String flag;
	
	
	public PrpDChannelRationPeriodRate(){}
	       

    /**       
     * ���Ա���/����������
     */
     @EmbeddedId
    @AttributeOverrides( {
        @AttributeOverride(name="rationCode", column=@Column(name="RATIONCODE") ), 
        @AttributeOverride(name="channelInfoNo", column=@Column(name="CHANNELINFONO") ), 
        @AttributeOverride(name="channelRationClauseKindNo", column=@Column(name="CHANNELRATIONCLAUSEKINDNO") ),
        @AttributeOverride(name="serialNo", column=@Column(name="SERIALNO") ) } )  
	public PrpDChannelRationPeriodRateId getId() {
		return id;
	}

	public void setId(PrpDChannelRationPeriodRateId id) {
		this.id = id;
	}

    /**
     * �����������δ���
     * 
     */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns( {
		    @JoinColumn(name = "rationCode", referencedColumnName="rationCode", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "channelInfoNo", referencedColumnName="CHANNELINFONO", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "CHANNELRATIONCLAUSEKINDNO", referencedColumnName="serialNo", nullable = false, insertable = false, updatable = false) })
	public PrpDChannelRationClauseKind getPrpDChannelRationClauseKind() {
		return prpDChannelRationClauseKind;
	}

	public void setPrpDChannelRationClauseKind(
			PrpDChannelRationClauseKind prpDChannelRationClauseKind) {
		this.prpDChannelRationClauseKind = prpDChannelRationClauseKind;
	}

    @Column(name="RISKCODE")
	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	@Column(name="CLAUSECODE")
	public String getClauseCode() {
		return clauseCode;
	}
	public void setClauseCode(String clauseCode) {
		this.clauseCode = clauseCode;
	}
	
	@Column(name="KINDCODE")
	public String getKindCode() {
		return kindCode;
	}
	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}
	@Column(name="RATETYPE")
	public String getRateType() {
		return rateType;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	
	@Column(name="ORIGINALPERIOLOWER")
	public BigDecimal getOriginalPerioLower() {
		return originalPerioLower;
	}
	public void setOriginalPerioLower(BigDecimal originalPerioLower) {
		this.originalPerioLower = originalPerioLower;
	}
	
	@Column(name="ORIGINALPERIODUPPER")
	public BigDecimal getPriginalPerioUpper() {
		return priginalPerioUpper;
	}
	public void setPriginalPerioUpper(BigDecimal priginalPerioUpper) {
		this.priginalPerioUpper = priginalPerioUpper;
	}
	
	@Column(name="PREMIUMORRATEUPPER")
	public BigDecimal getPremiumOrRateUpper() {
		return premiumOrRateUpper;
	}
	public void setPremiumOrRateUpper(BigDecimal premiumOrRateUpper) {
		this.premiumOrRateUpper = premiumOrRateUpper;
	}
	
	@Column(name="PREMIUMORRATELOWER")
	public BigDecimal getPremiumOrRateLower() {
		return premiumOrRateLower;
	}
	public void setPremiumOrRateLower(BigDecimal premiumOrRateLower) {
		this.premiumOrRateLower = premiumOrRateLower;
	}
	
	@Column(name="DEFAULTVALUE")
	public BigDecimal getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(BigDecimal defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	@Column(name="CHANNELCODE")
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	
	@Column(name="CHANNELNAME")
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	
	@Column(name="TCOL1")
	public String getTcol1() {
		return tcol1;
	}
	public void setTcol1(String tcol1) {
		this.tcol1 = tcol1;
	}
	
	@Column(name="TCOL2")
	public String getTcol2() {
		return tcol2;
	}
	public void setTcol2(String tcol2) {
		this.tcol2 = tcol2;
	}
	
	@Column(name="TCOL3")
	public String getTcol3() {
		return tcol3;
	}
	public void setTcol3(String tcol3) {
		this.tcol3 = tcol3;
	}
	
	@Column(name="FLAG")
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
}
