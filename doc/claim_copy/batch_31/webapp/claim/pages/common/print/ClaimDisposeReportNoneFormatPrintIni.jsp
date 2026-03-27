<%--

****************************************************************************

* DESC       ：赔案处理报告初始化

* AUTHOR     ：luqin

* CREATEDATE ：2005-6-8

* MODIFYLIST ：   Name       Date            Reason/Contents

****************************************************************************

--%>



<%-- 引入bean类部分 --%>

<%@page import="java.text.*"%>

<%@page import="com.sinosoft.claim.ui.control.action.*"%>

<%@page import="com.sinosoft.claim.dto.custom.*"%>

<%@page import="com.sinosoft.claim.dto.domain.*"%> 

<%@page import="com.sinosoft.claim.util.*"%> 

<%@page import="com.sinosoft.sysframework.common.util.*"%> 

<%@page import="com.sinosoft.sysframework.common.datatype.DateTime"%> 

<%@page import="com.sinosoft.sysframework.exceptionlog.*"%> 



<%
 	//变量声明部分

 	String strClaimNo = ""; //赔案号

 	String strPolicyNo = ""; //保单号     

 	String strKindCode = ""; //险别代码

 	String strAddress = ""; //出险地点

 	String strInsuredDate = ""; //保险期间

 	String strDamageStartDate = ""; //出险时间

 	//代码翻译变量

 	String strCode = "";

 	String strName = "";

 	boolean isChinese = true; //中文标志

 	String strInsuredAddress = "";

 	//对象定义部分

 	PrpLclaimDto prpLclaimDto = null; //ClaimDto对象

 	//得到ClaimDto,RegistDto,CertainLossDto,PolicyDto对象

 	ClaimDto claimDto = (ClaimDto) request.getAttribute("claimDto");

 	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");
 	String coins = request.getAttribute("coins").toString() + "%";

 	if (policyDto != null) {
 		PrpCmainDto prpCmainDto = policyDto.getPrpCmainDto();
 		if (prpCmainDto != null)
 			strInsuredAddress = prpCmainDto.getInsuredAddress();
 	}

 	UICodeAction uiCodeAction = new UICodeAction();

 	//得到prpLclaimDto对象

 	prpLclaimDto = claimDto.getPrpLclaimDto();

 	//得到赔案号
 	strClaimNo = prpLclaimDto.getClaimNo();

 	//得到保单号

 	strPolicyNo = prpLclaimDto.getPolicyNo();
 %>



<script language="javascript">

function loadForm()

{

	tdCaseNo.innerHTML = '<%=strClaimNo%>';

	tdPolicyNo.innerHTML = '<%=strPolicyNo%>';

	tdRiskCode.innerHTML = '<%=prpLclaimDto.getRiskCodeName()%>';

	tdSumAmount.innerHTML = '<%=new DecimalFormat("#,##0.00").format(prpLclaimDto.getSumAmount())%>';

	<% strInsuredDate = "自 " + prpLclaimDto.getStartDate().getYear()

	+ "年" + prpLclaimDto.getStartDate().getMonth()

	+ "月" + prpLclaimDto.getStartDate().getDate()

	+ "日 零时起"

	+ "<br>";
	if (!prpLclaimDto.getEndDate().toString().equals("")) {
		strInsuredDate += "<br>"

		+ "至 " + prpLclaimDto.getEndDate().getYear()

		+ "年" + prpLclaimDto.getEndDate().getMonth()

		+ "月" + prpLclaimDto.getEndDate().getDate()

		+ "日 二十四时止";
	} %>

	tdInsuredDate.innerHTML = '<%=strInsuredDate%>';

	tdInsuredName.innerHTML = '<%=StringConvert.encode(prpLclaimDto.getInsuredName())%>(<%=strInsuredAddress%>)';

	tdDamageAddress.innerHTML = '<%=prpLclaimDto.getDamageAddress()%>';

	<% strDamageStartDate = prpLclaimDto.getDamageStartDate().getYear()

	+ " 年 " + prpLclaimDto.getDamageStartDate().getMonth()

	+ " 月 " + prpLclaimDto.getDamageStartDate().getDate()

	+ " 日"; %>

	tdDamageStartDate.innerHTML = '<%=strDamageStartDate%>';

	<% //事故责任

	strCode = "";

	strName = "";

	strCode = StringConvert.encode(prpLclaimDto.getIndemnityDuty());

	strName = uiCodeAction.translateCodeCode("IndemnityDuty", strCode, isChinese);

	//科学计数法->普通计数法
	NumberFormat numberFormat = java.text.NumberFormat.getInstance();
	numberFormat.setGroupingUsed(false);
	String strSumDefLoss = numberFormat.format(prpLclaimDto.getSumDefLoss());
	String strSumClaim = numberFormat.format(prpLclaimDto.getSumClaim()); %>

	tdIndemnityDuty.innerHTML = '<%=prpLclaimDto.getDeductibleRate()%>';

	tdSumClaim.innerHTML = '<%=strSumClaim%>';

	tdSumDefLoss.innerHTML = '<%=strSumDefLoss%>';

	tdSumPaid.innerHTML = '<%=prpLclaimDto.getSumPaid()%>';

	tdCoins.innerHTML = '<%=coins%>';

}
</script>

  