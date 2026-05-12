/*****************************************************************************
 * DESC       ：定损登记的脚本函数页面
 * AUTHOR     ：中科軟
 * CREATEDATE ： 2004-10-13
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/
/**
 *@description 检查定损登记
 *@param       无
 *@return      通过返回true,否则返回false
 */

function checkForm() {
	return true;
}

/**
 *@description 提交
 *@param       无
 *@return      通过返回true,否则返回false
 */

function submitForm() {
	if (checkForm() == false) {
		return false;
	}

	fm.buttonSave.disabled = true;
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
 *@description 根据按钮状态保存报案数据
 *@param       this
 *@param       保存状态
 *@return      通过返回true,否则返回false
 */

function saveForm(field, saveType) {
	fm.buttonSaveType.value = saveType;
	if (!validateForm(fm, 'Prop_Data')) {
		return false;
	}

	//reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
	field.disabled = true;
	fm.submit();
}

/**
 @author      理赔组
 @description 校验险别
 @return      boolean: 合法为true,否则为false  
 */

function checkPropKindCode() {
	//校验录入险别
	//只能录入以下险别  
	//第三者责任险--B；
	//车上货物责任险--D2；
	//车载货物掉落责任险--H；
	if (fm.prpLpropKindCode.length > 0) {
		for (i = 1; i < fm.prpLpropKindCode.length; i++) {
			var kindCode = fm.prpLpropKindCode[i].value;
			if (kindCode != RISKINFO.KINDCODE_D_B && kindCode != RISKINFO.KINDCODE_D_D2 && kindCode != "H") {
				errorMessage("财产损失项目中,只能輸入B、D2、H 三个险别的损失！");
				return false;
			}
		}
	}
	return true;
}

/**
 *@description 弹出查看留言页面
 *@param       无
 */

function openWinQuery() {
	var win;
	var messagedo = "/claim/messageQueryList.do?registNo=" + fm.RegistNo.value;
	win = window.showModalDialog(messagedo, "NewWindow", "status=no,resizable=yes,scrollbars=yes,width=500,Height=400");
}

/**
 *@description 实现修理换件，人员，费用的显示变换
 *@param       spanID span编号
 *@param       spanName span的名称
 */

function changeSpan(spanID, spanName) {

	var spanNameRepairComponentObject = eval("SpanRepairComponent");
	var spanNamePersonObject = eval("SpanPerson");
	var spanNamePropObject = eval("SpanProp");

	if (spanName.id == "SpanRepairComponent") {
		spanNameRepairComponentObject.style.display = "";
		spanNamePersonObject.style.display = "none";
		spanNamePropObject.style.display = "none";
	} else if (spanName.id == "SpanPerson") {
		spanNameRepairComponentObject.style.display = "none";
		spanNamePersonObject.style.display = "";
		spanNamePropObject.style.display = "none";
	} else if (spanName.id == "SpanProp") {
		spanNameRepairComponentObject.style.display = "none";
		spanNamePersonObject.style.display = "none";
		spanNamePropObject.style.display = "";
	}

}

/**
 *@description 根据工时，工时单价和材料费用计算定损金额
 *@param       无
 *@return      通过返回true,否则返回false
 */

function getSumDefLoss(field, ext) {
	var fieldname = field.name;
	var i = 0;
	var findex = 0;
	var manHourFee;
	var materialFee;
	var sumDefLoss;
	var ManHour;
	var Quantity;

	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == field) {
			findex = i;
			break;
		}
	}

	if (ext == 1) {
		ManHour = parseFloat(fm.all("prpLrepairFeeManHour")[findex].value);
		manHourFee = parseFloat(fm.all("prpLrepairFeeManHourUnitPrice")[findex].value)
		materialFee = parseFloat(fm.all("prpLrepairFeeMaterialFee")[findex].value);
		if (isNaN(manHourFee) || manHourFee.length < 1) {
			manHourFee = 0;
		}
		if (isNaN(materialFee) || materialFee.length < 1) {
			materialFee = 0;
		}
		if (manHourFee == 0 && materialFee == 0) {}
		if (isNaN(ManHour) || ManHour.length < 1) {
			ManHour = 0;
		}
		sumDefLoss = manHourFee * ManHour + materialFee;
		fm.all("prpLrepairFeeSumDefLoss")[findex].value = point(round(sumDefLoss, 0), 0);
		sumRepairFee();
	} else {

		quantity = parseFloat(fm.all("prpLcomponentQuantity")[findex].value)
		manHourFee = parseFloat(fm.all("prpLcomponentManHourFee")[findex].value);
		materialFee = parseFloat(fm.all("prpLcomponentMaterialFee")[findex].value);
		if (isNaN(quantity) || quantity.length < 1) {
			quantity = 0;
		}
		if (isNaN(manHourFee) || manHourFee.length < 1) {
			manHourFee = 0;
		}
		if (quantity == 0 && manHourFee == 0) {}
		if (isNaN(materialFee) || materialFee.length < 1) {
			materialFee = 0;
		}
		sumDefLoss = parseFloat(point(round(quantity * manHourFee, 0), 0)) + parseFloat(point(round(materialFee, 0), 0));
		fm.all("prpLcomponentSumDefLoss")[findex].value = point(round(sumDefLoss, 0), 0);
		sumComponentFee();
	}

	return true;
}

