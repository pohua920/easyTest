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
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="com.sinosoft.claim.dto.domain.*"%> 
<%@page import="com.sinosoft.claim.util.*"%> 
<%@page import="com.sinosoft.sysframework.common.util.*"%> 
<%@page import="com.sinosoft.sysframework.common.datatype.DateTime"%> 
<%@page import="com.sinosoft.sysframework.exceptionlog.*"%> 
<%@ page import="java.util.ArrayList" %>
<jsp:directive.page import="com.sinosoft.claim.bl.facade.BLPrpCcoinsFacade"/>
<%@page import="com.sinosoft.claim.bl.facade.BLPrpLcfeecoinsFacade"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.sinosoft.claim.common.ConstantCodes"%>
<%
	//变量声明部分
	String strCompensateNo = request.getParameter("CompensateNo"); //赔款计算书号
	String strClaimNo = ""; //赔案号
	String strRegistNo = ""; //报案号
	String strPolicyNo = ""; //保单号
	String strLicenseNo = ""; //保单中的号牌号码
	String strLicenseColorCode = ""; //保单中的号牌底色
	String strLicenseNo1 = ""; //理赔车辆信息中的号牌号码
	String strLicenseColorCode1 = ""; //理赔车辆信息中的号牌底色
	String strInsureCarFlag = ""; //是否为本保单车辆
	String strTextType = "";
	String strContext = "&nbsp;理赔计算：<br>"; //赔款计算书文字
	String strDamageStartDate = ""; //出险时间
	String strDamageName = ""; //出险原因
	String strReportHour = ""; //出险时间小时
	String strInsuredDate = ""; //保险期间
	double dblJudgeFee = 0; //
	double dblCheckFee = 0; //
	double dblLawFee = 0; //
	double dblElseFee = 0; //
	double dblSumPrePaid = 0; //
	double dblSumRest = 0; //
	double dblCheckFee1 = 0;
	double dblAssessFee = 0;
	double dblRescueFee = 0; //施救费
	double dblGsjzFee = 0; //共损求助费
	double dblFlFee = 0; //法律费
	double dblAgentFee = 0; //代理费
	double dblPropSumLossPay = 0; //财产损失
	double dblPersonSumLossPay = 0; //人伤损失
	double dblSumLossPay = 0; //标的赔款
	String strSumThisPaid = ""; //
	String strCSumThisPaid = ""; //
	double dblSumThisPaid = 0; //
	String strSumPaid = ""; //
	String strMySumPaid = ""; //我司赔款金额
	String strOtherSumPaid = ""; //我司代付金额
	String strCSumPaid = ""; //
	double dblSumPaid = 0; //
	double dbMySumPaid = 0;//我司应付金额
	double dbOtherSumPaid = 0;//我司代付赔款金额
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
	//add begin by zhuly 20051103 
	String strSeatCount = ""; //座位數
	String strTonCount = ""; //噸位數
	String strFrameNo = ""; //車架號
	String strEngineNo = ""; //發動機號
	String[] subKindName = new String[6]; //附加险险别名称
	String[] subKindAmount = new String[6]; //附加险保额
	String strKindName = ""; //附加险别
	String strFlag = ""; //主险附加险标志
	String strKindAmount = ""; //附加险限额
	String strCarInsuredRelation = ""; //所属性质代码
	String strCarInsuredRelationName = "";//所属性质
	String strUseNatureCode = ""; //使用性质
	double dblSumprepaid = 0; //预付赔款 add by liping 080811
	String strSumprepaid = ""; //预付赔款 add by liping 080811

	//add end   by zhuly 20051103

	//代码翻译变量
	String strCode = "";
	String strName = "";
	boolean isChinese = true; //中文标志

	//对象定义部分

	PrpCitemCarDto prpItemCarDto = null; //ItemCarDto对象 
	PrpCitemKindDto prpItemKindDto = null; //保单的ItemKindDto对象
	PrpLclaimDto prpLclaimDto = null; //ClaimDto对象
	PrpLthirdPartyDto prpLthirdPartyDto = null; //ThirdPartyDto对象
	PrpLcompensateDto prpLcompensateDto = null; //CompensateDto对象
	PrpLchargeDto prpLchargeDto = null; //ChargeDto对象
	PrpLctextDto prpLctextDto = null;
	PrpLdriverDto prpLdriverDto = null;
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
	int intPropCount = 0;
	String lossDesc = ""; //损失程度

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

	//add by liping 080811

	//得到prpLclaimDto对象
	prpLclaimDto = claimDto.getPrpLclaimDto();
	//得到prpLregistDto对象
	prpLregistDto = registDto.getPrpLregistDto();

	strClaimNo = prpLclaimDto.getClaimNo();
	strOperatorCode = prpLcompensateDto.getOperatorCode();
	strOperatorName = uiCodeAction.translateUserCode(strOperatorCode, isChinese);
	DateTime dateTime = new DateTime();
	//String strDateTime = new DateTime(dateTime.current(),dateTime.YEAR_TO_HOUR);

	//得到保单号
	strPolicyNo = prpLclaimDto.getPolicyNo();

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
	PrpCmainDto prpCmainDto = policyDto.getPrpCmainDto(); //得到保单主表对象 
	//add by zhangyurui 取共保比例 begin
	double coinsRate = 1;
	ArrayList PrpCcoinsDtoList = (ArrayList) new BLPrpCcoinsFacade().findByConditions(" policyNO='" + prpCmainDto.getPolicyNo() + "' and coinsType='1' ", 0, 0);
	if (null != PrpCcoinsDtoList && PrpCcoinsDtoList.size() > 0) {
		PrpCcoinsDto prpCcoinsDto = (PrpCcoinsDto) PrpCcoinsDtoList.get(0);
		coinsRate = prpCcoinsDto.getCoinsRate() / 100;
	}
	//add by zhangyurui 取共保比例 end

	String RiskCode = ""; //保单险种代码
	String strRiskName = ""; //保单险种名称
	RiskCode = prpCmainDto.getRiskCode();//
	strRiskName = uiCodeAction.translateRiskCode(RiskCode, true);

	//得到损失程度描叙
	if (claimDto.getPrpLthirdCarLossDtoList() != null) {
		for (index = 0; index < claimDto.getPrpLthirdCarLossDtoList().size(); index++) {
			PrpLthirdCarLossDto prpLthirdCarLossDto = (PrpLthirdCarLossDto) claimDto.getPrpLthirdCarLossDtoList().get(0);
			lossDesc = prpLthirdCarLossDto.getLossDesc();
		}
	}
