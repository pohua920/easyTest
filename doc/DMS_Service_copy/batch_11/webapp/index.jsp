<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
<%
	String faccCode = (String)session.getAttribute("accCode");
	String UserCode = (String)session.getAttribute("UserCode");
	String ComCode=(String)session.getAttribute("ComCode");
	//String PowerType=session.getAttribute("PowerType").toString();
	//ApplicationContext context=WebApplicationContextUtils.getWebApplicationContext(application); 
	//UtiILoginLogService utiILoginLogService = (UtiILoginLogService)context.getBean("utiILoginLogService");
	//UtiIAccountService utiIAccountService = (UtiIAccountService)context.getBean("utiIAccountService");
	//String userCode = utiILoginLogService.getUserCodeByAccCode(faccCode);
	//UtiIAccount account = utiIAccountService.getUtiIAccountByCode(faccCode);
	//UtiILoginLog utiILoginLog = new UtiILoginLog();
	//Date date = new Date();
	//utiILoginLog.setUserCode(userCode);
	//utiILoginLog.setLoginTime(date);
	//utiILoginLog.setAccCode(faccCode);
	//utiILoginLog.setDescription(null);
	//utiILoginLog.setHoldTime(null);
	//utiILoginLog.setExitTime(null);
	//utiILoginLog.setFlag(null);
	//utiILoginLog.setSvrCode("ims");
	//utiILoginLogService.insertMethod(utiILoginLog);
	//session.setAttribute("utiILoginLog",utiILoginLog);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>应用工具管理系统</title>
</head>
<script language="javascript">
  	function quit() 
 	{ 
        //event.returnValue="是否确定要退出页面?";
  	} 
  	
  	if (self!=top){
       top.location=self.location;
    }
</script>
<!--modify by duanfa 20110728 start 样式修改-->
<!--frameset id="mainFrame" rows="46,*" cols="1113" framespacing="0" frameborder="no"	border="0" onbeforeunload="quit();"-->
<frameset id="mainFrame" rows="100,*" cols="1113" framespacing="0" frameborder="no"	border="0" onbeforeunload="quit();">
<!--modify by duanfa 20110728 end-->
	<frame src="${ctx}/pages/head.do" name="head" id="head" scrolling="no"  noresize="noresize" />
	<!--modify by duanfa 20110728 start 样式修改-->
	<!--frameset id="menuFrame" rows="100%*" cols="198,*" framespacing="0" frameborder="no" border="0" -->
	<frameset id="menuFrame" rows="100%*" cols="246,*" framespacing="0" frameborder="no" border="0" >
	<!--modify by duanfa 20110728 end-->
		<frame src="${ctx}/menu/showMenu.do?systemCode=dms&menuId=0&menuStyle=bocins&language=C&userCode=<%=UserCode%>&powerType=1&gradesIdString=" name="main" scrolling="auto" id="main" />
	  	<frame src="${ctx}/pages/Welcome.html" name="page" scrolling="auto" id="page" width="100%" height="100%"/>
	</frameset>
</frameset>
<noframes><body>您的浏览器不支持框架!</body></noframes>
</html>