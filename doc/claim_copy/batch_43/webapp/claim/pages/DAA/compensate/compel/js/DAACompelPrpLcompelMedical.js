/**
 * 刪除收據資料
 */
function deletePrpLcompelMedical(){
	var $cbx = $("#PrpLcompelMedical").find(":checked[name='cbx']");
	if ($cbx.length == 0) {
		alert("請選擇要刪除的費用收據資料！");
		return;
	}
	$cbx.each(function(){// 刪除選中行
		$(this).closest("tr[name='prpLcompelMedicalObject']").remove();
	});
	// 編號重排
	var $serialNo = $("#PrpLcompelMedical").find(":input[name='SerialNo']");
	$serialNo.each(function(i,e){
		e.value = i + 1;
	});
	if($serialNo.length>0){
		setSumAll();
	}else{
		$(":input[id^='SumFee']").each(function(){
			this.value="";
		});
	}
	
}
/**
 * 統計單張收據的費用金額 統計A01~A04 費用金額之和 到FeeA
 */
function setFeeA(tr){
	var SumFee = 0;// 總費用
	var FeeA;// 合計域
	$(tr).find(":input[name^='Fee']").each(function(){
		if (this.name == 'FeeHealthPoints' || this.name == 'FeeHealthAmount') {
			return true;
		}
		if (this.name == 'FeeA') {
			FeeA = this;
			return true;
		}
		if (this.value != "") {
			SumFee += parseFloat(this.value);
		}
	});
	if (FeeA) {
		FeeA.value = (SumFee > 0 ? Math.round(SumFee) : "");
		setSumFee(FeeA.name);
	}
}

/**
 * 統計每項費用金額之和
 */
function setSumFee(fname){
	var SumFee = 0;
	$("#PrpLcompelMedical").find(":input[name='" + fname + "']").each(function(){
		if (this.value != "") {
			SumFee += parseFloat(this.value);
		}
	});
	// 總費用
	$("#Sum" + fname).val((SumFee > 0 ? Math.round(SumFee) : ""));
	if (/^FeeA029[abcz]/.test(fname)) {// 统计 A029
		setSumFeeA029();
	}
}
/**
 * 統計A029各項費用金額之和
 */
function setSumFeeA029(){
	var SumFee = 0;
	$(":input[id^='SumFeeA029']").not("#SumFeeA029").each(function(){
		if (this.value != "") {
			SumFee += parseFloat(this.value);
		}
	});
	$("#SumFeeA029").val((SumFee > 0 ? Math.round(SumFee) : ""));
}


/***
 * 統計彙總所有A01-A04
 */
function setSumFeeA01234(){
	var SumFee = 0;
	$(":input[id^='lastSumFeeA0']").each(function(){
		if(this.id == "lastSumFeeA029" || this.id == "lastSumFeeA01234"){
			return true;
		}
		if (this.value != "") {
			SumFee += parseFloat(this.value);
		}
	});
	$("#SumFeeA01234").val((SumFee > 0 ? Math.round(SumFee) : ""));
}

/**
 * 總計
 */
