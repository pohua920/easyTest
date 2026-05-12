<%--
****************************************************************************
* DESC       ：出险後抄单打印页初始化
* AUTHOR     ： 项目组
* CREATEDATE ：2005-9-16
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

<%
	int i = 0;
	String strClassCode = ""; //险类
	String strPolicyNo = ""; //保单号
	String strRegistNo = ""; //报案号
	String strPolicyName = ""; //被保险人名称
	String strPolicyAddress = ""; //被保险人地址
	String strCoinsFlag = ""; //联共保标志
	String strCurrency = "";
	double dbSumAmount = 0.0;
	DateTime OperateDate = new DateTime();
	DateTime InputDate = new DateTime();
	DateTime SignDate = new DateTime();
	DateTime UnderWriteEndDate = new DateTime();
	DateTime StartDate = new DateTime();
	DateTime EndDate = new DateTime();

	String strRiskCode = ""; //险种代码
	String strRiskName = ""; //险种名称

	String[] strKindName = null; //险别名称
	//add by zhulei 20050917 产品信息
	String[] arrItemDetailName = null; //产品名称
	String[] arrItemFlag = null; //险别标志：主险/附加险
	String[] arrModel = null; //产品规格类型
	double[] dbAmount = null; //预计销售额
	double dbAmountSum = 0; //预计销售额合计
	String[] arrCurrency = null; //币种

	//add by zhulei 20050917 主险地址
	String[] arrKindAddress = null;

	String[] strEndorseNo = null;
	String[] strEndorType = null;
	DateTime[] strEndorDate = null;
	String[] strUnderWriteName = null;

	int[] iPlanNo = null;
	String[] strPlanEndorNo = null;
	double[] dbPlanFee = null;
	double[] dbRealFee = null;
	DateTime[] strPayDate = null;

	//add by zhulei 20050915 特别约定信息
	String[] arrEngage = null; //逐条特约信息
	String[] arrClauseCode = null; //附加/特约信息代码
	String[] arrTitleFlag = null; //名称/内容 标志
	String strEngage = ""; //特约信息汇总

	Collection collection = null;
	UICodeAction uiCodeAction = new UICodeAction();

	RegistDto registDto = (RegistDto) request.getAttribute("registDto");
	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");
	EndorseDto endorseDto = (EndorseDto) request.getAttribute("endorseDto");
	if (registDto != null && registDto.getPrpLregistDto() != null) {
		strPolicyNo = registDto.getPrpLregistDto().getPolicyNo();
		strClassCode = registDto.getPrpLregistDto().getClassCode();
		strRegistNo = registDto.getPrpLregistDto().getRegistNo();
	}
	if (policyDto != null && policyDto.getPrpCmainDto() != null) {
		strPolicyName = policyDto.getPrpCmainDto().getInsuredName();
		strPolicyAddress = policyDto.getPrpCmainDto().getAppliAddress();
		strCoinsFlag = policyDto.getPrpCmainDto().getCoinsFlag().toString();
		dbSumAmount = policyDto.getPrpCmainDto().getSumAmount();
		strCurrency = policyDto.getPrpCmainDto().getCurrency();
		OperateDate = policyDto.getPrpCmainDto().getOperateDate();
		InputDate = policyDto.getPrpCmainDto().getInputDate();
		SignDate = policyDto.getPrpCmainDto().getSignDate();
		UnderWriteEndDate = policyDto.getPrpCmainDto().getUnderWriteEndDate();
		StartDate = policyDto.getPrpCmainDto().getStartDate();
		EndDate = policyDto.getPrpCmainDto().getEndDate();
		strRiskName = uiCodeAction.translateRiskCode(policyDto.getPrpCmainDto().getRiskCode(), true);
	}
	if (endorseDto.getPrpPheadDtoList() != null) {
		collection = endorseDto.getPrpPheadDtoList();
		strEndorseNo = new String[collection.size()];
		strEndorType = new String[collection.size()];
		strEndorDate = new DateTime[collection.size()];
		strUnderWriteName = new String[collection.size()];
		i = 0;
		Iterator iterator = endorseDto.getPrpPheadDtoList().iterator();
		////System.out.println(iterator.getSize());
		while (iterator.hasNext()) {
			PrpPheadDto prpPheadDtoTemp = (PrpPheadDto) iterator.next();
			strEndorseNo[i] = prpPheadDtoTemp.getEndorseNo();
			strEndorType[i] = uiCodeAction.translateCodeCode("EndorType", prpPheadDtoTemp.getEndorType(), true);
			strEndorDate[i] = prpPheadDtoTemp.getEndorDate();
			strUnderWriteName[i] = prpPheadDtoTemp.getUnderWriteName();
			i++;
		}
	}
	if (policyDto.getPrpCplanDtoList() != null) {
		Iterator iterator = policyDto.getPrpCplanDtoList().iterator();
		collection = policyDto.getPrpCplanDtoList();
		iPlanNo = new int[collection.size()];
		strPlanEndorNo = new String[collection.size()];
		dbPlanFee = new double[collection.size()];
		dbRealFee = new double[collection.size()];
		strPayDate = new DateTime[collection.size()];
		i = 0;
		while (iterator.hasNext()) {
			PrpCplanDto prpCplanDtoTemp = (PrpCplanDto) iterator.next();

			iPlanNo[i] = prpCplanDtoTemp.getPayNo();
			if (prpCplanDtoTemp.getPayNo() == 0)
				iPlanNo[i] = 1;
			strPlanEndorNo[i] = prpCplanDtoTemp.getEndorseNo();
			dbPlanFee[i] = prpCplanDtoTemp.getPlanFee();
			dbRealFee[i] = prpCplanDtoTemp.getPlanFee() - prpCplanDtoTemp.getDelinquentFee();
			strPayDate[i] = prpCplanDtoTemp.getPlanDate();
			i++;
		}
	}
	if (policyDto.getPrpCitemKindDtoList() != null) {

		collection = policyDto.getPrpCitemKindDtoList();
		strKindName = new String[collection.size()];
		arrItemDetailName = new String[collection.size()]; //产品名称
		arrItemFlag = new String[collection.size()]; //险别标志：主险/附加险
		arrModel = new String[collection.size()]; //产品规格类型
		dbAmount = new double[collection.size()]; //预计销售额
		arrCurrency = new String[collection.size()]; //币种

		i = 0;
		Iterator iterator = policyDto.getPrpCitemKindDtoList().iterator();
		while (iterator.hasNext()) {
			PrpCitemKindDto prpCitemKindDtoTemp = (PrpCitemKindDto) iterator.next();
			strKindName[i] = prpCitemKindDtoTemp.getKindName();
			arrItemDetailName[i] = prpCitemKindDtoTemp.getItemDetailName(); //产品名称      
			arrItemFlag[i] = prpCitemKindDtoTemp.getFlag(); //险别标志：主险/附加险
			arrModel[i] = prpCitemKindDtoTemp.getModel(); //产品规格类型    
			dbAmount[i] = prpCitemKindDtoTemp.getAmount(); //预计销售额      
			dbAmountSum = dbAmountSum + dbAmount[i];
			arrCurrency[i] = prpCitemKindDtoTemp.getCurrency(); //币种            
			i++;
		}
	}

	//    if (strCoinsFlag.equals("0")) {         //非共保
	//        strCoinsFlag = "非共保";
	//    } else if (strCoinsFlag.equals("1")) {  //主共保
	//        strCoinsFlag = "主共保";
	//    } else if (strCoinsFlag.equals("2")) {  //共保
	//        strCoinsFlag = "共保";
	//    }
	if (strCoinsFlag.equals("0")) {
		strCoinsFlag = "否";
	} else {
		strCoinsFlag = "是";
	}

	//add by zhulei 20050915 特别约定
	if (policyDto.getPrpCengageDtoList() != null) {
		collection = policyDto.getPrpCengageDtoList();
		arrEngage = new String[collection.size()];
		arrClauseCode = new String[collection.size()];
		arrTitleFlag = new String[collection.size()];
		i = 0;
		Iterator iterator = policyDto.getPrpCengageDtoList().iterator();
		while (iterator.hasNext()) {
			PrpCengageDto prpCengageDto = (PrpCengageDto) iterator.next();
			arrEngage[i] = prpCengageDto.getClauses();
			arrTitleFlag[i] = prpCengageDto.getTitleFlag();
			arrClauseCode[i] = prpCengageDto.getClauseCode();
			//特约只显示标题，如果需要内容，把if(arrTitleFlag[i].equals("0"))去调就行了
			//if(arrTitleFlag[i].equals("0")){
			if (arrClauseCode[i].substring(0, 1).equals("T")) {
				strEngage = strEngage + arrEngage[i] + "<br>";
			}
			i++;
		}
	}

	//add by zhulei 20050917 主险地址
	if (policyDto.getPrpCaddressDtoList() != null) {
		collection = policyDto.getPrpCaddressDtoList();
		arrKindAddress = new String[collection.size()];
		i = 0;
		Iterator iterator = policyDto.getPrpCaddressDtoList().iterator();
		while (iterator.hasNext()) {
			PrpCaddressDto prpCaddressDto = (PrpCaddressDto) iterator.next();
			arrKindAddress[i] = prpCaddressDto.getAddressName();
			//System.out.println("================arrKindAddress=="+arrKindAddress[i]);
			i++;
		}
	}

	//add by zhulei 20050916 赔偿限额信息（这里用业务jar取数据）
	String strLimitType = ""; //赔偿限额/免赔额类别
	String strCurrency01 = ""; //每次事故赔偿限额币别
	String strCurrency03 = ""; //财产损失赔偿限额币别
	String strCurrency07 = ""; //人身伤亡赔偿限额币别
	String strCurrency02 = ""; //每人赔偿限额币别
	String strCurrency05 = ""; //每次事故绝对免赔币别

	double dbLimit01 = 0; //每次事故赔偿限额（累计）
	double dbLimit03 = 0; //财产损失赔偿限额（累计）
	double dbLimit07 = 0; //人身伤亡赔偿限额（累计）
	double dbLimit02 = 0; //每人赔偿限额（累计） 
	double dbLimit05 = 0; //每次事故绝对免赔（累计） 
	String strLimit01 = ""; //每次事故赔偿限额（显示格式0.00）
	String strLimit03 = ""; //财产损失赔偿限额（显示格式0.00）
	String strLimit07 = ""; //人身伤亡赔偿限额（显示格式0.00）
	String strLimit02 = ""; //每人赔偿限额（显示格式0.00）v
	String strLimit05 = ""; //每次事故绝对免赔（显示格式0.00）

	//add start by miaowenjun 20060907
	if (policyDto != null && policyDto.getPrpClimitDtoList() != null && policyDto.getPrpClimitDtoList().size() > 0) {
		for (i = 0; i < policyDto.getPrpClimitDtoList().size(); i++) {
			PrpClimitDto prpClimitDto = new PrpClimitDto();
			prpClimitDto = (PrpClimitDto) policyDto.getPrpClimitDtoList().get(i);
			strLimitType = prpClimitDto.getLimitType();
			if (prpClimitDto.getLimitGrade().equals("1")) {
				if (strLimitType.equals("02")) { //每次事故赔偿限额
					dbLimit01 = prpClimitDto.getLimitFee();
					strCurrency01 = prpClimitDto.getCurrency();
				} else if (strLimitType.equals("03")) { //财产损失赔偿限额
					dbLimit03 = prpClimitDto.getLimitFee();
					strCurrency03 = prpClimitDto.getCurrency();
				} else if (strLimitType.equals("05")) { //人身伤亡赔偿限额
					dbLimit07 = prpClimitDto.getLimitFee();
					strCurrency07 = prpClimitDto.getCurrency();
				} else if (strLimitType.equals("06")) { //每人赔偿限额
					dbLimit02 = prpClimitDto.getLimitFee();
					strCurrency02 = prpClimitDto.getCurrency();
				} else if (strLimitType.equals("05")) { //每次事故绝对免赔
					dbLimit05 = prpClimitDto.getLimitFee();
					strCurrency05 = prpClimitDto.getCurrency();
				}
			}
		}
	}
	//add end by miaowenjun 20060907
	//delete by miaowenjun 20060907
	/*
	BLPrpClimit blPrpClimit = new BLPrpClimit();
	String strSQL = "PolicyNo='" + strPolicyNo + "'";
	blPrpClimit.query(strSQL);
	for(i=0;i<blPrpClimit.getSize();i++){
		strLimitType = blPrpClimit.getArr(i).getLimitType();
	  if(blPrpClimit.getArr(i).getLimitGrade().equals("1")){
		if(strLimitType.equals("02")){  //每次事故赔偿限额
			//取最後一条信息（注掉部分是取多个值的和）
			//dbLimit02 = dbLimit01 + Double.parseDouble(blPrpClimit.getArr(i).getLimitFee());
			dbLimit01 = Double.parseDouble(blPrpClimit.getArr(i).getLimitFee());
			strCurrency01 = blPrpClimit.getArr(i).getCurrency();
		} else if(strLimitType.equals("03")){  //财产损失赔偿限额
			//取最後一条信息（注掉部分是取多个值的和）
			//dbLimit03 = dbLimit03 + Double.parseDouble(blPrpClimit.getArr(i).getLimitFee());
			dbLimit03 = Double.parseDouble(blPrpClimit.getArr(i).getLimitFee());
			strCurrency03 = blPrpClimit.getArr(i).getCurrency();
		} else if(strLimitType.equals("05")){  //人身伤亡赔偿限额
			//取最後一条信息（注掉部分是取多个值的和）
			//dbLimit07 = dbLimit07 + Double.parseDouble(blPrpClimit.getArr(i).getLimitFee());
			dbLimit07 = Double.parseDouble(blPrpClimit.getArr(i).getLimitFee());
			strCurrency07 = blPrpClimit.getArr(i).getCurrency();
		} else if(strLimitType.equals("06")){  //每人赔偿限额
			//取最後一条信息（注掉部分是取多个值的和）
			//dbLimit02 = dbLimit02 + Double.parseDouble(blPrpClimit.getArr(i).getLimitFee());
			dbLimit02 = Double.parseDouble(blPrpClimit.getArr(i).getLimitFee());
			strCurrency02 = blPrpClimit.getArr(i).getCurrency();
		} else if(strLimitType.equals("05")){  //每次事故绝对免赔
			//取最後一条信息（注掉部分是取多个值的和）
			//dbLimit05 = dbLimit05 + Double.parseDouble(blPrpClimit.getArr(i).getLimitFee());
			dbLimit05 = Double.parseDouble(blPrpClimit.getArr(i).getLimitFee());
			strCurrency05 = blPrpClimit.getArr(i).getCurrency();
		}
	  }
	}*/
	strLimit01 = new DecimalFormat("0.00").format(dbLimit01);
	strLimit03 = new DecimalFormat("0.00").format(dbLimit03);
	strLimit07 = new DecimalFormat("0.00").format(dbLimit07);
	strLimit02 = new DecimalFormat("0.00").format(dbLimit02);
	strLimit05 = new DecimalFormat("0.00").format(dbLimit05);

	//add by zhulei 20050917 prpMainLiab信息
	String strInsureArea = ""; //承保区域
	String strSaleArea = ""; //销售区域
	String bkWardStartDate = ""; //追溯期（起期）
	//add start by miaowenjun 20060907
	if (policyDto != null && policyDto.getPrpCmainLiabDtoList() != null && policyDto.getPrpCmainLiabDtoList().size() > 0) {
		PrpCmainLiabDto prpCmainLiabDto = new PrpCmainLiabDto();
		prpCmainLiabDto = (PrpCmainLiabDto) policyDto.getPrpCmainLiabDtoList().get(0);
		strInsureArea = prpCmainLiabDto.getInsureArea();
		strSaleArea = prpCmainLiabDto.getSaleArea();
		bkWardStartDate = prpCmainLiabDto.getBkWardStartDate();
	}
	//add end by miaowenjun 20060907
	//delete by miaowenjun 20060907
	/*BLPrpCmainLiab blPrpCmainLiab = new BLPrpCmainLiab();
	blPrpCmainLiab.getData(strPolicyNo);
	if(blPrpCmainLiab.getSize()>0){
	  strInsureArea = blPrpCmainLiab.getArr(0).getInsureArea();
	  strSaleArea = blPrpCmainLiab.getArr(0).getSaleArea();
	  bkWardStartDate = blPrpCmainLiab.getArr(0).getBkWardStartDate();
	}*/
%>