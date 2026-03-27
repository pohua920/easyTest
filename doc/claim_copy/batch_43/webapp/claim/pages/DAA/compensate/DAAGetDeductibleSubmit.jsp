<%--   
****************************************************************************
* DESC       ¡êo ?¡éD?2????a?a
* AUTHOR     ¡êo ¨¤¨ª?a¡Á¨¦
* CREATEDATE ¡êo 2013-03-31
* MODIFYLIST ¡êo   Name       Date            Reason/Contents
****************************************************************************
--%>
<%-- ¨°y¨¨?bean¨¤¨¤2?¡¤? --%>
<%@page import="com.sinosoft.claim.common.service.facade.PolicyService"%>
<%@page import="com.sinosoft.claim.common.util.EndorseViewHelper"%>
<%@page import="com.sinosoft.claim.compensate.util.DAACompensateViewHelper"%>
<%@page import="com.sinosoft.claim.common.vo.PolicyDto"%>
<%@page import="ins.framework.common.ServiceFactory"%>
<%@page import="com.sinosoft.claim.schema.model.PrpCitemKind"%>
<%@page import="com.sinosoft.claim.schema.model.PrpCitemCar"%>
<%@page import="com.sinosoft.claim.compensate.util.UIDeductCondAction"%>
<%@page import="java.util.*"%>
<script>
<% 
   PolicyService policyService = (PolicyService) ServiceFactory.getService("policyService");
   EndorseViewHelper endorseViewHelper = (EndorseViewHelper)ServiceFactory.getService("endorseViewHelper");
   DAACompensateViewHelper daaCompensateViewHelper = (DAACompensateViewHelper)ServiceFactory.getService("daaCompensateViewHelper");
  
	String strClauseTypeCode = request.getParameter("prpLcompensateClauseTypeCode");
	String Index = request.getParameter("Index");
	String strIndemnityDuty = request.getParameter("prpLcompensateIndemnityDuty");
	String strDeductibleTerm = request.getParameter("prpLcompensateDeductCond");
	String strEscapeFlag = request.getParameter("prpLcompensateEscapeFlag");
	String strRiskCode = request.getParameter("prpLcompensateRiskCode");
	String[] arrLossKindCode = request.getParameterValues("prpLlossDtoKindCode");
	String[] arrLossRealPay = request.getParameterValues("prpLlossDtoSumRealPay");
	String[] arrLossFeeTypeCode = request.getParameterValues("prpLlossDtoFeeTypeCode");
	String[] arrLDeductibleRate = request.getParameterValues("prpLlossDtoDeductibleRate");

	String[] arrPersonNo2 = request.getParameterValues("personLossSerialNo"); // ?¨²2?
	String[] arrPersonNo1 = request.getParameterValues("prpLpersonLossPersonNo"); // ¨ªa2?¡ê???¨¨??¡À¦Ì?D¨°o?
	String[] arrPDeductibleRate = request.getParameterValues("prpLpersonLossDeductibleRate");
	String[] arrKindCode = request.getParameterValues("prpLpersonLossKindCode");
	String[] arrRealpay = request.getParameterValues("prpLpersonLossSumRealPay");

	String strPolicyNo = request.getParameter("PolicyNo");
	String strDamageStartDate = request.getParameter("DamageStartDate");
	String strFlag = "";
	double dblPDeductibleRate = 0;
	double dblDeductibleRate = 0;
	double dblDeductible = 0;
	double dblDeductibleTmp = 0;
	int intCount = 0;

	try {
		PolicyDto policyDto = endorseViewHelper.findForEndorBefore(strPolicyNo, strDamageStartDate); // findForEndorBeforeCar??
		List<PrpCitemKind> prpCitemKindList = policyDto.getPrpCitemKindList();
		for (int i = 1; i < arrLossKindCode.length; i++) {
			strFlag = "";
			if (arrLossKindCode[i].equals("M") || arrLossFeeTypeCode[i].equals("27")) {
				continue;
			}
			for (int j = 0; j < prpCitemKindList.size(); j++) {
				PrpCitemKind prpCitemKind = prpCitemKindList.get(j);
				if (prpCitemKind.getFlag().length() > 4 && prpCitemKind.getFlag().substring(4, 5).equals("1") && arrLossKindCode[i].equals(prpCitemKind.getKindCode())) {
					strFlag = prpCitemKind.getFlag().substring(4, 5);
					break;
				}
			}
			if (strFlag.equals("1")) {
				dblDeductibleRate = daaCompensateViewHelper.getRate(strClauseTypeCode, arrLossKindCode[i], strIndemnityDuty, strDeductibleTerm, strEscapeFlag, strRiskCode); // getrate1()?
				if (Double.parseDouble(arrLDeductibleRate[i]) != 100) {
					dblDeductibleTmp = Double.parseDouble(arrLossRealPay[i]) * dblDeductibleRate / 100 / (1 - Double.parseDouble(arrLDeductibleRate[i]) / 100);
				}
				dblDeductible = dblDeductible + dblDeductibleTmp;
			}
		}
		for (int i = 1; i < arrRealpay.length; i++) {
			strFlag = "";
			for (int j = 0; j < prpCitemKindList.size(); j++) {
				for (int k = 1; k < arrKindCode.length; k++) {
					PrpCitemKind prpCitemKind = prpCitemKindList.get(j);
					if (prpCitemKind.getFlag().length() > 4 && prpCitemKind.getFlag().substring(4, 5).equals("1") && arrKindCode[k].equals(prpCitemKind.getKindCode())) {
						strFlag = prpCitemKind.getFlag().substring(4, 5);
						break;
					}
				}
			}
			if (strFlag.equals("1")) {
				dblDeductibleRate = daaCompensateViewHelper.getRate(strClauseTypeCode, arrKindCode[i], strIndemnityDuty, strDeductibleTerm, strEscapeFlag, strRiskCode);
				dblPDeductibleRate = Double.parseDouble(arrPDeductibleRate[i]) / 100;

				if (dblPDeductibleRate != 100) {
					dblDeductibleTmp = Double.parseDouble(arrRealpay[i]) * dblDeductibleRate / 100 / (1 - dblPDeductibleRate);
				}
				dblDeductible = dblDeductible + dblDeductibleTmp;
			}
		}
		out.println("window.status='';");
	} catch (Exception e) {
		e.printStackTrace();
		out.println("window.status='?¡éD?2????a?a¨º¡ì¡ã¨¹';");
	}
