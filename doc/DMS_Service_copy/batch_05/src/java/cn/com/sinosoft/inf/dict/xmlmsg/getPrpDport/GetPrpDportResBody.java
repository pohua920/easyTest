package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDport;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDportResBody implements SchemaNode{
	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private PrpDportResInfo PRPDPORT = new PrpDportResInfo();
	public PrpDportResInfo getPRPDPORT() {
		return PRPDPORT;
	}
	public void setPRPDPORT(PrpDportResInfo PRPDPORT) {
		this.PRPDPORT = PRPDPORT;
	}
	
	
}
