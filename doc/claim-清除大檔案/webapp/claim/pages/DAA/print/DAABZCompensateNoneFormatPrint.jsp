<%--
****************************************************************************
* DESC       ：机动车辆强制保险赔款计算书打印页面
* AUTHOR     ：理赔组
* CREATEDATE ：22004-11-16
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%-- 初始化 --%>
<%@include file="DAABZCompensateNoneFormatPrintIni.jsp"%>
<html>
<head>
<title><s:text name="title.printBeforeEdit.compulsoryVehiclePrint" /></title>
<%-- 机动车交通事故强制保险赔款计算书打印 --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
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
		<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
			<!--<tr>
          <td colspan="3" height="40" align=center style="font-family:宋体; font-size:14pt;">
            <img src="/claim/images/LOGO.jpg"/>
          </td>
        </tr> -->
			<tr>
				<td colspan="3" height="30" align=center style="font-family: 宋体; font-size: 16pt;">
					<br> <br>
					<p align=center>
						<B><s:text name="print.compulsoryVehicle" /><B>
					</p>
					<br>
					<%-- 机动车交通事故责任交强险赔款计算书 --%>
					<!--reasion:加入三个回车後，不能打印在一张纸上<br><br><br>-->
				</td>
			</tr>
			<!---<tr>
          <td  width="60%" align=left id="tdPolicyNo" style="font-family:宋体; font-size:9pt;">
            保险单号：
          </td>
        </tr>--->
			<tr>
				<td width="50%" align=left style="font-family: 宋体; font-size: 9pt;">
					<s:text name="print.insurerSignat" />
					<%-- 承保公司（签章） --%>
					：
				</td>
				<td width="50%" align=left style="font-family: 宋体; font-size: 9pt;">
					<s:text name="print.insIndemnityNo" />
					：<%=strCompensateNo%><%-- 交强险赔款计算书号 --%>
				</td>
			</tr>
		</table>
		<!-- 主体部分 -->
		<table width="100%" align="center" cellspacing="0" cellpadding="3" border="1" style="font-size: 10pt; border-collapse: collapse; bordercolor: #111111;">
			<!---	<tr>
       		   <td height="18" align="left" width="40%">保险单号码</td>
       		   <td id="tdPolicyNo" colspan="7"></td>
       		   <td width="40%" align="center">强制保单号</td>
               <td id="rdRpolicyNo"></td>
       	</tr>
       	<tr>
       		<td height="18" align="left" width="40%">报案编号</td>
       		   <td id="tdRegistNo" colspan="7"></td>
       		   <td width="40%" align="center">赔款计算书号</td>
               <td id="tdCompensateNo"></td>
       	</tr>--->
			<tr>
				<td width="12%" colspan=1 align=center style="font-family: 宋体; font-size: 9pt;">
					<s:text name="db.prpLregist.insuredName" />
				</td>
				<%-- 被保险人 --%>
				<td width="45%" id="tdInsuredName" colspan=4><%=prpLregistDto.getInsuredName()%></td>
				<td width="15%" align=center style="font-family: 宋体; font-size: 9pt;">
					<s:text name="print.insPolicyNo" />
				</td>
				<%-- 交强险保单号 --%>
				<td width="30%" id="tdRPolicyNo" colspan=1></td>
			</tr>
			<tr>
				<td width="12%" colspan=1 align=center style="font-family: 宋体; font-size: 9pt;">
					<s:text name="certainLoss.thirdCarLoss.prpLthirdPartyBrandName" />
				</td>
				<%-- 厂牌型号 --%>
				<td width="21%" id="tdBrandName" colspan="2"></td>
				<td width="10%" colspan=1 align=center style="font-family: 宋体; font-size: 9pt;">
					<s:text name="db.prpLlawsuit.licenseNo" />
				</td>
				<%-- 号牌号码 --%>
				<td width="12%" id="tdLicenseNo" colspan=1><%=prpLregistDto.getLicenseNo()%></td>
				<td align=center style="font-family: 宋体; font-size: 9pt;">
					<s:text name="print.insuranceBatchNo" />
				</td>
				<%-- 交强险批单号 --%>
				<td colspan=1><%=strEndorseNo%></td>
			</tr>
			<tr>
				<td width="12%" colspan=1 align=center style="font-family: 宋体; font-size: 9pt;">
					<s:text name="prpLcheck.damageCode" />
				</td>
				<%-- 出险原因 --%>
				<td width="20%" colspan="2"><%=prpLregistDto.getDamageName()%></td>
				<td width="12%" colspan=1 align=center style="font-family: 宋体; font-size: 9pt;">
					<s:text name="certainLoss.thirdCarLoss.indemnityDuty" />
				</td>
				<%-- 事故责任 --%>
				<td width="13%" colspan=1><%=strName%></td>
				<td align=center style="font-family: 宋体; font-size: 9pt;">
					<s:text name="certainLoss.thirdCarLoss.prpLthirdPartyDutyPercent" />
				</td>
				<%-- 责任比例 --%>
				<td id="tdIndemnityDutyRate" colspan=1><%=new DecimalFormat("#,##0.00").format(prpLcompensateDto.getIndemnityDutyRate())%>%
				</td>
			</tr>
			<tr>
				<td width="10%" colspan=1 align=center style="font-family: 宋体; font-size: 9pt;">
					<s:text name="prpLregist.damageDate" />
				</td>
				<%-- 出险日期 --%>
				<td id="tdDamageStartDate" width="23%" colspan="2"><%=strDamageStartDate%></td>
				<td width="10%" colspan=1 align=center style="font-family: 宋体; font-size: 9pt;">
					<s:text name="print.drivingArea" />
				</td>
				<%-- 行驶区域 --%>
				<td width="12%" colspan=1><%=prpLregistDto.getDamageAreaName()%></td>
				<td align=center style="font-family: 宋体; font-size: 9pt;">
					<s:text name="prpLcheck.handlerUnit" />
				</td>
				<%-- 事故处理部门 --%>
				<td colspan=1><%=strHandleUnit%></td>
			</tr>
			<tr>
				<td colspan=1 align=center style="font-family: 宋体; font-size: 9pt;">
					<s:text name="print.specifyDriver" />
				</td>
				<%-- 指定驾驶员 --%>
				<td colspan="2">
					<%
						if (intCriverCount > 0) {
					%>
					<%=StringConvert.encode(prpCcarDriverDto.getDriverName())%>
					<%
						}
					%>
				</td>
				<td colspan=1 align=center style="font-family: 宋体; font-size: 9pt;">
					<s:text name="certainLoss.prpLcheck.prpLcheckDamageAddress" />
				</td>
				<%-- 出险地点 --%>
				<td id="tdDamageAddress" colspan=3><%=prpLregistDto.getDamageAddress()%></td>
			</tr>
			<tr>
				<td colspan=1 align=center style="font-family: 宋体; font-size: 9pt;">
					<s:text name="print.compensDriver" />
				</td>
				<%-- 出险驾驶员 --%>
				<td colspan="2">
					<%
						if (intDriverCount > 0) {
					%>
					<%=StringConvert.encode(prpLdriverDto.getDriverName())%>
					<%
						}
					%>
				</td>
				<td colspan=1 align=center style="font-family: 宋体; font-size: 9pt;">
					<s:text name="print.dangerAreaType" />
				</td>
				<%-- 出险区域类别 --%>
				<td colspan=1><%=prpLregistDto.getDamageAddressType()%></td>
				<td colspan=1 align=center style="font-family: 宋体; font-size: 9pt;">
					<s:text name="print.dangerAreaType" />
				</td>
				<%-- 出险区域类别 --%>
				<td colspan=1><%=strDamageAddressType%></td>
			</tr>
			<tr>
				<td colspan=1 align=center style="font-family: 宋体; font-size: 9pt;">
					<s:text name="print.casualty" />
				</td>
				<%-- 人员伤亡情况 --%>
				<td colspan="2"><%=personInjure%></td>
				<td colspan=1 align=center style="font-family: 宋体; font-size: 9pt;">
					<s:text name="db.prpCmain_invest.investYear" />
				</td>
				<%-- 保险期限 --%>
				<td id="tdInsuredDate" colspan=3><%=strInsuredDate%></td>
			</tr>
			<%--
      	
        <tr>
     
          <td width="40%" align="center">条款类别</td>
          <td id="tdCarClause"></td>
        </tr>

          <tr height="18">
          
          <td align="center" width="40%">事故类别</td>
          <td id="tdIndemnityType"></td>
        </tr>
      
     <tr height="18">
          <td align="center" width="45%">新车购置价</td>
          <td id="tdPurchasePrice"></td>
          <td align="center" width="40%">免赔比例</td>
          <td id="tdLossRate" colspan="3"></td>
        </tr>
    
    
   --%>
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
				<td align="center" colspan="7" height="19">
					<s:text name="print.formulaReparat" />
				</td>
				<%-- 赔 款 计 算 公 式 --%>
			</tr>
			<tr>
				<td id="tdContextLaw" colspan="7" height="55" valign="top">
					<input type=text class=readonlyWhite style="overflow: hidden" readonly rows=18 cols=90>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<table width="100%" height="100%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr>
							<td height="18">
								&nbsp;
								<s:text name="print.prepaidNo" />
								：<%=intCompensatePreCount%></td>
							<%-- 已预付次数 --%>
							<td align=right>
								<s:text name="print.time" />
							</td>
							<%-- 次 --%>
						</tr>
					</table>
				</td>
				<td colspan="3">
					<table width="100%" height="100%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr>
							<td height="18">
								&nbsp;
								<s:text name="print.timePrepaidAmount" />
								<%-- 已预付金额 --%>
								：<%=new DecimalFormat("#,##0.00").format(dblSumPrePaid)%></td>
							<td align=right>
								<s:text name="print.yuan" />
							</td>
							<%-- 元 --%>
						</tr>
					</table>
				</td>
				<td colspan="2">
					<table width="100%" height="100%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr>
							<td height="18">
								&nbsp;
								<s:text name="print.goodsSalvage" />
								<%-- 损余物资/残值金额 --%>
								：<%=new DecimalFormat("#,##0.00").format(dblSumRest)%></td>
							<td align=right>
								<s:text name="print.yuan" />
							</td>
							<%-- 元 --%>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<!--  <td colspan="4">
            <table width="100%" height="100%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family:宋体; font-size:10pt;">
              <tr>
                <td id="tdSerialNo" height="18">&nbsp;已预付次数：</td>
                <td align=right>次&nbsp;</td>
              </tr>
            </table>

          </td>  -->
				<td colspan="2">
					<table width="100%" height="100%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr>
							<td height="18">
								&nbsp;
								<s:text name="print.inspectFee" />
								：<%=new DecimalFormat("#,##0.00").format(dblJianYan)%></td>
							<%-- 检验费 --%>
							<td align=right>
								<s:text name="print.yuan" />
							</td>
							<%-- 元 --%>
						</tr>
					</table>
				</td>
				<td colspan="3">
					<table width="100%" height="100%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr>
							<td height="18">
								&nbsp;
								<s:text name="print.generatSurveyFee" />
								：<%=new DecimalFormat("#,##0.00").format(dblCheckFee1)%></td>
							<%-- 代查勘费 --%>
							<td align=right>
								<s:text name="print.yuan" />
							</td>
							<%-- 元 --%>
						</tr>
					</table>
				</td>
				<td colspan="2">
					<table width="100%" height="100%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr>
							<td height="18">
								&nbsp;
								<s:text name="print.litigatArbitrat" />
								：<%=new DecimalFormat("#,##0.00").format(dblLawFee)%></td>
							<%-- 诉讼、仲裁费 --%>
							<td align=right>
								<s:text name="print.yuan" />
							</td>
							<%-- 元 --%>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<table width="100%" height="100%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr>
							<td height="18">
								&nbsp;
								<s:text name="print.surveyFee" />
								：<%=new DecimalFormat("#,##0.00").format(dblCheckFee)%></td>
							<%-- 查勘费 --%>
							<td align=right>
								<s:text name="print.yuan" />
							</td>
							<%-- 元 --%>
						</tr>
					</table>
				</td>
				<td colspan="3">
					<table width="100%" height="100%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr>
							<td height="18">
								&nbsp;
								<s:text name="print.assessFee" />
								：<%=new DecimalFormat("#,##0.00").format(dblAssessFee)%></td>
							<%-- 公估费 --%>
							<td align=right>
								<s:text name="print.yuan" />
							</td>
							<%-- 元 --%>
						</tr>
					</table>
				</td>
				<td colspan="2">
					<table width="100%" height="100%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr>
							<td height="18">
								&nbsp;
								<s:text name="db.prpLlawsuit.otherFee" />
								：<%=new DecimalFormat("#,##0.00").format(dblElseFee)%></td>
							<%-- 其他费用 --%>
							<td align=right>
								<s:text name="print.yuan" />
							</td>
							<%-- 元 --%>
						</tr>
					</table>
				</td>
			</tr>
			<%--
        
        
        <tr>
          <td colspan=7>
            <table width="100%" height="100%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family:宋体; font-size:10pt;">
              <tr height="18">
                <td id="tdCQiangFee" width="70%">支付抢救费用（人民币大写）：</td>
                <td id="tdQiangFee"" width="30%"></td>
                <td align=right>元)</td>
              </tr>
            </table>          </td>
        </tr>
 
         <tr>
          <td colspan=7>
            <table width="100%" height="100%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family:宋体; font-size:10pt;">
              <tr height="18">
                <td id="tdCQiangRePay" width="70%">垫付抢救费用（人民币大写）：</td>
                <td id="tdQiangRePay" width="30%"></td>
                <td align=right>元)</td>
              </tr>
            </table>          </td>
        </tr>    
        
         --%>
			<tr>
				<td colspan=7>
					<table width="100%" height="100%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr height="18">
							<td id="tdCSumQiangFee" width="70%">
								<s:text name="print.realPayReparat" />
								：
							</td>
							<%-- 本次实付赔款（人民币大写） --%>
							<td id="tdSumQiangFee" width="30%"></td>
							<td align=right>
								<s:text name="print.yuan" />
							</td>
							<%-- 元 --%>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan=7>
					<table width="100%" height="100%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr height="18">
							<td id="tdCAllSumPaid" width="70%">
								&nbsp;
								<s:text name="print.reparatAmountRMB" />
								：
							</td>
							<%-- 赔款合计（人民币大写） --%>
							<td id="tdAllSumPaid" width="30%">&nbsp;</td>
							<td align=right>
								<s:text name="print.yuan" />
							</td>
							<%-- 元 --%>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<table width="100%" height="50%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr height="40">
							<td width="33%" align="left" valign="top">
								&nbsp;
								<s:text name="print.primarOpinion" />
								：
							</td>
							<%-- 初级核赔人意见 --%>
						</tr>
						<tr height="18">
							<td width="33%" align="right">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<s:text name="print.year" />
								<%-- 年 --%>
								&nbsp;&nbsp;&nbsp;
								<s:text name="print.month" />
								<%-- 月 --%>
								&nbsp;&nbsp;&nbsp;
								<s:text name="regist.prpLregist.date" />
								<%-- 日 --%>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
					</table>
				</td>
				<td colspan="3">
					<table width="100%" height="50%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr height="40">
							<td width="33%" align="left" valign="top">
								&nbsp;
								<s:text name="print.intermeOpinion" />
								：
							</td>
							<%-- 中级核赔人意见 --%>
						</tr>
						<tr height="18">
							<td width="33%" align="right">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<s:text name="print.year" />
								<%-- 年 --%>
								&nbsp;&nbsp;&nbsp;
								<s:text name="print.month" />
								<%-- 月 --%>
								&nbsp;&nbsp;&nbsp;
								<s:text name="regist.prpLregist.date" />
								<%-- 日 --%>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
					</table>
				</td>
				<td colspan="2">
					<table width="100%" height="50%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr height="40">
							<td width="33%" align="left" valign="top">
								&nbsp;
								<s:text name="print.seniorOpinion" />
								：
							</td>
							<%-- 高级核赔人意见 --%>
						</tr>
						<tr height="18">
							<td width="33%" align="right">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<s:text name="print.year" />
								<%-- 年 --%>
								&nbsp;&nbsp;&nbsp;
								<s:text name="print.month" />
								<%-- 月 --%>
								&nbsp;&nbsp;&nbsp;
								<s:text name="regist.prpLregist.date" />
								<%-- 日 --%>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="7" height="80" align="left" valign="top">
					<table width="100%" height="50%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr height="30">
							<td width="33%" height="60" align="left" valign="top">
								&nbsp;
								<s:text name="print.superOpinion" />
								：
							</td>
							<%-- 上级审批意见 --%>
						</tr>
						<tr height="20">
							<td width="33%" height="20" valign="bottom" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<br>
		<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
			<tr>
				<td align="left" width="50%">
					&nbsp;
					<s:text name="print.adjuster" />
					：<%=strOperatorName%></td>
				<%-- 理算员 --%>
				<td align="left" width="50%">
					&nbsp;
					<s:text name="print.printDate" />
					：<%=new DateTime(dateTime.current(), dateTime.YEAR_TO_DAY)%></td>
				<%-- 打印日期 --%>
				</td>
		</table>
	</form>
	<jsp:include page="/common/print/CompensatePrintButton.jsp" />
</body>
</html>
