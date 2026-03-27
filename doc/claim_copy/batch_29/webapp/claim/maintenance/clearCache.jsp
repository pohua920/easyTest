<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="com.sinosoft.claim.common.util.CacheClear" %>
<%@ page import="com.sinosoft.claim.common.util.CommonUtils" %>
<%
	String cacheType = request.getParameter("cacheType");
	String params = request.getParameter("params");
	try {
		if(!CommonUtils.isEmpty(cacheType) && !CommonUtils.isEmpty(params)){
			if("WorkFlowSwitch".equals(cacheType)){
				CacheClear.resetWorkFlowSwitch(params);
			} else if("UserComcodePower".equals(cacheType)){
				CacheClear.resetUserComcodePower(params);
			} else if("UserRiskPower".equals(cacheType)){
				CacheClear.resetUserRiskPower(params);
			} else if("WorkFlowSwfModelUseModelNo".equals(cacheType)){
				CacheClear.resetWorkFlowSwfModelUseModelNo(params);
			} else if("ProcessInstanceIdCache".equals(cacheType)){
				CacheClear.resetProcessInstanceIdCache(params);
			} else {
				CacheClear.resetWorkFlow(cacheType, params);
			}
		}
		out.write("{\"status\":\"1\"}");
	} catch (Exception e){
		out.write("{\"status\":\"0\"}");
	}
%>