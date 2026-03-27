/*
Copyright (c) 2007, Yahoo! Inc. All rights reserved.
Code licensed under the BSD License:
http://developer.yahoo.net/yui/license.txt
version: 2.2.2
*/
/**
 * The DataSource utility provides a common configurable interface for widgets
 * to access a variety of data, from JavaScript arrays to online servers over
 * XHR.
 *
 * @module datasource
 * @requires yahoo, event
 * @optional xhr
 * @title DataSource Utility
 * @beta
 */

/****************************************************************************/
/****************************************************************************/
/****************************************************************************/

/**
 * The DataSource class defines and manages a live set of data for widgets to
 * interact with. Examples of live databases include in-memory
 * local data such as a JavaScript array, a JavaScript function, or JSON, or
 * remote data such as data retrieved through an XHR connection.
 *
 * @class DataSource
 * @uses YAHOO.util.EventProvider
 * @constructor
 * @param oLiveData {Object} Pointer to live database
 * @param oConfigs {Object} (optional) Object literal of configuration values
 */
YAHOO.util.DataSource = function(oLiveData, oConfigs) {
    if(oConfigs && (oConfigs.constructor == Object)) {
        for(var sConfig in oConfigs) {
            if(sConfig) {
                this[sConfig] = oConfigs[sConfig];
            }
        }
    }

    if(!oLiveData) {
        return;
    }
    
    if(YAHOO.lang.isArray(oLiveData)) {
        this.dataType = YAHOO.util.DataSource.TYPE_JSARRAY;
	} else {
		if (YAHOO.lang.isString(oLiveData)) {
        this.dataType = YAHOO.util.DataSource.TYPE_XHR;
		} else {
			if (YAHOO.lang.isFunction(oLiveData)) {
				this.dataType = YAHOO.util.DataSource.TYPE_JSFUNCTION;
			} else {
				if (YAHOO.lang.isObject(oLiveData)) {
					this.dataType = YAHOO.util.DataSource.TYPE_JSON;
				} else {
					this.dataType = YAHOO.util.DataSource.TYPE_UNKNOWN;
    }
    }
    }
    }

    this.liveData = oLiveData;
    var maxCacheEntries = this.maxCacheEntries;
    if(!YAHOO.lang.isNumber(maxCacheEntries) || (maxCacheEntries < 0)) {
        maxCacheEntries = 0;
    }
    if(maxCacheEntries > 0 && !this._aCache) {
        this._aCache = [];
    }

    this._sName = "instance" + YAHOO.util.DataSource._nIndex;
    YAHOO.util.DataSource._nIndex++;
    this.createEvent("cacheRequestEvent");

    /**
     * Fired when data is retrieved from the local cache.
     *
     * @event getCachedResponseEvent
     * @param oArgs.request {Object} The request object.
     * @param oArgs.response {Object} The response object.
     * @param oArgs.callback {Function} The callback function.
     * @param oArgs.caller {Object} The parent object of the callback function.
     */
    this.createEvent("cacheResponseEvent");

    /**
     * Fired when a request is sent to the live data source.
     *
     * @event requestEvent
     * @param oArgs.request {Object} The request object.
     * @param oArgs.callback {Function} The callback function.
     * @param oArgs.caller {Object} The parent object of the callback function.
     */
    this.createEvent("requestEvent");

    /**
     * Fired when live data source sends response.
     *
     * @event responseEvent
     * @param oArgs.request {Object} The request object.
     * @param oArgs.response {Object} The raw response object.
     * @param oArgs.callback {Function} The callback function.
     * @param oArgs.caller {Object} The parent object of the callback function.
     */
    this.createEvent("responseEvent");

    /**
     * Fired when response is parsed.
     *
     * @event responseParseEvent
     * @param oArgs.request {Object} The request object.
     * @param oArgs.response {Object} The parsed response object.
     * @param oArgs.callback {Function} The callback function.
     * @param oArgs.caller {Object} The parent object of the callback function.
     */
    this.createEvent("responseParseEvent");

    /**
     * Fired when response is cached.
     *
     * @event responseCacheEvent
     * @param oArgs.request {Object} The request object.
     * @param oArgs.response {Object} The parsed response object.
     * @param oArgs.callback {Function} The callback function.
     * @param oArgs.caller {Object} The parent object of the callback function.
     */
    this.createEvent("responseCacheEvent");
    /**
     * Fired when an error is encountered with the live data source.
     *
     * @event dataErrorEvent
     * @param oArgs.request {Object} The request object.
     * @param oArgs.callback {Function} The callback function.
     * @param oArgs.caller {Object} The parent object of the callback function.
     * @param oArgs.message {String} The error message.
     */
    this.createEvent("dataErrorEvent");

    /**
     * Fired when the local cache is flushed.
     *
     * @event cacheFlushEvent
     */
    this.createEvent("cacheFlushEvent");
};

