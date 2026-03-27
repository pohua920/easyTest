/*
Copyright (c) 2007, Yahoo! Inc. All rights reserved.
Code licensed under the BSD License:
http://developer.yahoo.net/yui/license.txt
version: 2.3.0
*/

YAHOO.util.Anim=function(el,attributes,duration,method){if(!el){}
this.init(el,attributes,duration,method);};YAHOO.util.Anim.prototype={toString:function(){var el=this.getEl();var id=el.id||el.tagName||el;return("Anim "+id);},patterns:{noNegatives:/width|height|opacity|padding/i,offsetAttribute:/^((width|height)|(top|left))$/,defaultUnit:/width|height|top$|bottom$|left$|right$/i,offsetUnit:/\d+(em|%|en|ex|pt|in|cm|mm|pc)$/i},doMethod:function(attr,start,end){return this.method(this.currentFrame,start,end-start,this.totalFrames);},setAttribute:function(attr,val,unit){if(this.patterns.noNegatives.test(attr)){val=(val>0)?val:0;}
YAHOO.util.Dom.setStyle(this.getEl(),attr,val+unit);},getAttribute:function(attr){var el=this.getEl();var val=YAHOO.util.Dom.getStyle(el,attr);if(val!=='auto'&&!this.patterns.offsetUnit.test(val)){return parseFloat(val);}
var a=this.patterns.offsetAttribute.exec(attr)||[];var pos=!!(a[3]);var box=!!(a[2]);if(box||(YAHOO.util.Dom.getStyle(el,'position')=='absolute'&&pos)){val=el['offset'+a[0].charAt(0).toUpperCase()+a[0].substr(1)];}else{val=0;}
return val;},getDefaultUnit:function(attr){if(this.patterns.defaultUnit.test(attr)){return'px';}
return'';},setRuntimeAttribute:function(attr){var start;var end;var attributes=this.attributes;this.runtimeAttributes[attr]={};var isset=function(prop){return(typeof prop!=='undefined');};if(!isset(attributes[attr]['to'])&&!isset(attributes[attr]['by'])){return false;}
start=(isset(attributes[attr]['from']))?attributes[attr]['from']:this.getAttribute(attr);if(isset(attributes[attr]['to'])){end=attributes[attr]['to'];}else if(isset(attributes[attr]['by'])){if(start.constructor==Array){end=[];for(var i=0,len=start.length;i<len;++i){end[i]=start[i]+attributes[attr]['by'][i]*1;}}else{end=start+attributes[attr]['by']*1;}}
this.runtimeAttributes[attr].start=start;this.runtimeAttributes[attr].end=end;this.runtimeAttributes[attr].unit=(isset(attributes[attr].unit))?attributes[attr]['unit']:this.getDefaultUnit(attr);return true;},init:function(el,attributes,duration,method){var isAnimated=false;var startTime=null;var actualFrames=0;el=YAHOO.util.Dom.get(el);this.attributes=attributes||{};this.duration=!YAHOO.lang.isUndefined(duration)?duration:1;this.method=method||YAHOO.util.Easing.easeNone;this.useSeconds=true;this.currentFrame=0;this.totalFrames=YAHOO.util.AnimMgr.fps;this.setEl=function(element){el=YAHOO.util.Dom.get(element);};this.getEl=function(){return el;};this.isAnimated=function(){return isAnimated;};this.getStartTime=function(){return startTime;};this.runtimeAttributes={};this.animate=function(){if(this.isAnimated()){return false;}
this.currentFrame=0;this.totalFrames=(this.useSeconds)?Math.ceil(YAHOO.util.AnimMgr.fps*this.duration):this.duration;if(this.duration===0&&this.useSeconds){this.totalFrames=1;}
YAHOO.util.AnimMgr.registerElement(this);return true;};this.stop=function(finish){if(finish){this.currentFrame=this.totalFrames;this._onTween.fire();}
YAHOO.util.AnimMgr.stop(this);};var onStart=function(){this.onStart.fire();this.runtimeAttributes={};for(var attr in this.attributes){this.setRuntimeAttribute(attr);}
isAnimated=true;actualFrames=0;startTime=new Date();};var onTween=function(){var data={duration:new Date()-this.getStartTime(),currentFrame:this.currentFrame};data.toString=function(){return('duration: '+data.duration+', currentFrame: '+data.currentFrame);};this.onTween.fire(data);var runtimeAttributes=this.runtimeAttributes;for(var attr in runtimeAttributes){this.setAttribute(attr,this.doMethod(attr,runtimeAttributes[attr].start,runtimeAttributes[attr].end),runtimeAttributes[attr].unit);}
actualFrames+=1;};var onComplete=function(){var actual_duration=(new Date()-startTime)/1000;var data={duration:actual_duration,frames:actualFrames,fps:actualFrames/actual_duration};data.toString=function(){return('duration: '+data.duration+', frames: '+data.frames+', fps: '+data.fps);};isAnimated=false;actualFrames=0;this.onComplete.fire(data);};this._onStart=new YAHOO.util.CustomEvent('_start',this,true);this.onStart=new YAHOO.util.CustomEvent('start',this);this.onTween=new YAHOO.util.CustomEvent('tween',this);this._onTween=new YAHOO.util.CustomEvent('_tween',this,true);this.onComplete=new YAHOO.util.CustomEvent('complete',this);this._onComplete=new YAHOO.util.CustomEvent('_complete',this,true);this._onStart.subscribe(onStart);this._onTween.subscribe(onTween);this._onComplete.subscribe(onComplete);}};YAHOO.util.AnimMgr=new function(){var thread=null;var queue=[];var tweenCount=0;this.fps=1000;this.delay=1;this.registerElement=function(tween){queue[queue.length]=tween;tweenCount+=1;tween._onStart.fire();this.start();};this.unRegister=function(tween,index){tween._onComplete.fire();index=index||getIndex(tween);if(index==-1){return false;}
queue.splice(index,1);tweenCount-=1;if(tweenCount<=0){this.stop();}
return true;};this.start=function(){if(thread===null){thread=setInterval(this.run,this.delay);}};this.stop=function(tween){if(!tween){clearInterval(thread);for(var i=0,len=queue.length;i<len;++i){if(queue[0].isAnimated()){this.unRegister(queue[0],0);}}
queue=[];thread=null;tweenCount=0;}
else{this.unRegister(tween);}};this.run=function(){for(var i=0,len=queue.length;i<len;++i){var tween=queue[i];if(!tween||!tween.isAnimated()){continue;}
if(tween.currentFrame<tween.totalFrames||tween.totalFrames===null)
{tween.currentFrame+=1;if(tween.useSeconds){correctFrame(tween);}
tween._onTween.fire();}
else{YAHOO.util.AnimMgr.stop(tween,i);}}};var getIndex=function(anim){for(var i=0,len=queue.length;i<len;++i){if(queue[i]==anim){return i;}}
return-1;};var correctFrame=function(tween){var frames=tween.totalFrames;var frame=tween.currentFrame;var expected=(tween.currentFrame*tween.duration*1000/tween.totalFrames);var elapsed=(new Date()-tween.getStartTime());var tweak=0;if(elapsed<tween.duration*1000){tweak=Math.round((elapsed/expected-1)*tween.currentFrame);}else{tweak=frames-(frame+1);}
if(tweak>0&&isFinite(tweak)){if(tween.currentFrame+tweak>=frames){tweak=frames-(frame+1);}
tween.currentFrame+=tweak;}};};YAHOO.util.Bezier=new function(){this.getPosition=function(points,t){var n=points.length;var tmp=[];for(var i=0;i<n;++i){tmp[i]=[points[i][0],points[i][1]];}
for(var j=1;j<n;++j){for(i=0;i<n-j;++i){tmp[i][0]=(1-t)*tmp[i][0]+t*tmp[parseInt(i+1,10)][0];tmp[i][1]=(1-t)*tmp[i][1]+t*tmp[parseInt(i+1,10)][1];}}
return[tmp[0][0],tmp[0][1]];};};(function(){YAHOO.util.ColorAnim=function(el,attributes,duration,method){YAHOO.util.ColorAnim.superclass.constructor.call(this,el,attributes,duration,method);};YAHOO.extend(YAHOO.util.ColorAnim,YAHOO.util.Anim);var Y=YAHOO.util;var superclass=Y.ColorAnim.superclass;var proto=Y.ColorAnim.prototype;proto.toString=function(){var el=this.getEl();var id=el.id||el.tagName;return("ColorAnim "+id);};proto.patterns.color=/color$/i;proto.patterns.rgb=/^rgb\(([0-9]+)\s*,\s*([0-9]+)\s*,\s*([0-9]+)\)$/i;proto.patterns.hex=/^#?([0-9A-F]{2})([0-9A-F]{2})([0-9A-F]{2})$/i;proto.patterns.hex3=/^#?([0-9A-F]{1})([0-9A-F]{1})([0-9A-F]{1})$/i;proto.patterns.transparent=/^transparent|rgba\(0, 0, 0, 0\)$/;proto.parseColor=function(s){if(s.length==3){return s;}
var c=this.patterns.hex.exec(s);if(c&&c.length==4){return[parseInt(c[1],16),parseInt(c[2],16),parseInt(c[3],16)];}
c=this.patterns.rgb.exec(s);if(c&&c.length==4){return[parseInt(c[1],10),parseInt(c[2],10),parseInt(c[3],10)];}
c=this.patterns.hex3.exec(s);if(c&&c.length==4){return[parseInt(c[1]+c[1],16),parseInt(c[2]+c[2],16),parseInt(c[3]+c[3],16)];}
return null;};proto.getAttribute=function(attr){var el=this.getEl();if(this.patterns.color.test(attr)){var val=YAHOO.util.Dom.getStyle(el,attr);if(this.patterns.transparent.test(val)){var parent=el.parentNode;val=Y.Dom.getStyle(parent,attr);while(parent&&this.patterns.transparent.test(val)){parent=parent.parentNode;val=Y.Dom.getStyle(parent,attr);if(parent.tagName.toUpperCase()=='HTML'){val='#fff';}}}}else{val=superclass.getAttribute.call(this,attr);}
return val;};proto.doMethod=function(attr,start,end){var val;if(this.patterns.color.test(attr)){val=[];for(var i=0,len=start.length;i<len;++i){val[i]=superclass.doMethod.call(this,attr,start[i],end[i]);}
val='rgb('+Math.floor(val[0])+','+Math.floor(val[1])+','+Math.floor(val[2])+')';}
else{val=superclass.doMethod.call(this,attr,start,end);}
return val;};proto.setRuntimeAttribute=function(attr){superclass.setRuntimeAttribute.call(this,attr);if(this.patterns.color.test(attr)){var attributes=this.attributes;var start=this.parseColor(this.runtimeAttributes[attr].start);var end=this.parseColor(this.runtimeAttributes[attr].end);if(typeof attributes[attr]['to']==='undefined'&&typeof attributes[attr]['by']!=='undefined'){end=this.parseColor(attributes[attr].by);for(var i=0,len=start.length;i<len;++i){end[i]=start[i]+end[i];}}
this.runtimeAttributes[attr].start=start;this.runtimeAttributes[attr].end=end;}};})();YAHOO.util.Easing={easeNone:function(t,b,c,d){return c*t/d+b;},easeIn:function(t,b,c,d){return c*(t/=d)*t+b;},easeOut:function(t,b,c,d){return-c*(t/=d)*(t-2)+b;},easeBoth:function(t,b,c,d){if((t/=d/2)<1){return c/2*t*t+b;}
return-c/2*((--t)*(t-2)-1)+b;},easeInStrong:function(t,b,c,d){return c*(t/=d)*t*t*t+b;},easeOutStrong:function(t,b,c,d){return-c*((t=t/d-1)*t*t*t-1)+b;},easeBothStrong:function(t,b,c,d){if((t/=d/2)<1){return c/2*t*t*t*t+b;}
return-c/2*((t-=2)*t*t*t-2)+b;},elasticIn:function(t,b,c,d,a,p){if(t==0){return b;}
if((t/=d)==1){return b+c;}
if(!p){p=d*.3;}
if(!a||a<Math.abs(c)){a=c;var s=p/4;}
else{var s=p/(2*Math.PI)*Math.asin(c/a);}
return-(a*Math.pow(2,10*(t-=1))*Math.sin((t*d-s)*(2*Math.PI)/p))+b;},elasticOut:function(t,b,c,d,a,p){if(t==0){return b;}
if((t/=d)==1){return b+c;}
if(!p){p=d*.3;}
if(!a||a<Math.abs(c)){a=c;var s=p/4;}
else{var s=p/(2*Math.PI)*Math.asin(c/a);}
return a*Math.pow(2,-10*t)*Math.sin((t*d-s)*(2*Math.PI)/p)+c+b;},elasticBoth:function(t,b,c,d,a,p){if(t==0){return b;}
if((t/=d/2)==2){return b+c;}
if(!p){p=d*(.3*1.5);}
if(!a||a<Math.abs(c)){a=c;var s=p/4;}
else{var s=p/(2*Math.PI)*Math.asin(c/a);}
if(t<1){return-.5*(a*Math.pow(2,10*(t-=1))*Math.sin((t*d-s)*(2*Math.PI)/p))+b;}
return a*Math.pow(2,-10*(t-=1))*Math.sin((t*d-s)*(2*Math.PI)/p)*.5+c+b;},backIn:function(t,b,c,d,s){if(typeof s=='undefined'){s=1.70158;}
return c*(t/=d)*t*((s+1)*t-s)+b;},backOut:function(t,b,c,d,s){if(typeof s=='undefined'){s=1.70158;}
return c*((t=t/d-1)*t*((s+1)*t+s)+1)+b;},backBoth:function(t,b,c,d,s){if(typeof s=='undefined'){s=1.70158;}
if((t/=d/2)<1){return c/2*(t*t*(((s*=(1.525))+1)*t-s))+b;}
return c/2*((t-=2)*t*(((s*=(1.525))+1)*t+s)+2)+b;},bounceIn:function(t,b,c,d){return c-YAHOO.util.Easing.bounceOut(d-t,0,c,d)+b;},bounceOut:function(t,b,c,d){if((t/=d)<(1/2.75)){return c*(7.5625*t*t)+b;}else if(t<(2/2.75)){return c*(7.5625*(t-=(1.5/2.75))*t+.75)+b;}else if(t<(2.5/2.75)){return c*(7.5625*(t-=(2.25/2.75))*t+.9375)+b;}
return c*(7.5625*(t-=(2.625/2.75))*t+.984375)+b;},bounceBoth:function(t,b,c,d){if(t<d/2){return YAHOO.util.Easing.bounceIn(t*2,0,c,d)*.5+b;}
return YAHOO.util.Easing.bounceOut(t*2-d,0,c,d)*.5+c*.5+b;}};(function(){YAHOO.util.Motion=function(el,attributes,duration,method){if(el){YAHOO.util.Motion.superclass.constructor.call(this,el,attributes,duration,method);}};YAHOO.extend(YAHOO.util.Motion,YAHOO.util.ColorAnim);var Y=YAHOO.util;var superclass=Y.Motion.superclass;var proto=Y.Motion.prototype;proto.toString=function(){var el=this.getEl();var id=el.id||el.tagName;return("Motion "+id);};proto.patterns.points=/^points$/i;proto.setAttribute=function(attr,val,unit){if(this.patterns.points.test(attr)){unit=unit||'px';superclass.setAttribute.call(this,'left',val[0],unit);superclass.setAttribute.call(this,'top',val[1],unit);}else{superclass.setAttribute.call(this,attr,val,unit);}};proto.getAttribute=function(attr){if(this.patterns.points.test(attr)){var val=[superclass.getAttribute.call(this,'left'),superclass.getAttribute.call(this,'top')];}else{val=superclass.getAttribute.call(this,attr);}
return val;};proto.doMethod=function(attr,start,end){var val=null;if(this.patterns.points.test(attr)){var t=this.method(this.currentFrame,0,100,this.totalFrames)/100;val=Y.Bezier.getPosition(this.runtimeAttributes[attr],t);}else{val=superclass.doMethod.call(this,attr,start,end);}
return val;};proto.setRuntimeAttribute=function(attr){if(this.patterns.points.test(attr)){var el=this.getEl();var attributes=this.attributes;var start;var control=attributes['points']['control']||[];var end;var i,len;if(control.length>0&&!(control[0]instanceof Array)){control=[control];}else{var tmp=[];for(i=0,len=control.length;i<len;++i){tmp[i]=control[i];}
control=tmp;}
if(Y.Dom.getStyle(el,'position')=='static'){Y.Dom.setStyle(el,'position','relative');}
if(isset(attributes['points']['from'])){Y.Dom.setXY(el,attributes['points']['from']);}
else{Y.Dom.setXY(el,Y.Dom.getXY(el));}
start=this.getAttribute('points');if(isset(attributes['points']['to'])){end=translateValues.call(this,attributes['points']['to'],start);var pageXY=Y.Dom.getXY(this.getEl());for(i=0,len=control.length;i<len;++i){control[i]=translateValues.call(this,control[i],start);}}else if(isset(attributes['points']['by'])){end=[start[0]+attributes['points']['by'][0],start[1]+attributes['points']['by'][1]];for(i=0,len=control.length;i<len;++i){control[i]=[start[0]+control[i][0],start[1]+control[i][1]];}}
this.runtimeAttributes[attr]=[start];if(control.length>0){this.runtimeAttributes[attr]=this.runtimeAttributes[attr].concat(control);}
this.runtimeAttributes[attr][this.runtimeAttributes[attr].length]=end;}
else{superclass.setRuntimeAttribute.call(this,attr);}};var translateValues=function(val,start){var pageXY=Y.Dom.getXY(this.getEl());val=[val[0]-pageXY[0]+start[0],val[1]-pageXY[1]+start[1]];return val;};var isset=function(prop){return(typeof prop!=='undefined');};})();(function(){YAHOO.util.Scroll=function(el,attributes,duration,method){if(el){YAHOO.util.Scroll.superclass.constructor.call(this,el,attributes,duration,method);}};YAHOO.extend(YAHOO.util.Scroll,YAHOO.util.ColorAnim);var Y=YAHOO.util;var superclass=Y.Scroll.superclass;var proto=Y.Scroll.prototype;proto.toString=function(){var el=this.getEl();var id=el.id||el.tagName;return("Scroll "+id);};proto.doMethod=function(attr,start,end){var val=null;if(attr=='scroll'){val=[this.method(this.currentFrame,start[0],end[0]-start[0],this.totalFrames),this.method(this.currentFrame,start[1],end[1]-start[1],this.totalFrames)];}else{val=superclass.doMethod.call(this,attr,start,end);}
return val;};proto.getAttribute=function(attr){var val=null;var el=this.getEl();if(attr=='scroll'){val=[el.scrollLeft,el.scrollTop];}else{val=superclass.getAttribute.call(this,attr);}
return val;};proto.setAttribute=function(attr,val,unit){var el=this.getEl();if(attr=='scroll'){el.scrollLeft=val[0];el.scrollTop=val[1];}else{superclass.setAttribute.call(this,attr,val,unit);}};})();YAHOO.register("animation",YAHOO.util.Anim,{version:"2.3.0",build:"442"});
/**
 * @author user
 */
			YAHOO.namespace("PICC");

			YAHOO.PICC.DataSource = function(aData, oConfigs){
                  
				if(arguments.length>0){
                     
				    // Set any config params passed in to override defaults
				    if(oConfigs && (oConfigs.constructor == Object)) {
				        for(var sConfig in oConfigs) {
				            this[sConfig] = oConfigs[sConfig];
				        }
				    }

				    // Initialization sequence
				    if(!YAHOO.lang.isArray(aData)) {
				        YAHOO.log("Could not instantiate JSArray DataSource due to invalid arguments", "error", this.toString());
				        return;
				    }
				    else {
						//adjust dataset type
						var _dataset = [];
						var _keys = [];

						if(YAHOO.lang.isObject(oConfigs)){


							if(!YAHOO.lang.isArray(aData)){
								aData[0] = aData;
							}

							if(!YAHOO.lang.isArray(oConfigs.keys)){
								_keys[0] = oConfigs.keys;
							}else{
								_keys = oConfigs.keys;
							}
							//
							for(var i = 0 ; i < aData.length ; i++ ){
								var queryStr = "| ";

								var _rowset = [];
								for(var j = 0 ; j<_keys.length ; j++){
									queryStr = queryStr + aData[i][ _keys[j] ] + " | ";
								}
								_rowset[0] = queryStr;
								_rowset[1] = aData[i][ oConfigs.primarykey];
								_rowset[2] = aData[i][ oConfigs.parentkey];
								_rowset[3] = aData[i][ oConfigs.display];
								_rowset[4] = aData[i][ oConfigs.hidden];
								_rowset[5] = aData[i][ oConfigs.tooltip];
								_rowset[6] = aData[i];//保存对象
								_dataset[i]= _rowset;
							}
						}
//						YAHOO.PICC.DataSource.superclass.constructor.call(this,_dataset,oConfigs);
				        this.data = _dataset;
				        this._init();
				        YAHOO.log("JS Array DataSource initialized","info",this.toString());
				    }

					this.queryMatchContains = true;
					this.queryMatchSubset = true;

				}
			};

			YAHOO.PICC.DataSource.prototype = new YAHOO.widget.DS_JSArray();
