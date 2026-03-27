package cn.com.sinosoft.inf.dict.xmlmsg.translateCode;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class TranslateCodeResPacket implements SchemaNode{

	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private TranslateCodeResBody BODY = new TranslateCodeResBody();
	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(ResponseHeadSchema HEAD) {
		this.HEAD = HEAD;
	}
	public TranslateCodeResBody getBODY() {
		return BODY;
	}
	public void setBODY(TranslateCodeResBody BODY) {
		this.BODY = BODY;
	}
	
}
