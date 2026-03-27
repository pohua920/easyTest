<%@page import="com.sinosoft.claim.common.web.LoginAction"%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<!-- mantis： OTH0113，處理人員：kelvin，需求單編號：OTH0113，登入時增加NFC讀卡機驗證、AD密碼驗證  start-->
<script src="<s:url value='/widgets/jquery/ui/jquery-ui-1.7.2.custom.js'/>" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" media="screen" href="<s:url value='/widgets/jquery/themes/ui-lightness/jquery-ui-1.7.2.custom.css'/>" />
<!-- mantis： OTH0113，處理人員：kelvin，需求單編號：OTH0113，登入時增加NFC讀卡機驗證、AD密碼驗證  end-->
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<%
	//mantis： CLM0110 ，處理人員：BK007 蘇哲，需求單編號：CLM0110.新核心-NFC讀卡機驗證新增開關
	request.setAttribute("isCardCheckOpen", LoginAction.isCardCheckOpen()) ;
	session.removeAttribute("user"); 
	

%>
<title><s:text name="title.pubBeforeEdit.claimsWorkflowSystem"/><%--理赔工作流系统 --%></title>
</head>
<body class="body_12" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form id="fm" name="fm" action="${ctx}/common/login.do" method="post">
	<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td align="center"><div class="main_12">
									<div class="logo_12">
										<%-- <img src="${ctx}/images/logo.png" height="63px" width="412px"/> --%>
										<!-- mantis： OTH0113，處理人員：kelvin，需求單編號：OTH0113，登入時增加NFC讀卡機驗證、AD密碼驗證  start-->
										<iframe src="sub.jsp" id="ifrm" width="1" height="1" ></iframe>
										<!-- mantis： OTH0113，處理人員：kelvin，需求單編號：OTH0113，登入時增加NFC讀卡機驗證、AD密碼驗證  end-->
										<img src="${ctx}/images/logo100.png" height="63px" width="412px"/>
									</div>
									<div class="cont_12">
										<div class="cont_bg_12">
											<div class="input_txt_12">
												<table width="100%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td width="30%" height="40" align="right"><s:text name="prompt.logon.prpDuserUserCode"/><%--用户名 --%>：</td>
														<td align="center"><input id="userCode" name="userCode" type="text"
															class="input1" maxlength="10"
															onBlur="reloadComCodeList()" />
															<input type="hidden" id="actionType" name="actionType" /> 
															<input type="hidden" id="systemCode" name="systemCode"  value="claim" />
															<!-- mantis： OTH0113，處理人員：kelvin，需求單編號：OTH0113，登入時增加NFC讀卡機驗證、AD密碼驗證  start-->
 															<!-- mantis： CLM0110 ，處理人員：BK007 蘇哲，需求單編號：CLM0110.新核心-NFC讀卡機驗證新增開關 start -->
															<c:choose>
																<c:when test="${isCardCheckOpen}">
																	<input type="hidden" id="cardNo" name="cardNo" />
																</c:when>
																<c:otherwise>
																	<input type="hidden" id="cardNo" name="cardNo" value="CLOSE"/>
																</c:otherwise>
															</c:choose>
															<!-- mantis： CLM0110 ，處理人員：BK007 蘇哲，需求單編號：CLM0110.新核心-NFC讀卡機驗證新增開關 end -->
															<!-- mantis： OTH0113，處理人員：kelvin，需求單編號：OTH0113，登入時增加NFC讀卡機驗證、AD密碼驗證  end-->
														</td>
													</tr>
													<tr>
														<td height="40" align="right"><s:text name="prompt.logon.prpDuserPassword"/><%--密 码 --%>：</td>
														<td align="center"><input id="password" name="password"
															type="password" class="input1" maxlength="20"
															onBlur="reloadComCodeList()"></td>
													</tr>
													<tr>
														<td height="40" align="right"><nobr><s:text name="db.prpDdbs.comCode"/><%--机 构 --%>：</nobr></td>
														<td align="center">
															<select id="comCode" name="comCode" class="input1" onFocus="reloadComCodeList()"></select>
														</td>
													</tr>
													<tr>
														<!-- mantis： OTH0113，處理人員：kelvin，需求單編號：OTH0113，登入時增加NFC讀卡機驗證、AD密碼驗證  start-->
														<td align="right" height="50" colspan="2">
															<img name="loginImage" id="loginId" src="${ctx}/images/main_butom.gif" width="199" height="24" style="cursor:hand"/>
														</td>
														<!-- mantis： OTH0113，處理人員：kelvin，需求單編號：OTH0113，登入時增加NFC讀卡機驗證、AD密碼驗證  end-->
													</tr>
												</table>
											</div>
										</div>
									</div>
									<div class="foot_12"><s:text name="pages.support"/><%--技术支持 中科软科技股份有限公司 --%></div>
									<!-- mantis： OTH0113，處理人員：kelvin，需求單編號：OTH0113，登入時增加NFC讀卡機驗證、AD密碼驗證  start-->
									<div id="dialog">
										<table>
											<tr align="center" >
												<td style="width:100%" colspan="2">
													請將識別證貼近NFC讀卡機<P>
													識別證感應後無作用時<P>
													請按『<font color="red">取消</font>』再按『<font color="red">登入</font>』後，<font color="red">再重新感應</font>！<P>
													<font color="red" >禁止使用他人帳號登入</font>！<P>
												</td>				
											</tr>
										</table> 
   
 									</div>
 									<!-- mantis： OTH0113，處理人員：kelvin，需求單編號：OTH0113，登入時增加NFC讀卡機驗證、AD密碼驗證  end-->
								</div></td>
						</tr>
					</table>
				</td>
			</tr>
	</table>
