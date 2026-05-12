<%--

****************************************************************************

* DESC       ：货运险查勘报告清单打印初始化

* AUTHOR     ：dongchengliang

* CREATEDATE ：2005-6-16

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
 	String policyNo = ""; //保单号

 	String registNo = ""; //报案号

 	String insuredName = ""; //保单号

 	String claimType = ""; //

 	String checker1 = ""; //

 	String damageTypeName = "";

 	String checkType = "";

 	String remark = ""; //

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

 	PrpLacciCheckDto prpLacciCheckDto = (PrpLacciCheckDto) request.getAttribute("prpLacciCheckDto");

 	PrpCmainDto prpCmainDto = (PrpCmainDto) request.getAttribute("prpCmainDto");

 	PrpLregistDto prpLregistDto = (PrpLregistDto) request.getAttribute("prpLregistDto");

 	java.util.ArrayList prpLacciCheckTextDtoList = (java.util.ArrayList) request.getAttribute("prpLacciCheckTextDtoList");

 	if (prpCmainDto != null) {

 		policyNo = prpCmainDto.getPolicyNo();

 	}
 	insuredName = prpLregistDto.getInsuredName();

 	if (prpLregistDto != null) {

 		checkEndDate = prpLacciCheckDto.getCheckEndDate();//调查结束日期

 		checkDate = prpLacciCheckDto.getCheckDate(); //调查开始日趋

 		damageStartDate = prpLregistDto.getDamageStartDate(); //出险时间

 		if (damageStartDate != null) {

 			damageStartYear = String.valueOf(damageStartDate.getYear());

 			damageStartMonth = String.valueOf(damageStartDate.getMonth());

 			damageStartDay = String.valueOf(damageStartDate.getDay());
 		}

 		if (checkDate.toString() != "") {

 			operateStartYear = String.valueOf(checkDate.getYear());

 			operateStartMonth = String.valueOf(checkDate.getMonth());

 			operateStartDay = String.valueOf(checkDate.getDay());

 		}

 	}

 	//获得调查描述 2005-08-16

 	for (int i = 0; i < prpLacciCheckTextDtoList.size(); i++) {

 		PrpLacciCheckTextDto prpLacciCheckTextDto = (PrpLacciCheckTextDto) prpLacciCheckTextDtoList.get(i);

 		strContext = strContext + prpLacciCheckTextDto.getContext();

 	}

 	String checkHour = "";
 	String checkMin = "";
 	String checkEndHour = "";
 	String checkEndMin = "";
 	String strDate = "调查时间：无";
 	//加入if条件判断；add by lidonghui 20070125
 	if (checkDate.toString() != "") {
 		checkHour = prpLacciCheckDto.getCheckHour().substring(0, prpLacciCheckDto.getCheckHour().indexOf(":"));
 		checkMin = prpLacciCheckDto.getCheckHour().substring(prpLacciCheckDto.getCheckHour().indexOf(":") + 1, prpLacciCheckDto.getCheckHour().length());

 		checkEndHour = prpLacciCheckDto.getCheckEndHour().substring(0, prpLacciCheckDto.getCheckEndHour().indexOf(":"));
 		checkEndMin = prpLacciCheckDto.getCheckEndHour().substring(prpLacciCheckDto.getCheckEndHour().indexOf(":") + 1, prpLacciCheckDto.getCheckEndHour().length());

 		strDate = "调查时间：" + checkDate.getYear() + "年" + checkDate.getMonth() + "月" + checkDate.getDay() + "日"

 		+ checkHour + "时" + checkMin + "分"

 		+ "至" + operateStartYear + "年" + operateStartMonth + "月" + operateStartDay + "日"

 		+ checkEndHour + "时" + checkEndMin + "分";
 	}
 %>



<script language="javascript">

function loadForm() {

	tdPolicyNo.innerHTML = '保单号码:' + '<%=policyNo%>';

	tdRegistNo.innerHTML = '案件号:' + '<%=prpLacciCheckDto.getRegistNo()%>';

	tdInsuredName.innerHTML = '被保险人姓名：' + '<%=insuredName%>';

	tdDamageTypeName.innerHTML = '事故类型：' + '<%=prpLacciCheckDto.getDamageTypeName()%>';

	tdCheckNature.innerHTML = '调查方式:' + '<%=prpLacciCheckDto.getCheckNature().equals("0") ? "直接调查" : "间接调查"%>';

	tdCheckerCode.innerHTML = '调查员:' + '<%=prpLacciCheckDto.getCheckerCode()%>';

	tdDamageStartDate.innerHTML = '事故日期：&nbsp;<%=damageStartYear%>年&nbsp;<%=damageStartMonth%>月&nbsp;<%=damageStartDay%>日';

}

</script>