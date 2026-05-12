<%--
****************************************************************************
* DESC       ：机动车辆保险结案催告、注销通知书打印初始化
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

<%
 	//变量声明部分
 	String strClaimNo = request.getParameter("ClaimNo"); //赔案号
 	String strPolicyNo = ""; //保单号
 	String strInsuredDate = ""; //保险期间
 	String strDamageStartDate = ""; //出险时间
 	String strInsureCarFlag = ""; //是否为本保单车辆
 	double dblSumAmount1 = 0; //保险金额    
 	double dblSumAmount2 = 0; //责任限额    
 	String strRiskCode = ""; //险种代码     
 	String strKindCode = ""; //险别代码     
 	String strCalculateFlag = ""; //是否记入保额
 	String strUserCode = ((UserDto) request.getSession().getAttribute("user")).getUserCode();
 	String strMessage = "";

 	//代码翻译变量
 	String strCode = "";
 	String strName = "";
 	boolean isChinese = true; //中文标志

 	//对象定义部分                                             
 	PrpCitemKindDto prpItemKindDto = null; //保单的ItemKindDto对象
 	PrpLregistDto prpLregistDto = null; //RegistDto对象
 	PrpLclaimDto prpLclaimDto = null; //ClaimDto对象
 	PrpLthirdPartyDto prpLthirdPartyDto = null; //ThirdPartyDto对象

 	int intClaimCount = 0; //ClaimDto对象的记录数
 	int intItemKindCount = 0; //ItemKindDto对象的记录数
 	int intThirdPartyCount = 0; //ThirdPartyDto对象的记录数

 	int index = 0;

 	//得到ClaimDto,RegistDto,CertainLossDto,PolicyDto对象
 	ClaimDto claimDto = (ClaimDto) request.getAttribute("claimDto");
 	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");
 	RegistDto registDto = (RegistDto) request.getAttribute("registDto");

 	UICodeAction uiCodeAction = new UICodeAction();
 	//得到prpLclaimDto对象
 	prpLclaimDto = claimDto.getPrpLclaimDto();

 	//得到保单号  
 	strPolicyNo = prpLclaimDto.getPolicyNo();

 	//得到blPrpCitemKind对象的记录数
 	if (policyDto.getPrpCitemKindDtoList() != null) {
 		intItemKindCount = policyDto.getPrpCitemKindDtoList().size();
 	}

 	//得到blPrpLthirdParty对象的记录数
 	if (claimDto.getPrpLthirdPartyDtoList() != null) {
 		intThirdPartyCount = claimDto.getPrpLthirdPartyDtoList().size();
 	}

 	//得到prpLregistDto对象
 	prpLregistDto = registDto.getPrpLregistDto();
 %>

<script language="javascript">
function loadForm() {
	tdClaimNo.innerHTML = '立案编号：' + '<%=strClaimNo%>';

	//*****报案信息表PrpLregist*****
	tdEstimateLoss.innerHTML = '<%=new DecimalFormat("#,##0.00").format(prpLregistDto.getEstimateLoss())%>' + ' 元';

	//*****立案信息表PrpLregist*****
	tdPolicyNo.innerHTML = '<%=StringConvert.encode(prpLclaimDto.getPolicyNo())%>';
	tdInsuredName.innerHTML = '<%=StringConvert.encode(prpLclaimDto.getInsuredName())%>';
	tdInsuredName1.innerHTML = '<%=StringConvert.encode(prpLclaimDto.getInsuredName())%>' + '（被保险人名称）：'; <% strInsuredDate = "自 " + prpLclaimDto.getStartDate().getYear() + "年" + prpLclaimDto.getStartDate().getMonth() + "月" + prpLclaimDto.getStartDate().getDate() + "日 零时起" + "<br>" + "<br>" + "至 " + prpLclaimDto.getEndDate().getYear() + "年" + prpLclaimDto.getEndDate().getMonth() + "月" + prpLclaimDto.getEndDate().getDate() + "日 二十四时止"; %>
		tdInsuredDate.innerHTML = '<%=strInsuredDate%>'; <% //出险险种
	strCode = "";
	strName = "";
	strCode = StringConvert.encode(prpLclaimDto.getRiskCode());
	strName = uiCodeAction.translateRiskCode(strCode, isChinese); %>
		tdRiskName.innerHTML = '<%=strName%>'; <% strDamageStartDate = prpLclaimDto.getDamageStartDate().getYear() + " 年 " + prpLclaimDto.getDamageStartDate().getMonth() + " 月 " + prpLclaimDto.getDamageStartDate().getDate() + " 日"; %>
		tdDamageStartDate.innerHTML = '<%=strDamageStartDate%>';
	tdDamageAddress.innerHTML = '<%=StringConvert.encode(prpLclaimDto.getDamageAddress())%>';

	//*****理赔车辆信息PrpLthirdParty*****
	<%
	if (claimDto.getPrpLthirdPartyDtoList() != null) {
		for (index = 0; index < intThirdPartyCount; index++) {
			prpLthirdPartyDto = (PrpLthirdPartyDto) claimDto.getPrpLthirdPartyDtoList().get(index);
			strInsureCarFlag = prpLthirdPartyDto.getInsureCarFlag();

			if (strInsureCarFlag.equals("1")) { %>
					tdLicenseNo.innerHTML = '<%=prpLthirdPartyDto.getLicenseNo()%>';
				tdBrandName.innerHTML = '<%=prpLthirdPartyDto.getBrandName()%>'; <%
			}
		}
	}

	//*****标的子险信息PrpTitemKind*****
	dblSumAmount1 = 0;
	dblSumAmount2 = 0;

	if (policyDto.getPrpCitemKindDtoList() != null) {
		for (index = 0; index < intItemKindCount; index++) {
			prpItemKindDto = (PrpCitemKindDto) policyDto.getPrpCitemKindDtoList().get(index);
			strCalculateFlag = prpItemKindDto.getCalculateFlag();
			if (strCalculateFlag.equals("Y")) {
				dblSumAmount1 += prpItemKindDto.getAmount();
			}
			if (strCalculateFlag.equals("N")) {
				dblSumAmount2 += prpItemKindDto.getAmount();
			}
		}
	} %>
		tdSumAmount1.innerHTML = '<%=new DecimalFormat("#,##0.00").format(dblSumAmount1)%>';
	tdSumAmount2.innerHTML = '<%=new DecimalFormat("#,##0.00").format(dblSumAmount2)%>';
}
</script>
 