//			YAHOO.lang.extend(YAHOO.PICC.DataSource,YAHOO.widget.DS_JSArray);

			YAHOO.PICC.selectUI = function(input, container, hidden, type, dataset, oConfig){
				if(arguments.length>0){
					this._oConfigs = oConfig;
					//constructs autoComplete.
					YAHOO.PICC.selectUI.superclass.constructor.call(this,input,container,dataset,oConfig);
					//set hidden obj.
					if(YAHOO.lang.isString(hidden)){

						this.hidden = document.getElementById(hidden);
						

					}else{

						this.hidden = hidden;
                        
					}
					this.type = type;

					//防止错误录入修改界面.
			        YAHOO.util.Event.addListener(this._oTextbox,"keydown",this.cleanHidden,this);
			        //special setting for onfocus show all result when dataset in local.
			        //YAHOO.util.Event.addListener(this._oTextbox,"focus",this.showAllItemLocal,this);

					//if label text is not save in database, system fill the information automaticly
					this.initInputValue(this);
					this.hidden.parentObj=this;
					//YAHOO.util.Event.addListener(this.hidden, "propertychange", this._onHiddenChange, this);		//for IE

				}
				//all added function.
				/*
				this.itemArrowToEvent.subscribe(
					function(sType,args){
						var oAC = args[0];
						var itemObj = args[1];
						var itemData = itemObj._oResultData;

						if(YAHOO.lang.isFunction(oAC.onsel)){
							oAC.onsel(oAC,itemData);
						}else{
							oAC._oTextbox.value = itemData[3];
							oAC._oTextbox.select();
							//oAC._stopEvent = true;
							this.hidden.value = itemData[1];
							//oAC._stopEvent = false;
						}
					}
				);*/
				this.textboxKeyEvent.subscribe(
					function(sType,args){
						var oAC = args[0];
						var nKeyCode = args[1];
						if(!oAC._isIgnoreKey(nKeyCode)){
							oAC.hidden.value='';
						}
					}
				);
				this.itemSelectEvent.subscribe(
					function(sType,args){
					
						var oAC = args[0];
						var itemObj = args[1];
						var itemData = args[2];
						if(YAHOO.lang.isFunction(oAC.onsel)){
							oAC._oTextbox.value = itemData[3];
							oAC.onsel(oAC,itemData);
							
						}else{
							oAC._oTextbox.value = itemData[3];
							oAC._oTextbox.select();
							this.hidden.value = itemData[1];
						
						}
					}
				);
				
				this.textboxBlurEvent.subscribe(
					function(sType,args){
						var oAC = args[0];
						oAC._toggleTips('',false);this.checkInputValue(this);
						if(!oAC._bItemSelected){
							if(YAHOO.lang.isNull(oAC._oTextbox.value) || oAC._oTextbox.value == ''){
								oAC.hidden.value='';
							}
							if(this.forcefill && (YAHOO.lang.isNull(oAC.hidden.value)|| oAC.hidden.value == '') ){
								oAC._oTextbox.value = '';
							}
						}
						//to let onfocus show all list.
						oAC._bItemSelected = false;
					}
				);

				this.textboxFocusEvent.subscribe(
					function(sType,args){
						var oAC = args[0];
						var itemObj = args[1];
						var itemData = args[2];
						var sText = oAC._oTextbox.value;

						//orgi text when focus. if not change, remote search will not execute.
						this.oldQuery = sText;
						oAC._oTextbox.select();
						//alert(itemObj);
						//if search from remote, need show tips.
						if(!YAHOO.lang.isUndefined(oAC.dwrfname)){
							this._toggleTips(this.inputtip,true);
						}else{
							oAC.sendQuery(sText);
						}
						/*
					    // Set timeout on the request
					    if(oAC.queryDelay > 0) {
					        var nDelayID =
					            setTimeout(function(){oAC.sendQuery(sText);},(oAC.queryDelay * 1000));
					        if(oAC._nDelayID != -1) {
					            clearTimeout(oAC._nDelayID);
					        }

					        oAC._nDelayID = nDelayID;
					    }
					    else {
					        // No delay so send request immediately
					        oAC.sendQuery(sText);
					    }*/
					}
				);
			};

			YAHOO.lang.extend(YAHOO.PICC.selectUI,YAHOO.widget.AutoComplete);

