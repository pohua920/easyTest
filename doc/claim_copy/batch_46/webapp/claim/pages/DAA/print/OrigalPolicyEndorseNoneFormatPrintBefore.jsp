<%--
****************************************************************************
* DESC       ：出险时保单列印页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-11-18
* MODIFYLIST ：Name       Date            Reason/Contents
*            ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK" %>

<%-- 引入bean类部分 --%>
<%@page import="java.text.*"%>
<%@page import="com.sinosoft.claim.ui.control.action.*"%>
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="com.sinosoft.claim.dto.domain.*"%> 
<%@page import="com.sinosoft.claim.util.*"%> 
<%@page import="com.sinosoft.sysframework.common.util.*"%> 
<%@page import="com.sinosoft.sysframework.common.datatype.DateTime"%> 
<%@page import="com.sinosoft.sysframework.exceptionlog.*"%>
<%@page import="com.sinosoft.claim.util.*"%>

<%
	//变量声明部分
	String strPolicyNo = request.getParameter("PolicyNo"); //保单号  
	String strEndDate = request.getParameter("EndDate");
	String strRiskCode = ""; //险种
	String strWhere = ""; //sql语句
	String strRiskPage = "";
	int i = 0;

	//得到保单对象的信息
	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");

	//根据保单数据分发到列印页面
	/*
	 if(strRiskCode.equals(BusinessRuleUtil.getOuterCode(request,"RISKCODE_DAA")))
	 {
	 strRiskPage = "/" + strRiskCode + "/cb/UIPolicy" + strRiskCode + "SingleCarNoneFormatPrint.jsp"  ;  //设置编辑页面
	 }
	 else 
	 {
	 strRiskPage = "/" + strRiskCode + "/cb/UIPolicy" + strRiskCode + "NoneFormatPrint.jsp"  ;
	 }  
	 */
%>
<html>
<head>
<title>理赔列印前输入单证号</title>
<%-- 公用函数 --%>
<script src="/ddccallweb/commonship/print/Common.js"></script>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script language="javascript">
function printOrigalPolicy() {
	//原始保单
	var strUrl = "";
	strUrl = "/claim/ClaimPrint.do?printType=HistoryPolicy&PolicyNo=<%=strPolicyNo%>&EDITTYPE=COPY&PrintType=O";
	printWindow(strUrl, "列印1");
}

function printEndorse() {
	//原始批单
	var strUrl = "";
	strUrl = "/claim/ClaimPrint.do?printType=HistoryEndorse&PolicyNo=<%=strPolicyNo%>&EDITTYPE=COPY";
	printWindow(strUrl, "列印1");
}

//显示列印窗口

function printWindow(strURL, strWindowName) {
	//alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
	return false;
	var pageWidth = screen.availWidth - 10;
	var pageHeight = screen.availHeight - 30;
	if (pageWidth < 100)
		pageWidth = 100;

	if (pageHeight < 100)
		pageHeight = 100;

	var newWindow = window.open(strURL, strWindowName, 'width=' + pageWidth + ',height=' + pageHeight + ',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
	newWindow.focus();
	return newWindow;
}          
</script>
</head>
<form name="fm">
	<table width="80%" height="164" align="center" cellpadding="0" cellspacing="0" class="common">
		<tr>
			<td valign="bottom" " height="92" colspan="4" class=common align="center" style="font-family: 宋体; font-size: 10pt;">原始保单及批单</td>
		</tr>
		<input type='hidden' name='EndDate' value="<%=request.getParameter("EndDate")%>">
		<input type='hidden' name='EndDateHour' value="<%=request.getParameter("EndDateHour")%>">
		<tr>
			<td height="70" colspan="2" align="center" class=common>
				<input type=button name=buttonPrintOrigalPolicy value="原始保单" class="bigbutton" onClick="printOrigalPolicy()">
			</td>
			<td colspan="2" align="center" class=common>
				<input type=button name=buttonPrintEndorse value="以往批单" class="bigbutton" onClick="printEndorse()">
			</td>
		</tr>
	</table>
</form>
</html>
