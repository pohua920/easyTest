package cn.com.sinosoft.inf.PMS.resDomains.prpDplanListResPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDplanListResBody implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private PrpDplanResList PRPDPLANLIST = new PrpDplanResList();

	public void validate() throws Exception {
	}

	public PrpDplanResList getPRPDPLANLIST() {
		return PRPDPLANLIST;
	}

	public void setPRPDPLANLIST(PrpDplanResList PRPDPLANLIST) {
		this.PRPDPLANLIST = PRPDPLANLIST;
	}

}
