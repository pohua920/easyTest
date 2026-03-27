/****************************************************************************
 * DESC       Application
 * AUTHOR     zhouxianli
 * CREATEDATE 2003-05-04
 * MODIFYLIST    Name       Date            Reason/Contents
 *          ------------------------------------------------------
 *
 ************************************************************************************/
 
 
 
function errorMessage(strErrMsg)
{
  var strMsg = "\u7cfb\u7edf\u4fe1\u606f:\n\n" + strErrMsg;
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
	parent.frames.head.CurrentPositionSpan.innerHTML=theHREF.title;
	//var a = fm.CurrentPositionSpan.value;
	//alert(a);
    //top.topFrame.setCommand(theHREF.title);
//    top.fraRight.rows = "100%,0%,*";
}


setVerbose(true);

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
function pointTwo( s )
{
  return point(s,2);
}

function pointFour( s )
{
  return point(s,4);
}

function dwrInvokeData(invokeMethod, inputArgs, callbackMethod, inputField, outputField, message, async)
{
  //DWRUtil.useLoadingMessage(message);
  
  useLoadingMessage(message);
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
    topFrame = window.top.topFrame;
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

function setReadonlyOfElement(iElement){
  if(iElement.type == "select-one"){
    if(iElement.setReadonlyFlag==true){
      return;
    }else{
      iElement.setReadonlyFlag = true;
    }
    var optionTags = new Array();
    var index = 0;
    for(var j = iElement.options.length-1; j >= 0; j--){
      var tag = new Array();
      tag["value"] = iElement.options[j].value;
      tag["text"]  = iElement.options[j].text;
      optionTags[index++] = tag;
      if(iElement.options[j].value != iElement.value){
        iElement.remove(j);
      }
    }
    iElement.optionTags = optionTags;
    iElement.className="readonly";
  }
  else if ((iElement.type == "hidden") || (iElement.type == "password") ||
    (iElement.type == "text") || (iElement.type == "textarea")){
    if(iElement.setReadonlyFlag==true){
      return;
    }else{
      iElement.setReadonlyFlag = true;
    }
    //event backup
    iElement.oldOnblur = iElement.onblur;
    iElement.onblur = functionDoNothing;
    iElement.oldOndblclick = iElement.ondblclick;
    iElement.ondblclick = functionDoNothing;
    iElement.oldOnfocus = iElement.onfocus;
    iElement.onfocus = functionDoNothing;
    iElement.oldClassName = iElement.className;

    iElement.readOnly = true;
    iElement.className="readonly";
  }
  // Do not disable button
  //else if(iElement.type=="button"){
  //  if(iElement.setReadonlyFlag==true){
  //    return;
  //  }else{
  //    iElement.setReadonlyFlag = true;
  //  }
  //}
  else if(iElement.type == "checkbox"){
    setCheckBoxReadonly(iElement,true);
  }
  else if(iElement.type == "radio"){
    setRadioReadonly(iElement,true)
  }
}

function undoSetReadonlyOfElement(iElement)
{
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
  }
  else if ((iElement.type=="hidden") || (iElement.type=="password") ||
            (iElement.type=="text") || (iElement.type=="textarea")){
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
  }
  else if(iElement.type=="button"){
    if(iElement.setReadonlyFlag!=true){
      return;
    }else{
      iElement.setReadonlyFlag = false;
    }
  }
  else if(iElement.type=="checkbox"){
    setCheckBoxReadonly(iElement,false);
  }
  else if(iElement.type=="radio"){
    setRadioReadonly(iElement,false);
  }
}

// flag:
//   true --> set ReadOnly
//   false--> unset ReadOnly
function setCheckBoxReadonly(field,flag)
{
  if(flag==true)
  {
    if(field.setCheckBoxReadonlyFlag!=true)
    {
      field.setCheckBoxReadonlyFlag=true;
      field.oldClassName = field.className;
      field.oldOnclick   = field.onclick;
      field.className = "readonlycheckbox";
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

function setRadioReadonly(field,flag)
{
  if(flag==true)
  {
    if(field.setRadioReadonlyFlag!=true)
    {
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

function checkFullDate(field)
{
  field.value = trim(field.value);
  var strValue = field.value;
  if(strValue=="")
  {
    return false;
  }
  if(isNumeric(strValue))
  {
    if(strValue.length > 6 && strValue.length < 9)
    {
      strValue = strValue.substring(0,4) + DATE_DELIMITER + strValue.substring(4,6) + DATE_DELIMITER + strValue.substring(6);
      field.value = strValue;
    }
    else
    {
      errorMessage("The correct date format is yyyy-MM-dd.");
      field.value="";
      field.focus();
      field.select();
      return false;
    }
  }
  if( !isDate(strValue,DATE_DELIMITER) && !isDate(strValue)||strValue.substring(0,1)=="0")
  {
    errorMessage("The correct date format is yyyy-MM-dd.");
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
			initChecklist();
			diffent();
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
