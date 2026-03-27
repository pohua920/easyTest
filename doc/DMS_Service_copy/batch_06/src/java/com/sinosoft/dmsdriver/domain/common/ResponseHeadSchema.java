package com.sinosoft.dmsdriver.domain.common;


public class ResponseHeadSchema implements SchemaNode{

	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private String REQUEST_TYPE="";//璇锋眰绫诲瀷
	private String RESPONSE_CODE="";//杩斿洖浠ｇ爜0锛氭棤杩斿洖body锛�锛氭湁杩斿洖body
	private String ERROR_CODE="";//閿欒浠ｇ爜
	private String ERROR_MESSAGE="";//閿欒淇℃伅
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
