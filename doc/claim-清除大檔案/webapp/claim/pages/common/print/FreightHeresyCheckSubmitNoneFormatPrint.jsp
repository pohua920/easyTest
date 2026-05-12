<%--
****************************************************************************
* DESC       ：货物运输保险代查勘委托书打印页面
* AUTHOR     ：zhuly
* CREATEDATE ：2005-11-15
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%-- 初始化 --%>
<%@include file="FreightHeresyCheckSubmitNoneFormatPrintIni.jsp"%>
<html>
<head>
<title>货物运输保险代查勘委托书列印</title>
<link rel="stylesheet" type="text/css" href="Standard.css">
</head>
<body bgcolor="#FFFFFF" onload="loadForm();">
	<!-- 标题部分 -->
	<table width="95%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td width="2%"></td>
			<td width="82%" align="left">
				<table width="100%" align="center" cellspacing="0" cellpadding="0" border="0">
					<tr height="40">
						<td colspan="2" height="40" align="center" style="font-family: 宋体; font-size: 14pt;">
							<img src="/claim/images/LOGO.jpg" />
						</td>
					</tr>
					<tr>
						<td colspan="2" height="40" align=top align=center style="text-align: center; font-family: 宋体; font-size: 13pt;">
							<B>货物运输保险代查勘委托书<B>
						</td>
					</tr>
					<tr>
						<td width="50%"></td>
						<td align="left" style="text-align: center; font-family: 宋体; font-size: 10pt;">委托单号：</td>
					<tr>
						<td height="30" style="text-align: left; font-family: 宋体; font-size: 10pt;">
							<ins>
								&nbsp;<%=strMakeCom%>&nbsp;
							</ins>
							公司（商检）:
						</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td colspan="2" height="50" style="text-align: left; font-family: 宋体; font-size: 10pt;">
							&nbsp;&nbsp;&nbsp;&nbsp;本公司於
							<ins>
								&nbsp;<%=ReportDate%>&nbsp;
							</ins>
							接到被保险人
							<ins>
								&nbsp;<%=strReportorName%>&nbsp;
							</ins>
							报案，得悉本公司承保的
							<ins>
								&nbsp;<%=strLossName%>&nbsp;
							</ins>
							租货物於
							<ins>
								&nbsp;<%=strDamageStartDate%>&nbsp;
							</ins>
							在
							<ins>
								&nbsp;<%=strDamageAddress%>&nbsp;
							</ins>
							发生事故。<br>
							<br>
							<br>
						</td>
					</tr>
					<tr>
						<td align="left" style="text-align: left; font-family: 宋体; font-size: 10pt;">标的保险内容：</td>
					</tr>
				</table>
				<table border=1 width="100%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
					<tr height="25">
						<td colspan="1" width="10%" align="center">被保险人</td>
						<td colspan="1" width="15%" id="tdInsuredName" align="center">&nbsp;</td>
						<td colspan="1" width="15%" align="center">保险单号码</td>
						<td colspan="1" width="15%" id="tdPolicyNo" align="center">&nbsp;</td>
						<td colspan="1" width="10%" align="center">险&nbsp;&nbsp;&nbsp;&nbsp;别</td>
						<td colspan="1" width="15%" id="" align="center">&nbsp;</td>
					</tr>
					<tr height="25">
						<td colspan="1" align="center">运输工具</td>
						<td colspan="1" id="tdBLName" align="center">&nbsp;</td>
						<td colspan="1" align="center">牌&nbsp;&nbsp;&nbsp;&nbsp;号</td>
						<td colspan="3" id="tdBLNo" align="center">&nbsp;</td>
					</tr>
					<tr height="25">
						<td colspan="1" align="center">货物名称</td>
						<td colspan="1" id="tdLossName" align="center">&nbsp;</td>
						<td colspan="1" align="center">货物数量</td>
						<td colspan="1" id="tdLossQuantity" align="center">&nbsp;</td>
						<td colspan="1" align="center">运单号码</td>
						<td colspan="1" id="tdCarryBillNo" align="center">&nbsp;</td>
					</tr>
					<tr height="25">
						<td colspan="1" align="center">保险金额</td>
						<td colspan="1" id="tdSumAmount" align="center">&nbsp;</td>
						<td colspan="1" align="center">目 的 地</td>
						<td colspan="1" id="tdEndSiteName" align="center">&nbsp;</td>
						<td colspan="1" align="center">起运日期</td>
						<td colspan="1" align="right" id="tdSailStartDate" align="center">年 月 日</td>
					</tr>
				</table>
				<table width="100%" align="left" cellspacing="0" cellpadding="2" border="0" style="font-family: 宋体; font-size: 10pt;">
					<tr>
						<td colspan="2" align="left">
							<br> 现委托你公司办如下事宜：<br> &nbsp;&nbsp;&nbsp;□现场查勘、调查出险原因並出具查勘报告<br> &nbsp;&nbsp;&nbsp;□拍摄事故现场及标的损失照片<br> &nbsp;&nbsp;&nbsp;□标的验损定价<br>
							&nbsp;&nbsp;&nbsp;如果估损金额超过&nbsp;&nbsp;&nbsp;&nbsp;万元，请书面通知本公司，得到答复後再作处理。 <br> &nbsp;&nbsp;&nbsp;请将上述资料及代查勘收据邮寄或由被保险人转交本公司。方框内打"√"为委托事项。<br>
							<br> 致谢！<br>
							<br>
							<br>
							<br>
							<br>
							<br>
							<br>
							<br>
							<br> <br>
							<br>
						</td>
					</tr>
					<tr height="30">
						<td>&nbsp;</td>
						<td nowrap>
							委托公司:<%=comCName%>
							&nbsp;&nbsp;&nbsp;&nbsp; 公司（章）
						</td>
					</tr>
					<tr height="30">
						
						<td width="50%">&nbsp;</td>
						<td>
							地 址:<%=addressCName%></td>
					</tr>
					<tr height="30">
						<td align="left">
							被保险人联系电话:<%=strPhoneNumber%></td>
						<td>
							邮政编码:<%=postCode%></td>
					</tr>
					<tr height="30">
						
						<td align="left"></td>
						<td>
							传 真:<%=faxNumber%></td>
					</tr>
					<tr height="30">
						
						<td>
							联系人：<%=strLinkerName%></td>
						<td>
							联系电话:<%=phoneNumber%></td>
					<tr height="30">
						<td>&nbsp;</td>
						<td>
							联&nbsp;系&nbsp;人:<%=manager%></td>
					</tr>
					<tr height="30">
						<td>&nbsp;</td>
						<td>
							委托日期
							<ins>
								&nbsp;<%=new SimpleDateFormat("yyyy年MM月dd日").format(new Date())%>&nbsp;
							</ins>
						</td>
					</tr>
				</table>
			</td>
			<td width="3%" style="font-family: 宋体; font-size: 10pt;" valign="top" align="center">
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>第<br>一<br>联<br>：<br>送<br>代<br>查<br>勘<br>单<br>位
			</td>
			<td width="3%" style="font-family: 宋体; font-size: 10pt;" valign="top">
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>第<br>二<br>联<br>：<br>委<br>托<br>公<br>司<br>留<br>存
			</td>
		</tr>
	</table>
	<!-- 按钮部分 -->
	<%-- include打印按钮 --%>
	<jsp:include page="/common/print/PrintButton.jsp" />
	<script language='javascript'>
		function printPage() {
			//add print liudaoping 2013-04-15
			//alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
			return false;
			divButton.style.display = "none";
			window.print();
		}
	</script>
</body>
</html>
