package cn.com.sinosoft.inf.dict.xmlmsg.productSYN;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;


public class SYNResPacket {
	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private String BODY = "";
	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(ResponseHeadSchema head) {
		HEAD = head;
	}
	public String getBODY() {
		return BODY;
	}
	public void setBODY(String body) {
		BODY = body;
	}
	
}
