/** 
 * 非自訂
 * 
 * delZero - 移除字串前面的0(EX:00123->123)
 * addZero - 增加字串前面的0(EX:123->00123)
 * 
 * mathFormat - 三位一撇(EX:123456->123,456,--)
 * showMathFormat - 三位一撇(EX:123456->123,456)
 * 
 * showROCDateFormat
 * convertRocDate - 中華民國年轉西元年(EX:1000101->20110101)
 */

/** 
 * 自訂
 * ====================頁籤====================
 * clickTab - 頁籤連動事件
 * 
 * ====================blockUI====================
 * block - 顯示blockUI
 * 
 * ====================隱藏或顯示====================
 * display:none和visibility:hidden的差別：
 * 1. display:none;     隱藏物件，但是那個物件不會佔著一個位置，隱藏時值仍在(JQuery的hide及show也是在透過操作display屬性來實現的)
 * 2. visibility:hidden;隱藏物件，但是那個物件還是佔著一個位置，隱藏時值仍在
 * 
 * showHide1 - 區塊的顯示或隱藏
 * showHide2 - 區塊的顯示或隱藏
 * 
 * columnSwitch1 - 欄位的顯示或隱藏(隱藏時，看不到欄位，但值依然存在)
 * columnSwitch2 - 欄位的顯示或隱藏並清空(隱藏時，看不到欄位，但值依然存在)
 * 
 * columnLock - 欄位的鎖住
 * columnOpen - 欄位的打開
 * 
 * ====================元素====================
 * setElement - 設定id的值
 * getElement - 取得id的值
 * copy - 複製fromdID的值至toID
 * 
 * ====================字串====================
 * replaceAll - 讓JavaScript字串具有replaceAll的功能，反斜線不適用
 * replaceALL - 讓JavaScript字串具有replaceAll的功能，反斜線適用
 * startsWith - 讓JavaScript字串具有startsWith的功能
 * endsWith - 讓JavaScript字串具有endsWith的功能
 *  
 * replaceSpaceToWord - 移除多餘空白
 * ltrim - 移除字串左邊的空白
 * rtrim - 移除字串右邊的空白
 * trim - 移除字串前後的空白
 * 
 * toUpperCase - 字母轉大寫
 * toUpperCase2 - 字母轉大寫/字母轉大寫(，並移除多餘空白)
 * toUpperCase2Arr - 字母轉大寫/字母轉大寫(，並移除多餘空白)
 * toUpperCase3 - 字母轉大寫/字母轉大寫(，並移除多餘空白/字串左邊的空白/字串右邊的空白/字串前後的空白)
 * toUpperCase3Arr - 字母轉大寫/字母轉大寫(，並移除多餘空白/字串左邊的空白/字串右邊的空白/字串前後的空白)
 * 
 * toAutoVal - 清除欄位值時自動給值
 * toAutoValArr - 清除欄位值時自動給值
 * 
 * isEmptyVal1 - 欄位值是否為空值或null
 * isEmptyVal2 - 欄位值是否為空值或null
 * 
 * ====================數字==================== 
 * JHshNumberText - 鎖鍵盤只能輸入數字
 * JHshNumberTextArr - 鎖鍵盤只能輸入數字
 * 
 * JHshNumDotText - 鎖鍵盤只能輸入數字以及小數點
 * JHshNumDotTextArr - 鎖鍵盤只能輸入數字以及小數點
 * 
 * integerCheck - 整數位數的檢核
 * decimalCheck - 小數位數的檢核
 * 
 * ====================運算====================
 * add - 加
 * sub - 減
 * mul - 乘
 * div - 除
 * mod - 餘數
 * getRoundResult - 四捨五入
 * getFloorResult - 無條件捨去
 * 
 * ====================textarea====================
 * bLen - 回傳字串的byte數
 * bLenCheck - 字串的byte數不能超過長度
 * 
 * textareaColsCheck - textarea的cols不能超過長度(每列byte數不能超過指定的byte數)
 * textareaRowsCheck - textarea的rows不能超過長度(不能超過指定的列數)
 * textareaBLenCheck - textarea的byte數不能超過長度
 * 
 * textareaBLenMsg - textarea的byte數訊息
 * textareaMsg - textarea的byte數訊息
 * 
 * titleChange - title顯示id的值
 * ====================日期====================
 * datePickerArr - 加上小日曆/加上小日曆及imageID
 * 
 * autoInputEndDate - 加上一年
 * 
 * isLeapYear - 是否閏年
 * 
 * getDkeyin - 取得輸入日期(民國年)
 * 
 * dateDiff - 取得日期相差天數
 * dateDiffOneYear - 日期相差天數最長一年整
 * 
 * dateCheck1 - 起日不能大於等於迄日
 * dateCheck2 - 起日不能大於迄日
 * 
 * hourCheck - 小時的檢核
 * minuteCheck - 分鐘的檢核
 * ====================訊息====================
 * getMessageFormat - 回傳訊息+換行
 * messageProcess - 處理檢核有誤的欄位
 */

