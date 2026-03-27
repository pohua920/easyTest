<%--
****************************************************************************
* DESC       ：机动车辆保险代查勘委托书打印初始化
* AUTHOR     ：zhulianyu
* CREATEDATE ：2005-11-15
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
 	String strContext = ""; //赔款计算书文字
 	String strDamageStartDate = ""; //出险时间
 	String strReportDate = ""; //报案时间
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
 	double dblAgentFee = 0; //代理费
 	String strSumThisPaid = ""; //
 	String strCSumThisPaid = ""; //
 	double dblSumThisPaid = 0; //
 	String strSumPaid = ""; //
 	String strCSumPaid = ""; //
 	double dblSumPaid = 0; //
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
 	String strSeatCount = ""; //座位數
 	String strTonCount = ""; //噸位數
 	String strFrameNo = ""; //车架号
 	String strEngineNo = ""; //发动机号
 	String[] subKindName = new String[6]; //附加险险别名称
 	String[] subKindAmount = new String[6]; //附加险保额
 	String strKindName = ""; //附加险别
 	String strFlag = ""; //主险附加险标志
 	String strKindAmount = ""; //附加险限额
 	String strCarInsuredRelation = ""; //所属性质代码
 	String strCarInsuredRelationName = "";//所属性质
 	String strUseNatureCode = ""; //使用性质
 	String strInsuredName = ""; //被保险人
 	String strBrandName = ""; //厂牌型号
 	String strDamageAddress = ""; //出险地点
 	String strDamageName = ""; //出险原因

 	//代码翻译变量
 	String strCode = "";
 	String strName = "";
 	boolean isChinese = true; //中文标志

 	//对象定义部分

 	PrpCitemCarDto prpItemCarDto = null; //ItemCarDto对象 
 	PrpCitemKindDto prpItemKindDto = null; //保单的ItemKindDto对象
 	PrpLchargeDto prpLchargeDto = null; //ChargeDto对象
 	PrpLctextDto prpLctextDto = null;
 	PrpLdriverDto prpLdriverDto = null;
 	PrpLregistDto prpLregistDto = null;
 	PrpLpropDto prpLpropDto = null;
 	PrpLextDto prpLextDto = null;
 	PrpCmainDto prpCmainDto = null; //保单主表对象

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
 	//System.out.println("zhulianyu"); 
 	//得到RegistDto,PolicyDto对象
 	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");
 	RegistDto registDto = (RegistDto) request.getAttribute("registDto");
 	UICodeAction uiCodeAction = new UICodeAction();
 	//得到prpCmainDto对象
 	if (policyDto.getPrpCmainDto() != null) {
 		prpCmainDto = policyDto.getPrpCmainDto();
 	}
 	//得到prpLregistDto对象
 	prpLregistDto = registDto.getPrpLregistDto();
 	//System.out.println("zhulianyu");
 	//得到保单号
 	strPolicyNo = prpCmainDto.getPolicyNo();

 	//得到blPrpCitemCar对象 
 	if (policyDto.getPrpCitemCarDtoList() != null) {
 		intItemCarCount = policyDto.getPrpCitemCarDtoList().size();
 	}
 	//System.out.println("zhulianyu");
 	//得到blPrpCitemKind对象的记录数
 	if (policyDto.getPrpCitemKindDtoList() != null) {
 		intItemKindCount = policyDto.getPrpCitemKindDtoList().size();
 	}

 	//报案信息
 	if (registDto.getPrpLregistDto() != null) {
 		strLicenseNo = prpLregistDto.getLicenseNo();
 		strEngineNo = prpLregistDto.getEngineNo();
 		strBrandName = prpLregistDto.getBrandName();
 		strEngineNo = prpLregistDto.getEngineNo();
 		strRegistNo = prpLregistDto.getRegistNo();
 		strDamageAddress = prpLregistDto.getDamageAddress();
 		//System.out.println("zhuilanyu"+strDamageAddress);
 		strDamageName = prpLregistDto.getDamageName();
 	}
 	//保单信息
 	if (policyDto.getPrpCmainDto() != null) {
 		strInsuredName = prpCmainDto.getInsuredName();
 	}
 	//System.out.println(strLicenseNo);
 %>

