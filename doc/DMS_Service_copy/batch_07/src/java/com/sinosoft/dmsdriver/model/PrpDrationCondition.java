package com.sinosoft.dmsdriver.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="PrpDrationCondition")
public class PrpDrationCondition implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**产品代码 */
	private	PrpDrationConditionId id;
    /** 方案代码 */
    private PrpDration prpDration;
    /** 方案简体中文名称 */
    private String rationName;
	/**起始年齡 */
	private	String beginAges;
	/**起始調整天數 */
	private	String beginAgesAdjustDays;
	/**迄止年齡*/
	private String endAges;
	/**迄止調整天數 */
	private	String endAgesAdjustDays;
	/**身份別*/
	private String identityType;
	/**性別*/
	private String gender;
	/**職業等級起*/
	private String careerBegin;
	/**職業等級迄*/
	private String careerEnd;
	/**新/續保件*/
	private String insureType;
	/**生效日期 */
	private	Date validDate;
	/**失效日期 */
	private	Date invalidDate;
	/**有效标记 */
	private	String validInd;
	/**創建者代號*/
	private	String createrCode;
	/**創建日期*/
	private Date createTime;
	/**更新代號*/
	private String updaterCode;
	/**修改日期*/
	private Date updateTime;
	/**預留字段1*/
	private String tcol1;//由于需求，借用到保费字段
	/**預留字段2*/
	private String tcol2;
	/**預留字段3*/
	private String tcol3;
	
	public PrpDrationCondition() {
	}	
		
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "rationCode", column = @Column(name = "rationCode")),
			@AttributeOverride(name = "conditionNo", column = @Column(name = "conditionNo")) })
	public PrpDrationConditionId getId() {
		return id;
	}

	public void setId(PrpDrationConditionId id) {
		this.id = id;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="rationCode", nullable=false, insertable=false, updatable=false)
    public PrpDration getPrpDration() {
        return this.prpDration;
    }
    
    public void setPrpDration(PrpDration prpDration) {
        this.prpDration = prpDration;
    }
	@Column(name="rationName")
	public String getRationName() {
		return rationName;
	}

	public void setRationName(String rationName) {
		this.rationName = rationName;
	}
	@Column(name="beginAges")
	public String getBeginAges() {
		return beginAges;
	}

	public void setBeginAges(String beginAges) {
		this.beginAges = beginAges;
	}
	@Column(name="beginAgesAdjustDays")
	public String getBeginAgesAdjustDays() {
		return beginAgesAdjustDays;
	}

	public void setBeginAgesAdjustDays(String beginAgesAdjustDays) {
		this.beginAgesAdjustDays = beginAgesAdjustDays;
	}
	@Column(name="endAges")
	public String getEndAges() {
		return endAges;
	}

	public void setEndAges(String endAges) {
		this.endAges = endAges;
	}
	@Column(name="endAgesAdjustDays")
	public String getEndAgesAdjustDays() {
		return endAgesAdjustDays;
	}

	public void setEndAgesAdjustDays(String endAgesAdjustDays) {
		this.endAgesAdjustDays = endAgesAdjustDays;
	}
	@Column(name="identityType")
	public String getIdentityType() {
		return identityType;
	}

	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}
	@Column(name="gender")
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	@Column(name="careerBegin")
	public String getCareerBegin() {
		return careerBegin;
	}

	public void setCareerBegin(String careerBegin) {
		this.careerBegin = careerBegin;
	}
	@Column(name="careerEnd")
	public String getCareerEnd() {
		return careerEnd;
	}

	public void setCareerEnd(String careerEnd) {
		this.careerEnd = careerEnd;
	}
	@Column(name="insureType")
	public String getInsureType() {
		return insureType;
	}

	public void setInsureType(String insureType) {
		this.insureType = insureType;
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
	
}
