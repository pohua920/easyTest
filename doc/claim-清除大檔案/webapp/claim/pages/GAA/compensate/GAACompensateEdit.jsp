<%@	page contentType="text/html; charset=GBK" language="java"%>
<%--
****************************************************************************
* DESC       ：实赔录入/修改页面
* AUTHOR     ：理赔组
* CREATEDATE ：2013-07-10
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<html xmlns:mpc>
<head>
<!--对title处理-->
<%@ include file="/common/taglibs.jsp"%>
<title><s:text name="title.compensateBeforeEdit.editCompensate" /></title>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
<%-- 标签页样式 --%>
<jsp:include page="/behaviors/MpcStyle.jsp" />
<%@ include file="/common/meta_js.jsp"%>
<script src="${ctx}/pages/GAA/compensate/js/GAACompensateEdit.js"></script>
<script src="${ctx}/pages/GAA/compensate/js/GAAPersonLossEdit.js"></script>
<script src="${ctx}/pages/GAA/compensate/js/GAACompensateEditAdd.js"></script>
<script src="${ctx}/pages/DAA/compensate/js/autoHospital.js"></script>
<%@include file="/pages/common/compensate/compensateLimit.jsp"%>
<script language="JavaScript">
	javascript: window.history.forward(1);
</script>
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
    jQuery.data($baseToExch, '${temp.id.exchCurrency}', '${temp.exchRate}');
</c:forEach>
<c:forEach items="${requestScope.exchToBase}" var="temp">
    jQuery.data($exchToBase, '${temp.id.baseCurrency}', '${temp.exchRate}');
</c:forEach>
</script>
</head>
</head>
<c:set var="oldCompensateLastAccessedTime" value="" scope="session" />
<c:if test="${user!=null}">
	<c:set var="riskCode" value="${user.riskCode}" scope="page" />
</c:if>
<c:if test="${user==null}">
	<c:set var="riskCode" value="${param.riskCode}" scope="page" />
</c:if>
<c:choose>
	<c:when test="${param.editTypeOther=='SHOWTASK'}">
		<body class="interface" onload="initPage();initSet();readonlyAllInput();hideButton('buttonLayer');oMPC.style.visibility='visible';">
	</c:when>
	<c:when test="${param.editType=='SHOW'}">
		<body class="interface" onload="initPage();initSet();readonlyAllInput();disabledAllButton('buttonArea');oMPC.style.visibility='visible';">
	</c:when>
	<c:otherwise>
		<body class="interface" onload="initPage();initSet(); oMPC.style.visibility='visible';">
	</c:otherwise>
