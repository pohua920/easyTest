<%--
****************************************************************************
* DESC       ：车险保单列印页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-11-23
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@page import="com.sinosoft.claim.common.ConstantCodes"%>
<%-- 初始化 --%>
<%@include file="PolicyDAAPolicyFormatPrintIni.jsp"%>
<%
	int strLineLimit = 15;
%>
<html>
<head>
<title>车险保单列印</title>
<link rel="stylesheet" type="text/css" href="/ddccallweb/css/Standard.css">
</head>
<body style="font-size: 9pt">
	<form id=fm>
		<!-- 标题部分 -->
		<table width="94%" align="center" cellspacing="0" cellpadding="0" border="0">
			<!--<tr>
        <td height="90px">
        </td>
        </tr>-->
			<td colspan="3" height="40" align=center style="font-family: 宋体; font-size: 14pt;">
				<img src="/claim/images/LOGO.jpg" />
			</td>
			<tr>
				<td align=center height="20" style="text-align: center; font-family: 宋体; font-size: 14pt;">
					<b>机动车辆保险单（抄单）</b>
				</td>
			</tr>
			<!--<tr>
          <td align=center height="30" style="text-align:center; font-family:宋体; font-size:14pt;">  
            <b><font size=2><%=strBigTitle%></font></b>
          </td>  
        </tr>-->
			<tr>
				<td align=right height="20" style="font-family: 宋体; font-size: 10pt;">
					保险单号:
					<%=strPolicyNo%>
				</td>
			</tr>
			<tr>
				<td style="font-family: 宋体; font-size: 11pt;">&nbsp;&nbsp;&nbsp;&nbsp;鉴於要保人已向保险人提出投保申请，並同意按约定交付保险费，保险人依照承保险种及其对应条款和特别约定承担赔偿责任。</td>
			</tr>
		</table>
		<!-- 投保信息 -->
		<table border=1 width="94%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:9pt;">
			<tr>
				<td height=22 colSpan=2>&nbsp;被 保 险 人</td>
				<td height=22 colspan=12 align=left>
					&nbsp;<%=strInsuredName%></td>
			</tr>
			<tr>
				<td width="4%" height=22 rowSpan=6 align="center">保险车辆情况</td>
				<td width="10%" height=22>&nbsp;号牌号码</td>
				<td height=22 colspan=3>
					&nbsp;<%=strLicenseNo%>
				</td>
				<td width="10%" height=22>&nbsp;厂牌型号</td>
				<td height=22 colspan=4>
					&nbsp;<%=strBrandName%></td>
				<td width="10%" height=22 colspan=2>&nbsp;发动机号</td>
				<td height=22 colspan=2>
					&nbsp;<%=strEngineNo%>
				</td>
			</tr>
			<tr>
				<td height=22>&nbsp;VIN 码</td>
				<td height=22 colspan=3>
					&nbsp;<%=strVINNo%></td>
				<td height=22>&nbsp;车 架 号</td>
				<td height=22 colspan=4>
					&nbsp;<%=strFrameNo%></td>
				<td height=22 colspan=2>&nbsp;车辆种类</td>
				<td height=22 colspan=2>
					&nbsp;<%=strCarKind%></td>
			</tr>
			<tr>
				<td height=22 colspan=3>
					&nbsp;核定载客&nbsp;<%=strSeatCount%>&nbsp;人
				</td>
				<td height=22 colspan=3>
					&nbsp;核定载质量&nbsp;<%=strTonCount%>&nbsp;千克
				<td height=22 colspan=4>
					&nbsp;排量/功率&nbsp;<%=strExhaustScale%>&nbsp;L/KW
				</td>
				<td height=22 colspan=3>
					&nbsp;已使用年限&nbsp;<%=strUseYears%>&nbsp;年
				</td>
			</tr>
			<tr>
				<td height=22>初次登记日期</td>
				<td height=22 colspan=3>
					&nbsp;<%=strEnrollDate%></td>
				<td height=22 colspan=4>
					&nbsp;已行驶里程&nbsp;<%=strRunMiles%>&nbsp;公里
				</td>
				<td height=22 colspan=1>&nbsp;使用性质</td>
				<td height=22 colspan=4>
					&nbsp;<%=strUseNature%></td>
			</tr>
			<tr>
				<td height=22>&nbsp;安全配置</td>
				<td height=22 colspan=7>
					&nbsp;<%=strSafeDevice%></td>
				<td height=22 colspan=1>&nbsp;固定停放地点</td>
				<td height=22 colspan=4>
					&nbsp;<%=strParkSite%></td>
			</tr>
			<tr>
				<td height=22>&nbsp;行驶網域</td>
				<td height=22 colspan=7>
					&nbsp;<%=strRunAreaName%></td>
				<td height=22 colspan=1>&nbsp;新车购置价</td>
				<td height=22 colspan=4>
					&nbsp;<%=strPurchasePrice%>元
				</td>
			</tr>
			<tr>
				<td height=22 width="28%" colspan=9 align=center>&nbsp;承保险种</td>
				<td height=22 width="28%" colspan=4 align=center>&nbsp;保险金额/责任限额(元)</td>
				<td height=22 width="12%" align=center>&nbsp;保险费(元)</td>
			</tr>
			<%
				//*****标的子险信息表PrpTitemKind*****
				String strKindCode = ""; //险别代码
				String strKindCodeTmp = ""; //险别代码的第一个字母
				String strKindCode1 = ""; //不带条款类别的险别代码
				String strFront = ""; //对於不同的险别在保险金额前可能有描述文字
				String strMiddle = ""; //对於不同的险别的保险金额
				String strBack = ""; //对於不同的险别在保险金额後可能有描述文字
				boolean isA = false; //是否投了A险  
				//reason:增加自负额
				boolean isQ = false; //是否投了自负额特约险
				double dValue = 0;

				//1.判断是否投了A险
				if (policyDtoOld.getPrpCitemKindDtoList() != null) {
					for (index = 0; index < intItemKindCount; index++) {
						PrpCitemKindDto prpCitemKindDto = (PrpCitemKindDto) policyDtoOld.getPrpCitemKindDtoList().get(index);
						strKindCodeTmp = prpCitemKindDto.getKindCode().substring(0, 1);
						if (strKindCodeTmp.equals(ConstantCodes.KINDCODE_D_A)) {
							isA = true;
							//reason:增加自负额
							dValue = prpCitemKindDto.getValue();
							if (dValue > 0) {
								isQ = true;
							}
							break;
						} else
							isA = false;
					}
				}
				//2.如果投保了A险就不必在B险後拼条款类别
				for (index = 0; index < strLineLimit; index++) {
					//车队保单时在第一行列印提示信息
					if ((index == 0) && (strCarType.equals("MultiCar"))) {
			%>
			<tr>
				<td height=22 colspan=9>车队保单,详情请见保险单附表。</td>
				<td height=22 colspan=4></td>
				<td height=22></td>
			</tr>
			<%
				}
					//项数超过指定列印范围时在第一行列印提示信息
					else if ((index == 0) && (intItemKindCount > strLineLimit)) {
			%>
			<tr>
				<td height=22 colspan=9>承保的险种太多,详情请见附表。</td>
				<td height=22 colspan=4></td>
				<td height=22></td>
			</tr>
			<%
				} else if ((intItemKindCount < strLineLimit) && (!strCarType.equals("MultiCar")) && (index < intItemKindCount)) {

						prpItemKindDto = (PrpCitemKindDto) policyDtoOld.getPrpCitemKindDtoList().get(index);
						//System.out.println("-------1---prpItemKindDto----"+prpItemKindDto.getKindName());          
						strKindCodeTmp = prpItemKindDto.getKindCode().substring(0, 1);
						if (strKindCodeTmp.equals(ConstantCodes.KINDCODE_D_B) && (isA == true))
							strKindCode = ConstantCodes.KINDCODE_D_B;
						else
							strKindCode = StringConvert.encode(prpItemKindDto.getKindCode());

						//对於显示的第一个主险险别，後面的条款类别要显示中文
						if (strKindCodeTmp.equals(ConstantCodes.KINDCODE_D_A) && (isA == true) || strKindCodeTmp.equals(ConstantCodes.KINDCODE_D_B) && (!(isA == true))) {
							if (strKindCode.length() > 1) {
								strCode = StringConvert.encode(strKindCode.substring(1));
								strName = uiCodeAction.translateCodeCode("ClauseType", strCode, isChinese);
								strKindCode = strName;
							}
							strKindCode1 = strKindCodeTmp;
						} else
							strKindCode1 = strKindCode;

						//计算每个险别的优惠金额
						dblTotalProfit = 0;
						if (policyDtoOld.getPrpCprofitDtoList() != null) {
							for (index1 = 0; index1 < intProfitCount; index1++) {
								prpProfitDto = (PrpCprofitDto) policyDtoOld.getPrpCprofitDtoList().get(index1);
								if (prpProfitDto.getKindCode().equals(strKindCode1) && (prpProfitDto.getItemkindNo() == prpItemKindDto.getItemKindNo())) {
									dblTotalProfit += prpProfitDto.getTotalProfit();
									dblSumTotalProfit += prpProfitDto.getTotalProfit();
								}
							}
						}

						//玻璃单独破碎险
						if (strKindCode.equals("F")) {
							if (prpItemKindDto.getModeCode().equals("2")) {
								//strFront = "进口";
								strFront = "";
								strMiddle = new DecimalFormat("#,##0.00").format(prpItemKindDto.getAmount());
								strBack = "";
								strMiddle = "";
							} else if (prpItemKindDto.getModeCode().equals("1")) {
								//strFront = "国产";
								strFront = "";
								strMiddle = new DecimalFormat("#,##0.00").format(prpItemKindDto.getAmount());
								strBack = "";
								strMiddle = "";
							} else {
								//strFront = "未知";
								strFront = "";
								strBack = "";
								strMiddle = new DecimalFormat("#,##0.00").format(prpItemKindDto.getAmount());
							}
							if (prpItemKindDto.getAmount() == 0.00 || prpItemKindDto.getAmount() == 0.0) {
								strMiddle = "0.00";
							}
							////System.out.println("checkpoint"+strMiddle);
						} else if (strKindCode.equals("T")) //车辆停驶损失险
						{
							strFront = "";
							strMiddle = new DecimalFormat("#,##0.00").format(prpItemKindDto.getUnitAmount());
							strBack = "";
							strBack = "/日*" + new DecimalFormat("0").format(prpItemKindDto.getQuantity()) + "日";
						} else if (strKindCode.equals("D3")) {
							strFront = "";
							strMiddle = new DecimalFormat("#,##0.00").format(prpItemKindDto.getUnitAmount());
							strBack = "";
							strBack = "/座*" + new DecimalFormat("0").format(prpItemKindDto.getQuantity()) + "座";
						} else if (strKindCode.equals(ConstantCodes.KINDCODE_D_D1)) {
							strFront = "";
							strMiddle = new DecimalFormat("#,##0.00").format(prpItemKindDto.getUnitAmount());
							strBack = "";
							strBack = "/座*" + new DecimalFormat("0").format(prpItemKindDto.getQuantity()) + "座";
						} else {
							strFront = "";
							strMiddle = new DecimalFormat("#,##0.00").format(prpItemKindDto.getAmount());
							strBack = "";
						}
						/*
						if(strKindCode.equals("M"))
						      {
						        String strTemp = "(";
						        boolean bflag = false;
						        int intCount = 0;

						        if(policyDtoOld.getPrpCitemKindDtoList()!=null){
						          for(int j=0; j<intItemKindCount; j++)
						          {
						            PrpCitemKindDto prpCitemKindDtoTemp = (PrpCitemKindDto)policyDtoOld.getPrpCitemKindDtoList().get(j);
						            if(prpCitemKindDtoTemp.getFlag()!=null && prpCitemKindDtoTemp.getFlag().length()>4 &&prpCitemKindDtoTemp.getFlag().substring(4,5).equals("1"))
						            {
						              intCount ++;
						              if(intCount==3)
						              {
						                strTemp += "等";
						                break;
						              }
						              if(bflag)
						              {
						                strTemp += ","+StringConvert.encode(prpCitemKindDtoTemp.getKindName());
						              }else
						              {
						                strTemp += StringConvert.encode(prpCitemKindDtoTemp.getKindName());
						                bflag = true;
						              }
						            }
						          }      
						        }                  

						        strTemp += ")";
						        prpItemKindDto.setKindName(prpItemKindDto.getKindName()+strTemp);
						      } 
						 */

						/*if(strMiddle.equals("0.00"))
						{
						  strMiddle = ""; 
						}*/
			%>
			<tr>
				<%-- 险别 --%>
				<td height=22 colspan=9>
					<table border=0 width="100%" cellspacing="0" cellpadding="0" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:9pt;">
						<tr>
							<td width="75%">
								&nbsp;<%=StringConvert.encode(prpItemKindDto.getKindName())%>(<%=strKindCode%>)
							</td>
							<td width="25%">
								<%
									if (dblTotalProfit > 0) {
								%>
								优惠<%=new DecimalFormat("#,##0.00").format(dblTotalProfit)%>
								<%
									}
								%>
							</td>
						</tr>
					</table>
				</td>
				<%-- 保额 --%>
				<td height=22 colspan=4>
					<!-- 这三项不能换行，否则会出现空格 -->
					<table width="100%" border="0" style="font-family: 宋体; font-size: 9pt;">
						<tr>
							<td width="60%">
								<div align="right"><%=strFront%><%=strMiddle%><%=strBack%></div>
							</td>
							<td></td>
						</tr>
					</table>
				</td>
				<%-- 保费 --%>
				<td height=22>
					<table width="100%" border="0" style="font-family: 宋体; font-size: 9pt;">
						<tr>
							<td width="80%">
								<div align="right"><%=new DecimalFormat("#,##0.00").format(prpItemKindDto.getPremium())%></div>
							</td>
							<td></td>
						</tr>
					</table>
				</td>
			</tr>
			<%
				} else {
						//reason:增加自负额
						if (isQ) {
			%>
			<td height=22 colspan=9>&nbsp;车辆损失自负额特约条款</td>
			<td height=22 colspan=4>
				&nbsp;绝对自负额<%=new DecimalFormat("#,##0.00").format(dValue)%></td>
			<td height=22></td>
			<%
				isQ = false;
						} else {
			%>
			<tr>
				<td height=22 colspan=9></td>
				<td height=22 colspan=4></td>
				<td height=22></td>
			</tr>
			<%
				}
					}
				}
			%>
			<tr>
				<td height=22 colspan=14>
					<table border=0 width="100%" height="100%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:9pt;">
						<tr>
							<td width="70%">
								&nbsp;保险费合计（人民币大写）：<%=strCSumPremium%></td>
							<td width="20%" align=right>
								(￥：<%=strSumPremium%></td>
							<td width="10%" align=right>元)</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td height=22 colspan=14>
					<table border=0 width="100%" cellspacing="0" cellpadding="0" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:9pt;">
						<tr>
							<td width="75%">
								&nbsp;保险期间&nbsp;自&nbsp;<%=strStartYear%>&nbsp;年&nbsp;<%=strStartMonth%>&nbsp;月&nbsp;<%=strStartDay%>&nbsp;日<%=strStartHour%>时起&nbsp;至 &nbsp;<%=strEndYear%>&nbsp;年&nbsp;<%=strEndMonth%>&nbsp;月&nbsp;<%=strEndDay%>&nbsp;日<%=strEndHour%>时止
							</td>
							<%
								if (!(strCarType.equals("MultiCar"))) {
							%>
							<td width="25%">
								<%
									if (dblSumTotalProfit > 0) {
								%>
								总优惠金额：<%=new DecimalFormat("0.00").format(dblSumTotalProfit)%>
								<%
									}
								%>
							</td>
							<%
								}
							%>
						</tr>
					</table>
				</td>
			</tr>
			<tr class=normal>
				<td align="center">特别约定</td>
				<td colspan=13>
					<textarea name="Engage" rows=5.5 cols=80 class=readonlyWhite readonly style="overflow: hidden; font-size: 9pt; border =0; margin-left: 10"><%=strClauses%></textarea>
				</td>
			</tr>
			<tr>
				<td height=22 colspan=4>&nbsp;保险合約争议解决方式</td>
				<td height=22 colspan=10>
					&nbsp;<%=strArgueSolution%></td>
			</tr>
			<tr>
				<td align="center">重要提示</td>
				<td colspan=13 height=40>
					&nbsp;1.本保险合約由保险条款、投保单、保险单、批单和特别约定组成。 <br>&nbsp;2.收到本保险单、承保险种对应的保险条款後，请立即核对，如有不符或疏漏，请在48小时内通知保险人並办理变更或补充手续；超过48小时未通知的，视为要保人无异议。 <br>&nbsp;3.请详细阅读承保险种对应的保险条款，特别是责任免除和要保人、被保险人义务。 <br>&nbsp;4.保险车辆转卖、转让、赠送他人或变更用途，应书面通知保险人並办理变更手续。
				</td>
			</tr>
			<tr>
				<td align="center" height=22>保险人</td>
				<td height=22 colspan=13>
					<table border=0 width="100%" height="100%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:9pt;">
						<tr>
							<td width="40%">
								&nbsp;公司名称：<%=strComCName%></td>
							<td width="60%" colspan=2>
								公司地址：<%=strAddressCName%></td>
						</tr>
						<tr>
							<td>
								&nbsp;邮政编码:
								<%=strPostCode%></td>
							<td>联系电话:</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>
								签单日期：<%=strOperateDate%></td>
							<td>（保险人签章）</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<!-- 结尾信息 -->
		<table border=0 width="90%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:9pt;">
			<tr>
				<td width="33%">
					核保人：
					<%=strUnderwriteName%></td>
				<td width="33%">
					制单人：
					<%=strOperatorCode%></td>
				<td width="33%">
					经办人：
					<%=strHandlerCode%></td>
			</tr>
		</table>
		<%-- include列印按钮 --%>
		<jsp:include page="/common/print/PrintButton.jsp" />
	</form>
</body>
</html>
