
<%--
****************************************************************************
* DESC       ：查勘登记录入/修改页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-06-03
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@page import="com.sinosoft.claim.schema.model.*"%>
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.sysframework.reference.*"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns:mpc>
<head>
<!--对title处理-->
<title><s:text name="certainLoss.prpLacciCheck.regist" /></title>
<%--查勘登记 --%>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<%@ include file="/common/meta_js.jsp"%>
<%-- 标签页样式 --%>
<jsp:include page="/behaviors/MpcStyle.jsp" />
<script src="/claim/pages/commonAcci/check/js/AcciCheckEdit.js"></script>
<!--这个函数是调动所能用到的通用js的过程，一般包括最常用的js的函数声明都在meta_js.jsp中-->
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
<s:if test="#parameters.editTypeOther=='SHOWTASK'">
	<body class="interface" onload="initPage();readonlyAllInput();hideButton('buttonLayer');oMPC.style.visibility='visible';">
</s:if>
<s:elseif test="#parameters.editType[0]=='SHOW'||parameters.editType[0]=='DELETE'">
	<body class="interface" onload="initPage();readonlyAllInput();oMPC.style.visibility='visible';">
</s:elseif>
<s:else>
	<body class="interface" onload="initPage();oMPC.style.visibility='visible';">
</s:else>
<DIV id="mainLayer" class="mainLayer">
	<form name=fm action="${ctx}/check/checkEditPost.do" method="post" onsubmit="return validateForm(this);">
		<c:if test="${editType == 'ADD' || editType == 'EDIT'}">
			<s:token></s:token>
		</c:if>
		<TABLE id="btnTable" border="0" cellpadding="0" cellspacing="0" style="height: 22px">
			<tr>
				<td align="left">
					<c:if test="${param.editTypeOther!='SHOWTASK'}">
						<input type="button" class="bigbutton" name="prpLmessageSave" value="<s:text name='button.claimsProcessingRecords.value' />" onclick="openWinSave(fm.prpLacciCheckRegistNo.value,fm.prpLacciCheckPolicyNo.value,fm.prpLacciCheckRiskCode.value,'check',fm.prpLcheckClaimNo.value);">
						<%--赔案处理记录--%>
						<input type="button" name="eCertify" class="button" value="<s:text name='button.electronicDocuments.value' />" onClick="openCertify('certifyFinishQueryList','prpLcertifyCertifyNo','${prpLacciCheck.registNo}','check');">
						<%--电子单证--%>
						<input type="button" name="buttonCertifyDirect" class="button" value="<s:text name='button.stateClaim.value' />" onClick="doCertifyDirect('${prpLacciCheck.registNo}','check')">
						<%--索赔清单--%>
						<c:if test="${needUndwrtFlag=='Y'}">
							<c:if test="${sendUndwrtFlag=='Y'}">
								<input type="button" class="button" name="taskView" value="<s:text name='button.TaskQuery.value' />" onclick="openWinTask('#parameters.swfLogFlowID[0]');">
								<%--任务查询--%>
							</c:if>
						</c:if>
					</c:if>
				</td>
			</tr>
		</TABLE>
		<mpc:container ID="oMPC">
			<mpc:page ID="tabMain" TABTITLE="調查登記" TABTEXT="調查登記">
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 1.查勘/代查勘头信息 --%>
						<%@include file="/pages/commonAcci/check/AcciCheckMainHeadEdit.jsp"%>
						<%-- 2.查勘中信息 --%>
						<%--@include file="/commonAcci/check/AcciCheckMainMiddleEdit.jsp" --%>
						<%-- 3.查勘尾信息 --%>
						<%@include file="/pages/commonAcci/check/AcciCheckMainTailEdit.jsp"%>
						<%-- 3.赔款费用 --%>
						<%--@include file="/commonAcci/check/AcciCheckChargeEdit.jsp"--%>
						<%-- 4.调查费用 --%>
						<%@include file="/pages/commonAcci/check/AcciCheckChargeEdit.jsp"%>
						<%-- 5.查勘备注信息 --%>
						<%@include file="/pages/commonAcci/check/AcciRegistTextEdit.jsp"%>
						<%-- 6.巨灾代码--%>
						<%@include file="/pages/common/regist/RegistKelpInfo.jsp"%>
						<%-- 送审审核片语和意见 --%>
						<%@include file="/pages/common/sendUndwrt/SendUndwrtTextEdit.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
		</mpc:container>
		<%--6.估损金额信息 --%>
		<%--@include file="/commonAcci/claim/AcciClaimFeeEdit.jsp"%-->
		<%--5.保存通用按钮 --%>
		<TABLE id="btnCommon" class="common">
			<TR>
				<TD align="center">
					<c:if test="${param.editTypeOther!='SHOWTASK'}">
						<%@include file="/pages/commonAcci/check/AcciCheckSave.jsp"%>
					</c:if>
				</TD>
			</TR>
		</TABLE>
	</form>
</DIV>
</body>
</html>
