package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDdisaster;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;


public class PrpDdisasterResPacket {
	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private PrpDdisasterResBody BODY = new PrpDdisasterResBody();

	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(ResponseHeadSchema head) {
		HEAD = head;
	}
	public PrpDdisasterResBody getBODY() {
		return BODY;
	}
	public void setBODY(PrpDdisasterResBody body) {
		BODY = body;
	}

}
