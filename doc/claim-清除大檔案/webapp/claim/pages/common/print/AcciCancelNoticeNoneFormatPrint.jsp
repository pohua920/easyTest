<%--

****************************************************************************

* DESC       ：意健险拒赔通知书

* AUTHOR     ：luqin

* CREATEDATE ：2005-06-19

* MODIFYLIST ：   Name       Date            Reason/Contents

*          ------------------------------------------------------

****************************************************************************

--%>
<%@ page contentType="text/html; charset=GBK"%>
<%-- 初始化 --%>
<%@include file="AcciCancelNoticeNoneFormatPrintIni.jsp"%>
<html>
<body onload="loadForm();">
	<p></p>
	<div align="center">
		<center>
			<table border="0" cellpadding="0" cellspacing="0" width="92%">
				<tr>
					<td width="100%">
						<p align="center">
							<b><font size="6">拒赔通知书</font></b>
						</p>
						<p></p>
						<div align="center">
							<center>
								<table border="0" cellpadding="0" cellspacing="0" width="92%" height="46">
									<tr>
										<td width="33%" height="46"></td>
										<td width="33%" height="46"></td>
										<td width="34%" height="46" id="tdClaimNo"></td>
									</tr>
								</table>
							</center>
						</div>
						<p>
							&nbsp;&nbsp;&nbsp; 兹有客户（姓名：
							<ins><%=strInsuredName%></ins>
							&nbsp;&nbsp;身份证号：
							<ins><%=strIdentifyNumber%></ins>
							）因（
							<ins><%=strDamageName%></ins>
							）提出理赔申请，经本公司审核，不属於本保险合約责任。
						</p>
						<p></p>
						<p></p>
						<p>此复</p>
						<p></p>
						<p>&nbsp;&nbsp;</p>
						<div align="center">
							<center>
								<table border="0" cellpadding="0" cellspacing="0" width="92%" height="46">
									<tr>
										<td width="33%" height="46" id="tdHandlerName"></td>
										<td width="33%" height="46">签批人：</td>
										<td width="34%" height="46"></td>
									</tr>
								</table>
							</center>
						</div>
						<p></p>
						<p></p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;</p>
						<div align="center">
							<center>
								<table border="0" cellpadding="0" cellspacing="0" width="92%" height="86">
									<tr>
										<td width="50%" height="44"></td>
										<td width="50%" height="44">单位签章</td>
									</tr>
									<tr>
										<td width="50%" height="42"></td>
										<td width="50%" height="42"><%=strDate%></td>
									</tr>
								</table>
							</center>
						</div>
					</td>
				</tr>
			</table>
		</center>
	</div>
</body>
</html>