// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。
package com.sinosoft.common.schema.model;
import java.math.BigDecimal;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業
 */
@Entity
@Table(name="PRPQCARGOITEM"
)
public class PrpQCargoItem  implements java.io.Serializable {
    private static final long serialVersionUID = 1L;


   
 /** 属性id */
     private PrpQCargoItemId id;
   
 /** 属性货物大类代码 */
     private String cargoBigTypeCode;
   
 /** 属性货物大类名称 */
     private String cargoBigTypeDesc;
   
 /** 属性货物小类代码 */
     private String cargoSmallTypeCode;
   
 /** 属性货物小类名称 */
     private String cargoSmallTypeDesc;
   
 /** 属性货物名称 */
     private String cargoName;
   
 /** 属性装载方式 */
     private String loadWay;
   
 /** 属性免赔额 */
     private BigDecimal deductible;
   
 /** 属性免赔率 */
     private BigDecimal deductibleRate;
   
 /** 属性费率 */
     private BigDecimal rate;
     /** 属性费率 */
     private String everyHighestPrice;
 /** 属性备注 */
     private String remark;
   
 /** 属性特别约定 */
     private String agreement;
   
 /** 属性短信转存后是否删除标记 */
     private String flag;
     /**属性兵险费率*/
     private BigDecimal soliderRate;
     /**属性逾龄加费*/
     private BigDecimal withoutTimeFee;
  
     /**属性发票金额*/
     private String invoiceAmount;
     /**属性币别*/
     private String currency;
     /**属性投保加成*/
     private String insureBonus;
     /**属性保险金额*/
     private String amount;
     /**属性总费率*/
     private String totalRate;
     /**属性兑换率*/
     private String exchangeRate; 
     /**属性保费*/
     private String premium; 
     /** 属性货品单价 */
     private String cargoPrice;
     /** 属性货品最高价 */
     private String cargoHighestPrice;
     /** 属性货品平均价*/
     private String cargoAvgPrice;
     /** 属性货品描述 */
     private String product;
     /** 属性货品包装方式 */
     private String packWay;
     
     /**原始货物名称*/
     private String baseCargoName;
     /**原始发票金额*/
     private String baseInvoiceAmount;
     
     // add by yjm 水险字段添加
     /**邮编区号*/
     private String postCode;
     
     /**地址*/
     private String postAddress;
     
     /**是否有消防设备*/
     private String isHaveFireControl;
     
     /**是否有监视系统*/
     private String isHaveMonitor;
     
     /**是否露天放置*/
     private String isSetOpen;
     
          /**
     * 属性PrpCmain
     */ 
    private PrpQmain prpQmain;
    
       /**
     * 属性PrpCmain的getter方法
     */ 
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PROPOSALNO", nullable=false, insertable=false, updatable=false)

    public PrpQmain getPrpQmain() {
        return this.prpQmain;
    }
    /**
     * 属性PRPCMAIN的setter方法
     */
    public void setPrpQmain(PrpQmain prpQmain) {
        this.prpQmain = prpQmain;
    }

	/**
	 * 类PrpCCargoItem的默认构造方法
	 */
    public PrpQCargoItem() {
    }

   
    /**
     * 属性id的getter方法
     */      @EmbeddedId

    
    @AttributeOverrides( {
        @AttributeOverride(name="proposalNo", column=@Column(name="PROPOSALNO") ), 
        @AttributeOverride(name="teamNo", column=@Column(name="TEAMNO") ), 
        @AttributeOverride(name="lineNo", column=@Column(name="LINENO") ) } )

    public PrpQCargoItemId getId() {
        return this.id;
    }
    /**
     * 属性id的setter方法
     */
    public void setId(PrpQCargoItemId id) {
        this.id = id;
    }
    /**
     * 属性货物大类代码的getter方法
     */ 
    
    @Column(name="CARGOBIGTYPECODE")

    public String getCargoBigTypeCode() {
        return this.cargoBigTypeCode;
    }
    /**
     * 属性货物大类代码的setter方法
     */
    public void setCargoBigTypeCode(String cargoBigTypeCode) {
        this.cargoBigTypeCode = cargoBigTypeCode;
    }
    /**
     * 属性货物大类名称的getter方法
     */ 
    
    @Column(name="CARGOBIGTYPEDESC")

    public String getCargoBigTypeDesc() {
        return this.cargoBigTypeDesc;
    }
    /**
     * 属性货物大类名称的setter方法
     */
    public void setCargoBigTypeDesc(String cargoBigTypeDesc) {
        this.cargoBigTypeDesc = cargoBigTypeDesc;
    }
    /**
     * 属性货物小类代码的getter方法
     */ 
    
    @Column(name="CARGOSMALLTYPECODE")