//非自訂--------------------------------------------------------------------------------------------------------
$(document).ready(function(){
	
	$('input[type="text"]').bind('focus',function(data){
		newColor(this);
	});	
	$('input[type="text"]').bind('blur',function(data){
		oldColor(this);
	});	
	
	$('input[type="password"]').bind('focus',function(data){
		newColor(this);
	});	
	$('input[type="password"]').bind('blur',function(data){
		oldColor(this);
	});
	
	$('input[type="radio"]').bind('focus',function(data){
		newColor(this);
	});	
	$('input[type="radio"]').bind('blur',function(data){
		oldColor(this);
	});
	
	$('input[type="file"]').bind('focus',function(data){
		newColor(this);
	});	
	$('input[type="file"]').bind('blur',function(data){
		oldColor(this);
	});
	
	$('textarea').bind('focus',function(data){
		newColor(this);
	});	
	$('textarea').bind('blur',function(data){
		oldColor(this);
	});


	$("select").mousedown(function(){
		$(this).css("backgroundColor","#00ffff");
	});
	$("select").blur(function(){
		if( $(this).attr("checkValidateColor")=="true" ){
			$(this).css("backgroundColor","yellow");
		}else{
			$(this).css("backgroundColor","white");
		}
	});
	
	// 此為排外 start
	$("input[readOnly]").focus(function(){
		$(this).css({backgroundColor:'EFEFEF'}).blur(function(){
			$(this).css({backgroundColor:'#EFEFEF'});
		});
	});
	// 此為排外 end
	
	$('td.mathFormat').each(function(i){
		var textValue = this.innerText;
		var rr = showMathFormat(textValue);
		this.innerText=rr;
	});
	
	$('td.ROCDateFormat').each(function(i){
		var textValue = this.innerText;
		var rr = showROCDateFormat(textValue, "/");
		this.innerText=rr;
	});
	
});

var lastColor;
function newColor(obj){
	lastColor=$(obj).css('backgroundColor');
	$(obj).css('backgroundColor','#00FFFF');
}
function oldColor(obj){
	if(lastColor == '#00FFFF' || lastColor == '#00ffff'){
		lastColor = 'white';
	}
	$(obj).css('backgroundColor',lastColor);
}

//移除字串前面的0(EX:00123->123)
function delZero(num){
	if(num.length==1 && num=='0'){
		return num;
	}else{
		for(var i=0; i<num.length; i++){
			if(num.indexOf("0")==0)	num = num.substr(1, num.length);
		}
	}
	return num;
}

//增加字串前面的0(EX:123->00123)
function addZero(str, length){
	str = str.toString();
	var strLength = str.length;
	var returnValue = str;
	for(var i=strLength; i<length; i++){
		returnValue = "0" + returnValue;
	}
	return returnValue;
}

//三位一撇(EX:123456->123,456,--)
function mathFormat(num, returnValue){
	var showValue = "";
	num = delZero(num);
	if(num/1000>=1){
		showValue = num.substr(num.length-3, num.length) + "," + returnValue;
		showValue = mathFormat(num.substr(0, num.length-3), showValue);
	} else { 
		showValue = num.concat(",").concat(returnValue);  
	}
	return showValue;
}

//三位一撇(EX:123456->123,456)
function showMathFormat(num){
	var afterDot = "";
	var returnValue = "";
	if(!isNaN(num)){
		if(num.indexOf(".")>-1){
			afterDot = num.substring(num.indexOf("."),num.length);
			num = num.substring(0, num.indexOf("."));
		}
	}
	returnValue = mathFormat(num,'');
	return returnValue.substring(0, returnValue.length-1)+afterDot;
}

function showROCDateFormat(ADDate, splitStr){
	if(ADDate!=""){
		var yyyy = ADDate.split(splitStr)[0] - 1911;
		return addZero(yyyy, 3) + "/" + addZero(ADDate.split(splitStr)[1], 2) + "/" + addZero(ADDate.split(splitStr)[2], 2);
	} else {
		return "";
	}
}

function convertRocDate(rocDate){
	var rocYear = (rocDate.substring(0,3) - 0)+1911; 
	alert(rocYear + rocDate.substring(3,rocDate.length));
	return rocYear + rocDate.substring(3,rocDate.length);
}

//自訂--------------------------------------------------------------------------------------------------------
/**
 * 頁籤連動事件
 * 
 * 請注意：此連動事件必須綁特定id寫法，否則無法work
 * <td  class="MainTdColor" style="width: 200px" align="center" id="tab_1_td">
 * <b><a href="#" class="SelectTagFont" id="tab_1" accesskey="1" tabindex="-1">1.基本資料</a></b>
 * </td>
 * <td  class="image" width="18px" id="tab_1_img"></td>
 * 
 * @param id 來源id
 */
function clickTab(id){
    $('a[id^=tab]').removeClass('SelectTagFont').addClass('MainTagFont');
    $('#'+id).removeClass('MainTagFont').addClass('SelectTagFont');
    
    $('td[id^=tab][id$=_td]').removeClass('MainTdColor').addClass('SelectTdColor');
    var id2 = id +"_td";
    $('#'+id2).removeClass('SelectTdColor').addClass('MainTdColor');
    
    $('td[id^=tab][id$=_img]').removeClass('image').addClass('imageGray');
    var id3 = id +"_img";
    $('#'+id3).removeClass('imageGray').addClass('image');
    
    var displayDivId = id + "_div";
    
    $('div[id^=tab][id$=_div]').hide();
    $('#' + displayDivId).show();
}

