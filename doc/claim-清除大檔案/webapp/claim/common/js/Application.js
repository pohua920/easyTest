/****************************************************************************
 * DESC       ：应用级JavaScript定义--Application Project控制(兼容IE5/NN6)
 * AUTHOR     ：理赔组
 * CREATEDATE ：2013-03-04
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 *
 ************************************************************************************/
 setVerbose(true);
 /** 全局变量bCancel; */
//Validate.js需要
var bCancel = false;

//Validate.js需要的常数开始
 var DB_INT_LENGTH = 64 //数据库位数,即整数长度
 var MAX_INTEGER = Math.pow(2, DB_INT_LENGTH - 1) - 1;
 var MIN_INTEGER = -Math.pow(2, DB_INT_LENGTH - 1);
 var MAX_SMALLINT = Math.pow(2, DB_INT_LENGTH / 4 - 1) - 1;
 var MIN_SMALLINT = -Math.pow(2, DB_INT_LENGTH / 4 - 1);
//Validate.js需要的常数结束

 /**
 * 隐藏输入框
 * @param field 元素
 * @param tableName tableName
 * @return 无
 */
 function hideSubPage(field, tableName) {
	var order = parseInt(getElementOrder(field));
	var obj = document.getElementsByName(tableName)[order - 1];
	obj.style.display = 'none';
 }

 /**
  * 显示输入框
  * @param field 元素
  * @param tableName tableName
  * @param leftMove 坐标左移偏移量，默认值0
  * @return 无
  */
 function showSubPage(field, tableName, divName, evt, leftMove) {
 	if (evt == null) {
 		evt = window.event;
 	}
 	if (leftMove == null) {
 		leftMove = 0;
 	}
 	var order = parseInt(getElementOrder(field));
 	var obj = document.getElementsByName(tableName)[order - 1];

 	var ex = evt.clientX + divName.scrollLeft;
 	var ey = evt.clientY + divName.scrollTop;

 	if (leftMove == 0) {
 		ex = ex - 500;
 	} else {
 		ex = ex - leftMove;
 	}
 	if (ex < 0) {
 		ex = 100;
 	}

 	ey = ey - 20;
 	if (obj != null) {
 		try {
 			obj.style.left = ex;
 			obj.style.top = ey;
 			obj.style.display = '';
 		} catch (E) {}
 	}

 }
 
 /**
  * 显示/隐藏页
  * @param fieldId
  * @param tableId tableId
  * @return 无
  */
 function showTablePage(fieldId, tableId) {
 	var field = document.getElementById(fieldId);
 	var table = document.getElementById(tableId);
 	if (table.style.display == "") {
 		//关闭
 		table.style.display = "none";
 		field.value = "(+)";
 	} else {
 		//打开
 		table.style.display = "";
 		field.value = "(-)";
 	}
 }
 
function errorMessage(strErrMsg)
{
  var strMsg = "\u7cfb\u7d71\u8a0a\u606f:\n\n" + strErrMsg;
  alert(strMsg);
}



function showErrorMessage(field,message){
    showMessage(getSchemaColumn(field.name).desc + message);
    field.select();
    setFocus(field);
}

function checkAllInputOrNotInput(field1,field2){
    if(isEmptyField(field1)&&isEmptyField(field2)){
        return true;
    }

    if((!isEmptyField(field1))&&(!isEmptyField(field2))){
        return true;
    }

    if(isEmptyField(field1)){
        showMessage("\u5f55\u5165" +getSchemaColumn(field2.name).desc +"\u7684\u540c\u65f6\u5fc5\u987b\u5f55\u5165" +getSchemaColumn(field1.name).desc );
        setFocus(field1);
    }else{
        showMessage("\u5f55\u5165" +getSchemaColumn(field1.name).desc +"\u7684\u540c\u65f6\u5fc5\u987b\u5f55\u5165" +getSchemaColumn(field2.name).desc );
        setFocus(field2);
    }

    return false;
}

//////////////////////////////////////////////////////////
////////////////////    Run   ////////////////////////////
//////////////////////////////////////////////////////////

function customBlurHandler(field){
  return true;
}

