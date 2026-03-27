<%@page import="com.opensymphony.xwork2.util.ValueStack"%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/i18njs.jsp"%>

<html>
<head>
<script type="text/javascript">
 function showErrorMessage()
 {
	var error=document.getElementById('error');
	 if(error.style.display=="none")
		 {
		 error.style.display="";		 
		 }
	 else if(error.style.display=="")
	 {	
		 error.style.display="none"	 
	 }
	 
 }
 function isAllEmpty()
 {
	var error1=document.getElementById('error1').innerText;
	var error2=document.getElementById('error2').innerText;
	var error3=document.getElementById('error3').innerText;
	var message=document.getElementById('message');
	error1=trim(error1);
	error2=trim(error2);
	error3=trim(error3);
	if(error1==""&&error2==""&&error3=="")
	{
		message.style.display="";
	}
 }
 <!-- add by songzhewen 20170328 mantis5446: 验收问题：各系统密碼到期用舊密碼登入後直接跳密碼修改begin-->	
 function tiaozhuan(){
	 var error1=document.getElementById('error1').innerText;
	 var user=document.getElementById('user');
	 var date=document.getElementById('date');
	 var now=document.getElementById('now');
	 if(error1.indexOf("已经过期") != -1){
	        	 window.location.href="/undwrt/CommonUpdatePwd.jsp";
	    }else if(error1.indexOf("原密码输入不正确") != -1){
	    	 if(user!=null&&Date.parse(date)<Date.parse(now)==true){
	    	     window.location.href="/undwrt/common/CommonUpdatePwd.jsp";
	    	 }else{
	    	  window.location.href="/undwrt/CommonUpdatePwd.jsp";
	       }
	    }else{
	    	//mantis： CAR0387，處理人員：DP0706，車險關聯單報價單核保功能調整
	    	parent.location.reload();
	      }
	 }
 <!-- add by songzhewen 20170328 mantis5446: 验收问题：各系统密碼到期用舊密碼登入後直接跳密碼修改begin-->	
 </script>
<jsp:include page="/common/meta_css.jsp" />
<jsp:include page="/common/meta_js.jsp" />
</head>

<body onload="isAllEmpty()">
	<form name="fm" method="post">
		<table class="common">
			<tr class=listtitle>
				<td colspan="2"><s:text name='undwrt.systemHint'/></td>
			  <!-- add by songzhewen 20170328 mantis5446: 验收问题：各系统密碼到期用舊密碼登入後直接跳密碼修改begin-->	
				<td><input  type="hidden" value="${session.user}"  id="user" ></td>
				<td><input  type="hidden" value="${session.date}"  id="date" ></td>
				<td><input  type="hidden" value="${session.now}"  id="now" ></td>
               <!-- add by songzhewen 20170328 mantis5446: 验收问题：各系统密碼到期用舊密碼登入後直接跳密碼修改end-->	
			</tr>
			<tr class=common>
				<td>
					<img src='/undwrt/common/images/tanhao.gif' align="absmiddle" style="cursor: pointer;"onclick="showErrorMessage();">
					<span id="error1"><s:property value="exception.message" /></span><br>
					<span id="error2"><s:property value="exception.errorMessage" /></span><br>
					<span id="error3"><s:property value="exception.errorModule" /></span><br>
					<span id="message" style="display:none;"><s:text name='undwrt.errorMessage'/></span><br>
					<span id="error" style="display:none;"><s:property value="exceptionStack" /></span><br>
				</td>
			</tr>
			<tr class=common>
				<td align=center colspan="2">
				   <!-- modify by songzhewen 20170328 mantis5446: 验收问题：各系统密碼到期用舊密碼登入後直接跳密碼修改begin-->	
					<input type="button" class="longbutton" name="goOnDealTask" value="<s:text name='undwrt.goDealNextTask'/>"
					onclick="tiaozhuan();"/>
					<!-- modify by songzhewen 20170328 mantis5446: 验收问题：各系统密碼到期用舊密碼登入後直接跳密碼修改begin-->	
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
