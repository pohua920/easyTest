//全局变量用来控制dwr的进度
var deepth11 = -1;
var deepth22 = -1;
var deepth2 = -1;
var deepth1 = -1;
/**
 *计算变更险种时的人员赔付金额
 */

function getThisField(Field) {
	var fieldname = field.name;
	var findex = 0;
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == field) {
			findex = i;
			break;
		}
	}
}

function changeIndemnityDuty() {
	var indemnityDuty = ""; //设置的值
	var i = 0; //循环使用
	switch (fm.indemnityDuty.value) {
	case "0": //全责
		indemnityDuty = "100";
		break;
	case "1": //主责
		indemnityDuty = "70";
		break;
	case "2": //同责
		indemnityDuty = "50";
		break;
	case "3": //次责
		indemnityDuty = "30";
		break;
	case "4": //无责
		indemnityDuty = "0.0";
		break;
	case "9": //其它
		if (fm.prpLcompensateIndemnityDutyRate.value != "")
			indemnityDuty = parseInt(fm.prpLcompensateIndemnityDutyRate.value);
		else
			indemnityDuty = "0.0";
		break;
	}
	fm.prpLcompensateIndemnityDutyRate.value = indemnityDuty;
	if (fm.all("prpLpersonLossSumDefPay").length > 1) {
		deepth22 = 1;
		deepth2 = fm.all("prpLpersonLossSumDefPay").length;
	}
	if (fm.all("prpLlossDtoIndemnityDutyRate").length > 1) {
		deepth11 = 1;
		deepth1 = fm.all("prpLlossDtoIndemnityDutyRate").length;
	}
	for (var index = 1; index < fm.all("prpLlossDtoIndemnityDutyRate").length; index++) {
		fm.all("prpLlossDtoIndemnityDutyRate")[index].value = indemnityDuty;
		calRealpay(fm.all("prpLlossDtoIndemnityDutyRate")[index]);
	}
	for (var index1 = 1; index1 < fm.all("prpLpersonLossIndemnityDutyRate").length; index1++) {
		fm.all("prpLpersonLossIndemnityDutyRate")[index1].value = indemnityDuty;

	}
	for (var index2 = 1; index2 < fm.all("prpLpersonLossSumDefPay").length; index2++) {
		if (!(isNaN(fm.all("prpLpersonLossSumDefPay")[index2].value))) {
			calPersonRealpay(fm.all("prpLpersonLossSumDefPay")[index2]);
		}
	}
}

/**
 *设置免赔条件
 */

function initDeductCond() {
	if (fm.all("prpLpersonLossSumDefPay").length > 1) {
		deepth22 = 1;
		deepth2 = fm.all("prpLpersonLossSumDefPay").length;
	}
	if (fm.all("prpLlossDtoIndemnityDutyRate").length > 1) {
		deepth11 = 1;
		deepth1 = fm.all("prpLlossDtoIndemnityDutyRate").length;
	}
	//将物损进行调整
	for (var index = 1; index < fm.all("prpLlossDtoIndemnityDutyRate").length; index++) {
		calRealpay(fm.all("prpLlossDtoIndemnityDutyRate")[index]);
	}
	//将人伤进行调整 

	for (var index2 = 1; index2 < fm.all("prpLpersonLossSumDefPay").length; index2++) {
		if (!(isNaN(fm.all("prpLpersonLossSumDefPay")[index2].value))) {
			calPersonRealpay(fm.all("prpLpersonLossSumDefPay")[index2]);
		}
	}
}

function calRealpayAll() {
	//将物损进行调整
	for (var index = 1; index < fm.all("prpLlossDtoIndemnityDutyRate").length; index++) {
		calRealpay(fm.all("prpLlossDtoIndemnityDutyRate")[index]);
	}
	//将人伤进行调整 
	for (var index1 = 1; index1 < fm.all("prpLpersonLossIndemnityDutyRate").length; index1++) {
		calPersonRealpay(fm.all("prpLpersonLossIndemnityDutyRate")[index1]);
	}
}


//chenjie 2013-04-27 客制化需求改动点 //车物损理赔金计算入口 

