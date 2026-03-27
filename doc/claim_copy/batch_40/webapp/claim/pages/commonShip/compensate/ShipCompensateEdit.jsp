<%--
****************************************************************************
* DESC	   ：实赔录入/修改页面
* AUTHOR	 ：中科软
* MODIFYLIST ：Name	   Date			Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="com.sinosoft.claim.common.ConstantCodes"%>
<html xmlns:mpc>
<head>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<!--对title处理-->
<title><s:text name="title.compensateBeforeEdit.editCompensate" /></title>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
<%-- 标签页样式 --%>
<jsp:include page="/behaviors/MpcStyle.jsp" />
<script src="${ctx}/pages/commonShip/compensate/js/ShipCompensateEdit.js"></script>
<script src="${ctx}/pages/DAA/compensate/js/autoHospital.js"></script>
<%@include file="/pages/common/compensate/compensateLimit.jsp"%>
<script type="text/javascript">
	//mpc调整
	$(function() {
		initWindow();
		$(window).resize(function() {
			initWindow();
		});
	})
	
	var $baseToExch = $("body");<%-- 汇率  本位币转换其他币别  --%>
	var $exchToBase = $("body");<%-- 汇率  其他币别转换本位币 --%>
	<c:forEach items="${requestScope.baseToExch}" var="temp">
		jQuery.data($baseToExch,'${temp.id.exchCurrency}','${temp.exchRate}');
	</c:forEach>
	<c:forEach items="${requestScope.exchToBase}" var="temp">
		jQuery.data($exchToBase,'${temp.id.baseCurrency}','${temp.exchRate}');
	</c:forEach>
</script>
</head>
<c:set var="oldCompensateLastAccessedTime" value="" scope="session" />
<c:choose>
	<c:when test="${param.editType=='SHOW' || param.editTypeOther=='SHOWTASK'}">
		<body onload="initPage();initSet();readonlyAllInput();oMPC.style.visibility='visible';">
	</c:when>
	<c:otherwise>
		<body onload="initPage();initSet();oMPC.style.visibility='visible';">
	</c:otherwise>
