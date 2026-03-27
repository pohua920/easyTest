package cn.com.sinosoft.inf.dict.xmlmsg.getprpdcurrency;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;


public class GetPrpDcurrencyResPacket {

	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private GetPrpDcurrencyResBody BODY = new GetPrpDcurrencyResBody();
	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(ResponseHeadSchema hEAD) {
		HEAD = hEAD;
	}
	public GetPrpDcurrencyResBody getBODY() {
		return BODY;
	}
	public void setBODY(GetPrpDcurrencyResBody bODY) {
		BODY = bODY;
	}
	
}
