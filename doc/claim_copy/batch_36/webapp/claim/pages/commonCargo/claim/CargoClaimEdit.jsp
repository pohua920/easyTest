<%--
****************************************************************************
* DESC	   ：立案登记录入/修改页面(非车险)
* AUTHOR	 ：中科软
* CREATEDATE ：2014-04-02
* MODIFYLIST ：   Name	   Date			Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/tlds/claim-app.tld" prefix="app" %>
<%@ page import="com.sinosoft.sysframework.reference.*"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns:mpc>
	<head>
		<!--对title处理-->
		<title><s:text name="title.claimyBeforeEdit.initiateProc"/></title><%-- 立案处理 --%>
		<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
		<%-- 标签页样式 --%>
		<jsp:include page="/behaviors/MpcStyle.jsp" />
		<%@include file="/common/i18njs.jsp"%>
		<%--	页面样式 --%>
		<%@ include file="/common/meta_js.jsp"%>
		<script src="/claim/pages/commonCargo/claim/js/CargoClaimEdit.js"></script>
		<script src="/claim/pages/commonCargo/claim/js/CargoClaimEditDwr.js"></script>
	<!--这个函数是调动所能用到的通用js的过程，一般包括最常用的js的函数声明都在meta_js.jsp中-->
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
	<s:if test="#request.editTypeOther =='SHOWTASK'">
	 <body class="interface" onload="initPage();initSet();readonlyAllInput();hideButton('buttonLayer');oMPC.style.visibility='visible';" >
	</s:if>
	<s:elseif test="#request.editType =='SHOW'">
	<!-- 查看页面，按钮要灰掉-->
	 <body class="interface" onload="initPage();initSet();readonlyAllInput();disabledAllButton('buttonArea');disabledAllButton('ClaimLoss_button');oMPC.style.visibility='visible';" >
	</s:elseif>
	<s:else>
	<body class="interface" onload="initPage();initSet();oMPC.style.visibility='visible';" >
	</s:else>
		<form name=fm action="/claim/claimSave.do" method="post" onsubmit="return validateForm(this);">
			<c:set var="oldClaimLastAccessedTime" value="" scope="session" />
			<DIV id="mainLayer" class="mainLayer"  >
				<table id="btnTable" cellpadding="0" cellspacing="0" border="0">
					<TR>
						<td>
							<input type="button" name="message" value="<s:text name='button.claimsProcessingRecords.value'/>" class="bigbutton" onclick="return openWinSave('${prpLclaim.registNo}',fm.prpLclaimPolicyNo.value,'${prpLclaim.riskCode}','claim','${prpLclaim.claimNo}');"><%-- 赔案处理记录 --%>
						</td>
						<td></td>
						<td>
							<input type="button" name="eCertify" class="bigbutton" value="<s:text name='button.checkedUpload.value'/>" onClick="openCertify('certifyFinishQueryList','prpLcertifyCertifyNo','${prpLclaim.registNo}','claim');"> <%-- 单证上传 --%>
						</td>
						<c:if test="${needUndwrtFlag == 'Y' &&  sendUndwrtFlag == 'Y'}">
						<td></td>
						<td>
							<input type="button" class="bigbutton" name="taskView" value="<s:text name='button.TaskQuery.value'/>" onclick="openWinTask('${swfLogFlowID}');"> <%-- 任务查询 --%>
						</td>
						</c:if> 
					</TR>
				</table>
				<mpc:container ID="oMPC" >
				 <%-- 1.1.报案基本信息页面 --%>
					<mpc:page ID="tabMain" TABTITLE="<s:text name='regist.prpLregist.registMain'/>" TABTEXT="<s:text name='regist.prpLregist.registMain'/>"><%-- 基本讯息 --%>
					<CENTER>
						<DIV  name="tabMain" class="tabMain" >
							<%-- 1.立案主头信息 --%>
							<%@include file="/pages/commonCargo/claim/CargoClaimMainHeadEdit.jsp" %>
							<%-- 2.立案中间信息 --%>
							<%@include file="/pages/commonCargo/claim/CargoClaimMainMiddleEdit.jsp" %>
							<%-- 3.立案尾信息 --%>
							<%@include file="/pages/commonCargo/claim/CargoClaimMainTailEdit.jsp" %>
							<%-- 7.立案报告 --%>
							<%@include file="/pages/commonCargo/claim/CargoClaimTextEdit.jsp"%>
							<%-- 6.巨灾代码--%>
							<%@include file="/pages/common/claim/ClaimKelpInfo.jsp"%>
							<%-- 送审审核片语和意见 --%>
							<%@include file="/pages/common/sendUndwrt/SendUndwrtTextEdit.jsp"%>
						</DIV>
					</CENTER>
					</mpc:page>
					<mpc:page ID="tabMain" TABTITLE="<s:text name='claim.amountInsurLossInfo'/>" TABTEXT="<s:text name='claim.amountInsurLossInfo'/>">
						<CENTER>
						<DIV  name="tabMain" class="tabMain">
							<%-- 8.险别估损金额信息 --%>
							<%@include file="/pages/commonCargo/claim/CargoClaimLossEdit.jsp"%>
						</DIV>
					</CENTER>
					</mpc:page>
					<mpc:page ID="tabMain" TABTITLE="<s:text name='claim.dangerousUnitInfo'/>" TABTEXT="<s:text name='claim.dangerousUnitInfo'/>">
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
						<TD align="center">
							<%-- 10.保存通用按钮 --%>
							<%@include file="/pages/commonCargo/claim/CargoClaimSave.jsp"%>
						</TD>
					</TR>
				</TABLE>
			</DIV>
		</form>
	</body>
</html>