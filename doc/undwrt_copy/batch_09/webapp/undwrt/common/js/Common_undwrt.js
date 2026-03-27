/***************************************************************************
* Description: 公用函数变量定义
* Author     : 新双核
* CreateDate:  2004-12-27 11:12
* UpdateLog：  Name       Date            Reason/Contents
****************************************************************************/


var BIZTYPE_POLICY   = "POLICY"; 
var BIZTYPE_PROPOSAL = "PROPOSAL";

var bCancel = false;
var DATE_DELIMITER = "-";
var DB_INT_LENGTH = 64; //数据库位数,即整数长度，默认为64位
var MAX_INTEGER  = Math.pow(2,DB_INT_LENGTH-1) - 1;
var MIN_INTEGER  = -Math.pow(2,DB_INT_LENGTH-1);
var MAX_SMALLINT = Math.pow(2,DB_INT_LENGTH/4-1) - 1;
var MIN_SMALLINT = -Math.pow(2,DB_INT_LENGTH/4-1);
var VERBOSE = false;//显示所有明细，开发环境中可以调用setVerbose(true)

//检查模糊查询的查询复合和查询条件
function checkBlur(option,condition)
{
	var index = 0;
	index = eval("fm."+condition).value.indexOf(":")+0;
	var value = "";
	value = eval("fm."+condition).value;
	if (eval("fm."+option).value==":" && value!="")
	  if (index<0)
	  {
	    eval("fm."+condition).focus();
	    errorMessage(i18n.messages.confirmInputCondition);  	    
	  }
	  else if (index == 0||
	           index == value.length-1)
	  {
	  	eval("fm."+condition).focus();
	    errorMessage(i18n.messages.confirmInputFormat);  	    
	  }
	
}


//对输入域按键时的日期校验

function pressFullDate(e)

{

  var value = String.fromCharCode(e.keyCode);

  if((value>=0 && value<=9) || value=="/" || value=="-")

    return true;

  else

    return false;

}





//定义常数
var FIELD_SEPARATOR = "_FIELD_SEPARATOR_";   //字段之间的分割符
var GROUP_SEPARATOR = "_GROUP_SEPARATOR_";     //一组代码之间的分割符

//控制输入域长度
// 使用方法如下所示
// <input name = "PolicyNo" maxlength="8" description="保单号"  onblur="checkLength(this)">
function checkLength(Field)
{
  var str;
  var count  = 0;
  
  var value  = Field.value;
  var length = Field.maxLength;
  var desc   = Field.description;
  
  if(value=="")
  {
    return;
  }

  if(value.indexOf("^")>-1 ||
     value.indexOf(FIELD_SEPARATOR)>-1 ||
     value.indexOf(GROUP_SEPARATOR)>-1
    )
  {
    errorMessage("^" + i18n.messages.retainCode);
    Field.focus();
    Field.select();
		return false;
	}

  //如果description属性不存在，则用name属性
  if(desc==null)
    desc = Field.name;

  //如果maxlength属性不存在，则返回
  if(isNaN(parseInt(length)))
    return;

  for(var i=0;i<value.length;i++)
  {
    str = escape(value.charAt(i));
    if(str.substring(0,2)=="%u" && str.length==6)
      count = count + 2;
    else
      count = count + 1;
  }

  if(count>length)
  {
    errorMessage(desc + i18n.messages.contentBeyond + "\n" + desc + i18n.messages.maxLength + length + i18n.messages.englishCode + "\n" + i18n.messages.inputAgain6);
    Field.focus();
    Field.select();
	}
}


/**检验输入的险种是否合法**/
function checkRiskCode(e)
{
  var charCode=e.keyCode;
  if(!(charCode>=97 && charCode <=122 || charCode>=65 && charCode <=90))
   return false;
  if( charCode>=97 && charCode<=122 )
  {
    window.event.keyCode = charCode-32;
  }
}


/* 大写输入域 --onkeypress时调用该方法 */
function uppercaseKey()
{
  var keycode = window.event.keyCode;
  if( keycode>=97 && keycode<=122 )
  {
    window.event.keyCode = keycode-32;
  }
}


/**取文件名**/
function getFileName(strFile)
{
  var index = strFile.lastIndexOf("\\");
  if(index==-1)
  {
    return strFile;
  }
  else
  {
    return strFile.substring(index+1);
  }
}


/**实现页面回退功能**/
function preWindow()
{
  history.back();
}


/**更换图片**/
function changeImage(image,gif)
{
  
	image.src='/undwrt/common/images/'+gif;
}

/**取消操作**/
function cancelForm()
{
	window.location.href="/undwrt/common/Blank.html";
}

/**关闭窗口**/
function closeWindow()
{
	window.close();
}

/**得到元素在Form中的同名元素中的顺序**/
function getElementOrder(field)
{
  var i = 0;
  var order = 0;
  var elements = document.getElementsByName(field.name);
  var elementsCount = elements.length;
  for(i=0;i<elementsCount;i++){
      order++;
      if(elements[i]==field){
          break;
      }
  }
  return order;
}

