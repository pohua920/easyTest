YAHOO.namespace("SINOSFOT.widget");
(function(){

	var CalendarTimeWidget=function (Id,formatter){
		this.textId=Id;
		this.calBtnId=Id+"_btn";
		this.formatter=formatter;
	};

	CalendarTimeWidget.prototype.run=function(){
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
	             function submitHandler() {
	                 var selDates = calendar.getSelectedDateTimes();
	                 var config={ format:me.formatter};
	                 var str=YAHOO.util.Date.format(selDates,config);
	                 str+=" "+selDates.getHours()+":"+selDates.getMinutes()+":"+selDates.getSeconds();
	                 calDateText.value=str;
	                 dialog.hide();
	             }
	             function emptyHandler() {
	            	 calDateText.value="";
	            	 dialog.hide();
	             }
	             dialog = new YAHOO.widget.Dialog('container', {
	                 visible:false,
	                 context:[calDateText, "tl", "bl"],
	                 buttons:[{text:"submit", handler: submitHandler, isDefault:true}, {text:"empty", handler: emptyHandler}],
	                 draggable:false,
	                 width:200,
	                 close:true
	             });
	             dialog.setHeader('Choose the Time');
	             dialog.setBody('<div id="caltime_'+me.textId+'"></div>');
	             dialog.render(document.body);
	             dialog.showEvent.subscribe(function() {
	                 if (YAHOO.env.ua.ie) {
	                     dialog.fireEvent("changeContent");
	                 }
	             });
	         }
	             calendar = new YAHOO.SINOSOFT.util.CalendarDateTime('caltime_'+me.textId, {
	                 iframe:false,          // Turn iframe off, since container has iframe support.
	                 hide_blank_weeks:true  // Enable, to demonstrate how we handle changing height, using changeContent
	             });
	             calendar.render();
//	             calendar.selectEvent.subscribe(function() {
//	                 if (calendar.getSelectedDates().length > 0) {
//	                      var selDate = calendar.getSelectedDates()[0];
//	                      var config={ format:me.formatter };
//	                      var str=YAHOO.util.Date.format(selDate,config);
//	                      calDateText.value = str;
//	                 } else {
//	                	 calDateText.value = "";
//	                 }
//	                 dialog.hide();
//	             });
	             calendar.renderEvent.subscribe(function() {
	                 dialog.fireEvent("changeContent");
	             });
	         var seldate = calendar.getSelectedDateTimes();
	         if (seldate.length > 0) {
	        	 calendar.cfg.setProperty("pagedate", seldate[0]);
	        	 calendar.render();
	         }
	         dialog.show();
		 }
		 
//		 
//		 Event.on(calDateText,"focus",function(e){
//			 showCalDate();
//		 });
		 
		 Event.on(showBtn, "click", function(e) {
			 showCalDate();
		  });
		
	};
	YAHOO.SINOSOFT.widget.CalendarTimeWidget=CalendarTimeWidget;
})();
YAHOO.register("CalendarTimeWidget",YAHOO.SINOSOFT.widget.CalendarTimeWidget,{version: "2.8.0r4", build: "2449"});