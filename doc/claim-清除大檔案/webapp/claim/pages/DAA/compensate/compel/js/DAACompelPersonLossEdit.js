	//删除受害人资讯

	function deletePrpLpersonLossObject(field) {
		$(field).parents("tr[name='prpLpersonLossObject']").remove();
		setPersonAndMedicalSerialNo();
		calFundCommerce();
		countPersonLoss();
	}
	//新增受害人资讯

	function insertPrpLpersonLossObject() {
		$("#PersonCommerce_Data").find("tr[name='prpLpersonLossObject']").clone(true).appendTo("#PrpLpersonLoss");
		setPersonAndMedicalSerialNo();
		countPersonLoss();
	}
	//增加受害人费用信息

	function insertPrpLpersonFeeLossObject(field) {
		var $prpLpersonLossObject = $(field).parents("tr[name='prpLpersonLossObject']");
		var identifyNumber = $prpLpersonLossObject.find("input[name='prpLpersonCommerceIdentifyNumber']").val();
		var sex = $prpLpersonLossObject.find(":input[name='prpLpersonCommerceSex']").val();
		var identityOfInjuredPerson = $prpLpersonLossObject.find(":input[name='prpLpersonCommerceIdentityOfInjuredPerson']").val(); //受害人身份
		$prpLpersonLossObject.find(".flag").val("1");
		if ($.trim(identifyNumber) != '' && (identityOfInjuredPerson != '1' || checkIdentifyNumber(identifyNumber, sex))) {
			$(field).parents("table[name='PersonFeeMedical']").children("tbody").append($("#PersonFeeMedical_Data").find("tr[name='prpLpersonFeeLossObject']").clone(true));
			setPersonAndMedicalSerialNo();
		} else {
			alert("請爲受害人錄入正確的身份證號");
		}
		var flag=$prpLpersonLossObject.find(".flag").val("1");
	}

	//删除受害人费用信息

	function deletePrpLpersonFeeLossObject(field) {
		var $PersonFeeMedical = $(field).parents("table[name='PersonFeeMedical']"); //当前受害人费用信息table
		$(field).parents("tr[name='prpLpersonFeeLossObject']").remove(); //删除当前费用信息
		setPersonAndMedicalSerialNo();
		//重新计算当前受害人的賠付金額
		reCalMaxPay($PersonFeeMedical[0]);

		setPersonLossCommerce($PersonFeeMedical[0]);
		calSumRealPay1($PersonFeeMedical[0]);
		calFundCommerce();
	}
	/**
	 * 考虑删除最高限额项的费用时，其他项加总超过新限额的情况（先A医疗20W，C残废180W；再B死亡0W ；调C残废至200W，删B）
	 * @param field
	 */

	function reCalMaxPay(field) {
		//删除费用后造成限额降低，从而超限的情况
		var pay = getMaxPay(field); //当前限额
		var hasMedical = false;
		var hasCrippled = false;
		var hasDeath = false;
		var sumDefPayA00 = pay[0];
		var sumDefPayB00 = pay[1];
		var sumDefPayC00 = pay[2];
		var maxPay = pay[3];
		var payAmount = sumDefPayA00 + sumDefPayB00 + sumDefPayC00; //当前赔付 
		var exceedingPayout = $(":input[name='exceedingPayout']").val();
		//判断加总到哪一项以后超过限额
		$(field).find("tr[name='prpLpersonFeeLossObject']").each(function () {
			var prpLpersonMedicalDetailCode = $(this).find(":input[name='prpLpersonMedicalDetailCode']").val(); //费用代码
			if ($.trim(prpLpersonMedicalDetailCode) != '') {
				var $prpLpersonMedicalSumDefPay = $(this).find(":input[name='prpLpersonMedicalSumDefPay']"); //核定赔偿
				var prpLpersonMedicalDetailName = $(this).find(":input[name='prpLpersonMedicalDetailName']").val(); //费用代码
				payAmount += parseFloat($prpLpersonMedicalSumDefPay.val());
				if (payAmount > maxPay) { //新设置限额未超限
					if(exceedingPayout=="false"){
						$prpLpersonMedicalSumDefPay.val(0);
						alert("加總‘" + prpLpersonMedicalDetailName + "’後費用給付超限" + (payAmount - maxPay) + "。(限額:" + maxPay + ")");
					}else if(exceedingPayout=="true"){
						var messages = "加總‘" + prpLpersonMedicalDetailName + "’後費用給付超限" + (payAmount - maxPay) + "。(限額:" + maxPay + ")";
						exceedingPayout = confirm(messages+"\n因您有超額賠付權限：點擊【確定】繼續賠付，點【取消】重新錄入！");
						if(!exceedingPayout){
							$prpLpersonMedicalSumDefPay.val(0);
						}
					}else if(exceedingPayout==false){
						$prpLpersonMedicalSumDefPay.val(0);
						alert("加總‘" + prpLpersonMedicalDetailName + "’後費用給付超限" + (payAmount - maxPay) + "。(限額:" + maxPay + ")");
					}
				}
			}
		});
	}
	/***
	 * 取当前受害人的赔付限额以及本案其他计算书已赔付情况
	 * @param field 当前受害人的所有费用信息所在table对象
	 * @returns {Number}
	 */

	function getMaxPay(field) {
		var hasMedical = false; //医疗标志
		var hasCrippled = false; //残废标志
		var hasDeath = false; //死亡标志
		$(field).find("tr[name='prpLpersonFeeLossObject']").each(function () {
			var prpLpersonMedicalDetailCode = $(this).find(":input[name='prpLpersonMedicalDetailCode']").val(); //费用代码
			if ($.trim(prpLpersonMedicalDetailCode) != '') {
				if (prpLpersonMedicalDetailCode.charAt(0) == 'A') {
					hasMedical = true; //有医疗
				} else if (prpLpersonMedicalDetailCode.charAt(0) == 'B') {
					hasDeath = true; //有死亡
				} else if (prpLpersonMedicalDetailCode.charAt(0) == 'C') {
					hasCrippled = true; //有伤残
				}
			}
		});
		var pay = new Array(0, 0, 0, 0);
		var $prpLpersonLossObject = $(field).parents("tr[name='prpLpersonLossObject']"); //当前受害人对象
		var identifyNumber = $prpLpersonLossObject.find("input[name='prpLpersonCommerceIdentifyNumber']").val();
		var $personPay = $("#limitMap").find("input[name$='" + identifyNumber + "']");
		if ($personPay.length > 0) {
			$personPay.each(function () {
				if (this.name.charAt(0) == 'A') {
					pay[0] = pay[0] + parseFloat(this.value); //医疗加总
					hasMedical = true;
				} else if (this.name.charAt(0) == 'B') {
					pay[1] = pay[1] + parseFloat(this.value); //死亡加总
					hasDeath = true;
				} else if (this.name.charAt(0) == 'C') {
					pay[2] = pay[2] + parseFloat(this.value); //残废加总
					hasCrippled = true;
				}
			});
		}
		var maxPay = (hasMedical ? MAXMEDICALPAY : 0); //当前限额
		if (hasDeath || hasCrippled) { //限额取的不是医疗，且有医疗的情况 则加上医疗
			maxPay += (hasDeath ? MAXDEATHPAY : MAXCRIPPLEDPAY);
		}
		pay[3] = maxPay;
		return pay;
	}


	/**
	 * 页面初始化时汇总受害人各项费用、赔偿金额
	 */

	function initRealPay() {
		$("#PrpLpersonLoss").find(":input[name='prpLpersonCommerceSumRealPay1']").each(function () {
			calSumRealPay1(this);
		});
	}
	/**
	 * 计算受害人赔付金额、各项费用加总金额
	 * @param field 当前受害人所在tr name='prpLpersonLossObject' 下的任一个document对象
	 */

	function calSumRealPay1(field) {
		var sumDefPayA00 = 0;
		var sumDefPayB00 = 0;
		var sumDefPayC00 = 0;
		var $prpLpersonLossObject = $(field).parents("tr[name='prpLpersonLossObject']")
		$prpLpersonLossObject.find("tr[name='prpLpersonFeeLossObject']").each(function () {
			var prpLpersonMedicalDetailCode = $(this).find(":input[name='prpLpersonMedicalDetailCode']").val(); //费用代码
			if ($.trim(prpLpersonMedicalDetailCode) != '') {
				var prpLpersonMedicalSumLoss = $(this).find(":input[name='prpLpersonMedicalSumLoss']").val(); //核定损失
				var prpLpersonMedicalSumDefPay = $(this).find(":input[name='prpLpersonMedicalSumDefPay']").val(); //核定赔偿
				if (prpLpersonMedicalDetailCode.charAt(0) == 'A') {
					sumDefPayA00 += parseFloat(prpLpersonMedicalSumDefPay);
				} else if (prpLpersonMedicalDetailCode.charAt(0) == 'B') {
					sumDefPayB00 += parseFloat(prpLpersonMedicalSumDefPay);
				} else if (prpLpersonMedicalDetailCode.charAt(0) == 'C') {
					sumDefPayC00 += parseFloat(prpLpersonMedicalSumDefPay);
				}
			}
		});
		$prpLpersonLossObject.find(":input[name='prpLpersonCommerceSumRealPay1']").val(Math.round(sumDefPayA00 + sumDefPayB00 + sumDefPayC00));
		$prpLpersonLossObject.find(":input[name='prpLPersonLossA00']").val(Math.round(sumDefPayA00)); //医疗加总
		$prpLpersonLossObject.find(":input[name='prpLPersonLossB00']").val(Math.round(sumDefPayB00)); //残废加总
		$prpLpersonLossObject.find(":input[name='prpLPersonLossC00']").val(Math.round(sumDefPayC00)); //死亡加总
	}
	/**
	 * 汇总賠款計算訊息
	 */

	function calFundCommerce() {
		var sumLossA00 = 0; //核定损失
		var sumLossB00 = 0;
		var sumLossC00 = 0;
		var sumDefPayA00 = 0; //核定赔偿、
		var sumDefPayB00 = 0;
		var sumDefPayC00 = 0;
		$("#PrpLpersonLoss").find("tr[name='prpLpersonFeeLossObject']").each(function () {
			var prpLpersonMedicalDetailCode = $(this).find(":input[name='prpLpersonMedicalDetailCode']").val(); //费用代码
			if ($.trim(prpLpersonMedicalDetailCode) != '') {
				var prpLpersonMedicalSumLoss = $(this).find(":input[name='prpLpersonMedicalSumLoss']").val(); //核定损失
				var prpLpersonMedicalSumDefPay = $(this).find(":input[name='prpLpersonMedicalSumDefPay']").val(); //核定赔偿
				if (prpLpersonMedicalDetailCode.charAt(0) == 'A') {
					sumLossA00 += parseFloat(prpLpersonMedicalSumLoss);
					sumDefPayA00 += parseFloat(prpLpersonMedicalSumDefPay);
				} else if (prpLpersonMedicalDetailCode.charAt(0) == 'B') {
					sumLossB00 += parseFloat(prpLpersonMedicalSumLoss);
					sumDefPayB00 += parseFloat(prpLpersonMedicalSumDefPay);
				} else if (prpLpersonMedicalDetailCode.charAt(0) == 'C') {
					sumLossC00 += parseFloat(prpLpersonMedicalSumLoss);
					sumDefPayC00 += parseFloat(prpLpersonMedicalSumDefPay);
				}
			}
		});
		//醫療費用
		$(":input[name='medicalSumLoss']").val(Math.round(sumLossA00)); //損失合計
		$(":input[name='medicalSumDefPay']").val(Math.round(sumDefPayA00)); //核定賠付金
		$(":input[name='medicalSumRelPay']").val(Math.round(sumDefPayA00)); //賠款金額
		//殘疾給付
		$(":input[name='crippledSumLoss']").val(Math.round(sumLossC00)); //損失合計
		$(":input[name='crippledSumDefPay']").val(Math.round(sumDefPayC00)); //核定賠付金
		$(":input[name='crippledSumRelPay']").val(Math.round(sumDefPayC00)); //賠款金額
		//死亡給付
		$(":input[name='deathSumLoss']").val(Math.round(sumLossB00)); //損失合計
		$(":input[name='deathSumDefPay']").val(Math.round(sumDefPayB00)); //核定賠付金
		$(":input[name='deathSumRelPay']").val(Math.round(sumDefPayB00)); //賠款金額
		//實賠金額總計
		$(":input[name='totalPay']").val(Math.round(sumDefPayA00 + sumDefPayB00 + sumDefPayC00));
		$(":input[name='prpLdangerRiskSumPaid']").val(Math.round(sumDefPayA00 + sumDefPayB00 + sumDefPayC00));
		calSumDutyPaid();
	}
	/**
	 * 修改费用类型清空当前费用的值和金额
	 */

	function clearPrpLpersonFeeLoss(field) {
		var $prpLpersonFeeLossObject = $(field).parents("tr[name='prpLpersonFeeLossObject']");
		var $ratingCode = $prpLpersonFeeLossObject.find(":input[name='prpLdisabilityLimitRatingCode']");
		$ratingCode.val("");
		$prpLpersonFeeLossObject.find(":input[name='prpLpersonMedicalSumDefPay']").val(0);
		var $detailCode = $prpLpersonFeeLossObject.find(":input[name='prpLpersonMedicalDetailCode']");
		var disabledFlag = true;
		if ($.trim($detailCode.val()) != '') {
			//判断该费用类别是否已存在，存在则清空
			var $detailCodes = $(field).parents("tr[name='prpLpersonLossObject']").find(":input[name='prpLpersonMedicalDetailCode']").not($detailCode[0]);
			var continueFlag = true;
			$detailCodes.each(function () {
				if ($.trim($(this).val()) == $.trim($detailCode.val())) {
					alert("該費用類別已存在!");
					continueFlag = false;
					return false;
				}
			});
			if (continueFlag) {
				setPersonLossCommerce(field);
				if ($.trim($detailCode.val()).charAt(0) == 'C') { //残废 则开放残废等级录入
					disabledFlag = false;
				}
			} else {
				$detailCode.val("");
				$prpLpersonFeeLossObject.find(":input[name='prpLpersonMedicalDetailName']").val("");
				$prpLpersonFeeLossObject.find(":input[name='medicDeathFlag']").val("");
			}
		}
		$ratingCode.attr("disabled", disabledFlag);
		//重新计算当前受害人的赔付合计、汇总賠款計算訊息
		calSumRealPay1(field);
		calFundCommerce();
	}
	/**
	 * 改变费用损失（损失小于赔偿则将赔偿清0，提示重新录入）
	 */

	function calCompelSumLoss(field) {
		if (isChange(field)) {
			var $prpLpersonFeeLossObject = $(field).parents("tr[name='prpLpersonFeeLossObject']");
			var $sumDefPay = $prpLpersonFeeLossObject.find(":input[name='prpLpersonMedicalSumDefPay']");
			if ($.trim(field.value) == '') {
				recoveryData(field);
				return alertMessage(field, "核定損失不能爲空值!");
			}
			if (parseFloat(field.value) < parseFloat($sumDefPay.val())) { //损失小于核定赔偿，赔偿先清0
				$sumDefPay.val(0);
				calSumRealPay1(field)
				alertMessage($sumDefPay[0], "核定損失小于核定賠付，請重新錄入核定賠付!");
			}
			calFundCommerce(); //重新汇总强制险賠款計算訊息
		}
	}
	/**
	 * 改变费用赔偿
	 * 1、费用不得超过损失，
	 * 2、同类型费用加总不得超过该类型限额，
	 * 3、所有赔偿加总不得超过该受害人所有费用类型中的最高限额
	 * 醫療費用限额 MAXMEDICALPAY = 200000;
	 * 殘疾給付額限额 MAXCRIPPLEDPAY = 2000000;
	 * 死亡给付限额 MAXDEATHPAY = 2200000;
	 */
	function calCompelSumDefPay(field) {
		if (isChange(field)) {
			var $currFeeLossObject = $(field).parents("tr[name='prpLpersonFeeLossObject']"); //当前正操作的费用讯息
			var currSumLoss = $currFeeLossObject.find(":input[name='prpLpersonMedicalSumLoss']").val(); //当前操作的费用的核定损失
			if ($.trim(field.value) == '') {
				recoveryData(field);
				return alertMessage(field, "核定賠付不能爲空值!");
			}
			if (parseFloat(currSumLoss) < parseFloat(field.value)) {
				recoveryData(field);
				return alertMessage(field, "核定賠付不得超過核定損失!");
			}
			var maxPay = 0; //当前限额
			var sumDefPayA00 = 0;
			var sumDefPayB00 = 0;
			var sumDefPayC00 = 0;
			var hasMedical = false; //医疗标志
			var hasCrippled = false; //残废标志
			var hasDeath = false; //死亡标志
			var $prpLpersonLossObject = $(field).parents("tr[name='prpLpersonLossObject']"); //当前受害人对象
			var $identifyNumber = $prpLpersonLossObject.find("input[name='prpLpersonCommerceIdentifyNumber']");
			var $personName = $prpLpersonLossObject.find("input[name='prpLpersonCommercePersonName']");
			var identifyNumber = $identifyNumber.val();
			var sex = $prpLpersonLossObject.find(":input[name='prpLpersonCommerceSex']").val();
			var identityOfInjuredPerson = $prpLpersonLossObject.find(":input[name='prpLpersonCommerceIdentityOfInjuredPerson']").val(); //受害人身份
			if ($.trim(identifyNumber) == '' || (identityOfInjuredPerson == '1' && !checkIdentifyNumber(identifyNumber, sex))) {
				recoveryData(field);
				return alertMessage($identifyNumber[0], "請爲受害人錄入正確的身份證號!");
			}
			$prpLpersonLossObject.find("tr[name='prpLpersonFeeLossObject']").each(function () {
				var prpLpersonMedicalDetailCode = $(this).find(":input[name='prpLpersonMedicalDetailCode']").val(); //费用代码
				if ($.trim(prpLpersonMedicalDetailCode) != '') {
					var prpLpersonMedicalSumLoss = $(this).find(":input[name='prpLpersonMedicalSumLoss']").val(); //核定损失
					var prpLpersonMedicalSumDefPay = $(this).find(":input[name='prpLpersonMedicalSumDefPay']").val(); //核定赔偿
					if (prpLpersonMedicalDetailCode.charAt(0) == 'A') {
						sumDefPayA00 += parseFloat(prpLpersonMedicalSumDefPay); //医疗加总
						hasMedical = true;
					} else if (prpLpersonMedicalDetailCode.charAt(0) == 'B') {
						sumDefPayB00 += parseFloat(prpLpersonMedicalSumDefPay); //死亡加总
						hasDeath = true;
					} else if (prpLpersonMedicalDetailCode.charAt(0) == 'C') {
						sumDefPayC00 += parseFloat(prpLpersonMedicalSumDefPay); //残废加总
						hasCrippled = true;
					}
				}
			});
			var $personPay = $("#limitMap").find("input[name$='" + identifyNumber + "']");
			var messages = "本案受害人：" +$personName.val()+"("+ identifyNumber + ")\n";
			if ($personPay.length > 0) {
				messages += "其他計算書已賠付:";
				$personPay.each(function () {
					if (this.name.charAt(0) == 'A') {
						sumDefPayA00 += parseFloat(this.value); //医疗加总
						messages += "醫療費用";
						hasMedical = true;
					} else if (this.name.charAt(0) == 'B') {
						sumDefPayB00 += parseFloat(this.value); //死亡加总
						messages += "死亡給付";
						hasDeath = true;
					} else if (this.name.charAt(0) == 'C') {
						sumDefPayC00 += parseFloat(this.value); //残废加总
						messages += "殘疾給付";
						hasCrippled = true;
					}
					messages += Math.round(this.value) + ";"
				});
				messages += "\n";
			}
			var maxPay = (hasMedical ? MAXMEDICALPAY : 0); //当前限额
			if (hasDeath || hasCrippled) { //限额取的不是医疗，且有医疗的情况 则加上医疗
				maxPay += (hasDeath ? MAXDEATHPAY : MAXCRIPPLEDPAY);
			}
			var exceedingPayout = $(":input[name='exceedingPayout']").val();
			if (sumDefPayA00 > MAXMEDICALPAY) { //校验医疗加总是否超出医疗限额，超出则撤销当前录入
				messages += "當前計算書醫療給付超出限額" + Math.round(sumDefPayA00 - MAXMEDICALPAY) + "! (醫療給付限額:" + MAXMEDICALPAY + ")";
				//判断用户是否有超额赔付权限，如果有超额赔付权限，就不控制医疗最高限额20万
				if(exceedingPayout=="true"){
					exceedingPayout = confirm(messages+"\n因您有超額賠付權限：點擊【確定】繼續賠付，點【取消】重新錄入！");
					if(!exceedingPayout){
						recoveryData(field);
						return true;
					}
				}else{
					recoveryData(field);
					return alertMessage(field, messages);
				}
			}
			if (sumDefPayC00 > MAXCRIPPLEDPAY) { //校验残废加总是否超出医疗限额，超出则撤销当前录入
				recoveryData(field);
				messages += "當前計算書殘廢給付超出限額" + Math.round(sumDefPayC00 - MAXCRIPPLEDPAY) + "! (殘廢給付限額:" + MAXCRIPPLEDPAY + ")";
				return alertMessage(field, messages);
			}
			if (sumDefPayB00 > MAXDEATHPAY) { //校验死亡加总是否超出医疗限额，超出则撤销当前录入
				recoveryData(field);
				messages += "當前計算書死亡給付超出限額" + Math.round(sumDefPayB00 - MAXDEATHPAY) + "! (死亡給付限額:" + MAXDEATHPAY + ")";
				return alertMessage(field, messages);
			}
			var sumDefPay = sumDefPayA00 + sumDefPayC00 + sumDefPayB00;
			if (sumDefPay > maxPay) { //校验该受害人赔付合计是否超过
				messages += "當前計算書賠付金額合計超出限額" + Math.round(sumDefPay - maxPay) + "! (限額:" + maxPay + ")";
				if(exceedingPayout=="false"||exceedingPayout==false){
					recoveryData(field);
					return alertMessage(field, messages);
				}else if(exceedingPayout=="true"){
					exceedingPayout = confirm(messages+"\n因您有超額賠付權限：點擊【確定】繼續賠付，點【取消】重新錄入！");
					if(!exceedingPayout){
						recoveryData(field);
						return true;
					}
				}
			}
			//校验通过则设置当前受害人赔付金额合计、各项费用加总信息
			calSumRealPay1(field);
			calFundCommerce(); //重新汇总强制险賠款計算訊息
		}
	}
	/**
	 * 重新设置受害人序号、费用信息序号(与其受害人序号一致)
	 */

	function setPersonAndMedicalSerialNo() {
		$("#PrpLpersonLoss").find("tr[name='prpLpersonLossObject']").each(function (i, prpLpersonLossObject) {
			$(prpLpersonLossObject).find(":input[name='personNum']").val(i + 1);
			$(prpLpersonLossObject).find(":input[name='prpLpersonCommerceSerialNo']").val(i + 1);
			$(prpLpersonLossObject).find("tr[name='prpLpersonFeeLossObject']").each(function () {
				$(this).find(":input[name='personMedicalSerialNo']").val(i + 1);
			});
		});
	}
	/**
	 * 统计受害人數彙整
	 */

	function countPersonLoss() {
		//理赔人数
		$(":input[name='personLossNumber']").val($("#PrpLpersonLoss").find(":input[name='personNum']").size());
		var carNumber = new Array(0, 0, 0, 0); //本车伤亡情形([医疗,残废,死亡]) 保证顺序
		var threeCarNumber = new Array(0, 0, 0, 0); //对方车伤残亡情形 [医疗,残废,死亡]
		var outerCarNumber = new Array(0, 0, 0, 0); //车外伤残亡情形 [医疗,残废,死亡]
		$("#PrpLpersonLoss").find("tr[name='prpLpersonLossObject']").each(function () {
			var situation = $(this).find(":input[name='prpLpersonCommerceRideSituation']").val(); //乘坐情况
			$(this).find(":checkbox[name='CommerceCasualties']").each(function (i, commerce) {
				//需要对CheckBox的值进行严格控制，保证此处索引的正确，当前(1医疗，2残废，3死亡)
				var index = parseInt($(commerce).val());
				if ($(commerce).is(":checked")) {
					if (situation == '1' || situation == '6') { //本车
						carNumber[index] = carNumber[index] + 1;
					} else if (situation == '4' || situation == '5') { //對方車
						threeCarNumber[index] = threeCarNumber[index] + 1;
					} else if (situation == '3') { //車外
						outerCarNumber[index] = outerCarNumber[index] + 1;
					}
				}
			})
		});
		//本车医疗、残废、死亡
		$("#PersonLossNumberCount").find("input[name='carMedicalNumber']").val(carNumber[1]);
		$("#PersonLossNumberCount").find("input[name='carCrippledNumber']").val(carNumber[2]);
		$("#PersonLossNumberCount").find("input[name='carDeathNumber']").val(carNumber[3]);
		//三者车医疗、残废、死亡
		$("#PersonLossNumberCount").find("input[name='threeCarMedicalNumber']").val(threeCarNumber[1]);
		$("#PersonLossNumberCount").find("input[name='threeCarCrippledNumber']").val(threeCarNumber[2]);
		$("#PersonLossNumberCount").find("input[name='threeCarDeathNumber']").val(threeCarNumber[3]);
		//车外医疗、残废、死亡
		$("#PersonLossNumberCount").find("input[name='outerCarMedicalNumber']").val(outerCarNumber[1]);
		$("#PersonLossNumberCount").find("input[name='outerCarCrippledNumber']").val(outerCarNumber[2]);
		$("#PersonLossNumberCount").find("input[name='outerCarDeathNumber']").val(outerCarNumber[3]);

	}
	/**
	 * 设置伤亡情形（需要根据费用类型确认）
	 * @param field
	 */

	function setPersonLossCommerce(field) {
		//找到当前操作的受害人
		var $prpLpersonLossObject = $(field).parents("tr[name='prpLpersonLossObject']");
		//伤亡情形CheckBox所处span
		var $commerceSpan = $prpLpersonLossObject.find("span[name='CommerceCasualtiesSpan']");
		$prpLpersonLossObject.find("input[name='prpLpersonMedicalDetailCode']").each(function () {
			var detailCode = $(this).val();
			if ($.trim(detailCode) != '') {
				if (detailCode.charAt(0) == 'A') { //有医疗
					$commerceSpan.find(":checkbox[name='CommerceCasualties'][value='1']").attr("checked", true);
				} else if (detailCode.charAt(0) == 'B') { //有死亡
					$commerceSpan.find(":checkbox[name='CommerceCasualties'][value='3']").attr("checked", true);
				} else if (detailCode.charAt(0) == 'C') { //有残废
					$commerceSpan.find(":checkbox[name='CommerceCasualties'][value='2']").attr("checked", true);
				}
			}
		});
		//设置对应隐藏域的值
		$commerceSpan.find(":input[name='prpLpersonCommerceCasualties']").val($commerceSpan.find("input:checked").map(function () {
			return $(this).val();
		}).get().join(","));
		countPersonLoss();
	}
	/**
	 * 页面加载完毕后，设置#PrpLpersonLoss下伤亡情形CheckBox
	 */

	function initCommerceCasualties() {
		$("#PrpLpersonLoss").find("span[name='CommerceCasualtiesSpan']").each(function (i, span) {
			var casualties = $(span).find(":input[name='prpLpersonCommerceCasualties']").val();
			$(span).find(":checkbox[name='CommerceCasualties']").each(function () {
				if (casualties.indexOf($(this).val()) != -1) {
					$(this).attr("checked", true);
				}
			});
		});
	}

	/**
	 * 残废获取固定的赔付金额(只在残废等级改变的时候触发)
	 */

	function getCrippledPay(field) {
		var claimNo = fm.prpLcompensateClaimNo.value;
		var ratingCode = $(field).val();
		$(field).blur();
		var $prpLpersonFeeLossObject = $(field).parents("tr[name='prpLpersonFeeLossObject']");
		$prpLpersonFeeLossObject.find(":input[name='prpLpersonMedicalInjuryGrade']").val(ratingCode);
		var detailCode = $prpLpersonFeeLossObject.find(":input[name='prpLpersonMedicalDetailCode']").val();
		var $sumLoss = $prpLpersonFeeLossObject.find(":input[name='prpLpersonMedicalSumLoss']");
		var $sumDefPay = $prpLpersonFeeLossObject.find(":input[name='prpLpersonMedicalSumDefPay']");
		if ($.trim(detailCode) != '') {
			$.getJSON(contextRootPath + "/compensate/getCrippledPay.do", {
					claimNo: claimNo,
					ratingCode: ratingCode
				},
				function (data) {
					if (data.limitFee != null) {
						var limitFee = parseFloat(data.limitFee); //获取的固定赔偿值
						$sumLoss.val(Math.round(limitFee));
						if (limitFee == parseFloat($sumDefPay.val())) { //此次改变未使得赔偿增加或减少
							calFundCommerce();
						} else {
							cacheData($sumDefPay[0]); //该函数可能会造成核定赔偿改变，先缓存其值
							$sumDefPay.val(Math.round(limitFee));
							calCompelSumDefPay($sumDefPay[0]);
						}
					} else if (data.errorMessage != null) {
						alert(data.errorMessage);
					}
				});
		}
	}
	/***
	 * 通过对受害人身份证号的校验，控制同一张计算书不能有身份证号重复的受害人
	 */

	function checkPerson(field) {
		var $prpLpersonLossObject = $(field).parents("tr[name='prpLpersonLossObject']");
		var $identifyNumber = $prpLpersonLossObject.find("input[name='prpLpersonCommerceIdentifyNumber']");
		var sex = $prpLpersonLossObject.find(":input[name='prpLpersonCommerceSex']").val();
		var identityOfInjuredPerson = $prpLpersonLossObject.find(":input[name='prpLpersonCommerceIdentityOfInjuredPerson']").val(); //受害人身份
		if ($identifyNumber.val() != '' && (identityOfInjuredPerson != '1' || checkIdentifyNumber($identifyNumber.val(), sex))) {
			var $person = $("#PrpLpersonLoss").find("input[name='prpLpersonCommerceIdentifyNumber'][value='" + $identifyNumber.val() + "']").not(field);
			if ($person.length > 0) {
				$identifyNumber.val("");
				return alertMessage(field, "當前已存在與該身份證號相同的受害人，請勿重複錄入!")
			}
		} else {
			return alertMessage(field, "請爲受害人錄入正確的身份證號!")
		}
		return true;
	}
	/***
	 * 判断每个受害人赔付是否超限
	 */

	function checkLimit() {
		var checkFlag = true;
		var exceedingPayout = $(":input[name='exceedingPayout']").val();
		$("#PrpLpersonLoss").find("tr[name='prpLpersonLossObject']").each(function (i, obj) {
			var maxPay = 0; //当前限额
			var sumDefPayA00 = 0;
			var sumDefPayB00 = 0;
			var sumDefPayC00 = 0;
			var hasMedical = false; //医疗标志
			var hasCrippled = false; //残废标志
			var hasDeath = false; //死亡标志
			$(obj).find("tr[name='prpLpersonFeeLossObject']").each(function () {
				var prpLpersonMedicalDetailCode = $(this).find(":input[name='prpLpersonMedicalDetailCode']").val(); //费用代码
				if ($.trim(prpLpersonMedicalDetailCode) != '') {
					var prpLpersonMedicalSumDefPay = $(this).find(":input[name='prpLpersonMedicalSumDefPay']").val(); //核定赔偿
					if (prpLpersonMedicalDetailCode.charAt(0) == 'A') {
						sumDefPayA00 += parseFloat(prpLpersonMedicalSumDefPay); //医疗加总
						hasMedical = true;
					} else if (prpLpersonMedicalDetailCode.charAt(0) == 'B') {
						sumDefPayB00 += parseFloat(prpLpersonMedicalSumDefPay); //死亡加总
						hasDeath = true;
					} else if (prpLpersonMedicalDetailCode.charAt(0) == 'C') {
						sumDefPayC00 += parseFloat(prpLpersonMedicalSumDefPay); //残废加总
						hasCrippled = true;
					}
				}
			});
			var identifyNumber = $(obj).find("input[name='prpLpersonCommerceIdentifyNumber']").val();
			var personName = $(obj).find("input[name='prpLpersonCommercePersonName']").val();
			var $personPay = $("#limitMap").find("input[name$='" + identifyNumber + "']");
			var pastPayMessage = "本案受害人："+personName+"(" + identifyNumber + ")\n";
			if ($personPay.length > 0) {
				pastPayMessage += "其他計算書已賠付:";
				$personPay.each(function () {
					if (this.name.charAt(0) == 'A') {
						sumDefPayA00 += parseFloat(this.value); //医疗加总
						pastPayMessage += "醫療費用";
						hasMedical = true;
					} else if (this.name.charAt(0) == 'B') {
						sumDefPayB00 += parseFloat(this.value); //死亡加总
						pastPayMessage += "死亡給付";
						hasDeath = true;
					} else if (this.name.charAt(0) == 'C') {
						sumDefPayC00 += parseFloat(this.value); //残废加总
						pastPayMessage += "殘疾給付";
						hasCrippled = true;
					}
					pastPayMessage += Math.round(this.value) + ";"
				});
				pastPayMessage += "\n";
			}
			var currPayMessage = "";
			var maxPay = (hasMedical ? MAXMEDICALPAY : 0); //当前限额
			var sumDefPay = sumDefPayA00 + sumDefPayC00 + sumDefPayB00; //当前总核定赔偿
			if (hasDeath || hasCrippled) { //限额取的不是医疗，且有医疗的情况 则加上医疗
				maxPay += (hasDeath ? MAXDEATHPAY : MAXCRIPPLEDPAY);
			}
			if (sumDefPayA00 > MAXMEDICALPAY) { //校验医疗加总是否超出医疗限额，超出则撤销当前录入
				currPayMessage += "當前計算書醫療給付超出限額" + Math.round(sumDefPayA00 - MAXMEDICALPAY) + "! (醫療給付限額:" + MAXMEDICALPAY + ")";
			} else if (sumDefPayC00 > MAXCRIPPLEDPAY) { //校验残废加总是否超出医疗限额，超出则撤销当前录入
				currPayMessage += "當前計算書殘廢給付超出限額" + Math.round(sumDefPayC00 - MAXCRIPPLEDPAY) + "! (殘廢給付限額:" + MAXCRIPPLEDPAY + ")";
			} else if (sumDefPayB00 > MAXDEATHPAY) { //校验死亡加总是否超出医疗限额，超出则撤销当前录入
				currPayMessage += "當前計算書死亡給付超出限額" + Math.round(sumDefPayB00 - MAXDEATHPAY) + "! (死亡給付限額:" + MAXDEATHPAY + ")";
			} else if (sumDefPay > maxPay) { //校验该受害人赔付合计是否超过
				currPayMessage += "當前計算書賠付金額合計超出限額" + Math.round(sumDefPay - maxPay) + "! (限額:" + maxPay + ")";
			}
			if (currPayMessage.length > 0) {
				if(exceedingPayout=="true"){
					exceedingPayout = window.confirm(pastPayMessage + currPayMessage+"\n因您有超額賠付權限：點擊【確定】繼續賠付，點【取消】重新錄入！");
					if(exceedingPayout==false){
						checkFlag = false;
					}
					return exceedingPayout;
				}else if(exceedingPayout=="false"||exceedingPayout==false){
					alert(pastPayMessage + currPayMessage);
					checkFlag = false;
					return false;
				}
			}
		});
		return checkFlag;
	}
	//查看历史赔付人员讯息

	function showPersonHistory() {
		var claimNo = document.getElementsByName("prpLcompensateClaimNo")[0].value;
		var url = contextRootPath + "/compensate/compensatePersonHistory.do?prpLcompensateClaimNo=" + claimNo;
		window.open(url, "歷史賠付受害人訊息");
	}
	//增加赔付人员信息

	function addPersonHistory(prpLpersonLoss) {
		var flag = true;
		$(":input[name='prpLpersonCommerceIdentifyNumber']").each(function () {
			if (prpLpersonLoss.identifyNumber == this.value) {
				flag = false;
				return false;
			}
		})
		if (!flag) {
			return flag;
		}
		//插入一条人伤信息
		insertPrpLpersonLossObject();
		var $prpLpersonLossObject = $("tr[name='prpLpersonLossObject']:last");
		$prpLpersonLossObject.find(":input[name='prpLpersonCommercePersonName']").val(prpLpersonLoss.personName);
		$prpLpersonLossObject.find(":input[name='prpLpersonCommerceSex']").val(prpLpersonLoss.sex);
		$prpLpersonLossObject.find(":input[name='prpLpersonCommerceFamilyName']").val(prpLpersonLoss.familyName);
		$prpLpersonLossObject.find(":input[name='prpLpersonCommerceBirthday']").val(prpLpersonLoss.birthday);
		$prpLpersonLossObject.find(":input[name='prpLpersonCommerceBirthday_show_format_rcDate']").val(prpLpersonLoss.birthday_show_format_rcDate);
		$prpLpersonLossObject.find(":input[name='prpLpersonCommerceAge']").val(prpLpersonLoss.age);
		$prpLpersonLossObject.find(":input[name='prpLpersonCommerceIdentityOfInjuredPerson']").val(prpLpersonLoss.identityOfInjuredPerson);
		$prpLpersonLossObject.find(":input[name='prpLpersonCommerceRideSituation']").val(prpLpersonLoss.rideSituation);
		$prpLpersonLossObject.find(":input[name='prpLpersonCommerceIdentifyNumber']").val(prpLpersonLoss.identifyNumber);
		$prpLpersonLossObject.find(":input[name='prpLpersonCommerceMedicalCode']").val(prpLpersonLoss.medicalCode);
		$prpLpersonLossObject.find(":input[name='prpLpersonCommerceEndCaseAndRecoverFlag']").val(prpLpersonLoss.endCaseAndRecoverFlag);
		$prpLpersonLossObject.find(":input[name='prpLpersonCommerceTelephoneNo']").val(prpLpersonLoss.telephoneNo);
		$prpLpersonLossObject.find(":input[name='prpLpersonCommerceProsecutorsOffice']").val(prpLpersonLoss.prosecutorsOffice);
		$prpLpersonLossObject.find(":input[name='prpLpersonCommerceCourtDoctor']").val(prpLpersonLoss.courtDoctor);
		$prpLpersonLossObject.find(":input[name='prpLpersonCommerceMobilePhone']").val(prpLpersonLoss.mobilePhone);
		$prpLpersonLossObject.find(":input[name='prpLpersonCommerceProsecutor']").val(prpLpersonLoss.prosecutor);
		$prpLpersonLossObject.find(":input[name='prpLpersonCommerceGarageHeadName']").val(prpLpersonLoss.garageHeadName);
		$prpLpersonLossObject.find(":input[name='prpLpersonCommerceHospitalCode']").val(prpLpersonLoss.hospitalCode);
		$prpLpersonLossObject.find(":input[name='prpLpersonCommerceHospitalName']").val(prpLpersonLoss.hospitalName);
		$prpLpersonLossObject.find(":input[name='prpLpersonCommerceDoctor']").val(prpLpersonLoss.doctor);
		$prpLpersonLossObject.find(":input[name='prpLpersonCommerceArrangeRate']").val(prpLpersonLoss.arrangeRate);
		if (prpLpersonLoss.casualties != null && prpLpersonLoss.casualties != "") {
			$prpLpersonLossObject.find(":input[name='prpLpersonCommerceCasualties']").val(prpLpersonLoss.casualties);
			$prpLpersonLossObject.find("span[name='CommerceCasualtiesSpan']").each(function (i, span) {
				var casualties = prpLpersonLoss.casualties;
				$(span).find(":checkbox[name='CommerceCasualties']").each(function () {
					if (casualties.indexOf($(this).val()) != -1) {
						$(this).attr("checked", true);
					}
				});
			});
		}
		$prpLpersonLossObject.find(":input[name='prpLpersonCommerceIndemnityDutyRate']").val(prpLpersonLoss.indemnityDutyRate);
		return true;
	}
	//强制险 出生年份 关联 年龄 
	function updatePersonCommerceAge(field){
		var age = 0;
		if(field.realValue != "" && field.realValue != null){
			var birthday = new Date(field.realValue.replace("-","/"));
			var now = new Date();//获得系统当前时间
			age = now.getFullYear()-birthday.getFullYear();
			var nextDate = getNextYearFullDate(field.realValue,age);
			var temp = compareFullDate(nextDate,convertFullDateToString(now));
			if(temp > 0 ){
				age -= 1;
			}
			if(age < 0 ){
				age = 0;
			}
		}else{
			age = "";
		}
		var index = $("input[name='"+field.name+"']").index(field);
		$("input[name='prpLpersonCommerceAge']").get(index).value = age;
	}
	
	/***
	 * 強制險醫療給付費用資訊
	 * @param btnFeild
	 */
	function insertMedicalDetail(btnFeild){
		
		$("#PrpLpersonLoss").find("tr[name='prpLpersonLossObject']").each(function (i, prpLpersonLossObject) {
			var flag=$(prpLpersonLossObject).find(".flag");
			if("1"!=flag.val()){
				flag.parents("prpLpersonFeeLossObject").remove();
			}
		});
		var compensateNo = $(":input[name='prpLcompensateCompensateNo']").val();
		var claimNo = $(":input[name='prpLcompensateClaimNo']").val();
		var $prpLpersonLossObject = $(btnFeild).closest("tr[name='prpLpersonLossObject']");
		var $prpLpayObjectInfoPaycodeType = document.getElementsByName("prpLpayObjectInfoPaycodeType");
		var prpLpayObjectInfoPaycodeType = $prpLpayObjectInfoPaycodeType[0].value;
		var identifyNumber = $prpLpersonLossObject.find(":input[name='prpLpersonCommerceIdentifyNumber']").val();
		var sex = $prpLpersonLossObject.find(":input[name='prpLpersonCommerceSex']").val();
		var identityOfInjuredPerson = $prpLpersonLossObject.find(":input[name='prpLpersonCommerceIdentityOfInjuredPerson']").val(); //受害人身份
		if (!($.trim(identifyNumber) != '' && (identityOfInjuredPerson != '1' || checkIdentifyNumber(identifyNumber, sex)))) {
			alert("請爲受害人錄入正確的身份證號");
			return ;
		}
		var personNo = $prpLpersonLossObject.find(":input[name='prpLpersonCommerceSerialNo']").val();
		var personName = $prpLpersonLossObject.find(":input[name='prpLpersonCommercePersonName']").val();
		var actionType = $(":input[name='editType']").val();
		var height = 600;
		var width = 1260;
		var url = contextRootPath + "/compensate/beforeInsertMedicalDetail.do?actionType="+actionType+"&claimNo="+claimNo+"&compensateNo="+compensateNo+"&personNo="+personNo+"&identifyNumber="+identifyNumber+"&personName="+personName+"&prpLpayObjectInfoPaycodeType="+prpLpayObjectInfoPaycodeType;
		var returnObj = window.showModalDialog(url, window, "dialogHeight:"+height+"px;dialogWidth:"+width+"px;help:no;resizable:yes;status:no;scroll:yes;");
		if(returnObj){
			$prpLpersonLossObject.find(":input[name='prpLpersonMedicalDetailCode'][value^='A0']").each(function(){
				$(this).closest("tr").remove();
			});
			var PersonFee = returnObj.PersonFee;
			var SumFee = returnObj.SumFee;
			var messages = "";
			if(SumFee && SumFee > 0){
				var sumDefPayA00 = 0;
				var identifyNumber = $prpLpersonLossObject.find("input[name='prpLpersonCommerceIdentifyNumber']").val();
				var $personPay = $("#limitMap").find("input[name$='" + identifyNumber + "']");
				if ($personPay.length > 0) {
					messages += "該受害人其他計算書已賠付:";
					$personPay.each(function () {
						if (this.name.charAt(0) == 'A') {
							sumDefPayA00 += parseFloat(this.value); //医疗加总
							messages += "醫療費用";
						}
						messages += Math.round(this.value) + ";"
					});
					messages += "\n";
				}
				var maxPay = MAXMEDICALPAY ; //醫療費用限額
				var exceedingPayout = $(":input[name='exceedingPayout']").val();
				if (sumDefPayA00 + SumFee > MAXMEDICALPAY) { //校验医疗加总是否超出医疗限额，超出则撤销当前录入
					messages += "當前計算書醫療給付超出限額" + Math.round(sumDefPayA00 + SumFee - MAXMEDICALPAY) + "! (醫療給付限額:" + MAXMEDICALPAY + ")";
					//判断用户是否有超额赔付权限，如果有超额赔付权限，就不控制医疗最高限额20万
					if(exceedingPayout=="true"){
						exceedingPayout = confirm(messages+"\n因您有超額賠付權限：點擊【確定】繼續賠付，點【取消】重新錄入醫療給付費用收據資料！");
						if(!exceedingPayout){
							return ;
						}
					}
					alert(messages + "請重新錄入醫療給付費用收據資料！");
					return ;
				}
				var $PersonFeeMedical = $(btnFeild).parents("table[name='PersonFeeMedical']").children("tbody");
				//設置費用資料
				$.each(PersonFee,function(i,fee){
					var $cloneObject = $("#PersonFeeMedical_Data").find("tr[name='prpLpersonFeeLossObject']").clone(true);
					$PersonFeeMedical.append($cloneObject);
					$cloneObject.find(":input[name='prpLpersonMedicalDetailCode']").val(fee.MedicalDetailCode);
					$cloneObject.find(":input[name='prpLpersonMedicalDetailName']").val(fee.MedicalDetailName);
					$cloneObject.find(":input[name='medicDeathFlag']").val("M");
					$cloneObject.find(":input[name='prpLpersonMedicalSumLoss']").val(fee.MedicalSumLoss);
					$cloneObject.find(":input[name='prpLpersonMedicalSumDefPay']").val(fee.MedicalSumDefPay);
				});
				setPersonAndMedicalSerialNo();
				if(returnObj.HealthPoints > 0 || returnObj.HealthAmount > 0){
					$prpLpersonLossObject.find(":input[name='prpLpersonCommerceMedicalCode']").val("Y");
					$prpLpersonLossObject.find(":input[name='prpLpersonCommerceHealthPoints']").val(returnObj.HealthPoints);
					$prpLpersonLossObject.find(":input[name='prpLpersonCommerceHealthAmount']").val(returnObj.HealthAmount);
				}
			} else {
				$prpLpersonLossObject.find(":input[name='prpLpersonCommerceHealthPoints']").val("0");
				$prpLpersonLossObject.find(":input[name='prpLpersonCommerceHealthAmount']").val("0");
			}
			var sumDefPayB00 = $prpLpersonLossObject.find(":input[name='prpLPersonLossB00']").val(); //残废加总
			var sumDefPayC00 = $prpLpersonLossObject.find(":input[name='prpLPersonLossC00']").val(); //死亡加总
			$prpLpersonLossObject.find(":input[name='prpLPersonLossA00']").val(Math.round(SumFee)); //医疗加总
			$prpLpersonLossObject.find(":input[name='prpLpersonCommerceSumRealPay1']").val(Math.round(parseFloat(SumFee) + parseFloat(sumDefPayB00) + parseFloat(sumDefPayC00)));
			//設置人傷
			var $CommerceCasualties1 = $prpLpersonLossObject.find(":input[name='CommerceCasualties'][value='1']");
			$CommerceCasualties1.prop("checked",(SumFee > 0));
			setPersonLossCommerce($CommerceCasualties1[0]);
			//統計賠付
			calFundCommerce();
		}
	}
	
	/***
	 * 理算提交校驗收據費用是否與受害人費用一致
	 */
	function checkCompeMedicalDetail(){
		var $mutualCompensateNo = $(":input[name='prpLcompensateMutualCompensateNo']");
		if($mutualCompensateNo.length > 0 && $mutualCompensateNo.val().length > 0 ){
			//互沖計算書就不校驗了。
			return true ;
		}
		var checkPerson = new Array();
		//統計本畫面存在強制險醫療給付費用的受害人資料
		$("#PrpLpersonLoss").find("tr[name='prpLpersonLossObject']").each(function(i,p){
			var prpLcompelMedical = new Object();
			var $prpLpersonLossObject = $(p);
			prpLcompelMedical.PersonName = $prpLpersonLossObject.find(":input[name='prpLpersonCommercePersonName']").val();
			prpLcompelMedical.IdentifyNumber = $prpLpersonLossObject.find(":input[name='prpLpersonCommerceIdentifyNumber']").val();
			prpLcompelMedical.SumFee = 0;
			prpLcompelMedical.FeeCode = new Array("A01","A021","A022","A023","A024","A025","A026","A029","A03","A04");
			prpLcompelMedical.FeeDefPay = new Array(0,0,0,0,0,0,0,0,0,0);
			$prpLpersonLossObject.find(":input[name='prpLpersonMedicalDetailCode'][value^='A0']").each(function(j,m){
				var $prpLpersonFeeLossObject = $(m).closest("tr");
				var MedicalDetailCode = m.value;
				var medicalSumDefPay = parseFloat($prpLpersonFeeLossObject.find(":input[name='prpLpersonMedicalSumDefPay']").val());
				prpLcompelMedical.SumFee += medicalSumDefPay;
				var index = jQuery.inArray(MedicalDetailCode,prpLcompelMedical.FeeCode);
				if(index + 1 > 0){
					prpLcompelMedical.FeeDefPay[index] += medicalSumDefPay;
				}
			});
			if(prpLcompelMedical.SumFee > 0){
				prpLcompelMedical.HealthPoints = parseFloat($prpLpersonLossObject.find(":input[name='prpLpersonCommerceHealthPoints']").val());
				prpLcompelMedical.HealthAmount = parseFloat($prpLpersonLossObject.find(":input[name='prpLpersonCommerceHealthAmount']").val());
				checkPerson.push(prpLcompelMedical);
			}
		});
		if(checkPerson.length > 0){
			var compensateNo = $(":input[name='prpLcompensateCompensateNo']").val();
			var claimNo = $(":input[name='prpLcompensateClaimNo']").val();
			var $prpLpayObjectInfoPaycodeType = document.getElementsByName("prpLpayObjectInfoPaycodeType");
			var paycodeType = $prpLpayObjectInfoPaycodeType[0].value;
			var result;
			$.ajax({
				type: "POST",
				url: contextRootPath + "/compensate/checkCompeMedicalDetail.do?claimNo="+claimNo+"&compensateNo="+compensateNo,
				async : false,
				cache : false,
				dataType : "json",
				success: function(data){
					if(data.success){
						result = data;
					} else {
						alert(data.msg);
					}
				}
			});
			if(result){//已存儲的醫療收據資料費用彙總（按受害人）
				var checkMessage = "";
				var checkMessage1 = "";
				$.each(checkPerson , function(i , prpLcompelMedical){
					// 檢索受害人是否已存儲有收據資料
					var index = $.inArray(prpLcompelMedical.IdentifyNumber , result.checkPersonIdentifyNumbers);
					if(index + 1 > 0){
						var diff = new Array();
						$.each(prpLcompelMedical.FeeDefPay , function(j , pay){
							if(result.checkPerson[index].FeeDefPay[j] != pay){//判斷每項費用是否一致
								diff.push(prpLcompelMedical.FeeCode[j]);
							}
						});
						if(diff.length > 0 ){
							checkMessage += "受害人"+prpLcompelMedical.PersonName +"（"+prpLcompelMedical.IdentifyNumber + "）醫療費用"+diff.join("、")+"與收據資料不一致！\n";
						}
					} else {
						if(paycodeType == "1"){
							checkMessage1 += "受害人"+prpLcompelMedical.PersonName +"（"+prpLcompelMedical.IdentifyNumber + "）未錄入醫療費用收據資料！請錄入後再提交！\n";
						}
					}
				});
				if(checkMessage.length > 0){
					checkMessage += "是否繼續 ？"
					return confirm(checkMessage);
				}
				if(checkMessage1.length>0){
					alert(checkMessage1);
					return false;
				}
			} else {//獲取收據資料失敗
				return false;
			}
		}
		return true;
	}