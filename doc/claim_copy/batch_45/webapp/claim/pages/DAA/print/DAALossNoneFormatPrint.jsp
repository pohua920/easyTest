<%--
****************************************************************************
* DESC       ：机动车辆保险定损报告/明细表打印页面
* AUTHOR     ：zhulianyu
* CREATEDATE ：2005-11-15
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%-- 初始化 --%>
<%@include file="DAALossNoneFormatPrintIni.jsp"%>
<html>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<link rel="stylesheet" type="text/css" href="/claim/DAA/print/StandardPrint.css">
<script language='javascript'>
	function noticeShow() {
		initshow.style.display = "none";
		initdisplay.style.display = "";
		notice.style.display = "";
		list.style.display = "none";
		initlist.style.display = "none";
	}
	function listShow() {
		initshow.style.display = "none";
		initdisplay.style.display = "";
		notice.style.display = "none";
		initlist.style.display = "";
	}
</script>
<%
	int intPageNum = 1;
	if (count5 > 2 || count4 > 2 || count3 > 2 || count2 > 1 || count1 > 2) {
		intPageNum = intComponentCount < 17 ? 2 : ((intComponentCount - 1) / pageCount + 3);
	} else {
		intPageNum = intComponentCount < 17 ? 1 : ((intComponentCount - 1) / pageCount + 2);
	}
