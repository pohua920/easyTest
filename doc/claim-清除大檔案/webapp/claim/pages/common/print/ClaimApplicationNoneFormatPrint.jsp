<%--
****************************************************************************
* DESC       ：非车险理赔申请书打印页面
* AUTHOR     ：罗畅
* CREATEDATE ：2010-05-27
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%-- 初始化 --%>
<%@include file="ClaimApplicationNoneFormatPrintIni.jsp"%>
<html>
<head>
<title><%=strRiskName%>理赔申请书列印</title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>
<body bgcolor="#FFFFFF">
	<form name="fm">
		<!-- 标题部分 -->
		<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
			<tr>
				<td colspan="3" height="50px" align=left>
					<img src="/claim/images/copyprintlogo.jpg" height="45px" />
				</td>
				<td colspan="3" height="50px" align=right style="font-family: 宋体; font-size: 9pt;">
					<br>公司地址：<%=strComAddress%>
					<br>服务电话：<%=strComPhoneNumber%>；传真：<%=strFaxNumber%>
				</td>
			</tr>
			<tr height="3px">
				<td colspan="6" height="3px">
					<hr />
				</td>
			</tr>
		</table>
		<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
			<tr>
				<td colspan="3" height="40" align=center style="font-family: 宋体; font-size: 18pt;">
					<B><%=strRiskName%>理赔申请书<B>
				</td>
			</tr>
		</table>
		<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
			<tr>
				<td colspan="3" height="10" align=right style="font-family: 宋体; font-size: 10pt;">
					赔案号：<%=strClaimNo%>
				</td>
			</tr>
		</table>
		<!-- 主体部分 -->
		<table border=1 width="92%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:12pt;">
			<tr align="left">
				<td rowspan=3 colspan=2 width="3%">
					被<br>保<br>险<br>人
				</td>
				<td width="5%" colspan=1 height="25">名称</td>
				<td colspan="3" width="37%"><%=strInsuredName%></td>
				<td width="8%" colspan=3>保险单号</td>
				<td colspan="3" width="37%"><%=strPolicyNo%></td>
			</tr>
			<tr align="left">
				<td width="5%" colspan=1 height="25">地址</td>
				<td colspan="3"><%=strInsuredAddress%></td>
				<td width="8%" colspan=3>险&nbsp;&nbsp;&nbsp;&nbsp;别</td>
				<td colspan="3"><%=strRiskName%></td>
			</tr>
			<tr align="left">
				<td width="5%" height="25" colspan=1>电话</td>
				<td colspan="3"><%=strInsuredPhoneNumber%></td>
				<td width="8%" colspan=3>保险期间</td>
				<td colspan="3"><%=strInsuredDate%></td>
			</tr>
			<%
				//货运险特殊控制
				if (RiskCode.substring(0, 2).equals("10") || RiskCode.substring(0, 2).equals("09")) {
			%>
			<tr align="left">
				<td rowspan=3 colspan=2 width="3%">
					联<br>系<br>人
				</td>
				<td width="5%" height="25" colspan=1>姓名</td>
				<td colspan="3"><%=strLinkerName%></td>
				<td width="8%" colspan=3>OP单号</td>
				<td colspan="3"><%=strOPcode%></td>
			</tr>
			<tr align="left">
				<td width="5%" height="25" colspan=1>地址</td>
				<td colspan="3"><%=strLinkerAddress%></td>
				<td width="8%" rowspan=2 colspan=3>运输路线</td>
				<td colspan="3" id="">
					自&nbsp;<%=strStartSiteName%></td>
			</tr>
			<tr align="left">
				<td width="5%" height="25" colspan=1>电话</td>
				<td colspan="3"><%=strLinkerPhoneNumber%></td>
				<td colspan="3">
					至&nbsp;<%=strEndSiteName%></td>
			</tr>
			<tr align="left">
				<td rowspan=3 colspan=2 width="5%">
					出<br>险<br>信<br>息
				</td>
				<td width="5%" height="25" colspan=1>时间</td>
				<td colspan="3"><%=strDamageStartDate%></td>
				<td width="8%" colspan=3>运输工具</td>
				<td colspan="3"><%=strBlno%></td>
			</tr>
			<tr align="left">
				<td width="5%" height="25" colspan=1>地点</td>
				<td colspan="3"><%=strDamageAddress%></td>
				<td width="8%" colspan=3>提单/运单/车次</td>
				<td colspan="3"><%=strInvoiceNo%></td>
			</tr>
			<tr align="left">
				<td width="5%" height="25" colspan=1>原因</td>
				<td colspan="3"><%=strDamageName%></td>
				<td width="8%" colspan=3>货物名称</td>
				<td colspan="3"><%=strItemDetailName%></td>
			</tr>
			<%
				} else {
			%>
			<tr align="left">
				<td rowspan=3 colspan=2 width="3%">
					联<br>系<br>人
				</td>
				<td width="5%" height="25" colspan=1>姓名</td>
				<td colspan="3"><%=strLinkerName%></td>
				<td width="8%" colspan=3>出险时间</td>
				<td colspan="3"><%=strDamageStartDate%></td>
			</tr>
			<tr align="left">
				<td width="5%" height="25" colspan=1>地址</td>
				<td colspan="3"><%=strLinkerAddress%></td>
				<td width="8%" colspan=3>出险原因</td>
				<td colspan="3" id=""><%=strDamageName%></td>
			</tr>
			<tr align="left">
				<td width="5%" height="25" colspan=1>电话</td>
				<td colspan="3"><%=strLinkerPhoneNumber%></td>
				<td width="12%" colspan=3>报损金额</td>
				<td colspan="3"><%=strEstimateLoss%></td>
			</tr>
			<tr align="left">
				<td width="11%" height="25" colspan=3>出险地点</td>
				<td colspan="9"><%=strDamageAddress%></td>
			</tr>
			<%
				}
			%>
		</table>
		<table <%//货运险特殊控制
			if (RiskCode.substring(0, 2).equals("10") || RiskCode.substring(0, 2).equals("09")) {%> height="400" <%} else {%> height="460" <%}%> border=2
			width="92%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#111111">
			<tr align="center">
				<td colspan="12" id="tdContext" valign="top" align="left" style="font-family: 宋体; font-size: 10pt;">
					<b>&nbsp;事故发生经过（简单描述事故出险、施救过程）：</b>
				</td>
			</tr>
			<%
				//家财险、意健险、责任险特殊控制
				if (RiskCode.substring(0, 2).equals("03") || RiskCode.substring(0, 2).equals("23") || RiskCode.substring(0, 2).equals("27") || RiskCode.substring(0, 2).equals("15")) {
			%>
			<tr align="center">
				<td rowspan=4 colspan=1 width="3%">
					伤<br>亡<br>资<br>料
				</td>
				<td width="3%" height="25" colspan=1></td>
				<td colspan="3" width="15%">姓名</td>
				<td colspan="2" width="8%">姓别</td>
				<td colspan="3" width="20%">身份证号</td>
				<td colspan="2" width="15%">受伤情形</td>
			</tr>
			<tr align="center">
				<td width="3%" height="25" colspan=1>1</td>
				<td colspan="3" width="15%"></td>
				<td colspan="2" width="8%">□男&nbsp;&nbsp;□女</td>
				<td colspan="3" width="20%"></td>
				<td colspan="2" width="15%">□死亡&nbsp;&nbsp;□残废&nbsp;&nbsp;□医疗</td>
			</tr>
			<tr align="center">
				<td width="3%" height="25" colspan=1>2</td>
				<td colspan="3" width="15%"></td>
				<td colspan="2" width="8%">□男&nbsp;&nbsp;□女</td>
				<td colspan="3" width="20%"></td>
				<td colspan="2" width="15%">□死亡&nbsp;&nbsp;□残废&nbsp;&nbsp;□医疗</td>
			</tr>
			<tr align="center">
				<td width="3%" height="25" colspan=1>3</td>
				<td colspan="3" width="15%"></td>
				<td colspan="2" width="8%">□男&nbsp;&nbsp;□女</td>
				<td colspan="3" width="20%"></td>
				<td colspan="2" width="15%">□死亡&nbsp;&nbsp;□残废&nbsp;&nbsp;□医疗</td>
			</tr>
			<%
				}
			%>
			<tr align="center">
				<td height="110" colspan="12" id="tdContext" valign="top" align="left" style="line-height: 20px">
					<%
						//意健险特殊控制
						if (RiskCode.substring(0, 2).equals("27")) {
					%>
					&nbsp;被保险人声明事项： <br>&nbsp;&nbsp;1.是否有投保其它社会或商业保险？□否&nbsp;&nbsp;□是，请说明：_________________________________________________； <br>&nbsp;&nbsp;2.报案人/申请人与被保险人的关系是：__________________________________________________________。<br>
					<%
						}
					%>
					<b>&nbsp;兹声明本申请书所填内容均属实情，否则自愿放弃保单所有之一切权利。</b>
					<pre>	被保险人				申请人</pre>
					<pre>	签&nbsp&nbsp&nbsp&nbsp章				签&nbsp&nbsp章</pre>
					<p align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
				</td>
			</tr>
		</table>
		<br />
		<table border=1 width="92%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#111111">
			<tr align="left">
				<td colspan=6>&nbsp;&nbsp;报案日期：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日</td>
				<td colspan=6>&nbsp;&nbsp;保费注记：□已收足&nbsp;&nbsp;&nbsp;&nbsp;□未收足__________________</td>
			</tr>
			<%
				//财产险特殊控制
				if (RiskCode.substring(0, 2).equals("01") || RiskCode.substring(0, 2).equals("04")) {
			%>
			<tr align="left">
				<td colspan=6>&nbsp;&nbsp;申请类别：□企财&nbsp;&nbsp;□营业中断&nbsp;&nbsp;□商综</td>
				<td colspan=6>&nbsp;&nbsp;申请事项：□标的物损失&nbsp;&nbsp;□三者财损&nbsp;&nbsp;□体伤&nbsp;&nbsp;□其它</td>
			</tr>
			<%
				}
			%>
			<%
				//建筑工程险特殊控制
				if (RiskCode.substring(0, 2).equals("07")) {
			%>
			<tr align="left">
				<td colspan=6>&nbsp;&nbsp;申请类别：□建筑&nbsp;&nbsp;□安装</td>
				<td colspan=6>&nbsp;&nbsp;申请事项：□标的物损失&nbsp;&nbsp;□三者财损&nbsp;&nbsp;□体伤&nbsp;&nbsp;□其它</td>
			</tr>
			<%
				}
			%>
			<%
				//家财险特殊控制
				if (RiskCode.substring(0, 2).equals("03") || RiskCode.substring(0, 2).equals("23")) {
			%>
			<tr align="left">
				<td colspan=6>&nbsp;&nbsp;预估损失：</td>
				<td colspan=6>&nbsp;&nbsp;申请事项：□标的物损失&nbsp;&nbsp;□窃盗&nbsp;&nbsp;□意外伤害/骨折&nbsp;&nbsp;□责任&nbsp;&nbsp;□其它</td>
			</tr>
			<%
				}
			%>
			<%
				//意健险特殊控制
				if (RiskCode.substring(0, 2).equals("27")) {
			%>
			<tr align="left">
				<td colspan=6>&nbsp;&nbsp;申请类别：□个人伤害&nbsp;&nbsp;□团体伤害&nbsp;&nbsp;□TP</td>
				<td colspan=6>&nbsp;&nbsp;申请事项：□意外伤害&nbsp;&nbsp;□医疗&nbsp;&nbsp;□住院&nbsp;&nbsp;□骨折&nbsp;&nbsp;□其它__________</td>
			</tr>
			<%
				}
			%>
			<%
				//货运险特殊控制
				if (RiskCode.substring(0, 2).equals("10") || RiskCode.substring(0, 2).equals("09")) {
			%>
			<tr align="left">
				<td colspan=6 width="45%">&nbsp;&nbsp;案件类型：□出口&nbsp;&nbsp;□进口&nbsp;&nbsp;□内陆运输&nbsp;&nbsp;□物流责任</td>
				<td colspan=3 width="20%">&nbsp;&nbsp;□单损&nbsp;&nbsp;□共损&nbsp;&nbsp;□救助</td>
				<td colspan=3 width="35%">&nbsp;&nbsp;担保：□需要_____________&nbsp;&nbsp;□不需要</td>
			</tr>
			<%
				}
			%>
			<%
				//责任险特殊控制
				if (RiskCode.substring(0, 2).equals("15")) {
				}
			%>
			<tr>
				<td colspan="12" height="150" align="left" valign="top">
					<table width="100%" height="50%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr height="40">
							<td width="33%" height="150" align="left" valign="top">&nbsp;&nbsp;备注：</td>
						</tr>
						<tr height="20">
							<td width="33%" height="2" align="left"></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<table width="92%" height="20" align="center" cellspacing="0" cellpadding="0" border="0">
			<tr>
				<td colspan="3" height="15" align=right style="font-family: 宋体; font-size: 10pt;">
					<pre> 理赔经办：<%=strHandlerName%>	</pre>
				</td>
			</tr>
		</table>
	</form>
	<jsp:include page="/common/print/PrintButton.jsp" />
	<%-- <jsp:include page="/DAA/compensate/DAASpecialPrintButton.jsp" />--%>
</body>
</html>