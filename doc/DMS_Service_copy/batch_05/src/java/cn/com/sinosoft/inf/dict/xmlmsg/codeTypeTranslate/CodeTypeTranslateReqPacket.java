package cn.com.sinosoft.inf.dict.xmlmsg.codeTypeTranslate;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class CodeTypeTranslateReqPacket implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private CodeTypeTranslateReqBody BODY = new CodeTypeTranslateReqBody();

	public RequestHeadSchema getHEAD() {
		return HEAD;
	}

	public void setHEAD(RequestHeadSchema HEAD) {
		this.HEAD = HEAD;
	}

	public CodeTypeTranslateReqBody getBODY() {
		return BODY;
	}

	public void setBODY(CodeTypeTranslateReqBody BODY) {
		this.BODY = BODY;
	}

}