/**
 * 顯示blockUI
 */
function block(){
	$.blockUI({ css: {
       border: 'none',
       padding: '15px',
       backgroundColor: '#000',
       color: '#fff',
       '-webkit-border-radius': '10px',
       '-moz-border-radius': '10px',
       opacity: .5
	} });
}

/**
 * 區塊的顯示或隱藏
 * 
 * @param id
 */
function showHide1(id){
	var hidden = $('#'+id).is(":hidden");//是否隱藏
	//var visible = $('#'+id).is(":visible");//是否顯示
	if(hidden){
		$('#'+id).show();
	}else{
		$('#'+id).hide();
	}
	return false;
}

/**
 * 區塊的顯示或隱藏
 *  
 * @param id
 * @param bl bl=true指顯示,bl=false指隱藏
 */
function showHide2(id, bl){
	if(bl){//顯示
		$('#'+id).show();
	}else{//隱藏
		$('#'+id).hide();
	}
	return false;
}

/**
 * 欄位的顯示或隱藏(隱藏時，看不到欄位，但值依然存在)
 * 
 * @param id id可以是divId、tdId、columnId
 * @param bl bl=true指顯示,bl=false指隱藏
 */
function columnSwitch1(id, bl){
	if(bl){//顯示
		$('#'+id).css("visibility", "visible");
	}else{//隱藏
		$('#'+id).css("visibility", "hidden");
	}
}
 
/**
 * 欄位的顯示或隱藏並清空(隱藏時，看不到欄位，但值依然存在)
 * 
 * @param id id可以是divId、tdId、columnId
 * @param bl bl=true指顯示,bl=false指隱藏並清空
 * @param idEmptyVal 欄位清空時填入值,填入''或0
 */
function columnSwitch2(id, bl, idEmptyVal){
 	if(bl){//顯示
 		$('#'+id).css("visibility", "visible");
 	}else{//隱藏並清空
 		$('#'+id).css("visibility", "hidden");
 		$('#'+id).val(idEmptyVal);
 	}
}

/**
 * 欄位的鎖住
 *  
 * @param id    id只能是columnId,不能是divId、tdId
 * @param type  指定欄位鎖住時的欄位color
 */
function columnLock(id, type){
 	var color = 'efefef';
 	if(type == '1'){
 		color = 'efefef';//灰色(用於一般欄位)
 	}else{
 		color = '99CCFF';//藍色(用於浮動視窗欄位)
 	}
 	$('#'+id).attr("readonly", true);
 	$('#'+id).css({'backgroundColor':'#'+color});
 	$('#'+id).css("border","0px #"+color);
 	$('#'+id).attr("tabindex","-1");
 	$('#'+id).unbind("focus").unbind("blur");
}

/**
 * 欄位的打開
 *  
 * @param id    id只能是columnId,不能是divId、tdId
 * @param type  指定欄位打開時的邊框color
 */
function columnOpen(id, type){
  	var color = 'efefef';
  	if(type == '1'){
  		color = 'efefef';//灰色(用於一般欄位)
  	}else{
  		color = '99CCFF';//藍色(用於浮動視窗欄位)
  	}
  	$('#'+id).attr("readonly", false);
  	$('#'+id).css({'backgroundColor':'#ffffff'});//白色
  	$('#'+id).css("border","1px groove #"+color);
  	$('#'+id).attr("tabindex","");
  	$('#'+id).unbind("focus").unbind("blur");
}

/**
 * 設定id的值(如果isCover為true，等同$('#'+id).val(value);)
 * 
 * @param id
 * @param value
 * @param isCover true是指無論id有沒有值都覆蓋，false是指id有值就不會覆蓋
 */
function setElement(id, value, isCover){
 	if(isCover){
 		$('#'+id).val(value);
 	}else{
 		if($('#'+id).val() == ''){
 			$('#'+id).val(value);
 		}
 	}
}

/**
 * 取得id的值
 * 
 * @param  id
 * @return value
 */
function getElement(id){
 	var value = '';
 	if(id != ''){
 		value = $('#'+id).val();
 	}
 	return value;
}

/**
 * 複製fromdID的值至toID(注意：此method會觸發toID的onchange事件)
 * 
 * @param fromID  來源id
 * @param toID    目的id
 * @param isCover true是指無論id有沒有值都覆蓋，false是指id有值就不會覆蓋
 */
function copy(fromID, toID, isCover){
 	var toValue = $('#'+toID).val();
 	var fromValue = $('#'+fromID).val();
 	if(isCover){
 		$('#'+toID).val(fromValue);
 	}else{
 		if(toValue == ''){
 			$('#'+toID).val(fromValue);
 		}
 	}
 	$('#'+toID).trigger('change');
}

//讓JavaScript字串具有replaceAll的功能，反斜線不適用
String.prototype.replaceAll = function(AFindText,ARepText){
    raRegExp = new RegExp(AFindText,"g");
    return this.replace(raRegExp,ARepText);
};

