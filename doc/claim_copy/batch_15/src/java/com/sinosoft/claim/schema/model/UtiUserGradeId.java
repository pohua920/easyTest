package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.sinosoft.sysframework.common.util.StringUtils;

/**
 * POJO类UtiUserGradeId
 */
@Embeddable
public class UtiUserGradeId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性归属机构 */
	private String comcode;

	/** 属性员工代码 */
	private String userCode;

	/** 属性岗位代码 */
	private String gradeCode;

	/**
	 * 类UtiUserGradeId的默认构造方法
	 */
	public UtiUserGradeId() {
	}

	/**
	 * 属性归属机构的getter方法
	 */

	@Column(name = "COMCODE")
	public String getComcode() {
		return this.comcode;
	}

	/**
	 * 属性归属机构的setter方法
	 */
	public void setComcode(String comcode) {
		this.comcode = StringUtils.rightTrim(comcode);
	}

	/**
	 * 属性员工代码的getter方法
	 */

	@Column(name = "USERCODE")
	public String getUserCode() {
		return this.userCode;
	}

	/**
	 * 属性员工代码的setter方法
	 */
	public void setUserCode(String userCode) {
		this.userCode = StringUtils.rightTrim(userCode);
	}

	/**
	 * 属性岗位代码的getter方法
	 */

	@Column(name = "GRADECODE")
	public String getGradeCode() {
		return this.gradeCode;
	}

	/**
	 * 属性岗位代码的setter方法
	 */
	public void setGradeCode(String gradeCode) {
		this.gradeCode = StringUtils.rightTrim(gradeCode);
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof UtiUserGradeId)) {
			return false;
		}
		UtiUserGradeId castOther = (UtiUserGradeId) other;

		return ((this.getComcode() == castOther.getComcode()) || (this.getComcode() != null && castOther.getComcode() != null && this.getComcode().equals(castOther.getComcode())))
				&& ((this.getUserCode() == castOther.getUserCode()) || (this.getUserCode() != null && castOther.getUserCode() != null && this.getUserCode().equals(castOther.getUserCode())))
				&& ((this.getGradeCode() == castOther.getGradeCode()) || (this.getGradeCode() != null && castOther.getGradeCode() != null && this.getGradeCode().equals(castOther.getGradeCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getComcode() == null ? 0 : this.getComcode().hashCode());
		result = 37 * result + (getUserCode() == null ? 0 : this.getUserCode().hashCode());
		result = 37 * result + (getGradeCode() == null ? 0 : this.getGradeCode().hashCode());
		return result;
	}

}
