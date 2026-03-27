<%--
****************************************************************************
* DESC       ：报案登记录入/修改页面/非车险通用
* AUTHOR     ：中科软
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns:mpc>
<head>
<%-- 标签页样式 --%>
<jsp:include page="/behaviors/MpcStyle.jsp" />
<title><s:text name="title.registBeforeEdit.editRegist" /></title>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
<%@ include file="/common/meta_js.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<script src="${ctx }/pages/commonLiab/regist/js/LiabRegistEdit.js"></script>
<script src="${ctx }/pages/common/regist/js/95519PerfectEdit.js"></script>
<script language="Javascript" src="${ctx}/common/js/InputCode.js"></script>
<%-- 标签页样式 --%>
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
<c:set var="oldRegistLastAccessedTime" value="" scope="session" />
<s:if test="#parameters.editTypeOther=='SHOWTASK'">
	<body class="interface" onload="initPage();initSet();readonlyAllInput();oMPC.style.visibility='visible';">
</s:if>
<s:elseif test="editType=='SHOW'">
	<body class="interface" onload="initPage();initSet();readonlyAllInput();disabledAllButton('buttonArea');oMPC.style.visibility='visible';">
</s:elseif>
<s:else>
	<body class="interface" onload="initPage();initSet();oMPC.style.visibility='visible';">
</s:else>
<DIV id="mainLayer" class="mainLayer">
	<form name=fm action="${ctx }/registSave.do" method="post" onsubmit="return validateForm(this,'Driver','');">
		<c:if test="${editType == 'ADD' || editType == 'EDIT' || editType == 'PERFECT'}">
			<s:token></s:token>
		</c:if>
		<input type="hidden" name="nodeType" value="regis">
		<input type="hidden" name="editType" value="${param.editType}">
		<TABLE id="btnTable" cellpadding="0" cellspacing="0" border="0" style="height: 22px">
			<TR>
				<TD align="left">
					<c:if test="${param.editTypeOther!='SHOWTASK'}">
						<input type="button" <c:if test="${editType=='ADD'}">disabled</c:if> name="messageSave" class="bigbutton" value="<s:text name='button.claimsProcessingRecords.value'/>" onclick="openWinSave('${prpLregist.registNo}','${prpLregist.policyNo}','${prpLregist.riskCode}','regis','')">
						<%--赔案处理记录  --%>
					</c:if>
				</TD>
			</TR>
		</TABLE>
		<mpc:container ID="oMPC">
			<%-- 1.1.报案基本信息页面 --%>
			<mpc:page ID="tabMain" TABTITLE="<s:text name="menu.regist.edit"/>" TABTEXT="<s:text name="menu.regist.edit"/>">
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 1.报案主信息头 --%>
						<%@include file="/pages/commonLiab/regist/LiabRegistMainHeadEdit.jsp"%>
						<%-- 2.报案主信息中 --%>
						<%@include file="/pages/commonLiab/regist/LiabRegistMainMiddleEdit.jsp"%>
						<%-- 3.报案主信息结尾 --%>
						<%@include file="/pages/commonLiab/regist/LiabRegistMainTailEdit.jsp"%>
						<!--加入出险时，标的信息-->
						<%@include file="/pages/DAA/regist/DAARegistPolicyRiskEdit.jsp"%>

						<%--5.出险摘要 --%>
						<%@include file="/pages/common/regist/RegistTextEdit.jsp"%>
						<%-- 4.巨灾代码--%>
						<%@include file="/pages/common/regist/RegistKelpInfo.jsp"%>
						<%-- 6.报案修改人信息、95519报案补充信息--%>
						<c:if test="${editType=='PERFECT'||editType=='SHOW'}">
							<%@include file="/pages/common/regist/ModifyInfo.jsp"%>
							<%@include file="/pages/common/regist/95519AdditionalInfo.jsp"%>
						</c:if>
					</DIV>
				</CENTER>
			</mpc:page>
			<%-- 1.2.报案受损信息页面 --%>
			<mpc:page ID="tabMain" TABTITLE="<s:text name="check.damagePropertyInfo" />" TABTEXT="<s:text name="check.damagePropertyInfo" />">
				<DIV name="tabMain" class="tabMain">
					<%-- 财产损失部位信息 --%>
					<%@include file="/pages/commonLiab/regist/LiabRegistThirdPropEdit.jsp"%>
					<%-- 人伤跟踪信息 --%>
					<%@ include file="/pages/commonLiab/regist/LiabRegistPersonTraceEdit.jsp"%>
				</DIV>
			</mpc:page>
		</mpc:container>
		<TABLE id="btnCommon" class="common">
			<TR>
				<TD align="center">
					<c:if test="${param.editTypeOther!='SHOWTASK'}">
						<%-- 保存通用按钮 --%>
						<%@include file="/pages/commonLiab/regist/LiabRegistSave.jsp"%>
					</c:if>
				</TD>
			</TR>
		</TABLE>
	</form>
</DIV>
</body>
</html>