/**
 *@description 汇总修理费计算
 *@param       无
 *@return      无
 */

function sumRepairFee() {
	var sumRepairDefFee = 0; //总修理定损费
	var sumManHourFee = 0; //总工时费
	var sumMaterialFee = 0; //总材料费
	var repairFeeManHour = 0;
	var repairFeeManHourUnitPrice = 0;
	var repairFeeMaterialFee = 0;
	if (fm.all("SumDefLoss1").length > 0) {
		for (j = 0; j < fm.all("SumDefLoss1").length; j++) {
			for (i = 1; i < fm.all("prpLrepairFeeManHour").length; i++) {
				if (j == fm.all("carLossRepairFeeLossItemCode")[i].value) {
					repairFeeManHour = parseFloat(fm.all("prpLrepairFeeManHour")[i].value);
					repairFeeManHourUnitPrice = parseFloat(fm.all("prpLrepairFeeManHourUnitPrice")[i].value);
					repairFeeMaterialFee = parseFloat(fm.all("prpLrepairFeeMaterialFee")[i].value);
					if (isNaN(repairFeeManHour)) {
						repairFeeManHour = 0;
					}
					if (isNaN(repairFeeManHourUnitPrice)) {
						repairFeeManHourUnitPrice = 0;
					}
					if (isNaN(repairFeeMaterialFee)) {
						repairFeeMaterialFee = 0;
					}
					sumManHourFee = sumManHourFee + parseFloat(point(round(repairFeeManHour * repairFeeManHourUnitPrice, 0), 0));
					sumMaterialFee = sumMaterialFee + parseFloat(point(round(repairFeeMaterialFee, 0), 0));
					sumRepairDefFee = sumManHourFee + sumMaterialFee;
				}
			}
			fm.all("SumDefLoss1")[j].value = sumRepairDefFee;
			fm.all("SumManHourFee1")[j].value = sumManHourFee;
			fm.all("SumMaterialFee1")[j].value = sumMaterialFee;
			sumRepairDefFee = 0;
			sumManHourFee = 0;
			sumMaterialFee = 0;
			sumPreDefLoss();
		}
	} else {
		for (i = 1; i < fm.all("prpLrepairFeeManHour").length; i++) {
			repairFeeManHour = parseFloat(fm.all("prpLrepairFeeManHour")[i].value);
			repairFeeManHourUnitPrice = parseFloat(fm.all("prpLrepairFeeManHourUnitPrice")[i].value);
			repairFeeMaterialFee = parseFloat(fm.all("prpLrepairFeeMaterialFee")[i].value);
			if (isNaN(repairFeeManHour)) {
				repairFeeManHour = 0;
			}
			if (isNaN(repairFeeManHourUnitPrice)) {
				repairFeeManHourUnitPrice = 0;
			}
			if (isNaN(repairFeeMaterialFee)) {
				repairFeeMaterialFee = 0;
			}

			sumManHourFee = sumManHourFee + parseFloat(point(round(repairFeeManHour * repairFeeManHourUnitPrice, 0), 0));
			sumMaterialFee = sumMaterialFee + parseFloat(point(round(repairFeeMaterialFee, 0), 0));
			sumRepairDefFee = sumManHourFee + sumMaterialFee;

		}
		fm.SumDefLoss1.value = sumRepairDefFee;
		fm.SumManHourFee1.value = sumManHourFee;
		fm.SumMaterialFee1.value = sumMaterialFee;
		sumPreDefLoss();
	}
	return true;
}

