/**
 * Code Input 
 */
/** vars for codechange */ 
var codeSelectFieldIndex = null;
var codeSelectFieldValue = null;
var codeSelectCodeMethod = null;
var codeSelectCodeType = null;
var codeSelectCodeRelation = null;
var codeSelectIsClear = null;
var codeSelectIsQueryCode = null;
var codeSelectRealCondition = null;
var codeSelectCallBackMethod = null;
var codeSelectGetDataMethod = null;
var codeSelectElementOrder = 0;
var codeSelectElementLength = 1; 
var codeSelectHasSubmit = false;

/**
 * prepare select data,for codeinput
 * @param field
 * @param codeType
 * @param codeRelation split by ","
 * @param isClear
 * @param isQueryCode ????????????????????????????
 * @param otherCondition mode(key=value,key=value,key=fm.policyNo.value,key=fm.itemkindNo[].value)
 * @param callBackMethod callback
 * @param getDataMethod get data method,when codeType is custom
 */
function clearFieldValue() {
    inCodeQuery = false;
    var elementOrder = parseInt(document.forms[0].elementOrder.value, 10);
    var elementLength = parseInt(document.forms[0].elementLength.value, 10);
    //var openerFm = window.dialogArguments[0].parent.document.forms[0];
    //因为 代理人理赔维护这个页面使用上面的window.dialogArguments[0].parent方法为空，获取不到父页面，window.dialogArguments获取父页面的属性
    var openerFm = window.dialogArguments.document.forms[0];
    if (document.forms[0].isClear.value == "Y"||document.forms[0].isClear.value == "y") {
        var relations = new Array();
        if (document.forms[0].codeRelation.value.indexOf(",") > -1) {
            relations = document.forms[0].codeRelation.value.split(",");
        } else {
            relations[0] = document.forms[0].codeRelation.value;
        }
        var fieldIndex = parseInt(document.forms[0].fieldIndex.value, 10);
        var relationsCount = relations.length;
        for (var i = 0; i < relationsCount; i++) {
            relations[i] = trim(relations[i]);
            if(relations[i]==null||relations[i]==""){
                continue;
            }
            var field = null;         
            var relation = parseInt(relations[i], 10);
            if(isNaN(relation)){ 
                field = eval("openerFm."+relations[i]);
                if(elementLength>1){
                    field = field[elementOrder];
                }
            }else{
                field = openerFm.elements[fieldIndex + relation];
            } 
            field.value=""; 
        }
    }
}
function code_CodeSelectForAcciItem(field, codeType, codeRelation, isClear, isQueryCode, otherCondition, callBackMethod, getDataMethod) {
    if (event.type == "keyup") {
        var charCode = window.event.keyCode;
        if (!(charCode == 13 & window.event.ctrlKey)) {
            return;
        }
    }
    var elementOrder = getElementOrder(field)-1;
    var personLossSerialNo = fm.personLossSerialNo[elementOrder].value;
    var count = fm.prpLpersonLossSerialNo.length;
    var index = 0;
    for(var i=0;i<count;i++){
     	if(fm.prpLpersonLossSerialNo[i].value==personLossSerialNo){
     		index = i;
     		break;
     	}
    }
    otherCondition  = otherCondition +'|'+fm.prpLpersonLossKindCode[index].value;;
    inCodeQuery = true; 
    private_Code_CallServiceByDialog(field, "select", codeType, codeRelation, isClear, isQueryCode, otherCondition, callBackMethod, getDataMethod);
    inCodeQuery = false;
}
function code_CodeSelect(field, codeType, codeRelation, isClear, isQueryCode, otherCondition, callBackMethod, getDataMethod) {
    if (event.type == "keyup") {
        var charCode = window.event.keyCode;
        if (!(charCode == 13 & window.event.ctrlKey)) {
            return;
        }
    }
    inCodeQuery = true;
    private_Code_CallServiceByDialog(field, "select", codeType, codeRelation, isClear, isQueryCode, otherCondition, callBackMethod, getDataMethod);
    inCodeQuery = false;
}
/**
 * prepare query data,for codequery,can select many value
 * @param field
 * @param codeType
 * @param codeRelation split by ","
 * @param isClear
 * @param otherCondition mode(key=value,key=value,key=fm.policyNo.value,key=fm.itemkindNo[].value)
 * @param callBackMethod callback
 * @param getDataMethod get data method,when codeType is custom
 */
