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
<table class="common" cellpadding="5" cellspacing="1" align="center">
	<tr>
		<td class="top">
			<-- <s:text name="prompt.uwcondition.ResultCondition"/><%-- 选择某级别，设置该级别的双核条件。 --%>
		</td>
	</tr>
</table>
<!--
<table class="common" cellpadding="5" cellspacing="1" align="center">
	<tr>
		<td colspan="4" class="top"><strong>基本信息</strong></td>
	</tr>
	<tr>
		<td width="15%" class="page">审核类型：</td>
		<td width="35%" class="page">
			<bean:write name="UtiUwConditionDto" property="uwType"/>
			-
			<bean:write name="UtiUwConditionDto" property="uwTypeName"/>
		</td>
		<td width="15%" class="page">审核部门：</td>
		<td width="35%" class="page">
			<bean:write name="UtiUwConditionDto" property="comCode"/>
			-
			<bean:write name="UtiUwConditionDto" property="comName"/>
		</td>
	</tr>
	<tr>
		<td width="15%" class="page">险种大类：</td>
		<td width="35%" class="page">
			<bean:write name="UtiUwConditionDto" property="riskCategoryCode"/>
			-
			<bean:write name="UtiUwConditionDto" property="riskCategoryName"/>
		</td>
		<td width="15%" class="page">&nbsp;</td>
		<td width="35%" class="page">&nbsp;</td>
	</tr>
	<tr>
		<td width="15%" class="page">险类：</td>
		<td width="85%" class="page" colspan="3">
			<bean:write name="UtiUwConditionDto" property="classCode"/>
			-
			<bean:write name="UtiUwConditionDto" property="className"/>
		</td>
	</tr>
	<tr>
		<td width="15%" class="page">险种：</td>
		<td width="85%" class="page" colspan="3">
			<bean:write name="UtiUwConditionDto" property="riskCode"/>
		</td>
	</tr>
	<tr>
		<td width="15%" class="page">模板：</td>
		<td width="35%" class="page">
			<bean:write name="UtiUwConditionDto" property="modelNo"/>
			-
			<bean:write name="UtiUwConditionDto" property="modelName"/>
		</td>
		<td width="15%" class="page">&nbsp;</td>
		<td width="35%" class="page">&nbsp;</td>
	</tr>
</table>
-->
<form name="fm" action="" method="POST">
</form>
<script language="javascript">
	 function prepareUpdate(actionType, nodeNo ,comCode)
	 {
			fm.action = "/claim/processUwCondition.do?actionType=" + actionType +"&nodeNo=" + nodeNo+"&comCode=" +comCode;
			fm.submit();
	 }
</script>
</body>
</html>
