<%--
****************************************************************************
* DESC       ： 理赔流转讨论留言保存页面
* AUTHOR     ： Sunhao
* CREATEDATE ： 2004-07-28
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true">
<head>
<title><s:text name="message.comment" /></title>
<!-- 留言区 -->
<script language="javascript">
	//提交表单，保存留言内容
	function saveMessage() {
		if (!validateForm(fm)) {
			return false;
		}
		fm.saved.value = "1";
		fm.action = "${ctx}/messageSave.do";
		fm.target = "ShowFrame";
		fm.submit();
	}
	function queryMessage() {
		fm.action = "${ctx}/messageQuery.do";
		fm.target = "ShowFrame";
		fm.submit();

	}
</script>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard_green.css">
</head>
<body onload="initPage();queryMessage();">
	<form name="fm" method="post" action="${ctx}/messageSave.do" onsubmit="return validateForm(this);">
		<table width="100%" border="0" class="common" cellpadding="5" cellspacing="1">
			<tr style="display: none">
				<td class="title">
					<s:text name="prompt.queRegist.RegistNo" />
					：
				</td>
				<!-- 报案号 -->
				<td class="input">
					<input name="prpLmessageRegistNo" style="width: 100%" class="readonly" readonly="true" value="${prpLmessage.id.registNo }">
				</td>
				<td class="title">
					<s:text name="db.prpCprofit.policyNo" />
					：
				</td>
				<!-- 保单号 -->
				<td class="input">
					<input name="prpLmessagePolicyNo" style="width: 100%" class="readonly" readonly="true" value="${prpLmessage.policyNo}">
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="check.claimNum" />
					：
				</td>
				<!-- 赔案号 -->
				<td class="input">
					<input name="prpLmessageClaimNo" style="width: 100%" class="readonly" readonly="true" value="${prpLmessage.claimNo}">
				</td>
				<td class="title">
					<s:text name="db.prpCmain.insuredName" />
					：
				</td>
				<!-- 被保险人名称 -->
				<td class="input">
					<input name="insuredName" style="width: 100%" class="readonly" readonly="true" value="${insuredName }">
				</td>
				<td class="title" style="display: none">
					<s:text name="db.prpCmain_loan.riskCode" />
					：
				</td>
				<!-- 险种代码 -->
				<td class="input" style="display: none">
					<input name="prpLmessageRiskCode" class="readonly" readonly="true" value="${prpLmessage.riskCode}">
				</td>
			</tr>
			<tr style="display: none">
				<td class="title">
					<s:text name="db.prpLregist.inputDate" />
					：
				</td>
				<!-- 输单日期 -->
				<td class="input">
					<input name="prpLmessageInputDate" class="readonly" readonly="true" value="${prpLmessage.inputDate}">
				</td>
				<td class="title">
					<s:text name="guarantee.nodeType" />
					：
				</td>
				<!-- 节点类型 -->
				<td class="input">
					<input name="prpLmessageNodeTypeName" class="readonly" readonly="true" value="${prpLmessage.nodeTypeName}">
					<input type="hidden" name="prpLmessageNodeType" value="${prpLmessage.nodeType}">
				</td>
			</tr>
			<tr style="display: none">
				<td class="title">
					<s:text name="db.prpCmain.operatorCode" />
					：
				</td>
				<!-- 操作员代码 -->
				<td class="input">
					<input name="prpLmessageOperatorCode" class="readonly" readonly="true" value="${prpLmessage.operatorCode}">
				</td>
				<td class="title">
					<s:text name="message.operateName" />
					：
				</td>
				<!-- 操作员姓名 -->
				<td class="input">
					<input name="prpLmessageOperatorName" class="readonly" readonly="true" value="${prpLmessage.operatorName}">
				</td>
			</tr>
			<tr>
				<td class="title" colspan="4">
					<div align="center" class="style1">
						<s:text name="message.writeMessage" />
					</div>
				</td>
				<!-- 记录信息 -->
			</tr>
		</table>
		<table width="100%">
			<tr>
				<td colspan="2" align="center">
					<textarea name="prpLmessageContext" cols="70" rows="5"></textarea>
				</td>
			</tr>
			<tr>
				<td align=center>
					<input type="hidden" name="saved" value="0">
					<input type="hidden" name="prpLmessageSave" value="editMessage">
					<input type="hidden" name="registNo" value="${prpLmessage.id.registNo}">
					<input type="hidden" name="claimNo" value="${prpLmessage.claimNo}">
					<c:if test="${prpLmessage.nodeType=='taskView'}">
						<input type="button" name="Submit" value="<s:text name="button.save.value" />" class="button" onclick="return saveMessage();" disabled="disabled">
						<!-- 保存 -->
				</td>
				<td>
					<input type=button name=buttonBack class='button' value="<s:text name="button.return.value" />" onclick="return history.back();">
					<!-- 返回 -->
					</c:if>
					<c:if test="${prpLmessage.nodeType!='taskView'}">
						<input type="button" name="Submit" value="<s:text name="button.save.value" />" class="button" onclick="return saveMessage();">
						<!-- 保存 -->
				</td>
				<td>
					<input type="reset" class="button" name="reset" value="<s:text name="button.reset.value" />">
					<!-- 清除 -->
					</c:if>
					<input type="button" name="query" class="button" value="<s:text name="button.query.value" />" onclick="return queryMessage();" style="display: none;">
					<!-- 查询 -->
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<iframe name="ShowFrame" id="ShowFrame" height="400" width="450" style='Z-INDEX: 1; WIDTH: 100%;' 0' vspace='0' frameborder='0' scrolling='auto' src="#"></iframe>
				</td>
			</tr>
		</table>
	</form>
</BODY>
</html>