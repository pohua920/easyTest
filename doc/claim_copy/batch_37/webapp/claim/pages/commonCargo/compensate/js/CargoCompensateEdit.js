/*****************************************************************************
 * DESC       ：实赔的脚本函数页面
 * AUTHOR     ：liubvo
 * CREATEDATE ： 2004-05-19
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/
//add by wangliguang begin
/**
 *@description 费用相关域整理
 *@param       无
 *@return      无
 */
function setInput(field) {
	disablebutton();
	var i = getElementOrder(field) - 1;
//	if (fm.all("prpLchargeChargeCode")[i].value == "03" || fm.all("prpLchargeChargeName")[i] == "施救费") {
//		fm.all("prpLchargeSumRealPay")[i].value = fm.all("prpLchargeChargeReport")[i].value;
//		fm.prpLchargeChargeAmount[i].value = 0;
//	} else 
	if ((fm.all("prpLchargeChargeCode")[i].value.length > 0) && (fm.all("prpLchargeChargeName")[i].value.length > 0)) {
		fm.prpLchargeSumRealPay[i].value = 0;
		fm.prpLchargeChargeAmount[i].value = fm.all("prpLchargeChargeReport")[i].value;
	} else if ((fm.all("prpLchargeChargeCode")[i].value.length <= 0) || (fm.all("prpLchargeChargeName")[i].value.length <= 0)) {
		fm.prpLchargeSumRealPay[i].value = 0;
		fm.prpLchargeChargeAmount[i].value = 0;
		fm.prpLchargeChargeReport[i].value = 0;
	}
	calFund(field);
}
//add by wangliguang end
/**
 *@description 检查报案登记
 *@param       无
 *@return      通过返回true,否则返回false
 */
function checkForm() {
	////检查登记主表输入
	//if(checkRegistMain()==false)
	//{
	//	return false;
	//}
	////检查三者车辆输入
	//if(checkThirdParty()==false)
	//{
	//	return false;
	//}
	////检查驾驶员输入
	//if(checkDriver()==false)
	//{
	//  return false;
	//}
	////检查文本输入
	//if(checkRegistText()==false)
	//{
	//  return false;
	//}

	return true;
}

/**
 *@description 设值页面的一些初始化信息
 *@param       无
 *@return      通过返回true,否则返回false
 */
function initSet() {
	//add by zhangyurui 2009-09-21 增加收到客户索赔申请已过天数提示 begin】
	var passDayList = document.getElementsByName("passDay");
	if (passDayList.length > 0 && passDayList[0] != null && passDayList[0].value != 0) {
		alert(i18n.compensate.customerReceived + passDayList[0].value + i18n.compensate.quicklyPossible); //收到客户索赔申请已过    //天，请尽快处理！
	}
	//add by zhangyurui 2009-09-21 增加收到客户索赔申请已过天数提示 end】
	var chiefflag = fm.chiefflag.value;
	var coinsFlag = fm.coinsFlag.value;
	//add by qingyongli start 2005-7-31 股东业务提示
	var message = "";
	var shareHolderFlag = fm.shareHolderFlag.value;
	//add by hanliang end  2005-12-22
	//var payFee = parseInt(fm.prpLregistPayFee.value);
	//if(payFee==-1){
	//alert("此保单保费未缴！");
	//} 
	//else if(payFee==0){
	//alert("此保单保费未缴全！");
	//}
	if (coinsFlag == 1) {
		alert(i18n.compensate.mainPolicy); //本保单为主共保单,请注意生成联共保分摊信息!\n注意輸入损失时请輸入总共的损失！
	}
	if (coinsFlag == 2) {
		alert(i18n.compensate.childPolicy); //本保单为从共保单,请注意生成联共保分摊信息!\n注意輸入损失时请輸入总共的损失！
	}
	if (coinsFlag == 3) {
		alert(i18n.compensate.childShare); //本保单为从联保单,请注意生成联共保分摊信息!\n注意輸入损失时请輸入总共的损失！
	}
	var payFee = parseInt(fm.payFee.value);
	var delinquentfeeCase = fm.delinquentfeeCase.value;
	if (payFee == -1) {
		alert(i18n.claim.premiumNotPaid); //此保单保费未缴！\n
	} else if (payFee == 0) {
		alert(i18n.claim.premiumNotComplete); //此保单保费未缴全！\n
	} else if (payFee == -2) {
		message = message + i18n.certainLoss.policyPremiumPay; //此保单已缴未缴全,请慎重处理！！！ \n
		message = message + delinquentfeeCase + "\n";
	}
	if (message.length > 0) {
		alert(message);
	}
	//add by qinyongli end  2005-7-31

	//   add by wzy 20090410  start 
	//reasion:理算初始化时，签单币别不是CNY时，弹出当前兑换率
	if (fm.BaseCurrency1.value != CURRENCYINFO.LOCAL_CURRENCY) {
		alert(i18n.claim.singleCurrency + fm.BaseCurrency1.value + i18n.claim.useSparingly + fm.ExchRate1.value); //此案件签单币别为    //，不是CNY，请慎重处理！\n当前兑换率为
	}
	//   add by wzy 20090410  end 
	return true;
}

function GenerateCtextFlag(flag) {
	fm.GenerateCompensateFlag.value = flag;
	/*
  var span1 = eval("spanPerson");
  var span2 = eval("spanCharge");
  var span3 = eval("spanlLoss");
  span1.style.display="none";
  span2.style.display="none";
  span3.style.display="none";
  span1.src="/claim/images/butCollapseBlue.gif";
  span2.src="/claim/images/butCollapseBlue.gif";
  span3.src="/claim/images/butCollapseBlue.gif";
  */
}
/**
 *@description 提交
 *@param       无
 *@return      通过返回true,否则返回false
 */
