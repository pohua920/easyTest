<%--
****************************************************************************
* DESC	：立案登记录入/修改页面(非车险)
* AUTHOR     ：理赔组
* CREATEDATE ：2014-04-15
* MODIFYLIST ：   Name	Date	     Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/tlds/claim-app.tld" prefix="app" %>
<%@page import="com.sinosoft.claim.dto.domain.*"%>
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.sysframework.reference.*"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns:mpc>
	<head>
		<!--对title处理-->
		<title><s:text name="title.claimyBeforeEdit.initiateProc" /></title><%--立案处理--%>
		<%-- 页面样式  --%>
		<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
		<%-- 标签页样式 --%>
		<jsp:include page="/behaviors/MpcStyle.jsp" />
		<%@include file="/common/i18njs.jsp"%>
		<%--页面样式 --%>
		<%@ include file="/common/meta_js.jsp"%>
		<script src="/claim/pages/commonLiab/claim/js/LiabClaimEdit.js"></script>
		<script src="/claim/pages/commonLiab/claim/js/LiabClaimEditDwr.js"></script>
		<!--这个函数是调动所能用到的通用js的过程，一般包括最常用的js的函数声明都在meta_js.jsp中-->
		<script type="text/javascript">
			var riskCode = '${prpLclaim.riskCode}';
			var perilCount = '${prpLregistDto1.perilCount}';
			if(riskCode == "GC" && perilCount > 15){		 
				alert(i18n.prompt.claim.overContractNumber);//此保單已超過約定賠款次數(15次)，請注意。
			}
			
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
		<form name=fm action="/claim/claimSave.do" method="post" onsubmit="return validateForm(this);" autocomplete="off">
			<input type="hidden" name="editType" value="${editType}">
			<c:if test="${editType == 'ADD' || editType == 'EDIT'}">
				<s:token></s:token>
			</c:if>
			<c:set var="oldClaimLastAccessedTime" scope="session" value=""/>
			<DIV id="mainLayer" class="mainLayer">
				<TABLE  id="btnTable" cellpadding="0" cellspacing="0" border="0" style="height: 22px">
					<TR>
						<td>
							<c:if test="${param.editTypeOther!='SHOWTASK'}">
								<input type="button" name="message" value="<s:text name='button.claimsProcessingRecords.value' />"  class="bigbutton" onclick="return openWinSave('${prpLclaim.registNo}',fm.prpLclaimPolicyNo.value,fm.prpLclaimRiskCode.value,'claim',fm.prpLclaimClaimNo.value);"><%--赔案处理记录--%>
								<input type="button" name="eCertify" class="bigbutton" value="<s:text name='button.electronicDocuments.value'/>" onClick="openCertify('certifyFinishQueryList','prpLcertifyCertifyNo','${prpLclaim.registNo}','claim');">
								<c:if test="${needUndwrtFlag == 'Y' &&  sendUndwrtFlag == 'Y'}">
									<input type="button" class="bigbutton" name="taskView" value="<s:text name='button.TaskQuery.value' />" onclick="openWinTask('<%=request.getParameter("swfLogFlowID") %>');"><%--任务查询--%>
								</c:if>
							</c:if>
						</td>
					</TR>
				</TABLE>
				<mpc:container ID="oMPC" >
					<%-- 1.1.报案基本訊息页面 --%>
					<mpc:page ID="tabMain" TABTITLE="基本訊息" TABTEXT="基本訊息">
						<CENTER>
							<DIV name="tabMain" class="tabMain">
								<%-- 1.立案主头訊息 --%>
								<%@include file="/pages/commonLiab/claim/LiabClaimMainHeadEdit.jsp" %>
								<%-- 2.立案中间訊息 --%>
								<%@include file="/pages/commonLiab/claim/LiabClaimMainMiddleEdit.jsp" %>
								<%-- 3.立案尾訊息 --%>
								<%@include file="/pages/commonLiab/claim/LiabClaimMainTailEdit.jsp" %>
								<%-- 6.添加单证 --%>
								<%//@include file="/commonLiab/claim/LiabClaimDocEdit.jsp"%>
								<%-- 7.立案报告 --%>
								<%@include file="/pages/commonLiab/claim/LiabClaimTextEdit.jsp"%>
								<%-- modify by liyanjie move 4.巨灾代码  20051024--%>   
								<%-- 4.巨灾代码--%>
								<%@include file="/pages/common/claim/ClaimKelpInfo.jsp"%>
								<%-- 送审审核片语和意见 --%>
							    <%@include file="/pages/common/sendUndwrt/SendUndwrtTextEdit.jsp"%>
							  </DIV>
						</CENTER>
					</mpc:page>
					<mpc:page ID="tabMain" TABTITLE="估损金额訊息" TABTEXT="估损金额訊息">
						<CENTER>
							<DIV name="tabMain" class="tabMain">
							<%-- 5.险别估损金额訊息 --%>
							<%@include file="/pages/commonLiab/claim/LiabClaimLossEdit.jsp"%>
							</DIV>
						</CENTER>
					</mpc:page>
					<mpc:page ID="tabMain" TABTITLE="危险单位訊息" TABTEXT="危险单位訊息">
						<CENTER>
							<DIV name="tabMain" class="tabMain">
							<%-- 8.指定危险单位訊息 --%>
							<%@include file="/pages/common/claim/ClaimRiskUnit.jsp"%>
							</DIV>
						</CENTER>
					</mpc:page>
					<mpc:page ID="tabMain" TABTITLE="受损訊息" TABTEXT="受损訊息">
						<CENTER>
							<DIV name="tabMain" class="tabMain">
							<%-- 5.报案受损訊息页面 --%>
							<%-- 人伤跟踪訊息 --%>
							<%@ include file="/pages/commonLiab/regist/LiabRegistPersonTraceEdit.jsp"%>
							<%@include file="/pages/commonLiab/regist/LiabRegistThirdPropEdit.jsp"%>
							</DIV>
						</CENTER>
					</mpc:page>
				</mpc:container>
				<TABLE id="btnCommon" class="common">
					<TR>
						<TD align="center">
							<c:if test="${param.editTypeOther!='SHOWTASK'}">
								<%-- 9.保存通用按钮 --%>
								<%@include file="/pages/commonLiab/claim/LiabClaimSave.jsp"%>
							</c:if>
						</TD>
					</TR>
				</TABLE>
			</DIV> 
		</form>
	</body>
</html>