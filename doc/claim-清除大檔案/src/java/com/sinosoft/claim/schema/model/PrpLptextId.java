package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLptextId
 */
@Embeddable
public class PrpLptextId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性预赔计算书号 */
	private String preCompensateNo;

	/** 属性行序号 */
	private Long lineNo;

	/**
	 * 类PrpLptextId的默认构造方法
	 */
	public PrpLptextId() {
	}

	/**
	 * 属性预赔计算书号的getter方法
	 */

	@Column(name = "PRECOMPENSATENO")
	public String getPreCompensateNo() {
		return this.preCompensateNo;
	}

	/**
	 * 属性预赔计算书号的setter方法
	 */
	public void setPreCompensateNo(String preCompensateNo) {
		this.preCompensateNo = preCompensateNo;
	}

	/**
	 * 属性行序号的getter方法
	 */

	@Column(name = "LINENO")
	public Long getLineNo() {
		return this.lineNo;
	}

	/**
	 * 属性行序号的setter方法
	 */
	public void setLineNo(Long lineNo) {
		this.lineNo = lineNo;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLptextId)) {
			return false;
		}
		PrpLptextId castOther = (PrpLptextId) other;

		return ((this.getPreCompensateNo() == castOther.getPreCompensateNo()) || (this.getPreCompensateNo() != null && castOther.getPreCompensateNo() != null && this.getPreCompensateNo().equals(castOther.getPreCompensateNo())))
				&& ((this.getLineNo() == castOther.getLineNo()) || (this.getLineNo() != null && castOther.getLineNo() != null && this.getLineNo().equals(castOther.getLineNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getPreCompensateNo() == null ? 0 : this.getPreCompensateNo().hashCode());
		result = 37 * result + (getLineNo() == null ? 0 : this.getLineNo().hashCode());
		return result;
	}

}
