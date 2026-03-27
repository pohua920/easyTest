package cn.com.sinosoft.inf.PMS.resDomains.prpDriskShortRateListResPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDriskShortRateListResBody implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private PrpDriskShortRateResList PRPDRISKSHORTRATELIST = new PrpDriskShortRateResList();

	public void validate() throws Exception {
	}

	public PrpDriskShortRateResList getPRPDRISKSHORTRATELIST() {
		return PRPDRISKSHORTRATELIST;
	}

	public void setPRPDRISKSHORTRATELIST(PrpDriskShortRateResList PRPDRISKSHORTRATELIST) {
		this.PRPDRISKSHORTRATELIST = PRPDRISKSHORTRATELIST;
	}

}
