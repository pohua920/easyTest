
<html>
<head>
<title><s:text name="title.printBeforeEdit.motorVehRegistPrint" /></title>
<%-- 机动车辆保险“双代”登记簿清单打印 --%>
<link rel="stylesheet" type="text/css" href="Standard.css">
</head>
<body bgcolor="#FFFFFF">
	<!-- 标题部分 -->
	<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td colspan="2" height="40" align=top align=center style="text-align: center; font-family: 宋体; font-size: 14pt;"><B><s:text name="print.motorVehRegist" /><B> <%-- 机动车辆保险“双代”登记簿 --%></td>
		</tr>
	</table>
	
	<s:text name="print.fillUnit" />
	<%-- 填制单位 --%>： 
	<s:text name="print.fillSheet" />
	<%-- 登记期限 --%>：  
	<s:text name="print.tabulatingDate" />
	<%-- 制表日期 --%>：
	<s:text name="print.year" />
	<%-- 年 --%>
	<s:text name="print.month" />
	<%-- 月 --%>
	<s:text name="regist.prpLregist.date" />
	<%-- 日 --%>
	<table border=1 width="97%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
		<tr>
			<td width="2%" rowspan="2"><s:text name="regist.prpLregist.serialNo" /></td>
			<%-- 序号 --%>
			<td width="7%"><s:text name="print.caseNo" /></td>
			<%-- 立案编号 --%>
			<td width="7%"><s:text name="db.prpLregist.insuredName" /></td>
			<%-- 被保险人 --%>
			<td width="7%"><s:text name="certainLoss.thirdCarLoss.prpLthirdPartyBrandName" /></td>
			<%-- 厂牌型号 --%>
			<td width="8%"><s:text name="db.prpLsalvation.driverName" /></td>
			<%-- 驾驶员姓名 --%>
			<td colspan="2"><s:text name="certainLoss.prpLcheck.prpLcheckDamageAddress" /></td>
			<%-- 出险地点 --%>
			<td width="7%"><s:text name="regist.prpLregist.reportType" /></td>
			<%-- 报案方式 --%>
			<td width="8%" rowspan="2"><s:text name="print.acceptPersonOpin" /></td>
			<%-- 受理人处理意见 --%>
			<td width="12%" rowspan="2"><s:text name="print.insCompanyFeedback" /></td>
			<%-- 承保公司反馈情况日期及委托事宜 --%>
			<td width="8%"><s:text name="certainLoss.prpLscheduleMainWF.Handler" /></td>
			<%-- 查勘人员 --%>
			<td width="6%" rowspan="2"><s:text name="check.surveyTtime" /></td>
			<%-- 查勘时间 --%>
			<td width="6%" rowspan="2"><s:text name="print.estimatTotalLoss" /></td>
			<%-- 估损金额合计 --%>
			<td width="8%" rowspan="2"><s:text name="db.prpLcomponent.remark" /></td>
			<%-- 备注 --%>
		</tr>
		<tr>
			<td><s:text name="print.claimNo" /></td>
			<%-- 赔案编号 --%>
			<td><s:text name="db.prpLlawsuit.licenseNo" /></td>
			<%-- 号牌号码 --%>
			<td><s:text name="db.prpLlawsuit.licenseNo" /></td>
			<%-- 号牌号码 --%>
			<td><s:text name="regist.prpLregist.damageCode" /></td>
			<%-- 出险原因 --%>
			<td width="7%"><s:text name="regist.prpLregist.damageTime" /></td>
			<%-- 出险时间 --%>
			<td width="7%"><s:text name="regist.prpLregist.registTime" /></td>
			<%-- 报案时间 --%>
			<td><s:text name="prpLregist.reportorName" /></td>
			<%-- 报案人 --%>
			<td><s:text name="prpLcheck.checkArea" /></td>
			<%-- 查勘地点 --%>
		</tr>
		<tr>
			<td rowspan="2">1</td>
			<td></td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td colspan="2">&nbsp;</td>
			<td></td>
			<td rowspan="2">&nbsp;</td>
			<td rowspan="2"></td>
			<td>&nbsp;</td>
			<td rowspan="2">&nbsp;</td>
			<td rowspan="2">&nbsp;</td>
			<td rowspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td rowspan="2">2</td>
			<td></td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td colspan="2">&nbsp;</td>
			<td></td>
			<td rowspan="2">&nbsp;</td>
			<td rowspan="2"></td>
			<td>&nbsp;</td>
			<td rowspan="2">&nbsp;</td>
			<td rowspan="2">&nbsp;</td>
			<td rowspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td rowspan="2">3</td>
			<td></td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td colspan="2">&nbsp;</td>
			<td></td>
			<td rowspan="2">&nbsp;</td>
			<td rowspan="2"></td>
			<td>&nbsp;</td>
			<td rowspan="2">&nbsp;</td>
			<td rowspan="2">&nbsp;</td>
			<td rowspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td rowspan="2">4</td>
			<td></td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td colspan="2">&nbsp;</td>
			<td></td>
			<td rowspan="2">&nbsp;</td>
			<td rowspan="2"></td>
			<td>&nbsp;</td>
			<td rowspan="2">&nbsp;</td>
			<td rowspan="2">&nbsp;</td>
			<td rowspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td rowspan="2">5</td>
			<td></td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td colspan="2">&nbsp;</td>
			<td></td>
			<td rowspan="2">&nbsp;</td>
			<td rowspan="2"></td>
			<td>&nbsp;</td>
			<td rowspan="2">&nbsp;</td>
			<td rowspan="2">&nbsp;</td>
			<td rowspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td rowspan="2">6</td>
			<td></td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td colspan="2">&nbsp;</td>
			<td></td>
			<td rowspan="2">&nbsp;</td>
			<td rowspan="2"></td>
			<td>&nbsp;</td>
			<td rowspan="2">&nbsp;</td>
			<td rowspan="2">&nbsp;</td>
			<td rowspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td rowspan="2">7</td>
			<td></td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td colspan="2">&nbsp;</td>
			<td></td>
			<td rowspan="2">&nbsp;</td>
			<td rowspan="2"></td>
			<td>&nbsp;</td>
			<td rowspan="2">&nbsp;</td>
			<td rowspan="2">&nbsp;</td>
			<td rowspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td rowspan="2">8</td>
			<td></td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td colspan="2">&nbsp;</td>
			<td></td>
			<td rowspan="2">&nbsp;</td>
			<td rowspan="2"></td>
			<td>&nbsp;</td>
			<td rowspan="2">&nbsp;</td>
			<td rowspan="2">&nbsp;</td>
			<td rowspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td rowspan="2">9</td>
			<td></td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td colspan="2">&nbsp;</td>
			<td></td>
			<td rowspan="2">&nbsp;</td>
			<td rowspan="2"></td>
			<td>&nbsp;</td>
			<td rowspan="2">&nbsp;</td>
			<td rowspan="2">&nbsp;</td>
			<td rowspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td rowspan="2">10</td>
			<td></td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td colspan="2">&nbsp;</td>
			<td></td>
			<td rowspan="2">&nbsp;</td>
			<td rowspan="2"></td>
			<td>&nbsp;</td>
			<td rowspan="2">&nbsp;</td>
			<td rowspan="2">&nbsp;</td>
			<td rowspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td rowspan="2">11</td>
			<td></td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td colspan="2">&nbsp;</td>
			<td></td>
			<td rowspan="2">&nbsp;</td>
			<td rowspan="2"></td>
			<td>&nbsp;</td>
			<td rowspan="2">&nbsp;</td>
			<td rowspan="2">&nbsp;</td>
			<td rowspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td rowspan="2">12</td>
			<td></td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td colspan="2">&nbsp;</td>
			<td></td>
			<td rowspan="2">&nbsp;</td>
			<td rowspan="2"></td>
			<td>&nbsp;</td>
			<td rowspan="2">&nbsp;</td>
			<td rowspan="2">&nbsp;</td>
			<td rowspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td colspan="12"><s:text name="certainLoss.subtotal" /></td>
			<%-- 小计 --%>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
	</table>
	
	<s:text name="print.review" />
	<%-- 复核人 --%>
	：
	<s:text name="print.lister" />
	<%-- 制表人 --%>
	： 
	<s:text name="manage.subsection" />
	<%-- 第 --%>
	
	<s:text name="navigator.page" />
	<%-- 页 --%>
	，
	<s:text name="manage.total" />
	<%-- 共 --%>
	
	<s:text name="navigator.page" />
	<%-- 页 --%>
	<!-- 按钮部分 -->
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