<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<%@ taglib uri="/WEB-INF/undwrt-app.tld" prefix="app"%>
<%@ page import="com.sinosoft.sysframework.reference.AppConfig"%>
<%@ taglib prefix="rc" uri="http://util.one.sinosoft.com/RCDate"%>
<%@page import="java.util.*"%>
<%@page import="java.lang.*"%>
<%--add by gaojunfeng 添加繳費資料輸入介面  20161025  --%>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="/undwrt/css/Standard1.css">
		<script type='text/javascript' src="/undwrt/widgets/yui/yahoo-dom-event/yahoo-dom-event.js"></script>
		<script type='text/javascript' src="/undwrt/widgets/yui/yahoo/yahoo-min.js"></script>
		<script type='text/javascript' src="/undwrt/widgets/yui/yahoo/yahoo.js"></script>
		<script type='text/javascript' src="/undwrt/widgets/yui/utilities/utilities.js"></script>
		<script type='text/javascript' src="/undwrt/widgets/yui2/treeview/treeview-min.js"></script>
		<script type='text/javascript' src="/undwrt/widgets/yui2/json/json-min.js"></script>
		<script src="/undwrt/common/js/WfLogQuery.js"></script>
		<script src="/undwrt/common/js/My97DatePicker/WdatePicker.js"></script>
		<!-- mantis： CAR0491，處理人員：CC009，需求單編號：車險核保系統，收費註記功能優化 -->
		<script src="/undwrt/common/js/Common.js"></script>
		<jsp:include page="/common/meta_css.jsp" />
		
	<script language=Javascript>
	//mantis： CAR0491，處理人員：CC009，需求單編號：車險核保系統，收費註記功能優化 start
	window.onload = function() {
		
		document.getElementById("approvalCode").disabled=true; //授權碼
		document.getElementById("creditAmount").disabled=true; //信用卡金額
		document.getElementById("checkAccount").disabled=true; //支票號碼
		document.getElementById("expireDate").disabled=true; //開票日期
		document.getElementById("checkAmount").disabled=true; //支票金額
		document.getElementById("issuerName").disabled=true; //開票人
	};
	//mantis： CAR0491，處理人員：CC009，需求單編號：車險核保系統，收費註記功能優化 end
	function enterNextInput(field) {
		var KEY_CODE_BACKSPACE = 8;//键盘上的"Backspace"
		var bankCode1= fm.bankCode1;
		var bankCode2= fm.bankCode2;
		if("bankCode1"==field.id) {
			if(event.keyCode!=KEY_CODE_BACKSPACE) {
				if(field.value!='') {
					var length = field.value.length;
					if(length==3) {
						bankCode2.focus();
					}
				}
			}
		} else if("bankCode2"==field.id) {
			if(event.keyCode==KEY_CODE_BACKSPACE) {
				if(field.value=='' || field.value.length<1) {
					bankCode1.focus();
					if(bankCode1.value!='') {
						var valueTemp = bankCode1.value;
						var length = bankCode1.value.length;
						if(length>0) {
							bankCode1.value = '';
							bankCode1.value = valueTemp;
						}
					}
					
				}
			}
		}
	}
	//增加银行代码部分由3码+4码查询出银行名称
	function queryBankInfoInput(){
		//获取银行代号前三位
 		var BankCode = fm.bankCode1.value;
		if(null==BankCode || ''==BankCode){
			errorMessage("银行前三位代码不能为空！");
			return;
		}
// 		//获取银行代号后四位
 		var BankCodeDetail = fm.bankCode2.value;
		if(null==BankCodeDetail || ''==BankCodeDetail){
			errorMessage("银行后四位代码不能为空！");
			return;
		}
		//拼接银行全代码
		var LastBankCode = BankCode+BankCodeDetail;
		//調用查詢函數，通過银行代码查询银行名称
		 var callback ={
				 success:function(res){
			    	 var prpDBankInfolist = [];
			    	 prpDBankInfolist = YAHOO.lang.JSON.parse(res.responseText);
			    	 //給銀行名稱欄位賦值
			    	 var BankNameField = fm.bank;
			    	 if(prpDBankInfolist.data[0] == "" || prpDBankInfolist.data[0] == null){
			    		alert("輸入的銀行代碼不存在，請檢查輸入");
			    		return false;
			    	 }
			    	 BankNameField.value = prpDBankInfolist.data[0].bankName;
			     },
			     failure:function(res){
					errorMessage("輸入的銀行代碼不存在，請檢查輸入！");
			     } 				 			
			};
		 YAHOO.util.Connect.asyncRequest('POST','/undwrt/undwrtDeal/queryBankInfo.do?LastBankCode='+LastBankCode,callback,"text");
	}
	
	//mantis： CAR0491，處理人員：CC009，需求單編號：車險核保系統，收費註記功能優化 START
	//提交表單
	function mySubmit(){
		fm.proposalNo.value = fm.proposalNo.value;
		fm.virtualNo.value = fm.virtualNo.value;
		fm.appliName.value = fm.appliName.value;
		fm.mainPolicyNo.value = fm.mainPolicyNo.value;
		var payWay = fm.payWay.value; //支付方式
		var payAmount = fm.payAmount.value;	//收費金額
		var busiType = fm.busiType.value;
		//mantis： CAR0670，處理人員：DP0713，需求單編號：支票收費註記卡控調整
		var checkPay = fm.checkPay.value;
		// mantis：CAR0520，處理人員：DP0714，車險繳費註記功能優化 -- start
		var payDate = fm.payDate.value; //繳費日期
		if(null==payDate || ''==payDate){
			alert("繳費日期不能為空!");
			return false;
		}
		var today = fm.today.value;
		var t = today.split('-');
		var p = payDate.split('-');
		if (p > t) {
			alert("客戶繳費日不可以晚於系統日期");
			fm.payDate.value = '';
			return false;
		}
		// mantis：CAR0520，處理人員：DP0714，車險繳費註記功能優化 -- end
		if(payWay==3) { //支付方式選擇信用卡，需錄入信用卡金額
			
			// mantis：CAR0692，處理人員：DP1580，車險調整收費註記規則 start
			var proposalNo = fm.proposalNo.value;
			if(proposalNo.startsWith("9A")||proposalNo.startsWith("9B")){
				alert("繳費方式為信用卡，無法使用收費註記，請再確認！");
				return false;
			}
			// mantis：CAR0692，處理人員：DP1580，車險調整收費註記規則 end
			
			var approvalCode = fm.approvalCode.value; //授權碼
			// mantis：CAR0520，處理人員：DP0714，車險繳費註記功能優化
			//var payDate = fm.payDate.value; //繳費日期
			var creditAmount = fm.creditAmount.value; //信用卡金額
			if(null==approvalCode || ''==approvalCode){
				alert("授權碼不能為空!");
				return false;
			}
			// mantis：CAR0520，處理人員：DP0714，車險繳費註記功能優化 -- start
			/*if(null==payDate || ''==payDate){
				alert("繳費日期不能為空!");
				return false;
			}*/
			// mantis：CAR0520，處理人員：DP0714，車險繳費註記功能優化 -- end
			if(null==creditAmount || ''==creditAmount) {
				alert("信用卡金額不能為空!");
				return false;
			}
			if(creditAmount != payAmount){
				alert("刷卡金額不等於收費金額");
				return false;
			}
		}else if (payWay==4){
			var checkAccount = fm.checkAccount.value; //支票號碼
			var expireDate = fm.expireDate.value; //開票日期
			var checkAmount = fm.checkAmount.value; //支票金額
			var issuerName = fm.issuerName.value; //開票人
			//mantis： CAR0670，處理人員：DP0713，需求單編號：支票收費註記卡控調整 START
			if(checkPay != ""){
				if(null==checkAccount || ''==checkAccount){
					alert("支票號碼不能為空!");
					return false;
				}else{
					if(checkAccount.startsWith(' ') || checkAccount.endsWith(' ')){
						alert("支票號碼頭尾不可為空白");
						return false;
					}
				}
				if(null==expireDate || ''==expireDate){
					alert("開票日期不能為空!");
					return false;
				}
				// mantis：CAR0520，處理人員：DP0714，車險繳費註記功能優化 -- start
				var e = expireDate.split('-');
				if (e > t) {
					alert("開票日期不可以晚於系統日期");
					fm.expireDate.value = '';
					return false;
				}
				// mantis：CAR0520，處理人員：DP0714，車險繳費註記功能優化 -- end
				if(null==checkAmount || ''==checkAmount){
					alert("支票金額不能為空!");
					return false;
				}
				if(null==issuerName || ''==issuerName){
					alert("開票人不能為空!");
					return false;
				}else{
					if(issuerName.startsWith(' ') || issuerName.endsWith(' ')){
						alert("開票人頭尾不可為空白");
						return false;
					}
				}
				if(checkAmount != payAmount){
					alert("支票金額不等於收費金額");
					return false;
				}
				
				var expireDate = fm.expireDate.value; //繳費日期
				var validDate = fm.validDate.value; //保險起日or批改生效日
				var pay = expireDate.split('-');
				var start = validDate.split('-');
				if(start < pay){
					if(busiType == 'E'){
						alert("開票日期不可晚於批改生效日");
					}else{
						alert("開票日期不可晚於保險起日");
					}
					return false;
				}
			}
			//mantis： CAR0670，處理人員：DP0713，需求單編號：支票收費註記卡控調整 END
		}
		// mantis：CAR0520，處理人員：DP0714，車險繳費註記功能優化
		//var payDate = fm.payDate.value; //繳費日期
		var validDate = fm.validDate.value; //保險起日or批改生效日
		var pay = payDate.split('-');
		var start = validDate.split('-');
		//mantis： CAR0670，處理人員：DP0713，需求單編號：支票收費註記卡控調整 START
		if(checkPay != ""){
			if(start < pay){
				if(busiType == 'E'){
					alert("客戶繳費日不可晚於批改生效日");
				}else{
					alert("客戶繳費日不可晚於保險起日");
				}
				return false;
			}
		}
		//mantis： CAR0670，處理人員：DP0713，需求單編號：支票收費註記卡控調整 END
		if(!confirm("是否繼續儲存?")) {
        	return false;
        }
		fm.action = "/undwrt/undwrtDeal/updatePayRef.do";
		fm.method = "post";
		fm.submit();
	}
	//校驗授權碼格式
	function checkApprovalCode(obj){
		var code = obj.value;
		if(code != ""){
			var reg = /^.[A-Za-z0-9]+$/;
			if(!reg.test(code)){
				alert("授權碼應為6碼英數字");
				obj.value = "";
				return false;
			}
			if(code.length != 6){
				alert("授權碼應為6碼英數字");
				obj.value = "";
				return false;
			}
		}
	}
	//繳費方式變更時欄位控制
	function payWayChange(obj){
		var payWay = obj.value;
		fm.approvalCode.value = '';
		fm.creditAmount.value = '';
		fm.checkAccount.value = '';
		fm.expireDate.value = '';
		fm.checkAmount.value = '';
		fm.issuerName.value = '';
		if(payWay == '3'){	//支付方式選擇信用卡
			document.getElementById("approvalCode").disabled=false; //授權碼
			document.getElementById("creditAmount").disabled=false; //信用卡金額
			document.getElementById("checkAccount").disabled=true; //支票號碼
			document.getElementById("expireDate").disabled=true; //開票日期
			document.getElementById("checkAmount").disabled=true; //支票金額
			document.getElementById("issuerName").disabled=true; //開票人
		}else if(payWay == '4'){ //支付方式選擇支票
			document.getElementById("approvalCode").disabled=true; //授權碼
			document.getElementById("creditAmount").disabled=true; //信用卡金額
			document.getElementById("checkAccount").disabled=false; //支票號碼
			document.getElementById("expireDate").disabled=false; //開票日期
			document.getElementById("checkAmount").disabled=false; //支票金額
			document.getElementById("issuerName").disabled=false; //開票人
		}else {
			document.getElementById("approvalCode").disabled=true; //授權碼
			document.getElementById("creditAmount").disabled=true; //信用卡金額
			document.getElementById("checkAccount").disabled=true; //支票號碼
			document.getElementById("expireDate").disabled=true; //開票日期
			document.getElementById("checkAmount").disabled=true; //支票金額
			document.getElementById("issuerName").disabled=true; //開票人
		}
	}
	//mantis： CAR0491，處理人員：CC009，需求單編號：車險核保系統，收費註記功能優化 END
	//校驗數字格式
	function checkNumber(obj){
		var number = obj.value;
		if(number != ""){
			var reg = /^[1-9]\d*$/;
			if(reg.test(number)){
				return;
			}else{
				alert("請輸入正確的數字格式!");
				obj.value = "";
				return false;
			}
		}
	}
	</script>
	</head>
	<body>
	<form name = "fm" action="/undwrt/undwrtDeal/updatePayRef.do" >
		<table class="fix_table"  cellspacing="1" id="payFeeInfo" style="width: 100%;background-color: #87CEFA;height: 50%" >
		<thead>
			<tr align="center">
				<td class=formtitle colspan="4" style="background-color: #87CEFA">繳費資料輸入</td>
			</tr>
		</thead>
			<!-- mantis： CAR0491，處理人員：CC009，需求單編號：車險核保系統，收費註記功能優化 START -->
			<tr >
				<td width="20%" class="right3">要保書號：</td>
				<td width="30%" class="left3">
				<input type="hidden" name="busiType" id = "busiType" value = "<%=request.getAttribute("busiType") %>">
				<!-- mantis： CAR0670，處理人員：DP0713，需求單編號：支票收費註記卡控調整 -->
				<input type="hidden" name="checkPay" id = "checkPay" value = "<%=request.getAttribute("checkPay") %>">
				<input type="text" name="proposalNo" id="proposalNo" class="readonly" 
			    	value="<%=request.getAttribute("proposalNo") %>" title="" maxlength="58" readOnly="readonly" />
				</td>
			
				<td width="20%" class="right3">繳款單號:</td>
				<td width="30%" class="left3">
				<input type="text" readOnly="readonly"  class="readonly" 
					value="<%=request.getAttribute("virtualNo") %>" name="virtualNo" id="virtualNo" maxlength="30" >
				</td>
			</tr>
			
			<tr>
				<td width="20%" class="right3">保險起日：</td>
				<td width="30%" class="left3">
				<input class="readonly" type="text" name="startDate" id="startDate" value="<rc:rcDate value="${startDate}" format="yyyy-MM-dd"/>"
				readOnly="readonly" />
				</td>
				
				<td width="20%" class="right3">保險迄日：</td>
				<td width="30%" class="left3">
				<input class="readonly" type="text" name="endDate" id="endDate" value="<rc:rcDate value="${endDate}" format="yyyy-MM-dd"/>"
				readOnly="readonly" />
				</td>
			</tr>

            <!-- mantis：CAR0520，處理人員：DP0714，車險繳費註記功能優化 start -->
			<tr>
				<td width="20%" class="right3">關聯保險起日：</td>
				<td width="30%" class="left3">
				    <%if (request.getAttribute("refStartDate")!=null) {%>
				    <input class="readonly" type="text" name="refStartDate" id="refStartDate" value="<rc:rcDate value="${refStartDate}" format="yyyy-MM-dd"/>" readOnly="readonly" />
				    <%}%>
				</td>
				<td width="20%" class="right3">關聯保險迄日：</td>
				<td width="30%" class="left3">
				    <%if (request.getAttribute("refEndDate")!=null) {%>
				    <input class="readonly" type="text" name="refEndDate" id="refEndDate" value="<rc:rcDate value="${refEndDate}" format="yyyy-MM-dd"/>" readOnly="readonly" />
				    <%}%>
				</td>
			</tr>
            <!-- mantis：CAR0520，處理人員：DP0714，車險繳費註記功能優化 end -->
			
			<tr>
				<td width="20%" class="right3">要保人：</td>
				<td width="30%" class="left3">
				<input type="text" class="readonly" readOnly="readonly" 
	             value="<%=request.getAttribute("appliName") %>" name="appliName" maxlength="12" id="appliName">
				</td>
				
				<td width="20%" class="right3">強制證號：</td>
				<td width="30%" class="left3">
				<input type="text" class="readonly" readOnly="readonly" 
				value="<%=request.getAttribute("mainPolicyNo") %>" name="mainPolicyNo" id="mainPolicyNo" maxlength="15">
				</td>
			</tr>
			
			<tr>
				<td width="20%" class="right3">客戶繳費日：</td>
				<td width="30%" class="left3">
				<input type="hidden" name="validDate" id = "validDate" value = "<rc:rcDate value="${validDate}" format="yyyy-MM-dd"/>">
				<!-- mantis：CAR0520，處理人員：DP0714，車險繳費註記功能優化 -->
				<input type="hidden" name="today" id="today" value = "<rc:rcDate value="${todayDateRc}" format="yyyy-MM-dd"/>">
				<input class="input_common" type="text" name="payDate"  id="payDate" value="<rc:rcDate value="${todayDateRc}" format="yyyy-MM-dd"/>"
				 onFocus="WdatePicker({dateFmt:'yyy-MM-dd'})" >
				</td>
				
				<td width="20%" class="right3">繳費方式：</td>
				<td width="30%" class="left3">
				<select class="input_common" name="payWay"  onchange="payWayChange(this);" > 
					<option value='1'>1現金</option>
					<option value='2'>2匯款</option>
					<option value='3'>3信用卡</option>
					<option value='4'>4支票</option>
					<option value='5'>5超商郵局代收</option>
					<option value='6'>6其他代收</option>
					<option value='7'>7抵繳</option>
					<option value='8'>8農金</option>
					<option value='9'>9收費通知</option>
					<!-- <option value='10'>10信用卡+現金</option> -->
				</select>
				</td>
			</tr>
			
			<tr>
				<td width="20%" class="right3">支票號碼：</td>
				<td width="30%" class="left3">
				<input type="text" class="input_common" name="checkAccount" maxlength="120"
	                  id="checkAccount">
				</td>
				
				<td width="20%" class="right3">收費金額：</td>
				<td width="30%" class="left3">
				<input type="text" class="readonly" value="<%=request.getAttribute("payAmount") %>" name="payAmount" id="payAmount" maxlength="30" 
				readOnly="readonly" />
				</td>
			</tr>
			
			<tr>
				<td width="20%" class="right3">開票日期：</td>
				<td width="30%" class="left3">
				<input class="input_common" type="text" name="expireDate" id="expireDate" value="" onFocus="WdatePicker({dateFmt:'yyy-MM-dd'})" >
				</td>
				
				<td width="20%" class="right3">信用卡金額：</td>
				<td width="30%" class="left3">
				<input type="text" class="input_common" 
	              name="creditAmount" maxlength="12" id="creditAmount" onblur="checkNumber(this);">
				</td>
			</tr>
			
			<tr>
				<td width="20%" class="right3">支票金額：</td>
				<td width="30%" class="left3">
				<input type="text" class="input_common" name="checkAmount" id="checkAmount" maxlength="255" onblur="checkNumber(this);">
				</td>
				<!-- mantis： CAR0491，處理人員：CC009，需求單編號：車險核保系統，收費註記功能優化 START -->
				<td width="20%" class="right3">授權碼：</td>
				<td width="30%" class="left3">
				<input type="text" class="input_common" name="approvalCode" maxlength="12" id="approvalCode" maxlength="6" onblur="checkApprovalCode(this);">
				</td>
			</tr>
			
			<tr>
				<td width="20%" class="right3">開票人：</td>
				<td width="30%" class="left3">
				<input type="text" class="input_common" name="issuerName" id="issuerName" maxlength="100">
				</td>
				
				<td width="20%" class="right3">執行人：</td>
				<td width="30%" class="left3">
				<input type="text" name="userCode" id="userCode" class="readonly" 
			    	value="<%=request.getAttribute("userCode") %>" title="" maxlength="58" readOnly="readonly" />
				</td>
			</tr>
			<!-- mantis： CAR0491，處理人員：CC009，需求單編號：車險核保系統，收費註記功能優化 END -->
			<tr>
            	<td colspan="6" align="center"> 
				<input class="button" type="button" alt="儲存 " value="儲存" onclick="mySubmit()">
				<!-- mantis：CAR0520，處理人員：DP0714，車險繳費註記功能優化 -->
	 			<input class="button" type="reset" alt="清除" value="清除" style="display:none">
	         	</td>
           </tr>
           
		</table>
	</form>
	</body>	
</html>	
