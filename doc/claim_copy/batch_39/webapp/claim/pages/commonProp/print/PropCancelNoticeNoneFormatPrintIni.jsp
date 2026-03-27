<%--
****************************************************************************
* DESC       ：财产险赔款计算书打印页面初始化
* AUTHOR     ：理赔组
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@page errorPage="UIErrorPage"%>
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
	String strCompensateNo = request.getParameter("CompensateNo"); //赔款计算书号
	if (strCompensateNo == null)
		strCompensateNo = "";
	String strClaimNo = ""; //赔案号
	String strPolicyNo = ""; //保单号
	double dblSumAmount1 = 0; //保险金额      
	String strKindCode = ""; //险别代码
	String strCause = ""; //出险原因
	String strAddress = ""; //出险地点
	String strDamageStartDate = ""; //出险时间
	String strCountExp = ""; //赔款计算公式
	String strDealerCode = "";
	String strContext1 = "";
	String strClaimText = "";

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
	PrpLregistTextDto prpLregistTextDto = null;
	PrpLregistDto prpLregistDto = null;

	int intCompensateCount = 0; //CompensateDto对象的记录数
	int intItemCarCount = 0; //ItemcarDto对象的记录数
	int intItemKindCount = 0; //ItemKindDto对象的记录数
	int intThirdPartyCount = 0; //ThirdPartyDto对象的记录数
	int intChargeCount = 0; //ChargeDto对象的记录数
	int intCtextCount = 0; //CtextDto对象的记录数
	int intCtextCountTmp = 0; //textarea行数

	int index = 0;

	//得到ClaimDto对象
	ClaimDto claimDto = (ClaimDto) request.getAttribute("claimDto");
	RegistDto registDto = (RegistDto) request.getAttribute("registDto");
	UICodeAction uiCodeAction = new UICodeAction();
	//得到prpLclaimDto对象
	prpLclaimDto = claimDto.getPrpLclaimDto();
%>
<script language="javascript">
  function loadForm()
  {
    
    //*****赔款计算书表PrpLcompensate*****
    tdCompensateNo.innerHTML  = '<%=strCompensateNo%>';

    //*****立案信息表PrpLclaim*****
    tdPolicyNo.innerHTML     = '<%=strPolicyNo%>';
    tdInsuredName.innerHTML  = '<%=StringConvert.encode(prpLclaimDto.getInsuredName())%>';
    tdRiskCode.innerHTML  ='<%=prpLclaimDto.getRiskCode()%>';
    tdLossName.innerHTML    = '<%=prpLclaimDto.getLossName()%>';
    tdSumAmount.innerHTML = '<%=new DecimalFormat("#,##0.00").format(prpLclaimDto
					.getSumAmount())%>';
<%strDamageStartDate = prpLclaimDto.getDamageStartDate().getYear()
					+ " 年 " + prpLclaimDto.getDamageStartDate().getMonth()
					+ " 月 " + prpLclaimDto.getDamageStartDate().getDate()
					+ " 日";%>                    
    tdDamageStartDate.innerHTML = '<%=strDamageStartDate%>';
    tdDamageAddress.innerHTML = '<%=prpLclaimDto.getDamageAddress()%>';
    tdCancelReason.innerHTML = '<%=prpLclaimDto.getCancelReason()%>';
<%if (registDto.getPrpLregistTextDtoList() != null) {
				int intSizeTemp = registDto.getPrpLregistTextDtoList().size();
				if (intSizeTemp > 0) {
					for (int i = 0; i < intSizeTemp; i++) {
						prpLregistTextDto = (PrpLregistTextDto) registDto
								.getPrpLregistTextDtoList().get(i);

						if (!prpLregistTextDto.getContext().trim().equals("")) {
							strContext1 += StringConvert
									.encode(prpLregistTextDto.getContext())
									+ "\\r\\n";
						}
					}
					if (!strContext1.trim().equals("")) {
						strClaimText += strContext1;

					}
				}
			}%>
    tdRegistText.innerHTML = '<%=strClaimText%>
	';
	}
</script>
