<%--
**************************************************************************
* DESC       ：货运险出险通知书清单打印初始化
* AUTHOR     ：wangwei
* CREATEDATE ：2005-5-21
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>

<%-- 引入bean类部分 --%>
<%@page import="java.text.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.sinosoft.claim.ui.control.action.*"%>
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="com.sinosoft.claim.dto.domain.*"%> 
<%@page import="com.sinosoft.claim.util.*"%> 
<%@page import="com.sinosoft.sysframework.common.util.*"%> 
<%@page import="com.sinosoft.sysframework.common.datatype.DateTime"%> 
<%@page import="com.sinosoft.sysframework.exceptionlog.*"%> 
<%@page import="com.sinosoft.utiall.dbsvr.*"%>
<%@page import="com.sinosoft.claim.bl.facade.*"%>

<%
	String strPolicyNo = ""; //保单号
	String strInsuredName = ""; //被保险人
	double dblSumAmount = 0; //保险金额
	String strRegistNo = ""; //报案号
	String strStartDate = ""; //出险日期
	String strDamageName = ""; //出险原因
	String strDamageAddress = ""; //出险地点
	String strLindline = ""; //运输路线
	String strEstimateLoss = "";
	String strConveyance = ""; //运输方式
	String strVoyageNo = "";
	String strBargainNo = "";
	String strViaSiteName = "";
	String strCheckAgentCode = "";
	String strCheckAgentName = "";
	//add by zhulei 20050930
	String strReportorName = ""; //报案人
	String strPhoneNumber = ""; //联系电话
	String strReportDate = ""; //报案日期
	String strReceiverName = ""; //登记人
	String strInputDate = ""; //登记日期
	//add by miaowenjun 20060905
	String strMainPolicyNo = ""; //预约保单号  
	String strRemark = ""; //承运人

	int intItemKind = 0;
	int index = 0;

	PrpCitemKindDto prpCitemKindDto = null;

	//add by miaowenjun 20060905
	strMainPolicyNo = (String) request.getAttribute("strMainPolicyNo");
	strRemark = (String) request.getAttribute("strRemark");
	PrpLregistDto prpLregistDto = (PrpLregistDto) request.getAttribute("prpLregistDto");
	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");
	PrpCmainDto prpCmainDto = (PrpCmainDto) policyDto.getPrpCmainDto();
	UICodeAction uiCodeAction = new UICodeAction();

	PrpCmainCargoDto prpCmainCargoDto = policyDto.getPrpCmainCargoDto();

	strPolicyNo = prpLregistDto.getPolicyNo();
	strInsuredName = prpLregistDto.getInsuredName();
	strRegistNo = prpLregistDto.getRegistNo();
	strDamageName = prpLregistDto.getDamageName();
	strDamageAddress = prpLregistDto.getDamageAddress();
	strEstimateLoss = String.valueOf(prpLregistDto.getEstimateLoss());

	String strContent = "";
	BLPrpLregistTextFacade blPrpLregistTextFacade = new BLPrpLregistTextFacade();
	PrpLregistTextDto prpLregistTextDto = new PrpLregistTextDto();
	ArrayList textArrayList = null;
	textArrayList = (ArrayList) blPrpLregistTextFacade.findByConditions("RegistNo='" + strRegistNo + "'");
	Iterator textI = textArrayList.iterator();
	while (textI.hasNext()) {
		prpLregistTextDto = (PrpLregistTextDto) textI.next();
		strContent = strContent + prpLregistTextDto.getContext();
	}

	strReportorName = prpLregistDto.getReportorName();
	strPhoneNumber = prpLregistDto.getPhoneNumber();
	strReportDate = prpLregistDto.getReportDate().toString();
	strReceiverName = prpLregistDto.getReceiverName();
	strInputDate = prpLregistDto.getInputDate().toString();

	//add by zhulei 20050927 
	String strLossName = ""; //货物名称
	String strLossQuantity = ""; //货物数量
	String strUnit = ""; //货物单位
	String strBLNo = ""; //航次，航班号

	strLossName = prpLregistDto.getLossName();
	strLossQuantity = new DecimalFormat("###0").format(prpLregistDto.getLossQuantity());
	strUnit = prpLregistDto.getUnit();

	if (prpLregistDto.getDamageStartDate() == null || prpLregistDto.getDamageStartDate().toString().trim().equals("")) {
		strStartDate = "";
	} else {
		strStartDate = prpLregistDto.getDamageStartDate().getYear() + "年" + prpLregistDto.getDamageStartDate().getMonth() + "月" + prpLregistDto.getDamageStartDate().getDay() + "日";
	}

	if (prpCmainCargoDto != null) {
		strConveyance = prpCmainCargoDto.getConveyance();
		strVoyageNo = prpCmainCargoDto.getVoyageNo();
		strBargainNo = prpCmainCargoDto.getBargainNo();
		strViaSiteName = policyDto.getPrpCmainCargoDto().getViaSiteName();
		strCheckAgentCode = prpCmainCargoDto.getCheckAgentCode();
		strBLNo = prpCmainCargoDto.getBLNo();
	}

	//国外检验代理人代码转换
	if (strCheckAgentCode != null && !strCheckAgentCode.equals("")) {
		ArrayList prpDidentifierDtoList = uiCodeAction.translateCheckAgentCodeToName(" CheckAgentCode=" + strCheckAgentCode);
		if (prpDidentifierDtoList.size() > 1) {
			strCheckAgentName = ((PrpDidentifierDto) prpDidentifierDtoList.get(0)).getIdentifierName();
		}
	}
	String strStartDate1 = prpCmainDto.getStartDate().toString(); //货运险起运日期未保单的起保日期

	String strKindName = ""; //保险条款及险别
	intItemKind = policyDto.getPrpCitemKindDtoList().size();
	for (index = 0; index < intItemKind; index++) {
		prpCitemKindDto = (PrpCitemKindDto) policyDto.getPrpCitemKindDtoList().get(index);
		if (prpCitemKindDto.getFlag().substring(1, 2).equals("1")) {
			strKindName = strKindName + prpCitemKindDto.getKindName();
		}
		String strAmount = String.valueOf(prpCitemKindDto.getAmount());
	}

	if (policyDto.getPrpCmainCargoDto() != null) {
		if (strViaSiteName.equals("")) {
			strLindline = policyDto.getPrpCmainCargoDto().getStartSiteName() + " 到 " + policyDto.getPrpCmainCargoDto().getEndSiteName();
		} else {
			strLindline = " 由 " + policyDto.getPrpCmainCargoDto().getStartSiteName() + " 经 " + policyDto.getPrpCmainCargoDto().getViaSiteName() + " 到 " + policyDto.getPrpCmainCargoDto().getEndSiteName();
		}
	}

	//add by zhulei 20050927  //delete by miaowenjun 20060905
	/*String strMainPolicyNo = "";    //预约保单号  
	BLPrpCmainSub blPrpCmainSub = new BLPrpCmainSub();
	blPrpCmainSub.query("PolicyNo='"+strPolicyNo+"'");
	if(blPrpCmainSub.getSize()>0){
	  strMainPolicyNo = blPrpCmainSub.getArr(0).getMainPolicyNo();
	}
	
	String strRemark = "";        //承运人
	BLPrpCmainCargo blPrpCmainCargo = new BLPrpCmainCargo();
	blPrpCmainCargo.query("PolicyNo='"+strPolicyNo+"'");
	if(blPrpCmainCargo.getSize()>0){
	  strRemark = blPrpCmainCargo.getArr(0).getRemark();
	}*/

	//add by zhulei begin 20050929 运输方式翻译
	if (strConveyance != null && !strConveyance.equals("")) {
		DBPrpDcode dbPrpDcode = new DBPrpDcode();
		dbPrpDcode.getInfo("ConveyanceType", strConveyance);
		strConveyance = dbPrpDcode.getCodeCName();
	}
%>

<script language="javascript">
    function loadForm() {
        tdRegistNo.innerHTML    = '报案号:' + '<%=strRegistNo%>';
        tdInsuredName.innerHTML = '<%=strInsuredName%>';
        tdPolicyNo.innerHTML    = '<%=strPolicyNo%>';
        tdAmount.innerHTML      = '<%=dblSumAmount%>';
        tdConveyance.innerHTML  = '<%=strConveyance%>';
        tdLindline.innerHTML    = '<%=strLindline%>';
        //tdVoyageNo.innerHTML    = '<%=strVoyageNo%>';
        tdStartDate.innerHTML   = '<%=strStartDate1%>';
        tdDamageStartDate.innerHTML = '<%=strStartDate%>';
        tdDamageName.innerHTML  = '<%=strDamageName%>';
        tdEstimateLoss.innerHTML= '<%=strEstimateLoss%>';
        tdDamageAddress.innerHTML = '<%=strDamageAddress%>';
        //tdCheckAgentName.innerHTML = '<%=strCheckAgentName%>';
        
    }
</script>
