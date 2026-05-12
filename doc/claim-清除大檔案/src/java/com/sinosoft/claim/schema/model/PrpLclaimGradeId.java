package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLclaimGradeId
 */
@Embeddable
public class PrpLclaimGradeId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性员工代码 */
	private String userCode;

	/** 属性任务代码 */
	private String taskCode;

	/** 属性配置参数 */
	private String configPara;

	/**
	 * 类PrpLclaimGradeId的默认构造方法
	 */
	public PrpLclaimGradeId() {
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
		this.userCode = userCode;
	}

	/**
	 * 属性任务代码的getter方法
	 */

	@Column(name = "TASKCODE")
	public String getTaskCode() {
		return this.taskCode;
	}

	/**
	 * 属性任务代码的setter方法
	 */
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	/**
	 * 属性配置参数的getter方法
	 */

	@Column(name = "CONFIGPARA")
	public String getConfigPara() {
		return this.configPara;
	}

	/**
	 * 属性配置参数的setter方法
	 */
	public void setConfigPara(String configPara) {
		this.configPara = configPara;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLclaimGradeId)) {
			return false;
		}
		PrpLclaimGradeId castOther = (PrpLclaimGradeId) other;

		return ((this.getUserCode() == castOther.getUserCode()) || (this.getUserCode() != null && castOther.getUserCode() != null && this.getUserCode().equals(castOther.getUserCode())))
				&& ((this.getTaskCode() == castOther.getTaskCode()) || (this.getTaskCode() != null && castOther.getTaskCode() != null && this.getTaskCode().equals(castOther.getTaskCode())))
				&& ((this.getConfigPara() == castOther.getConfigPara()) || (this.getConfigPara() != null && castOther.getConfigPara() != null && this.getConfigPara().equals(castOther.getConfigPara())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getUserCode() == null ? 0 : this.getUserCode().hashCode());
		result = 37 * result + (getTaskCode() == null ? 0 : this.getTaskCode().hashCode());
		result = 37 * result + (getConfigPara() == null ? 0 : this.getConfigPara().hashCode());
		return result;
	}

	public PrpLclaimGradeId(String userCode, String taskCode, String configPara) {
		this.userCode = userCode;
		this.taskCode = taskCode;
		this.configPara = configPara;
	}

}