//讓JavaScript字串具有replaceAll的功能，反斜線適用
function replaceALL(strOrg,strFind,strReplace){
 	var index = 0;
 	while(strOrg.indexOf(strFind,index) != -1){
 		strOrg = strOrg.replace(strFind,strReplace);
 		index = strOrg.indexOf(strFind,index);
 	}
 	return strOrg;
}

//讓JavaScript字串具有startsWith的功能
String.prototype.startsWith = function(str) {
 	return (this.match("^"+str)==str);
};

//讓JavaScript字串具有endsWith的功能
String.prototype.endsWith = function(str) {
 	return (this.match(str+"$")==str);
};


//移除多餘空白
function replaceSpaceToWord(obj, word){
	obj.val(obj.val().replaceAll(' ',word));
}
  
//移除字串左邊的空白
function ltrim(id){
	$('#' + id).val($('#' + id).val().replace(/^[\s]*/gi,""));
}

//移除字串右邊的空白
function rtrim(id){
	$('#' + id).val($('#' + id).val().replace(/[\s]*$/gi,""));
}

//移除字串前後的空白
function trim(id){
	ltrim(id);
	rtrim(id);
}

//字母轉大寫
function toUpperCase(obj){
	obj.val(obj.val().toUpperCase());
}

//字母轉大寫/字母轉大寫(，並移除多餘空白)
function toUpperCase2(id, removeEmpty){
	$('#'+id).val($('#'+id).val().toUpperCase());
	if(removeEmpty){
		replaceSpaceToWord($('#'+id), '');
	}
}

//字母轉大寫/字母轉大寫(，並移除多餘空白)
function toUpperCase2Arr(strArr, removeEmpty){
	$.each( strArr, function(variable, value){
		$('#'+strArr[variable]).bind("change", function(){
			$('#'+strArr[variable]).val($('#'+strArr[variable]).val().toUpperCase());
			if(removeEmpty){
				replaceSpaceToWord($('#'+strArr[variable]), '');
			}
		});
	});
}

//字母轉大寫/字母轉大寫(，並移除多餘空白/字串左邊的空白/字串右邊的空白/字串前後的空白)
function toUpperCase3(id, type){
	$('#'+id).val($('#'+id).val().toUpperCase());
	if(type == '1'){
		replaceSpaceToWord($('#'+id), '');
	}else if(type == '2'){
		ltrim(id);
	}else if(type == '3'){
		rtrim(id);
	}else if(type == '4'){
		ltrim(id);
		rtrim(id);
	}
}

//字母轉大寫/字母轉大寫(，並移除多餘空白/字串左邊的空白/字串右邊的空白/字串前後的空白)
function toUpperCase3Arr(strArr, type){
	$.each( strArr, function(variable, value){
		$('#'+strArr[variable]).bind("change", function(){
			$('#'+strArr[variable]).val($('#'+strArr[variable]).val().toUpperCase());
			if(type == '1'){
				replaceSpaceToWord($('#'+strArr[variable]), '');
			}else if(type == '2'){
				ltrim(strArr[variable]);
			}else if(type == '3'){
				rtrim(strArr[variable]);
			}else if(type == '4'){
				ltrim(strArr[variable]);
				rtrim(strArr[variable]);
			}
		});
	});
}

//清除欄位值時自動給值
function toAutoVal(id, val){
	if($('#'+id).val() == ''){
		$('#'+id).val(val);
	}
}

//清除欄位值時自動給值
function toAutoValArr(strArr, val){
	$.each( strArr, function(variable, value){
		$('#'+strArr[variable]).bind("change", function(){
			if($('#'+strArr[variable]).val() == ''){
				$('#'+strArr[variable]).val(val);
			}
		});
	});
}

//欄位值是否為空值或null
function isEmptyVal1(id){
	var val = $('#'+id).val();
	if(val == '' || val == null){
		return true;
	}else{
		return false;
	}
}

//欄位值是否為空值或null
function isEmptyVal2(val){
	if(val == '' || val == null){
		return true;
	}else{
		return false;
	}
}

//鎖鍵盤只能輸入數字
function JHshNumberText(){
	if ( !(((window.event.keyCode >= 48) && (window.event.keyCode <= 57)) || (window.event.keyCode == 13))){
		window.event.keyCode = 0 ;
	}
}

//鎖鍵盤只能輸入數字
function JHshNumberTextArr(numArr){
	$.each( numArr, function(variable, value){
		$('#'+numArr[variable]).bind("keypress", function(){
			if ( !(((window.event.keyCode >= 48) && (window.event.keyCode <= 57)) || (window.event.keyCode == 13))){
				window.event.keyCode = 0 ;
			}
		});
	});
}

//鎖鍵盤只能輸入數字以及小數點
function JHshNumDotText(){
	if ( !(((window.event.keyCode >= 48) && (window.event.keyCode <= 57)) || (window.event.keyCode == 46) || (window.event.keyCode == 13))){
		window.event.keyCode = 0 ;
	}
}

