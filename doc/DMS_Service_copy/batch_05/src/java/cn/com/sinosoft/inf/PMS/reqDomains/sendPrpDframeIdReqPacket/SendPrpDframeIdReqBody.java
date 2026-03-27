package cn.com.sinosoft.inf.PMS.reqDomains.sendPrpDframeIdReqPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class SendPrpDframeIdReqBody implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
		// TODO Auto-generated method stub
	}
	private String FRAMECODE = "";

	public String getFRAMECODE() {
		return FRAMECODE;
	}
	public void setFRAMECODE(String framecode) {
		FRAMECODE = framecode;
	}

}
