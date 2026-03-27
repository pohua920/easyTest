/****************************************************************************
 * DESC       :MulLine.js, don't modify. 
 * CREATEDATE :2006-09-11
 * MODIFYLIST :   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 *
 ****************************************************************************/

var recentDeletedRowNo = 0;
var recentDeletedTBody = null;
var globalindex = new Array();

/**
 \u8fd4\u56de\u6700\u8fd1\u88ab\u5220\u9664\u7684\u884c\u7684\u5e8f\u53f7,\u5982\u679c\u662f\u4e00\u6b21\u5220\u9664\u591a\u884c\uff0c\u5219\u4e3a\u5220\u9664\u7684\u7b2c\u4e00\u884c\u7684\u5e8f\u53f7
 */
function getRecentDeletedRowNo(){
  return recentDeletedRowNo;
}

/**
 \u76f4\u63a5\u8c03\u7528\u63d2\u5165\u51fd\u6570,\u4ec5\u4f9b\u9ad8\u7ea7\u7528\u6237\u4f7f\u7528
 */
function directInsertRow(pageCode,dataPageCode,field){
   return private_insertRow(pageCode,dataPageCode,field);
}

/**
 \u76f4\u63a5\u8c03\u7528\u5220\u9664\u51fd\u6570,\u4ec5\u4f9b\u9ad8\u7ea7\u7528\u6237\u4f7f\u7528
 */
function directDeleteRow(field,pageCode,pageDataRowsCount,controlRowsCount){
   return private_deleteRow(field,pageCode,pageDataRowsCount,controlRowsCount);
}

/**
 \u76f4\u63a5\u8c03\u7528\u8bbe\u7f6e\u989c\u8272\u51fd\u6570,\u4ec5\u4f9b\u9ad8\u7ea7\u7528\u6237\u4f7f\u7528
 */
function directSetRowColor(pageCode,dataPageCode,index,color){
  private_setRowColor(pageCode,dataPageCode,index,color);
}
/**
 \u63d2\u5165\u51fd\u6570,Framework\u4f7f\u7528
 */
function insertRow(pageCode,field){
  var obj;
  var index;

  //Call beforeInsertRow of pageCode
  obj = eval("window.beforeInsert" + upperCaseFirstChar(pageCode));
  if(obj != null)  {
    obj.apply(this,arguments);
  }

  //call realy insertRow of pageCode
  obj = eval("window.insert" + upperCaseFirstChar(pageCode));
  if(obj != null)  {
    index=obj.apply(this,arguments);
  }
  else{  //default method
    index=directInsertRow(pageCode,pageCode+"_Data",field);
  }

  //Call afterInsertRow of pageCode
  obj = eval("window.afterInsert" + upperCaseFirstChar(pageCode));
  if(obj != null){
    obj.apply(this,arguments);
  }


//  directSetRowColor(pageCode,pageCode+"_Data",index,"#EEECFA")
}

/**
 \u5220\u9664\u51fd\u6570,Framework\u4f7f\u7528
 */
function deleteRow(field,pageCode,pageDataRowsCount,controlRowsCount){
  var obj;
  var index;
  if(pageDataRowsCount==undefined){
    pageDataRowsCount=1;
  }
  if(controlRowsCount==undefined){
    controlRowsCount=1;
  }
  //Call beforeDeleteRow of pageCode
  obj = eval("window.beforeDelete" + upperCaseFirstChar(pageCode));
  if(obj != null)  {
    obj.apply(this,arguments);
  }

  //call realy insertRow of pageCode
  obj = eval("window.delete" + upperCaseFirstChar(pageCode));
  if(obj != null){
    index = obj.apply(this,arguments);
  }
  else{  //\u5982\u679c\u6ca1\u6709\u81ea\u5b9a\u4e49\u5220\u9664\u65b9\u6cd5\u5219\u8c03\u7528\u9ed8\u8ba4\u7684\u5220\u9664\u65b9\u6cd5
    index = directDeleteRow(field,pageCode,pageDataRowsCount,controlRowsCount);
  }

  //Call afterDeleteRow of pageCode
  obj = eval("window.afterDelete" + upperCaseFirstChar(pageCode));
  if(obj != null)  {
    obj.apply(this,arguments);
  }
}

