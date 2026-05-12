<%--
****************************************************************************
* DESC       ：机动车辆保险损失情况简易确认书打印初始化
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
	String strRegistNo = ""; //报案号
	String strPolicyNo = ""; //保单号
	String strLicenseNo = ""; //号牌号码
	String strLicenseNo1 = ""; //号牌号码
	String strFrameNo = ""; //发动机号
	String strDamageStartDate = ""; //出险时间
	String strInsureCarFlag = ""; //是否为本保单车辆
	double dblRepSumDefLoss = 0; //换件项目总金额  
	double dblComSumDefLoss = 0; //修理项目总金额  
	double dblProSumReject = 0; //残值作价金额
	String strRepSumDefLoss1 = ""; // 
	String strComSumDefLoss1 = ""; // 
	String strProSumReject1 = ""; // 
	String strRepSumDefLoss2 = ""; // 
	String strComSumDefLoss2 = ""; // 
	String strProSumReject2 = ""; // 
	String strRepContext = ""; //换件项目总金额（中文描述）
	String strComContext = ""; //修理项目总金额（中文描述）
	String strProContext = ""; //残值作价金额（中文描述）
	String strGearboxType = ""; //
	String strGearboxName = ""; //
	String strQuoteCompanyGrade = ""; //
	String strQuoteCompanyName = ""; //
	double dblQuantity = 0;
	double dblManHourFee1 = 0;
	double dblQueryPrice = 0;
	double dblQuotedPrice = 0;
	double dblManHour = 0; //工时
	double dblManHourFee2 = 0; //工时费
	double dblMaterialFee = 0; //材料费
	double dbSum1 = 0;
	double dbSum2 = 0;
	String strMessage = "";
	//add by caopeng start at 2005-12-20
	double dbSumSum2 = 0.0;
	//add by caopeng end at 2005-12-20

	//代码翻译变量

	String strCode = "";
	String strName = "";
	boolean isChinese = true; //中文标志

	//对象定义部分

	PrpCitemCarDto prpItemcarDto = null; //保单的ItemcarDto对象
	PrpLclaimDto prpLclaimDto = null; //ClaimDto对象
	PrpLthirdPartyDto prpLthirdPartyDto = null; //ThirdPartyDto对象
	PrpLcheckDto prpLcheckDto = null; //checkDto对象
	PrpLpropDto prpLpropDto = null; //propDto对象
	PrpLcomponentDto prpLcomponentDto = null; //ComponentDto对象
	PrpLrepairFeeDto prpLrepairFeeDto = null; //RepairFeeDto对象

	int intClaimCount = 0; //ClaimDto对象的记录数
	int intItemCarCount = 0; //Item_carDto对象的记录数
	int intThirdPartyCount = 0; //ThirdPartyDto对象的记录数
	int intCheckCount = 0; //CheckDto对象的记录数
	int intPropCount = 0; //PropDto对象的记录数
	int intComponentCount = 0; //ComponentDto对象的记录数
	int intRepairFeeCount = 0; //RepairFeeDto对象的记录数

	int index = 0;

	//得到ClaimDto,RegistDto,CertainLossDto,PolicyDto对象
	ClaimDto claimDto = (ClaimDto) request.getAttribute("claimDto");
	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");
	RegistDto registDto = (RegistDto) request.getAttribute("registDto");
	CheckDto checkDto = (CheckDto) request.getAttribute("checkDto");
	CertainLossDto certainLossDto = (CertainLossDto) request.getAttribute("certainLossDto");

	UICodeAction uiCodeAction = new UICodeAction();
	//得到保单号
	strPolicyNo = claimDto.getPrpLclaimDto().getPolicyNo();

	strRegistNo = claimDto.getPrpLclaimDto().getRegistNo();

	//得到blPrpCitemCar对象
	if (policyDto.getPrpCitemCarDtoList() != null) {
		intItemCarCount = policyDto.getPrpCitemCarDtoList().size();
	}

	//得到prpLclaimDto对象
	prpLclaimDto = claimDto.getPrpLclaimDto();

	//得到blPrpLthirdParty对象的记录数 
	if (claimDto.getPrpLthirdPartyDtoList() != null) {
		intThirdPartyCount = claimDto.getPrpLthirdPartyDtoList().size();
	}

	//得到check对象
	prpLcheckDto = checkDto.getPrpLcheckDto();

	//得到blPrpLprop对象的记录数  
	if (certainLossDto.getPrpLpropDtoList() != null) {
		intPropCount = certainLossDto.getPrpLpropDtoList().size();
	}

	//得到blPrpLcomponent对象的记录数
	if (certainLossDto.getPrpLcomponentDtoList() != null) {
		intComponentCount = certainLossDto.getPrpLcomponentDtoList().size();
	}

	//得到blPrpLrepairFee对象的记录数
	if (certainLossDto.getPrpLrepairFeeDtoList() != null) {
		intRepairFeeCount = certainLossDto.getPrpLrepairFeeDtoList().size();
	}
