/***
 *DAACompelCompensatePayObjectEdit.js
 *与强制险賠付對象訊息相关的JS
 */
/**
 * 赔款支付方式改变时调用下JS
 */

function payObjectOwnerShipChange(field) {
	var $PrpLpayObjectInfo = $(field).parents("tr[name='PrpLpayObjectInfo']");
	if ($(field).val() == "B") { //汇款
		$PrpLpayObjectInfo.find("span[name='InfoUniformNo1']").show(); //显示统一编号
		$PrpLpayObjectInfo.find("span[name='InfoUniformNo2']").hide(); //隐藏个人身份证号
		$PrpLpayObjectInfo.find("span[name='spanCutBack']").hide(); //隐藏禁背项
		$PrpLpayObjectInfo.find("tr[name='bankInfo']").show(); //显示银行支付账户讯息
		$PrpLpayObjectInfo.find("tr[name='AreaInfo']").show(); //显示邮递信息
		$PrpLpayObjectInfo.find("tr[name='PayDate']").hide(); //隐藏 现金支付需要的其他信息
		$PrpLpayObjectInfo.find("span[name='BeneficiaryPhone1']").show(); //显示收款人电话
		$PrpLpayObjectInfo.find("span[name='BeneficiaryPhone2']").hide(); //隐藏市内电话
		$PrpLpayObjectInfo.find("img[name='BeneficiaryPhoneIMG']").show(); //显示收款人电话必录的图片
	} else if ($(field).val() == "Q") { //支票
		$PrpLpayObjectInfo.find("span[name='InfoUniformNo1']").show(); //显示统一编号
		$PrpLpayObjectInfo.find("span[name='InfoUniformNo2']").hide(); //隐藏个人身份证号
		$PrpLpayObjectInfo.find("span[name='spanCutBack']").show(); //显示禁背项
		$PrpLpayObjectInfo.find("tr[name='bankInfo']").hide(); //隐藏银行支付账户讯息
		$PrpLpayObjectInfo.find("tr[name='AreaInfo']").show(); //显示邮递信息
		$PrpLpayObjectInfo.find("tr[name='PayDate']").hide(); //隐藏 现金支付需要的其他信息
		$PrpLpayObjectInfo.find("span[name='BeneficiaryPhone1']").show(); //显示收款人电话
		$PrpLpayObjectInfo.find("span[name='BeneficiaryPhone2']").hide(); //隐藏市内电话
		$PrpLpayObjectInfo.find("img[name='BeneficiaryPhoneIMG']").show(); //显示收款人电话必录的图片
	} else if ($(field).val() == "C") { //现金
		$PrpLpayObjectInfo.find("span[name='InfoUniformNo1']").hide(); //隐藏统一编号
		$PrpLpayObjectInfo.find("span[name='InfoUniformNo2']").show(); //显示个人身份证号
		$PrpLpayObjectInfo.find("span[name='spanCutBack']").hide(); //隐藏禁背项
		$PrpLpayObjectInfo.find("tr[name='bankInfo']").hide(); //隐藏银行支付账户讯息
		$PrpLpayObjectInfo.find("tr[name='AreaInfo']").hide(); //隐藏邮递信息
		$PrpLpayObjectInfo.find("tr[name='PayDate']").show(); //显示现金支付需要的其他信息
		$PrpLpayObjectInfo.find("span[name='BeneficiaryPhone1']").hide(); //隐藏收款人电话
		$PrpLpayObjectInfo.find("span[name='BeneficiaryPhone2']").show(); //显示市内电话
		$PrpLpayObjectInfo.find("img[name='BeneficiaryPhoneIMG']").hide(); //隐藏收款人电话必录的图片
		//现金支付 默认收款人为个人（赔付代号为健保局的情况不用设置）
		if ($(":input[name='prpLpayObjectInfoPaycodeType']").val() != "3") {
			$PrpLpayObjectInfo.find(":input[name='prpLpayObjectInfoPaymentKind']").val("4");
		}
	}
}
/**
 * 賠付代號改变时
 * 如果賠付代號(賠案)值為3，費用代碼類型自動設為 6（費用類型：健保局）
 */

