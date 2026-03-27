<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.IConstants"%>
 <%-- moidfy  update by tongziliang 2011-09-29 reason:修改页面的按钮样式和优化页面样式 --%>
<link rel="stylesheet" href="${ctx}/style/popup/login.css" type="text/css"></link>
<html>
<head>
<title>账户管理</title>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>

</head>
<body id="all_title">
<div id="wrapper">
<div id="container">
<div id="crash_menu">
<h2 align="center">请输入新添账户信息</h2>
</div>
<s:form name="fm" action="" target="accountTreeRight">
	<input type="hidden" name="userCode" value="${userCode}"/>
	<input type="hidden" name="userName" value="${userName }"/>
	<input type="hidden" name="svrName" value="${svrName }"/>
	<input type="hidden" name="svrCode" id="svrCode" value=""/>	
	<input type="hidden" name="utiIAccount.comCode" value="${comCode}">
	<s:hidden name="accSort" id="accSort" value="${accSort }"/>
	<table class="fix_table">	
		<tr>
			<td class="bgc_tt short">账号代码<font color="red">*</font></td>
			<td class="long"><input type="text" name="utiIAccount.accCode"  class='input_w w_30' maxlength="32" value="${newaccCode}" readonly></td>			
			<td class="bgc_tt short">账号名称<font color="red">*</font></td>
			<td class="long"><input type="text" name="utiIAccount.accName"  id="accName" class='input_w w_30 dc-chk' maxlength="30" id="accName"></td>
		</tr>
		<tr>
			<td class="bgc_tt short">账号密码<font color="red">*</font></td>			
			<td class="long"><input type="password" name="utiIAccount.password"  class='input_w w_30 dc-chk' maxlength="10" id="password1"></td>		
			<td class="bgc_tt short">确认密码<font color="red">*</font></td>
			<td class="long">
				<input type="password" name="password2"  class='input_w w_30 dc-chk' maxlength="10" id="password2"></td>
		</tr>
		<tr>
			<td class="bgc_tt short">服务代码<font color="red">*</font></td>			
			<td class="long">
<!--					<div id="validStatusMapDiv" class="selectui-indiv  dc-chk">-->
<!--			        <div class="selectConfig">-->
<!--			        <div class="codeType">StaticSelect</div>-->
<!--			        </div>-->
<!--			        <c:set var="checked" value="0" />-->
<!--			  			<ce:select name="sCode" id="sCode" cssClass="selectui-input-up input_w w_15" value="${sCode}" onchange="checkURL();" list="svrList" disabled="true"/>-->
<!--			    	</div>-->
					<input type="text" name="sCode" class='input_w w_15' maxlength="30" value="${sCode }" readonly>
			    </td>			
			<td class="bgc_tt short">服务名称</td>
			<td class="long"><input type="text" name="utiIAccount.svrName"  class='input_w w_30' maxlength="30" value="${svrName }" readonly></td>
		</tr>		
		<tr>
			<td class="bgc_tt short">用户代码</td>			
			<td class="long"><input type="text" name="utiIAccount.userCode"  class='input_w w_30' maxlength="30"  value="${userCode}" readonly></td>			
			<td class="bgc_tt short">用户名称</td>
			<td class="long"><input type="text" name="utiIAccount.userName"  class='input_w w_30' maxlength="30"  value="${userName }" readonly></td>
		</tr>
		<tr>
			<td class="bgc_tt short">有效截止日期</td>
			<td class="long"><input maxlength="20" readonly name="utiIAccount.validendDate"  class='input_w w_30 Wdate' id="validenddate"  onFocus="WdatePicker()">
			</td>
			<td class="bgc_tt short">原系统账号代码</td>			
			<td class="long"><input type="text" name="utiIAccount.faccCode"  class='input_w w_30' maxlength="32"  value="${accCode }" readonly></td>	
		</tr>			
		${requestScope.table}	
		<tr align="center">
			<td align="center"  colspan="4">
			<button type="button"  value="" onclick="addAccount()"><span><em>保存</em></span></button>
<!--                <input type="button" class="button_ty" value="保存" onclick="addAccount()">-->
            </td>
		</tr>
	</table>
</s:form></div>
<div id="content_navigation" class="query" align="center"></div>
<div id="content" class="sort"></div>
<div id="content_navigation" class="query" align="center"></div>
</div>
</body>
</html>
<%@ include file="/common/meta_js.jsp"%>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script type='text/javascript' src='/ims/dwr/interface/Ims.js'></script>
<script language="javascript" src="/ims/widgets/yui/autocomplete/autocomplete-min.js"></script>
<script type="text/javascript">
	function addAccount(){
		 //var svrCode = document.getElementById("svrCode").value;
		 var accName = document.getElementById("accName").value;
			var i = 0;
			for(var j=0;j<accName.length;j++){
				 if(accName.charAt(j)<='\255') {   
					 i++;
				  }else {   
					  i=i+3;
				 }	
			}
			if(i > 30){
				alert("账户名称过长，请检查！");	
				return false;
			}
			var password1 = document.getElementById("password1").value;
			var password2 = document.getElementById("password2").value;
			if(password1 != password2){
				alert('两次输入密码不一致！');
				return false;
			}
		 var sCode = document.getElementById("sCode").value;
		 if(sCode!=null||sCode!=""){
		 document.getElementById("svrCode").value = sCode;
	   // if(svrCode == '请选择...' || sCode == 0){
		//	alert('请选择服务代码！');
		//	return false;
		 //}else{
			 if(checkForm()){
					fm.action = "${ctx}/utiIAccount/addAccount.do";
					fm.target="utiIUserQueryRight";
					fm.submit();
			}else{
				alert("界面输入有误，请核实！");
				return false;
			}
		 }else{
			alert("服务代码有误");
			return false;
		 }
	}

	function checkURL(){
		var code = fm.document.getElementById("sCode").options[fm.document.getElementById("sCode").selectedIndex].text;
		var scode = code.split("-");
		alert("scode");
		if(scode[0]=="请选择..."){
			document.getElementById("utiIAccount.svrName").value = "";
			document.getElementById("svrCode").value = '请选择...';
		}else{
			document.getElementById("svrCode").value = scode[1];
			document.getElementById("utiIAccount.svrName").value = scode[0];
		}
	}

	function checkForm(){
		return YAHOO.quote.data.datacheck('fm');
		}
	//var accName = document.getElementById("accName").value;
	//if(accName == ""){
	//	alert("账号名称不能为空！");
  	//	return false;
  	//}
	//var password = document.getElementById("password").value;
	//if(password.length < 6 || password.length > 20){
	//	alert("密码长度应该在6-20位之间！");
	//	return false;
	//}
	//var validenddate = document.getElementById("validenddate").value;
	//if(validenddate == ""){
	//	alert("请选择有效日期！");
	//	return false;
	//}
	//	if(YAHOO.quote.data.datacheck('fm')){
	//		if(checkLen()){
	//			fm.action = "${ctx}/utiIAccount/addAccount.do";
	//			fm.submit();
	//		}
	//	}else{
	//		alert("界面输入有误，请核实！");
	//	}
	//}
</script>