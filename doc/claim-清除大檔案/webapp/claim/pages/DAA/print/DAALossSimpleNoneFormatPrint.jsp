<%--
****************************************************************************
* DESC       ：机动车辆保险速决赔案车辆损失情况确认书打印页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-11-16
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%-- 初始化 --%>
<%@include file="DAALossSimpleNoneFormatPrintIni.jsp"%>
<html>
<link rel="stylesheet" type="text/css" href="/claim/DAA/print/StandardPrint.css">
<body bgcolor="#FFFFFF" onload="loadForm()">
	<!-- 标题部分 -->
	<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td colspan="2" height="40" align=top align=center style="font-family: 宋体; font-size: 14pt;">
				<B>机动车辆保险速决赔案车辆损失情况确认书<B>
			</td>
		</tr>
		<tr>
			<td align=left id="tdClaimNo" width="50%" style="font-family: 宋体; font-size: 10pt;">报案编号：</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td align=left id="tdClauseType" width="50%" style="font-family: 宋体; font-size: 10pt;">定损单编号：</td>
			<td align=right id="tdComName" width="50%" style="font-family: 宋体; font-size: 10pt;">承保公司：</td>
		</tr>
	</table>
	<!-- 主体部分 -->
	<table border=1 width="92%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
		<tr>
			<td height="20" colspan="5" id="tdInsuredName">被保险人：</td>
			<td height="20" colspan="5" id="tdDamageStartDate">出险时间：</td>
		</tr>
		<tr>
			<td height="20" colspan="5" id="tdPolicyNo">保险单号：</td>
			<td height="20" colspan="5" id="tdDamageAddress">出险地点：</td>
		</tr>
		<tr>
			<td height="20" colspan="5" id="tdSumAmount">保险金额：</td>
			<td height="20" colspan="5" id="tdIndemnityDuty">事故责任：</td>
		</tr>
		<tr>
			<td height="20" colspan="4" id="tdBrandName">厂牌型号：</td>
			<td height="20" colspan="3" id="tdLicenseNo">号牌号码：</td>
			<td height="20" colspan="3" id="tdGearboxType">变速箱型式：</td>
		</tr>
		<tr>
			<td height="20" colspan="4">制造年份：</td>
			<td height="20" colspan="3" id="tdEngineNo">发动机号：</td>
			<td height="20" colspan="3" id="">发动机型号：</td>
		</tr>
		<tr>
			<td height="20" colspan="4" id="tdCheckDate">定损时间：</td>
			<td height="20" colspan="6" id="tdCheckSite">定损地点：</td>
		</tr>
		<tr>
			<td height="20" colspan="7" id="tdFrameNo">车架号码：</td>
			<td height="20" colspan="3" id="tdRepairStartDate">送修时间：</td>
		</tr>
		<tr>
			<td height="20" colspan="7" id="tdQuoteCompanyGrade">报价公司：</td>
			<td height="20" colspan="3" id="tdRepairEndDate">修复竣工时间：</td>
		</tr>
		<tr valign="top">
			<td colspan="10" height="50">损失部位及程度概述：</td>
		</tr>
		<tr height="20" align="center">
			<td rowspan=11 width="5%">
				零<br>部<br>件<br>更<br>换<br>项<br>目<br>清<br>单
			</td>
			<td rowspan=2 width="5%">序号</td>
			<td colspan=2>零配件</td>
			<td rowspan=2 width="12%">
				配件<br>
				<br>编号
			</td>
			<td rowspan=2 width="8%">数量</td>
			<td rowspan=2 width="10%">工时费
			<td rowspan=2 width="10%">估计价格</td>
			<td rowspan=2 width="10%">报价</td>
			<td rowspan=2 width="12%">小计</td>
		</tr>
		<tr height="20">
			<td width="14%" align="center">部位</td>
			<td width="14%" align="center">名称</td>
		</tr>
		<%
			int intComponentCountTmp = 0; //打印的换件项目信息记录数

			//换件项目信息
			if (intComponentCount > 8) //项数超过指定打印范围
			{
				intComponentCountTmp = 7;
			} else {
				intComponentCountTmp = intComponentCount;
			}

			for (index = 0; index < intComponentCountTmp; index++) {
				prpLcomponentDto = (PrpLcomponentDto) certainLossDto.getPrpLcomponentDtoList().get(index);

				//dblQuantity    += prpLcomponentDto.getQuantity();
				dblQuantity += prpLcomponentDto.getQuantity();
				//dblManHourFee1 += prpLcomponentDto.getManHourFee();
				dblManHourFee1 += prpLcomponentDto.getManHourFee();
				dblQueryPrice += prpLcomponentDto.getQueryPrice();
				dblQuotedPrice += prpLcomponentDto.getQuotedPrice();
				dbSum1 += prpLcomponentDto.getQuantity() * prpLcomponentDto.getQuotedPrice();
		%>
		<tr height="20">
			<td align="center">
				<%=index + 1%>
			</td>
			<td>
				<%=StringConvert.encode(prpLcomponentDto.getPartDesc())%>
			</td>
			<td>
				<%=StringConvert.encode(prpLcomponentDto.getCompName())%>
			</td>
			<td>
				<%=StringConvert.encode(prpLcomponentDto.getCompCode())%>
			</td>
			<%
				//if( prpLcomponentDto.getQuantity()==0 )
					if (prpLcomponentDto.getQuantity() == 0) {
			%>
			<td>&nbsp;</td>
			<%
				} else {
			%>
			<td>
				<%=prpLcomponentDto.getQuantity()%>
			</td>
			<%
				}
					//if( prpLcomponentDto.getManHourFee()==0 )
					if (prpLcomponentDto.getManHourFee() == 0) {
			%>
			<td>&nbsp;</td>
			<%
				} else {
			%>
			<td>
				<%=new DecimalFormat("#,##0.00").format(prpLcomponentDto.getManHourFee())%>
			</td>
			<%
				}
					if (prpLcomponentDto.getQueryPrice() == 0) {
			%>
			<td>&nbsp;</td>
			<%
				} else {
			%>
			<td>
				<%=new DecimalFormat("#,##0.00").format(prpLcomponentDto.getQueryPrice())%>
			</td>
			<%
				}
					if (prpLcomponentDto.getQuotedPrice() == 0) {
			%>
			<td>&nbsp;</td>
			<%
				} else {
			%>
			<td>
				<%=new DecimalFormat("#,##0.00").format(prpLcomponentDto.getQuotedPrice())%>
			</td>
			<%
				}
					//modify by caopeng start at 2005-12-20
					if ((prpLcomponentDto.getQuantity() * prpLcomponentDto.getQuotedPrice()) == 0) {
			%>
			<td>&nbsp;</td>
			<%
				} else {
			%>
			<td>
				<%=new DecimalFormat("#,##0.00").format((prpLcomponentDto.getQuantity() * prpLcomponentDto.getQuotedPrice()))%>
			</td>
			<%
				}
					//modify by caopeng end at 2005-12-20
			%>
		</tr>
		<%
			if (intComponentCount > 8) //项数超过指定打印范围
		%>
		<tr height="20">
			<td align="center">8</td>
			<td colspan="10">余项请见清单</td>
		</tr>
		<%
			}

			for (index = 0; index < 8 - intComponentCountTmp; index++) {
		%>
		<tr height="20">
			<td align="center"><%=intComponentCount + index + 1%></td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<%
			}
		%>
		<tr height="20">
			<td colspan="4" align="center">小 计</td>
			<td><%=DataUtils.zeroToEmpty(dblQuantity)%></td>
			<td><%=DataUtils.zeroToEmpty(dblManHourFee1)%></td>
			<td><%=DataUtils.zeroToEmpty(dblQueryPrice)%></td>
			<td><%=DataUtils.zeroToEmpty(dblQuotedPrice)%></td>
			<td><%=DataUtils.zeroToEmpty(dbSum1)%></td>
		</tr>
		<tr height="20" align="center">
			<td rowspan=10>
				修<br>理<br>项<br>目<br>清<br>单
			</td>
			<td>序号</td>
			<td colspan=4>修理项目名称</td>
			<td>工时</td>
			<td>工时费</td>
			<td>材料费</td>
			<td>小计</td>
		</tr>
		<%
			int intRepairFeeCountTmp = 0; //打印的修理项目信息记录数

			//修理项目信息
			if (intRepairFeeCount > 8) //项数超过指定打印范围
			{
				intRepairFeeCountTmp = 5;
			} else {
				intRepairFeeCountTmp = intRepairFeeCount;
			}

			for (index = 0; index < intRepairFeeCountTmp; index++) {
				prpLrepairFeeDto = (PrpLrepairFeeDto) certainLossDto.getPrpLrepairFeeDtoList().get(index);

				//dblManHour     += prpLrepairFeeDto.getManHour();
				dblManHour += prpLrepairFeeDto.getManHour();
				// dblManHourFee2 += prpLrepairFeeDto.getManHourFee();
				dblManHourFee2 += prpLrepairFeeDto.getManHourFee();
				//dblMaterialFee += prpLrepairFeeDto.getMaterialFee();
				dblMaterialFee += prpLrepairFeeDto.getMaterialFee();
				dbSum2 = prpLrepairFeeDto.getManHour() * prpLrepairFeeDto.getManHourFee() + prpLrepairFeeDto.getMaterialFee();
				//modify by caopeng start at 2005-12-20
				dbSumSum2 += dbSum2;
				//modify by caopeng end at 2005-12-20
		%>
		<tr height="20">
			<td align="center">
				<%=index + 1%>
			</td>
			<td colspan=4>
				<%=StringConvert.encode(prpLrepairFeeDto.getCompName())%>
			</td>
			<%
				//if( prpLrepairFeeDto.getManHour()==0 )
					if (prpLrepairFeeDto.getManHour() == 0) {
			%>
			<td>&nbsp;</td>
			<%
				} else {
			%>
			<td>
				<%=new DecimalFormat("#,##0.00").format(prpLrepairFeeDto.getManHour())%>
			</td>
			<%
				}
					//if( prpLrepairFeeDto.getManHourFee()==0 )
					if (prpLrepairFeeDto.getManHourFee() == 0) {
			%>
			<td>&nbsp;</td>
			<%
				} else {
			%>
			<td>
				<%=new DecimalFormat("#,##0.00").format(prpLrepairFeeDto.getManHourFee())%>
			</td>
			<%
				}
					//if( prpLrepairFeeDto.getMaterialFee()==0 )
					if (prpLrepairFeeDto.getMaterialFee() == 0) {
			%>
			<td>&nbsp;</td>
			<%
				} else {
			%>
			<td>
				<%=new DecimalFormat("#,##0.00").format(prpLrepairFeeDto.getMaterialFee())%>
			</td>
			<%
				}
			%>
			<td>
				<%=new DecimalFormat("#,##0.00").format(dbSum2)%>
			</td>
		</tr>
		<%
			}

			if (intRepairFeeCount > 8) //项数超过指定打印范围
			{
		%>
		<tr height="20">
			<td align="center">8</td>
			<td colspan="10">余项请见清单</td>
		</tr>
		<%
			}

			for (index = 0; index < 8 - intRepairFeeCountTmp; index++) {
		%>
		<tr height="20">
			<td align="center"><%=intRepairFeeCount + index + 1%></td>
			<td colspan=4>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<%
			}
		%>
		<tr height="20">
			<td colspan="5" align="center">小 计</td>
			<td><%=DataUtils.zeroToEmpty(dblManHour)%></td>
			<td><%=DataUtils.zeroToEmpty(dblManHourFee2)%></td>
			<td><%=DataUtils.zeroToEmpty(dblMaterialFee)%></td>
			<td>
				<%
					//add by caopeng start at 2005-12-20
				%><%=DataUtils.zeroToEmpty(dbSumSum2)%>
				<%
					//add by caopeng end at 2005-12-20
				%>
			</td>
		</tr>
		<tr align="center" valign="middle">
			<td colspan="10">
				<table width="100%" border="0" height="100%" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
					<tr>
						<td align="left" width="75%" height="33%" id="tdComContext">&nbsp;</td>
						<td align="left" width="20%" height="33%" id="tdComSumDefLoss">&nbsp;</td>
						<td align="right" width="5%" height="33%">元）</td>
					</tr>
					<tr>
						<td align="left" width="75%" height="33%" id="tdRepContext"></td>
						<td align="left" width="20%" height="33%" id="tdRepSumDefLoss">&nbsp;</td>
						<td align="right" width="5%" height="33%">元）</td>
					</tr>
					<tr>
						<td align="left" width="75%" height="33%" id="tdProContext">&nbsp;</td>
						<td align="left" width="20%" height="33%" id="tdProSumReject">&nbsp;</td>
						<td align="right" width="5%" height="33%">元）</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr valign="middle">
			<td colspan="10" height="60">
				&nbsp;&nbsp;&nbsp;&nbsp; 保险合約当事人各方经协商，同意按本确认书载明的修理及更换项目为确定本次事故损失范围的依据，並达成如下协议：<br> &nbsp;&nbsp;&nbsp;&nbsp; 本确认书所列修理费总计金额均已包含各项税费，其为保险公司认定的损失最高赔付金额，超过此金额部分，保险公司不予赔付。<br>
			</td>
		</tr>
		<tr>
			<td colspan="4" height="70" align="center" valign="middle">
				<table width="100%" border="0" height="100%" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
					<tr>
						<td valign="top">
							保险公司<br>
							<br> 签章：<br>
						</td>
					</tr>
					<tr>
						<td align="right" valign="bottom">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日&nbsp;</td>
					</tr>
				</table>
			</td>
			<td colspan="3" colspan="2" height="70" valign="middle" align="center">
				<table width="100%" border="0" height="100%" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
					<tr>
						<td valign="top">
							被保险人<br>
							<br> 签章：<br>
						</td>
					</tr>
					<tr>
						<td align="right" valign="bottom">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日&nbsp;</td>
					</tr>
				</table>
			</td>
			<td colspan="3" height="70" align="center" valign="middle">
				<table width="100%" border="0" height="100%" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td align="right" valign="bottom">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日&nbsp;</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<%-- include打印按钮 --%>
	<jsp:include page="/common/print/PrintButton.jsp" />
</body>
</html>