function calRealpay(field) {
	if (field.value != "") {
		var fieldname = field.name;
		var findex;
		for (var i = 1; i < fm.all(fieldname).length; i++) {
			if (fm.all(fieldname)[i] == field) {
				findex = i;
				break;
			}
		}
		if (findex != 'undefined')
			if (fm.prpLlossDtoKindCode[findex].value != 'C5' && fm.prpLlossDtoKindCode[findex].value != 'T' && fm.prpLlossDtoKindCode[findex].value != 'C7') {
				var count = getElementCount("deductName");
				var deductCondArray = new Array();
				for (var j = 0; j < count; j++) {

					//if(fm.deductCondition[j].checked == true){
					//  if(fm.all("Times")[j].value ==0 || fm.all("Times")[j].value ==''){
					//  fm.all("Times")[j].value = 1;
					//}
					//}else{
					//fm.all("Times")[j].value = 0;
					// }
					//如果免赔被选中的话，Times=1，没有被选中的话 Times=0；
					//组织deductCondArray的数组，作为後面进行判断逻辑的基础数据
					//add by chenjie 对象已换成PrpLdeductCond
					deductCondArray[j] = ({
						id: {
							deductCondCode: fm.all("deductConditionTemp")[j].value
						},
						deductCondName: fm.all("deductName")[j].value,
						times: parseInt(fm.all("Times")[j].value)
					});
				}

				//将组织好的数组条件，传入到 dwrInvokeDataAction中进行整理prpLdeductCondList变量
				//为什么要分开呢，现在这个地方总出现0的情况。。。
				// add by liping 080805 dwr和js顺序执行控制
				DWREngine.setAsync(false);
				dwrInvokeDataAction.getDeductCondList(deductCondArray);
				var licenseNo = "";
				var licenseNoA = "";
				var kindCode;
				var SumDefPay; //核定赔偿金额
				var purchasePrice; //新车购置价（出险时）
				var factValue; //实际价值（出险时）
				var SumLoss; //核损金额
				var SumRest; //残值
				var CompelPay; //交强险赔款
				var ClaimRate; //承保比例
				var DutyDeductibleRate; //事故责任免赔率
				var DeductibleRate; //免赔率
				var DriverDeductibleRate; //驾驶员免赔率
				var mainKindCode; //主险代码
				var DeductibleRateOfMainKind; //主险的绝对免赔率
				var Deductible; //可选免赔额
				var Deductibletemp; //免赔
				var DutyRate; //责任比例
				var ArrangeRate; //协商赔偿比例
				var Realpay; //赔偿金额
				var temp;
				var unitPrice;
				var Amount;
				var outputObject;
				var inputObject = field;
				var prpLlossDtoIsLossAll = "";
				var policyno = fm.prpLcompensatePolicyNo.value;
				var exceptDeductiblePay;
				var exceptDeductibleRate;
				var riskCode = fm.prpLcompensateRiskCode.value;
				var indemnityDuty = fm.indemnityDuty[1].value;
				if (indemnityDuty == "9") {
					DutyRate = parseFloat(fm.all("prpLcompensateIndemnityDutyRate").value);
					if (parseFloat(DutyRate) > parseFloat("100")) {
						indemnityDuty = "0";
					} else if (parseFloat(DutyRate) > parseFloat("50")) {
						indemnityDuty = "1";
					} else if (parseFloat(DutyRate) > parseFloat("0")) {
						indemnityDuty = "3";
					}

				}
				var registNo = fm.prpLregistExtRegistNo.value;
				var flagn = '0';
				var kindCodeTemp;
				var getAmountFlag = false;

				//出险日期
				var damageStartDate = fm.DamageStartDate.value;
				//出险小时
				var damageStartHour = fm.prpLcompensateDamageStartDate.value;

				//标的车牌照
				licenseNoA = fm.prpLcompensateLicenseNo.value;
				//车辆牌照
				licenseNo = trim(fm.prpLlossDtoLicenseNo[findex].value);
				//标的车新车购置价（出险时）
				purchasePrice = fm.purchasePrice.value;
				//标的车的实际价值（出险时）
				factValue = fm.factValue.value;
				//实际损失
				SumLoss = fm.all("prpLlossDtoSumLoss")[findex].value;
				//残值
				SumRest = fm.all("prpLlossDtoSumRest")[findex].value;
				//交强险赔款
				CompelPay = fm.all("prpLlossDtoCompelPay")[findex].value;
				//核定赔偿
				SumDefPay = fm.all("prpLlossDtoSumDefPay")[findex].value;
				//承保比例
				ClaimRate = fm.all("prpLlossDtoClaimRate")[findex].value;
				//事故责任比例
				DutyRate = fm.all("prpLcompensateIndemnityDutyRate")[findex].value;
				//协商赔偿比例
				ArrangeRate = fm.all("prpLlossDtoArrangeRate")[findex].value;
				//事故责任免赔率
				DutyDeductibleRate = fm.all("prpLlossDtoDutyDeductibleRate")[findex].value;
				//绝对免赔率
				DeductibleRate = fm.all("prpLlossDtoDeductibleRate")[findex].value;
				unitPrice = fm.all("prpLlossDtoUnitPrice")[findex].value;
				kindCode = fm.all("prpLlossDtoKindCode")[findex].value;
				kindCodeTemp = kindCode;
				Deductible = fm.prpLDeductible.value;
				if (kindCode == 'A' || kindCode == 'G' || ((kindCode == 'Y' || kindCode == 'S') && licenseNoA == licenseNo) || kindCode == 'T' || kindCode == 'E') {
					prpLlossDtoIsLossAll = fm.prpLlossDtoIsLossAll.value;
				}

				if (kindCode == 'Y' || kindCode == 'S') //教练车和附加出境都是和车损三者互斥的险别
				{
					if (licenseNoA == licenseNo) {
						kindCode = "A";
						isLicenseNo = 1;
					} else
						kindCode = "B";
				}
				if (kindCode == 'X1' || (riskCode == '0502' && (kindCodeTemp == 'K1' || kindCodeTemp == 'K2'))) //发动机损失险和K1、K2都是走A险的
				{
					kindCode = "A";
				}
				for (var index = 0; index < fm.prpLlossDtoKindCodeShow.length; index++) {
					if (fm.prpLlossDtoKindCodeShow[index].value == kindCode) {
						getAmountFlag = true;
						Amount = fm.all("kindAmount")[index].value;
						flagn = fm.flag[index].value;
						break;
					}
				}
				if (getAmountFlag == false) {
					Amount = fm.kindAmount.value;
					flagn = fm.flag.value;
				}
				if (kindCode != kindCodeTemp) {
					for (var index = 0; index < fm.prpLlossDtoKindCodeShow.length; index++) {
						if (fm.prpLlossDtoKindCodeShow[index].value == kindCodeTemp) {
							flagn = fm.flag[index].value;
						}
						if (fm.prpLlossDtoKindCodeShow[index].value == kindCodeTemp && (kindCode == 'B' || (parseInt(fm.all("kindAmount")[index].value) == 0))) {
							fm.all("kindAmount")[index].value = Amount;
							break;
						}
					}
				}
				if (kindCode == 'AB') { //提车保险赔偿主车为车损险，赔偿三者车或者财时为三者险
					if (licenseNoA == licenseNo) {
						kindCode = "A";
					} else {
						kindCode = "B";
					}
				}
				if (purchasePrice == '' || isNaN(purchasePrice)) {
					purchasePrice = '0';
				}
				if (factValue == 0 && kindCode == 'A') {
					alert(i18n.compensate.vehicleNotActualValue); //车辆实际价值未带出，折旧率未取到！
				}
				if (kindCodeTemp == 'X1') {
					kindCode = kindCodeTemp;
				}
				if (SumDefPay == '' || isNaN(SumDefPay))
					SumDefPay = '0';
				if (SumRest == '' || isNaN(SumRest))
					SumRest = '0';
				if (CompelPay == '' || isNaN(CompelPay))
					CompelPay = '0';
				if (ClaimRate == '' || isNaN(ClaimRate))
					ClaimRate = '100';
				if (DutyRate == '' || isNaN(DutyRate))
					DutyRate = '0';
				if (ArrangeRate == '' || isNaN(ArrangeRate))
					ArrangeRate = '100';
				if (DutyDeductibleRate == '' || isNaN(DutyDeductibleRate))
					DutyDeductibleRate = '0';
				if (DeductibleRate == '' || isNaN(DeductibleRate))
					DeductibleRate = '0'
				if (isNaN(unitPrice) || unitPrice == '')
					unitPrice = '0';
				if (isNaN(Amount) || Amount == '')
					Amount = '0';
				if (isNaN(Deductible) || Deductible == '')
					Deductible = '0';
				if (isNaN(prpLlossDtoIsLossAll))
					prpLlossDtoIsLossAll = "";

				if (policyno != "") {
					var inputArgs = {
						SumLoss: SumLoss,
						SumRest: SumRest,
						SumDefPay: SumDefPay,
						ClaimRate: ClaimRate,
						DutyRate: DutyRate,
						ArrangeRate: ArrangeRate,
						DutyDeductibleRate1: DutyDeductibleRate,
						DeductibleRate: DeductibleRate,
						unitPrice: unitPrice,
						Amount: Amount,
						riskCode1: riskCode,
						kindCode: kindCode,
						indemnityDuty0: indemnityDuty,
						registNo: registNo,
						flagn: flagn,
						prpLlossDtoIsLossAll: prpLlossDtoIsLossAll,
						policyno: policyno,
						CompelPay: CompelPay,
						purchasePrice: purchasePrice,
						factValue: factValue,
						Deductible: Deductible,
						damageStartDate: damageStartDate,
						damageStartHour: damageStartHour
					};
					var param = DWRUtil.getValues(inputArgs);
					dwrInvokeData("getPrpLlossDto", param, "rollbackPrpLlossDto", inputObject, outputObject);
				}
				DWREngine.setAsync(true);
			} //end if prpLlossDtoKindCode!='C5'
	} //end if field.value!=""
}


