<%--
****************************************************************************
* DESC       ：货运险保险拒赔/注销案件通知书打印页面
* AUTHOR     ：zhulianyu
* CREATEDATE ：2005-11-14
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%-- 初始化 --%>
<%@include file="FreightRefuseCancelNoticeNoneFormatPrintIni.jsp"%>
<html>
<link rel="stylesheet" type="text/css" href="/claim/DAA/print/StandardPrint.css">
<body bgcolor="#FFFFFF" onload="loadForm();">
	<!-- 标题部分 -->
	<table width="95%">
		<tr>
			<td width="99%">
				<table width="100%" align="center" cellspacing="0" cellpadding="0" border="0">
					<tr height="40">
						<td colspan="2" height="40" align="center" style="font-family: 宋体; font-size: 14pt;">
							<img src="/claim/images/LOGO.jpg" />
						</td>
					</tr>
					<tr height="40">
						<td colspan="2" id="tdTitleName" height="40" align="center" style="font-family: 宋体; font-size: 14pt;">
							<B>货运险保险拒赔/註銷案件通知书<B>
						</td>
					</tr>
					<tr height="30">
						<td align=left id="tdRegistNo" width="50%" style="font-family: 宋体; font-size: 10pt;">报案号：</td>
						<td align=left id="tdCaseNo" width="50%" style="font-family: 宋体; font-size: 10pt;">归档号：</td>
					</tr>
				</table>
				<!-- 主体部分 -->
				<table border=1 width="100%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
					<tr height="25">
						<td colspan=1 width="12%" align="center">被保险人</td>
						<td colspan=4 id="tdInsuredName" width="46%" align="center">&nbsp;</td>
						<td width="12%" align="center">保险金额</td>
						<td id="tdSumAmount1" width="22%" align="center">&nbsp;</td>
					</tr>
					<tr height="25">
						<td colspan=1 width="12%" align="center">保险险别</td>
						<td colspan=2 id="" width="34%" align="center">&nbsp;</td>
						<td width="12%" align="center">保险标的</td>
						<td colspan=3 id="tdLossName" width="34%" align="center">&nbsp;</td>
					</tr>
					<tr height="25">
						<td colspan=1 width="12%" align="center">保单号码</td>
						<td colspan=2 id="tdPolicyNo" width="34%" align="center">&nbsp;</td>
						<td width="12%" align="center">保险期间</td>
						<td colspan=3 id="tdInsuredDate" width="34%" align="center">&nbsp;</td>
					</tr>
					<tr height="25">
						<td width="12%" align="center">出险时间</td>
						<td colspan=2 id="tdDamageStartDate" width="34%" align="center"></td>
						<td width="12%" align="center">出险地点</td>
						<td colspan=3 id="tdDamageAddress" width="34%" align="center">&nbsp;</td>
					</tr>
					<tr height="25">
						<td colspan=2 width="32%" align="center">保险财产地址或运输工具</td>
						<td colspan=5 id="tdBLName" width="60%" align="center">&nbsp;</td>
					</tr>
					<tr height="25">
						<td colspan=2 width="32%" align="center">货物运输起讫地点</td>
						<td colspan=5 id="tdSiteName" width="60%" align="center">&nbsp;</td>
					</tr>
					<tr height="140">
						<td colspan="7" valign=top id="tdDamageCause">出险原因：</td>
					</tr>
					<tr height="140">
						<td colspan="7" valign=top id="tdCancelReason">拒赔/註銷理由：</td>
					</tr>
					<tr height="140">
						<td colspan="7" valign=top>
							附件目录：<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、<br>
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、<br>
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、<br>
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4、
						</td>
					</tr>
					<tr height="140">
						<td colspan="3" valign=top width="46%">
							特别提示：<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;被保险人如有异议，在接到本通知书後十五日内，向本公司或本公司高階主管部门提出书面复议。逾期作自动放弃处理。<br>
							<br>
							<br>
							<br>
							<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;被保险人签章：<br>
							<br>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日
						</td>
						<td colspan="4" valign=top>
							本公司地址：<%=addressCName%><br>
							<br> 邮政编码：<%=postCode%><br>
							<br> 联系电话：<%=phoneNumber%><br>
							<br>
							<br>
							<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;保险人签章：<br>
							<br>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=new SimpleDateFormat("yyyy年MM月dd日").format(new Date())%>
						</td>
					</tr>
				</table>
				<table border="0" style="font-family: 楷体_GB2312; font-size: 10pt;">
					<tr>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;注：本单由被保险人签收或保险人以挂号信邮寄。挂号信邮局回执存入卷宗。</td>
					</tr>
				</table>
			</td>
			<td width="1%" style="font-family: 楷体_GB2312; font-size: 10pt;">
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>第<br>一<br>联<br>：<br>公<br>司<br>留<br>存<br>联<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>第<br>二<br>联<br>：<br>客<br>户<br>留<br>存<br>联
			</td>
		</tr>
	</table>
	<%-- include打印按钮 --%>
	<jsp:include page="/common/print/PrintButton.jsp" />
</body>
</html>
