/**增加一个费用资讯信息*/
function insertChargeObject(){
	$("#Charge_Data").find("table[name='chargeObject']").clone(true).appendTo("#PrpLcharge");
	setPrpLchargeSerialNo();
}

function setPrpLchargeSerialNo() {
	var count = getElementCount("prpLchargeSerialNo");
	for ( var i = 1; i < count; i++) {
		fm.prpLchargeSerialNo[i].value = i;
	}
}

/** 删除一个费用资讯信息 */
function deleteChargeObject(field){
   var $curr = $(field);//当前对象
   var $chargeObject = $curr.parents("table[name='chargeObject']");//当前操作的费用资讯table
   $chargeObject.remove();//移除
   setPrpLchargeSerialNo();
   //calSumDutyPaid();
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
	$("div[name='payObject']").eq(1).show();
}
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
}
/***
 * 费用支付方式发生改变时
 */

function chargeOwnerShipChange(field) {
	var $chargeObject = $(field).parents("table[name='chargeObject']")
	if (field.value == "B") { //汇款
		$chargeObject.find("span[name='spanCutBack']").hide(); //隐藏禁背
		$chargeObject.find("tr[name='bankInfo']").show(); //显示支付账户信息
	} else if (field.value == "Q") { //支票
		$chargeObject.find("span[name='spanCutBack']").show();
		$chargeObject.find("tr[name='bankInfo']").hide();
	}
}
/**
 * 坐标
 * @param obj
 * @returns {Number}
 */
function findPosX(obj){
    var curLeft = 0;
    if(obj.offsetParent){
      do{
        curLeft += obj.offsetLeft;
      }while(obj = obj.offsetParent);
    }else if(obj.x){
      curLeft += obj.x;
    }
    return curLeft;
  }
function findPosY(obj){
   var curTop = 0;
   if (obj.offsetParent){
    do{
      curTop += obj.offsetTop;
    }while(obj = obj.offsetParent);
  }else if(obj.y){
    curTop += obj.y;
  }
  return curTop;
}

function deletePrpLpayObjectInfo(field){//删除一个支付对象
    var $prpLpayObjectInfo = $(field).parents("tr[name='PrpLpayObjectInfo']");
    var index = 0;
    $prpLpayObjectInfo.nextAll("tr[name='PrpLpayObjectInfo']").each(function(){
        var $serialNo = $(this).find("input[name='prpLpayObjectInfoSerialNo']");
        if(index ==0){
        	index = parseInt($serialNo.val())-1;
        }
        $(this).find("span[name='payObjectIndex']").html($serialNo.val()-1);
        $serialNo.val($serialNo.val()-1);
    });
    $prpLpayObjectInfo.remove();
    if(index==0){
    	index = $.find("input[name='prpLpayObjectInfoSerialNo']").length;
    }
    $.each($.find("input[name='prpLpersonPayObjectSerialNo']"),function(i,n){
    	if(i>0&&n.value!=""){
    		var payObjectValue = n.value.split(";");
			var payObjectValueTemp = "";
			for(var i=0;i<payObjectValue.length;i++){
				var payObjectTemp = payObjectValue[i].split(":");
				if(index<parseInt(payObjectTemp[0])){
					payObjectValueTemp += (parseInt(payObjectTemp[0])-1)+":"+payObjectTemp[1]+";";
				}else if(index>parseInt(payObjectTemp[0])){
					payObjectValueTemp += payObjectValue[i]+";";
				}
			}
			if(payObjectValueTemp!=""){
				payObjectValueTemp = payObjectValueTemp.substring(0, payObjectValueTemp.length-1);
			}
			n.value = payObjectValueTemp;
    	}
	});
	uLprpLPayObjectinfo();
 }

/**
 * 多个賠付對象讯息 
 */