function code_CodeQuery(field, codeType, codeRelation, isClear, isQueryCode, otherCondition, callBackMethod, getDataMethod) {
    if (event.type == "keyup") {
        var charCode = window.event.keyCode;
        if (!(charCode == 13 & window.event.ctrlKey)) {
            return;
        }
    }
    inCodeQuery = true; 
    private_Code_CallServiceByDialog(field, "query", codeType, codeRelation, isClear, isQueryCode, otherCondition, callBackMethod, getDataMethod);    
    inCodeQuery = false;
}
/**
 * only for parse params,set the value into public vars.
 */
function private_Code_ParseParams(field, codeMethod, codeType, codeRelation, isClear, isQueryCode, otherCondition, callBackMethod, getDataMethod) {
    var elementOrder = getElementOrder(field)-1;
    codeSelectElementLength=getElementCount(field.name);    
    codeSelectElementOrder=elementOrder;
    
    var fieldIndex = getElementIndexInForm(document.forms[0], field);
    var fieldValue = field.value;
    if (fieldValue != null) {
        fieldValue = fieldValue.replace("*", "%");
    }
    var relations = new Array();
    if (codeRelation.indexOf(",") > -1) {
        relations = codeRelation.split(",");
    } else {
        relations[0] = codeRelation;
    }
    var conditions = new Array();
    if(otherCondition==null || otherCondition==undefined ||  otherCondition=="null"){
      otherCondition="";
    } 
    if (otherCondition.indexOf(",") > -1) {
        conditions = otherCondition.split(",");
    } else {
        conditions[0] = otherCondition;
    } 
    var conditionsCount = conditions.length;
    var realCondition = ""; 
    
    for (var i = 0; i < conditionsCount; i++) { 
    	  if(conditions[i]==null||conditions[i]==""){
            continue;
        }
        var equalPos = conditions[i].indexOf("=");
        var condName = "";
        var condStatement = conditions[i];
        if(equalPos>0){
          condName = conditions[i].substring(0,equalPos) + "=";
          condStatement = conditions[i].substring(equalPos+1);
        }
        var condValue = "";
        if(condStatement.indexOf("[]")==-1){
            try{
                if(condStatement.indexOf(".value")>-1){
                  condValue = eval(condStatement);
                }else{
                  condValue = condStatement;
                }
            }catch (E) {
                condValue = condStatement;
            }
        }else{
            var startPos = condStatement.indexOf("[");
            var endPos = condStatement.indexOf("]");
            if(startPos==endPos-1){
                condStatement = condStatement.substring(0,startPos+1) + elementOrder + condStatement.substring(endPos);
            }
            try{
                condValue = eval(condStatement);
            }catch (E) {
                condValue = condStatement;
            }
        }
        realCondition += condName + condValue;
        if(i<conditionsCount-1){
            realCondition+=",";
        }
    }
 
    if(isClear==undefined || isClear=="null"){
        isClear="Y";
    }
    if(isQueryCode==undefined || isQueryCode=="null"){
        isQueryCode="Y";
    }
    if(otherCondition==undefined || otherCondition=="null"){
        otherCondition="";
    }
    if(callBackMethod==undefined || callBackMethod=="null"){
        callBackMethod="";
    }
    if(getDataMethod==undefined || getDataMethod=="null"){
        getDataMethod="";
    } 
    codeSelectFieldIndex= fieldIndex;
    codeSelectFieldValue=fieldValue;
    codeSelectCodeMethod= codeMethod;
    codeSelectCodeType = codeType;
    codeSelectCodeRelation= codeRelation;
    codeSelectIsClear= isClear;
    codeSelectIsQueryCode= isQueryCode;
    codeSelectRealCondition = realCondition;
    codeSelectCallBackMethod=callBackMethod;
    codeSelectGetDataMethod=getDataMethod;
}

