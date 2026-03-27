<%--
****************************************************************************
* DESC       ：报案查询条件结果页面
* AUTHOR     ：lijiyuan
* CREATEDATE ：2004-03-01
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>

<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app" %>

<%
	//原因：向页面中增加一个打印按钮
%>
  <script language="javascript">
  <%--案件状态标志处理--%>
    function submitForm()
    {
      fm.submit();//提交
    }
  </script>
<html:html locale="true">
<head>
<app:css />
<title><s:text name="title.registBeforeEdit.queryRegist" /></title>
<html:base />
</head>
<body>
	<form name="fm" action="/claim/processLinkRecord.do" method="post">
		<input type="hidden" name="pageFlag">
		<input type="hidden" name="editType" value="queryList">
		<input type="hidden" name="nodeType" value="claim">
		<table class="common" cellpadding="5" cellspacing="1">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="linkRecord.claimRelateResultList" />
				</td>
			</tr>
			<!-- 理赔联系记录结果列表 -->
			<tr>
				<!--<td class="centertitle" >案件状态</td> -->
				<td class="centertitle">
					<s:text name="db.prpLclaim.claimNo" />
				</td>
				<td class="centertitle">
					<s:text name="prompt.queRegist.RegistNo" />
				</td>
				<!-- 报案号 -->
				<td class="centertitle">
					<s:text name="db.utiTtyRecord.userName" />
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
							<a href="/claim/processLinkRecord.do?claimNo=<bean:write name='prpLclaim1' property='claimNo'/>&editType=view&riskCode=<bean:write name="prpLclaim1" property="riskCode"/>"><bean:write
									name="prpLclaim1" property="claimNo" /></a>
						</td>
						<td align="center">
							<bean:write name="prpLclaim1" property="registNo" />
						</td>
						<td align="center">
							<bean:write name="prpLclaim1" property="operatorCode" />
						</td>
						<td align="center">
							<bean:write name="prpLclaim1" property="operateDate" />
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
					<!-- 共查询出 --><%=index%><s:text name="compensate.common2" />
					<!-- 条满足条件的记录 -->
				</td>
			</tr>
		</table>
		</table>
		</tr>
		</table>
	</form>
</body>
</html:html>