function rollbackPrpLlossDto(inputObject, outputObject, returnObject) {
	var prpLlossDto = returnObject; //对象迁移後类型为PrpLloss
	var fieldname = inputObject.name;
	var findex;
	for (var i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == inputObject) {
			findex = i;
			break;
		}
	}
	fm.prpLlossDtoClaimRate[findex].value = prpLlossDto.claimRate;
	fm.prpLlossDtoIndemnityDutyRate[findex].value = prpLlossDto.indemnityDutyRate;
	fm.prpLlossDtoDutyDeductibleRate[findex].value = prpLlossDto.dutyDeductibleRate;
	fm.prpLlossDtoDeductibleRate[findex].value = prpLlossDto.deductiblerate;
	fm.prpLlossDtoExceptDeductiblePay[findex].value = prpLlossDto.exceptDeductiblePay;
	fm.prpLlossDtoExceptDeductibleRate[findex].value = prpLlossDto.exceptDeductibleRate;
	fm.prpLlossDtoFlag[findex].value = prpLlossDto.flag;
	fm.prpLlossDtoSumRealPay[findex].value = point(round(prpLlossDto.sumRealPay, 0), 0);
	if (fm.prpLchargeKindCode) {
		for (var i = 1; i < fm.prpLchargeKindCode.length; i++) {
			if ((fm.prpLchargeKindCode[i].value == "A" || fm.prpLchargeKindCode[i].value == "B" || fm.prpLchargeKindCode[i].value == "Z" || fm.prpLchargeKindCode[i].value == "X1" || fm.prpLchargeKindCode[i].value == "K1" || fm.prpLchargeKindCode[i].value == "K2" || fm.prpLchargeKindCode[i].value == "Y" || fm.prpLchargeKindCode[i].value == "S" || fm.prpLchargeKindCode[i].value == "E")) {
				calChargeAmount(fm.prpLchargeChargeReport[i]);
			}
		}
	}
	for (var index = 0; index < fm.prpLlossDtoKindCodeShow.length; index++) {
		if (fm.prpLlossDtoKindCodeShow[index].value == fm.all("prpLlossDtoKindCode")[findex].value) {
			if (fm.flag[index].value == '1') {
				initExceptDeductible();
				break;
			}
		}
	}
	initEvryTypeRealPay();
	if (deepth1 != -1 || deepth2 != -1) {
		deepth11++;
		if (deepth11 == deepth1 && deepth22 == deepth2) {
			checkAmountn();
			showMessage5();
			deepth11 = -1;
			deepth1 = -1;
			deepth22 = -1;
			deepth2 = -1;
		}
	}

	calFund();
	var count = getElementCount("deductName");
	for (var j = 0; j < count; j++) {
		fm.all("deductCondition")[j].disabled = false;
	}
	undisablebutton();
}

