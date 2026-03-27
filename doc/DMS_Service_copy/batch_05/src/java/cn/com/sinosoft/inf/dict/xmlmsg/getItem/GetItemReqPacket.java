package cn.com.sinosoft.inf.dict.xmlmsg.getItem;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadPacket;

public class GetItemReqPacket {

	private RequestHeadPacket	HEAD	= new RequestHeadPacket();
	private GetItemReqBody	BODY	= new GetItemReqBody();

	public RequestHeadPacket getHEAD() {
		return HEAD;
	}

	public void setHEAD(RequestHeadPacket head) {
		HEAD = head;
	}

	public GetItemReqBody getBODY() {
		return BODY;
	}

	public void setBODY(GetItemReqBody body) {
		BODY = body;
	}

}
