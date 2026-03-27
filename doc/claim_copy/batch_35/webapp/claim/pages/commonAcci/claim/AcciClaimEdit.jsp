<%--
****************************************************************************
* DESC       ：立案登记录入/修改页面(非车险)
* AUTHOR     ：中科软
* CREATEDATE ：2014-03-12
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/tlds/claim-app.tld" prefix="app"%>
<%@ page import="java.util.*"%>
<%@ page import="com.sinosoft.sysframework.reference.*"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns:mpc>
<head>
<meta http-equiv="content-type" content="text/html;charset=GBK">
<!--对title处理-->
<title><s:text name="title.claimyBeforeEdit.initiateProc" /></title>
<%--立案处理--%>
<%-- 标签页样式 --%>
<jsp:include page="/behaviors/MpcStyle.jsp" />
<%-- 页面样式  --%>
<%@ include file="/common/meta_js.jsp"%>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<%-- 标签页样式 --%>
<script src="${ctx }/pages/commonAcci/claim/js/AcciClaimEdit.js"></script>
<script src="${ctx }/pages/commonAcci/claim/js/AcciClaimEditDwr.js"></script>
<script src="${ctx}/pages/DAA/compensate/js/autoHospital.js"></script>
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
<c:set var="editType" value="${param.editType}"></c:set>
<s:if test="#parameters.editTypeOther=='SHOWTASK'">
	<body class="interface" onload="initPage();initSet();readonlyAllInput();hideButton('buttonLayer');oMPC.style.visibility='visible';">
</s:if>
<s:elseif test="#parameters.editType[0]=='SHOW'">
	<body class="interface" onload="initPage();initSet();readonlyAllInput();disabledAllButton('buttonArea'); disabledAllButton('ClaimLoss_button');disabledAllButton('ClaimLoss');oMPC.style.visibility='visible';">
</s:elseif>
<s:else>
	<body class="interface" onload="initPage();initSet();oMPC.style.visibility='visible';">
</s:else>
<form name="fm" action="/claim/claimSave.do" method="post" onsubmit="return validateForm(this);"  autocomplete="off">
	<input type="hidden" name="editType" value="${editType}">
	<c:if test="${editType == 'ADD' || editType == 'EDIT'}">
		<s:token></s:token>
	</c:if>
	<c:set var="oldClaimLastAccessedTime" value="" scope="session"></c:set>
	<!-- 判断是否重复提交使用 -->
	<DIV id="mainLayer" class="mainLayer">
		<table id="btnTable" cellpadding="0" cellspacing="0" border="0" style="height: 22px">
			<tr>
				<td align="left">
					<c:if test="${param.editTypeOther!='SHOWTASK'}">
						<input type="button" name="messageSave" class='bigbutton' value="<s:text name='button.claimsProcessingRecords.value' />" onclick="return openWinSave('${prpLclaim.registNo}',fm.prpLclaimPolicyNo.value,fm.prpLclaimRiskCode.value,'claim',fm.prpLclaimClaimNo.value);">
						<%--赔案处理记录--%>
						<input type="button" name="eCertify" class="button" value="<s:text name='button.electronicDocuments.value' />" onClick="openCertify('certifyFinishQueryList','prpLcertifyCertifyNo','${prpLclaim.registNo}','claim');">
						<%--单证上传--%>
						<c:if test="${requestScope.needUndwrtFlag=='Y'}">
							<c:choose>
								<c:when test="${requestScope.sendUndwrtFlag=='Y'}">
									<input type="button" class="button" name="taskView" value="<s:text name='button.TaskQuery.value' />" onclick="openWinTask('${param.swfLogFlowID}');">
									<%--任务查询--%>
								</c:when>
							</c:choose>
						</c:if>
					</c:if>
				</td>
			</tr>
		</table>
		<mpc:container ID="oMPC">
			<mpc:page ID="tabMain" TABTITLE="基本訊息" TABTEXT="基本訊息">
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 1.立案主头信息 --%>
						<%@include file="/pages/commonAcci/claim/AcciClaimMainHeadEdit.jsp"%>
						<%-- 3.立案尾信息 --%>
						<%@include file="/pages/commonAcci/claim/AcciClaimMainTailEdit.jsp"%>
						<%-- 送审审核片语和意见 --%>
					</DIV>
				</CENTER>
			</mpc:page>
			<mpc:page ID="tabMain" TABTITLE="險別估損訊息" TABTEXT="險別估損訊息">
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 7.险别估损金额信息 --%>
						<%@include file="/pages/commonAcci/claim/AcciClaimLossEdit.jsp"%>
						<%-- 8.指定危险单位信息 --%>
						<%@include file="/pages/common/claim/ClaimRiskUnit.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
			<mpc:page ID="tabMain" TABTITLE="出險摘要" TABTEXT="出險摘要">
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 5.巨灾代码--%>
						<%@include file="/pages/common/claim/ClaimKelpInfo.jsp"%>
						<%-- 6.立案报告 --%>
						<%@include file="/pages/DAA/claim/DAAClaimTextEdit.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
			<mpc:page ID="tabMain" TABTITLE="受損訊息" TABTEXT="受損訊息">
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 5.报案受损信息页面 --%>
						<%@include file="/pages/common/regist/RegistPersonTraceEdit.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
			<mpc:page ID="tabMain" TABTITLE="索賠申請人訊息" TABTEXT="索賠申請人訊息" style="display:none">
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						 索赔申请人信息 
						<%@include file="/pages/common/claim/ClaimProposer.jsp"%>
						 4.添加单证 
					</DIV>
				</CENTER>
			</mpc:page>
		</mpc:container>
		<TABLE id="btnCommon" class="common">
			<TR>
				<TD align="center">
					<c:if test="${param.editTypeOther!='SHOWTASK'}">
						<%-- 9.保存通用按钮 --%>
						<%@include file="/pages/commonAcci/claim/AcciClaimSave.jsp"%>
					</c:if>
				</TD>
			</TR>
		</TABLE>
	</DIV>
</form>
</body>
</html>