function submitForm() {
	fm.buttonApprove.disabled = true;
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

function setChangelossChargeFlag() {
	//目前只为了联共保判断而增加的,表示变化没有被联共保操作过
	if (fm.all("lossOrChargeHaveChanged") != null) {
		fm.lossOrChargeHaveChanged.value = '1';
	}
}


/**
 * mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核
 * @returns {Boolean}
 */
function checkPayObjectInfo(){
	var checkFlag = true;
	$("input[name='prpLchargeAreaCode']").each(function (i) {
		var areaCode = $(this).val(); //郵遞區號
		var oAreaCode2 = trim(areaCode);
		var areaCode2 = trim(areaCode).replace(/[^\d]/g,'');
		if(oAreaCode2 != areaCode2){
			alert("第 " + (i) + " 條賠款費用資訊‘郵遞區號’ 只能輸入數值! ");
			checkFlag = false;
			return false; 
		}
    });
	if(checkFlag){
		$("[name='PrpLpayObjectInfo']").find("tr[name='AreaInfo']").each(function (i) {
			var areaCode = $(this).find(":input[name='prpLpayObjectInfoAreaCode']").val(); //郵遞區號
			var oAreaCode2 = trim(areaCode);
			var areaCode2 = trim(areaCode).replace(/[^\d]/g,'');
			if(oAreaCode2 != areaCode2){
				alert("賠付對象" + (i) + " 費用資訊‘郵遞區號’ 只能輸入數值! ");
				checkFlag = false;
				return false; 
			}
	    });
	}
	return checkFlag;
}

/**
 *@description 根据按钮状态保存报案数据
 *@param       this
 *@param       保存状态
 *@return      通过返回true,否则返回false
 */
function saveForm(field, saveType) {
	//mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核  START
	if (saveType == "4" && checkPayObjectInfo() == false) {
		return false;
	}
	//mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 END
	//add by liuwei 2010-12-13 如果结案类型是拒赔，则不得赔付标的损失赔偿（可以赔付赔款费用）begin
	if ("2" == fm.prpLcompensateFinallyFlag.value) {
		if (fm.prpLcompensateSumThisPaid.value > 0) {
			alert(i18n.compensate.notPay); //结案类型是拒赔，不得赔付标的损失赔偿（可以赔付赔款费用）!
			return false;
		}
	}
	//add by liuwei 2010-12-13 如果结案类型是拒赔，则不得赔付标的损失赔偿（可以赔付赔款费用）end
	//add by luochang at 2010-10-08 提交时对送审进行判断
	if (saveType == "4" && checkUndwrt() == false) {
		return false;
	}

	var errorMessage = "";
	fm.buttonSaveType.value = saveType;
	//add by zhangyurui 2009-04-02 增加对险别不能为空控制 begin
	var prpLchargeKindCodeList = document.getElementsByName("prpLchargeKindCode");
	var prpLchargeKindNameList = document.getElementsByName("prpLchargeKindName");
	var prpLlossDtoKindCodeList = document.getElementsByName("prpLlossDtoKindCode");
	var prpLlossDtoKindNameList = document.getElementsByName("prpLlossDtoKindName");
	for (var i = 1; i < prpLchargeKindCodeList.length; i++) {
		if (prpLchargeKindCodeList[i].value == "" || prpLchargeKindNameList[i].value == "") {
			errorMessage = errorMessage + i18n.compensate.riskNotEmpty; //险别信息不能为空！\n
		}
	}
	for (var i = 1; i < prpLlossDtoKindCodeList.length; i++) {
		if (prpLlossDtoKindCodeList[i].value == "" || prpLlossDtoKindNameList[i].value == "") {
			errorMessage = errorMessage + i18n.compensate.riskNotEmpty; //险别信息不能为空！\n
		}
	}
	//add by zhangyurui 2009-04-02 增加对险别不能为空控制 end
	//add by luochang 2010-05-31 当支付方式为汇款时，帳户信息不能为空控制 begin
//	var prpLchargeAccountCodeList = document.getElementsByName("prpLchargeAccountCode");
//	var prpLchargeOwnerNameCQ = document.getElementsByName("prpLchargeOwnerNameCQ");
//	var prpLchargeOwnerShipList = document.getElementsByName("prpLchargeOwnerShip");
//	for (var i = 1; i < prpLchargeAccountCodeList.length; i++) {
//		if (prpLchargeOwnerShipList[i].value == "B" && prpLchargeAccountCodeList[i].value == "") {
//			errorMessage = errorMessage + i18n.compensate.paymentnotNotEmpty; //当费用支付方式为汇款时，对应的支付帳户信息不允许为空！\n
//		} else if (prpLchargeOwnerShipList[i].value != "B" && prpLchargeOwnerNameCQ[i].value == "") {
//			errorMessage = errorMessage + i18n.compensate.paymentCash; //当费用支付方式为现金或支票时，对应的支付对象姓名不允许为空！\n
//		}
//	}
//	var prpLCompensateOwnership = fm.prpLCompensateOwnership.value;
//	var prpLCompensateAccountCode = fm.prpLCompensateAccountCode.value;
//	var prpLCompensateOwnerNameCQ = fm.prpLCompensateOwnerNameCQ.value;
//	if (prpLCompensateOwnership == "B" && prpLCompensateAccountCode == "") {
//		errorMessage = errorMessage + i18n.compensate.paymentAccountInfoNotEmpt+"\n"; //当标的损失赔款支付方式为汇款时，对应的支付帳户信息不允许为空！\n
//	} else if (prpLCompensateOwnership != "B" && prpLCompensateOwnerNameCQ == "") {
//		errorMessage = errorMessage + i18n.compensate.paymentAccountNameNotEmpt+"\n"; //当标的损失赔款支付方式为现金或支票时，对应的支付对象姓名不允许为空！\n
//	}
	//add by luochang 2010-05-31 当支付方式为汇款时，帳户信息不能为空控制 end

	//add by caozhigang 2010-01-22 如果輸入了一级巨灾代码，那么二级代码也必须輸入 begin
//	var prpCatastropheCode1 = fm.prpCatastropheCode1.value;
//	var prpCatastropheName1 = fm.prpCatastropheName1.value;
//	var prpCatastropheCode2 = fm.prpCatastropheCode2.value;
//	var prpCatastropheName2 = fm.prpCatastropheName2.value;
//	if (!((prpCatastropheCode1.length > 0 && prpCatastropheName1.length > 0 && prpCatastropheCode2.length > 0 && prpCatastropheName2.length > 0) //一级二级都輸入
//	|| (prpCatastropheCode1.length < 1 && prpCatastropheCode2.length < 1 && prpCatastropheName1.length < 1 && prpCatastropheName2.length < 1))) //一级二级都不輸入
//	{
//		errorMessage = errorMessage + i18n.certify.selectOther; //如果要輸入巨灾代码，请将一级代码和二级代码都录完整，没有二级代码的，请选择“其他”\n
//	}
//	if (prpCatastropheCode2.length > 0 && prpCatastropheCode1) {
//		if (prpCatastropheCode2.length < 12) {
//			if (prpCatastropheCode2.length == 7 && prpCatastropheCode2.substring(0, 2) == prpCatastropheCode1) {
//				//允许选择“其他”
//			} else {
//				errorMessage = errorMessage + i18n.certify.accidentCases; //今年出险的案件，不能选择以前的巨灾代码！
//				//以前的编码不规范，只能模糊提示
//			}
//		} else {
//			var year = prpCatastropheCode2.substring(0, 4);
//			var prpLcompensateDamageStartDate = fm.prpLcompensateDamageStartDate.value.substring(0, 4);
//			if (year != prpLcompensateDamageStartDate) {
//				errorMessage = errorMessage + prpLcompensateDamageStartDate + i18n.compensate.select + year + i18n.compensate.codes; //  年出险的案件，不能选择    //年的巨灾代码！
//			}
//		}
//	}
	//add by caozhigang 2010-01-22 如果輸入了一级巨灾代码，那么二级代码也必须輸入 end
	//add by zhangyurui 2009-05-26 增加是否代付赔款判断 begin
	var isPayForOther = document.getElementsByName("isPayForOther");
	if (isPayForOther.length > 1 && isPayForOther[0].checked == false && isPayForOther[1].checked == false) {
		errorMessage = errorMessage + i18n.compensate.NotCompensation; //请选择是否代付赔款\n
	}
	//add by zhangyurui 2009-05-26 增加是否代付赔款判断 end
	//textarea文本框设置值
	var context = fm.prpLctextContextInnerHTML.value;
	if (context.length < 1) {
		errorMessage = errorMessage + i18n.compensate.NotCalculation; //赔款计算过程不允许为空\n
	}
	//add by wzy start at 2009-04-02
	//reasion:增加"是否为案终计算书"的判断
	//var prpLcompensateFinallyFlaglist = document.getElementsByName("prpLcompensateFinallyFlag");
	//if(prpLcompensateFinallyFlaglist[0].checked==false && prpLcompensateFinallyFlaglist[1].checked==false){
	// errorMessage = errorMessage + "请选择是否为案终计算书\n";
	//}
	
	//reasion:增加联共保的判断
	if (fm.chiefflag.value == "1" || fm.chiefflag.value == '3') {
		if (fm.all("lossOrChargeHaveChanged") != null && fm.lossOrChargeHaveChanged.value == '1') {
			errorMessage = errorMessage + i18n.compensate.amountsChanged; //金额已发生变化，请选择'生成联共保分摊信息'按钮，重新生成联共保信息後再保存！
		}
	}
	//mantis： CLM0105，處理人員：BL061 張明財，需求單編號：CLM0105 新核心-手機正規化 start
	if (saveType == "4"){
		for (var i = 1; i < fm.prpLpayObjectInfoBeneficiaryPhone.length; i++) {
		var prpLpayObjectInfoBeneficiaryPhone =fm.prpLpayObjectInfoBeneficiaryPhone[i].value;
		if (prpLpayObjectInfoBeneficiaryPhone.length > 0) {
			 if (prpLpayObjectInfoBeneficiaryPhone.substr(0, 2)=='09'){
			    	reg =/^09[0-9]{8}$/;
			    	  if(!reg.test(prpLpayObjectInfoBeneficiaryPhone)){
			    		errorMessage =errorMessage +"受款人"+i+"電話有誤\n";
			    	}
			  } else {
			      reg =/^[0-9]{2,3}[0-9]{7,8}$/;
			      if (!reg.test(prpLpayObjectInfoBeneficiaryPhone)){
			    	errorMessage =errorMessage +"受款人"+i+"電話有誤\n";
			      }
			}
		    		
		 }
		}
		//行动电话
		for (var i = 1; i < fm.prpLpayObjectInfoMobilePhoneNo.length; i++) {
		var prpLpayObjectInfoMobilePhoneNo =fm.prpLpayObjectInfoMobilePhoneNo[i].value;
		if (prpLpayObjectInfoMobilePhoneNo.length > 0) {
			    var reg =/^09[0-9]{8}$/;
			    if(!reg.test(prpLpayObjectInfoMobilePhoneNo)){
			    	errorMessage =errorMessage +"受款人"+i+"行動電話有誤\n";
			    }
			    		
		}   
		}
	} //mantis： CLM0105，處理人員：BL061 張明財，需求單編號：CLM0105 新核心-手機正規化 end  
	if (errorMessage.length > 0) {
		alert(errorMessage);
		return false;
	}
	//reason: ValidateData.js中的校验不起作用时，因为没有调用校验方法
	if (!validateForm(fm, 'Engage_Data,lLoss_Data,Limit_Data,Charge_Data')) {
		return false;
	}
	var underWriteFlag = fm.prpLcompensateUnderWriteFlag.value;

	if (saveType == 4) {
		//判断理算报告是不能为空的。	
		var context2 = fm.prpLltextContextInnerHTML.value;
		var lltext2title = fm.tdLltextTitle.value;

		if (context2.length < 1) {
			errorMessage = errorMessage + lltext2title + i18n.js.notAllowedEmpty; //不允许为空\n
			alert(errorMessage)
			return false;
		}


		//       alert(context2.length)
		//       return false;
		//add by lixiang end at 2006-8-1
		//非案终计算书进行实赔金额是否大於估损金额的判断add by qinyongli 2005-09-20
		/*由於国寿的要求，需要进行去掉这样的判断。
  	if(fm.prpLcompensateFinallyFlag[1].checked){
  	    var sumClaim = parseFloat(fm.prpLcompensateSumClaim.value);
  	    var sumPaidAll = parseFloat(fm.sumPaidAll.value);
  	    var sumThisPaid = parseFloat(fm.prpLcompensateSumThisPaid.value);
  	    sumPaidAll = sumPaidAll + sumThisPaid;
  	    if(sumPaidAll>sumClaim){
  	    	alert("实赔金额大於估损金额，不允许提交核赔，您可以暂存後处理！");
  	    	return false;
  	    }
        } 
        //add end 2005-09-20
        */
		/*
    if(underWriteFlag!=1){
     alert("核赔复核不通过时不能提交");
     return false;
    }*/
		//赔付标的信息或赔付人员信息二者其一必须填写，否则不能提交实赔
		var prpLlossDtoSerialNo = fm.prpLlossDtoSerialNo.length;
		var prpLchargeSerialNo = fm.prpLchargeSerialNo.length;
		if (prpLlossDtoSerialNo == undefined && prpLchargeSerialNo == undefined) {
			alert(i18n.compensate.claimBook); //赔款计算书中的赔付标的，赔款费用至少有一条记录!
			return false;
		}
		//案件最终 赔款金额为：	,费用金额为：	,请确认！
		if (confirm(i18n.commonAcci.compensate.caseFinallyIndemnity + fm.prpLcompensateSumThisPaid.value + i18n.commonAcci.compensate.feeAmount + fm.prpLcompensateSumNoDutyFee.value + i18n.commonAcci.compensate.pleaseConfirm)) {} else {
			undisablebutton();
			return false;
		}
	}
	//reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
	field.disabled = true;
	//modify by weishixin add end 20040616
	fm.submit();

	return true;
}


/**
 @author 中科软
 @description 增加一条赔付人员费用信息方法
 @param       无
 @return      无
 @see         UIMulLine#insertRow
 @see         UIMulLine#setRowRecordState
*/
function insertRowKind() {
	/*
  if(fm.PersonName.disabled==true)
  {
    errorMessage("请先添加一条人员信息!");
    return;
  }

	if(isEmpty(fm.PersonLossKindCodeU))
	{
		errorMessage("请先输入赔付人员险别!");
		fm.PersonLossKindCodeU.focus();
		return;
	}

  if (!isEmpty(fm.PersonLossOverAmount))
  {
    errorMessage(fm.PersonLossOverAmount.value);
    return;
  }
  */

	insertRow('Kind', 'Kind_Data')
	/*
  //设置行关键字（序号）
  setRowRecordState("PersonLoss","Kind");

  var findex;
  findex = fm.all("PersonLossClaimRate").length;
  fm.all("PersonLossClaimRate")[findex-1].value = "100.0000";
*/
}

/**
 *@description 弹出留言保存页面
 *@param       无
 *@return      通过返回true,否则返回false
 */

function openWinSave() {

	var policyNo = fm.prpLcompensatePolicyNo.value;
	var riskCode = fm.prpLcompensateRiskCode.value;
	var businessNo = fm.prpLcompensateClaimNo.value;
	var claimNo = fm.prpLcompensateClaimNo.value;
	msg = window.open("/claim/messageQueryInfo.do?businessNo=" + businessNo + "&nodeType=compe&policyNo=" + policyNo + "&riskCode=" + riskCode + "&claimNo=" + claimNo, "NewWindow", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,width=500,Height=300");
}

/**
 *@description 弹出查看留言页面
 *@param       无
 *@return      通过返回true,否则返回false
 */

function openWinQuery() {
	var win;
	var messagedo = "/claim/messageQueryList.do?claimNo=" + fm.prpLcompensateClaimNo.value;

	win = window.showModalDialog(messagedo, "NewWindow", "status=no,resizable=yes,scrollbars=yes,width=500,Height=400");
}

//Modify By sunhao add begin 2004-09-06
/**
 *@description 弹出关联页面
 *@param       无
 *@return      通过返回true,否则返回false
 */
function relate() {

	var policyNo = fm.prpLcompensatePolicyNo.value;
	var claimNo = fm.prpLcompensateClaimNo.value;
	var newWindow = window.open("/claim/RelateBusinessNo.do?policyNo=" + policyNo + "&claimNo=" + claimNo, "NewWindow", "width=640,height=300,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");

}
//Modify By sunhao add end 2004-09-06


//Modify By 理赔组 add end 2004-09-07
/**
 *@description 实赔任务复核
 *@param       无
 *@return      通过返回true,否则返回false
 */
function approveSubmit() {
	if (checkForm() == false) {
		return false;
	}

	fm.buttonApprove.disabled = true;
	fm.submit();
	return true;
}

//Modify By 理赔组 add end 2004-09-07

/**
 @author 中科软
 @description 生成理算报告
 @param       无
 @return      无
 */
function generateCtext() {

	if (checkForm() == false) {
		return false;
	}

	//解除锁定,否则无法传入下一页
	//fm.Sex.disabled = false;

	//保存数据
	//saveRecord("Loss");
	//saveRowRecordToSingleTable("PersonLoss","Kind");

	var oldAction = fm.action;
	var oldTarget = fm.target;

	fm.action = "/claim/compensate/compensateGenerate.do";
	fm.target = "fraCalculate";
	//fm.target="_blank";

	fm.submit();

	fm.action = oldAction;
	fm.target = oldTarget;

	//清除数据
	//clearRecord("Loss");
	//clearRecord("PersonLoss");
	//clearRowRecord("Kind");

	//load data
	//loadRowRecord("PersonLoss","Kind","Kind_Data");
	return true;
}


//按钮单击事件，用於条款的显示

function buttonOnClick(fieldObject) {
	var intIndex = parseInt(fieldObject.num);
	var spanId = 'span_Engage_Context';
	if (isNaN(fm.button_Engage_Open_Context.length)) {} else { //多行
		spanId = 'span_Engage_Context' + "[" + intIndex + "]";
	}
	showSubPage2(spanId);
}


//按钮单击事件，用於相同保单号码多报案的显示

function buttonOnClick2(strSubPageCode) {
	var sameCount = parseInt(fm.PerilCount.value);

	if (sameCount < 1) {
		fm.button_Peril_Open_Context.disabled = true;
		return;
	}
	showSubPage2(strSubPageCode);

}

//显示输入框
//leftMove 默认值0，坐标左移leftMove

function showSubPage2(spanID, leftMove) {
	var intLeftMove = (leftMove == null ? 0 : leftMove);
	var span = eval(spanID);
	var strTemp = span.id;

	var ex = window.event.clientX + document.body.scrollLeft; //得到事件的坐标x
	var ey = window.event.clientY + document.body.scrollTop; //得到事件的坐标y

	ex = ex - 520;

	if (ex < 0) {
		ex = 0;
	}
	ex = ex - intLeftMove;

	span.style.left = ex;
	span.style.top = ey;
	span.style.display = '';
}
//按钮单击事件，用於相同保单号码多报案的显示

function buttonOnClick(strSubPageCode) {
	var sameCount = parseInt(fm.PerilCount.value);

	if (sameCount < 1) {
		fm.button_Peril_Open_Context.disabled = true;
		return;
	}
	showSubPage1(strSubPageCode);

}

//显示输入框
//leftMove 默认值0，坐标左移leftMove

function showSubPage1(spanID, leftMove) {
	var intLeftMove = (leftMove == null ? 0 : leftMove);
	var span = eval(spanID);
	var strTemp = span.id;

	var ex = window.event.clientX + document.body.scrollLeft; //得到事件的坐标x
	var ey = window.event.clientY + document.body.scrollTop; //得到事件的坐标y

	ex = ex - 520;

	if (ex < 0) {
		ex = 0;
	}
	ex = ex - intLeftMove;
	ey = ey + 10;
	span.style.left = ex;
	span.style.top = ey;
	span.style.display = '';
}

function calcuateSumRealPay(field) {
	var index = getElementOrder(field) - 1;
	var realPay = (parseFloat(fm.prpLlossDtoSumLoss[index].value, 10) -
		parseFloat(fm.prpLlossDtoSumRest[index].value, 10)) *
		parseFloat(fm.prpLlossDtoClaimRate[index].value, 10) / 100 *
		(1 - parseFloat(fm.prpLlossDtoDeductibleRate[index].value, 10) / 100);
	var deductible = parseFloat(fm.prpLlossDtoSumLoss[index].value, 10) *
		parseFloat(fm.prpLlossDtoDeductibleRate[index].value, 10) / 100;

	var realPay = (parseFloat(fm.prpLlossDtoSumLoss[index].value, 10) -
		parseFloat(fm.prpLlossDtoSumRest[index].value, 10)) *
		parseFloat(fm.prpLlossDtoClaimRate[index].value, 10) / 100 - deductible;

	if (isNaN(realPay)) {
		realPay = 0;
	}
	fm.prpLlossDtoDeductible[index].value = deductible;
	fm.prpLlossDtoSumRealPay[index].value = realPay;

	//add by lixiang start at 2007-07-04
	//reasion:增加变化的量设置
	setChangelossChargeFlag();
	//add by lixiang end at 2007-07-04
	//计算责任赔款合计、赔款合计、其它费用、实赔金额
	calFund(field);


}

//如果是案终陪付，则显示结案报告。

function changePrpLcompensateFinallyFlag() {
	if (fm.prpLcompensateFinallyFlag[0].checked) {
		Lltext.style.display = "";
	} else {
		Lltext.style.display = "none";
	}
}




/**
 @author 中科软
 @description 计算赔付标的和人员核损金额之和
 @param       无
 @return      无
 @see         UICommon.js#point、round
*/
function calLoss() {
	//定义变量
	var dblSumLoss = 0;
	var LossSumLoss = 0;
	var PersonLossSumLoss = 0;
	var i = 0;

	//赔付标的的核损金额
	//保存数据
	//setCurrentRecord("Loss");

	//var lossData= getPageData("Loss");

	for (i = 1; i < fm.all("prpLlossDtoSumLoss").length; i++) {
		LossSumLoss = parseFloat(fm.all("prpLlossDtoSumLoss")[i].value);

		if (isNaN(LossSumLoss))
			LossSumLoss = 0;

		dblSumLoss = dblSumLoss + LossSumLoss;
	}
}

function checkRepeatItemCode(field) {
	//取得当前的数据
	var fieldname = field.name;
	var findex = 0;
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == field) {
			findex = i;
			break;
		}
	}
	var dangerNo = fm.all("prpLlossDtoDangerNo")[findex].value;
	var kindCode = fm.all("prpLlossDtoKindCode")[findex].value;
	var itemCode = fm.all("prpLlossDtoItemCode")[findex].value;
	var itemKindNo = fm.all("prpLlossDtoItemKindNo")[findex].value;
	//得到已经輸入的险别和标的
	for (i = 1; i < fm.all("prpLlossDtoKindCode").length - 1; i++) {
		if (i == findex) {
			continue;
		}
		if (itemCode != "" && fm.all("prpLlossDtoDangerNo")[i].value == dangerNo && fm.all("prpLlossDtoKindCode")[i].value == kindCode && fm.all("prpLlossDtoItemCode")[i].value == itemCode && fm.all("prpLlossDtoItemKindNo")[i].value == itemKindNo) {
			alert(i18n.compensate.alreadyExists); //您輸入的险别和标的已经存在
			fm.all("prpLlossDtoItemCode")[findex].value = "";
			fm.all("prpLlossDtoLossName")[findex].value = "";
			fm.all("prpLlossDtoAmount")[findex].value = "";
			fm.all("prpLlossDtoAmountDisplay")[findex].value = "";
			//fm.all("prpLlossDtoItemCode")[findex].focus();
		}
	}
	// add by wenbin start at 2007-10-25
	//reasion:根据选择险别和标的,自动显示免赔额,或免赔率
	if (kindCode.length > 0 && itemCode.length > 0) {

		var inputObject = field;
		var outputObject;
		var policyno = fm.policyno.value;
		var inputArgs = {
			kindCode1: kindCode,
			itemCode: itemCode,
			policyno: policyno
		};
		var param = DWRUtil.getValues(inputArgs);
		DWREngine.setAsync(false);
		dwrInvokeData("getPrpcitemkind", param, "rollbackItemCode", inputObject, outputObject);
		DWREngine.setAsync(true);
	} else {
		fm.all("prpLlossDtoDeductible")[findex].value = "0.00";
		fm.all("prpLlossDtoDeductibleRate")[findex].value = "0.00";
	}

}

