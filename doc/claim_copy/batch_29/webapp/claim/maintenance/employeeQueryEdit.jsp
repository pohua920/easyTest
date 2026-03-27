<%@page import="java.util.ArrayList"%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<!-- mantis：CLM0125，處理人員：DP0713，需求單編號：新功能理賠人員資料維護  -->
<title>人員資料維護</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
	<style type="text/css">
		.trblue {
			background-color: #D9FCF4 ;
		}
	</style>
	<script language="Javascript" src="/claim/common/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript">
		function employeeQuery(f){
			$("#actionerror").fadeOut(100);
			$("#queryType").prop("disabled",f==1);
			if(f && fm.pageNo){//調整條件重新查詢
				fm.pageNo.value = "1";
			}
			var params = $("form").serialize();
			params= decodeURIComponent(params,true);
            params= encodeURI(encodeURI(params)); 
			$.ajax({
				url: contextRootPath + "/common/employeeQuery.do",
				type : "POST",
				cache: false,
				dataType : "html",
				data : params,
				success: function(html){
					$("#employeeloading").empty().append(html);
					$(":radio[name='cbx']").click(function(){
						if(this.checked){
							$(this).closest("tr").addClass("trblue").siblings("tr").removeClass("trblue");
						}
					});
				}
			});
		}
		
		
		function locate(pageNo) {
			$("#actionerror").fadeOut(100);
			if (pageNo < 1) {
				alert("\u5df2\u5230\u7b2c\u4e00\u9875");
				return false;
			}
			if (pageNo > parseInt(getFirstElementValue("pagesCount"), 10)) {
				alert("\u5df2\u5230\u6700\u540e\u4e00\u9875");
				return false;
			}
			if (pageNo == 1 && parseInt(fm.pageNo.value, 10) == 1) {
				alert("\u5df2\u5230\u7b2c\u4e00\u9875");
				return false;
			}
			if (pageNo == 1
					&& pageNo == parseInt(getFirstElementValue("pagesCount"),
							10)) {
				alert("\u5df2\u5230\u6700\u540e\u4e00\u9875");
				return false;
			}
			fm.pageNo.value = pageNo;
			employeeQuery(0);
		}

		function addEmployee(bankLevel) {
			//mantis：CLM0178，處理人員：DP0713，需求單編號：新核心-功能理賠人員資料維護新增區塊鏈驗證欄位
			var height = 370;
			var width = 600;
			var editType = "ADD";
			var url = contextRootPath + "/common/employeeBeforeEdit.do?editType="
					+ editType;
			var $cbx = $(":radio[checked][name='cbx']");
			$("#actionerror").fadeOut(100);
			var returnObj = window.showModalDialog(url, window, "dialogHeight:"
					+ height + "px;dialogWidth:" + width
					+ "px;help:no;resizable:yes;status:no;scroll:yes;");
			if (returnObj) {

			}
		}

		function editEmployee() {
			debugger;
			//mantis：CLM0178，處理人員：DP0713，需求單編號：新核心-功能理賠人員資料維護新增區塊鏈驗證欄位
			var height = 370;
			var width = 600;
			var editType = "EDIT";
			var url = contextRootPath + "/common/employeeBeforeEdit.do?editType="
					+ editType;
			var $cbx = $(":radio[checked][name='cbx']");
			if ($cbx.length == 0) {
				$("#actionerror").find("em").text("");
				$("#actionerror").fadeIn(500, function() {
					$(this).find("em").text("請選擇要修改的資料！");
				});
				return false;
			} else {
				$("#actionerror").fadeOut(100);
				var cbx = $cbx.val().split(",");
				//var id = cbx[0];//暫時不用
				var userCode = cbx[1];
				url += "&userCode=" + userCode;
			}
			var returnObj = window.showModalDialog(url, window, "dialogHeight:"
					+ height + "px;dialogWidth:" + width
					+ "px;help:no;resizable:yes;status:no;scroll:yes;");
			if (returnObj) {
				debugger;
				var prpLuser = returnObj;
				var $td = $cbx.closest("tr").children();
				$cbx
						.prop(
								"value",
								(prpLuser.id + ","+prpLuser.userCode));
				$td.eq(1).text(prpLuser.userCode);
				$td.eq(2).text(prpLuser.userName);
				$td.eq(3).text(prpLuser.workPlaceNm);
				$td.eq(4).text(prpLuser.comcode);
				$td.eq(6).text(prpLuser.feeQuota);
				$td.eq(8).text((prpLuser.userFlag == "1" ? "有效" : "無效"));
			}
		}
	</script>
</head>
<body style="overflow: auto;">
	<form name="fm" id="fm" method="post">
		<input type="hidden" name="queryType" id="queryType" value="querycontinue">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td class="formtitle" colspan="4">理賠人員資料查詢頁面</td>
			</tr>
			<tr >
				<td class="title">員工姓名：</td>
				<!-- 赔款计算书号 -->
				<td class="input">
					<select class="tag" name="queryUserNameSign">
						<option value="=" selected="selected">=</option>
						<option value="=*">=*</option>
					</select>
					<input type="text" name="queryUserName" class="query" value="" maxlength="3">
				</td>
				<td class="title">員工代碼：</td>
				<td class="input">
					<select class="tag" name="queryUserCodeSign">
						<option value="=" selected="selected">=</option>
						<option value="=*">=*</option>
					</select>
					<input type="text" name="queryUserCode" class="query" value="" maxlength="40">
				</td>
			</tr>
			<tr>
				<td class="title">工作地點：</td>
				<!-- 赔款计算书号 -->
				<td class="input">
					<select class="tag" name="queryWorkPlaceNmSign">
						<option value="=" selected="selected">=</option>
						<option value="=*">=*</option>
					</select>
					<input type="text" name="queryWorkPlaceNm" class="query" value="" maxlength="7">
				</td>
				<td class="title">歸屬單位：</td>
				<td class="input">
					<select class="tag" name="queryComcodeSign">
						<option value="=" selected="selected">=</option>
						<option value="=*">=*</option>
					</select>
					<input type="text" name="queryComcode" class="query" value="" maxlength="40">
				</td>
			</tr>
			<tr>
				<td class="title">車資上限金額：</td>
				<td class="input">
					<select class="tag" name="queryFeeQuotaSign">
						<option value="=" selected="selected">=</option>
						<option value="=*">=*</option>
					</select>
					<input type="text" name="queryFeeQuota" class="query" value="" maxlength="10">
				</td>
				<td class="title">狀態：</td>
				<td class="input">
					<input type="radio" name="queryUserFlag" value="0" >&nbsp;無效&nbsp;&nbsp;
					<input type="radio" name="queryUserFlag" value="1" checked="checked" >&nbsp;有效&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<td class="button" colspan="4">
					<input type="button" class="button" value="<s:text name='button.query.value' />" onclick="employeeQuery(1);">
				</td>
			</tr>
		</table>
		<table width="98%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" class="button">
					<div align="left" style="width:450px;float: left;">
						<input type="button" class="button" value="新增人員" onclick="addEmployee();"/>
						<input type="button" class="button" value="修改資料" onclick="editEmployee();"/>
					</div>
					<div align="left" style="width:100px;float: left;display: none" id="actionsuccess">
						<b><em style="color: blue;"></em></b>
					</div>
					<div align="left" style="width:300px;float: left;display: none" id="actionerror">
						<b><em style="color: red;"></em></b>
					</div>
				</td>
			</tr>
			<tr>
				<td id="employeeloading"></td>
			</tr>
		</table>
	</form>
</body>
</html>