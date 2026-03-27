/*******************************************************************************
 * DESC ：报案登记的脚本函数页面(车险类的) AUTHOR ：weishixin CREATEDATE ： 2013-09-10 MODIFYLIST ：
 * Name Date Reason/Contents
 * ------------------------------------------------------
 ******************************************************************************/
// 比较两个日期字符串
// date1=date2则返回0 , date1>date2则返回1 , date1<date2则返回-1

function compareFullDate(date1, date2) {
	var strValue1 = date1.split(DATE_DELIMITER);
	var date1Temp = new Date(strValue1[0], parseInt(strValue1[1], 10) - 1, parseInt(strValue1[2], 10));

	var strValue2 = date2.split(DATE_DELIMITER);
	var date2Temp = new Date(strValue2[0], parseInt(strValue2[1], 10) - 1, parseInt(strValue2[2], 10));

	if (date1Temp.getTime() == date2Temp.getTime())
		return 0;
	else if (date1Temp.getTime() > date2Temp.getTime())
		return 1;
	else
		return -1;
}


/**
 * @description 检查报案登记
 * @param 无
 * @return 通过返回true,否则返回false
 */

function checkForm() {
	var curDate = new Date();
	var year = curDate.getFullYear();
	var month = curDate.getMonth() + 1;
	var day = curDate.getDate();

	var hour = curDate.getHours();
	var minute = curDate.getMinutes();

	var today = year + "-" + month + "-" + day;

	// 录单日期在报案日起之後
	if (compareFullDate(fm.prpLregistReportDate.value, today) > 0) {
		errorMessage("報案時間不能晚於當前日期");
		return false;
	}

	var i = compareFullDate(fm.prpLregistDamageStartDate.value, fm.prpLregistReportDate.value);
	if (i > 0) {
		errorMessage("報案時間不能早於出險時間");
		return false;
	}

	if (i == 0 && (parseInt(fm.prpLregistDamageStartHour.value , 10) > parseInt(fm.prpLregistReportHour.value , 10))) {
		errorMessage("報案時間不能早於出險時間");
		return false;
	}

	if (i == 0 && (parseInt(fm.prpLregistDamageStartMinute.value , 10) > parseInt(fm.prpLregistReportMinute.value , 10))) {
		if (parseInt(fm.prpLregistDamageStartHour.value , 10) >= parseInt(fm.prpLregistReportHour.value , 10)) {
			errorMessage("報案時間不能早於出險時間");
			return false;
		}
	}
	return true;
}


/**
 * @description 提交
 * @param 无
 * @return 通过返回true,否则返回false
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
 * @description 清除
 * @param 无
 * @return 通过返回true,否则返回false
 */

function resetForm() {
	if (window.confirm(i18n.prompt.regist.isClear)) {
		var vURL = fm.originalRequestURITemp.value;	 
		location.href = vURL;
		return true;
	} else {
		return false;
	}
}

/**
 * @description 根据按钮状态保存报案数据
 * @param this
 * @param 保存状态
 * @return 通过返回true,否则返回false
 */

