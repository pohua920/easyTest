<%--
****************************************************************************
* DESC       ：机动车辆保险拒赔 /注销报告打印初始化
* AUTHOR     ：caopeng
* CREATEDATE ：2005-12-08
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
<%@page import="java.util.Iterator"%>
<%
	//变量声明部分
	String strClaimNo = request.getParameter("ClaimNo"); //赔案号
	String strPolicyNo = ""; //保单号
	String strRegistNo = ""; //报案号
	String strInsuredDate = ""; //保险期限
	String strInsuredName = ""; //被保险人
	String strDamageStartDate = ""; //出险时间
	String strDamageAddress = ""; //出险地点
	String strIndemnityDuty = ""; //所负责任
	String strInsureCarFlag = ""; //是否为本保单车辆
	String strLicenseNo = ""; //车牌号码
	String strBrandName = ""; //厂牌车型
	String strContext = ""; //案情介绍
	String strCancelReason = ""; //拒赔注销理由

	String strRiskCode = ""; //险种代码     
	String strKindCode = ""; //险别代码     
	String strCalculateFlag = ""; //是否记入保额
	String strUserCode = ((UserDto) request.getSession().getAttribute("user")).getUserCode();
	String strMessage = "";
	String strTextType = "";

	//代码翻译变量
	String strCode = "";
	String strName = "";
	boolean isChinese = true; //中文标志

	//对象定义部分                                             
	PrpCitemKindDto prpItemKindDto = null; //保单的ItemKindDto对象
	PrpLregistDto prpLregistDto = null; //RegistDto对象
	PrpLclaimDto prpLclaimDto = null; //ClaimDto对象
	PrpLthirdPartyDto prpLthirdPartyDto = null; //ThirdPartyDto对象
	PrpLltextDto prpLltextDto = null; //PrpLltextDto对象

	int intClaimCount = 0; //ClaimDto对象的记录数
	int intItemKindCount = 0; //ItemKindDto对象的记录数
	int intThirdPartyCount = 0; //ThirdPartyDto对象的记录数
	int index = 0;
	int intLtextCount = 0; //PrpLltextDto对象的记录数
	int intLtextCountTmp = 0;

	//得到ClaimDto,RegistDto,CertainLossDto,PolicyDto对象
	ClaimDto claimDto = (ClaimDto) request.getAttribute("claimDto");
	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");
	RegistDto registDto = (RegistDto) request.getAttribute("registDto");

	UICodeAction uiCodeAction = new UICodeAction();
	//得到prpLclaimDto对象
	prpLclaimDto = claimDto.getPrpLclaimDto();

	//得到保单号  
	strPolicyNo = prpLclaimDto.getPolicyNo();
	strCode = prpLclaimDto.getIndemnityDuty();
	strIndemnityDuty = uiCodeAction.translateCodeCode("IndemnityDuty", strCode, isChinese);

	//得到blPrpCitemKind对象的记录数
	if (policyDto.getPrpCitemKindDtoList() != null) {
		intItemKindCount = policyDto.getPrpCitemKindDtoList().size();
	}

	//得到blPrpLthirdParty对象的记录数
	if (claimDto.getPrpLthirdPartyDtoList() != null) {
		intThirdPartyCount = claimDto.getPrpLthirdPartyDtoList().size();
	}

	//得到PrpLltext对象的记录数
	if (claimDto.getPrpLltextDtoList() != null) {
		intLtextCount = claimDto.getPrpLltextDtoList().size();
	}

	//得到prpLregistDto对象
	prpLregistDto = registDto.getPrpLregistDto();
	strRegistNo = prpLregistDto.getRegistNo();

	//得到案情介绍
	UICheckAction uiCheckAction = new UICheckAction();
	CheckDto checkDto = uiCheckAction.findByPrimaryKey(strRegistNo);
	PrpLregistTextDto prpLregistTextDto = new PrpLregistTextDto();

	if (checkDto.getPrpLregistTextDtoList() != null) {
		Iterator iterator = checkDto.getPrpLregistTextDtoList().iterator();
		while (iterator.hasNext()) {
			PrpLregistTextDto prpLregistTextDtoTemp = (PrpLregistTextDto) iterator.next();
			strContext = strContext + prpLregistTextDtoTemp.getContext();
		}
	}
	strInsuredName = StringConvert.encode(prpLclaimDto.getInsuredName());

	//*****立案信息表PrpLregist*****

	strInsuredDate = "自" + prpLclaimDto.getStartDate().getYear() + "年" + prpLclaimDto.getStartDate().getMonth() + "月" + prpLclaimDto.getStartDate().getDate() + "日" + prpLclaimDto.getStartHour() + "时起"

	+ "至" + prpLclaimDto.getEndDate().getYear() + "年" + prpLclaimDto.getEndDate().getMonth() + "月" + prpLclaimDto.getEndDate().getDate() + "日" + prpLclaimDto.getEndHour() + "时止";

	//出险险种
	strCode = "";
	strName = "";
	strCode = StringConvert.encode(prpLclaimDto.getRiskCode());
	strName = uiCodeAction.translateRiskCode(strCode, isChinese);

	strDamageStartDate = prpLclaimDto.getDamageStartDate().getYear() + "年" + prpLclaimDto.getDamageStartDate().getMonth() + "月" + prpLclaimDto.getDamageStartDate().getDate() + "日" + prpLclaimDto.getDamageStartHour().substring(0, 2) + "时";
	strDamageAddress = StringConvert.encode(prpLclaimDto.getDamageAddress());
	//*****理赔车辆信息PrpLthirdParty*****

	if (claimDto.getPrpLthirdPartyDtoList() != null) {
		for (index = 0; index < intThirdPartyCount; index++) {
			prpLthirdPartyDto = (PrpLthirdPartyDto) claimDto.getPrpLthirdPartyDtoList().get(index);
			strInsureCarFlag = prpLthirdPartyDto.getInsureCarFlag();

			if (strInsureCarFlag.equals("1")) {
				strLicenseNo = prpLthirdPartyDto.getLicenseNo();
				strBrandName = StringConvert.encode(prpLthirdPartyDto.getBrandName());
			}
		}
	}
	//拒赔注销理由
	if (claimDto.getPrpLltextDtoList() != null) {
		for (index = 0; index < intLtextCount; index++) {
			prpLltextDto = (PrpLltextDto) claimDto.getPrpLltextDtoList().get(index);
			strTextType = StringConvert.encode(prpLltextDto.getTextType());
			if (strTextType != null && strTextType.equals("10")) {
				strCancelReason = strCancelReason + StringConvert.encode(prpLltextDto.getContext());
			}
		}
	}

	intLtextCountTmp = 0; //text的行数

	for (index = 0; index < strCancelReason.length(); index++) {
		if (strCancelReason.substring(index, index + 1).equals("\\")) {
			if (!(strCancelReason.substring(index).length() < 4)) {
				if (strCancelReason.substring(index, index + 4).equals("\\r\\n")) {
					intLtextCountTmp++; //只要有回车换行，intLtextCountTmp+1
				}
			}
		}
	}

	int x = 0;
	int y = 0;
	if (!(strCancelReason.length() < 4)) //如果strCancelReason.length()>=4，判断strCancelReason结尾是文字，还是回车换行
	{
		x = strCancelReason.length() - 4;
		y = strCancelReason.length();
		if (!strCancelReason.substring(x, y).equals("\\r\\n")) {
			intLtextCountTmp += 1;
		}
	} else
		//如果strCancelReason不足1行，intLtextCountTmp = 1; 
		intLtextCountTmp = 1;

	/*if( intLtextCountTmp>20 )
	{      
	   strCancelReason = "<br>";        
	}*/
	while (strCancelReason.indexOf("\\r\\n") != -1) {
		strCancelReason = strCancelReason.substring(0, strCancelReason.indexOf("\\r\\n")) + "<br>" + strCancelReason.substring(strCancelReason.indexOf("\\r\\n") + "\\r\\n".length());
	}
%>
