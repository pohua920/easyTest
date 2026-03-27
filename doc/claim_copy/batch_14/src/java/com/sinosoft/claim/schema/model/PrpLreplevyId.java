package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLreplevyId
 */
@Embeddable
public class PrpLreplevyId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性赔案号 */
	private String claimNo;

	/** 属性用户输入的出险次数或单证个数 */
	private BigDecimal times;

	/**
	 * 类PrpLreplevyId的默认构造方法
	 */
	public PrpLreplevyId() {
	}

	/**
	 * 属性赔案号的getter方法
	 */

	@Column(name = "CLAIMNO")
	public String getClaimNo() {
		return this.claimNo;
	}

	/**
	 * 属性赔案号的setter方法
	 */
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	/**
	 * 属性用户输入的出险次数或单证个数的getter方法
	 */

	@Column(name = "TIMES")
	public BigDecimal getTimes() {
		return this.times;
	}

	/**
	 * 属性用户输入的出险次数或单证个数的setter方法
	 */
	public void setTimes(BigDecimal times) {
		this.times = times;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLreplevyId)) {
			return false;
		}
		PrpLreplevyId castOther = (PrpLreplevyId) other;

		return ((this.getClaimNo() == castOther.getClaimNo()) || (this.getClaimNo() != null && castOther.getClaimNo() != null && this.getClaimNo().equals(castOther.getClaimNo())))
				&& ((this.getTimes() == castOther.getTimes()) || (this.getTimes() != null && castOther.getTimes() != null && this.getTimes().equals(castOther.getTimes())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getClaimNo() == null ? 0 : this.getClaimNo().hashCode());
		result = 37 * result + (getTimes() == null ? 0 : this.getTimes().hashCode());
		return result;
	}

}