YAHOO.augment(YAHOO.util.DataSource, YAHOO.util.EventProvider);
YAHOO.util.DataSource.TYPE_UNKNOWN = -1;

/**
 * Type is a JavaScript Array.
 *
 * @property TYPE_JSARRAY
 * @type Number
 * @final
 * @default 0
 */
YAHOO.util.DataSource.TYPE_JSARRAY = 0;

/**
 * Type is a JavaScript Function.
 *
 * @property TYPE_JSFUNCTION
 * @type Number
 * @final
 * @default 1
 */
YAHOO.util.DataSource.TYPE_JSFUNCTION = 1;

/**
 * Type is hosted on a server via an XHR connection.
 *
 * @property TYPE_XHR
 * @type Number
 * @final
 * @default 2
 */
YAHOO.util.DataSource.TYPE_XHR = 2;

/**
 * Type is JSON.
 *
 * @property TYPE_JSON
 * @type Number
 * @final
 * @default 3
 */
YAHOO.util.DataSource.TYPE_JSON = 3;

/**
 * Type is XML.
 *
 * @property TYPE_XML
 * @type Number
 * @final
 * @default 4
 */
YAHOO.util.DataSource.TYPE_XML = 4;

/**
 * Type is plain text.
 *
 * @property TYPE_TEXT
 * @type Number
 * @final
 * @default 5
 */
YAHOO.util.DataSource.TYPE_TEXT = 5;
/**
 * Error message for invalid data responses.
 *
 * @property ERROR_DATAINVALID
 * @type String
 * @final
 * @default "Invalid data"
 */
YAHOO.util.DataSource.ERROR_DATAINVALID = "Invalid data";

/**
 * Error message for null data responses.
 *
 * @property ERROR_DATANULL
 * @type String
 * @final
 * @default "Null data"
 */
YAHOO.util.DataSource.ERROR_DATANULL = "Null data";
YAHOO.util.DataSource._nIndex = 0;

/**
 * Name of DataSource instance.
 *
 * @property _sName
 * @type String
 * @private
 */
YAHOO.util.DataSource.prototype._sName = null;

/**
 * Local cache of data result objects indexed chronologically.
 *
 * @property _aCache
 * @type Object[]
 * @private
 */
YAHOO.util.DataSource.prototype._aCache = null;
YAHOO.util.DataSource.prototype.maxCacheEntries = 0;

 /**
 * Pointer to live database.
 *
 * @property liveData
 * @type Object
 */
YAHOO.util.DataSource.prototype.liveData = null;

 /**
 * If data is accessed over XHR via Connection Manager, the connection timeout is
 * configurable in milliseconds the XHR connection will wait for a server
 * response. A a value of zero indicates the XHR connection will wait forever.
 * Any value greater than zero will use the Connection utility's Auto-Abort
 * feature.
 *
 * @property connTimeout
 * @type Number
 * @default 0
 */
YAHOO.util.DataSource.prototype.connTimeout = null;

 /**
 * Alias to YUI Connection Manager. Allows implementers to specify their own
 * subclasses of the YUI Connection Manager utility.
 *
 * @property connMgr
 * @type Object
 * @default YAHOO.util.Connect
 */
