package cn.com.sinosoft.inf.dict.xmlmsg.getCompany;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class GetCompanyReqPacket implements SchemaNode{

	private static final long serialVersionUID = 1L;
	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private GetCompanyReqBody BODY = new GetCompanyReqBody();
	public RequestHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(RequestHeadSchema head) {
		HEAD = head;
	}
	
	public GetCompanyReqBody getBODY() {
		return BODY;
	}
	public void setBODY(GetCompanyReqBody body) {
		BODY = body;
	}
	public void validate() throws Exception {
	}

}
