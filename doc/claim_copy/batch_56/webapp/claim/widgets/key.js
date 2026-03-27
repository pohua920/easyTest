/**
 * @author BugLu
 */
function keyExchange(e,ob){
	//var str="";alert(e.keyCode);
	//for(s in e) str=str+s+"="+e[s]+"\n";alert(str);
	var obj = YAHOO.util.Event.getTarget(e);
	if( ((obj.tagName == "INPUT") || (obj.tagName == "SELECT")) && (e.keyCode==13) ) {e.keyCode=9;e.returnValue=true;};
	
}
