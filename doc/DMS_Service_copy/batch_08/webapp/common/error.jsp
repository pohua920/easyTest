<%@ page contentType="text/html;charset=utf-8" isErrorPage="true" %>
<%@page import="java.util.Enumeration"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="ins.framework.exception.*"%>

<%
String title = ""; //信息
String content = ""; //详细信息
StringWriter stringWriter = new StringWriter();

if(exception==null){
	exception = (Throwable)request.getAttribute("javax.servlet.error.exception");
}

if(exception!=null){
	Throwable throwable = null;
	
	if(exception instanceof BusinessException){
		throwable = (BusinessException)exception;
	}else if(exception instanceof ServletException){
		throwable = ((ServletException)exception).getRootCause();
	}else {
		throwable = exception;
	}
	
	title = throwable.getMessage();
	throwable.printStackTrace(new PrintWriter(stringWriter));
}
%>
<html>
<head>
<title>Error Page</title>
<style>
	td{font-size:9pt;}
</style>
<script language=javascript>
function shContent()
{
  if(content.style.display=='')
    content.style.display = 'none';
  else
    content.style.display = '';
}
</script>
</head>
<body>

  <table class=common align=center>
    <tr>
      <td class=formtitle colspan="2">系统提示</td>
    </tr>
    <tr>
      <td align=center>
        <img src='${pageContext.request.contextPath}/pages/image/failure.gif'
          style='cursor:hand' alt='详细信息' onclick="shContent()">
      </td>
      <td class="common">
        <%=title%>
      </td>
    </tr>
  </table>

<div id="content" style="display:none">
	<pre><%=stringWriter%></pre>
	<table border="1">
		<tr>
			<th>request.getAttributeName</th>
			<th>request.getAttribute</th>
		</tr>
<%
		Enumeration enums =  request.getAttributeNames();
		while(enums.hasMoreElements()){
			String key = (String)enums.nextElement();
			out.println( "<tr><td>");
			out.println( key );
			out.println( " </td><td>");
			out.println( request.getAttribute(key) );
			out.println( " </td></tr>");
		}
%>
	</table>

</div>
</body>
</html>