function setSumAll(){
	var fObject;// 用来统计总费用的行资料
	$("#PrpLcompelMedical").find("tr[name='prpLcompelMedicalObject']").each(function(){
		setFeeA(this);
		fObject = this;
	});
	$(fObject).find(":input[name^='Fee']").each(function(){
		setSumFee(this.name);
	});
	setSumFeeA029();
	setSumFeeA01234();
}
function savePrpLcompelMedical(actionType,status){
	var checkArray = new Array();
	var medArray = new Array();
	var $prpLpayObjectInfoPaycodeType = document.getElementsByName("prpLpayObjectInfoPaycodeType");
	var $startDate = $("#PrpLcompelMedical").find(":input[name='StartDate']");
	var $damageDate = document.getElementsByName("damageDate");

	var flag = true;
	$("#PrpLcompelMedical").find("tr[name='prpLcompelMedicalObject']").each(function(i,tr){
		checkArray.push(i + 1);
		$(tr).find(":input[name^='Fee']").each(function(j,e){
			if (this.value != "" && parseFloat(this.value) != 0) {
				checkArray.pop();
				medArray.push(i);
			}
		});
	});
	if (checkArray.length > 0) {
		alert("第[" + checkArray.join("、") + "]筆醫療費用收據資料無任何金額訊息，請錄入或刪除！");
		return;
	}
	// mantis： CLM0046 ，處理人員：BK007 蘇哲，需求單編號：CLM0046強制險醫療明細提醒同一身分證號與日期重複時有提醒與之前重複的賠次
	var vMsg="";
	$startDate.each(function(i,e){
		if(e.value == ""||e.length < 0){
			flag = false;
			alert("請輸入'就診日期（起日）再進行保存或提交'");
			return false;
		}else if(e.value<$damageDate[0].value){
			flag = false;
			alert("就診日期（起日）不能小於出險日期，請修改後再進行保存或提交");
			e.value = "";
			return false;
		}
		/*
		 * mantis： CLM0046 ，處理人員：BK007 蘇哲，需求單編號：CLM0046強制險醫療明細提醒同一身分證號與日期重複時有提醒與之前重複的賠次-start
		 * 處理過程：
		 *  PrpLcompensateService 新增 verifyPrpLcompelMedical 方法，提供 verifyMedicalDetail Ajax 使用，驗證同一身分證號與日期重複資訊
		 *  DAACompelPrpLcompelMedical.js 提供前台驗證，有輸入時提醒與送出驗證及提醒
		 *  ClaimPrintAction 列印功能新增備註頁面(prpLcompelMedicalComment.jrxml)及調整 項目渲染時的提示 (prpLcompelMedical_subreport0.jrxml)
		 */
		var verifyMsg = verifyDate(e,true);
		if(verifyMsg){
			vMsg = vMsg + verifyMsg + "\r\n";
		}
		/*
		 * mantis： CLM0046 ，處理人員：BK007 蘇哲，需求單編號：CLM0046強制險醫療明細提醒同一身分證號與日期重複時有提醒與之前重複的賠次 -end
		 */
	});
	/*
	 * mantis： CLM0046 ，處理人員：BK007 蘇哲，需求單編號：CLM0046強制險醫療明細提醒同一身分證號與日期重複時有提醒與之前重複的賠次-start
	 * 處理過程：
	 *  PrpLcompensateService 新增 verifyPrpLcompelMedical 方法，提供 verifyMedicalDetail Ajax 使用，驗證同一身分證號與日期重複資訊
	 *  DAACompelPrpLcompelMedical.js 提供前台驗證，有輸入時提醒與送出驗證及提醒
	 *  ClaimPrintAction 列印功能新增備註頁面(prpLcompelMedicalComment.jrxml)及調整 項目渲染時的提示 (prpLcompelMedical_subreport0.jrxml)
	 */
	if(vMsg != ""){
		alert(vMsg);
		return false;
	}
	/*
	 * mantis： CLM0046 ，處理人員：BK007 蘇哲，需求單編號：CLM0046強制險醫療明細提醒同一身分證號與日期重複時有提醒與之前重複的賠次 -end
	 */
	if(!flag){
		return false;
	}
	if (medArray.length == 0 && $prpLpayObjectInfoPaycodeType[0].value == "1") {
		alert("賠付代號為“一般賠案”時，必須錄入要存儲的醫療費用收據資料！");
		return;
	}
	if (actionType == "ADD" || actionType == "EDIT") {// 強制險理算計算書處理
		//依項目檢查 上限
		var errorMessage = "";
		var sumFeeA024 =(isNaN(parseFloat(document.getElementById("lastSumFeeA024").value)) ? 0:parseFloat(document.getElementById("lastSumFeeA024").value)) + parseFloat(document.getElementById("SumFeeA024").value);
		var sumFeeA026 =(isNaN(parseFloat(document.getElementById("lastSumFeeA026").value))? 0:parseFloat(document.getElementById("lastSumFeeA026").value)) + parseFloat(document.getElementById("SumFeeA026").value);
		var sumFeeA03 = (isNaN(parseFloat(document.getElementById("lastSumFeeA03").value))? 0:parseFloat(document.getElementById("lastSumFeeA03").value))+ parseFloat(document.getElementById("SumFeeA03").value);
		var sumFeeA04 = (isNaN(parseFloat(document.getElementById("lastSumFeeA04").value))? 0:parseFloat(document.getElementById("lastSumFeeA04").value)) + parseFloat(document.getElementById("SumFeeA04").value);
		var sumFeeA =  (isNaN(parseFloat(document.getElementById("SumFeeA").value))? 0:parseFloat(document.getElementById("SumFeeA").value)) + (isNaN(parseFloat(document.getElementById("SumFeeA01234").value))? 0:parseFloat(document.getElementById("SumFeeA01234").value));

		//mantis：CLM0296 ，處理人員：DP0713，需求單編號：新核心-調整醫療給付費用明細費用放寬卡控限額 START
		var hideFeeA024 = $(':input[name="HideFeeA024"]').val() || (typeof FeeA024 !== 'undefined' ? FeeA024 : '');
		var hideFeeA026 = $(':input[name="HideFeeA026"]').val() || (typeof FeeA026 !== 'undefined' ? FeeA026 : '');
		var hideFeeA03 = $(':input[name="HideFeeA03"]').val() || (typeof FeeA03 !== 'undefined' ? FeeA03 : '');
		var hideFeeA04 = $(':input[name="HideFeeA04"]').val() || (typeof FeeA04 !== 'undefined' ? FeeA04 : '');
		var hideFeeA00 = $(':input[name="HideFeeA00"]').val() || (typeof FeeA00 !== 'undefined' ? FeeA00 : '');
		//alert("sumFeeA024:"+sumFeeA024+"hideFeeA024:"+hideFeeA024+"/FeeA024:"+FeeA024);
		if(sumFeeA024>parseFloat(hideFeeA024,10)){
			errorMessage += "費用A024上限為"+hideFeeA024+"萬元\n";
		}
		//alert("sumFeeA026:"+sumFeeA026+"/hideFeeA026:"+hideFeeA026+"/FeeA026:"+FeeA026);
		if(sumFeeA026>parseFloat(hideFeeA026,10)){
			errorMessage += "費用A026上限為"+hideFeeA026+"元\n";
		}
		//alert("sumFeeA03:"+sumFeeA03+"/hideFeeA03:"+hideFeeA03+"/FeeA03:"+FeeA03);
		if(sumFeeA03>parseFloat(hideFeeA03,10)){
			errorMessage += "費用A03上限為"+hideFeeA03+"萬元\n";
		}
		//alert("sumFeeA04:"+sumFeeA04+"/hideFeeA04:"+hideFeeA04+"/FeeA04:"+FeeA04);
		if(sumFeeA04>parseFloat(hideFeeA04,10)){
			errorMessage += "費用A04上限為"+hideFeeA04+"元\n";
		}
		//alert("sumFeeA:"+sumFeeA+"/hideFeeA00:"+hideFeeA00+"/FeeA00:"+FeeA00);
		if(sumFeeA>parseFloat(hideFeeA00,10)){
			errorMessage += "費用A00上限為"+hideFeeA00+"元\n";
		}
		//mantis：CLM0296 ，處理人員：DP0713，需求單編號：新核心-調整醫療給付費用明細費用放寬卡控限額 END
		if(errorMessage.length>0){
			alert(errorMessage+"已超過限額，請修改後再提交");
			return false;
		}
		saveReturn(actionType, status);
	} else if (actionType == "AMEND") {// 醫療費用收據資料補錄編輯處理
		if(status == "4"){
			if($prpLpayObjectInfoPaycodeType[0].value == "1"){
				if (medArray.length == 0) {
					var identifyNumber = document.getElementById("identifyNumber").value;
					var personName = document.getElementsByName("personName")[0].value;
					alert("受害人"+personName +"（"+identifyNumber + "）未錄入醫療費用收據資料！請錄入後再提交！\n");
					return;
				}
			}
		}
		checkSave(actionType, status);
	}
}
/**
 * 理算計算書處理保存收據資料，并將各項費用彙總帶入理算畫面
 * @param actionType
 * @param status
 */
