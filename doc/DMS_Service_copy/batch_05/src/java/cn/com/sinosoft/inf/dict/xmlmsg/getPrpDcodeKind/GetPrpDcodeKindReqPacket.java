package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDcodeKind;

import com.sinosoft.dmsdriver.domain.common.RequestHeadPacket;

public class GetPrpDcodeKindReqPacket {

	private RequestHeadPacket	HEAD	= new RequestHeadPacket();
	private GetPrpDcodeKindReqBody	BODY	= new GetPrpDcodeKindReqBody();

	public RequestHeadPacket getHEAD() {
		return HEAD;
	}

	public void setHEAD(RequestHeadPacket head) {
		HEAD = head;
	}

	public GetPrpDcodeKindReqBody getBODY() {
		return BODY;
	}

	public void setBODY(GetPrpDcodeKindReqBody body) {
		BODY = body;
	}

}
