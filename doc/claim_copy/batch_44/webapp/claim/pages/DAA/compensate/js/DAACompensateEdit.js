/*****************************************************************************
 * DESC       ：实赔的脚本函数页面
 * AUTHOR     ：中科軟
 * CREATEDATE ： 2004-05-19
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/
/**
 *@description 正在处理理算任务选择免赔率
 *@param       无
 *@return      打开选择免赔率页面
 */

function choseDeductibleRate() {
	pageUrl = "/claim/compensate/compensateBeforeEditList.do?" + "ClaimNo=" + fm.prpLcompensateClaimNo.value + "&swfLogFlowID=" + fm.swfLogFlowID.value + "&swfLogLogNo=" + fm.swfLogLogNo.value + "&riskCode=" + fm.prpLcompensateRiskCode.value + "&editType=RECHOSE&nodeType=compe&status=0" + "&businessNo=" + fm.prpLcompensateClaimNo.value + "&policyNo=" + fm.prpLcompensatePolicyNo.value + "&CompensateNo=" + fm.prpLcompensateCompensateNo.value + "&modelNo=1&rechoseFlag=1&nodeNo=12&compeCount=0";

	window.open(pageUrl, "", "resizable=0,scrollbars,width=550,height=320");
}
/**
 *@description 检查报案登记
 *@param       无
 *@return      通过返回true,否则返回false
 */

function checkForm() {

	return true;
}

function changeCompensateFlag(flag) {
	fm.GenerateCompensateFlag.value = flag;
}

function GenerateCtextFlag(flag) {
	fm.GenerateCompensateFlag.value = flag;
}
/**
 *@description 设值页面的一些初始化信息
 *@param       无
 *@return      通过返回true,否则返回false
 */

function initSet() {
	//默认展开特别约定
	initRealPay();
	countPersonLossNumber();
	calFundNew();
	//判断保费是否交付，否则弹出提示信息
	var payFee = parseInt(fm.prpLcompensatePayFee.value);
	var errorMessage = "";
	if (payFee == -1) {
		errorMessage=errorMessage+i18n.certainLoss.policyPremiumNoPay;  // 此保单保费未缴,请慎重处理！！
	} else if (payFee == 0) {
		errorMessage=errorMessage+i18n.certainLoss.policyPremiumPay;  // 此保单已缴未缴全,请慎重处理！！！
	}
	//mantis： CLM0092 ，處理人員：BK007 蘇哲，需求單編號：CLM0092.新核心-閉鎖期提醒 - start
	//判斷是否在閉鎖期
	if(fm.isCloseBetween.value === 'true'){
		alert("請注意本賠次在閉鎖期！！！！！");
	}
	//mantis： CLM0092 ，處理人員：BK007 蘇哲，需求單編號：CLM0092.新核心-閉鎖期提醒 - end
	if (fm.chiefflag) {
		var chiefFlag = fm.chiefflag.value;
		if (chiefFlag != 0) {
			alert("本保單爲主共保單,請注意產生聯共保分攤信息!");
		}
	}
	//mantis：CLM0193 ，處理人員：DP0713，需求單編號：新核心-代步車日期計算及輸入檢核
	//mantis：CLM0221 ，處理人員：DP0713，需求單編號：新核心-車體險車輛資料完工日期欄位調整
	//mantis：CLM0213，處理人員：DP0713，需求單編號：新核心-車體險維修時間重疊檢核新增險種 START
	try{
		calculateFinishAndDayCount();		
	}catch(e){}
	//mantis：CLM0213，處理人員：DP0713，需求單編號：新核心-車體險維修時間重疊檢核新增險種 END
	if (errorMessage.length > 0) {
		alert(errorMessage);
		return false;
	}
	return true;
}

/**
 *@description 提交
 *@param       无
 *@return      通过返回true,否则返回false
 */

function submitDelete() {
	fm.buttonDelete.disabled = true;
	fm.submit();
	return true;
}
/**
 *@description 提交
 *@param       无
 *@return      通过返回true,否则返回false
 */

function submitForm() {
	fm.buttonApprove.disabled = true;
	fm.submit();
	return true;
}

/**
 *@description 清除
 *@param       无
 *@return      通过返回true,否则返回false
 */

function resetForm() {
	if (window.confirm("確定要清除嗎？")) {
		location.href = location.href;
		return true;
	} else {
		return false;
	}
}


/**
 @author
 @description 增加一条赔付人员费用信息方法
 @param       无
 @return      无
 @see         UIMulLine#insertRow
 @see         UIMulLine#setRowRecordState
*/

function insertRowKind() {

	insertRow('Kind', 'Kind_Data')
}

/**
 *@description 实赔任务复核
 *@param       无
 *@return      通过返回true,否则返回false
 */

function approveSubmit() {
	if (checkForm() == false) {
		return false;
	}

	fm.buttonApprove.disabled = true;
	fm.submit();
	return true;
}


/**
 @author 中科软
 @description 生成理算报告
 @param       无
 @return      无
 */

function generateCtext() {

	if (checkForm() == false) {
		return false;
	}

	var oldAction = fm.action;
	var oldTarget = fm.target;

	fm.action = "/claim/compensate/compensateGenerate.do";
	fm.target = "fraCalculate";

	fm.submit();

	fm.action = oldAction;
	fm.target = oldTarget;
	return true;
}


/**
 @author 中科软
 @description 计算赔偿比例（改变实际价值时触发）
              若赔偿比例为空，则赔偿比例=保额/新车购置价 也就是  (保额/限额)/实际价值
 @param       field:触发域
 @return      boolean值，合法返回true,不合法返回false
 @see         UICommon.js#point、round
*/

function calLossClaimRate(field) {

	var fieldname = field.name;
	var findex = 0;

	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == field) {
			findex = i;
			break;
		}
	}

	var prpLlossDtoAmount = parseFloat(fm.all("prpLlossDtoAmount")[findex].value);

	var ClaimRate = 0;

	if (isNaN(prpLlossDtoAmount))
		prpLlossDtoAmount = 0;

	if (isEmptyField(field)) {
		field.value = prpLlossDtoAmount;
	}

	ClaimRate = point(round(prpLlossDtoAmount / parseFloat(field.value) * 100, 0), 0);
	if (ClaimRate > 100)
		ClaimRate = 100;
	fm.all("prpLlossDtoClaimRate")[findex].value = ClaimRate;

	calRealpay(field);

	return true;
}


/**
 @author 中科软
 @description 分险别校验是否超过保单中的限额
 @param       KindCode:险别
 @param       PersonNo:人员序号
 @return      无
 */

function CheckLimitAmountByKindCode(KindCode, PersonNo) {
	if (checkCodeInQuery()) {
		return;
	}

	if (KindCode == "")
		return;

	//解除锁定,否则无法传入下一页
	fm.Sex.disabled = false;
	fm.PersonLossLicenseNo.disabled = false;

	//保存数据
	saveRecord("Loss");
	saveRowRecordToSingleTable("PersonLoss", "Kind");

	var oldAction = fm.action;
	var oldTarget = fm.target;

	fm.target = "fraSubmit"
	fm.action = "/ddccallweb/DAA/lp/compensate/UILDAACheckLimitAmountSubmit.jsp?KindCode=" + KindCode + "&PersonNo=" + PersonNo;

	fm.submit();

	fm.action = oldAction;
	fm.target = oldTarget;

	//清除数据
	clearRecord("Loss");
	clearRecord("PersonLoss");
	clearRowRecord("Kind");

	//load data
	loadRowRecord("PersonLoss", "Kind", "Kind_Data");

}

/**
 @author 中科软
 @description 计算责任赔款合计、赔款合计、其它费用、实赔金额
 @param       无
 @return      无
 @see         UICommon.js#point、round
*/
// 客制化需求改动点 

function calFund() {
	//定义变量

	var dblSumDutyPaid = 0; //责任赔款合计（=（赔款费用附加信息中）计入赔款金额+（赔付标的附加信息中）赔偿金额+（赔付人员附加信息中）赔付合计）
	var dblSumPaid = 0; //赔款总计（=责任赔款合计+其它费用）dblSumDutyPaid +  dblSumNoDutyFee
	var dblSumPrePaid = 0; //预赔金额
	var dblSumNoDutyFee = 0; //其它费用（（赔款费用附加信息中）费用金额 - 计入赔款金额）PrpLCharge.ChargeAmount-PrpLCharge.SumRealPay
	var dblSumThisPaid = 0; //实赔金额（=责任赔款合计－已预付赔款）dblSumPaid - dblSumPrePaid

	var chargeRealPay = 0;
	var chargeAmount = 0;
	var lossRealPay = 0;
	var personLossRealPay = 0;

	//计算责任赔款合计
	var i = 0;

	//1.赔款费用的计入赔款金额，以及其它费用
	var elements = getTableElements("PrpLcharge");
	for (i = 1; i < fm.all("prpLchargeSumRealPay").length; i++) {
		chargeRealPay = parseFloat(fm.all("prpLchargeSumRealPay")[i].value); //记入赔款
		chargeAmount = parseFloat(fm.all("prpLchargeChargeAmount")[i].value); //费用

		if (isNaN(chargeRealPay))
			chargeRealPay = 0;
		if (isNaN(chargeAmount))
			chargeAmount = 0;

		dblSumDutyPaid = dblSumDutyPaid + chargeRealPay;
		dblSumNoDutyFee = dblSumNoDutyFee + (chargeAmount - chargeRealPay);
	}

	//2.赔付标的的赔偿金额 
	var dblRealPayAB = 0; //modify by dengxh update at 20040914
	var dblRealPayA = 0; //车损险（A）总赔款金额
	var dblRealPayATotal = 0; //车损险（A）最终赔款金额，可能为空 
	for (i = 1; i < fm.all("prpLlossDtoSumRealPay").length; i++) {
		lossRealPay = parseFloat(fm.all("prpLlossDtoSumRealPay")[i].value);

		if (isNaN(lossRealPay))
			lossRealPay = 0;

		dblSumDutyPaid = dblSumDutyPaid + lossRealPay;
		if ((fm.all("prpLlossDtoKindCode")[i].value == "A" || fm.all("prpLlossDtoKindCode")[i].value == "B") && fm.all("prpLlossDtoFeeTypeCode")[i].value == '27') {
			dblRealPayAB = dblRealPayAB + lossRealPay;
		}
		if (fm.all("prpLlossDtoKindCode")[i].value == "A") {
			dblRealPayA = dblRealPayA + lossRealPay;
		}

	}

	var deductibleAall = parseFloat(fm.prpLDeductible.value);
	if (isNaN(deductibleAall))
		deductibleAall = 0;
	//用车损险赔款减去免赔额

	dblRealPayATotal = dblRealPayA;
	//加入域显示车损险最终赔款
	if (dblRealPayATotal > 0) {
		fm.lastRealPay.value = dblRealPayATotal;
	} else {
		if (deductibleAall > 0) {
			fm.lastRealPay.value = dblRealPayATotal;
		}
	}

	//3.赔付人员的赔付合计
	var personLossData = getTableElements("PrpLpersonFeeLoss");
	var intPersonLossCount = personLossData.length;

	for (i = 1; i < fm.all("prpLpersonLossSumRealPay").length; i++) {
		personLossRealPay = parseFloat(fm.all("prpLpersonLossSumRealPay")[i].value);

		if (isNaN(personLossRealPay))
			personLossRealPay = 0;

		dblSumDutyPaid = dblSumDutyPaid + personLossRealPay;
	}

	//得到预赔金额
	dblSumPrePaid = parseFloat(fm.prpLcompensateSumPrePaid.value, 10);
	if (isNaN(dblSumPrePaid))
		dblSumPrePaid = 0;

	//计算赔款总计
	dblSumPaid = dblSumDutyPaid + dblSumNoDutyFee;

	//计算实赔金额
	dblSumThisPaid = dblSumDutyPaid - dblSumPrePaid;
	if (fm.exceptDeductibleRateAll) {
		var exceptAll = parseFloat(fm.exceptDeductibleRateAll.value);
		dblSumPaid = dblSumPaid + exceptAll;
		dblSumDutyPaid = dblSumDutyPaid + exceptAll;
		dblSumThisPaid = dblSumThisPaid + exceptAll;
	}
	fm.prpLcompensateSumDutyPaid.value = point(round(dblSumDutyPaid, 0), 0);
	fm.prpLcompensateSumNoDutyFee.value = point(round(dblSumNoDutyFee, 0), 0);
	fm.prpLcompensateSumPaid.value = point(round(dblSumPaid, 0), 0);
	fm.prpLcompensateSumThisPaid.value = point(round(dblSumThisPaid, 0), 0);
	fm.prpLdangerRiskSumPaid.value = point(round(dblSumThisPaid, 0), 0);
	fm.prpLcompensateSumSelfValue.value = point(round(dblRealPayAB, 0), 0);
	if (fm.buttonCoins) {
		creatCoins();
		creatCoinsFlag('1');
		resetChangelossCharge();
	}
}


/**
 @author 中科软
 @description 修理天数修改时触发(只对车辆停驶险)
 @param       无
 @return      boolean值
*/

function changeLossQuantity(field) {
	var fieldname = field.name; //域名
	var i = 0;
	var findex = 0; //定位序号

	//定位
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == field) {
			findex = i;
			break;
		}
	}

	var LossQuantity = parseFloat(fm.all("prpLlossDtoLossQuantity")[findex].value);
	var UnitPrice = parseFloat(fm.all("prpLlossDtoUnitPrice")[findex].value);

	if (isNaN(LossQuantity))
		LossQuatity = 0;
	if (isNaN(UnitPrice))
		UnitPrice = 0;

	var Quantity = parseInt(fm.all("prpLlossDtoLossQuantity")[findex].oldValue);
	var UnitAmount = parseFloat(fm.all("prpLlossDtoUnitPrice")[findex].oldValue);

	if (LossQuantity > Quantity) {
		errorMessage("修理天数不能大於承保的天数(" + Quantity + "天)!");
		fm.LossQuantity.focus();
		fm.LossQuantity.select();
		return false;
	}
	if (UnitPrice > UnitAmount) {
		errorMessage("单位赔偿金额不能大於承保的单位限额(" + UnitAmount + ")!");
		fm.UnitPrice.focus();
		fm.UnitPrice.select();
		return false;
	}

	fm.all("prpLlossDtoSumLoss")[findex].value = point(round(LossQuantity * UnitPrice, 0), 0);
	fm.all("prpLlossDtoSumLoss")[findex].onchange();

	return true;
}

//

/**
 @author 中科软
 @description 计算赔付标的和人员核损金额之和
 @param       无
 @return      无
 @see         UICommon.js#point、round
*/

function calLoss() {
	//定义变量
	var dblSumLoss = 0;
	var LossSumLoss = 0;
	var PersonLossSumLoss = 0;
	var i = 0;

	for (i = 1; i < fm.all("prpLlossDtoSumLoss").length; i++) {
		LossSumLoss = parseFloat(fm.all("prpLlossDtoSumLoss")[i].value);

		if (isNaN(LossSumLoss))
			LossSumLoss = 0;

		dblSumLoss = dblSumLoss + LossSumLoss;
	}
	for (i = 1; i < fm.all("prpLpersonLossSumLoss").length; i++) {
		PersonLossSumLoss = parseFloat(fm.all("prpLpersonLossSumLoss")[i].value);

		if (isNaN(PersonLossSumLoss))
			PersonLossSumLoss = 0

		dblSumLoss = dblSumLoss + PersonLossSumLoss;
	}

	fm.prpLcompensateSumLoss.value = point(round(dblSumLoss, 0), 0);
}




/**
 @author 中科软
 @description 使一个输入域设置为只读
 @param       iElement: 域
 @return      无
 */

function setReadonlyOfElementOfLoss(iElement) {
	if (iElement.type == "select-one") {
		iElement.disabled = true;
	} else if (iElement.type == "text") {
		iElement.onfocus = null;
		iElement.readOnly = true;
		iElement.className = "readonly";
	}
}


/**
 @author 中科软
 @description 使一个输入域设置为可输入
 @param       iElement: 域
 @return      无
 */

function undoSetReadonlyOfElementOfLoss(iElement) {
	if (iElement.type == "select-one") {
		iElement.disabled = false;
	} else {
		iElement.readOnly = false;
		iElement.className = 'common';

	}
}




//离开域时的数字校验

function checkInteger(field, MinValue, MaxValue) {
	field.value = trim(field.value);
	var strValue = field.value;
	if (strValue == "")
		strValue = "0";
	var desc = field.description;
	//如果description属性不存在，则用name属性
	if (desc == null)
		desc = field.name;

	MinValue = parseInt(MinValue, 10);
	if (isNaN(MinValue))
		MinValue = MIN_INTEGER;

	MaxValue = parseInt(MaxValue, 10);
	if (isNaN(MaxValue))
		MaxValue = MAX_INTEGER;
	var value = parseInt(strValue, 10);
	if (isNaN(value) || value > MaxValue || value < MinValue) {
		errorMessage("请输入合法的" + desc + "\n类型为数字(integer),最小值为" + MinValue + ",最大值为" + MaxValue);
		field.focus();
		field.select();
		return false;
	}
	return true;
}

/**
 @author 中科软
 @description 校验计入赔款金额不能超过费用金额
 @param       Field: 触发域
 @return      boolean: 合法为true,否则为false
 @see         UICommon.js#isEmpty
 */

function checkChargeAmount(Field) {
	var fieldname = Field.name; //域名
	var i = 0;
	var findex = 0; //定位序号
	var ChargeAmount = 0; //费用金额
	var ChargeRealPay = 0; //计入赔款金额

	//定位
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == Field) {
			findex = i;
			break;
		}
	}
	if (fieldname == "prpLchargeChargeReport") {
		var chargeCode = fm.prpLchargeChargeCode[findex].value; //费用类别
		var chargeAmount = fm.prpLchargeChargeAmount[findex].value; //费用类别
		if (chargeCode == '03') {
			fm.all("prpLchargeSumRealPay")[findex].value = parseFloat(chargeAmount);
		} else {
			fm.all("prpLchargeSumRealPay")[findex].value = 0;
		}
		calFund();
		if (!(isEmptyField(Field)) && !(isEmptyField(fm.all("prpLchargeSumRealPay")[findex]))) {
			ChargeAmount = parseFloat(chargeAmount);
			ChargeRealPay = parseFloat(fm.all("prpLchargeSumRealPay")[findex].value);
		}

	} else if (fieldname == "prpLchargeChargeAmount") {
		//reason:赔款费用中计入赔款金额自动带出且为只读
		var chargeCode = fm.prpLchargeChargeCode[findex].value; //费用类别
		//reason:赔款费用中计入赔款金额自动带出且为只读
		if (chargeCode == '03') {
			fm.all("prpLchargeSumRealPay")[findex].value = Field.value;
		} else {
			fm.all("prpLchargeSumRealPay")[findex].value = 0;
		}
		calFund();
		if (!(isEmptyField(Field)) && !(isEmptyField(fm.all("prpLchargeSumRealPay")[findex]))) {
			ChargeAmount = parseFloat(Field.value);
			ChargeRealPay = parseFloat(fm.all("prpLchargeSumRealPay")[findex].value);
		}
	} else if (fieldname == "prpLchargeSumRealPay") {
		if (!(isEmptyField(fm.all("prpLchargeChargeAmount")[findex])) && !(isEmptyField(Field))) {
			ChargeAmount = parseFloat(fm.all("prpLchargeChargeAmount")[findex].value);
			ChargeRealPay = parseFloat(Field.value);
		}
	}

	if (ChargeAmount < ChargeRealPay) {
		if (fieldname == "prpLchargeChargeAmount") {
			errorMessage("计入赔款金额不能超过费用金额！");
			Field.focus();
			Field.select();
			return false;
		} else if (fieldname == "prpLchargeSumRealPay") {
			errorMessage("计入赔款金额不能超过费用金额！");
			fm.all("prpLchargeChargeAmount")[findex].focus();
			fm.all("prpLchargeChargeAmount")[findex].select();
			return false;
		}
	}

	//车身划痕损失险最高费用为5000
	if (fm.all("prpLchargeKindCode")[findex].value == "L" && parseFloat(fm.all("prpLchargeChargeAmount")[findex].value) > 5000) {
		errorMessage("车身划痕损失险最高赔款费用为5000元！");
		fm.all("prpLchargeChargeAmount")[findex].focus();
		fm.all("prpLchargeChargeAmount")[findex].select();
		return false;
	}

	return true;
}

