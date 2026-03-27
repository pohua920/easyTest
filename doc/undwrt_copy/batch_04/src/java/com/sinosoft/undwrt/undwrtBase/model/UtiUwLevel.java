package com.sinosoft.undwrt.undwrtBase.model;

// Generated 2012-12-31 14:01:30 by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 核保級別配置類.
 */
@Entity(name = "UTIUWLEVEL_UNDWRT")
@Table(name = "UTIUWLEVEL")
public class UtiUwLevel implements java.io.Serializable {

	/** 屬性id. */
	private UtiUwLevelId id;

	/** 屬性險種代碼. */
	private String riskCode;

	/** 屬性起始日期. */
	private String startDate;

	/** 屬性終止日期. */
	private String endDate;

	/** 屬性標志. */
	private String flag;

	/** 屬性險類代碼. */
	private String classCode;

	/**
	 * Instantiates a new uti uw level.
	 */
	public UtiUwLevel() {
	}

	/**
	 * Instantiates a new uti uw level.
	 * 
	 * @param id
	 *            id
	 */
	public UtiUwLevel(UtiUwLevelId id) {
		this.id = id;
	}

	/**
	 * 獲取屬性id.
	 * 
	 * @return 屬性id的值
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "userCode", column = @Column(name = "USERCODE", nullable = false, length = 10)),
			@AttributeOverride(name = "comCode", column = @Column(name = "COMCODE", nullable = false, length = 10)),
			@AttributeOverride(name = "modelNo", column = @Column(name = "MODELNO", nullable = false, precision = 15, scale = 0)),
			@AttributeOverride(name = "nodeNo", column = @Column(name = "NODENO", nullable = false, precision = 22, scale = 0)),
			@AttributeOverride(name = "validsStatus", column = @Column(name = "VALIDSTATUS", nullable = false, length = 1)),
			@AttributeOverride(name = "uwType", column = @Column(name = "UWTYPE", nullable = false, length = 1)) })
	public UtiUwLevelId getId() {
		return this.id;
	}

	/**
	 * 設置屬性id.
	 * 
	 * @param id
	 *            待設置的id的值
	 */
	public void setId(UtiUwLevelId id) {
		this.id = id;
	}

	/**
	 * 獲取屬性險種代碼.
	 * 
	 * @return 屬性險種代碼的值
	 */
	@Column(name = "RISKCODE", length = 400)
	public String getRiskCode() {
		return this.riskCode;
	}

	/**
	 * 設置屬性險種代碼.
	 * 
	 * @param riskCode
	 *            待設置的險種代碼的值
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	/**
	 * 獲取屬性起始日期.
	 * 
	 * @return 屬性起始日期的值
	 */
	@Column(name = "STARTDATE", length = 10)
	public String getStartDate() {
		return this.startDate;
	}

	/**
	 * 設置屬性起始日期.
	 * 
	 * @param startDate
	 *            待設置的起始日期的值
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * 獲取屬性終止日期.
	 * 
	 * @return 屬性終止日期的值
	 */
	@Column(name = "ENDDATE", length = 10)
	public String getEndDate() {
		return this.endDate;
	}

	/**
	 * 設置屬性終止日期.
	 * 
	 * @param endDate
	 *            待設置的終止日期的值
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * 獲取屬性標志.
	 * 
	 * @return 屬性標志的值
	 */
	@Column(name = "FLAG", length = 1)
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 設置屬性標志.
	 * 
	 * @param flag
	 *            待設置的標志的值
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 獲取屬性險類代碼.
	 * 
	 * @return 屬性險類代碼的值
	 */
	@Column(name = "CLASSCODE")
	public String getClassCode() {
		return this.classCode;
	}

	/**
	 * 設置屬性險類代碼.
	 * 
	 * @param classCode
	 *            待設置的險類代碼的值
	 */
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
}