%>

<script language="javascript">
function loadForm() {
	tdClaimNo.innerHTML = '报案编号：' + '<%=strRegistNo%>';

	//*****立案信息表PrpLregist*****
	tdPolicyNo.innerHTML = '保险单号：' + '<%=strPolicyNo%>';
	tdInsuredName.innerHTML = '被保险人：' + '<%=StringConvert.encode(prpLclaimDto.getInsuredName())%>'; <% //承保公司
	strCode = "";
	strName = "";
	strCode = StringConvert.encode(prpLclaimDto.getComCode());
	strName = uiCodeAction.translateComCode(strCode, isChinese); %>
		tdComName.innerHTML = '承保公司：' + '<%=strName%>';
	tdSumAmount.innerHTML = '保险金额：' + '<%=new DecimalFormat("#,##0.00").format(prpLclaimDto.getSumAmount())%>'; <% strDamageStartDate = prpLclaimDto.getDamageStartDate().toString();
	//    strDamageStartDate = prpLclaimDto.getDamageStartDate().getYear()
	//                        + " 年 " + prpLclaimDto.getDamageStartDate().getMonth()
	//                        + " 月 " + prpLclaimDto.getDamageStartDate().getDate()
	//                        + " 日";%>                    
	tdDamageStartDate.innerHTML = '出险时间：' + '<%=strDamageStartDate%>';
	tdDamageAddress.innerHTML = '出险地点：' + '<%=StringConvert.encode(prpLclaimDto.getDamageAddress())%>'; <% //事故责任
	strCode = "";
	strName = "";
	strCode = StringConvert.encode(prpLclaimDto.getIndemnityDuty());
	strName = uiCodeAction.translateCodeCode("IndemnityDuty", strCode, isChinese); %>
		tdIndemnityDuty.innerHTML = '事故责任：' + '<%=strName%>';

	//*****理赔车辆信息PrpLthirdParty*****
	<%
	if (claimDto.getPrpLthirdPartyDtoList() != null) {
		for (index = 0; index < intThirdPartyCount; index++) {
			prpLthirdPartyDto = (PrpLthirdPartyDto) claimDto.getPrpLthirdPartyDtoList().get(index);
			strInsureCarFlag = prpLthirdPartyDto.getInsureCarFlag();

			if (strInsureCarFlag.equals("1")) {
				strLicenseNo = prpLthirdPartyDto.getLicenseNo(); %>
					tdLicenseNo.innerHTML = '号牌号码：' + '<%=strLicenseNo%>';
				tdBrandName.innerHTML = '厂牌型号：' + '<%=prpLthirdPartyDto.getBrandName()%>';
				tdEngineNo.innerHTML = '发动机号：' + '<%=prpLthirdPartyDto.getEngineNo()%>'; <% strFrameNo = prpLthirdPartyDto.getFrameNo();

				//条款类别
				strCode = "";
				strName = "";
				strCode = StringConvert.encode(prpLthirdPartyDto.getClauseType());
				strName = uiCodeAction.translateCodeCode("ClauseType", strCode, isChinese); %>
				//tdClauseType.innerHTML= '条款类别：' + '=strName';
				<%
			}
		}
	}

	if (policyDto.getPrpCitemCarDtoList() != null) {
		intItemCarCount = policyDto.getPrpCitemCarDtoList().size();
		//*****机动车险标的信息表PrpCitemCar*****    
		for (index = 0; index < intItemCarCount; index++) {
			prpItemcarDto = (PrpCitemCarDto) policyDto.getPrpCitemCarDtoList().get(index);
			strLicenseNo1 = StringConvert.encode(prpItemcarDto.getLicenseNo());

			if (strLicenseNo1.equals(strLicenseNo)) {
				if (prpItemcarDto.getVINNo() == "") { %>
						tdFrameNo.innerHTML = '车架号码（VIN码）：' + '<%=strFrameNo%>'; <%
				} else { %>
						tdFrameNo.innerHTML = '车架号码（VIN码）：' + '<%=strFrameNo%>' + '（' + '<%=prpItemcarDto.getVINNo()%>' + '）'; <%
				}
			}
		}
	}

	//*****查勘/代查勘信息表PrpLcheck*****    
	//for(index=0;index<intCheckCount;index++)
	//{
	//prpLcheckDto = blClaim.getBLPrpLcheck().getArr(index);%>
	tdCheckDate.innerHTML = '定损时间：' + '<%=prpLcheckDto.getCheckDate()%>';
	tdCheckSite.innerHTML = '定损地点：' + '<%=prpLcheckDto.getCheckSite()%>'; <% //}

	//*****修理费用清单表PrpLrepairFee*****
	dblRepSumDefLoss = 0;
	if (certainLossDto.getPrpLrepairFeeDtoList() != null) {
		intRepairFeeCount = certainLossDto.getPrpLrepairFeeDtoList().size();
		for (index = 0; index < intRepairFeeCount; index++) {
			prpLrepairFeeDto = (PrpLrepairFeeDto) certainLossDto.getPrpLrepairFeeDtoList().get(index);
			dblRepSumDefLoss += prpLrepairFeeDto.getSumDefLoss(); %>
				tdRepairStartDate.innerHTML = '送修时间：' + '<%=prpLrepairFeeDto.getRepairStartDate()%>';
			tdRepairEndDate.innerHTML = '修复竣工时间：' + '<%=prpLrepairFeeDto.getRepairEndDate()%>'; <%
		}
	}

	//*****换件项目清单表PrpLcomponent*****    
	dblComSumDefLoss = 0;
	if (certainLossDto.getPrpLcomponentDtoList() != null) {
		for (index = 0; index < intComponentCount; index++) {
			prpLcomponentDto = (PrpLcomponentDto) certainLossDto.getPrpLcomponentDtoList().get(index);

			dblComSumDefLoss += prpLcomponentDto.getSumDefLoss();

			//变速箱型式
			strGearboxType = prpLcomponentDto.getGearboxType();
			if (strGearboxType.equals("1")) {
				strGearboxName = "手动档";
			} else if (strGearboxType.equals("2")) {
				strGearboxName = "自动档";
			} else {
				strGearboxName = "未知";
			}

			//报价公司
			strQuoteCompanyGrade = prpLcomponentDto.getRepairStartDate().toString();
			if (strQuoteCompanyGrade.equals("1")) {
				strQuoteCompanyName = "总公司";
			} else if (strQuoteCompanyGrade.equals("2")) {
				strQuoteCompanyName = "省公司";
			} else if (strQuoteCompanyGrade.equals("3")) {
				strQuoteCompanyName = "地市公司";
			} else {
				strQuoteCompanyName = "其它";
			} %>
				tdGearboxType.innerHTML = '变速箱型式：' + '<%=strGearboxName%>';
			tdQuoteCompanyGrade.innerHTML = '报价公司：' + '<%=strQuoteCompanyName%>'; <%
		}
	}

	if (certainLossDto.getPrpLpropDtoList() != null) {
		intPropCount = certainLossDto.getPrpLpropDtoList().size();
		//*****财产核定损明细清单表PrpLprop*****    
		for (index = 0; index < intPropCount; index++) {
			prpLpropDto = (PrpLpropDto) certainLossDto.getPrpLpropDtoList().get(index);
			dblProSumReject += prpLpropDto.getSumReject();
		}
	}

	if (dblComSumDefLoss != 0) {
		strComSumDefLoss1 = MoneyUtils.toChinese(dblComSumDefLoss, prpLcomponentDto.getCurrency());
	}
	if (dblRepSumDefLoss != 0) {
		strRepSumDefLoss1 = MoneyUtils.toChinese(dblRepSumDefLoss, prpLrepairFeeDto.getCurrency());
	}
	if (dblProSumReject != 0) {
		strProSumReject1 = MoneyUtils.toChinese(dblProSumReject, prpLpropDto.getCurrency());
	}

	strRepSumDefLoss2 = "（￥：" + DataUtils.zeroToEmpty(dblRepSumDefLoss);
	strComSumDefLoss2 = "（￥：" + DataUtils.zeroToEmpty(dblComSumDefLoss);
	strProSumReject2 = "（￥：" + DataUtils.zeroToEmpty(dblProSumReject);

	strRepContext = "修理费总计金额：（人民币大写） " + strRepSumDefLoss1;
	strComContext = "换件项目共计 " + DataUtils.zeroToEmpty(intComponentCount) + " 项，总计金额：（人民币大写） " + strComSumDefLoss1;
	strProContext = "残值作价金额：（人民币大写） " + strProSumReject1; %>
	tdRepSumDefLoss.innerHTML = '<%=strRepSumDefLoss2%>';
	tdComSumDefLoss.innerHTML = '<%=strComSumDefLoss2%>';
	tdProSumReject.innerHTML = '<%=strProSumReject2%>';
	tdRepContext.innerHTML = '<%=strRepContext%>';
	tdComContext.innerHTML = '<%=strComContext%>';
	tdProContext.innerHTML = '<%=strProContext%>';
}
</script>
 