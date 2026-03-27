YAHOO.namespace("SINOSOFT.widget");
(function(){
	function TimeWidget(container_id,container_config){
		this.init.apply(this,arguments);
	};
	TimeWidget.prototype={
		container_id:null,
		_container:null,
		_hourC:null,
		_hourid:null,
		_minuteC:null,
		_minuteid:null,
		_secondC:null,
		_secondid:null,
		_hourSpin:null,
		_minuteSpin:null,
		_secondSpin:null,
		_hourStr:":",
		_minuteStr:":",
		_secondStr:"",
		_speStrC:null,
		_speStrC2:null,
		_speStrC3:null,
		_currentVal:null,
		init:function(container_id,container_config){
			this.container_id=container_id;	
			if(container_config!=null){
				this._hourStr=container_config.hourStr||":";
				this._minuteStr=container_config.minuteStr||":";
				this._secondStr=container_config.secondStr||"";
				this._currentVal=container_config.date||Date();
			}else{
				this._currentVal=Date();
			}
			this.initStyles();
			this.render();
		},
		initStyles:function(){
			var Dom=YAHOO.util.Dom;
			this._container=Dom.get(this.container_id);
			
			this._hourC=document.createElement("DIV");
			this._hourid=this.container_id+"_hour";
			this._hourC.id=this._hourid;
			this._hourC.className="divLeft";
			
			this._minuteC=document.createElement("DIV");
			this._minuteid=this.container_id+"_minute";
			this._minuteC.id=this._minuteid;
			this._minuteC.className="divLeft";
			
			this._secondC=document.createElement("DIV");
			this._secondid=this.container_id+"_second";
			this._secondC.id=this._secondid;
			this._secondC.className="divLeft";
			
			this._speStrC=document.createElement("DIV");
			this._speStrC.innerHTML=this._hourStr;
			this._speStrC.className="divLeft";
			
			this._speStrC2=document.createElement("DIV");
			this._speStrC2.innerHTML=this._minuteStr;
			this._speStrC2.className="divLeft";
			
			this._speStrC3=document.createElement("DIV");
			this._speStrC3.innerHTML=this._secondStr;
			this._speStrC3.className="divLeft";
			
			this._container.appendChild(this._hourC);
		 	this._container.appendChild(this._speStrC);
			this._container.appendChild(this._minuteC);
			this._container.appendChild(this._speStrC2);
			this._container.appendChild(this._secondC);
			this._container.appendChild(this._speStrC3);

			
		},
		render:function(){
			var SpinWidget=YAHOO.SINOSOFT.widget.SpinWidget;
			this._hourSpin=new SpinWidget(this._hourid,{min:0,max:23,width:35});
			this._minuteSpin=new SpinWidget(this._minuteid,{min:0,max:59,width:35});
			this._secondSpin=new SpinWidget(this._secondid,{min:0,max:59,width:35});
			this.setTime(this._currentVal);
		},
		
		getSelectedTime:function(){
			var _date=new Date();
			_date.setHours(this.getSelectedHour());
			_date.setMinutes(this.getSelectedMinute());
			_date.setSeconds(this.getSelectedSecond());
			return _date;
		},
		getSelectedHour:function(){
			return this._hourSpin.getCurrentVal();
		},
		getSelectedMinute:function(){
			return this._minuteSpin.getCurrentVal();
		},
		getSelectedSecond:function(){
			return this._secondSpin.getCurrentVal();
		},
		setHourVal:function(val){
			this._hourSpin.setCurrentVal(val);
		},
		setMinuteVal:function(val){
			this._minuteSpin.setCurrentVal(val);
		},
		setSecondVal:function(val){
			this._secondSpin.setCurrentVal(val);
		},
		//chang string to date class,
		setTime:function(curdate){
			var date;
			if(curdate.construtor=String)
				date=new Date(curdate);
			else if(curdate.constructor=Date)
				date=curdate;
			this.setHourVal(date.getHours());
			this.setMinuteVal(date.getMinutes());
			this.setSecondVal(date.getSeconds());
		}
	};
	YAHOO.SINOSOFT.widget.TimeWidget=TimeWidget;	
})();
YAHOO.register("TimeWidget",YAHOO.SINOSOFT.widget.TimeWidget,{version: "2.8.0r4", build: "2449"});