<%--
****************************************************************************
* DESC       ：机动车辆强制保险赔款计算书列印页面
* AUTHOR     ：理赔组
* CREATEDATE ：22004-11-16
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%-- 初始化 --%>
<%@include file="DAADCompensateNoneFormatPrintIni.jsp"%>
<html>
<head>
<title>机动车保险赔款计算书列印</title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<meta http-equiv="pragma" CONTENT="no-cache">
<meta http-equiv="Cache-Control" CONTENT="no-cache,   must-revalidate">
<style type="text/css">
<!--
.style2 {
	font-size: 10pt
}

.STYLE3 {
	font-size: 10px
}

.STYLE6 {
	font-size: 14px
}
-->
</style>
</head>
<body bgcolor="#FFFFFF" onLoad="loadForm();">
	<form name="fm">
		<!-- 标题部分 -->
		<table width="100%" align="center" cellspacing="0" cellpadding="0" border="0">
			<!--<tr>
          <td colspan="3" height="40" align=center style="font-family:宋体; font-size:14pt;">
            <img src="/claim/images/LOGO.jpg"/>
          </td>
        </tr>-->
			<tr>
				<td colspan="3" height="30" align=center style="font-family: 宋体; font-size: 16pt;">
					<p align=center>
						<B>机动车辆商业保险赔款计算书<B>
					</p>
				</td>
			</tr>
			<tr>
				<td width="40%" align=left style="font-family: 宋体; font-size: 9pt;">承保公司（签章）：</td>
				<td width="40%" align=left style="font-family: 宋体; font-size: 9pt;">
					交强险承保公司：<%=strCompany%>
				</td>
				<td width="30%" align=left style="font-family: 宋体; font-size: 9pt;"></td>
			</tr>
			<tr>
				<td width="30%" align=left style="font-family: 宋体; font-size: 9pt;">
					商业保险赔款计算书号：<%=strCompensateNo%>
				</td>
				<td width="30%" align=left style="font-family: 宋体; font-size: 9pt;">
					交强险赔款计算书号：<%=strCCompensateNo%>
				</td>
				<td width="30%" align="right" style="font-family: 宋体; font-size: 9pt;">
					赔案号：<%=strClaimNo%>
				</td>
			</tr>
		</table>
		<!--- <tr>
          <td  width="60%" align=left id="tdPolicyNo" style="font-family:宋体; font-size:9pt;">
            保险单号：
          </td>
        </tr>
        
        <tr>
          <td   width="60%" align=left id="tdRegistNo" style="font-family:宋体; font-size:9pt;">
            报案编号：
          </td>
          <td   width="40%" align=left id="tdCompensateNo" style="font-family:宋体; font-size:9pt;">
            赔款计算书号：
          </td>
        </tr>--->
		<!-- 主体部分 -->
		<table width="100%" align="center" cellspacing="0" cellpadding="2" border="1" style="font-size: 10pt; border-collapse: collapse; bordercolor: #111111;">
			<tr>
				<td width="15%" align="left" style="font-family: 宋体; font-size: 9pt;">被保险人</td>
				<td width="50%" id="tdInsuredName" colspan=4><%=prpLregistDto.getInsuredName()%></td>
				<td width="35%" colspan="2" align="center" style="font-family: 宋体; font-size: 9pt;">
					<b>责任交强险赔偿情况</b>
				</td>
			</tr>
			<tr>
				<td align="left" style="font-family: 宋体; font-size: 9pt;">商业险保单号</td>
				<td width="20%" id="tdPolicyNo" colspan=2></td>
				<td width="15%" align="left" style="font-family: 宋体; font-size: 9pt;">商业保险批单号</td>
				<td width="15%" id="tdEndorseNo" colspan=1></td>
				<td width="17%" align="left" style="font-family: 宋体; font-size: 9pt;">
					<b>医疗费用赔偿限额</b>
				</td>
				<td width="18%" colspan=1><%=strLimit1%></td>
			</tr>
			<tr>
				<td align="left" style="font-family: 宋体; font-size: 9pt;">厂牌型号</td>
				<td width="20%" id="tdBrandName" colspan=2></td>
				<td width="15%" align="left" style="font-family: 宋体; font-size: 9pt;">号牌号码</td>
				<td width="15%" id="tdLicenseNo" colspan=1></td>
				<td width="17%" align="left" style="font-family: 宋体; font-size: 9pt;">
					<b>死亡伤残赔偿限额</b>
				</td>
				<td width="18%" colspan=1><%=strLimit2%></td>
			</tr>
			<tr>
				<td align="left" style="font-family: 宋体; font-size: 9pt;">新车购置价</td>
				<td width="20%" id="tdPurchasePrice" colspan=2><%=new DecimalFormat("#,##0.00").format(prpItemCarDto.getPurchasePrice())%></td>
				<td width="15%" align="left" style="font-family: 宋体; font-size: 9pt;">事故责任</td>
				<td width="15%" id="tdIndemnityDuty" colspan=1></td>
				<td width="17%" align="left" style="font-family: 宋体; font-size: 9pt;">
					<b>财产损失赔偿限额</b>
				</td>
				<td width="18%" colspan=1><%=strLimit2%></td>
			</tr>
			<tr>
				<td align="left" style="font-family: 宋体; font-size: 9pt;">出险原因</td>
				<td width="20%" id="tdDamageName" colspan=2></td>
				<td width="15%" align="left" style="font-family: 宋体; font-size: 9pt;">责任免赔率</td>
				<td width="15%" colspan=1><%=strLossRate%>%
				</td>
				<td width="17%" align="left" style="font-family: 宋体; font-size: 9pt;">责任比例</td>
				<td width="18%" id="tdIndemnityDutyRate" colspan=1><%=new DecimalFormat("#,##0.00").format(prpLcompensateDto.getIndemnityDutyRate())%>%
				</td>
			</tr>
			<tr>
				<td align="left" style="font-family: 宋体; font-size: 9pt;">出险时间</td>
				<td width="20%" id="tdDamageStartDate" colspan=2></td>
				<td width="15%" align="left" style="font-family: 宋体; font-size: 9pt;">绝对免赔率</td>
				<td width="15%" colspan=1><%=strDeduLossRate%>%
				</td>
				<td width="17%" align="left" style="font-family: 宋体; font-size: 9pt;">赔偿比例</td>
				<td width="18%" colspan=1><%=strClaimRate%>%
				</td>
			</tr>
			<tr>
				<td align="left" style="font-family: 宋体; font-size: 9pt;">指定驾驶员</td>
				<td width="20%" id="tdDriverName1" colspan=2><%=strDriverName1%></td>
				<td width="15%" align="left" style="font-family: 宋体; font-size: 9pt;">绝对免赔额</td>
				<td width="15%" colspan=1><%=strDeduLossFee%></td>
				<td width="17%" align="left" style="font-family: 宋体; font-size: 9pt;">赔案类别</td>
				<td width="18%" id="tdClaimType" colspan=1><%=strClaimType%></td>
			</tr>
			<tr>
				<td align="left" style="font-family: 宋体; font-size: 9pt;">出险驾驶员</td>
				<td width="20%" id="tdDriverName" colspan=2><%=strDriverName%></td>
				<td align="left" style="font-family: 宋体; font-size: 9pt;">出险地点</td>
				<td id="tdDamageAddress" colspan=3></td>
			</tr>
			<tr>
				<td align="left" style="font-family: 宋体; font-size: 9pt;">行驶網域</td>
				<td width="20%" id="tdRunAreaName" colspan=2></td>
				<td align="left" style="font-family: 宋体; font-size: 9pt;">出险網域类别</td>
				<td id="tdDamageAddressType" colspan=3></td>
			</tr>
			<tr>
				<td align="left" style="font-family: 宋体; font-size: 9pt;">事故处理部门</td>
				<td width="20%" id="tdHandleUnit" colspan=2></td>
				<td align="left" style="font-family: 宋体; font-size: 9pt;">人员伤亡情况</td>
				<td colspan=3><%=personInjure%></td>
			</tr>
			<tr>
				<td align="left" style="font-family: 宋体; font-size: 9pt;">损失程度</td>
				<td width="20%" id="tdSumClaim" colspan=2><%=lossDesc%></td>
				<td align="left" style="font-family: 宋体; font-size: 9pt;">保险期限</td>
				<td id="tdInsuredDate" colspan=3><%=personInjure%></td>
			</tr>
			<!--     
         <tr height="18">
          <td align="left" width="15%">&nbsp;出险原因</td>
          <td id="tdDamageName" colspan="3">&nbsp;</td>
          <td align="center" width="15%">&nbsp;事故责任</td>
          <td id="tdIndemnityDuty" colspan="3">&nbsp;</td>
          <td align="center" width="10%">&nbsp;责任限额</td>
          <td id="tdSumAmount2" colspan="3">&nbsp;</td>
        </tr>
        -->
			<!-- weiqun temp edit -->
			<!--	
	<tr height="18">
          <td align="left" width="15%">&nbsp;指定驾驶员</td>
          <td id="tdDriverName1" colspan="3">&nbsp;</td>
          <td align="center" width="15%">&nbsp;绝对免赔率</td>
          <td id="" colspan="3">&nbsp;</td>
          
          <td align="center" width="10%">&nbsp;赔案类别</td>
          <td id="tdClaimType" colspan="3">&nbsp;</td>
        </tr>
        -->
			<!--
	       <tr height="18">
	       　<td align="left" width="15%">&nbsp;出险驾驶员</td>
          <td id="tdDriverName" colspan="3">&nbsp;</td>
          <td align="center" width="15%">&nbsp;绝对免赔额</td>
          <td id="" colspan="3">&nbsp;</td>
          <td align="center" width="15%">&nbsp;出险区域类别</td>
          <td id="tdDamageAddressType" colspan="3">&nbsp;</td>          
        </tr>
        -->
			<!--
        
        <tr>
          <td align="left" width="15%">&nbsp;损失程度</td>
          <td id="tdSumClaim" colspan="3">&nbsp;</td>
          <td align="left" width="15%">&nbsp;行驶区域</td>
          <td id="tdRunAreaName" colspan="3">&nbsp;</td>

        </tr>
        
        -->
			<!--
	<tr height="18">
          <td align="left" width="15%">&nbsp事故处理部门</td>
          <td id="tdHandleUnit" colspan="3">&nbsp;</td>
          <td align="center" width="15%">&nbsp;人员伤亡情况</td>
          <td id="tdPersonInjure" colspan="9">&nbsp;</td>
        </tr>
        
        -->
			<!-- weiqun temp edit -->
			<tr>
				<td align="center" colspan="7" height="15">分&nbsp;&nbsp;&nbsp; 险&nbsp;&nbsp;&nbsp; 种&nbsp;&nbsp;&nbsp; 赔&nbsp;&nbsp;&nbsp; 款&nbsp;&nbsp;&nbsp; 计&nbsp;&nbsp;&nbsp; 算&nbsp;&nbsp;&nbsp;
					公&nbsp;&nbsp;&nbsp; 式</td>
			</tr>
			<tr>
				<td id="tdContext" colspan="7" height="180" valign="top">
					<!-- 减少计算公式的行数，为了在一张纸上-->
					<input type=text rows=14 cols=90 class=readonlyWhite readonly style="overflow: hidden; FONT-SIZE: 10pt">
				</td>
			</tr>
			<%
				if (!"".equals(prpLcompensateDto.getExceptions())) {
			%>
			<tr>
				<td colspan="7" align="left" style="font-family: 宋体; font-size: 9pt; color: red;">
					<B>收款人与被保险人不一致时，须经领导（核赔人）审核生效</B>
				</td>
			</tr>
			<%
				if ("B".equals(strOwnership)) {
			%>
			<tr>
				<td align="left" style="font-family: 宋体; font-size: 9pt;">银行帳号</td>
				<td width="20%" colspan=2><%=strAccountCode%></td>
				<td width="15%" align="left" style="font-family: 宋体; font-size: 9pt;">银行总行</td>
				<td width="15%" colspan=1><%=strBankName%></td>
				<td width="17%" align="left" style="font-family: 宋体; font-size: 9pt;">开户银行</td>
				<td width="18%" colspan=1><%=new String(strCustomBankName.getBytes(), "gbk")%></td>
			</tr>
			<tr>
				<td align="left" style="font-family: 宋体; font-size: 9pt;">帳户归属人证件代码</td>
				<td width="20%" colspan=2><%=strCertifiCateCode%></td>
				<td width="15%" align="left" style="font-family: 宋体; font-size: 9pt;">帳户归属人名称</td>
				<td width="15%" colspan=1><%=strOwnerName%></td>
				<td width="17%" align="left" style="font-family: 宋体; font-size: 9pt;">帳户归属人联系电话</td>
				<td width="18%" colspan=1><%=strOwnerPhoneNo%></td>
			</tr>
			<tr>
				<td align="left" style="font-family: 宋体; font-size: 9pt;">帳户类型</td>
				<td width="20%" colspan=2><%=strAccountTypeName%></td>
				<td width="15%" align="left" style="font-family: 宋体; font-size: 9pt;">帳户币别</td>
				<td width="15%" colspan=1><%=strAccountCurrency%></td>
				<td width="17%" align="left" style="font-family: 宋体; font-size: 9pt;"></td>
				<td width="18%" colspan=1></td>
			</tr>
			<%
				} else {
			%>
			<tr>
				<td align="left" style="font-family: 宋体; font-size: 9pt;">支付对象姓名</td>
				<td width="20%" colspan=2><%=strOwnerName%></td>
				<td align="left" style="font-family: 宋体; font-size: 9pt;">证件号码</td>
				<td colspan=3><%=strCertifiCateCode%></td>
			</tr>
			<%
				}
			%>
			<tr>
				<%
					if ("9".equals(strExceptions)) {
				%>
				<td align="left" style="font-family: 宋体; font-size: 9pt;">例外事项原因</td>
				<td width="20%" colspan=2><%=strExceptionsName%></td>
				<td align="left" style="font-family: 宋体; font-size: 9pt;">例外事项原因描述</td>
				<td colspan=3><%=strReason%></td>
				<%
					} else {
				%>
				<td align="left" style="font-family: 宋体; font-size: 9pt;">例外事项原因</td>
				<td width="20%" colspan=6><%=strExceptionsName%></td>
				<%
					}
				%>
			</tr>
			<%
				}
			%>
			<tr>
				<td colspan="2">
					<table width="100%" height="100%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr>
							<td height="18" id="tdSerialNo">
								<span class="STYLE3">&nbsp;已预付次数<span class="STYLE3">：</span>
							</td>
							<td align=right>次&nbsp;</td>
						</tr>
					</table>
				</td>
				<td colspan="3">
					<table width="100%" height="100%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr>
							<td height="18" id="tdSumPrePaid">
								<span class="STYLE3">&nbsp;已预付金额<span class="STYLE3">：</span>
							</td>
							<td align=right>元&nbsp;</td>
						</tr>
					</table>
				</td>
				<td colspan="2">
					<table width="100%" height="100%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr>
							<td height="18" id="tdSumRest">
								&nbsp;损余物资/残值金额<span class="STYLE3">：</span>
							</td>
							<td align=right>元&nbsp;</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<table width="100%" height="100%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr>
							<td height="18" id="tdJianYan">
								<span class="STYLE3">&nbsp;检验费<span class="STYLE3">：</span>
							</td>
							<td align=right>元&nbsp;</td>
						</tr>
					</table>
				</td>
				<td colspan="3">
					<table width="100%" height="100%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr>
							<td height="18" id="tdCheckFee1">
								<span class="STYLE3">&nbsp;代查勘费<span class="STYLE3">：</span>
							</td>
							<td align=right>元&nbsp;</td>
						</tr>
					</table>
				</td>
				<td colspan="2">
					<table width="100%" height="100%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr>
							<td height="18" id="tdLawFee">
								&nbsp;诉讼、仲裁费<span class="STYLE3">：</span>
							</td>
							<td align=right>元&nbsp;</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<table width="100%" height="100%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr>
							<td height="18" id="tdCheckFee">
								<span class="STYLE3">&nbsp;查勘费<span class="STYLE3">：</span>
							</td>
							<td align=right>元&nbsp;</td>
						</tr>
					</table>
				</td>
				<td colspan="3">
					<table width="100%" height="100%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr>
							<td height="18" id="tdAssessFee">
								<span class="STYLE3">&nbsp;公估费<span class="STYLE3">：</span>
							</td>
							<td align=right>元&nbsp;</td>
						</tr>
					</table>
				</td>
				<td colspan="2">
					<table width="100%" height="100%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr>
							<td height="18" id="tdElseFee">
								&nbsp;其它费用<span class="STYLE3">：</span>
							</td>
							<td align=right>元&nbsp;</td>
						</tr>
					</table>
				</td>
			</tr>
			<!--  <td colspan="4">
            <table width="100%" height="100%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family:宋体; font-size:10pt;">
              <tr>
                <td id="tdSerialNo" height="18">&nbsp;已预付次数：</td>
                <td align=right>次&nbsp;</td>
              </tr>
            </table>

          </td>  -->
			<!--
        <tr>
          <td colspan=12>

            <table width="100%" height="100%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family:宋体; font-size:10pt;">
              <tr height="18">
                <td id="tdCSumThisPaid" width="70%">&nbsp;本次实付赔款（人民币大写）：</td>
                <td id="tdSumThisPaid" width="30%">&nbsp;</td>
                <td align=right>元）&nbsp;</td>
              </tr>
            </table>

          </td>
        </tr>
        -->
			<tr>
				<td colspan=7>
					<table width="100%" height="80%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr height="18">
							<td width="70%" id="tdCSumThisPaid">&nbsp;本次实付赔款（人民币大写）：</td>
							<td id="tdSumPaid" width="30%">&nbsp;</td>
							<td align=right>元）&nbsp;</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan=7>
					<table width="100%" height="80%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr height="18">
							<td width="70%" id="tdCAllSumPaid">&nbsp;赔款合计（人民币大写）：</td>
							<td id="tdAllSumPaid" width="30%">&nbsp;</td>
							<td align=right>元）&nbsp;</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<table width="100%" height="50%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr height="40">
							<td width="33%" align="left" valign="top">&nbsp;初级核赔人意见：</td>
						</tr>
						<tr height="18">
							<td width="33%" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						</tr>
					</table>
				</td>
				<td colspan="3">
					<table width="100%" height="50%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr height="40">
							<td width="33%" align="left" valign="top">&nbsp;中级核赔人意见：</td>
						</tr>
						<tr height="18">
							<td width="33%" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						</tr>
					</table>
				</td>
				<td colspan="2">
					<table width="100%" height="50%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr height="40">
							<td width="33%" align="left" valign="top">&nbsp;高级核赔人意见：</td>
						</tr>
						<tr height="18">
							<td width="33%" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="7" height="60" align="left" valign="top">
					<table width="100%" height="50%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr height="30">
							<td width="33%" align="left" valign="top">&nbsp;备注： （注：注明保费收讫金额、日期及收款人）</td>
						</tr>
						<tr height="20">
							<td width="33%" align="left"></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="7" height="60" align="left" valign="top">
					<table width="100%" height="50%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr height="30">
							<td width="33%" align="left" valign="top">
								&nbsp;高階审批意见<span class="STYLE3">：</span>
							</td>
						</tr>
						<tr height="20">
							<td width="33%" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<br>
		<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
			<tr>
				<td align="left" width="50%">
					&nbsp;理算员：<%=strOperatorName%></td>
				<td align="left" width="50%">
					&nbsp;列印日期：<%=new DateTime(dateTime.current(), dateTime.YEAR_TO_DAY)%>&nbsp;&nbsp;<%=new DateTime(dateTime.current(), dateTime.HOUR_TO_SECOND)%>
				</td>
			</tr>
		</table>
	</form>
	<jsp:include page="/common/print/CompensatePrintButton.jsp" />
	<%-- <jsp:include page="/common/print/PrintButton.jsp" />--%>
	<%-- <jsp:include page="/DAA/compensate/DAASpecialPrintButton.jsp" />--%>
</body>
</html>
