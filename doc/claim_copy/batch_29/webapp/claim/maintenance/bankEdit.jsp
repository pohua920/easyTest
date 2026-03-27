<%--
****************************************************************************
* DESC       ：代理人手机号码录入界面
* AUTHOR     ： 理赔组 陈杰
* CREATEDATE ： 2013-03-08
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true">
<head>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<title>銀行代碼維護</title>
<%--代理人手机号码录入页面 --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script type="text/javascript">
	function saveBank(){
		$("#saveBtn").prop("disabled" , true);
		$("#actionerror").find("em").text("");
		var params = $("form").serialize();
		params= decodeURIComponent(params,true);
        params= encodeURI(encodeURI(params)); 
		$.ajax({
			type : "POST",
			url : contextRootPath + "/common/saveBank.do",
			data : params,
			async : false,
			cache : false,
			dataType : "json",
			success : function(data){
				if (data.msg) {
					$("#actionerror").find("em").text("");
					$("#actionerror").fadeIn(500,function(){
						$(this).find("em").text(data.msg);
					});
				} else {
					$("#actionsuccess").find("em").text("");
					$("#actionsuccess").fadeIn(500,function(){
						$(this).find("em").text("存儲成功！");
					}).fadeOut(2000);
					$("#editType").val("EDIT");
					$("#spanEditType").text("修改");
					var prpLbank = data.prpLbank;
					$("#origBankCode").val(prpLbank.origBankCode);
					if(prpLbank.bankLevel == "1"){//增加或編輯總行時，同步總行資料（總行的 代碼一致）
						$("#bankCode").val(prpLbank.id.bankCode);
						$("#bankCName").val(prpLbank.bankCName);
					}
					if(${ param.editType == "EDIT" }){
						window.returnValue = prpLbank;
					}
				}
			}
		});
	}
	$(function(){
		$(":input").ajaxStart(function(){
			$(this).prop("disabled" , true);
		 }).ajaxComplete(function(){
			$(this).prop("disabled" , false);
		 });
	});
</script>
</head>
<body >
	<form name="fm" action="${ctx}/common/saveBank.do" method="post" >
		<input type="hidden" name="editType" id="editType" value="${param.editType}">
		<input type="hidden" name="prpLbank.bankLevel" value="${prpLbank.bankLevel}" >
		<input type="hidden" name="prpLbank.origBankCode" id="origBankCode" value="${prpLbank.origBankCode}" >
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="common">
			<tr>
				<td class="formtitle" colspan="2">
					銀行代碼<span id="spanEditType"><c:if test="${param.editType=='ADD'}">新增</c:if><c:if test="${param.editType=='EDIT'}">修改</c:if></span>
				</td>
			</tr>
			<tr>
				<td class="title" style="width: 20%">總行代碼:</td>
				<td class="input" style="width: 80%">
					<c:choose>
						<c:when test="${prpLbank.bankLevel == '1'}">
							<input type="text" maxlength="3" id="upperBankCode" name="prpLbank.id.upperBankCode" value="${prpLbank.id.upperBankCode}" class="input" style="width: 120px;">
						</c:when>
						<c:when test="${prpLbank.bankLevel == '2'}">
							<input type="text" maxlength="3" id="upperBankCode" name="prpLbank.id.upperBankCode" value="${prpLbank.id.upperBankCode}" class="readonly" readonly="readonly" style="width: 120px;">
						</c:when>
						<c:otherwise></c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td class="title" style="width: 20%">總行名稱:</td>
				<td class="input" style="width: 80%">
					<c:choose>
						<c:when test="${prpLbank.bankLevel == '1'}">
							<input type="text" maxlength="200" id="upperBankCName" name="prpLbank.upperBankCName" value="${prpLbank.upperBankCName}" class="input" >
						</c:when>
						<c:when test="${prpLbank.bankLevel == '2'}">
							<input type="text" maxlength="200" id="upperBankCName" name="prpLbank.upperBankCName" value="${prpLbank.upperBankCName}" class="readonly" readonly="readonly">
						</c:when>
						<c:otherwise></c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr <c:if test="${prpLbank.bankLevel == '1'}"> style="display: none" </c:if> >
				<td class="title" style="width: 20%">分行代碼:</td>
				<td class="input" style="width: 80%">
					<input type="text" maxlength="7" id="bankCode" name="prpLbank.id.bankCode" value="${prpLbank.id.bankCode}" class="input" style="width: 120px;">
				</td>
			</tr>
			<tr <c:if test="${prpLbank.bankLevel == '1'}"> style="display: none" </c:if> >
				<td class="title" style="width: 20%">分行名稱:</td>
				<td class="input" style="width: 80%">
					<input type="text" maxlength="200" id="bankCName" name="prpLbank.bankCName" value="${prpLbank.bankCName}" class="input" >
				</td>
			</tr>
			<tr>
				<td class="title" style="width: 20%">簡稱:</td>
				<td class="input" style="width: 80%">
					<input type="text" maxlength="200" id="bankShortName" name="prpLbank.bankShortName" value="${prpLbank.bankShortName}" class="input" >
				</td>
			</tr>
			<tr>
				<td class="title" style="width: 20%">狀態:</td>
				<td class="input" style="width: 80%">
					<input type="radio" name="prpLbank.validstatus" value="0" <c:if test="${prpLbank.validstatus=='0'}">checked="checked"</c:if> >&nbsp;無效&nbsp;&nbsp;
					<input type="radio" name="prpLbank.validstatus" value="1" <c:if test="${prpLbank.validstatus=='1'}">checked="checked"</c:if> >&nbsp;有效&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<td class='button' colspan="4">
					<input type="button" class="button" id="saveBtn" value="存儲" style="cursor: hand" onClick="saveBank();">&nbsp;&nbsp;
					<input type="button" class="button" value="關閉" style="cursor: hand" onclick="window.close();" >
				</td>
			</tr>
		</table>
		<div align="left" style="display: none" id="actionsuccess">
			<b><em style="color: blue;">&nbsp;&nbsp;</em></b>
		</div>
		<div align="left" style="display: none" id="actionerror">
			<b><em style="color: red;">&nbsp;&nbsp;</em></b>
		</div>
	</form>
</body>
</html>