function private_Code_CallServiceByDialog(field, codeMethod, codeType, codeRelation, isClear, isQueryCode, otherCondition, callBackMethod, getDataMethod) {
    if(codeType=="PolicyKindCode"){
    	var PolicyBzNo = "";
    	try{
	    	PolicyBzNo = fm.prplCheckPolicyBzNoShow.value;
    		if(PolicyBzNo != null && PolicyBzNo.length>0){
    			otherCondition = otherCondition + "|" + PolicyBzNo;
    		}
    	}catch(ex){}
    }
    //支付对象/责任明细/伤残等级/受损标的特殊处理
    if(codeType=="payObject" || "policyItemCode"==codeType || "InjuryGrade" == codeType || "PolicyItemKindCode" == codeType|| "InjuryItemCode" == codeType){
		var fieldname=field.name;
		var i = 0;
		var findex=0;
		for(i=1;i<fm.all(fieldname).length;i++){
			if( fm.all(fieldname)[i] == field ){
				findex=i;
				break;
			}
		}
		if(codeType=="payObject"){
			var serialNo = fm.prpLchargePayObjectType[findex].value;
			otherCondition = serialNo;
		}else if(codeType == "policyItemCode"){
			var KindCode = fm.prpLclaimLossKindCode[findex].value;
			otherCondition = otherCondition + "|" + KindCode;
		}else if("InjuryGrade" == codeType){		//只有选择了伤残类才能得到伤残等级
			if (findex>0){
                otherCondition = otherCondition +"|"+ fm.prpLpersonLossLiabDetailCode[findex].value;
             }
		}else if("PolicyItemKindCode" == codeType){  //根据保单号和险别得到受损标的
			otherCondition = otherCondition +"|"+ fm.prpLlossDtoKindCode[findex].value;
		}else if("InjuryItemCode" == codeType){  //根据保单号和险别得到受损标的
		    var injuryCode = fm.prpLpersonLossInjuryCode[findex];
		    if(injuryCode.value.length == 0){
		        alert("請先選擇殘廢項目！");
		        injuryCode.focus();
		        return false;
		    }
            otherCondition = otherCondition +"|"+injuryCode.value;
        }
	}
	//add by liuwei at 2011-02-15 从事行业分三级选择 start
	if(codeType=="BusinessSource1"){
	    var index = getElementOrder(field);
	    fm.prpLpersonTraceJobCode2[index-1].value="";
        fm.prpLpersonTraceJobName2[index-1].value="";
        if(fm.prpLpersonTraceJobCode){
          fm.prpLpersonTraceJobCode[index-1].value="";
          fm.prpLpersonTraceJobName[index-1].value="";
        }else{
          fm.prpLpersonJobCode[index-1].value="";
          fm.prpLpersonJobName[index-1].value="";
        }
        otherCondition= otherCondition + "|" + "codecode=";
	}
	if(codeType=="BusinessSource2"){
	    var index = getElementOrder(field);
	    if(fm.prpLpersonTraceJobName1[index-1].value==""){
	        alert(i18n.common.pleaseFirstChooseClassificat);  //请先选择行业大分类!
            fm.prpLpersonTraceJobName1[index-1].focus();
            return;
	    }

	    if(fm.prpLpersonTraceJobCode){
          fm.prpLpersonTraceJobCode[index-1].value="";
          fm.prpLpersonTraceJobName[index-1].value="";
        }else{
          fm.prpLpersonJobCode[index-1].value="";
          fm.prpLpersonJobName[index-1].value="";
        }
	    otherCondition= otherCondition + "|" + "codecode="+fm.prpLpersonTraceJobCode1[index-1].value;
	}
	if(codeType=="BusinessSource"){
	    var index = getElementOrder(field);
	    if(fm.prpLpersonTraceJobName1[index-1].value==""){
	        alert(i18n.common.pleaseFirstChooseClassificat);  //请先选择行业大分类!
            fm.prpLpersonTraceJobName1[index-1].focus();
            return;
	    }else if(fm.prpLpersonTraceJobName2[index-1].value==""){
	        alert(i18n.common.pleaseFirstChooseClassificat);  //请先选择行业大分类!
            fm.prpLpersonTraceJobName2[index-1].focus();
            return;
	    }
	    otherCondition= otherCondition + "|" + "codecode="+fm.prpLpersonTraceJobCode2[index-1].value;
	}
	//add by liuwei at 2011-02-15 从事行业分三级选择 end
	if(codeType=="getInsuranceSurveyor"){
		if(fm.NewComCode.value==""){
			alert(i18n.verifyPrice.pleaseSelectAgency);  //请先选择公估机构！
			fm.NewComCName.focus();
			return;
		}
		otherCondition="newComCode='"+fm.NewComCode.value+"'";
	}
	if(codeType=="portCode"){
		if(fm.countryFlag.value=="1"){
			if(fm.foreignCountryCode.value==""){
				alert(i18n.common.pleaseSelectCountry);  //请先选择国家！
				return false;
			}
			otherCondition=fm.countryFlag.value+","+fm.language.value+","+fm.foreignCountryCode.value;
		}else{
			otherCondition=fm.countryFlag.value+","+fm.language.value;
		}
	}
	if(codeType=="foreignCountryCode"){
		otherCondition=fm.language.value;
	}
    private_Code_ParseParams(field, codeMethod, codeType, codeRelation, isClear, isQueryCode, otherCondition, callBackMethod, getDataMethod);
    var url="/claim/pages/common/pub/QueryCodeInput.jsp";
    var obj = new Object();  
    window.prototype=obj;
    obj.pageNo="1";
    obj.rowsPerPage="20";
    obj.fieldIndex=codeSelectFieldIndex;
    obj.fieldValue=codeSelectFieldValue;
    obj.codeMethod=codeSelectCodeMethod;
    obj.codeType=codeSelectCodeType;
    obj.codeRelation=codeSelectCodeRelation;
    obj.isClear=codeSelectIsClear;
    obj.isQueryCode=codeSelectIsQueryCode;
    obj.otherCondition=codeSelectRealCondition;
    obj.callBackMethod=codeSelectCallBackMethod;
    obj.getDataMethod=codeSelectGetDataMethod;
    obj.elementOrder=codeSelectElementOrder;
    obj.elementLength=codeSelectElementLength;
    var handle = window.showModalDialog(url,window,"dialogHide:yes;help:no;status:no;scroll:yes;dialogWidth:300px;dialogHeight:460px");
    
    try {
        field.focus();
    }
    catch (E) {
    }
    //do after window close
    private_Code_CallBack(codeSelectCallBackMethod);   
    
} 
 
