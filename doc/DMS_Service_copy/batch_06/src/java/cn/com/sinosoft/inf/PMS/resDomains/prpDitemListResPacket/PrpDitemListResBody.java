package cn.com.sinosoft.inf.PMS.resDomains.prpDitemListResPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDitemListResBody implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private PrpDitemResList PRPDITEMLIST = new PrpDitemResList();

	public void validate() throws Exception {
	}

	public PrpDitemResList getPRPDITEMLIST() {
		return PRPDITEMLIST;
	}

	public void setPRPDITEMLIST(PrpDitemResList PRPDITEMLIST) {
		this.PRPDITEMLIST = PRPDITEMLIST;
	}

}