function setReadonlyWhileHasValue(fields){
    var i =0;
    for(i=0;i<fields.length;i++){
        if(isEmptyField(fields[i])==false){
            fields[i].readOnly = true;
            fields[i].className="readonly";
        }
    }
}


function setOption(selectName,strValue)
{
  if(strValue==null || trim(strValue)=="")
  {
    return;
  }

  var arrayField=strValue.split(GROUP_SEPARATOR);
  var i=0;
  var j=0;
  var intCount = getElementCount(selectName);
  var frm = document.forms("fm");
  if(intCount>1)
  {
    for(j=0;j<intCount;j++)
    {
      frm.all(selectName)[j].options.length = 0;
    }
  }
  else
  {
    frm.all(selectName).options.length = 0;
  }

  while(i<arrayField.length)
  {
    if(intCount>1)
    {
      for(j=0;j<intCount;j++)
      {
        var option=document.createElement("option");
        var arrayTemp=arrayField[i].split(FIELD_SEPARATOR);
        var strFieldName=arrayTemp[0];
        var strFieldValue=unescape(arrayTemp[1]);
        option.value=strFieldName;
        option.text=strFieldValue;

        frm.all(selectName)[j].add(option);
      }
    }
    else
    {
        var option=document.createElement("option");
        var arrayTemp=arrayField[i].split(FIELD_SEPARATOR);
        var strFieldName=arrayTemp[0];
        var strFieldValue=unescape(arrayTemp[1]);
        option.value=strFieldName;
        option.text=strFieldValue;
        frm.all(selectName).add(option);
    }
    i++;
  }
}

function processMenuClick(theHREF)
{
	try{
		//window.parent.parent.frames.head.CurrentPositionSpan.innerHTML=theHREF.title;
		findIframe("fraTitle").setPositionSpan(theHREF.title);
		//top.fraTitle.setPositionSpan(theHREF.title);
		window.parent.findIframe("fraTitle").setPositionSpan(theHREF.title);
	}catch(e){
	}
}

function findIframe(iFrameName,object){
	if(object==undefined){
		object = window;
	}
	var frames = object.frames;
	for(var i=0;i<frames.length;i++){
		if(frames[i].name==iFrameName){
			return frames[i];
		}
	}
	if(object.opener){
		return findIframe(iFrameName,object.opener);
	}
	if(object.dialogArguments){
		return findIframe(iFrameName,object.dialogArguments);
	}
	if(object == object.parent){
		return null;
	}
	return findIframe(iFrameName,object.parent);
}

if(isVerbose()==false){
    document.oncontextmenu=new Function('event.returnValue=false;');
    document.onselectstart=new Function('event.returnValue=false;');
}




function formatFloat(value,count,precision,delimiterChar)
{
  count = count==null?3:count;
  precision = precision==null?2:precision;
  delimiterChar = delimiterChar==null?",":delimiterChar;

  var strMinus = "";
  if(value<0)
  {
    strMinus = "-";
    value = -1*value;
  }

  var strReturn = "";
  var strValue = point(round(value,precision),precision);

  strReturn = strValue.substring(strValue.length-precision-1);
  strValue = strValue.substring(0,strValue.length-precision-1);
  while(strValue.length>count)
  {
    strReturn = delimiterChar + strValue.substring(strValue.length-count) + strReturn;
    strValue = strValue.substring(0,strValue.length-count);
  }

  strReturn = strMinus + strValue + strReturn;
  return strReturn;
}

function round(number,precision){
	if(isNaN(number)){
		number = 0;
	}
	var prec = Math.pow(10,precision);
	var result = Math.round( number * prec) ;
	result = result/prec;
	return result;
}


function dwrInvokeData(invokeMethod, inputArgs, callbackMethod, inputField, outputField, message, async)
{
  //DWRUtil.useLoadingMessage(message);
  
  //useLoadingMessage(message);
  async = (async == null) ? true : async;
  var funcInvoke = "dwrInvokeDataAction." + invokeMethod + "(inputArgs, {" +
                   "callback:function(returnObject){" + callbackMethod +
                   "(inputField, outputField, returnObject)}," +
                   "async:" + async +" });";
  eval(funcInvoke);
}

