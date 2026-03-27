package cn.com.sinosoft.inf.dict.xmlmsg.getshortrate;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;

import com.sinosoft.dmsdriver.domain.common.SchemaNode;

public class GetShortRateReqPacket implements SchemaNode {
	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private GetShortRateReqBody BODY = new GetShortRateReqBody();

	public RequestHeadSchema getHEAD() {
		return HEAD;
	}

	public void setHEAD(RequestHeadSchema HEAD) {
		this.HEAD = HEAD;
	}

	public GetShortRateReqBody getBODY() {
		return BODY;
	}

	public void setBODY(GetShortRateReqBody BODY) {
		this.BODY = BODY;
	}

}
