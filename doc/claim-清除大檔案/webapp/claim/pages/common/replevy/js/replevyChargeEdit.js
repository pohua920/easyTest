/**
 * 同DAACompelChargeEdit.js內容一致，調整時請同步
 * 强制险费用讯息相关JS （后续考虑与、任意险费用、追偿费用统一起来）
 */
/***
 * 费用支付方式发生改变时
 */

function chargeOwnerShipChange(field) {
	var $chargeObject = findPageCodeObject($(field).closest("table").get(0),"Charge");
	if (field.value == "B") { //汇款
		$chargeObject.find("span[name='spanCutBack']").hide(); //隐藏禁背
		$chargeObject.find("tr[name='bankInfo']").show(); //显示支付账户信息
	} else if (field.value == "Q") { //支票
		$chargeObject.find("span[name='spanCutBack']").show();
		$chargeObject.find("tr[name='bankInfo']").hide();
	}
}
/**清空当前赔付费用帳户*/
function clearPaymentNew(field) {
	var $curr = $(field); //当前对象
	var $chargeObject = findPageCodeObject($(field).closest("table").get(0),"Charge");; //当前操作的费用资讯table
	$chargeObject.find("input[name='prpLchargeChargeReport']").val(0); //清空費用金額
	$chargeObject.find("input[name='prpLchargeChargeAmount']").val(0); //清空實際費用
	calSumFeeAll();
}
//费用信息项发生改变 

function setRealPayNew(field) {
	if (isChange(field)) {
		var checkFlag = true; //默认通过检验
		var $chargeObject = findPageCodeObject($(field).closest("table").get(0),"Charge");;
		if (field.name == 'prpLchargeChargeReport') {
			$chargeObject.find(":input[name='prpLchargeChargeAmount']").val(field.value);
			$chargeObject.find(":input[name='prpLchargeSumRealPay']").val(field.value);
		} else if (field.name == 'prpLchargeChargeAmount') {
			if (parseFloat(field.value) > 0 && parseFloat(field.value) > parseFloat($chargeObject.find(":input[name='prpLchargeChargeReport']").val())) {
				recoveryData(field); //否则恢复数据
				checkFlag = alertMessage(field, "實際費用不能大於费用金额!");
			} else {
				$chargeObject.find(":input[name='prpLchargeSumRealPay']").val(field.value);
			}
		}
		if (checkFlag) { //有改变且校验通过，则重新计算赔付额
			calSumFeeAll();
		}
	}
}

//修改费用代码或支付类型或支付对象时，清空相应的付款信息
function newClearPayment(field) {
	var i = getElementOrder(field) - 1;
	fm.prpLchargeSumRealPay[i].value = 0;
	fm.prpLchargeChargeAmount[i].value = 0;
	fm.prpLchargeOwnerName[i].value = "";
	fm.prpLchargeUniformNo[i].value = "";
	fm.prpLchargeBankCode[i].value = "";
	fm.prpLchargeBankName[i].value = "";
	fm.prpLchargeAccountCode[i].value = "";
	fm.prpLchargeCustomBankCode[i].value = "";
	fm.prpLchargeCustomBankName[i].value = "";
	fm.prpLchargeAreaCode[i].value = "";
	fm.prpLchargeCourierAddress[i].value = "";
	calSumFeeAll();
}

function getReplevyPayObject(field, flag) {
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
		chargeCode = chargeCodeList[index].value;
		payObjectType = prpLchargePayObjectType[index].value;
		if ( payObjectType == "A") { //手工輸入支付对象
			var serialNo = getElementOrder(field) - 1;
			var url = "/claim/pages/common/account/PaymentAccountName.jsp?serialNo=" + serialNo;
			var handle = window.showModalDialog(url, window, "dialogHide:yes;help:no;status:no;scroll:yes;dialogWidth:300px;dialogHeight:460px");
			if (handle == null || handle == "") {
				fm.prpLchargePayObjectName[serialNo].value = ""
			} else {
				getAccountByPayObjectName(field, handle);
			}
		} else {//带出外部机构
			if (flag == 0) {
				code_CodeSelect(field, 'getReplevyPayObject', '0,1', 'Y', 'Y', chargeCode + "|" + payObjectType);
			} else {
				code_CodeSelect(field, 'getReplevyPayObject', '-1,0', 'Y', 'Y', chargeCode + "|" + payObjectType);
			}
			getExternAlagency(field, index);
		}
}

function getAccountByPayObjectName(field, payObjectName) {
	var order = getElementOrder(field) - 1;
	var submitStr = "AccountCode.do?actionType=SearchWithPayObjectName&ownerName=" + payObjectName + "&serialNo=" + order;
	window.open(submitStr, '', 'resizable=1,scrollbars=yes,overflow=scroll,width=600,height=600');
}

//当支付对象为外部机构时，自动带出外部机构的银行帳号

function getExternAlagency(field, index) {
	var inputObject = field;
	var outputObject;
	var ChargeCode = fm.prpLchargeChargeCode[index].value;
	var PayObjectType = fm.prpLchargePayObjectType[index].value;
	var PayObjectCode = fm.prpLchargePayObjectCode[index].value;
	if (PayObjectType == "B" && PayObjectCode != "") {
		DWREngine.setAsync(false);
		dwrInvokeData("getExternAlagencyByStr", PayObjectCode, "rollbackExternAlagency", inputObject, outputObject);
		DWREngine.setAsync(true);
	} else {
		fm.prpLchargeOwnerShip[index].options[0].selected = true;
		fm.prpLchargeAccountCode[index].value = "";
		fm.prpLchargeBankName[index].value = "";
		fm.prpLchargeBankCode[index].value = "";
		fm.prpLchargeCustomBankCode[index].value = "";
		fm.prpLchargeCustomBankName[index].value = "";
		fm.prpLchargeOwnerName[index].value = "";
		fm.prpLchargeUniformNo[index].value= "";
		fm.prpLchargeAreaCode[index].value = "";
		fm.prpLchargeCourierAddress[index].value= "";
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
	fm.prpLchargeAccountCode[findex].value = prplexternalagencyDto.accountCode;
	fm.prpLchargeBankName[findex].value = prplexternalagencyDto.bankName;
	fm.prpLchargeBankCode[findex].value = prplexternalagencyDto.bankCode;
	fm.prpLchargeCustomBankCode[findex].value = prplexternalagencyDto.customBankCode;
	fm.prpLchargeCustomBankName[findex].value = prplexternalagencyDto.customBankName;
	fm.prpLchargeOwnerName[findex].value = prplexternalagencyDto.comcname;
	fm.prpLchargeUniformNo[findex].value = prplexternalagencyDto.certifiCateCode;
	fm.buttonAddAcc[findex].disabled = false;
	undisablebutton();
}

//修改费用代码是清空支付对象 

function clearPayObject(field) {
	var i = getElementOrder(field) - 1;
	var prpLchargePayObjectNameList = document.getElementsByName("prpLchargePayObjectName");
	var prpLchargePayObjectCodeList = document.getElementsByName("prpLchargePayObjectCode");
	prpLchargePayObjectNameList[i].value = "";
	prpLchargePayObjectCodeList[i].value = "";
}
