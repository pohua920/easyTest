<%--
****************************************************************************
* DESC       ：理赔审核书初始化
* AUTHOR     ：caopeng
* CREATEDATE ：2005-12-14
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
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
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.sinosoft.claim.bl.facade.BLPrpCcoinsFacade"%>

<%
	//变量声明部分
	boolean buttonFlag = false;
	String strCompensateNo = request.getParameter("CompensateNo"); //理赔审核书号
	String strClaimNo = ""; //赔案号
	String strRegistNo = ""; //报案号
	String strPolicyNo = ""; //保单号
	String strTextType = "";
	String strContext = ""; //赔款审核书文字
	String strAcciName = ""; //事故人姓名
	String strSexCode = ""; //性别代码
	String strAcciSex = ""; //事故人性别
	String strAcciAge = ""; //事故人年龄
	String strAcciIDCardNo = ""; //事故人身份证号
	String strAcciDate = ""; //事故时间
	String strDamageTypeName = ""; //事故类型
	String strClaimTypeName = ""; //事故性质
	String strDamageName = ""; //事故原因
	String strInsuredDate = ""; //保险期限

	//add by zhangyingrui start at 20060831
	String strRegistTextContext = ""; //事故经过、结果及其现状
	double sumrealpay1 = 0.00; //意外事故
	double sumrealpay2 = 0.00; //意外残疾
	double sumrealpay3 = 0.00; //意外医疗
	double sumrealpay4 = 0.00; //重大疾病
	double sumrealpay5 = 0.00; //住院医疗
	double sumrealpay6 = 0.00; //住院补贴
	double sumrest = 0.00; //自费金额
	double chargeAmoutcheck = 0.00; //查勘费用
	double sumDutyPaid = 0.00;//实际给付金额、结案总金额
	String strChargeAmoutcheck = "";
	String strCChargeAmoutcheck = "";
	String strSumDutyPaid = "";
	String strCSumDutyPaid = "";

	double sumPaid1 = 0.00; //给付项目及金额合计
	double sumPaid2 = 0.00; //扣款项目及金额合计

	double sumloss = 0.00; //申请金额
	double deductible = 0.00; //免赔额
	double claimrate = 0.00; //责任比例
	double sumrealpay = 0.00; //给付金额
	String strCompensate = "";//计算公式
	double dblSumprepaid = 0; //预付赔款 add by liping 080811
	String strSumprepaid = ""; //预付赔款 add by liping 080811

	//add by zhangyingrui end at 20060831 

	double dblSumAmount = 0; //保险金额 

	//代码翻译变量
	String strCode = "";
	String strName = "";
	boolean isChinese = true; //中文标志

	//对象定义部分

	PrpLclaimDto prpLclaimDto = null; //ClaimDto对象
	PrpLcompensateDto prpLcompensateDto = null; //CompensateDto对象
	PrpLchargeDto prpLchargeDto = null; //ChargeDto对象
	PrpLctextDto prpLctextDto = null;
	PrpLregistDto prpLregistDto = null;
	PrpLpropDto prpLpropDto = null;
	PrpLextDto prpLextDto = null;
	PrpLlossDto prpLlossDto = null;
	PrpLpersonLossDto prpLpersonLossDto = null;

	int index = 0;

	ClaimDto claimDto = (ClaimDto) request.getAttribute("claimDto");
	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");
	RegistDto registDto = (RegistDto) request.getAttribute("registDto");
	CheckDto checkDto = (CheckDto) request.getAttribute("checkDto");
	CompensateDto compensateDto = (CompensateDto) request.getAttribute("compensateDto");
	CertainLossDto certainLossDto = (CertainLossDto) request.getAttribute("certainLossDto");

	EndorseDto endorseDto = (EndorseDto) request.getAttribute("endorseDto");

	UICodeAction uiCodeAction = new UICodeAction();
	//得到prpLcompensateDto 对象
	prpLcompensateDto = compensateDto.getPrpLcompensateDto();

	//add by zhangyingrui start at 20060831  原因：得到审核批文对象
	/*ArrayList prpLctextDtoList = compensateDto.getPrpLctextDtoDtoList();
	if ( prpLctextDtoList !=null && prpLctextDtoList.size()>0){
				for (Iterator iter = prpLctextDtoList.iterator(); iter.hasNext();) {
					PrpLctextDto element = (PrpLctextDto) iter.next();
					if("1".equals(element.getTextType())){
						strContext = strContext + element.getContext();
						}
				}
	}*/

	//得到计算公式的信息
	String tempContext = "";

	if (compensateDto.getPrpLctextDtoDtoList() != null) {
		//*****赔款计算文字表PrpLctext*****//
		int intCtextCount = compensateDto.getPrpLctextDtoDtoList().size();
		for (index = 0; index < intCtextCount; index++) {
			prpLctextDto = (PrpLctextDto) compensateDto.getPrpLctextDtoDtoList().get(index);
			strTextType = StringConvert.encode(prpLctextDto.getTextType());
			if (strTextType.charAt(0) == '1') {
				strContext = strContext + StringConvert.encode(prpLctextDto.getContext());
			}

			if (strTextType.charAt(0) == '5') {
				tempContext = tempContext + StringConvert.encode(prpLctextDto.getContext());
			}
		}
	}

	//检查计算书文字打出来是几行
	int intTempContextCountTmp = 0; //textarea的行数
	//    String la="\\";
	//    String lala="\\r\\n";

	for (index = 0; index < tempContext.length(); index++) {
		if (tempContext.substring(index, index + 1).equals("\\")) {
			if (!(tempContext.substring(index).length() < 4)) {
				if (tempContext.substring(index, index + 4).equals("\\r\\n")) {
					intTempContextCountTmp += 1; //只要有回车换行，intTempContextCountTmp+1
				}
			}
		}
	}

	int x1 = 0;
	int y1 = 0;
	if (!(tempContext.length() < 4)) //如果tempContext.length()>=4，判断tempContext结尾是文字，还是回车换行
	{
		x1 = tempContext.length() - 4;
		y1 = tempContext.length();
		if (!tempContext.substring(x1, y1).equals("\\r\\n")) {
			intTempContextCountTmp += 1;
		}
	} else
		//如果tempContext不足1行，intTempContextCountTmp = 1; 
		intTempContextCountTmp = 1;

	if (intTempContextCountTmp > 12) {
		tempContext = "<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;理算过程请详见清单。";
		//add by liuwei at 2011-03-04 控制打印附页按钮可用 start
		buttonFlag = true;
		//add by liuwei at 2011-03-04 控制打印附页按钮可用 end
	}
	while (tempContext.indexOf("\\r\\n") != -1)
		tempContext = tempContext.substring(0, tempContext.indexOf("\\r\\n")) + "<br>" + tempContext.substring(tempContext.indexOf("\\r\\n") + "\\r\\n".length());

	//==================================
	//检查审核文字打出来是几行
	int intCtextCountTmp = 0; //textarea的行数
	//    String la="\\";
	//    String lala="\\r\\n";

	for (index = 0; index < strContext.length(); index++) {
		if (strContext.substring(index, index + 1).equals("\\")) {
			if (!(strContext.substring(index).length() < 4)) {
				if (strContext.substring(index, index + 4).equals("\\r\\n")) {
					intCtextCountTmp += 1; //只要有回车换行，intCtextCountTmp+1
				}
			}
		}
	}

	int x = 0;
	int y = 0;
	if (!(strContext.length() < 4)) //如果strContext.length()>=4，判断strContext结尾是文字，还是回车换行
	{
		x = strContext.length() - 4;
		y = strContext.length();
		if (!strContext.substring(x, y).equals("\\r\\n")) {
			intCtextCountTmp += 1;
		}
	} else
		//如果strContext不足1行，intCtextCountTmp = 1; 
		intCtextCountTmp = 1;

	if (intCtextCountTmp > 12) {
		//strContext = strContext.substring(0,600)+"<br>计算书信息过多，请详见清单。"; 
		//caopeng 11.29
		strContext = "<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;理算过程请详见清单。";
		//caopeng 11.29
		//add by liuwei at 2011-03-04 控制打印附页按钮可用 start
		buttonFlag = true;
		//add by liuwei at 2011-03-04 控制打印附页按钮可用 end
	}
	while (strContext.indexOf("\\r\\n") != -1)
		strContext = strContext.substring(0, strContext.indexOf("\\r\\n")) + "<br>" + strContext.substring(strContext.indexOf("\\r\\n") + "\\r\\n".length());

	//获得理算明细
	strCompensate = "(申请金额 - 自费金额 - 免赔金额) * 责任比例 + 计入赔款的费用\r\n" + "=";
	ArrayList prpLpersonLossDtoList = compensateDto.getPrpLpersonLossDtoList();
	int num = 0;
	if (prpLpersonLossDtoList != null && prpLpersonLossDtoList.size() > 0) {
		num = prpLpersonLossDtoList.size();
	}
	String[] strLiabDetail = new String[num]; //理算明细名称
	double[] dblSumRealPay = new double[num]; //理算明细金额 
	if (prpLpersonLossDtoList != null && prpLpersonLossDtoList.size() > 0) {
		for (int m = 0; m < num; m++) {
			strLiabDetail[m] = "";
			dblSumRealPay[m] = 0.00;
		}

		for (Iterator iter = prpLpersonLossDtoList.iterator(); iter.hasNext();) {
			PrpLpersonLossDto element = (PrpLpersonLossDto) iter.next();
			/*if("E001".equals(element.getLiabDetailCode())){ //意外身故
				sumrealpay1 = sumrealpay1 + element.getSumRealPay();
				}
			if("E101".equals(element.getLiabDetailCode())){ //意外残疾
				sumrealpay2 = sumrealpay2 + element.getSumRealPay();
				}
			if("E401".equals(element.getLiabDetailCode())){ //意外医疗(门急诊)
				sumrealpay3 = sumrealpay3 + element.getSumRealPay();
				}
			if("E501".equals(element.getLiabDetailCode())){ //意外医疗（住院）
				sumrealpay4 = sumrealpay4 + element.getSumRealPay();
				}
			if("E901".equals(element.getLiabDetailCode())){ //住院医疗
				sumrealpay5 = sumrealpay5 + element.getSumRealPay();
				}
			if("E801".equals(element.getLiabDetailCode())
			   ||"E601".equals(element.getLiabDetailCode())){ //住院津贴或意外住院医疗现金补贴
				sumrealpay6 = sumrealpay6 + element.getSumRealPay();
				}*/
			sumrest = sumrest + element.getSumRest();

			//计算公式 = (申请金额 - 自费金额 - 免赔金额) * 责任比例 + 计入赔款的费用
			//申请金额在prplpersonloss表的sumloss字段中，自费金额在sumrest字段中，免赔额在deductible字段中，赔偿比例在claimrate字段中
			//给付金额在sumrealpay字段中
			//int i = prpLpersonLossDtoList.size();
			//strCompensate = strCompensate + "+("+element.getSumLoss()+"-"+element.getSumRest()
			//                +"-"+element.getDeductible()+")*"+element.getClaimRate()+"%\r\n"	;				              
		}
	}

	ArrayList prpLchargeDtoList = compensateDto.getPrpLchargeDtoList();
	if (prpLchargeDtoList != null && prpLchargeDtoList.size() > 0) {
		for (Iterator iter = prpLchargeDtoList.iterator(); iter.hasNext();) {
			PrpLchargeDto element = (PrpLchargeDto) iter.next();
			//计入赔款的费用在prplcharge表的sumrealpay字段中
			strCompensate = strCompensate + "+" + element.getSumRealPay() + "\r\n";
			//获取查勘费用
			if ("查勘费".equals(element.getChargeName())) { //意外身故
				chargeAmoutcheck = chargeAmoutcheck + element.getChargeAmount();
			}
		}
	}
	strCompensate = strCompensate + "\r\n=" + prpLcompensateDto.getSumDutyPaid();
	//sumPaid1 = sumrealpay1 + sumrealpay2 +sumrealpay3 +sumrealpay4 +sumrealpay5 +sumrealpay6;

	strChargeAmoutcheck = new DecimalFormat("#,##0.00").format(chargeAmoutcheck);
	double oppositeChargeAmoutcheck = -chargeAmoutcheck;
	if (chargeAmoutcheck >= 0) {
		strCChargeAmoutcheck = MoneyUtils.toChinese(chargeAmoutcheck, prpLcompensateDto.getCurrency());
	} else {
		strCChargeAmoutcheck = "-" + MoneyUtils.toChinese(-chargeAmoutcheck, prpLcompensateDto.getCurrency());
	}

	//获取实际给付金额、结案总金额
	//modify by liuwei at 2011-07-27 从（联、共）保显示我司份额 beging
	if (policyDto != null) {
		if ("2".equals(policyDto.getPrpCmainDto().getCoinsFlag()) || "3".equals(policyDto.getPrpCmainDto().getCoinsFlag())) {
			double coinsRate = 1;
			ArrayList PrpCcoinsDtoList = (ArrayList) new BLPrpCcoinsFacade().findByConditions(" policyNO='" + policyDto.getPrpCmainDto().getPolicyNo() + "' and coinsType='1' ", 0, 0);
			if (null != PrpCcoinsDtoList && PrpCcoinsDtoList.size() > 0) {
				PrpCcoinsDto prpCcoinsDto = (PrpCcoinsDto) PrpCcoinsDtoList.get(0);
				coinsRate = prpCcoinsDto.getCoinsRate() / 100;
			}

			BigDecimal bigCoinsRate = new BigDecimal(Double.toString(coinsRate));
			BigDecimal bigSumDutyPaid = new BigDecimal(Double.toString(prpLcompensateDto.getSumDutyPaid()));
			BigDecimal bigSumPrePaid = new BigDecimal(Double.toString(prpLcompensateDto.getSumPrePaid()));
			sumDutyPaid = bigSumDutyPaid.multiply(bigCoinsRate).doubleValue();
			dblSumprepaid = bigSumPrePaid.multiply(bigCoinsRate).doubleValue();
		} else {
			sumDutyPaid = prpLcompensateDto.getSumDutyPaid();
			dblSumprepaid = prpLcompensateDto.getSumPrePaid();
		}
	}
	//modify by liuwei at 2011-07-27 从（联、共）保显示我司份额 end
	//add by liping 080819	

	strSumprepaid = new DecimalFormat("#,##0.00").format(dblSumprepaid);
	strSumDutyPaid = new DecimalFormat("#,##0.00").format(sumDutyPaid);
	double oppositeSumDutyPaid = -sumDutyPaid;
	if (sumDutyPaid >= 0) {
		strCSumDutyPaid = MoneyUtils.toChinese(sumDutyPaid, prpLcompensateDto.getCurrency());
	} else {
		strCSumDutyPaid = "-" + MoneyUtils.toChinese(-sumDutyPaid, prpLcompensateDto.getCurrency());
	}
	//add by zhangyingrui end at 20060831

	//得到prpLclaimDto对象
	prpLclaimDto = claimDto.getPrpLclaimDto();
	//得到prpLregistDto对象
	prpLregistDto = registDto.getPrpLregistDto();

	strClaimNo = prpLclaimDto.getClaimNo();
	//得到保单号
	strPolicyNo = prpLclaimDto.getPolicyNo();

	//得到事故人信息
	if (claimDto != null) {
		PrpLacciPersonDto prpLacciPersonDto = (PrpLacciPersonDto) claimDto.getPrpLacciPersonDto();
		if (prpLacciPersonDto != null) {
			strAcciName = StringConvert.encode(prpLacciPersonDto.getAcciName());
			strSexCode = prpLacciPersonDto.getSex();
			strAcciSex = uiCodeAction.translateCodeCode("SexCode", strSexCode, true);
			strAcciAge = String.valueOf(prpLacciPersonDto.getAge());
			strAcciIDCardNo = prpLacciPersonDto.getIdentifyNumber();
		}
	}

	//得到立案信息
	if (prpLclaimDto != null) {
		strCode = prpLclaimDto.getDamageTypeCode();
		strDamageTypeName = uiCodeAction.translateCodeCode("DamageTypeCode", strCode, true);
		DateTime dtAcciDate = new DateTime();
		String strDamageStartHour = prpLclaimDto.getDamageStartHour();
		dtAcciDate = prpLclaimDto.getDamageStartDate();
		strAcciDate = dtAcciDate.getYear() + "年" + dtAcciDate.getMonth() + "月" + dtAcciDate.getDay() + "日" + strDamageStartHour;
		strCode = prpLclaimDto.getClaimType();
		strClaimTypeName = uiCodeAction.translateCodeCode("CaseCode", strCode, true);
		//delete by zhangyingrui start at 20060831
		//strClaimTypeName = "性质：" + strClaimTypeName;
		//delete by zhangyingrui end at 20060831
		//add by zhangyingrui start at 20060831 原因：获取事故经过、结果及其现状
		ArrayList registTextContextList = registDto.getPrpLregistTextDtoList();
		if (registTextContextList != null && registTextContextList.size() > 0) {
			for (Iterator iter = registTextContextList.iterator(); iter.hasNext();) {
				PrpLregistTextDto element = (PrpLregistTextDto) iter.next();
				if ("1".equals(element.getTextType())) {
					strRegistTextContext = strRegistTextContext + element.getContext();
				}
			}
		}
		//add by zhangyingrui start at 20060831    
		strCode = prpLclaimDto.getDamageCode();
		strDamageName = uiCodeAction.translateCodeCode("DamageCode", strCode, true);
		////System.out.println("====="+strClaimTypeName);
	}

	//得到保单信息
	if (policyDto != null) {
		PrpCmainDto prpCmainDto = policyDto.getPrpCmainDto();
		if (prpCmainDto != null) {
			strInsuredDate = "自 " + prpCmainDto.getStartDate().getYear() + "年" + prpCmainDto.getStartDate().getMonth() + "月" + prpCmainDto.getStartDate().getDate() + "日" + prpCmainDto.getStartHour() + "时起" + "至 " + prpCmainDto.getEndDate().getYear() + "年" + prpCmainDto.getEndDate().getMonth() + "月" + prpCmainDto.getEndDate().getDate() + "日" + prpCmainDto.getEndHour() + "时止";
			dblSumAmount = prpCmainDto.getSumAmount();
		}
	}
