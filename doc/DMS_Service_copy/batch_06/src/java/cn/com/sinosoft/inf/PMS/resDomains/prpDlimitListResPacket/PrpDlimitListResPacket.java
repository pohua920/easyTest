package cn.com.sinosoft.inf.PMS.resDomains.prpDlimitListResPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDlimitListResPacket implements SchemaNode{
	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private PrpDlimitListResBody BODY = new PrpDlimitListResBody();
	
	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(ResponseHeadSchema HEAD) {
		this.HEAD = HEAD;
	}
	public PrpDlimitListResBody getBODY() {
		return BODY;
	}
	public void setBODY(PrpDlimitListResBody BODY) {
		this.BODY = BODY;
	}

}
