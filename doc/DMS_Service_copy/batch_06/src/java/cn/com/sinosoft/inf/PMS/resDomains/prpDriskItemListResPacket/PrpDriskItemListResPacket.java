package cn.com.sinosoft.inf.PMS.resDomains.prpDriskItemListResPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDriskItemListResPacket implements SchemaNode{
	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private PrpDriskItemListResBody BODY = new PrpDriskItemListResBody();
	
	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(ResponseHeadSchema HEAD) {
		this.HEAD = HEAD;
	}
	public PrpDriskItemListResBody getBODY() {
		return BODY;
	}
	public void setBODY(PrpDriskItemListResBody BODY) {
		this.BODY = BODY;
	}

}