/**
 * code input for data change
 * @param field
 * @param codeType
 * @param codeRelation split by ","
 * @param isClear
 * @param otherCondition mode(key=value,key=value)
 * @param callBackMethod callback
 * @param getDataMethod get data method,when codeType is custom
 */
function code_CodeChange(field, codeType, codeRelation, isClear, isQueryCode, otherCondition, callBackMethod, getDataMethod) {
//    var codeMethod = "change";
//    private_Code_ParseParams(field, codeMethod, codeType, codeRelation, isClear, isQueryCode, otherCondition, callBackMethod, getDataMethod);
//   
//    var url = "/claimCar/processClaimCodeInput.do";     
//    var pars="actionType=query";
//    pars=pars+"&fieldIndex="+codeSelectFieldIndex;
//    pars=pars+"&fieldValue="+codeSelectFieldValue;
//    pars=pars+"&codeMethod="+codeSelectCodeMethod;
//    pars=pars+"&codeType="+codeSelectCodeType;
//    pars=pars+"&codeRelation="+codeSelectCodeRelation;
//    pars=pars+"&isClear="+codeSelectIsClear;
//    pars=pars+"&otherCondition="+codeSelectRealCondition;
//    pars=pars+"&callBackMethod="+codeSelectCallBackMethod;
//    pars=pars+"&getDataMethod="+codeSelectGetDataMethod; 
//    var myAjax = new Ajax.Request(
//        url,{method:'get',asynchronous:'false',parameters:pars,onComplete:setFieldValueForCodeChange}
//    );
	if (event.type == "keyup") {
        var charCode = window.event.keyCode;
        if (!(charCode == 13 & window.event.ctrlKey)) {
            return;
        }
    }
    inCodeQuery = true; 
    private_Code_CallServiceByDialog(field, "change", codeType, codeRelation, isClear, isQueryCode, otherCondition, callBackMethod, getDataMethod);
    inCodeQuery = false;
}
/** for query */
function setFieldValue() {
    inCodeQuery = false; 
    var elementOrder = parseInt(document.forms[0].elementOrder.value, 10);
    var elementLength = parseInt(document.forms[0].elementLength.value, 10);
    //var openerFm = window.dialogArguments[0].parent.document.forms[0];
    //因为 代理人理赔维护这个页面使用上面的window.dialogArguments[0].parent方法为空，获取不到父页面，window.dialogArguments获取父页面的属性
    var openerFm = window.dialogArguments.document.forms[0];
    var relations = new Array();
    if (document.forms[0].codeRelation.value.indexOf(",") > -1) {
        relations = document.forms[0].codeRelation.value.split(",");
    } else {
        relations[0] = document.forms[0].codeRelation.value;
    }
    var fieldIndex = parseInt(document.forms[0].fieldIndex.value, 10);
    
    if (document.forms[0].codeselect.selectedIndex < 0) {
        document.forms[0].codeselect.selectedIndex = 0;
        return false;
    }
    var value = ""; 
    var rowValues = new Array();
    var values = new Array();
    var selectedCount = 0;
    for (var i = 0; i < document.forms[0].codeselect.length; i++) {
        if (document.forms[0].codeselect.options[i].selected == true) {
            rowValues = new Array();
            var selectedValue = document.forms[0].codeselect.options[i].value;
            if (selectedValue.indexOf(FIELD_SEPARATOR) > -1) {
                rowValues = selectedValue.split(FIELD_SEPARATOR);
            } else {
                rowValues[0] = selectedValue;
            }
            values[selectedCount++] = rowValues;
        }
    }
    var relationsCount = relations.length;
    for (var i = 0; i < relationsCount; i++) {
        relations[i] = trim(relations[i]);
        if(relations[i]==null||relations[i]==""){
            continue;
        }
        value = values[0][i];
        if(i >= values[0].length) {
          break;
        }
        for (var j = 1; j < selectedCount; j++) {
            if (i >= values[j].length) {
                value = value + "," + values[j][values.length - 1];
            } else {
                value = value + "," + values[j][i];
            }
        } 
        var field = null;         
        var relation = parseInt(relations[i], 10);
        if(isNaN(relation)){ 
        	 	field = eval("openerFm."+relations[i]);
            if(elementLength>1){
                field = field[elementOrder];
            }
        }else{
            field = openerFm.elements[fieldIndex + relation];
        } 
        field.value=value;
    }
 
    window.close();
}
function cancelFieldValue() {
    inCodeQuery = false;
    var elementOrder = parseInt(document.forms[0].elementOrder.value, 10);
    var elementLength = parseInt(document.forms[0].elementLength.value, 10);
    //var openerFm = window.dialogArguments[0].parent.document.forms[0];
    //因为 代理人理赔维护这个页面使用上面的window.dialogArguments[0].parent方法为空，获取不到父页面，window.dialogArguments获取父页面的属性
    var openerFm = window.dialogArguments.document.forms[0];
    if (document.forms[0].isClear.value == "Y"||document.forms[0].isClear.value == "y") {
        var relations = new Array();
        if (document.forms[0].codeRelation.value.indexOf(",") > -1) {
            relations = document.forms[0].codeRelation.value.split(",");
        } else {
            relations[0] = document.forms[0].codeRelation.value;
        }
        var fieldIndex = parseInt(document.forms[0].fieldIndex.value, 10);
        var relationsCount = relations.length;
        for (var i = 0; i < relationsCount; i++) {
            relations[i] = trim(relations[i]);
            if(relations[i]==null||relations[i]==""){
                continue;
            }
            var field = null;         
            var relation = parseInt(relations[i], 10);
            if(isNaN(relation)){ 
                field = eval("openerFm."+relations[i]);
                if(elementLength>1){
                    field = field[elementOrder];
                }
            }else{
                field = openerFm.elements[fieldIndex + relation];
            } 
            field.value=""; 
        }
    }

    window.close();
}

