package cn.com.sinosoft.inf.dict.xmlmsg.GetIdentity;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadPacket;


public class GetIdentityReqPacket {

	private RequestHeadPacket HEAD = new RequestHeadPacket();

	private GetIdentityReqBody BODY = new GetIdentityReqBody();

	public RequestHeadPacket getHEAD() {
		return HEAD;
	}

	public void setHEAD(RequestHeadPacket head) {
		HEAD = head;
	}

	public GetIdentityReqBody getBODY() {
		return BODY;
	}

	public void setBODY(GetIdentityReqBody body) {
		BODY = body;
	}



}
