<%--
****************************************************************************
* DESC       ：理赔调查报告初始化
* AUTHOR     ：caopeng
* CREATEDATE ：2005-12-13
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
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
<%@page import="java.util.ArrayList"%>

<%
	//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@");
	String policyNo = ""; //保单号
	String strRegistNo = ""; //报案号
	String strClaimNo = ""; //赔案号
	String insuredName = ""; //
	String claimType = ""; //
	String checker1 = "";
	String damageTypeName = "";
	String checkType = "";
	String strAcciName = ""; //事故人姓名

	int intSize = 0; //ArrayList的大小
	int index = 0;

	DateTime checkEndDate = new DateTime();

	DateTime checkDate = new DateTime();

	DateTime damageStartDate = new DateTime();

	String damageStartYear = "";

	String damageStartMonth = "";

	String damageStartDay = "";

	String damageStartHour = "";

	String damageStartMinute = "";

	String operateStartYear = "";

	String operateStartMonth = "";

	String operateStartDay = "";

	String operateStartHour = "";

	String operateStartMinutue = "";

	String strContext = ""; //调查描述

	//对象定义部分
	ClaimDto claimDto = null; //ClaimDto对象
	ArrayList arrPrpLacciPersonDto = null;

	PrpLacciCheckDto prpLacciCheckDto = (PrpLacciCheckDto) request.getAttribute("prpLacciCheckDto");

	PrpCmainDto prpCmainDto = (PrpCmainDto) request.getAttribute("prpCmainDto");

	PrpLregistDto prpLregistDto = (PrpLregistDto) request.getAttribute("prpLregistDto");

	ArrayList prpLacciCheckTextDtoList = (ArrayList) request.getAttribute("prpLacciCheckTextDtoList");
	UIClaimAction uiClaimAction = new UIClaimAction();

	strRegistNo = prpLacciCheckDto.getRegistNo();
	UICodeAction uiCodeAction = new UICodeAction();
	strClaimNo = uiCodeAction.translateBusinessCode(strRegistNo, true);

	//System.out.println("@@@@@@@@@@@@@@@wrerwer@@@@@@@@@");
	//得到claimDto对象
	if (strClaimNo != null && !strClaimNo.trim().equals("")) {
		claimDto = (ClaimDto) uiClaimAction.findByPrimaryKey(strClaimNo);
		if (claimDto != null) {
			PrpLacciPersonDto prpLacciPersonDto = (PrpLacciPersonDto) claimDto.getPrpLacciPersonDto();
			if (prpLacciPersonDto != null) {
				strAcciName = prpLacciPersonDto.getAcciName();
			}
		}
	}

	if (prpCmainDto != null) {

		policyNo = prpCmainDto.getPolicyNo();

		insuredName = prpCmainDto.getInsuredName();

	}

	if (prpLregistDto != null) {

		checkEndDate = prpLacciCheckDto.getCheckEndDate();//调查结束日期

		checkDate = prpLacciCheckDto.getCheckDate(); //调查开始日趋

		damageStartDate = prpLregistDto.getDamageStartDate(); //出险时间

		if (damageStartDate != null) {

			damageStartYear = String.valueOf(damageStartDate.getYear());

			damageStartMonth = String.valueOf(damageStartDate.getMonth());

			damageStartDay = String.valueOf(damageStartDate.getDay());

		}

		if (checkDate != null) {

			operateStartYear = String.valueOf(checkDate.getYear());

			operateStartMonth = String.valueOf(checkDate.getMonth());

			operateStartDay = String.valueOf(checkDate.getDay());

		}

	}

	//System.out.println("@@@@@@sdsdsdsa222@@@@@@@@@@@@@@@@@@");

	//获得调查描述

	for (int i = 0; i < prpLacciCheckTextDtoList.size(); i++) {

		PrpLacciCheckTextDto prpLacciCheckTextDto = (PrpLacciCheckTextDto) prpLacciCheckTextDtoList.get(i);

		strContext = strContext + prpLacciCheckTextDto.getContext();

	}

	//System.out.println("@@@@@@33333@@@@@@@@@@@@");

	String strDate = "调查时间：" + checkDate.getYear() + "年" + checkDate.getMonth() + "月" + checkDate.getDay() + "日";

	//  + prpLacciCheckDto.getCheckHour().substring(0,2) + "时" + prpLacciCheckDto.getCheckHour().substring(3,5) + "分" ;

	//  + "至" + operateStartYear + "年" +  operateStartMonth + "月" +  operateStartDay + "日"

	// +  prpLacciCheckDto.getCheckEndHour().substring(0, 2) + "时" + prpLacciCheckDto.getCheckEndHour().substring(3,5) + "分";

	//System.out.println("@@@@@@8888@555@@@@@@@@@@@");
%>



<script language="javascript">
function loadForm() {

	spRegistNo.innerHTML = '<%=strRegistNo%>';
	spClaimNo.innerHTML = '<%=strClaimNo%>';
	spCheckYear.innerHTML = '<%=checkEndDate.getYear()%>';
	spCheckMonth.innerHTML = '<%=checkEndDate.getMonth()%>';
	spCheckDate.innerHTML = '<%=checkEndDate.getDay()%>';
	spAcciName.innerHTML = '<%=strAcciName%>';

}
</script>