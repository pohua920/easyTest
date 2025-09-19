<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="UTF-8"%>
<%@ page import="java.net.*,com.tlg.util.*,org.owasp.esapi.*" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	InetAddress addr = InetAddress.getLocalHost();
	String host = addr.getHostName();
	String port = String.valueOf(request.getLocalPort());
	String basePath = request.getScheme()+"://"+host+":"+port+path;
    String message = StringUtil.nullToSpace((String)request.getAttribute("message"));
    if(message == null || "".equals(message)){
		message = StringUtil.nullToSpace((String)request.getParameter("message"));
  	  	if(!"".equals(message)){
  	  		//message = new String(message.getBytes("ISO-8859-1"), "UTF-8");
			message = ESAPI.encoder().encodeForHTML(URLDecoder.decode(message,"UTF-8"));
     	}
    }
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>

<body>
	<object id="WebBrowser" width=0 height=0 classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2></object>
	<script type="text/javascript">
	  var message = "<%=message%>";
	  if(message != "null" && message != "") alert(message);
	  
		window.setTimeout("close_self()",1000);
		function close_self() {
			try{
				var webBrowser = document.getElementById("WebBrowser");
				webBrowser.ExecWB(45,1);
					//document.all.WebBrowser.ExecWB(45,1); 
			} catch(e){
				//window.open("wwoa","_self");
	 			window.close(); 
			}
		}
	</script>
</body>
</html>