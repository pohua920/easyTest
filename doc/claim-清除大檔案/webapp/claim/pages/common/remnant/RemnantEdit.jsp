<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<html xmlns:mpc>
<head>
<title><s:text name='remnant.editPage' /></title>
<!-- 页面样式  -->
<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
<%@ include file="/common/meta_js.jsp"%>
<!-- 标签页样式 -->
<jsp:include page="/behaviors/MpcStyle.jsp" />
<!-- 时间控件 -->
<script src="${ctx}/pages/common/remnant/js/remnantEdit.js"></script>
<script src="${ctx}/pages/common/remnant/js/remnantLoss.js"></script>
<script src="${ctx}/pages/common/account/js/paymentAccount.js"></script>
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
<c:set var="oldRegistLastAccessedTime" value="" scope="session" />
<s:if test="#parameters.editType[0]=='show'||#parameters.editType[0]=='undwrt'">
	<body class="interface" onload="initPage();readonlyAllInput();initSet();oMPC.style.visibility='visible'">
</s:if>
<s:else>
	<body class="interface" onload="initPage();oMPC.style.visibility='visible'">
</s:else>
<DIV id="mainLayer" class="mainLayerNoBtn">
	<form name="fm" method="post" action="${ctx}/remnantSave.do">
		<mpc:container ID="oMPC">
			<mpc:page ID="tabMain" TABTITLE="<s:text name='remnant.base.info'/>" TABTEXT="<s:text name='remnant.base.info'/>">
				<%--残余物基本信息 --%>
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%--残余物主信息--%>
						<%@include file="/pages/common/remnant/RemnantMainHeadEdit.jsp"%>
						<!---审批处理意见 --->
                        <%@include file="/pages/common/remnant/RemnantOpinion.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
			<mpc:page ID="tabMain" TABTITLE="<s:text name='remnant.info'/>" TABTEXT="<s:text name='remnant.info'/>">
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 残余物信息 --%>
						<%@include file="/pages/common/remnant/RemnantLoss.jsp"%>
						<%@include file="/pages/common/remnant/RemnantBuyer.jsp"%>
						<%@include file="/pages/common/remnant/RemnantPayObjectInfo.jsp"%>
						<%@include file="/pages/common/remnant/RemnantCharge.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
			<mpc:page ID="tabMain" TABTITLE="<s:text name='remnant.info'/>" TABTEXT="理算報告">
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 理算報告 --%>
						<%@include file="/pages/common/remnant/RemnantTextEdit.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
		</mpc:container>
		<TABLE id="btnCommon" class="common">
			<TR>
				<TD align="center">
					<!--- 残余物保存 --->
					<%@include file="/pages/common/remnant/RemnantSave.jsp"%>
				</td>
			</tr>
		</TABLE>
	</form>
</DIV>
</body>
</html>
