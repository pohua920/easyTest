package com.sinosoft.undwrt.undwrtBase.model;
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类SwfNotion.
 */
@Entity(name = "SWFNOTION_UNDWRT")
@Table(name = "SWFNOTION")
public class SwfNotion implements java.io.Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 属性ID. */
	private SwfNotionId id;

	/** 属性處理意見. */
	private String handleText;

	/** 属性標誌. */
	private String flag;

	/**
	 * 类SwfNotion的默认构造方法.
	 */
	public SwfNotion() {
	}

	/**
	 * 属性id的getter方法.
	 * 
	 * @return the 属性id
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "flowId", column = @Column(name = "FLOWID")),
			@AttributeOverride(name = "logNo", column = @Column(name = "LOGNO")),
			@AttributeOverride(name = "lineNo", column = @Column(name = "LINENO")) })
	public SwfNotionId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法.
	 * 
	 * @param id
	 *            the new 属性id
	 */
	public void setId(SwfNotionId id) {
		this.id = id;
	}

	/**
	 * 属性处理意见的getter方法.
	 * 
	 * @return the 属性处理意见
	 */

	@Column(name = "HANDLETEXT")
	public String getHandleText() {
		return this.handleText;
	}

	/**
	 * 属性处理意见的setter方法.
	 * 
	 * @param handleText
	 *            the new 属性处理意见
	 */
	public void setHandleText(String handleText) {
		this.handleText = handleText;
	}

	/**
	 * 属性属性Flag位的getter方法.
	 * 
	 * @return the 属性属性Flag位
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性属性Flag位的setter方法.
	 * 
	 * @param flag
	 *            the new 属性属性Flag位
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