//replace enter to tab
/*
function onkeydownHander(){
  if(   event.keyCode==13
     && event.srcElement.type!='button'
     && event.srcElement.type!='submit'
     && event.srcElement.type!='reset'
     && event.srcElement.type!='textarea')

     event.keyCode=9;
}
document.attachEvent("onkeydown",onkeydownHander);
*/


function private_getTopFrameForUseLoadingMessage()
{
  var topFrame = null;
  try{
	  topFrame = findIframe("topFrame");
  }catch(E){};
  if(topFrame==null || topFrame==undefined){
    topFrame=window;
  }
  return topFrame;
}

function useLoadingMessage(message) {
  var loadingMessage;
  if (message) loadingMessage = message;
  else loadingMessage = "Loading";

  var topFrame = private_getTopFrameForUseLoadingMessage();

  DWREngine.setPreHook(function() {
    //var disabledZone = $('disabledZone');
    var disabledZone = topFrame.document.getElementById("disabledZone");
    if (!disabledZone) {
      disabledZone = topFrame.document.createElement('div');
      disabledZone.setAttribute('id', 'disabledZone');
      disabledZone.style.position = "absolute";
      disabledZone.style.zIndex = "1000";
      disabledZone.style.left = "0px";
      disabledZone.style.top = "0px";
      disabledZone.style.width = "100%";
      disabledZone.style.height = "100%";
      topFrame.document.body.appendChild(disabledZone);
      var messageZone = topFrame.document.createElement('div');
      messageZone.setAttribute('id', 'messageZone');
      messageZone.style.position = "absolute";
      messageZone.style.top = "0px";
      messageZone.style.right = "0px";
      messageZone.style.background = "red";
      messageZone.style.color = "white";
      messageZone.style.fontFamily = "Arial,Helvetica,sans-serif";
      messageZone.style.padding = "4px";
      disabledZone.appendChild(messageZone);
      var text = topFrame.document.createTextNode(loadingMessage);
      messageZone.appendChild(text);
    }
    else {
      //$('messageZone').innerHTML = loadingMessage;
      var messageZone = topFrame.document.getElementById("messageZone");
      messageZone.innerHTML = loadingMessage;
      disabledZone.style.visibility = 'visible';
    }
  });

  DWREngine.setPostHook(function() {
    var disabledZone = topFrame.document.getElementById("disabledZone");
    disabledZone.style.visibility = 'hidden';
    //topFrame.document.$('disabledZone').style.visibility = 'hidden';
  });
}

function functionReturnFalse()
{
  return false;
}

function functionReturnTrue()
{
  return true;
}

function functionDoNothing()
{
  //do nothing
}

function functionCancelFocus()
{
  this.blur();
  window.focus();
  return false;
}

function isDate(date,splitChar)
{
  var charSplit = (splitChar==null?"-":splitChar);
  var strValue = date.split(charSplit);

  if(strValue.length!=3) return false;
  if(!isInteger(strValue[0]) || !isInteger(strValue[1]) || !isInteger(strValue[2]) ) return false;

  var intYear  = parseInt(strValue[0],10);
  var intMonth = parseInt(strValue[1],10)-1;
  var intDay   = parseInt(strValue[2],10);

  var dt = new Date(intYear,intMonth,intDay);
  if( dt.getFullYear() != intYear ||
      dt.getMonth() != intMonth ||
      dt.getDate() != intDay
     )
  {
    return false;
  }
  return true;
}

function formatDateToRC(date, format) {
	if (!date) {
		return;
	}
		
	if (!format){
		format = "yyyy-MM-dd";
	}
	
	switch (typeof date) {
		case "string":
			date = new Date(date.replace(/-/, "/"));
			break;
		case "number":
			date = new Date(date);
			break;
	}
	
	if (!date instanceof Date){
		return;
	}
	var dict = {
		"yyyy" : date.getFullYear(),
		"yyy" : date.getFullYear()-1911,
		"M" : date.getMonth() + 1,
		"d" : date.getDate(),
		"H" : date.getHours(),
		"m" : date.getMinutes(),
		"s" : date.getSeconds(),
		"MM" : ("" + (date.getMonth() + 101)).substr(1),
		"dd" : ("" + (date.getDate() + 100)).substr(1),
		"HH" : ("" + (date.getHours() + 100)).substr(1),
		"mm" : ("" + (date.getMinutes() + 100)).substr(1),
		"ss" : ("" + (date.getSeconds() + 100)).substr(1)
	};
	return format.replace(/(yyyy|yyy?|MM?|dd?|HH?|ss?|mm?)/g, function() {
		return dict[arguments[0]];
	});
}

