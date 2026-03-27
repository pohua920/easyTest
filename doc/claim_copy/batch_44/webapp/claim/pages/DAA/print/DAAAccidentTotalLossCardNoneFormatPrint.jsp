<%--
****************************************************************************
* DESC       ：事故车辆全损单页面
* AUTHOR     ：caopeng
* CREATEDATE ：2005-12-09
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=gbk"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%-- 初始化 --%>
<%@include file="DAAAccidentTotalLossCardNoneFormatPrintIni.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title><s:text name="title.printBeforeEdit.totalLossAcc" /></title>
<%-- 事故车辆全损单 --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body>
	<table width="669" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr height=30>
			<td colspan="2" align="center" style="font-family: 宋体; font-size: 14pt;"><img src="/claim/images/LOGO.jpg" /></td>
		</tr>
		<tr height=30>
			<td height="35" colspan="2" align=center valign="middle" style="font-family: 宋体; font-size: 14pt;">
				<center>
					<p>
						<strong><s:text name="print.totalLossAcc" /></strong>
					</p>
					<%-- 事故车辆全损单 --%>
				</center>
			</td>
		</tr>
		<tr height=30>
			<td width="95%" align=center valign="middle" style="font-family: 宋体; font-size: 10pt;"><p align="right">
					&nbsp;&nbsp;
					<s:text name="manage.total" />
					<%-- 共 --%>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<s:text name="navigator.page" />
					<%-- 页 --%>
					&nbsp;&nbsp;
					<s:text name="manage.subsection" />
					<%-- 第 --%>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<s:text name="navigator.page" />
					<%-- 页 --%>
				</p></td>
			<td width="5%" align=center valign="middle" style="font-family: 宋体; font-size: 10pt;">&nbsp;</td>
		</tr>
		<tr height=30>
			<td colspan="2" align=center valign="middle" style="font-family: 宋体; font-size: 10pt;"><table width="92%" border="1" cellpadding="0" cellspacing="0" bordercolor="111111"
					style="font-family: 宋体; font-size: 10pt;">
					<tr>
						<td width="10%" height="25"><div align="center">
								<s:text name="certainLoss.prpLcheck.insuredName" />
							</div></td>
						<%-- 被保险人 --%>
						<td colspan="3">&nbsp;</td>
						<td width="12%" height="25"><div align="center">
								<s:text name="db.prpLlawsuit.licenseNo" />
							</div></td>
						<%-- 号牌号码 --%>
						<td colspan="3">&nbsp;</td>
					</tr>
					<tr>
						<td height="25"><div align="center">
								<s:text name="db.prpLlawsuit.brandName" />
							</div></td>
						<%-- 厂牌型号 --%>
						<td colspan="3">&nbsp;</td>
						<td height="25"><div align="center">
								<s:text name="print.frameNum" />
							</div></td>
						<%-- 车架号码 --%>
						<td colspan="3">&nbsp;</td>
					</tr>
					<tr>
						<td height="25"><div align="center">
								<s:text name="certainLoss.thirdCarLoss.prpLthirdPartyEngineNo" />
							</div></td>
						<%-- 发动机号 --%>
						<td colspan="3">&nbsp;</td>
						<td height="25"><div align="center">
								<s:text name="db.prpCitem_car.purchasePrice" />
							</div></td>
						<%-- 新车清除价格 --%>
						<td colspan="3">&nbsp;</td>
					</tr>
					<tr>
						<td height="25"><div align="center">
								<s:text name="db.prpLloss.amount" />
							</div></td>
						<%-- 保险金额 --%>
						<td colspan="3">&nbsp;</td>
						<td height="25"><div align="center">
								<s:text name="db.prpCcarDevice.actualValue" />
							</div></td>
						<%-- 实际价值 --%>
						<td colspan="3">&nbsp;</td>
					</tr>
					<tr>
						<td height="25"><div align="center">
								<s:text name="db.prpDScrapTerm.useYear" />
							</div></td>
						<%-- 使用年限 --%>
						<td colspan="3">&nbsp;</td>
						<td height="25"><div align="center">
								<s:text name="prpLcheck.checkDate" />
							</div></td>
						<%-- 查勘日期 --%>
						<td colspan="3">&nbsp;</td>
					</tr>
					<tr>
						<td height="25" colspan="2"><div align="center">
								<s:text name="print.scrapProject" />
							</div></td>
						<%-- 报&nbsp;&nbsp;废&nbsp;&nbsp;项&nbsp;&nbsp;目 --%>
						<td width="13%"><div align="center">
								<s:text name="print.picNo" />
							</div></td>
						<%-- 照片编号 --%>
						<td width="13%"><div align="center">
								<s:text name="certainLoss.thirdCarLoss.LossFee" />
							</div></td>
						<%-- 损失金额 --%>
						<td height="25" colspan="2"><div align="center">
								<s:text name="print.salvageProject" />
							</div></td>
						<%-- 残&nbsp;&nbsp;值&nbsp;&nbsp;项&nbsp;&nbsp;目 --%>
						<td width="13%"><div align="center">
								<s:text name="print.picNo" />
							</div></td>
						<%-- 照片编号 --%>
						<td width="11%"><div align="center">
								<s:text name="print.salvValueAmount" />
							</div></td>
						<%-- 残值金额 --%>
					</tr>
					<tr>
						<td height="25" colspan="2">&nbsp;</td>
						<td height="25">&nbsp;</td>
						<td height="25">&nbsp;</td>
						<td height="25" colspan="2">&nbsp;</td>
						<td height="25">&nbsp;</td>
						<td height="25">&nbsp;</td>
					</tr>
					<tr>
						<td height="25" colspan="2">&nbsp;</td>
						<td height="25">&nbsp;</td>
						<td height="25">&nbsp;</td>
						<td height="25" colspan="2">&nbsp;</td>
						<td height="25">&nbsp;</td>
						<td height="25">&nbsp;</td>
					</tr>
					<tr>
						<td height="25" colspan="2">&nbsp;</td>
						<td height="25">&nbsp;</td>
						<td height="25">&nbsp;</td>
						<td height="25" colspan="2">&nbsp;</td>
						<td height="25">&nbsp;</td>
						<td height="25">&nbsp;</td>
					</tr>
					<tr>
						<td height="25" colspan="2">&nbsp;</td>
						<td height="25">&nbsp;</td>
						<td height="25">&nbsp;</td>
						<td height="25" colspan="2">&nbsp;</td>
						<td height="25">&nbsp;</td>
						<td height="25">&nbsp;</td>
					</tr>
					<tr>
						<td height="25" colspan="2">&nbsp;</td>
						<td height="25">&nbsp;</td>
						<td height="25">&nbsp;</td>
						<td height="25" colspan="2">&nbsp;</td>
						<td height="25">&nbsp;</td>
						<td height="25">&nbsp;</td>
					</tr>
					<tr>
						<td height="25" colspan="2">&nbsp;</td>
						<td height="25">&nbsp;</td>
						<td height="25">&nbsp;</td>
						<td height="25" colspan="2">&nbsp;</td>
						<td height="25">&nbsp;</td>
						<td height="25">&nbsp;</td>
					</tr>
					<tr>
						<td height="25" colspan="2">&nbsp;</td>
						<td height="25">&nbsp;</td>
						<td height="25">&nbsp;</td>
						<td height="25" colspan="2">&nbsp;</td>
						<td height="25">&nbsp;</td>
						<td height="25">&nbsp;</td>
					</tr>
					<tr>
						<td height="25" colspan="2">&nbsp;</td>
						<td height="25">&nbsp;</td>
						<td height="25">&nbsp;</td>
						<td height="25" colspan="2">&nbsp;</td>
						<td height="25">&nbsp;</td>
						<td height="25">&nbsp;</td>
					</tr>
					<tr>
						<td height="25" colspan="2">&nbsp;</td>
						<td height="25">&nbsp;</td>
						<td height="25">&nbsp;</td>
						<td height="25" colspan="2">&nbsp;</td>
						<td height="25">&nbsp;</td>
						<td height="25">&nbsp;</td>
					</tr>
					<tr>
						<td height="25" colspan="2">&nbsp;</td>
						<td height="25">&nbsp;</td>
						<td height="25">&nbsp;</td>
						<td height="25" colspan="2">&nbsp;</td>
						<td height="25">&nbsp;</td>
						<td height="25">&nbsp;</td>
					</tr>
					<tr>
						<td height="25" colspan="3"><div align="center">
								<s:text name="print.total01" />
							</div></td>
						<%-- 合&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计 --%>
						<td height="25">&nbsp;</td>
						<td height="25" colspan="3"><div align="center">
								<s:text name="print.total01" />
							</div></td>
						<%-- 合&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计 --%>
						<td height="25">&nbsp;</td>
					</tr>
					<tr>
						<td height="25" colspan="8"><s:text name="print.damagesAppRMB" /> <%-- 经核定损失费合计人民币 --%>：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:text
								name="print.yuan" /> <%-- 元 --%>，<s:text name="print.vehicleActual" /> <%-- 达到该车实际价值的 --%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;%，<s:text
								name="print.repairValue" />。<%-- 失去修复价值 --%></td>
					</tr>
					<tr>
						<td height="25" colspan="8"><s:text name="print.suggestDeductRMB" /> <%-- 建议扣除残值人民币 --%>：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;，<s:text
								name="print.constTotalLoss" /> <%-- 推定全损 --%>。</td>
					</tr>
					<tr>
						<td colspan="4"><p>&nbsp;</p>
							<p>&nbsp;</p>
							<p>
								&nbsp;&nbsp;
								<s:text name="db.prpLregist.insuredName" />：
							</p> <%-- 被保险人 --%>
							<p>&nbsp;</p>
							<p>
								&nbsp;&nbsp;
								<s:text name="db.prpLregist.handler1Name" />：
							</p> <%-- 经办人 --%>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<p align="right">
								<s:text name="print.year" />
								<%-- 年 --%>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<s:text name="print.month" />
								<%-- 月 --%>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<s:text name="regist.prpLregist.date" />
								<%-- 日 --%>
								</p></td>
					          <td colspan="4">
					          <p>&nbsp;</p>
					          <p>&nbsp;</p>
					          <p>&nbsp;&nbsp;<s:text name="print.insCompanyChapter" /></p> <%-- 保险公司（章） --%>
					          <p>&nbsp;</p>
					          <p>&nbsp;&nbsp;<s:text name="db.prpLregist.handler1Name" />：</p><%-- 经办人 --%>
					          <p>&nbsp;</p>
					          <p>&nbsp;</p>
					          <p align="right">
					          <s:text name="print.year" />
					          <%-- 年 --%>
					          &nbsp;&nbsp;&nbsp;&nbsp;
					          <s:text name="print.month" />
					          <%-- 月 --%>
					          &nbsp;&nbsp;&nbsp;&nbsp;
					          <s:text name="regist.prpLregist.date" />
					          <%-- 日 --%></p>
					          </td>
						      </tr>
						    </table></td>
						  </tr>
						</table>
						<%-- include列印按钮 --%>
						      <jsp:include page="/common/print/PrintButton.jsp" />
						  <script language='javascript'>
						    function printPage() {
						    	divButton.style.display = "none";
						    	//addprintliudaoping 2013-04-15
						    	//alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
						    	returnfalse;
						    	window.print();
						    }
						  </script>



</body>
</html>
