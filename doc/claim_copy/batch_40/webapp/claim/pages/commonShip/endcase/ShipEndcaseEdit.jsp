<!--
****************************************************************************
* DESC       ：结案登记录入/修改页面
* AUTHOR     ：中科软
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
-->
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true">
<head>
<!--对title处理-->
<title><s:text name="title.endcaseBeforeEdit.editEndcase" /></title>
<%--结案登记--%>
<!-- 页面样式  -->
<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
<script src="${ctx}/pages/commonShip/endcase/js/ShipEndcaseEdit.js"></script>
<%@ include file="/common/meta_js.jsp"%>
<jsp:include page="/behaviors/MpcStyle.jsp" />
<script type="text/javascript">
	//mpc调整
    $(function(){
	    initWindow();
	    $(window).resize(function(){
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
	<form name=fm action="${ctx}/endcase/endcaseSave.do?step=step1" method="post" onsubmit="return validateForm(this);">
		<c:if test="${editType == 'ADD' || editType == 'EDIT' }">
			<s:token></s:token>
		</c:if>
		<TABLE id="btnTable" cellpadding="0" cellspacing="0" border="0" style="height: 22px">
			<TR>
				<td>
					<c:if test="${param.editTypeOther!='SHOWTASK'}">
						<%--撰写留言--%>
						<input type="button" name="prpLmessageSave" value="<s:text name='button.composeMessage.value' />" class="bigbutton" onclick="openWinSave('${prpLclaim.registNo}','${prpLclaim.policyNo}','${prpLclaim.riskCode}','endca','${prpLclaim.claimNo}');">
						<%--查看留言--%>
						<input type="button" name="prpLmessageView" value="<s:text name='button.viewMessage.value' />" class="bigbutton" onClick="openWinSave('${prpLclaim.registNo}','${prpLclaim.policyNo}','${prpLclaim.riskCode}','endca','${prpLclaim.claimNo}');">
						<font color="#666666"> <s:text name="scheduleObject.note1" /> <%--注：--%>“<font color="#FF0000">*</font>”<s:text name="scheduleObject.note2" /> <%--为必选项--%>，“<img src="${ctx}/images/bgDoubleClick2.gif" width="13" height="13"
							align="absbottom">” <s:text name="scheduleObject.note3" /> <%--为双击选择项--%>
						</font>
					</c:if>
				</td>
			</TR>
		</TABLE>
		<mpc:container ID="oMPC" style="width:830px;height:520px;">
			<mpc:page ID="tabMain" TABTITLE="<s:text name='regist.prpLregist.registMain'/>" TABTEXT="<s:text name='regist.prpLregist.registMain'/>">
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<!-- 1.结案主信息 -->
						<%@include file="/pages/commonShip/endcase/ShipEndcaseMainEdit.jsp"%>
						<!-- 4.结案文本信息 -->
						<%@include file="/pages/DAA/endcase/DAAEndcaseTextEdit.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
		</mpc:container>
		<TABLE id="btnCommon" class="common">
			<TR>
				<TD align="center">
					<%-- 保存通用按钮 --%>
					<%@include file="/pages/DAA/endcase/DAAEndcaseSave.jsp"%>
				</TD>
			</TR>
		</TABLE>
	</form>
</DIV>
</body>
</html>