%>
  var tempFrame = parent.document.frames("fraInterface");
  tempFrame.fm.target="interface";
  if(tempFrame.fm.prpLlossDtoKindCode[<%= Index %>].value == "M")  
  {
    var prpLcompensateSumDutyPaid = parseFloat(tempFrame.fm.prpLcompensateSumDutyPaid.value) - parseFloat(tempFrame.fm.prpLlossDtoSumRealPay[<%= Index %>].value);
    var prpLcompensateSumPaid     = parseFloat(tempFrame.fm.prpLcompensateSumPaid.value) - parseFloat(tempFrame.fm.prpLlossDtoSumRealPay[<%= Index %>].value);
    var prpLcompensateSumThisPaid = parseFloat(tempFrame.fm.prpLcompensateSumThisPaid.value) - parseFloat(tempFrame.fm.prpLlossDtoSumRealPay[<%= Index %>].value);
    tempFrame.fm.prpLlossDtoSumRealPay[<%= Index %>].value  = "<%=dblDeductible%>"; 
    tempFrame.fm.prpLcompensateSumDutyPaid.value  = parseFloat(prpLcompensateSumDutyPaid) + parseFloat(tempFrame.fm.prpLlossDtoSumRealPay[<%= Index %>].value);  
    tempFrame.fm.prpLcompensateSumPaid.value      = parseFloat(prpLcompensateSumPaid) + parseFloat(tempFrame.fm.prpLlossDtoSumRealPay[<%= Index %>].value);  
    tempFrame.fm.prpLcompensateSumThisPaid.value  = parseFloat(prpLcompensateSumThisPaid) + parseFloat(tempFrame.fm.prpLlossDtoSumRealPay[<%= Index %>].value);  
    <%
      //System.out.println("---?¡éD?2????a?a¦Ì??-??---dblDeductible----"+dblDeductible);
    %>
	}
</script>
