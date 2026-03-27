package cn.com.sinosoft.inf.dict.xmlmsg.codetranslate;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadPacket;


public class CodeTranslateReqPacket {

	private RequestHeadPacket HEAD = new RequestHeadPacket();
	private CodeTranslateReqBody BODY = new CodeTranslateReqBody();
	public RequestHeadPacket getHEAD() {
		return HEAD;
	}

	public void setHEAD(RequestHeadPacket hEAD) {
		HEAD = hEAD;
	}
	public CodeTranslateReqBody getBODY() {
		return BODY;
	}
	public void setBODY(CodeTranslateReqBody bODY) {
		BODY = bODY;
	}
	
}