var prpLfieldIndex = 0;
var prpLfieldName = 0;
function setPrpObjectinfoSerialNo(field){
	var odiv = document.getElementById("prpLPayObjectinfo");
	prpLfieldIndex = getElementOrder(field,document.forms[0])-1;
	prpLfieldName = field.name;
	var payObjectSerialNo = document.getElementsByName("payObjectSerialNo");
	var payObjectPayAmount = document.getElementsByName("payObjectPayAmount");
	for(var i=0;i<payObjectSerialNo.length;i++){
		payObjectSerialNo[i].checked = false;
		payObjectPayAmount[i].value = "";
	}
	if(field.value!=""){
		var payObjectValue = field.value.split(";");
		for(var i=0;i<payObjectValue.length;i++){
			var payObjectTemp = payObjectValue[i].split(":");
			if(payObjectSerialNo.length>=parseInt(payObjectTemp[0])){
				payObjectSerialNo[parseInt(payObjectTemp[0])-1].checked=true;
				payObjectPayAmount[parseInt(payObjectTemp[0])-1].value=payObjectTemp[1];
			}
		}
	}
	odiv.style.left = findPosX(field) - 3-300;
	odiv.style.top = findPosY(field) - 5;
	odiv.style.height = payObjectSerialNo.length==0?1*10:payObjectSerialNo.length*10;
	odiv.style.display = "block"
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

function uLprpLPayObjectinfo(){
	var uiLi_first = '<li><input type="checkbox" onclick="setPayObjectPayAmount();" name="payObjectSerialNo" value="';
	var uiLi_list = ' 	賠付金額: <input  type="text" name="payObjectPayAmount" onblur="setPayObjectPayAmount();" value="" class="common" style="width:100px"/></li>';
	var uiLi = "";
	$.each($.find("input[name='prpLpayObjectInfoSerialNo']"),function(i,n){
		if(i>0){
			uiLi += uiLi_first+i+'" />賠付對象'+i+uiLi_list;
		}
	});
	if(uiLi==""){
		uiLi = "沒有賠款給付對象訊息，請錄入賠款給付對象。";
	}
	var odiv = document.getElementById("prpLPayObjectinfo");
	if(odiv.style.display!="none"){
	    odiv.style.display = "none";
    }
	 var uiodiv = document.getElementById("uLprpLPayObjectinfo");
	 uiodiv.innerHTML=uiLi;
 }
/**
 * 设置赔付信息的录入域
 * @return
 */
function setPayObjectPayAmount() {
	var payObjectSerialNo = document.getElementsByName("payObjectSerialNo");
	var payObjectPayAmount = document.getElementsByName("payObjectPayAmount");
	var payObjectValue = "";
	for ( var i = 0; i < payObjectSerialNo.length; i++) {
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
	if(payObjectValue!=""){
		payObjectValue = payObjectValue.substring(0, payObjectValue.length-1);
	}
	document.getElementsByName(prpLfieldName)[prpLfieldIndex].value = payObjectValue;
	setPrpLpayObjectInfoPayAmount();
}
/**
 * 设置赔付对象的金额
 * @return
 */
function setPrpLpayObjectInfoPayAmount(){
	var prpLpayObjectInfoPayAmount = document.getElementsByName("prpLpayObjectInfoPayAmount");
	for(var i=0;i<prpLpayObjectInfoPayAmount.length;i++){
		prpLpayObjectInfoPayAmount[i].value = 0;
	}
	var prpLpersonLossPayObjectSerialNo = document.getElementsByName("prpLpersonLossPayObjectSerialNo");
	for(var i=0;i<prpLpersonLossPayObjectSerialNo.length;i++){
		if(prpLpersonLossPayObjectSerialNo[i].value!=""){
			var payObjectValue = prpLpersonLossPayObjectSerialNo[i].value.split(";");
			for(var j =0;j<payObjectValue.length;j++){
				var payObjectTemp = payObjectValue[j].split(":");
				prpLpayObjectInfoPayAmount[parseInt(payObjectTemp[0])].value =parseFloat(payObjectTemp[1]) + parseFloat(prpLpayObjectInfoPayAmount[parseInt(payObjectTemp[0])].value);
			}
		}
		
	}
}
/**数据提交后，设置不是隐藏*/
function saveFromUnDisabled(){
	$(":input[name='prpLpersonLossPaymentType']").each(function (){
		setPaymentTypeReadonly(this,false);
		setPaymentFractureReadonly(this,false);
	});
	$(":input[name='prpLpersonLossSex']").each(function(){
		$(this).attr("disabled",false);
	});
}