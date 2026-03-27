package com.sinosoft.claim.schema.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/***
 * PrpLltext内容集的一个模板，暂时主要用于理算说明类型的配置
 * @author 中科软
 */
@Entity
@Table(name = "PRPLLTEXTMODEL")
public class PrpLltextModel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private PrpLltextModelId id;
	/** 险种 riskCode */
	private String riskCode;
	/** 明细内容的标题 */
	private String title;
	/** 明细内容 */
	private String context;
	/** 状态 */
	private String validateStatus;

	public PrpLltextModel() {
		id = new PrpLltextModelId();
	}

	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "riskType", column = @Column(name = "RISKTYPE")), @AttributeOverride(name = "contextNo", column = @Column(name = "CONTEXTNO")),
			@AttributeOverride(name = "lineNo", column = @Column(name = "LINENO")) })
	public PrpLltextModelId getId() {
		return id;
	}

	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	public void setId(PrpLltextModelId id) {
		this.id = id;
	}

	@Column(name = "TITLE")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContext() {
		return context;
	}

	@Column(name = "CONTEXT")
	public void setContext(String context) {
		this.context = context;
	}

	@Column(name = "VALIDATESTATUS")
	public String getValidateStatus() {
		return validateStatus;
	}

	public void setValidateStatus(String validateStatus) {
		this.validateStatus = validateStatus;
	}

}