YAHOO.PICC.selectUI.prototype._stopEvent = false;


YAHOO.PICC.selectUI.prototype._onTextboxFocus = function (v,oSelf) {
    oSelf._oTextbox.setAttribute("autocomplete","off");
	//oSelf.textboxFocusEvent.fire(oSelf);
	if(oSelf._oTextbox.readOnly == true){
		return;
	}
	if (!oSelf._bFocused){
		oSelf._bFocused = true;
		oSelf._oTextbox.select();

		if(YAHOO.lang.isUndefined(oSelf.dwrfname)){
			//oSelf._oTextbox.value="";
			oSelf._sendQuery("");
		}else{
			oSelf._toggleTips(oSelf.inputtip,true);
		}
	}else{
		if(!oSelf._bItemSelected) {
        	if(YAHOO.lang.isUndefined(oSelf.dwrfname)){
				oSelf._sendQuery("");
			}else{
				oSelf._toggleTips(oSelf.inputtip,true);
			}
    	}

	}
    if(!oSelf._bItemSelected) {
        //oSelf.textboxFocusEvent.fire(oSelf);
    }
};
YAHOO.PICC.selectUI.prototype._onHiddenChange = function(v,oSelf){
	if(oSelf._stopEvent) return;
	if (v.type == "propertychange" && v.propertyName == "value"		//for IE
		|| v.type == "input" ){		//for Firefox
		//oSelf._stopEvent=true;
		//oSelf.hidden.value="";
		//oSelf._stopEvent=false;
		oSelf.initInputValue(oSelf);
	}
}

