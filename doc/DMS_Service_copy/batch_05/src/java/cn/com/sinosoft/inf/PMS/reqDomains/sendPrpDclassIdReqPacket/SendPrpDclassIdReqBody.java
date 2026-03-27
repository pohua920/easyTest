package cn.com.sinosoft.inf.PMS.reqDomains.sendPrpDclassIdReqPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class SendPrpDclassIdReqBody implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
		// TODO Auto-generated method stub
	}
	private String CLASSCODE = "";

	public String getCLASSCODE() {
		return CLASSCODE;
	}
	public void setCLASSCODE(String classcode) {
		CLASSCODE = classcode;
	}

}
