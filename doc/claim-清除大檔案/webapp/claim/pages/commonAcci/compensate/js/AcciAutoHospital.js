/**创建求XMLHttpRequest对象*/
function createRequest(field,codeType,relation) {
	var hospitalCode = "";
	var hospitalName = "";
	if(codeType=="codeName"){
		hospitalName = field.value;
	}else{
		hospitalCode = field.value;
	}
	var url = contextRootPath+"/common/autoHospitalAction.do";
     $.ajax({
		type:"get",
 		url:url,
 		cache:false,
 		dataType:"text",
		data:"hospitalCode="+hospitalCode+"&hospitalName="+hospitalName,
		success:function(data){
			if (data.length>0){
		        popdiv(data,field,codeType,relation); //添加option选项
		    }else {
		        var odiv = document.getElementById("hospitalList");
		        odiv.innerHTML = "";
		        odiv.style.display = "none";
		    }
		}
	});
}
/**
	*发送请求，获取下一个列表框的列表数据
	*参数oValue为当前列表框的选中值，此值作为下一个列表框的parentID号
	*codeType=codeCode,codeName;
	*codeType =0,1,2;
	*获取医院的列表
**/
var fieldIndex = 0;
function getHospital(field,codeType,relation){
    if (field.value.length >= 1) {
    	fieldIndex = getElementIndexInForm(document.forms[0], field);
        createRequest(field,codeType,relation);
    }else{
    	//隐藏弹出的DIV
        hidePopdiv();
    }
}

/**弹出DIV显示列表*/
function popdiv(stxt,field,codeType,relation){
    var odiv = document.getElementById("hospitalList");
    odiv.style.left = findPosX(field)-3;
    odiv.style.top = findPosY(field)-5;
    if (stxt!= "") {
    	if(codeType=="paymentType"||codeType=="paymentType1"||codeType=="paymentType2"){
    		odiv.style.width = "50px";
    		odiv.innerHTML = getlistringType(stxt,codeType,relation);
    	}else{
    		odiv.style.width = "400px";
    		odiv.innerHTML = getlistring(trim(stxt),codeType,relation);
    	}
        odiv.style.display = "block";
    }else {
        hidePopdiv(); //隐藏弹出的DIV
    }
}
/**定位div的内容*/
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
/**定位div的内容*/
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
/**隐藏弹出的DIV*/
function hidePopdiv(){
    var odiv = document.getElementById("hospitalList");
    if(odiv.style.display!="none"){
	    odiv.innerHTML = "";
	    odiv.style.display = "none";
    }
}

/**获取列表的HTML代码*/
function getlistring(stxt,codeType,relation) {
    var jsonObj = eval(stxt); // JSON字符串转JSON对象
    var str = "<ul id='ultxt' style='list-style-type:none;padding-left:0;margin-left:0'>";
    var s1 = "<li style='padding-left:5px;padding-right:5px' onmousemove=\"limouseover(this)\" onmouseout=\"limouseout(this)\" onclick=\"liselect(this,'"+codeType+"','"+relation+"')\">";
    var s2 = "</li>"
    for (i = 0; i < jsonObj.length; i++) {
        str = str + s1 + jsonObj[i].hospitalCode+"-"+jsonObj[i].hospitalName + s2
    }
    str = str + "</ul>"
    return str;
}
/**选择医院后，赋值*/
function liselect(obj,codeType,relation) {
	var openerFm = window.document.forms[0];
	var relations = new Array();
	if (relation.indexOf(",") > -1) {
		relations = relation.split(",");
	} else {
		relations[0] = relation;
	}

	var value = "";
	var rowValues = new Array();
	var selectedCount = 0;
	var selectedValue = obj.innerText;
	if (selectedValue.indexOf("-") > -1) {
		rowValues = selectedValue.split("-");
	} else {
		rowValues[0] = selectedValue;
	}
	var relationsCount = relations.length;
	for ( var i = 0; i < relationsCount; i++) {
		relations[i] = trim(relations[i]);
		if (relations[i] == null || relations[i] == "") {
			continue;
		}
		value = rowValues[i];
		if (i >= rowValues.length) {
			break;
		}
		var field = null;
		var relation = parseInt(relations[i], 10);
		if (isNaN(relation)) {
			field = eval("openerFm." + relations[i]);
		} else {
			field = openerFm.elements[fieldIndex + relation];
			if(relation==0){
				field.focus();
			}
		}
		field.value = value;
		setCacheValue(field);
	}
    document.getElementById("hospitalList").style.display = "none";
}
/**鼠标移入后修改颜色*/
function limouseover(obj) {
	obj.style.background="Blue";
	obj.style.color="#FFFFFF";
}
/**鼠标移出后修改颜色*/
function limouseout(obj) {
    obj.style.background="";
	obj.style.color="";
}
/**验证医院是否存在*/
function isHospital(field,codeType){
	setTimeout(function(){
		var hospitalCode = "";
		var hospitalName = "";
		if(field.value==""){
			return false;
		}
		if(getCacheValue(field)==field.value){
			return false;
		};
		if(codeType=="codeName"){
			hospitalName = field.value;
		}else{
			hospitalCode = field.value;
		}
		var url = contextRootPath+"/common/verificationHospital.do";
	     $.ajax({
			type:"get",
	 		url:url,
	 		cache:false,
	 		dataType:"text",
			data:"hospitalCode="+hospitalCode+"&hospitalName="+hospitalName,
			success:function(data){
				if ("true"!=trim(data)){
			       field.value = "";
			    }
			}
		});
	},1000)
}