function rollbackItemCode(inputObject, outputObject, returnObject) {

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
	var prpCitemKindDto = returnObject;

	fm.all("prpLlossDtoDeductible")[findex].value = prpCitemKindDto.deductible;
	fm.all("prpLlossDtoDeductibleRate")[findex].value = prpCitemKindDto.deductibleRate;
	calRealpay(inputObject);
}
// add by wenbin end at 2007-10-25

//add by qinyongli 查看出险时保单信息,在业务系统中进行保单还原
/**function backWardPolicy(){
     var SHOWTYPE  ="SHOW";
     var BizNo     =fm.prpLcompensatePolicyNo.value;
     var RiskCode  =fm.prpLcompensateRiskCode.value;
     var damageDate=fm.damageDate.value;
     var vURL = '/prpall/' + RiskCode + '/tbcbpg/UIPrPoEn' + RiskCode + 'Show.jsp?BIZTYPE=POLICY&SHOWTYPE=SHOW&BizNo='+ BizNo+'&RiskCode='+ RiskCode+'&damageDate='+ damageDate;
     window.open(vURL,'详细信息','width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}*/


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

function creatCoinsFlag(countFlag) {
	fm.countFlag.value = countFlag;
}

function calRealpay(field) {
	var SumLoss; //核损金额
	var SumRest; //残值
	var ClaimRate; //赔偿比例
	var DeductibleRate; //免赔率
	var Deductible; //免赔额
	var Deductibletemp; //免赔
	var DutyRate; //责任比例
	var Realpay; //赔偿金额
	var temp;
	var ItemValue; //保险金额
	var inputObject = field;
	var outputObject;
	var fieldname = inputObject.name;
	var findex = 0;
	var riskCode = fm.prpLcompensateRiskCode.value;
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == field) {
			findex = i;
			break;
		}
	}
	SumLoss = fm.all("prpLlossDtoSumLoss")[findex].value;
	SumRest = fm.all("prpLlossDtoSumRest")[findex].value;
	ClaimRate = fm.all("prpLlossDtoClaimRate")[findex].value;
	DeductibleRate = fm.all("prpLlossDtoDeductibleRate")[findex].value;
	Deductible = fm.all("prpLlossDtoDeductible")[findex].value;
	Amount = fm.all("prpLlossDtoAmount")[findex].value;
	var baseCurrency = fm.all("prpLlossDtoCurrency4")[findex].value;
	var exchCurrency = fm.MergeCurrency.value; //目标币别

	//add by wenbin start at 2007-10-26
	//reasion:选择计算免赔额免赔率
	if (SumLoss != 0 || SumLoss != '') {
		if (SumLoss - Deductible > SumLoss - SumLoss / 100 * DeductibleRate) {

			Deductible = "0.00";
			fm.all("prpLlossDtoDeductible")[findex].value = "0.00";
		} else {
			DeductibleRate = "0";
			fm.all("prpLlossDtoDeductibleRate")[findex].value = "0";;
		}
	}
	//add by wenbin end at 2007-10-26     

	if (baseCurrency == '')
		baseCurrency == "CNY";
	if (isNaN(Amount) || Amount == '')
		Amount = "0";
	if (isNaN(SumLoss) || SumLoss == '')
		SumLoss = "0";
	if (isNaN(SumRest) || SumRest == '')
		SumRest = "0";
	if (isNaN(ClaimRate) || ClaimRate == '')
		ClaimRate = "0";
	else
		ClaimRate = parseFloat(ClaimRate) / 100;
	if (isNaN(DutyRate) || DutyRate == '')
		DutyRate = "0";
	else
		DutyRate = parseFloat(DutyRate) / 100;
	if (isNaN(DeductibleRate) || DeductibleRate == '')
		DeductibleRate = "0";
	else
		DeductibleRate = parseFloat(DeductibleRate) / 100;
	if (isNaN(Deductible))
		Deductible = "0";


	if (parseFloat(DeductibleRate) > 1) {
		alert(i18n.compensate.greaterThan); //免赔率不能大於100
		fm.all("prpLlossDtoDeductibleRate")[findex].value = "0";
		return false;
	}


	/*
     //免赔额、免赔率如果输入只能输入其中一项
   if(parseFloat(DeductibleRate)>0&&parseFloat(Deductible)>0){
     alert("免赔率与免赔额只能输入一项");
     if(fieldname=="prpLlossDtoDeductible"){
           fm.all("prpLlossDtoDeductible")[findex].value="0";
     }else{
           fm.all("prpLlossDtoDeductibleRate")[findex].value="0";
     }
     return false;
   }  
*/




	//add by lixiang start at 2007-07-04
	//reasion:增加变化的量设置
	setChangelossChargeFlag();
	//add by lixiang end at 2007-07-04
	fm.all("prpLlossDtoDeductible")[findex].value = point(round(Deductible, 0), 0);
	var inputArgs = {
		baseCurrency: baseCurrency,
		exchCurrency: exchCurrency,
		riskCode: riskCode,
		SumLoss: SumLoss,
		SumRest: SumRest,
		ClaimRate: ClaimRate,
		DeductibleRate: DeductibleRate,
		Deductible: Deductible
	};
	var param = DWRUtil.getValues(inputArgs);
	DWREngine.setAsync(false);
	dwrInvokeData("compensateRealPay", param, "rollbackCalRealpay", inputObject, outputObject);
	DWREngine.setAsync(true);
}

