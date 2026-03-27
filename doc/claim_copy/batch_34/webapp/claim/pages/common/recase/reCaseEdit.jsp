<%--
****************************************************************************
* DESC       ：申请重开赔案主画面
* AUTHOR     ：理赔组
* CREATEDATE ：2013-03-15
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@page import="javax.servlet.http.HttpServletRequest"%>
<%@ page contentType="text/html; charset=GBK" %>
<%@page import="com.sinosoft.claim.schema.model.PrpLclaim"%>
<%@page import="com.sinosoft.claim.schema.model.PrpLrecase"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta_js.jsp"%>
<%@page  import="ins.framework.common.DateTime"%>
<%
	PrpLclaim prpLclaim = (PrpLclaim) request.getAttribute("prpLclaim");
	PrpLrecase prpLrecase = (PrpLrecase) request.getAttribute("prpLrecase");
	String ended = "";
	String recaseend = "";
	if (prpLclaim != null && prpLclaim.getEndCaseDate() != null) {
		ended = "y"; //已结案
	} else {
		ended = "n";
	}
	if (prpLrecase != null) {
		if (prpLrecase.getCloseCaseDate() != null) {
			recaseend = "y";//重开赔案已结束
		} else {
			recaseend = "n";
		}
	} else {
		recaseend = String.valueOf(request.getAttribute("recaseend"));
	}
%>
<html>
<head>
<title><s:text name="title.recaseBeforeEdit.reopenClaim" /></title>
<%--重开赔案--%>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
<script language='javascript'>
   function checkendCase() {
		var ended = "<%=ended%>";
		var recaseend = "<%=recaseend%>"
		if (ended == "n") {
			alert("此案件未結案，不能重開賠案！");
			fm.buttonSave.disabled = true;
			return false;
		}
		if (recaseend == "n") {
			alert("此案件上次重開賠案尚未結案，不能再次重開賠案！");
			fm.buttonSave.disabled = true;
			return false;
		}
		return true;
	}

	function submitForm() {
		if (checkendCase() == true) {
			fm.submit();
		}
	}

	function resetForm() {
		fm.reset();
	}
  </script>
</head>
<%-- 调用loadForm 初始化页面 --%>
<body class="interface" onload="initPage();">
	<form name=fm action="${ctx}/recase/reCaseEditPost.do" method="post" onsubmit="return validateForm(this);">
		<s:token></s:token>
		<table class="common" cellpadding="5" cellspacing="1">
			<tr>
				<td class=formtitle colspan="4">
					<s:text name="archive.reopenClaim" />
				</td>
				<%--重开赔案--%>
			</tr>
			<tr>
				<td class="title">
					<s:text name="check.claimNum" />
					：
				</td>
				<%--赔案号--%>
				<td class="input">
					<input type='hidden' name='RiskCode' value="${prpLclaim.riskCode}">
					<input name="ClaimNo" class="readonly" readonly value="${prpLclaim.claimNo}">
				</td>
				<td class="title">
					<s:text name="db.view_larrearage.policyNo" />
					：
				</td>
				<%--保单号--%>
				<td class="input">
					<input name="PolicyNo" class="readonly" readonly value="${prpLclaim.policyNo}">
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="recase.lastClosingTime" />
					：
				</td>
				<%--上次结案时间--%>
				<td class="input">
					<%-- <input name=endCaseDate class="readonly" readonly value="${prpLclaim.endCaseDate}">--%>
					<rc:rcDate name="endCaseDate" class="readonly" readonly="true" wdatePicker="false" style="width:80px" value="${prpLclaim.endCaseDate}" />
				</td>
				<td class="title">
					<s:text name="recase.lastClosingPeople" />
					：
				</td>
				<%--上次结案操作员--%>
				<td class="input">
					<input type="hidden" name="ItemCode"  value="${prpLclaim.endCaserCode}">
					<input name="reCaseItemName" class="readonly" readonly value="${prpLclaim.endCaserName}">
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="recase.reopenClaimTime" />
					：
				</td>
				<%--重开赔案时间--%>
				<td class="input">
					<%-- <input name=reCaseDate type="input" class="readonly" value="<%=new DateTime(DateTime.current(),DateTime.YEAR_TO_SECOND).toString()%>">--%>
					<rc:rcDate name="reCaseDate" class="readonly" readonly="true" wdatePicker="false" style="width:145px" value="<%=new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString()%>" />
				</td>
				<td class="title">
					<s:text name="db.prpLlawsuit.operatorCode" />
					：
				</td>
				<%--操作员--%>
				<td class="input">
					<input type="hidden" name="reCasehandleCode"  value="${user.userCode}">
					<input name="reCasehandleName" type="input" class="readonly" value="${user.userName}">
				</td>
			</tr>
			<tr>
				<td class="title" colspan=4>
					<s:text name="recase.reopenClaimCause" />
					：
				</td>
				<%--重开赔案原因--%>
			</tr>
			<tr>
				<td class="input" colspan="4" align="center">
					<input type='hidden' name="riskCode" value="${prpLclaim.riskCode}">
					<textarea name='appRecaseReason' wrap="hard" rows=15 cols=80 class=common></textarea>
				</td>
			</tr>
		</table>
		<input type="hidden" name="editType" value="SAVE">
		<table class="common" align="center">
			<tr>
				<td class="button">
					<input type="button" name="buttonSave" value="<s:text name='button.determine.value' />" class="button" onclick="submitForm();">
				</td>
				<td class="button">
					<input type="button" name="buttonCancel" value="<s:text name='button.cancel.value' />" class="button" onclick="resetForm();">
				</td>
			</tr>
		</table>
	</form>
</body>
<!--这个函数是调动所能用到的通用js的过程，一般包括最常用的js的函数声明都在meta_js.jsp中-->
</html>
