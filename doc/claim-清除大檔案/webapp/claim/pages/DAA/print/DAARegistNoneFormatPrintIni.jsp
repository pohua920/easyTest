<%--
****************************************************************************
* DESC       ：机动车辆保险报案记录(代抄单)打印初始化
* AUTHOR     ：理赔组
* CREATEDATE ：2004-11-16
* MODIFYLIST ：   Name       Date            Reason/Contents
--------------------------------------------------------------------------
****************************************************************************
--%>

<%-- 引入bean类部分 --%>
<%@page import="java.text.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.claim.ui.control.action.*"%>
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="com.sinosoft.claim.dto.domain.*"%> 
<%@page import="com.sinosoft.claim.util.*"%> 
<%@page import="com.sinosoft.sysframework.common.util.*"%> 
<%@page import="com.sinosoft.sysframework.common.datatype.DateTime"%> 
<%@page import="com.sinosoft.utility.string.Str"%>
<%@page import="com.sinosoft.claim.common.ConstantCodes"%>
<%
	String strCode = "";
	String strName = "";
	boolean isChinese = true; //中文标志

	//对象定义部分

	int index = 0;
	PrpCmainDto prpMainDto = null; //保单的MainDto对象
	PrpCinsuredDto prpInsuredDto = null; //保单的InsuredDto对象
	PrpCitemCarDto PrpCitemCarDto = null; //保单的ItemcarDto对象
	PrpCcarDriverDto prpcarDriverDto = null; //保单的CarDriverDto对象
	PrpCitemKindDto prpCitemKindDto = null; //保单的ItemKindDto对象
	PrpCengageDto prpEngageDto = null; //保单的EngageDto对象
	PrpLclaimDto prpLclaimDto = null; //ClaimDto对象
	PrpLdriverDto prpLdriverDto = null; //DriverDto对象
	PrpLthirdPartyDto prpLthirdPartyDto = null; //ThirdPartyDto对象
	PrpPheadDto prpPheadDto = null; //PheadDto对象
	//reason:保险车辆出险信息建议显示报案出险摘要信息，目前只显示报案出险经过
	PrpLregistTextDto prpLregistTextDto = null;

	PrpCplanDto prpCplanDto = null;
	PrpLextDto prpLextDto = null;

	UICodeAction uiCodeAction = new UICodeAction();

	String strRegistNo1 = "";
	int intRegistCount = 0; //RegistDto对象的记录数
	int intInsuredCount = 0; //InsuredDto对象的记录数
	int intCarDriverCount = 0; //CarDriverDto对象的记录数
	int intItemKindCount = 0; //ItemKindDto对象的记录数
	int intItemCarCount = 0; //ItemCarDto对象的记录数
	int intEngageCount = 0; //EngageDto对象的记录数
	int intEngageCountTmp = 0; //textarea行数
	int intDriverCount = 0; //DriverDto对象的记录数
	int intThirdPartyCount = 0; //ThirdPartyDto对象的记录数
	int intPheadCount = 0; //PheadDto对象的记录数
	int intPheadCountTmp = 0;
	int intClaimCount = 0; //ClaimDto对象的记录数
	int intClaimCountTmp = 0;
	int intCompensateCount = 0; //Compensate对象的记录数   
	int intPlanCount = 0;

	String strInsuredNature = ""; //判断是自然人还是法人
	String strChangeLessFlag = ""; //判断是主驾驶还是副驾驶
	String strInsuredTerm = ""; //得到"/"的保险期间 
	String strClauseCode = ""; //得到特别约定代码：T0001
	String strClauses = ""; //得到特别约定内容
	String strFlag = ""; //判断是特别约定的名称还是内容，Flag[2]=0:名称，Flag[2]=1:内容
	String strEnrollDate = ""; //初次登记日期
	String strLicenseNo = ""; //保单中的号牌号码
	String strLicenseColorCode = ""; //保单中的号牌底色
	String strLicenseNo1 = ""; //理赔车辆信息中的号牌号码
	String strLicenseColorCode1 = ""; //理赔车辆信息中的号牌底色
	String strLicenseNo2 = ""; //号牌号码
	String strInsureCarFlag = ""; //是否为本保单车辆
	String strEndorseNo = ""; //批单号
	String strPheadText = ""; //批单信息
	String strClaimText = ""; //出险信息
	String strContext1 = ""; //出险摘要
	double dblSumPaid = 0; //赔款总计
	String strOperatorName = "";
	String strHandlerName = "";
	String strUnderwriteName = "";
	String strUserCode = "";
	String strUserName = "";
	String strInputDate = "";
	String strMessage = "";
	String strDelinquentFee = "";
	double douDelinquentFee = 0d;
	double douPlanFee = 0d;
	String[] strKindCode = null;
	String[] strKindName = null;
	String[] strDangerLevel = null;//风险水平
	String[] douAmount = null;
	String checkInfo = "";//查勘信息回复

	String strColorCode = "";//牌照颜色
	String outColorCode = "";//牌照颜色输出
	String strDirverFirstTime = "";//驾驶证初次领证日期
	String strInsuredCompany = "";//被保险人单位信息
	String strInsuredCompanyName = "";//被保险人单位信息
	String strCarClause = "";//基本条款类别
	String strCarClauseName = "";//基本条款类别名称
	double strSumPremium = 0;//保险费
	String strArgumentName = "";
	String strArgumentNumber = "";
	String strArgumentFirst = "";

	String policyStr = "";//是否是交强商业险关联，如果是现实关联保单的信息，不是各自显示

	String compelNo = "";
	String compelComName = "";
	String comName = "";
	String handerName1 = "";

	//取得policyDto，registDto，endorseDto
	RegistDto registDto = (RegistDto) request.getAttribute("registDto");
	PrpLregistDto prpLregistDto = (PrpLregistDto) request.getAttribute("prpLregistDto");
	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");
	EndorseDto endorseDto = (EndorseDto) request.getAttribute("endorseDto");
	prpMainDto = policyDto.getPrpCmainDto();
	String strPolicyNo = prpLregistDto.getPolicyNo(); //保单号码
	UIPolicyAction uiPolicyAction = new UIPolicyAction();
	//得到交强险保单信息
	if (registDto.getPrpLRegistRPolicyDtoOfCompel() != null) {
		compelNo = registDto.getPrpLRegistRPolicyDtoOfCompel().getPolicyNo();

		String comCode = uiPolicyAction.findByPrimaryKey(compelNo).getPrpCmainDto().getComCode();
		if (!(comCode == null || "".equals(comCode))) {
			compelComName = new UICodeAction().translateComCode(comCode, true);
		}
	}
	comName = new UICodeAction().translateComCode(prpMainDto.getComCode(), true);
	handerName1 = uiCodeAction.translateUserCode(prpMainDto.getHandlerCode(), isChinese);

	Collection prpLregistDtoList = new ArrayList();
	String registInfo = "";
	PrpLregistDto prpLregistDto1 = null;
	UIRegistAction uiRegistAction = new UIRegistAction();
	prpLregistDtoList = (ArrayList) uiRegistAction.findSamePolicyRegist(strPolicyNo);
	Iterator it = prpLregistDtoList.iterator();
	int count = 0;
	while (it.hasNext()) {
		count++;
		prpLregistDto1 = (PrpLregistDto) it.next();
		registInfo += "&nbsp;报案号：" + prpLregistDto1.getRegistNo() + "&nbsp;&nbsp;报案时间：" + prpLregistDto1.getDamageStartDate() + "<br>";
		if (count > 2) {
			registInfo += "数据过多，请查看历次出险信息！";
			break;
		}
	}

	//得到PrpCengage对象的记录数  
	if (policyDto.getPrpCengageDtoList() != null) {
		intEngageCount = policyDto.getPrpCengageDtoList().size();
	}

	//得到PrpPhead对象  
	if (endorseDto.getPrpPheadDtoList() != null) {
		intPheadCount = endorseDto.getPrpPheadDtoList().size();
	}

	UIClaimAction uiClaimAction = new UIClaimAction();
	String conditions = " PolicyNo='" + strPolicyNo + "' AND RegistNo !='" + prpLregistDto.getRegistNo() + "' AND ClaimDate<='" + prpLregistDto.getReportDate() + "' ";
	ArrayList listTemp = (ArrayList) uiClaimAction.findByConditions(conditions);
	if (listTemp != null) {
		intCompensateCount = listTemp.size();
	}

	//if(registDto.getPrpLregistTextDtoList()!=null){

	//}

	if (compelNo.equals(prpLregistDto.getPolicyNo())) {
		prpLregistDto.setPolicyNo("");
		policyStr = "交强保险基本信息";
	} else if (compelNo != null && !"".equals(compelNo) && !compelNo.equals(prpLregistDto.getPolicyNo())) {
		policyStr = "商业交强保险基本信息";
	} else if (compelNo == null || "".equals(compelNo)) {
		policyStr = " 商业保险基本信息";
	}
