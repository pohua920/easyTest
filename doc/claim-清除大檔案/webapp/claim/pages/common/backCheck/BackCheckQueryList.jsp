<%--
****************************************************************************
* DESC       ：修复验车结果
* AUTHOR     ：中科软
* CREATEDATE ：2005-09-24
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
<title><s:text name="title.claimBeforeEdit.queryClaim" /></title>
<script src="/claim/common/js/showpage.js">
	
</script>
<html:base />
</head>
<body class="interface" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<form name="fm" action="/claim/verifyLossQuery.do" method="post">
		<table border="0" align="center" cellpadding="0" cellspacing="0" class="common">
			<tr>
				<td width="184" height="26" valign="bottom">
					<table width="184" height="19" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<img src="/claim/images/bgBarLeft.gif" width="12" height="19">
							</td>
							<td width="161" class="formtitle">
								<s:text name="backCheck.queryRepairInformation" />
							</td>
							<!-- 查询修复验车信息 -->
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
				<td class="centertitle">
					<s:text name="db.prpLclaimStatus.status" />
				</td>
				<!-- 案件状态 -->
				<td class="centertitle">
					<s:text name="db.prpLrepairFee.registNo" />
				</td>
				<!-- 报案号 -->
				<td class="centertitle">
					<s:text name="db.prpLclaim.policyNo" />
				</td>
				<td class="centertitle">
					<s:text name="backCheck.nuclearRomanCode" />
				</td>
				<!-- 核损人代码 -->
				<td class="centertitle">
					<s:text name="backCheck.nuclearRomanDate" />
				</td>
				<!-- 核损日期 -->
			</tr>
			<%
				int index = 0;
			%>
			<logic:notEmpty name="prpLverifyLossDto" property="verifyLossList">
				<logic:iterate id="prpLverifyLoss1" name="prpLverifyLossDto" property="verifyLossList">
					<%
						if (index % 2 == 0)
										out.print("<tr class=listodd>");
									else
										out.print("<tr class=listeven>");
					%>
					<td align="center">
						<logic:equal name="prpLverifyLoss1" property="status" value='1'>
							<s:text name="common.status.untreated" />
							<!-- 未处理 -->
						</logic:equal>
						<logic:equal name="prpLverifyLoss1" property="status" value='2'>
							<s:text name="common.status.intreating" />
							<!-- 正处理 -->
						</logic:equal>
						<logic:equal name="prpLverifyLoss1" property="status" value='3'>
							<s:text name="common.status.treated" />
							<!-- 已处理 -->
						</logic:equal>
						<logic:equal name="prpLverifyLoss1" property="status" value='4'>
							<s:text name="common.status.submited" />
							<!-- 已提交 -->
						</logic:equal>
						<logic:equal name="prpLverifyLoss1" property="status" value='5'>
							<s:text name="common.status.revoked" />
							<!-- 已撤消 -->
						</logic:equal>
					</td>
					<td align="center">
						<a
							href="/claim/verifyLossFinishQueryList.do?prpLverifyLossRegistNo=<bean:write name='prpLverifyLoss1' property='registNo'/>&editType=<bean:write name='prpLverifyLossDto' property='editType'/>&riskCode=<bean:write name='prpLverifyLoss1' property='riskCode'/>&lossItemCode=<bean:write name='prpLverifyLoss1' property='lossItemCode'/>&nodeType=backc&flag=1">
							<bean:write name="prpLverifyLoss1" property="registNo" />
						</a>
					</td>
					<td align="center">
						<bean:write name="prpLverifyLoss1" property="policyNo" />
					</td>
					<td align="center">
						<bean:write name="prpLverifyLoss1" property="handlerCode" />
					</td>
					<td align="center">
						<bean:write name="prpLverifyLoss1" property="defLossDate" />
					</td>
					</tr>
					<%
						index++;
					%>
				</logic:iterate>
			</logic:notEmpty>
			<tr class="listtail">
				<td colspan="5">
					<table width="100%" class="common" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<bean:define id="pageview" name="prpLverifyLossDto" property="turnPageDto" />
							<%
								PrpLverifyLossDto prpLverifyLossDto = (PrpLverifyLossDto) request.getAttribute("prpLverifyLossDto");
									int curPage = prpLverifyLossDto.getTurnPageDto().getPageNo();
							%>
							<%@include file="/common/pub/TurnOverPage.jsp"%>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		</tr>
		</table>
		<input type="hidden" name="editType" value="EDIT">
	</form>
</body>
</html:html>
