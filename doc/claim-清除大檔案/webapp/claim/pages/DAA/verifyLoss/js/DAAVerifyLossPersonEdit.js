/**
 * 插入一条费用讯息
 * @param field
 * @return
 */
function insertPersonFeeLoss(field) {
	var $cloneObject = $("#personFeeLoss_Data").find("tr[name='trPersonFeeLoss']").clone(true);
	var $tablePersonFeeLoss = $(field).parents("table[name='tablePersonFeeLoss']");
	var prpLpersonSerialNo = $(field).parents("tr[name='trPersonLoss']").find("input[name='prpLpersonSerialNo']").val();
	$cloneObject.find("input[name='personSerialNo']").val(prpLpersonSerialNo);
	$cloneObject.appendTo($tablePersonFeeLoss);
	return true;
}
/**
 * 插入一个人伤讯息
 * @param field
 * @return
 */
function insertPersonLoss(field) {
	var $cloneObject = $("#personLoss_Data").find("tr[name='trPersonLoss']").clone(true);
	var prpLpersonSerialNo = $("tr[name='trPersonLoss']").length;
	$cloneObject.find("input[name='prpLpersonSerialNo']").val(prpLpersonSerialNo);
	$cloneObject.appendTo("#personLoss");
	return true;
}

/**
  删除控制按钮控制的行，禁止非本模块调用
  字段，页名称，数据页中控制按钮的个数，数据页中每个控制按钮的控制的TR的个数
  返回删除行的序号（从1开始）
 */

function deleteRowTable(field, trName) {
	var $trName = $(field).parents("tr[name='"+trName+"']");
	if(trName=="trPersonFeeLoss"){
		$trName.next().remove();
	}
	$trName.remove();
}

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
				sumDefLoss = $(n).find("input[name='prpLpersonSumDefLoss']").val();
				veriSumDefLoss = $($trPersonFeeLoss.get(i+1)).find("input[name='prpLpersonVeriSumDefLoss']").val();
				if(parseFloat(sumDefLoss)!=parseFloat(veriSumDefLoss)){
					message +="第"+(i/2)+"條費用訊息定損金額和核損金額不一致，請回退修改。\n";
				}
			}
		})
		if(message!=""){
			alert(message);
			return false;
		}
		$("input[name='prpLverifyLossUnderWriteFlag']").val("1");
	}
	//提交之前去掉disabled的属性
	$("tr[name='trPersonLoss']").each(function(){
		$(this).find("select[name='prpLpersonFamilyName']").removeAttr("disabled");
		$(this).find("select[name='prpLpersonPersonSex']").removeAttr("disabled");
		$(this).find("select[name='prpLpersonWoundGrade']").removeAttr("disabled");
		$(this).find("select[name='prpLpersonChangeHospital']").removeAttr("disabled");
		$(this).find("select[name='prpLpersonFixedIncomeFlag']").removeAttr("disabled");
		$(this).find("select[name='prpLpersonPayPersonType']").removeAttr("disabled");
	});
	$("input[name='buttonSaveType']").val(saveType);
	//设置按钮变灰
	$("input[name='buttonSave']").attr("disabled","disabled");
	$("input[name='buttonSaveFinishSubmit']").attr("disabled","disabled");
	$("input[name='buttonCancel']").attr("disabled","disabled");
	$("input[name='buttonReject']").attr("disabled","disabled");
	$("input[name='buttonGiveup']").attr("disabled","disabled");
	fm.submit();
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
	sumPersonLossFee();
}
/**
 *@description 汇总人员伤亡费用计算
 *@param       无
 *@return      无
 */
function sumPersonLossFee() {
	var prpLpersonSumLossSum = 0; //報損金額
	var prpLpersonSumRejectSum = 0; //剔除金额
	var prpLpersonSumDefLossSum = 0; //定損金額
	var sumPreDefLoss = 0;
	$("tr[name='trPersonLoss']").each(function(i,n){
		if(i>0){
			prpLpersonSumLossSum = 0;
			prpLpersonSumRejectSum = 0;
			prpLpersonSumDefLossSum = 0;
			$(n).find("input[name='prpLpersonSumLoss']").each(function(){
				if($.isNumeric(this.value)){
					prpLpersonSumLossSum += parseFloat(this.value);
				}
			})
			$(n).find("input[name='prpLpersonSumReject']").each(function(){
				if($.isNumeric(this.value)){
					prpLpersonSumRejectSum += parseFloat(this.value);
				}
			})
			$(n).find("input[name='prpLpersonSumDefLoss']").each(function(){
				if($.isNumeric(this.value)){
					prpLpersonSumDefLossSum += parseFloat(this.value);
				}
			})
			prpLpersonSumLossSum = point(round(prpLpersonSumLossSum,0),0);
			prpLpersonSumRejectSum = point(round(prpLpersonSumRejectSum,0),0);
			prpLpersonSumDefLossSum = point(round(prpLpersonSumDefLossSum,0),0);
			$(n).find("input[name='prpLpersonSumLossSum']").val(prpLpersonSumLossSum);
			$(n).find("input[name='prpLpersonSumRejectSum']").val(prpLpersonSumRejectSum);
			$(n).find("input[name='prpLpersonSumDefLossSum']").val(prpLpersonSumDefLossSum);
			sumPreDefLoss += prpLpersonSumDefLossSum;
		}
	});
	$("input[name='prpLverifyLossSumPreDefLoss']").val(sumPreDefLoss);//定损金额
	var prpLverifyLossSumDefLoss = 0;//核损金额
	$("input[name='prpLpersonVeriSumDefLoss']").each(function(){
		if($.isNumeric(this.value)){
			prpLverifyLossSumDefLoss += parseFloat(this.value);
		}
	});
	prpLverifyLossSumDefLoss = point(round(prpLverifyLossSumDefLoss,0),0);
	$("input[name='prpLverifyLossSumDefLoss']").val(prpLverifyLossSumDefLoss);//核损金额
	sumPrpLverifyLossWarpDefLoss();
}
/**
 * 计算赔偿金额
 * @param field
 * @return
 */
