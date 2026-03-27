package cn.com.sinosoft.inf.PMS.resDomains.prpDkindListResPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDkindListResPacket implements SchemaNode{
	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private PrpDkindListResBody BODY = new PrpDkindListResBody();
	
	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(ResponseHeadSchema HEAD) {
		this.HEAD = HEAD;
	}
	public PrpDkindListResBody getBODY() {
		return BODY;
	}
	public void setBODY(PrpDkindListResBody BODY) {
		this.BODY = BODY;
	}

}