/**根据承保范围查询费用类别*/
function queryPaymentType(field,codeType){
	var $tr = $(field).parents("tr[name='prpLpersonFeeLossPaymentTr']");
	var $contractingScope = $tr.find(":input[name='prpLpersonLossContractingScope']");
	var isCreate = true;
	//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 START
//	var $kindCode = $tr.find(":input[name='prpLpersonLossKindCode']");
//	if($kindCode.val()=="TR47"){
//		//alert("stop from queryPaymentType");
//		//return
//	};
	//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 END
	if($contractingScope.val()==""){
		isCreate = false;
	}
	if(isCreate&&codeType=="paymentType1"){
		if($tr.find(":input[name='prpLpersonLossPaymentType']").val()==""){
			isCreate = false;
		}
	}
	if(isCreate&&codeType=="paymentType2"){
		if($tr.find(":input[name='prpLpersonLossPaymentType1']").val()==""){
			isCreate = false;
		}
	}
    if (isCreate) {
    	fieldIndex = getElementIndexInForm(document.forms[0], field);
    	setTimeout(function(){
    		createRequestType(field,codeType);
    	},100);
    }else{
    	//隐藏弹出的DIV
        hidePopdiv();
    }
}

/**创建求XMLHttpRequest对象*/
function createRequestType(field,codeType) {
	var url = contextRootPath+"/common/autoPaymentTypeAction.do";
	var $tr = $(field).parents("tr[name='prpLpersonFeeLossPaymentTr']");
	var contractingScope = $tr.find(":input[name='prpLpersonLossContractingScope']").val();
	var paymentType = $tr.find(":input[name='prpLpersonLossPaymentType']").val();
	var paymentType1 = $tr.find(":input[name='prpLpersonLossPaymentType1']").val();
	var paymentType2 = $tr.find(":input[name='prpLpersonLossPaymentType2']").val();
	$.ajax({
		type:"get",
 		url:url,
 		cache:false,
 		dataType:"json",
 		data:"contractingScope="+contractingScope+"&paymentType="+paymentType+"&paymentType1="+paymentType1+"&paymentType2="+paymentType2+"&codeType="+codeType,
		success:function(data){
		if (data.message==undefined||data.message==null){
			popdiv(data,field,codeType); //添加option选项
	    }else {
	        var odiv = document.getElementById("hospitalList");
	        odiv.innerHTML = "";
	        odiv.style.display = "none";
	    }
		}
	});
}

