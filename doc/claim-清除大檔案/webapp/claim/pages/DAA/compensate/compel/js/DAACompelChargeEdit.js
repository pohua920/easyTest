/**
 * DAACompelChargeEdit.js
 * 强制险费用讯息相关JS （后续考虑与、任意险费用、追偿费用统一起来）
 */
/***
 * 费用支付方式发生改变时
 */

function chargeOwnerShipChange(field) {
	var $chargeObject = $(field).parents("table[name='chargeObject']")
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
	var $chargeObject = $curr.parents("table[name='chargeObject']"); //当前操作的费用资讯table
	$chargeObject.find("input[name='prpLchargeChargeReport']").val(0); //清空費用金額
	$chargeObject.find("input[name='prpLchargeChargeAmount']").val(0); //清空實際費用
	calSumDutyPaid();
}
/**删除一个费用资讯信息*/

function deleteChargeObject(field) {
	var $curr = $(field); //当前对象
	var $chargeObject = $curr.parents("table[name='chargeObject']"); //当前操作的费用资讯table
	$chargeObject.remove(); //移除
	calSumDutyPaid();
}
/**增加一个费用资讯信息*/

function insertChargeObject() {
	$("#Charge_Data").find("table[name='chargeObject']").clone(true).appendTo("#PrpLcharge");
}

//费用信息项发生改变 

function setRealPayNew(field) {
	if (isChange(field)) {
		var checkFlag = true; //默认通过检验
		var $chargeObject = $(field).parents("table[name='chargeObject']");
		if (field.name == 'prpLchargeChargeReport') {
			$chargeObject.find(":input[name='prpLchargeChargeAmount']").val(field.value);
			$chargeObject.find(":input[name='prpLchargeSumRealPay']").val(field.value);
		} else if (field.name == 'prpLchargeChargeAmount') {
			if (parseFloat(field.value) > parseFloat($chargeObject.find(":input[name='prpLchargeChargeReport']").val())) {
				recoveryData(field); //否则恢复数据
				checkFlag = alertMessage(field, "實際費用不能大於费用金额!");
			} else {
				$chargeObject.find(":input[name='prpLchargeSumRealPay']").val(field.value);
			}
		}
		if (checkFlag) { //有改变且校验通过，则重新计算赔付额
			calSumDutyPaid();
		}
	}
}