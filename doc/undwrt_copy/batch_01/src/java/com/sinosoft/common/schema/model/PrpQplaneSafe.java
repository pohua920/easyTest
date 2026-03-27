package com.sinosoft.common.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

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

/**
 * mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業
 */
@Entity
@Table(name = "PRPQPLANESAFE")
public class PrpQplaneSafe implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpQplaneSafeId id;

	 /** 属性PRPQMAIN */
    private PrpQmain prpQmain;
	
	/** 属性險種代碼 */
	private String riskCode;

	/** 属性標的序號 */
	private Long itemNo;

	/** 属性所有者公司 */
	private String companyName;

	/** 属性日期 */
	private Date happenDate;

	/** 属性原因 */
	private String reason;

	/** 属性损失金额 */
	private Long lossAmount;

	/** 属性標誌 */
	private String flag;

	/**
	 * 类prpQplaneSafe的默认构造方法
	 */
	public PrpQplaneSafe() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "proposalNo", column = @Column(name = "PROPOSALNO")),
			@AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpQplaneSafeId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpQplaneSafeId id) {
		this.id = id;
	}

    /**
     * 属性PRPQMAIN的getter方法
     */ 
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PROPOSALNO", nullable=false, insertable=false, updatable=false)

    public PrpQmain getPrpQmain() {
        return this.prpQmain;
    }
    /**
     * 属性PRPQMAIN的setter方法
     */
    public void setPrpQmain(PrpQmain prpQmain) {
        this.prpQmain = prpQmain;
    }
	
    /**
	 * 属性險種代碼的getter方法
	 */

	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return this.riskCode;
	}

	/**
	 * 属性險種代碼的setter方法
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	/**
	 * 属性標的序號的getter方法
	 */

	@Column(name = "ITEMNO")
	public Long getItemNo() {
		return this.itemNo;
	}

	/**
	 * 属性標的序號的setter方法
	 */
	public void setItemNo(Long itemNo) {
		this.itemNo = itemNo;
	}

	/**
	 * 属性所有者公司的getter方法
	 */

	@Column(name = "COMPANYNAME")
	public String getCompanyName() {
		return this.companyName;
	}

	/**
	 * 属性所有者公司的setter方法
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * 属性日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "HAPPENDATE")
	public Date getHappenDate() {
		return this.happenDate;
	}

	/**
	 * 属性日期的setter方法
	 */
	public void setHappenDate(Date happenDate) {
		this.happenDate = happenDate;
	}

	/**
	 * 属性原因的getter方法
	 */

	@Column(name = "REASON")
	public String getReason() {
		return this.reason;
	}

	/**
	 * 属性原因的setter方法
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * 属性损失金额的getter方法
	 */

	@Column(name = "LOSSAMOUNT")
	public Long getLossAmount() {
		return this.lossAmount;
	}

	/**
	 * 属性损失金额的setter方法
	 */
	public void setLossAmount(Long lossAmount) {
		this.lossAmount = lossAmount;
	}

	/**
	 * 属性標誌的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性標誌的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
