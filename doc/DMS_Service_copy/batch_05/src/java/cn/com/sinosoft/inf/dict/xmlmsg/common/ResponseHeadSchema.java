package cn.com.sinosoft.inf.dict.xmlmsg.common;


public class ResponseHeadSchema implements SchemaNode{

	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private String REQUEST_TYPE="";//请求类型
	private String RESPONSE_CODE="";//返回代码0：无返回body，1：有返回body
	private String ERROR_CODE="";//错误代码
	private String ERROR_MESSAGE="";//错误信息
	public String getREQUEST_TYPE() {
		return REQUEST_TYPE;
	}
	public void setREQUEST_TYPE(String REQUEST_TYPE) {
		this.REQUEST_TYPE = REQUEST_TYPE;
	}
	public String getRESPONSE_CODE() {
		return RESPONSE_CODE;
	}
	public void setRESPONSE_CODE(String RESPONSE_CODE) {
		this.RESPONSE_CODE = RESPONSE_CODE;
	}
	public String getERROR_CODE() {
		return ERROR_CODE;
	}
	public void setERROR_CODE(String ERROR_CODE) {
		this.ERROR_CODE = ERROR_CODE;
	}
	public String getERROR_MESSAGE() {
		return ERROR_MESSAGE;
	}
	public void setERROR_MESSAGE(String ERROR_MESSAGE) {
		this.ERROR_MESSAGE = ERROR_MESSAGE;
	}
	
}
