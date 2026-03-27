package com.sinosoft.undwrt.undwrtBase.model;

// Generated 2012-12-31 14:01:30 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 核保級別配置類id.
 */
@Embeddable
public class UtiUwLevelId implements java.io.Serializable {

	/** 屬性員工代碼. */
	private String userCode;

	/** 屬性機構代碼. */
	private String comCode;

	/** 屬性模板號. */
	private int modelNo;

	/** 屬性節點號. */
	private int nodeNo;

	/** 屬性有效狀態. */
	private String validStatus;

	/** 屬性類型 T核保. */
	private String uwType;
	
	private String rowId;

	/**
	 * 構造方法.
	 */
	public UtiUwLevelId() {
	}

	/**
	 * Instantiates a new uti uw level id.
	 * 
	 * @param userCode
	 *            員工代碼
	 * @param comCode
	 *            機構代碼
	 * @param modelNo
	 *            模板號
	 * @param nodeNo
	 *            節點號
	 * @param validStatus
	 *            有效狀態
	 * @param uwType
	 *            類型
	 */
	public UtiUwLevelId(String userCode, String comCode, int modelNo, int nodeNo, String validStatus, String uwType) {
		this.userCode = userCode;
		this.comCode = comCode;
		this.modelNo = modelNo;
		this.nodeNo = nodeNo;
		this.validStatus = validStatus;
		this.uwType = uwType;
	}

	/**
	 * Instantiates a new uti uw level id.
	 * 
	 * @param userCode
	 *            員工代碼
	 * @param comCode
	 *            機構代碼
	 * @param riskCode
	 *            險種代碼
	 * @param modelNo
	 *            模板號
	 * @param nodeNo
	 *            節點號
	 * @param startDate
	 *            起始日期
	 * @param endDate
	 *            終止日期
	 * @param validStatus
	 *            有效狀態
	 * @param flag
	 *            標志
	 * @param uwType
	 *            類型
	 * @param classCode
	 *            險類
	 */
	public UtiUwLevelId(String userCode, String comCode, String riskCode, int modelNo, int nodeNo, String startDate, String endDate, String validStatus,
			String flag, String uwType, String classCode) {
		super();
		this.userCode = userCode;
		this.comCode = comCode;
		this.modelNo = modelNo;
		this.nodeNo = nodeNo;
		this.validStatus = validStatus;
		this.uwType = uwType;
	}

	/**
	 * 獲取屬性員工代碼.
	 * 
	 * @return 屬性員工代碼的值
	 */
	@Column(name = "USERCODE", nullable = false, length = 10)
	public String getUserCode() {
		return this.userCode;
	}

	/**
	 * 設置屬性員工代碼.
	 * 
	 * @param userCode
	 *            待設置的員工代碼的值
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * 獲取屬性機構代碼.
	 * 
	 * @return 屬性機構代碼的值
	 */
	@Column(name = "COMCODE", nullable = false, length = 10)
	public String getComCode() {
		return this.comCode;
	}

	/**
	 * 設置屬性機構代碼.
	 * 
	 * @param comCode
	 *            待設置的機構代碼的值
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	/**
	 * 獲取屬性模板號.
	 * 
	 * @return 屬性模板號的值
	 */
	@Column(name = "MODELNO", nullable = false, precision = 15, scale = 0)
	public int getModelNo() {
		return this.modelNo;
	}

	/**
	 * 設置屬性模板號.
	 * 
	 * @param modelNo
	 *            待設置的模板號的值
	 */
	public void setModelNo(int modelNo) {
		this.modelNo = modelNo;
	}

	/**
	 * 獲取屬性節點號.
	 * 
	 * @return 屬性節點號的值
	 */
	@Column(name = "NODENO", nullable = false, precision = 22, scale = 0)
	public int getNodeNo() {
		return this.nodeNo;
	}

	/**
	 * 設置屬性節點號.
	 * 
	 * @param nodeNo
	 *            待設置的節點號的值
	 */
	public void setNodeNo(int nodeNo) {
		this.nodeNo = nodeNo;
	}

	/**
	 * 獲取屬性有效狀態.
	 * 
	 * @return 屬性有效狀態的值
	 */
	@Column(name = "VALIDSTATUS", nullable = false, length = 1)
	public String getValidStatus() {
		return this.validStatus;
	}

	/**
	 * 設置屬性有效狀態.
	 * 
	 * @param validStatus
	 *            待設置的有效狀態的值
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	/**
	 * 獲取屬性類型.
	 * 
	 * @return 屬性類型的值
	 */
	@Column(name = "UWTYPE", nullable = false, length = 1)
	public String getUwType() {
		return this.uwType;
	}

	/**
	 * 設置屬性類型.
	 * 
	 * @param uwType
	 *            待設置的類型的值
	 */
	public void setUwType(String uwType) {
		this.uwType = uwType;
	}
	
	@Column(name = "ROWID", nullable = false)
	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
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
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof UtiUwLevelId))
			return false;
		UtiUwLevelId castOther = (UtiUwLevelId) other;

		return ((this.getUserCode() == castOther.getUserCode()) || (this.getUserCode() != null && castOther.getUserCode() != null && this.getUserCode().equals(
				castOther.getUserCode())))
				&& ((this.getComCode() == castOther.getComCode()) || (this.getComCode() != null && castOther.getComCode() != null && this.getComCode().equals(
						castOther.getComCode())))
				&& (this.getModelNo() == castOther.getModelNo())
				&& ((this.getNodeNo() == castOther.getNodeNo()) || (this.getNodeNo() != 0 && castOther.getNodeNo() != 0 && this.getNodeNo() == (castOther
						.getNodeNo())))
				&& ((this.getValidStatus() == castOther.getValidStatus()) || (this.getValidStatus() != null && castOther.getValidStatus() != null && this
						.getValidStatus().equals(castOther.getValidStatus())))
				&& ((this.getUwType() == castOther.getUwType()) || (this.getUwType() != null && castOther.getUwType() != null && this.getUwType().equals(
						castOther.getUwType())));
	}

	/**
	 * Hash code.
	 * 
	 * @return the int
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		int result = 17;

		result = 37 * result + (getUserCode() == null ? 0 : this.getUserCode().hashCode());
		result = 37 * result + (getComCode() == null ? 0 : this.getComCode().hashCode());
		result = 37 * result + (int) this.getModelNo();
		result = 37 * result + (this.getNodeNo());
		result = 37 * result + (getValidStatus() == null ? 0 : this.getValidStatus().hashCode());
		result = 37 * result + (getUwType() == null ? 0 : this.getUwType().hashCode());
		return result;
	}

}
