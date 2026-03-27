<%--
****************************************************************************
* DESC       ：实赔录入/修改页面
* AUTHOR     ：中科软
* MODIFYLIST ：Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns:mpc>
<head>
<title><s:text name="title.registBeforeEdit.editRegist" /></title>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<%-- 标签页样式 --%>
<jsp:include page="/behaviors/MpcStyle.jsp" />
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<script type="text/javascript">
	//mpc调整
	$(function() {
		initWindow();
		$(window).resize(function() {
			initWindow();
		});
	})
</script>
<script
	src="${ctx }/pages/commonCargo/compensate/js/CargoCompensateEdit.js"></script>
<script
	src="${ctx }/pages/commonCargo/compensate/js/CargoPersonLossEdit.js"></script>
<script
	src="${ctx }/pages/commonCargo/compensate/js/CargoCompensateEditAdd.js"></script>
</head>
<c:set var="oldCompensateLastAccessedTime" value="" scope="session" />
<s:if test="#session.user == null">
	<c:set var="riskCode" value="${param.riskCode}" />
</s:if>
<s:else>
	<c:set var="riskCode" value="${session.user.riskCode}" />
</s:else>
<c:set var="editType" value="${param.editType }" />
<c:set var="editTypeOther" value="${param.editTypeOther}" />
<s:if test="editTypeOther == 'SHOWTASK'">
	<body onload="initPage();initSet();readonlyAllInput();hideButton('buttonLayer');oMPC.style.visibility='visible';">
</s:if>
<s:elseif test="editTypeOther == 'SHOW'">
	<body onload="initPage();initSet();readonlyAllInput();oMPC.style.visibility='visible';">
</s:elseif>
<s:else>
	<body onload="initPage();initSet();oMPC.style.visibility='visible';">