function calPersonRealpay(Field) {
	if (Field.value != "") {
		var count = getElementCount("deductName");
		var deductCondArray = new Array();
		for (var j = 0; j < count; j++) {

			deductCondArray[j] = ({
				id: {
					deductCondCode: fm.all("deductConditionTemp")[j].value
				},
				deductCondName: fm.all("deductName")[j].value,
				times: parseInt(fm.all("Times")[j].value)
			});
		}
		DWREngine.setAsync(false);
		dwrInvokeDataAction.getDeductCondList(deductCondArray);
		DWREngine.setAsync(true);
		var fieldname = Field.name; //域名
		var i = 0;
		var findex = 0; //定位序号
		var SumLoss = 0; //核损金额
		var ClaimRate; //赔偿比例
		var CompelPay; //强制保险赔款
		var DutyDeductibleRate; //事故责任免赔率
		var DriverDeductibleRate; //驾驶员免赔率
		var DeductibleRate; //绝对免赔率
		var MainKindDeductibleRate; //所在主险的绝对免赔率
		var DutyRate; //责任比例
		var ArrangeRate; //协商赔偿比例
		var Realpay; //赔付金额
		var temp;
		var Amount;
		var outputObject;
		var inputObject = Field;
		var SumDefPay = 0;
		var indemnityDuty = fm.indemnityDuty[1].value;
		var kindCode;
		var riskCode = fm.prpLcompensateRiskCode.value;
		var registNo = fm.prpLregistExtRegistNo.value;
		var flagn = "0";

		if (indemnityDuty == "9") {
			DutyRate = parseFloat(fm.all("prpLcompensateIndemnityDutyRate").value);
			if (parseFloat(DutyRate) > parseFloat("100")) {
				indemnityDuty = "0";
			} else if (parseFloat(DutyRate) > parseFloat("50")) {
				indemnityDuty = "1";
			} else if (parseFloat(DutyRate) > parseFloat("0")) {
				indemnityDuty = "3";
			}

		}
		for (i = 1; i < fm.all(fieldname).length; i++) {
			if (fm.all(fieldname)[i] == Field) {
				findex = i;
				break;
			}
		}
		//============给变量赋值
		if (fm.all("prpLpersonLossSumLoss")[findex] == undefined) {} else {
			SumLoss = parseFloat(fm.all("prpLpersonLossSumLoss")[findex].value);
			SumDefPay = parseFloat(fm.all("prpLpersonLossSumDefPay")[findex].value);
			ClaimRate = parseFloat(fm.all("prpLpersonLossClaimRate")[findex].value);
			CompelPay = parseFloat(fm.all("prpLpersonLossCompelPay")[findex].value);
		}
		SumRest = 0;
		DeductibleRate = 0;
		DutyRate = 0;
		ArrangeRate = 0;
		Realpay = 0;
		temp = 0;
		var findex1 = 0;
		for (i = 1; i < fm.all("prpLpersonLossIndemnityDutyRate").length; i++) {
			if (fm.all("personLossSerialNo")[findex] == undefined) {
				findex1 = i;
				break;
			} else {
				if (fm.all("prpLpersonLossSerialNo")[i].value == fm.all("personLossSerialNo")[findex].value) {
					findex1 = i;
					break;
				}
			}
		}
		kindCode = fm.all("prpLpersonLossKindCode")[findex1].value;
		DutyRate = parseFloat(fm.all("prpLcompensateIndemnityDutyRate").value);
		ArrangeRate = parseFloat(fm.all("prpLpersonLossArrangeRate")[findex1].value);
		DeductibleRate = parseFloat(fm.all("prpLpersonLossDeductibleRate")[findex1].value);
		DutyDeductibleRate = parseFloat(fm.all("prpLpersonLossDutyDeductibleRate")[findex1].value);
		MainKindDeductibleRate = parseFloat(fm.all("prpLpersonLossMainKindDeductibleRate")[findex1].value);
		var kindCodeTemp = kindCode;
		if (kindCode == 'Y' || kindCode == 'S') //附加出境保险和教练车是有人伤的
		{
			kindCode = "B";
		}
		if (isNaN(SumLoss)) SumLoss = 0; //
		if (isNaN(SumDefPay)) SumDefPay = 0; //LYM 20060620	  
		if (isNaN(CompelPay))
			CompelPay = 0;
		if (isNaN(DeductibleRate))
			DeductibleRate = 0;
		if (isNaN(DutyDeductibleRate))
			DutyDeductibleRate = 0;
		if (isNaN(MainKindDeductibleRate))
			MainKindDeductibleRate = 0;
		else
			MainKindDeductibleRate = MainKindDeductibleRate / 100;
		if (isNaN(DutyRate))
			DutyRate = 0;
		if (isNaN(ArrangeRate))
			ArrangeRate = '100';
		if (isNaN(ClaimRate))
			ClaimRate = '100';
		var size = parseInt(fm.size.value);
		for (var index = 0; index < size; index++) {
			if (fm.prpLlossDtoKindCodeShow[index].value == kindCode) {
				flagn = fm.flag[index].value;
				Amount = fm.kindAmount[index].value;
				break;
			}
		}
		if (kindCode != kindCodeTemp) {
			for (var index = 0; index < fm.prpLlossDtoKindCodeShow.length; index++) {
				if (fm.prpLlossDtoKindCodeShow[index].value == kindCodeTemp) {
					flagn = fm.flag[index].value;
					break;
				}
			}
		}
		var inputArgs = {
			SumLoss: SumLoss,
			SumRest: SumRest,
			SumDefPay: SumDefPay,
			DutyRate: DutyRate,
			ArrangeRate: ArrangeRate,
			DutyDeductibleRate: DutyDeductibleRate,
			DeductibleRate: DeductibleRate,
			Amount: Amount,
			riskCode1: riskCode,
			kindCode: kindCode,
			indemnityDuty1: indemnityDuty,
			registNo: registNo,
			flagn: flagn,
			CompelPay: CompelPay
		};
		var param = DWRUtil.getValues(inputArgs);
		DWREngine.setAsync(false);
		dwrInvokeData("getPrpLpersonLossDto", param, "rollbackPrpLpersonLossDto", inputObject, outputObject);
		DWREngine.setAsync(true);
	}
}

