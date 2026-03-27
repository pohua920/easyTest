package cn.com.sinosoft.inf.PMS.resDomains.prpDkindListResPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDkindListResBody implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private PrpDkindResList PRPDKINDLIST = new PrpDkindResList();

	public void validate() throws Exception {
	}

	public PrpDkindResList getPRPDKINDLIST() {
		return PRPDKINDLIST;
	}

	public void setPRPDKINDLIST(PrpDkindResList PRPDKINDLIST) {
		this.PRPDKINDLIST = PRPDKINDLIST;
	}

}
