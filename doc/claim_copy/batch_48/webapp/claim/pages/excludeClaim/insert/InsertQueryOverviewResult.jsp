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
				<td class="formtitle" align="left"><s:text name="excludeClaim.ExceptList" /></td>
				<%-- 可设置除外案件列表 --%>
			</tr>
			<tr>
			</tr>
		</table>
		<TABLE cellpadding="3" cellspacing="2" class="common" id=ExcludeClaimResultTable>
			<THEAD>
				<TR class="formtitle">
					<TD width=8% class="formtitle"><s:text name="regist.prpLregist.serialNo" /></TD>
					<%-- 序号 --%>
					<TD width=20% class="formtitle"><s:text name="prpLregist.registNo" /></TD>
					<%-- 报案号 --%>
					<TD width=20% class="formtitle"><s:text name="db.prpLlawsuit.policyNo" /></TD>
					<%-- 保单号 --%>
					<TD width=20% class="formtitle"><s:text name="db.prpLclaim.insuredName" /></TD>
					<%-- 被保险人 --%>
					<TD width=20% class="formtitle"><s:text name="prpLregist.reportHour" /></TD>
					<%-- 报案时间 --%>
					<TD width=12% class="formtitle"><s:text name="compensate.insuranceComCode" /></TD>
					<%-- 承保机构代码 --%>
				</TR>
			</thead>
			<tbody>
				<%
					int indexA = 0;
				%>
				<logic:notEmpty name="prpLregistDtoList">
					<logic:iterate id="prpLregistDto" name="prpLregistDtoList">
						<TR class=content>
							<TD align='center' style="COLOR: #000000; BACKGROUND-COLOR: #F7F7F7;"><%=++indexA%></TD>
							<TD align='center' style="COLOR: #000000; BACKGROUND-COLOR: #F7F7F7;"><a href="javascript:void(0)" onclick="prepareInsert('<bean:write name="prpLregistDto" property="registNo" filter="true"/>')"> <bean:write name="prpLregistDto" property="registNo" filter="true" />
							</a></TD>
							<TD align='center' style="COLOR: #000000; BACKGROUND-COLOR: #F7F7F7;"><bean:write name="prpLregistDto" property="policyNo" filter="true" /></TD>
							<TD align='center' style="COLOR: #000000; BACKGROUND-COLOR: #F7F7F7;"><bean:write name="prpLregistDto" property="insuredName" filter="true" /></TD>
							<TD align='center' style="COLOR: #000000; BACKGROUND-COLOR: #F7F7F7;"><bean:write name="prpLregistDto" property="reportDate" filter="true" /></TD>
							<TD align='center' style="COLOR: #000000; BACKGROUND-COLOR: #F7F7F7;"><bean:write name="prpLregistDto" property="comCode" filter="true" /></TD>
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
							String actionType = "insertQuery";//查詢的actionType
						%>
						<%@include file="/common/pub/TurnOverPage.jsp"%>
					</tr>
				</table>
			</tfoot>
		</TABLE>
	</form>
</body>
</html>