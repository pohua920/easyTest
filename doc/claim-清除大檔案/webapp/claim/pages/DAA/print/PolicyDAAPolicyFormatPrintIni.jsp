<%--
****************************************************************************
* DESC       ：车险保单列印（套印）初始化
* AUTHOR     ：理赔组
* CREATEDATE ：2004-11-23
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>

<%-- 引入bean类部分 --%>
<%@page import="java.text.*"%>
<%@page import="com.sinosoft.claim.ui.control.action.*"%>
<%@page import="com.sinosoft.claim.ui.control.viewHelper.*"%>
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="com.sinosoft.claim.dto.domain.*"%> 
<%@page import="com.sinosoft.claim.util.*"%> 
<%@page import="com.sinosoft.sysframework.common.util.*"%> 
<%@page import="com.sinosoft.sysframework.common.datatype.DateTime"%> 
<%@page import="com.sinosoft.sysframework.exceptionlog.*"%> 
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Vector"%>  

<%
  	//变量声明部分
  	String strPolicyNo = ""; //取保单号
  	String strEditType = ""; //列印方式 
  	String strBigTitle = ""; //列印方式描述
  	String strCarType = ""; //是单车还是多车
  	String strContractNo = ""; //合同号
  	String strInsuredName = ""; //被保险人姓名
  	String strStartYear = ""; //起保年
  	String strStartMonth = ""; //起保月
  	String strStartDay = ""; //起保日
  	String strStartDate = ""; //起保日期——车队使用
  	boolean isStartDate = true; //车队中的起保日期是否一致
  	String strEndYear = ""; //终保年
  	String strEndMonth = ""; //终保月
  	String strEndDay = ""; //终保日
  	String strCSumPremium = ""; //大写总保费
  	String strSumPremium = ""; //总保费
  	String strArgueSolution = ""; //争议解决方式
  	String strComCode = ""; //业务归属机构代码 
  	String strComCName = ""; //业务归属机构中文名称
  	String strAddressCName = ""; //业务归属机构地址
  	String strPostCode = ""; //业务归属机构邮政编码
  	String strPhoneNumber = ""; //业务归属机构电话号码
  	String strUnderwriteName = ""; //核保人
  	String strOperatorCode = ""; //制单人
  	String strHandlerCode = ""; //经办人                       
  	String strOperateDate = ""; //制单日期                       

  	String strLicenseNo = ""; //号牌号码
  	String strBrandName = ""; //厂牌型号
  	String strEngineNo = ""; //发动机号
  	String strVINNo = ""; //VIN号
  	String strFrameNo = ""; //车架号
  	String strCarKind = ""; //车辆种类
  	String strSeatCount = ""; //核定载客
  	String strTonCount = ""; //核定载质量
  	String strExhaustScale = ""; //排量/功率
  	String strUseYears = ""; //已使用年限
  	String strEnrollDate = ""; //初次登记时间
  	String strRunMiles = ""; //已行驶里程数
  	String strUseNature = ""; //使用性质
  	String strParkSite = ""; //固定停放地点
  	String strRunAreaName = ""; //行驶区域
  	String strPurchasePrice = ""; //新车购置价
  	String strSafeDevice = ""; //安全配置
  	double dblTotalProfit = 0; //优惠金额
  	double dblSumTotalProfit = 0; //总优惠金额
  	String strStartHour = ""; //起保时
  	String strEndHour = ""; //终保时

  	String strClauseCode = ""; //得到特别约定代码：T0001
  	String strClauses = ""; //得到特别约定内容
  	String strFlag = ""; //判断是特别约定的名称还是内容，Flag[2]=0:名称，Flag[2]=0:内容
  	String strCountryNature = ""; //判断是国产玻璃还是进口玻璃

  	int intPolicyCount = 0; //合同号下的保单数目
  	double dblHSumPremium = 0; //合同号下的总保费

  	//代码翻译变量
  	String strCode = "";
  	String strName = "";
  	boolean isChinese = true; //中文标志

  	//对象定义部分
  	Vector vecPoliEndor = new Vector();

  	PrpCmainDto prpMainDto = null; //保单/投保单的MainDto对象
  	PrpCitemCarDto prpItemcarDto = null; //保单/投保单的ItemcarDto对象
  	PrpCitemKindDto prpItemKindDto = null; //保单/投保单的ItemKindDto对象
  	PrpCprofitDto prpProfitDto = null; //保单/投保单的ProfitDto对象
  	PrpCengageDto prpEngageDto = null; //保单/投保单的EngageDto对象
  	PrpCfeeDto prpFeeDto = null; //保单/投保单的fee对象

  	int intItemKindCount = 0; //ItemKindDto对象的记录数
  	int intProfitCount = 0; //ProfitDto对象的记录数
  	int intEngageCount = 0; //EngageDto对象的记录数

  	int index = 0;
  	int index1 = 0;
  %>

