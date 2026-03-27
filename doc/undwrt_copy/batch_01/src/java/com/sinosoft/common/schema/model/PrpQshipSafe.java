package com.sinosoft.common.schema.model;

import java.math.BigDecimal;
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
 * @author bi086
 *
 */
@Entity
@Table(name = "PRPQSHIPSAFE")
public class PrpQshipSafe implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpQshipSafeId id;

	 /** 属性PRPQMAIN */
    private PrpQmain prpQmain;
	
	/** 属性險種代碼 */
	private String riskCode;

	/** 属性標的序號 */
	private Long itemNo;
	
	/** 属性船名 */
	private String shipCName;
	
	/** 属性承保公司 */
	private String companyName;

	/** 属性出险日期 */
	private Date happenDate;

	/** 属性出险地点 */
	private String place;

	/** 属性出险原因 */
	private String reason;

	/** 属性损失金额 */
	private Long lossAmount;

	/** 属性损失次数 */
	private Long lossTimes;

	/** 属性費率 */
	private BigDecimal rate;
	
	/** 属性保费 */
	private BigDecimal premium;
	
	/** 属性標誌 */
	private String flag;
	/**判断出险记录与是否船队*/
	private String isFlag;
	/**
	 * 类prpQplaneSafe的默认构造方法
	 */
	public PrpQshipSafe() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "proposalNo", column = @Column(name = "PROPOSALNO")),
			@AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpQshipSafeId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpQshipSafeId id) {
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
	@Column(name = "SHIPCNAME")
	public String getShipCName() {
		return shipCName;
	}

	public void setShipCName(String shipCName) {
		this.shipCName = shipCName;
	}
	@Column(name = "PLACE")
	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	@Column(name = "LOSSTIMES")
	public Long getLossTimes() {
		return lossTimes;
	}

	public void setLossTimes(Long lossTimes) {
		this.lossTimes = lossTimes;
	}
	
	@Column(name = "RATE")
	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	@Column(name = "PREMIUM")
	public BigDecimal getPremium() {
		return premium;
	}

	public void setPremium(BigDecimal premium) {
		this.premium = premium;
	}
	@Column(name = "ISFLAG")
	public String getIsFlag() {
		return isFlag;
	}

	public void setIsFlag(String isFlag) {
		this.isFlag = isFlag;
	}
}