function setPrpLpayObjectInfoPaycodeType(field) {
	if (field.value == "3") { //健保局
		$("#PayAccountInfo").find("tr[name='PrpLpayObjectInfo']").each(function () {
			//赔案代号为健保局时，设置所有的費用代碼類为健保局
			var $paymentKind = $(this).find(":input[name='prpLpayObjectInfoPaymentKind']");
			var ownerShip = $(this).find(":input[name='prpLpayObjectInfoOwnerShip']").val();
			if (ownerShip == "C" && $paymentKind.val() == "4") { //该操作使得现金支付的费用类型由个人改为健保局时
				$(this).find("span[name='InfoUniformNo1']").show(); //显示统一编号
				$(this).find("span[name='InfoUniformNo2']").hide(); //隐藏个人身份证号
			}
			$paymentKind.val("6")
		});
	}
}
/***
 * 改变费用代码时的，现金支付的费用类型改为个人时，切换统一编号为个人身份证号
 */

function changePaymentKind(field) {
	var $prpLpayObjectInfo = $(field).parents("tr[name='PrpLpayObjectInfo']");
	var ownerShip = $prpLpayObjectInfo.find(":input[name='prpLpayObjectInfoPaymentKind']").val();
	if (ownerShip == "C" && field.value == "4") {
		$prpLpayObjectInfo.find("span[name='InfoUniformNo1']").show(); //显示统一编号
		$prpLpayObjectInfo.find("span[name='InfoUniformNo2']").hide(); //隐藏个人身份证号
	} else {
		$prpLpayObjectInfo.find("span[name='InfoUniformNo1']").hide(); //隐藏统一编号
		$prpLpayObjectInfo.find("span[name='InfoUniformNo2']").show(); //显示个人身份证号
	}
}

/**
 * 添加一个支付对象
 * 1、设置被添加对象的序号；
 * 2、赔案代号为健保局时设置默认费用类型
 * 3、设置赔付对象的讯息
 */

function insertPrpLpayObjectInfo() {
	var $prpLpayObjectInfo = $("#PrpLpayObjectInfo_Data").find("tr[name='PrpLpayObjectInfo']").clone(true);
	var serialNo = $("#PayAccountInfo").find("tr[name='PrpLpayObjectInfo']").length;
	$prpLpayObjectInfo.find("span[name='payObjectIndex']").html(serialNo + 1); //设置序号
	$prpLpayObjectInfo.find("input[name='prpLpayObjectInfoSerialNo']").val(serialNo + 1);
	//当前赔付代号为健保局时，需设置費用類型为健保局
	if ($(":input[name='prpLpayObjectInfoPaycodeType']").val() == "3") {
		$prpLpayObjectInfo.find(":input[name='prpLpayObjectInfoPaymentKind']").val("6");
	}
	$prpLpayObjectInfo.appendTo("#PayAccountInfo");
	uLprpLPayObjectinfo();
	$("div[name='payObject']").eq(1).show();
}
/***
 * 删除一个赔付对象
 * 1、该赔付对象之后的所有赔付对象序号减1
 * 2、删除每个人伤赔给该对象的赔付金额；
 * 3、赔付对象序号有减小的 同步减小所有人伤赔付中的赔付对象信息（序号）
 * @param field
 */

