<%--
****************************************************************************
* DESC       ：事故照片粘贴单打印页面
* AUTHOR     ：caopeng
* CREATEDATE ：2005-12-12
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=gb2312"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%-- 初始化 --%>
<%@include file="DAAAccidentPhotoCardNoneFormatPrintIni.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title><s:text name="title.printBeforeEdit.picSingAccident" /></title>
<%-- 事故照片粘贴单 --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body>
	<table width="642" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: '宋体'; font-size: 14pt">
		<tr height=30>
			<td width="92%" align="center"><img src="/claim/images/LOGO.jpg" /></td>
		</tr>
		<tr height=30>
			<td height="35" align=center valign="middle" style="font-family: 宋体; font-size: 14pt;">
				<center>
					<p>
						<span class="style6"><strong><s:text name="print.picSingAccident" /> </strong> </span>
					</p>
					<%-- 事故照片粘贴单 --%>
				</center>
			</td>
		</tr>
		<tr height=30>
			<td align=center valign="top" style="font-family: 宋体; font-size: 10pt;"><s:text name="certainLoss.claims" />：<%=request.getParameter("ClaimNo")%></td>
			<%-- 赔案号 --%>
		</tr>
		<tr height=30>
			<td align=center valign="top" style="font-family: 宋体; font-size: 10pt;">
				<table width="93%" height="80%" border="1" cellpadding="2" cellspacing="0" bordercolor="#111111" style="border-collapse: collapse; font-family: '宋体'; font-size: 10pt">
					<tr>
						<td width="69%" align="left" valign="top"><p>
								&nbsp;
								<s:text name="print.picture" />：
							</p> <%-- 照片 --%>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p></td>
						<td width="31%" align="left" valign="top"><p>
								&nbsp;
								<s:text name="certify.instructe" />：
							</p> <%-- 说明 --%>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p></td>
					</tr>
					<tr>
						<td align="left" valign="top"><p>
								&nbsp;
								<s:text name="print.picture" />：
							</p> <%-- 照片 --%>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p></td>
						<td align="left" valign="top"><p>
								&nbsp;
								<s:text name="certify.instructe" />：
							</p> <%-- 说明 --%>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<jsp:include page="/common/print/PrintButton.jsp" />
	<%--<jsp:include page="/DAA/compensate/DAASpecialPrintButton.jsp" />--%>
</body>
</html>
