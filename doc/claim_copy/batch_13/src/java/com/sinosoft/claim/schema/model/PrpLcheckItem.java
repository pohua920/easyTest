package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * POJO类PrpLcheckItem
 */
@Entity
@Table(name = "PRPLCHECKITEM")
public class PrpLcheckItem implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLcheckItemId id;

	/** 属性insurecarflag */
	private String insurecarflag;

	/** 属性claimcomcode */
	private String claimcomcode;

	/** 属性selectsend */
	private String selectsend;

	/** 属性surveytimes */
	private BigDecimal surveytimes;

	/** 属性surveytype */
	private String surveytype;

	/** 属性checksite */
	private String checksite;

	/** 属性licenseno */
	private String licenseno;

	/** 属性scheduleobjectid */
	private String scheduleobjectid;

	/** 属性scheduleobjectname */
	private String scheduleobjectname;

	/** 属性INPUTDATE */
	private Date inputDate;

	/** 属性OPERATORCODE */
	private String operatorCode;

	/** 属性checkoperatorcode */
	private String checkoperatorcode;

	/** 属性resultinfo */
	private String resultinfo;

	/** 属性bookflag */
	private String bookflag;

	/** 属性scheduletype */
	private String scheduletype;

	/** 属性checkflag */
	private String checkflag;

	/** 属性checkinfo */
	private String checkinfo;

	/** 属性FLAG */
	private String flag;
	/** 属性显示列表 */
	private Collection<?> checkItemList;

	/** 属性操作员名称 */
	private String operatorName = "";

	@Transient
	public Collection<?> getCheckItemList() {
		return checkItemList;
	}

	public void setCheckItemList(Collection<?> checkItemList) {
		this.checkItemList = checkItemList;
	}

	/**
	 * 属性的getter方法
	 */
	@Transient
	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	/**
	 * 类PrpLcheckItem的默认构造方法
	 */
	public PrpLcheckItem() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides( { @AttributeOverride(name = "scheduleid", column = @Column(name = "SCHEDULEID")), @AttributeOverride(name = "registno", column = @Column(name = "REGISTNO")),
			@AttributeOverride(name = "itemno", column = @Column(name = "ITEMNO")) })
	public PrpLcheckItemId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLcheckItemId id) {
		this.id = id;
	}

	/**
	 * 属性insurecarflag的getter方法
	 */

	@Column(name = "INSURECARFLAG")
	public String getInsurecarflag() {
		return this.insurecarflag;
	}

	/**
	 * 属性insurecarflag的setter方法
	 */
	public void setInsurecarflag(String insurecarflag) {
		this.insurecarflag = insurecarflag;
	}

	/**
	 * 属性claimcomcode的getter方法
	 */

	@Column(name = "CLAIMCOMCODE")
	public String getClaimcomcode() {
		return this.claimcomcode;
	}

	/**
	 * 属性claimcomcode的setter方法
	 */
	public void setClaimcomcode(String claimcomcode) {
		this.claimcomcode = claimcomcode;
	}

	/**
	 * 属性selectsend的getter方法
	 */

	@Column(name = "SELECTSEND")
	public String getSelectsend() {
		return this.selectsend;
	}

	/**
	 * 属性selectsend的setter方法
	 */
	public void setSelectsend(String selectsend) {
		this.selectsend = selectsend;
	}

	/**
	 * 属性surveytimes的getter方法
	 */

	@Column(name = "SURVEYTIMES")
	public BigDecimal getSurveytimes() {
		return this.surveytimes;
	}

	/**
	 * 属性surveytimes的setter方法
	 */
	public void setSurveytimes(BigDecimal surveytimes) {
		this.surveytimes = surveytimes;
	}

	/**
	 * 属性surveytype的getter方法
	 */

	@Column(name = "SURVEYTYPE")
	public String getSurveytype() {
		return this.surveytype;
	}

	/**
	 * 属性surveytype的setter方法
	 */
	public void setSurveytype(String surveytype) {
		this.surveytype = surveytype;
	}

	/**
	 * 属性checksite的getter方法
	 */

	@Column(name = "CHECKSITE")
	public String getChecksite() {
		return this.checksite;
	}

	/**
	 * 属性checksite的setter方法
	 */
	public void setChecksite(String checksite) {
		this.checksite = checksite;
	}

	/**
	 * 属性licenseno的getter方法
	 */

	@Column(name = "LICENSENO")
	public String getLicenseno() {
		return this.licenseno;
	}

	/**
	 * 属性licenseno的setter方法
	 */
	public void setLicenseno(String licenseno) {
		this.licenseno = licenseno;
	}

	/**
	 * 属性scheduleobjectid的getter方法
	 */

	@Column(name = "SCHEDULEOBJECTID")
	public String getScheduleobjectid() {
		return this.scheduleobjectid;
	}

	/**
	 * 属性scheduleobjectid的setter方法
	 */
	public void setScheduleobjectid(String scheduleobjectid) {
		this.scheduleobjectid = scheduleobjectid;
	}

	/**
	 * 属性scheduleobjectname的getter方法
	 */

	@Column(name = "SCHEDULEOBJECTNAME")
	public String getScheduleobjectname() {
		return this.scheduleobjectname;
	}

	/**
	 * 属性scheduleobjectname的setter方法
	 */
	public void setScheduleobjectname(String scheduleobjectname) {
		this.scheduleobjectname = scheduleobjectname;
	}

	/**
	 * 属性INPUTDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "INPUTDATE")
	public Date getInputDate() {
		return this.inputDate;
	}

	/**
	 * 属性INPUTDATE的setter方法
	 */
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	/**
	 * 属性OPERATORCODE的getter方法
	 */

	@Column(name = "OPERATORCODE")
	public String getOperatorCode() {
		return this.operatorCode;
	}

	/**
	 * 属性OPERATORCODE的setter方法
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * 属性checkoperatorcode的getter方法
	 */

	@Column(name = "CHECKOPERATORCODE")
	public String getCheckoperatorcode() {
		return this.checkoperatorcode;
	}

	/**
	 * 属性checkoperatorcode的setter方法
	 */
	public void setCheckoperatorcode(String checkoperatorcode) {
		this.checkoperatorcode = checkoperatorcode;
	}

	/**
	 * 属性resultinfo的getter方法
	 */

	@Column(name = "RESULTINFO")
	public String getResultinfo() {
		return this.resultinfo;
	}

	/**
	 * 属性resultinfo的setter方法
	 */
	public void setResultinfo(String resultinfo) {
		this.resultinfo = resultinfo;
	}

	/**
	 * 属性bookflag的getter方法
	 */

	@Column(name = "BOOKFLAG")
	public String getBookflag() {
		return this.bookflag;
	}

	/**
	 * 属性bookflag的setter方法
	 */
	public void setBookflag(String bookflag) {
		this.bookflag = bookflag;
	}

	/**
	 * 属性scheduletype的getter方法
	 */

	@Column(name = "SCHEDULETYPE")
	public String getScheduletype() {
		return this.scheduletype;
	}

	/**
	 * 属性scheduletype的setter方法
	 */
	public void setScheduletype(String scheduletype) {
		this.scheduletype = scheduletype;
	}

	/**
	 * 属性checkflag的getter方法
	 */

	@Column(name = "CHECKFLAG")
	public String getCheckflag() {
		return this.checkflag;
	}

	/**
	 * 属性checkflag的setter方法
	 */
	public void setCheckflag(String checkflag) {
		this.checkflag = checkflag;
	}

	/**
	 * 属性checkinfo的getter方法
	 */

	@Column(name = "CHECKINFO")
	public String getCheckinfo() {
		return this.checkinfo;
	}

	/**
	 * 属性checkinfo的setter方法
	 */
	public void setCheckinfo(String checkinfo) {
		this.checkinfo = checkinfo;
	}

	/**
	 * 属性FLAG的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性FLAG的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
