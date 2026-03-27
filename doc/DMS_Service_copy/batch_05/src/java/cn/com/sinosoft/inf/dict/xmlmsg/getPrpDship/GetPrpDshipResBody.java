package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDship;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDshipResBody implements SchemaNode{
	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private PrpDshipResInfo PRPDSHIPINFO = new PrpDshipResInfo();
	
	public PrpDshipResInfo getPRPDSHIPINFO() {
		return PRPDSHIPINFO;
	}
	public void setPRPDSHIPINFO(PrpDshipResInfo PRPDSHIPINFO) {
		this.PRPDSHIPINFO = PRPDSHIPINFO;
	}
	
}