function saveReturn(actionType,status){
	var result = false;
	var codes;
	var personName = encodeURI(encodeURI(document.getElementsByName("personName")[0].value)) ;
	$.ajax({
		type : "POST",
		url : contextRootPath + "/compensate/insertMedicalDetail.do?actionType=" + actionType + "&status=" + status + "&personName=" + personName,
		data : $("form").serialize(),
		async : false,
		cache : false,
		dataType : "json",
		success : function(data){
			if (data.success) {
				alert("存儲成功！");
				result = true;
				codes = data.codes;
			} else {
				alert(data.msg);
			}
		}
	});
	if (result) {// 存儲成功，帶入理算畫面
		var obj = new Object();
		obj.PersonFee = new Array();
		obj.SumFee = 0;// 費用加總
		obj.HealthPoints = 0;
		obj.HealthAmount = 0;
		$(":input[id^='SumFee']").not("#SumFeeA01234,#SumFeeA,#SumFeeA029a,#SumFeeA029b,#SumFeeA029c,#SumFeeA029z,[value='']").each(function(){
			var FeeName = this.id.replace("SumFee", "");
			var Fee = this.value;
			if (FeeName == 'HealthPoints') {
				obj.HealthPoints = Fee;
			} else if (FeeName == 'HealthAmount') {
				obj.HealthAmount = Fee;
			} else {
				obj.SumFee += parseFloat(Fee);
				$.each(codes, function(i,e){
					if (FeeName == e.id.codeCode) {
						var fee = new Object();
						fee.MedicalDetailCode = e.id.codeCode;
						fee.MedicalDetailName = e.codeCName;
						fee.MedicalSumLoss = Fee;
						fee.MedicalSumDefPay = Fee;
						obj.PersonFee.push(fee);
						return false;
					}
				});
			}
		});
		window.returnValue = obj;
		window.close();
	}
}
/**
 * 收據資料補錄編輯
 * @param actionType
 * @param status
 */