//鎖鍵盤只能輸入數字以及小數點
function JHshNumDotTextArr(numArr){
	$.each( numArr, function(variable, value){
		$('#'+numArr[variable]).bind("keypress", function(){
			if ( !(((window.event.keyCode >= 48) && (window.event.keyCode <= 57)) || (window.event.keyCode == 46) || (window.event.keyCode == 13))){
				window.event.keyCode = 0 ;
			}
		});
	});
}

/**
 * 整數位數的檢核
 * 
 * @param type       type=1指可輸入[正]整數,type=2指可輸入[正負]整數
 * @param id         欄位id
 * @param idLen1     欄位整數位數
 * @param idName     欄位名稱
 * @param idEmptyVal 欄位清空時填入值,填入''或0
 */
function integerCheck(type, id, idLen1, idName, idEmptyVal){
	var msg = '';
 	var value = $('#'+id).val();
 	if(value != "" && value != 0){
		var reg1 = eval("/^([\-]?)([0-9]{1,"+idLen1+"})$/");
		if(type == 1){
			reg1 = eval("/^([0-9]{1,"+idLen1+"})$/");
			//msg = '正';
		}
		if(!reg1.test(value)){
			alert('「'+idName+'」可輸入'+idLen1+'位'+msg+'整數');
			$('#'+id).blur();
			$('#'+id).focus();
			$('#'+id).val(idEmptyVal);
		}
	}
	return;
}

/**
 * 小數位數的檢核
 * 
 * @param type       type=1指可輸入[正]整數,type=2指可輸入[正負]整數
 * @param id         欄位id
 * @param idLen1     欄位整數位數
 * @param idLen2     欄位小數位數
 * @param idName     欄位名稱
 * @param idEmptyVal 欄位清空時填入值,填入''或0
 */
function decimalCheck(type, id, idLen1, idLen2, idName, idEmptyVal){
	var msg = '';
	var value = $('#'+id).val();
	if(value != "" && value != 0){
		if(value.indexOf(".")==-1){
			var reg1 = eval("/^([\-]?)([0-9]{1,"+idLen1+"})$/");
			if(type == 1){
				reg1 = eval("/^([0-9]{1,"+idLen1+"})$/");
				//msg = '正';
			}
			if(!reg1.test(value)){
				alert('「'+idName+'」可輸入'+idLen1+'位'+msg+'整數'+idLen2+'位小數');
				$('#'+id).blur();
				$('#'+id).focus();
				$('#'+id).val(idEmptyVal);
		    }
		}else{
			var reg2 = eval("/^([\-]?)([0-9]{1,"+idLen1+"})(\.[0-9]{1,"+idLen2+ "})?$/");
			if(type == 1){
				reg2 = eval("/^([0-9]{1,"+idLen1+"})(\.[0-9]{1,"+idLen2+ "})?$/");
				//msg = '正';
			}
			if(!reg2.test(value)){
				alert('「'+idName+'」可輸入'+idLen1+'位'+msg+'整數'+idLen2+'位小數');
				$('#'+id).blur();
				$('#'+id).focus();
				$('#'+id).val(idEmptyVal);
		    }
		}
	}
	return;
}

//加法函數，用來得到精確的加法結果
//說明：javascript的加法結果會有誤差，在兩個浮點數相加的時候會比較明顯。這個函數返回較為精確的加法結果。
//調用：add(arg1,arg2)
//返回值：arg1加上arg2的精確結果
function add(arg1,arg2){
	var r1,r2,m;
	try{r1=arg1.toString().split(".")[1].length;}catch(e){r1=0;};
	try{r2=arg2.toString().split(".")[1].length;}catch(e){r2=0;};
	m=Math.pow(10,Math.max(r1,r2));
	return (arg1*m+arg2*m)/m;
}

//減法函數，用來得到精確的加法結果
//說明：javascript的減法結果會有誤差，在兩個浮點數相減的時候會比較明顯。這個函數返回較為精確的減法結果。
//調用：sub(arg1,arg2)
//返回值：arg1減去arg2的精確結果
function sub(arg1,arg2){
	return add(arg1,-arg2);
}

//乘法函數，用來得到精確的乘法結果
//說明：javascript的乘法結果會有誤差，在兩個浮點數相乘的時候會比較明顯。這個函數返回較為精確的乘法結果。
//調用：mul(arg1,arg2)
//返回值：arg1乘以arg2的精確結果
function mul(arg1,arg2){
	var m=0,s1=arg1.toString(),s2=arg2.toString();
	try{m+=s1.split(".")[1].length;}catch(e){}
	try{m+=s2.split(".")[1].length;}catch(e){}
	return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m);
}

//除法函數，用來得到精確的除法結果
//說明：javascript的除法結果會有誤差，在兩個浮點數相除的時候會比較明顯。這個函數返回較為精確的除法結果。
//調用：div(arg1,arg2)
//返回值：arg1除以arg2的精確結果
function div(arg1,arg2){
	var t1=0,t2=0,r1,r2;
	try{t1=arg1.toString().split(".")[1].length;}catch(e){}
	try{t2=arg2.toString().split(".")[1].length;}catch(e){}
	with(Math){
		r1=Number(arg1.toString().replace(".",""));
		r2=Number(arg2.toString().replace(".",""));
		return (r1/r2)*pow(10,t2-t1);
	}
}