/**
 @author 中科软
 @description 同险别的免赔率，责任比例要相等
 @param       无
 @return      无
 @see         UICommon.js#point、round
*/

function checkKindCode(Field) {

	var fieldname = Field.name; //域名
	var i = 0;
	var findex = 0; //定位序号
	var ChargeAmount = 0; //费用金额
	var ChargeRealPay = 0; //计入赔款金额

	var findFlag = 0;

	//定位
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == Field) {
			findex = i;
			break;
		}
	}

	//取得当前险别代码 
	var strValue = fm.prpLpersonLossKindCode[findex].value;
	if (strValue == "")
		return;
	//判断选择的险别是否为出险日期当时生效的险别
	for (var j = 0; j < damageKind.length; j++) {
		if (damageKind[j] == strValue) {
			findFlag = 1;
			break;
		}
	}
	if (findFlag == 0) {
		alert("您選擇的險別不是出險日期時的險別,請重新進行選擇");
		fm.prpLpersonLossKindCode[findex].value = "";
		return false;
	}

	var kindCode = "";
	for (i = 0; i < fm.all("prpLpersonLossKindCode").length; i++) {
		kindCode = fm.all("prpLpersonLossKindCode")[i].value;
		if (kindCode == fm.all("prpLpersonLossKindCode")[findex].value) {
			//只修改本人的免赔率
			if (fm.all("prpLpersonLossSerialNo")[i].value == fm.all("prpLpersonLossSerialNo")[findex].value) {
				fm.all("prpLpersonLossIndemnityDutyRate")[i].value = fm.all("prpLpersonLossIndemnityDutyRate")[findex].value;
				fm.all("prpLpersonLossDeductibleRate")[i].value = fm.all("prpLpersonLossDeductibleRate")[findex].value;
			}
		}
	}
}




/**
 @author 中科软
 @description 计算赔付人员的赔款金额(改变责任比例时造成的赔款金额的改变)
              涉及所有当前行的费用信息
 @param       无
 @return      无
 @see         UICommon.js#point、round
*/

function calRealpay2(field) {
	var i = 0;
	var findex = 0; //定位序号
	var SumLoss; //核损金额
	var SumRest; //残值
	var ClaimRate; //赔偿比例
	var DeductibleRate; //免赔率
	var Deductible; //免赔额
	var Deductibletemp; //免赔
	var DutyRate; //责任比例
	var Realpay; //赔付金额
	var temp;

	var fieldname = field.name; //域名

	//定位
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == field) {
			findex = i;
			break;
		}
	}

	var findex1 = 0;
	for (i = 1; i < fm.all("prpLpersonLossIndemnityDutyRate").length; i++) {
		if (fm.all("prpLpersonLossSerialNo")[i].value == fm.all("personLossSerialNo")[findex].value) {
			findex1 = i;
			break;
		}
	}
	DutyRate = parseFloat(fm.all("prpLpersonLossIndemnityDutyRate")[findex1].value);
	DeductibleRate = parseFloat(fm.all("prpLpersonLossDeductibleRate")[findex1].value);
	if (isNaN(DutyRate))
		DutyRate = 0;
	else
		DutyRate = DutyRate / 100;
	if (isNaN(DeductibleRate))
		DeductibleRate = 0;
	else
		DeductibleRate = DeductibleRate / 100;
	//给变量赋值
	SumLoss = 0;
	SumRest = 0; //
	ClaimRate = 0;
	Deductible = 0; //
	Deductibletemp = 0;
	Realpay = 0;
	temp = 0;

	for (i = 1; i < fm.all("prpLpersonLossSumRealPay").length; i++) {
		if (fm.all("personLossSerialNo")[i].value == fm.all("prpLpersonLossSerialNo")[findex].value) {

			SumLoss = parseFloat(fm.all("prpLpersonLossSumLoss")[i].value);
			ClaimRate = parseFloat(fm.all("prpLpersonLossClaimRate")[i].value);

			if (isNaN(SumLoss))
				SumLoss = 0;
			if (isNaN(ClaimRate))
				ClaimRate = 0;
			else
				ClaimRate = ClaimRate / 100;

			/*计算赔款金额
			 * 如果免赔高：（核定损失 - 残值）* 赔偿比例 * 责任比例 * （1 - 免赔率）
			 * # 此条取消----如果免赔额：（核定损失 - 残值）* 赔偿比例 * 责任比例  - 免赔额
			 */
			temp = (SumLoss - SumRest) * ClaimRate * DutyRate; //temp=（核定损失 - 残值）* 赔偿比例 * 责任比例
			Deductibletemp = temp * DeductibleRate; //免赔= temp * 免赔率

			Realpay = temp * (1 - DeductibleRate);

			fm.all("prpLpersonLossSumRealPay")[i].value = point(round(Realpay, 0), 0);
		}
	}

	//计算赔付人员中的赔付合计
	calSumRealpay(field);

	//计算责任赔款合计、赔款合计、其它费用、实赔金额
	calFund();
}

/**
 @author 中科软
 @description 计算赔付人员中的赔付合计(改变单价、数目、赔偿比例、
              责任比例时造成的赔款金额的改变)
 @param       无
 @return      无
 @see         UICommon.js#point、round
*/

function calSumRealpay(field) {
	var findex = 0; //定位序号
	var i = 0;
	var Realpay = 0; //赔款金额
	var SumRealpay = 0; //赔付合计
	var SumDefpay = 0; //核定赔偿合计
	var Defpay = 0;
	var fieldname = field.name; //域名

	//定位
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == field) {
			findex = i;
			break;
		}
	}
	var findex1 = 0;
	for (i = 1; i < fm.all("prpLpersonLossIndemnityDutyRate").length; i++) {
		if (getElementCount("personLossSerialNo") > 1) {
			if (fm.all("prpLpersonLossSerialNo")[i].value == fm.all("personLossSerialNo")[findex].value) {
				findex1 = i;
				break;
			}
		}
	}
	//给变量赋值
	Realpay = 0;
	SumRealpay = 0;

	//计算赔付合计
	for (i = 1; i < fm.all("prpLpersonLossSumRealPay").length; i++) {
		if (fm.all("personLossSerialNo")[i].value == fm.all("prpLpersonLossSerialNo")[findex1].value) {
			Realpay = parseFloat(fm.all("prpLpersonLossSumRealPay")[i].value);
			Defpay = parseFloat(fm.all("prpLpersonLossSumDefPay")[i].value);
			if (isNaN(Realpay)) Realpay = 0;
			if (isNaN(Defpay)) Defpay = 0;
			if (fieldname == "buttonPersonFeeLossDelete" && i != findex) {
				SumRealpay = SumRealpay + Realpay;
				SumDefpay = SumDefpay + Defpay;
			} else if (fieldname != "buttonPersonFeeLossDelete") {
				SumRealpay = SumRealpay + Realpay;
				SumDefpay = SumDefpay + Defpay;
			}
		}
	}
	//将用来显示的赔付合计设置到界面上
	fm.all("prpLpersonLossSumRealPay1")[findex1].value = point(round(SumRealpay, 0), 0);
	fm.all("prpLpersonLossSumDefPay1")[findex1].value = point(round(SumDefpay, 0), 0);
}


/**
 @author 中科软
 @description 计算赔付人员的核损金额(改变单价和数目时造成的核损金额的改变)
 @param       Field: 触发域
 @return      无
 @see         UICommon.js#point、round
*/

function calSumLoss(Field) {

	var fieldname = Field.name; //域名
	var i = 0;
	var findex = 0; //定位序号
	var SumLoss; //核损金额
	var UnitPrice; //单价
	var Quantity; //数量

	//定位
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == Field) {
			findex = i;
			break;
		}
	}

	//给变量赋值
	UnitPrice = parseFloat(fm.all("prpLpersonLossUnitAmount")[findex].value);
	Quantity = parseFloat(fm.all("prpLpersonLossLossQuantity")[findex].value);
	SumLoss = 0;

	//计算核损金额
	if (isNaN(UnitPrice))
		UnitPrice = 0;
	if (isNaN(Quantity))
		Quantity = 0;

	SumLoss = UnitPrice * Quantity;
	fm.all("prpLpersonLossSumLoss")[findex].value = point(round(SumLoss, 0), 0);

	//计算赔付人员的赔款金额
	calRealpay1(Field);

	//计算赔付标的和人员核损金额之和
	calLoss();
}

/**
 @author 中科软
 @description 计算赔付人员的赔款金额(改变单价、数目和赔偿比例时造成的赔款金额的改变)
              只涉及触发域所对应的当前行的费用信息
 @param       Field: 触发域
 @return      无
 @see         UICommon.js#point、round
*/

function calRealpay1(Field) {
	var fieldname = Field.name; //域名
	var i = 0;
	var findex = 0; //定位序号
	var SumLoss; //核损金额
	var SumRest; //残值
	var ClaimRate; //赔偿比例
	var DeductibleRate; //免赔率
	var Deductible; //免赔额
	var Deductibletemp; //免赔
	var DutyRate; //责任比例
	var Realpay; //赔付金额
	var temp;

	//定位
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == Field) {
			findex = i;
			break;
		}
	}
	//给变量赋值
	SumLoss = parseFloat(fm.all("prpLpersonLossSumLoss")[findex].value);
	SumDefPay = parseFloat(fm.all("prpLpersonLossSumDefPay")[findex].value);
	SumRest = 0; //
	ClaimRate = parseFloat(fm.all("prpLpersonLossClaimRate")[findex].value);
	DeductibleRate = 0;
	Deductible = 0; //
	Deductibletemp = 0;
	DutyRate = 0;
	Realpay = 0;
	temp = 0;
	var findex1 = 0;
	for (i = 1; i < fm.all("prpLpersonLossIndemnityDutyRate").length; i++) {
		if (fm.all("prpLpersonLossSerialNo")[i].value == fm.all("personLossSerialNo")[findex].value) {
			findex1 = i;
			break;
		}
	}
	DutyRate = parseFloat(fm.all("prpLpersonLossIndemnityDutyRate")[findex1].value);
	DeductibleRate = parseFloat(fm.all("prpLpersonLossDeductibleRate")[findex1].value);
	if (isNaN(SumLoss))
		SumLoss = 0;
	if (isNaN(SumDefPay))
		SumDefPay = 0;
	if (isNaN(ClaimRate))
		ClaimRate = 0;
	else
		ClaimRate = ClaimRate / 100;
	if (isNaN(DeductibleRate))
		DeductibleRate = 0;
	else
		DeductibleRate = DeductibleRate / 100;
	if (isNaN(DutyRate))
		DutyRate = 0;
	else
		DutyRate = DutyRate / 100;

	/*计算赔款金额
	 * 如果免赔高：（核定损失 - 残值）* 赔偿比例 * 责任比例 * （1 - 免赔率）
	 * # 此条取消----如果免赔额：（核定损失 - 残值）* 赔偿比例 * 责任比例  - 免赔额
	 */
	temp = (SumDefPay) * ClaimRate * DutyRate; //temp=（核定损失 - 残值）* 赔偿比例 * 责任比例  //LYM
	Deductibletemp = temp * DeductibleRate; //免赔= temp * 免赔率
	Realpay = temp * (1 - DeductibleRate);
	fm.all("prpLpersonLossSumRealPay")[findex].value = point(round(Realpay, 0), 0);

	//计算赔付人员中的赔付合计
	calSumRealpay(Field);

	//计算责任赔款合计、赔款合计、其它费用、实赔金额
	calFund();
}


/**
 @author 中科软
 @description 赔付金额修改时触发
 @param       Field:触发域
 @return      boolean值
*/

function changePersonLossRealpay(Field) {
	var fieldname = Field.name;
	var i = 0;
	var findex = 0;

	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == Field) {
			findex = i;
			break;
		}
	}

	var PersonLossSumLoss = fm.all("prpLpersonLossSumLoss")[findex].value;
	var PersonLossRealpay = parseFloat(Field.value);

	if (isNaN(PersonLossSumLoss))
		PersonLossSumLoss = 0;
	if (isNaN(PersonLossRealpay))
		PersonLossRealpay = 0;

	if (PersonLossRealpay > PersonLossSumLoss) {
		errorMessage("赔付金额不能大於核损金额!");
		Field.focus();
		Field.select();
		return false;
	}

	//计算赔付人员中的赔付合计
	calSumRealpay(Field);

	//计算责任赔款合计、赔款合计、其它费用、实赔金额
	calFund();

	return true;
}

/**
 @author 中科软
 @description 改变理赔类型时校验
 @param       Field:触发域
 @return      boolean型，合法返回true,不合法返回false
 */

function changeLFlag(Field) {
	if (Field.value == 'D') {
		errorMessage("非国内货运险赔款计算书理赔类型不能为D!");
		Field.focus();
		return false;
	}
	return true;
}



/**
 @author 中科软 //页面初始话的时候用
 @description 计算赔付人员中的赔付合计(改变单价、数目、赔偿比例、
              责任比例时造成的赔款金额的改变)
 @param       无
 @return      无
 @see         UICommon.js#point、round
*/

function calSumRealpayInit() {
	var i = 0;
	var Realpay = 0; //赔款金额
	var SumRealpay = 0; //赔付合计
	var Defpay = 0; //赔付合计
	var SumDefpay = 0; //赔付合计
	//定位
	for (i = 0; i < fm.all("prpLpersonLossSumRealPay1").length; i++) {
		//给变量赋值
		Realpay = 0;
		SumRealpay = 0;
		Defpay = 0;
		SumDefpay = 0;

		//计算赔付合计
		for (j = 0; j < fm.all("prpLpersonLossSumRealPay").length; j++) {
			if (fm.all("personLossSerialNo")[j].value == fm.all("prpLpersonLossSerialNo")[i].value) {
				Realpay = parseFloat(fm.all("prpLpersonLossSumRealPay")[j].value);
				Defpay = parseFloat(fm.all("prpLpersonLossSumDefPay")[j].value);
				if (isNaN(Realpay)) Realpay = 0;
				if (isNaN(Defpay)) Defpay = 0;
				SumDefpay = SumDefpay + Defpay
				SumRealpay = SumRealpay + Realpay;
			}
		}

		//将用来显示的赔付合计设置到界面上
		fm.all("prpLpersonLossSumRealPay1")[i].value = point(round(SumRealpay, 0), 0);
		fm.all("prpLpersonLossSumDefPay1")[i].value = point(round(SumDefpay, 0), 0);

	}
	return true;
}

/**
 @author 中科软
 @description 分险别校验是否超过保单中的限额
 @param       KindCode:险别
 @param       PersonNo:人员序号
 @return      无
 */

function getDeductibleRate(Field, Type) {
	var index = 0;
	var KindCode = "";

	if (Type == "lLoss") //赔付标的
	{
		index = getElementOrder(Field) - 1;
		KindCode = fm.all("prpLlossDtoKindCode")[index].value;
	} else if (Type == "Charge") {
		index = getElementOrder(Field) - 1;
		KindCode = fm.prpLchargeKindCode[index].value;
	} else {
		index = getElementOrder(Field) - 1;
		KindCode = fm.prpLpersonLossKindCode[index].value;
	}

	if (KindCode == "")
		return;

	var oldTarget = fm.target;
	var oldAction = fm.action;

	fm.target = "fraSubmit";
	fm.action = "/claim/pages/DAA/compensate/sunny/DAAGetDeductibleRateSubmit.jsp?KindCode=" + KindCode + "&Type=" + Type + "&Index=" + index;
	fm.submit();
	fm.target = oldTarget;
	fm.action = oldAction;

}


/**
 @author 中科软
 @description 险别是车损或三者，並且损失明细为27扣免赔时，允许輸入残值，因为赔偿金额=-残值
 @param       KindCode:险别
 @param       PersonNo:人员序号
 @return      无
 */

function changeLossSumRestShowStyle(Field) //赔付标的信息中若险别为“三者险”则置残值为空，且只读
{
	var index1 = 0;
	index1 = getElementOrder(Field) - 1;
	if (fm.prpLlossDtoKindCode[index1].value != "M") {
		fm.button_Loss_Refresh.disabled = true;
	} else if (fm.prpLlossDtoKindCode[index1].value == "M") {
		fm.button_Loss_Refresh.disabled = false;
	}
	if (fm.prpLlossDtoKindCode[index1].value == "B" && fm.prpLlossDtoFeeTypeCode[index1].value != "27") {
		setReadonlyOfElementOfLoss(fm.prpLlossDtoSumRest[index1]);
		fm.prpLlossDtoSumRest[index1].value = "";
	} else {
		undoSetReadonlyOfElementOfLoss(fm.prpLlossDtoSumRest[index1]);
	}
}

function changeLossClaimRate(Field) //赔付标的信息中若险别为“车损险”则置赔付比例为（A险别的保额除以新车购置价），且赔付比例只读
{
	var index1 = 0;
	index1 = getElementOrder(Field) - 1;
	if (fm.prpLlossDtoKindCode[index1].value == "A") {
		var escapeFlag = fm.prpLcompensateEscapeFlag.value;
		if (!(escapeFlag.length > 1 && escapeFlag.substring(1, 2) == "Y")) {
			var purchasePrice = parseFloat(fm.prpLcompensatePurchasePrice.value);
			var AKindCodeAmount = parseFloat(fm.prpLlossDtoAmount[index1].value);
			if (purchasePrice > 0 && purchasePrice != AKindCodeAmount) {
				var lossClaimRate = AKindCodeAmount * 100.00 / purchasePrice;
				fm.prpLlossDtoClaimRate[index1].value = point(round(lossClaimRate, 0), 0);
				setReadonlyOfElementOfLoss(fm.prpLlossDtoClaimRate[index1]);
			}
		}
	} else {
		if (fm.prpLlossDtoFeeTypeCode[index1].value != "27" && fm.prpLlossDtoKindCode[index1].value != "M")
			undoSetReadonlyOfElementOfLoss(fm.prpLlossDtoClaimRate[index1]);
	}
}
//修改自负额的处理
//reason:增加自负额

