<%--         
******************************************************************************
* DESC       :  关联 
* AUTHOR     ： 
* CREATEDATE :  2004-07-12
* MODIFYLIST ：  Name       Date            Reason/Contents
*          --------------------------------------------------------------------
*               
******************************************************************************
--%>

<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app" %>
<%@page import="com.sinosoft.claim.util.*"%>
<%@page import="com.sinosoft.claim.ui.control.action.*"%>

<%
	/*
	 程序思路：
	 ===========================
	 [1]根据保单号码从个个相关类别中查询出关联的号码，提供给用户选择
	 [2]根据用户所选单证的来源，进行相应内容的查看。
	 */

	//定义变量
	String PolicyNo = "";
	String RiskCode = "";
	//查询保单号
	DBPrpCmain dbPrpCmain = new DBPrpCmain();
	//查询头
	BLPrpPhead blPrpPhead = new BLPrpPhead();
	//查立案
	BLPrpLclaim blPrpLclaim = new BLPrpLclaim();
	//查预赔
	BLPrpLprepay blPrpLprepay = new BLPrpLprepay();
	//查实赔
	BLPrpLcompensate blPrpLcompensate = new BLPrpLcompensate();
	//查再保
	BLFcoRepolicy blFcoRepolicy = new BLFcoRepolicy(); //再保分保保单

	//开始做查询条件
	String strCondition = "";
	int i = 0;
	String strBizNo = "";
	//接收保单号
	PolicyNo = request.getParameter("PolicyNo");
	strCondition = "PolicyNo='" + PolicyNo + "'";

	dbPrpCmain.getInfo(PolicyNo);
	RiskCode = dbPrpCmain.getRiskCode();
	blPrpPhead.query(strCondition);
	blPrpLclaim.query(strCondition);
	blPrpLprepay.query(strCondition);
	blPrpLcompensate.query(strCondition);
	blFcoRepolicy.query(strCondition); //查询再保分保保单
%>
<html>
<head>
<title><s:text name="title.pubBeforeEdit.displayLinkPage" /></title>
<%--显示关联页面--%>
<script language="javascript">
	   
	  //显示投保单
	  function showProposal(strProposalNo)
	  {
	     window.location = "/claim/<%=RiskCode%>/tbcbpg/UIPrPoEn<%=RiskCode%>Show.jsp?"
	                     + "<%=SysConst.getProperty("BIZTYPE")%>=<%=SysConst.getProperty("BIZTYPE_PROPOSAL")%>"
	                     + "&BizNo=" + strProposalNo
	                     + "&<%=SysConst.getProperty("SHOWTYPE")%>=<%=SysConst.getProperty("SHOWTYPE_SHOW")%>";  
	  }
	  //显示保单
	  function showPolicy(strPolicyNo)
	  {
	     window.location = "/claim/<%=RiskCode%>/tbcbpg/UIPrPoEn<%=RiskCode%>Show.jsp?"
	                     + "<%=SysConst.getProperty("BIZTYPE")%>=<%=SysConst.getProperty("BIZTYPE_POLICY")%>"
	                     + "&BizNo=" + strPolicyNo
	                     + "&<%=SysConst.getProperty("SHOWTYPE")%>=<%=SysConst.getProperty("SHOWTYPE_SHOW")%>";
	
	  }
	  //显示批单
	  function showEndorse(strEndorseNo)
	  {
	     window.location = "/claim/<%=RiskCode%>/tbcbpg/UIPrPoEn<%=RiskCode%>Show.jsp?"
	                     + "<%=SysConst.getProperty("BIZTYPE")%>=<%=SysConst.getProperty("BIZTYPE_ENDORSE")%>"
	                     + "&BizNo=" + strEndorseNo
	                     + "&<%=SysConst.getProperty("SHOWTYPE")%>=<%=SysConst.getProperty("SHOWTYPE_SHOW")%>";    
	  }
	  //显示收费登记
	  function showJpayRec()
	  {
	  
	  }
	  //显示付费
	  function showJrefRec()
	  {
	  
	  }
	  //显示立案
	  function showClaim(strClaimNo)
	  {
	   <%UICodeAction uiCodeAction = new UICodeAction();
			String riskInnerCode = uiCodeAction.translateRiskCodetoInnerCode(RiskCode);
			if (("DAE").equals(riskInnerCode)) {
				RiskCode = BusinessRuleUtil.getOuterCode(request, "RISKCODE_DAA");
			}%>
	    window.location = "/claim/<%=RiskCode%>/lp/claim/UIL<%=RiskCode%>ClaimShow.jsp?"
	    								+ "ClaimNo=" + strClaimNo;
	  }
	  //显示预赔计算书
	  function showPrepay(strPreCompensateNo)
	  {
	    <%String riskInnerCode1 = uiCodeAction.translateRiskCodetoInnerCode(RiskCode);
			if (("DAE").equals(riskInnerCode1)) {
				RiskCode = BusinessRuleUtil.getOuterCode(request, "RISKCODE_DAA");
			}%>
	    window.location = "/claim/<%=RiskCode%>/lp/prepay/UIL<%=RiskCode%>PrepayShow.jsp?"
		        					+ "PreCompensateNo=" + strPreCompensateNo;
	  }
	  //显示计算书
	  function showCompensate(strCompensateNo)
	  {
	   <%String riskInnerCode2 = uiCodeAction.translateRiskCodetoInnerCode(RiskCode);
			if (("DAE").equals(riskInnerCode2)) {
				RiskCode = BusinessRuleUtil.getOuterCode(request, "RISKCODE_DAA");
			}%>
	    window.location = "/claim/<%=RiskCode%>/lp/compensate/UIL<%=RiskCode%>CompensateShow.jsp?"
	    								+ "CompensateNo=" + strCompensateNo; 
	  }
	  //显示再保分保摘要
	  //原因：加入再保分保信息显示
	  function showFcoAbstract(strRePolicyNo)
	  {
	    window.location = "/claim/common/pub/UILFcoAbstractShow.jsp?"
	    								+ "RePolicyNo=" + strRePolicyNo; 
	  }
	  </script>
