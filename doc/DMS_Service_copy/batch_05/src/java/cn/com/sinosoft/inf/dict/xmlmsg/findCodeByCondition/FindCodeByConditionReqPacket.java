package cn.com.sinosoft.inf.dict.xmlmsg.findCodeByCondition;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;


public class FindCodeByConditionReqPacket {

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private FindCodeByConditionReqBody BODY = new FindCodeByConditionReqBody();

	public RequestHeadSchema getHEAD() {
		return HEAD;
	}

	public void setHEAD(RequestHeadSchema HEAD) {
		this.HEAD = HEAD;
	}

	public FindCodeByConditionReqBody getBODY() {
		return BODY;
	}

	public void setBODY(FindCodeByConditionReqBody BODY) {
		this.BODY = BODY;
	}

}
