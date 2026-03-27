package cn.com.sinosoft.inf.PMS.resDomains.prpDriskItemListResPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDriskItemListResBody implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private PrpDriskItemResList PRPDRISKITEMLIST = new PrpDriskItemResList();

	public void validate() throws Exception {
	}

	public PrpDriskItemResList getPRPDRISKITEMLIST() {
		return PRPDRISKITEMLIST;
	}

	public void setPRPDRISKITEMLIST(PrpDriskItemResList PRPDRISKITEMLIST) {
		this.PRPDRISKITEMLIST = PRPDRISKITEMLIST;
	}

}
