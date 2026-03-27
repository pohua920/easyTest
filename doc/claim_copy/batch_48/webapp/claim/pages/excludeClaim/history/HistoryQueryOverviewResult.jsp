<%@ page contentType="text/html; charset=GBK"%>
<%@page import="com.sinosoft.claim.dto.custom.TurnPageDto"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script src="/claim/excludeClaim/js/ExcludeClaimEdit.js"></script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" onload="">
	<form id="fm" name="fm" action="" method="post">
		<table class="common" cellpadding="0" cellspacing="0">
			<tr>
				<td class="formtitle" align="left"><s:text name="excludeClaim.ExceptTaskList" /></td>
				<%-- 除外历史任务列表 --%>
			</tr>
			<tr>
			</tr>
		</table>
		<TABLE cellpadding="3" cellspacing="0" class="common" id=ExcludeClaimResultTable>
			<THEAD>
				<TR class="formtitle">
					<TD width=5% class="formtitle"><s:text name="regist.prpLregist.serialNo" /></TD>
					<%-- 序号 --%>
					<TD width=15% class="formtitle"><s:text name="prpLregist.registNo" /></TD>
					<%-- 报案号 --%>
					<TD width=15% class="formtitle"><s:text name="db.prpLlawsuit.policyNo" /></TD>
					<%-- 保单号 --%>
					<TD width=10% class="formtitle"><s:text name="excludeClaim.ExceptNumber" /></TD>
					<%-- 除外人员工号 --%>
					<TD width=10% class="formtitle"><s:text name="excludeClaim.ExceptNames" /></TD>
					<%-- 除外人员名称 --%>
					<TD width=10% class="formtitle"><s:text name="excludeClaim.ExceptTime" /></TD>
					<%-- 除外时间--%>
					<TD width=35% class="formtitle"><s:text name="excludeClaim.ExceptReason" /></TD>
					<%-- 除外原因 --%>
				</TR>
			</thead>
			<tbody>
				<%
					int indexA = 0;
				%>
				<logic:notEmpty name="prplexcludeclaimDtoList">
					<logic:iterate id="prplexcludeclaimDto" name="prplexcludeclaimDtoList">
						<TR class=content>
							<TD align='center'><%=++indexA%></TD>
							<TD align='center'><bean:write name="prplexcludeclaimDto" property="registno" filter="true" /></TD>
							<TD align='center'><bean:write name="prplexcludeclaimDto" property="policyno" filter="true" /></TD>
							<TD align='center'><bean:write name="prplexcludeclaimDto" property="operatorcode" filter="true" /></TD>
							<TD align='center'><bean:write name="prplexcludeclaimDto" property="operatorname" filter="true" /></TD>
							<TD align='center'><bean:write name="prplexcludeclaimDto" property="inputdate" filter="true" /></TD>
							<TD align='center'><bean:write name="prplexcludeclaimDto" property="excludereason" filter="true" /></TD>
						</TR>
					</logic:iterate>
				</logic:notEmpty>
			</tbody>
			<tfoot>
				<!-- 翻页 -->
				<table width="100%" class="common" align="center" cellpadding="0" cellspacing="0">
					<tr>
						<bean:define id="pageview" name="turnPageDto" />
						<%
							TurnPageDto turnPageDto = (TurnPageDto) request.getAttribute("turnPageDto");
							int curPage = turnPageDto.getPageNo();
							String actionType = "historyQuery";//查詢的actionType
						%>
						<%@include file="/common/pub/TurnOverPage.jsp"%>
					</tr>
				</table>
			</tfoot>
		</TABLE>
	</form>
</body>
</html>