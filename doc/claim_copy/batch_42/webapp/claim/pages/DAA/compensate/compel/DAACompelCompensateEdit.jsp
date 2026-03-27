<%@	page contentType="text/html; charset=GBK" language="java"%>
<%--
****************************************************************************
* DESC       ：实赔录入/修改页面
* AUTHOR     ：理赔组 陈杰
* CREATEDATE ： 2013-03-14
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta_js.jsp"%>
<html xmlns:mpc>
<head>
<!--对title处理-->
<title><s:text name="title.compensateBeforeEdit.editCompensate" /></title>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script src="${ctx}/pages/DAA/compensate/compel/js/DAACompelCompensateEditNew.js"></script>
<script src="${ctx}/pages/DAA/compensate/compel/js/DAACompelCompensatePayObjectEdit.js"></script>
<script src="${ctx}/pages/DAA/compensate/compel/js/DAACompelChargeEdit.js"></script>
<script src="${ctx}/pages/DAA/compensate/compel/js/DAACompelPersonLossEdit.js"></script>
<script src="${ctx}/pages/DAA/compensate/js/DAAlLossEdit.js"></script>
<script src="${ctx}/pages/DAA/compensate/js/autoHospital.js"></script>
<%-- 标签页样式 --%>
<jsp:include page="/behaviors/MpcStyle.jsp" />
<script type="text/javascript">
	//mpc调整
	$(function() {
		initWindow();
		$(window).resize(function() {
			initWindow();
		});
	})
</script>
<script language="JavaScript">
	javascript: window.history.forward(1);
</script>
<script type="text/javascript">
$(document)
.ready(
	function () {
		//医院代码查询不用禁用按钮
		$(
			":input[name!='prpLpersonCommerceHospitalCode'][name!='prpLpersonCommerceHospitalName']")
			.filter(":enabled").ajaxStart(function () {
				$(this).attr("disabled", true); //请求开始禁用按钮
			}).ajaxComplete(function () {
				$(this).attr("disabled", false); //请求完成恢复按钮
			});
	});
</script>
</head>
<c:choose>
	<c:when test="${param.editType=='SHOW'}">
		<body class="interface" onload="initPage();initSet();calSumDutyPaid();readonlyAllInput();disabledAllButton('buttonArea');">
	</c:when>
	<c:otherwise>
		<body class="interface" onload="initPage();initSet();calSumDutyPaid();">
	</c:otherwise>
</c:choose>
<c:choose>
	<c:when test="${param.editType=='DELETE'}">
		<form name=fm action="${ctx}/compensate/compensateDelete.do" method="post" onsubmit="return validateForm(this);" autocomplete="off">
	</c:when>
	<c:otherwise>
		<form name=fm action="${ctx}/compensate/compensateSave.do" method="post" onsubmit="return validateForm(this);" autocomplete="off">
	</c:otherwise>
</c:choose>
<c:if test="${editType == 'ADD' || editType == 'EDIT'}">
	<s:token></s:token>
