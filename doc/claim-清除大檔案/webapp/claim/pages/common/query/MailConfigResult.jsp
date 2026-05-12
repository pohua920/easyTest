<%--
****************************************************************************
* DESC       ：代理人手机号码查询界面
* AUTHOR     ： liuwei
* CREATEDATE ： 2011-04-19
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@page import="java.util.*"%>
<%@page import="com.fubon.mail.schema.MailConfigSchema"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<html locale="true">
<head>
<title><s:text name="title.query.agentPhoneQueryPage" /></title>
<%--代理人手机号码查询页面 --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<base target="QueryResultFrame">
</head>
<%
	List list = null;
	MailConfigSchema mailConfigSchema = null;
	if (request.getAttribute("list") != null) {
		list = (List) request.getAttribute("list");
	}
%>
<body>
	<form name="fm" action="/claim/mailConfig.do" method="post" onsubmit="">
		<table width=100%>
			<tr>
				<td colspan=10 align="center" class="formtitle">
					<s:text name="query.emailAddressQueryShow" />
					<%--邮箱地址查询展示 --%>
				</td>
			</tr>
			<tr>
				<td class="formtitle" width="5%">
					<s:text name="db.prpDrate.serialNo" />
					<%--序号 --%>
				</td>
				<td class="formtitle" width="20%">
					<s:text name="db.prpDdbs.comName" />
					<%--机构名称 --%>
				</td>
				<td class="formtitle" width="10%">
					<s:text name="db.prpDuser.userCode" />
					<%--员工代码 --%>
				</td>
				<td class="formtitle" width="15%">
					<s:text name="db.prpDuser.userName" />
					<%--员工名称 --%>
				</td>
				<td class="formtitle" width="20%">
					<s:text name="query.emailAddress" />
					<%--邮箱地址 --%>
				</td>
				<td class="formtitle" width="5%">
					<s:text name="db.prpGnode.nodeName" />
					<%--节点名称 --%>
				</td>
				<td class="formtitle" width="5%">
					<s:text name="query.ifUsefull" />
					<%--是否有效 --%>
				</td>
				<td class="formtitle" width="10%">
					<s:text name="query.bulidTime" />
					<%--创建时间 --%>
				</td>
				<td class="formtitle" colspan="2" width="10%">
					<s:text name="certify.operate" />
					<%--操作 --%>
				</td>
			</tr>
			<%
				int index = 0;
				String userCode = "";
				String userName = "";
				String strValidStatus = "有效";
				String node = "";
				if (list != null) {
					for (int i = 0; i < list.size(); i++) {
						mailConfigSchema = (MailConfigSchema) list.get(i);
						node = mailConfigSchema.getNode();
						if ("regis".equals(node)) {
							node = "报案";
						} else if ("claim".equals(node)) {
							node = "立案";
						} else if ("sched".equals(node)) {
							node = "调度";
						} else if ("check".equals(node)) {
							node = "查勘";
						} else if ("certa".equals(node)) {
							node = "定损";
						} else if ("verif".equals(node)) {
							node = "核损";
						} else if ("certi".equals(node)) {
							node = "单证";
						} else if ("compe".equals(node)) {
							node = "理算";
						} else if ("compp".equals(node)) {
							node = "计算书";
						} else if ("veric".equals(node)) {
							node = "核赔";
						} else if ("endca".equals(node)) {
							node = "结案";
						} else if ("other".equals(node)) {
							node = "其它";
						} else if ("all".equals(node)) {
							node = "全部";
						}
						if (mailConfigSchema.getUserCode() != null) {
							userCode = mailConfigSchema.getUserCode();
						}
						if (mailConfigSchema.getUserName() != null) {
							userName = mailConfigSchema.getUserName();
						}
						if (mailConfigSchema.getValidStatus().equals("0")) {
							strValidStatus = "无效";
						}
						if (index % 2 == 0) {
							out.print("<tr class=listodd>");
						} else {
							out.print("<tr class=listeven>");
						}
			%>
			<tr>
				<td align="center" class="page"><%=i + 1%></td>
				<td align="center" class="page"><%=mailConfigSchema.getComCName()%></td>
				<td align="center" class="page"><%=userCode%></td>
				<td align="center" class="page"><%=userName%></td>
				<td align="center" class="page"><%=mailConfigSchema.getEmail()%></td>
				<td align="center" class="page"><%=node%></td>
				<td align="center" class="page"><%=strValidStatus%></td>
				<td align="center" class="page"><%=mailConfigSchema.getCreateTime()%></td>
				<td class='button' class="page">
					<a href="mailConfig.do?mailConfig_sequence='<%=mailConfigSchema.getMailConfig_sequence()%>'&editType=delete"><s:text name="certify.delete" />
						<%--删除 --%></a>
				</td>
				<td class='button' class="page">
					<a href="mailConfig.do?mailConfig_sequence='<%=mailConfigSchema.getMailConfig_sequence()%>'&editType=update"><s:text name="button.edit.value" />
						<%--修改 --%></a>
				</td>
			</tr>
			<%
				index++;
					}
				}
			%>
		</table>
	</form>
</body>
</html>