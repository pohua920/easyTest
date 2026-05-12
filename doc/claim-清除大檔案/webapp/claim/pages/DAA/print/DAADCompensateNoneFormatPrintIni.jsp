<%--
****************************************************************************
* DESC       ：机动车辆保险赔款计算书打印初始化
* AUTHOR     ：理赔组
* CREATEDATE ：2004-11-16
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>

<%-- 引入bean类部分 --%>
<%@page import="java.text.*"%>
<%@page import="com.sinosoft.claim.ui.control.action.*"%>
<%@page import="com.sinosoft.claim.bl.facade.*"%>
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="com.sinosoft.claim.dto.domain.*"%> 
<%@page import="com.sinosoft.claim.util.*"%> 
<%@page import="com.sinosoft.sysframework.common.util.*"%> 
<%@page import="com.sinosoft.sysframework.common.datatype.DateTime"%> 
<%@page import="com.sinosoft.sysframework.exceptionlog.*"%> 
<%@page import="java.util.*" %>
<%@page import="com.sinosoft.claim.common.ConstantCodes"%>
<%
	//变量声明部分

	String strCompensateNo = request.getParameter("CompensateNo"); //赔款计算书号
	String strClaimNo = ""; //立案号
	String strCaseNo = ""; //赔案号
	String strRegistNo = ""; //报案号
	String strPolicyNo = ""; //保单号
	String strRPolicyNo = "";
	String strLicenseNo = ""; //保单中的号牌号码
	String strLicenseColorCode = ""; //保单中的号牌底色
	String strLicenseNo1 = ""; //理赔车辆信息中的号牌号码
	String strLicenseColorCode1 = ""; //理赔车辆信息中的号牌底色
	String strInsureCarFlag = ""; //是否为本保单车辆
	String strTextType = "";
	String strContext = ""; //赔款计算书文字
	String strContextLaw = ""; //交强文字
	String strDamageStartDate = ""; //出险时间
	String strInsuredDate = ""; //保险期间
	double dblJudgeFee = 0; //
	double dblCheckFee = 0; //
	double dblLawFee = 0; //
	double dblElseFee = 0; //
	double dblSumPrePaid = 0; //
	double dblSumRest = 0; //
	double dblCheckFee1 = 0;
	double dblJianYan = 0;
	double dblAssessFee = 0;
	String strSumThisPaid = ""; //
	String strCSumThisPaid = ""; //
	double dblSumThisPaid = 0; //

	String strSumPaid = ""; //
	String strCSumPaid = ""; //
	double dblSumPaid = 0; //
	String strChargeCode = "";
	double dblSumAmount1 = 0; //保险金额    
	double dblSumAmount2 = 0; //责任限额    
	String strRiskCode = ""; //险种代码     
	String strKindCode = ""; //险别代码     
	String strCalculateFlag = ""; //是否记入保额
	String strMessage = "";
	String strUnderWriteName = "";
	String strApproverCode = "";
	String strOperatorCode = "";
	String strApproverName = "";
	String strOperatorName = "";
	String strLossRate = "";
	String strTitleName = "机动车辆保险赔款计算书";
	String strIndemnityType = "";
	String strKindCodeThird = ""; //判断三者责任限额标志位
	double strAmount = 0; //三者责任限额
	String strAllSumPaid = "";
	String strCAllSumPaid = "";
	String personInjure = "无人员伤亡";

	//代码翻译变量
	String strCode = "";
	String strName = "";
	boolean isChinese = true; //中文标志
	int countNumber = 0;
	int countNumber1 = 0;

	//支付对象变量声明
	String strAccountCode = "";//银行帳号
	String strBankCode = "";//总行代码
	String strBankName = "";//总行名称
	String strCustomBankCode = "";//支行代码
	String strCustomBankName = "";//支行名称
	String strCertifiCateCode = "";//帳户归属人证件代码
	String strOwnerName = "";//帳户归属人名称
	String strOwnerPhoneNo = "";//帳户归属人电话
	String strAccountType = "";//帳户类型
	String strAccountTypeName = "";//帳户类型名称
	String strAccountCurrency = "";//帳户币别
	String strOwnership = "";//支付方式

	String strExceptions = "";//例外事项代码
	String strExceptionsName = "";//例外事项
	String strReason = "";//原因

	//对象定义部分

	PrpCitemCarDto prpItemCarDto = null; //ItemCarDto对象 
	PrpCitemKindDto prpItemKindDto = null; //保单的ItemKindDto对象
	PrpLclaimDto prpLclaimDto = null; //ClaimDto对象
	PrpLthirdPartyDto prpLthirdPartyDto = null; //ThirdPartyDto对象
	PrpLcompensateDto prpLcompensateDto = null; //CompensateDto对象
	PrpLchargeDto prpLchargeDto = null; //ChargeDto对象
	PrpLctextDto prpLctextDto = null;
	PrpLdriverDto prpLdriverDto = null;
	PrpCcarDriverDto prpCcarDriverDto = null;
	PrpLregistDto prpLregistDto = null;
	PrpLpropDto prpLpropDto = null;
	PrpLextDto prpLextDto = null;

	int intCompensateCount = 0; //CompensateDto对象的记录数
	int intItemCarCount = 0; //ItemcarDto对象的记录数
	int intItemKindCount = 0; //ItemKindDto对象的记录数
	int intThirdPartyCount = 0; //ThirdPartyDto对象的记录数
	int intChargeCount = 0; //ChargeDto对象的记录数
	int intCtextCount = 0; //CtextDto对象的记录数
	int intCtextCountTmp = 0; //textarea行数
	int intDriverCount = 0; //DriverDto对象的记录数
	int intCriverCount = 0; //prpCcarDriverDto对象的记录数
	int intPropCount = 0;
	String lossDesc = ""; //损失程度
	String strDriverName1 = "";
	double lawSumPaid = 0;
	String strLawSumPaid = "";
	String strLawCSumPaid = "";
	String strDriverName = "";
	String strDamageHourMS = "";
	String strRunAreaCode = "";
	String strRunAreaName = "";

	int index = 0;

	//得到ClaimDto,RegistDto,CertainLossDto,PolicyDto对象
	ClaimDto claimDto = (ClaimDto) request.getAttribute("claimDto");
	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");
	RegistDto registDto = (RegistDto) request.getAttribute("registDto");
	CheckDto checkDto = (CheckDto) request.getAttribute("checkDto");
	CompensateDto compensateDto = (CompensateDto) request.getAttribute("compensateDto");
	CertainLossDto certainLossDto = (CertainLossDto) request.getAttribute("certainLossDto");

	EndorseDto endorseDto = (EndorseDto) request.getAttribute("endorseDto");

	UICodeAction uiCodeAction = new UICodeAction();
	//得到prpLcompensateDto 对象
	prpLcompensateDto = compensateDto.getPrpLcompensateDto();
	//得到是否是法三计算书标志
	/* if(prpLcompensateDto.getFlag()!=null&&!prpLcompensateDto.getFlag().equals("")){
	    if(prpLcompensateDto.getFlag().substring(3,5)!=null&&prpLcompensateDto.getFlag().substring(3,5).equals("B1")){
	       //strTitleName = "机动车辆保险赔款计算书(法定三者)";
	    }
	 }
	 */

	//得到prpLclaimDto对象
	prpLclaimDto = claimDto.getPrpLclaimDto();

	//得到prpLregistDto对象
	prpLregistDto = registDto.getPrpLregistDto();

	strClaimNo = prpLclaimDto.getClaimNo();
	strRegistNo = prpLclaimDto.getRegistNo();
	strRiskCode = prpLclaimDto.getRiskCode();
	strCaseNo = prpLclaimDto.getCaseNo();
	strIndemnityType = prpLclaimDto.getDamageTypeName();
	if (strCaseNo == null) {
		strCaseNo = "";
	}
	strOperatorCode = prpLcompensateDto.getHandlerCode();
	strOperatorName = uiCodeAction.translateUserCode(strOperatorCode, isChinese);
	DateTime dateTime = new DateTime();
	//String strDateTime = new DateTime(dateTime.current(),dateTime.YEAR_TO_HOUR);

	//得到保单号
	strPolicyNo = prpLclaimDto.getPolicyNo();

	UIPrpLregistRPolicyAction uiPrpLregistRPolicyAction = new UIPrpLregistRPolicyAction();
	Collection collection = uiPrpLregistRPolicyAction.findByConditions("registNo = '" + strRegistNo + "'");
	if (collection != null) {
		for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
			PrpLRegistRPolicyDto prpLRegistRPolicyDto = (PrpLRegistRPolicyDto) iterator.next();
			if (strPolicyNo.equals(prpLRegistRPolicyDto.getPolicyNo()) || strPolicyNo == prpLRegistRPolicyDto.getPolicyNo()) {

			} else {
				strRPolicyNo = prpLRegistRPolicyDto.getPolicyNo();
			}
		}
	}

	//得到blPrpCitemCar对象 
	if (policyDto.getPrpCitemCarDtoList() != null) {
		intItemCarCount = policyDto.getPrpCitemCarDtoList().size();
	}

	//得到blPrpCitemKind对象的记录数
	if (policyDto.getPrpCitemKindDtoList() != null) {
		intItemKindCount = policyDto.getPrpCitemKindDtoList().size();
	}

	//得到blPrpLthirdParty对象的记录数  
	if (claimDto.getPrpLthirdPartyDtoList() != null) {
		intThirdPartyCount = claimDto.getPrpLthirdPartyDtoList().size();
	}

	//得到blPrpLcharge对象的记录数
	if (compensateDto.getPrpLchargeDtoList() != null) {
		intChargeCount = compensateDto.getPrpLchargeDtoList().size();
	}
	//得到dbPrpLctext对象的记录数  
	if (compensateDto.getPrpLctextDtoDtoList() != null) {
		intCtextCount = compensateDto.getPrpLctextDtoDtoList().size();
	}

	//得到损失程度描叙
	if (claimDto.getPrpLthirdCarLossDtoList() != null) {
		for (index = 0; index < claimDto.getPrpLthirdCarLossDtoList().size(); index++) {
			PrpLthirdCarLossDto prpLthirdCarLossDto = (PrpLthirdCarLossDto) claimDto.getPrpLthirdCarLossDtoList().get(0);
			lossDesc = prpLthirdCarLossDto.getLossDesc();
		}
	}
	//add by 罗畅  begin at 2010-09-19 重写预赔信息方法
	int intCompensatePreCount = 0;//预赔次数
	String strClaimNoConditions = " claimno = '" + strClaimNo + "' and underwriteflag = '1'";
	UIPrepayAction uiPrepayAction = new UIPrepayAction();
	Iterator iterator = uiPrepayAction.findByConditions(strClaimNoConditions).iterator();
	while (iterator.hasNext()) {
		intCompensatePreCount++;
		PrpLprepayDto prpLprepayDto = (PrpLprepayDto) iterator.next();
		dblSumPrePaid += prpLprepayDto.getSumPrePaid();
	}

	//add by 罗畅  end at 2010-09-19 重写预赔信息方法
	strAccountCode = prpLcompensateDto.getAccountCode();
	strBankCode = prpLcompensateDto.getBankCode();
	strBankName = prpLcompensateDto.getBankName();
	strCustomBankCode = prpLcompensateDto.getCustomBankCode();
	strCustomBankName = prpLcompensateDto.getCustomBankName();
	strCertifiCateCode = prpLcompensateDto.getCertifiCateCode();
	strOwnerName = prpLcompensateDto.getOwnerName();
	strOwnerPhoneNo = prpLcompensateDto.getOwnerPhoneNo();
	strAccountType = prpLcompensateDto.getAccountType();
	if ("1".equals(strAccountType)) {
		strAccountTypeName = "存折";
	} else if ("2".equals(strAccountType)) {
		strAccountTypeName = "信用卡";
	} else if ("3".equals(strAccountType)) {
		strAccountTypeName = "储值卡";
	} else {
		strAccountTypeName = "其他";
	}
	strAccountCurrency = prpLcompensateDto.getAccountCurrency();
	strOwnership = prpLcompensateDto.getOwnership();
	strExceptions = prpLcompensateDto.getExceptions();
	if ("1".equals(strExceptions)) {
		strExceptionsName = "责任保险第三者";
	} else if ("2".equals(strExceptions)) {
		strExceptionsName = "生效的法院判决或仲裁裁决";
	} else if ("3".equals(strExceptions)) {
		strExceptionsName = "共保业务";
	} else if ("4".equals(strExceptions)) {
		strExceptionsName = "交强险垫付/支付";
	} else if ("5".equals(strExceptions)) {
		strExceptionsName = "支付救助基金";
	} else if ("6".equals(strExceptions)) {
		strExceptionsName = "车辆过户";
	} else if ("7".equals(strExceptions)) {
		strExceptionsName = "根据保单约定，需向第三方支付";
	} else if ("8".equals(strExceptions)) {
		strExceptionsName = "1000元以下现金支付";
	} else {
		strExceptionsName = "其它";
	}
	strReason = prpLcompensateDto.getReason();