function calSumPersonDefLoss(field){
	var $trPersonFeeLoss = $(field).parents("tr[name='trPersonFeeLoss']");
	var $prpLpersonSumLoss = $trPersonFeeLoss.find("input[name='prpLpersonSumLoss']");
	if(!$.isNumeric($prpLpersonSumLoss.val())){
		$prpLpersonSumLoss.val(0);
	}
	var $prpLpersonSumReject = $trPersonFeeLoss.find("input[name='prpLpersonSumReject']");
	if(!$.isNumeric($prpLpersonSumReject.val())){
		$prpLpersonSumReject.val(0);
	}
	var $prpLpersonSumDefLoss = $trPersonFeeLoss.find("input[name='prpLpersonSumDefLoss']");
	var sumDefLoss = parseFloat($prpLpersonSumLoss.val())-parseFloat($prpLpersonSumReject.val());
	sumDefLoss = point(round(sumDefLoss,0),0);
	$prpLpersonSumDefLoss.val(sumDefLoss);
	sumPersonLossFee();
}
/**
 * 计算核损的赔偿金额
 * @param field
 * @return
 */
function calSumPersonVeriDefLoss(field){
	var $trPersonFeeLoss = $(field).parents("tr[name='trPersonFeeLoss']");
	var $prpLpersonVeriSumLoss = $trPersonFeeLoss.find("input[name='prpLpersonVeriSumLoss']");
	if(!$.isNumeric($prpLpersonVeriSumLoss.val())){
		$prpLpersonVeriSumLoss.val(0);
	}
	var $prpLpersonVeriSumReject = $trPersonFeeLoss.find("input[name='prpLpersonVeriSumReject']");
	if(!$.isNumeric($prpLpersonVeriSumReject.val())){
		$prpLpersonVeriSumReject.val(0);
	}
	var $prpLpersonVeriSumDefLoss = $trPersonFeeLoss.find("input[name='prpLpersonVeriSumDefLoss']");
	var sumDefLoss = parseFloat($prpLpersonVeriSumLoss.val())-parseFloat($prpLpersonVeriSumReject.val());
	sumDefLoss = point(round(sumDefLoss,0),0);
	$prpLpersonVeriSumDefLoss.val(sumDefLoss);
	var prpLverifyLossSumDefLoss = 0;//核损金额
	$("input[name='prpLpersonVeriSumDefLoss']").each(function(){
		if($.isNumeric(this.value)){
			prpLverifyLossSumDefLoss += parseFloat(this.value);
		}
	});
	prpLverifyLossSumDefLoss = point(round(prpLverifyLossSumDefLoss,0),0);
	$("input[name='prpLverifyLossSumDefLoss']").val(prpLverifyLossSumDefLoss);//核损金额
	sumPrpLverifyLossWarpDefLoss();
}
/**
 * 设置傷情類別
 * @param field
 * @return
 */
function woundCodeChange(field){
	var $trPersonLoss = $(field).parents("tr[name='trPersonLoss']");
	var fieldName = field.name+"Txt";
	var $txt = $trPersonLoss.find("input[name='"+fieldName+"']");
	if(field.checked){
		$txt.val("1");
	}else{
		$txt.val("0");
	}
}
/**
 * 检查人员
 * @param Field
 * @return
 */
function checkRelatePersonNo(field){
	return true;
}
/**
@description 设置该页属性（只读或可写）
@param       无
@return      无
*/
function setPropertyOfPage(field){
	var $trPersonLoss = $(field).parents("tr[name='trPersonLoss']");
	var prpLpersonPayPersonType = parseInt(field.value);
	//关联只能是伤残亡的，只有伤残才能輸入伤残比例
	if(prpLpersonPayPersonType ==1 || prpLpersonPayPersonType==4 || prpLpersonPayPersonType==5){
		$trPersonLoss.find("input[name='prpLpersonRelatePersonNo']").attr("readOnly","readonly");
		//人员类型为亡的
		if(prpLpersonPayPersonType==5){
			$trPersonLoss.find("input[name='prpLpersonLossRate']").attr("readOnly","readonly");
		}else{
			$trPersonLoss.find("input[name='prpLpersonLossRate']").removeAttr("readOnly");
		}
	}else{
		$trPersonLoss.find("input[name='prpLpersonRelatePersonNo']").removeAttr("readOnly");
		$trPersonLoss.find("input[name='prpLpersonLossRate']").attr("readOnly","readonly");
	}
}

/**
 * 计算偏差金额
 * @return
 */
function sumPrpLverifyLossWarpDefLoss(){
	var $prpLverifyLossFirstDefLoss = $("input[name='prpLverifyLossFirstDefLoss']");
	var $prpLverifyLossWarpDefLoss = $("input[name='prpLverifyLossWarpDefLoss']");
	var $prpLpersonVeriSumDefLoss = $("input[name='prpLpersonVeriSumDefLoss']");
	var sumDefLoss = 0;
	$prpLpersonVeriSumDefLoss.each(function (){
		if(!$.isNumeric(this.value)){
			this.value = "0";
		}
		sumDefLoss += parseFloat(this.value);
	});
	var warpDefLoss = sumDefLoss-parseFloat($prpLverifyLossFirstDefLoss.val());
	sumDefLoss = point(round(warpDefLoss,0),0);
	$prpLverifyLossWarpDefLoss.val(warpDefLoss);
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