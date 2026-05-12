package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpMaxUseId
 */
@Embeddable
public class PrpMaxUseId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性合编组 */
	private String groupNo;

	/** 属性编号 */
	private String tableName;

	/** 属性表名 */
	private String maxNo;

	/**
	 * 类PrpMaxUseId的默认构造方法
	 */
	public PrpMaxUseId() {
	}

	/**
	 * 属性合编组的getter方法
	 */

	@Column(name = "GROUPNO")
	public String getGroupNo() {
		return this.groupNo;
	}

	/**
	 * 属性合编组的setter方法
	 */
	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	/**
	 * 属性编号的getter方法
	 */

	@Column(name = "TABLENAME")
	public String getTableName() {
		return this.tableName;
	}

	/**
	 * 属性编号的setter方法
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * 属性表名的getter方法
	 */

	@Column(name = "MAXNO")
	public String getMaxNo() {
		return this.maxNo;
	}

	/**
	 * 属性表名的setter方法
	 */
	public void setMaxNo(String maxNo) {
		this.maxNo = maxNo;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpMaxUseId)) {
			return false;
		}
		PrpMaxUseId castOther = (PrpMaxUseId) other;

		return ((this.getGroupNo() == castOther.getGroupNo()) || (this.getGroupNo() != null && castOther.getGroupNo() != null && this.getGroupNo().equals(castOther.getGroupNo())))
				&& ((this.getTableName() == castOther.getTableName()) || (this.getTableName() != null && castOther.getTableName() != null && this.getTableName().equals(castOther.getTableName())))
				&& ((this.getMaxNo() == castOther.getMaxNo()) || (this.getMaxNo() != null && castOther.getMaxNo() != null && this.getMaxNo().equals(castOther.getMaxNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getGroupNo() == null ? 0 : this.getGroupNo().hashCode());
		result = 37 * result + (getTableName() == null ? 0 : this.getTableName().hashCode());
		result = 37 * result + (getMaxNo() == null ? 0 : this.getMaxNo().hashCode());
		return result;
	}

}
