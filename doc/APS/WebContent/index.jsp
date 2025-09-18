<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" errorPage="/include/ExceptionHandler.jsp"%>
<%@ page import="java.net.*,com.tlg.util.*,org.springframework.web.context.WebApplicationContext,org.springframework.web.context.support.WebApplicationContextUtils,com.tlg.sys.service.AthSystemStatusService,com.tlg.sys.entity.AthSystemStatus" %>
<%
	InetAddress addr = InetAddress.getLocalHost();
	String host = addr.getHostName();
	//String host = request.getServerName();
	String port = String.valueOf(request.getLocalPort());
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+host+":"+port+path;
    String redirectUrl = basePath + "/" + "index.htm";
    
    System.out.println("index.jsp");
    String guid = new GUID().toString(30);
    
    UserInfo ui = null;
	ServletContext sc = request.getSession().getServletContext();
	String sysPort = StringUtil.nullToSpace((String)sc.getAttribute("sysPort"));
	String useLocalContext = StringUtil.nullToSpace((String)sc.getAttribute("useLocalContext"));
	String defaultPath = "";
	if(!StringUtil.isSpace(sysPort) && sysPort.indexOf(":") == -1){
		defaultPath = request.getScheme()+"://" + InetAddress.getByName(request.getServerName()).getHostAddress() + ":" + sysPort + "/SYS";
	}else{
		defaultPath = request.getScheme()+"://" + sysPort + "/SYS";
	}
	if(!"Y".equals(useLocalContext)){
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(sc);
		AthSystemStatusService athSystemStatusService = (AthSystemStatusService)context.getBean("athSystemStatusService");
		
		Result result = athSystemStatusService.findAthSystemStatusBySystemPriority("SYS");
		if(result.getResObject() != null){
			AthSystemStatus athSystemStatus = (AthSystemStatus)result.getResObject();
			defaultPath = request.getScheme()+ "://" + InetAddress.getByName(athSystemStatus.getHost()).getHostAddress() + ":" + athSystemStatus.getPort() + "/" + athSystemStatus.getContextpath();
		}
	}
%>
<html>
<head>
<!-- mantis：OTH0078，處理人員：BJ085，需求單編號：OTH0078 更名相關-APS、CWP、SYS系統中有台壽保產險、tlg、網域名稱更為新名稱 -->
<title>中國信託產險</title>
</head>
		<script>
			alert("請您重新登入~(3)");
			parent.parent.location.href = "<%=defaultPath%>/index.htm";
		</script>
</html>
