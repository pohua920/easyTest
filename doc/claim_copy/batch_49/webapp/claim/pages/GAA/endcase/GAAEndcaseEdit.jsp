<!--
****************************************************************************
* DESC       ：结案登记录入/修改页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-06-28
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
-->
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns:mpc>
<head>
<!--对title处理-->
<title><s:text name="title.endcaseBeforeEdit.editEndcase" /></title>
<%-- 结案登记 --%>
<%@ include file="/common/meta_js.jsp"%>
<!-- 页面样式  -->
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<!-- 标签页样式 -->
<jsp:include page="/behaviors/MpcStyle.jsp" />
<script src="${ctx}/pages/GAA/endcase/js/GAAEndcaseEdit.js"></script>
<script type="text/javascript">
	function showNotBackCount() {
		var NotBackCount = document.getElementsByName("prpNotBackCount");
		if (NotBackCount.length > 0 && NotBackCount[0].value != "0") {
			alert("該案件還有" + NotBackCount[0].value + "份尚未收回的擔保單證，請關注處理！");
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
<s:if test="#parameters.editTypeOther=='SHOWTASK'">
	<body class="interface" onload="initPage();initSet();readonlyAllInput();hideButton('buttonLayer');oMPC.style.visibility='visible';" style="scroll: no; overflow: hidden;">
</s:if>
<s:elseif test="#parameters.editType[0]=='SHOW'">
	<body class="interface" onload="initPage();initSet();readonlyAllInput();disabledAllButton('buttonArea');oMPC.style.visibility='visible';" style="scroll: no; overflow: hidden;">
</s:elseif>
<s:else>
	<body class="interface" onload="initPage();initSet();oMPC.style.visibility='visible';" style="scroll: no; overflow: hidden;">
</s:else>
<DIV id="mainLayer" class="mainLayer">
	<form name=fm action="${ctx}/endcase/endcaseSave.do?step=step1" method="post" onsubmit="return validateForm(this);">
		<c:if test="${editType == 'ADD' || editType == 'EDIT' }">
			<s:token></s:token>
		</c:if>
		<table id="btnTable" cellpadding="0" cellspacing="0" border="0" style="height: 22px">
			<tr>
				<td>
					<c:if test="${param.editTypeOther!='SHOWTASK'}">
						<input type="button" name="prpLmessageSave" value="<s:text name='button.claimsProcessingRecords.value'/>" class="bigbutton" onclick="openWinSave1();return false;">
						<%-- 赔案处理记录 --%>
					</c:if>
				</td>
			</tr>
		</table>
		<mpc:container ID="oMPC">
			<mpc:page ID="tabMain" TABTITLE="<s:text name='regist.prpLregist.registMain'/>" TABTEXT="<s:text name='regist.prpLregist.registMain'/> "><%--基本讯息--%>
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<!-- 1.结案主信息 -->
						<%@include file="/pages/GAA/endcase/GAAEndcaseMainEdit.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
			<mpc:page ID="tabMain" TABTITLE="<s:text name='db.prpLltext.text2'/>" TABTEXT="<s:text name='db.prpLltext.text2'/>"><%--结案报告--%>
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<!-- 4.结案文本信息 -->
						<%@include file="/pages/GAA/endcase/GAAEndcaseTextEdit.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
		</mpc:container>
		<TABLE id="btnCommon" class="common">
			<TR>
				<TD align="center">
					<c:if test="${param.editTypeOther!='SHOWTASK'}">
						<!-- 保存通用按钮 -->
						<%@include file="/pages/GAA/endcase/GAAEndcaseSave.jsp"%>
					</c:if>
				</TD>
			</TR>
		</TABLE>
	</form>
</DIV>
</body>
</html>
