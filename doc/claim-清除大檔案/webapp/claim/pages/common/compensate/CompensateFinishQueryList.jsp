<%--
****************************************************************************
* DESC       ：实赔查询结果显示页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-05-19
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
			<td colspan=4 class="formtitle">
				<s:text name="title.claimBeforeEdit.titleName" />
			</td>
		</tr>
		<tr>
			<td class="centertitle">
				<s:text name="db.prpLcfee.compensateNo" />
			</td>
			<!-- 赔款计算书号 -->
			<td class="centertitle">
				<s:text name="check.claimNum" />
			</td>
			<!-- 赔案号 -->
			<td class="centertitle">
				<s:text name="prompt.queRegist.PolicyNo" />
			</td>
			<!-- 保单号 -->
			<td class="centertitle">
				<s:text name="compensate.sumPayMoney" />
			</td>
			<!-- 总赔付金额(折TWD) -->
		</tr>
		<%
			int index = 0;
		%>
		<logic:notEmpty name="prpLcompensateDto" property="compensateList">
			<logic:iterate id="prpLcompensate1" name="prpLcompensateDto" property="compensateList">
				<%
					if (index % 2 == 0)
									out.print("<tr class=listodd>");
								else
									out.print("<tr class=listeven>");
				%>
				<tr class=common>
					<td>
						<a
							href="/claim/compensateFinishQueryList.do?prpLcompensateCompensateNo=<bean:write name='prpLcompensate1' property='compensateNo'/>&editType=<bean:write name='prpLcompensateDto' property='editType'/>">
							<bean:write name="prpLcompensate1" property="compensateNo" />
						</a>
					</td>
					<td>
						<bean:write name="prpLcompensate1" property="claimNo" />
					</td>
					<td>
						<bean:write name="prpLcompensate1" property="policyNo" />
					</td>
					<td>
						<bean:write name="prpLcompensate1" property="sumPaid" />
					</td>
				</tr>
				<%
					index++;
				%>
			</logic:iterate>
		</logic:notEmpty>
	</table>
	</tr>
	</table>
</body>
</html:html>