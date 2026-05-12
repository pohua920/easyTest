<%--
****************************************************************************
* DESC       ：结案登记录入/修改页面
* AUTHOR     ：理赔组
* CREATEDATE ：2013-02-03
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%--
****************************************************************************
* DESC       ：结案登记录入/修改页面
* AUTHOR     ：中科软
* CREATEDATE ：2013-02-03
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns:mpc>
<head>
<!--对title处理-->
<title><s:text name="title.endcaseBeforeEdit.editEndcase" /></title>
<!--结案登记-->
<%@ include file="/common/meta_js.jsp"%>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
<script src="${ctx}/pages/DAA/endcase/js/DAAEndcaseEdit.js"></script>
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
</head>
<c:if test="${editType == 'SHOW'}">
	<body class="interface" onload="initPage();initSet();readonlyAllInput();oMPC.style.visibility='visible';">
</c:if>
<c:if test="${editType != 'SHOW'}">
	<body class="interface" onload="initPage();initSet();oMPC.style.visibility='visible';">
</c:if>
<DIV id="mainLayer" class="mainLayer">
	<form name=fm action="/claim/endcase/endcaseSave.do?step=step1" method="post" onsubmit="return validateForm(this);">
		<input type="hidden" name="nodeType" value="endca">
		<input type="hidden" name="editType" value="${editType}">
		<%--
			/** 工作流参数 2013-03-14 chenjie */
		--%>
		<c:if test="${editType == 'ADD' || editType == 'EDIT' }">
			<s:token></s:token>
		</c:if>
		<input type="hidden" name="swfLogFlowID" value="<c:out value='${param.swfLogFlowID}'/>">
		<input type="hidden" name="swfLogLogNo" value="<c:out value='${param.swfLogLogNo}'/>">
		<input type="hidden" name="swfLogActorId" value="<c:out value='${param.actorId}'/>">
		<input type="hidden" name="swfLogProcessId" value="<c:out value='${param.processId}'/>">
		<TABLE id="btnTable" cellpadding="0" cellspacing="0" border="0">
			<TR>
				<td align="left">
					<!--赔案处理记录-->
					<input type="button" name="prpLmessageSave" class="bigbutton" style="width: 120px" value="<s:text name='button.claimsProcessingRecords.value'/>"
						onclick="openWinSave(fm.prpLclaimRegistNo.value,fm.prpLclaimPolicyNo.value,fm.prpLclaimRegistNo.value,'endca',fm.prpLendcaseClaimNo1.value);">
				</td>
				<%--索赔材料交接单--%>
			</tr>
		</table>
		<mpc:container ID="oMPC">
			<mpc:page ID="tabMain" TABTITLE="<s:text name="regist.prpLregist.registMain" />" TABTEXT="<s:text name="regist.prpLregist.registMain" />">
				<%--基本信息--%>
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 1.结案主信息 --%>
						<%@include file="/pages/DAA/endcase/DAAEndcaseMainEdit.jsp"%>
						<%-- 4.结案文本信息 --%>
						<%@include file="/pages/DAA/endcase/DAAEndcaseTextEdit.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
			<mpc:page ID="tabMain" TABTITLE="<s:text name="endcase.calculationsList" />" TABTEXT="<s:text name="endcase.calculationsList" />">
				<%-- 赔案号对应赔款计算书列表--%>
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 赔案号对应赔款计算书列表 --%>
						<%@include file="/pages/common/compensate/CompensateMainBeforeEdit.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
			<mpc:page ID="tabMain" TABTITLE="<s:text name="endcase.printButton" />" TABTEXT="<s:text name="endcase.printButton" />">
				<%--打印按钮 --%>
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 打印按钮 --%>
						<%@include file="/pages/DAA/endcase/DAAEndcasePrint.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
		</mpc:container>
		<TABLE id="btnCommon" class="common">
			<TR>
				<TD align="center">
					<CENTER><%@include file="/pages/DAA/endcase/DAAEndcaseSave.jsp"%></CENTER>
				</td>
			</tr>
		</table>
	</form>
</DIV>
</body>
</html>
