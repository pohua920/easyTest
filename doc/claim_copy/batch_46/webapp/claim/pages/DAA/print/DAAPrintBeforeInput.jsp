<%--
****************************************************************************
* DESC       ：理赔列印前输入单证号
* AUTHOR     ：理赔组
* CREATEDATE ：2004-11-16
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
 
<%-- 引入bean类部分 --%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.utility.string.*"%>
<%@page import="com.ddcc.utiall.dbsvr.*"%>
<%@page import="com.ddcc.utiall.dbsvr.*"%>
<%@page import="com.ddcc.ddccall.blsvr.lp.*"%>
<%@page import="com.ddcc.ddccall.dbsvr.lp.*"%>
<%@page import="com.ddcc.ddccall.schema.*"%>
<%@page import="com.sinosoft.utility.string.Date"%>
 
<%
 	String strPrintType = request.getParameter("PrintType");
 	String strTitleName = "";
 	String strBizName = "";
 	ChgDate chgDate = new ChgDate();

 	String strWherePart = "";
 	int intCount = 0;
 	String strMessage = "";

 	if (strPrintType.equals("Regist")) {
 		//1.报案记录（代抄单）
 		strTitleName = "机动车辆保险报案记录（承保理赔信息）列印";
 		strBizName = "报案号";
 	}
 	if (strPrintType.equals("Pilfer")) {
 		//2.出险（盗抢）证明 
 		strTitleName = "出险（盗抢）证明列印";
 		strBizName = "赔案号";
 	}
 	if (strPrintType.equals("Cancelnotice")) {
 		//3.拒赔通知书
 		strTitleName = "拒赔通知书列印";
 		strBizName = "赔案号";
 	}
 	if (strPrintType.equals("Canceltrans")) {
 		//4.拒赔案件报告书
 		strTitleName = "拒赔案件报告书列印";
 		strBizName = "赔案号";
 	}
 	if (strPrintType.equals("LossSimple")) {
 		//5.损失情况简易确认书
 		strTitleName = "损失情况简易确认书列印";
 		strBizName = "赔案号";
 	}
 	if (strPrintType.equals("Loss")) {
 		//6.损失情况确认书
 		strTitleName = "损失情况确认书列印";
 		strBizName = "赔案号";
 	}
 	if (strPrintType.equals("ComponentList")) {
 		//7.零部件更换项目清单
 		strTitleName = "零配件更换项目清单列印";
 		strBizName = "赔案号";
 	}
 	if (strPrintType.equals("ComponentAdd")) {
 		//8.零部件更换项目清单附表
 		strTitleName = "零配件更换项目清单附表列印";
 		strBizName = "赔案号";
 	}
 	if (strPrintType.equals("RepairList")) {
 		//9.修理项目清单
 		strTitleName = "修理项目清单列印";
 		strBizName = "赔案号";
 	}
 	if (strPrintType.equals("RepairAdd")) {
 		//10.修理项目清单附表
 		strTitleName = "修理项目清单附表列印";
 		strBizName = "赔案号";
 	}
 	if (strPrintType.equals("PropList")) {
 		//11.财产损失确认书
 		strTitleName = "财产损失确认书列印";
 		strBizName = "赔案号";
 	}
 	if (strPrintType.equals("Compensate")) {
 		//12.赔款计算书
 		strTitleName = "赔款计算书列印";
 		strBizName = "赔款计算书号";
 	}
 	if (strPrintType.equals("CompensateAdd")) {
 		//13.赔款计算书附页
 		strTitleName = "赔款计算书附页列印";
 		strBizName = "赔款计算书号";
 	}
 	if (strPrintType.equals("PayStatList")) {
 		//14.赔款统计明细表
 		strTitleName = "赔款统计明细表";
 		strBizName = "归档号";
 	}
 	if (strPrintType.equals("Drawnotice")) {
 		//15.领取赔款通知书
 		strTitleName = "领取赔款通知书列印";
 		//    strBizName   = "归档号";
 		strBizName = "赔款计算书号";
 	}
 	if (strPrintType.equals("Prepay")) {
 		//16.预付赔款审批表
 		strTitleName = "预付赔款审批表列印";
 		strBizName = "预赔号";
 	}
 	if (strPrintType.equals("Pressnotice")) {
 		//17.结案催告、注销通知书
 		strTitleName = "结案催告、註銷通知书列印";
 		strBizName = "赔案号";
 	}
 	if (strPrintType.equals("Endcase")) {
 		//18.结案报告书
 		strTitleName = "结案报告书列印";
 		// strBizName   = "归档号";
 		strBizName = "赔案号";
 	}
 	if (strPrintType.equals("HistoryFile")) {
 		//19.原始保单及出险前批单
 		strTitleName = "原始保单及出险前批单列印";
 		strBizName = "保单号";
 	}
 	if (strPrintType.equals("FileOnRisk")) {
 		//20.出险时保单
 		strTitleName = "出险时保单列印";
 		strBizName = "保单号";
 	}
 %>  
