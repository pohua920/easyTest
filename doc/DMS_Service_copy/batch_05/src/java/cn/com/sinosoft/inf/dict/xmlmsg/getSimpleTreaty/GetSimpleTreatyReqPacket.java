package cn.com.sinosoft.inf.dict.xmlmsg.getSimpleTreaty;

import com.sinosoft.dmsdriver.domain.common.RequestHeadPacket;

public class GetSimpleTreatyReqPacket {

	private RequestHeadPacket	HEAD	= new RequestHeadPacket();
	private GetSimpleTreatyReqBody	BODY	= new GetSimpleTreatyReqBody();

	public RequestHeadPacket getHEAD() {
		return HEAD;
	}

	public void setHEAD(RequestHeadPacket head) {
		HEAD = head;
	}

	public GetSimpleTreatyReqBody getBODY() {
		return BODY;
	}

	public void setBODY(GetSimpleTreatyReqBody body) {
		BODY = body;
	}

}
