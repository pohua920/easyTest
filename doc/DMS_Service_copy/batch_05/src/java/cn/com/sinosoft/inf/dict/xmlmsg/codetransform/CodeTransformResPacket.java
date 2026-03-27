package cn.com.sinosoft.inf.dict.xmlmsg.codetransform;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;



public class CodeTransformResPacket {

	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private CodeTransformResBody BODY = new CodeTransformResBody();
	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(ResponseHeadSchema hEAD) {
		HEAD = hEAD;
	}
	public CodeTransformResBody getBODY() {
		return BODY;
	}
	public void setBODY(CodeTransformResBody bODY) {
		BODY = bODY;
	}
	
}