/**查找元素在Form中的顺序，没有则返回-1**/
function getElementIndex(Field)
{
  var intElementIndex = -1;

  for(var i=0;i<fm.elements.length;i++) //查找fm里的元素
  {
    if(fm.elements[i]==Field)
    {
      intElementIndex=i;
      break;
    }
  }
  return intElementIndex;
}

/**查找在Form中的同名元素，没有则返回0**/
function getElementCount(strFieldName)
{
  var intCount = 0;

  for(var i=0;i<fm.elements.length;i++) //查找fm里的元素
  {
    if(fm.elements[i].name==strFieldName)
    {
      intCount++;
    }
  }
  return intCount;
}

/**对span的显示、隐藏**/
function showPage(input,spanID)
{
 
  if(spanID.style.display=="")
  {
    //关闭
    spanID.style.display="none";
    input.src="/undwrt/common/images/butCollapse.gif";
  }
  else
  {
    //打开
    spanID.style.display="";
    input.src="/undwrt/common/images/butExpand.gif";
  }
}


/**显示错误信息**/
function errorMessage(strErrMsg)
{
  var strMsg = i18n.messages.systemInformation + "\n\n" + strErrMsg;
  alert(strMsg);
}


/**去掉字符串头尾空格**/
function trim(s)
{
  var strReturn;
  strReturn = leftTrim(s);
  strReturn = rightTrim(strReturn);
  return strReturn;
}


/**对输入域是否是整数的校验**/
function isInteger(strValue)
{
  var NUM="0123456789";
  var i;
  if(strValue==null || strValue=="") return false;
  for(i=0;i<strValue.length;i++)
  {
    if(NUM.indexOf(strValue.charAt(i))<0) return false;

  }
  return true;
}


/**对输入域是否是数字的校验**/
function isNumeric(strValue)
{
  var NUM="0123456789.";
  var i;
  if(strValue==null ||strValue=="") return false;
  for(i=0;i<strValue.length;i++)
  {
    if(NUM.indexOf(strValue.charAt(i))<0) return false
  }
  if(strValue.indexOf(".")!=strValue.lastIndexOf(".")) return false;
  return true;
}


/**离开域时的数字校验**/
function checkNumber(Field)
{
	var strValue=Field.value;
	strValue = strValue.toString().replace(/,/g, "");  
	if( trim(strValue)!="" && !isNumeric(strValue) )
	{
	  errorMessage(i18n.messages.inputValidDigit);
		Field.focus();
		Field.select();
		return false;
	}
	return true;
}


/**离开域时检查年**/
function checkYear(Field)
{
	var strValue=Field.value;
	if(trim(strValue)!="" && !(isInteger(strValue) && strValue.length==4 ) )
	{
	  errorMessage(i18n.messages.yearIsFourDigits);
		Field.focus();
		Field.select();
	}
}


/**离开域时检查月**/
function checkMonth(Field)
{
	var strValue=Field.value;
	if(trim(strValue)!="" && !(isInteger(strValue) && eval(strValue)>0 && eval(strValue)<13 ) )
	{
	  errorMessage(i18n.messages.monthBetween);
		Field.focus();
		Field.select();
	}
}


/**离开域时检查日**/
function checkDay(Field)
{
	var strValue=Field.value;
	if(trim(strValue)!="" && !(isInteger(strValue) && eval(strValue)>0 && eval(strValue)<32 ) )
	{
	  errorMessage(i18n.messages.dateBetween);
		Field.focus();
		Field.select();
	}
}


/**离开域时检查小时**/
function checkHour(Field)
{
	var strValue=Field.value;
	if(trim(strValue)!="" && !(isInteger(strValue) && eval(strValue)>=0 && eval(strValue)<=24 ) )
	{
		errorMessage(i18n.messages.hourBetween);
		Field.focus();
		Field.select();
	}
}


/**离开域时检查空**/
function hasValue(Field)
{
	if(Field.value=="")
		return false;
	else
	  return true;
}


/**按键时的整数校验**/
function checkInteger(e)
{
  var charCode=e.keyCode;
  if(charCode>=48 && charCode<=57)
  {
    return true;
  }
  return false;
}


/**按键时的整数校验(只允许录入数字和:)**/
function checkIntegerBlur(e)
{
  var charCode=e.keyCode;
  if(charCode>=48 && charCode<=58)
  {
    return true;
  }
  return false;
}


/**按键时的日期校验**/
function checkDate(e)
{
	var charCode = e.keyCode;
	if((charCode>=47 && charCode<=57) || charCode==45)//(只允许输入数字和"/")
	  return true;
	return false;
}


/**按键时的日期校验(只允许录入数字"/"和":"和" ")**/
function checkDateBlur(e)
{
	var charCode = e.keyCode;
	if((charCode>=47 && charCode<=58) || charCode==45 || charCode ==32)
	  return true;
	return false;
}


