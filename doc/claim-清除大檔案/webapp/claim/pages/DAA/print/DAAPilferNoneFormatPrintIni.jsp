<%--
****************************************************************************
* DESC       ：机动车辆出险(盗抢)证明打印初始化
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
<%@page import="java.util.ArrayList"%>

<%
	//变量声明部分
	String strClaimNo = request.getParameter("ClaimNo"); //赔案号
	String strRegistNo = ""; //报案号
	String strPolicyNo = ""; //保单号
	String strLicenseNo = ""; //保单中的号牌号码
	String strLicenseColorCode = ""; //保单中的号牌底色
	String strLicenseNo1 = ""; //理赔车辆信息中的号牌号码
	String strLicenseColorCode1 = ""; //理赔车辆信息中的号牌底色
	String strLicenseNo2 = ""; //号牌号码
	String strFrameNo = ""; //发动机号
	String strInsuredFlag = ""; //判断是投保人还是被保险人
	String strEnrollDate = ""; //初次登记日期
	String strReceiveLicenseDate = ""; //初次领证日期
	String strInsureCarFlag = ""; //是否为本保单车辆
	String strTextType = ""; //报案文字
	String strRegistText = ""; //出险信息
	String strDamageStartDate = ""; //出险时间
	String strMessage = "";

	//代码翻译变量
	String strCode = "";
	String strName = "";
	boolean isChinese = true; //中文标志

	//对象定义部分

	PrpCmainDto prpMainDto = null; //保单的MainDto对象
	PrpCinsuredDto prpInsuredDto = null; //保单的InsuredDto对象
	PrpCitemCarDto prpItemcarDto = null; //保单的ItemcarDto对象
	PrpLregistDto prpLregistDto = null; //RegistDto对象
	PrpLregistTextDto prpLregistTextDto = null; //RegistTextDto对象
	PrpLclaimDto prpLclaimDto = null; //ClaimDto对象
	PrpLdriverDto prpLdriverDto = null; //DriverDto对象
	PrpLthirdPartyDto prpLthirdPartyDto = null; //ThirdPartyDto对象

	int intClaimCount = 0; //ClaimDto对象的记录数
	int intInsuredCount = 0; //InsuredDto对象的记录数
	int intItemCarCount = 0; //ItemCarDto对象的记录数
	int intDriverCount = 0; //DriverDto对象的记录数
	int intThirdPartyCount = 0; //ThirdPartyDto对象的记录数
	int intRegistTextCount = 0; //RegistTextDto对象的记录数

	int index = 0;
	//得到PolicyDto,ClaimDto,RegistDto对象
	RegistDto registDto = (RegistDto) request.getAttribute("registDto");
	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");
	ClaimDto claimDto = (ClaimDto) request.getAttribute("claimDto");

	UICodeAction uiCodeAction = new UICodeAction();

	//得到保单号
	strPolicyNo = claimDto.getPrpLclaimDto().getPolicyNo();

	//得到prpMainDto对象
	prpMainDto = policyDto.getPrpCmainDto();

	//得到PrpCinsured对象的记录数  
	if (policyDto.getPrpCinsuredDtoList() != null) {
		intInsuredCount = policyDto.getPrpCinsuredDtoList().size();
	}
	//得到prpItemcarDto对象
	if (policyDto.getPrpCitemCarDtoList() != null) {
		intItemCarCount = policyDto.getPrpCitemCarDtoList().size();
	}
	//得到PrpLthirdParty对象的记录数
	if (claimDto.getPrpLthirdPartyDtoList() != null) {
		intThirdPartyCount = claimDto.getPrpLthirdPartyDtoList().size();
	}

	//得到PrpLdriver对象的记录数
	if (claimDto.getPrpLdriverDtoList() != null) {
		intDriverCount = claimDto.getPrpLdriverDtoList().size();
	}

	//得到prpLclaimDto对象
	prpLclaimDto = claimDto.getPrpLclaimDto();

	//得到prpLregistDto对象
	prpLregistDto = registDto.getPrpLregistDto();

	//得到blPrpLregistText对象的记录数
	if (registDto.getPrpLregistTextDtoList() != null) {
		intRegistTextCount = registDto.getPrpLregistTextDtoList().size();
	}
