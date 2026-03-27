package cn.com.sinosoft.inf.dict.xmlmsg.getPlan;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadPacket;


public class GetPlanReqPacket {

	private RequestHeadPacket HEAD = new RequestHeadPacket();

	private GetPlanReqBody BODY = new GetPlanReqBody();

	public RequestHeadPacket getHEAD() {
		return HEAD;
	}

	public void setHEAD(RequestHeadPacket head) {
		HEAD = head;
	}

	public GetPlanReqBody getBODY() {
		return BODY;
	}

	public void setBODY(GetPlanReqBody body) {
		BODY = body;
	}


}