function checkSave(actionType,status){
	if (status == "4") {// 提交時校驗收據的各項費用彙總是否與計算書該受害人賠付情況一致
		var compensateNo = $("#compensateNo").val();
		var identifyNumber = $("#identifyNumber").val();
		var personNo = $("#personNo").val();
		var resultData;
		$.ajax({
			type : "POST",
			url : contextRootPath + "/compensate/checkMedicalDetail.do?compensateNo=" + compensateNo + "&identifyNumber=" + identifyNumber + "&personNo=" + personNo,
			async : false,
			cache : true,
			dataType : "json",
			success : function(data){
				if (data.success) {
					resultData = data;
				} else {
					alert(data.msg);
				}
			}
		});
		if(resultData){
			var FeeCode = resultData.FeeCode;
			var FeeRealPay = resultData.FeeRealPay;//計算書已核賠賠付的
			var currPay = new Array(0,0,0,0,0,0,0,0,0,0);//本次收據錄入的各項費用彙總
			var currHealthPoints = 0;
			var currHealthAmount = 0;
			$(":input[id^='SumFee']").not("#SumFeeA01234,#SumFeeA,#SumFeeA029a,#SumFeeA029b,#SumFeeA029c,#SumFeeA029z,[value='']").each(function(){
				var FeeName = this.id.replace("SumFee", "");
				var Fee = this.value;
				if (FeeName == 'HealthPoints') {
					currHealthPoints = parseFloat(Fee);
				} else if (FeeName == 'HealthAmount') {
					obj.HealthAmount = parseFloat(Fee);
				} else {
					var index = $.inArray(FeeName , FeeCode);
					if(index >= 0){
						currPay[index] = parseFloat(Fee);
					}
				}
			});
			if(currHealthPoints == resultData.HealthPoints && currHealthAmount == resultData.HealthAmount ){
				var checkResult = true;
				$.each(FeeRealPay , function(i,e){
					if(e != currPay[i]){
						checkResult = false;
						return false;
					}
				});
				if(!checkResult){
					alert("當前收據資料各項醫療費用、是否以健保身份就醫 彙總結果與該受害人已賠付的各項醫療費用訊息不一致，只能暫存處理！");
					return;
				}
			}
		} else {
			return;
		}
	}
	$.ajax({
		type : "POST",
		url : contextRootPath + "/compensate/insertMedicalDetail.do?actionType=" + actionType + "&status=" + status,
		data : $("form").serialize(),
		async : false,
		cache : false,
		dataType : "json",
		success : function(data){
			if (data.success) {
				alert("存儲成功！");
				window.returnValue = data.status;
				if(status == "4"){
					window.close();
				}
			} else {
				alert(data.msg);
			}
		}
	});
}
/*
 * mantis： CLM0046 ，處理人員：BK007 蘇哲，需求單編號：CLM0046強制險醫療明細提醒同一身分證號與日期重複時有提醒與之前重複的賠次-start
 * 處理過程：
 *  PrpLcompensateService 新增 verifyPrpLcompelMedical 方法，提供 verifyMedicalDetail Ajax 使用，驗證同一身分證號與日期重複資訊
 *  DAACompelPrpLcompelMedical.js 提供前台驗證，有輸入時提醒與送出驗證及提醒
 *  ClaimPrintAction 列印功能新增備註頁面(prpLcompelMedicalComment.jrxml)及調整 項目渲染時的提示 (prpLcompelMedical_subreport0.jrxml)
 */
