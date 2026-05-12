/*****************************************************************************
 * DESC       ：核价登记的脚本函数页面
 * AUTHOR     ：中科軟
 * CREATEDATE ：2006-03-07
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
	if (fm.comLevel.value != "1") //总公司
		setReadonlyOfElementOfLoss(fm.prpLcarLossSumVeriManager);

	setReadonlyOfElementOfLoss(fm.prpLrepairFeeRepairFactoryName);
	setReadonlyOfElementOfLoss(fm.prpLrepairFeeRepairStartDate);
	setReadonlyOfElementOfLoss(fm.prpLrepairFeeRepairEndDate);
	setReadonlyOfElementOfLoss(fm.prpLrepairFeeHandlerCode);
	setReadonlyOfElementOfLoss(fm.prpLrepairFeeHandlerName);

	setReadonlyOfElementOfLoss(fm.prpLverifyLossVerifyRemark);

	var element1 = document.getElementsByName('prpLcomponentKindCode');
	if (element1.length > 1) {
		for (i = 1; i < element1.length; i++) {
			setReadonlyOfElementOfLoss(fm.prpLcomponentKindName[i]);
			setReadonlyOfElementOfLoss(fm.prpLcomponentCompName[i]);
			setReadonlyOfElementOfLoss(fm.prpLcomponentOriginalId[i]);
			setReadonlyOfElementOfLoss(fm.prpLcomponentFirstMaterialFee[i]);
			setReadonlyOfElementOfLoss(fm.prpLcomponentMaterialFee[i]);
			setReadonlyOfElementOfLoss(fm.prpLcomponentSys4SPrice[i]);
			setReadonlyOfElementOfLoss(fm.prpLcomponentSysMarketPrice[i]);
			setReadonlyOfElementOfLoss(fm.prpLcomponentSysMatchPrice[i]);
			setReadonlyOfElementOfLoss(fm.prpLcomponentNative4SPrice[i]);
			setReadonlyOfElementOfLoss(fm.prpLcomponentNativeMarketPrice[i]);
			setReadonlyOfElementOfLoss(fm.prpLcomponentNativeMatchPrice[i]);
			setReadonlyOfElementOfLoss(fm.prpLcomponentQuantity[i]);
			setReadonlyOfElementOfLoss(fm.prpLcomponentRemark[i]);
			setReadonlyOfElementOfLoss(fm.buttonComponentDelete[i]);
		}
	}

	var element2 = document.getElementsByName('prpLrepairFeeKindCode');
	if (element2.length > 1) {
		for (j = 1; j < element2.length; j++) {
			setReadonlyOfElementOfLoss(fm.prpLrepairFeeKindName[j]);
			setReadonlyOfElementOfLoss(fm.prpLrepairFeeCompName[j]);

			setReadonlyOfElementOfLoss(fm.prpLrepairFeeManHour[j]);
			setReadonlyOfElementOfLoss(fm.prpLrepairFeeManHourUnitPrice[j]);
			setReadonlyOfElementOfLoss(fm.prpLrepairFeeRemark[j]);

			setReadonlyOfElementOfLoss(fm.buttonRepairFeeDelete[j]);

		}
	}

	setReadonlyOfElementOfLoss(fm.buttonRepairFee);
	setReadonlyOfElementOfLoss(fm.buttonGetFittings);
	setReadonlyOfElementOfLoss(fm.buttonComponentInsert);


	if (fm.nodeType.value == "verpo") { //对外询价
		tdNextHandleDept.innerHTML = "";
		fm.nextDeptName.style.display = "none";
		fm.nextHandlerName.style.display = "none";

	}

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
 *@description 检查是不是可以按提交按钮
 *@return      通过返回true,否则返回false
 */

