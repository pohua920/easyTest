<%--
****************************************************************************
* DESC       ：机动车辆保险报案记录(代抄单)打印页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-11-16
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@page import="java.util.*"%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<link rel="stylesheet" type="text/css" href="/claim/DAA/print/StandardPrint.css">
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<%-- 初始化 --%>
<%@include file="DAARegistNoneFormatPrintIni.jsp"%>
<html>
<body bgcolor="#FFFFFF" onLoad="loadForm();">
	<form name="fm">
		<!-- 标题部分 -->
		<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
			<tr>
				<td colspan="3" height="40" align=center style="font-family: 宋体; font-size: 14pt;">
					<img src="/claim/images/LOGO.jpg" />
				</td>
			</tr>
			<tr>
				<td colspan="2" height="40" style="text-align: left; font-family: 宋体; font-size: 16pt;">
					<p align=center>
						<B>机动车辆保险报案记录（承保理赔信息）<B>
					</p>
				</td>
			<tr>
				<td align="left" id="tdRegistNo" style="font-family: 宋体; font-size: 10pt;">&nbsp;</td>
			</tr>
		</table>
		<!-- 主体部分 -->
		<table border=1 width="100%" align="center" cellspacing="0" cellpadding="2" style="font-size: 10pt; border-collapse: collapse; bordercolor: #111111;">
			<tr>
				<td valign="middle" colspan="4" width="51%" height="21" style="font-family: 宋体; font-size: 10pt;">
					交强险保单号：<%=compelNo%></td>
				<td valign="left" width="49%" id="tdPolicyNo" style="font-family: 宋体; font-size: 10pt;" colspan="4"></td>
			</tr>
			<tr>
				<td valign="middle" colspan="4" height="21" style="font-family: 宋体; font-size: 10pt;">
					交强险承保公司：<%=compelComName%></td>
				<td valign="middle" colspan="4" height="21" id="tdReportDate"></td>
			</tr>
			<tr>
				<td valign="middle" colspan="4" height="21" id="tdBrandName" style="font-family: 宋体; font-size: 10pt;"></td>
				<td valign="middle" colspan="4" height="21" id="tdLicenseNo"></td>
			</tr>
			<tr>
				<td valign="middle" colspan="4" height="21" id="tdReportorName"></td>
				<td valign="middle" colspan="4" height="21" id="tdInsuredName"></td>
			</tr>
			<tr>
				<td valign="middle" colspan="4" height="21">报案人与被保险人的关系：</td>
				<td valign="middle" colspan="4" height="21" id="tdReportType"></td>
			</tr>
			<tr>
				<td valign="middle" colspan="3" height="21" id="tdDriverName"></td>
				<td valign="middle" colspan="1" height="21" id="tdDrivingCarType"></td>
				<td valign="middle" colspan="4" height="21" id="tdDrivingLicenseNo"></td>
			</tr>
			<tr>
				<td valign="middle" colspan="4" height="21" id="tdDamageStartDate"></td>
				<td valign="middle" colspan="4" height="21" id="tdDamageName"></td>
			</tr>
			<tr>
				<td valign="middle" colspan="3" height="21" id="tdDamageAddress"></td>
				<td valign="middle" colspan="5" height="21" id="tdDamageArea"></td>
			</tr>
			<tr>
				<td valign="middle" colspan="8" height="21" id="tdDamageAddressType"></td>
			</tr>
			<tr>
				<td valign="middle" colspan="3" height="21" id="tdFirstSiteFlag"></td>
				<td valign="middle" colspan="5" height="21"><%=personInjure%></td>
			</tr>
			<tr>
				<td valign="middle" colspan="8" height="21" id="tdHandleUnit"></td>
			</tr>
			<tr>
				<td valign="middle" colspan="8" height="21" id="tdContext">
					<br> <br> <br>
				</td>
			</tr>
			<tr>
				<td valign="middle" rowspan="<%=7 + intItemKindCount / 2%>" colspan="1" align="center" height="21" width="3%"><%=policyStr%></td>
				<td valign="middle" colspan="2" height="21">
					厂牌型号：<%=StringConvert.encode(PrpCitemCarDto.getBrandName())%></td>
				<td valign="middle" colspan="2" height="21">
					号牌号码：<%=strLicenseNo%></td>
				<td valign="middle" colspan="3" height="21" id="tdEngineNo"></td>
			</tr>
			<tr>
				<td valign="middle" colspan="2" id="tdPurchasePrice" height="21"></td>
				<td valign="middle" colspan="2" height="21" id="tdFrameNo"></td>
				<td valign="middle" colspan="3" height="21">
					核定载客<u>&nbsp;<%=carHavePerson%>&nbsp;
					</u>人 核定载质量<u>&nbsp;<%=carWeight%>&nbsp;
					</u>吨
				</td>
			</tr>
			<tr>
				<td valign="middle" rowspan="2" colspan="2" id="tdRunAreaName" height="21" width="41%"></td>
				<td valign="middle" colspan="2" id="tdUseNatureCode" height="21" width="36%"></td>
				<td valign="middle" colspan="3" height="21" id="tdInsuredTerm" width="26%"></td>
			</tr>
			<tr>
				<td valign="middle" colspan="1" width="30%" height="21">交费日期：</td>
				<td valign="middle" colspan="2" width="20%" id="tdSumPremium" height="21"></td>
				<td valign="middle" colspan="2" height="21" width="20%" id="tdUseYears"></td>
			</tr>
			<tr>
				<td valign="middle" colspan="1" width="7%" height="21" align=center>&nbsp;序号</td>
				<td valign="middle" colspan="1" width="20%" height="21" align=center>&nbsp;承保险种（代码）</td>
				<td valign="middle" colspan="1" width="18%" height="21" align=center>&nbsp;保险金额/责任金额</td>
				<td valign="middle" colspan="1" width="6%" height="21" align=center>&nbsp;序号</td>
				<td valign="middle" colspan="1" width="10%" height="21" align=center>&nbsp;承保险种（代码）</td>
				<td valign="middle" colspan="2" width="27%" height="21" align=center>&nbsp;保险金额/责任金额</td>
			</tr>
			<%
				if (intItemKindCount % 2 != 0)
					intItemKindCount = intItemKindCount + 1;
				for (int i = 0; i < intItemKindCount; i = i + 2) {
					if (i < intItemKindCount - 2 || intItemKindCount == strKindCode.length) {
						if (strKindCode[i] == null) {
							strKindCode[i] = "";
							strKindName[i] = "";
						}
						if (strKindCode[i + 1] == null) {
							strKindCode[i + 1] = "";
							strKindName[i + 1] = "";
						} else {
							if (strKindCode[i + 1].equals(strKindCode[i])) {
								strKindCode[i + 1] = "";
								strKindName[i + 1] = "";
								douAmount[i + 1] = "";
							}
						}
						if (douAmount[i] == null) {
							douAmount[i] = "";
						}
						if (douAmount[i + 1] == null) {
							douAmount[i + 1] = "";
						}
						if (strDangerLevel[i] == null) {
							strDangerLevel[i] = "";
						}
						if (strDangerLevel[i + 1] == null) {
							strDangerLevel[i + 1] = "";
						}
			%>
			<tr>
				<td valign="middle" colspan="1" height="21" align=center><%=i + 1%></td>
				<td valign="middle" colspan="1" height="21" align=center><%=strKindName[i]%>&nbsp;<%=strKindCode[i]%>&nbsp;&nbsp;<%=strDangerLevel[i]%></td>
				<td valign="middle" colspan="1" height="21" align=center><%=douAmount[i]%></td>
				<td valign="middle" colspan="1" height="21" align=center><%=i + 2%></td>
				<td valign="middle" colspan="1" height="21" align=center><%=strKindName[i + 1]%>&nbsp;<%=strKindCode[i + 1]%>&nbsp;&nbsp;<%=strDangerLevel[i + 1]%></td>
				<td valign="middle" colspan="2" height="21" align=center><%=douAmount[i + 1]%></td>
			</tr>
			<%
				} else {
			%>
			<tr>
				<td valign="middle" colspan="1" height="21" align=center><%=i + 1%></td>
				<td valign="middle" colspan="1" height="21" align=center><%=strKindName[i]%>&nbsp;<%=strKindCode[i]%>&nbsp;&nbsp;<%=strDangerLevel[i]%></td>
				<td valign="middle" colspan="1" height="21" align=center><%=douAmount[i]%></td>
				<td valign="middle" colspan="1" height="21" align=center>&nbsp;</td>
				<td valign="middle" colspan="1" height="21" align=center>&nbsp;&nbsp;&nbsp;</td>
				<td valign="middle" colspan="2" height="21" align=center>&nbsp;</td>
			</tr>
			<%
				}
				}
			%>
			<tr>
				<td valign="middle" colspan="2" height="21">
					业务归属部门：<%=comName%></td>
				<td valign="middle" colspan="1" height="21" id="tdHandlerName">&nbsp;</td>
				<td valign="middle" colspan="2" height="21">
					出单员：<%=handerName1%></td>
				<td valign="middle" colspan="2" height="21" id="tdUnderwriteName">&nbsp;</td>
			</tr>
			<tr>
				<td valign="middle" width="28%" align="center" colspan="2" height="22" align=center>
					<br>特别约定 <br>
				</td>
				<td valign="middle" align="left" colspan="6" width="72%" height="22" id="tdEngage">
					<input type=text rows=5 cols=97 class=readonlyWhite readonly>
				</td>
			</tr>
			<tr>
				<td valign="middle" colspan="2" height="25" align=center>
					<br>保险单批改信息<br>
				</td>
				<td valign="middle" colspan="6" align="left" height="25" id="tdPheadText">
					<input type=text rows=5 cols=97 class=readonlyWhite readonly>
				</td>
			</tr>
			<tr>
				<td valign="middle" height="21" colspan="2" align=center>
					<br>保险车辆出险信息<br>
				</td>
				<td valign="middle" colspan="6" align="left" height="21"><%=registInfo%></td>
			</tr>
			<tr>
				<td valign="middle" height="21" colspan="2" align=center>
					<br>查勘信息回复<br>
				</td>
				<td valign="middle" colspan="6" align="left" height="21"></td>
			</tr>
			<tr>
				<td valign="middle" colspan="3" height="20" id="tdPheadCount"></td>
				<td valign="middle" colspan="1" height="20" id="tdClaimCount"></td>
				<td valign="middle" colspan="2" height="20" id="tdCompensateCount"></td>
				<td valign="middle" colspan="2" height="20" id="tdSumPaid"></td>
			</tr>
			<tr>
				<td valign="middle" colspan="4" height="20" id="tdInsuredAddress"></td>
				<td valign="middle" colspan="4" height="20" id="tdInsuredPostCode"></td>
			</tr>
			<tr>
				<td valign="middle" colspan="3" height="20" id="tdLinkerName1"></td>
				<td valign="middle" colspan="1" height="20" id="tdPhoneNumber1"></td>
				<td valign="middle" colspan="4" height="20" id="tdMobile"></td>
			</tr>
		</table>
		<table width="100%" align="center" cellspacing="0" cellpadding="0" border="0">
			<tr>
				<td align=left width="50%" height="14" id="tdUserName"></td>
				<td colspan="2" align=left width="50%" id="tdInputDate"></td>
			</tr>
		</table>
		<%-- include打印按钮 --%>
		<jsp:include page="/common/print/PrintButton.jsp" />
	</form>
</body>
<!--这个函数是调动所能用到的通用js的过程，一般包括最常用的js的函数声明都在meta_js.jsp中-->
</html>
