<%--
****************************************************************************
* DESC       ：核损登记录入/修改页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-07-13
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true" xmlns:mpc>
	<head>
		<!--对title处理-->
		<title><s:text name="title.verifyLossBeforeEdit.editVerifyLoss" /></title>
		<%--核损登记 --%>
		<%@ include file="/common/meta_js.jsp"%>
		<%@include file="/common/i18njs.jsp"%>
		<%-- 页面样式  --%>
		<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
		<%-- 标签页样式 --%>
		<jsp:include page="/behaviors/MpcStyle.jsp" />
		<script src="${ctx }/pages/DAA/verifyLoss/js/DAAVerifyLossPersonEdit.js"></script>
		<script>
		//mpc调整
		$(function() {
			initWindow();
			$(window).resize(function() {
				initWindow();
			});
		})
  	  //设置伤情信息表的初始值
      function loadPrpLpersonWound() {
          var prpLpersonPersonNo = document.getElementsByName("prpLpersonPersonNo");
          var index = -1;
          var woundCodeCheck;
          <c:forEach items="${prpLpersonWound.woundList}" var="prpLpersonWoundTemp">
          	index = -1;
          	for(var i=0;i<prpLpersonPersonNo.length;i++){
              	if(prpLpersonPersonNo[i].value=="${prpLpersonWoundTemp.id.personNo}"){
              		index = i;
              		break;
                 }
             }
             if(index>-1){
             	woundCodeCheck = document.getElementsByName("woundCodeCheck${prpLpersonWoundTemp.woundCode}")[index];
             	woundCodeCheck.checked=true;
             	woundCodeCheck = document.getElementsByName("woundCodeCheck${prpLpersonWoundTemp.woundCode}Txt")[index];
             	woundCodeCheck.value="1";
             }
      	</c:forEach>
     	return true;
     }
  </script>
		<META http-equiv="Content-Type" content="text/html;	charset=GBK">
	</head>
	<c:set var="editType" value="${param.editType}" scope="page" />
	<c:set var="nodeType" value="${param.nodeType}" scope="page" />
	<c:choose>
		<c:when test="${editType=='SHOW'}">
			<body class="interface" onload="initPage();initSet();readonlyAllInput();disabledAllButton('buttonArea');loadPrpLpersonWound();">
		</c:when>
		<c:otherwise>
			<body class="interface" onload="initPage();initSet();loadPrpLpersonWound();">
		</c:otherwise>
	</c:choose>
	<DIV id="mainLayer" class="mainLayer">
		<form name="fm" action="${ctx }/verifyLoss/verifyLossSave.do" method="post" onsubmit="return validateForm(this);">
			<c:if test="${editType == 'ADD' || editType == 'EDIT' }">
				<s:token></s:token>
			</c:if>
			<input type="hidden" name="nodeType" value="${nodeType }">
			<input type="hidden" name="editType" value="${editType}">
			<input type="hidden" name="prpLverifyLossPayFee" value="${requestScope.payFee}" />
			<table id="btnTable" cellpadding="0" cellspacing="0" border="0">
				<tr>
					<td align="left">
						<input type="button" class="bigbutton" name="message" value="<s:text name='button.claimsProcessingRecords.value' />"
							onclick="openWinSave(fm.prpLverifyLossRegistNo.value,fm.prpLverifyLossPolicyNo.value,fm.prpLverifyLossRiskCode.value,'verif',fm.prpLverifyLossClaimNo.value);">
						<font color="#666666"> <s:text name="prompt.check.note" /> <%-- 注 --%>：“<font color="#FF0000">*</font>” <s:text name="prompt.check.fieldWill" /> <%-- 为必选项 --%>，
						“<img src="/claim/images/bgDoubleClick2.gif" width="13" height="13" align="absbottom">” <s:text name="prompt.check.doubleClick" /><%-- 为双击选择项 --%>。</font>
					</td>
					<%--
				      <td><input type="button" name="message" value="讨论留言" onclick="openWinSave()"></td><td><input type="button" name="messageView" value="查看留言" onclick="openWinQuery()"></td><td width="70%" align="right"><font color="#666666">　注：“<font color="#FF0000">*</font>”为必选项，“<img src="/claim/images/bgDoubleClick2.gif" width="13" height="13" align="absbottom">”
				      为双击选择项。</font>
				      </td>
				      --%>
				</tr>
			</table>
			<mpc:container ID="oMPC">
				<mpc:page ID="tabMain" TABTITLE="<s:text name="regist.prpLregist.registMain" />" TABTEXT="<s:text name="regist.prpLregist.registMain" />">
					<%--基本信息--%>
					<CENTER>
						<DIV name="tabMain" class="tabMain">
							<%-- 核损主表的画面 --%>
							<%@include file="/pages/DAA/verifyLoss/DAAVerifyLossMainEdit.jsp"%>
							<%-- 当是人伤核损的时候显示回退的原因 --%>
							<c:if test="${prpLverifyLoss.id.nodeType=='wound'}">
								<table border="0" align="center" cellpadding="4" cellspacing="1" bgcolor="#2D8EE1" class="title" style="width: 100%">
									<tr>
										<td colspan=2 class="formtitle">
											<s:text name="verifyLoss.editVerifyLoss" />
										</td>
									</tr>
									<%--核损登记 --%>
									<tr>
										<td class="input" style="width: 20%">
											<s:text name="certainLoss.rollbackCauses" />:
										</td>
										<%--回退的原因 --%>
										<td class="input" style="width: 80%">
											<input name="prpLverifyLossVeriwReturnReason" class="input" style="width: 640px" value="${prpLverifyLoss.veriwReturnReason }">
										</td>
									</tr>
								</table>
							</c:if>
						</DIV>
					</CENTER>
				</mpc:page>
				<mpc:page ID="tabMain" TABTITLE="<s:text name="certainLoss.inventory" />" TABTEXT="<s:text name="certainLoss.inventory" />">
					<CENTER>
						<DIV name="tabMain" class="tabMain">
							<%-- 1.人员伤亡清单页面 --%>
							<%@include file="/pages/DAA/verifyLoss/DAAVerifyLossPersonEdit.jsp"%>
						</DIV>
					</CENTER>
				</mpc:page>
			</mpc:container>
			<TABLE id="btnCommon" class="common">
				<TR>
					<TD align="center">
						<%-- 保存通用按钮 --%>
						<%@include file="/pages/DAA/verifyLoss/DAAVerifyLossSave.jsp"%>
					</td>
				</TR>
			</TABLE>
		</form>
	</DIV>
	</body>
</html>
