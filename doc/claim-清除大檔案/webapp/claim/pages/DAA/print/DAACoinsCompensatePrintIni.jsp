<%--

****************************************************************************

* DESC       ：理赔流联共保打印页初始化

* AUTHOR     ：GuoXu

* CREATEDATE ：2005-12-26

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
<%@page import="com.sinosoft.claim.bl.facade.*"%>
<%@page import="com.sinosoft.claim.ui.control.viewHelper.*"%>
<%
	int i = 0;
	int j = 0;
	double sumCoinsRate = 0.0;
	double sumCoinsPaid0 = 0.0;
	double sumCoinsPaid1 = 0.0;
	double sumAllPaid = 0.0;
	String strClaimNo = "";
	String[] strCoinsFlag = null;
	String coinsFlag = "";
	String[] strBusinessNo = null;
	String businessNo = "";
	String comCode = "";
	String comName = "";
	int[] intSerialNo = null;
	String[] strRiskCode = null;
	String[] policyNo = null;
	String[] strPolicyNo = null;
	String[] strCurrency = null;
	String[] strCoinsCode = null;
	String[] strCoinsCodeCfee = null;
	String[] strCoinsName = null;
	String[] strCoinsType = null;
	double[] dbCoinsRate = null;
	String[] strChiefFlag = null;
	String[] strLossFeeType = null;
	String[] strChargeCode = null;
	String[] strChargeName = null;

	double[] dbSumpaid = null;
	double[] dbCoinsSumpaid = null;
	double[] coinsSumPaid0 = null;
	double[] coinsSumPaid1 = null;

	String[] strFlag = null;
	double dbSumCoinsPaid0 = 0.0;
	double dbSumCoinsPaid1 = 0.0;
	CompensateDto compensateDto = (CompensateDto) request.getAttribute("compensateDto");
	businessNo = compensateDto.getPrpLcompensateDto().getCompensateNo();
	comCode = compensateDto.getPrpLcompensateDto().getComCode();
	strClaimNo = compensateDto.getPrpLcompensateDto().getClaimNo();
	UICodeAction uiCodeAction = new UICodeAction();
	comName = uiCodeAction.translateComCode(comCode, true);

	Collection collection = null;
	//System.out.println("businessNo ='"+businessNo+"'");
	String conditions = "businessNo='" + businessNo + "'";
	BLPrpLcfeecoinsFacade blPrpLcfeecoinsFacade = new BLPrpLcfeecoinsFacade();
	collection = blPrpLcfeecoinsFacade.findByConditions(conditions);
	if (collection != null) {
		Iterator iterator = collection.iterator();
		intSerialNo = new int[collection.size()];
		policyNo = new String[collection.size()];
		strRiskCode = new String[collection.size()];
		strCurrency = new String[collection.size()];
		strCoinsCodeCfee = new String[collection.size()];
		strCoinsType = new String[collection.size()];
		strChiefFlag = new String[collection.size()];
		strLossFeeType = new String[collection.size()];
		strChargeCode = new String[collection.size()];
		strChargeName = new String[collection.size()];
		dbSumpaid = new double[collection.size()];
		dbCoinsSumpaid = new double[collection.size()];
		strFlag = new String[collection.size()];
		i = 0;
		while (iterator.hasNext()) {
			PrpLcfeecoinsDto prpLcfeecoinsDto = (PrpLcfeecoinsDto) iterator.next();
			intSerialNo[i] = prpLcfeecoinsDto.getSerialNo();
			policyNo[i] = prpLcfeecoinsDto.getPolicyNo();
			strRiskCode[i] = prpLcfeecoinsDto.getRiskCode();
			strCurrency[i] = prpLcfeecoinsDto.getCurrency();
			strCoinsCodeCfee[i] = prpLcfeecoinsDto.getCoinsCode();
			strCoinsType[i] = prpLcfeecoinsDto.getCoinsType();
			strChiefFlag[i] = prpLcfeecoinsDto.getChiefFlag();
			strLossFeeType[i] = prpLcfeecoinsDto.getLossFeeType();
			strChargeCode[i] = prpLcfeecoinsDto.getChargeCode();
			strChargeName[i] = prpLcfeecoinsDto.getChargeName();
			dbSumpaid[i] = prpLcfeecoinsDto.getSumPaid();
			dbCoinsSumpaid[i] = prpLcfeecoinsDto.getCoinsSumPaid();
			strFlag[i] = prpLcfeecoinsDto.getFlag();

			i++;
		}
	}

	Collection collection1 = null;
	//modify by wangliguang begin
	//reaso：查询联共保信息应该以PolicyNo（本公司产生的保单号）为条件
	String conditions1 = "PolicyNo='" + policyNo[1] + "'";
	//modify by wangliguang end
	BLPrpCcoinsFacade blPrpCcoinsFacade = new BLPrpCcoinsFacade();
	collection1 = blPrpCcoinsFacade.findByConditionsChiefFlag(conditions1);
	if (collection1 != null) {
		Iterator iterator1 = collection1.iterator();
		strPolicyNo = new String[collection1.size()];
		dbCoinsRate = new double[collection1.size()];
		strCoinsCode = new String[collection1.size()];
		strCoinsName = new String[collection1.size()];
		coinsSumPaid0 = new double[collection1.size()];
		coinsSumPaid1 = new double[collection1.size()];
		j = 0;
		while (iterator1.hasNext()) {
			PrpCcoinsDtoBase prpCcoinsDtoBase = (PrpCcoinsDtoBase) iterator1.next();
			strPolicyNo[j] = prpCcoinsDtoBase.getPolicyNo();
			dbCoinsRate[j] = prpCcoinsDtoBase.getCoinsRate();
			strCoinsCode[j] = prpCcoinsDtoBase.getCoinsCode();
			strCoinsName[j] = prpCcoinsDtoBase.getCoinsName();
			j++;
		}
	}

	for (int k = 0; k < j; k++) {
		for (int m = 0; m < i; m++) {
			if (strCoinsCode[k].equals(strCoinsCodeCfee[m]) && Integer.parseInt(strLossFeeType[m]) == 0) {
				coinsSumPaid0[k] += dbCoinsSumpaid[m];
			} else if (strCoinsCode[k].equals(strCoinsCodeCfee[m]) && Integer.parseInt(strLossFeeType[m]) == 1) {
				coinsSumPaid1[k] += dbCoinsSumpaid[m];
			}
		}
	}

	EndorseViewHelper endorseViewHelper = new EndorseViewHelper();
	UIClaimAction uiClaimAction = new UIClaimAction();
	ClaimDto claimDto = uiClaimAction.findByPrimaryKey(strClaimNo);
	PolicyDto policyDto = endorseViewHelper.findForEndorBefore(compensateDto.getPrpLcompensateDto().getPolicyNo(), claimDto.getPrpLclaimDto().getDamageStartDate().toString(), claimDto.getPrpLclaimDto().getDamageStartHour());
	coinsFlag = policyDto.getPrpCmainDto().getCoinsFlag();
	if (Integer.parseInt(coinsFlag) == 1) {
		coinsFlag = "共保";
	} else if (Integer.parseInt(coinsFlag) == 3) {
		coinsFlag = "联保";
	}

	String mDateTime = ""; //出单日期
	Calendar cal = Calendar.getInstance();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	mDateTime = formatter.format(cal.getTime());
	String strCurerncy1 = strCurrency[0];
	//System.out.println("初始化结束");
%>