function fieldOnKeyPress() {
    var charCode = window.event.keyCode;
    if (charCode == 13) { //enter
        setFieldValue();
    } else if (charCode == 27) { //escape 
        cancelFieldValue();     
    } else if (charCode == 38) { //up arrow
        if((fm.codeselect.selectedIndex==0)){
        		var currentPageNo = parseInt(fm.pageNo.value,10);
        		if(codeSelectHasSubmit){
        			  return;
        		}
          	if(currentPageNo>1){
          			locate(currentPageNo-1);
          			codeSelectHasSubmit = true;
          	}
        }
	} else if (charCode == 40) { //down arrow
        if((fm.codeselect.selectedIndex==fm.codeselect.options.length-1)){
        		var currentPageNo = parseInt(fm.pageNo.value,10);
        		if(codeSelectHasSubmit){
        			  return;
        		}
          	if(parseInt(fm.pagesCount.value,10)>currentPageNo){
          			locate(currentPageNo+1);
          			codeSelectHasSubmit = true;
          	}
        }        
    }  
}

/** only for onchange */
function setFieldValueForCodeChange(originalRequest){    
    var elementOrder = getElementOrder(document.forms[0].elements[codeSelectFieldIndex])-1;
    var value = trim(originalRequest.responseText); 
    var relations = new Array();
    if (codeSelectCodeRelation.indexOf(",") > -1) {
        relations = codeSelectCodeRelation.split(",");
    } else {
        relations[0] = codeSelectCodeRelation;
    }
    var relationsCount = relations.length; 
    var fieldIndex = parseInt(codeSelectFieldIndex, 10);
    var values = new Array();
    if (value.indexOf(FIELD_SEPARATOR) > -1) {
        values = value.split(FIELD_SEPARATOR);
    } else {
        values[0] = value;
    }             
    for (var i = 0; i < relationsCount; i++) { 
        relations[i] = trim(relations[i]);
        if(relations[i]==null||relations[i]==""){
            continue;
        }    
        var field = null;         
        var relation = parseInt(relations[i], 10);
        if(isNaN(relation)){ 
            field = eval("document.forms[0]."+relations[i]);
            if(field.length>1){
                field = field[elementOrder];
            }
        }else{
            field = document.forms[0].elements[codeSelectFieldIndex + relation];
        }              
         
    		if(trim(value)!=""){
    				if (i < values.length) { 
								field.value = values[i];
						}
				}else{		
		        if (codeSelectIsClear == "Y"||codeSelectIsClear == "y"){
		            field.value = "";
		        }else if(codeSelectIsClear == "H" || codeSelectIsClear == "h"){
		        		 //do nothing
		        }else if(codeSelectIsClear == "N" || codeSelectIsClear == "n"){
		        		if(i == 0){
		        				field.value = "";
		        		}
		      	}	 
    		} 
    }
  
    private_Code_CallBack(codeSelectCallBackMethod);         
}
 
