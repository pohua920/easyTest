<%@	page contentType="text/html; charset=GBK" language="java"%>
<%-- 
**************************************************************************
* DESC       ：定损登记录入/修改页面
* AUTHOR     ：理赔组
* CREATEDATE ：2013-02-23
* MODIFYLIST ：   Name       Date            Reason/Contents
**************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta_js.jsp"%>
<html xmlns:mpc>
<head>
<!--对title处理-->
<title><s:text name="title.certainLossBeforeEdit.editCertainLoss" /> <!--s:text name="certainLoss.prpLverifyLoss.lossregist"/--></title>
<!--定损登记-->
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<%-- 标签页样式 --%>
<jsp:include page="/behaviors/MpcStyle.jsp" />
<script src="/claim/pages/DAA/certainLoss/js/DAACertainLossEdit.js"></script>
<script src="/claim/pages/DAA/certainLoss/js/DAACertainLossRepairComponentEdit.js"></script>
<script type="text/javascript">
	//mpc调整
	$(function() {
		initWindow();
		$(window).resize(function() {
			initWindow();
		});
	})

	function checkNum(field) {
		var re = /^[0-9]*$/; //匹配正整数
		if (field.value != "" && !re.test(field.value)) { //
			alert("請輸入正確數字");
			field.focus();
			return false;
		}
	}
	javascript: window.history.forward(1);
</script>
</head>
<c:set var="oldCertainLossLastAccessedTime" value="" scope="session"/>
<c:choose>
	<c:when test="${param.editType =='SHOW'}">
		<body onload="initPage();initSet();readonlyAllInput();oMPC.style.visibility='visible';">
	</c:when>
	<c:when test="${param.editType =='ADD'}">
		<body onload="initPage();initSet();oMPC.style.visibility='visible';">
	</c:when>
	<c:otherwise>
		<body onload="initPage();initSet();oMPC.style.visibility='visible';">
	</c:otherwise>
</c:choose>
<DIV id="mainLayer" class="mainLayer">
	<c:set var="strPrpLclaimGradeFlag" value="" />
	<c:set var="prpLclaimGradeValueLower" value="0" />
	<c:set var="prpLclaimGradeValueUpper" value="0" />
	<c:set var="prpLclaimGrade1ValueUpper" value="0" />
	<c:set var="prpLclaimGrade2ValueUpper" value="0" />
	<c:if test="${requestScope.prpLclaimGrade1 != null}">
		<c:set var="prpLclaimGrade1ValueUpper" value="${requestScope.prpLclaimGrade1.valueUpper}" />
	</c:if>
	<c:if test="${requestScope.prpLclaimGrade2 != null}">
		<c:set var="prpLclaimGrade2ValueUpper" value="${requestScope.prpLclaimGrade2.valueUpper}" />
	</c:if>
	<c:choose>
		<c:when test="${requestScope.prpLclaimGrade != null}">
			<c:set var="prpLclaimGradeValueLower" value="${requestScope.prpLclaimGrade.valueLower}" />
			<c:set var="prpLclaimGradeValueUpper" value="${requestScope.prpLclaimGrade.valueUpper}" />
		</c:when>
		<c:otherwise>
			<c:set var="strPrpLclaimGradeFlag" value="1" />
		</c:otherwise>
	</c:choose>
	<form name="fm" action="${ctx}/certainLoss/certainLossSave.do" method="post" onsubmit="return validateForm(this);">
		<input type="hidden" name="nodeType" value="${param.nodeType }">
		<input type="hidden" name="editType" value="${editType}">
		<input type="hidden" name="prpLclaimGradeValueLower" value="${pageScope.prpLclaimGradeValueLower}">
		<input type="hidden" name="prpLclaimGradeValueUpper" value="${pageScope.prpLclaimGradeValueUpper}">
		<input type="hidden" name="prpLclaimGradeFlag" value="${pageScope.strPrpLclaimGradeFlag}">
		<input type="hidden" name="prpLclaimGradeSumWorkHourFee" value="${pageScope.prpLclaimGrade1ValueUpper}">
		<input type="hidden" name="prpLclaimGradeSumChangCompFee" value="${pageScope.prpLclaimGrade2ValueUpper}">
		<input type="hidden" name="certainLossFirst" value="${requestScope.prplCertianLossFirst}" />
		<input type="hidden" name="riskcode" value="${requestScope.prpLregist.riskCode}" />
		<input type="hidden" name="policyno" value="${requestScope.prpLregist.policyNo}">
		<input type="hidden" name="prpLcertainLossPayFee" value="${payFee}" />
		<c:if test="${param.editType == 'ADD' || param.editType == 'EDIT'}">
			<s:token />
		</c:if>
		<%-- 保存通用按钮页面 --%>
		<TABLE id="btnTable" cellpadding="0" cellspacing="0" border="0">
			<TR>
				<td align="left">
					<!--赔案处理记录-->
					<input type="button" style="width: 92px" class="button" name="message" value="<s:text name='button.claimsProcessingRecords.value'/>"
						onclick="openWinSave(fm.RegistNo.value,fm.prpLverifyLossPolicyNo.value,fm.prpLverifyLossRiskCode.value,'certa',fm.prpLverifyLossClaimNo.value);">
					<!--电子单证-->
					<input type="button" class="button" name="eCertify" value="<s:text name='button.electronicDocuments.value'/>"
						onClick="openCertify('certifyFinishQueryList','prpLcertifyCertifyNo','${requestScope.prpLregist.registNo }','certa');">
					<!--索赔清单-->
					<input type="button" class="button" name="buttonCertifyDirect" value="<s:text name='button.stateClaim.value'/>" onClick="doCertifyDirect('${requestScope.prpLregist.registNo }','certa')">
					<!--多车互碰理赔计算-->
					<input type="hidden" class="bigbutton" name="ManyCar" value="<s:text name='button.manyCalculations.value'/>" title="多車互碰理賠計算" onclick="showManyCar()">
				</td>
			</tr>
		</TABLE>
		<mpc:container ID="oMPC">
			<mpc:page ID="tabMain" TABTITLE="<s:text name="regist.prpLregist.registMain" />" TABTEXT="<s:text name="regist.prpLregist.registMain" />">
				<%--基本信息--%>
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 1.1.1.定损基本讯息 --%>
						<%@include file="/pages/DAA/certainLoss/DAACertainLossMainEdit.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
			<mpc:page ID="tabMain" TABTITLE="<s:text name="claim.cheSunInfo" />" TABTEXT="<s:text name="claim.cheSunInfo" />">
				<%--车损信息--%>
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%@include file="/pages/DAA/certainLoss/DAARepairComponentMainEdit.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
		</mpc:container>
		<TABLE id="btnCommon" class="common">
			<TR>
				<TD align="center"><%@include file="/pages/DAA/certainLoss/DAACertainLossSave.jsp"%></TD>
			</TR>
		</TABLE>
	</form>
</DIV>
</body>
</html>
