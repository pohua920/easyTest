/*****************************************************************************
 * DESC       ：追偿的脚本函数页面(车险类的)
 * AUTHOR     ：中科軟
 * CREATEDATE ：2006-12-19
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
			for (var index = 0; index < fm.all("buttonChargeDelete").length; index++) {
				fm.all("buttonChargeDelete")[index].disabled = true;
			}
		}
	}
	fm.buttonDriverInsert.disabled = true;
	//差异化
	fm.buttonPayPersonInfoInsert.disabled = true;
	if (fm.buttonPayPersonInfoDelete != undefined) {
		if (fm.all("buttonPayPersonInfoDelete").length == undefined) {
			fm.buttonPayPersonInfoDelete.disabled = true;
		} else {
			for (var index = 0; index < fm.all("buttonPayPersonInfoDelete").length; index++) {
				fm.all("buttonPayPersonInfoDelete")[index].disabled = true;
			}
		}
	}
	//end
}


function doCertifyDirect(businessNo, nodeType) {
	window.open("/claim/certifyBeforeEdit.do?RegistNo=" + businessNo + "&editType=CertifyDirect&nodeType=" + nodeType, "winName", "resizable=0,scrollbars=1,width=800,height=600");
}



function saveForm() {
	var prpLchargeKindCodeList = document.getElementsByName("prpLchargeKindCode"); //险别代码
	var prpLchargeKindNameList = document.getElementsByName("prpLchargeKindName"); //险别名称
	var prpLchargeChargeCodeList = document.getElementsByName("prpLchargeChargeCode"); //费用名称
	var prpLchargeOwnerNameList = document.getElementsByName("prpLchargeOwnerName"); //賠付對象
	var prpLchargeOwnerShipList = document.getElementsByName("prpLchargeOwnerShip"); //费用支付方式
	var prpLchargeUniformNoList = document.getElementsByName("prpLchargeUniformNo"); //ID/統一編號
	var prpLchargeBankCodeList = document.getElementsByName("prpLchargeBankCode"); //總行代號
	var prpLchargeBankNameList = document.getElementsByName("prpLchargeBankName"); //總行名稱
	var prpLchargeAccountCodeList = document.getElementsByName("prpLchargeAccountCode"); //匯款帳號
	var prpLchargeCustomBankCodeList = document.getElementsByName("prpLchargeCustomBankCode"); //分行代號
	var prpLchargeCustomBankNameList = document.getElementsByName("prpLchargeCustomBankName"); //分行名稱
	var prpLchargeAreaCodeList = document.getElementsByName("prpLchargeAreaCode"); //郵遞區號
	var prpLchargeCourierAddressList = document.getElementsByName("prpLchargeCourierAddress"); //郵遞地址
	var prpLchargeCertificateCodeList = document.getElementsByName("prpLchargeCertificateCode"); //證件類型
	
		for (var i = 1; i < prpLchargeOwnerNameList.length; i++) {
			var tempOwnerShip = prpLchargeOwnerShipList[i].value;
	        //mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 START
			if(undefined!=prpLchargeAreaCodeList[i] && null!=prpLchargeAreaCodeList[i]){
		        var oAreaCode2 = trim(prpLchargeAreaCodeList[i].value);
		        var areaCode2 = trim(prpLchargeAreaCodeList[i].value).replace(/[^\d]/g,'');
		        if(oAreaCode2.length > 3){
		        	alert("第 " + (i) + " 条費用資訊‘郵遞區號’ 長度超過3位數!");
		        	return false; //跳出each
		        } else
		        if(oAreaCode2 != areaCode2){
		        	alert("第 " + (i) + " 条費用資訊‘郵遞區號’ 只能輸入數值!");
		        	return false; //跳出each
		        } 
			}
	        //mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 END
			if (prpLchargeKindCodeList[i].value == '' || prpLchargeKindNameList[i].value == '') {
				alert("第 " + (i) + " 条費用資訊‘險別代碼’、‘險別名稱’必須輸入!");
				return false; //跳出each
			} else if (prpLchargeChargeCodeList[i].value == '') {
				alert("第 " + (i) + " 条費用資訊‘費用名稱’必須輸入!");
				return false; //跳出each
			} else if (trim(prpLchargeOwnerNameList[i].value) == '') {
				alert("第 " + (i) + " 条費用資訊‘賠付對象’必須輸入!");
				return false; //跳出each
			} else if (trim(prpLchargeUniformNoList[i].value) == '') {
				alert("第 " + (i) + " 条費用資訊‘統一編號’必須輸入!");
				return false; //跳出each
			} else if (tempOwnerShip == "B" && (trim(prpLchargeAccountCodeList[i].value) == '' || trim(prpLchargeBankCodeList[i].value) == '' || trim(prpLchargeBankNameList[i].value) == '' || trim(prpLchargeCustomBankCodeList[i].value) == '' || trim(prpLchargeCustomBankNameList[i].value) == '')) {
				alert("第 " + (i) + " 条費用支付方式爲匯款，必須輸入費用支付帳戶資訊!");
				return false;
			} else if (trim(prpLchargeAreaCodeList[i].value) == '' || trim(prpLchargeCourierAddressList[i].value) == '') {
				alert("第 " + (i) + " 条費用資訊‘郵遞區號’、‘郵遞地址’必須輸入!");
				return false; //跳出each
			} else if (prpLchargeCertificateCodeList[i].value == "01" && !checkIdentifyNumber(fm.prpLchargeUniformNo[i].value, '9')) {
				alert("請爲第" + i + "條追償費用錄入正確的身份證號");
				return false;
			} else if (prpLchargeCertificateCodeList[i].value == "02" && !checkUniformNo(fm.prpLchargeUniformNo[i].value)) {
				alert("請爲第" + i + "條追償費用錄入正確的統一編號");
				return false;
			}
		}
	var prpLpayObjectInfoOwnerShipList = document.getElementsByName("prpLpayObjectInfoOwnerShip"); //标的损失赔款支付方式
//	if (prpLpayObjectInfoOwnerShipList.length <= 1) {
//		alert("必須輸入至少一條追償收入訊息!");
//		return false;
//	}
	var prpLpayObjectInfoOwnerNameList = document.getElementsByName("prpLpayObjectInfoOwnerName"); //賠付對象
	var prpLpayObjectInfoPaymentKindList = document.getElementsByName("prpLpayObjectInfoPaymentKind"); //費用類型
	var prpLpayObjectInfoUniformNoList = document.getElementsByName("prpLpayObjectInfoUniformNo"); //ID/統一編號
	var prpLpayObjectInfoPayAmountList = document.getElementsByName("prpLpayObjectInfoPayAmount"); //理赔金

	var prpLpayObjectInfoBeneficiaryPhoneList = document.getElementsByName("prpLpayObjectInfoBeneficiaryPhone"); //受款人電話
	var prpLpayObjectInfoBankCodeList = document.getElementsByName("prpLpayObjectInfoBankCode"); //總行代號
	var prpLpayObjectInfoBankNameList = document.getElementsByName("prpLpayObjectInfoBankName"); //總行名稱
	var prpLpayObjectInfoAccountCodeList = document.getElementsByName("prpLpayObjectInfoAccountCode"); //匯款帳號
	var prpLpayObjectInfoCustomBankCodeList = document.getElementsByName("prpLpayObjectInfoCustomBankCode"); //分行代號
	var prpLpayObjectInfoCustomBankNameList = document.getElementsByName("prpLpayObjectInfoCustomBankName"); //分行名稱
	var prpLpayObjectInfoAreaCodeList = document.getElementsByName("prpLpayObjectInfoAreaCode"); //郵遞區號
	var prpLpayObjectInfoCourierAddressList = document.getElementsByName("prpLpayObjectInfoCourierAddress"); //郵遞地址
	var prpLpayObjectInfoCertificateCodeList = document.getElementsByName("prpLpayObjectInfoCertificateCode"); //證件類型

		for (var i = 1; i < prpLpayObjectInfoOwnerShipList.length; i++) {
	        //mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 START
			if(undefined!=prpLpayObjectInfoAreaCodeList[i] && null!=prpLpayObjectInfoAreaCodeList[i]){
		        var oAreaCode2 = trim(prpLpayObjectInfoAreaCodeList[i].value);
		        var areaCode2 = trim(prpLpayObjectInfoAreaCodeList[i].value).replace(/[^\d]/g,'');
		        if(oAreaCode2.length > 3){
		        	alert("收取對象 " + i + " 費用資訊‘郵遞區號’ 長度超過3位數!");
		        	return false; //跳出each
		        } else
		        if(oAreaCode2 != areaCode2){
		        	alert("收取對象 " + i + " 費用資訊‘郵遞區號’ 只能輸入數值!");
		        	return false; //跳出each
		        } 
			}
	        //mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 END
			if (trim(prpLpayObjectInfoOwnerNameList[i].value) == '') {
				alert("收取對象 " + i + " ‘收取對象’必須輸入!");
				return false; //跳出each
			}
			if ((trim(fm.prpLpayObjectInfoUniformNo[i].value) != '' && prpLpayObjectInfoCertificateCodeList[i].value == "01") && !checkIdentifyNumber(fm.prpLpayObjectInfoUniformNo[i].value, '9')) {
				alert("請爲 收取對象" + i + " 錄入正確的身份證號");
				return false;
			} else if ((trim(fm.prpLpayObjectInfoUniformNo[i].value) != '' && prpLpayObjectInfoCertificateCodeList[i].value == "02") && !checkUniformNo(fm.prpLpayObjectInfoUniformNo[i].value)) {
				alert("請爲 收取對象" + i + " 錄入正確的統一編號");
				return false;
			}
	    var prpLpayObjectInfoOwnerNameList = document.getElementsByName("prpLpayObjectInfoOwnerName");//賠付對象
	    var prpLpayObjectInfoPaymentKindList = document.getElementsByName("prpLpayObjectInfoPaymentKind");//費用類型
	    var prpLpayObjectInfoUniformNoList = document.getElementsByName("prpLpayObjectInfoUniformNo");//ID/統一編號
	    var prpLpayObjectInfoPayAmountList = document.getElementsByName("prpLpayObjectInfoPayAmount");//理赔金
	   
	    var prpLpayObjectInfoBeneficiaryPhoneList = document.getElementsByName("prpLpayObjectInfoBeneficiaryPhone");//受款人電話
	    var prpLpayObjectInfoBankCodeList = document.getElementsByName("prpLpayObjectInfoBankCode");//總行代號
	    var prpLpayObjectInfoBankNameList = document.getElementsByName("prpLpayObjectInfoBankName");//總行名稱
	    var prpLpayObjectInfoAccountCodeList = document.getElementsByName("prpLpayObjectInfoAccountCode");//匯款帳號
	    var prpLpayObjectInfoCustomBankCodeList = document.getElementsByName("prpLpayObjectInfoCustomBankCode");//分行代號
	    var prpLpayObjectInfoCustomBankNameList = document.getElementsByName("prpLpayObjectInfoCustomBankName");//分行名稱
	    var prpLpayObjectInfoAreaCodeList = document.getElementsByName("prpLpayObjectInfoAreaCode");//郵遞區號
	    var prpLpayObjectInfoCourierAddressList = document.getElementsByName("prpLpayObjectInfoCourierAddress");//郵遞地址
		var prpLpayObjectInfoCertificateCodeList = document.getElementsByName("prpLpayObjectInfoCertificateCode");//證件類型
		
		for(var i=1;i<prpLpayObjectInfoOwnerShipList.length;i++){
	        if(trim(prpLpayObjectInfoOwnerNameList[i].value)==''){
	        	alert("收取對象 "+i+" ‘收取對象’必須輸入!");
	            return false;//跳出each
	        }
	//        else if(prpLpayObjectInfoUniformNoList[i].value==''){
	//           alert("收取對象 "+i+" ‘統一編號’必須輸入!");
	//           return false;//跳出each
	//        }
	//        else if(prpLpayObjectInfoBeneficiaryPhoneList[i].value==''){
	//           alert("收取對象 "+i+" ‘受款人電話’必須輸入!");
	//           return false;//跳出each
	//        }
	//        else if(prpLpayObjectInfoOwnerShipList[i].value=='B' 
	//       		&& (trim(prpLpayObjectInfoBankCodeList[i].value)==''||trim(prpLpayObjectInfoBankNameList[i].value)==''
	//       			||trim(prpLpayObjectInfoAccountCodeList[i].value)==''||trim(prpLpayObjectInfoCustomBankCodeList[i].value)==''
	//       				||trim(prpLpayObjectInfoCustomBankNameList[i].value)=='')){
	//            alert("收取對象 "+i+" 追償款收取方式爲匯款，必須錄入帳戶訊息!");
	//            return false;//跳出each
	//        }
	//        else if(trim(prpLpayObjectInfoAreaCodeList[i].value)==''||trim(prpLpayObjectInfoCourierAddressList[i].value)==''){
	//            alert("收取對象 "+i+" ‘郵遞區號’、‘郵遞地址’必須輸入!");
	//            return false;//跳出each
	//        }
	        if((trim(fm.prpLpayObjectInfoUniformNo[i].value)!='' && prpLpayObjectInfoCertificateCodeList[i].value=="01") && !checkIdentifyNumber(fm.prpLpayObjectInfoUniformNo[i].value,'9')){
	        	alert("請爲 收取對象"+i+" 錄入正確的身份證號")
	        	return false;
	        }else if((trim(fm.prpLpayObjectInfoUniformNo[i].value)!='' && prpLpayObjectInfoCertificateCodeList[i].value=="02") && !checkUniformNo(fm.prpLpayObjectInfoUniformNo[i].value)){
	        	alert("請爲 收取對象"+i+" 錄入正確的統一編號")
	        	return false;
	        }
		}
		var count1 = document.getElementsByName("prpLlossSumRealPay").length;
		var count2 = document.getElementsByName("prpLchargeChargeAmount").length;
	//	if (count1 < 2 && count2 < 2) {
	//		errorMessage("請至少輸入壹條追償收入訊息或者追償費用訊息！");
	//		return false;
	//	} else 
		if (count1 > 0 || count2 > 0) {
			var sumPaidAll = 0.00;
			var sumFeeAll = 0.00;
			if (count1 > 0) {
				for (var i = 1; i < count1; i++) {
	//				if (fm.prpLlossSumRealPay[i].value == 0) {
	//					errorMessage("第" + i + "條追償收入的的金額爲零，請刪除！");
	//					return false;
	//				}
					sumPaidAll = parseFloat(sumPaidAll) + parseFloat(fm.prpLlossSumRealPay[i].value);
					if (fm.prpLlossKindCode[i].value.length < 1 || fm.prpLlossKindName[i].value.length < 1) {
						errorMessage("請將第" + i + "條追償收入的險別訊息輸入完整！");
						return false;
					}
					var prpLlossPayObjectSerialNo = fm.prpLlossPayObjectSerialNo[i].value;
					var payObjectPayAmount = prpLlossPayObjectSerialNo.split(";");
					var payObjectPayAmountTemp = 0;
					for (var j = 0; j < payObjectPayAmount.length; j++) {
						var payObject = payObjectPayAmount[j].split(":");
						payObjectPayAmountTemp += parseFloat(payObject[1]);
					}
					if (parseFloat(fm.prpLlossSumRealPay[i].value) != payObjectPayAmountTemp) {
						errorMessage("每一條追償收入的追償金額必須與收取對象金額相等！");
						return false;
					}
				}
			}
			if (count2 > 0) {
				for (var j = 1; j < count2; j++) {
	//				if (fm.prpLchargeChargeAmount[j].value == 0) {
	//					errorMessage("第" + (i - 1) + "條追償費用的的金額爲零，請刪除！");
	//					return false;
	//				}
					sumFeeAll += fm.prpLchargeChargeAmount[j].value;
					if (fm.prpLchargeChargeAmount[j].value > 0 && (fm.prpLchargeKindCode[j].value.length < 1 || fm.prpLchargeKindName[j].value.length < 1)) {
						errorMessage("請將第" + (i - 1) + "條追償費用的險別訊息輸入完整！");
						return false;
					}
					if (fm.prpLchargeChargeAmount[j].value > 0 && (fm.prpLchargeChargeCode[j].value.length < 1 || fm.prpLchargeChargeName[j].value.length < 1)) {
						errorMessage("請將第" + (i - 1) + "條追償費用的費用訊息輸入完整！");
						return false;
					}
				}
			}
//			if (sumPaidAll == 0 && sumFeeAll == 0) {
//				errorMessage("請至少輸入壹條金額不爲零的追償收入訊息或者追償費用訊息！");
//				return false;
//			}
			var prpLpayObjectInfoPayAmount = document.getElementsByName("prpLpayObjectInfoPayAmount");
			var prpLpayObjectInfoPayAmountAll = 0;
			for (var i = 1; i < prpLpayObjectInfoPayAmount.length; i++) {
				prpLpayObjectInfoPayAmountAll = parseFloat(prpLpayObjectInfoPayAmountAll) + parseFloat(prpLpayObjectInfoPayAmount[i].value);
			}
			if (parseFloat(sumPaidAll) != parseFloat(prpLpayObjectInfoPayAmountAll)) {
				errorMessage("追償收入金額總和與收取對象追償金額總和不相等！");
				return false;
			}
			var countPayObjectSerialNo = document.getElementsByName("prpLlossPayObjectSerialNo").length;
			if (countPayObjectSerialNo > 1) {
				for (var i = 1; i < countPayObjectSerialNo; i++) {
					if (fm.prpLlossPayObjectSerialNo[i].value < 1) {
						errorMessage("請將第" + i + "條追償收入的收取對象訊息輸入完整！");
						return false;
					}
				}
			}
	
		}
		}
		var prpLlossSumRealPay = document.getElementsByName("prpLlossSumRealPay");
		var prpLlossSumRealPaySum = 0;
		for(var i = 1; i < prpLlossSumRealPay.length; i ++){
			prpLlossSumRealPaySum = +prpLlossSumRealPay[i].value;
		}
		var prpLchargeChargeAmount = document.getElementsByName("prpLchargeChargeAmount");
		var prpLchargeChargeAmountSum = 0;
		for(var i = 1; i < prpLchargeChargeAmount.length; i ++){
			prpLchargeChargeAmountSum = +prpLchargeChargeAmount[i].value;
		}
		if(!confirm("案件最終  追償收入為：" + prpLlossSumRealPaySum + "，" + " 追償費用為：" + prpLchargeChargeAmountSum + "。" + "請確認！")){
			return false;
		}
		fm.buttonSave.disabled = true;
		fm.submit();
	}

function changePayForOtherFlag(flag) {
	if (flag == 1) {
		alert(i18n.replevy.recoveryInformation); // 从共方的追偿信息将送收付，请核赔通过後去收付系统尽心更後续处理！
		fm.isPayForOtherFlag.value = "1";
	} else {
		alert(i18n.replevy.totalInformation); //将只会把我放信息送给收入系统，页面展示的从共方信息仅供参考！
		fm.isPayForOtherFlag.value = "0";
	}
}
//重新计算页面金额

function calSumPaidAll() {
	var OldSumPaidAll = fm.OldSumPaidAll.value;
	var SumThisPaid = 0.00;
	var SumPaidAll = OldSumPaidAll;
	var count = getElementCount("prpLlossSumRealPay");
	for (var i = 1; i < count; i++) {
		SumThisPaid = parseFloat(SumThisPaid) + parseFloat(fm.prpLlossSumRealPay[i].value);
		SumPaidAll = parseFloat(SumPaidAll) + parseFloat(fm.prpLlossSumRealPay[i].value);
	}
	fm.SumThisPaid.value = SumThisPaid;
	fm.prpLcompensateSumDutyPaid.value = SumThisPaid;
	fm.SumPaidAll.value = SumPaidAll;
	//如果是主共或主联，则刷新分摊信息
	if (fm.coinsFlag.value == "1") {
		creatCoins();
		creatCoinsFlag();
		resetChangelossCharge();
	}
}
//重新计算页面金额

function calSumFeeAll() {
	var SumThisCharge = 0.00;
	var OldSumFeeAll = fm.OldSumFeeAll.value;
	var SumFeeAll = OldSumFeeAll;
	var count = getElementCount("prpLchargeChargeAmount");
	for (var i = 1; i < count; i++) {
		SumThisCharge = parseFloat(SumThisCharge) + parseFloat(fm.prpLchargeChargeAmount[i].value);
		SumFeeAll = parseFloat(SumFeeAll) + parseFloat(fm.prpLchargeChargeAmount[i].value);
	}
	fm.SumThisCharge.value = SumThisCharge;
	fm.SumFeeAll.value = SumFeeAll;
	//如果是主共或主联，则刷新分摊信息
	if (fm.coinsFlag.value == "1") {
		creatCoins();
		creatCoinsFlag();
		resetChangelossCharge();
	}
}

function creatCoins() {
	var countFlag = fm.countFlag.value;
	if (countFlag == '1') {
		deleteallRow1('Coins', 'Coins_Data');
	}
	if (isSameKindCode() == false) {
		return false;
	}
	var oldAction = fm.action;
	var oldTarget = fm.target;
	fm.action = "/claim/compensateCoins.do";
	fm.target = "fraCalculate";
	fm.submit();
	fm.action = oldAction;
	fm.target = oldTarget;
	return true;
}

function creatCoinsFlag(countFlag) {
	fm.countFlag.value = countFlag;
}

function isSameKindCode() {
	var count = getElementCount("prpLchargeSerialNo") - 1;
	if (count > 1) {

		for (var i = 1; i < count; i++) {

			if (fm.prpLchargeChargeCode[count].value == fm.prpLchargeChargeCode[i].value) {

				alert(i18n.compensate.sameCostNotEntry); //同种费用不能重复輸入!
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

function isSameKindCode() {
	var count = getElementCount("prpLchargeSerialNo") - 1;
	if (count > 1) {

		for (var i = 1; i < count; i++) {

			if (fm.prpLchargeChargeCode[count].value == fm.prpLchargeChargeCode[i].value) {

				alert(i18n.compensate.sameCostNotEntry); //同种费用不能重复輸入!
				return false;
			}
		}

	}
	return true
}

function undwrt() {
	fm.action = "/claim/replevySave.do?editType=UNDWRT";
	fm.submit();
	fm.buttonUndwrt.disabled = true;
}

function withdrawal() {
	fm.action = "/claim/replevySave.do?editType=WITHDRAWAL";
	fm.submit();
	fm.buttonWithdrawal.disabled = true;
}
//显示打印窗口

function print() {
	//add print liudaoping 2013-04-15
	//alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
	return false;
	if (fm.compensateNo.value.length < 1) {
		alert("请输入查询条件!");
		return false;
	}
	var pageWidth = screen.availWidth - 10;
	var pageHeight = screen.availHeight - 30;
	if (pageWidth < 100)
		pageWidth = 100;

	if (pageHeight < 100)
		pageHeight = 100;

	var strURL = "/claim/replevyBeforeQuery.do?editType=PRINT&compensateNo=" + fm.compensateNo.value;

	var newWindow = window.open(strURL, '追偿计算书列印', 'width=' + pageWidth + ',height=' + pageHeight + ',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');

	newWindow.focus();
	return newWindow;
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
	if (chargeCodeList[index] == null || chargeCodeList[index].value == "") {
		alert(i18n.replevy.nameCost); // 请选择费用名称
		return;
	} else {
		chargeCode = chargeCodeList[index].value;
		payObjectType = prpLchargePayObjectType[index].value;
		//add by liuwei at 2010-12-21 根据费用类型和支付类型的不同，要求带出外部机构或手工輸入支付对象 begin
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
			if (flag == 0) {
				code_CodeSelect(field, 'getReplevyPayObject', '0,1', 'Y', 'Y', chargeCode + "|" + payObjectType);
			} else {
				code_CodeSelect(field, 'getReplevyPayObject', '-1,0', 'Y', 'Y', chargeCode + "|" + payObjectType);
			}
			getExternAlagency(field, index);
		}
		//add by liuwei at 2010-12-21 根据费用类型和支付类型的不同，要求带出外部机构或手工輸入支付对象 end
	}
}

//add by liuwei 2010-12-22 修改费用代码是清空支付对象 begin

function clearPayObject(field) {
	var i = getElementOrder(field) - 1;
	var prpLchargePayObjectNameList = document.getElementsByName("prpLchargePayObjectName");
	var prpLchargePayObjectCodeList = document.getElementsByName("prpLchargePayObjectCode");
	prpLchargePayObjectNameList[i].value = "";
	prpLchargePayObjectCodeList[i].value = "";
}
//add by liuwei 2010-12-22 修改费用代码是清空支付对象 end

//add by liuwei at 2010-12-22 当手工輸入支付对象後，检查该对象是否存在银行帳号信息 begin

function getAccountByPayObjectName(field, payObjectName) {
	var order = getElementOrder(field) - 1;
	var submitStr = "AccountCode.do?actionType=SearchWithPayObjectName&ownerName=" + payObjectName + "&serialNo=" + order;
	window.open(submitStr, '', 'resizable=1,scrollbars=yes,overflow=scroll,width=600,height=600');
}
//add by liuwei at 2010-12-22 当手工輸入支付对象後，检查该对象是否存在银行帳号信息 end

//add by liuwei at 2010-12-22 修改费用代码或支付类型或支付对象时，清空相应的付款信息 begin

function clearPayment(field) {
	var i = getElementOrder(field) - 1;
	fm.prpLchargeSumRealPay[i].value = 0;
	fm.prpLchargeChargeAmount[i].value = 0;
	fm.prpLchargeAccountCode[i].value = "";
	fm.prpLchargeBankName[i].value = "";
	fm.prpLchargeBankCode[i].value = "";
	fm.prpLchargeOwnerNameCQ[i].value = "";
	calSumFeeAll();
}
//add by liuwei 2013-5-7,追偿客制化，start

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
//end

//add by liuwei at 2010-12-22 修改费用代码或支付类型或支付对象时，清空相应的付款信息 end

//add by luochang begin at 2010-06-13 当支付对象为外部机构时，自动带出外部机构的银行帳号

function getExternAlagency(field, index) {
	var inputObject = field;
	var outputObject;
	var ChargeCode = fm.prpLchargeChargeCode[index].value;
	var PayObjectType = fm.prpLchargePayObjectType[index].value;
	var PayObjectCode = fm.prpLchargePayObjectCode[index].value;
	if ((ChargeCode == "04" || ChargeCode == "05" || ChargeCode == "06" || ChargeCode == "07" || ChargeCode == "13" || ChargeCode == "15") && PayObjectType == "B" && PayObjectCode != "") {
		DWREngine.setAsync(false);
		dwrInvokeData("getExternAlagencyByStr", PayObjectCode, "rollbackExternAlagency", inputObject, outputObject);
		DWREngine.setAsync(true);
	} else {
		fm.prpLchargeOwnerShip[index].options[0].selected = true;
		fm.prpLchargeAccountCode[index].value = "";
		fm.prpLchargeBankName[index].value = "";
		fm.prpLchargeBankCode[index].value = "";
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
	fm.prpLchargeAccountCode[findex].value = prplexternalagencyDto.accountCode == null ? "" : prplexternalagencyDto.accountCode;
	fm.prpLchargeBankName[findex].value = prplexternalagencyDto.bankName == null ? "" : prplexternalagencyDto.bankName;
	fm.prpLchargeBankCode[findex].value = prplexternalagencyDto.bankCode == null ? "" : prplexternalagencyDto.bankCode;
	fm.prpLchargeCustomBankName[findex].value = prplexternalagencyDto.customBankName == null ? "" : prplexternalagencyDto.customBankName;
	fm.prpLchargeCertifiCateCode[findex].value = prplexternalagencyDto.certifiCateCode == null ? "" : prplexternalagencyDto.certifiCateCode;
	fm.prpLchargeOwnerName[findex].value = prplexternalagencyDto.ownerName == null ? "" : prplexternalagencyDto.ownerName;
	fm.prpLchargePhoneNo[findex].value = prplexternalagencyDto.ownerPhoneNo == null ? "" : prplexternalagencyDto.ownerPhoneNo;
	fm.prpLchargeAccountCurrency[findex].value = prplexternalagencyDto.accountCurrency == null ? "" : prplexternalagencyDto.accountCurrency;
	fm.prpLchargeAccountType[findex].value = prplexternalagencyDto.accountType == null ? "" : prplexternalagencyDto.accountType;
	if (prplexternalagencyDto.accountType == "1") {
		fm.prpLchargeAccountTypeShow[findex].value = "存折";
	} else if (prplexternalagencyDto.accountType == "2") {
		fm.prpLchargeAccountTypeShow[findex].value = "信用卡";
	} else if (prplexternalagencyDto.accountType == "3") {
		fm.prpLchargeAccountTypeShow[findex].value = "储值卡";
	} else if (prplexternalagencyDto.accountType == "4") {
		fm.prpLchargeAccountTypeShow[findex].value = "其他";
	} else {
		fm.prpLchargeAccountTypeShow[findex].value = "";
	}
	fm.buttonAddAcc[findex].disabled = false;

	undisablebutton();
}

//刘伟2013-6-2，验证賠付對象序号是否存在

function checkPayObjectSerialNo(field) {
	if (isInteger(field.value)) {
		var index = parseInt(field.value);
		var $prpLpayObjectInfoPayAmount = $.find(":input[name='prpLpayObjectInfoPayAmount']"); //賠付對象
		if (index <= 0 || index >= $prpLpayObjectInfoPayAmount.length) {
			field.value = "";
			alert("你輸入的收取對象不存在，請重新輸入。");
			return false;
		}
		var payAmount = new Array($prpLpayObjectInfoPayAmount.length);
		for (var i = 0; i < payAmount.length; i++) {
			payAmount[i] = 0;
		}
		var $prpLlossPayObjectSerialNo = $.find(":input[name='prpLlossPayObjectSerialNo']"); //賠付對象
		var $prpLlossSumRealPay = $.find(":input[name='prpLlossSumRealPay']"); //追償收入
		$.each($prpLlossPayObjectSerialNo, function (i, n) {
			if (i > 0) {
				if (isInteger(n.value)) {
					 if(jQuery.isNumeric($prpLlossSumRealPay[i].value)){
						payAmount[parseInt(n.value)] += parseFloat($prpLlossSumRealPay[i].value);
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
		alert("你輸入的收取對象不存在，請重新輸入。");
		return false;
	}
}

function calPayAmount() {
	var $prpLlossPayObjectSerialNo = $.find(":input[name='prpLlossPayObjectSerialNo']"); //賠付對象
	var $prpLlossSumRealPay = $.find(":input[name='prpLlossSumRealPay']"); //追償收入
	var $prpLpayObjectInfoPayAmount = $.find(":input[name='prpLpayObjectInfoPayAmount']"); //賠付對象
	var payAmount = new Array($prpLpayObjectInfoPayAmount.length);
	for (var i = 0; i < payAmount.length; i++) {
		payAmount[i] = 0;
	}
	$.each($prpLlossPayObjectSerialNo, function (i, n) {
		if (i > 0) {
			if (isInteger(n.value)) {
				 if(jQuery.isNumeric($prpLlossSumRealPay[i].value)){
					payAmount[parseInt(n.value)] += parseFloat($prpLlossSumRealPay[i].value);
				}
			}
		}
	});
	$.each($prpLpayObjectInfoPayAmount, function (i, n) {
		if (i > 0) {
			n.value = payAmount[i];
		}
	});
}

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
	odiv.style.left = findPosX(field) - 100;
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
	var prpLpersonLossPayObjectSerialNo = document.getElementsByName("prpLlossPayObjectSerialNo");
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