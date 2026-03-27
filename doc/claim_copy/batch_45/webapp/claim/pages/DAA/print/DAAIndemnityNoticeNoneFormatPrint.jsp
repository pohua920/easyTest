<%--
****************************************************************************
* DESC       ：机动车辆保险赔款通知书打印页面
* AUTHOR     ：caopeng
* CREATEDATE ：2005-12-09
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
<%@include file="DAAIndemnityNoticeNoneFormatPrintIni.jsp"%>
<html>
<head>
<title>机动车辆保险赔款通知书/收据列印</title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>
<html>
<link rel="stylesheet" type="text/css" href="/claim/DAA/print/StandardPrint.css">
<body>
	<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr height=30>
			<td colspan="2" align="center"><img src="/claim/images/LOGO.jpg" /></td>
		</tr>
		<tr height=30>
			<td colspan="2" align=center style="font-family: 宋体; font-size: 14pt;"><B>
					<center>
						<B> 赔款通知书/收据</b>
					</center>
			</b></td>
		</tr>
		<tr height=20>
			<td align=left id="tdRegistNo" width="48%" style="font-family: 宋体; font-size: 10pt;">&nbsp;</td>
			<td width="52%">&nbsp;&nbsp;&nbsp;&nbsp;<strong>赔款计算书号：<span id="spCompensateNo"><%=strCompensateNo%></span></strong></td>
		</tr>
	</table>
	<table width="92%" border="1" align="center" bordercolor="#111111" cellspacing="0" cellpadding="0">
		<tr>
			<td bordercolor="#000000"><p>&nbsp;</p>
				<table width="90%" border="0" align="center" valign="middle">
					<tr>
						<td height="40" colspan="2"><p>
								<strong>&nbsp;&nbsp;&nbsp;&nbsp;被保险人<ins>
										&nbsp;&nbsp;&nbsp;&nbsp;<span id="spInsuredName"><%=strInsuredName%></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font>
									</ins>（保单号<ins>
										&nbsp;&nbsp;<%=strPolicyNo%>&nbsp;&nbsp;
									</ins>）提出索赔申请。经本公司审核，现已结案。
								</strong>
							</p></td>
					</tr>
					<tr>
						<td height="40" colspan="2"><strong>&nbsp;&nbsp;&nbsp;&nbsp;被保险人收到 财产保险有限公司<ins>
									<font id="" height="25" width="23%">&nbsp;&nbsp;<span><%=strComCName%></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</font>
								</ins>公司签发的<ins>
									<font id="" height="25" width="23%">&nbsp;&nbsp;<span id="spClaimNo"><%=strClaimNo%></span>&nbsp;&nbsp;
									</font>
								</ins>赔案的赔款。
						</strong></td>
					</tr>
					<tr>
						<td width="61%" height="45"><strong>赔案金额人民币（大写）&nbsp;&nbsp;<span id="spCSumPaid"><%=strCSumPaid%></span></strong></td>
						<td width="39%"><div align="center">
								<strong>（￥&nbsp;&nbsp;<span id="spSumPaid"><%=strSumPaid + "&nbsp;&nbsp;元"%></span>&nbsp;&nbsp;）
								</strong>
							</div></td>
					</tr>
					<tr>
						<td height="45" colspan="2"><table width="100%" border="0">
								<tr>
									<td><p>
											<strong>收款单位银行：</strong>
										</p></td>
									<td height="50"><p>
											<strong>收款人银行帳号：</strong>
										</p></td>
								</tr>
								<tr>
									<td height="50"><strong>收款单位帳号：</strong></td>
									<td><strong>收款人身份证号：</strong></td>
								</tr>
							</table></td>
					</tr>
				</table></td>
		</tr>
	</table>
	<table width="92%" border="0" align="center">
		<tr>
			<td width="33%"><strong>签章：</strong></td>
			<td width="33%"><strong>经办：</strong></td>
			<td width="34%" align="right"><strong><span id="spYear"><%=strYear%></span>&nbsp;年&nbsp;<span id="spMonth"><%=strMonth%></span>&nbsp;月&nbsp;<span id="spDate"><%=strDate%>&nbsp;日</strong></td>
		</tr>
	</table>
	<jsp:include page="/common/print/PrintButton.jsp" />
	<%--<jsp:include page="/DAA/compensate/DAASpecialPrintButton.jsp" />--%>
</body>
</html>
