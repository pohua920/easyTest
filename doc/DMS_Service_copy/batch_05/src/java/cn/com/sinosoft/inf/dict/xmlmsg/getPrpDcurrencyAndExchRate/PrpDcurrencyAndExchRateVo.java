package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDcurrencyAndExchRate;

import java.math.BigDecimal;

public class PrpDcurrencyAndExchRateVo implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String currencyCode;
	
	private String currencyCName;
	
	private String currencyEName;
	
	private String accBookCode;
	
	private String newCurrencyCode;

	private String validStatus;

	private String flag;
	
	private BigDecimal exchrate;

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrencyCName() {
		return currencyCName;
	}

	public void setCurrencyCName(String currencyCName) {
		this.currencyCName = currencyCName;
	}

	public String getCurrencyEName() {
		return currencyEName;
	}

	public void setCurrencyEName(String currencyEName) {
		this.currencyEName = currencyEName;
	}

	public String getAccBookCode() {
		return accBookCode;
	}

	public void setAccBookCode(String accBookCode) {
		this.accBookCode = accBookCode;
	}

	public String getNewCurrencyCode() {
		return newCurrencyCode;
	}

	public void setNewCurrencyCode(String newCurrencyCode) {
		this.newCurrencyCode = newCurrencyCode;
	}

	public String getValidStatus() {
		return validStatus;
	}

	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public BigDecimal getExchrate() {
		return exchrate;
	}

	public void setExchrate(BigDecimal exchrate) {
		this.exchrate = exchrate;
	}
	
}
