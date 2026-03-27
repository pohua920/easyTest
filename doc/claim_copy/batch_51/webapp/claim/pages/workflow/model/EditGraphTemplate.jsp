<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
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
<link href="/claim/css/Standard.css" rel="stylesheet" type="text/css">
</head>
<body>
  <table class="common" align="center">
	<tr>
			<td class="caption" colspan="4"><B><s:text name="workflow.viewOaConfiguration" /></B>
			<%--可视化工作流配置 --%></td>
	</tr>
    <tr>
			<td colspan=4 " align="center"><applet name=wfPad id=wfPad code=com.sinosoft.workflow.WfPad.class archive="/claim/workflow/model/wfpad.jar" width=100% height=410>
          <param name = "actionType" value = "<%=request.getAttribute("actionType")%>" >
          <param name = "modelNo" value = "<%=request.getAttribute("modelNo")%>" >
          <param name = "postServlet" value = "processGraphTemplate" >
					alt="Your browser understands the &lt;APPLET&gt; tag but isn't running the applet, for some reason." Your browser is completely ignoring the &lt;APPLET&gt; tag!
				</applet></td>
    </tr>
  </table>
</body>
</html>