/**
 * eval callback method
 */ 
function private_Code_CallBack(callBackMethodValue){
    if (callBackMethodValue != "") {
	      var callbackValues = new Array(); 
	      if (callBackMethodValue.indexOf(";") > -1) {
	          callbackValues = callBackMethodValue.split(";");
	      }else{
	        	callbackValues[0] = callBackMethodValue;
	      }
	      var callbackCount = callbackValues.length;
	      for (var i = 0; i < callbackCount; i++) {
	          callbackValues[i] = trim(callbackValues[i]);
	          if(callbackValues[i]==null||callbackValues[i]==""){
	              continue;
	          }
	          eval(callbackValues[i]);
	      }
	  }
}
function addCondition(key,value){
    var retValue = key + "=" + value;
    return retValue;
}

function getElementIndexInForm(form,field){
  var intElementIndex = -1;
  var elementsCount = form.elements.length;
  for(var i=0;i<elementsCount;i++) 
  {
    if(form.elements[i]==field)
    {
      intElementIndex=i;
      break;
    }
  }
  return intElementIndex;
}

function locate(pageNo) {
    if (pageNo < 1) {
        alert("\u5df2\u5230\u7b2c\u4e00\u9875");
        return false;
    }
    if (pageNo > parseInt(getFirstElementValue("pagesCount"), 10)) {
        alert("\u5df2\u5230\u6700\u540e\u4e00\u9875");
        return false;
    }
    if (pageNo == 1 && parseInt(fm.pageNo.value, 10) == 1) {
        alert("\u5df2\u5230\u7b2c\u4e00\u9875");
        return false;
    }
    if (pageNo == 1 && pageNo == parseInt(getFirstElementValue("pagesCount"), 10)) {
        alert("\u5df2\u5230\u6700\u540e\u4e00\u9875");
        return false;
    }
    fm.pageNo.value = pageNo;
    fm.submit();
    return true;
}
function goPage() {
    var pageNo = parseInt(getFirstElementValue("newPageNo"), 10);
    if (isNaN(pageNo)) {
        pageNo = 1;
    }
    if (pageNo > parseInt(getFirstElementValue("pagesCount"), 10)) {
        alert("\u65e0\u6cd5\u8f6c\u5230\u7b2c" + pageNo + "\u9875");
        return false;
    }
    return locate(pageNo);
}