YAHOO.util.DataSource.prototype.connMgr = YAHOO.util.Connect || null;

/**
 * Where the live data is held.
 *
 * @property dataType
 * @type Number
 * @default YAHOO.util.DataSource.TYPE_UNKNOWN
 *
 */
YAHOO.util.DataSource.prototype.dataType = YAHOO.util.DataSource.TYPE_UNKNOWN;

/**
 * Format of response.
 *
 * @property responseType
 * @type Number
 * @default YAHOO.util.DataSource.TYPE_UNKNOWN
 */
YAHOO.util.DataSource.prototype.responseType = YAHOO.util.DataSource.TYPE_UNKNOWN;

/**
 * Response schema object literal takes a combination of the following properties:
 *
 * <dl>
 * <dt>resultsList</dt> <dd>Pointer to array of tabular data</dd>
 * <dt>resultNode</dt> <dd>Pointer to node name of row data (XML data only)</dd>
 * <dt>recordDelim</dt> <dd>Record delimiter (text data only)</dd>
 * <dt>fieldDelim</dt> <dd>Field delimiter (text data only)</dd>
 * <dt>fields</dt> <dd>Array of field names (aka keys), or array of object literals
 * such as: {key:"fieldname",converter:YAHOO.util.DataSource.convertDate}</dd>
 * </dl>
 *
 * @property responseSchema
 * @type Object
 */
YAHOO.util.DataSource.prototype.responseSchema = null;
YAHOO.util.DataSource.convertNumber = function(sData) {
    return sData * 1;
};

/**
 * Converts data from String to Date objects.
 *
 * @method convertDate
 * @method sData {String} Date string.
 * @return {Date} Date object.
 * @static
 */
YAHOO.util.DataSource.convertDate = function(sData) {
    var mm = sMarkup.substring(0,sMarkup.indexOf("/"));
    sMarkup = sMarkup.substring(sMarkup.indexOf("/")+1);
    var dd = sMarkup.substring(0,sMarkup.indexOf("/"));
    var yy = sMarkup.substring(sMarkup.indexOf("/")+1);
    return new Date(yy, mm, dd);
};
YAHOO.util.DataSource.prototype.toString = function() {
    return "DataSource " + this._sName;
};

/**
 * Overridable method passes request to cache and returns cached response if any,
 * refreshing the hit in the cache as the newest item. Returns null if there is
 * no cache hit.
 *
 * @method getCachedResponse
 * @param oRequest {Object} Request object.
 * @param oCallback {Function} Handler function to receive the response
 * @param oCaller {Object} The Calling object that is making the request
 * @return {Object} Cached response object or null.
 */
YAHOO.util.DataSource.prototype.getCachedResponse = function(oRequest, oCallback, oCaller) {
    var aCache = this._aCache;
    var nCacheLength = (aCache) ? aCache.length : 0;
    var oResponse = null;
    if((this.maxCacheEntries > 0) && aCache && (nCacheLength > 0)) {
        this.fireEvent("cacheRequestEvent", {request:oRequest,callback:oCallback,caller:oCaller});
        for(var i = nCacheLength-1; i >= 0; i--) {
            var oCacheElem = aCache[i];
            if(this.isCacheHit(oRequest,oCacheElem.request)) {
                oResponse = oCacheElem.response;
                aCache.splice(i,1);
                this.addToCache(oRequest, oResponse);
                this.fireEvent("cacheResponseEvent", {request:oRequest,response:oResponse,callback:oCallback,caller:oCaller});
                break;
            }
        }
    }
    return oResponse;
};

/**
 * Default overridable method matches given request to given cached request.
 * Returns true if is a hit, returns false otherwise.  Implementers should
 * override this method to customize the cache-matching algorithm.
 *
 * @method isCacheHit
 * @param oRequest {Object} Request object.
 * @param oCachedRequest {Object} Cached request object.
 * @return {Boolean} True if given request matches cached request, false otherwise.
 */