function getValue(Field) {
	var index1 = 0;
	index1 = getElementOrder(Field) - 1;
	if ((fm.prpLlossDtoKindCode[index1].value == "A" || fm.prpLlossDtoKindCode[index1].value == "B") && fm.prpLlossDtoFeeTypeCode[index1].value == "27") {
		var countA = 0;
		var countB = 0;
		for (var i = 0; i < fm.all("prpLlossDtoKindCode").length; i++) {
			if (fm.all("prpLlossDtoFeeTypeCode")[i].value == '27') {
				if (fm.all("prpLlossDtoKindCode")[i].value == "A") {
					countA++;
					if (countA >= 2) {
						errorMessage("赔付标的中每个险别下的扣免赔只能輸入一次!");
						return false;
					}
				} else if (fm.all("prpLlossDtoKindCode")[i].value == "B") {
					countB++;
					if (countB >= 2) {
						errorMessage("赔付标的中每个险别下的扣免赔只能輸入一次!");
						return false;
					}
				}
			}
		}
		if (countA < 2 && countB < 2) {
			setReadonlyOfElementOfLoss(fm.prpLlossDtoItemValue[index1]);
			setReadonlyOfElementOfLoss(fm.prpLlossDtoSumLoss[index1]);
			setReadonlyOfElementOfLoss(fm.prpLlossDtoIndemnityDutyRate[index1]);
			setReadonlyOfElementOfLoss(fm.prpLlossDtoLossQuantity[index1]);
			setReadonlyOfElementOfLoss(fm.prpLlossDtoClaimRate[index1]);
			setReadonlyOfElementOfLoss(fm.prpLlossDtoDeductibleRate[index1]);
			undoSetReadonlyOfElementOfLoss(fm.prpLlossDtoSumRest[index1]);

			var KindCode = "";
			KindCode = fm.prpLlossDtoKindCode[index1].value;

			var oldTarget = fm.target;
			var oldAction = fm.action;

			fm.target = "fraSubmit"
			fm.action = "/claim/pages/DAA/compensate/DAAGetValueSubmit.jsp?KindCode=" + KindCode + "&PolicyNo=" + fm.prpLcompensatePolicyNo.value + "&Index=" + index1;
			fm.submit();

			fm.target = oldTarget;
			fm.action = oldAction;
		}
	} else {
		undoSetReadonlyOfElementOfLoss(fm.prpLlossDtoSumRest[index1]);
		undoSetReadonlyOfElementOfLoss(fm.prpLlossDtoItemValue[index1]);
		undoSetReadonlyOfElementOfLoss(fm.prpLlossDtoSumLoss[index1]);
		if (fm.prpLlossDtoKindCode[index1].value == 'T') {
			undoSetReadonlyOfElementOfLoss(fm.prpLlossDtoLossQuantity[index1]);
			undoSetReadonlyOfElementOfLoss(fm.prpLlossDtoUnitPrice[index1]);
		} else {
			setReadonlyOfElementOfLoss(fm.prpLlossDtoLossQuantity[index1]);
			setReadonlyOfElementOfLoss(fm.prpLlossDtoUnitPrice[index1]);
		}

		if (fm.prpLlossDtoKindCode[index1].value == "B" && fm.prpLlossDtoFeeTypeCode[index1].value != "27") {
			setReadonlyOfElementOfLoss(fm.prpLlossDtoSumRest[index1]);
		}
		if (fm.prpLlossDtoKindCode[index1].value == "A" && fm.prpLcompensatePurchasePrice.value != fm.prpLlossDtoAmount[index1].value) {
			setReadonlyOfElementOfLoss(fm.prpLlossDtoClaimRate[index1]);
		}

		if (fm.prpLlossDtoKindCode[index1].value != "M") {
			fm.button_Loss_Refresh.disabled = true;
		}
		if (fm.prpLlossDtoKindCode[index1].value == "M") {
			fm.button_Loss_Refresh.disabled = false;
			setReadonlyOfElementOfLoss(fm.prpLlossDtoSumRest[index1]);
			setReadonlyOfElementOfLoss(fm.prpLlossDtoItemValue[index1]);
			setReadonlyOfElementOfLoss(fm.prpLlossDtoSumLoss[index1]);
			setReadonlyOfElementOfLoss(fm.prpLlossDtoIndemnityDutyRate[index1]);
			setReadonlyOfElementOfLoss(fm.prpLlossDtoLossQuantity[index1]);
			setReadonlyOfElementOfLoss(fm.prpLlossDtoClaimRate[index1]);
			setReadonlyOfElementOfLoss(fm.prpLlossDtoDeductibleRate[index1]);
			setReadonlyOfElementOfLoss(fm.prpLlossDtoFeeTypeCode[index1]);
			setReadonlyOfElementOfLoss(fm.prpLlossDtoFeeTypeName[index1]);
		}
	}
}


function checkLossDeductibleRate(Field) {
	if (parseFloat(Field.value) < parseFloat(Field.oldValue)) {
		alert("免賠率只能上調不允許下調！");
		Field.value = Field.oldValue;
		calRealpay(Field);
		Field.focus();
		return false;
	}
	return true;
}
/**
 @author 中科软
 @description 汇总
 @param       无
 @return      无
 */

function showLossCollect() {
	var newWindow = window.open("/claim/pages/DAA/compensate/DAALossCollect.jsp", "new", "width=500,height=220,top=200,left=200,scrollbars=yes");
}

/**
 @author 中科软
 @description 按险别名称，项目名称汇总信息
 @param       无
 @return      返回一个包含险别名称，项目名称，核损金额，赔偿金额的数组
 */

function getLoss() {
	var arrayLoss;
	var arrayLossCollect = new Array();
	var i = 0
	var j = 0;
	var findex;
	var existFlag = false;
	//汇总标的信息
	for (i = 1; i < fm.all("lossDtoSerialNo").length; i++) {
		arrayLoss = new Array();

		arrayLoss["LossKindName"] = fm.prpLlossDtoKindName[i].value;
		arrayLoss["LossName"] = fm.prpLlossDtoLossName[i].value;
		arrayLoss["LossSumLoss"] = parseFloat(fm.prpLlossDtoSumLoss[i].value);
		arrayLoss["LossRealPay"] = parseFloat(fm.prpLlossDtoSumRealPay[i].value);

		if (isNaN(arrayLoss["LossSumLoss"]))
			arrayLoss["LossSumLoss"] = 0;
		if (isNaN(arrayLoss["LossRealPay"]))
			arrayLoss["LossRealPay"] = 0;
		//按险别名称，项目名称汇总信息
		for (j = 0; j < arrayLossCollect.length; j++) {
			if (arrayLossCollect[j]["LossKindName"] == arrayLoss["LossKindName"] && arrayLossCollect[j]["LossName"] == arrayLoss["LossName"]) {
				existFlag = true;
				break;
			} else {
				existFlag = false;
			}
		}

		if (!existFlag) {
			arrayLossCollect[j] = arrayLoss; //一个汇总项
		} else {
			arrayLossCollect[j]["LossSumLoss"] = arrayLossCollect[j]["LossSumLoss"] + arrayLoss["LossSumLoss"];
			arrayLossCollect[j]["LossRealPay"] = arrayLossCollect[j]["LossRealPay"] + arrayLoss["LossRealPay"];
		}
	}
	//汇总人伤信息
	var count = getElementCount("personLossSerialNo");
	if (count != 1) {
		for (i = 1; i < fm.all("personLossSerialNo").length; i++) {
			arrayLoss = new Array();

			for (var index = 1; index < fm.all("prpLpersonLossSerialNo").length; index++) {
				if (fm.personLossSerialNo[i].value == fm.prpLpersonLossSerialNo[index].value) {
					findex = index;
					break;
				}
			}

			arrayLoss["LossKindName"] = fm.prpLpersonLossKindName[findex].value;
			arrayLoss["LossName"] = fm.prpLpersonLossPersonName[findex].value;
			arrayLoss["LossSumLoss"] = parseFloat(fm.prpLpersonLossSumLoss[i].value);
			arrayLoss["LossRealPay"] = parseFloat(fm.prpLpersonLossSumRealPay[i].value);

			if (isNaN(arrayLoss["LossSumLoss"]))
				arrayLoss["LossSumLoss"] = 0;
			if (isNaN(arrayLoss["LossRealPay"]))
				arrayLoss["LossRealPay"] = 0;
			//按险别名称，项目名称汇总信息
			for (j = 0; j < arrayLossCollect.length; j++) {
				if (arrayLossCollect[j]["LossKindName"] == arrayLoss["LossKindName"] && arrayLossCollect[j]["LossName"] == arrayLoss["LossName"]) {
					existFlag = true;
					break;
				} else {
					existFlag = false;
				}
			}

			if (!existFlag) {
				arrayLossCollect[j] = arrayLoss; //一个汇总项
			} else {
				arrayLossCollect[j]["LossSumLoss"] = arrayLossCollect[j]["LossSumLoss"] + arrayLoss["LossSumLoss"];
				arrayLossCollect[j]["LossRealPay"] = arrayLossCollect[j]["LossRealPay"] + arrayLoss["LossRealPay"];
			}
		}
	}
	return arrayLossCollect;
}

/**
 @author 中科软
 @description 刷新赔付标的中不计免赔险别的不计免赔值
 @param       无
 @return      无
 */

function refreshLoss(Field) {
	var index = 0;
	index = getElementOrder(Field) - 1;

	var i = 0;
	var j = 0;
	var Deductible = 0;
	var LossDeductibleRate = 0;
	var PersonLossDeductibleRate = 0;
	var sumLossRealPay = 0;
	var strFlag = "";
	var bFind = false;

	var strRiskCode = fm.prpLcompensateRiskCode.value;
	var LossDeductibleRate1 = 0;
	var PersonLossDeductibleRate1 = 0;
	var strIsSafeLoad = fm.prpLcompensateDeductCond.value;
	strIsSafeLoad = strIsSafeLoad.substring(2, 3); //取违反安全装载特殊免赔条件

	//查找是否輸入了不计免赔险
	for (i = 0; i < fm.all("prpLlossDtoSerialNo").length; i++) {
		if (fm.prpLlossDtoKindCode[i].value == 'M') {
			bFind = true;
			break;
		}
	}


	//没有輸入不计免赔险则无需刷新
	if (bFind == false)
		return;

	var oldAction = fm.action;
	var oldTarget = fm.target;
	fm.action = "/claim/pages/DAA/compensate/DAAGetDeductibleSubmit.jsp?PolicyNo=" + fm.prpLcompensatePolicyNo.value + "&DamageStartDate=" + fm.DamageStartDate.value + "&Index=" + index;
	fm.target = "fraCalculate";

	fm.submit();

	fm.action = oldAction;
	fm.target = oldTarget;
}



/**
 @author 中科软
 @description 计算赔付标的中的赔偿金额（改变实际价值、核定损失、残值、责任比例时触发）
              计算赔款金额
              如果免赔高：（核定损失 - 残值）* 赔偿比例 * 责任比例 * （1 - 免赔率）
              # 此条取消----如果免赔额：（核定损失 - 残值）* 赔偿比例 * 责任比例  - 免赔额
 @param       无
 @return      无
 @see         UICommon.js#、round
*/

function calRealpayForSunny(field) {
	var SumLoss; //核损金额
	var SumRest; //残值
	var ClaimRate; //赔偿比例
	var DutyDeductibleRate; //事故责任免赔率
	var DeductibleRate; //免赔率
	var DriverDeductibleRate; //驾驶员免赔率
	var mainKindCode; //主险代码
	var DeductibleRateOfMainKind; //主险的绝对免赔率
	var Deductible; //免赔额
	var Deductibletemp; //免赔
	var DutyRate; //责任比例
	var Realpay; //赔偿金额
	var temp;
	var unitPrice;
	//取得当前的数据
	var fieldname = field.name;
	var findex = 0;

	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == field) {
			findex = i;
			break;
		}
	}

	SumLoss = parseFloat(fm.all("prpLlossDtoSumLoss")[findex].value);
	SumRest = parseFloat(fm.all("prpLlossDtoSumRest")[findex].value);
	ClaimRate = parseFloat(fm.all("prpLlossDtoClaimRate")[findex].value);
	DutyRate = parseFloat(fm.all("prpLlossDtoIndemnityDutyRate")[findex].value);
	DutyDeductibleRate = parseFloat(fm.all("prpLlossDtoDutyDeductibleRate")[findex].value);
	DeductibleRate = parseFloat(fm.all("prpLlossDtoDeductibleRate")[findex].value);
	DriverDeductibleRate = parseFloat(fm.all("prpLlossDtoDriverDeductibleRate")[findex].value);
	unitPrice = parseFloat(fm.all("prpLlossDtoUnitPrice")[findex].value);
	if (fm.prpLcompensateRiskCode.value == "DAS" && fm.all("prpLlossDtoKindCode")[findex].value == "F") {
		DeductibleRate = 10.0;
		fm.LossDeductibleRate.value = (round(DeductibleRate, 0), 0)
	}
	Deductible = parseFloat(fm.all("prpLlossDtoDeductible")[findex].value);
	Amount = parseFloat(fm.all("prpLlossDtoAmount")[findex].value);
	if (isNaN(Amount))
		Amount = 0;
	if (isNaN(SumLoss))
		SumLoss = 0;
	if (isNaN(SumRest))
		SumRest = 0;
	if (isNaN(ClaimRate))
		ClaimRate = 0;
	else
		ClaimRate = ClaimRate / 100;
	if (isNaN(DutyRate))
		DutyRate = 0;
	else
		DutyRate = DutyRate / 100;
	if (isNaN(DutyDeductibleRate))
		DutyDeductibleRate = 0;
	else
		DutyDeductibleRate = DutyDeductibleRate / 100;
	if (isNaN(DeductibleRate))
		DeductibleRate = 0;
	else
		DeductibleRate = DeductibleRate / 100;

	if (isNaN(DriverDeductibleRate))
		DriverDeductibleRate = 0;
	else
		DriverDeductibleRate = DriverDeductibleRate / 100;

	if (isNaN(Deductible))
		Deductible = 0;

	if (isNaN(unitPrice))
		unitPrice = 0;


	/*计算赔款金额
	 * 如果免赔高：（核定损失 - 残值）* 赔偿比例 * 责任比例 * （1 - 免赔率）
	 * # 此条取消----如果免赔额：（核定损失 - 残值）* 赔偿比例 * 责任比例  - 免赔额
	 */
	temp = (SumLoss - SumRest) * ClaimRate * DutyRate;
	if (fm.all("prpLlossDtoKindCode")[findex].value == "C" || fm.all("prpLlossDtoKindCode")[findex].value == "L") {
		temp = (SumLoss - SumRest) * ClaimRate;
	}

	Deductibletemp = temp * DeductibleRate;
	if (fm.prpLcompensateRiskCode.value == "DAS" && fm.all("prpLlossDtoKindCode")[findex].value == "F" && Deductibletemp < 100.0 && temp > 0.0) {
		Deductibletemp = 100.0;

		Realpay = temp - Deductibletemp;
		if (temp > 0) {
			DeductibleRate = (Deductibletemp * 100.0) / temp;
			fm.all("prpLlossDtoDeductibleRate")[findex].value = (round(DeductibleRate, 0), 0)
		}
	} else {
		//个别附加险要获得主险的绝对免赔率
		if (fm.all("prpLlossDtoKindCode")[findex].value == "D2" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "D3" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "D4" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "Y" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "H") {
			mainKindCode = "B";
		}
		if (fm.all("prpLlossDtoKindCode")[findex].value == "G0" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "L" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "Z" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "Y" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "X") {
			mainKindCode = "A";
		}
		for (var j = 0; j < fm.all("prpLlossDtoKindCode").length; j++) {
			if (fm.all("prpLlossDtoKindCode")[j].value == mainKindCode) {
				DeductibleRateOfMainKind = fm.all("prpLlossDtoDeductibleRate")[j].value;
				break;
			}
		}
		//应 要求,屏蔽掉主险的责任免赔率
		DeductibleRateOfMainKind = 0;
		if (fm.all("prpLlossDtoKindCode")[findex].value == "D2" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "D3" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "D4" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "Y" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "G0" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "L" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "Z" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "X" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "H") {
			fm.all("PrpLlossDtoMainKindDuctibleRate")[findex].value = DeductibleRateOfMainKind;
			Realpay = temp * (1 - DutyDeductibleRate) * (1 - DeductibleRate) * (1 - DriverDeductibleRate) * (1 - DeductibleRateOfMainKind / 100);
		} else if (fm.all("prpLlossDtoKindCode")[findex].value == "T" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "C") {
			//停驶、代步车调整
			Realpay = (temp * (1 - DutyDeductibleRate) - unitPrice) * (1 - DriverDeductibleRate);

		} else {
			Realpay = temp * (1 - DutyDeductibleRate) * (1 - DeductibleRate) * (1 - DriverDeductibleRate);
		}
	}

	if ((fm.all("prpLlossDtoKindCode")[findex].value == "B" || fm.all("prpLlossDtoKindCode")[findex].value == "D2" || fm.all("prpLlossDtoKindCode")[findex].value == "W") && temp > Amount) {
		Realpay = Amount * (1 - DutyDeductibleRate) * (1 - DeductibleRate) * (1 - DriverDeductibleRate);
	}
	if (Realpay > Amount && Amount > 0) {
		Realpay = Amount;
	}
	if ((fm.all("prpLlossDtoKindCode")[findex].value == "A" || fm.all("prpLlossDtoKindCode")[findex].value == "B") && fm.all("prpLlossDtoFeeTypeCode")[findex].value == "27") {
		Realpay = SumRest * (-1);
	}
	fm.all("prpLlossDtoSumRealPay")[findex].value = (round(Realpay, 0), 0);

	//计算责任赔款合计、赔款合计、其它费用、实赔金额
	calFund();
}

function checkAmount(kindCode, amount) {
	var sumAmountOfKind = 0;
	for (i = 1; i < fm.all("prpLlossDtoKindCode").length; i++) {
		if (fm.all("prpLlossDtoKindCode")[i].value == kindCode) {
			sumAmountOfKind = sumAmountOfKind + parseFloat(fm.all("prpLlossDtoSumRealPay")[i].value);
		}
	}

	if (sumAmountOfKind > parseFloat(amount)) {
		return false;
	} else {
		return true;
	}
}


/**
 @author 中科软
 @description 计算赔付人员的赔款金额(改变责任比例时造成的赔款金额的改变)
              涉及所有当前行的费用信息
 @param       无
 @return      无
 @see         UICommon.js#、round
*/

function calRealpay2ForSunny(field) {
	var i = 0;
	var flagn;
	var findex = 0; //定位序号
	var SumLoss; //核损金额
	var sumDefPay1 = 0;
	var CompelPay1 = 0;
	var CompelPay;
	var SumDefPay;

	var DutyDeductibleRate; //事故责任免赔率
	var DeductibleRate; //绝对免赔率
	var MainKindDeductibleRate; //所在主险的绝对免赔率
	var exceptDeductibleRate1;

	var Deductibletemp; //免赔
	var DutyRate; //责任比例
	var ArrangeRate; //协商赔款比例
	var Realpay; //赔付金额
	var temp;

	var fieldname = field.name; //域名

	//定位
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == field) {
			findex = i;
			break;
		}
	}
	//如果本条费用信息不存在则返回
	if (getElementCount("personLossSerialNo") <= findex) {
		return;
	}
	var findex1;
	for (var i = 1; i < fm.all("prpLpersonLossIndemnityDutyRate").length; i++) {
		if (fm.all("prpLpersonLossSerialNo")[i].value == fm.all("personLossSerialNo")[findex].value) {
			findex1 = i;
		}
	}
	if (fieldname != "buttonPersonFeeLossDelete") {
		DutyRate = parseFloat(fm.all("prpLpersonLossIndemnityDutyRate")[findex1].value);
		ArrangeRate = parseFloat(fm.all("prpLpersonLossArrangeRate")[findex1].value);
		DutyDeductibleRate = parseFloat(fm.all("prpLpersonLossDutyDeductibleRate")[findex1].value);
		DeductibleRate = parseFloat(fm.all("prpLpersonLossDeductibleRate")[findex1].value);
		MainKindDeductibleRate = parseFloat(fm.all("prpLpersonLossMainKindDeductibleRate")[findex1].value);
		exceptDeductibleRate1 = parseFloat(fm.all("prpLpersonLossExceptDeductibleRate1")[findex1].value);
		for (var c = 0; c < fm.prpLlossDtoKindCodeShow.length; c++) {
			if (fm.prpLlossDtoKindCodeShow[c].value == fm.all("prpLpersonLossKindCode")[findex1].value) {
				flagn = fm.flag[c].value
			}
		}
		if (isNaN(ArrangeRate))
			ArrangeRate = 0;
		else
			ArrangeRate = ArrangeRate / 100;
		if (isNaN(exceptDeductibleRate1))
			exceptDeductibleRate1 = 0;


		if (isNaN(DutyRate))
			DutyRate = 0;
		else
			DutyRate = DutyRate / 100;

		if (isNaN(DeductibleRate))
			DeductibleRate = 0;
		else
			DeductibleRate = DeductibleRate / 100;

		if (isNaN(DutyDeductibleRate))
			DutyDeductibleRate = 0;
		else
			DutyDeductibleRate = DutyDeductibleRate / 100;
		if (isNaN(MainKindDeductibleRate))
			MainKindDeductibleRate = 0;
		else
			MainKindDeductibleRate = MainKindDeductibleRate / 100;


		//给变量赋值
		SumDefPay = 0;
		SumLoss = 0;
		SumRest = 0; //
		ClaimRate = 0;
		Deductible = 0; //
		Deductibletemp = 0;
		Realpay = 0;
		temp = 0;
		for (i = 1; i < fm.all("prpLpersonLossSumRealPay").length; i++) {
			if (fm.all("personLossSerialNo")[i].value == fm.all("prpLpersonLossSerialNo")[findex1].value) {
				SumDefPay = parseFloat(fm.all("prpLpersonLossSumDefPay")[i].value);
				CompelPay = parseFloat(fm.all("prpLpersonLossCompelPay")[i].value);
				ClaimRate = parseFloat(fm.all("prpLpersonLossClaimRate")[i].value);
				if (isNaN(SumLoss))
					SumLoss = 0;

				/*计算赔款金额
				 * 如果免赔高：（核定损失 - 残值）* 赔偿比例 * 责任比例 * （1 - 免赔率）
				 * # 此条取消----如果免赔额：（核定损失 - 残值）* 赔偿比例 * 责任比例  - 免赔额
				 */
				temp = (SumDefPay - CompelPay) * DutyRate * ArrangeRate; //temp=（核定损失 - 残值）* 赔偿比例 * 责任比例 * 协商赔偿比例 
				MainKindDeductibleRate = 0;
				//部分险别需要承上所在主险的绝对免赔率
				if (fm.all("prpLpersonLossKindCode")[findex1].value == "R") //||
				{
					Realpay = temp * (1 - DutyDeductibleRate - DeductibleRate) / DutyRate;
				} else {
					Realpay = temp * (1 - DutyDeductibleRate - DeductibleRate);
				}
				fm.all("prpLpersonLossSumRealPay")[i].value = (round(Realpay, 0), 0);
				if (flagn == '1') {
					fm.prpLpersonLossExceptDeductibleRate[i].value = parseInt(exceptDeductibleRate1);
					fm.prpLpersonLossExceptDeductiblePay[i].value = (SumDefPay - CompelPay) * DutyRate * ArrangeRate * ((parseInt(fm.prpLpersonLossExceptDeductibleRate[i].value)) / 100);
					initExceptDeductible();
				}
			}

		}
	}
	//计算赔付人员中的赔付合计
	calSumRealpay(field);
	//汇总各个险别的金额
	initEvryTypeRealPay();
	//计算责任赔款合计、赔款合计、其它费用、实赔金额
	calFund();
}




