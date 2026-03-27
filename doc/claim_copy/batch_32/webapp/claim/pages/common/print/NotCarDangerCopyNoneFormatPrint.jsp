<%--
****************************************************************************
* DESC       ：出险後抄单打印页面
* AUTHOR     ： 项目组
* CREATEDATE ： 2005-09-14
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@page contentType="text/html; charset=GBK"%>
<%--初始化--%>
<%@include file="NotCarDangerCopyNoneFormatPrintIni.jsp"%>
<%
	String printType = (String) request.getAttribute("printType");
%>
<html>
<head>
<title>财产保险公司承保理赔信息</title>
</head>
<!--按险类调用不同模块-->
<body>
	<%
		if (strClassCode.equals("10") || strClassCode.equals("09")) {
	%>
	<%--include抄单头信息--%>
	<jsp:include page="/common/print/copyprint/CopyNoneFormatPrintHead10.jsp" />
	<%--include抄单主险信息--%>
	<jsp:include page="/common/print/copyprint/CopyNoneFormatPrintMain.jsp" />
	<%--include抄单附加信息--%>
	<jsp:include page="/common/print/copyprint/CopyNoneFormatPrintSub.jsp" />
	<%--include抄单批改信息--%>
	<jsp:include page="/common/print/copyprint/CopyNoneFormatPrintEndorse.jsp" />
	<%
		if ("CopyPrintNew".equals(printType)) {
	%>
	<%
		} else {
	%>
	<%--include抄单保费到帳信息--%>
	<jsp:include page="/common/print/copyprint/CopyNoneFormatPrintAccount.jsp" />
	<%--include抄单分保信息--%>
	<jsp:include page="/common/print/copyprint/CopyNoneFormatPrintReins.jsp" />
	<%
		}
	%>
	<%
		if ("CopyPrintNew".equals(printType)) {
	%>
	<%--include报案信息--%>
	<jsp:include page="/common/print/copyprint/CopyNoneFormatPrintRegistInfo.jsp" />
	<%
		}
	%>
	<%--include抄单历史赔付信息--%>
	<jsp:include page="/common/print/copyprint/CopyNoneFormatPrintHistory.jsp" />
	<%
		} else if (strClassCode.equals("01") || strClassCode.equals("02") || strClassCode.equals("11")) {
	%>
	<%--include抄单头信息--%>
	<jsp:include page="/common/print/copyprint/CopyNoneFormatPrintHead01.jsp" />
	<%--include抄单主险信息--%>
	<jsp:include page="/common/print/copyprint/CopyNoneFormatPrintMain01.jsp" />
	<%--include抄单附加险信息--%>
	<jsp:include page="/common/print/copyprint/CopyNoneFormatPrintSub01.jsp" />
	<%--include抄单批改信息--%>
	<jsp:include page="/common/print/copyprint/CopyNoneFormatPrintEndorse.jsp" />
	<%
		if ("CopyPrintNew".equals(printType)) {
	%>
	<%--include报案信息--%>
	<jsp:include page="/common/print/copyprint/CopyNoneFormatPrintRegistInfo.jsp" />
	<%
		} else {
	%>
	<%--include抄单保费到帳信息--%>
	<jsp:include page="/common/print/copyprint/CopyNoneFormatPrintAccount01.jsp" />
	<%
		}
	%>
	<%--include抄单历史赔付信息--%>
	<jsp:include page="/common/print/copyprint/CopyNoneFormatPrintHistory01.jsp" />
	<%
		} else if (strClassCode.equals("08")) {
	%>
	<%--include抄单头信息--%>
	<jsp:include page="/common/print/copyprint/CopyNoneFormatPrintHead08.jsp" />
	<%--include抄单附加险信息--%>
	<jsp:include page="/common/print/copyprint/CopyNoneFormatPrintSub08.jsp" />
	<%--include抄单批改信息--%>
	<jsp:include page="/common/print/copyprint/CopyNoneFormatPrintEndorse.jsp" />
	<%--include抄单保费到帳信息--%>
	<jsp:include page="/common/print/copyprint/CopyNoneFormatPrintAccount01.jsp" />
	<%--include抄单历史赔付信息--%>
	<jsp:include page="/common/print/copyprint/CopyNoneFormatPrintHistory01.jsp" />
	<%
		} else if (strClassCode.equals("15")) {
	%>
	<%--include抄单头信息--%>
	<jsp:include page="/common/print/copyprint/CopyNoneFormatPrintHead15.jsp" />
	<%--include抄单主险信息--%>
	<jsp:include page="/common/print/copyprint/CopyNoneFormatPrintMain01.jsp" />
	<%--include抄单附加险信息--%>
	<jsp:include page="/common/print/copyprint/CopyNoneFormatPrintSub01.jsp" />
	<%--include抄单批改信息--%>
	<jsp:include page="/common/print/copyprint/CopyNoneFormatPrintEndorse.jsp" />
	<%
		if ("CopyPrintNew".equals(printType)) {
	%>
	<%--include报案信息--%>
	<jsp:include page="/common/print/copyprint/CopyNoneFormatPrintRegistInfo.jsp" />
	<%
		} else {
	%>
	<%--include抄单保费到帳信息--%>
	<jsp:include page="/common/print/copyprint/CopyNoneFormatPrintAccount01.jsp" />
	<%
		}
	%>
	<%--include抄单历史赔付信息--%>
	<jsp:include page="/common/print/copyprint/CopyNoneFormatPrintHistory01.jsp" />
	<%
		} else if (strClassCode.equals("16")) {
	%>
	<%--include抄单头信息--%>
	<jsp:include page="/common/print/copyprint/CopyNoneFormatPrintHead16.jsp" />
	<%--include抄单附加险信息--%>
	<jsp:include page="/common/print/copyprint/CopyNoneFormatPrintSub01.jsp" />
	<%--include抄单批改信息--%>
	<jsp:include page="/common/print/copyprint/CopyNoneFormatPrintEndorse.jsp" />
	<%--include抄单保费到帳信息--%>
	<jsp:include page="/common/print/copyprint/CopyNoneFormatPrintAccount01.jsp" />
	<%--include抄单历史赔付信息--%>
	<jsp:include page="/common/print/copyprint/CopyNoneFormatPrintHistory01.jsp" />
	<%
		} else if (strClassCode.equals("17")) {
	%>
	<%--include抄单头信息--%>
	<jsp:include page="/common/print/copyprint/CopyNoneFormatPrintHead17.jsp" />
	<%--include抄单附加险信息--%>
	<jsp:include page="/common/print/copyprint/CopyNoneFormatPrintSub01.jsp" />
	<%--include抄单批改信息--%>
	<jsp:include page="/common/print/copyprint/CopyNoneFormatPrintEndorse.jsp" />
	<%--include抄单保费到帳信息--%>
	<jsp:include page="/common/print/copyprint/CopyNoneFormatPrintAccount01.jsp" />
	<%--include抄单历史赔付信息--%>
	<jsp:include page="/common/print/copyprint/CopyNoneFormatPrintHistory01.jsp" />
	<%
		} else {
	%>
	<table style="font-family: 宋体; font-size: 11pt; width: 90%" align="center">
		<tr>
			<td align="center" width="100%" style="font-size: 12pt;" colspan="2">
				<b> 财产保险有限公司&nbsp;&nbsp;<%=strRiskName%>承保理赔信息-公共信息
				</b>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<hr width="100%" align="center" style="direction: inherit" />
			</td>
		</tr>
		<%
			if ("CopyPrintNew".equals(printType)) {
				} else {
		%>
		<tr>
			<td colspan="2">
				报案号：<%=strRegistNo%></td>
		</tr>
		<%
			}
		%>
		<tr>
			<td colspan="2">
				保险单号：<%=strPolicyNo%></td>
		</tr>
		<%
			if ("CopyPrintNew".equals(printType)) {
		%>
		<tr>
			<td>
				总保额：<%=dbSumAmount%></td>
			<td>
				总保费：<%=dbSumpremium%></td>
		</tr>
		<%
			}
		%>
		<tr>
			<td colspan="2">
				被保险人名称：<%=strPolicyName%></td>
		</tr>
		<%
			if ("CopyPrintNew".equals(printType)) {
		%>
		<tr>
			<td colspan="2">
				联系电话：<%=strPolicyPhonenumber%></td>
		</tr>
		<%
			}
		%>
		<tr>
			<td colspan="2">
				被保险人地址：<%=strPolicyAddress%></td>
		</tr>
		<%
			if ("CopyPrintNew".equals(printType)) {
				} else {
		%>
		<tr>
			<td colspan="2">
				保险金额：<%=strCurrency%><%=new DecimalFormat("#,##0.00").format(dbSumAmount)%>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				保险标的：<%=strItemDetailName%></td>
		</tr>
		<tr>
			<td colspan="2">
				保险标的地址：
				<%
				if (strAddressName.length > 0) {
							if (strAddressName.length == 1) {
			%>
				<%=strAddressName[0]%>
				<%
					} else {
				%>
				1、<%=strAddressName[0]%>
				<%
					}
							}
				%>
			</td>
		</tr>
		<%
			for (int prpCaddressDtoIndex = 1; prpCaddressDtoIndex < strAddressName.length; prpCaddressDtoIndex++) {
		%>
		<tr>
			<td colspan="2" align="left">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=prpCaddressDtoIndex + 1%>、<%=strAddressName[prpCaddressDtoIndex]%></td>
		</tr>
		<%
			}
		%>
		<tr>
			<td colspan="2">
				免赔说明：
				<%
				if (strLimitName.length() == 0) {
			%>
				无
				<%
				}
			%>
			</td>
		</tr>
		<%
			if (strLimitName.length() > 0) {
		%>
		<tr>
			<td colspan="2">
				<table align="left" width="90%">
					<tr align="left">
						<td width="7%"></td>
						<td><%=strLimitName%></td>
					</tr>
				</table>
			</td>
		</tr>
		<%
			}
				}
		%>
		<tr>
			<td>
				投保日期：<%=OperateDate%></td>
			<td>
				輸入日期：<%=UnderWriteEndDate%></td>
		</tr>
		<tr>
			<td>
				签单日期：<%=SignDate%></td>
			<td>
				出单日期：<%=InputDate%></td>
		</tr>
		<tr>
			<td colspan="2">
				保险期间：<%=StartDate%>到<%=EndDate%></td>
		</tr>
		<%
			if ("CopyPrintNew".equals(printType)) {
		%>
		<tr>
			<td colspan="5">
				归属机构名称：<%=strComcname%></td>
		</tr>
		<tr>
			<td colspan="5">免赔信息：</td>
		</tr>
		<%
			}
		%>
		<%
			if ("CopyPrintNew".equals(printType)) {
		%>
		<tr>
			<td colspan="2">
				<b>主险：</b>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<table width="90%" align="center">
					<tr>
						<td align="left" width="30%">名称</td>
						<td align="left" width="30%">标的项目</td>
						<td align="left" width="30%">标的名称</td>
						<td align="left" width="20%">保额/限额</td>
					</tr>
					<tr>
						<td colspan="4">
							<hr align="center" width="90%" style="direction: inherit" />
						</td>
					</tr>
					<%
						for (i = 0; i < strKindName.length; i++) {
									if ("".equals(flag[i]) == false) {
										if (flag[i].substring(1, 2).equals("1")) {
					%>
					<tr>
						<td align="left"><%=strKindName[i]%></td>
						<td align="left"><%=strItemCode[i]%></td>
						<td align="left"><%=strItemdetailName[i]%></td>
						<td align="left"><%=new DecimalFormat("#,##0.00").format(dbAmount[i])%></td>
					</tr>
					<%
						}
									}
								}
					%>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<b>附加险：</b>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<table width="90%" align="center">
					<tr>
						<td align="left" width="30%">名称</td>
						<td align="left" width="30%">标的项目</td>
						<td align="left" width="30%">标的名称</td>
						<td align="left" width="20%">保额/限额</td>
					</tr>
					<tr>
						<td colspan="4">
							<hr align="center" width="90%" style="direction: inherit" />
						</td>
					</tr>
					<%
						for (i = 0; i < strKindName.length; i++) {
									if ("".equals(flag[i]) == false) {
										if (flag[i].substring(1, 2).equals("2")) {
					%>
					<tr>
						<td align="left"><%=strKindName[i]%></td>
						<td align="left"><%=strItemCode[i]%></td>
						<td align="left"><%=strItemdetailName[i]%></td>
						<td align="left"><%=new DecimalFormat("#,##0.00").format(dbAmount[i])%></td>
					</tr>
					<%
						}
									}
								}
					%>
				</table>
			</td>
		</tr>
		<tr></tr>
		<td colspan="2">
			<b>特别约定：</b>
			<%
				for (int prpCengageDtoIndex = 0; prpCengageDtoIndex < prpCengageDtoList.size(); prpCengageDtoIndex++) {
							if (strCengageDto[prpCengageDtoIndex] == null) {
							} else {
			%>
			<%=prpCengageDtoIndex + 1%>.&nbsp<%=strCengageDto[prpCengageDtoIndex]%>
			<%
				}
						}
			%>
		</td>
		</tr>
		<%
			} else {
		%>
		<tr></tr>
		<td colspan="2">
			<b>特别约定：</b>
			<%
				for (int prpCengageDtoIndex = 0; prpCengageDtoIndex < prpCengageDtoList.size(); prpCengageDtoIndex++) {
							if (strCengageDto[prpCengageDtoIndex] == null) {
							} else {
			%>
			<%=prpCengageDtoIndex + 1%>.&nbsp<%=strCengageDto[prpCengageDtoIndex]%>
			<%
				}
						}
			%>
		</td>
		</tr>
		<tr>
			<td colspan="2">
				<hr width="100%" align="center" style="direction: inherit" />
			</td>
		</tr>
		<%
			//责任险显示主险\附加险信息
					if (strClassCode.equals("04")) { //客户要求责任险显示主险、附加险的限额
		%>
		<tr>
			<td colspan="2">
				<table align="center" width="100%">
					<tr>
						<td colspan="7">
							<b>主险赔偿限额：</b>
						</td>
					</tr>
					<tr>
						<td align="center" width="20%">营业场所名称</td>
						<!--<td align="left" width="10%" >地址序号</td>-->
						<td align="left" width="10%">币别</td>
						<td align="left" width="20%">金额</td>
						<!--<td align="left" width="15%" >累积赔偿限额</td>
      	  <td align="left" width="10%" >每次赔偿限额</td>
          <td align="left" width="15%" >每次事故财产损失赔偿限额</td>
          <td align="left" width="15%" >每次事故人身伤亡赔偿限额</td>
          <td align="left" width="10%" >其中，每人赔偿限额</td>-->
					</tr>
					<tr>
						<td colspan="7">
							<hr align="center" width="90%" style="direction: inherit" />
						</td>
					</tr>
					<%
						for (i = 0; i < flag.length; i++) {
										if (flag[i].substring(1, 2).equals("1")) {
					%>
					<tr>
						<td align="center"><%=itemDetailName[i]%></td>
						<!--<td align="left"></td>-->
						<td align="left"><%=currency[i]%></td>
						<td align="left"><%=new DecimalFormat("#,##0.00").format(limitAmount[i])%></td>
					</tr>
					<%
						}
									}
					%>
					<tr>
						<td colspan="7">
							<b>附加险赔偿限额：</b>
						</td>
					</tr>
					<tr>
						<td align="center" width="20%">营业场所名称</td>
						<!-- <td align="left" width="10%" >地址序号</td>-->
						<td align="left" width="5%">币别</td>
						<td align="left" width="15%">金额</td>
					</tr>
					<tr>
						<td colspan="7">
							<hr align="center" width="90%" style="direction: inherit" />
						</td>
					</tr>
					<%
						for (i = 0; i < flag.length; i++) {
										if (flag[i].substring(1, 2).equals("2")) {
					%>
					<tr>
						<td align="center"><%=itemDetailName[i]%></td>
						<!-- <td align="left"></td>-->
						<td align="left"><%=currency[i]%></td>
						<td align="left"><%=new DecimalFormat("#,##0.00").format(dbAmount[i])%></td>
					</tr>
					<%
						}
									}
					%>
				</table>
			</td>
		</tr>
		<%
			} else {
		%>
		<tr>
			<td colspan="2">
				<b>主险：</b>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<table width="90%" align="center">
					<tr>
						<td align="left" width="30%">名称</td>
						<td align="left" width="30%">标的项目</td>
						<td align="left" width="30%">标的名称</td>
						<td align="left" width="20%">保额/限额</td>
					</tr>
					<tr>
						<td colspan="4">
							<hr width="100%" align="center" style="direction: inherit" />
						</td>
					</tr>
					<%
						for (i = 0; i < strKindName.length; i++) {
										if (!"".equals(flag[i])) {
											if (flag[i] != null && "1".equals(flag[i].substring(1, 2))) {
					%>
					<tr>
						<td align="left"><%=strKindName[i]%></td>
						<td align="left"><%=strItemCode[i]%></td>
						<td align="left"><%=strItemdetailName[i]%></td>
						<td align="left"><%=new DecimalFormat("#,##0.00").format(dbAmount[i])%></td>
					</tr>
					<%
						}
										}
									}
					%>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<hr width="100%" align="center" style="direction: inherit" />
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<b>附加险：</b>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<table width="90%" align="center">
					<tr>
						<td align="left" width="30%">名称</td>
						<td align="left" width="30%">标的项目</td>
						<td align="left" width="30%">标的名称</td>
						<td align="left" width="20%">保额/限额</td>
					</tr>
					<tr>
						<td colspan="4">
							<hr width="100%" align="center" style="direction: inherit" />
						</td>
					</tr>
					<%
						for (i = 0; i < strKindName.length; i++) {
										if (!"".equals(flag[i])) {
											if (flag[i] != null && "2".equals(flag[i].substring(1, 2))) {
					%>
					<tr>
						<td align="left"><%=strKindName[i]%></td>
						<td align="left"><%=strItemCode[i]%></td>
						<td align="left"><%=strItemdetailName[i]%></td>
						<td align="left"><%=new DecimalFormat("#,##0.00").format(dbAmount[i])%></td>
					</tr>
					<%
						}
										}
									}
					%>
				</table>
			</td>
		</tr>
		<%
			}
				}
		%>
		<tr>
			<td colspan="2">
				<hr width="100%" align="center" style="direction: inherit" />
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<b>批改情况：</b>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<table width="90%" align="center">
					<tr>
						<td align="left" width="30%">批单号</td>
						<td align="left" width="30%">批改原因</td>
						<td align="left" width="20%">批改时间</td>
						<td align="left" width="20%">核保人</td>
					</tr>
					<tr>
						<td colspan="4">
							<hr width="100%" align="center" style="direction: inherit" />
						</td>
					</tr>
					<%
						for (i = 0; i < strEndorseNo.length; i++) {
					%>
					<tr>
						<td align="left"><%=strEndorseNo[i]%></td>
						<td align="left"><%=strEndorType[i]%></td>
						<td align="left"><%=strEndorDate[i]%></td>
						<td align="left"><%=strUnderWriteName[i]%></td>
					</tr>
					<%
						}
					%>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<hr width="100%" align="center" style="direction: inherit" />
			</td>
		</tr>
		<%
			if ("CopyPrintNew".equals(printType)) {
		%>
		<tr>
			<td colspan="2">
				<b>报案信息：</b>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<table width="90%" align="center">
					<tr>
						<td colspan="5">
							报案号：<%=strRegistno%></td>
					</tr>
					<tr>
						<td colspan="2">
							报案时间：<%=strRegistReportDate%></td>
						<td colspan="2">
							联系人：<%=strRregistLinkerName%></td>
						<td colspan="1">
							联系电话：<%=strRegistPhoneNumber%></td>
					</tr>
					<tr>
						<td colspan="2">
							出险时间：<%=strRegistDamageStartDate%></td>
						<td colspan="2">
							出险地点：<%=strRegistDamageAddress%></td>
						<td colspan="1">
							出险原因：<%=strRegistDamageCode%></td>
					</tr>
					<tr>
						<td colspan="5">
							报案内容摘要：<%=strRegistContext%></td>
					</tr>
					<tr>
						<td colspan="5">
							<hr width="90%" align="center" style="direction: inherit" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<%
			} else {
		%>
		<tr>
			<td colspan="2">
				<b>报案信息：</b>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<table width="90%" align="center">
					<tr>
						<td colspan="5">
							报案号：<%=strRegistno%></td>
					</tr>
					<tr>
						<td colspan="2">
							报案时间：<%=strRegistReportDate%></td>
						<td colspan="2">
							联系人：<%=strRregistLinkerName%></td>
						<td colspan="1">
							联系电话：<%=strRegistPhoneNumber%></td>
					</tr>
					<tr>
						<td colspan="2">
							出险时间：<%=strRegistDamageStartDate%></td>
						<td colspan="2">
							出险地点：<%=strRegistDamageAddress%></td>
						<td colspan="1">
							出险原因：<%=strRegistDamageCode%></td>
					</tr>
					<tr>
						<td colspan="5">
							报案内容摘要：<%=strRegistContext%></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<hr width="100%" align="center" style="direction: inherit" />
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<b>保费到帳情况：</b>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<table width="90%" align="center">
					<tr>
						<td align="left">付款期数</td>
						<td align="left">批单号</td>
						<td align="left">应收</td>
						<td align="left">实收</td>
						<td align="left">到帳日期</td>
					</tr>
					<tr>
						<td colspan="5">
							<hr width="100%" align="center" style="direction: inherit" />
						</td>
					</tr>
					<%
						for (i = 0; i < strPlanEndorNo.length; i++) {
					%>
					<tr>
						<td align="left"><%=iPlanNo[i]%></td>
						<td align="left"><%=strPlanEndorNo[i]%></td>
						<td align="left"><%=new DecimalFormat("#,##0.00").format(dbPlanFee[i])%></td>
						<td align="left"><%=new DecimalFormat("#,##0.00").format(dbRealFee[i])%></td>
						<td align="left"><%=strPayDate[i]%></td>
					</tr>
					<%
						}
					%>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<hr width="100%" align="center" style="direction: inherit" />
			</td>
		</tr>
		<%
			}
		%>
		<tr>
			<td colspan="2">
				<b>歷史賠付紀錄：</b>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<table width="90%" align="center">
					<tr>
						<td align="center">赔案号</td>
						<td align="center">出险时间</td>
						<%
							if ("CopyPrintNew".equals(printType)) {
						%>
						<td align="center">赔付金额</td>
						<td align="center">结案日期</td>
						<td align="center">核赔人</td>
						<%
							} else {
						%>
						<td align="center">未决金额</td>
						<td align="center">赔付金额</td>
						<td align="center">结案日期</td>
						<td align="center">理算人</td>
						<td align="center">核赔人</td>
						<!-- <td align="left">赔付次数</td> -->
						<%
							}
						%>
					</tr>
					<tr>
						<td colspan="7">
							<hr width="100%" align="center" style="direction: inherit" />
						</td>
					</tr>
					<%
						for (int t = 0; t < strClaimNo.length; t++) {
					%>
					<tr>
						<td align="center"><%=strClaimNo[t]%></td>
						<td align="center"><%=strDamangeDate[t]%></td>
						<%
							if ("CopyPrintNew".equals(printType)) {
						%>
						<td align="center"><%=dbSumPaid[t]%></td>
						<td align="center"><%=arrEndCaseDate[t]%></td>
						<td align="center"><%=arrUnderWriteName[t]%></td>
						<%
							} else {
						%>
						<td align="center"><%=new DecimalFormat("0.00").format(dbSumClaim[t] - dbSumPaid[t])%></td>
						<td align="center"><%=dbSumPaid[t]%></td>
						<td align="center"><%=arrEndCaseDate[t]%></td>
						<td align="center"><%=arrHandlerName[t]%>
						</td>
						<td align="center"><%=arrUnderWriteName[t]%></td>
						<!-- <td align="left"> </td> -->
						<%
							}
						%>
					</tr>
					<%
						}
					%>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<hr width="100%" align="center" style="direction: inherit" />
			</td>
		</tr>
		<tr>
			<td>
				经办人：<%=strUserName%></td>
			<td>
				日期：<%=mDateTime%></td>
		</tr>
	</table>
	<%
		}
	%>
	<!--include打印按钮-->
	<jsp:include page="/common/print/PrintButton.jsp" />
</body>
</html>
