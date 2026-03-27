package cn.com.sinosoft.inf.dict.xmlmsg.translateLimit;

import com.sinosoft.dmsdriver.domain.common.RequestHeadSchema;
import com.sinosoft.dmsdriver.domain.common.SchemaNode;

public class TranslateLimitReqPacket implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private TranslateLimitReqBody BODY = new TranslateLimitReqBody();

	public RequestHeadSchema getHEAD() {
		return HEAD;
	}

	public void setHEAD(RequestHeadSchema HEAD) {
		this.HEAD = HEAD;
	}

	public TranslateLimitReqBody getBODY() {
		return BODY;
	}

	public void setBODY(TranslateLimitReqBody body) {
		BODY = body;
	}

}
