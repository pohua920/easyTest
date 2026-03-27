package cn.com.sinosoft.inf.PMS.resDomains.prpDriskLimitListResPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDriskLimitListResPacket implements SchemaNode{
	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private PrpDriskLimitListResBody BODY = new PrpDriskLimitListResBody();
	
	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(ResponseHeadSchema HEAD) {
		this.HEAD = HEAD;
	}
	public PrpDriskLimitListResBody getBODY() {
		return BODY;
	}
	public void setBODY(PrpDriskLimitListResBody BODY) {
		this.BODY = BODY;
	}

}
