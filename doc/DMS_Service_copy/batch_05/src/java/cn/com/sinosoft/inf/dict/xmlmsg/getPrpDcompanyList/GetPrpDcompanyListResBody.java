package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDcompanyList;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDcompanyListResBody implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private PrpDcompanyList PRPDCOMPANYLIST = new PrpDcompanyList();

	public void validate() throws Exception {
	}

	public PrpDcompanyList getPRPDCOMPANYLIST() {
		return PRPDCOMPANYLIST;
	}

	public void setPRPDCOMPANYLIST(PrpDcompanyList PRPDCOMPANYLIST) {
		this.PRPDCOMPANYLIST = PRPDCOMPANYLIST;
	}

	
}
