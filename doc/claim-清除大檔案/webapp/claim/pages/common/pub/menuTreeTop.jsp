
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="css/text1.css" type="text/css">
<script language="JavaScript" type="text/javascript">
	var l = 0;
	var menuTreeStation = false;
	
	function control() {
		//l = (l == 0) ? l : 0;
		menuTreeStation = !menuTreeStation;
		top.fraAddress.document.all("openMenuTD").style.display = menuTreeStation ? "" : "none";
		//document.all("menuTreeTitle").style.display = menuTreeStation ? "none" : "";
		//document.all("menuTreeHid").style.display = menuTreeStation ? "none" : "";
		//top.menuTreeFrame.menuTreeBody.document.all("menuTreeTD").style.display = menuTreeStation ? "none" : "";
		top.document.all.menuTreeFrame.noResize = menuTreeStation;
		l = menuTreeStation ? 180 : 0;
	}
	
	top.document.all.bottomFrame.noResize = true;
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" background="images/bj3.gif">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#F0F7F2" background="images/bj3.gif">
		<tr>
			<td width="9" background="images/l_left_1.gif">
				<img src="images/l_left_1.gif" width="9" height="5">
			</td>
			<td id="menuTreeTitle" height="25" background="images/mtop_2.gif" class="dh" align="center" nowrap>
				<s:text name="pub.functionMenu" />
				<%--功 能 菜 单--%>
			</td>
			<td id="menuTreeHid" width="1%" height="25" background="images/mtop_2.gif" class="dh" align="right" nowrap valign="middle">
				<img src="images/menuHid.gif" onClick="control();" alt="隐藏功能選單" style="cursor: 'hand'">&nbsp;
			</td>
		</tr>
	</table>
</body>
</html>