function checkSubmitButton() {
	var msg = fm.prpLverifyLossVerifyPriceOuterMsg.value;
	var sglCusCompP = parseFloat(fm.prpLclaimGradeSglCusCompP.value); //单个自定义配件价格
	var sglCarCusCompP = parseFloat(fm.prpLclaimGradeSglCarCusCompP.value); //单车自定义配件价格
	var sglCarAllCompP = parseFloat(fm.prpLclaimGradeSglCarAllCompP.value); //单车全部配件价格

	var cusCompPTmp = 0; //页面上一行自定义配件价格 对应上报价格
	var sglCusCompPTmp = 0; //页面上单个自定义配件价格 对应上报价格/数量
	var SumSglCarCusCompP = 0; //单车自定义配件价格
	var SumSglCarAllCompP = 0; //单车全部配件价格，包括标配
	var componentQuantity = 1; //页面上一行自定义配件数量 对应数量


	if (isNaN(sglCusCompP)) sglCusCompP = 0;
	if (isNaN(sglCarCusCompP)) sglCarCusCompP = 0;
	if (isNaN(sglCarAllCompP)) sglCarAllCompP = 0;

	if (msg == "") {
		var element1 = document.getElementsByName('prpLcomponentKindCode');
		if (element1.length > 1) {
			for (i = 1; i < element1.length; i++) {
				cusCompPTmp = parseFloat(fm.prpLcomponentMaterialFee[i].value);
				componentQuantity = fm.prpLcomponentQuantity[i].value;
				if (isNaN(componentQuantity)) {
					componentQuantity = 1;
				}

				if (isNaN(cusCompPTmp)) {
					cusCompPTmp = 0;
					sglCusCompPTmp = 0;
				} else {
					sglCusCompPTmp = cusCompPTmp / componentQuantity;
				}

				//计算总的单车自定义配件价格
				if (fm.prpLcomponentFlag[i].value = '0') {
					if (sglCusCompPTmp > sglCusCompP) {
						alert(i18n.verifyPrice.singleCustomPriceInquiry); //单个自定义配件价格超出核价权限,必须向外询价!
						return false;
					}
					SumSglCarCusCompP = SumSglCarCusCompP + cusCompPTmp; //单车自定义=每行自定义上报价格的累加
				}

				SumSglCarAllCompP = SumSglCarAllCompP + cusCompPTmp; //单车全部 = 每行上报价格的累加        
			}
		}

		if (SumSglCarCusCompP > sglCarCusCompP) {
			alert(i18n.verifyPrice.bikeCustomFittingPriceInquir); //单车自定义配件价格之和超出核价权限,必须向外询价!
			return false;
		}

		if (SumSglCarAllCompP > sglCarAllCompP) {
			alert(i18n.verifyPrice.bikeAllFittingPriceInquir); //单车全部配件价格之和超出核价权限,必须向外询价!
			return false;
		}

	}

	return true;
}


/**
 *@description 检查核价登记
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
	var i = element.length - 1;
	if (fm.verpOpinion.value == "01")
		fm.prpLverifyLossExtTitle[i].value = "同意報價";
	else if (fm.verpOpinion.value == "02")
		fm.prpLverifyLossExtTitle[i].value = "補充信息";
	else if (fm.verpOpinion.value == "03")
		fm.prpLverifyLossExtTitle[i].value = "價格異議發回";
	else if (fm.verpOpinion.value == "04")
		fm.prpLverifyLossExtTitle[i].value = "價格已修正";
	else if (fm.verpOpinion.value == "05")
		fm.prpLverifyLossExtTitle[i].value = "向外詢價";
	else if (fm.verpOpinion.value == "99")
		fm.prpLverifyLossExtTitle[i].value = "其他";
}

/**
 *@description 根据按钮状态保存报案数据
 *@param       this
 *@param       保存状态
 *@return      通过返回true,否则返回false
 */

function saveForm(field, saveType) {
	fm.buttonSaveType.value = saveType;
	var element = document.getElementsByName('prpLverifyLossExtTitle');
	var i = element.length - 1;
	if (fm.prpLverifyLossExtTitle[i].value == "") {
		alert(i18n.verifyPrice.pleaseEnterPrice); //请输入核价意见！
		return false;
	}

	//得到核价意见  
	var verpOpinion = trim(fm.verpOpinion.value);

	//提交校验
	if (saveType == "4") {
		//对外核价
		if (fm.nodeType.value == "verpo") {
			if (verpOpinion == "04" || verpOpinion == "05") { //价格已修正、向外询价  	
				errorMessage(i18n.verifyPrice.handleForeignPriceTask) //处理对外核价任务时核价意见不允许选择“价格已修正”或“向外询价”!
				return false;
			}
		} else {
			if (verpOpinion == "05") { //向外询价  
				if (isEmptyField(fm.nextHandleDept)) {
					errorMessage(i18n.verifyPrice.nuclearPriceOpinionChoose) //核价意见为“向外询价”时,必须选择向外询价机构!
					return false;
				}
			}

			//判断是否可以提交
			if (verpOpinion != "01" && verpOpinion != "04" && verpOpinion != "05") {
				errorMessage(i18n.verifyPrice.nuclearPriceOpinionEnquiry) //核价意见为“同意定损”、“价格已修正”、“向外询价”时才可以提交该案件!
				return false;
			}

			//提交核损时，校验是否有超核价权限，超权限不允许提交核损，必须向外询价
			if (verpOpinion == "01" || verpOpinion == "04") {
				if (checkSubmitButton() == false) {
					return false;
				}
			}
		}
	}

	//退回案件校验
	if (saveType == "5") {
		//判断是否可以提交
		if (verpOpinion == "01" || verpOpinion == "04" || verpOpinion == "05") {
			errorMessage("核价意见为“补充信息”或“价格异义发回”时才可以退回该案件!")
			return false;
		}
	}

	//reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
	field.disabled = true;

	fm.submit();
}

