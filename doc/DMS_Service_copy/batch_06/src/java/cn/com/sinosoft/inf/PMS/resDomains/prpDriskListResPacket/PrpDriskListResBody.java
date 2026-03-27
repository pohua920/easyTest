package cn.com.sinosoft.inf.PMS.resDomains.prpDriskListResPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDriskListResBody implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private PrpDriskResList PRPDRISKLIST = new PrpDriskResList();

	public void validate() throws Exception {
	}

	public PrpDriskResList getPRPDRISKLIST() {
		return PRPDRISKLIST;
	}

	public void setPRPDRISKLIST(PrpDriskResList PRPDRISKLIST) {
		this.PRPDRISKLIST = PRPDRISKLIST;
	}

}
