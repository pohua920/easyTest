package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sinosoft.claim.dto.custom.TurnPageDto;

/**
 * POJO类PrpLInsuranceSurveyor
 */
@Entity
@Table(name = "PRPLINSURANCESURVEYOR")
public class PrpLInsuranceSurveyor implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLInsuranceSurveyorId id;

	/** 属性公估师中文名称 */
	private String comcname;

	/** 属性公估师英文名称 */
	private String comename;

	/** 属性联系电话 */
	private String telephone;

	/** 属性电子邮件 */
	private String email;

	/** 属性有效标志 */
	private String validStatus;
	private TurnPageDto turnPageDto = null;

	private String editType = "";

	private String newComCName = "";

	private String comType = "";

	/**
	 * 类PrpLInsuranceSurveyor的默认构造方法
	 */
	public PrpLInsuranceSurveyor() {
		id = new PrpLInsuranceSurveyorId();// 对ID初始化
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "comCode", column = @Column(name = "COMCODE")), @AttributeOverride(name = "newcomcode", column = @Column(name = "NEWCOMCODE")) })
	public PrpLInsuranceSurveyorId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLInsuranceSurveyorId id) {
		this.id = id;
	}

	/**
	 * 属性公估师中文名称的getter方法
	 */

	@Column(name = "COMCNAME")
	public String getComcname() {
		return this.comcname;
	}

	/**
	 * 属性公估师中文名称的setter方法
	 */
	public void setComcname(String comcname) {
		this.comcname = comcname;
	}

	/**
	 * 属性公估师英文名称的getter方法
	 */

	@Column(name = "COMENAME")
	public String getComename() {
		return this.comename;
	}

	/**
	 * 属性公估师英文名称的setter方法
	 */
	public void setComename(String comename) {
		this.comename = comename;
	}

	/**
	 * 属性联系电话的getter方法
	 */

	@Column(name = "TELEPHONE")
	public String getTelephone() {
		return this.telephone;
	}

	/**
	 * 属性联系电话的setter方法
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * 属性电子邮件的getter方法
	 */

	@Column(name = "EMAIL")
	public String getEmail() {
		return this.email;
	}

	/**
	 * 属性电子邮件的setter方法
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 属性有效标志的getter方法
	 */

	@Column(name = "VALIDSTATUS")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**
	 * 属性有效标志的setter方法
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	@Transient
	public TurnPageDto getTurnPageDto() {
		return turnPageDto;
	}

	public void setTurnPageDto(TurnPageDto turnPageDto) {
		this.turnPageDto = turnPageDto;
	}

	@Transient
	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	@Transient
	public String getNewComCName() {
		return newComCName;
	}

	public void setNewComCName(String newComCName) {
		this.newComCName = newComCName;
	}

	@Transient
	public String getComType() {
		return comType;
	}

	public void setComType(String comType) {
		this.comType = comType;
	}

}
