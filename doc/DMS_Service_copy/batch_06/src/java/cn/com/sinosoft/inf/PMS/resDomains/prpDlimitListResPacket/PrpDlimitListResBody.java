package cn.com.sinosoft.inf.PMS.resDomains.prpDlimitListResPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDlimitListResBody implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private PrpDlimitResList PRPDLIMITLIST = new PrpDlimitResList();

	public void validate() throws Exception {
	}

	public PrpDlimitResList getPRPDLIMITLIST() {
		return PRPDLIMITLIST;
	}

	public void setPRPDLIMITLIST(PrpDlimitResList PRPDLIMITLIST) {
		this.PRPDLIMITLIST = PRPDLIMITLIST;
	}

}
