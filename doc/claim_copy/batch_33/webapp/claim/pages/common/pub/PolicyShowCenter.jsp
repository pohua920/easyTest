<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ page import="com.sinosoft.utiall.dbsvr.DBPrpDrisk"%>
<%@ page import="com.sinosoft.claim.bl.facade.BLPolicyFacade"%>
<%@ page import="com.sinosoft.sysframework.reference.AppConfig"%>
<%@page import="com.sinosoft.claim.dto.custom.UserDto"%>
<%
	//String strComCode  = request.getParameter("myComCode");
	UserDto userDto = (UserDto) session.getAttribute("user");
	String strComCode = userDto.getComCode();
	String strBIZTYPE = request.getParameter("BIZTYPE");
	String strSHOWTYPE = request.getParameter("SHOWTYPE");
	String strBizNo = request.getParameter("BizNo");
	String strRiskCode = request.getParameter("RiskCode");
	String strDamageDate = request.getParameter("damageDate");
	String strCoreURL = AppConfig.get("sysconst.Core_URL");
	String strCommonRisk = "";
	String strURL = "";

	DBPrpDrisk dbPrpDrisk = new DBPrpDrisk();
	int intFlag = dbPrpDrisk.getInfo(strRiskCode);
	strCommonRisk = strRiskCode;
	BLPolicyFacade blPolicyFacade = new BLPolicyFacade();
	String strPolicySort = blPolicyFacade.findPrpCmainDtoByPrimaryKey(strBizNo).getPolicySort();
	if (dbPrpDrisk.getFlag().length() >= 3) {
		//通用险种（财产）：00Q1
		if (dbPrpDrisk.getFlag().substring(1, 3).equals("Q1")) {
			//strCommonRisk = SysConfig.getProperty("COMMONRISKQ1").trim();
			strCommonRisk = "00Q1";
		}
		//通用险种（责任1）：00Z1
		else if (dbPrpDrisk.getFlag().substring(1, 3).equals("Z1")) {
			//strCommonRisk = SysConfig.getProperty("COMMONRISKZ1").trim();
			strCommonRisk = "00Z1";
		}
		//通用险种（责任2）：00Z2
		else if (dbPrpDrisk.getFlag().substring(1, 3).equals("Z2")) {
			//strCommonRisk = SysConfig.getProperty("COMMONRISKZ2").trim();
			strCommonRisk = "00Z2";
		}
		//通用险种（货运1）：00Y1
		else if (dbPrpDrisk.getFlag().substring(1, 3).equals("Y1")) {
			//strCommonRisk = SysConfig.getProperty("COMMONRISKY1").trim();
			strCommonRisk = "00Y1";
		}
		//通用险种（货运2）：00Y2
		else if (dbPrpDrisk.getFlag().substring(1, 3).equals("Y2")) {
			//strCommonRisk = SysConfig.getProperty("COMMONRISKY2").trim();
			strCommonRisk = "00Y2";
		}
		//通用险种（船舶）：00C1
		else if (dbPrpDrisk.getFlag().substring(1, 3).equals("C1")) {
			//strCommonRisk = SysConfig.getProperty("COMMONRISKC1").trim();
			strCommonRisk = "00C1";
		}
		//通用险种（投资型产品）：00T1 added by LanNing 20070924
		else if (dbPrpDrisk.getFlag().substring(1, 3).equals("T1")) {
			strCommonRisk = "00T1";
		}
	}
	//  if("I".equals(strPolicySort)){
	//	  strURL = strCoreURL + "card/tbcbpg/UIPrPoEnCardShow.jsp?BIZTYPE=POLICY&BizNo=" + strBizNo + "&PolicyType=01&SHOWTYPE=SHOW&damageDate="+strDamageDate;
	//    }else{
	//	  strURL = strCoreURL + strCommonRisk +"/tbcbpg/UIPrPoEn"+ strCommonRisk +"Show.jsp?BIZTYPE="+ strBIZTYPE +"&SHOWTYPE=SHOW&BizNo="+ strBizNo+"&RiskCode="+ strRiskCode+"&UserCode="+""+"&myComCode="+strComCode+"&damageDate="+strDamageDate;
	//  }
	if (strDamageDate != null && strDamageDate.length() > 10) {
		strDamageDate = strDamageDate.substring(0, 10);
	}
	strURL = strCoreURL + "policy/browsePolicyForClaim.do?bizNo=" + strBizNo + "&riskCode=" + strRiskCode + "&damagedate=" + strDamageDate + "&systemCode=claim";
	System.err.println(strURL);
	response.setCharacterEncoding("GBK");
	response.sendRedirect(strURL);
%>
<%--<script type="text/javascript">--%>
<%--  window.open('<%=strURL%>','详细信息','width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');--%>
<%--</script>--%>
<%--<jsp:include flush="true" page="<%=strURL%>">--%>
<%--</jsp:include>forward page='<%=strURL%>'>--%>
<%--  <jsp:param name='BIZTYPE' value='<%=strBIZTYPE%>' />--%>
<%--  <jsp:param name='SHOWTYPE'  value='SHOW' />--%>
<%--  <jsp:param name='BizNo'  value='<%=strBizNo%>' />--%>
<%--  <jsp:param name='RiskCode' value='<%=strRiskCode%>' />--%>
<%--  <jsp:param name='UserCode' value='<%=""%>' />--%>
<%--  <jsp:param name='myComCode' value='<%=strComCode%>' />--%>
<%--  </jsp:include>--%>
<%--</jsp:forward>--%>


