package cn.com.sinosoft.inf.PMS.reqDomains.sendPrpDkindIdReqPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class SendPrpDkindIdReqBody implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
		// TODO Auto-generated method stub
	}
	private String KINDCODE = "";

	public String getKINDCODE() {
		return KINDCODE;
	}
	public void setKINDCODE(String kindcode) {
		KINDCODE = kindcode;
	}



}
