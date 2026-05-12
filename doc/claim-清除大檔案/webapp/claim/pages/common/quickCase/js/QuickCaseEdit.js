/*****************************************************************************
 * DESC       ：简易赔案的脚本函数页面(车险类的)
 * AUTHOR     ：中科軟
 * CREATEDATE ：2007-06-22
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/

function initButton() {
	if (fm.buttonClaimLossDelete != undefined) {
		if (fm.all("buttonClaimLossDelete").length == undefined) {
			fm.buttonClaimLossDelete.disabled = true;
		} else {
			for (var i = 0; i < fm.all("buttonClaimLossDelete").length; i++) {
				fm.all("buttonClaimLossDelete")[i].disabled = true;
			}
		}
	}
	fm.buttonClaimLossDelete1.disabled = true;
	fm.buttonDriverInsert1.disabled = true;
	if (fm.buttonChargeDelete != undefined) {
		if (fm.all("buttonChargeDelete").length == undefined) {
			fm.buttonChargeDelete.disabled = true;
		} else {
			for (var index = 0; fm.all("buttonChargeDelete").length; index++) {
				fm.all("buttonChargeDelete")[index].disabled = true;
			}
		}
	}
	fm.buttonChargeDelete1.disabled = true;
	fm.buttonDriverInsert.disabled = true;
}


function doCertifyDirect(businessNo, nodeType) {
	window.open("/claim/certifyBeforeEdit.do?RegistNo=" + businessNo + "&editType=CertifyDirect&nodeType=" + nodeType, "winName", "resizable=0,scrollbars=1,width=800,height=600");
}



function saveForm() {

	if (!validateForm(fm, 'Replevy_Data,Charge_Data')) {
		return false;
	}

	if (trim(fm.prpLreplevyRepleviedName.value).length < 1) {
		errorMessage("被简易赔案人名称不能为空");
		return false;
	}
	if (trim(fm.prpLreplevySumValidFee.value).length < 1) {
		errorMessage("本次追回金额不能为空");
		return false;
	}
	if (fm.prpLreplevyValidDate.value.length < 1) {
		errorMessage("本次追回日期不能为空");
		return false;
	}
	fm.submit();
}

/* 计算本次追回金额 =  (-总简易赔案金额)-费用的总金额*/

function replevySumValidFee() {
	var prpLreplevySumValidFee = fm.prpLreplevySumValidFee.value;
	var otherCharge = fm.otherCharge.value;
	var prpLreplevySumDutyPaid = 0;
	var sumValidFeeAll = 0;
	if (isNaN(prpLreplevySumValidFee))
		prpLreplevySumValidFee = 0.0;
	if (isNaN(otherCharge))
		otherCharge = 0.0;
	prpLreplevySumDutyPaid = (0 - parseInt(prpLreplevySumValidFee)) - parseInt(otherCharge);
	fm.prpLreplevySumDutyPaid.value = prpLreplevySumDutyPaid;
	fm.sumValidFeeAll.value = parseInt(prpLreplevySumValidFee) - parseInt(otherCharge);
}