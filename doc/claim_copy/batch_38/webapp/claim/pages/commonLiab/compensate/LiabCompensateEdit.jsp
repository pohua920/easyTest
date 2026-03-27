<%@	page contentType="text/html; charset=GBK" language="java"%>
<%--
****************************************************************************
* DESC	   ：实赔录入/修改页面
* AUTHOR	 ：中科软
* MODIFYLIST ：   Name	   Date			 Reason/Contents
****************************************************************************
--%>
<%@page import="com.sinosoft.claim.common.ConstantCodes"%>
<html xmlns:mpc>
<head>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta_js.jsp"%>
<!--对title处理-->
<title><s:text name="title.compensateBeforeEdit.editCompensate" /></title>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
<%-- 标签页样式 --%>
<jsp:include page="/behaviors/MpcStyle.jsp" />
<script src="${ctx}/pages/commonLiab/compensate/js/LiabCompensateEdit.js"></script>
<script src="${ctx}/pages/DAA/compensate/js/autoHospital.js"></script>
<%@include file="/pages/common/compensate/compensateLimit.jsp"%>
<script language="JavaScript">
	javascript:window.history.forward(1);
</script>
<script type="text/javascript">
	//mpc调整
	$(function(){
		initWindow();
		$(window).resize(function(){
			initWindow();
		});
	})
	var $baseToExch = $("body");<%-- 汇率  本位币转换其他币别  --%>
	var $exchToBase = $("body");<%-- 汇率  其他币别转换本位币 --%>
</script>
<c:forEach items="${requestScope.baseToExch}" var="temp">
	<script type="text/javascript">jQuery.data($baseToExch, '${temp.id.exchCurrency}', '${temp.exchRate}');</script>
</c:forEach>
<c:forEach items="${requestScope.exchToBase}" var="temp">
	<script type="text/javascript">jQuery.data($exchToBase, '${temp.id.baseCurrency}', '${temp.exchRate}');</script>
</c:forEach>
</head>
<c:set var="oldCompensateLastAccessedTime" value="" scope="session" />
<c:choose>
	<c:when test="${param.editType=='SHOW' || param.editTypeOther=='SHOWTASK'}">
		<body class="interface" onload="initPage();initSet();readonlyAllInput();disabledAllButton('buttonArea');oMPC.style.visibility='visible';">
	</c:when>
	<c:otherwise>
		<body class="interface" onload="initPage();initSet(); oMPC.style.visibility='visible';">
	</c:otherwise>
