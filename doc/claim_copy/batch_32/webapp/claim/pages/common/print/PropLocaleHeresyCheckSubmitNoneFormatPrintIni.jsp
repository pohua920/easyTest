<%--
****************************************************************************
* DESC       ：财产险代查勘委托书打印初始化
* AUTHOR     ：zhulianyu
* CREATEDATE ：2005-11-15
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>

<%-- 引入bean类部分 --%>
<%@page import="java.text.*"%>
<%@page import="com.sinosoft.claim.ui.control.action.*"%>
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="javax.servlet.http.*"%>
<%@page import="com.sinosoft.claim.dto.domain.*"%> 
<%@page import="com.sinosoft.claim.util.*"%> 
<%@page import="com.sinosoft.sysframework.common.util.*"%> 
<%@page import="com.sinosoft.sysframework.common.datatype.DateTime"%> 
<%@page import="com.sinosoft.sysframework.exceptionlog.*"%> 

<%
 	//变量声明部分
 	String strClaimNo = ""; //赔案号
 	String strRegistNo = ""; //报案号
 	String strPolicyNo = ""; //保单号
 	String strTextType = "";
 	String strContext = ""; //赔款计算书文字
 	String strDamageStartDate = ""; //出险时间  
 	String ReportDate = ""; //报案日期 
 	String strDamageAddress = ""; //出险地点  
 	String strInsuredDate = ""; //保险期间

 	String strDate = ""; //保险期限
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
 	double dblSumLoss = 0; //标的损失金额
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
 	String[] subKindName = new String[6]; //附加险险别名称
 	String[] subKindAmount = new String[6]; //附加险保额
 	String strKindName = ""; //附加险别
 	String strFlag = ""; //主险附加险标志
 	String strKindAmount = ""; //附加险限额
 	String strUseNatureCode = ""; //使用性质
 	String strStartSiteName = ""; //运输起始地
 	String strViaSiteName = ""; //运输中转地
 	String strEndSiteName = ""; //运输终止地
 	String strCaseNo = ""; //赔案编号
 	String strSumAmount = ""; //保险金额
 	String strBLName = ""; //运输工具
 	String strBLNo = ""; //运具牌号
 	String strCarryBillNo = ""; //运单号码
 	String strSailStartDate = ""; //开航日期
 	String strLossName = ""; //受损标的(货物名称)
 	String strReportorName = ""; //报案人
 	String strMakeCom = ""; // 承保公司（出单机构） 
 	int intLossQuantity = 0; //受损标的数量 
 	String strLinkerName = ""; //联系人
 	String strPhoneNumber = ""; //联系电话

 	String comCName = ""; //代查勘公司中文名称
 	String addressCName = ""; //代查勘公司地址
 	String postCode = ""; //代查勘公司邮编
 	String phoneNumber = ""; //代查勘公司电话
 	String manager = ""; //代查勘公司经理
 	String faxNumber = ""; //代查勘公司传真

 	String handleUnitName = ""; //公估公司名称
 	//代码翻译变量
 	String strCode = "";
 	String strName = "";
 	boolean isChinese = true; //中文标志
 	//HttpServletRequest httpServletRequest ;
 	//对象定义部分
 	if (request.getSession().getAttribute("user") == null) {
 		out.println("登陆超时，请你重新登陆！");
 		return;
 	}
 	String strUserCode = ((UserDto) request.getSession().getAttribute("user")).getUserCode(); //获得当前user的ID
 	//System.out.println("<br>strUserCode=======================:"+strUserCode);
 	handleUnitName = request.getParameter("HandleUnitName");
 	String strUserName = ((UserDto) request.getSession().getAttribute("user")).getUserName();
 	String strComCode = ((UserDto) request.getSession().getAttribute("user")).getComCode();

 	PrpDcompanyDto prpDcompanyDto = null; //代查勘公司信息(打印此通知书user所在的公司)                                          
 	PrpCitemKindDto prpItemKindDto = null; //保单的ItemKindDto对象
 	PrpCmainDto prpCmainDto = null; //保单主表对象
 	PrpLchargeDto prpLchargeDto = null; //ChargeDto对象
 	PrpLctextDto prpLctextDto = null;
 	PrpLregistDto prpLregistDto = null;
 	PrpLextDto prpLextDto = null; //理赔扩展信息表
 	PrpCmainCargoDto prpCmainCargoDto = null; //货运险保单信息

 	int intItemCarCount = 0; //ItemcarDto对象的记录数
 	int intItemKindCount = 0; //ItemKindDto对象的记录数
 	int intChargeCount = 0; //ChargeDto对象的记录数
 	int intCtextCount = 0; //CtextDto对象的记录数
 	int intCtextCountTmp = 0; //textarea行数

 	int index = 0;
 	//得到RegistDto,PolicyDto对象
 	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");
 	RegistDto registDto = (RegistDto) request.getAttribute("registDto");
 	PrpLclaimDto prpLclaimDto = (PrpLclaimDto) request.getAttribute("prpLclaimDto");
 	UIPolicyAction uiPolicyAction = new UIPolicyAction();
 	UICodeAction uiCodeAction = new UICodeAction();
 	UICompanyAction uiCompanyAction = new UICompanyAction();
 	prpLregistDto = registDto.getPrpLregistDto(); //得到prpLregistDto对象
 	strRegistNo = prpLregistDto.getRegistNo(); //得到报案号
 	String strRiskName = uiCodeAction.translateRiskCode(prpLregistDto.getRiskCode(), true);

 	strPolicyNo = prpLregistDto.getPolicyNo(); //得到保单号
 	//policyDto                             = uiPolicyAction.findByPrimaryKey(strPolicyNo); //根据保单号获得保单对象
 	prpCmainDto = policyDto.getPrpCmainDto(); //得到保单主表对象

 	prpCmainCargoDto = policyDto.getPrpCmainCargoDto(); //得到prpCmainCargoDto对象
 	prpLextDto = registDto.getPrpLextDto(); //得到prpLextDto对象
 	DateTime dateTime = new DateTime();
 	prpDcompanyDto = uiCompanyAction.findByPrimaryKey(strComCode);
 	if (prpDcompanyDto != null) {
 		comCName = prpDcompanyDto.getComCName(); //代查勘公司中文名称
 		addressCName = prpDcompanyDto.getAddressCName(); //代查勘公司地址
 		postCode = prpDcompanyDto.getPostCode(); //代查勘公司邮编
 		phoneNumber = prpDcompanyDto.getPhoneNumber(); //代查勘公司电话
 		manager = prpDcompanyDto.getManager(); //代查勘公司经理（联系人）
 		faxNumber = prpDcompanyDto.getFaxNumber(); //代查勘公司传真
 	}
 	//String strDateTime = new DateTime(dateTime.current(),dateTime.YEAR_TO_HOUR);
 	//当前时间
 	String year = new DateTime(dateTime.current(), dateTime.YEAR_TO_YEAR).toString();
 	String month = new DateTime(dateTime.current(), dateTime.MONTH_TO_MONTH).toString();
 	String day = new DateTime(dateTime.current(), dateTime.DAY_TO_DAY).toString();

 	String MakeCom = prpCmainDto.getMakeCom();
 	//得到承保单位
 	//保单期限
 	strDate = prpCmainDto.getStartDate().getYear() + "年" + prpCmainDto.getStartDate().getMonth() + "月" + prpCmainDto.getStartDate().getDate() + "日" + (prpCmainDto.getStartHour() + "") + "时-" + prpCmainDto.getEndDate().getYear() + "年" + prpCmainDto.getEndDate().getMonth() + "月" + prpCmainDto.getEndDate().getDate() + "日" + (prpCmainDto.getEndHour() + "") + "时";

 	strMakeCom = uiCodeAction.translateComCode(MakeCom, true);
 	//得到保单号
 	strPolicyNo = prpCmainDto.getPolicyNo();
 	//得到保险金额 
 	strSumAmount = "" + new DecimalFormat("#,##0.00").format(prpCmainDto.getSumAmount());

 	//报案信息
 	if (registDto.getPrpLregistDto() != null) {
 		strLossName = prpLregistDto.getLossName();//受损标的
 		intLossQuantity = (int) prpLregistDto.getLossQuantity();//受损标的数量      
 		strReportorName = prpLregistDto.getReportorName();//报案人
 		strDamageAddress = prpLregistDto.getDamageAddress();//出险地点
 		strLinkerName = prpLregistDto.getLinkerName();//联系人
 		strPhoneNumber = prpLregistDto.getPhoneNumber();//联系电话
 	}
 	//货运险标的信息
 	if (policyDto.getPrpCmainCargoDto() != null) {
 		strStartSiteName = prpCmainCargoDto.getStartSiteName(); //运输起始地
 		strViaSiteName = prpCmainCargoDto.getViaSiteName(); //运输中转地
 		strEndSiteName = prpCmainCargoDto.getEndSiteName(); //运输终止地    
 		strBLName = prpCmainCargoDto.getBLName(); //运输工具 
 		strBLNo = prpCmainCargoDto.getBLNo(); //运具牌号  
 		strCarryBillNo = prpCmainCargoDto.getCarryBillNo(); //运单号

 	}
 	//理赔扩展信息
 	if (registDto.getPrpLextDto() != null) {
 		strSailStartDate = prpLextDto.getSailStartDate().getYear() + " 年 " + prpLextDto.getSailStartDate().getMonth() + " 月 " + prpLextDto.getSailStartDate().getDate() + " 日 ";
 	}
 	strClaimNo = prpLclaimDto.getClaimNo();
 	//System.out.println("zhulianyu");
 %>

