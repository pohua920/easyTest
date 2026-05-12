package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类SwfPathGraph 工作流路径图形表
 */
@Entity
@Table(name = "SWFPATHGRAPH")
public class SwfPathGraph implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private SwfPathGraphId id;

	/** 属性节点X坐标 */
	private Integer posX = 0;

	/** 属性节点Y坐标 */
	private Integer posY = 0;

	/** 属性标志字段 */
	private String flag;

	/**
	 * 类SwfPathGraph的默认构造方法
	 */
	public SwfPathGraph() {
		id = new SwfPathGraphId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "modelNo", column = @Column(name = "MODELNO")), @AttributeOverride(name = "pathNo", column = @Column(name = "PATHNO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public SwfPathGraphId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(SwfPathGraphId id) {
		this.id = id;
	}

	/**
	 * 属性节点X坐标的getter方法
	 */

	@Column(name = "POSX")
	public Integer getPosX() {
		return this.posX;
	}

	/**
	 * 属性节点X坐标的setter方法
	 */
	public void setPosX(Integer posX) {
		this.posX = posX;
	}

	/**
	 * 属性节点Y坐标的getter方法
	 */

	@Column(name = "POSY")
	public Integer getPosY() {
		return this.posY;
	}

	/**
	 * 属性节点Y坐标的setter方法
	 */
	public void setPosY(Integer posY) {
		this.posY = posY;
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
