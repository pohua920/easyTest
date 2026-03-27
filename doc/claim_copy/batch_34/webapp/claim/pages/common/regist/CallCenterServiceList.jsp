<%--
****************************************************************************
* DESC       ：报案查询条件结果页面
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
<%@ page import="com.sinosoft.claim.dto.domain.*"%>
<%
	//原因：向页面中增加一个打印按钮
%>
<script src="/claim/common/js/showpage.js">
	
</script>
<html:html locale="true">
<head>
<app:css />
<title><s:text name="title.registBeforeEdit.queryRegist" /></title>
<html:base />
</head>
<body>
	<input type="hidden" name="pageFlag">
	<form name="fm" action="/claim/RegistQuery.do" method="post">
		<table class="common" cellpadding="5" cellspacing="1">
			<tr>
				<td colspan="6" class="formtitle">
					<s:text name="regist.serviceNumberList" />
					<%--服务单号列表 --%>
				</td>
			</tr>
			<tr>
				<td class="centertitle">
					<s:text name="db.prpDrate.serialNo" />
					<%--序号 --%>
				</td>
				<td class="centertitle">
					<s:text name="regist.type" />
					<%--类型 --%>
				</td>
				<td class="centertitle">
					<s:text name="regist.inputNumber" />
					<%--录入日期 --%>
				</td>
				<td class="centertitle">
					<s:text name="regist.sitdownNumber" />
					<%--坐席号 --%>
				</td>
				<td class="centertitle">
					<s:text name="regist.serviceNumber" />
					<%--服务单号 --%>
				</td>
			</tr>
			<%
				int index = 0;
			%>
			<logic:notEmpty name="serviceList">
				<logic:iterate id="prpLcallCenterDto" name="serviceList">
					<%
						if (index % 2 == 0)
										out.print("<tr class=listodd>");
									else
										out.print("<tr class=listeven>");
					%>
					<td align="center">
						<bean:write name="prpLcallCenterDto" property="serialNo" />
					</td>
					<td align="center">
						<logic:equal name="prpLcallCenterDto" property="type" value='A'>
							<s:text name="regist.saveAction" />
							<%--保存报案 --%>
						</logic:equal>
						<logic:equal name="prpLcallCenterDto" property="type" value='B'>
							<s:text name="regist.modifyActionNote" />
							<%--修改报案备注 --%>
						</logic:equal>
					</td>
					</td>
					<td align="center">
						<bean:write name='prpLcallCenterDto' property='inputDate' />
					</td>
					<td align="center">
						<bean:write name='prpLcallCenterDto' property='surId' />
					</td>
					<td align="center">
						<bean:write name="prpLcallCenterDto" property="serviceNo" />
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
						</tr>
					</table>
				</td>
			</tr>
		</table>
		</table>
		</tr>
		</table>
	</form>
</body>
</html:html>