function dateDiff(dateStart,dateEnd,MD)
{
  if(MD=="D")
  {
    var endTm = dateEnd.getTime();
    var startTm = dateStart.getTime();
    var diffDay = (endTm-startTm)/86400000+1;

    var diffDayTemp = "'" + diffDay + "'";
    if(diffDayTemp.indexOf(".") != -1){
      diffDay = parseInt(parseInt(diffDay, 10) + 1, 10);
    }

    return diffDay;
  }
  else
  {
    var endD = dateEnd.getDate();
    var endM = dateEnd.getMonth();
    var endY = dateEnd.getFullYear();
    var startD = dateStart.getDate();
    var startM = dateStart.getMonth();
    var startY = dateStart.getFullYear();

    if(endD>=startD)
    {
      return (endY-startY)*12+(endM-startM)+1;
    }
    else
    {
      return (endY-startY)*12+(endM-startM);
    }
  }
}

function isLeapYear(strCheckYear)
{
  var check4=strCheckYear%4==0?1:0;
  var check100=strCheckYear%100==0?-1:0;
  var check400=strCheckYear%400==0?1:0;
  var result=check4+check100+check400;
  if(result==1)
  {
    return true
  }
  return false
}
 


function getNextDateFullDate(strDate,intCount)
{
  var tempDate = new Date(replace(strDate,"-","/"));
  if(intCount == null)
  {
    intCount =1;
  }

  var nextDateInMS = tempDate.getTime() + (intCount * 24 * 60 * 60 * 1000 );
  var strReturn = convertFullDateToString(new Date(nextDateInMS));
  return strReturn;
}

function getNextMonthFullDate(strDate,intCount)
{
  var tempDate = new Date(replace(strDate,"-","/"));
  if(intCount == null)
  {
    intCount =1;
  }

  tempDate.setMonth(tempDate.getMonth() + intCount );
  var strReturn = convertFullDateToString(tempDate);
  return strReturn;
}

function getNextYearFullDate(strDate,intCount)
{
  var tempDate = new Date(replace(strDate,"-","/"));
  if(intCount == null)
  {
    intCount =1;
  }

  tempDate.setFullYear(tempDate.getFullYear() + intCount );
  var strReturn = convertFullDateToString(tempDate);
  return strReturn;
}

function convertFullDateToString(date) {
  if(date==null) {
    date = new Date();
  }

  var strDate = "";
  var year = "";
  var month = "";
  var day = "";
  year = date.getFullYear();
  if(parseInt(date.getMonth() + 1, 10) < 10) {
    month = "0" + parseInt(date.getMonth() + 1, 10)
  } else {
    month = parseInt(date.getMonth() + 1, 10);
  }
  if(parseInt(date.getDate(), 10) < 10) {
    day = "0" + parseInt(date.getDate(), 10)
  } else {
    day = parseInt(date.getDate(), 10);
  }
  strDate = year + DATE_DELIMITER + month + DATE_DELIMITER + day;
  return strDate;
}

function isNumeric(strValue)
{
  var result = regExpTest(strValue,/\d*[.]?\d*/g);
  return result;
}

function isInteger(strValue)
{
  var result = regExpTest(strValue,/\d+/g);
  return result;
}

//检查日期输入域
function checkFullDate(field)
{
  field.value = trim(field.value);
  var strValue = field.value;
  var desc   = field.description;
  //如果description属性不存在，则用name属性
  if(desc==null)
    desc = field.name;
  if(strValue=="")
  {
    return false;
  }
  if(isNumeric(strValue ))
  {
    if(strValue.length > 6 && strValue.length < 9)
    {
        strValue = strValue.substring(0,4) + DATE_DELIMITER + strValue.substring(4,6) + DATE_DELIMITER + strValue.substring(6);

        field.value = strValue;
    }
     else
     {
      errorMessage("请输入合法的日期，格式为YYYY-MM-DD 或者YYYYMMDD");
        field.value="";
        field.focus();
        field.select();
        return false;
     }
  }
  if( !isDate(strValue,DATE_DELIMITER) && !isDate(strValue)||strValue.substring(0,1)=="0")
  {
    errorMessage("请输入合法的日期，格式为YYYY-MM-DD 或者YYYYMMDD");
    field.value="";
    field.focus();
    field.select();
    return false;
  }

  return true;
}

