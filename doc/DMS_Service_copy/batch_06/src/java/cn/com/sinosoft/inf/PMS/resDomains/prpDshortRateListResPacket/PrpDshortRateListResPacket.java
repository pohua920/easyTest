package cn.com.sinosoft.inf.PMS.resDomains.prpDshortRateListResPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDshortRateListResPacket implements SchemaNode{
	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private PrpDshortRateListResBody BODY = new PrpDshortRateListResBody();
	
	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(ResponseHeadSchema HEAD) {
		this.HEAD = HEAD;
	}
	public PrpDshortRateListResBody getBODY() {
		return BODY;
	}
	public void setBODY(PrpDshortRateListResBody BODY) {
		this.BODY = BODY;
	}

}
