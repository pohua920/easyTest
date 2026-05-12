<%--

****************************************************************************

* DESC       ：显示(非车险)立案登记的险别估损金额页面

* AUTHOR     ：luqin

* CREATEDATE ：2005-06-19

* MODIFYLIST ：   Name       Date            Reason/Contents

*          ------------------------------------------------------

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
 	String strPolicyNo = "";

 	String strLinkerName = "";

 	String strPostAddress = "";

 	int intinsured = 0;

 	PrpCinsuredDto prpCinsuredDto = null;

 	DateTime dateTime = new DateTime();

 	CompensateDto compensateDto = (CompensateDto) request.getAttribute("compensateDto");

 	ClaimDto claimDto = (ClaimDto) request.getAttribute("claimDto");

 	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");

 	PrpLcompensateDto prpLcompensateDto = compensateDto.getPrpLcompensateDto();

 	PrpLclaimDto prpLclaimDto = claimDto.getPrpLclaimDto();

 	strPolicyNo = prpLcompensateDto.getPolicyNo();
 %>

<script language="javascript">

function loadForm() {

	tdCompensateNo.innerHTML = '<%=prpLcompensateDto.getCompensateNo()%>';

	tdOperatorCode.innerHTML = '<%=prpLcompensateDto.getOperatorCode()%>';

	tdOperatorName.innerHTML = '<%=prpLclaimDto.getOperatorName()%>';

	tdComName.innerHTML = '<%=prpLcompensateDto.getComCode()%>';



	tdPolicyNo.innerHTML = '<%=strPolicyNo%>';

	tdPrintTime.innerHTML = '<%=new DateTime(dateTime.current(), dateTime.YEAR_TO_HOUR)%>';

	tdTimes.innerHTML = '<%=prpLcompensateDto.getTimes()%>';



	<%
	if (policyDto.getPrpCinsuredDtoList() != null) {

		intinsured = policyDto.getPrpCinsuredDtoList().size();

		prpCinsuredDto = (PrpCinsuredDto) policyDto.getPrpCinsuredDtoList().get(0);

		strLinkerName = prpCinsuredDto.getLinkerName(); %>

		tdPostAddress.innerHTML = '<%=prpCinsuredDto.getPostAddress()%>';

		tdPhoneNumber.innerHTML = '<%=prpCinsuredDto.getPhoneNumber()%>';

		tdInsuredCode.innerHTML = '<%=prpCinsuredDto.getInsuredCode()%>';

		<%
	} %>

}

</script>