function deletePrpLpayObjectInfo(field) {
	var $prpLpayObjectInfo = $(field).parents("tr[name='PrpLpayObjectInfo']");
	var currSerialNo = $prpLpayObjectInfo.find(":input[name='prpLpayObjectInfoSerialNo']").val(); //当前删除支付对象的序号
	$prpLpayObjectInfo.nextAll("tr[name='PrpLpayObjectInfo']").each(function () {
		var $serialNo = $(this).find("input[name='prpLpayObjectInfoSerialNo']");
		$(this).find("span[name='payObjectIndex']").html($serialNo.val() - 1);
		$serialNo.val($serialNo.val() - 1);
	});
	$prpLpayObjectInfo.remove();
	//重新计算每个人伤的 賠付對象讯息
	$("#PrpLpersonLoss").find(":input[name='prpLpersonLossPayObjectSerialNo']").each(function () {
		var serialInfo = $(this).val();
		var temp = "";
		if ($.trim(serialInfo) != '') {
			var payObjectValue = serialInfo.split(";");
			for (var i = 0; i < payObjectValue.length; i++) {
				var kv = payObjectValue[i].split(":");
				var key = parseInt(kv[0]);
				if (key > parseInt(currSerialNo)) {
					temp += ";" + (key - 1) + ":" + kv[1];
				} else if (key < parseInt(currSerialNo)) {
					temp += ";" + payObjectValue[i];
				}
			}
			$(this).val(temp == "" ? "" : temp.substring(1));
		}
	});
	uLprpLPayObjectinfo();
}
/**
 * 组织所有赔付对象序号与赔付金额的对应信息
 */

function uLprpLPayObjectinfo() {
	var uiLi_first = '<li><input type="checkbox" onclick="setPayObjectPayAmount();" name="payObjectSerialNo" value="';
	var uiLi_list = ' 	賠付金額: <input  type="text" name="payObjectPayAmount" onblur="setPayObjectPayAmount();" value="" class="common" style="width:100px"/></li>';
	var uiLi = "";
	$.each($.find("input[name='prpLpayObjectInfoSerialNo']"), function (i, n) {
		if (i > 0) {
			uiLi += uiLi_first + i + '" />賠付對象' + i + uiLi_list;
		}
	});
	if (uiLi == "") {
		uiLi = "沒有賠款給付對象訊息，請錄入賠款給付對象。";
	}
	var odiv = document.getElementById("prpLPayObjectinfo");
	if (odiv.style.display != "none") {
		odiv.style.display = "none";
	}
	var uiodiv = document.getElementById("uLprpLPayObjectinfo");
	uiodiv.innerHTML = uiLi;
}

/***
 * 计算并判断 （减去预赔金额之后）
 * 1、每个個赔付對象理赔金額 == 所有受害人对该赔付对象的赔款金额之和（多赔一）
 * 2、每个受害人的赔付金额总和 == 该受害人对所有赔付对象的赔款金额之和（一赔多）
 * @returns {Boolean}
 */

