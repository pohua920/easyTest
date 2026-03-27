<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="org.apache.struts2.ServletActionContext"%>
<%@ page import="com.opensymphony.xwork2.ActionContext"%>
<%@ page import="com.opensymphony.xwork2.util.ValueStack"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
  <head>
  	<title>服务认证方式</title>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script type='text/javascript' src="/ims/dwr/interface/Ims.js"></script>
<script type="text/javascript" src="${ctx}/common/js/sinosoft.js"></script>
<script type="text/javascript" src="${ctx}/common/js/MulLine.js"></script>
<script type="text/javascript" src="${ctx}/common/js/Common.js"></script>
<script type="text/javascript" src="${ctx }/common/dtree/dtree.js"></script>
<script language="javascript" src="${ctx}/common/js/Common.js"></script>
<script language="javascript" src="${ctx}/widgets/yui/yahoo-dom-event/yahoo-dom-event.js"></script>
<script language="javascript" src="${ctx}/widgets/yui/datasource/datasource-beta-min.js"></script>
<script language="javascript" src="${ctx}/widgets/yui/datatable/datatable-beta-min.js"></script>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
  </head>
   <script type="text/javascript">
  	var values="";
  	var names="";
  	function setOperator(optionValue){ 
  		names="";	
		values = values+optionValue.value;
		document.getElementById("methods").value = document.getElementById("methods").value + document.getElementById(optionValue.id).value;
		document.getElementById("hiddenName").value = document.getElementById("hiddenName").value + document.getElementById(optionValue.id).name;
  	}
  	
	function addSign(signValue){
		names="";
		values = values+signValue.value;
		document.getElementById("methods").value = document.getElementById("methods").value + document.getElementById(signValue.id).value;
		document.getElementById("hiddenName").value = document.getElementById("hiddenName").value + document.getElementById(signValue.id).name;
	}
  	function setMethodOk(){
  	//	if(values.length==0){
  	//			alert("请选择服务认证方式!!");
  	//		return;
  	//	}else{
  	  		var aa = document.getElementById("hiddenName").value;
			Ims.validStatus(aa,callBack);
  //	  	}
  	}
  	function callBack(data){
		if(data=="false"){
			alert("输入的认证错误,请仔细核对");
		}else{
			if((window.opener.document.getElementById("svrloginmethod").value).length==0&&(window.opener.document.getElementById("utiISvr.svrLoginMethod").value).length==0){
		  		window.opener.document.getElementById("svrloginmethod").value = document.getElementById("methods").value;
		  		window.opener.document.getElementById("utiISvr.svrLoginMethod").value = document.getElementById("hiddenName").value;
		  		window.close();
	  		}else{
	  			window.opener.document.getElementById("svrloginmethod").value = document.getElementById("methods").value;
		  		window.opener.document.getElementById("utiISvr.svrLoginMethod").value = document.getElementById("hiddenName").value;
	  			window.close();
	  	  	}
		}
  	}
  	
  	function cleanUp(){
  		document.getElementById("methods").value="";
  		document.getElementById("hiddenName").value="";
  	}
  </script>
  <body>
  	<div id="wrapper">
	<div id="container">
  	<form>
  	<div id="crash_menu">
  		<h2 align="center">请选择服务认证方式</h2>
  	</div>
  		<table align="center" class="fix_table">
  			<tr>
  				<td width="50%" rowspan="4" >
  					<textarea rows="5" name="methods" id="methods" readonly="true" ></textarea>
  				</td >
				<td colspan="4"><div align="center">方式名称</div></td>
			<tr>
  				<td width="30%">
  					<input type="button" name="card" class="button_ty" id="card" value="磁卡" onclick="setOperator(this)"/>
  					<input type="button" name="usbkey" class="button_ty" id="usbkey" value="USBKEY" onclick="setOperator(this)"/>
  					<input type="button" name="nameAndPwd" class="button_ty" id="nameAndPwd" value="账号密码" onclick="setOperator(this)"/>
  				</td>
	  		</tr>
	  		<tr>
	  			<td colspan="4"><div align="center">组合关系</div></td>
	  		</tr>
			<tr>
				<td width="30%">
					<input type="button" name="&" class="button_ty" id="and" value=" and " onclick="addSign(this);"/>
  					<input type="button" name="|" class="button_ty" id="or" value=" or " onclick="addSign(this);"/>
  					<input type="button" name="(" id="bracketsL" class="button_ty" value=" ( " onclick="addSign(this);"/>
					<input type="button" name=")" id="bracketsR" class="button_ty" value=" ) " style= "height:20px;width:30px" onclick="addSign(this);"  />
				</td>
				<input type="hidden" name="hiddenName" id="hiddenName" value="" />
			</tr>
  		</table>
  		<br>
  		<br>
  		<div align="center">
	  		<input type="button" class="button_ty" onclick="setMethodOk()" value="确定">
			<input type="button" class="button_ty" onclick="cleanUp()" value="清空">
		  	<input type="button" class="button_ty" onclick="window.close()" value="返回">
		</div>
  	</form>
  	</div>
  	</div>
  </body>
</html>