<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/claim-platform.tld" prefix="app" %>
<html>
<head>
<title></title>
<jsp:include page="/platform/uwcondition/StaticJavascript.jsp" />
<link href="/claim/platform/css/Standard.css" rel="stylesheet" type="text/css">
</head>
<body style="margin: 0px; scroll: no; overflow: hidden;">
<script language="javascript">
	function disableButton(field)
	{
		fm.nodeIndex.value = field.value;
		var nodeIndex = parseInt(fm.nodeIndex.value);
		if(parseInt(fm.nodeNo[nodeIndex].value) == 1)
		{
			fm.n2.disabled = "true";
		}
		else
		{
			fm.n2.disabled = null;
		}
	}
</script>
<form name="fm" action="" method="POST">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td style="width: 145px;">
			<div style="border: solid 1px #000080; width:140px; height:458px; overflow:auto;">
				<table class="common" cellpadding="3" cellspacing="1">
					<tr>
						<td class="top"><strong><s:text name="archive.level"/></strong></td><%--级别  --%>
					</tr>
					<INPUT type="hidden" name="nodeIndex" value="-10">
					<%int nodeIndex=0;%>
					<logic:present name="swfNodeList">
					<logic:iterate id="nodeDto" name="swfNodeList">
						<tr>
							<td class="page">
								<input type="radio" name="radioSelect" value="<%=nodeIndex++%>" 
								       onclick="disableButton(this);">
								<bean:write name="nodeDto" property="nodeName"/>
								<INPUT type="hidden" name="nodeNo" value='<bean:write name="nodeDto" property="nodeNo"/>'>
							</td>
						</tr>
					</logic:iterate>
					</logic:present>
					<tr>
						<td class="page">
							<center>
								<input type="button" name="n1" value="<s:text name='button.Factor.value'/>" class="button3" <%-- 因子值 --%>
								       onclick="prepareUpdate('prepareUpdateUtiUwCondition');">
								<input type="button" name="n2" value="<s:text name='button.Personnel.value'/>"  class="button3"<%-- 人员 --%>
								       onclick="prepareUpdate('prepareUpdateUtiUwLevel');" disabled="disabled">
							</center>
						</td>
					</tr>
					<tr>
						<td class="top" ><strong><s:text name="regist.prpLregist.registMain"/></strong></td> <%-- 基本信息 --%>
					</tr>
					<tr>
						<td class="page" ><s:text name="uwcondition.TypeAuditing"/>：<bean:write name="conditionDto" property="uwTypeName"/></td> <%-- 审核类型 --%>
					</tr>
					<tr>
						<td class="page" ><s:text name="uwcondition.AuditDepartment1"/>：<br/><bean:write name="conditionDto" property="comCode"/>-<bean:write name="conditionDto" property="comName"/></td> <%-- 审核部门1 --%>
						<INPUT type="hidden" name="comCode" value='<bean:write name="conditionDto" property="comCode"/>'>
					</tr>
					<tr>
						<td class="page" ><s:text name="archive.riskClass"/>：<br/><bean:write name="conditionDto" property="classCode"/>-<bean:write name="conditionDto" property="className"/></td><%-- 险类 --%>
					</tr>
					<tr>
						<td class="page" ><s:text name="regist.prpLregist.riskCodeName"/>：<br/><bean:write name="conditionDto" property="riskCode"/></td><%-- 险种 --%>
					</tr>
					<tr>
						<td class="page" ><s:text name="uwcondition.Template"/>：<br/><bean:write name="conditionDto" property="modelName"/></td><%-- 模板 --%>
					</tr>
				</table>
			</div>
		</td>
		<td rowspan="2">
			<div style="border: solid 1px #000080; width:630px; height:485px;">
				<iframe src='/claim/platform/uwcondition/EditUwConditionRight.jsp' 
				        name="iframe1" width="630px;" height="485px;">
				</iframe>
			</div>
		</td>
	</tr>
	<tr>
		<td style="width: 145px;">
			<div style="height: 30px; width: 140px;">
				<center>
					<table>
						<tr>
							<td><input type="button" name="btnBack" value="<s:text name='button.ReturnResultsList.value'/>" class="longbutton" onclick="backOverview();"></td><%-- 返回到结果列表 --%>
						</tr>
					</table>
				</center>
			</div>
		</td>
	</tr>
</table>
</form>
<script language="javascript">
	function prepareUpdate(actionType)
	{
		var nodeIndex = parseInt(fm.nodeIndex.value);
		if(nodeIndex != -10)
		{
			var nodeNo = fm.nodeNo[nodeIndex].value;
			var comCode = fm.comCode.value;
			document.iframe1.prepareUpdate(actionType, nodeNo,comCode);
		}
	}
	function backOverview()
	{
			fm.action = "/claim/processUwCondition.do?actionType=queryContinue";
			fm.submit();
	}
</script>
</body>
</html>