function beforeSaveForm(field, saveType) {
	var nodeType = fm.nodeType.value ;
	//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業
	var commMsg = "請確認受損訊息內【人傷跟蹤訊息】頁簽之受害人身分證號、乘坐狀況、乘坐牌照號碼、受害人出生年月日皆為必填。";
    //mantis：CLM0257，處理人員：DP0713，需求單編號：新核心-備案任務處理，新增[是否為強制險區塊鏈攤賠案件]選項 START
    var compulsoryBchain = false;
	if(undefined!=fm.prpLregistIsCompulsoryBchainClaim && "undefined"!=fm.prpLregistIsCompulsoryBchainClaim
    		&& fm.prpLregistIsCompulsoryBchainClaim.length>0){
    	if("Y"==fm.prpLregistIsCompulsoryBchainClaim[1].value 
    		&& fm.prpLregistIsCompulsoryBchainClaim[1].checked == true){
    		compulsoryBchain = true;
    	}
    }
    //mantis：CLM0257，處理人員：DP0713，需求單編號：新核心-備案任務處理，新增[是否為強制險區塊鏈攤賠案件]選項 END
	//mantis：CLM0209，處理人員：DP0713，需求單編號：新核心-立案節點同步備案人傷訊息更新區塊鏈資料 START
	if (saveType == "44") {
		//mantis：CLM0291 ，處理人員： DP0713 ，需求單編號：第三方車輛各承保公司判斷強制證號碼數調整06-和泰小於等於15碼 START
		if(!checkCINo()){
			return false;
		}
		//mantis：CLM0291 ，處理人員： DP0713 ，需求單編號：第三方車輛各承保公司判斷強制證號碼數調整06-和泰小於等於15碼END
		var riskCodeForWf = document.getElementsByName('prpLclaimRiskCode')[0].value
		if(riskCodeForWf == "B01"){//立案這個參數抓的到  備案用(fm.prpLregistRiskCode)
			var countPrpLpersonTraceIdNumber = 0;
			$('input[name="prpLpersonTraceIdNumber"]').each(function(index){
				if(index!=0){			
					if($(this).val()!=""){
						countPrpLpersonTraceIdNumber++;
					}
				}
			});
			//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 (判斷人傷資料及區塊鏈移除) START
			/*
			//mantis：CLM0257，處理人員：DP0713，需求單編號：新核心-備案任務處理，新增[是否為強制險區塊鏈攤賠案件]選項 START
			if(!compulsoryBchain){
				//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 START
				if(countPrpLpersonTraceIdNumber==0){
					//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 
					alert(commMsg);
					return false;
				}
				for (var i = 1; i < fm.prpLpersonTraceIdNumber.length; i++) {
					if (fm.prpLpersonTraceIdNumber[i].value == '') {
						//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 
						alert(commMsg);
						return false;
					}
				}
				for (var i = 1; i < fm.prpLpersonTraceLicenseno.length; i++) {
					if (fm.prpLpersonTraceLicenseno[i].value == '') {
						//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 
						alert(commMsg);
						return false;
					}
				}
				for (var i = 1; i < fm.prpLpersonTraceApplicantBirthday.length; i++) {
					if (fm.prpLpersonTraceApplicantBirthday[i].value == '') {
						//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 
						alert(commMsg);
						return false;
					}
				}
				if(fm.defValue.value == "Y"){
					if(!compulsoryCaseQuery310()){
						return false;
					}
				}
			}
			//mantis：CLM0257，處理人員：DP0713，需求單編號：新核心-備案任務處理，新增[是否為強制險區塊鏈攤賠案件]選項 END
			 */
			//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 END
		}
		
	}
	//mantis：CLM0209，處理人員：DP0713，需求單編號：新核心-立案節點同步備案人傷訊息更新區塊鏈資料 END
	if (saveType == "4") {
		//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 START
		var riskCode = null!=fm.prpLregistRiskCode&&undefined!=fm.prpLregistRiskCode?fm.prpLregistRiskCode.value:null;
		//mantis：CLM0226，處理人員：DP0713，需求單編號：新核心-立案修改功能修改出險地點調整
		//mantis：CLM0257，處理人員：DP0713，需求單編號：新核心-備案任務處理，新增[是否為強制險區塊鏈攤賠案件]選項
		if(!compulsoryBchain && null!=riskCode && (riskCode == "B01" || fm.registType.value != "0")){
			var countPrpLpersonTraceIdNumber = 0;
			$('input[name="prpLpersonTraceIdNumber"]').each(function(index){
				if(index!=0){			
					if($(this).val()!=""){
						countPrpLpersonTraceIdNumber++;
					}
				}
			});
			var unnecessary = fm.registType.value=='2'?true:false;//備案的備案類型 有關聯單，可以沒有人傷資料
			if(countPrpLpersonTraceIdNumber==0){
				if(!unnecessary){
					alert(commMsg);
					return false;
				}
			}
			for (var i = 1; i < fm.prpLpersonTraceIdNumber.length; i++) {
				if (fm.prpLpersonTraceIdNumber[i].value == '') {
					if(!unnecessary){
						alert(commMsg);
						return false;
					}
				}
			}
			for (var i = 1; i < fm.prpLpersonTraceLicenseno.length; i++) {
				if (fm.prpLpersonTraceLicenseno[i].value == '') {
					if(!unnecessary){
						alert(commMsg);
						return false;
					}
				}
			}
			for (var i = 1; i < fm.prpLpersonTraceApplicantBirthday.length; i++) {
				if (fm.prpLpersonTraceApplicantBirthday[i].value == '') {
					if(!unnecessary){
						alert(commMsg);
						return false;
					}
				}
			}
			if(!compulsoryCaseQuery310()){
				if(!unnecessary){
					return false;
				}
			}
		}
		
		//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 END
		var sharingFlag = ""; // 同业共摊标记
		if (nodeType == 'regis') {
			for (var i = 0; i < fm.prpLregistSharingFlag.length; i++) {
				if (fm.prpLregistSharingFlag[i].checked) {
					sharingFlag = fm.prpLregistSharingFlag[i].value;
					break;
				}
			}
			if (saveType == '4' && '1' == sharingFlag) {
				alert("此保單涉及同業共攤，且同業已賠付，請暫存!");
				return false;
			}
			//mantis： CLM0105，處理人員：BL061 張明財，需求單編號：CLM0105 新核心-手機正規化 start
			var errorMessage = "";
			var reg ="";
			
			var prpLregistReportorPhoneNumber =fm.prpLregistReportorPhoneNumber.value;
			if (prpLregistReportorPhoneNumber.length > 0) {
				 if (prpLregistReportorPhoneNumber.substr(0, 2)=='09'){
				    	reg =/^09[0-9]{8}$/;
				    	  if(!reg.test(prpLregistReportorPhoneNumber)){
				    		errorMessage =errorMessage +"備案人市話有誤\n";
				    	}
				  } else {
				      reg =/^[0-9]{2,3}[0-9]{7,8}$/;
				      if (!reg.test(prpLregistReportorPhoneNumber)){
				    	errorMessage =errorMessage +"備案人市話有誤\n";
				      }
				}
			  }
			var prpLregistReportorMobile =fm.prpLregistReportorMobile.value;
			if (prpLregistReportorMobile.length > 0) {
			    	 reg =/^09[0-9]{8}$/;
			    	  if(!reg.test(prpLregistReportorMobile)){
			    		errorMessage =errorMessage +"備案人手機 有誤\n";
			    	}	
			  }
			var prpLregistPhoneNumber =fm.prpLregistPhoneNumber.value;
			if (prpLregistPhoneNumber.length > 0) {
				 if (prpLregistPhoneNumber.substr(0, 2)=='09'){
				    	reg =/^09[0-9]{8}$/;
				    	  if(!reg.test(prpLregistPhoneNumber)){
				    		errorMessage =errorMessage +"駕駛人電話 有誤\n";
				    	}
				  } else {
				      reg =/^[0-9]{2,3}[0-9]{7,8}$/;
				      if (!reg.test(prpLregistPhoneNumber)){
				    	errorMessage =errorMessage +"駕駛人電話有誤\n";
				      }
				}
			  }
			
			var prpLregistDriverMobile =fm.prpLregistDriverMobile.value;
			if (prpLregistDriverMobile.length > 0) {
			    	 reg =/^09[0-9]{8}$/;
			    	 if(!reg.test(prpLregistDriverMobile)){
			    		errorMessage =errorMessage +" 駕駛人手機 有誤\n ";
			    }   	
			  }

			var prpLregistInsuredPhoneNumber =fm.prpLregistInsuredPhoneNumber.value;
			if (prpLregistInsuredPhoneNumber.length > 0) {
				 if (prpLregistInsuredPhoneNumber.substr(0, 2)=='09'){
				    	reg =/^09[0-9]{8}$/;
				    	  if(!reg.test(prpLregistPhoneNumber)){
				    		errorMessage =errorMessage +"被保險人電話有誤\n";
				    	}
				  } else {
				      reg =/^[0-9]{2,3}[0-9]{7,8}$/;
				      if (!reg.test(prpLregistInsuredPhoneNumber)){
				    	errorMessage =errorMessage +"被保險人電話有誤\n";
				      }
				}
			  }
			for (var i = 1; i < fm.prpLdriverDriverPhone.length; i++) {
				var prpLdriverDriverPhone =fm.prpLdriverDriverPhone[i].value;
				if (prpLdriverDriverPhone.length > 0) {
					 if (prpLdriverDriverPhone.substr(0, 2)=='09'){
					    	reg =/^09[0-9]{8}$/;
					    	  if(!reg.test(prpLdriverDriverPhone)){
					    		errorMessage =errorMessage +"駕駛人電話有誤\n";
					    	}
					  } else {
					      reg =/^[0-9]{2,3}[0-9]{7,8}$/;
					      if (!reg.test(prpLdriverDriverPhone)){
					    	errorMessage =errorMessage +"駕駛人電話有誤\n";
					      }
					}
				  }
			}
			
			for (var i = 1; i < fm.prpLdriverMobilePhone.length; i++) {
				var prpLdriverMobilePhone =fm.prpLdriverMobilePhone[i].value;
				if (prpLdriverMobilePhone.length > 0) {
					    	reg =/^09[0-9]{8}$/;
					    	  if(!reg.test(prpLdriverMobilePhone)){
					    		errorMessage =errorMessage +"駕駛人手機 有誤\n";
					    	}
					}
				  }
			if (errorMessage.length > 0) {
				alert(errorMessage);
				return false;
			}//mantis： CLM0105，處理人員：BL061 張明財，需求單編號：CLM0105 新核心-手機正規化  end
			//把判断出险原因不能为空放在这儿，去掉ValidateData.js里面的判断
			var prpLregistDamageCode = trim(fm.prpLregistDamageCode.value);
			var prpLregistDamageName = trim(fm.prpLregistDamageName.value);
			var prpLregistDamageCodeBZ = trim(fm.prpLregistDamageCodeBZ.value);
			var prpLregistDamageNameBZ = trim(fm.prpLregistDamageNameBZ.value);
//			if (fm.registType.value != "1" && (null == prpLregistDamageCode || "" == prpLregistDamageCode || null == prpLregistDamageName || "" == prpLregistDamageName)) {
//				alert("任意險出險原因不能為空，請錄入！");
//				return false;
//			}
//			if (fm.registType.value != "0" && (null == prpLregistDamageCodeBZ || "" == prpLregistDamageCodeBZ || null == prpLregistDamageNameBZ || "" == prpLregistDamageNameBZ)) {
//				alert("強制險出險原因不能為空，請錄入！");
//				return false;
//			}
		} else if (nodeType == 'check' && document.getElementsByName("prpLdriverSerialNo").length < 2) { //查勘至少录入一条驾驶员记录。
			alert("沒有錄入駕駛員記錄，請至少錄入一條駕駛員記錄！");
			return false;
		}
		/*
		mantis： CLM0001，處理人員：David，需求單編號：CLM0001 start
		     處理過程：取消生日為必填欄位。 20190318 需求再次變更，生日依然需必填
		*/
		for (var i = 1; i < fm.prpLdriverLicenseNo.length; i++) {
			if (fm.prpLdriverBirthday[i].value == '') {
				alert("駕駛員訊息出生年份不允許為空，請錄入！");
				return false;
			}
		}
		/* mantis： CLM0001，處理人員：David，需求單編號：CLM0001 end*/
		var flag = 1;
		//mantis： CLM0120 ，處理人員：DP0728 蘇英碩，需求單編號：CLM0120.新核心-強制證號長度管控
		//mantis：CLM0291 ，處理人員： DP0713 ，需求單編號：第三方車輛各承保公司判斷強制證號碼數調整06-和泰小於等於15碼 START
		if(!checkCINo(2)){
			return false;
		}
		////mantis：CLM0291 ，處理人員： DP0713 ，需求單編號：第三方車輛各承保公司判斷強制證號碼數調整06-和泰小於等於15碼END
		// 声明标示变量，1表示驾驶员信息中輸入的车牌号全部是已经輸入的车辆
		for (var i = 1; i < fm.prpLdriverLicenseNo.length; i++) {
			// 遍历驾驶员信息中的所有车牌号
			if (flag == 0 || fm.prpLdriverLicenseNo[i].value == "" || fm.prpLdriverLicenseNo[i].value == null) {
				break; // 有一个司机驾驶的车辆不是已经輸入的车辆或者为空就跳出循环
			}
			if (flag == 1) { // 如果上一个司机驾驶的车辆是已经輸入的车辆则进入循环
				flag = 0;
				for (var j = 1; j < fm.prpLthirdPartyLicenseNo.length; j++) {
					// 遍历已经輸入车辆的车牌号
					if (fm.prpLdriverLicenseNo[i].value == fm.prpLthirdPartyLicenseNo[j].value) {
						// 进行比较
						flag = 1;
						break;
					}
				}
			}
		}
		if (nodeType == 'check') {
			var flag = true;
			$(":input[name='kindCode']").each(function (i, e) {
				if (i > 0 && $(e).val() == '') {
					alert("車損險別訊息不能爲空");
					flag = false;
					return false;
				}
			});
			if (!flag) {
				return false;
			}
			//mantis： CLM0105，處理人員：BL061 張明財，需求單編號：CLM0105 新核心-手機正規化 start
			if (saveType == "4") {
				var errorMessage = "";
			    var reg ="";
				var prpLregistPhoneNumber =fm.prpLregistPhoneNumber.value;
				if (prpLregistPhoneNumber.length > 0) {
					 if (prpLregistPhoneNumber.substr(0, 2)=='09'){
					    	reg =/^09[0-9]{8}$/;
					    	  if(!reg.test(prpLregistPhoneNumber)){
					    		errorMessage =errorMessage +"被保險人電話有誤\n";
					    	}
					  } else {
					      reg =/^[0-9]{2,3}[0-9]{7,8}$/;
					      if (!reg.test(prpLregistPhoneNumber)){
					    	errorMessage =errorMessage +"被保險人電話有誤\n";
					      }
					}
				  }
				
				var prpLregistMobile =fm.prpLregistMobile.value;
				if (prpLregistMobile.length > 0) {
					 if (prpLregistMobile.substr(0, 2)=='09'){
					    	reg =/^09[0-9]{8}$/;
					    	  if(!reg.test(prpLregistMobile)){
					    		errorMessage =errorMessage +"被保險人手機有誤\n";
					    	}
					  } 
				  }
				
				var reportorPhoneNumber =fm.reportorPhoneNumber.value;
				if (reportorPhoneNumber.length > 0) {
					 if (reportorPhoneNumber.substr(0, 2)=='09'){
					    	reg =/^09[0-9]{8}$/;
					    	  if(!reg.test(reportorPhoneNumber)){
					    		errorMessage =errorMessage +"備案人電話有誤\n";
					    	}
					  } else {
					      reg =/^[0-9]{2,3}[0-9]{7,8}$/;
					      if (!reg.test(prpLregistPhoneNumber)){
					    	errorMessage =errorMessage +"備案人電話有誤\n";
					      }
					}
				  } 
				var phoneNumber =fm.phoneNumber.value;
				if (phoneNumber.length > 0) {
					 if (phoneNumber.substr(0, 2)=='09'){
					    	reg =/^09[0-9]{8}$/;
					    	  if(!reg.test(phoneNumber)){
					    		errorMessage =errorMessage +"聯系電話有誤\n";
					    	}
					  } else {
					      reg =/^[0-9]{2,3}[0-9]{7,8}$/;
					      if (!reg.test(phoneNumber)){
					    	errorMessage =errorMessage +"聯系電話有誤\n";
					      }
					}
				  } 
				
				var driverMobile =fm.driverMobile.value;
				if (driverMobile.length > 0) {
						var reg =/^09[0-9]{8}$/;
						if(!reg.test(driverMobile)){
		    			  errorMessage =errorMessage +"駕駛人手機有誤\n";
						}
				} 
				for (var i = 1; i < fm.prpLdriverDriverPhone.length; i++) {
					var prpLdriverDriverPhone =fm.prpLdriverDriverPhone[i].value;
					if (prpLdriverDriverPhone.length > 0) {
						 if (prpLdriverDriverPhone.substr(0, 2)=='09'){
						    	reg =/^09[0-9]{8}$/;
						    	  if(!reg.test(prpLdriverDriverPhone)){
						    		errorMessage =errorMessage +"駕駛人"+i+"電話有誤\n";
						    	}
						  } else {
						      reg =/^[0-9]{2,3}[0-9]{7,8}$/;
						      if (!reg.test(prpLdriverDriverPhone)){
						    	errorMessage =errorMessage +"駕駛人"+i+"電話有誤\n";
						      }
						}
					  }
				}
				
				for (var i = 1; i < fm.prpLdriverMobilePhone.length; i++) {
					var prpLdriverMobilePhone =fm.prpLdriverMobilePhone[i].value;
					if (prpLdriverMobilePhone.length > 0) {
						    	reg =/^09[0-9]{8}$/;
						    	  if(!reg.test(prpLdriverMobilePhone)){
						    		errorMessage =errorMessage +"駕駛人"+i+"手機 有誤\n";
						    	}
						}
					  }
				if (errorMessage.length > 0) {
					alert(errorMessage);
					return false;
				}//mantis： CLM0105，處理人員：BL061 張明財，需求單編號：CLM0105 新核心-手機正規化 END
			}
		}
		if (flag == 0) {
			alert(i18n.regist.driverDrivingVehicle); // 驾驶员所驾驶的车辆必须是已经輸入的车辆
			return false;
		}
	}
	if(nodeType == "claim"){
		var $simpleFlag = $(":input[name='prpLclaimSimpleFlag']");
		$simpleFlag.val("0");

		//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 START
		var msg = "";
		if(saveType =="22" || saveType=="44"){//儲存(44) 或 暫存(22) 才要擋
			var eachObj = $("tr[name='prpLclaimLossObject']");
			eachObj.each(function (i, obj) {
				if($(obj).find(":input[name='prpLclaimLossKindCode']").val()=="E9"){
					if(!($(obj).find(":input[name='prpLclaimLossFeeCategory']").val()=="H" || 
							$(obj).find(":input[name='prpLclaimLossFeeCategory']").val()=="D")){
						var name = $(obj).find(":input[name='prpLclaimLossKindName']").val();
						msg = msg + (name+" 的範圍必須選擇 失能 或 死亡，請重新選擇。 \n");
					}
				}
			});
			if (msg.length > 0) {
				alert(msg);
				return false;
			}
		}
		//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 END
		//mantis： CLM0058 ，處理人員：BK007 蘇哲，需求單編號：CLM0058車臉出險只走簡易流程，並將一般流程隱藏  start
		if (saveType == "44") {// 車險立案簡易賠案
			$simpleFlag.val("1");
			saveType = "4";
		}
		if (saveType == "22") {// 車險立案簡易賠案
			$simpleFlag.val("1");
			saveType = "2";
		}
		//mantis： CLM0105，處理人員：BL061 張明財，需求單編號：CLM0105 新核心-手機正規化 start
		var errorMessage = "";
		if (saveType == "4") {
			for (var i = 1; i < fm.prpLdriverDriverPhone.length; i++) {
				var prpLdriverDriverPhone =fm.prpLdriverDriverPhone[i].value;
				if (prpLdriverDriverPhone.length > 0) {
					 if (prpLdriverDriverPhone.substr(0, 2)=='09'){
					    	reg =/^09[0-9]{8}$/;
					    	  if(!reg.test(prpLdriverDriverPhone)){
					    		errorMessage =errorMessage +"駕駛人電話有誤\n";
					    	}
					  } else {
					      reg =/^[0-9]{2,3}[0-9]{7,8}$/;
					      if (!reg.test(prpLdriverDriverPhone)){
					    	errorMessage =errorMessage +"駕駛人電話有誤\n";
					      }
					}
				  }
			}
			
			for (var i = 1; i < fm.prpLdriverMobilePhone.length; i++) {
				var prpLdriverMobilePhone =fm.prpLdriverMobilePhone[i].value;
				if (prpLdriverMobilePhone.length > 0) {
					    	reg =/^09[0-9]{8}$/;
					    	  if(!reg.test(prpLdriverMobilePhone)){
					    		errorMessage =errorMessage +"駕駛人手機 有誤\n";
					    	}
					}
				  }
			if (errorMessage.length > 0) {
				alert(errorMessage);
				return false;
			}//mantis： CLM0105，處理人員：BL061 張明財，需求單編號：CLM0105 新核心-手機正規化 start
			
			//mantis： CLM0160 ，處理人員：DP0713 ，需求單編號：預估金額100萬卡控問題 START
			var sum = 0;
			var limitAmt = 1000000;
			for ( var j = 1; j < fm.prpLclaimLossCurrency.length; j++) {
				if (isEmptyField(fm.prpLclaimLossCurrency[j])) {
					errorMessage =errorMessage +("第" + j + "條估損金額中幣别不能為空!\n");
					//fm.prpLclaimLossCurrency[j].focus();
					//return false;
				}
				if (isEmptyField(fm.prpLclaimLossKindCode[j])) {
					//mantis：CLM0244 ，處理人員： DP0713 ，需求單編號：新核心-車險立案預估金額判斷新增不可為零
					errorMessage =errorMessage +("第" + j + "條估損金額中險別代碼不能為空！\n");
					//fm.prpLclaimLossKindCode[j].focus();
					//return false;
				}
				if (isEmptyField(fm.prpLclaimLossSumClaim[j])) {
					errorMessage =errorMessage +("第" + j + "條估損金額中金額不能為空!\n");
					//fm.prpLclaimLossSumClaim[j].focus();
					//return false;
				}else{
					//gradeLevel != 0 非(理賠助理/部門理賠科長)&comcode =00(總公司)
					if(fm.gradeLevel.value != '0' && Number(fm.prpLclaimLossSumClaim[j].value) > limitAmt){
						errorMessage =errorMessage +("第" + j + "條估損金額中金額超過100萬元，僅能由總公司覆核人員進行處理!\n");
						//return false;
					}
					sum += Number(fm.prpLclaimLossSumClaim[j].value);
				}
			}
			if(fm.gradeLevel.value != '0' && sum >= limitAmt && ""==errorMessage){
				errorMessage = ("估損合計金額超過100萬元，僅能由總公司覆核人員進行處理!");
				//return false;
			}
			if (errorMessage.length > 0) {
				alert(errorMessage);
				return false;
			}
			//mantis： CLM0160 ，處理人員：DP0713 ，需求單編號：預估金額100萬卡控問題 END
		}
		//mantis： CLM0058 ，處理人員：BK007 蘇哲，需求單編號：CLM0058車臉出險只走簡易流程，並將一般流程隱藏  end
	}
	saveForm(field, saveType)
}

