package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDdisaster;



public class PrpDdisasterReqPacket {
	
	private RequestHeadPacket HEAD = new RequestHeadPacket();
	
	private PrpDdisasterReqBody BODY = new PrpDdisasterReqBody();
	
	
	public RequestHeadPacket getHEAD() {
		return HEAD;
	}
	public void setHEAD(RequestHeadPacket head) {
		HEAD = head;
	}
	public PrpDdisasterReqBody getBODY() {
		return BODY;
	}
	public void setBODY(PrpDdisasterReqBody body) {
		BODY = body;
	}
	

}
