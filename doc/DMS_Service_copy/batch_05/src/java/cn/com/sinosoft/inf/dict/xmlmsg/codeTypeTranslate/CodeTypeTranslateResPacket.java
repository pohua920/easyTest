package cn.com.sinosoft.inf.dict.xmlmsg.codeTypeTranslate;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class CodeTypeTranslateResPacket implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private CodeTypeTranslateResBody BODY = new CodeTypeTranslateResBody();

	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}

	public void setHEAD(ResponseHeadSchema HEAD) {
		this.HEAD = HEAD;
	}

	public CodeTypeTranslateResBody getBODY() {
		return BODY;
	}

	public void setBODY(CodeTypeTranslateResBody BODY) {
		this.BODY = BODY;
	}

}