</s:else>
<DIV id="mainLayer" class="mainLayer">
	<form name=fm action="${ctx}/compensate/compensateSave.do"
		method="post" onsubmit="return validateForm(this);">
		<input type="hidden" name='prpLregistPayFee'
			value="${prpLcompensate.palyFlag}">
		<TABLE id="btnTable" cellpadding="0" cellspacing="0" border="0">
			<TR>
				<td>
					<input type="button" name="prpLmessageSave"
						value="<s:text name='button.claimsProcessingRecords.value'/>"
						class="bigbutton"
						onclick="openWinSave(fm.prpLcompensateClaimNo.value,fm.prpLcompensatePolicyNo.value,fm.prpLcompensateRiskCode.value,'compe',fm.prpLcompensateClaimNo.value)">
					<%-- 赔案处理记录 --%>
					<input type="button" name="eCertify" class="bigbutton"
						value="<s:text name='button.checkedUpload.value'/>"
						onClick="openCertify('certifyFinishQueryList','prpLcertifyCertifyNo','${prpLcompensate.registNo}','compe');">
					<%-- 单证上传 --%>
					<c:if test="${needUndwrtFlag == 'Y'}">
						<c:if test="${sendUndwrtFlag == 'Y'}">
							<input type="button" class="bigbutton" name="taskView"
								value="<s:text name='button.TaskQuery.value'/>"
								onclick="openWinTask('${param.swfLogFlowID}');">
							<%-- 任务查询 --%>
						</c:if>
					</c:if>
					<c:if test="${editType == 'SHOW'}">
						<c:if
							test="${session.user.userCode == prpLcompensate.handlerCode}">
							<input type="hidden" name="assessor" class="bigbutton" value="<s:text name='button.assessTeacher.value'/>" onClick="openAssessor(fm.prpLcompensateClaimNo.value);">
							<%-- 公估师评估 --%>
						</c:if>
					</c:if>
				</td>
			</TR>
		</TABLE>
		<mpc:container ID="oMPC">
			<%--报案基本信息页面 --%>
			<mpc:page ID="tabMain" TABTITLE="<s:text name='regist.prpLregist.registMain'/>" TABTEXT="<s:text name='regist.prpLregist.registMain'/>">
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 1.报案主信息 --%>
						<%@include
							file="/pages/commonCargo/compensate/CargoCompensateMainHeadEdit.jsp"%>
						<%-- 2.特别约定 --%>
						<%@include file="/pages/DAA/compensate/DAACompensateCengage.jsp"%>
						<%-- 2.赔偿限额/免赔额 --%>
						<%@include
							file="/pages/commonCargo/compensate/CargoCompensatePrpDlimit.jsp"%>
						<%-- 1.报案主信息 --%>
						<%@include
							file="/pages/commonCargo/compensate/CargoCompensateMainTailEdit.jsp"%>
						<%--添加结案报告 --%>
						<%@include
							file="/pages/commonCargo/compensate/CargoEndCaseCompensateTextEdit.jsp"%>
						<%-- 4.理算报告 --%>
						<%@include
							file="/pages/commonCargo/compensate/CargoCompensateTextEdit.jsp"%>
						<%-- 4.付款说明 --%>
						<%@include
							file="/pages/common/compensate/CompensatePayTextEdit.jsp"%>
						<%-- 5.指定危险单位信息 --%>
						<%@include file="/pages/common/claim/ClaimRiskUnit.jsp"%>
						<%-- 6.巨灾代码--%>
						<%@include file="/pages/common/claim/ClaimKelpInfo.jsp"%>
						<%-- 9.核赔退回原因--%>
						<c:if test="${not empty swfNotionDtoList}">
							<%@include
								file="/pages/common/compensate/CompensateUndwrtInfo.jsp"%>
						</c:if>
						<%-- 核赔意见 --%>
						<%@include file="/pages/common/pub/UndwrtTextEdit.jsp"%>
						<%-- 送审审核片语和意见 --%>
						<%@include file="/pages/common/sendUndwrt/SendUndwrtTextEdit.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
			<mpc:page ID="tabMain" TABTITLE="<s:text name='button.PayoutInformation.value'/>" TABTEXT="<s:text name='button.PayoutInformation.value'/>">
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 1.赔付标的信息 --%>
						<%@include
							file="/pages/commonCargo/compensate/CargoCompensateLlossEdit.jsp"%>
						<c:if test="${not empty riskCode && riskCode == 'ZAA'}">
							<%-- 2.赔付人员信息 --%>
							<%@include
								file="/pages/commonCargo/compensate/CargoCompensatePersonLossEdit.jsp"%>
						</c:if>
						<%-- 3.赔款费用 --%>
						<%@include
							file="/pages/commonCargo/compensate/CargoCompensateChargeEdit.jsp"%>
						<%-- 支付帳户信息 --%>
						<%@include
							file="/pages/commonCargo/compensate/EditPrpdpaymentaccountPage.jsp"%>
						<%-- 4.联共保赔款费用分摊信息 --%>
						<c:if test="${not empty requestScope.coinsFlag}">
							<input type="hidden" name="chiefflag" value="${coinsFlag}">
							<c:set var="strCoinsFlag" value="${coinsFlag}" />
							<c:if
								test="${strCoinsFlag == '1' || strCoinsFlag == '2' || strCoinsFlag == '3' }">
								<%@include
									file="/pages/common/compensate/CompensateCoinsEditFrame.jsp"%>
							</c:if>
						</c:if>
						<c:if test="${empty requestScope.coinsFlag}">
							<input type="hidden" name="chiefflag" value="0">
						</c:if>
					</DIV>
				</CENTER>
			</mpc:page>
		</mpc:container>
		<TABLE id="btnCommon" class="common">
			<TR>
				<TD align="center">
					<%-- 保存通用按钮 --%>
					<%@include file="/pages/DAA/compensate/DAACompensateSave.jsp"%>
				</TD>
			</TR>
		</TABLE>
	</form>
</DIV>
</body>
</html>
