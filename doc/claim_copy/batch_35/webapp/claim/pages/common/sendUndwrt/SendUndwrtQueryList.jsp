<%--
****************************************************************************
* DESC       ：送审任务审核查询结果页面
* AUTHOR     ：罗畅
* CREATEDATE ：2010-10-12
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%@ page import="com.sinosoft.claim.dto.custom.*"%>
<%@ page import="com.sinosoft.claim.dto.domain.*"%>
<%@ page import="java.util.*"%>
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
<title><s:text name="title.sendUndwrtBeforeEdit.ResultsDisplay" /></title>
<%-- 送审任务待审核查询结果显示 --%>
<script>
	function mysubmit() {
		fm.pageNo.value = "1";
		fm.submit();

	}
</script>
<html:base />
</head>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<body class="interface" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="initPage();">
	<form name="fm" action="/claim/SendUndwrtQuery.do?actionType=Query" method="post" onsubmit="return validateForm(this);">
		<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="sendUndwrt.TaskInformation" />
				</td>
			</tr>
			<%--  送审任务待审核查询信息 --%>
			<tr>
				<td class='title'>
					<s:text name="sendUndwrt.OperationType" />
					：
				</td>
				<%--  理赔操作类型 --%>
				<td class='input'>
					<select name="prpLSendUndwrtNodeType" style="width: 150px">
						<option value="">
							<s:text name="print.all" />
						</option>
						<%--  全部 --%>
						<option value="check">
							<s:text name="check.mentHereunde " />
						</option>
						<%--查勘 --%>
						<option value="claim">
							<s:text name="check.record" />
						</option>
						<%--立案--%>
						<option value="compe">
							<s:text name="sendUndwrt.Adjusting" />
						</option>
						<%-- 理算--%>
					</select>
				</td>
				<td class='title'>
					<s:text name="sendUndwrt.OrganizationCode" />
					：
				</td>
				<%--  理赔组织机构代码 --%>
				<td class='input'>
					<input type=text name="prpLSendUndwrtComCode" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="sendUndwrt.StaffCode" />
					：
				</td>
				<%--送审人员代码 --%>
				<td class='input'>
					<input type=text name="prpLSendUndwrtOperatorCode" class="query">
				</td>
				<td class='title'>
					<s:text name="sendUndwrt.StaffName" />
					:
				</td>
				<%--送审人员名称 --%>
				<td class='input'>
					<input type=text name="prpLSendUndwrtOperatorName" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="sendUndwrt.BusinessNumber" />
					：
				</td>
				<%-- 业务号--%>
				<td class='input'>
					<input type=text name="prpLSendUndwrtBusinessNo" class="query">
				</td>
				<td class='title'></td>
				<td class='input'></td>
			</tr>
			<tr>
				<td class='button' colspan="4">
					<input type=button class='button' value="<s:text name='button.query.value' />" onClick="fm.submit()">
				</td>
			</tr>
		</table>
		<input type="hidden" name="pageFlag">
		<table bgcolor="#2D8EE1" class="common" cellpadding="4" cellspacing="1">
			<tr>
				<td class="centertitle">
					<s:text name="sendUndwrt.BusinessNumber" />
					：
				</td>
				<%-- 业务号--%>
				<td class="centertitle">
					<s:text name="db.view_larrearage.insuredname" />
				</td>
				<%-- 被保险人名称--%>
				<td class="centertitle">
					<s:text name="sendUndwrt.NodeName" />
				</td>
				<%-- 送审节点名称 --%>
				<td class="centertitle">
					<s:text name="sendUndwrt.StaffName" />
					:
				</td>
				<%--送审人员名称 --%>
				<td class="centertitle">
					<s:text name="sendUndwrt.TrialTime" />
				</td>
				<%-- 送审时间 --%>
			</tr>
			<%
				int index = 0;
			%>
			<logic:notEmpty name="prpLSendUndwrtDtoList">
				<logic:iterate id="prpLSendUndwrtDto" name="prpLSendUndwrtDtoList">
					<%
						if (index % 2 == 0)
										out.print("<tr class=listodd>");
									else
										out.print("<tr class=listeven>");
					%>
					<td align="center" width="20%">
						<logic:notEmpty name="prpLSendUndwrtDto" property="swfLogDto">
							<bean:define id="swfLogDto" name="prpLSendUndwrtDto" property="swfLogDto" />
							<a
								href="/claim/<logic:equal
         name="prpLSendUndwrtDto" property="nodeType" value="claim"><logic:equal 
         name="swfLogDto" property="nodeStatus" value="0">claimBeforeEdit.do?RegistNo=<bean:write 
         name="swfLogDto" property="keyIn"/></logic:equal><logic:notEqual 
         name="swfLogDto" property="nodeStatus" value="0">claimFinishQueryList.do?prpLclaimClaimNo=<bean:write 
         name="swfLogDto" property="keyOut"/></logic:notEqual></logic:equal><logic:equal
         name="prpLSendUndwrtDto" property="nodeType" value="check"><logic:equal 
         name="swfLogDto" property="nodeStatus" value="0">checkBeforeEdit.do?RegistNo=<bean:write 
         name="swfLogDto" property="keyIn"/></logic:equal><logic:notEqual 
         name="swfLogDto" property="nodeStatus" value="0">checkFinishQueryList.do?prpLcheckCheckNo=<bean:write 
         name="swfLogDto" property="keyIn"/></logic:notEqual></logic:equal><logic:equal
         name="prpLSendUndwrtDto" property="nodeType" value="compe">compensateBeforeEdit.do?ClaimNo=<bean:write 
         name="swfLogDto" property="keyIn"/></logic:equal><logic:equal
         name="prpLSendUndwrtDto" property="nodeType" value="compp">compensateFinishQueryList.do?prpLcompensateCompensateNo=<bean:write 
         name="swfLogDto" property="businessNo"/></logic:equal>&swfLogFlowID=<bean:write name="swfLogDto" property="flowID"/>&swfLogLogNo=<bean:write name="swfLogDto" property="logNo"/>&status=<bean:write name="swfLogDto" property="nodeStatus"/>&riskCode=<bean:write name="swfLogDto" property="riskCode"/><logic:equal name="swfLogDto" property="nodeStatus" value="0">&editType=ADD</logic:equal><logic:notEqual name="swfLogDto" property="nodeStatus" value="0">&editType=EDIT</logic:notEqual>&nodeType=<bean:write name="swfLogDto" property="nodeType"/>&businessNo=<bean:write name="swfLogDto" property="businessNo"/>&keyIn=<bean:write name="swfLogDto" property="keyIn"/>&policyNo=<bean:write name="swfLogDto" property="policyNo"/>&modelNo=<bean:write name="swfLogDto" property="modelNo"/>&nodeNo=<bean:write name="swfLogDto" property="nodeNo"/>&dfFlag=<bean:write name="swfLogDto" property="dfFlag"/>
        ">
								<bean:write name="prpLSendUndwrtDto" property="businessNo" />
							</a>
						</logic:notEmpty>
					</td>
					<td align="center" width="20%">
						<bean:write name="swfLogDto" property="insuredName" />
					</td>
					<td align="center" width="20%">
						<logic:equal name="prpLSendUndwrtDto" property="nodeType" value="claim">
							<s:text name="check.record" />
						</logic:equal>
						<%--立案--%>
						<logic:equal name="prpLSendUndwrtDto" property="nodeType" value="check">
							<s:text name="check.mentHereunde " />
						</logic:equal>
						<%--查勘 --%>
						<logic:equal name="prpLSendUndwrtDto" property="nodeType" value="compe">
							<s:text name="sendUndwrt.Adjusting" />
						</logic:equal>
						<%-- 理算--%>
						<logic:equal name="prpLSendUndwrtDto" property="nodeType" value="compp">
							<s:text name="sendUndwrt.Adjusting" />
						</logic:equal>
						<%-- 理算--%>
					</td>
					<td align="center" width="20%">
						<bean:write name="prpLSendUndwrtDto" property="operatorName" />
					</td>
					<td align="center" width="20%">
						<bean:write name="prpLSendUndwrtDto" property="inputDate" />
					</td>
					</tr>
					<%
						index++;
					%>
				</logic:iterate>
			</logic:notEmpty>
			<tr class="listtail">
				<td colspan="7">
					<table width="100%" class="common" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<bean:define id="pageview" name="turnPageDto" />
							<%
								TurnPageDto turnPageDto = (TurnPageDto) request.getAttribute("turnPageDto");
									int curPage = turnPageDto.getPageNo();
							%>
							<%@include file="/common/pub/TurnOverPage.jsp"%>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>
</body>
</html:html>
