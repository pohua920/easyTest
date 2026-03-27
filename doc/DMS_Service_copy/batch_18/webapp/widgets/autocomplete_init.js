/**
 * @author BugLu
 */
if(YAHOO.Claim == undefined) YAHOO.namespace("Claim");
YAHOO.Claim.autocomplete=[];

function initAutoCompleteJSArray(elInput,elContainer,data,attachInput,fnCallback){
	var dtSource = new YAHOO.widget.DS_JSArray(data);
	initAutoComplete(dtSource,elInput,elContainer,data,attachInput,fnCallback);
	return YAHOO.Claim.autocomplete[elInput];
}
/*
 * @param elInput <input>的标签
 */
function initAutoCompleteJson(elInput,elContainer,data,attachInput){
	var dtSource = new YAHOO.widget.DS_XHR(URL,data,{scriptQueryParam:data[1]});
	initAutoComplete(dtSource,elInput,elContainer,data,attachInput);
	return YAHOO.Claim.autocomplete[elInput];
}
/*
 * @param dtSource YAHOO.widget.DataSource;
 */
function initAutoComplete(dtSource,elInput,elContainer,data,attachInput){
	if(!YAHOO.lang.isString(elInput)) elInput = elInput.id;
	if(!YAHOO.lang.isString(elContainer)) elContainer = elContainer.id;
	YAHOO.Claim.autocomplete[elInput] = new YAHOO.widget.AutoComplete(elInput,elContainer, dtSource,{useIframe:true, animSpeed:0.001});
	YAHOO.Claim.autocomplete[elInput].queryDelay = 0;
	YAHOO.Claim.autocomplete[elInput].forceSelection = true;
	YAHOO.Claim.autocomplete[elInput].useIFrame = true;
	YAHOO.Claim.autocomplete[elInput].useShadow = true;
	YAHOO.Claim.autocomplete[elInput].minQueryLength = 0;
	YAHOO.Claim.autocomplete[elInput].textboxFocusEvent.subscribe(function(){this.sendQuery("");});
	YAHOO.util.Event.purgeElement(elInput,true,'click');
	YAHOO.util.Event.addListener(elInput, 'click', function(){YAHOO.Claim.autocomplete[elInput].sendQuery("");});
	//设置选择后的item对应的value存放位置，<input type='hidden' id = attachInput/>
	if(attachInput!=undefined){
		if(!YAHOO.lang.isString(attachInput)) attachInput = attachInput.id;
		YAHOO.Claim.autocomplete[elInput].itemSelectEvent.subscribe(function(oSelf, oItem, oResultData){
			elValue = YAHOO.util.Dom.get(attachInput);
			if(elValue!=null){
				elValue.value = oItem[2][1];
			}else{
				alert("页面元素"+attachInput+"不存在!")
			}
		});
		YAHOO.Claim.autocomplete[elInput].selectionEnforceEvent.subscribe(function(){YAHOO.util.Dom.get(attachInput).value = "";});
	}
	//大于10条记录显示滚动条
	YAHOO.Claim.autocomplete[elInput].doBeforeExpandContainer=function(oInput, oContainer, sQuery, oResultItem){
		if(oResultItem.length>10){
			el = YAHOO.util.Dom.getElementsByClassName("", "UL", oContainer)
			if(el.length>=1){
				YAHOO.util.Dom.setStyle(el[0],"height","100px");
				YAHOO.util.Dom.setStyle(el[0],"overflow","auto");
				//YAHOO.util.Dom.setStyle(el[0],"display","inline-block");
			}else{
				YAHOO.util.Dom.setStyle(el[0],"height","auto");
				YAHOO.util.Dom.setStyle(el[0],"overflow","hidden");
				//YAHOO.util.Dom.setStyle(el[0],"display","");
			}
		}
		return true;
	}
}

