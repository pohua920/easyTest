<%@page import="java.util.ArrayList"%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<title>抵押權人維護</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
	<style type="text/css">
		.trblue {
			background-color: #D9FCF4 ;
		}
	</style>
	<script language="Javascript" src="/claim/common/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript">
		function bankQuery(f){
			$("#actionerror").fadeOut(100);
			$("#queryType").prop("disabled",f==1);
			if(f && fm.pageNo){//調整條件重新查詢
				fm.pageNo.value = "1";
			}
			var params = $("form").serialize();
			params= decodeURIComponent(params,true);
            params= encodeURI(encodeURI(params)); 
			$.ajax({
				url: contextRootPath + "/common/bankQuery.do",
				type : "POST",
				cache: false,
				dataType : "html",
				data : params,
				success: function(html){
					$("#bankloading").empty().append(html);
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
			bankQuery(0);
		}

		function addBank(bankLevel) {
			var height = 300;
			var width = 600;
			var editType = "ADD";
			var url = contextRootPath + "/common/bankBeforeEdit.do?editType="
					+ editType + "&bankLevel=" + bankLevel;
			var $cbx = $(":radio[checked][name='cbx']");
			if (bankLevel == "2") {
				if ($cbx.length == 0) {
					$("#actionerror").find("em").text("");
					$("#actionerror").fadeIn(500, function() {
						$(this).find("em").text("請勾選新增分行的總行代碼！");
					});
					return false;
				} else {
					var cbx = $cbx.val().split(",");
					var bankCode = cbx[0];
					var upperBankCode = cbx[1];
					var bankLevel = cbx[2];
					if (bankLevel != "1") {
						$("#actionerror").find("em").text("");
						$("#actionerror").fadeIn(500, function() {
							$(this).find("em").text("請勾選新增分行的總行代碼！");
						});
						return false;
					}
					url += "&bankCode=" + bankCode + "&upperBankCode="
							+ upperBankCode;
				}
			}
			$("#actionerror").fadeOut(100);
			var returnObj = window.showModalDialog(url, window, "dialogHeight:"
					+ height + "px;dialogWidth:" + width
					+ "px;help:no;resizable:yes;status:no;scroll:yes;");
			if (returnObj) {

			}
		}

		function editBank() {
			var height = 300;
			var width = 600;
			var editType = "EDIT";
			var url = contextRootPath + "/common/bankBeforeEdit.do?editType="
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
				var bankCode = cbx[0];
				var upperBankCode = cbx[1];
				url += "&bankCode=" + bankCode + "&upperBankCode="
						+ upperBankCode;
			}
			var returnObj = window.showModalDialog(url, window, "dialogHeight:"
					+ height + "px;dialogWidth:" + width
					+ "px;help:no;resizable:yes;status:no;scroll:yes;");
			if (returnObj) {
				var prpLbank = returnObj;
				var $td = $cbx.closest("tr").children();
				$cbx
						.prop(
								"value",
								(prpLbank.id.bankCode + ","
										+ prpLbank.id.upperBankCode + "," + prpLbank.bankLevel));
				$td.eq(1).text((prpLbank.bankLevel == '2' ? "分行" : "總行"));
				$td.eq(2).text(prpLbank.id.upperBankCode);
				$td.eq(3).text(prpLbank.upperBankCName);
				if (prpLbank.bankLevel == '2') {
					$td.eq(4).text(prpLbank.id.bankCode);
					$td.eq(5).text(prpLbank.bankCName);
				}
				$td.eq(6).text((prpLbank.validstatus == "1" ? "有效" : "無效"));
			}
		}

		function resetBank(editType, validstatus) {
			var $cbx = $(":radio[checked][name='cbx']");
			if ($cbx.length == 0) {
				$("#actionerror").find("em").text("");
				$("#actionerror")
						.fadeIn(
								2000,
								function() {
									$(this)
											.find("em")
											.text(
													"請選擇要的"
															+ (validstatus == "1" ? "恢復"
																	: "刪除")
															+ "的資料！");
								});
				return false;
			} else {
				$("#actionerror").fadeOut(5000);
				var cbx = $cbx.val().split(",");
				var bankCode = cbx[0];
				var upperBankCode = cbx[1];
				$.ajax({
					url : contextRootPath + "/common/saveBank.do",
					type : "POST",
					cache : false,
					dataType : "json",
					data : {
						"editType" : editType,
						"prpLbank.id.bankCode" : bankCode,
						"prpLbank.id.upperBankCode" : upperBankCode,
						"prpLbank.validstatus" : validstatus
					},
					success : function(data) {
						if (data.msg) {
							$("#actionerror").find("em").text("");
							$("#actionerror").fadeIn(2000, function() {
								$(this).find("em").text(data.msg);
							}).fadeOut(10000);
						} else {
							$("#actionsuccess").find("em").text("");
							$("#actionsuccess").fadeIn(1000, function() {
								$(this).find("em").text("操作成功！");
							}).fadeOut(5000);
							$cbx.closest("tr").find("td[name='tdstatus']")
									.html((validstatus == "1" ? "有效" : "無效"));

						}
					}
				});
			}
		}
	</script>
</head>
<body style="overflow: auto;">
	<form name="fm" id="fm" method="post">
		<input type="hidden" name="queryType" id="queryType" value="querycontinue">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td class="formtitle" colspan="4">查詢條件</td>
			</tr>
			<tr >
				<td class="title">總行代碼：</td>
				<!-- 赔款计算书号 -->
				<td class="input">
					<select class="tag" name="queryUpperBankCodeSign">
						<option value="=" selected="selected">=</option>
					</select>
					<input type="text" name="queryUpperBankCode" class="query" value="" maxlength="3">
				</td>
				<td class="title">總行名稱：</td>
				<td class="input">
					<select class="tag" name="queryUpperBankCNameSign">
						<option value="=" selected="selected">=</option>
						<option value="=*">=*</option>
					</select>
					<input type="text" name="queryUpperBankCName" class="query" value="" maxlength="40">
				</td>
			</tr>
			<tr>
				<td class="title">分行代碼：</td>
				<!-- 赔款计算书号 -->
				<td class="input">
					<select class="tag" name="queryBankCodeSign">
						<option value="=" selected="selected">=</option>
						<option value="=*">=*</option>
					</select>
					<input type="text" name="queryBankCode" class="query" value="" maxlength="7">
				</td>
				<td class="title">分行名稱：</td>
				<td class="input">
					<select class="tag" name="queryBankCNameSign">
						<option value="=" selected="selected">=</option>
						<option value="=*">=*</option>
					</select>
					<input type="text" name="queryBankCName" class="query" value="" maxlength="40">
				</td>
			</tr>
			<tr>
				<td class="title">級別：</td>
				<!-- 赔款计算书号 -->
				<td class="input">
					<input type="radio" name="queryBankLevel" value="1" checked="checked" >&nbsp;總行&nbsp;&nbsp;
					<input type="radio" name="queryBankLevel" value="2">&nbsp;分行&nbsp;&nbsp;
				</td>
				<td class="title">狀態：</td>
				<td class="input">
					<input type="radio" name="queryValidstatus" value="0" >&nbsp;無效&nbsp;&nbsp;
					<input type="radio" name="queryValidstatus" value="1" checked="checked" >&nbsp;有效&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<td class="button" colspan="4">
					<input type="button" class="button" value="<s:text name='button.query.value' />" onclick="bankQuery(1);">
				</td>
			</tr>
		</table>
		<table width="98%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" class="button">
					<div align="left" style="width:450px;float: left;">
						<input type="button" class="button" value="新增總行" onclick="addBank('1');"/>
						<input type="button" class="button" value="新增分行" onclick="addBank('2');"/>
						<input type="button" class="button" value="修改" onclick="editBank();"/>
						<input type="button" class="button" value="刪除" onclick="resetBank('DELETE','0');"/>
						<input type="button" class="button" value="恢復" onclick="resetBank('RESUME','1');"/>
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
				<td id="bankloading"></td>
			</tr>
		</table>
	</form>
</body>
</html>