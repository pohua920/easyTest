package cn.com.sinosoft.inf.PMS.resDomains.prpDriskShortRateListResPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDriskShortRateListResPacket implements SchemaNode{
	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private PrpDriskShortRateListResBody BODY = new PrpDriskShortRateListResBody();
	
	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(ResponseHeadSchema HEAD) {
		this.HEAD = HEAD;
	}
	public PrpDriskShortRateListResBody getBODY() {
		return BODY;
	}
	public void setBODY(PrpDriskShortRateListResBody BODY) {
		this.BODY = BODY;
	}

}
