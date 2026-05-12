<%--
****************************************************************************
* DESC       ：查勘结果
* AUTHOR     ：理赔组
* CREATEDATE ：2013-02-01
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
				<s:text name="check.retrieveInfoSurvey" />
			</td>
		</tr>
		<%--查询查勘信息--%>
		<tr>
			<td class="centertitle">
				<s:text name="certainLoss.prpLscheduleMainWF.caseState" />
			</td>
			<%--案件状态：--%>
			<td class="centertitle">
				<s:text name="prpLregist.registNo" />:
			</td>
			<%--报案号--%>
			<td class="centertitle">
				<s:text name="db.prpLclaim.policyNo" />
			</td>
			<td class="centertitle">
				<s:text name="certainLoss.person1" />
			</td>
			<%--查勘人1--%>
			<td class="centertitle">
				<s:text name="db.prpLclaimStatus.operatedate" />：
			</td>
			<%--操作时间--%>
		</tr>
		<%
			int index = 0;
		%>
		<logic:notEmpty name="prpLcheckDto" property="checkList">
			<logic:iterate id="prpLcheck1" name="prpLcheckDto" property="checkList">
				<%
					if (index % 2 == 0)
									out.print("<tr class=listodd>");
								else
									out.print("<tr class=listeven>");
				%>
				<tr class=common>
					<td align="center">
						<logic:equal name="prpLcheck1" property="status" value='1'>
							<s:text name="common.status.untreated" />
							<%--未处理--%>
						</logic:equal>
						<logic:equal name="prpLcheck1" property="status" value='2'>
							<s:text name="common.status.intreating" />
							<%--正处理--%>
						</logic:equal>
						<logic:equal name="prpLcheck1" property="status" value='3'>
							<s:text name="common.status.treated" />
							<%--已处理--%>
						</logic:equal>
						<logic:equal name="prpLcheck1" property="status" value='4'>
							<s:text name="common.status.submited" />
							<%--已提交--%>
						</logic:equal>
						<logic:equal name="prpLcheck1" property="status" value='5'>
							<s:text name="common.status.revoked" />
							<%--已撤消--%>
						</logic:equal>
					</td>
					<td align="center">
						<a href="/claim/certifyFinishQueryList.do?prpLcertifyCertifyNo=<bean:write name='prpLcheck1' property='registNo'/>&editType=EDIT&nodeType=check"><bean:write name="prpLcheck1"
								property="registNo" /></a>
					</td>
					<td align="center">
						<bean:write name="prpLcheck1" property="policyNo" />
					</td>
					<td align="center">
						<bean:write name="prpLcheck1" property="checker1" />
					</td>
					<td align="center">
						<bean:write name="prpLcheck1" property="operateDate" />
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
				<%--共查询出--%><%=index%><s:text name="certainLoss.meetRecord" />
				<%--条满足条件的记录--%>
			</td>
		</tr>
	</table>
	</tr>
	</table>
</body>
</html:html>