<%--
****************************************************************************
* DESC       ：非水权益转让书打印
* AUTHOR     ：liping
* CREATEDATE ：2006-11-23
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
			<td colspan="4" height="40" align=top align=center style="text-align: center; font-family: 宋体; font-size: 14pt;">
				<B><font face="仿宋_GB2312">权益转让书</font>
			</td>
		</tr>
		<tr>
			<td></td>
		</tr>
		<tr>
			<td></td>
		</tr>
		<tr align="center">
			<td>
				<table>
					<tr>
						<td cospan="4" align=left width=600 style="table-layout: fixed; word-wrap: break-word; word-break: break-all; line-height: 36px">
							&nbsp;&nbsp;&nbsp;&nbsp;<font face="仿宋_GB2312" style="font-size: 14pt">兹由我单位（本人）在贵公司投保的<u><bean:write name="interestsTransferReportPrintDto" property="policyNo" /></u>号保险单项下的保险标的於<u><bean:write
										name="interestsTransferReportPrintDto" property="damageYear" /></u>年<u><bean:write name="interestsTransferReportPrintDto" property="damageMonth" /></u>月<u><bean:write
										name="interestsTransferReportPrintDto" property="damageDay" /></u>日在<u><bean:write name="interestsTransferReportPrintDto" property="damageAddress" /></u>因<u><bean:write
										name="interestsTransferReportPrintDto" property="damageCause" /></u>出险一案，已由贵公司赔偿人民币（￥<u><bean:write name="interestsTransferReportPrintDto" format="0.00" property="sumPaid" /></u>）。根据保险条款规定，贵公司对该案的一切赔偿责任已经终止。鉴於此，我单位（本人）声明将已取得赔款部分的保险标的的一切权益（含残值，若有残值委付时）转让给贵公司，並确认贵公司可直接使用自己名义或使用我单位（本人）的名义向责任方采取法律或其它措施以行使这些权利或获得相应补偿。
							</font>
						</td>
					</tr>
					<tr>
						<td cospan="4" align=left width=600 style="table-layout: fixed; word-wrap: break-word; word-break: break-all; line-height: 36px">
							&nbsp;&nbsp;&nbsp;&nbsp;<font face="仿宋_GB2312" style="font-size: 14pt">为使贵公司实现该项权益，我单位（本人）保证将根据贵公司的合理要求提供充分协助。</font>
						</td>
					</tr>
					<tr>
						<td cospan="2" align=center style="table-layout: fixed; word-wrap: break-word; word-break: break-all; line-height: 72px">
							<font face="仿宋_GB2312" style="font-size: 14pt">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;权益转让人签章： </font>
						</td>
					</tr>
					<tr>
						<td cospan="2" align=center style="table-layout: fixed; word-wrap: break-word; word-break: break-all; line-height: 36px">
							<font face="仿宋_GB2312" style="font-size: 14pt">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;日 </font>
						</td>
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<jsp:include page="/common/print/PrintButton.jsp" />
</body>
</html>