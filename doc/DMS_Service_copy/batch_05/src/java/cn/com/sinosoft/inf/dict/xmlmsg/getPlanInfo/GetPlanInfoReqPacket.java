package cn.com.sinosoft.inf.dict.xmlmsg.getPlanInfo;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadPacket;



public class GetPlanInfoReqPacket {
	
	private RequestHeadPacket HEAD = new RequestHeadPacket();
	
	private GetPlanInfoReqBody BODY = new GetPlanInfoReqBody();
	
	
	public RequestHeadPacket getHEAD() {
		return HEAD;
	}
	public void setHEAD(RequestHeadPacket head) {
		HEAD = head;
	}
	public GetPlanInfoReqBody getBODY() {
		return BODY;
	}
	public void setBODY(GetPlanInfoReqBody body) {
		BODY = body;
	}
	

}
