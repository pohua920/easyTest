<%--
****************************************************************************
* DESC       ：已提交案件查询输入界面
* AUTHOR     ： zhangpeng
* CREATEDATE ： 2004-04-21
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<html:html locale="true">
<head>
<title><s:text name="title.scheduleObject.schedulingInformationQuery" /></title>
<%--调度机构查询 --%>
<%-- 公用函数 --%>
<script src="/claim/common/js/Common.js"></script>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body class="interface" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="initPage();">
	<form name="fm" action="/claim/scheduleObjectQuery.do" method="post" onsubmit="return validateForm(this);">
		<table border="0" align="center" cellpadding="0" cellspacing="0" class="common">
			<tr>
				<td width="184" height="26" valign="bottom">
					<table width="184" height="19" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="161" class="formtitle">
								<s:text name="scheduleObject.schedulingInformationQueryConditions" />
							</td>
							<%--调度机构查询条件 --%>
						</tr>
					</table>
				</td>
				<td valign="bottom">
					<font color="#666666">&nbsp;
				</td>
			</tr>
		</table>
		<table width="90%" border="0" align="center" cellpadding="4" cellspacing="1" bgcolor="#2D8EE1" class="common">
			<tr>
				<td class='title' style="width: 15%">
					<s:text name="scheduleObject.mechanismConfigurationCode" />
					<%--调度机构代码 --%>
					:
				</td>
				<td class='input' style="width: 25%">
					<select class=query name="ComCodeSign" style="width: 40px">
						<option value="=">=</option>
						<option value="*">*</option>
					</select>
					<input type=text name="ComCode" class="input" style="width: 140px">
				</td>
				<td class='title' style="width: 15%">
					<s:text name="scheduleObject.mechanismConfigurationName" />
					<%--调度机构名称 --%>
					:
				</td>
				<td class='input' style="width: 25%">
					<select class=query name="ComCNameSign" style="width: 40px">
						<option value="=">=</option>
						<option value="*">*</option>
					</select>
					<input type=text name="ComCName" class="input" style="width: 140px">
				</td>
				<td class='button' style="width: 20%" rowspan=2>
					<input type=submit class='button' value="<s:text name='button.query.value' />">
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="db.prpDcompany.validStatus " />
					<%--效力状态 --%>
					:
				</td>
				<td class="input">
					<input type=checkbox name="validStatus1" value="1">
					<s:text name="regist.prpLregist.yes" />
					<%--是 --%>
					</radio>
					<input type=checkbox name="validStatus2" value="0">
					<s:text name="regist.prpLregist.no" />
					<%--否 --%>
					</radio>
				</td>
				<td class="title" style="width: 15%" style="valign:bottom">
					<s:text name="scheduleObject.mechanismConfigurationKind" />
					<%--调度机构类型 --%>
					:
				</td>
				<td class="input" style="width: 20%">
					<input type=checkbox name="comType1" value="1000001">
					<s:text name="regist.prpLregist.repairFactory" />
					<%--修理厂 --%>
					</radio>
					<input type=checkbox name="comType2" value="1000002">
					<s:text name="scheduleObject.gongGuHang" />
					<%--公估行 --%>
					</radio>
				</td>
			</tr>
		</table>
		<input type="hidden" name="editType" value="QUERY">
	</form>
</body>
</html:html>