YAHOO.PICC.selectUI.prototype.checkInputValue = function(oAC){
	// if(oAC.dwrfname!=undefined && oAC.dwrfname!='') return;
	var elInput  = oAC._oTextbox;
	var elHidden = oAC.hidden;
	var dset = oAC.dataSource.data;
	
	if (dset == undefined || dset == null) return;
	
	for(var i=0 ; i<dset.length ;i++){
		var arecord = dset[i];
		var sid = arecord[1];
		var inputValue = trim(elInput.value);
		//alert(arecord[3]+"---"+elInput.value);
		if( trim(sid) == inputValue || trim(arecord[3]) == inputValue){
			elHidden.value = arecord[1];
			elInput.value = arecord[3];
			break;
		}
	}
}

YAHOO.PICC.selectUI.prototype.changeInputValue = function(oAC){
	//alert("aac");
	if(oAC.dwrfname!=undefined && oAC.dwrfname!='') return;
	var elInput  = oAC._oTextbox;
	var elHidden = oAC.hidden;
	if(elHidden.value != undefined && elHidden.value != '' && oAC.dataSource != undefined && oAC.dataSource.data != undefined){
		//alert("aab");
		var dset = oAC.dataSource.data;
			if(dset){
			//	alert(dset.length);
				for(var i=0 ; i<dset.length ;i++){
					
				//	alert(dset[i]);
					var arecord = dset[i];
					var sid = arecord[1];
					//alert(arecord[0]);
				//	alert(arecord[1]);
				//	alert(arecord[2]);
				//	alert(arecord[3]);
				//	alert(arecord[4]);
					if(sid == elHidden.value){
						elInput.value = arecord[3];
						break;
					}
				}
			}
	}
};
function trim(str) {
	return str.replace(/^\s*(.*?)[\s\n]*$/g, '$1');
}
			//本地数据时候，当没有记录字典label的时候，需要显示label。
			YAHOO.PICC.selectUI.prototype.initInputValue = function(oAC){
				if(oAC.dwrfname!=undefined && oAC.dwrfname!='') return;
				var elInput  = oAC._oTextbox;
				var elHidden = oAC.hidden;
				//alert(elInput+"-"+elHidden);
				//if( YAHOO.lang.isUndefined(elInput.value) /*|| elInput.value == ''*/){
					if(elHidden!=null && elHidden.value != undefined && elHidden.value != '' && oAC.dataSource != undefined && oAC.dataSource.data != undefined){
						var dset = oAC.dataSource.data;
						if(dset){
							for(var i=0 ; i<dset.length ;i++){
								var arecord = dset[i];
								var sid = arecord[1];
								if(trim(sid) == trim(elHidden.value)){
									elInput.value = arecord[3];
									break;
								}
							}
						}
					}
				//}else{
				//	elInput.value = "";
				//}
			};


			/**
			 * tip information collapse.
			 * @param {Object} sTips
			 * @param {Object} bShow
			 */
			YAHOO.PICC.selectUI.prototype._toggleTips = function(sTips,bShow){
				var oContainer = this._oContainer;
			    var oAnim = this._oAnim;
//				bShow=true;
//				debugger;

			    if(oAnim && oAnim.getEl()) {

			        if(oAnim.isAnimated()) {
			            oAnim.stop();
			        }
					//debugger;
					if(!this.oTip){
						var regCon = YAHOO.util.Dom.getRegion(oContainer);
						this.oTip = document.createElement("div");
						YAHOO.util.Dom.addClass(this.oTip,"yui-ac-tip");
						var ow = regCon.right - regCon.left;
						YAHOO.util.Dom.setStyle(this.oTip,"width",ow+"px");
						YAHOO.util.Dom.setStyle(this.oTip,"display","none");
//						YAHOO.util.Dom.setStyle(this.oTip,"background-color","red");
						var pn = oContainer.parentNode;
						//pn.insertBefore(this.oTip,this._oTextbox.nextSibling);
						pn.insertBefore(this.oTip,this._oTextbox);
						//notice pad
						var oTippad = document.createElement("div");
//						oTippad.style.width = "100%";
//						oTippad.style.height = "100%";
						oTippad.style.position = "absolute";
						oTippad.style.backgroundColor = "#ffffff";
						this.oTip.oPad = this.oTip.appendChild(oTippad);

						//layer iframe
				        var oIFrame = document.createElement("iframe");
				        oIFrame.src = "javascript:false";
				        oIFrame.frameBorder = 0;
				        oIFrame.scrolling = "no";
				        oIFrame.style.position = "relative";
				        oIFrame.style.width = "0";
				        oIFrame.style.height = "0";
				        oIFrame.tabIndex = -1;
						oIFrame.style.zIndex=-1;
						this.oTip.oFrame = this.oTip.appendChild(oIFrame);

//						this.oTip.appendChild(oIFrame);
					}
					var regInput = YAHOO.util.Dom.getRegion(this._oTextbox);
					var regTop = regInput.bottom - regInput.top;
//					YAHOO.util.Dom.setStyle(this.oTip,"left",regInput.left);
//					YAHOO.util.Dom.setStyle(this.oTip,"top",regInput.bottom);
					YAHOO.util.Dom.setStyle(this.oTip,"left",0);
					YAHOO.util.Dom.setStyle(this.oTip,"top",regTop+"px");

					if(bShow){
//						this.oTip.innerHTML = "<textarea color=red>"+sTips+"</textarea>";
						this.oTip.oPad.innerHTML = sTips;
						YAHOO.util.Dom.setStyle(this.oTip,"display","block");
						var oh = this.oTip.oPad.offsetHeight;
						var ow = this.oTip.oPad.offsetWidth;

						this.oTip.style.height = oh+"px";
//						this.oTip.style.width = ow+"px";

						this.oTip.oFrame.style.height = oh+"px";
						this.oTip.oFrame.style.width = ow+"px";


					}else{
						YAHOO.util.Dom.setStyle(this.oTip,"display","none");
					}
				}
			}


			/**
			 * show the tip message
			 * @param {Object} bShow
			 * @param {Object} sTips
			 */

			YAHOO.PICC.selectUI.prototype._onTextboxKeyUp = function(v,oSelf) {
			    // Check to see if any of the public properties have been updated
			    oSelf._initProps();
			    oSelf._oTextbox.title="";
			    var nKeyCode = v.keyCode;
			    oSelf._nKeyCode = nKeyCode;
			    var sText = this.value; //string in textbox
				if(!oSelf._bContainerOpen && (nKeyCode == 40) ){//向下的方向键
					if(YAHOO.lang.isUndefined(oSelf.dwrfname)){
						oSelf._sendQuery("");
					}
				}
			    // Filter out chars that don't trigger queries
			    if(oSelf._isIgnoreKey(nKeyCode) || (sText.toLowerCase() == oSelf._sCurQuery)) {
			        return;
			    }
			    else {
			        oSelf._bItemSelected = false;
			        YAHOO.util.Dom.removeClass(oSelf._oCurItem,  oSelf.highlightClassName);
			        oSelf._oCurItem = null;
			        oSelf.textboxKeyEvent.fire(oSelf, nKeyCode);
			    }

			    // Set timeout on the request
			    if(oSelf.queryDelay > 0) {
			        var nDelayID =
			            setTimeout(function(){oSelf.sendQuery(sText);},(oSelf.queryDelay * 1000));

			        if(oSelf._nDelayID != -1) {
			            clearTimeout(oSelf._nDelayID);
			        }

			        oSelf._nDelayID = nDelayID;
			    }
			    else {
			        // No delay so send request immediately
			        oSelf.sendQuery(sText);
			    }
			};

			YAHOO.PICC.selectUI.prototype.numPar=/\d{1,}$/;
			YAHOO.PICC.selectUI.prototype.sendQuery = function(sQuery){
				if(this.numPar.test(sQuery)){
					sQuery = "| " + sQuery;
				}
				if(YAHOO.lang.isUndefined(this.dwrfname)){
					this._sendQuery(sQuery);
				}else{
					if(/*this.oldQuery == sQuery || */YAHOO.lang.isNull(sQuery) || sQuery == '' ){
						return;
					}

					//显示等待查询提示
					this._toggleTips(this.waittip,true);
					
					// 清空原来下来框的数据
					this.dataSource.data = [];

					this.oldQuery = sQuery;
					//参数设置
					sQuery = encodeURI(decodeURIComponent(sQuery).toLowerCase());
					var arg = sQuery;
					var oAC = this;
					if(this.dwrfarg != 'undefined' && this.dwrfarg != ''){
						arg = this.dwrfarg(this._oTextbox);
					}
                    
                    var ar = {};
                    for(var s in arg){
                        ar[s]=arg[s];
                    }

                    try{
                        if (arg.extraCond!=undefined && arg.extraCond!=null)
                            ar.extraCond = eval(arg.extraCond);
                    }
                    catch (ex){
                        alert(ex);
                        return;
                    }


				    var responseSuccess = function(oResp) {

						results = new YAHOO.PICC.DataSource(oResp,oAC._oConfigs);
						if(!YAHOO.lang.isNull(oAC.dataSource)){
//							oAC.dataSource.destory();
						}
						oAC.dataSource = results;
//				       	for (var i=0; i<aResults.length; i++){		//转换为[表现,数据]形式
//				       		aResults[i] = [aResults[i][oData.config.display],aResults[i]];
//				       	}
						//hidden tips
						oAC._toggleTips(oAC.waittip,false);

				        oAC._populateList(sQuery, results.data , oAC);		//回调函数，进行下拉展现

				    };

				    var responseFailure = function(oResp) {	//通讯失败处理
				    	oAC._toggleTips(oAC.noresulttip,true);
				        return;
				    };

					var oCallback = { success:responseSuccess, failure:responseFailure };

					if(YAHOO.lang.isNumber(this._nTimeout) && (this._nTimeout > 0)) {	//设置超时时间
				        oCallback.timeout = oParent._nTimeout;
				    }

				    if(this._oConn) {
				        YAHOO.util.Connect.abort(this._oConn);			//若有连接进行中，则取消上次连接
				    }

					var inputField;
					var outputField;
					if(arg.typeParam!=undefined&&arg.typeParam=="DisasterParam"){
						arg.typeParam=document.getElementById("prpLdisaster.disasterTypeCode").value;
						ar.typeParam=document.getElementById("prpLdisaster.disasterTypeCode").value;
					}
					var strDwr = "dwrInvokeDataAction."+this.dwrfname+"(ar,responseSuccess,false);";
					eval(strDwr);


				}
			}

			/**
			 * 在修改界面之前,清空hidden的值.
			 * @param {Object} oSelf
			 */
			YAHOO.PICC.selectUI.prototype.cleanHidden = function(v,oSelf){
				if(!oSelf._isIgnoreKey(v.keyCode)){
					oSelf.hidden.value='';
					if(oSelf.forceSelection){
						//oSelf._oTextbox.value = '';
					}
				}
			}

			YAHOO.PICC.selectUI.prototype.formatResult = function(oResultItem, sQuery) {
				if(YAHOO.lang.isFunction(this.format)){
					return this.format(oResultItem);
				}
			}