/**按键时的数字校验**/
function checkNumeric(e)
{

  var charCode=e.keyCode;
	if(charCode>31 && (charCode<48 || charCode>57) && charCode!=46)
	{
	return false;
	}
	return true;
}



/**按键时的数字校验**/
function checkNumericBlur(e)
{

  var charCode=e.keyCode;
	if(charCode>31 && (charCode<48 || charCode>58) && charCode!=46)
	{
	return false;
	}
	return true;
}


/**按键时的数字校验**/
function checkNumericBlur(e)
{

  var charCode=e.keyCode;
	if(charCode>31 && (charCode<48 || charCode>58) && charCode!=46)
	{
	return false;
	}
	return true;
}


/**日期的合法判断**/
function isLegalDate(y,m,d)
{
  if(isNaN(parseInt(y,10)) || isNaN(parseInt(m,10)) || isNaN(parseInt(d,10)) )
    return false;
  var dt = new Date(parseInt(y,10),parseInt(m,10)-1,parseInt(d,10));
  if( dt.getYear()==parseInt(y,10) &&
      dt.getMonth()==parseInt(m,10)-1 &&
      dt.getDate()==parseInt(d,10)
    )
    return true;
  else
    return false;
}


/**对输入域是否是日期的校验(yyyy-mm-dd)格式**/
//jiang modifid at 2002-07-10
//添加了sign参数，!isInteger改为isNaN,eval改为parseInt,原代码调试出错，现调试通过
function isDate(date,sign)
{
	
  var strValue;
  strValue=date.split(sign);

  if(strValue.length!=3) return false;
  if(isNaN(strValue[0]) || isNaN(strValue[1]) || isNaN(strValue[2]) ) return false;

  var intYear=parseInt(strValue[0]);
  var intMonth=parseInt(strValue[1]);
  var intDay=parseInt(strValue[2]);

  if( intYear<0 || intYear>9999 || intMonth<0 || intMonth>12 || intDay<0 || intDay>31 ) return false;
  return true;
}


/**比较两个日期字符串**/
/** date1=date2则返回0 , date1>date2则返回1 , date1<date2则返回2**/
function compareDate(date1,date2)
{
  var strValue=date1.split("-");
  var date1Temp=new Date(strValue[0],strValue[1],strValue[2]);

  strValue=date2.split("-");
  var date2Temp=new Date(strValue[0],strValue[1],strValue[2]);

  if(date1Temp.getTime()==date2Temp.getTime())
    return 0;
  else if(date1Temp.getTime()>date2Temp.getTime())
    return 1;
  else
    return 2;
}

//在表格下方添加一组数据,
//参数为页代码名称和页原始数据代码名称
//例:insertRow("Engage","Engage$Data");
function insertRow(PageCode,DataPageCode)
{  
  if(DataPageCode==null)
  {
    DataPageCode = PageCode + "_Data";
  }
  var oTBODY     = document.all(PageCode).tBodies.item(0);
  var oTBODYData = document.all(DataPageCode).tBodies.item(0);
  var oCellsData;
  var oTR;
  var oTCell;
  var i = 0;
  var j = 0;

  for(i=0;i<oTBODYData.rows.length;i++)
  {
    oCellsData = oTBODYData.rows(i).cells;

    oTR=oTBODY.insertRow(-1);

    if(oTBODYData.rows(i).className!=null)
    {
      oTR.className = oTBODYData.rows(i).className;
    }
    if(oTBODYData.rows(i).align!=null)
    {
      oTR.align = oTBODYData.rows(i).align;
    }
    for(j=0;j<oCellsData.length;j++)
    {
      oTCell=oTR.insertCell(j);
      oTCell.innerHTML = oCellsData.item(j).innerHTML;
      if(oCellsData.item(j).className!=null)
      {
        oTCell.className = oCellsData.item(j).className;
      }
      if(oCellsData.item(j).align!=null)
      {
        oTCell.align = oCellsData.item(j).align;
      }
    }
  }
}

//删除控制按钮控制的行
//页名称，字段，数据页中控制按钮的个数，数据页中每个控制按钮的控制的TR的个数
function deleteRow(PageCode,Field,intPageDataKeyCount,intRowCount)
{
  if (intPageDataKeyCount==null)
  {
    intPageDataKeyCount = 1;
  }

  if (intRowCount==null)
  {
    intRowCount = 1;
  }
  var intIndex = parseInt(getElementOrder(Field)) -1;  //顺序改为以0开始
  var oTBODY   = document.all(PageCode).tBodies.item(0);
  intIndex = intIndex - intPageDataKeyCount;  //去掉隐含域中的控制按钮的个数
  for(var i=0;i<intRowCount;i++)
  {
    oTBODY.deleteRow(intIndex*intRowCount);
  }
}

//得到一页的多行记录的记录数
//页名称
function getRowsCount(PageCode)
{
  var oTBODY   = document.all(PageCode).tBodies.item(0);
  var intCount = oTBODY.rows.length;
  return intCount;
}

