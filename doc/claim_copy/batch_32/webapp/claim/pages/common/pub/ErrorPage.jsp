<%--
 *************************************************************************
 * 程序名称: MessagePage.jsp
 * 程序功能: 信息反馈页面
 * 最近更新人: 理赔组
 * 最近更新日期: 2013-01-30
 ****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ page isErrorPage="true"%>
<%@ page import="com.sinosoft.claim.util.ExceptionHelper" %>
<%@ include file="/common/taglibs.jsp"%>  
<%
  	ExceptionHelper exceptionHelper = new ExceptionHelper();
  	exceptionHelper.process(request, response);
  	String caption = (String) request.getAttribute("caption");
  	String content = (String) request.getAttribute("content");
  	String message = (String) request.getAttribute("message");
  	String needAlert = request.getParameter("needAlert");
  	if (needAlert == null || needAlert.trim().length() == 0) {
  		needAlert = "false";
  	}
  %>
<html>
<head>
<style>
BODY {
	BORDER-TOP-WIDTH: 0px;
	BORDER-LEFT-WIDTH: 0px;
	BORDER-BOTTOM-WIDTH: 0px;
	BORDER-RIGHT-WIDTH: 0px;
	SCROLLBAR-FACE-COLOR: #FFFFFF;
	SCROLLBAR-HIGHLIGHT-COLOR: #999999;
	SCROLLBAR-SHADOW-COLOR: #999999;
	SCROLLBAR-3DLIGHT-COLOR: #FFFFFF;
	SCROLLBAR-ARROW-COLOR: #ffffff;
	SCROLLBAR-TRACK-COLOR: #FFFFFF;
	SCROLLBAR-DARKSHADOW-COLOR: #FFFFFF;
}
</style>
<script language=javascript>
  /**
   * show/hide  Content
   */
  function showContent()
  {
    if(trContent.style.display=='')
      trContent.style.display = 'none';
    else
      trContent.style.display = '';
  }
  </script>
<link href="/claim/platform/css/Standard.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="center" class="bg">
				<table width="80%" border="0" cellspacing="0" cellpadding="5">
					<tr>
						<td height="35" align="center" class="menu2">
							<strong><%=caption%></strong>
						</td>
					</tr>
					<tr>
						<td align="center" class="menu2">
							<a href="#"><img src="/claim/images/imgError.gif" style='cursor: hand' alt='详细信息' ondblclick="showContent()" width="74" height="72" border="0" align="absmiddle"></a><%=content%></td>
						<%--
        <%--</tr>
				<tr align="center">
					<td>
						<input type="button" class="button" value="返 回" onclick="window.history.back();">
					</td>
				</tr>--%>
					<tr id="trContent" style="display: none">
						<td align=left colspan="2" width="80%">
							<pre>
            <%=message%>
            </pre>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>
