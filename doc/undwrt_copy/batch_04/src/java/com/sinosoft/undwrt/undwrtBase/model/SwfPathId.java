package com.sinosoft.undwrt.undwrtBase.model;
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类SwfPathId.
 */
@Embeddable
public class SwfPathId implements java.io.Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 属性模板號. */
	private int modelNo;

	/** 属性路徑號. */
	private int pathNo;

	/**
	 * 类SwfPathId的默认构造方法.
	 */
	public SwfPathId() {
	}

	/**
	 * 属性属性模版号的getter方法.
	 * 
	 * @return the 属性属性模版号
	 */

	@Column(name = "MODELNO")
	public int getModelNo() {
		return this.modelNo;
	}

	/**
	 * 属性属性模版号的setter方法.
	 * 
	 * @param modelNo
	 *            the new 属性属性模版号
	 */
	public void setModelNo(int modelNo) {
		this.modelNo = modelNo;
	}

	/**
	 * 属性属性路径号的getter方法.
	 * 
	 * @return the 属性属性路径号
	 */

	@Column(name = "PATHNO")
	public int getPathNo() {
		return this.pathNo;
	}

	/**
	 * 属性属性路径号的setter方法.
	 * 
	 * @param pathNo
	 *            the new 属性属性路径号
	 */
	public void setPathNo(int pathNo) {
		this.pathNo = pathNo;
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
		if (!(other instanceof SwfPathId)) {
			return false;
		}
		SwfPathId castOther = (SwfPathId) other;

		return ((this.getModelNo() == castOther.getModelNo()) || (this
				.getModelNo() != 0 && castOther.getModelNo() != 0 && this
				.getModelNo()==(castOther.getModelNo())))
				&& ((this.getPathNo() == castOther.getPathNo()) || (this
						.getPathNo() != 0 && castOther.getPathNo() != 0 && this
						.getPathNo()==(castOther.getPathNo())));
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
				+ ( this.getModelNo());
		result = 37 * result
				+ (this.getPathNo());
		return result;
	}

}
