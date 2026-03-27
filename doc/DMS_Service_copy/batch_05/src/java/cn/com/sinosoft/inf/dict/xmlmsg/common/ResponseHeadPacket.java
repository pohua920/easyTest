package cn.com.sinosoft.inf.dict.xmlmsg.common;


public class ResponseHeadPacket {

	private String REQUEST_TYPE="";//请求类型
	private String RESPONSECODE="";//返回代码0：无返回body，1：有返回body
	private String ERRORCODE="";//错误代码
	private String ERRORMESSAGE="";//错误信息
	public String getREQUEST_TYPE() {
		return REQUEST_TYPE;
	}
	public void setREQUEST_TYPE(String REQUEST_TYPE) {
		this.REQUEST_TYPE = REQUEST_TYPE;
	}
	public String getRESPONSECODE() {
		return RESPONSECODE;
	}
	public void setRESPONSECODE(String RESPONSE_CODE) {
		this.RESPONSECODE = RESPONSE_CODE;
	}
	public String getERRORCODE() {
		return ERRORCODE;
	}
	public void setERRORCODE(String ERROR_CODE) {
		this.ERRORCODE = ERROR_CODE;
	}
	public String getERRORMESSAGE() {
		return ERRORMESSAGE;
	}
	public void setERRORMESSAGE(String ERROR_MESSAGE) {
		this.ERRORMESSAGE = ERROR_MESSAGE;
	}
	
}
