<!--
****************************************************************************
* DESC       ：重开赔案录入赔案号页面
* AUTHOR     ： 中科软
* CREATEDATE ： 2013-03-14
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
-->
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta_js.jsp"%>
<html>
<head>
<title><s:text name="title.endcase.inputAllClaimNumber" /></title>
<!-- 录入赔案号 -->
<!-- 页面样式  -->
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<script language='javascript'>
	function submitForm(field) {
		if (fm.reCaseClaimNo.value == "") {
			alert("賠案號碼不能爲空!");
			return false;
		}
		//长度不在做控制
		//else if(trim(fm.reCaseClaimNo.value).length!=21)
		//{
		//alert("赔案号应为21位长!");
		//return false;
		//}
		else {
			// reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
			field.disabled = true;
			fm.submit();
		}
	}

	function resetForm() {
		fm.reset();
	}
</script>
</head>
<body onload="initPage();">
	<form name="fm" action="${ctx}/recase/reCaseBeforeEdit.do?type=send" method="post">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=2 class="formtitle">
					<s:text name="archive.inputClaimNumber" />
				</td>
			</tr>
			<!-- 输入赔案号 -->
			<tr>
				<td class="title2" align="center">
					<s:text name="check.claimNum" />：
				</td>
				<!-- 赔案号 -->
				<td class="input2">
					<input type=text name="reCaseClaimNo" class="common">
				</td>
			</tr>
			<tr>
				<td class="button" colspan=2 align="center">
					<input type=button class="button" class="button" value="<s:text name="button.next.value" />" onclick="submitForm(this);">
					<!-- 下一步 -->
				</td>
			</tr>
		</table>
		<input type="hidden" name="editType" value="ADD">
	</form>
</body>
</html>