function rollbackCalRealpay(inputObject, outputObject, returnObject) {
	var prpLlossDto = returnObject;
	var fieldname = inputObject.name;
	var findex = 0;
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == inputObject) {
			findex = i;
			break;
		}
	}

	fm.all("prpLlossDtoSumRealPay")[findex].value = point(round(prpLlossDto.sumRealPay, 0), 0);
	var isMain = 1;
	var kindCode = fm.all("prpLlossDtoKindCode")[findex].value;
	var kindCodeFlag = kindCode.substring(0, 1);
	if (kindCodeFlag == 0) {
		isMain = 1;
	} else {
		isMain = 0;
	}
	var Amount;
	if (fm.all("prpLlossDtoAmountDisplay")[findex]) {
		Amount = parseFloat(fm.all("prpLlossDtoAmountDisplay")[findex].value);
	} else {
		Amount = parseFloat(fm.all("prpLlossDtoAmountDisplay")[0].value);
	}
	if (isMain == 1 && prpLlossDto.sumRealPay > Amount) {
		alert(i18n.compensate.cannotAmount); //赔偿金额不能大於保险金额
		fm.all("prpLlossDtoSumLoss")[findex].focus;
		return false;
	}

	//计算责任赔款合计、赔款合计、其它费用、实赔金额
	calFund(fm.prpLchargeSumRealPay);
}

