package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpCaddressId
 */
@Embeddable
public class PrpLdisabilityLimitId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	// 等级，，C01,C02,C03
	private String ratingCode;
	// 版本号
	private String version;
	// 险别
	private String riskCode;
	// 险种
	private String kindCode;

	public PrpLdisabilityLimitId() {
	}

	public PrpLdisabilityLimitId(String ratingCode, String version, String riskCode, String kindCode) {
		super();
		this.ratingCode = ratingCode;
		this.version = version;
		this.riskCode = riskCode;
		this.kindCode = kindCode;
	}

	@Column(name = "ratingCode")
	public String getRatingCode() {
		return ratingCode;
	}

	public void setRatingCode(String ratingCode) {
		this.ratingCode = ratingCode;
	}

	@Column(name = "version")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name = "riskCode")
	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	@Column(name = "kindCode")
	public String getKindCode() {
		return kindCode;
	}

	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLdisabilityLimitId)) {
			return false;
		}
		PrpLdisabilityLimitId castOther = (PrpLdisabilityLimitId) other;

		return ((this.getRatingCode() == castOther.getRatingCode()) || (this.getRatingCode() != null && castOther.getRatingCode() != null && this.getRatingCode().equals(castOther.getRatingCode())))
				&& ((this.getVersion() == castOther.getVersion()) || (this.getVersion() != null && castOther.getVersion() != null && this.getVersion().equals(castOther.getVersion())))
				&& ((this.getRiskCode() == castOther.getRiskCode()) || (this.getRiskCode() != null && castOther.getRiskCode() != null && this.getRiskCode().equals(castOther.getRiskCode())))
				&& ((this.getKindCode() == castOther.getKindCode()) || (this.getKindCode() != null && castOther.getKindCode() != null && this.getKindCode().equals(castOther.getKindCode())));
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + (getRatingCode() == null ? 0 : this.getRatingCode().hashCode());
		result = 37 * result + (getVersion() == null ? 0 : this.getVersion().hashCode());
		result = 37 * result + (getRiskCode() == null ? 0 : this.getRiskCode().hashCode());
		result = 37 * result + (getKindCode() == null ? 0 : this.getKindCode().hashCode());
		return result;
	}

}
