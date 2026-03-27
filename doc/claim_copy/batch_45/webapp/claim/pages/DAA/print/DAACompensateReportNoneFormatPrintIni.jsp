<%--
****************************************************************************
* DESC       ：机动车辆保险理算报告书打印初始化
* AUTHOR     ：maliang
* CREATEDATE ：2006-08-04
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
 	//变量定义部分
 	String strPolicyNo = ""; //保单号码
 	String strCompensateNo = ""; //赔款计算书号码
 	String strRegistNo = ""; //报案号码
 	String strClaimNo = ""; //立案号码
 	String strInsuredName = ""; //被保险人名称
 	String strCompany = ""; //承保公司
 	String strComCode = ""; //承保公司编码
 	String strContextRegist = ""; //第一段报案文字
 	String strContextCheck = ""; //第二段查勘文字
 	int strDamageDateYear = 0; //出险年
 	int strDamageDateMonth = 0; //出险月
 	int strDamageDateDay = 0; //出险日
 	String strDamageDateHour = ""; //出现小时
 	String strDriverName = ""; //驾驶员姓名

 	int intItemCarCount = 0; //PrpCitemCarDto对象记录数
 	int index = 0; //循环的记录器
 	String strLicenseNo = ""; //保单中的车型
 	String strCode = ""; // 条款类别
 	String strName = ""; //条款名称
 	boolean isChinese = true; //中文标志
 	String strLicenseNo1 = ""; //报案中的车型
 	String strDrivingLicenseNo = ""; //驾驶证号
 	int intDrivingYear = 0; //驾驶年限
 	String strDrivingCarType = ""; //准价车型
 	int intDriverCount = 0;
 	String strBrandName = ""; //厂牌型号 
 	String strDamageName = ""; //出险原因
 	String strDamageTypeName = ""; //保险事故类型
 	String strFirstDuanluo = ""; //第一段
 	String strLicenseNo2 = ""; //号牌号码
 	String strChecker1 = ""; //查勘人1
 	String strChecker2 = ""; //查勘人2
 	int strCheckDateYear = 0; //查勘年
 	int strCheckDateMonth = 0; //查勘月
 	int strCheckDateDay = 0; //查勘日
 	String strCheckSite = ""; //查勘地点
 	String strTypeName = ""; //险种名称  
 	String strRiskCode = ""; //险种代码
 	String strOperatorCode = ""; //经办人代码
 	String strOperatorName = ""; //经办人名称 
 	String strIndemnityDutyCode = ""; //责任代码
 	String strIndemnityDutyName = ""; //责任名称
 	double dblIndemnityDutyRate = 0; //责任比例
 	String strSecondDuanluo = ""; //第二段

 	String strInsuredTerm = ""; //保险期限
 	String strFrameNo = ""; //车架号
 	String dblPurchasePrice = ""; //新车购置价
 	String strRunAreaName = ""; //车辆行驶区域
 	String strUseYears = ""; //使用年限
 	String strSumAmount = ""; //应交保费
 	String strPlanFee = ""; //已交保费
 	int intPlanCount = 0;
 	double douDelinquentFee = 0;
 	double douDelinquentFeeTem = 0;
 	double douSumFee = 0; //应缴
 	double douSumFeeTem = 0;
 	String strSumFee = "";
 	double dblPayFee = 0; //已交
 	String strPayFee = "";
 	String strThirdDuanluo = ""; //第三段
 	String strFiveDuanluo = "";
 	int j = 0;
 	String strKindNameOutTem = ""; //险别输出
 	String strKindNameOut = "";
 	String strSumFeeOutTem = "";
 	String strSumFeeOut = "";
 	String strKindNameOutTem1 = ""; //险别输出
 	String strKindNameOut1 = "";
 	String strSumFeeOutTem1 = "";
 	String strSumFeeOut1 = "";

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

 	PrpLextDto prpLextDto = null;
 	PrpCmainDto prpMainDto = null;
 	PrpLcheckDto prpLcheckDto = null;
 	PrpCplanDto prpCplanDto = null;
 	PrpLpersonDto prpLpersonDto = null;
 	PrpLcomponentDto prpLcomponentDto = null;
 	PrpLrepairFeeDto prpLrepairFeeDto = null;
 	PrpLpropDto prpLpropDto = null;

 	PrpLlossDto prpLlossDto = null;
 	PrpLpersonLossDto prpLpersonLossDto = null;

 	//得到ClaimDto,RegistDto,CertainLossDto,PolicyDto对象
 	ClaimDto claimDto = (ClaimDto) request.getAttribute("claimDto");
 	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");
 	RegistDto registDto = (RegistDto) request.getAttribute("registDto");
 	CheckDto checkDto = (CheckDto) request.getAttribute("checkDto");
 	CompensateDto compensateDto = (CompensateDto) request.getAttribute("compensateDto");
 	CertainLossDto certainLossDto = (CertainLossDto) request.getAttribute("certainLossDto");
 	EndorseDto endorseDto = (EndorseDto) request.getAttribute("endorseDto");

 	//代码转化对象
 	UICodeAction uiCodeAction = new UICodeAction();

 	//得到prpLcompensateDto 对象
 	prpLcompensateDto = compensateDto.getPrpLcompensateDto();
 	//得到prpLclaimDto对象
 	prpLclaimDto = claimDto.getPrpLclaimDto();
 	//得到prpLregistDto对象
 	prpLregistDto = registDto.getPrpLregistDto();
 	//得到prpCmain对象
 	prpMainDto = policyDto.getPrpCmainDto();
 	//得到prpcLcheck 对象
 	prpLcheckDto = checkDto.getPrpLcheckDto();

 	strInsuredName = prpMainDto.getInsuredName(); //被保险人
 	strPolicyNo = compensateDto.getPrpLcompensateDto().getPolicyNo(); //保单号码
 	strCompensateNo = request.getParameter("CompensateNo"); //计算书号码
 	strRegistNo = prpLclaimDto.getRegistNo(); //报案编号
 	strClaimNo = prpLclaimDto.getClaimNo(); //立案编号

 	//承保公司
 	strComCode = StringConvert.encode(prpLregistDto.getComCode());
 	strCompany = uiCodeAction.translateComCode(strComCode, true);

 	strDamageDateYear = prpLclaimDto.getDamageStartDate().getYear(); //出险年
 	strDamageDateMonth = prpLclaimDto.getDamageStartDate().getMonth(); //出险月
 	strDamageDateDay = prpLclaimDto.getDamageStartDate().getDate(); //出险日
 	strDamageDateHour = prpLclaimDto.getDamageStartHour().substring(0, 2); //出险小时

 	//得到blPrpCitemCar对象 
 	if (policyDto.getPrpCitemCarDtoList() != null) {
 		intItemCarCount = policyDto.getPrpCitemCarDtoList().size();
 	}
 	if (policyDto.getPrpCitemCarDtoList() != null) {
 		for (index = 0; index < intItemCarCount; index++) {
 			prpItemCarDto = (PrpCitemCarDto) policyDto.getPrpCitemCarDtoList().get(index);
 			strLicenseNo = StringConvert.encode(prpItemCarDto.getLicenseNo());
 			//strLicenseColorCode = StringConvert.encode(prpItemCarDto.getLicenseColorCode());

 			strCode = StringConvert.encode(prpItemCarDto.getUseNatureCode());
 			strName = uiCodeAction.translateCodeCode("UseNature", strCode, isChinese); //车辆使用性质 
 			strFrameNo = prpItemCarDto.getFrameNo(); //车架号
 			dblPurchasePrice = new DecimalFormat("#,##0.00").format(prpItemCarDto.getPurchasePrice()); //新车购置价 

 			strRunAreaName = StringConvert.encode(prpItemCarDto.getRunAreaName());
 			if (!strRunAreaName.equals("")) {

 			} else {
 				strRunAreaName = "中华人民共和国境内(不含港澳台)";
 			}

 			strUseYears = DataUtils.zeroToEmpty(prpItemCarDto.getUseYears());

 		}
 	}

 	if (registDto.getPrpLdriverDtoList() != null) {
 		intDriverCount = registDto.getPrpLdriverDtoList().size();
 		for (index = 0; index < intDriverCount; index++) {
 			prpLdriverDto = (PrpLdriverDto) registDto.getPrpLdriverDtoList().get(index);
 			strLicenseNo1 = prpLdriverDto.getLicenseNo();

 			if (strLicenseNo1.equals(strLicenseNo)) {

 				strDriverName = prpLdriverDto.getDriverName(); //驾驶员名称
 				strDrivingLicenseNo = prpLdriverDto.getDrivingLicenseNo(); //驾驶证号
 				intDrivingYear = prpLdriverDto.getDrivingYear(); //驾驶年限
 				strDrivingCarType = prpLdriverDto.getDrivingCarType(); //准驾车型
 			}
 		}
 	}

 	strBrandName = prpLregistDto.getBrandName(); //厂牌型号
 	strDamageName = prpLregistDto.getDamageName(); //出险原因
 	strDamageTypeName = prpLregistDto.getDamageTypeName(); //事故类型
 	strLicenseNo2 = prpLregistDto.getLicenseNo(); //号牌号码

 	strFirstDuanluo = "<br>&nbsp;&nbsp;&nbsp;&nbsp;<ins>" + strDamageDateYear + "</ins>年<ins>" + strDamageDateMonth + "</ins>月<ins>" + strDamageDateDay + "</ins>日<ins>" + strDamageDateHour + "</ins>时，驾驶员（姓名）：<ins>" + strDriverName + "</ins>&nbsp;&nbsp;（驾驶证号：<ins>" + strDrivingLicenseNo + "</ins>&nbsp;&nbsp;驾驶年限：<ins>" + intDrivingYear + "</ins>；准驾车型：<ins>" + strDrivingCarType
 			+ "</ins>）&nbsp;&nbsp;驾驶（号牌号码：<ins>" + strLicenseNo2 + "</ins>；厂牌型号：<ins>" + strBrandName + "</ins>）车辆，因<ins>" + strDamageName + "</ins>）原因发生<ins>" + strDamageTypeName + "</ins>（保险事故类型）事故，造成保险损失。";

 	strChecker1 = prpLcheckDto.getChecker1(); //获取第一查勘人
 	strChecker2 = prpLcheckDto.getChecker2(); //获取第二查勘人

 	strCheckDateYear = prpLcheckDto.getCheckDate().getYear(); //出险年
 	strCheckDateMonth = prpLcheckDto.getCheckDate().getMonth(); //出险月
 	strCheckDateDay = prpLcheckDto.getCheckDate().getDate(); //出险日
 	strCheckSite = prpLcheckDto.getCheckSite(); //查勘地点

 	strRiskCode = prpLclaimDto.getRiskCode();
 	strTypeName = uiCodeAction.translateRiskCode(strRiskCode, true); //获取保险险种

 	/*
 	if (policyDto.getPrpCitemKindDtoList() != null) {
 	   for (int i = 0; i < policyDto.getPrpCitemKindDtoList().size(); i++) {
 	       prpItemKindDto = (PrpCitemKindDto) policyDto.getPrpCitemKindDtoList().get(i);

 	       strType=prpItemKindDto.getKindName();            
 	   
 	       strTypeName=strTypeName+strType+" ";
 	       
 	   }
 	   strTypeName=strTypeName+";";
 	}
 	
 	 */

 	strOperatorCode = prpLcompensateDto.getHandlerCode();
 	strOperatorName = uiCodeAction.translateUserCode(strOperatorCode, isChinese); //获取经办人

 	strIndemnityDutyCode = prpLcompensateDto.getIndemnityDuty(); //获取责任代码
 	strIndemnityDutyName = uiCodeAction.translateCodeCode("IndemnityDuty", strIndemnityDutyCode, isChinese); //获取责任类型

 	dblIndemnityDutyRate = prpLcompensateDto.getIndemnityDutyRate(); //获取责任比例

 	strSecondDuanluo = "<br>&nbsp;&nbsp;&nbsp;&nbsp;接到报案後，由<ins>" + strChecker1 + "</ins>和<ins>" + strChecker2 + "&nbsp;&nbsp;</ins>同志於<ins>" + strCheckDateYear + "</ins>年<ins>" + strDamageDateMonth + "</ins>月<ins>" + strCheckDateDay + "</ins>日到<ins>" + strCheckSite + "</ins>（地点）进行了查勘。根据查勘情况以及有关证明材料，认定该事故属<ins>" + strTypeName + "</ins>（险种）保险责任。此事故经<ins>" + strOperatorName
 			+ "</ins>认定被保险人负<ins>" + strIndemnityDutyName + "</ins>责任，被保险人应当承担<ins>" + dblIndemnityDutyRate + "</ins>%的损失。<ins>";

 	strInsuredTerm = prpMainDto.getStartDate().getYear() + "-" + prpMainDto.getStartDate().getMonth() + "-" + prpMainDto.getStartDate().getDate() + "至" + prpMainDto.getEndDate().getYear() + "-" + prpMainDto.getEndDate().getMonth() + "-" + prpMainDto.getEndDate().getDate();

 	strSumAmount = new DecimalFormat("#,##0.00").format(policyDto.getPrpCmainDto().getSumPremium());

 	if (policyDto.getPrpCplanDtoList() != null) {
 		intPlanCount = policyDto.getPrpCplanDtoList().size();
 		for (index = 0; index < intPlanCount; index++) {
 			prpCplanDto = (PrpCplanDto) policyDto.getPrpCplanDtoList().get(index);

 			douSumFee = prpCplanDto.getPlanFee();
 			douSumFeeTem = douSumFeeTem + douSumFee;

 			douDelinquentFee = prpCplanDto.getDelinquentFee();
 			douDelinquentFeeTem = douDelinquentFeeTem + douDelinquentFee;

 		}
 		//System.out.println("+++++++++++++++++douSumFeeTem="+douSumFeeTem);
 		//System.out.println("-----------------douDelinquentFeeTem="+douDelinquentFeeTem);
 		dblPayFee = douSumFeeTem - douDelinquentFeeTem;

 		strSumFee = new DecimalFormat("#,##0.00").format(douSumFeeTem);
 		strPayFee = new DecimalFormat("#,##0.00").format(dblPayFee);
 	}

 	strThirdDuanluo = "<br>	被保险人应缴保费：<ins>" + strSumAmount + "</ins>元，已缴付<ins>" + strPayFee + "</ins>元。";
 	// ■电话   □上门   □传真
 	strFiveDuanluo = "经过（第一现场□、第二现场 □）定损，本次事故损失核定如下：（按险别列明）";

 	//---------------计算定损
 	ArrayList cTemp = policyDto.getPrpCitemKindDtoList();
 	String[] strKindName = new String[cTemp.size()]; //条款名称
 	double[] dbSumFee = new double[cTemp.size()]; //每个条款对应的总保费
 	//double [] dbSumFee;

 	for (index = 0; index < cTemp.size(); index++) {
 		prpItemKindDto = (PrpCitemKindDto) cTemp.get(index);
 		strKindName[index] = prpItemKindDto.getKindName();
 		dbSumFee[index] = 0;

 		for (j = 0; j < certainLossDto.getPrpLpersonDtoList().size(); j++) {
 			prpLpersonDto = (PrpLpersonDto) certainLossDto.getPrpLpersonDtoList().get(j);
 			if (prpItemKindDto.getKindCode().equals(prpLpersonDto.getKindCode())) {
 				dbSumFee[index] += prpLpersonDto.getSumDefLoss();
 			}
 		}

 		for (j = 0; j < certainLossDto.getPrpLcomponentDtoList().size(); j++) {
 			prpLcomponentDto = (PrpLcomponentDto) certainLossDto.getPrpLcomponentDtoList().get(j);
 			if (prpItemKindDto.getKindCode().equals(prpLcomponentDto.getKindCode())) {
 				//modify by liuwei at 2011-02-14 显示核损金额 start
 				//dbSumFee[index]+=prpLcomponentDto.getSumDefLoss();
 				dbSumFee[index] += prpLcomponentDto.getSumVeriLoss();
 				//modify by liuwei at 2011-02-14 显示核损金额 start
 			}
 		}

 		for (j = 0; j < certainLossDto.getPrpLrepairFeeDtoList().size(); j++) {
 			prpLrepairFeeDto = (PrpLrepairFeeDto) certainLossDto.getPrpLrepairFeeDtoList().get(j);
 			if (prpItemKindDto.getKindCode().equals(prpLrepairFeeDto.getKindCode())) {
 				//modify by liuwei at 2011-02-14 显示核损金额 start
 				//dbSumFee[index]+=prpLrepairFeeDto.getSumDefLoss();
 				dbSumFee[index] += prpLrepairFeeDto.getVeriSumLoss();
 				//modify by liuwei at 2011-02-14 显示核损金额 start
 			}
 		}

 		for (j = 0; j < certainLossDto.getPrpLpropDtoList().size(); j++) {
 			prpLpropDto = (PrpLpropDto) certainLossDto.getPrpLpropDtoList().get(j);
 			if (prpItemKindDto.getKindCode().equals(prpLpropDto.getKindCode())) {
 				dbSumFee[index] += prpLpropDto.getSumDefLoss();
 			}
 		}

 	}

 	for (index = 0; index < cTemp.size(); index++) {
 		if (dbSumFee[index] != 0 && !strKindName.equals("")) {
 			strKindNameOutTem = strKindName[index];
 			strSumFeeOutTem = new DecimalFormat("#,##0.00").format(dbSumFee[index]);

 			strKindNameOut = strKindNameOut + "<tr><td align=left width=180>" + strKindNameOutTem + "</td><td align=left width=80>合计:</td><td align=left width=80>" + strSumFeeOutTem + "元</td><td ></td></tr>";
 		}
 	}

 	//---------------计算理算
 	ArrayList cTemp1 = policyDto.getPrpCitemKindDtoList();
 	String[] strKindName1 = new String[cTemp1.size()]; //条款名称
 	double[] dbSumFee1 = new double[cTemp1.size()]; //每个条款对应的总保费 

 	for (index = 0; index < cTemp1.size(); index++) {
 		prpItemKindDto = (PrpCitemKindDto) cTemp.get(index);
 		strKindName1[index] = prpItemKindDto.getKindName();
 		dbSumFee1[index] = 0;

 		for (j = 0; j < compensateDto.getPrpLlossDtoList().size(); j++) {
 			prpLlossDto = (PrpLlossDto) compensateDto.getPrpLlossDtoList().get(j);
 			if (prpItemKindDto.getKindCode().equals(prpLlossDto.getKindCode())) {
 				dbSumFee1[index] += prpLlossDto.getSumRealPay();
 			}
 		}

 		for (j = 0; j < compensateDto.getPrpLpersonLossDtoList().size(); j++) {
 			prpLpersonLossDto = (PrpLpersonLossDto) compensateDto.getPrpLpersonLossDtoList().get(j);
 			if (prpItemKindDto.getKindCode().equals(prpLpersonLossDto.getKindCode())) {
 				dbSumFee1[index] += prpLpersonLossDto.getSumRealPay();
 			}
 		}

 		for (j = 0; j < compensateDto.getPrpLchargeDtoList().size(); j++) {
 			prpLchargeDto = (PrpLchargeDto) compensateDto.getPrpLchargeDtoList().get(j);
 			if (prpItemKindDto.getKindCode().equals(prpLchargeDto.getKindCode())) {
 				dbSumFee1[index] += prpLchargeDto.getSumRealPay();
 			}
 		}

 	}

 	for (index = 0; index < cTemp1.size(); index++) {
 		if (dbSumFee1[index] != 0 && !strKindName.equals("")) {
 			strKindNameOutTem1 = strKindName1[index];
 			strSumFeeOutTem1 = new DecimalFormat("#,##0.00").format(dbSumFee1[index]);

 			strKindNameOut1 = strKindNameOut1 + "<tr><td align=left width=180>" + strKindNameOutTem1 + "</td><td align=left width=80>合计:</td><td align=left width=80>" + strSumFeeOutTem1 + "元</td><td ></td></tr>";
 		}
 	}

 	//----------------计算赔款总计

 	double dblSumPaid = 0;
 	double dblSumPaidTmp = 0;
 	String strSumPaid = "";
 	String strCSumPaid = "";
 	String strCSumPaidOut = "";

 	dblSumPaid = prpLcompensateDto.getSumPaid();
 	dblSumPaidTmp = Math.abs(dblSumPaid);
 	strSumPaid = new DecimalFormat("#,##0.00").format(dblSumPaid);
 	strCSumPaid = MoneyUtils.toChinese(dblSumPaidTmp, prpLcompensateDto.getCurrency());
 	if (dblSumPaid < 0) {
 		strCSumPaid = "负" + strCSumPaid;
 	}

 	strCSumPaidOut = "（人民币大写）" + strCSumPaid + "&nbsp;&nbsp;（￥：" + strSumPaid + "元）";
 %>


