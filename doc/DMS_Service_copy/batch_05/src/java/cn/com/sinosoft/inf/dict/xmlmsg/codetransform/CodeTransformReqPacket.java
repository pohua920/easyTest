package cn.com.sinosoft.inf.dict.xmlmsg.codetransform;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadPacket;


public class CodeTransformReqPacket {

	private RequestHeadPacket HEAD = new RequestHeadPacket();
	private CodeTransformReqBody BODY = new CodeTransformReqBody();
	public RequestHeadPacket getHEAD() {
		return HEAD;
	}
	public void setHEAD(RequestHeadPacket hEAD) {
		HEAD = hEAD;
	}
	public CodeTransformReqBody getBODY() {
		return BODY;
	}
	public void setBODY(CodeTransformReqBody bODY) {
		BODY = bODY;
	}
	
}
