package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLbackVisitTextId
 */
@Embeddable
public class PrpLbackVisitTextId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性客户回访记录ID */
	private BigDecimal backVisitID;

	/** 属性报案号码 */
	private String registNo;

	/** 属性业务类型 */
	private String backVisitType;

	/** 属性行序号 */
	private BigDecimal lineNo;

	/**
	 * 类PrpLbackVisitTextId的默认构造方法
	 */
	public PrpLbackVisitTextId() {
	}

	/**
	 * 属性客户回访记录ID的getter方法
	 */

	@Column(name = "BACKVISITID")
	public BigDecimal getBackVisitID() {
		return this.backVisitID;
	}

	/**
	 * 属性客户回访记录ID的setter方法
	 */
	public void setBackVisitID(BigDecimal backVisitID) {
		this.backVisitID = backVisitID;
	}

	/**
	 * 属性报案号码的getter方法
	 */

	@Column(name = "REGISTNO")
	public String getRegistNo() {
		return this.registNo;
	}

	/**
	 * 属性报案号码的setter方法
	 */
	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}

	/**
	 * 属性业务类型的getter方法
	 */

	@Column(name = "BACKVISITTYPE")
	public String getBackVisitType() {
		return this.backVisitType;
	}

	/**
	 * 属性业务类型的setter方法
	 */
	public void setBackVisitType(String backVisitType) {
		this.backVisitType = backVisitType;
	}

	/**
	 * 属性行序号的getter方法
	 */

	@Column(name = "LINENO")
	public BigDecimal getLineNo() {
		return this.lineNo;
	}

	/**
	 * 属性行序号的setter方法
	 */
	public void setLineNo(BigDecimal lineNo) {
		this.lineNo = lineNo;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLbackVisitTextId)) {
			return false;
		}
		PrpLbackVisitTextId castOther = (PrpLbackVisitTextId) other;

		return ((this.getBackVisitID() == castOther.getBackVisitID()) || (this.getBackVisitID() != null && castOther.getBackVisitID() != null && this.getBackVisitID().equals(castOther.getBackVisitID())))
				&& ((this.getRegistNo() == castOther.getRegistNo()) || (this.getRegistNo() != null && castOther.getRegistNo() != null && this.getRegistNo().equals(castOther.getRegistNo())))
				&& ((this.getBackVisitType() == castOther.getBackVisitType()) || (this.getBackVisitType() != null && castOther.getBackVisitType() != null && this.getBackVisitType().equals(castOther.getBackVisitType())))
				&& ((this.getLineNo() == castOther.getLineNo()) || (this.getLineNo() != null && castOther.getLineNo() != null && this.getLineNo().equals(castOther.getLineNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getBackVisitID() == null ? 0 : this.getBackVisitID().hashCode());
		result = 37 * result + (getRegistNo() == null ? 0 : this.getRegistNo().hashCode());
		result = 37 * result + (getBackVisitType() == null ? 0 : this.getBackVisitType().hashCode());
		result = 37 * result + (getLineNo() == null ? 0 : this.getLineNo().hashCode());
		return result;
	}

}
