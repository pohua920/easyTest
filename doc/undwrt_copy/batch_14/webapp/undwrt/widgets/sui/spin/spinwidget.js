 YAHOO.namespace("SINOSOFT.widget");
(function(){

  function SpinWidget(id,config){
		this.init.apply(this,arguments);		
  };
  
function SpinControlAcceleration(increment, milliseconds)
{
  increment = parseFloat(increment);
  if(isNaN(increment) || increment < 0)
    increment = 0;
  
  milliseconds = parseInt(milliseconds);
  if(isNaN(milliseconds) || milliseconds < 0)
    milliseconds = 0;
    
  this.GetIncrement = function()
  { return increment; };
  
  this.GetMilliseconds = function()
  { return milliseconds; }    ;
}

function SpinControlAccelerationCollection()
{
  var _array = new Array();
  
  this.GetCount = function()
  { return _array.length; };
  
  this.GetIndex = function(index)
  {
    if(index < 0 || index >= _array.length)
      return null;
    
    return _array[index];
  };
  
  this.RemoveIndex = function(index)
  {
    if(index < 0 || index >= _array.length)
      return;
     
    newArray = new Array(); 
    for(var i=0; i<_array.length; i++)
    {
      if(i == index)
        continue;
      newArray.push(_array[i]);
    }
    _array = newArray;
  };
  
  this.Clear = function()
  {
    _array = new Array();
  };
  
  this.Add = function(spa)
  {
    if(spa.constructor != SpinControlAcceleration)
      return;
      
    if(_array.length == 0)
    {
      _array.push(spa);
      return;
    }
    
    var newSec = spa.GetMilliseconds();
    if(newSec > _array[_array.length-1].GetMilliseconds())
    { 
      _array.push(spa);
      return;
    }
    
    var added = false;
    var newArray = new Array();    
    var indexSec;
    for(var i=0; i<_array.length; i++)
    {
      if(added)
      {
        newArray.push(_array[i]);
      }
      else 
      {
        indexSec = _array[i].GetMilliseconds();
        if(indexSec < newSec)
        {
          newArray.push(_array[i]);        
        }
        else if(indexSec == newSec)
        {
          newArray.push(spa);
          added = true;
        }
        else
        {
          newArray.push(_array[i]);
          newArray.push(spa);
          added = true;
        }
      }
    }
    _array = newArray;
    return;     
  };
};

  
SpinWidget._EVENT_TYPES={
	UPBTN_MOUSE_DOWN : "mousedown",
	UPBTN_MOUSE_UP : "mouseup",
	UPBTN_MOUSE_OVER : "mouseover",
	
	DOWNBTN_MOUSE_DOWN: "mousedown",
	DOWNBTN_MOUSE_UP : "mouseup",
	DOWNBTN_MOUSE_OVER : "mouseover",
	
	TEXTBOX_CHANGE: "change",
	TEXTBOX_FOCUS: "focus",
	TEXTBOX_BLUR: "blur"
};
  
SpinWidget.prototype={
		Config : null,
		id : null,
		_upButton : null,
		_downButton : null,
		_textBox : null,
		_container:null,
		_leftEdge:null,
		_bottomEdge:null,
		_topEdge:null,
		_rightEdge:null,
		_currentValue:1,
		_minimumVal:0,
		_maximumVal:60,
		_interval:-1,
		_running:0,
		_width:50,
		_increment:1,
		_timeStart:0,
		_accelerationCollection:null,
		_callbackArray: null,
		_bodyEventHooked : false,
		
		init:function(id, config) {
		    this.id=id;
			if(config!=null){
				this._minimumVal=config.min||0;
				this._maximumVal=config.max||60;
				this._currentValue=config.current||0;
				this._width=config.width||50;
			}
			this._accelerationCollection=new SpinControlAccelerationCollection();
			this._callbackArray=new Array();
			this.initStyles();
			this.initEvents();
		},
		
		setCurrentVal:function(val){
			if(val!=null){
				this.UpdateCurrentValue(val);
			}
		},
		initEvents : function() {
			var defEvents = SpinWidget._EVENT_TYPES;
			var Event=YAHOO.util.Event,
            CE = YAHOO.util.CustomEvent,
            spinWidget = this; 
			

			
//			timeWidget.upbtnMouseDownEvent=new CE(defEvents.UPBTN_MOUSE_DOWN);
//			timeWidget.upbtnMouseDownEvent.subscribe(timeWidget.onUpBtnMouseDown,this,true);
			
//			timeWidget.upbtnMouseUpEvent=new CE(defEvents.UPBTN_MOUSE_UP);
//			timeWidget.upbtnMouseUpEvent.subscribe(timeWidget.onUpbtnMouseUp,this,true);
			
//			timeWidget.upbtnMouseOverEvent=new CE(defEvents.UPBTN_MOUSE_OVER);
//			timeWidget.upbtnMouseOverEvent.subscribe(timeWidget.onUpbtnMouseOver,this,true);
			//====================================================
//			timeWidget.downbtnMouseDownEvent=new CE(defEvents.DOWNBTN_MOUSE_DOWN);
//			timeWidget.downbtnMouseDownEvent.subscribe(timeWidget.onDownbtnMouseDown,this,true);
//			
//			timeWidget.downbtnMouseUpEvent=new CE(defEvents.DOWNBTN_MOUSE_UP);
//			timeWidget.downbtnMouseUpEvent.subscribe(timeWidget.onDownbtnMouseUp,this,true);
//			
//			timeWidget.downbtnMouseOverEvent=new CE(defEvents.DOWNBTN_MOUSE_OVER);
//			timeWidget.downbtnMouseOverEvent.subscribe(timeWidget.onDownbtnMouseOver,this,true);
			//======================================================
			
//			timeWidget.textboxChangeEvent=new CE(defEvents.TEXTBOX_CHANGE);
//			timeWidget.textboxChangeEvent.subscribe(timeWidget.onTextboxChange,this,true);
			
//			timeWidget.textboxFocueEvent=new CE(defEvents.TEXTBOX_FOCUS);
//			timeWidget.textboxFocueEvent.subscribe(timeWidget.onTextboxFocus,this,true);
			
//			timeWidget.textboxBlurEvent=new CE(defEvents.TEXTBOX_BLUR);
//			timeWidget.textboxBlurEvent.subscribe(timeWidget.onTextboxBlur,this,true);
    //---------------------------------------!!!----------------------
			Event.addListener(spinWidget._upButton,defEvents.UPBTN_MOUSE_DOWN,spinWidget.onUpBtnMouseDown,this,true);
			Event.addListener(spinWidget._upButton,defEvents.UPBTN_MOUSE_UP,spinWidget.onUpbtnMouseUp,this,true);
			Event.addListener(spinWidget._upButton,defEvents.UPBTN_MOUSE_OVER,spinWidget.onUpbtnMouseOver,this,true);
			
			Event.addListener(spinWidget._downButton,defEvents.DOWNBTN_MOUSE_DOWN,spinWidget.onDownbtnMouseDown,this,true);
			Event.addListener(spinWidget._downButton,defEvents.DOWNBTN_MOUSE_UP,spinWidget.onDownbtnMouseUp,this,true);
			Event.addListener(spinWidget._downButton,defEvents.DOWNBTN_MOUSE_OVER,spinWidget.onDownbtnMouseOver,this,true);
			
			Event.addListener(spinWidget._textBox,defEvents.TEXTBOX_CHANGE,spinWidget.onTextboxChange,this,true);
			Event.addListener(spinWidget._textBox,defEvents.TEXTBOX_FOCUS,spinWidget.onTextboxFocus,this,true);
			Event.addListener(spinWidget._textBox,defEvents.TEXTBOX_BLUR,spinWidget.onTextboxBlur,this,true);
						
		},
		
		
		initStyles: function(){
			this._container = YAHOO.util.Dom.get(this.id);
			this. _container.className = 'spinContainer';
  
			this._leftEdge = document.createElement("DIV");
			this._leftEdge.className = 'spinLeftRightEdge';
			this._leftEdge.style.left = '0px';
  
			this._bottomEdge = document.createElement("DIV");
			this._bottomEdge.className = 'spinTopBottomEdge';
			this._bottomEdge.style.top = '19px';
  
			this._topEdge = document.createElement("DIV");
			this._topEdge.className = 'spinTopBottomEdge';
			this._topEdge.style.top = '0px';
  
			this._rightEdge = document.createElement("DIV");
			this._rightEdge.className = 'spinLeftRightEdge';
			this._rightEdge.style.right = '0px';
  
			this._textBox = document.createElement("INPUT");
			this._textBox.id=this.id+"_text";
			this._textBox.type = 'text';
			this._textBox.className = 'spinInput';
			this._textBox.value = this._currentValue;
  
			this._upButton = document.createElement("DIV");
			this._upButton.id=this.id+"_spinUpBtn";
			this._upButton.className = 'spinUpBtn';
			
			this._downButton = document.createElement("DIV");
			this._downButton.id=this.id+"_spinDownBtn";
			this._downButton.className = 'spinDownBtn';
			
			if(YAHOO.env.ua.ie ==6){
				this._downButton.style.backgroundColor = '#FFFFFF';
				this._upButton.style.backgroundColor = '#FFFFFF';
			}
			
			this._container.appendChild(this._leftEdge);
			this._container.appendChild(this._bottomEdge);
			this._container.appendChild(this._topEdge);
			this._container.appendChild(this._rightEdge);
			this._container.appendChild(this._textBox);
			this._container.appendChild(this._upButton);
			this._container.appendChild(this._downButton);  
			
			this._container.style.width = this._width + 'px';
			this._bottomEdge.style.width = (this._width - 1) + 'px';
			this._topEdge.style.width = (this._width - 1) + 'px';
			this._textBox.style.width = (this._width - 20) + 'px';  
		},
		
		setupConfig: function(){
		
		},
		
		render:function(){
		
		},
		
		Run: function ()  {
			var _this=this;
			if(_this._running == 0)
					return;
			var elapsed = new Date().getTime() - _this._timeStart;
			var inc = _this._increment;
			
			if(_this._accelerationCollection.GetCount() != 0)
			{
				inc = 0;
				for(var i = 0; i<_this._accelerationCollection.GetCount(); i++)
				{
				if(elapsed < _this._accelerationCollection.GetIndex(i).GetMilliseconds())
				break;
				inc = _this._accelerationCollection.GetIndex(i).GetIncrement();
				}    
			}
			else if(elapsed < 600)
			{
				return;
			}
    
			_this.DoChange(inc);
		},
		
		ClearBtns:function(e){
			var target = this.getEventTarget(e);
			if(target == this._upButton || target == this._downButton)
				return;
			this._upButton.className = 'spinUpBtn';
			this._downButton.className = 'spinDownBtn';
			this.CancelRunning();
    
			if(this._bodyEventHooked)
			{
				YAHOO.util.Event.addListener(document.body, 'mouseover', this.ClearBtns,this,true);
				this._bodyEventHooked = false;
			}
			return this.cancelEvent(e);
		},
		
		getEventTarget:function(e)
		{
			e=e||window.event;
			return e.target || e.srcElement;
		},
		
		onUpBtnMouseDown:function(e){
			this.UpPress(e);
		},
		
		onUpbtnMouseUp:function(e){
		},
		
		onUpbtnMouseOver:function(e){
			if(!this._bodyEventHooked)
					YAHOO.util.Event.addListener(document.body, 'mouseover', this.ClearBtns,this,true);
		    this._upButton.className = 'spinUpBtnHover';
			this._downButton.className = 'spinDownBtn';
			this.CancelRunning();
		},
		
		onDownbtnMouseDown:function(e){
			this.DownPress(e);
		},
		
		onDownbtnMouseUp:function(e){
		
		},
		
		
		onDownbtnMouseOver:function(e){
			    if(!this._bodyEventHooked)
					YAHOO.util.Event.addListener(document.body, 'mouseover', this.ClearBtns,this,true);    
				this._upButton.className = 'spinUpBtnHover';
				this._downButton.className = 'spinDownBtn';
				this.CancelRunning();
				return this.cancelEvent(e);
		},
		CancelRunning:function(){
			this._running = 0;
			if(this._interval != -1)
			{
			clearInterval(this._interval);
			this._interval = -1;
			}
		},
		
		UpPress : function(e){
		    this._upButton.className = 'spinUpBtnPress';
			this._downButton.className = 'spinDownBtn';
			this.StartRunning(1);
			this._textBox.focus();
			return this.cancelEvent(e);
		},
		
		DownPress :function(e){
			this._upButton.className = 'spinUpBtn';
			this._downButton.className = 'spinDownBtnPress';
			this.StartRunning(-1);
			this._textBox.focus();
			return this.cancelEvent(e);
		},
		
		StartRunning:function(newState){
			if(this._running != 0)
				this.CancelRunning();
			this._running = newState;
			this.DoChange(this._increment);
			this._timeStart = new Date().getTime();
			var me=this;
			this._interval = setInterval(function(){me.Run();},150);
		},
		
		DoChange:function(inc){
			 var newVal = this._currentValue + inc * this._running;
			this.UpdateCurrentValue(newVal);
		},
		onTextboxChange:function(e){
			var val = parseFloat(this._textBox.value);
			if(isNaN(val))
				val = this._currentValue;
			if(val <this._minimumVal)
				val = this._minimumVal;
			if(val > this._maximumVal)
				val = this._maximumVal;
			this.UpdateCurrentValue(val);
		},
		
		onTextboxFocus:function(e){
			var Event=YAHOO.util.Event;
			Event.addListener(window, 'DOMMouseScroll', this.MouseWheel,this,true);
			Event.addListener(document, 'mousewheel', this.MouseWheel,this,true);
			 return this.cancelEvent(e);
		},
		
		onTextboxBlur:function(e){
			var Event=YAHOO.util.Event;
			Event.removeListener(window, 'DOMMouseScroll', this.MouseWheel);
			Event.removeListener(document, 'mousewheel', this.MouseWheel);
			return this.cancelEvent(e);
		},
		
		UpdateCurrentValue:function(newVal){
			if(newVal <this._minimumVal)
				newVal = this._maximumVal;
			if(newVal > this._maximumVal)
				newVal = this._minimumVal;
  
			newVal = Math.round(1000*newVal)/1000;
			this._textBox.value = newVal;
			if(newVal == this._currentValue)
				return;
			this._currentValue = newVal;
			    for(var i=0; i<this._callbackArray.length; i++)
				this._callbackArray[i](this, this._currentValue);
		},
		
		MouseWheel:function(e){
			e = e ||window.event;
			var movement = e.detail ? e.detail / -3 : e.wheelDelta/120;
			this.UpdateCurrentValue(this._currentValue +  movement);
			return this.cancelEvent(e);
		},
		
		cancelEvent:function(e)
		{
			e=e||window.event;
			if(e.stopPropagation)
				e.stopPropagation();
			if(e.preventDefault)
				e.preventDefault();
			e.cancelBubble = true;
			e.cancel = true;
			e.returnValue = false;
			return false;
		},
		
		getCurrentVal:function(){
			return this._currentValue;
		},
		setCurrentVal:function(val){
			this.UpdateCurrentValue(val);
		}
  };
  YAHOO.SINOSOFT.widget.SpinWidget=SpinWidget;
 })();
YAHOO.register("SpinWidget", YAHOO.SINOSOFT.widget.SpinWidget, {version: "2.8.0r4", build: "2449"});