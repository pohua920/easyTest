package cn.com.sinosoft.inf.dict.xmlmsg.exchange;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class ExchangeReqPacket implements SchemaNode {
	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private ExchangeReqBody BODY = new ExchangeReqBody();

	public RequestHeadSchema getHEAD() {
		return HEAD;
	}

	public void setHEAD(RequestHeadSchema HEAD) {
		this.HEAD = HEAD;
	}

	public ExchangeReqBody getBODY() {
		return BODY;
	}

	public void setBODY(ExchangeReqBody BODY) {
		this.BODY = BODY;
	}

}
