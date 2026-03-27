package cn.com.sinosoft.inf.PMS.resDomains.prpDshortRateListResPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDshortRateListResBody implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private PrpDshortRateResList PRPDSHORTRATELIST = new PrpDshortRateResList();

	public void validate() throws Exception {
	}

	public PrpDshortRateResList getPRPDSHORTRATELIST() {
		return PRPDSHORTRATELIST;
	}

	public void setPRPDSHORTRATELIST(PrpDshortRateResList PRPDSHORTRATELIST) {
		this.PRPDSHORTRATELIST = PRPDSHORTRATELIST;
	}

}