/**
 @author 中科软
 @description 计算赔付人员的赔款金额(改变单价、数目和赔偿比例时造成的赔款金额的改变)
              只涉及触发域所对应的当前行的费用信息
 @param       Field: 触发域
 @return      无
 @see         UICommon.js#、round
*/

function calRealpay1ForSunny(Field) {
	var fieldname = Field.name; //域名
	var i = 0;
	var findex = 0; //定位序号
	var SumLoss; //核损金额
	var SumRest; //残值
	var ClaimRate; //赔偿比例

	var DutyDeductibleRate; //事故责任免赔率
	var DriverDeductibleRate; //驾驶员免赔率
	var DeductibleRate; //绝对免赔率
	var MainKindDeductibleRate; //所在主险的绝对免赔率

	var Deductible; //免赔额
	var Deductibletemp; //免赔
	var DutyRate; //责任比例
	var Realpay; //赔付金额
	var temp;

	//定位
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == Field) {
			findex = i;
			break;
		}
	}


	//给变量赋值
	SumLoss = parseFloat(fm.all("prpLpersonLossSumLoss")[findex].value);
	SumRest = 0; //
	ClaimRate = parseFloat(fm.all("prpLpersonLossClaimRate")[findex].value);
	DeductibleRate = 0;
	Deductible = 0; //
	Deductibletemp = 0;
	DutyRate = 0;
	Realpay = 0;
	temp = 0;
	var findex1 = 0;
	for (i = 1; i < fm.all("prpLpersonLossIndemnityDutyRate").length; i++) {
		if (fm.all("prpLpersonLossSerialNo")[i].value == fm.all("personLossSerialNo")[findex].value) {
			findex1 = i;
			break;
		}
	}
	DutyRate = parseFloat(fm.all("prpLpersonLossIndemnityDutyRate")[findex1].value);
	DeductibleRate = parseFloat(fm.all("prpLpersonLossDeductibleRate")[findex1].value);
	DutyDeductibleRate = parseFloat(fm.all("prpLpersonLossDutyDeductibleRate")[findex1].value);
	DriverDeductibleRate = parseFloat(fm.all("prpLpersonLossDriverDeductibleRate")[findex1].value);
	MainKindDeductibleRate = parseFloat(fm.all("prpLpersonLossMainKindDeductibleRate")[findex1].value);
	Amount = parseFloat(fm.all("prpLpersonLossAmount")[findex1].value);
	if (isNaN(SumLoss))
		SumLoss = 0;
	if (isNaN(ClaimRate))
		ClaimRate = 0;
	else
		ClaimRate = ClaimRate / 100;
	if (isNaN(DeductibleRate))
		DeductibleRate = 0;
	else
		DeductibleRate = DeductibleRate / 100;

	if (isNaN(DutyDeductibleRate))
		DutyDeductibleRate = 0;
	else
		DutyDeductibleRate = DutyDeductibleRate / 100;

	if (isNaN(DriverDeductibleRate))
		DriverDeductibleRate = 0;
	else
		DriverDeductibleRate = DriverDeductibleRate / 100;

	if (isNaN(MainKindDeductibleRate))
		MainKindDeductibleRate = 0;
	else
		MainKindDeductibleRate = MainKindDeductibleRate / 100;


	if (isNaN(DutyRate))
		DutyRate = 0;
	else
		DutyRate = DutyRate / 100;

	/*计算赔款金额
	 * 如果免赔高：（核定损失 - 残值）* 赔偿比例 * 责任比例 * （1 - 免赔率）
	 * # 此条取消----如果免赔额：（核定损失 - 残值）* 赔偿比例 * 责任比例  - 免赔额
	 */

	temp = (SumLoss - SumRest) * ClaimRate * DutyRate; //temp=（核定损失 - 残值）* 赔偿比例 * 责任比例
	Deductibletemp = temp * DeductibleRate; //免赔= temp * 免赔率

	//部分险别需要承上所在主险的绝对免赔率
	if (fm.all("prpLpersonLossKindCode")[findex1].value == "D2" ||
		fm.all("prpLpersonLossKindCode")[findex1].value == "D3" ||
		fm.all("prpLpersonLossKindCode")[findex1].value == "D4" ||
		fm.all("prpLpersonLossKindCode")[findex1].value == "Y" ||
		fm.all("prpLpersonLossKindCode")[findex1].value == "G0" ||
		fm.all("prpLpersonLossKindCode")[findex1].value == "L" ||
		fm.all("prpLpersonLossKindCode")[findex1].value == "Z" ||
		fm.all("prpLpersonLossKindCode")[findex1].value == "X" ||
		fm.all("prpLpersonLossKindCode")[findex1].value == "H") {
		Realpay = temp * (1 - DutyDeductibleRate) * (1 - DeductibleRate) * (1 - DriverDeductibleRate) * (1 - MainKindDeductibleRate);
	} else {
		Realpay = temp * (1 - DutyDeductibleRate) * (1 - DeductibleRate) * (1 - DriverDeductibleRate);
	}
	//  }
	if (temp > Amount) {
		Realpay = Amount * (1 - DutyDeductibleRate) * (1 - DeductibleRate) * (1 - DriverDeductibleRate);
	}
	fm.all("prpLpersonLossSumRealPay")[findex].value = (round(Realpay, 0), 0);

	//计算赔付人员中的赔付合计
	calSumRealpay(Field);

	//计算责任赔款合计、赔款合计、其它费用、实赔金额
	calFund();
}

function changePrpLcompensateFinallyFlag() {
	if (fm.prpLcompensateFinallyFlag[0].checked) {
		Lltext.style.display = "";
	} else {
		Lltext.style.display = "none";
	}
}

//查看出险时保单信息,在业务系统中进行保单还原

function backWardPolicy() {
	var SHOWTYPE = "SHOW";
	var BizNo = fm.prpLcompensatePolicyNo.value;
	var RiskCode = fm.prpLcompensateRiskCode.value;
	var damageDate = fm.damageDate.value;
	var vURL = '/claim/pages/common/pub/PolicyShowCenter.jsp?BIZTYPE=POLICY&SHOWTYPE=SHOW&BizNo=' + BizNo + '&RiskCode=' + RiskCode + '&damageDate=' + damageDate;
	window.open(vURL, '详细信息', 'width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}


function getArrangeRate(field) {
	var fieldname = field.name;
	var i = 0;
	var ArrangeRate_A;
	var prpLlossDtoKindCode_A;


	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == field) {
			findex = i;
			break;
		}
	}
	prpLlossDtoKindCode_A = fm.all("prpLlossDtoKindCode")[findex].value;
	ArrangeRate_A = parseFloat(fm.all("prpLlossDtoArrangeRate")[findex].value);

	if (isNaN(ArrangeRate_A) || ArrangeRate_A.length < 1) {
		ArrangeRate_A = 0;
	}

	for (i = 1; i < fm.all("prpLlossDtoArrangeRate").length; i++) {
		if (fm.all("prpLlossDtoKindCode")[i].value == prpLlossDtoKindCode_A) {
			fm.all("prpLlossDtoArrangeRate")[i].value = (round(ArrangeRate_A, 0), 0);
		}
	}
	calRealpay(field);

}

function getIndemnityDutyRate(field) {
	var fieldname = field.name;
	var i = 0;
	var findex = 0;
	var propSumLoss;
	var propSumReject;
	var propSumDefLoss;
	var IndemnityDutyRate_A;
	var prpLlossDtoKindCode_A;


	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == field) {
			findex = i;
			break;
		}
	}

	prpLlossDtoKindCode_A = fm.all("prpLlossDtoKindCode")[findex].value;
	IndemnityDutyRate_A = parseFloat(fm.all("prpLlossDtoIndemnityDutyRate")[findex].value);

	if (isNaN(IndemnityDutyRate_A) || IndemnityDutyRate_A.length < 1) {
		IndemnityDutyRate_A = 0;
	}
	for (i = 1; i < fm.all("prpLlossDtoIndemnityDutyRate").length; i++) {
		if (fm.all("prpLlossDtoKindCode")[i].value == prpLlossDtoKindCode_A) {
			fm.all("prpLlossDtoIndemnityDutyRate")[i].value = (round(IndemnityDutyRate_A, 0), 0);
		}
	}
	calRealpay(field);
}

/**
 @author 中科软
 @description 计算赔付标的中的赔偿金额（改变实际价值、核定损失、残值、责任比例时触发）
              计算赔款金额
              如果免赔高：（核定损失 - 残值）* 赔偿比例 * 责任比例 * （1 - 免赔率）
              # 此条取消----如果免赔额：（核定损失 - 残值）* 赔偿比例 * 责任比例  - 免赔额
 @param       无
 @return      无
 @see         UICommon.js#、round
*/

function calRealpayForDuBang(field) {

	var SumLoss; //核损金额
	var SumRest; //残值
	var ClaimRate; //赔偿比例
	var DutyDeductibleRate; //事故责任免赔率
	var DeductibleRate; //免赔率
	var DriverDeductibleRate; //驾驶员免赔率
	var mainKindCode; //主险代码
	var DeductibleRateOfMainKind; //主险的绝对免赔率
	var Deductible; //免赔额
	var Deductibletemp; //免赔
	var DutyRate; //责任比例
	var ArrangeRate; //协商赔偿比例
	var Realpay; //赔偿金额
	var temp;
	var unitPrice;
	var Amount;

	var fieldname = field.name;
	var findex = 0;
	try {
		for (i = 1; i < fm.all(fieldname).length; i++) {
			if (fm.all(fieldname)[i] == field) {
				findex = i;
				break;
			}
		}

		//实际损失
		SumLoss = parseFloat(fm.all("prpLlossDtoSumLoss")[findex].value);
		//残值
		SumRest = parseFloat(fm.all("prpLlossDtoSumRest")[findex].value);
		//核定赔偿
		SumDefPay = parseFloat(fm.all("prpLlossDtoSumDefPay")[findex].value);
		//承保比例
		ClaimRate = parseFloat(fm.all("prpLlossDtoClaimRate")[findex].value);
		//事故责任比例
		DutyRate = parseFloat(fm.all("prpLlossDtoIndemnityDutyRate")[findex].value);
		//协商赔偿比例
		ArrangeRate = parseFloat(fm.all("prpLlossDtoArrangeRate")[findex].value);
		//事故责任免赔率
		DutyDeductibleRate = parseFloat(fm.all("prpLlossDtoDutyDeductibleRate")[findex].value);
		//绝对免赔率
		DeductibleRate = parseFloat(fm.all("prpLlossDtoDeductibleRate")[findex].value);

		unitPrice = parseFloat(fm.all("prpLlossDtoUnitPrice")[findex].value);
		Amount = parseFloat(fm.all("prpLlossDtoAmount")[findex].value);
	} catch (E) {

	}
	if (isNaN(Amount))
		Amount = 0;
	if (isNaN(SumLoss))
		SumLoss = 0;
	if (isNaN(SumRest))
		SumRest = 0;
	if (isNaN(SumDefPay))
		SumDefPay = 0;
	if (isNaN(ClaimRate))
		ClaimRate = 0;
	else
		ClaimRate = ClaimRate / 100;
	if (isNaN(DutyRate))
		DutyRate = 0;
	else
		DutyRate = DutyRate / 100;
	if (isNaN(ArrangeRate))
		ArrangeRate = 0;
	else
		ArrangeRate = ArrangeRate / 100;
	if (isNaN(DutyDeductibleRate))
		DutyDeductibleRate = 0;
	else
		DutyDeductibleRate = DutyDeductibleRate / 100;
	if (isNaN(DeductibleRate))
		DeductibleRate = 0;
	else
		DeductibleRate = DeductibleRate / 100;
	if (isNaN(Deductible))
		Deductible = 0;

	if (isNaN(unitPrice))
		unitPrice = 0;

	/*计算赔款金额
	 * 如果免赔高：（核定损失 - 残值）* 赔偿比例 * 协商免赔率 * 责任比例 * （1 - 免赔率）
	 */

	try {
		var kindCode = fm.all("prpLlossDtoKindCode")[findex].value;

		if (kindCode == "C" || kindCode == "L") {
			//这些险种不需要按责任比例计算
			temp = (SumDefPay) * ClaimRate * ArrangeRate; //LYM 20060620
		} else {
			//其它险种需要按责任比例计算
			temp = (SumDefPay) * ClaimRate * ArrangeRate * DutyRate; //LYM 20060620
		}

		Deductibletemp = temp * DeductibleRate;

		//============个别附加险要获得主险的绝对免赔率
		if (fm.all("prpLlossDtoKindCode")[findex].value == "D2" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "D3" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "D4" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "Y" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "H") {
			mainKindCode = "B";
		}
		if (fm.all("prpLlossDtoKindCode")[findex].value == "G0" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "L" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "Z" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "Y" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "X") {
			mainKindCode = "A";
		}
		for (var j = 0; j < fm.all("prpLlossDtoKindCode").length; j++) {
			if (fm.all("prpLlossDtoKindCode")[j].value == mainKindCode) {
				DeductibleRateOfMainKind = fm.all("prpLlossDtoDeductibleRate")[j].value;
				break;
			}
		}
		//应 要求,屏蔽掉主险的责任免赔率
		DeductibleRateOfMainKind = 0;
		if (fm.all("prpLlossDtoKindCode")[findex].value == "D2" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "D3" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "D4" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "Y" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "G0" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "L" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "Z" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "X" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "H") {
			fm.all("PrpLlossDtoMainKindDuctibleRate")[findex].value = DeductibleRateOfMainKind;
			Realpay = temp * (1 - (DutyDeductibleRate + DeductibleRate)) * (1 - DeductibleRateOfMainKind / 100);

		} else if (fm.all("prpLlossDtoKindCode")[findex].value == "T" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "C") {
			//停驶、代步车调整
			Realpay = (temp * (1 - DutyDeductibleRate) - unitPrice);

		} else {
			Realpay = temp * (1 - (DutyDeductibleRate + DeductibleRate));

		}

		//reason:实赔处理，车上货物责任险、无过失责任险超限时系统的赔付计算不对
		if ((fm.all("prpLlossDtoKindCode")[findex].value == "B" || fm.all("prpLlossDtoKindCode")[findex].value == "D2" || fm.all("prpLlossDtoKindCode")[findex].value == "W") && temp > Amount) {
			if (Amount > 0) {
				Realpay = Amount * (1 - (DutyDeductibleRate + DeductibleRate));
			}
		}
		if (Realpay > Amount && Amount > 0) {
			Realpay = Amount;
		}
		//reason:险别是车损或三者，並且损失明细为27扣免赔，则赔偿金额=-残值
		if ((fm.all("prpLlossDtoKindCode")[findex].value == "A" || fm.all("prpLlossDtoKindCode")[findex].value == "B") && fm.all("prpLlossDtoFeeTypeCode")[findex].value == "27") {
			Realpay = SumRest * (-1);
		}
		fm.all("prpLlossDtoSumRealPay")[findex].value = (round(Realpay, 0), 0);

		if (fm.all("prpLlossDtoKindCode")[findex].value == "G") {
			fm.all("prpLlossDtoSumRealPay")[findex].value = (round(fm.all("prpLlossDtoSumDefPay")[findex].value, 0), 0);
		}


	} catch (E) {}
	calFund();

}

/**
 @author 中科软
 @description 计算赔付标的中的赔偿金额（改变实际价值、核定损失、残值、责任比例时触发）
              计算赔款金额
              如果免赔高：（核定损失 - 残值）* 赔偿比例 * 责任比例 * （1 - 免赔率）
              # 此条取消----如果免赔额：（核定损失 - 残值）* 赔偿比例 * 责任比例  - 免赔额
 @param       无
 @return      无
 @see         UICommon.js#、round
*/

function calRealpayForDuBangAll() {
	var SumLoss; //核损金额
	var SumRest; //残值
	var ClaimRate; //赔偿比例
	var DutyDeductibleRate; //事故责任免赔率
	var DeductibleRate; //免赔率
	var DriverDeductibleRate; //驾驶员免赔率
	var mainKindCode; //主险代码
	var DeductibleRateOfMainKind; //主险的绝对免赔率
	var Deductible; //免赔额
	var Deductibletemp; //免赔
	var DutyRate; //责任比例
	var ArrangeRate; //协商赔偿比例
	var Realpay; //赔偿金额
	var temp;
	var unitPrice;
	var Amount;

	var findex = 0;
	for (i = 1; i < fm.all("prpLlossDtoSumLoss").length; i++) {
		findex = i;
		SumLoss = parseFloat(fm.all("prpLlossDtoSumLoss")[findex].value);
		SumRest = parseFloat(fm.all("prpLlossDtoSumRest")[findex].value);
		SumDefPay = parseFloat(fm.all("prpLlossDtoSumDefPay")[findex].value);
		ClaimRate = parseFloat(fm.all("prpLlossDtoClaimRate")[findex].value);
		DutyRate = parseFloat(fm.all("prpLlossDtoIndemnityDutyRate")[findex].value);
		//协商赔偿比例
		ArrangeRate = parseFloat(fm.all("prpLlossDtoArrangeRate")[findex].value);
		DutyDeductibleRate = parseFloat(fm.all("prpLlossDtoDutyDeductibleRate")[findex].value);
		DeductibleRate = parseFloat(fm.all("prpLlossDtoDeductibleRate")[findex].value);
		unitPrice = parseFloat(fm.all("prpLlossDtoUnitPrice")[findex].value);
		Amount = parseFloat(fm.all("prpLlossDtoAmount")[findex].value);
		if (isNaN(Amount)) Amount = 0;
		if (isNaN(SumLoss)) SumLoss = 0;
		if (isNaN(SumRest)) SumRest = 0;
		if (isNaN(SumDefPay)) SumDefPay = 0;
		if (isNaN(Deductible)) Deductible = 0;
		if (isNaN(unitPrice)) unitPrice = 0;

		if (isNaN(ClaimRate))
			ClaimRate = 0;
		else
			ClaimRate = ClaimRate / 100;

		if (isNaN(DutyRate))
			DutyRate = 0;
		else
			DutyRate = DutyRate / 100;

		if (isNaN(ArrangeRate))
			ArrangeRate = 0;
		else
			ArrangeRate = ArrangeRate / 100;

		if (isNaN(DutyDeductibleRate))
			DutyDeductibleRate = 0;
		else
			DutyDeductibleRate = DutyDeductibleRate / 100;

		if (isNaN(DeductibleRate))
			DeductibleRate = 0;
		else
			DeductibleRate = DeductibleRate / 100;

		/*计算赔款金额
		 * 如果免赔高：（核定损失 - 残值）* 赔偿比例 * 责任比例 * （1 - 免赔率）
		 */
		var kindCode = fm.all("prpLlossDtoKindCode")[findex].value;
		if (kindCode == "C" || kindCode == "L" || kindCode == "G") {
			temp = (SumDefPay) * ClaimRate * ArrangeRate; //LYM 20060620
		} else {
			temp = (SumDefPay) * ClaimRate * ArrangeRate * DutyRate; //LYM 20060620	
		}
		//============个别附加险要获得主险的绝对免赔率
		if (kindCode == "D2" ||
			kindCode == "D3" ||
			kindCode == "D4" ||
			kindCode == "Y" ||
			kindCode == "H") {
			mainKindCode = "B";
		}
		if (kindCode == "G0" ||
			kindCode == "L" ||
			kindCode == "Z" ||
			kindCode == "Y" ||
			kindCode == "X") {
			mainKindCode = "A";
		}

		for (var j = 0; j < fm.all("prpLlossDtoKindCode").length; j++) {
			if (fm.all("prpLlossDtoKindCode")[j].value == mainKindCode) {
				DeductibleRateOfMainKind = fm.all("prpLlossDtoDeductibleRate")[j].value;
				break;
			}
		}
		//应 要求,屏蔽掉主险的责任免赔率
		DeductibleRateOfMainKind = 0;
		if (kindCode == "D2" ||
			kindCode == "D3" ||
			kindCode == "D4" ||
			kindCode == "Y" ||
			kindCode == "G0" ||
			kindCode == "L" ||
			kindCode == "Z" ||
			kindCode == "X" ||
			kindCode == "H") {
			fm.all("PrpLlossDtoMainKindDuctibleRate")[findex].value = DeductibleRateOfMainKind;
			Realpay = temp * (1 - (DutyDeductibleRate + DeductibleRate)) * (1 - DeductibleRateOfMainKind / 100);
		} else if (kindCode == "T" || kindCode == "C") {
			//停驶、代步车调整
			Realpay = (temp * (1 - DutyDeductibleRate) - unitPrice);
		} else {
			Realpay = temp * (1 - (DutyDeductibleRate + DeductibleRate));
		}
		//reason:实赔处理，车上货物责任险、无过失责任险超限时系统的赔付计算不对
		if ((kindCode == "B" || kindCode == "D2" || kindCode == "W") && temp > Amount) {
			if (Amount > 0) {
				Realpay = Amount * (1 - (DutyDeductibleRate + DeductibleRate));
			}
		}
		if (Realpay > Amount && Amount > 0) {
			Realpay = Amount;
		}
		//reason:险别是车损或三者，並且损失明细为27扣免赔，则赔偿金额=-残值
		if ((kindCode == "A" || kindCode == "B") && fm.all("prpLlossDtoFeeTypeCode")[findex].value == "27") {
			Realpay = SumRest * (-1);
		}
		fm.all("prpLlossDtoSumRealPay")[findex].value = (round(Realpay, 0), 0);
	}
	//计算责任赔款合计、赔款合计、其它费用、实赔金额
	calFund();
}

