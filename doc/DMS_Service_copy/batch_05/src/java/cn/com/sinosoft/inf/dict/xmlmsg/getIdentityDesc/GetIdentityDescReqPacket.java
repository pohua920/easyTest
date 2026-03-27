package cn.com.sinosoft.inf.dict.xmlmsg.getIdentityDesc;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;

import com.sinosoft.dmsdriver.domain.common.SchemaNode;

public class GetIdentityDescReqPacket implements SchemaNode {
	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private GetIdentityDescReqBody BODY = new GetIdentityDescReqBody();

	public RequestHeadSchema getHEAD() {
		return HEAD;
	}

	public void setHEAD(RequestHeadSchema HEAD) {
		this.HEAD = HEAD;
	}

	public GetIdentityDescReqBody getBODY() {
		return BODY;
	}

	public void setBODY(GetIdentityDescReqBody BODY) {
		this.BODY = BODY;
	}

}
