package cn.com.sinosoft.inf.PMS.resDomains.prpDriskClauseKindListResPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDriskClauseKindListResPacket implements SchemaNode{
	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private PrpDriskClauseKindListResBody BODY = new PrpDriskClauseKindListResBody();
	
	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(ResponseHeadSchema HEAD) {
		this.HEAD = HEAD;
	}
	public PrpDriskClauseKindListResBody getBODY() {
		return BODY;
	}
	public void setBODY(PrpDriskClauseKindListResBody BODY) {
		this.BODY = BODY;
	}

}
