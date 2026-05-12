
<%--
****************************************************************************
* DESC       ： 货运险列印前输入业务号页面
* AUTHOR     ： wangwei
* CREATEDATE ： 2005-05-21
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app" %>
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="com.sinosoft.sysframework.common.datatype.DateTime;"%>

<%
	String strPrintType = request.getParameter("printType");
	String strTitleName = "";
	String strBizName = "";

	String strWherePart = "";
	int intCount = 0;
	String strMessage = "";

	//System.out.println("UILprintBeforInput.jsp"+strPrintType+"00000000000000000000000");
	if (strPrintType.equals("FreightCompensate")) {

		//1.货运险赔款计算书
		strTitleName = "赔款计算书列印";
		strBizName = "赔款计算书号";
	}

	if (strPrintType.equals("FreightCompensateNotice")) {

		//2.货运险赔案终结报告书
		strTitleName = "货运险赔案终结报告书列印";
		strBizName = "赔案号";
	}

	if (strPrintType.equals("FreightDamageNotice")) {

		//3.货运险理赔信息摘要列印
		strTitleName = "货运险理赔信息摘要列印";
		strBizName = "报案号";
	}

	if (strPrintType.equals("freightCheckReport")) {

		//4.货运险查勘报告
		strTitleName = "货运险检验报告列印";
		strBizName = "报案号";
	}

	if (strPrintType.equals("ClaimDispose")) {
		//5.理赔处理报告
		strTitleName = "理赔处理报告列印";
		strBizName = "赔案号";
	}

	if (strPrintType.equals("LocalCheck")) {
		//6.现场查堪报告
		strTitleName = "现场查堪报告列印";
		strBizName = "报案号";
	}

	if (strPrintType.equals("CopyPrint")) {
		//7.抄单列印
		strTitleName = "承保理赔信息列印";
		strBizName = "报案号";
	}
	if (strPrintType.equals("CopyPrintNew")) {
		//7.抄单列印
		strTitleName = "新承保理赔信息列印";
		strBizName = "报案号";
	}

	if (strPrintType.equals("AcciCheck")) {
		//7.意健险调查报告
		strTitleName = "意健险调查报告";
		strBizName = "报案号";
	}
	if (strPrintType.equals("PropCancelNotice")) {
		//8.财产险拒赔通知书
		strTitleName = "财产险拒赔通知书列印";
		strBizName = "赔案号";
	}
	if (strPrintType.equals("PropCompensate")) {
		//10.财产险赔款计算书
		strTitleName = "财产险赔款计算书列印";
		strBizName = "赔款计算书号";
	}
	if (strPrintType.equals("PropCompensateNotice")) {
		//11.财产险赔款通知书
		strTitleName = "财产险赔款通知书";
		strBizName = "赔款计算书号";
	}
	if (strPrintType.equals("ShipEndcase")) {
		//12.船货险结案报告列印
		strTitleName = "船货险结案报告列印";
		strBizName = "赔案号";
	}
	if (strPrintType.equals("AcciNotClaim")) {
		//13.意健险不予立案通知书列印
		strTitleName = "意健险不予立案通知书列印";
		strBizName = "报案号";
	}
	if (strPrintType.equals("AcciReview")) {
		//14.意健险二核批单列印
		strTitleName = "意健险二核批单列印";
		strBizName = "赔款计算书号";
	}
	if (strPrintType.equals("AcciCancelNotice")) {
		//15.意健险拒赔通知书列印
		strTitleName = "意健险拒赔通知书列印";
		strBizName = "赔案号";
	}
	if (strPrintType.equals("ShipCopyPrint")) {
		//货船险抄单列印
		strTitleName = "货船险抄单列印";
		strBizName = "报案号";
	}
	if (strPrintType.equals("ReparationsList")) { //意健险理赔批单列印
		strTitleName = "意健险理赔批单列印";
		strBizName = "赔款计算书号";
	}
	if (strPrintType.equals("FreightHeresyCheck")) {
		strTitleName = "货运险代查勘委托书列印";
		strBizName = "报案号";
	}
	if (strPrintType.equals("FreightNationalCompensate")) {
		strTitleName = "国内货物运输保险赔款理算书";
		strBizName = "赔款计算书号";
	}
	if (strPrintType.equals("FreightInportCompensate")) {
		strTitleName = "进口货物运输保险赔款理算书";
		strBizName = "赔款计算书号";
	}
	if (strPrintType.equals("FreightExportCompensate")) {
		strTitleName = "出口货物运输保险赔款理算书";
		strBizName = "赔款计算书号";
	}
	if (strPrintType.equals("FreightRefuseCancel")) {
		strTitleName = "拒赔/註銷案件通知书";
		strBizName = "赔案号";
	}
	if (strPrintType.equals("PropLocalCheck")) {
		strTitleName = "财产险现场查勘报告";
		strBizName = "报案号";
	}
	if (strPrintType.equals("LiabCompensate")) {
		strTitleName = "责任险赔款理算报告";
		strBizName = "赔款计算书号";
	}

	if (strPrintType.equals("LiabLocaleCheck")) {
		strTitleName = "责任险现场查勘报告";
		strBizName = "报案号";
	}
	if (strPrintType.equals("PropLocaleHeresy")) {
		strTitleName = "非水险代查勘委托书";
		strBizName = "报案号";
	}

	if (strPrintType.equals("FreightClaimApply")) {
		strTitleName = "货运险索赔申请书";
		strBizName = "报案号";
	}

	if (strPrintType.equals("ClaimDocumentHandinCredence"))//人身险索赔文件交接凭证
	{
		strTitleName = "人身险索赔文件交接凭证列印";
		strBizName = "赔案号";
	}
	if (strPrintType.equals("InvesReport")) {
		//意健险理赔调查报告
		strTitleName = "意健险理赔调查报告列印";
		strBizName = "调查号";
	}
	if (strPrintType.equals("CompensateAuditBook")) {
		//理赔审核书
		strTitleName = "理赔审核书列印";
		strBizName = "赔款计算书号";
	}
	if (strPrintType.equals("FreightCoinsType")) {
		//联共保计算书
		strTitleName = "联共保计算书列印";
		strBizName = "赔款计算书号";
	}

	if (strPrintType.equals("interestsTransferReport")) {
		//非水权益转让书
		strTitleName = "非水权益转让书";
		strBizName = "立案号";
	}
	if (strPrintType.equals("mairineRightsTransferReport")) {
		//水险权益转让书
		strTitleName = "水险权益转让书";
		strBizName = "立案号";
	}
	if (strPrintType.equals("noMarineHeresyCheck")) {
		//非水代查勘委托书
		strTitleName = "非水代查勘委托书";
		strBizName = "报案号";
	}
	if (strPrintType.equals("claimApplication")) {
		//理赔申请书
		strTitleName = "理赔申请书";
		strBizName = "赔案号";

	}
