YAHOO.namespace("SINOSOFT.widget");
(function(){
	var Dom=YAHOO.util.Dom;
	var NewCalendar=function(o){
		NewCalendar.superclass.constructor.call(this, o);
		var Calendar=YAHOO.widget.Calendar,
			DateMath = YAHOO.widget.DateMath,
		    DEF_CFG = Calendar.DEFAULT_CONFIG;
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
		this.monthSpin=null;
		this.yearSpin=null;
		this.setMonthWithUp=function(monthVal){
		        var cfgPageDate = DEF_CFG.PAGEDATE.key,
				prevDate = this.cfg.getProperty(cfgPageDate);
				var newDate=new Date(prevDate);
				newDate.setMonth(monthVal-1);
				this.cfg.setProperty(cfgPageDate, newDate);
				this.resetRenderers();
				this.changePageEvent.fire(prevDate, newDate);
		};
		this.setYearWithUp=function(yearVal){
				var cfgPageDate = DEF_CFG.PAGEDATE.key,
				prevDate = this.cfg.getProperty(cfgPageDate);
				var newDate=new Date(prevDate);
				newDate.setYear(yearVal);
				this.cfg.setProperty(cfgPageDate, newDate);
				this.resetRenderers();
				this.changePageEvent.fire(prevDate, newDate);
		};
	};
	var renderHeader=function(html){
		var colSpan = 7,
            DEPR_NAV_LEFT = "us/tr/callt.gif",
            DEPR_NAV_RIGHT = "us/tr/calrt.gif",
            cfg = this.cfg,
			Calendar=YAHOO.widget.Calendar,
			DateMath = YAHOO.widget.DateMath,
		    DEF_CFG = Calendar.DEFAULT_CONFIG,
            pageDate = cfg.getProperty(DEF_CFG.PAGEDATE.key),
            strings= cfg.getProperty(DEF_CFG.STRINGS.key),
            prevStr = (strings && strings.previousMonth) ?  strings.previousMonth : "",
            nextStr = (strings && strings.nextMonth) ? strings.nextMonth : "",
            monthLabel="";

        if (cfg.getProperty(DEF_CFG.SHOW_WEEK_HEADER.key)) {
            colSpan += 1;
        }
    
        if (cfg.getProperty(DEF_CFG.SHOW_WEEK_FOOTER.key)) {
            colSpan += 1;
        }

        html[html.length] = "<thead>";
        html[html.length] =  "<tr>";
        html[html.length] =   '<th colspan="' + colSpan + '" class="' + this.Style.CSS_HEADER_TEXT + '">';
        html[html.length] =    '<div class="' + this.Style.CSS_HEADER + '">';

        var renderLeft, renderRight = false;

        if (this.parent) {
            if (this.index === 0) {
                renderLeft = true;
            }
            if (this.index == (this.parent.cfg.getProperty("pages") -1)) {
                renderRight = true;
            }
        } else {
            renderLeft = true;
            renderRight = true;
        }

        if (renderLeft) {
            monthLabel  = this._buildMonthLabel(DateMath.subtract(pageDate, DateMath.MONTH, 1));

            var leftArrow = cfg.getProperty(DEF_CFG.NAV_ARROW_LEFT.key);
            // Check for deprecated customization - If someone set IMG_ROOT, but didn't set NAV_ARROW_LEFT, then set NAV_ARROW_LEFT to the old deprecated value
            if (leftArrow === null && Calendar.IMG_ROOT !== null) {
                leftArrow = Calendar.IMG_ROOT + DEPR_NAV_LEFT;
            }
            var leftStyle = (leftArrow === null) ? "" : ' style="background-image:url(' + leftArrow + ')"';
            html[html.length] = '<div id="left_arrow"><a class="' + this.Style.CSS_NAV_LEFT + '"' + leftStyle + ' href="#">' + prevStr + ' (' + monthLabel + ')' + '</a></div>';
        }
        var month_str="<div id='container' style='padding-left:25px;'><div id='sinosoft_month_"+this.id+"' style='float:left;'></div><div style='float:left;'>\u6708&nbsp</div>";
		var year_str="<div id='sinosoft_year_"+this.id+"'></div></div>";
        var lbl = month_str+year_str;
        var cal = this.parent || this;
        if (cal.cfg.getProperty("navigator")) {
            lbl = "<a class=\"" + this.Style.CSS_NAV + "\" href=\"#\">" + lbl + "</a>";
        }
        html[html.length] = lbl;

        if (renderRight) {
            monthLabel  = this._buildMonthLabel(DateMath.add(pageDate, DateMath.MONTH, 1));

            var rightArrow = cfg.getProperty(DEF_CFG.NAV_ARROW_RIGHT.key);
            if (rightArrow === null && Calendar.IMG_ROOT !== null) {
                rightArrow = Calendar.IMG_ROOT + DEPR_NAV_RIGHT;
            }
            var rightStyle = (rightArrow === null) ? "" : ' style="background-image:url(' + rightArrow + ')"';
            html[html.length] = '<div id="right_arrow"><a class="' + this.Style.CSS_NAV_RIGHT + '"' + rightStyle + ' href="#">' + nextStr + ' (' + monthLabel + ')' + '</a></div>';
        }

        html[html.length] = '</div>\n</th>\n</tr>';

        if (cfg.getProperty(DEF_CFG.SHOW_WEEKDAYS.key)) {
            html = this.buildWeekdays(html);
        }
        
        html[html.length] = '</thead>';
    
        return html;
	};
	var render=function(){
		var  cfg = this.cfg,
			 Calendar=YAHOO.widget.Calendar,
			 DateMath = YAHOO.widget.DateMath,
			 Event=YAHOO.util.Event,
		     DEF_CFG = Calendar.DEFAULT_CONFIG,
			 DATE_TIME=this.cfg.getProperty(DEF_CFG.PAGEDATE.key);
		this.beforeRenderEvent.fire();

        // Find starting day of the current month
        var workingDate = DateMath.findMonthStart(this.cfg.getProperty(DEF_CFG.PAGEDATE.key));

        this.resetRenderers();
        this.cellDates.length = 0;

        Event.purgeElement(this.oDomContainer, true);

        var html = [];

        html[html.length] = '<table cellSpacing="0" class="' + this.Style.CSS_CALENDAR + ' y' + (workingDate.getFullYear() + this.Locale.YEAR_OFFSET) +'" id="' + this.id + '">';
        html = this.renderHeader(html);
        html = this.renderBody(workingDate, html);
        html = this.renderFooter(html);
        html[html.length] = '</table>';

        this.oDomContainer.innerHTML = html.join("\n");
		
		
	    this.monthSpin=null;		
		this.monthSpin=new YAHOO.SINOSOFT.widget.SpinWidget("sinosoft_month_"+this.id,{min:1,max:12,current:DATE_TIME.getMonth()+1,width:35});
		this.yearSpin=null;
		this.yearSpin=new YAHOO.SINOSOFT.widget.SpinWidget("sinosoft_year_"+this.id,{min:1988,max:2100,current:DATE_TIME.getFullYear(),width:50});
	
		
        this.applyListeners();
        this.cells = Dom.getElementsByClassName(this.Style.CSS_CELL, "td", this.id);
    
        this.cfg.refireEvent(DEF_CFG.TITLE.key);
        this.cfg.refireEvent(DEF_CFG.CLOSE.key);
        this.cfg.refireEvent(DEF_CFG.IFRAME.key);
		
		this.renderEvent.fire();
	};
	var applyListeners=function(){
	   NewCalendar.superclass.applyListeners.call(this,arguments);
	   var Dom=YAHOO.util.Dom,Event=YAHOO.util.Event;	   
	   var month_text=Dom.get('sinosoft_month_'+this.id+'_text');
	   Event.addListener(month_text,'blur',function(){                   
				this.setMonthWithUp(month_text.value);	
		     },this,true);
	   var year_text=Dom.get('sinosoft_year_'+this.id+'_text');
	   Event.addListener(year_text,'blur',function(){                   
				this.setYearWithUp(year_text.value);	
		     },this,true);
	};
	YAHOO.SINOSOFT.widget.Calendar=NewCalendar;
	YAHOO.lang.extend(YAHOO.SINOSOFT.widget.Calendar,YAHOO.widget.Calendar,{
	   renderHeader:renderHeader,
	   render:render,
	   applyListeners:applyListeners
	});
})();