//餘數函數，用來得到精確的餘數結果
//說明：javascript的餘數結果會有誤差，在兩個浮點數取餘數的時候會比較明顯。這個函數返回較為精確的餘數結果。
//調用：mod1000(arg1,arg2,小數位數)
//返回值：arg1%arg2的精確結果
function mod(arg1,arg2){
	var t1=0,t2=0;
	try{t1=arg1.toString().split(".")[1].length;}catch(e){}
	try{t2=arg2.toString().split(".")[1].length;}catch(e){}
	with(Math){
		var t = pow(10,abs(t2-t1));
		var t1 = round(arg1*t);
		var t2 = round(arg2*t);
		return (t1%t2)/t;
	}
}

//四捨五入函數，用來得到精確的四捨五入結果
//調用：getRoundResult(num,n)
//返回值：num四捨五入至小數點n位的精確結果
function getRoundResult(num,n){
	return Math.round(num*Math.pow(10,n))/Math.pow(10,n);
}

//無條件捨去函數，用來得到精確的無條件捨去結果
//調用：getFloorResult(num,n)
//返回值：num無條件捨去至小數點n位的精確結果
function getFloorResult(num,n){
	return Math.floor(num*Math.pow(10,n))/Math.pow(10,n);
}

//回傳字串的byte數
String.prototype.bLen = function() {
    var arr = this.match(/[^\x00-\xff]/ig);
    return arr == null ? this.length : this.length + arr.length;
}
/*
String.prototype.bLen = function() {
	var len = this.length;
	var reLen = 0;
	for (var i = 0; i < len; i++) {
		if (this.charCodeAt(i) < 27 || this.charCodeAt(i) > 126) {
			reLen += 2;
		} else {
			reLen++;
		}
	}
	return reLen;
}
*/

//字串的byte數不能超過長度
function bLenCheck(columnId, columnLen){
	var result = true;
	var column = $('#'+columnId).val();
	if(column.bLen() > columnLen){
		result = false;
	}
	return result;
}

//textarea的cols不能超過長度(每列byte數不能超過指定的byte數)
function textareaColsCheck(columnId, cols){
	var result = true;
	var column = $('#'+columnId).val();
	var row = column.split("\n");
	for(var i=0; i<row.length; i++){
		if(row[i].bLen() > cols){
			result = false;
		}
	}
	return result;
}

//textarea的rows不能超過長度(不能超過指定的列數)
function textareaRowsCheck(columnId, rows){
	var result = true;
	var column = $('#'+columnId).val();
	var row = column.split("\n");
	if(row.length > rows){
		result = false;
	}
	return result;
}

//textarea的byte數不能超過長度
function textareaBLenCheck(columnId, columnLen, cols){
	var result = true;
	var bLen = 0;
	var column = $('#'+columnId).val();
	var row = column.split("\n");
	var len = row.length - 1;
	for(var i=0; i<row.length; i++){
		if(i == len){
			bLen += row[i].bLen();
		}else{
			if(row[i].bLen() > cols){
				bLen += row[i].bLen();
			}else{
				bLen += cols;
			}
		}
	}
	if(bLen > columnLen){
		result = false;
	}
	return result;
}

//textarea的byte數訊息
function textareaBLenMsg(columnId, cols, rows){
	var msg0 = "";//回傳訊息0
	var msg1 = "";//回傳訊息1
	var msg2 = "";//回傳訊息2
	var msg = new Array(3);//回傳訊息
	var bLen = 0;//textarea的byte數
	var column = $('#'+columnId).val();//textarea的欄位值
	var row = column.split("\n");
	var len = row.length - 1;//textarea的列數-1
	for(var i=0; i<row.length; i++){
		if(i == len){//最後一列
			//加總最後一列的byte數
			bLen += row[i].bLen();
			//alert請換行(缺點：會一直顯示alert)
			//if(row[i].bLen() > cols){
			//	alert("請換行");
			//}
			//自動換行(缺點：於交界處剩1byte打中文字會清空textarea中所有文字))
			//if(row[i].bLen() >= cols){
			//	$('#'+columnId).val($('#'+columnId).val()+"\n");
			//}
			//訊息2
			if(row[i].bLen() > cols){
				var overNo = row[i].bLen() - cols;
				if(msg2 != ""){
					msg2 += "，";
				}
				msg2 += "第"+(i+1)+"列超過"+overNo+"個字元";
			}
		}else{//非最後一列
			if(row[i].bLen() > cols){
				//加總非最後一列的byte數(超過該列字元數者，以該列實際字元數加總)
				bLen += row[i].bLen();
				//訊息2
				var overNo = row[i].bLen() - cols;
				if(msg2 != ""){
					msg2 += "，";
				}
				msg2 += "第"+(i+1)+"列超過"+overNo+"個字元";
			}else{
				//加總非最後一列的byte數(不滿該列字元數者，以該列字元數加總)
				bLen += cols;
			}
		}
	}
	msg0 = "目前輸入第"+bLen+"個字元，最多可輸入"+mul(cols,rows)+"個字元";
	msg1 = "(最多可輸入"+rows+"列，每列"+cols+"個字元)";
	if(msg2 != ""){
		msg2 += "，請於該列按Enter";
	}
	if(!textareaRowsCheck(columnId, rows)){
		msg2 += "(輸入超過"+rows+"列)";
	}
	msg[0]=msg0;//msg0的maxlength建議設為45
	msg[1]=msg1;//msg1的maxlength建議設為35
	msg[2]=msg2;//msg2的maxlength建議設為[(列數*20)+30]
	return msg;
}

