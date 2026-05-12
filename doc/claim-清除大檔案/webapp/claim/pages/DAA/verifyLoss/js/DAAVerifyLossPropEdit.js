/**
 *@description 根据按钮状态保存报案数据
 *@param       this
 *@param       保存状态
 *@return      通过返回true,否则返回false
 */
function saveForm(field, saveType) {
	if(saveType==5){
		var $veriwReturnReason = $.trim($("input[name='prpLverifyLossVeriwReturnReason']").val());
		if($veriwReturnReason==""){
			alert("請填寫回退原因！");
			return false;
		}
		$("input[name='prpLverifyLossUnderWriteFlag']").val("0");
	}else if(saveType==4){
		var message = "";
		var sumDefLoss = "";
		var veriSumDefLoss = "";
		var $trPersonFeeLoss = $("tr[name='trPersonFeeLoss']");
		$trPersonFeeLoss.each(function(i,n){
			if(i>0&&i%2==0){
				sumDefLoss = $(n).find("input[name='prpLpropSumDefLoss']").val();
				veriSumDefLoss = $($trPersonFeeLoss.get(i+1)).find("input[name='prpLpropVeriSumDefLoss']").val();
				if(parseFloat(sumDefLoss)!=parseFloat(veriSumDefLoss)){
					message +="第"+(i/2)+"條訊息定損金額和核損金額不一致，請回退修改。\n";
				}
			}
		})
		if(message!=""){
			alert(message);
			return false;
		}
		$("input[name='prpLverifyLossUnderWriteFlag']").val("1");
	}
	$("input[name='buttonSaveType']").val(saveType);
	//设置按钮变灰
	$("input[name='buttonSave']").attr("disabled","disabled");
	$("input[name='buttonSaveFinishSubmit']").attr("disabled","disabled");
	$("input[name='buttonCancel']").attr("disabled","disabled");
	$("input[name='buttonReject']").attr("disabled","disabled");
	$("input[name='buttonGiveup']").attr("disabled","disabled");
	fm.submit();
}

/*
        插入一条新的之後的处理（可选方法）
*/
function afterInsertprop() {
	setPrpLpropSerialNo();
}

/*
        删除本条WarnRegion之後的处理（可选方法）
      */
function afterDeleteprop(field) {
	setPrpLpropSerialNo();
}

/**
 * 设置setPrpLpropSerialNo
 */
function setPrpLpropSerialNo() {
	var count = getElementCount("prpLpropSerialNo");
	for (var i = 0; i < count; i++) {
		//alert("看看什么时候运行?count="+count+"  i="+i); 
		if (count != 1) {
			fm.prpLpropSerialNo[i].value = i;
		}
	}
}
/**
 * 页面初始话的方法
 * @return
 */
function initSet(){
	//增加保费是否实收提示
	var payFee = $("input[name='prpLverifyLossPayFee']").val();
	var errorMessage = "";
	if (payFee == -1) {
		errorMessage=errorMessage+i18n.certainLoss.policyPremiumNoPay;  // 此保单保费未缴,请慎重处理！！
	} else if (payFee == 0) {
		errorMessage=errorMessage+i18n.certainLoss.policyPremiumPay;  // 此保单已缴未缴全,请慎重处理！！！
	}
	if (errorMessage != "") {
		alert(errorMessage);
	}
	sumPropLossFee();
}
function sumPropLossFee(){
	var sumLossSum = 0; //受损金额
	var sumRejectSum = 0; //剔除金额
	var sumDefLossSum = 0; //核损金额 
	$("input[name='prpLpropSumLoss']").each(function(){
		if($.isNumeric(this.value)){
			sumLossSum += parseFloat(this.value);
		}else{
			this.value = 0;
		}
	})
	$("input[name='prpLpropSumReject']").each(function(){
		if($.isNumeric(this.value)){
			sumRejectSum += parseFloat(this.value);
		}else{
			this.value = 0;
		}
	})
	$("input[name='prpLpropSumDefLoss']").each(function(){
		if($.isNumeric(this.value)){
			sumDefLossSum += parseFloat(this.value);
		}else{
			this.value = 0;
		}
	})
	sumLossSum = point(round(sumLossSum,0),0);
	sumRejectSum = point(round(sumRejectSum,0),0);
	sumDefLossSum = point(round(sumDefLossSum,0),0);
	$("input[name='prpLpropSumSumLoss']").val(sumLossSum);
	$("input[name='prpLpropSumSumReject']").val(sumRejectSum);
	$("input[name='prpLpropSumSumDefLoss']").val(sumDefLossSum);
	$("input[name='prpLverifyLossSumPreDefLoss']").val(sumDefLossSum);//定损金额
	var prpLverifyLossSumDefLoss = 0;
	$("input[name='prpLpropVeriSumDefLoss']").each(function(){
		if($.isNumeric(this.value)){
			prpLverifyLossSumDefLoss += parseFloat(this.value);
		}else{
			this.value = 0;
		}
	})
	prpLverifyLossSumDefLoss = point(round(prpLverifyLossSumDefLoss,0),0);
	$("input[name='prpLverifyLossSumDefLoss']").val(prpLverifyLossSumDefLoss);//核损金额
	sumPrpLverifyLossWarpDefLoss();
}
/**
 * 计算偏差金额
 * @return
 */
