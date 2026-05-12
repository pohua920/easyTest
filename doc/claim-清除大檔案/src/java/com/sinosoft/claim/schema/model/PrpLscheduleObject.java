package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * POJO类PrpLscheduleObject调度对象表
 */
@Entity
@Table(name = "PRPLSCHEDULEOBJECT")
public class PrpLscheduleObject implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性调度对象代码 */
	private String scheduleObjectID;

	/** 属性调度对象名称 */
	private String scheduleObjectName;

	/** 属性调度对象类别 */
	private String objectType;

	/** 属性调度对象所在表主键 */
	private String objectKey;

	/** 属性调度对象所在表 */
	private String objectTable;

	/** 属性调度对象所属地区 */
	private String objectArea;

	/** 属性调度对象所属分组 */
	private BigDecimal objectGroup;

	/** 属性标志字段 */
	private String flag;

	/**
	 * 类PrpLscheduleObject的默认构造方法
	 */
	public PrpLscheduleObject() {
	}

	/**
	 * 属性调度对象代码的getter方法
	 */
	@Id
	@Column(name = "SCHEDULEOBJECTID")
	public String getScheduleObjectID() {
		return this.scheduleObjectID;
	}

	/**
	 * 属性调度对象代码的setter方法
	 */
	public void setScheduleObjectID(String scheduleObjectID) {
		this.scheduleObjectID = scheduleObjectID;
	}

	/**
	 * 属性调度对象名称的getter方法
	 */

	@Column(name = "SCHEDULEOBJECTNAME")
	public String getScheduleObjectName() {
		return this.scheduleObjectName;
	}

	/**
	 * 属性调度对象名称的setter方法
	 */
	public void setScheduleObjectName(String scheduleObjectName) {
		this.scheduleObjectName = scheduleObjectName;
	}

	/**
	 * 属性调度对象类别的getter方法
	 */

	@Column(name = "OBJECTTYPE")
	public String getObjectType() {
		return this.objectType;
	}

	/**
	 * 属性调度对象类别的setter方法
	 */
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	/**
	 * 属性调度对象所在表主键的getter方法
	 */

	@Column(name = "OBJECTKEY")
	public String getObjectKey() {
		return this.objectKey;
	}

	/**
	 * 属性调度对象所在表主键的setter方法
	 */
	public void setObjectKey(String objectKey) {
		this.objectKey = objectKey;
	}

	/**
	 * 属性调度对象所在表的getter方法
	 */

	@Column(name = "OBJECTTABLE")
	public String getObjectTable() {
		return this.objectTable;
	}

	/**
	 * 属性调度对象所在表的setter方法
	 */
	public void setObjectTable(String objectTable) {
		this.objectTable = objectTable;
	}

	/**
	 * 属性调度对象所属地区的getter方法
	 */

	@Column(name = "OBJECTAREA")
	public String getObjectArea() {
		return this.objectArea;
	}

	/**
	 * 属性调度对象所属地区的setter方法
	 */
	public void setObjectArea(String objectArea) {
		this.objectArea = objectArea;
	}

	/**
	 * 属性调度对象所属分组的getter方法
	 */

	@Column(name = "OBJECTGROUP")
	public BigDecimal getObjectGroup() {
		return this.objectGroup;
	}

	/**
	 * 属性调度对象所属分组的setter方法
	 */
	public void setObjectGroup(BigDecimal objectGroup) {
		this.objectGroup = objectGroup;
	}

	/**
	 * 属性标志字段的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性标志字段的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
