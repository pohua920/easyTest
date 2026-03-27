package com.sinosoft.dmsdriver.domain.common;


public class ResponseHeadPacket {

	private String REQUEST_TYPE="";//璇锋眰绫诲瀷
	private String RESPONSECODE="";//杩斿洖浠ｇ爜0锛氭棤杩斿洖body锛�锛氭湁杩斿洖body
	private String ERRORCODE="";//閿欒浠ｇ爜
	private String ERRORMESSAGE="";//閿欒淇℃伅
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
