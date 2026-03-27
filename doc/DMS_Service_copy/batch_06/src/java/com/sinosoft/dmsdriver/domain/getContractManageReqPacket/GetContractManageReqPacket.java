package com.sinosoft.dmsdriver.domain.getContractManageReqPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadPacket;


public class GetContractManageReqPacket {

	private RequestHeadPacket HEAD = new RequestHeadPacket();

	private GetContractManageReqBody BODY = new GetContractManageReqBody();

	public RequestHeadPacket getHEAD() {
		return HEAD;
	}

	public void setHEAD(RequestHeadPacket head) {
		HEAD = head;
	}

	public GetContractManageReqBody getBODY() {
		return BODY;
	}

	public void setBODY(GetContractManageReqBody body) {
		BODY = body;
	}

}
