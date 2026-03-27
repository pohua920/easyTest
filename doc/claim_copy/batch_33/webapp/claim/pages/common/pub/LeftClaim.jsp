<%--
 *************************************************************************
 * 程序名称: LeftClaim.jsp
 * 程序功能: 左边包含菜单页面
 * 最近更新人: lixiang
 * 最近更新日期: 2004-06-14 
 ****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app"%>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=gb2312">
<title></title>
<style type=text/css>
.o1 {
	position: absolute;
	background-color: #718BD6;
	font-size: 10px;
	color: #ffffff;
	cursor: hand;
}
</style>
<script language=javascript>
	function changeLeft() {
		if (parent.document.getElementsByName("fraSet").item(0).cols != "13,*") {
			parent.document.getElementsByName("fraSet").item(0).cols = "13,*";
			document.getElementById("menuSwitch").innerHTML = "<font class=o1>&#9658;</font>";
		} else {
			parent.document.getElementsByName("fraSet").item(0).cols = "21%,*";
			document.getElementById("menuSwitch").innerHTML = "<font class=o1>&#9668;</font>";
		}
	}
</script>
</head>
<body bgcolor=#F6F6F6>
	<table width=100% height=100% border=0 cellpadding=0 cellspacing=0>
		<tr>
			<td width=100%>
				<iframe id=menuleft name=menuleft style="height: 100%; width: 100%; visibility: inherit" border="0" marginwidth="0" marginheight="0" framespacing="0" FRAMEBORDER=NO
					src="/claim/common/pub/LeftMenu.jsp"></iframe>
			</td>
			<td bgcolor=#F6F6F6>
				<table width=100% height=100% border=0 cellpadding=0 cellspacing=0>
					<tr>
						<td height=22 onclick=changeLeft()></td>
					</tr>
					<tr>
						<td onclick=changeLeft() height=100% id=menuSwitch>
							<font class=o1>&#9668;</font>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>