YAHOO.util.DataSource.prototype.isCacheHit = function(oRequest, oCachedRequest) {
    return (oRequest === oCachedRequest);
};

/**
 * Adds a new item to the cache. If cache is full, evicts the stalest item
 * before adding the new item.
 *
 * @method addToCache
 * @param oRequest {Object} Request object.
 * @param oResponse {Object} Response object to cache.
 */
YAHOO.util.DataSource.prototype.addToCache = function(oRequest, oResponse) {
    var aCache = this._aCache;
    if(!aCache) {
        return;
    }
    while(aCache.length >= this.maxCacheEntries) {
        aCache.shift();
    }
    var oCacheElem = {request:oRequest,response:oResponse};
    aCache.push(oCacheElem);
    this.fireEvent("responseCacheEvent",{request:oRequest,response:oResponse});
};

/**
 * Flushes cache.
 *
 * @method flushCache
 */
YAHOO.util.DataSource.prototype.flushCache = function() {
    if(this._aCache) {
        this._aCache = [];
        this.fireEvent("cacheFlushEvent");
    }
};

/**
 * First looks for cached response, then sends request to live data.
 *
 * @method sendRequest
 * @param oRequest {Object} Request object
 * @param oCallback {Function} Handler function to receive the response
 * @param oCaller {Object} The Calling object that is making the request
 */
YAHOO.util.DataSource.prototype.sendRequest = function(oRequest, oCallback, oCaller) {
    var oCachedResponse = this.getCachedResponse(oRequest, oCallback, oCaller);
    if(oCachedResponse) {
        oCallback.call(oCaller, oRequest, oCachedResponse);
        return;
    }
    this.makeConnection(oRequest, oCallback, oCaller);
};

/**
 * Overridable method provides default functionality to make a connection to
 * live data in order to send request. The response coming back is then
 * forwarded to the handleResponse function. This method should be customized
 * for more complex implementations.
 *
 * @method makeConnection
 * @param oRequest {Object} Request object.
 * @param oCallback {Function} Handler function to receive the response
 * @param oCaller {Object} The Calling object that is making the request
 */
YAHOO.util.DataSource.prototype.makeConnection = function(oRequest, oCallback, oCaller) {
    this.fireEvent("requestEvent", {request:oRequest,callback:oCallback,caller:oCaller});
    var oRawResponse = null;
    switch(this.dataType) {
        case YAHOO.util.DataSource.TYPE_JSARRAY:
        case YAHOO.util.DataSource.TYPE_JSON:
            oRawResponse = this.liveData;
            this.handleResponse(oRequest, oRawResponse, oCallback, oCaller);
            break;
        case YAHOO.util.DataSource.TYPE_JSFUNCTION:
            oRawResponse = this.liveData(oRequest);
            this.handleResponse(oRequest, oRawResponse, oCallback, oCaller);
            break;
        case YAHOO.util.DataSource.TYPE_XHR:
            /**
             * Connection Manager success handler
             *
             * @method _xhrSuccess
             * @param oResponse {Object} HTTPXMLRequest object
             * @private
             */
            var _xhrSuccess = function(oResponse) {
                if(oResponse && (!this._oConn || (oResponse.tId != this._oConn.tId))) {
                    this.fireEvent("dataErrorEvent", {request:oRequest,callback:oCallback,caller:oCaller,message:YAHOO.util.DataSource.ERROR_DATAINVALID});
                    return null;
			} else {
				if (!oResponse) {
                    this.fireEvent("dataErrorEvent", {request:oRequest,callback:oCallback,caller:oCaller,message:YAHOO.util.DataSource.ERROR_DATANULL});
                    oCallback.call(oCaller, oRequest, oResponse, true);

                    return null;
				} else {
					this.handleResponse(oRequest, oResponse, oCallback, oCaller);
                }
                }
            };

            /**
             * Connection Manager failure handler
             *
             * @method _xhrFailure
             * @param oResponse {Object} HTTPXMLRequest object
             * @private
             */
            var _xhrFailure = function(oResponse) {
                this.fireEvent("dataErrorEvent", {request:oRequest,callback:oCallback,caller:oCaller,message:YAHOO.util.DataSource.ERROR_DATAINVALID});
                oCallback.call(oCaller, oRequest, oResponse, true);
                    
                return null;
            };
		var _xhrCallback = {success:_xhrSuccess, failure:_xhrFailure, scope:this};
            if(YAHOO.lang.isNumber(this.connTimeout) && (this.connTimeout > 0)) {
                _xhrCallback.timeout = this.connTimeout;
            }
            if(this._oConn && this.connMgr) {
                this.connMgr.abort(this._oConn);
            }

            var sUri = this.liveData+"?"+oRequest;
            if(this.connMgr) {
                this._oConn = this.connMgr.asyncRequest(this.connMgr._method, sUri, _xhrCallback, null);
		} else {
                oCallback.call(oCaller, oRequest, null, true);
            }

            break;
        default:
            break;
    }
};

