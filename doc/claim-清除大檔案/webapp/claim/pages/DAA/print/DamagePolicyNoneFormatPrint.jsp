<%--
****************************************************************************
* DESC       ：出险时保单打印页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-11-18
* MODIFYLIST ：Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK" %>

<%@page errorPage="/UIErrorPage"%>

<%-- 引入bean类部分 --%>
<%@page import="java.text.*"%>
<%@page import="com.sinosoft.claim.ui.control.action.*"%>
<%@page import="com.sinosoft.claim.ui.control.viewHelper.*"%>
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="com.sinosoft.claim.dto.domain.*"%> 
<%@page import="com.sinosoft.claim.util.*"%> 
<%@page import="com.sinosoft.sysframework.common.util.*"%> 
<%@page import="com.sinosoft.sysframework.common.datatype.DateTime"%> 
<%@page import="com.sinosoft.sysframework.exceptionlog.*"%>
<%@page import="com.sinosoft.claim.util.*"%>

<%
	//变量声明部分
	String strPolicyNo = ""; //保单号  
	String strDamageDate = request.getParameter("EndDate"); //截至日期  
	String strDamageHour = request.getParameter("EndHour"); //截止时间
	String strStartDate = ""; //起保日期
	String strStartHour = ""; //起保小时
	String strEndDate = ""; //终保日期
	String strEndHour = ""; //终保小时
	String strRiskCode = ""; //险种
	String strMessage = ""; //消息
	String strWhere = ""; //sql语句
	String strEndorseNo = ""; //批单号
	String strRiskPage = ""; //打印页面

	int index = 0;

	//得到保单对象的信息
	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");

	PrpCmainDto prpCmainDto = policyDto.getPrpCmainDto();

	strPolicyNo = prpCmainDto.getPolicyNo();

	//流转录入页面
	strRiskCode = prpCmainDto.getRiskCode(); //险种代码

	//截至日期是否在保险期内
	//dbPrpDrisk.getInfo(strRiskCode);
	strStartDate = prpCmainDto.getStartDate().toString();
	strStartHour = prpCmainDto.getStartHour() + "";
	strEndDate = prpCmainDto.getEndDate().toString();
	strEndHour = prpCmainDto.getEndHour() + "";

	EndorseViewHelper endorseViewHelper = new EndorseViewHelper();
	PolicyDto policyDtoOld = endorseViewHelper.findForEndorBefore(prpCmainDto.getPolicyNo(), strDamageDate);

	request.setAttribute("policyDto", policyDtoOld);

	//根据保单数据分发到打印页面
	UICodeAction uiCodeAction = new UICodeAction();
	String strRiskType = uiCodeAction.translateRiskCodetoRiskType(strRiskCode);
	if (("D").equals(strRiskType)) {
		strRiskPage = "PolicyDAASingleCarNoneFormatPrint.jsp";
	} else {
		strRiskPage = "/" + strRiskCode + "/cb/UIPolicy" + strRiskCode + "NoneFormatPrint.jsp";
	}

	//System.out.println("=============="+strRiskPage);
%>
<jsp:forward page='<%=strRiskPage%>'>
	<jsp:param name='EDITTYPE' value='COPY' />
	<jsp:param name='PolciyNo' value='<%=strPolicyNo%>' />
	<jsp:param name='PrintType' value='L' />
</jsp:forward>
