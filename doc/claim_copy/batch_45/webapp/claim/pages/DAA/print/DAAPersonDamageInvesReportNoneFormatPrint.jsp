<%--
****************************************************************************
* DESC       ：机动车辆保险人身伤亡调查报告页面
* AUTHOR     ：caopeng
* CREATEDATE ：2005-12-07
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
<%@include file="DAAPersonDamageInvesReportNoneFormatPrintIni.jsp"%>
<html>
<head>
<title>机动车辆保险人身伤亡调查报告列印</title>
<style type="text/css">
INPUT.printline {
	FONT-SIZE: 10pt;
	BORDER-TOP: #ececec 0px solid;
	WIDTH: 10px;
	COLOR: #000000;
	BORDER-BOTTOM: #400000 1px solid;
	BORDER-RIGHT-STYLE: none;
	BORDER-LEFT-STYLE: none;
	BACKGROUND-COLOR: #ffffff text-align:center
}
</style>
</head>
<body bgcolor="#FFFFFF">
	<div align="center">
		<table width="92%" border="0" align="center" cellpadding="0" cellspacing="0" style="font-family: 宋体; font-size: 10pt;">
			<tr height=30>
				<td colspan="2" height="40" align="center" style="text-align: center; font-family: 宋体; font-size: 14pt;">
					<span><img src="/claim/images/LOGO.jpg" /></span>
				</td>
			</tr>
			<tr height=30>
				<td colspan="2" align=center style="font-family: 宋体; font-size: 14pt;">
					<B>
						<center>
							机动车辆保险人身伤亡调查报告
							<center>
								<B> </b>
							</center>
						</center>
					</b><br>
					<br>
				</td>
			</tr>
			<tr height=20>
				<td width="48%" height="25" align=left>
					报案号：
					<%=strRegistNo%></td>
				<td align=left width="52%">
					&nbsp;&nbsp;赔案号：
					<%=strClaimNo%></td>
			</tr>
			<tr height=20>
				<td height="25" colspan="2" align=left>
					<table width="100%" height="701" border="0" align="center" cellpadding="0" cellspacing="0" style="font-family: 宋体; font-size: 10pt;">
						<tr>
							<td height="25" colspan="2" align="left">
								被保险人名称：
								<input type=text name="prplCommission" readonly="true" class="printline" style="width: 100px; text-align: center" value="<%=strInsuredName%>">
							</td>
							<td colspan="2">
								<div align="left">
									保单号
									<input type=text name="prplCommission" readonly="true" class="printline" style="width: 150px; text-align: center" value="<%=strPolicyNo%>">
								</div>
							</td>
							<td colspan="2">
								<div align="left">
									标的车牌号码：
									<input type=text name="prplCommission" readonly="true" class="printline" style="width: 90px; text-align: center" value="<%=strLicenseNo%>">
								</div>
							</td>
						</tr>
						<tr>
							<td height="25" colspan="6">
								<div align="left">
									人身伤亡险别：&nbsp;&nbsp;&nbsp;&nbsp;
									<!--□第三者人身伤亡责任险&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□车上人员责任险&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□其他--><%=strReferKindName%></div>
							</td>
						</tr>
						<tr>
							<td height="25" colspan="2">
								<div align="left">
									出险时间：
									<ins>
										<input type=text name="prplCommission" readonly="true" class="printline" style="width: auto; text-align: center" value="<%=strdamageStartDate + "  " + strdamageStartHour%>">
									</ins>
								</div>
							</td>
							<td height="25" colspan="4">
								<div align="left">
									出险地点：
									<input type=text name="prplCommission" readonly="true" class="printline" style="width: 330px; text-align: center" value="<%=strDamageAddress%>">
								</div>
							</td>
						</tr>
						<tr>
							<td height="25" colspan="2">
								<div align="left">
									出险人员姓名：
									<input type=text name="prplCommission" readonly="true" class="printline" style="width: 100px; text-align: center" value="<%=strD1%>">
								</div>
							</td>
							<td width="96" height="25">
								<div align="left">
									性别：
									<input type=text name="prplCommission" readonly="true" class="printline" style="width: 40px; text-align: center" value="<%=strSexName%>">
								</div>
							</td>
							<td width="108">
								年龄：
								<input type=text readonly="true" class="printline" style="width: 40px; text-align: center" value="<%=strD3%>">
								岁
							</td>
							<td height="25" colspan="2">
								<div align="left">
									&nbsp;职业：
									<input name="text" type=text class="printline" style="width: 130px; text-align: center" value="<%=strD2%>" readonly="true">
								</div>
							</td>
						</tr>
						<tr valign="top">
							<td height="205" colspan="6">
								<div align="left">
									<p>出险原因、经过及结果：</p>
									&nbsp;&nbsp;&nbsp;&nbsp;
									<%=tempContext%>
								</div>
							</td>
						</tr>
						<tr valign="top">
							<td height="109" colspan="6">
								<div align="left">
									<p>伤亡程度：</p>
									&nbsp;&nbsp;&nbsp;&nbsp;<%=strWoundRemark%></div>
							</td>
						</tr>
						<tr valign="top">
							<td height="237" colspan="6">
								<div align="left">
									<p>情况说明（包括事故性质、施救情况、就医单位级别等）：</p>
									<p>
										&nbsp;&nbsp;&nbsp;&nbsp;事故类型:&nbsp;&nbsp;<%=strDamageTypeName%></p>
									<p>
										&nbsp;&nbsp;&nbsp;&nbsp;施救医院:&nbsp;&nbsp;<%=strHospital%></p>
								</div>
							</td>
						</tr>
						<tr>
							<td height="25" colspan="2">
								<div align="left">
									被询问人：
									<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>
								</div>
							</td>
							<td height="25">&nbsp;</td>
							<td height="25">&nbsp;</td>
							<td width="171" height="25">
								<div align="right">
									询问人：
									<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>
								</div>
							</td>
							<td width="50">
								<div align="left"></div>
							</td>
						</tr>
						<tr>
							<td width="125" height="25">
								<div align="right">&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;月&nbsp;&nbsp;日</div>
							</td>
							<td width="96" height="25">&nbsp;</td>
							<td height="25">&nbsp;</td>
							<td height="25">&nbsp;</td>
							<td height="25">
								<div align="center">&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;月&nbsp;&nbsp;日</div>
							</td>
							<td width="50" height="25">
								<div align="center"></div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
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