function compareFullDate(date1,date2)
{
  var strValue1=date1.split(DATE_DELIMITER);
  var date1Temp=new Date(strValue1[0],parseInt(strValue1[1],10)-1,parseInt(strValue1[2],10));

  var strValue2=date2.split(DATE_DELIMITER);
  var date2Temp=new Date(strValue2[0],parseInt(strValue2[1],10)-1,parseInt(strValue2[2],10));

  if(date1Temp.getTime()==date2Temp.getTime())
    return 0;
  else if(date1Temp.getTime()>date2Temp.getTime())
    return 1;
  else
    return -1;
}


/**********************************************/
/************* Loading Bar  *******************/
/**********************************************/
/** deleteNode when page load*/
if (window.attachEvent) {   
   window.attachEvent("onload", delLoadingNode);   
} else if (window.addEventListener) {   
   window.addEventListener("load", delLoadingNode, false);    
}
/** deleteNode */
function  delLoadingNode(){   
  var nodeId = "loading";
  try{   
	  var div =document.getElementById(nodeId);  
	  if(div !==null){
		  document.body.removeChild(div);
		  div=null;    
 	  }  
  } catch(e){   
  	alert("delete node "+nodeId+" error");
  }   
}


// changestyle.js


function ExChgClsName(Btn,Obj){
	var obj=document.getElementById(Obj);
		obj.style.display =obj.style.display == "none" ? "" : "none";
if(obj.style.display==""){
		Btn.className='default';
   }else{
   Btn.className='down'
}		
}

function addLoadEvent(func) {
			var oldonload = window.onload;
			
			if (typeof window.onload != "function") {
				window.onload = func;
			} else {
				window.onload = function () {
					oldonload();
					func();
				}
			}
		}
		
		/*------------------------------------+
		 | Functions to run when window loads |
		 +------------------------------------*/
		addLoadEvent(function () {
			//initChecklist();
			//diffent();
		});
		
		/*----------------------------------------------------------+
		 | initChecklist: Add :hover functionality on labels for IE |
		 +----------------------------------------------------------*/
		function initChecklist() {
			if (document.all && document.getElementById) {
				// Get all unordered lists
				var lists = document.getElementsByTagName("input");
				
				for (i = 0; i < lists.length; i++) {
					var theList = lists[i];
					
					// Only work with those having the class "checklist"
					if (theList.className.indexOf("upload") > -1 ) {

							theList.onmouseover = function() { this.className="upload_over"; };
							theList.onmouseout = function() { this.className="upload"; };

					}
					if (theList.className.indexOf("download") > -1 ) {

							theList.onmouseover = function() { this.className="download_over"; };
							theList.onmouseout = function() { this.className="download"; };

					}
					if (theList.className.indexOf("btn_refresh") > -1 ) {

							theList.onmouseover = function() { this.className="btn_refresh_over"; };
							theList.onmouseout = function() { this.className="btn_refresh"; };

					}
					if (theList.className.indexOf("btn_zoom") > -1 ) {

							theList.onmouseover = function() { this.className="btn_zoom_over"; };
							theList.onmouseout = function() { this.className="btn_zoom"; };

					}
					if (theList.className.indexOf("btn_print") > -1 ) {

							theList.onmouseover = function() { this.className="btn_print_over"; };
							theList.onmouseout = function() { this.className="btn_print"; };

					}
					if (theList.className.indexOf("button_ty") > -1 ) {

							theList.onmouseover = function() { this.className="button_ty_over"; };
							theList.onmouseout = function() { this.className="button_ty"; };

					}
				}
			}
		}
		
function diffent(){
			var Ps = document.getElementsByTagName("p");
			for (i = 0; i < Ps.length; i++) {
			var theP = Ps[i];
            if (theP.className.indexOf("bd_out") > -1 ) {
							theP.onmouseover = function() { this.className="bd_over"; };
							theP.onmouseout = function() { this.className="bd_out"; };
					}
}
}

