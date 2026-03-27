var CALENDAR_MILLISEC_PER_DAY = 86400000;
var EXTRA_HOUR_DAYLIGHT_SAVINGS_MS = 3600001;
var CALENDAR_FRAME_URL = '/claim/common/pub/simple_calendar_frame.htm';
var YEAR_RANGE_START = 1800;
var YEAR_RANGE_END = 2101;
 
function CalendarInit(is_modal) {
	if (is_modal != null && is_modal == true) {
		calendar_isModal = true;
	}
	else {
		var hid ="'hidden'";
		document.write('<iframe '
			+ '	src="' + CALENDAR_FRAME_URL + '"' + 
			+ '	name="calendar" '
			+ '	id="calendar" '
			+ ' ok_to_set_focus="false" ' 
			+ '	style="LEFT: 0px; TOP: 0px; WIDTH: 1px; HEIGHT: 1px; POSITION: absolute; VISIBILITY: hidden; z-index: 20;" '
			+ ' onblur="hideCalendar();" '
			+ '	scrolling="no"> '
			+ '</iframe>');
	}
}

var gXOffsetOfObjectToPositonNear;  
var gHeightOfObjectToPositonNear; 
var gYOffsetOfObjectToPositonNear; 
var gWidthOfObjectToPositonNear;
var calendarcancelcode; 

function TogglePopupCalendarWindow_internal_commonInit(objectToPositionOver, dateString, setcode, cancelcode) {
 		calSaveState(null); 
		calendarresultcode = setcode;
		calendarcancelcode = cancelcode; 
	
		var dateToUse;
		if (dateString == "" || dateString == null)  {
			dateToUse = new Date();
		} else {
			try {
				var expandDate = new Object();
				expandDate.value = dateString;
				calender_expand_date(expandDate, true,"1");
				dateToUse = new Date(expandDate.value);
				if (isNaN(dateToUse)) {
					dateToUse = new Date();
				}
			} catch (e) {
				dateToUse = new Date();
			}
		}
		
		
		calendarinitday = dateToUse.getDate(); 
		calendarinitmonth = dateToUse.getMonth(); 
		calendarinityear = dateToUse.getFullYear(); 
		//判断设置的日期时间
		//alert(calendarinityear+" " +calendarinitmonth+" "+calendarinitday)
}

function TogglePopupCalendarWindow_internal(objectToPositionOver, dateString, setcode, cancelcode) {
 	if (document.all.calendar.style.visibility == 'hidden') {
		TogglePopupCalendarWindow_internal_commonInit(objectToPositionOver, dateString, setcode, cancelcode);
	
		document.all.calendar.ok_to_set_focus = true; 
		document.all.calendar.src = CALENDAR_FRAME_URL;
			
		document.all.calendar.style.pixelHeight = 0; //computed later. Set to 0 to prevent scroll bar from temporarily causing the page size to change.
		calendar_getAbsolutePosition(objectToPositionOver, document.all.calendar.style);
    
		if (document.all.calendar.style.pixelTop < 0) {
			document.all.calendar.style.pixelTop = 0;
		} 
		gXOffsetOfObjectToPositonNear = document.all.calendar.style.pixelTop;
		gHeightOfObjectToPositonNear = objectToPositionOver.offsetHeight;
		gYOffsetOfObjectToPositonNear = document.all.calendar.style.pixelLeft;
		gWidthOfObjectToPositonNear = objectToPositionOver.offsetWidth;
		document.all.calendar.style.pixelTop += objectToPositionOver.offsetHeight;
			
		showCalendar =  showCalendarForReal;
		startingup = true;
	} else {
		hideCalendar();
	}
}

function TogglePopupCalendarWindow(fieldName, yearRangeStart, yearRangeEnd) {
	YEAR_RANGE_START = yearRangeStart;
	YEAR_RANGE_END = yearRangeEnd;
	
	var objectToPositionOver = eval(fieldName);
	var dateString = objectToPositionOver.value;
	//?????
	//alert(dateString);
	
	var setcode = fieldName +".value = calendarFormatDateField(day, month, year);" + fieldName + ".focus();calender_expand_date(" + fieldName + ");calendarCallOnChange(" + fieldName + ");" ;
	var cancelcode = fieldName + ".focus();";

	TogglePopupCalendarWindow_internal(objectToPositionOver, dateString, setcode, cancelcode);
}

var calendar_isModal = false;

