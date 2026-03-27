// JavaScript Document
/*显示、隐藏Module*/
/* o  {	control:image id	expand:展开图像	collapse:收起图像	}*/
EXPAND_IMAGE = "images/butExpandBlue.gif";
COLLAPSE_IMAGE = "images/butCollapseBlue.gif";
function switchModule(e,o){
	if( o == undefined ) return;
	if( o.module == undefined ) return;
	o.module.cfg.setProperty("visible",!o.module.cfg.getProperty("visible"));
	if(o.module.cfg.getProperty("visible")){
		o.control.src = (o.expand == undefined)?EXPAND_IMAGE:o.expand;
	}else{
		o.control.src = (o.expand == undefined)?COLLAPSE_IMAGE:o.collapse;
	}
}