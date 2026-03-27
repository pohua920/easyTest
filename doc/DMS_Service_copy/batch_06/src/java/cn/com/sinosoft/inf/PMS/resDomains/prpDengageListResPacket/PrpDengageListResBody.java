package cn.com.sinosoft.inf.PMS.resDomains.prpDengageListResPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDengageListResBody implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private PrpDengageResList PRPDENGAGELIST = new PrpDengageResList();

	public void validate() throws Exception {
	}

	public PrpDengageResList getPRPDENGAGELIST() {
		return PRPDENGAGELIST;
	}

	public void setPRPDENGAGELIST(PrpDengageResList PRPDENGAGELIST) {
		this.PRPDENGAGELIST = PRPDENGAGELIST;
	}

}
