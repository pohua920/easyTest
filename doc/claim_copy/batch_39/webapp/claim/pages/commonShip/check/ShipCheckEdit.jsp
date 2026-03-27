<%--
****************************************************************************
* DESC       ：查勘登记录入/修改页面
* AUTHOR     ：中科软
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>

<%@	page contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>

<html xmlns:mpc>
<head>
<title>
	<s:text name="title.checkBeforeEdit.editCheck"/><%-- 查勘登记 --%>
</title>
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<%-- 标签页样式 --%>
<jsp:include page="/behaviors/MpcStyle.jsp" />
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<script type="text/javascript">
	//mpc调整
	$(function() {
		initWindow();
		$(window).resize(function() {
			initWindow();
		});
	})
</script>
<script src="/claim/pages/commonShip/check/js/ShipCheckEdit.js"></script>
<script src="${ctx}/pages/DAA/compensate/js/autoHospital.js"></script>
</head>
<s:if test="#parameters.editTypeOther=='SHOWTASK'">
	<body onload="initPage();readonlyAllInput();hideButton('buttonLayer');oMPC.style.visibility='visible';" >
</s:if>
<s:elseif test="#attr.editType=='SHOW' || #attr.editType=='DELETE'">
	<body onload="initPage();readonlyAllInput();disabledAllButton('buttonArea');oMPC.style.visibility='visible';" >
</s:elseif>
<s:else>
	<body onload="initPage();oMPC.style.visibility='visible';" >
</s:else>
<DIV id="mainLayer" class="mainLayer">
	<form name=fm action="${ctx}/check/checkEditPost.do" method="post" onsubmit="return validateForm(this);">
		<c:if test="${editType == 'ADD' || editType == 'EDIT'}">
			<s:token></s:token>
		</c:if>
		<TABLE id="btnTable" cellpadding="0" cellspacing="0" border="0" style="height: 22px">
			<TR>
				<td align="left">
					<c:if test="${param.editTypeOther!='SHOWTASK'}">
						<input type="button" name="prpLmessageSave" class="bigbutton" value="<s:text name='button.claimsProcessingRecords.value'/>" onclick="openWinSave(fm.prpLcheckRegistNo.value,fm.prpLcheckPolicyNo.value,fm.prpLcheckRiskCode.value,'check','');"><%-- 赔案处理记录 --%>
						<input type="button" name="buttonCertifyDirect" class="bigbutton" value="<s:text name='button.stateClaim.value'/>" onClick="doCertifyDirect('${prpLcheck.id.registNo }','check')"><%-- 索赔清单 --%>
						<input type="button" name="eCertify" class="bigbutton" value="<s:text name='button.electronicDocuments.value'/>" onClick="openCertify('certifyFinishQueryList','prpLcertifyCertifyNo','${prpLcheck.id.registNo}','check');"> <%-- 电子单证 --%>
						<s:if test="#needUndwrtFlag=='Y'">
							<s:if test="#sendUndwrtFlag=='Y'">
								<input type="button" class="button" name="taskView" value="<s:text name='button.TaskQuery.value'/>" onclick="openWinTask('${param.swfLogFlowID }');"> <%-- 任务查询 --%>
							</s:if>
						</s:if>
					</c:if>
				</td>
			</TR>
		</TABLE>
		<mpc:container ID="oMPC">
			<%-- 报案基本信息页面 --%>
			<mpc:page ID="tabMain" TABTITLE="基本信息" TABTEXT="基本信息">
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 1.查勘/代查勘头信息 --%>
						<%@include file="/pages/commonShip/check/ShipCheckMainHeadEdit.jsp"%>
						<%-- 2.查勘备注信息 --%>
						<%@include file="/pages/commonShip/check/ShipCheckTextEdit.jsp"%>
						<%-- 3.巨灾代码 --%>
						<%@include file="/pages/common/regist/RegistKelpInfo.jsp"%>
						<%-- 4.送审审核片语和意见 --%>
						<%@include file="/pages/common/sendUndwrt/SendUndwrtTextEdit.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
			<mpc:page ID="tabMain" TABTITLE="<s:text name="check.damagePropertyInfo" />" TABTEXT="<s:text name="check.damagePropertyInfo" />">
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
		</mpc:container>
		<TABLE id="btnCommon" class="common">
			<TR>
				<TD align="center">
					<c:if test="${param.editTypeOther!='SHOWTASK'}">
						<%--7.保存通用按钮 --%>
						<%@include file="/pages/commonShip/check/ShipCheckSave.jsp" %>
					</c:if>
				</TD>
			</TR>
		</TABLE>
	</form>
</DIV>
</body>
</html>
