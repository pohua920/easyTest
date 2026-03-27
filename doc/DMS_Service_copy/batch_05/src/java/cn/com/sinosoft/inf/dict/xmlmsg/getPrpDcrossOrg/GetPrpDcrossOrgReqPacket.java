package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDcrossOrg;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadPacket;


public class GetPrpDcrossOrgReqPacket {

	private RequestHeadPacket HEAD = new RequestHeadPacket();

	private GetPrpDcrossOrgReqBody BODY = new GetPrpDcrossOrgReqBody();

	public RequestHeadPacket getHEAD() {
		return HEAD;
	}

	public void setHEAD(RequestHeadPacket head) {
		HEAD = head;
	}

	public GetPrpDcrossOrgReqBody getBODY() {
		return BODY;
	}

	public void setBODY(GetPrpDcrossOrgReqBody body) {
		BODY = body;
	}





}
