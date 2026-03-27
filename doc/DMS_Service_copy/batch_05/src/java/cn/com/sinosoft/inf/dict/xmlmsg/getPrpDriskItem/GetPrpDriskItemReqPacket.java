package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDriskItem;

import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDdisaster.RequestHeadPacket;

public class GetPrpDriskItemReqPacket {
	private RequestHeadPacket HEAD = new RequestHeadPacket();
	
	private GetPrpDriskItemReqBody BODY = new GetPrpDriskItemReqBody();
	
	
	public RequestHeadPacket getHEAD() {
		return HEAD;
	}
	public void setHEAD(RequestHeadPacket head) {
		HEAD = head;
	}
	public GetPrpDriskItemReqBody getBODY() {
		return BODY;
	}
	public void setBODY(GetPrpDriskItemReqBody body) {
		BODY = body;
	}
}
