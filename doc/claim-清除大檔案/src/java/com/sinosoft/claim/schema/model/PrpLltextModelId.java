package com.sinosoft.claim.schema.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PrpLltextModelId implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/** 险种大类 riskType */
	private String riskType;
	/** 内容集号 配置为险种riskCode（险种没配置则 riskType），01开始 */
	private String contextNo;
	/** 属性行号 */
	private Integer lineNo;

	public PrpLltextModelId(String riskType, String contextNo, Integer lineNo) {
		this.riskType = riskType;
		this.contextNo = contextNo;
		this.lineNo = lineNo;
	}

	public PrpLltextModelId() {
	}

	@Column(name = "RISKTYPE")
	public String getRiskType() {
		return riskType;
	}

	public void setRiskType(String riskType) {
		this.riskType = riskType;
	}

	@Column(name = "CONTEXTNO")
	public String getContextNo() {
		return contextNo;
	}

	public void setContextNo(String contextNo) {
		this.contextNo = contextNo;
	}

	@Column(name = "LINENO")
	public Integer getLineNo() {
		return lineNo;
	}

	public void setLineNo(Integer lineNo) {
		this.lineNo = lineNo;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLltextModelId)) {
			return false;
		}
		PrpLltextModelId castOther = (PrpLltextModelId) other;

		return ((this.getRiskType() == castOther.getRiskType()) || (this.getRiskType() != null && castOther.getRiskType() != null && this.getRiskType().equals(castOther.getRiskType())))
				&& ((this.getContextNo() == castOther.getContextNo()) || (this.getContextNo() != null && castOther.getContextNo() != null && this.getContextNo().equals(castOther.getContextNo())))
				&& ((this.getLineNo() == castOther.getLineNo()) || (this.getLineNo() != null && castOther.getLineNo() != null && this.getLineNo().equals(castOther.getLineNo())));
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + (getRiskType() == null ? 0 : this.getRiskType().hashCode());
		result = 37 * result + (getContextNo() == null ? 0 : this.getContextNo().hashCode());
		result = 37 * result + (getLineNo() == null ? 0 : this.getLineNo().hashCode());
		return result;
	}
}
