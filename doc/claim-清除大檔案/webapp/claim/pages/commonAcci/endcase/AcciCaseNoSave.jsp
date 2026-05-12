<!--
****************************************************************************
* DESC       ：结案登记录入/修改页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-06-28
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
-->
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true">
<head>
<!--对title处理-->
<title><s:text name="title.endcaseBeforeEdit.editEndcase" /></title>
<%--结案登记--%>
<app:css />
<!-- 页面样式  -->
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<%@ include file="/common/meta_js.jsp"%>
<script language="javascript">
function submitForm() {
    fm.buttonSave.disabled = true;
    fm.submit(); //提交
}
</script>
</head>
<body class="interface" onload="initPage();">
	<form name="fm" method="post" action="${ctx }/endcase/endcaseSave.do?step=step3">
		<input type="hidden" name="prplCaseNoCaseNo1" value="${prpLcaseNo.id.caseNo}">
		<input type="hidden" name="prpLendcaseClaimNo1" value="${prpLcaseNo.claimNo}">
		<input type="hidden" name=buttonSaveType value="2">
		<table border="0" align="center" cellpadding="0" cellspacing="0" class="common">
			<tr>
				<td width="184" height="26" valign="bottom">
					<table width="184" height="19" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="161" class="formtitle">
								<s:text name="commonAcci.endcase.generateFinalNo" />
							</td>
							<%--生成结案号--%>
						</tr>
					</table>
				</td>
				<td valign="bottom">
					<font color="#666666">&nbsp;
				</td>
			</tr>
		</table>
		<table border="0" align="center" cellpadding="0" cellspacing="0" class="common">
			<tr>
				<td class="centertitle">
					<s:text name="certainLoss.claims" />：
				</td>
				<%--赔案号--%>
				<td class="centertitle">${prpLcaseNo.claimNo}</td>
				<td class="centertitle">
					<s:text name="db.view_larrearage.policyNo" />：
				</td>
				<%--保单号--%>
				<td class="centertitle">${prpLcaseNo.policyNo}</td>
			</tr>
		</table>
		<table bgcolor="#2D8EE1" class="common" cellpadding="4" cellspacing="1">
			<tr>
				<td class="centertitle" colspan="3" align="left">
					<s:text name="endcase.claimsInformation" />
				</td>
				<%--赔案信息--%>
			</tr>
			<tr>
				<td class="centertitle">
					<s:text name="db.prpLpersonloss.compensateNo" />
				</td>
				<%--赔款计算书号--%>
				<td class="centertitle">
					<s:text name="db.prpLcompensate.caseNo" />
				</td>
				<%--结案号--%>
				<td class="centertitle">
					<s:text name="compensate.compel.paymentAmount" />
				</td>
				<%--赔款金额--%>
			</tr>
			<c:if test="${prpLcaseNo.caseList!=null}">
				<c:forEach var="prpLcaseNoTemp" items="${prpLcaseNo.caseList}" varStatus="caseList_status">
					<c:if test="${caseList_status.index%2==0}">
						<tr class="listodd">
					</c:if>
					<c:if test="${caseList_status.index%2!=0}">
						<tr class="listeven">
					</c:if>
					<td>${prpLcaseNoTemp.id.certiNo}</td>
					<td>${prpLcaseNoTemp.id.caseNo}</td>
					<td>${prpLcaseNoTemp.sumPaid}</td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
		</tr>
		</table>
		<table class="common" align="center">
			<tr>
				<td class="button" colspan="2">
					<input type="button" name="buttonSave" value="<s:text name='button.save.value' />" onclick="submitForm();">
				</td>
				<td class="button" colspan="2">
					<input type="button" name="buttonCancel" value="<s:text name='button.cancel.value' />">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>