//textarea的byte數訊息
function textareaMsg(msgIdArr, columnId, cols, rows, msgType){
	var msgArr = textareaBLenMsg(columnId, cols, rows, msgType);
	if(msgType == 1){//2行，使用時機：基本檔
		$('#'+msgIdArr[0]).val(msgArr[0]+msgArr[1]+"(注意：中文字算2個字元)");
		$('#'+msgIdArr[0]).css({'color':'#0000FF'});//藍色
		$('#'+msgIdArr[1]).val(msgArr[2]);
		$('#'+msgIdArr[1]).css({'color':'#FF0000'});//紅色
	}
	if(msgType == 2){//3行，使用時機：承保/批改作業(品名、嘜頭、增補內容)
		$('#'+msgIdArr[0]).val(msgArr[0]);
		$('#'+msgIdArr[0]).css({'color':'#0000FF'});//藍色
		$('#'+msgIdArr[1]).val(msgArr[1]);
		$('#'+msgIdArr[1]).css({'color':'#0000FF'});//藍色
		$('#'+msgIdArr[2]).val(msgArr[2]);
		$('#'+msgIdArr[2]).css({'color':'#FF0000'});//紅色
	}
	if(msgType == 3){//1行，使用時機：承保/批改作業(條款)
		$('#'+msgIdArr[0]).val(msgArr[2]);
		$('#'+msgIdArr[0]).css({'color':'#FF0000'});//紅色
	}
	if(msgType == 4){//2行，使用時機：承保/批改作業(保單備註)、起運通知書/批改作業(批文)、客製化收據列印作業(台幣保費(文字))
		$('#'+msgIdArr[0]).val(msgArr[0]+msgArr[1]);
		$('#'+msgIdArr[0]).css({'color':'#0000FF'});//藍色
		$('#'+msgIdArr[1]).val(msgArr[2]);
		$('#'+msgIdArr[1]).css({'color':'#FF0000'});//紅色
	}
}

//title顯示id的值
function titleChange(id){
	$('#'+id).attr("title", $('#'+id).val()); 
}

//加上小日曆及imageID
function datePickerArr(dateArr, addImageID){
	if(addImageID){
		$.each( dateArr, function(variable, value){
			var id = dateArr[variable];
			$('#'+dateArr[variable]).datePicker({
				"startDate" : "01/01/1911",
				"dateType" : "roc",
				"dateFormat" : "yyy/mm/dd",
				"imageID" : id+"Img"
			});
		});
	}else{
		$.each( dateArr, function(variable, value){
			$('#'+dateArr[variable]).datePicker({
				"startDate" : "01/01/1911",
				"dateType" : "roc",
				"dateFormat" : "yyy/mm/dd"
			});
		});
	}
}

//加上一年
function autoInputEndDate(begID, endID, addImageID){
	var begDate = $('#'+begID).val();
	var endDate = $('#'+endID).val();
	var eY = ''+((begDate.substring(0,3)-0)+1);
	if(begDate != ''){
		if(eY.length==2){
			eY = '0'+eY;
		}
		var endDate = eY+begDate.substring(3,begDate.length);
		var eM = (begDate.substring(4,6)-0);
		var ed = (begDate.substring(7,9)-0);
		if(eM == 2 && ed == 29){
			endDate = eY +'/0'+eM+'/28';
		}
		$('#'+endID).val(endDate);
		if(addImageID){
			$('#'+endID).datePicker({
				"year":eY+1911,
				"startDate" : "01/01/1911",
				"dateType" : "roc",
				"dateFormat" : "yyy/mm/dd",
				"imageID" : endID+"Img"
			});
		}else{
			$('#'+endID).datePicker({
				"year":eY+1911,
				"startDate" : "01/01/1911",
				"dateType" : "roc",
				"dateFormat" : "yyy/mm/dd"
			});
		}
		$('#'+endID).trigger('change');
	}
}

//是否閏年
function isLeapYear(year){
	if((year %4==0 && year %100!=0) || (year %400==0)){
		return true;
	}else{
		return false;
	}
}

//取得輸入日期(民國年)
function getDkeyin(){
	var today = new Date();
	var yyy = today.getFullYear()-1911;
	var mm = today.getMonth()+1;
	var dd = today.getDate();
	if(mm<10){mm='0'+mm;}
	if(dd<10){dd='0'+dd;}
	return +yyy+'/'+mm+'/'+dd;
}

/**
 * 取得日期相差天數
 * 
 * @param  begDate 日期起值(民國年)
 * @param  endDate 日期迄值(民國年)
 * @return days    相差天數
 */
