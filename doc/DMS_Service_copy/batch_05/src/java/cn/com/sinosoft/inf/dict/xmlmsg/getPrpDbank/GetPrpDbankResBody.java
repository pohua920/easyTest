package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDbank;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDbankResBody implements SchemaNode {
	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private PrpDbankInfo PRPDBANKINFO = new PrpDbankInfo();

	public PrpDbankInfo getPRPDBANKINFO() {
		return PRPDBANKINFO;
	}

	public void setPRPDBANKINFO(PrpDbankInfo PRPDBANKINFO) {
		this.PRPDBANKINFO = PRPDBANKINFO;
	}

}
