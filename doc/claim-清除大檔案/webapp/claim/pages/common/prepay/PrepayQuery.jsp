<!--
****************************************************************************
* DESC       ：预赔查询条件输入页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-05-11
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
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
</head>
<body>
	<form action="/RegistQuery.do" focus="prpLregistRegistNo" method="post">
		<table class=common id="QueryInput" border="0" align="center" cellpadding="5" cellspacing="1">
			<tr>
				<td class=title>
					<s:text name="prompt.regist.registForm.RegistNo" />
				</td>
				<td class=input>
					<select class="codecode" name="RegistNoSign">
						<%@include file="/common/pub/UIStringOption.html"%>
					</select>
					<input type="text" name="registForm.prpLregistRegistNo" styleClass="common" maxlength="22" />
				</td>
				<td class=title>
					<s:text name="prompt.regist.registForm.PolicyNo" />
				</td>
				<td class=input>
					<select name="PolicyNoSign"><%@include file="/common/pub/UIStringOption.html"%></select>
					<input type="text" name="registForm.prpLregistPolicyNo" styleClass="common" maxlength="22" />
				</td>
			</tr>
			<tr>
				<td class=button colspan="2">
					<innput type="submit" styleClass="button1" value="<s:text name='button.query.value' />" />
				</td>
				<td class=button colspan="2">
					<innput type="reset" styleClass="button1" value="<s:text name='button.reset.value' />" />
				</td>
			</tr>
		</table>
		<script src="${ctx}/common/js/showpage.js">
			
		</script>
	</form>
</body>
</html>
