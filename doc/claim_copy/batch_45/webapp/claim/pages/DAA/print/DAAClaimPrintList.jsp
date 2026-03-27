<%--
****************************************************************************
* DESC       ：工作流查询报案信息结果页面
* AUTHOR     ：weishixin
* CREATEDATE ：2004-03-01
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%@ page import="com.sinosoft.claim.dto.custom.*"%>
<%@ page import="com.sinosoft.claim.dto.domain.*"%>
<%
	String printType = request.getParameter("printType");
	String strPrintType = printType + "Print";
	//System.out.println("--------------printType======="+printType);//赔案号
%>
<html:html locale="true">
<head>
<app:css />
<STYLE>
BODY {
	SCROLLBAR-FACE-COLOR: #EFFAFF;
	SCROLLBAR-HIGHLIGHT-COLOR: #4D9AC4;
	SCROLLBAR-SHADOW-COLOR: #4D9AC4;
	SCROLLBAR-3DLIGHT-COLOR: #EFFAFF;
	SCROLLBAR-ARROW-COLOR: #EFFAFF;
	SCROLLBAR-TRACK-COLOR: #EFFAFF;
	SCROLLBAR-DARKSHADOW-COLOR: #EFFAFF;
}
</STYLE>
<title>定损列印查询结果</title>
<script src="/claim/common/js/showpage.js"></script>
<script language='javascript'>
          function submitForm(printType, registNo, claimNo, serialNo, strLicenseNo)
          {
          	var strUrl = "";
          	strUrl = "/claim/ClaimPrint.do?printType=" + printType + "&RegistNo=" + registNo + "&ClaimNo=" + claimNo + "&lossItemCode=" + serialNo + "&strLicenseNo=" + strLicenseNo;

          	printWindow(strUrl, "列印1");
          }
          //显示打印窗口
          function printWindow(strURL, strWindowName) {
          	var pageWidth = screen.availWidth - 10;
          	var pageHeight = screen.availHeight - 30;
          	if (pageWidth < 100)
          		pageWidth = 100;

          	if (pageHeight < 100)
          		pageHeight = 100;
          	//add print liudaoping 2013-04-15
          	//alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
          	return false;
          	var newWindow = window.open(strURL, strWindowName, 'width=' + pageWidth + ',height=' + pageHeight + ',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
          	newWindow.focus();
          	return newWindow;
          }
          </script>
<html:base />
</head>
<body class="interface" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<form name="fm" method="post" onsubmit="return validateForm(this);">
		<input type="hidden" name="pageFlag">
		<table border="0" align="center" cellpadding="0" cellspacing="0" class="common">
			<tr>
				<td width="184" height="26" valign="bottom">
					<table width="184" height="19" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="161" class="formtitle">定损项目结果信息</td>
						</tr>
					</table>
				</td>
				<td valign="bottom">
					<font color="#666666">&nbsp;
				</td>
			</tr>
		</table>
		<table bgcolor="#2D8EE1" class="common" cellpadding="4" cellspacing="1">
			<tr>
				<td class="centertitle">序号</td>
				<td class="centertitle">报案号</td>
				<td class="centertitle">车牌号码</td>
				<td class="centertitle">车辆类型</td>
				<td class="centertitle">操作</td>
			</tr>
			<%
        int index = 0;
     %>
			<logic:notEmpty name="certainLossDto" property="prpLthirdPartyDtoList">
				<logic:iterate id="prpLthirdparty" name="certainLossDto" property="prpLthirdPartyDtoList">
					<tr>
						<%
		if (index % 2 == 0)
			out.print("<tr class=listodd>");
		else
			out.print("<tr class=listeven>");
	%>
						<td align="center">
							<bean:write name="prpLthirdparty" property="serialNo" />
						</td>
						<td align="center">
							<bean:write name="prpLthirdparty" property="registNo" />
						</td>
						<td align="center">
							<bean:write name="prpLthirdparty" property="licenseNo" />
						</td>
						<logic:equal name="prpLthirdparty" property="serialNo" value="1">
							<td align="center">标的车</td>
						</logic:equal>
						<logic:notEqual name="prpLthirdparty" property="serialNo" value="1">
							<td align="center">三者车</td>
						</logic:notEqual>
						<td align="center">
							<input type=button value="列印" class='button'
								onclick="submitForm('<%=strPrintType%>','<bean:write name="prpLthirdparty" property="registNo"/>','<bean:write name="prpLthirdparty" property="claimNo"/>','<bean:write name="prpLthirdparty" property="serialNo"/>' ,'<bean:write name="prpLthirdparty" property="licenseNo"/>');">
						</td>
					</tr>
					<%index++;%>
				</logic:iterate>
			</logic:notEmpty>
		</table>
		<script src="/claim/common/StaticJavascript.jsp"> </script>
	</form>
</body>
</html:html>
