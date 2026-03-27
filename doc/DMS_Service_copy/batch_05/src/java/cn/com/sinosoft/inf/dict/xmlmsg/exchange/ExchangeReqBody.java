package cn.com.sinosoft.inf.dict.xmlmsg.exchange;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class ExchangeReqBody implements SchemaNode {
	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private String CURRDATE = "";
	private String BASECURRENCY= "";
	private String EXCHCURRENCY= "";
	private String AMOUNT= "";
	public String getCURRDATE() {
		return CURRDATE;
	}
	public void setCURRDATE(String CURRDATE) {
		this.CURRDATE = CURRDATE;
	}
	public String getBASECURRENCY() {
		return BASECURRENCY;
	}
	public void setBASECURRENCY(String BASECURRENCY) {
		this.BASECURRENCY = BASECURRENCY;
	}
	public String getEXCHCURRENCY() {
		return EXCHCURRENCY;
	}
	public void setEXCHCURRENCY(String EXCHCURRENCY) {
		this.EXCHCURRENCY = EXCHCURRENCY;
	}
	public String getAMOUNT() {
		return AMOUNT;
	}
	public void setAMOUNT(String AMOUNT) {
		this.AMOUNT = AMOUNT;
	}
	
}
