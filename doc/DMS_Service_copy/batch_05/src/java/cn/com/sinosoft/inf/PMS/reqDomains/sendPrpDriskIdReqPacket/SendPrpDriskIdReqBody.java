package cn.com.sinosoft.inf.PMS.reqDomains.sendPrpDriskIdReqPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class SendPrpDriskIdReqBody implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
		// TODO Auto-generated method stub
	}
	private String RISKCODE = "";

	public String getRISKCODE() {
		return RISKCODE;
	}
	public void setRISKCODE(String riskcode) {
		RISKCODE = riskcode;
	}



}
