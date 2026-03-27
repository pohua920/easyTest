<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="com.sinosoft.claimprint.ui.dto.DAAPrpLItemLossConfirmationPrintDto"%>
<%@ page import="com.sinosoft.claim.dto.domain.PrpLpropDto"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<html:html lang="true">
<head>
<html:base />
<title>财产损失确认书</title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body>
	<%
		DAAPrpLItemLossConfirmationPrintDto dAAPrpLItemLossConfirmationPrintDto = (DAAPrpLItemLossConfirmationPrintDto) request.getAttribute("dAAPrpLItemLossConfirmationPrintDto");
	%>
	<table width="85%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td colspan="3" height="40" align=center style="font-family: 宋体; font-size: 14pt;">
				<img src="/claim/images/LOGO.jpg" />
			</td>
		</tr>
		<tr>
			<td colspan="3" height="40" align=center style="text-align: center; font-family: 宋体; font-size: 14pt;">
				<B>机动车辆保险财产损失确认书 </B>
			</td>
		</tr>
	</table>
	<br>
	<table width="95%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td width="18%" height="40" style="font-family: 宋体; font-size: 10pt;">被保险人：</td>
			<td width="23%" style="font-family: 宋体; font-size: 10pt;">
				<bean:write name="dAAPrpLItemLossConfirmationPrintDto" property="insuredName" />
			</td>
			<td width="18%" style="font-family: 宋体; font-size: 10pt;">交强险承保公司：</td>
			<td width="23%" style="font-family: 宋体; font-size: 10pt;"></td>
			<td width="9%" style="font-family: 宋体; font-size: 10pt;"></td>
			<td width="9%" style="font-family: 宋体; font-size: 10pt;"></td>
		</tr>
		<tr>
			<td style="font-family: 宋体; font-size: 10pt;">商业保险报案号：</td>
			<td style="font-family: 宋体; font-size: 10pt;">
				<logic:notEqual name="dAAPrpLItemLossConfirmationPrintDto" property="kindCode" value="21">
					<bean:write name="dAAPrpLItemLossConfirmationPrintDto" property="registNo" />
				</logic:notEqual>
			</td>
			<td style="font-family: 宋体; font-size: 10pt;">交强险报案号：</td>
			<td style="font-family: 宋体; font-size: 10pt;">
				<logic:equal name="dAAPrpLItemLossConfirmationPrintDto" property="kindCode" value="BZ">
					<bean:write name="dAAPrpLItemLossConfirmationPrintDto" property="registNo" />
				</logic:equal>
			</td>
			<td style="font-family: 宋体; font-size: 10pt;" colspan="2" align="right">第 &nbsp; 页&nbsp;&nbsp;共 &nbsp; 页</td>
		</tr>
	</table>
	<br>
	<table style="font-family: 宋体; font-size: 10pt; border-collapse: collapse; bordercolor: #111111;" width="100%" align="center" cellspacing="0" cellpadding="1" border="1">
		<tr>
			<td colspan="2" width="40%">
				保险单号：
				<bean:write name="dAAPrpLItemLossConfirmationPrintDto" property="policyNo" />
			</td>
			<td colspan="2" width="30%">
				厂牌型号：
				<bean:write name="dAAPrpLItemLossConfirmationPrintDto" property="brandName" />
			</td>
			<td width="30%">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2">
				车牌号码：
				<bean:write name="dAAPrpLItemLossConfirmationPrintDto" property="licenseNo" />
			</td>
			<td colspan="2">
				责任限额：
				<bean:write name="dAAPrpLItemLossConfirmationPrintDto" property="amount" />
			</td>
			<td>
				出险险别：
				<bean:write name="dAAPrpLItemLossConfirmationPrintDto" property="kindName" />
			</td>
		</tr>
		<tr>
			<td width="10%" align="center">序号</td>
			<td width="30%" align="center">损坏财产名称</td>
			<td width="15%" align="center">损坏情况</td>
			<td width="15%" align="center">价格</td>
			<td align="center">保险公司核定损失金额</td>
		</tr>
		<%
			Iterator iter = dAAPrpLItemLossConfirmationPrintDto.getPrpLPropDto().iterator();
				double sumPrice = 0.00;
				for (int i = 0; i < 28; i++) {
		%>
		<tr>
			<td align="center">
				<%=i + 1%>
			</td>
			<%
				if (iter.hasNext()) {
							PrpLpropDto prpLpropDto = (PrpLpropDto) iter.next();
							sumPrice += prpLpropDto.getSumDefLoss();
			%>
			<td align="center">
				<%=prpLpropDto.getLossItemName()%>
			</td>
			<td align="center">&nbsp;</td>
			<td align="center">
				<%=new DecimalFormat("#,##0").format(prpLpropDto
								.getSumLoss())%>
			</td>
			<td align="center">
				<%=new DecimalFormat("#,##0").format(prpLpropDto
								.getSumDefLoss())%>
			</td>
			<%
				} else {
			%>
			<td align="center">&nbsp;</td>
			<td align="center">&nbsp;</td>
			<td align="center">&nbsp;</td>
			<td align="center">&nbsp;</td>
			<%
				}
			%>
		</tr>
		<%
			}
		%>
		<tr>
			<td colspan="3" align="center">
				合计：
				<%=new DecimalFormat("#,##0").format(sumPrice)%>
			</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2">
				保险公司： <br> <br> <br> 签章： <br> <br>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;月&nbsp;&nbsp;日
			</td>
			<td colspan="2">
				被保险人： <br> <br> <br> 签章： <br> <br>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;月&nbsp;&nbsp;日
			</td>
			<td>
				<br> <br> <br> <br> <br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;月&nbsp;&nbsp;日
			</td>
		</tr>
	</table>
	<table style="font-size: 10pt; border-collapse: collapse; bordercolor: #111111;" width="100%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td width="50%" height="40" align="center" style="font-family: 宋体; font-size: 10pt;">核赔人：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td width="50%" align="center" style="font-family: 宋体; font-size: 10pt;">经办人：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		</tr>
	</table>
	<!-- 按钮部分 -->
	<%-- include打印按钮 --%>
	<jsp:include page="/pages/common/print/PrintButton.jsp" />
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
</html:html>
