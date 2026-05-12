package com.ctbcins.util.print;

/**
 * 公用的取值
 * 因為邏輯一致不用都分開寫這樣不好維護
 * mantis：CLM0072 ，處理人員：BK007 蘇哲，需求單編號：CLM0072.工程險追償理算書
 * @author bk007
 *
 */
public interface Report {

	//總公司經辦
	String getHandlerCode();
	void setHandlerCode(String handlerCode);
	String getHandlerName();
	void setHandlerName(String handlerName);
	//分公司經辦
	String getSubHandlerCode();
	void setSubHandlerCode(String subHandlerCode);
	String getSubHandlerName();
	void setSubHandlerName(String subHandlerName);
	//理賠說明 or 追償說明
	String getContext();
	void setContext(String context);
}
