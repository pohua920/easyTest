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
var codeSelectRealCondition = null;
var codeSelectTypeParam = null;
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
 * @param otherCondition mode(key=value,key=value,key=fm.policyNo.value,key=fm.itemkindNo[].value)
 * @param callBackMethod callback
 * @param getDataMethod get data method,when codeType is custom
 */
function code_CodeSelect(field, codeType, codeRelation, isClear, otherCondition, typeParam, varParamField, callBackMethod, getDataMethod) {
    if (event.type == "keyup") {
        var charCode = window.event.keyCode;
        if (!(charCode == 13 & window.event.ctrlKey)) {
            return;
        }
    }
    if(varParamField != undefined && trim(varParamField)!= ""){
    	var woundCode  = document.getElementsByName(varParamField)[0].value;
    	typeParam = getWoundType(trim(woundCode));
    }
    inCodeQuery = true; 
    private_Code_CallServiceByDialog(field, "select", codeType, codeRelation, isClear, otherCondition, typeParam, callBackMethod, getDataMethod);
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
function code_CodeQuery(field, codeType, codeRelation, isClear, otherCondition, typeParam, callBackMethod, getDataMethod) {
    if (event.type == "keyup") {
        var charCode = window.event.keyCode;
        if (!(charCode == 13 & window.event.ctrlKey)) {
            return;
        }
    }
    inCodeQuery = true; 
    private_Code_CallServiceByDialog(field, "query", codeType, codeRelation, isClear, otherCondition, typeParam, callBackMethod, getDataMethod);    
    inCodeQuery = false;
}
/**
 * only for parse params,set the value into public vars.
 */
function private_Code_ParseParams(field, codeMethod, codeType, codeRelation, isClear, otherCondition, typeParam, callBackMethod, getDataMethod) {
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
//    if (isClear == "Y"||isClear == "y") {
//        var relationsCount = relations.length;
//        for (var i = 0; i < relationsCount; i++) {
//            try {
//                var field = null;         
//                var relation = parseInt(relations[i], 10);
//                if(isNaN(relation)){ 
//                    field = eval("document.forms[0]."+relations[i]);
//                    if(field.length>1){
//                        field = field[elementOrder];
//                    }
//                }else{
//                    field = document.forms[0].elements[fieldIndex + relation];
//                }                             
//                field.value="";
//            }
//            catch (E) {
//            }
//        }
//    } 
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
    if(otherCondition==undefined || otherCondition=="null"){
        otherCondition="";
    }
    if(typeParam==undefined || typeParam=="null"){
        typeParam="";
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
    codeSelectRealCondition = realCondition;
    codeSelectTypeParam = typeParam;
    codeSelectCallBackMethod=callBackMethod;
    codeSelectGetDataMethod=getDataMethod;
}

function private_Code_CallServiceByDialog(field, codeMethod, codeType, codeRelation, isClear, otherCondition, typeParam, callBackMethod, getDataMethod) {
    private_Code_ParseParams(field, codeMethod, codeType, codeRelation, isClear, otherCondition, typeParam, callBackMethod, getDataMethod);
    var url = contextRootPath + "/common/PrepareCodeInput.do";
    var obj = new Object();  
    window.prototype=obj;
    obj.pageNo="1";
    obj.pageSize="50";
    obj.fieldIndex=codeSelectFieldIndex;
    obj.fieldValue=codeSelectFieldValue;
    obj.codeMethod=codeSelectCodeMethod;
    obj.codeType=codeSelectCodeType;
    obj.codeRelation=codeSelectCodeRelation;
    obj.isClear=codeSelectIsClear;
    obj.otherCondition=codeSelectRealCondition;
    obj.typeParam=codeSelectTypeParam;
    obj.callBackMethod=codeSelectCallBackMethod;
    obj.getDataMethod=codeSelectGetDataMethod;
    obj.elementOrder=codeSelectElementOrder;
    obj.elementLength=codeSelectElementLength;
    var handle = window.showModalDialog(url,window,"resizable:yes;dialogHide:yes;help:no;status:no;scroll:yes;dialogWidth:350px;dialogHeight:420px");
    
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
function code_CodeChange(field, codeType, codeRelation, isClear, otherCondition, typeParam, varParamField,callBackMethod, getDataMethod) {
    var codeMethod = "change";
    if(varParamField != undefined && trim(varParamField)!= ""){
    	var woundCode  = document.getElementsByName(varParamField)[0].value;
    	typeParam = getWoundType(trim(woundCode));
    }
    private_Code_ParseParams(field, codeMethod, codeType, codeRelation, isClear, otherCondition, typeParam, callBackMethod, getDataMethod);
    var url = contextRootPath + "/common/changeCodeInput.do";     
    var pars="actionType=query";
    pars=pars+"&fieldIndex="+codeSelectFieldIndex;
    pars=pars+"&fieldValue="+codeSelectFieldValue;
    pars=pars+"&codeMethod="+codeSelectCodeMethod;
    pars=pars+"&codeType="+codeSelectCodeType;
    pars=pars+"&codeRelation="+codeSelectCodeRelation;
    pars=pars+"&isClear="+codeSelectIsClear;
    pars=pars+"&otherCondition="+codeSelectRealCondition;
    pars=pars+"&typeParam="+codeSelectTypeParam;
    pars=pars+"&callBackMethod="+codeSelectCallBackMethod;
    pars=pars+"&getDataMethod="+codeSelectGetDataMethod; 
    var myAjax = new Ajax.Request(
        url,{method:'get',asynchronous:'false',parameters:pars,onComplete:setFieldValueForCodeChange}
    );
}
/** for query */
function setFieldValue() {
    inCodeQuery = false; 
    var elementOrder = parseInt(document.forms[0].elementOrder.value, 10);
    var elementLength = parseInt(document.forms[0].elementLength.value, 10);
    
    var callerWindowObj = window.dialogArguments;
    var openerFm = callerWindowObj.document.forms[0];
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
    var callerWindowObj = window.dialogArguments;
    var openerFm = callerWindowObj.document.forms[0];
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
	}
/*
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
*/
}

/** only for onchange */
function setFieldValueForCodeChange(originalRequest){
    var inputValue = document.forms[0].elements[codeSelectFieldIndex].value;
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
    if((value == null || value == "") && inputValue != "")
    {
      alert(inputValue + " " + "\u4ee3\u7801\u4e0d\u5b58\u5728\uff01");
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

/**
 * trim
 */
 function trim(str) { 
 	if(str == undefined){
 		return "";
 	}
	return str.replace(/^\s*(.*?)[\s\n]*$/g, '$1'); 
 }
 
 function getWoundType(woundCode){
 	var woundType = "";
 	if("02" == woundCode){
 		woundType = "WoundDamage";
 	}else if("03" == woundCode){
 		woundType = "WoundDead";
 	}else{
 		woundType = "WoundInjure";
 	}
 	return woundType;
 }
 