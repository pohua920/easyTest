package cn.com.sinosoft.inf.PMS.reqDomains.sendPrpDengageIdReqPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class SendPrpDengageIdReqBody implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
		// TODO Auto-generated method stub
	}
	private String ENGAGECODE = "";

	public String getENGAGECODE() {
		return ENGAGECODE;
	}
	public void setENGAGECODE(String engagecode) {
		ENGAGECODE = engagecode;
	}


}
