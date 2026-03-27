package com.sinosoft.dmsdriver.domain.common;


public class RequestPacket implements SchemaNode{
	
	private static final long serialVersionUID = 1L;
	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private RequestBodySchema BODY = new RequestBodySchema();
	public void validate() throws Exception {
	}
	public RequestHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(RequestHeadSchema head) {
		HEAD = head;
	}
	public RequestBodySchema getBODY() {
		return BODY;
	}
	public void setBODY(RequestBodySchema body) {
		BODY = body;
	}
}
