<!--
****************************************************************************
* DESC       ：报案查询条件输入页面
* AUTHOR     ：lijiyuan
* CREATEDATE ：2004-03-01
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
-->
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true">
<head>
<title><s:text name="prompt.regist.RegistQuery.QueryTitleName" /></title>
<script src="${ctx}/common/js/showpage.js">
	
</script>
<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
</head>
<body>
	<form action="/RegistQuery" focus="prpLregistRegistNo" method="post">
		<table class=common id="QueryInput" width="80%" border="0" align="center" cellpadding="5" cellspacing="1">
			<tr>
				<td class=title>
					<s:text name="prompt.regist.registForm.RegistNo" />
				</td>
				<td class=input>
					<select class="codecode" name="RegistNoSign">
						<%@include file="/common/pub/UIStringOption.html"%>
					</select>
					<input type="text" name="prpLregistPolicyNo" value="${registForm.prpLregistPolicyNo }" styleClass="common" maxlength="22">
				</td>
				<td class=title>
					<s:text name="prompt.regist.registForm.PolicyNo" />
				</td>
				<td class=input>
					<select name="PolicyNoSign"><%@include file="/common/pub/UIStringOption.html"%></select>
					<input type="text" name="prpLregistPolicyNo" value="${registForm.prpLregistPolicyNo }" styleClass="common" maxlength="22">
				</td>
			</tr>
		</table>
		<table width=100%>
			<tr>
				<td class=button colspan="2">
					<submit styleClass="button1" value="<s:text name='button.query.value'/>" />
					<%-- 查询 --%>
				</td>
				<td class=button colspan="2">
					<reset styleClass="button1" value="<s:text name='button.reset.value'/>" />
					<%-- 清除 --%>
				</td>
			</tr>
		</table>
		<script src="${ctx}/common/js/showpage.js">
			
		</script>
	</form>
</body>
</html>
