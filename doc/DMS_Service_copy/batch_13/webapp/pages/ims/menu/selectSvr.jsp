<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>功能管理</title>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>
<%@ page import="java.util.*"%>
<%@ include file="/common/meta_js.jsp"%>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script type="text/javascript" src='/ims/dwr/interface/Ims.js'></script>
</head>
<body id="all_title">
<div id="wrapper"> 
<div id="container">
<div id="crash_menu">
<h2 align="center">选择服务</h2>
</div>
<form name="fm" action="" method="post" >
<input type="hidden" name="editType" id="editType" value="${editType}">
		<input type="hidden" name="smcMenu.utiISvr.svrCode" id="smcMenu.utiISvr.svrCode" />
		<input type="hidden" name="smcMenu.utiISvr.svrName" id="smcMenu.utiISvr.svrName" />	
		<input type="hidden"  name="editType" id="editType" value="${editType}"/>
		<table width="100%" class="fix_table">
			<tr>
			
		    <td class="bgc_tt short">服务</td>
			<td class="long">
			    <div id="validStatusMapDiv" class="selectui-indiv  dc-chk">
			        <div class="selectConfig">
			        <div class="codeType">StaticSelect</div>
			        </div>
			        <c:set var="checked" value="0" />
			  		<ce:select name="svrCode" id="svrCode" cssClass="selectui-input-up input_w w_30" value="${checked}" onchange="checkURL();" list="svrMap" /><img src="${ctx}/pages/image/imgMustInput.gif" />
			    </div>
			    
			</td>		
		</tr>
		 
	</table>
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr align="center" class="top">
			<td><input type="button" value="下一步" class="button_ty" onclick="next()"></td>
		</tr>
	</table>
</form>
</div>
</div>
</body>
</html>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script type='text/javascript' src='/ims/dwr/interface/Ims.js'></script>
<script language="javascript">
	var tabView = new YAHOO.widget.TabView('tabdemo');
	var tabFlag = new Array();
	tabFlag.push("taskIframe1");
	//YAHOO.util.Event.addListener(window,'load',init);	
	//function init(){
	//	var svrCode_tip = new YAHOO.widget.Tooltip("svrCode_tip",{text:"请双击选择服务代码",context:"svrCode",zIndex:300});
	//}

	function checkURL(){
		var code = fm.document.getElementById("svrCode").options[fm.document.getElementById("svrCode").selectedIndex].text;
		var scode = code.split("-");
		if(scode[1]!=""){
			document.getElementById("smcMenu.utiISvr.svrCode").value = scode[1];
			document.getElementById("smcMenu.utiISvr.svrName").value = scode[0];
		}else{
			document.getElementById("smcMenu.utiISvr.svrName").value = "";
		}
	}
    function next(){
        var sCode = document.getElementById("smcMenu.utiISvr.svrCode").value;
        var code = fm.document.getElementById("svrCode").options[fm.document.getElementById("svrCode").selectedIndex].text;
    	scode = code.split("-");
    	if(scode[1]!=""){
    		document.getElementById("smcMenu.utiISvr.svrCode").value = scode[1];
    		document.getElementById("smcMenu.utiISvr.svrName").value = scode[0];
    	}else{
    		document.getElementById("smcMenu.utiISvr.svrName").value = "";
    	}
     if ("请选择..."==(code)){
   	     alert(" 请选择服务！");
   	     return false;
     }else {
        Ims.hasData(sCode,callBack);
        }
  /*      var scode = new Array();
    	if(YAHOO.quote.data.datacheck('fm') ){
    		var code = fm.document.getElementById("svrCode").options[fm.document.getElementById("svrCode").selectedIndex].text;
    		scode = code.split("-");
    		if(scode[1]!=""){
    			document.getElementById("sCode").value = scode[1];
    			document.getElementById("svrName").value = scode[0];
    		}else{
    			document.getElementById("svrName").value = "";
    		}
       	 if ("请选择..."==(code)){
   	     alert(" 请选择服务！");
       	 }else{
       		fm.action="${ctx}/utiITask/prepareFrame.do";
			fm.submit();
    	 }
    	}
    */
	}
	function callBack(data){
		if(data=="noTask"){
			alert("该服务不包含功能，不能进行菜单管理！");
			return false;
		}else{
			var scode = new Array();
		    if(YAHOO.quote.data.datacheck('fm') ){
		        fm.action="${ctx}/smcMenu/prepareFrame.do";
				fm.submit();
		   }
		}
	}
</script>