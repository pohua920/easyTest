<%--
****************************************************************************
* DESC       ：预赔登记录入/修改页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-05-10
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns:mpc>
<head>
<SCRIPT LANGUAGE="JavaScript">
	function initAlert() {
		var prePayFlag = fm.prePayFlagTemp.value;
		if (prePayFlag == '0') {
			alert("<s:text name='prompt.prepay.feeFailure'/>！");//保费未实收，系统不允许预赔
		}
	}
</SCRIPT>
<!--对title处理-->
<title><s:text name="title.prepayBeforeEdit.editPrepay" /></title>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<%-- 标签页样式 --%>
<jsp:include page="/behaviors/MpcStyle.jsp" />
<script src="${ctx }/pages/DAA/prepay/js/DAAPrepayEdit.js"></script>
<%@ include file="/common/meta_js.jsp"%>
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
<s:if test="#attr.editType=='SHOW'">
	<body class="interface" onload="initPage();initSet();readonlyAllInput();oMPC.style.visibility='visible';">
</s:if>
<s:else>
	<body class=interface onload="initPage();initSet();initAlert();oMPC.style.visibility='visible';">
</s:else>
<DIV id="mainLayer" class="mainLayer">
	<form name=fm action="${ctx }/prepaySave.do" method="post" onsubmit="return validateForm(this);">
		<iframe name=CodeFrame src='/claim/common/pub/QueryCodeInputOverviewNone.jsp' style='DISPLAY: none; Z-INDEX: 100; POSITION: absolute' marginwidth='0' marginheight='0' hspace='0' vspace='0'
			frameborder='0' scrolling='no'></iframe>
		<c:if test="${editType == 'ADD' || editType == 'EDIT'}">
			<s:token></s:token>
		</c:if>
		<input type="hidden" name="prePayFlagTemp" value="${prePayFlag }">
		<TABLE id="btnTable" cellpadding="0" cellspacing="0" border="0">
			<TR>
				<td align="left">
					<!--赔案处理记录-->
					<input type="button" class="bigbutton" name="message" value="<s:text name='button.claimsProcessingRecords.value'/>"
						onclick="openWinSave(fm.prpLprepayClaimNo.value,fm.prpLprepayPolicyNo.value,fm.prpLprepayRiskCode.value,'prepa',fm.prpLprepayClaimNo.value)">
					<!--电子单证-->
					<input type="button" name="eCertify" class=button value="<s:text name='button.electronicDocuments.value'/>"
						onClick="openCertify('certifyFinishQueryList','prpLcertifyCertifyNo',fm.registNo.value,'speci');">
					<!--索赔清单-->
					<input type="button" name="buttonCertifyDirect" class=button value="<s:text name='button.stateClaim.value'/>" onClick="doCertifyDirect(fm.registNo.value,'speci')">
				</td>
			</tr>
		</TABLE>
		<mpc:container ID="oMPC">
			<mpc:page ID="tabMain" TABTITLE="<s:text name="regist.prpLregist.PreCompensationRegistMain" />" TABTEXT="<s:text name="regist.prpLregist.PreCompensationRegistMain" />">
				<%--预赔基本信息 --%>
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 1.预赔主信息 --%>
						<%@include file="/pages/DAA/prepay/DAAPrepayMainEdit.jsp"%>
						<%-- 赔付支付信息   --%>
						<%@include file="/pages/common/prepay/EditPrpdpaymentaccountPrePage.jsp"%>
						<%-- 4.预赔备注信息 --%>
						<%@include file="/pages/DAA/prepay/DAAPrepayTextEdit.jsp"%>
						<%-- 5.核赔意见 --%>
						<%@include file="/pages/common/pub/UndwrtTextEdit.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
		</mpc:container>
		<TABLE id="btnCommon" class="common">
			<TR>
				<TD align="center">
					<%-- 保存通用按钮 --%>
					<%@include file="/pages/DAA/prepay/DAAPrepaySave.jsp"%>
				</TD>
			</TR>
		</TABLE>
	</form>
</DIV>
</body>
</html>
