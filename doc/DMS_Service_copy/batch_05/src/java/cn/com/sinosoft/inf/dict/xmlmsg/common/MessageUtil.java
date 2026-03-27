package cn.com.sinosoft.inf.dict.xmlmsg.common;


public class MessageUtil {
	public static ResponseHeadSchema setHeadMessage(String errorcode,
			String errormessage, String reqeusttype, String responsecode) {
		ResponseHeadSchema head = new ResponseHeadSchema();
		head.setERROR_CODE(errorcode);
		head.setERROR_MESSAGE(errormessage);
		head.setREQUEST_TYPE(reqeusttype);
		head.setRESPONSE_CODE(responsecode);
		return head; 
	}
}