/**
 * Handles raw data response from live data source.
 *
 * @method handleResponse
 * @param oRequest {Object} Request object
 * @param oRawResponse {Object} The raw response from the live database
 * @param oCallback {Function} Handler function to receive the response
 * @param oCaller {Object} The calling object that is making the request
 */
YAHOO.util.DataSource.prototype.handleResponse = function(oRequest, oRawResponse, oCallback, oCaller) {
    this.fireEvent("responseEvent", {request:oRequest,response:oRawResponse,callback:oCallback,caller:oCaller});
    var xhr = (this.dataType == YAHOO.util.DataSource.TYPE_XHR) ? true : false;
    var oParsedResponse = null;
    switch(this.responseType) {
        case YAHOO.util.DataSource.TYPE_JSARRAY:
            if(xhr && oRawResponse.responseText) {
                oRawResponse = oRawResponse.responseText;
            }
            oParsedResponse = this.parseArrayData(oRequest, oRawResponse);
            break;
        case YAHOO.util.DataSource.TYPE_JSON:
            if(xhr && oRawResponse.responseText) {
                oRawResponse = oRawResponse.responseText;
            }
            oParsedResponse = this.parseJSONData(oRequest, oRawResponse);
            break;
        case YAHOO.util.DataSource.TYPE_XML:
            if(xhr && oRawResponse.responseXML) {
                oRawResponse = oRawResponse.responseXML;
            }
            oParsedResponse = this.parseXMLData(oRequest, oRawResponse);
            break;
        case YAHOO.util.DataSource.TYPE_TEXT:
            if(xhr && oRawResponse.responseText) {
                oRawResponse = oRawResponse.responseText;
            }
            oParsedResponse = this.parseTextData(oRequest, oRawResponse);
            break;
        default:
            break;
    }

    if(oParsedResponse) {
        this.fireEvent("responseParseEvent", {request:oRequest,response:oParsedResponse,callback:oCallback,caller:oCaller});
        this.addToCache(oRequest, oParsedResponse);
        oCallback.call(oCaller, oRequest, oParsedResponse);
	} else {
        this.fireEvent("dataErrorEvent", {request:oRequest,callback:oCallback,caller:oCaller,message:YAHOO.util.DataSource.ERROR_DATANULL});
        oCallback.call(oCaller, oRequest, null, true);
    }
};

/**
 * Overridable method parses raw array data into a response object.
 *
 * @method parseArrayData
 * @param oRequest {Object} Request object.
 * @param oRawResponse {Object} The raw response from the live database
 * @return {Object} Parsed response object
 */
