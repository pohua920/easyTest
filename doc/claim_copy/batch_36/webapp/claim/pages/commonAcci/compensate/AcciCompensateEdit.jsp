<%--
****************************************************************************
* DESC       ：实赔录入/修改页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-05-19
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns:mpc>
<head>
<!--对title处理-->
<title><s:text name="title.registBeforeEdit.editRegist" /></title>
<%-- 页面样式  --%>
<%@ include file="/common/meta_js.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<link rel="stylesheet" type="text/css" href="${ctx }/common/js/jqselect.css">
<script language="javascript" type="text/javascript" src="${ctx }/common/js/jqselect.js"></script>
<%-- 标签页样式 --%>
<jsp:include page="/behaviors/MpcStyle.jsp" />
<script src="${ctx }/pages/commonAcci/compensate/js/AcciCompensateEdit.js"></script>
<script src="${ctx }/pages/commonAcci/compensate/js/AcciPersonLossEditNew.js"></script>
<script src="${ctx }/pages/commonAcci/compensate/js/AcciAutoHospital.js"></script>
<script src="${ctx }/pages/commonAcci/compensate/js/AcciCompensateEditAdd.js"></script>
<script type="text/javascript">
//mpc调整
$(function () {
    initWindow();
    $(window).resize(function () {
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
<c:set var="oldCompensateLastAccessedTime" value="" scope="session" />
<c:set var="riskCode" value="" scope="page" />
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
		<body class="interface" onload="initPage();initSet();oMPC.style.visibility='visible';">
	</c:otherwise>
</c:choose>
<DIV id="mainLayer" class="mainLayer">
	<form name=fm action="${ctx}/compensate/compensateSave.do" method="post" onsubmit="return validateForm(this);" autocomplete="off">
		<input type="hidden" name="editType" value="${editType}">
		<input type="hidden" name='prpLregistPayFee' value="${prpLcompensate.palyFlag}">
		<input type="hidden" name="cancheck" value="${cancheck }">
		<input type="hidden" name="checkNotOver" value="${checkNotOver }">
		<c:if test="${editType == 'ADD' || editType == 'EDIT'}">
			<s:token></s:token>
		</c:if>
		<table id="btnTable" cellpadding="0" cellspacing="0" border="0" style="height: 22px">
			<tr>
				<td>
					<c:if test="${param.editTypeOther!='SHOWTASK'}">
						<input type="button" name="prpLmessageSave" class='bigbutton' value="<s:text name='button.claimsProcessingRecords.value' />" onclick="openWinSave(fm.prpLcompensateClaimNo.value,fm.prpLcompensatePolicyNo.value, fm.prpLcompensateRiskCode.value,'compe', fm.prpLcompensateClaimNo.value);">
						<%--赔案处理记录--%>
						<input type="button" name="eCertify" class="bigbutton" value="<s:text name='button.electronicDocuments.value' />" onClick="openCertify('certifyFinishQueryList','prpLcertifyCertifyNo','${prpLcompensate.registNo}','compe');">
						<%--单证上传 --%>
						<c:if test="${param.editType=='SHOW'}">
							<c:if test="${user.userCode==prpLcompensate.handlerCode}">
								<input type="hidden" name="assessor" class="bigbutton" value="<s:text name='button.assessTeacher.value' />" onClick="openAssessor(fm.prpLcompensateClaimNo.value);">
								<%--公估师评估--%>
							</c:if>
						</c:if>
						<c:if test="${needUndwrtFlag == 'Y'}">
							<c:if test="${sendUndwrtFlag == 'Y'}">
								<input type="button" class="bigbutton" name="taskView" value="<s:text name='button.TaskQuery.value' />" onclick="openWinTask('${param.swfLogFlowID}');">
								<%--任务查询--%>
							</c:if>
						</c:if>
					</c:if>
				</td>
			</tr>
		</table>
		<mpc:container ID="oMPC" style="width:830px;height:520px;">
			<mpc:page ID="tabMain" TABTITLE="基本訊息" TABTEXT="基本訊息 ">
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 1.报案主信息 --%>
						<%@include file="/pages/commonAcci/compensate/AcciCompensateMainHeadEdit.jsp"%>
						<%-- 2.特别约定 --%>
						<%@include file="/pages/DAA/compensate/DAACompensateCengage.jsp"%>
						<%-- 2.赔偿限额/免赔额 --%>
						<%-- //@include file="/commonAcci/compensate/AcciCompensatePrpDlimit.jsp" --%>
						<%-- 3.赔付标的信息--%>
						<%--//@include file="/commonAcci/compensate/AcciCompensateLlossEdit.jsp"--%>
						<%-- 1.报案主信息 --%>
						<%@include file="/pages/commonAcci/compensate/AcciCompensateMainTailEdit.jsp"%>
						<%-- 4.理算报告 --%>
						<%@include file="/pages/commonAcci/compensate/AcciCompensateTextEdit2.jsp"%>
						<%-- //增加赔款计算过程说明 --%>
						<%@include file="/pages/commonAcci/compensate/AcciCompensateTextEdit.jsp"%>
						<%-- 3.發送再保通知 --%>
						<%@include file="/pages/common/compensate/CompensateNotice.jsp"%>
						<%-- 5.结案报告 意健险不要 2005-08-01--%>
						<%-- 6.指定危险单位信息 --%>
						<%@include file="/pages/common/claim/ClaimRiskUnit.jsp"%>
						<%-- 8.巨灾代码--%>
						<%@include file="/pages/common/claim/ClaimKelpInfo.jsp"%>
						<!-- reason:显示核赔退回原因 -->
						<%-- 9.核赔退回原因--%>
						<c:if test="${swfNotionDtoList != null}">
							<%@include file="/pages/common/compensate/CompensateUndwrtInfo.jsp"%>
						</c:if>
						<%-- 核赔意见 --%>
						<%@include file="/pages/common/pub/UndwrtTextEdit.jsp"%>
						<%-- 送审审核片语和意见 --%>
						<%@include file="/pages/common/sendUndwrt/SendUndwrtTextEdit.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
			<mpc:page ID="tabMain" TABTITLE="賠損人訊息" TABTEXT="賠損人訊息">
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 1.索赔申请人信息  差异化开发去掉索赔申请人
							<%@include file="/pages/common/compensate/CompensateProposer.jsp"%>
						--%>
						<%-- 2.赔付人员信息 --%>
						<%@include file="/pages/commonAcci/compensate/AcciCompensatePersonLossEdit.jsp"%>
						<%-- 3.赔款费用 --%>
						<%@include file="/pages/commonAcci/compensate/AcciCompensateChargeEdit.jsp"%>
						<%-- 支付帳户信息 --%>
						<%@include file="/pages/commonAcci/compensate/EditPrpdpaymentaccountPage.jsp"%>
						<%-- 4.联共保赔款费用分摊信息 --%>
						<c:choose>
							<c:when test="${not empty  requestScope.coinsFlag}">
								<input type="hidden" name="chiefflag" value="${requestScope.coinsFlag}">
								<c:if test="${requestScope.coinsFlag==1 || requestScope.coinsFlag==2|| requestScope.coinsFlag==3 }">
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
		</mpc:container>
		<TABLE id="btnCommon" class="common">
			<TR>
				<TD align="center">
					<c:if test="${param.editTypeOther!='SHOWTASK'}">
						<%-- 保存通用按钮 --%>
						<%@include file="/pages/commonAcci/compensate/AcciCompensateSave.jsp"%>
					</c:if>
				</TD>
			</TR>
		</TABLE>
	</form>
</DIV>
</body>
</html>
