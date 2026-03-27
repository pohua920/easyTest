package cn.com.sinosoft.inf.dict.xmlmsg.getPlanWhetherHasFixed;

import com.sinosoft.dmsdriver.domain.common.RequestHeadPacket;

public class GetPlanWhetherHasFixedReqPacket {

	private RequestHeadPacket HEAD = new RequestHeadPacket();
	
	private GetPlanWhetherHasFixedReqBody BODY = new GetPlanWhetherHasFixedReqBody();

	public RequestHeadPacket getHEAD() {
		return HEAD;
	}

	public void setHEAD(RequestHeadPacket head) {
		HEAD = head;
	}

	public GetPlanWhetherHasFixedReqBody getBODY() {
		return BODY;
	}

	public void setBODY(GetPlanWhetherHasFixedReqBody body) {
		BODY = body;
	}
}
