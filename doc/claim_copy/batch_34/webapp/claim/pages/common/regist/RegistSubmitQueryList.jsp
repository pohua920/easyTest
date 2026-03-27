<%--
****************************************************************************
* DESC       ：已提交报案查询条件输入页面
* AUTHOR     ：zhangpeng
* CREATEDATE ：2004-04-21
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
<title><s:text name="title.registBeforeEdit.queryRegist" /></title>
<script src="/claim/common/js/showpage.js">
	
</script>
<html:base />
</head>
<body>
	<table class="common" cellpadding="4" cellspacing="1">
		<tr>
			<td colspan="4" class="formtitle">
				<s:text name="title.registBeforeEdit.titleName" />
			</td>
		</tr>
		<tr>
			<td class="centertitle">
				<s:text name="common.status.submited" />
				<%--已提交 --%>
				<s:text name="db.prpLregist.registNo" />
			</td>
			<td class="centertitle">
				<s:text name="db.prpLregist.policyNo" />
			</td>
			<td class="centertitle">
				<s:text name="db.prpLregist.operatorCode" />
			</td>
			<td class="centertitle">
				<s:text name="db.prpLregist.inputDate" />
			</td>
		</tr>
		<%
			int index = 0;
		%>
		<logic:notEmpty name="prpLregistDto" property="registList">
			<logic:iterate id="prpLregist1" name="prpLregistDto" property="registList">
				<%
					if (index % 2 == 0)
									out.print("<tr class=listodd>");
								else
									out.print("<tr class=listeven>");
				%>
				<td>
					<a href="/claim/registSubmitQueryList.do?prpLregistRegistNo=<bean:write name='prpLregist1' property='registNo'/>&editType=<bean:write name='prpLregistDto' property='editType'/>"> <bean:write
							name="prpLregist1" property="registNo" /></a>
				</td>
				<td>
					<bean:write name="prpLregist1" property="policyNo" />
				</td>
				<td>
					<bean:write name="prpLregist1" property="operatorCode" />
				</td>
				<td>
					<bean:write name="prpLregist1" property="inputDate" />
				</td>
				</tr>
				<%
					index++;
				%>
			</logic:iterate>
		</logic:notEmpty>
	</table>
</body>
</html:html>