%>

<script language="javascript">
//add by liuwei at 2011-02-11 打印附页 start

function printPageAdd() {
	//add print liudaoping 2013-04-15
	//alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
	return false;
	var url = "/claim/ClaimPrint.do?printType=CompensateAdd&CompensateNo=<%=strCompensateNo%>";
	var newWindow = window.open(url, "NewWindow", "width=600,height=500,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
}
//add by liuwei at 2011-02-11 打印附页 end

function loadForm() {

	//---------赔款计算书表PrpLcompensate*****
	<!-- tdCompensateNo.innerHTML  = '<%=strCompensateNo%>';-->

	<%
	if (strRiskCode.equals(ConstantCodes.RISKCODE_DAZ)) {

		double lawSumPaidTmp = 0;

		lawSumPaid = prpLcompensateDto.getSumPaid();
		lawSumPaidTmp = Math.abs(lawSumPaid);
		strLawSumPaid = new DecimalFormat("#,##0.00").format(lawSumPaid);
		strLawCSumPaid = MoneyUtils.toChinese(lawSumPaidTmp, prpLcompensateDto.getCurrency());
		if (lawSumPaid < 0) {
			strLawCSumPaid = "负" + strLawCSumPaid;
		}

	} else {

		double dblSumThisPaidTmp = 0;
		double dblSumPaidTmp = 0;

		//对预付赔款调整为中间赔付

		dblSumRest = prpLcompensateDto.getSumRest();
		dblSumThisPaid = prpLcompensateDto.getSumThisPaid();
		strSumThisPaid = new DecimalFormat("#,##0.00").format(dblSumThisPaid);

		dblSumThisPaidTmp = Math.abs(dblSumThisPaid);
		strCSumThisPaid = MoneyUtils.toChinese(dblSumThisPaidTmp, prpLcompensateDto.getCurrency());
		if (dblSumThisPaid < 0) {
			strCSumThisPaid = "负" + strCSumThisPaid;
		}
		dblSumPaid = prpLcompensateDto.getSumPaid();
		dblSumPaidTmp = Math.abs(dblSumPaid);
		strSumPaid = new DecimalFormat("#,##0.00").format(dblSumPaid);
		strCSumPaid = MoneyUtils.toChinese(dblSumPaidTmp, prpLcompensateDto.getCurrency());
		if (dblSumPaid < 0) {
			strCSumPaid = "负" + strCSumPaid;
		}

	}

	//处理赔款商业险与交强险合计信息--暂时
	double dblCAllSumPaidTmp = 0;
	double dblCAllSumPaid = 0;

	dblCAllSumPaid = lawSumPaid + dblSumPaid;
	dblCAllSumPaidTmp = Math.abs(dblCAllSumPaid);
	strAllSumPaid = new DecimalFormat("#,##0.00").format(dblCAllSumPaid);
	strCAllSumPaid = MoneyUtils.toChinese(dblCAllSumPaidTmp, prpLcompensateDto.getCurrency());
	if (dblSumPaid < 0) {
		strCAllSumPaid = "负" + strCAllSumPaid;
	} %>
		tdSumPrePaid.innerHTML = '&nbsp;已预付赔款：' + '<%=DataUtils.zeroToEmpty(dblSumPrePaid)%>';
	tdSumRest.innerHTML = '&nbsp;损余物资/残值金额：' + '<%=DataUtils.zeroToEmpty(dblSumRest)%>';
	tdCSumThisPaid.innerHTML = '&nbsp;本次实付赔款（人民币大写）：' + '<%=strCSumThisPaid%>';
	<!--   tdSumThisPaid.innerHTML   = '（￥：'                        + '<%=strSumThisPaid%>'; -->
	<!--   tdCSumPaid.innerHTML      = '&nbsp;商业保险赔款合计（人民币大写）：'      + '<%=strCSumPaid%>';-->
	<!--tdSumPaid.innerHTML       = '元（￥：'                        + '<%=strSumPaid%>';-->
	tdSumPaid.innerHTML = '元（￥：' + '<%=strSumThisPaid%>';
	tdCAllSumPaid.innerHTML = '&nbsp;赔款合计（人民币大写）：' + '<%=strCAllSumPaid%>';
	tdAllSumPaid.innerHTML = '元（￥：' + '<%=strAllSumPaid%>';




	//立案信息表PrpLclaim*****
	tdPolicyNo.innerHTML = '<%=strPolicyNo%>';
	<!--tdRPolicyNo.innerHTML    = '<%=strRPolicyNo%>';-->
	tdInsuredName.innerHTML = '<%=StringConvert.encode(prpLregistDto.getInsuredName())%>';
	//tdDriverName.innerHTML         = '<!%=StringConvert.encode(prpLregistDto.getLinkerName())%>'; 
	<!--tdIndemnityType.innerHTML = '<%=strIndemnityType%>';-->
	<!--tdRegistNo.innerHTML     = '<%=strRegistNo%>';-->

	<!--  tdCaseNo.innerHTML     = '<%=strCaseNo%>'; -->
	<% strDamageHourMS = prpLclaimDto.getDamageStartHour() + "";
	strDamageStartDate = prpLclaimDto.getDamageStartDate().getYear() + "年" + prpLclaimDto.getDamageStartDate().getMonth() + "月" + prpLclaimDto.getDamageStartDate().getDate() + "日" + strDamageHourMS;
	//String damangeAddressType = uiCodeAction.translateCodeCode("DamageAddress",prpLregistDto.getDamageAddressType(),true);%>                    
	tdDamageStartDate.innerHTML = '<%=strDamageStartDate%>';
	tdDamageName.innerHTML = '<%=StringConvert.encode(prpLclaimDto.getDamageName())%>';
	tdSumClaim.innerHTML = '<%=lossDesc%>';
	tdDamageAddress.innerHTML = '<%=StringConvert.encode(prpLclaimDto.getDamageAddress())%>';
	//tdDamageAddressType.innerHTML   = '<!%=StringConvert.encode(prpLclaimDto.getDamageAreaName())%>';  
	<% //事故责任
	strCode = "";
	strName = "";
	strCode = StringConvert.encode(prpLclaimDto.getIndemnityDuty());
	strName = uiCodeAction.translateCodeCode("IndemnityDuty", strCode, isChinese); %>
		tdIndemnityDuty.innerHTML = '<%=strName%>';
	tdIndemnityDutyRate.innerHTML = '<%=new DecimalFormat("#,##0.00").format(prpLcompensateDto.getIndemnityDutyRate())%>' + '％'; <%
	if (prpLclaimDto != null) {
		//modify by liuwei at 2011-04-27 由於有的险种在立案基本信息表中没有保存终保日期所以需要从保单基本信息表中获取 start
		if (prpLclaimDto.getEndDate().isEmpty()) {
			PrpCmainDto prpCmainDto = new UIClaimAction().findByPolicyNoKey(strRPolicyNo);
			strInsuredDate = "自 " + prpCmainDto.getStartDate().getYear() + "年" + prpCmainDto.getStartDate().getMonth() + "月" + prpCmainDto.getStartDate().getDate() + "日 零时起" + "至 " + prpCmainDto.getEndDate().getYear() + "年" + prpCmainDto.getEndDate().getMonth() + "月" + prpCmainDto.getEndDate().getDate() + "日 二十四时止";
		} else {
			strInsuredDate = "自 " + prpLclaimDto.getStartDate().getYear() + "年" + prpLclaimDto.getStartDate().getMonth() + "月" + prpLclaimDto.getStartDate().getDate() + "日 零时起" + "至 " + prpLclaimDto.getEndDate().getYear() + "年" + prpLclaimDto.getEndDate().getMonth() + "月" + prpLclaimDto.getEndDate().getDate() + "日 二十四时止";
		}
		//modify by liuwei at 2011-04-27 由於有的险种在立案基本信息表中没有保存终保日期所以需要从保单基本信息表中获取 end
	} %>
		tdInsuredDate.innerHTML = '<%=strInsuredDate%>';

	//---------理赔车辆信息PrpLthirdParty*****  
	<% //modify by lixiaohua 20040326 begin reason 赔款计算书厂牌型号打印不出
	if (policyDto.getPrpCitemCarDtoList() != null) {
		for (index = 0; index < intItemCarCount; index++) {
			prpItemCarDto = (PrpCitemCarDto) policyDto.getPrpCitemCarDtoList().get(index);
			strLicenseNo = StringConvert.encode(prpItemCarDto.getLicenseNo());
			strLicenseColorCode = StringConvert.encode(prpItemCarDto.getLicenseColorCode());
			strRunAreaCode = StringConvert.encode(prpItemCarDto.getRunAreaCode());
			strRunAreaName = uiCodeAction.translateCodeCode("RunArea", strRunAreaCode, isChinese); %>
				tdLicenseNo.innerHTML = '<%=StringConvert.encode(prpItemCarDto.getLicenseNo())%>';
			tdBrandName.innerHTML = '<%=StringConvert.encode(prpItemCarDto.getBrandName())%>';
			tdRunAreaName.innerHTML = '<%=strRunAreaName%>'; <% //条款类别
			strCode = "";
			strName = "";
			strCode = StringConvert.encode(prpItemCarDto.getClauseType());
			strName = uiCodeAction.translateCodeCode("ClauseType", strCode, isChinese); %>
			<!-- tdCarClause.innerHTML		= '<%=strName%>';      -->
			<%
		}
	}

	if (policyDto.getPrpCitemCarDtoList() != null) {
		//---------机动车险标的信息表PrpCitemCar*****    
		for (index = 0; index < intItemCarCount; index++) {
			prpItemCarDto = (PrpCitemCarDto) policyDto.getPrpCitemCarDtoList().get(index);
			strLicenseNo1 = StringConvert.encode(prpItemCarDto.getLicenseNo());

			if (strLicenseNo1.equals(strLicenseNo)) { %>
					tdPurchasePrice.innerHTML = '<%=new DecimalFormat("#,##0.00").format(prpItemCarDto.getPurchasePrice())%>'; <%
			}
		}
	}

	//---------标的子险信息PrpCitemKind*****
	dblSumAmount1 = 0;
	dblSumAmount2 = 0;

	if (policyDto.getPrpCitemKindDtoList() != null) {
		for (index = 0; index < intItemKindCount; index++) {
			prpItemKindDto = (PrpCitemKindDto) policyDto.getPrpCitemKindDtoList().get(index);
			//strRiskCode = prpItemKindDto.getRiskCode();
			//strKindCode = prpItemKindDto.getKindCode();
			//dbPrpDkind.getInfo(strRiskCode,strKindCode);
			//strCalculateFlag = dbPrpDkind.getCalculateFlag().substring(0,2);
			strCalculateFlag = prpItemKindDto.getCalculateFlag();
			strKindCodeThird = prpItemKindDto.getKindCode();
			//strCalculateFlag="Y1";
			if (strCalculateFlag.equals("Y")) {
				dblSumAmount1 += prpItemKindDto.getAmount();
			}
			if (strCalculateFlag.equals("A")) {
				//  dblSumAmount2 += prpItemKindDto.getAmount();
			}
			if (strKindCodeThird.equals(ConstantCodes.KINDCODE_D_B)) {
				strAmount = prpItemKindDto.getAmount();
			}
			if (strKindCodeThird.equals(ConstantCodes.KINDCODE_D_A)) {
				dblSumAmount2 = prpItemKindDto.getAmount();
			}
		}
	} %>
	<!--  tdSumAmount1.innerHTML = '<%=new DecimalFormat("#,##0.00").format(dblSumAmount2)%>';-->
	<!--   tdSumAmount2.innerHTML = '<%=new DecimalFormat("#,##0.00").format(dblSumAmount2)%>';  -->



	<!--tdAmount.innerHTML = '<%=new DecimalFormat("#,##0.00").format(strAmount)%>';-->
	<%
	if (strRiskCode.equals(ConstantCodes.RISKCODE_DAZ)) {
		if (compensateDto.getPrpLctextDtoDtoList() != null) {
			//-----------交强险赔款计算文字
			for (index = 0; index < intCtextCount; index++) {
				prpLctextDto = (PrpLctextDto) compensateDto.getPrpLctextDtoDtoList().get(index);
				strTextType = StringConvert.encode(prpLctextDto.getTextType());
				if (strTextType.charAt(0) == '1') {
					strContextLaw = strContextLaw + StringConvert.encode(prpLctextDto.getContext());
				}
			}
		}

		//检查计算书文字打出来是几行
		intCtextCountTmp = 0; //textarea的行数

		for (index = 0; index < strContextLaw.length(); index++) {
			if (strContextLaw.substring(index, index + 1).equals("\\")) {
				if (!(strContextLaw.substring(index).length() < 4)) {
					if (strContextLaw.substring(index, index + 4).equals("\\r\\n")) {
						intCtextCountTmp += 1; //只要有回车换行，intCtextCountTmp+1
					}
				}
			}
		}

		int x = 0;
		int y = 0;
		if (!(strContextLaw.length() < 4)) //如果strContextLaw.length()>=4，判断strContextLaw结尾是文字，还是回车换行
		{
			x = strContextLaw.length() - 4;
			y = strContextLaw.length();
			if (!strContextLaw.substring(x, y).equals("\\r\\n")) {
				intCtextCountTmp += 1;
			}
		} else
		//如果strContextLaw不足1行，intCtextCountTmp = 1; 
			intCtextCountTmp = 1;

		if (intCtextCountTmp > 10) {
			if (strContext.length() > 250) {
				countNumber1 = 250;
				countNumber = strContext.indexOf("\\r\\n", countNumber1);
				//strContext = strContext.substring(0,countNumber)+"<br>计算书信息过多，请详见清单。";
				strContext = "计算书信息过多，请详见清单。";
				//add by liuwei at 2011-02-11 控制打印附页按钮可用 start%>
				buttonPrintAdd.disabled = false; <% //add by liuwei at 2011-02-11 控制打印附页按钮可用 end
			}
		}
		while (strContextLaw.indexOf("\\r\\n") != -1)
			strContextLaw = strContextLaw.substring(0, strContextLaw.indexOf("\\r\\n")) + "<br>" + strContextLaw.substring(strContextLaw.indexOf("\\r\\n") + "\\r\\n".length());

	} else {

		if (compensateDto.getPrpLctextDtoDtoList() != null) {
			//------------赔款计算文字表PrpLctext*****
			for (index = 0; index < intCtextCount; index++) {
				prpLctextDto = (PrpLctextDto) compensateDto.getPrpLctextDtoDtoList().get(index);
				strTextType = StringConvert.encode(prpLctextDto.getTextType());
				if (strTextType.charAt(0) == '1') {
					strContext = strContext + StringConvert.encode(prpLctextDto.getContext());
				}
			}
		}

		//检查计算书文字打出来是几行
		intCtextCountTmp = 0; //textarea的行数

		for (index = 0; index < strContext.length(); index++) {
			if (strContext.substring(index, index + 1).equals("\\")) {
				if (!(strContext.substring(index).length() < 4)) {
					if (strContext.substring(index, index + 4).equals("\\r\\n")) {
						intCtextCountTmp += 1; //只要有回车换行，intCtextCountTmp+1
					}
				}
			}
		}

		int x1 = 0;
		int y1 = 0;
		if (!(strContext.length() < 4)) //如果strContext.length()>=4，判断strContext结尾是文字，还是回车换行
		{
			x1 = strContext.length() - 4;
			y1 = strContext.length();
			if (!strContext.substring(x1, y1).equals("\\r\\n")) {
				intCtextCountTmp += 1;
			}
		} else
		//如果strContext不足1行，intCtextCountTmp = 1; 
			intCtextCountTmp = 1;

		/*   if( intCtextCountTmp>20 )
				   {
				     if(strContext.length()>500){
				         //strContext = strContext.substring(0,500)+"<br>计算书信息过多，请详见清单。"; 
				         strContext = "计算书信息过多，请详见清单。"; 
				         //add by liuwei at 2011-02-11 控制打印附页按钮可用 start%>
      buttonPrintAdd.disabled=false;
      <%//add by liuwei at 2011-02-11 控制打印附页按钮可用 end
				     }
				   } */
		if (intCtextCountTmp > 10) {
			if (strContext.length() > 250) {
				countNumber1 = 250;
				//modify by lixiang start at 2007-08-03
				//reasion:由於可能出现第250个为汉字的情况，所以countNumber有可能为-1，那么就只能在-1的情况下，取250个字了。
				countNumber = strContext.indexOf("\\r\\n", countNumber1);
				if (countNumber == -1) {
					//strContext = strContext.substring(0,250)+"<br>计算书信息过多，请详见清单。";
					strContext = "计算书信息过多，请详见清单。";
					//add by liuwei at 2011-02-11 控制打印附页按钮可用 start%>
					buttonPrintAdd.disabled = false; <% //add by liuwei at 2011-02-11 控制打印附页按钮可用 end
				} else {
					//strContext = strContext.substring(0,countNumber)+"<br>计算书信息过多，请详见清单。";
					strContext = "计算书信息过多，请详见清单。";
					//add by liuwei at 2011-02-11 控制打印附页按钮可用 start%>
					buttonPrintAdd.disabled = false; <% //add by liuwei at 2011-02-11 控制打印附页按钮可用 end
				}
				//modify by lixiang start at 2007-08-03

			}
		}

		while (strContext.indexOf("\\r\\n") != -1)
			strContext = strContext.substring(0, strContext.indexOf("\\r\\n")) + "<br>" + strContext.substring(strContext.indexOf("\\r\\n") + "\\r\\n".length());
	} %>
		tdContext.innerHTML = '' + '<br>' + '<%=strContext%>';

	<% //--------赔款费用信息表PrpLcharge*****    
	dblJudgeFee = 0;
	dblCheckFee = 0;
	dblLawFee = 0;
	dblElseFee = 0;
	dblCheckFee1 = 0;
	dblAssessFee = 0;
	dblJianYan = 0;

	if (compensateDto.getPrpLchargeDtoList() != null) {
		for (index = 0; index < intChargeCount; index++) {
			prpLchargeDto = (PrpLchargeDto) compensateDto.getPrpLchargeDtoList().get(index);
			strChargeCode = StringConvert.encode(prpLchargeDto.getChargeCode());
			System.out.println("xixixixixixixiixstrChargeCode" + strChargeCode);
			if (!prpLchargeDto.getChargeFlag().equals("0")) {
				//continue;//add by qinyongli 费用只打印原始费用
			}
			//add by zhangyurui 2008-11-13 计入赔款的费用不打印在费用信息当中 begin
			if (prpLchargeDto.getChargeAmount() == prpLchargeDto.getSumRealPay()) {
				continue;
			}
			//add by zhangyurui 2008-11-13 计入赔款的费用不打印在费用信息当中 end      
			if (strChargeCode.equals("07")) //鉴定费
			{
				dblJudgeFee += prpLchargeDto.getChargeAmount();
			} else if (strChargeCode.equals("06")) //代查勘费
			{
				dblCheckFee1 += prpLchargeDto.getChargeAmount();
			} else if (strChargeCode.equals("05")) //诉讼费
			{
				dblLawFee += prpLchargeDto.getChargeAmount();
			} else if (strChargeCode.equals("04")) //查勘费
			{
				dblCheckFee += prpLchargeDto.getChargeAmount();

			} else if (strChargeCode.equals("13")) //公估费
			{
				dblAssessFee += prpLchargeDto.getChargeAmount();
			} else if (strChargeCode.equals("01")) //检验费
			{
				dblJianYan += prpLchargeDto.getChargeAmount();
			} else //其他费用
			{
				dblElseFee += prpLchargeDto.getChargeAmount();
			}
		}

		//获取赔偿比例
		double ThelossRate = 0;
		if (compensateDto.getPrpLlossDtoList() != null) {
			for (index = 0; index < compensateDto.getPrpLlossDtoList().size(); index++) {
				PrpLlossDto prpLlossDto = (PrpLlossDto) compensateDto.getPrpLlossDtoList().get(0);
				ThelossRate = prpLlossDto.getIndemnityDutyRate();
				break;
			}
		}
	} %>
	<!-- tdJudgeFee.innerHTML = '&nbsp;鉴定费：'   + '<%=DataUtils.zeroToEmpty(dblJudgeFee)%>';-->
	tdCheckFee1.innerHTML = '&nbsp;代查勘费：' + '<%=DataUtils.zeroToEmpty(dblCheckFee1)%>';
	tdLawFee.innerHTML = '&nbsp;诉讼、仲裁费：' + '<%=DataUtils.zeroToEmpty(dblLawFee)%>';
	tdElseFee.innerHTML = '&nbsp;其他费用：' + '<%=DataUtils.zeroToEmpty(dblElseFee)%>';
	tdCheckFee.innerHTML = '&nbsp;查勘费：' + '<%=DataUtils.zeroToEmpty(dblCheckFee)%>';
	tdAssessFee.innerHTML = '&nbsp;公估费：' + '<%=DataUtils.zeroToEmpty(dblAssessFee)%>';
	tdJianYan.innerHTML = '&nbsp;检验费：' + '<%=DataUtils.zeroToEmpty(dblJianYan)%>'; <% //strSumThisPaid  = new DecimalFormat("#,##0.00").format(dblSumThisPaid-dblJudgeFee-dblCheckFee-dblLawFee-dblElseFee-dblSumPrePaid-dblSumRest);
	//strCSumThisPaid = MoneyUtils.toChinese(dblSumThisPaid-dblJudgeFee-dblCheckFee-dblLawFee-dblElseFee-dblSumPrePaid-dblSumRest,prpLcompensateDto.getCurrency());
	double finalyPaidTmp = 0;
	if ((dblSumThisPaid - dblJudgeFee - dblCheckFee - dblLawFee - dblElseFee - dblSumRest) < 0) {
		finalyPaidTmp = Math.abs(dblSumThisPaid - dblJudgeFee - dblCheckFee - dblLawFee - dblElseFee - dblSumRest);
	} else {
		finalyPaidTmp = dblSumThisPaid - dblJudgeFee - dblCheckFee - dblLawFee - dblElseFee - dblSumRest;
	}
	strSumThisPaid = new DecimalFormat("#,##0.00").format(dblSumThisPaid - dblJudgeFee - dblCheckFee - dblLawFee - dblElseFee - dblSumRest);
	strCSumThisPaid = MoneyUtils.toChinese(finalyPaidTmp, prpLcompensateDto.getCurrency());
	if ((dblSumThisPaid - dblJudgeFee - dblCheckFee - dblLawFee - dblElseFee - dblSumRest) < 0) {
		strCSumThisPaid = "负" + strCSumThisPaid;
	} %>
	<!--   tdCSumThisPaid.innerHTML  = '&nbsp;本次实付赔款（人民币大写）：'  + '<%=strCSumThisPaid%>';     -->
	<!--   tdSumThisPaid.innerHTML   = '（￥：'                        + '<%=strSumThisPaid%>';           -->
	<% //---------车辆信息表PrpLdriver*****  
	if (registDto.getPrpLdriverDtoList() != null) {
		intDriverCount = registDto.getPrpLdriverDtoList().size();
		for (index = 0; index < intDriverCount; index++) {
			prpLdriverDto = (PrpLdriverDto) registDto.getPrpLdriverDtoList().get(index);
			String strLicenseNo2 = StringConvert.encode(prpLdriverDto.getLicenseNo());
			if (strLicenseNo.equals(strLicenseNo2)) {
				strDriverName = StringConvert.encode(prpLdriverDto.getDriverName());
				break;
			}
		}

		if (strDriverName == null || "".equals(strDriverName)) {
			strDriverName = StringConvert.encode(prpLregistDto.getLinkerName());
		}
	} %>
		tdDriverName.innerHTML = '<%=strDriverName%>'; <%
	if (policyDto.getPrpCcarDriverDtoList() != null) {
		intCriverCount = policyDto.getPrpCcarDriverDtoList().size();
		for (index = 0; index < intCriverCount; index++) {
			prpCcarDriverDto = (PrpCcarDriverDto) policyDto.getPrpCcarDriverDtoList().get(index);
			if (prpCcarDriverDto.getChangelessFlag().equals("1")) {
				strDriverName1 = StringConvert.encode(prpCcarDriverDto.getDriverName()); %>
					tdDriverName1.innerHTML = '<%=strDriverName1%>'; <%
			}
		}
	} %> <% String claimType = ""; //案件类型
	String strDamageAreaCode = ""; //出险区域代码
	if (checkDto.getPrpLcheckDto() != null) {
		claimType = checkDto.getPrpLcheckDto().getClaimType();
		strDamageAreaCode = checkDto.getPrpLcheckDto().getDamageAreaCode();
	} else {
		claimType = prpLclaimDto.getClaimType();
		strDamageAreaCode = prpLclaimDto.getDamageAreaCode();
	}
	String strClaimType = uiCodeAction.translateCodeCode("CaseCode", claimType, true);
	String handleUnit = prpLregistDto.getHandleUnit();
	String strHandleUnit = uiCodeAction.translateCodeCode("HandleUnit", handleUnit, true);
	String strDamageAreaName = uiCodeAction.translateCodeCode("DamageAreaCode", strDamageAreaCode, true); %>
		tdClaimType.innerHTML = '<%=strClaimType%>';
	tdHandleUnit.innerHTML = '<%=strHandleUnit%>';
	tdDamageAddressType.innerHTML = '<%=StringConvert.encode(strDamageAreaName)%>';
	<!--  tdRemark.innerHTML = '备注：'; -->
	<% double douDelinquentFee = 0;
	String strDelinquentFee = "";
	String strPlanFee = "";
	if (policyDto.getPrpCplanDtoList() != null) {
		int intPlanCount = policyDto.getPrpCplanDtoList().size();
		for (index = 0; index < intPlanCount; index++) {
			PrpCplanDto prpCplanDto = (PrpCplanDto) policyDto.getPrpCplanDtoList().get(index);
			douDelinquentFee = prpCplanDto.getDelinquentFee();
			if (douDelinquentFee == 0) {
				strDelinquentFee = "是";
				strPlanFee = "已足额交费";
			} else {
				strDelinquentFee = "否";
				strPlanFee = "还欠保费：" + douDelinquentFee;
			} %>
			<!-- tdEndorseNo.innerHTML  = '<%=prpCplanDto.getEndorseNo()%>'; -->	
			<!-- tdRemark.innerHTML = '备注：'+'<br>'+'保费缴费情况：'+'<%=strPlanFee%>' + '<br>'+'保费收讫日期：'+'<%=prpCplanDto.getPlanDate()%>'+'<br>'+'收款人：'+''; -->
			//tdSerialNo.innerHTML  = '已预付次数：'+'<%=DataUtils.zeroToEmpty(prpCplanDto.getSerialNo() - 1)%>';
			tdSerialNo.innerHTML = '&nbsp;已预付次数：' + '<%=DataUtils.zeroToEmpty(intCompensatePreCount)%>'; <%
		}
	} %> <% prpLextDto = registDto.getPrpLextDto();
	if (prpLextDto != null) {
		long personInjureB = prpLextDto.getPersonInjureB();
		long personDeathB = prpLextDto.getPersonDeathB();
		long personInjureD1 = prpLextDto.getPersonInjureD1();
		long personDeathD1 = prpLextDto.getPersonDeathD1();
		String personInjure1 = "";
		String personInjure2 = "";
		personInjure = "第三者（伤 " + personInjureB + " 人，亡 " + personDeathB + " 人），车上人员（伤 " + personInjureD1 + " 人， 亡 " + personDeathD1 + " 人）";

		if (personInjureB == 0) {
			if (personDeathB == 0) {
				personInjure1 = "";
			} else {
				personInjure1 = "第三者（亡 " + personDeathB + " 人），";
			}
		} else {
			if (personDeathB == 0) {
				personInjure1 = "第三者（伤 " + personInjureB + " 人），";
			} else {
				personInjure1 = "第三者（伤 " + personInjureB + " 人，亡 " + personDeathB + " 人），";
			}
		}
		if (personInjureD1 == 0) {
			if (personDeathD1 == 0) {
				personInjure2 = "";
			} else {
				personInjure2 = "车上人员（亡 " + personDeathD1 + " 人）";
			}
		} else {
			if (personDeathD1 == 0) {
				personInjure2 = "车上人员（伤 " + personInjureD1 + " 人）";
			} else {
				personInjure2 = "车上人员（伤 " + personInjureD1 + " 人， 亡 " + personDeathD1 + " 人）";
			}
		}
		personInjure = personInjure1 + personInjure2;
		if ((prpLextDto.getPersonInjureB() == 0) && (prpLextDto.getPersonDeathB() == 0) && (prpLextDto.getPersonInjureD1() == 0) && (prpLextDto.getPersonDeathD1() == 0)) {
			personInjure = "无人员伤亡";
		} %>
		<!--   tdPersonInjure.innerHTML = '<%=personInjure%>';  -->
		<%
	} %> <% String strEndorseNo = "";
	if (endorseDto.getPrpPheadDtoList() != null) {
		for (index = 0; index < endorseDto.getPrpPheadDtoList().size(); index++) {
			PrpPheadDto prpPheadDto = (PrpPheadDto) endorseDto.getPrpPheadDtoList().get(index);
			strEndorseNo = prpPheadDto.getEndorseNo(); %>
				tdEndorseNo.innerHTML = '<%=strEndorseNo%>'; <%
		}
	} %>

} <% PrpCmainDto cPrpCmainDto = new UIClaimAction().findByPolicyNoKey(strRPolicyNo);

PrpLclaimDto cClaimDto = null;
PrpLcompensateDto cCompensateDto = null;
PrpClimitDto prpClimitDto = null;
PrpLlossDto prpLlossDto = new BLPrpLlossFacade().findByPrimaryKey(strCompensateNo, 1);
String strCompany = "";
String strCCompensateNo = "";
String strLimit1 = "0.00";
String strLimit2 = "0.00";
String strLimit3 = "0.00";
String strDeduLossRate = "";
String strClaimRate = "";
String strDeduLossFee = "0.00";
if (prpLlossDto != null) {
	strLossRate = new DecimalFormat("#,##0.00").format(prpLlossDto.getDutyDeductibleRate());
	strDeduLossRate = new DecimalFormat("#,##0.00").format(prpLlossDto.getMainKindDeductibleRate());
	strClaimRate = new DecimalFormat("#,##0.00").format(prpLlossDto.getClaimRate());
	strDeduLossFee = new DecimalFormat("#,##0.00").format(prpLlossDto.getDeductible());
}
if (cPrpCmainDto != null) {
	strCompany = uiCodeAction.translateComCode(cPrpCmainDto.getComCode(), true);
	Iterator iter = new UIClaimAction().findByConditions("registNo='" + strRegistNo + "' AND riskcode='" + ConstantCodes.RISKCODE_DAZ + "'").iterator();
	if (iter.hasNext()) {
		cClaimDto = (PrpLclaimDto) iter.next();
	}
	if (cClaimDto != null) {
		iter = new UICompensateAction().findByClaimNo("claimNo='" + cClaimDto.getClaimNo() + "'").iterator();
		if (iter.hasNext()) {
			cCompensateDto = (PrpLcompensateDto) iter.next();
			strCCompensateNo = cCompensateDto.getCompensateNo();
			Iterator iter2 = new UIPrpClimitAction().findByConditions("policyNo='" + strRPolicyNo + "' AND limittype='90'", cClaimDto.getDamageStartDate().toString(), cPrpCmainDto.getStartDate().toString()).iterator();
			while (iter2.hasNext()) {
				prpClimitDto = (PrpClimitDto) iter2.next();
				if (prpClimitDto.getLimitType().equals("90")) {
					strLimit2 = new DecimalFormat("#,##0.00").format(prpClimitDto.getLimitFee());
				} else if (prpClimitDto.getLimitType().equals("91")) {
					strLimit1 = new DecimalFormat("#,##0.00").format(prpClimitDto.getLimitFee());
				} else if (prpClimitDto.getLimitType().equals("92")) {
					strLimit3 = new DecimalFormat("#,##0.00").format(prpClimitDto.getLimitFee());
				}
			}
		}
	}
} %>
</script>
  