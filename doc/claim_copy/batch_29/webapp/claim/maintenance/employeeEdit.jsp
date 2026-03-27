<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true">
<head>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<title>人員資料維護</title>
<%--mantis：CLM0125，處理人員：DP0713，需求單編號：新功能理賠人員資料維護 --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script type="text/javascript">
	function changeUserName(){
		if($("#editType").val()=="ADDED"){
			$("#editType").val("ADD");
		}
	}
	function saveEmployee(){
		var re = /^[A-Za-z0-9]+$/;
		if(!re.exec($("#userCode").val())){
			$("#actionerror").fadeIn(500,function(){
				$(this).find("em").text("員工編號僅能輸入英文及數字");
			});
			return false;
		}
		//mantis：CLM0178，處理人員：DP0713，需求單編號：新核心-功能理賠人員資料維護新增區塊鏈驗證欄位 START
		if($("#tel").val()==""){
			$("#actionerror").find("em").text("");
			$("#actionerror").fadeIn(500,function(){
				$(this).find("em").text("員工電話必須輸入");
			});
			return false;
		}
		if($("#ext").val()==""){
			$("#actionerror").find("em").text("");
			$("#actionerror").fadeIn(500,function(){
				$(this).find("em").text("員工分機必須輸入");
			});
			return false;
		}
		if($("#email").val()==""){
			$("#actionerror").find("em").text("");
			$("#actionerror").fadeIn(500,function(){
				$(this).find("em").text("員工信箱必須輸入");
			});
			return false;
		}
		var validRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
		if (!validRegex.exec($("#email").val())) {
			$("#actionerror").fadeIn(500,function(){
				$(this).find("em").text("員工信箱格式錯誤");
			});
			return false;
		}
		//mantis：CLM0178，處理人員：DP0713，需求單編號：新核心-功能理賠人員資料維護新增區塊鏈驗證欄位 END
		if(!TWIDCheck($("#id").val())){
			$("#actionerror").fadeIn(500,function(){
				$(this).find("em").text("員工身分證號");
			});
			return false;
		}
		if(isNaN($("#comcode").val())){
			$("#actionerror").fadeIn(500,function(){
				$(this).find("em").text("歸屬單位僅能輸入數字");
			});
			return false;
		}
		if($("#feeQuota").val()==""){
			$("#actionerror").find("em").text("");
			$("#actionerror").fadeIn(500,function(){
				$(this).find("em").text("員工車資上限金額(元)必須輸入");
			});
			return false;
		}
		if(isNaN($("#feeQuota").val())){
			$("#actionerror").find("em").text("");
			$("#actionerror").fadeIn(500,function(){
				$(this).find("em").text("員工車資上限金額(元)僅能輸入數字");
			});
			return false;
		}
		
		if($("#feeQuota").val().length>=6){
			$("#actionerror").find("em").text("");
			$("#actionerror").fadeIn(500,function(){
				$(this).find("em").text("員工車資上限金額(元)長度僅能輸入5位數");
			});
			return false;
		}
		$("#saveBtn").prop("disabled" , true);
		$("#actionerror").find("em").text("");
		var params = $("form").serialize();
		params= decodeURIComponent(params,true);
        params= encodeURI(encodeURI(params)); 
		$.ajax({
			type : "POST",
			url : contextRootPath + "/common/saveEmployee.do",
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
					//mantis：CLM0178，處理人員：DP0713，需求單編號：新核心-功能理賠人員資料維護新增區塊鏈驗證欄位
					$("#editType").val("EDIT");
					//$("#spanEditType").text("修改");
					var prpLuser = data.prpLuser;
					//$("#origBankCode").val(prpLuser.origBankCode);
					//mantis：CLM0178，處理人員：DP0713，需求單編號：新核心-功能理賠人員資料維護新增區塊鏈驗證欄位
					if(${ param.editType == "EDIT" }){
						window.returnValue = prpLuser;
					}
				}
			}
		});
	}
	// 台灣身份證字號格式檢查程式
	function TWIDCheck(value)
	{
	    var a = new Array('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'X', 'Y', 'W', 'Z', 'I', 'O');
	    var b = new Array(1, 9, 8, 7, 6, 5, 4, 3, 2, 1);
	    var c = new Array(2);
	    var d;
	    var e;
	    var f;
	    var g = 0;
	    var h = /^[a-z](1|2)\d{8}$/i;
	    if (value.search(h) == -1)
	    {
	        return false;
	    }
	    else
	    {
	        d = value.charAt(0).toUpperCase();
	        f = value.charAt(9);
	    }
	    for (var i = 0; i < 26; i++)
	    {
	        if (d == a[i])//a==a
	        {
	            e = i + 10; //10
	            c[0] = Math.floor(e / 10); //1
	            c[1] = e - (c[0] * 10); //10-(1*10)
	            break;
	        }
	    }
	    for (var i = 0; i < b.length; i++)
	    {
	        if (i < 2)
	        {
	            g += c[i] * b[i];
	        }
	        else
	        {
	            g += parseInt(value.charAt(i - 1)) * b[i];
	        }
	    }
	    if ((g % 10) == f)
	    {
	        return true;
	    }
	    if ((10 - (g % 10)) != f)
	    {
	        return false;
	    }
	    return true;
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
		<input type="hidden" id="createUser" name="prpLuser.createUser" value="${prpLuser.createUser}">
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="common">
			<!--mantis：CLM0178，處理人員：DP0713，需求單編號：新核心-功能理賠人員資料維護新增區塊鏈驗證欄位 START -->
			<tr>
				<td class="formtitle" colSpan="2">人員資料維護</td>
			</tr>
			<!--mantis：CLM0178，處理人員：DP0713，需求單編號：新核心-功能理賠人員資料維護新增區塊鏈驗證欄位 END -->
			<tr>
				<td class="title" style="width: 20%">員工編號:</td>
				<td class="input" style="width: 80%">
					<c:choose>
						<c:when test="${param.editType == 'ADD'}">
							<input type="text" maxlength="8" id="userCode" name="prpLuser.userCode" value="${prpLuser.userCode}" class="input" style="width: 120px;" onchange="changeUserName();">
						</c:when>
						<c:when test="${param.editType == 'EDIT'}">
							${prpLuser.userCode}<input type="hidden" maxlength="8" id="userCode" name="prpLuser.userCode" value="${prpLuser.userCode}" style="width: 120px;">
						</c:when>
						<c:otherwise></c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td class="title" style="width: 20%">員工姓名:</td>
				<td class="input" style="width: 80%">
					<input type="text" maxlength="20" id="userName" name="prpLuser.userName" value="${prpLuser.userName}" class="input" >
				</td>
			</tr>
			<tr>
				<td class="title" style="width: 20%">員工身分證號:</td>
				<td class="input" style="width: 80%">
					<input type="text" maxlength="10" id="id" name="prpLuser.id" value="${prpLuser.id}" class="input" >
				</td>
			</tr>
			<tr>
				<td class="title" style="width: 20%">工作地點:</td>
				<td class="input" style="width: 80%">
					<input type="text" maxlength="6" id="workPlaceNm" name="prpLuser.workPlaceNm" value="${prpLuser.workPlaceNm}" class="input" >
				</td>
			</tr>
			<tr>
				<td class="title" style="width: 20%">歸屬單位:</td>
				<td class="input" style="width: 80%">
					<input type="text" maxlength="2" id="comcode" name="prpLuser.comcode" value="${prpLuser.comcode}" class="input" >
				</td>
			</tr>
			<!-- mantis：CLM0178，處理人員：DP0713，需求單編號：新核心-功能理賠人員資料維護新增區塊鏈驗證欄位 START -->
			<tr>
				<td class="title" style="width: 20%">員工信箱:</td>
				<td class="input" style="width: 80%">
					<input type="text" maxlength="40" id="email" name="prpLuser.email" value="${prpLuser.email}" class="input" >
				</td>
			</tr>
			<tr>
				<td class="title" style="width: 20%">員工電話:</td>
				<td class="input" style="width: 80%">
					<input type="text" maxlength="12" id="tel" name="prpLuser.tel" value="${prpLuser.tel}" class="input" >
				</td>
			</tr>
			<tr>
				<td class="title" style="width: 20%">員工分機:</td>
				<td class="input" style="width: 80%">
					<input type="text" maxlength="10" id="ext" name="prpLuser.ext" value="${prpLuser.ext}" class="input" >
				</td>
			</tr>
			<!-- mantis：CLM0178，處理人員：DP0713，需求單編號：新核心-功能理賠人員資料維護新增區塊鏈驗證欄位 END -->
			<tr>
				<td class="title" style="width: 20%">員工車資上限金額(元):</td>
				<td class="input" style="width: 80%">
					<input type="text" maxlength="5" id="feeQuota" name="prpLuser.feeQuota" value="<fmt:formatNumber value='${prpLuser.feeQuota}' pattern='#'/>" class="input" >
				</td>
			</tr>
			<tr>
				<td class="title" style="width: 20%">狀態:</td>
				<td class="input" style="width: 80%">
					<input type="radio" name="prpLuser.userFlag" value="0" <c:if test="${prpLuser.userFlag=='0'}">checked="checked"</c:if> >&nbsp;無效&nbsp;&nbsp;
					<input type="radio" name="prpLuser.userFlag" value="1" <c:if test="${prpLuser.userFlag=='1'}">checked="checked"</c:if> >&nbsp;有效&nbsp;&nbsp;
				</td>
			</tr>
		<c:choose>
			<c:when test="${param.editType == 'ADD'}">
				<input type="hidden" maxlength="150" id="updateRec" name="param.updateRec" value="新增" >
			</c:when>
			<c:when test="${param.editType == 'EDIT'}">
			<tr>
				<td class="title" style="width: 20%" >修改原因註記:</td>
				<td class="input" style="width: 80%">
					<input type="text" maxlength="100" id="updateRec" name="param.updateRec" value="${param.updateRec}" style="width: 90%;">
				</td>
			</tr>
			</c:when>
			<c:otherwise></c:otherwise>
		</c:choose>
			<tr>
				<td class='button' colspan="4">
					<input type="button" class="button" id="saveBtn" value="儲存" style="cursor: hand" onClick="saveEmployee();">&nbsp;&nbsp;
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