/**
 * ??????????????????????
 * @param fieldName ????????????
 * @since 2005-08-29
 */
function getFirstElementValue(fieldName){
  var field;
  if(getElementCount(fieldName)>0){
    field = document.getElementsByName(fieldName)[0];
  }
  return field.value;
}

/**
 * ????????????????????
 * @param fieldName ??????????
 * @since 2005-08-29
 */
function setSameElementValue(field){ 
  if(getElementCount(field.name)>1){
    var fields = document.getElementsByName(field.name);
    for(var i=0;i<fields.length;i++){
      fields[i].value=field.value;
    }
  } 
}

function changeRowsPerPage(field) {
    var rows = parseInt(field.value, 10);
    if (isNaN(rows)) {
        return false;
    }
    if (rows > 500) {
        alert("\u6bcf\u9875\u4e0d\u5141\u8bb8\u8d85\u8fc7500\u6761");
        return false;
    }
    fm.rowsPerPage.value = rows;
    fm.pageNo.value = 1;
    fm.submit();
    return true;
}

/**
 * ??????Document????element??name????????????????element????????????????0
 * @param fieldName ????????
 * @return ??Document????element??name????????????????element????
 */
function getElementCount(fieldName){
    var count = 0;
    count = document.getElementsByName(fieldName).length;
    return count;
}


/**
* ???????? ????????????,????????????
*/
function dbclickComCodeByProvinceCode(field, eventType, coordinate, queryType, otherConditions, dealType, saveType)//加了个saveType
{
	if(dealType=="Check"){
	fm.nextHandlerCode1.value="";
	fm.nextHandlerName1.value="";
	}else if(dealType=="CertainLoss"){
//modify by zhyi 20110825 fubon-2199 ?????清空下面的人?信息　start
	if("GETBACKEDIT"==saveType){//调度改派时执行
		document.getElementById("nextHandlerCode").value="";
		document.getElementById("nextHandlerName").value="";
	}else{
		document.getElementsByName("nextHandlerCode")[field.id].value="";
		document.getElementsByName("nextHandlerName")[field.id].value="";
	}
	
	//modify by zhyi 20110825 fubon-2199 ?????清空下面的人?信息　end
	//fm.nextHandlerName.value="";
	}
	if(eventType == "dbclick" || eventType == "keyup")
	{
		code_CodeSelect(field,'ComCodeByProvinceCode',coordinate,'Y',queryType,otherConditions);
	}
	else if(eventType == "change")
	{
		code_CodeChange(field,'ComCodeByProvinceCode',coordinate,'Y',queryType,otherConditions);
	}
}

