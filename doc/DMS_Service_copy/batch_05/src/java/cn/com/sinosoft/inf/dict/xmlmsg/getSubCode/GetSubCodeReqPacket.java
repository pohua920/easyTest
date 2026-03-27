package cn.com.sinosoft.inf.dict.xmlmsg.getSubCode;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;

public class GetSubCodeReqPacket {

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private GetSubCodeReqBody BODY = new GetSubCodeReqBody();

	public RequestHeadSchema getHEAD() {
		return HEAD;
	}

	public void setHEAD(RequestHeadSchema HEAD) {
		this.HEAD = HEAD;
	}

	public GetSubCodeReqBody getBODY() {
		return BODY;
	}

	public void setBODY(GetSubCodeReqBody BODY) {
		this.BODY = BODY;
	}

}
