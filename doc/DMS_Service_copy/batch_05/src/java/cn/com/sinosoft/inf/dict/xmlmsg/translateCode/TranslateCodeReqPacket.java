package cn.com.sinosoft.inf.dict.xmlmsg.translateCode;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class TranslateCodeReqPacket implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}
	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private TranslateCodeReqBody BODY = new TranslateCodeReqBody();

	public RequestHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(RequestHeadSchema HEAD) {
		this.HEAD = HEAD;
	}
	public TranslateCodeReqBody getBODY() {
		return BODY;
	}
	public void setBODY(TranslateCodeReqBody BODY) {
		this.BODY = BODY;
	}
}
