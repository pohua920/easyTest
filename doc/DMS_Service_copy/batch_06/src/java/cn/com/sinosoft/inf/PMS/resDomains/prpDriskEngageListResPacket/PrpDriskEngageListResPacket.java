package cn.com.sinosoft.inf.PMS.resDomains.prpDriskEngageListResPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDriskEngageListResPacket implements SchemaNode{
	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private PrpDriskEngageListResBody BODY = new PrpDriskEngageListResBody();
	
	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(ResponseHeadSchema HEAD) {
		this.HEAD = HEAD;
	}
	public PrpDriskEngageListResBody getBODY() {
		return BODY;
	}
	public void setBODY(PrpDriskEngageListResBody BODY) {
		this.BODY = BODY;
	}

}
