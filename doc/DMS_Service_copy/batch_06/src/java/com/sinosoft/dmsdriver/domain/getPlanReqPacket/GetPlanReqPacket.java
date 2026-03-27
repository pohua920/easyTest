package com.sinosoft.dmsdriver.domain.getPlanReqPacket;

import com.sinosoft.dmsdriver.domain.common.RequestHeadPacket;


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