%>
<html:html locale="true">
<head>
<title>理赔列印前输入单证号</title>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script src="/claim/DAA/print/js/DAAPrintBeforeEdit.js"></script>
<script language='javascript'>
   function loadForm() {
		TitleName.innerHTML = '<%=strTitleName%>';
		fm.PrintType.value = '<%=strPrintType%>';
		BizName.innerHTML = '<%=strBizName%>' + '：';
		fm.BizName.value = '<%=strBizName%>';
	}


	function checkForm() {
		if (fm.BizNo.length < 1) {
			fm.BizNo.focus();
			errorMessage(fm.BizName.value + "不能为空!");
			return false;
		}
		//modify by zhuly 20051110
		//else if(trim(fm.BizNo.value).length!=21 && trim(fm.BizNo.value).length !=25)
		//{
		//  fm.BizNo.focus();
		//  errorMessage(fm.BizName.value + "应为21位或25位长!");
		//  return false;
		//}

		return true;
	}


	function submitForm() {

		if (checkForm() == true) {
			var strUrl = "";

			if (fm.PrintType.value == "FreightCompensate") //1.货运险赔款计算书
			{
				strUrl = "/claim/ClaimPrint.do?printType=" + fm.PrintType.value + "&CompensateNo=" + fm.BizNo.value;
			}

			if (fm.PrintType.value == "FreightCompensateNotice") //2.货运险赔案终结报告书
			{
				strUrl = "/claim/ClaimPrint.do?printType=" + fm.PrintType.value + "&CompensateNo=" + fm.BizNo.value + "&actionType=" + fm.PrintType.value;
			}

			if (fm.PrintType.value == "FreightDamageNotice") //3.货运险出险通知书
			{
				strUrl = "/claim/ClaimPrint.do?printType=" + fm.PrintType.value + "&CompensateNo=" + fm.BizNo.value;
			}

			if (fm.PrintType.value == "freightCheckReport") //4.货运险查勘报告
			{
				strUrl = "/claim/ClaimPrint.do?printType=" + fm.PrintType.value + "&RegistNo=" + fm.BizNo.value + "&actionType=" + fm.PrintType.value;
			}

			if (fm.PrintType.value == "ClaimDispose") //5.理赔处理报告
			{
				strUrl = "/claim/ClaimPrint.do?printType=" + fm.PrintType.value + "&CompensateNo=" + fm.BizNo.value;
			}

			if (fm.PrintType.value == "LocalCheck") //6.现场查勘报告
			{
				strUrl = "/claim/ClaimPrint.do?printType=" + fm.PrintType.value + "&CompensateNo=" + fm.BizNo.value;
			}

			//modify by wangwei add start 2050528
			if (fm.PrintType.value == "CopyPrint") {
				strUrl = "/claim/ClaimPrint.do?printType=" + fm.PrintType.value + "&RegistNo=" + fm.BizNo.value;

			}
			if (fm.PrintType.value == "CopyPrintNew") {
				strUrl = "/claim/ClaimPrint.do?printType=" + fm.PrintType.value + "&RegistNo=" + fm.BizNo.value;

			}
			//modify by wangwei add end 20050528

			//modify by dongchengliang add start 20050615
			if (fm.PrintType.value == "AcciCheck") {
				// strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&CompensateNo=" + fm.BizNo.value;
				strUrl = "/claim/checkQuery.do?editType=PRINT&RegistNo=" + fm.BizNo.value;
			}
			if (fm.PrintType.value == "ShipEndcase") {
				strUrl = "/claim/ClaimPrint.do?printType=" + fm.PrintType.value + "&CompensateNo=" + fm.BizNo.value;
			}
			if (fm.PrintType.value == "PropCompensateNotice") {
				strUrl = "/claim/ClaimPrint.do?printType=" + fm.PrintType.value + "&CompensateNo=" + fm.BizNo.value;
			}
			if (fm.PrintType.value == "AcciNotClaim") {
				strUrl = "/claim/ClaimPrint.do?printType=" + fm.PrintType.value + "&RegistNo=" + fm.BizNo.value;
			}
			//modify by dongchengliang add end 20050615
			if (fm.PrintType.value == "PropCancelNotice") {
				strUrl = "/claim/ClaimPrint.do?printType=" + fm.PrintType.value + "&ClaimNo=" + fm.BizNo.value;
			}
			if (fm.PrintType.value == "PropCompensate") {
				strUrl = "/claim/ClaimPrint.do?printType=" + fm.PrintType.value + "&CompensateNo=" + fm.BizNo.value;
			}
			if (fm.PrintType.value == "AcciReview") {
				strUrl = "/claim/ClaimPrint.do?printType=" + fm.PrintType.value + "&CompensateNo=" + fm.BizNo.value;
			}
			if (fm.PrintType.value == "AcciCancelNotice") {
				strUrl = "/claim/ClaimPrint.do?printType=" + fm.PrintType.value + "&ClaimNo=" + fm.BizNo.value;
			}
			if (fm.PrintType.value == "ShipCopyPrint") {
				strUrl = "/claim/ClaimPrint.do?printType=" + fm.PrintType.value + "&RegistNo=" + fm.BizNo.value;
			}
			if (fm.PrintType.value == "ReparationsList") { //意健险批单列印
				strUrl = "/claim/ClaimPrint.do?printType=" + fm.PrintType.value + "&CompensateNo=" + fm.BizNo.value;
			}
			//add begin by zhuly 20051110  
			if (fm.PrintType.value == "FreightHeresyCheck") //货运险代查勘委托书列印
			{
				strUrl = "/claim/ClaimPrint.do?printType=" + fm.PrintType.value + "&RegistNo=" + fm.BizNo.value;
			}
			if (fm.PrintType.value == "FreightNationalCompensate") //国内货运险赔款理算书
			{
				strUrl = "/claim/ClaimPrint.do?printType=" + fm.PrintType.value + "&CompensateNo=" + fm.BizNo.value;
			}
			if (fm.PrintType.value == "FreightInportCompensate") //进口货运险赔款理算书
			{
				strUrl = "/claim/ClaimPrint.do?printType=" + fm.PrintType.value + "&CompensateNo=" + fm.BizNo.value;
			}
			if (fm.PrintType.value == "FreightExportCompensate") //出口货运险赔款理算书
			{
				strUrl = "/claim/ClaimPrint.do?printType=" + fm.PrintType.value + "&CompensateNo=" + fm.BizNo.value;
			}
			if (fm.PrintType.value == "FreightRefuseCancel") //拒赔/注销案件通知书
			{
				strUrl = "/claim/ClaimPrint.do?printType=" + fm.PrintType.value + "&ClaimNo=" + fm.BizNo.value;
			}
			if (fm.PrintType.value == "PropLocalCheck") //财产险现场查勘报告
			{
				strUrl = "/claim/ClaimPrint.do?printType=" + fm.PrintType.value + "&RegistNo=" + fm.BizNo.value;
			}
			if (fm.PrintType.value == "LiabCompensate") //责任险赔款理算报告
			{
				strUrl = "/claim/ClaimPrint.do?printType=" + fm.PrintType.value + "&CompensateNo=" + fm.BizNo.value;
			}
			//modify by hanliang add end 20051208
			if (fm.PrintType.value == "PropLocalCheck") {
				strUrl = "/claim/ClaimPrint.do?printType=" + fm.PrintType.value + "&RegistNo=" + fm.BizNo.value;
			}
			//add end   by zhuly 20051110
			//add start   by hanliang 20051212 
			if (fm.PrintType.value == "LiabLocaleCheck") {
				strUrl = "/claim/ClaimPrint.do?printType=" + fm.PrintType.value + "&RegistNo=" + fm.BizNo.value;
			}
			if (fm.PrintType.value == "PropLocaleHeresy") //财产险理赔计算书
			{
				strUrl = "/claim/ClaimPrint.do?printType=" + fm.PrintType.value + "&RegistNo=" + fm.BizNo.value;
			}
			//add end   by hanliang 20051212
			//add start   by hanliang 20051214 
			if (fm.PrintType.value == "FreightClaimApply") //货运险索赔申请书
			{
				strUrl = "/claim/ClaimPrint.do?printType=" + fm.PrintType.value + "&RegistNo=" + fm.BizNo.value;
			}
			//add end   by hanliang 20051214
			//add by caopeng start at 20051213
			if (fm.PrintType.value == "ClaimDocumentHandinCredence") //人身险索赔文件交接凭证
			{
				strUrl = "/claim/ClaimPrint.do?printType=" + fm.PrintType.value + "&ClaimNo=" + fm.BizNo.value;
			}
			if (fm.PrintType.value == "InvesReport") //意健险理赔调查报告
			{
				strUrl = "/claim/ClaimPrint.do?printType=" + fm.PrintType.value + "&RegistNo=" + fm.BizNo.value;
			}
			if (fm.PrintType.value == "CompensateAuditBook") //理赔审核书
			{
				strUrl = "/claim/ClaimPrint.do?printType=" + fm.PrintType.value + "&CompensateNo=" + fm.BizNo.value;
			}
			if (fm.PrintType.value == "FreightCoinsType") //联共保计算书
			{
				strUrl = "/claim/ClaimPrint.do?printType=" + fm.PrintType.value + "&CompensateNo=" + fm.BizNo.value;
			}
			//add by liping  start 20061123 start 
			if (fm.PrintType.value == "interestsTransferReport") //4.非水权益转让书
			{
				strUrl = "/claim/ClaimPrint.do?printType=" + fm.PrintType.value + "&ClaimNo=" + fm.BizNo.value + "&actionType=" + fm.PrintType.value;
			}
			if (fm.PrintType.value == "mairineRightsTransferReport") {
				strUrl = "/claim/ClaimPrint.do?printType=" + fm.PrintType.value + "&ClaimNo=" + fm.BizNo.value + "&actionType=" + fm.PrintType.value;
			}
			if (fm.PrintType.value == "noMarineHeresyCheck") {
				strUrl = "/claim/ClaimPrint.do?printType=" + fm.PrintType.value + "&RegistNo=" + fm.BizNo.value + "&actionType=" + fm.PrintType.value;
			}
			//add by caopeng end at 20051214
			//add by luochang begin at 20100527
			if (fm.PrintType.value == "claimApplication") {
				strUrl = "/claim/ClaimPrint.do?printType=" + fm.PrintType.value + "&ClaimNo=" + fm.BizNo.value;
			}
			//add by luochang end at 20100527
			printWindow(strUrl, "列印1");
		}
	}

	//显示列印窗口

	function printWindow(strURL, strWindowName) {
		//add print liudaoping 2013-04-15
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
<body onload="loadForm();"">
	<form name="fm" method="post" onsubmit="return validateForm(this);">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=2 class="formtitle" id="TitleName"></td>
			</tr>
			<tr>
				<td class='title2' id="BizName"></td>
				<td class='input2'>
					<input type='hidden' name='BizName'>
					<input class="common" type='text' name='BizNo' maxlength='25'>
				</td>
			</tr>
			<%--modify by zhaozhuo modify 20050414 end--%>
			<tr style="display: none" id="trEndDate">
				<td class='title2'>截止日期：</td>
				<td class='input2'>
					<input class="common" type='text' name='EndDate' style="width: 60" maxlength="10">
					日
					<input class="common" type='text' name='EndDateHour' style="width: 20">
					时
				</td>
			</tr>
			<tr>
				<td class="button" align="center" colspan="2">
					<input type=button value="下一步" class='button' onclick="submitForm();">
				</td>
			</tr>
			<input type='hidden' name="PrintType">
		</table>
	</form>
</body>
</html:html>
