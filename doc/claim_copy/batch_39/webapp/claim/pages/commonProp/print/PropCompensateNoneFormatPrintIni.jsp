<%--
****************************************************************************
* DESC       ：财产险赔款计算书打印页面初始化
* AUTHOR     ：理赔组
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
<%
	//变量声明部分
	String strCompensateNo = ""; //赔款计算书号
	String strClaimNo = ""; //赔案号
	String strPolicyNo = ""; //保单号
	double dblSumAmount1 = 0; //保险金额      
	String strKindCode = ""; //险别代码
	String strCause = ""; //出险原因
	String strAddress = ""; //出险地点

	String strCountExp = ""; //赔款计算公式
	String strDamageStartDate = "";
	String strInsuredDate = "";

	//赔款核定
	double dblClaim = 0; //赔款核定
	double dblAgen = 0; //代理费
	double dblCheck = 0; //检验费
	double dblExamine = 0; //查勘费
	double dblRest = 0; //其它费用
	double dblSum = 0; //赔款合计
	double dblEndMoney = 0; //本案已付赔款金额
	double dblTrueMoney = 0; //结案时实付赔款金额

	//代码翻译变量
	String strCode = "";
	String strName = "";
	boolean isChinese = true; //中文标志

	//对象定义部分
	PrpLclaimDto prpLclaimDto = null; //ClaimDto对象
	PrpLcompensateDto prpLcompensateDto = null; //CompensateDto对象
	PrpLctextDto prpLctextDto = null;

	int intCompensateCount = 0; //CompensateDto对象的记录数
	int intItemCarCount = 0; //ItemcarDto对象的记录数
	int intItemKindCount = 0; //ItemKindDto对象的记录数
	int intThirdPartyCount = 0; //ThirdPartyDto对象的记录数
	int intChargeCount = 0; //ChargeDto对象的记录数
	int intCtextCount = 0; //CtextDto对象的记录数
	int intCtextCountTmp = 0; //textarea行数

	int index = 0;

	//得到ClaimDto,RegistDto,CertainLossDto,PolicyDto对象
	CompensateDto compensateDto = (CompensateDto) request
			.getAttribute("compensateDto");
	ClaimDto claimDto = (ClaimDto) request.getAttribute("claimDto");
	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");
	RegistDto registDto = (RegistDto) request.getAttribute("registDto");
	CheckDto checkDto = (CheckDto) request.getAttribute("checkDto");

	CertainLossDto certainLossDto = (CertainLossDto) request
			.getAttribute("certainLossDto");

	UICodeAction uiCodeAction = new UICodeAction();
	//得到prpLcompensateDto 对象
	prpLcompensateDto = compensateDto.getPrpLcompensateDto();
	strCompensateNo = prpLcompensateDto.getCompensateNo();
	//得到prpLclaimDto对象
	prpLclaimDto = claimDto.getPrpLclaimDto();

	strClaimNo = prpLclaimDto.getClaimNo();
	String strApproverCode = prpLcompensateDto.getApproverCode(); //复核员代码
	String strOperatorCode = prpLcompensateDto.getOperatorCode(); //操作员代码
	String strApproverName = uiCodeAction.translateUserCode(
			strApproverCode, isChinese);
	String strOperatorName = uiCodeAction.translateUserCode(
			strOperatorCode, isChinese);

	//得到保单号
	strPolicyNo = prpLclaimDto.getPolicyNo();

	//得到blPrpCitemCar对象 
	if (policyDto.getPrpCitem_carDtoList() != null) {
		intItemCarCount = policyDto.getPrpCitem_carDtoList().size();
	}

	//得到blPrpCitemKind对象的记录数
	if (policyDto.getPrpCitemKindDtoList() != null) {
		intItemKindCount = policyDto.getPrpCitemKindDtoList().size();
	}

	//得到blPrpLcharge对象的记录数
	if (compensateDto.getPrpLchargeDtoList() != null) {
		intChargeCount = compensateDto.getPrpLchargeDtoList().size();
	}
	//得到dbPrpLctext对象的记录数  
	if (compensateDto.getPrpLctextDtoDtoList() != null) {
		intCtextCount = compensateDto.getPrpLctextDtoDtoList().size();
	}
	String strEndMoney = new DecimalFormat("#,##0.00")
			.format(prpLcompensateDto.getSumPrePaid());
	String strCEndMoney = MoneyUtils.toChinese(
			prpLcompensateDto.getSumPrePaid(),
			prpLcompensateDto.getCurrency());
%>
<script language="javascript">
  function loadForm()
  {
    
    //*****赔款计算书表PrpLcompensate*****
    tdCompensateNo.innerHTML = '<%=strCompensateNo%>';

    //*****立案信息表PrpLclaim*****
    tdPolicyNo.innerHTML     = '<%=strPolicyNo%>';
    tdInsuredName.innerHTML  = '<%=StringConvert.encode(prpLclaimDto.getInsuredName())%>';
    tdLossName.innerHTML    = '<%prpLclaimDto.getLossName();%>';
    tdSumAmount.innerHTML = '<%=new DecimalFormat("#,##0.00").format(prpLclaimDto
					.getSumAmount())%>';
<%strDamageStartDate = prpLclaimDto.getDamageStartDate().getYear()
					+ " 年 " + prpLclaimDto.getDamageStartDate().getMonth()
					+ " 月 " + prpLclaimDto.getDamageStartDate().getDate()
					+ " 日";%>                    
    tdDamageStartDate.innerHTML = '<%=strDamageStartDate%>';
<%strInsuredDate = "自 " + prpLclaimDto.getStartDate().getYear() + "年"
					+ prpLclaimDto.getStartDate().getMonth() + "月"
					+ prpLclaimDto.getStartDate().getDate() + "日 零时起" + "<br>"
					+ "<br>" + "至 " + prpLclaimDto.getEndDate().getYear() + "年"
					+ prpLclaimDto.getEndDate().getMonth() + "月"
					+ prpLclaimDto.getEndDate().getDate() + "日 二十四时止";%>                    
    tdInsuredDate.innerHTML = '<%=strInsuredDate%>';
    tdDamageAddress.innerHTML = '<%=prpLclaimDto.getDamageAddress()%>
	';

	}
</script>
