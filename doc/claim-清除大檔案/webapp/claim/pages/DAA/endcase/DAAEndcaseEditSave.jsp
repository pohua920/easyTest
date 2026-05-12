<%--
****************************************************************************
* DESC       ：结案登记录入/修改页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-06-28
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true">
<head>
<!--对title处理-->
<title><s:text name="title.endcaseBeforeEdit.editEndcase" /></title>
<!--结案登记-->
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<script language="javascript">
	function submitForm() {
		fm.submit();
	}
</script>
</head>
<body onload="initPage();" class="interface">
	<form name="fm" method="post" action="/claim/endcaseSave.do?step=step2">
		<h3>
			<s:text name="prompt.endcase.successfullySaved" />！
		</h3>
		<br>
		<!--结案登记保存成功-->
		<h3>
			<s:text name="prompt.endcase.click.next" />！
		</h3>
		<!--要生成归档号,请点击下一步按钮-->
		<hr>
		<input type=hidden name="prpLendcaseClaimNo1" value="${claimNo }">
		<input type="button" name="buttonNext" class="bigbutton" value="<s:text name='button.continueNext.value'/>" onclick="submitForm()">
		<!--继续下一步-->
		<input type="hidden" name=buttonSaveType value="1">
	</form>
</body>
</html>
