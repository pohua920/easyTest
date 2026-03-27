package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDport;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDportReqBody implements SchemaNode {

	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private String PORTCODE = "";
	public String getPORTCODE() {
		return PORTCODE;
	}
	public void setPORTCODE(String PORTCODE) {
		this.PORTCODE = PORTCODE;
	}
	

}
