<%--
****************************************************************************
* DESC       ：机动车辆保险预付赔款审批表打印初始化
* AUTHOR     ：理赔组
* CREATEDATE ：2004-11-16
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
<%@page import="java.util.*"%>

<%
	//变量声明部分
	String strPreCompensateNo = request.getParameter("PreCompensateNo"); //预赔号
	String strClaimNo = ""; //赔案号
	String strInsuredDate = ""; //保险期间
	String strDamageStartDate = ""; //出险时间
	String strInsureCarFlag = ""; //是否为本保单车辆
	String strCSumPrePaid = ""; //大写预赔金额
	String strUserCode = ((UserDto) request.getSession().getAttribute("user")).getUserCode();
	String strUserName = "";
	String strInputDate = "";
	String strMessage = "";

	//代码翻译变量
	String strCode = "";
	String strName = "";
	boolean isChinese = true; //中文标志

	//对象定义部分                                           
	PrpLregistDto prpLregistDto = null; //RegistDto对象
	PrpLclaimDto prpLclaimDto = null; //ClaimDto对象
	PrpLthirdPartyDto prpLthirdPartyDto = null; //ThirdPartyDto对象

	int intPrepayCount = 0; //PrepayDto对象的记录数
	int intThirdPartyCount = 0; //ThirdPartyDto对象的记录数

	int index = 0;

	//得到ClaimDto,RegistDto,CertainLossDto,PolicyDto对象
	ClaimDto claimDto = (ClaimDto) request.getAttribute("claimDto");
	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");
	RegistDto registDto = (RegistDto) request.getAttribute("registDto");
	PrepayDto prepayDto = (PrepayDto) request.getAttribute("prepayDto");

	UICodeAction uiCodeAction = new UICodeAction();

	//得到prpLclaimDto对象
	prpLclaimDto = claimDto.getPrpLclaimDto();
	strClaimNo = prpLclaimDto.getClaimNo();
	// add by liping 080804
	String configCode = uiCodeAction.translateRiskCodetoConfigCode(prpLclaimDto.getRiskCode());
	double sumAmount = prpLclaimDto.getSumAmount();

	//得到blPrpLthirdParty对象的记录数
	if (registDto.getPrpLthirdPartyDtoList() != null) {
		intThirdPartyCount = registDto.getPrpLthirdPartyDtoList().size();
	}
	//得到prpLregistDto对象
	prpLregistDto = registDto.getPrpLregistDto();

	PrpLprepayDto prpLprepayDto = prepayDto.getPrpLprepayDto();

	//增加交强险预付处理类型add by liping 080804
	String castType = "";
	if ("5".equals(prpLprepayDto.getCaseType())) {
		castType = " ■ 预付 □ 垫付";
	} else {
		castType = " □ 预付 ■ 垫付";
	}
	String estimateLoss = new DecimalFormat("#,##0.00").format(prpLclaimDto.getSumClaim());
	String riskName = uiCodeAction.translateRiskCode(prpLclaimDto.getRiskCode(), isChinese);
	String licenseNo = "";
	String brandName = "";

	//预付、垫付原因
	String prepayReason = "";
	ArrayList prpLptextDtoList = prepayDto.getPrpLptextDtoList();
	if (prpLptextDtoList != null && prpLptextDtoList.size() > 0) {
		for (int i = 0; i < prpLptextDtoList.size(); i++) {
			prepayReason += ((PrpLptextDto) prpLptextDtoList.get(i)).getContext();
			if (prepayReason.length() > 300) {
				prepayReason += "<br>&nbsp;&nbsp;&nbsp;&nbsp;";
			}
		}
	}
	//预付、垫付原因
	String registReason = "";
	ArrayList prpLregistTextDtoList = registDto.getPrpLregistTextDtoList();
	if (prpLregistTextDtoList != null && prpLregistTextDtoList.size() > 0) {
		for (int i = 0; i < prpLregistTextDtoList.size(); i++) {
			registReason += ((PrpLregistTextDto) prpLregistTextDtoList.get(i)).getContext();
			if (prepayReason.length() > 300) {
				registReason += "<br>&nbsp;&nbsp;&nbsp;&nbsp;";
			}
		}
	}
