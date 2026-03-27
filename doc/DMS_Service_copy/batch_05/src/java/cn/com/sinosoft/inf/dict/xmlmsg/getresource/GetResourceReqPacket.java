package cn.com.sinosoft.inf.dict.xmlmsg.getresource;

import com.sinosoft.dmsdriver.domain.common.RequestHeadPacket;

public class GetResourceReqPacket {

	private RequestHeadPacket	HEAD	= new RequestHeadPacket();
	private GetResourceReqBody	BODY	= new GetResourceReqBody();

	public RequestHeadPacket getHEAD() {
		return HEAD;
	}

	public void setHEAD(RequestHeadPacket head) {
		HEAD = head;
	}

	public GetResourceReqBody getBODY() {
		return BODY;
	}

	public void setBODY(GetResourceReqBody body) {
		BODY = body;
	}

}
