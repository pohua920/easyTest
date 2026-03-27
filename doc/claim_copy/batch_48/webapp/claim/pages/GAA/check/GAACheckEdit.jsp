<%--
****************************************************************************
* DESC       ：查勘登记录入/修改页面
* AUTHOR     ：理赔组
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page contentType="text/html; charset=GBK"%>
<html xmlns:mpc>
<head>
<!--对title处理-->
<title>
	<%-- 查勘登记 --%>
	<s:text name="certainLoss.prpLscheduleMainWF.prpLcheckRegist" />
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
<script src="/claim/pages/GAA/check/js/GAACheckEdit.js"></script>
</head>
<s:if test="#parameters.editTypeOther=='SHOWTASK'">
	<body class="interface" onload="initPage();initSet();readonlyAllInput();hideButton('buttonLayer');oMPC.style.visibility='visible';">
</s:if>
<s:elseif test="#parameters.editType[0]=='SHOW'||#parameters.editType[0]=='DELETE'">
	<body class="interface" onload="initPage();initSet();readonlyAllInput();disabledAllButton('buttonArea');oMPC.style.visibility='visible';">
</s:elseif>
<s:else>
	<body onload="initPage();initSet();oMPC.style.visibility='visible';">
</s:else>
<DIV id="mainLayer" class="mainLayer">
	<form name="fm" action="${ctx}/check/checkEditPost.do" method="post" onsubmit="return validateForm(this);">
		<c:if test="${editType == 'ADD' || editType == 'EDIT'}">
			<s:token></s:token>
		</c:if>
		<table id="btnTable" border="0" cellpadding="0" cellspacing="0" style="height: 22px">
			<tr>
				<td align="left">
					<c:if test="${param.editTypeOther!='SHOWTASK'}">
						<input type="button" class=bigbutton name="prpLmessageSave" class="button" value="<s:text name='button.claimsProcessingRecords.value'/>"
							onclick="openWinSave(fm.prpLcheckRegistNo.value,fm.prpLcheckPolicyNo.value,fm.prpLcheckRiskCode.value,'check','');">
							<%-- 赔案处理记录 --%>
						<input type="button" name="eCertify" class="button" value="<s:text name='button.electronicDocuments.value'/>"
							onClick="openCertify('certifyFinishQueryList','prpLcertifyCertifyNo','${prpLcheck.id.registNo}','check');">
							<%--电子单证  --%>
						<input type="button" name="buttonCertifyDirect" class="button" value="<s:text name='button.stateClaim.value'/>" onClick="doCertifyDirect('${prpLcheck.id.registNo}','check')">
							<%-- 索赔清单 --%>
						<c:if test="${needUndwrtFlag=='Y'}">
							<c:if test="${sendUndwrtFlag=='Y'}">
								<input type="button" class="bigbutton" name="taskView" value="<s:text name='button.TaskQuery.value'/>" onclick="openWinTask('#parameters.swfLogFlowID');">
								<%-- 任务查询 --%>
							</c:if>
						</c:if>
					</c:if>
				</td>
			</tr>
		</table>
		<mpc:container ID="oMPC">
			<mpc:page ID="tabMain" TABTITLE="<s:text name='title.checkBeforeEdit.editCheck'/>" TABTEXT="<s:text name='title.checkBeforeEdit.editCheck'/>">
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 1.查勘/代查勘头信息 --%>
						<%@include file="/pages/GAA/check/GAACheckMainHeadEdit.jsp"%>
						<%-- 2.查勘中信息 --%>
						<%--@include file="/pages/GAA/check/GAACheckMainMiddleEdit.jsp" --%>
						<%-- 3.查勘尾信息 --%>
						<%@include file="/pages/GAA/check/GAACheckMainTailEdit.jsp"%>
						<%-- 4.查勘备注信息 --%>
						<%@include file="/pages/GAA/regist/GAARegistTextEdit.jsp"%>
						<%--5.估损金额信息 --%>
						<%--@include file="/pages/GAA/claim/GAAClaimFeeEdit.jsp"--%>
						<%-- 6.巨灾代码--%>
						<%@include file="/pages/common/regist/RegistKelpInfo.jsp"%>
						<%--7.送审审核片语和意见 --%>
						<%@include file="/pages/common/sendUndwrt/SendUndwrtTextEdit.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
			<mpc:page ID="tabMain" TABTITLE="<s:text name="check.damagePropertyInfo" />" TABTEXT="<s:text name="check.damagePropertyInfo" />">
				<%--损失信息 --%>
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 财产损失部位信息 --%>
						<%@include file="/pages/GAA/regist/GAARegistThirdPropEdit.jsp"%>
						<%-- 人伤跟踪信息 --%>
						<%@ include file="/pages/GAA/regist/RegistPersonTraceEdit.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
		</mpc:container>
		<TABLE id="btnCommon" class="common">
			<TR>
				<TD align="center">
					<c:if test="${param.editTypeOther!='SHOWTASK'}">
						<%-- 保存通用按钮 --%>
						<%@include file="/pages/GAA/check/GAACheckSave.jsp"%>
					</c:if>
				</TD>
			</TR>
		</TABLE>
	</form>
</DIV>
</body>
</html>
