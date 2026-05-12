 /**********************车物损******************/
 //自负额、自负额比例不许下调校验

 function checkLossDeductibleRateNew(field) {
 	if (1 != 1 && isChange(field) && parseFloat(field.value) < parseFloat($(field).data(field.name))) { //暂时不限制，加条件1!=1
 		recoveryData(field);
 		return alertMessage(field, $(field).attr("title") + "不允許下調");
 	}
 	return true;
 }

 //车物损理赔金计算 change事件

 function calRealpayNew(field) {
 	if (isChange(field)) {
 		var $curr = $(field);
 		var $prpLlossObject = $(field).parents("tr[name='prpLlossObject']");
 		var kindCode = $prpLlossObject.find(":input[name='prpLlossDtoKindCode']").val(); //险别
 		var kindName = $prpLlossObject.find(":input[name='prpLlossDtoKindName']").val(); //险别
 		var sumDefPay = $prpLlossObject.find(":input[name='prpLlossDtoSumDefPay']").val(); //核定赔偿
 		var compelPay = $prpLlossObject.find(":input[name='prpLlossDtoCompelPay']").val(); //强制险给付金额
 		var sumRest = $prpLlossObject.find(":input[name='prpLlossDtoSumRest']").val(); //剔除金额、残值
 		var depreRate = $prpLlossObject.find(":input[name='prpLlossDtoDepreRate']").val(); //折旧率
 		var dutyDeductibleRate = $prpLlossObject.find(":input[name='prpLlossDtoDutyDeductibleRate']").val(); //自负额比例
 		var deductible = $prpLlossObject.find(":input[name='prpLlossDtoDeductible']").val(); //自负额
 		var indemnityDutyRate = $prpLlossObject.find(":input[name='prpLlossDtoIndemnityDutyRate']").val(); //肇事責任比率
 		var policyNo = $(":input[name='prpLdangerPolicyNo']").val(); //保单号;
 		if (kindCode != '' && sumDefPay != '' && !isNaN(sumDefPay)) { //核定赔偿为0，不继续下去

 	 		//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 START
 			//車損、物損賠付資訊
 	 		if("31"==kindCode || "32"==kindCode){
 	 			cleanE3E9();
 	 		}
 	 		//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 END 
 	 		//mantis： CLM0166，處理人員：DP0713，需求單編號：車體新商品上線險別0Y START
 	 		if("07"==kindCode){
 	 			clean07();
 	 		}
 	 		//mantis： CLM0166，處理人員：DP0713，需求單編號：車體新商品上線險別0Y END
 	 		
 			var $sumRealPay = $prpLlossObject.find(":input[name='prpLlossDtoSumRealPay']");
 			if (parseFloat(sumDefPay) != 0) {
 				$.getJSON(contextRootPath + "/compensate/calPrpLlossRealpay.do", {
 						policyNo: policyNo,
 						kindCode: kindCode,
 						kindName: encodeURI(kindName),
 						sumDefPay: sumDefPay,
 						compelPay: compelPay,
 						sumRest: sumRest,
 						depreRate: depreRate,
 						dutyDeductibleRate: dutyDeductibleRate,
 						deductible: deductible,
 						indemnityDutyRate: indemnityDutyRate
 					},
 					function (data) {
 						if (data.prpLloss != null) {
 							var prpLlossDto = data.prpLloss;
 							cacheData($sumRealPay[0]); //缓存其本次计算前的值（超限则还原）
 							$sumRealPay.val(Math.round(prpLlossDto.sumRealPay));
 							if (checkLimit(kindCode, "0", Math.round(prpLlossDto.sumRealPay), "")) { //超出限额
 								recoveryData(field); //还原当前改变项
 								recoveryData($sumRealPay[0]); //还原当前赔付额
 							} else {
 								calFundNew();
 							}
 						} else if (data.errorMessage != null) {
 							alert(data.errorMessage);
 							window.setTimeout(function () {
 								$curr.focus();
 							});
 						}
 					});
 			} else {
 				$sumRealPay.val(0);
 				calFundNew();
 			}
 		}
 	}
 }
 /**
  * mantis：CLM0193 ，處理人員：DP0713，需求單編號：新核心-代步車日期計算及輸入檢核
  */
 var stopForQuamtity0cOverDayAcount = false;
 function calculateFinishAndDayCount(){
	try {
		debugger;
		stopForQuamtity0cOverDayAcount = false;
		var prpLrepairFeeRepairStartDate = $("input[name='prpLrepairFeeRepairStartDate']").val();//進廠日期
		var timeStamp_date = new Date(prpLrepairFeeRepairStartDate.replace("-","/").replace("-","/")) ; //進廠日期
		var timeStamp_date2=new Date(timeStamp_date);
		//乙、	完工日期：進廠日期-1+核定賠償金額/保險額度單日保額(元)
		timeStamp_date2.setDate(timeStamp_date.getDate() - 1);//STEP1:進廠日期-1
		var dayPerAmount = $("input[name='dayPerAmount']").val();//保險額度單日保額(元)
		if(""!=prpLrepairFeeRepairStartDate){
			var eachObj = $("tr[name='prpLlossObject']");
			var dayCount = 0;//天數
			//公式：進廠日期-1+核定賠償金額/保險額度單日保額(元)
			var finishDate = "";//完工日期
			var quamtity = 0;
			eachObj.each(function (i, obj) {
					var site = ['0A','0B','0C','14'];//,'0Y','07'
					var checkKC = $(obj).find(":input[name='prpLlossDtoKindCode']").val();
					checkPay = $(obj).find(":input[name='prpLlossDtoSumDefPay']").val();
//					var policyNo = $(":input[name='prpLdangerPolicyNo']").val(); //保单号;
					var policyNo = fm.prpLcompensatePolicyNo.value;//保单号;
					if(undefined!=checkKC && null!=checkKC){
						for (var i = 0; i < site.length; i++) {
							if(site[i] == checkKC && $.isNumeric(checkPay) && parseInt(checkPay,10)>0){
	
								var jsonData= (function(){
									$.ajax({
										url:contextRootPath + "/compensate/calDayPerAmount.do?policyNo="+policyNo+"&kindCode="+checkKC,
						 				async : false,
						 				cache : false,
						 				dataType: "json",
						 				contentType: "application/json; charset=utf-8",
											success: 
										function (data) {
												var perDay =0;
												if(null!=data && null!=data.dayPerAmount && ""!=data.dayPerAmount){
													perDay = Math.floor(parseInt(checkPay,10)/parseInt(data.dayPerAmount,10));
													//乙、	完工日期：進廠日期-1+核定賠償金額/保險額度單日保額(元)
													dayCount += perDay;
												}
												if(null!=data && null!=data.quamtity && ""!=data.quamtity){
													quamtity = parseInt(data.quamtity,10);
												}

												//丙、	該張保單賠付累計使用代步車天數不應超過承保範圍(QUANTITY)
												//alert(checkKC+"/"+perDay+"/"+quamtity);
												if(perDay>quamtity && !stopForQuamtity0cOverDayAcount){
													stopForQuamtity0cOverDayAcount = true;
												}else{
													stopForQuamtity0cOverDayAcount = false;
												}
										}
									});
								})();
							}
						};
					}
			});
			//debugger;
			var timeStamp_date3=new Date(timeStamp_date2);
			//乙、	完工日期：進廠日期-1+核定賠償金額/保險額度單日保額(元)
			timeStamp_date3.setDate(timeStamp_date2.getDate() + parseInt(dayCount,10));
			//mantis：CLM0221 ，處理人員：DP0713，需求單編號：新核心-車體險車輛資料完工日期欄位調整 START
			if(dayCount!="" && parseInt(dayCount,10)>0){
				//顯示民國 
				$("#finishDate").text((timeStamp_date3.getFullYear()-1911) +"-"+(timeStamp_date3.getMonth()+1)+"-" +timeStamp_date3.getDate());
				//存檔西元
				$("input[name='prpLrepairFeeCompleteDate']").val(timeStamp_date3.getFullYear() +"-"+(timeStamp_date3.getMonth()+1)+"-" +timeStamp_date3.getDate());
				//mantis：CLM0213，處理人員：DP0713，需求單編號：新核心-車體險維修時間重疊檢核新增險種
				$("input[name='prpLcomponentCompleteDate']").val(timeStamp_date3.getFullYear() +"-"+(timeStamp_date3.getMonth()+1)+"-" +timeStamp_date3.getDate());
			}else{
				//顯示民國 
				$("#finishDate").text("");
				//存檔西元
				$("input[name='prpLrepairFeeCompleteDate']").val("");
				//mantis：CLM0213，處理人員：DP0713，需求單編號：新核心-車體險維修時間重疊檢核新增險種
				$("input[name='prpLcomponentCompleteDate']").val("");
			}
			//mantis：CLM0213，處理人員：DP0713，需求單編號：新核心-車體險維修時間重疊檢核新增險種 START
			$("#dayCount").text(dayCount);
			$("input[name='prpLrepairFeeCourtesyCarUseDates']").val(dayCount);
			$("input[name='prpLcomponentCourtesyCarUseDates']").val(dayCount);
			//mantis：CLM0221 ，處理人員：DP0713，需求單編號：新核心-車體險車輛資料完工日期欄位調整 END
			//mantis：CLM0213，處理人員：DP0713，需求單編號：新核心-車體險維修時間重疊檢核新增險種 END
		}
	} catch (e) {
		// TODO: handle exception
	}
 }
 
 /**
  * mantis：CLM0175，處理人員：DP0713，需求單編號：新核心-車險計算書新增理賠已出險次數
  * @returns {Boolean}
  */
 function checkLossObjectListsMatchAndHavePay(){
	var check = true;
	var eachObj = $("tr[name='prpLlossObject']");
	eachObj.each(function (i, obj) {
		try {
			//mantis：CLM0213，處理人員：DP0713，需求單編號：新核心-車體險維修時間重疊檢核新增險種 ('0X','A1','A2','A3','B1','11')
			var site = ['01', '05', '07','09','0G','0A','0B','0C','14','0X','A1','A2','A3','B1','11'];
			var checkKC = $(obj).find(":input[name='prpLlossDtoKindCode']").val();
			checkPay = $(obj).find(":input[name='prpLlossDtoSumDefPay']").val();
			if(undefined!=checkKC && null!=checkKC){
				for (var i = 0; i < site.length; i++) {
					if(site[i] == checkKC && $.isNumeric(checkPay) && parseInt(checkPay,10)>0){
						if(!RepairComponentFillInTheForm()){
							check = false;
							return false;
						}
					}
				};
			}
		} catch (e) {
			// TODO: handle exception
		}
	});
	 
	 return check;
 }
 
 /**
  * mantis：CLM0175，處理人員：DP0713，需求單編號：新核心-車險計算書新增理賠已出險次數
  * @returns {Boolean}
  */
 function RepairComponentFillInTheForm(){
	 var rtn = true;
	 var prpLrepairFeeRepairFactoryCode =  $("select[name='prpLrepairFeeRepairFactoryCode']").val();
	 var prpLrepairFeeRepairFactoryName = $("input[name='prpLrepairFeeRepairFactoryName']").val();
	 var prpLrepairFeeRepairStartDate_show_format_rcDate = $("input[name='prpLrepairFeeRepairStartDate_show_format_rcDate']").val();
	 var prpLrepairFeeRepairEndDate_show_format_rcDate = $("input[name='prpLrepairFeeRepairEndDate_show_format_rcDate']").val();
	 
	 if(rtn && (prpLrepairFeeRepairFactoryCode == "" || prpLrepairFeeRepairFactoryName == "")){
		 alert("車輛訊息頁的「修理廠類型」、「修理廠名稱」需為必填");
		 rtn =false;
	 }
	 if(rtn && (prpLrepairFeeRepairStartDate_show_format_rcDate == "" || prpLrepairFeeRepairEndDate_show_format_rcDate == "")){
		 alert("車輛訊息頁的「進廠日期」及「約定交車日期」需為必填");
		 rtn =false;
	 }
	 
	 var timeStamp_date_one = new Date(prpLrepairFeeRepairStartDate_show_format_rcDate.replace("-","/").replace("-","/")) ; //進廠日期
	 var timeStamp_date_two = new Date(prpLrepairFeeRepairEndDate_show_format_rcDate.replace("-","/").replace("-","/")) ;//約定交車日期
	 if(rtn){
		 if (timeStamp_date_one instanceof Date && timeStamp_date_two instanceof Date) {
			 if(timeStamp_date_one.getTime() > timeStamp_date_two.getTime()){
				 alert("車輛訊息頁的「約定交車日期」必須在「進廠日期」之後");
				 rtn=false;
			 }
		 }
	 }
	 
	 var check = 0;
 	 $("#Component").find("tr[name='TrComponent']").each(function(){
 		var prpLcomponentKindName = $(this).find(":input[name='prpLcomponentKindName']");
 		if(prpLcomponentKindName.val() !=""){
 			check++;
 		}
	 });
	 $("#RepairFee").find("tr[name='TrRepairFee']").each(function(){
 		var prpLrepairFeeKindName = $(this).find(":input[name='prpLrepairFeeKindName']");
 		if(prpLrepairFeeKindName.val() !=""){
 			check++;
 		}
	 });
	 if(rtn && check==0){
		 alert("車輛訊息頁的「零配件更換專案費用清單」、「修理專案費用清單」不可為空");
		 rtn =false;
	 }
		
	 return rtn;
 }
 
 /**
  * mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3
  * @returns {Boolean}
  */
 function calRealpayNewSubmitCheck() {
	var check = true;
	var eachObj = $("tr[name='prpLlossObject']");
	eachObj.each(function (i, obj) {
		try {
			var checkE1 = $(obj).find(":input[name='prpLlossDtoKindCode']").val();
			if(undefined!=checkE1 && null!=checkE1 && check &&
				checkE1.substr(0,1)=="E"){
				check = dataCheck($(obj).find(":input[name='prpLlossDtoSumDefPay']"));
				if(!check){
					return false;
				}
			}
		} catch (e) {
			// TODO: handle exception
		}
	});

	
	var eachObj2 = $("tr[name='prpLpersonFeeLossObject']");
	eachObj2.each(function (i, obj) {
		try {
			var checkE2 = $(obj).find(":input[name='prpLpersonLossKindCode']").val();
			if(undefined!=checkE2 && null!=checkE2 && check &&
					checkE2.substr(0,1)=="E"){
				check = dataCheck($(obj).find(":input[name='prpLpersonLossSumDefPay']"));

				if(!check){
					return false;
				}
			}
		} catch (e) {
			// TODO: handle exception
		}
	});
	if(!check){//dataCheck裡面的checkLimit呼叫java驗證後 錯誤訊息會alert
		return false;
	}
	return true;
 }
 
 /**
  * mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3
  * @param field
  * @returns
  */
 function dataCheck(field){

		var $curr = $(field);
		var $prpLlossObject = $(field).parents("tr[name='prpLlossObject']");
		var kindCode = $prpLlossObject.find(":input[name='prpLlossDtoKindCode']").val(); //险别
		var kindName = $prpLlossObject.find(":input[name='prpLlossDtoKindName']").val(); //险别
		var sumDefPay = $prpLlossObject.find(":input[name='prpLlossDtoSumDefPay']").val(); //核定赔偿
		var compelPay = $prpLlossObject.find(":input[name='prpLlossDtoCompelPay']").val(); //强制险给付金额
		var sumRest = $prpLlossObject.find(":input[name='prpLlossDtoSumRest']").val(); //剔除金额、残值
		var depreRate = $prpLlossObject.find(":input[name='prpLlossDtoDepreRate']").val(); //折旧率
		var dutyDeductibleRate = $prpLlossObject.find(":input[name='prpLlossDtoDutyDeductibleRate']").val(); //自负额比例
		var deductible = $prpLlossObject.find(":input[name='prpLlossDtoDeductible']").val(); //自负额
		var indemnityDutyRate = $prpLlossObject.find(":input[name='prpLlossDtoIndemnityDutyRate']").val(); //肇事責任比率
		var policyNo = $(":input[name='prpLdangerPolicyNo']").val(); //保单号;
		if (kindCode != '' && sumDefPay != '' && !isNaN(sumDefPay)) { //核定赔偿为0，不继续下去
			var $sumRealPay = $prpLlossObject.find(":input[name='prpLlossDtoSumRealPay']");
			if (parseFloat(sumDefPay) != 0) {
			    var result=true;
				var jsonData= (function(){
					$.ajax({
						url:contextRootPath + "/compensate/calPrpLlossRealpay.do?policyNo="+policyNo+
						"&kindCode="+kindCode+
						"&kindName="+kindName+
						"&sumDefPay="+sumDefPay+
						"&compelPay="+compelPay+
						"&compelPay="+compelPay+
						"&sumRest="+sumRest+
						"&depreRate="+depreRate+
						"&dutyDeductibleRate="+dutyDeductibleRate+
						"&deductible="+deductible+
						"&indemnityDutyRate="+indemnityDutyRate,
		 				async : false,
		 				cache : false,
		 				dataType: "json",
		 				contentType: "application/json; charset=utf-8",
							success: 
						function (data) {
							if (data.prpLloss != null) {
								var prpLlossDto = data.prpLloss;
								if (checkLimit(kindCode, "0", Math.round(prpLlossDto.sumRealPay), "")) { //超出限额
									result = false;
								}
							} else if (data.errorMessage != null) {
								alert(data.errorMessage);
								window.setTimeout(function () {
									$curr.focus();
								});
								result = false;
							}
						}
					});
					//alert("rtn:"+result);
				    return result;
				})();
				//alert("jsonData:"+jsonData);
				return jsonData;
			}
		}
		return true;
 }
 
 /***
  * 车、物损、人伤赔付险别控制
  * 1. 24只有在酒驾时才赔付；
  * 2. 保单承保32、3B险别适用24赔付;保单承保31险别适用24赔付
  * 3. E1只有在非酒驾时才赔付；
  * @param field 当前双击域
  * @param typeName 当前赔付类别 prpLloss 车物损 prpLpersonLoss 人伤
  */

 function inputKindControl(field, typeName) {
 	var $kindCode = null;
 	var $kindName = null;
 	if (typeName == "prpLloss") {
 		var $prpLlossObject = $(field).parents("tr[name='prpLlossObject']");
 		$kindCode = $prpLlossObject.find(":input[name='prpLlossDtoKindCode']");
 		$kindName = $prpLlossObject.find(":input[name='prpLlossDtoKindName']");
 	} else if (typeName == "prpLpersonLoss") {
 		var $prpLpersonLossObject = $(field).parents("tr[name='prpLpersonFeeLossObject']");
 		$kindCode = $prpLpersonLossObject.find(":input[name='prpLpersonLossKindCode']");
 		$kindName = $prpLpersonLossObject.find(":input[name='prpLpersonLossKindName']");
 		if ($kindCode.val() != "") {
 			var $limitObject = $("input[name='limitKindCode'][value='" + $kindCode.val() + "']").parents("div[name='limitObject']");
 			if ($limitObject.length > 0) { //设置自负额
 				var deductible = parseFloat($limitObject.find(":input[name='limitDeductible']").val());
 				$prpLpersonLossObject.find(":input[name='prpLpersonLossSumRest']").val(Math.round(deductible));
 			}
 		}
 	}
 	if ($kindCode != null) {
 		var damageCode = $("input[name='prpLcompensateDamageCode']").val();
 		var checkFlag = false;
 		if ($kindCode.val() == "24") { //第三人受酒類車禍補償附加條款
 			if (damageCode == "12") { //酒驾情况
 				var prpLlossKind = $("#limitList").find("input[name='limitKindCode'][value='32'],input[name='limitKindCode'][value='3B']").size();
 				var prpLpersonLossKind = $("#limitList").find("input[name='limitKindCode'][value='31']").size();
 				if (typeName == "prpLloss" && prpLlossKind == 0) {
 					alert("该保單未承保適用‘" + $kindName.val() + "’進行車物損賠付的險別!");
 					checkFlag = true;
 				}
 				if (typeName == "prpLpersonLoss" && prpLpersonLossKind == 0) {
 					alert("该保單未承保適用‘" + $kindName.val() + "’進行人伤賠付的險別!");
 					checkFlag = true;
 				}
 			} else {
 				alert("‘" + $kindName.val() + "’不能進行非酒駕賠償!");
 				checkFlag = true;
 			}
 		} else if ($kindCode.val() == "E1") { //第三人責任超額保險（不含酒償險）
 			if (damageCode == "12") { //酒驾情况
 				alert("‘" + $kindName.val() + "’不能進行酒駕賠償!");
 				checkFlag = true;
 			}
 		//mantis： CLM0016，處理人員：David，需求單編號：CLM0016 20190816新增可賠付商品險種 E5 start
 		} else if ($kindCode.val() == "E5") { //第三人責任保險超額責任-乙式（不含酒償險）
 			if (damageCode == "12") { //酒驾情况
 				alert("‘" + $kindName.val() + "’不能進行酒駕賠償!");
 				checkFlag = true;
 			}
 		}
 		//mantis： CLM0016，處理人員：David，需求單編號：CLM0016 end
 		if (checkFlag) {
 			$kindCode.val("");
 			$kindName.val("");
 		}
 	}
 }
 /***
  * 校验险别赔付
  * 24、E1不能赔付酒驾的情况
  */

 function checkKind() {
 	var damageCode = $("input[name='prpLcompensateDamageCode']").val();
 	var kindCodes = new Array(); //当前参与赔付的所有险别
 	$("#PrpLloss").find("input[name='prpLlossDtoKindCode']").each(function () {
 		if ($.trim(this.value) != "") {
 			kindCodes.push(this.value);
 		}
 	});
 	$("#PrpLpersonLoss").find("input[name='prpLpersonLossKindCode']").each(function () {
 		if ($.trim(this.value) != "") {
 			kindCodes.push(this.value);
 		}
 	});
 	if (damageCode == "12" && $.inArray("E1", kindCodes) != -1) { //酒驾情况
 		alert("‘E1 第三人責任超額保險（不含酒償險）’不能進行酒駕賠償!");
 		return false;
 	}
 	
 	//mantis： CLM0016，處理人員：David，需求單編號：CLM0016 20190816新增可賠付商品險種E5 
 	if (damageCode == "12" && $.inArray("E5", kindCodes) != -1) { //酒驾情况
 		alert("‘E5 第三人責任保險超額責任-乙式（不含酒償險）’不能進行酒駕賠償!");
 		return false;
 	}
 	
 	if ($.inArray("24", kindCodes) != -1) { //24赔偿时
 		if (damageCode != "12") {
 			alert("‘24 第三人受酒類車禍補償附加條款’不能進行非酒駕賠償!");
 			return false;
 		}
 		var $prpLloss = $("#PrpLloss").find("input[name='prpLlossDtoKindCode'][value='24']");
 		var $prpLpersonLoss = $("#PrpLpersonLoss").find("input[name='prpLpersonLossKindCode'][value='24']");
 		var $prpLlossKind = $("#limitList").find("input[name='limitKindCode'][value='32'],input[name='limitKindCode'][value='3B']");
 		var $prpLpersonLossKind = $("#limitList").find("input[name='limitKindCode'][value='31']").size();
 		if ($prpLloss.length > 0 && $prpLlossKind.length == 0) {
 			alert("该保單未承保適用‘24 第三人受酒類車禍補償附加條款’進行車物損賠付的險別!");
 			return false;
 		}
 		if ($prpLpersonLoss.length > 0 && $prpLpersonLossKind.length == 0) {
 			alert("该保單未承保適用‘24 第三人受酒類車禍補償附加條款’進行人伤賠付的險別!");
 			return false;
 		}
 	}
 	return true;
 }

 /***
  * 获取当前险别的车物损赔付总额
  * @param currKindCode
  */

 function getPrpLlossPayAmount(currKindCode) {
 	var sumPay = 0; //当前的险别赔款总额
 	$("#PrpLloss").find("tr[name='prpLlossObject']").each(function () {
 		var $kindCode = $(this).find(":input[name='prpLlossDtoKindCode'][value='" + currKindCode + "']");
 		if ($kindCode.length > 0) {
 			var sumRealPay = $(this).find(":input[name='prpLlossDtoSumRealPay']").val();
 			sumPay += (sumRealPay == "" || isNaN(sumRealPay) ? 0 : parseFloat(sumRealPay));
 		}
 	});
 	return sumPay;
 }

 /***
  * 获取当前险别的人伤赔款金额
  * @param currKindCode
  * @returns {Number}
  */

 function getPrpLpersonLossPayAmount(currKindCode) {
 	var sumPay = 0; //当前的险别赔款总额
 	$("#PrpLpersonLoss").find("tr[name='prpLpersonLossObject']").each(function (i, obj) {
 		$(obj).find("tr[name='prpLpersonFeeLossObject']").each(function () {
 			var $kindCode = $(this).find(":input[name='prpLpersonLossKindCode'][value='" + currKindCode + "']");
 			var $sumRealPay = $(this).find(":input[name='prpLpersonLossSumRealPay']");
 			if ($kindCode.length > 0) {
 				sumPay += ($sumRealPay.val() == "" || isNaN($sumRealPay.val()) ? 0 : parseFloat($sumRealPay.val()));
 			}
 		});
 	});
 	return sumPay;
 }
 /***
  * 获取当前受害人的赔款金额
  * @param currKindCode
  * @param identifyNumber
  * @returns {Number}
  */

 function getPersonPayAmount(currKindCode, identifyNumber) {
 	var sumPay = 0; //当前的险别赔款总额
 	$("#PrpLpersonLoss").find("tr[name='prpLpersonLossObject']").each(function (i, obj) {
 		var $identifyNumber = $(obj).find(":input[name='prpLpersonLossIdentifyNumber'][value='" + identifyNumber + "']");
 		if ($identifyNumber.length > 0) {
 			$(obj).find("tr[name='prpLpersonFeeLossObject']").each(function () {
 				var $kindCode = $(this).find(":input[name='prpLpersonLossKindCode'][value='" + currKindCode + "']");
 				var $sumRealPay = $(this).find(":input[name='prpLpersonLossSumRealPay']");
 				if ($kindCode.length > 0) {
 					sumPay += ($sumRealPay.val() == "" || isNaN($sumRealPay.val()) ? 0 : parseFloat($sumRealPay.val()));
 				}
 			});
 		}
 	});
 	return sumPay;
 }

 /**
  * 判断车物损、人伤是否超出险别限额
  * @param kindCode 险别
  * @param type 0 车物损 ； 1 人伤
  * @param identifyNumber 当前受害人身份证
  * @returns {Boolean} true 超限
  * 校验规则：
  * limitMeter 计次：不能超过赔付次数 -1 本次赔付无次数限制
  * limitResidue 保险期间累计：的历次含本次总赔付不能超限额 -1非累计
  * limitType 0：每次事故：车物损、人伤总赔款不得超过限额limitAmount
  * limitType 2：每次事故：可对车物损、人伤分别进行赔付。每次事故赔车物损部分总额不得超过限额limitPropAmount
  * 			   每次事故赔人伤部分每人不得超过限额limitAmount - limitPropAmount
  * limitType 1：每次事故：人伤赔付且每次事故不得超过限额limitAmount,每人不得超过限额limitPersonAmount
  */

 function checkLimit(kindCode, type, payAmount, identifyNumber) {
 	if (payAmount == 0) { //当前计算赔付为0 默认不会超出限额
 		return false;
 	}
 	var $limitObject = $("#limitList").find(":input[name='limitKindCode'][value='" + kindCode + "']").parents("div[name='limitObject']");
 	var messges = "";
 	if ($limitObject.length > 0) {
 		var limitFlag = $limitObject.find("input[name='limitFlag']").val(); //true 接受限额控制的险别
 		if (limitFlag == "0") {
 			var limitKindCode = $limitObject.find("input[name='limitKindCode']").val();
 			//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 (順便幫忙送CLM0086)START
 			//mantis： CLM0086，處理人員：BK007 蘇哲，需求單編號：CLM0086.超額問題開發與測試 -start
 			if(limitKindCode.substr(0,1) === 'E'){
 				if(!checkExcess(kindCode,type,identifyNumber)){
 					alert("未達使用第三人責任保險超額賠付標準，請使用第三人責任保險進行賠付!");
 					return true;
 				}
 			}
 			//mantis： CLM0086，處理人員：BK007 蘇哲，需求單編號：CLM0086.超額問題開發與測試 -end
 			//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 (順便幫忙送CLM0086)END
 			var limitKindName = $limitObject.find("input[name='limitKindName']").val();
 			var limitAmount = Math.round(parseFloat($limitObject.find("input[name='limitAmount']").val()));
 			var limitPastPay = Math.round(parseFloat($limitObject.find("input[name='limitPastPay']").val()));
 			var limitMeter = $limitObject.find("input[name='limitMeter']").val();
 			var limitType = $limitObject.find("input[name='limitType']").val();
 			var limitResidue = parseFloat($limitObject.find("input[name='limitResidue']").val());
 			var checkPerson = false; //是否需要校验每人是否超限额的标志
 			if (limitMeter == "0") {
 				messges += limitKindName + "賠付次數已達上限!(可賠付次數：" + $limitObject.find("input[name='limitMaxNum']").val() + ")\n";
 			} else if (limitResidue == 0) {
 				messges += limitKindName + "累計賠付已達上限!(已累計賠付：" + $limitObject.find("input[name='limitTotalPay']").val() + ")\n";
 			} else if (limitType == 0 || limitType == 1) { //每次事故 人伤\车\财产总和不得超过limitAmount
 				var sumPay = getPrpLlossPayAmount(kindCode) + getPrpLpersonLossPayAmount(kindCode); //每事故赔付合计
 				if ((sumPay + limitPastPay) > limitAmount) { //每事故是否超出限额
 					//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 START
 					if(limitKindCode.substr(0,1) != 'E'){
 						messges += limitKindName + "本案賠付超出" + (sumPay + limitPastPay - limitAmount) + "元!(限額：" + limitAmount + ")\n";
 					}
 					//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 END
 				} else { //校验每人的情况
 					if (limitType == 1 && type == "1") {
 						checkPerson = true;
 					}
 				}
 			} else if (limitType == 2) {
 				var limitPersonPastPay = Math.round(parseFloat($limitObject.find("input[name='limitPersonPastPay']").val()));
 				var limitPropAmount = Math.round(parseFloat($limitObject.find("input[name='limitPropAmount']").val()));
 				var limitPropPastPay = limitPastPay - limitPersonPastPay;
 				if (type == "0") { //赔付车物损的险别、单独又计算每人每事故的情况
 					var sumPropPay = getPrpLlossPayAmount(kindCode);
 					if ((sumPropPay + limitPropPastPay) > limitPropAmount) {
 						messges += limitKindName + "本案賠付超出" + (sumPropPay + limitPropPastPay - limitPropAmount) + "元!(車物損賠付限額：" + limitPropAmount + ")\n";
 					}
 				} else if (type == "1") { //赔付人伤，人伤单独限制
 					var personAmount = limitAmount - limitPropAmount;
 					var sumPersonPay = getPrpLpersonLossPayAmount(kindCode); //每事故赔付合计
 					if ((sumPersonPay + limitPersonPastPay) > personAmount) { //每事故人伤赔付合计否超出限额
 						messges += limitKindName + "本案賠付超出" + (sumPersonPay + limitPersonPastPay - personAmount) + "元!(人傷賠付限額：" + personAmount + ")\n";
 					} else {
 						checkPerson = true;
 					}
 				}
 			}
 			if (checkPerson) {
 				var limitPersonAmount = Math.round(parseFloat($limitObject.find("input[name='limitPersonAmount']").val()));
 				var personPay = getPersonPayAmount(kindCode, identifyNumber); //该受害人赔付合计
 				var $pastPay = $("#limitList").find(":input[name='" + identifyNumber + "_" + kindCode + "']");
 				if ($pastPay.length > 0) { //该受害人在本案已审核通过的计算书中有赔付
 					personPay += ($pastPay.val() == "" || isNaN($pastPay.val()) ? 0 : parseFloat($pastPay.val()));
 				}
 				if (personPay > limitPersonAmount) {
 					messges += limitKindName + "受害人" + identifyNumber + "本案賠付超出" + (personPay - limitPersonAmount) + "元!(限額：" + limitPersonAmount + "元/人)\n";
 				}
 			}
 		}
 		//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 START
		var e_limitKindCode = $limitObject.find("input[name='limitKindCode']").val();
 		if(e_limitKindCode.substr(0,1) === 'E'){
 			var e_limitAmount = Math.round(parseFloat($limitObject.find("input[name='limitAmount']").val()));
 			var e_limitPastPay = Math.round(parseFloat($limitObject.find("input[name='limitPastPayE']").val()));//+E
 			var e_limitKindName = $limitObject.find("input[name='limitKindName']").val();
 			var e_sumPay = getPrpLlossPayAmount(e_limitKindCode) + getPrpLpersonLossPayAmount(e_limitKindCode); 
			if ((e_sumPay + e_limitPastPay) > e_limitAmount) { 
				messges += e_limitKindName + "本案賠付超出" + (e_sumPay + e_limitPastPay - e_limitAmount) + "元!(超額限額：" + e_limitAmount + ")\n";
			}
 		}
 		//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 END
 	}
 	if (messges.length > 0) {
 		alert(messges);
 		return true;
 	}
 	return false;
 }
 