//清除一页的所有多行记录
//页名称
function deleteAllRows(PageCode)
{
  var oTBODY   = document.all(PageCode).tBodies.item(0);
  var intCount = getRowsCount(PageCode);

  for(var i=0;i<intCount;i++)
  {
   oTBODY.deleteRow(0);
  }
}

//按↓（向下）键时调用页面的"insert" + PageCode + "()"方法
function fieldOnKeyUp(PageCode)
{
  if( window.event.keyCode==40)
  {
    eval("insert" + PageCode + "()");
  }
}
//各个多行输入区属性信息
var pagesAttributes = new Array(); //存放所有的上一条/下一条模式页数据
var attributes      = new Array(); //存放一种上一条/下一条模式页数据
var attribute       = new Array(); //存放一种上一条/下一条模式页数据

function previousRecord(PageCode)
{
  setCurrentRecord(PageCode);
  attributes = pagesAttributes[PageCode];
  if(attributes.currentIndex<=0)
  {
    attributes.currentIndex=0;
    setRecordState(PageCode);
    errorMessage(i18n.messages.theFirstOne);
    return;
  }
  attributes.currentIndex--;
  loadRecord(PageCode);
  setRecordState(PageCode);
}

function nextRecord(PageCode)
{
  var i = 0;
  attributes = pagesAttributes[PageCode];
  var attribute = new Array();
  var intIndex = attributes.currentIndex;

  //保存当前记录
  setCurrentRecord(PageCode);

  //如果到最后一行了，则提示
  if(intIndex>=attributes.count-1)
  {
    errorMessage(i18n.messages.theLastOne);
  }
  else //否则，读出下一行内容
  {
    intIndex++;
    attributes.currentIndex = intIndex;
    loadRecord(PageCode);
  }
  setRecordState(PageCode);
}

function deleteRecord(PageCode)
{
  var i=0;

  attributes = pagesAttributes[PageCode];

  //只有一条记录时，不是删除而是清空
  if(attributes.count<=1)
  {
    attribute = new Array();
    attributes[attributes.currentIndex] = attribute;
    for(i=0;i<attributes["field"].length;i++)
    {
      attribute[i] = "";
      eval("fm." + attributes.field[i] + ".value=''");
    }
    attributes.count=1;
    attributes.currentIndex=0;
    return;
  }
  var attributesTemp = new Array();

  for(i=0;i<attributes.currentIndex;i++)
  {
    attributesTemp[i]=attributes[i];
  }
  for(i=attributes.currentIndex+1;i<attributes.length;i++)
  {
    attributesTemp[i-1]=attributes[i];
  }

  attributesTemp.currentIndex = attributes.currentIndex-1;
  attributesTemp.count = attributes.count-1;
  attributesTemp.field = attributes.field;
  attributes = attributesTemp;

  if(attributes.currentIndex<0)
  {
    attributes.currentIndex = 0;
  }

  pagesAttributes[PageCode]=attributes;
  loadRecord(PageCode);
  setRecordState(PageCode);
}

function loadRecord(PageCode)
{
  var i = 0;
  attributes = pagesAttributes[PageCode];
  attribute = new Array();
  attribute = attributes[attributes.currentIndex];

  for(i=0;i<attributes["field"].length;i++)
  {
    eval("fm." + attributes.field[i] + ".value='" + attribute[i] + "'");
  }
}

function addRecord(PageCode)
{
  attributes = pagesAttributes[PageCode];
  setCurrentRecord(PageCode);
  attribute = new Array();
  var intIndex = attributes.count;
  attributes.currentIndex = intIndex;
  attributes[attributes.currentIndex] = attribute;

  for(var i=0;i<attributes["field"].length;i++)
  {
    attribute[i] = "";
    eval("fm." + attributes.field[i] + ".value='" + attribute[i] + "'");
  }
  attributes.count++;
  setRecordState(PageCode);
}

function setCurrentRecord(PageCode)
{
  attributes = pagesAttributes[PageCode];
  attributes[attributes.currentIndex] = attribute;

  for(var i=0;i<attributes["field"].length;i++)
  {
    attribute[i] = eval("fm." + attributes.field[i] + ".value");
  }
}

function setRecordState(PageCode)
{
  attributes = pagesAttributes[PageCode];
  eval("span$" + PageCode + "$State").innerHTML = i18n.messages.di + (attributes.currentIndex + 1) + i18n.messages.together + attributes.length + i18n.messages.length;
}


//保存记录进入span
function saveRecord(PageCode)
{
  setCurrentRecord(PageCode);
  clearRecord(PageCode);

  var strText = "";
  attributes = pagesAttributes[PageCode];
  for(var i=0;i<attributes.length;i++)
  {
    for(var j=0;j<attributes["field"].length;j++)
    {
      strText = strText +
                "<input type=hidden name='" + attributes.field[j] + "'" +
                " value='" + attributes[i][j]+ "'>";
    }
  }
  eval("span$" + PageCode).innerHTML = strText;
  return true;
}

