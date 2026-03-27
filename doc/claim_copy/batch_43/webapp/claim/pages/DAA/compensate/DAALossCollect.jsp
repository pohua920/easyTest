<%--
****************************************************************************
* DESC       ：实赔录入/修改页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-05-19
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta_js.jsp"%>
<html locale="true">
<head>
<!--对title处理-->
<title><s:text name="title.compensateBeforeEdit.summaryPage" /> <%-- 汇总页面 --%></title>
<app:css />
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script src="${ctx}/pages/DAA/compensate/js/DAACompensateEdit.js"></script>
<script language=javascript>
function loadForm() {
	var arrayItem = new Array();
	arrayItem = window.opener.getLoss();
	var strContent = "";
	strContent = strContent + "<table class=common cellpadding='5' cellspacing='1'>";
	strContent = strContent + "  <tr class=listtitle>";
	strContent = strContent + "    <td ><s:text name="
	regist.prpLregist.kindName " /></td>"; <%--险别名称--%>
		strContent = strContent + "    <td ><s:text name="
	db.prpVersion.projectName " /></td>"; <%--项目名称--%>
		strContent = strContent + "    <td ><s:text name="
	compensate.amountNucDamage " /></td>"; <%--核损金额--%>
		strContent = strContent + "    <td ><s:text name="
	claim.compenPay " /></td>"; <%--赔偿金额--%>
		strContent = strContent + "  </tr>";
	for (var i = 0; i < arrayItem.length; i++) {
		var trClass = "";
		if (i % 2 == 0)
			trClass = "listodd";
		else
			trClass = "listeven";
		strContent = strContent + "  <tr class=" + trClass + ">";
		strContent = strContent + "    <td>" + arrayItem[i].LossKindName + "</td>";
		strContent = strContent + "    <td>" + arrayItem[i].LossName + "</td>";
		strContent = strContent + "    <td align=right>" + point(round(arrayItem[i].LossSumLoss, 0), 0) + "</td>";
		strContent = strContent + "    <td align=right>" + point(round(arrayItem[i].LossRealPay, 0), 0) + "</td>";
		strContent = strContent + "  </tr>";
	}
	strContent = strContent + "</table>";
	divContent.innerHTML = strContent;
}
</script>
</head>
<%-- 调用loadForm 初始化页面 --%>
<body class="interface" onload="loadForm();">
	<div id=divContent></div>
	<table class=common cellpadding="5" cellspacing="1">
		<tr>
			<td align=center>
				<input type="button" value="<s:text name='button.close.value' />" name = 'button_Peril_Close_Context' class="button" alt="關閉" onclick="window.close();">
			</td>
		</tr>
	</table>
</body>
<!--这个函数是调动所能用到的通用js的过程，一般包括最常用的js的函数声明都在meta_js.jsp中-->
</html>