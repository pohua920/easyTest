<%--
****************************************************************************
* DESC       ：货运险赔款计算书打印页面初始化
* AUTHOR     ：wangwei
* CREATEDATE ：2005-5-21
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>

<%-- 引入bean类部分 --%>
<%@page import="java.text.*"%>
<%@page import="com.sinosoft.claim.ui.control.action.*"%>
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="com.sinosoft.claim.dto.domain.*"%>
<%@page import="com.sinosoft.claim.bl.facade.*"%>
<%@page import="com.sinosoft.claim.util.*"%>
<%@page import="com.sinosoft.sysframework.common.util.*"%>
<%@page import="com.sinosoft.sysframework.common.datatype.DateTime"%>
<%@page import="com.sinosoft.sysframework.exceptionlog.*"%>
<%@page import="com.sinosoft.function.insutil.bl.facade.BLPubRateFacade"%>
<%@page import="com.sinosoft.utiall.dbsvr.DBPrpDcode"%>
<%@page import="com.sinosoft.utiall.blsvr.BLPrpDuser"%>

<%
	//变量声明部分
	String strCompensateNo = request.getParameter("CompensateNo"); //赔款计算书号
	String strRiskName = ""; //险种名称
	String strClaimNo = ""; //赔案号
	String strPolicyNo = ""; //保单号
	String strClassCode = ""; //险类
	double dblSumAmount1 = 0; //保险金额
	String strKindCode = ""; //险别代码
	String strKindName = ""; //险别名称
	String strInvoiceNo = ""; //发票号
	String strCount = ""; //保品及件数
	String strItemDetial = ""; //保品
	String strQuantity = ""; //件数
	String strConveyance = ""; //运输工具
	String strAgenName = ""; //理赔代理人
	String strTPath = ""; //运输路线
	DateTime dateDamageStartDate = null; //出险日期
	String strDamageStartDate = ""; //出险日期
	String strCause = ""; //出险原因
	String strAddress = ""; //出险地点
	String strCountExp = ""; //赔款计算公式
	String strStartDate = ""; ///起保（起运）日期-格式显示

	//赔款核定
	double dblClaim = 0; //赔款核定
	double dblHelp = 0.0d; //施救费用
	double dblAgen = 0; //代理费
	double dblCheck = 0; //检验费
	double dblExamine = 0; //查勘费
	double dblRest = 0; //其它费用
	double dblSum = 0; //赔款合计
	double dblEndMoney = 0; //本案已付赔款金额
	double dblTrueMoney = 0; //结案时实付赔款金额

	//代码翻译变量
	String strCode = "";
	String strName = "";
	boolean isChinese = true; //中文标志

	//对象定义部分
	PrpCitemCarDto prpItemcarDto = null; //ItemcarDto对象
	PrpCitemKindDto prpItemKindDto = null; //保单的ItemKindDto对象
	PrpLclaimDto prpLclaimDto = null; //ClaimDto对象

	PrpLcompensateDto prpLcompensateDto = null; //CompensateDto对象
	PrpLchargeDto prpLchargeDto = null; //ChargeDto对象
	PrpLctextDto prpLctextDto = null;
	PrpLlossDto prpLlossDto = null;

	int intCompensateCount = 0; //CompensateDto对象的记录数
	int intItemCarCount = 0; //ItemcarDto对象的记录数
	int intItemKindCount = 0; //ItemKindDto对象的记录数
	int intThirdPartyCount = 0; //ThirdPartyDto对象的记录数
	int intChargeCount = 0; //ChargeDto对象的记录数
	int intCtextCount = 0; //CtextDto对象的记录数
	int intCtextCountTmp = 0; //textarea行数
	int intLossCount = 0; //LossDto对象的记录数
	int index = 0;
	DateTime dataTime = new DateTime();
	DateTime exchDate = new DateTime(dataTime.current(), DateTime.YEAR_TO_DAY);
	BLPubRateFacade blPubRateFacade = new BLPubRateFacade();
	double douExchangeRate = 0d;
	double douSumRealPay = 0.0;
	String strCurrency4 = "";
	String strCurrency4Show = "";
	double douSumRealPayShow = 0.0;
	double douSumRealPayCNY = 0.0;
	double douSumPay = 0.0;

	//得到ClaimDto,RegistDto,CertainLossDto,PolicyDto对象
	ClaimDto claimDto = (ClaimDto) request.getAttribute("claimDto");
	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");
	RegistDto registDto = (RegistDto) request.getAttribute("registDto");
	CheckDto checkDto = (CheckDto) request.getAttribute("checkDto");
	CompensateDto compensateDto = (CompensateDto) request.getAttribute("compensateDto");
	CertainLossDto certainLossDto = (CertainLossDto) request.getAttribute("certainLossDto");

	UICodeAction uiCodeAction = new UICodeAction();
	//得到prpLcompensateDto 对象
	prpLcompensateDto = compensateDto.getPrpLcompensateDto();

	//得到prpLclaimDto对象
	prpLclaimDto = claimDto.getPrpLclaimDto();

	strClaimNo = prpLclaimDto.getClaimNo();
	String strApproverCode = prpLcompensateDto.getApproverCode(); //复核员代码
	String strOperatorCode = prpLcompensateDto.getOperatorCode(); //操作员代码
	String strApproverName = uiCodeAction.translateUserCode(strApproverCode, isChinese);
	String strOperatorName = uiCodeAction.translateUserCode(strOperatorCode, isChinese);

	String strOperator = ""; //理赔人
	Date strInputDate = null; //理赔日期
	String strUnderWriteName = ""; //核赔人
	Date strUnderWriteEndDate = null; //核赔日期
	String strChecker = ""; //查勘人
	Date dateCheckDate = null; //查勘日期
	String strCheckDateYear = "   ";
	String strCheckDateMonth = "   ";
	String strCheckDateDate = "   ";
	if (prpLcompensateDto != null) {
		strOperator = prpLcompensateDto.getOperatorCode();
		BLPrpDuser blPrpDuser = new BLPrpDuser();
		blPrpDuser.query("UserCode='" + strOperator + "'");
		strOperator = blPrpDuser.getArr(0).getUserName();
		strInputDate = prpLcompensateDto.getInputDate();
		strUnderWriteName = prpLcompensateDto.getUnderWriteName();
		strUnderWriteEndDate = prpLcompensateDto.getUnderWriteEndDate();
	}
	PrpLcheckDto prpLcheckDto = new PrpLcheckDto();
	BLPrpLcheckFacade blPrpLcheckFacade = new BLPrpLcheckFacade();
	prpLcheckDto = blPrpLcheckFacade.findByPrimaryKey(prpLclaimDto.getRegistNo(), 1);
	if (prpLcheckDto != null) {
		strChecker = prpLcheckDto.getChecker1();
		dateCheckDate = prpLcheckDto.getCheckDate();
		//strCheckDateYear  = dateCheckDate.getYear();
		//strCheckDateMonth = dateCheckDate.getMonth();
		//strCheckDateDate  = dateCheckDate.getDate();
	}

	//得到保单号
	strPolicyNo = prpLclaimDto.getPolicyNo();
	strClassCode = prpLclaimDto.getClassCode();
	strRiskName = uiCodeAction.translateRiskCode(prpLclaimDto.getRiskCode(), true);

	//得到blPrpCitemCar对象
	if (policyDto.getPrpCitemCarDtoList() != null) {
		intItemCarCount = policyDto.getPrpCitemCarDtoList().size();
	}

	//得到blPrpCitemKind对象的记录数
	if (policyDto.getPrpCitemKindDtoList() != null) {
		intItemKindCount = policyDto.getPrpCitemKindDtoList().size();
	}

	//得到blPrpLcharge对象的记录数
	if (compensateDto.getPrpLchargeDtoList() != null) {
		intChargeCount = compensateDto.getPrpLchargeDtoList().size();
	}
	//得到dbPrpLctext对象的记录数
	if (compensateDto.getPrpLctextDtoDtoList() != null) {
		intCtextCount = compensateDto.getPrpLctextDtoDtoList().size();
	}
	if (compensateDto.getPrpLlossDtoList() != null) {
		intLossCount = compensateDto.getPrpLlossDtoList().size();
	}
	String strSumPaid = new DecimalFormat("#,##0.00").format(prpLcompensateDto.getSumPaid());
	double dbSumPaid = prpLcompensateDto.getSumPaid();
	String strCurrency1 = prpLcompensateDto.getCurrency();
	String strSumPaidCNY = "";
	if (strCurrency1.equals("CNY")) {
		strSumPaidCNY = strSumPaid;
	} else {
		douExchangeRate = blPubRateFacade.getExchangeRate(strCurrency1, "CNY", exchDate);
		strSumPaidCNY = new DecimalFormat("#,##0.00").format(douExchangeRate * dbSumPaid);
	}
	String strCSumPaid = "零圆";
	if (prpLcompensateDto.getSumPaid() != 0) {
		strCSumPaid = MoneyUtils.toChinese(prpLcompensateDto.getSumPaid(), prpLcompensateDto.getCurrency());
	}
	String strSumPrePaid = new DecimalFormat("#,##0.00").format(prpLcompensateDto.getSumPrePaid());
	String strCSumPrePaid = "零圆";
	if (prpLcompensateDto.getSumPrePaid() != 0) {
		strCSumPrePaid = MoneyUtils.toChinese(prpLcompensateDto.getSumPrePaid(), prpLcompensateDto.getCurrency());
	}
	String strSumThisPaid = new DecimalFormat("#,##0.00").format(prpLcompensateDto.getSumThisPaid());
	String strCSumThisPaid = "零圆";
	if (prpLcompensateDto.getSumThisPaid() != 0) {
		strCSumThisPaid = MoneyUtils.toChinese(prpLcompensateDto.getSumThisPaid(), prpLcompensateDto.getCurrency());
	}
	//起保（起运）日期
	DateTime StartDate = policyDto.getPrpCmainDto().getStartDate();
	strStartDate = StartDate.getYear() + "年" + StartDate.getMonth() + "月" + StartDate.getDate() + "日";
	dateDamageStartDate = prpLclaimDto.getDamageStartDate();
	strDamageStartDate = dateDamageStartDate.getYear() + "年" + dateDamageStartDate.getMonth() + "月" + dateDamageStartDate.getDate() + "日";
	String strInsuredDateTime = ""; //保险期限描述
	DateTime dateInsuredStartDate = null;
	DateTime dateInsuredEndDate = null;
	dateInsuredStartDate = policyDto.getPrpCmainDto().getStartDate();
	dateInsuredEndDate = policyDto.getPrpCmainDto().getEndDate();
	strInsuredDateTime = dateInsuredStartDate.getYear() + "年" + dateInsuredStartDate.getMonth() + "月" + dateInsuredStartDate.getDate() + "日--" + dateInsuredEndDate.getYear() + "年" + dateInsuredEndDate.getMonth() + "月" + dateInsuredEndDate.getDate() + "日";