var GlobalCodeCash={};//定义全局的本地Code缓存

//var MAX_ZINDEX = 300;
var MAX_OPTIONS_SIZE = 10;
var G_ZINDEX = 300;
var INDEX_SELECT_QUERY = 0;
var INDEX_SELECT_KEY = 1;
var INDEX_SELECT_PARENTKEY = 2;
var INDEX_SELECT_DISPLAY = 3;
var INDEX_SELECT_HIDDEN = 4;
var INDEX_SELECT_TOOLTIP = 5;
var INDEX_SELECT_OBJ = 6;
var SELECTUI_INPUT_CLASS = "selectui-input";
var SELECTUI_CONTAINER_CLASS  = "selectui-container";

var _defaultSelectFormat = function(item){
	return item[INDEX_SELECT_KEY]+"-"+item[INDEX_SELECT_TOOLTIP];
}

var _defaultLocalSelectConfig = function(sel){
	//ac.prehighlightClassName = "yui-ac-prehighlight";
	sel.typeAhead = false;
   	sel.useShadow = false;
	sel.animHoriz = false;
	sel.animVert = false;
	sel.useIFrame = true;
	sel.maxResultsDisplayed = 100;
    sel.forceSelection = false;
	sel.queryDelay = 0.5;
    sel.minQueryLength = 0;
}

