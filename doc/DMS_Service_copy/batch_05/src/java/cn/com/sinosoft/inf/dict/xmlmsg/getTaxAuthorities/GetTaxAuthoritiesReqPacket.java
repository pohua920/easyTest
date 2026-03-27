package cn.com.sinosoft.inf.dict.xmlmsg.getTaxAuthorities;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class GetTaxAuthoritiesReqPacket implements SchemaNode{

	private static final long serialVersionUID = 1L;
	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private GetTaxAuthoritiesReqBody BODY = new GetTaxAuthoritiesReqBody();
	public void validate() throws Exception {
	}
	public RequestHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(RequestHeadSchema head) {
		HEAD = head;
	}
	public GetTaxAuthoritiesReqBody getBODY() {
		return BODY;
	}
	public void setBODY(GetTaxAuthoritiesReqBody body) {
		BODY = body;
	}

}
