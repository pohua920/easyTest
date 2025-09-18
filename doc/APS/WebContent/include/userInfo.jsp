<%@ page language="java" pageEncoding="utf-8" errorPage="/include/ExceptionHandler.jsp"%>
<%@ page import="java.net.InetAddress" %>
<%@ page import="com.tlg.util.*,org.springframework.web.context.WebApplicationContext,org.springframework.web.context.support.WebApplicationContextUtils,com.tlg.sys.service.AthSystemStatusService,com.tlg.sys.entity.AthSystemStatus"%>
<%
	//String basePath = (String)session.getAttribute("basePath");
	//String userInfoPath = request.getContextPath();
	//String userInfoBasePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+userInfoPath+"/";
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

	
	try{
		System.out.println("userInfo.jsp");
		if(session != null){
			ui = (UserInfo)session.getAttribute("userInfo");
			if(ui == null){
				session.invalidate();
				session = null;
%>
	<script>
		alert("請您重新登入!(1)");
		parent.parent.location.href = "<%=defaultPath%>/index.htm";
	</script>
<%
			}
		}

	}catch(IllegalStateException ie){
		throw ie;
	}catch(NullPointerException npe){
%>
	<script>
		alert("請您重新登入!(2)");
		parent.parent.location.href = "<%=defaultPath%>/index.htm";
	</script>
<%
	}
%>