/**
 * 公用JavaScript(兼容IE5/NN6)--平台中心组控制，项目组不得修改，否则後果自负
 * 如果发现有问题或需求，请通知提供者
 * 如果方法没有用private开头,则方法是公开的,且保证向後兼容.
 * 最後更新:2004-08-11
 */
/** 全局变量bCancel; */
var bCancel = false;
var DATE_DELIMITER = "-";
var DB_INT_LENGTH = 64; //数据库位数,即整数长度，默认为64位
var MAX_INTEGER  = Math.pow(2,DB_INT_LENGTH-1) - 1;
var MIN_INTEGER  = -Math.pow(2,DB_INT_LENGTH-1);
var MAX_SMALLINT = Math.pow(2,DB_INT_LENGTH/4-1) - 1;
var MIN_SMALLINT = -Math.pow(2,DB_INT_LENGTH/4-1);
var VERBOSE = false;//显示所有明细，开发环境中可以调用setVerbose(true)
var FIELD_SEPARATOR = "_FIELD_SEPARATOR_";   //字段之间的分割符
var GROUP_SEPARATOR = "_GROUP_SEPARATOR_";     //一组代码之间的分割符

/**
 * 设置日期分割符，默认为'/'
 * @param delimiter 日期分割符
 */
function setDateDelimiter(delimiter){
    DATE_DELIMITER = delimiter;
}
/**
 * 设置数据库整数长度，默认为64
 * @param len 整数长度
 */
function setDBIntLength(len){
    DB_INT_LENGTH = len;
    MAX_INTEGER  = Math.pow(2,DB_INT_LENGTH-1) - 1;
    MIN_INTEGER  = -Math.pow(2,DB_INT_LENGTH-1);
    MAX_SMALLINT = Math.pow(2,DB_INT_LENGTH/4-1) - 1;
    MIN_SMALLINT = -Math.pow(2,DB_INT_LENGTH/4-1);
}

/**
 * 设置是否显示明细，默认为不显示
 * @param verbose 日期分割符
 */
