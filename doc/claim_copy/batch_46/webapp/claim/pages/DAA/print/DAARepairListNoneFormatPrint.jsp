<%--
****************************************************************************
* DESC       ：机动车辆保险车辆损失情况确认书修理项目清单列印页面
* AUTHOR     : 理赔组
* CREATEDATE ：2004-11-16
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@include file="DAALossNoneFormatPrintIni.jsp"%>
<%
	if (count5<=2 && count4<=2 && count3<=2 && count2<=1 && count1<=2) {
		strMessage = "抱歉!修理项目不需列印清单附页,请您列印清单。";
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
	double totalManHour = 0.00;
	double totalManHourFee = 0.00;
	double totalMaterialFee = 0.00;
%>
<%
   		int intPageNum = 1;
   		if(count5>2 || count4>2 || count3>2 || count2>1 || count1>2){
   			intPageNum = intComponentCount<17?2:((intComponentCount-1) / pageCount+3);
   		}else{
   			intPageNum = intComponentCount<17?1:((intComponentCount-1) / pageCount+2);
   		}
%>
<html>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<link rel="stylesheet" type="text/css" href="/claim/DAA/print/StandardPrint.css">
<body bgcolor="#FFFFFF" onload="loadForm();">
	<!-- 标题部分 -->
	<table width="100%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr height=30>
			<td colspan="3" align="center">
				<img src="/claim/images/LOGO.jpg" />
			</td>
		</tr>
		<tr height=30>
			<td colspan="3" align=center style="font-family: 宋体; font-size: 14pt;">
				<B><center>
						机动车辆保险定损报告明细表（修理）</span>
						<center>
							<B>
			</td>
		</tr>
		<tr height=20>
			<td align=left width="48%" id="tdInsuredName" style="font-family: 宋体; font-size: 10pt;">
				被保险人：<%=strInsuredName%>
			</td>
			<td align=left id="tdRegistNo" width="35%" style="font-family: 宋体; font-size: 10pt;">
				报案号：<%=strRegistNo%></td>
			<td width="17%" align=right style="font-family: 宋体; font-size: 10pt;">
				共<%=intPageNum%>页&nbsp;&nbsp;&nbsp;第2页
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
	<table border=1 width="100%" align="center" cellspacing="0" cellpadding="0" style="font-family: 宋体; font-size: 11pt; border-collapse: collapse" bordercolor="#111111">
		<tr>
			<td width="50%" valign="top">
				<table frame=void width="100%" border=1 align="top" cellspacing="0" cellpadding="0" style="font-family: 宋体; font-size: 11pt; border-collapse: collapse" bordercolor="#111111">
					<tr align="center" bgcolor="#CCCCCC" height="25">
						<td width="10%" colspan="1">
							<B>序号<B>
						</td>
						<td width="50%">
							<B>修理项目名称<B>
						</td>
						<td width="10%">
							<B>工时<B>
						</td>
						<td width="30%">
							<B>工时费<B>
						</td>
					</tr>
					<%
						if (repairListInfo.size() <= 23) {
							for (int i = 0; i < repairListInfo.size(); i++) {
								if (((RepairContentDto) repairListInfo.get(i)).getIsType()) {
					%>
					<tr height="25">
						<td width="10%" colspan="1" height="25"></td>
						<td width="50%"><%=((RepairContentDto) repairListInfo.get(i))
								.getRepairType()%></td>
						<td width="10%"></td>
						<td width="30%"></td>
					</tr>
					<%
						} else {
					%>
					<tr height="25">
						<td width="10%" colspan="1" align="center" height="25"><%=((RepairContentDto) repairListInfo.get(i))
								.getNo()%></td>
						<td width="50%" align="center"><%=((RepairContentDto) repairListInfo.get(i))
								.getRepairName()%></td>
						<td width="10%" align="center"><%=new DecimalFormat("#,##0")
								.format(((RepairContentDto) repairListInfo
										.get(i)).getRepairHour())%></td>
						<td width="30%" align="center"><%=new DecimalFormat("#,##0.00")
								.format(((RepairContentDto) repairListInfo
										.get(i)).getRepairHourFee())%></td>
					</tr>
					<%
						}
							}
					%>
					<%
						for (int i = 0; i < 23 - repairListInfo.size(); i++) {
					%>
					<tr align="center" height="25">
						<td width="10%" colspan="1"></td>
						<td width="50%"></td>
						<td width="10%"></td>
						<td width="30%"></td>
					</tr>
					<%
						}
					%>
					<tr bgcolor="#CCCCCC" height="25">
						<td width="60%" colspan="2" align="center">
							<B>小计<B>
						</td>
						<td width="10%" align="center"><%=new DecimalFormat("#,##0").format(sumhour)%></td>
						<td width="30%" align="center"><%=new DecimalFormat("#,##0.00").format(sumhourfee)%></td>
					</tr>
				</table>
			</td>
			<td width="50%" valign="top">
				<table width="100%" frame=void border=1 align="top" cellspacing="0" cellpadding="0" style="font-family: 宋体; font-size: 11pt; border-collapse: collapse" bordercolor="#111111">
					<tr align="center" bgcolor="#CCCCCC" height="25">
						<td width="10%" colspan="1">
							<B>序号<B>
						</td>
						<td width="50%">
							<B>修理项目名称<B>
						</td>
						<td width="10%">
							<B>工时<B>
						</td>
						<td width="30%">
							<B>工时费<B>
						</td>
					</tr>
					<%
						for (int i = 0; i < 23; i++) {
					%>
					<tr align="center" height="25">
						<td width="10%" colspan="1"></td>
						<td width="50%"></td>
						<td width="10%"></td>
						<td width="30%"></td>
					</tr>
					<%
						}
					%>
					<tr bgcolor="#CCCCCC" height="25">
						<td width="60%" colspan="2" align="center">
							<B>小计<B>
						</td>
						<td width="10%"></td>
						<td width="30%"></td>
					</tr>
				</table>
			</td>
		</tr>
		<%
			} else if (repairListInfo.size() <= 46) {
				for (int i = 0; i < 23; i++) {
					firstsumhour += ((RepairContentDto) repairListInfo.get(i))
							.getRepairHour();
					firstsumhourfee += ((RepairContentDto) repairListInfo
							.get(i)).getRepairHourFee();
					if (((RepairContentDto) repairListInfo.get(i)).getIsType()) {
		%>
		<tr height="25">
			<td width="10%" colspan="1" height="25"></td>
			<td width="50%" height="25"><%=((RepairContentDto) repairListInfo.get(i))
								.getRepairType()%></td>
			<td width="10%" align="center" height="25"></td>
			<td width="30%" align="center" height="25"></td>
		</tr>
		<%
			} else {
		%>
		<tr height="25" height="25">
			<td width="10%" colspan="1" align="center" height="25"><%=((RepairContentDto) repairListInfo.get(i))
								.getNo()%></td>
			<td width="50%" align="center" height="25"><%=((RepairContentDto) repairListInfo.get(i))
								.getRepairName()%></td>
			<td width="10%" align="center" height="25"><%=new DecimalFormat("#,##0")
								.format(((RepairContentDto) repairListInfo
										.get(i)).getRepairHour())%></td>
			<td width="30%" align="center" height="25"><%=new DecimalFormat("#,##0.00")
								.format(((RepairContentDto) repairListInfo
										.get(i)).getRepairHourFee())%></td>
		</tr>
		<%
			}
				}
		%>
		<tr bgcolor="#CCCCCC" height="25">
			<td width="60%" colspan="2" align="center">
				<B>小计<B>
			</td>
			<td width="10%" align="center"><%=new DecimalFormat("#,##0").format(firstsumhour)%></td>
			<td width="30%" align="center"><%=new DecimalFormat("#,##0.00").format(firstsumhourfee)%></td>
		</tr>
	</table>
	</td>
	<td width="50%" valign="top">
		<table width="100%" frame=void border=1 align="top" cellspacing="0" cellpadding="0" style="font-family: 宋体; font-size: 11pt; border-collapse: collapse" bordercolor="#111111">
			<tr align="center" bgcolor="#CCCCCC" height="25">
				<td width="10%" colspan="1">
					<B>序号<B>
				</td>
				<td width="50%">
					<B>修理项目名称<B>
				</td>
				<td width="10%">
					<B>工时<B>
				</td>
				<td width="30%">
					<B>工时费<B>
				</td>
			</tr>
			<%
				for (int i = 23; i < repairListInfo.size(); i++) {
						if (((RepairContentDto) repairListInfo.get(i)).getIsType()) {
			%>
			<tr height="25">
				<td width="10%" colspan="1" height="25"></td>
				<td width="50%" height="25"><%=((RepairContentDto) repairListInfo.get(i))
								.getRepairType()%></td>
				<td width="10%" height="25"></td>
				<td width="30%" height="25"></td>
			</tr>
			<%
				} else {
			%>
			<tr height="25">
				<td width="10%" colspan="1" align="center" height="25"><%=((RepairContentDto) repairListInfo.get(i))
								.getNo()%></td>
				<td width="50%" align="center" height="25"><%=((RepairContentDto) repairListInfo.get(i))
								.getRepairName()%></td>
				<td width="10%" align="center" height="25"><%=new DecimalFormat("#,##0")
								.format(((RepairContentDto) repairListInfo
										.get(i)).getRepairHour())%></td>
				<td width="30%" align="center" height="25"><%=new DecimalFormat("#,##0.00")
								.format(((RepairContentDto) repairListInfo
										.get(i)).getRepairHourFee())%></td>
			</tr>
			<%
				}
					}
			%>
			<%
				for (int i = 0; i < 46 - repairListInfo.size(); i++) {
			%>
			<tr align="center" height="25">
				<td width="10%" colspan="1"></td>
				<td width="50%"></td>
				<td width="10%"></td>
				<td width="30%"></td>
			</tr>
			<%
				}
			%>
			<tr bgcolor="#CCCCCC" height="25">
				<td width="60%" colspan="2" align="center">
					<B>小计<B>
				</td>
				<td width="10%" align="center"><%=new DecimalFormat("#,##0").format(sumhour
						- firstsumhour)%></td>
				<td width="30%" align="center"><%=new DecimalFormat("#,##0.00").format(sumhourfee
						- firstsumhourfee)%></td>
			</tr>
		</table>
	</td>
	</tr>
	<%
		} else {
			for (int i = 0; i < 23; i++) {
				firstsumhour += ((RepairContentDto) repairListInfo.get(i))
						.getRepairHour();
				firstsumhourfee += ((RepairContentDto) repairListInfo
						.get(i)).getRepairHourFee();
				if (((RepairContentDto) repairListInfo.get(i)).getIsType()) {
	%>
	<tr height="25">
		<td width="10%" colspan="1" height="25"></td>
		<td width="50%" height="25"><%=((RepairContentDto) repairListInfo.get(i))
								.getRepairType()%></td>
		<td width="10%" height="25"></td>
		<td width="30%" height="25"></td>
	</tr>
	<%
		} else {
	%>
	<tr height="25">
		<td width="10%" colspan="1" align="center" height="25"><%=((RepairContentDto) repairListInfo.get(i))
								.getNo()%></td>
		<td width="50%" align="center" height="25"><%=((RepairContentDto) repairListInfo.get(i))
								.getRepairName()%></td>
		<td width="10%" align="center" height="25"><%=new DecimalFormat("#,##0")
								.format(((RepairContentDto) repairListInfo
										.get(i)).getRepairHour())%></td>
		<td width="30%" align="center" height="25"><%=new DecimalFormat("#,##0.00")
								.format(((RepairContentDto) repairListInfo
										.get(i)).getRepairHourFee())%></td>
	</tr>
	<%
		}
			}
	%>
	<tr bgcolor="#CCCCCC" height="25">
		<td width="60%" colspan="2" align="center">
			<B>小计<B>
		</td>
		<td width="10%" align="center"><%=new DecimalFormat("#,##0").format(firstsumhour)%></td>
		<td width="30%" align="center"><%=new DecimalFormat("#,##0.00").format(firstsumhourfee)%></td>
	</tr>
	</table>
	</td>
	<td width="50%" valign="top">
		<table width="100%" frame=void border=1 align="top" cellspacing="0" cellpadding="0" style="font-family: 宋体; font-size: 11pt; border-collapse: collapse" bordercolor="#111111">
			<tr align="center" bgcolor="#CCCCCC" height="25">
				<td width="10%" colspan="1">
					<B>序号<B>
				</td>
				<td width="50%">
					<B>修理项目名称<B>
				</td>
				<td width="10%">
					<B>工时<B>
				</td>
				<td width="30%">
					<B>工时费<B>
				</td>
			</tr>
			<%
				for (int i = 23; i < 46; i++) {
						secondsumhour += ((RepairContentDto) repairListInfo.get(i))
								.getRepairHour();
						secondsumhourfee += ((RepairContentDto) repairListInfo
								.get(i)).getRepairHourFee();
						if (((RepairContentDto) repairListInfo.get(i)).getIsType()) {
			%>
			<tr height="25">
				<td width="10%" colspan="1" height="25"></td>
				<td width="50%" height="25"><%=((RepairContentDto) repairListInfo.get(i))
								.getRepairType()%></td>
				<td width="10%" height="25"></td>
				<td width="30%" height="25"></td>
			</tr>
			<%
				} else {
			%>
			<tr height="25">
				<td width="10%" colspan="1" align="center" height="25"><%=((RepairContentDto) repairListInfo.get(i))
								.getNo()%></td>
				<td width="50%" align="center" height="25"><%=((RepairContentDto) repairListInfo.get(i))
								.getRepairName()%></td>
				<td width="10%" align="center" height="25"><%=new DecimalFormat("#,##0")
								.format(((RepairContentDto) repairListInfo
										.get(i)).getRepairHour())%></td>
				<td width="30%" align="center" height="25"><%=new DecimalFormat("#,##0.00")
								.format(((RepairContentDto) repairListInfo
										.get(i)).getRepairHourFee())%></td>
			</tr>
			<%
				}
					}
			%>
			<tr bgcolor="#CCCCCC" height="25">
				<td width="60%" colspan="2" align="center">
					<B>小计<B>
				</td>
				<td width="10%" align="center"><%=new DecimalFormat("#,##0").format(secondsumhour)%></td>
				<td width="30%" align="center"><%=new DecimalFormat("#,##0.00")
						.format(secondsumhourfee)%></td>
			</tr>
		</table>
	</td>
	</tr>
	</table>
	<!-- 结尾部分 -->
	<table width="100%" align="center" cellspacing="0" bordercolor="#111111" cellpadding="2" border="1" style="font-family: 宋体; font-size: 10pt;">
		<tr align="left">
			<td width="30%" id="tdSanctioner">
				<B>查勘定损人：<B><%=strHandlerName%>
			</td>
			<td width="35%">
				<B>核价人：<B><%=strApproverName%>
			</td>
			<%
				if (strApproverName == "") {
			%>
			<td width="35%" align="left">
				<B>
					<%--=verpDate.getYear()--%>年<%--=verpDate.getMonth()--%>月<%--=verpDate.getDay()--%>日<B>
			</td>
			<%
				}
			%>
			<%
				if (strApproverName != "") {
			%>
			<td width="35%" align="left">
				<B><%=verpDate.getYear()%>年<%=verpDate.getMonth()%>月<%=verpDate.getDay()%>日<B>
			</td>
			<%
				}
			%>
		</tr>
	</table>
	<table width="100%" align="center" cellspacing="0" cellpadding="2" border="0" style="font-family: 宋体; font-size: 10pt;">
		<tr align="left">
			<td colspan="2">
				注: 1、油漆、防冻液和非零配件更换项目列入本表修理项目中；<br> &nbsp;&nbsp;&nbsp;&nbsp;2、零配件更换项目的工时费不列入本表修理项目中。<br>
			</td>
		</tr>
	</table>
	<%
		}
	%>
	<!-- 修理附页-->
	<%
		int p = 0;
		String table1Style = "display:none";
		if (pageTotal > 1)
			table1Style = "";
		for (p = 0; p < pageTotal - 1; p++) {
	%>
	<table style="width:100%;align:center;<%=table1Style%>" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr height=30>
			<td colspan="3" align="center">
				<img src="/claim/images/LOGO.jpg" />
			</td>
		</tr>
		<tr height=30>
			<td colspan="3" align=center style="font-family: 宋体; font-size: 14pt;">
				<B><center>
						机动车辆保险定损报告明细表（修理-附页）</span>
						<center>
							<B>
			</td>
		</tr>
		<tr height=20>
			<td align=left width="48%" id="tdInsuredName" style="font-family: 宋体; font-size: 10pt;">
				被保险人：<%=strInsuredName%>
			</td>
			<td align=left id="tdRegistNo" width="35%" style="font-family: 宋体; font-size: 10pt;">
				报案号：<%=strRegistNo%></td>
			<td width="17%" align=right style="font-family: 宋体; font-size: 10pt;">
				共<%=pageTotal%>页&nbsp;&nbsp;&nbsp;第<%=p + 2%>页
			</td>
		</tr>
	</table>
	<table border=1 style="width:100%;align:center;<%=table1Style%>" align="center" cellspacing="0" cellpadding="0" border="0" style="border-collapse: collapse" bordercolor="#111111"
		style="font-family:宋体; font-size:10pt;">
		<tr height="25" border=1>
			<td width="15%" align=center id="tdLicenseNo" style="font-family: 宋体; font-size: 10pt;">号牌号码:</td>
			<td width="35%" align=center><%=licenseNo%></td>
			<td width="15%" align=center id="tdPolicyNo" style="font-family: 宋体; font-size: 10pt;">保险单号:</td>
			<td width="35%" align=center><%=strPolicyNo%></td>
		</tr>
		<tr height="25">
			<td width="15%" align=center id="" style="font-family: 宋体; font-size: 10pt;">发动机号:</td>
			<td width="35%" align=center><%=engineNo%></td>
			<td width="15%" align=center id="" style="font-family: 宋体; font-size: 10pt;">车架号:</td>
			<td width="35%" align=center><%=frameNo%>
			</td>
		</tr>
	</table>
	<table border=1 style="width:100%;align:center;<%=table1Style%>" align="center" cellspacing="0" cellpadding="0" style="font-family:宋体; font-size:11pt; border-collapse:collapse" bordercolor="#111111">
		<tr>
			<td width="50%" valign="top">
				<table width="100%" border=1 align="top" cellspacing="0" cellpadding="0" style="font-family: 宋体; font-size: 11pt; border-collapse: collapse" bordercolor="#111111">
					<tr align="center" bgcolor="#CCCCCC" height="25">
						<td width="10%" colspan="1">
							<B>序号<B>
						</td>
						<td width="50%">
							<B>修理项目名称<B>
						</td>
						<td width="10%">
							<B>工时<B>
						</td>
						<td width="30%">
							<B>工时费<B>
						</td>
					</tr>
					<%
						int sizeResult = (repairListInfo.size() - 46) % 54;
							//直接判断到底是
							if ((repairListInfo.size() <= 73 + 54 * p)
									|| ((repairListInfo.size() > (100 + 54 * p)) && (sizeResult <= 27))) {
								firstsumhour = 0;
								firstsumhourfee = 0;
								for (int i = 46 + 54 * p; i < 73 + 54 * p; i++) {
									firstsumhour += ((RepairContentDto) repairListInfo
											.get(i)).getRepairHour();
									firstsumhourfee += ((RepairContentDto) repairListInfo
											.get(i)).getRepairHourFee();
									if (((RepairContentDto) repairListInfo.get(i))
											.getIsType()) {
					%>
					<tr height="25">
						<td width="10%" colspan="1" height="25"></td>
						<td width="50%" height="25"><%=((RepairContentDto) repairListInfo.get(i))
									.getRepairType()%></td>
						<td width="10%" height="25"></td>
						<td width="30%" height="25"></td>
					</tr>
					<%
						} else {
					%>
					<tr height="25">
						<td width="10%" colspan="1" align="center" height="25"><%=((RepairContentDto) repairListInfo.get(i))
									.getNo()%></td>
						<td width="50%" align="center" height="25"><%=((RepairContentDto) repairListInfo.get(i))
									.getRepairName()%></td>
						<td width="10%" align="center" height="25"><%=new DecimalFormat("#,##0")
									.format(((RepairContentDto) repairListInfo
											.get(i)).getRepairHour())%></td>
						<td width="30%" align="center" height="25"><%=new DecimalFormat("#,##0.00")
									.format(((RepairContentDto) repairListInfo
											.get(i)).getRepairHourFee())%></td>
					</tr>
					<%
						}
									if (i == repairListInfo.size() - 1) {
										for (int j = 0; j < 27 - sizeResult; j++) {
					%>
					<tr align="center" height="25">
						<td width="10%" colspan="1"></td>
						<td width="50%"></td>
						<td width="10%"></td>
						<td width="30%"></td>
					</tr>
					<%
						}
										break;
									}
								}
					%>
					<tr bgcolor="#CCCCCC" height="25">
						<td width="60%" colspan="2" align="center">
							<B>小计<B>
						</td>
						<td width="10%" align="center"><%=new DecimalFormat("#,##0").format(firstsumhour)%></td>
						<td width="30%" align="center"><%=new DecimalFormat("#,##0.00")
							.format(firstsumhourfee)%></td>
					</tr>
				</table>
			</td>
			<td width="50%" valign="top">
				<table width="100%" border=1 align="top" cellspacing="0" cellpadding="0" style="font-family: 宋体; font-size: 11pt; border-collapse: collapse" bordercolor="#111111">
					<tr align="center" bgcolor="#CCCCCC" height="25">
						<td width="10%" colspan="1">
							<B>序号<B>
						</td>
						<td width="50%">
							<B>修理项目名称<B>
						</td>
						<td width="10%">
							<B>工时<B>
						</td>
						<td width="30%">
							<B>工时费<B>
						</td>
					</tr>
					<%
						if (p != pageTotal - 2) {
									secondsumhour = 0;
									secondsumhourfee = 0;
									for (int i = 73 + 54 * p; i < 100 + 54 * p; i++) {
										secondsumhour += ((RepairContentDto) repairListInfo
												.get(i)).getRepairHour();
										secondsumhourfee += ((RepairContentDto) repairListInfo
												.get(i)).getRepairHourFee();
										if (((RepairContentDto) repairListInfo.get(i))
												.getIsType()) {
					%>
					<tr height="25">
						<td width="10%" colspan="1" height="25"></td>
						<td width="50%" height="25"><%=((RepairContentDto) repairListInfo
										.get(i)).getRepairType()%></td>
						<td width="10%" height="25"></td>
						<td width="30%" height="25"></td>
					</tr>
					<%
						} else {
					%>
					<tr height="25">
						<td width="10%" colspan="1" align="center" height="25"><%=((RepairContentDto) repairListInfo
										.get(i)).getNo()%></td>
						<td width="50%" align="center" height="25"><%=((RepairContentDto) repairListInfo
										.get(i)).getRepairName()%></td>
						<td width="10%" align="center" height="25"><%=new DecimalFormat("#,##0")
										.format(((RepairContentDto) repairListInfo
												.get(i)).getRepairHour())%></td>
						<td width="30%" align="center" height="25"><%=new DecimalFormat("#,##0.00")
										.format(((RepairContentDto) repairListInfo
												.get(i)).getRepairHourFee())%></td>
					</tr>
					<%
						}
									}
								} else {
									secondsumhour = 0;
									secondsumhourfee = 0;
									for (int i = 0; i < 27; i++) {
					%>
					<tr align="center" height="25">
						<td width="10%" colspan="1"></td>
						<td width="50%"></td>
						<td width="10%"></td>
						<td width="30%"></td>
					</tr>
					<%
						}
								}
					%>
					<tr bgcolor="#CCCCCC" height="25">
						<td width="60%" colspan="2" align="center">
							<B>小计<B>
						</td>
						<td width="10%" align="center"><%=new DecimalFormat("#,##0").format(secondsumhour)%></td>
						<td width="30%" align="center"><%=new DecimalFormat("#,##0.00")
							.format(secondsumhourfee)%></td>
					</tr>
				</table>
			</td>
		</tr>
		<%
			} else if ((repairListInfo.size() <= 100 + 54 * p || (repairListInfo
						.size() > (100 + 54 * p)))) {
					firstsumhour = 0;
					secondsumhour = 0;
					secondsumhourfee = 0;
					firstsumhourfee = 0;
					for (int i = 46 + 54 * p; i < 73 + 54 * p; i++) {
						firstsumhour += ((RepairContentDto) repairListInfo
								.get(i)).getRepairHour();
						firstsumhourfee += ((RepairContentDto) repairListInfo
								.get(i)).getRepairHourFee();
						if (((RepairContentDto) repairListInfo.get(i))
								.getIsType()) {
		%>
		<tr height="25">
			<td width="10%" colspan="1" height="25"></td>
			<td width="50%" height="25"><%=((RepairContentDto) repairListInfo.get(i))
									.getRepairType()%></td>
			<td width="10%" align="center" height="25"></td>
			<td width="30%" align="center" height="25"></td>
		</tr>
		<%
			} else {
		%>
		<tr height="25">
			<td width="10%" colspan="1" align="center" height="25"><%=((RepairContentDto) repairListInfo.get(i))
									.getNo()%></td>
			<td width="50%" align="center" height="25"><%=((RepairContentDto) repairListInfo.get(i))
									.getRepairName()%></td>
			<td width="10%" align="center" height="25"><%=new DecimalFormat("#,##0")
									.format(((RepairContentDto) repairListInfo
											.get(i)).getRepairHour())%></td>
			<td width="30%" align="center" height="25"><%=new DecimalFormat("#,##0.00")
									.format(((RepairContentDto) repairListInfo
											.get(i)).getRepairHourFee())%></td>
		</tr>
		<%
			}
					}
		%>
		<tr bgcolor="#CCCCCC" height="25">
			<td width="60%" colspan="2" align="center">
				<B>小计<B>
			</td>
			<td width="10%" align="center"><%=new DecimalFormat("#,##0").format(firstsumhour)%></td>
			<td width="30%" align="center"><%=new DecimalFormat("#,##0.00")
							.format(firstsumhourfee)%></td>
		</tr>
	</table>
	</td>
	<td width="50%" valign="top">
		<table width="100%" border=1 align="top" cellspacing="0" cellpadding="0" style="font-family: 宋体; font-size: 11pt; border-collapse: collapse" bordercolor="#111111">
			<tr align="center" bgcolor="#CCCCCC" height="25">
				<td width="10%" colspan="1">
					<B>序号<B>
				</td>
				<td width="50%">
					<B>修理项目名称<B>
				</td>
				<td width="10%">
					<B>工时<B>
				</td>
				<td width="30%">
					<B>工时费<B>
				</td>
			</tr>
			<%
				for (int i = 73 + 54 * p; i < 100 + 54 * p; i++) {
							secondsumhour += ((RepairContentDto) repairListInfo
									.get(i)).getRepairHour();
							secondsumhourfee += ((RepairContentDto) repairListInfo
									.get(i)).getRepairHourFee();
							if (((RepairContentDto) repairListInfo.get(i))
									.getIsType()) {
			%>
			<tr height="25">
				<td width="10%" colspan="1" height="25"></td>
				<td width="50%" height="25"><%=((RepairContentDto) repairListInfo.get(i))
									.getRepairType()%></td>
				<td width="10%" height="25"></td>
				<td width="30%" height="25"></td>
			</tr>
			<%
				} else {
			%>
			<tr height="25">
				<td width="10%" colspan="1" align="center" height="25"><%=((RepairContentDto) repairListInfo.get(i))
									.getNo()%></td>
				<td width="50%" align="center" height="25"><%=((RepairContentDto) repairListInfo.get(i))
									.getRepairName()%></td>
				<td width="10%" align="center" height="25"><%=new DecimalFormat("#,##0")
									.format(((RepairContentDto) repairListInfo
											.get(i)).getRepairHour())%></td>
				<td width="30%" align="center" height="25"><%=new DecimalFormat("#,##0.00")
									.format(((RepairContentDto) repairListInfo
											.get(i)).getRepairHourFee())%></td>
			</tr>
			<%
				}
							if (i == repairListInfo.size() - 1)
								break;
						}
			%>
			<%
				for (int i = 0; i < 100 + 54 * p - repairListInfo.size(); i++) {
			%>
			<tr align="center" height="25">
				<td width="10%" colspan="1"></td>
				<td width="50%"></td>
				<td width="10%"></td>
				<td width="30%"></td>
			</tr>
			<%
				}
			%>
			<tr bgcolor="#CCCCCC" height="25">
				<td width="60%" colspan="2" align="center">
					<B>小计<B>
				</td>
				<td width="10%" align="center"><%=new DecimalFormat("#,##0").format(secondsumhour)%></td>
				<td width="30%" align="center"><%=new DecimalFormat("#,##0.00")
							.format(secondsumhourfee)%></td>
			</tr>
		</table>
	</td>
	</tr>
	<%
		}
	%>
	</table>
	<!-- 结尾部分 -->
	<table style="width:100%;align:center;<%=table1Style%>" align="center" cellspacing="0" bordercolor="#111111" cellpadding="2" border="1" style="font-family:宋体; font-size:10pt;">
		<tr align="left">
			<td width="30%" id="tdSanctioner">
				<B>查勘定损人：<B><%=strHandlerName%>
			</td>
			<td width="35%">
				<B>核价人：<B><%=strApproverName%>
			</td>
			<%
				if (strApproverName == "") {
			%>
			<td width="35%" align="left">
				<B>
					<%--=verpDate.getYear()--%>年<%--=verpDate.getMonth()--%>月<%--=verpDate.getDay()--%>日<B>
			</td>
			<%
				}
			%>
			<%
				if (strApproverName != "") {
			%>
			<td width="35%" align="left">
				<B><%=verpDate.getYear()%>年<%=verpDate.getMonth()%>月<%=verpDate.getDay()%>日<B>
			</td>
			<%
				}
			%>
		</tr>
	</table>
	<table style="width:100%;align:center;<%=table1Style%>" align="center" cellspacing="0" cellpadding="2" border="0" style="font-family:宋体; font-size:10pt;">
		<tr align="left">
			<td colspan="2">
				注: 1、油漆、防冻液和非零配件更换项目列入本表修理项目中；<br> &nbsp;&nbsp;&nbsp;&nbsp;2、零配件更换项目的工时费不列入本表修理项目中。<br>
			</td>
		</tr>
	</table>
	<%
		}
	%>
	<script language='javascript'>
		function printPage() {
			//add print liudaoping 2013-04-15
			//alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
			return false;
			tbButton.style.display = "none";
			window.print();
		}
	</script>
	<!-- 按钮部分 -->
	<table id="tbButton" cellpadding="0" cellspacing="0" width="80%" style="display:">
		<tr>
			<td class=button style="width: 50%" align="center">
				<input type=button name=buttonPrint value=" 列 印 " class="button" onclick="return printPage()">
			</td>
			<td class=button style="width: 50%" align="center">
				<input type=button name=buttonClose value=" 关 闭 " class="button" onclick="javascript:window.close()">
			</td>
		</tr>
	</table>
</body>
</html>
