<%--
****************************************************************************
* DESC       ：公估机构新增保存界面
* AUTHOR     ： weizeyu
* CREATEDATE ： 2009-03-06
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<jsp:directive.page import="com.sinosoft.claim.dto.domain.PrplexternalagencyDto" />
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="com.sinosoft.claim.dto.domain.PrplexternalagencyDto"%>
<html locale="true">
<head>
<title><s:text name="query.assessmentInformationQueryPage" /></title>
<%--公估信息查询页面 --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script language="javascript">
  <%--案件状态标志处理--%>
<!--
	function submitForm(editType) {
		if (editType == 'delete') {
			fm.editType.value = 'delete';
		} else if (editType == 'update') {
			fm.editType.value = 'update';
		} else if (editType == 'add') {
			fm.editType.value = 'add';
		}
		fm.submit();//提交
	}
//-->
</script>
</head>
<body>
	<form name="fm" action="/claim/externalAgency/externalagency.do?" method="post" onsubmit="return validateForm(this);">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="query.assessmentInformationSuccess" />
					:
					<%--公估机构保存成功 --%>
					<input type=text name="ComCode" class="readonly" readonly maxlength="12" style="width: 120px" value="${prplexternalagency.id.comCode}">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>