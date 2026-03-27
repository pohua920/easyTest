
<%--
****************************************************************************
* DESC       ：定损登记录入/修改页面
* AUTHOR     ：中科软
* CREATEDATE ：2013-03-13
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="ins.framework.common.*"%>
<%@ page import="com.sinosoft.sysframework.reference.AppConfig"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns:mpc>
<head>
<!--对title处理-->
<title><s:text name="title.certainLossBeforeEdit.editCertainLoss" /></title>
<!--定损登记-->
<app:css />
<app:claimCodeInput />
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<%-- 标签页样式 --%>
<jsp:include page="/behaviors/MpcStyle.jsp" />
<%@ include file="/common/meta_js.jsp"%>
<script src="/claim/pages/DAA/certainLoss/js/DAACertainLossEdit.js"></script>
<script src="/claim/pages/DAA/certainLoss/js/DAACertainLossPropEdit.js"></script>
<script type="text/javascript">
	//mpc调整
	$(function() {
		initWindowNoBtn();
		$(window).resize(function() {
			initWindowNoBtn();
		});
	})
</script>
</head>
<c:set var="oldCertainLossLastAccessedTime" value="" scope="session"/>
<c:choose>
	<c:when test="${param.editType =='SHOW'}">
		<body onload="initPage();initSet();readonlyAllInput();disabledAllButton('buttonArea');oMPC.style.visibility='visible';">
	</c:when>
	<c:otherwise>
		<body onload="initPage();initSet();oMPC.style.visibility='visible';">
	</c:otherwise>
</c:choose>
<DIV id="mainLayer" class="mainLayerNoBtn">
	<form name="fm" action="${ctx}/certainLoss/certainLossSave.do" method="post" onsubmit="return validateForm(this);">
		<input type="hidden" name="nodeType" value="${param.nodeType}">
		<input type="hidden" name="editType" value="${editType}">
		<c:if test="${param.editType == 'ADD' || param.editType == 'EDIT'}">
			<s:token />
		</c:if>
		<input type="hidden" name="riskcode" value="${prpLregist.riskCode}">
		<input type="hidden" name="policyno" value="${prpLregist.policyNo}">
		<input type="hidden" name="prpLcertainLossPayFee" value="${requestScope.payFee}" />
		<mpc:container ID="oMPC">
			<mpc:page ID="tabMain" TABTITLE="<s:text name="regist.prpLregist.registMain" />" TABTEXT="<s:text name="regist.prpLregist.registMain" />">
				<%--基本信息--%>
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<table width="100%" class=common cellpadding="5" cellspacing="1">
							<tr>
								<td class="formtitle">
									<s:text name="certainLoss.lossOfRegistration" />
								</td>
								<!--定损登记-->
							</tr>
						</table>
						<%-- 定损主表的画面 --%>
						<%@include file="/pages/DAA/certainLoss/DAACertainLossMainEdit.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
			<mpc:page ID="tabMain" TABTITLE="<s:text name="quickCase.propertyDamageList" />" TABTEXT="<s:text name="quickCase.propertyDamageList" />">
				<%--财产损失清单--%>
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 1.财产损失清单页面 --%>
						<%@include file="/pages/DAA/certainLoss/DAACertainLossPropEdit.jsp"%>
						<input type="hidden" name="lossTypeFlag" value="<c:out value='${param.lossTypeFlag}'/>">
					</DIV>
				</CENTER>
			</mpc:page>
		</mpc:container>
		<TABLE id="btnCommon" class="common">
			<TR>
				<TD align="center">
					<%-- 保存通用按钮 --%>
					<%@include file="/pages/DAA/certainLoss/DAACertainLossSave.jsp"%>
				</TD>
			</TR>
		</TABLE>
	</form>
</DIV>
</body>
</html>