/**
  \u5728\u8868\u683c\u4e0b\u65b9\u6dfb\u52a0\u4e00\u7ec4\u6570\u636e\uff0c\u7981\u6b62\u975e\u672c\u6a21\u5757\u8c03\u7528
  \u53c2\u6570\u4e3a\u9875\u4ee3\u7801\u540d\u79f0\u548c\u9875\u539f\u59cb\u6570\u636e\u4ee3\u7801\u540d\u79f0
  \u4f8b:insertRow("Engage","Engage_Data");
  \u8fd4\u56de\u63d2\u5165\u884c\u7684\u5e8f\u53f7\uff08\u4ece1\u5f00\u59cb\uff09
  */
function private_insertRow(pageCode,dataPageCode,field){
  var oTBODY = field;   
  while (oTBODY!=null && oTBODY.tagName != "TABLE" && oTBODY.id!=pageCode)  {
    oTBODY = oTBODY.parentElement;
  } 
  oTBODY=oTBODY.tBodies[0];
  var oTBODYData = document.getElementById(dataPageCode).tBodies.item(0);
  var rowsCount = oTBODYData.rows.length;
  for(var i=0;i<rowsCount;i++){
    oTBODY.appendChild(oTBODYData.rows[i].cloneNode(true));  
    var rowData = oTBODY.rows[oTBODY.rows.length-1];    
    var elements=rowData.getElementsByTagName("INPUT");
    for(var j=0;j<elements.length;j++){   
      var element = elements[j];
      if(element!=null){
        //jcv_custom_initElement(element);
      } 
    }
    elements=rowData.getElementsByTagName("SELECT");
    for(var j=0;j<elements.length;j++){   
      var element = elements[j];
      if(element!=null){
        //jcv_custom_initElement(element);
      } 
    }
    elements=rowData.getElementsByTagName("TEXTAREA");
    for(var j=0;j<elements.length;j++){   
      var element = elements[j];
      if(element!=null){
        //jcv_custom_initElement(element);
      } 
    }
  } 
 
  return rowsCount;
}

/**
  \u5220\u9664\u63a7\u5236\u6309\u94ae\u63a7\u5236\u7684\u884c\uff0c\u7981\u6b62\u975e\u672c\u6a21\u5757\u8c03\u7528
  \u5b57\u6bb5\uff0c\u9875\u540d\u79f0\uff0c\u6570\u636e\u9875\u4e2d\u63a7\u5236\u6309\u94ae\u7684\u4e2a\u6570\uff0c\u6570\u636e\u9875\u4e2d\u6bcf\u4e2a\u63a7\u5236\u6309\u94ae\u7684\u63a7\u5236\u7684TR\u7684\u4e2a\u6570
  \u8fd4\u56de\u5220\u9664\u884c\u7684\u5e8f\u53f7\uff08\u4ece1\u5f00\u59cb\uff09
 */
function private_deleteRow(field,pageCode,pageDataRowsCount,controlRowsCount){
  var oTBODY = field;  
  while (oTBODY!=null && oTBODY.parentElement!=null && oTBODY.tagName != "TBODY" && oTBODY.parentElement.id!=pageCode)	{
  	oTBODY = oTBODY.parentElement;
  }

  var tempElements = oTBODY.getElementsByTagName(field.tagName);
  var tempElementsCount = tempElements.length; 

  var order = 0;
  for (var i = 0; i < tempElementsCount; i++) {
      if(tempElements[i].name == field.name){
      	order++;
      }
      if(tempElements[i] == field){
      	break;
      }
  } 


  order = order - pageDataRowsCount;  //\u53bb\u6389\u9690\u542b\u57df\u4e2d\u7684\u63a7\u5236\u6309\u94ae\u7684\u4e2a\u6570

  
  for(var i=0;i<controlRowsCount;i++)  {
    oTBODY.removeChild(oTBODY.rows[order*controlRowsCount]);
  }
  //for(var i=0;i<controlRowsCount;i++)  {
  //	oTBODY.removeChild(oTBODY.rows[order]);
 // }
  
  recentDeletedTBody = oTBODY;
  recentDeletedRowNo = order;
  return order;
}

/**
 * \u8bbe\u7f6e\u4e00\u884c\u7684\u989c\u8272
 */
