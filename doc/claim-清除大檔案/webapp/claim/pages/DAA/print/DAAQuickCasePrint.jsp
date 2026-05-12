<%@ page language="java" pageEncoding="GBK"%>
<%@include file="DAAQuickCasePrintini.jsp"%>
<%@ page contentType="text/html; charset=gb2312"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<html>
<head>
<title>机动车辆简易赔案处理单</title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<link rel="stylesheet" type="text/css" href="/claim/DAA/print/StandardPrint.css">
</head>
<body>
	<%--
		<jsp:include page="/common/pub/UIErrorPage.jsp">
			<jsp:param name="Picture" value="F" />
			<jsp:param name="Content" value="4" />
		</jsp:include>

		

		<jsp:include page="/common/pub/UIErrorPage.jsp">
			<jsp:param name="Picture" value="F" />
			<jsp:param name="Content" value="4" />
		</jsp:include>
		
		 --%>
	<table style="font-size: 10pt; border-collapse: collapse; bordercolor: #111111;" width="100%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td colspan="3" height="40" align=center style="text-align: center; font-family: 宋体; font-size: 14pt;">
				<B>机动车辆简易赔案处理单<br>
				</B>
			</td>
		</tr>
	</table>
	<br>
	<table style="font-size: 10pt; border-collapse: collapse; bordercolor: #111111;" width="100%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td width="15%" height="40" style="font-family: 宋体; font-size: 10pt;">交强赔案号：</td>
			<td width="20%" style="font-family: 宋体; font-size: 10pt;">
				<%=strCompelclaimNo%>
			</td>
			<td width="15%" style="font-family: 宋体; font-size: 10pt;">交强保单号：</td>
			<td width="20%" style="font-family: 宋体; font-size: 10pt;">
				<%=strCompelPolicyNo%>
			</td>
			<td width="15%" style="font-family: 宋体; font-size: 10pt;">交强承保公司：</td>
			<td width="21%" style="font-family: 宋体; font-size: 10pt;">
				<%=strInsureComName%>
			</td>
		</tr>
		<tr>
			<td style="font-family: 宋体; font-size: 10pt;">商业赔案号：</td>
			<td style="font-family: 宋体; font-size: 10pt;">
				<%=strclaimNo%>
			</td>
			<td style="font-family: 宋体; font-size: 10pt;">商业保单号：</td>
			<td style="font-family: 宋体; font-size: 10pt;">
				<%=strPolicyNo%>
			</td>
			<td style="font-family: 宋体; font-size: 10pt;"></td>
			<td style="font-family: 宋体; font-size: 10pt;"></td>
		</tr>
	</table>
	<table style="font-size: 10pt; border-collapse: collapse; bordercolor: #111111;" width="100%" align="center" cellspacing="0" cellpadding="2" border="1">
		<tr>
			<td width="3%" height="50%" style="font-family: 宋体; font-size: 10pt;" rowspan="12" align=center>
				<b>索赔信息</b>
			</td>
		<tr>
			<td width="11%">被保险人</td>
			<td colspan="3">
				<%=strInsuredName%>
			</td>
			<td width="11%">牌照号码</td>
			<td width="16%">
				<%=strlicenseNo%>
			</td>
			<td width="16%">使用性质</td>
			<td width="13%">
				<%=carKind%>
			</td>
		</tr>
		<tr>
			<td>报 案 人</td>
			<td width="8%">
				<%=strReportorName%>
			</td>
			<td width="8%">联系电话</td>
			<td width="14%">
				<%=strReportPhoneNo%>
			</td>
			<td>驾驶员姓名</td>
			<td>
				<%=strDriverName%>
			</td>
			<td>联系电话</td>
			<td></td>
		</tr>
		<tr>
			<td>出险时间</td>
			<td>
				<%=strDamageDate%>
			</td>
			<td>报案时间</td>
			<td>
				<%=strReportDate%>
			</td>
			<td>出险地点</td>
			<td colspan="3">
				<%=strDamageAddress%>
			</td>
		<tr>
			<td>出险原因</td>
			<td colspan="7">
				<%
					if ("830".equals(strDamageCode)) {
				%>
				■碰撞&nbsp;&nbsp; □倾覆 &nbsp;&nbsp; □火灾 &nbsp;&nbsp;□爆炸 &nbsp;&nbsp; □盗抢&nbsp;&nbsp; □自燃 &nbsp;&nbsp;□暴雨&nbsp;&nbsp; □雹灾 &nbsp;&nbsp; □其它
				<%
					}
				%>
				<%
					if ("831".equals(strDamageCode)) {
				%>
				□碰撞&nbsp;&nbsp; ■倾覆 &nbsp;&nbsp; □火灾 &nbsp;&nbsp;□爆炸 &nbsp;&nbsp; □盗抢&nbsp;&nbsp; □自燃 &nbsp;&nbsp;□暴雨&nbsp;&nbsp; □雹灾 &nbsp;&nbsp; □其它
				<%
					}
				%>
				<%
					if ("832".equals(strDamageCode)) {
				%>
				□碰撞&nbsp;&nbsp; □倾覆 &nbsp;&nbsp; ■火灾 &nbsp;&nbsp;□爆炸 &nbsp;&nbsp; □盗抢&nbsp;&nbsp; □自燃 &nbsp;&nbsp;□暴雨&nbsp;&nbsp; □雹灾 &nbsp;&nbsp; □其它
				<%
					}
				%>
				<%
					if ("833".equals(strDamageCode)) {
				%>
				□碰撞&nbsp;&nbsp; □倾覆 &nbsp;&nbsp; □火灾 &nbsp;&nbsp;■爆炸 &nbsp;&nbsp; □盗抢&nbsp;&nbsp; □自燃 &nbsp;&nbsp;□暴雨&nbsp;&nbsp; □雹灾 &nbsp;&nbsp; □其它
				<%
					}
				%>
				<%
					if ("815".equals(strDamageCode)) {
				%>
				□碰撞&nbsp;&nbsp; □倾覆 &nbsp;&nbsp; □火灾 &nbsp;&nbsp;□爆炸 &nbsp;&nbsp; ■盗抢&nbsp;&nbsp; □自燃 &nbsp;&nbsp;□暴雨&nbsp;&nbsp; □雹灾 &nbsp;&nbsp; □其它
				<%
					}
				%>
				<%
					if ("816".equals(strDamageCode)) {
				%>
				□碰撞&nbsp;&nbsp; □倾覆 &nbsp;&nbsp; □火灾 &nbsp;&nbsp;□爆炸 &nbsp;&nbsp; □盗抢&nbsp;&nbsp; ■自燃 &nbsp;&nbsp;□暴雨&nbsp;&nbsp; □雹灾 &nbsp;&nbsp; □其它
				<%
					}
				%>
				<%
					if ("804".equals(strDamageCode)) {
				%>
				□碰撞&nbsp;&nbsp; □倾覆 &nbsp;&nbsp; □火灾 &nbsp;&nbsp;□爆炸 &nbsp;&nbsp; □盗抢&nbsp;&nbsp; □自燃 &nbsp;&nbsp;■暴雨&nbsp;&nbsp; □雹灾 &nbsp;&nbsp; □其它
				<%
					}
				%>
				<%
					if ("811".equals(strDamageCode)) {
				%>
				□碰撞&nbsp;&nbsp; □倾覆 &nbsp;&nbsp; □火灾 &nbsp;&nbsp;□爆炸 &nbsp;&nbsp; □盗抢&nbsp;&nbsp; □自燃 &nbsp;&nbsp;□暴雨&nbsp;&nbsp; ■雹灾 &nbsp;&nbsp; □其它
				<%
					}
				%>
				<%
					if (!("830".equals(strDamageCode) && !("831".equals(strDamageCode))
							&& !("832".equals(strDamageCode))
							&& !("833".equals(strDamageCode))
							&& !("815".equals(strDamageCode))
							&& !("816".equals(strDamageCode))
							&& !("804".equals(strDamageCode)) && !("811"
								.equals(strDamageCode)))) {
				%>
				□碰撞&nbsp;&nbsp; □倾覆 &nbsp;&nbsp; □火灾 &nbsp;&nbsp;□爆炸 &nbsp;&nbsp; □盗抢&nbsp;&nbsp; □自燃 &nbsp;&nbsp;□暴雨&nbsp;&nbsp; □雹灾 &nbsp;&nbsp; ■其它
				<%
					}
				%>
			</td>
		</tr>
		<tr>
			<td>开户银行</td>
			<td colspan="3"></td>
			<td>帐 号</td>
			<td colspan="3"></td>
		</tr>
		<tr>
			<td>开 户 名</td>
			<td colspan="8"></td>
		</tr>
		<tr height="120">
			<td>出险原因及经过：</td>
			<td colspan="8">
				&nbsp;&nbsp;&nbsp;<%=strDamageText%><br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br> &nbsp;&nbsp;兹声明本人所填上述资料均为真实情形，没有任何虚假和隐瞒，否则，自愿放弃保险单之一切权利，並承担相应 的法律责任。
			</td>
		</tr>
		<tr>
			<td colspan="2">被保险人（报案人）签字：</td>
			<td colspan="2"></td>
			<td>联系电话：</td>
			<td></td>
			<td>日期：</td>
			<td></td>
		</tr>
		</tr>
		<tr>
	</table>
	<table style="font-size: 10pt; border-collapse: collapse; bordercolor: #111111;" width="100%" align="center" cellspacing="0" cellpadding="2" border="1">
		<td width="3%" height="50%" style="font-family: 宋体; font-size: 10pt;" rowspan="22" align=center>
			<b>查勘意见</b>
		</td>
		<tr>
			<td width="11%">驾驶员姓名</td>
			<td width="11%">
				<%=strDriverNameCheck%>
			</td>
			<td width="17%">是否指定定驾驶员</td>
			<td width="13%">□是 □否</td>
			<td width="18%">驾驶证是否有效</td>
			<%
				String check06 = "";
				check06 = (String) request.getAttribute("check06");
				if ("".equals(check06) || check06 == null) {
			%><td width="18%">□是 □否</td>
			<%
				} else if ("1".equals(check06)) {
			%>
			<td width="18%">■是 □否</td>
			<%
				} else {
			%>
			<td width="18%">□是 ■否</td>
			<%
				}
			%>
		</tr>
		<tr>
			<td>厂牌型号</td>
			<td>
				<%=strBrandName%>
			</td>
			<td>牌照号码</td>
			<td>
				<%=strLicenseNoCheck%>
			</td>
			<td>行驶证是否有效</td>
			<td width="13%">□是 □否</td>
		</tr>
		<tr>
			<td>车架号</td>
			<td>
				<%=strFrameNo%>
			</td>
			<td>VIN号</td>
			<td>
				<%=strVINNoCheck%>
			</td>
			<td>发动机号码</td>
			<td>
				<%=strEnginNoCheck%>
			</td>
		</tr>
		<tr>
			<td>出险原因</td>
			<td colspan="8">
				<%
					if ("830".equals(strDamageCodeCheck)) {
				%>
				■碰撞&nbsp;&nbsp; □倾覆 &nbsp;&nbsp; □火灾 &nbsp;&nbsp;□爆炸 &nbsp;&nbsp; □盗抢&nbsp;&nbsp; □自燃 &nbsp;&nbsp;□暴雨&nbsp;&nbsp; □雹灾 &nbsp;&nbsp; □其它
				<%
					}
				%>
				<%
					if ("831".equals(strDamageCodeCheck)) {
				%>
				□碰撞&nbsp;&nbsp; ■倾覆 &nbsp;&nbsp; □火灾 &nbsp;&nbsp;□爆炸 &nbsp;&nbsp; □盗抢&nbsp;&nbsp; □自燃 &nbsp;&nbsp;□暴雨&nbsp;&nbsp; □雹灾 &nbsp;&nbsp; □其它
				<%
					}
				%>
				<%
					if ("832".equals(strDamageCodeCheck)) {
				%>
				□碰撞&nbsp;&nbsp; □倾覆 &nbsp;&nbsp; ■火灾 &nbsp;&nbsp;□爆炸 &nbsp;&nbsp; □盗抢&nbsp;&nbsp; □自燃 &nbsp;&nbsp;□暴雨&nbsp;&nbsp; □雹灾 &nbsp;&nbsp; □其它
				<%
					}
				%>
				<%
					if ("833".equals(strDamageCodeCheck)) {
				%>
				□碰撞&nbsp;&nbsp; □倾覆 &nbsp;&nbsp; □火灾 &nbsp;&nbsp;■爆炸 &nbsp;&nbsp; □盗抢&nbsp;&nbsp; □自燃 &nbsp;&nbsp;□暴雨&nbsp;&nbsp; □雹灾 &nbsp;&nbsp; □其它
				<%
					}
				%>
				<%
					if ("815".equals(strDamageCodeCheck)) {
				%>
				□碰撞&nbsp;&nbsp; □倾覆 &nbsp;&nbsp; □火灾 &nbsp;&nbsp;□爆炸 &nbsp;&nbsp; ■盗抢&nbsp;&nbsp; □自燃 &nbsp;&nbsp;□暴雨&nbsp;&nbsp; □雹灾 &nbsp;&nbsp; □其它
				<%
					}
				%>
				<%
					if ("816".equals(strDamageCodeCheck)) {
				%>
				□碰撞&nbsp;&nbsp; □倾覆 &nbsp;&nbsp; □火灾 &nbsp;&nbsp;□爆炸 &nbsp;&nbsp; □盗抢&nbsp;&nbsp; ■自燃 &nbsp;&nbsp;□暴雨&nbsp;&nbsp; □雹灾 &nbsp;&nbsp; □其它
				<%
					}
				%>
				<%
					if ("804".equals(strDamageCodeCheck)) {
				%>
				□碰撞&nbsp;&nbsp; □倾覆 &nbsp;&nbsp; □火灾 &nbsp;&nbsp;□爆炸 &nbsp;&nbsp; □盗抢&nbsp;&nbsp; □自燃 &nbsp;&nbsp;■暴雨&nbsp;&nbsp; □雹灾 &nbsp;&nbsp; □其它
				<%
					}
				%>
				<%
					if ("811".equals(strDamageCodeCheck)) {
				%>
				□碰撞&nbsp;&nbsp; □倾覆 &nbsp;&nbsp; □火灾 &nbsp;&nbsp;□爆炸 &nbsp;&nbsp; □盗抢&nbsp;&nbsp; □自燃 &nbsp;&nbsp;□暴雨&nbsp;&nbsp; ■雹灾 &nbsp;&nbsp; □其它
				<%
					}
				%>
				<%
					if (!("830".equals(strDamageCodeCheck)
							&& !("831".equals(strDamageCodeCheck))
							&& !("832".equals(strDamageCodeCheck))
							&& !("833".equals(strDamageCodeCheck))
							&& !("815".equals(strDamageCodeCheck))
							&& !("816".equals(strDamageCodeCheck))
							&& !("804".equals(strDamageCodeCheck)) && !("811"
								.equals(strDamageCodeCheck)))) {
				%>
				□碰撞&nbsp;&nbsp; □倾覆 &nbsp;&nbsp; □火灾 &nbsp;&nbsp;□爆炸 &nbsp;&nbsp; □盗抢&nbsp;&nbsp; □自燃 &nbsp;&nbsp;□暴雨&nbsp;&nbsp; □雹灾 &nbsp;&nbsp; ■其它
				<%
					}
				%>
			
		</tr>
		<tr>
			<td>查勘地点</td>
			<td colspan="8">
				<%=CheckSite%>
				<%--□第一现场 &nbsp;□保险公司 &nbsp; □交警扣车场&nbsp; □特约服务站  &nbsp;□非特约修理厂&nbsp; □其它--%>
		</tr>
		<tr>
			<td>委托状态</td>
			<td colspan="8">□查勘 &nbsp;&nbsp; □核损 &nbsp;&nbsp; □立案 &nbsp;&nbsp; □缮制 &nbsp;&nbsp; □核赔 &nbsp;&nbsp; □结案 &nbsp;&nbsp; □支付</td>
		</tr>
		<tr>
			<td>查勘意见：</td>
			<td colspan="8">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br> 查勘员（签字）：
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 查勘时间：
			</td>
		</tr>
	</table>
	<table style="font-size: 10pt; border-collapse: collapse; bordercolor: #111111;" width="100%" align="center" cellspacing="0" cellpadding="2" border="1">
		<td width="3%" height="50%" style="font-family: 宋体; font-size: 10pt;" rowspan="22" align=center>
			<b>损失情况</b>
		</td>
		<tr>
			<td align=center colspan="4">项 目</td>
			<td align=center colspan="4">金 额</td>
		</tr>
		<%
			String lossname1 = "";
			double sumRealValue1 = 0d;
			String lossname2 = "";
			double sumRealValue2 = 0d;
			PrpLcompensateDto compensateDto = null;
			PrpLcompensateDto compelcompensateDto = null;

			ArrayList losslist1 = null;
			ArrayList losslist2 = null;
			losslist1 = (ArrayList) request.getAttribute("losslist1");
			losslist2 = (ArrayList) request.getAttribute("losslist2");
			PrpLlossDto prpLlossDto1 = null;
			PrpLlossDto prpLlossDto2 = null;
			if (losslist1 != null) {
				for (int i = 0; i < losslist1.size(); i++) {
					prpLlossDto1 = (PrpLlossDto) losslist1.get(i);
					lossname1 = prpLlossDto1.getLossName();
					//reason：SumLoss才是损失情况
					sumRealValue1 = prpLlossDto1.getSumLoss();
					String LicenseNo1 = prpLlossDto1.getLicenseNo();
		%>
		<tr>
			<td align=center colspan="4"><%=lossname1%>(<%=LicenseNo1%>)
			</td>
			<td align=center colspan="4"><%=sumRealValue1%></td>
		</tr>
		<%
			}

			}
			if (losslist2 != null) {
				for (int i = 0; i < losslist2.size(); i++) {
					prpLlossDto2 = (PrpLlossDto) losslist2.get(i);
					lossname2 = prpLlossDto2.getLossName();
					sumRealValue2 = prpLlossDto2.getSumRealPay();
					String LicenseNo2 = prpLlossDto2.getLicenseNo();
		%>
		<tr>
			<td align=center colspan="4"><%=lossname2%>(<%=LicenseNo2%>)
			</td>
			<td align=center colspan="4"><%=sumRealValue2%></td>
		</tr>
		<%
			}
			}
		%>
	</table>
	<table style="font-size: 10pt; border-collapse: collapse; bordercolor: #111111;" width="100%" align="center" cellspacing="0" cellpadding="2" border="1">
		<td width="3%" height="50%" style="font-family: 宋体; font-size: 10pt;" rowspan="22" align=center>
			<b>赔款计算</b>
		</td>
		<tr>
			<td width="10%">事故责任</td>
			<td width="9%">
				<%=strDutydutyName%>
			</td>
			<td width="9%">赔偿比例</td>
			<td width="8%">
				<%=strClaimRate%>%
			</td>
			<td width="11%">责任免赔率</td>
			<td width="8%">
				<%=strLossRate%>%
			</td>
			<td width="12%">绝对免赔率</td>
			<td width="9%">
				<%=strDeduLossRate%>%
			</td>
			<td width="11%">绝对免赔额</td>
			<td width="10%">
				<%=strDeduLossFee%>
			</td>
		</tr>
		<tr>
			<td>交强险赔款计算:</td>
			<td colspan="9">
				<bean:write name="prpLctextDto2" property="context" />
			</td>
		</tr>
		<tr>
			<td>商业险赔款计算：</td>
			<td colspan="9">
				<bean:write name="prpLctextDto1" property="context" />
			</td>
		</tr>
	</table>
	<table style="font-size: 10pt; border-collapse: collapse; bordercolor: #111111;" width="100%" align="center" cellspacing="0" cellpadding="2" border="1">
		<td width="3%" height="50%" style="font-family: 宋体; font-size: 10pt;" rowspan="22" align=center>
			<b>审批拦</b>
		</td>
		<tr>
			<td colspan="2">省分公司车险部签字：</td>
			<td colspan="2">市分公司车险部签字：</td>
			<td colspan="2">查勘定损人员签字：</td>
		</tr>
		<tr>
			<td colspan="2">&nbsp;</td>
			<td colspan="2">&nbsp;</td>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td align=right colspan="2">年 &nbsp;&nbsp; 月 &nbsp;&nbsp; 日</td>
			<td align=right colspan="2">年 &nbsp;&nbsp; 月&nbsp;&nbsp; 日</td>
			<td align=right colspan="2">年 &nbsp;&nbsp; 月 &nbsp;&nbsp; 日</td>
		</tr>
		</tr>
	</table>
	<table style="font-size: 10pt; border-collapse: collapse; bordercolor: #111111;" width="100%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td style="font-family: 宋体; font-size: 10pt;"></td>
			<td style="font-family: 宋体; font-size: 10pt;"></td>
			<td style="font-family: 宋体; font-size: 10pt;">赔案缮制:</td>
			<td style="font-family: 宋体; font-size: 10pt;">年 &nbsp;&nbsp; 月 &nbsp;&nbsp; 日</td>
		</tr>
		<tr>
			<td style="font-family: 宋体; font-size: 10pt;"></td>
			<td style="font-family: 宋体; font-size: 10pt;"></td>
			<td style="font-family: 宋体; font-size: 10pt;">&nbsp;</td>
			<td style="font-family: 宋体; font-size: 10pt;">&nbsp;</td>
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
