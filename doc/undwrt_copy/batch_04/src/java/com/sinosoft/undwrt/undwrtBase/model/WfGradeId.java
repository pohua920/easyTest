package com.sinosoft.undwrt.undwrtBase.model;
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类WfGradeId.
 */
@Embeddable
public class WfGradeId implements java.io.Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 属性流水号. */
	private String flowId;

	/** 属性序号. */
	private int logNo;

	/** 属性定级方式. */
	private String gradeMode;

	/**
	 * 类WfGradeId的默认构造方法.
	 */
	public WfGradeId() {
	}

	/**
	 * 属性流水号的getter方法.
	 * 
	 * @return the 属性流水号
	 */

	@Column(name = "FLOWID")
	public String getFlowId() {
		return this.flowId;
	}

	/**
	 * 属性流水号的setter方法.
	 * 
	 * @param flowId
	 *            the new 属性流水号
	 */
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	/**
	 * 属性序号的getter方法.
	 * 
	 * @return the 属性序号
	 */

	@Column(name = "LOGNO")
	public int getLogNo() {
		return this.logNo;
	}

	/**
	 * 属性序号的setter方法.
	 * 
	 * @param logNo
	 *            the new 属性序号
	 */
	public void setLogNo(int logNo) {
		this.logNo = logNo;
	}

	/**
	 * 属性定级方式的getter方法.
	 * 
	 * @return the 属性定级方式
	 */

	@Column(name = "GRADEMODE")
	public String getGradeMode() {
		return this.gradeMode;
	}

	/**
	 * 属性定级方式的setter方法.
	 * 
	 * @param gradeMode
	 *            the new 属性定级方式
	 */
	public void setGradeMode(String gradeMode) {
		this.gradeMode = gradeMode;
	}

	/**
	 * Equals.
	 * 
	 * @param other
	 *            the other
	 * @return true, if successful
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof WfGradeId)) {
			return false;
		}
		WfGradeId castOther = (WfGradeId) other;

		return ((this.getFlowId() == castOther.getFlowId()) || (this
				.getFlowId() != null && castOther.getFlowId() != null && this
				.getFlowId().equals(castOther.getFlowId())))
				&& ((this.getLogNo() == castOther.getLogNo()) || (this
						.getLogNo() != 0 && castOther.getLogNo() != 0 && this
						.getLogNo()==(castOther.getLogNo())))
				&& ((this.getGradeMode() == castOther.getGradeMode()) || (this
						.getGradeMode() != null
						&& castOther.getGradeMode() != null && this
						.getGradeMode()==(castOther.getGradeMode())));
	}

	/**
	 * Hash code.
	 * 
	 * @return the int
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getFlowId() == null ? 0 : this.getFlowId().hashCode());
		result = 37 * result
				+ (this.getLogNo());
		result = 37 * result
				+ (getGradeMode() == null ? 0 : this.getGradeMode().hashCode());
		return result;
	}

}
