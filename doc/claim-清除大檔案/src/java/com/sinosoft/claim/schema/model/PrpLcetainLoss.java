package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类PrpLcetainLoss
 */
@Entity
@Table(name = "PRPLCETAINLOSS")
public class PrpLcetainLoss implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLcetainLossId id;

	/**
	 * 类PrpLcetainLoss的默认构造方法
	 */
	public PrpLcetainLoss() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "claimNo", column = @Column(name = "CLAIMNO")), @AttributeOverride(name = "riskCode", column = @Column(name = "RISKCODE")),
			@AttributeOverride(name = "registNo", column = @Column(name = "REGISTNO")), @AttributeOverride(name = "itemNo", column = @Column(name = "ITEMNO")), @AttributeOverride(name = "licenseNo", column = @Column(name = "LICENSENO")),
			@AttributeOverride(name = "policyNo", column = @Column(name = "POLICYNO")), @AttributeOverride(name = "startDate", column = @Column(name = "STARTDATE")), @AttributeOverride(name = "startHour", column = @Column(name = "STARTHOUR")),
			@AttributeOverride(name = "endDate", column = @Column(name = "ENDDATE")), @AttributeOverride(name = "endHour", column = @Column(name = "ENDHOUR")), @AttributeOverride(name = "currency", column = @Column(name = "CURRENCY")),
			@AttributeOverride(name = "sumrest", column = @Column(name = "SUMREST")), @AttributeOverride(name = "summanager", column = @Column(name = "SUMMANAGER")),
			@AttributeOverride(name = "sumcertainloss", column = @Column(name = "SUMCERTAINLOSS")), @AttributeOverride(name = "sumverifyloss", column = @Column(name = "SUMVERIFYLOSS")),
			@AttributeOverride(name = "lossdesc", column = @Column(name = "LOSSDESC")), @AttributeOverride(name = "indemnityduty", column = @Column(name = "INDEMNITYDUTY")),
			@AttributeOverride(name = "indemnitydutyrate", column = @Column(name = "INDEMNITYDUTYRATE")), @AttributeOverride(name = "remark", column = @Column(name = "REMARK")),
			@AttributeOverride(name = "operatorCode", column = @Column(name = "OPERATORCODE")), @AttributeOverride(name = "approverCode", column = @Column(name = "APPROVERCODE")), @AttributeOverride(name = "flag", column = @Column(name = "FLAG")) })
	public PrpLcetainLossId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLcetainLossId id) {
		this.id = id;
	}

}
