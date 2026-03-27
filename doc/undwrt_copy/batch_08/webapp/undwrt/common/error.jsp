<%@ page contentType="text/html;charset=GBK" isErrorPage="true" %>
<%@page import="java.util.Enumeration"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="ins.framework.exception.*"%>
<%-- <%@include file="http://localhost:7001/undwrt/common/meta_js.jsp" %>
<%@include file="http://localhost:7001/undwrt/common/meta.jsp" %> --%>
<jsp:useBean id="excptionMessage" class="com.sinosoft.undwrt.common.servlet.ExceptionMessageBean" scope="page"></jsp:useBean>
<%
excptionMessage.executeExcptionMessage(exception,request);
%>
<html>
<head>
<title>Error Page</title>
<%@ include file="/common/taglibs.jsp"%> 

<style>
	td{font-size:9pt;}
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
       // PNC-8997 互碰自赔案件,如果查勘提交不符合条件，关闭提示信息后，最下方一排按钮全部置灰，建议关闭提示信息后刷新页面。
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
			// PNC-8997 互碰自赔案件,如果查勘提交不符合条件，关闭提示信息后，最下方一排按钮全部置灰，建议关闭提示信息后刷新页面。
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
      <td class=formtitle colspan="2"><s:text name='prompt.systemShow'/></td>
    </tr>
    <tr>
      <td align=center>
        <img src='${pageContext.request.contextPath}/common/images/failure.gif'
          style='cursor:hand' alt='<s:text name='prompt.messages.detailedMessage'/>' onclick="shContent()">
      </td>
      <td class="common">
        <%=excptionMessage.getTitle()%>
      </td>
    </tr>
    <tr id="trCloseButton" >
      <td colspan="2" align="center">
		<input type="button" value=" <s:text name='undwrt.close'/> " onclick="closeMethod();" class="button_ty">
		<input type="button" value=" <s:text name='prompt.back'/> " onclick="refreshMethod();" class="button_ty">
      </td>
    </tr>

  </table>

<div id="content" style="display:none">
	<pre><%=excptionMessage.getExceptionMessage()%></pre>
	<table border="1">
		<tr>
			<th>request.getAttributeName</th>
			<th>request.getAttribute</th>
		</tr>
<%
		Enumeration enums =  request.getAttributeNames();
		while(enums.hasMoreElements()){
			String key = (String)enums.nextElement();
			//System.out.println("zzzzzzzzzzzzzzzzzzzzzzz   " + key);
			out.println( "<tr><td>");
			out.println( key );
			out.println( " </td><td>");
			out.println( request.getAttribute(key) );
			//System.out.println("ccccccccccccccccc  " + request.getAttribute(key));
			out.println( " </td></tr>");
		}
%>
	</table>

</div>
</body>
</html>