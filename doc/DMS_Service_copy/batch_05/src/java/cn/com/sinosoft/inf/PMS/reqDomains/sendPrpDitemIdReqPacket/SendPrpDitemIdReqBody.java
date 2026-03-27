package cn.com.sinosoft.inf.PMS.reqDomains.sendPrpDitemIdReqPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class SendPrpDitemIdReqBody implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
		// TODO Auto-generated method stub
	}
	private String ITEMCODE = "";

	public String getITEMCODE() {
		return ITEMCODE;
	}
	public void setITEMCODE(String itemcode) {
		ITEMCODE = itemcode;
	}



}
