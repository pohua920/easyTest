/*****************************************************************************
 * DESC       ：核损登记的脚本函数页面
 * AUTHOR     ：中科軟
 * CREATEDATE ： 2004-07-13
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/

function insertRowCertainLossExt(RowCode) {
	insertRow(RowCode);

	var element1 = document.getElementsByName('prpLverifyLossExtTitle');
	var i = element1.length - 1;

	if (fm.nodeType.value == "verip" || fm.nodeType.value == "verpo") {
		if (fm.verpOpinion.value == "01")
			fm.prpLverifyLossExtTitle[i].value = "同意定損";
		else if (fm.verpOpinion.value == "02")
			fm.prpLverifyLossExtTitle[i].value = "補充信息";
		else if (fm.verpOpinion.value == "03")
			fm.prpLverifyLossExtTitle[i].value = "價格異議發回";
		else if (fm.verpOpinion.value == "04")
			fm.prpLverifyLossExtTitle[i].value = "價格已修正";
		else if (fm.verpOpinion.value == "05")
			fm.prpLverifyLossExtTitle[i].value = "向外詢價";
	}

	if (fm.nodeType.value == "verif") {
		if (fm.verifyOpinion.value == "01")
			fm.prpLverifyLossExtTitle[i].value = "同意定損";
		else if (fm.verifyOpinion.value == "02")
			fm.prpLverifyLossExtTitle[i].value = "價格異議";
		else if (fm.verifyOpinion.value == "03")
			fm.prpLverifyLossExtTitle[i].value = "信息不充分";
		else if (fm.verifyOpinion.value == "04")
			fm.prpLverifyLossExtTitle[i].value = "出具檢驗報告";
		else if (fm.verifyOpinion.value == "05")
			fm.prpLverifyLossExtTitle[i].value = "其它";

	}
}


/**
 *@description 回勘初始化的只读项设置
 *@param       无
 *@return      通过返回true,否则返回false
 */

function initReadonly() {
	setReadonlyOfElementOfLoss(fm.prpLrepairFeeRepairFactoryName);
	setReadonlyOfElementOfLoss(fm.prpLrepairFeeRepairStartDate);
	setReadonlyOfElementOfLoss(fm.prpLrepairFeeRepairEndDate);
	setReadonlyOfElementOfLoss(fm.prpLrepairFeeHandlerCode);
	setReadonlyOfElementOfLoss(fm.prpLrepairFeeHandlerName);
	setReadonlyOfElementOfLoss(fm.prpLverifyLossVerifyRemark);
	setReadonlyOfElementOfLoss(fm.prpLverifyLossWarpDefLoss);

	var element1 = document.getElementsByName('prpLcomponentKindCode');

	if (element1.length > 1) {
		for (i = 1; i < element1.length; i++) {}
	} else {}

	var element2 = document.getElementsByName('prpLrepairFeeKindCode');
	if (element2.length > 1) {
		for (j = 0; j < element2.length; j++) {
			setReadonlyOfElementOfLoss(fm.prpLrepairFeeVeriMaterialFee[j]);
			setReadonlyOfElementOfLoss(fm.prpLrepairFeeVeriSumLoss[j]);
			setReadonlyOfElementOfLoss(fm.prpLrepairFeeVeriRemark[j]);
		}
	} else {}

	return true;
}


/**
 @author 中科软
 @description 使一个输入域设置为只读
 @param       iElement: 域   
 @return      无
 */

function setReadonlyOfElementOfLoss(iElement) {
	if (iElement.type != null) {
		if (iElement.type == "select-one") {
			iElement.disabled = true;
		} else if (iElement.type == "text") {
			iElement.onfocus = null;
			iElement.onblur = null;
			iElement.readOnly = true;
			iElement.className = "readonly";
		} else if (iElement.type == "button") {
			iElement.style.display = "none";
		}
	}
}


