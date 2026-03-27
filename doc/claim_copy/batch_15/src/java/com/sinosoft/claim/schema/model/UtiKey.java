package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * POJO类UtiKey
 */
@Entity
@Table(name = "UTIKEY")
public class UtiKey implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性编号 */
	private String tableName;

	/** 属性字段名 */
	private String fieldName;

	/** 属性名称 */
	private String fieldMeaning;

	/** 属性COLLENGTH */
	private int colLength;

	/** 属性HEADID */
	private String headID;

	/** 属性标志 */
	private String flag;

	/**
	 * 类UtiKey的默认构造方法
	 */
	public UtiKey() {
	}

	/**
	 * 属性编号的getter方法
	 */
	@Id
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
	 * 属性字段名的getter方法
	 */

	@Column(name = "FIELDNAME")
	public String getFieldName() {
		return this.fieldName;
	}

	/**
	 * 属性字段名的setter方法
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * 属性名称的getter方法
	 */

	@Column(name = "FIELDMEANING")
	public String getFieldMeaning() {
		return this.fieldMeaning;
	}

	/**
	 * 属性名称的setter方法
	 */
	public void setFieldMeaning(String fieldMeaning) {
		this.fieldMeaning = fieldMeaning;
	}

	/**
	 * 属性COLLENGTH的getter方法
	 */

	@Column(name = "COLLENGTH")
	public int getColLength() {
		return this.colLength;
	}

	/**
	 * 属性COLLENGTH的setter方法
	 */
	public void setColLength(int colLength) {
		this.colLength = colLength;
	}

	/**
	 * 属性HEADID的getter方法
	 */

	@Column(name = "HEADID")
	public String getHeadID() {
		return this.headID;
	}

	/**
	 * 属性HEADID的setter方法
	 */
	public void setHeadID(String headID) {
		this.headID = headID;
	}

	/**
	 * 属性标志的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性标志的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
