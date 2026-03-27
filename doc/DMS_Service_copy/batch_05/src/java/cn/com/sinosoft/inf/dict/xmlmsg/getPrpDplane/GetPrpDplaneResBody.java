package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDplane;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDplaneResBody implements SchemaNode{
	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private PrpDplaneResInfo PRPDPLANERESINFO = new PrpDplaneResInfo();
	public PrpDplaneResInfo getPRPDPLANERESINFO() {
		return PRPDPLANERESINFO;
	}
	public void setPRPDPLANERESINFO(PrpDplaneResInfo PRPDPLANERESINFO) {
		this.PRPDPLANERESINFO = PRPDPLANERESINFO;
	}
	
}
