<%@page pageEncoding="GBK"%>
<%--
****************************************************************************
* DESC       ：机动车量盗抢险查勘报告打印初始化
* AUTHOR     ：理赔组
* CREATEDATE ：2005-11-15
* MODIFYLIST ：   Name       Date            Reason/Contents
--------------------------------------------------------------------------
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
<%@page import="java.util.Iterator"%>
<%
	//变量声明部分
	String strClaimNo = ""; //赔案号
	String strRegistNo = ""; //报案号
	String strPolicyNo = ""; //保单号
	String strChecker = ""; //查勘员
	DateTime checkDate = null; //查勘时间    add by miaowenjun 20060404  

	//本车车辆信息
	String strInsuredName = "";
	String strDamageAddress = "";
	String strdamageStartDate = "";
	String strdamageStartHour = "";
	String strdamageStartMinute = "";

	String strBrandName = ""; //厂牌型号
	String strEngineNo = ""; //发动机号
	double strRunDistance = 0.0d; //车辆已行驶公里数
	int strUseYears = 0; //车辆实际使用年限
	String strLicenseNo = ""; //车牌号
	String strFrameNo = ""; //车架号

	//第三方车辆信息
	String strBrandNamethird = ""; //厂牌型号
	String strEngineNothird = ""; //发动机号
	double strRunDistancethird = 0.0d; //车辆已行驶公里数
	int strUseYearsthird = 0; //车辆实际使用年限
	String strLicenseNothird = ""; //车牌号
	String strFrameNothird = ""; //车架号
	String strD1 = ""; //驾驶员姓名
	String strD2 = ""; //驾驶证号码
	String strD3 = ""; //驾驶证有效期
	String strUseNatureCode = ""; //使用性质代码
	String strUseNatureName = ""; //使用性质
	String strCheckSite = ""; //查勘地点
	String strSexCode = ""; //驾驶员性别代码
	String strSexName = ""; //驾驶员性别名称
	String strMarriageCode = ""; //婚否代码
	String strMarriageName = ""; //婚否名称
	String strBusinessSource = ""; //从业性质代码
	String strBusinessName = ""; //从业性质名称
	String strSafeDevice = ""; //安全设备
	String strParkSite = ""; //固定停放地点
	//代码翻译变量

	String strCode = "";
	String strName = "";
	boolean isChinese = true; //中文标志

	//对象定义部分

	PrpLthirdPartyDto prpLthirdPartyDto = null; //ThirdPartyDto对象
	PrpLregistDto prpLregistDto = new PrpLregistDto();
	int intThirdPartyCount = 0; //ThirdPartyDto对象的记录数

	int index = 0;

	RegistDto registDto = new RegistDto();
	registDto = (RegistDto) request.getAttribute("registDto");

	prpLregistDto = registDto.getPrpLregistDto();
	strInsuredName = prpLregistDto.getInsuredName();
	strPolicyNo = prpLregistDto.getPolicyNo();
	strRegistNo = prpLregistDto.getRegistNo();

	//代码转换
	UICodeAction uiCodeAction = new UICodeAction();
	//获得保单信息
	UIPolicyAction uiPolicyAction = new UIPolicyAction();
	PolicyDto policyDto = uiPolicyAction.findByPrimaryKey(strPolicyNo);
	PrpCitemCarDto prpCitemCarDto = new PrpCitemCarDto();

	int intItemCarCount = 0;//标的子险信息记录数
	intItemCarCount = policyDto.getPrpCitemCarDtoList().size();
	if (policyDto.getPrpCitemCarDtoList() != null) {
		//*****机动车险标的信息表PrpCitemCar*****
		for (index = 0; index < intItemCarCount; index++) {
			prpCitemCarDto = (PrpCitemCarDto) policyDto.getPrpCitemCarDtoList().get(index);
			strUseNatureCode = prpCitemCarDto.getUseNatureCode();
			strUseNatureName = uiCodeAction.translateCodeCode("UseNature", strUseNatureCode, true);
			strSafeDevice += uiCodeAction.translateCodeCode("SafeDevice", prpCitemCarDto.getSafeDevice(), true);
			if (prpCitemCarDto.getParkSite().equals("1") || "".equals(prpCitemCarDto.getParkSite())) {
				strParkSite = " 无 ";
			}
			if (prpCitemCarDto.getParkSite().equals("2") || prpCitemCarDto.getParkSite().equals("3") || prpCitemCarDto.getParkSite().equals("4")) {
				strParkSite = " 有 ";
			}
		}
	}

	//获得查勘信息
	UICheckAction uiCheckAction = new UICheckAction();
	CheckDto checkDto = uiCheckAction.findByPrimaryKey(strRegistNo);
	PrpLcheckDto prpLcheckDto = checkDto.getPrpLcheckDto();
	if (checkDto.getPrpLcheckDto() != null) {
		strCheckSite = prpLcheckDto.getCheckSite();
		strChecker = prpLcheckDto.getChecker1();
		checkDate = prpLcheckDto.getCheckDate();
	}
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

	for (index = 0; index < registDto.getPrpLthirdPartyDtoList().size(); index++) {
		prpLthirdPartyDto = (PrpLthirdPartyDto) registDto.getPrpLthirdPartyDtoList().get(index);
		if (index == 0)
			strClaimNo = prpLthirdPartyDto.getClaimNo();
		//取得保险车辆信息
		if (prpLthirdPartyDto.getInsureCarFlag().equals("1")) {
			strBrandName = prpLthirdPartyDto.getBrandName();
			strEngineNo = prpLthirdPartyDto.getEngineNo();
			strRunDistance = prpLthirdPartyDto.getRunDistance();
			strUseYears = prpLthirdPartyDto.getUseYears();
			strLicenseNo = prpLthirdPartyDto.getLicenseNo();
			strFrameNo = prpLthirdPartyDto.getFrameNo();
		} else {
			strBrandNamethird = prpLthirdPartyDto.getBrandName();
			strEngineNothird = prpLthirdPartyDto.getEngineNo();
			strRunDistancethird = prpLthirdPartyDto.getRunDistance();
			strUseYearsthird = prpLthirdPartyDto.getUseYears();
			strLicenseNothird = prpLthirdPartyDto.getLicenseNo();
			strFrameNothird = prpLthirdPartyDto.getFrameNo();
		}

	}
	//*****机动车驾驶员关系表
	int intCarDriver = 0;
	intCarDriver = policyDto.getPrpCcarDriverDtoList().size();

	PrpCcarDriverDto prpCcarDriverDto = new PrpCcarDriverDto();
	//caopeng 11.30
	UIClaimAction uiClaimAction = new UIClaimAction();
	ClaimDto claimDto = uiClaimAction.findByPrimaryKey(strClaimNo);
	ArrayList arrPrpLdriverDto = null;
	PrpLdriverDto prpLdriverDto = null;
	if (claimDto != null) {
		arrPrpLdriverDto = claimDto.getPrpLdriverDtoList();
		if (arrPrpLdriverDto != null) {
			for (index = 0; index < arrPrpLdriverDto.size(); index++) {
				prpLdriverDto = (PrpLdriverDto) claimDto.getPrpLdriverDtoList().get(index);
				strD1 = prpLdriverDto.getDriverName();
				strD2 = prpLdriverDto.getDrivingLicenseNo();
				strSexCode = prpLdriverDto.getDriverSex();
				strSexName = uiCodeAction.translateCodeCode("SexCode", strSexCode, true);
			}
		}
	}

	/*  if(policyDto.getPrpCcarDriverDtoList()!=null){
	 for(index=0;index<intCarDriver;index++)
	 {
	 prpCcarDriverDto  = (PrpCcarDriverDto)policyDto.getPrpCcarDriverDtoList().get(index);
	 strD1             = prpCcarDriverDto.getDriverName();
	 strD2             = prpCcarDriverDto.getDrivingLicenseNo();
	 strSexCode        = prpCcarDriverDto.getSex();
	 strSexName        = uiCodeAction.translateCodeCode("SexCode",strSexCode,true);
	 strMarriageCode   = prpCcarDriverDto.getMarriage();
	 strMarriageName   = uiCodeAction.translateCodeCode("Marriage",strMarriageCode,true);
	 strBusinessSource = prpCcarDriverDto.getBusinessSource();//从业类别代码
	 strBusinessName   = uiCodeAction.translateCodeCode("BusinessSource",strBusinessSource,true);
	 }
	 }*/
	//caopeng 11.30
	strDamageAddress = prpLregistDto.getDamageAddress();
	strdamageStartDate = prpLregistDto.getDamageStartDate().getYear() + " 年 " + prpLregistDto.getDamageStartDate().getMonth() + " 月 " + prpLregistDto.getDamageStartDate().getDate() + " 日 " + prpLregistDto.getDamageStartHour().substring(0, 2) + " 时 ";
%>







