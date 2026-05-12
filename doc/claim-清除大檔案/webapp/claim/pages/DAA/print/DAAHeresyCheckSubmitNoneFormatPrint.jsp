<%--
****************************************************************************
* DESC       ：机动车辆保险代查勘委托书列印页面
* AUTHOR     ：zhulianyu
* CREATEDATE ：2005-11-15
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%-- 初始化 --%>
<%@include file="DAAHeresyCheckSubmitNoneFormatPrintIni.jsp"%>
<html>
<head>
<title>机动车辆保险代查勘委托书列印</title>
<link rel="stylesheet" type="text/css" href="Standard.css">
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body bgcolor="#FFFFFF" onload="loadForm();">
	<!-- 标题部分 -->
	<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td colspan="2" height="40" align="center" style="text-align: center; font-family: 宋体; font-size: 14pt;">
				<Img src="/claim/images/LOGO.jpg" />
			</td>
		</tr>
		<tr>
			<td colspan="2" height="40" align=center style="text-align: center; font-family: 宋体; font-size: 14pt;">
				<B>机动车辆保险代理查勘委托书<B>
			</td>
		</tr>
		<tr>
			<td></td>
			<td align="right">委托单号：_______________________________________</td>
		<tr>
			<td align="left">__________________公司:</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2"> 本公司保险车辆在你司范围（附近）出险，现委托你公司按列明授权协助处理相关理赔事项。有关资料详见下表 ：</td>
		</tr>
	</table>
	<table border=1 width="92%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#111111">
		<tr height="25">
			<td colspan="1" width="15%">保单号码：</td>
			<td colspan="2" width="31%" id="tdPolicyNo">&nbsp;</td>
			<td colspan="1" width="15%">号牌号码：</td>
			<td colspan="2" width="31%" id="tdLicenseNo">&nbsp;</td>
		</tr>
		<tr height="25">
			<td colspan="1">被保险人</td>
			<td colspan="2" id="tdInsuredName">&nbsp;</td>
			<td colspan="1">保险险别</td>
			<td colspan="2" id="">&nbsp;</td>
		</tr>
		<tr height="25">
			<td colspan="1">厂牌型号</td>
			<td colspan="1" width="15%" id="tdBrandName">&nbsp;</td>
			<td colspan="1">使用性质</td>
			<td colspan="1" id="tdUseNatureCode">口营业 口非营业</td>
			<td colspan="1">附加险</td>
			<td colspan="1" width="15%" id="tdKindName0">&nbsp;</td>
		</tr>
		<tr height="25">
			<td colspan="1">发动机号</td>
			<td colspan="1" id="tdEngineNo">&nbsp;</td>
			<td colspan="1">吨/座位</td>
			<td colspan="1" id="tdSeatTonCount">&nbsp;</td>
			<td colspan="1" width="10%">附加险</td>
			<td colspan="1" id="tdKindName1">&nbsp;</td>
		</tr>
		<tr height="25">
			<td colspan="1">车损险保额</td>
			<td colspan="1" id="tdSumAmount1"><%=new DecimalFormat("#,##0.00").format(dblSumAmount1) + "元"%></td>
			<td colspan="1">第三者责任限额</td>
			<td colspan="1" id="tdSumAmount2"><%=new DecimalFormat("#,##0.00").format(dblSumAmount2) + "元"%></td>
			<td colspan="1">附加险</td>
			<td colspan="1" width="15%" id="tdKindName2">&nbsp;</td>
		</tr>
		<tr height="25">
			<td colspan="1">保险期限</td>
			<td colspan="5" id="tdInsuredDate">
				<p>自 年 月 日起 至 年 月 日止</p>
			</td>
		</tr>
		<tr height="25">
			<td colspan="1">报案号码</td>
			<td colspan="2" id="tdRegistNo">&nbsp;</td>
			<td colspan="1">出险地点</td>
			<td colspan="2" id="tdDamageAddress">&nbsp;</td>
		</tr>
		<tr height="25">
			<td colspan="1">出险时间</td>
			<td colspan="2" id="tdDamageStartDate">&nbsp;</td>
			<td colspan="1">报案时间</td>
			<td colspan="2" id="tdReportDate">&nbsp;</td>
		</tr>
		<tr height="25">
			<td colspan="1">出险原因</td>
			<td colspan="5" id="tdDamageName">&nbsp;</td>
		</tr>
	</table>
	<table width="80%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td colspan="2">
				<p>
					现委托你公司办如下事宜：<br> &nbsp;&nbsp;&nbsp;1、代查勘，並将查勘报告、定损报告、人伤调查报告、现场照片、车辆损坏照片 查勘费用收据等所有查勘资料邮寄本公司， 本公司将凭据划付查勘费用。<br> &nbsp;&nbsp;&nbsp;2、代定损。<br> &nbsp;&nbsp;&nbsp;3、<br>
					&nbsp;&nbsp;&nbsp;4、<br> &nbsp;&nbsp;&nbsp;如预估车损超过 元，第三者责任损失超过 元，敬请告之本公司後再做处理。<br> <br> 此谢！
				</p>
			</td>
		</tr>
		<tr height="25">
			<td width="46">&nbsp;</td>
			<td>委托公司:____________________________公司（章）</td>
		</tr>
		<tr height="25">
			
			<td width="46%">&nbsp;</td>
			<td>地 址:___________________________________</td>
		</tr>
		<tr height="25">
			<td>被保险人联系电话: ____________________</td>
			<td>邮政编码:____________________________________</td>
		</tr>
		<tr height="25">
			
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;____________________</td>
			<td>传 真:____________________________________</td>
		</tr>
		<tr height="25">
			
			<td>联系人：______________________________</td>
			<td>联系电话:___________________________________</td>
		<tr height="25">
			<td>&nbsp;</td>
			<td>联&nbsp;系&nbsp;人:___________________________________</td>
		</tr>
		<tr height="25">
			<td>&nbsp;</td>
			<td>委托日期___________________年_____月_____日</td>
		</tr>
	</table>
	<!--
  <br>
  联系人： 电话： 地址： <br><p>
  年 月 日 传真： 邮编： <br>
</p>-->
	<!-- 按钮部分 -->
	<%-- include列印按钮 --%>
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
