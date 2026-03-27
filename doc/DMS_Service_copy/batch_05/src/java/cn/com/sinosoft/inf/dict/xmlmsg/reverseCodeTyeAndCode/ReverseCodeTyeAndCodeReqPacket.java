package cn.com.sinosoft.inf.dict.xmlmsg.reverseCodeTyeAndCode;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadPacket;


public class ReverseCodeTyeAndCodeReqPacket {

	private RequestHeadPacket HEAD = new RequestHeadPacket();
	private ReverseCodeTyeAndCodeReqBody BODY = new ReverseCodeTyeAndCodeReqBody();
	public RequestHeadPacket getHEAD() {
		return HEAD;
	}

	public void setHEAD(RequestHeadPacket hEAD) {
		HEAD = hEAD;
	}

	public ReverseCodeTyeAndCodeReqBody getBODY() {
		return BODY;
	}

	public void setBODY(ReverseCodeTyeAndCodeReqBody body) {
		BODY = body;
	}
}