//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 (順便幫忙送CLM0086)START
//mantis： CLM0086，處理人員：BK007 蘇哲，需求單編號：CLM0086.超額問題開發與測試 -start
 function checkExcess( kindCode , type , identifyNumber ){
	var kindCodes = new Array(); //当前参与赔付的所有险别
	//取车物损险别的赔付
	var propKindCodes = new Array(); //车物损赔付的险别，
	var propPayAmounts = new Array(); //除恶无损赔付险别对应的理赔金
	$("#PrpLloss").find("tr[name='prpLlossObject']").each(function () {
		var kindCode = $(this).find(":input[name='prpLlossDtoKindCode']").val();
		var sumRealPay = $(this).find(":input[name='prpLlossDtoSumRealPay']").val();
		processProp(kindCode, sumRealPay, kindCodes, propKindCodes, propPayAmounts);
	});
	//取人傷險種賠付資料
	var personKindCodes = new Array(); //人伤赔付的险别
 	var personPayAmounts = new Array(); //人伤赔付的险别对应的理赔金
 	var persons = new Array(); //受害人 根据身份证号
 	var personPerKind = new Array(); //每人每险别，
 	var personPerKindAmount = new Array(); //每人每险别对应理赔金，
 	$("#PrpLpersonLoss").find("tr[name='prpLpersonLossObject']").each(function () {
 		var identifynumber = $(this).find(":input[name='prpLpersonLossIdentifyNumber']").val();
		if($.trim(identifynumber) != ""){//需求變更127改動，身份證號沒有輸入時，無視該受害人按賠付校驗
 		$(this).find("tr[name='prpLpersonFeeLossObject']").each(function () {
 			var kindCode = $(this).find(":input[name='prpLpersonLossKindCode']").val();
 			var sumRealPay = $(this).find(":input[name='prpLpersonLossSumRealPay']").val();
 			processPerson(kindCode, identifynumber, sumRealPay, kindCodes, personKindCodes, personPayAmounts, personPerKind, personPerKindAmount, persons);
 		});
		}
 	});
 	var $limitObject;
 	var targetKindCode;
	if(type == "0"){
		//alert("判斷財損超額");
		$limitObject = $("#limitList").find(":input[name='limitKindCode'][value='32']").parents("div[name='limitObject']");
		targetKindCode = "32";
	}else{
		//alert("判斷人傷超額");
		$limitObject = $("#limitList").find(":input[name='limitKindCode'][value='31']").parents("div[name='limitObject']");
		targetKindCode = "31";
	}
	if ($limitObject.length > 0 && $limitObject.find("input[name='limitFlag']").val() == "0") {
		if($limitObject.find("input[name='limitMeter']").val() == "0"){ // 賠付次數 -1:還可以賠付 0:次數已用完
			return true;
		}
		if(parseFloat($limitObject.find("input[name='limitResidue']").val()) == 0){
			return true;
		}
		var limitAmount = Math.round(parseFloat($limitObject.find("input[name='limitAmount']").val())); //單一事故限額保額 
		var limitPastPay = Math.round(parseFloat($limitObject.find("input[name='limitPastPay']").val())); //
		var limitType = $limitObject.find("input[name='limitType']").val(); //
		var checkPerson = false; //是否需要检查每个人的限额是否超限
		if (limitType == 0 || limitType == 1) {
			var index = $.inArray(targetKindCode, propKindCodes);
			var sumPay = (index != -1 ? parseFloat(propPayAmounts[index]) : 0);
			index = $.inArray(targetKindCode, personKindCodes);
			sumPay += (index != -1 ? parseFloat(personPayAmounts[index]) : 0);
			if ((sumPay + limitPastPay) == limitAmount) { //每事故合计不能超每事故限额
				return true;
			} else if (limitType == 1 && type == "1") {
				checkPerson = true; //需要校验每人
			}
		} else if(limitType == 2){
			var limitPropAmount = Math.round(parseFloat($limitObject.find("input[name='limitPropAmount']").val()));
			var limitPersonPastPay = Math.round(parseFloat($limitObject.find("input[name='limitPersonPastPay']").val()));
			var index = $.inArray(targetKindCode, propKindCodes);
			if (index != -1) {
				var sumPropPay = parseFloat(propPayAmounts[index]);
				var limitPropPastPay = limitPastPay - limitPersonPastPay;
				if ((sumPropPay + limitPropPastPay) == limitPropAmount) {
					return true;
				}
			}
			index = $.inArray(targetKindCode, personKindCodes);
			if (index != -1) {
				var personAmount = limitAmount - limitPropAmount;
				var sumPersonPay = parseFloat(personPayAmounts[index]);
				if ((sumPersonPay + limitPersonPastPay) > personAmount) {
					return true;
				}
				checkPerson = true; //需要校验每人
			}
		}
		if (checkPerson) {
			var limitPersonAmount = Math.round(parseFloat($limitObject.find("input[name='limitPersonAmount']").val()));
			var personPay = getPersonPayAmount(targetKindCode, identifyNumber); //该受害人赔付合计
			var $pastPay = $("#limitList").find(":input[name='" + identifyNumber + "_" + targetKindCode + "']");
			if ($pastPay.length > 0) { //该受害人在本案已审核通过的计算书中有赔付
				personPay += ($pastPay.val() == "" || isNaN($pastPay.val()) ? 0 : parseFloat($pastPay.val()));
			}
			//alert(identifyNumber + "@"+personPay +":"+limitPersonAmount);
			if (personPay == limitPersonAmount) {
				return true;
			}
		}
	}
	return false;
 }