function calFund(field) {
	index = getElementOrder(field) - 1;
	if (undefined != fm.prpLchargeSumRealPay[index] || undefined != fm.prpLchargeChargeReport[index]) {
		if ((fm.prpLchargeSumRealPay[index].value - fm.prpLchargeChargeReport[index].value) > 0) {
			alert(i18n.compensate.costAmount); //计入赔款金额不能大於费用金额！
			fm.prpLchargeSumRealPay[index].value = 0;
			fm.prpLchargeChargeAmount[index].value = fm.prpLchargeChargeReport[index].value;
		}
	}
	var chargeRealPay = "0";
	var chargeAmount = "0";
	var lossRealPay = "0";
	var personLossRealPay = "0";
	var policyNo = fm.policyno.value;
	var riskCode = fm.prpLcompensateRiskCode.value;
	var baseCurrency = '';
	var exchCurrency = fm.MergeCurrency.value; //目标币别
	var chargeRealPay = "0";

	var inputObject = field;
	var outputObject;
	var i = 0;
	for (i = 1; i < fm.all("prpLchargeSumRealPay").length; i++) {

		if (fm.all("prpLchargeSumRealPay")[i].value == '' || isNaN(fm.all("prpLchargeSumRealPay")[i].value))
			fm.all("prpLchargeSumRealPay")[i].value = "0";
		if (fm.all("prpLchargeChargeReport")[i].value == '' || isNaN(fm.all("prpLchargeChargeReport")[i].value)) {
			fm.all("prpLchargeChargeReport")[i].value = "0";
		}
		if (isNaN(fm.all("prpLchargeChargeReport")[i].value) || fm.all("prpLchargeChargeReport")[i].value == '')
			fm.all("prpLchargeChargeReport")[i].value = "CNY";
		//chargeRealPay = chargeRealPay + "-" + fm.all("prpLchargeSumRealPay")[i].value;
		//chargeAmount  = chargeAmount + "-" + fm.all("prpLchargeChargeReport")[i].value; 
		//baseCurrency = baseCurrency + "-" + fm.prpLchargeCurrency[i].value;
		chargeRealPay = chargeRealPay + "," + fm.all("prpLchargeSumRealPay")[i].value;
		chargeAmount = chargeAmount + "," + fm.all("prpLchargeChargeAmount")[i].value;
		baseCurrency = baseCurrency + "," + fm.prpLchargeCurrency[i].value;
		//dwr中是用","分格的数据

	}

	//2.赔付标的的赔偿金额
	//保存数据
	var lossData = getTableElements("lLoss");
	var dblSumRest = "0";
	var dblSumPrePaid = fm.prpLcompensateSumPrePaid.value;
	if (dblSumPrePaid == '' || isNaN(dblSumPrePaid))
		dblSumPrePaid = "0";
	var intLossCount = lossData.length;
	var dblRealPayAB = 0;
	for (i = 1; i < fm.all("prpLlossDtoSumRealPay").length; i++) {
		//lossRealPay = fm.all("prpLlossDtoSumRealPay")[i].value;

		if (isNaN(fm.all("prpLlossDtoSumRealPay")[i].value) || fm.all("prpLlossDtoSumRealPay")[i].value == '')
			fm.all("prpLlossDtoSumRealPay")[i].value = "0";
		//dblSumRest = dblSumRest + "-" + fm.all("prpLlossDtoSumRest")[i].value;	
		//lossRealPay = lossRealPay + "-" + fm.all("prpLlossDtoSumRealPay")[i].value;
		dblSumRest = dblSumRest + "," + fm.all("prpLlossDtoSumRest")[i].value;
		lossRealPay = lossRealPay + "," + fm.all("prpLlossDtoSumRealPay")[i].value;
		//dwr中是用","分格的数据
	}

	var inputArgs = {
		policyNo: policyNo,
		baseCurrency: baseCurrency,
		exchCurrency: exchCurrency,
		riskCode: riskCode,
		chargeRealPay: chargeRealPay,
		chargeAmount: chargeAmount,
		lossRealPay: lossRealPay,
		dblSumRest: dblSumRest,
		dblSumPrePaid: dblSumPrePaid
	};
	var param = DWRUtil.getValues(inputArgs);
	DWREngine.setAsync(false);
	dwrInvokeData("getSumRealPay", param, "rollbackCalFund", inputObject, outputObject);
	DWREngine.setAsync(true);
}