</head>
<%-- 调用loadForm 初始化页面 --%>
<body class="interface" background="/claim/common/images/bgCommon.gif">
	<table class="common" align="center" cellspacing="3" cellpadding="5">
		<tr>
			<td class="formtitle" colspan="2">
				<s:text name="pub.correlationInfo" />
			</td>
			<%--关联信息--%>
		</tr>
		<tr class="listodd">
			<td width="10%">
				<s:text name="pub.insuranApplicat" />
				</a>
			</td>
			<%--投保单--%>
			<td width="90%" align="left">
				<a href="javascript:showProposal('<%=dbPrpCmain.getProposalNo()%>')"><%=dbPrpCmain.getProposalNo()%></a>
			</td>
		</tr>
		<tr class="listodd">
			<td width="10%">
				<s:text name="pub.insurancePolicy" />
				</a>
			</td>
			<%--保单--%>
			<td width="90%" align="left">
				<a href="javascript:showPolicy('<%=PolicyNo%>')"><%=PolicyNo%></a>
			</td>
		</tr>
		<tr class="listodd">
			<td width="10%">
				<s:text name="pub.batchSheet" />
			</td>
			<%--批单--%>
			<td width="90%" align="left">
				<%
	for (i = 0; i < blPrpPhead.getSize(); i++) {
		strBizNo = blPrpPhead.getArr(i).getEndorseNo();
%>
				<a href="javascript:showEndorse('<%=strBizNo%>')"><%=strBizNo%></a>
				<%
	}
%>
			</td>
		</tr>
		<!--<tr class="listodd">
	      <td><a href="javascript:showJpayRec()" >收 费</a></td>
	      <td><a href="javascript:showJrefRec()" >付 费</a></td>
	    </tr>-->
		<tr class="listodd">
			<td width="10%">
				<s:text name="check.record" />
			</td>
			<%--立案--%>
			<td width="90%" align="left">
				<%
	for (i = 0; i < blPrpLclaim.getSize(); i++) {
		strBizNo = blPrpLclaim.getArr(i).getClaimNo();
%>
				<a href="javascript:showClaim('<%=strBizNo%>')"><%=strBizNo%></a>
				<%
	}
%>
			</td>
		</tr>
		<tr class="listodd">
			<td width="10%">
				<s:text name="check.advance" />
			</td>
			<%--预赔--%>
			<td width="90%" align="left">
				<%
	for (i = 0; i < blPrpLprepay.getSize(); i++) {
		strBizNo = blPrpLprepay.getArr(i).getPreCompensateNo();
%>
				<a href="javascript:showPrepay('<%=strBizNo%>')"><%=strBizNo%></a>
				<%
	}
%>
			</td>
		</tr>
		<tr class="listodd">
			<td width="10%">
				<s:text name="pub.realLoss" />
			</td>
			<%--实赔--%>
			<td width="90%" align="left">
				<%
	for (i = 0; i < blPrpLcompensate.getSize(); i++) {
		strBizNo = blPrpLcompensate.getArr(i).getCompensateNo();
%>
				<a href="javascript:showCompensate('<%=strBizNo%>')"><%=strBizNo%></a>
				<%
	}
%>
			</td>
		</tr>
		<tr class="listodd">
			<td width="10%">
				<s:text name="pub.reinsurance" />
			</td>
			<%--再保--%>
			<td width="90%" align="left">
				<%
	//原因：加入再保分保信息显示
	for (i = 0; i < blFcoRepolicy.getSize(); i++) {
		strBizNo = blFcoRepolicy.getArr(i).getRepolicyNo();
%>
				<a href="javascript:showFcoAbstract('<%=strBizNo%>')"><%=strBizNo%></a>
				<%
	}
%>
			</td>
		</tr>
	</table>
</body>
</html>
