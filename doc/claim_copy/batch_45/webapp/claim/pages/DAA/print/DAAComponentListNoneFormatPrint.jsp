<%--
****************************************************************************
* DESC       ：机动车辆保险损失情况确认书零部件更换项目清单(代询价单附件)列印页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-11-16
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
*　modify by zhyi 20110831 fuon-2206
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@include file="DAALossNoneFormatPrintIni.jsp"%>
<html>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<link rel="stylesheet" type="text/css" href="/claim/DAA/print/StandardPrint.css">
<body bgcolor="#FFFFFF" onload="loadForm();">
	<script language='javascript'>
		function printPage() {
			//add print liudaoping 2013-04-15
			//alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
			return false;
			tbButton.style.display = "none";
			window.print();
		}
	</script>
	<%
		if (intComponentCount < 17) {
			strMessage = "抱歉!更换项目未超过16项,不需列印清单附页,请您列印清单。";
			//System.out.println(strMessage);
	%>
	<jsp:include page="/common/pub/UIErrorPage.jsp">
		<jsp:param name="Picture" value="F" />
		<jsp:param name="Content" value="<%=strMessage%>" />
	</jsp:include>
	<%
		return;
		}
	%>
	<%
		int intPageNum = 1;
		int intPage = 2;
		if (count5 > 2 || count4 > 2 || count3 > 2 || count2 > 1 || count1 > 2) {
			intPageNum = intComponentCount < 17 ? 2 : ((intComponentCount - 1) / pageCount + 3);
			intPage = 3;
		} else {
			intPageNum = intComponentCount < 17 ? 1 : ((intComponentCount - 1) / pageCount + 2);
			intPage = 2;
		}
	%>
	<%
		int size = changeListInfo.size();
		int strTimes = (size - 1) / pageCount + 1;
		for (int m = 0; m < strTimes; m++) {
	%>
	<!-- 标题部分 -->
	<table width="90%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr height=30>
			<td colspan="3" align="center">
				<img src="/claim/images/LOGO.jpg" />
			</td>
		</tr>
		<tr height=30>
			<td colspan="3" align=centerstyle="font-family:宋体; font-size: 14pt;">
				<B><center>
						机动车辆保险车辆损失情况确认书<br> <span>零配件更换项目清单</span>
					</center> <B>
			</td>
		</tr>
		<tr height=20>
			<td colspan="3" style="font-family: 宋体; font-size: 10pt;">
				被保险人：<%=strInsuredName%></td>
		</tr>
		<tr height=20>
			<td align=left width="40%" id="tdInsuredName" style="font-family: 宋体; font-size: 10pt;">
				报案号：<%=strRegistNo%></td>
			<td align=left id="tdRegistNo" width="35%" style="font-family: 宋体; font-size: 10pt;">
				交强险承保公司：<%=strRemark%></td>
			<td width="25%" align=right style="font-family: 宋体; font-size: 10pt;">
				共&nbsp;<%=intPageNum%>&nbsp;页&nbsp;&nbsp;&nbsp;第&nbsp;<%=m + intPage%>&nbsp;页
			</td>
		</tr>
	</table>
	<!-- 主体部分 -->
	<table border=1 width="100%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
		<tr>
			<td height=20 colspan="1" width="12%" align="center">牌照号码</td>
			<td height=20 colspan="2" width="22%" align="center"><%=licenseNo%></td>
			<td height=20 colspan="1" width="14%" align="center">保单号码</td>
			<td height=20 colspan="4" align="center"><%=strPolicyNo%></td>
		</tr>
		<tr>
			<td height=20 colspan="1" align="center">发动机号</td>
			<td height=20 colspan="2" align="center"><%=engineNo%></td>
			<td height=20 colspan="1" align="center">车架号</td>
			<td height=20 colspan="4" align="center"><%=prpItemcarDto.getFrameNo()%></td>
		</tr>
		<tr>
			<td height=20 colspan="1" align="center">厂牌型号</td>
			<td height=20 colspan="2" align="center"><%=brandName%></td>
			<td width="12%" height=20 colspan="1" align="center">出险时间</td>
			<td height=20 colspan="1" width="17%" align="center"><%=strDamageStartDate%></td>
			<td width="15%" height=20 align="center">保险险别</td>
			<td height=20 colspan="2" align="center"><%=kindName%></td>
		</tr>
		<tr height=20>
			<td height=20 colspan="1" align="center">生产日期</td>
			<td height=20 colspan="2" align="center"><%=strEnrollDate%></td>
			<td height=20 colspan="1" align="center">排 气 量(L)</td>
			<td height=20 colspan="1" align="center"><%=douExhaustScale%></td>
			<td height=20 align="center">变速箱形式</td>
			<td height=20 colspan="2" align="center">□ 手动档 □ 自动档</td>
		</tr>
		<tr height=20>
			<td height=20 colspan="1" align="center">发动机形式</td>
			<td height=20 colspan="2" align="center">□ 化油器 □ 电喷</td>
			<td height=25 colspan="1" align="center">安全装置</td>
			<td height=25 colspan="4" align="center">□安全气囊 &nbsp;&nbsp; □ABS系统 &nbsp;&nbsp; □无安全装置</td>
		</tr>
	</table>
	<table border="1" width="100%" align="center" cellspacing="0" cellpadding="0" style="border-collapse: collapse; font-family: 宋体; font-size: 9pt;" bordercolor="#111111">
		<tr>
			<td colspan="4" width="100%" valign="top">
				<table border="1" frame=void width="100%" align="center" cellspacing="0" cellpadding="0" style="font-family: 宋体; font-size: 11pt; border-collapse: collapse" bordercolor="#111111">
					<tr height=20 bgcolor="#CCCCCC">
						<td height=20 colspan="1" width="5%" align="center">序号</td>
						<td height=20 colspan="1" width="22.5%" align="center">更换配件名称</td>
						<td height=20 colspan="1" width="10%" align="center">数量</td>
						<td height=20 colspan="1" width="12.5%" align="center">配件价格</td>
						<td height=20 colspan="1" width="5%" align="center">序号</td>
						<td height=20 colspan="1" width="22.5%" align="center">更换配件名称</td>
						<td height=20 colspan="1" width="10%" align="center">数量</td>
						<td height=20 colspan="1" width="12.5%" align="center">配件价格</td>
					</tr>
					<%
						if (changeListInfo == null || changeListInfo.size() == 0) {
					%>
					<%
						}
							int intTemp = 0;
							for (int i = m * pageCount; i < (intBaseCount + (m * pageCount)); i++) {
					%>
					<%
						if (i < changeListInfo.size()) {
									intTemp = i + intBaseCount;
					%>
					<tr height=20>
						<td align="center" height=20><%=i + 1%></td>
						<td align="center" height=20><%=((RepairContentDto) changeListInfo.get(i)).getChangeName()%></td>
						<td align="center" height=20><%=new DecimalFormat("#,##0").format(((RepairContentDto) changeListInfo.get(i)).getChangeCount())%></td>
						<td align="center" height=20><%=new DecimalFormat("#,##0.00").format(((RepairContentDto) changeListInfo.get(i)).getChangeFee())%></td>
						<!--  <td width="15%" align="center"><%=new DecimalFormat("#,##0.00").format((((RepairContentDto) changeListInfo.get(i)).getChangeFee()) * (((RepairContentDto) changeListInfo.get(i)).getChangeCount()))%></td>-->
						<%
							if (intTemp < changeListInfo.size()) {
											if (changeListInfo.get(intTemp) != null && ((RepairContentDto) changeListInfo.get(intTemp)).getChangeName().equals("")) {
						%>
						<td height=20 colspan="1" align="center"></td>
						<td height=20 colspan="1" align="center"></td>
						<td height=20 colspan="1" align="center"></td>
						<td height=20 colspan="1" align="center"></td>
						<%
							} else {
						%>
						<td align="center" height=20><%=(i + intBaseCount + 1)%></td>
						<td align="center" height=20><%=((RepairContentDto) changeListInfo.get(intTemp)).getChangeName()%></td>
						<td align="center" height=20><%=new DecimalFormat("#,##0").format(((RepairContentDto) changeListInfo.get(intTemp)).getChangeCount())%></td>
						<td align="center" height=20><%=new DecimalFormat("#,##0.00").format(((RepairContentDto) changeListInfo.get(intTemp)).getChangeFee())%></td>
						<!--  <td width="15%" align="center"><%=new DecimalFormat("#,##0.00").format((((RepairContentDto) changeListInfo.get(intTemp)).getChangeFee()) * (((RepairContentDto) changeListInfo.get(intTemp)).getChangeCount()))%></td>-->
						<%
							}
										} else {
						%>
						<td height=20 colspan="1" align="center"></td>
						<td height=20 colspan="1" align="center"></td>
						<td height=20 colspan="1" align="center"></td>
						<td height=20 colspan="1" align="center"></td>
						<%
							}
						%>
					</tr>
					<%
						} else {
					%>
					<tr height=20>
						<td height=20 colspan="1" align="center">&nbsp;</td>
						<td height=20 colspan="1" align="center">&nbsp;</td>
						<td height=20 colspan="1" align="center">&nbsp;</td>
						<td height=20 colspan="1" align="center">&nbsp;</td>
						<td height=20 colspan="1" align="center">&nbsp;</td>
						<td height=20 colspan="1" align="center">&nbsp;</td>
						<td height=20 colspan="1" align="center">&nbsp;</td>
						<td height=20 colspan="1" align="center">&nbsp;</td>
					</tr>
					<%
						}
							}
					%>
					<%
						double tdSumMaterialFeeTemp = 0.0;
							for (index = (0 + (m * pageCount)); index < (intBaseCount + (m * pageCount)); index++) {
								if (index < changeListInfo.size()) {
									tdSumMaterialFeeTemp += (((RepairContentDto) changeListInfo.get(index)).getChangeFee()) * (((RepairContentDto) changeListInfo.get(index)).getChangeCount());
								}
							}
							double tdSumMaterialFeeTemp1 = 0.0;
							for (index = (intBaseCount + (m * pageCount)); index < (pageCount + (m * pageCount)); index++) {
								if (index < changeListInfo.size()) {
									tdSumMaterialFeeTemp1 += (((RepairContentDto) changeListInfo.get(index)).getChangeFee()) * (((RepairContentDto) changeListInfo.get(index)).getChangeCount());
								}
							}
					%>
					<tr height=30>
						<td colspan="3" align=center>
							<b>材料费小计：</b>
						</td>
						<td align=center>
							<font color="red"><b><%=new DecimalFormat("#,##0.00").format(tdSumMaterialFeeTemp)%></b></font>
						</td>
						<td colspan="3" align=center>
							<b>材料费小计：</b>
						</td>
						<td align=center>
							<font color="red"><b><%=new DecimalFormat("#,##0.00").format(tdSumMaterialFeeTemp1)%></b></font>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<%
		//強制分頁列印
			if (m != strTimes - 1) {
	%>
	<div STYLE="page-break-after: always;"></div>
	<%
		}
	%>
	<%
		}
	%>
	<br>
	<table id="tbButton" cellpadding="0" cellspacing="0" width="80%" align="center" style="display:">
		<tr>
			<td class=button style="width: 50%" align="center"><input type=button name=buttonPrint value=" 列 印 " class="button" onclick="return printPage()"></td>
			<td class=button style="width: 50%" align="center"><input type=button name=buttonClose value=" 关 闭 " class="button" onclick="javascript:window.close()"></td>
		</tr>
	</table>
</body>
</html>
