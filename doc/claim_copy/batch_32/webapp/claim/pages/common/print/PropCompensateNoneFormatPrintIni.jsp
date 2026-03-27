<%--
****************************************************************************
* DESC       ：财产险赔款计算书打印页面初始化
* AUTHOR     ：hanliang
* CREATEDATE ：2005-12-12
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
<%@ page import="com.sinosoft.claim.dto.domain.PrpPheadDto" %> 
<%@ page import="java.util.*" %>
<jsp:directive.page import="com.sinosoft.claim.bl.facade.BLPrpCcoinsFacade"/>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.sinosoft.claim.bl.facade.BLPrpLcfeecoinsFacade"%>
<%
	//变量声明部分
	String strCompensateNo = ""; //赔款计算书号
	//System.out.println("strCompensateNo = "+strCompensateNo);
	String strClaimNo = ""; //赔案号
	String strPolicyNo = ""; //保单号
	double dblSumAmount1 = 0; //保险金额      
	String strKindCode = ""; //险别代码
	String strCause = ""; //出险原因
	String strAddress = ""; //出险地点
	String strDamageAddress = ""; //出险地点
	String strDamageStartDate = ""; //出险时间
	String strReportHour = ""; //出险小时
	String strTextType = "";
	String strContext = ""; //赔款计算书文字
	String strCountExp = ""; //赔款计算公式
	String strInsuredDate = "";
	String strInsuredName = ""; //被保险人
	String strInsuredAddress = ""; //保险财产地址
	String strEndorseNo = ""; //批单号码

	String strChargeCode = ""; //

	//赔款核定
	double dblJudgeFee = 0; //鉴定费
	double dblCheckFee = 0; //查勘费
	double dblLawFee = 0; //诉讼费
	double dblElseFee = 0; //其他费用
	double dblSumPrePaid = 0; //
	double dblSumRest = 0; //
	double dblCheckFee1 = 0; //代查勘费
	double dblAssessFee = 0; //公估费
	double dblRescueFee = 0; //施救费
	double dblAgentFee = 0; //代理费
	double dblGsjzFee = 0; //共损救助费
	double dblFlFee = 0; //法律费  
	double dblPropSumLossPay = 0; //财产损失
	double dblPersonSumLossPay = 0; //人伤损失
	double dblSumLossPay = 0; //标的赔款
	double dblSumprepaid = 0; //预付赔款 add by liping 080811

	double dblSumLoss = 0; //核定损失，保险标的损失
	String strCurrency2 = ""; //核定损失，保险标的损失币别
	String strSumPaid = ""; //赔款合计格式化
	String strMySumPaid = ""; //我司赔款金额
	String strOtherSumPaid = ""; //我司代付金额
	String strCSumPaid = ""; //赔款合计大写
	String strSumLossPay = ""; //标的赔款
	String strSumprepaid = ""; //预付赔款 add by liping 080811
	String strCSumLossPay = ""; //标的赔款中文
	double dblSumPaid = 0; //赔款合计
	double dbMySumPaid = 0;//我司应付金额
	double dbOtherSumPaid = 0;//我司代付赔款金额
	//代码翻译变量
	String strCode = "";
	String strName = "";
	boolean isChinese = true; //中文标志

	//对象定义部分
	PrpLclaimDto prpLclaimDto = null; //ClaimDto对象
	PrpLcompensateDto prpLcompensateDto = null; //CompensateDto对象
	PrpLctextDto prpLctextDto = null;

	int intCompensateCount = 0; //CompensateDto对象的记录数
	int intItemCarCount = 0; //ItemcarDto对象的记录数
	int intItemKindCount = 0; //ItemKindDto对象的记录数
	int intThirdPartyCount = 0; //ThirdPartyDto对象的记录数
	int intChargeCount = 0; //ChargeDto对象的记录数
	int intCtextCount = 0; //CtextDto对象的记录数
	int intCtextCountTmp = 0; //textarea行数

	int index = 0;

	//得到ClaimDto,RegistDto,CertainLossDto,PolicyDto对象
	CompensateDto compensateDto = (CompensateDto) request.getAttribute("compensateDto");
	ClaimDto claimDto = (ClaimDto) request.getAttribute("claimDto");
	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");
	RegistDto registDto = (RegistDto) request.getAttribute("registDto");
	CheckDto checkDto = (CheckDto) request.getAttribute("checkDto");
	PrpLchargeDto prpLchargeDto = null;
	CertainLossDto certainLossDto = (CertainLossDto) request.getAttribute("certainLossDto");
	//System.out.println("@@@@@@1111@@@@@@@@@@@");

	UIEndorseAction uiEndorseAction = new UIEndorseAction();
	UICodeAction uiCodeAction = new UICodeAction();
	//得到prpLcompensateDto 对象
	prpLcompensateDto = compensateDto.getPrpLcompensateDto();
	strCompensateNo = prpLcompensateDto.getCompensateNo();
	//得到prpLclaimDto对象
	prpLclaimDto = claimDto.getPrpLclaimDto();

	strClaimNo = prpLclaimDto.getClaimNo();
	String strApproverCode = prpLcompensateDto.getApproverCode(); //复核员代码
	String strOperatorCode = prpLcompensateDto.getOperatorCode(); //操作员代码
	String strApproverName = uiCodeAction.translateUserCode(strApproverCode, isChinese);
	String strOperatorName = uiCodeAction.translateUserCode(strOperatorCode, isChinese);
	//System.out.println("@@@@@@@@@@@@@222@@@@");

	//得到保单号
	strPolicyNo = prpLclaimDto.getPolicyNo();
	PrpCmainDto prpCmainDto = policyDto.getPrpCmainDto(); //得到保单主表对象 
	//add by zhangyurui 取共保比例 begin
	double coinsRate = 1;
	ArrayList PrpCcoinsDtoList = (ArrayList) new BLPrpCcoinsFacade().findByConditions(" policyNO='" + prpCmainDto.getPolicyNo() + "' and coinsType='1' ", 0, 0);
	if (null != PrpCcoinsDtoList && PrpCcoinsDtoList.size() > 0) {
		PrpCcoinsDto prpCcoinsDto = (PrpCcoinsDto) PrpCcoinsDtoList.get(0);
		coinsRate = prpCcoinsDto.getCoinsRate() / 100;
	}
	//add by zhangyurui 取共保比例 end

	strInsuredAddress = prpCmainDto.getInsuredAddress(); //保险财产地址
	//从共、从联保额显示总保额
	if ("2".equals(prpCmainDto.getCoinsFlag()) || "4".equals(prpCmainDto.getCoinsFlag())) {
		BigDecimal bd1 = new BigDecimal(new DecimalFormat(".00").format(prpCmainDto.getSumAmount()));
		BigDecimal bd2 = new BigDecimal(new DecimalFormat(".00").format(coinsRate));
		dblSumAmount1 = bd1.divide(bd2, BigDecimal.ROUND_HALF_UP).doubleValue();
	} else {
		dblSumAmount1 = prpCmainDto.getSumAmount(); //保险金额
	}

	String strCurrency = prpCmainDto.getCurrency();

	//获取保险标的
	String itemDetailName = "";
	PrpCitemKindDto prpCitemKindDto = new PrpCitemKindDto();
	ArrayList prpCItemKindDtoList = new ArrayList();
	prpCItemKindDtoList = policyDto.getPrpCitemKindDtoList();
	if (prpCItemKindDtoList.size() == 1) {
		prpCitemKindDto = (PrpCitemKindDto) prpCItemKindDtoList.get(0);
		itemDetailName = prpCitemKindDto.getItemDetailName();
	}
	if (prpCItemKindDtoList.size() == 2) {
		prpCitemKindDto = (PrpCitemKindDto) prpCItemKindDtoList.get(0);
		String str1 = prpCitemKindDto.getItemDetailName();
		prpCitemKindDto = (PrpCitemKindDto) prpCItemKindDtoList.get(1);
		String str2 = prpCitemKindDto.getItemDetailName();
		itemDetailName = str1 + "," + str2;
	}
	if (prpCItemKindDtoList.size() > 2) {
		prpCitemKindDto = (PrpCitemKindDto) prpCItemKindDtoList.get(0);
		String str1 = prpCitemKindDto.getItemDetailName();
		prpCitemKindDto = (PrpCitemKindDto) prpCItemKindDtoList.get(1);
		String str2 = prpCitemKindDto.getItemDetailName();
		if ("".equals(str2))
			itemDetailName = str1 + "等";
		else
			itemDetailName = str1 + "," + str2 + "等";
	}

	String RiskCode = ""; //保单险种代码
	String strRiskName = ""; //保单险种名称
	RiskCode = prpCmainDto.getRiskCode();//
	strRiskName = uiCodeAction.translateRiskCode(RiskCode, true);

	EndorseDto endorseDto = uiEndorseAction.findByConditions(strPolicyNo);//根据保单号得到批单对象
	ArrayList prpPheadDtoList = endorseDto.getPrpPheadDtoList();
	if (prpPheadDtoList != null && prpPheadDtoList.size() > 0) {
		PrpPheadDto prpPheadDto = (PrpPheadDto) endorseDto.getPrpPheadDtoList().get(0);
		strEndorseNo = prpPheadDto.getEndorseNo();
		//System.out.println("=======strEndorseNo:" + strEndorseNo);
	} else {
		strEndorseNo = "";
	}

	PrpLregistDto prpLregistDto = registDto.getPrpLregistDto(); //得到报案信息对象
	//modify by zhangyurui 2009-02-24 被保险人从报案表取 begin
	strInsuredName = prpLregistDto.getInsuredName(); //被保险人
	//modify by zhangyurui 2009-02-24 被保险人从报案表取 end
	strDamageAddress = prpLregistDto.getDamageAddress();
	strDamageStartDate = prpLregistDto.getDamageStartDate().getYear() + "年" + prpLregistDto.getDamageStartDate().getMonth() + "月" + prpLregistDto.getDamageStartDate().getDate() + "日";
	strReportHour = prpLregistDto.getReportHour().toString();

	//得到blPrpCitemCar对象 
	/*
	 if(policyDto.getPrpCitem_carDtoList()!=null){
	 intItemCarCount     = policyDto.getPrpCitem_carDtoList().size();
	 }  
	 */
	//得到blPrpCitemKind对象的记录数
	if (policyDto.getPrpCitemKindDtoList() != null) {
		intItemKindCount = policyDto.getPrpCitemKindDtoList().size();
	}
	int indexl = 0;
	//得到blPrpLcharge对象的记录数
	if (compensateDto.getPrpLchargeDtoList() != null) {
		indexl = compensateDto.getPrpLchargeDtoList().size();
	}
	//得到dbPrpLctext对象的记录数  
	if (compensateDto.getPrpLctextDtoDtoList() != null) {
		intCtextCount = compensateDto.getPrpLctextDtoDtoList().size();
	}
	String strEndMoney = new DecimalFormat("#,##0.00").format(prpLcompensateDto.getSumPrePaid());
	String strCEndMoney = MoneyUtils.toChinese(prpLcompensateDto.getSumPrePaid(), prpLcompensateDto.getCurrency());
	//dblSumPaid = prpLcompensateDto.getSumPaid();
	String currency = prpLcompensateDto.getCurrency();
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
		BigDecimal bigDblSumPaid = new BigDecimal(Double.toString(prpLcompensateDto.getSumPaid()));
		BigDecimal bigDblPropSumLossPay = new BigDecimal(Double.toString(compensateDto.getPrpLcompensateDto().getLossSumRealPay()));
		BigDecimal bigDblPersonSumLossPay = new BigDecimal(Double.toString(compensateDto.getPrpLcompensateDto().getPersonLossSumRealPay()));
		BigDecimal bigDblSumprepaid = new BigDecimal(Double.toString(compensateDto.getPrpLcompensateDto().getSumPrePaid()));
		dblSumPaid = bigDblSumPaid.multiply(bigCoinsRate).doubleValue();
		dblPropSumLossPay = bigDblPropSumLossPay.multiply(bigCoinsRate).doubleValue();//财产损失
		dblPersonSumLossPay = bigDblPersonSumLossPay.multiply(bigCoinsRate).doubleValue();//人伤损失
		dblSumprepaid = bigDblSumprepaid.multiply(bigCoinsRate).doubleValue();//预赔
	} else {
		dblSumPaid = prpLcompensateDto.getSumPaid();
		if ("1".equals(prpCmainDto.getCoinsFlag()) || "3".equals(prpCmainDto.getCoinsFlag())) {//主（联、共）保
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

		dblPropSumLossPay = compensateDto.getPrpLcompensateDto().getLossSumRealPay(); //财产损失
		dblPersonSumLossPay = compensateDto.getPrpLcompensateDto().getPersonLossSumRealPay(); //人伤损失
		dblSumprepaid = (compensateDto.getPrpLcompensateDto()).getSumPrePaid();
	}

	strSumPaid = new DecimalFormat("#,##0.00").format(dblSumPaid);
	if (dblSumPaid < 0) {
		dblSumPaid = -dblSumPaid;
		strCSumPaid = "负" + MoneyUtils.toChinese(dblSumPaid, prpLcompensateDto.getCurrency());
		dblSumPaid = -dblSumPaid;
	} else {
		strCSumPaid = MoneyUtils.toChinese(dblSumPaid, prpLcompensateDto.getCurrency());
	}

	strMySumPaid = new DecimalFormat("#,##0.00").format(dbMySumPaid);
	strOtherSumPaid = new DecimalFormat("#,##0.00").format(dbOtherSumPaid);
	//modify by liuwei at 2011-07-27 从（联、共）保显示我司份额 end
	String strCurrency1 = compensateDto.getPrpLcompensateDto().getCurrency();

	// add by liping 080811
	strSumprepaid = new DecimalFormat("#,##0.00").format(dblSumprepaid);

	dblSumLossPay = dblPropSumLossPay + dblPersonSumLossPay; //标的赔款
	strSumLossPay = new DecimalFormat("#,##0.00").format(dblSumLossPay);

	//

	//modify by wangliguang 20080507 begin
	//reason:解决财产险赔付为负时，打印赔款理算数报错
	if (dblSumLossPay >= 0) {
		strCSumLossPay = MoneyUtils.toChinese(dblSumLossPay, prpLcompensateDto.getCurrency());
	} else {
		strCSumLossPay = MoneyUtils.toChinese(Math.abs(dblSumLossPay), prpLcompensateDto.getCurrency());
		strCSumLossPay = "负值" + strCSumLossPay;
	}
	//modify by wangliguang 20080507 end

	dblJudgeFee = 0;
	dblCheckFee = 0;
	dblLawFee = 0;
	dblElseFee = 0;
	dblCheckFee1 = 0;
	dblAssessFee = 0;
	dblRescueFee = 0;
	String bibie = "";
	String MJudgeFee = "CNY";
	String MCheckFee = "CNY";
	String MAssessFee = "CNY";
	String MRescueFee = "CNY";
	String MAgentFee = "CNY";
	String MElseFee = "CNY";
	String MGsjzFee = "CNY";
	String MFlFee = "CNY";
	String MLawFee = "CNY";

	intChargeCount = compensateDto.getPrpLchargeDtoList().size();
	if (compensateDto.getPrpLchargeDtoList() != null) {
		for (indexl = 0; indexl < intChargeCount; indexl++) {
			prpLchargeDto = (PrpLchargeDto) compensateDto.getPrpLchargeDtoList().get(indexl);
			strChargeCode = StringConvert.encode(prpLchargeDto.getChargeCode());

			if (strChargeCode.equals("04")) //查勘费
			{
				dblCheckFee += prpLchargeDto.getChargeAmount();
				MCheckFee = prpLchargeDto.getCurrency();
			} else if (strChargeCode.equals("13")) //公估费
			{
				dblAssessFee += prpLchargeDto.getChargeAmount();
				MAssessFee = prpLchargeDto.getCurrency();
			//} else if (strChargeCode.equals("03")) //施救费
			//{
				//dblRescueFee += prpLchargeDto.getSumRealPay(); //施救费计入赔款了     
				//MRescueFee = prpLchargeDto.getCurrency();
			} else if (strChargeCode.equals("05")) //诉讼费
			{
				dblLawFee += prpLchargeDto.getChargeAmount();
				MLawFee = prpLchargeDto.getCurrency();
			} else //其它
			{
				dblElseFee += prpLchargeDto.getChargeAmount();
				MElseFee = prpLchargeDto.getCurrency();
			}
		}
	}

	if (compensateDto.getPrpLlossDtoList() != null && compensateDto.getPrpLlossDtoList().size() > 0) {

		PrpLlossDto prpLlossDto = (PrpLlossDto) compensateDto.getPrpLlossDtoList().get(0); // 得到赔付标的信息对象
		dblSumLoss = prpLlossDto.getSumLoss();
		bibie = prpLlossDto.getCurrency2();
		strCurrency2 = prpLlossDto.getCurrency2();
		strCurrency2 = uiCodeAction.translateCurrencyCode(strCurrency2, true);
	}

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
		int length = strContext.length();
		if (length >= 1200) {
			strContext = strContext.substring(0, 1200) + "<br>计算书信息过多，请详见清单。";
		} else {
			strContext = strContext.substring(0, length);
		}
	}
	while (strContext.indexOf("\\r\\n") != -1)
		strContext = strContext.substring(0, strContext.indexOf("\\r\\n")) + "<br>" + strContext.substring(strContext.indexOf("\\r\\n") + "\\r\\n".length());
