<%--
****************************************************************************
* DESC       ：邮件配制维护操作界面
* AUTHOR     ：zhyi 
* CREATEDATE ： 2011-09-22
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<html locale="true">
<head>
<title><s:text name="title.query.emailQueryPage" /></title>
<%--邮件配制维护查询页面 --%>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<%-- 标签页样式 --%>
<jsp:include page="/behaviors/MpcStyle.jsp" />
<!--这个函数是调动所能用到的通用js的过程，一般包括最常用的js的函数声明都在meta_js.jsp中-->
</head>
<body>
	<form name="fm" action="/claim/mailConfig.do" method="post" onsubmit="">
		<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td class="formtitle" colspan="9">
					<s:text name="query.queryEmailPage" />
					<%--查询邮件配制维护信息 --%>
				</td>
			</tr>
			<tr>
				<td class="title" style="width: 11%">
					<s:text name="query.organization" />
					<%--组织机构 --%>
					:
				</td>
				<td class="input">
					<input type=text class="codecode" name="comCode" num=-1 style="width: 20%" title="具體單位" value="" ondblclick="dbclickComCodeByProvinceCode1(this,'dbclick','0,1','Y','50','Check');"
						onkeyup="dbclickComCodeByProvinceCode1(this,'keyup','0,1','Y','50','Check');" onchange="dbclickComCodeByProvinceCode1(this,'change','0,1','Y','50','Check');">
					<input type=text class="codecode" name="comCname" title="具體單位" style="width: 50%" value="" ondblclick="dbclickComCodeByProvinceCode1(this,'dbclick','-1,0','N','50');"
						onkeyup="dbclickComCodeByProvinceCode1(this,'keyup','-1,0','N','50');" onchange="dbclickComCodeByProvinceCode1(this,'change','-1,0','N','50');">
					<img src="/claim/images/bgMarkMustInput.jpg">
				</td>
				<td class="title" style="width: 11%">
					<s:text name="check.personnel" />
					<%--人员 --%>
					:
				</td>
				<td class="input" colspan=3>
					<input type=text name="userCode" class="codecode" style="width: 20%" maxlength="10" title="操作員" value="" ondblclick="dbclickCheckPerson1(this,'dbclick','0,1','Y');"
						onkeyup="dbclickCheckPerson1(this,'keyup','0,1','Y');">
					<input type=text name="userName" class="codecode" style="width: 50%" title="操作員" value="" ondblclick="dbclickCheckPerson1(this,'dbclick','-1,0','N');"
						onkeyup="dbclickCheckPerson1(this,'keyup','-1,0','N');">
				</td>
			</tr>
			<tr>
				<td>
					<s:text name="query.ifUsefull" />
					<%--是否有效 --%>
					：
				</td>
				<td>
					<s:text name="query.flagTrue" />
					<%--有效 --%>
					<input type="radio" name="validStatus" value="1" checked="checked" />
					<s:text name="query.flagFalse" />
					<%--无效 --%>
					<input type="radio" name="validStatus" value="0" />
				</td>
			</tr>
			<tr>
				<td class='button' colspan="2">
					<input type="button" class='button' value="<s:text name='button.query.value' />" onclick="return submitForm('select')">
				</td>
				<td class='button' colspan="2">
					<input type=button class='button' value="<s:text name="button.add.value" />" onClick="return insertMethod();">
					<%--增加 --%>
				</td>
			</tr>
			<tr>
				<td colspan="9">
					<iframe name=QueryResultFrame src='about:blank' style='Z-INDEX: 1; WIDTH: 100%; HEIGHT: 410;' 0' marginheight='0' hspace='0' vspace='0' frameborder='0' scrolling='AUTO'> </iframe>
				</td>
			</tr>
		</table>
	</form>
</body>
<script language="javascript">
	function insertMethod() {
		fm.action = "/claim/common/query/MailConfigEdit.jsp?editType=insert";
		fm.target = "QueryResultFrame";
		fm.submit();
		return true;
	}
	function submitForm(editType) {
		fm.action = "/claim/mailConfig.do?editType=" + editType;
		fm.target = "QueryResultFrame";
		fm.submit();
		return true;
	}
</script>
</html>