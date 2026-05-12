/*
 ****************************************************************************
 * DESC       ：Ajax处理文件
 * Author     : 湛进
 * CREATEDATE ：2010-03-17
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *					湛进		2011-03-17		Ajax异步请求处理
 ****************************************************************************
 */
 //获取Ajax请求对象
function AjaxRequestObject(){
	var request=false;
	if (window.XMLHttpRequest){
		request = new XMLHttpRequest();
	}else{
		if(window.ActiveXObject){
			try{
				request = new ActiveXObject("Msxml2.XMLHTTP");
			}catch(e){
				try{
					request = new ActiveXObject("Microsoft.XMLHTTP");
				}catch(failed){
					request=false;
				}
			}
		}
	}
	if(!request){
		alert(i18n.common.ieBrowserNotSupport);  //您的IE浏览器不支持Ajax异步请求
	}
	return request;
}
//设置请求路径，但在设置回调函数时，回调函数名必须以callBack命名，这个可以做为模板
function sendRequest(xmlRequest,url){
	xmlRequest.open("POST",url);
	xmlRequest.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	xmlRequest.send(null);
	xmlRequest.onreadystatechange = callBack;
}