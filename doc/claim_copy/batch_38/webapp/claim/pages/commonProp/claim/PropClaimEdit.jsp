<%--
****************************************************************************
* DESC       ：立案登记录入/修改页面(非车险)
* AUTHOR     ：中科软
* CREATEDATE ：2013-07-14
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta_js.jsp"%>
<%@page import="com.sinosoft.sysframework.reference.*"%>
<html xmlns:mpc>
<head>
<!--对title处理-->
<title><s:text name="title.claimyBeforeEdit.initiateProc" /></title>
<%-- 立案处理 --%>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<%-- 页面样式  --%>
<%@ include file="/common/meta_js.jsp"%>
<%-- 标签页样式 --%>
<jsp:include page="/behaviors/MpcStyle.jsp" />
<script src="${ctx }/pages/commonProp/claim/js/PropClaimEdit.js"></script>
<script src="${ctx }/pages/commonProp/claim/js/PropClaimEditDwr.js"></script>
<script type="text/javascript">
	//mpc调整
	$(function() {
		initWindow();
		$(window).resize(function() {
			initWindow();
		});
	})
</script>
</head>
<s:if test="#parameters.editTypeOther=='SHOWTASK'">
	<body class="interface" onload="initPage();initSet();readonlyAllInput();hideButton('buttonLayer');oMPC.style.visibility='visible';">
</s:if>
<s:elseif test="#request.editType=='SHOW'">
	<!--查看页面，按钮要灰掉-->
	<body class="interface" onload="initPage();initSet();readonlyAllInput();disabledAllButton('buttonArea');disabledAllButton('ClaimLoss_button');disabledAllButton('ClaimLoss');oMPC.style.visibility='visible';">
</s:elseif>
<%-- mantis：CLM0272 ，處理人員：DP0713，需求單編號：新核心-立案修改功能新增火險修改 Start --%>
<s:elseif test="#request.editType=='EDIT' && #request.specialEditCase=='specialEditCase'">
	<body class="interface" onload="initPage();initSet();initenableEditTableField();oMPC.style.visibility='visible';">
</s:elseif>
<%-- mantis：CLM0272 ，處理人員：DP0713，需求單編號：新核心-立案修改功能新增火險修改 Start --%>
<s:else>
	<body class="interface" onload="initPage();initSet();oMPC.style.visibility='visible';">
</s:else>
<DIV id="mainLayer" class="mainLayer">
	<form name=fm action="${ctx }/claimSave.do" method="post" onsubmit="return validateForm(this);" autocomplete="off">
		<input type="hidden" name="editType" value="${editType}">
		<c:if test="${editType == 'ADD' || editType == 'EDIT'}">
			<s:token></s:token>
		</c:if>
		<c:set var="oldClaimLastAccessedTime" value="" scope="session"></c:set>
		<table id="btnTable" cellpadding="0" cellspacing="0" border="0" style="height: 22px">
			<tr>
				<td>
					<c:if test="${param.editTypeOther!='SHOWTASK'}">
						<input type="button" name="message" class="bigbutton" value="<s:text name='button.claimsProcessingRecords.value'/>"
							onclick="openWinSave(fm.registno.value,fm.prpLclaimPolicyNo.value,'${prpLclaim.riskCode}','claim','${prpLclaim.claimNo}');">
						<%-- 赔案处理记录 --%>
						<input type="button" name="eCertify" class="bigbutton" value="<s:text name='button.checkedUpload.value'/>"
							onClick="openCertify('certifyFinishQueryList','prpLcertifyCertifyNo','${prpLclaim.registNo}','check');">
						<%-- 单证上传 --%>
						<c:if test="${needUndwrtFlag=='Y' && sendUndwrtFlag=='Y'}">
							<input type="button" class="bigbutton" name="taskView" value="<s:text name='button.TaskQuery.value'/>" onclick="openWinTask('${param.swfLogFlowID}');">
							<%-- 任务查询 --%>
						</c:if>
					</c:if>
				</td>
			</tr>
		</table>
		<mpc:container ID="oMPC">
			<mpc:page ID="tabMain" TABTITLE="<s:text name='regist.prpLregist.registMain'/>" TABTEXT="<s:text name='regist.prpLregist.registMain'/>"><%-- 基本讯息 --%>
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 1.立案主头信息 --%>
						<%@include file="/pages/commonProp/claim/PropClaimMainHeadEdit.jsp"%>
						<%-- 2.立案中间信息 --%>
						<%@include file="/pages/commonProp/claim/PropClaimMainMiddleEdit.jsp"%>
						<%-- 3.立案尾信息 --%>
						<%@include file="/pages/commonProp/claim/PropClaimMainTailEdit.jsp"%>
						<%--送审审核片语和意见 --%>
						<%@include file="/pages/common/sendUndwrt/SendUndwrtTextEdit.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
			<mpc:page ID="tabMain" TABTITLE="<s:text name='claim.infoAmountLoss'/>" TABTEXT="<s:text name='claim.infoAmountLoss'/>"><%-- 估损金额讯息 --%>
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%@include file="/pages/commonProp/claim/PropClaimLossEdit.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
			<mpc:page ID="tabMain" TABTITLE="<s:text name='db.regist.registText.textType1'/>" TABTEXT="<s:text name='db.regist.registText.textType1'/>"><%-- 出险摘要 --%>
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 7.立案报告 --%>
						<%@include file="/pages/commonProp/claim/PropClaimTextEdit.jsp"%>
						<%-- 6.巨灾代码--%>
						<%@include file="/pages/common/claim/ClaimKelpInfo.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
			<mpc:page ID="tabMain" TABTITLE="<s:text name='claim.dangerousUnitInfo'/>" TABTEXT="<s:text name='claim.dangerousUnitInfo'/>"><%-- 危险单位讯息 --%>
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 9.指定危险单位信息 --%>
						<%@include file="/pages/common/claim/ClaimRiskUnit.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
		</mpc:container>
		<TABLE id="btnCommon" class="common">
			<TR>
				<TD align="center">
					<c:if test="${param.editTypeOther!='SHOWTASK'}">
						<%-- 10.保存通用按钮 --%>
						<%@include file="/pages/commonProp/claim/PropClaimSave.jsp"%>
					</c:if>
				</TD>
			</TR>
		</TABLE>
	</form>
</DIV>
</body>
</html>
