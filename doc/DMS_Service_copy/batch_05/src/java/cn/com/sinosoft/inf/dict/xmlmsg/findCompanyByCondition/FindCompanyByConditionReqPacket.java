package cn.com.sinosoft.inf.dict.xmlmsg.findCompanyByCondition;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;

public class FindCompanyByConditionReqPacket {

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private RequestHeadSchema HEAD = new RequestHeadSchema();
	private FindCompanyByConditionReqBody BODY = new FindCompanyByConditionReqBody();

	public RequestHeadSchema getHEAD() {
		return HEAD;
	}

	public void setHEAD(RequestHeadSchema HEAD) {
		this.HEAD = HEAD;
	}

	public FindCompanyByConditionReqBody getBODY() {
		return BODY;
	}

	public void setBODY(FindCompanyByConditionReqBody BODY) {
		this.BODY = BODY;
	}

}
