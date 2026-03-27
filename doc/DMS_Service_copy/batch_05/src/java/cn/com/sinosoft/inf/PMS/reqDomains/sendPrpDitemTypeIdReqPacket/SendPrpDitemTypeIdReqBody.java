package cn.com.sinosoft.inf.PMS.reqDomains.sendPrpDitemTypeIdReqPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class SendPrpDitemTypeIdReqBody implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
		// TODO Auto-generated method stub
	}
	private String ITEMTYPE = "";

	public String getITEMTYPE() {
		return ITEMTYPE;
	}
	public void setITEMTYPE(String itemtype) {
		ITEMTYPE = itemtype;
	}

}