function saveForm(field, saveType) {
	// reason:增加输单日期和出险日期的判断，输单日期必须在出险日期之後
	var prpLregistStartDate = fm.prpLregistStartDate.value;
	var prpLregistEndDate = fm.prpLregistEndDate.value;
	var prpLregistDamageStartDate = fm.prpLregistDamageStartDate.value;
	if(prpLregistDamageStartDate.length == 0 ){
		alert("請輸入出險日期！");
		return false;
	}
	var prpLregistReportDate = fm.prpLregistReportDate.value;//备案日期
	if(prpLregistReportDate.length == 0 ){
		alert("備案日期不能為空！");
		return false;
	}
	var prpLregistInputDate = fm.prpLregistInputDate.value;//输入日期
	var startDate = new Date(prpLregistStartDate.replace(/-/g,"/"));//起保日期
	startDate.setHours(parseInt(fm.prpLregistStartHour.value , 10),0,0);
	var endDate = new Date(prpLregistEndDate.replace(/-/g,"/"));//終保日期
	endDate.setHours(parseInt(fm.prpLregistEndHour.value , 10),0,0);
	var damageStartDate = new Date(prpLregistDamageStartDate.replace(/-/g,"/"));//出險日期
	var damageStartHour  = fm.prpLregistDamageStartHour.value;
	var damageStartMinute  = fm.prpLregistDamageStartMinute.value;
	damageStartDate.setHours(parseInt(damageStartHour , 10),parseInt(damageStartMinute , 10),0);
	var reportDate = new Date(prpLregistReportDate.replace(/-/g,"/"));//備案日期
	var reportHour  = fm.prpLregistReportHour.value;
	var reportMinute  = fm.prpLregistReportMinute.value;
	reportDate.setHours(parseInt(reportHour , 10),parseInt(reportMinute , 10),0);
	
	var inputDate = new Date(prpLregistInputDate.replace(/-/g,"/"));//出單日期

	if ((damageStartDate < startDate) || (damageStartDate > endDate)) {
		alert(i18n.regist.tipDangerTimeDuringReport); // 提示：出险时间在保险期间以外，不给予报案!
		return false;
	}
	
	if (prpLregistInputDate < prpLregistDamageStartDate) {
		alert(i18n.regist.dateCannotGreaterDanger); //出险日期不能大於输单日期
		return false;
	}
	if (reportDate < damageStartDate) {
		alert(i18n.regist.dateCannotGreaterDanger); //出险日期不能大於输单日期
		return false;
	}
	if (saveType == "4") {
		// 如果輸入了从事行业，一级、二级和三级都要輸入 start
		for (i = 1; i < fm.prpLpersonTracePersonNo.length; i++) {
			if (fm.prpLpersonTraceJobCode1[i].value != "") {
				if (fm.prpLpersonTraceJobCode2[i].value == "") {
					alert(i18n.certainLoss.pleaseSelectIndustry2); // 请选择二级行业！
					fm.prpLpersonTraceJobName2[i].focus();
					return false;
				}
				if (fm.prpLpersonTraceJobCode[i].value == "") {
					alert(i18n.certainLoss.pleaseSelectIndustry3); // 请选择三级行业！
					fm.prpLpersonTraceJobName[i].focus();
					return false;
				}
			}
			if (fm.prpLpersonTraceJobCode2[i].value != "") {
				if (fm.prpLpersonTraceJobCode[i].value == "") {
					alert(i18n.certainLoss.pleaseSelectIndustry3); // 请选择三级行业！
					fm.prpLpersonTraceJobName[i].focus();
					return false;
				}
			}
		}

		var errorMessage = "";
		var riskCode = fm.prpLregistRiskCode.value;
		
		var othFlag = fm.prpLregistOthFlag.value;
		if (othFlag.substring(2, 3) == "1") {
			alert(i18n.regist.policyNotAllowReport); // 保单已退保，不允许报案！
			return false;
		}
		if (othFlag.substring(3, 4) == "1") {
			alert(i18n.regist.policyNotAllowReport); // 保单已退保，不允许报案！
			return false;
		}
		var message = "";
		// 获取报案出险延期天数
		var delayDays = fm.configValue.value;
		var regist_damage = (reportDate.getTime() - damageStartDate.getTime()) / (24 * 60 * 60 * 1000);
		if (regist_damage >= delayDays) {
			message = message + "報案出險延期天數大於" + delayDays + "天，是否通過？\n";
		}
		if (message != "") {
			if (window.confirm(message) == false) {
				return false;
			}
		}

		var context = fm.prpLregistTextContextInnerHTML.value;
		if (context.length < 1) {
			errorMessage = errorMessage + "出險摘要不允許為空\n";
		}

		var prpLregistDamageAddress = fm.prpLregistDamageAddress.value;
		if (prpLregistDamageAddress.length < 1) {
			errorMessage = errorMessage + "出險地點不允許為空\n";
		}
		if (prpLregistDamageAddress.length > 100) {
			errorMessage = errorMessage + "出險地點長度不能超過100個字符（50個字），請做刪減！\n";
		}
		var prpLregistReportorName = fm.prpLregistReportorName.value;
		if (prpLregistReportorName.length < 1) {
			errorMessage = errorMessage + "報案人不允許為空\n";
		}

		var prpLregistLinkerName = fm.prpLregistLinkerName.value;
		if (prpLregistLinkerName.length < 1) {
			errorMessage = errorMessage + "聯繫人不允許為空\n";
		}

		if (fm.registType) {
			if (fm.registType.value == "") {
				errorMessage = errorMessage + "請選擇報案類型\n";
			}
		}

		/**
		 * 协会车牌号重复控制
		 */
		var thirdCarLicenses1 = document.getElementsByName('prpLthirdPartyLicenseNo');
		var carCount = thirdCarLicenses1.length;
		for (var i = carCount - 1; i > 0; i--) {
			for (var k = 1; k < i; k++) {
				if (thirdCarLicenses1[k].value != "") {
					if (thirdCarLicenses1[i].value == thirdCarLicenses1[k].value) {
						errorMessage = errorMessage + "輸入車牌號重複\n";
						break;
					}
				}
			}
		}
		var carKindCodeList = document.getElementsByName("carKindCode");
		for (var i = 2; i < carCount; i++) {
			if (carKindCodeList[i].value != '98' && carKindCodeList[i].value != '99') {
				license = thirdCarLicenses1[i];
				var strInput = license.value;
				if (strInput == null || strInput.length == 0) {
					alert("牌照號碼不能為空!");
					return false;
				}
				var re = /^[\u4e00-\u9fa5a-zA-Z0-9- ]{1,12}$/;
				if (strInput.search(re) == -1) {
					alert("輸入的牌照號碼格式不正確");
					return false;
				}
			}
		}

		// 当事故处理类型为1（当事人自行协商处理）时，三者车为必录项 begin
		//var strManageType = document.getElementsByName('prpLregistManageType');
		//if (strManageType != null && strManageType.length == 1) {
			//if (strManageType[0].value == '1' && carCount < 3) // 因为前台有个disable域，用来新增模板，所以这取小於3
			//{
				//errorMessage = errorMessage + "事故處理類型為 當事人自行協商處理時，必須輸入三者車信息\n";
			//}
		//}
		// 当事故处理类型为1（当事人自行协商处理）时，三者车为必录项 end
		var TracePersonName = document.getElementsByName('prpLpersonTracePersonName');
		for (var i = 1; i < TracePersonName.length; i++) {
			if (TracePersonName[i].value == "") {
				errorMessage = errorMessage + i18n.regist.peopleTrackInfoNotEmpty + "\n"; // 人伤跟踪信息中伤者姓名不能为空\n
				break;
			}
		}

		if (errorMessage.length > 0) {
			alert(errorMessage);
			return false;
		}
		// ValidateData.js中的校验不起作用时，因为没有调用校验方法
		if (!validateForm(fm, 'ThirdParty_Data,Driver_Data,ThirdCarLoss_Data,PersonTrace_Data')) {
			return false;
		}
		//校验身份证录入
		var driverName = document.getElementsByName('prpLdriverDriverName');
		var driverSex = document.getElementsByName('driverSex');
		var drivingCarType = document.getElementsByName('drivingCarType');
		var identifyNumber = document.getElementsByName('prpLdriverIdentifyNumber');
		var driverIdentity = document.getElementsByName("prpLdriverDriverIdentity");
		for (var i = 1; i < driverName.length; i++) {
			if(identifyNumber[i].value!=""){
				if (driverIdentity[i].value=="1" && !checkIdentifyNumber(identifyNumber[i].value, driverSex[i].value)) {
					alert("請爲駕駛員 " + driverName[i].value + " 錄入正確的身份證號碼");
					return false;
				}else if (driverIdentity[i].value=="3"&&!checkUniformNo(identifyNumber[i].value)) {
					alert("請爲駕駛員 " + driverName[i].value + " 錄入正確的統一編號");
					return false;
				}
			}
		}
		if (checkForm() == false) {
			return false;
		}
		//mantis：CLM0204，處理人員：CE046，需求單編號：新核心-第三方強制證號規則調整 START
		if (checkCINo() == false) {
			return false;
		}
		//mantis：CLM0204，處理人員：CE046，需求單編號：新核心-第三方強制證號規則調整 END
		// add for : 报案提交时提示报案时间超出险时间48小时，是否继续
		if (confirmOverTime() && !window.confirm("報案時間已超過出險時間48小時，是否繼續")) {
			return false;
		}

		for (var i = 1; i < fm.prpLthirdPartySerialNo.length; i++) {
			if (isRightDutyPercent(fm.prpLthirdPartyDutyPercent[i]) == false) {
				return false;
			}
		}


		if (saveType == "4") {

			// 1.检查一下人伤跟踪是否有记录，如果有记录，可以调度人伤
//			var count = getElementCount("prpLpersonTracePersonNo");
//			if (count > 1) fm.prpLpersonTraceSelectSend.value = "1";
			// 2.检查一下财产定损是否有记录，如果有记录，可以调度财产定损
			count = getElementCount("prpLthirdPropItemNo");
			if (count > 1) fm.prpLthirdPropSelectSend.value = "1";
			// reason:提交的时候产生人伤和财产的定损调度。

			// 还是需要检查是不是选择了一个调度，就是如果是车险的，需要检查至少选择一个车做为调度
			if (!submittime()) return false;
			// 人伤设置
			ableAllInput();
		}
		var underWriteEndDate = fm.prpLregistUnderWriteEndDate.value;
		if (underWriteEndDate != "") {
			if ((damageStartDate < underWriteEndDate) && (startDate < underWriteEndDate)) {
				if (confirm("出險時間在起保日期到簽單日期之間！是否通過") == false) {
					return false;
				}
			}
		}
	}
	fm.buttonSaveType.value = saveType;
	fm.nextScheduleTypeCheck.value = "1";
	// reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
	field.disabled = true;
	if (fm.prpLthirdPartyLicenseNo[1]) {
		fm.prpLthirdPartyLicenseNo[1].disabled = false;
		fm.prpLthirdPartyBrandName[1].disabled = false;
		fm.carKindCode[1].disabled = false;
		fm.licenseColorCode[1].disabled = false;
	}
	// modify by weishixin add end 20040616
	//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 START
	try{
		fm.prpLregistSharingFlag[0].disabled = false;
		fm.prpLregistSharingFlag[1].disabled = false;
	}catch(e){}
	//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 END
	fm.submit();
	return true;
}

