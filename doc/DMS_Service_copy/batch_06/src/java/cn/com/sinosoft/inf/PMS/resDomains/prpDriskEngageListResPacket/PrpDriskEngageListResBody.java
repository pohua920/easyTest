package cn.com.sinosoft.inf.PMS.resDomains.prpDriskEngageListResPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDriskEngageListResBody implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private PrpDriskEngageResList PRPDRISKENGAGELIST = new PrpDriskEngageResList();

	public void validate() throws Exception {
	}

	public PrpDriskEngageResList getPRPDRISKENGAGELIST() {
		return PRPDRISKENGAGELIST;
	}

	public void setPRPDRISKENGAGELIST(PrpDriskEngageResList PRPDRISKENGAGELIST) {
		this.PRPDRISKENGAGELIST = PRPDRISKENGAGELIST;
	}

}
