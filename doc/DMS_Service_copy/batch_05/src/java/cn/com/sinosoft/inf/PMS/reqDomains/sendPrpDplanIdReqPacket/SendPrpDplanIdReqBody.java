package cn.com.sinosoft.inf.PMS.reqDomains.sendPrpDplanIdReqPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class SendPrpDplanIdReqBody implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
		// TODO Auto-generated method stub
	}
	private String PLANCODE = "";

	public String getPLANCODE() {
		return PLANCODE;
	}
	public void setPLANCODE(String plancode) {
		PLANCODE = plancode;
	}



}
