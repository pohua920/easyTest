<%--
****************************************************************************
* DESC       ：立案查询结果显示页面
* AUTHOR     ：lijiyuan
* CREATEDATE ：2004-03-01
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
<title><s:text name="title.certifyBeforeEdit.toTask" /></title>
<%--待处理任务--%>
<script src="/claim/common/js/showpage.js">
	
</script>
<html:base />
</head>
<body>
	<table class="common" cellpadding="5" cellspacing="1">
		<tr>
			<td colspan=4 class="formtitle">
				<s:text name="certify.toTasks" />
			</td>
		</tr>
		<%--待处理任务--%>
		<tr>
			<td class="centertitle">
				<s:text name="prpLbpmMain.mainNo" />
			</td>
			<%--报案号--%>
			<td class="centertitle">
				<s:text name="db.view_larrearage.policyNo " />
			</td>
			<%--保单号--%>
			<td class="centertitle">
				<s:text name="db.prpLlawsuit.operatorCode" />
			</td>
			<%--操作员--%>
			<td class="centertitle">
				<s:text name="db.prpLlawsuit.inputDate" />
			</td>
			<%--输单日期--%>
		</tr>
		<%
			int index = 0;
		%>
		<logic:notEmpty name="prpLclaimDto" property="claimList">
			<logic:iterate id="prpLclaim1" name="prpLclaimDto" property="claimList">
				<%
					if (index % 2 == 0)
									out.print("<tr class=listodd>");
								else
									out.print("<tr class=listeven>");
				%>
				<tr class=common>
					<td align="center">
						<bean:write name="prpLclaim1" property="businessNo" />
					</td>
					<td align="center">
						<bean:write name="prpLclaim1" property="policyNo" />
					</td>
					<td align="center">
						<bean:write name="prpLclaim1" property="handlerCode" />
					</td>
					<td align="center">
						<bean:write name="prpLclaim1" property="inputDate" />
					</td>
				</tr>
				<%
					index++;
				%>
			</logic:iterate>
		</logic:notEmpty>
		<tr class="listtail">
			<td colspan="4">
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