function calRealpay1ForDuBang(Field) {


	var fieldname = Field.name; //域名
	var i = 0;
	var findex = 0; //定位序号
	var SumLoss; //核损金额
	var SumRest; //残值
	var ClaimRate; //赔偿比例

	var DutyDeductibleRate; //事故责任免赔率
	var DriverDeductibleRate; //驾驶员免赔率
	var DeductibleRate; //绝对免赔率
	var MainKindDeductibleRate; //所在主险的绝对免赔率

	var Deductible; //免赔额
	var Deductibletemp; //免赔
	var DutyRate; //责任比例
	var ArrangeRate; //协商赔偿比例
	var Realpay; //赔付金额
	var temp;
	var Amount;

	//========================
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == Field) {
			findex = i;
			break;
		}
	}
	//============给变量赋值
	SumLoss = parseFloat(fm.all("prpLpersonLossSumLoss")[findex].value);
	SumDefPay = parseFloat(fm.all("prpLpersonLossSumDefPay")[findex].value);
	ClaimRate = parseFloat(fm.all("prpLpersonLossClaimRate")[findex].value);
	SumRest = 0;
	DeductibleRate = 0;
	Deductible = 0;
	Deductibletemp = 0;
	DutyRate = 0;
	ArrangeRate = 0;
	Realpay = 0;
	temp = 0;
	var findex1 = 0;
	for (i = 1; i < fm.all("prpLpersonLossIndemnityDutyRate").length; i++) {
		if (fm.all("prpLpersonLossSerialNo")[i].value == fm.all("personLossSerialNo")[findex].value) {
			findex1 = i;
			break;
		}
	}
	DutyRate = parseFloat(fm.all("prpLpersonLossIndemnityDutyRate")[findex1].value);
	ArrangeRate = parseFloat(fm.all("prpLpersonLossArrangeRate")[findex1].value);
	DeductibleRate = parseFloat(fm.all("prpLpersonLossDeductibleRate")[findex1].value);
	DutyDeductibleRate = parseFloat(fm.all("prpLpersonLossDutyDeductibleRate")[findex1].value);
	MainKindDeductibleRate = parseFloat(fm.all("prpLpersonLossMainKindDeductibleRate")[findex1].value);
	if (isNaN(SumLoss)) SumLoss = 0; //
	if (isNaN(SumDefPay)) SumDefPay = 0; //LYM 20060620


	if (isNaN(ClaimRate))
		ClaimRate = 0;
	else
		ClaimRate = ClaimRate / 100;
	if (isNaN(DeductibleRate))
		DeductibleRate = 0;
	else
		DeductibleRate = DeductibleRate / 100;

	if (isNaN(DutyDeductibleRate))
		DutyDeductibleRate = 0;
	else
		DutyDeductibleRate = DutyDeductibleRate / 100;

	if (isNaN(MainKindDeductibleRate))
		MainKindDeductibleRate = 0;
	else
		MainKindDeductibleRate = MainKindDeductibleRate / 100;
	if (isNaN(DutyRate))
		DutyRate = 0;
	else
		DutyRate = DutyRate / 100;

	if (isNaN(ArrangeRate))
		ArrangeRate = 0;
	else
		ArrangeRate = ArrangeRate / 100;


	/*计算赔款金额
	 * 如果免赔高：（核定损失 - 残值）* 赔偿比例 * 责任比例 * （1 - 免赔率）
	 */
	temp = (SumDefPay) * ClaimRate * DutyRate * ArrangeRate; //temp=（核定损失 - 残值）* 赔偿比例 * 责任比例  //LYM 20060620



	Deductibletemp = temp * DeductibleRate; //免赔= temp * 免赔率

	//================绝对免赔率==============================
	//部分险别需要承上所在主险的绝对免赔率
	if (fm.all("prpLpersonLossKindCode")[findex1].value == "D2" ||
		fm.all("prpLpersonLossKindCode")[findex1].value == "D3" ||
		fm.all("prpLpersonLossKindCode")[findex1].value == "D4" ||
		fm.all("prpLpersonLossKindCode")[findex1].value == "Y" ||
		fm.all("prpLpersonLossKindCode")[findex1].value == "G0" ||
		fm.all("prpLpersonLossKindCode")[findex1].value == "L" ||
		fm.all("prpLpersonLossKindCode")[findex1].value == "Z" ||
		fm.all("prpLpersonLossKindCode")[findex1].value == "X" ||
		fm.all("prpLpersonLossKindCode")[findex1].value == "H") {
		Realpay = temp * (1 - (DutyDeductibleRate + DeductibleRate)) * (1 - MainKindDeductibleRate);
	} else {
		Realpay = temp * (1 - (DutyDeductibleRate + DeductibleRate));

	}
	fm.all("prpLpersonLossSumRealPay")[findex].value = (round(Realpay, 0), 0);
	//计算赔付人员中的赔付合计
	calSumRealpay(Field);
	//计算责任赔款合计、赔款合计、其它费用、实赔金额
	calFund();
}

function setRealPay() {
	var flag = 0;
	for (i = 1; i < fm.all("prpLchargeSerialNo").length; i++) {
//		if (fm.all("prpLchargeChargeCode")[i].value == "03") {
//			fm.all("prpLchargeSumRealPay")[i].value = fm.all("prpLchargeChargeAmount")[i].value;
//			flag = 1;
//		} else {
			fm.all("prpLchargeSumRealPay")[i].value = 0;
//		}
	}
	if (flag == 1) {
		initChargeSumRealPay();
	}
	calFund();
}

function calSumPropAndPerson() {

	var i = 0;
	var findex = 0; //定位序号
	var qindex = 0;
	var SumLoss; //核损金额
	var UnitPrice; //单价
	var Quantity; //数量
	var AmountPrice = 0;
	var allPropPrice = 0;
	var allMedicalPrice = 0;
	var allDeformityPrice = 0;

	var allCompelPropLimit = 0;
	var allCompelMedicalLimit = 0;
	var allCompelDeformityLimit = 0;

	var allSuperCompelPropPrice = 0;
	var allSuperCompelMedicalPrice = 0;
	var allSuperCompelDeformityPrice = 0;


	var wealthTitle = ["财产损失", "0", "1000", "2000", "50", "26000", "10000", "10", "10", "8100", "8100"];
	var medicalTitle = ["医疗费用", "0", "20000", "2000", "50", "26000", "10000", "10", "10", "8100", "8100"];
	var deformityTitle = ["死亡残疾", "0", "40000", "2000", "50", "26000", "10000", "10", "10", "8100", "8100"];
	var kindcodetype = "B";


	for (i = 1; i < fm.all("propLicenseNo").length; i++) {
		allPropPrice = allPropPrice + (parseFloat(fm.all("propSumLoss")[i].value) - parseFloat(fm.all("propEliminate")[i].value));

	}

	if (isNaN(allPropPrice))
		allPropPrice = 0;

	for (i = 1; i < fm.all("personMedicalSerialNo").length; i++) {
		allMedicalPrice = allMedicalPrice + (parseFloat(fm.all("prpLpersonMedicalSumLoss")[i].value) - parseFloat(fm.all("prpLpersonMedicalRejectSum")[i].value));
	}
	if (isNaN(allMedicalPrice))
		allMedicalPrice = 0;

	for (i = 1; i < fm.all("personDeformitySerialNo").length; i++) {
		allDeformityPrice = allDeformityPrice + (parseFloat(fm.all("prpLpersonDeformitySumLoss")[i].value) - parseFloat(fm.all("prpLpersonDeformityRejectSum")[i].value));
	}
	if (isNaN(allDeformityPrice))
		allDeformityPrice = 0;




	qindex++;
	wealthTitle[qindex] = allPropPrice;
	medicalTitle[qindex] = allMedicalPrice;
	deformityTitle[qindex] = allDeformityPrice;
	qindex++;
	wealthTitle[qindex] = allCompelPropLimit;
	medicalTitle[qindex] = allCompelMedicalLimit;
	deformityTitle[qindex] = allCompelDeformityLimit;
	qindex++;
	wealthTitle[qindex] = allSuperCompelPropPrice;
	medicalTitle[qindex] = allSuperCompelMedicalPrice;
	deformityTitle[qindex] = allSuperCompelDeformityPrice;



	for (x = 0; x < fm.all("wealth").length; x++) {
		fm.wealth[x].value = wealthTitle[x];
	}
	for (x = 0; x < fm.all("medical").length; x++) {
		fm.medical[x].value = medicalTitle[x];
	}
	for (x = 0; x < fm.all("diedeformity").length; x++) {
		fm.diedeformity[x].value = deformityTitle[x];
	}
}
/**
 *
 *检测该险别是否可以輸入残值
 */

function checkInputPower(field) {
	var fieldname = field.name; //域名
	var i = 0;
	var findex = 0; //定位序号

	//定位
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == field) {
			findex = i;
			break;
		}
	}
	if (fm.all("prpLlossDtoKindCode")[findex].value == "B" && fm.all("prpLlossDtoFeeTypeCode")[findex].value != "27") {
		alert("B險別不允許輸入殘值！");
		setReadonlyOfElementOfLoss(fm.all("prpLlossDtoSumRest")[findex]);
		fm.all("prpLlossDtoSumRest")[findex].value = "0.00";
	}
}

/**
 *
 *修改该案的事故责任比例时将各项损失的事故责任比例改为该案的事故责任比例
 */

function setAllIndemnityDutyRate() {
	var i = 0;
	var indemnityDutyRate = 0;

	indemnityDutyRate = parseFloat(fm.prpLcompensateIndemnityDutyRate.value);
	if (isNaN(indemnityDutyRate) || indemnityDutyRate.length < 1) {
		indemnityDutyRate = 0;
	}

	if (fm.all("prpLlossDtoIndemnityDutyRate").length != 'undefined' && fm.all("prpLlossDtoIndemnityDutyRate").length > 0) {
		for (i = 1; i < fm.all("prpLlossDtoIndemnityDutyRate").length; i++) {
			fm.all("prpLlossDtoIndemnityDutyRate")[i].value = indemnityDutyRate;
		}

	}

	if (fm.all("prpLpersonLossIndemnityDutyRate").length != 'undefined' && fm.all("prpLpersonLossIndemnityDutyRate").length > 1) {
		for (i = 1; i < fm.all("prpLpersonLossIndemnityDutyRate").length; i++) {
			fm.all("prpLpersonLossIndemnityDutyRate")[i].value = indemnityDutyRate;
		}
	}
	calRealpayForDuBangAll();
	if (fm.all("prpLpersonLossIndemnityDutyRate").length != 'undefined' && fm.all("prpLpersonLossIndemnityDutyRate").length > 1) {

		for (i = 1; i < fm.all("prpLpersonLossIndemnityDutyRate").length; i++) {
			calRealpay2ForSunny(fm.all("prpLpersonLossIndemnityDutyRate")[i]);
		}
	}
	return true;
}


//商业险是否超过限额判断

function checkQuotaByKindCode() {

	for (var i = 0; i < fm.all("kindCode").length; i++) {
		var sumRealPayTemp = 0;
		var sumRealPay = 0;

		var kindCode = fm.all("kindCode")[i].value;
		var KindAmount = parseFloat(fm.all("kindAmount")[i].value);


		for (var k = 1; k < fm.all("prpLlossDtoKindCode").length; k++) {
			if (fm.all("prpLlossDtoKindCode")[k].value == kindCode) {
				sumRealPayTemp += parseFloat(fm.all("prpLlossDtoSumDefPay")[k].value);
				if (isNaN(sumRealPayTemp)) sumRealPayTemp = 0;
			}
		}

		for (var j = 1; j < fm.all("prpLpersonLossKindCode").length; j++) {
			if (fm.all("prpLpersonLossKindCode")[j].value == kindCode) {
				sumRealPayTemp += parseFloat(fm.all("prpLpersonLossSumDefPay1")[j].value);
				if (isNaN(sumRealPayTemp)) sumRealPayTemp = 0;
			}

		}

		sumRealPay = sumRealPayTemp;
		if (kindCode == 'B' && (sumRealPay > KindAmount)) { //目前只考虑B
			alert(" 第三者責任險賠償金額超過了賠償限額(" + KindAmount + " 元),請調整核定賠款，再重新產生理算報告");
			return false;
		} else { //其他险别

		}
	}
	return true;

}




//设置事故责任免赔率

function changeAccidentDeductRate() {
	var indemnityDuty = fm.indemnityDuty.value; //责任
	var deductRate = ""; //责任免赔率
	var kindDuty = ""; //险别责任拼串  
	var kindCode = "";
	var strKindDuty = "";

	var i = 0; //循环使用
	var j = 0;
	for (j = 0; j < fm.all("kindCodedutyName").length; j++) { //1
		kindDuty = fm.all("kindCodedutyName")[j].value;
		deductRate = fm.all("deductRate")[j].value;

		//改变已经存在的
		for (i = 0; i < fm.all("prpLlossDtoKindCode").length; i++) { //-----标的
			kindCode = fm.all("prpLlossDtoKindCode")[i].value;
			strKindDuty = kindCode + indemnityDuty;
			if (kindDuty == strKindDuty) {
				fm.all("prpLlossDtoDutyDeductibleRate")[i].value = deductRate;
			}
		}
		for (i = 0; i < fm.all("prpLpersonLossKindCode").length; i++) { //----人伤 
			kindCode = fm.all("prpLpersonLossKindCode")[i].value;
			strKindDuty = kindCode + indemnityDuty;
			if (kindDuty == strKindDuty) {
				fm.all("prpLpersonLossDutyDeductibleRate")[i].value = deductRate;
			}
		}
		//不存在的当增加时代出最新的免赔率
		var deductibleCount = fm.all("prpLlossDtoKindCodeShow").length;
		for (var i = 0; i < deductibleCount; i++) {
			kindCode = fm.all("prpLlossDtoKindCodeShow")[i].value;
			strKindDuty = kindCode + indemnityDuty;
			if (kindDuty == strKindDuty) {
				fm.DutyDeductibleRate[i].value = deductRate;
			}
		}
	}
}

//人伤信息，选择了险别後把事故责任免赔率和绝对免赔率带出来

function getPersonDeductRate() {
	var indemnityDuty = fm.indemnityDuty.value; //责任

	var deductRate = ""; //责任免赔率
	var kindDuty = ""; //险别责任拼串  
	var kindCode = "";
	var strKindDuty = "";

	var i = 0; //循环使用
	var j = 0;
	var m = 0;
	var n = 0;

	for (j = 0; j < fm.all("kindCodedutyName").length; j++) { //1
		kindDuty = fm.all("kindCodedutyName")[j].value;
		deductRate = fm.all("deductRate")[j].value;


		for (i = 0; i < fm.all("prpLpersonLossKindCode").length; i++) { //----人伤 
			kindCode = fm.all("prpLpersonLossKindCode")[i].value;
			strKindDuty = kindCode + indemnityDuty;
			if (kindDuty == strKindDuty) {
				fm.all("prpLpersonLossDutyDeductibleRate")[i].value = deductRate;
			}
		}

	}

	for (m = 0; m < fm.all("kindCode2").length; m++) { //绝对免赔率

		kindCode2 = fm.all("kindCode2")[m].value;
		absolutDeductRate = fm.all("absolutDeductRate")[m].value;

		for (n = 0; n < fm.all("prpLpersonLossKindCode").length; n++) { //----人伤 
			kindCode = fm.all("prpLpersonLossKindCode")[n].value;
			if (kindCode == kindCode2) {
				fm.all("prpLpersonLossDeductibleRate")[n].value = absolutDeductRate;
			}
		}

	}
}



//function creatCoins() {
//
//
//	var countFlag = fm.countFlag.value;
//	if (countFlag == '1') {
//		deleteallRow1('Coins', 'Coins_Data');
//	}
//	if (isSameKindCode() == false) {
//		return false;
//	}
//
//	var oldAction = fm.action;
//	var oldTarget = fm.target;
//	fm.action = "/claim/compensate/compensateCoins.do";
//	fm.target = "fraCalculate";
//	fm.submit();
//
//	fm.action = oldAction;
//	fm.target = oldTarget;
//
//	return true;
//}

function isSameKindCode() {
	var count = getElementCount("prpLchargeSerialNo") - 1;
	if (count > 1) {

		for (var i = 1; i < count; i++) {

			if (fm.prpLchargeChargeCode[count].value == fm.prpLchargeChargeCode[i].value) {

				alert("同種費用不能重複輸入!");
				return false;
			}
		}

	}
	return true
}

function deleteallRow1(pageCode, dataPageCode)　　 {　　
	var index = 0; //当前table索引
	　　
	var oTBODY = document.getElementsByName(pageCode)[0].tBodies.item;　　
	var oTBODYData = document.getElementById(pageCode).tBodies.item(0);
	var oldelementNumber = oTBODYData.rows.length;　　　　
	for (var i = 0; i < oldelementNumber; i++)　　 {　　
		oTBODYData.removeChild(oTBODYData.rows[0]);　　
	}　　
}

function creatCoinsFlag(countFlag) {
	fm.countFlag.value = countFlag;
}

function checkIndemnityDutyRate() {
	var compensateIndemnityDuty = fm.indemnityDuty.value;
	if (compensateIndemnityDuty == '0' || compensateIndemnityDuty == '2') {
		if (parseInt(fm.prpLcompensateIndemnityDutyRate.value) != 100 || parseInt(fm.prpLcompensateIndemnityDutyRate.value) != 50) {
			alert("事故責任爲全責或同責時，責任比例不可修改！");
			return false;
		}
	} else {
		if (compensateIndemnityDuty == '1') {
			if (parseInt(fm.prpLcompensateIndemnityDutyRate.value) > 99 || parseInt(fm.prpLcompensateIndemnityDutyRate.value) < 51) {
				fm.prpLcompensateIndemnityDutyRate.value = "70";
				alert("事故責任爲主責時，責任比例不能大於99%、小於51%");
				return false;
			}
		}
		if (compensateIndemnityDuty == '3') {
			if (parseInt(fm.prpLcompensateIndemnityDutyRate.value) > 51 || parseInt(fm.prpLcompensateIndemnityDutyRate.value) < 1) {
				fm.prpLcompensateIndemnityDutyRate.value = "30";
				alert("事故責任爲主責時，責任比例不能大於51%、小於1%");
				return false;
			}
		}
		if (compensateIndemnityDuty == '9' || compensateIndemnityDuty == '4') {
			fm.prpLcompensateIndemnityDutyRate.value = "0";
			alert("事故責任爲無責或其他時，責任比例不可修改！");
			return false;
		}

	}
	return true;

}



