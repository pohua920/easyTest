package cn.com.sinosoft.inf.dict.xmlmsg.reverseCodeTyeAndCode;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;


public class ReverseCodeTyeAndCodeResPacket {

	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private ReverseCodeTyeAndCodeResBody BODY = new ReverseCodeTyeAndCodeResBody();

	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(ResponseHeadSchema head) {
		HEAD = head;
	}
	public ReverseCodeTyeAndCodeResBody getBODY() {
		return BODY;
	}
	public void setBODY(ReverseCodeTyeAndCodeResBody body) {
		BODY = body;
	}

}