function initSet_qs() {
	var qsFlag = fm.qsFlag.value;
	if (qsFlag != null && qsFlag != '' && qsFlag == 'Y') { // 关联则初始化，不关联则不初始化
		var qs_prpLregistStartDate = fm.qs_prpLregistStartDate.value;
		var qs_prpLregistEndDate = fm.qs_prpLregistEndDate.value;
		var qs_prpLregistDamageStartDate = fm.prpLregistDamageStartDate.value;

		var startTenDay = new Date(qs_prpLregistStartDate.substring(0, 4), qs_prpLregistStartDate.substring(5, 7) - 1, qs_prpLregistStartDate.substring(8, 10) - 1);
		var endTenDay = new Date(qs_prpLregistEndDate.substring(0, 4), qs_prpLregistEndDate.substring(5, 7) - 1, qs_prpLregistEndDate.substring(8, 10));
		var DamageStartTen = new Date(qs_prpLregistDamageStartDate.substring(0, 4), qs_prpLregistDamageStartDate.substring(5, 7) - 1, qs_prpLregistDamageStartDate.substring(8, 10));
		var StartTen = (DamageStartTen.getTime() - startTenDay.getTime()) / (24 * 60 * 60 * 1000);
		var EndTen = (endTenDay.getTime() - DamageStartTen.getTime()) / (24 * 60 * 60 * 1000);

		// 判断是否是相同保单号码有1个以上的报案,只在登记的时候提示.
		var registNo = fm.prpLregistRegistNo.value;
		var sameCount = parseInt(fm.qs_PerilCount.value);

		var RecentCount = parseInt(fm.qs_RecentCount.value);
		var RegistViewLimitDay = parseInt(fm.RegistViewLimitDay.value);

		var payFee = parseInt(fm.qs_prpLregistPayFee.value);
		var policyNo = fm.mainPolicyNo.value;


		var errorMessage = "";
		var underWriteEndDate = fm.prpLregistUnderWriteEndDate.value;
		var qs_prpLregistStartDate = fm.qs_prpLregistStartDate.value;
		if (underWriteEndDate > qs_prpLregistStartDate) {
			errorMessage = errorMessage + i18n.regist.strongPolicieSingleBusiness + "\n"; // 此强三保单为倒签单业务！\n
		}

		if (qs_prpLregistDamageStartDate < qs_prpLregistStartDate) {
			errorMessage = errorMessage + i18n.regist.strongCompensatTimeNotInsur + "\n"; // 强三出险时间不在保险期间内！\n
		}
		if (qs_prpLregistDamageStartDate > qs_prpLregistEndDate) {
			errorMessage = errorMessage + i18n.regist.strongCompensatTimeNotInsur + "\n"; // 强三出险时间不在保险期间内！\n
		}
		if (StartTen < 10) {
			errorMessage = errorMessage + i18n.regist.policyComeEffect + StartTen + i18n.regist.nextDayEffect + "\n"; // 保单生效
			// 天後出险！\n
		}
		if (EndTen < 10) {
			errorMessage = errorMessage + i18n.regist.checkTimeDate3 + EndTen + i18n.regist.day + "\n"; // 强三出险时间离止保日期只有
			// 天！\n
		}


		if (registNo.length < 1) {
			// 说明是登记
			if (sameCount > 0) {
				errorMessage = errorMessage + i18n.regist.policyNumber + policyNo + i18n.regist.strongPolicyAlreadyDanger + sameCount + i18n.regist.times; // 保单号码为
				// 的强三保单已经出险
				// 次！
				// \n
				if (RecentCount > 0 && RegistViewLimitDay > 0) {
					errorMessage = errorMessage + i18n.regist.oneRecently + RegistViewLimitDay + i18n.regist.alreadyGetDanger + RecentCount + i18n.regist.times; // 其中最近
					// 天已经出险
					// 次！
					// \n
				}
			} else {
				fm.button_Peril_Open_Context.disabled = true;
			}
		}

		if (payFee == -1) {
			errorMessage = errorMessage + i18n.regist.policyPremiumNotPaid + "\n"; // 此强三保单保费未缴,请慎重处理！！
		} else if (payFee == 0) {
			errorMessage = errorMessage + i18n.regist.policyNotPayFullFee + "\n"; // 此强三保单已缴未缴全,请慎重处理！！！
		}

		if (errorMessage.length > 0) {
			alert(errorMessage);
			return false;
		}

	}

	return true;
}