function getObjectMessage() {
	var findex = 0;
	for (i = 1; i < fm.prpLlossDtoKindCode.length; i++) {
		findex = i;
	}

	if (fm.prpLcompensateIndemnityDutyRate) {
		fm.prpLlossDtoIndemnityDutyRate[findex].value = fm.prpLcompensateIndemnityDutyRate.value;
	}
	fm.prpLlossDtoArrangeRate[findex].value = 100.0;
	fm.prpLlossDtoClaimRate[findex].value = 100.0;
}


function getObjectMessage2() {
	var findex = 0;
	for (i = 1; i < fm.prpLpersonLossKindCode.length; i++) {
		findex = i;
	}

	if (fm.prpLcompensateIndemnityDutyRate) {
		fm.prpLpersonLossIndemnityDutyRate[findex].value = fm.prpLcompensateIndemnityDutyRate.value;
	}
	fm.prpLpersonLossArrangeRate[findex].value = 100.0;
}


function initExceptDeductible() {
	var flag = 0;
	var findexExceptDeductible = 0;
	var exceptDeductibleRate = 0;
	var exceptDeductiblePay = 0;
	var exceptDeductibleRateAll = 0;
	if (fm.exceptDeductibleKindCode) {
		for (var index = 1; index < fm.exceptDeductibleKindCode.length; index++) {
			fm.exceptDeductiblePay[index].value = 0;
		}
		for (var index1 = 0; index1 < fm.prpLlossDtoSerialNo.length; index1++) {
			for (var index = 0; index < fm.exceptDeductibleKindCode.length; index++) {
				if (fm.exceptDeductibleKindCode[index].value != "")
					if (fm.prpLlossDtoKindCode[index1].value == fm.exceptDeductibleKindCode[index].value) {
						exceptDeductiblePay = parseFloat(fm.exceptDeductiblePay[index].value);
						exceptDeductiblePay += parseFloat(fm.prpLlossDtoExceptDeductiblePay[index1].value);
						fm.exceptDeductiblePay[index].value = (round(exceptDeductiblePay, 0), 0);
						fm.exceptDeductibleRate[index].value = fm.prpLlossDtoExceptDeductibleRate[index1].value;
					}
			}
		}
		for (var index2 = 1; index2 < fm.personLossSerialNo.length; index2++) {
			var findex;
			for (var c = 1; c < fm.prpLpersonLossSerialNo.length; c++) {
				if (fm.personLossSerialNo[index2].value == fm.prpLpersonLossSerialNo[c].value)
					findex = c;
			}
			for (var index = 1; index < fm.exceptDeductibleKindCode.length; index++) {
				if (fm.exceptDeductibleKindCode[index].value != "")
					if (fm.prpLpersonLossKindCode[findex].value == fm.exceptDeductibleKindCode[index].value) {
						exceptDeductiblePay = parseFloat(fm.exceptDeductiblePay[index].value);
						exceptDeductiblePay += parseFloat(fm.prpLpersonLossExceptDeductiblePay[index2].value);
						fm.exceptDeductiblePay[index].value = (round(exceptDeductiblePay, 0), 0);
						fm.exceptDeductibleRate[index].value = fm.prpLpersonLossExceptDeductibleRate[index2].value;
					}
			}
		}


		if (fm.prpLchargeSerialNo) {
			for (var index3 = 0; index3 < fm.prpLchargeSerialNo.length; index3++) {
				for (var index = 0; index < fm.exceptDeductibleKindCode.length; index++) {
					if (fm.exceptDeductibleKindCode[index].value != "") {
						if (fm.prpLchargeKindCode[index3].value == 'A' && fm.exceptDeductibleKindCode[index].value == 'A') {
							if ('03' == fm.prpLchargeChargeCode[index3].value) {
								exceptDeductiblePay = parseFloat(fm.exceptDeductiblePay[index].value);
								exceptDeductiblePay = exceptDeductiblePay + parseFloat(fm.prpLchargeExceptDeductiblePay[index3].value);
								fm.exceptDeductiblePay[index].value = (round(exceptDeductiblePay, 0), 0);
							}
						}
						if (fm.prpLchargeKindCode[index3].value == 'B' && fm.exceptDeductibleKindCode[index].value == 'B') {
							if ('03' == fm.prpLchargeChargeCode[index3].value) {
								exceptDeductiblePay = parseFloat(fm.exceptDeductiblePay[index].value);
								exceptDeductiblePay = exceptDeductiblePay + parseFloat(fm.prpLchargeExceptDeductiblePay[index3].value);
								fm.exceptDeductiblePay[index].value = (round(exceptDeductiblePay, 0), 0);
							}
						}
					}
				}
			}
		}

		for (var index1 = 1; index1 < fm.exceptDeductibleKindCode.length; index1++) {
			exceptDeductibleRateAll = parseFloat(exceptDeductibleRateAll) + parseFloat(fm.exceptDeductiblePay[index1].value);
		}
		fm.exceptDeductibleRateAll.value = (round(exceptDeductibleRateAll, 0), 0);
	}
}


function showManyCar() {
	window.open("../pages/common/carsDamageCount/DAACarsDamageCount.htm");
}

function insertRow2(field) {
	var flag = 0;
	var kind;
	var fieldname = field.name;
	var findex = 0;
	for (var i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == field) {
			findex = i;
			break;
		}
	}
	if (fieldname == "prpLpersonLossKindCode" || fieldname == "prpLpersonLossKindName")
		fieldname = fm.prpLpersonLossKindCode[findex];
	else if (fieldname == "prpLlossDtoKindCode" || fieldname == "prpLlossDtoKindName")
		fieldname = fm.prpLlossDtoKindCode[findex];
	else
		fieldname = fm.prpLchargeKindCode[findex];
	kind = fieldname.value;
	//如果还未确定险别，放弃添加
	if (isEmptyField(field)) {
		flag = 2;
	} else {
		//如果还没有不计免赔数据，放弃判断 
		if (fm.exceptDeductibleKindCode) {
			//判断不计免赔栏中是否已经有了这个险别

			if (fm.exceptDeductibleKindCode) {
				for (var index = 0; index < fm.exceptDeductibleKindCode.length; index++) {
					if (fm.exceptDeductibleKindCode[index].value != "")
						if (fm.exceptDeductibleKindCode[index].value == fieldname.value)
							flag = 2;
				}
			}
		}
		//进行进一步判断
		if (flag == 0) {
			//调用dwr函数进行判断进行
			checkExceptDeductible(kind, field);
		}
	}
}
//用於在不计免赔栏中删除数据

function deleteRow2(field, pagecode) {
	var fieldValue = "";
	var lossKind = "";
	var lossKind1 = "";
	var flag = 0;
	var countExcept = 0;
	var countLoss = 0;
	var findexExcept = 0;
	var findexLoss = 0;
	var order = getElementOrder(field);
	//如果没有不计免赔数据，放弃操作
	if (fm.exceptDeductibleKindCode) {
		if (pagecode == 'CompensateLoss') {
			fieldValue = fm.prpLlossDtoKindCode[order - 1].value;
			lossKind = "fm.prpLlossDtoKindCode";
			lossKind1 = "fm.prpLlossDtoExceptDeductiblePay";
		} else if (pagecode == 'Person') {
			fieldValue = fm.prpLpersonLossKindCode[order - 1].value;
			lossKind = "fm.prpLpersonLossKindCode";
			lossKind1 = "fm.prpLpersonLossExceptDeductiblePay";
		} else {
			fieldValue = fm.prpLchargeKindCode[order - 1].value;
			lossKind = "fm.prpLchargeKindCode";
			lossKind1 = "fm.prpLchargeExceptDeductiblePay";
		}
		//判断不计免赔栏中是否有该险别 
		for (var index = 0; index < fm.exceptDeductibleKindCode.length; index++) {
			if (fm.exceptDeductibleKindCode[index].value != "")
				if (fm.exceptDeductibleKindCode[index].value == fieldValue) {
					flag = 2;
					findexExcept = index;
				}
		}
		if (flag == 2) //如果没有该险种的不计免赔就放弃以下操作
		{
			//判断该险别是否还有其它损失
			if (fm.prpLlossDtoKindCode) {
				for (var index1 = 0; index1 < fm.prpLlossDtoKindCode.length; index1++) {
					if (fm.prpLlossDtoKindCode[index1].value == fieldValue) {
						if ("fm.prpLlossDtoKindCode" == lossKind) {
							findexLoss = index1;
						}
						countLoss++;
					}
				}
			}
			if (fm.prpLpersonLossKindCode) {
				for (var index2 = 0; index2 < fm.prpLpersonLossKindCode.length; index2++) {
					if (fm.prpLpersonLossKindCode[index2].value == fieldValue) {
						if ("fm.prpLpersonLossKindCode" == lossKind) {
							findexLoss = index2;
						}
						countLoss++;
					}
				}
			}
			if (fm.prpLchargeKindCode) {
				for (var index3 = 0; index3 < fm.prpLchargeKindCode.length; index3++) {
					if (fm.prpLchargeKindCode[index3].value == fieldValue) {
						if ("fm.prpLchargeKindCode" == lossKind) {
							findexLoss = index3;
						}
						if (!('03' == fm.prpLchargeChargeCode[index3].value)) {
							countLoss++;
						}
					}
				}
			}
			//针对不同情况作不同的处理
			if (countLoss >= 1) {
				if (eval(lossKind1 + "[" + findexLoss + "]")) {
					if (fm.exceptDeductiblePay[findexExcept].value != "" && eval(lossKind1 + "[" + findexLoss + "].value") != "") {
						fm.exceptDeductiblePay[findexExcept].value = parseInt(fm.exceptDeductiblePay[findexExcept].value) - parseInt(eval(lossKind1 + "[" + findexLoss + "].value"));
					}
				}
			} else {
				deleteRow(fm.exceptDeductibleKindCode[findexExcept], 'exceptLoss1');
			}

		}
	}
}
//add by lidonghui 2007-05-19用於检测不计免赔栏中的险种是否和受损险种一致

function checkExcept4() {
	var flag = 0;
	if (fm.exceptDeductibleKindCode) {

		for (var index = 0; index < fm.exceptDeductibleKindCode.length; index++) {

			if (fm.exceptDeductibleKindCode[index].value != "") {
				if (fm.prpLlossDtoKindCode) {
					for (var index1 = 0; index1 < fm.prpLlossDtoKindCode.length; index1++) {
						if (fm.prpLlossDtoKindCode[index1].value != "")
							if (fm.prpLlossDtoKindCode[index1].value == fm.exceptDeductibleKindCode[index].value) {
								flag = 1;
							}
					}
				}
				if (fm.prpLpersonLossKindCode) {
					for (var index2 = 0; index2 < fm.prpLpersonLossKindCode.length; index2++) {
						if (fm.prpLpersonLossKindCode[index2].value != "")
							if (fm.prpLpersonLossKindCode[index2].value == fm.exceptDeductibleKindCode[index].value) {
								flag = 1;
							}
					}
				}
				if (fm.prpLchargeKindCode) {
					for (var index3 = 0; index3 < fm.prpLchargeKindCode.length; index3++) {
						if (fm.prpLchargeKindCode[index3].value != "")
							if (fm.prpLchargeKindCode[index3].value == fm.exceptDeductibleKindCode[index].value) {
								flag = 1;
							}
					}
				}
			}
			if (flag == 0 && fm.exceptDeductibleKindCode[index].value != "") {
				if (parseInt(fm.exceptDeductibleRateAll.value) != 0) {
					fm.exceptDeductibleRateAll.value = parseInt(fm.exceptDeductibleRateAll.value) - parseInt(fm.exceptDeductiblePay[index].value);
				}
				deleteRow(fm.exceptDeductibleKindCode[index], 'exceptLoss1');

			}
			flag = 0;
		}

	}
}
//add by lidonghui 汇总各险别的实赔金额及核定赔偿金额

function initEvryTypeRealPay() {
	var sumDefPayAllShow = 0;
	var sumRealPayAllShow = 0;
	var sumDefPayAllShowMiddle = 0;
	if (fm.prpLlossDtoKindCodeShow) {
		for (var index = 0; index < fm.prpLlossDtoKindCodeShow.length; index++) {
			fm.prpLsumDefPayAllShow[index].value = 0;
			fm.prpLsumRealPayAllShow[index].value = 0;
			fm.prpLsumDefPayAllShowMiddle[index].value = 0;
		}
		for (var index1 = 0; index1 < fm.prpLlossDtoSerialNo.length; index1++) {
			for (var index = 0; index < fm.prpLlossDtoKindCodeShow.length; index++) {
				if (fm.prpLlossDtoKindCodeShow[index].value != "")
					if (fm.prpLlossDtoKindCodeShow[index].value == fm.prpLlossDtoKindCode[index1].value) {
						if ((fm.prpLlossDtoKindCode[index1].value == 'Y' || fm.prpLlossDtoKindCode[index1].value == 'S') && fm.licenseNo[index1].value == fm.prpLcompensateLicenseNo.value) {} else {
							sumDefPayAllShow = parseFloat(fm.prpLsumDefPayAllShow[index].value);
							sumDefPayAllShow += parseFloat(fm.prpLlossDtoSumDefPay[index1].value);
							fm.prpLsumDefPayAllShow[index].value = sumDefPayAllShow;
							sumDefPayAllShowMiddle = parseFloat(fm.prpLsumDefPayAllShowMiddle[index].value);
							sumDefPayAllShowMiddle += parseFloat(fm.prpLlossDtoSumRest[index1].value);
							sumDefPayAllShowMiddle += parseFloat(fm.prpLlossDtoCompelPay[index1].value);
							fm.prpLsumDefPayAllShowMiddle[index].value = sumDefPayAllShowMiddle;
							sumRealPayAllShow = parseInt(fm.prpLsumRealPayAllShow[index].value);
							sumRealPayAllShow += parseInt(fm.prpLlossDtoSumRealPay[index1].value);
							fm.prpLsumRealPayAllShow[index].value = sumRealPayAllShow;
						}
					}
			}
		}
		for (var index2 = 0; index2 < fm.prpLpersonLossSerialNo.length; index2++) {
			for (var index = 0; index < fm.prpLlossDtoKindCodeShow.length; index++) {
				if (fm.prpLlossDtoKindCodeShow[index].value != "")
					if (fm.prpLlossDtoKindCodeShow[index].value == fm.prpLpersonLossKindCode[index2].value) {
						sumDefPayAllShow = parseFloat(fm.prpLsumDefPayAllShow[index].value);
						sumDefPayAllShow += parseFloat(fm.prpLpersonLossSumDefPay1[index2].value);
						fm.prpLsumDefPayAllShow[index].value = sumDefPayAllShow;
						sumRealPayAllShow = parseInt(fm.prpLsumRealPayAllShow[index].value);
						sumRealPayAllShow += parseInt(fm.prpLpersonLossSumRealPay1[index2].value);
						fm.prpLsumRealPayAllShow[index].value = sumRealPayAllShow;
						for (var j = 1; j < fm.personLossSerialNo.length; j++) {
							if (fm.personLossSerialNo[j].value == fm.prpLpersonLossSerialNo[index2].value) {
								sumDefPayAllShowMiddle = parseFloat(fm.prpLsumDefPayAllShowMiddle[index].value);
								sumDefPayAllShowMiddle += parseFloat(fm.prpLpersonLossCompelPay[j].value);
								fm.prpLsumDefPayAllShowMiddle[index].value = sumDefPayAllShowMiddle;
							}
						}
					}
			}
		}
		initChargeSumRealPay();
		if (deepth1 == -1 && deepth2 == -1) {
			checkAmountn();
			showMessage5();
		}
	}
}

function showMessage5() {

	var message = fm.compensateMessage1.value;
	if (message != "") {
		alert(message);
		fm.compensateMessage1.value = "";
	}
}
//用於计算车辆的实际价值

function calRealValuen() {
	var finalValue = parseFloat(fm.finalValue.value);
	var purchasePrice = parseFloat(fm.purchasePrice.value);
	var factValue = 0;
	if (finalValue != 0) {
		factValue = purchasePrice * finalValue;
	}
	fm.factValue.value = (round(factValue, 0), 0);
}
//用於控制A险的输入及B险的输入

function inputControl(field) {}
//该函数用於在B险超限时各损失类别之间分配比例

function distributeAmount(amount, realPayAll, kindCode) {
	if (fm.prpLlossDtoKindCode) {
		for (var index = 1; index < fm.prpLlossDtoKindCode.length; index++) {
			if (fm.prpLlossDtoKindCode[index].value == kindCode && fm.prpLlossDtoSumDefPay[index].value != "") {
				if ((kindCode == 'Y' || kindCode == 'S') && fm.licenseNo[index].value == fm.prpLcompensateLicenseNo.value) {} else {
					var dutyRate = parseInt(fm.prpLlossDtoIndemnityDutyRate[index].value);
					var dutyDeductibleRate = parseInt(fm.prpLlossDtoDutyDeductibleRate[index].value);
					var deductibleRate = parseInt(fm.prpLlossDtoDeductibleRate[index].value);

					//超限额分摊，赔款不乘事故责任 add by liping 2008-05-28
					var amount1 = parseFloat(amount * (1 - dutyDeductibleRate / 100 - deductibleRate / 100));
					if (parseInt(fm.prpLlossDtoSumDefPay[index].value) != 0) {
						var sumDefPay = parseFloat(fm.prpLlossDtoSumDefPay[index].value);
						var sumRest = parseFloat(fm.prpLlossDtoSumRest[index].value);
						var compelPay = parseFloat(fm.prpLlossDtoCompelPay[index].value);
						fm.prpLlossDtoSumRealPay[index].value = (round(((sumDefPay - sumRest - compelPay) / realPayAll) * amount1, 0), 0);
					}
					if (parseInt(fm.prpLlossDtoExceptDeductibleRate[index].value) != 0) {
						var exceptDeductibleRate = parseFloat(fm.prpLlossDtoExceptDeductibleRate[index].value);
						//超限额分摊，不再乘事故责任 add by liping 2008-05-28
						var exceptDeductPay = parseFloat(amount * exceptDeductibleRate / 100);
						var exceptDeductiblePay1 = ((sumDefPay - sumRest - compelPay) / realPayAll) * exceptDeductPay;
						fm.prpLlossDtoExceptDeductiblePay[index].value = (round(exceptDeductiblePay1, 0), 0);
						initExceptDeductible();
					}
				}
			}
		}
	}
	if (fm.prpLpersonLossKindCode) {
		for (var index = 1; index < fm.prpLpersonLossKindCode.length; index++) {
			if (fm.prpLpersonLossKindCode[index].value == kindCode && fm.prpLpersonLossSumDefPay1[index].value != "") {
				if (parseInt(fm.prpLpersonLossSumDefPay1[index].value) != 0) {
					var dutyRate = parseInt(fm.prpLpersonLossIndemnityDutyRate[index].value);
					var dutyDeductibleRate = parseInt(fm.prpLpersonLossDutyDeductibleRate[index].value);
					var deductibleRate = parseInt(fm.prpLpersonLossDeductibleRate[index].value);
					//超限额分摊，不再乘事故责任 add by liping 2008-05-28
					var amount1 = parseFloat(amount * (1 - dutyDeductibleRate / 100 - deductibleRate / 100));
					for (var i = 1; i < fm.personLossSerialNo.length; i++) {
						if (fm.personLossSerialNo[i].value == fm.prpLpersonLossSerialNo[index].value) {
							if (parseInt(fm.prpLpersonLossSumDefPay[i].value) != 0) {
								var sumDefPay = parseFloat(fm.prpLpersonLossSumDefPay[i].value);
								var compelPay = parseFloat(fm.prpLpersonLossCompelPay[i].value);
								fm.prpLpersonLossSumRealPay[i].value = (round(((sumDefPay - compelPay) / realPayAll) * amount1, 0), 0);
							}
							if (parseInt(fm.prpLpersonLossExceptDeductibleRate[i].value) != 0) {
								var exceptDeductibleRate = parseFloat(fm.prpLpersonLossExceptDeductibleRate[i].value);
								//超限额分摊，不再乘事故责任 add by liping 2008-05-28	                  
								var exceptDeductiblePay = amount * exceptDeductibleRate / 100
								var exceptDeductiblePay1 = exceptDeductiblePay * ((sumDefPay - compelPay) / realPayAll);
								fm.prpLpersonLossExceptDeductiblePay[i].value = (round(exceptDeductiblePay1, 0), 0);
								initExceptDeductible();
							}
							calSumRealpay(fm.personLossSerialNo[i]);
						}
					}
				}
			}
		}
	}
	calFund();
}
//用来校验是否超限以及该做何种处理

