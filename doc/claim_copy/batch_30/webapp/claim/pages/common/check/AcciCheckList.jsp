<%--
****************************************************************************
* DESC       ：调查信息列表
* AUTHOR     ：理赔组
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%@ page import="com.sinosoft.claim.dto.domain.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.sinosoft.claim.util.*"%>
<html:html locale="true">
<head>
<app:css />
<STYLE>
BODY {
	SCROLLBAR-FACE-COLOR: #EFFAFF;
	SCROLLBAR-HIGHLIGHT-COLOR: #4D9AC4;
	SCROLLBAR-SHADOW-COLOR: #4D9AC4;
	SCROLLBAR-3DLIGHT-COLOR: #EFFAFF;
	SCROLLBAR-ARROW-COLOR: #EFFAFF;
	SCROLLBAR-TRACK-COLOR: #EFFAFF;
	SCROLLBAR-DARKSHADOW-COLOR: #EFFAFF;
}
</STYLE>
<title><s:text name="title.checkBeforeEdit.reportPrintInfo" /></title>
<%--调查报告打印信息列表--%>
<script src="/claim/common/js/showpage.js">
	
</script>
<html:base />
</head>
<body class="interface" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<form name="fm">
		<input type="hidden" name="pageFlag">
		<table border="0" align="center" cellpadding="0" cellspacing="0" class="common">
			<tr>
				<td width="184" height="26" valign="bottom">
					<table width="184" height="19" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="161" class="formtitle">
								<s:text name="check.reportPrintInfo" />
							</td>
							<%--调查报告打印信息列表--%>
						</tr>
					</table>
				</td>
				<td valign="bottom">
					<font color="#666666">&nbsp;
				</td>
			</tr>
		</table>
		<table bgcolor="#2D8EE1" class="common" cellpadding="4" cellspacing="1">
			<tr>
				<td class="title" width="30%">
					<s:text name="db.prpLacciCheck.checkNo" />:
				</td>
				<%--调查号--%>
				<td class="input">
					<%
						String registNo = "";

							Collection prpLacciCheckDtoList = (Collection) request.getAttribute("prpLacciCheckDtoList");
							Iterator registListTemp = prpLacciCheckDtoList.iterator();
							while (registListTemp.hasNext()) {
								PrpLacciCheckDto prpLregistDtoTemp = (PrpLacciCheckDto) registListTemp.next();
								registNo = prpLregistDtoTemp.getCheckNo();
					%>
					<a href="/claim/ClaimPrint.do?printType=AcciCheck&CompensateNo=<%=registNo%>"><%=registNo%><br></a>
					<%
						}
					%>
				</td>
			</tr>
		</table>
	</form>
</body>
</html:html>