YAHOO.util.DataSource.prototype.parseArrayData = function(oRequest, oRawResponse) {
    if(YAHOO.lang.isArray(oRawResponse) && YAHOO.lang.isArray(this.responseSchema.fields)) {
        var oParsedResponse = [];
        var fields = this.responseSchema.fields;
        for(var i=oRawResponse.length-1; i>-1; i--) {
            var oResult = {};
            for(var j=fields.length-1; j>-1; j--) {
                var field = fields[j];
                var key = field.key || field;
                var data = oRawResponse[i][j] || oRawResponse[i][key];
                if(field.converter) {
                    data = field.converter(data);
                }
                oResult[key] = data;
            }
            oParsedResponse.unshift(oResult);
        }
        return oParsedResponse;
	} else {
        return null;
    }
};

/**
 * Overridable method parses raw plain text data into a response object.
 *
 * @method parseTextData
 * @param oRequest {Object} Request object
 * @param oRawResponse {Object} The raw response from the live database
 * @return {Object} Parsed response object
 */
YAHOO.util.DataSource.prototype.parseTextData = function(oRequest, oRawResponse) {
	if (YAHOO.lang.isString(oRawResponse) && YAHOO.lang.isArray(this.responseSchema.fields) && YAHOO.lang.isString(this.responseSchema.recordDelim) && YAHOO.lang.isString(this.responseSchema.fieldDelim)) {
        var oParsedResponse = [];
        var recDelim = this.responseSchema.recordDelim;
        var fieldDelim = this.responseSchema.fieldDelim;
        var fields = this.responseSchema.fields;
        if(oRawResponse.length > 0) {
            var newLength = oRawResponse.length-recDelim.length;
            if(oRawResponse.substr(newLength) == recDelim) {
                oRawResponse = oRawResponse.substr(0, newLength);
            }
            var recordsarray = oRawResponse.split(recDelim);
            for(var i = recordsarray.length-1; i>-1; i--) {
                var oResult = {};
                for(var j=fields.length-1; j>-1; j--) {
                    var fielddataarray = recordsarray[i].split(fieldDelim);
                    var data = fielddataarray[j];
                    if(data.charAt(0) == "\"") {
                        data = data.substr(1);
                    }
                    if(data.charAt(data.length-1) == "\"") {
                        data = data.substr(0,data.length-1);
                    }
                    var field = fields[j];
                    var key = field.key || field;
                    if(field.converter) {
                        data = field.converter(data);
                    }
                    oResult[key] = data;
                }
                oParsedResponse.unshift(oResult);
            }
        }
        return oParsedResponse;
	} else {
        return null;
    }
};

/**
 * Overridable method parses raw XML data into a response object.
 *
 * @method parseXMLData
 * @param oRequest {Object} Request object
 * @param oRawResponse {Object} The raw response from the live database
 * @return {Object} Parsed response object
 */
YAHOO.util.DataSource.prototype.parseXMLData = function(oRequest, oRawResponse) {
        var bError = false;
        var oParsedResponse = [];
	var xmlList = (this.responseSchema.resultNode) ? oRawResponse.getElementsByTagName(this.responseSchema.resultNode) : null;
        if(!xmlList || !YAHOO.lang.isArray(this.responseSchema.fields)) {
            bError = true;
	} else {
            for(var k = xmlList.length-1; k >= 0 ; k--) {
                var result = xmlList.item(k);
                var oResult = {};
                for(var m = this.responseSchema.fields.length-1; m >= 0 ; m--) {
                    var field = this.responseSchema.fields[m];
                    var key = field.key || field;
                    var data = null;
                    var xmlAttr = result.attributes.getNamedItem(key);
                    if(xmlAttr) {
                        data = xmlAttr.value;
				} else {
                        var xmlNode = result.getElementsByTagName(key);
                        if(xmlNode && xmlNode.item(0) && xmlNode.item(0).firstChild) {
                            data = xmlNode.item(0).firstChild.nodeValue;
					} else {
                               data = "";
                        }
                    }
                    if(field.converter) {
                        data = field.converter(data);
                    }
                    oResult[key] = data;
                }
                oParsedResponse.unshift(oResult);
            }
        }
        if(bError) {
            return null;
        }
        return oParsedResponse;
};

