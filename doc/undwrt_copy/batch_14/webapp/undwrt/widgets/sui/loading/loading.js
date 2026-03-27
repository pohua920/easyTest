YAHOO.namespace("SINOSOFT.widget");
(function() {
	var Lang=YAHOO.lang,Event=YAHOO.util.Event,Dom=YAHOO.util.Dom;
	var Loading = function() {
		Loading.prototype.init.apply(this, arguments);
	};
	
	Loading.prototype = {
		_headerMsg : 'Loading, please wait...',
		_bodyInfo:'',
		_imageSrc:'',
		_panelWait:null,
		init : function() {
		   this.initConfig(arguments);
		   this._imageSrc = contextRootPath+'/widgets/sui/loading/loading.gif',
		   this._bodyInfo = '<img src="'+this._imageSrc+'"/>';
		   this._panelWait=new YAHOO.widget.Panel("wait", {
				width : "240px",
				fixedcenter : true,
				close : false,
				draggable : false,
				zindex : 4,
				modal : true,
				visible : false
			});
		   this._panelWait.setHeader(this._headerMsg);
		   this._panelWait.setBody(this._bodyInfo);
		   this._panelWait.render(document.body);
		},
		initConfig:function(args){
			if(Lang.isObject(args)){
				if('pageInfo' in args){
					this._headerMsg=args.pageInfo;
				}
			}
		},
		run:function(){
			this._panelWait.show();
		}
	};
	YAHOO.SINOSOFT.widget.Loading=Loading;
})();
YAHOO.register("Loading",YAHOO.SINOSOFT.widget.Loading, {version: "2.8.0r4", build: "2449"});