function private_setRowColor(pageCode,dataPageCode,index,color){
  var i = 0;
  var command = "";
  var elements = getTableElements(dataPageCode);
  var elementsCount = elements.length;
  for(i=0;i<elementsCount;i++){
    command = "document.getElementsByName('" + elements[i].name + "')["+index+"].style.backgroundColor = color;"
    eval(command);
  }
} 

function getOrderForMulLine(pageCode,field,parentPageCode){
  var oTable = private_getMulLineTBody(pageCode,field).parentElement;
  var oTBODY = private_getMulLineTBody(parentPageCode,field)
  var tempTables = oTBODY.getElementsByTagName("TABLE");
  var index = 0;
  for (var i = 0; i < tempTables.length; i++) {
    if(tempTables[i].id == pageCode){
      index++;
    }
    if(tempTables[i] == oTable){
      break;
    }
  } 
  var tempField = oTBODY.getElementsByTagName("INPUT");
  var order = 0;
  var value;
  for (var i = 0; i < tempField.length; i++) {
    if(tempField[i].name != field.name){
      continue;
    }
    order++;
    if(order==index){ 
      return getElementOrder(tempField[i]); 
    }
  }  
  return -1;
}
function getFirstOrderForMulLine(pageCode,field,elementName,tagName){
  var oTable = private_getMulLineTBody(pageCode,field).parentElement;
  var tempField = oTable.getElementsByTagName(tagName); 
  for (var i = 0;i<tempField.length;i++) {
    if(tempField[i].name == elementName){      
      return getElementOrder(tempField[i]); 
    }
  } 
  return -1;
}
function getLastOrderForMulLine(pageCode,field,elementName,tagName){
  var oTable = private_getMulLineTBody(pageCode,field).parentElement;
  var tempField = oTable.getElementsByTagName(tagName); 
  for (var i = tempField.length-1; i >=0; i--) {
    if(tempField[i].name == elementName){      
      return getElementOrder(tempField[i]); 
    }
  } 
  return -1;
}  
function getInnerOrderForMulLine(pageCode,field){
  var oTable = private_getMulLineTBody(pageCode,field).parentElement;
  var tempField = oTable.getElementsByTagName(field.tagName); 
  var order=0;
  for (var i = 0;i<tempField.length;i++) {
    if(tempField[i].name == field.name){    
      order++;
    }
    if(tempField[i] == field){    
      return order;
    }
  } 
  return -1;
}
function getTableOrderForMulLine(parentPageCode,pageCode,object){
  var oTable = private_getMulLineTBody(pageCode,object).parentElement;
  var oParentTable = private_getMulLineTBody(parentPageCode,oTable);
  var tempTables = oParentTable.getElementsByTagName("TABLE"); 
  var order = 0;  
  for (var i = 0; i < tempTables.length; i++) {
    if(tempTables[i].id == oTable.id){
      order++;
    }else{
      continue;
    } 
    if(tempTables[i] == oTable){      
      return order;
    }
  } 
  return -1;
} 

function private_getMulLineTBody(pageCode,object){
  var oTBODY = object;  
  while (oTBODY!=null && oTBODY.parentElement!=null && oTBODY.tagName != "TBODY" && oTBODY.parentElement.id!=pageCode)  {
    oTBODY = oTBODY.parentElement;
  }
  return oTBODY;
}

function getRecentDeletedTBody(){
  return recentDeletedTBody;
}

function insertRowForStruts2(pageCode,field,index){
	
	index = index==null?0:index;
	var gi = parseInt(globalindex[pageCode]);
	if(isNaN(gi)){
	    globalindex[pageCode] = index;
	    gi = index;
	}
	var tbody = document.all(pageCode+"_Data").tBodies.item(0);
	var rowsCount = tbody.rows.length;
  	for(var i=0;i<rowsCount;i++){
		var s  = tbody.rows[i].outerHTML;
		while(s.indexOf("_[0]")>-1){
			s = s.replace("_[0]","["+ gi +"]");
		}
		
		var tdiv = document.createElement("div");
		tdiv.innerHTML = "<table id=tmpTable>"+s+"</table>";
		tdiv.style.display = "none";
		
		document.appendChild(tdiv);
		
		var row = document.all("tmpTable").tBodies.item(0).rows[0].cloneNode(true);
		document.removeChild(tdiv);
		document.all(pageCode).tBodies.item(0).appendChild(row);
    }
    gi++;
	globalindex[pageCode] = gi;

}