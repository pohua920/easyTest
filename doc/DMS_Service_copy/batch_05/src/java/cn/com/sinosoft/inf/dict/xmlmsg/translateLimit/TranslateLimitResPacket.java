package cn.com.sinosoft.inf.dict.xmlmsg.translateLimit;

import com.sinosoft.dmsdriver.domain.common.ResponseHeadSchema;
import com.sinosoft.dmsdriver.domain.common.SchemaNode;

public class TranslateLimitResPacket implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private TranslateLimitResBody BODY = new TranslateLimitResBody();

	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}

	public void setHEAD(ResponseHeadSchema HEAD) {
		this.HEAD = HEAD;
	}

	public TranslateLimitResBody getBODY() {
		return BODY;
	}

	public void setBODY(TranslateLimitResBody body) {
		BODY = body;
	}


}
