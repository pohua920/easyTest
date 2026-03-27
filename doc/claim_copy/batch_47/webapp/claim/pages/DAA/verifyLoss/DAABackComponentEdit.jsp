<%--
****************************************************************************
* DESC       ：修复验车登记录入/修改页面
* AUTHOR     ：中科软
* CREATEDATE ：2005-04-21
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%@page import="com.sinosoft.claim.dto.domain.*"%>
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="java.util.*"%>
<%@ page import="com.sinosoft.sysframework.common.datatype.*"%>
<html xmlns:mpc>
<head>
<!--对title处理-->
<title><s:text name="title.verifyLossBeforeEdit.complexCanRegist" /></title>
<%--复勘登记 --%>
<app:css />
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<%-- 标签页样式 --%>
<jsp:include page="/behaviors/MpcStyle.jsp" />
<script src="/claim/DAA/verifyLoss/js/DAAVerifyLossEdit.js"></script>
<script src="/claim/DAA/verifyLoss/js/DAAVerifyLossRepairComponentEdit.js"></script>
<META http-equiv="Content-Type" content="text/html;	charset=GB2312">
</head>
<%
	String editType = request.getParameter("editType");
	String nodeType = request.getParameter("nodeType");

	if (editType.equals("SHOW")) {
%>
<body class="interface" onload="initPage();initSet();readonlyAllInput();disabledAllButton('buttonArea');oMPC.style.visibility='visible'">
	<%
		} else {
	%>

<body class="interface" onload="initPage();initSet();initReadonly();oMPC.style.visibility='visible'">
	<%
		}
	%>
	<form name="fm" action="/claim/verifyLossSave.do" method="post" onsubmit="return validateForm(this);">
		<input type="hidden" name="org.apache.struts.taglib.html.TOKEN" value="<%=session.getAttribute("org.apache.struts.action.TOKEN")%>">
		<input type="hidden" name="nodeType" value="<%=nodeType%>">
		<DIV id="buttonLayer" style="position: absolute; top: 560px; left: 10px; width: 100%; z-index: 1;" align="center">
			<table cellpadding="0" cellspacing="0" align="center">
				<tr>
					<td>
						<%-- 保存通用按钮 --%>
						<%@include file="/pages/DAA/verifyLoss/DAABackComponentSave.jsp"%>
					</td>
				</tr>
			</table>
		</DIV>
		<div style="position: absolute; top: 2px; right: 0px; z-index: 1;">
			<table cellpadding="0" cellspacing="0" border="0">
				<tr>
					<td>
						<input type="button" class=bigbutton name="message" value="<s:text name='button.claimsProcessingRecords.value' />"
							onclick="openWinSave(fm.prpLverifyLossRegistNo.value,fm.prpLverifyLossPolicyNo.value,fm.prpLverifyLossRiskCode.value,'verif',fm.prpLverifyLossClaimNo.value);">
					</td>
					<%--赔案处理记录 --%>
					<!-- 
		      <td><input type="button" class=button name="messageView" value="查看留言" onclick="openWinQuery('registNo',fm.RegistNo.value);"></td>
		       -->
					<td>
						<input type="button" class=button name="eCertify" value="<s:text name='button.electronicDocuments.value' />"
							onClick="openCertify('certifyFinishQueryList','prpLcertifyCertifyNo','<bean:write name='prpLcheckDtoTemp' property='registNo'/>','verif')">
					</td>
					<%--电子单证 --%>
					<td>
						<input type="button" class=button name="buttonCertifyDirect" value="<s:text name='button.stateClaim.value' />"
							onClick="doCertifyDirect('<bean:write name='prpLcheckDtoTemp' property='registNo'/>','verif')">
					</td>
					<%--索赔清单 --%>
				</tr>
			</table>
		</div>
		<%!int indexVerifyLoss = 0;
			int repairFeeNo = 0;
			int componentNo = 0;%>
		<DIV id="mainLayer" style="position: absolute; top: 30px; left: 2px; height: 520px; z-index: 1;">
			<mpc:container ID="oMPC" style="width:830px;height:520px;">
				<mpc:page ID="tabMain" TABTITLE="基本訊息" TABTEXT="基本訊息">
					<CENTER>
						<DIV style="width: 100%; height: 515px; background-color: #F7F7F7; overflow: scroll;">
							<%-- 核损主表的画面 --%>
							<%@include file="/pages/DAA/verifyLoss/DAAVerifyLossMainEdit.jsp"%>
							<%-- 4.报案信息补充说明 --%>
							<%@include file="/pages/DAA/regist/DAARegistExtEdit.jsp"%>
						</DIV>
					</CENTER>
				</mpc:page>
				<mpc:page ID="tabMain" TABTITLE="車輛訊息" TABTEXT="車輛訊息">
					<CENTER>
						<DIV style="width: 100%; height: 515px; background-color: #F7F7F7; overflow: scroll;">
							<%-- 1.核损环节过程的修理/换件清单页面 --%>
							<%@include file="/pages/DAA/verifyLoss/DAAVerifyLossRepairComponentEdit.jsp"%>
							<%-- 3. 核价、核损意见、备注 --%>
							<%@include file="/pages/DAA/certainLoss/DAACertainLossOpinion.jsp"%>
							<%-- 4. 定核损信息补充说明 --%>
							<%@include file="/pages/DAA/certainLoss/DAACertainLossExtEdit.jsp"%>
						</DIV>
					</CENTER>
				</mpc:page>
			</mpc:container>
		</DIV>
	</form>
</body>
<!--这个函数是调动所能用到的通用js的过程，一般包括最常用的js的函数声明都在meta_js.jsp中-->
<%@include file="/common/meta_js.jsp"%>
</html>