<script language="javascript">
function loadForm() {
	tdLicenseNo.innerHTML = '<%=StringConvert.encode(strLicenseNo)%>';
	tdEngineNo.innerHTML = '<%=StringConvert.encode(strEngineNo)%>';
	tdPolicyNo.innerHTML = '<%=StringConvert.encode(strPolicyNo)%>';
	tdInsuredName.innerHTML = '<%=StringConvert.encode(prpCmainDto.getInsuredName())%>';
	tdRegistNo.innerHTML = '<%=StringConvert.encode(prpLregistDto.getRegistNo())%>';
	tdDamageAddress.innerHTML = '<%=StringConvert.encode(prpLregistDto.getDamageAddress())%>';
	tdDamageName.innerHTML = '<%=StringConvert.encode(strDamageName)%>'; <% strDamageStartDate = prpLregistDto.getDamageStartDate().getYear() + " 年 " + prpLregistDto.getDamageStartDate().getMonth() + " 月 " + prpLregistDto.getDamageStartDate().getDate() + " 日 " + prpLregistDto.getDamageStartHour().substring(0, 2) + " 时 ";
	strReportDate = prpLregistDto.getReportDate().getYear() + " 年 " + prpLregistDto.getReportDate().getMonth() + " 月 " + prpLregistDto.getReportDate().getDate() + " 日 " + prpLregistDto.getReportHour().substring(0, 2) + " 时 ";
	String damangeAddressType = uiCodeAction.translateCodeCode("DamageAddress", prpLregistDto.getDamageAddressType(), true); %>
		tdDamageStartDate.innerHTML = '<%=strDamageStartDate%>';
	tdDamageAddress.innerHTML = '<%=StringConvert.encode(prpLregistDto.getDamageAddress())%>';
	tdReportDate.innerHTML = '<%=strReportDate%>'; <% strInsuredDate = "自 " + prpCmainDto.getStartDate().getYear() + "年" + prpCmainDto.getStartDate().getMonth() + "月" + prpCmainDto.getStartDate().getDate() + "日" + prpCmainDto.getStartHour() + "时起" + "至 " + prpCmainDto.getEndDate().getYear() + "年" + prpCmainDto.getEndDate().getMonth() + "月" + prpCmainDto.getEndDate().getDate() + "日" + prpCmainDto.getEndHour() + "时止"; %>
		tdInsuredDate.innerHTML = '<%=strInsuredDate%>';

	//*****理赔车辆信息PrpLthirdParty*****  
	<% //modify by lixiaohua 20040326 begin reason 赔款计算书厂牌型号打印不出
	if (policyDto.getPrpCitemCarDtoList() != null) {
		for (index = 0; index < intItemCarCount; index++) {
			prpItemCarDto = (PrpCitemCarDto) policyDto.getPrpCitemCarDtoList().get(index);
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
			tdBrandName.innerHTML = '<%=StringConvert.encode(prpItemCarDto.getBrandName())%>';
			tdSeatTonCount.innerHTML = '<%=strSeatCount + "座/" + strTonCount + "吨"%>';
			tdEngineNo.innerHTML = '<%=StringConvert.encode(prpItemCarDto.getEngineNo())%>';

			<% //System.out.println(prpItemCarDto.getLicenseNo()+" "+prpItemCarDto.getBrandName()+" ppppppppppppppppppppppppp");
			//条款类别
			strCode = "";
			strName = "";
			strCode = StringConvert.encode(prpItemCarDto.getClauseType());
			strName = uiCodeAction.translateCodeCode("ClauseType", strCode, isChinese); %> <%
		}
	}
	//*****标的子险信息PrpCitemKind*****
	dblSumAmount1 = 0;
	dblSumAmount2 = 0;

	if (policyDto.getPrpCitemKindDtoList() != null) {
		//System.out.println("有几个险别");
		int tempindex = 0; //定义临时变量
		for (index = 0; index < intItemKindCount; index++) {
			prpItemKindDto = (PrpCitemKindDto) policyDto.getPrpCitemKindDtoList().get(index);
			strRiskCode = prpItemKindDto.getRiskCode();
			strKindCode = prpItemKindDto.getKindCode();
			strKindName = prpItemKindDto.getKindName();
			strKindAmount = "" + prpItemKindDto.getAmount();
			strFlag = prpItemKindDto.getFlag();
			//strCalculateFlag = dbPrpDkind.getCalculateFlag().substring(0,2);
			strCalculateFlag = prpItemKindDto.getCalculateFlag();
			//strCalculateFlag="Y1";
			if (strFlag.substring(1, 2).equals("1") && strKindCode.equals(ConstantCodes.KINDCODE_D_A)) //主险车损险保额 
			{
				dblSumAmount1 += prpItemKindDto.getAmount();
				//System.out.println("车损险保额"+dblSumAmount1);
			}
			if (strFlag.substring(1, 2).equals("1") && strKindCode.equals(ConstantCodes.KINDCODE_D_B)) //主险三者險限額
			{
				dblSumAmount2 += prpItemKindDto.getAmount();
			}
			if (strFlag.substring(1, 2).equals("2") && tempindex < 6) //附加险
			{
				subKindName[tempindex] = strKindName; %> <%= "tdKindName" + tempindex %> .innerHTML = '<%=subKindName[tempindex]%>'; <% tempindex++;
			}
		}
	}
	//System.out.println(dblSumAmount1+"  "+dblSumAmount2);%>
	tdSumAmount1.innerHTML = '<%=new DecimalFormat("#,##0.00").format(dblSumAmount1) + "元"%>';
	tdSumAmount2.innerHTML = '<%=new DecimalFormat("#,##0.00").format(dblSumAmount2) + "元"%>'; <% //System.out.println(dblSumAmount1+"  "+dblSumAmount2);%>  
}
</script>
  