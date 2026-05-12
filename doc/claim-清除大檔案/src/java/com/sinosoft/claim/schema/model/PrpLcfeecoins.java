package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * POJO类PrpLcfeecoins
 */
@Entity
@Table(name = "PRPLCFEECOINS")
public class PrpLcfeecoins implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLcfeecoinsId id;

	/** 属性险种 */
	private String riskCode;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性币别 */
	private String currency;

	/** 属性联共保人代码 */
	private String coinsCode;

	/** 属性联共保人名称 */
	private String coinsName;

	/** 属性联共保类型 */
	private String coinsType;

	/** 属性联共保比例 */
	private double coinsRate;

	/** 属性首席标志 */
	private String chiefFlag;

	/** 属性赔款费用标志 */
	private String lossFeeType;

	/** 属性费用类别代码 */
	private String chargeCode;

	/** 属性费用名称 */
	private String chargeName;

	/** 属性赔付金额 */
	private Double sumPaid;

	/** 属性分摊赔付金额 */
	private Double coinsSumPaid;

	/** 属性标志 */
	private String flag;

	private List<PrpLcfeecoins> prpLcfeecoinsList;

	/**
	 * 类PrpLcfeecoins的默认构造方法
	 */
	public PrpLcfeecoins() {
		id = new PrpLcfeecoinsId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "businessNo", column = @Column(name = "BUSINESSNO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpLcfeecoinsId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLcfeecoinsId id) {
		this.id = id;
	}

	/**
	 * 属性险种的getter方法
	 */

	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return this.riskCode;
	}

	/**
	 * 属性险种的setter方法
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	/**
	 * 属性保单号码的getter方法
	 */

	@Column(name = "POLICYNO")
	public String getPolicyNo() {
		return this.policyNo;
	}

	/**
	 * 属性保单号码的setter方法
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	/**
	 * 属性币别的getter方法
	 */

	@Column(name = "CURRENCY")
	public String getCurrency() {
		return this.currency;
	}

	/**
	 * 属性币别的setter方法
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * 属性联共保人代码的getter方法
	 */

	@Column(name = "COINSCODE")
	public String getCoinsCode() {
		return this.coinsCode;
	}

	/**
	 * 属性联共保人代码的setter方法
	 */
	public void setCoinsCode(String coinsCode) {
		this.coinsCode = coinsCode;
	}

	/**
	 * 属性联共保人名称的getter方法
	 */

	@Column(name = "COINSNAME")
	public String getCoinsName() {
		return this.coinsName;
	}

	/**
	 * 属性联共保人名称的setter方法
	 */
	public void setCoinsName(String coinsName) {
		this.coinsName = coinsName;
	}

	/**
	 * 属性联共保类型的getter方法
	 */

	@Column(name = "COINSTYPE")
	public String getCoinsType() {
		return this.coinsType;
	}

	/**
	 * 属性联共保类型的setter方法
	 */
	public void setCoinsType(String coinsType) {
		this.coinsType = coinsType;
	}

	/**
	 * 属性联共保比例的getter方法
	 */

	@Column(name = "COINSRATE")
	public double getCoinsRate() {
		return this.coinsRate;
	}

	/**
	 * 属性联共保比例的setter方法
	 */
	public void setCoinsRate(double coinsRate) {
		this.coinsRate = coinsRate;
	}

	/**
	 * 属性首席标志的getter方法
	 */

	@Column(name = "CHIEFFLAG")
	public String getChiefFlag() {
		return this.chiefFlag;
	}

	/**
	 * 属性首席标志的setter方法
	 */
	public void setChiefFlag(String chiefFlag) {
		this.chiefFlag = chiefFlag;
	}

	/**
	 * 属性赔款费用标志的getter方法
	 */

	@Column(name = "LOSSFEETYPE")
	public String getLossFeeType() {
		return this.lossFeeType;
	}

	/**
	 * 属性赔款费用标志的setter方法
	 */
	public void setLossFeeType(String lossFeeType) {
		this.lossFeeType = lossFeeType;
	}

	/**
	 * 属性费用类别代码的getter方法
	 */

	@Column(name = "CHARGECODE")
	public String getChargeCode() {
		return this.chargeCode;
	}

	/**
	 * 属性费用类别代码的setter方法
	 */
	public void setChargeCode(String chargeCode) {
		this.chargeCode = chargeCode;
	}

	/**
	 * 属性费用名称的getter方法
	 */

	@Column(name = "CHARGENAME")
	public String getChargeName() {
		return this.chargeName;
	}

	/**
	 * 属性费用名称的setter方法
	 */
	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}

	/**
	 * 属性赔付金额的getter方法
	 */

	@Column(name = "SUMPAID")
	public Double getSumPaid() {
		return this.sumPaid;
	}

	/**
	 * 属性赔付金额的setter方法
	 */
	public void setSumPaid(Double sumPaid) {
		if (sumPaid == null) {
			this.sumPaid = 0D;
		} else {
			this.sumPaid = sumPaid;
		}
	}

	/**
	 * 属性分摊赔付金额的getter方法
	 */

	@Column(name = "COINSSUMPAID")
	public Double getCoinsSumPaid() {
		return this.coinsSumPaid;
	}

	/**
	 * 属性分摊赔付金额的setter方法
	 */
	public void setCoinsSumPaid(Double coinsSumPaid) {
		if (coinsSumPaid == null) {
			this.coinsSumPaid = 0D;
		} else {
			this.coinsSumPaid = coinsSumPaid;
		}
	}

	/**
	 * 属性标志的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性标志的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Transient
	public List<PrpLcfeecoins> getPrpLcfeecoinsList() {
		return prpLcfeecoinsList;
	}

	public void setPrpLcfeecoinsList(List<PrpLcfeecoins> prpLcfeecoinsList) {
		this.prpLcfeecoinsList = prpLcfeecoinsList;
	}

}