//mantis： CLM0086，處理人員：BK007 蘇哲，需求單編號：CLM0086.超額問題開發與測試 -end
//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3(順便幫忙送CLM0086) END

 /***
  * 判断当前所有险别的赔付金额是否超限
  * true 校验通过未超出限额 ；false：校验不通过 超出限额
  */

 function checkAllLimitItemKind() {
 	var kindCodes = new Array(); //当前参与赔付的所有险别
 	//取车物损险别的赔付
 	var propKindCodes = new Array(); //车物损赔付的险别，
 	var propPayAmounts = new Array(); //除恶无损赔付险别对应的理赔金
 	$("#PrpLloss").find("tr[name='prpLlossObject']").each(function () {
 		var kindCode = $(this).find(":input[name='prpLlossDtoKindCode']").val();
 		var sumRealPay = $(this).find(":input[name='prpLlossDtoSumRealPay']").val();
 		processProp(kindCode, sumRealPay, kindCodes, propKindCodes, propPayAmounts);
 	});
 	var personKindCodes = new Array(); //人伤赔付的险别
 	var personPayAmounts = new Array(); //人伤赔付的险别对应的理赔金
 	var persons = new Array(); //受害人 根据身份证号
 	var personPerKind = new Array(); //每人每险别，
 	var personPerKindAmount = new Array(); //每人每险别对应理赔金，
 	$("#PrpLpersonLoss").find("tr[name='prpLpersonLossObject']").each(function () {
 		var identifynumber = $(this).find(":input[name='prpLpersonLossIdentifyNumber']").val();
		if($.trim(identifynumber) != ""){//需求變更127改動，身份證號沒有輸入時，無視該受害人按賠付校驗
 		$(this).find("tr[name='prpLpersonFeeLossObject']").each(function () {
 			var kindCode = $(this).find(":input[name='prpLpersonLossKindCode']").val();
 			var sumRealPay = $(this).find(":input[name='prpLpersonLossSumRealPay']").val();
 			processPerson(kindCode, identifynumber, sumRealPay, kindCodes, personKindCodes, personPayAmounts, personPerKind, personPerKindAmount, persons);
 		});
		}
 	});
 	var messges = ""; //提示信息
 	$.each(kindCodes, function (i, kindCode) {
 		var $limitObject = $("#limitList").find(":input[name='limitKindCode'][value='" + kindCode + "']").parents("div[name='limitObject']");
 		if ($limitObject.length > 0) {
 			var limitFlag = $limitObject.find("input[name='limitFlag']").val(); //true 接受限额控制的险别
 			if (limitFlag == "0") {
 				//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3(順便幫忙送CLM0086) START
 				var limitKindCode = $limitObject.find("input[name='limitKindCode']").val();
 				//mantis： CLM0086，處理人員：BK007 蘇哲，需求單編號：CLM0086.超額問題開發與測試 -start
 				var srp1 = Math.abs(0+$limitObject.find(":input[name='prpLlossDtoSumRealPay']").val());
 				var sumRealPay1 = isNaN(srp1)?0:srp1;
 				var srp2 = Math.abs(0+$limitObject.find(":input[name='prpLpersonLossSumRealPay']").val());
 				var sumRealPay2 = isNaN(srp2)?0:srp2;
 				if(limitKindCode.substr(0,1) === 'E' && ( sumRealPay1 + sumRealPay2 ) != 0){
 	 				var canExcess = false;
 	 				$.each(persons, function (i, identifynumber) {
 	 					canExcess = canExcess || !checkExcess(kindCode,"1",identifynumber);
 	 				});
 	 				if(!checkExcess(kindCode,"0","") && canExcess){
 	 					messges += "未達使用第三人責任保險超額賠付標準，請使用第三人責任保險進行賠付!\n";
 	 				}
 	 			}
 				//mantis： CLM0086，處理人員：BK007 蘇哲，需求單編號：CLM0086.超額問題開發與測試 -end
 				//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 (順便幫忙送CLM0086) END
 				var limitKindName = $limitObject.find("input[name='limitKindName']").val();
 				var limitAmount = Math.round(parseFloat($limitObject.find("input[name='limitAmount']").val()));
 				var limitPastPay = Math.round(parseFloat($limitObject.find("input[name='limitPastPay']").val()));
 				var limitMeter = $limitObject.find("input[name='limitMeter']").val();
 				var limitType = $limitObject.find("input[name='limitType']").val();
 				var limitResidue = parseFloat($limitObject.find("input[name='limitResidue']").val());
 				var checkPerson = false; //是否需要检查每个人的限额是否超限
 				if (limitMeter == "0") {
 					//mantis：CLM0252，處理人員：DP0713，需求單編號：新核心-車險道路救援案件次數上限問題確認 START
 					if(kindCode.substr(0,1) == "Y"){//開頭Y 為道路救援
	 					$("#PrpLloss").find("tr[name='prpLlossObject']").each(function (i) {
	 						var $prpLlossDtoKindCode = $(this).find(":input[name='prpLlossDtoKindCode']");
	 						if(undefined!=$prpLlossDtoKindCode && $prpLlossDtoKindCode.val() == kindCode){
		 				 		var $prpLlossDtoSumRealPay = $(this).find(":input[name='prpLlossDtoSumRealPay']");
		 				 		if($prpLlossDtoSumRealPay.val() != "0"){//當Y1賠付金額為0時候 忽略這次判斷，直接過
		 				 			messges += limitKindName + "賠付次數已達上限!(可賠付次數：" + $limitObject.find("input[name='limitMaxNum']").val() + ")\n";
		 				 		}
	 						}
	 					});
	 				}else{
	 					messges += limitKindName + "賠付次數已達上限!(可賠付次數：" + $limitObject.find("input[name='limitMaxNum']").val() + ")\n";
	 				}
 					//mantis：CLM0252，處理人員：DP0713，需求單編號：新核心-車險道路救援案件次數上限問題確認 END
 				} else if (limitResidue == 0) {
 					messges += limitKindName + "累計賠付已達上限!(已累計賠付：" + $limitObject.find("input[name='limitTotalPay']").val() + ")\n";
 				} else if (limitType == 0 || limitType == 1) { //每次事故 人伤\车\财产总和不得超过limitAmount
 					var sumPay = 0;
 					var index = $.inArray(kindCode, propKindCodes);
 					sumPay += (index != -1 ? parseFloat(propPayAmounts[index]) : 0);
 					index = $.inArray(kindCode, personKindCodes);
 					sumPay += (index != -1 ? parseFloat(personPayAmounts[index]) : 0);
 					if ((sumPay + limitPastPay) > limitAmount) { //每事故合计不能超每事故限额
 						//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 START
 	 					if(limitKindCode.substr(0,1) != 'E'){
 							messges += limitKindName + "本案賠付超出" + (sumPay + limitPastPay - limitAmount) + "元!(限額：" + limitAmount + ")\n";
 	 					}
 	 					//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 END
 					} else if (limitType == 1) {
 						checkPerson = true; //需要校验每人
 					}
 					//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3(順便幫忙送CLM0086)
 					//mantis： CLM0086，處理人員：BK007 蘇哲，需求單編號：CLM0086.超額問題開發與測試
 					//sumRealPayForExcess += sumPay;
 				} else if (limitType == 2) { //每人每事故  每人每险别赔付不得超过险别限额
 					var limitPropAmount = Math.round(parseFloat($limitObject.find("input[name='limitPropAmount']").val()));
 					var limitPersonPastPay = Math.round(parseFloat($limitObject.find("input[name='limitPersonPastPay']").val()));
 					var index = $.inArray(kindCode, propKindCodes);
 					if (index != -1) {
 						var sumPropPay = parseFloat(propPayAmounts[index]);
 						var limitPropPastPay = limitPastPay - limitPersonPastPay;
 						if ((sumPropPay + limitPropPastPay) > limitPropAmount) {
 							messges += limitKindName + "本案賠付車物損超出" + (sumPropPay + limitPropPastPay - limitPropAmount) + "元!(車物損賠付限額：" + limitPropAmount + ")\n";
 						}
 						//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3(順便幫忙送CLM0086)
 						//mantis： CLM0086，處理人員：BK007 蘇哲，需求單編號：CLM0086.超額問題開發與測試
 						//sumRealPayForExcess += sumPropPay;
 					}
 					index = $.inArray(kindCode, personKindCodes);
 					if (index != -1) {
 						var personAmount = limitAmount - limitPropAmount;
 						var sumPersonPay = parseFloat(personPayAmounts[index]);
 						if ((sumPersonPay + limitPersonPastPay) > personAmount) {
 							messges += limitKindName + "本案賠付人傷超出" + (sumPersonPay + limitPersonPastPay - personAmount) + "元!(人傷賠付限額：" + personAmount + ")\n";
 						}
 						checkPerson = true; //需要校验每人
						//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3(順便幫忙送CLM0086) 
 						//mantis： CLM0086，處理人員：BK007 蘇哲，需求單編號：CLM0086.超額問題開發與測試
 						//sumRealPayForExcess += sumPersonPay;
 					}
 				}
 				if (checkPerson) {
 					var limitPersonAmount = Math.round(parseFloat($limitObject.find("input[name='limitPersonAmount']").val()));
 					//每人每险别不得超过险别限额
 					$.each(persons, function (i, identifynumber) {
 						var tempKey = identifynumber + "_" + kindCode;
 						var index = $.inArray(tempKey, personPerKind);
 						if (index != -1) {
 							var personPay = parseFloat(personPerKindAmount[index]); //此人该险别赔付
 							var $p = $("#limitList").find(":input[name='" + tempKey + "']");
 							if ($p.length > 0) {
 								personPay += parseFloat($p.val());
 							}
 							if (personPay - limitPersonAmount > 0) {
 								messges += limitKindName + "受害人" + identifynumber + "本案賠付超出" + (personPay - limitPersonAmount) + "元!(限額：" + limitPersonAmount + "元/人)\n";
 							}
 						}
 					});
 				}
 				//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3(順便幫忙送CLM0086) START
 				//mantis： CLM0086，處理人員：BK007 蘇哲，需求單編號：CLM0086.超額問題開發與測試 -start
 				//if(limitKindCode.substr(0,1) === 'E' && sumRealPayForExcess != 0){
 	 				//var canExcess = false;
 	 				//$.each(persons, function (i, identifynumber) {
 	 				//	canExcess = canExcess || !checkExcess(kindCode,"1",identifynumber);
 	 				//});
 	 				//if(!checkExcess(kindCode,"0","") && canExcess){
 	 				//	messges += "未達使用第三人責任保險超額賠付標準，請使用第三人責任保險進行賠付!\n";
 	 			//	}
 	 		//	}
 				//mantis： CLM0086，處理人員：BK007 蘇哲，需求單編號：CLM0086.超額問題開發與測試 -end 			}
 				//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3(順便幫忙送CLM0086) END
 			}
 			//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 START
 			var e_limitKindCode = $limitObject.find("input[name='limitKindCode']").val();
 	 		if(e_limitKindCode.substr(0,1) === 'E'){
 	 			var e_limitAmount = Math.round(parseFloat($limitObject.find("input[name='limitAmount']").val()));
 	 			var e_limitPastPay = Math.round(parseFloat($limitObject.find("input[name='limitPastPayE']").val()));//+E
 	 			var e_limitKindName = $limitObject.find("input[name='limitKindName']").val();
 	 			var e_sumPay = getPrpLlossPayAmount(e_limitKindCode) + getPrpLpersonLossPayAmount(e_limitKindCode); 
 				if ((e_sumPay + e_limitPastPay) > e_limitAmount) { 
 					messges += e_limitKindName + "本案賠付超出" + (e_sumPay + e_limitPastPay - e_limitAmount) + "元!(超額限額：" + e_limitAmount + ")\n";
 				}
 	 		}
 	 		//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 END
 		}
 	});
 	if ($.trim(messges).length > 0) {
 		alert(messges);
 		return false;
 	}
 	return true;
 }

 /***
  * 汇总处理当前险别车物损
  * @param kindCode 当前险别
  * @param sumRealPay 当前险别理赔金
  * @param kindCodes 当前参与赔付的所有险别
  * @param propKindCodes 参与车物损赔付的险别
  * @param propPayAmounts 参与车物损赔付的险别对应理赔金
  */

 function processProp(kindCode, sumRealPay, kindCodes, propKindCodes, propPayAmounts) {
 	var index = $.inArray(kindCode, kindCodes);
 	if (index == -1) {
 		kindCodes.push(kindCode);
 	}
 	index = $.inArray(kindCode, propKindCodes);
 	if (index == -1) { //不存在，则加入数组
 		propKindCodes.push(kindCode);
 		propPayAmounts.push(sumRealPay);
 	} else { //存在则理赔金累加
 		propPayAmounts[index] = parseFloat(propPayAmounts[index]) + parseFloat(sumRealPay);
 	}

 }
 /***
  * 汇总处理当前人伤险别赔付
  * @param kindCode 当前险别
  * @param identifynumber 当前受害人身份证
  * @param sumRealPay 当前理赔金
  * @param kindCodes 当前参与赔付的所有险别
  * @param personKindCodes 参与人伤赔付的险别
  * @param personPayAmounts 参与人伤赔付的险别对应理赔金
  * @param personPerKind  每人每险别
  * @param personPerKindAmount 每人每险别对应理赔金
  * @param persons 受害人数
  */

 function processPerson(kindCode, identifynumber, sumRealPay, kindCodes, personKindCodes, personPayAmounts, personPerKind, personPerKindAmount, persons) {
 	var index = $.inArray(kindCode, kindCodes);
 	if (index == -1) { //汇总当前计算书参与赔付的险别
 		kindCodes.push(kindCode);
 	}
 	index = $.inArray(kindCode, personKindCodes);
 	if (index == -1) { //汇总同险别
 		personKindCodes.push(kindCode);
 		personPayAmounts.push(sumRealPay);
 	} else {
 		personPayAmounts[index] = parseFloat(personPayAmounts[index]) + parseFloat(sumRealPay);
 	}
 	index = $.inArray(identifynumber, persons);
 	if (index == -1) { //汇总受害人
 		persons.push(identifynumber);
 	}
 	var tempValue = identifynumber + "_" + kindCode;
 	index = $.inArray(tempValue, personPerKind);
 	if (index == -1) { //汇总每个受害人对应每个险别的理赔金赔付
 		personPerKind.push(tempValue);
 		personPerKindAmount.push(sumRealPay);
 	} else {
 		personPerKindAmount[index] = parseFloat(personPerKindAmount[index]) + parseFloat(sumRealPay);
 	}
 }

 //人伤理赔金计算

 function calRealpay2ForSunnyNew(field) {
 	if (isChange(field) || field.name == 'prpLdisabilityLimitRatingCode') {
 		var $curr = $(field);
 		var $prpLpersonFeeLossObject = $(field).parents("tr[name='prpLpersonFeeLossObject']"); //操作费用
 		var $prpLpersonLossObject = $(field).parents("tr[name='prpLpersonLossObject']"); //操作受害人
 		var kindCode = $prpLpersonFeeLossObject.find(":input[name='prpLpersonLossKindCode']").val(); //险别
 		var kindName = $prpLpersonFeeLossObject.find(":input[name='prpLpersonLossKindName']").val(); //
 		var identifyNumber = $prpLpersonLossObject.find(":input[name='prpLpersonLossIdentifyNumber']").val(); //受害人身份证
 		var liabDetailCode = $prpLpersonFeeLossObject.find(":input[name='prpLpersonLossLiabDetailCode']").val(); //人傷費用類別代碼
 		var medicDeathFlag = $prpLpersonFeeLossObject.find(":input[name='medicDeathFlag']").val(); //人傷費用類別類型
 		var ratingCode = $prpLpersonFeeLossObject.find(":input[name='prpLdisabilityLimitRatingCode']").val(); //残废等级
 		var sumDefPay = $prpLpersonFeeLossObject.find(":input[name='prpLpersonLossSumDefPay']").val(); //核定赔偿
 		var compelPay = $prpLpersonFeeLossObject.find(":input[name='prpLpersonLossCompelPay']").val(); //强制险给付金额
 		var sumRest = $prpLpersonFeeLossObject.find(":input[name='prpLpersonLossSumRest']").val(); //剔除金额、残值 | 本例为自负额
 		var indemnityDutyRate = $prpLpersonLossObject.find(":input[name='prpLpersonLossIndemnityDutyRate']").val(); //肇事責任比率
 		var claimNo = $(":input[name='prpLcompensateClaimNo']").val(); //取赔案号
 		//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 START
 		//任意險受害人資訊
 		if("31"==kindCode || "32"==kindCode){
 			cleanE3E9();
 		}
 		//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 END 
 		//mantis： CLM0166，處理人員：DP0713，需求單編號：車體新商品上線險別0Y START
 		if("07"==kindCode){
 			clean07();
 		}
 		//mantis： CLM0166，處理人員：DP0713，需求單編號：車體新商品上線險別0Y END
		if ($.trim(kindCode) != "" && $.trim(liabDetailCode) != '') { //
 			if (field.name == 'prpLdisabilityLimitRatingCode' && kindCode != '47' && kindCode != '48') {
 				//非47\48操作下拉框无效
 				return false;
 			}
 			$.getJSON(contextRootPath + "/compensate/calPrpLpersonLossRealpay.do", {
 					claimNo: claimNo,
 					kindCode: kindCode,
 					kindName: encodeURI(kindName),
 					ratingCode: ratingCode,
 					liabDetailCode: liabDetailCode,
 					medicDeathFlag: medicDeathFlag,
 					sumDefPay: sumDefPay,
 					compelPay: compelPay,
 					sumRest: sumRest,
 					indemnityDutyRate: indemnityDutyRate
 				},
 				function (data) {
 					if (data.prpLpersonLoss != null) {
 						var prpLpersonLoss = data.prpLpersonLoss;
 						var $sumRealPay = $prpLpersonFeeLossObject.find(":input[name='prpLpersonLossSumRealPay']");
 						var $sumDefPay = $prpLpersonFeeLossObject.find(":input[name='prpLpersonLossSumDefPay']");
 						if (field.name == 'prpLdisabilityLimitRatingCode') {
 							$sumDefPay.val(Math.round(prpLpersonLoss.sumRealPay));
 						} else {
 							cacheData($sumRealPay[0]); //缓存其本次计算前的值（超限则还原）
 						}
 						$sumRealPay.val(Math.round(prpLpersonLoss.sumRealPay));
						if($.trim(identifyNumber) != ""){//需求變更127調整，沒有錄入身份證號時，無需校驗是否超出限額
 						if (checkLimit(kindCode, "1", Math.round(prpLpersonLoss.sumRealPay), identifyNumber)) { //超出限额
 							if (field.name != 'prpLdisabilityLimitRatingCode') {
 								recoveryData(field); //还原当前改变项
 								recoveryData($sumRealPay[0]); //还原当前赔付额
 							} else {
 								$sumRealPay.val(0);
 							}
 						}
						}
 						calSumRealPay1(field);
 					} else if (data.errorMessage != null) {
 						alert(data.errorMessage);
 						window.setTimeout(function () {
 							$curr.focus();
 						});
 					}
 				});
 		}
 	}
 }

 //获取险别的险别的事故责任免赔率、自负额、赔付限额等信息 divPrpCitemKind 下

 function getPrpLlossPrpItemKind(field) {
 	var $prpLlossObject = $(field).parents("tr[name='prpLlossObject']");
 	var prpLlossDtoKindCode = $prpLlossObject.find(":input[name='prpLlossDtoKindCode']").val();
 	var prpLlossDtoKindName = $prpLlossObject.find(":input[name='prpLlossDtoKindName']").val();
 	if ($.trim(prpLlossDtoKindCode) != '' && prpLlossDtoKindName != '') {
 		var $limitObject = $("input[name='limitKindCode'][value='" + prpLlossDtoKindCode + "']").parents("div[name='limitObject']");
 		if ($limitObject.length > 0) {
 			var deductible = parseFloat($limitObject.find(":input[name='limitDeductible']").val());
 			var deductibleRate = parseFloat($limitObject.find(":input[name='limitDeductibleRate']").val());
 			var kindAmount = parseFloat($limitObject.find(":input[name='limitAmount']").val());
 			$prpLlossObject.find(":input[name='prpLlossDtoDeductible']").val(Math.round(deductible)); //设置自负额
 			$prpLlossObject.find(":input[name='prpLlossDtoDutyDeductibleRate']").val(deductibleRate); //设置自负额比例
 			$prpLlossObject.find(":input[name='prpLlossDtoDeductibleRate']").val(deductibleRate); //设置绝对免赔率
 			$prpLlossObject.find(":input[name='prpLlossDtoItemValue']").val(Math.round(kindAmount)); //设置标的价值，赔偿限额
 		}
 	} else {
 		clearPrpLloss(field);
 	}
 }
 //清空车物损 赔付

 function clearPrpLloss(field) {
 	var $prpLlossObject = $(field).parents("tr[name='prpLlossObject']");
 	$prpLlossObject.find(":input[name='prpLlossDtoSumDefPay']").val(0); //核定賠償
 	$prpLlossObject.find(":input[name='prpLlossDtoCompelPay']").val(0); //強制險給付金額
 	$prpLlossObject.find(":input[name='prpLlossDtoDeductible']").val(0); //自負額
 	$prpLlossObject.find(":input[name='prpLlossDtoDutyDeductibleRate']").val(0); //自負額比例
 	$prpLlossObject.find(":input[name='prpLlossDtoSumRealPay']").val(0); //賠償金額
 	calFundNew();
 }

 //增加一个车物损信息

 function insertPrpLlossObject(field) {
 	var $cloneObject = $("#prpLloss_Data").find("tr[name='prpLlossObject']").clone(true);
 	$cloneObject.find(":input[name='prpLlossDtoIndemnityDutyRate']").val($(":input[name='prpLcompensateIndemnityDutyRate']").val());
 	$cloneObject.appendTo("#PrpLloss");
 }
 //删除一个车物损信息

 function deletePrpLlossObject(field) {
 	$(field).parents("tr[name='prpLlossObject']").remove();
 	calFundNew();
 }

 //计算赔付信息

 function calFundNew() {
 	var dblSumDutyPaid = 0; //责任赔款合计（=（赔款费用附加信息中）计入赔款金额+（赔付标的附加信息中）赔偿金额+（赔付人员附加信息中）赔付合计）

 	var dblSumPaid = 0; //赔款总计（=责任赔款合计+其它费用）dblSumDutyPaid +  dblSumNoDutyFee
 	var dblSumPrePaid = 0; //预赔金额
 	var dblSumNoDutyFee = 0; //其它费用（（赔款费用附加信息中）费用金额 - 计入赔款金额）+ 独立处理费用 PrpLCharge.ChargeAmount-PrpLCharge.SumRealPay + PrpLcompensate.IndependentCosts
 	var dblSumThisPaid = 0; //实赔金额（=责任赔款合计－已预付赔款）dblSumPaid - dblSumPrePaid

 	var chargeRealPay = 0; //计入赔款的
 	var chargeAmount = 0; //实际费用
 	var prpLlossRealPay = 0;
 	var personLossRealPay = 0;
 	//车物损赔款
 	$("#PrpLloss").find("input[name='prpLlossDtoSumRealPay']").each(function () {
 		prpLlossRealPay += (isNaN($(this).val()) ? 0 : parseFloat($(this).val()));
 	});
 	//人伤赔款信息
 	$("#PrpLpersonLoss").find("input[name='prpLpersonLossSumRealPay']").each(function () {
 		personLossRealPay += (isNaN($(this).val()) ? 0 : parseFloat($(this).val()));
 	});
 	//费用资讯信息
 	$("#PrpLcharge").find("input[name='prpLchargeChargeAmount']").each(function () {
 		chargeAmount += (isNaN($(this).val()) ? 0 : parseFloat($(this).val()));
 	});
 	//费用资讯信息
 	$("#PrpLcharge").find("input[name='prpLchargeSumRealPay']").each(function () {
 		chargeRealPay += (isNaN($(this).val()) ? 0 : parseFloat($(this).val()));
 	});

 	dblSumDutyPaid = prpLlossRealPay + personLossRealPay + chargeRealPay;
 	//赔款金额：赔款金额之和，不包括费用
 	$(":input[name='prpLcompensateSumDutyPaid']").val(Math.round(dblSumDutyPaid));
 	//var $independentCosts = $(":input[name='prpLcompensateIndependentCosts']");
 	//费用金额：费用金额之和，不包括赔款金额 //+ 独立处理费用
 	dblSumNoDutyFee = chargeAmount - chargeRealPay;
 	$("input[name='prpLcompensateSumNoDutyFee']").val(Math.round(dblSumNoDutyFee));
 	//本案合计：赔款合计与费用之和
 	dblSumPaid = dblSumDutyPaid + dblSumNoDutyFee;
 	$(":input[name='prpLcompensateSumPaid']").val(Math.round(dblSumPaid));
 	//已预付赔款金额：预付赔款金额之和
 	dblSumPrePaid = parseFloat(fm.prpLcompensateSumPrePaid.value, 10);
 	if (isNaN(dblSumPrePaid)) {
 		dblSumPrePaid = 0;
 	}
 	//本次赔付金额：赔款合计减去已预付赔款
 	dblSumThisPaid = dblSumDutyPaid - dblSumPrePaid;
 	$(":input[name='prpLcompensateSumThisPaid']").val(Math.round(dblSumThisPaid));
 	setAccidentType();
 }

 /******************受害人资讯**********************************/
 //删除受害人资讯

 function deletePrpLpersonLossObject(field) {
 	$(field).parents("tr[name='prpLpersonLossObject']").remove();
 	calFundNew();
 	setPersonserialNo_new();
 }
 //新增受害人资讯

 function insertPrpLpersonLossObject() {
 	$("#Person_Data").find("tr[name='prpLpersonLossObject']").clone(true).appendTo("#PrpLpersonLoss");
 	setPersonserialNo_new();
 }
 //增加受害人费用信息

 function insertPrpLpersonFeeLossObject(field) {
 	var $prpLpersonLossObject = $(field).parents("tr[name='prpLpersonLossObject']");
 	var identifyNumber = $prpLpersonLossObject.find(":input[name='prpLpersonLossIdentifyNumber']").val(); //受害人身份证
 	var prpLpersonLossSex = $prpLpersonLossObject.find(":input[name='prpLpersonLossSex']").val(); //
 	var identityOfInjuredPerson = $prpLpersonLossObject.find(":input[name='prpLpersonLossIdentityOfInjuredPerson']").val(); //受害人身份
//	需求變更127調整，移除沒有身份證號亦可增加賠付費用訊息
//	if ($.trim(identifyNumber) == '' || (identityOfInjuredPerson == '1' && !checkIdentifyNumber(identifyNumber, prpLpersonLossSex))) {
//		alert("請爲受害人錄入正確的身份證號!");
//		return false;
//	}
 	var $cloneObject = $("#PersonFeeLoss_Data").find("tr[name='prpLpersonFeeLossObject']").clone(true);
 	$(field).parents("table[name='PrpLpersonFeeLoss']").children("tbody").append($cloneObject);
 	setPersonserialNo_new();
 	return true;
 }

 //删除受害人费用信息

 function deletePrpLpersonFeeLossObject(field) {
 	var $prpLpersonFeeLoss = $(field).parents("table[name='PrpLpersonFeeLoss']"); //当前受害人费用信息table
 	$(field).parents("tr[name='prpLpersonFeeLossObject']").remove(); //删除当前费用信息
 	//重新计算当前受害人的賠付金額
 	calSumRealPay1($prpLpersonFeeLoss[0]);
 	setPersonserialNo_new();
 }
 //重新计算序号

 function setPersonserialNo_new() {
 	var prpLpersonLossSerialNo = $("input[name='prpLpersonLossSerialNo']"); //重新计算
 	$.each($("table[name='PrpLpersonFeeLoss']"), function (i, n) {
 		if (i > 0) {
 			prpLpersonLossSerialNo[i].value = i;
 			var personLossSerialNo = $(n).find("input[name='personLossSerialNo']");
 			$.each(personLossSerialNo, function (j, m) {
 				m.value = prpLpersonLossSerialNo[i].value;
 			});
 		}
 	})

 }

 //人伤费用类型改变 清空当前条费用信息

 function clearPrpLpersonFeeLoss(field) {
 	if ($.trim(field.value) == "" || checkLiabDetailCode(field)) {
 		var $prpLpersonFeeLossObject = $(field).parents("tr[name='prpLpersonFeeLossObject']");
 		$prpLpersonFeeLossObject.find(":input[name='prpLdisabilityLimitRatingCode']").val(""); //残废等级
 		$prpLpersonFeeLossObject.find(":input[name='prpLpersonLossInjuryGrade']").val(""); //
 		//$prpLpersonFeeLossObject.find(":input[name='prpLpersonLossSumDefPay']").val(0); //核定賠償
 		$prpLpersonFeeLossObject.find(":input[name='prpLpersonLossCompelPay']").val(0); //強制險給付金額
 		$prpLpersonFeeLossObject.find(":input[name='prpLpersonLossSumRealPay']").val(0); //賠付金額
 		calSumRealPay1(field);
 	}
 }
 /**
  * 统计受害人數彙整
  */

 function countPersonLossNumber() {
 	//理赔人数
 	$(":input[name='personLossNumber']").val($("#PrpLpersonLoss").find(":input[name='prpLpersonLossSerialNo']").size());
 	var carNumber = new Array(0, 0, 0, 0); //本车伤亡情形([医疗,残废,死亡]) 保证顺序
 	var threeCarNumber = new Array(0, 0, 0, 0); //对方车伤残亡情形 [医疗,残废,死亡]
 	$("#PrpLpersonLoss").find("tr[name='prpLpersonLossObject']").each(function () {
 		var situation = $(this).find(":input[name='prpLpersonLossRideSituation']").val(); //乘坐情况
 		var casualties = $(this).find(":input[name='prpLpersonLossCasualties']").val(); //傷亡情形 
 		var index = parseInt(casualties); //1\医疗,2\残废,3\死亡
 		if (situation == '1' || situation == '6') { //本车
 			carNumber[index] = carNumber[index] + 1;
 		} else if (situation == '4' || situation == '5') { //對方車
 			threeCarNumber[index] = threeCarNumber[index] + 1;
 		}
 	});
 	//本车医疗、残废、死亡
 	$("#PersonLossNumberCount").find("input[name='carMedicalNumber']").val(carNumber[1]);
 	$("#PersonLossNumberCount").find("input[name='carCrippledNumber']").val(carNumber[2]);
 	$("#PersonLossNumberCount").find("input[name='carDeathNumber']").val(carNumber[3]);
 	//三者车医疗、残废、死亡
 	$("#PersonLossNumberCount").find("input[name='threeCarMedicalNumber']").val(threeCarNumber[1]);
 	$("#PersonLossNumberCount").find("input[name='threeCarCrippledNumber']").val(threeCarNumber[2]);
 	$("#PersonLossNumberCount").find("input[name='threeCarDeathNumber']").val(threeCarNumber[3]);

 }


 /**
  * 判断当前费用类别是否已赔付（当前受害人下不得存在2个同样的费用类别赔付）
  * @param field （当前费用讯息下任一DOM对象）
  * {Boolean} true 存在相同的费用类别
  */

 function checkLiabDetailCode(field) {
 	var $prpLpersonFeeLoss = $(field).parents("table[name='PrpLpersonFeeLoss']");
 	var $prpLpersonFeeLossObject = $(field).parents("tr[name='prpLpersonFeeLossObject']");
 	var $currDetailCode = $prpLpersonFeeLossObject.find(":input[name='prpLpersonLossLiabDetailCode']");
 	var $kindCode = $prpLpersonFeeLossObject.find(":input[name='prpLpersonLossKindCode']");
 	var $prpLpersonLossKindCode = $prpLpersonFeeLoss.find(":input[name='prpLpersonLossKindCode']")
 	var checkFlag = false;
 	$prpLpersonFeeLoss.find(":input[name='prpLpersonLossLiabDetailCode']").not($currDetailCode[0]).each(function (i, n) {
 		if ($.trim(n.value) == $.trim($currDetailCode.val()) && $prpLpersonLossKindCode[i].value == $kindCode.val()) {
 			alert("該費用類別已存在!");
 			$currDetailCode.val("");
 			$prpLpersonFeeLossObject.find(":input[name='prpLpersonLossLiabDetailName']").val("");
 			$prpLpersonFeeLossObject.find(":input[name='medicDeathFlag']").val("");
 			checkFlag = true;
 			return false;
 		}
 	});
 	return checkFlag;
 }

 //人伤险别改变时 清空费用信息、赔付合计
 function clearPrpLpersonLoss(field) {
	if($.trim(field.value)!=""){
		calRealpay2ForSunnyNew(field);
	}
	cacheData(field);
 	//calFundNew(); //本案赔款合计
 }

 //计算受害人赔付合计

 function calSumRealPay1(field) {
 	var $prpLpersonLossObject = $(field).parents("tr[name='prpLpersonLossObject']"); //当前受害人
 	var sumRealPay1 = 0;
 	$prpLpersonLossObject.find("input[name='prpLpersonLossSumRealPay']").each(function () {
 		sumRealPay1 += (isNaN($(this).val()) ? 0 : parseFloat($(this).val()));
 	});
 	$prpLpersonLossObject.find("input[name='prpLpersonLossSumRealPay1']").val(Math.round(sumRealPay1));
 	calFundNew(); //本案赔款合计
 }
 /**
  * 初始化汇总各受害人赔付金额合计
  */

 function initRealPay() {
 	$("#PrpLpersonLoss").find(":input[name='prpLpersonLossSumRealPay1']").each(function (i, field) {
 		var $prpLpersonLossObject = $(field).parents("tr[name='prpLpersonLossObject']"); //当前受害人
 		var sumRealPay1 = 0;
 		$prpLpersonLossObject.find("input[name='prpLpersonLossSumRealPay']").each(function () {
 			sumRealPay1 += (isNaN($(this).val()) ? 0 : parseFloat($(this).val()));
 		});
 		$(field).val(Math.round(sumRealPay1));
 	});
 }


 //人伤费用类型改变时 对残废等级的处理

 function setRatingCode(field) {
 	var $prpLpersonFeeLossObject = $(field).parents("tr[name='prpLpersonFeeLossObject']");
 	var medicDeathFlag = $prpLpersonFeeLossObject.find(":input[name='medicDeathFlag']");
 	var $ratingCode = $prpLpersonFeeLossObject.find(":input[name='prpLdisabilityLimitRatingCode']");
 }


 /*********************费用资讯***********************************/

 /**清空当前赔付费用帳户*/

 function clearPaymentNew(field) {
 	var $curr = $(field); //当前对象
 	var $chargeObject = $curr.parents("table[name='chargeObject']"); //当前操作的费用资讯table
 	$chargeObject.find("td[name='payFeeTD']").find("input[name^='prpLcharge']").val(""); //清空对象payFeeTD下所有input值
 	$chargeObject.find("input[name='prpLchargeChargeReport']").val(0); //清空費用金額
 	$chargeObject.find("input[name='prpLchargeChargeAmount']").val(0); //清空實際費用
 	calFundNew();
 }
 /**删除一个费用资讯信息*/

 function deleteChargeObject(field) {
 	var $curr = $(field); //当前对象
 	var $chargeObject = $curr.parents("table[name='chargeObject']"); //当前操作的费用资讯table
 	$chargeObject.remove(); //移除
 	calFundNew();
 }
 /**增加一个费用资讯信息*/

 function insertChargeObject(field) {
 	$("#ChargeTD").append($("#Charge_Data").html());
 }
 //获取支付对象

 function getPayObjectNew(field) {
 	var $chargeObject = $(field).parents("table[name='chargeObject']");
 	var prpLchargeChargeCode = $chargeObject.find(":input[name='prpLchargeChargeCode']").val(); //费用名称
 	var prpLchargePayObjectType = $chargeObject.find(":input[name='prpLchargePayObjectType']").val(); //支付类别
 	if (prpLchargeChargeCode == null || prpLchargeChargeCode == "") {
 		alert("請選擇費用名稱");
 		return;
 	} else {
 		var serialNo = $(":input[name='" + field.name + "']").index($(field));
 		var ownerName = field.value;
 		var url = "/claim/pages/common/account/PaymentAccountName.jsp?serialNo=" + serialNo + "&ownerName=" + ownerName;
 		var handle = window.showModalDialog(url, window, "dialogHide:yes;help:no;status:no;scroll:yes;dialogWidth:300px;dialogHeight:460px");
 		if (handle == null || handle == "") {
 			$chargeObject.find(":input[name='prpLchargePayObjectName']").val("");
 			$chargeObject.find(":input[name='prpLchargePayObjectName']").val("");
 		} else {
 			$chargeObject.find(":input[name='prpLchargePayObjectName']").val(handle);
 			//fm.prpLchargeOwnerName[serialNo].value = handle;
 			if ($chargeObject.find(":input[name='prpLchargeOwnerShip']").val() == 'B') { //汇款
 				getAccountByPayObjectName(serialNo, handle);
 			}
 		}
 	}
 }
 //费用获取帳户信息

 function getAccountByPayObjectName(serialNo, payObjectName) {
 	var submitStr = "AccountCode.do?actionType=SearchWithPayObjectName&ownerName=" + payObjectName + "&serialNo=" + serialNo;
 	window.open(submitStr, '', 'resizable=1,scrollbars=yes,overflow=scroll,width=600,height=600');
 }
 //费用信息项发生改变 

 function setRealPayNew(field) {
 	if (isChange(field)) {
 		var checkFlag = true; //默认通过检验
 		var $chargeObject = $(field).parents("table[name='chargeObject']");
 		if (field.name == 'prpLchargeChargeReport') {
 			$chargeObject.find(":input[name='prpLchargeChargeAmount']").val(field.value);
 		} else if (field.name == 'prpLchargeChargeAmount') {
 			if (parseFloat(field.value) > parseFloat($chargeObject.find(":input[name='prpLchargeChargeReport']").val())) {
 				recoveryData(field); //否则恢复数据
 				checkFlag = alertMessage(field, "實際費用不能大於费用金额!");
 			}
 		}
 		if (checkFlag) { //有改变且校验通过，则重新计算赔付额
 			calFundNew();
 		}
 	}
 }
 //修改支付对象名称时，自动修改支付对象

 function setPrpLchargeOwnerName(field) {
 	var $chargeObject = $(field).parents("table[name='chargeObject']");
 	$chargeObject.find(":input[name='prpLchargeOwnerName']").val(field.value);
 }

 /*************************赔付对象信息*********************************/

 function deletePrpLpayObjectInfo(field) { //删除一个支付对象
 	var $prpLpayObjectInfo = $(field).parents("tr[name='PrpLpayObjectInfo']");
 	var index = 0;
 	$prpLpayObjectInfo.nextAll("tr[name='PrpLpayObjectInfo']").each(function () {
 		var $serialNo = $(this).find("input[name='prpLpayObjectInfoSerialNo']");
 		if (index == 0) {
 			index = parseInt($serialNo.val()) - 1;
 		}
 		$(this).find("span[name='payObjectIndex']").html($serialNo.val() - 1);
 		$serialNo.val($serialNo.val() - 1);
 	});
 	$prpLpayObjectInfo.remove();
 	if (index == 0) {
 		index = $.find("input[name='prpLpayObjectInfoSerialNo']").length;
 	}
 	$.each($.find("input[name='prpLlossPayObjectSerialNo']"), function (i, n) {
 		if (i > 0 && n.value != "") {
 			var payObjectValue = n.value.split(";");
 			var payObjectValueTemp = "";
 			for (var i = 0; i < payObjectValue.length; i++) {
 				var payObjectTemp = payObjectValue[i].split(":");
 				if (index < parseInt(payObjectTemp[0])) {
 					payObjectValueTemp += (parseInt(payObjectTemp[0]) - 1) + ":" + payObjectTemp[1] + ";";
 				} else if (index > parseInt(payObjectTemp[0])) {
 					payObjectValueTemp += payObjectValue[i] + ";";
 				}
 			}
 			if (payObjectValueTemp != "") {
 				payObjectValueTemp = payObjectValueTemp.substring(0, payObjectValueTemp.length - 1);
 			}
 			n.value = payObjectValueTemp;
 		}
 	});
 	$.each($.find("input[name='prpLpersonLossPayObjectSerialNo']"), function (i, n) {
 		if (i > 0 && n.value != "") {
 			var payObjectValue = n.value.split(";");
 			var payObjectValueTemp = "";
 			for (var i = 0; i < payObjectValue.length; i++) {
 				var payObjectTemp = payObjectValue[i].split(":");
 				if (index < parseInt(payObjectTemp[0])) {
 					payObjectValueTemp += (parseInt(payObjectTemp[0]) - 1) + ":" + payObjectTemp[1] + ";";
 				} else if (index > parseInt(payObjectTemp[0])) {
 					payObjectValueTemp += payObjectValue[i] + ";";
 				}
 			}
 			if (payObjectValueTemp != "") {
 				payObjectValueTemp = payObjectValueTemp.substring(0, payObjectValueTemp.length - 1);
 			}
 			n.value = payObjectValueTemp;
 		}
 	});
 	uLprpLPayObjectinfo();
 }

 function insertPrpLpayObjectInfo() { //添加一个支付对象
 	var $prpLpayObjectInfo = $("#PrpLpayObjectInfo_Data").find("tr[name='PrpLpayObjectInfo']").clone(true);
 	var serialNo = $("#PayAccountInfo").find("tr[name='PrpLpayObjectInfo']").length;
 	$prpLpayObjectInfo.find("span[name='payObjectIndex']").html(serialNo + 1);
 	$prpLpayObjectInfo.find("input[name='prpLpayObjectInfoSerialNo']").val(serialNo + 1);
 	$prpLpayObjectInfo.appendTo("#PayAccountInfo");
 	uLprpLPayObjectinfo();
 	$("div[name='payObject']").eq(1).show();
 }

 function payObjectInfoOwnerShip(field) { //修改支付方式
 	var $ownerShip = $(field);
 	var $prpLpayObjectInfo = $ownerShip.parents("tr[name='PrpLpayObjectInfo']");
 	if ($ownerShip.val() == "B") { //汇款
 		$prpLpayObjectInfo.find("span[name='spanCutBack']").hide(); //隐藏禁背
 		$prpLpayObjectInfo.find("tr[name='bankInfo']").show(); //开放银行帳户录入
 	} else {
 		$prpLpayObjectInfo.find("tr[name='bankInfo']").hide(); //关闭银行帳户录入
 	}
 	if ($ownerShip.val() == "Q") { //支票
 		$prpLpayObjectInfo.find("span[name='spanCutBack']").show(); //显示禁背
 		$prpLpayObjectInfo.find("tr[name='bankInfo']").hide(); //隐藏银行帳户录入
 	} else {
 		$prpLpayObjectInfo.find("span[name='spanCutBack']").hide(); //隐藏禁背
 	}
 }

 function queryUserNew(field) {
 	var registNo = $(":input[name='prpLregistExtRegistNo']").val();
 	var btnName = field.name;
 	var ownerName = "";
 	var actionType = "";
	var uniformNo = "";
	var certificateCode = "";
	var accountCode = "";
 	var serialno = $(":input[name='" + btnName + "']").index($(field));
 	if ('buttonAddPrpLpayObjectInfo' == btnName) {
 		actionType = "queryUserCom";
		var $tr = $(field).closest("tr[name='PrpLpayObjectInfo']");
		uniformNo = $tr.find(":input[name='prpLpayObjectInfoUniformNo']").val();
		certificateCode = $tr.find(":input[name='prpLpayObjectInfoCertificateCode']").val();
		accountCode= $tr.find(":input[name='prpLpayObjectInfoAccountCode']").val();
 	} else if ('buttonAddPrpLcharge' == btnName) {
 		actionType = "queryUser";
		var $tb = $(field).closest("table[name='chargeObject']");
		uniformNo = $tb.find(":input[name='prpLchargeUniformNo']").val();
		certificateCode = $tb.find(":input[name='prpLchargeCertificateCode']").val();
		accountCode= $tb.find(":input[name='prpLchargeAccountCode']").val();
 	}
 	if (actionType != "") {
 		//mantis：CLM0062 ，處理人員：BK007 蘇哲，需求單編號：CLM0062.AML換新的理賠新核心 開始
 		var friskCode = fm.riskcode || fm.riskCode;
 		var riskcode = friskCode.value;
		var submitStr = "AccountCode.do?registNo=" + registNo + "&serialNo=" + serialno + "&actionType=" + actionType + "&certificateCode=" + certificateCode+ "&uniformNo=" + uniformNo+ "&accountCode=" + accountCode + "&riskCode="+riskcode;
		//mantis：CLM0062 ，處理人員：BK007 蘇哲，需求單編號：CLM0062.AML換新的理賠新核心 結束
		window.open(submitStr, "Print", "resizable=1,scrollbars=yes,overflow=scroll,width=980,height=600");
 	}
 }

 /************************表单校验********************************/

 //检查赔付金额之和是否等於 本次賠付金額

 function checkSumPayAmount() {
 	//总赔付金额
 	var sumPayAmount = 0;
 	$("#PayAccountInfo").find(":input[name='prpLpayObjectInfoPayAmount']").each(function () {
 		sumPayAmount += (isNaN($(this).val()) ? 0 : parseFloat($(this).val()));
 	});
 	var prpLlossRealPay = 0;
 	//车物损赔款
 	$("#PrpLloss").find("input[name='prpLlossDtoSumRealPay']").each(function () {
 		prpLlossRealPay += (isNaN($(this).val()) ? 0 : parseFloat($(this).val()));
 	});
 	var personLossRealPay = 0;
 	//人伤赔款信息
 	$("#PrpLpersonLoss").find("input[name='prpLpersonLossSumRealPay1']").each(function () {
 		personLossRealPay += (isNaN($(this).val()) ? 0 : parseFloat($(this).val()));
 	});
 	if (sumPayAmount != (prpLlossRealPay + personLossRealPay)) {
 		alert("C2=("+sumPayAmount+") 比較( prpLlossRealPay="+prpLlossRealPay+"+A2="+personLossRealPay+")"+"賠付對象的總理賠金額與本次賠付金額不相等!");
 		//alert("賠付對象的總理賠金額與本次賠付金額不相等!");
 		return false;
 	}
 	return true;
 }
 //校验车物损信息录入

 function checkPrpLloss() {
 	var checkFlag = true;
 	$("#PrpLloss").find("tr[name='prpLlossObject']").each(function (i) {
 		var $prpLlossDtoKindCode = $(this).find(":input[name='prpLlossDtoKindCode']");
 		var $prpLlossDtoKindName = $(this).find(":input[name='prpLlossDtoKindName']");
 		var $prpLlossDtoLicenseNo = $(this).find(":input[name='prpLlossDtoLicenseNo']");
 		var $prpLlossDtoLossName = $(this).find(":input[name='prpLlossDtoLossName']");
 		if ($.trim($prpLlossDtoKindCode.val()) == '' || $.trim($prpLlossDtoKindName.val()) == '') {
 			checkFlag = alertMessage($prpLlossDtoKindCode[0], "第 " + (i + 1) + " 條車損、物損賠付資訊‘險別’必須輸入!");
 			return false; //跳出循环
 		} else if ($.trim($prpLlossDtoLicenseNo.val()) == '') {
 			checkFlag = alertMessage($prpLlossDtoLicenseNo[0], "第 " + (i + 1) + " 條車損、物損賠付資訊‘牌照號碼’必須輸入!");
 			return false;
 		} else if ($.trim($prpLlossDtoLossName.val()) == '') {
 			checkFlag = alertMessage($prpLlossDtoLossName[0], "第 " + (i + 1) + " 條車損、物損賠付資訊‘財物名稱’必須輸入!");
 			return false;
 		} else {
 			var re = /^[\u4e00-\u9fa5a-zA-Z0-9- ]{1,12}$/;
 			if ($.trim($prpLlossDtoLicenseNo.val()).search(re) == -1) {
 				checkFlag = alertMessage($prpLlossDtoLossName[0], "第" + (i + 1) + " 條車損、物損賠付資訊‘牌照號碼’格式不正確!");
 				return false;
 			}
 		}
 	});
 	return checkFlag;
 }
 
 //mantis：CLM0155，處理人員：DP0713，車體險自負額有責任時卡控自負額發票號碼必輸
 function checkPrpLloss2() {
	 	var checkFlag = true;
	 	var claimNo = $(":input[name='prpLcompensateClaimNo']").val();
	 	var $indemnityDutyRate = $(":input[name='prpLcompensateIndemnityDutyRate']"); //本車肇責百分比
	 	var rate = parseInt($indemnityDutyRate.val());
	 	var riskCode = $("input[name='prpLcompensateRiskCode']").val();
	 	
	 	$("#PrpLloss").find("tr[name='prpLlossObject']").each(function (i) {
	 		var $prpLlossDtoKindCode = $(this).find(":input[name='prpLlossDtoKindCode']");
			var deductible = $(this).find(":input[name='prpLlossDtoDeductible']");//自負額
	 		var dutyDeductibleRate = $(this).find(":input[name='prpLlossDtoDutyDeductibleRate']"); //自负额比例
	 		var sumDefPay = $(this).find(":input[name='prpLlossDtoSumDefPay']"); //核定赔偿
 			var deductibletype = "";
	 			
 			if(riskCode=="A01" && rate!=0){//僅任意險
	 			$.ajax({
	 				type : 'POST',
	 				url : contextRootPath + "/compensate/checkPrpLlossDeductibletype.do?" 
	 						+"prpLcompensateClaimNo="+claimNo+"&"
	 						+"prpLlossKindCode="+$prpLlossDtoKindCode.val(),
	 				async : false,
	 				cache : false,
	 				dataType: "json",
	 				contentType: "application/json; charset=utf-8",
	 				success : function(data) {
	 					deductibletype = data.deductibletype;
	 					
//		 				alert(riskCode+"/"+claimNo+"/"+$prpLlossDtoKindCode.val()+ " deductibletype is "+deductibletype);
	 					if(undefined!=deductibletype && null!=deductibletype && $prpLlossDtoKindCode.val()!='07'){
	 						var checkDeductibleType = false;
	 						if(deductibletype=="1" ||deductibletype=="2"){//自負額單位類別為1、2則'自負額'不可等於0
	 							checkDeductibleType = true;
	 							if(undefined==deductible && null!=deductible && $.trim(deductible.val())=='' || $.trim(deductible.val())=='0'){
	 								alert("請確實輸入本車理賠自負額金額。");
	 								checkFlag= false;
	 							}
	 						}else if(deductibletype=="3"){//當自負額單位類別為3時'自負額比率'不可為0
	 							checkDeductibleType = true;
	 							if(undefined==dutyDeductibleRate && null!=dutyDeductibleRate && $.trim(dutyDeductibleRate.val())=='' || $.trim(dutyDeductibleRate.val())=='0'){
	 								alert("請確實輸入本車理賠自負額比率。");
	 								checkFlag= false;
	 							}
	 						}
	 						
	 						
	 						if(checkDeductibleType && checkFlag){
	 							//核定賠償
	 							if(undefined==sumDefPay && null!=sumDefPay && $.trim(sumDefPay.val())=='' || $.trim(sumDefPay.val())=='0'){
	 								alert("請確實輸入核定賠償金額。");
	 								checkFlag= false;
	 							}
	 						}
	 						if(checkDeductibleType && checkFlag){
		 						//(3)	車損、物損有寫入賠付資訊並且寫入有承保自負額險種，則該案件自負額大於0時，發票號碼必須輸入
		 						var $carInsurance = $("#spanCarInsurance").find("tr[name='CarInsuranceObject']");
		 						if($carInsurance.length == 0 ){
		 							alert("請確實輸入自負額發票號碼。");
	//	 							checkFlag= false;//CLM0155 暫時僅顯示不擋
		 						}else{
			 						var checkDuctibleInvoice = false;
			 						$("#spanCarInsurance").find("tr[name='CarInsuranceObject']").each(function(i,e){
			 							var deductibleInvoice = $(e).find(":input[name='prpLcarInsuranceDeductibleInvoice']").val();
			 							if($.trim(deductibleInvoice).length != 0 ){
			 								checkDuctibleInvoice = true;
			 							}
			 						});
			 						if(!checkDuctibleInvoice){
			 							alert("請確實輸入自負額發票號碼。");
//			 							checkFlag= false;//CLM0155 暫時僅顯示不擋
			 						}
		 						}
	 						}
	 					}
	 				},
	 				error: function (jqXHR, textStatus, errorThrown) { 
	 					alert("saveForm ajax Error:"+errorThrown); 
	 				}
	 			});
 			}
	 	});
	 	return checkFlag;
	 }
 //校验受害人資訊信息录入

 function checkPrpLpersonLoss() {
 	var checkFlag = true;
 	$("#PrpLpersonLoss").find("tr[name='prpLpersonLossObject']").each(function (i, personLossObject) {
 		var $prpLpersonLossPersonName = $(personLossObject).find(":input[name='prpLpersonLossPersonName']"); //受害人
 		var $prpLpersonLossBirthday = $(personLossObject).find(":input[name='prpLpersonLossBirthday']"); //出生年份
 		var $prpLpersonLossPayObjectSerialNo = $(personLossObject).find(":input[name='prpLpersonLossPayObjectSerialNo']"); //赔付对象讯息
 		var $prpLpersonLossIdentifyNumber = $(personLossObject).find(":input[name='prpLpersonLossIdentifyNumber']"); //身份证号
		var sumRealPay1 = $(personLossObject).find(":input[name='prpLpersonLossSumRealPay1']").val(); //被保險人賠付金額
 		var prpLpersonLossSex = $(personLossObject).find(":input[name='prpLpersonLossSex']").val(); //性别
 		var prpLpersonLossIdentityOfInjuredPerson = $(personLossObject).find(":input[name='prpLpersonLossIdentityOfInjuredPerson']").val(); //受害人身份
		if(parseFloat(sumRealPay1) > 0 ){//需求變更127，賠付金額>0，則於暫存及提交時檢核人員姓名、出生年分、身分證號此三個欄位須必輸有值
 		if ($.trim($prpLpersonLossPersonName.val()) == '') {
 			checkFlag = alertMessage($prpLpersonLossPersonName[0], "第 " + (i + 1) + " 条受害人資訊‘人員姓名’必須輸入!");
 			return false; //跳出each
 		} else if ($.trim($prpLpersonLossBirthday.val()) == '') {
 			checkFlag = alertMessage($prpLpersonLossBirthday[0], "第 " + (i + 1) + " 条受害人資訊‘出生年份’必須輸入!");
 			return false; //跳出each
 		}else if ($.trim($prpLpersonLossIdentifyNumber.val()) == '') {
				checkFlag = alertMessage($prpLpersonLossIdentifyNumber[0], "第 " + (i + 1) + " 条受害人資訊‘身份證號’必須輸入!");
 			return false; //跳出each
			}
		}
		if ($.trim($prpLpersonLossIdentifyNumber.val()) != '' && prpLpersonLossIdentityOfInjuredPerson == "1" 
			&& !checkIdentifyNumber($prpLpersonLossIdentifyNumber.val(), prpLpersonLossSex)) {
 			checkFlag = alertMessage($prpLpersonLossIdentifyNumber[0], "第 " + (i + 1) + " 条受害人資訊‘身份證號’输入不正确!");
 			return false; //跳出each
 		} else {
 			$(personLossObject).find("tr[name='prpLpersonFeeLossObject']").each(function (j, prpLpersonFeeLossObject) {
 				var $prpLpersonLossKindCode = $(prpLpersonFeeLossObject).find(":input[name='prpLpersonLossKindCode']"); //险别代码
 				var $prpLpersonLossKindName = $(prpLpersonFeeLossObject).find(":input[name='prpLpersonLossKindName']"); //险别名称
 				var $prpLpersonLossLiabDetailCode = $(prpLpersonFeeLossObject).find(":input[name='prpLpersonLossLiabDetailCode']"); //人伤费用类别代码
 				var $prpLpersonLossLiabDetailName = $(prpLpersonFeeLossObject).find(":input[name='prpLpersonLossLiabDetailName']"); //人伤费用类别名称
 				if ($.trim($prpLpersonLossKindCode.val()) == '' || $.trim($prpLpersonLossKindName.val()) == '') {
 					checkFlag = alertMessage($prpLpersonLossKindCode[0], "第 " + (i + 1) + " 条受害人資訊‘險別’必須輸入!");
 					return false; //跳出each
 				} else if ($prpLpersonLossLiabDetailCode.val() == '' || $prpLpersonLossLiabDetailName.val() == '') {
 					checkFlag = alertMessage($prpLpersonLossLiabDetailCode[0], "第 " + (i + 1) + " 条受害人資訊,第 " + (j + 1) + " 条費用資訊 ‘費用類別’必須輸入!");
 					return false; //跳出子each
 				}
 			});
 			return checkFlag; //true 则continue each() false 则 break each()
 		}
 	});
 	return checkFlag; //这个才是函数的校验结果
 }
 //校验費用資訊

 function checkPrpLcharge() {
 	var checkFlag = true;
 	$("#ChargeTD").find("table[name='chargeObject']").each(function (i) {
 		var $prpLchargeKindCode = $(this).find(":input[name='prpLchargeKindCode']"); //险别代码
 		var $prpLchargeKindName = $(this).find(":input[name='prpLchargeKindName']"); //险别名称
 		var $prpLchargeChargeCode = $(this).find(":input[name='prpLchargeChargeCode']"); //费用名称
 		var $prpLchargeChargeName = $(this).find(":input[name='prpLchargeChargeName']");
 		var $prpLchargePayObjectName = $(this).find(":input[name='prpLchargeKindCode']"); //支付對象名稱
 		var $prpLchargeOwnerName = $(this).find(":input[name='prpLchargeOwnerName']"); //賠付對象
 		var $prpLchargeUniformNo = $(this).find(":input[name='prpLchargeUniformNo']"); //ID/統一編號

 		var $prpLchargeOwnerShip = $(this).find(":input[name='prpLchargeOwnerShip']"); //費用支付方式
 		var $prpLchargeBankCode = $(this).find(":input[name='prpLchargeBankCode']"); //總行代號
 		var $prpLchargeBankName = $(this).find(":input[name='prpLchargeBankName']"); //總行名稱
 		var $prpLchargeAccountCode = $(this).find(":input[name='prpLchargeAccountCode']"); //匯款帳號
 		var $prpLchargeCustomBankCode = $(this).find(":input[name='prpLchargeCustomBankCode']"); //分行代號
 		var $prpLchargeCustomBankName = $(this).find(":input[name='prpLchargeCustomBankName']"); //分行名稱
 		var $prpLchargeAreaCode = $(this).find(":input[name='prpLchargeAreaCode']"); //郵遞區號
 		var $prpLchargeCourierAddress = $(this).find(":input[name='prpLchargeCourierAddress']"); //郵遞地址
 		//mantis： CLM0096 ，處理人員：BK007 蘇哲，需求單編號：CLM0096.新核心-給付對象資訊郵遞區號卡控數值-start
 		var oAreaCode = trim($prpLchargeAreaCode.val());
		var areaCode = trim($prpLchargeAreaCode.val()).replace(/[^\d]/g,'');
		if(oAreaCode != areaCode){
			alert("第 " + (i + 1) + " 条費用資訊‘郵遞區號’ 只能輸入數值!");
			checkFlag = false;
			return false; //
		} else if ($prpLchargeKindCode.val() == '' || $prpLchargeKindName.val() == '') {
		//mantis： CLM0096 ，處理人員：BK007 蘇哲，需求單編號：CLM0096.新核心-給付對象資訊郵遞區號卡控數值-end
 			checkFlag = alertMessage($prpLchargeKindCode[0], "第 " + (i + 1) + " 条費用資訊‘險別代碼’、‘險別名稱’必須輸入!");
 			return false; //跳出each
 		} else if ($prpLchargeChargeCode.val() == '') {
 			checkFlag = alertMessage($prpLchargeChargeCode[0], "第 " + (i + 1) + " 条費用資訊‘費用名稱’必須輸入!");
 			return false; //跳出each
 		} else if ($.trim($prpLchargePayObjectName.val()) == '' || $.trim($prpLchargeOwnerName.val()) == '') {
 			checkFlag = alertMessage($prpLchargeOwnerName[0], "第 " + (i + 1) + " 条費用資訊‘支付對象名稱’必須輸入!");
 			return false; //跳出each
 		} else if ($prpLchargeUniformNo.val() == '') {
 			checkFlag = alertMessage($prpLchargeUniformNo[0], "第 " + (i + 1) + " 条費用資訊‘統一編號/身份證號’必須輸入!");
 			return false; //跳出each
 		} else if ($prpLchargeOwnerShip.val() == 'B' && ($.trim($prpLchargeAccountCode.val()) == '' || $.trim($prpLchargeBankCode.val()) == '' || $.trim($prpLchargeBankName.val()) == '' || $.trim($prpLchargeCustomBankCode.val()) == '' || $.trim($prpLchargeCustomBankCode.val()) == '')) {
 			alert("第 " + (i + 1) + " 条費用支付方式爲匯款，必須輸入費用支付帳戶資訊!");
 			checkFlag = false;
 			return false; //跳出each
 		} else if ($.trim($prpLchargeAreaCode.val()) == '' || $.trim($prpLchargeCourierAddress.val()) == '') {
 			alert("第 " + (i + 1) + " 条費用資訊‘郵遞區號’、‘郵遞地址’必須輸入!");
 			checkFlag = false;
 			return false; //跳出each
 		}
 	});
 	return checkFlag;
 }
 //校验赔付对象

 function checkPrpLpayObjectInfo() {
 	var checkFlag = true;
 	$("#PayAccountInfo").find("tr[name='PrpLpayObjectInfo']").each(function (i) {
 		var $prpLpayObjectInfoOwnerName = $(this).find(":input[name='prpLpayObjectInfoOwnerName']"); //賠付對象
 		var $prpLpayObjectInfoPaymentKind = $(this).find(":input[name='prpLpayObjectInfoPaymentKind']"); //費用類型
 		var $prpLpayObjectInfoUniformNo = $(this).find(":input[name='prpLpayObjectInfoUniformNo']"); //ID/統一編號
 		var $prpLpayObjectInfoOwnerShip = $(this).find(":input[name='prpLpayObjectInfoOwnerShip']"); //費用支付方式

 		var $prpLpayObjectInfoBeneficiaryPhone = $(this).find(":input[name='prpLpayObjectInfoBeneficiaryPhone']"); //受款人電話
 		var $prpLpayObjectInfoBankCode = $(this).find(":input[name='prpLpayObjectInfoBankCode']"); //總行代號
 		var $prpLpayObjectInfoBankName = $(this).find(":input[name='prpLpayObjectInfoBankName']"); //總行名稱
 		var $prpLpayObjectInfoAccountCode = $(this).find(":input[name='prpLpayObjectInfoAccountCode']"); //匯款帳號
 		var $prpLpayObjectInfoCustomBankCode = $(this).find(":input[name='prpLpayObjectInfoCustomBankCode']"); //分行代號
 		var $prpLpayObjectInfoCustomBankName = $(this).find(":input[name='prpLpayObjectInfoCustomBankName']"); //分行名稱
 		var $prpLpayObjectInfoAreaCode = $(this).find(":input[name='prpLpayObjectInfoAreaCode']"); //郵遞區號
 		var $prpLpayObjectInfoCourierAddress = $(this).find(":input[name='prpLpayObjectInfoCourierAddress']"); //郵遞地址
 		//mantis： CLM0096 ，處理人員：BK007 蘇哲，需求單編號：CLM0096.新核心-給付對象資訊郵遞區號卡控數值-start
 		var oAreaCode = trim($prpLpayObjectInfoAreaCode.val());
		var areaCode = trim($prpLpayObjectInfoAreaCode.val()).replace(/[^\d]/g,'');
		if(oAreaCode != areaCode){
			alert("賠付對象 " + (i + 1) + " ‘郵遞區號’ 只能輸入數值!");
			checkFlag = false;
			return false; //
		} else if ($.trim($prpLpayObjectInfoOwnerName.val()) == '') {
		//mantis： CLM0096 ，處理人員：BK007 蘇哲，需求單編號：CLM0096.新核心-給付對象資訊郵遞區號卡控數值-end
			checkFlag = alertMessage($prpLpayObjectInfoOwnerName[0], "賠付對象 " + (i + 1) + " ‘賠付對象’必須輸入!");
 			return false; //跳出each
 		} else if ($prpLpayObjectInfoPaymentKind.val() == '') {
 			alert("賠付對象 " + (i + 1) + " ‘費用類型’必須輸入!");
 			checkFlag = false;
 			return false; //跳出each
 		} else if ($prpLpayObjectInfoUniformNo.val() == '') {
 			alert("賠付對象 " + (i + 1) + " ‘統一編號/身份證號’必須輸入!");
 			checkFlag = false;
 			return false; //跳出each
 		} else if ($prpLpayObjectInfoBeneficiaryPhone.val() == '') {
 			alert("賠付對象 " + (i + 1) + " ‘受款人電話’必須輸入!");
 			checkFlag = false;
 			return false; //跳出each
 		} else if ($prpLpayObjectInfoOwnerShip.val() == 'B' && ($.trim($prpLpayObjectInfoBankCode.val()) == '' || $.trim($prpLpayObjectInfoBankName.val()) == '' || $.trim($prpLpayObjectInfoAccountCode.val()) == '' || $.trim($prpLpayObjectInfoCustomBankCode.val()) == '' || $.trim($prpLpayObjectInfoCustomBankName.val()) == '')) {
 			alert("賠付對象 " + (i + 1) + " 費用支付方式爲匯款，必須輸入支付帳戶資訊!");
 			checkFlag = false;
 			return false; //跳出each
 		} else if ($.trim($prpLpayObjectInfoAreaCode.val()) == '' || $.trim($prpLpayObjectInfoCourierAddress.val()) == '') {
 			alert("賠付對象 " + (i + 1) + " ‘郵遞區號’、‘郵遞地址’必須輸入!");
 			checkFlag = false;
 			return false; //跳出each
 		}
 	});
 	//验证prpLlossPayObjectSerialNo支付对象是否录入
 	//prpLpersonLossPayObjectSerialNo 支付对象的信息是否存在
 	var $prpLpayObjectInfoPayAmount = $.find(":input[name='prpLpayObjectInfoPayAmount']"); //賠付對象
 	var serialNo = $prpLpayObjectInfoPayAmount.length;
 	var $prpLlossPayObjectSerialNo = $.find(":input[name='prpLlossPayObjectSerialNo']"); //賠付序号
 	var $prpLlossDtoSumRealPay = $.find(":input[name='prpLlossDtoSumRealPay']"); //賠付金额
 	//去掉预陪的金额
 	var prpLcompensateSumPrePaid = $.find(":input[name='prpLcompensateSumPrePaid']")[0].value;
 	if (null != prpLcompensateSumPrePaid && "" != prpLcompensateSumPrePaid) {
 		prpLcompensateSumPrePaid = parseFloat(prpLcompensateSumPrePaid);
 	} else {
 		prpLcompensateSumPrePaid = 0;
 	}
 	var message = "";
 	var payAmount = new Array(serialNo);
 	for (var i = 0; i < payAmount.length; i++) {
 		payAmount[i] = 0;
 	}
 	$.each($prpLlossPayObjectSerialNo, function (i, n) {
 		if (i > 0) {
 			var prpLpersonAmount = 0;
 			if (n.value != "") {
 				var payObjectValue = n.value.split(";");
 				for (var j = 0; j < payObjectValue.length; j++) {
 					var payObjectValueTemp = payObjectValue[j].split(":");
 					var payObjectSerialNo = parseInt(payObjectValueTemp[0]);
 					var payObjectAmount = parseFloat(payObjectValueTemp[1]);
 					payAmount[payObjectSerialNo] += payObjectAmount;
 					prpLpersonAmount += payObjectAmount;
 				}
 			}
 			if (parseFloat($prpLlossDtoSumRealPay[i].value) != prpLpersonAmount && parseFloat($prpLlossDtoSumRealPay[i].value) != prpLpersonAmount + prpLcompensateSumPrePaid) {
 				message += "第" + i + "筆車損、物損支付對象訊息金額不等，請重新輸入!\n";
 			}
 		}
 	});
 	var $prpLpersonLossSumRealPay1 = $.find(":input[name='prpLpersonLossSumRealPay1']"); //賠付金额
 	var $prpLpersonFeeLoss = $.find("table[name='PrpLpersonFeeLoss']"); //费用
 	$.each($prpLpersonFeeLoss, function (j, n) {
 		if (j > 0) {
 			var $prpLpersonLossPayObjectSerialNo = $(n).find(":input[name='prpLpersonLossPayObjectSerialNo']"); //賠付赔付序号
 			var prpLpersonLossSumRealPay = $(n).find(":input[name='prpLpersonLossSumRealPay']"); //賠付金额
 			var sumRealPay1 = 0;
 			$.each($prpLpersonLossPayObjectSerialNo, function (i, n) {
 				var prpLpersonAmount = 0;
 				if (n.value != "") {
 					var payObjectValue = n.value.split(";");
 					for (var j = 0; j < payObjectValue.length; j++) {
 						var payObjectValueTemp = payObjectValue[j].split(":");
 						var payObjectSerialNo = parseInt(payObjectValueTemp[0]);
 						var payObjectAmount = parseFloat(payObjectValueTemp[1]);
 						payAmount[payObjectSerialNo] += payObjectAmount;
 						prpLpersonAmount += payObjectAmount;
 					}
 				}
 				sumRealPay1 += parseFloat(prpLpersonLossSumRealPay[i].value);
 				if (parseFloat(prpLpersonLossSumRealPay[i].value) != prpLpersonAmount && parseFloat(prpLpersonLossSumRealPay[i].value) != prpLpersonAmount + prpLcompensateSumPrePaid) {
 					message += "第" + j + "筆受害人訊息支付對象訊息金額不等，請重新輸入!\n";
 				}
 			});
 			if (parseFloat($prpLpersonLossSumRealPay1[j].value) != sumRealPay1) {
 				message += "第" + j + "筆受害人賠付金額合計不等於個分項之和!\n";
 			}
 		}
 	})
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
 		checkFlag = false;
 		return false;
 	}
 	return checkFlag;
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
 		var $prpLlossPayObjectSerialNo = $.find(":input[name='prpLlossPayObjectSerialNo']"); //賠付對象
 		var $prpLlossDtoSumRealPay = $.find(":input[name='prpLlossDtoSumRealPay']"); //賠付對象
 		$.each($prpLlossPayObjectSerialNo, function (i, n) {
 			if (i > 0) {
 				if (isInteger(n.value)) {
						 if(jQuery.isNumeric($prpLlossDtoSumRealPay[i].value)){
 						payAmount[parseInt(n.value)] += parseFloat($prpLlossDtoSumRealPay[i].value);
 					}
 				}
 			}
 		});

 		var $prpLpersonLossPayObjectSerialNo = $.find(":input[name='prpLpersonLossPayObjectSerialNo']"); //賠付對象
 		var $prpLpersonLossSumRealPay1 = $.find(":input[name='prpLpersonLossSumRealPay1']"); //賠付對象
 		$.each($prpLpersonLossPayObjectSerialNo, function (i, n) {
 			if (i > 0) {
 				if (isInteger(n.value)) {
						 if(jQuery.isNumeric($prpLpersonLossSumRealPay1[i].value)){
 						payAmount[parseInt(n.value)] += parseFloat($prpLpersonLossSumRealPay1[i].value);
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
 		alert("妳輸入的賠付對象不存在，請重新輸入。");
 		return false;
 	}
 }

 //校验基本信息界面录入

 function checkMainPage() {
	//mantis： CLM0091 ，處理人員：BK007 蘇哲，需求單編號：CLM0091.新核心-理算文件齊全日 -start
	var $prpLcompensateFileReadyDate = $(":input[name='prpLcompensateFileReadyDate']");
	if($.trim($prpLcompensateFileReadyDate.val()) == ''){
		return alertMessage($prpLcompensateFileReadyDate[0],"文件收集齊全日必須輸入!");
	}
	//mantis： CLM0091 ，處理人員：BK007 蘇哲，需求單編號：CLM0091.新核心-理算文件齊全日 -end
	//mantis：CLM0283 ，處理人員： DP0713 ，需求單編號：理算任務的理算文件備齊日不可大於該賠案新增當日日期(PA、TA、GA) START
	var riskCodeCheck = $("input[name='prpLcompensateRiskCode']").val();
	if(riskCodeCheck == "PA" || riskCodeCheck=="TA" || riskCodeCheck=="GA"){
		var inputDateStr = $(":input[name='prpLcompensateFileReadyDate']").val();
		var inputDate = new Date(inputDateStr.replace(/-/g, '/')); // 轉換日期格式
		inputDate.setHours(0, 0, 0, 0); // 將目標時間歸零
		var today = new Date();
		today.setHours(0, 0, 0, 0); // 將今天時間歸零

		if (inputDate > today) {
	        alert("理算文件備齊日不可大於該賠案新增當日日期，請重新選擇！");
	        return false;
	   	}
	}
	//mantis：CLM0283 ，處理人員： DP0713 ，需求單編號：理算任務的理算文件備齊日不可大於該賠案新增當日日期(PA、TA、GA) END
 	var $prpLcheckPhoneNumber = $(":input[name='prpLcheckPhoneNumber']");
 	if ($.trim($prpLcheckPhoneNumber.val()) == '') {
 		return alertMessage($prpLcheckPhoneNumber[0], "被保險人電話必須輸入!");
 	}
 	var self = $(":input[name='prpLcompensateIndemnityDutyRate']").val(); //本方肇责
 	var opposite = $(":input[name='prpLcompensateOppositeIndemnityDuty']").val(); //对方肇责
 	var other = $(":input[name='prpLcompensateOtherIndemnityDuty']").val(); //其他肇责
 	if ((parseFloat(self) + parseFloat(opposite) + parseFloat(other)) != 100) {
 		alert("本車、對方車、其他肇責百分比分配不正確!");
 		return false;
 	}
 	//mantis： CLM0088 ，處理人員：BK007 蘇哲，需求單編號：CLM0088.本車肇責100%追償只能選擇否 -start
 	if(parseFloat(self) == 100 && $(":input[name='replevyFlag']").val() != "0" ){
 		alert("本車肇責百分比與追償註記不符合!")
 		return false;
 	}
 	//mantis： CLM0088 ，處理人員：BK007 蘇哲，需求單編號：CLM0088.本車肇責100%追償只能選擇否 -end
 	var $prpLctextContextInnerHTML = $(":input[name='prpLctextContextInnerHTML']");
 	if ($.trim($prpLctextContextInnerHTML.html()) == '') {
 		return alertMessage($prpLctextContextInnerHTML[0], "理算報告必須輸入!");
 	}
 	var $prpLcompensateHandlerCode = $(":input[name='prpLcompensateHandlerCode']");
 	if ($.trim($prpLcompensateHandlerCode.val()) == '') {
 		return alertMessage($prpLcompensateHandlerCode[0], "業務經辦人必須輸入!");
 	}
 	var lossLen = $(":input[name='prpLlossDtoKindCode']").length;
 	var personLen = $(":input[name='prpLpersonLossKindCode']").length;
 	var chargeLen = $(":input[name='prpLchargeKindCode']").length;
 	if(lossLen<=1&&personLen<=1&&chargeLen<=1){
 		alert(i18n.compensate.claimBook); //赔款计算书中的赔付标的，赔款费用至少有一条记录!
 		return false;
 	}
 	return true;
 }

 //肇事责任比例

 function checkIndemnityDuty(field) {
 	var $indemnityDuty = $(":input[name='indemnityDuty']");
 	var $indemnityDutyRate = $(":input[name='prpLcompensateIndemnityDutyRate']"); //本車肇責百分比
 	var $oppositeIndemnityDuty = $(":input[name='prpLcompensateOppositeIndemnityDuty']"); //對方車肇責百分比
 	var $otherIndemnityDuty = $(":input[name='prpLcompensateOtherIndemnityDuty']"); //其他肇責百分比
 	var rate = parseInt($indemnityDutyRate.val());
 	var opposite = parseInt($oppositeIndemnityDuty.val());
 	var other = parseInt($otherIndemnityDuty.val());
 	if (field.name == 'indemnityDuty') {
 		if (field.value == 1) {
 			rate = 100;
 		} else if (field.value == 2) { //主因
 			rate = 70;
 		} else if (field.value == 3) { //同為肇事因素
 			rate = 50;
 		} else if (field.value == 4) { //次因
 			rate = 30;
 		} else if (field.value == 5) { //無責
 			rate = 0;
 		}
 	} else if (field.name == 'prpLcompensateIndemnityDutyRate') {
 		if (rate == 100) {
 			$indemnityDuty.val(1);
 		} else if (rate < 100 && rate > 50) { //主因
 			$indemnityDuty.val(2);
 		} else if (rate == 50) { //同為肇事因素
 			$indemnityDuty.val(3);
 		//<!-- mantis：CLM0071 ，處理人員：BK007 蘇哲，需求單編號：CLM0071.車險理算節點修正 -->
 		} else if (rate > 0 && rate < 50) { //次因
 			$indemnityDuty.val(4);
 		} else if (rate == 0) { //無責
 			$indemnityDuty.val(5);
 		} else {
 			$indemnityDuty.val(6);
 		}
 	}
 	if ((100 - rate) == 0) { //移除主责後可分配的百分比
 		opposite = 0;
 		other = 0;
 	} else {
 		if (field.name == 'prpLcompensateOppositeIndemnityDuty') {
 			if ((100 - rate - opposite) <= 0) {
 				opposite = 100 - rate;
 				other = 0;
 			} else if ((100 - rate - opposite) > 0) {
 				other = 100 - rate - opposite;
 			}
 		} else {
 			if ((100 - rate - other) <= 0) {
 				opposite = 0;
 				other = 100 - rate;
 			} else if ((100 - rate - other) > 0) {
 				opposite = 100 - rate - other;
 			}
 		}
 	}
 	$indemnityDutyRate.val(rate + ".0"); //下拉框中的值是double类型，必须做如此转换才能赋值=。=
 	$oppositeIndemnityDuty.val(opposite + ".0"); //
 	$otherIndemnityDuty.val(other + ".0"); //
 	//mantis： CLM0088 ，處理人員：BK007 蘇哲，需求單編號：CLM0088.本車肇責100%追償只能選擇否 -start
 	if(rate == 100){
		var $replevyFlag = $(":input[name='replevyFlag']"); //追償註記
		$replevyFlag.val("0");
	}
 	//mantis： CLM0088 ，處理人員：BK007 蘇哲，需求單編號：CLM0088.本車肇責100%追償只能選擇否 -end
 }

 //至少有一条车物损\任意險受害人資訊

 function checkUnique() {
 	var prpLlossLength = $("#PrpLloss").find("tr[name='prpLlossObject']").length;
 	var prpLpersonLossLength = $("#PrpLpersonLoss").find("tr[name='prpLpersonLossObject']").length;
 	if (prpLlossLength == 0 && prpLpersonLossLength == 0) {
 		alert("理算至少有賠一筆付車損/物損訊息或受害人訊息!");
 		return false;
 	}
 	return true;
 }

 /**********************提交表单*************************/
 /**
  *@description 根据按钮状态保存报案数据
  *@param       this
  *@param       保存状态
  *@return      通过返回true,否则返回false
  */

 function saveForm(field, saveType) {
	 //mantis： CLM0008 ，處理人員： David ，需求單編號： CLM0008   原因  新增可賠付新商品險種-金額檢核 start
	 var totalMoney = 0;
	 	for (var index = 1; index < fm.prpLpersonLossKindCode.length; index++) {
			var kindcode = fm.prpLpersonLossKindCode[index].value;
			var money = fm.prpLpersonLossSumRealPay[index].value;
			if (kindcode == "33") {
				totalMoney+=parseInt(money,10);
			}
		 }
	 	
	 	for (var index = 1; index < fm.prpLlossDtoKindCode.length; index++) {
			var kindcode = fm.prpLlossDtoKindCode[index].value;
			var money = fm.prpLlossDtoSumRealPay[index].value;
			if (kindcode == "33") {
				totalMoney+=parseInt(money,10);
			}
		 }
	 	if(totalMoney > 20000000){
	 		alert("任意汽車第三人責任保險-單一保額型,兩類賠付金額加總不可超過總保額20000000！\n");
			return false;
	 	}
	 	//mantis： CLM0008 ，處理人員： David ，需求單編號： CLM0008 end
		//mantis：CLM0175，處理人員：DP0713，需求單編號：新核心-車險計算書新增理賠已出險次數 START
	 	if(!checkLossObjectListsMatchAndHavePay()){
			return false;
		}
		if(!checkDateBetweenHaventDuplicateCase()){
			return false;
		}
		if(!mustBeforeDamageDate()){
			return false;
		}
		//mantis：CLM0175，處理人員：DP0713，需求單編號：新核心-車險計算書新增理賠已出險次數 END

		// mantis：CLM0216，處理人員：DP0714，新核心-新增車險醫詢費用提示檢核 -- start
		var licenseNo = fm.prpLcompensateLicenseNo.value; //牌照號碼
		var damageDate = fm.DamageStartDate.value; //出險日期
		var damageHour = fm.DamageStartHour.value; //出險小時
		var compensateNo = fm.prpLcompensateCompensateNo.value;//計算書號

		// mantis：CLM0229，處理人員：DP0714，新核心-醫詢費用強制任意累積費用調整(整段覆蓋) -- start
        $.ajax({
        	type : 'POST',
        	url : contextRootPath + "/compensate/checkLicenceNoAndDamageStartDate.do?licenseNo=" + licenseNo
        	+ "&damageDate=" + damageDate + "&damageHour=" + damageHour + "&compensateNo=" + compensateNo,
        	async : false,
        	cache : false,
        	dataType: "json",
        	contentType: "application/json; charset=utf-8",
        	success : function(data) {
        		var sumChargeAmount = parseInt(data.sumChargeAmount);
    			for (var i=0; i<fm.prpLchargeSerialNo.length; i++) {
                    if (typeof(fm.prpLchargeChargeCode[i]) !== 'undefined' && fm.prpLchargeChargeCode[i] !== null) {
    					var chargeCode = fm.prpLchargeChargeCode[i].value; //費用名稱
    					if ('Y' == chargeCode) { //代墊費用
    						// 當賠案牌照及出險日期皆一致，且輸入金額大於2000元，則跳提示訊息
    						var chargeReport = fm.prpLchargeChargeReport[i].value; // 費用金額
    						sumChargeAmount += parseInt(chargeReport);
    						
    					}
    				}
    			}
    			if (sumChargeAmount > 2000) {
    				if (!confirm("請確認是否需攤付代墊之醫詢費用")) {
    					return false;
    				}
				}
        	},
        	error: function (jqXHR, textStatus, errorThrown) { 
        		alert("checkLicenceNoAndDamageStartDate ajax Error:" + errorThrown); 
        	}
        });
        // mantis：CLM0229，處理人員：DP0714，新核心-醫詢費用強制任意累積費用調整(整段覆蓋) -- end
        // mantis：CLM0216，處理人員：DP0714，新核心-新增車險醫詢費用提示檢核 -- end

	if (saveType == "4") {
		//mantis：CLM0193 ，處理人員：DP0713，需求單編號：新核心-代步車日期計算及輸入檢核 START
		if(stopForQuamtity0cOverDayAcount){
			alert("代步車使用天數已超過承保額度，請重新確認後提交結案資料。");
			return false;
		}
		//mantis：CLM0193 ，處理人員：DP0713，需求單編號：新核心-代步車日期計算及輸入檢核 END
		//mantis：CLM0213，處理人員：DP0713，需求單編號：新核心-車體險維修時間重疊檢核新增險種
		calculateFinishAndDayCount();
		//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 START
		if(!calRealpayNewSubmitCheck()){
			return false;
		}
		//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 END
		//mantis： CLM0166，處理人員：DP0713，需求單編號：車體新商品上線險別0Y START
		if(!checkLimitDeductible()){
			return false;
		}
		//mantis： CLM0166，處理人員：DP0713，需求單編號：車體新商品上線險別0Y END
		//mantis：CLM0146，處理人員：DP0713，需求單編號：新核心-賠款給付對象於零結時檢核不可大於一人 START
		var feeCount=0;
		//實際費用  费用合计
		var sumFee = 0.00; //费用之和，
		$("#PrpLcharge").find(":input[name='prpLchargeChargeAmount']").each(function () {
			sumFee += (isNaN($(this).val()) ? 0 : parseFloat($(this).val()));
			feeCount++;
		});
		if(sumFee==0 && feeCount>0){
			alert("請清空費用資訊支付對象訊息。");
			return false;
		}
		var payCount=0;
		//理赔金額
		var prpLpayObjectInfoPayAmountList = document.getElementsByName("prpLpayObjectInfoPayAmount"); 
		var sumPay = 0;//理赔金額總和
		for (var i = 1; i < prpLpayObjectInfoPayAmountList.length; i++) {
			var tempPayShip = prpLpayObjectInfoPayAmountList[i].value;
			sumPay+=isNaN(tempPayShip)?0:tempPayShip;
			payCount++;
		}
		if(sumPay==0 && payCount > 0){
			alert("請清空賠款給付對象訊息。");
			return false; 
		}
		//mantis：CLM0146，處理人員：DP0713，需求單編號：新核心-賠款給付對象於零結時檢核不可大於一人 END
		//mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核  START
		var prpLpayObjectInfoAreaCodeList = document.getElementsByName("prpLpayObjectInfoAreaCode"); //賠付對象
		for (var i = 1; i < prpLpayObjectInfoAreaCodeList.length; i++) {
			if(undefined!=prpLpayObjectInfoAreaCodeList[i] && null!=prpLpayObjectInfoAreaCodeList[i]){
				var oAreaCode = trim(prpLpayObjectInfoAreaCodeList[i].value);
				var areaCode = trim(prpLpayObjectInfoAreaCodeList[i].value).replace(/[^\d]/g,'');
				if(oAreaCode.length > 3){
					alert("賠付對象" + (i) + " ‘郵遞區號’ 長度超過3位數!");
					return false; //
				} else 
				if(oAreaCode != areaCode){
					alert("賠付對象 " + (i) + " ‘郵遞區號’ 只能輸入數值!");
					return false; //
				} 
			}
		}
		var prpLchargeAreaCodeList = document.getElementsByName("prpLchargeAreaCode"); //郵遞區號
		for (var i = 1; i < prpLchargeAreaCodeList.length; i++) {
			if(undefined!=prpLchargeAreaCodeList[i] && null!=prpLchargeAreaCodeList[i]){
				var oAreaCode = trim(prpLchargeAreaCodeList[i].value);
				var areaCode = trim(prpLchargeAreaCodeList[i].value).replace(/[^\d]/g,'');
				if(oAreaCode.length > 3){
					alert("第 " + (i) + " 条費用資訊‘郵遞區號’ 長度超過3位數!");
					return false; //
				} else 
				if(oAreaCode != areaCode){
					alert("第 " + (i) + " 条費用資訊‘郵遞區號’ 只能輸入數值!");
					return false; //
				}
			}
		}
		//mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核  END
		/**
		 * 車險自付額發票校驗
		 */
		if(!deductibleInvoiceCheck()){
			//mantis：CLM0074 ，處理人員：BK007 蘇哲，需求單編號：CLM0074.理賠系統-車體險訊息[自負額發票]
			//不alert，因為deductibleInvoiceCheck已經會alert了
			return false;
		}
 		var prpLcompensateSumClaim = document.getElementsByName("prpLcompensateSumClaim"); //预估金额
 		for (var i = 0; i < prpLcompensateSumClaim.length; i++) {
 			if (prpLcompensateSumClaim[i].value == "") {
 				alert("預估金額不允許爲空！\n");
 				return false;
 			}
 		}
 		for (var i = 1; i < fm.prpLchargeUniformNo.length; i++) {
 			var prpLchargeCertificateCode = fm.prpLchargeCertificateCode[i].value; //證件類型
 			var prpLchargeUniformNo = fm.prpLchargeUniformNo[i].value; //證件代碼
 			if (prpLchargeCertificateCode == "01" && !checkIdentifyNumber(prpLchargeUniformNo, "9")) {
 				alert("請爲費用資訊第  " + i + " 條錄入正確的身份證號");
 				return false;
 			}
 			if (prpLchargeCertificateCode == "02" && !checkUniformNo(prpLchargeUniformNo)) {
 				alert("請爲費用資訊第  " + i + " 條錄入正確的統一編號");
 				return false;
 			}
 		}

 		for (var i = 1; i < fm.prpLpayObjectInfoUniformNo.length; i++) {
 			var prpLpayObjectInfoCertificateCode = fm.prpLpayObjectInfoCertificateCode[i].value; //證件類型
 			var prpLpayObjectInfoUniformNo = fm.prpLpayObjectInfoUniformNo[i].value; //證件代碼
 			if (prpLpayObjectInfoCertificateCode == "01" && !checkIdentifyNumber(prpLpayObjectInfoUniformNo, "9")) {
 				alert("請爲賠款給付對象訊息  賠付對象 " + i + " 錄入正確的身份證號");
 				return false;
 			}
 			if (prpLpayObjectInfoCertificateCode == "02" && !checkUniformNo(prpLpayObjectInfoUniformNo)) {
 				alert("請爲賠款給付對象訊息  賠付對象 " + i + " 錄入正確的統一編號");
 				return false;
 			}
 		}
		//mantis： CLM0105，處理人員：BL061 張明財，需求單編號：CLM0105 新核心-手機正規化 start
		var errorMessage = "";
 		var prpLcheckPhoneNumber =fm.prpLcheckPhoneNumber.value;
 			if (prpLcheckPhoneNumber.length > 0) {
 				if (prpLcheckPhoneNumber.substr(0, 2)=='09'){
			    	reg =/^09[0-9]{8}$/;
			    	if(!reg.test(prpLcheckPhoneNumber)){
			    		errorMessage =errorMessage +"被保險人電話有誤\n";
			    	}
			    } else {
			      reg =/^[0-9]{2,3}[0-9]{7,8}$/;
			      if (!reg.test(prpLcheckPhoneNumber)){
			    	errorMessage =errorMessage +"被保險人電話有誤\n";
			      }
			  }
 			}
 
 	    for (var i = 1; i < fm.prpLpayObjectInfoBeneficiaryPhone.length; i++) {	
 	    	var prpLpayObjectInfoBeneficiaryPhone =fm.prpLpayObjectInfoBeneficiaryPhone[i].value;
 	    	if (prpLpayObjectInfoBeneficiaryPhone.length > 0) {
 	    		if (prpLpayObjectInfoBeneficiaryPhone.substr(0, 2)=='09'){
			    	reg =/^09[0-9]{8}$/;
			    	  if(!reg.test(prpLpayObjectInfoBeneficiaryPhone)){
			    		errorMessage =errorMessage + " 賠付對象"+ i +"受款人電話有誤\n";
			    	}
 	    		} else {
 	    			reg =/^[0-9]{2,3}[0-9]{7,8}$/;
 	    			if (!reg.test(prpLpayObjectInfoBeneficiaryPhone)){
 	    				errorMessage =errorMessage + " 賠付對象"+ i +"受款人電話有誤\n";
 	    			}
 	    		}
 	    	}
 		}
 	   for (var i = 1; i < fm.prpLpersonLossTelephoneNo.length; i++) {	
		var prpLpersonLossTelephoneNo =fm.prpLpersonLossTelephoneNo[i].value;
		if (prpLpersonLossTelephoneNo.length > 0) {
			 if (prpLpersonLossTelephoneNo.substr(0, 2)=='09'){
			    	reg =/^09[0-9]{8}$/;
			    	  if(!reg.test(prpLpersonLossTelephoneNo)){
			    		errorMessage =errorMessage+"第" + i +"受害人電話有誤\n";
			    	}
			  } else {
			      reg =/^[0-9]{2,3}[0-9]{7,8}$/;
			      if (!reg.test(prpLpersonLossTelephoneNo)){
			    	errorMessage =errorMessage +"第" + i +"受害人電話有誤\n";
			      }
			}
		   	
		  }
 	   }
 	   for (var i = 1; i < fm.prpLpersonLossMobilePhone.length; i++) {	
 		   var prpLpersonLossMobilePhone =fm.prpLpersonLossMobilePhone[i].value;
 		   if (prpLpersonLossMobilePhone.length > 0) {
 			   	var reg =/^09[0-9]{8}$/;
		    	  if(!reg.test(prpLpersonLossMobilePhone)){
		    		errorMessage =errorMessage+"第" + i +"受害人手機電話有誤\n ";
		    	} 	
 		   }
 	   	}
		if (errorMessage.length > 0) {
			alert(errorMessage);
			return false;
		} //mantis： CLM0105，處理人員：BL061 張明財，需求單編號：CLM0105 新核心-手機正規化 end 
		//mantis：CLM0155，處理人員：DP0713，車體險自負額有責任時卡控自負額發票號碼必輸 START
//		if(!checkPrpLloss2()){
//			return false;
//		}
		//mantis：CLM0155，處理人員：DP0713，車體險自負額有責任時卡控自負額發票號碼必輸 END
 		if (checkMainPage() && checkSumPayAmount() && checkPrpLloss() && checkPrpLpersonLoss() && checkPrpLcharge() && checkKind() && checkPrpLpayObjectInfo() && checkAllLimitItemKind()&&checkCertainLoss() && checkCarInsurance() ) {
 			if(!checkKindPay()){
 				return false;
 			}
 			fm.buttonSaveType.value = saveType;
 			//解禁残废等级的select disable，保证其能与後台交互
 			$(":input[name='prpLdisabilityLimitRatingCode']").each(function () {
 				this.disabled = false;
 			});
 			$("input[name='buttonSave']").attr("disabled",true);
 			$("input[name='buttonSaveFinishSubmit']").attr("disabled",true);
 			$("input[name='buttonCancel']").attr("disabled",true);
 			$("input[name='buttonGiveup']").attr("disabled",true);
 			$("input[name='buttonBack']").attr("disabled",true);
 			//mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核 START
// 			fm.submit();
// 			return true;
 			
 			var riskCode = $("input[name='prpLcompensateRiskCode']").val();
 			var claimNo = $(":input[name='prpLcompensateClaimNo']").val();
 			var prpLpayObjectInfoUniformNoAry=[];
 			for (var i = 1; i < fm.prpLpayObjectInfoUniformNo.length; i++) {
 	 			//var prpLpayObjectInfoCertificateCode = fm.prpLpayObjectInfoCertificateCode[i].value; //證件類型
 	 			var prpLpayObjectInfoUniformNo = fm.prpLpayObjectInfoUniformNo[i].value; //證件代碼
 	 			prpLpayObjectInfoUniformNoAry.push(prpLpayObjectInfoUniformNo);
 			}
 			debugger;
 			var checkSubmit = false;
 			$.ajax({
 				type : 'POST',
 				url : contextRootPath + "/compensate/checkPayuserList.do?" 
 						+"prpLcompensateRiskCode=" +riskCode+"&" +
 						"buttonSaveType="+saveType+"&" +
 						"prpLcompensateClaimNo="+claimNo+"&" +
 						"prpLpayObjectInfoUniformNo="+prpLpayObjectInfoUniformNoAry,
 				async : false,
 				cache : false,
 				dataType: "json",
 				contentType: "application/json; charset=utf-8",
 				success : function(data) {
 					if(data.message != ''){
 						alert(data.message);
 					}else{
 						checkSubmit = true;
 					}
 				},
 				error: function (jqXHR, textStatus, errorThrown) { 
 					alert("saveForm ajax Error:"+errorThrown); 
 				}
 			});
 			if(checkSubmit){
				fm.submit();
				return true;
 			}else{
	 			$(":input[name='prpLdisabilityLimitRatingCode']").each(function () {
	 				this.disabled = true;
	 			});
	 			$("input[name='buttonSave']").attr("disabled",false);
	 			$("input[name='buttonSaveFinishSubmit']").attr("disabled",false);
	 			$("input[name='buttonCancel']").attr("disabled",false);
	 			$("input[name='buttonGiveup']").attr("disabled",false);
	 			$("input[name='buttonBack']").attr("disabled",false);
				return false;
 			}
 			//mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核 END
 		}
 	} else {
 		var prpLcompensateHandlerCode = document.getElementsByName("prpLcompensateHandlerCode"); //费用名称
 		if (prpLcompensateHandlerCode.length > 0 && prpLcompensateHandlerCode[0].value == "") {
 			alert("業務經辦人必須輸入!");
 			return false; //
 		}
 		var prpLchargeChargeCodeList = document.getElementsByName("prpLchargeChargeCode"); //费用名称
 		for (var i = 1; i < prpLchargeChargeCodeList.length; i++) {
 			if (trim(prpLchargeChargeCodeList[i].value) == '') {
 				alert("第 " + (i) + " 条費用資訊‘費用名稱’必須輸入!");
 				return false; //
 			}
 		}
 		fm.buttonSaveType.value = saveType;
 		//解禁残废等级的select disable，保证其能与後台交互
 		$(":input[name='prpLdisabilityLimitRatingCode']").each(function () {
 			this.disabled = false;
 		});
 		$("input[name='buttonSave']").attr("disabled",true);
		$("input[name='buttonSaveFinishSubmit']").attr("disabled",true);
		$("input[name='buttonCancel']").attr("disabled",true);
		$("input[name='buttonGiveup']").attr("disabled",true);
		$("input[name='buttonBack']").attr("disabled",true);
 		fm.submit();
 		return true;
 	}


 }

 function uLprpLPayObjectinfo() {
 	var uiLi_first = '<li><input type="checkbox" onclick="setPayObjectPayAmount();" name="payObjectSerialNo" value="';
		var uiLi_list = ' 	賠付金額: <input  type="text" name="payObjectPayAmount" onblur="setPayObjectPayAmount();" value="" class="common" style="width:100px"/></li>';
 	var uiLi = "";
 	$.each($.find("input[name='prpLpayObjectInfoSerialNo']"), function (i, n) {
 		if (i > 0) {
				uiLi += uiLi_first+i+'" />賠付對象'+i+uiLi_list;
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
 	odiv.style.left = findPosX(field) - 303;
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
 	var prpLlossPayObjectSerialNo = document.getElementsByName("prpLlossPayObjectSerialNo");
 	for (var i = 0; i < prpLlossPayObjectSerialNo.length; i++) {
 		if (prpLlossPayObjectSerialNo[i].value != "") {
 			var payObjectValue = prpLlossPayObjectSerialNo[i].value.split(";");
 			for (var j = 0; j < payObjectValue.length; j++) {
 				var payObjectTemp = payObjectValue[j].split(":");
 				prpLpayObjectInfoPayAmount[parseInt(payObjectTemp[0])].value = parseFloat(payObjectTemp[1]) + parseFloat(prpLpayObjectInfoPayAmount[parseInt(payObjectTemp[0])].value);
 				prpLpayObjectInfoPayAmount[parseInt(payObjectTemp[0])].readOnly=true;
 				prpLpayObjectInfoPayAmount[parseInt(payObjectTemp[0])].className="readonly";
 			}
 		}

 	}
 	for (var i = 0; i < prpLpersonLossPayObjectSerialNo.length; i++) {
 		if (prpLpersonLossPayObjectSerialNo[i].value != "") {
 			var payObjectValue = prpLpersonLossPayObjectSerialNo[i].value.split(";");
 			for (var j = 0; j < payObjectValue.length; j++) {
 				var payObjectTemp = payObjectValue[j].split(":");
 				prpLpayObjectInfoPayAmount[parseInt(payObjectTemp[0])].value = parseFloat(payObjectTemp[1]) + parseFloat(prpLpayObjectInfoPayAmount[parseInt(payObjectTemp[0])].value);
 			}
 		}

 	}
 }
 
 /***
  * 核心車險理賠系統需求變更#83為避免廠商提供重覆開立自負額發票，建立系統檢核機制
  * 若為”分損”,存在車體險賠付，且”自負額”大於0，則必須輸入所有欄位並建立系統檢核機制
  */
 function checkCarInsurance(){
// mantis：CLM0074 ，處理人員：BK007 蘇哲，需求單編號：CLM0074.理賠系統-車體險訊息[自負額發票] -start
//	var $prpLcarInsuranceCompensateNo = $("#CarInsurance").find(":input[name='prpLcarInsuranceCompensateNo']");
//	if($prpLcarInsuranceCompensateNo.length > 0 ){
//		var compensateNo = $(":input[name='prpLcompensateCompensateNo']").val();
//		var carInsuranceCompensateNo = $prpLcarInsuranceCompensateNo.first().val();
//		if( carInsuranceCompensateNo !="" && compensateNo != carInsuranceCompensateNo ){//
//			return true;//如果一結已有錄入車體險訊息，則二結無需再錄入。
//		}
//	}
// mantis：CLM0074 ，處理人員：BK007 蘇哲，需求單編號：CLM0074.理賠系統-車體險訊息[自負額發票] -end
	 var lossType = $(":input[name='prpLcompensateLossType']").val();
	 var checkFlag = true;
	 var re = /^[A-Za-z]{2}\d{8}$/;//檢核自負額發票號邏輯(前2碼為英文，後8碼為數字，共10碼)
	 var hasCarPay = false;//有車體險賠付
	 var hasCarDedu = false;//有車體險賠付，且自負額大於0
	 $("#spanlLoss").find("tr[name='prpLlossObject']").each(function(){
		 var kindCode = $(this).find(":input[name='prpLlossDtoKindCode']").val();
		 var deductible = $(this).find(":input[name='prpLlossDtoDeductible']").val();
		 var sumRealPay = $(this).find(":input[name='prpLlossDtoSumRealPay']").val();
		 if($.inArray(kindCode,CarKindCodeArray) > -1 && parseFloat(sumRealPay) > 0 ){
			 hasCarPay = true;
			 if(parseFloat(deductible) > 0 ){
				 hasCarDedu = true;
				 return false;
			 }
		 }
	 });
	 if(hasCarPay){//存在車體險賠付
		 var $carInsurance = $("#spanCarInsurance").find("tr[name='CarInsuranceObject']");
		 if($carInsurance.length == 0 ){
			 alert("存在車體險賠付，請錄入車體險訊息！");
			 return false;
		 }
		 $("#spanCarInsurance").find("tr[name='CarInsuranceObject']").each(function(i,e){
			 var deductibleInvoice = $(e).find(":input[name='prpLcarInsuranceDeductibleInvoice']").val();
			 var uniformNo = $(e).find(":input[name='prpLcarInsuranceUniformNo']").val();
			 var repairUniformNo = $(e).find(":input[name='prpLcarInsuranceRepairUniformNo']").val();
			 var handlerCode = $(e).find(":input[name='prpLcarInsuranceHandlerCode']").val();
			 if($.trim(deductibleInvoice).length == 0 ){
				 if(lossType == "2" && hasCarDedu ){//分損且自負額大於 0 ，必輸
					 alert("損失代號為分損且車體險賠付自負額大於0，車體險訊息必須錄入自負額發票號！");
					 checkFlag = false;
					 return false;
				 }
			 }else {
				 var certificateType = $(e).find(":input[name='prpLcarInsuranceCertificateType']").val();//取憑證類型
				 if(certificateType == "1"){//憑證為發票
					 if(!re.test(deductibleInvoice)){
						 alert("車體險訊息自負額發票號校驗不通過！（規則：前2碼為英文，後8碼為數字，共10碼）");
						 checkFlag = false;
						 return false;
					 }
				 }
			 }
			 /*#083 第三次修改 需求变更 刪除開立者統編*/
/*				 if($.trim(uniformNo).length == 0){
					 alert("請爲車體險訊息第  " + (i+1) + " 條錄入開立者統編！");
					 checkFlag = false;
					 return false;
				 } else if(!checkUniformNo(uniformNo)){
					 alert("請爲車體險訊息第  " + (i+1) + " 條錄入正確的開立者統編！");
					 checkFlag = false;
					 return false;
				 }*/
			 if($.trim(repairUniformNo).length == 0){
				 if(lossType == "2"){
					 alert("損失代號為分損，車體險訊息必須錄入修理廠統一編號/負責人身份證字號！");
					 checkFlag = false;
					 return false;
				 }
			 } else if(!(checkIdentifyNumber(repairUniformNo,"9")||checkUniformNo(repairUniformNo) )){
				 alert("請爲車體險訊息錄入正確的修理廠統一編號/負責人身份證字號！");
				 checkFlag = false;
				 return false;
			 }
			 if($.trim(handlerCode).length == 0){//必輸項
				 alert("請爲車體險訊息錄入理賠經辦人員！");
				 checkFlag = false;
				 return false;
			 }
		 });
	 } else {
		 if(checkFlag){
			 $("#spanCarInsurance").find("tr[name='CarInsuranceObject']").remove();
		 }
	 }
	 return checkFlag;
 }
 
 /***
  * 校驗險別賠付是否超過預估
  */
 function checkKindPay(){
	var propKindArray = new Array();//車物損賠付險別
	var propKindPayArray = new Array();//車物損險別賠付金額
	var personKindArray = new Array();//人傷賠付險別
	var personKindPayArray = new Array();//人傷險別賠付金額
	var tempIndex;
	var kindCode;
	var sumRealPay;
	$("#PrpLloss").find("tr[name='prpLlossObject']").each(function(){
		kindCode = $(this).find(":input[name='prpLlossDtoKindCode']").val();
		sumRealPay = $(this).find(":input[name='prpLlossDtoSumRealPay']").val();
//		tempIndex = jQuery.inArray(kindCode,propKindArray);
//		if(tempIndex == -1){
			propKindArray.push(kindCode);
			propKindPayArray.push(sumRealPay);
//		}else {
//			propKindPayArray[tempIndex] = propKindPayArray[tempIndex] + sumRealPay;
//		}
	});
	$("#PrpLpersonLoss").find("tr[name='prpLpersonLossObject']").each(function(i,obj){
		$(obj).find("tr[name='prpLpersonFeeLossObject']").each(function(){
			kindCode = $(this).find(":input[name='prpLpersonLossKindCode']").val();
			sumRealPay = $(this).find(":input[name='prpLpersonLossSumRealPay']").val();
//			tempIndex = jQuery.inArray(kindCode,propKindArray);
//			if(tempIndex == -1){
				personKindArray.push(kindCode);
				personKindPayArray.push(sumRealPay);
//			}else {
//				personKindPayArray[tempIndex] = personKindPayArray[tempIndex] + sumRealPay;
//			}
		});
	});
	var claimNo = $(":input[name='prpLcompensateClaimNo']").val(); //取赔案号
	var checkresult = false;
	$.ajax({
		url : contextRootPath + "/compensate/checkKindPay.do",
		type: "POST",
		dataType : "json",
		async : false,
		data : {
			claimNo : claimNo,
			propKind : propKindArray.join(","),
			propKindPay : propKindPayArray.join(","),
			personKind : personKindArray.join(","),
			personKindPay : personKindPayArray.join(",")
		},
		success : function(data){
			if(data && data.msg){
				if(confirm(data.msg + " 請確認是否繼續 ？ ")){
					checkresult = true;
				}
			} else {
				checkresult = true;
			}
		},
		error : function(){
			alert("校驗險別賠付是否超過預估出現異常！");
		}
	});
	return checkresult;
 }

function setAccidentType(){
	var editType = $(":input[name='editType']").val();
	if(editType != "ADD" && editType != "EDIT"){
		return;
	}
	var carKindCodes = new Array();//車體險賠付險種
	var propKindCodes = new Array();//責任險賠付險種
	var carkindPay = 0;//車體險賠付金額
	var propKindPay = 0;//責任險賠付金額
	var carReservedEstimateY = Array();// 車體險保留預估的
	var carReservedEstimateN = Array();// 車體險未保留預估的
	var propReservedEstimateY = Array();// 責任險保留預估的
	var propReservedEstimateN = Array();// 責任險未保留預估的
	var indemnityDutyRate = parseFloat($("#prpLcompensateIndemnityDutyRate").val());
	var $prpLlossKindCode = $("#spanlLoss").find(":input[name='prpLlossDtoKindCode']");
	var $prpLlossSumRealPay = $("#spanlLoss").find(":input[name='prpLlossDtoSumRealPay']");
	var $prpLlossReservedEstimate = $("#spanlLoss").find(":input[name='prpLlossReservedEstimate']");
	$prpLlossKindCode.each(function(i,e){
		var kindCode = $.trim(e.value);
		var sumRealPay = $prpLlossSumRealPay.get(i).value;
		var estimate = $prpLlossReservedEstimate.get(i).value;
		if(kindCode.length != 0){
			if($.inArray(kindCode,CarKindCodeArray) > -1){
				carKindCodes.push(kindCode);
				carkindPay += isNaN(sumRealPay) ? 0 : parseFloat(sumRealPay);
				if( "Y" == estimate){//
					carReservedEstimateY.push(kindCode);
				} else {
					carReservedEstimateN.push(kindCode);
				}
			}else{
				propKindCodes.push(kindCode);
				propKindPay += isNaN(sumRealPay) ? 0 : parseFloat(sumRealPay);
				if( "Y" == estimate){//
					propReservedEstimateY.push(kindCode);
				} else {
					propReservedEstimateN.push(kindCode);
				}
			}
		}
	});
	var $prpLpersonLossKindCode = $("#PrpLpersonLoss").find(":input[name='prpLpersonLossKindCode']");
	var $prpLpersonLossSumRealPay = $("#PrpLpersonLoss").find(":input[name='prpLpersonLossSumRealPay']");
	var $prpLpersonLossReservedEstimate = $("#PrpLpersonLoss").find(":input[name='prpLpersonLossReservedEstimate']");
	$prpLpersonLossKindCode.each(function(i,e){
		var kindCode = $.trim(e.value);
		var sumRealPay = $prpLpersonLossSumRealPay.get(i).value;
		var estimate = $prpLpersonLossReservedEstimate.get(i).value;
		if(kindCode.length != 0){
			propKindCodes.push(kindCode);
			propKindPay += isNaN(sumRealPay) ? 0 : parseFloat(sumRealPay);
			if( "Y" == estimate){//
				propReservedEstimateY.push(kindCode);
			} else {
				propReservedEstimateN.push(kindCode);
			}
		}
	});
	var claimNo = $(":input[name='prpLcompensateClaimNo']").val();
	var obj ;
	$.ajax({
		url : contextRootPath + "/compensate/getResponsAccidentType.do",
		type: "POST",
		dataType : "json",
		async : false,
		cache : true,
		data : {
			claimNo : claimNo
		},
		success : function(data){
			if(data && data.msg){
				alert(msg);
			} else {
				obj = data;
			}
		},
		error : function(){
			alert("獲取本案肇責情況出現異常！");
		}
	});
	if(obj && !obj.msg){
		if(carKindCodes.length == 0){
			$("#divCarAccidentType").hide();
			$(":input[name='prpLcompensateAccidentType']").val("");
		}else{
			$("#divCarAccidentType").show();
//			var prpLclaimCarAccidentType = $(":input[name='prpLclaimCarAccidentType']").val();
//			getAccidentType("prpLcompensateAccidentType",carKindCodes,carkindPay,indemnityDutyRate,prpLclaimCarAccidentType,carReservedEstimateY,carReservedEstimateN);
			setAccidentTypeNew("prpLcompensateAccidentType",carkindPay,indemnityDutyRate,carReservedEstimateY,carReservedEstimateN,obj.claimCarAccidentType,obj.carFlag,obj.carPay,obj.compeCarAccidentType);
		}
		if(propKindCodes.length == 0){
			$("#divPropAccidentType").hide();
			$(":input[name='prpLcompensatePropAccidentType']").val("");
		}else{
			$("#divPropAccidentType").show();
//			var prpLclaimPropAccidentType = $(":input[name='prpLclaimPropAccidentType']").val();
//			getAccidentType("prpLcompensatePropAccidentType",propKindCodes,propKindPay,indemnityDutyRate,prpLclaimPropAccidentType,propReservedEstimateY,propReservedEstimateN);
			setAccidentTypeNew("prpLcompensatePropAccidentType",propKindPay,indemnityDutyRate,propReservedEstimateY,propReservedEstimateN,obj.claimPropAccidentType,obj.propFlag,obj.propPay,obj.compePropAccidentType);
		}
	}
}

/***
 * @param inputName 錄入域名稱（select.id ,input.name）
 * @param kindPay 賠付險種（車體險或責任險）之賠償金額
 * @param indemnityDutyRate 本車肇事百分比
 * @param reservedEstimateY 保留預估的險種
 * @param reservedEstimateN 未保留預估的險種
 * @param claimAccidentType 車體險或責任險 立案所輸之肇責
 * @param flag 前次是否已存在 車體險或責任險 賠付情況
 * @param pay 車體險或責任險 已累計賠付金額
 * @param compeAccidentType 車體險或責任險 一結之肇責類型
 */
function setAccidentTypeNew(inputName , kindPay , indemnityDutyRate , reservedEstimateY , reservedEstimateN , claimAccidentType , flag , pay , compeAccidentType ){
	var tempAccidentType = "";
	// 肇責情況
	if(indemnityDutyRate == 0){//Y 無肇責
		tempAccidentType = "2";
	} else { //N
		//已累計賠償金額情況  
		if(kindPay > 0 || ( flag && pay > 0 )){ // Y
			tempAccidentType = "1";
		} else {// N 
			// 保留預估情況
			if(reservedEstimateY.length == 0 && reservedEstimateN.length > 0){// Y  都未保留預估
				tempAccidentType = "3";
			} else { // N 存在保留預估
				// 是否存 車體險或責任險 在賠付情況
				if(flag){ // Y 存在 取一結
					tempAccidentType = compeAccidentType;
				} else {//N 不存在 取立案
					tempAccidentType = claimAccidentType;
				}
			}
		}
	}
	$(":input[name='" + inputName + "']").val(tempAccidentType);
	$("#" + inputName).val(tempAccidentType);
}
/***
 * @param inputName 錄入域名稱（select.id ,input.name）
 * @param kindCodes 賠付險種
 * @param kindPay 賠付險種之賠償金額
 * @param indemnityDutyRate 本車肇事百分比
 * @param prpLclaimAccidentType 立案時所輸入之肇責類型
 * @param reservedEstimateY 保留預估的險種
 * @param reservedEstimateN 未保留預估的險種
 */
function getAccidentType(inputName , kindCodes , kindPay , indemnityDutyRate , prpLclaimAccidentType , reservedEstimateY , reservedEstimateN ){
	var $divPreAccidentType = $("#divPreAccidentType");
	var tempAccidentType = "";
	if($divPreAccidentType.length == 0 ){
		tempAccidentType = getAT(kindPay != 0, indemnityDutyRate, prpLclaimAccidentType, reservedEstimateY, reservedEstimateN);
	} else {//險別已有賠付，屬於二結
		var accidentType = "";
		var accidentTypeTimes = 0;
		//二結取一結之肇責類型
		$.each(kindCodes,function(i,kindCode){//檢閱賠付險別中最初結案的肇責類型，取最早一結的險種之肇責類型
			var $at = $divPreAccidentType.find(":input[name='kindAT_"+kindCode+"']");
			if($at.length > 0){//有賠付
				var times = parseInt($divPreAccidentType.find(":input[name='kindAT_times_"+kindCode+"']").val());
				if(accidentTypeTimes == 0 || accidentTypeTimes > times){
					accidentTypeTimes = times;
					accidentType = $at.val();
				}
			}
		})
		if(accidentType == "1" || accidentType == "2"){
			tempAccidentType = accidentType;
		} else {//賠付險種屬初次理算，或者 一結 之肇責為 3 
			tempAccidentType = getAT(kindPay != 0, indemnityDutyRate, prpLclaimAccidentType, reservedEstimateY, reservedEstimateN);
		}
	}
	$(":input[name='" + inputName + "']").val(tempAccidentType);
	$("#" + inputName).val(tempAccidentType);
	return tempAccidentType;
}

/***
 * 肇責類型規則
 * i.	賠付險種中有“賠償金額”且“本車肇事百分比”非為0者，肇責類型為"1"有肇責，計次。
 * ii.	賠付險種中有“賠償金額”且“本車肇事百分比”為0者，肇責類型為"2"無肇責，不計次。
 * iii.	賠付險種中無“賠償金額”且都“未保留預估金額”者，肇責類型為"3"有肇責，不計次。
 * 
 * v.	任意險若賠付險種中同時”有賠償金額”及保留或未保留預估者，以有賠償金額者之邏輯判斷(肇責類型為1或2)。
 * vi.	任意險賠付險種中無“賠償金額”且同時有保留預估及未保留預估者，帶立案時所輸入之肇責類型。
 * @param hasKindPay 是否有賠償金額
 * @param indemnityDutyRate 本車肇事百分比
 * @param claimAccidentType 立案時所輸入之肇責類型
 * @param Y 車體險或責任險保留預估的險種
 * @param N 車體險或責任險未保留預估的險種
 */
function getAT(hasKindPay , indemnityDutyRate , claimAccidentType , Y , N){
//	if(hasKindPay){//有”賠償金額”
//		return indemnityDutyRate > 0 ? "1" : "2";
//	} else {// 無賠償金額
//		return Y.length == 0 && N.length > 0 ? "3" : claimAccidentType ;
//	}
	//20160412規則新調整
/*
	1.	本案保車肇事責任百分比=0時
		i.	無論險種是否有賠付金額，肇責類型皆為"2"無肇責，不計次。
	2.	本案保車肇事責任百分比>0時
		i.	賠付險種中有"賠償金額"，肇責類型為"1"有肇責，計次。
		ii.	賠付險種中"無賠償金額"且都未保留預估金額者，肇責類型為"3"有肇責，不計次。
		iv.	任意險賠付險種中無"賠償金額"且 有保留預估者，帶立案時所輸入之肇責類型。
	3.	同一險種(車體險或非車體險)二次賠付時，以一結之肇責類型直接代入。若一結之肇責類型為1或2，二結必同一結肇責類型。
	4.	若一結之肇責類型為3，二結依i或ii或iii之邏輯判斷。
*/
	if(indemnityDutyRate == 0){//無肇責
		return "2";
	}
	//有肇責
	if(hasKindPay){//有肇責 有"賠償金額"
		return "1";
	}
	//有肇責 都未保留預估金額者 ? "3" : 立案之肇責類型
	return Y.length == 0 && N.length > 0 ? "3" : claimAccidentType ;
}
/***
 * 需求變更127增加
 * 修改身份證號碼，則該受害人下的費用之賠付金額需重新計算（因為要計算限額是否超出）
 * @param field
 */
function changeIdentifyNumber(field){
	var $prpLpersonLossObject = $(field).parents("tr[name='prpLpersonLossObject']"); //操作受害人
	var $identifyNumber = $prpLpersonLossObject.find(":input[name='prpLpersonLossIdentifyNumber']"); //受害人身份证
	var identifyNumber = $identifyNumber.val();
	var sumRealPay1 = $prpLpersonLossObject.find(":input[name='prpLpersonLossSumRealPay1']").val(); //被保險人賠付金額
	var sex = $prpLpersonLossObject.find(":input[name='prpLpersonLossSex']").val(); //性别
	var identityOfInjuredPerson = $prpLpersonLossObject.find(":input[name='prpLpersonLossIdentityOfInjuredPerson']").val(); //受害人身份
	if($.trim(identifyNumber) != ""){
		if((identityOfInjuredPerson == "1" && !checkIdentifyNumber(identifyNumber, sex))){
			alertMessage($identifyNumber, "請錄入正確的身份證號！");
			return false;
		} else if(sumRealPay1 > 0){//修改身份證，則重新計算各項費用之賠付金額
			$prpLpersonLossObject.find(":input[name='prpLpersonLossSumDefPay']").each(function(){
				$(this).change();
			});
		}
	}
}
/**
 * 理算任意險 車險自付額發票校驗
 */
function deductibleInvoiceCheck(){
	//多次理算，只有一張自負額發票，此時此發票為只讀，不做重複性檢核
//	mantis：CLM0074 ，處理人員：BK007 蘇哲，需求單編號：CLM0074.理賠系統-車體險訊息[自負額發票]
//	var $prpLcarInsuranceCompensateNo = $("#CarInsurance").find(":input[name='prpLcarInsuranceCompensateNo']");
//	if($prpLcarInsuranceCompensateNo.length > 0 ){
//		var compensateNo = $(":input[name='prpLcompensateCompensateNo']").val();
//		var carInsuranceCompensateNo = $prpLcarInsuranceCompensateNo.first().val();
//		//一筆賠案只需錄一筆車體險訊息
//		if( compensateNo != carInsuranceCompensateNo ){//
//			return true;
//		}
//	}
//	mantis：CLM0074 ，處理人員：BK007 蘇哲，需求單編號：CLM0074.理賠系統-車體險訊息[自負額發票]
	var prpLcarInsuranceCertificateType = $("#CarInsurance").find(":input[name='prpLcarInsuranceCertificateType']").val();//憑證類型
	var deductibleInvoice = $("#CarInsurance").find(":input[name='prpLcarInsuranceDeductibleInvoice']").val();//發票號碼
	var compensateNo = $(":input[name='prpLcompensateCompensateNo']").val();//計算書號
	var checkresult = true;
	if(prpLcarInsuranceCertificateType == "1"&& deductibleInvoice != ""){
		$.ajax({
			url : contextRootPath + "/compensate/deductibleInvoiceCheck.do?deductibleInvoice=" + deductibleInvoice + "&compensateNo="+compensateNo,
			type : "POST",
			dataType :"json",
			async : false,
			success :function(data){
				if(data.count !="0"){
					alert("該自負額發票號（"+deductibleInvoice+"）已存在，請重新輸入！");
					checkresult = false;
				}
			}
		});
	}
	return checkresult;
}

//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 START
function getCodeSelectForPersonLossLiabDetailCode(field,operateType,codeModel){
	var prpLpersonLossKindCode = $(field).parents("tr[name=prpLpersonFeeLossObject]").find(":input[name='prpLpersonLossKindCode']").val();
	var codeType = "PersonFeeTypeFlag";
	if(prpLpersonLossKindCode == "E9"){
		codeType += "E9";//PersonFeeTypeFlagE9
	}
	if("ondblclick" == operateType){
		if(codeModel == "code"){
			return code_CodeSelect(field,codeType,'0,1,2','Y','Y',fm.prpLcompensateRiskCode.value);
		}else if(codeModel=="name"){
			return code_CodeSelect(field,codeType,'-1,0,1','Y','N',fm.prpLcompensateRiskCode.value);
		}
	}else if("onchange" == operateType){
		if(codeModel == "code"){
			return code_CodeChange(field, codeType,'0,1,2','Y','Y',fm.prpLcompensateRiskCode.value);			
		}else if(codeModel=="name"){
			return code_CodeChange(field, codeType,'-1,0,1','Y','N',fm.prpLcompensateRiskCode.value);
		}
	}else if("onkeyup" == operateType){
		if(codeModel == "code"){
			return code_CodeSelect(field,codeType,'0,1,2','Y','Y',fm.prpLcompensateRiskCode.value);			
		}else if(codeModel=="name"){
			return code_CodeSelect(field,codeType,'-1,0,1','Y','N',fm.prpLcompensateRiskCode.value);
		}
	}
}

function cleanChangeCodeSelectForPersonLossLiabDetailCode(field){
	if($.trim(field.value)!=""){
		$(field).parents("tr[name=prpLpersonFeeLossObject]").find(":input[name='prpLpersonLossLiabDetailCode']").val("");
		$(field).parents("tr[name=prpLpersonFeeLossObject]").find(":input[name='prpLpersonLossLiabDetailName']").val("");
	}
}


function cleanE3E9(){
	//清空車損、物損賠付資訊
	var eachObj = $("tr[name='prpLlossObject']");
	eachObj.each(function (i, obj) {
		try {
			var checkE = $(obj).find(":input[name='prpLlossDtoKindCode']").val();
			if(undefined!=checkE && null!=checkE &&
					checkE.substr(0,1)=="E"){
				$(obj).find(":input[name='prpLlossDtoSumDefPay']").val("0");
			}
		} catch (e) {
			// TODO: handle exception
		}
	});

	//任意險受害人資訊
	var eachObj = $("tr[name='prpLpersonFeeLossObject']");
	eachObj.each(function (i, obj) {
		try {
			var checkE = $(obj).find(":input[name='prpLpersonLossKindCode']").val();
			if(undefined!=checkE && null!=checkE &&
					checkE.substr(0,1)=="E"){
				$(obj).find(":input[name='prpLpersonLossSumDefPay']").val("0");
			}
		} catch (e) {
			// TODO: handle exception
		}
	});
}
//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 END

//mantis： CLM0166，處理人員：DP0713，需求單編號：車體新商品上線險別0Y
function checkLimitDeductible(){
		 var exist0Y = $("#limitList").find(":input[name='limitKindCode'][value='0Y']").val()!=undefined?true:false;
		 if(!exist0Y){
			 return true;
		 }
		 var has0Y = false;//無車體險賠付
		 $("#spanlLoss").find("tr[name='prpLlossObject']").each(function(){
			 var kindCode = $(this).find(":input[name='prpLlossDtoKindCode']").val();
			 if(kindCode=='0Y'){
				 has0Y = true;
			 }
		 });
		 var limitDeductibleName =  "";
		 var limitDeductibleCount = "";
		 var limitDeductibleTypeConfirm="";
		 
		 var $limitObject = $("#limitList").find(":input[name='limitKindCode'][value='0Y']").parents("div[name='limitObject']");
		 if ($limitObject.length > 0) {
			 limitKindName = $limitObject.find("input[name='limitKindName']").val();
			 limitDeductibleCount = $limitObject.find("input[name='limitDeductibleCount']").val();
			 limitDeductibleTypeConfirm = $limitObject.find("input[name='limitDeductibleTypeConfirm']").val();
		 }
		 //mantis：CLM0235，處理人員： DP0713 ，需求單編號：新核心-0Y險種裡算提交檢核問題 START
		 var dutyVal = $("#prpLcompensateIndemnityDutyRate").val()!=undefined && $("#prpLcompensateIndemnityDutyRate").val()>0?true:false;
		 var exist07Chosed= $("#PrpLloss").find(":input[name='prpLlossDtoKindCode'][value='07']").val()!=undefined?true:false;
		 var _07SumRealPay= $("#PrpLloss").find(":input[name='prpLlossDtoKindCode'][value='07']").parents().find("#prpLlossDtoSumRealPay").val();
		 
		 if($("#indemnityDuty").val() == "5"){//存在車體險賠付
			 if(has0Y){
				 alert("本車肇事責任無責時，車損、物損賠付資訊不可填寫 "+limitKindName);
				 return false;
			 }
		 }else{
			 if(!has0Y && dutyVal && exist07Chosed && _07SumRealPay>0){
				 alert("本車肇事責任非無責時，車損、物損賠付資訊 必須填寫 "+limitKindName+"，有效保期內已使用"+limitDeductibleCount+"次");
				 return false;
			 }
		 }
		 //mantis：CLM0235，處理人員： DP0713 ，需求單編號：新核心-0Y險種裡算提交檢核問題 END
		 return true;
}

//mantis： CLM0166，處理人員：DP0713，需求單編號：車體新商品上線險別0Y
function clean07(){
	//清空車損、物損賠付資訊
	var eachObj = $("tr[name='prpLlossObject']");
	eachObj.each(function (i, obj) {
		try {
			if($(obj).find(":input[name='prpLlossDtoKindCode']").val()=='07'){	
				$(obj).find(":input[name='prpLlossDtoDeductible']").val("0");
				$(obj).find(":input[name='prpLlossDtoDutyDeductibleRate']").val("0");
			}
		} catch (e) {
			// TODO: handle exception
		}
	});
}

/**
 * mantis：CLM0175，處理人員：DP0713，需求單編號：新核心-車險計算書新增理賠已出險次數
 * @param field
 * @returns
 */
function checkDateBetweenHaventDuplicateCase(){
	var policyNo = $(":input[name='prpLdangerPolicyNo']").val();
	var checkresult = true;
	jQuery.ajax({
		url : contextRootPath + "/compensate/checkDateBetweenHaventDuplicateCase.do?policyNo=" + policyNo ,
		type : "POST",
		dataType :"json",
		async : false,
		success :function(data){
			if(data.duplicateCase !="0"){
				alert("請複查本案車輛維修事項是否有重複。");
				//checkresult = false;//改為只提示不擋
			}
		}
	});
	return checkresult;
}
/**
 * mantis：CLM0175，處理人員：DP0713，需求單編號：新核心-車險計算書新增理賠已出險次數
 * @param field
 * @returns
 */
function mustBeforeDamageDate(){
	var checkresult = true;
	var claimNo = $(":input[name='prpLcompensateClaimNo']").val();
	var repairStartDate = $(":input[name='prpLrepairFeeRepairStartDate_show_format_rcDate']").val();
	if(undefined!=repairStartDate && null!=repairStartDate && ""!=repairStartDate){
		var repairStartDateCheck = new Date(repairStartDate.replace("-","/").replace("-","/")) ; //進廠日期
		if (repairStartDateCheck instanceof Date) {
			jQuery.ajax({
				url : contextRootPath + "/compensate/checkDateBeforeDamageDate.do?claimNo=" + claimNo +"&repairStartDate="+repairStartDate,
				type : "POST",
				dataType :"json",
				async : false,
				success :function(data){
					if(null!=data.beforeDamageDate && !data.beforeDamageDate){
						alert("車輛訊息頁的「進廠日期」必須在出險日期之後");
						checkresult = false;
					}
				}
			});
		}
	}
	return checkresult;
}