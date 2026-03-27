/*
 * Created on 2005-6-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.sinosoft.undwrt.common.vo;

import java.io.Serializable;

/**
 * The Class CommonDangerItemInfoVo.
 * 
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class CommonDangerItemInfoVo implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = CommonDangerItemInfoVo.class.getName().hashCode();

    /** 屬性業務號. */
    private String businessNo = "";  //业务号
    
    /** 屬性險種代碼. */
    private String riskCode = "";    //险种代码
    
    /** 屬性危險單位號. */
    private int  dangerNo = 0;        //危险单位主序号
    
    /** 屬性The sinosoft item kind no. */
    private int  itemKindNo = 0;        //危险单位标的子序号
    
    /** 屬性The sinosoft kind flag. */
    private String kindFlag = "";    //险别归类标志
    
    /** 屬性The sinosoft kind code. */
    private String kindCode = "";     //险别代码
    
    /** 屬性The sinosoft kind name. */
    private String kindName = "";     //险别名称
    
    /** 屬性The sinosoft item code. */
    private String itemCode = "";     //标的项目
    
    /** 屬性The sinosoft item detail name. */
    private String itemDetailName = ""; //标的名称
    
    /** 屬性The sinosoft post code. */
    private String postCode ="";          //危险单位标的邮政编码
    
    /** 屬性The sinosoft address code. */
    private String addressCode="";        //投保单标的邮政编码(为使危险单位标的地址和投保单.保单数据兼容使用)
    
    /** 屬性The sinosoft address no. */
    private int  addressNo = 0;           //危险单位标的地址序号
    
    /** 屬性The sinosoft address name. */
    private String addressName = "";      //危险单位地址名称
    
    /** 屬性The sinosoft currency. */
    private String currency = "";         //原币
    
    /** 屬性The sinosoft amount. */
    private double amount = 0D;           //原币保额
    
    /** 屬性The sinosoft chg amount. */
    private double chgAmount= 0D;         //变化保额
    
    /** 屬性The sinosoft premium. */
    private double premium = 0D;          //原币保费
    
    /** 屬性The sinosoft chg premium. */
    private double chgPremium = 0D;
    
    /** 屬性The sinosoft calculate flag. */
    private String calculateFlag = "";    //是否计算保额
    
    /** 屬性The sinosoft currency2. */
    private String currency2 = "";        //折币(与支付币种一致)
    
    /** 屬性The sinosoft exchange rate. */
    private double exchangeRate = 0D;     //兑换率
    
    /** 屬性The sinosoft tol amount. */
    private double tolAmount    =0d;      //投保单,保单的总保额
    
    /** 屬性The sinosoft tol premium. */
    private double tolPremium   =0d;      //投保单，保单的总保费
    
    /** 屬性標志. */
    private String flag = "";
    
    /** 屬性The sinosoft endorse flag. */
    private String endorseFlag = "";      //批改标示
    //add by luyang 意健险用 2005-8-31
    /** 屬性The sinosoft discount. */
    private double discount = 0d;     //折扣
    
    /** 屬性The sinosoft quantity. */
    private double quantity = 0d;     //人数
    
    /** 屬性The sinosoft value. */
    private double value    = 0d;     //份数
    
    /** 屬性The sinosoft chg quantity. */
    private double chgQuantity = 0d;  //人数变化

    //add by douzongxing 每次事故赔偿限额 20081126 begin
    /** 屬性The sinosoft limit03 fee. */
    private double limit03Fee = 0d; //累计责任限额
    
    /** 屬性The sinosoft chg limit03 fee. */
    private double chgLimit03Fee = 0d; //变化累计责任限额
    
    /** 屬性The sinosoft limit fee. */
    private double limitFee = 0d; //每次事故赔偿限额
    
    /** 屬性The sinosoft chg limit fee. */
    private double chgLimitFee = 0d; //变化每次事故赔偿限额
   //add by douzongxing 每次事故赔偿限额 20081126 end 
	/** 屬性The sinosoft risk level. */ 
    private String riskLevel;
    
    /** 屬性The sinosoft risk level desc. */
    private String riskLevelDesc;
    
    /** 屬性The sinosoft risk class. */
    private String riskClass;
    
    /** 屬性風險類別描述. */
    private String riskClassDesc;
    
    /** 屬性The sinosoft retention value. */
    private Double retentionValue;
    
    /** 屬性The sinosoft ret currency. */
    private String retCurrency;
    /** 屬性险别是否临分1:临分,0:不临分,默认为0. */
    private String isFacultative;
    
    private String sameRiskNo;
	
	/**
	 * 獲取屬性the sinosoft chg amount.
	 * 
	 * @return 屬性the sinosoft chg amount的值
	 */
	public double getChgAmount() {
		return chgAmount;
	}
	
	/**
	 * 設置屬性the sinosoft chg amount.
	 * 
	 * @param chgAmount
	 *            The chgAmount to set.
	 */
	public void setChgAmount(double chgAmount) {
		this.chgAmount = chgAmount;
	}
	
	/**
	 * 獲取屬性the sinosoft chg premium.
	 * 
	 * @return Returns the chgPremium.
	 */
	public double getChgPremium() {
		return chgPremium;
	}
	
	/**
	 * 設置屬性the sinosoft chg premium.
	 * 
	 * @param chgPremium
	 *            The chgPremium to set.
	 */
	public void setChgPremium(double chgPremium) {
		this.chgPremium = chgPremium;
	}
    
    /**
	 * 獲取屬性the sinosoft address code.
	 * 
	 * @return Returns the addressCode.
	 */
    public String getAddressCode() {
        return addressCode;
    }
    
    /**
	 * 設置屬性the sinosoft address code.
	 * 
	 * @param addressCode
	 *            The addressCode to set.
	 */
    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }
    
    /**
	 * 獲取屬性the sinosoft tol amount.
	 * 
	 * @return Returns the tolAmount.
	 */
    public double getTolAmount() {
        return tolAmount;
    }
    
    /**
	 * 設置屬性the sinosoft tol amount.
	 * 
	 * @param tolAmount
	 *            The tolAmount to set.
	 */
    public void setTolAmount(double tolAmount) {
        this.tolAmount = tolAmount;
    }
    
    /**
	 * 獲取屬性the sinosoft tol premium.
	 * 
	 * @return Returns the tolPremium.
	 */
    public double getTolPremium() {
        return tolPremium;
    }
    
    /**
	 * 設置屬性the sinosoft tol premium.
	 * 
	 * @param tolPremium
	 *            The tolPremium to set.
	 */
    public void setTolPremium(double tolPremium) {
        this.tolPremium = tolPremium;
    }
    
    /**
	 * 獲取屬性the sinosoft serial version uid.
	 * 
	 * @return Returns the serialVersionUID.
	 */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    
    /**
	 * 獲取屬性the sinosoft address name.
	 * 
	 * @return Returns the addressName.
	 */
    public String getAddressName() {
        return addressName;
    }
    
    /**
	 * 設置屬性the sinosoft address name.
	 * 
	 * @param addressName
	 *            The addressName to set.
	 */
    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }
    
    /**
	 * 獲取屬性the sinosoft address no.
	 * 
	 * @return Returns the addressNo.
	 */
    public int getAddressNo() {
        return addressNo;
    }
    
    /**
	 * 設置屬性the sinosoft address no.
	 * 
	 * @param addressNo
	 *            The addressNo to set.
	 */
    public void setAddressNo(int addressNo) {
        this.addressNo = addressNo;
    }
    
    /**
	 * 獲取屬性the sinosoft amount.
	 * 
	 * @return Returns the amount.
	 */
    public double getAmount() {
        return amount;
    }
    
    /**
	 * 設置屬性the sinosoft amount.
	 * 
	 * @param amount
	 *            The amount to set.
	 */
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    /**
	 * 獲取屬性業務號.
	 * 
	 * @return Returns the businessNo.
	 */
    public String getBusinessNo() {
        return businessNo;
    }
    
    /**
	 * 設置屬性業務號.
	 * 
	 * @param businessNo
	 *            The businessNo to set.
	 */
    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }
    
    /**
	 * 獲取屬性the sinosoft calculate flag.
	 * 
	 * @return Returns the calculateFlag.
	 */
    public String getCalculateFlag() {
        return calculateFlag;
    }
    
    /**
	 * 設置屬性the sinosoft calculate flag.
	 * 
	 * @param calculateFlag
	 *            The calculateFlag to set.
	 */
    public void setCalculateFlag(String calculateFlag) {
        this.calculateFlag = calculateFlag;
    }
    
    /**
	 * 獲取屬性the sinosoft currency.
	 * 
	 * @return Returns the currency.
	 */
    public String getCurrency() {
        return currency;
    }
    
    /**
	 * 設置屬性the sinosoft currency.
	 * 
	 * @param currency
	 *            The currency to set.
	 */
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    /**
	 * 獲取屬性the sinosoft currency2.
	 * 
	 * @return Returns the currency2.
	 */
    public String getCurrency2() {
        return currency2;
    }
    
    /**
	 * 設置屬性the sinosoft currency2.
	 * 
	 * @param currency2
	 *            The currency2 to set.
	 */
    public void setCurrency2(String currency2) {
        this.currency2 = currency2;
    }
    
    /**
	 * 獲取屬性危險單位號.
	 * 
	 * @return Returns the dangerNo.
	 */
    public int getDangerNo() {
        return dangerNo;
    }
    
    /**
	 * 設置屬性危險單位號.
	 * 
	 * @param dangerNo
	 *            The dangerNo to set.
	 */
    public void setDangerNo(int dangerNo) {
        this.dangerNo = dangerNo;
    }
    
    /**
	 * 獲取屬性the sinosoft exchange rate.
	 * 
	 * @return Returns the exchangeRate.
	 */
    public double getExchangeRate() {
        return exchangeRate;
    }
    
    /**
	 * 設置屬性the sinosoft exchange rate.
	 * 
	 * @param exchangeRate
	 *            The exchangeRate to set.
	 */
    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
    
    /**
	 * 獲取屬性標志.
	 * 
	 * @return Returns the flag.
	 */
    public String getFlag() {
        return flag;
    }
    
    /**
	 * 設置屬性標志.
	 * 
	 * @param flag
	 *            The flag to set.
	 */
    public void setFlag(String flag) {
        this.flag = flag;
    }
    
    /**
	 * 獲取屬性the sinosoft item code.
	 * 
	 * @return Returns the itemCode.
	 */
    public String getItemCode() {
        return itemCode;
    }
    
    /**
	 * 設置屬性the sinosoft item code.
	 * 
	 * @param itemCode
	 *            The itemCode to set.
	 */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }
    
    /**
	 * 獲取屬性the sinosoft item detail name.
	 * 
	 * @return Returns the itemDetailName.
	 */
    public String getItemDetailName() {
        return itemDetailName;
    }
    
    /**
	 * 設置屬性the sinosoft item detail name.
	 * 
	 * @param itemDetailName
	 *            The itemDetailName to set.
	 */
    public void setItemDetailName(String itemDetailName) {
        this.itemDetailName = itemDetailName;
    }
    
    /**
	 * 獲取屬性the sinosoft kind code.
	 * 
	 * @return Returns the kindCode.
	 */
    public String getKindCode() {
        return kindCode;
    }
    
    /**
	 * 設置屬性the sinosoft kind code.
	 * 
	 * @param kindCode
	 *            The kindCode to set.
	 */
    public void setKindCode(String kindCode) {
        this.kindCode = kindCode;
    }
    
    /**
	 * 獲取屬性the sinosoft kind flag.
	 * 
	 * @return Returns the kindFlag.
	 */
    public String getKindFlag() {
        return kindFlag;
    }
    
    /**
	 * 設置屬性the sinosoft kind flag.
	 * 
	 * @param kindFlag
	 *            The kindFlag to set.
	 */
    public void setKindFlag(String kindFlag) {
        this.kindFlag = kindFlag;
    }
    
    /**
	 * 獲取屬性the sinosoft kind name.
	 * 
	 * @return Returns the kindName.
	 */
    public String getKindName() {
        return kindName;
    }
    
    /**
	 * 設置屬性the sinosoft kind name.
	 * 
	 * @param kindName
	 *            The kindName to set.
	 */
    public void setKindName(String kindName) {
        this.kindName = kindName;
    }
    
    /**
	 * 獲取屬性the sinosoft post code.
	 * 
	 * @return Returns the postCode.
	 */
    public String getPostCode() {
        return postCode;
    }
    
    /**
	 * 設置屬性the sinosoft post code.
	 * 
	 * @param postCode
	 *            The postCode to set.
	 */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
    
    /**
	 * 獲取屬性the sinosoft premium.
	 * 
	 * @return Returns the premium.
	 */
    public double getPremium() {
        return premium;
    }
    
    /**
	 * 設置屬性the sinosoft premium.
	 * 
	 * @param premium
	 *            The premium to set.
	 */
    public void setPremium(double premium) {
        this.premium = premium;
    }
    
    /**
	 * 獲取屬性險種代碼.
	 * 
	 * @return Returns the riskCode.
	 */
    public String getRiskCode() {
        return riskCode;
    }
    
    /**
	 * 設置屬性險種代碼.
	 * 
	 * @param riskCode
	 *            The riskCode to set.
	 */
    public void setRiskCode(String riskCode) {
        this.riskCode = riskCode;
    }

    /**
	 * 獲取屬性the sinosoft item kind no.
	 * 
	 * @return Returns the itemKindNo.
	 */
    public int getItemKindNo() {
        return itemKindNo;
    }
    
    /**
	 * 設置屬性the sinosoft item kind no.
	 * 
	 * @param itemKindNo
	 *            The itemKindNo to set.
	 */
    public void setItemKindNo(int itemKindNo) {
        this.itemKindNo = itemKindNo;
    }

    /**
	 * 設置屬性the sinosoft discount.
	 * 
	 * @param discount
	 *            待設置的the sinosoft discount的值
	 */
    public void setDiscount(double discount)
    {
      this.discount = discount;
    }

    /**
	 * 獲取屬性the sinosoft discount.
	 * 
	 * @return 屬性the sinosoft discount的值
	 */
    public double getDiscount()
    {
      return this.discount;
    }

    /**
	 * 設置屬性the sinosoft value.
	 * 
	 * @param value
	 *            待設置的the sinosoft value的值
	 */
    public void setValue(double value)
    {
      this.value = value;
    }

    /**
	 * 獲取屬性the sinosoft value.
	 * 
	 * @return 屬性the sinosoft value的值
	 */
    public double getValue ()
    {
      return this.value;
    }

    /**
	 * 設置屬性the sinosoft quantity.
	 * 
	 * @param quantity
	 *            待設置的the sinosoft quantity的值
	 */
    public void setQuantity(double quantity)
    {
      this.quantity = quantity;
    }

    /**
	 * 獲取屬性the sinosoft quantity.
	 * 
	 * @return 屬性the sinosoft quantity的值
	 */
    public double getQuantity()
    {
      return quantity;
    }

    /**
	 * 設置屬性the sinosoft chg quantity.
	 * 
	 * @param chgQuantity
	 *            待設置的the sinosoft chg quantity的值
	 */
    public void setChgQuantity(double chgQuantity)
    {
      this.chgQuantity = chgQuantity;
    }

    /**
	 * 獲取屬性the sinosoft chg quantity.
	 * 
	 * @return 屬性the sinosoft chg quantity的值
	 */
    public double getChgQuantity()
    {
      return chgQuantity;
    }
    
    /**
	 * 設置屬性the sinosoft endorse flag.
	 * 
	 * @param endorseFlag
	 *            待設置的the sinosoft endorse flag的值
	 */
    public void setEndorseFlag(String endorseFlag)
    {
      this.endorseFlag = endorseFlag;
    }

    /**
	 * 獲取屬性the sinosoft endorse flag.
	 * 
	 * @return 屬性the sinosoft endorse flag的值
	 */
    public String getEndorseFlag()
    {
      return endorseFlag;
    }
    
    //add by douzongxing 每次事故赔偿限额 20081126 begin
     
    /**
	 * 設置屬性the sinosoft limit fee.
	 * 
	 * @param limitFee
	 *            待設置的the sinosoft limit fee的值
	 */
    public void setLimitFee(double limitFee)
    {
      this.limitFee = limitFee;
    }

    /**
	 * 獲取屬性the sinosoft limit fee.
	 * 
	 * @return 屬性the sinosoft limit fee的值
	 */
    public double getLimitFee()
    {
      return limitFee;
    }
    
    /**
	 * 設置屬性the sinosoft chg limit fee.
	 * 
	 * @param chgLimitFee
	 *            待設置的the sinosoft chg limit fee的值
	 */
    public void setChgLimitFee(double chgLimitFee)
    {
      this.chgLimitFee = chgLimitFee;
    }

    /**
	 * 獲取屬性the sinosoft chg limit fee.
	 * 
	 * @return 屬性the sinosoft chg limit fee的值
	 */
    public double getChgLimitFee()
    {
      return chgLimitFee;
    }
    
    
    /**
	 * 設置屬性the sinosoft limit03 fee.
	 * 
	 * @param limit03Fee
	 *            待設置的the sinosoft limit03 fee的值
	 */
    public void setLimit03Fee(double limit03Fee)
    {
      this.limit03Fee = limit03Fee;
    }

    /**
	 * 獲取屬性the sinosoft limit03 fee.
	 * 
	 * @return 屬性the sinosoft limit03 fee的值
	 */
    public double getLimit03Fee()
    {
      return limit03Fee;
    }
    
    /**
	 * 設置屬性the sinosoft chg limit03 fee.
	 * 
	 * @param chgLimit03Fee
	 *            待設置的the sinosoft chg limit03 fee的值
	 */
    public void setChgLimit03Fee(double chgLimit03Fee)
    {
      this.chgLimit03Fee = chgLimit03Fee;
    }

    /**
	 * 獲取屬性the sinosoft chg limit03 fee.
	 * 
	 * @return 屬性the sinosoft chg limit03 fee的值
	 */
    public double getChgLimit03Fee()
    {
      return chgLimit03Fee;
    }
   //add by douzongxing 每次事故赔偿限额 20081126 end 
	/**
	 * 獲取屬性the sinosoft risk level.
	 * 
	 * @return 屬性the sinosoft risk level的值
	 */
   public String getRiskLevel() {
		return riskLevel;
	}
	
	/**
	 * 設置屬性the sinosoft risk level.
	 * 
	 * @param riskLevel
	 *            待設置的the sinosoft risk level的值
	 */
	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}
	
	/**
	 * 獲取屬性the sinosoft risk level desc.
	 * 
	 * @return 屬性the sinosoft risk level desc的值
	 */
	public String getRiskLevelDesc() {
		return riskLevelDesc;
	}
	
	/**
	 * 設置屬性the sinosoft risk level desc.
	 * 
	 * @param riskLevelDesc
	 *            待設置的the sinosoft risk level desc的值
	 */
	public void setRiskLevelDesc(String riskLevelDesc) {
		this.riskLevelDesc = riskLevelDesc;
	}
	
	/**
	 * 獲取屬性the sinosoft risk class.
	 * 
	 * @return 屬性the sinosoft risk class的值
	 */
	public String getRiskClass() {
		return riskClass;
	}
	
	/**
	 * 設置屬性the sinosoft risk class.
	 * 
	 * @param riskClass
	 *            待設置的the sinosoft risk class的值
	 */
	public void setRiskClass(String riskClass) {
		this.riskClass = riskClass;
	}
	
	/**
	 * 獲取屬性風險類別描述.
	 * 
	 * @return 屬性風險類別描述的值
	 */
	public String getRiskClassDesc() {
		return riskClassDesc;
	}
	
	/**
	 * 設置屬性風險類別描述.
	 * 
	 * @param riskClassDesc
	 *            待設置的風險類別描述的值
	 */
	public void setRiskClassDesc(String riskClassDesc) {
		this.riskClassDesc = riskClassDesc;
	}
	
	/**
	 * 獲取屬性the sinosoft retention value.
	 * 
	 * @return 屬性the sinosoft retention value的值
	 */
	public Double getRetentionValue() {
		return retentionValue;
	}
	
	/**
	 * 設置屬性the sinosoft retention value.
	 * 
	 * @param retentionValue
	 *            待設置的the sinosoft retention value的值
	 */
	public void setRetentionValue(Double retentionValue) {
		this.retentionValue = retentionValue;
	}
	
	/**
	 * 獲取屬性the sinosoft ret currency.
	 * 
	 * @return 屬性the sinosoft ret currency的值
	 */
	public String getRetCurrency() {
		return retCurrency;
	}
	
	/**
	 * 設置屬性the sinosoft ret currency.
	 * 
	 * @param retCurrency
	 *            待設置的the sinosoft ret currency的值
	 */
	public void setRetCurrency(String retCurrency) {
		this.retCurrency = retCurrency;
	}

	public String getIsFacultative() {
		return isFacultative;
	}

	public void setIsFacultative(String isFacultative) {
		this.isFacultative = isFacultative;
	}

	public String getSameRiskNo() {
		return sameRiskNo;
	}

	public void setSameRiskNo(String sameRiskNo) {
		this.sameRiskNo = sameRiskNo;
	}
	
}
