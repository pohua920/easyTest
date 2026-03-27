package com.sinosoft.claim.schema.model;

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
 * POJO类PrpLcompelMedical 強制險受害人醫療給付費用收據資料
 */
/**
 * @author 中科软
 */
@Entity
@Table(name = "PRPLCOMPELMEDICAL")
public class PrpLcompelMedical implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	/** 属性id */
	private PrpLcompelMedicalId id;
	/** 受害人姓名 */
	private String personName;
	/** 收據起日 */
	private Date startDate;
	/** 收據迄日 */
	private Date endDate;
	/** 急救費用 */
	private Double a01;
	/** 自行負擔之病房費差額 */
	private Double a021;
	/** 膳食費 */
	private Double a022;
	/** 自行負擔之義肢器材及裝置費用 */
	private Double a023;
	/** 義齒器材及裝置費用 */
	private Double a024;
	/** 義眼器材及裝置費用 */
	private Double a025;
	/** 其他必要之醫療器材 */
	private Double a026;
	/** 部分負擔 */
	private Double a029a;
	/** 掛號費 */
	private Double a029b;
	/** 診斷證明書 */
	private Double a029c;
	/** 依健保緊急自墊醫療費用核退辦法核付診療費用 */
	private Double a029z;
	/** 接送費用 */
	private Double a03;
	/** 看護費用 */
	private Double a04;
	/** 状态 0 ： 待補錄 ； 2-暂存；4-已校核  （實際資料不存在0 的，只是表示一個狀態）*/
	private String status;
	/** 受害人數別 */
	private Integer personNo;
	/** 資料存儲時間 */
	private Date inputDate;
	/** a029 其他診療費用 */
	private Double a029;
	
	private Double healthPoints;
	private Double healthAmount;
	/*
	 * mantis： CLM0046 ，處理人員：BK007 蘇哲，需求單編號：CLM0046強制險醫療明細提醒同一身分證號與日期重複時有提醒與之前重複的賠次-start
	 * 處理過程：
	 *  PrpLcompensateService 新增 verifyPrpLcompelMedical 方法，提供 verifyMedicalDetail Ajax 使用，驗證同一身分證號與日期重複資訊
	 *  DAACompelPrpLcompelMedical.js 提供前台驗證，有輸入時提醒與送出驗證及提醒
	 *  ClaimPrintAction 列印功能新增備註頁面(prpLcompelMedicalComment.jrxml)及調整 項目渲染時的提示 (prpLcompelMedical_subreport0.jrxml)
	 */
	private String vMsg="";
	@Transient
	public String getvMsg() {
		return vMsg;
	}
	public void setvMsg(String vMsg) {
		this.vMsg = vMsg;
	}
	/*
	 * mantis： CLM0046 ，處理人員：BK007 蘇哲，需求單編號：CLM0046強制險醫療明細提醒同一身分證號與日期重複時有提醒與之前重複的賠次 -end
	 */

	/**總計*/
	private Double sumFeeA;
	/**是否以健保身份就醫(Y/N)*/
	private String healthHospitalize;
	
	/**
	 * 类的默认构造方法
	 */
	public PrpLcompelMedical() {
		this.id = new PrpLcompelMedicalId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "compensateNo", column = @Column(name = "COMPENSATENO")), @AttributeOverride(name = "identifyNumber", column = @Column(name = "IDENTIFYNUMBER")),
			@AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpLcompelMedicalId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLcompelMedicalId id) {
		this.id = id;
	}

	@Column(name = "PERSONNAME")
	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "STARTDATE")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ENDDATE")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "A01")
	public Double getA01() {
		return a01;
	}

	public void setA01(Double a01) {
		this.a01 = a01;
	}

	@Column(name = "A021")
	public Double getA021() {
		return a021;
	}

	public void setA021(Double a021) {
		this.a021 = a021;
	}

	@Column(name = "A022")
	public Double getA022() {
		return a022;
	}

	public void setA022(Double a022) {
		this.a022 = a022;
	}

	@Column(name = "A023")
	public Double getA023() {
		return a023;
	}

	public void setA023(Double a023) {
		this.a023 = a023;
	}

	@Column(name = "A024")
	public Double getA024() {
		return a024;
	}

	public void setA024(Double a024) {
		this.a024 = a024;
	}

	@Column(name = "A025")
	public Double getA025() {
		return a025;
	}

	public void setA025(Double a025) {
		this.a025 = a025;
	}

	@Column(name = "A026")
	public Double getA026() {
		return a026;
	}

	public void setA026(Double a026) {
		this.a026 = a026;
	}

	@Column(name = "A029A")
	public Double getA029a() {
		return a029a;
	}

	public void setA029a(Double a029a) {
		this.a029a = a029a;
	}

	@Column(name = "A029B")
	public Double getA029b() {
		return a029b;
	}

	public void setA029b(Double a029b) {
		this.a029b = a029b;
	}

	@Column(name = "A029C")
	public Double getA029c() {
		return a029c;
	}

	public void setA029c(Double a029c) {
		this.a029c = a029c;
	}

	@Column(name = "A029Z")
	public Double getA029z() {
		return a029z;
	}

	public void setA029z(Double a029z) {
		this.a029z = a029z;
	}

	@Column(name = "A03")
	public Double getA03() {
		return a03;
	}

	public void setA03(Double a03) {
		this.a03 = a03;
	}

	@Column(name = "A04")
	public Double getA04() {
		return a04;
	}

	public void setA04(Double a04) {
		this.a04 = a04;
	}

	

	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "PERSONNO")
	public Integer getPersonNo() {
		return personNo;
	}

	public void setPersonNo(Integer personNo) {
		this.personNo = personNo;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "INPUTDATE")
	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	@Transient
	public Double getA029() {
		return a029;
	}

	public void setA029(Double a029) {
		this.a029 = a029;
	}
	@Transient
	public Double getSumFeeA() {
		return sumFeeA;
	}

	public void setSumFeeA(Double sumFeeA) {
		this.sumFeeA = sumFeeA;
	}

	@Column(name = "healthHospitalize")
	public String getHealthHospitalize() {
		return healthHospitalize;
	}

	public void setHealthHospitalize(String healthHospitalize) {
		this.healthHospitalize = healthHospitalize;
	}
	
	@Column(name = "healthPoints")
	public Double getHealthPoints() {
		return healthPoints;
	}

	public void setHealthPoints(Double healthPoints) {
		this.healthPoints = healthPoints;
	}
	@Column(name = "healthAmount")
	public Double getHealthAmount() {
		return healthAmount;
	}

	public void setHealthAmount(Double healthAmount) {
		this.healthAmount = healthAmount;
	}

	/***
	 * 用於校驗受害人的醫療費用加總 是否與 收據費用加總資料一致
	 * @param m
	 * @return
	 */
	public boolean feeEquals(PrpLcompelMedical m){
		return (a01 == null ? 0d : a01.doubleValue()) == (m.getA01() == null ? 0d : m.getA01().doubleValue())
				&& (a01 == null ? 0d : a01.doubleValue()) == (m.getA01() == null ? 0d : m.getA01().doubleValue())
				&& (a021 == null ? 0d : a021.doubleValue()) == (m.getA021() == null ? 0d : m.getA021().doubleValue())
				&& (a022 == null ? 0d : a022.doubleValue()) == (m.getA022() == null ? 0d : m.getA022().doubleValue())
				&& (a023 == null ? 0d : a023.doubleValue()) == (m.getA023() == null ? 0d : m.getA023().doubleValue())
				&& (a024 == null ? 0d : a024.doubleValue()) == (m.getA024() == null ? 0d : m.getA024().doubleValue())
				&& (a025 == null ? 0d : a025.doubleValue()) == (m.getA025() == null ? 0d : m.getA025().doubleValue())
				&& (a026 == null ? 0d : a026.doubleValue()) == (m.getA026() == null ? 0d : m.getA026().doubleValue())
				&& (a029 == null ? 0d : a029.doubleValue()) == (m.getA029() == null ? 0d : m.getA029().doubleValue())
				&& (a03 == null ? 0d : a03.doubleValue()) == (m.getA03() == null ? 0d : m.getA03().doubleValue())
				&& (a04 == null ? 0d : a04.doubleValue()) == (m.getA04() == null ? 0d : m.getA04().doubleValue());
	}
}
