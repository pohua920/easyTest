<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %> 
<%@ page import="com.tlg.util.UserInfo,com.tlg.util.EventLogger"%>
<%@ page isErrorPage="true" %>
<%
String path = request.getContextPath();
String basePath = (String)session.getAttribute("basePath");
//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
EventLogger el = new EventLogger(this.getClass());
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
  <head>
    <title>發生異常錯誤頁面</title>
    <script>//show message
      var message = "<%=request.getAttribute("message")%>";
      if(message != "null" && message != "") alert(message);
    </script>
  </head>
  
  <body>
  <s:property value="exception.message"/>
  <s:property value="exceptionStack"/>
  <%
	try{
		if(exception==null){
		   	if(request.getAttribute("exception")!=null){
				exception = (Exception)request.getAttribute("exception");
			}
		}
		if(exception!=null) {
	    	String exceptionName = exception.getClass().getName();
	    	el.logThrow(exceptionName,exception);
	    	
		  	if(exceptionName.indexOf("SQLException")!=-1){
		  		exception.printStackTrace();
		    %>
				發生錯誤：資料庫發生異常，請洽計算機中心人員處理
			<%
			}else if(exceptionName.indexOf("NullPointerException")!=-1){
				exception.printStackTrace();
			%>
				發生錯誤：資料庫資料錯誤，請先檢查資料庫連線狀況，或洽詢系統相關人員修正資料
			<%
			}else if(exceptionName.indexOf("IllegalStateException")!=-1){
			%>
			<Script>
				//alert("逾時未使用系統，系統已將您登出離線\n\n請您重新登入系統");
				alert("請您重新登入系統～～");
				parent.parent.location.href = '<%=response.encodeURL(basePath+"/index.htm")%>';
			</Script>	
			<%
			}else{
				exception.printStackTrace();
			%>
				發生錯誤：目前無法使用此頁面功能，請洽詢系統相關人員
			<%
			}
      		out.println("<br><font color=\"#FFFFFF\">ExceptionHandler:"+exception.toString()+"</font><br>");
      		out.println("<table border='0' width='100%'>");
      		out.println("<tr>");
      		out.println("<td align='center'>");
      		out.println("<input type='button' value='回上頁' onClick='javascript: history.go(-1)'>");
      		out.println("</td>");
      		out.println("</tr>");
      		out.println("</table>");
		}
		exception = null;
	}catch(IllegalStateException ie){
		%>
			<Script>
				alert("逾時未使用系統，系統已將您登出離線\n\n請您重新登入系統");
				parent.parent.location.href = '<%=response.encodeURL(basePath+"/index.htm")%>';
			</Script>			
		<%
	}
  %>
  </body>
</html>