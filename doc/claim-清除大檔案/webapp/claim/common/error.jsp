<%@page import="org.apache.commons.lang.exception.ExceptionUtils"%>
<%@ page contentType="text/html;charset=GBK" isErrorPage="true" %>
<%@page import="java.util.Enumeration"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="ins.framework.exception.*"%>
<html>
<head>
<title>Error Page</title>
<%@ include file="/common/taglibs.jsp"%> 

<style>
	td{font-size:11pt;}
.button_ty,.button_ty_over{color:#000;border:1px solid #94D8E4;padding:1px 5px 1px 5px;height:20px;}
.button_ty{background: #fff url(${ctx}/pages/image/btbg_blue.gif) repeat-x left left -2px;}
.button_ty_over{background: #fff url(${ctx}/pages/image/btbg_orange.gif) repeat-x left left -2px;}
</style>
<script language=javascript>
function shContent()
{
  if(content.style.display=='')
    content.style.display = 'none';
  else
    content.style.display = '';
}

function closeIFrame() {
	if(document.parentWindow.name=="msgIFrame"){
	  var ifr = document.parentWindow.parent.document.getElementById("msgIFrame"); 
		document.parentWindow.parent.document.body.removeChild(ifr);
	}
}

function loadBody(){
  if(document.parentWindow.name=="msgIFrame"){
    trCloseButton.style.display = "";
  }
}
function closeMethod(){
  if(parent!=null && parent.window!=null){
    if(parent.submitDlg!=null){
       parent.submitDlg.hide();
       // PNC-8997 互碰自赔案件,如果查勘提交不符合条件，关闭提示信息後，最下方一排按钮全部置灰，建议关闭提示信息後刷新页面。
       if(parent.reloadPage!=undefined && parent.reloadPage!=null){
 	  	parent.reloadPage();
 	  }
    }
 	else{
 	  window.close();
 	}
  }else{
   	window.location="about:blank";
  }
}
function refreshMethod(){
	if(parent!=null && parent.window!=null){
		if(parent.submitDlg!=null){
			parent.submitDlg.hide();
			// PNC-8997 互碰自赔案件,如果查勘提交不符合条件，关闭提示信息後，最下方一排按钮全部置灰，建议关闭提示信息後刷新页面。
			if(parent.reloadPage!=undefined && parent.reloadPage!=null){
		 	  	parent.reloadPage();
		 	}
		}else{
			parent.window.close();
		}
	}
}

</script>
</head>
<body onload="loadBody()">

  <table class=common align=center>
    <tr>
      <td class=formtitle colspan="2"><s:text name="prompt.system.title"/><%--系统提示 --%><img src='${pageContext.request.contextPath}/common/images/bgClaimFailure.gif'
          style='cursor:hand' alt='详细信息' onclick="shContent()"></td>
    </tr>
    <tr>
      <td width="100%"> 
        <pre>
<%
if(exception!=null){
	out.print( exception.getLocalizedMessage());
}else{
    out.print( "Unknow exception");
}
%>
        </pre>
      </td>
      <td class="common"> 
      </td>
    </tr>
    <tr id="trCloseButton" >
      <td colspan="2" align="center">
		<input type="button" value=" <s:text name="button.close.value"/>" onclick="closeMethod();" class="button_ty"><%--关 闭  --%>
		<input type="button" value=" <s:text name="button.return.value"/>" onclick="refreshMethod();" class="button_ty"> <%--返 回  --%>
      </td>
    </tr>

  </table>

<div id="content" style="display:none"> 
	<table border="1" width="90%" align="center">
	 	<caption><b><font color="#0000ff">Exception StackTrace</font></b></caption>
		<tr bgcolor="bfe7ee">
			<td width="100%">
				<textarea readonly="readonly" rows="20" style="width:100%"><%=ExceptionUtils.getFullStackTrace(exception) %></textarea>
			</td>
		</tr> 
	</table>	
	 <table border="1" width="90%" align="center">
	 	<caption><b><font color="#0000ff">Request Attributes</font></b></caption>
		<tr bgcolor="bfe7ee">
			<th width="30%">key</th>
			<th>value</th>
		</tr>
<%
		Enumeration enums =  request.getAttributeNames();
		while(enums.hasMoreElements()){
			String key = (String)enums.nextElement(); 
			out.println( "<tr><td>");
			out.println( key );
			out.println( "</td><td>");
			out.println( request.getAttribute(key) ); 
			out.println( "</td></tr>");
		}
%>
	</table>
	<table border="1" width="90%" align="center" background="#ff0000">
		<caption><b><font color="#0000ff">Request Parameters</font></b></caption> 
		<tr bgcolor="bfe7ee">
			<th width="30%">key</th>
			<th>value</th>
		</tr>	  
<%
		enums =  request.getParameterNames();
		while(enums.hasMoreElements()){
			String key = (String)enums.nextElement(); 
			out.println( "<tr><td>");
			out.println( key );
			out.println( " </td><td>");
			out.println( request.getParameter(key) ); 
			out.println( " </td></tr>");
		}
%>
	</table>
	<table border="1" width="90%" align="center" background="#ff0000">
		<caption><b><font color="#0000ff">Session</font></b></caption> 
		<tr bgcolor="bfe7ee">
			<th width="30%">key</th>
			<th>value</th>
		</tr>	  
<%
		enums =  session.getAttributeNames();
		while(enums.hasMoreElements()){
			String key = (String)enums.nextElement(); 
			out.println( "<tr><td>");
			out.println( key );
			out.println( " </td><td>");
			out.println( session.getAttribute(key)); 
			out.println( " </td></tr>");
		}
%>
	</table>	
</div>
</body>
</html>