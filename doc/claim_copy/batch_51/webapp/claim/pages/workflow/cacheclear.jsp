<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="ins.framework.common.ServiceFactory"%>
<%@ page import="com.sinosoft.one.bpm.support.BpmServiceSupport"%>
<%@ page import="com.sinosoft.claim.common.util.CommonUtils"%>
<%@ page import="java.util.*"%>
<%
	String processId = request.getParameter("processId");
	String businessId = request.getParameter("businessId");
	if(CommonUtils.isEmpty(processId) || CommonUtils.isEmpty(businessId)){
		response.getWriter().print("{\"status\":\"0\",\"message\":\"Á÷³ÌID¡¢˜I„ÕÌ–´a£¡\"}");
	} else {
		BpmServiceSupport bpmServiceSupport = (BpmServiceSupport) ServiceFactory.getService("bpmServiceSupport");
		bpmServiceSupport.removeInstanceIdFromCache(processId, businessId);
		response.getWriter().print("{\"status\":\"1\"}");
	}
%>