function rollbackPrpLpersonLossDto(inputObject, outputObject, returnObject) {
	var prpLpersonLossDto = returnObject;
	var fieldname = inputObject.name;
	var findex;
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == inputObject) {
			findex = i;
			break;
		}
	}
	var findex1 = 0;
	for (i = 1; i < fm.all("prpLpersonLossIndemnityDutyRate").length; i++) {
		if (fm.all("personLossSerialNo")[findex] == undefined) {
			findex1 = i;
			break;
		} else {
			if (fm.all("prpLpersonLossSerialNo")[i].value == fm.all("personLossSerialNo")[findex].value) {
				findex1 = i;
				break;
			}
		}
	}

	fm.prpLpersonLossDutyDeductibleRate[findex1].value = prpLpersonLossDto.dutyDeductibleRate;
	fm.prpLpersonLossDeductibleRate[findex1].value = prpLpersonLossDto.deductiblerate;

	if (fm.prpLpersonLossSumRealPay[findex] == undefined) {} else {
		fm.prpLpersonLossSumRealPay[findex].value = point(round(prpLpersonLossDto.sumRealPay, 0), 0);
		fm.prpLpersonLossFlag[findex].value = prpLpersonLossDto.flag;
	}
	for (var index = 0; index < fm.prpLlossDtoKindCodeShow.length; index++) {
		if (fm.prpLlossDtoKindCodeShow[index].value == fm.all("prpLpersonLossKindCode")[findex1].value) {
			if (fm.flag[index].value == '1') {
				fm.prpLpersonLossExceptDeductibleRate1[findex1].value = prpLpersonLossDto.exceptDeductibleRate;
				fm.prpLpersonLossExceptDeductiblePay[findex].value = prpLpersonLossDto.exceptDeductiblePay;
				fm.prpLpersonLossExceptDeductibleRate[findex].value = prpLpersonLossDto.exceptDeductibleRate;
				initExceptDeductible();

				break;
			}
		}
	}
	calSumRealpay(inputObject);
	initEvryTypeRealPay();
	if (deepth1 != -1 || deepth2 != -1) {
		deepth22++;
		if (deepth11 == deepth1 && deepth22 == deepth2) {
			checkAmountn();
			showMessage5();
			deepth11 = -1;
			deepth1 = -1;
			deepth22 = -1;
			deepth2 = -1;
		}
	}
	//计算赔付人员中的赔付合计     
	calFund();
	undisablebutton();
}

