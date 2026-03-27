<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<html:html lang="true">
<head>
<html:base />
<title>机动车辆保险赔案报告书</title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body>
	<table width="100%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td colspan="4" height="40" align=center style="font-family: 宋体; font-size: 14pt;">
				<img src="/claim/images/LOGO.jpg" />
			</td>
		</tr>
		<tr>
			<td colspan="4" height="40" align=center style="font-family: 宋体; font-size: 16pt;">
				<p align=center>
					<B>机动车辆保险赔案报告书<B>
				</p>
				<br>
			</td>
		</tr>
		<tr>
			<td colspan="4" align=left style="font-family: 宋体; font-size: 10pt;">
				被保险人：
				<bean:write name="dAAReparationReportPrintDto" property="insuredName" />
			</td>
		</tr>
		<tr>
			<td width="18%" align=left style="font-family: 宋体; font-size: 10pt;">交强险承保公司：</td>
			<td width="14%" align=left style="font-family: 宋体; font-size: 10pt;">
				<bean:write name="dAAReparationReportPrintDto" property="compelCompany" />
			</td>
			<td width="34%" align=left style="font-family: 宋体; font-size: 10pt;">
				交强险保单号：
				<bean:write name="dAAReparationReportPrintDto" property="compelPolicyNo" />
			</td>
			<td width="34%" align=left style="font-family: 宋体; font-size: 10pt;">
				交强险立案号：
				<bean:write name="dAAReparationReportPrintDto" property="compelClaimNo" />
			</td>
		</tr>
		<tr>
			<td align=left style="font-family: 宋体; font-size: 10pt;">商业险承保公司：</td>
			<td align=left style="font-family: 宋体; font-size: 10pt;">
				<bean:write name="dAAReparationReportPrintDto" property="businessCompany" />
			</td>
			<td width="34%" align=left style="font-family: 宋体; font-size: 10pt;">
				商业险保单号：
				<bean:write name="dAAReparationReportPrintDto" property="businessPolicyNo" />
			</td>
			<td width="34%" align=left style="font-family: 宋体; font-size: 10pt;">
				商业险立案号：
				<bean:write name="dAAReparationReportPrintDto" property="businessClaimNo" />
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<hr>
			</td>
		</tr>
		<tr height="300">
			<td colspan="4" style="font-family: 宋体; font-size: 11pt;">
				&nbsp;&nbsp;&nbsp;&nbsp; <u><bean:write name="dAAReparationReportPrintDto" property="damageDate" /> </u> ，驾驶人员（姓名）： <u><logic:equal name="dAAReparationReportPrintDto" property="driverName"
						value="">&nbsp;&nbsp;&nbsp;&nbsp;</logic:equal> <bean:write name="dAAReparationReportPrintDto" property="driverName" /> </u> （驾驶证号码： <u><logic:equal name="dAAReparationReportPrintDto"
						property="drivingLicenseNo" value="">&nbsp;&nbsp;&nbsp;&nbsp;</logic:equal> <bean:write name="dAAReparationReportPrintDto" property="drivingLicenseNo" /> </u> ；驾龄: <u><bean:write
						name="dAAReparationReportPrintDto" property="driverAge" /> </u>年 ；准驾车型： <u><logic:equal name="dAAReparationReportPrintDto" property="drivingCarType" value="">&nbsp;&nbsp;&nbsp;&nbsp;</logic:equal>
					<bean:write name="dAAReparationReportPrintDto" property="drivingCarType" /> </u> ）驾驶（号牌号码： <u><bean:write name="dAAReparationReportPrintDto" property="lisenseNo" /> </u> ；厂牌型号： <u> <bean:write
						name="dAAReparationReportPrintDto" property="brandName" />
				</u> ）车辆 ，因 <u><bean:write name="dAAReparationReportPrintDto" property="damageTypeName" /> </u> 原因发生 <u> <bean:write name="dAAReparationReportPrintDto" property="damageName" />
				</u> （出险原因）事故，造成保险损失。 <br> <br> <br> <br> <br> &nbsp;&nbsp;&nbsp;&nbsp;接到报案後， 由 <u><bean:write name="dAAReparationReportPrintDto" property="checker1" /> </u> <u><logic:notEqual
						name="dAAReparationReportPrintDto" property="checker2" value=" ">&nbsp;和&nbsp;<bean:write name="dAAReparationReportPrintDto" property="checker2" />&nbsp;&nbsp;</logic:notEqual> </u> 同志於 <u><bean:write
						name="dAAReparationReportPrintDto" property="checkDate" /> </u>到 <u><bean:write name="dAAReparationReportPrintDto" property="checkSite" /> </u>（地点） 进行了查勘。根据查勘情况以及有关证明材料，认定该事故属 <u><bean:write
						name="dAAReparationReportPrintDto" property="kindName" /> </u> （险种）保险责任。此次事故经 <u><bean:write name="dAAReparationReportPrintDto" property="checker1" /> </u> 认定被保险人负 <u><bean:write
						name="dAAReparationReportPrintDto" property="indemnityRuty" /> </u> 责任，被保险人应承担 <u><bean:write name="dAAReparationReportPrintDto" property="indemnityRutyRate" /> </u> ％的损失。
			</td>
		</tr>
		<tr height="100">
			<td colspan="4" style="font-family: 宋体; font-size: 11pt;">1、本车交强险情况：</td>
		</tr>
		<tr height="200">
			<td colspan="4" style="font-family: 宋体; font-size: 11pt;">
				2、本车商业保险承保情况： <br> <br> 被保险人应缴商业保险费： <u><bean:write name="dAAReparationReportPrintDto" property="sumAmount" /> </u> 元，已缴付 <u><bean:write name="dAAReparationReportPrintDto"
						property="haveAmount" /> </u> 元。 <br> 经过（□第一现场 □第二现场）定损，本次事故损失核定如下：（按险种列明） <br> <br> <br>
				<logic:notEmpty name="dAAReparationReportPrintDto" property="compensateData">
					<logic:iterate id="comp" name="dAAReparationReportPrintDto" property="compensateData">
						<bean:write name="comp" property="kindName" /> : <bean:write name="comp" property="lossName" />
						<bean:write name="comp" property="feeTypeName" />：<bean:write name="comp" property="sumDefPay" />元<br>
					</logic:iterate>
				</logic:notEmpty>
				<br> <br> 经计算，赔款建议如下：（按交强险和商业保险的各类限额或险种列明） <br> <br> <br>
				<logic:notEmpty name="dAAReparationReportPrintDto" property="compensateData">
					<logic:iterate id="comp2" name="dAAReparationReportPrintDto" property="compensateData">
						<bean:write name="comp2" property="kindName" /> : <bean:write name="comp2" property="lossName" />
						<bean:write name="comp2" property="feeTypeName" />：<bean:write name="comp2" property="sumRealPay" />元<br>
					</logic:iterate>
				</logic:notEmpty>
				<br> <br> 赔款总计：
				<bean:write name="dAAReparationReportPrintDto" property="compensateFee" />
				元
			</td>
		</tr>
		<tr height="100">
			<td style="font-family: 宋体; font-size: 11pt;" align="right" colspan="4">
				报告人：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <br> <br> <u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u>年 <u>&nbsp;&nbsp;&nbsp;&nbsp;</u>月 <u>&nbsp;&nbsp;&nbsp;&nbsp;</u>日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</td>
		</tr>
		<!-- 按钮部分 -->
		<%-- include列印按钮 --%>
		<div align="center" id="divButton" style="display:">
			<p>
			<table cellpadding="0" cellspacing="0" width="80%" id="test" style="display:">
				<tr>
					<td class=button style="width: 50%" align="center">
						<input class="button" type="button" name="buttonPrint" value=" 列 印 " onclick="printPage()">
					</td>
					<td class=button style="width: 50%" align="center">
						<input class="button" type="button" name="buttonClose" value=" 关 闭 " onclick="window.close();">
					</td>
				</tr>
			</table>
			</p>
		</div>
		<script language='javascript'>
			function printPage() {
				//add print liudaoping 2013-04-15
				//alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
				return false;
				test.style.display = "none";
				window.print();
			}
		</script>
</body>
</html:html>
