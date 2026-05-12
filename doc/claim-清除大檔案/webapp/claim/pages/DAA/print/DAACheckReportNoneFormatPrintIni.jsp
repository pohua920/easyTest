<%--
****************************************************************************
* DESC       ：机动车现场查勘记录打印初始化
* AUTHOR     ：理赔组
* CREATEDATE ：2004-11-16
* MODIFYLIST ：   Name       Date            Reason/Contents
--------------------------------------------------------------------------
*                 lirj     20040317        费用分开，按车牌号码打印
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
<%@page import="java.util.*"%>
<%
	//变量声明部分
	String strClaimNo = ""; //赔案号
	String strRegistNo = ""; //报案号
	String strPolicyNo = ""; //保单号
	String prpLRegistRPolicyNo = ""; //强制保单号

	/**DateTime currentDateTime = new DateTime();
	int intYear = currentDateTime.getYear();
	int intMonth = currentDateTime.getMonth();
	int intDay = currentDateTime.getDay();
	int intHour = currentDateTime.getHour();
	int intMinute     = currentDateTime.getMinute();
	int intSecond  = currentDateTime.getSecond();
	String strPrintTime = new Integer(intYear).toString() + "年" +
	new Integer(intMonth).toString() + "月" +
	new Integer(intDay).toString() + "日" +
	new Integer(intHour).toString() + "时" +
	new Integer(intMinute).toString() + "分" +
	new Integer(intSecond).toString() + "秒";*/

	//本车车辆信息
	String strInsuredName = "";
	String strDamageAddress = "";
	String strdamageStartDate = "";
	String strdamageStartHour = "";
	String strdamageStartMinute = "";
	String indemnityDutyCode = "";//责任代码
	String indemnityDuty = "";//事故责任
	String handleUnitCode = "";//事故处理部门
	String handleUnit = "";//事故处理方式   
	int intItemKindCount = 0;
	String strBrandName = ""; //厂牌型号
	String strEngineNo = ""; //发动机号
	double strRunDistance = 0.0d; //车辆已行驶公里数
	int strUseYears = 0; //车辆实际使用年限
	String strLicenseNo = ""; //车牌号
	String strFrameNo = ""; //车架号
	//add by zhaolu 20060904 start
	String strLicenseColorCode = ""; //车牌底色标志
	String strLicenseColor = "";//车牌底色
	int intCarSerialNo[] = null;//车辆的SerialNo
	//add by zhaolu 20060904 end

	//第三方车辆信息
	//Modify by zhaolu 20060905 start
	String strBrandNamethird[] = null; //厂牌型号
	String strEngineNothird[] = null; //发动机号
	double strRunDistancethird[] = null; //车辆已行驶公里数
	int strUseYearsthird[] = null; //车辆实际使用年限
	String strLicenseNothird[] = null; //车牌号
	String strFrameNothird[] = null; //车架号
	//System.out.println("-------------0---------------");
	String strD1 = ""; //驾驶员姓名
	String strD2 = ""; //驾驶证号码
	String strD3 = ""; //驾驶证有效期
	//Modify by zhaolu 20060905 end
	//add begin by zhuly 20051107
	String strUseNatureCode = "";//使用性质代码
	String strUseNatureName = "";//使用性质
	String strCheckSite = "";//查勘地点
	String strDamageCode = "";//出险原因代码
	String strDamageClause = "";//出险原因
	String strRunAreaCode = "";//行驶区域代码
	String strRunAreaName = "";//行驶区域
	String strSexCode = "";//驾驶员性别代码
	String strSexName = "";//驾驶员性别名称
	//add end by zhuly 20051107

	ArrayList prpLcheckExtDtoList = new ArrayList();

	//add by zhaolu 2006090404 start
	PrpLdriverDto prpLdriverDto = new PrpLdriverDto();
	ArrayList prpLdriverDtoList = new ArrayList();
	String strThirdPrpLregistPhoneNumber[] = null;//三者车驾驶员联系电话
	String strThirdPrpLdriverName[] = null;//三者车驾驶员姓名
	String strThirdPrpLdriverLicenseNo[] = null;//三者车驾驶员证件号
	DateTime thirdDateTime[] = null;//三者领证时间
	int intPrpLdriverSerialNo[] = null;//驾驶员SerialNo
	String strPrpLregistPhoneNumber = "";//标的车驾驶员联系电话
	String strPrpLdriverName = ""; //标的车驾驶员姓名
	String strPrpLdriverLicenseNo = "";//标的车驾驶员证件号
	DateTime dateTime = null;
	String strPrpLregistDamageAddress = "";//出险地点
	String strFirstSiteFlag = "";//是否第一现场
	String strFirstSite = "";//是否第一现场
	long personDeathB = 0;//第三者亡人数
	long personInjureB = 0;//第三者伤人数
	long personDeathD1 = 0;//车上人员亡人数
	long personInjureD1 = 0;//车上人员伤人数
	String[] strKindCode = new String[20];//险种代码
	String[] strKindName = new String[20];//险种名称
	//add by zhaolu 20060904 end

	//add begin by miaowenjun 20060405
	PrpLcheckLossDto prpLcheckLossDto = null;
	ArrayList prpLcheckLossList = new ArrayList();
	double lossFee = 0d;
	double lossFeeThird = 0d;
	//add end by miaowenjun 20060405
	double lossFeeC = 0d;
	//add begin by miaowenjun 20060327
	String strgetCheckDate = "";//查勘日期
	String strchecker1 = "";//查勘人
	String strchecker2 = "";//查勘人2
	//add end by miaowenjun 20060327

	//代码翻译变量

	String strCode = "";
	String strName = "";
	boolean isChinese = true; //中文标志

	//对象定义部分

	//prpLRegistRPolicyNo = (String)request.getAttribute("prpLRegistRPolicyNo");
	PrpLthirdPartyDto prpLthirdPartyDto = null; //ThirdPartyDto对象
	PrpLregistDto prpLregistDto = new PrpLregistDto();

	int intThirdPartyCount = 0; //ThirdPartyDto对象的记录数

	int index = 0;

	RegistDto registDto = new RegistDto();
	registDto = (RegistDto) request.getAttribute("registDto");
	strPolicyNo = prpLregistDto.getPolicyNo();

	//add by zhaolu 20060904 start
	handleUnitCode = registDto.getPrpLregistDto().getHandleUnit();
	personDeathB = registDto.getPrpLregistDto().getPersonDeathB();
	personInjureB = registDto.getPrpLregistDto().getPersonInjureB();
	personDeathD1 = registDto.getPrpLregistDto().getPersonDeathD1();
	personInjureD1 = registDto.getPrpLregistDto().getPersonInjureD1();
	prpLdriverDtoList = registDto.getPrpLdriverDtoList();
	strPrpLregistDamageAddress = registDto.getPrpLregistDto().getDamageAddress();
	strFirstSiteFlag = "";
	//add by zhaolu 20060904 end

	strRegistNo = request.getParameter("RegistNo");

	//caopeng 11.29
	//add begin by zhuly 20051107 车险标地信息
	//代码转换
	UICodeAction uiCodeAction = new UICodeAction();

	//查勘信息
	UICheckAction uiCheckAction = new UICheckAction();
	CheckDto checkDto = uiCheckAction.findByPrimaryKey(strRegistNo);
	indemnityDutyCode = checkDto.getPrpLcheckDto().getIndemnityDuty();
	indemnityDuty = uiCodeAction.translateCodeCode("IndemnityDuty", indemnityDutyCode, true);
	handleUnit = uiCodeAction.translateCodeCode("HandleUnit", handleUnitCode, true);
	//add by zhaolu 20060904 start
	strFirstSiteFlag = checkDto.getPrpLcheckDto().getFirstSiteFlag();
	if ("0".equals(strFirstSiteFlag))
		strFirstSite = "否";
	if ("1".equals(strFirstSiteFlag))
		strFirstSite = "是";
	//add by  zhaolu 20060904 end
	prpLcheckLossList = checkDto.getPrpLcheckLossDtoList(); //add by miaowenjun 20060405
	//System.out.println("----------------------------");
	//System.out.println(registDto.getPrpLthirdPartyDtoList().size());
	//System.out.println(prpLcheckLossList.size());
	//System.out.println("---------------------------");
	PrpLcheckDto prpLcheckDto = checkDto.getPrpLcheckDto();
	if (checkDto.getPrpLcheckDto() != null) {
		strchecker1 = prpLcheckDto.getChecker1();
		strchecker2 = prpLcheckDto.getChecker2();
		strgetCheckDate = prpLcheckDto.getCheckDate().getYear() + "年" + prpLcheckDto.getCheckDate().getMonth() + "月" + prpLcheckDto.getCheckDate().getDay() + "日";
		strCheckSite = prpLcheckDto.getCheckSite();
		//System.out.println("checkpoint"+strCheckSite);
		//caopeng 11.29
		strDamageAddress = strCheckSite;
		strPolicyNo = prpLcheckDto.getPolicyNo();
		//11.29
		strDamageCode = prpLcheckDto.getDamageCode();
		strDamageClause = uiCodeAction.translateCodeCode("DamageCode", strDamageCode, true);
	}
	//获得保单信息
	UIPolicyAction uiPolicyAction = new UIPolicyAction();
	PolicyDto policyDto = uiPolicyAction.findByPrimaryKey(strPolicyNo);
	PrpCitemCarDto prpCitemCarDto = new PrpCitemCarDto();

	//获得驾驶员信息
	//add by zhaolu 20060905 start
	if (prpLdriverDtoList != null) {
		strThirdPrpLregistPhoneNumber = new String[prpLdriverDtoList.size()];
		strThirdPrpLdriverName = new String[prpLdriverDtoList.size()];
		strThirdPrpLdriverLicenseNo = new String[prpLdriverDtoList.size()];
		intPrpLdriverSerialNo = new int[prpLdriverDtoList.size()];
		thirdDateTime = new DateTime[prpLdriverDtoList.size()];
		for (int i = 0; i < prpLdriverDtoList.size(); i++) {
			prpLdriverDto = (PrpLdriverDto) prpLdriverDtoList.get(i);
			intPrpLdriverSerialNo[i] = prpLdriverDto.getSerialNo();
			if (intPrpLdriverSerialNo[i] == 1) {
				strPrpLdriverName = prpLdriverDto.getDriverName();
				strPrpLregistPhoneNumber = prpLdriverDto.getDriverSeaRoute();
				strPrpLdriverLicenseNo = prpLdriverDto.getDrivingLicenseNo();
				dateTime = prpLdriverDto.getReceiveLicenseDate();
			} else {
				strThirdPrpLregistPhoneNumber[i] = prpLdriverDto.getDriverSeaRoute();
				strThirdPrpLdriverName[i] = prpLdriverDto.getDriverName();
				strThirdPrpLdriverLicenseNo[i] = prpLdriverDto.getDrivingLicenseNo();
				thirdDateTime[i] = prpLdriverDto.getReceiveLicenseDate();
			}

		}
	}
	//add by zhaolu 20060905 end

	//*****机动车险标的信息表PrpCitemCar*****
	int intItemCarCount = 0;//标的子险信息记录数
	intItemCarCount = policyDto.getPrpCitemCarDtoList().size();
	//System.out.println("共有"+intItemCarCount+"条标的子险信息");
	if (policyDto.getPrpCitemCarDtoList() != null) {
		for (index = 0; index < intItemCarCount; index++) {
			prpCitemCarDto = (PrpCitemCarDto) policyDto.getPrpCitemCarDtoList().get(index);
			strUseNatureCode = prpCitemCarDto.getUseNatureCode();
			strUseNatureName = uiCodeAction.translateCodeCode("UseNature", strUseNatureCode, true);
			strRunAreaCode = prpCitemCarDto.getRunAreaCode();
			strRunAreaName = uiCodeAction.translateCodeCode("RunArea", strRunAreaCode, true);
		}
	}
	//*****机动车驾驶员关系表
	int intCarDriver = 0;
	intCarDriver = policyDto.getPrpCcarDriverDtoList().size();

	//System.out.println("共有"+intCarDriver+"条驾驶员信息");
	PrpCcarDriverDto prpCcarDriverDto = new PrpCcarDriverDto();
	if (policyDto.getPrpCcarDriverDtoList() != null) {
		for (index = 0; index < intCarDriver; index++) {
			prpCcarDriverDto = (PrpCcarDriverDto) policyDto.getPrpCcarDriverDtoList().get(index);
			strD1 = prpCcarDriverDto.getDriverName();
			strD2 = prpCcarDriverDto.getDrivingLicenseNo();
			strSexCode = prpCcarDriverDto.getSex();
			strSexName = uiCodeAction.translateCodeCode("SexCode", strSexCode, true);
		}
	}
	//add end by zhuly 20051107
	prpLregistDto = registDto.getPrpLregistDto();
	strInsuredName = prpLregistDto.getInsuredName();
	strDamageAddress = prpLregistDto.getDamageAddress();

	//strdamageStartDate	=String.valueOf(prpLregistDto.getDamageStartDate());
	strdamageStartDate = prpLregistDto.getDamageStartDate().getYear() + " 年 " + prpLregistDto.getDamageStartDate().getMonth() + " 月 " + prpLregistDto.getDamageStartDate().getDate() + " 日 ";
	if (prpLregistDto.getDamageStartHour() != null) {
		if (!"0".equals(prpLregistDto.getDamageStartHour().substring(0, 1))) {
			strdamageStartDate += prpLregistDto.getDamageStartHour().substring(0, 2) + " 时";
		} else {
			strdamageStartDate += prpLregistDto.getDamageStartHour().substring(1, 2) + " 时";
		}
	}
	strdamageStartHour = String.valueOf(prpLregistDto.getDamageStartHour());
	strdamageStartMinute = String.valueOf(prpLregistDto.getDamageStartMinute());

	PrpLregistTextDto prpLregistTextDto = new PrpLregistTextDto();
	String tempContext = "";
	if (checkDto.getPrpLregistTextDtoList() != null) {
		Iterator iterator = checkDto.getPrpLregistTextDtoList().iterator();
		while (iterator.hasNext()) {
			PrpLregistTextDto prpLregistTextDtoTemp = (PrpLregistTextDto) iterator.next();
			tempContext = tempContext + prpLregistTextDtoTemp.getContext();
		}
	}

	/*
	 UIPolicyAction   uiPolicyAction = new UIPolicyAction();

	 PolicyDto   policyDto = new PolicyDto();
	 PrpCitemCarDto prpCitemCarDto= new PrpCitemCarDto();
	 ArrayList prpCitemCarDtoList  = new ArrayList();
	 policyDto = uiPolicyAction.findByPrimaryKey(strPolicyNo);
	 prpCitemCarDtoList = policyDto.getprpCitemCarDtoList();
	 Iterator ititemkind = prpCitemCarDtoList.iterator();
	 while(ititemkind.hasNext())
	 {
	 PrpCitemCarDto prpCitemCarDto = (PrpCitemCarDto)ititemkind.next();
	 if(prpCitemCarDto.getItemNo()==1)
	 {
	 break;
	 }
	 }
	 */

	//Modify by zhaolu 20060905 start
	strBrandNamethird = new String[registDto.getPrpLthirdPartyDtoList().size()];
	strEngineNothird = new String[registDto.getPrpLthirdPartyDtoList().size()];
	strRunDistancethird = new double[registDto.getPrpLthirdPartyDtoList().size()];
	strUseYearsthird = new int[registDto.getPrpLthirdPartyDtoList().size()];
	strLicenseNothird = new String[registDto.getPrpLthirdPartyDtoList().size()];
	strFrameNothird = new String[registDto.getPrpLthirdPartyDtoList().size()];

	for (index = 0; index < registDto.getPrpLthirdPartyDtoList().size(); index++) {
		prpLthirdPartyDto = (PrpLthirdPartyDto) registDto.getPrpLthirdPartyDtoList().get(index);
		//intCarSerialNo[index] = prpLthirdPartyDto.getSerialNo();
		if (index == 0)
			strClaimNo = prpLthirdPartyDto.getClaimNo();
		//取得保险车辆信息
		//if(prpLthirdPartyDto.getInsureCarFlag().equals("1") && prpLcheckLossDto.getReferSerialNo() == 1)
		if (prpLthirdPartyDto.getInsureCarFlag().equals("1")) {
			strBrandName = prpLthirdPartyDto.getBrandName();
			strEngineNo = prpLthirdPartyDto.getEngineNo();
			strRunDistance = prpLthirdPartyDto.getRunDistance();
			strUseYears = prpLthirdPartyDto.getUseYears();
			strLicenseNo = prpLthirdPartyDto.getLicenseNo();
			strFrameNo = prpLthirdPartyDto.getFrameNo();
			//add by zhaolu 20060905 start
			//得到车牌底色
			strLicenseColorCode = prpLthirdPartyDto.getLicenseColorCode();
			strLicenseColor = uiCodeAction.translateCodeCode("LicenseColor", strLicenseColorCode, true);
			//add by zhaolu 20060905 end

		} else {

			strBrandNamethird[index] = prpLthirdPartyDto.getBrandName();
			strEngineNothird[index] = prpLthirdPartyDto.getEngineNo();
			strRunDistancethird[index] = prpLthirdPartyDto.getRunDistance();
			strUseYearsthird[index] = prpLthirdPartyDto.getUseYears();
			strLicenseNothird[index] = prpLthirdPartyDto.getLicenseNo();
			strFrameNothird[index] = prpLthirdPartyDto.getFrameNo();

		}

	}
	//Modify by zhaolu 20060905 end
	//add by zhaolu 20060905 start
	//财产损失
	if (prpLcheckLossList != null) {
		for (int indexFee = 0; indexFee < prpLcheckLossList.size(); indexFee++) {
			prpLcheckLossDto = (PrpLcheckLossDto) prpLcheckLossList.get(indexFee);
			if (prpLcheckLossDto.getReferSerialNo() == 1 && prpLcheckLossDto.getLossFeeType().equals("1")) {
				lossFee += prpLcheckLossDto.getLossFee();
			}

			if (prpLcheckLossDto.getLossFeeType().equals("1") && prpLcheckLossDto.getReferSerialNo() > 1) {
				lossFeeThird += prpLcheckLossDto.getLossFee();
			}

		}
	}
	//add by zhaolu 20060905 end

	//add by zhaolu 20060905 start
	//添加险种信息
	PrpCitemKindDto prpCitemKindDto = new PrpCitemKindDto();
	PrpCitemKindDto rPrpCitemKindDto = new PrpCitemKindDto();
	/**if(policyDto.getPrpCitemKindDtoList()!=null){
	intItemKindCount = policyDto.getPrpCitemKindDtoList().size();
	//add by zhaolu 20060902 start
	 rPrpCitemKindDto = (PrpCitemKindDto)request.getAttribute("rPrpCitemKindDto");
	if( rPrpCitemKindDto != null )
	 intItemKindCount = intItemKindCount + 1;
	//add by zhaolu 20060902 end
	for(index=0;index<intItemKindCount;index++){
	 //add by zhaolu 20060902 start
	 if( rPrpCitemKindDto != null ){
	 if (index == intItemKindCount - 1 )
	 {
	      strKindCode[index] = rPrpCitemKindDto.getKindCode();
	      strKindName[index] = rPrpCitemKindDto.getKindName();
	      
	 }
	 }
	 //add by zhaolu 20060902 end
	 if(index != intItemKindCount - 1){
	 		
	  prpCitemKindDto = (PrpCitemKindDto)policyDto.getPrpCitemKindDtoList().get(index);
	  strKindCode[index] = prpCitemKindDto.getKindCode();
	  strKindName[index] = prpCitemKindDto.getKindName();
	  }
	
	 }
	} */
	if (policyDto.getPrpCitemKindDtoList() != null) {
		intItemKindCount = policyDto.getPrpCitemKindDtoList().size();
		rPrpCitemKindDto = (PrpCitemKindDto) request.getAttribute("rPrpCitemKindDto");
		if (rPrpCitemKindDto != null) {
			prpLRegistRPolicyNo = (String) request.getAttribute("prpLRegistRPolicyNo");
			intItemKindCount = intItemKindCount + 1;
			for (index = 0; index < intItemKindCount; index++) {
				if (index == intItemKindCount - 1) {
					strKindCode[index] = rPrpCitemKindDto.getKindCode();
					strKindName[index] = rPrpCitemKindDto.getKindName();
				}
				if (index != intItemKindCount - 1) {
					prpCitemKindDto = (PrpCitemKindDto) policyDto.getPrpCitemKindDtoList().get(index);
					strKindCode[index] = prpCitemKindDto.getKindCode();
					strKindName[index] = prpCitemKindDto.getKindName();
				}
			}
		} else {
			for (index = 0; index < intItemKindCount; index++) {
				prpCitemKindDto = (PrpCitemKindDto) policyDto.getPrpCitemKindDtoList().get(index);
				strKindCode[index] = prpCitemKindDto.getKindCode();
				strKindName[index] = prpCitemKindDto.getKindName();
			}
		}
	}
	//add by zhaolu 20060905 end

	//add by zhaolu 20060905 start
	prpLcheckExtDtoList = checkDto.getPrpLcheckExtDtoList();
	//add by zhaolu 20060905 end
%>
