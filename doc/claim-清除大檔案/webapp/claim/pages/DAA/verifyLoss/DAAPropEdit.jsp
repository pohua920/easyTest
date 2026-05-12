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
		<title><s:text name="title.verifyLossBeforeEdit.editVerifyLoss" />
		</title>
		<%--核损登记 --%>
		<%@ include file="/common/meta_js.jsp"%>
		<%@include file="/common/i18njs.jsp"%>
		<%-- 标签页样式 --%>
		<jsp:include page="/behaviors/MpcStyle.jsp" />
		<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
		<script src="${ctx }/pages/DAA/verifyLoss/js/DAAVerifyLossPropEdit.js"></script>
		<META http-equiv="Content-Type" content="text/html;	charset=GBK">
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
	<c:set var="editType" value="${param.editType}" scope="page" />
	<c:set var="nodeType" value="${param.nodeType}" scope="page" />
	<c:choose>
		<c:when test="${editType=='SHOW'}">
			<body class="interface" onload="initPage();initSet();readonlyAllInput();disabledAllButton('buttonArea');">
		</c:when>
		<c:otherwise>
			<body class="interface" onload="initPage();initSet();">
		</c:otherwise>
	</c:choose>
	<DIV id="mainLayer" class="mainLayer" style="background-image: " >
		<form name="fm" action="${ctx }/verifyLoss/verifyLossSave.do" method="post" onsubmit="return validateForm(this);">
			<c:if test="${editType == 'ADD' || editType == 'EDIT' }">
				<s:token></s:token>
			</c:if>
			<input type="hidden" name="nodeType" value="${nodeType }">
			<input type="hidden" name="editType" value="${editType}">
			<input type="hidden" name="prpLverifyLossPayFee" value="${requestScope.payFee}" />
			<table id="btnTable" cellpadding="0" cellspacing="0" border="0">
				<tr>
					<td class=button>
						<input type="button" class=bigbutton name="message" value="<s:text name='button.claimsProcessingRecords.value' />" onclick="openWinSave('${prpLregist.registNo}',fm.policyno.value,fm.riskcode.value,'propc',fm.prpLverifyLossClaimNo.value)">
					</td>
					<%--赔案处理记录 --%>
					<!-- 
	      			 <td class=button ><input type="button" class=bigbutton name="messageView" value="查看留言" onclick="openWinQuery('registNo','<bean:write name='prpLregistDto' property='registNo' filter='true' />')"></td>
		  			-->
				</tr>
			</table>
			<mpc:container ID="oMPC">
				<mpc:page ID="tabMain" TABTITLE="<s:text name="regist.prpLregist.registMain" />" TABTEXT="<s:text name="regist.prpLregist.registMain" />">
					<%--基本信息--%>
					<CENTER>
						<DIV name="tabMain" class="tabMain">
							<%-- 核损主表的画面 --%>
							<%@include file="/pages/DAA/verifyLoss/DAAVerifyLossMainEdit.jsp"%>
						</DIV>
					</CENTER>
					</mpc:page>
					<mpc:page ID="tabMain" TABTITLE="<s:text name='quickCase.propertyDamageList' />" TABTEXT="<s:text name='quickCase.propertyDamageList' />">
						<CENTER>
							<DIV name="tabMain" class="tabMain">	
								<%-- 1.财产损失清单页面 --%>
								<%@include file="/pages/DAA/verifyLoss/DAAVerifyLossPropEdit.jsp"%>
								<%-- 当是人伤核损的时候显示回退的原因 --%>
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
							</DIV>
						</CENTER>
					</mpc:page>
				</mpc:container>
				<TABLE id="btnCommon" class="common">
					<TR>
						<td align="center">
							<%-- 保存通用按钮 --%>
							<%@include file="/pages/DAA/verifyLoss/DAAVerifyLossSave.jsp"%>
						</td>
					</TR>
				</TABLE>
		</form>
	</DIV>
	</body>
</html>
