package cn.com.sinosoft.inf.dict.xmlmsg.getprpdcompany;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDcompanyResBody implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private PrpDcompanyResInfo PRPDCOMPANY = new PrpDcompanyResInfo();

	public void validate() throws Exception {
	}

	public PrpDcompanyResInfo getPRPDCOMPANY() {
		return PRPDCOMPANY;
	}

	public void setPRPDCOMPANY(PrpDcompanyResInfo PRPDCOMPANY) {
		this.PRPDCOMPANY = PRPDCOMPANY;
	}

	
}
