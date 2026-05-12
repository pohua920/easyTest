<%--
****************************************************************************
* DESC       ：回访查询结果
* AUTHOR     ：理赔组
* CREATEDATE ：2004-07-13
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<html:html locale="true">
<head>
<app:css />
<title><s:text name="title.claimBeforeEdit.queryClaim" /></title>
<script src="/claim/common/js/showpage.js">
	
</script>
<html:base />
</head>
<body>
	<table class="common" cellpadding="5" cellspacing="1">
		<tr>
			<td colspan=5 class="formtitle">
				<s:text name="certainLoss.nuclearDamageQuery" />
			</td>
		</tr>
		<!-- 查询核损信息 -->
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
				<s:text name="db.prpLlawsuit.operatorCode" />
			</td>
			<!-- 操作员 -->
			<td class="centertitle">
				<s:text name="db.prpLclaimStatus.operatedate" />
			</td>
			<!-- 操作时间 -->
		</tr>
		<%
			int index = 0;
		%>
		<logic:notEmpty name="swfLogDto" property="swfLogList">
			<logic:iterate id="swfLogList1" name="swfLogDto" property="swfLogList">
				<%
					if (index % 2 == 0)
									out.print("<tr class=listodd>");
								else
									out.print("<tr class=listeven>");
				%>
				<tr class=common>
					<td align="center">
						<logic:equal name="swfLogList1" property="nodeStatus" value='1'>
							<s:text name="common.status.untreated" />
							<!-- 未处理 -->
						</logic:equal>
						<logic:equal name="swfLogList1" property="nodeStatus" value='2'>
							<s:text name="common.status.intreating" />
							<!-- 正处理 -->
						</logic:equal>
						<logic:equal name="swfLogList1" property="nodeStatus" value='3'>
							<s:text name="common.status.treated" />
							<!-- 已处理 -->
						</logic:equal>
						<logic:equal name="swfLogList1" property="nodeStatus" value='4'>
							<s:text name="common.status.submited" />
							<!-- 已提交 -->
						</logic:equal>
						<logic:equal name="swfLogList1" property="nodeStatus" value='5'>
							<s:text name="common.status.revoked" />
							<!-- 已撤消 -->
						</logic:equal>
					</td>
					<td align="center">
						<a
							href="/claim/backVisitBeforeEdit.do?RegistNo=<bean:write name='swfLogList1' property='businessNo'/>&editType=<bean:write name='swfLogDto' property='editType'/>&&lossTypeFlag=<bean:write name='swfLogList1' property='typeFlag'/>">
							<bean:write name="swfLogList1" property="businessNo" />
						</a>
					</td>
					<td align="center">
						<bean:write name="swfLogList1" property="policyNo" />
					</td>
					<td align="center">
						<bean:write name="swfLogList1" property="handlerCode" />
					</td>
					<td align="center">
						<bean:write name="swfLogList1" property="handleTime" />
					</td>
				</tr>
				<%
					index++;
				%>
			</logic:iterate>
		</logic:notEmpty>
		<tr class="listtail">
			<td colspan="5">
				<s:text name="certainLoss.totalInquiries" />
				<!-- 共查询出 --><%=index%><s:text name="certainLoss.meetRecord" />
				<!-- 条满足条件的记录 -->
			</td>
		</tr>
	</table>
	</tr>
	</table>
</body>
</html:html>
