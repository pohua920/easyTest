package cn.com.sinosoft.inf.PMS.resDomains.prpDclassListResPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDclassListResBody implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private PrpDclassResList PRPDCLASSLIST = new PrpDclassResList();

	public void validate() throws Exception {
	}

	public PrpDclassResList getPRPDCLASSLIST() {
		return PRPDCLASSLIST;
	}

	public void setPRPDCLASSLIST(PrpDclassResList PRPDCLASSLIST) {
		this.PRPDCLASSLIST = PRPDCLASSLIST;
	}

}