function checkAmountn() {
	var dutyRate = fm.prpLcompensateIndemnityDutyRate.value;

	for (var index = 0; index < fm.prpLlossDtoKindCodeShow.length; index++) {
		if ((parseInt((fm.prpLsumDefPayAllShow[index].value) - parseInt(fm.prpLsumDefPayAllShowMiddle[index].value)) * parseInt(dutyRate) / 100) > (round(fm.kindAmount[index].value, 0), 0)) {
			if (fm.prpLlossDtoKindCodeShow[index].value == 'B') //三者险超限提示並处理
			{
				var message = fm.compensateMessage1.value;
				var re = new RegExp("第三者责任险别核定赔偿金额超限，将在各条损失之间分摊费用！", "i");
				var r = message.match(re);
				if (r == null) {
					message = message + "第三者责任险别核定赔偿金额超限，将在各条损失之间分摊费用！\n";
				}
				fm.compensateMessage1.value = message;
				distributeAmount(round(fm.kindAmount[index].value, 0), (parseFloat(fm.prpLsumDefPayAllShow[index].value) - parseFloat(fm.prpLsumDefPayAllShowMiddle[index].value)), fm.prpLlossDtoKindCodeShow[index].value);
			} else if (fm.prpLlossDtoKindCodeShow[index].value == 'Y') //教练车特约超限提示並处理(按三者处理时)
			{
				var message = fm.compensateMessage1.value;
				var re = new RegExp("教练车特约条款核定赔偿金额超限，将在各条损失之间分摊费用！", "i");
				var r = message.match(re);
				if (r == null) {
					message = message + "教练车特约条款核定赔偿金额超限，将在各条损失之间分摊费用！\n";
				}
				fm.compensateMessage1.value = message;
				distributeAmount(round(fm.kindAmount[index].value, 0), (parseFloat(fm.prpLsumDefPayAllShow[index].value) - parseFloat(fm.prpLsumDefPayAllShowMiddle[index].value)), fm.prpLlossDtoKindCodeShow[index].value);
			} else if (fm.prpLlossDtoKindCodeShow[index].value == 'S') //附加出境保险超限提示並处理（按三者）
			{
				var message = fm.compensateMessage1.value;
				var re = new RegExp("附加出境条款核定赔偿金额超限，将在各条损失之间分摊费用！", "i");
				var r = message.match(re);
				if (r == null) {
					message = message + "附加出境条款核定赔偿金额超限，将在各条损失之间分摊费用！\n";
				}
				fm.compensateMessage1.value = message;
				distributeAmount(round(fm.kindAmount[index].value, 0), (parseFloat(fm.prpLsumDefPayAllShow[index].value) - parseFloat(fm.prpLsumDefPayAllShowMiddle[index].value)), fm.prpLlossDtoKindCodeShow[index].value);
			} else if (fm.prpLlossDtoKindCodeShow[index].value == 'X') //新增加设备损失险别超限提示
			{
				var message = fm.compensateMessage1.value;
				var re = new RegExp("新增加设备损失险别核定赔偿金额超限，将按限额调整！", "i");
				var r = message.match(re);
				if (r == null) {
					message = message + "新增加设备损失险别核定赔偿金额超限，将按限额调整！\n";
				}
				fm.compensateMessage1.value = message;
				doK2(round(fm.kindAmount[index].value, 0), (parseFloat(fm.prpLsumDefPayAllShow[index].value) - parseFloat(fm.prpLsumDefPayAllShowMiddle[index].value)), fm.prpLlossDtoKindCodeShow[index].value);
			} else if (fm.prpLlossDtoKindCodeShow[index].value == 'D11') //车上人员(驾驶员)超限提示並处理
			{
				var message = fm.compensateMessage1.value;
				var re = new RegExp("车上人员责任险别（驾驶员）核定赔偿金额超限，按限额调整！", "i");
				var r = message.match(re);
				if (r == null) {
					message = message + "车上人员责任险别（驾驶员）核定赔偿金额超限，按限额调整！\n";
				}
				fm.compensateMessage1.value = message;
				doD11(parseInt(fm.kindAmount[index].value), parseFloat(parseFloat(fm.prpLsumDefPayAllShow[index].value) - parseFloat(fm.prpLsumDefPayAllShowMiddle[index].value)));
			} else if (fm.prpLlossDtoKindCodeShow[index].value == 'L') //车身划痕险超限提示
			{
				var message = fm.compensateMessage1.value;
				var re = new RegExp("车身划痕险核定赔偿金额超限，按限额调整！", "i");
				var r = message.match(re);
				if (r == null) {
					message = message + "车身划痕险核定赔偿金额超限，按限额调整！\n";
				}
				fm.compensateMessage1.value = message;
			} else if (fm.prpLlossDtoKindCodeShow[index].value == 'NZ') //随车行李物品超限提示
			{
				var message = fm.compensateMessage1.value;
				var re = new RegExp("随车行李物品险核定赔偿金额超限，按限额调整！", "i");
				var r = message.match(re);
				if (r == null) {
					message = message + "随车行李物品险核定赔偿金额超限，按限额调整！\n";
				}
				fm.compensateMessage1.value = message;
			} else if ('F' == fm.prpLlossDtoKindCodeShow[index].value || 'NX' == fm.prpLlossDtoKindCodeShow[index].value || 'NY' == fm.prpLlossDtoKindCodeShow[index].value) {
				var amountA;
				var valueNX;
				var valueNY;
				for (var c = 0; c < fm.prpLlossDtoKindCodeShow.length; c++) {
					if (fm.prpLlossDtoKindCodeShow[c].value == 'A') {
						amountA = parseFloat(fm.kindAmount[c].value);
					}
				}
				if (fm.prpLlossDtoKindCodeShow[index].value == 'NX') {
					valueNX = parseFloat(fm.value[index].value);
				}
				if (fm.prpLlossDtoKindCodeShow[index].value == 'NY') {
					valueNY = parseFloat(fm.value[index].value);
				}
				if ('F' == fm.prpLlossDtoKindCodeShow[index].value) //玻璃单独破碎险超限提示
				{
					if (parseFloat(fm.prpLsumDefPayAllShow[index].value) > parseFloat(amountA)) {
						var message = fm.compensateMessage1.value;
						var re = new RegExp("玻璃单独破碎险别核定赔偿金额超限，将重新计算！", "i");
						var r = message.match(re);
						if (r == null) {
							message = message + "玻璃单独破碎险别核定赔偿金额超限，将重新计算！\n";
						}
						fm.compensateMessage1.value = message;
						doF(amountA);
					}
				}
				if ('NX' == fm.prpLlossDtoKindCodeShow[index].value) //新车特约条款A超限提示
				{
					if (parseFloat(fm.prpLsumDefPayAllShow[index].value) < parseFloat(amountA * valueNX / 100)) {
						var message = fm.compensateMessage1.value;
						var re = new RegExp("新车特约条款A险别未达到协定金额，应按车损险重新计算！", "i");
						var r = message.match(re);
						if (r == null) {
							message = message + "新车特约条款A险别未达到协定金额，应按车损险重新计算！\n";
						}
						fm.compensateMessage1.value = message;
						for (var c = 1; c < fm.prpLlossDtoKindCode.length; c++) {
							if ('NX' == fm.prpLlossDtoKindCode[c].value) {
								fm.prpLlossDtoSumRealPay[c].value = 0;
								fm.prpLlossDtoKindCode[c].value = "";
								fm.prpLlossDtoKindName[c].value = "";
							}
							break;
						}
					}
				}
				if ('NY' == fm.prpLlossDtoKindCodeShow[index].value) //新车特约条款B超限提示
				{
					var dutyValue = parseInt(fm.prpLcompensateIndemnityDutyRate.value);
					if (parseFloat(fm.prpLsumDefPayAllShow[index].value) < parseFloat(amountA * valueNY * dutyValue / 100 / 100)) {
						var message = fm.compensateMessage1.value;
						var re = new RegExp("新车特约条款B险别未达到协定金额，应按车损险重新计算！", "i");
						var r = message.match(re);
						if (r == null) {
							message = message + "新车特约条款B险别未达到协定金额，应按车损险重新计算！\n";
						}
						fm.compensateMessage1.value = message;
						for (var c = 1; c < fm.prpLlossDtoKindCode.length; c++) {
							if ('NY' == fm.prpLlossDtoKindCode[c].value) {
								fm.prpLlossDtoSumRealPay[c].value = 0;
								fm.prpLlossDtoKindCode[c].value = "";
								fm.prpLlossDtoKindName[c].value = "";
							}
							break;
						}
					}
				}
			} else if ('Z' == fm.prpLlossDtoKindCodeShow[index].value) //自燃损失险超限提示！
			{
				var message = fm.compensateMessage1.value;
				var re = new RegExp("自燃损失险核定赔偿金额超限，按限额调整！", "i");
				var r = message.match(re);
				if (r == null) {
					message = message + "自燃损失险核定赔偿金额超限，按限额调整！\n";
				}
				fm.compensateMessage1.value = message;
			} else if ('X1' == fm.prpLlossDtoKindCodeShow[index].value) //发动机特约条款超限提示
			{
				var message = fm.compensateMessage1.value;
				var re = new RegExp("发动机特约条款核定赔偿金额超限，已作相应调整！", "i");
				var r = message.match(re);
				if (r == null) {
					message = message + "发动机特约条款核定赔偿金额超限，已作相应调整！\n";
				}
				fm.compensateMessage1.value = message;
			} else if ('C6' == fm.prpLlossDtoKindCodeShow[index].value) //法律费用超限提示
			{
				var message = fm.compensateMessage1.value;
				var re = new RegExp("法律费用核定赔偿金额超限，按限额调整！", "i");
				var r = message.match(re);
				if (r == null) {
					message = message + "法律费用核定赔偿金额超限，按限额调整！\n";
				}
				fm.compensateMessage1.value = message;
			} else if ('K1' == fm.prpLlossDtoKindCodeShow[index].value) //起重、装卸、挖掘扩展条款超限提示
			{
				var message = fm.compensateMessage1.value;
				var re = new RegExp("起重、装卸、挖掘扩展条款核定赔偿金额超限，按限额调整！", "i");
				var r = message.match(re);
				if (r == null) {
					message = message + "起重、装卸、挖掘扩展条款核定赔偿金额超限，按限额调整！\n";
				}
				fm.compensateMessage1.value = message;
				doK2(round(fm.kindAmount[index].value, 0), (parseInt(fm.prpLsumDefPayAllShow[index].value) - parseInt(fm.prpLsumDefPayAllShowMiddle[index].value)), fm.prpLlossDtoKindCodeShow[index].value);
			} else if ('K2' == fm.prpLlossDtoKindCodeShow[index].value) //特种车固定设备、仪器损坏扩展条款
			{
				var message = fm.compensateMessage1.value;
				var re = new RegExp("特种车固定设备、仪器损坏扩展条款核定赔偿金额超限，按限额调整！", "i");
				var r = message.match(re);
				if (r == null) {
					message = message + "特种车固定设备、仪器损坏扩展条款核定赔偿金额超限，按限额调整！\n";
				}
				fm.compensateMessage1.value = message;
				doK2(round(fm.kindAmount[index].value, 0), (parseInt(fm.prpLsumDefPayAllShow[index].value) - parseInt(fm.prpLsumDefPayAllShowMiddle[index].value)), fm.prpLlossDtoKindCodeShow[index].value);
			} else if ('V1' == fm.prpLlossDtoKindCodeShow[index].value) //附加油污责任险
			{
				var message = fm.compensateMessage1.value;
				var re = new RegExp("附加油污责任险核定赔偿金额超限，按限额调整！", "i");
				var r = message.match(re);
				if (r == null) {
					message = message + "附加油污责任险核定赔偿金额超限，按限额调整！\n";
				}
				fm.compensateMessage1.value = message;
			} else if ('T' == fm.prpLlossDtoKindCodeShow[index].value) //车辆停驶险
			{
				var message = fm.compensateMessage1.value;
				var re = new RegExp("车辆停驶险损失金额超限，按限额调整！", "i");
				var r = message.match(re);
				if (r == null) {
					message = message + "车辆停驶险损失金额超限，按限额调整！\n";
				}
				fm.compensateMessage1.value = message;
			} else if ('D2' == fm.prpLlossDtoKindCodeShow[index].value) //车上货物责任险
			{
				var message = fm.compensateMessage1.value;
				var re = new RegExp("车上货物责任险核定赔偿金额超限，将按保额调整！", "i");
				var r = message.match(re);
				if (r == null) {
					message = message + "车上货物责任险核定赔偿金额超限，将按保额调整！\n";
				}
				fm.compensateMessage1.value = message;
				doK2((parseInt((round(fm.kindAmount[index].value, 0), 0))), (parseInt(fm.prpLsumDefPayAllShow[index].value) - parseInt(fm.prpLsumDefPayAllShowMiddle[index].value)), fm.prpLlossDtoKindCodeShow[index].value);
			} else if ('E' == fm.prpLlossDtoKindCodeShow[index].value) //火灾、爆炸、自燃损失险
			{
				var message = fm.compensateMessage1.value;
				var re = new RegExp("火灾、爆炸、自燃损失险核定赔偿金额超限，按限额调整！", "i");
				var r = message.match(re);
				if (r == null) {
					message = message + "火灾、爆炸、自燃损失险核定赔偿金额超限，按限额调整！\n";
				}
				fm.compensateMessage1.value = message;
			}
		} //判断是否超限 
		if (parseFloat(fm.prpLchargeSumRealPayAllShow[index].value) > parseFloat(fm.prpLchargeAmountShow[index].value)) {
			alert(fm.prpLlossDtoKindNameShow[index].value + "的施救費超限，請調整！");
			doCharge(fm.prpLlossDtoKindCodeShow[index].value);
		}
		if (fm.prpLpersonLossKindCode) {
			for (var i = 0; i < fm.prpLpersonLossKindCode.length; i++) {

				if (fm.prpLpersonLossKindCode[i].value != "") {
					var kindCode = fm.prpLpersonLossKindCode[i].value;
					if (kindCode == fm.prpLlossDtoKindCodeShow[index].value) {
						if (kindCode == 'D12') {
							//maoxuemin  begin
							var sumPersonLoss = 0;
							var sumDefPayAllShowMiddles = 0;
							var SumDefpay = 0;
							for (var i1 = 1; i1 < fm.personLossSerialNo.length; i1++) {
								if (fm.personLossSerialNo[i1].value == fm.prpLpersonLossSerialNo[i].value) {
									PersonLossSumLoss = parseFloat(fm.all("prpLpersonLossSumDefPay")[i1].value);

									if (isNaN(PersonLossSumLoss))
										PersonLossSumLoss = 0;
									sumPersonLoss = sumPersonLoss + PersonLossSumLoss;
									sumDefPayAllShowMiddles += parseInt(fm.prpLpersonLossCompelPay[i1].value);
								}
							}
							if (parseInt((parseInt(sumPersonLoss) - parseInt(sumDefPayAllShowMiddles)) * parseInt(fm.prpLpersonLossIndemnityDutyRate[i].value) / 100) > parseInt(fm.unitAmount[index].value)) {
								var message = fm.compensateMessage1.value;
								var re = new RegExp("车上人员责任险别（乘客）核定赔偿金额超过单人限额，按限额调整！", "i");
								var r = message.match(re);
								if (r == null) {
									message = message + "车上人员责任险别（乘客）核定赔偿金额超过单人限额，按限额调整！\n";
								}
								fm.compensateMessage1.value = message;
								var unitAmount = parseFloat(fm.unitAmount[index].value);
								var realpay = parseFloat(fm.prpLpersonLossSumRealPay1[i].value);
								var amount1 = unitAmount * (1 - parseFloat(fm.prpLpersonLossDutyDeductibleRate[i].value) / 100 - parseFloat(fm.prpLpersonLossDeductibleRate[i].value) / 100);
								var exceptDeductibleRate = parseInt(fm.prpLpersonLossExceptDeductibleRate1[i].value);
								var exceptDeductiblePay1 = unitAmount * parseInt(fm.prpLpersonLossIndemnityDutyRate[i].value) / 100 * exceptDeductibleRate / 100;
								for (var i1 = 1; i1 < fm.personLossSerialNo.length; i1++) {
									if (fm.personLossSerialNo[i1].value == fm.prpLpersonLossSerialNo[i].value) {
										var sumRealPay = parseFloat(fm.all("prpLpersonLossSumDefPay")[i1].value) * parseFloat(fm.prpLpersonLossIndemnityDutyRate[i].value) / 100 * (1 - parseFloat(fm.prpLpersonLossDutyDeductibleRate[i].value) / 100 - parseFloat(fm.prpLpersonLossDeductibleRate[i].value) / 100);
										var compelPay = parseFloat(fm.prpLpersonLossCompelPay[i1].value) * parseFloat(fm.prpLpersonLossIndemnityDutyRate[i].value) / 100 * (1 - parseFloat(fm.prpLpersonLossDutyDeductibleRate[i].value) / 100 - parseFloat(fm.prpLpersonLossDeductibleRate[i].value) / 100);
										var newRealPay = parseFloat(sumPersonLoss) * parseFloat(fm.prpLpersonLossIndemnityDutyRate[i].value) / 100 * (1 - parseFloat(fm.prpLpersonLossDutyDeductibleRate[i].value) / 100 - parseFloat(fm.prpLpersonLossDeductibleRate[i].value) / 100);
										fm.prpLpersonLossSumRealPay[i1].value = (round((sumRealPay / newRealPay) * amount1 - compelPay, 0), 0);
										SumDefpay = SumDefpay + parseFloat(fm.prpLpersonLossSumRealPay[i1].value);
										if (parseInt(fm.prpLpersonLossExceptDeductibleRate[i1].value) != 0) {
											compelPay = parseFloat(fm.prpLpersonLossCompelPay[i1].value) * parseFloat(fm.prpLpersonLossIndemnityDutyRate[i].value) / 100 * exceptDeductibleRate / 100;
											fm.prpLpersonLossExceptDeductiblePay[i1].value = (round(exceptDeductiblePay1 * (sumRealPay / realpay) - compelPay, 0), 0);
											initExceptDeductible();
										}
										calSumRealpay(fm.personLossSerialNo[i1]);
									}
								} //end for 小人伤                
							} //end if 超过单座限额
						} //end if kindCode=='D12'
						else if (kindCode == 'R') { //modify by liping 080605 事故责任下和限额进行比较
							if ((parseInt(fm.prpLsumDefPayAllShow[index].value) - parseInt(fm.prpLsumDefPayAllShowMiddle[index].value)) > parseInt(fm.kindAmount[index].value)) {
								var message = fm.compensateMessage1.value;
								var re = new RegExp("交通事故精神损害责任险核定赔偿金额超过单人限额，按限额调整！", "i");
								var r = message.match(re);
								if (r == null) {
									message = message + "交通事故精神损害责任险核定赔偿金额超过单人限额，按限额调整！\n";
								}
								fm.compensateMessage1.value = message;
								var unitAmount = parseInt(fm.kindAmount[index].value);
								var realpay = parseFloat(fm.prpLpersonLossSumRealPay1[i].value);
								var amount1 = unitAmount * (1 - parseFloat(fm.prpLpersonLossDutyDeductibleRate[i].value) / 100 - parseFloat(fm.prpLpersonLossDeductibleRate[i].value) / 100);
								var exceptDeductibleRate = parseInt(fm.prpLpersonLossExceptDeductibleRate1[i].value);
								var exceptDeductiblePay1 = unitAmount * exceptDeductibleRate / 100;
								for (var i1 = 1; i1 < fm.personLossSerialNo.length; i1++) {
									if (fm.personLossSerialNo[i1].value == fm.prpLpersonLossSerialNo[i].value) {
										var compelPay = parseFloat(fm.prpLpersonLossCompelPay[i1].value) * (1 - parseFloat(fm.prpLpersonLossDutyDeductibleRate[i].value) / 100 - parseFloat(fm.prpLpersonLossDeductibleRate[i].value) / 100);
										var sumRealPay = parseFloat(fm.prpLpersonLossSumRealPay[i1].value);
										fm.prpLpersonLossSumRealPay[i1].value = (round((sumRealPay / realpay) * amount1 - compelPay, 0), 0);
										if (parseInt(fm.prpLpersonLossExceptDeductibleRate[i1].value) != 0) {
											compelPay = parseFloat(fm.prpLpersonLossCompelPay[i1].value) * exceptDeductibleRate / 100;
											fm.prpLpersonLossExceptDeductiblePay[i1].value = (round(exceptDeductiblePay1 * (sumRealPay / realpay) - compelPay, 0), 0);
											initExceptDeductible();
										}
										calSumRealpay(fm.personLossSerialNo[i1]);
									}
								} //end for 小人伤                
							} //end if 超过单座限额
						} //end if kindCode=='R'
					} //end if 险别相等
				} //end if 险别不为空
			} //end for 大人伤
		} //end if 判断是否有人伤    
	} //end for 保单险别
	//计算责任赔款合计、赔款合计、其它费用、实赔金额
	calFund();
}
//用来进行当D11超限时的处理