    public String getCargoSmallTypeCode() {
        return this.cargoSmallTypeCode;
    }
    /**
     * 属性货物小类代码的setter方法
     */
    public void setCargoSmallTypeCode(String cargoSmallTypeCode) {
        this.cargoSmallTypeCode = cargoSmallTypeCode;
    }
    /**
     * 属性货物小类名称的getter方法
     */ 
    
    @Column(name="CARGOSMALLTYPEDESC")

    public String getCargoSmallTypeDesc() {
        return this.cargoSmallTypeDesc;
    }
    /**
     * 属性货物小类名称的setter方法
     */
    public void setCargoSmallTypeDesc(String cargoSmallTypeDesc) {
        this.cargoSmallTypeDesc = cargoSmallTypeDesc;
    }
    /**
     * 属性货物名称的getter方法
     */ 
    
    @Column(name="CARGONAME")

    public String getCargoName() {
        return this.cargoName;
    }
    /**
     * 属性货物名称的setter方法
     */
    public void setCargoName(String cargoName) {
        this.cargoName = cargoName;
    }
    /**
     * 属性装载方式的getter方法
     */ 
    
    @Column(name="LOADWAY")

    public String getLoadWay() {
        return this.loadWay;
    }
    /**
     * 属性装载方式的setter方法
     */
    public void setLoadWay(String loadWay) {
        this.loadWay = loadWay;
    }
    /**
     * 属性免赔额的getter方法
     */ 
    
    @Column(name="DEDUCTIBLE")

    public BigDecimal getDeductible() {
        return this.deductible;
    }
    /**
     * 属性免赔额的setter方法
     */
    public void setDeductible(BigDecimal deductible) {
        this.deductible = deductible;
    }
    /**
     * 属性免赔率的getter方法
     */ 
    
    @Column(name="DEDUCTIBLERATE")

    public BigDecimal getDeductibleRate() {
        return this.deductibleRate;
    }
    /**
     * 属性免赔率的setter方法
     */
    public void setDeductibleRate(BigDecimal deductibleRate) {
        this.deductibleRate = deductibleRate;
    }
    /**
     * 属性费率的getter方法
     */ 
    
    @Column(name="RATE")

    public BigDecimal getRate() {
        return this.rate;
    }
    /**
     * 属性费率的setter方法
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
    /**
     * 属性备注的getter方法
     */ 
    
    @Column(name="REMARK")

    public String getRemark() {
        return this.remark;
    }
    /**
     * 属性备注的setter方法
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    /**
     * 属性特别约定的getter方法
     */ 
    
    @Column(name="AGREEMENT")

    public String getAgreement() {
        return this.agreement;
    }
    /**
     * 属性特别约定的setter方法
     */
    public void setAgreement(String agreement) {
        this.agreement = agreement;
    }
    /**
     * 属性短信转存后是否删除标记的getter方法
     */ 
    
    @Column(name="FLAG")