function checkedCheckBox(values,TreeLength,vGroupTree,userOrGroup){
  var value = new Array();
  value = values.split(",");
  for(var i=0;i<value.length;i++){//页面输入域的值 
    var checkValue = value[i];
	for(j=0;j<TreeLength;j++){//列表显示的值
	  if(userOrGroup == "user"){
		if(trim(checkValue) == trim(vGroupTree.data[j].userCode)){
		  fm.treeCheckBox[j+1].checked=true;
		}
	  }else{
		var id = vGroupTree.data[j].id;
		if(checkValue == vGroupTree.data[j].id){
		  fm.treeCheckBox[j+1].checked=true;
		}
	  }
	}
  }
}	
//对输入域按键时的数字校验
function pressDecimal(e){
	var value = String.fromCharCode(e.keyCode);
	if((value>=0 && value<=9) || value=="."|| value=="-"){
		return true;
	}else{
		return false;
	}
}
//离开域时的数字校验Decimal
function checkDecimal(field,p,s,MinValue,MaxValue){
	field.value = trim(field.value);
	var strValue=field.value;
	if(strValue==""){
		strValue = "0";
	}
	if(strValue.length>0 && strValue.charAt(0)=="-"){
		strValue = strValue.substring(1);
	}
	
	var title   = field.title;
	//如果description属性不存在，则用name属性
	if(title==null){
		title = field.name;
	}
	
	if(!isNumeric(strValue)){
		errorMessage("请输入合法的数字");
		field.focus();
		field.select();
		return false;
	}
	p = parseInt(p,10);
	s = parseInt(s,10);
	
	var pLength;
	var sLength;
	var position = strValue.indexOf(".");
	if(position>-1){
		pLength = position;
		sLength = strValue.length - position - 1;
	}else{
		pLength = strValue.length;
		sLength = 0;
	}
	
	if(pLength>(p-s) || sLength>s){
		errorMessage("请输入合法的" + title +"\n类型为数字,整数位最长为" + (p-s) + ",小数位最长为" + s);
		field.focus();
		field.select();
		return false;
	}
	
	var value = parseFloat(strValue);
	if(MaxValue!=null && MinValue!=null && trim(MaxValue)!="" && trim(MinValue)!=""){
		MinValue = parseFloat(MinValue);
		MaxValue = parseFloat(MaxValue);
		if(isNaN(value) || value>MaxValue || value<MinValue){
			errorMessage("请输入合法的" + title +"\n类型为数字,最小值为" + MinValue + ",最大值为" +MaxValue);
			field.focus();
			field.select();
			return false;
		}
	}
	return true;
}