%>

<script language="javascript">
function loadForm() {

	//*****赔款计算书表PrpLcompensate*****
	//tdCompensateNo.innerHTML = '<%=strCompensateNo%>';

	//*****立案信息表PrpLclaim*****
	tdPolicyNo.innerHTML = '<%=strPolicyNo%>';
	//tdInsuredName.innerHTML  = '<%=StringConvert.encode(prpLclaimDto.getInsuredName())%>';
	tdLossName.innerHTML = '<%=itemDetailName%>';
	//tdSumAmount.innerHTML = '<%=new DecimalFormat("#,##0.00").format(prpLclaimDto.getSumAmount())%>';
	<% strDamageStartDate = prpLclaimDto.getDamageStartDate().getYear() + "年" + prpLclaimDto.getDamageStartDate().getMonth() + "月" + prpLclaimDto.getDamageStartDate().getDate() + "日"; %>
		tdDamageStartDate.innerHTML = '<%=strDamageStartDate%>'; <% //modify by liuwei at 2011-04-27 由於有的险种在立案基本信息表中没有保存终保日期所以需要从保单基本信息表中获取 start
	if (prpLclaimDto.getEndDate().isEmpty()) {
		strInsuredDate = "自 " + prpCmainDto.getStartDate().getYear() + "年" + prpCmainDto.getStartDate().getMonth() + "月" + prpCmainDto.getStartDate().getDate() + "日&nbsp;&nbsp;&nbsp;零&nbsp;&nbsp;&nbsp;时起" + "至 " + prpCmainDto.getEndDate().getYear() + "年" + prpCmainDto.getEndDate().getMonth() + "月" + prpCmainDto.getEndDate().getDate() + "日 二十四 时止";
	} else {
		strInsuredDate = "自 " + prpLclaimDto.getStartDate().getYear() + "年" + prpLclaimDto.getStartDate().getMonth() + "月" + prpLclaimDto.getStartDate().getDate() + "日&nbsp;&nbsp;&nbsp;零&nbsp;&nbsp;&nbsp;时起" + "至 " + prpLclaimDto.getEndDate().getYear() + "年" + prpLclaimDto.getEndDate().getMonth() + "月" + prpLclaimDto.getEndDate().getDate() + "日 二十四 时止";
	}
	//modify by liuwei at 2011-04-27 由於有的险种在立案基本信息表中没有保存终保日期所以需要从保单基本信息表中获取 end%>   
	tdInsuredDate.innerHTML = '<%=strInsuredDate%>';
	tdDamageAddress.innerHTML = '<%=prpLclaimDto.getDamageAddress()%>';
	tdInsuredName.innerHTML = '<%=strInsuredName%>';
	tdInsuredAddress.innerHTML = '<%=strInsuredAddress%>';
	tdSumAmount1.innerHTML = '<%=strCurrency%>' + '&nbsp;' + '<%=new DecimalFormat("#,##0.00").format(dblSumAmount1)%>';
	tdEndorseNo.innerHTML = '<%=strEndorseNo%>';
	tdDamageAddress.innerHTML = '<%=strDamageAddress%>';
	tdDamageStartDate.innerHTML = '<%=strDamageStartDate%>' + '<%=strReportHour.substring(0, 2)%>' + '时';
	tdContext.innerHTML = '<%="赔款计算：<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + strContext%>';
	//tdSumLoss.innerHTML = '<%=new DecimalFormat("#,##0.00").format(dblSumLoss)%>';
	tdSumLoss.innerHTML = '<%=bibie%>' + '&nbsp;' + '<%=new DecimalFormat("#,##0.00").format(dblSumLossPay)%>';

	tdCurrency2.innerHTML = '&nbsp;' + '<%=strCurrency2%>';
	tdCurrency22.innerHTML = '<%=strCurrency2%>';
	tdClaimNo.innerHTML = '<%=strClaimNo%>';
	tdMySumPaid.innerHTML = '&nbsp;<%=strCurrency1%>&nbsp;' + '<%=strMySumPaid%>';
	tdOtherSumPaid.innerHTML = '&nbsp;<%=strCurrency1%>&nbsp;' + '<%=strOtherSumPaid%>';
	//施救费
	//dblRescueFee>0?(new DecimalFormat("#,##0.00").format(dblRescueFee)):(DataUtils.zeroToEmpty(dblRescueFee))
	//tdRescueFee.innerHTML = '&nbsp;'+ '<%=new DecimalFormat("#,##0.00").format(dblRescueFee)%>';  
	//查勘费
	//出现问题    tdCheckFee.innerHTML = '<%=bibie%>'+'&nbsp;' + '<%=new DecimalFormat("#,##0.00").format(dblCheckFee)%>';
	//检验鉴定费
	// tdJudgeFee.innerHTML = '&nbsp;'+ '<%=new DecimalFormat("#,##0.00").format(dblJudgeFee)%>';
	//共损救助费
	//tdGsjzFee.innerHTML = '&nbsp;'+ '<%=new DecimalFormat("#,##0.00").format(dblGsjzFee)%>';
	//公估费
	// tdAssessFee.innerHTML = '<%=bibie%>'+'&nbsp;'+ '<%=new DecimalFormat("#,##0.00").format(dblAssessFee)%>';
	//法律费
	// tdFlFee.innerHTML = '&nbsp;'+ '<%=new DecimalFormat("#,##0.00").format(dblFlFee)%>';
	//其它
	//tdElseFee.innerHTML  = '<%=bibie%>'+'&nbsp;' + '<%=new DecimalFormat("#,##0.00").format(dblElseFee)%>';

	// tdMRescueFee.innerHTML = '&nbsp;'+ '<%=uiCodeAction.translateCurrencyCode(MRescueFee, true)%>';
	// modify 出现问题 tdMCheckFee.innerHTML = '&nbsp;'+ '<%=uiCodeAction.translateCurrencyCode(MCheckFee, true)%>';
	//tdMJudgeFee.innerHTML = '&nbsp;'+ '<%=uiCodeAction.translateCurrencyCode(MJudgeFee, true)%>';
	//tdMGsjzFee.innerHTML = '&nbsp;'+ '<%=uiCodeAction.translateCurrencyCode(MGsjzFee, true)%>';
	// tdMAssessFee.innerHTML = '&nbsp;'+ '<%=uiCodeAction.translateCurrencyCode(MAssessFee, true)%>';
	// tdMFlFee.innerHTML = '&nbsp;'+ '<%=uiCodeAction.translateCurrencyCode(MFlFee, true)%>';
	//  tdMElseFee.innerHTML = '&nbsp;'+ '<%=uiCodeAction.translateCurrencyCode(MElseFee, true)%>';

	//tdAgentFee.innerHTML = '&nbsp;'+'<%=new DecimalFormat("#,##0.00").format(dblAgentFee)%>';
	//tdMAgentFee.innerHTML = '&nbsp;'+ '<%=uiCodeAction.translateCurrencyCode(MAgentFee, true)%>';

	//tdSumPaid.innerHTML = '&nbsp;'+ '<%=strSumPaid%>';
}
</script>
  