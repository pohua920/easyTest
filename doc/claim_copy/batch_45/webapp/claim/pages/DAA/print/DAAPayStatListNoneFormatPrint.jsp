<%--
****************************************************************************
* DESC       ：机动车辆保险赔款统计明细表打印页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-11-16
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@page errorPage="/UIErrorPage"%>
<%-- 初始化 --%>
<%@include file="DAAPayStatListNoneFormatPrintIni.jsp"%>
<html>
<head>
<title>机动车辆保险赔款统计明细表列印</title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body bgcolor="#FFFFFF" onload="loadForm();">
	<!-- 标题部分 -->
	<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td colspan="2" height="40" align=top align=center style="text-align: center; font-family: 宋体; font-size: 14pt;">
				<B>机动车辆保险赔款统计明细表<B>
			</td>
		</tr>
		<tr>
			<td width="50%" align=left id="tdInsuredName" style="font-family: 宋体; font-size: 10pt;">被保险人：</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td width="50%" align=left id="tdPolicyNo" style="font-family: 宋体; font-size: 10pt;">保险单号：</td>
			<td width="50%" align=left id="tdClauseType" style="font-family: 宋体; font-size: 10pt;">条款类别：</td>
		</tr>
		<tr>
			<td width="50%" align=left id="tdClaimNo" style="font-family: 宋体; font-size: 10pt;">立案编号：</td>
			<td width="50%" align=left id="tdCaseNo" style="font-family: 宋体; font-size: 10pt;">赔案编号：</td>
		</tr>
	</table>
	<!-- 主体部分 -->
	<table border=1 width="92%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
		<tr align="center">
			<td width="4%" align="center">险别</td>
			<td colspan="2">赔款项目</td>
			<td width="10%">损失金额</td>
			<td width="10%">赔偿金额</td>
			<td width="4%" align="center">险别</td>
			<td width="25%">赔款项目</td>
			<td width="10%">损失金额</td>
			<td width="10%">赔偿金额</td>
		</tr>
		<tr>
			<td rowspan="6" width="4%" align="center">车辆损失险赔款</td>
			<td colspan="2" height="25">材料费</td>
			<td width="10%" height="25"><%=strCarCaiLiaoLoss%></td>
			<td width="10%" height="25"><%=strCarCaiLiaoRealpay%></td>
			<td rowspan="14" align="center" width="4%">
				<p>附</p>
				<p>加</p>
				<p>车</p>
				<p>上</p>
				<p>人</p>
				<p>员</p>
				<p>责</p>
				<p>任</p>
				<p>险</p>
				<p>赔</p>
				<p>款</p>
			</td>
			<td width="25%" height="25">医疗费（含二次费用）</td>
			<td width="10%" height="25"><%=strPersonYiliaoLoss%></td>
			<td width="10%" height="25"><%=strPersonYiliaoRealpay%></td>
		</tr>
		<tr>
			<td colspan="2" height="25">工时费</td>
			<td width="10%" height="25"><%=strCarGongshiLoss%></td>
			<td width="10%" height="25"><%=strCarGongshiRealpay%></td>
			<td width="25%" height="25">误工费</td>
			<td width="10%" height="25"><%=strPersonWugongLoss%></td>
			<td width="10%" height="25"><%=strPersonWugongRealpay%></td>
		</tr>
		<tr>
			<td colspan="2" height="25">施救费</td>
			<td width="10%" height="25"><%=strCarShijiuLoss%></td>
			<td width="10%" height="25"><%=strCarShijiuRealpay%></td>
			<td width="25%" height="25">住院伙食补助费</td>
			<td width="10%" height="25"><%=strPersonZhuyuanLoss%></td>
			<td width="10%" height="25"><%=strPersonZhuyuanRealpay%></td>
		</tr>
		<tr>
			<td colspan="2" height="25">残值</td>
			<td width="10%" height="25"><%=strCarSumRest%></td>
			<td width="10%" height="25"></td>
			<td width="25%" height="25">护理费</td>
			<td width="10%" height="25"><%=strPersonHuliLoss%></td>
			<td width="10%" height="25"><%=strPersonHuliRealpay%></td>
		</tr>
		<tr>
			<td colspan="2" height="25">其他费用</td>
			<td width="10%" height="25"><%=strCarCarElseLoss%></td>
			<td width="10%" height="25"><%=strCarCarElseRealpay%></td>
			<td width="25%" height="25">医疗护理费合计</td>
			<td width="10%" height="25"><%=strPersonYiliaoSumLoss%></td>
			<td width="10%" height="25"><%=strPersonYiliaoSumRealpay%></td>
		</tr>
		<tr>
			<td colspan="2" height="25">小计</td>
			<td width="10%" height="25"><%=strCarCarSumLoss%></td>
			<td width="10%" height="25"><%=strCarCarSumRealpay%></td>
			<td width="25%" height="25">残疾者生活补助费</td>
			<td width="10%" height="25"><%=strPersonCanshengLoss%></td>
			<td width="10%" height="25"><%=strPersonCanshengRealpay%></td>
		</tr>
		<tr>
			<td rowspan="26" align="center" width="4%">
				<p>第</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
				<p>三</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
				<p>责</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
				<p>任</p>
			</td>
			<td rowspan="6" width="4%" align="center">
				<p>车</p>
				<p>损</p>
				<p>赔</p>
				<p>款</p>
			</td>
			<td width="23%" height="25">材料费</td>
			<td width="10%" height="25"><%=strThirdCailiaoLoss%></td>
			<td width="10%" height="25"><%=strThirdCailiaoRealpay%></td>
			<td width="25%" height="25">残疾用具费</td>
			<td width="10%" height="25"><%=strPersonCanjuLoss%></td>
			<td width="10%" height="25"><%=strPersonCanjuRealpay%></td>
		</tr>
		<tr>
			<td width="23%" height="25">工时费</td>
			<td width="10%" height="25"><%=strThirdGongshiLoss%></td>
			<td width="10%" height="25"><%=strThirdGongshiRealpay%></td>
			<td width="25%" height="25">死亡补偿费</td>
			<td width="10%" height="25"><%=strPersonSiwangLoss%></td>
			<td width="10%" height="25"><%=strPersonSiwangRealpay%></td>
		</tr>
		<tr>
			<td width="23%" height="25">施救费</td>
			<td width="10%" height="25"><%=strThirdShijiuLoss%></td>
			<td width="10%" height="25"><%=strThirdShijiuRealpay%></td>
			<td width="25%" height="25">丧葬费</td>
			<td width="10%" height="25"><%=strPersonSangzangLoss%></td>
			<td width="10%" height="25"><%=strPersonSangzangRealpay%></td>
		</tr>
		<tr>
			<td width="23%" height="25">残值</td>
			<td width="10%" height="25"><%=strThirdSumRest%></td>
			<td width="10%" height="25"></td>
			<td width="25%" height="25">残、亡补费小计</td>
			<td width="10%" height="25"><%=strPersonCanwangSumLoss%></td>
			<td width="10%" height="25"><%=strPersonCanwangSumRealpay%></td>
		</tr>
		<tr>
			<td width="23%" height="25">其他费用</td>
			<td width="10%" height="25"><%=strThirdCarElseLoss%></td>
			<td width="10%" height="25"><%=strThirdCarElseRealpay%></td>
			<td width="25%" height="25">被抚养人生活费</td>
			<td width="10%" height="25"><%=strPersonFuyangLoss%></td>
			<td width="10%" height="25"><%=strPersonFuyangRealpay%></td>
		</tr>
		<tr>
			<td width="23%" height="25">小计</td>
			<td width="10%" height="25"><%=strThirdCarSumLoss%></td>
			<td width="10%" height="25"><%=strThirdCarSumRealpay%></td>
			<td width="25%" height="25">交通费</td>
			<td width="10%" height="25"><%=strPersonJiaotongLoss%></td>
			<td width="10%" height="25"><%=strPersonJiaotongRealpay%></td>
		</tr>
		<tr>
			<td rowspan="17" width="4%" align="center">
				<p>人</p>
				<p>员</p>
				<p>伤</p>
				<p>亡</p>
				<p>赔</p>
				<p>款</p>
			</td>
			<td width="23%" height="25">医疗费（含二次费用）</td>
			<td width="10%" height="25"><%=strThirdYiliaoLoss%></td>
			<td width="10%" height="25"><%=strThirdYiliaoRealpay%></td>
			<td width="25%" height="25">住宿费</td>
			<td width="10%" height="25"><%=strPersonZhusuLoss%></td>
			<td width="10%" height="25"><%=strPersonZhusuRealpay%></td>
		</tr>
		<tr>
			<td width="23%" height="25">误工费</td>
			<td width="10%" height="25"><%=strThirdWugongLoss%></td>
			<td width="10%" height="25"><%=strThirdWugongRealpay%></td>
			<td width="25%" height="25">小计</td>
			<td width="10%" height="25"><%=strPersonSumLoss%></td>
			<td width="10%" height="25"><%=strPersonSumRealpay%></td>
		</tr>
		<tr>
			<td width="23%" height="25">住院伙食补助费</td>
			<td width="10%" height="25"><%=strThirdZhuyuanLoss%></td>
			<td width="10%" height="25"><%=strThirdZhuyuanRealpay%></td>
			<td colspan="2" height="25">盗抢险赔款</td>
			<td width="10%" height="25"><%=strDaoqiangLoss%></td>
			<td width="10%" height="25"><%=strDaoqiangRealpay%></td>
		</tr>
		<tr>
			<td width="23%" height="25">护理费</td>
			<td width="10%" height="25"><%=strThirdHuliLoss%></td>
			<td width="10%" height="25"><%=strThirdHuliRealpay%></td>
			<td colspan="2" height="25">火灾、爆炸、自然损失险赔款</td>
			<td width="10%" height="25"><%=strHuozaiLoss%></td>
			<td width="10%" height="25"><%=strHuozaiRealpay%></td>
		</tr>
		<tr>
			<td width="23%" height="25">医疗护理费小计</td>
			<td width="10%" height="25"><%=strThirdYiliaoSumLoss%></td>
			<td width="10%" height="25"><%=strThirdYiliaoSumRealpay%></td>
			<td colspan="2" height="25">自然损失险赔款</td>
			<td width="10%" height="25"><%=strZiranLoss%></td>
			<td width="10%" height="25"><%=strZiranRealpay%></td>
		</tr>
		<tr>
			<td width="23%" height="25">残疾者生活补助费</td>
			<td width="10%" height="25"><%=strThirdCanshengLoss%></td>
			<td width="10%" height="25"><%=strThirdCanshengRealpay%></td>
			<td colspan="2" height="25">玻璃单独破碎险赔款</td>
			<td width="10%" height="25"><%=strPoliLoss%></td>
			<td width="10%" height="25"><%=strPoliRealpay%></td>
		</tr>
		<tr>
			<td width="23%" height="25">残疾用具费</td>
			<td width="10%" height="25"><%=strThirdCanjuLoss%></td>
			<td width="10%" height="25"><%=strThirdCanjuRealpay%></td>
			<td colspan="2" height="25">车上货物责任险赔款</td>
			<td width="10%" height="25"><%=strHuowuLoss%></td>
			<td width="10%" height="25"><%=strHuowuRealpay%></td>
		</tr>
		<tr>
			<td width="23%" height="25">残补费小计</td>
			<td width="10%" height="25"><%=strThirdCanbuSumLoss%></td>
			<td width="10%" height="25"><%=strThirdCanbuSumRealpay%></td>
			<td colspan="2" height="25">停驶损失险赔款</td>
			<td width="10%" height="25"><%=strTingshiLoss%></td>
			<td width="10%" height="25"><%=strTingshiRealpay%></td>
		</tr>
		<tr>
			<td width="23%" height="25">死亡补偿费</td>
			<td width="10%" height="25"><%=strThirdSiwangLoss%></td>
			<td width="10%" height="25"><%=strThirdSiwangRealpay%></td>
			<td colspan="2" height="25">无过失责任险赔款</td>
			<td width="10%" height="25"><%=strWuguoshiLoss%></td>
			<td width="10%" height="25"><%=strWuguoshiRealpay%></td>
		</tr>
		<tr>
			<td width="23%" height="25">丧葬费</td>
			<td width="10%" height="25"><%=strThirdSangzangLoss%></td>
			<td width="10%" height="25"><%=strThirdSangzangRealpay%></td>
			<td colspan="2" height="25">车身划痕损失险</td>
			<td width="10%" height="25"><%=strHuahenLoss%></td>
			<td width="10%" height="25"><%=strHuahenRealpay%></td>
		</tr>
		<tr>
			<td width="23%" height="25">亡补费小计</td>
			<td width="10%" height="25"><%=strThirdWangbuSumLoss%></td>
			<td width="10%" height="25"><%=strThirdWangbuSumRealpay%></td>
			<td colspan="2" height="25">不计免赔特约赔款</td>
			<td width="10%" height="25"><%=strMianpeiLoss%></td>
			<td width="10%" height="25"><%=strMianpeiRealpay%></td>
		</tr>
		<tr>
			<td width="23%" height="25">被抚养人生活费</td>
			<td width="10%" height="25"><%=strThirdFuyangLoss%></td>
			<td width="10%" height="25"><%=strThirdfuyangRealpay%></td>
			<td colspan="2" height="25">救助特约赔款</td>
			<td width="10%" height="25"><%=strJiuzhuLoss%></td>
			<td width="10%" height="25"><%=strJiuzhuRealpay%></td>
		</tr>
		<tr>
			<td width="23%" height="25">交通费</td>
			<td width="10%" height="25"><%=strThirdJiaotongLoss%></td>
			<td width="10%" height="25"><%=strThirdJiaotongRealpay%></td>
			<td colspan="2" height="25">附加险赔款合计</td>
			<td width="10%" height="25"><%=strElseRiskLoss%></td>
			<td width="10%" height="25"><%=strElseRiskRealpay%></td>
		</tr>
		<tr>
			<td width="23%" height="25">住宿费</td>
			<td width="10%" height="25"><%=strThirdZhusuLoss%></td>
			<td width="10%" height="25"><%=strThirdZhusuRealpay%></td>
			<td colspan="2" height="25">代查勘费</td>
			<td width="10%" height="25"><%=strChakanLoss%></td>
			<td width="10%" height="25"><%=strChakanRealpay%></td>
		</tr>
		<tr>
			<td width="23%" height="25">交通住宿费小计</td>
			<td width="10%" height="25"><%=strThirdJiaotongSumLoss%></td>
			<td width="10%" height="25"><%=strThirdJiaotongSumRealpay%></td>
			<td colspan="2" height="25">鉴定费</td>
			<td width="10%" height="25"><%=strJiandingLoss%></td>
			<td width="10%" height="25"><%=strJiandingRealpay%></td>
		</tr>
		<tr>
			<td width="23%" height="25">其他费用</td>
			<td width="10%" height="25"><%=strThirdPersonElseLoss%></td>
			<td width="10%" height="25"><%=strThirdPersonElseRealpay%></td>
			<td colspan="2" height="25">其他诉讼、仲裁费</td>
			<td width="10%" height="25"><%=strElseSusongLoss%></td>
			<td width="10%" height="25"><%=strElseSusongRealpay%></td>
		</tr>
		<tr>
			<td width="23%" height="25">人员伤亡费合计</td>
			<td width="10%" height="25"><%=strThirdPersonSumLoss%></td>
			<td width="10%" height="25"><%=strThirdPersonSumRealpay%></td>
			<td colspan="2" height="25">损余物资/残值金额</td>
			<td width="10%" height="25"><%=strElseSumRest%></td>
			<td width="10%" height="25"></td>
		</tr>
		<tr>
			<td colspan="2" height="25">&nbsp;&nbsp;&nbsp;其他财产赔款</td>
			<td width="10%" height="25"><%=strThirdPropElseLoss%></td>
			<td width="10%" height="25"><%=strThirdPropElseRealpay%></td>
			<td colspan="2" height="25">预付赔款</td>
			<td width="10%" height="25"><%=strSumPrepaid%></td>
			<td width="10%" height="25"></td>
		</tr>
		<tr>
			<td colspan="2" height="25">&nbsp;&nbsp;&nbsp;诉讼、仲裁费用</td>
			<td width="10%" height="25"><%=strSusongLoss%></td>
			<td width="10%" height="25"><%=strSusongRealpay%></td>
			<td colspan="2" height="25">其它费用</td>
			<td width="10%" height="25"><%=strChargeElseLoss%></td>
			<td width="10%" height="25"><%=strChargeElseRealpay%></td>
		</tr>
		<tr>
			<td colspan="2" height="25">&nbsp;&nbsp;&nbsp;小计</td>
			<td width="10%" height="25"><%=strThirdSumLoss%></td>
			<td width="10%" height="25"><%=strThirdSumRealpay%></td>
			<td colspan="2" height="25">赔款合计</td>
			<td colspan="2" width="10%" height="25"><%=strSumPaid%></td>
		</tr>
	</table>
	<%-- include打印按钮 --%>
	<jsp:include page="/common/print/PrintButton.jsp" />
</body>
</html>
