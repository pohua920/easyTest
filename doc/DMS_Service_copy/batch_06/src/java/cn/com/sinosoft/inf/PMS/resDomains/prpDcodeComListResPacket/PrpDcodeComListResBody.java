package cn.com.sinosoft.inf.PMS.resDomains.prpDcodeComListResPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDcodeComListResBody implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private PrpDcodeComResList PRPDCODECOMLIST = new PrpDcodeComResList();

	public void validate() throws Exception {
	}

	public PrpDcodeComResList getPRPDCODECOMLIST() {
		return PRPDCODECOMLIST;
	}

	public void setPRPDCODECOMLIST(PrpDcodeComResList prpdcodecomlist) {
		PRPDCODECOMLIST = prpdcodecomlist;
	}
}
