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
<%--结案登记--%>
<!-- 页面样式  -->
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<!-- 标签页样式 -->
<jsp:include page="/behaviors/MpcStyle.jsp" />
<script src="${ctx }/pages/commonAcci/endcase/js/AcciEndcaseEdit.js"></script>
<%@ include file="/common/meta_js.jsp"%>
<script type="text/javascript">
function showNotBackCount() {
    var NotBackCount = document.getElementsByName("prpNotBackCount");
    if (NotBackCount.length > 0 && NotBackCount[0].value != "0") {
        alert("该案件还有" + NotBackCount[0].value + "份尚未收回的担保单证，请关注处理！");
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
	<body class="interface" onload="initPage();initSet();readonlyAllInput();hideButton('buttonLayer');oMPC.style.visibility='visible';">
</s:if>
<s:elseif test="#parameters.editType[0]=='SHOW'">
	<body class="interface" onload="initPage();initSet();readonlyAllInput();disabledAllButton('buttonArea');oMPC.style.visibility='visible';">
</s:elseif>
<s:else>
	<body class="interface" onload="initPage();initSet();oMPC.style.visibility='visible';">
</s:else>
<DIV id="mainLayer" class="mainLayer">
	<form name=fm action="${ctx }/endcase/endcaseSave.do?step=step1" method="post" onsubmit="return validateForm(this);">
		<c:if test="${editType == 'ADD' || editType == 'EDIT' }">
			<s:token></s:token>
		</c:if>
		<table id="btnTable" border="0" cellpadding="0" cellspacing="0" style="height: 22px">
			<tr>
				<td>
					<c:if test="${param.editTypeOther!='SHOWTASK'}">
						<input type="button" class="bigbutton" name="prpLmessageSave" value="<s:text name='button.claimsProcessingRecords.value' />" onclick="openWinSave1();return false;">
					</c:if>
				</td>
			</tr>
		</table>
		<mpc:container ID="oMPC">
			<mpc:page ID="tabMain" TABTITLE="基本訊息" TABTEXT="基本訊息">
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<!-- 1.结案主信息 -->
						<%@include file="/pages/commonAcci/endcase/AcciEndcaseMainEdit.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
			<mpc:page ID="tabMain" TABTITLE="結算報告" TABTEXT="結算報告">
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<!-- 4.结案文本信息 -->
						<%@include file="/pages/DAA/endcase/DAAEndcaseTextEdit.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
		</mpc:container>
		<TABLE id="btnCommon" class="common">
			<TR>
				<TD align="center">
					<c:if test="${param.editTypeOther!='SHOWTASK'}">
						<!-- 保存通用按钮 -->
						<%@include file="/pages/DAA/endcase/DAAEndcaseSave.jsp"%>
					</c:if>
				</TD>
			</TR>
		</TABLE>
	</form>
</DIV>
</body>
</html>
