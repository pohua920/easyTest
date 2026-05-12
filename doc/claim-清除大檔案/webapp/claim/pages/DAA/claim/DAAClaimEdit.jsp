<%--
****************************************************************************
* DESC       ：立案登记录入/修改页面
* AUTHOR     ：中科软
* CREATEDATE ：2013-04-14
* MODIFYLIST ：   Name       Date            Reason/Contents
                  理赔组     2013/07/02      去掉了估损金额信息画面，修改了格式
****************************************************************************
--%>
<%@ page import="com.sinosoft.claim.schema.model.PrpLclaim"%>
<%@ page contentType="text/html; charset=GBK"%>
<%@page import="com.sinosoft.sysframework.reference.AppConfig"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="ins.framework.common.*"%>
<%@ page import="com.sinosoft.utiall.schema.PrpDcodeSchema"%>
<%@ page import="com.sinosoft.claim.schema.model.PrpLcheck"%>
<html xmlns:mpc>
<head>
<!--对title处理-->
<title><s:text name="title.claimyBeforeEdit.initiateProc" /> <%-- 立案处理 --%></title>
<jsp:include page="/behaviors/MpcStyle.jsp" />
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<script src="${ctx }/pages/DAA/regist/js/DAARegistEdit.js"></script>
<script src="${ctx }/pages/DAA/claim/js/DAAClaimEdit.js"></script>
<script src="${ctx }/pages/DAA/claim/js/DAAClaimEditNew.js"></script>
<script src="${ctx }/pages/DAA/regist/js/DAAThirdCarLossEdit.js"></script>
<script src="${ctx }/pages/DAA/claim/js/DAAClaimEditDWR.js"></script>
<%-- 标签页样式 --%>
<%@ include file="/common/meta_js.jsp"%>
<script language="JavaScript">
	javascript: window.history.forward(1);
</script>
<script language="Javascript" src="${ctx }/common/js/InputCode.js"></script>
</head>
<script type="text/javascript">
	//mpc调整
	$(function() {
		initWindow();
		$(window).resize(function() {
			initWindow();
		});
	})
</script>
<c:set var="prpLclaim" value="${prpLclaim}"></c:set>
<c:set var="damageAreaCode" value=""></c:set>
<c:set var="damageAreaName" value=""></c:set>
<c:set var="prpLcheck" value="${prpLcheck}"></c:set>
<c:set var="prpDcodeSchema" value="${prpDcodeSchema}"></c:set>
<c:if test="${prpLcheck != null}">
	<c:set var="damageAreaCode" value="${prpLcheck.damageAreaCode}"></c:set>
</c:if>
<c:if test="${prpDcodeSchema != null}">
	<c:set var="damageAreaName" value="${prpDcodeSchema.codeCName}"></c:set>
</c:if>
<s:if test="#request.editType=='SHOW'">
	<body class="interface" onload="initPage();initSet();oninitSum();readonlyAllInput();disabledAllButton('buttonArea');showButton();oMPC.style.visibility='visible';">
</s:if>
<s:elseif test="#request.editType=='ADD'">
	<body class="interface" onload="initPage();initSet();changeIndemnityDuty1();flashSumClaimNew();oMPC.style.visibility='visible';">
</s:elseif>
<%-- mantis： CLM0197，處理人員：CD078，需求單編號：CLM0197 新核心-新增立案修改出險日期及出險地區功能Start --%>
<s:elseif test="#request.editType=='EDIT' && #requestScope.specialEditCase=='specialEditCase'">
	<body class="interface" onload="initPage();initSet();oninitSum();readonlyAllInput();disabledAllButton('buttonArea');initenableEditTableField();oMPC.style.visibility='visible';">
</s:elseif>
<%-- mantis： CLM0197，處理人員：CD078，需求單編號：CLM0197 新核心-新增立案修改出險日期及出險地區功能End --%>
<s:else>
	<body class="interface" onload="initPage();initSet();oninitSum();oMPC.style.visibility='visible';">
