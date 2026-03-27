package com.sinosoft.dmsdriver.domain.common;

import cn.com.sinosoft.inf.dict.server.common.DictPage;

public class ResponsePacket {
	private ResponseHeadPacket HEAD = new ResponseHeadPacket();
	private DictPage BODY = new DictPage();
	public ResponseHeadPacket getHEAD() {
		return HEAD;
	}
	public void setHEAD(ResponseHeadPacket head) {
		HEAD = head;
	}
	public DictPage getBODY() {
		return BODY;
	}
	public void setBODY(DictPage body) {
		BODY = body;
	}
	
}
