<%--
****************************************************************************
* DESC       ：车险理赔申请书打印页面
* AUTHOR     ：罗畅
* CREATEDATE ：2010-07-26
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
<%@include file="DAAClaimApplyNoneFormatPrintIni.jsp"%>
<html>
<head>
<title>机动车辆保险理赔申请书列印</title>
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
					<br>公司电话：<%=strComPhoneNumber%>；传真：<%=strFaxNumber%></td>
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
					<B>机动车辆保险理赔申请书<B>
				</td>
			</tr>
		</table>
		<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
			<tr>
				<td colspan="3" height="10" align=right style="font-family: 宋体; font-size: 9pt;">
					交强险赔案号：<%=strCompClaimNo%>
				</td>
			</tr>
			<tr>
				<td colspan="3" height="10" align=right style="font-family: 宋体; font-size: 9pt;">
					商业险赔案号：<%=strClaimNo%>
				</td>
			</tr>
		</table>
		<!-- 主体部分 -->
		<table border=1 width="92%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:12pt;">
			<tr align="left">
				<td colspan=3 width="3%">号牌号码</td>
				<td colspan="3" width="37%"><%=strLicenseNo%></td>
				<td width="8%" colspan=3>厂牌型号</td>
				<td colspan="3" width="37%"><%=strBrandName%></td>
			</tr>
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
			<tr align="left">
				<td rowspan=3 colspan=2 width="3%">
					驾<br>驶<br>员
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
				<td width="12%" colspan=3>出险类型</td>
				<td colspan="3"><%=strDamageTypeName%></td>
			</tr>
			<tr align="left">
				<td width="11%" height="25" colspan=3>驾驶证号</td>
				<td colspan="3"></td>
				<td width="12%" colspan=3>出险地点</td>
				<td colspan="3"><%=strDamageAddress%></td>
			</tr>
		</table>
		<table height="510" border=2 width="92%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#111111">
			<tr align="center">
				<td colspan="12" id="tdContext" valign="top" align="left" style="font-family: 宋体; font-size: 10pt;">
					<b>&nbsp;事故发生经过（简单描述出险经过、事故现场情形）：</b> <br /> <br /> <br /> <br /> <br /> <br /> <br /> <br /> <br /> <br /> <br /> <br /> <br /> <br />
					<table border=0 width="100%">
						<tr align="right">
							<td width="75%"></td>
							<td>事故现场绘图&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr align="center">
				<td height="80" colspan="10" id="tdContext" valign="top" align="left" style="font-family: 宋体; font-size: 10pt;">
					<pre>	三者车号牌	驾驶员姓名		联系电话</pre>
				</td>
				<td height="160" width="25%" rowspan="2" colspan="2" id="tdContext" valign="top" align="left" style="font-family: 宋体; font-size: 12pt;">
					<table height="100%" width="100%" border=1 align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#CCCCCC">
						<%
							for (int tr = 0; tr < 4; tr++) {
						%>
						<tr height="25%">
							<%
								for (int td = 0; td < 4; td++) {
							%>
							<td width="25%" style="border-style: dotted"></td>
							<%
								}
							%>
						</tr>
						<%
							}
						%>
					</table>
				</td>
			</tr>
			<tr align="center">
				<td height="80" colspan="10" id="tdContext" valign="top" align="left" style="font-family: 宋体; font-size: 10pt;">
					<pre>	三者车号牌	驾驶员姓名		联系电话</pre>
				</td>
			</tr>
			<tr align="center">
				<td height="110" colspan="12" id="tdContext" valign="top" align="left" style="font-family: 宋体; font-size: 10pt; line-height: 20px">
					<b>&nbsp;兹声明本申请书所填内容均属实情，否则自愿放弃保单所有之一切权利。</b>
					<pre>	被保险人				申请人</pre>
					<pre>	签&nbsp&nbsp&nbsp&nbsp章				签&nbsp&nbsp章</pre>
					<p align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
				</td>
			</tr>
		</table>
		<br />
		<table border=1 width="92%" align="center" cellspacing="0" cellpadding="2" style="font-family: 宋体; font-size: 12pt; border-collapse: collapse" bordercolor="#111111">
			<tr align="left">
				<td colspan=6>&nbsp;&nbsp;报案日期：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日</td>
				<td colspan=6>&nbsp;&nbsp;免赔额/免赔率：</td>
			</tr>
			<tr align="left">
				<td colspan=6>&nbsp;&nbsp;驾驶资格确认：&nbsp;&nbsp;□行驶证&nbsp;&nbsp;□驾驶证</td>
				<td colspan=6>&nbsp;&nbsp;申请类别：&nbsp;&nbsp;□标的车损&nbsp;&nbsp;□三者车、财损&nbsp;&nbsp;□体伤&nbsp;&nbsp;□窃盗</td>
			</tr>
			<tr>
				<td colspan="12" height="120" align="left" valign="top">
					<table width="100%" height="50%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 12pt;">
						<tr height="40">
							<td width="33%" height="120" align="left" valign="top">&nbsp;&nbsp;备注：</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>
	<jsp:include page="/common/print/PrintButton.jsp" />
</body>
</html>