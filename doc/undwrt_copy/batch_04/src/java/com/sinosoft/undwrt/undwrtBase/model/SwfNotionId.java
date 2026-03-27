package com.sinosoft.undwrt.undwrtBase.model;
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类SwfNotionId.
 */
@Embeddable
public class SwfNotionId implements java.io.Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 属性工作流ID. */
	private String flowId;

	/** 属性日誌序號. */
	private int logNo;

	/** 属性行號. */
	private int lineNo;

	/**
	 * 类SwfNotionId的默认构造方法.
	 */
	public SwfNotionId() {
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
	 * 属性行号的getter方法.
	 * 
	 * @return the 属性行号
	 */

	@Column(name = "LINENO")
	public int getLineNo() {
		return this.lineNo;
	}

	/**
	 * 属性行号的setter方法.
	 * 
	 * @param lineNo
	 *            the new 属性行号
	 */
	public void setLineNo(int lineNo) {
		this.lineNo = lineNo;
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
		if (!(other instanceof SwfNotionId)) {
			return false;
		}
		SwfNotionId castOther = (SwfNotionId) other;

		return ((this.getFlowId() == castOther.getFlowId()) || (this
				.getFlowId() != null && castOther.getFlowId() != null && this
				.getFlowId().equals(castOther.getFlowId())))
				&& ((this.getLogNo() == castOther.getLogNo()) || (this
						.getLogNo() != 0 && castOther.getLogNo() != 0 && this
						.getLogNo()==(castOther.getLogNo())))
				&& ((this.getLineNo() == castOther.getLineNo()) || (this
						.getLineNo() != 0 && castOther.getLineNo() != 0 && this
						.getLineNo()==(castOther.getLineNo())));
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
				+ (getLogNo() == 0 ? 0 : this.getLogNo());
		result = 37 * result
				+ (getLineNo() == 0 ? 0 : this.getLineNo());
		return result;
	}

}