function rollbackCalFund(inputObject, outputObject, returnObject) {
	var prpLcompensateDto = returnObject;

	fm.prpLcompensateSumDutyPaid.value = point(round(prpLcompensateDto.sumDutyPaid, 0), 0);
	fm.prpLcompensateSumNoDutyFee.value = point(round(prpLcompensateDto.sumNoDutyFee, 0), 0);
	fm.prpLcompensateSumPaid.value = point(round(prpLcompensateDto.sumPaid, 0), 0);
	fm.prpLcompensateSumThisPaid.value = point(round(prpLcompensateDto.sumThisPaid, 0), 0);
	fm.prpLdangerRiskSumPaid.value = point(round(prpLcompensateDto.sumThisPaid, 0), 0); //危险单位赋值
	fm.prpLcompensateSumRest.value = point(round(prpLcompensateDto.sumRest, 0), 0);

	//add by liping 08-04-24 
	if (fm.buttonCoins) {
		creatCoins();
		creatCoinsFlag('1');
		resetChangelossCharge();
	}
	undisablebutton();

}

function getPayObject(field) {
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
		alert(i18n.replevy.nameCost); //请选择费用名称
		return;
	} else {
		chargeCode = chargeCodeList[index].value;
		payObjectType = prpLchargePayObjectType[index].value;
		//add by liuwei at 2010-12-21 根据费用类型和支付类型的不同，要求带出外部机构或手工輸入支付对象 begin
		if (((chargeCode == "04" || chargeCode == "05" || chargeCode == "07" || chargeCode == "13" || chargeCode == "15") && payObjectType == "A") ||
			 chargeCode == "08" || chargeCode == "99") { //手工輸入支付对象
			var serialNo = getElementOrder(field) - 1;
			var url = "/claim/common/account/PaymentAccountName.jsp?serialNo=" + serialNo;
			var handle = window.showModalDialog(url, window, "dialogHide:yes;help:no;status:no;scroll:yes;dialogWidth:300px;dialogHeight:460px");
			if (handle == null || handle == "") {
				fm.prpLchargePayObjectName[serialNo].value = ""
			} else {
				fm.prpLchargePayObjectName[serialNo].value = handle;
				fm.prpLchargeOwnerName[serialNo].value = handle;
				getAccountByPayObjectName(field, handle);
			}
		} else { //带出外部机构
			code_CodeSelect(field, 'getPayObject', '-1,0', 'Y', 'N', chargeCode + "|" + payObjectType);
			getExternAlagency(field, index);
		}
		//add by liuwei at 2010-12-21 根据费用类型和支付类型的不同，要求带出外部机构或手工輸入支付对象 end
	}
}
//add by zhangyurui 2009-02-27 修改费用代码是清空支付对象 begin