//设置核价默认值

function initVeriPrice() {
	setComponetVeriPriceDefault();
}

/**
 *@description 设值页面的一些初始化信息
 *@param       无
 *@return      通过返回true,否则返回false
 */

function initSet() {

	//计算工时费合计
	sumRepairFee();

	//计算换件费合计
	sumComponentFee();

	return true;
}


function checkVerpCompPrice(serialNo) {

	var standerPriceHere = getStanderPrice(serialNo);
	var compVerpPrice = parseFloat(fm.prpLcomponentVerpCompPrice[serialNo].value);
	if (isNaN(compVerpPrice)) {
		compVerpPrice = 0;
	}

	if (standerPriceHere == 0) { //自定义配件和参考价为0时不比较
		return true;
	} else {
		if (standerPriceHere < compVerpPrice) {
			alert(i18n.verifyPrice.priceCannotStipulatPrice); //核定价格不能高於规定价格
			return false;
		}
	}
}

function getStanderPrice(serialNo) {

	var standerPrice = 0;
	var repairFactoryType = fm.prpLrepairFeeRepairFactoryCode.value;
	var prpLcomponentFlag = fm.prpLcomponentFlag[serialNo].value;

	if (prpLcomponentFlag != null && prpLcomponentFlag == "1") { //只有标配的才判断

		var sys4SPrice = parseFloat(fm.prpLcomponentSys4SPrice[serialNo].value);
		var sysMarketPrice = parseFloat(fm.prpLcomponentSysMarketPrice[serialNo].value);
		var native4SPrice = parseFloat(fm.prpLcomponentNative4SPrice[serialNo].value);
		var nativeMarketPrice = parseFloat(fm.prpLcomponentNativeMarketPrice[serialNo].value);

		if (isNaN(sys4SPrice)) sys4SPrice = 0;
		if (isNaN(sysMarketPrice)) sysMarketPrice = 0;
		if (isNaN(native4SPrice)) native4SPrice = 0;
		if (isNaN(nativeMarketPrice)) nativeMarketPrice = 0;

		if (repairFactoryType == "01") { //专修店（4S）
			if (native4SPrice == 0) { //本地专修价为0，取系统专修价
				standerPrice = sys4SPrice;
			} else {
				standerPrice = native4SPrice;
			}
		} else { //一、二类厂
			if (nativeMarketPrice == 0) {
				standerPrice = sysMarketPrice;
			} else {
				standerPrice = nativeMarketPrice;
			}
		}

	} else { //不是标配不比较，标准价设为0
		standerPrice = 0;
	}

	return standerPrice;
}




/**
 *@description 设置核价默认值
 *@param       无
 *@return      无
 */

function setComponetVeriPriceDefault() {
	var repairFactoryType = ""; //修理厂类型

	repairFactoryType = fm.prpLrepairFeeRepairFactoryCode.value;

	for (i = 1; i < fm.all("prpLcomponentSys4SPrice").length; i++) {
		getComponetVeriPriceDefault(fm.prpLcomponentSerialNo[i].value);
	}
}

//获取核价初始值

