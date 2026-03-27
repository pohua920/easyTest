package cn.com.sinosoft.inf.dict.xmlmsg.risktransform;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadPacket;


public class RiskTransformReqPacket {

	private RequestHeadPacket HEAD = new RequestHeadPacket();
	private RiskTransformReqBody BODY = new RiskTransformReqBody();
	public RequestHeadPacket getHEAD() {
		return HEAD;
	}
	public void setHEAD(RequestHeadPacket hEAD) {
		HEAD = hEAD;
	}
	public RiskTransformReqBody getBODY() {
		return BODY;
	}
	public void setBODY(RiskTransformReqBody bODY) {
		BODY = bODY;
	}
	
}