<%
	strPolicyNo = request.getParameter("PolicyNo"); //取保单号   
	strCarType = request.getParameter("CarType"); //是单车还是多车
	strEditType = request.getParameter("EDITTYPE");

	//得到PolicyDto,ClaimDto,RegistDto对象
	EndorseDto endorseDto = (EndorseDto) request.getAttribute("endorseDto");
	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");

	//如果页面输入的是报案号而不是保单号，request是取不到的------------
	PrpCmainDto prpCmainDto = policyDto.getPrpCmainDto();
	strPolicyNo = prpCmainDto.getPolicyNo();
	//--------------------------------------------------------

	UICodeAction uiCodeAction = new UICodeAction();

	prpMainDto = policyDto.getPrpCmainDto();
	PolicyDto policyDtoOld = new PolicyDto();
	//增加列印类型,区分列印出险保单抄件
	String PrintType = request.getParameter("PrintType");
	if (PrintType == null) {
		PrintType = "C";
	}
	if (strCarType == null) {
		strCarType = "SingleCar";
	}
	//
	if (strEditType.equals("COPY")) {
		strBigTitle = "（抄件）";
	}

	if (strCarType.equals("SingleCar")) //单车
	{
		if (PrintType.equals("L")) {
			//System.out.println("--------1-----------jsp----L--");    
			//blPolicy = new BLPolicy();
			policyDtoOld = (PolicyDto) request.getAttribute("policyDto");
			//System.out.println("--------2-----------jsp----L--");    
		}
		if (PrintType.equals("O")) {
			//System.out.println("--------1-----------jsp----O--");  
			/*
			blEndorse = new BLEndorse();
			vecPoliEndor = blEndorse.findForEndorBefore(strPolicyNo,prpMainDto.getStartDate());
			blPolicy = (BLPolicy)vecPoliEndor.elementAt(0);
			 */
			EndorseViewHelper endorseViewHelper = new EndorseViewHelper();
			policyDtoOld = endorseViewHelper.findForEndorBefore(strPolicyNo, prpMainDto.getStartDate().toString());
			//System.out.println("-------2------------jsp----O--");         
		}
		if (PrintType.equals("C")) {
			//blPolicy = new BLPolicy();
			//blPolicy.getData(strPolicyNo);
		}
		//得到MainDto对象
		prpMainDto = policyDtoOld.getPrpCmainDto();

		//得到ItemcarDto对象
		if (policyDtoOld.getPrpCitemCarDtoList() != null) {
			prpItemcarDto = (PrpCitemCarDto) policyDtoOld.getPrpCitemCarDtoList().get(0);
		}
		//System.out.println("-------3------------jsp----O--");         
		//prpItemcarDto = blPolicy.getBLPrpCitemCar().getArr(0);

		//得到ItemKindDto对象的记录数
		ArrayList itemKindDtoList = new ArrayList();
		if (policyDtoOld.getPrpCitemKindDtoList() != null) {
			itemKindDtoList = policyDtoOld.getPrpCitemKindDtoList();
			intItemKindCount = policyDtoOld.getPrpCitemKindDtoList().size();
		}
		EndorseViewHelper endorseViewHelper = new EndorseViewHelper();
		policyDtoOld.setPrpCitemKindDtoList(endorseViewHelper.TransKind(itemKindDtoList, prpItemcarDto.getClauseType()));
		//blPrpCitemKind    = blPolicy.getBLPrpCitemKind();
		//blPrpCitemKind    = new UIItemKindPrint().get(blPrpCitemKind,prpItemcarDto.getClauseType());
		//intItemKindCount  = blPrpCitemKind.getSize(); 

		//得到ProfitDto对象的记录数
		if (policyDtoOld.getPrpCprofitDtoList() != null) {
			intProfitCount = policyDtoOld.getPrpCprofitDtoList().size();
		}

		//得到EngageDto对象的记录数
		if (policyDtoOld.getPrpCengageDtoList() != null) {
			intEngageCount = policyDtoOld.getPrpCengageDtoList().size();
		}

		//reason:保费合计从cfee表中取
		//得到Fee对象
		if (policyDtoOld.getPrpCfeeDtoList() != null) {

			prpFeeDto = (PrpCfeeDto) policyDtoOld.getPrpCfeeDtoList().get(0);
		}
		//System.out.println("-------4------------jsp----O--");         
		//*****保单基本信息表PrpCmain*****
		strContractNo = StringConvert.encode(prpMainDto.getContractNo()); //车队合同号

		//保费合计
		strCSumPremium = MoneyUtils.toChinese(prpMainDto.getSumPremium(), prpMainDto.getCurrency());

		//reason:保费合计从cfee表中取  
		//modify bu lixiaohua 保费合计从cmain表中取 begin 20040220
		strSumPremium = new DecimalFormat("#,##0.00").format(prpMainDto.getSumPremium());

		//争议解决方式
		strCode = "";
		strName = "";
		strCode = StringConvert.encode(prpMainDto.getArgueSolution());
		if (strCode.equals("1")) //诉讼
		{
			strName = "诉讼";
		} else if (strCode.equals("2")) //仲裁委员会裁决
		{
			strName = "提交" + StringConvert.encode(prpMainDto.getArbitBoardName()) + "处理。";
		}
		strArgueSolution = strName;

		//*****机动车险标的信息表PrpCitemCar*****
		//在UIPolicyDAASingleCarPolicyFormatPrint.jsp中判断是国产玻璃还是进口玻璃
		strCountryNature = StringConvert.encode(prpItemcarDto.getCountryNature());

		strLicenseNo = StringConvert.encode(prpItemcarDto.getLicenseNo()); //号牌号码
		strBrandName = StringConvert.encode(prpItemcarDto.getBrandName()); //厂牌型号
		strEngineNo = StringConvert.encode(prpItemcarDto.getEngineNo()); //发动机号
		strVINNo = StringConvert.encode(prpItemcarDto.getVINNo()); //VIN码
		strFrameNo = StringConvert.encode(prpItemcarDto.getFrameNo()); //车架号

		//车辆种类
		strCode = "";
		strName = "";
		strCode = StringConvert.encode(prpItemcarDto.getCarKindCode());
		strName = uiCodeAction.translateCodeCode("CarKind", strCode, isChinese);
		strCarKind = strName;

		strSeatCount = "" + prpItemcarDto.getSeatCount(); //核定载客
		strTonCount = "" + new DecimalFormat("#,##0.00").format(prpItemcarDto.getTonCount()); //核定载质量
		strExhaustScale = "" + new DecimalFormat("0.0000").format(prpItemcarDto.getExhaustScale()); //排量/功率
		strUseYears = "" + prpItemcarDto.getUseYears(); //已使用年限

		//初次登记日期  
		DateTime dt = new DateTime();
		dt = prpItemcarDto.getEnrollDate();
		//strEnrollDate   = prpItemcarDto.getEnrollDate();
		if (strEnrollDate != null) {
			strEnrollDate = "" + dt.getYear() + "年" + dt.getMonth() + "月";
		}
		//大地要求将此信息注掉
		//strRunMiles      = "" + new DecimalFormat("#,##0.00").format(prpItemcarDto.getRunMiles());       //行驶里程 

		//使用性质
		strCode = "";
		strName = "";
		strCode = StringConvert.encode(prpItemcarDto.getUseNatureCode());
		if (strCode != null && strCode.equals("84"))
			strCode = "83";
		strName = uiCodeAction.translateCodeCode("UseNature", strCode, isChinese);
		strUseNature = strName;

		//安全配置(1:防盗装置，2:ABS，3:安全气囊)
		strCode = "";
		strName = "";
		strSafeDevice = StringConvert.encode(prpItemcarDto.getSafeDevice()); //得到的值的格式为："1，2，3"
		int i = 0;
		for (i = 0; i < strSafeDevice.length(); i++) {
			String temp = strSafeDevice.substring(i, i + 1);
			if (temp.equals("1") || temp.equals("2") || temp.equals("3")) {
				if (i == (strSafeDevice.length() - 1)) {
					strCode = temp;
					strName = strName + uiCodeAction.translateCodeCode("SafeDevice", strCode, isChinese) + " ";
				} else {
					strCode = temp;
					strName = strName + uiCodeAction.translateCodeCode("SafeDevice", strCode, isChinese) + "；";
				}
			}
		}
		strSafeDevice = strName;

		//固定停放地点
		strCode = "";
		strName = "";
		strCode = StringConvert.encode(prpItemcarDto.getParkSite());
		strName = uiCodeAction.translateCodeCode("ParkSite", strCode, isChinese);
		strParkSite = strName;

		//行驶区域
		strCode = "";
		strName = "";
		strCode = StringConvert.encode(prpItemcarDto.getRunAreaCode());
		if (!strCode.equals("")) {
			strName = uiCodeAction.translateCodeCode("RunArea", strCode, true);

		} else {
			strName = "中华人民共和国境内(不含港澳台)";
		}
		strRunAreaName = strName;

		strPurchasePrice = new DecimalFormat("#,##0.00").format(prpItemcarDto.getPurchasePrice()); //新车购置价

		//*****标的子险信息表PrpCitemKind*****
		//在UIPolicyDAASingleCarPolicyFormatPrint.jsp中实现了

		//*****特别约定表PrpCengage*****
		strClauses = "";

		//先看有没有新增设备的特别约定
		int intLimitLine = 5; //在textarea中限制显示的行数
		if ((!strCarType.equals("MultiCar")) && (prpItemcarDto.getNewDeviceFlag().equals("1"))) {
			intLimitLine = 4;
			strClauses += "承保新增设备，详见新增设备明细表\\r\\n";
		}
		if (policyDtoOld.getPrpCengageDtoList() != null) {
			for (index = 0; index < intEngageCount; index++) {
				prpEngageDto = (PrpCengageDto) policyDtoOld.getPrpCengageDtoList().get(index);
				strClauseCode = prpEngageDto.getClauseCode();
				String strTitleFlag = prpEngageDto.getTitleFlag();
				if (strTitleFlag.trim().equals("1")) {
					strClauses += prpEngageDto.getClauses() + "\\r\\n";
				}
			}
		}

		//检查特别约定打出来是几行
		int intEngageCountTmp1 = 1; //textarea的行数，之所以intEngageCountTmp的初始植为1：是为标题留出1行
		int intEngageCountTmp2 = 1; //textarea的行数，之所以intEngageCountTmp的初始植为1：是为标题留出1行
		String strClausesTmp = ""; //带"更多内容，请详见清单..."的文字

		for (index = 0; index < strClauses.length(); index++) {
			if (strClauses.substring(index, index + 1).equals("\\")) {
				if (!(strClauses.substring(index).length() < 4)) {
					if (strClauses.substring(index, index + 4).equals("\\r\\n")) {
						intEngageCountTmp1 += 1; //只要有回车换行，intEngageCountTmp1+1
						intEngageCountTmp2 += 1; //只要有回车换行，intEngageCountTmp2+1
						if (intEngageCountTmp2 == intLimitLine) {
							strClausesTmp = strClauses.substring(0, index + 4) //intLimitLine行的strClauses + "更多内容，请详见清单..."
									+ "更多内容，请详见清单...";
						}
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
				intEngageCountTmp1 += 1;
			}
		} else
			//如果strClauses不足1行，intEngageCountTmp1 = 1; 
			intEngageCountTmp1 = 1;

		//如果intEngageCountTmp1>intLimitLine+1，则strClauses = intLimitLine行的strClauses + "更多内容，请详见清单..."
		if (intEngageCountTmp1 > intLimitLine + 1) {
			strClauses = strClausesTmp;
		}

		//由於是直接放在HTML中，所以需要使用StringConvert.decode()将文字中的"\\r\\n"转化为"\r\n"
		strClauses = StringConvert.decode(strClauses);
	}

	else if (strCarType.equals("MultiCar")) //多车
	{
		/*
		  BLPrpCmain  blPrpCmain = null;   
		  strContractNo = request.getParameter("PolicyNo");   //取合同号
		  
		  //多车保单"保单号"列印合同号 
		  strPolicyNo   = strContractNo;
		  
		  blPrpCmain = new BLPrpCmain();
		  
		  //得到第一张保单基本信息对象
		  blPrpCmain.query("rowid = (SELECT MIN(rowid) FROM PrpCmain WHERE contractno = '"+ strContractNo+"')");
		  prpMainDto = blPrpCmain.getArr(0);
		  strStartDate = prpMainDto.getStartDate().trim();  ////得到第一张保单的起保日期
		  
		  blPrpCmain.query("contractno='" + strContractNo + "'");
		  intPolicyCount = blPrpCmain.getSize();
		  for(index=0;index<intPolicyCount;index++)
		  {
		    dblHSumPremium += Double.parseDouble(StringConvert.chgStrZero(blPrpCmain.getArr(index).getSumPremium()));
		    //判断车队中的起保日期是否一致
		    if(!blPrpCmain.getArr(index).getStartDate().trim().equals(strStartDate))
		      isStartDate = false;  
		  }
		  //特别约定
		  strClauses = "总共投保车辆数量为" + intPolicyCount + "辆，总保费" + new DecimalFormat("#,##0.00").format(dblHSumPremium) + "元。";
		 */
	}

	//*****公共部分****//
	//被保险人名称
	strInsuredName = StringConvert.encode(prpMainDto.getInsuredName());

	//保险期间——如果是单车列印或是车队列印，但起保日期一致则列印保险期间
	if (!strCarType.equals("MultiCar") || (strCarType.equals("MultiCar") && isStartDate == true)) {
		strStartYear = "" + prpMainDto.getStartDate().getYear();
		strStartMonth = "" + prpMainDto.getStartDate().getMonth();
		strStartDay = "" + prpMainDto.getStartDate().getDate();
		strEndYear = "" + prpMainDto.getEndDate().getYear();
		strEndMonth = "" + prpMainDto.getEndDate().getMonth();
		strEndDay = "" + prpMainDto.getEndDate().getDate();
		strStartHour = "" + prpMainDto.getStartHour(); //起保时
		strEndHour = "" + prpMainDto.getEndHour(); //终保时

	}

	//出单机构信息
	strComCode = StringConvert.encode(prpMainDto.getComCode());
	//prpDcompanyDto.getInfo(strComCode);
	UICompanyAction uiCompanyAction = new UICompanyAction();
	PrpDcompanyDto prpDcompanyDto = uiCompanyAction.findByPrimaryKey(strComCode);
	strComCName = prpDcompanyDto.getComCName();
	strAddressCName = prpDcompanyDto.getAddressCName();
	strPostCode = prpDcompanyDto.getPostCode();
	strPhoneNumber = prpDcompanyDto.getPhoneNumber();

	//签单日期
	strOperateDate = "" + prpMainDto.getOperateDate().getYear() + "年" + prpMainDto.getOperateDate().getMonth() + "月" + prpMainDto.getOperateDate().getDate() + "日";

	strUnderwriteName = StringConvert.encode(prpMainDto.getUnderWriteName()); //核保人

	//制单人
	strCode = "";
	strName = "";
	strCode = StringConvert.encode(prpMainDto.getOperatorCode());
	strName = uiCodeAction.translateUserCode(strCode, isChinese);
	strOperatorCode = strName;

	//经办人
	strCode = "";
	strName = "";
	strCode = StringConvert.encode(prpMainDto.getHandlerCode());
	strName = uiCodeAction.translateUserCode(strCode, isChinese);
	strHandlerCode = strName;

	//法定三者列印投保单确认码
	//确认主表参数                        
	String strValidNo = "";

	//其他变量
	/*
	//BLPrpExtraBValid blPrpExtraBValid           = new BLPrpExtraBValid();
	PrpExtraBValidDto  prpExtraBValidDto  = null;
	String isql = "";
	String isql_count = "";
	int    countValid = 0;
	 
	isql = "SELECT * FROM PrpExtraBValid WHERE PolicyNo='" + strPolicyNo + "'";
	isql_count = "SELECT COUNT(*) FROM PrpExtraBValid WHERE PolicyNo='" + strPolicyNo + "'";
	 
	countValid = blPrpExtraBValid.getCount(isql_count);

	if( countValid == 0)
	{
	  strValidNo       = ""; 
	}
	else
	{
	  blPrpExtraBValid.query(isql);
	  prpExtraBValidDto = blPrpExtraBValid.getArr(0);
	  strValidNo    = prpExtraBValidDto.getValidNo();
	  strValidNo    = "投保单确认码为：" + strValidNo;
	}
	 */
	//modify by lirj update end 040326
	//System.out.println("-------5------------jsp----O--");
%> 