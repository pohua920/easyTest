<%--
****************************************************************************
* DESC       ：承诺支付医疗费用担保函打印初始化
* AUTHOR     ：zhaolu
* CREATEDATE ：2006-06-12
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
<%@page import="java.util.Iterator"%>
<%@page import="com.sinosoft.claim.common.ConstantCodes"%>

<%
	//变量声明部分
	String strClaimNo = ""; //赔案号
	String strRegistNo = ""; //报案号
	String strPolicyNo = ""; //保单号

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
	//伤员属性
	String strReferKind = "";//险别代码
	String strReferKindName = "";//险别名称
	String strD1 = "";//伤员姓名
	String strD2 = "";//伤员职业
	String strD3 = "";//伤员年龄
	String strSexCode = "";//伤员性别代码
	String strSexName = "";//伤员性别名称
	String strWoundRemark = "";//伤员伤势描述
	String strHospital = "";//施救医院 

	String strCheckSite = "";//查勘地点
	String strDamageCode = "";//出险原因代码
	String strDamageClause = "";//出险原因
	String strDamageTypeCode = "";//事故类型代码
	String strDamageTypeName = "";//事故类型说明

	//代码翻译变量

	String strCode = "";
	String strName = "";
	boolean isChinese = true; //中文标志

	//对象定义部分

	PrpLthirdPartyDto prpLthirdPartyDto = null; //ThirdPartyDto对象
	PrpLregistDto prpLregistDto = new PrpLregistDto();
	PrpLpersonTraceDto prpLpersonTraceDto = new PrpLpersonTraceDto();

	int intThirdPartyCount = 0; //ThirdPartyDto对象的记录数

	int index = 0;
	int intTemp = 0;

	RegistDto registDto = new RegistDto();
	registDto = (RegistDto) request.getAttribute("registDto");
	strPolicyNo = prpLregistDto.getPolicyNo();

	strRegistNo = request.getParameter("RegistNo");

	//代码转换
	UICodeAction uiCodeAction = new UICodeAction();

	//查勘信息
	UICheckAction uiCheckAction = new UICheckAction();
	CheckDto checkDto = uiCheckAction.findByPrimaryKey(strRegistNo);
	PrpLcheckDto prpLcheckDto = checkDto.getPrpLcheckDto();
	if (checkDto.getPrpLcheckDto() != null) {
		strCheckSite = prpLcheckDto.getCheckSite();
		//System.out.println("checkpoint"+strCheckSite);

		strDamageAddress = strCheckSite;
		strPolicyNo = prpLcheckDto.getPolicyNo();

		strDamageCode = prpLcheckDto.getDamageCode();
		strDamageClause = uiCodeAction.translateCodeCode("DamageCode", strDamageCode, true);
		strDamageTypeCode = prpLcheckDto.getDamageTypeCode();
		strDamageTypeName = uiCodeAction.translateCodeCode("DamageTypeCode", strDamageTypeCode, true);
	}
	//获得保单信息
	UIPolicyAction uiPolicyAction = new UIPolicyAction();
	PolicyDto policyDto = uiPolicyAction.findByPrimaryKey(strPolicyNo);
	PrpCitemCarDto prpCitemCarDto = new PrpCitemCarDto();

	//*****伤员信息

	int intPersonTrace = 0;
	if (registDto.getPrpLpersonTraceDtoList() != null) {
		intPersonTrace = registDto.getPrpLpersonTraceDtoList().size();
		for (index = 0; index < intPersonTrace; index++) {
			prpLpersonTraceDto = (PrpLpersonTraceDto) registDto.getPrpLpersonTraceDtoList().get(index);
			strReferKind = prpLpersonTraceDto.getReferKind();
			strReferKindName = uiCodeAction.translateKindCode(ConstantCodes.RISKCODE_DAA, strReferKind, true);
			strD1 = prpLpersonTraceDto.getPersonName();
			strD2 = prpLpersonTraceDto.getJobName();
			strD3 = String.valueOf(prpLpersonTraceDto.getPersonAge());
			strSexCode = prpLpersonTraceDto.getPersonSex();
			strSexName = uiCodeAction.translateCodeCode("SexCode", strSexCode, true);
			strWoundRemark = prpLpersonTraceDto.getWoundRemark();
			strHospital = prpLpersonTraceDto.getHospital();
		}
	}

	prpLregistDto = registDto.getPrpLregistDto();
	strInsuredName = prpLregistDto.getInsuredName();
	strDamageAddress = prpLregistDto.getDamageAddress();

	strdamageStartDate = String.valueOf(prpLregistDto.getDamageStartDate());
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
%>