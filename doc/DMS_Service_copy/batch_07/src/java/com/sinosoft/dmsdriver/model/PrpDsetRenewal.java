package com.sinosoft.dmsdriver.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "prpdsetrenewal")
public class PrpDsetRenewal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PrpDsetRenewalId id;
	/**��Ʒ����*/
	private PrpDset prpDset;
    /**�Ƿ��m�����I*/
	private String convertFlag;
	/**�����ߴ�̖*/
	private String createrCode;
	/**��������*/
	private Date createTime;
	/**�����ߴ�̖*/
	private String updaterCode;
	/**��������*/
	private Date updateTime;
	/**��Ч����*/
	private Date validDate;
	/**ʧЧ����*/
	private Date invalidDate;
	/**�A���ֶ�1*/
	private String tcol1;
	/**�A���ֶ�2*/
	private String tcol2;
	/**�A���ֶ�3*/
	private String tcol3;

	public PrpDsetRenewal() {
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "setCode", column = @Column(name = "setCode")),
			@AttributeOverride(name = "newSetCode", column = @Column(name = "newSetCode")) })
	public PrpDsetRenewalId getId() {
		return id;
	}

	public void setId(PrpDsetRenewalId id) {
		this.id = id;
	}

	@Column(name = "convertFlag")
	public String getConvertFlag() {
		return convertFlag;
	}

	public void setConvertFlag(String convertFlag) {
		this.convertFlag = convertFlag;
	}

	@Column(name = "createrCode")
	public String getCreaterCode() {
		return createrCode;
	}

	public void setCreaterCode(String createrCode) {
		this.createrCode = createrCode;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "createTime")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "updaterCode")
	public String getUpdaterCode() {
		return updaterCode;
	}

	public void setUpdaterCode(String updaterCode) {
		this.updaterCode = updaterCode;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "updateTime")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "validDate")
	public Date getValidDate() {
		return validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "invalidDate")
	public Date getInvalidDate() {
		return invalidDate;
	}

	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}

	@Column(name = "tcol1")
	public String getTcol1() {
		return tcol1;
	}

	public void setTcol1(String tcol1) {
		this.tcol1 = tcol1;
	}

	@Column(name = "tcol2")
	public String getTcol2() {
		return tcol2;
	}

	public void setTcol2(String tcol2) {
		this.tcol2 = tcol2;
	}

	@Column(name = "tcol3")
	public String getTcol3() {
		return tcol3;
	}

	public void setTcol3(String tcol3) {
		this.tcol3 = tcol3;
	}
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="setCode", nullable=false, insertable=false, updatable=false)
	public PrpDset getPrpDset() {
		return prpDset;
	}
	public void setPrpDset(PrpDset prpDset) {
		this.prpDset = prpDset;
	}
}