/**
 *@description 汇总换件费计算
 *@param       无
 *@return      无
 */

function sumComponentFee() {
	var sumComponentDefFee = 0; //总换件定损费
	var sumComponentManHourFee = 0; //总工时费
	var sumComponentMaterialFee = 0; //总材料费
	var quantity = 0;
	var manHourFee = 0;
	var materialFee = 0;

	if (fm.all("SumDefLoss2").length > 0) {
		for (j = 0; j < fm.all("SumDefLoss2").length; j++) {
			for (i = 1; i < fm.all("prpLcomponentQuantity").length; i++) {
				if (j == fm.all("carLossComponentLossItemCode")[i].value) {
					quantity = parseFloat(fm.all("prpLcomponentQuantity")[i].value);
					manHourFee = parseFloat(fm.all("prpLcomponentManHourFee")[i].value);
					materialFee = parseFloat(fm.all("prpLcomponentMaterialFee")[i].value);
					if (isNaN(quantity) || quantity.length < 1) {
						quantity = 0;
					}
					if (isNaN(manHourFee) || manHourFee.length < 1) {
						manHourFee = 0;
					}
					if (isNaN(materialFee) || materialFee.length < 1) {
						materialFee = 0;
					}
					sumComponentManHourFee = sumComponentManHourFee + parseFloat(point(round(quantity * manHourFee, 0), 0));
					sumComponentMaterialFee = sumComponentMaterialFee + parseFloat(point(round(materialFee, 0), 0));
					sumComponentDefFee = sumComponentManHourFee + sumComponentMaterialFee;
				}
			}
			fm.all("SumDefLoss2")[j].value = point(round(sumComponentDefFee, 0), 0);
			fm.all("SumManHourFee2")[j].value = point(round(sumComponentManHourFee, 0), 0);
			fm.all("SumMaterialFee2")[j].value = point(round(sumComponentMaterialFee, 0), 0);
			sumComponentDefFee = 0;
			sumComponentManHourFee = 0;
			sumComponentMaterialFee = 0;
			sumPreDefLoss();
		}
	} else {
		for (i = 1; i < fm.all("prpLcomponentQuantity").length; i++) {
			quantity = parseFloat(fm.all("prpLcomponentQuantity")[i].value);
			manHourFee = parseFloat(fm.all("prpLcomponentManHourFee")[i].value);
			materialFee = parseFloat(fm.all("prpLcomponentMaterialFee")[i].value);
			if (isNaN(quantity)) {
				quantity = 0;
			}
			if (isNaN(manHourFee)) {
				manHourFee = 0;
			}
			if (isNaN(materialFee)) {
				materialFee = 0;
			}

			sumComponentManHourFee = sumComponentManHourFee + parseFloat(point(round(quantity * manHourFee, 0), 0));
			sumComponentMaterialFee = sumComponentMaterialFee + parseFloat(point(round(materialFee, 0), 0));
			sumComponentDefFee = sumComponentManHourFee + sumComponentMaterialFee;
		}
		fm.SumDefLoss2.value = point(round(sumComponentDefFee, 0), 0);
		fm.SumManHourFee2.value = point(round(sumComponentManHourFee, 0), 0);
		fm.SumMaterialFee2.value = point(round(sumComponentMaterialFee, 0), 0);
		sumPreDefLoss();
	}
	return true;
}
/**
 *@description 设值页面的一些初始化信息
 *@param       无
 *@return      通过返回true,否则返回false
 */