/**
 *@description 检查核损登记
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

function change() {
	var element = document.getElementsByName('prpLverifyLossExtTitle');
	var buttonSave = document.getElementsByName("buttonSave");
	var buttonSaveFinishSubmit = document.getElementsByName("buttonSaveFinishSubmit");
	var i = element.length - 1;
	if (fm.verifyOpinion.value == "01") {
		buttonSave[0].style.display = "";
		buttonSaveFinishSubmit[0].style.display = "";
		fm.prpLverifyLossExtTitle[i].value = "同意定損";
	} else if (fm.verifyOpinion.value == "02") {
		buttonSave[0].style.display = "none";
		buttonSaveFinishSubmit[0].style.display = "none";
		fm.prpLverifyLossExtTitle[i].value = "價格異議";
	} else if (fm.verifyOpinion.value == "03") {
		buttonSave[0].style.display = "none";
		buttonSaveFinishSubmit[0].style.display = "none";
		fm.prpLverifyLossExtTitle[i].value = "信息不充分";
	} else if (fm.verifyOpinion.value == "04") {
		buttonSave[0].style.display = "none";
		buttonSaveFinishSubmit[0].style.display = "none";
		fm.prpLverifyLossExtTitle[i].value = "出具檢驗報告";
	} else if (fm.verifyOpinion.value == "99") {
		buttonSave[0].style.display = "none";
		buttonSaveFinishSubmit[0].style.display = "none";
		fm.prpLverifyLossExtTitle[i].value = "其它";
	}
}

/**
 *@description 根据按钮状态保存报案数据
 *@param       this
 *@param       保存状态
 *@return      通过返回true,否则返回false
 */

function saveForm(field, saveType) {


	var element = document.getElementsByName('prpLverifyLossExtTitle');
	var i = element.length - 1;
	if (i > 0 && fm.prpLverifyLossExtTitle[i].value == "") {
		alert("請輸入核損意見！");
		return false;
	}

	fm.buttonSaveType.value = saveType;


	//得到核损意见
	//取LossLossItemCode
	var prpLverifyLossLossItemCode = trim(fm.prpLverifyLossLossItemCode.value);
	var lossItemCode = 0;
	lossItemCode = parseInt(prpLverifyLossLossItemCode);
	var prpLverifyLossNodeType = trim(fm.prpLverifyLossNodeType.value);
	if("certa"==prpLverifyLossNodeType){
		var verifyOpinion = trim(fm.verifyOpinion.value);
	}
	if (saveType == "4") {

		if (trim(fm.nodeType.value) != "backc") {

			if (trim(fm.nextNodeNo.value).length < 1) {
				alert("請選擇要提交的下一個節點！")
				return false;
			}

			//判断是否可以提交
			if("certa"==prpLverifyLossNodeType){
				if (verifyOpinion != "01") {
					errorMessage("核損意見爲同意定損時才可以提交該案件!")
					return false;
				}
			}
			//增加对定损核损金额不一致时，不能提交核损的限制：	
			//定损价格
			var CarSumPreDefLoss = document.getElementsByName("CarSumPreDefLoss")[0].value;
			var CarSumDefLoss = document.getElementsByName("CarSumDefLoss")[0].value;
			if (parseFloat(CarSumPreDefLoss) != parseFloat(CarSumDefLoss)) {
				errorMessage("核損金額與定損金額不同，只有同意定損金額時才可以提交該案件!");
				return false;
			}

		}
	}

	if (saveType == "5") {
		//判断是否可以提交
		if("certa"==prpLverifyLossNodeType){
			if (verifyOpinion == "01") {
				errorMessage("核損意見為同意定損時不可以退回該案件!")
				return false;
			}
		}

	}
	//reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
	field.disabled = true;
	fm.submit();
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

	//增加保费是否实收提示
	var payFee = fm.prpLverifyLossPayFee.value;
	var errorMessage = "";
	if (payFee == -1) {
		errorMessage=errorMessage+i18n.certainLoss.policyPremiumNoPay;  // 此保单保费未缴,请慎重处理！！
	} else if (payFee == 0) {
		errorMessage=errorMessage+i18n.certainLoss.policyPremiumPay;  // 此保单已缴未缴全,请慎重处理！！！
	}
	if (errorMessage != "") {
		alert(errorMessage);
	}

	checkObject = eval(fm.all(sumloss));
	if (checkObject != null) {
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
	}
	sumallvalue = 0;
	checkObject = eval(fm.all(sumreject));
	if (checkObject != null) {
		for (i = 1; i < fm.all(sumreject).length; i++) {
			sumvalue = fm.all(sumreject)[i].value;

			if (isNaN(sumvalue) || sumvalue.length < 1)
				sumvalue = 0;
			if (isNaN(sumallvalue) || sumallvalue.length < 1)
				sumallvalue = 0;

			sumallvalue = parseFloat(sumallvalue) + parseFloat(sumvalue);
		}
		fm.prpLpropSumSumReject.value = point(round(sumallvalue, 0), 0);
	}
	sumallvalue = 0;
	checkObject = eval(fm.all(sumdefloss));
	if (checkObject != null) {
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
	}
	sumRepairFee();
	sumComponentFee();
	sumPersonLossFee();
	return true;
}

