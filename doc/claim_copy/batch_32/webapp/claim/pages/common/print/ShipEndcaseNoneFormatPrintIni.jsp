<%--

****************************************************************************

* DESC       ：货运险赔案终结报告书　

* AUTHOR     ：zhulei

* CREATEDATE ：2005-9-20

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

<%@page import="com.sinosoft.function.insutil.bl.facade.BLPubRateFacade"%>

<%@page import="com.sinosoft.utiall.dbsvr.DBPrpDcode"%>




<%
	//变量声明部分

	String strCompensateNo = request.getParameter("CompensateNo"); //赔款计算书号

	String strClaimNo = ""; //赔案号

	String strPolicyNo = ""; //保单号

	//得到ClaimDto,RegistDto,CertainLossDto,PolicyDto对象 zhulei leave：不一定有用，待整理

	ClaimDto claimDto = (ClaimDto) request.getAttribute("claimDto");

	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");

	RegistDto registDto = (RegistDto) request.getAttribute("registDto");

	CheckDto checkDto = (CheckDto) request.getAttribute("checkDto");

	CompensateDto compensateDto = (CompensateDto) request.getAttribute("compensateDto");

	CertainLossDto certainLossDto = (CertainLossDto) request.getAttribute("certainLossDto");

	//得到保单号

	//strPolicyNo = policyDto.getPrpCmainDto().getPolicyNo();

	//System.out.println(".............strPolicyNo="+strPolicyNo);

	BLPrpCmainCargo blPrpCmainCargo = new BLPrpCmainCargo();

	//blPrpCmainCargo.
%>