package com.sinosoft.claim.schema.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLverifyLossId
 */
@Embeddable
public class PrpLverifyLossId implements java.io.Serializable {

	/**
	 * @Fields serialVersionUID:
	 */
	private static final long serialVersionUID = 1L;

	/** 属性REGISTNO */
	private String registNo;

	/** 属性标的序号 */
	private String lossItemCode;
	/** 属性节点名称 */
	private String nodeType;

	public PrpLverifyLossId() {

	}

	public PrpLverifyLossId(String registNo, String lossItemCode, String nodeType) {
		super();
		this.registNo = registNo;
		this.lossItemCode = lossItemCode;
		this.nodeType = nodeType;
	}

	@Column(name = "REGISTNO")
	public String getRegistNo() {
		return registNo;
	}

	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}

	@Column(name = "LOSSITEMCODE")
	public String getLossItemCode() {
		return lossItemCode;
	}

	public void setLossItemCode(String lossItemCode) {
		this.lossItemCode = lossItemCode;
	}
	@Column(name = "nodeType")
	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLverifyLossExtId)) {
			return false;
		}
		PrpLverifyLossId castOther = (PrpLverifyLossId) other;

		return ((this.getRegistNo() == castOther.getRegistNo()) || (this.getRegistNo() != null && castOther.getRegistNo() != null && this.getRegistNo().equals(castOther.getRegistNo())))
				&& ((this.getLossItemCode() == castOther.getLossItemCode()) || (this.getLossItemCode() != null && castOther.getLossItemCode() != null && this.getLossItemCode().equals(castOther.getLossItemCode())))
				&& ((this.getNodeType() == castOther.getNodeType()) || (this.getNodeType() != null && castOther.getNodeType() != null && this.getNodeType().equals(castOther.getNodeType())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getRegistNo() == null ? 0 : this.getRegistNo().hashCode());
		result = 37 * result + (getLossItemCode() == null ? 0 : this.getLossItemCode().hashCode());
		result = 37 * result + (getNodeType() == null ? 0 : this.getNodeType().hashCode());
		return result;
	}
}
