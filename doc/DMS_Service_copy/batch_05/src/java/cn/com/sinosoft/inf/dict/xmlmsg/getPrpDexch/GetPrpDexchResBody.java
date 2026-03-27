package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDexch;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDexchResBody implements SchemaNode {

	private static final long serialVersionUID = 1L;

	private PrpDexchInfo PRPDEXCHINFO = new PrpDexchInfo();

	public void validate() throws Exception {
	}

	public PrpDexchInfo getPRPDEXCHINFO() {
		return PRPDEXCHINFO;
	}

	public void setPRPDEXCHINFO(PrpDexchInfo PRPDEXCHINFO) {
		this.PRPDEXCHINFO = PRPDEXCHINFO;
	}

}
