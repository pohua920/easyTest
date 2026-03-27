<%--
****************************************************************************
* DESC       ： 显示配件管理信息
* AUTHOR     ： claim
* CREATEDATE ： 2007-07-09
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@page contentType="text/html;charset=gb2312"%>
<%-- 引入bean类部分 --%>
<%@page import="java.util.*"%>
<%@ page import="com.sinosoft.claim.dto.custom.UserDto"%>
<%@ page import="com.sinosoft.sysframework.reference.*"%>
<%
	//首先找到配件系统的地址
	String SERVER_URL = AppConfig.get("sysconst.FITTINGS_URL");
	String SERVER_IP = AppConfig.get("sysconst.FITTINGS_IP");
	String callName = (String) request.getParameter("callName");

	String userCode = "";
	UserDto user = (UserDto) session.getAttribute("user");
	if (user != null) {
		userCode = user.getUserCode();
	}
	String openUrl = SERVER_IP + "/" + callName + "?" + "UserId=" + userCode;

	System.out.println(openUrl);

	//if ("".equals(callName)){

	//}
%>
<script language="javascript">
   //alert('<%=openUrl%>');
   //var newWindow = window.open
	('<%=openUrl%>','配件系统相关功能','width=1010,height=670,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	//newWindow.focus();
</script>
<html>
<form name=fm action="<%=openUrl%>" method="post"></form>
<html>
<script language="javascript">
	fm.submit();
</script>