</c:if>
<input type="hidden" name="editType" value="${editType}">
<DIV id="mainLayer" class="mainLayer">
	<TABLE id="btnTable" cellpadding="0" cellspacing="0" border="0">
		<TR>
			<td align="left">
				<%-- 多车互碰理赔计算 --%>
				<input type="button" class="bigbutton" name="prpLmessageSave" value="<s:text name='button.claimsProcessingRecords.value' />"
					onclick="openWinSave(fm.prpLcompensateClaimNo.value,fm.prpLcompensatePolicyNo.value,fm.prpLcompensateRiskCode.value,'compe',fm.prpLcompensateClaimNo.value)">
				<%-- 赔案处理记录 --%>
				<input type="button" name="eCertify" class="button" value="<s:text name='button.electronicDocuments.value' />"
					onClick="openCertify('certifyFinishQueryList','prpLcertifyCertifyNo',fm.prpLregistExtRegistNo.value,'compe');">
				<%-- 电子单证 --%>
			</td>
		</tr>
	</table>
	<mpc:container ID="oMPC">
		<mpc:page ID="tabMain" TABTITLE="基本訊息" TABTEXT="基本訊息">
			<CENTER>
				<DIV name="tabMain" class="tabMain">
					<%@include file="/pages/DAA/compensate/DAACompensateMainHeadEdit.jsp"%>
					<%-- 2.特别约定 <%@include file="/pages/DAA/compensate/DAACompensateCengage.jsp"%> --%>
					<%-- 6.理算主信息 --%>
					<jsp:include page="/pages/DAA/compensate/DAACompensateMainTailEdit.jsp" />
					<%--如果是案终赔付，增加结案报告
                            <jsp:include page="/pages/DAA/compensate/DAAEndCaseCompensateTextEdit.jsp"/>
                            --%>
					<%-- 2.单证主信息 --%>
					<%--理算任务处理，屏蔽“工作质量审核信息 ”栏，－刘国安确认--%>
					<jsp:include page="/pages/DAA/compensate/DAAPrpLqualityCheckEdit.jsp" />
					<%-- 4.报案信息补充说明 --%>
					<jsp:include page="/pages/DAA/regist/DAARegistExtEdit.jsp" />
					<%-- 核赔意见 --%>
					<jsp:include page="/pages/common/pub/UndwrtTextEdit.jsp" />
				</DIV>
			</CENTER>
		</mpc:page>
		<mpc:page ID="tabMain" TABTITLE="賠付訊息" TABTEXT="賠付訊息">
			<CENTER>
				<DIV name="tabMain" class="tabMain">
					<c:if test="${empty chargeType || chargeType !='D'}">
						<%@include file="/pages/DAA/compensate/compel/DAACompelCompensatePropEdit.jsp"%>
						<%@include file="/pages/DAA/compensate/compel/DAACompelCompensatePersonLossEdit.jsp"%>
						<%@include file="/pages/DAA/compensate/compel/DAACompensatePropPersonSum.jsp"%>
						<%-- 9.車體險訊息 
						<jsp:include page="/pages/DAA/compensate/DAACompensateCarInsurance.jsp" />
						--%>
					</c:if>
					<%@include file="/pages/DAA/compensate/compel/DAACompelCompensateChargeEdit.jsp"%>
					<c:if test="${empty chargeType || chargeType !='D'}">
						<%-- 支付帳户信息 --%>
						<%@include file="/pages/common/compensate/EditPrpdpaymentaccountPage.jsp"%>
					</c:if>
					<%-- 7.理算报告 --%>
					<%@include file="/pages/DAA/compensate/DAACompensateTextEdit.jsp"%>
				</DIV>
			</CENTER>
		</mpc:page>
		<c:if test="${certainLossFlag}">
			<mpc:page ID="tabMain" TABTITLE="<s:text name="certainLoss.vehicleInfo" />" TABTEXT="<s:text name="certainLoss.vehicleInfo" />">
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 5.定损讯息  --%>
						<jsp:include page="/pages/DAA/compensate/DAACompensateRepairComponentEdit.jsp" />
					</DIV>
				</CENTER>
			</mpc:page>
		</c:if>
		<mpc:page ID="tabMain" TABTITLE="危險單位訊息" TABTEXT="危險單位訊息">
			<CENTER>
				<DIV name="tabMain" class="tabMain">
					<%-- 5.指定危险单位信息 --%>
					<%@include file="/pages/common/claim/ClaimRiskUnit.jsp"%>
				</DIV>
			</CENTER>
		</mpc:page>
	</mpc:container>
	<TABLE id="btnCommon" class="common">
		<TR>
			<TD align="center">
				<%-- 8.保存通用按钮 --%>
				<%@include file="/pages/DAA/compensate/DAACompensateSave.jsp"%>
			</td>
		</tr>
	</table>
</DIV>
</form>
</body>
</html>