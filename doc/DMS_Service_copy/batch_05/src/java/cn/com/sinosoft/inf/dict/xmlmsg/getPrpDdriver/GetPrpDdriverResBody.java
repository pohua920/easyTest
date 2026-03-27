package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDdriver;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDdriverResBody implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private PrpDdriverResInfo PRPDDRIVER = new PrpDdriverResInfo();
	public void validate() throws Exception {
	}
	public PrpDdriverResInfo getPRPDDRIVER() {
		return PRPDDRIVER;
	}
	public void setPRPDDRIVER(PrpDdriverResInfo PRPDDRIVER) {
		this.PRPDDRIVER = PRPDDRIVER;
	}
	
	
}