</s:else>
<form name=fm action="${ctx }/claimSave.do" method="post" onsubmit="return validateForm(this);" autocomplete="off">
	<c:if test="${editType == 'ADD' || editType == 'EDIT'}">
		<s:token></s:token>
	</c:if>
	<input type="hidden" name="nodeType" value="claim">
	<input type="hidden" name="editType" value="${editType}">
	<c:set var="oldClaimLastAccessedTime" value="" scope="session"></c:set>
	<!-- 判断是否重复提交使用 -->
	<DIV id="mainLayer" class="mainLayer">
		<TABLE id="btnTable" cellpadding="0" cellspacing="0" border="0">
			<TR>
				<td align="left">
					<input type="button" name="message" class="bigbutton" value="<s:text name='button.claimsProcessingRecords.value' />"
						<%-- 赔案处理记录 --%>
							onclick="return openWinSave(fm.prpLclaimRegistNo.value,'${prpLclaim.policyNo}',fm.prpLclaimRiskCode.value,'claim','');">
					<input type="button" name="eCertify" class=button value="<s:text name='button.electronicDocuments.value' />"
						onClick="openCertify('certifyFinishQueryList','prpLcertifyCertifyNo','${prpLclaim.registNo}','claim');">
					<%-- 电子单证 --%>
				</td>
			</TR>
		</TABLE>
		<mpc:container ID="oMPC">
			<mpc:page ID="tabMain" TABTITLE="<s:text name="regist.prpLregist.registMain" />" TABTEXT="<s:text name="regist.prpLregist.registMain" />">
				<%-- 基本信息--%>
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 1.立案主信息 --%>
						<%@include file="/pages/DAA/claim/DAAClaimMainEdit.jsp"%>
						<%-- 6.立案报告 --%>
						<%@include file="/pages/DAA/claim/DAAClaimTextEdit.jsp"%>
						<%-- 巨灾代码信息--%>
						<%@include file="/pages/DAA/claim/PropClaimKelpInfo.jsp"%>
						<%-- 8.理赔联系记录--%>
						<%-- 
					    	<%@include file="/pages/DAA/regist/DAARegistExtEdit.jsp"%>
					    	--%>
					</DIV>
				</CENTER>
			</mpc:page>
			<mpc:page ID="tabMain" TABTITLE="<s:text name="regist.prpLregist.registLoss" /> " TABTEXT="<s:text name="regist.prpLregist.registLoss" /> ">
				<%--受损信息 --%>
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%@include file="/pages/DAA/claim/DAAClaimThirdPartyEdit.jsp"%>
						<%@include file="/pages/DAA/regist/DAARegistThirdPropEdit.jsp"%>
						<%@include file="/pages/DAA/claim/DAASelfClaimPersonTraceEdit.jsp"%>
						<%@include file="/pages/DAA/claim/DAAClaimDriverEdit.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
			<mpc:page ID="tabMain" TABTITLE="<s:text name="claim.infoAmountLoss" />" TABTEXT=<s:text name="claim.infoAmountLoss" />>
				<%--估损金额信息 --%>
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%@include file="/pages/DAA/claim/DAAClaimLossEdit.jsp"%>
						<%@include file="/pages/DAA/claim/DAAClaimExceptDeductibleRateEdit.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
			<mpc:page ID="tabMain" TABTITLE="<s:text name="claim.dangerousUnitInfo" />" TABTEXT="<s:text name="claim.dangerousUnitInfo" />">
				<%--危险单位信息 --%>
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%@include file="/pages/common/claim/ClaimRiskUnit.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
		</mpc:container>
		<TABLE id="btnCommon" class="common">
			<TR>
				<TD align="center">
					<%-- 保存通用按钮 --%>
					<%@include file="/pages/DAA/claim/DAAClaimSave.jsp"%>
				</TD>
			</TR>
		</TABLE>
	</DIV>
</form>
</body>
</html>