function dateDiff(begDate, endDate){
	var bY = parseInt(begDate.substring(0,3),10)+1911;
	var eY = parseInt(endDate.substring(0,3),10)+1911;
	var bMD = begDate.substring(3,9);
	var eMD = endDate.substring(3,9);
	var bDate = bY + bMD;
	var eDate = eY + eMD;
	bDate = new Date(bDate);
	eDate = new Date(eDate);
	var time = eDate.getTime() - bDate.getTime(); 
	var days = parseInt(time / (1000 * 60 * 60 * 24));
	return days;
}

/**
 * 日期相差天數最長一年整
 * 迄日-起日>一年，回傳false
 * 
 * @param begID 起日Id(民國年)
 * @param endID 迄日Id(民國年)
 */
function dateDiffOneYear(begID, endID){
 	 var begDate = $('#'+begID).val();
 	 var endDate = $('#'+endID).val();
 	 if("" != begDate && "" != endDate){
 	 	//1.使用起日算出一年後的迄日
 	 	var eY = ''+((begDate.substring(0,3)-0)+1);
 	 	if(eY.length==2){
 	 		eY = '0'+eY;
 	 	}
 	 	var eDate = eY+begDate.substring(3,begDate.length);
 	 	var eM = (begDate.substring(4,6)-0);
 	 	var eD = (begDate.substring(7,9)-0);
 	 	if(eM == 2 && eD == 29){
 	 		eDate = eY +'/0'+eM+'/28';
 	 	}
 	 	//2.使用迄日-用起日算出一年後的迄日
 	 	if(dateDiff(eDate, endDate) > 0){
 	 		return false;
 	 	}
 	 }
 	 return true;
}

//起日不能大於等於迄日
function dateCheck1(begID, endID){
	var result = true;
	var begDate = $('#'+begID).val();
	var endDate = $('#'+endID).val();
	if("" != begDate && "" != endDate){
		var bY = parseInt(begDate.substring(0,3),10);
		var eY = parseInt(endDate.substring(0,3),10);
		if(bY > eY){
			result = false;
		}else if(bY == eY){
			var bM = parseInt(begDate.substring(4,6),10);
			var eM = parseInt(endDate.substring(4,6),10);
			if(bM > eM){
				result = false;
			}else if(bM == eM){
				var bD = parseInt(begDate.substring(7,9),10);
				var eD = parseInt(endDate.substring(7,9),10);
				if(bD >= eD){
					result = false;
				}
			}
		}
	}
	return result;
}

//起日不能大於迄日
function dateCheck2(begID, endID){
	var result = true;
	var begDate = $('#'+begID).val();
	var endDate = $('#'+endID).val();
	if("" != begDate && "" != endDate){
		var bY = parseInt(begDate.substring(0,3),10);
		var eY = parseInt(endDate.substring(0,3),10);
		if(bY > eY){
			result = false;
		}else if(bY == eY){
			var bM = parseInt(begDate.substring(4,6),10);
			var eM = parseInt(endDate.substring(4,6),10);
			if(bM > eM){
				result = false;
			}else if(bM == eM){
				var bD = parseInt(begDate.substring(7,9),10);
				var eD = parseInt(endDate.substring(7,9),10);
				if(bD > eD){
					result = false;
				}
			}
		}
	}
	return result;
}


/**
 * 小時的檢核
 * 
 * @param id
 */
function hourCheck(id){
	var hour = $('#'+id).val();
	if("" != hour){
		var hh = parseInt(hour,10);	
		if(hh < 0 || hh > 23){
			return false;
		}
	}
	return true;
}

/**
 * 分鐘的檢核
 * 
 * @param id
 */
function minuteCheck(id){
	var minute = $('#'+id).val();
	if("" != minute){
		var mm = parseInt(minute,10);	
		if(mm < 0 || mm > 59){
			return false;
		}
	}
	return true;
}

/**
 * 回傳訊息+換行
 * 
 * @param msg
 */
function getMessageFormat(msg){
 	if(msg != ''){
 		return msg+"\n";
 	}
 	return "";
}

/**
 * 處理檢核有誤的欄位
 * 
 * @param message
 * @param errid
 */
function messageProcess(message, errid){
 	//1.上次檢核有誤的欄位
 	//(1)上次變成黃色的欄位必須變回白色
 	$('input[type="text"]').css('background-color','#FFFFFF');
 	$("textarea").css('background-color','#FFFFFF');
 	$('select').css('background-color','#FFFFFF');
 	//(2)readonly欄位不須變成白色
 	$("input[readOnly]").css('background-color','#efefef');
 	//2.此次檢核有誤的欄位
 	//(1)alert錯誤訊息
 	alert(message);
 	//(2)檢核有誤的欄位必須變成黃色
 	var erridArray = errid.split(",");
 	for (var i=0; i < erridArray.length; i=i+1){
 		var idname = erridArray[i];
 		if(idname != ''){
 			if(i==0){//檢核有誤的第一個欄位必須focus
 				$('#'+idname).focus();
 			}
 			$('#'+idname).css('background-color','yellow');
 		}
 	}
}