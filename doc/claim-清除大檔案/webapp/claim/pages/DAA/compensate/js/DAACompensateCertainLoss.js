/**
 *  计算换件合计
 * @param field
 * @return
 */
function getComponentSumLoss(field){
	var $tr = $(field).parents("tr[name='TrComponent']");
	var $MaterialFee = $tr.find(":input[name='prpLcomponentMaterialFee']");
	if(!$.isNumeric($MaterialFee.val())){
		$MaterialFee.val(0);
	}
	var $Quantity = $tr.find(":input[name='prpLcomponentQuantity']");
	if(!$.isNumeric($Quantity.val())){
		$Quantity.val(1);
	}
	var $RestFee = $(this).find(":input[name='prpLcomponentRestFee']");
	if(!$.isNumeric($RestFee.val())){
		$RestFee.val(0);
	}
	var $SumDefLoss = $tr.find(":input[name='prpLcomponentSumDefLoss']");
	var sumDefLoss = parseFloat($MaterialFee.val())*parseFloat($Quantity.val());
	$SumDefLoss.val(pointTwo(sumDefLoss));
	sumComponentRepairFee();
}
/**
 * 计算工时费用
 * @param field
 * @return
 */
function getRepairFeeSumLoss(field){
	var $tr = $(field).parents("tr[name='TrRepairFee']");
	var $SumDefLoss = $tr.find(":input[name='prpLrepairFeeSumDefLoss']");
	if(!$.isNumeric($SumDefLoss.val())){
		$SumDefLoss.val(0);
	}
	sumComponentRepairFee();
}
/**
 * 统计定损合计
 * @return
 */
function sumComponentRepairFee(){
	var sumDefLoss2 = 0;
	var prpLcarLossSumRest = 0;
	$("#Component").find("tr[name='TrComponent']").each(function(){
		var $SumDefLoss = $(this).find(":input[name='prpLcomponentSumDefLoss']");
		if($.isNumeric($SumDefLoss.val())){
			sumDefLoss2 += parseFloat($SumDefLoss.val());
		}
		var $IfRemain = $(this).find(":input[name='prpLcomponentIfRemain']");
		if($IfRemain.val()=="0"){
			var $RestFee = $(this).find(":input[name='prpLcomponentRestFee']");
			if($.isNumeric($RestFee.val())){
				prpLcarLossSumRest += parseFloat($RestFee.val());
			}
		}
	});
	$(":input[name='SumDefLoss2']").val(pointTwo(sumDefLoss2));
	$(":input[name='prpLcarLossSumRest']").val(pointTwo(prpLcarLossSumRest));
	var sumDefLoss1 = 0;
	$("#RepairFee").find("tr[name='TrRepairFee']").each(function(){
		var $SumDefLoss = $(this).find(":input[name='prpLrepairFeeSumDefLoss']");
		if($.isNumeric($SumDefLoss.val())){
			sumDefLoss1 += parseFloat($SumDefLoss.val());
		}
	});
	$(":input[name='SumDefLoss1']").val(pointTwo(sumDefLoss1));
	var sumDef = pointTwo(sumDefLoss2+sumDefLoss1);
	$(":input[name='prpLcarLossSumCertainLoss']").val(sumDef);
	$(":input[name='prpLverifyLossSumPreDefLoss']").val(sumDef);
	$(":input[name='prpLverifyLossSumDefLoss']").val(sumDef);
	$(":input[name='prpLverifyLossFirstDefLoss']").val(sumDef);
	$(":input[name='prpLverifyLossWarpDefLoss']").val(sumDef);
}
/**
 * 理算提交后效验定损讯息
 * @return
 */
function checkCertainLoss(){
	var checkFlag = true;
	$("#Component").find(":input[name='prpLcomponentKindCode']").each(function(i,n){
		if(n.value==""){
			errorMessage("零配件更換專案費用清單的第"+(i+1)+"行沒有輸入險別!");
			checkFlag = false;
			return false;
		}
	});
	$("#RepairFee").find(":input[name='prpLrepairFeeKindCode']").each(function(i,n){
		if(n.value==""){
			errorMessage("修理專案費用清單 "+(i+1)+"行沒有輸入險別!");
			checkFlag = false;
			return false;
		}
	});
	return checkFlag;
}
$(function(){
	var certainLossFlag = $(":input[name='certainLossFlag']").val();
	if(certainLossFlag=="true"){
		sumComponentRepairFee();
	}
});