%>
<body bgcolor="#FFFFFF" onload="loadForm()">
	<%--<div id="initshow" style="display:">
    <table width="50%"  border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td>
          <div align="center">
            <input name="loss" type="button" id="loss" class="button" value="定损报告" onClick="noticeShow()">
          </div></td>
        <td>
          <div align="center">
            <input name="list" type="submit" id="list" class="button" value="明细表" onClick="listShow()">
          </div></td>
      </tr>
    </table>
  </div>
  <div id="initdisplay" style="display:none">
    --%>
	<!-- 标题部分 -->
	<table width="95%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 11pt;">
		<tr height=30>
			<td colspan="3" align="center">
				<img src="/claim/images/LOGO.jpg" />
			</td>
		</tr>
		<tr height=30>
			<td colspan="3" align=center style="font-family: 宋体; font-size: 14pt;">
				<B><center>
						机动车辆保险车辆损失情况确认书
						<center>
							<B>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr height=20>
			<td align=left colspan="3" style="font-family: 宋体; font-size: 10pt;">
				被保险人：
				<%=strInsuredName%>
			</td>
		</tr>
		<tr>
			<td width="40%" style="font-family: 宋体; font-size: 10pt;">
				报案号：
				<%=strRegistNo%>
			</td>
			<td width="40%" style="font-family: 宋体; font-size: 10pt;">
				交强险承保公司：<%=strRemark%>
			</td>
			<td width="20%" align=right style="font-family: 宋体; font-size: 10pt;">
				共
				<%=intPageNum%>
				页&nbsp;&nbsp;&nbsp;第&nbsp;1&nbsp;页
			</td>
		</tr>
	</table>
	<!-- 主体部分 -->
	<table border="1" width="100%" align="center" cellspacing="0" cellpadding="0" style="border-collapse: collapse; font-family: 宋体; font-size: 11pt;" bordercolor="#111111">
		<tr>
			<td height=20 colspan="1" width="12%" align="center">牌照号码</td>
			<td height=20 colspan="2" width="22%" align="center">
				<%=licenseNo%>
			</td>
			<td height=20 colspan="1" width="14%" align="center">保单号码</td>
			<td height=20 colspan="4" align="center">
				<%=strPolicyNo%>
			</td>
		</tr>
		<tr>
			<td height=20 colspan="1" align="center">发动机号</td>
			<td height=20 colspan="2" align="center">
				<%=engineNo%>
			</td>
			<td height=20 colspan="1" align="center">车架号</td>
			<td height=20 colspan="4" align="center">
				<%=prpItemcarDto.getFrameNo()%>
			</td>
		</tr>
		<tr>
			<td height=20 colspan="1" align="center">厂牌型号</td>
			<td height=20 colspan="2" align="center">
				<%=brandName%>
			</td>
			<td width="12%" height=20 colspan="1" align="center">出险时间</td>
			<td height=20 colspan="1" width="17%" align="center">
				<%=strDamageStartDate%>
			</td>
			<td width="15%" height=20 align="center">保险险别</td>
			<td height=20 colspan="2" align="center">
				<%=kindName%>
			</td>
		</tr>
		<tr height=20>
			<td height=20 colspan="1" align="center">生产日期</td>
			<td height=20 colspan="2" align="center">
				<%=strEnrollDate%>
			</td>
			<td height=20 colspan="1" align="center">排 气 量(L)</td>
			<td height=20 colspan="1" align="center">
				<%=douExhaustScale%>
			</td>
			<td height=20 align="center">变速箱形式</td>
			<td height=20 colspan="2" align="center">□ 手动档 □ 自动档</td>
		</tr>
		<tr height=20>
			<td height=20 colspan="1" align="center">发动机形式</td>
			<td height=20 colspan="2" align="center">□ 化油器 □ 电喷</td>
			<td height=25 colspan="1" align="center">安全装置</td>
			<td height=25 colspan="4" align="center">□安全气囊 &nbsp;&nbsp; □ABS系统 &nbsp;&nbsp; □无安全装置</td>
			<!--        
        <td height=20 colspan="1">变速箱型式</td>
        <td height=20 colspan="1"> □ 自动 □ 手动</td>
        -->
		</tr>
		<!-- modify by zhyi 20110826 fubon-2206 -->
		<!-- 
			 <tr height=20>
				<td height=20 colspan="1" align="center">
					事故责任
				</td>
				<td height=20 colspan="3" align="center">
					□全部 □主要 □同等 □次要 □无责 □单方
				</td>
				<td height=20 colspan="1" align="center">
					送修时间
				</td>
				<td height=20 colspan="1" align="center">
					<%=strRepairStartDate%>
				</td>
				<td height=20 colspan="1" align="center" width="12%">
					修复竣工时间
				</td>
				<td height=20 colspan="1" align="center" width="8%">
					<%=strRepairEndDate%>
				</td>
			</tr>
			-->
		<!--
        <td height=25 colspan="1" align="center">安全装置</td>
        <td height=25 colspan="3" align="center"><%=strSafeDevice%></td>      
        -->
		<%--<table border=1 width="96%" align="center" cellspacing="0"
			cellpadding="2"
			style="font-family:宋体; font-size:11pt; border-collapse:collapse;display:none"
			bordercolor="#111111">
			<tr>
				<td height=20 colspan="1" width="8%" align="center">
					事故责任
				</td>
				<td height=20 colspan="1" width="38%" align="center">
					<%=strIndemnityDuty%>
				</td>
				<td height=20 colspan="1" width="8%" align="center">
					送修时间
				</td>
				<td height=20 colspan="1" width="18%" align="center">
					<%=strRepairStartDate%>
				</td>
				<td height=20 colspan="1" width="10%" align="center">
					修复竣工时间
				</td>
				<td height=20 colspan="1" align="center">
					<%=strRepairEndDate%>
				</td>
			</tr>
		</table>
		--%>
		<%--div id="notice" style="display:none"--%>
		<tr>
			<td height=20 colspan="4" width="40%" valign="top">
				<table border="1" frame=void width="100%" align="center" cellspacing="0" cellpadding="0" style="font-family: 宋体; font-size: 11pt; border-collapse: collapse" bordercolor="#111111">
					<tr>
						<td height=20 colspan="1" width="10%" align="center">序号</td>
						<td height=20 colspan="1" width="45%" align="center">更换配件名称</td>
						<td height=20 colspan="1" width="20%" align="center">数量</td>
						<td height=20 colspan="1" width="25%" align="center">配件价格</td>
					</tr>
					<%
						if (intComponentCount > 16) {
							for (int i = 0; i < 16; i++) {
								if (i == 0) {
					%>
					<tr>
						<td colspan="4" height=20 align="center">具体更换项目详见零配件更换项目清单</td>
					</tr>
					<%
						} else {
					%>
					<tr>
						<td height=20 colspan="1" align="center">&nbsp;</td>
						<td height=20 colspan="1" align="center">&nbsp;</td>
						<td height=20 colspan="1" align="center">&nbsp;</td>
						<td height=20 colspan="1" align="center">&nbsp;</td>
					</tr>
					<%
						}
							}
						} else {
							for (int i = 0; i < 16; i++) {
								if (i < intComponentCount) {
					%>
					<tr>
						<td height=20 colspan="1" align="center">
							<%=i + 1%>
						</td>
						<td height=20 colspan="1" align="center">
							<%=strCompName[i]%>
						</td>
						<td height=20 colspan="1" align="center">
							<%=new DecimalFormat("#").format(dblMaterialQuantity[i])%>
						</td>
						<td height=20 colspan="1" align="center">
							<%=new DecimalFormat("#,##0.00").format(dblMaterialUnitPrice[i])%>
						</td>
					</tr>
					<%
						} else {
					%>
					<tr>
						<td height=20 colspan="1" align="center">&nbsp;</td>
						<td height=20 colspan="1" align="center">&nbsp;</td>
						<td height=20 colspan="1" align="center">&nbsp;</td>
						<td height=20 colspan="1" align="center">&nbsp;</td>
					</tr>
					<%
						}
							}
						}
						double sumchange = 0.00;
						double sumManagerFee = 0.00;
						double sumFloatRateFee = 0.00;
						String strDblSumManHourFee = "";
						for (int i = 0; i < intComponentCount; i++) {
							sumchange += dblMaterialUnitPrice[i] * dblMaterialQuantity[i];
						}
						//modify by wangliguang 
						if (intCarLossCount > 0) {
							sumManagerFee = (sumchange) / 100 * sumManager[0]; //管理費。不能加運費後再乘
						}
						//add by zhyi 增加浮動比例
						if (intRepairFeeCount > 0) {
							sumFloatRateFee = dblSumManHourFee[0] / 100 * sumFloatRate[0];
						}
					%>
					<tr>
						<td height=20 colspan="3" align="center">材料费小计：</td>
						<td height=20 colspan="1" align="center">
							<%=new DecimalFormat("#,##0.00").format(sumchange)%>
						</td>
					</tr>
					<tr>
						<td height=20 colspan="3" align="center">零配件管理费：</td>
						<td height=20 colspan="1" align="center">
							<%--modify by wangliguangs --%>
							<%=new DecimalFormat("#,##0.00").format(sumManagerFee)%>
						</td>
					</tr>
					<tr>
						<td height=20 colspan="3" align="center">旧件回收项目：</td>
						<td height=20 colspan="1" align="center"></td>
					</tr>
				</table>
			</td>
			<td height=20 colspan="4" width="60%" valign="top">
				<table border=1 width="100%" frame=void align="center" cellspacing="0" cellpadding="0" style="font-family: 宋体; font-size: 11pt; border-collapse: collapse" bordercolor="#111111">
					<tr>
						<td height=20 colspan="1" width="70%" align="center">修理项目</td>
						<td height=20 colspan="1" width="30%" align="center">工时费</td>
					</tr>
					<%
						if (count5 <= 2) {
					%>
					<tr>
						<td height=20 colspan="1">事故拆装：</td>
						<td height=20 colspan="1"></td>
						<td height=20 colspan="1"></td>
					</tr>
					<%
						for (int i = 0; i < intRepairFeeCount && inttemp < 2; i++) {
								if (strRepairType[i].equals("事故拆装")) {
									inttemp++;
					%>
					<tr>
						<td height=20 colspan="1" align="center">
							<%=strCompName1[i]%>
						</td>
						<td height=20 colspan="1" align="center">
							<%=new DecimalFormat("#,##0.00").format(dblManHourFee[i])%>
						</td>
					</tr>
					<%
						}
							}
							for (int i = 0; i < 2 - inttemp; i++) {
					%>
					<tr>
						<td height=20 colspan="1"></td>
						<td height=20 colspan="1"></td>
					</tr>
					<%
						}
							inttemp = 0;
						} else {
					%>
					<tr>
						<td height=20 colspan="1">事故拆装：</td>
						<td height=20 colspan="1" align="center">
							<%=new DecimalFormat("#,##0.00").format(sumhourfee5)%>
						</td>
					</tr>
					<tr>
						<td height=20 colspan="2">具体内容详见零配件更换清单</td>
					</tr>
					<tr>
						<td height=20 colspan="1"></td>
						<td height=20 colspan="1"></td>
					</tr>
					<%
						}
						inttemp = 0;
					%>
					<%
						if (count1 <= 2) {
					%>
					<tr>
						<td height=20 colspan="1">事故钣金：</td>
						<td height=20 colspan="1"></td>
					</tr>
					<%
						for (int i = 0; i < intRepairFeeCount && inttemp < 2; i++) {
								if (strRepairType[i].equals("事故钣金")) {
									inttemp++;
					%>
					<tr>
						<td height=20 colspan="1" align="center">
							<%=strCompName1[i]%>
						</td>
						<td height=20 colspan="1" align="center">
							<%=new DecimalFormat("#,##0.00").format(dblManHourFee[i])%>
						</td>
					</tr>
					<%
						}
							}
							for (int i = 0; i < 2 - inttemp; i++) {
					%>
					<tr>
						<td height=20 colspan="1"></td>
						<td height=20 colspan="1"></td>
					</tr>
					<%
						}
							inttemp = 0;
						} else {
					%>
					<tr>
						<td height=20 colspan="1">事故钣金：</td>
						<td height=20 colspan="1" align="center">
							<%=new DecimalFormat("#,##0.00").format(sumhourfee1)%>
						</td>
					</tr>
					<tr>
						<td height=20 colspan="2">具体内容详见零配件更换清单</td>
					</tr>
					<tr>
						<td height=20 colspan="1"></td>
						<td height=20 colspan="1"></td>
					</tr>
					<%
						}
						inttemp = 0;
					%>
					<%
						if (count3 <= 2) {
					%>
					<tr>
						<td height=20 colspan="1">机修：</td>
						<td height=20 colspan="1"></td>
					</tr>
					<%
						for (int i = 0; i < intRepairFeeCount && inttemp < 2; i++) {
								if (strRepairType[i].equals("事故机修")) {
									inttemp++;
					%>
					<tr>
						<td height=20 colspan="1" align="center">
							<%=strCompName1[i]%>
						</td>
						<td height=20 colspan="1" align="center">
							<%=new DecimalFormat("#,##0.00").format(dblManHourFee[i])%>
						</td>
					</tr>
					<%
						}
							}
							for (int i = 0; i < 2 - inttemp; i++) {
					%>
					<tr>
						<td height=20 colspan="1"></td>
						<td height=20 colspan="1"></td>
					</tr>
					<%
						}
							inttemp = 0;
						} else {
					%>
					<tr>
						<td height=20 colspan="1">机修：</td>
						<td height=20 colspan="1" align="center">
							<%=new DecimalFormat("#,##0.00").format(sumhourfee3)%>
						</td>
					</tr>
					<tr>
						<td height=20 colspan="2">具体内容详见零配件更换清单</td>
					</tr>
					<tr>
						<td height=20 colspan="1"></td>
						<td height=20 colspan="1"></td>
					</tr>
					<%
						}
						inttemp = 0;
					%>
					<%
						if (count4 <= 2) {
					%>
					<tr>
						<td height=20 colspan="1">电工：</td>
						<td height=20 colspan="1"></td>
					</tr>
					<%
						for (int i = 0; i < intRepairFeeCount && inttemp < 2; i++) {
								if (strRepairType[i].equals("事故电工")) {
									inttemp++;
					%>
					<tr>
						<td height=20 colspan="1" align="center">
							<%=strCompName1[i]%>
						</td>
						<td height=20 colspan="1" align="center">
							<%=new DecimalFormat("#,##0.00").format(dblManHourFee[i])%>
						</td>
					</tr>
					<%
						}
							}
							for (int i = 0; i < 2 - inttemp; i++) {
					%>
					<tr>
						<td height=20 colspan="1"></td>
						<td height=20 colspan="1"></td>
					</tr>
					<%
						}
							inttemp = 0;
						} else {
					%>
					<tr>
						<td height=20 colspan="1">电工：</td>
						<td height=20 colspan="1" align="center">
							<%=new DecimalFormat("#,##0.00").format(sumhourfee4)%>
						</td>
					</tr>
					<tr>
						<td height=20 colspan="2">具体内容详见零配件更换清单</td>
					</tr>
					<tr>
						<td height=20 colspan="1"></td>
						<td height=20 colspan="1"></td>
					</tr>
					<%
						}
						inttemp = 0;
					%>
					<%
						if (count2 <= 1) {
					%>
					<tr>
						<td height=20 colspan="1">事故油漆：</td>
						<td height=20 colspan="1"></td>
					</tr>
					<%
						for (int i = 0; i < intRepairFeeCount && inttemp < 2; i++) {
								if (strRepairType[i].equals("事故喷漆")) {
									inttemp++;
					%>
					<tr>
						<td height=20 colspan="1" align="center">
							<%=strCompName1[i]%>
						</td>
						<td height=20 colspan="1" align="center">
							<%=new DecimalFormat("#,##0.00").format(dblManHourFee[i])%>
						</td>
					</tr>
					<%
						}
							}
							for (int i = 0; i < 2 - inttemp; i++) {
					%>
					<tr>
						<td height=20 colspan="1"></td>
						<td height=20 colspan="1"></td>
					</tr>
					<%
						}
							inttemp = 0;
						} else {
					%>
					<tr>
						<td height=20 colspan="1">事故油漆：</td>
						<td height=20 colspan="1" align="center">
							<%=new DecimalFormat("#,##0.00").format(sumhourfee2)%>
						</td>
					</tr>
					<tr>
						<td height=20 colspan="2">具体内容详见零配件更换清单</td>
					</tr>
					<tr>
						<td height=20 colspan="1"></td>
						<td height=20 colspan="1"></td>
					</tr>
					<%
						}
						inttemp = 0;
					%>
					<tr>
						<td height=20 colspan="1" align="center">工时费小计：</td>
						<td height=20 colspan="1" align="center">
							<%=new DecimalFormat("#,##0.00").format(dblSumManHourFee[0])%>
						</td>
					</tr>
					<tr>
						<td height=20 colspan="1" align="center">浮动比例费：</td>
						<td height=20 colspan="1" align="center">
							<%--modify by wangliguangs --%>
							<%=new DecimalFormat("#,##0.00").format(sumFloatRateFee)%>
						</td>
					</tr>
					<tr>
						<!-- modify by liuwei at 2011-08-16 增加运费显示项 start -->
						<td height=20 colspan="1" align="center">运费：</td>
						<td height=20 colspan="1" align="center">
							<%=new DecimalFormat("#,##0.00").format(sumTransFee[0])%>
						</td>
						<!-- modify by liuwei at 2011-08-16 增加运费显示项 end -->
					</tr>
					<tr>
						<td height=20 colspan="1" align="center">残值扣除：</td>
						<td height=20 colspan="1" align="center">
							<%=sumRest%>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td height=20 colspan="8">
				<b> 本页未尽之栏目，请见零配件更换项目清单。 </b>
			</td>
		</tr>
		<tr valign="top">
			<td colspan="8" height="80">
				1．经事故有关各方协商，完全同意按以上核定的价格修理。总计工料费人民币 <u>&nbsp;<%=MoneyUtils.toChinese(dblSumManHourFee[0] + dblSumMaterialFee[0] - Double.parseDouble(sumRest) + sumTransFee[0] + sumManagerFee + sumFloatRateFee, strCurrency)%>&nbsp;
				</u> (￥<u> <%=new DecimalFormat("#,##0.00").format(dblSumManHourFee[0] + dblSumMaterialFee[0] - Double.parseDouble(sumRest) + sumTransFee[0] + sumManagerFee + sumFloatRateFee)%></u> )。 <br> 2．维修单位同意按以上核定项目保质保量修车。如有违背，保险公司有权向维修单位追回价格差额。 <br> 3．维修单位保证在 &nbsp;&nbsp;&nbsp;&nbsp;
				日内保质保量完成修理，如违约，愿意赔偿因拖延时间而造成被保险人的经济损失。 <br> 4．维修单位对以上核定的修理项目和价格无任何异议。如存在修理质量或价格超标，由维修单位负责全部责任。 <br> 5、其它约定： <br> <br> <br>
			</td>
		</tr>
		<tr>
			<td colspan="2" height="100" align="left" valign="middle">
				保险公司（签章） <br> <br> 查勘定损人： <br> 日期： <br> 核价人： <br> 日期： <br>
			</td>
			<td colspan="2" height="100" valign="middle" align="left">
				被保险人（签章）： <br> <br> <br> <br> <br> 日期： <br>
			</td>
			<td colspan="2" height="100" align="left" valign="middle">
				修理厂（签章）： <br> <br> <br> <br> <br> 日期： <br>
			</td>
			<td colspan="2" height="100" align="left" valign="middle">
				第三者或受损方（签章）： <br> <br> <br> <br> <br> 日期： <br>
			</td>
		</tr>
	</table>
	</div>
	<%--modify by liuwei at 2011-02-11 start--%>
	<%-- include打印按钮 --%>
	<%-- <jsp:include page="/common/print/PrintButton.jsp" /> --%>
	<jsp:include page="/common/print/LossPrintButton.jsp" />
	<%--modify by liuwei at 2011-02-11 end--%>
</body>
</html>