/**
 *@description 计算工时费合计、核价工时费合计
 *@param       无
 *@return      无
 */

function sumRepairFee() {
	var sumRepairDefFee = 0; //总修理定损费
	var sumRepairVerifyFee = 0; //总修理核价费
	var prpLrepairFeeSumDefLoss = 0;
	var prpLrepairFeeVeriSumLoss = 0;
	//modify by zhyi 20110905 fubon-2422　增加浮動比例
	var sumFloatRate = 0;
	var sumRepairDefFeeTemp = 0;
	var sumRepairVerifyFeeTemp = 0;

	try {
		for (i = 1; i < fm.all("prpLrepairFeeManHour").length; i++) {
			prpLrepairFeeVeriSumLoss = point(round(parseFloat(fm.all("prpLrepairFeeVeriSumLoss")[i].value), 0), 0);
			prpLrepairFeeSumDefLoss = point(round(parseFloat(fm.all("prpLrepairFeeSumDefLoss")[i].value), 0), 0);

			if (isNaN(prpLrepairFeeVeriSumLoss)) {
				prpLrepairFeeVeriSumLoss = 0;
			}
			if (isNaN(prpLrepairFeeSumDefLoss)) {
				prpLrepairFeeSumDefLoss = 0;
			}

			sumRepairDefFee = sumRepairDefFee + prpLrepairFeeSumDefLoss;
			sumRepairVerifyFee = sumRepairVerifyFee + prpLrepairFeeVeriSumLoss;
		}
		//定损工时费合计
		sumFloatRate = parseFloat(fm.prpLcarLossSumFloatRate.value);
		if (isNaN(sumFloatRate) || sumFloatRate == "") {
			SumManager = 0;
			fm.prpLcarLossSumFloatRate.value = 0;
		}
		sumRepairDefFeeTemp = sumRepairDefFee * (1 + parseFloat(sumFloatRate) / 100);
		sumRepairDefFeeTemp = round(sumRepairDefFeeTemp, 0);
		sumRepairVerifyFeeTemp = sumRepairVerifyFee * (1 + parseFloat(sumFloatRate) / 100);
		sumRepairVerifyFeeTemp = round(sumRepairVerifyFeeTemp, 0);
		fm.SumDefLoss1.value = sumRepairDefFeeTemp;
		//核价工时费合计
		fm.SumVerifyLoss1.value = sumRepairVerifyFeeTemp;

		sumprpLverifyLossWarpDefLoss();
	} catch (ex) {}

	return true;
}


/**
 *@description 计算换件费合计、核价换件费合计
 *@param       无
 *@return      无
 */