%>

<script language="javascript">
function loadForm() {

	tdPolicyNo.innerHTML = '商业险保险单号：' + '<%=prpLregistDto.getPolicyNo()%>';
	<!--    tdPolicyNo1.innerHTML = '保险单号：' + '<%=prpLregistDto.getPolicyNo()%>'; -->
	tdRegistNo.innerHTML = '报案号：' + '<%=prpLregistDto.getRegistNo()%>';

	//*****报案信息表PrpLregist*****
	<% //报案方式
	strCode = "";
	strName = "";
	//strCode = StringConvert.encode(prpLregistDto.getReportType());
	//strName = uiCodeAction.translateCodeCode("ReportType",strCode,isChinese);

	String strReportType = prpLregistDto.getReportType();
	/*
			if(prpLregistDto.getReportType().indexOf("电话")>=0)
			  strReportType = "■电话   □上门   □传真   □邮件   □其它"; 
			else if(prpLregistDto.getReportType().indexOf("上门")>=0)
			  strReportType = "□电话   ■上门   □传真   □邮件   □其它"; 
			else if(prpLregistDto.getReportType().indexOf("传真")>=0)
			  strReportType = "□电话   □上门   ■传真   □邮件   □其它"; 
			else if(prpLregistDto.getReportType().indexOf("电邮")>=0)
			  strReportType = "□电话   □上门   □传真   ■邮件   □其它"; 
			else
			  strReportType = "□电话   □上门   □传真   □邮件   ■其它"; 
			 */

	String strDateTemp = "";
	if (prpLregistDto.getDamageStartHour() != null) {
		//      if(prpLregistDto.getDamageStartHour().length()<2){
		strDateTemp = prpLregistDto.getDamageStartHour();
		//      }else{
		//        strDateTemp = prpLregistDto.getDamageStartHour().substring(0,2);
		//      }
	} %>
		tdReportType.innerHTML = '报案方式：' + '<%=strReportType%><%//=prpLregistDto.getReportType()%>';
	tdReportorName.innerHTML = '报案人姓名：' + '<%=prpLregistDto.getReportorName()%>';
	tdReportDate.innerHTML = '报案时间：' + '<%=prpLregistDto.getReportDate()%>' + ' ' + '<%=prpLregistDto.getReportHour()%>';
	//tdLinkerName.innerHTML      = '联系人：'   + '<%=StringConvert.encode(prpLregistDto.getLinkerName())%>';
	tdLinkerName1.innerHTML = '联系人：' + '<%=StringConvert.encode(prpLregistDto.getLinkerName())%>';
	//tdPhoneNumber.innerHTML     = '联系电话：' + '<%=StringConvert.encode(prpLregistDto.getPhoneNumber())%>';
	tdPhoneNumber1.innerHTML = '联系电话：' + '<%=StringConvert.encode(prpLregistDto.getPhoneNumber())%>';
	//tdDamageStartDate.innerHTML = '出险时间：' + '<%=prpLregistDto.getDamageStartDate()%>';
	tdDamageStartDate.innerHTML = '出险时间：' + '<%=prpLregistDto.getDamageStartDate()%>' + ' ' + '<%=strDateTemp%>';
	tdDamageName.innerHTML = '出险原因：' + '<%=StringConvert.encode(prpLregistDto.getDamageName())%>';
	tdDamageAddress.innerHTML = '出险地点：' + '<%=StringConvert.encode(prpLregistDto.getDamageAddress())%>' + '&nbsp;&nbsp;路段(&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;)'; <% String damageArea = prpLregistDto.getDamageAreaCode();
	if ("01".equals(damageArea)) {
		damageArea = "出险網域：■市内   □市外   □省内    □省外    □中国境外  ";
	} else if ("02".equals(damageArea)) {
		damageArea = "出险網域：□市内   ■市外   □省内    □省外    □中国境外  ";
	} else if ("03".equals(damageArea)) {
		damageArea = "出险網域：□市内   □市外   ■省内    □省外    □中国境外  ";
	} else if ("04".equals(damageArea)) {
		damageArea = "出险網域：□市内   □市外   □省内    ■省外    □中国境外  ";
	} else if ("10".equals(damageArea)) {
		damageArea = "出险網域：□市内   □市外   □省内    □省外    ■中国境外  ";
	} else {
		damageArea = "出险網域：□市内   □市外   □省内    □省外    □中国境外  ";
	} %>
		tdDamageArea.innerHTML = '<%=StringConvert.encode(damageArea)%>'; <% //是否是第一现场报案
	strCode = "";
	strName = "";
	strCode = prpLregistDto.getFirstSiteFlag();
	if (strCode.equals("0")) {
		strName = "否";
	} else {
		strName = "是";
	} %>
		tdFirstSiteFlag.innerHTML = '是否是第一现场报案：' + '<%=strName%>';
	<!--    tdPersonInjure.innerHTML    = '伤亡人员：';  -->
	<!--   tdDamageAreaName.innerHTML  = '出险網域：'+'<%=prpLregistDto.getDamageAreaName()%>'; -->
	<% String damageAddressType = prpLregistDto.getDamageAddressType();
	System.out.println("测试数据damageAddressType：" + damageAddressType);
	if ("06".equals(damageAddressType)) {
		damageAddressType = "出险地点分类：□高速公路    □普通公路    □城市道路    □乡村便道和机耕道    □场院及其它&nbsp";
	} else if ("13".equals(damageAddressType)) {
		damageAddressType = "出险地点分类：□高速公路    □普通公路    □城市道路    □乡村便道和机耕道    □场院及其它&nbsp";
	} else if ("14".equals(damageAddressType)) {
		damageAddressType = "出险地点分类：□高速公路    □普通公路    □城市道路    □乡村便道和机耕道    □场院及其它&nbsp";
	} else if ("15".equals(damageAddressType)) {
		damageAddressType = "出险地点分类：□高速公路    □普通公路    □城市道路    □乡村便道和机耕道    □场院及其它&nbsp";
	} else if ("16".equals(damageAddressType)) {
		damageAddressType = "出险地点分类：□高速公路    □普通公路    □城市道路    □乡村便道和机耕道    □场院及其它&nbsp";
	} %>
		tdDamageAddressType.innerHTML = '<%=damageAddressType%>'; <% //处理部门
	strCode = "";
	strCode = StringConvert.encode(prpLregistDto.getHandleUnit());
	String strHandleUnit = uiCodeAction.translateCodeCode("HandleUnit", strCode, true);
	String strComCode = StringConvert.encode(prpLregistDto.getComCode());
	String strComName = uiCodeAction.translateComCode(strComCode, true);
	if ("01".equals(strCode)) {
		strHandleUnit = "■交警    □派出所     □消防部门    □保险公司    □自行处理    □其它&nbsp";
	} else if ("02".equals(strCode)) {
		strHandleUnit = "□交警    ■派出所     □消防部门    □保险公司    □自行处理    □其它&nbsp";
	} else if ("03".equals(strCode)) {
		strHandleUnit = "□交警    □派出所     ■消防部门    □保险公司    □自行处理    □其它&nbsp";
	} else if ("04".equals(strCode)) {
		strHandleUnit = "□交警    □派出所     □消防部门    ■保险公司    □自行处理    □其它&nbsp";
	} else if ("05".equals(strCode)) {
		strHandleUnit = "□交警    □派出所     □消防部门    □保险公司    ■自行处理    □其它&nbsp";
	} else if ("99".equals(strCode)) {
		strHandleUnit = "□交警    □派出所     □消防部门    □保险公司    □自行处理    ■其它&nbsp";
	} else {
		strHandleUnit = "□交警    □派出所     □消防部门    □保险公司    □自行处理    □其它&nbsp";
	} %>
		tdHandleUnit.innerHTML = '事故处理部门：' + '<%=strHandleUnit%>';
	//tdHandleUnit1.innerHTML = '承保公司：' +'<%=Str.encode(strComName)%>';
	<% 
	//承保公司
	strName = "";
	strName = StringConvert.encode(prpLregistDto.getComName()); %>

	//*****保险关系人表PrpCinsured*****
	tdInsuredName.innerHTML = '被保险人：';
	tdInsuredAddress.innerHTML = '被保险人住址：';
	tdInsuredPostCode.innerHTML = '邮政编码：';
	tdMobile.innerHTML = '移动电话：';
	//tdCustomerType.innerHTML    = '客户类别：';
	//tdInsuredCompany.innerHTML    = '被保险人单位性质：';
	<% String strInsuredFlag = ""; //判断是投保人还是被保险人 
	if (policyDto.getPrpCinsuredDtoList() != null) {
		ArrayList prpCinsuredDtoList = policyDto.getPrpCinsuredDtoList();
		intInsuredCount = prpCinsuredDtoList.size();
		for (index = 0; index < intInsuredCount; index++) {
			prpInsuredDto = (PrpCinsuredDto)((ArrayList) prpCinsuredDtoList).get(index);
			strInsuredFlag = prpInsuredDto.getInsuredFlag();
			strInsuredCompany = prpInsuredDto.getBusinessSource();
			strInsuredCompanyName = uiCodeAction.translateCodeCode("BusinessDetail", strInsuredCompany, isChinese);
			if (strInsuredFlag.equals("1")) //被保险人
			{
				//关系人性质(即客户类别)
				strCode = "";
				strName = "";
				strCode = prpInsuredDto.getInsuredType();
				strName = uiCodeAction.translateCodeCode("InsuredType", strCode, isChinese); %>
					tdInsuredName.innerHTML = '被保险人：' + '<%=StringConvert.encode(prpInsuredDto.getInsuredName())%>';
				tdInsuredAddress.innerHTML = '被保险人住址：' + '<%=StringConvert.encode(prpInsuredDto.getInsuredAddress())%>';
				tdInsuredPostCode.innerHTML = '邮政编码：' + '<%=StringConvert.encode(prpInsuredDto.getPostCode())%>';
				tdMobile.innerHTML = '移动电话：' + '<%=StringConvert.encode(prpInsuredDto.getMobile())%>';
				//tdInsuredCompany.innerHTML = '被保险人单位性质：'+ '<%=strInsuredCompanyName%>';
				<% //单位性质  (法人（即单位）才有单位性质内容)
				strCode = "";
				strName = "";
				if (strInsuredNature.equals("4")) //法人（即单位）
				{
					strCode = prpInsuredDto.getBusinessSort();
					strName = "&nbsp&nbsp(" + uiCodeAction.translateCodeCode("BusinessSort", strCode, isChinese) + ")";
				} else if (strInsuredNature.equals("3")) //自然人（即个人）
				{
					strName = "";
				} %>

				<%
			}
		}
	} %>
	//--------------保单基本信息表PrpCmain***** 
	<% //保险期间
	strInsuredTerm = prpMainDto.getStartDate().getYear() + "-" + prpMainDto.getStartDate().getMonth() + "-" + prpMainDto.getStartDate().getDate() + "至" + prpMainDto.getEndDate().getYear() + "-" + prpMainDto.getEndDate().getMonth() + "-" + prpMainDto.getEndDate().getDate(); %>
		tdInsuredTerm.innerHTML = '保险期间：' + '<%=strInsuredTerm%>'; <% // 保险费
	strSumPremium = prpMainDto.getSumPremium();

	//争议解决方式
	strCode = "";
	strName = "";
	strCode = StringConvert.encode(prpMainDto.getArgueSolution());
	if (strCode.equals("1")) //诉讼
	{
		strName = "诉讼";
	} else if (strCode.equals("2")) //仲裁
	{
		strName = "仲裁";
	} %>
		tdSumPremium.innerHTML = '应收保费：' + '<%=strSumPremium%>';;
	// tdArgue.innerHTML='争议解决方式：'      + '<%=strName%>';
	<% //签单人
	strCode = "";
	strCode = StringConvert.encode(prpMainDto.getOperatorCode());
	strOperatorName = uiCodeAction.translateUserCode(strCode, isChinese);

	//经办人
	strCode = "";
	//reason:归属业务员
	//strCode = StringConvert.encode(prpMainDto.getHandlerCode());
	strCode = StringConvert.encode(prpMainDto.getHandler1Code());
	strHandlerName = uiCodeAction.translateUserCode(strCode, isChinese);
	//核保人
	strUnderwriteName = StringConvert.encode(prpMainDto.getUnderWriteName());

	//抄单人
	strUserCode = prpLregistDto.getOperatorCode();
	strUserName = uiCodeAction.translateUserCode(strUserCode, isChinese);

	//抄单日期
	strInputDate = DateTime.current().getYear() + "年" + DateTime.current().getMonth() + "月" + DateTime.current().getDay() + "日"; %>

	tdHandlerName.innerHTML = '经办人：' + '<%=strHandlerName%>';
	tdUnderwriteName.innerHTML = '核保人：' + '<%=strUnderwriteName%>';
	tdUserName.innerHTML = '抄单人：' + '<%=strUserName%>';
	tdInputDate.innerHTML = '抄单日期：' + '<%=strInputDate%>';
	//tdOperatorName.innerHTML   ='签单人：'+'<%=strOperatorName%>';

	//------------------理赔车辆信息(三者车)PrpLthirdParty*****
	tdLicenseNo.innerHTML = '号牌号码：';
	//tdColorCode.innerHTML        = '牌照底色：';
	tdUseYears.innerHTML = '已使用年限：';

	tdBrandName.innerHTML = '厂牌型号：';
	tdEngineNo.innerHTML = '发动机号：';

	tdFrameNo.innerHTML = '车架号(VIN)：';

	//tdVinCode.innerHTML          = 'VIN码：';
	//tdCarRegistFirst.innerHTML   = '车辆初次登记日期：';

	tdUseNatureCode.innerHTML = '车辆使用性质：';
	// tdSeatCount.innerHTML     = '核定载客: ';
	tdRunAreaName.innerHTML = '车辆行驶網域：';
	tdPurchasePrice.innerHTML = '新车购置价：';
	<!--   tdLicenseNo1.innerHTML      = '号牌号码：'; -->
	tdUseYears.innerHTML = '已使用年限：';
	<!--   tdBrandName1.innerHTML  =tdBrandName.innerHTML; -->

	<% String carHavePerson = "";
	String carWeight = "";
	if (registDto.getPrpLthirdPartyDtoList() != null) {
		intThirdPartyCount = registDto.getPrpLthirdPartyDtoList().size();
		for (index = 0; index < intThirdPartyCount; index++) {
			prpLthirdPartyDto = (PrpLthirdPartyDto) registDto.getPrpLthirdPartyDtoList().get(index);
			strInsureCarFlag = prpLthirdPartyDto.getInsureCarFlag();

			if (strInsureCarFlag.equals("1")) {
				strLicenseNo = StringConvert.encode(prpLthirdPartyDto.getLicenseNo()); %>
					tdLicenseNo.innerHTML = '号牌号码：' + '<%=strLicenseNo%>';
				<!--       tdLicenseNo1.innerHTML      = '号牌号码：'         + '<%=strLicenseNo%>'; -->
				tdUseYears.innerHTML = '已使用年限：' + '<%=DataUtils.zeroToEmpty(prpLthirdPartyDto.getUseYears())%>' + '年'; <% //号牌底色
				strCode = "";
				strName = "";
				strCode = StringConvert.encode(prpLthirdPartyDto.getLicenseColorCode());
				strName = "";
				strLicenseColorCode = strCode;
				strName = uiCodeAction.translateCodeCode("LicenseColor", strCode, isChinese); %>

				tdBrandName.innerHTML = '厂牌型号：' + '<%=StringConvert.encode(prpLthirdPartyDto.getBrandName())%>';
				<!--     tdBrandName1.innerHTML        = tdBrandName.innerHTML ; -->
				tdEngineNo.innerHTML = '发动机号：' + '<%=StringConvert.encode(prpLthirdPartyDto.getEngineNo())%>';

				tdFrameNo.innerHTML = '车架号（VIN）：' + '<%=StringConvert.encode(prpLthirdPartyDto.getFrameNo())%>';

				// tdVinCode.innerHTML   = 'VIN号：' + '<%=StringConvert.encode(prpLthirdPartyDto.getVINNo())%>';       
				<% //车辆种类
				strCode = "";
				strName = "";
				strCode = StringConvert.encode(prpLthirdPartyDto.getCarKindCode());
				strName = uiCodeAction.translateCodeCode("CarKind", strCode, isChinese); %>
				//tdCarType.innerHTML  = '车辆种类：'+ '<%=strName%>';
				<% //条款类别
				strCode = "";
				strName = "";
				strCode = StringConvert.encode(prpLthirdPartyDto.getClauseType());
				strName = uiCodeAction.translateCodeCode("ClauseType", strCode, isChinese); %>

				tdUseNatureCode.innerHTML = '车辆使用性质：';
				//tdSeatCount.innerHTML     = '核定载客 '+ '&nbsp;&nbsp;' + ' 人' +  ' 核定载质量 '+ '&nbsp;&nbsp;' + ' 千克';
				tdRunAreaName.innerHTML = '车辆行驶網域：';
				tdPurchasePrice.innerHTML = '新车购置价：' + '&nbsp;&nbsp;' + '元';
				//tdCarRegistFirst.innerHTML = '车辆初次登记日期：'
				<%
			}
		}
	} %> <% //------------机动车险标的信息表PrpCitemCar*****
	if (policyDto.getPrpCitemCarDtoList() != null) {
		intItemCarCount = policyDto.getPrpCitemCarDtoList().size();
		for (index = 0; index < intItemCarCount; index++) {
			PrpCitemCarDto = (PrpCitemCarDto) policyDto.getPrpCitemCarDtoList().get(index);
			strLicenseNo2 = StringConvert.encode(PrpCitemCarDto.getLicenseNo());
			strColorCode = PrpCitemCarDto.getColorCode();
			carHavePerson = PrpCitemCarDto.getSeatCount() + "";
			carWeight = PrpCitemCarDto.getTonCount() + "";
			outColorCode = uiCodeAction.translateCodeCode("ColorCode", strColorCode, isChinese);
			strCarClause = PrpCitemCarDto.getClauseType();
			strCarClauseName = uiCodeAction.translateCodeCode("ClauseType", strCarClause, isChinese);

			if (strLicenseNo2.equals(strLicenseNo)) {
				//使用性质
				strCode = "";
				strName = "";
				strCode = StringConvert.encode(PrpCitemCarDto.getUseNatureCode());
				strName = uiCodeAction.translateCodeCode("UseNature", strCode, isChinese); %>
				//tdColorCode.innerHTML     ='牌照颜色：'         + '<%=outColorCode%>';

				tdUseNatureCode.innerHTML = '使用性质：' + '<%=strName%>';
				//tdSeatCount.innerHTML     = '核定载客 '           + '<%=PrpCitemCarDto.getSeatCount()%>' + ' 人' + ' 核定载质量 '      + '<%=DataUtils.zeroToEmpty(PrpCitemCarDto.getTonCount())%>' + ' 千克';
				//tdCarClause.innerHTML			= '基本条款类别：'	+ '<%=strCarClauseName%>';
				<% //初次登记日期
				strEnrollDate = PrpCitemCarDto.getEnrollDate().toString();
				if ((strEnrollDate == null) || (strEnrollDate.equals(""))) {
					strEnrollDate = "";
				} %>
				//tdCarRegistFirst.innerHTML = '车辆初次登记日期：'      + '<%=strEnrollDate%>';  
				<% //行驶区域
				strCode = "";
				strName = "";
				strCode = StringConvert.encode(PrpCitemCarDto.getRunAreaCode());
				if (!strCode.equals("")) {
					strName = uiCodeAction.translateCodeCode("RunArea", strCode, true);
				} else {
					strName = "中华人民共和国境内(不含港澳台)";
				} %>
					tdRunAreaName.innerHTML = '车辆行驶網域：' + '<%=strName%>' + '<br>是否足额交费：';
				tdPurchasePrice.innerHTML = '新车购置价：' + '<%=new DecimalFormat("#,##0.00").format(PrpCitemCarDto.getPurchasePrice())%>' + '元'; <%
			}
		}
	} %>
	//车险驾驶员信息表PrpLdriver*****
	tdDriverName.innerHTML = '驾驶员姓名：';
	tdDrivingCarType.innerHTML = '准驾车型：';

	tdDrivingLicenseNo.innerHTML = '驾驶证号码：';
	//tdDriverFirstTime.innerHTML = '驾驶证初次领证日期：';
	<%
	if (registDto.getPrpLdriverDtoList() != null) {
		intDriverCount = registDto.getPrpLdriverDtoList().size();
		PrpLdriverDto prpLdriverDtoTemp = new PrpLdriverDto();
		for (index = 0; index < intDriverCount; index++) {
			prpLdriverDto = (PrpLdriverDto) registDto.getPrpLdriverDtoList().get(index);
			strLicenseNo1 = prpLdriverDto.getLicenseNo();
			strLicenseColorCode1 = prpLdriverDto.getLicenseColorCode();
			if (index == 0) {
				prpLdriverDtoTemp = (PrpLdriverDto) registDto.getPrpLdriverDtoList().get(index);
			}
			strDirverFirstTime = prpLdriverDto.getReceiveLicenseDate().toString();
			if ((strDirverFirstTime == null) || (strDirverFirstTime.equals(""))) {
				strDirverFirstTime = "";
			}

			if (strLicenseNo1.equals(strLicenseNo) && strLicenseColorCode1.equals(strLicenseColorCode)) { %>
					tdDriverName.innerHTML = '驾驶员姓名：' + '<%=StringConvert.encode(prpLdriverDto.getDriverName())%>';
				tdDrivingCarType.innerHTML = '准驾车型：' + '<%=StringConvert.encode(prpLdriverDto.getDrivingCarType())%>';

				tdDrivingLicenseNo.innerHTML = '驾驶证号码：' + '<%=StringConvert.encode(prpLdriverDto.getDrivingLicenseNo())%>';
				//tdDriverFirstTime.innerHTML = '驾驶证初次领证日期：'+'<%=strDirverFirstTime%>';
				<%
			}
		}
		if (intDriverCount > 0) { %>
				tdDriverName.innerHTML = '驾驶员姓名：' + '<%=StringConvert.encode(prpLdriverDtoTemp.getDriverName())%>';
			tdDrivingCarType.innerHTML = '准驾车型：' + '<%=StringConvert.encode(prpLdriverDtoTemp.getDrivingCarType())%>';

			tdDrivingLicenseNo.innerHTML = '驾驶证号码：' + '<%=StringConvert.encode(prpLdriverDtoTemp.getDrivingLicenseNo())%>';

			<%
		}
	} %>
	//车辆驾驶员关系表PrpCcarDriver*****
	//tdArgument.innerHTML = '约定驾驶员';
	// tdArgumentName.innerHTML = '驾驶员名称：';
	//tdArgumentNumber.innerHTML = '驾驶证号码：';
	//tdArgumentFirst.innerHTML = '初次领证日期：';
	<%
	if (policyDto.getPrpCcarDriverDtoList() != null) {
		intCarDriverCount = policyDto.getPrpCcarDriverDtoList().size();
		for (index = 0; index < intCarDriverCount; index++) {
			prpcarDriverDto = (PrpCcarDriverDto) policyDto.getPrpCcarDriverDtoList().get(index);
			strChangeLessFlag = prpcarDriverDto.getChangelessFlag();

			if (strChangeLessFlag.equals("1")) //主驾驶
			{
				strArgumentName = prpcarDriverDto.getDriverName();
				strArgumentNumber = prpcarDriverDto.getDrivingLicenseNo();
				strArgumentFirst = prpcarDriverDto.getAcceptLicenseDate().toString(); %>
				//tdArgumentName.innerHTML = '驾驶员名称：'+'<%=strArgumentName%>';
				//tdArgumentNumber.innerHTML = '驾驶证号码：'+'<%=strArgumentNumber%>';
				//tdArgumentFirst.innerHTML = '初次领证日期：'+'<%=strArgumentFirst%>'; 
				<%
			} else if (strChangeLessFlag.equals("0")) //从驾驶员
			{ %>

				<%
			}
		}
	} %>


	//特别约定表PrpCengage*****
	<% strClauses = "";
	for (index = 0; index < intEngageCount; index++) {
		prpEngageDto = (PrpCengageDto) policyDto.getPrpCengageDtoList().get(index);
		strClauseCode = prpEngageDto.getClauseCode();
		String strTitleFlag = prpEngageDto.getTitleFlag();
		strClauses += StringConvert.encode(prpEngageDto.getClauses());
	}

	//检查特别约定打出来是几行
	intEngageCountTmp = 0; //textarea的行数

	for (index = 0; index < strClauses.length(); index++) {
		if (strClauses.substring(index, index + 1).equals("\\")) {
			if (!(strClauses.substring(index).length() < 4)) {
				if (strClauses.substring(index, index + 4).equals("\\r\\n")) {
					intEngageCountTmp += 1; //只要有回车换行，intEngageCountTmp+1
				}
			}
		}
	}

	int x = 0;
	int y = 0;
	if (!(strClauses.length() < 4)) //如果strClauses.length()>=4，判断strClauses结尾是文字，还是回车换行
	{
		x = strClauses.length() - 4;
		y = strClauses.length();
		if (!strClauses.substring(x, y).equals("\\r\\n")) {
			intEngageCountTmp += 1;
		}
	} else
	//如果strClauses不足1行，intEngageCountTmp = 1; 
		intEngageCountTmp = 1;

	if (intEngageCountTmp > 3) { %>
			tdEngage.innerHTML = ' 特别约定：内容较多，请详见特别约定清单'; <%
	} else { %>
			tdEngage.innerHTML = '<%=strClauses%>'; <%
	} %> <% //批改信息表PrpPhead*****
	strPheadText = "";
	int intPheadCount1 = 0;
	String strEndorType = "";
	//理赔组
	if (intPheadCount > 7) {
		intPheadCountTmp = 7;
	} else {
		intPheadCountTmp = intPheadCount;
	}

	//intPheadCount = endorseDto.getPrpPHeadDtoList().size();
	for (index = 0; index < intPheadCountTmp; index++) {
		prpPheadDto = (PrpPheadDto) endorseDto.getPrpPheadDtoList().get(index);
		strEndorseNo = prpPheadDto.getEndorseNo();
		strEndorType = prpPheadDto.getEndorType();
		if (!strEndorType.equals("56")) {

			//得到批单号和批单日期
			strPheadText += "批单号：" + StringConvert.encode(prpPheadDto.getEndorseNo());

			strPheadText += " 批单日期：" + prpPheadDto.getEndorDate().getYear() + "年" + prpPheadDto.getEndorDate().getMonth() + "月" + prpPheadDto.getEndorDate().getDate() + "日";

			//得到blPrpPmain对象
			UIEndorseAction uiEndorseAction = new UIEndorseAction();
			EndorseDto endorseDtoTemp = uiEndorseAction.findByPrimaryKey(strEndorseNo);
			PrpPmainDto PrpPmainDto = endorseDtoTemp.getPrpPmainDto();

			//得到保额变化量和保费变化量
			if (PrpPmainDto != null) {
				strPheadText += " 保额变化量：" + new DecimalFormat("0.00").format(PrpPmainDto.getChgAmount());
				strPheadText += " 保费变化量：" + new DecimalFormat("0.00").format(PrpPmainDto.getChgPremium());
			}
			strPheadText += "<br>";
		} else {
			intPheadCount1++;
		}
	}

	if (intPheadCount > 7) {
		strPheadText += "(其余批改信息请见批单)";
	} %>
		tdPheadText.innerHTML = '<%=strPheadText%>';

	<% strClaimText = "";

	strClaimText += " 出险日期：" + prpLregistDto.getDamageStartDate().getYear() + "年" + prpLregistDto.getDamageStartDate().getMonth() + "月" + prpLregistDto.getDamageStartDate().getDate() + "日" + "<br>";

	strClaimText += " 出险原因：" + StringConvert.encode(prpLregistDto.getDamageName());
	strClaimText += "\\r\\n";
	//reason:保险车辆出险信息建议显示报案出险摘要信息，目前只显示报案出险经过
	if (registDto.getPrpLregistTextDtoList() != null) {
		int intSizeTemp = registDto.getPrpLregistTextDtoList().size();
		if (intSizeTemp > 0) {
			for (int i = 0; i < intSizeTemp; i++) {
				if (((PrpLregistTextDto) registDto.getPrpLregistTextDtoList().get(i)).getTextType().trim().equals("1")) {
					prpLregistTextDto = (PrpLregistTextDto) registDto.getPrpLregistTextDtoList().get(i);
					if (!prpLregistTextDto.getContext().trim().equals("")) {
						strContext1 += StringConvert.encode(prpLregistTextDto.getContext()) + "\\r\\n";
					}
				}
				// 查勘回复信息取查勘报告
				if (((PrpLregistTextDto) registDto.getPrpLregistTextDtoList().get(i)).getTextType().trim().equals("3")) {
					prpLregistTextDto = (PrpLregistTextDto) registDto.getPrpLregistTextDtoList().get(i);
					if (!prpLregistTextDto.getContext().trim().equals("")) {
						checkInfo += StringConvert.encode(prpLregistTextDto.getContext()) + "<br>";
					}
				}
			}
			if (!strContext1.trim().equals("")) {
				strClaimText += strContext1;
			}
		}
	}


	if (intCompensateCount > 8) {
		strClaimText += "(其余出险信息请见立案)";
	}
	//计算赔款总计
	for (int m = 0; m < intCompensateCount; m++) {
		prpLclaimDto = (PrpLclaimDto) listTemp.get(m);
		dblSumPaid = dblSumPaid + prpLclaimDto.getSumPaid();
	} %>
		tdPheadCount.innerHTML = '本单批改次数：' + '<%=DataUtils.zeroToEmpty(intPheadCount - intPheadCount1)%>';
	tdClaimCount.innerHTML = '车辆出险次数：' + '<%=DataUtils.zeroToEmpty(intCompensateCount)%>';
	tdCompensateCount.innerHTML = '赔款次数：' + '<%=DataUtils.zeroToEmpty(intClaimCount)%>';
	tdSumPaid.innerHTML = '赔款总计：' + '<%=DataUtils.zeroToEmpty(dblSumPaid)%>';
	tdContext.innerHTML = '出险经过及损失情况：（行驶方向，避让措施，财物损坏部位等）&nbsp;' + '<%=strClaimText%>'; <%
	if (policyDto.getPrpCitemKindDtoList() != null) {
		intItemKindCount = policyDto.getPrpCitemKindDtoList().size();
		PrpCitemKindDto rPrpCitemKindDto = new PrpCitemKindDto();
		rPrpCitemKindDto = (PrpCitemKindDto) request.getAttribute("rPrpCitemKindDto");
		if (rPrpCitemKindDto != null)
			intItemKindCount = intItemKindCount + 1;
		strKindCode = new String[intItemKindCount];
		strKindName = new String[intItemKindCount];
		strDangerLevel = new String[intItemKindCount];
		douAmount = new String[intItemKindCount];
		for (index = 0; index < intItemKindCount; index++) {
			if (rPrpCitemKindDto != null) {
				if (index == intItemKindCount - 1) {
					strKindCode[index] = rPrpCitemKindDto.getKindCode();
					strKindName[index] = rPrpCitemKindDto.getKindName();
					douAmount[index] = new Double(rPrpCitemKindDto.getAmount()).toString();
				}
			}
			if (index != intItemKindCount - 1 || rPrpCitemKindDto == null) {
				prpCitemKindDto = (PrpCitemKindDto) policyDto.getPrpCitemKindDtoList().get(index);
				strKindCode[index] = prpCitemKindDto.getKindCode();
				strKindName[index] = prpCitemKindDto.getKindName();
				strDangerLevel[index] = "";
				if (prpCitemKindDto.getModeCode() != null && !prpCitemKindDto.getModeCode().equals("") && (prpCitemKindDto.getKindCode().equals(ConstantCodes.KINDCODE_D_A) || prpCitemKindDto.getKindCode().equals(ConstantCodes.KINDCODE_D_B))) {
					if (prpCitemKindDto.getModeCode().equals("1")) {
						strDangerLevel[index] = "(风险水平A)";
					}
					if (prpCitemKindDto.getModeCode().equals("2")) {
						strDangerLevel[index] = "(风险水平B)";
					}
					if (prpCitemKindDto.getModeCode().equals("3")) {
						strDangerLevel[index] = "(风险水平C)";
					}
				}

				/*else{ */
				if (prpCitemKindDto.getAmount() == 0) {
					douAmount[index] = "0.00";
				} else {
					douAmount[index] = String.valueOf(new DecimalFormat("#,##0.00").format(prpCitemKindDto.getAmount()));
				}
				if (strKindCode[index].equals("F")) {
					if ((prpCitemKindDto.getModeName()).equals("国产玻璃")) {
						douAmount[index] += "（国产玻璃）";
					} else {
						douAmount[index] += "（进口玻璃）";
					}
				}
			}
		}
	}
	//System.out.println("^^^^^^^^^^^^^^"+intItemKindCount);%>
	<!--  tdDelinquentFee.innerHTML  = '是否足额交费：';  -->
	<!--  tdPlanFee.innerHTML        = '应收保费：' ; -->
	<!--   tdPlanDate.innerHTML       = '交费日期：'; -->
	<% String strPlanFee = "";
	if (policyDto.getPrpCplanDtoList() != null) {
		intPlanCount = policyDto.getPrpCplanDtoList().size();
		for (index = 0; index < intPlanCount; index++) {
			prpCplanDto = (PrpCplanDto) policyDto.getPrpCplanDtoList().get(index);
			douDelinquentFee = prpCplanDto.getDelinquentFee();
			if (douDelinquentFee == 0) {
				strDelinquentFee = "是";
				strPlanFee = "已足额交费";
			} else {
				strDelinquentFee = "否";
				strPlanFee = "还欠保费：" + douDelinquentFee;
			} %>
			<!--  tdDelinquentFee.innerHTML  = '是否足额交费：'+'<%=strDelinquentFee%>';  -->
			<!-- tdPlanFee.innerHTML        = '应收保费：' +'<%=strPlanFee%>'; -->
			<!-- tdPlanDate.innerHTML       = '交费日期：'+'<%=prpCplanDto.getPlanDate()%>'; -->
			<%
		}
	} %> <% prpLextDto = registDto.getPrpLextDto();
	String personInjure = "伤亡人员：□第三者（伤 0 人 亡 0 人）   □车上人员（伤 0 人 亡 0 人）&nbsp";
	if (prpLextDto != null) {
		long personInjureB = prpLextDto.getPersonInjureB();
		long personDeathB = prpLextDto.getPersonDeathB();
		long personInjureD1 = prpLextDto.getPersonInjureD1();
		long personDeathD1 = prpLextDto.getPersonDeathD1();
		String personInjure1 = "";
		String personInjure2 = "";
		personInjure = "伤亡人员：";

		if (personInjureB == 0) {
			if (personDeathB == 0) {
				personInjure += "□第三者（伤 0 人 亡 0 人）";
			} else {
				personInjure += "■第三者（伤 0 人 亡 " + personDeathB + " 人）";
			}
		} else {
			if (personDeathB == 0) {
				personInjure += "■第三者（伤 " + personInjureB + " 人 亡 0 人）";
			} else {
				personInjure += "■第三者（伤 " + personInjureB + " 人 亡 " + personDeathB + " 人）";
			}
		}
		if (personInjureD1 == 0) {
			if (personDeathD1 == 0) {
				personInjure += "□车上人员（伤 0 人 亡 0 人）&nbsp";
			} else {
				personInjure += "■车上人员（伤 0 人 亡 " + personDeathD1 + " 人）";
			}
		} else {
			if (personDeathD1 == 0) {
				personInjure += "■车上人员（伤 " + personInjureD1 + " 人 亡 0 人）";
			} else {
				personInjure += "■车上人员（伤 " + personInjureD1 + " 人 亡 " + personDeathD1 + " 人）";
			}
		}
	} %>
}
</script>
 