/**
 * @description 设值页面的一些初始化信息
 * @param 无
 * @return 通过返回true,否则返回false
 */

function initSet() {
	//提示是否在保险期限内，是否距离保单起期或止期很近（10天）
	var checkFlag = fm.checkFlag.value;
	
	var prpLregistStartDate = fm.prpLregistStartDate.value;
	var prpLregistEndDate = fm.prpLregistEndDate.value;
	var prpLregistDamageStartDate = fm.prpLregistDamageStartDate.value;
	var prpLregistReportDate = fm.prpLregistReportDate.value;//备案日期
	var prpLregistInputDate = fm.prpLregistInputDate.value;//输入日期
	var startDate = new Date(prpLregistStartDate.replace(/-/g,"/"));//起保日期
	startDate.setHours(parseInt(fm.prpLregistStartHour.value , 10),0,0);
	var endDate = new Date(prpLregistEndDate.replace(/-/g,"/"));//終保日期
	endDate.setHours(parseInt(fm.prpLregistEndHour.value , 10),0,0);
	var damageStartDate = new Date(prpLregistDamageStartDate.replace(/-/g,"/"));//出險日期
	var damageStartHour  = fm.prpLregistDamageStartHour.value;
	var damageStartMinute  = fm.prpLregistDamageStartMinute.value;
	damageStartDate.setHours(parseInt(damageStartHour , 10),parseInt(damageStartMinute , 10),0);
	var StartTen = (damageStartDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
	var EndTen = (endDate.getTime() - damageStartDate.getTime()) / (24 * 60 * 60 * 1000);
	//備案日期
	var reportDate = new Date(prpLregistReportDate.replace(/-/g,"/"));
	var reportHour  = fm.prpLregistReportHour.value;
	var reportMinute  = fm.prpLregistReportMinute.value;
	reportDate.setHours(parseInt(reportHour , 10),parseInt(reportMinute , 10),0);

	if (fm.buttonThirdPartyDelete[1]) {
		fm.buttonThirdPartyDelete[1].disabled = true;
//		fm.prpLthirdPartyLicenseNo[1].disabled = true;
		// 涉案车辆信息中本保单车辆号牌底色、车辆种类、厂牌型号不允许修改
		fm.prpLthirdPartyBrandName[1].disabled = true;
		fm.carKindCode[1].disabled = true;
		fm.licenseColorCode[1].disabled = true;
	}
	if (fm.buttonDriverDelete[1]) {
		fm.buttonDriverDelete[1].disabled = true;
	}
	if (fm.prpLregistRegistNo.length < 10) {
		fm.messageSave.disabled = true;
		fm.messageView.disabled = true;
	}

	// 判断是否是相同保单号码有1个以上的报案,只在登记的时候提示.

	var registNo = fm.prpLregistRegistNo.value;
	var sameCount = parseInt(fm.PerilCount.value);
	var RecentCount = parseInt(fm.RecentCount.value);
	var RegistViewLimitDay = parseInt(fm.RegistViewLimitDay.value);
	var payFee = parseInt(fm.prpLregistPayFee.value);
	var policyNo = fm.prpLregistPolicyNo.value;


	var errorMessage = "";
	var endorType = fm.endorType.value;
	if ("54" == endorType) {
		message = message + "此保單已停效！\n";
	}
	// 原车险保单为倒签单业务，但进行报案时均无相应的提示，但是其它非车险理赔是有这个提示的，请确认该需求在车险处是否使用。
	var underWriteEndDate = fm.prpLregistUnderWriteEndDate.value;
	
	if (underWriteEndDate > prpLregistStartDate) {
		errorMessage = errorMessage + "此保單為倒簽單業務！\n";
	}
	// reason:200509-lpba-023 提示是否在保险期限内，是否距离保单起期或止期很近（10天）
	if (checkFlag != 0) { // checkFlag = select count(*) from prpPmain where
		errorMessage = errorMessage + "此保單還處在批改狀態 ！\n";
	}
	if (damageStartDate < startDate || damageStartDate > endDate) {
		errorMessage = errorMessage + "出險時間不在保單保險期間內！\n"; //出险时间不在保险期间内！\n
	} else if((endDate.getTime() - startDate.getTime()) >= (30 * 24 * 60 * 60 * 1000)){//保險期間30天以上的增加如下提示
		StartTen = Math.round(StartTen);
		EndTen = Math.round(EndTen);
		if (StartTen < 10) {
			errorMessage = errorMessage + "保單生效 " + (StartTen == 0 ?"當天":(StartTen+"天後")) + " 出險！\n"; //保单生效    天後出险！\n
		}
		if (EndTen < 10) {
			errorMessage = errorMessage + "出險時間距離終保日期只有 " + EndTen + " 天！\n"; //出险时间离止保日期只有    天！\n
		}
	}
	if (registNo.length < 1) {
		// 说明是登记
		if (sameCount > 0) {
			errorMessage = errorMessage + "保單號碼為'" + policyNo + "'已經出險" + sameCount + "次，請查看出險次數信息！ \n";
			if (RecentCount > 0 && RegistViewLimitDay > 0) {
				errorMessage = errorMessage + "其中最近" + RegistViewLimitDay + "天已經出險" + RecentCount + "次！ \n";
			}
		} else {
			fm.button_Peril_Open_Context.disabled = true;
		}
	}

	if (payFee == -1) {
		errorMessage = errorMessage + "此保單保費未繳，請慎重處理！ \n";

	} else if (payFee == 0) {
		errorMessage = errorMessage + "此保單保費未繳全，請慎重處理！ \n";

	}


	if (errorMessage.length > 0) {
		alert(errorMessage);
		return false;
	}


	// add end
	return true;
}


function noChange() {
	fm.insureCarFlag[1].value = 1;
	alert("涉案車輛的第一輛車必須為保單車輛");
	return true;
}


/**
 * 功能：将报案扩展信息变成可编辑 return true/false
 */

function eidtRegistExt(tableId) {
	var testStr = "";
	var elements = getTableElements(tableId);
	for (var i = 0; i < elements.length; i++) {
		if (elements[i].name == "prpLregistExtOperatorCode" || elements[i].name == "prpLregistExtContext") {
			tempElements = elements[i];
			// 将输入域变为只读
			if (tempElements.type == "text" || tempElements.type == "hidden") {
				tempElements.style.fontSize = "11pt";
				tempElements.style.borderTop = "#4196BF 1px solid";
				tempElements.style.borderBottom = "#4196BF 1px solid";
				tempElements.style.borderRight = "#4196BF 1px solid";
				tempElements.style.borderLeft = "#4196BF 1px solid";
				tempElements.style.width = "100%";
				tempElements.style.backgroundColor = "#ffffff";
				tempElements.readOnly = false;
			}
			// 将输入域变为只读
			if (tempElements.type == "radio") {
				tempElements.disabled = false;
			}
		}
	}
}

// Reason:出险原因、事故原因按其使用频率来排序
// 出险原因变化後触发该事件，出险原因名称也跟着改变

function getDamageName(field) {
	var list = field;
	fm.prpLregistDamageName.value = list.options[list.selectedIndex].text;
}

// 事故原因变化後触发该事件，事故原因名称也跟着改变

function getDamageTypeName(field) {
	var list = field;
	fm.prpLregistDamageTypeName.value = list.options[list.selectedIndex].text;
}



/**
 * @description 改变赔偿责任时触发，相应改变责任比例
 * @param 无
 * @return 无
 */

function changeIndemnityDuty() {
	var indemnityDuty = ""; // 设置的值
	var i = 0; // 循环使用

	switch (fm.indemnityDuty.value) {
	case "0": // 全责
		indemnityDuty = "100";
		break;
	case "1": // 主责
		indemnityDuty = "70";
		break;
	case "2": // 同责
		indemnityDuty = "50";
		break;
	case "3": // 次责
		indemnityDuty = "30";
		break;
	case "4": // 无责
		indemnityDuty = "0.0";
		break;
	case "9": // 其它
		indemnityDuty = "0.0";
		break;
	}
	for (var i = 1; i < fm.prpLthirdPartySerialNo.length; i++) {
		fm.prpLthirdPartyDutyPercent[i].value = indemnityDuty;
	}
}

// 报案时在涉案车辆信息中不显示对涉案车辆责任比例

function changeProperties() {}


// 责任比例变化时，改变事故责任预估

function getIndemnityDuty() {}

// 触发该事件时，自动生成出险摘要

function generateRegistText() {
	// 得到驾驶员信息
	var prplDriver = "";
	var prplRegistText = "";
	for (var i = 1; i < fm.prpLdriverSerialNo.length; i++) {
		if (i == 1) {
			prplDriver = trim(fm.prpLdriverDriverName[i].value);
			break;
		}
	}
	// 得到标的车牌信息
	var prplLicenseNo = "";
	for (var i = 1; i < fm.prpLthirdPartySerialNo.length; i++) {
		if (i == 1) {
			prplLicenseNo = trim(fm.prpLthirdPartyLicenseNo[i].value);
			break;
		}
	}
	// 得到时间、地点、事故原因、出险原因
	var prpLregistLinkerName = trim(fm.prpLregistLinkerName.value); // 得到驾驶人
	var prpLregistPhoneNumber = trim(fm.prpLregistPhoneNumber.value); // 得到驾驶人联系电话
	var prpLregistDamageStartDate = trim(fm.prpLregistDamageStartDate.value);
	var prpLregistDamageStartHour = trim(fm.prpLregistDamageStartHour.value);
	var prpLregistDamageStartMinute = trim(fm.prpLregistDamageStartMinute.value);
	var prpLregistDamageAddress = trim(fm.prpLregistDamageAddress.value);
	var prpLregistDamageName = "";
	if (fm.registType.value == '1') { //强制险的取强制险出险原因
		prpLregistDamageName = fm.prpLregistDamageNameBZ.value;
	} else {
		fm.prpLregistDamageName.value
	}
	if (prpLregistLinkerName == "" || prpLregistLinkerName == null) {
		prpLregistLinkerName = "駕駛人";
	}
	if (trim(fm.prpLregistDriverMobile.value) != "") { //联系方式默认取手机
		prpLregistPhoneNumber = trim(fm.prpLregistDriverMobile.value);
	}
	if (prpLregistPhoneNumber == "" || prpLregistLinkerName == null) {
		prpLregistPhoneNumber = "聯係電話";
	}
	var year = parseInt(prpLregistDamageStartDate) - 1911;
	var date = prpLregistDamageStartDate.substring(4, prpLregistDamageStartDate.length);
	var prplRegistText1 = year + date + "日" + prpLregistDamageStartHour + "時" + prpLregistDamageStartMinute + "分由" + prpLregistLinkerName + "(" + prpLregistPhoneNumber + ")駕駛";

	// 得到标的车与三者车损失信息
	var count = getElementCount("prpLthirdPartySerialNo"); // 受损车数量
	var serialNoCount = getElementCount("RelateSerialNo"); // 受损部位数量，没有受损部位为1
	var lossMessage1 = ""; // 标的车信息
	var lossMessage2 = ""; // 三者车信息
	var lossMessage3 = ""; // 总的出险摘要
	var lossMessageTemp = ""; // 三者受损部位信息

	for (var j = 1; j < count; j++) {
		if (serialNoCount == 1) { // 没有受损部位
			if (j < (count - 1)) { // 除去标的车
				lossMessage2 = lossMessage2 + fm.prpLthirdPartyLicenseNo[j + 1].value + "受損;"
			}
		} else { // 有受损部位
			lossMessageTemp = "";
			for (var k = 0; k < serialNoCount; k++) {
				if (fm.RelateSerialNo[k].value == "1" && j == 1) { // 标的车有受损部位
					lossMessage1 = lossMessage1 + fm.partName[k].value + fm.compName[k].value + "、";
				} else { // 三者车
					if (fm.RelateSerialNo[k].value == j) {
						lossMessageTemp = lossMessageTemp + fm.partName[k].value + fm.compName[k].value + "、";
					}
				}
			} // endfor
			if (j > 1) { // 三者车
				var position2 = lossMessageTemp.lastIndexOf("、");
				lossMessageTemp = lossMessageTemp.substring(0, position2);
				lossMessage2 = lossMessage2 + trim(fm.prpLthirdPartyLicenseNo[j].value) + lossMessageTemp + "受損;";
			}
		} // endelse
	} // endfor

	var position1 = lossMessage1.lastIndexOf("、");
	lossMessage1 = lossMessage1.substring(0, position1);
	lossMessage3 = prplLicenseNo + lossMessage1 + "號車於" + prpLregistDamageAddress + "發生" + prpLregistDamageName + "事故";

	// 得到处理部门信息
	var HandleUnitName = "";
	HandleUnitName = trim(fm.prpLregistHandleUnitName.value);

	// 得到人伤信息(需求不明确,生成规则可能以後还需改动)
	var personCount = getElementCount("prpLpersonTracePersonNo");
	var personMessage = "";
	var personMessageTemp = "";
	for (var j = 1; j < personCount; j++) {
		personMessageTemp = fm.prpLpersonTracePersonName[j].value + "涉及險種爲" + fm.prpLpersonTraceReferKind[j].value + "受傷部位:" + fm.prpLpersonTracePartDesc[j].value + ";";
		personMessage = personMessage + personMessageTemp;
	}
	var personLossFlag = trim(fm.personLossFlag.value);
	var personFlag = "";
	if (personLossFlag == 1) {
		personFlag = ",有人傷";
	}
	// 得到其它损失信息(需求不明确,生成规则可能以後还需改动)
	var propCount = getElementCount("prpLthirdPropItemNo");
	var propMessage = "";
	var propMessageTemp = "";
	for (var j = 1; j < propCount; j++) {
		propMessageTemp = fm.prpLthirdLossItemName[j].value + "受損(" + fm.prpLthirdPropLossDesc[j].value + ");";
		propMessage = propMessage + propMessageTemp;
	}
	var prpLregistthirdLicenseNo = trim(fm.prpLregistthirdLicenseNo.value);
	// 拼串得到出险摘要
	prplRegistText = "     " + prplRegistText1 + lossMessage3 + personMessage + propMessage + personFlag;
	if (prpLregistthirdLicenseNo != "") {
		prplRegistText += ",三者車：" + prpLregistthirdLicenseNo
	}
	var prpLregistRemark = trim(fm.prpLregistRemark.value);
	if (prpLregistRemark != "") {
		prplRegistText += "\n" + "     " + "備註：" + prpLregistRemark
	}
	fm.prpLregistTextContextInnerHTML.value = prplRegistText;
	return true;
}


function submittime() {
	return true;
}

/***
 * 判斷備案時間是否超過出險時間48小時
 * true 超出 false 未超出
 * @returns {Boolean}
 */
function confirmOverTime() {
	var prpLregistDamageStartDate = fm.prpLregistDamageStartDate.value;
	if(prpLregistDamageStartDate.length == 0 ){
		alert("請輸入出險日期！");
		return false;
	}
	var prpLregistReportDate = fm.prpLregistReportDate.value;//备案日期
	if(prpLregistReportDate.length == 0 ){
		alert("備案日期不能為空！");
		return false;
	}
	var damageStartDate = new Date(prpLregistDamageStartDate.replace(/-/g,"/"));//出險日期
	var damageStartHour  = fm.prpLregistDamageStartHour.value;
	var damageStartMinute  = fm.prpLregistDamageStartMinute.value;
	damageStartDate.setHours(parseInt(damageStartHour , 10),parseInt(damageStartMinute , 10),0);
	//備案日期
	var reportDate = new Date(prpLregistReportDate.replace(/-/g,"/"));
	var reportHour  = fm.prpLregistReportHour.value;
	var reportMinute  = fm.prpLregistReportMinute.value;
	reportDate.setHours(parseInt(reportHour , 10),parseInt(reportMinute , 10),0);
	return (reportDate.getTime() - damageStartDate.getTime()) > ( 48 * 60 * 60 * 1000 );
}

function locateMpcPage() {
	for (var i = 0; i < 4; i++) {
		coMPC.selectedIndex = i + 1;
		coMPC.DoPropChange("selectedIndex");
	}
	coMPC.selectedIndex = "1";
	coMPC.DoPropChange("selectedIndex");
}
/**
 * @description 检查报案时所有车辆輸入责任比例之和小於100%
 * @param 无
 * @return 通过返回true,否则返回false
 */

function checkPrpLthirdPartyDutyPercent() {
	var thirdPartyDutyPercent = 0;
	for (var i = 1; i < fm.prpLthirdPartyDutyPercent.length; i++) {
		if (fm.prpLthirdPartyDutyPercent[i].value != null && fm.prpLthirdPartyDutyPercent[i].value != 0) {
			thirdPartyDutyPercent = thirdPartyDutyPercent + parseInt(fm.prpLthirdPartyDutyPercent[i].value);
		}
	}
	if (thirdPartyDutyPercent > 100) {
		alert("所有車輛責任比例之和大於100，請重新輸入");
		return false;
	} else {
		return true;
	}
}
//在是否有保強制險中選擇 否 時，被保險人身分應主動帶入： 
//0-未投保車輛,且不需填列/檢核(承保公司代號)及(強制保險證編號),任意險亦是如此

function setInsuredIdentity(field) {
	var index = -1;
	if (field.value != '0') {
		return true;
	}
	var $isInsurance = $(":input[name='prpLthirdPartyIsInsurance']");
	$isInsurance.each(function (i) {
		if (this == field) {
			index = i;
			return false;
		}
	});
	if (index > -1) {
		var $insuredIdentity = $(":input[name='prpLthirdPartyInsuredIdentity']");
		$($insuredIdentity[index]).val("0");
	}
}

function changeDriverInfo() {
	var prpLregistLinkerName = document.getElementById("prpLregistLinkerName");
	var prpLregistPhoneNumber = document.getElementById("prpLregistPhoneNumber");
	var prpLregistDriverMobile = document.getElementById("prpLregistDriverMobile");
	if (prpLregistLinkerName != null) {
		fm.prpLdriverDriverName[1].value = prpLregistLinkerName.value;
	}
	if (prpLregistPhoneNumber != null) {
		fm.prpLdriverDriverPhone[1].value = prpLregistPhoneNumber.value;
	}
	if (prpLregistDriverMobile != null) {
		fm.prpLdriverMobilePhone[1].value = prpLregistDriverMobile.value;
	}
}

//mantis： CLM0120 ，處理人員：DP0728 蘇英碩，需求單編號：CLM0120.新核心-強制證號長度管控 Start
//mantis：CLM0204，處理人員：CE046，需求單編號：新核心-第三方強制證號規則調整 START
//mantis：CLM0228，處理人員：CE046，需求單編號：新核心-第三方強制證號規則類別98 99修正  START
function checkCINo(){
	var InsuranceNoList = document.getElementsByName("prpLthirdPartyInsuranceNo");
	var InsuranceNoCount = InsuranceNoList.length;
	for (var i = 2; i < InsuranceNoCount; i++) {
		if(document.getElementsByName("prpLthirdPartyInsuranceNo")[i] != undefined){
			var prpLthirdPartyInsuranceNo = trim(document.getElementsByName("prpLthirdPartyInsuranceNo")[i].value);
			var company = document.getElementsByName("prpLthirdPartyInsureComCode")[i].value;
			var carType = document.getElementsByName("carKindCode")[i].value;
			var isInsurance = document.getElementsByName("prpLthirdPartyIsInsurance")[i].value;
			if(company != '32' && company != '99' && isInsurance == 1 && carType != 98 && carType != 99 && company!=null && prpLthirdPartyInsuranceNo.length > 0){
				if(company == '01'){//台產    (tf)
					if(company != prpLthirdPartyInsuranceNo.substring(0,2)){
						alert("承保公司代號選擇錯誤");
						return false;
					}else{
						if(prpLthirdPartyInsuranceNo.length != 13){	
							alert("第三方車輛強制保險證編號長度不符");
							return false;
						}
					}

				}else if(company == '02'){//兆豐    (ck)
					if(company != prpLthirdPartyInsuranceNo.substring(0,2)){
							alert("承保公司代號選擇錯誤");
							return false;
					}else{
						if(prpLthirdPartyInsuranceNo.length != 13){
							alert("第三方車輛強制保險證編號長度不符");
							return false;
						}
					}

				}else if(company == '03'){//華山
					if(company != prpLthirdPartyInsuranceNo.substring(0,2)){
							alert("承保公司代號選擇錯誤");
							return false;
					}else{
						if(prpLthirdPartyInsuranceNo.length != 14){
							alert("第三方車輛強制保險證編號長度不符");
							return false;
						}
					}

				}else if(company == '05'){//富邦    (fb) 汽車10、12、16碼，機車15、16及10碼
					if(company != prpLthirdPartyInsuranceNo.substring(0,2)){
							alert("承保公司代號選擇錯誤");
							return false;
					}else{
						if(prpLthirdPartyInsuranceNo.length < 10 || prpLthirdPartyInsuranceNo.length > 16){
							alert("第三方車輛強制保險證編號長度不符");
							return false;
						}
					}

				}else if(company == '06'){//蘇黎世  (mo)
					if(company != prpLthirdPartyInsuranceNo.substring(0,2)){
							alert("承保公司代號選擇錯誤");
							return false;
					}else{
						//mantis：CLM0284 ，處理人員： DP0713 ，需求單編號：第三方車輛各承保公司判斷強制證號碼數調整(06蘇黎世-和泰)須為15碼)
						//mantis：CLM0291 ，處理人員： DP0713 ，需求單編號：第三方車輛各承保公司判斷強制證號碼數調整06-和泰小於等於15碼
						if(prpLthirdPartyInsuranceNo.length > 15){
							alert("第三方車輛強制保險證編號長度不符");
							return false;
						}
					}

				}else if(company == '07'){//泰安    (ta)
					if(company != prpLthirdPartyInsuranceNo.substring(0,2)){
							alert("承保公司代號選擇錯誤");
							return false;
					}else{
						if(prpLthirdPartyInsuranceNo.length != 13){
							alert("第三方車輛強制保險證編號長度不符");
							return false;
						}
					}

				}else if(company == '08'){//明台    (mt)
					if(company != prpLthirdPartyInsuranceNo.substring(0,2)){
							alert("承保公司代號選擇錯誤");
							return false;
					}else{
						if(prpLthirdPartyInsuranceNo.length != 12){
							alert("第三方車輛強制保險證編號長度不符");
							return false;
						}
					}

				}else if(company == '09'){//南山    (ci)
					if(company != prpLthirdPartyInsuranceNo.substring(0,2)){
							alert("承保公司代號選擇錯誤");
							return false;
					}else{
						if(prpLthirdPartyInsuranceNo.length <= 11||prpLthirdPartyInsuranceNo.length>13){
							alert("第三方車輛強制保險證編號長度不符");
							return false;
						}
					}

				}else if(company == '10'){//第一    (fi)
					if(company != prpLthirdPartyInsuranceNo.substring(0,2)){
							alert("承保公司代號選擇錯誤");
							return false;
					}else{
						if(prpLthirdPartyInsuranceNo.length != 13){
							alert("第三方車輛強制保險證編號長度不符");
							return false;
						}
					}

				}else if(company == '12'){//旺旺友聯(un)
					if(company != prpLthirdPartyInsuranceNo.substring(0,2)){
							alert("承保公司代號選擇錯誤");
							return false;
					}else{
						if(prpLthirdPartyInsuranceNo.length != 13){
							alert("第三方車輛強制保險證編號長度不符");
							return false;
						}
					}

				}else if(company == '13'){//新光    (sk)
					if(company != prpLthirdPartyInsuranceNo.substring(0,2)){
							alert("承保公司代號選擇錯誤");
							return false;
					}else{
						if(prpLthirdPartyInsuranceNo.length < 10 || prpLthirdPartyInsuranceNo.length > 11){
							alert("第三方車輛強制保險證編號長度不符");
							return false;
						}
					}

				}else if(company == '14'){//華南    (hn)
					if(company != prpLthirdPartyInsuranceNo.substring(0,2)){
							alert("承保公司代號選擇錯誤");
							return false;
					}else{
						if(prpLthirdPartyInsuranceNo.length != 14){
							alert("第三方車輛強制保險證編號長度不符");
							return false;
						}
					}

				}else if(company == '15'){//國泰世紀(dt)
					if(company != prpLthirdPartyInsuranceNo.substring(0,2)){
							alert("承保公司代號選擇錯誤");
							return false;
					}else{
						if(prpLthirdPartyInsuranceNo.length != 13){
							alert("第三方車輛強制保險證編號長度不符");
							return false;
						}
					}

				}else if(company == '17'){//新安東京(nw)
					if(company != prpLthirdPartyInsuranceNo.substring(0,2)){
							alert("承保公司代號選擇錯誤");
							return false;
					}else{
						if(prpLthirdPartyInsuranceNo.length != 12){
							alert("第三方車輛強制保險證編號長度不符");
							return false;
						}
					}

				}else if(company == '18'){//台壽保  (dg)
					if(company != prpLthirdPartyInsuranceNo.substring(0,2)){
							alert("承保公司代號選擇錯誤");
							return false;
					}else{
						if(prpLthirdPartyInsuranceNo.length != 13){
							alert("第三方車輛強制保險證編號長度不符");
							return false;
						}
					}

				}else if(company == '31'){//美商環球
					if(company != prpLthirdPartyInsuranceNo.substring(0,2)){
							alert("承保公司代號選擇錯誤");
							return false;
					}else{
						if(prpLthirdPartyInsuranceNo.length < 13){
							alert("第三方車輛強制保險證編號長度不符");
							return false;
						}
					}

				}else if(company == '44'){//日商三井住友
					if(prpLthirdPartyInsuranceNo.substr(6, 4) == 'CT'){
						if (company != prpLthirdPartyInsuranceNo.substring(0,2)){//第5、6碼為'CT'者(保代車)12碼，其他15碼
								alert("承保公司代號選擇錯誤");
								return false;
						}else{
							if(prpLthirdPartyInsuranceNo.length != 12){ 
								alert("第三方車輛強制保險證編號長度不符");
								return false;
							}
						}	

					}else{
						if (company != prpLthirdPartyInsuranceNo.substring(0,2)){//第5、6碼為'CT'者(保代車)12碼，其他15碼
								alert("承保公司代號選擇錯誤");
								return false;
						}else{
							if(prpLthirdPartyInsuranceNo.length != 15){
								alert("第三方車輛強制保險證編號長度不符");
								return false;}
							}
						}

					}
				}else{
					if(isInsurance == 1 && company != '32' && company != '99' && carType != 98 && carType != 99){
						alert("請先選擇承保公司代號");
						return false;}
					
					else if (company == '32' && isInsurance == 1 && prpLthirdPartyInsuranceNo.length > 0){
						alert("承保公司代號32不可有強制險證號");
						return false;
					}
					else if (company == '99' && isInsurance == 1 && prpLthirdPartyInsuranceNo.length > 0){
						alert("承保公司代號99不可有強制險證號");
						return false;
					}
				}
			}else{
				if(undefined != prpLthirdPartyInsuranceNo && null != prpLthirdPartyInsuranceNo){
					alert("第三方車輛強制保險證編號不可為空值");
				}
			}	
		if (document.getElementsByName("prpLthirdPartyInsureComCode")[i].value == '32' && document.getElementsByName("prpLthirdPartyIsInsurance")[i].value == 1){
			alert("承保公司代號32不可有保強制險");
			return false;}
		else if (document.getElementsByName("prpLthirdPartyInsureComCode")[i].value == '99' && document.getElementsByName("prpLthirdPartyIsInsurance")[i].value == 1){
			alert("承保公司代號99不可有保強制險");
			return false;}
	}
	//mantis：CLM0291 ，處理人員： DP0713 ，需求單編號：第三方車輛各承保公司判斷強制證號碼數調整06-和泰小於等於15碼
	return true;
}
//mantis：CLM0228，處理人員：CE046，需求單編號：新核心-第三方強制證號規則類別98 99修正  End
//mantis：CLM0204，處理人員：CE046，需求單編號：新核心-第三方強制證號規則調整 END
//mantis： CLM0120 ，處理人員：DP0728 蘇英碩，需求單編號：CLM0120.新核心-強制證號長度管控 End

//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 START
var pre_registTextContext ="";
function compulsoryCaseQuery310(){
	var idNumber="";
	var idNumberType="";
	for (var i = 1; i < fm.prpLpersonTraceIdNumber.length; i++) {
		if (fm.prpLpersonTraceIdNumber[i].value != '') {
			idNumber+=","+fm.prpLpersonTraceIdNumber[i].value;
			idNumberType+=","+fm.prpLpersonTraceIdNumberType[i].value;
		}
	}
    var result=true;
    //mantis：CLM0257，處理人員：DP0713，需求單編號：新核心-備案任務處理，新增[是否為強制險區塊鏈攤賠案件]選項 START
    if(undefined!=fm.prpLregistIsCompulsoryBchainClaim && "undefined"!=fm.prpLregistIsCompulsoryBchainClaim
    		&& fm.prpLregistIsCompulsoryBchainClaim.length>0){
    	if("Y"==fm.prpLregistIsCompulsoryBchainClaim[1].value 
    		&& fm.prpLregistIsCompulsoryBchainClaim[1].checked == true){
    		return true;
    	}
    }
    //mantis：CLM0257，處理人員：DP0713，需求單編號：新核心-備案任務處理，新增[是否為強制險區塊鏈攤賠案件]選項 END
    //mantis：CLM0209，處理人員：DP0713，需求單編號：新核心-立案節點同步備案人傷訊息更新區塊鏈資料 START
    var damageStartDate = undefined!=fm.prpLregistDamageStartDate?fm.prpLregistDamageStartDate.value:fm.prpLclaimDamageStartDate.value;
    var damageStartHour = undefined!=fm.prpLregistDamageStartHour?fm.prpLregistDamageStartHour.value:fm.prpLclaimDamageStartHour.value;
    var damageStartMinute = undefined!=fm.prpLregistDamageStartMinute?fm.prpLregistDamageStartMinute.value:fm.prpLclaimDamageStartMinute.value;
	var jsonData= (function(){
		$.ajax({
			url:contextRootPath + "/webservice/compulsoryCaseQuery310.do?"+
			"idNumber="+idNumber+
			"&idNumberType="+idNumberType+
			"&prpLregistDamageStartDate="+damageStartDate+
			"&prpLregistDamageStartHour="+damageStartHour+
			"&prpLregistDamageStartMinute="+damageStartMinute
			,async : false,
				cache : false,
				dataType: "json",
				contentType: "application/json; charset=utf-8",
				success: 
			function (data) {
				result = false;
				var success=false;
				if(data.result != null && data.result != ''){
					var rtn = data.result.split("|");
					if(rtn[0] == '1'){
						success = true;
					}
					//0181 devMode START
					if(rtn[0] == '-1'){//僅有測試區才會出現的判斷
						//避免備案(備案不擋)送出後區塊鏈異常 且在立案被送出所已清空ID讓項目被擋在立案 使得USER比須回到備案修正
						for (var i = 1; i < fm.prpLpersonTraceIdNumber.length; i++) {
							fm.prpLpersonTraceIdNumber[i].value = "";
						}
						if(confirm("測試環境!區塊鏈系統異常，是否跳過區塊鏈檢驗!!")){
							success = true;
						}else{
							return false;
						}
						
					}
					//0181 devMode END
					data.result = data.result.substring(2,data.result.length);//除去前兩碼判定碼(0|...)
				}else{
					//避免備案(備案不擋)送出後區塊鏈異常 且在立案被送出所已清空ID讓項目被擋在立案 使得USER比須回到備案修正
					for (var i = 1; i < fm.prpLpersonTraceIdNumber.length; i++) {
						fm.prpLpersonTraceIdNumber[i].value = "";
					}
					alert("區塊鏈系統異常，請確認系統無誤後再行提交案件。");
					return false;
				}
				if(pre_registTextContext!=""){
					//寫回
					if(null!=fm.prpLregistTextContextInnerHTML && undefined!=fm.prpLregistTextContextInnerHTML){
						fm.prpLregistTextContextInnerHTML.value=fm.prpLregistTextContextInnerHTML.value.replace(pre_registTextContext,"");
					}else if(null!=fm.prpLltextContextInnerHTML && undefined!=fm.prpLltextContextInnerHTML){
						fm.prpLltextContextInnerHTML.value=fm.prpLltextContextInnerHTML.value.replace(pre_registTextContext,"");
					}
					//清除
					pre_registTextContext = "";
				}
				pre_registTextContext = data.result!=undefined?data.result:"";
				//寫入
				if(null!=fm.prpLregistTextContextInnerHTML && undefined!=fm.prpLregistTextContextInnerHTML){
					fm.prpLregistTextContextInnerHTML.value=fm.prpLregistTextContextInnerHTML.value+(data.result!=undefined?data.result:"");
				}else if(null!=fm.prpLltextContextInnerHTML && undefined!=fm.prpLltextContextInnerHTML){
					fm.prpLltextContextInnerHTML.value=fm.prpLltextContextInnerHTML.value+(data.result!=undefined?data.result:"");
				}
				
				if (!success) {
					result = false;
					$("#prpLregistSharingFlag1").attr('checked', true);
					
					alert("該案件同業已受理，請至區塊鏈平台確認。");
				} else{
					result = true;//通過可繼續
					$("#prpLregistSharingFlag0").attr('checked', true);
				}
			}
		});
		////mantis：CLM0209，處理人員：DP0713，需求單編號：新核心-立案節點同步備案人傷訊息更新區塊鏈資料 END
		//alert("rtn:"+result);
	    return result;
	})();
	//alert("jsonData:"+jsonData);
	return jsonData;
}
//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 END