function setVerbose(verbose){
    VERBOSE = verbose;
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
 * 判断客户端浏览器是否为Netscape
 * @return 客户端浏览器为Netscape则返回true,否则返回false;
 */
function isNetscape(){
    if(navigator.appName=="Netscape"){
        return true;
    }else{
        return false;
    }
}

/**
 * 是否是IE6
 * @since 2004-12-07
 * @return 是返回ture，否则返回false
*/
function isIE6(){
   if(navigator.appVersion.indexOf("MSIE 6")>-1){
        return true;
   }else{
        return false;
   }
}

var verbose = false;//显示所有明细，生产环境中应改为false

/**
 * 判断客户端浏览器是否为Netscape
 * @return 客户端浏览器为Netscape则返回true,否则返回false;
 */
function isNetscape()
{
  if(navigator.appName=="Netscape")
    return true;
  else
    return false;
}

/**
 * 展开“+”号内容或隐藏“—”号内容；
 *
 */
function showPage(img,spanID)
{

  if(spanID.style.display=="")
  {
   //关闭
    spanID.style.display="none";
    img.src="/claim/images/butCollapseBlue.gif";
  }
  else
  {
   //展开
    spanID.style.display="";
    img.src="/claim/images/butExpandBlue.gif";
  }
}

/**
 * 得到传入element在Document中的name相同的elements中的顺序(从1开始)
 * @param field element
 * @return 传入element在Document中的name相同的elements中的顺序(从1开始)
 */
function getElementOrder(field)
{
    var i = 0;
    var order = 0;
    var elements = document.getElementsByName(field.name);
    for(i=0;i<elements.length;i++)
    {
        order++;
        if(elements[i]==field)
        {
            break;
        }
    }

    return order;
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
 * 查找在Document中的element的name属性等如传入值的element个数，没有则返回0
 * @param fieldName 元素名称
 * @return 在Document中的element的name属性等如传入值的element个数
 */
function getElementCount(fieldName)
{
    var count = 0;
    count = document.getElementsByName(fieldName).length;
    return count;
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

/**
 * 得到Table的所有元素
 * @param tableId 表名称
 * @return table的所有元素
 */
function getTableElements(tableId){
	var i = 0;
	var elements = new Array();
	var tempElements = null;
	var tbody;
	var index = 0;
	try {
		var tbodies = document.getElementById(tableId).tBodies;
		for (i = 0; i < tbodies.length; i++) {
			tbody = tbodies.item(i);
			tempElements = tbody.getElementsByTagName("INPUT"); // 加入INPUT域
			for ( var j = 0; j < tempElements.length; j++) {
				elements[index++] = tempElements[j];
			}
			tempElements = tbody.getElementsByTagName("SELECT"); // 加入SELECT域
			for ( var j = 0; j < tempElements.length; j++) {
				elements[index++] = tempElements[j];
			}
			tempElements = tbody.getElementsByTagName("TEXTAREA"); // 加入TEXTAREA域
			for ( var j = 0; j < tempElements.length; j++) {
				elements[index++] = tempElements[j];
			}
		}
	} catch (e) {
		
	}
	return elements;
}

/**
 * 去掉字符串头空格
 * @param value 传人字符串
 * @return 去掉头空格後的字符串
 */
function leftTrim(value)
{
  var re =/^\s*/;
  if(value==null)
    return null;

  return value.replace(re,"");
}

/**
 * 去掉字符串尾空格
 * @param value 传人字符串
 * @return 去掉尾空格後的字符串
 */
function rightTrim(value)
{
  var re =/\s*$/;
  if(value==null)
    return null;

  return value.replace(re,"");
}

/**
 * 去掉字符串头尾空格
 * @param value 传人字符串
 * @return 去掉头尾空格後的字符串
 */
function trim(value)
{
  return leftTrim(rightTrim(value));
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
 * 替换字符串函数
 * @param str 原串
 * @param strFind 查找串
 * @param strReplaceWith 替换串
 * @return 返回替换後的字符串
 */
function replace(str,strFind,strReplaceWith){
    var strReturn;
    var re = new RegExp(strFind,"g");
    if(str==null){
        return null;
    }
    strReturn = str.replace(re,strReplaceWith);
    return strReturn;
}

/**
 * 检查输入域是否为空
 * @param field 输入域
 * @return 如果输入域的值为null或空，则返回true，否则返回false
 */
function isEmptyField(field){
    if(field.value==null || trim(field.value)==""){
        return true;
    }
    return false;
}

/**
 * mantis：CLM0244 ，處理人員： DP0713 ，需求單編號：新核心-車險立案預估金額判斷新增不可為零
 * 检查输入域是否为空 不可為0
 * @param field 输入域
 * @return 如果输入域的值为null或空，则返回true，否则返回false
 */
function isEmptyZeroField(field){
    if(field.value==null || trim(field.value)=="" || trim(field.value)=="0"){
        return true;
    }
    return false;
}


//打印日志信息
function log(value){
    if(verbose==true){
        window.status=(value);
    }
}

//关联
function relateProposalNo(strProposalNo)
{
		if(strProposalNo == null || trim(strProposalNo).length != 22)
		{
		 	alert(i18n.common.notProvideEffectiveNo);  //没有提供有效的22位投保单号！
		 	return;
		}

  var strURL = "/claim/common/pub/RelateProposalNo.jsp?ProposalNo="+strProposalNo;
  var newWindow = window.open(strURL,"Relate",'width=640,height=300,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
  newWindow.focus();
}


//对数字四舍五入
//数值,精度
function round(number,precision)
{
  if(isNaN(number))
    number = 0;
  var prec = Math.pow(10,precision);
  var result = Math.round( number * prec) ;
  result = result/prec;
  return result;
}


//对数字进行格式化,保证precision位
function point(number,precision)
{
	if(precision!=0){
  if(isNaN(number))
    number = 0;
  var result = number.toString();
  if(result.indexOf(".")==-1)
    result = result + ".";

  result = result + newString("0",precision);
  result = result.substring(0,precision + result.indexOf(".") + 1);
	}
	else{
		var result = number.toString();
		if(result.indexOf(".")==-1){
			result = parseInt(result);
		}
		else{
			result = round(result,0);
			result = parseInt(result);
		}
	}
  return result;
}

//对数字第三位四舍五入
function mathRound(number)
{
  return round(number,2);
}

//对数字按0.00格式化,外币需要保留2位小数
function pointTwo(s,currency){
	if(currency==null||currency==undefined||currency==""||currency=="NTD"){
		return point(s, 0);
	}
	return round(s, 2);
}

//对数字按0.0000 格式化
function pointFour( s )
{
	return point(s, 0);
}

//对数字格式化，delimiterChar默认为"," precision默认为3
function numberFormat(ivalue,delimiterChar,precision)
{
  if((ivalue==null) || (ivalue==""))
    return "";

  if(delimiterChar==null || delimiterChar=="")
    delimiterChar = ",";

  if(precision==null || precision =="")
    precision = 3;

  var i = 0;
  var ovalue = "";
  var times;

  var avalue = "";
  if(ivalue.indexOf(".")>-1)
  {
    avalue = "." + ivalue.substring(ivalue.indexOf(".")+1);
    ivalue = ivalue.substring(0,ivalue.indexOf("."));
  }

  times = ivalue.length % precision;
  if(times!=0)
  {
    ovalue = ivalue.substring(0,times);
    ivalue = ivalue.substring(times);
  }

  for(i=0;i<ivalue.length;i++)
  {
    if(i%precision==0)
    {
      ovalue += delimiterChar;
    }
    ovalue += ivalue.substring(i,i+1)
  }

  if(ovalue.substring(0,1) == delimiterChar)
    ovalue = ovalue.substring(1);


  return ovalue + avalue;
}


/**
 * 格式化数字
 * @param value 值
 * @param count 分割位数 默认为3
 * @param precision 小数点保留位数 默认为2
 * @param delimiterChar 分割符 默认为','
 */
function formatFloat(value,count,precision,delimiterChar)
{
  count = count==null?3:count;
  precision = precision==null?2:precision;
  delimiterChar = delimiterChar==null?",":delimiterChar;


  var strReturn = ""; //返回值
  var strValue = point(round(value,precision),precision); //格式化成指定小数位数

  strReturn = strValue.substring(strValue.length-precision-1);
  strValue = strValue.substring(0,strValue.length-precision-1);
  while(strValue.length>count)
  {
    strReturn = delimiterChar + strValue.substring(strValue.length-count) + strReturn;
    strValue = strValue.substring(0,strValue.length-count);
  }

  strReturn = strValue + strReturn;
  return strReturn;
}


//分割代码並放在select域里
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
 * 将给定字符串复制ｎ遍
 * @param intLength 字符串长度
 * @return 字符串
 */
function newString(iString, iTimes)
{
  var str = "";
  for (var i = 0 ; i < iTimes; i++)
     str = str + iString;
  return str;
}


/**
 * 功能：将输入域变成只读，同时将CSS的属性变成只读
 * return true/false
 */
function readonlyAllInput()
{
  var testStr = "" ;
  var tempElements = null;

  for(i=0; i<document.all.length; i++)
  {
    //alert(document.all(i).tagName);
    if(document.all(i).tagName=="INPUT")
    {
        tempElements = document.all(i);
        //将输入域变为只读
        if(tempElements.type=="text")
        {
	    	tempElements.style.fontSize="11pt";
	    	tempElements.style.borderTop = "none";
	    	tempElements.style.borderBottom = "none";
	    	tempElements.style.borderRight= "none";
	    	tempElements.style.borderLeft = "none" ;
	    	//tempElements.style.width="80%";
	    	tempElements.style.color = "#000000";
	    	tempElements.style.backgroundColor = "#F4F9FF";
        	tempElements.readOnly=true;
        }
        //将输入域变为只读
        if(tempElements.type=="radio")
        {
        	tempElements.disabled=true;
        }
        //将"+""-"按钮变为只读
        if(tempElements.type=="button"){
        	if(tempElements.value == "+" || tempElements.value == "-"){
        		tempElements.disabled=true;
        	}
          }
        ////将输入域变为只读
        //if(tempElements.type=="submit")
        //{
        //	tempElements.disabled=true;
        //}
    }
    //将选择域变为只读
    if(document.all(i).tagName=="SELECT")
    {
    	tempElements = document.all(i);
    	tempElements.disabled = true;
    }
    //将选择域变为只读
    if(document.all(i).tagName=="TEXTAREA")
    {
    	tempElements = document.all(i);
    	tempElements.readOnly=true;
    	tempElements.style.backgroundColor = "RGB(247,247,247)";
    }

  }
}


/**
 * 功能：将输入域变成可写，同时将CSS的属性变成可写
 * return true/false
 */
function ableAllInput()
{
  var testStr = "" ;
  var tempElements = null;

  for(i=0; i<document.all.length; i++)
  {
    //alert(document.all(i).tagName);
    if(document.all(i).tagName=="INPUT")
    {
        tempElements = document.all(i);
        //将输入域变为可写
        if(tempElements.type=="text")
        {
  	    	tempElements.style.fontSize="11pt";
  	    	tempElements.style.borderTop = "none";
  	    	tempElements.style.borderBottom = "none";
  	    	tempElements.style.borderRight= "none";
  	    	tempElements.style.borderLeft = "none" ;
  	    	//tempElements.style.width="80%";
  	    	tempElements.style.color = "#000000";
  	    	tempElements.style.backgroundColor = "#F4F9FF";
          tempElements.readOnly=false;
        }
        //将输入域变为可写
        if(tempElements.type=="radio")
        {
        	tempElements.disabled=false;
        }
        //将输入域变为可写
        if(tempElements.type=="checkbox")
        {
        	tempElements.disabled=false;
        }
    }
    //将选择域变为只读
    if(document.all(i).tagName=="SELECT")
    {
    	tempElements = document.all(i);
    	tempElements.disabled = false;
    }
    //将选择域变为只读
    if(document.all(i).tagName=="TEXTAREA")
    {
    	tempElements = document.all(i);
    	tempElements.disabled = false;
    }

  }

}
/**
 * 功能： 按钮域的按钮域变成可读
 * @param tableID 含有按钮的表ID
 */

 function disabledAllButton(tableId)
 {
   var elements = getTableElements(tableId);

   for(var i=0;i<elements.length;i++)
   {
       if(elements[i].name=="buttonBack"||elements[i].name=="print"||elements[i].name=="buttonClose"){ //如果名字为buttonBack,print,buttonClose则继续
       	continue;
       } 
       //将button设成不可用
       if(elements[i].type == "button")
       {  
       	 elements[i].disabled = true;
       }
       //将submit设成不可用
       if(elements[i].type == "submit")
       {
          elements[i].disabled = true;
       }
       //将reset设成不可用
       if(elements[i].type == "reset")
       {
          elements[i].disabled = true;
       }

    }
 }
 //计算两个日期的差,返回差的月数(M)或天数(D)
//2003/3/17 改为(其中天数包含2.29这一天)
function dateDiff(dateStart,dateEnd,MD)
{
  var i;
  if(MD=="D") //按天计算差
  {
    var endTm   = dateEnd.getTime();
    var startTm = dateStart.getTime();
    var diffDay = (endTm - startTm)/86400000 + 1;

    return diffDay;
  }
  else //按月计算差
  {
    var endD   = dateEnd.getDate();
    var endM   = dateEnd.getMonth();
    var endY   = dateEnd.getFullYear();
    var startD = dateStart.getDate();
    var startM = dateStart.getMonth();
    var startY = dateStart.getFullYear();

    if(endD>startD) //跟终端版fcalc_month函数统一，endD>startD时才加1
    {
      return (endY-startY)*12 + (endM-startM) + 1;
    }
    else
    {
      return (endY-startY)*12 + (endM-startM);
    }
  }
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
      errorMessage("输入日期格式错误, 格式为 YYYY-MM-DD 或 YYYYMMDD");
      field.value="";
      field.focus();
      field.select();
      return false;
    }
  }
  if( !isDate(strValue,DATE_DELIMITER) && !isDate(strValue)||strValue.substring(0,1)=="0")
  {
    errorMessage("输入日期格式错误, 格式为 YYYY-MM-DD 或 YYYYMMDD");
    field.value="";
    field.focus();
    field.select();
    return false;
  }
  return true;
}
function isNumeric(strValue)//验证大于0的整数和小数
{
  var result = regExpTest(strValue,/\d*[.]?\d*/g);
  return result;
}

function regExpTest(source,re)
{
  var result = false;

  if(source==null || source=="")
    return false;

  if(source==re.exec(source))
    result = true;

  return result;
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

function isInteger(strValue)
{
  var result = regExpTest(strValue,/\d+/g);
  return result;
}
//将封住的按钮放开，用於dwr控制
function undoDisabledButton(tableId)
{
   var elements = getTableElements(tableId);

   for(var i=0;i<elements.length;i++)
   {
   		if(elements[i].name=="buttonBack"||elements[i].name=="buttonClose"){ //如果名字为buttonBack,buttonClose则继续
       	continue;
      } 
       //将button设成可用
       if(elements[i].type == "button")
       {  
       	 elements[i].disabled = false;
       }
       //将submit设成可用
       if(elements[i].type == "submit")
       {
          elements[i].disabled = false;
       }
       //将reset设成可用
       if(elements[i].type == "reset")
       {
          elements[i].disabled = false;
       }

    }
}

/**
 * 输入域汉字长度限制
 * @param value value
 * @param byteLength 限制长度
 * @param title 输入域名称
 * @param attribute 输入域ID属性值
 */
function limitLength(value, byteLength, title, attribute) {
	var newvalue = value.replace(/[^\x00-\xff]/g, "**");
	var length = newvalue.length;
	if (length * 1 <= byteLength * 1) {
		return;
	}
	var limitDate = newvalue.substr(0, byteLength);
	var count = 0;
	var limitvalue = "";
	for (var i = 0; i < limitDate.length; i++) {
		var flat = limitDate.substr(i, 1);
		if (flat == "*") {
			count++;
		}
	}
	var size = 0;
	var istar = newvalue.substr(byteLength * 1 - 1, 1);
	if (count % 2 == 0) {
		size = count / 2 + (byteLength * 1 - count);
		limitvalue = value.substr(0, size);
	} else {
		size = (count - 1) / 2 + (byteLength * 1 - count);
		limitvalue = value.substr(0, size);
	}
	alert(title + "\u6700\u5927\u8f93\u5165" + byteLength + "\u4e2a\u5b57\u8282\uff08\u76f8\u5f53\u4e8e" + byteLength / 2 + "\u4e2a\u6c49\u5b57\uff09\uff01");
	document.getElementById(attribute).value = limitvalue;
	return;
}/**
 * 隐藏按钮域
 * @param buttonName 按钮域name属性值
 */
function hideButton(buttonName) {
    for (var i=0;i<document.getElementsByName(buttonName).length;i++) {
        document.getElementsByName(buttonName)[i].style.display="none";
    }
}

//---------------差异化新增的部分
   //缓存input获得焦点时的值 onfocus事件,作用是:当前校验项失去焦点时，若本次录入不通过校验则还原其本次值
   function cacheData(field){
      $(field).data(field.name,field.value);
   }
   //还原 field 域的值，作用是：本次录入通不过校验，则还原域的值为其获得焦点时的值
   function recoveryData(field){
      $(field).val($(field).data(field.name));
   }
   //判断值域是否改变
   function isChange(field){
      return field.value != $(field).data(field.name);
   }
   //理算校验金额录入 最好在失去焦点时校验
   function validateMoney(field){
      if($.trim(field.value)==""||isNaN(field.value)){
         recoveryData(field);
         return alertMessage(field,$(field).attr("title")+"必須是正確的金額輸入!");
      }
      field.value = Math.round(parseFloat(field.value));
      return true;
   }
   //理算校验比例录入 最好在失去焦点时校验
   function validatePercent(field,min,max){
      if($.trim(field.value)==""||isNaN(field.value)||parseFloat(field.value)<parseFloat(min)||parseFloat(field.value)>parseFloat(max)){
         recoveryData(field);
         return alertMessage(field,$(field).attr("title")+"必須在"+min+"%與"+max+"%之間 !");
      }
      return true;
   }
   //理算校验年龄录入 最好在失去焦点时校验
   function validateAge(field,min,max){
      if($.trim(field.value)!=''&& (isNaN(field.value)||parseFloat(field.value)<parseFloat(min)||parseFloat(field.value)>parseFloat(max))){
         recoveryData(field);
         return alertMessage(field,$(field).attr("title")+"必須在"+min+"與"+max+"之間 !");
      }
      if(field.value!=""){
    	  field.value = parseInt(field.value);
      }
      return true;
   }
   //alert提示消息 且 让其获取鼠标焦点
   function alertMessage(field,message){
       alert(message);
       window.setTimeout(function(){
       	   if($(field).is("input:visible")){
       		   try{
       			 $(field).focus();
       		   }catch(e){
       			   
       		   }
       	   }
       });
       return false;
   }
   
/**
 * oMPC初始化 右上部有按钮的页面
 * 规则：参考 DAACheckEdit.jsp （样式什么的最好从参考页面复制）
 * 1、body下只有一个主div id="mainLayer" style="position:absolute;top:5px;left:2px;z-index:1;"
 * 2、将页面上部按钮放入主div 最上（mpc:container外） id命名btnTable 其他样式同参考页面
 * 3、将页面下部按钮放入主div 最下（mpc:container外） id命名btnCommon,调整TD align="center" 其他样式同参考页面
 * 4、移除mainLayer 及 mpc:page 下 div 的style height值 命名div name="tabMain" 其他样式同参考页面
 * 5、移除oMPC的style 由本函数来控制
 * 6、添加jquery初始化javascript
 */
function initWindow(){
     var initWidth = window.screen.width;
     if(window.parent.fraMenu){
        initWidth -= $(window.parent.fraMenu).width();
     }
     initWidth -= (20 + 2);//减去 （滚动条的20 +mainLayer 的右偏移量）
     $("#oMPC").width(initWidth);//初始化oMPC宽度
        $("#btnCommon").width(initWidth);//右上部按钮的位置
        $("#btnTable").css("left",initWidth-$("#btnTable").width());//下面通用按钮的位置
        //mainLayer、tabMain、oMPC的高 = window的高 - 上面按钮区的高 - 下面按钮区的高 - mainLayer的top位移
        var initHeight = $(window).height()-$("#btnCommon").height()-$("#btnTable").height()-5;
        $("div[name='tabMain']").each(function(){
           $(this).height(initHeight);
        });
        //下面按钮区的相对位移为
        $("#btnCommon").css("top",initHeight+$("#btnTable").height());//下面通用按钮的位置
        $("#oMPC").height(initHeight);//初始化oMPC宽度
        $("#mainLayer").height($(window).height()-5);
}
/**
 * oMPC初始化 右上部无按钮的页面
 * 规则同initWindow;区别在於没有了#btnTable後,mainLayer的top偏移量需要增加至27
 * 参考certainLoss DAAPropEdit.jsp
 */
function initWindowNoBtn(){
     var initWidth = window.screen.width;
     if(window.parent.fraMenu){
        initWidth -= $(window.parent.fraMenu).width();
     }
     initWidth -= (20 + 2);//减去 （滚动条的20 +mainLayer 的右偏移量）
     $("#oMPC").width(initWidth);//初始化oMPC宽度
     $("#btnCommon").width(initWidth);//右上部按钮的位置
     //$("#btnTable").css("left",initWidth-$("#btnTable").width());//下面通用按钮的位置
     //mainLayer、tabMain、oMPC的高 = window的高 - 上面按钮区的高 - 下面按钮区的高 - mainLayer的top位移
     //var initHeight = $(window).height()-$("#btnCommon").height()-$("#btnTable").height()-5;
     //上部无按钮则 mainLayer 偏移量为 27
     var initHeight = $(window).height()-$("#btnCommon").height()-27;
     $("div[name='tabMain']").each(function(){
        $(this).height(initHeight);
     });
     //下面按钮区的相对位移为
     $("#btnCommon").css("top",initHeight);//下面通用按钮的位置
     $("#oMPC").height(initHeight);//初始化oMPC宽度
     $("#mainLayer").height($(window).height()-27);
}

/**
 * 台湾身份证校验规则
 * @author 中科软 
 * @param fieldValue 校验值
 * @param sexFlag 性别码 (1:男 2:女 9:未说明)
 * @returns {Boolean} true 合法身份证号码
 */
function checkIdentifyNumber(fieldValue,sexNum){
	/*
	 mantis： CLM0040，處理人員：BK007 蘇哲，需求單編號：CLM0040 外來人口統一證號格式修正 --- start
	  處理過程：
		改使用台壽保統一 證號檢核webservice
		參考文件 P:\01.需求變更\理賠\CLM0040.外來人口統一證號格式修正\證號檢核web Service.docx
	*/
	var resultValue = false;
	$.ajax({
		url : contextRootPath + "/tlg/common/verifyIdentifyNumber.do?checkId=" +fieldValue,
		type : 'GET',
		async : false,
		cache : false,
		dataType: "json",
		contentType: "application/json; charset=utf-8",
		success : function(data) {
			if(data.code == 'S0000' && data.identifyType == '01'){ //01 - 身分證 05 - 居留證 60 - 統一編號
				if(sexNum != '9' && sexNum != data.gender ){
					resultValue = false; //與對應性別不符合
				}
				resultValue = true;
			}else{
				alert(data.msg);
			}
		},
		error: function (jqXHR, textStatus, errorThrown) { 
			alert("checkIdentifyNumber ajax Error:"+errorThrown); 
		}
	});
 	return resultValue;
	/*
	var re = /^[A-Z][1-2]\d{8}$/;//基础匹配(例A123456789)
    if(re.test(fieldValue)){//
    	if(sexNum!='9'&& fieldValue.charAt(1)!=sexNum){
   		 	return false;//与指定的性别不合
    	}
        var weight = "1987654321";//权数 按顺序
		var firstNum ="0123456789ABCDEFGHJKLMNPQRSTUVXYWZIO";//A-Z地区代码，位置索引为其转换码
		var areaCode = fieldValue.substring(0,1);//地区码
		var switchCode = (new String(firstNum.indexOf(areaCode))).concat(fieldValue.substring(1,9));//转换码
		var checkCode = fieldValue.substring(9);//校验码
		var sumValue = 0;//转换码*权数 求和
		for(var i=0;i<=switchCode.length-1;i++){
			sumValue +=parseInt(weight.charAt(i))*parseInt(switchCode.charAt(i))
		}
		return (sumValue+parseInt(checkCode))%10==0
    }
    return false;
    */
 	/* mantis： CLM0040，處理人員：BK007 蘇哲，需求單編號：CLM0040 外來人口統一證號格式修正 --- end */
}

/**
 * 台湾统一编号校验
 * @author 中科软
 * @param fieldValue 校验值
 * @returns {Boolean} true 合法的统一编号
 */
function checkUniformNo(fieldValue) {
	/*
	 mantis： CLM0040，處理人員：BK007 蘇哲，需求單編號：CLM0040 外來人口統一證號格式修正 --- start
	  處理過程：
		改使用台壽保統一 證號檢核webservice
		參考文件 P:\01.需求變更\理賠\CLM0040.外來人口統一證號格式修正\證號檢核web Service.docx
	*/
	var resultValue = false;
	$.support.cors =true;
	$.ajax({
		url : contextRootPath + "/tlg/common/verifyIdentifyNumber.do?checkId=" +fieldValue,
		type : 'GET',
		async : false,
		cache : false,
		dataType: "json",
		contentType: "application/json; charset=utf-8",
		success : function(data) {
			if(data.code == 'S0000' && data.identifyType == '60'){ //01 - 身分證 05 - 居留證 60 - 統一編號
				resultValue = true;
			}
//			alert("code = " + data.code + ",msg = " + data.msg + ",insuredType = " + data.insuredType + ",gender = " + data.gender + ",identifyType = " + data.identifyType);
		},
		error: function (jqXHR, textStatus, errorThrown) { 
			alert("checkUniformNo ajax Error:"+errorThrown); 
		}
	});
	return resultValue;
	/*
	var re = /^\d{8}$/;
	if (re.test(fieldValue)) {
		var weight = "12121241";// 权数 按码位顺序
		var sumValue = 0;// 转换码*权数 求和
		for ( var i = 0; i <= fieldValue.length - 1; i++) {
			var temp = parseInt(fieldValue.charAt(i))* parseInt(weight.charAt(i));
			if (temp >= 10) {
				temp = new String(temp);
				sumValue += parseInt(temp.charAt(0)) + parseInt(temp.charAt(1));
			} else {
				sumValue += temp;
			}
		}
		return sumValue % 10 == 0 || (fieldValue.charAt(6) == '7' && (sumValue + 1) % 10 == 0);
	}
	return false;
	*/
	/* mantis： CLM0040，處理人員：BK007 蘇哲，需求單編號：CLM0040 外來人口統一證號格式修正 --- end */
}

/***
 * 偏移显示一个与当前文本域相关的隐藏对象，
 * @param field 当前文本域
 * @param $div 隐藏目标的jquery对象
 */
function showDiv(field,$div){
    var offset = $(field).offset();
    var width = $(window).width();;
    var height = $(window).height();
    var left = 0;
    var top = 0;
    if(offset.left < width/2){
        left = offset.left;
    }else{
        left = offset.left - $div.width() - 2;
    }
    if(offset.top < height/2){
        top = offset.top + $(field).height() + 5 ;
    }else{
        top = offset.top - $div.height() - 2;//多余减去的2是测试做的微调处理
    }
    var obj = new Object();
    obj.top = top;
    obj.left = left;
    $div.show(0,function(){
        $(this).offset(obj);
    });
    $div.offset(obj);
}

/**
 * jquery 对象设置默认值
 * @param $object
 * @param defaultValue
 * @param 值的币别 金额处理时才有
 */
function initValue($object,defaultValue,exchCurrency){
    var v = $object.val();
    var f = parseFloat(v);
    if ($.trim(v).length == 0 || isNaN(f)) {
        $object.val(defaultValue);
        return defaultValue;
    } else {
        if ("NTD" == exchCurrency || f % 1 == 0) {
            $object.val(Math.round(f));
        }else{
            $object.val(round(f, 2));
        }
    }
    return parseFloat($object.val());
}
/***
 * 根据币别获取金额值，NTD删除小数，其他精确到2位小数
 * @param formatValue 格式化的金額
 * @param currency 當前金額的幣別
 * @returns 格式化后的金額
 */
function getFormatValueByCurrency(formatValue,currency){
    if ("NTD" == currency || formatValue % 1 == 0) {
        return Math.round(formatValue);
    } else {
        return round(formatValue, 2);
    }
}
/**比较两个日期字符串**/
/** date1=date2则返回0 , date1>date2则返回1 , date1<date2则返回2**/
function compareDate(date1, date2) {
	var strValue = date1.split("-");
	var date1Temp = new Date(strValue[0], strValue[1], strValue[2]);
	strValue = date2.split("-");
	var date2Temp = new Date(strValue[0], strValue[1], strValue[2]);

	if (date1Temp.getTime() == date2Temp.getTime())
		return 0;
	else if (date1Temp.getTime() > date2Temp.getTime())
		return 1;
	else
		return 2;
}


/***
 * 校驗出險時間是否可修改
 * @param startDate 起保日期
 */
function checkFlashPage(startDate) {
	var reportDateStr = fm.prpLregistReportDate.value;
	var reportHourStr = fm.prpLregistReportHour.value;
	var reportMinuteStr = fm.prpLregistReportMinute.value;
	var reportDate= new Date(Date.parse(reportDateStr.replace(/-/g,"/")));//转换成Data();
	if($.trim(reportHourStr)=="" || isNaN(reportHourStr) || parseInt(reportHourStr , 10) > 23 || parseInt(reportHourStr , 10) < 0 ){
		alert("備案小時不正確！");
		return false;
	}else{
		reportDate.setHours(parseInt(reportHourStr , 10));
	}
	if($.trim(reportMinuteStr)=="" || isNaN(reportMinuteStr) || parseInt(reportMinuteStr , 10) > 59 || parseInt(reportMinuteStr , 10) < 0 ){
		alert("備案分鐘不正確！");
		return false;
	}else{
		reportDate.setMinutes(parseInt(reportMinuteStr , 10));
	}
	var currDate = new Date();//當前時間
	if(reportDate > currDate){
		alert("備案時間不得超過當前時間！");
		return false;
	}
	var damageDate = fm.prpLregistDamageStartDate.value;
	var damageHour = fm.prpLregistDamageStartHour.value;
	var damageMinute = fm.prpLregistDamageStartMinute.value;
	var dDate= new Date(Date.parse(damageDate.replace(/-/g,"/")));//转换成Data();//修改后出險日期
	if($.trim(damageHour)=="" || isNaN(damageHour) || parseInt(damageHour , 10) > 23 || parseInt(damageHour , 10) < 0 ){
		alert("出險小時錄入不正確！");
		return false;
	}else{
		dDate.setHours(parseInt(damageHour , 10));
	}
	if($.trim(damageMinute)=="" || isNaN(damageMinute) || parseInt(damageMinute , 10) > 59 || parseInt(damageMinute , 10) < 0 ){
		alert("出險分鐘錄入不正確！");
		return false;
	}else{
		dDate.setMinutes(parseInt(damageMinute , 10));
	}
	if(dDate > currDate){
		alert("出險時間在當前時間之後！");
		return false;
	}
	if(dDate < startDate){
		if(!confirm("出險時間在起保時間之前，確定繼續?")){
			return false;
		}
	}
	return true;
}

/***
 * 获取字符长，字母数字算1个，汉字算2个
 * @param val 实际值
 * @param num 显示汉字个数
 * @returns 返回截取字符
 */
function getSubStr(val , num){
	var size = val.length;
	if (size <= num) {
		return val;
	}
	var len = 0;
	var index = 0
	for (; index < size; index++) {
		var length = val.charCodeAt(index);
		if (length >= 0 && length <= 128) {
			len += 1;
		} else {
			len += 2;
		}
		if (len > num * 2) {
			return val.substr(0, index);
		}
	}
	return val.substr(0, index);
}

/***
 * 將 yyyy-mm-dd 西元年時間轉換為yyy-mm-dd 民國時間
 * @param date
 * @returns {String}
 */
function getMGDate(date){
	var d = date.replace(/-/g,"/");
	var sdate = new Date(d);
	var year = sdate.getFullYear()-1911;
	var month = sdate.getMonth() + 1;
	var day = sdate.getDate();
	return (year < 100 ? "0" : "") + year + "-" + (month < 10 ? "0" : "") + month + "-" + (day < 10 ? "0" : "") + day;
}

function checkRegistTime(f){
	if(f.name=="prpLregistDamageStartMinute"){
		if(!(/^[0-5]?\d$/.test(f.value))){
			alert("出險時間分鐘應為 0 ~ 59 ");
			f.value = f.defaultValue;
			return false;
		}
	} else if(f.name=="prpLregistDamageStartHour"){
		if(!(/^((2[0-3])|([0-1]?\d))$/.test(f.value))){
			alert("出險時間小時應為 0 ~ 23 ");
			f.value = f.defaultValue;
			return false;
		}
	} else if(f.name=="prpLregistReportMinute"){
		if(!(/^[0-5]?\d$/.test(f.value))){
			alert("備案時間分鐘應為 0 ~ 59 ");
			f.value = f.defaultValue;
			return false;
		}
	} else if(f.name=="prpLregistReportHour"){
		if(!(/^((2[0-3])|([0-1]?\d))$/.test(f.value))){
			alert("備案時間小時應為 0 ~ 23 ");
			f.value = f.defaultValue;
			return false;
		}
	}
	var prpLregistStartDate = fm.prpLregistStartDate.value;
	var startDate= new Date(Date.parse(prpLregistStartDate.replace(/-/g,"/")));//起保日期
	startDate.setHours(parseInt(fm.prpLregistStartHour.value , 10),0,0);
	var prpLregistEndDate = fm.prpLregistEndDate.value;
	var endDate = new Date(prpLregistEndDate.replace(/-/g,"/"));//終保日期
	endDate.setHours(parseInt(fm.prpLregistEndHour.value , 10),0,0);
	
	var prpLregistDamageStartDate = fm.prpLregistDamageStartDate.value;
	var damageStartHour  = fm.prpLregistDamageStartHour.value;
	var damageStartMinute  = fm.prpLregistDamageStartMinute.value;
	
	var damageStartDate = new Date(prpLregistDamageStartDate.replace(/-/g,"/"));//出險日期
	damageStartDate.setHours(parseInt(damageStartHour , 10),parseInt(damageStartMinute , 10),0);
	
	var prpLregistReportDate = fm.prpLregistReportDate.value;//备案日期
	var reportDate = new Date(prpLregistReportDate.replace(/-/g,"/"));//備案日期
	var reportHour  = fm.prpLregistReportHour.value;
	var reportMinute  = fm.prpLregistReportMinute.value;
	reportDate.setHours(parseInt(reportHour , 10),parseInt(reportMinute , 10),0);
	if(/^prpLregistReportDate/.test(f.name) || f.name=="prpLregistReportHour" || f.name=="prpLregistReportMinute"){
		if(reportDate > getServerCurrTime()){
			alert("備案時間不得大於系統當前時間！");
		} else if(reportDate < damageStartDate){
			alert("備案時間不得小於出險時間！");
		} else {//通過校驗則無需任何提示或恢復
			return false;
		}
		f.value = f.defaultValue;
		if(/^prpLregistReportDate/.test(f.name)){
			$(f).attr("realValue",fm.prpLregistReportDate.defaultValue);
			fm.prpLregistReportDate.value = fm.prpLregistReportDate.defaultValue;
		}
		return false;
	} else if(/^prpLregistDamageStartDate/.test(f.name) || f.name=="prpLregistDamageStartHour" || f.name=="prpLregistDamageStartMinute"){
		if(damageStartDate > reportDate){
			alert("出險時間不得大於備案時間！");
		} else if(damageStartDate > (getServerCurrTime())){
			alert("出險時間不得大於系統當前時間！");
		} else if(damageStartDate > endDate || damageStartDate < startDate){
			if(confirm("出險時間不在保單保險期間內！是否繼續？")){
				return true;
			}
		} else if(f.name=="prpLregistDamageStartMinute"){//只調整分鐘，則無需刷新
			return false;
		} else {
			return true;
		}
		f.value = f.defaultValue;
		if(/^prpLregistDamageStartDate/.test(f.name)){
			$(f).attr("realValue",fm.prpLregistDamageStartDate.defaultValue);
			fm.prpLregistDamageStartDate.value = fm.prpLregistDamageStartDate.defaultValue;
		}
	}
	return false;
}

/***
 * 獲取服務器當前時間
 */
function getServerCurrTime(){
	var currTime = new Date();
	$.ajax({
		type: "POST",
		url: contextRootPath + "/common/timeserver.jsp",
		cache: false,
		async: false ,
		dataType: "json",
		success: function(d){
			currTime = new Date(d.time);
		}
	})
	return currTime;
}
