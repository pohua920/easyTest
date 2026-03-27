YAHOO.namespace("SINOSOFT.widget");
(function(){
	var CalendarWidget=function(Id,formatter){
		this.textId=Id;
		this.calBtnId=Id+"_btn";
		this.formatter=formatter;
	}

	CalendarWidget.prototype.run=function(){
		 var Event = YAHOO.util.Event, Dom = YAHOO.util.Dom,dialog,calendar;
		 var me=this;
		 var showBtn = Dom.get(this.calBtnId);
		 var calDateText=Dom.get(this.textId);
		 
		 function showCalDate(){
			 if(dialog){
				 dialog.hide();
			 }
			 dialog=null;
	         if (!dialog) {
	             Event.on(document, "click", function(e) {
	                 var el = Event.getTarget(e);
	                 var dialogEl = dialog.element;
	                 if (el != dialogEl && !Dom.isAncestor(dialogEl, el) && el != showBtn && !Dom.isAncestor(showBtn, el)&& el != calDateText && !Dom.isAncestor(calDateText, el)) {
	                     dialog.hide();
	                 }
	             });
	             function resetHandler() {
	                 var selDates = calendar.getSelectedDates();
	                 var resetDate;
	                 if (selDates.length > 0) {
	                     resetDate = selDates[0];
	                 } else {
	                     resetDate = calendar.today;
	                 }
	                 calendar.cfg.setProperty("pagedate", resetDate);
	                 calendar.render();
	             }
	             function closeHandler() {
	                 dialog.hide();
	             }
	             dialog = new YAHOO.widget.Dialog('container', {
	                 visible:false,
	                 context:[calDateText, "tl", "bl"],
	                 buttons:[{text:"\u91cd\u7f6e", handler: resetHandler, isDefault:true}, {text:"\u5173\u95ed", handler: closeHandler}],
	                 draggable:false,
	                 width:200,
	                 close:true
	             });
	             dialog.setHeader('\u9009\u62e9\u65f6\u95f4');
	             dialog.setBody('<div id="cal_'+me.textId+'"></div>');
	             dialog.render(document.body);
	             dialog.showEvent.subscribe(function() {
	                 if (YAHOO.env.ua.ie) {
	                     dialog.fireEvent("changeContent");
	                 }
	             });
	         }
	             calendar = new YAHOO.widget.Calendar('cal_'+me.textId, {
	                 iframe:false,          // Turn iframe off, since container has iframe support.
	                 hide_blank_weeks:true  // Enable, to demonstrate how we handle changing height, using changeContent
	             });

	             var months = ["\u4e00\u6708","\u4e8c\u6708", "\u4e09\u6708", "\u56db\u6708", "\u4e94\u6708", "\u516d\u6708", "\u4e03\u6708", "\u516b\u6708", "\u4e5d\u6708", "\u5341\u6708", "\u5341\u4e00\u6708", "\u5341\u4e8c\u6708"];
	             var weekdays = ["\u65e5", "\u4e00", "\u4e8c", "\u4e09", "\u56db", "\u4e94", "\u516d"];
	             calendar.cfg.setProperty("MONTHS_SHORT", months);
	             calendar.cfg.setProperty("MONTHS_LONG", months);
	             calendar.cfg.setProperty("WEEKDAYS_1CHAR", weekdays);
	             calendar.cfg.setProperty("WEEKDAYS_SHORT", weekdays);
	             calendar.cfg.setProperty("WEEKDAYS_MEDIUM", weekdays);
	             calendar.cfg.setProperty("WEEKDAYS_LONG", weekdays);

	             calendar.render();
	             calendar.selectEvent.subscribe(function() {
	                 if (calendar.getSelectedDates().length > 0) {
	                      var selDate = calendar.getSelectedDates()[0];
	                      var config={ format:me.formatter };
	                      var str=YAHOO.util.Date.format(selDate,config);
	                      calDateText.value = str;
	                 } else {
	                	 calDateText.value = "";
	                 }
	                 dialog.hide();
	             });
	             calendar.renderEvent.subscribe(function() {
	                 dialog.fireEvent("changeContent");
	             });
	         var seldate = calendar.getSelectedDates();
	         if (seldate.length > 0) {
	        	 calendar.cfg.setProperty("pagedate", seldate[0]);
	        	 calendar.render();
	         }
	         dialog.show();
		 }
		 
	//当text聚焦的时候，弹出窗口	 
//		 Event.on(calDateText,"focus",function(e){
//			 showCalDate();
//		 });
		 
		 Event.on(showBtn, "click", function(e) {
			 showCalDate();
		  });
	};
	YAHOO.SINOSOFT.widget.CalendarWidget=CalendarWidget;
})();
YAHOO.register("CalendarWidget",YAHOO.SINOSOFT.widget.CalendarWidget,{version: "2.8.0r4", build: "2449"});
