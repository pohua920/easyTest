<%--
****************************************************************************
* DESC       ：货运险查勘报告清单打印初始化
* AUTHOR     ：wangwei
* CREATEDATE ：2005-5-22
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
<%@page import="com.sinosoft.claim.bl.facade.*"%>
<%@page import="com.sinosoft.utiall.dbsvr.DBPrpDcode"%>
<%@page import="com.sinosoft.claim.resource.dtofactory.domain.*"%>

<%
	String strPolicyNo = ""; //保单号
	String strRegistNo = ""; //报案号
	String strEndorseNo = ""; //批单号
	UIEndorseAction uiEndorseAction = new UIEndorseAction();
	UIPrpLcheckExtAction uiPrpLcheckExtAction = new UIPrpLcheckExtAction();

	PrpLregistDto prpLregistDto = (PrpLregistDto) request.getAttribute("prpLregistDto");
	PrpPheadDto prpPheadDto = null;
	PrpLcheckDto prpLcheckDto = (PrpLcheckDto) request.getAttribute("prpLcheckDto");
	PrpDcompanyDto prpDcompanyDto = (PrpDcompanyDto) request.getAttribute("prpDcompanyDto");
	PrpLextDto prpLextDto = (PrpLextDto) request.getAttribute("PrpLextDto");
	PrpCmainDto prpCmainDto = (PrpCmainDto) request.getAttribute("prpCmainDto");
	PrpCmainCargoDto prpCmainCargoDto = (PrpCmainCargoDto) request.getAttribute("prpCmainCargoDto");
	strPolicyNo = prpLregistDto.getPolicyNo();
	strRegistNo = prpLregistDto.getRegistNo();

	UICheckAction uiCheckAction = new UICheckAction();
	CheckDto checkDto = uiCheckAction.findByPrimaryKey(strRegistNo);

	prpLextDto = checkDto.getPrpLextDto();
	/*
	 out.println("<br>prpLextDto=======================:"+prpLextDto);
	 out.println("<br>strRegistNo=======================:"+strRegistNo);
	 out.println("<br>prpLregistDto=======================:"+prpLregistDto);
	 out.println("<br>prpLcheckDto=======================:"+prpLcheckDto);
	 out.println("<br>prpDcompanyDto=======================:"+prpDcompanyDto);
	 out.println("<br>prpLextDto=======================:"+prpLextDto);
	 out.println("<br>prpCmainDto=======================:"+prpCmainDto);
	 out.println("<br>prpCmainCargoDto=======================:"+prpCmainCargoDto);
	 */
	Collection prpPheadDtoList = uiEndorseAction.findByPrpPheadConditions(" policyNo='" + strPolicyNo + "'");
	if (prpPheadDtoList != null) {
		Iterator iterator = prpPheadDtoList.iterator();
		while (iterator.hasNext()) {
			prpPheadDto = (PrpPheadDto) iterator.next();
			strEndorseNo = prpPheadDto.getEndorseNo();
		}
	}
	Collection list = (ArrayList) request.getAttribute("list");
	//      Iterator it = list.iterator();

	//    String strItemDetailName = "";   //货物名称
	//      while(it.hasNext())
	//			{

	//		    PrpCitemKindDto prpCitemKindDto=(PrpCitemKindDto)it.next();
	//        if(strItemDetailName.indexOf(prpCitemKindDto.getItemDetailName())<0){
	//           strItemDetailName = strItemDetailName + prpCitemKindDto.getItemDetailName() +","+ prpCitemKindDto.getQuantity();
	//          }    
	//      }

	//        Iterator itt=list.iterator();
	double dbDeductible = 0;
	//      	while(itt.hasNext())
	//				{
	//		      PrpCitemKindDto prpCitemKindDto=(PrpCitemKindDto)itt.next();
	//			    dbDeductible =(double)prpCitemKindDto.getDeductible();
	//			    break;
	//			  }

	//prpLregistDto
	String strDamageStartDate = ""; //出险日期
	String strDamageName = ""; //出险原因
	String strEstiCurrency = ""; //估损金额币别
	String strEstimateLoss = ""; //估损金额
	DateTime dateDamageStartDate = null; //出险日期
	DateTime dateReportDate = null; //报案日期
	String strReportDate = ""; //报案日期
	String strLinkerName = ""; //联系人
	String strPhoneNumber = ""; //联系方式
	if (prpLregistDto != null) {
		strDamageStartDate = prpLregistDto.getDamageStartDate().toString();
		strDamageName = prpLregistDto.getDamageName();
		strEstiCurrency = prpLregistDto.getEstiCurrency();
		strLinkerName = prpLregistDto.getLinkerName();
		strPhoneNumber = prpLregistDto.getPhoneNumber();
		strEstimateLoss = new DecimalFormat("0.00").format(prpLregistDto.getEstimateLoss());
		dateReportDate = prpLregistDto.getReportDate();
		strReportDate = dateReportDate.getYear() + "年" + dateReportDate.getMonth() + "月" + dateReportDate.getDate() + "日";
		dateDamageStartDate = prpLregistDto.getDamageStartDate();
		strDamageStartDate = dateDamageStartDate.getYear() + "年" + dateDamageStartDate.getMonth() + "月" + dateDamageStartDate.getDate() + "日" + prpLregistDto.getDamageStartHour().substring(0, 2) + "时";

	}

	//prpLcheckDto
	DateTime dateCheckDate = null; //
	String strCheckDate = ""; //时间
	String strCheckSite = ""; //出险地点
	String CheckSite = ""; //查勘地点	
	String CheckDate = ""; //查勘日期
	String strChecker1 = ""; //查勘人
	String strCheckUnitName = ""; //参加查勘的各方
	String strHandleUnit = ""; //参加查勘的各方
	String ClaimNo = ""; //赔案号 
	String Conveyance = ""; //运输方式
	if (prpLcheckDto != null) {
		strCheckDate = prpLcheckDto.getCheckDate().toString();
		dateCheckDate = prpLcheckDto.getCheckDate();
		strCheckDate = dateCheckDate.getYear() + "年" + dateCheckDate.getMonth() + "月" + dateCheckDate.getDate() + "日";

		strCheckSite = prpLcheckDto.getCheckSite();
		CheckSite = prpLcheckDto.getCheckSite();
		CheckDate = dateCheckDate.getYear() + "年" + dateCheckDate.getMonth() + "月" + dateCheckDate.getDate() + "日";
		strChecker1 = prpLcheckDto.getChecker1();
		strCheckUnitName = prpLcheckDto.getCheckUnitName();
		strHandleUnit = prpLcheckDto.getHandleUnit();
		ClaimNo = prpLcheckDto.getClaimNo();
	}

	//prpLextDto
	String strAppliCheckDate = ""; //申请查勘日期
	String strAppliPhone = ""; //投保人或代表联系电话
	String strInsuredPhone = ""; //被保险人或代表联系电话
	String strCargoLossCheckCom = ""; //货损查勘公司
	String strUnloadDate = ""; //卸货日期
	int strRestQuantity = 0; //残值数量
	double strCargoValue = 0.0; //货价
	String strGuarantor = ""; //担保人
	String strRemark = ""; //备注,其他说明事项
	String strSailStartDate = ""; //起运日期
	String strLoadingNo = ""; //提单/运单
	String strInvoiceNo = ""; //发票号码NO
	String strValue1 = ""; //货物名称及数量
	String strValue2 = ""; //查勘估损金额

	if (prpLextDto != null) {
		strAppliCheckDate = prpLextDto.getAppliCheckDate().toString();
		strAppliPhone = prpLextDto.getAppliPhone();
		strInsuredPhone = prpLextDto.getInsuredPhone();
		strCargoLossCheckCom = prpLextDto.getCargoLossCheckCom();
		strUnloadDate = prpLextDto.getUnloadDate().toString();
		strRestQuantity = prpLextDto.getRestQuantity();
		strCargoValue = prpLextDto.getCargoValue();
		strGuarantor = prpLextDto.getGuarantor();
		strRemark = prpLextDto.getRemark();

		DateTime dateSailStartDate = prpLextDto.getSailStartDate(); //起运日期
		strSailStartDate = dateSailStartDate.getYear() + "年" + dateSailStartDate.getMonth() + "月" + dateSailStartDate.getDate() + "日";
		strCargoLossCheckCom = prpLextDto.getCargoLossCheckCom();
		strLoadingNo = prpLextDto.getRemark();
		strInvoiceNo = prpLextDto.getValue3();
		strValue1 = prpLextDto.getValue1();
		strValue2 = prpLextDto.getValue2();
	}

	//prpCmainDto
	UICompanyAction uiCompanyAction = new UICompanyAction();
	String strSumAmount = "0.00"; //保额
	String strSumValue = "0.00"; //货价
	String strAppliName = ""; //投保人名称
	String strInsuredName = ""; //被保险人名称
	String strComCode = ""; //承保公司代码
	if (prpCmainDto != null) {
		strSumAmount = new DecimalFormat("0.00").format(prpCmainDto.getSumAmount());
		strSumValue = new DecimalFormat("0.00").format(prpCmainDto.getSumValue());
		strAppliName = prpCmainDto.getAppliName();
		strInsuredName = prpCmainDto.getInsuredName();
		strComCode = prpCmainDto.getComCode();
		prpDcompanyDto = uiCompanyAction.findByPrimaryKey(strComCode);//根据承保公司代码得到公司对象
	}

	//prpDcompanyDto
	String strComCName = ""; //承保公司
	if (prpDcompanyDto != null) {
		strComCName = prpDcompanyDto.getComCName();
	}

	//prpCmainCargoDto
	String strLadingNo = ""; //提单/运单
	String strCarryBillNo = ""; //货票号码
	String strConveyance = ""; //运输方式
	String strVoyageNo = ""; //车次/航次/车号
	String strTPath = ""; //运输路线
	String strStartSiteName = ""; //运输路线起点
	String strViaSiteName = ""; //运输路线经过
	String strEndSiteName = ""; //运输路线终点

	String strInvoiceCurrency = ""; //发票金额币别
	String strInvoiceAmount = ""; //发票金额
	String strPlusRate = ""; //加成比例
	String strCreditNo = ""; //信用证号
	String strShipNoteNo = ""; //起运通知书编号
	String strBargainNo = ""; //合同号
	String strTransferConveyance = ""; //转运工具
	String strBLNo = ""; //运具牌号
	String strTonCount = ""; //吨位数
	String strStartSiteCode = ""; //起始地编码
	String strViaSiteCode = ""; //中转地编码
	String strReshipSiteName = ""; // 转载地名称
	String strEndDetailName = ""; //具体终止地名称
	String strClaimSite = ""; //赔款偿付地点
	String strTransferBank = ""; //过户银行
	String strBLName = ""; //运输工具名称

	if (prpCmainCargoDto != null) {
		strLadingNo = prpCmainCargoDto.getLadingNo();
		strCarryBillNo = prpCmainCargoDto.getCarryBillNo();
		strConveyance = prpCmainCargoDto.getConveyance();
		if (strConveyance != null && !strConveyance.equals("")) {
			DBPrpDcode dbPrpDcode = new DBPrpDcode();
			dbPrpDcode.getInfo("ConveyanceType", strConveyance);
			strConveyance = dbPrpDcode.getCodeCName();
		}
		strVoyageNo = prpCmainCargoDto.getVoyageNo();
		strStartSiteName = prpCmainCargoDto.getStartSiteName();
		if (prpCmainCargoDto.getViaSiteName() != null) {
			strViaSiteName = prpCmainCargoDto.getViaSiteName();
		}
		strEndSiteName = prpCmainCargoDto.getEndSiteName();

		strInvoiceCurrency = prpCmainCargoDto.getInvoiceCurrency();
		strInvoiceAmount = prpCmainCargoDto.getInvoiceAmount() + "";
		strPlusRate = prpCmainCargoDto.getPlusRate() + "";
		strCreditNo = prpCmainCargoDto.getCreditNo();
		strShipNoteNo = prpCmainCargoDto.getShipNoteNo();
		strBargainNo = prpCmainCargoDto.getBargainNo();
		strTransferConveyance = prpCmainCargoDto.getTransferConveyance();
		strBLNo = prpCmainCargoDto.getBLNo();
		strTonCount = prpCmainCargoDto.getTonCount() + "";
		strStartSiteCode = prpCmainCargoDto.getStartSiteCode();
		strStartSiteName = prpCmainCargoDto.getStartSiteName();
		strViaSiteCode = prpCmainCargoDto.getViaSiteCode();
		strViaSiteName = prpCmainCargoDto.getViaSiteName();
		strReshipSiteName = prpCmainCargoDto.getReshipSiteName();
		strEndSiteName = prpCmainCargoDto.getEndSiteName();
		strEndDetailName = prpCmainCargoDto.getEndDetailName();
		strClaimSite = prpCmainCargoDto.getClaimSite();
		strTransferBank = prpCmainCargoDto.getTransferBank();
		strBLName = prpCmainCargoDto.getBLName();
	}
	if (strStartSiteName.length() > 0)
		strTPath = strTPath + "从 " + strStartSiteName + " 起";
	if (strViaSiteName.length() > 0)
		strTPath = strTPath + " 经 " + strStartSiteName + " ";
	if (strStartSiteName.length() > 0)
		strTPath = strTPath + "至 " + strEndSiteName;

	//zhulei leave:取 赔款计算书表 数据......
	String conditions = "";
	Collection collection = null;
	conditions = "RegistNo='" + strRegistNo + "' AND TextType='3'";
	BLPrpLregistTextFacade blPrpLregistTextFacade = new BLPrpLregistTextFacade();
	collection = blPrpLregistTextFacade.findByConditions(conditions);
	int i = 0;
	String strContext = ""; //查勘报告
	//String[] arrContext = "";      //查勘报告
	if (collection != null) {
		Iterator iterator1 = collection.iterator();
		//arrContext = new String[collection.size()];
		while (iterator1.hasNext()) {
			PrpLregistTextDto prpLregistTextDto = (PrpLregistTextDto) iterator1.next();
			//arrContext[i] = prpLRegistTextDto.getContext();
			strContext = strContext + prpLregistTextDto.getContext();
			i++;
		}
	}
%>
