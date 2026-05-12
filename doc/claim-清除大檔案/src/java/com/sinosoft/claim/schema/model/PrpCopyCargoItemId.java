// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。
package com.sinosoft.claim.schema.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;

/**
 * POJO类PrpCCargoItemId
 */
@Embeddable
public class PrpCopyCargoItemId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性批单号 */
	private String endorseNo;

	/** 属性组号 */
	private String teamNo;

	/** 属性行号 */
	private String lineNo;

	/**
	 * 类PrpCCargoItemId的默认构造方法
	 */
	public PrpCopyCargoItemId() {
	}

	/**
	 * 属性批单号码的getter方法
	 */
	@Column(name = "ENDORSENO")
	public String getEndorseNo() {
		return this.endorseNo;
	}

	/**
	 * 属性批单号码的setter方法
	 */
	public void setEndorseNo(String endorseNo) {
		this.endorseNo = endorseNo;
	}

	/**
	 * 属性组号的getter方法
	 */

	@Column(name = "TEAMNO")
	public String getTeamNo() {
		return this.teamNo;
	}

	/**
	 * 属性组号的setter方法
	 */
	public void setTeamNo(String teamNo) {
		this.teamNo = teamNo;
	}

	/**
	 * 属性行号的getter方法
	 */

	@Column(name = "LINENO")
	public String getLineNo() {
		return this.lineNo;
	}

	/**
	 * 属性行号的setter方法
	 */
	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpCopyCargoItemId)) {
			return false;
		}
		PrpCopyCargoItemId castOther = (PrpCopyCargoItemId) other;

		return ((this.getEndorseNo() == castOther.getEndorseNo()) || (this.getEndorseNo() != null && castOther.getEndorseNo() != null && this.getEndorseNo().equals(castOther.getEndorseNo())))
				&& ((this.getTeamNo() == castOther.getTeamNo()) || (this.getTeamNo() != null && castOther.getTeamNo() != null && this.getTeamNo().equals(castOther.getTeamNo())))
				&& ((this.getLineNo() == castOther.getLineNo()) || (this.getLineNo() != null && castOther.getLineNo() != null && this.getLineNo().equals(castOther.getLineNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getEndorseNo() == null ? 0 : this.getEndorseNo().hashCode());
		result = 37 * result + (getTeamNo() == null ? 0 : this.getTeamNo().hashCode());
		result = 37 * result + (getLineNo() == null ? 0 : this.getLineNo().hashCode());
		return result;
	}

}