</c:choose>
<DIV id="mainLayer" class="mainLayer">
	<form name=fm action="${ctx}/compensate/compensateSave.do" method="post" onsubmit="return validateForm(this);">
		<input type="hidden" name="editType" value="${editType}">
		<c:if test="${editType == 'ADD' || editType == 'EDIT'}">
			<s:token></s:token>
		</c:if>
		<input type="hidden" name='prpLregistPayFee' value="<c:out value='${requestScope.prpLcompensate.palyFlag}'/>">
		<table id="btnTable" cellpadding="0" cellspacing="0" border="0" style="height: 22px">
			<tr>
				<td>
					<c:if test="${param.editTypeOther!='SHOWTASK'}">
						<input type="button" name="prpLmessageSave" value="<s:text name='button.claimsProcessingRecords.value'/>" class="bigbutton"  style="width: 100px" 
							onclick="openWinSave(fm.prpLcompensateClaimNo.value,fm.prpLcompensatePolicyNo.value,fm.prpLcompensateRiskCode.value,'compe',fm.prpLcompensateClaimNo.value)">
						<%-- 赔案处理记录 --%>
						<input type="button" name="eCertify" class="bigbutton"  style="width: 100px"  value="<s:text name='button.checkedUpload.value'/>"
							onClick="openCertify('certifyFinishQueryList','prpLcertifyCertifyNo','<c:out value="${requestScope.prpLcompensate.registNo}"/>','compe');">
						<%-- 单证上传 --%>
						<c:if test="${param.editType =='SHOW' && sessionScope.user.userCode==requestScope.prpLcompensate.handlerCode}">
							<input type="hidden" name="assessor" class="bigbutton" style="width: 100px" value="<s:text name='button.assessTeacher.value'/>" onClick="openAssessor(fm.prpLcompensateClaimNo.value);">
							<%-- 公估师评估 --%>
						</c:if>
						<c:if test="${requestScope.needUndwrtFlag=='Y' && requestScope.sendUndwrtFlag=='Y'}">
							<input type="button" class="bigbutton"  style="width: 100px"  name="taskView" value="<s:text name='button.TaskQuery.value'/>" onclick="openWinTask('<c:out value="${param.swfLogFlowID}"/>');">
							<%-- 任务查询 --%>
						</c:if>
					</c:if>
				</td>
			</tr>
		</table>
		<mpc:container ID="oMPC" style="width:830px;height:520px;">
			<mpc:page ID="tabMain" TABTITLE="基本訊息" TABTEXT="基本訊息">
				<%--基本讯息--%>
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 1.报案主信息 --%>
						<%@include file="/pages/commonLiab/compensate/LiabCompensateMainHeadEdit.jsp"%>
						<%-- 2.特别约定 --%>
						<%@include file="/pages/DAA/compensate/DAACompensateCengage.jsp"%>
						<%-- 4.付款说明 --%>
						<%@include file="/pages/common/compensate/CompensatePayTextEdit.jsp"%>
						<%-- 5.指定危险单位信息 --%>
						<%@include file="/pages/common/claim/ClaimRiskUnit.jsp"%>
						<%-- 8.巨灾代码--%>
						<%@include file="/pages/common/claim/ClaimKelpInfo.jsp"%>
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
				<%--赔付讯息--%>
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 1.赔付标的信息 --%>
						<%@include file="/pages/commonLiab/compensate/LiabCompensateLlossEdit.jsp"%>
						<%-- 2.赔付人员信息 --%>
						<%@include file="/pages/commonLiab/compensate/LiabCompensatePersonLossEdit.jsp"%>
						<%-- 3.赔款费用 --%>
						<%@include file="/pages/commonLiab/compensate/LiabCompensateChargeEdit.jsp"%>
						<%-- 4.报案主信息 --%>
						<%@include file="/pages/commonLiab/compensate/LiabCompensateMainTailEdit.jsp"%>
						<%-- 支付帳户信息 --%>
						<%@include file="/pages/commonLiab/compensate/EditPrpdpaymentaccountPage.jsp"%>
						<%-- 5.联共保赔款费用分摊信息 --%>
						<c:choose>
							<c:when test="${not empty coinsFlag}">
								<input type="hidden" name="chiefflag" value="${ coinsFlag}">
								<c:if test="${ coinsFlag==1 || coinsFlag==2|| coinsFlag==3 }">
									<%@include file="/pages/common/compensate/CompensateCoinsEditFrame.jsp"%>
								</c:if>
							</c:when>
							<c:otherwise>
								<input type="hidden" name="chiefflag" value="0">
							</c:otherwise>
						</c:choose>
					</DIV>
				</CENTER>
			</mpc:page>
			<mpc:page ID="tabMain" TABTITLE="<s:text name='compensate.adjustReport'/>" TABTEXT="<s:text name='compensate.adjustReport'/>">
				<%--理算报告--%>
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 1.理算說明 --%>
						<%@include file="/pages/commonLiab/compensate/LiabEndCaseCompensateTextEdit.jsp"%>
						<%-- 2.賠款計算過程 --%>
						<%@include file="/pages/commonLiab/compensate/LiabCompensateTextEdit.jsp"%>
						<%-- 3.發送再保通知 --%>
						<%@include file="/pages/common/compensate/CompensateNotice.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
		</mpc:container>
		<TABLE id="btnCommon" class="common">
			<TR>
				<td align="center">
					<c:if test="${param.editTypeOther!='SHOWTASK'}">
						<%-- 保存通用按钮 --%>
						<%@include file="/pages/commonLiab/compensate/LiabCompensateSave.jsp"%>
					</c:if>
				</TD>
			</TR>
		</TABLE>
</DIV>
</form>
</body>
</html>