var startingup = false; 
function HidePopupCalendarWindow() {
	try {
		if (!startingup) hideCalendar();
	} catch (e) {
	}
} 
document.HidePopupCalendarWindow = 	HidePopupCalendarWindow;
	
function calendarY2Kyear(number) { 
	inputText = number;
	number = number - 0;
	if (isNaN(number)) {
		return "????";
	}
	if (number < 100) {
		return (number < 70) ? number + 2000 : number + 1900; 
	} else {
		return number;
	}
} 

function calendarFormatDateField(day, month, year) {
	return '' + calendarY2Kyear(year) + '-' + calendarPadout(month - 0 + 1) + '-' + calendarPadout(day); 	
} 

function calendarFormatDateFieldChageDate(day, month, year) {
	return '' + calendarY2Kyear(year) + '/' + calendarPadout(month - 0 + 1) + '/' + calendarPadout(day); 	
} 

function calendarFormatDateFieldFromString(yy_mm_dd) {
	var d = '';
	var m = '';
	var y = '';
	var state = 'year';
	for(var i = 0; i<yy_mm_dd.length; i++) {
		if (yy_mm_dd.charAt(i) == '-') {
			if (state == 'year') {
				state = 'month';
			} else if (state == 'month') {
				state = 'day';
			}
		} else {
			if (state == 'month') {
				m += yy_mm_dd.charAt(i);
			} else if (state == 'day') {
				d += yy_mm_dd.charAt(i);
			} else {
				y += yy_mm_dd.charAt(i);
			}
		}
	}
	
	if (d == '') {
		d = 1;
	}
	if (m == '') {
		m = 1;
	}
	if (y == '') {
		var thisyear = (new Date()).getFullYear();
		y += thisyear;
	}
	
	return '' + calendarY2Kyear(y) + '-' + calendarPadout(m) + '-' + calendarPadout(d) ; 	
} 

function calendarFormatDateFieldFromDate(theDate) {
	return calendarFormatDateField(theDate.getDate(), theDate.getMonth(), theDate.getFullYear());	
}

function calendarFormatDateFieldFromINIDate(theDate) {
	return calendarFormatDateFieldChageDate(theDate.getDate(), theDate.getMonth(), theDate.getFullYear());	
}

function calendarPadout(number) { 
	return ((number-0) < 10) ? '0' + (number-0) : number; 
} 


function calendar_getAbsolutePosition (node, styleResult) {
	var node2 = node;
	var top = node.offsetTop;
	var left = node.offsetLeft;
	
	for (node=node.offsetParent; node != null; node=node.offsetParent) {
		top = node.offsetTop + top;
		left = node.offsetLeft + left;
	}
	
	styleResult.top = top;
	styleResult.left = left;
}

var firsttime = true; 
var calendarresultcode = null; 
var calendarinitday = null; 
var calendarinitmonth = null; 
var calendarinityear = null; 
var calendariframe = null; 

function calInit(cal) { 
	cal.calInit(calendarinitday, calendarinitmonth, calendarinityear); 
} 

var calendarstate = null; 

function calSaveState(s) { 
	calendarstate = s;
} 

function calRestoreState() { 
	return calendarstate; 
} 

function calResults(day, month, year) { 
	eval(calendarresultcode);

} 

function cancelCalendar(day, month, year) { 
	if (!calendar_isModal) {
		eval(calendarcancelcode);
		hideCalendar(); 
	}
} 

function growCalendarFrame(h, w) { 	
	if (!calendar_isModal) {
		var windowHeight = document.body.clientHeight;
		var calendarBottom = document.all.calendar.style.pixelTop + h;
		if (calendarBottom > windowHeight) {
			document.all.calendar.style.pixelTop = gXOffsetOfObjectToPositonNear - h;

			if (document.all.calendar.style.pixelTop < 0) {
				var sideAmount = calendarBottom - windowHeight;
				document.all.calendar.style.pixelTop = (gXOffsetOfObjectToPositonNear + gHeightOfObjectToPositonNear) - sideAmount;
			} 
			
			if (document.all.calendar.style.pixelTop < 0) {
				document.all.calendar.style.pixelTop = 0;
			} 
		}
	
		var windowWidth = document.body.clientWidth;
		var calendarEdge = document.all.calendar.style.pixelLeft + w;
		if (calendarEdge > windowWidth) {
			document.all.calendar.style.pixelLeft = windowWidth - w;
		}

		document.all.calendar.style.pixelHeight = h;
		document.all.calendar.style.pixelWidth = w;
	} else {
		window.resizeTo(w, h);
	}
}

