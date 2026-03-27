package cn.com.sinosoft.inf.dict.xmlmsg.getTradeCodes;

import com.sinosoft.dmsdriver.domain.common.RequestHeadPacket;

public class GetTradeCodesReqPacket {

	private RequestHeadPacket	HEAD	= new RequestHeadPacket();
	private GetTradeCodesReqBody	BODY	= new GetTradeCodesReqBody();

	public RequestHeadPacket getHEAD() {
		return HEAD;
	}

	public void setHEAD(RequestHeadPacket head) {
		HEAD = head;
	}

	public GetTradeCodesReqBody getBODY() {
		return BODY;
	}

	public void setBODY(GetTradeCodesReqBody body) {
		BODY = body;
	}

}