function sumPrpLverifyLossWarpDefLoss(){
	var $prpLverifyLossFirstDefLoss = $("input[name='prpLverifyLossFirstDefLoss']");
	var $prpLverifyLossWarpDefLoss = $("input[name='prpLverifyLossWarpDefLoss']");
	var sumDefLoss = $("input[name='prpLverifyLossSumDefLoss']").val();//核损金额
	var warpDefLoss = sumDefLoss-parseFloat($prpLverifyLossFirstDefLoss.val());
	sumDefLoss = point(round(warpDefLoss,0),0);
	$prpLverifyLossWarpDefLoss.val(warpDefLoss);
}
/**
 * 计算核损金额
 * @param field
 * @return
 */
function calSumPropVeriDefLoss(field){
	var $trPersonFeeLoss = $(field).parents("tr[name='trPersonFeeLoss']");
	var $prpLpropVeriSumLoss = $trPersonFeeLoss.find("input[name='prpLpropVeriSumLoss']");
	if(!$.isNumeric($prpLpropVeriSumLoss.val())){
		$prpLpropVeriSumLoss.val(0);
	}
	var $prpLpropVeriSumReject = $trPersonFeeLoss.find("input[name='prpLpropVeriSumReject']");
	if(!$.isNumeric($prpLpropVeriSumReject.val())){
		$prpLpropVeriSumReject.val(0);
	}
	var $prpLpropVeriSumDefLoss = $trPersonFeeLoss.find("input[name='prpLpropVeriSumDefLoss']");
	var sumDefLoss = parseFloat($prpLpropVeriSumLoss.val())-parseFloat($prpLpropVeriSumReject.val());
	sumDefLoss = point(round(sumDefLoss,0),0);
	$prpLpropVeriSumDefLoss.val(sumDefLoss);
	var prpLverifyLossSumDefLoss = 0;//核损金额
	$("input[name='prpLpropVeriSumDefLoss']").each(function(){
		if($.isNumeric(this.value)){
			prpLverifyLossSumDefLoss += parseFloat(this.value);
		}
	});
	prpLverifyLossSumDefLoss = point(round(prpLverifyLossSumDefLoss,0),0);
	$("input[name='prpLverifyLossSumDefLoss']").val(prpLverifyLossSumDefLoss);//核损金额
	sumPrpLverifyLossWarpDefLoss();
}
/**
 * 清除操作
 * @return
 */
function resetForm() {
	if (window.confirm("確定要清除嗎？")) {
		location.href = location.href;
		return true;
	} else {
		return false;
	}
}
/**
 *@description 弹出报案的画面
 *@param       无
 *@return      通过返回true,否则返回false
 */
function relateRegist() {
	var registNo = $("input[name='RegistNo']").val();
	var riskcode = $("input[name='riskcode']").val();
	var linkURL = "/claim/registFinishQueryList.do?prpLregistRegistNo=" + registNo + "&editType=SHOW&riskCode=" + riskcode;
	if (opener == undefined) {
		linkURL = linkURL + "&ifclose=true";
	}
	var newWindow = window.open(linkURL, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
}
/**
 *@description 弹出查勘的画面
 *@param       无
 *@return      通过返回true,否则返回false
 */
function relateCheck() {
	var registNo = $("input[name='RegistNo']").val();
	var riskcode = $("input[name='riskcode']").val();
	var linkURL = "/claim/check/checkFinishQueryList.do?prpLcheckCheckNo=" + registNo + "&editType=SHOW&riskCode=" + riskcode;
	if (opener == undefined) {
		linkURL = linkURL + "&ifclose=true";
	}
	var newWindow = window.open(linkURL, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
}