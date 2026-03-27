YAHOO.namespace("SINOSOFT.widget");
(function() {
	var Dom = YAHOO.util.Dom, Event = YAHOO.util.Event;
	var ShowHide = function(id) {
		ShowHide.prototype.init.call(this, id);
	};

	ShowHide.prototype = {
		_SHOWHIDE_PREFIX : 'showhide_',      
		_SHOWHIDE_MORE : '_more',
		_SHOWHIDE_LESS : '_less',  
		_SHOWHIDE_CONFIG: '_config',
		_SHOWHIDE_TARGETDIV : '_target',
		_SHOWHIDE_EXPANDLABEL : '_expandLabel',
		_SHOWHIDE_EXPANDBTN : '_expandBtn',
		_SHOWHIDE_EXPANDCONTAINER : '_expandContainer',
		_expandLabelEl : null,
		_expandStatus : 0, 
		_expandContainerEl : null, 
		_showHideConfigEl:null,
		_expandAni : null,
		_collapseAni : null,
		_targetDivEl : null,
		_targetDiv : null,
		_moreStr : 'more',
		_lessStr : 'less', 
		id : null, 
		init : function(id) {
			this.id = id;
			this._targetDiv = Dom.get(this._SHOWHIDE_PREFIX + this.id
					+ this._SHOWHIDE_TARGETDIV);
			this._expandBtnEl = Dom.get(this._SHOWHIDE_PREFIX + this.id
					+ this._SHOWHIDE_EXPANDBTN);
			this._expandContainerEl = Dom.get(this._SHOWHIDE_PREFIX + this.id
					+ this._SHOWHIDE_EXPANDCONTAINER);
			this._expandLabelEl = Dom.get(this._SHOWHIDE_PREFIX + this.id
					+ this._SHOWHIDE_EXPANDLABEL);

			var config_target= Dom.get(this._SHOWHIDE_PREFIX + this.id
					+ this._SHOWHIDE_TARGETDIV);
			this._targetDiv =config_target.innerHTML;
			this._moreStr = Dom.get(this._SHOWHIDE_PREFIX + this.id
					+ this._SHOWHIDE_MORE).innerHTML;
			this._lessStr = Dom.get(this._SHOWHIDE_PREFIX + this.id
					+ this._SHOWHIDE_LESS).innerHTML;

			this._targetDivEl = Dom.get(this._targetDiv);
			
			this._showHideConfigEl=Dom.get(this._SHOWHIDE_PREFIX+this.id+this._showHideConfigEl);
			
			var heightValue = Dom.getStyle(this._targetDivEl, 'height');
			var _expandAniAttr = {
				height : {
					to : parseInt(heightValue)
				}
			};
			var _collapseAniAttr = {
				height : {
					to : 1
				}
			};
			Dom.setStyle(this._targetDivEl, 'height', '0px');
			this._expandAni = new YAHOO.util.Anim(this._targetDiv,
					_expandAniAttr, 0.5);
			this._collapseAni = new YAHOO.util.Anim(this._targetDiv,
					_collapseAniAttr, 0.5);

			this.initStyle();
			this.initEvent();
		},

		initStyle : function() {	
			Dom.addClass(this._targetDivEl,'showhide_target');
			Dom.setStyle(this._expandLabelEl, 'margin-top', '2px');
		},

		initEvent : function() {
			Event.on(this._expandContainerEl, 'click', this.toggleClick,this,true);  
		},

		toggleClick : function() {   
			if (this._expandStatus === 0) {
				// open:
				this._expandLabelEl.innerHTML = this._lessStr;
				Dom.setStyle(this._targetDivEl, 'display', 'block');
				this._expandAni.animate();
				this._expandStatus = 1;
			} else {
				// close:
				this._expandLabelEl.innerHTML = this._moreStr;
				this._collapseAni.onComplete
						.subscribe(function() {
							Dom.setStyle(this._targetDivEl, 'display',
									'none');
						});
				this._collapseAni.animate();
				this._expandStatus = 0;

			}
		}

	};

	YAHOO.SINOSOFT.widget.ShowHide = ShowHide;
})();

YAHOO.register("ShowHide", YAHOO.SINOSOFT.widget.ShowHide, {
	version : "2.8.0r4",
	build : "2449"
});