</form>
</body>
<script type="text/javascript" language="Javascript">
var oldUserCode = "";
function reloadComCodeList() {
	window.status = new Date();
	if (trim(fm.userCode.value) == oldUserCode) {
		return;
	}
	removeAllComCode();
	if (trim(fm.password.value) == "") {
		return;
	}
	oldUserCode = trim(fm.userCode.value);
	if (trim(fm.userCode.value).length >0) {
		var url = "${ctx}/common/processCodeInputS.do";
		var data = $("#fm").serializeArray();
		$.getJSON(url, data, processComCodeStateChange);
	}
}
function processComCodeStateChange(json) {
	var message = json.message;
	var startIndex = json.startIndex;
	var recordsReturned = json.totalRecords;
	var options = '';
	if(message != "success"){
		alert(message);
	}else{
		$("#comCode").empty();
		for(startIndex; startIndex<recordsReturned; startIndex++){
			options +=  "<option value='" + json.data[startIndex].comCode + "'>"+ json.data[startIndex].comCode + "-" + json.data[startIndex].comName +"</option>";
		}
		$('#comCode').html(options);
	}
}
function removeAllComCode() {
	$("#comCode").empty();
}

var intPageWidth = screen.availWidth;
var intPageHeight = screen.availHeight;
var ua = window.navigator.userAgent;
var msie = ua.indexOf("MSIE ");
window.name = 'MainWindow';
window.resizeTo(intPageWidth, intPageHeight);
window.focus();
/**
 * 是否是IE6
 * @since
 * @return 是返回ture，否则返回false
 */
function isIE6() {
	if (navigator.appVersion.indexOf("MSIE 6") > -1) {
		return true;
	} else {
		return false;
	}
}
function submitForm() {
	if (fm.password.value == "") {
		alert("請輸入密碼！");
		return false;
	}
	if (fm.comCode.value == "") {
		alert("請選擇登入機構。\n如果下拉列表中沒有選項，也許是您沒有引入任何機構，請與系統管理員聯系。");
		return false;
	}
	fm.actionType.value = "login";
}
//${ctx}/common/login.do
	
// mantis： OTH0113，處理人員：kelvin，需求單編號：OTH0113，登入時增加NFC讀卡機驗證、AD密碼驗證  start	
$(function () {
	
	var loginErrMsg = '<c:out value="${loginErrMsg}"/>';
	if(loginErrMsg != "" && loginErrMsg != null){
		alert(loginErrMsg);
	}
	
	$("#dialog").dialog({
		resizable: false,
		position : "center",
		autoOpen : false,
		height : "100px",
	    width : "350px",
	    modal : true,
	    draggable: false,
	    open: function() { 
	    	$("#ifrm").contents().find("#subTemp").val("");
	    	$("#ifrm").contents().find("#subTemp").focus();
	     },
		buttons: {
			"取消": function() {
				$(this).dialog("close");
				$("#ifrm").contents().find("#subTemp").val("");
			}
     	}
	});
	$("#dialog").click(function(){
		$("#ifrm").contents().find("#subTemp").focus();
	});
	$('#dialog').mouseover(function(){
		$("#ifrm").contents().find("#subTemp").focus();
	});
	$('#loginId').click(function(){
		if ($("#userCode").val() == "") {
			alert("請輸入員工代號！");
			return false;
		}
		if ($("#password").val() == "") {
			alert("請輸入密碼！");
			return false;
		}
		if ($("#comCode").val() == "" || $("#comCode").val() == null) {
			alert("請選擇登入機構。\n如果下拉列表中沒有選項，也許是您沒有引入任何機構，請與系統管理員聯系。");
			return false;
		}
		//<!-- mantis： CLM0110 ，處理人員：BK007 蘇哲，需求單編號：CLM0110.新核心-NFC讀卡機驗證新增開關 start -->
		<c:choose>
		<c:when test="${isCardCheckOpen}">
			$("#dialog").dialog("open");
		</c:when>
		<c:otherwise>
			$('#fm', window.parent.document).submit();
		</c:otherwise>
		</c:choose>
		//<!-- mantis： CLM0110 ，處理人員：BK007 蘇哲，需求單編號：CLM0110.新核心-NFC讀卡機驗證新增開關 end -->
	});

	$("#userCode").on("keyup", function (e) {
		$(this).val($(this).val().toUpperCase());
	});
});
// mantis： OTH0113，處理人員：kelvin，需求單編號：OTH0113，登入時增加NFC讀卡機驗證、AD密碼驗證  end
</script>
</html>