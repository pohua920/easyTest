package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDcodeList;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;


public class GetPrpDcodeListReqPacket {

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private GetPrpDcodeListReqBody BODY = new GetPrpDcodeListReqBody();

	public RequestHeadSchema getHEAD() {
		return HEAD;
	}

	public void setHEAD(RequestHeadSchema HEAD) {
		this.HEAD = HEAD;
	}

	public GetPrpDcodeListReqBody getBODY() {
		return BODY;
	}

	public void setBODY(GetPrpDcodeListReqBody BODY) {
		this.BODY = BODY;
	}

}
