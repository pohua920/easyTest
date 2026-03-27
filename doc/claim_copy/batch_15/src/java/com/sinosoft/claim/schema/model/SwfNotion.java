package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类SwfNotion工作流意见处理表
 */
@Entity
@Table(name = "SWFNOTION")
public class SwfNotion implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private SwfNotionId id;

	/** 属性处理意见 */
	private String handleText;

	/** 属性标志字段 */
	private String flag;

	/**
	 * 类SwfNotion的默认构造方法
	 */
	public SwfNotion() {
		id = new SwfNotionId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "flowID", column = @Column(name = "FLOWID")), @AttributeOverride(name = "logNo", column = @Column(name = "LOGNO")), @AttributeOverride(name = "lineNo", column = @Column(name = "LINENO")) })
	public SwfNotionId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(SwfNotionId id) {
		this.id = id;
	}

	/**
	 * 属性处理意见的getter方法
	 */

	@Column(name = "HANDLETEXT")
	public String getHandleText() {
		return this.handleText;
	}

	/**
	 * 属性处理意见的setter方法
	 */
	public void setHandleText(String handleText) {
		this.handleText = handleText;
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