function clearPayObject(field) {
	var i = getElementOrder(field) - 1;
	var prpLchargePayObjectNameList = document.getElementsByName("prpLchargePayObjectName");
	var prpLchargePayObjectCodeList = document.getElementsByName("prpLchargePayObjectCode");
	prpLchargePayObjectNameList[i].value = "";
	prpLchargePayObjectCodeList[i].value = "";
}
//add by zhangyurui 2009-02-27 修改费用代码是清空支付对象 end

//add by luochang begin at 2010-06-13 当支付对象为外部机构时，自动带出外部机构的银行帳号

function getExternAlagency(field, index) {
	var inputObject = field;
	var outputObject;
	var ChargeCode = fm.prpLchargeChargeCode[index].value;
	var PayObjectType = fm.prpLchargePayObjectType[index].value;
	var PayObjectCode = fm.prpLchargePayObjectCode[index].value;
	if ((ChargeCode == "04" || ChargeCode == "05" || ChargeCode == "06" || ChargeCode == "07" || ChargeCode == "13" || ChargeCode == "15") && PayObjectType == "B" && PayObjectCode != "") {
		var inputArgs = {
			comCode: PayObjectCode
		};
		var param = DWRUtil.getValues(inputArgs);
		DWREngine.setAsync(false);
		dwrInvokeData("getExternAlagency", param, "rollbackExternAlagency", inputObject, outputObject);
		DWREngine.setAsync(true);
	} else {
		fm.prpLchargeOwnerShip[index].options[0].selected = true;
		fm.prpLchargeAccountCode[index].value = "";
		fm.prpLchargeBankName[index].value = "";
		fm.prpLchargeBankCode[index].value = "";
		fm.prpLchargeCustomBankCode[index].value = "";
		fm.prpLchargeCustomBankName[index].value = "";
		fm.prpLchargeOwnerName[index].value = "";
		fm.prpLchargeUniformNo[index].value= "";
		fm.prpLchargeAreaCode[index].value = "";
		fm.prpLchargeCourierAddress[index].value= "";
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
	fm.prpLchargeAccountCode[findex].value = prplexternalagencyDto.accountcode;
	fm.prpLchargeBankName[findex].value = prplexternalagencyDto.bankname;
	fm.prpLchargeBankCode[findex].value = prplexternalagencyDto.bankcode;
	fm.prpLchargeCustomBankName[findex].value = prplexternalagencyDto.custombankname;
	fm.prpLchargeCertifiCateCode[findex].value = prplexternalagencyDto.certificatecode;
	fm.prpLchargeOwnerName[findex].value = prplexternalagencyDto.ownername;
	fm.prpLchargePhoneNo[findex].value = prplexternalagencyDto.ownerphoneno;
	fm.prpLchargeAccountCurrency[findex].value = prplexternalagencyDto.accountcurrency;
	fm.prpLchargeAccountType[findex].value = prplexternalagencyDto.accounttype;
//	if (prplexternalagencyDto.accounttype == "1") {
//		fm.prpLchargeAccountTypeShow[findex].value = "存折";
//	} else if (prplexternalagencyDto.accounttype == "2") {
//		fm.prpLchargeAccountTypeShow[findex].value = "信用卡";
//	} else if (prplexternalagencyDto.accounttype == "3") {
//		fm.prpLchargeAccountTypeShow[findex].value = "储值卡";
//	} else if (prplexternalagencyDto.accounttype == "4") {
//		fm.prpLchargeAccountTypeShow[findex].value = "其他";
//	} else {
//		fm.prpLchargeAccountTypeShow[findex].value = "";
//	}
	fm.buttonAddAcc[findex].disabled = false;

	undisablebutton();
}
//add by luochang begin at 2010-06-13 当支付对象为外部机构时，自动带出外部机构的银行帳号

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
	fm.prpLchargeChargeReport[i].value = 0;
	fm.prpLchargeSumRealPay[i].value = 0;
	fm.prpLchargeChargeAmount[i].value = 0;
	fm.prpLchargeAccountCode[i].value = "";
	fm.prpLchargeBankName[i].value = "";
	fm.prpLchargeBankCode[i].value = "";
	fm.prpLchargeCustomBankCode[i].value = "";
	fm.prpLchargeCustomBankName[i].value = "";
	fm.prpLchargeOwnerName[i].value = "";
	fm.prpLchargeUniformNo[i].value= "";
	fm.prpLchargeAreaCode[i].value = "";
	fm.prpLchargeCourierAddress[i].value= "";
	setInput(field);
	checkBeyondQuota(field);
}
//add by liuwei at 2010-12-22 修改费用代码或支付类型或支付对象时，清空相应的付款信息 end