var _defaultRemoteSelectConfig = function(sel,delay){
	//ac.prehighlightClassName = "yui-ac-prehighlight";
	sel.typeAhead = false;
   	sel.useShadow = false;
	sel.animHoriz = false;
	sel.animVert = false;
	sel.useIFrame = true;
	sel.maxResultsDisplayed = 30;
    sel.forceSelection = false;
	sel.queryDelay = 0.8;
    sel.minQueryLength = 0;
}

function initAllSelectUi(){
	var codes=[];
	var list = YAHOO.util.Dom.getElementsByClassName("selectui-indiv","DIV",document.body);
	//alert(list.length);
	for(var i=0;i<list.length;i++){
		var el=list[i];
		//alert(el.id);
		if(el.id!=undefined && el.id!="" && el.id.indexOf("_[")==-1){
			addSelect(list[i].id,{},codes);
		}
	}
	getAllCode(codes,'C');
}

function addSelect(divName,oCfg,codes){
	var code={};
	var childs = YAHOO.util.Dom.getElementsBy(function(e){return true;},"*",divName);
	for(var j=0;j<childs.length;j++){
		var node=childs[j];
		if(node.tagName=="DIV"){
			if(node.id=="selectUi.codeType") code.codeType=node.className;
			if(node.className.indexOf(SELECTUI_CONTAINER_CLASS)!=-1){
					code.container = (node.name == undefined) ? node.id:node.name;
			}
			if(node.className=="selectConfig"){
				var cfgs = YAHOO.util.Dom.getElementsBy(function(e){code[e.className]=e.innerHTML;return true},"div",node);
			}
		}else if(node.tagName=="INPUT"){
			if(node.className.indexOf(SELECTUI_INPUT_CLASS)!=-1) code.field = (node.name == undefined || node.name=="") ? node.id:node.name;
			if(node.type=="hidden") code.hidden = (node.name == undefined || node.name=="") ? node.id:node.name;
		}else if(node.tagName=="SELECT"){//用于替换静态下拉框
			if(node.className.indexOf(SELECTUI_INPUT_CLASS)!=-1) code.field = (node.name == undefined || node.name == "") ? node.id:node.name;
		}
	}
	//alert(code.codeType+"- "+code.field+"- "+code.container+"- "+code.hidden+"\n "+code.inputHint);
	for(var s in oCfg){
		code[s]=oCfg[s];
	}
	if(code.language==undefined) code.language="C";
	if(code.riskCode==undefined) code.riskCode="PUB";
	code.forceSelection = (code.forceSelection=="0")?false:true;
	var index;
	if(code.zIndex==undefined){
		code.zIndex = G_ZINDEX;
		G_ZINDEX--;
	}

	//alert(code.field+"--"+code.zIndex);
	if(code.codeType=="StaticSelect"){
		replaceSelect(code.field,code.zIndex);
		YAHOO.util.Dom.setStyle(divName, "z-index", code.zIndex);
	}else{
		if(divName.indexOf("[")!=-1){
			index = divName.split("[")[1].split("]")[0];
			initMutiSelect(index, code);
		}else{
			codes.push(code);
		}
	}

	return codes;
}

