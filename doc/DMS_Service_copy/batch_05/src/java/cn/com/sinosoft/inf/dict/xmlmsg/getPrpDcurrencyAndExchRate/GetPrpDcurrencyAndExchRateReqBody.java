package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDcurrencyAndExchRate;

public class GetPrpDcurrencyAndExchRateReqBody {

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}
	
	private String currencyCode;
	private String currencyName;
	private String validStatus;


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}


	public String getCurrencyCode() {
		return currencyCode;
	}


	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}


	public String getCurrencyName() {
		return currencyName;
	}


	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}


	public String getValidStatus() {
		return validStatus;
	}


	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}
	
}