function doD11(amount, realpay) {
	if (fm.prpLpersonLossKindCode) {
		for (var index = 1; index < fm.prpLpersonLossKindCode.length; index++) {
			if (fm.prpLpersonLossKindCode[index].value == 'D11' && fm.prpLpersonLossSumDefPay1[index].value != "") {
				if (parseInt(fm.prpLpersonLossSumDefPay1[index].value) != 0) {
					var dutyRate = parseInt(fm.prpLpersonLossIndemnityDutyRate[index].value);
					var dutyDeductibleRate = parseInt(fm.prpLpersonLossDutyDeductibleRate[index].value);
					var deductibleRate = parseInt(fm.prpLpersonLossDeductibleRate[index].value);
					//modify by liping 080605 限额不乘事故责任比例
					var amount1 = parseFloat(amount * (1 - dutyDeductibleRate / 100 - deductibleRate / 100));
					for (var i = 1; i < fm.personLossSerialNo.length; i++) {
						if (fm.personLossSerialNo[i].value == fm.prpLpersonLossSerialNo[index].value) {
							var sumDefPay;
							var compelPay;
							if (parseInt(fm.prpLpersonLossSumDefPay[i].value) != 0) {
								sumDefPay = parseFloat(fm.prpLpersonLossSumDefPay[i].value);
								compelPay = parseFloat(fm.prpLpersonLossCompelPay[i].value);
								fm.prpLpersonLossSumRealPay[i].value = (round(((sumDefPay - compelPay) / realpay) * amount1, 0), 0);
							}
							if (parseInt(fm.prpLpersonLossExceptDeductibleRate[i].value) != 0) {
								var exceptDeductibleRate = parseFloat(fm.prpLpersonLossExceptDeductibleRate[i].value);
								var exceptDeductiblePay = parseFloat(exceptDeductibleRate / 100 * amount * dutyRate / 100);
								fm.prpLpersonLossExceptDeductiblePay[i].value = (round((sumDefPay - compelPay) / realpay * exceptDeductiblePay, 0), 0);
								initExceptDeductible();
							}
						}
					}
					calSumRealpay(fm.prpLpersonLossKindCode[index]);
				}
			}
		}
		//计算责任赔款合计、赔款合计、其它费用、实赔金额
		calFund();
	}
}
//用来处理玻璃单独破碎险超限的情况

function doF(amount) {
	for (var index = 1; index < fm.prpLlossDtoKindCode.length; index++) {
		if ('F' == fm.prpLlossDtoKindCode[index].value) {
			fm.prpLlossDtoSumRealPay[index].value = (round(amount, 0), 0);
		}
	}
	//计算责任赔款合计、赔款合计、其它费用、实赔金额
	calFund();
}

//用来控制输入的核定赔偿金额不能大於损失金额

function inputControl2(field) {
	var fieldname = field.name;
	var findex = 0;
	for (var i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == field) {
			findex = i;
			break;
		}
	}
	if (field.name == 'prpLlossDtoSumDefPay') {}
	if (field.name == 'prpLpersonLossSumDefPay') {}
}

//改变出险时的实际价值

function relateChange2(field) {
	if (fm.prpLlossDtoKindCode) {
		for (var index = 1; index < fm.prpLlossDtoKindCode.length; index++) {
			if ((fm.prpLlossDtoKindCode[index].value == 'NX' || fm.prpLlossDtoKindCode[index].value == 'NY') && fm.prpLlossDtoSumRealPay[index].value != 0) {
				fm.prpLlossDtoSumRealPay[index].value = field.value;
				break;
			}
		}
	}
}
//对Y险别超限进行重新分配

function doK2(amount, sumdefPay1, kindCode) {
	for (var index = 1; index < fm.prpLlossDtoKindCode.length; index++) {
		if (fm.prpLlossDtoKindCode[index].value == kindCode && fm.prpLlossDtoSumDefPay[index].value != "") {
			var dutyRate = parseInt(fm.prpLlossDtoIndemnityDutyRate[index].value);
			if (kindCode == 'X' || kindCode == 'D2')
				dutyRate = 100;
			var claimRate = parseFloat(fm.prpLlossDtoClaimRate[index].value);
			var dutyDeductibleRate = parseInt(fm.prpLlossDtoDutyDeductibleRate[index].value);
			var deductibleRate = parseInt(fm.prpLlossDtoDeductibleRate[index].value);
			var amount1 = parseFloat(amount * claimRate / 100 * (1 - dutyDeductibleRate / 100 - deductibleRate / 100));
			if (parseInt(fm.prpLlossDtoSumDefPay[index].value) != 0) {
				var sumDefPay = parseFloat(fm.prpLlossDtoSumDefPay[index].value);
				var sumRest = parseFloat(fm.prpLlossDtoSumRest[index].value);
				var compelPay = parseFloat(fm.prpLlossDtoCompelPay[index].value);
				fm.prpLlossDtoSumRealPay[index].value = (round(((sumDefPay - sumRest - compelPay) / sumdefPay1) * amount1, 0), 0);
			}
			if (parseInt(fm.prpLlossDtoExceptDeductibleRate[index].value) != 0) {
				var exceptDeductibleRate = parseFloat(fm.prpLlossDtoExceptDeductibleRate[index].value);
				var exceptDeductPay = parseFloat(amount * dutyRate / 100 * exceptDeductibleRate / 100);
				var exceptDeductiblePay1 = ((sumDefPay - sumRest - compelPay) / sumdefPay1) * exceptDeductPay;
				fm.prpLlossDtoExceptDeductiblePay[index].value = (round(exceptDeductiblePay1, 0), 0);
				initExceptDeductible();
			}
		}
	}
	calFund();
}
//对施救费用进行单独汇总

function initChargeSumRealPay() {
	if (fm.prpLlossDtoKindCodeShow) {
		for (var index = 0; index < fm.prpLlossDtoKindCodeShow.length; index++) {
			fm.prpLchargeSumRealPayAllShow[index].value = 0;
			fm.prpLchargeAmountShow[index].value = 0;
		}
	}
	if (fm.prpLchargeSerialNo) {
		for (var index3 = 1; index3 < fm.prpLchargeSerialNo.length; index3++) {
			for (var index = 0; index < fm.prpLlossDtoKindCodeShow.length; index++) {
				var kindCode1 = fm.prpLchargeKindCode[index3].value;
				var kindCode2 = fm.prpLlossDtoKindCodeShow[index].value;
				if (fm.prpLlossDtoKindCodeShow[index].value != "")
					if (kindCode1 == kindCode2 && (kindCode1 == 'A' || kindCode1 == 'Z' || kindCode1 == 'B' || kindCode1 == 'X1' || kindCode1 == 'K1' || kindCode1 == 'K2' || kindCode1 == 'Y' || kindCode1 == 'S' || kindCode1 == 'E'))
						if ('03' == fm.prpLchargeChargeCode[index3].value) {
							chargeSumRealPay = parseFloat(fm.prpLchargeSumRealPayAllShow[index].value);
							chargeSumRealPay += parseFloat(fm.prpLchargeSumRealPay[index3].value);
							fm.prpLchargeSumRealPayAllShow[index].value = chargeSumRealPay;
							fm.prpLchargeAmountShow[index].value = parseFloat(fm.kindAmount[index].value);
						}
			}
		}
	}
}
//用来处理施救费超限的情况

function doCharge(kindCode) {
	for (var index3 = 1; index3 < fm.prpLchargeSerialNo.length; index3++) {
		if (fm.prpLchargeKindCode[index3].value == kindCode) {
			fm.prpLchargeChargeAmount[index3].value = 0;
			fm.prpLchargeSumRealPay[index3].value = 0;
		}
	}
}
//用来计算C7险的金额

function calC7(index1, kindCode) {
	var count = getElementCount("deductName");
	var flag = 0;
	for (var j = 0; j < count; j++) {
		if (fm.deductConditionTemp[j].value == '180' && fm.Times[j].value == 1) {
			flag = 1;
		}
	}
	if (flag == 1) {
		for (var index = 1; index < fm.prpLlossDtoKindCode.length; index++) {
			if (fm.prpLlossDtoKindCode[index].value == kindCode && fm.licenseNo[index].value == fm.licenseNo[index1].value) {
				var result = (parseFloat(fm.prpLlossDtoSumRealPay[index].value) + parseFloat(fm.prpLDeductible.value)) / (1 - (parseInt(fm.prpLlossDtoDeductibleRate[index].value) + parseInt(fm.prpLlossDtoDutyDeductibleRate[index].value)) / 100) * 0.1;
				fm.prpLlossDtoSumLoss[index1].value = (round(result, 0), 0);
				fm.prpLlossDtoSumDefPay[index1].value = (round(result, 0), 0);
				fm.prpLlossDtoSumRealPay[index1].value = (round(result, 0), 0);
			}
		}
	}
}
//控制按钮的可见和不可见状态

function changeAdvanceStatus(field) {
	if (field.value == '1') //全责垫付
	{
		fm.displayInputInfo.style.display = "";
		if (fm.advanceCaseStatus.value == '' || fm.advanceCaseStatus.value == '00') {
			fm.displayUpload.style.display = "";
			fm.displayGetConfirm.style.display = "none";
		} else if (fm.advanceCaseStatus.value == '10') {
			fm.displayUpload.style.display = "none";
			fm.displayGetConfirm.style.display = "";
		} else {
			fm.displayUpload.style.display = "none";
			fm.displayGetConfirm.style.display = "";
			fm.displayGetConfirm.disabled = true;
		}
	} else //其他
	{
		fm.displayInputInfo.style.display = "none";
		fm.displayUpload.style.display = "none";
		fm.displayGetConfirm.style.display = "none";
	}
}
//用来打开一个新的页面然後重新輸入无责方的信息

function inputNullInfo() {
	var registNo = fm.prpLregistExtRegistNo.value;
	var newWindow = window.open("/claim/advance.do?registNo=" + registNo, "NewWindow", "width=640,height=300,top=0,left=0,toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
}

//清空理算报告

function clearPrpLctext() {
	var Context = document.getElementsByName('prpLctextContextInnerHTML');
	if (Context.length > 0) {
		Context[0].value = '';
	}
}

//add by luochang begin at 2010-06-13 获取支付对象

function getPayObject(field) {
	var fieldName = field.name;
	var fieldNameList = document.getElementsByName(fieldName);
	var chargeCodeList = document.getElementsByName("prpLchargeChargeCode");
	var prpLchargePayObjectType = document.getElementsByName("prpLchargePayObjectType");
	var chargeCode;
	var index;
	var payObjectType;
	for (var i = 0; i < fieldNameList.length; i++) {
		if (fieldNameList[i] == field) {
			index = i;
			break;
		}
	}
	if (chargeCodeList[index] == null || chargeCodeList[index].value == "") {
		alert("請選擇費用名稱");
		return;
	} else {
		chargeCode = chargeCodeList[index].value;
		payObjectType = prpLchargePayObjectType[index].value;
		if (((chargeCode == "04" || chargeCode == "05" || chargeCode == "07" || chargeCode == "13" || chargeCode == "15") && payObjectType == "A") ||
			chargeCode == "08" || chargeCode == "99") { //手工輸入支付对象
			var serialNo = getElementOrder(field) - 1;
			var url = "/claim/pages/common/account/PaymentAccountName.jsp?serialNo=" + serialNo;
			var handle = window.showModalDialog(url, window, "dialogHide:yes;help:no;status:no;scroll:yes;dialogWidth:300px;dialogHeight:460px");
			if (handle == null || handle == "") {
				fm.prpLchargePayObjectName[serialNo].value = ""
			} else {
				fm.prpLchargePayObjectName[serialNo].value = handle;
				getAccountByPayObjectName(field, handle);
			}
		} else { //带出外部机构
			code_CodeSelect(field, 'getPayObject', '-1,0', 'Y', 'Y', chargeCode + "|" + payObjectType);
			getExternAlagency(field, index);
		}
	}
}

function getExternAlagency(field, index) {
	var inputObject = field;
	var outputObject;
	var ChargeCode = fm.prpLchargeChargeCode[index].value;
	var PayObjectType = fm.prpLchargePayObjectType[index].value;
	var PayObjectCode = fm.prpLchargePayObjectCode[index].value;
	if ((ChargeCode == "04" || ChargeCode == "05" || ChargeCode == "06" || ChargeCode == "07" || ChargeCode == "13" || ChargeCode == "15") && PayObjectType == "B" && PayObjectCode != "") {
		var inputArgs = {
			comCode: PayObjectCode
		};
		var param = DWRUtil.getValues(inputArgs);
		DWREngine.setAsync(false);
		dwrInvokeData("getExternAlagency", param, "rollbackExternAlagency", inputObject, outputObject);
		DWREngine.setAsync(true);
	} else {
		fm.prpLchargeOwnerShip[index].options[0].selected = true;
		fm.prpLchargeAccountCode[index].value = "";
		fm.prpLchargeBankName[index].value = "";
		fm.prpLchargeBankCode[index].value = "";
		fm.prpLchargeCustomBankName[index].value = "";
		fm.prpLchargeCertifiCateCode[index].value = "";
		fm.prpLchargeOwnerName[index].value = "";
		fm.prpLchargePhoneNo[index].value = "";
		fm.prpLchargeAccountCurrency[index].value = "";
		fm.prpLchargeAccountType[index].value = "";
		fm.prpLchargeAccountTypeShow[index].value = "";
	}
}

function rollbackExternAlagency(inputObject, outputObject, returnObject) {
	var fieldname = inputObject.name;
	var findex = 0;
	if (fm.all(fieldname).length != undefined) {
		for (i = 1; i < fm.all(fieldname).length; i++) {
			if (fm.all(fieldname)[i] == inputObject) {
				findex = i;
				break;
			}
		}
	}
	var prplexternalagencyDto = returnObject;


	fm.prpLchargeOwnerShip[findex].options[0].selected = true;
	document.all["bank"][findex].style.display = "block";
	document.all["accountCQ"][i].style.display = "none";
	fm.prpLchargeOwnerNameCQ[i].value = "";
	fm.prpLchargeCertifiCateCodeCQ[i].value = "";
	fm.prpLchargeAccountCode[findex].value = prplexternalagencyDto.accountcode;
	fm.prpLchargeBankName[findex].value = prplexternalagencyDto.bankname;
	fm.prpLchargeBankCode[findex].value = prplexternalagencyDto.bankcode;
	fm.prpLchargeCustomBankName[findex].value = prplexternalagencyDto.custombankname;
	fm.prpLchargeCertifiCateCode[findex].value = prplexternalagencyDto.certificatecode;
	fm.prpLchargeOwnerName[findex].value = prplexternalagencyDto.ownername;
	fm.prpLchargePhoneNo[findex].value = prplexternalagencyDto.ownerphoneno;
	fm.prpLchargeAccountCurrency[findex].value = prplexternalagencyDto.accountcurrency;
	fm.prpLchargeAccountType[findex].value = prplexternalagencyDto.accounttype;
	if (prplexternalagencyDto.accounttype == "1") {
		fm.prpLchargeAccountTypeShow[findex].value = "存折";
	} else if (prplexternalagencyDto.accounttype == "2") {
		fm.prpLchargeAccountTypeShow[findex].value = "信用卡";
	} else if (prplexternalagencyDto.accounttype == "3") {
		fm.prpLchargeAccountTypeShow[findex].value = "储值卡";
	} else if (prplexternalagencyDto.accounttype == "4") {
		fm.prpLchargeAccountTypeShow[findex].value = "其他";
	} else {
		fm.prpLchargeAccountTypeShow[findex].value = "";
	}
	fm.buttonAddAcc[findex].disabled = false;

	undisablebutton();
}

function calRealpayWithFinalValue() {
	for (var index = 1; index < fm.all("prpLlossDtoIndemnityDutyRate").length; index++) {
		var kindcode = fm.all("prpLlossDtoKindCode")[index].value;
		if (kindcode == "A" || kindcode == "K1" || kindcode == "K2" || kindcode == "S" || kindcode == "Y" || kindcode == "G") {
			calRealpay(fm.all("prpLlossDtoIndemnityDutyRate")[index]);
		}
	}
}

function clearPayObject(field) {
	var i = getElementOrder(field) - 1;
	var prpLchargePayObjectNameList = document.getElementsByName("prpLchargePayObjectName");
	var prpLchargePayObjectCodeList = document.getElementsByName("prpLchargePayObjectCode");
	prpLchargePayObjectNameList[i].value = "";
	prpLchargePayObjectCodeList[i].value = "";
}

function getAccountByPayObjectName(field, payObjectName) {
	var order = getElementOrder(field) - 1;
	var submitStr = "AccountCode.do?actionType=SearchWithPayObjectName&ownerName=" + payObjectName + "&serialNo=" + order;
	window.open(submitStr, '', 'resizable=1,scrollbars=yes,overflow=scroll,width=600,height=600');
}

function clearPayment(field) {
	var i = getElementOrder(field) - 1;
	fm.prpLchargeChargeReport[i].value = 0;
	fm.prpLchargeSumRealPay[i].value = 0;
	fm.prpLchargeChargeAmount[i].value = 0;
	fm.prpLchargeOwnerName[i].value = "";
	fm.prpLchargeUniformNo[i].value = "";
	fm.prpLchargeCutBack[i].value = "";
	fm.prpLchargeBankCode[i].value = "";
	fm.prpLchargeBankName[i].value = "";
	fm.prpLchargeAccountCode[i].value = "";
	fm.prpLchargeCustomBankCode[i].value = "";
	fm.prpLchargeCustomBankName[i].value = "";
	fm.prpLchargeCourierAddress[i].value = "";
	fm.prpLchargeAreaCode[i].value = "";
	calChargeAmount(field);
	setRealPay();
}

function buttonOnClick3(fieldObject) {
	var intIndex = parseInt(getElementOrder(fieldObject) - 1);
	var spanId = 'span_Engage_Context00';
	if (isNaN(fm.button_Engage_Open_Context00.length)) {} else { //多行
		spanId = 'span_Engage_Context00' + "[" + intIndex + "]";
	}
	showSubPage3(spanId);
}


function showSubPage3(spanID, leftMove) {
	var intLeftMove = (leftMove == null ? 0 : leftMove);
	var span = eval(spanID);
	var strTemp = span.id;

	var ex = window.event.clientX + document.body.scrollLeft; //得到事件的坐标x
	var ey = window.event.clientY + document.body.scrollTop; //得到事件的坐标y

	ex = ex - 520;

	if (ex < 0) {
		ex = 0;
	}
	ex = ex - intLeftMove;

	span.style.left = ex;
	span.style.top = ey;
	span.style.display = '';
}