</c:choose>
<DIV id="mainLayer" class="mainLayer">
	<form name=fm action="${ctx}/compensate/compensateSave.do" method="post" onsubmit="return validateForm(this);">
		<input type="hidden" name="editType" value="${editType}">
		<input type="hidden" name='prpLregistPayFee' value="<c:out value='${requestScope.prpLcompensate.palyFlag}'/>">
		<c:if test="${editType == 'ADD' || editType == 'EDIT'}">
			<s:token></s:token>
		</c:if>
		<table id="btnTable" cellpadding="0" cellspacing="0" border="0" style="height: 22px">
			<tr>
				<td>
					<c:if test="${param.editTypeOther!='SHOWTASK'}">
						<%-- 赔案处理记录 --%>
						<input type="button" name="prpLmessageSave" value="<s:text name='button.claimsProcessingRecords.value'/>" class="bigbutton"
							onclick="openWinSave(fm.prpLcompensateClaimNo.value,fm.prpLcompensatePolicyNo.value,fm.prpLcompensateRiskCode.value,'compe',fm.prpLcompensateClaimNo.value)">
						<%-- 单证上传 --%>
						<input type="button" name="eCertify" class="bigbutton" value="<s:text name='button.checkedUpload.value'/>" onClick="openCertify('certifyFinishQueryList','prpLcertifyCertifyNo','<c:out value="${requestScope.prpLcompensate.registNo}"/>','compe');">
						<c:if test="${param.editType =='SHOW'}">
							<c:if test="${sessionScope.user.userCode==requestScope.prpLcompensate.handlerCode}">
								<input type="hidden" name="assessor" class="bigbutton" value="<s:text name='button.assessTeacher.value'/>" onClick="openAssessor(fm.prpLcompensateClaimNo.value);">
								<%-- 公估师评估 --%>
							</c:if>
						</c:if>
						<c:if test="${requestScope.needUndwrtFlag=='Y' && requestScope.sendUndwrtFlag=='Y'}">
							<input type="button" class="bigbutton" name="taskView" value="<s:text name='button.TaskQuery.value'/>" onclick="openWinTask('<c:out value="${param.swfLogFlowID}"/>');">
							<%-- 任务查询 --%>
						</c:if>
					</c:if>
				</td>
			</tr>
		</table>
		<mpc:container ID="oMPC" style="width:830px;height:420px;visibility:hidden">
			<mpc:page ID="tabMain" TABTITLE="main" TABTEXT="<s:text name='regist.prpLregist.registMain'/>"><%--基本讯息--%>
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 1.报案主信息 --%>
						<%@include file="/pages/GAA/compensate/GAACompensateMainHeadEdit.jsp"%>
						<%-- 2.特别约定 --%>
						<jsp:include page="/pages/GAA/compensate/GAACompensateCengage.jsp"/>
						<%-- 2.赔偿限额/免赔额 --%>
						<%--@include file="/pages/GAA/compensate/PropCompensatePrpDlimit.jsp"--%>
						<%-- 4.付款说明 --%>
						<%@include file="/pages/common/compensate/CompensatePayTextEdit.jsp"%>
						<%-- 5.指定危险单位信息 --%>
						<%@include file="/pages/common/claim/ClaimRiskUnit.jsp"%>
						<%-- 6.巨灾代码--%>
						<%@include file="/pages/common/claim/ClaimKelpInfo.jsp"%>
						<!-- reason:显示核赔退回原因 -->
						<%-- 9.核赔退回原因--%>
						<c:if test="${not empty requestScope.swfNotionList}">
							<%@include file="/pages/common/compensate/CompensateUndwrtInfo.jsp"%>
						</c:if>
						<%-- 核赔意见 --%>
						 <jsp:include page="/pages/common/pub/UndwrtTextEdit.jsp"/>
						<%--送审审核片语和意见 --%>
						 <jsp:include page="/pages/common/sendUndwrt/SendUndwrtTextEdit.jsp"/>
					</DIV>
				</CENTER>
			</mpc:page>
			<mpc:page ID="tabMain" TABTITLE="<s:text name='button.PayoutInformation.value'/>" TABTEXT="<s:text name='button.PayoutInformation.value'/>"><%--赔付讯息--%>
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 1.赔付标的信息 --%>
						<%@include file="/pages/GAA/compensate/GAACompensateLlossEdit.jsp"%>
						<%-- 2.赔付人员信息 --%>
						<%@include file="/pages/GAA/compensate/GAACompensatePersonLossEdit.jsp"%>
						<%-- 3.赔款费用 --%>
						<%@include file="/pages/GAA/compensate/GAACompensateChargeEdit.jsp"%>
						<%-- 支付帳户信息 --%>
						<jsp:include page="/pages/GAA/compensate/EditPrpdpaymentaccountPage.jsp"/>
						<%-- 4.联共保赔款费用分摊信息 --%>
						<c:choose>
							<c:when test="${not empty  requestScope.coinsFlag}">
								<input type="hidden" name="chiefflag" value="${requestScope.coinsFlag}">
								<c:if test="${requestScope.coinsFlag==1 || requestScope.coinsFlag==2|| requestScope.coinsFlag==3}">
									<%@include file="/pages/common/compensate/CompensateCoinsEditFrame.jsp"%>
								</c:if>
							</c:when>
							<c:otherwise>
								<input type="hidden" name="chiefflag" value="0">
							</c:otherwise>
						</c:choose>
						<%-- 5.主信息 --%>
						<%@include file="/pages/GAA/compensate/GAACompensateMainTailEdit.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
			<mpc:page ID="tabMain" TABTITLE="<s:text name='compensate.adjustReport'/>" TABTEXT="<s:text name='compensate.adjustReport'/>"><%--理算报告--%>
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%--增加定损说明--%>
						<%@include file="/pages/GAA/compensate/GAAEndCaseCompensateTextEdit.jsp"%>
						<%-- 2.理算报告 --%>
						<%@include file="/pages/GAA/compensate/GAACompensateTextEdit.jsp"%>
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
						<%@include file="/pages/GAA/compensate/GAACompensateSave.jsp"%>
					</c:if>
				</TD>
			</TR>
		</TABLE>
</DIV>
</form>
</div>
</body>
</html>