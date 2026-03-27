package cn.com.sinosoft.inf.dict.xmlmsg.countWorkDay;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;

import com.sinosoft.dmsdriver.domain.common.SchemaNode;

public class CountWorkDayResPacket implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}
	
	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private CountWorkDayResBody BODY = new CountWorkDayResBody();
	
	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(ResponseHeadSchema head) {
		HEAD = head;
	}
	public CountWorkDayResBody getBODY() {
		return BODY;
	}
	public void setBODY(CountWorkDayResBody body) {
		BODY = body;
	}

	}