%>
<script language="javascript">
function printPageAdd() {
	//alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
	return false;
	var url = "/claim/ClaimPrint.do?printType=CompensateAuditBookAdd&CompensateNo=<%=strCompensateNo%>";
	var newWindow = window.open(url, "NewWindow", "width=600,height=500,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
}

function loadForm() {

	spClaimNo.innerHTML = '<%=strClaimNo%>';
	//spClaimNo1.innerHTML = '<%=strClaimNo%>';
	spPolicyNo.innerHTML = '<%=strPolicyNo%>';
	spAcciName.innerHTML = '<%=strAcciName%>';
	spAcciSex.innerHTML = '<%=strAcciSex%>';
	spAcciAge.innerHTML = '<%=strAcciAge%>';
	spAcciIDCardNo.innerHTML = '<%=strAcciIDCardNo%>';
	spDamageTypeName.innerHTML = '<%=strDamageTypeName%>';
	spAcciDate.innerHTML = '<%=strAcciDate%>';
	spAcciDate1.innerHTML = '<%=strAcciDate%>';
	spAcciDamageDesc.innerHTML = '<%="&nbsp;&nbsp;事故原因：" + strDamageName + "&nbsp;&nbsp;" + strClaimTypeName%>';
	spAcciDamageName.innerHTML = '<%=strDamageName%>';
	//spPolicyNo1.innerHTML  = '<%=strPolicyNo%>'; 
	spInsuredDate.innerHTML = '<%=strInsuredDate%>';
	//spSumAmount.innerHTML = '<%=new DecimalFormat("#,##0.00").format(dblSumAmount)%>';
	tdContext.innerHTML = '<%=tempContext%>';
	if ( <%= buttonFlag %> ) {
		buttonPrintAdd.disabled = false;
	}
}
</script>
