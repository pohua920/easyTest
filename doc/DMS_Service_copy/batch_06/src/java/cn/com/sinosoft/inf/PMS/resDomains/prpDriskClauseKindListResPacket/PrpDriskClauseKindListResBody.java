package cn.com.sinosoft.inf.PMS.resDomains.prpDriskClauseKindListResPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDriskClauseKindListResBody implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private PrpDriskClauseKindResList PRPDRISKCLAUSEKINDLIST = new PrpDriskClauseKindResList();

	public void validate() throws Exception {
	}

	public PrpDriskClauseKindResList getPRPDRISKCLAUSEKINDLIST() {
		return PRPDRISKCLAUSEKINDLIST;
	}

	public void setPRPDRISKCLAUSEKINDLIST(PrpDriskClauseKindResList PRPDRISKCLAUSEKINDLIST) {
		this.PRPDRISKCLAUSEKINDLIST = PRPDRISKCLAUSEKINDLIST;
	}

}
