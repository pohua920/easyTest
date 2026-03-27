<%@	page contentType="text/html; charset=GBK" language="java"%>
<%--
****************************************************************************
* DESC       ：核损登记录入/修改页面
* AUTHOR     ：理赔组
* CREATEDATE ：2013-02-23
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@page import="com.sinosoft.sysframework.reference.AppConfig"%>
<%@ include file="/common/taglibs.jsp"%>
<script language="JavaScript">
	javascript: window.history.forward(1);
</script>
<%@ include file="/common/meta_js.jsp"%>
<html xmlns:mpc>
<head>
<!--对title处理-->
<title><s:text name="title.verifyLossBeforeEdit.editVerifyLoss" /></title>
<%--核损登记 --%>
<app:css />
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<%-- 标签页样式 --%>
<jsp:include page="/behaviors/MpcStyle.jsp" />
<script src="/claim/pages/DAA/verifyLoss/js/DAAVerifyLossEdit.js"></script>
<script src="/claim/pages/DAA/certainLoss/js/DAAVerifyLossRepairComponentEdit.js"></script>
<META http-equiv="Content-Type" content="text/html;	charset=GBK">
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
<c:choose>
	<c:when test="${param.editType =='SHOW'}">
		<body class="interface" onload="initPage();initSet();readonlyAllInput();oMPC.style.visibility='visible'">
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="${param.nodeType =='backc'}">
				<body class="interface" onload="initPage();initSet();initReadonly();oMPC.style.visibility='visible'">
			</c:when>
			<c:otherwise>
				<body class="interface" onload="initPage();initSet();oMPC.style.visibility='visible'">
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>
<form name="fm" action="${ctx}/verifyLoss/verifyLossSave.do" method="post" onsubmit="return validateForm(this);">
	<input type="hidden" name="editType" value="${editType}">
	<c:if test="${editType == 'ADD' || editType == 'EDIT' }">
		<s:token></s:token>
	</c:if>
	<input type="hidden" name="nodeType" value="<c:out value='${param.nodeType}'/>">
	<input type="hidden" name="prpLverifyLossPayFee" value="<c:out value='${requestScope.payFee}'/>" />
	<DIV id="mainLayer" class="mainLayer">
		<TABLE id="btnTable" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td align="left">
					<input type="button" name="message" class=bigbutton value="<s:text name='button.claimsProcessingRecords.value' />"
						onclick="openWinSave(fm.prpLverifyLossRegistNo.value,fm.prpLverifyLossPolicyNo.value,fm.prpLverifyLossRiskCode.value,'verif',fm.prpLverifyLossClaimNo.value);">
					<%--赔案处理记录 --%>
					<input type="button" name="eCertify"  class=button value="<s:text name='button.electronicDocuments.value' />"
						onClick="openCertify('certifyFinishQueryList','prpLcertifyCertifyNo','${requestScope.prpLcheckTemp.id.registNo}','verif')">
					<%--电子单证 --%>
					<input type="button" name="buttonCertifyDirect" class=button value="<s:text name='button.stateClaim.value' />" onClick="doCertifyDirect('${requestScope.prpLcheckTemp.id.registNo}','verif')">
				</td>
				<%--索赔清单 --%>
			</tr>
		</table>
		<mpc:container ID="oMPC">
			<mpc:page ID="tabMain" TABTITLE="<s:text name="regist.prpLregist.registMain" />" TABTEXT="<s:text name="regist.prpLregist.registMain" />">
				<%--基本信息--%>
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 核损主表的画面 --%>
						<%@include file="/pages/DAA/verifyLoss/DAAVerifyLossMainEdit.jsp"%>
						<%-- 当是核损的时候显示备注,核损意见 --%>
						<%-- 4.报案信息补充说明 --%>
						<%@include file="/pages/DAA/regist/DAARegistExtEdit.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
			<mpc:page ID="tabMain" TABTITLE="<s:text name="certainLoss.vehicleInfo" />" TABTEXT="<s:text name="certainLoss.vehicleInfo" />">
				<%--车辆信息--%>
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 1.核损环节过程的修理/换件清单页面 --%>
						<%@include file="/pages/DAA/verifyLoss/DAAVerifyLossRepairComponentEdit.jsp"%>
						<%-- 当是核损的时候显示备注,核损意见 --%>
						<%-- 3. 核价、核损意见、备注 --%>
						<%@include file="/pages/DAA/certainLoss/DAACertainLossOpinion.jsp"%>
						<%-- 4. 定核损信息补充说明 --%>
						<%@include file="/pages/DAA/certainLoss/DAACertainLossExtEdit.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
		</mpc:container>
		<TABLE id="btnCommon" class="common">
			<TR>
				<TD align="center">
					<%-- 保存通用按钮 --%>
					<%@include file="/pages/DAA/verifyLoss/DAAVerifyLossSave.jsp"%>
				</TD>
			</TR>
		</TABLE>
	</DIV>
</form>
</body>
</html>