//public
function setCheckBoxReadonly(field,flag)
{
  if(flag==null)
  {
    errorMessage("函数setCheckBoxReadonly使用错误，Flag应该为True/Flase!");
    return;
  }

  if(flag==true)
  {
    if(field.setCheckBoxReadonlyFlag!=true)
    {
      field.setCheckBoxReadonlyFlag = true;
      field.oldClassName = field.className;
      field.oldOnclick   = field.onclick;
      //field.className = "readonly1";  //邵海林 注释 此样式待定
      field.onclick = functionReturnFalse;
    }
  }
  else
  {
    if(field.setCheckBoxReadonlyFlag==true)
    {
      field.className = field.oldClassName;
      field.onclick = field.oldOnclick;
      field.setCheckBoxReadonlyFlag = false;
    }
  }
}
//public
function setRadioReadonly(field,flag)
{
	if(flag==null)
	{
	  errorMessage("函数setRadioReadonly使用错误，Flag应该为True/Flase!");
	  return;
	}
	if(flag==true)
	{
	  if(field.setRadioReadonlyFlag!=true)
	  {
	    field.setRadioReadonlyFlag = true;
	    field.oldClassName = field.className;
	    field.oldOnfocus   = field.onfocus;
	    field.className = "readonlyradio";
	    field.onfocus = functionCancelFocus;
	  }
	}
	else
	{
	  if(field.setRadioReadonlyFlag==true)
	  {
	    field.className = field.oldClassName;
	    field.onfocus = field.oldOnfocus;
	    field.setRadioReadonlyFlag = false;
	  }
	}
}
//public
function setReadonlyOfElement(iElement){
	if(iElement.type=="select-one"){
		if(iElement.setReadonlyFlag==true){
			return;
		}else{
			iElement.setReadonlyFlag = true;
		}
		
		var optionTags = new Array();
		var index = 0;
		for(var j=iElement.options.length-1;j>=0;j--){
			var tag = new Array();
			tag["value"] = iElement.options[j].value;
			tag["text"]  = iElement.options[j].text;
			optionTags[index++] = tag;
			if(iElement.options[j].value!=iElement.value){
				iElement.remove(j);
			}
		}
		iElement.optionTags = optionTags;
	}else if((iElement.type=="hidden")||
			(iElement.type=="password")||
			(iElement.type=="text")||
			(iElement.type=="textarea")){
		if(iElement.setReadonlyFlag==true){
			return;
		}else{
			iElement.setReadonlyFlag = true;
		}
		//事件存储在oldXXX里
		iElement.oldOnblur = iElement.onblur;
		iElement.onblur = functionDoNothing;
		iElement.oldOndblclick = iElement.ondblclick;
		iElement.ondblclick = functionDoNothing;
		iElement.oldOnfocus = iElement.onfocus;
		iElement.onfocus = functionDoNothing;
		iElement.oldClassName = iElement.className;
		iElement.readOnly = true;
		iElement.className = "readonly1";
		
		if(iElement.style.width==""){
			switch (iElement.oldClassName){
				case "codecode" :
					// iElement.style.width="40px";
					break;
				case "common" :
					iElement.style.width="160px";
					break;
				case "readonly" :
					iElement.style.width="160px";
					break;
				default :
			}
		}
	}else if(iElement.type=="button"){
		if(iElement.setReadonlyFlag==true){
			return;
		}else{
			iElement.setReadonlyFlag = true;
		}
		if(iElement.name.indexOf("Delete")>-1 || iElement.name.indexOf("Insert")>-1|| iElement.name.indexOf("submit")>-1){
			iElement.disabled = true;
		}
	}else if(iElement.type=="checkbox"){
		setCheckBoxReadonly(iElement,true);
	}else if(iElement.type=="radio"){
		setRadioReadonly(iElement,true);
	}
}

//将容器里的元素设置为只读或可读写
function setContainerReadonly(Container,Flag){
	var i = 0;
	var vFlag = (Flag==null?true:Flag);
	var elements;
	
	//Input域
	elements = Container.getElementsByTagName("input");
	for(i=0;i<elements.length;i++){
		if(vFlag){
			setReadonlyOfElement(elements[i]);
		}else{
			undoSetReadonlyOfElement(elements[i]);
		}
	}
	
	//Select域
	elements = Container.getElementsByTagName("select");
	for(i=0;i<elements.length;i++){
		if(vFlag){
			setReadonlyOfElement(elements[i]);
		}else{
			undoSetReadonlyOfElement(elements[i]);
		}
	}
	
	//Textarea域
	elements = Container.getElementsByTagName("textarea");
	for(i=0;i<elements.length;i++){
		if(vFlag){
			setReadonlyOfElement(elements[i]);
		}else{
			undoSetReadonlyOfElement(elements[i]);
		}
	}
}

//public
//一次给所有的text,textarea设置为readonly,select-one变成只保留当前选项
//过程部分可逆
function setReadonlyOfAllInput(){
	for(var i=0;i<fm.elements.length;i++){
		setReadonlyOfElement(fm.elements[i]);
	}
}