function sumComponentFee() {
	var sumComponentMaterialFee = 0; //定损换件费合计
	var sumComponentVerpCompPrice = 0; //核价换件费合计
	var sumManageFee = 0; //管理费 
	var sumVerifyManageFee = 0; //总公司核定管理费
	var sumTax = 0; //税金   
	var sumTransFee = 0; //运费
	var sumRestFee = 0; //残值合计
	var quantity = 0;
	var verpCompPrice = 0;
	var materialFee = 0;
	if (fm.all("prpLcomponentVeriVerpCompPrice") != null) {
		if (fm.all("prpLcomponentVeriVerpCompPrice").length == undefined) {
			sumComponentVerpCompPrice = sumComponentVerpCompPrice + parseFloat(fm.prpLcomponentVeriVerpCompPrice.value);
		} else {
			for (var i = 0; i < fm.all("prpLcomponentVeriVerpCompPrice").length; i++) {


				sumComponentVerpCompPrice = sumComponentVerpCompPrice + parseFloat(fm.prpLcomponentVeriVerpCompPrice[i].value);
			}
		}
	}

	for (var j = 1; j < fm.all("prpLcomponentVeriRestFee").length; j++) {
		if (fm.all("prpLcomponentIfRemain")[j].value != '1') {
			sumRestFee = sumRestFee + parseFloat(fm.all("prpLcomponentVeriRestFee")[j].value);
		}
		if (isNaN(sumRestFee))
			sumRestFee = 0;
	}
	if (sumRestFee == 0) {
		sumRestFee = fm.prpLcarLossSumRest.value
	} else {
		fm.prpLcarLossSumRest.value = sumRestFee;
	}
	sumTransFee = parseFloat(fm.prpLcarLossSumTransFee.value);
	sumManageFee = parseFloat(fm.prpLcarLossSumManager.value);
	if (isNaN(sumTransFee) || sumTransFee == "") {
		sumTransFee = 0;
	}
	if (isNaN(sumManageFee) || sumManageFee == "") {
		sumManageFee = 0;
	}
	if (isNaN(sumManageFee)) {
		sumManageFee = 0;
	}
	//總費用計算＝配件費－殘值+運費+(-)管理費
	fm.SumVerifyLoss2.value = (point(round(sumComponentVerpCompPrice, 0), 0) + point(round(sumTransFee, 0), 0) - 　point(round(sumRestFee, 0), 0)) + point(round((sumComponentVerpCompPrice * sumManageFee / 100), 0), 0); //换件总金额

	//核损总金额 
	fm.SumDefLoss2.value = fm.SumVerifyLoss2.value;
	fm.CarSumDefLoss.value = point(round(parseFloat(fm.SumVerifyLoss1.value), 0), 0) + point(round(parseFloat(fm.SumVerifyLoss2.value), 0), 0);
	sumprpLverifyLossWarpDefLoss();
}

/**
 * 偏差定损金额计算
 */

function sumprpLverifyLossWarpDefLoss() {
	fm.prpLverifyLossWarpDefLoss.value = parseInt(fm.SumVerifyLoss1.value) + parseInt(fm.SumDefLoss2.value) - parseInt(fm.prpLverifyLossFirstDefLoss.value);
	fm.prpLverifyLossWarpDefLoss.value = point(round(fm.prpLverifyLossWarpDefLoss.value, 0), 0);
}



/**
 *@description 汇总人员伤亡费用计算
 *@param       无
 *@return      无
 */

function sumPersonLossFee() {
	/**********************/
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
		for (j = 1; j < fm.all("prpLpersonSumLossSum").length; j++) {
			for (i = 1; i < fm.all("prpLpersonSumLoss").length; i++) {
				if (j == fm.all("personSerialNo")[i].value) {
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
 *@description 计算总的定损金额
 *@param       无
 *@return      通过返回true,否则返回false
 */

function sumPreDefLoss() {
	var sumPropLoss = 0; //财产
	var sumRepairLoss = 0; //修理
	var sumComponentLoss = 0; //换件
	var sumPersonLoss = 0; //人员

	var sumPreDefLoss = 0;
	if (fm.prpLpropSumSumDefLoss != undefined) {
		sumPropLoss = fm.prpLpropSumSumDefLoss.value;
	}
	if (isNaN(sumPropLoss))
		sumPropLoss = 0;
	if (fm.all("SumDefLoss1") != null) {
		if (fm.all("SumDefLoss1").length > 0) {
			for (i = 0; i < fm.all("SumDefLoss1").length; i++) {
				sumRepairLoss = parseFloat(sumRepairLoss) + parseFloat(fm.all("SumDefLoss1")[i].value);
			}
		} else {
			sumRepairLoss = fm.SumDefLoss1.value;
		}
	}
	if (isNaN(sumRepairLoss) || sumRepairLoss.length < 1)
		sumRepairLoss = 0;

	if (fm.all("SumDefLoss1") != null) {
		if (fm.all("SumDefLoss2").length > 0) {
			for (i = 0; i < fm.all("SumDefLoss2").length; i++) {
				sumComponentLoss = parseFloat(sumComponentLoss) + parseFloat(fm.all("SumDefLoss2")[i].value);
			}
		} else {
			sumComponentLoss = fm.SumDefLoss2.value;
		}
	}
	if (isNaN(sumComponentLoss) || sumComponentLoss.length < 1)
		sumComponentLoss = 0;

	sumPreDefLoss = parseFloat(sumRepairLoss) + parseFloat(sumPropLoss) + parseFloat(sumComponentLoss) + parseFloat(sumPersonLoss);
	fm.prpLverifyLossSumPreDefLoss.value = sumPreDefLoss;

}


/**
 @author 中科软
 @description 校验核损金额
 @param       Field: 触发域  
 @return      boolean: 合法为true,否则为false   
 */

function checkPrpLpersonVeriSumLoss(Field) {
	var fieldname = Field.name;
	var findex = 0;
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == Field) {
			findex = i;
			break;
		}
	}
	if (parseFloat(Field.value) > parseFloat(fm.prpLpersonSumLoss[findex].value)) {
		errorMessage("核损金额不应大於定损金额,请重新输入！");
		Field.focus();
		Field.select();
		return false;
	}
	return true;
}

/**
 @author 中科软 
 @description 校验核损金额
 @param       Field: 触发域  
 @return      boolean: 合法为true,否则为false   
 */

function checkPrpLpersonVeriSumReject(Field) {
	var fieldname = Field.name;
	var findex = 0;
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == Field) {
			findex = i;
			break;
		}
	}
	if (parseFloat(Field.value) > parseFloat(fm.prpLpersonSumReject[findex].value)) {
		errorMessage("核损金额不应大於定损金额,请重新输入！");
		Field.focus();
		Field.select();
		return false;
	}
	return true;
}

