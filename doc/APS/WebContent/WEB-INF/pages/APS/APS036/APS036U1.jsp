<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "再保分進維護作業";
	String image = path + "/" + "images/";
	String GAMID = "APS036U1";
	String mDescription = "再保分進維護作業";
	String nameSpace = "/aps/036";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 
mantis：FIR0238，處理人員：BJ085，需求單編號：FIR0238 稽核議題檢核-例外地址維護作業  start
-->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script language="JavaScript">

	//init start
	//存放險種明細用
	var insObj = [];
	var detail = '<c:out value="${reinsInwardInsDataStr}" escapeXml="false"/>';

	//init end
	
	$(function() {
		if(detail != "" && detail != null){
			insObj = JSON.parse(detail);
			reBuildTable();
		}
		//只能輸入數字
		$(".number").on("keyup",function (event) {    
			if (this.value != this.value.replace(/[^0-9\.]/g, '')) {
				this.value = this.value.replace(/[^0-9\.]/g, '');
			}
		});
		//取代逗號
		$(".replaceComa").on("click",function (event) {    
			this.value = $(this).val().replace(/\,/g, '');	 
		});
		//增加千分位
		$(".addComa").on("blur",function (event) {    
			if(this.value != "" && this.value != null && this.value.toString().indexOf(',') == -1){
				this.value = formatNumber(this.value);
			}
		});
		

		
		$("#dialog").dialog({
			modal: true,
			autoOpen: false,
			height : "auto", 
			width : "auto",
			close:function(event, ui){ // 對話框關閉時觸發的方法
				clearInsData();
			}
		});
		
		//點新增險種觸發動作
		$("#insOpener").click(function() {
			$("#insPoname option").remove();
			var classcode = $( "#classcode option:selected" ).val();
			if(classcode == '0'){
				alert("請先選擇險種別");
				$("#classcode").focus();
				return false;
			}
			var currency = $( "#currency option:selected" ).val();
			if(currency == ''){
				alert("請先選擇險幣別");
				$("#currency").focus();
				return false;
			}
			var exchangeRate = $("#exchangeRate").val();
			if(exchangeRate == ''){
				alert("請先輸入匯率！");
				$("#exchangeRate").focus();
				return false;
			}
			var undertakingRate = $("#undertakingRate").val();
			if(undertakingRate == ''){
				alert("請先輸入承接比例！");
				$("#undertakingRate").focus();
				return false;
			}
			
			clearInsData();
			ajaxAction('findUwins');
			
			$("#dialog").dialog("open");
			$("#autoOpen").val(true);
		});
		
		//險種檔，險種名稱下拉觸發動作
		$("#insPoname").change(function() {
			$("#insTypAmountRate").val("");
			$("#insTypPremRate").val("");
			$("#insEarthAmountRate").val("");
			$("#insEarthPremRate").val("");
			var key = $( "#insPoname option:selected" ).val();
			var strAry = key.split('-');
			$("#insUwins").val(strAry[0]);
			$("#insPoins").val(strAry[1]);
			$("#insInscode").val(strAry[2]);
			$("#insActins").val(strAry[3]);
			//複製主檔保額及相關資料
			copyMainToIns();
			//險種檔計算
			calculateInsDetail();
		});
		
		
		
		//validate有錯誤時的訊息
		<c:if test="${not empty errorMsg}">
		var msgg = "有錯誤\n";
			<c:forEach items="${errorMsg}" var="entry">   
				$('input[id^=<c:out value="${entry.key}" /> ]').css('background-color','yellow');
				<c:if test="${entry.value != ''}">
					msgg = msgg + 	'<c:out value="${entry.value}" />' + "\n";
				</c:if>
			</c:forEach> 
			alert(msgg);
		</c:if>

	});
	
	function form_submit(method){
		 $("label").html('');
		 var roleType =  $("#roleType").val();
		 if(roleType == "" || roleType == null){
			 alert("無法取得角色資料，請重新登入");
			 return;
		 }
		
		 if('pass' == method){
			 
			 if(roleType == "RI003"){
				 var commissionDate = $("#commissionDate").val();
				 var payDate = $("#payDate").val();
				 var firstPremDueDate = $("#firstPremDueDate").val();
				 var billDate = $("#billDate").val();
				 
			 	if(commissionDate != "" && commissionDate != null){
					 
					if (commissionDate.length != 7) { 
						alert("日期格式錯誤(YYYMMDD，例如：1110203)");
						$("#commissionDate").val("");
						$("#commissionDate").focus();
						return;
					}
						
					var re = /^[0-9]*/;//判斷字串是否為數字//判斷正整數/[1−9] [0−9]∗]∗/ 
					if (!re.test(commissionDate)) { 
						alert("請輸入數字");
						$("#commissionDate").val("");
						$("#commissionDate").focus();
						return;
					}
					//1110612
					var year = commissionDate.substr(0,3);
					var mm = commissionDate.substr(3,2);
					var dd = commissionDate.substr(5,2);
					if(!isExistDate(year + "/" + mm + "/" + dd)){
						alert("請輸入正確日期");
						$("#commissionDate").val("");
						$("#commissionDate").focus();
						return;
					}
					 
					 //alert("請輸入佣金付款/收回日");
					// $("#commissionDate").focus();
					// return false;
				 }
				 if(payDate != "" && payDate != null){
					 
						if (payDate.length != 7) { 
							alert("日期格式錯誤(YYYMMDD，例如：1110203)");
							$("#payDate").val("");
							$("#payDate").focus();
							return;
						}
							
						var re = /^[0-9]*/;//判斷字串是否為數字//判斷正整數/[1−9] [0−9]∗]∗/ 
						if (!re.test(payDate)) { 
							alert("請輸入數字");
							$("#payDate").val("");
							$("#payDate").focus();
							return;
						}
						//1110612
						var year = payDate.substr(0,3);
						var mm = payDate.substr(3,2);
						var dd = payDate.substr(5,2);
						if(!isExistDate(year + "/" + mm + "/" + dd)){
							alert("請輸入正確日期");
							$("#payDate").val("");
							$("#payDate").focus();
							return;
						}
					 //alert("請輸入保費收款/退款日");
					 //$("#payDate").focus();
					// return false;
				 }
				 if(firstPremDueDate != "" && firstPremDueDate != null){
					 
						if (firstPremDueDate.length != 7) { 
							alert("日期格式錯誤(YYYMMDD，例如：1110203)");
							$("#firstPremDueDate").val("");
							$("#firstPremDueDate").focus();
							return;
						}
							
						var re = /^[0-9]*/;//判斷字串是否為數字//判斷正整數/[1−9] [0−9]∗]∗/ 
						if (!re.test(firstPremDueDate)) { 
							alert("請輸入數字");
							$("#firstPremDueDate").val("");
							$("#firstPremDueDate").focus();
							return;
						}
						//1110612
						var year = firstPremDueDate.substr(0,3);
						var mm = firstPremDueDate.substr(3,2);
						var dd = firstPremDueDate.substr(5,2);
						if(!isExistDate(year + "/" + mm + "/" + dd)){
							alert("請輸入正確日期");
							$("#firstPremDueDate").val("");
							$("#firstPremDueDate").focus();
							return;
						}
					 //alert("請輸入第一期保費到期日");
					 //$("#firstPremDueDate").focus();
					// return false;
				 }
				 if(billDate != "" && billDate != null){
					 
						if (billDate.length != 7) { 
							alert("日期格式錯誤(YYYMMDD，例如：1110203)");
							$("#billDate").val("");
							$("#billDate").focus();
							return;
						}
							
						var re = /^[0-9]*/;//判斷字串是否為數字//判斷正整數/[1−9] [0−9]∗]∗/ 
						if (!re.test(billDate)) { 
							alert("請輸入數字");
							$("#billDate").val("");
							$("#billDate").focus();
							return;
						}
						//1110612
						var year = billDate.substr(0,3);
						var mm = billDate.substr(3,2);
						var dd = billDate.substr(5,2);
						if(!isExistDate(year + "/" + mm + "/" + dd)){
							alert("請輸入正確日期");
							$("#billDate").val("");
							$("#billDate").focus();
							return;
						}
				 }
			 }
			 
			 if(!confirm("請確認是否『通過』？")){
				 return false;
			 }
			 if(roleType == "RI002"){
				 $("#mainForm").attr("action","btnInsAudit.action");
				 $("#auditResult").val('0'); 
			 }
			 if(roleType == "RI003"){
				 $("#mainForm").attr("action","btnReinsAudit.action");
				 $("#auditResult").val('0');
			 }
			 $("#mainForm").submit();
		 }
		 if('reject' == method){
			 if(!confirm("請確認是否『駁回』？")){
				 return false;
			 }
			 if(roleType == "RI002"){
				 $("#mainForm").attr("action","btnInsAudit.action");
				 $("#auditResult").val('1');
			 }
			 if(roleType == "RI003"){
				 $("#mainForm").attr("action","btnReinsAudit.action");
				 $("#auditResult").val('1');
			 }
			 $("#mainForm").submit();
		 }
		 if('clear' == method){
				if (confirm("請確認是否放棄修改並返回查詢頁面？")==true){
					$("#clearForm").submit();
				}
			return false;
		 }
	}
	
	function showMsg(msg){
		alert(msg);
	}
	
	var msg = "<%=request.getAttribute("message")%>";
	if('' != msg && 'null' != msg){
		showMsg(msg);
	}
	
	function ajaxAction(action){
		//取出ajax xml相對路徑
		var contextPath = '<%=request.getScheme()%>' + '://<%=request.getServerName()+":"+request.getServerPort()+request.getContextPath()%>';
		var path = ''; 

		if(action == 'findUwins'){
	   		var classCode = $("#classcode").val();
	    	path = contextPath + '/aps/ajax013/findInsDataByParams.action?classCode=' + classCode;
		}
		//執行ajax
		$.ajax({
			url : path,
			type: 'POST',
			data: {	},
			dataType: 'json',
			timeout: 10000,
			async: false,
			cache: false,
			error: function (data, status, e){
				ajaxError(data, status);
			},
			success: function (data, status){
				ajaxSuccess(action, data);
			}
		});
	}
	//ajax成功時會回來的method
	function ajaxSuccess(action, data){
		if(action == 'findUwins'){
			if(data.isExist){
				$('#insPoname').find('option').remove().end();
				var options = '';
				options += '<option value=""></option>';
				for (var key in data){
					if(key == 'isExist'){
						continue;
					}
					if(key == ''){
						continue;
					}
				    options += '<option value="' + key + '">' + key + '-' +data[key] + '</option>';
				}
			    $("#insPoname").append(options);
			}else{
				alert('查無此險別下資料');
			}
		}
	}

	//ajax發生錯誤時會呼叫的method
	function ajaxError(data, status){
		alert('操作失敗');
	}
	
	function formatNumber(num) { 
		if (!isNaN(num)) { 
			return ("" + num).replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g, "$1,"); 
		} 
	}
	
	//險種檔datagrid
	function reBuildTable(){
		
		$('#gridtable tr').remove();
		$('#gridtable').append('<tr align="center" style="background-color: #87CEFA;border-color: #428ABD;">'
			+ '<th width="160px">險種名稱</th>'
			+ '<th width="60px">出單險種</th>'
			+ '<th width="60px">30險種</th>'
			+ '<th width="60px">財務險種</th>'
			+ '<th width="160px">(原幣)總保額金額</th>'
			+ '<th width="160px">(原幣)總保險費</th>'
			+ '<th width="160px">明細</th></tr>');
		
		for(var i = 0 ; i < insObj.length ; i++){
			var obj = insObj[i];
			if(obj == null){
				continue;
			}
			$('#gridtable').append('<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF" >'
			+ '<td align="center">' + obj.poname + '</td>'
			+ '<td align="center">' + obj.poins + '</td>'
			+ '<td align="center">' + obj.inscode + '</td>'
			+ '<td align="center">' + obj.actins + '</td>'
			+ '<td align="center">' + obj.oriCurrAmount + '</td>'
			+ '<td align="center">' + obj.oriCurrPrem + '</td>'
			+ '<td align="center"><a href="#" onClick="updateDetail(' + (i + 1) + ')">檢視</a></td></tr>');
		}
		
	}
	//清空險種暫存資料
	function clearInsData(){
		$("td.insSerial").text("");
		$("td.insPoname").text("");
		$("td.insUwins").text("");
		$("td.insPoins").text("");
		$("td.insInscode").text("");
		$("td.insActins").text("");
		$("td.insTypAmountRate").text("");
		$("td.insTypPremRate").text("");
		$("td.insEarthAmountRate").text("");
		$("td.insEarthPremRate").text("");
		$("td.insDeductibleNature").text("");
		$("td.insDeductibleNonnature").text("");
		$("td.insOriCurrAmount").text("");
		$("td.insNtAmount").text("");
		$("td.insOriCurrPrem").text("");
		$("td.insNtPrem").text("");
		$("td.insOriCurrInwardAmount").text("");
		$("td.insNtInwardAmount").text("");
		$("td.insOriCurrInwardPrem").text("");
		$("td.insNtInwardPrem").text("");
		$("td.insCommissionRate").text("");
		$("td.insOriCurrCommission").text("");
		$("td.insNtCommission").text("");
	}
	
	//帶出險種資料
	function updateDetail(idx) {
		
		ajaxAction('findUwins');
		
		//alert("idx = " + idx + ",length = " + insObj.length);
		var obj = insObj[idx - 1];
		$("#dialog").dialog("open");
		var key = obj.uwins + "-" + obj.poins + "-" + obj.inscode + "-" + obj.actins;
		//alert("key = " + key);
		$("td.insSerial").append(idx - 1);
		$("td.insPoname").append(obj.poname);
		$("td.insUwins").append(obj.uwins);
		$("td.insPoins").append(obj.poins);
		$("td.insInscode").append(obj.inscode);
		$("td.insActins").append(obj.actins);
		$("td.insTypAmountRate").append(obj.typAmountRate);
		$("td.insTypPremRate").append(obj.typPremRate);
		$("td.insEarthAmountRate").append(obj.earthAmountRate);
		$("td.insEarthPremRate").append(obj.earthPremRate);
		$("td.insDeductibleNature").append(obj.deductibleNature);
		$("td.insDeductibleNonnature").append(obj.deductibleNonnature);
		$("td.insOriCurrAmount").append(obj.oriCurrAmount);
		$("td.insNtAmount").append(obj.ntAmount);
		$("td.insOriCurrPrem").append(obj.oriCurrPrem);
		$("td.insNtPrem").append(obj.ntPrem);
		$("td.insOriCurrInwardAmount").append(obj.oriCurrInwardAmount);
		$("td.insNtInwardAmount").append(obj.ntInwardAmount);
		$("td.insOriCurrInwardPrem").append(obj.oriCurrInwardPrem);
		$("td.insNtInwardPrem").append(obj.ntInwardPrem);
		$("td.insCommissionRate").append(obj.commissionRate + '%');
		$("td.insOriCurrCommission").append(obj.oriCurrCommission);
		$("td.insNtCommission").append(obj.ntCommission);
		//alert("insObj.length = " + insObj.length);
		
	}
	//刪除險種資料
	function delDetail(idx) {
		if (confirm('請確認是否刪除' + insObj[idx - 1].poname + '？')){
			insObj[idx - 1] = null;
			calculateFb1Prem();
			calculateCommission();
			reBuildTable();
		}
	}
	
	function isExistDate(dateStr) {
		var dateObj = dateStr.split('/'); // yyyy/mm/dd

		//列出12個月，每月最大日期限制
		var limitInMonth = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];

		var theYear = parseInt(dateObj[0]);
		var theMonth = parseInt(dateObj[1]);
		var theDay = parseInt(dateObj[2]);
		var isLeap = new Date(theYear, 1, 29).getDate() === 29; // 是否為閏年?

		if (isLeap) {
		  // 若為閏年，最大日期限制改為 29
		  limitInMonth[1] = 29;
		}

		// 比對該日是否超過每個月份最大日期限制
		return theDay <= limitInMonth[theMonth - 1];
	}
