package cn.com.sinosoft.inf.PMS.reqDomains.sendPrpDlimitIdReqPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class SendPrpDlimitIdReqBody implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
		// TODO Auto-generated method stub
	}
	private String LIMITCODE = "";

	public String getLIMITCODE() {
		return LIMITCODE;
	}
	public void setLIMITCODE(String limitcode) {
		LIMITCODE = limitcode;
	}

}
