package cn.com.sinosoft.inf.PMS.resDomains.prpDitemTypeListResPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDitemTypeListResBody implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private PrpDitemTypeResList PRPDITEMTYPELIST = new PrpDitemTypeResList();

	public void validate() throws Exception {
	}

	public PrpDitemTypeResList getPRPDITEMTYPELIST() {
		return PRPDITEMTYPELIST;
	}

	public void setPRPDITEMTYPELIST(PrpDitemTypeResList PRPDITEMTYPELIST) {
		this.PRPDITEMTYPELIST = PRPDITEMTYPELIST;
	}

}
