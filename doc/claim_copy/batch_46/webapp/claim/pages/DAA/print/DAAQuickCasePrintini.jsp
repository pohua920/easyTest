
<%@page import="java.text.*"%>
<%@page import="com.sinosoft.claim.ui.control.action.*"%>
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="com.sinosoft.claim.dto.domain.*"%> 
<%@page import="com.sinosoft.claim.util.*"%> 
<%@page import="com.sinosoft.sysframework.common.util.*"%> 
<%@page import="com.sinosoft.sysframework.common.datatype.DateTime"%> 
<%@page import="com.sinosoft.sysframework.exceptionlog.*"%> 
<%@page import="com.sinosoft.utility.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.claim.common.ConstantCodes"%>
<%
	String strInsuredName = "";
	String strlicenseNo = "";
	String strReportorName = "";
	String strReportDate = "";
	String strReportPhoneNo = "";
	String strDamageDate = "";
	String strDamageAddress = "";
	String strDamageReason = "";
	String strDriverName = "";
	String strCode = "";
	String strName = "";
	String strDamageCodeCheck = "";
	String strLicenseNoCheck = "";
	String registNo = "";
	String strDriverNameCheck = "";
	String strclaimTypeFlag = "";
	String strBrandName = "";
	// String strpolicyNo="";
	String strFrameNo = "";
	String CheckSite = "";
	String strregistRpolicyNo = "";
	String strVINNoCheck = "";
	String strclaimNo = "";
	String strCompelclaimNo = "";
	String registRclaimNo = "";
	String strDamageCode = "";
	String strPolicyNo = "";
	String strCompelPolicyNo = "";
	String strDamageText = "";//³öÏÕÕªÒª
	String strIndemnityDuty = "";
	RegistDto registDto = null;
	CheckDto checkDto = null;
	QuickCaseDto quickCaseDto = null;
	String strEnginNoCheck = "";
	ArrayList prpLRegistRPolicyList = null;
	//list1=(ArrayList)registDto.getPrpLRegistRPolicyList();
	prpLRegistRPolicyList = (ArrayList) request.getAttribute("PrpLRegistRPolicyList");

	registDto = (RegistDto) request.getAttribute("registDto");
	quickCaseDto = (QuickCaseDto) request.getAttribute("quickCaseDto");
	checkDto = (CheckDto) request.getAttribute("checkDto");
	PrpLRegistRPolicyDto prpLRegistRPolicyDto = null;
	if (prpLRegistRPolicyList != null) {
		for (int i = 0; i < prpLRegistRPolicyList.size(); i++) {
			prpLRegistRPolicyDto = (PrpLRegistRPolicyDto) prpLRegistRPolicyList.get(i);
			if (ConstantCodes.RISKCODE_DAA.equals(prpLRegistRPolicyDto.getRiskCode())) {
				strclaimNo = prpLRegistRPolicyDto.getClaimNo();
				strPolicyNo = prpLRegistRPolicyDto.getPolicyNo();
			}
			if (ConstantCodes.RISKCODE_DAZ.equals(prpLRegistRPolicyDto.getRiskCode())) {
				strCompelclaimNo = prpLRegistRPolicyDto.getClaimNo();
				strCompelPolicyNo = prpLRegistRPolicyDto.getPolicyNo();
			}
		}

		// strCompelclaimNo = registDto.getPrpLRegistRPolicyDtoOfCompel().getClaimNo();
		//strclaimNo = registDto.getPrpLRegistRPolicyDto().getClaimNo();
		// strCompelPolicyNo = registDto. getPrpLRegistRPolicyDtoOfCompel().getPolicyNo();
		// strPolicyNo = registDto.getPrpLRegistRPolicyDto().getPolicyNo();

	}

	System.out.println(">>>>>>>>>>>>>11111" + strCompelclaimNo + strclaimNo + strCompelPolicyNo + strPolicyNo);
	/*if(quickCaseDto!=null){
	strCompelclaimNo= quickCaseDto.getRegistRclaimNo();
	strclaimNo      = quickCaseDto.getClaimNo();
	strCompelPolicyNo = quickCaseDto.getRegistRpolicyNo();
	strPolicyNo = quickCaseDto.getPolicyNo();
	System.out.println(">>>>>>>>>>>>>"+strCompelclaimNo+strclaimNo+strCompelPolicyNo+strPolicyNo);
	}*/
	if (registDto.getPrpLregistDto() != null) {

		strInsuredName = registDto.getPrpLregistDto().getInsuredName();
		strlicenseNo = registDto.getPrpLregistDto().getLicenseNo();
		strReportorName = registDto.getPrpLregistDto().getReportorName();
		strReportDate = registDto.getPrpLregistDto().getReportDate() + "";
		strDamageDate = registDto.getPrpLregistDto().getDamageStartDate() + "";
		strDamageAddress = registDto.getPrpLregistDto().getDamageAddress();
		strReportPhoneNo = registDto.getPrpLregistDto().getReportorPhoneNumber();
		strDamageCode = registDto.getPrpLregistDto().getDamageCode();
	}

	//strpolicyNo    = quickCaseDto.getPolicyNo();
	strregistRpolicyNo = quickCaseDto.getRegistRpolicyNo();
	// strclaimNo     = quickCaseDto.getClaimNo();

	//registRclaimNo = quickCaseDto.getRegistRclaimNo();
	//strDamageCode  = registDto.getPrpLregistDto().getDamageCode();
	ArrayList driverlist = null;
	driverlist = (ArrayList) registDto.getPrpLdriverDtoList();
	if (driverlist.size() > 0) {
		PrpLdriverDto driverDto = (PrpLdriverDto) driverlist.get(0);
		strDriverName = driverDto.getDriverName();
	}
	PrpLthirdPartyDto thirdPartyDto = null;
	List list = null;
	list = registDto.getPrpLthirdPartyDtoList();
	if (list.size() > 0) {
		for (int i = 0; i < list.size(); i++)
			thirdPartyDto = (PrpLthirdPartyDto) list.get(i);
		if ("1".equals(thirdPartyDto.getInsureCarFlag())) {

			//strdriverPhone = thirdPartyDto.getPrpLdriverDto()
		}
	}
	List checkList = null;
	PrpLthirdPartyDto thirdPartyDtocheck = null;
	checkList = checkDto.getPrpLthirdPartyDtoList();
	ArrayList driverlistCheck = null;
	driverlistCheck = (ArrayList) checkDto.getPrpLdriverDtoList();
	if (driverlistCheck.size() > 0) {
		PrpLdriverDto driverDtoCheck = (PrpLdriverDto) driverlist.get(0);
		strDriverNameCheck = driverDtoCheck.getDriverName();

	}
	if (checkList.size() > 0) {
		for (int i = 0; i < checkList.size(); i++)
			thirdPartyDtocheck = (PrpLthirdPartyDto) checkList.get(i);
		if ("1".equals(thirdPartyDtocheck.getInsureCarFlag())) {

			strLicenseNoCheck = thirdPartyDtocheck.getLicenseNo();
			strBrandName = thirdPartyDtocheck.getBrandName();
			strFrameNo = thirdPartyDtocheck.getFrameNo();

			strVINNoCheck = thirdPartyDtocheck.getVINNo();
			strEnginNoCheck = thirdPartyDtocheck.getEngineNo();
		}
	}
	PrpLcheckDto prplcheckDto = null;
	prplcheckDto = checkDto.getPrpLcheckDto();
	if (prplcheckDto != null) {
		strDamageCodeCheck = prplcheckDto.getDamageCode();
		CheckSite = prplcheckDto.getCheckSite();
	}
	ArrayList registTextList = null;
	registTextList = registDto.getPrpLregistTextDtoList();
	if (registTextList != null) {
		for (int i = 0; i < registTextList.size(); i++) {
			strDamageText = strDamageText + ((PrpLregistTextDto) registTextList.get(i)).getContext();
		}

	}
	String strDeduLossRate = "0.00";
	String strClaimRate = "0.0";
	String strDeduLossFee = "0.00";
	String strLossRate = "0.00";
	PrpLlossDto prpLlossDto = null;
	prpLlossDto = (PrpLlossDto) request.getAttribute("prpLlossDto");
	if (prpLlossDto != null) {
		strLossRate = new DecimalFormat("#,##0.00").format(prpLlossDto.getDutyDeductibleRate());
		strDeduLossRate = new DecimalFormat("#,##0.00").format(prpLlossDto.getMainKindDeductibleRate());
		strClaimRate = new DecimalFormat("#,##0.00").format(prpLlossDto.getClaimRate());
		strDeduLossFee = new DecimalFormat("#,##0.00").format(prpLlossDto.getDeductible());
	}
	strIndemnityDuty = (String) request.getAttribute("indemnityDuty");
	String strInsureComName = (String) request.getAttribute("insureComName");
	String strDutydutyName = (String) request.getAttribute("dutyName");
	String carKind = (String) request.getAttribute("carKind");
	System.out.println("comName" + strInsureComName);
	//compensateDto = (PrpLcompensateDto)request.getAttribute("CompelcompensateDto");
	// compelcompensateDto = (PrpLcompensateDto)request.getAttribute("compensateDto");
	//PrpLCItemCarDto PrpCitemCarDto =
	//strCode = StringConvert.encode(PrpCitemCarDto.getUseNatureCode());
	// strName = uiCodeAction.translateCodeCode("UseNature",strCode,isChinese);
%>