function initSet() {

	var sumloss = "prpLpropSumLoss";
	var sumreject = "prpLpropSumReject";
	var sumdefloss = "prpLpropSumDefLoss";
	var sumvalue;
	var sumallvalue;

	for (i = 1; i < fm.all(sumloss).length; i++) {
		sumvalue = fm.all(sumloss)[i].value;

		if (isNaN(sumvalue) || sumvalue.length < 1) {
			sumvalue = 0;
		}
		if (isNaN(sumallvalue) || sumallvalue.length < 1)
			sumallvalue = 0;
		sumallvalue = parseFloat(sumallvalue) + parseFloat(sumvalue);
	}
	fm.prpLpropSumSumLoss.value = point(round(sumallvalue, 0), 0);

	sumallvalue = 0;
	for (i = 1; i < fm.all(sumreject).length; i++) {
		sumvalue = fm.all(sumreject)[i].value;

		if (isNaN(sumvalue) || sumvalue.length < 1)
			sumvalue = 0;
		if (isNaN(sumallvalue) || sumallvalue.length < 1)
			sumallvalue = 0;

		sumallvalue = parseFloat(sumallvalue) + parseFloat(sumvalue);
	}
	fm.prpLpropSumSumReject.value = point(round(sumallvalue, 0), 0);

	sumallvalue = 0;
	for (i = 1; i < fm.all(sumdefloss).length; i++) {
		sumvalue = fm.all(sumdefloss)[i].value;
		if (isNaN(sumvalue) || sumvalue.length < 1) {
			sumvalue = 0;
		}
		if (isNaN(sumallvalue) || sumallvalue.length < 1) {
			sumallvalue = 0;
		}
		sumallvalue = parseFloat(sumallvalue) + parseFloat(sumvalue);
	}
	fm.prpLpropSumSumDefLoss.value = point(round(sumallvalue, 0), 0);
	sumPersonLossFee();
	sumPreDefLoss();
	return true;
}

/**
 *@description 计算总的定损金额
 *@param       无
 *@return      通过返回true,否则返回false
 */

function sumPreDefLoss() {
	var sumPropLoss = 0; //财产
	var sumPersonLoss = 0; //人员

	var sumPreDefLoss = 0;
	sumPropLoss = fm.prpLpropSumSumDefLoss.value;
	if (isNaN(sumPropLoss))
		sumPropLoss = 0;

	sumPreDefLoss = parseFloat(sumPropLoss) + parseFloat(sumPersonLoss);
}


/**
 @description 计算人员伤亡费用清单核损金额
 @param       Field: 触发域 
 @return      无     
 @see         calculateAll
*/

function calSumPersonDefLoss(Field) {
	var fieldname = Field.name;
	var i = 0;
	var findex = 0;
	var sumLoss;
	var sumReject;
	var sumDefLoss;

	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == Field) {
			findex = i;
			break;
		}
	}

	sumLoss = parseFloat(fm.all("prpLpersonSumLoss")[findex].value);
	sumReject = parseFloat(fm.all("prpLpersonSumReject")[findex].value);

	if (isNaN(sumLoss))
		sumLoss = 0;
	if (isNaN(sumReject))
		sumReject = 0;

	if (sumLoss == 0 && sumReject == 0)
		return;

	sumDefLoss = sumLoss - sumReject;

	fm.all("prpLpersonSumDefLoss")[findex].value = point(round(sumDefLoss, 0), 0);
	sumPersonLossFee();
}

/**
 *@description 汇总人员伤亡费用计算
 *@param       无
 *@return      无
 */

