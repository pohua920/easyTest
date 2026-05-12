<%--
****************************************************************************
* DESC       ：预付通融赔款审批单打印页面
* AUTHOR     ：理赔组
* CREATEDATE ：22004-11-16
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
<%@include file="DAAPrepareIndemnityExamineCardNoneFormatPrintIni.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>预付通融赔款审批单</title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body>
	<table width="100%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr height=30>
			<td colspan="2" align="center" class="style2">
				<img src="/claim/images/LOGO.jpg" />
			</td>
		</tr>
		<tr height=30>
			<td height="35" colspan="2" align=center valign="middle" style="font-family: 宋体; font-size: 14pt;">
				<center>
					<p>
						<span class="style6"><strong>预 付 /通融 赔 款 审 批 单 </strong> </span>
					</p>
				</center>
			</td>
		</tr>
		<tr height=30>
			<td width="52%" height="22" align=left valign="bottom" style="font-family: 宋体; font-size: 10pt;">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;承保险别：<%=strRiskName%></td>
			<td width="48%" align=left valign="bottom" style="font-family: 宋体; font-size: 10pt;">
				赔案编号：
				<%=strClaimNo%></td>
		</tr>
		<tr height=30>
			<td colspan="2" align=center valign="top" style="font-family: 宋体; font-size: 10pt;">
				<table width="88%" border="1" cellpadding="0" cellspacing="0" bordercolor="111111" style="border-collapse: collapse">
					<tr>
						<td height="25" colspan="2">
							<div align="center">保单号码</div>
						</td>
						<td width="32%">
							<div align="center"><%=strPolicyNo%></div>
						</td>
						<td width="14%" height="25">
							<div align="center">被保险人</div>
						</td>
						<td width="34%">
							<div align="center"><%=strInsuredName%></div>
						</td>
					</tr>
					<tr>
						<td height="25" colspan="2">
							<div align="center">保险期限</div>
						</td>
						<td>
							<div align="center"><%=strInsuredDate%></div>
						</td>
						<td height="25">
							<div align="center">保险金额</div>
						</td>
						<td>
							<div align="center"><%=strSumAmount%></div>
						</td>
					</tr>
					<tr>
						<td height="25" colspan="2">
							<div align="center">出险日期</div>
						</td>
						<td>
							<div align="center"><%=strDamageStartDate%></div>
						</td>
						<td height="25">
							<div align="center">估赔金额</div>
						</td>
						<td>
							<div align="center"><%=strSumClaim%></div>
						</td>
					</tr>
					<tr>
						<td height="25" colspan="2">
							<div align="center">出险原因</div>
						</td>
						<td>
							<div align="center"><%=strDamageName%></div>
						</td>
						<td height="25">
							<div align="center">预付金额</div>
						</td>
						<td>
							<div align="center"><%=strSumThisPaid%></div>
						</td>
					</tr>
					<tr>
						<td width="12%" height="250" valign="middle">
							<p align="center">申</p>
							<p align="center">请</p>
							<p align="center">理</p>
							<p align="center">由</p>
							<p></p>
						</td>
						<td colspan="4" valign="top">
							<table width="99%" height="132" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td height="225" colspan="4">&nbsp;</td>
								</tr>
								<tr>
									<td width="50%" height="25">
										<div align="right">签章：</div>
									</td>
									<td width="23%" height="25">&nbsp;</td>
									<td width="10%" height="25">日期：</td>
									<td width="17%" height="25">&nbsp;</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td width="12%" height="131">
							<p align="center">分公司核</p>
							<p align="center">赔人意见</p>
							<p></p>
						</td>
						<td colspan="4" valign="top">
							<table width="99%" height="132" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td height="106" colspan="4">
										<p>&nbsp;</p>
										<p>&nbsp;</p>
									</td>
								</tr>
								<tr>
									<td width="50%" height="25">
										<div align="right">签章：</div>
									</td>
									<td width="23%" height="25">&nbsp;</td>
									<td width="10%" height="25">日期：</td>
									<td width="17%" height="25">&nbsp;</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td width="12%" height="131">
							<p align="center">总公司中</p>
							<p align="center">级核赔人</p>
							<p align="center">意见</p>
							<p></p>
						</td>
						<td colspan="4" valign="top">
							<table width="99%" height="132" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td height="106" colspan="4">
										<p>&nbsp;</p>
										<p>&nbsp;</p>
									</td>
								</tr>
								<tr>
									<td width="50%" height="25">
										<div align="right">签章：</div>
									</td>
									<td width="23%" height="25">&nbsp;</td>
									<td width="10%" height="25">日期：</td>
									<td width="17%" height="25">&nbsp;</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td width="12%" height="131">
							<p align="center">总公司首</p>
							<p align="center">席核赔人</p>
							<p align="center">意见</p>
							<p></p>
						</td>
						<td colspan="4" valign="top">
							<table width="99%" height="132" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td height="106" colspan="4">
										<p>&nbsp;</p>
										<p>&nbsp;</p>
									</td>
								</tr>
								<tr>
									<td width="50%" height="25">
										<div align="right">签章：</div>
									</td>
									<td width="23%" height="25">&nbsp;</td>
									<td width="10%" height="25">日期：</td>
									<td width="17%" height="25">&nbsp;</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<jsp:include page="/common/print/PrintButton.jsp" />
	<%--<jsp:include page="/DAA/compensate/DAASpecialPrintButton.jsp" />--%>
</body>
</html>
