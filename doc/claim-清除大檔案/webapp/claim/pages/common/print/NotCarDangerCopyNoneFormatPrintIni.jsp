<%--

****************************************************************************

* DESC       ：出险後抄单打印页初始化

* AUTHOR     ：wangwei

* CREATEDATE ：2005-5-28

* MODIFYLIST ：   Name       Date            Reason/Contents

****************************************************************************

--%>



<%-- 引入bean类部分 --%>
<%@page import="java.util.Collection"%>

<%@page import="java.util.Iterator"%>

<%@page import="java.text.*"%>

<%@page import="com.sinosoft.claim.ui.control.action.*"%>

<%@page import="com.sinosoft.claim.dto.custom.*"%>

<%@page import="com.sinosoft.claim.dto.domain.*"%>

<%@page import="com.sinosoft.claim.util.*"%>

<%@page import="com.sinosoft.sysframework.common.util.*"%>

<%@page import="com.sinosoft.sysframework.common.datatype.DateTime"%>

<%@page import="com.sinosoft.sysframework.exceptionlog.*"%>

<%@page import="com.sinosoft.utiall.blsvr.*"%>

<%@page import="com.sinosoft.claim.bl.facade.*"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.sinosoft.utility.string.Str"%>



<%
	//责任险公共变量
	String[] itemDetailName = null;
	String[] currency = null;
	double[] everyTimeLimit = null;
	double[] everyTimePropLimit = null;
	double[] everyTimePersonLimit = null;
	double[] everyPersonLimit = null;
	String[] kindCode = null;
	String[] kindNamef = null;
	String[] currencyf = null;
	double[] dbAmountf = null;
	double[] limitAmount = null;
	double[] everyTimeLimitf = null;
	int[] itemKindNo = null;
	String[] flag = null;
	String[] limitType = null;
	String strflag = null;
	double[] limitNo = null;
	int j = 0;
	Collection collectionLimit = null;
	int i = 0;

	String strClassCode = ""; //险类

	String strPolicyNo = ""; //保单号

	String strRegistNo = ""; //报案号

	String strPolicyName = ""; //被保险人名称

	String strPolicyPhonenumber = ""; //联系电话

	String strPolicyAddress = ""; //被保险人地址

	String strCoinsFlag = ""; //联共保标志

	String strCurrency = "";

	double dbSumAmount = 0.0; //总保额

	double dbSumpremium = 0.0; //总保费

	String strComcname = "";//保单归属机构

	DateTime OperateDate = new DateTime();

	DateTime InputDate = new DateTime();

	DateTime SignDate = new DateTime();

	DateTime UnderWriteEndDate = new DateTime();

	DateTime StartDate = new DateTime();

	DateTime EndDate = new DateTime();

	String strRiskCode = ""; //险种代码

	String strRiskName = ""; //险种名称

	String[] strKindName = null; //险别名称
	String[] strItemCode = null; //标的项目
	String[] strItemdetailName = null;//标的名称
	double[] dbAmount = null; //险别保额

	String strRegistno = ""; //报案号
	Date strRegistReportDate = new DateTime(); //报案时间
	String strRregistLinkerName = ""; //联系人
	String strRegistPhoneNumber = ""; //联系电话
	DateTime strRegistDamageStartDate = new DateTime();//出险时间
	String strRegistDamageAddress = "";//出险地点
	String strRegistDamageCode = "";//出险原因
	String strRegistContext = "";//报案内容摘要
	String[] strAddressName = null;//保险标的地址
	String strLimitName = "";//免赔说明

	String[] strCengageDto = null;

	String[] strEndorseNo = null;

	String[] strEndorType = null;

	DateTime[] strEndorDate = null;

	String[] strUnderWriteName = null;

	int[] iPlanNo = null;

	String[] strPlanEndorNo = null;

	double[] dbPlanFee = null;

	double[] dbRealFee = null;

	DateTime[] strPayDate = null;

	Collection collection = null;

	UICodeAction uiCodeAction = new UICodeAction();

	String strItemDetailName = "";

	RegistDto registDto = (RegistDto) request.getAttribute("registDto");

	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");

	BLPrpDkind blPrpDkind = new BLPrpDkind();
	String strSQL = "";
	if (registDto != null && registDto.getPrpLregistDto() != null) {
		strRegistno = registDto.getPrpLregistDto().getRegistNo();
		strRegistReportDate = registDto.getPrpLregistDto().getReportDate();
		strRregistLinkerName = registDto.getPrpLregistDto().getLinkerName();
		strRegistPhoneNumber = registDto.getPrpLregistDto().getPhoneNumber();
		strRegistDamageStartDate = registDto.getPrpLregistDto().getDamageStartDate();
		strRegistDamageAddress = registDto.getPrpLregistDto().getDamageAddress();
		strRegistDamageCode = registDto.getPrpLregistDto().getDamageName();
		Collection prpLregistTextList = registDto.getPrpLregistTextDtoList();
		StringBuffer context = new StringBuffer();
		StringBuffer callCenterInfo = new StringBuffer();
		if (prpLregistTextList != null) {
			Iterator iterator = prpLregistTextList.iterator();
			while (iterator.hasNext()) {
				PrpLregistTextDto prpLregistTextDto = (PrpLregistTextDto) iterator.next();
				if ("1".equals(prpLregistTextDto.getTextType())) {
					context.append("  ");
					context.append(prpLregistTextDto.getContext());
					context.append("\t");
				}
			}
		}
		strRegistContext = context.toString();
	}

	//取特别约定
	ArrayList prpCengageDtoList = policyDto.getPrpCengageDtoList();
	if (prpCengageDtoList == null) {
		strCengageDto = new String[] { "" };
	} else {
		strCengageDto = new String[prpCengageDtoList.size()];
		int z = 0;
		for (Iterator prpCengageDtoIterator = prpCengageDtoList.iterator(); prpCengageDtoIterator.hasNext();) {
			PrpCengageDto prpCengageDto = (PrpCengageDto) prpCengageDtoIterator.next();
			if ("0".equals(prpCengageDto.getTitleFlag())) {
				strCengageDto[z] = prpCengageDto.getClauses();
				z++;
			}
		}
	}
	//获取免赔说明
	int index = 0;
	int intClauseSerialNo = 1;
	for (Iterator iterator = prpCengageDtoList.iterator(); iterator.hasNext();) {
		PrpCengageDto prpCengageDto = (PrpCengageDto) iterator.next();
		if ("T9995".equals(prpCengageDto.getClauseCode()) && "0".equals(prpCengageDto.getTitleFlag())) {
			index++;
			if (intClauseSerialNo != index) {
				intClauseSerialNo = index;
				strLimitName = strLimitName + "<br/>" + index + "：" + prpCengageDto.getClauses() + "<br/>";
			} else {
				strLimitName = strLimitName + index + "：" + prpCengageDto.getClauses() + "<br/>";
			}
		} else if ("T9995".equals(prpCengageDto.getClauseCode()) && !"0".equals(prpCengageDto.getTitleFlag())) {
			strLimitName = strLimitName + prpCengageDto.getClauses();
		}
	}

	EndorseDto endorseDto = (EndorseDto) request.getAttribute("endorseDto");

	if (registDto != null && registDto.getPrpLregistDto() != null) {

		strPolicyNo = registDto.getPrpLregistDto().getPolicyNo();

		strClassCode = registDto.getPrpLregistDto().getClassCode();

		strRegistNo = registDto.getPrpLregistDto().getRegistNo();

		//add by liuwei at 2011-03-22 获取被保险险人 start
		strPolicyName = registDto.getPrpLregistDto().getInsuredName();
		//add by liuwei at 2011-03-22 获取被保险险人 end
	}

	if (policyDto != null && policyDto.getPrpCmainDto() != null) {

		//获取保险标的
		PrpCitemKindDto prpCitemKindDto = new PrpCitemKindDto();
		ArrayList prpCItemKindDtoList = new ArrayList();
		prpCItemKindDtoList = policyDto.getPrpCitemKindDtoList();
		//modify by liuwei at 2011-05-27 获取保险标的信息 start
		Map itemKindMap = new HashMap();
		for (int i1 = 0; i1 < prpCItemKindDtoList.size(); i1++) {
			prpCitemKindDto = (PrpCitemKindDto) prpCItemKindDtoList.get(i1);
			if (!"".equals(prpCitemKindDto.getItemDetailName())) {
				itemKindMap.put(prpCitemKindDto.getItemCode(), prpCitemKindDto.getItemDetailName());
			}
		}

		Set itemKindset = itemKindMap.keySet();
		for (Iterator it = itemKindset.iterator(); it.hasNext();) {
			strItemDetailName = strItemDetailName + "," + itemKindMap.get(it.next());
		}

		if (strItemDetailName.length() > 0) {
			strItemDetailName = strItemDetailName.substring(1);
		}
		//modify by liuwei at 2011-05-27 获取保险标的信息 end
		//获取保险标的地址
		PrpCaddressDto prpCaddressDto = new PrpCaddressDto();
		List prpCaddressList = new ArrayList();
		prpCaddressList = policyDto.getPrpCaddressDtoList();
		if (prpCaddressList != null) {
			strAddressName = new String[prpCaddressList.size()];
			for (int index1 = 0; index1 < prpCaddressList.size(); index1++) {
				prpCaddressDto = (PrpCaddressDto) prpCaddressList.get(index1);
				strAddressName[index1] = prpCaddressDto.getAddressName();
			}
		}
		//modify by liuwei at 2011-03-22 这里注释掉获取被保险人名称，获取被保险人放到从报案对象中获取 start
		//strPolicyName = policyDto.getPrpCmainDto().getInsuredName();
		//modify by liuwei at 2011-03-22 这里注释掉获取被保险人名称，获取被保险人放到从报案对象中获取 end
		Collection prpcinsuredDtoList = new ArrayList();
		PrpCinsuredDto prpcinsuredDto = null;
		prpcinsuredDtoList = policyDto.getPrpCinsuredDtoList();
		Iterator iterator1 = prpcinsuredDtoList.iterator();
		while (iterator1.hasNext()) {
			prpcinsuredDto = (PrpCinsuredDto) iterator1.next();
			if ("1".equals(prpcinsuredDto.getInsuredFlag())) {
				break;
			}
		}
		strPolicyPhonenumber = prpcinsuredDto.getPhoneNumber();
		strPolicyAddress = policyDto.getPrpCmainDto().getAppliAddress();
		strCoinsFlag = policyDto.getPrpCmainDto().getCoinsFlag().toString();
		dbSumAmount = policyDto.getPrpCmainDto().getSumAmount();
		dbSumpremium = policyDto.getPrpCmainDto().getSumPremium();
		strComcname = new UICodeAction().findPrpDcompanyByPrimaryKey(policyDto.getPrpCmainDto().getComCode()).getComCName();
		//System.out.println("=================[ strPolicyName ]" +strPolicyName);
		strPolicyAddress = policyDto.getPrpCmainDto().getInsuredAddress();

		strCoinsFlag = policyDto.getPrpCmainDto().getCoinsFlag().toString();

		dbSumAmount = policyDto.getPrpCmainDto().getSumAmount();

		strCurrency = policyDto.getPrpCmainDto().getCurrency();

		OperateDate = policyDto.getPrpCmainDto().getOperateDate();

		InputDate = policyDto.getPrpCmainDto().getInputDate();

		SignDate = policyDto.getPrpCmainDto().getSignDate();

		UnderWriteEndDate = policyDto.getPrpCmainDto().getUnderWriteEndDate();

		StartDate = policyDto.getPrpCmainDto().getStartDate();

		EndDate = policyDto.getPrpCmainDto().getEndDate();

		strRiskCode = policyDto.getPrpCmainDto().getRiskCode();

		strRiskName = uiCodeAction.translateRiskCode(policyDto.getPrpCmainDto().getRiskCode(), true);

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

			strEndorType[i] = uiCodeAction.translateCodeCode("EndorType", prpPheadDtoTemp.getEndorType(), true);

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

			if (prpCplanDtoTemp.getPayNo() == 0)

				iPlanNo[i] = 1;

			strPlanEndorNo[i] = prpCplanDtoTemp.getEndorseNo();

			dbPlanFee[i] = prpCplanDtoTemp.getPlanFee();

			if (prpCplanDtoTemp.getDelinquentFee() == 0.00) {
				dbRealFee[i] = prpCplanDtoTemp.getPlanFee();
				strPayDate[i] = prpCplanDtoTemp.getPlanDate();
			} else {
				dbRealFee[i] = 0.00;
				strPayDate[i] = new DateTime();
			}

			i++;

		}

	}

	//责任险显示主险附加险限额
	if (policyDto.getPrpCitemKindDtoList() != null) {

		if (strClassCode.equals("04")) {
			collection = policyDto.getPrpCitemKindDtoList();

			flag = new String[collection.size()];
			kindCode = new String[collection.size()];
			strKindName = new String[collection.size()];
			strItemCode = new String[collection.size()];
			itemKindNo = new int[collection.size()]; //01 每次 
			itemDetailName = new String[collection.size()]; //营业场所
			currency = new String[collection.size()];
			dbAmount = new double[collection.size()]; //04 累积
			limitAmount = new double[collection.size()]; //赔偿限额
			// everyTimeLimit    = new double[collection.size()];  //01 每次 
			// everyTimePropLimit    = new double[collection.size()]; //03 每次财产
			//everyTimePersonLimit    = new double[collection.size()]; //07 每次人
			//everyPersonLimit    = new double[collection.size()];  //02 每人

			i = 0;
			Iterator iterator = policyDto.getPrpCitemKindDtoList().iterator();
			//System.out.println("专线主线-----1-------------"); 
			while (iterator.hasNext()) {
				//System.out.println("专线主线-----111------------"); 
				PrpCitemKindDto prpCitemKindDtoTemp = (PrpCitemKindDto) iterator.next();
				strItemdetailName = new String[collection.size()];
				flag[i] = prpCitemKindDtoTemp.getFlag(); //System.out.println("专线主线-flag-------"+flag[i]); 
				strKindName[i] = prpCitemKindDtoTemp.getKindName();
				strItemCode[i] = prpCitemKindDtoTemp.getItemCode();
				strItemdetailName[i] = prpCitemKindDtoTemp.getItemDetailName();
				dbAmount[i] = prpCitemKindDtoTemp.getAmount();
				itemKindNo[i] = prpCitemKindDtoTemp.getItemKindNo(); //System.out.println("专线主线-itemKindNo-------"+itemKindNo[i]);
				limitAmount[i] = prpCitemKindDtoTemp.getAmount();
				currency[i] = prpCitemKindDtoTemp.getCurrency(); //System.out.println("专线主线-currency-------"+currency[i]);
				itemDetailName[i] = prpCitemKindDtoTemp.getItemDetailName(); //System.out.println("专线主线-itemDetailName-------"+itemDetailName[i]);
				strKindName[i] = prpCitemKindDtoTemp.getKindName(); //System.out.println("专线主线-strKindName-------"+strKindName[i]);
				strItemCode[i] = prpCitemKindDtoTemp.getItemCode();
				kindCode[i] = prpCitemKindDtoTemp.getKindCode(); //System.out.println("专线主线-flag-------"+flag[i]);
				//System.out.println("专线主线-----2------------"); 

				collectionLimit = policyDto.getPrpClimitDtoList();

				//limitType = new String[collectionLimit.size()]; 
				limitNo = new double[collectionLimit.size()];

				Iterator it = policyDto.getPrpClimitDtoList().iterator();
				j = 0;
				while (it.hasNext()) {
					//System.out.println("专线主线-----3-------------");
					PrpClimitDto prpClimitDtoTemp = (PrpClimitDto) it.next();
					limitNo[j] = prpClimitDtoTemp.getLimitNo();
					// limitType[j]= prpClimitDtoTemp.getLimitType();
					if (limitNo[j] == itemKindNo[i]) {
						//System.out.println("专线主线-----33---j---"+j);
						/*if(limitType[j].equals("04")){dbAmount[i] = prpClimitDtoTemp.getLimitFee(); }
						 else if(limitType[j].equals("01")){everyTimeLimit[i] = prpClimitDtoTemp.getLimitFee(); }
						 else if(limitType[j].equals("03")){everyTimePropLimit[i] = prpClimitDtoTemp.getLimitFee(); }
						else if(limitType[j].equals("07")){everyTimePersonLimit[i] = prpClimitDtoTemp.getLimitFee(); }
						 else if(limitType[j].equals("02")){everyPersonLimit[i] = prpClimitDtoTemp.getLimitFee(); }*/
					} else { //System.out.println("专线主线-----3333---j---"+j);
					}
					j++;
					//System.out.println("专线主线-----4-------------");
				}
				i++;
			}

		} else {
			collection = policyDto.getPrpCitemKindDtoList();
			flag = new String[collection.size()];
			strItemdetailName = new String[collection.size()];
			strKindName = new String[collection.size()];
			strItemCode = new String[collection.size()];
			dbAmount = new double[collection.size()];

			i = 0;

			Iterator iterator = policyDto.getPrpCitemKindDtoList().iterator();

			while (iterator.hasNext()) {
				PrpCitemKindDto prpCitemKindDtoTemp = (PrpCitemKindDto) iterator.next();
				if (prpCitemKindDtoTemp.getFamilyName() != null && strPolicyName.equals(prpCitemKindDtoTemp.getFamilyName())) {
					flag[i] = prpCitemKindDtoTemp.getFlag();
					strKindName[i] = prpCitemKindDtoTemp.getKindName();
					strItemCode[i] = prpCitemKindDtoTemp.getItemCode();
					strItemdetailName[i] = prpCitemKindDtoTemp.getItemDetailName();
					dbAmount[i] = prpCitemKindDtoTemp.getAmount();
					i++;
				} else if (prpCitemKindDtoTemp.getFamilyName() == null || "".equals(prpCitemKindDtoTemp.getFamilyName())) {
					flag[i] = prpCitemKindDtoTemp.getFlag();
					strKindName[i] = prpCitemKindDtoTemp.getKindName();
					strItemCode[i] = prpCitemKindDtoTemp.getItemCode();
					strItemdetailName[i] = prpCitemKindDtoTemp.getItemDetailName();
					dbAmount[i] = prpCitemKindDtoTemp.getAmount();
					i++;
				}
			}
		}

	}

	//获取历史赔付记录
	String[] strClaimNo = null; //赔案号
	DateTime[] strDamangeDate = null; //出险日期
	String[] strDamageName = null; //出险原因
	double[] dbSumPaid = null; //赔付金额
	DateTime[] arrEndCaseDate = null; //结案日期(claim表)
	DateTime[] arrEndCaseDate1 = null; //结案日期(compensate表)
	double[] dbSumClaim = null; //未决金额(估损金额)
	String[] arrCaseNo = null; //归档号
	String[] arrOperatorCode = null; //经办人（操作人代码）
	String[] arrUnderWriteName = null; //核陪人名称
	String[] arrHandlerCode = null; //理算人代码
	String[] arrHandlerName = null; //理算人名称
	BLPrpDuser blPrpDuser = new BLPrpDuser();
	Collection collection1 = null;
	String conditions = "";
	int k = 0;
	conditions = "PolicyNo='" + strPolicyNo + "'";
	BLClaimFacade blClaimFacade = new BLClaimFacade();
	collection = blClaimFacade.findByConditions(conditions);
	if (collection != null) {
		Iterator iterator = collection.iterator();
		strClaimNo = new String[collection.size()];
		strDamangeDate = new DateTime[collection.size()];
		strDamageName = new String[collection.size()];
		dbSumPaid = new double[collection.size()];
		arrEndCaseDate = new DateTime[collection.size()];
		arrEndCaseDate1 = new DateTime[collection.size()];
		dbSumClaim = new double[collection.size()];
		arrCaseNo = new String[collection.size()];
		arrOperatorCode = new String[collection.size()];
		arrUnderWriteName = new String[collection.size()];
		arrHandlerCode = new String[collection.size()];
		arrHandlerName = new String[collection.size()];
		i = 0;
		while (iterator.hasNext()) {
			PrpLclaimDtoBase prpLclaimDtoBase = (PrpLclaimDtoBase) iterator.next();
			strClaimNo[i] = prpLclaimDtoBase.getClaimNo();
			strDamangeDate[i] = prpLclaimDtoBase.getDamageStartDate();
			strDamageName[i] = prpLclaimDtoBase.getDamageName();
			dbSumPaid[i] = prpLclaimDtoBase.getSumPaid();
			arrEndCaseDate[i] = prpLclaimDtoBase.getEndCaseDate();
			dbSumClaim[i] = prpLclaimDtoBase.getSumClaim();
			arrCaseNo[i] = prpLclaimDtoBase.getCaseNo();
			//zhulei leave:取 赔款计算书表 数据......
			conditions = "ClaimNo='" + strClaimNo[i] + "'";
			BLCompensateFacade blCompensateFacade = new BLCompensateFacade();
			//System.out.println("test===========----conditions="+conditions);
			collection1 = blCompensateFacade.findByConditions(conditions);
			k = 0;
			if (collection1 != null) {
				Iterator iterator1 = collection1.iterator();
				while (iterator1.hasNext()) {
					//System.out.println("==========67676767========");
					PrpLcompensateDto prpLcompensateDto = (PrpLcompensateDto) iterator1.next();
					arrUnderWriteName[i] = prpLcompensateDto.getUnderWriteName();
					arrHandlerCode[i] = prpLcompensateDto.getHandlerCode();
					blPrpDuser = new BLPrpDuser();
					blPrpDuser.query("UserCode='" + arrHandlerCode[i] + "'");
					arrHandlerName[i] = blPrpDuser.getArr(0).getUserName();
					break;
				}
			}
			//add by zhulei arrUnderWriteName不能打出null
			if (arrUnderWriteName[i] == null) {
				arrUnderWriteName[i] = "";
			}
			if (arrHandlerName[i] == null) {
				arrHandlerName[i] = "";
			}
			i++;
		}
	}

	String strUserName = ""; //抄单人
	String mDateTime = ""; //抄单日期
	UserDto user = (UserDto) session.getAttribute("user");
	strUserName = user.getUserName();
	Calendar cal = Calendar.getInstance();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	mDateTime = formatter.format(cal.getTime());

	if (strCoinsFlag.equals("0")) { //非共保

		strCoinsFlag = "非共保";

	} else if (strCoinsFlag.equals("1")) { //主共保

		strCoinsFlag = "主共保";

	} else if (strCoinsFlag.equals("2")) { //共保

		strCoinsFlag = "共保";

	}
%>