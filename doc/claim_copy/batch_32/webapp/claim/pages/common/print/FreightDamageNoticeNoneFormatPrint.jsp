<%--
***************************************************************************
* DESC       ：货运险理赔信息摘要清单打印
* AUTHOR     ：wangwei
* CREATEDATE ：2005-5-22
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%-- 初始化 --%>
<%@include file="FreightDamageNoticeNoneFormatPrintIni.jsp"%>
<html>
<head>
<title>货运险理赔信息摘要清单列印</title>
<link rel="stylesheet" type="text/css" href="Standard.css">
</head>
<body bgcolor="#FFFFFF" onload="loadForm()">
	<!-- 标题部分 -->
	<table width="90%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td colspan="2" height="40" align=top align=center style="text-align: center; font-family: 宋体; font-size: 14pt;">
				<B>货运险理赔信息摘要 <B>
			</td>
		</tr>
		<tr>
			<td id="tdRegistNo" style="text-align: right; font-family: 宋体; font-size: 11pt;"></td>
		</tr>
	</table>
	<table border=1 width="90%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:11pt;">
		<tr>
			<td width="20%">被保险人</td>
			<td width="30%" id="tdInsuredName" colspan="2"></td>
			<td width="20%">保单号码</td>
			<td width="30%" id="tdPolicyNo" colspan="2"></td>
		</tr>
		<tr>
			<td>保险条款及险别</td>
			<td id="tdKindName" colspan="2"><%=strKindName%></td>
			<td>预约协议号</td>
			<td id="tdBargainNo" colspan="2"><%=strMainPolicyNo%></td>
		</tr>
		<tr>
			<td>保险金额</td>
			<td id="tdAmount" colspan="2"></td>
			<td rowspan="2">货物名称及数量</td>
			<td rowspan="2" id="tdQuantity" colspan="2"><%=strLossName%>
				<%=strLossQuantity%>
				<%=strUnit%></td>
		</tr>
		<tr>
			<td>起运日期</td>
			<td id="tdStartDate" colspan="2"></td>
		</tr>
		<tr>
			<td>运输路线</td>
			<td id="tdLindline" colspan="2"></td>
			<td>运输方式</td>
			<td id="tdConveyance" colspan="2"></td>
		</tr>
		<tr>
			<td>航次/运输工具</td>
			<td colspan="2"><%=strBLNo%></td>
			<td>报损金额</td>
			<td id="tdEstimateLoss" colspan="2"></td>
		</tr>
		<tr>
			<td>出险日期</td>
			<td id="tdDamageStartDate"></td>
			<td>出险地点</td>
			<td id="tdDamageAddress"></td>
			<td>出险原因</td>
			<td id="tdDamageName"></td>
		</tr>
		<tr>
			<td>报案人</td>
			<td>
				<%=strReportorName%></td>
			<td>联系电话</td>
			<td>
				<%=strPhoneNumber%></td>
			<td>报案日期</td>
			<td>
				<%=strReportDate%></td>
		</tr>
		<tr>
			<td height="60pix" colspan="6" align="left" valign="top">
				出险摘要：
				<%=strContent%></td>
		</tr>
		<tr>
			<td colspan="6">
				以上为报案登记信息，登记人：<%=strReceiverName%>登记日期：<%=strInputDate%></td>
		</tr>
		<tr>
			<td>审批人签字</td>
			<td></td>
			<td>审批日期</td>
			<td></td>
			<td>审批意见</td>
			<td></td>
		</tr>
		<tr>
			<td>经办人签字</td>
			<td colspan="2"></td>
			<td>日期</td>
			<td colspan="2"></td>
		</tr>
		<tr>
			<td colspan="6" align="center">以下信息理赔经办填写</td>
		</tr>
		<tr>
			<td>案件类型</td>
			<td colspan="2">单损□共损□救助□</td>
			<td>是否可以追偿</td>
			<td colspan="2">是□否□</td>
		</tr>
		<tr>
			<td>检验代理人/查勘人</td>
			<td colspan="2"></td>
			<td>共损理算师</td>
			<td colspan="2"></td>
		</tr>
		<tr>
			<td>救助人</td>
			<td colspan="5"></td>
		</tr>
		<tr>
			<td>担保人(如果有)</td>
			<td colspan="2">&nbsp;</td>
			<td>担保金额</td>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td rowspan="4">明确相关信息</td>
			<td>收货人</td>
			<td colspan="4">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="5">出险原因起运日期卸货日期</td>
		</tr>
		<tr>
			<td colspan="2">车次/航次/航班/船名/车号</td>
			<td colspan="3"></td>
		</tr>
		<tr>
			<td>承运人</td>
			<td colspan="2"></td>
			<td>提货日期</td>
			<td colspan="2"></td>
		</tr>
		<tr>
			<td>估损金额</td>
			<td colspan="5"></td>
		</tr>
		<tr>
			<td>约定查勘日期</td>
			<td colspan="2"></td>
			<td>实际查勘日</td>
			<td colspan="2"></td>
		</tr>
		<tr>
			<td colspan="6" height="150pix" valign="top">损失情况出险经过:</td>
		</tr>
		<tr>
			<td colspan="6" height="90pix" valign="top">理赔联系记录:</td>
		</tr>
	</table>
	<!--include打印按钮-->
	<jsp:include page="/common/print/PrintButton.jsp" />
</body>
</html>
