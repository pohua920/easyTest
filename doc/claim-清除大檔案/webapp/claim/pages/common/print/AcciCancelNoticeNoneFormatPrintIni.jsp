<%--

****************************************************************************

* DESC       ：意健险拒赔通知书（初始化页面）

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
	String strIdentifyNumber = "";

	String strDate = "";

	String strDamageName = "";

	DateTime dateTime = new DateTime();

	ClaimDto claimDto = (ClaimDto) request.getAttribute("claimDto");

	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");

	PrpLclaimDto prpLclaimDto = claimDto.getPrpLclaimDto();

	PrpCinsuredDto prpCinsuredDto = null;

	String strInsuredName = prpLclaimDto.getInsuredName();

	if (policyDto.getPrpCinsuredDtoList() != null) {

		prpCinsuredDto = (PrpCinsuredDto) policyDto
				.getPrpCinsuredDtoList().get(0);

		strIdentifyNumber = prpCinsuredDto.getIdentifyNumber();

	}

	strDate = dateTime.current().getYear() + "年"
			+ dateTime.current().getMonth() + "月"
			+ dateTime.current().getDate() + "日";

	strDamageName = prpLclaimDto.getDamageName();
%>
<script language="javascript">

function loadForm(){

  tdClaimNo.innerHTML = '赔案号：'+'<%=prpLclaimDto.getClaimNo()%>';

  tdHandlerName.innerHTML = '经办人：'+'<%=prpLclaimDto.getHandlerName()%>
	';

	}
</script>