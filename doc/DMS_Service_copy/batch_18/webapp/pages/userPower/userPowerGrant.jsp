<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<%-- moidfy  update by tongziliang 2011-10-09 reason:修改页面的按钮样式和优化页面样式 --%>
<link rel="stylesheet" href="${ctx}/style/popup/login.css" type="text/css"></link>
<html>
<head></head>
<body >
	<table class="fix_table" border="0" >
	<div id="viewTab" class="yui-navset">   
	    <ul class="yui-nav">
	    <li class="selected"><a href="#tab1"><em>功能范围配置</em></a></li>
	    <li><a href="#tab2" onclick="javascript:showiframe('com');"><em>允许机构配置</em></a></li>
        <li><a href="#tab4" onclick="javascript:showiframe('exceptCom');"><em>除外机构配置</em></a></li>
	    <li><a href="#tab3" onclick="javascript:showiframe('product');"><em>允许产品配置</em></a></li>
       
	    </ul>
		<div class="yui-content">
	        <div id="tab1">
	        	<iframe id="taskIframe" src="${ctx}/saaUserPower/taskPowerConfig.do?userCode=${userCode}" frameborder="0" width="100%" height="580"></iframe>
	        </div>
	        <div id="tab2">
	        	<iframe id="comIframe" src="#" frameborder="0" width="100%" height="580"></iframe>
	        </div>
            <div id="tab4">
	        	<iframe id="exceptComIframe" src="#" frameborder="0" width="100%" height="580"></iframe>
	        </div>
	        <div id="tab3">
	        	<iframe id="productIframe" src="#" frameborder="0" width="100%" height="580"></iframe>
	        </div>
	    </div>
	 </div>
	<tr>
	<s:form name="fm" method="post" enctype ="multipart/form-data">	
		<td align="center">
		<button type="button" name="submitIframeButton" onclick="javascript:iframe_submit()"  value=""><span><em>保存配置</em></span></button>
<!--		<input type="button" name="submitIframeButton" onclick="javascript:iframe_submit()" class="button_ty" value="保存配置">-->
		</td>
	</s:form>
	</tr>	
</table>
</body>
</html>
<script type="text/javascript">
var tabView = new YAHOO.widget.TabView('viewTab');
var taskFlage="t";
var comFlage="";
var productFlage="";
var exceptComFlage="";
	function iframe_submit(){

		var comUrl=document.all.comIframe.src;
		var exceptComUrl=document.all.exceptComIframe.src;
		var productUrl=document.all.productIframe.src;
		if(comUrl=="#"){
			alert("系统检测到您并未针对该人员进行允许机授权，请返回");
			return false;
		}
		if(exceptComUrl=="#"){
			alert("系统检测到您并未针对该人员进行除外机构授权，请返回");
			return false;
		}
		if(productUrl=="#"){
			alert("系统检测到您并未针对该人员进允许行产品授权，请返回");
			return false;
		}
		//alert("comUrl-----"+comUrl);
		//alert("exceptComUrl-----"+exceptComUrl);
		//alert("productUrl-----"+productUrl);
		if(comUrl!="#"){
			
			//document.frames["productIframe"].document.forms["fm"].submit();
			document.frames["exceptComIframe"].document.forms["fm"].submit();
			
		}
        if(exceptComUrl!="#"){
			
			//document.frames["comIframe"].document.forms["fm"].submit();
			document.frames["productIframe"].updateAuthRisk();
		}
		if(productUrl!="#"){
			document.frames["comIframe"].document.forms["fm"].submit();
			//document.frames["exceptComIframe"].document.forms["fm"].submit();
		}
		
			document.frames["taskIframe"].subMitMethod();
			fm.submitIframeButton.disabled=true;
		}

	function show(temp){
		if(temp=="task"){
			document.getElementById("taskIframe").style.display="block";
			document.getElementById("comIframe").style.display="none";
			document.getElementById("exceptComIframe").style.display="none";
			document.getElementById("productIframe").style.display="none";
		}
		if(temp=="com"){
			document.getElementById("taskIframe").style.display="none";
			document.getElementById("comIframe").style.display="block";
			document.getElementById("exceptComIframe").style.display="none";
			document.getElementById("productIframe").style.display="none";
		}
		if(temp=="exceptCom"){
			document.getElementById("taskIframe").style.display="none";
			document.getElementById("comIframe").style.display="none";
			document.getElementById("exceptComIframe").style.display="block";
			document.getElementById("productIframe").style.display="none";
		}
		if(temp=="product"){
			document.getElementById("taskIframe").style.display="none";
			document.getElementById("comIframe").style.display="none";
			document.getElementById("exceptComIframe").style.display="none";
			document.getElementById("productIframe").style.display="block";
		}
	}
	
	function showiframe(temp){
			/*if(temp=="task"&&taskFlage!="t"){
				document.all.taskIframe.src="${ctx}/saaUserPower/taskPowerConfig.do?userCode=${userCode}"
			}*/
			
			if(temp=="com"&& comFlage!="t"){
				document.all.comIframe.src="${ctx}/saaUserPower/comPowerConfig.do?userCode=${userCode}"
				comFlage="t";
			}
			if(temp=="exceptCom"&& exceptComFlage!="t"){
				document.all.exceptComIframe.src="${ctx}/saaUserPower/exceptComPowerConfig.do?userCode=${userCode}"
			    exceptComFlage="t";
			}
			if(temp=="product"&& productFlage!="t"){
				document.all.productIframe.src="${ctx}/saaUserPower/productPowerConfig.do?userCode=${userCode}"
				productFlage="t";
			}
		}
</script>