/**
 * Overridable method parses raw JSON data into a response object.
 *
 * @method parseJSONData
 * @param oRequest {Object} Request object
 * @param oRawResponse {Object} The raw response from the live database
 * @return {Object} Parsed response object
 */
YAHOO.util.DataSource.prototype.parseJSONData = function(oRequest, oRawResponse) {
    if(oRawResponse && YAHOO.lang.isArray(this.responseSchema.fields)) {
        var fields = this.responseSchema.fields;
        var bError = false;
        var oParsedResponse = [];
        var jsonObj,jsonList;
        if(YAHOO.lang.isString(oRawResponse)) {
			var isNotMac = (navigator.userAgent.toLowerCase().indexOf("khtml") == -1);
            if(oRawResponse.parseJSON && isNotMac) {
                jsonObj = oRawResponse.parseJSON();
                if(!jsonObj) {
                    bError = true;
                }
			} else {
				if (window.JSON && JSON.parse && isNotMac) {
                jsonObj = JSON.parse(oRawResponse);
                if(!jsonObj) {
                    bError = true;
                }
				} else {
                try {
						while (oRawResponse.length > 0 && (oRawResponse.charAt(0) != "{") && (oRawResponse.charAt(0) != "[")) {
                        oRawResponse = oRawResponse.substring(1, oResponse.length);
                    }

                    if(oRawResponse.length > 0) {
                        var objEnd = Math.max(oRawResponse.lastIndexOf("]"),oRawResponse.lastIndexOf("}"));
                        oRawResponse = oRawResponse.substring(0,objEnd+1);
                        jsonObj = eval("(" + oRawResponse + ")");
                        if(!jsonObj) {
                            bError = true;
                        }

                    }
                }
                catch(e) {
                    bError = true;
               }
            }
        }
		} else {
			if (oRawResponse.constructor == Object) {
            jsonObj = oRawResponse;
			} else {
				bError = true;
        }
        }
        if(jsonObj && jsonObj.constructor == Object) {
            try {
                jsonList = eval("jsonObj." + this.responseSchema.resultsList);
            }
            catch(e) {
                bError = true;
            }
        }

        if(bError || !jsonList) {
            return null;
		} else {
			if (!YAHOO.lang.isArray(jsonList)) {
				jsonList = [jsonList];
        }
        }
        for(var i = jsonList.length-1; i >= 0 ; i--) {
            var oResult = {};
            var jsonResult = jsonList[i];
            for(var j = fields.length-1; j >= 0 ; j--) {
                var field = fields[j];
                var key = field.key || field;
                var data;
                if(key.indexOf(".")>-1){
                	data = jsonResult[key];
                } else {
                	data = eval("jsonResult." + key);
                }
                
                if((typeof data == "undefined") || (data === null)) {
                    data = "";
                }
                if(field.converter) {
                    data = field.converter(data);
                }
                oResult[key] = data;
            }
            oParsedResponse.unshift(oResult);
        }
        YAHOO.log("Parsed JSON data = " + oParsedResponse,"info",this.toString());
        this.totalRecords = parseInt(eval("jsonObj.totalRecords"));
        if(isNaN(this.totalRecords)){
            this.totalRecords = 0;
        }
		if (eval("jsonObj.msg") != null) {
			YAHOO.widget.DataTable.MSG_EMPTY = eval("jsonObj.msg");
		} else {
			YAHOO.widget.DataTable.MSG_EMPTY = STATIC_MSG_EMPTY;
		}
		if (eval("jsonObj.message") != null) {
			YAHOO.widget.DataTable.MESSAGE = eval("jsonObj.message");
		} else {
			YAHOO.widget.DataTable.MESSAGE = null;
		}
        return oParsedResponse;
	} else {
        YAHOO.log("JSON data could not be parsed" + oRawResponse,"error",this.toString());
        return null;
    }
};
YAHOO.register("datasource", YAHOO.util.DataSource, {version:"2.2.2", build:"204"});

