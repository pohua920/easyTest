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
		<!--对title处理-->
		<title><s:text name="title.registBeforeEdit.editRegist" /></title>
		<%@ include file="/common/meta_js.jsp"%>
		<%-- 页面样式  --%>
		<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
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
		<script src="${ctx }/pages/commonCargo/regist/js/CargoRegistEdit.js"></script>
		<script src="${ctx }/pages/common/regist/js/95519PerfectEdit.js"></script>
		<script language="Javascript" src="${ctx}/common/js/InputCode.js" ></script>
	</head>
	<c:set var="flag" value="Cargo" />
	<c:set var="oldRegistLastAccessedTime" value="" />
	<s:if test="#attr.editTypeOther=='SHOWTASK'">
		<body onload="initPage();initSet();readonlyAllInput();oMPC.style.visibility='visible';">
	</s:if>
	<s:elseif test="#attr.editType=='SHOW'||#attr.editType=='DELETE'">
		<body onload="initPage();initSet();readonlyAllInput();disabledAllButton('buttonArea');oMPC.style.visibility='visible';">
	</s:elseif>
	<s:else>
		<body onload="initPage();initSet();oMPC.style.visibility='visible';">
	</s:else>
	<DIV id="mainLayer" class="mainLayer">
		<form name=fm action="${ctx }/registSave.do" method="post" onsubmit="return validateForm(this,'Driver','');">
			<mpc:container ID="oMPC">
				<%-- 报案基本信息页面 --%>
				<mpc:page ID="tabMain">
					<CENTER>
						<DIV name="tabMain" class="tabMain">
							<input type="hidden" name="checkFlag" value="${checkFlag }">
							<%-- 1.报案主信息头 --%>
							<%@include file="/pages/commonCargo/regist/CargoRegistMainHeadEdit.jsp"%>
							<%-- 2.报案主信息中 --%>
							<%@include file="/pages/commonCargo/regist/CargoRegistMainMiddleEdit.jsp"%>
							<%-- 3.报案主信息结尾 --%>
							<%@include file="/pages/commonCargo/regist/CargoRegistMainTailEdit.jsp"%>
							<!-- 加入出险时，标的信息-->
							<%@include file="/pages/DAA/regist/DAARegistPolicyRiskEdit.jsp"%>
							<%--5.出险摘要 --%>
							<%@include file="/pages/common/regist/RegistTextEdit.jsp"%>
							<%--  move 4.巨灾代码  --%>
							<%-- 4.巨灾代码--%>
							<%@include file="/pages/common/regist/RegistKelpInfo.jsp"%>
							<%-- 5.报案修改人信息、95519报案补充信息--%>
						</DIV>
					</CENTER>
				</mpc:page>
			</mpc:container>
			<TABLE id="btnCommon" class="common">
				<TR>
					<TD align="center">
						<c:if test="${editType=='editType'||editType=='SHOW'}">
						<%@include file="/pages/common/regist/ModifyInfo.jsp"%>
						<%@include file="/pages/common/regist/95519AdditionalInfo.jsp"%>
						</c:if>
						<%-- 保存通用按钮 --%>
						<c:if test="${editTypeOther!='SHOWTASK'}">
						<%@include file="/pages/commonCargo/regist/CargoRegistSave.jsp"%>
						</c:if>
						<%-- 关联抄单打印按钮 --%>
						<%--@include file="/common/print/RegistPrintButton.jsp"--%>
					</TD>
				</TR>
			</TABLE>
		</form>
	</DIV>
	</body>
</html>