//清除记录所在的span的内容
function clearRecord(PageCode)
{
  eval("span$" + PageCode).innerHTML = "";
  return true;
}


function doTest()
{
  var oTBODYData   = document.all("Tengage").tBodies.item(0);
  var oCellsData;
  var oTR;
  var oTCell;
  var i = 0;
  var j = 0;
  var strText = "";

//  for(i=0;i<oTBODYData.rows.length;i++)
//  {
//    oCellsData = oTBODYData.rows(i).cells;
//
//    for(j=0;j<oCellsData.length;j++)
//    {
//      strText += oCellsData[j].className;
//    }
//
//  }
  strText = oTBODYData.innerHTML;
  fm.log.value = strText;
}



//各个多行输入区属性信息
var allPageData    = new Array();  //公用，不能当作临时变量改变
var onePageData    = new Array();
var onePageRowData = new Array();

//按回车键时定位记录
function fieldLocateRecord(PageCode,Field)
{
  var index = parseInt(Field.value);
  if(!isNaN(index))
  {
    locateRecord(PageCode,index);
  }
}

//功能：定位第n页
//工作原理：
//保存当前记录
//检查记录号，如果有读出并显示，设置状态。
//否则提示错误信息
//设置状态。
function locateRecord(PageCode,index)
{
  onePageData = allPageData[PageCode];
  //保存当前记录
  setCurrentRecord(PageCode);

  //如果超过最后一行，则提示
  if(index>onePageData.count)
  {
    errorMessage(i18n.messages.notExist + index + i18n.messages.record);
  }
  else if(index<1)
  {
    errorMessage(i18n.messages.recordNumber);
  }
  else //否则，读出下一行内容
  {
    onePageData.currentIndex = index - 1;
    loadRecord(PageCode);
  }
  setRecordState(PageCode);
}

//初始化页
function iniRecord(PageCode)
{
  var i = 0;
  var elementIndex = 0;
  var elements;
  onePageData=new Array();
  onePageData["field"] = new Array();
  //得到Input域的名字
  elements = document.all(PageCode).tBodies.item(0).getElementsByTagName("input");
  for(i=0;i<elements.length;i++)
  {
    onePageData["field"][elementIndex] = elements[i].name;
    elementIndex++;
  }
  //得到Select域的名字
  elements = document.all(PageCode).tBodies.item(0).getElementsByTagName("select");
  for(i=0;i<elements.length;i++)
  {
    onePageData["field"][elementIndex] = elements[i].name;
    elementIndex++;
  }
  //得到textarea域的名字
  elements = document.all(PageCode).tBodies.item(0).getElementsByTagName("textarea");
  for(i=0;i<elements.length;i++)
  {
    onePageData["field"][elementIndex] = elements[i].name;
    elementIndex++;
  }

  onePageData["currentIndex"]=-1;        //当前记录索引
  onePageData["count"]=0;

  for(i=0;i<onePageData["field"].length;i++)
  {
    eval("fm." + onePageData.field[i] + ".value=''");  
  }
  allPageData[PageCode]=onePageData;
  disableRecordInput(PageCode);
  setRecordState(PageCode);
  
}

//使一页对应的输入域disable
function disableRecordInput(PageCode)
{
  var i = 0;
  onePageData = allPageData[PageCode];
  for(i=0;i<onePageData["field"].length;i++)
  {
    eval("fm." + onePageData.field[i] + ".disabled=true");
  }
  return true;
}

//使一页对应的输入域enable
function enableRecordInput(PageCode)
{
  var i = 0;
  onePageData = allPageData[PageCode];
  for(i=0;i<onePageData["field"].length;i++)
  {
    eval("fm." + onePageData.field[i] + ".disabled=false");
  }
  return true;
}

//对输入域是否是日期的校验(日期格式xxxx/xx/xx)
function isDateI(date)
{
  var strValue;
  strValue=date.split("/");

  if(strValue.length!=3) return false;
  if(!isInteger(strValue[0]) || !isInteger(strValue[1]) || !isInteger(strValue[2]) ) return false;

  var intYear=eval(strValue[0]);
  var intMonth=eval(strValue[1]);
  var intDay=eval(strValue[2]);

  if( intYear<0 || intYear>9999 || intMonth<0 || intMonth>12 || intDay<0 || intDay>31 ) return false;
  return true;
}

//比较两个日期字符串(日期格式xxxx/xx/xx)
// date1=date2则返回0 , date1>date2则返回1 , date1<date2则返回2
function compareDateI(date1,date2)
{
  var strValue=date1.split("/");
  var date1Temp=new Date(strValue[0],strValue[1],strValue[2]);

  strValue=date2.split("/");
  var date2Temp=new Date(strValue[0],strValue[1],strValue[2]);

  if(date1Temp.getTime()==date2Temp.getTime())
    return 0;
  else if(date1Temp.getTime()>date2Temp.getTime())
    return 1;
  else
    return 2;
}