function showCalendarForReal() { 
	startingup = false;
	
	if (!calendar_isModal) {
		document.all.calendar.style.visibility = '';
		document.all.calendar.focus();
	}
} 

hideCalendar_firsttime = true;
function hideCalendar() { 
	if (!calendar_isModal) {
		if (hideCalendar_firsttime==true && document.all.calendar.style.visibility == '') {
			hideCalendar_firsttime = false;
		}

		document.all.calendar.style.visibility = 'hidden';
		document.all.calendar.style.pixelTop = 0;
		document.all.calendar.style.pixelLeft = 0;
		document.all.calendar.style.pixelHeight = 1;
		document.all.calendar.style.pixelWidth = 1;
		showCalendar =  hideCalendar;
		ok_to_set_focus = false; 

	}
} 

var showCalendar =  hideCalendar;

function stripInitialSpaces(st) {
	for( var i=0; i<st.length; i++) {
		if (st.charAt(i) != ' ') break;
	}
	return st.substr(i, st.length-i);
}

function calender_expand_date(currentDateField, non_field,iniflag){
	if (currentDateField.value == "") return;
	
	var thisyear = (new Date()).getFullYear();
	var thismonth = (new Date()).getMonth()+1;
	
	currentDateField.value = stripInitialSpaces(currentDateField.value);
	currentDateField.value = currentDateField.value.replace(/\ /g, '-');
	currentDateField.value = currentDateField.value.replace(/\//g, '-');
	currentDateField.value = currentDateField.value.replace(/\./g, '-');

	 
    var slashCounter = 0;
    var lastChar = '';
    
	for(var i=0; i < currentDateField.value.length; i++) {
		lastChar = currentDateField.value.charAt(i);
		if(lastChar == '-') {
			slashCounter++;
		}
	}
	
	if (slashCounter == 2) {
		currentDateField.value = calendarFormatDateFieldFromString(currentDateField.value);
	} else if (slashCounter == 1) {
		currentDateField.value = thisyear + '-' + currentDateField.value;
		currentDateField.value = calendarFormatDateFieldFromString(currentDateField.value);
	} else if (slashCounter == 0) {
		currentDateField.value = thisyear + '-' + thismonth + '-' + currentDateField.value;
		currentDateField.value = calendarFormatDateFieldFromString(currentDateField.value);
	} else {
		currentDateField.value = calendarFormatDateFieldFromString(currentDateField.value);
	}
	
	var date = new Date(currentDateField.value.replace(/\-/g, '/'));
	if (isNaN(date)) {
		
		if (non_field == true) {
			throw "Not a valid date";
		} else {
			operationAlert("日期的格式不正確，請使用yyyy-mm-dd格式");
			currentDateField.focus();
			currentDateField.select();
		}
	} else {
		var LOWDATE = new Date('1800/01/01');	
		var HIGHDATE = new Date('9999/12/31');	
		
		if ((LOWDATE.getTime() > date.getTime()) || (HIGHDATE.getTime() < date.getTime())) {
			if (non_field == true) {
				throw "Not a valid date";
			} else {
				operationAlert("日期的格式不正確，請輸入1800-01-01到9990-12-31之間的日期");
				currentDateField.focus();
				currentDateField.select();
			}
		} else {
			  if (iniflag=="1"){
			  	//第一次初始化
			     currentDateField.value = calendarFormatDateFieldFromINIDate(date);	
			  }else{
				   currentDateField.value = calendarFormatDateFieldFromDate(date);
			  }
		}
	}
}

function cal_hilighttdate(obj) {
	obj.select();
}
	
function calendarCallOnChange(field) {
	if (field.onchange != null) {
		field.onchange();
	}
}

CalendarInit();


//modify by wangli add start 20050407
/**
  *双击得到时间
  *@param  this
  */
function getDateTime(field)
{
  var fieldCount = parseInt(getElementOrder(field));  //得到第几个元素
  fieldCount = fieldCount - 1 ;
 // alert("wangli : "+field.name);
  var fieldName = field.name;
  TogglePopupCalendarWindow('document.fm.'+fieldName+'['+fieldCount+']', '<%=(new DateTime(DateTime.current().toString(),DateTime.YEAR_TO_DAY).getYear()-15) %>', '<%=(new DateTime(DateTime.current().toString(),DateTime.YEAR_TO_DAY).getYear()+2) %>');
 // alert(fieldCount);
  return true;
}
//modify by wangli add end 20050407