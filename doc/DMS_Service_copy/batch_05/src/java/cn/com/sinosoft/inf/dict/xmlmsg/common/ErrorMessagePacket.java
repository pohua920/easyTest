package cn.com.sinosoft.inf.dict.xmlmsg.common;


public class ErrorMessagePacket {

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}
	private ResponseHeadSchema HEAD = new ResponseHeadSchema();// 请求类型

	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(ResponseHeadSchema HEAD) {
		this.HEAD = HEAD;
	}

}
