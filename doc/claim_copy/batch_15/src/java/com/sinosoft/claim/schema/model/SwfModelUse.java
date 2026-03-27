package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * POJO类SwfModelUse模板使用设置表
 */
@Entity
@Table(name = "SWFMODELUSE")
public class SwfModelUse implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private SwfModelUseId id;

	/** 属性模板状态 */
	private String modelStatus;

	/** 属性标志字段 */
	private String flag;

	/*
	 * 数据库中没有的字段，在页面上展示用和处理逻辑使用
	 */
	/** 编辑类型 */
	private String editType = "";
	/** 列表 */
	List<SwfModelUse> modelUseList = new ArrayList<SwfModelUse>(0);

	/**
	 * 类SwfModelUse的默认构造方法
	 */
	public SwfModelUse() {
		id = new SwfModelUseId();
	}
	
	/**
	 * 两个对象值的复制,
	 * @param swfModelUse
	 */
	public SwfModelUse(SwfModelUse swfModelUse) {
		id = new SwfModelUseId();
		this.setFlag(swfModelUse.getFlag());
		this.setModelStatus(swfModelUse.getModelStatus());
		this.getId().setComCode(swfModelUse.getId().getComCode());
		this.getId().setModelNo(swfModelUse.getId().getModelNo());
		this.getId().setModelType(swfModelUse.getId().getModelType());
		this.getId().setRiskCode(swfModelUse.getId().getRiskCode());
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "modelNo", column = @Column(name = "MODELNO")), @AttributeOverride(name = "riskCode", column = @Column(name = "RISKCODE")), @AttributeOverride(name = "comCode", column = @Column(name = "COMCODE")),
			@AttributeOverride(name = "modelType", column = @Column(name = "MODELTYPE")) })
	public SwfModelUseId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(SwfModelUseId id) {
		this.id = id;
	}

	/**
	 * 属性模板状态的getter方法
	 */

	@Column(name = "MODELSTATUS")
	public String getModelStatus() {
		return this.modelStatus;
	}

	/**
	 * 属性模板状态的setter方法
	 */
	public void setModelStatus(String modelStatus) {
		this.modelStatus = modelStatus;
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

	@Transient
	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	@Transient
	public List<SwfModelUse> getModelUseList() {
		return modelUseList;
	}

	public void setModelUseList(List<SwfModelUse> modelUseList) {
		this.modelUseList = modelUseList;
	}

}