function disableSelectUi(){//修改为readOnly 2007.10.19
	var list = YAHOO.util.Dom.getElementsByClassName("selectui-input","INPUT",document.body);
	for(var i=0;i<list.length;i++){
		var el=list[i];
		//el.disabled=true;
		el.readOnly=true;
	}
}

function initMutiSelect(index,selectConfig){
	var pos=index - 1;
	if(selectConfig.field!=undefined){
		while(selectConfig.field.indexOf("?")>-1){
			selectConfig.field = selectConfig.field.replace("?", pos);
		}
	}
	if(selectConfig.hidden!=undefined){
		while(selectConfig.hidden.indexOf("?")>-1){
			selectConfig.hidden = selectConfig.hidden.replace("?", pos);
		}
	}else{
		alert("hidden没有设置!");
	}
	selectConfig.container = selectConfig.field+"_Container";
	if(selectConfig.codeName!=undefined){
		while(selectConfig.codeName.indexOf("?")>-1){
			selectConfig.codeName = selectConfig.codeName.replace("?", pos);
		}
	}
	var div=YAHOO.util.Dom.get(selectConfig.field).parentNode;
	if(div!=null){
		var zIndex=(selectConfig.zIndex!=undefined)?selectConfig.zIndex:G_ZINDEX-parseInt(index);
		YAHOO.util.Dom.setStyle(div,"z-index",zIndex);
	}

	var firstAttr;
	var getArg = function(){
		selectConfig.query = YAHOO.util.Dom.get(selectConfig.field).value;
		return selectConfig;//;
	}
	var onselect;
	if(selectConfig.onSelect == undefined){
		onselect = function(oAC,result){
			YAHOO.util.Dom.get(selectConfig.hidden).value=result[INDEX_SELECT_HIDDEN];
			if(selectConfig.codeName!=undefined){
				YAHOO.util.Dom.get(selectConfig.codeName).value=result[INDEX_SELECT_DISPLAY];
			}
		};
	}else{
		onselect = eval(selectConfig.onSelect);
	}
	if((selectConfig.type == undefined) || (selectConfig.type == null)){
		selectConfig.type = "firstLoad";
	}
	if(selectConfig.inputHint==undefined){
		selectConfig.inputHint="";
	}
	if(selectConfig.type=="firstLoad"){
		var codes = [];
		codes.push(selectConfig);
		getAllCode(codes,'C');
	}else{
		var oConfig = {  keys: ['code','name'],
							primarykey: 'code',
							display: 'name',
							hidden: 'code',
							tooltip: 'name',
							querytype: 'seperate',
							onsel:onselect,
							dwrfarg:getArg,
							dwrfname:'getDcode',
							inputtip: '请输入'+selectConfig.inputHint,
							noresulttip: '没有符合条件的记录! ',
							waittip: '数据查询中! ',
							format:_defaultSelectFormat
							};
		var ds = new YAHOO.PICC.DataSource([],oConfig);
		var ac = new YAHOO.PICC.selectUI(selectConfig.field,selectConfig.field+"_Container",selectConfig.hidden,'list',ds,oConfig);
   		_defaultRemoteSelectConfig(ac);
   		if(selectConfig.forceSelection!=undefined && selectConfig.forceSelection==true){
    		ac.forceSelection=true;
    	}
	}
}

function initSelect(codes){
	if(codes.onSelect == undefined){
		onselect = function(oAC,result){
			YAHOO.util.Dom.get(codes.hidden).value=result[INDEX_SELECT_HIDDEN];
			if(codes.codeName!=undefined){
				YAHOO.util.Dom.get(codes.codeName).value=result[INDEX_SELECT_DISPLAY];
			}
			try{
				YAHOO.util.Dom.get(codes.hidden).onchange();
			}catch(e){
			}
			
		};
	}else{
		onselect = eval(codes.onSelect);
	}
	var forcefill = true;
	if(codes.forceSelection!=undefined && codes.forceSelection==false){
    	forcefill = false;
    }
	var oConfig = {  keys: ['code','name'],
							primarykey: 'code',
							display: 'name',
							hidden: 'code',
							tooltip: 'name',
							querytype: 'seperate',
							onsel:onselect,
							inputtip: '',
							noresulttip: '',
							waittip: '',
							format:_defaultSelectFormat,
							forcefill:forcefill
							};

	var ds = new YAHOO.PICC.DataSource(codes.dataSet,oConfig);

	var ac = new YAHOO.PICC.selectUI(codes.field,codes.field+"_Container",codes.hidden,'list',ds,oConfig);
	_defaultLocalSelectConfig(ac);
	//if(codes.forceSelection!=undefined && codes.forceSelection==true){
    //	ac.forceSelection=true;
    //}
}
//将select替换成autocomplete
function replaceSelect(id,zIndex){
	var el=YAHOO.util.Dom.get(id);
	var data = [];
	var text,value;
	for(var i=0;i<el.options.length;i++){
		var item={name:el.options[i].text, code:el.options[i].value};
		data.push(item);
	}
	text=el.options[el.selectedIndex].text;
	value=el.options[el.selectedIndex].value;

	var selectDiv = document.createElement("DIV");
	var container = document.createElement("DIV");
	var selectInput = document.createElement("INPUT");
	var hiddenInput = document.createElement("INPUT");
	var required;
	if(el.className.indexOf("selectui-required")>=0){
		required = document.createElement("SPAN");
		required.className = "selectRequired";
		required.innerHTML = "<font color=#FF0000>&nbsp;*</font>";
		//el.className = el.className.replace(/required/,"");
		//alert(el.className)
	}
	selectDiv.id=id+"_DIV";
	selectDiv.className="selectui-indiv";
	container.id = id+"_ac_Container";
	selectDiv.appendChild(selectInput);
	selectDiv.appendChild(hiddenInput);
	selectDiv.appendChild(container);
	if(required!=undefined){
		selectDiv.appendChild(required);
	}
	selectInput.id=id+"_ac";
	//selectInput.className="selectui-input";
	selectInput.className=selectInput.className+" "+el.className;
	YAHOO.util.Dom.setStyle(hiddenInput,"display", "none");
	hiddenInput.id=el.id;
	hiddenInput.name=el.name;
	hiddenInput.onchange=el.onchange;
	var codes={field:selectInput.id, hidden:hiddenInput.id, dataSet:data, forceSelection:true};
	el.parentNode.replaceChild(selectDiv, el);
	hiddenInput.value=value;
	selectInput.value=text;
	initSelect(codes);
	YAHOO.util.Dom.setStyle(selectDiv, "z-index", zIndex);
}
addTip=function(el,msg){
	el.title = msg;
}
function initRemoteCode(codes, language){
	var inputObject,outputObject;
	
	var getArg = function(){
		var ar = {};
		codes.query = YAHOO.util.Dom.get(codes.field).value;
		ar.query = codes.query;
		ar.codeType = codes.codeType;
		ar.typeParam = codes.typeParam;
		ar.type = codes.type;
		ar.language = codes.language;
		ar.riskCode = codes.riskCode;
        ar.extraCond = codes.extraCond;
		return ar;//;
	}
	var onselect;
	if(codes.onSelect == undefined){
		onselect = function(oAC,result){
			YAHOO.util.Dom.get(codes.hidden).value=result[INDEX_SELECT_HIDDEN];
			if(codes.codeName!=undefined){
				YAHOO.util.Dom.get(codes.codeName).value=result[INDEX_SELECT_DISPLAY];
			}
			oAC._oTextbox.value=trim(oAC._oTextbox.value);
			addTip(oAC._oTextbox,result[INDEX_SELECT_TOOLTIP]);
		};
	}else{
		onselect = eval(codes.onSelect);
	}
	if(codes.inputHint==undefined){
		codes.inputHint="";
	}
	var forcefill = true;
	if(codes.forceSelection!=undefined && codes.forceSelection==false){
    	forcefill = false;
    }

	var oConfig = {  keys: ['code','name'],
							primarykey: 'code',
							display: (codes.display==undefined)?'name':codes.display,
							hidden: 'code',
							tooltip: 'name',
							querytype: 'seperate',
							onsel:onselect,
							dwrfarg:getArg,
							dwrfname:'getDcode',
							inputtip: '请输入'+codes.inputHint,
							noresulttip: '没有符合条件的记录! ',
							waittip: '数据查询中! ',
							format:_defaultSelectFormat,
							forcefill:forcefill
							};
	var data;
	var ds = new YAHOO.PICC.DataSource(data,oConfig);
	var ac = new YAHOO.PICC.selectUI(codes.field,codes.field+"_Container",codes.hidden,'list',ds,oConfig);
    _defaultRemoteSelectConfig(ac);
}

