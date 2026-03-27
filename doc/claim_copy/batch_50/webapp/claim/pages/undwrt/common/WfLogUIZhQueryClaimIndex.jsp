<%--
****************************************************************************
* DESC       ：综合查询赔款计算书结果显示页面
* Author     : 统计分析项目组
* CREATEDATE ：2005-07-04
* MODIFYLIST ：   Name       Date            Reason/Contents
*                 Wangct		2005-07-04			Created
****************************************************************************
--%>

<%@page errorPage="/UIErrorPage"%>

<%-- 引入bean类部分 --%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>

<%
  //定义变量
  String strRiskCode = request.getParameter("RiskCode");
  String strRegistNo = request.getParameter("RegistNo");
  String strClaimNo = request.getParameter("ClaimNo");
  String strType = request.getParameter("Type");
  String strCompensateNo = request.getParameter("CompensateNo");
  String strBusinessType = request.getParameter("BusinessType");
%>

<script language=javascript>
//显示报案信息
function showRegist(strRiskCode, strRegistNo) {
    var strRegistPage = "/claim/registFinishQueryList.do?prpLregistRegistNo=" + strRegistNo + "&editType=SHOW&riskCode=" + strRiskCode;
    parent.fraMain.location = strRegistPage;
}

//显示查勘信息

function showCheck(strRiskCode, strRegistNo) {
    var strCheckPage = "/claim/checkFinishQueryList.do?prpLcheckCheckNo=" + strRegistNo + "&editType=SHOW&riskCode=" + strRiskCode;
    parent.fraMain.location = strCheckPage;
}

//显示调查记录信息

function showacciCheck(strRiskCode, strRegistNo) {
    var stracciCheckPage = "/claim/checkFinishQueryList.do?prpLcheckCheckNo=" + strRegistNo + "-001&editType=SHOW&type=acci&riskCode=" + strRiskCode + "&keyIn=" + strRegistNo + "-001";
    parent.fraMain.location = stracciCheckPage;
}

//显示立案信息

function showClaim(strRiskCode, strClaimNo) {
    var strClaimPage = "/claim/claimFinishQueryList.do?prpLclaimClaimNo=" + strClaimNo + "&editType=SHOW&riskCode=" + strRiskCode;
    parent.fraMain.location = strClaimPage;
}

//显示赔款计算书列表

function showCompensateList(strClaimNo) {
    var strCompensatePage = "/prpall/common/qry/UIZhQueryCompensateShow.jsp?Condition=ClaimNo=" + strClaimNo;
    parent.fraMain.location = strCompensatePage;
}

//显示赔款计算书信息

function showCompensate(strRiskCode, strCompensateNo) {
    var strCompensatePage = "";
    if (strRiskCode != "" && (strRiskCode.substring(0, 2) == "09" || strRiskCode.substring(0, 2) == "10")) {

        strCompensatePage = "/claim/ClaimPrint.do?printType=FreightCompensate&CompensateNo=" + strCompensateNo;
    } else {
        strCompensatePage = "/claim/compensateFinishQueryList.do?prpLcompensateCompensateNo=" + strCompensateNo + "&editType=SHOW&riskCode=" + strRiskCode;
    }
    parent.fraMain.location = strCompensatePage;
}

//显示结案信息

function showEndCase(strRiskCode, strClaimNo) {
    var strEndCasePage = "/claim/endcaseFinishQueryList.do?prpLendcaseEndcaseNo=" + strClaimNo + "&editType=SHOW&riskCode=" + strRiskCode + "&ClaimNoSign=*";
    parent.fraMain.location = strEndCasePage;
}

//显示预赔信息

function showPrePay(strRiskCode, strCompensateNo, strBusinessType) {
    if (strBusinessType == "Y") {
        var strPrePayPage = "/claim/prepayFinishQueryList.do?prpLprepayPrepayNo=" + strCompensateNo +
            "&editType=SHOW&riskCode=" + strRiskCode + "&ifclose=true";
        parent.fraMain.location = strPrePayPage;
    }
}
//显示实赔信息

function showRealPay(strRiskCode, strCompensateNo) {
    var strRealPayPage = "/claim/compensateFinishQueryList.do?prpLcompensateCompensateNo=" + strCompensateNo +
        "&editType=SHOW&riskCode=" + strRiskCode + "&ifclose=true";
    parent.fraMain.location = strRealPayPage;
}
//显示定损信息