</c:choose>
<DIV id="mainLayer" class="mainLayer">
	<form name=fm action="${ctx}/compensate/compensateSave.do" method="post" onsubmit="return validateForm(this);">
		<input type="hidden" name="editType" value="${editType}">
		<c:if test="${editType == 'ADD' || editType == 'EDIT'}">
			<s:token></s:token>
		</c:if>
		<input type="hidden" name='prpLregistPayFee' value="${prpLcompensate.palyFlag}">
		<TABLE id="btnTable" cellpadding="0" cellspacing="0" border="0" style="height: 22px">
			<TR>
				<td>
					<c:if test="${param.editTypeOther!='SHOWTASK'}">
						<input type="button" name="prpLmessageSave" value="<s:text name='button.claimsProcessingRecords.value' />" class="bigbutton" style="width: 100px"
							onclick="openWinSave(fm.prpLcompensateClaimNo.value,fm.prpLcompensatePolicyNo.value,fm.prpLcompensateRiskCode.value,'compe',fm.prpLcompensateClaimNo.value)">
						<%--赔案处理记录--%>
						<input type="button" name="eCertify" class="bigbutton" style="width: 100px" value="<s:text name='button.electronicDocuments.value' />" onClick="openCertify('certifyFinishQueryList','prpLcertifyCertifyNo','${prpLcompensate.registNo}','compe');">
						<%--单证上传--%>
						<c:if test="${editType == 'SHOW' && sessionScope.user.userCode == prpLcompensate.handlerCode}">
							<input type="hidden" name="assessor" class="bigbutton" style="width: 100px" value="<s:text name='button.assessTeacher.value'/>" onClick="openAssessor(fm.prpLcompensateClaimNo.value);">
							<%-- 公估师评估 --%>
						</c:if>
						<c:if test="${needUndwrtFlag == 'Y' && sendUndwrtFlag == 'Y'}">
							<input type="button" class="bigbutton" style="width: 100px" name="taskView" value="<s:text name='button.TaskQuery.value'/>" onclick="openWinTask('${param.swfLogFlowID}');">
							<%-- 任务查询 --%>
						</c:if>
					</c:if>
				</td>
			</TR>
		</TABLE>
		<mpc:container ID="oMPC" style="width:830px;height:520px;">
			<%-- 1.1.报案基本信息页面 --%>
			<mpc:page ID="tabMain" TABTITLE="<s:text name='regist.prpLregist.registMain'/>" TABTEXT="<s:text name='regist.prpLregist.registMain'/>">
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 1.报案主信息 --%>
						<%@include file="/pages/commonShip/compensate/ShipCompensateMainHeadEdit.jsp"%>
						<%-- 2.特别约定 --%>
						<%@include file="/pages/commonShip/compensate/ShipCompensateCengage.jsp"%>
						<%-- 3.赔偿限额/免赔额 --%>
						<%@include file="/pages/commonShip/compensate/ShipCompensatePrpDlimit.jsp"%>
						<%-- 4.报案主信息 --%>
						<%@include file="/pages/commonShip/compensate/ShipCompensateMainTailEdit.jsp"%>
						<%-- 6.付款说明 --%>
						<%@include file="/pages/common/compensate/CompensatePayTextEdit.jsp"%>
						<%-- 7.指定危险单位信息 --%>
						<%@include file="/pages/common/claim/ClaimRiskUnit.jsp"%>
						<%-- 8.巨灾代码--%>
						<%@include file="/pages/common/claim/ClaimKelpInfo.jsp"%>
						<!-- reason:显示核赔退回原因 -->
						<%-- 9.核赔退回原因--%>
						<c:if test="${not empty requestScope.swfNotionList}">
							<%@include file="/pages/common/compensate/CompensateUndwrtInfo.jsp"%>
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
						<%@include file="/pages/commonShip/compensate/ShipCompensateLlossEdit.jsp"%>
						<%-- 2.赔付人员信息 --%>
						<%@include file="/pages/commonShip/compensate/ShipCompensatePersonLossEdit.jsp"%>
						<%-- 3.赔款费用 --%>
						<%@include file="/pages/commonShip/compensate/ShipCompensateChargeEdit.jsp"%>
						<%-- 赔款支付信息 --%>
						<%@include file="/pages/commonShip/compensate/EditPrpdpaymentaccountPage.jsp"%>
						<%-- 4.联共保赔款费用分摊信息 --%>
						<c:if test="${not empty coinsFlag}">
							<input type="hidden" name="chiefflag" value="${coinsFlag}">
							<c:set var="strCoinsFlag" value="${coinsFlag}" />
							<c:if test="${strCoinsFlag == '1' || strCoinsFlag == '2' || strCoinsFlag == '3'}">
								<%@include file="/pages/common/compensate/CompensateCoinsEditFrame.jsp"%>
							</c:if>
						</c:if>
						<c:if test="${empty coinsFlag}">
							<input type="hidden" name="chiefflag" value="0">
						</c:if>
					</DIV>
				</CENTER>
			</mpc:page>
			<mpc:page ID="tabMain" TABTITLE="<s:text name='compensate.adjustReport'/>" TABTEXT="<s:text name='compensate.adjustReport'/>">
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 1.理算说明  --%>
						<%@include file="/pages/commonShip/compensate/ShipEndCaseCompensateTextEdit.jsp"%>
						<%-- 2.理算报告 --%>
						<%@include file="/pages/commonShip/compensate/ShipCompensateTextEdit.jsp"%>
						<%-- 3.發送再保通知 --%>
						<%@include file="/pages/common/compensate/CompensateNotice.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
		</mpc:container>
		<TABLE id="btnCommon" class="common">
			<TR>
				<TD align="center">
					<c:if test="${param.editTypeOther!='SHOWTASK'}">
						<%-- 保存通用按钮 --%>
						<%@include file="/pages/DAA/compensate/DAACompensateSave.jsp"%>
					</c:if>
				</TD>
			</TR>
		</TABLE>
	</form>
</DIV>
</body>
</html>
