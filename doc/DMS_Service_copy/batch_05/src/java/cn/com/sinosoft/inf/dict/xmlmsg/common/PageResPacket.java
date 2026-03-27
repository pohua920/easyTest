package cn.com.sinosoft.inf.dict.xmlmsg.common;

import cn.com.sinosoft.inf.dict.server.common.DictPage;

public class PageResPacket {
	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private DictPage BODY = new DictPage();
	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(ResponseHeadSchema head) {
		HEAD = head;
	}
	public DictPage getBODY() {
		return BODY;
	}
	public void setBODY(DictPage body) {
		BODY = body;
	}
	
}
