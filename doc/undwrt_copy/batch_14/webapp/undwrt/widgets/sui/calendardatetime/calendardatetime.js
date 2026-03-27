YAHOO.namespace('SINOSOFT.util');
YAHOO.SINOSOFT.util.CalendarDateTime = function(o) {
	YAHOO.SINOSOFT.util.CalendarDateTime.superclass.constructor.call(this, o);
	var months = [ "\u4e00\u6708", "\u4e8c\u6708", "\u4e09\u6708",
			"\u56db\u6708", "\u4e94\u6708", "\u516d\u6708", "\u4e03\u6708",
			"\u516b\u6708", "\u4e5d\u6708", "\u5341\u6708",
			"\u5341\u4e00\u6708", "\u5341\u4e8c\u6708" ];
	var weekdays = [ "\u65e5", "\u4e00", "\u4e8c", "\u4e09", "\u56db",
			"\u4e94", "\u516d" ];
	this.cfg.setProperty("MONTHS_SHORT", months);
	this.cfg.setProperty("MONTHS_LONG", months);
	this.cfg.setProperty("WEEKDAYS_1CHAR", weekdays);
	this.cfg.setProperty("WEEKDAYS_SHORT", weekdays);
	this.cfg.setProperty("WEEKDAYS_MEDIUM", weekdays);
	this.cfg.setProperty("WEEKDAYS_LONG", weekdays);
	this.timeWidget=null; 
	
	this.getSelectedDateTimes=function(){
		 var selDates = this.getSelectedDates(),resetDate;
	     if (selDates.length > 0) {
	         resetDate = selDates[0];
	     } else {
	         resetDate = this.today;
	     }
		 resetDate.setHours(this.timeWidget.getSelectedHour());
		 resetDate.setMinutes(this.timeWidget.getSelectedMinute());
		 resetDate.setSeconds(this.timeWidget.getSelectedSecond());
		 return resetDate;
	};
};
var	renderFooter2 = function(html) {
		html[html.length] = "<tfoot><tr class='calweekdayrow'><th colspan='7'width='100%'>";   
		var timeId="timeWidget_"+this.id;
		html[html.length] ="<div id='"+timeId+"'><div>";
		html[html.length] = "</th></tr></tfoot>";
		return html;
	};
	
var onRender2=function(){
	this.timeWidget=new YAHOO.SINOSOFT.widget.TimeWidget("timeWidget_"+this.id,{hourStr:'\u65f6',minuteStr:'\u5206',secondStr:'\u79d2'});
	//timeWidget.render();
};

YAHOO.lang.extend(YAHOO.SINOSOFT.util.CalendarDateTime, YAHOO.widget.Calendar,
		{
			renderFooter : renderFooter2,
			onRender:onRender2
		});