<html>
  <head>
    <title>理赔列印前输入单证号</title>
    <%-- 公用函数 --%>
    <script src="/ddccallweb/common/print/Common.js"></script>
    <%-- 页面样式  --%>
    <link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
    <script language='javascript'>     
    function loadForm() {
    	TitleName.innerHTML = '<%=strTitleName%>';
    	fm.PrintType.value = '<%=strPrintType%>';
    	BizName.innerHTML = '<%=strBizName%>' + '：';
    	fm.BizName.value = '<%=strBizName%>';

    	if ("<%=strPrintType%>" == "FileOnRisk" || "<%=strPrintType%>" == "HistoryFile") {
    		trEndDate.style.display = "";
    	}

    	if ("<%=strPrintType%>" == "FileOnRisk") {
    		fm.EndDate.value = '<%=chgDate.getCurrentTime("yyyy-MM-dd")%>';
    		fm.EndDateHour.value = "<%=new Date().getString(Date.HOUR)%>";
    	}

    }


    function checkForm() {
    	if (isEmpty(fm.BizNo)) {
    		fm.BizNo.focus();
    		errorMessage(fm.BizName.value + "不能为空!");
    		return false;
    	} else if (trim(fm.BizNo.value).length != 22) {
    		fm.BizNo.focus();
    		errorMessage(fm.BizName.value + "应为22位长!");
    		return false;
    	}

    	return true;
    }


    function submitForm() {
    	if (checkForm() == true) {
    		var strUrl = "";

    		if (fm.PrintType.value == "Regist") //1.报案记录（代抄单）
    		{
    			strUrl = "/ddccallwebDAARegistNoneFormatPrint.jsp?RegistNo=" + fm.BizNo.value;
    		}
    		if (fm.PrintType.value == "Pilfer") //2.出险（盗抢）证明
    		{
    			strUrl = "/ddccallwebDAAPilferNoneFormatPrint.jsp?ClaimNo=" + fm.BizNo.value;
    		}
    		if (fm.PrintType.value == "Cancelnotice") //3.拒赔通知书
    		{
    			strUrl = "/ddccallwebDAACancelnoticeNoneFormatPrint.jsp?ClaimNo=" + fm.BizNo.value;
    		}
    		if (fm.PrintType.value == "Canceltrans") //4.拒赔案件报告书
    		{
    			strUrl = "/ddccallwebDAACanceltransNoneFormatPrint.jsp?ClaimNo=" + fm.BizNo.value;
    		}
    		if (fm.PrintType.value == "LossSimple") //5.损失情况简易确认书
    		{
    			strUrl = "/ddccallwebDAALossSimpleNoneFormatPrint.jsp?ClaimNo=" + fm.BizNo.value;
    		}
    		if (fm.PrintType.value == "Loss") //6.损失情况确认书
    		{
    			strUrl = "/ddccallwebDAALossNoneFormatPrint.jsp?ClaimNo=" + fm.BizNo.value;
    		}
    		if (fm.PrintType.value == "ComponentList") //7.零部件更换项目清单
    		{
    			strUrl = "/ddccallwebDAAComponentListNoneFormatPrint.jsp?ClaimNo=" + fm.BizNo.value;
    		}
    		if (fm.PrintType.value == "ComponentAdd") //8.零部件更换项目清单附表
    		{
    			strUrl = "/ddccallwebDAAComponentAddNoneFormatPrint.jsp?ClaimNo=" + fm.BizNo.value;
    		}
    		if (fm.PrintType.value == "RepairList") //9.修理项目清单
    		{
    			strUrl = "/ddccallwebDAARepairListNoneFormatPrint.jsp?ClaimNo=" + fm.BizNo.value;
    		}
    		if (fm.PrintType.value == "RepairAdd") //10.修理项目清单附表
    		{
    			strUrl = "/ddccallwebDAARepairAddNoneFormatPrint.jsp?ClaimNo=" + fm.BizNo.value;
    		}
    		if (fm.PrintType.value == "PropList") //11.财产损失确认书
    		{
    			strUrl = "/ddccallwebDAAPropListNoneFormatPrint.jsp?ClaimNo=" + fm.BizNo.value;
    		}
    		if (fm.PrintType.value == "Compensate") //12.赔款计算书
    		{
    			strUrl = "/ddccallwebDAACompensateNoneFormatPrint.jsp?CompensateNo=" + fm.BizNo.value;
    		}
    		if (fm.PrintType.value == "CompensateAdd") //13.赔款计算书附页
    		{
    			strUrl = "/ddccallwebDAACompensateAddNoneFormatPrint.jsp?CompensateNo=" + fm.BizNo.value;
    		}
    		if (fm.PrintType.value == "PayStatList") //14.赔款统计明细表
    		{
    			strUrl = "/ddccallwebDAAPayStatListNoneFormatPrint.jsp?CaseNo=" + fm.BizNo.value;
    		}
    		if (fm.PrintType.value == "Drawnotice") //15.领取赔款通知书
    		{
    			strUrl = "/ddccallwebDAADrawnoticeNoneFormatPrint.jsp?CompensateNo=" + fm.BizNo.value;
    		}
    		if (fm.PrintType.value == "Prepay") //16.预付赔款审批表
    		{
    			strUrl = "/ddccallwebDAAPrepayNoneFormatPrint.jsp?PreCompensateNo=" + fm.BizNo.value;
    		}
    		if (fm.PrintType.value == "Pressnotice") //17.结案催告、注销通知书
    		{
    			strUrl = "/ddccallwebDAAPressnoticeNoneFormatPrint.jsp?ClaimNo=" + fm.BizNo.value;
    		}
    		if (fm.PrintType.value == "Endcase") //18.结案报告书
    		{
    			// strUrl = "/ddccallwebDAAEndcaseNoneFormatPrint.jsp?CaseNo=" + fm.BizNo.value;  
    			strUrl = "/ddccallwebDAAEndcaseNoneFormatPrint.jsp?ClaimNo=" + fm.BizNo.value;
    		}
    		if (fm.PrintType.value == "HistoryFile") //19.原始保单及出险前批单
    		{
    			//strUrl = "/ddccallwebDAAEndcaseNoneFormatPrint.jsp?CaseNo=" + fm.BizNo.value;  
    			strUrl = "/ddccallweb/common/lp/print/UILOrigalPolicyEndorseNoneFormatPrintBefore.jsp?PolicyNo=" + fm.BizNo.value;

    		}
    		if (fm.PrintType.value == "FileOnRisk") //20.出险时保单
    		{
    			// strUrl = "/ddccallweb/common/lp/print/UILFileOnRiskNoneFormatPrint.jsp";  
    			strUrl = "/ddccallweb/common/lp/print/UILDamagePolicyNoneFormatPrint.jsp?PolicyNo=" + fm.BizNo.value + "&EndDate=" + fm.EndDate.value + " &EndDateHour=" + fm.EndDateHour.value;

    		}
    		//alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
    		return false;
    		printWindow(strUrl, "列印1");
    		//          var newWindow = window.open(strUrl,"",'width=760,height=400,top=0,left=0,toolbar=1,location=1,directories=0,menubar=1,scrollbars=1,resizable=1,status=1');
    		//          newWindow.focus();
    		//          return newWindow;
    	}
    }
    </script>
  </head>
<body class="interface" onload="loadForm();">
	<form name='fm' method="post">
		<table class="three" align=center>
			<tr>
				<td class=formtitle colspan="3" id="TitleName"></td>
				<input type='hidden' name="PrintType">
			</tr>
			<tr>
				<td class='title' style="width: 20%" id="BizName"></td>
				<td class='input'>
					<input type='hidden' name='BizName'>
					<input class="common" type='text' name='BizNo' maxlength='22' onkeypress="return uppercaseKey();">
				</td>
			</tr>
			<tr style="display: none" id="trEndDate">
				<td class='title' style="width: 20%">截止日期：</td>
				<td class='input'>
					<input class="common" type='text' name='EndDate' style="width: 80" maxlength="10" onkeypress="return pressDatetime(event)">
					日
					<input class="common" type='text' name='EndDateHour' style="width: 20" onkeypress="return pressInteger(event);">
					时
				</td>
			</tr>
			<tr>
				<td class=centertitle colspan="2">
					<input type=button value="下一步" class="button" onclick="submitForm();">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
  