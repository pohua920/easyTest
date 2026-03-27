YAHOO.namespace('SINOSOFT.widget');
var ShowDialogWidget=function(header,url){
	this.init.apply(this,arguments);
};
ShowDialogWidget.prototype={
		header:'header',
		url:'javascript:void;',
		panel:null,
		init:function(header,url){
			this.header=header;
			this.url=url;
			this.panel=new YAHOO.widget.Panel("panel",{iframe:true, visible:true, width:750, height:500, underlay:"shadow", constraintoviewport:true,modal:true, fixedcenter:true});
			this.panel.setHeader(this.header);
			this.panel.setBody("<iframe name='frame' src='"+this.url+"'  frameborder='0' style='margin:0; padding:0; width:100%; height: 100%'></iframe>");
			this.panel.render(document.body);
		},
		show:function(){
			this.panel.show();
		}
};
YAHOO.SINOSOFT.widget.ShowDialogWidget=ShowDialogWidget;