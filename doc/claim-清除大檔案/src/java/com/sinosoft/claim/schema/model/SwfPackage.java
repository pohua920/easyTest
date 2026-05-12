package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类SwfPackage工作流日志业务信息表
 */
@Entity
@Table(name = "SWFPACKAGE")
public class SwfPackage implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private SwfPackageId id;

	/** 属性明细项内容 */
	private String detailContent;

	/** 属性标志字段 */
	private String flag;

	/**
	 * 类SwfPackage的默认构造方法
	 */
	public SwfPackage() {
		id = new SwfPackageId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "packageID", column = @Column(name = "PACKAGEID")), @AttributeOverride(name = "detailNo", column = @Column(name = "DETAILNO")) })
	public SwfPackageId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(SwfPackageId id) {
		this.id = id;
	}

	/**
	 * 属性明细项内容的getter方法
	 */

	@Column(name = "DETAILCONTENT")
	public String getDetailContent() {
		return this.detailContent;
	}

	/**
	 * 属性明细项内容的setter方法
	 */
	public void setDetailContent(String detailContent) {
		this.detailContent = detailContent;
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
