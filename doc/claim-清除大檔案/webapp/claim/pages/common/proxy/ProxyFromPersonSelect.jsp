<%--
****************************************************************************
* DESC       ：查询机构内非车查勘、立案、理算、结案权限需调派人员界面
* AUTHOR     ：罗畅
* CREATEDATE ： 2010-06-08
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
<title>需调派人员选择</title>
<%-- 公用函数 --%>
<script src="/claim/common/js/Common.js"></script>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<script language="javascript">
	function submitForm() {
		var proxyFromPersonCode = fm.ProxyFromPerson.value;
		if (proxyFromPersonCode == "") {
			alert("请选择需要调派的人员！");
			return false;
		}
		fm.submit();//提交
	}
</script>
<body>
	<form name="fm" action="/claim/Proxy.do?actionType=ViewClaim" method="post">
		<table width="100%" border="0" align="center" cellpadding="4" cellspacing="1" class="common">
			<tr>
				<td colspan="2" class="formtitle">需调派人员选择</td>
			</tr>
			<tr>
				<td width='45%' align="right">人员名称：</td>
				<td width='55%'>
					<select class=tag name="ProxyFromPerson">
						<option value="" selected>请选择</option>
						<logic:notEmpty name="UserDtoList">
							<logic:iterate id="PrpDuserDto" name="UserDtoList">
								<option value="<bean:write name='PrpDuserDto' property='userCode'/>">
									<bean:write name='PrpDuserDto' property='userName' />
								</option>
							</logic:iterate>
						</logic:notEmpty>
					</select>
				</td>
			</tr>
			<tr>
				<td class="title" style="color: red" colspan="2" align="center">
					帮助：系统自动带出您登陆机构下，拥有非车险查勘、立案、单证收集、理算或结案中一或多项权限的人员。<br> 如果没有带出您所需调派的人员，请确认您登陆时选择的机构，以及该人员是否有此机构下相应的权限。<br>
				</td>
			</tr>
		</table>
		<table width=100%>
			<tr>
				<td class='button' align="center" colspan="2">
					<input id="button" type=button class='bigbutton' value="查询在处理的赔案" onClick="submitForm();">
				</td>
			</tr>
		</table>
	</form>
</body>
</html:html>
