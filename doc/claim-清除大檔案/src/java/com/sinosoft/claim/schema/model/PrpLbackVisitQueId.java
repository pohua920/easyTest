package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLbackVisitQueId
 */
@Embeddable
public class PrpLbackVisitQueId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性客户回访记录ID */
	private BigDecimal backVisitID;

	/** 属性REGISTNO */
	private String registno;

	/** 属性业务类型 */
	private String backVisitType;

	/** 属性回访项目代码 */
	private String questionCode;

	/**
	 * 类PrpLbackVisitQueId的默认构造方法
	 */
	public PrpLbackVisitQueId() {
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
	 * 属性REGISTNO的getter方法
	 */

	@Column(name = "REGISTNO")
	public String getRegistno() {
		return this.registno;
	}

	/**
	 * 属性REGISTNO的setter方法
	 */
	public void setRegistno(String registno) {
		this.registno = registno;
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
	 * 属性回访项目代码的getter方法
	 */

	@Column(name = "QUESTIONCODE")
	public String getQuestionCode() {
		return this.questionCode;
	}

	/**
	 * 属性回访项目代码的setter方法
	 */
	public void setQuestionCode(String questionCode) {
		this.questionCode = questionCode;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLbackVisitQueId)) {
			return false;
		}
		PrpLbackVisitQueId castOther = (PrpLbackVisitQueId) other;

		return ((this.getBackVisitID() == castOther.getBackVisitID()) || (this.getBackVisitID() != null && castOther.getBackVisitID() != null && this.getBackVisitID().equals(castOther.getBackVisitID())))
				&& ((this.getRegistno() == castOther.getRegistno()) || (this.getRegistno() != null && castOther.getRegistno() != null && this.getRegistno().equals(castOther.getRegistno())))
				&& ((this.getBackVisitType() == castOther.getBackVisitType()) || (this.getBackVisitType() != null && castOther.getBackVisitType() != null && this.getBackVisitType().equals(castOther.getBackVisitType())))
				&& ((this.getQuestionCode() == castOther.getQuestionCode()) || (this.getQuestionCode() != null && castOther.getQuestionCode() != null && this.getQuestionCode().equals(castOther.getQuestionCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getBackVisitID() == null ? 0 : this.getBackVisitID().hashCode());
		result = 37 * result + (getRegistno() == null ? 0 : this.getRegistno().hashCode());
		result = 37 * result + (getBackVisitType() == null ? 0 : this.getBackVisitType().hashCode());
		result = 37 * result + (getQuestionCode() == null ? 0 : this.getQuestionCode().hashCode());
		return result;
	}

}
