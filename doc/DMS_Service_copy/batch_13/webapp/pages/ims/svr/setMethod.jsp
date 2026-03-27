<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
  <head>
  	<title>服务认证方式</title>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<script type="text/javascript" src="${ctx}/common/js/sinosoft.js"></script>
<script type="text/javascript" src="${ctx}/common/js/MulLine.js"></script>
<script type="text/javascript" src="${ctx}/common/js/Common.js"></script>
<script type="text/javascript" src="${ctx }/common/dtree/dtree.js"></script>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
  </head>
  <script type="text/javascript">
  	var values="";
  	var names="";
  	function setMethod(){ 
  		names="";	
  		values="";	
  		var checkboxs = document.getElementsByName("checkboxs");
  		for(var i=0;i<checkboxs.length;i++){
  			if(checkboxs[i].checked){
  				values = values+checkboxs[i].value+",";
  				names = names+document.getElementById(checkboxs[i].value).innerHTML+",";
  			}
  		}
  		if(names.length>0){
  			names=names.substring(0,names.length-1);
  			values=values.substring(0,values.length-1);
  		}
  		document.getElementById("methods").value=names;
  	}
  	function setMethodOk(){
  		if(names.length==0){
  			alert("请选择方式!!!");
  			return;
  		}
  		window.opener.document.getElementById("svrloginmethod").value=names;
  		window.opener.document.getElementById("utiISvr.svrloginmethod").value=values;
  		window.close();
  	}
  </script>
  <body>
  	<form action="">
  	<div id="crash_menu">
  	<h2 align="center">请选择服务认证方式</h2>
  	</div>
  		<table align="center">
  			<tr>
  				<td>
  					<input  type="text" name="methods" id="methods" cssClass='input_w w_15' size="30" readonly >
  				</td>
  				<td>
  					<input type="checkbox" name="checkboxs" value="method1" onclick="setMethod()"><span id="method1">磁卡</span><br>
  					<input type="checkbox" name="checkboxs" value="method2" onclick="setMethod()"><span id="method2">CA</span><br>
  					<input type="checkbox" name="checkboxs" value="method3" onclick="setMethod()"><span id="method3">账号密码</span><br>
  				</td>
	  			</tr>
  		</table>
  		<br>
  		<br>
  		<div align="center">
	  		<input type="button" class="button_ty" onclick="setMethodOk()" value="确定">
		  	<input type="button" class="button_ty" onclick="window.close()" value="返回">
		</div>
  	</form>
  </body>
</html>
