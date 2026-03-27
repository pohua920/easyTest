<%--
****************************************************************************
* DESC       ：立案登记录入/修改页面(非车险)
* AUTHOR     ：中科软
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="com.sinosoft.sysframework.reference.*"%>
<html xmlns:mpc>
<head>
	<!--对title处理-->
	<title><s:text name="title.claimyBeforeEdit.initiateProc" /></title><%--立案处理--%>
	<%-- 页面样式  --%>
	<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
	<jsp:include page="/behaviors/MpcStyle.jsp" />
	<%@include file="/common/i18njs.jsp"%>
	<%@ include file="/common/meta_js.jsp"%>
	<%-- 标签页样式 --%>
	<script src="${ctx}/pages/commonShip/claim/js/ShipClaimEdit.js"></script>
	<script src="${ctx}/pages/commonShip/claim/js/ShipClaimEditDwr.js"></script>
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
<s:if test="#parameters.editTypeOther =='SHOWTASK'">
	<body class="interface" onload="initPage();initSet();readonlyAllInput();hideButton('buttonLayer');oMPC.style.visibility='visible';" >
</s:if>
<s:elseif test="#request.editType =='SHOW'">
	<body class="interface" onload="initPage();initSet();readonlyAllInput();disabledAllButton('buttonArea');disabledAllButton('ClaimLoss_button');oMPC.style.visibility='visible';" >
</s:elseif>
<s:else>
	<body class="interface" onload="initPage();initSet();oMPC.style.visibility='visible';" >
</s:else>
<DIV id="mainLayer" class="mainLayer">
<form name=fm action="/claim/claimSave.do" method="post" onsubmit="return validateForm(this);" autocomplete="off">
	<input type="hidden" name="editType" value="${editType}">
	<c:if test="${editType == 'ADD' || editType == 'EDIT'}">
		<s:token></s:token>
	</c:if>
	<c:set var="oldClaimLastAccessedTime" value="" scope="session" />
	<table id="btnTable" cellpadding="0" cellspacing="0" border="0" style="height: 22px">
		<TR>
			<td>
				<c:if test="${param.editTypeOther!='SHOWTASK'}">
					<input type="button" name="message" value="<s:text name='button.claimsProcessingRecords.value'/>" class="bigbutton" onclick="return openWinSave('${prpLclaim.registNo}',fm.prpLclaimPolicyNo.value,'${prpLclaim.riskCode}','claim','${prpLclaim.claimNo}');"><%-- 赔案处理记录 --%>
					<input type="button" name="eCertify" class="bigbutton" value="<s:text name='button.checkedUpload.value'/>" onClick="openCertify('certifyFinishQueryList','prpLcertifyCertifyNo','${prpLclaim.registNo}','claim');"> <%-- 单证上传 --%>
					<c:if test="${needUndwrtFlag == 'Y' &&  sendUndwrtFlag == 'Y'}">
						<input type="button" class="bigbutton" name="taskView" value="<s:text name='button.TaskQuery.value'/>" onclick="openWinTask('${swfLogFlowID}');"> <%-- 任务查询 --%>
					</c:if>
				</c:if>
			</td>
		</TR>
	</table>
	<mpc:container ID="oMPC">
		<%-- 1.1.报案基本信息页面 --%>
		<mpc:page ID="tabMain" TABTITLE="<s:text name='regist.prpLregist.registMain'/>" TABTEXT="<s:text name='regist.prpLregist.registMain'/>"><%-- 基本讯息 --%>
			<CENTER>
				<DIV name="tabMain" class="tabMain">
				<%-- 1.立案主头信息 --%>
				<%@include file="/pages/commonShip/claim/ShipClaimMainHeadEdit.jsp" %>
				<%-- 2.立案中间信息 --%>
				<%@include file="/pages/commonShip/claim/ShipClaimMainMiddleEdit.jsp" %>
				<%-- 3.立案尾信息 --%>
				<%@include file="/pages/commonShip/claim/ShipClaimMainTailEdit.jsp" %>
				<%-- 7.立案报告 --%>
				<%@include file="/pages/commonShip/claim/ShipClaimTextEdit.jsp"%>
				<%-- 6.巨灾代码--%>
				<%@include file="/pages/common/claim/ClaimKelpInfo.jsp"%>
				<%-- 送审审核片语和意见 --%>
				<%@include file="/pages/common/sendUndwrt/SendUndwrtTextEdit.jsp"%>
				</DIV>
			</CENTER>
		</mpc:page>
		<mpc:page ID="tabMain" TABTITLE="<s:text name="check.damagePropertyInfo2" />" TABTEXT="<s:text name="check.damagePropertyInfo2" />">
				<%--损失信息 --%>
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 财产损失部位信息 --%>
						<%@include file="/pages/commonShip/regist/ShipRegistThirdPropEdit.jsp"%>
						<%-- 人伤跟踪信息 --%>
						<%@ include file="/pages/commonShip/claim/ShipClaimPersonTraceEdit.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
		<mpc:page ID="tabMain" TABTITLE="<s:text name='claim.amountInsurLossInfo'/>" TABTEXT="<s:text name='claim.amountInsurLossInfo'/>"><%-- 险别估损金额信息 --%>
			<CENTER>
				<DIV  name="tabMain" class="tabMain">
				<%-- 8.险别估损金额信息 --%>
				<%@include file="/pages/commonShip/claim/ShipClaimLossEdit.jsp"%>
				</DIV>
			</CENTER>
		</mpc:page>
		<mpc:page ID="tabMain" TABTITLE="<s:text name='claim.dangerousUnitInfo'/>" TABTEXT="<s:text name='claim.dangerousUnitInfo'/>"><%-- 危险单位信息 --%>
			<CENTER>
				<DIV  name="tabMain" class="tabMain">
				<%-- 9.指定危险单位信息 --%>
				<%@include file="/pages/common/claim/ClaimRiskUnit.jsp"%>
				</DIV>
			</CENTER>
		</mpc:page>
	</mpc:container>
	<TABLE id="btnCommon" class="common">
		<TR>
			<TD>
				<c:if test="${param.editTypeOther!='SHOWTASK'}">
					<%-- 10.保存通用按钮 --%>
					<%@include file="/pages/commonShip/claim/ShipClaimSave.jsp"%>
				</c:if>
			</TD>
		</TR>
	</TABLE>
</form>
</DIV>
</body>
</html>