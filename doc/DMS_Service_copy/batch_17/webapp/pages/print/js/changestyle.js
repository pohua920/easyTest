
function ExChgClsName(switchon,switchoff,timer,btn,navigation,content,menu){
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
  
 }
function ExChgClsName_on(switchon,switchoff,timer,btn,navigation,content,menu){
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
 }
 
function hidden_menu(tree_item){
	var tree_obj=document.getElementById(tree_item);
	tree_obj.className=tree_obj.className=="tree_off"?"tree_on":"tree_off";
}