//对输入域是否是满足查询格式的日期的校验(日期格式xxxx/xx/xx)
function isQueryDate(sign,date)
{
  var strValue;

  //区间的判断
  if (sign==":")
  {
  	strValue=date.split(":");
  	if (strValue.length!=2) return false;
  	if (!isDateI(strValue[0])) return false;
  	if (!isDateI(strValue[1])) return false;
  	if (compareDateI(strValue[0],strValue[1])==1) return false;
	}
	//单一日期的判断
	else
	{
		return isDateI(date);
	}
  return true;
}

//对输入域是否是满足查询格式的整数的校验integer
function isQueryInteger(sign,integer)
{
  var strValue;

  //区间的判断
  if (sign==":")
  {
  	strValue=integer.split(":");
  	if (strValue.length!=2) return false;
  	if (!isInteger(strValue[0])) return false;
  	if (!isInteger(strValue[1])) return false;
  	if (strValue[0]>strValue[1]) return false;
	}
	//单一日期的判断
	else
	{
		return isInteger(integer);
	}
  return true;
}

//对输入域是否是满足查询格式的数字的校验
function isQueryNum(sign,num)
{
  var strValue;

  //区间的判断
  if (sign==":")
  {
  	strValue=num.split(":");
  	if (strValue.length!=2) return false;
  	if (!isNumeric(strValue[0])) return false;
  	if (!isNumeric(strValue[1])) return false;
  	if (strValue[0]<strValue[1]) return false;
	}
	//单一日期的判断
	else
	{
		return isNumeric(num);
	}
  return true;
}


//给新***代码赋值 --代码维护模块专用onblur=setNewCode(this)
function setNewCode(field)
{
  if( trim(fm.all("new"+field.name).value)=="" )
  {
    fm.all("new"+field.name).value = field.value;
  }
}


//对输入域是否是日期的校验
function isCodeDate(date)
{
  var strValue;
  strValue=date.split("/");

  if(strValue.length!=3) return false;
  if(!isInteger(strValue[0]) || !isInteger(strValue[1]) || !isInteger(strValue[2]) ) return false;

  var intYear=eval(strValue[0]);
  var intMonth=eval(strValue[1]);
  var intDay=eval(strValue[2]);

  if( intYear<0 || intYear>9999 || intMonth<0 || intMonth>12 || intDay<0 || intDay>31 ) return false;
  return true;
}

/**
 * 检查输入域是否为空
 * @param field 输入域
 * @return 如果输入域的值为null或空，则返回true，否则返回false
 */
function isEmptyField(field){
	var fieldValue = replace(field.value, " ", "");
	fieldValue = fieldValue.replace(/\s/g,"");
    if(fieldValue==null || fieldValue==""){
        return true;
    }
    return false;
}

function deleteRow_new(field,pageCode)
{ 
  var obj;
  var index;

  //Call beforeDeleteRow of pageCode
  obj = eval("window.beforeDelete" + pageCode);
 
  if(obj != null)
  {
    obj.apply(obj,arguments);
  }

  //call realy insertRow of pageCode
  obj = eval("window.delete" + pageCode);
  if(obj != null)
  {
    index = obj.apply(obj,arguments);
  }
  else  //如果没有自定义删除方法则调用默认的删除方法
  {
    index = directDeleteRow(field,pageCode,1,1);
  }

  //Call afterDeleteRow of pageCode
  obj = eval("window.afterDelete" + pageCode);
  if(obj != null)
  {
    obj.apply(obj,arguments);
  }

}

/**
 直接调用删除函数,仅供高级用户使用
 */
function directDeleteRow(field,pageCode,pageDataRowsCount,controlRowsCount)
{
   return private_deleteRow(field,pageCode,pageDataRowsCount,controlRowsCount);
}

/**
  删除控制按钮控制的行，禁止非本模块调用
  字段，页名称，数据页中控制按钮的个数，数据页中每个控制按钮的控制的TR的个数
  返回删除行的序号（从1开始）
 */
function private_deleteRow(field,pageCode,pageDataRowsCount,controlRowsCount)
{
  recentDeletedRowNo = parseInt(getElementOrder(field));
  var order = recentDeletedRowNo - 1;  //顺序改为以0开始
  var oTBODY   = document.getElementById(pageCode).tBodies.item(0);
  order = order - pageDataRowsCount;  //去掉隐含域中的控制按钮的个数
  for(var i=0;i<controlRowsCount;i++)
  {
    oTBODY.removeChild(oTBODY.rows[order*controlRowsCount]);
  }
  return recentDeletedRowNo-1;
}

//分割代码并放在select域里
//串的格式: 值FIELD_SEPARATOR文本GROUP_SEPARATOR值FIELD_SEPARATOR文本...
function setOption(selectName,strValue)
{
  //查不到代码返回
  if(strValue==null || trim(strValue)=="")
  {
    return;
  }

  var arrayField=strValue.split(GROUP_SEPARATOR);
  var i=0;
  var j=0;
  var intCount = getElementCount(selectName);

  if(intCount>1)
  {
    for(j=0;j<intCount;j++)
    {
      fm.all(selectName)[j].options.length = 0;
    }
  }
  else
  {
    fm.all(selectName).options.length = 0;
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

        fm.all(selectName)[j].add(option);
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
      fm.all(selectName).add(option);
    }
    i++;
  }
}


