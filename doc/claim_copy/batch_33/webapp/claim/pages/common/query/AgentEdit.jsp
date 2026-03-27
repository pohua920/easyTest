<%--
****************************************************************************
* DESC       ：代理人手机号码录入界面
* AUTHOR     ： 理赔组 陈杰
* CREATEDATE ： 2013-03-08
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true">
<head>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<title><s:text name="title.modifySumClaim.agentMobileNumberEntryPage" /></title>
<%--代理人手机号码录入页面 --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body>
	<form name="fm" action="${ctx}/AgentMobile.do" method="post" onsubmit="return validateForm(this);">
		<input type="hidden" name="editType" value="insert">
		<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<c:choose>
				<c:when test="${param.editType=='insert'}">
					<tr>
						<td class="formtitle" colspan="4">
							<s:text name="query.addPhone" />
						</td>
						<%--增加手机号码 --%>
					</tr>
					<tr>
						<td class='title'>
							<s:text name="db.prpDagent.agentCode" />
							:
						</td>
						<%--代理人代码 --%>
						<td class='input'>
							<input name="AgentCode" class="codecode" description="代理人名称" value="" ondblclick="code_CodeSelect(this, 'AgentCode','0,1','Y');" onchange="code_CodeSelect(this, 'AgentCode','0,1','Y');"
								onkeyup="code_CodeSelect(this, 'AgentCode','0,1','Y');">
						</td>
						<td class='title'>
							<s:text name="db.prpDagent.agentName" />
							:
						</td>
						<%--代理人名称 --%>
						<td class='input'>
							<input name="AgentName" class="codename" description="代理人名称" value="" ondblclick="code_CodeSelect(this, 'AgentCode','-1,0','Y');" onchange="code_CodeSelect(this, 'AgentCode','-1,0','Y');"
								onkeyup="code_CodeSelect(this, 'AgentCode','-1,0','Y');">
						</td>
					</tr>
					<tr>
						<td class='title'>
							<s:text name="query.mobilePhone" />
							:
						</td>
						<%--手机号码 --%>
						<td class='input'>
							<input type="text" maxlength="11" name="MobileNo" value="" class="input" onkeypress="return pressNumber(event);" onblur="return checkNumber(this);">
						</td>
						<td class='title'>
							<s:text name="query.agentHereinafterReferred" />
							:
						</td>
						<%--代理人简称 --%>
						<td class='input'>
							<input name="agentNameSimple" class="input" description="代理人名称" value="">
						</td>
					</tr>
					<tr>
						<td class='button' colspan="4">
							<input type=button class='button' value="<s:text name="button.save.value" />" onClick="return submitFormToSave();">
							<%--保存 --%>
						</td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr>
						<td class="formtitle" colspan="4">
							<s:text name="query.modifyPhone" />
						</td>
						<%--修改手机号码 --%>
					</tr>
					<tr>
						<td class='title'>
							<s:text name="db.prpDagent.agentCode" />
							:
						</td>
						<%--代理人代码 --%>
						<td class='input'>
							<input name="AgentCode" class="codecode" description="代理人名称" value="<c:out value="${requestScope.prpDagent.agentCode}" />" ondblclick="code_CodeSelect(this, 'AgentCode','0,1','Y');"
								onchange="code_CodeSelect(this, 'AgentCode','0,1','Y');" onkeyup="code_CodeSelect(this, 'AgentCode','0,1','Y');">
						</td>
						<td class='title'>
							<s:text name="db.prpDagent.agentName" />
							:
						</td>
						<%--代理人名称 --%>
						<td class='input'>
							<input name="AgentName" class="codename" description="代理人名称" value="<c:out value="${requestScope.prpDagent.agentName}" />" ondblclick="code_CodeSelect(this, 'AgentCode','-1,0','Y');"
								onchange="code_CodeSelect(this, 'AgentCode','-1,0','Y');" onkeyup="code_CodeSelect(this, 'AgentCode','-1,0','Y');">
						</td>
					</tr>
					<tr>
						<td class='title'>
							<s:text name="query.mobilePhone" />
							:
						</td>
						<%--手机号码 --%>
						<td class='input'>
							<input type="text" maxlength="11" name="MobileNo" value="<c:out value="${requestScope.prpDagent.mobileNo}" />" class="input" onkeypress="return pressNumber(event);"
								onblur="return checkNumber(this);">
						</td>
						<td class='title'>
							<s:text name="query.agentHereinafterReferred" />
							:
						</td>
						<%--代理人简称 --%>
						<td class='input'>
							<input name="agentNameSimple" class="input" description="代理人名称简称" value="<c:out value="${requestScope.prpDagent.agentNameSimple}" />">
						</td>
					</tr>
					<tr>
						<td class='button' colspan="4">
							<input type=button class='button' value="<s:text name="button.save.value" />" onClick="return submitFormToSave();">
							<%--保存 --%>
						</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</table>
	</form>
</body>
<script language="javascript">
	function pressNumber(e) {
		var value = String.fromCharCode(e.keyCode);
		if ((value >= 0 && value <= 9))
			return true;
		else
			return false;
	}
	function checkNumber(Field) {
		var strValue = Field.value;
		if (trim(strValue) != "" && !isNumeric(strValue)) {
			errorMessage("请输入合法的数字");
			Field.focus();
			Field.select();
			return false;
		}
		return true;
	}
	function submitFormToSave() {
		var MobileNo = fm.MobileNo.value;
		if (MobileNo == "") {
			alert("请输入手机号码！");
			return false;
		}
		if (MobileNo != "" && MobileNo.length != 11) {
			alert("请输入11位手机号码！");
			return false;
		}
		var AgentCode = fm.AgentCode.value;
		if (AgentCode == "") {
			alert("请选择代理人！");
			return false;
		}
		fm.submit();
	}
</script>
</html>