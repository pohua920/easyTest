<%--
****************************************************************************
* DESC       ：报案登记录入/修改页面/非车险通用
* AUTHOR     ：中科软
* CREATEDATE ：2014-03-12
* MODIFYLIST ： Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta_js.jsp"%>
<html xmlns:mpc>
<head>
<%-- 标签页样式 --%>
<jsp:include page="/behaviors/MpcStyle.jsp" />
<!--对title处理-->
<title><s:text name="title.registBeforeEdit.editRegist" /></title>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<script src="${ctx }/pages/GAA/regist/js/GAARegistEdit.js"></script>
<script src="${ctx }/pages/common/regist/js/95519PerfectEdit.js"></script>
<script src="${ctx}/pages/DAA/compensate/js/autoHospital.js"></script>
<script language="Javascript" src="${ctx }/common/js/InputCode.js"></script>
<script type="text/javascript">
	javascript: window.history.forward(1);
	function termTypeChangge() {//当选择团单免导时触发是否放开被保险人输入域
		var termFlag = document.getElementsByName("termFlag");
		if (termFlag.length > 0 && termFlag[0].checked == true) {
			document.getElementById("prpLregistInsuredNameSpan").innerHTML = "<input type=text name=\"prpLregistInsuredName\" title=\"被保险人名称\" style=\"width:40%\" class=\"input\"	value=\"\">";
		} else {
			document.getElementById("prpLregistInsuredNameSpan").innerHTML = "<input type=text name=\"prpLregistInsuredName\" title=\"被保险人名称\" style=\"width:40%\" class=\"codecode\"	value=\"\" ondblclick=\"getCinsured(this)\" onkeyup=\"getCinsured(this)\" onchange=\"getCinsured(this)\">";
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
</head>
<c:set var="oldRegistLastAccessedTime" value="" scope="session"></c:set>
<c:set var="flag" value="Prop" scope="page"></c:set>
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
						<input type="button" <c:if test="${editType=='ADD'}">disabled</c:if> name="messageSave" class="bigbutton" value="<s:text name='button.claimsProcessingRecords.value'/>"
							onclick="openWinSave('${prpLregist.registNo}','${prpLregist.policyNo}','${prpLregist.riskCode}','regis','')"><%--赔案处理记录  --%>
					</c:if>
				</TD>
			</TR>
		</TABLE>
		<mpc:container ID="oMPC">
			<c:choose>
				<c:when test="${prpLregist.cancelDate!=null&&prpLregist.cancelDate!=''}">
					<mpc:page ID="tabMain" TABTITLE="<s:text name="menu.regist.edit"/>" TABTEXT="<s:text name="menu.regist.edit"/><s:text name="claim.cancelled"/>">
						<%-- 已注销 --%>
				</c:when>
				<c:otherwise>
					<mpc:page ID="tabMain" TABTITLE="<s:text name="menu.regist.edit"/>" TABTEXT="<s:text name="menu.regist.edit"/>">
				</c:otherwise>
			</c:choose>
			<CENTER>
				<DIV name="tabMain" class="tabMain">
					<%-- 1.报案主信息头 --%>
					<%@include file="/pages/GAA/regist/GAARegistMainHeadEdit.jsp"%>
					<%-- 2.报案主信息中 --%>
					<%@include file="/pages/GAA/regist/GAARegistMainMiddleEdit.jsp"%>
					<%-- 3.报案主信息结尾 --%>
					<%@include file="/pages/GAA/regist/GAARegistMainTailEdit.jsp"%>
					<%@include file="/pages/DAA/regist/DAARegistPolicyRiskEdit.jsp"%>
					<%--5.出险摘要 --%>
					<%@include file="/pages/common/regist/RegistTextEdit.jsp"%>
					<%-- 4.巨灾代码--%>
					<%@include file="/pages/common/regist/RegistKelpInfo.jsp"%>
					<%-- 5.报案修改人信息、95519报案补充信息--%>
					<c:if test="${editType=='SHOW'||editType=='PERFECT'}">
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
					<%@include file="/pages/GAA/regist/GAARegistThirdPropEdit.jsp"%>
					<%@include file="/pages/GAA/regist/RegistPersonTraceEdit.jsp"%>
				</DIV>
			</mpc:page>
		</mpc:container>
		<TABLE id="btnCommon" class="common">
			<TR>
				<TD align="center">
					<%-- 6.保存通用按钮 --%>
					<c:if test="${param.editTypeOther!='SHOWTASK'}">
						<%@include file="/pages/GAA/regist/GAARegistSave.jsp"%>
					</c:if>
				</TD>
			</TR>
		</TABLE>
		<%-- 关联抄单打印按钮 --%>
		<%-- @include file="/common/print/RegistPrintButton.jsp" --%>
	</form>
</DIV>
</body>
</html>