/**
 插入函数,Framework使用
 */
function insertRow_new(pageCode)
{
  var obj;
  var index;

  //Call beforeInsertRow of pageCode
  obj = eval("window.beforeInsert" + pageCode);
  if(obj != null)
  {
    obj.apply();
  }

  //call realy insertRow of pageCode
  obj = eval("window.insert" + pageCode);
  if(obj != null)
  {
    index=obj.apply();
  }
  else  //如果没有自定义添加方法则调用默认的添加方法
  {
    index=directInsertRow(pageCode,pageCode+"_Data");
  }

  //Call afterInsertRow of pageCode
  obj = eval("window.afterInsert" + pageCode);
  if(obj != null)
  {
    obj.apply();
  }
}

/**
 直接调用插入函数,仅供高级用户使用
 */
function directInsertRow(pageCode,dataPageCode)
{
   return private_insertRow(pageCode,dataPageCode);
}

/**
  在表格下方添加一组数据，禁止非本模块调用
  参数为页代码名称和页原始数据代码名称
  例:insertRow("Engage","Engage_Data");
  返回插入行的序号（从1开始）
  */
function private_insertRow(pageCode,dataPageCode)
{

  var oTBODY     = document.getElementById(pageCode).tBodies.item(0);
  var oTBODYData = document.getElementById(dataPageCode).tBodies.item(0);
  for(var i=0;i<oTBODYData.rows.length;i++)
  {
    oTBODY.appendChild(oTBODYData.rows[i].cloneNode(true));
  }

  return private_getRowsCount(pageCode);
}

/**
 * 得到Table的所有元素
 * @param tableId 表名称
 * @return table的所有元素
 */
function getTableElements(tableId){
    var i = 0;
    var elements=new Array();
    var tempElements = null;
    var tbody;
    var index=0;
    var tbodies = document.getElementById(tableId).tBodies;
    for(i=0;i<tbodies.length;i++){
        tbody=tbodies.item(i);
        tempElements=tbody.getElementsByTagName("INPUT");        //加入INPUT域

        for(i=0;i<tempElements.length;i++){
            elements[index++]=tempElements[i];
        }

        tempElements=tbody.getElementsByTagName("SELECT");     //加入SELECT域
        for(i=0;i<tempElements.length;i++){
            elements[index++]=tempElements[i];
        }

        tempElements=tbody.getElementsByTagName("TEXTAREA"); //加入TEXTAREA域
        for(i=0;i<tempElements.length;i++){
            elements[index++]=tempElements[i];
        }
    }
    return elements;
}

/**
 * 检查是否显示明细
 * @return 是否显示明细
 */
function isVerbose(){
    return VERBOSE;
}

/**
 * 打印日志信息
 */
function log(value){
    if(isVerbose()){
        window.status=value;
    }
}

/**
 * 传入element是否是Document中的name相同的elements中的第0个 
 * @param field element
 * @return 是返回true，否则返回false
 */
function isFirstElement(field){
    var elements = document.getElementsByName(field.name);
    if(elements[0]==field){
    	return true;
    }
    else{
    	return false;
    }
}

/**
 * 正则表达式测试
 * @param source 传人字符串
 * @param re 正则表达式
 * @return 正则表达式测试结果
 */
function regExpTest(resource,re)
{
  var result = false;

  if(resource==null || resource=="")
    return false;

  if(resource==re.exec(resource))
    result = true;

  return result;
} 

/**
 * 得到字符串的字节长度
 * @param value 字符串
 * @return 字符串的字节长度
 */
function getByteLength(value)
{
  var str;
  var count  = 0;

  for(var i=0;i<value.length;i++)
  {
    str = escape(value.charAt(i));
    if(str.length==6)
      count = count + 2;
    else
      count = count + 1;
  }

  return count;
}

//替换字符串函数
function replace(strExpression,strFind,strReplaceWith)
{
  var strReturn;
  var intIndex;
  strReturn = (strExpression==null?"":strExpression);

  while((intIndex=strReturn.indexOf(strFind))>-1)
  {
    strReturn = strReturn.substring(0,intIndex) + strReplaceWith
               + strReturn.substring(intIndex+strFind.length,strReturn.length);
  }
  return strReturn;
}

//去掉字符串头尾空格
function trim(s)
{
  var strReturn;
  strReturn=s;
  if(s==null)
  {
    return null;
  }

  while(strReturn.indexOf(" ")==0) strReturn=strReturn.substring(1);
  if(strReturn.length==0) return "";
  while(strReturn.lastIndexOf(" ")==strReturn.length-1)
  {
    strReturn=strReturn.substring(0,strReturn.length-1);
    if(strReturn.length==0) return "";
  }
  return strReturn;
}



