package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDdealer;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDdealerReqBody implements SchemaNode {

	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private String DEALERCODE = "";
	public String getDEALERCODE() {
		return DEALERCODE;
	}
	public void setDEALERCODE(String DEALERCODE) {
		this.DEALERCODE = DEALERCODE;
	}
	
}
