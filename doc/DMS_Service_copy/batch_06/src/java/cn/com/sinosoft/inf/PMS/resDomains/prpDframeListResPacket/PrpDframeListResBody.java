package cn.com.sinosoft.inf.PMS.resDomains.prpDframeListResPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDframeListResBody implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private PrpDframeResList PRPDFRAMELIST = new PrpDframeResList();

	public void validate() throws Exception {
	}

	public PrpDframeResList getPRPDFRAMELIST() {
		return PRPDFRAMELIST;
	}

	public void setPRPDFRAMELIST(PrpDframeResList PRPDFRAMELIST) {
		this.PRPDFRAMELIST = PRPDFRAMELIST;
	}

}
