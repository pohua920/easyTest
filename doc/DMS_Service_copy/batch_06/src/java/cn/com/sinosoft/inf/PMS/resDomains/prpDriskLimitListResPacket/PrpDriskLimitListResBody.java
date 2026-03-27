package cn.com.sinosoft.inf.PMS.resDomains.prpDriskLimitListResPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDriskLimitListResBody implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private PrpDriskLimitResList PRPDRISKLIMITLIST = new PrpDriskLimitResList();

	public void validate() throws Exception {
	}

	public PrpDriskLimitResList getPRPDRISKLIMITLIST() {
		return PRPDRISKLIMITLIST;
	}

	public void setPRPDRISKLIMITLIST(PrpDriskLimitResList PRPDRISKLIMITLIST) {
		this.PRPDRISKLIMITLIST = PRPDRISKLIMITLIST;
	}

}