/**
 @author 中科软 
 @description 校验核损金额
 @param       Field: 触发域  
 @return      boolean: 合法为true,否则为false   
 */

function checkPrpLpersonVeriSumDefLoss(Field) {
	var fieldname = Field.name;
	var findex = 0;
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == Field) {
			findex = i;
			break;
		}
	}
	if (parseFloat(Field.value) > parseFloat(fm.prpLpersonSumDefLoss[findex].value)) {
		errorMessage("核损金额不应大於定损金额,请重新输入！");
		Field.focus();
		Field.select();
		return false;
	}
	return true;
}


/**
 @description 计算人员伤亡费用清单核损金额
 @param       Field: 触发域 
 @return      无      
*/

function calSumPersonVeriDefLoss(Field) {
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

	sumLoss = parseFloat(fm.all("prpLpersonVeriSumLoss")[findex].value);
	sumReject = parseFloat(fm.all("prpLpersonVeriSumReject")[findex].value);

	if (isNaN(sumLoss))
		sumLoss = 0;
	if (isNaN(sumReject))
		sumReject = 0;

	if (sumLoss == 0 && sumReject == 0)
		return;

	sumDefLoss = sumLoss - sumReject;

	fm.all("prpLpersonVeriSumDefLoss")[findex].value = point(round(sumDefLoss, 0), 0);

}

/**
 *@description 根据报价，管理费率计算零配件的价格
 *@param       无
 *@return      通过返回true,否则返回false
 */

function getMaterialFee(field, ext) {
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
	if (fieldname == "prpLcomponentQuotedPrice") {
		QuotedPrice = parseFloat(field.value);
		MaterialFee = QuotedPrice * (1 + fm.prpLcarLossSumVeriManager.value / 100);
		fm.all("prpLcomponentMaterialFee")[findex].value = point(round(MaterialFee, 0), 0);
	} else {
		for (i = 1; i < fm.all("prpLcomponentMaterialFee").length; i++) {
			QuotedPrice = parseFloat(fm.all("prpLcomponentQuotedPrice")[i].value);
			MaterialFee = QuotedPrice * (1 + field.value / 100);
			fm.all("prpLcomponentMaterialFee")[i].value = point(round(MaterialFee, 0), 0);
		}
	}
	return true;
}

/**
 *@description 设置伤情类别得隐含域
 *@param       无
 *@return      通过返回true,否则返回false
 */

function woundCodeChange(field) {
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

	//更改隐含项的值
	if (field.checked == true) {
		fm.all(fieldname + "Txt")[findex].value = "1";
	} else {
		fm.all(fieldname + "Txt")[findex].value = "0";
	}
	return true;
}


/**
 *@description 根据工时，工时单价和材料费用计算定损金额
 *@param       无
 *@return      通过返回true,否则返回false
 */

