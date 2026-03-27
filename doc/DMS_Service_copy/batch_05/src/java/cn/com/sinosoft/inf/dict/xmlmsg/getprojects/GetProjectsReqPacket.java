package cn.com.sinosoft.inf.dict.xmlmsg.getprojects;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadPacket;

public class GetProjectsReqPacket {

	private RequestHeadPacket	HEAD	= new RequestHeadPacket();
	private GetProjectsReqBody	BODY	= new GetProjectsReqBody();

	public RequestHeadPacket getHEAD() {
		return HEAD;
	}

	public void setHEAD(RequestHeadPacket head) {
		HEAD = head;
	}

	public GetProjectsReqBody getBODY() {
		return BODY;
	}

	public void setBODY(GetProjectsReqBody body) {
		BODY = body;
	}

}