//public
//过程部分可逆,select-one所有附加事件被取消 --OK了
function undoSetReadonlyOfElement(iElement){
	if(iElement.type=="select-one"){
		if(iElement.setReadonlyFlag!=true){
			return;
		}else{
			iElement.setReadonlyFlag = false;
  	}
  	var optionTags = iElement.optionTags;
  	var currentValue = iElement.value;
  	
  	for(var i=iElement.options.length-1;i>=0;i--){
  		iElement.remove(i);
  	}
  	
  	for(var i=optionTags.length-1;i>=0;i--){
  		var tag = optionTags[i];
  		var op = document.createElement("OPTION");
  		op.value = tag.value;
  		op.text =  tag.text;
  		iElement.add(op);
  	}
  	iElement.value = currentValue;
  }else if ((iElement.type=="hidden")||
  			(iElement.type=="password")||
  			(iElement.type=="text")||
  			(iElement.type=="textarea")){
  	if(iElement.setReadonlyFlag!=true){
  		return;
  	}else{
  		iElement.setReadonlyFlag = false;
  	}
  	iElement.onblur = iElement.oldOnblur;
  	iElement.ondblclick = iElement.oldOndblclick;
  	iElement.onfocus = iElement.oldOnfocus;
  	iElement.readOnly = false;
  	iElement.className = iElement.oldClassName;
  }else if(iElement.type=="button"){
  	if(iElement.setReadonlyFlag!=true){
  		return;
  	}else{
  		iElement.setReadonlyFlag = false;
  	}

		if(iElement.name.indexOf("Delete")>-1 || iElement.name.indexOf("Insert")>-1 || iElement.name.indexOf("submit")>-1){
			iElement.disabled = false;
		}
	}else if(iElement.type=="checkbox"){
		setCheckBoxReadonly(iElement,false);
	}else if(iElement.type=="radio"){
		setRadioReadonly(iElement,false);
	}
}

//一次给所有的text,textarea的readonly设置为false,select-one恢复初始设置
function undoSetReadonlyOfAllInput(){
	for(var i=0;i<fm.elements.length;i++){
		undoSetReadonlyOfElement(fm.elements[i]);
	}
}
//调用dwr时屏蔽按钮

function disablebutton() {
	var elements = document.getElementsByTagName("INPUT");
	for (var i = 0; i < elements.length; i++) {
		//将button设成不可用
		if (elements[i].type == "button") {
			elements[i].disabled = true;
		}
		//将submit设成不可用
		if (elements[i].type == "submit") {
			elements[i].disabled = true;
		}
		//将reset设成不可用
		if (elements[i].type == "reset") {
			elements[i].disabled = true;
		}
	}
}

//调用dwr完毕恢复按钮使用

function undisablebutton() {
	var elements = document.getElementsByTagName("INPUT");
	for (var i = 0; i < elements.length; i++) {
		//将button设成不可用
		if (elements[i].type == "button") {
			elements[i].disabled = false;
		}
		//将submit设成不可用
		if (elements[i].type == "submit") {
			elements[i].disabled = false;
		}
		//将reset设成不可用
		if (elements[i].type == "reset") {
			elements[i].disabled = false;
		}
	}
}

//reason:强三

function relateBeforePolicyNo(policyNo, riskCode, damagedate) {
	var SHOWTYPE = "SHOW";

	var BizNo = policyNo;

	var RiskCode = riskCode;

	var damageDate = damagedate;
	var coreURL = fm.coreURL.value;
//	vURL = coreURL + RiskCode + '/tbcbpg/UIPrPoEn' + RiskCode + 'Show.jsp?BIZTYPE=POLICY&SHOWTYPE=SHOW&BizNo=' + BizNo + '&RiskCode=' + RiskCode + '&damageDate=' + damageDate;
	vURL = '/claim/pages/common/pub/PolicyShowCenter.jsp?BIZTYPE=POLICY&SHOWTYPE=SHOW&BizNo=' + BizNo + '&RiskCode=' + RiskCode + '&damageDate=' + damageDate + '&coreURL=' + coreURL;
	//var vURL = '/prpall/PathForward?BizNo='+BizNo+'&RiskCode='+RiskCode+'&damageDate='+ damageDate;  
	//var vURL = '/prpall/' + RiskCode + '/tbcbpg/UIPrPoEn' + RiskCode + 'Show.jsp?BIZTYPE=POLICY&SHOWTYPE=SHOW&BizNo='+ BizNo+'&RiskCode='+ RiskCode+'&damageDate='+ damageDate;
	window.open(vURL, '详细信息', 'width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}
/**
 * 默认的校验Form的方法
 * @return 通过true/不通过false
 */
function validateQueryForm(form) {
	if (bCancel == true) {
		return true;
	}
	return validateType(form) && validateDate(form);
}