<%--
****************************************************************************
* DESC       ：95519报案完善查询结果页面
* AUTHOR     ：曹志刚
* CREATEDATE ：2009-7-31
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@page import="com.sinosoft.claim.dto.custom.TurnPageDto"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script src="/claim/common/regist/js/95519PerfectEdit.js"></script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" onload="">
	<form id="fm" name="fm" method="post">
		<TABLE cellpadding="3" cellspacing="1" class="common" id=RegistResultTable>
			<THEAD>
				<TR class="tableHead">
					<TD width=18% class="centertitle">
						<s:text name="db.prpLregist.registNo" />
						<%--报案号 --%>
					</TD>
					<TD width=18% class="centertitle">
						<s:text name="db.prpCprofit.policyNo" />
						<%--保单号 --%>
					</TD>
					<TD width=6% class="centertitle">
						<s:text name="db.prpDdbs.riskCode" />
						<%--险种 --%>
					</TD>
					<TD width=15% class="centertitle">
						<s:text name="db.prpLregist.insuredName" />
						<%--被保险人 --%>
					</TD>
					<TD width=10% class="centertitle">
						<s:text name="regist.prpLregist.registTime" />
						<%--报案时间 --%>
					</TD>
					<TD width=10% class="centertitle">
						<s:text name="regist.serviceNumber" />
						<%--服务单号 --%>
					</TD>
					<TD width=13% class="centertitle">
						<s:text name="regist.modifyState" />
						<%--修改状态 --%>
					</TD>
				</TR>
			</thead>
			<tbody>
				<logic:notEmpty name="prpLregistDtoList">
					<logic:iterate id="prpLregistDto" name="prpLregistDtoList">
						<TR class=listodd>
							<logic:equal name="prpLregistDto" property="modifyFlag" value="1">
								<TD>
									<a
										href="/claim/registBeforeEdit.do?editType=PERFECT&prpLregistRegistNo=<bean:write name='prpLregistDto' property='registNo' filter='true'/>&prpCmainPolicyNo=<bean:write name='prpLregistDto' property='policyNo' filter='true'/>"
										target="fraInterface"> <bean:write name='prpLregistDto' property='registNo' filter='true' /></a>
								</TD>
							</logic:equal>
							<logic:notEqual name="prpLregistDto" property="modifyFlag" value="1">
								<TD title="本案已註銷或已立案，不允許修改！">
									<a href="#" onclick="alert('本案已註銷或已立案，不允許修改！');"> <bean:write name='prpLregistDto' property='registNo' filter='true' /></a>
								</TD>
							</logic:notEqual>
							<TD>
								<bean:write name='prpLregistDto' property='policyNo' filter='true' />
							</TD>
							<TD>
								<bean:write name='prpLregistDto' property='riskCode' filter='true' />
							</TD>
							<TD>
								<bean:write name='prpLregistDto' property='insuredName' filter='true' />
							</TD>
							<TD>
								<bean:write name='prpLregistDto' property='reportDate' filter='true' />
							</TD>
							<TD title="CX400:表示此報案是通過400報案系統接入的">
								<bean:write name='prpLregistDto' property='serviceNo' filter='true' />
							</TD>
							<TD title="CX400:表示此報案是通過400報案系統接入的">
								<logic:equal name="prpLregistDto" property="modifyFlag" value="-1">
									<s:text name="regist.registerCannotModify" />
									<%--已注销,不可修改 --%>
								</logic:equal>
								<logic:equal name="prpLregistDto" property="modifyFlag" value="0">
									<s:text name="regist.cannotModify" />
									<%--已立案，不可修改 --%>
								</logic:equal>
								<logic:equal name="prpLregistDto" property="modifyFlag" value="1">
									<s:text name="regist.canModify" />
									<%--可修改 --%>
								</logic:equal>
							</TD>
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
							String actionType = "query";//查詢的actionType
						%>
						<%@include file="/common/pub/TurnOverPage.jsp"%>
					</tr>
				</table>
			</tfoot>
		</TABLE>
		<br>
	</form>
</body>
</html>