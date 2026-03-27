YAHOO.namespace("SINOSOFT.widget.AutoCompleteWidget");
YAHOO.SINOSOFT.widget.AutoCompleteWidget.ZINDEX = 1000;
(function() {
	// 查询的时候传递给後台的数据。
	function codeCondition(codeType, type, riskCode, typeParam, extraCond,
			language, userCode, query) {
		this.codeType = codeType;
		this.type = type;
		this.riskCode = riskCode;
		this.typeParam = typeParam;
		this.extraCond = extraCond;
		this.language = language;
		this.userCode = userCode;
		this.query = query;
	}
	// 为了兼容ie和firefox使用的getElementsByClassName
	var getElementsByClassName = function(className, tag, elm) {
		if (document.getElementsByClassName) {
			getElementsByClassName = function(className, tag, elm) {
				elm = elm || document;
				var elements = elm.getElementsByClassName(className), nodeName = (tag) ? new RegExp(
						"\\b" + tag + "\\b", "i")
						: null, returnElements = [], current;
				for ( var i = 0, il = elements.length; i < il; i += 1) {
					current = elements[i];
					if (!nodeName || nodeName.test(current.nodeName)) {
						returnElements.push(current);
					}
				}
				return returnElements;
			};
		} else if (document.evaluate) {
			getElementsByClassName = function(className, tag, elm) {
				tag = tag || "*";
				elm = elm || document;
				var classes = className.split(" "), classesToCheck = "", xhtmlNamespace = "http://www.w3.org/1999/xhtml", namespaceResolver = (document.documentElement.namespaceURI === xhtmlNamespace) ? xhtmlNamespace
						: null, returnElements = [], elements, node;
				for ( var j = 0, jl = classes.length; j < jl; j += 1) {
					classesToCheck += "[contains(concat(' ', @class, ' '), ' "
							+ classes[j] + " ')]";
				}
				try {
					elements = document.evaluate(".//" + tag + classesToCheck,
							elm, namespaceResolver, 0, null);
				} catch (e) {
					elements = document.evaluate(".//" + tag + classesToCheck,
							elm, null, 0, null);
				}
				while ((node = elements.iterateNext())) {
					returnElements.push(node);
				}
				return returnElements;
			};
		} else {
			getElementsByClassName = function(className, tag, elm) {
				tag = tag || "*";
				elm = elm || document;
				var classes = className.split(" "), classesToCheck = [], elements = (tag === "*" && elm.all) ? elm.all
						: elm.getElementsByTagName(tag), current, returnElements = [], match;
				for ( var k = 0, kl = classes.length; k < kl; k += 1) {
					classesToCheck.push(new RegExp("(^|\\s)" + classes[k]
							+ "(\\s|$)"));
				}
				for ( var l = 0, ll = elements.length; l < ll; l += 1) {
					current = elements[l];
					match = false;
					for ( var m = 0, ml = classesToCheck.length; m < ml; m += 1) {
						match = classesToCheck[m].test(current.className);
						if (!match) {
							break;
						}
					}
					if (match) {
						returnElements.push(current);
					}
				}
				return returnElements;
			};
		}
		return getElementsByClassName(className, tag, elm);
	};

	// 自动完成组件构造
	function autoCompleteWidget(userCode, autoAutoId) {
		this.userCode = userCode;
		this.wholeContainer = autoAutoId + "_selectui-indiv";
		this.configId = autoAutoId + "_selectConfig";
		this.textId = autoAutoId;
		this.containerId = autoAutoId + "_container";
		YAHOO.SINOSOFT.widget.AutoCompleteWidget.ZINDEX -= 10;
		YAHOO.util.Dom.setStyle(this.wholeContainer, 'z-index',
				YAHOO.SINOSOFT.widget.AutoCompleteWidget.ZINDEX);
	}

	// 自动完成组件
	autoCompleteWidget.prototype.run = function() {
		var Event = YAHOO.util.Event, Dom = YAHOO.util.Dom, queryCondition, configEL, codeDS, codeAutoComp;
		var codeType, type, forceSelection, riskCode, extraCond, inputHint, onSelect, language, typeParam,display;
		var me = this;
		// 初始化所有参数
		function init() {
			configEL = Dom.get(me.configId);
			if (getElementsByClassName("codeType", 'div', configEL).length > 0) {
				codeType = getElementsByClassName("codeType", 'div', configEL)[0].innerHTML;
			} else {
				codeType = "";
			}

			if (getElementsByClassName("type", 'div', configEL).length > 0) {
				type = getElementsByClassName("type", 'div', configEL)[0].innerHTML;
			} else {
				type = "firstLoad";
			}

			if (getElementsByClassName("forceSelection", 'div', configEL).length > 0) {
				forceSelection = getElementsByClassName("forceSelection",
						'div', configEL)[0].innerHTML;
			}

			if (getElementsByClassName("riskCode", 'div', configEL).length > 0) {
				riskCode = getElementsByClassName("riskCode", 'div', configEL)[0].innerHTML;
			} else {
				riskCode = "PUB";
			}
			if (getElementsByClassName("extraCond", 'div', configEL).length > 0) {
				extraCond = getElementsByClassName("extraCond", 'div', configEL)[0].innerHTML;
			} else {
				extraCond = "";
			}
			if (getElementsByClassName("inputHint", 'div', configEL).length > 0) {
				inputHint = getElementsByClassName("inputHint", 'div', configEL)[0].innerHTML;
			} else {
				inputHint = "";
			}
			if (getElementsByClassName("onSelect", 'div', configEL).length > 0) {
				onSelect = getElementsByClassName("onSelect", 'div', configEL)[0].innerHTML;
			} else {
				onSelect = "";
			}
			if (getElementsByClassName("language", 'div', configEL).length > 0) {
				language = getElementsByClassName("language", 'div', configEL)[0].innerHTML;
			} else {
				language = "C";
			}
			if (getElementsByClassName("typeParam", 'div', configEL).length > 0) {
				typeParam = getElementsByClassName("typeParam", 'div', configEL)[0].innerHTML;
			} else {
				typeParam = "";
			}
			if (getElementsByClassName("display", 'div', configEL).length > 0) {
				display = getElementsByClassName("display", 'div', configEL)[0].innerHTML;
			} else {
				display = "code";
			}
		}

		function populateArray(dataFromServer, dataFromBrowser) {
			for ( var i = 0; i < dataFromServer.length; i++) {
				// 数据显示格式
				dataFromBrowser.push( [
						dataFromServer[i].code + '-' + dataFromServer[i].name,
						dataFromServer[i].code ,dataFromServer[i].name]);
			}
		}
		function getCode(sQuery) {
			var aResults = [];
			var callbackProxy = function(results) {
				populateArray(results, aResults);
			};
			var callMetaData = {
				callback : callbackProxy,
				async : false
			};
			if (queryCondition)
				init();
			sQuery =  encodeURI(sQuery);
			queryCondition = new codeCondition(codeType, type, riskCode,
					typeParam, extraCond, language, me.userCode, sQuery);
			dwrInvokeDataAction.getDcode(queryCondition, callMetaData);
			return aResults;
		}

		init();
		codeDS = new YAHOO.widget.DS_JSFunction(getCode);
		codeDS.maxCacheEntries = 50;
		var codeAutoConfig = {
			minQueryLength : 0,
			maxResultsDisplayed : 50,
			prehighlightClassName : "yui-ac-prehighlight",
			useShadow : true,
			autoSnapContainer : true
		};
		if (forceSelection === "1")
			codeAutoConfig.forceSelection = true;

		codeAutoComp = new YAHOO.widget.AutoComplete(me.textId, me.containerId,
				codeDS, codeAutoConfig);
		if (Dom.get(me.textId)) {
			if (type === "firstLoad")
				codeAutoComp.textboxFocusEvent.subscribe(function() {
					var sInputValue = Dom.get(me.textId).value;
					if (sInputValue.length === 0) {
						var oSelf = this;
						setTimeout(function() {
							oSelf.sendQuery("");
						}, 0);
					}
				});
			else if (type === "inputLoad") {
				codeAutoComp.textboxFocusEvent.subscribe(function() {
					codeAutoComp.setHeader(inputHint);
					codeAutoComp.snapContainer();
					codeAutoComp.expandContainer(); 
				});
			}
			codeAutoComp.itemSelectEvent.subscribe(function(sType, aArgs) {
				var oData ;
				if(display!=="code"){
					oData= aArgs[2][2];
				}else{
					oData= aArgs[2][1];
				}
				Dom.get(me.textId).value = oData;
			});
		}
	};
	YAHOO.SINOSOFT.widget.AutoCompleteWidget = autoCompleteWidget;
})();
YAHOO.register("AutoCompleteWidget", YAHOO.SINOSOFT.widget.AutoCompleteWidget,
		{
			version : "2.8.0r4",
			build : "2449"
		});
function addSelect(usercode,id){
	new YAHOO.SINOSOFT.widget.AutoCompleteWidget(usercode,id).run();
}
