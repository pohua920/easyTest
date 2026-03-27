<%--
****************************************************************************
* DESC       ：水险权益转让书打印
* AUTHOR     ：liping
* CREATEDATE ：2006-11-27
* MODIFYLIST ：   id       Date            Reason/Contents
    建议打印设置：上
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="java.text.*"%>
<%-- 初始化 --%>
<html>
<head>
<title>权益转让书</title>
<link rel="stylesheet" type="text/css" href="Standard.css">
<style type="text/css">
<!--
.line1 {
	font-size: 10px;
	letter-spacing: 2px;
	color: #000000;
}
-->
</style>
</head>
<body bgcolor="#FFFFFF">
	<!-- 标题部分 -->
	<table width="85%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td height="40" align="center">
				<Img src="/claim/images/LOGO.jpg" />
			</td>
		</tr>
		<tr>
			<td>&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<td colspan="4" height="42" align=center>
				<b><font face="宋体" style="font-size: 小三" size="4">赔 款 收 据 和 权 益 转 让 书</font></b>
			</td>
		</tr>
		<tr>
			<td align="center">
				<u> <span style="font-size: 五号; font-family: Times New Roman" lang="EN-US">RECEIPT&nbsp; AND&nbsp; SUBROGATION&nbsp; FORM</span></u>
			</td>
		</tr>
		<tr>
			<td></td>
		</tr>
	</table>
	<div align="center">
		<table width="85%" cellspacing="0" cellpadding="0" border="0">
			<tr>
				<td align=left width=50%>
					<font style="font-size: 小四" face="Times New Roman">Loss No</font><font class="line1">______________________________</font>
				</td>
				<td align=left width="50%">
					<font style="font-size: 小四" face="Times New Roman">Policy/Certificate</font><font class="line1">__________________</font>
				</td>
			</tr>
			<tr>
				<td></td>
				<td cospan="4" align=left>
					<font style="font-size: 小四" face="Times New Roman">Insured Amount</font><font class="line1">___________________</font>
				</td>
			</tr>
			<tr>
				<td colspan=2></td>
			</tr>
			<tr>
				<td colspan=2></td>
			</tr>
			<tr>
				<td colspan="2" align=left width="650">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="font-size: 小四"><font face="Times New Roman"> To:China Life Property and Casualty Insurance Company Limited</font> </font>
				</td>
			</tr>
			<tr>
				<td colspan=2 align=left>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font face="Times New Roman"> <font style="font-size: 小四">Received from China Life Property and Casualty Insurance Company </font></font>
				</td>
			</tr>
			<tr>
				<td colspan=2 align=left>
					<font style="font-size: 小四" face="Times New Roman">Limited</font><font face="Times New Roman"> the sum of </font> <font class="line1">________________________________________________________</font>
				</td>
			</tr>
			<tr>
				<td colspan=2 align=left>
					<font class="line1">__________________________________________________________________________</font><font face="Times New Roman" style="font-size: 小四">&nbsp;</font>
				</td>
			</tr>
			<tr>
				<td colspan=2 align=left>
					<font style="font-size: 小四"><font face="Times New Roman">(&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;) in full and
							final settlement of the claim under the above mentioned</font> </font>
				</td>
			</tr>
			<tr>
				<td colspan=2>
					<font style="font-size: 小四" face="Times New Roman">Policy/Certificate on</font><font class="line1">_______________________________________________________</font>
				</td>
			</tr>
			<tr>
				<td colspan=2>
					<font class="line1">__________________________________________________________________________</font><font face="Times New Roman" style="font-size: 小四">&nbsp;</font>
				</td>
			</tr>
			<tr>
				<td colspan=2>
					<font style="font-size: 小四" face="Times New Roman">shipped per s/s</font><font class="line1">____________________________________________________________</font>
				</td>
			</tr>
			<tr>
				<td colspan=2>
					<font style="font-size: 小四" face="Times New Roman">from</font><font class="line1">________________________________________</font><font style="font-size: 小四" face="Times New Roman">to</font><font
						class="line1">____________________________</font>
				</td>
			</tr>
			<tr>
				<td colspan=2 height="17"></td>
			</tr>
			<tr>
				<td colspan=2></td>
			</tr>
			<tr>
				<td colspan=2 width="505" align="justify">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font face="Times New Roman"> </font> <font style="font-size: 小四" face="Times New Roman">In consideration of having received this payment,
						we hereby agree to assign, ransfer and subrogate to you, to the extent of your interest, all our rights and remedies in and in respect of the subject matter insured, and to grant you full power
						and give you any assistance you may reasonably require of us in the exercise of such rights and remedies in our or your name and at your own expense. </font>
				</td>
			</tr>
			<tr>
				<td colspan=2>
					<font style="font-size: 小四" face="Times New Roman">Dated at</font><font class="line1">____________________</font><font style="font-size: 小四" face="Times New Roman">this</font><font class="line1">______________________</font><font
						style="font-size: 小四" face="Times New Roman">day of</font><font class="line1">_______________</font>
				</td>
			</tr>
			<tr>
				<td colspan=2></td>
			</tr>
			<tr>
				<td></td>
				<td>
					<font style="font-size: 小四" face="Times New Roman">Signed</font><font class="line1">___________________________</font>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					&nbsp;&nbsp;&nbsp;&nbsp;<font style="font-size: 小四" face="Times New Roman">Stamp</font>
				</td>
			</tr>
			<tr>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td colspan=2>
					<font style="font-size: 小四" face="Times New Roman">N.B. This document must bear the Legal</font>
				</td>
			</tr>
			<tr>
				<td colspan=2>
					&nbsp;&nbsp;&nbsp;<font style="font-size: 小四" face="Times New Roman">&nbsp;Stamp necessary for Agreement</font>
				</td>
			</tr>
		</table>
	</div>
	<jsp:include page="/common/print/PrintButton.jsp" />
</body>
</html>