function sumPrpLpayObjectInfopayAmount() {
	var prpLpayObjectInfopayAmount = document.getElementsByName("prpLpayObjectInfopayAmount");
	var sumAmount = 0;
	for (var i = 1; i < prpLpayObjectInfopayAmount.length; i++) {
 		if(!jQuery.isNumeric(prpLpayObjectInfopayAmount[i].value)){
			prpLpayObjectInfopayAmount[i].value = "0";
		}
		sumAmount += parseFloat(prpLpayObjectInfopayAmount[i].value);
	}
	var prpLcompensateSumThisPaid = parseFloat(document.getElementsByName("prpLcompensateSumThisPaid")[0].value); //本车陪付金额
	var prpLcompensateSumPrePaid = $.find(":input[name='prpLcompensateSumPrePaid']")[0].value; //预陪金额
	//去掉预陪的金额
	if (null != prpLcompensateSumPrePaid && "" != prpLcompensateSumPrePaid) {
		prpLcompensateSumPrePaid = parseFloat(prpLcompensateSumPrePaid);
	} else {
		prpLcompensateSumPrePaid = 0;
	}
	if (prpLcompensateSumThisPaid != sumAmount && prpLcompensateSumThisPaid != sumAmount + prpLcompensateSumPrePaid) {
		alert("多個支付對象賠付金額之和不等於合計賠付金額，請重新錄入！");
		return false;
	}
	var message = "";
	var $prpLpayObjectInfoPayAmount = $.find(":input[name='prpLpayObjectInfoPayAmount']"); //賠付對象
	var serialNo = $prpLpayObjectInfoPayAmount.length;
	var payAmount = new Array(serialNo);
	for (var i = 0; i < payAmount.length; i++) {
		payAmount[i] = 0;
	}
	var $prpLpersonLossPayObjectSerialNo = $.find(":input[name='prpLpersonLossPayObjectSerialNo']"); //賠付對象
	var prpLpersonCommerceSumRealPay1 = $.find(":input[name='prpLpersonCommerceSumRealPay1']"); //賠付對象
	//mantis： CLM0188，處理人員：CD078，需求單編號：CLM0188 新核心-強制險理賠費用可單結費用資料-START
	//20240422 SIT/UAT/PROD 與正式區PROD 程式有差距，故將此區域覆蓋回SIT修正後，再與正式PROD程式 同步
	$.each($prpLpersonLossPayObjectSerialNo, function (i, n) {
		if (i > 0) {
			if (n.value != "") {
				var payObjectValue = (n.value).split(";");
				var prpLpersonAmount = 0;
				for (var j = 0; j < payObjectValue.length; j++) {
					var payObjectValueTemp = payObjectValue[j].split(":");
					var payObjectSerialNo = parseInt(payObjectValueTemp[0]);
					var payObjectAmount = parseFloat(payObjectValueTemp[1]);
					payAmount[payObjectSerialNo] += payObjectAmount;
					prpLpersonAmount += payObjectAmount;
				}
				if (parseFloat(prpLpersonCommerceSumRealPay1[i].value) != prpLpersonAmount && parseFloat(prpLpersonCommerceSumRealPay1[i].value) != prpLpersonAmount + prpLcompensateSumPrePaid) {
					message += "第" + i + "筆受害人訊息支付金額不等，請重新輸入!\n";
				}
			} else {
				//	message += "第" + i + "筆受害人訊息支付對象信息沒有輸入!\n";
			}
		}
	});
	//mantis： CLM0188，處理人員：CD078，需求單編號：CLM0188 新核心-強制險理賠費用可單結費用資料-END
	$.each($prpLpayObjectInfoPayAmount, function (i, n) {
		if (i > 0) {
     		 if(jQuery.isNumeric(n.value)){
				if (payAmount[i] != parseFloat(n.value)) {
					message += "第" + i + "筆賠付對象訊息賠付金額與需要支付金額不等,請重新輸入!\n";
				}
			} else {
				message += "第" + i + "筆賠付對象訊息沒有輸入賠付金額\n";
			}
		}
	});
	if (message.length > 0) {
		alert(message);
		return false;
	}
	return true;
}

//校验赔付对象序号是否存在

function checkPayObjectSerialNo(field) {
	if (isInteger(field.value)) {
		var index = parseInt(field.value);
		var $prpLpayObjectInfoPayAmount = $.find(":input[name='prpLpayObjectInfoPayAmount']"); //賠付對象
		if (index <= 0 || index >= $prpLpayObjectInfoPayAmount.length) {
			field.value = "";
			alert("妳輸入的賠付對象不存在，請重新輸入。");
			return false;
		}
		var payAmount = new Array($prpLpayObjectInfoPayAmount.length);
		for (var i = 0; i < payAmount.length; i++) {
			payAmount[i] = 0;
		}
		var $prpLpersonLossPayObjectSerialNo = $.find(":input[name='prpLpersonLossPayObjectSerialNo']"); //賠付對象
		var prpLpersonCommerceSumRealPay1 = $.find(":input[name='prpLpersonCommerceSumRealPay1']"); //賠付對象
		$.each($prpLpersonLossPayObjectSerialNo, function (i, n) {
			if (i > 0) {
				if (isInteger(n.value)) {
 					 if(jQuery.isNumeric(prpLpersonCommerceSumRealPay1[i].value)){
						payAmount[parseInt(n.value)] += parseFloat(prpLpersonCommerceSumRealPay1[i].value);
					}
				}
			}
		});
		$.each($prpLpayObjectInfoPayAmount, function (i, n) {
			if (i > 0) {
				n.value = payAmount[i];
			}
		});
	} else {
		field.value = "";
		alert("輸入的賠付對象不存在，請重新輸入。");
		return false;
	}
}
/**** 点击受害人賠付對象讯息,选择该受害人对每个赔付对象的赔付 begin**/
var prpLfieldIndex = 0;
var prpLfieldName = 0;