function displayTimes(obj) {
	var timesArray = document.getElementsByName("Times");
	var deductConditionArray = document.getElementsByName("deductCondition");
	var findex;
	for (var i = 0; i < fm.all(obj.name).length; i++) {
		if (fm.all(obj.name)[i] == obj) {
			findex = i;
		} else {
			//如果当前行是3个责任免赔之一，需要使另外两个为0的。
			var othervalue = fm.all(obj.name)[i].value;
			if ((obj.value == '110' || obj.value == '120' || obj.value == '121') && (othervalue == '110' || othervalue == '120' || othervalue == '121')) {
				timesArray[i].value = "0";
				fm.all(obj.name)[i].checked = false;
			}
		}
	}
	if (obj.checked == true) {
		timesArray[findex].value = "1";
	} else {
		timesArray[findex].value = "0";
	}
	var timeflag = document.getElementsByName("timesFlag");
	if (obj.checked == true && timeflag[findex].value == "1") {
		timesArray[findex].style.display = "";
	} else {
		timesArray[findex].style.display = "none";
	}
}


//车险理算计算费用

function calChargeAmount(field) {

	disablebutton(); //add by liping 2008-04-24   
	var outputObject;
	var inputObject = field;
	var serialNo = 0;
	var exceptDeductiblePay = 0;
	var exceptDeductibleRate = 0;
	for (var i = 0; i < fm.all(field.name).length; i++) {
		if (fm.all(field.name)[i] == field) {
			serialNo = i;
			break;
		}
	}
	var chargeReport = fm.all('prpLchargeChargeReport')[serialNo].value; //费用金额
	var chargeAmount = 0.0;
	var kindCode = fm.all('prpLchargeKindCode')[serialNo].value; //险别
	var chargeCode = fm.all('prpLchargeChargeCode')[serialNo].value; //费用类型
	if ((kindCode == 'A' || kindCode == 'B' || kindCode == 'Z' || kindCode == 'X1' || kindCode == 'K1' || kindCode == 'K2' || kindCode == 'Y' || kindCode == 'S' || kindCode == 'E') && chargeCode == '03') {

		var claimRate;
		var deductibleRate;
		var dutyDeductibleRate;
		var indemnityDutyRate;
		for (var index1 = 0; index1 < fm.prpLlossDtoSerialNo.length; index1++) {
			if (kindCode == fm.prpLlossDtoKindCode[index1].value) {
				if (kindCode == 'B' && fm.licenseNo[index1].value == licenseNo) {
					claimRate = fm.prpLlossDtoClaimRate[index1].value;
					dutyDeductibleRate = fm.prpLlossDtoDutyDeductibleRate[index1].value;
					indemnityDutyRate = fm.prpLlossDtoIndemnityDutyRate[index1].value;
					deductibleRate = fm.prpLlossDtoDeductibleRate[index1].value;
					exceptDeductibleRate = parseInt(fm.prpLlossDtoExceptDeductibleRate[index1].value);
					break;
				} else {
					claimRate = fm.prpLlossDtoClaimRate[index1].value;
					dutyDeductibleRate = fm.prpLlossDtoDutyDeductibleRate[index1].value;
					indemnityDutyRate = fm.prpLlossDtoIndemnityDutyRate[index1].value;
					deductibleRate = fm.prpLlossDtoDeductibleRate[index1].value;
					exceptDeductibleRate = parseInt(fm.prpLlossDtoExceptDeductibleRate[index1].value);
					break;
				}
			}
		}
		if (kindCode == 'Z' || kindCode == 'X1' || kindCode == 'K1' || kindCode == 'K2' || kindCode == 'E')
			indemnityDutyRate = 100;
		chargeAmount = chargeReport * claimRate / 100 * indemnityDutyRate / 100 * (1 - (dutyDeductibleRate / 100 + deductibleRate / 100));
		if (kindCode == 'E')
			chargeAmount = chargeReport * (1 - deductibleRate / 100);
		fm.all("prpLchargeSumRealPay")[serialNo].value = point(round(chargeAmount, 0), 0);
		fm.all("prpLchargeChargeAmount")[serialNo].value = point(round(chargeAmount, 0), 0);
		if (!isNaN(parseInt(exceptDeductibleRate))) //重新计算不计免赔
		{
			exceptDeductiblePay = chargeReport * claimRate / 100 * indemnityDutyRate / 100 * exceptDeductibleRate / 100;
			fm.all("prpLchargeExceptDeductiblePay")[serialNo].value = point(round(exceptDeductiblePay, 0), 0);
			fm.all("prpLchargeExceptDeductibleRate")[serialNo].value = exceptDeductibleRate;
			initExceptDeductible();
		}
		initEvryTypeRealPay();
	} else {
		for (var index1 = 0; index1 < fm.prpLlossDtoSerialNo.length; index1++) {
			if (kindCode == fm.prpLlossDtoKindCode[index1].value) {
				exceptDeductibleRate = parseInt(fm.prpLlossDtoExceptDeductibleRate[index1].value);
			}

		}
		if (!isNaN(parseInt(exceptDeductibleRate))) //重新计算不计免赔
		{
			exceptDeductiblePay = chargeReport * claimRate / 100 * indemnityDutyRate / 100 * exceptDeductibleRate / 100;
			fm.all("prpLchargeExceptDeductiblePay")[serialNo].value = point(round(exceptDeductiblePay, 0), 0);
			fm.all("prpLchargeExceptDeductibleRate")[serialNo].value = exceptDeductibleRate;
			initExceptDeductible();
		}
		fm.all("prpLchargeChargeAmount")[serialNo].value = point(round(chargeReport, 0), 0);
		fm.all("prpLchargeSumRealPay")[serialNo].value = 0.00;
	}
	calFund();
	calLoss();
	undisablebutton(); //add by liping 2008-04-24                                         
}