%>

<script language="javascript">
function loadForm() {
	//tdClaimNo.innerHTML = '立案编号：' + '<%=strClaimNo%>';

	//*****报案信息表PrpLregist*****
	//tdEstimateLoss.innerHTML = '<%=new DecimalFormat("#,##0.00").format(prpLregistDto.getEstimateLoss())%>';    
	//*****立案信息表PrpLregist*****
	tdPolicyNo.innerHTML = '<%=StringConvert.encode(prpLclaimDto.getPolicyNo())%>';
	tdInsuredName.innerHTML = '<%=StringConvert.encode(prpLclaimDto.getInsuredName())%>';
	//tdSumAmount.innerHTML   = '<%=new DecimalFormat("#,##0.00").format(prpLclaimDto.getSumAmount())%>';
	<%
	if (configCode.equals("RISKCODE_DAZ")) {
		strInsuredDate = "自 " + prpLclaimDto.getStartDate().getYear() + "年" + prpLclaimDto.getStartDate().getMonth() + "月" + prpLclaimDto.getStartDate().getDate() + "日 零时起" + "至 " + prpLclaimDto.getEndDate().getYear() + "年" + prpLclaimDto.getEndDate().getMonth() + "月" + prpLclaimDto.getEndDate().getDate() + "日 二十四时止";
	} else {
		strInsuredDate = "自 " + prpLclaimDto.getStartDate().getYear() + "年" + prpLclaimDto.getStartDate().getMonth() + "月" + prpLclaimDto.getStartDate().getDate() + "日 零时起" + "<br>" + "<br>" + "至 " + prpLclaimDto.getEndDate().getYear() + "年" + prpLclaimDto.getEndDate().getMonth() + "月" + prpLclaimDto.getEndDate().getDate() + "日 二十四时止";
	} %>

	<% //出险险种
	strCode = "";
	strName = "";
	strCode = StringConvert.encode(prpLclaimDto.getRiskCode());
	strName = uiCodeAction.translateRiskCode(strCode, isChinese); %>
	//tdRiskName.innerHTML = '<%=strName%>';
	<% strDamageStartDate = prpLclaimDto.getDamageStartDate().getYear() + " 年 " + prpLclaimDto.getDamageStartDate().getMonth() + " 月 " + prpLclaimDto.getDamageStartDate().getDate() + " 日";
	String damageAddress = StringConvert.encode(prpLclaimDto.getDamageAddress()); %>

	//*****理赔车辆信息PrpLthirdParty*****
	<%
	if (registDto.getPrpLthirdPartyDtoList() != null) {
		for (index = 0; index < intThirdPartyCount; index++) {
			prpLthirdPartyDto = (PrpLthirdPartyDto) registDto.getPrpLthirdPartyDtoList().get(index);
			strInsureCarFlag = prpLthirdPartyDto.getInsureCarFlag();

			if (strInsureCarFlag.equals("1")) {
				licenseNo = prpLthirdPartyDto.getLicenseNo();
				brandName = prpLthirdPartyDto.getBrandName(); %>

				<% //条款类别
				strCode = "";
				strName = "";
				strCode = StringConvert.encode(prpLthirdPartyDto.getClauseType());
				strName = uiCodeAction.translateCodeCode("ClauseType", strCode, isChinese); %>

				<%
			}
		}
	}

	//*****预赔登记表PrpLprepay*****

	strCSumPrePaid = MoneyUtils.toChinese(prpLprepayDto.getSumPrePaid(), prpLprepayDto.getCurrency());
	strCSumPrePaid = "&nbsp;预付赔款金额（大写人民币）：" + strCSumPrePaid; %>

	<% //填报人 
	strUserName = uiCodeAction.translateUserCode(strUserCode, isChinese);

	//填报时间
	strInputDate = DateTime.current().getYear() + "年" + DateTime.current().getMonth() + "月" + DateTime.current().getDate() + "日"; %>

}
</script>
