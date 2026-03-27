package cn.com.sinosoft.inf.dict.xmlmsg.reverseCoTyeAndCode;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadPacket;


public class ReverseCoTyeAndCodeReqPacket {

	private RequestHeadPacket HEAD = new RequestHeadPacket();
	private ReverseCoTyeAndCodeReqBody BODY = new ReverseCoTyeAndCodeReqBody();
	public RequestHeadPacket getHEAD() {
		return HEAD;
	}

	public void setHEAD(RequestHeadPacket hEAD) {
		HEAD = hEAD;
	}

	public ReverseCoTyeAndCodeReqBody getBODY() {
		return BODY;
	}

	public void setBODY(ReverseCoTyeAndCodeReqBody body) {
		BODY = body;
	}
}
