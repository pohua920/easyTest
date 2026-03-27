package cn.com.sinosoft.inf.dict.xmlmsg.getTradeCodes;

public class GetTradeCodesReqBody {

	private static final long	serialVersionUID	= 1L;

	public void validate() throws Exception {
	}

	private String	upperCode = "";
	private String	riskCode = "";

	public String getUpperCode() {
		return upperCode;
	}
	public void setUpperCode(String upperCode) {
		this.upperCode = upperCode;
	}
	public String getRiskCode() {
		return riskCode;
	}
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

}
