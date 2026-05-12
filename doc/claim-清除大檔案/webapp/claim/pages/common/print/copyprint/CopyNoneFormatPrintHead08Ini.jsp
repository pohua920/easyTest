<%--
****************************************************************************
* DESC       ：出险後抄单打印页初始化－建筑工程险
* AUTHOR     ： 项目组
* CREATEDATE ：2005-9-16
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
<%@page import="com.sinosoft.utiall.blsvr.*"%>

<%
    int i = 0;
    String strClassCode = ""; //险类
    String strPolicyNo = ""; //保单号
    String strRegistNo = ""; //报案号
    String strPolicyName = ""; //被保险人名称
    String strPolicyAddress = ""; //被保险人地址
    String strCoinsFlag     = ""; //联共保标志
    String strCurrency   ="";
    double dbSumAmount   = 0.0;   //总保额
    double dbDeductible = 0;      //免赔额
    DateTime OperateDate = new DateTime();
    DateTime InputDate = new DateTime();
    DateTime SignDate = new DateTime();
    DateTime UnderWriteEndDate = new DateTime();
    DateTime StartDate = new DateTime();
    DateTime EndDate = new DateTime();


    String strRiskCode     = ""; //险种代码
    String strRiskName     = ""; //险种名称


    String[] strKindName = null;   //险别名称
    String[] strKindCode = null;   //险别代码
    String[] strItemDetailName = null; //标的名称
    String[] strKindCurrency = null; //险别币别
    double[] dbAmount   = null;  //险别保额

    String[] strEndorseNo = null;
    String[] strEndorType = null;
    DateTime[] strEndorDate = null;
    String[] strUnderWriteName = null;

    int[] iPlanNo = null;
    String[] strPlanEndorNo = null;
    double[] dbPlanFee = null;
    double[] dbRealFee = null;
    DateTime[] strPayDate = null;

    //add by zhulei 20050915 特别约定信息
    String[] arrEngage = null;     //特约、附加信息
    String[] arrClauseCode = null; //特约、附加代码
    String strEngage = "";

    //add by zhulei 20050916
    String strConstructName = "";    //工程名称


    Collection collection = null;
    UICodeAction uiCodeAction = new UICodeAction();

    RegistDto registDto = (RegistDto) request.getAttribute("registDto");
    PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");
    EndorseDto endorseDto = (EndorseDto) request.getAttribute("endorseDto");
    if(registDto!=null && registDto.getPrpLregistDto()!=null){
        strPolicyNo = registDto.getPrpLregistDto().getPolicyNo();
        strClassCode = registDto.getPrpLregistDto().getClassCode();
        strRegistNo = registDto.getPrpLregistDto().getRegistNo();
    }
    if(policyDto!=null && policyDto.getPrpCmainDto()!=null){
        strPolicyName = policyDto.getPrpCmainDto().getInsuredName();
        strPolicyAddress = policyDto.getPrpCmainDto().getAppliAddress();
        strCoinsFlag= policyDto.getPrpCmainDto().getCoinsFlag().toString();
        dbSumAmount =policyDto.getPrpCmainDto().getSumAmount();
        strCurrency =policyDto.getPrpCmainDto().getCurrency();
        OperateDate = policyDto.getPrpCmainDto().getOperateDate();
        InputDate = policyDto.getPrpCmainDto().getInputDate();
        SignDate = policyDto.getPrpCmainDto().getSignDate();
        UnderWriteEndDate = policyDto.getPrpCmainDto().getUnderWriteEndDate();
        StartDate = policyDto.getPrpCmainDto().getStartDate();
        EndDate = policyDto.getPrpCmainDto().getEndDate();
        strRiskCode = policyDto.getPrpCmainDto().getRiskCode();
        strRiskName = uiCodeAction.translateRiskCode(policyDto.getPrpCmainDto().getRiskCode(),true);
    }
    if (endorseDto.getPrpPheadDtoList() != null) {
    	  collection = endorseDto.getPrpPheadDtoList();
    	  strEndorseNo = new String[collection.size()];
        strEndorType = new String[collection.size()];
        strEndorDate = new DateTime[collection.size()];
        strUnderWriteName = new String[collection.size()];
        i = 0;
				Iterator iterator = endorseDto.getPrpPheadDtoList()
						.iterator();
				////System.out.println(iterator.getSize());
				while (iterator.hasNext()) {
					PrpPheadDto prpPheadDtoTemp = (PrpPheadDto) iterator
							.next();
					strEndorseNo[i] = prpPheadDtoTemp.getEndorseNo();
					strEndorType[i] = uiCodeAction.translateCodeCode("EndorType",prpPheadDtoTemp.getEndorType(),true);
					strEndorDate[i] = prpPheadDtoTemp.getEndorDate();
					strUnderWriteName[i] = prpPheadDtoTemp.getUnderWriteName();
					i++;
				}
			}

		if (policyDto.getPrpCplanDtoList() != null) {
				Iterator iterator = policyDto.getPrpCplanDtoList()
						.iterator();
    	  collection = policyDto.getPrpCplanDtoList();
			  iPlanNo = new int[collection.size()];
        strPlanEndorNo = new String[collection.size()];
        dbPlanFee = new double[collection.size()];
        dbRealFee = new double[collection.size()];
        strPayDate = new DateTime[collection.size()];
        i = 0;
				while (iterator.hasNext()) {
					PrpCplanDto prpCplanDtoTemp = (PrpCplanDto) iterator
							.next();

			    iPlanNo[i] = prpCplanDtoTemp.getPayNo();
			    if(prpCplanDtoTemp.getPayNo()==0)
			      iPlanNo[i] = 1;
			    strPlanEndorNo[i] = prpCplanDtoTemp.getEndorseNo();
			    dbPlanFee[i] = prpCplanDtoTemp.getPlanFee();
			    dbRealFee[i] = prpCplanDtoTemp.getPlanFee() - prpCplanDtoTemp.getDelinquentFee();
			    strPayDate[i] = prpCplanDtoTemp.getPlanDate();
			    i++;
				}
			}

		if (policyDto.getPrpCitemKindDtoList() != null) {

    	  collection = policyDto.getPrpCitemKindDtoList();
			  strKindName = new String[collection.size()];
			  strKindCode = new String[collection.size()];
			  strKindCurrency = new String[collection.size()];
			  strItemDetailName = new String[collection.size()];
			  dbAmount    = new double[collection.size()];
			  i = 0;
				Iterator iterator = policyDto.getPrpCitemKindDtoList()
						.iterator();
				while (iterator.hasNext()) {
					PrpCitemKindDto prpCitemKindDtoTemp = (PrpCitemKindDto) iterator
							.next();
					strKindName[i] = prpCitemKindDtoTemp.getKindName();
					strKindCode[i] = prpCitemKindDtoTemp.getKindCode();
					strKindCurrency[i] = prpCitemKindDtoTemp.getCurrency();
					strItemDetailName[i] = prpCitemKindDtoTemp.getItemDetailName();
					dbAmount[i] = prpCitemKindDtoTemp .getAmount();
					dbDeductible = dbDeductible + prpCitemKindDtoTemp.getDeductible();
					i++;
				}
			}

