<html locale="true">
<!-- 页面不在使用，在影像系统中查看单证信息 -->
<head>
<title><s:text name="title.certifyBeforeEdit.viewDocumentInfo" /></title>
<%-- 查看单证信息 --%>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<%
	String[] strFileName = request.getParameterValues("FileName");
	String intSerialNo = request.getParameter("SerialNo");
	String imageName = "demo";
%>
<SCRIPT LANGUAGE="JavaScript">
	
	function big(imageName, i) {
		var old = document.all(imageName)[i].width;
		var b = 100;
		//alert(document.all.imgBtn.style.width);
		old = old + b;
		if (old > 2000)
			old = 2000;

		// var obj=document.getElementsByName(imageName)
		document.all(imageName)[i].width = old;
	}
	function small(imageName, i) {
		var old = document.all(imageName)[i].width;

		var b = 100;
		//alert(document.all.imgBtn.style.width);
		old = old - b;
		if (old < 50)
			old = 50;
		document.all(imageName)[i].width = old;
	}
	function becomebig(imagename) {
		var obj = document.getElementsByName(imagename);
		for ( var i = 0; i < obj.length; i++) {
			big(imagename, i);
		}
	}
	function becomesmall(imagename) {
		var obj = document.getElementsByName(imagename);
		for ( var i = 0; i < obj.length; i++) {
			small(imagename, i);
		}
	}
CRIPT>
</head>
<body>
	<form name=fm method="post">
		<table border="0" cellpadding="5" cellspacing="1" class="common">
			<input align="center" type="button" class=button name='addsize' value="<s:text name='button.amplificate.value' />" onclick="javascript:becomebig('<%=imageName%>');">
			<%-- 放大 --%>
			<input align="center" type="button" class=button name='decsize' value="<s:text name='button.narrow.value' />" onclick="javascript:becomesmall('<%=imageName%>');">
			<%-- 缩小 --%>
			<input align="center" type="button" class=button name='decsize' value="<s:text name='button.return.value' />" onclick="history.back();">
			<br>
			<br>
			<%for (int i = 0; i < strFileName.length; i++) {%>
			<img name='<%=imageName%>' src="<%=strFileName[i]%>">
			<br>
			<br>
			<%}%>
		</table>
	</form>
</body>
</html>
