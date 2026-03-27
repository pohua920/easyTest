
function ExChgClsName(switchon,switchoff,timer,btn,navigation,content,menu,page){
  var Obj_1=document.getElementById(switchon)
  Obj_1.className="off"
  var Obj_2=document.getElementById(switchoff)
  Obj_2.className="on"
  var Obj_3=document.getElementById(timer)
  Obj_3.className="off";
  var Obj_4=document.getElementById(btn)
  Obj_4.className="floder_on";
  var Obj_5=document.getElementById(navigation)
  Obj_5.className="alt_width";
  var Obj_6=document.getElementById(content)
  Obj_6.className="alt_margin";
  var menu_Obj=document.getElementById(menu);
  menu_Obj.className="dl_off";
  var dds=menu_Obj.getElementsByTagName('dd');
  for (var i=0;i<dds.length;i++){
	 dds[i].className="off";
 }
  var page_Obj=document.getElementById(page);
  page_Obj.className="page_expand";
 }
function ExChgClsName_on(switchon,switchoff,timer,btn,navigation,content,menu,page){
  var Obj_1=document.getElementById(switchon)
  Obj_1.className="on"
  var Obj_2=document.getElementById(switchoff)
  Obj_2.className="off"
  var Obj_3=document.getElementById(timer)
  Obj_3.className="on";
  var Obj_4=document.getElementById(btn)
  Obj_4.className="floder";
  var Obj_5=document.getElementById(navigation)
  Obj_5.className="default_width";
  var Obj_6=document.getElementById(content)
  Obj_6.className="dedault_margin";
 var menu_Obj=document.getElementById(menu);
 var dds=menu_Obj.getElementsByTagName('dd');
 menu_Obj.className="dl_on";
 for (var i=0;i<dds.length;i++){
	 dds[i].className="on";
 }
  var page_Obj=document.getElementById(page);
  page_Obj.className="page_default";
 }
 

function hidden_menu(tree_item){
	for(var i=0;i<4;i++){
		if("treediv"+i==tree_item){
			var tree_obj=document.getElementById(tree_item);
			tree_obj.className="tree_on";
		}else{
			document.getElementById("treediv"+i).className="tree_off";		
	}
	
}
}

function closetip(popup,scroll_pop){
	var pop_obj=document.getElementById(popup);
	var scroll_obj=document.getElementById(scroll_pop);
	pop_obj.className="off";
	scroll_obj.className="on";
}