package cn.com.sinosoft.inf.dict.xmlmsg.agentSYN;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class SYNReqBody  implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}
	private Object data = new Object();

	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
