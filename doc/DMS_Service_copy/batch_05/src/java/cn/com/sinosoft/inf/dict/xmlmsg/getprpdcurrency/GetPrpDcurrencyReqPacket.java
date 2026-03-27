package cn.com.sinosoft.inf.dict.xmlmsg.getprpdcurrency;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadPacket;


public class GetPrpDcurrencyReqPacket {

	private RequestHeadPacket HEAD = new RequestHeadPacket();
	private GetPrpDcurrencyReqBody BODY = new GetPrpDcurrencyReqBody();
	public RequestHeadPacket getHEAD() {
		return HEAD;
	}

	public void setHEAD(RequestHeadPacket hEAD) {
		HEAD = hEAD;
	}
	public GetPrpDcurrencyReqBody getBODY() {
		return BODY;
	}
	public void setBODY(GetPrpDcurrencyReqBody bODY) {
		BODY = bODY;
	}
	
}
