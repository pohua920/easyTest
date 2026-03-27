<!--****************************************************************************************
 * DESC       ：	Portal自动登录集成系统实现
 * Author     : sinosoft
 * CREATEDATE ：	2012-02-14
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *
 *****************************************************************************************-->
<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="org.jasig.cas.client.authentication.AttributePrincipal"%>

<%
	AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
	Map<String, Object> attributes = principal.getAttributes();
	String usercode = (String) attributes.get("usercode");
	String password = (String) attributes.get("password");
	String comcode = request.getParameter("comcode");
	
	if(null==comcode){
		comcode = (String) attributes.get("comcode");
	}
%>


<jsp:forward page="/common/login.do">
	<jsp:param name="userCode" value="<%=usercode%>"/>
	<jsp:param name="password" value="<%=password%>"/>
	<jsp:param name="comCode" value="<%=comcode%>"/>
	<jsp:param name="actionType" value="login"/>
	<jsp:param name="systemCode" value="claim"/>
</jsp:forward>