</script>
</head>
<body style="margin: 0; text-align: left">
<table id="table1" cellSpacing="1" cellPadding="1" width="970px" border="0">
	<tr>
		<td width="485px">
		<a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoQuery.action">
		<img src="${pageContext.request.contextPath}/images/Search_Icon.gif" border="0" width="48" height="26"></a> 
		<img src="${pageContext.request.contextPath}/images/Help_Icon.gif" border="0" width="49" height="26"></td>
		<td align="right" width="485px">PGMID：<%=GAMID%></td>
	</tr>
	<tr>
		<td align="center" colSpan="2" width="970px"><h3><%=title%></h3></td>
	</tr>
</table>

<!-- clear form -->
<s:form theme="simple" action="lnkGoQuery" namespace="/aps/036" id="clearForm" name="clearForm" />
<!-- form 開始 -->
<s:form theme="simple" namespace="/aps/036" id="mainForm" name="mainForm">

	<input type="hidden" name="reinsInwardInsDataStr" id="reinsInwardInsDataStr" />
	<input type="hidden" name="reinsInwardMainData.type" id="type" value="<c:out value='${reinsInwardMainData.type}' escapeXml='false'/>"/>
	<input type="hidden" name="reinsInwardMainData.oid" id="oid" value="<c:out value='${reinsInwardMainData.oid}' escapeXml='false'/>"/>
	<input type="hidden" name="reinsInwardMainData.status" id="status" value="<c:out value='${reinsInwardMainData.status}' escapeXml='false'/>"/>
	<input type="hidden" name="roleType" id="roleType" value="<c:out value='${roleType}' escapeXml='false'/>"/>
	<input type="hidden" name="auditResult" id="auditResult" value="<c:out value='${auditResult}' escapeXml='false'/>"/>
	
	<table id="table2" cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="MainTdColor" style="width: 200px" align="center"><span id="lbSearch"><b>修改作業</b></span></td>
			<td class="image" style="width: 20px"></td>
			<td colspan="2"></td>
		</tr>
	</table>
	<fieldset style="width:800px;">
	    <legend>基本資料</legend>
		<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="870px" border="0">
			<tr>
				<td align="right" width="200px">險種別：</td>
				<td align="left" width="200px">
					<s:select key="reinsInwardMainData.classcode" id="classcode" theme="simple" 
					list="#{'0':'請選擇', 'A':'任意車險', 'B':'強制險', 'E':'工程險', 'F01':'商業火險', 'F02':'住火險', 'M':'水險', 'C':'新種險', 'C1':'傷害險'}" disabled="true"/>
				</td>
				<td align="right" width="200px">作業類型：</td>
				<td align="left">
					<s:if test='reinsInwardMainData.type == "P"'>保單</s:if>
					<s:if test='reinsInwardMainData.type == "T"'>批單</s:if>
				</td>
			</tr>
			<tr>
				<td align="right" width="200px">分出保險公司：</td>
				<td align="left" colspan="3"><s:select key="reinsInwardMainData.cmnpNo" id="cmnpNo" theme="simple" 
					list="#{'':'', '01':'01-臺灣產物保險股份有限公司','02':'02-兆豐產物保險股份有限公司','05':'05-富邦產物保險股份有限公司','06':'06-和泰產物保險股份有限公司','07':'07-泰安產物保險股份有限公司','08':'08-明台產物保險股份有限公司',
					'09':'09-南山產物保險股份有限公司','10':'10-第一產物保險股份有限公司','12':'12-旺旺友聯產物保險股份有限公司','13':'13-新光產物保險股份有限公司','14':'14-華南產物保險股份有限公司','15':'15-國泰世紀產物保險股份有限公司',
					'17':'17-新安東京海上產物保險股份有限公司','28':'28-比利時商裕利安宜產物保險股份有限公司台灣分公司','29':'29-新加坡商美國國際產物保險股份有限公司台灣分公司','30':'30-法商科法斯產物保險股份有限公司台灣分公司',
					'32':'32-美商安達產物保險公司台北分公司','46':'46-法商法國巴黎產物保險股份有限公司台灣分公司'}" disabled="true"/>
				
				</td>
			</tr>
			<tr>
				<td align="right" width="200px">分出保經代公司：</td>
				<td align="left"><s:property value="reinsInwardMainData.broker" /></td>
				<td align="right" width="200px"></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" width="200px">分進編號：</td>
				<td align="left"><s:property value="reinsInwardMainData.inwardNo" /></td>
				<td align="right" width="200px">暫存號：</td>
				<td align="left">
					<s:property value="reinsInwardMainData.proposalNo" />
				</td>
			</tr>
			<tr>
				<td align="right" width="200px">保單號碼：</td>
				<td align="left">
					<s:property value="reinsInwardMainData.policyNo" />
				</td>
				<td align="right" width="200px">批單號碼：</td>
				<td align="left">
					<s:property value="reinsInwardMainData.endorseNo" />
				</td>
			</tr>
			<tr>
				<td align="right" width="200px">保單起迄日：</td>
				<td align="left" ><s:property value="reinsInwardMainData.startDate" />～<s:property value="reinsInwardMainData.endDate" /></td>
				<td align="right" width="200px">批單起迄日：</td>
				<td align="left" >
					<s:if test='reinsInwardMainData.type == "T"'>
						<s:property value="reinsInwardMainData.endorseStartDate" />～<s:property value="reinsInwardMainData.endorseEndDate" />
					</s:if>
				</td>
			</tr>
			<tr>
				<td align="right" width="200px">簽單日：</td>
				<td align="left" ><s:property value="reinsInwardMainData.signDate" /></td>
				<td align="right" width="200px"></td>
				<td align="left" ></td>
			</tr>
			<tr>
				<td align="right" width="200px">要保人名稱：</td>
				<td align="left"><s:property value="reinsInwardMainData.applicantName" /></td>
				<td align="right" width="200px">要保人ID：</td>
				<td align="left"><s:property value="reinsInwardMainData.applicantId" /></td>
			</tr>
			<tr>
				<td align="right" width="200px">被保人名稱：</td>
				<td align="left"><s:property value="reinsInwardMainData.insuredName" /></td>
				<td align="right" width="200px">被保人ID：</td>
				<td align="left"><s:property value="reinsInwardMainData.insuredId" /></td>
			</tr>
			<tr>
				<td align="right" width="200px">標的物地址/主保單號：</td>
				<td align="left" colspan="3"><s:property value="reinsInwardMainData.objInfo" /></td>
			</tr>
		</table>
	</fieldset>
	<fieldset style="width:800px;">
	    <legend>分進內容</legend>
		<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="870px" border="0">
			<tr>
				<td align="right" width="200px">幣別：</td>
				<td align="left" width="200px">
					<s:select key="reinsInwardMainData.currency" id="currency" theme="simple" 
					list="#{'TWD':'台幣', 'USD':'美金', 'GBP':'英鎊', 'EUR':'歐元', 'HKD':'港幣', 'CNY':'人民幣', 'SGD':'新加坡幣', 
					'JPY':'日圓', 'KRW':'韓元', 'AUD':'澳幣', 'NZD':'紐元', 'SEK':'瑞典幣', 'CHF':'瑞士法郎', 'CAD':'加拿大幣', 
					'PHP':'菲國比索', 'THB':'泰幣', 'VND':'越南幣', 'IDR':'印尼幣', 'MYR':'馬來幣', 'ZAR':'南非幣'}" disabled="true"/>
				</td>
				<td align="right" width="200px">匯率：</td>
				<td align="left" ><s:property value="reinsInwardMainData.exchangeRate" /></td>
			</tr>
			<tr>
				<td align="right" width="200px">承接比例：</td>
				<td align="left" ><s:property value="reinsInwardMainData.undertakingRate" />%</td>
				<td align="right" width="200px"></td>
				<td align="left" ></td>
			</tr>
		</table>
	</fieldset>
	<fieldset style="width:800px;">
	    <legend>原幣</legend>
	    <table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="870px" border="0">
			<tr>
				<td align="right" width="200px">總保額：</td>
				<td align="left" width="200px"><s:property value="reinsInwardMainData.oriCurrAmount" /></td>
				<td align="right" width="200px">總保費：</td>
				<td align="left"><s:property value="reinsInwardMainData.oriCurrPrem" /></td>
			</tr>
			<tr>
				<td align="right" width="200px">最大點保額：</td>
				<td align="left" width="200px"><s:property value="reinsInwardMainData.oriCurrMaxAmount" /></td>
				<td align="right" width="200px"></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td colspan="3"><br></td>
			</tr>
			<tr>
				<td align="right" width="200px">分進總保額：</td>
				<td align="left" width="200px"><s:property value="reinsInwardMainData.oriCurrInwardAmount" /></td>
				<td align="right" width="200px">分進總保費：</td>
				<td align="left"><s:property value="reinsInwardMainData.oriCurrInwardPrem"/></td>
			</tr>
			<tr>
				<td align="right" width="200px">分進最大點保額：</td>
				<td align="left" width="200px"><s:property value="reinsInwardMainData.oriCurrInwardMaxAmount" /></td>
				<td align="right" width="200px">佣金：</td>
				<td align="left"><s:property value="reinsInwardMainData.oriCurrCommission"/></td>
			</tr>
		</table>
	</fieldset>
	<fieldset style="width:800px;">
	    <legend>台幣</legend>
	    <table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="870px" border="0">
			<tr>
				<td align="right" width="200px">總保額：</td>
				<td align="left" width="200px"><s:property value="reinsInwardMainData.ntAmount" /></td>
				<td align="right" width="200px">總保費：</td>
				<td align="left"><s:property value="reinsInwardMainData.ntPrem" /></td>
			</tr>
			<tr>
				<td align="right" width="200px">最大點保額：</td>
				<td align="left" width="200px"><s:property value="reinsInwardMainData.ntMaxAmount" /></td>
				<td align="right" width="200px"></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td colspan="3"><br></td>
			</tr>
			<tr>
				<td align="right" width="200px">分進總保額：</td>
				<td align="left"><s:property value="reinsInwardMainData.ntInwardAmount" /></td>
				<td align="right" width="200px">分進總保費：</td>
				<td align="left"><s:property value="reinsInwardMainData.ntInwardPrem"/></td>
			</tr>
			<tr>
				<td align="right" width="200px">分進最大點保額：</td>
				<td align="left"><s:property value="reinsInwardMainData.ntInwardMaxAmount"/></td>
				<td align="right" width="200px">佣金：</td>
				<td align="left"><s:property value="reinsInwardMainData.ntCommission" /></td>
			</tr>
		</table>
	</fieldset>
	<fieldset style="width:800px;">
	    <legend>險種明細</legend>
		<table id="gridtable" border="1" class="main_table"  width="870px"></table>
	    <div id="dialog" title="險種明細">
			<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="870px" border="0">
				<tr>
					<td align="right" width="200px">險種名稱：</td>
					<td align="left" width="200px" class="insPoname" ></td>
					<td align="right" width="200px"> </td>
					<td align="left" width="200px"><s:hidden key="insSerial" id="insSerial" /></td>			
				</tr>
				<tr>
					<td align="right" width="200px">大險種：</td>
					<td align="left" class="insUwins" ></td>
					<td align="right" width="200px">出單險種：</td>
					<td align="left" class="insPoins"></td>
				</tr>
				<tr>
					<td align="right" width="200px">30險種：</td>
					<td align="left" class="insInscode"></td>
					<td align="right" width="200px">財務險種：</td>
					<td align="left" class="insActins"></td>
				</tr>
				<tr>
					<td align="right" width="200px">颱洪保額比例：</td>
					<td align="left" class="insTypAmountRate">%</td>
					<td align="right" width="200px">颱洪保費比例：</td>
					<td align="left" class="insTypPremRate">%</td>
				</tr>
				<tr>
					<td align="right" width="200px">地震保額比例：</td>
					<td align="left" class="insEarthAmountRate">%</td>
					<td align="right" width="200px">地震保費比例：</td>
					<td align="left" class="insEarthPremRate">%</td>
				</tr>
				<tr>
					<td align="right" width="200px" >(非天災)自負額：</td>
					<td align="left" class="insDeductibleNonnature"></td>
					<td align="right" width="200px">(天災)自負額：</td>
					<td align="left" class="insDeductibleNature"></td>
				</tr>
				<tr>
					<td align="right" width="200px">佣金率：</td>
					<td align="left" colspan="3" class="insCommissionRate"></td>
				</tr>
			</table>
		<fieldset style="width:800px;">	
		    <legend>原幣</legend>
	    	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="850px" border="0">
				<tr>
					<td align="right" width="200px">總保額：</td>
					<td align="left" width="200px" class="insOriCurrAmount"></td>
					<td align="right" width="200px">總保險費：</td>
					<td align="left" width="200px" class="insOriCurrPrem"></td>
				</tr>
				<tr>
					<td align="right" width="200px">分進總保額：</td>
					<td align="left" class="insOriCurrInwardAmount"></td>
					<td align="right" width="200px">分進總保費：</td>
					<td align="left" class="insOriCurrInwardPrem"></td>
				</tr>
				<tr>
					<td align="right" width="200px" >佣金：</td>
					<td align="left" class="insOriCurrCommission"></td>
					<td align="right" width="200px"></td>
					<td align="left" ></td>
				</tr>
			</table>
		</fieldset>
		<fieldset style="width:800px;">	
		    <legend>台幣</legend>
	    	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="850px" border="0">
				<tr>
					<td align="right" width="200px">總保額：</td>
					<td align="left" width="200px" class="insNtAmount"></td>
					<td align="right" width="200px">總保險費：</td>
					<td align="left" width="200px" class="insNtPrem"></td>
				</tr>
				<tr>
					<td align="right" width="200px">分進總保額：</td>
					<td align="left" class="insNtInwardAmount"></td>
					<td align="right" width="200px">分進總保費：</td>
					<td align="left" class="insNtInwardPrem"></td>
				</tr>
				<tr>
					<td align="right" width="200px">佣金：</td>
					<td align="left" class="insNtCommission"></td>
					<td align="right" width="200px"></td>
					<td align="left" ></td>
				</tr>
			</table>
		</fieldset>
	    </div>

	</fieldset>
	<fieldset style="width:800px;">
	    <legend>收費資訊</legend>
		<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="870px" border="0">
			<tr>
				<td align="right" width="200px"> </td>
				<td align="left" width="200px"> </td>
				<td align="right" width="200px"> </td>
				<td align="left" width="200px"> </td>
			</tr>
			<tr>
				<td align="right" width="200px">期數：</td>
				<td align="left" colspan="3"><s:property value="reinsInwardMainData.payNo" /></td>
			</tr>
			

			<tr>
				<td align="right" width="200px">佣金付款/收回日：</td>
				<td align="left" >
					<s:if test='roleType == "RI003" && reinsInwardMainData.status == "2"'>
						<s:textfield key="reinsInwardMainData.commissionDate" id="commissionDate" size="7" maxlength="7"/>(民國年月日共7碼)
					</s:if>
					<s:else>
						<s:property value="reinsInwardMainData.commissionDate" />　
					</s:else>
				</td>
				<td align="right" width="200px">帳單號碼：</td>
				<td align="left">
					<s:if test='roleType == "RI003" && reinsInwardMainData.status == "2"'>
						<s:textfield key="reinsInwardMainData.billNo" id="billNo" size="10" maxlength="10"/>
					</s:if>
					<s:else>
						<s:property value="reinsInwardMainData.billNo" />
					</s:else>
				</td>
			</tr>
			<tr>
				<td align="right" width="200px">保費收款/退款日：</td>
				<td align="left">
					<s:if test='roleType == "RI003" && reinsInwardMainData.status == "2"'>
						<s:textfield key="reinsInwardMainData.payDate" id="payDate" size="7" maxlength="7"/>(民國年月日共7碼)
					</s:if>
					<s:else>
						<s:property value="reinsInwardMainData.payDate" />
					</s:else>
				</td>
				<td align="right" width="200px">立帳日：</td>
				<td align="left">
					<s:if test='roleType == "RI003" && reinsInwardMainData.status == "2"'>
						<s:textfield key="reinsInwardMainData.billDate" id="billDate" size="7" maxlength="7"/>(民國年月日共7碼)
					</s:if>
					<s:else>
						<s:property value="reinsInwardMainData.billDate" />
					</s:else>
				</td>
			</tr>
			<tr>
				<td align="right" width="200px">第一期保費到期日：</td>
				<td align="left" colspan="3">
					<s:if test='roleType == "RI003" && reinsInwardMainData.status == "2"'>
						<s:textfield key="reinsInwardMainData.firstPremDueDate" id="firstPremDueDate" size="7" maxlength="7"/>(民國年月日共7碼)
					</s:if>
					<s:else>
						<s:property value="reinsInwardMainData.firstPremDueDate" />
					</s:else>
				</td>
			</tr>
		</table>
	</fieldset>
	<fieldset style="width:800px;">
	    <legend>其他</legend>
		<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="870px" border="0">
			<tr>
				<td align="right" width="200px">備註：</td>
				<td align="left" colspan="3"><s:property value="reinsInwardMainData.comments" /></td>
			</tr>
			<tr>
				<td align="right" width="200px">輸入人員：</td>
				<td align="left" width="200px"><s:property value="reinsInwardMainData.creater"  /></td>
				<td align="right" width="200px">輸入日期：</td>
				<td align="left" width="200px"><s:property value="reinsInwardMainData.createDate" /></td>
			</tr>
			<tr>
				<td align="right" width="200px">險部覆核人員：</td>
				<td align="left"><s:property value="reinsInwardMainData.insReviewer" /></td>
				<td align="right" width="200px">險部覆核日期：</td>
				<td align="left"><s:property value="reinsInwardMainData.insReviewDate" /></td>
			</tr>
			<tr>
				<td align="right" width="200px">再保覆核人員：</td>
				<td align="left"><s:property value="reinsInwardMainData.reinsReviewer" /></td>
				<td align="right" width="200px">再保覆核日期：</td>
				<td align="left"><s:property value="reinsInwardMainData.reinsReviewDate" /></td>
			</tr>
		</table>
	</fieldset>
	<table width="970px">
		<tr>
			<td align="center">
				<s:if test='(roleType == "RI002" && reinsInwardMainData.status == "1") || (roleType == "RI003" && reinsInwardMainData.status == "2")'>
					<input value="通過" type="button" onclick="javascript:form_submit('pass');">&nbsp;&nbsp;&nbsp;&nbsp; 
					<input value="駁回" type="button" onclick="javascript:form_submit('reject');" />
				</s:if>
				
			</td>
		</tr>
	</tr>
</s:form>
</body>
</html>