function sumPersonLossFee() {
	var prpLpersonSumLossSum = 0; //受损金额
	var prpLpersonSumRejectSum = 0; //剔除金额
	var prpLpersonSumDefLossSum = 0; //核损金额 
	var sumLoss = 0;
	var sumReject = 0;
	var sumDefLoss = 0;
	var vlength = 0;
	if (isNaN(fm.all("prpLpersonSumLossSum"))) {
		vlength = fm.all("prpLpersonSumLossSum").length;
	}
	if (vlength > 0) {
		for (j = 0; j < fm.all("prpLpersonSumLossSum").length; j++) {
			for (i = 1; i < fm.all("prpLpersonSumLoss").length; i++) {
				if (j == (fm.all("personSerialNo")[i].value - 1)) {
					sumLoss = parseFloat(fm.all("prpLpersonSumLoss")[i].value);
					sumReject = parseFloat(fm.all("prpLpersonSumReject")[i].value);
					sumDefLoss = parseFloat(fm.all("prpLpersonSumDefLoss")[i].value);
					if (isNaN(sumLoss) || sumLoss.length < 1) {
						quantity = 0;
					}
					if (isNaN(sumReject) || sumReject.length < 1) {
						manHourFee = 0;
					}
					if (isNaN(sumDefLoss) || sumDefLoss.length < 1) {
						materialFee = 0;
					}
					prpLpersonSumLossSum = prpLpersonSumLossSum + parseFloat(point(round(sumLoss, 0), 0));
					prpLpersonSumRejectSum = prpLpersonSumRejectSum + parseFloat(point(round(sumReject, 0), 0));
					prpLpersonSumDefLossSum = prpLpersonSumDefLossSum + parseFloat(point(round(sumDefLoss, 0), 0));
				}
			}
			fm.all("prpLpersonSumLossSum")[j].value = point(round(prpLpersonSumLossSum, 0), 0);
			fm.all("prpLpersonSumRejectSum")[j].value = point(round(prpLpersonSumRejectSum, 0), 0);
			fm.all("prpLpersonSumDefLossSum")[j].value = point(round(prpLpersonSumDefLossSum, 0), 0);
			prpLpersonSumLossSum = 0; //受损金额
			prpLpersonSumRejectSum = 0; //剔除金额
			prpLpersonSumDefLossSum = 0; //核损金额 
			sumPreDefLoss();
		}

	} else if (vlength == 0) {} else {
		for (i = 1; i < fm.all("prpLpersonSumLoss").length; i++) {
			sumLoss = parseFloat(fm.all("prpLpersonSumLoss")[i].value);
			sumReject = parseFloat(fm.all("prpLpersonSumReject")[i].value);
			sumDefLoss = parseFloat(fm.all("prpLpersonSumDefLoss")[i].value);
			if (isNaN(sumLoss) || sumLoss.length < 1) {
				quantity = 0;
			}
			if (isNaN(sumReject) || sumReject.length < 1) {
				manHourFee = 0;
			}
			if (isNaN(sumDefLoss) || sumDefLoss.length < 1) {
				materialFee = 0;
			}
			prpLpersonSumLossSum = prpLpersonSumLossSum + parseFloat(point(round(sumLoss, 0), 0));
			prpLpersonSumRejectSum = prpLpersonSumRejectSum + parseFloat(point(round(sumReject, 0), 0));
			prpLpersonSumDefLossSum = prpLpersonSumDefLossSum + parseFloat(point(round(sumDefLoss, 0), 0));
		}
		fm.prpLpersonSumLossSum.value = point(round(prpLpersonSumLossSum, 0), 0);
		fm.prpLpersonSumRejectSum.value = point(round(prpLpersonSumRejectSum, 0), 0);
		fm.prpLpersonSumDefLossSum.value = point(round(prpLpersonSumDefLossSum, 0), 0);
		sumPreDefLoss();
	}

}


/**
 *@description 弹出关联页面
 *@param       无
 *@return      通过返回true,否则返回false
 */

function relate() {
	var policyNo = fm.PolicyNo.value;
	var registNo = fm.RegistNo.value;
	var newWindow = window.open("/claim/RelateBusinessNo.do?policyNo=" + policyNo + "&registNo=" + registNo, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
}