//离开域时的数字校验Datetime
function checkDatetime(field,from,to)
{
  field.value = trim(field.value);
  field.value = replace(field.value,"/","-");
  var strValue=field.value;
  var desc   = field.description;
  //如果description属性不存在，则用name属性
  if(desc==null)
    desc = field.name;

  if(strValue=="")
  {
    return true;
  }
  from = from.toLowerCase();
  to = to.toLowerCase();

  if(from=="year" && to=="month")
  {
    if(isNumeric(field.value))
    {
      if(strValue.length>4)
      {
        strValue = strValue.substring(0,4) + "-" + strValue.substring(4);
        field.value = strValue;
      }
    }

    if(regExpTest(strValue,/[\d]{4}[-][\d]{1,2}/)==false)
    {
      errorMessage(i18n.messages.inputValid + desc +"\n" + i18n.messages.typeFormat2);
      field.focus();
      field.select();
      return false;
    }

    var month = parseInt(replace(strValue.substring(strValue.indexOf("-")+1),"0",""),10);
    if(!(month>=1 && month<=12))
    {
      errorMessage(i18n.messages.inputValidMonth);
      field.focus();
      field.select();
      return false;
    }
  }
  else if(from=="year" && to=="minute")
  {
    if(isNumeric(field.value))
    {
      if(strValue.length==12)
      {
        strValue = strValue.substring(0,4) + "-" + strValue.substring(4,6) + "-" + strValue.substring(6,8) + " " + strValue.substring(8,10) + ":" + strValue.substring(10,12);
        field.value = strValue;
      }
      else if(strValue.length==8)
      {
        strValue = strValue.substring(0,4) + "-" + strValue.substring(4,6) + "-" + strValue.substring(6,8) + " " + "00:00";
        field.value = strValue;
      }
    }
    if( regExpTest(strValue,/[\d]{4}[-][\d]{1,2}[-][\d]{1,2} [\d]{1,2}:[\d]{1,2}:[\d]{1,2}/)==false)
    {
      errorMessage(i18n.messages.inputValid + desc +"\n" + i18n.messages.dateFormat2);
      field.focus();
      field.select();
      return false;
    }
    var pos = strValue.indexOf(" ");
    var tempDate = strValue.substring(0,pos);
    strValue = strValue.substring(pos+1);
    if(!isDate(tempDate,"-"))
    {
      errorMessage(i18n.messages.inputValidDate);
      field.focus();
      field.select();
      return false;
    }
    pos = strValue.indexOf(":");
    var hour = parseInt(strValue.substring(0,pos),10);
    var minute = parseInt(strValue.substring(pos+1),10);
    if(!(hour>=0 && hour<=24))
    {
      errorMessage(i18n.messages.inputValidHour);
      field.focus();
      field.select();
      return false;
    }
    if(!(minute>=0 && minute<=59))
    {
      errorMessage(i18n.messages.inputValidMin);
      field.focus();
      field.select();
      return false;
    }
  }
  else
  {
    errorMessage("Not support now!");
    return false;
  }
  return true;
}

/**
  得到一页的多行记录的记录数
*/
function private_getRowsCount(pageCode)
{
  var oTBODY   = document.getElementById(pageCode).tBodies.item(0);
  var intCount = oTBODY.rows.length;
  return intCount;
}

 //去掉字符串头空格
function leftTrim(strValue)
{
  var re =/^\s*/;
  if(strValue==null)
    return null;

 strValue= "" + strValue;
  var strReturn = strValue.replace(re,"");

  return strReturn;
}

//去掉字符串尾空格
function rightTrim(strValue)
{
  var re =/\s*$/;
  if(strValue==null)
    return null;

  var strReturn = strValue.replace(re,"");

  return strReturn;
}

//对输入域按键时的数字校验
function pressDecimal(e)
{
  var value = String.fromCharCode(e.keyCode);
  if((value>=0 && value<=9) || value==".")
    return true;
  else
    return false;
}

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
     if(strValue.length < 9)
    {
      strValue = strValue.substring(0,4) + DATE_DELIMITER + strValue.substring(4,6) + DATE_DELIMITER + strValue.substring(6);
      field.value = strValue;
    }
     else
     {
     	  errorMessage(i18n.messages.inputValid + desc +"\n" + i18n.messages.dateFormat1);
        field.value="";
        field.focus();
        field.select();
        return false;	
     }
  }
  if( !isDate(strValue,DATE_DELIMITER) && !isDate(strValue)||strValue.substring(0,1)=="0")
  {
    errorMessage(i18n.messages.inputValid + desc +"\n" + i18n.messages.dateFormat1);
    field.value="";
    field.focus();
    field.select();
    return false;
  }
  return true;
}


function setFormAllEnabled()
{
  var i = 0;
  for(i=0;i<fm.elements.length;i++)
  {
    fm.elements[i].disabled=false;
  }
}

function openWindow(strURL,strName)
{
  var newWindow = window.open(strURL,strName,'width=640,height=480,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
  newWindow.focus();
  return newWindow;
}