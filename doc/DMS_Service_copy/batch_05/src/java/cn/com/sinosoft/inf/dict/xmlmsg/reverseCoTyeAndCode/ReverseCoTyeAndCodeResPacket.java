package cn.com.sinosoft.inf.dict.xmlmsg.reverseCoTyeAndCode;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;


public class ReverseCoTyeAndCodeResPacket {

	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private ReverseCoTyeAndCodeResBody BODY = new ReverseCoTyeAndCodeResBody();

	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(ResponseHeadSchema head) {
		HEAD = head;
	}
	public ReverseCoTyeAndCodeResBody getBODY() {
		return BODY;
	}
	public void setBODY(ReverseCoTyeAndCodeResBody body) {
		BODY = body;
	}

}