%>
 
<script language="javascript">
function loadForm() {
 	tdRegistNo.innerHTML = '报案编号：' + '<%=StringConvert.encode(prpLclaimDto.getRegistNo())%>';
 	tdClaimNo.innerHTML = '立案编号：' + '<%=strClaimNo%>';

 	//*****报案信息表PrpLregist*****
 	tdLinkerPostCode.innerHTML = '邮政编码：' + '<%=StringConvert.encode(prpLregistDto.getLinkerPostCode())%>';

 	//*****投保单基本信息表PrpCmain*****
 	<% //操作员
 	strCode = "";
 	strCode = StringConvert.encode(prpMainDto.getHandlerCode());
 	strName = uiCodeAction.translateUserCode(strCode, isChinese); %>
 		tdLinkerName1.innerHTML = '联系人：' + '<%=strName%>';

 	//*****立案信息表PrpLclaim*****
 	tdInsuredName.innerHTML = '被保险人：' + '<%=StringConvert.encode(prpLclaimDto.getInsuredName())%>'; <% String strDateTemp = "";
 	if (prpLclaimDto.getDamageStartHour() != null) {
 		if (prpLclaimDto.getDamageStartHour().length() < 2) {
 			strDateTemp = prpLclaimDto.getDamageStartHour();
 		} else {
 			strDateTemp = prpLclaimDto.getDamageStartHour().substring(0, 2);
 		}
 	}
 	strDamageStartDate = prpLclaimDto.getDamageStartDate().getYear() + " 年 " + prpLclaimDto.getDamageStartDate().getMonth() + " 月 " + prpLclaimDto.getDamageStartDate().getDate() + " 日 " + strDateTemp + " 时"; %>
 		tdDamageStartDate.innerHTML = '出险时间：' + '<%=strDamageStartDate%>';
 	tdDamageAddress.innerHTML = '出险地点：' + '<%=StringConvert.encode(prpLclaimDto.getDamageAddress())%>';

 	//*****保险关系人表PrpCinsured*****
 	<%
 	if (policyDto.getPrpCinsuredDtoList() != null) {
 		ArrayList listTemp = policyDto.getPrpCinsuredDtoList();
 		for (index = 0; index < intInsuredCount; index++) {
 			prpInsuredDto = (PrpCinsuredDto) listTemp.get(index);
 			strInsuredFlag = prpInsuredDto.getInsuredFlag();

 			if (strInsuredFlag.equals("1")) //被保险人
 			{ %>
 					tdLinkerName.innerHTML = '联系人：' + '<%=StringConvert.encode(prpInsuredDto.getLinkerName())%>';
 				tdPhoneNumber.innerHTML = '联系电话：' + '<%=StringConvert.encode(prpInsuredDto.getPhoneNumber())%>'; <%
 			}
 		}
 	}

 	if (claimDto.getPrpLthirdPartyDtoList() != null) {
 		//*****理赔车辆信息PrpLthirdParty*****  
 		for (index = 0; index < intThirdPartyCount; index++) {
 			prpLthirdPartyDto = (PrpLthirdPartyDto) claimDto.getPrpLthirdPartyDtoList().get(index);
 			strInsureCarFlag = prpLthirdPartyDto.getInsureCarFlag();

 			if (strInsureCarFlag.equals("1")) {
 				//承保公司
 				strCode = "";
 				strName = "";
 				UICompanyAction uiCompanyAction = new UICompanyAction();

 				strCode = StringConvert.encode(prpLthirdPartyDto.getInsureComCode());
 				//dbPrpDcompany.getInfo(strCode); 
 				strName = uiCodeAction.translateComCode(strCode, isChinese);
 				PrpDcompanyDto prpDcompanyDto = uiCompanyAction.findByPrimaryKey(strCode);
 				if (prpDcompanyDto == null)
 					prpDcompanyDto = new PrpDcompanyDto(); %>
 					tdComName.innerHTML = '承保公司：' + '<%=strName%>';
 				tdPhoneNumber1.innerHTML = '联系电话：' + '<%=prpDcompanyDto.getPhoneNumber()%>'; <% strLicenseNo = StringConvert.encode(prpLthirdPartyDto.getLicenseNo());
 				strLicenseColorCode = StringConvert.encode(prpLthirdPartyDto.getLicenseColorCode()); %>
 					tdLicenseNo.innerHTML = '号牌号码：' + '<%=StringConvert.encode(prpLthirdPartyDto.getLicenseNo())%>';
 				tdBrandName.innerHTML = '厂牌型号：' + '<%=StringConvert.encode(prpLthirdPartyDto.getBrandName())%>';
 				tdEngineNo.innerHTML = '发动机号：' + '<%=StringConvert.encode(prpLthirdPartyDto.getEngineNo())%>'; <% strFrameNo = StringConvert.encode(prpLthirdPartyDto.getFrameNo());
 			}
 		}
 	}

 	if (policyDto.getPrpCitemCarDtoList() != null) {
 		//*****机动车险标的信息表PrpCitemCar*****    
 		for (index = 0; index < intItemCarCount; index++) {
 			prpItemcarDto = (PrpCitemCarDto) policyDto.getPrpCitemCarDtoList().get(index);
 			strLicenseNo2 = StringConvert.encode(prpItemcarDto.getLicenseNo());

 			if (strLicenseNo2.equals(strLicenseNo)) {
 				//车身颜色
 				strCode = "";
 				strName = "";
 				strCode = StringConvert.encode(prpItemcarDto.getColorCode());
 				strName = uiCodeAction.translateCodeCode("ColorCode", strCode, true); %>
 					tdColorCode.innerHTML = '车身颜色：' + '<%=strName%>';
 				tdSeatCount.innerHTML = '座位/吨位：' + '<%=prpItemcarDto.getSeatCount()%>' + '/' + '<%=new DecimalFormat("0.00").format(prpItemcarDto.getTonCount() / 1000)%>'; <%
 				if (StringConvert.encode(prpItemcarDto.getVINNo()) == "") { %>
 						tdFrameNo.innerHTML = '车架号（VIN）：' + '<%=strFrameNo%>'; <%
 				} else { %>
 						tdFrameNo.innerHTML = '车架号（VIN）：' + '<%=strFrameNo%>' + '（' + '<%=StringConvert.encode(prpItemcarDto.getVINNo())%>' + '）'; <%
 				}
 				//初次登记日期
 				strEnrollDate = prpItemcarDto.getEnrollDate().toString();
 				if ((strEnrollDate == null) || (strEnrollDate.trim().equals(""))) {
 					strEnrollDate = "";
 				} else {
 					strEnrollDate = prpItemcarDto.getEnrollDate().getYear() + " 年 " + prpItemcarDto.getEnrollDate().getMonth() + " 月 ";
 				} %>
 					tdEnrollDate.innerHTML = '车辆初次登记日期：' + '<%=strEnrollDate%>';
 				tdPurchasePrice.innerHTML = '新车购置价：' + '<%=new DecimalFormat("#,##0.00").format(prpItemcarDto.getPurchasePrice())%>' + '元'; <%
 			}
 		}
 	} %>
 	//*****车险驾驶员信息表PrpLdriver*****
 	tdDriverName.innerHTML = '驾驶员姓名：';
 	tdDrivingLicenseNo.innerHTML = '驾驶证号码：';
 	tdReceiveLicenseDate.innerHTML = '初次领证日期：';
 	tdDrivingCarType.innerHTML = '准驾车型：'; <%
 	if (claimDto.getPrpLdriverDtoList() != null) {
 		for (index = 0; index < intDriverCount; index++) {
 			prpLdriverDto = (PrpLdriverDto) claimDto.getPrpLdriverDtoList().get(index);
 			strLicenseNo1 = prpLdriverDto.getLicenseNo();
 			strLicenseColorCode1 = prpLdriverDto.getLicenseColorCode();

 			if (strLicenseNo1.equals(strLicenseNo) && strLicenseColorCode1.equals(strLicenseColorCode)) { %>
 					tdDriverName.innerHTML = '驾驶员姓名：' + '<%=StringConvert.encode(prpLdriverDto.getDriverName())%>';
 				tdDrivingLicenseNo.innerHTML = '驾驶证号码：' + '<%=StringConvert.encode(prpLdriverDto.getDrivingLicenseNo())%>'; <% //初次登记日期
 				strReceiveLicenseDate = prpLdriverDto.getReceiveLicenseDate().toString();
 				if ((strReceiveLicenseDate == null) || (strReceiveLicenseDate.trim().equals(""))) {
 					strReceiveLicenseDate = "";
 				} else {
 					strReceiveLicenseDate = prpLdriverDto.getReceiveLicenseDate().getYear() + " 年 " + prpLdriverDto.getReceiveLicenseDate().getMonth() + " 月 ";
 				} %>
 					tdReceiveLicenseDate.innerHTML = '初次领证日期：' + '<%=strReceiveLicenseDate%>';
 				tdDrivingCarType.innerHTML = '准驾车型：' + '<%=StringConvert.encode(prpLdriverDto.getDrivingCarType())%>'; <%
 			}
 		}
 	}

 	strRegistText = "";
 	if (registDto.getPrpLregistTextDtoList() != null) {
 		//*****报案文字表PrpLregistText*****
 		for (index = 0; index < intRegistTextCount; index++) {
 			prpLregistTextDto = (PrpLregistTextDto) registDto.getPrpLregistTextDtoList().get(index);
 			strTextType = prpLregistTextDto.getTextType();

 			if (strTextType.equals("1")) {
 				strRegistText += StringConvert.encode(prpLregistTextDto.getContext()) + "\\r\\n";
 			}
 		}

 		//检查报案文字打出来是几行
 		int intRegistTextCountTmp1 = 0; //textarea的行数
 		int intRegistTextCountTmp2 = 0; //textarea的行数
 		String strRegistTextTmp = ""; //带"更多内容，请详见清单..."的文字

 		for (index = 0; index < strRegistText.length(); index++) {
 			if (strRegistText.substring(index, index + 1).equals("\\")) {
 				if (!(strRegistText.substring(index).length() < 4)) {
 					if (strRegistText.substring(index, index + 4).equals("\\r\\n")) {
 						intRegistTextCountTmp1 += 1; //只要有回车换行，intRegistTextCountTmp1+1
 						intRegistTextCountTmp2 += 1; //只要有回车换行，intRegistTextCountTmp2+1
 						if (intRegistTextCountTmp2 == 9) {
 							strRegistTextTmp = strRegistText.substring(0, index + 4) //9行的strRegistText + "更多内容，请详见清单..."
 							+ "更多内容，请详见清单...";
 						}
 					}
 				}
 			}
 		}

 		int x = 0;
 		int y = 0;
 		if (!(strRegistText.length() < 4)) //如果strRegistText.length()>=4，判断strRegistText结尾是文字，还是回车换行
 		{
 			x = strRegistText.length() - 4;
 			y = strRegistText.length();
 			if (!strRegistText.substring(x, y).equals("\\r\\n")) {
 				intRegistTextCountTmp1 += 1;
 			}
 		} else
 		//如果strRegistText不足1行，intRegistTextCountTmp1 = 1; 
 			intRegistTextCountTmp1 = 1;

 		//如果intRegistTextCountTmp1>10，则strRegistText = 9行的strRegistText + "更多内容，请详见清单..."
 		if (intRegistTextCountTmp1 > 10) {
 			strRegistText = strRegistTextTmp;
 		}
 	}
 	String strReportor = prpLregistDto.getReportorName();
 	String strReportType = uiCodeAction.translateCodeCode("ReportType", prpLregistDto.getReportType(), true);

 	strRegistText = strRegistText + "<br>" + "&nbsp;&nbsp;报案人：" + strReportor + "<br>&nbsp;&nbsp;报案时间：" + strDamageStartDate + "<br>&nbsp;&nbsp;报案方式：" + strReportType; %>
 		tdRegistText.innerHTML = '<%=strRegistText%>';
 }
</script>
