<%--
****************************************************************************
* DESC       ：原始单证粘贴页打印页面
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
<%@include file="DAAOriginNoteCardNoneFormatPrintIni.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>原始单证粘贴页</title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body>
	<table width="642" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: '宋体'; font-size: 14pt">
		<tr height=30>
			<td colspan="2" align="center">
				<img src="/claim/images/LOGO.jpg" />
			</td>
		</tr>
		<tr height=30>
			<td height="35" colspan="2" align=center valign="middle" style="font-family: 宋体; font-size: 14pt;">
				<center>
					<p>
						<span class="style6"><strong>原始单证粘贴页 </strong></span>
					</p>
				</center>
			</td>
		</tr>
		<tr height=30>
			<td colspan="2" align=center valign="top" style="font-family: 宋体; font-size: 10pt;">
				赔案号：<%=request.getParameter("ClaimNo")%></td>
		</tr>
		<tr height=30>
			<td colspan="2" align=center valign="top" style="font-family: 宋体; font-size: 10pt;">
				<table width="93%" height="80%" border="1" cellpadding="2" cellspacing="0" bordercolor="#111111" style="border-collapse: collapse; font-family: '宋体'; font-size: 10pt">
					<tr>
						<td colspan="6" align="left" valign="top">
							<p></p>
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
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
						</td>
					</tr>
					<tr>
						<td height="25" colspan="3" align="left" valign="top">
							<div align="center">索赔材料</div>
						</td>
						<td height="25" colspan="2" align="left" valign="top">
							<div align="center">审核结果</div>
						</td>
						<td width="16%" height="25" rowspan="2" align="left" valign="middle">
							<div align="center">备注</div>
						</td>
					</tr>
					<tr>
						<td width="20%" height="25" align="left" valign="top">
							<div align="center">名称</div>
						</td>
						<td width="12%" height="25" align="left" valign="top">
							<div align="center">份数</div>
						</td>
						<td width="18%" height="25" align="left" valign="top">
							<div align="center">金额</div>
						</td>
						<td width="16%" height="25" align="left" valign="top">
							<div align="center">应赔份数</div>
						</td>
						<td width="18%" height="25" align="left" valign="top">
							<div align="center">金额</div>
						</td>
					</tr>
					<tr>
						<td height="25" align="left" valign="top">&nbsp;</td>
						<td height="25" align="left" valign="top">&nbsp;</td>
						<td height="25" align="left" valign="top">&nbsp;</td>
						<td height="25" align="left" valign="top">&nbsp;</td>
						<td height="25" align="left" valign="top">&nbsp;</td>
						<td height="25" rowspan="5" align="left" valign="top">&nbsp;</td>
					</tr>
					<tr>
						<td height="25" align="left" valign="top">&nbsp;</td>
						<td height="25" align="left" valign="top">&nbsp;</td>
						<td height="25" align="left" valign="top">&nbsp;</td>
						<td height="25" align="left" valign="top">&nbsp;</td>
						<td height="25" align="left" valign="top">&nbsp;</td>
					</tr>
					<tr>
						<td height="25" align="left" valign="top">&nbsp;</td>
						<td height="25" align="left" valign="top">&nbsp;</td>
						<td height="25" align="left" valign="top">&nbsp;</td>
						<td height="25" align="left" valign="top">&nbsp;</td>
						<td height="25" align="left" valign="top">&nbsp;</td>
					</tr>
					<tr>
						<td height="25" align="left" valign="top">&nbsp;</td>
						<td height="25" align="left" valign="top">&nbsp;</td>
						<td height="25" align="left" valign="top">&nbsp;</td>
						<td height="25" align="left" valign="top">&nbsp;</td>
						<td height="25" align="left" valign="top">&nbsp;</td>
					</tr>
					<tr>
						<td height="25" align="left" valign="top">
							<div align="center">核赔金额</div>
						</td>
						<td height="25" colspan="2" align="left" valign="top">&nbsp;</td>
						<td height="25" colspan="2" align="left" valign="top">&nbsp;</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr height=30>
			<td width="50%" align=center valign="top" style="font-family: 宋体; font-size: 10pt;">
				<div align="left">&nbsp;&nbsp;&nbsp;经办人</div>
			</td>
			<td width="50%" align=center valign="top" style="font-family: 宋体; font-size: 10pt;">
				<div align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;月&nbsp;&nbsp;日&nbsp;&nbsp;&nbsp;</div>
			</td>
		</tr>
	</table>
	<jsp:include page="/common/print/PrintButton.jsp" />
	<%--<jsp:include page="/DAA/compensate/DAASpecialPrintButton.jsp" />--%>
</body>
</html>