<script language="javascript"> 
function loadForm() {
	tdPolicyNo.innerHTML = '<%=strPolicyNo%>';
	tdInsuredName.innerHTML = '<%=StringConvert.encode(prpCmainDto.getInsuredName())%>';
	//tdCarryBillNo.innerHTML   = '<%=strCarryBillNo%>';
	tdSumAmount.innerHTML = '<%=prpCmainDto.getCurrency()%>  ' + '<%=strSumAmount%>';
	tdLinkerName.innerHTML = '<%=strLinkerName%>';
	//tdBLName.innerHTML        = '<%=StringConvert.encode(strBLName)%>';
	//tdBLNo.innerHTML          = '<%=StringConvert.encode(strBLNo)%>';
	//tdEndSiteName.innerHTML   = '<%=StringConvert.encode(strEndSiteName)%>';
	//tdSailStartDate.innerHTML = '<%=strSailStartDate%>';
	//tdLossName.innerHTML      = '<%=StringConvert.encode(strLossName)%>';
	//tdLossQuantity.innerHTML  = '<%=intLossQuantity%>';
	tdDate.innerHTML = '<%=strDate%>';
	tdPhoneNumber.innerHTML = '<%=strPhoneNumber%>';

	<% strDamageStartDate = prpLregistDto.getDamageStartDate().getYear() + " 年 " + prpLregistDto.getDamageStartDate().getMonth() + " 月 " + prpLregistDto.getDamageStartDate().getDate() + " 日";
	ReportDate = prpLregistDto.getReportDate().getYear() + " 年 " + prpLregistDto.getReportDate().getMonth() + " 月 " + prpLregistDto.getReportDate().getDate() + " 日 " + (prpLregistDto.getReportHour() + "").substring(0, 2) + " 时 ";
	String damangeAddressType = uiCodeAction.translateCodeCode("DamageAddress", prpLregistDto.getDamageAddressType(), true); %>
		tdDamageStartDate.innerHTML = '<%=strDamageStartDate%>';
	//tdDamageName.innerHTML      = '<%=StringConvert.encode(prpLregistDto.getDamageName())%>';    
	tdDamageAddress.innerHTML = '<%=StringConvert.encode(prpLregistDto.getDamageAddress())%>';
	//tdDamageAddressType.innerHTML   = '<%=damangeAddressType%>';
	//tdIndemnityDuty.innerHTML     = '<%=strName%>';
	//tdInsuredDate.innerHTML = '<%=strInsuredDate%>';           
	<% //*****标的子险信息PrpTitemKind*****
	dblSumAmount1 = 0;
	dblSumAmount2 = 0;

	if (policyDto.getPrpCitemKindDtoList() != null) {
		int tempindex = 0; //定义临时变量
		//得到blPrpCitemKind对象的记录数
		intItemKindCount = policyDto.getPrpCitemKindDtoList().size();
		for (index = 0; index < intItemKindCount; index++) {
			prpItemKindDto = (PrpCitemKindDto) policyDto.getPrpCitemKindDtoList().get(index);
			//add begin by zhuly 20051103 reason:增加子险信息
			strRiskCode = prpItemKindDto.getRiskCode();
			strKindCode = prpItemKindDto.getKindCode();
			strKindName = prpItemKindDto.getKindName();
			strKindAmount = "" + prpItemKindDto.getAmount();
			strFlag = prpItemKindDto.getFlag();
		}
	} %>
	//tdSumAmount1.innerHTML = '<%=new DecimalFormat("#,##0.00").format(policyDto.getPrpCmainDto().getSumAmount())%>';
	//tdSumAmount2.innerHTML = '<%=new DecimalFormat("#,##0.00").format(dblSumAmount2)%>';       
}
</script>
  