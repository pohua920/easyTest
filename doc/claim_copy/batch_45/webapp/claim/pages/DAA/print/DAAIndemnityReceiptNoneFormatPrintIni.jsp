<%--
****************************************************************************
* DESC       ：机动车辆保险赔款收据打印页面初始化
* AUTHOR     ：caopeng
* CREATEDATE ：2005-12-02
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
<%@page import="java.util.ArrayList"%>
<jsp:directive.page import="com.sinosoft.claim.bl.facade.BLPrpCcoinsFacade"/>

<%
	//变量声明部分
	String strCompensateNo = request.getParameter("CompensateNo"); //赔款计算书号
	String strClaimNo = ""; //赔案号
	String strRegistNo = ""; //报案号
	String strPolicyNo = ""; //保单号
	String strSumPaid = ""; //
	String strCSumPaid = ""; //
	double dblSumPaid = 0; //
	double dblSumThisPaid = 0; //责任赔款(不包括不计入赔款的费用)
	double dblSumPrePaid = 0;
	String strSumThisPaid = "";
	String strSumPrePaid = "";
	String strCSumThisPaid = "";
	String strCSumPrePaid = "";
	String strChargeCode = "";
	String strInsuredName = ""; //被保险人
	String strInsuredCode = ""; //被保险人代码
	String strYear = "";
	String strMonth = "";
	String strDate = "";
	String strComCode = ""; //业务归属机构代码 
	String strComCName = ""; //业务归属机构中文名称
	//代码翻译变量
	String strCode = "";
	String strName = "";
	boolean isChinese = true; //中文标志
	String riskCodeName = "";

	String strUnitBank = ""; //收款单位银行
	String strUnitAccount = ""; //收款单位帐号
	String strAccount = ""; //收款人银行帐号
	String strIdentifyNumber = ""; //收款人身份证号
	String strDamageDate = "";//出险日期
	String strCurrency = "";//币别
	String strLossName = "";//受损标的
	String strCoinFlagPring = request.getParameter("coinFlag");//共保时打印总金额还是分摊後金额标示位
	System.out.println("strCoinFlagPring==" + strCoinFlagPring);

	//对象定义部分

	PrpLclaimDto prpLclaimDto = null; //ClaimDto对象
	PrpLcompensateDto prpLcompensateDto = null; //CompensateDto对象
	PrpCmainDto prpMainDto = null; //CMainDto对象
	PrpLregistDto prpLregistDto = null;

	int index = 0;

	//得到ClaimDto,RegistDto,CertainLossDto,PolicyDto对象
	ClaimDto claimDto = (ClaimDto) request.getAttribute("claimDto");
	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");
	CompensateDto compensateDto = (CompensateDto) request.getAttribute("compensateDto");
	RegistDto registDto = (RegistDto) request.getAttribute("registDto");
	PrpLIndemnityReceiptDto prpLIndemnityReceiptDto = (PrpLIndemnityReceiptDto) request.getAttribute("prpLIndemnityReceiptDto");

	prpLregistDto = registDto.getPrpLregistDto();
	String strLicenseNo = prpLregistDto.getLicenseNo();

	UICodeAction uiCodeAction = new UICodeAction();
	//得到prpLcompensateDto 对象
	prpLcompensateDto = compensateDto.getPrpLcompensateDto();
	ArrayList prpLchargeDtoList = new ArrayList();
	PrpLchargeDto prpLchargeDto = null;
	String sumChargeAmount = "";
	double dblSumChargeAmount = 0;
	prpLchargeDtoList = compensateDto.getPrpLchargeDtoList();
	if (prpLchargeDtoList.size() > 0) {
		for (int j = 0; j < prpLchargeDtoList.size(); j++) {
			prpLchargeDto = (PrpLchargeDto) prpLchargeDtoList.get(j);
			//modify by wangliguang begin
			if ("B".equals(prpLchargeDto.getPayObjectType()) && (!"03".equals(prpLchargeDto.getChargeCode()))) {
				//modify by wangliguang end 
				//modify by liping 2008-0424
				//车险理赔费用类型为外部时，均在赔款收据中体现  
				if (prpLchargeDto.getSumRealPay() > 0) {
				} else {
					dblSumChargeAmount = dblSumChargeAmount + prpLchargeDto.getChargeAmount();
				}
			}
		}
	}

	//得到prpLclaimDto对象
	prpLclaimDto = claimDto.getPrpLclaimDto();
	if (policyDto != null) {
		prpMainDto = policyDto.getPrpCmainDto();
		if (prpMainDto != null) {
			strComCode = StringConvert.encode(prpMainDto.getComCode());
			strComCode = strComCode.substring(0, 7) + "000";
			UICompanyAction uiCompanyAction = new UICompanyAction();
			PrpDcompanyDto prpDcompanyDto = uiCompanyAction.findByPrimaryKey(strComCode);
			if (prpDcompanyDto != null)
				strComCName = prpDcompanyDto.getComCName();
			if (strComCName.length() > 2)
				strComCName = strComCName.substring(0, strComCName.length() - 2);

		}
	}

	java.util.Date date = new java.util.Date();
	strYear = String.valueOf(date.getYear() + 1900);
	strMonth = String.valueOf(date.getMonth() + 1);
	strDate = String.valueOf(date.getDate());
	//得到保单号
	strPolicyNo = prpLclaimDto.getPolicyNo();
	strDamageDate = prpLclaimDto.getDamageStartDate().toString();
	strCurrency = prpLclaimDto.getCurrency();
	String estiCurrencyName = uiCodeAction.translateCurrencyCode(strCurrency, true);
	strLossName = prpLclaimDto.getLossName();
	String strcomCode1 = prpLclaimDto.getComCode();
	UICodeAction CodeAction = UICodeAction.getInstance();
	// String comName1 = CodeAction.translateComCode(strcomCode1, true);
	String comName1 = "";
	if (strcomCode1.substring(0, 2).equals("11")) {
		comName1 = "北京";
	} else if (strcomCode1.substring(0, 2).equals("31")) {
		comName1 = "上海";
	} else if (strcomCode1.substring(0, 2).equals("44")) {
		if (strcomCode1.substring(0, 4).equals("4403")) {
			comName1 = "深圳";
		} else {
			comName1 = "广州";
		}
	} else if (strcomCode1.substring(0, 2).equals("14")) {
		comName1 = "山西";
	} else if (strcomCode1.substring(0, 2).equals("23")) {
		comName1 = "黑龙江";
	} else if (strcomCode1.substring(0, 2).equals("52")) {
		comName1 = "贵州";
	} else if (strcomCode1.substring(0, 2).equals("43")) {
		comName1 = "湖南";
	} else if (strcomCode1.substring(0, 2).equals("41")) {
		comName1 = "河南";
	} else if (strcomCode1.substring(0, 2).equals("35")) {
		if (strcomCode1.substring(0, 4).equals("3502")) {
			comName1 = "厦门";
		} else {
			comName1 = "福建";
		}
	} else if (strcomCode1.substring(0, 2).equals("33")) {
		if (strcomCode1.substring(0, 4).equals("3302")) {
			comName1 = "宁波";
		} else {
			comName1 = "浙江";
		}
	} else if (strcomCode1.substring(0, 2).equals("32")) {
		comName1 = "江苏";
	}

	riskCodeName = uiCodeAction.translateRiskCode(prpLclaimDto.getRiskCode(), true);

	strInsuredName = StringConvert.encode(prpLregistDto.getInsuredName());

	if (strInsuredName.length() > 10) {

		strInsuredName = strInsuredName.substring(0, 9) + "<br>" + strInsuredName.substring(9, strInsuredName.length());

	}
	strInsuredCode = StringConvert.encode(prpLregistDto.getInsuredCode());
	if (prpLcompensateDto != null) {
		dblSumPaid = prpLcompensateDto.getSumPaid();
		//modify by wangliguang begin
		dblSumThisPaid = prpLcompensateDto.getSumThisPaid() + dblSumChargeAmount;
		dblSumPrePaid = prpLcompensateDto.getSumPrePaid();
		//modify by wangliguang end
		if ("0".equals(strCoinFlagPring) && !"0".equals(prpMainDto.getCoinsFlag())) {
			double coinsRate = 1;
			ArrayList PrpCcoinsDtoList = (ArrayList) new BLPrpCcoinsFacade().findByConditions(" policyNO='" + prpLcompensateDto.getPolicyNo() + "' and coinsType='1' ", 0, 20);
			if (null != PrpCcoinsDtoList && PrpCcoinsDtoList.size() > 0) {
				PrpCcoinsDto prpCcoinsDto = (PrpCcoinsDto) PrpCcoinsDtoList.get(0);
				coinsRate = prpCcoinsDto.getCoinsRate() / 100;
			}
			dblSumThisPaid = dblSumThisPaid * coinsRate;
			dblSumPrePaid = dblSumPrePaid * coinsRate;
		}
		strClaimNo = prpLcompensateDto.getClaimNo();
		if (prpLIndemnityReceiptDto != null) {
			strUnitBank = prpLIndemnityReceiptDto.getUnitBank(); //收款单位银行
			strUnitAccount = prpLIndemnityReceiptDto.getUnitAccount();
			strAccount = prpLIndemnityReceiptDto.getAccount(); //收款人银行帐号
			strIdentifyNumber = prpLIndemnityReceiptDto.getIdentifyNumber(); //收款人身份证号
		}
	}
	strSumPaid = new DecimalFormat("#,##0.00").format(dblSumPaid);
	if (dblSumPaid < 0) {
		strCSumPaid = "负" + MoneyUtils.toChinese(-dblSumPaid, prpLcompensateDto.getCurrency());

	} else {

		strCSumPaid = MoneyUtils.toChinese(dblSumPaid, prpLcompensateDto.getCurrency());
	}
	strSumThisPaid = new DecimalFormat("#,##0.00").format(dblSumThisPaid);
	strSumPrePaid = new DecimalFormat("#,##0.00").format(dblSumPrePaid);
	if (dblSumThisPaid < 0 || dblSumPrePaid < 0) {
		strCSumThisPaid = "负" + MoneyUtils.toChinese(-dblSumThisPaid, prpLcompensateDto.getCurrency());
		strCSumPrePaid = "负" + MoneyUtils.toChinese(-dblSumPrePaid, prpLcompensateDto.getCurrency());
	} else {

		strCSumThisPaid = MoneyUtils.toChinese(dblSumThisPaid, prpLcompensateDto.getCurrency());
		strCSumPrePaid = MoneyUtils.toChinese(dblSumPrePaid, prpLcompensateDto.getCurrency());
	}

	String strCustomBankName = prpLcompensateDto.getCustomBankName();//开户行名称
	String strAccountCode = prpLcompensateDto.getAccountCode();//银行帳号
	String strOwnerName = prpLcompensateDto.getOwnerName();//帳号归属人名称
	String strCertifiCateCode = prpLcompensateDto.getCertifiCateCode();//帳号归属人证件号
	String strOwnerPhoneNo = prpLcompensateDto.getOwnerPhoneNo();//帳号归属人电话号
	String strOperatorName = uiCodeAction.translateUserCode(prpLcompensateDto.getOperatorCode(), isChinese);//理算人员名称
	DateTime strOperatorDate = prpLcompensateDto.getInputDate();//理算时间
%> 



  