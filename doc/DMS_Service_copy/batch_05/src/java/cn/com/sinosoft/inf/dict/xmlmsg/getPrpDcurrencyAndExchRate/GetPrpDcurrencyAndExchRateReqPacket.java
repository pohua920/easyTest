package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDcurrencyAndExchRate;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadPacket;


public class GetPrpDcurrencyAndExchRateReqPacket {

	private RequestHeadPacket HEAD = new RequestHeadPacket();

	private GetPrpDcurrencyAndExchRateReqBody BODY = new GetPrpDcurrencyAndExchRateReqBody();

	public RequestHeadPacket getHEAD() {
		return HEAD;
	}

	public void setHEAD(RequestHeadPacket head) {
		HEAD = head;
	}

	public GetPrpDcurrencyAndExchRateReqBody getBODY() {
		return BODY;
	}

	public void setBODY(GetPrpDcurrencyAndExchRateReqBody body) {
		BODY = body;
	}






}