function getAllCode(codes,language){
	var inputArg = [];
	var codecfg = [];
	for(var i=0;i<codes.length;i++){
		var ro = codes[i];
		ro.language = language;
		ro.query = "";
		if(ro.type == null) ro.type="firstLoad";
		var div=YAHOO.util.Dom.get(ro.field).parentNode;
		if(div!=null){
			//if(YAHOO.util.Dom.getStyle(div,"style")!=""){
			YAHOO.util.Dom.setStyle(div,"z-index",ro.zIndex);
			//}
		}
		if(ro.type == "inputLoad"){
			initRemoteCode(ro,language);
			continue;
		}

		var ar = {};
		ar.codeType = ro.codeType;
		ar.typeParam = ro.typeParam;
		ar.type = ro.type;
		ar.riskCode = ro.riskCode;
		ar.language = ro.language;
		ar.query = ro.query;
        try{
            if (ro.extraCond!=undefined && ro.extraCond!=null)
                ar.extraCond = eval(ro.extraCond);
        }
        catch (ex){
            // ignore
        }
        codecfg[i]=ar;
		inputArg[i]=ro;
	}
	if(inputArg.length>0){
    	dwrInvokeData("getAllCode",codecfg,"callbackGetAllCode",inputArg,"","",false);
	}
}

function getCustomCode(codes,fn,language){
	var inputArg = [];
	for(var i=0;i<codes.length;i++){
		var ro = codes[i];
		ro.language = language;
		if(ro.type == null) ro.type="firstLoad";
		var div=YAHOO.util.Dom.get(ro.field).parentNode;
		if(div!=null){
			YAHOO.util.Dom.setStyle(div,"z-index",ro.zIndex);
		}
		if(ro.type == "inputLoad"){
			initRemoteCode(ro,language);
		}
		inputArg[i]=ro;
	}
	dwrInvokeData(fn,codes,"callbackGetAllCode",inputArg,"","",false);
}

var callbackGetAllCode = function(inputObject,outputObject,returnObject){
	for(var i=0;i<inputObject.length;i++){
		var ro = inputObject[i];
		if(ro!=undefined){
		if(ro.type=="firstLoad"){
			var data=[];
			var codes = returnObject[ro.codeType];
			for(var k=0;k<codes.length;k++){
				var co = codes[k];
				var obj={};
				if(co.name==null) co.name="";
				obj["code"]=trim(co.code);
				obj["name"]=trim(co.name);
				data.push(obj);
				GlobalCodeCash[co.codeType] = data;//代码缓存
			}
			if(ro.field=="") continue;//仅缓存
			var onselect = function(oAC,result){
				YAHOO.util.Dom.get(ro.hidden).value=result[INDEX_SELECT_HIDDEN];
				if(codes.codeName!=undefined){
					YAHOO.util.Dom.get(ro.codeName).value=result[INDEX_SELECT_DISPLAY];
				}
			};
			if((ro.onSelect != undefined) && (ro.onSelect != null)){
				onselect = ro.onSelect;
			}
			var forcefill = true;
			if(ro.forceSelection!=undefined && ro.forceSelection==false){
    			forcefill = false;
    		}
			var oConfig = {  keys: ['code','name'],
							primarykey: 'code',
							display: 'name',
							hidden: 'code',
							tooltip: 'name',
							querytype: 'seperate',
							onsel:'',					//callback when select one item
							dwrfarg:onselect,					//dwr function args maker.
							inputtip: '请输入查询条件! ',				//
							noresulttip: '没有符合条件的记录! ',		//
							waittip: '数据查询中! ',			//
							format:_defaultSelectFormat,
							forcefill:forcefill
							};
			if(GlobalCodeCash[ro.codeType]!=undefined){
				data=GlobalCodeCash[ro.codeType];
			}
			var ds = new YAHOO.PICC.DataSource(data,oConfig);
			var ac = new YAHOO.PICC.selectUI(ro.field,ro.field+"_Container",ro.hidden,'list',ds,oConfig);
			 _defaultLocalSelectConfig(ac);

			}
		}
	}
}
YAHOO.widget.AutoComplete.prototype.doBeforeExpandContainer=function(oInput, oContainer, sQuery, oResultItem){
		if(oResultItem.length>MAX_OPTIONS_SIZE){
			oContainer._oContent.style.height="150px";
		}
		if(oResultItem.length==1){
			oContainer._oContent.style.height="40px";
		}
		return true;
	}
	
function hiddenName(obj,name){
	var id=obj.id+"_ac";
	document.getElementById(name).value=document.getElementById(id).value;
}