//驗證時間確認
function verifyDate(filed,isSaveAction){
	if(filed.value!=""){
		$parentTr = $(filed).parent("td").parent("tr");
		var compensateNo = $("#compensateNo").val();
		var identifyNumber = $("#identifyNumber").val();
		var serialNo = $parentTr.find(":input[name=SerialNo]").val();
		var $vMsg = $parentTr.find(":input[name=vMsg]");
		var saveAction = !(isSaveAction != true);
		if(saveAction){
			var collisionMsg = verifyDateAjax(compensateNo,identifyNumber,serialNo,filed.value);
			if(collisionMsg==""){
				$vMsg.val("");
				$vMsg.attr("readonly","readonly");
				$vMsg.addClass("readonly");
				$vMsg.removeClass("input");
			}else if($.trim($vMsg.val()) == "N" ){
				$vMsg.removeAttr("readonly");
				$vMsg.addClass("input");
				$vMsg.removeClass("readonly");
			}else{
				if(!$vMsg.hasClass("input")){
					$vMsg.removeAttr("readonly");
					$vMsg.addClass("input");
					$vMsg.removeClass("readonly");
				}
				return"編號["+serialNo+"] 在 "+collisionMsg+" 日期重複請確認";
			}
		}else{
			var collisionMsg = verifyDateAjax(compensateNo,identifyNumber,serialNo,filed.value);
			if(collisionMsg==""){
				$vMsg.val("");
				$vMsg.attr("readonly","readonly");
				$vMsg.addClass("readonly");
				$vMsg.removeClass("input");
			}else{
				alert("編號["+serialNo+"] 在 "+collisionMsg+" 日期重複請確認");
				$vMsg.removeAttr("readonly");
				$vMsg.addClass("input");
				$vMsg.removeClass("readonly");
			}
		}
		
	}
}
function verifyDateAjax(compensateNo,identifyNumber,serialNo,startDate){
	var collision = [];
	$("#PrpLcompelMedical").find(":input[name='StartDate']").each(function(i,e){
		if(serialNo != (i+1) && e.value == startDate){
			collision.push(compensateNo+":"+(i+1));
		}
	});
	var bMsg="";
	$.ajax({
		type : "POST",
		url : contextRootPath + "/compensate/verifyMedicalDetail.do",
		data : {
			compensateNo : compensateNo,
			identifyNumber : identifyNumber,
			serialNo : serialNo,
			startDate : startDate
		},
		async : false,
		cache : true,
		dataType : "json",
		success : function(data) {
			if (data.success) {
				bMsg = data.msg;
				if(data.msg){
					for (var j = 0; j < collision.length; j++) {
						if((data.msg).indexOf(collision[j])<0){
							bMsg = bMsg + "、" + collision[j];
						}
					}
				}else{
					bMsg=collision.join("、");
				}
			} else {
				alert(data.msg);
			}
		}
	});
	return bMsg;
}
/*
 * mantis： CLM0046 ，處理人員：BK007 蘇哲，需求單編號：CLM0046強制險醫療明細提醒同一身分證號與日期重複時有提醒與之前重複的賠次 -end
 */

//列印醫療給付費用明細
function printPrpLcompelMedical(){
	var healthHospitalize = document.getElementsByName("healthHospitalize");

	for(var i = 0,l=healthHospitalize.length;i<l;i++){
	    (function(i){
	    	healthHospitalize[i].disabled = "";
	    })(i);
	}
	fm.action = contextRootPath + "/compensate/printPrpLcompelMedical.do";
	fm.submit();
}