/**获取列表的HTML代码*/
function getlistringType(jsonObj,codeType) {
    var str = "<ul id='ultxt' style='list-style-type:none;padding-left:0;margin-left:0'>";
    var s1 = "<li style='padding-left:5px;padding-right:5px' onmousemove=\"limouseover(this)\" onmouseout=\"limouseout(this)\" onclick=\"liselectType(this,'"+codeType+"')\"";
    var s2 = "</li>"
    for (i = 0; i < jsonObj.length; i++) {
    	str += s1+" typ='"+jsonObj[i].type+"' typ1='"+jsonObj[i].type1+"' typ2='"+jsonObj[i].type2+"' content='"+jsonObj[i].content+"' injuryGrade='"+jsonObj[i].injuryGrade+"' paymentRate='"+jsonObj[i].paymentRate+"'>";
    	if(codeType=="paymentType"){
    		 str = str + jsonObj[i].type + s2;
    	}else if(codeType=="paymentType1"){
    		str = str  + jsonObj[i].type1 + s2;
    	}else if(codeType=="paymentType2"){
    		str = str + jsonObj[i].type2 + s2;
    	}
    }
    str = str + "</ul>";
    return str;
}
/**选择显示的内容后，赋值*/
function liselectType(obj,codeType) {
	var openerFm = window.document.forms[0];
	var field = openerFm.elements[fieldIndex];
	var $tr = $(field).parents("tr[name='prpLpersonFeeLossPaymentTr']");
	var cacheValue = getCacheValue(field);
	var selectedValue = obj.innerText;
	if(cacheValue != selectedValue){
		clearPaymentType(field,codeType);
	}
	if(codeType=="paymentType2"){
		var $paymentContent = $tr.find(":input[name='prpLpersonLossPaymentContent']");
		var $paymentType = $tr.find(":input[name='prpLpersonLossPaymentType']");
		if($paymentType.val()=="03"){
			$paymentContent.val($(obj).attr("content")+$(obj).attr("injuryGrade")+"級");
		}else{
			$paymentContent.val($(obj).attr("content"));
		}
		$tr.find(":input[name='prpLpersonLossPaymentRate']").val($(obj).attr("paymentRate"));
	}
	field.value = selectedValue;
	setCacheValue(field);
	hidePopdiv();
}
/**清除后面的内容*/
function clearPaymentType(field,codeType){
	var $tr = $(field).parents("tr[name='prpLpersonFeeLossPaymentTr']");
	if(codeType=="paymentType"){
		$tr.find(":input[name='prpLpersonLossPaymentType']").val("");
		$tr.find(":input[name='prpLpersonLossPaymentType1']").val("");
		$tr.find(":input[name='prpLpersonLossPaymentType2']").val("");
		$tr.find(":input[name='prpLpersonLossPaymentContent']").val("");
	}else if(codeType=="paymentType1"){
		$tr.find(":input[name='prpLpersonLossPaymentType1']").val("");
		$tr.find(":input[name='prpLpersonLossPaymentType2']").val("");
	}else{
		$tr.find(":input[name='prpLpersonLossPaymentType2']").val("");
	}
	$tr.find(":input[name='prpLpersonLossPaymentRate']").val("0");
	$tr.find(":input[name='prpLpersonLossPaymentContent']").val("");
	$tr.find(":input[name='prpLpersonLossNotHospitalDays']").val("");
	$tr.find(":input[name='prpLpersonLossFractureSite']").val("");
	$tr.find(":input[name='prpLpersonLossFractureDegree']").val("");
	$tr.find(":input[name='prpLpersonLossSumRealPay']").val("");
	$tr.find(":input[name='prpLpersonLossSumRealPayNTD']").val("");
}

/**验证给付类别是否存在*/
function isPaymentType(field,codeType){
	setTimeout(function(){
		checkpaymentType(field,codeType);
		var $tr = $(field).parents("tr[name='prpLpersonFeeLossPaymentTr']");
		var $contractingScope = $tr.find(":input[name='prpLpersonLossContractingScope']");
		var $paymentType = $tr.find(":input[name='prpLpersonLossPaymentType']");
		var $paymentType1 = $tr.find(":input[name='prpLpersonLossPaymentType1']");
		var $paymentType2 = $tr.find(":input[name='prpLpersonLossPaymentType2']");
		var isCreate = true;
		if(getCacheValue(field)==field.value){
			return false;
		};
		if($contractingScope.val()==""){
			isCreate = false;
		}
		if(isCreate&&codeType=="paymentType1"){
			if($paymentType.val()==""){
				isCreate = false;
			}
		}
		if(isCreate&&codeType=="paymentType2"){
			if($paymentType1.val().value==""){
				isCreate = false;
			}
		}
		if(!isCreate){
			return isCreate;
		}
		if(field.value==""){
			return;
		}
		var url = contextRootPath+"/common/verificationPaymentType.do";
		$.ajax({
			type:"get",
	 		url:url,
	 		cache:false,
	 		dataType:"json",
	 		data:"contractingScope="+$contractingScope.val()+"&paymentType="+$paymentType.val()+"&paymentType1="+$paymentType1.val()+"&paymentType2="+$paymentType2.val()+"&codeType="+codeType,
			success:function(data){
				if (data.message=="true"){
					setCacheValue(field);
					if(codeType=="paymentType2"){
						setPaymentType2(data,field);
						checkpaymentType(field,codeType);
					}
			    }else{
			    	clearPaymentType(field,codeType);
			    	checkpaymentType(field,codeType);
			    }
			}
		});
	},1000)
}

/**选择给付对象2时，设置给付说明*/
function setPaymentType2(data,field){
	var $tr = $(field).parents("tr[name='prpLpersonFeeLossPaymentTr']");
	var $paymentContent = $tr.find(":input[name='prpLpersonLossPaymentContent']");
	var $paymentType = $tr.find(":input[name='prpLpersonLossPaymentType']");
	if($paymentType.val()=="03"){
		$paymentContent.val(data.result[0].content+data.result[0].injuryGrade+"級");
	}else{
		$paymentContent.val(data.result[0].content);
	}
	$tr.find(":input[name='prpLpersonLossPaymentRate']").val(data.result[0].paymentRate);
}

/**设置缓存值*/
function setCacheValue(field){
	$(field).attr("cacheValue",field.value);
}
/**获取缓存的值*/
function getCacheValue(field){
	return $(field).attr("cacheValue");
}
/**隐藏div内容*/
$(function(){
	$("div").click(hidePopdiv);
});