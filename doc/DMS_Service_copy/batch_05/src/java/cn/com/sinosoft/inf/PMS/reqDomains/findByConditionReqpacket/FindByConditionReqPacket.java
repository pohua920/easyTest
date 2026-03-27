package cn.com.sinosoft.inf.PMS.reqDomains.findByConditionReqpacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;

public class FindByConditionReqPacket {

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private FindByConditionReqBody BODY = new FindByConditionReqBody();

	public RequestHeadSchema getHEAD() {
		return HEAD;
	}

	public void setHEAD(RequestHeadSchema HEAD) {
		this.HEAD = HEAD;
	}

	public FindByConditionReqBody getBODY() {
		return BODY;
	}

	public void setBODY(FindByConditionReqBody BODY) {
		this.BODY = BODY;
	}

}
