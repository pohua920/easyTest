package com.sinosoft.common.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

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
 */
@Entity
@Table(name = "PRPTPLANEDEVICE")
public class PrpTplaneDevice implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpTplaneDeviceId id;

	 /** 属性PRPTMAIN */
    private PrpTmain prpTmain;
	
	/** 属性險種代碼 */
	private String riskCode;

	/** 属性標的序號 */
	private Long itemNo;

	/** 属性属性设备类型 */
	private String deviceType;

	/** 属性属性设备名称 */
	private String deviceName;

	/** 属性属性新件购置价 */
	private BigDecimal purchasePrice;

	/** 属性属性实际价值 */
	private BigDecimal actualValue;

	/** 属性備註 */
	private String remark;

	/** 属性標誌 */
	private String flag;

	/** 属性購買日期 */
	private Date buyDate;

	/**
	 * 类prpTplaneDevice的默认构造方法
	 */
	public PrpTplaneDevice() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "proposalNo", column = @Column(name = "PROPOSALNO")),
			@AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpTplaneDeviceId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpTplaneDeviceId id) {
		this.id = id;
	}

    /**
     * 属性PRPTMAIN的getter方法
     */ 
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PROPOSALNO", nullable=false, insertable=false, updatable=false)

    public PrpTmain getPrpTmain() {
        return this.prpTmain;
    }
    /**
     * 属性PRPTMAIN的setter方法
     */
    public void setPrpTmain(PrpTmain prpTmain) {
        this.prpTmain = prpTmain;
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
	 * 属性属性设备类型的getter方法
	 */

	@Column(name = "DEVICETYPE")
	public String getDeviceType() {
		return this.deviceType;
	}

	/**
	 * 属性属性设备类型的setter方法
	 */
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * 属性属性设备名称的getter方法
	 */

	@Column(name = "DEVICENAME")
	public String getDeviceName() {
		return this.deviceName;
	}

	/**
	 * 属性属性设备名称的setter方法
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	/**
	 * 属性属性新件购置价的getter方法
	 */

	@Column(name = "PURCHASEPRICE")
	public BigDecimal getPurchasePrice() {
		return this.purchasePrice;
	}

	/**
	 * 属性属性新件购置价的setter方法
	 */
	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	/**
	 * 属性属性实际价值的getter方法
	 */

	@Column(name = "ACTUALVALUE")
	public BigDecimal getActualValue() {
		return this.actualValue;
	}

	/**
	 * 属性属性实际价值的setter方法
	 */
	public void setActualValue(BigDecimal actualValue) {
		this.actualValue = actualValue;
	}

	/**
	 * 属性備註的getter方法
	 */

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 属性備註的setter方法
	 */
	public void setRemark(String remark) {
		this.remark = remark;
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

	/**
	 * 属性購買日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "BUYDATE")
	public Date getBuyDate() {
		return this.buyDate;
	}

	/**
	 * 属性購買日期的setter方法
	 */
	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}

}
