<%--
****************************************************************************
* DESC       ：查勘登记录入/修改页面
* AUTHOR     ：中科软
* CREATEDATE ：2013-1-03
* MODIFYLIST ：   Name       Date            Reason/Contents
***************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="com.sinosoft.claim.dto.domain.*"%>
<%@ page import="com.sinosoft.claim.dto.custom.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.sinosoft.sysframework.reference.*"%>
<%@ page import="com.sinosoft.sysframework.common.datatype.*"%>
<%@ page import="com.sinosoft.prpall.schema.*"%>
<html xmlns:mpc>
<head>
<!--对title处理-->
<title>
	<%-- 查勘登记 --%> <s:text name="certainLoss.prpLcheck.prpLcheckRegist" />
</title>
<%-- 标签页样式 --%>
<jsp:include page="/behaviors/MpcStyle.jsp" />
<%-- 页面样式	--%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<script language="JavaScript">
	javascript: window.history.forward(1);
	/**
	 *@description 设置画面的初始值
	 *@param	     无
	 *@return      通过返回true,否则返回false
	 */
	function loadCheckLoss() {
		var referKind = "${prpLcheck.referKind}";
		var arrayReferKind = new Array();
		arrayReferKind = referKind.split(",");
		var i = 0;
		var j = 0;
		for (i = 0; i < arrayReferKind.length; i++) {
			for (j = 0; j < fm.prpLcheckReferKind.length; j++) {
				if (fm.prpLcheckReferKind[j].value == arrayReferKind[i]) {
					fm.prpLcheckReferKind[j].checked = true;
					break;
				}
			}
		}
	}
</script>
<script type="text/javascript">
	//mpc调整
	$(function() {
		initWindow();
		$(window).resize(function() {
			initWindow();
		});
	})
</script>
<script src="/claim/pages/DAA/regist/js/DAARegistEdit.js"></script>
<script src="/claim/pages/DAA/certainLoss/js/DAACertainLossEdit.js"></script>
<script src="/claim/pages/DAA/check/js/DAACheckEdit.js"></script>
<script src="/claim/pages/DAA/regist/js/DAAThirdCarLossEdit.js"></script>
<script src="/claim/common/js/date/WdatePicker.js"></script>
</head>
<s:if test="#request.editType=='SHOW'||#request.editType=='DELETE'">
	<body class="interface" onload="initPage();initSet();initSetForCheck();readonlyAllInput();oMPC.style.visibility='visible'">
</s:if>
<s:else>
	<body class="interface" onload="initPage();initSet();initSetForCheck();oMPC.style.visibility='visible';initAdvance();">
</s:else>
<DIV id="mainLayer" class="mainLayer">
	<form name=fm action="${ctx}/check/checkEditPost.do" method="post" onsubmit="return validateForm(this);">
		<c:if test="${editType == 'ADD' || editType == 'EDIT'}">
			<s:token></s:token>
		</c:if>
		<input type="hidden" name="nodeType" value="check">
		<input type="hidden" name="editType" value="${editType}">
		<input type="hidden" name="riskcode" value="${prpLcheck.riskCode}">
		<input type="hidden" name="policyno" value="${prpLcheck.policyNo}">
		<TABLE id="btnTable" cellpadding="0" cellspacing="0" border="0">
			<TR>
				<td align="left">
					<input type="button" style="width: 92px" name="prpLmessageSave" class="bigbutton" value="<s:text name='button.claimsProcessingRecords.value' />"
						onclick="openWinSave(fm.prpLcheckRegistNo.value,fm.prpLcheckPolicyNo.value,fm.prpLcheckRiskCode.value,'check','');">
					<%-- 赔案处理记录 --%>
					<input type="button" name="eCertify" class="button" value="<s:text name='button.electronicDocuments.value' />"
						onClick="openCertify('certifyFinishQueryList','prpLcertifyCertifyNo','${prpLcheck.id.registNo}','check');">
					<%-- 电子单证 --%>
					<input type="button" name="buttonCertifyDirect" class="button" value="<s:text name='button.stateClaim.value' />" onClick="doCertifyDirect('${prpLcheck.id.registNo}','check')">
					<%-- 索赔清单 --%>
					<input class=button style="display:none" type="button" name="certifyDirectPrint" value="<s:text name='button.stateNote.value' />" onClick="certifyDirectList('${prpLcheck.id.registNo}','certi')">
				    <%-- 索赔须知 --%>
				</td>
			</TR>
		</TABLE>
		<mpc:container ID="oMPC">
			<mpc:page ID="tabMain" TABTITLE="基本訊息" TABTEXT="<s:text name="regist.prpLregist.registMain" />">
				<%--基本信息 --%>
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 1.查勘/代查勘主信息 --%>
						<%@include file="/pages/DAA/check/DAACheckMainEdit.jsp"%>
						<%-- 特别约定 --%>
						<%-- <%@include file="/pages/DAA/compensate/DAACompensateCengage.jsp"%> --%>
						<%-- 6.查勘备注信息 --%>
						<%@include file="/pages/DAA/regist/DAARegistTextEdit.jsp"%>
						<%-- 4.报案信息补充说明 --%>
						<%@include file="/pages/DAA/regist/DAARegistExtEdit.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
			<mpc:page ID="tabMain" TABTITLE="<s:text name="certainLoss.generSurvey.extentInfo" />" TABTEXT="<s:text name="certainLoss.generSurvey.extentInfo" />">
				<%--查勘/代查勘扩展信息 --%>
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 2.查勘/代查勘扩展 --%>
						<%@include file="/pages/DAA/check/DAACheckExtEdit.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
			<mpc:page ID="tabMain" TABTITLE="<s:text name="check.damagePropertyInfo" />" TABTEXT="<s:text name="check.damagePropertyInfo" />">
				<%--损失信息 --%>
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 3.涉案车辆 --%>
						<%@include file="/pages/DAA/claim/DAAClaimThirdPartyEdit.jsp"%>
						<%--  财产损失部位信息 --%>
						<%@include file="/pages/DAA/regist/DAARegistThirdPropEdit.jsp"%>
						<%-- 4.1 人伤跟踪信息 --%>
						<%@ include file="/pages/DAA/claim/DAAClaimPersonTraceEdit.jsp"%>
						<%-- 5.驾驶员信息 --%>
						<%@include file="/pages/DAA/claim/DAAClaimDriverEdit.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
		</mpc:container>
		<TABLE id="btnCommon" class="common">
			<TR>
				<TD align="center">
					<%-- 保存通用按钮 --%>
					<%@include file="/pages/DAA/check/DAACheckSave.jsp"%>
				</TD>
			</TR>
		</TABLE>
	</form>
</DIV>
</body>
</html>