package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.sinosoft.sysframework.common.util.StringUtils;

/**
 * POJO类UtiUserGrade
 */
@Entity
@Table(name = "UTIUSERGRADE")
public class UtiUserGrade implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private UtiUserGradeId id;

	/** 属性失效日期 */
	private Date invalidDate;

	/** 属性是否有效 */
	private String validStatus;

	/** 属性备注 */
	private String remark;

	/** 属性标志 */
	private String flag;

	/** 属性员工姓名 */
	private String userName;
	/** 属性机构名称 */
	private String comName;
	/** 属性岗位名称 */
	private String gradeName;

	/**
	 * 类UtiUserGrade的默认构造方法
	 */
	public UtiUserGrade() {
		setValidStatus("1");
		id = new UtiUserGradeId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "comcode", column = @Column(name = "COMCODE")), @AttributeOverride(name = "userCode", column = @Column(name = "USERCODE")),
			@AttributeOverride(name = "gradeCode", column = @Column(name = "GRADECODE")) })
	public UtiUserGradeId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(UtiUserGradeId id) {
		this.id = id;
	}

	/**
	 * 属性失效日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "INVALIDDATE")
	public Date getInvalidDate() {
		return this.invalidDate;
	}

	/**
	 * 属性失效日期的setter方法
	 */
	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}

	/**
	 * 属性是否有效的getter方法
	 */

	@Column(name = "VALIDSTATUS")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**
	 * 属性是否有效的setter方法
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = StringUtils.rightTrim(validStatus);
	}

	/**
	 * 属性备注的getter方法
	 */

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 属性备注的setter方法
	 */
	public void setRemark(String remark) {
		this.remark = StringUtils.rightTrim(remark);
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
		this.flag = StringUtils.rightTrim(flag);
	}

	@Transient
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Transient
	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	@Transient
	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

}
