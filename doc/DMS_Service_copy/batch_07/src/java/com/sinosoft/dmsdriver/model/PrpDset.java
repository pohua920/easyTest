package com.sinosoft.dmsdriver.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="PrpDset")
public class PrpDset implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**��Ʒ����*/
	private String riskCode;
	/**��Ʒ���� */
	private	PrpDrisk prpDrisk;
	/**��װ��Ʒ���� */
	private	String setCode;
	/**��װ��Ʒ��� */
	private	String setName;
	/**��װ��ƷӢ�����*/
	private String setEName;
	/**��Ч���� */
	private	Date validDate;
	/**ʧЧ���� */
	private	Date invalidDate;
	/**��Ч��� */
	private	String validInd;
	/**�m��ʧЧ���� */
	private	Date renewalInvalidDate;
	/**�m���D�Q���I*/
	private	String renewalFlag;
	/**���]*/
	private	String remark;
	/**�����ߴ�̖*/
	private	String createrCode;
	/**��������*/
	private Date createTime;
	/**���´�̖*/
	private String updaterCode;
	/**�޸�����*/
	private Date updateTime;
	/**��҇���I*/
	private String auditFlag;
	/**�A���ֶ�1*/
	private String tcol1;
	/**�A���ֶ�2*/
	private String tcol2;
	/**�A���ֶ�3*/
	private String tcol3;
	
    private List<PrpDsetRationrelation> prpDsetRationrelations = new ArrayList<PrpDsetRationrelation>(0);
    private List<PrpDsetChannel> prpDsetChannels = new ArrayList<PrpDsetChannel>(0);
    private List<PrpDsetRenewal> prpDsetRenewals = new ArrayList<PrpDsetRenewal>(0);
	public PrpDset() {
	}
	
	@Id
	@Column(name="setCode")
	public String getSetCode() {
		return setCode;
	}
	public void setSetCode(String setCode) {
		this.setCode = setCode;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	//@JoinColumn(name = "riskCode", referencedColumnName="riskCode")
	@JoinColumn(name = "riskCode", nullable=false, insertable=false, updatable=false)
	
	public PrpDrisk getPrpDrisk() {
		return prpDrisk;
	}
	public void setPrpDrisk(PrpDrisk prpDrisk) {
		this.prpDrisk = prpDrisk;
	}

	@Column(name="setName")
	public String getSetName() {
		return setName;
	}
	public void setSetName(String setName) {
		this.setName = setName;
	}
	@Column(name="setEName")
	public String getSetEName() {
		return setEName;
	}
	public void setSetEName(String setEName) {
		this.setEName = setEName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="validDate")
	public Date getValidDate() {
		return validDate;
	}
	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}
	@Temporal(TemporalType.DATE)
	@Column(name="invalidDate")
	public Date getInvalidDate() {
		return invalidDate;
	}
	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}

	@Column(name="validInd")
	public String getValidInd() {
		return validInd;
	}
	public void setValidInd(String validInd) {
		this.validInd = validInd;
	}
	@Temporal(TemporalType.DATE)
	@Column(name="renewalInvalidDate")
	public Date getRenewalInvalidDate() {
		return renewalInvalidDate;
	}
	public void setRenewalInvalidDate(Date renewalInvalidDate) {
		this.renewalInvalidDate = renewalInvalidDate;
	}

	@Column(name="renewalFlag")
	public String getRenewalFlag() {
		return renewalFlag;
	}
	public void setRenewalFlag(String renewalFlag) {
		this.renewalFlag = renewalFlag;
	}
	@Column(name="remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name="createrCode")
	public String getCreaterCode() {
		return createrCode;
	}
	public void setCreaterCode(String createrCode) {
		this.createrCode = createrCode;
	}
	@Temporal(TemporalType.DATE)
	@Column(name="createTime")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name="updaterCode")
	public String getUpdaterCode() {
		return updaterCode;
	}
	public void setUpdaterCode(String updaterCode) {
		this.updaterCode = updaterCode;
	}
	@Temporal(TemporalType.DATE)
	@Column(name="updateTime")
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@Column(name="auditFlag")
	public String getAuditFlag() {
		return auditFlag;
	}
	public void setAuditFlag(String auditFlag) {
		this.auditFlag = auditFlag;
	}
	@Column(name="tcol1")
	public String getTcol1() {
		return tcol1;
	}
	public void setTcol1(String tcol1) {
		this.tcol1 = tcol1;
	}
	@Column(name="tcol2")
	public String getTcol2() {
		return tcol2;
	}
	public void setTcol2(String tcol2) {
		this.tcol2 = tcol2;
	}
	@Column(name="tcol3")
	public String getTcol3() {
		return tcol3;
	}
	public void setTcol3(String tcol3) {
		this.tcol3 = tcol3;
	}
	@Column(name="riskCode")
	public String getRiskCode() {
		return riskCode;
	}
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="prpDset")
	public List<PrpDsetChannel> getPrpDsetChannels() {
		return prpDsetChannels;
	}
	public void setPrpDsetChannels(List<PrpDsetChannel> prpDsetChannels) {
		this.prpDsetChannels = prpDsetChannels;
	}
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="prpDset")
	public List<PrpDsetRationrelation> getPrpDsetRationrelations() {
		return prpDsetRationrelations;
	}
	public void setPrpDsetRationrelations(
			List<PrpDsetRationrelation> prpDsetRationrelations) {
		this.prpDsetRationrelations = prpDsetRationrelations;
	}
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="prpDset")
	public List<PrpDsetRenewal> getPrpDsetRenewals() {
		return prpDsetRenewals;
	}
	public void setPrpDsetRenewals(List<PrpDsetRenewal> prpDsetRenewals) {
		this.prpDsetRenewals = prpDsetRenewals;
	}
}
