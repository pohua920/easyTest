<%@ page contentType="text/html; charset=GBK" %>
<%@ page isErrorPage="true"%>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%
  String strCaption = (String)request.getAttribute("caption"); //标题
  String strTitle = (String)request.getAttribute("title"); //信息
  String strContent = (String)request.getAttribute("context"); //详细信息
%>

<html>
<head>
<style >
BODY {
	BORDER-TOP-WIDTH: 0px;
    BORDER-LEFT-WIDTH: 0px;
    BORDER-BOTTOM-WIDTH: 0px;
    BORDER-RIGHT-WIDTH: 0px;
    SCROLLBAR-FACE-COLOR:#FFFFFF;
    SCROLLBAR-HIGHLIGHT-COLOR:#999999;
    SCROLLBAR-SHADOW-COLOR:#999999;
    SCROLLBAR-3DLIGHT-COLOR:#FFFFFF;
    SCROLLBAR-ARROW-COLOR:#ffffff;
    SCROLLBAR-TRACK-COLOR:#FFFFFF;
    SCROLLBAR-DARKSHADOW-COLOR:#FFFFFF;
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
  <link href="/platform/css/Standard.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td align="center" class="bg">
      <table width="80%" border="0" cellspacing="0" cellpadding="5">
        <tr>
          <td height="35" align="center" class="menu2"><strong><%=strCaption%></strong></td>
        </tr>
        <tr>
          <td align="center" class="menu2"><a href="#"><img src="/platform/images/imgError.gif" style='cursor:hand' alt='详细信息' onclick="showContent()" width="74" height="72" border="0" align="absmiddle"></a><%=strTitle%></td>
        </tr>
				<tr align="center">
					<td>
						<input type="button" class="button" value="<s:text name='button.return.value'/>" onclick="window.history.back();"><%-- 返 回 --%>
					</td>
				</tr>
        <tr id="trContent" style="display:none">
          <td align=left colspan="2" width="80%">
            <pre>
            <%
            //=strContent
            %>
            </pre>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<!--双核权限用。-->
<script language="javascript">
	 function nodeCondition(actionType, nodeNo)
	 {
			fm.action = "/platform/processUwCondition.do?actionType=" + actionType + "&nodeNo=" + nodeNo;
			fm.submit();
	 }
</script>
</body>
</html>