function getSumDefLossVerify(field, ext) {
	var fieldname = field.name;
	var i = 0;
	var findex = 0;
	var manHourFee;
	var materialFee;
	var sumDefLoss;
	var ManHour;
	var Quantity;
	var veriRestFee = 0;

	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == field) {
			findex = i;
			break;
		}
	}

	if (ext == 1) {
		ManHour = parseFloat(fm.all("prpLrepairFeeVeriManHour")[findex].value);
		manHourFee = parseFloat(fm.all("prpLrepairFeeVeriManUnitPrice")[findex].value)
		materialFee = parseFloat(fm.all("prpLrepairFeeVeriMaterialFee")[findex].value);
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

		sumDefLoss = manHourFee * ManHour;
		fm.all("prpLrepairFeeVeriSumLoss")[findex].value = point(round(sumDefLoss, 0), 0);
		sumRepairFee();
	} else {

		quantity = parseFloat(fm.all("prpLcomponentVeriQuantity")[findex].value)
		manHourFee = parseFloat(fm.all("prpLcomponentVeriManHourFee")[findex].value);
		materialFee = parseFloat(fm.all("prpLcomponentVeriMaterFee")[findex].value);
		veriRestFee = parseFloat(fm.all("prpLcomponentVeriRestFee")[findex].value);


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
		if (isNaN(veriRestFee) || veriRestFee.length < 1) {
			veriRestFee = 0;
		}

		sumDefLoss = parseFloat(point(round(quantity * materialFee, 0), 0)) + parseFloat(point(round(manHourFee, 0), 0));
		sumDefLoss = sumDefLoss - veriRestFee;
		fm.all("prpLcomponentSumVeriLoss")[findex].value = point(round(sumDefLoss, 0), 0);
		sumComponentFee();
	}
	return true;

}


/**
 *@description 弹出查勘的画面
 *@param       无
 *@return      通过返回true,否则返回false
 */

