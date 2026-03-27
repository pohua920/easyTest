YAHOO.namespace("saa.calendar");
YAHOO.saa.calendar = [];
function init_calendar(calContainer,imgBtn,dateText,dateType){
	YAHOO.saa.calendar[dateText] = new YAHOO.widget.Calendar(dateText,calContainer,{close:true});

	YAHOO.saa.calendar[dateText].cfg.setProperty("MONTHS_SHORT",   ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"]);
	YAHOO.saa.calendar[dateText].cfg.setProperty("MONTHS_LONG",    ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"]);
	YAHOO.saa.calendar[dateText].cfg.setProperty("WEEKDAYS_1CHAR", ["日", "一", "二", "三", "四", "五", "六"]);
	YAHOO.saa.calendar[dateText].cfg.setProperty("WEEKDAYS_SHORT", ["日", "一", "二", "三", "四", "五", "六"]);
	YAHOO.saa.calendar[dateText].cfg.setProperty("WEEKDAYS_MEDIUM",["日", "一", "二", "三", "四", "五", "六"]);
	YAHOO.saa.calendar[dateText].cfg.setProperty("WEEKDAYS_LONG",  ["日", "一", "二", "三", "四", "五", "六"]);

	YAHOO.saa.calendar[dateText].render();

	YAHOO.util.Dom.setStyle(calContainer,"display","none");
	YAHOO.util.Dom.setStyle(calContainer,"position","absolute");


	YAHOO.util.Event.addListener(imgBtn,"click",switchCalendar,YAHOO.saa.calendar[dateText],true);
	//YAHOO.util.Event.addListener(imgBtn,"click",YAHOO.saa.calendar[dateText].show,YAHOO.saa.calendar[dateText],true);

	//YAHOO.saa.calendar[dateText].selectEvent.subscribe(handleSelect,YAHOO.saa.calendar[dateText],false);
	YAHOO.saa.calendar[dateText].selectEvent.subscribe(handleSelect,YAHOO.saa.calendar[dateText],true);


	function switchCalendar(){
		if(YAHOO.util.Dom.getStyle(YAHOO.saa.calendar[dateText].oDomContainer,"display") == "block" ){
			YAHOO.util.Dom.setStyle(calContainer,"display","none");
		}else{
			YAHOO.util.Dom.setStyle(calContainer,"display","block");
		
			//YAHOO.util.Dom.setStyle(calContainer,"left","90px");
			//YAHOO.util.Dom.setStyle(calContainer,"top","25px");
			
		}
	}


	function handleSelect(type,args,obj){
		var dates = args[0];
		var date = dates[0];
		var year = date[0];
		var month = date[1];
		if( month < 10){
			month = "0" + month;
		}
		var day = date[2];
		if( day < 10){
			day = "0" + day;
		}

		var dateInput = document.getElementById(dateText);

		if(dateType=="toSecond"){
			var date=new Date();
			var hour=date.getHours();
			if(hour<10){
				hour="0"+hour;
			}
			var minutes=date.getMinutes();
			if(minutes<10){
				minutes="0"+minutes;
			}
			var second=date.getSeconds();
			if(second<10){
				second="0"+second;
			}

			dateInput.value = year + "-"+month+"-"+day+" "+hour+":"+minutes+":"+second;
		}else if(dateType=="toMinute"){
			var date=new Date();
			var hour=date.getHours();
			if(hour<10){
				hour="0"+hour;
			}
			var minutes=date.getMinutes();
			if(minutes<10){
				minutes="0"+minutes;
			}
			dateInput.value = year + "-"+month+"-"+day+" "+hour+":"+minutes;
		}
		else{
			dateInput.value = year + "-"+month+"-"+day;
		}

		YAHOO.util.Dom.setStyle(calContainer,"display","none");
	}

}