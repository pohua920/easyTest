package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDcode;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDcodeResBody implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private PrpDcodeResInfo PRPDCODERESINFO = new PrpDcodeResInfo();

	public PrpDcodeResInfo getPRPDCODERESINFO() {
		return PRPDCODERESINFO;
	}

	public void setPRPDCODERESINFO(PrpDcodeResInfo PRPDCODERESINFO) {
		this.PRPDCODERESINFO = PRPDCODERESINFO;
	}

}
