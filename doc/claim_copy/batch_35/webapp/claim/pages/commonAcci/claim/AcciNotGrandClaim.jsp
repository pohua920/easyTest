<%--
****************************************************************************
* DESC       ：不予立案处理页面
* AUTHOR     ： 项目组
* CREATEDATE ：2005-09-06
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<script language="JavaScript">
	javascript: window.history.forward(1);
</script>
<%@ include file="/common/meta_js.jsp"%>
<html locale="true">
<head>
<!--对title处理-->
<app:css />
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script src="/claim/pages/DAA/claim/js/DAAClaimEdit.js"></script>
</head>
<body class=interface onload="initPage();">
	<form name=fm action="/claim/notGrandClaim.do" method="post" onsubmit="if(document.fm.temp.value==0) return false;else document.fm.temp.value=0;">
		<INPUT type=hidden value="1" name="temp">
		<table cellpadding="5" cellspacing="1" class=common>
			<tr class=listtitle>
				<td colspan="4">
					<s:text name="commonAcci.claim.healthInsuran" />
					<%--意健险不予立案处理--%>
					<input type="hidden" name="swfLogFlowID" class="common" value="<c:out value='${param.swfLogFlowID}'/>">
					<input type="hidden" name="swfLogLogNo" class="common" value="<c:out value='${param.swfLogLogNo}'/>">
				</td>
			</tr>
			<tr>
				<td class="title" style="width: 15%; valign: bottom">
					<s:text name="db.prpLclaim.registNo" />:
				</td>
				<%--备案号码--%>
				<td class="input" style="width: 35%; valign: bottom" colspan=3>
					<input type="text" name="prpLclaimRegistNo" class="readonly" title="備案號碼" maxlength="22" readonly="true" value="<c:out value='${requestScope.prpLregist.registNo}'/>">
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="print.compInsurerIinfo" />:
				</td>
				<%--被保险人--%>
				<td class="input">
					<input type=text name="prpLclaimInsuredName" title="被保險人" class="readonly" value="<c:out value='${requestScope.prpLregist.insuredName}'/>">
				</td>
				<td class="title">
					<s:text name="db.view_larrearage.policyNo" />：
				</td>
				<%--保单号--%>
				<td class="input">
					<input type=text name="prpLclaimPolicyNo" title="保單號碼" class="readonly" value="<c:out value='${requestScope.prpLregist.policyNo}'/>">
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="regist.prpLregist.damageTime" />:
				</td>
				<%--出险时间--%>
				<td class="input">
					<input type=text name="prpLclaimDealerName" title="出險時間" class="readonly" value="<fmt:formatDate value='${requestScope.prpLregist.damageStartDate}' pattern='yyyy-MM-dd'/>">
				</td>
				<td class="title">
					<s:text name="regist.prpLregist.damageAddress" />：
				</td>
				<%--出险地点--%>
				<td class="input">
					<input type=text name="prpLclaimDamageAddress" title="出險地點" class="readonly" value="<c:out value='${requestScope.prpLregist.damageAddress}'/>">
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="claim.applicant" />:
				</td>
				<%--申请人--%>
				<td class="input">
					<input type="hidden" name="prpLclaimDealerCode" title="註銷賠案申請人" class="readonly" value="<c:out value='${requestScope.prpLregist.dealerCode}'/>">
					<input type=text name="prpLclaimDealerName" title="註銷賠案申請人" class="readonly" value="<c:out value='${requestScope.prpLregist.comName}'/>">
				</td>
				<td class="title">
					<s:text name="claim.applyTime" />：
				</td>
				<%--申请时间--%>
				<td class="input">
					<input type=text name="prpLclaimCancelDate" title="申請時間" class="readonly" value="<fmt:formatDate value='${requestScope.prpLregist.cancelDate}' pattern='yyyy-MM-dd'/>">
				</td>
			</tr>
			<tr>
				<td class="title" colspan="4">
					<s:text name="commonAcci.claim.shallNotRecordReason" />：
				</td>
				<%--不予立案原因--%>
			</tr>
			<tr>
				<td class="input" colspan="4" align="center">
					<textarea name="prpLclaimContext" wrap="hard" rows="15" cols="80" class="common"></textarea>
					<input type="button" name="btnRegistText" class=bigbutton value="產生不予立案文本" onclick="return generateNoClaimText();">
				</td>
			</tr>
		</table>
		<table class="common" align="center">
			<tr>
				<td class=button align="center">
					<!--确 定按钮-->
					<input type=submit name=buttonSave class='button' value="<s:text name='button.submit.value' />">
				</td>
				<td class=button align="center">
					<input type=button name=buttonCancel class='button' value="<s:text name='button.cancel.value' />" onclick="return resetForm();">
					<input type=hidden name="editType" title="操作類型" class="readonly" value="<c:out value='${requestScope.prpLregist.editType}'/>">
					<input type=hidden name="nodeType" title="節點類型" class="readonly" value="<c:out value='${param.nodeType}'/>">
					<input type=hidden name="businessNo" title="業務號碼" class="readonly" value="<c:out value='${param.bussinessNo}'/>">
					<input type=hidden name="riskCode" title="業務號碼" class="readonly" value="<c:out value='${param.riskCode}'/>">
					<input type=hidden name="prpLcancelclaimPrintFlag" title="業務號碼" class="readonly" value='0'>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>