%>

<script language="javascript">
function loadForm() {
	tdClaimNo.innerHTML = '<%=strClaimNo%>';

	//*****赔款计算书表PrpLcompensate*****
	tdCompensateNo.innerHTML = '赔款计算书号：' + '<%=strCompensateNo%>';

	//*****立案信息表PrpLclaim*****
	tdPolicyNo.innerHTML = '<%=strPolicyNo%>';
	tdInsuredName.innerHTML = '<%=StringConvert.encode(prpLclaimDto.getInsuredName())%>';
	tdSumAmount.innerHTML = '<%=prpLclaimDto.getCurrency()%><%=new DecimalFormat("#,##0.00").format(prpLclaimDto.getSumAmount())%>';
	tdDamageTypeName.innerHTML = '<%=prpLclaimDto.getDamageName()%>';
	tdDamageAddress.innerHTML = '<%=prpLclaimDto.getDamageAddress()%>';

	tdCheckAgentName.innerHTML = '<%=prpLcompensateDto.getCheckAgentName()%>';
	<%
	if (strClassCode.equals("09") || strClassCode.equals("10")) { %>
			tdSumPaid.innerHTML = '<%=strCurrency1%><%=strSumPaid%>'; <%
	} else { %>
			tdSumPaid.innerHTML = '<%=strSumPaid%>'; <%
	} %>
		tdSumPaidCNY.innerHTML = '<%=strSumPaidCNY%>'; <%
	if (strClassCode.equals("09") || strClassCode.equals("10")) { %>
			tdCSumPaid.innerHTML = '<%=strCSumPaid%>(<%=strSumPaid%>)'; <%
	} else { %>
			tdCSumPaid.innerHTML = '<%=strCSumPaid%>'; <%
	} %>
		tdSumPrePaid.innerHTML = '<%=strCSumPrePaid%>(<%=strSumPrePaid%>)';
	tdSumThisPaid.innerHTML = '<%=strCSumThisPaid%>(<%=strSumThisPaid%>)'; <%
	for (index = 0; index < intLossCount; index++) {
		prpLlossDto = (PrpLlossDto) compensateDto.getPrpLlossDtoList().get(index);
		double douDeductibleRate = prpLlossDto.getDeductibleRate();
		double douDeductible = prpLlossDto.getDeductible();

		douSumRealPay = prpLlossDto.getSumRealPay();
		strCurrency4 = prpLlossDto.getCurrency4();

		if (index == 0) {
			strCurrency4Show = strCurrency4;
			douSumRealPayShow += douSumRealPay;
		} else if (strCurrency4Show.equals(strCurrency4)) {
			douSumRealPayShow += prpLlossDto.getSumRealPay();
		} else {
			strCurrency4Show = "";
		}
		if (strCurrency4.equals("CNY")) {
			douSumRealPayCNY += douSumRealPay;
		} else {
			douExchangeRate = blPubRateFacade.getExchangeRate(strCurrency4, "CNY", exchDate);
			douSumRealPayCNY += douSumRealPay * douExchangeRate;
		}
	}

	String strSumRealPayShow = ""; //合计金额格式显示
	strSumRealPayShow = new DecimalFormat("#,##0.00").format(douSumRealPayShow);

	if (!strCurrency4Show.equals("")) {
		if (strClassCode.equals("09") || strClassCode.equals("10")) { %>
				tdSumRealpay.innerHTML = '<%=strCurrency4Show%><%=strSumRealPayShow%>'; <%
		} else { %>
				tdSumRealpay.innerHTML = '<%=strSumRealPayShow%>'; <%
		}
	}
	if (strClassCode.equals("09") || strClassCode.equals("10")) { %>
			tdCurrency4.innerHTML = '<%=douSumRealPayCNY%>'; <%
	} else { %>
			tdCurrency4.innerHTML = '<%=strCurrency4Show%>'; <%
	}

	blPubRateFacade = new BLPubRateFacade();
	douExchangeRate = 0d;
	double douChargeAmountCNY = 0d;
	for (index = 0; index < intChargeCount; index++) {
		prpLchargeDto = (PrpLchargeDto) compensateDto.getPrpLchargeDtoList().get(index);
		String strChargeName = prpLchargeDto.getChargeName();
		String strCurrency = prpLchargeDto.getCurrency();
		double douChargeAmount = prpLchargeDto.getSumRealPay();
		String strChargeAmount = new DecimalFormat("###0.00").format(douChargeAmount);
		if (strCurrency.equals("CNY")) {
			douChargeAmountCNY = douChargeAmount;
		} else {
			douExchangeRate = blPubRateFacade.getExchangeRate(strCurrency, "CNY", exchDate);
			douChargeAmountCNY = douChargeAmount * douExchangeRate;
		}
		//System.out.println("=====================strChargeName="+strChargeName+"-");
		//System.out.println("=====================douChargeAmount="+douChargeAmount+"-");
		if (strChargeName.equals("共同海损")) {
			if (strClassCode.equals("09") || strClassCode.equals("10")) { %>
					tdChargeAmount1.innerHTML = '<%=strCurrency%><%=strChargeAmount%>';
				tdChargeAmountCNY1.innerHTML = '<%=douChargeAmountCNY%>'; <%
			} else { %>
					tdChargeAmount1.innerHTML = '<%=strChargeAmount%>';
				tdChargeAmountCNY1.innerHTML = '<%=strCurrency%>'; <%
			}
		}
		if (strChargeName.equals("施救费")) {
			if (strClassCode.equals("09") || strClassCode.equals("10")) { %>
					tdChargeAmount2.innerHTML = '<%=strCurrency%><%=strChargeAmount%>';
				tdChargeAmountCNY2.innerHTML = '<%=douChargeAmountCNY%>'; <%
			} //if(货运与财险显示不同)
		}
		if (strChargeName.equals("代理费")) {
			if (strClassCode.equals("09") || strClassCode.equals("10")) { %>
					tdChargeAmount3.innerHTML = '<%=strCurrency%><%=strChargeAmount%>';
				tdChargeAmountCNY3.innerHTML = '<%=douChargeAmountCNY%>'; <%
			} else { %>
					tdChargeAmount3.innerHTML = '<%=strChargeAmount%>';
				tdChargeAmountCNY3.innerHTML = '<%=strCurrency%>'; <%
			}
		}
		if (strChargeName.equals("检验费")) {
			if (strClassCode.equals("09") || strClassCode.equals("10")) { %>
					tdChargeAmount4.innerHTML = '<%=strCurrency%><%=strChargeAmount%>';
				tdChargeAmountCNY4.innerHTML = '<%=douChargeAmountCNY%>'; <%
			} else { %>
					tdChargeAmount4.innerHTML = '<%=strChargeAmount%>';
				tdChargeAmountCNY4.innerHTML = '<%=strCurrency%>'; <%
			}
		}
		if (strChargeName.equals("查勘费")) {
			if (strClassCode.equals("09") || strClassCode.equals("10")) { %>
					tdChargeAmount5.innerHTML = '<%=strCurrency%><%=strChargeAmount%>';
				tdChargeAmountCNY5.innerHTML = '<%=douChargeAmountCNY%>'; <%
			} else { %>
					tdChargeAmount5.innerHTML = '<%=strChargeAmount%>';
				tdChargeAmountCNY5.innerHTML = '<%=strCurrency%>'; <%
			}
		}
		if (strChargeName.equals("其他")) {
			if (strClassCode.equals("09") || strClassCode.equals("10")) { %>
					tdChargeAmount6.innerHTML = '<%=strCurrency%><%=strChargeAmount%>';
				tdChargeAmountCNY6.innerHTML = '<%=douChargeAmountCNY%>'; <%
			} else { %>
					tdChargeAmount6.innerHTML = '<%=strChargeAmount%>';
				tdChargeAmountCNY6.innerHTML = '<%=strCurrency%>'; <%
			}
		}
	} %> <%
	for (index = 0; index < intItemKindCount; index++) {
		prpItemKindDto = (PrpCitemKindDto) policyDto.getPrpCitemKindDtoList().get(index);
		if (prpItemKindDto.getFlag().substring(1, 2).equals("1")) {
			strKindName = strKindName + prpItemKindDto.getKindName() + "<br>";
		}
		//zhulei leave:以下过滤方法只能过滤掉连续的同名标的，对於不连续的还需要循环判断，这里还需要修改
		if (!prpItemKindDto.getItemDetailName().equals(strItemDetial)) {
			if (strClassCode.equals("09") || strClassCode.equals("10")) {
				strCount += prpItemKindDto.getItemDetailName() + new DecimalFormat("###0").format(prpItemKindDto.getQuantity()) + prpItemKindDto.getModel() + "<br>";
			} else {
				strCount += prpItemKindDto.getItemDetailName() + "  ";
			}
			strItemDetial = prpItemKindDto.getItemDetailName();
		}
	}
	if (strClassCode.equals("09") || strClassCode.equals("10")) {
		DBPrpDcode dbPrpDcode = new DBPrpDcode();
		dbPrpDcode.getInfo("ConveyanceType", policyDto.getPrpCmainCargoDto().getConveyance());
		strConveyance = dbPrpDcode.getCodeCName();
	}
	%>
	tdCount.innerHTML = '<%=strCount%>';
	//fm.itemCount.value = <%=strCount%>
	tdKindName.innerHTML = '<%=strKindName%>';
	tdConveyance.innerHTML = '<%=strConveyance%>'; <% //System.out.println(".............test.....test......test22......");
	////System.out.println(policyDto.getPrpCmainCargoDto().getInvoiceNo());
	String strBLNo = ""; //车号、航班号
	if (strClassCode.equals("09") || strClassCode.equals("10")) {
		strBLNo = policyDto.getPrpCmainCargoDto().getBLNo();
		String strViaSiteName = policyDto.getPrpCmainCargoDto().getViaSiteName();
		if (strViaSiteName.equals("")) { %>
				tdLindline.innerHTML = '由 <%=policyDto.getPrpCmainCargoDto().getStartSiteName()%> 到 <%=policyDto.getPrpCmainCargoDto().getEndSiteName()%>'; <%
		} else { %>
				tdLindline.innerHTML = '由 <%=policyDto.getPrpCmainCargoDto().getStartSiteName()%> 经 <%=policyDto.getPrpCmainCargoDto().getViaSiteName()%> 到 <%=policyDto.getPrpCmainCargoDto().getEndSiteName()%>'; <%
		}
	}
	String strContext = "";
	String strContext2 = "";
	for (index = 0; index < intCtextCount; index++) {
		prpLctextDto = (PrpLctextDto) compensateDto.getPrpLctextDtoDtoList().get(index);
		if (prpLctextDto.getTextType().equals("1")) {
			strContext += prpLctextDto.getContext();
		} else {
			strContext2 += prpLctextDto.getContext();
		}
	}
	//System.out.println(strContext);

	String strUnloadDate = ""; //卸货日期
	if (strClassCode.equals("09") || strClassCode.equals("10")) {
		PrpLextDto prpLextDto = new PrpLextDto();
		UICheckAction uiCheckAction = new UICheckAction();
		checkDto = uiCheckAction.findByPrimaryKey(claimDto.getPrpLclaimDto().getRegistNo());
		prpLextDto = checkDto.getPrpLextDto();
		if (prpLextDto != null) {
			//System.out.println(".............prpLextDto.getUnloadDate()=========="+prpLextDto.getUnloadDate()+"--");
			if (prpLextDto.getUnloadDate() != null && !prpLextDto.getUnloadDate().toString().equals("")) {
				strUnloadDate = prpLextDto.getUnloadDate().getYear() + "年" + prpLextDto.getUnloadDate().getMonth() + "月" + prpLextDto.getUnloadDate().getDate() + "日";
			}
		}
	} %>
	//tdContext.innerHTML = '<>';
}
</script>
  