//    if (strCoinsFlag.equals("0")) {         //非共保
//        strCoinsFlag = "非共保";
//    } else if (strCoinsFlag.equals("1")) {  //主共保
//        strCoinsFlag = "主共保";
//    } else if (strCoinsFlag.equals("2")) {  //共保
//        strCoinsFlag = "共保";
//    }
    if (strCoinsFlag.equals("0")) {
        strCoinsFlag = "否";
    } else {
        strCoinsFlag = "是";
    }

    //add by zhulei 20050915 特别约定
    if (policyDto.getPrpCengageDtoList() != null) {
    	collection = policyDto.getPrpCengageDtoList();
      arrEngage = new String[collection.size()];
      arrClauseCode = new String[collection.size()];
      i = 0;
      Iterator iterator = policyDto.getPrpCengageDtoList()
      		.iterator();
      while (iterator.hasNext()) {
      	PrpCengageDto prpCengageDto = (PrpCengageDto) iterator
      			.next();
      	arrEngage[i] = prpCengageDto.getClauses();
      	arrClauseCode[i] = prpCengageDto.getClauseCode();
      	////System.out.println("===========arrEngage[i]="+arrEngage[i]);
      	////System.out.println("===========strEngage="+strEngage);
      	////System.out.println("======================arrEngage[i].indexOf(strEngage)="+arrEngage[i].indexOf(strEngage));
      	//特约不重复显示
      	if(arrClauseCode[i].substring(0,1).equals("T")){
      	  if(strEngage.indexOf(arrEngage[i])<0){
      	    strEngage = strEngage + arrEngage[i] + "<br>";
          }
        }
      	i++;
      }
    }

    //add by zhulei 20050917 被保险人名称
    String[] arrInsuredName = null;    //被保险人名称
    String[] arrInsuredNature = null;  //被保险人性质
    String[] arrInsuredFlag = null;  //被保险人性质
    //add start by miaowenjun 20060907
    String[] arrItemAddress = null; 
    String strConstructionName = ""; //工程名称
    String strStartFixDate = "";     //安装起期（建筑起期）
    String strEndFixDate = "";       //安装止期（建筑止期）
    String strTestPeriod = "";       //试车天数
    String strTestStartDate = "";    //试车起期
    String strTestEndDate = "";      //试车止期
    String strStartAddDate = "";     //保证起期
    String strEndAddDate = "";       //保证止期
    String[] strLimitType  = null;   //赔偿限额/免赔额类别
    String[] arrLimitTypeName = null; //赔偿限额/免赔额类别名称
    String[] strLimitCurrency = null;   //偿限额币别
    double[] dbLimitFee    = null;   //赔偿限额
    String[] arrLimitGrade = null;   //限额/免赔级别
    
    String strCurrency10 = "";  //每次事故赔偿限额币别
    String strCurrency07 = "";  //财产损失赔偿限额币别
    String strCurrency06 = "";  //人身伤亡赔偿限额币别
    String strCurrency05 = "";  //每人赔偿限额币别
    String strCurrency09 = "";  //每次事故绝对免赔币别
    String strCurrency11 = "";  //累计赔偿限额币别
    
    double dbLimit10 = 0;      //每次事故赔偿限额
    double dbLimit07 = 0;      //财产损失赔偿限额
    double dbLimit06 = 0;      //人身伤亡赔偿限额
    double dbLimit05 = 0;      //每人赔偿限额
    double dbLimit09 = 0;      //每次事故绝对免赔
    double dbLimit11 = 0;      //累计赔偿限额币别
    if(policyDto != null && policyDto.getPrpCinsuredDtoList() != null && policyDto.getPrpCinsuredDtoList().size()>0){
    		arrInsuredName = new String[policyDto.getPrpCinsuredDtoList().size()];
		    arrInsuredNature = new String[policyDto.getPrpCinsuredDtoList().size()];
		    arrInsuredFlag = new String[policyDto.getPrpCinsuredDtoList().size()];
		    for(i=0;i<policyDto.getPrpCinsuredDtoList().size();i++){
		    		PrpCinsuredDto prpCinsuredDto = new PrpCinsuredDto();
		    		prpCinsuredDto = (PrpCinsuredDto)policyDto.getPrpCinsuredDtoList().get(i);
		    		arrInsuredName[i] = prpCinsuredDto.getInsuredName();
			    	arrInsuredNature[i] = prpCinsuredDto.getInsuredNature();
			    	arrInsuredFlag[i] = prpCinsuredDto.getInsuredFlag();
		    }
    }
    if(policyDto != null && policyDto.getPrpCaddressDtoList() != null && policyDto.getPrpCaddressDtoList().size()>0){
    		arrItemAddress = new String[policyDto.getPrpCaddressDtoList().size()];
    		for(i=0;i<policyDto.getPrpCaddressDtoList().size();i++){
    				PrpCaddressDto prpCaddressDto = new PrpCaddressDto();
    				prpCaddressDto = (PrpCaddressDto)policyDto.getPrpCaddressDtoList().get(i);
    				arrItemAddress[i] = prpCaddressDto.getAddressName();
    		}
    }
    if(policyDto != null && policyDto.getPrpCmainConstructDtoList() != null && policyDto.getPrpCmainConstructDtoList().size()>0){
    		PrpCmainConstructDto prpCmainConstructDto = new PrpCmainConstructDto();
    		prpCmainConstructDto = (PrpCmainConstructDto)policyDto.getPrpCmainConstructDtoList().get(0)
    		strConstructionName = prpCmainConstructDto.getConstructName();
	    	strStartFixDate     = prpCmainConstructDto.getStartFixDate();
	    	strEndFixDate       = prpCmainConstructDto.getEndFixDate();
	    	strTestPeriod       = prpCmainConstructDto.getTestPeriod();
	    	strTestStartDate    = prpCmainConstructDto.getTestStartDate();
	    	strTestEndDate      = prpCmainConstructDto.getTestEndDate();
	    	strStartAddDate     = prpCmainConstructDto.getStartAddDate();
	    	strEndAddDate       = prpCmainConstructDto.getEndAddDate();
    }
    if(policyDto != null && policyDto.getPrpClimitDtoList() != null && policyDto.getPrpClimitDtoList().size()>0){
    		strLimitType  = new String[policyDto.getPrpClimitDtoList().size()];
		    arrLimitTypeName  = new String[policyDto.getPrpClimitDtoList().size()];
		    strLimitCurrency   = new String[policyDto.getPrpClimitDtoList().size()];
		    dbLimitFee    = new double[policyDto.getPrpClimitDtoList().size()];
		    arrLimitGrade = new String[policyDto.getPrpClimitDtoList().size()];
		    for(i=0;i<policyDto.getPrpClimitDtoList().size();i++){
		    		PrpClimitDto prpClimitDto = new PrpClimitDto();
		    		prpClimitDto = (PrpClimitDto)policyDto.getPrpClimitDtoList().get(i);
			    	strLimitType[i]  = prpClimitDto.getLimitType();
			    	strLimitCurrency[i]   = prpClimitDto.getCurrency();
			    	dbLimitFee[i]    = prpClimitDto.getLimitFee();
			    	arrLimitGrade[i] = prpClimitDto.getLimitGrade();
			    	
			    	if(strLimitType[i].equals("10")){  //每次事故赔偿限额
			    		dbLimit10 = prpClimitDto.getLimitFee();
			    		strCurrency10 = prpClimitDto.getCurrency();
			    	} else if(strLimitType[i].equals("07")){  //财产损失赔偿限额
			    		dbLimit07 = prpClimitDto.getLimitFee();
			    		strCurrency07 = prpClimitDto.getCurrency();
			    	} else if(strLimitType[i].equals("06")){  //人身伤亡赔偿限额
			    		dbLimit06 = prpClimitDto.getLimitFee();
			    		strCurrency06 = prpClimitDto.getCurrency();
			    	} else if(strLimitType[i].equals("05")){  //每人赔偿限额
			    		dbLimit05 = prpClimitDto.getLimitFee();
			    		strCurrency05 = prpClimitDto.getCurrency();
			    	} else if(strLimitType[i].equals("09")){  //每次事故绝对免赔
			    		dbLimit09 = prpClimitDto.getLimitFee();
			    		strCurrency09 = prpClimitDto.getCurrency();
			    	} else if(strLimitType[i].equals("11")){  //每次事故绝对免赔
			    		dbLimit11 = prpClimitDto.getLimitFee();
			    		strCurrency11 = prpClimitDto.getCurrency();
			    	}
			    	
			    	arrLimitTypeName[i] = new UICodeAction().translateLimit(strRiskCode,strLimitType[i],true);
			  }
    }
  	//add end by miaowenjun 20060907
    //delete by miaowenjun 20060907
    /*BLPrpCinsured blPrpCinsured = new BLPrpCinsured();
    blPrpCinsured.getData(strPolicyNo);
    arrInsuredName = new String[blPrpCinsured.getSize()];
    arrInsuredNature = new String[blPrpCinsured.getSize()];
    arrInsuredFlag = new String[blPrpCinsured.getSize()];
    for(i=0;i<blPrpCinsured.getSize();i++){
    //System.out.println("|||-------------blPrpCinsured.getArr(i).getInsuredName()--"+blPrpCinsured.getArr(i).getInsuredName());
    //System.out.println("|||---------blPrpCinsured.getArr(i).getInsuredNature()---"+blPrpCinsured.getArr(i).getInsuredNature());
    	arrInsuredName[i] = blPrpCinsured.getArr(i).getInsuredName();
    	arrInsuredNature[i] = blPrpCinsured.getArr(i).getInsuredNature();
    	arrInsuredFlag[i] = blPrpCinsured.getArr(i).getInsuredFlag();
    }

    //add by zhulei 20050917 保险工程地址
    String[] arrItemAddress = null;
    BLPrpCaddress blPrpCaddress = new BLPrpCaddress();
    blPrpCaddress.getData(strPolicyNo);
    arrItemAddress = new String[blPrpCaddress.getSize()];
  	for(i=0;i<blPrpCaddress.getSize();i++){
    	arrItemAddress[i] = blPrpCaddress.getArr(i).getAddressName();
    	//System.out.println("=================test==========arrItemAddress="+arrItemAddress[i]);
    }

    //add by zhulei 20050917 工程信息
    BLPrpCmainConstruct blPrpCmainConstruct = new BLPrpCmainConstruct();
    blPrpCmainConstruct.getData(strPolicyNo);
    if(blPrpCmainConstruct.getSize()>0){
    	strConstructionName = blPrpCmainConstruct.getArr(0).getConstructName();

    	strStartFixDate     = blPrpCmainConstruct.getArr(0).getStartFixDate();
    	strEndFixDate       = blPrpCmainConstruct.getArr(0).getEndFixDate();
    	strTestPeriod       = blPrpCmainConstruct.getArr(0).getTestPeriod();
    	strTestStartDate    = blPrpCmainConstruct.getArr(0).getTestStartDate();
    	strTestEndDate      = blPrpCmainConstruct.getArr(0).getTestEndDate();
    	strStartAddDate     = blPrpCmainConstruct.getArr(0).getStartAddDate();
    	strEndAddDate       = blPrpCmainConstruct.getArr(0).getEndAddDate();
    }

    //add by zhulei 20050916 赔偿限额信息（这里用业务jar取数据，与01险类不同，这里需要用数组处理）
    
    
    BLPrpDlimit blPrpDlimit = new BLPrpDlimit();
    BLPrpClimit blPrpClimit = new BLPrpClimit();
    String strSQL = "PolicyNo='" + strPolicyNo + "'";
    blPrpClimit.query(strSQL);
    strLimitType  = new String[blPrpClimit.getSize()];
    arrLimitTypeName  = new String[blPrpClimit.getSize()];
    strLimitCurrency   = new String[blPrpClimit.getSize()];
    dbLimitFee    = new double[blPrpClimit.getSize()];
    arrLimitGrade = new String[blPrpClimit.getSize()];
    for(i=0;i<blPrpClimit.getSize();i++){
    	strLimitType[i]  = blPrpClimit.getArr(i).getLimitType();
    	strLimitCurrency[i]   = blPrpClimit.getArr(i).getCurrency();
    	dbLimitFee[i]    = Double.parseDouble(blPrpClimit.getArr(i).getLimitFee());
    	arrLimitGrade[i] = blPrpClimit.getArr(i).getLimitGrade();
    	
    	if(strLimitType[i].equals("10")){  //每次事故赔偿限额
    		dbLimit10 = Double.parseDouble(blPrpClimit.getArr(i).getLimitFee());
    		strCurrency10 = blPrpClimit.getArr(i).getCurrency();
    	} else if(strLimitType[i].equals("07")){  //财产损失赔偿限额
    		dbLimit07 = Double.parseDouble(blPrpClimit.getArr(i).getLimitFee());
    		strCurrency07 = blPrpClimit.getArr(i).getCurrency();
    	} else if(strLimitType[i].equals("06")){  //人身伤亡赔偿限额
    		dbLimit06 = Double.parseDouble(blPrpClimit.getArr(i).getLimitFee());
    		strCurrency06 = blPrpClimit.getArr(i).getCurrency();
    	} else if(strLimitType[i].equals("05")){  //每人赔偿限额
    		dbLimit05 = Double.parseDouble(blPrpClimit.getArr(i).getLimitFee());
    		strCurrency05 = blPrpClimit.getArr(i).getCurrency();
    	} else if(strLimitType[i].equals("09")){  //每次事故绝对免赔
    		dbLimit09 = Double.parseDouble(blPrpClimit.getArr(i).getLimitFee());
    		strCurrency09 = blPrpClimit.getArr(i).getCurrency();
    	} else if(strLimitType[i].equals("11")){  //每次事故绝对免赔
    		dbLimit11 = Double.parseDouble(blPrpClimit.getArr(i).getLimitFee());
    		strCurrency11 = blPrpClimit.getArr(i).getCurrency();
    	}
    	
    	blPrpDlimit = new BLPrpDlimit();
    	blPrpDlimit.query("RiskCode='"+strRiskCode+"' AND LimitCode='"+strLimitType[i]+"'");
    	if(blPrpDlimit.getSize()>0){
    	  arrLimitTypeName[i] = blPrpDlimit.getArr(0).getLimitCName();
    	}
    }*/
%>