<%--
****************************************************************************
* DESC       ：理赔调查报告打印
* AUTHOR     ：caopeng
* CREATEDATE ：2005-12-13
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=gb2312"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%-- 初始化 --%>
<%@include file="AcciInvesReportNoneFormatPrintIni.jsp"%>
<html>
<head>
<title>理赔调查报告列印</title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body onLoad="loadForm();">
	<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td colspan="3" height="40" align=center style="font-family: 宋体; font-size: 14pt;">
				<img src="/claim/images/LOGO.jpg" />
			</td>
		</tr>
		<tr>
			<td colspan="3" height="20" align=center style="font-family: 宋体; font-size: 14pt;">
				<B>理赔调查报告<B>
			</td>
		</tr>
		<tr>
			<td width="33%" height="20" align=center style="font-family: 宋体; font-size: 10pt;">
				<div align="left">
					报案号：<span id="spRegistNo">&nbsp;</span>
				</div>
			</td>
			<td width="35%" height="20" align=center style="font-family: 宋体; font-size: 10pt;">
				<div align="left">
					赔案号：<span id="spClaimNo">&nbsp;</span>
				</div>
			</td>
			<td width="32%" height="20" align=center style="font-family: 宋体; font-size: 10pt;">
				<div align="right">
					提交调查日期：<span id="spCheckYear">&nbsp;&nbsp;&nbsp;&nbsp;</span>年<span id="spCheckMonth">&nbsp;&nbsp;</span>月<span id="spCheckDate">&nbsp;&nbsp;</span>日
				</div>
			</td>
		</tr>
		<tr>
			<td height="20" colspan="3" align=center style="font-family: 宋体; font-size: 10pt;">
				<table width="100%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#111111" style="border-collapse: collapse; font-family: 宋体 font-size : 10pt;">
					<tr height="185">
						<td width="2%">
							<p align="center">提交调查人填写栏</p>
						</td>
						<td colspan="2" valign="top">
							<table width="100%" height="185" border="0" align="center" cellpadding="0" cellspacing="0">
								<tr align="left">
									<td height="25" colspan="2">
										&nbsp;事故者姓名：
										<!--<span id="spAcciName">&nbsp;</span>-->
									</td>
									<td colspan="2">事故结果：</td>
								</tr>
								<tr align="left">
									<td height="25" colspan="4">&nbsp;调查项目：</td>
								</tr>
								<tr>
									<td height="110" colspan="4">
										<br> <br> <br> <br> <br>
									</td>
								</tr>
								<tr>
									<td width="18%" height="25">
										<div align="right"></div>
									</td>
									<td width="18%">&nbsp;</td>
									<td width="20%" height="25">&nbsp;</td>
									<td width="44%">提交调查人：</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td height="25" colspan="3" valign="top">
							<p>&nbsp;查证途径及过程：</p>
							<br> <br> <br> <br> <br> <br> <br> <br> <br> <br> <br> <br> <br>
						</td>
					</tr>
					<tr>
						<td height="25" colspan="3" valign="top">
							<p>&nbsp;调查结论：</p>
							<br> <br> <br> <br>
						</td>
					</tr>
					<tr>
						<td height="25" colspan="3" valign="top">
							<p>&nbsp;附证明材料（注明份数）：</p>
							<br> <br> <br>
						</td>
					</tr>
					<tr valign="middle">
						<td height="25" colspan="3">
							<p>&nbsp;实际调查天数： 天</p>
						</td>
					</tr>
					<tr valign="top">
						<td height="81" colspan="2">
							<p align="left">&nbsp;如调查超时，请填写超时原因：</p>
							<div align="left">
								<br> <br>
							</div>
						</td>
						<td width="53%">
							<div align="left">&nbsp;审核人意见：</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td height="20" align=center style="font-family: 宋体; font-size: 10pt;">
				<div align="left">调查人：</div>
			</td>
			<td height="20" align=center style="font-family: 宋体; font-size: 10pt;">提交报告日期：</td>
			<td height="20" align=center style="font-family: 宋体; font-size: 10pt;">&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;月&nbsp;&nbsp;日</td>
		</tr>
	</table>
	<!--include打印按钮-->
	<jsp:include page="/common/print/PrintButton.jsp" />
</body>
</html>
