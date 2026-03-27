package cn.com.sinosoft.inf.dict.xmlmsg.exchange;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class ExchangeResPacket implements SchemaNode {

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private ExchangeResBody BODY = new ExchangeResBody();

	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}

	public void setHEAD(ResponseHeadSchema HEAD) {
		this.HEAD = HEAD;
	}

	public ExchangeResBody getBODY() {
		return BODY;
	}

	public void setBODY(ExchangeResBody BODY) {
		this.BODY = BODY;
	}

}