function showCertainLoss(strRiskCode, strRegistNo) {
    var strCertainLossPage = "/claim/certainLossFinishQueryList.do?prpLverifyLossRegistNo=" + strRegistNo + "&editType=SHOW&lossItemCode=1&riskCode=" + strRiskCode;
    parent.fraMain.location = strCertainLossPage;
}
//显示核损信息

function showVerifyLoss(strRiskCode, strRegistNo) {
    var strVerifyLossPage = "/claim/verifyLossFinishQueryList.do?prpLverifyLossRegistNo=" + strRegistNo + "&editType=SHOW&lossItemCode=1&nodeType=verif&riskCode=" + strRiskCode;
    parent.fraMain.location = strVerifyLossPage;
}
</script>
<style>
</style>
<html>
<head>
<title><s:text name="title.undwrtBeforeEdit.ClaimsInformation" /></title>
<%-- 理赔信息 --%>
<%-- 公用函数 
  <script src="/prpall/common/pub/UICommon.js"></script>--%>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<%-- 调用loadForm 初始化页面 --%>
<body class="interface">
	<table width="100%" cellpadding="5" cellspacing="1" align="center">
		<tr>
			<td class="button"><input class="button" type="button" name="Regist" onclick="showRegist('<%=strRiskCode%>','<%=strRegistNo%>');" value="<s:text name='button.Report.value'/>">
			<%-- 报案 --%></td>
			<td class="button">
				<%if (strRiskCode.trim().substring(0,2).equals("27")){%> <input class="button" type="button" name="acciCheck" onclick="showacciCheck('<%=strRiskCode%>','<%=strRegistNo%>');" value="<s:text name='button.InvestigationRecord.value'/>">
			<%-- 调查记录 --%> <%}else{%> <input class="button" type="button" name="Check" onclick="showCheck('<%=strRiskCode%>','<%=strRegistNo%>');" value="<s:text name='button.Mapping.value'/>">
			<%-- 查勘 --%> <%}%>
			</td>
			<td class="button"><input class="button" type="button" name="Claim" onclick="showClaim('<%=strRiskCode%>','<%=strClaimNo%>');" value="<s:text name='button.record.value'/>"> <%--立案  --%></td>
			<td class="button"><input class="button" type="button" name="CertainLoss" onclick="showCertainLoss('<%=strRiskCode%>','<%=strRegistNo%>');" value="<s:text name='button.Loss.value'/>">
			<%-- 定损 --%></td>
			<td class="button"><input class="button" type="button" name="VerifyLoss" onclick="showVerifyLoss('<%=strRiskCode%>','<%=strRegistNo%>');" value="<s:text name='button.NuclearDamage.value'/>">
			<%-- 核损 --%></td>
			<td class="button">
				<%if (strCompensateNo==null||strCompensateNo.trim().equals("")){%> <input class="button" type="button" name="CompensateList" onclick="showCompensateList('<%=strClaimNo%>');" value="<s:text name='button.AccountList.value'/>">
			<%-- 计算书列表 --%> <%}else{%> <input class="button" type="button" name="Compensate" onclick="showCompensate('<%=strRiskCode%>','<%=strCompensateNo%>');" value="<s:text name='button.accountBook.value'/>">
			<%--赔款计算书 --%> <%}%>
			</td>
			<!--   //在核赔中不能看到结案信息且暂时没有预赔，予以屏蔽。  2005-9-16
		<td class="button">
		 	<input class="button" type="button" name="EndCase" onclick="showEndCase('<%=strRiskCode%>','<%=strClaimNo%>');" value="结案">
		</td>
		<td class="button">
			<input class="button" type="button" name="PrePay"
			onclick="showPrePay('<%=strRiskCode%>', '<%=strCompensateNo%>', '<%=strBusinessType%>');" value="预赔">
		</td>
		-->
			<!--
		<td class="button">
			<input class="button" type="button" name="RealPay"
			onclick="showRealPay('<%=strRiskCode%>', '<%=strCompensateNo%>');" value="实赔">
		</td>
-->
		</tr>
		<!--
需要在/common/pub/UITitle.jsp和/commonship/pub/UITitle.jsp的undwrt_Init方法中增加如下代码，
将一个固定的用户名和密码登录到工作流，在工作流中遇到该用户则初始化相关参数：
    	oBao.open("POST","/claim/logonin.do?prpDuserUserCode=Viewer&prpDuserPassword=XXXX",false);
    	oBao.send();
另外，工作流中需要增加特殊控制，注意安全性以及屏蔽相关可写的按钮。
-->
	</table>
</body>
</html>