function getComponetVeriPriceDefault(serialNo) {
	var standCompPrice = 0;
	var standCompPrice = getStanderPrice(serialNo);
	var materialFee = parseFloat(fm.all("prpLcomponentMaterialFee")[serialNo].value);
	var verpCompPrice = parseFloat(fm.all("prpLcomponentVerpCompPrice")[serialNo].value);

	if (verpCompPrice != 0) {
		return;
	} else {
		if (standCompPrice == 0) { //自定义的核价默认值取上报价格
			fm.all("prpLcomponentVerpCompPrice")[serialNo].value = point(round(materialFee, 0), 0);
		} else { //标配时，上报价格和标准比较，谁小取谁
			if (materialFee > standCompPrice) {
				fm.all("prpLcomponentVerpCompPrice")[serialNo].value = point(round(standCompPrice, 0), 0);
			} else {
				fm.all("prpLcomponentVerpCompPrice")[serialNo].value = point(round(materialFee, 0), 0);
			}
		}
	}
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

	for (i = 1; i < fm.all("prpLrepairFeeManHour").length; i++) {
		prpLrepairFeeVeriSumLoss = parseFloat(fm.all("prpLrepairFeeVeriSumLoss")[i].value);
		prpLrepairFeeSumDefLoss = parseFloat(fm.all("prpLrepairFeeSumDefLoss")[i].value);

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
	fm.SumDefLoss1.value = point(round(sumRepairDefFee, 0), 0);;

	//核价工时费合计
	fm.SumVerifyLoss1.value = point(round(sumRepairVerifyFee, 0), 0);

	//计算总的定损金额
	sumPreDefLoss();

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

	sumManageFee = parseFloat(fm.all("prpLcarLossSumManager").value); //管理费 
	sumVeriManageFee = parseFloat(fm.all("prpLcarLossSumVeriManager").value); //总公司核定管理费
	sumTax = parseFloat(fm.all("prpLcarLossSumTax").value); //税金   
	sumTransFee = parseFloat(fm.all("prpLcarLossSumTransFee").value); //运费
	sumRestFee = parseFloat(fm.all("prpLcarLossSumRest").value); //残值合计

	if (isNaN(sumManageFee)) sumManageFee = 0;
	if (isNaN(sumVeriManageFee)) sumVeriManageFee = 0;
	if (isNaN(sumTax)) sumTax = 0;
	if (isNaN(sumTransFee)) sumTransFee = 0;
	if (isNaN(sumRestFee)) sumRestFee = 0;

	for (i = 1; i < fm.all("prpLcomponentQuantity").length; i++) {
		quantity = parseFloat(fm.all("prpLcomponentQuantity")[i].value);
		verpCompPrice = parseFloat(fm.all("prpLcomponentVerpCompPrice")[i].value);
		materialFee = parseFloat(fm.all("prpLcomponentMaterialFee")[i].value);

		if (isNaN(quantity)) quantity = 0;
		if (isNaN(verpCompPrice)) verpCompPrice = 0;
		if (isNaN(materialFee)) materialFee = 0;

		sumComponentMaterialFee = sumComponentMaterialFee + parseFloat(point(round(quantity * materialFee, 0), 0));
		sumComponentVerpCompPrice = sumComponentVerpCompPrice + parseFloat(point(round(quantity * verpCompPrice, 0), 0));

		fm.all("prpLcomponentSumDefLoss")[i].value = parseFloat(point(round(quantity * verpCompPrice, 0), 0));
	}

	//定损换件费合计 = 上报价格合计 + 管理费 + 税金 + 运费 – 残值合计(上报价格合计 = 每一个配件的上报价格 × 数量之和)
	sumComponentMaterialFee = sumComponentMaterialFee + sumManageFee + sumTax + sumTransFee - sumRestFee;

	if (sumVeriManageFee != 0)
		sumManageFee = sumVeriManageFee;

	//核价换件费合计 = 核价合计 + 管理费 + 税金 + 运费 – 残值合计(核价合计 = 每一个配件的核价 × 数量之和)
	sumComponentVerpCompPrice = sumComponentVerpCompPrice + sumManageFee + sumTax + sumTransFee - sumRestFee;

	//定损换件费合计
	fm.SumDefLoss2.value = point(round(sumComponentMaterialFee, 0), 0);
	//核价换件费合计
	fm.SumVerifyLoss2.value = point(round(sumComponentVerpCompPrice, 0), 0);

	//计算总的定损金额、总的核价金额
	sumPreDefLoss();

	return true;
}

/**
 *@description 计算总的定损金额、总的核价金额、偏差定损金额
 *@param       无
 *@return      通过返回true,否则返回false
 */

function sumPreDefLoss() {
	var sumRepairLoss = 0; //修理
	var sumComponentLoss = 0; //换件
	var sumRepairVerifyLoss = 0; //修理
	var sumComponentVerifyLoss = 0; //换件

	var sumPreDefLoss = 0;
	var sumVerifyLoss = 0;
	var firstDefLoss = 0; //初次定损金额
	var warpDefLoss = 0; //偏差定损金额

	sumRepairLoss = parseFloat(fm.SumDefLoss1.value);
	sumComponentLoss = parseFloat(fm.SumDefLoss2.value);

	sumRepairVerifyLoss = parseFloat(fm.SumVerifyLoss1.value);
	sumComponentVerifyLoss = parseFloat(fm.SumVerifyLoss2.value);

	if (isNaN(sumRepairLoss)) sumRepairLoss = 0;
	if (isNaN(sumRepairVerifyLoss)) sumRepairVerifyLoss = 0;
	if (isNaN(sumComponentLoss)) sumComponentLoss = 0;
	if (isNaN(sumComponentVerifyLoss)) sumComponentVerifyLoss = 0;

	sumPreDefLoss = parseFloat(sumRepairLoss) + parseFloat(sumComponentLoss);
	sumVerifyLoss = parseFloat(sumRepairVerifyLoss) + parseFloat(sumComponentVerifyLoss);

	//总的定损金额
	fm.prpLcarLossSumCertainLoss.value = point(round(sumPreDefLoss, 0), 0);

	//总的核价金额
	fm.prpLcarLossSumVerifyLoss.value = point(round(sumVerifyLoss, 0), 0);

	firstDefLoss = parseFloat(fm.prpLverifyLossFirstDefLoss.value);

	if (isNaN(firstDefLoss)) firstDefLoss = 0;

	warpDefLoss = firstDefLoss - sumVerifyLoss;

	//偏差定损金额
	fm.prpLverifyLossWarpDefLoss.value = point(round(warpDefLoss, 0), 0);
}


/**
 *@description 根据工时、工时单价计算定损金额、根据核价计算总核价金额
 *@param       无
 *@return      通过返回true,否则返回false
 */

function getSumDefLossVerify(field, ext) {
	var fieldname = field.name;
	var i = 0;
	var findex = 0;
	var manUnitPrice;
	var ManHour;
	var sumDefLoss;

	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == field) {
			findex = i;
			break;
		}
	}

	if (ext == 1) {
		ManHour = parseFloat(fm.all("prpLrepairFeeVeriManHour")[findex].value);
		manUnitPrice = parseFloat(fm.all("prpLrepairFeeVeriManUnitPrice")[findex].value)

		if (isNaN(ManHour)) ManHour = 0;
		if (isNaN(manUnitPrice)) manUnitPrice = 0;

		sumDefLoss = manUnitPrice * ManHour;

		fm.all("prpLrepairFeeVeriMaterialFee")[findex].value = point(round(sumDefLoss, 0), 0);
		fm.all("prpLrepairFeeVeriSumLoss")[findex].value = point(round(sumDefLoss, 0), 0);
		sumRepairFee();
	} else {
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
	var linkURL = "/claim/checkFinishQueryList.do?prpLcheckCheckNo=" + registNo + "&editType=SHOW&riskCode=" + fm.riskcode.value;
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
	var newWindow = window.open(linkURL, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
}


/**
 *@description 弹出关联页面
 *@param       无
 *@return      通过返回true,否则返回false
 */

function relatePolicy() {
	var riskCode = fm.riskcode.value;
	var policyNo = fm.PolicyNo.value;
	var vURL = '/prpall/' + riskCode + '/tbcbpg/UIPrPoEn' + riskCode + 'Show.jsp?BIZTYPE=POLICY&SHOWTYPE=SHOW&BizNo=' + policyNo + '&RiskCode=' + riskCode;
	window.open(vURL, '详细信息', 'width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 *@description 处理索赔资料清单
 *@param       businessNo
 */

function doCertifyDirect(businessNo, nodeType) {
	window.open("/claim/certifyBeforeEdit.do?RegistNo=" + businessNo + "&editType=CertifyDirect&nodeType=" + nodeType, "winName", "resizable=0,scrollbars=1,width=800,height=600");
}