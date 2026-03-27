<%--
****************************************************************************
* DESC       ：录入单证前输入报案号页面
* AUTHOR     ： 理赔组
* CREATEDATE ： 2004-07-05
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<html locale="true">
<head>
<title><s:text name="title.certainLossBeforeEdit.entryDocument" /></title>
<%--录入单证--%>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body onload="initPage();">
	<FORM action="upload.jsp" encType=multipart/form-data method=post>
		<TABLE>
			<TBODY>
				<TR>
					<TD>
						11
						<INPUT size=60 type=file name="file">
						<INPUT type=text name="test2" value="11111">
					</TD>
				</TR>
				<TR>
					<TD>
						12
						<INPUT size=60 type=file name="file">
						<INPUT type=text name="test3" value="22222">
					</TD>
				</TR>
				<TR>
					<TD>
						13
						<INPUT size=60 type=text name="text" value="text11">
					</TD>
				</TR>
				<TR>
					<TD align=right>
						<INPUT type=submit value=Send name="send">
					</TD>
				</TR>
			</TBODY>
		</TABLE>
	</FORM>
</body>
</html>
