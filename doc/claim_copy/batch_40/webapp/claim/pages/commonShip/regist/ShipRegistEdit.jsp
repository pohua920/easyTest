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
		<title><s:text name="title.registBeforeEdit.editRegist" /></title>
		<%-- 标签页样式 --%>
		<jsp:include page="/behaviors/MpcStyle.jsp" />
		<%-- 页面样式  --%>
		<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
		<%@ include file="/common/meta_js.jsp"%>
		<script src="${ctx}/pages/commonShip/regist/js/ShipRegistEdit.js"></script>
		<script src="${ctx}/pages/common/regist/js/95519PerfectEdit.js"></script>
		<script language="Javascript" src="${ctx}/common/js/InputCode.js" ></script>
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
		<body onload="initPage();initSet();readonlyAllInput();oMPC.style.visibility='visible';">
	</s:if>
	<s:elseif test="#attr.editType=='SHOW'||#attr.editType=='DELETE'">
		<body onload="initPage();initSet();readonlyAllInput();disabledAllButton('buttonArea');oMPC.style.visibility='visible';">
	</s:elseif>
	<s:else>
		<body onload="initPage();initSet();oMPC.style.visibility='visible';">
	</s:else>
	<DIV id="mainLayer" class="mainLayer">
		<form name=fm action="${ctx}/registSave.do" method="post" onsubmit="return validateForm(this,'Driver','');">
		<c:if test="${editType == 'ADD' || editType == 'EDIT' || editType == 'PERFECT'}">
			<s:token></s:token>
		</c:if>
		<TABLE id="btnTable" cellpadding="0" cellspacing="0" border="0" style="height: 22px">
				<TR>
					<TD align="left">
						<c:if test="${param.editTypeOther!='SHOWTASK'}">
							<input type=hidden name=nodeType value="regist"/>
							<c:if test="${editType!='ADD'}">
								<td>
									<input type="button" name="messageSave" class="bigbutton" value="<s:text name='button.claimsProcessingRecords.value' />"
										onclick="openWinSave('${prpLregist.registNo}','${prpLregist.policyNo}','${prpLregist.riskCode}','regis','')">
								</td><%--赔案处理记录--%>
							</c:if>
							<c:if test="${editType=='ADD'}">
								<td>
									<input type="button" name="messageSave" disabled class="bigbutton" value="<s:text name='button.claimsProcessingRecords.value' />"
										onclick="openWinSave('${prpLregist.registNo}','${prpLregist.policyNo}','${prpLregist.riskCode}','regis','')">
								</td>  <%--赔案处理记录--%>
							</c:if>
						</c:if>
					</TD>
				</TR>
			</TABLE>
			<mpc:container ID="oMPC">
				<mpc:page ID="tabMain" TABTITLE="<s:text name="menu.regist.edit"/>" TABTEXT="<s:text name="menu.regist.edit"/>">
					<CENTER>
						<DIV name="tabMain" class="tabMain">
							<input type="hidden" name="checkFlag" value="${checkFlag }">
						<%-- 1.报案主信息头 --%>
						<%@include file="/pages/commonShip/regist/ShipRegistMainHeadEdit.jsp"%>
						<%-- 2.报案主信息中 --%>
						<%@include file="/pages/commonShip/regist/ShipRegistMainMiddleEdit.jsp"%>
						<%-- 3.报案主信息结尾 --%>
						<%@include file="/pages/commonShip/regist/ShipRegistMainTailEdit.jsp"%>
						<%@include file="/pages/DAA/regist/DAARegistPolicyRiskEdit.jsp"%>
						<%--5.出险摘要 --%>
						<%@include file="/pages/common/regist/RegistTextEdit.jsp"%>
						<%-- 4.巨灾代码--%>
						<%@include file="/pages/common/regist/RegistKelpInfo.jsp"%>
						<%-- 6.报案修改人信息、95519报案补充信息--%>
						<%--<c:if test="${editType=='PERFECT'||editType=='SHOW'}">
						<%@include file="/pages/common/regist/ModifyInfo.jsp"%>
						<%@include file="/pages/common/regist/95519AdditionalInfo.jsp"%>
						</c:if>--%>
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
			<TABLE id="btnCommon" class="common" >
				<TR>
					<TD align="center">
						<c:if test="${param.editTypeOther!='SHOWTASK'}">
							<%-- 保存通用按钮 --%>
							<%@include file="/pages/commonShip/regist/ShipRegistSave.jsp"%>
						</c:if>
					</TD>
				</TR>
			</TABLE>
		</form>
	</DIV>
	</body>
</html>