function relateCheck() {
	var registNo = fm.RegistNo.value;
	var linkURL = "/claim/check/checkFinishQueryList.do?prpLcheckCheckNo=" + registNo + "&editType=SHOW&riskCode=" + fm.riskcode.value;
	//modify by liuwei at 2011-05-03 当不是从弹出窗口弹出窗口时，弹出窗口显示【关闭】按钮而不是【退回】按钮 start
	if (opener == undefined) {
		linkURL = linkURL + "&ifclose=true";
	}
	//modify by liuwei at 2011-05-03 当不是从弹出窗口弹出窗口时，弹出窗口显示【关闭】按钮而不是【退回】按钮 end
	var newWindow = window.open(linkURL, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
}

/**
 *@description 弹出报案的画面
 *@param       无
 *@return      通过返回true,否则返回false
 */

function relateRegist() {
	var registNo = fm.RegistNo.value;
	var linkURL = "/claim/registFinishQueryList.do?prpLregistRegistNo=" + registNo + "&editType=SHOW&riskCode=" + fm.riskcode.value;
	//modify by liuwei at 2011-05-03 当不是从弹出窗口弹出窗口时，弹出窗口显示【关闭】按钮而不是【退回】按钮 start
	if (opener == undefined) {
		linkURL = linkURL + "&ifclose=true";
	}
	//modify by liuwei at 2011-05-03 当不是从弹出窗口弹出窗口时，弹出窗口显示【关闭】按钮而不是【退回】按钮 end
	var newWindow = window.open(linkURL, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
}


function relatePolicy() {
	var riskCode = fm.riskcode.value;
	var policyNo = fm.PolicyNo.value;
	var coreURL = fm.coreURL.value;
	var vURL = coreURL + riskCode + '/tbcbpg/UIPrPoEn' + riskCode + 'Show.jsp?BIZTYPE=POLICY&SHOWTYPE=SHOW&BizNo=' + policyNo + '&RiskCode=' + riskCode;
	window.open(vURL, '详细信息', 'width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 *@description 处理索赔资料清单
 *@param       businessNo
 */

function doCertifyDirect(businessNo, nodeType) {
	window.open("/claim/certifyBeforeEdit.do?RegistNo=" + businessNo + "&editType=CertifyDirect&nodeType=" + nodeType, "winName", "resizable=0,scrollbars=1,width=800,height=600");
}


function initVerifyReadonly() {
	var element1 = document.getElementsByName('prpLcomponentKindCode');

	if (element1.length > 1) {
		for (i = 0; i < element1.length; i++) {
			setReadonlyOfElementOfLoss(fm.prpLcomponentSys4SPrice[i]);
			setReadonlyOfElementOfLoss(fm.prpLcomponentSysMarketPrice[i]);
			setReadonlyOfElementOfLoss(fm.prpLcomponentSysMatchPrice[i]);
			setReadonlyOfElementOfLoss(fm.prpLcomponentNative4SPrice[i]);
			setReadonlyOfElementOfLoss(fm.prpLcomponentNativeMarketPrice[i]);
			setReadonlyOfElementOfLoss(fm.prpLcomponentVerpCompPrice[i]);
			setReadonlyOfElementOfLoss(fm.prpLcomponentNativeMatchPrice[i]);
			setReadonlyOfElementOfLoss(fm.prpLcomponentOriginalId[i]);
			setReadonlyOfElementOfLoss(fm.prpLcomponentFirstMaterialFee[i]);
			setReadonlyOfElementOfLoss(fm.prpLcomponentMaterialFee[i]);
			setReadonlyOfElementOfLoss(fm.prpLcomponentQuantity[i]);
			setReadonlyOfElementOfLoss(fm.prpLcomponentCompName[i]);
			setReadonlyOfElementOfLoss(fm.prpLcomponentKindName[i]);
			setReadonlyOfElementOfLoss(fm.prpLcomponentRemark[i]);
			setReadonlyOfElementOfLoss(fm.buttonComponentDelete[i]);

		}
	} else {

		setReadonlyOfElementOfLoss(fm.prpLcomponentSys4SPrice);
		setReadonlyOfElementOfLoss(fm.prpLcomponentSysMarketPrice);
		setReadonlyOfElementOfLoss(fm.prpLcomponentSysMatchPrice);
		setReadonlyOfElementOfLoss(fm.prpLcomponentNative4SPrice);
		setReadonlyOfElementOfLoss(fm.prpLcomponentNativeMarketPrice);
		setReadonlyOfElementOfLoss(fm.prpLcomponentVerpCompPrice);
		setReadonlyOfElementOfLoss(fm.prpLcomponentNativeMatchPrice);
		setReadonlyOfElementOfLoss(fm.prpLcomponentOriginalId);
		setReadonlyOfElementOfLoss(fm.prpLcomponentFirstMaterialFee);
		setReadonlyOfElementOfLoss(fm.prpLcomponentMaterialFee);
		setReadonlyOfElementOfLoss(fm.prpLcomponentQuantity);
		setReadonlyOfElementOfLoss(fm.prpLcomponentCompName);
		setReadonlyOfElementOfLoss(fm.prpLcomponentKindName);
		setReadonlyOfElementOfLoss(fm.prpLcomponentRemark);
		setReadonlyOfElementOfLoss(fm.buttonComponentDelete);

	}

	element1 = document.getElementsByName('prpLrepairFeeKindName');
	var partCode = document.getElementsByName('prpLrepairFeePartCode');
	var repairType = document.getElementsByName('prpLrepairFeeRepairType');
	if (partCode.length > 1) {
		for (i = 1; i < partCode.length; i++) {
			setReadonlyOfElementOfLoss(fm.prpLrepairFeePartCode[i]);
		}
	} else {
		setReadonlyOfElementOfLoss(fm.prpLrepairFeePartCode);
	}

	if (repairType.length > 1) {
		for (i = 1; i < repairType.length; i++) {
			setReadonlyOfElementOfLoss(fm.prpLrepairFeeRepairType[i]);
		}
	} else {
		setReadonlyOfElementOfLoss(fm.prpLrepairFeeRepairType);
	}

	if (element1.length > 1) {
		for (i = 0; i < element1.length; i++) {
			setReadonlyOfElementOfLoss(fm.prpLrepairFeeKindName[i]);
			setReadonlyOfElementOfLoss(fm.prpLrepairFeeManHour[i]);
			setReadonlyOfElementOfLoss(fm.prpLrepairFeeManHourUnitPrice[i]);
			setReadonlyOfElementOfLoss(fm.prpLrepairFeeSumDefLoss[i]);
			setReadonlyOfElementOfLoss(fm.prpLrepairFeeRemark[i]);
			setReadonlyOfElementOfLoss(fm.prpLrepairFeeCompName[i]);
			setReadonlyOfElementOfLoss(fm.buttonRepairFeeDelete[i]);
		}
	} else {

		setReadonlyOfElementOfLoss(fm.prpLrepairFeeKindName);
		setReadonlyOfElementOfLoss(fm.prpLrepairFeeManHour);
		setReadonlyOfElementOfLoss(fm.prpLrepairFeeManHourUnitPrice);
		setReadonlyOfElementOfLoss(fm.prpLrepairFeeSumDefLoss);
		setReadonlyOfElementOfLoss(fm.prpLrepairFeeRemark);
		setReadonlyOfElementOfLoss(fm.prpLrepairFeeCompName);
		setReadonlyOfElementOfLoss(fm.buttonRepairFeeDelete);
	}

	element1 = document.getElementsByName('prpLrepairFeeVeriManHour');
	if (element1.length > 1) {
		for (i = 0; i < element1.length; i++) {
			setReadonlyOfElementOfLoss(fm.prpLrepairFeeVeriManHour[i]);
			setReadonlyOfElementOfLoss(fm.prpLrepairFeeVeriManUnitPrice[i]);
		}
	} else {

		setReadonlyOfElementOfLoss(fm.prpLrepairFeeVeriManHour);
		setReadonlyOfElementOfLoss(fm.prpLrepairFeeVeriManUnitPrice);

	}

	setReadonlyOfElementOfLoss(fm.buttonRepairFee);

	tdNextHandleDept.innerHTML = "";
	fm.nextDeptName.style.display = "none";
	fm.nextHandlerName.style.display = "none";


	try {
		setReadonlyOfElementOfLoss(fm.verpOpinion);
	} catch (ex1) {

	}

	setReadonlyOfElementOfLoss(fm.prpLcarLossSumTransFee);
	setReadonlyOfElementOfLoss(fm.prpLcarLossSumTax);
	setReadonlyOfElementOfLoss(fm.prpLcarLossSumManager);
	setReadonlyOfElementOfLoss(fm.prpLcarLossSumVeriManager);
	setReadonlyOfElementOfLoss(fm.prpLcarLossSumRest);
	setReadonlyOfElementOfLoss(fm.prpLcarLossLossItemName);
	setReadonlyOfElementOfLoss(fm.prpLcarLossModelCode);
	setReadonlyOfElementOfLoss(fm.prpLcarLossBrandName);
	setReadonlyOfElementOfLoss(fm.prpLcarLossEngineNo);
	setReadonlyOfElementOfLoss(fm.prpLcarLossFrameNo);
	setReadonlyOfElementOfLoss(fm.prpLcarLossVINNo);
	setReadonlyOfElementOfLoss(fm.prpLrepairFeeRepairFactoryCode);
	setReadonlyOfElementOfLoss(fm.prpLrepairFeeRepairFactoryName);
	setReadonlyOfElementOfLoss(fm.prpLrepairFeeRepairStartDate);
	setReadonlyOfElementOfLoss(fm.prpLrepairFeeRepairEndDate);
	setReadonlyOfElementOfLoss(fm.prpLrepairFeeHandlerCode);
	setReadonlyOfElementOfLoss(fm.prpLrepairFeeHandlerName);
	setReadonlyOfElementOfLoss(fm.prpLverifyLossVerpRemark);

	return true;
}


function getVeriSumDefLoss(field) {
	var i = 0;
	var fieldname = field.name;
	var findex = 0;
	var veriSumDefLoss;
	var veriQuantity;
	var veriMaterFee;
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == field) {
			findex = i;
			break;
		}
	}
	if (fm.all(fieldname).length == undefined) {
		veriMaterFee = fm.prpLcomponentVeriMaterFee.value
		veriQuantity = fm.prpLcomponentVeriQuantity.value;
	} else {
		veriMaterFee = fm.prpLcomponentVeriMaterFee[findex].value
		veriQuantity = fm.prpLcomponentVeriQuantity[findex].value;
	}
	if (isNaN(veriMaterFee) || veriMaterFee == '')
		veriMaterFee = 0;
	if (isNaN(veriQuantity) || veriQuantity == '')
		veriQuantity = 0;

	if (fm.all(fieldname).length == undefined) {
		fm.prpLcomponentVeriSumDefLoss.value = point(round(veriMaterFee * veriQuantity, 0), 0);
		fm.prpLcomponentVeriVerpCompPrice.value = point(round(veriMaterFee * veriQuantity, 0), 0);
	} else {
		fm.prpLcomponentVeriSumDefLoss[findex].value = point(round(veriMaterFee * veriQuantity, 0), 0);
		fm.prpLcomponentVeriVerpCompPrice[findex].value = point(round(veriMaterFee * veriQuantity, 0), 0);
	}
	sumComponentFee();

}