/**
* ???????????? ????????????
*/
function dbclickCheckPerson(field, eventType, coordinate, queryType)
{
	if(trim(fm.prpLscheduleMainWFScheduleObjectID.value) == "")
	{
		alert(i18n.common.pleaseFirstSelectUnit);  //请先选择查勘单位！
		return false;
	}
	if(eventType == "dbclick" || eventType == "keyup")
	{
		code_CodeSelect(field,'CheckPerson',coordinate,'Y',queryType,fm.prpLscheduleMainWFScheduleObjectID.value);
	}
	else if(eventType == "change")
	{
		code_CodeChange(field,'CheckPerson',coordinate,'Y',queryType,fm.prpLscheduleMainWFScheduleObjectID.value);
	}
}
//add by zhyi fubon-2525 20110922
function dbclickComCodeByProvinceCode1(field, eventType, coordinate, queryType, otherConditions, dealType)
{
	if(dealType=="Check"){
	fm.userCode.value="";
	fm.userName.value="";
	}else if(dealType=="CertainLoss"){
//modify by zhyi 20110825 fubon-2199 ?????清空下面的人?信息　start
	document.getElementsByName("userCode")[field.id].value="";
	document.getElementsByName("userName")[field.id].value="";
	//modify by zhyi 20110825 fubon-2199 ?????清空下面的人?信息　end
	//fm.nextHandlerName.value="";
	}
	if(eventType == "dbclick" || eventType == "keyup")
	{
		code_CodeSelect(field,'ComCodeByProvinceCode',coordinate,'Y',queryType,otherConditions);
	}
	else if(eventType == "change")
	{
		code_CodeChange(field,'ComCodeByProvinceCode',coordinate,'Y',queryType,otherConditions);
	}
}

// add by zhyi fubon-2525 20110922
function dbclickCheckPerson1(field, eventType, coordinate, queryType)
{
	if(trim(fm.comCode.value) == "")
	{
		alert(i18n.common.ownershipInstitut);  //请先归属机构！
		fm.comCode.focus();
		return false;
	}
	if(eventType == "dbclick" || eventType == "keyup")
	{
		code_CodeSelect(field,'SelectPerson',coordinate,'Y',queryType,fm.comCode.value);
	}
	else if(eventType == "change")
	{
		code_CodeChange(field,'SelectPerson',coordinate,'Y',queryType,fm.comCode.value);
	}
}
/**
* ???????????????? ????????????
*/
function dbclickCertainLoss(field, eventType, coordinate, queryType,fieldvalue)
{
	/*if(trim(fm.prpLscheduleItemScheduleObjectID.value) == "")
	{
		alert("请先选择定损单位！");
		return false;
	}*/

	var index=0;
	var fields=fm(fieldvalue.name);
	var index = getElementOrder(field)-1;
	var nextNodeNo = "";
	var ObjectIdLength = fm.prpLscheduleItemScheduleObjectID.length;
	if(undefined == ObjectIdLength){
		nextNodeNo = fm.nextNodeNo.value;
		objectId = fm.prpLscheduleItemScheduleObjectID.value;
	} else {
		nextNodeNo = fm.nextNodeNo[index].value;
		objectId = fm.prpLscheduleItemScheduleObjectID[index].value;
	}
	
	if(eventType == "dbclick" || eventType == "keyup")
	{
		code_CodeSelect(field,'certainLossHanderCode',coordinate,'Y',queryType,nextNodeNo + "|" + objectId);
	}
	else if(eventType == "change")
	{
		code_CodeChange(field,'certainLossHanderCode',coordinate,'Y',queryType,nextNodeNo + "|" + objectId);
	}
}