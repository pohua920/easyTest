<%--
****************************************************************************
* DESC       ：删除案件查询输入界面
* AUTHOR     ： 中科软
* CREATEDATE ： 2013-03-04
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta_js.jsp"%>
<html>
<head>
<title><s:text name="title.registBeforeEdit.titleName" /></title>
<%-- 公用函数 --%>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script language='javascript'>
	function submitForm(field) {
		if (fm.RegistNo.value == "") {
			errorMessage("您好：備案號碼不能為空，請輸入！");
			fm.RegistNo.focus();
			return false;
		}
		// 当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
		field.disabled = true;
		fm.submit();
	}
	
	function document.onkeydown() {
		if (event.keyCode == 13) {
			document.getElementById("button").click();
			return false;
		}
	}
</script>
</head>
<body onload="initPage();document.onkeydown();">
	<form name="fm" action="${ctx }/regist/registQuery.do" method="post" onsubmit="return validateForm(this);">
		<table class="common" style="width: 80%" border="0" align="center" cellpadding="4" cellspacing="1" bgcolor="#2D8EE1">
			<tr class=listtitle>
				<td colspan="4">
					<s:text name="title.registBeforeEdit.titleName" />
			</tr>
			<tr>
				<td class='title' style="width: 8.8%">
					<s:text name="db.prpLregist.registNo" />:
				</td>
				<td class='input' style="width: 10%">
					<input type=text name="RegistNo" maxlength='22' class="input" onblur="">
				</td>
				<td class='input' style="width: 10%">
					<input type="button" class='button' value="<s:text name="button.next.value"/>" onclick="submitForm(this);">
					<%--下一步 --%>
				</td>
			</tr>
		</table>
		<input type="hidden" name="editType" value="DELETE">
	</form>
</body>
</html>