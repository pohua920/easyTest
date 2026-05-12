package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类PrpLverifyLossItem
 */
@Entity
@Table(name = "PRPLVERIFYLOSSITEM")
public class PrpLverifyLossItem implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLverifyLossItemId id;

	/**
	 * 类PrpLverifyLossItem的默认构造方法
	 */
	public PrpLverifyLossItem() {
		id = new PrpLverifyLossItemId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "registno", column = @Column(name = "REGISTNO")), @AttributeOverride(name = "claimNo", column = @Column(name = "CLAIMNO")),
			@AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")), @AttributeOverride(name = "lossType", column = @Column(name = "LOSSTYPE")), @AttributeOverride(name = "riskCode", column = @Column(name = "RISKCODE")),
			@AttributeOverride(name = "policyNo", column = @Column(name = "POLICYNO")), @AttributeOverride(name = "insuredName", column = @Column(name = "INSUREDNAME")), @AttributeOverride(name = "licenseNo", column = @Column(name = "LICENSENO")),
			@AttributeOverride(name = "licenseColorCode", column = @Column(name = "LICENSECOLORCODE")), @AttributeOverride(name = "carKindCode", column = @Column(name = "CARKINDCODE")),
			@AttributeOverride(name = "currency", column = @Column(name = "CURRENCY")), @AttributeOverride(name = "sumPreDefLoss", column = @Column(name = "SUMPREDEFLOSS")),
			@AttributeOverride(name = "sumDefLoss", column = @Column(name = "SUMDEFLOSS")), @AttributeOverride(name = "makeCom", column = @Column(name = "MAKECOM")), @AttributeOverride(name = "comCode", column = @Column(name = "COMCODE")),
			@AttributeOverride(name = "handlerCode", column = @Column(name = "HANDLERCODE")), @AttributeOverride(name = "handlerName", column = @Column(name = "HANDLERNAME")),
			@AttributeOverride(name = "defLossDate", column = @Column(name = "DEFLOSSDATE")), @AttributeOverride(name = "underWriteCode", column = @Column(name = "UNDERWRITECODE")),
			@AttributeOverride(name = "underWriteName", column = @Column(name = "UNDERWRITENAME")), @AttributeOverride(name = "underWriteEndDate", column = @Column(name = "UNDERWRITEENDDATE")),
			@AttributeOverride(name = "underwriteflag", column = @Column(name = "UNDERWRITEFLAG")), @AttributeOverride(name = "nodeType", column = @Column(name = "NODETYPE")), @AttributeOverride(name = "remark", column = @Column(name = "REMARK")),
			@AttributeOverride(name = "verifyRemark", column = @Column(name = "VERIFYREMARK")), @AttributeOverride(name = "flag", column = @Column(name = "FLAG")) })
	public PrpLverifyLossItemId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLverifyLossItemId id) {
		this.id = id;
	}

}