%>

<script language="javascript">
function loadForm() {

	//*****赔款计算书表PrpLcompensate*****
	//tdCompensateNo.innerHTML  = '<%=strCompensateNo%>';

	<% dblSumPrePaid = prpLcompensateDto.getSumPrePaid();
	dblSumRest = prpLcompensateDto.getSumRest();
	dblSumThisPaid = prpLcompensateDto.getSumThisPaid();
	strSumThisPaid = new DecimalFormat("#,##0.00").format(dblSumThisPaid);
	if (dblSumThisPaid < 0) {
		strCSumThisPaid = "负" + MoneyUtils.toChinese(-dblSumThisPaid, prpLcompensateDto.getCurrency());
	} else {
		strCSumThisPaid = MoneyUtils.toChinese(dblSumThisPaid, prpLcompensateDto.getCurrency());
	}
	dblSumPaid = prpLcompensateDto.getSumPaid();
	//modify by liuwei at 2011-07-27 从（联、共）保显示我司份额 beging
	if ("2".equals(prpCmainDto.getCoinsFlag()) || "4".equals(prpCmainDto.getCoinsFlag())) {
		String conditions = "businessNo='" + strCompensateNo + "' and coinstype='1'";
		BLPrpLcfeecoinsFacade blPrpLcfeecoinsFacade = new BLPrpLcfeecoinsFacade();
		Collection collection = blPrpLcfeecoinsFacade.findByConditions(conditions);
		if (collection != null) {
			Iterator iterator = collection.iterator();
			while (iterator.hasNext()) {
				PrpLcfeecoinsDto prpLcfeecoinsDto = (PrpLcfeecoinsDto) iterator.next();
				BigDecimal bd1 = new BigDecimal(Double.toString(prpLcfeecoinsDto.getCoinsSumPaid()));
				BigDecimal bd2 = new BigDecimal(Double.toString(dbMySumPaid));
				dbMySumPaid = bd1.add(bd2).doubleValue();
			}
		}

		BigDecimal bigCoinsRate = new BigDecimal(Double.toString(coinsRate));
		BigDecimal bigDblPropSumLossPay = new BigDecimal(Double.toString(prpLcompensateDto.getLossSumRealPay()));
		BigDecimal bigDblPersonSumLossPay = new BigDecimal(Double.toString(prpLcompensateDto.getPersonLossSumRealPay()));
		BigDecimal bigDblSumprepaid = new BigDecimal(Double.toString(prpLcompensateDto.getSumPrePaid()));
		dblPropSumLossPay = bigDblPropSumLossPay.multiply(bigCoinsRate).doubleValue(); //财产损失
		dblPersonSumLossPay = bigDblPersonSumLossPay.multiply(bigCoinsRate).doubleValue(); //人伤损失
		dblSumprepaid = bigDblSumprepaid.multiply(bigCoinsRate).doubleValue(); //预赔
	} else {
		if ("1".equals(prpCmainDto.getCoinsFlag()) || "3".equals(prpCmainDto.getCoinsFlag())) { //主（联、共）保
			dbMySumPaid = dblSumPaid * coinsRate;
			if (!"0".equals(prpLcompensateDto.getIsPayForOther())) {
				dbOtherSumPaid = dblSumPaid * (1 - coinsRate);
			} else {
				dbOtherSumPaid = 0;
			}
		} else {
			dbMySumPaid = dblSumPaid;
			dbOtherSumPaid = 0;
		}

		dblPropSumLossPay = prpLcompensateDto.getLossSumRealPay(); //财产损失
		dblPersonSumLossPay = prpLcompensateDto.getPersonLossSumRealPay(); //人伤损失
		dblSumprepaid = (compensateDto.getPrpLcompensateDto()).getSumPrePaid();
	}
	strMySumPaid = new DecimalFormat("#,##0.00").format(dbMySumPaid);
	strOtherSumPaid = new DecimalFormat("#,##0.00").format(dbOtherSumPaid);
	//modify by liuwei at 2011-07-27 从（联、共）保显示我司份额 end
	strSumPaid = new DecimalFormat("#,##0.00").format(dblSumPaid);
	if (dblSumPaid < 0) {
		strCSumPaid = "负" + MoneyUtils.toChinese(-dblSumPaid, prpLcompensateDto.getCurrency());
	} else {
		strCSumPaid = MoneyUtils.toChinese(dblSumPaid, prpLcompensateDto.getCurrency());
	}

	String strCurrency = prpLcompensateDto.getCurrency();
	strSumprepaid = new DecimalFormat("#,##0.00").format(dblSumprepaid);
	dblSumLossPay = dblPropSumLossPay + dblPersonSumLossPay; //标的赔款
	String strSumLossPay = new DecimalFormat("#,##0.00").format(dblSumLossPay);
	String strCSumLossPay = "";
	if (dblSumLossPay < 0) {
		strCSumLossPay = "负" + MoneyUtils.toChinese(-dblSumLossPay, prpLcompensateDto.getCurrency());
	} else {
		strCSumLossPay = MoneyUtils.toChinese(dblSumLossPay, prpLcompensateDto.getCurrency());
	} %>
	//tdSumPrePaid.innerHTML    = '&nbsp;已预付赔款：'                    + '<%=DataUtils.zeroToEmpty(dblSumPrePaid)%>';
	//tdSumRest.innerHTML       = '&nbsp;损余物资/残值金额：'           + '<%=DataUtils.zeroToEmpty(dblSumRest)%>';
	//tdCSumThisPaid.innerHTML  = '&nbsp;本次实付赔款（人民币大写）：'  + '<%=strCSumThisPaid%>';
	//tdSumThisPaid.innerHTML   = '（￥：'                        + '<%=strSumThisPaid%>';

	//tdCSumPaid.innerHTML      = '&nbsp;责任赔款（大写）：'      + '<%=strCSumPaid%>'+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(小写)￥:'+ '<%=strSumPaid%>元';

	tdSSumPaid.innerHTML = '<%=strCurrency%>' + '&nbsp;' + '<%=strSumPaid%>';
	tdMySumPaid.innerHTML = '&nbsp;<%=strCurrency%>&nbsp;' + '<%=strMySumPaid%>';
	tdOtherSumPaid.innerHTML = '&nbsp;<%=strCurrency%>&nbsp;' + '<%=strOtherSumPaid%>';
	//*****立案信息表PrpLclaim*****
	tdPolicyNo.innerHTML = '<%=strPolicyNo%>';
	tdRiskName.innerHTML = '<%=strRiskName%>';
	//tdEndorseNo.innerHTML = '<%//=strEndorseNo%>';

	tdPropSumLossPay.innerHTML = '<%=strCurrency%>' + '&nbsp;' + '<%=new DecimalFormat("#,##0.00").format(dblPropSumLossPay)%>';
	tdPersonSumLossPay.innerHTML = '<%=strCurrency%>' + '&nbsp;' + '<%=new DecimalFormat("#,##0.00").format(dblPersonSumLossPay)%>';


	tdCSumLossPay.innerHTML = '&nbsp;责任赔款（大写）：' + '<%=strCSumLossPay%>' + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（小写）：<%=strCurrency%>&nbsp;' + '<%=strSumLossPay%>';
	tdSSumLossPay.innerHTML = '<%=strCurrency%>' + '&nbsp;' + '<%=strSumLossPay%>'; <% strDamageStartDate = prpLclaimDto.getDamageStartDate().getYear() + "年" + prpLclaimDto.getDamageStartDate().getMonth() + "月" + prpLclaimDto.getDamageStartDate().getDate() + "日";
	strReportHour = prpLregistDto.getReportHour().toString();
	strDamageName = prpLregistDto.getDamageName();
	String damangeAddressType = uiCodeAction.translateCodeCode("DamageAddress", prpLregistDto.getDamageAddressType(), true); %>
		tdDamageStartDate.innerHTML = '<%=strDamageStartDate%>' + '<%=strReportHour.substring(0, 2)%>' + '时';
	tdDamageName.innerHTML = '<%=StringConvert.encode(prpLclaimDto.getDamageName())%>';
	//tdSumClaim.innerHTML        = '<%=lossDesc%>';    
	//tdDamageAddress.innerHTML   = '<%=StringConvert.encode(prpLclaimDto.getDamageAddress())%>';
	//tdDamageAddressType.innerHTML   = '<%=damangeAddressType%>';
	// tdDamageName.innerHTML = '<%=strDamageName%>';
	tdInsuredDate.innerHTML = '<%=prpLclaimDto.getInsuredName()%>'; <% //事故责任
	strCode = "";
	strName = "";
	strCode = StringConvert.encode(prpLclaimDto.getIndemnityDuty());
	strName = uiCodeAction.translateCodeCode("IndemnityDuty", strCode, isChinese); %>
	//tdIndemnityDuty.innerHTML     = '<%=strName%>';
	//tdIndemnityDutyRate.innerHTML = '<%=new DecimalFormat("#,##0.00").format(prpLclaimDto.getIndemnityDutyRate())%>'+'％';
	<% //modify by liuwei at 2011-04-27 由於有的险种在立案基本信息表中没有保存终保日期所以需要从保单基本信息表中获取 start
	if (prpLclaimDto.getEndDate().isEmpty()) {
		strInsuredDate = "自 " + prpCmainDto.getStartDate().getYear() + "年" + prpCmainDto.getStartDate().getMonth() + "月" + prpCmainDto.getStartDate().getDate() + "日 零时起" + "至 " + prpCmainDto.getEndDate().getYear() + "年" + prpCmainDto.getEndDate().getMonth() + "月" + prpCmainDto.getEndDate().getDate() + "日 二十四时止";
	} else {
		strInsuredDate = "自 " + prpLclaimDto.getStartDate().getYear() + "年" + prpLclaimDto.getStartDate().getMonth() + "月" + prpLclaimDto.getStartDate().getDate() + "日 零时起" + "至 " + prpLclaimDto.getEndDate().getYear() + "年" + prpLclaimDto.getEndDate().getMonth() + "月" + prpLclaimDto.getEndDate().getDate() + "日 二十四时止";
	}
	//modify by liuwei at 2011-04-27 由於有的险种在立案基本信息表中没有保存终保日期所以需要从保单基本信息表中获取 end%>                    
	tdInsuredDate.innerHTML = '<%=strInsuredDate%>';

	//*****理赔车辆信息PrpLthirdParty*****  
	<% //获得赔偿限额
	double dblSumAmount3 = 0;
	PrpCitemKindDto prpCitemKindDto = null;
	if (policyDto.getPrpCitemKindDtoList() != null) {
		for (index = 0; index < policyDto.getPrpCitemKindDtoList().size(); index++) {
			prpCitemKindDto = (PrpCitemKindDto) policyDto.getPrpCitemKindDtoList().get(index);
			strCalculateFlag = prpCitemKindDto.getCalculateFlag();
			if (strCalculateFlag.equals("N")) {
				dblSumAmount3 += prpCitemKindDto.getAmount();
			}
		}
	}

	//modify by lixiaohua 20040326 begin reason 赔款计算书厂牌型号打印不出
	if (policyDto.getPrpCitemCarDtoList() != null) {
		for (index = 0; index < intItemCarCount; index++) {
			prpItemCarDto = (PrpCitemCarDto) policyDto.getPrpCitemCarDtoList().get(index);
			strLicenseNo = StringConvert.encode(prpItemCarDto.getLicenseNo());
			strLicenseColorCode = StringConvert.encode(prpItemCarDto.getLicenseColorCode());
			strSeatCount = "" + prpItemCarDto.getSeatCount();
			strTonCount = "" + prpItemCarDto.getTonCount();
			strEngineNo = StringConvert.encode(prpItemCarDto.getEngineNo());
			strFrameNo = StringConvert.encode(prpItemCarDto.getFrameNo());
			strCarInsuredRelation = StringConvert.encode(prpItemCarDto.getCarInsuredRelation());
			strCarInsuredRelationName = uiCodeAction.translateCodeCode("CarInsuredRelation", strCarInsuredRelation, true);
			strUseNatureCode = StringConvert.encode(prpItemCarDto.getUseNatureCode());
			if (strUseNatureCode.equals("1") || strUseNatureCode.equals("2") || strUseNatureCode.equals("3")) {
				strUseNatureCode = "非营业"; //非营业
			} else {
				strUseNatureCode = "营业"; //营业
			} %>
				tdUseNatureCode.innerHTML = '<%=strUseNatureCode%>';
			tdLicenseNo.innerHTML = '<%=StringConvert.encode(prpItemCarDto.getLicenseNo())%>';
			tdBrandName.innerHTML = '<%=StringConvert.encode(prpItemCarDto.getBrandName())%>';
			tdCarInsuredRelationName.innerHTML = '<%=strCarInsuredRelationName%>';
			tdSeatTonCount.innerHTML = '<%=strSeatCount + "座/" + strTonCount + "吨"%>';
			tdEngineNo.innerHTML = '<%=StringConvert.encode(prpItemCarDto.getEngineNo())%>';
			tdFrameNo.innerHTML = '<%=StringConvert.encode(prpItemCarDto.getFrameNo())%>';

			<% //System.out.println(prpItemCarDto.getLicenseNo()+" "+prpItemCarDto.getBrandName()+" ppppppppppppppppppppppppp");
			//条款类别
			strCode = "";
			strName = "";
			strCode = StringConvert.encode(prpItemCarDto.getClauseType());
			strName = uiCodeAction.translateCodeCode("ClauseType", strCode, isChinese); %> <%
		}
	}

	if (policyDto.getPrpCitemCarDtoList() != null) {
		//*****机动车险标的信息表PrpCitemCar*****    
		for (index = 0; index < intItemCarCount; index++) {
			prpItemCarDto = (PrpCitemCarDto) policyDto.getPrpCitemCarDtoList().get(index);
			strLicenseNo1 = StringConvert.encode(prpItemCarDto.getLicenseNo());

			if (strLicenseNo1.equals(strLicenseNo)) { %>
				//tdPurchasePrice.innerHTML = '<%=new DecimalFormat("#,##0.00").format(prpItemCarDto.getPurchasePrice())%>';
				<%
			}
		}
	}

	//*****标的子险信息PrpTitemKind*****
	dblSumAmount1 = 0;
	dblSumAmount2 = 0;

	if (policyDto.getPrpCitemKindDtoList() != null) {
		int tempindex = 0; //定义临时变量
		for (index = 0; index < intItemKindCount; index++) {
			//System.out.println("循環了幾次"+index+1);

			prpItemKindDto = (PrpCitemKindDto) policyDto.getPrpCitemKindDtoList().get(index);
			//add begin by zhuly 20051103 reason:增加子險信息
			strRiskCode = prpItemKindDto.getRiskCode();
			strKindCode = prpItemKindDto.getKindCode();
			strKindName = prpItemKindDto.getKindName();
			strKindAmount = "" + prpItemKindDto.getAmount();
			strFlag = prpItemKindDto.getFlag();
			//System.out.println("kjkjkj"+prpItemKindDto.getFlag().substring(1,2));
			//add end by zhuly 20051103
			//strRiskCode = prpItemKindDto.getRiskCode();
			//strKindCode = prpItemKindDto.getKindCode();
			//dbPrpDkind.getInfo(strRiskCode,strKindCode);
			//strCalculateFlag = dbPrpDkind.getCalculateFlag().substring(0,2);
			strCalculateFlag = prpItemKindDto.getCalculateFlag();
			//strCalculateFlag="Y1";
			if (strFlag.substring(1, 2).equals("1") && strKindCode.equals(ConstantCodes.KINDCODE_D_A)) //主险車損險保額 
			{
				dblSumAmount1 += prpItemKindDto.getAmount();
			}
			if (strFlag.substring(1, 2).equals("1") && strKindCode.equals(ConstantCodes.KINDCODE_D_B)) //主险三者險限額
			{
				dblSumAmount2 += prpItemKindDto.getAmount();
			}
			if (strFlag.substring(1, 2).equals("2") && tempindex < 6) //附加险
			{
				subKindName[tempindex] = strKindName;
				subKindAmount[tempindex] = "" + new DecimalFormat("#,##0.00").format(prpItemKindDto.getAmount()); %>
				//<%="tdKindName" + tempindex%>.innerHTML = '<%=subKindName[tempindex]%>'; 
				//<%="tdKindAmount" + tempindex%>.innerHTML = '<%=subKindAmount[tempindex]%>';              
				<% tempindex++;
			}
		}
	} %>
	//tdSumAmount1.innerHTML = '<%=new DecimalFormat("#,##0.00").format(policyDto.getPrpCmainDto().getSumAmount())%>';
	tdSumAmount2.innerHTML = 'CNY' + '&nbsp;' + '<%=new DecimalFormat("#,##0.00").format(dblSumAmount3)%>';
	//tdDeductibleRate.innerHTML = '<%=prpItemKindDto.getDeductibleRate()%>'+'％';   
	<%
	if (compensateDto.getPrpLctextDtoDtoList() != null) {
		//*****赔款计算文字表PrpLctext*****
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
	//    String la="\\";
	//    String lala="\\r\\n";

	for (index = 0; index < strContext.length(); index++) {
		if (strContext.substring(index, index + 1).equals("\\")) {
			if (!(strContext.substring(index).length() < 4)) {
				if (strContext.substring(index, index + 4).equals("\\r\\n")) {
					intCtextCountTmp += 1; //只要有回车换行，intCtextCountTmp+1
				}
			}
		}
	}

	int x = 0;
	int y = 0;
	if (!(strContext.length() < 4)) //如果strContext.length()>=4，判断strContext结尾是文字，还是回车换行
	{
		x = strContext.length() - 4;
		y = strContext.length();
		if (!strContext.substring(x, y).equals("\\r\\n")) {
			intCtextCountTmp += 1;
		}
	} else
	//如果strContext不足1行，intCtextCountTmp = 1; 
		intCtextCountTmp = 1;

	if (intCtextCountTmp > 20) {
		strContext = strContext.substring(0, 1000) + "<br>计算书信息过多，请详见清单。";
	}
	while (strContext.indexOf("\\r\\n") != -1)
		strContext = strContext.substring(0, strContext.indexOf("\\r\\n")) + "<br>&nbsp;" + strContext.substring(strContext.indexOf("\\r\\n") + "\\r\\n".length()); %>
		tdContext.innerHTML = '<%=strContext%>'; <% //*****赔款费用信息表PrpLcharge*****    
	dblJudgeFee = 0; //鉴定费
	dblCheckFee = 0; //查勘费
	dblLawFee = 0; //诉讼费
	dblElseFee = 0; //其它
	dblCheckFee1 = 0; //代查费
	dblAssessFee = 0; //公估费
	dblRescueFee = 0; //施救费
	dblGsjzFee = 0; //公损救助费
	dblFlFee = 0; //法律费
	if (compensateDto.getPrpLchargeDtoList() != null) {
		for (index = 0; index < intChargeCount; index++) {
			prpLchargeDto = (PrpLchargeDto) compensateDto.getPrpLchargeDtoList().get(index);
			strChargeCode = StringConvert.encode(prpLchargeDto.getChargeCode());

			if (strChargeCode.equals("07")) //鉴定费
			{
				dblJudgeFee += prpLchargeDto.getChargeAmount();
			}
			//else if( strChargeCode.equals("06") )  //代查勘费
			//{    
			//dblCheckFee1 += prpLchargeDto.getChargeAmount();
			//}   
			else if (strChargeCode.equals("05")) //诉讼费
			{
				dblLawFee += prpLchargeDto.getChargeAmount();
			} else if (strChargeCode.equals("04")) //查勘费
			{
				dblCheckFee += prpLchargeDto.getChargeAmount();
			} else if (strChargeCode.equals("13")) //公估费
			{
				dblAssessFee += prpLchargeDto.getChargeAmount();
			//} else if (strChargeCode.equals("03")) //施救费
			//{
				//dblRescueFee += prpLchargeDto.getSumRealPay();
			} else if (strChargeCode.equals("09")) //代理费
			{
				dblAgentFee += prpLchargeDto.getChargeAmount();
			} else if (strChargeCode.equals("11")) //共损救助费
			{
				dblGsjzFee += prpLchargeDto.getChargeAmount();
			} else if (strChargeCode.equals("15")) //法律费
			{
				dblFlFee += prpLchargeDto.getChargeAmount();
			} else //其它
			{
				dblElseFee += prpLchargeDto.getChargeAmount();
			}
			//      else                                   //其他费用
			//     {    
			//        dblElseFee  += prpLchargeDto.getChargeAmount();
			//      }   
		}
	} %>
	//检验鉴定费
	//tdJudgeFee.innerHTML = '<%=dblJudgeFee > 0 ? (strCurrency) : ("")%>'+'&nbsp;'+'<%=dblJudgeFee > 0 ? (new DecimalFormat("#,##0.00").format(dblJudgeFee)) : (DataUtils.zeroToEmpty(dblJudgeFee))%>';

	//查勘费    
	//tdCheckFee.innerHTML = '<%=dblCheckFee > 0 ? (strCurrency) : ("")%>'+'&nbsp;' + '<%=dblCheckFee > 0 ? new DecimalFormat("#,##0.00").format(dblCheckFee) : DataUtils.zeroToEmpty(dblCheckFee)%>';

	//待查费
	//tdCheckFee1.innerHTML = '<%=strCurrency%>'+'&nbsp;'+'<%=dblCheckFee1 > 0 ? (new DecimalFormat("#,##0.00").format(dblCheckFee1)) : (DataUtils.zeroToEmpty(dblCheckFee1))%>';

	//诉讼费
	//tdLawFee.innerHTML = '&nbsp;' + '<%=dblLawFee > 0 ? new DecimalFormat("#,##0.00").format(dblLawFee) : DataUtils.zeroToEmpty(dblLawFee)%>';

	//法律费
	//tdFlFee.innerHTML = '<%=dblFlFee > 0 ? (strCurrency) : ("")%>'+'&nbsp;' + '<%=dblFlFee > 0 ? new DecimalFormat("#,##0.00").format(dblFlFee) : DataUtils.zeroToEmpty(dblFlFee)%>';

	//共损救助费
	//tdGsjzFee.innerHTML = '<%=dblGsjzFee > 0 ? (strCurrency) : ("")%>'+'&nbsp;' + '<%=dblGsjzFee > 0 ? new DecimalFormat("#,##0.00").format(dblGsjzFee) : DataUtils.zeroToEmpty(dblGsjzFee)%>';

	//施救费
	//tdRescueFee.innerHTML = '<%=dblRescueFee > 0 ? (strCurrency) : ("")%>'+'&nbsp;'+'<%=dblRescueFee > 0 ? new DecimalFormat("#,##0.00").format(dblRescueFee) : DataUtils.zeroToEmpty(dblRescueFee)%>';
	//其它    
	//tdElseFee.innerHTML = '<%=dblElseFee > 0 ? (strCurrency) : ("")%>'+'&nbsp;' + '<%=dblElseFee > 0 ? (new DecimalFormat("#,##0.00").format(dblElseFee)) : (DataUtils.zeroToEmpty(dblElseFee))%>';

	//公估费
	//tdAssessFee.innerHTML = '<%=dblAssessFee > 0 ? (strCurrency) : ("")%>'+'&nbsp;' + '<%=dblAssessFee > 0 ? new DecimalFormat("#,##0.00").format(dblAssessFee) : DataUtils.zeroToEmpty(dblAssessFee)%>';

	//代理费
	//tdAgentFee.innerHTML = '&nbsp;'   + '<%=DataUtils.zeroToEmpty(dblAgentFee)%>';


	//tdClaimNo.innerHTML = '&nbsp;'   + '<%=strClaimNo%>';

	<% strSumThisPaid = new DecimalFormat("#,##0.00").format(dblSumThisPaid - dblJudgeFee - dblCheckFee - dblLawFee - dblElseFee - dblSumPrePaid - dblSumRest);
	if (dblSumThisPaid - dblJudgeFee - dblCheckFee - dblLawFee - dblElseFee - dblSumPrePaid - dblSumRest < 0) {
		strCSumThisPaid = "负" + MoneyUtils.toChinese(-(dblSumThisPaid - dblJudgeFee - dblCheckFee - dblLawFee - dblElseFee - dblSumPrePaid - dblSumRest), prpLcompensateDto.getCurrency());
	} else {
		strCSumThisPaid = MoneyUtils.toChinese(dblSumThisPaid - dblJudgeFee - dblCheckFee - dblLawFee - dblElseFee - dblSumPrePaid - dblSumRest, prpLcompensateDto.getCurrency());
	} %>
	//tdCSumThisPaid.innerHTML  = '&nbsp;本次实付赔款（人民币大写）：'  + '<%=strCSumThisPaid%>';    
	//tdSumThisPaid.innerHTML   = '（￥：'                        + '<%=strSumThisPaid%>';
	<% //*****车辆信息表PrpLdriver*****  
	if (registDto.getPrpLdriverDtoList() != null) {
		intDriverCount = registDto.getPrpLdriverDtoList().size();
		for (index = 0; index < intDriverCount; index++) {
			prpLdriverDto = (PrpLdriverDto) registDto.getPrpLdriverDtoList().get(index); %>
			//tdDriverName.innerHTML         = '<%=StringConvert.encode(prpLdriverDto.getDriverName())%>';
			//tdDriverName1.innerHTML         = '<%=StringConvert.encode(prpLdriverDto.getDriverName())%>';

			<%
		}
	} %> <% String claimType = prpLregistDto.getClaimType();
	String strClaimType = uiCodeAction.translateCodeCode("CaseCode", claimType, true);
	String handleUnit = prpLregistDto.getHandleUnit();
	String strHandleUnit = uiCodeAction.translateCodeCode("HandleUnit", handleUnit, true); %>
	//tdClaimType.innerHTML = '<%=strClaimType%>';
	//tdHandleUnit.innerHTML = '<%=strHandleUnit%>';
	//tdRunAreaName.innerHTML = '<%=prpLregistDto.getDamageAreaName()%>';

	//tdLossRate.innerHTML    = '<%=new DecimalFormat("#,##0.00").format(prpLclaimDto.getIndemnityDutyRate())%>'+'％';
	//tdRemark.innerHTML = '备注：';
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
			//tdEndorseNo.innerHTML  = '<%=prpCplanDto.getEndorseNo()%>';
			//tdRemark.innerHTML = '备注：'+'<br>'+'保费缴费情况：'+'<%=strPlanFee%>' + '<br>'+'保费收讫日期：'+'<%=prpCplanDto.getPlanDate()%>'+'<br>'+'收款人：'+'';
			//tdSerialNo.innerHTML  = '已预付次数：'+'<%=DataUtils.zeroToEmpty(prpCplanDto.getSerialNo() - 1)%>';
			<%
		}
	} %> <% prpLextDto = registDto.getPrpLextDto();
	if (prpLextDto != null) {
		long personInjureB = prpLextDto.getPersonInjureB();
		long personDeathB = prpLextDto.getPersonDeathB();
		long personInjureD1 = prpLextDto.getPersonInjureD1();
		long personDeathD1 = prpLextDto.getPersonDeathD1();
		String personInjure1 = "";
		String personInjure2 = "";
		String personInjure = "第三者（伤 " + personInjureB + " 人，亡 " + personDeathB + " 人），车上人员（伤 " + personInjureD1 + " 人， 亡 " + personDeathD1 + " 人）";

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
		//tdPersonInjure.innerHTML = '<%=personInjure%>';
		<%
	} %> <% String strEndorseNo = "";
	if (endorseDto.getPrpPheadDtoList() != null) {
		for (index = 0; index < endorseDto.getPrpPheadDtoList().size(); index++) {
			PrpPheadDto prpPheadDto = (PrpPheadDto) endorseDto.getPrpPheadDtoList().get(index);
			strEndorseNo = prpPheadDto.getEndorseNo(); %>
				tdEndorseNo.innerHTML = '<%=strEndorseNo%>';
			tdInsuredName.innerHTML = '<%=prpLclaimDto.getInsuredName()%>'; <%
		}
	} %>

}
</script>
  