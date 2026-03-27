package cn.com.sinosoft.inf.dict.xmlmsg.countWorkDay;

import com.sinosoft.dmsdriver.domain.common.RequestHeadPacket;

public class CountWorkDayReqPacket {

	private RequestHeadPacket HEAD = new RequestHeadPacket();
	
	private CountWorkDayReqBody BODY = new CountWorkDayReqBody();

	public RequestHeadPacket getHEAD() {
		return HEAD;
	}

	public void setHEAD(RequestHeadPacket head) {
		HEAD = head;
	}

	public CountWorkDayReqBody getBODY() {
		return BODY;
	}

	public void setBODY(CountWorkDayReqBody body) {
		BODY = body;
	}
}
