YAHOO.namespace('SINOSOFT.widget');
YAHOO.SINOSOFT.widget.SubmitDialogWidget=function(formInfo,header,url){
	this.header=header;
	this.url=url;
	this.dialog=new YAHOO.widget.Dialog("submitDlg",{iframe:true, visible:true, width:550, height:400, underlay:"shadow", constraintoviewport:true, fixedcenter:true, modal:true,zIndex:302});
	this.dialog.setHeader(this.header);
	this.dialog.setBody("<iframe name='submitFrame' src='javascript:false;'  frameborder='0' style='margin:0; padding:0; width:100%; height: 100%'></iframe>");
	this.dialog.render(document.body);
	this.dialog.show();
	this.fm=formInfo;
	this.fm.action = this.url;
	this.fm.target = "submitFrame";
	this.fm.submit();
};