function setPrpObjectinfoSerialNo(field) {
	var odiv = document.getElementById("prpLPayObjectinfo");
	prpLfieldIndex = getElementOrder(field, document.forms[0]) - 1;
	prpLfieldName = field.name;
	var payObjectSerialNo = document.getElementsByName("payObjectSerialNo");
	var payObjectPayAmount = document.getElementsByName("payObjectPayAmount");
	for (var i = 0; i < payObjectSerialNo.length; i++) {
		payObjectSerialNo[i].checked = false;
		payObjectPayAmount[i].value = "";
	}
	if (field.value != "") {
		var payObjectValue = field.value.split(";");
		for (var i = 0; i < payObjectValue.length; i++) {
			var payObjectTemp = payObjectValue[i].split(":");
			if (payObjectSerialNo.length >= parseInt(payObjectTemp[0])) {
				payObjectSerialNo[parseInt(payObjectTemp[0]) - 1].checked = true;
				payObjectPayAmount[parseInt(payObjectTemp[0]) - 1].value = payObjectTemp[1];
			}
		}
	}
	odiv.style.left = findPosX(field) - 3;
	odiv.style.top = findPosY(field) - 5;
	odiv.style.height = payObjectSerialNo.length == 0 ? 1 * 10 : payObjectSerialNo.length * 10;
	odiv.style.display = "block";
}

function setPayObjectPayAmount() {
	var payObjectSerialNo = document.getElementsByName("payObjectSerialNo");
	var payObjectPayAmount = document.getElementsByName("payObjectPayAmount");

	var payObjectValue = "";
	for (var i = 0; i < payObjectSerialNo.length; i++) {
		if (payObjectSerialNo[i].checked) {
			payObjectValue += payObjectSerialNo[i].value;
 			if (jQuery.isNumeric(payObjectPayAmount[i].value)) {
				payObjectValue += ":" + payObjectPayAmount[i].value;
			} else {
				payObjectValue += ":0";
			}
			payObjectValue += ";";
		}
	}
	if (payObjectValue != "") {
		payObjectValue = payObjectValue.substring(0, payObjectValue.length - 1);
	}
	document.getElementsByName(prpLfieldName)[prpLfieldIndex].value = payObjectValue;
	setPrpLpayObjectInfoPayAmount();
}

function setPrpLpayObjectInfoPayAmount() {
	var prpLpayObjectInfoPayAmount = document.getElementsByName("prpLpayObjectInfoPayAmount");
	for (var i = 0; i < prpLpayObjectInfoPayAmount.length; i++) {
		prpLpayObjectInfoPayAmount[i].value = 0;
	}
	var prpLpersonLossPayObjectSerialNo = document.getElementsByName("prpLpersonLossPayObjectSerialNo");
	for (var i = 0; i < prpLpersonLossPayObjectSerialNo.length; i++) {
		if (prpLpersonLossPayObjectSerialNo[i].value != "") {
			var payObjectValue = prpLpersonLossPayObjectSerialNo[i].value.split(";");
			for (var j = 0; j < payObjectValue.length; j++) {
				var payObjectTemp = payObjectValue[j].split(":");
				prpLpayObjectInfoPayAmount[parseInt(payObjectTemp[0])].value = parseFloat(payObjectTemp[1]) + parseFloat(prpLpayObjectInfoPayAmount[parseInt(payObjectTemp[0])].value);
				prpLpayObjectInfoPayAmount[parseInt(payObjectTemp[0])].readOnly=true;
				prpLpayObjectInfoPayAmount[parseInt(payObjectTemp[0])].className="readonly";
			}
		}

	}
}
/**** 点击受害人賠付對象讯息,选择该受害人对每个赔付对象的赔付 end**/