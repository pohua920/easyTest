<%--
****************************************************************************
* DESC       ：预赔登记录入/修改页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-05-10
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true">
<head>
<!--对title处理-->
<title><s:text name="title.prepayBeforeEdit.editPrepay" /></title>
<%-- 页面样式  --%>
<%@ include file="/common/meta_js.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<script src="${ctx }/pages/DAA/prepay/js/DAAPrepayEdit.js"></script>
</head>
<body class=interface onload="initPage();initShow();" class="interface">
	<form name=fm action="${ctx }/prepayApprove.do" method="post" onsubmit="return validateForm(this);">
		<%-- 1.预赔主信息 --%>
		<%@include file="/pages/DAA/prepay/DAAPrepayMainShow.jsp"%>
		<%-- 4.预赔备注信息 --%>
		<%@include file="/pages/DAA/prepay/DAAPrepayTextShow.jsp"%>
		<%-- 保存通用按钮 --%>
		<table cellpadding="0" cellspacing="0" width="100%" style="display:">
			<tr>
				<td align="center">
					<input type="button" class=button name="buttonApprove" value="<s:text name='button.review.value'/>" onClick="approveSubmit();">
				</td>
			</tr>
		</table>
	</form>
</body>
<!--这个函数是调动所能用到的通用js的过程，一般包括最常用的js的函数声明都在meta_js.jsp中-->
</html>
