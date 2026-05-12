  /**
 * 通用的双击域
 * @param selfId 你双击的文本域id
 * @param relationId  你希望改变值的文本域id
 * @param methodType  你要执行的方法类型，一般是自己在QueryCodeAllAction类中写，但是如果是PDLDCode1表，那么不用再写了
 * @param paramsByJSON  传递的参数，A:a|B:b;其中A是对应到表中的字段，a是页面某个文本域的id，!!特别注意：如果methodType=PDLDcode1，那么A是CodeType，a是codetype的一个值!!
 * 特别注意！如果要使用请定义一个全局变量ctx，这个是和路径有关的变量。eg:var ctx = $("#ctx");
 * 可以参见ReportApply.jsp使用的双击域方法
 */
function queryCode(selfId,relationId,methodType,paramsByJSON,functionName){
	var obj =  new Object();
	obj.selfId = selfId;
	obj.relationId = relationId;
	obj.methodType = methodType;
	//obj.functionName = functionName;
	var fun = {
			functionName : functionName
	}
	if(methodType != "PDLDcode1" && paramsByJSON != null && paramsByJSON != ""){		
		var temp = paramsByJSON.substring(1,paramsByJSON.length);
		var params = "";
		var arrayStr = paramsByJSON.split("|");
		for(var i = 0 ; i < arrayStr.length ; i++){
			var tempArray = arrayStr[i].split(":");
			var value = $("#"+tempArray[1]).val();
			params += tempArray[0]+":"+value+"|";
		}
		obj.paramsByJSON = params.substring(0,params.length-1);
	}else{
		obj.paramsByJSON = paramsByJSON;
	}
	window.obj = obj;
	window.fun = fun;
	var url= contextRootPath + "/common/pub/QueryCodeInputResult.jsp";
	var w=(screen.availWidth-300)/2; 
    var h=(screen.availHeight-380)/2;
    window.showModalDialog(url,window,"dialogHide:yes;help:no;status:no;scroll:yes;dialogWidth:300px;dialogHeight:380px,dialogLeft:"+w+",dialogTop:"+h);
}