function checkExceptDeductible(kind, field) {
	var policyno = fm.prpLcompensatePolicyNo.value;
	if (policyno != null || policyno != "") {
		var inputArgs = {
			kind: kind,
			policyno: policyno
		};
		var inputObject = field;
		var outputObject;
		var param = DWRUtil.getValues(inputArgs);
		DWREngine.setAsync(false);
		dwrInvokeData("checkExceptDeductible", param, "rollbackExceptDeductible1", inputObject, outputObject);
		DWREngine.setAsync(true);
	}
}

function rollbackExceptDeductible1(inputObject, outputObject, returnObject) {
	var prpCitemKindDto = returnObject;
	if (prpCitemKindDto.kindCode != "") {

		insertRow('exceptLoss1');
		var index = fm.exceptDeductibleKindCode.length;
		fm.exceptDeductibleKindCode[index - 1].value = prpCitemKindDto.kindCode;
		fm.exceptDeductibleKindName[index - 1].value = prpCitemKindDto.kindName;
		fm.exceptDeductiblePay[index - 1].value = 0;
	}
	undisablebutton();
}

function getAllDeductibleRate(field) {
	var count = getElementCount("deductName");
	var deductCondArray = new Array();
	//count 为当前获取的所有的免赔条件的数目
	for (var j = 0; j < count; j++) {

		if (fm.deductCondition[j].checked == true) {
			if (fm.all("Times")[j].value == 0 || fm.all("Times")[j].value == '') {
				fm.all("Times")[j].value = 1;
			}
		} else {
			fm.all("Times")[j].value = 0;
		}
		//如果免赔被选中的话，Times=1，没有被选中的话 Times=0；
		//组织deductCondArray的数组，作为後面进行判断逻辑的基础数据
		if (fm.all("prpLlossDtoIndemnityDutyRate").length == undefined && fm.all("prpLpersonLossIndemnityDutyRate").length == undefined) {} else {
			fm.all("deductCondition")[j].disabled = true;
		}
		deductCondArray[j] = ({
			id: {
				deductCondCode: fm.all("deductConditionTemp")[j].value
			},
			deductCondName: fm.all("deductName")[j].value,
			times: parseInt(fm.all("Times")[j].value)
		});
	}

	//将组织好的数组条件，传入到 dwrInvokeDataAction中进行整理prpLdeductCondList变量
	DWREngine.setAsync(false);
	dwrInvokeDataAction.getDeductCondList(deductCondArray);
	DWREngine.setAsync(true);
	var findex;
	var fieldname = field.name;
	for (var i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == field) {
			findex = i;
			break;
		}
	}
	var kindCode = fm.prpLpersonLossKindCode[findex].value;
	var indemnityDuty = fm.indemnityDuty.value;
	var riskCode = fm.prpLcompensateRiskCode.value;
	var registNo = fm.prpLregistExtRegistNo.value;
	var kindCodeTemp = kindCode;
	var Amount;
	var flag;
	if (kindCode == 'Y' || kindCode == 'S') //附加出境保险和教练车是有人伤的
	{
		kindCode = "B";
	}
	for (var index = 0; index < fm.prpLlossDtoKindCodeShow.length; index++) {
		if (fm.prpLlossDtoKindCodeShow[index].value == kindCode) {
			Amount = fm.all("kindAmount")[index].value;
			flag = fm.flag[index].value;
			break;
		}
	}
	if (kindCode != kindCodeTemp) {
		for (var index = 0; index < fm.prpLlossDtoKindCodeShow.length; index++) {
			if (fm.prpLlossDtoKindCodeShow[index].value == kindCodeTemp && ((parseInt(fm.all("kindAmount")[index].value) > Amount) || (parseInt(fm.all("kindAmount")[index].value) == 0))) {
				fm.all("kindAmount")[index].value = Amount;
				break;
			}
		}
	}
	if (kindCode == 'R') {
		fm.prpLpersonLossDutyDeductibleRate[findex].value = 0;
		fm.prpLpersonLossDeductibleRate[findex].value = 20;
		if (flag == '1') {
			fm.prpLpersonLossExceptDeductibleRate1[findex].value = 20;
		}
		return true;
	}
	if (isNaN(indemnityDuty))
		indemnityDuty = 0;
	var outputObject;
	var inputObject = field;
	if (kindCode != "" && registNo != "" && riskCode != "") {
		var inputArgs = {
			kindCode: kindCode,
			indemnityDuty: indemnityDuty,
			riskCode1: riskCode,
			registNo: registNo
		};
		var param = DWRUtil.getValues(inputArgs);
		DWREngine.setAsync(false);
		dwrInvokeData("getAllDeductibleRate", param, "rollbackAllDeductibleRate", inputObject, outputObject);
		DWREngine.setAsync(true);
	}
}

