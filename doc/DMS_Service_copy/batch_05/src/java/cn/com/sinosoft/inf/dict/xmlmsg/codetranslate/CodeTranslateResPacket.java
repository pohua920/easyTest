package cn.com.sinosoft.inf.dict.xmlmsg.codetranslate;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;



public class CodeTranslateResPacket {

	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private CodeTranslateResBody BODY = new CodeTranslateResBody();
	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(ResponseHeadSchema hEAD) {
		HEAD = hEAD;
	}
	public CodeTranslateResBody getBODY() {
		return BODY;
	}
	public void setBODY(CodeTranslateResBody bODY) {
		BODY = bODY;
	}
	
}