    public String getFlag() {
        return this.flag;
    }
    /**
     * 属性短信转存后是否删除标记的setter方法
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }
    /**
     * soliderRate.
     *
     * @return  the soliderRate
     * @since   JDK 1.7
     */
    @Column(name="SOLIDERRATE")
    public BigDecimal getSoliderRate() {
        return soliderRate;
    }
    /**
     * soliderRate.
     *
     * @param   soliderRate    the soliderRate to set
     * @since   JDK 1.7
     */
    public void setSoliderRate(BigDecimal soliderRate) {
        this.soliderRate = soliderRate;
    }
    /**
     * withoutTimeFee.
     *
     * @return  the withoutTimeFee
     * @since   JDK 1.7
     */
    @Column(name="WITHOUTTIMEFEE")
    public BigDecimal getWithoutTimeFee() {
        return withoutTimeFee;
    }
    /**
     * withoutTimeFee.
     *
     * @param   withoutTimeFee    the withoutTimeFee to set
     * @since   JDK 1.7
     */
    public void setWithoutTimeFee(BigDecimal withoutTimeFee) {
        this.withoutTimeFee = withoutTimeFee;
    }
    /**
     * invoiceAmount.
     *
     * @return  the invoiceAmount
     * @since   JDK 1.7
     */
    @Column(name="INVOICEAMOUNT")
    public String getInvoiceAmount() {
        return invoiceAmount;
    }
    /**
     * invoiceAmount.
     *
     * @param   invoiceAmount    the invoiceAmount to set
     * @since   JDK 1.7
     */
    public void setInvoiceAmount(String invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }
    /**
     * currency.
     *
     * @return  the currency
     * @since   JDK 1.7
     */
    @Column(name="CURRENCY")
    public String getCurrency() {
        return currency;
    }
    /**
     * currency.
     *
     * @param   currency    the currency to set
     * @since   JDK 1.7
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    /**
     * insureBonus.
     *
     * @return  the insureBonus
     * @since   JDK 1.7
     */
    @Column(name="INSUREBONUS")
    public String getInsureBonus() {
        return insureBonus;
    }
    /**
     * insureBonus.
     *
     * @param   insureBonus    the insureBonus to set
     * @since   JDK 1.7
     */
    public void setInsureBonus(String insureBonus) {
        this.insureBonus = insureBonus;
    }
    /**
     * amount.
     *
     * @return  the amount
     * @since   JDK 1.7
     */
    @Column(name="AMOUNT")
    public String getAmount() {
        return amount;
    }
    /**
     * amount.
     *
     * @param   amount    the amount to set
     * @since   JDK 1.7
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }
    /**
     * totalRate.
     *
     * @return  the totalRate
     * @since   JDK 1.7
     */
    @Column(name="TOTALRATE")
    public String getTotalRate() {
        return totalRate;
    }
    /**
     * totalRate.
     *
     * @param   totalRate    the totalRate to set
     * @since   JDK 1.7
     */
    public void setTotalRate(String totalRate) {
        this.totalRate = totalRate;
    }
    /**
     * exchangeRate.
     *
     * @return  the exchangeRate
     * @since   JDK 1.7
     */
    @Column(name="EXCHANGERATE")
    public String getExchangeRate() {
        return exchangeRate;
    }
    /**
     * exchangeRate.
     *
     * @param   exchangeRate    the exchangeRate to set
     * @since   JDK 1.7
     */
    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
    /**
     * premium.
     *
     * @return  the premium
     * @since   JDK 1.7
     */
    @Column(name="PREMIUM")
    public String getPremium() {
        return premium;
    }
    /**
     * premium.
     *
     * @param   premium    the premium to set
     * @since   JDK 1.7
     */
    public void setPremium(String premium) {
        this.premium = premium;
    }

    @Column(name="CARGOPRICE")
    public String getCargoPrice() {
		return cargoPrice;
	}
	public void setCargoPrice(String cargoPrice) {
		this.cargoPrice = cargoPrice;
	}
	
	@Column(name="CARGOHIGHESTPRICE")
	public String getCargoHighestPrice() {
		return cargoHighestPrice;
	}
	public void setCargoHighestPrice(String cargoHighestPrice) {
		this.cargoHighestPrice = cargoHighestPrice;
	}
	
	@Column(name="CARGOAVGPRICE")
	public String getCargoAvgPrice() {
		return cargoAvgPrice;
	}
	public void setCargoAvgPrice(String cargoAvgPrice) {
		this.cargoAvgPrice = cargoAvgPrice;
	}
	
	@Column(name="PRODUCT")
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	
	@Column(name="PACKWAY")
	public String getPackWay() {
		return packWay;
	}
	public void setPackWay(String packWay) {
		this.packWay = packWay;
	}

	@Column(name="EVERYHIGHESTPRICE")
	public String getEveryHighestPrice() {
		return everyHighestPrice;
	}
	public void setEveryHighestPrice(String everyHighestPrice) {
		this.everyHighestPrice = everyHighestPrice;
	}

	@Column(name="BASECARGONAME")
	public String getBaseCargoName() {
		return baseCargoName;
	}
	public void setBaseCargoName(String baseCargoName) {
		this.baseCargoName = baseCargoName;
	}
	
	@Column(name="BASEINVOICEAMOUNT")
	public String getBaseInvoiceAmount() {
		return baseInvoiceAmount;
	}
	public void setBaseInvoiceAmount(String baseInvoiceAmount) {
		this.baseInvoiceAmount = baseInvoiceAmount;
	}
	
 	@Column(name="POSTCODE")
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	
	@Column(name="POSTADDRESS")
	public String getPostAddress() {
		return postAddress;
	}
	public void setPostAddress(String postAddress) {
		this.postAddress = postAddress;
	}
	
	@Column(name="ISHAVEFIRECONTROL")
	public String getIsHaveFireControl() {
		return isHaveFireControl;
	}
	public void setIsHaveFireControl(String isHaveFireControl) {
		this.isHaveFireControl = isHaveFireControl;
	}
	
	@Column(name="ISHAVEMONITOR")
	public String getIsHaveMonitor() {
		return isHaveMonitor;
	}
	public void setIsHaveMonitor(String isHaveMonitor) {
		this.isHaveMonitor = isHaveMonitor;
	}
	
	@Column(name="ISSETOPEN")
	public String getIsSetOpen() {
		return isSetOpen;
	}
	public void setIsSetOpen(String isSetOpen) {
		this.isSetOpen = isSetOpen;
	}
}