function rollbackAllDeductibleRate(inputObject, outputObject, returnObject) {
	var fieldname = inputObject.name;
	var findex = 0;
	for (var i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == inputObject) {
			findex = i;
			break;
		}
	}
	var exceptDeductibleRateDto = returnObject;
	fm.prpLpersonLossDutyDeductibleRate[findex].value = exceptDeductibleRateDto.afterDeductibleRate;
	fm.prpLpersonLossDeductibleRate[findex].value = exceptDeductibleRateDto.deductibleRate;
	fm.prpLpersonLossExceptDeductibleRate1[findex].value = exceptDeductibleRateDto.exceptDeductibleRatePay;

	for (var j = 1; j < fm.all("prpLpersonLossSerialNo").length; j++) {
		if (fm.prpLpersonLossPersonNo(findex).value == fm.prpLpersonLossSerialNo(j).value) {
			calRealpay2ForSunny(fm.prpLpersonLossSerialNo(j));
		}
	}
	undisablebutton();
}
//上传事故信息,上传影像资料信息

function uploadToPlatForm(uploadType) {
	var inputObject = uploadType;
	var outputObject;
	var registNo = fm.prpLregistExtRegistNo.value;
	var comCode = fm.prpLcompensateComCode.value;
	var inputArgs = {
		uploadType1: uploadType,
		registNo1: registNo,
		comCode1: comCode
	};
	var param = DWRUtil.getValues(inputArgs);
	DWREngine.setAsync(false);
	dwrInvokeData("getPrpLagentDto", param, "rollbackPrpLagentDto", inputObject, outputObject);
	DWREngine.setAsync(true);

}

function rollbackPrpLagentDto(inputObject, outputObject, returnObject) {
	var returnInfo = returnObject;
	var uploadType = inputObject;
	if (uploadType == 'DA') {
		if (!window.confirm(returnInfo.errorMessage)) //给操作员反馈信息
		{
			fm.displayUpload.style.display = "";
			fm.displayUploadImage.style.display = "none";
			fm.displayGetConfirm.style.display = "none";
			fm.advanceCaseStatus.value = "05";
		}
		fm.displayUploadImage.value = '上传影像信息';
	} else if (uploadType == 'D5') {
		if (returnInfo.returnType == '1') {
			fm.advanceCaseStatus.value = "10";
			fm.displayUpload.style.display = "none";
			fm.displayGetConfirm.style.display = "";
		} else {
			alert(returnInfo.errorMessage);
		}
	}
	undoDisabledButton('buttonArea');
}
//全责方获取确认信息

function getNullConfirm() {
	var inputObject;
	var outputObject;
	var registNo = fm.prpLregistExtRegistNo.value;
	var comCode = fm.prpLcompensateComCode.value;
	var inputArgs = {
		registNo1: registNo,
		comCode1: comCode
	};
	var param = DWRUtil.getValues(inputArgs);
	disabledAllButton('buttonArea');
	DWREngine.setAsync(false);
	dwrInvokeData("nullConfirmInfo", param, "rollbackGetNullConfirm", inputObject, outputObject);
	DWREngine.setAsync(true);
}

function rollbackGetNullConfirm(inputObject, outputObject, returnObject) {
	var returnInfo = returnObject;
	if (returnInfo.returnType == 1 && returnInfo.advanceResponseReturnDto.nullComments == '') {
		alert(i18n.compel.noResponsiblePartyThrough) //无责方已经确认通过！
		fm.advanceCaseStatus.value = "20";
		fm.displayUpload.style.display = "none";
		fm.displayGetConfirm.style.display = "";
		fm.displayGetConfirm.disabled = true;
	} else {
		alert(returnInfo.errorMessage);
	}
	undoDisabledButton('buttonArea');
}
