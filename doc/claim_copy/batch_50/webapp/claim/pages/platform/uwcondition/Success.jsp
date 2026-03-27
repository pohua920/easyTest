<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/claim-platform.tld" prefix="app" %>
<html>
<head>
	<jsp:include page="/platform/uwcondition/StaticJavascript.jsp" />
	<link href="/claim/platform/css/Standard.css" rel="stylesheet" type="text/css">
</head>
<body>
<table width="100%" height="80%" border="0" cellpadding="5" cellspacing="0">
  <tr>
    <td align="center" class="bg">
			<img src="images/imgSuccess.gif">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<font color="green"><s:text name="prompt.uwcondition.Sucess"/></font><%-- ²Ù×÷³É¹¦£¡ --%>
		</td>
  </tr>
	<logic:present name="SuccessMessage">
	<tr>
    <td align="center" class="bg">
			<font color="green"><bean:write name="SuccessMessage"/></font>
		</td>
  </tr>
	</logic:present>
</table>
<form name="fm" action="" method="POST">
</form>
<script language="javascript">
	 function nodeCondition(actionType, nodeNo)
	 {
			fm.action = "/claim/processUwCondition.do?actionType=" + actionType + "&nodeNo=" + nodeNo;
			fm.submit();
	 }
</script>
</body>
</html>
