<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "再保分進維護作業";
	String image = path + "/" + "images/";
	String GAMID = "APS036A0";
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
	var detail = $("#reinsInwardInsDataStr").val();
	if(detail != "" && detail != null){
		insObj = detail;
		reBuildTable();
	}
	//init end
	
	$(function() {
		
		$("#sameApplicantId").click(function() {
			if($("#sameApplicantId").prop("checked")) {
				
				$("#insuredName").val($("#applicantName").val());
				$("#insuredId").val($("#applicantId").val());
			} else {
				$("#insuredName").val("");
				$("#insuredId").val("");
			}
		});
		//生效日異動
		$("#startDate").change(function() {
			var startDate = $("#startDate").val();
			if(startDate != "" && startDate != null){
				if (startDate.length != 7) { 
					alert("日期格式錯誤(YYYMMDD，例如：1110203)");
					$("#startDate").val("");
					$("#startDate").focus();
					return;
				}
				
				var re = /^[0-9]*/;//判斷字串是否為數字//判斷正整數/[1−9] [0−9]∗]∗/ 
				if (!re.test(startDate)) { 
					alert("請輸入數字");
					$("#startDate").val("");
					$("#startDate").focus();
					return;
				}
				//1110612
				var year = startDate.substr(0,3);
				var mm = startDate.substr(3,2);
				var dd = startDate.substr(5,2);
				year = parseInt(year,10) + 1911;
				if(!isExistDate(year + "/" + mm + "/" + dd)){
					alert("請輸入正確日期");
					$("#startDate").val("");
					$("#startDate").focus();
					return;
				}
				
				var date = year + '-' + mm + '-' + dd;
				var mydate = new Date(date);
				if(mydate instanceof Date && !isNaN(mydate)){
					mydate.setFullYear(mydate.getFullYear() + 1);
					var endMM = (parseInt(mydate.getMonth(),10) + 1) + "";
					var endDD = mydate.getDate() + "";
					if (endMM.length < 2) 
						endMM = '0' + endMM;
				    if (endDD.length < 2) 
				    	endDD = '0' + endDD;
					var endDate = (parseInt(mydate.getFullYear(),10) - 1911) + "" + endMM + "" + endDD;
					$("#endDate").val(endDate);
				}else{
					alert("請輸入正確日期");
					$("#startDate").val("");
					$("#startDate").focus();
					return;
				}
			}
		});
		//只能輸入數字
		$(".numberDot").on("keyup",function (event) {    
			this.value = this.value.replace(/[^0-9\.]/g, '');
		});
		$(".number").on("keyup",function (event) {    
			this.value = this.value.replace(/[^\-0-9]/g, '');
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
		
		//主檔總保額欄位內容異動時觸發
		$("#oriCurrAmount").change(function() {
			calculateMain();			
		});
		//主檔總保費欄位內容異動時觸發
		$("#oriCurrPrem").change(function() {
			calculateMain();
		});
		//主檔最大保額欄位內容異動時觸發
		$("#oriCurrMaxAmount").change(function() {
			calculateMain();
		});
		//主檔匯率欄位內容異動時觸發
		$("#exchangeRate").change(function() {
			if($("#exchangeRate").val() != "" && $("#exchangeRate").val() != null){
				var ary = $("#exchangeRate").val().toString().split("."); 
				if(ary.length > 2){
					alert("匯率欄位格式錯誤");
					$("#exchangeRate").val("");
					$("#exchangeRate").focus();
					return false;
				}
				if(ary.length == 2){
					var n = ary[1];
					if(n.length > 5){
						alert("匯率只允許輸入到小數點第5位");
						$("#exchangeRate").val("");
						$("#exchangeRate").focus();
						return false;
					}					
				}
			}
			calculateMain();
		});
		//主檔承接比例欄位內容異動時觸發
		$("#undertakingRate").change(function() {
			if($("#undertakingRate").val() != "" && $("#undertakingRate").val() != null){
				var ary = $("#undertakingRate").val().toString().split("."); 
				if(ary.length > 2){
					alert("承接比例格式錯誤");
					$("#undertakingRate").val("");
					$("#undertakingRate").focus();
					return false;
				}
				if(ary.length == 2){
					var n = ary[1];
					if(n.length > 2){
						alert("承接比例只允許輸入到小數點第2位");
						$("#undertakingRate").val("");
						$("#undertakingRate").focus();
						return false;
					}
				}
				if(new Decimal($("#undertakingRate").val()) > 100){
					alert("承接比例不可超過100");
					$("#undertakingRate").val("");
					$("#undertakingRate").focus();
					return false;
				}
			}
			calculateMain();
		});
		//險種檔總保額欄位內容異動時觸發
		$("#insOriCurrAmount").change(function() {
			calculateInsDetail();
		});
		//險種檔總保費欄位內容異動時觸發
		$("#insOriCurrPrem").change(function() {
			calculateInsDetail();
		});
		//險種檔颱洪保額比例內容異動時觸發
		$("#insTypAmountRate").change(function() {
			calculateInsDetail();
		});
		//險種檔颱洪保費比例內容異動時觸發
		$("#insTypPremRate").change(function() {
			calculateInsDetail();
		});
		//險種檔地震保額比例內容異動時觸發
		$("#insEarthAmountRate").change(function() {
			calculateInsDetail();
		});
		//險種檔地震保費比例內容異動時觸發
		$("#insEarthPremRate").change(function() {
			calculateInsDetail();
		});
		//險種檔佣金率內容異動時觸發
		$("#insCommissionRate").change(function() {
			var exchangeRate = $("#exchangeRate").val().replace(/\,/g, '');
			var insOriCurrInwardPrem = $("#insOriCurrInwardPrem").val().replace(/\,/g, '');
			var insCommissionRate = $("#insCommissionRate").val();
			
			
			if(insCommissionRate == "" || insCommissionRate == null){
				alert("請先輸入佣金率");
				return false;
			}
			
			var ary = $("#insCommissionRate").val().toString().split("."); 
			if(ary.length > 2){
				alert("佣金率格式錯誤");
				$("#insCommissionRate").val("");
				$("#insCommissionRate").focus();
				return false;
			}
			if(ary.length == 2){
				var n = ary[1];
				if(n.length > 2){
					alert("佣金率只允許輸入到小數點第2位");
					$("#insCommissionRate").val("");
					$("#insCommissionRate").focus();
					return false;
				}
			}
			if(new Decimal($("#insCommissionRate").val()) > 100){
				alert("佣金率不可超過100");
				$("#insCommissionRate").val("");
				$("#insCommissionRate").focus();
				return false;
			}
			
			if(insOriCurrInwardPrem != "" && insCommissionRate != "" ){
				//(原幣)佣金
				$("#insOriCurrCommission").val(new Decimal(insOriCurrInwardPrem).times(new Decimal(insCommissionRate)).times(new Decimal(0.01)).toFixed(2));
				$("#insOriCurrCommission").val(formatNumber($("#insOriCurrCommission").val()));
			}
			if(insOriCurrInwardPrem != "" && insCommissionRate != "" && exchangeRate != ""){
				//(台幣)佣金
				$("#insNtCommission").val(new Decimal(insOriCurrInwardPrem).times(new Decimal(insCommissionRate)).times(new Decimal(exchangeRate)).times(new Decimal(0.01)).toFixed(0));
				$("#insNtCommission").val(formatNumber($("#insNtCommission").val()));
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
		
		//險種檔點儲存觸發動作
		$("#insAdd").click(function() {
			var key = $( "#insPoname option:selected" ).val();
			var insSerial = $("#insSerial").val();
			var reinsInwardInsData = new Object();
			//insSerial若有值則清空原本的
			if(insSerial != null){
				insObj[insSerial] = null;
			}
			reinsInwardInsData.oid = insObj.length + 1;
			reinsInwardInsData.poname = $( "#insPoname option:selected" ).text().split('-')[4];
			reinsInwardInsData.uwins = $("#insUwins").val();
			reinsInwardInsData.poins = $("#insPoins").val();
			reinsInwardInsData.inscode = $("#insInscode").val();
			reinsInwardInsData.actins = $("#insActins").val();
			reinsInwardInsData.typAmountRate = $("#insTypAmountRate").val();
			reinsInwardInsData.typPremRate = $("#insTypPremRate").val();
			reinsInwardInsData.earthAmountRate = $("#insEarthAmountRate").val();
			reinsInwardInsData.earthPremRate = $("#insEarthPremRate").val();
			reinsInwardInsData.deductibleNature = $("#insDeductibleNature").val();
			reinsInwardInsData.deductibleNonnature = $("#insDeductibleNonnature").val();
			reinsInwardInsData.oriCurrAmount = $("#insOriCurrAmount").val();
			reinsInwardInsData.ntAmount = $("#insNtAmount").val();
			reinsInwardInsData.oriCurrPrem = $("#insOriCurrPrem").val();
			reinsInwardInsData.ntPrem = $("#insNtPrem").val();
			reinsInwardInsData.oriCurrMaxAmount = $("#insOriCurrMaxAmount").val();
			reinsInwardInsData.ntMaxAmount = $("#insNtMaxAmount").val();
			reinsInwardInsData.oriCurrInwardAmount = $("#insOriCurrInwardAmount").val();
			reinsInwardInsData.ntInwardAmount = $("#insNtInwardAmount").val();
			reinsInwardInsData.oriCurrInwardPrem = $("#insOriCurrInwardPrem").val();
			reinsInwardInsData.ntInwardPrem = $("#insNtInwardPrem").val();
			reinsInwardInsData.oriCurrInwardMaxAmount = $("#insOriCurrInwardMaxAmount").val();
			reinsInwardInsData.ntInwardMaxAmount = $("#insNtInwardMaxAmount").val();
			reinsInwardInsData.commissionRate = $("#insCommissionRate").val();
			reinsInwardInsData.oriCurrCommission = $("#insOriCurrCommission").val();
			reinsInwardInsData.ntCommission = $("#insNtCommission").val();
			
			if(reinsInwardInsData.poname == "" ||reinsInwardInsData.poname == null){
				alert("請選擇險種名稱");
				$("#insPoname").focus();
				return false;
			}
			if(reinsInwardInsData.oriCurrAmount == "" ||reinsInwardInsData.oriCurrAmount == null){
				alert("請輸入總保額");
				$("#insOriCurrAmount").focus();
				return false;
			}
			if(reinsInwardInsData.ntAmount == "" ||reinsInwardInsData.ntAmount == null){
				alert("請輸入(台幣)總保額");
				$("#insNtAmount").focus();
				return false;
			}
			if(reinsInwardInsData.oriCurrPrem == "" ||reinsInwardInsData.oriCurrPrem == null){
				alert("請輸入(原幣)總保費");
				$("#insOriCurrPrem").focus();
				return false;
			}
			if(reinsInwardInsData.ntPrem == "" ||reinsInwardInsData.ntPrem == null){
				alert("請輸入(台幣)總保費");
				$("#insNtPrem").focus();
				return false;
			}
			if(reinsInwardInsData.commissionRate == "" ||reinsInwardInsData.commissionRate == null){
				alert("請輸入佣金率");
				$("#insCommissionRate").focus();
				return false;
			}
			if(insObj.length == 0){
				insObj[0] = reinsInwardInsData;	
			}else{
				insObj[insObj.length] = reinsInwardInsData;	
			}
			//清空輸入值
			clearInsData();
			$("#dialog").dialog("close");

			//若是存入颱洪或是地震時，要重算商火保費
			if("F-F-28-FT" == key || "F-F-25-FE01" == key || "F-F-3-FB1" == key){
				calculateFb1Prem();	
			}
			//計算總佣金
			calculateCommission();
			//建立grid
			reBuildTable();
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

		//validate
		$("#mainForm").validate({
			isShowLabel:false,
			isAlertLocalMsg:false,
			rules: {
				"reinsInwardMainData.applicantName":{
					"required":true
				},
				"reinsInwardMainData.applicantId":{
					"required":true
				},
				"reinsInwardMainData.insuredName":{
					"required":true
				},
				"reinsInwardMainData.insuredId":{
					"required":true
				},
				"reinsInwardMainData.policyNo":{
					"required":true
				},
				"reinsInwardMainData.startDate":{
					"required":true
				},
				"reinsInwardMainData.endDate":{
					"required":true
				},
				"reinsInwardMainData.classcode":{
					"required":true
				},
				"reinsInwardMainData.currency":{
					"required":true
				},
				"reinsInwardMainData.exchangeRate":{
					"required":true
				},
				"reinsInwardMainData.oriCurrAmount":{
					"required":true
				},
				"reinsInwardMainData.ntAmount":{
					"required":true
				},
				"reinsInwardMainData.oriCurrPrem":{
					"required":true
				},
				"reinsInwardMainData.ntPrem":{
					"required":true
				},
				"reinsInwardMainData.oriCurrInwardAmount":{
					"required":true
				},
				"reinsInwardMainData.ntInwardAmount":{
					"required":true
				},
				"reinsInwardMainData.oriCurrInwardPrem":{
					"required":true
				},
				"reinsInwardMainData.ntInwardPrem":{
					"required":true
				}
			},
			messages: {
				"reinsInwardMainData.applicantName":{
					"required":"請輸入要保人名稱!"
				},
				"reinsInwardMainData.applicantId":{
					"required":"請輸入要保人ID!"
				},
				"reinsInwardMainData.insuredName":{
					"required":"請輸入被保人名稱!"
				},
				"reinsInwardMainData.insuredId":{
					"required":"請輸入被保人ID!"
				},
				"reinsInwardMainData.policyNo":{
					"required":"請輸入保單號碼!"
				},
				"reinsInwardMainData.startDate":{
					"required":"請輸入保單起日!"
				},
				"reinsInwardMainData.endDate":{
					"required":"請輸入保單迄日!"
				},
				"reinsInwardMainData.classcode":{
					"required":"請選擇險種別！"
				},
				"reinsInwardMainData.currency":{
					"required":"請選擇幣別！"
				},
				"reinsInwardMainData.exchangeRate":{
					"required":"請輸入匯率！"
				},
				"reinsInwardMainData.oriCurrAmount":{
					"required":"請輸入(原幣)總保額！"
				},
				"reinsInwardMainData.ntAmount":{
					"required":"請輸入(台幣)總保額"
				},
				"reinsInwardMainData.oriCurrPrem":{
					"required":"請輸入(原幣)總保險費"
				},
				"reinsInwardMainData.ntPrem":{
					"required":"請輸入(台幣)總保險費"
				},
				"reinsInwardMainData.oriCurrInwardAmount":{
					"required":"請輸入(原幣)分進總保額"
				},
				"reinsInwardMainData.ntInwardAmount":{
					"required":"請輸入(台幣)分進總保額"
				},
				"reinsInwardMainData.oriCurrInwardPrem":{
					"required":"請輸入(原幣)分進總保費"
				},
				"reinsInwardMainData.ntInwardPrem":{
					"required":"請輸入(台幣)分進總保險費"
				}
			}	
		});
	});
	
	function form_submit(method){
		 $("label").html('');
		 if('create' == method){
			 
			 $("#reinsInwardInsDataStr").val(JSON.stringify(insObj)); 
			 
			 $("#mainForm").attr("action","btnCreate.action");
			 $("#mainForm").submit();
		 }
		 if('clear' == method){
				if (confirm("請確認是否放棄新增並返回查詢頁面？")==true){
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
				var options = '';
				options += '<option value=""></option>';
				for (var key in data){
					if(key == 'isExist'){
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
	
	function calculateMain() { 
		
		var oriCurrAmount = $("#oriCurrAmount").val().replace(/\,/g, '');	 
		var oriCurrPrem = $("#oriCurrPrem").val().replace(/\,/g, '');	 
		var oriCurrMaxAmount = $("#oriCurrMaxAmount").val().replace(/\,/g, '');	 
		var exchangeRate = $("#exchangeRate").val();
		var undertakingRate = $("#undertakingRate").val();
		//var commissionRate = $("#commissionRate").val();
		
		if(oriCurrAmount != "" && exchangeRate != "" ){
			//(台幣)總保險金額
			$("#ntAmount").val(new Decimal(oriCurrAmount).times(new Decimal(exchangeRate)).toFixed(0));
			$("#ntAmount").val(formatNumber($("#ntAmount").val()));
		}
		
		if(oriCurrAmount != "" && exchangeRate != "" && undertakingRate != "" ){
			//(台幣)總保險金額
			$("#ntInwardAmount").val(new Decimal(oriCurrAmount).times(new Decimal(undertakingRate)).times(new Decimal(0.01)).times(new Decimal(exchangeRate)).toFixed(0));
			$("#ntInwardAmount").val(formatNumber($("#ntInwardAmount").val()));
		}
		
		if(oriCurrAmount != "" && undertakingRate != ""){
			//(原幣)分進總保額金額 %
			$("#oriCurrInwardAmount").val(new Decimal(oriCurrAmount).times(new Decimal(undertakingRate)).times(new Decimal(0.01)).toFixed(2));
			$("#oriCurrInwardAmount").val(formatNumber($("#oriCurrInwardAmount").val()));
		}
		
		
		if(oriCurrPrem != "" && exchangeRate != "" ){
			//(台幣)總保險費
			$("#ntPrem").val(new Decimal(oriCurrPrem).times(new Decimal(exchangeRate)).toFixed(0));
			$("#ntPrem").val(formatNumber($("#ntPrem").val()));
		}

		if(oriCurrPrem != "" && exchangeRate != "" && undertakingRate != "" ){
			//(台幣)分進總保險費
			$("#ntInwardPrem").val(new Decimal(oriCurrPrem).times(new Decimal(undertakingRate)).times(new Decimal(0.01)).times(new Decimal(exchangeRate)).toFixed(0));
			$("#ntInwardPrem").val(formatNumber($("#ntInwardPrem").val()));
		}

		if(oriCurrPrem != "" && undertakingRate != "" ){
			//(原幣)分進總保險費
			$("#oriCurrInwardPrem").val(new Decimal(oriCurrPrem).times(new Decimal(undertakingRate)).times(new Decimal(0.01)).toFixed(2));
			$("#oriCurrInwardPrem").val(formatNumber($("#oriCurrInwardPrem").val()));
		}
		
		if(oriCurrMaxAmount != "" && exchangeRate != ""){
			//(台幣)最大點保額
			$("#ntMaxAmount").val(new Decimal(oriCurrMaxAmount).times(new Decimal(exchangeRate)).toFixed(0));
			$("#ntMaxAmount").val(formatNumber($("#ntMaxAmount").val()));
		}
		
		if(oriCurrMaxAmount != "" && undertakingRate != ""){
			//(原幣)分進最大點保額
			$("#oriCurrInwardMaxAmount").val(new Decimal(oriCurrMaxAmount).times(new Decimal(undertakingRate)).times(new Decimal(0.01)).toFixed(2));
			$("#oriCurrInwardMaxAmount").val(formatNumber($("#oriCurrInwardMaxAmount").val()));
		}
		
		if(oriCurrMaxAmount != "" && exchangeRate != "" && undertakingRate != ""){
			//(台幣)分進最大點保額
			$("#ntInwardMaxAmount").val(new Decimal(oriCurrMaxAmount).times(new Decimal(undertakingRate)).times(new Decimal(0.01)).times(new Decimal(exchangeRate)).toFixed(0));
			$("#ntInwardMaxAmount").val(formatNumber($("#ntInwardMaxAmount").val()));
		}
		
		//小數點2位及格式化
		$("#oriCurrAmount").val(formatNumber(new Decimal(oriCurrAmount).toFixed(2)));
		$("#oriCurrPrem").val(formatNumber(new Decimal(oriCurrPrem).toFixed(2)));
		//if(oriCurrPrem != "" && commissionRate != "" ){
			//(原幣)佣金
		//	$("#oriCurrCommission").val(new Decimal(oriCurrPrem).times(new Decimal(commissionRate)).times(new Decimal(0.01)));
		//	$("#oriCurrCommission").val(formatNumber($("#oriCurrCommission").val()));
		//}
		
		//if(oriCurrPrem != "" && commissionRate != "" && exchangeRate != ""){
			//(台幣)佣金
		//	$("#ntCommission").val(new Decimal(oriCurrPrem).times(new Decimal(commissionRate)).times(new Decimal(exchangeRate)).times(new Decimal(0.01)));
		//	$("#ntCommission").val(formatNumber($("#ntCommission").val()));
		//}

	}

	function calculateInsDetail(){ 
		//順序很重要，先算保額→保費→佣金
		//拿外面的原幣總保額
		var insOriCurrAmount = $("#oriCurrAmount").val().replace(/\,/g, '');
		//拿外面的原幣總保費
		var insOriCurrPrem = $("#oriCurrPrem").val().replace(/\,/g, '');
		var exchangeRate = $("#exchangeRate").val();
		var undertakingRate = $("#undertakingRate").val();
		var insTypAmountRate = $("#insTypAmountRate").val();
		var insTypPremRate = $("#insTypPremRate").val();
		var insEarthAmountRate = $("#insEarthAmountRate").val();
		var insEarthPremRate = $("#insEarthPremRate").val();
		
		var key = $("#insPoname option:selected" ).val();
		
		if("F-F-28-FT" == key || "F-F-25-FE01" == key){
			if(insTypAmountRate == ""){
				insTypAmountRate = "100";
			}
			if(insTypPremRate == ""){
				insTypPremRate = "100";
			}
			if(insEarthAmountRate == ""){
				insEarthAmountRate = "100";
			}
			if(insEarthPremRate == ""){
				insEarthPremRate = "100";
			}
		}
		
		//火險一年期商業颱風洪水
		if("F-F-28-FT" == key && insOriCurrAmount != "" && insTypAmountRate != ""){
			//保額=總保額*地震保額比例
			insOriCurrAmount = new Decimal(insOriCurrAmount).times(new Decimal(insTypAmountRate)).times(new Decimal(0.01)).toFixed(2);
			$("#insOriCurrAmount").val(insOriCurrAmount);
			$("#insOriCurrAmount").val(formatNumber($("#insOriCurrAmount").val()));
		}
		if("F-F-28-FT" == key && insOriCurrPrem != "" && insTypPremRate != ""){
			//保費=總保費*地震保費比例
			insOriCurrPrem = new Decimal(insOriCurrPrem).times(new Decimal(insTypPremRate)).times(new Decimal(0.01)).toFixed(2);
			$("#insOriCurrPrem").val(insOriCurrPrem);
			$("#insOriCurrPrem").val(formatNumber($("#insOriCurrPrem").val()));
		}
		//火險一年期商業地震保險
		if("F-F-25-FE01" == key && insOriCurrAmount != "" && insEarthAmountRate != ""){
			//保額=總保額*颱洪保額比例
			insOriCurrAmount = new Decimal(insOriCurrAmount).times(new Decimal(insEarthAmountRate)).times(new Decimal(0.01)).toFixed(2);
			$("#insOriCurrAmount").val(insOriCurrAmount);
			$("#insOriCurrAmount").val(formatNumber($("#insOriCurrAmount").val()));
		}
		if("F-F-25-FE01" == key && insOriCurrAmount != "" && insEarthPremRate != ""){
			//保費=總保費*颱洪保費比例
			insOriCurrPrem = new Decimal(insOriCurrPrem).times(new Decimal(insEarthPremRate)).times(new Decimal(0.01)).toFixed(2);
			$("#insOriCurrPrem").val(insOriCurrPrem);
			$("#insOriCurrPrem").val(formatNumber($("#insOriCurrPrem").val()));
		}
		
		//火險一年期商業
		if("F-F-3-FB1" == key){
			//保額	=總保額 → 不用處理
			//保費	=總保費-地震保費-颱洪保費
			if(insObj != null && insObj.length > 0 ){
				insOriCurrPrem = calculateFb1PremA(insOriCurrPrem);
				$("#insOriCurrPrem").val(formatNumber(insOriCurrPrem.times(new Decimal(undertakingRate)).toFixed(2)));
			}
		}
		
		if(insOriCurrAmount != "" && exchangeRate != "" ){
			//(台幣)總保額
			$("#insNtAmount").val(new Decimal(insOriCurrAmount).times(new Decimal(exchangeRate)).toFixed(0));
			$("#insNtAmount").val(formatNumber($("#insNtAmount").val()));
		}
		
		if(insOriCurrAmount != "" && exchangeRate != "" && undertakingRate != "" ){
			//(台幣)分進總保額
			$("#insNtInwardAmount").val(new Decimal(insOriCurrAmount).times(new Decimal(undertakingRate)).times(new Decimal(0.01)).times(new Decimal(exchangeRate)).toFixed(0));
			$("#insNtInwardAmount").val(formatNumber($("#insNtInwardAmount").val()));
		}
		
		if(insOriCurrAmount != "" && undertakingRate != ""){
			//(原幣)分進總保額金額 %
			$("#insOriCurrInwardAmount").val(new Decimal(insOriCurrAmount).times(new Decimal(undertakingRate)).times(new Decimal(0.01)).toFixed(2));
			$("#insOriCurrInwardAmount").val(formatNumber($("#insOriCurrInwardAmount").val()));
		}
		
		if(insOriCurrPrem != "" && exchangeRate != "" ){
			//(原幣)總保險費
			if(insOriCurrPrem.toString().indexOf(',') != -1){
				insOriCurrPrem = insOriCurrPrem.replace(/\,/g, '');
			}
			$("#insNtPrem").val(new Decimal(insOriCurrPrem).times(new Decimal(exchangeRate)).toFixed(0));
			$("#insNtPrem").val(formatNumber($("#insNtPrem").val()));
		}

		if(insOriCurrPrem != "" && exchangeRate != "" && undertakingRate != "" ){
			//(台幣)分進總保險費
			$("#insNtInwardPrem").val(new Decimal(insOriCurrPrem).times(new Decimal(undertakingRate)).times(new Decimal(0.01)).times(new Decimal(exchangeRate)).toFixed(0));
			$("#insNtInwardPrem").val(formatNumber($("#insNtInwardPrem").val()));
		}

		if(insOriCurrPrem != "" && undertakingRate != "" ){
			//(原幣)分進總保費
			$("#insOriCurrInwardPrem").val(new Decimal(insOriCurrPrem).times(new Decimal(undertakingRate)).times(new Decimal(0.01)).toFixed(2));
			$("#insOriCurrInwardPrem").val(formatNumber($("#insOriCurrInwardPrem").val()));
		}
		//小數點2位及格式化
		$("#insOriCurrAmount").val(formatNumber(new Decimal(insOriCurrAmount).toFixed(2)));
		$("#insOriCurrPrem").val(formatNumber(new Decimal(insOriCurrPrem).toFixed(2)));
	}
	
	//若先存入地震或颱洪時，計算一年期商火
	function calculateFb1PremA(sumPrem){
		if(insObj == null || insObj.length == 0 ){
			return;	
		}
		var prem = 0;
		for(var i = 0 ; i < insObj.length ; i++){
			var obj = insObj[i];
			if(obj == null){
				continue;
			}
			var key = obj.uwins + "-" + obj.poins + "-" + obj.inscode + "-" + obj.actins;
			//颱洪或地震
			if("F-F-28-FT" == key || "F-F-25-FE01" == key){
				var oriCurrPrem = obj.oriCurrPrem;
				if(oriCurrPrem == "" || oriCurrPrem == null ){
					oriCurrPrem = 0;
				}
				prem = new Decimal(prem).plus(new Decimal(oriCurrPrem));
			}
		}
		return new Decimal(sumPrem).sub(new Decimal(prem));
	}
	
	//若先存入商火時，再存入地震或颱洪，需重新計算一年期商火保費
	function calculateFb1Prem(){
		if(insObj == null || insObj.length == 0 ){
			return;
		}
		//總保費
		var exchangeRate = $("#exchangeRate").val();
		var undertakingRate = $("#undertakingRate").val();
		var oriCurrPrem = $("#oriCurrPrem").val().replace(/\,/g, '');	 
		var idx = null;
		var firObj = null;
		
		var prem = 0;
		for(var i = 0 ; i < insObj.length ; i++){
			var obj = insObj[i];
			if(obj == null){
				continue;
			}
			var key = obj.uwins + "-" + obj.poins + "-" + obj.inscode + "-" + obj.actins;
			//颱洪或地震
			if("F-F-28-FT" == key || "F-F-25-FE01" == key){
				var tmpPrem = obj.oriCurrPrem.replace(/\,/g, '');
				if(tmpPrem == "" || tmpPrem == null ){
					tmpPrem = 0;
				}
				prem = new Decimal(prem).plus(new Decimal(tmpPrem));
				continue;
			}
			//找出商火物件
			if("F-F-3-FB1" == key){
				idx = i;
				firObj = obj;
				continue;
			}
		}
		if(idx != null && firObj != null){
			var firPrem = new Decimal(oriCurrPrem).sub(new Decimal(prem));
			var commissionRate = firObj.commissionRate;
			if(commissionRate == "" || commissionRate == null){
				commissionRate = 0;
			}
			firObj.oriCurrPrem = formatNumber(new Decimal(firPrem).toFixed(2));
			//重新設定相關保費資料
			if(firPrem != "" && exchangeRate != "" ){
				//(台幣)總保費
				firObj.ntPrem = formatNumber(new Decimal(firPrem).times(new Decimal(exchangeRate)).toFixed(0));
			}
			if(firPrem != "" && undertakingRate != "" ){
				//(原幣)分進總保險費
				firObj.oriCurrInwardPrem = formatNumber(new Decimal(firPrem).times(new Decimal(undertakingRate)).times(new Decimal(0.01)).toFixed(2));
			}
			if(firPrem != "" && exchangeRate != "" && undertakingRate != "" ){
				//(台幣)分進總保險費
				firObj.ntInwardPrem = formatNumber(new Decimal(firPrem).times(new Decimal(undertakingRate)).times(new Decimal(0.01)).times(new Decimal(exchangeRate)).toFixed(0));
			}
			if(firPrem != "" && commissionRate != "" ){
				//(原幣)佣金
				firObj.oriCurrCommission = formatNumber(new Decimal(firPrem).times(new Decimal(undertakingRate)).times(new Decimal(commissionRate)).times(new Decimal(0.0001)).toFixed(2));
			}
			if(firPrem != "" && commissionRate != "" && exchangeRate != ""){
				//(台幣)佣金
				firObj.ntCommission = formatNumber(new Decimal(firPrem).times(new Decimal(undertakingRate)).times(new Decimal(commissionRate)).times(new Decimal(exchangeRate)).times(new Decimal(0.0001)).toFixed(0));
			}
			//塞回陣列
			insObj[idx] = firObj;
		}
	}
	//計算總佣金
	function calculateCommission(){
		var commission = 0;
		var ntCommission = 0;
		for(var i = 0 ; i < insObj.length ; i++){
			var obj = insObj[i];
			if(obj == null){
				continue;
			}
			if(obj.oriCurrCommission != "" && obj.oriCurrCommission != null){
				var tmp = obj.oriCurrCommission.toString();
				if(tmp.indexOf(',') != -1){
					tmp = tmp.replace(/\,/g, '');
				}
				commission = new Decimal(commission).plus(new Decimal(tmp));	
			}
			if(obj.ntCommission != "" && obj.ntCommission != null){
				var tmp = obj.ntCommission.toString();
				if(tmp.indexOf(',') != -1){
					tmp = tmp.replace(/\,/g, '');
				}
				ntCommission = new Decimal(ntCommission).plus(new Decimal(tmp));	
			}
		}
		$("#oriCurrCommission").val(formatNumber(commission));
		$("#ntCommission").val(formatNumber(ntCommission));
	}
	
	function copyMainToIns(){
		
		$("#insOriCurrAmount").val($("#oriCurrAmount").val());
		$("#insNtAmount").val($("#ntAmount").val());
		$("#insOriCurrPrem").val($("#oriCurrPrem").val());
		$("#insNtPrem").val($("#ntPrem").val());
		$("#insOriCurrMaxAmount").val($("#oriCurrMaxAmount").val());
		$("#insNtMaxAmount").val($("#ntMaxAmount").val());
		$("#insOriCurrInwardAmount").val($("#oriCurrInwardAmount").val());
		$("#insNtInwardAmount").val($("#ntInwardAmount").val());
		$("#insOriCurrInwardPrem").val($("#oriCurrInwardPrem").val());
		$("#insNtInwardPrem").val($("#ntInwardPrem").val());
		$("#insOriCurrInwardMaxAmount").val($("#oriCurrInwardMaxAmount").val());
		$("#insNtInwardMaxAmount").val($("#ntInwardMaxAmount").val());
		//$("#insCommissionRate").val($("#commissionRate").val());
		//$("#insOriCurrCommission").val($("#oriCurrCommission").val());
		//$("#insNtCommission").val($("#ntCommission").val());
		
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
			+ '<td align="center"><a href="#" onClick="updateDetail(' + obj.oid + ')">修改</a> &nbsp; <a href="#" onClick="delDetail(' + obj.oid + ')">刪除</a></td></tr>');
		}
		
	}
	//清空險種暫存資料
	function clearInsData(){
		$("#insSerial").val("");
		$("#insPoname").val("");
		$("#insUwins").val("");
		$("#insPoins").val("");
		$("#insInscode").val("");
		$("#insActins").val("");
		$("#insTypAmountRate").val("");
		$("#insTypPremRate").val("");
		$("#insEarthAmountRate").val("");
		$("#insEarthPremRate").val("");
		$("#insDeductibleNature").val("");
		$("#insDeductibleNonnature").val("");
		$("#insOriCurrAmount").val("");
		$("#insNtAmount").val("");
		$("#insOriCurrPrem").val("");
		$("#insNtPrem").val("");
		$("#insOriCurrInwardAmount").val("");
		$("#insNtInwardAmount").val("");
		$("#insOriCurrInwardPrem").val("");
		$("#insNtInwardPrem").val("");
		$("#insCommissionRate").val("");
		$("#insOriCurrCommission").val("");
		$("#insNtCommission").val("");
	}
	
	//帶出險種資料
	function updateDetail(idx) {
		//alert("idx = " + idx + ",length = " + insObj.length);
		var obj = insObj[idx - 1];
		$("#dialog").dialog("open");
		var key = obj.uwins + "-" + obj.poins + "-" + obj.inscode + "-" + obj.actins;
		$("#insSerial").val(idx - 1);
		$("#insPoname").val(key);
		$("#insUwins").val(obj.uwins);
		$("#insPoins").val(obj.poins);
		$("#insInscode").val(obj.inscode);
		$("#insActins").val(obj.actins);
		$("#insTypAmountRate").val(obj.typAmountRate);
		$("#insTypPremRate").val(obj.typPremRate);
		$("#insEarthAmountRate").val(obj.earthAmountRate);
		$("#insEarthPremRate").val(obj.earthPremRate);
		$("#insDeductibleNature").val(obj.deductibleNature);
		$("#insDeductibleNonnature").val(obj.deductibleNonnature);
		$("#insOriCurrAmount").val(obj.oriCurrAmount);
		$("#insNtAmount").val(obj.ntAmount);
		$("#insOriCurrPrem").val(obj.oriCurrPrem);
		$("#insNtPrem").val(obj.ntPrem);
		$("#insOriCurrInwardAmount").val(obj.oriCurrInwardAmount);
		$("#insNtInwardAmount").val(obj.ntInwardAmount);
		$("#insOriCurrInwardPrem").val(obj.oriCurrInwardPrem);
		$("#insNtInwardPrem").val(obj.ntInwardPrem);
		$("#insCommissionRate").val(obj.commissionRate);
		$("#insOriCurrCommission").val(obj.oriCurrCommission);
		$("#insNtCommission").val(obj.ntCommission);
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
	<input type="hidden" name="reinsInwardMainData.type" id="type" value="P" />
	<table id="table2" cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="MainTdColor" style="width: 200px" align="center"><span id="lbSearch"><b>新增作業</b></span></td>
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
					list="#{'0':'請選擇', 'A':'任意車險', 'B':'強制險', 'E':'工程險', 'F01':'商業火險', 'F02':'住火險', 'M':'水險', 'C':'新種險', 'C1':'傷害險'}" />
				</td>
				<td align="right" width="200px">作業類型：</td>
				<td align="left">保單</td>
			</tr>
			<tr>
				<td align="right" width="200px">分出保險公司：</td>
				<td align="left" colspan="3"><s:select key="reinsInwardMainData.cmnpNo" id="cmnpNo" theme="simple" 
					list="#{'':'', '01':'01-臺灣產物保險股份有限公司','02':'02-兆豐產物保險股份有限公司','05':'05-富邦產物保險股份有限公司','06':'06-和泰產物保險股份有限公司','07':'07-泰安產物保險股份有限公司','08':'08-明台產物保險股份有限公司',
					'09':'09-南山產物保險股份有限公司','10':'10-第一產物保險股份有限公司','12':'12-旺旺友聯產物保險股份有限公司','13':'13-新光產物保險股份有限公司','14':'14-華南產物保險股份有限公司','15':'15-國泰世紀產物保險股份有限公司',
					'17':'17-新安東京海上產物保險股份有限公司','28':'28-比利時商裕利安宜產物保險股份有限公司台灣分公司','29':'29-新加坡商美國國際產物保險股份有限公司台灣分公司','30':'30-法商科法斯產物保險股份有限公司台灣分公司',
					'32':'32-美商安達產物保險公司台北分公司','46':'46-法商法國巴黎產物保險股份有限公司台灣分公司'}" />
				
				</td>
			</tr>
			<tr>
				<td align="right" width="200px">分出保經代公司：</td>
				<td align="left" colspan="3">
					<s:textfield key="reinsInwardMainData.broker" id="broker" size="40" maxlength="40" />
				</td>
			</tr>
			<tr>
				<td align="right" width="200px">分進編號：</td>
				<td align="left"><s:textfield key="reinsInwardMainData.inwardNo" id="inwardNo" size="20" maxlength="20"/></td>
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
				<td align="right" width="200px">保單起迄日(YYYMMDD)：</td>
				<td align="left" >
					<s:textfield key="reinsInwardMainData.startDate" id="startDate" size="7" maxlength="7" cssClass="number"/>-
					<s:textfield key="reinsInwardMainData.endDate" id="endDate" size="7" maxlength="7" cssClass="number"/>
					
				</td>
				<td align="right" width="200px">批單起迄日：</td>
				<td align="left" ></td>
			</tr>
			<tr>
				<td align="right" width="200px">簽單日：</td>
				<td align="left" ><s:property value="reinsInwardMainData.signDate" /></td>
				<td align="right" width="200px"></td>
				<td align="left" ></td>
			</tr>
			<tr>
				<td align="right" width="200px">要保人名稱：</td>
				<td align="left"><s:textfield key="reinsInwardMainData.applicantName" id="applicantName" size="30" maxlength="50"/></td>
				<td align="right" width="200px">要保人ID：</td>
				<td align="left"><s:textfield key="reinsInwardMainData.applicantId" id="applicantId" size="20" maxlength="20" /></td>
			</tr>
			<tr>
				<td align="right" width="200px">被保人名稱：</td>
				<td align="left"><s:textfield key="reinsInwardMainData.insuredName" id="insuredName" size="30" maxlength="50"/></td>
				<td align="right" width="200px">被保人ID：</td>
				<td align="left">
					<s:textfield key="reinsInwardMainData.insuredId" id="insuredId" size="20" maxlength="20" />
					<s:checkbox key="sameApplicantId" id="sameApplicantId" />同要保人
				</td>
			</tr>
			<tr>
				<td align="right" width="200px">標的物地址/主保單號：</td>
				<td align="left" colspan="3"><s:textfield key="reinsInwardMainData.objInfo" id="objInfo" size="100" maxlength="150"/></td>
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
					list="#{'TWD':'台幣', 'USD':'USD-美金', 'GBP':'GBP-英鎊', 'EUR':'EUR-歐元', 'HKD':'HKD-港幣', 'CNY':'CNY-人民幣', 'SGD':'SGD-新加坡幣', 
					'JPY':'JPY-日圓', 'KRW':'KRW-韓元', 'AUD':'AUD-澳幣', 'NZD':'NZD-紐元', 'SEK':'SEK-瑞典幣', 'CHF':'CHF-瑞士法郎', 'CAD':'CAD-加拿大幣', 
					'PHP':'PHP-菲國比索', 'THB':'THB-泰幣', 'VND':'VND-越南幣', 'IDR':'IDR-印尼幣', 'MYR':'MYR-馬來幣', 'ZAR':'ZAR-南非幣'}" />
				</td>
				<td align="right" width="200px">匯率：</td>
				<td align="left" ><s:textfield key="reinsInwardMainData.exchangeRate" id="exchangeRate" size="10" maxlength="10" cssClass="numberDot" value="1"/></td>				
			</tr>
			<tr>
				<td align="right" width="200px">承接比例：</td>
				<td align="left" ><s:textfield key="reinsInwardMainData.undertakingRate" id="undertakingRate" size="10" maxlength="10" cssClass="numberDot"/>%</td>
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
				<td align="left"><s:textfield key="reinsInwardMainData.oriCurrAmount" id="oriCurrAmount" size="30" maxlength="30" cssClass="replaceComa numberDot addComa"/></td>
				<td align="right" width="200px">總保費：</td>
				<td align="left"><s:textfield key="reinsInwardMainData.oriCurrPrem" id="oriCurrPrem" size="30" maxlength="30" cssClass="replaceComa numberDot addComa"/></td>
			</tr>
			<tr>
				<td align="right" width="200px">最大點保額：</td>
				<td align="left"><s:textfield key="reinsInwardMainData.oriCurrMaxAmount" id="oriCurrMaxAmount" size="30" maxlength="30" cssClass="replaceComa numberDot addComa"/></td>
				<td align="right" width="200px"></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td colspan="3"><br></td>
			</tr>
			<tr>
				<td align="right" width="200px">分進總保額：</td>
				<td align="left"><s:textfield key="reinsInwardMainData.oriCurrInwardAmount" id="oriCurrInwardAmount" size="30" maxlength="30" cssClass="replaceComa numberDot addComa"/></td>
				<td align="right" width="200px">分進總保費：</td>
				<td align="left"><s:textfield key="reinsInwardMainData.oriCurrInwardPrem" id="oriCurrInwardPrem" size="30" maxlength="30" cssClass="replaceComa numberDot addComa"/></td>
			</tr>
			<tr>
				<td align="right" width="200px">分進最大點保額：</td>
				<td align="left"><s:textfield key="reinsInwardMainData.oriCurrInwardMaxAmount" id="oriCurrInwardMaxAmount" size="30" maxlength="30" cssClass="replaceComa numberDot addComa"/></td>
				<td align="right" width="200px">佣金：</td>
				<td align="left"><s:textfield key="reinsInwardMainData.oriCurrCommission" id="oriCurrCommission" size="30" maxlength="30" cssClass="replaceComa numberDot addComa"/></td>
			</tr>
		</table>
	</fieldset>
	<fieldset style="width:800px;">
	    <legend>台幣</legend>
		<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="870px" border="0">
			<tr>
				<td align="right" width="200px">總保額：</td>
				<td align="left"><s:textfield key="reinsInwardMainData.ntAmount" id="ntAmount" size="30" maxlength="30" cssClass="replaceComa number addComa" tabindex="-1"/></td>
				<td align="right" width="200px">總保險費：</td>
				<td align="left"><s:textfield key="reinsInwardMainData.ntPrem" id="ntPrem" size="30" maxlength="30" cssClass="replaceComa number addComa" tabindex="-1"/></td>
			</tr>
			<tr>
				<td align="right" width="200px">最大點保額：</td>
				<td align="left"><s:textfield key="reinsInwardMainData.ntMaxAmount" id="ntMaxAmount" size="30" maxlength="30" cssClass="replaceComa number addComa" tabindex="-1"/></td>
				<td align="right" width="200px"></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td colspan="3"><br></td>
			</tr>
			<tr>
				<td align="right" width="200px">分進總保額：</td>
				<td align="left"><s:textfield key="reinsInwardMainData.ntInwardAmount" id="ntInwardAmount" size="30" maxlength="30" cssClass="replaceComa number addComa" tabindex="-1"/></td>
				<td align="right" width="200px">分進總保費：</td>
				<td align="left"><s:textfield key="reinsInwardMainData.ntInwardPrem" id="ntInwardPrem" size="30" maxlength="30" cssClass="replaceComa number addComa" tabindex="-1"/></td>
			</tr>
			<tr>
				<td align="right" width="200px">分進最大點保額：</td>
				<td align="left"><s:textfield key="reinsInwardMainData.ntInwardMaxAmount" id="ntInwardMaxAmount" size="30" maxlength="30" cssClass="replaceComa number addComa" tabindex="-1"/></td>
				<td align="right" width="200px">佣金：</td>
				<td align="left"><s:textfield key="reinsInwardMainData.ntCommission" id="ntCommission" size="30" maxlength="30" cssClass="replaceComa number addComa" tabindex="-1"/></td>
			</tr>
		</table>
	</fieldset>
	<fieldset style="width:800px;">
	    <legend>險種明細</legend>
		<table id="gridtable" border="1" class="main_table"  width="870px"></table>
  		<table width="870px" cellpadding="0" cellspacing="0" >
			<tr>
				<td align="center">
					<p><input type="button" value="新增險種" id="insOpener"/>
				</td>
			</tr>
		</table>
	    <div id="dialog" title="新增險種">
			<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="870px" border="0">
				<tr>
					<td align="right" width="250px">險種名稱：</td>
					<td align="left" width="250px" ><s:select key="reinsInwardInsData.poname" id="insPoname" theme="simple" list="#{'':''}" onchange=""/></td>
					<td align="right" width="250px"></td>
					<td align="left"><s:hidden key="insSerial" id="insSerial" /></td>			
				</tr>
				<tr>
					<td align="right" width="250px">大險種：</td>
					<td align="left"><s:textfield key="reinsInwardInsData.uwins" id="insUwins" size="2" maxlength="2"/></td>
					<td align="right" width="250px">出單險種：</td>
					<td align="left"><s:textfield key="reinsInwardInsData.poins" id="insPoins" size="5" maxlength="5" /></td>
				</tr>
				<tr>
					<td align="right" width="250px">30險種：</td>
					<td align="left"><s:textfield key="reinsInwardInsData.inscode" id="insInscode" size="5" maxlength="5"/></td>
					<td align="right" width="250px">財務險種：</td>
					<td align="left"><s:textfield key="reinsInwardInsData.actins" id="insActins" size="5" maxlength="5" /></td>
				</tr>
				<tr>
					<td align="right" width="200px">颱洪保額比例：</td>
					<td align="left" ><s:textfield key="reinsInwardInsData.typAmountRate" id="insTypAmountRate" size="10" maxlength="10"/>%</td>
					<td align="right" width="200px">颱洪保費比例：</td>
					<td align="left" ><s:textfield key="reinsInwardInsData.typPremRate" id="insTypPremRate" size="10" maxlength="10" />%</td>
				</tr>
				<tr>
					<td align="right" width="200px">地震保額比例：</td>
					<td align="left" ><s:textfield key="reinsInwardInsData.earthAmountRate" id="insEarthAmountRate" size="10" maxlength="10"/>%</td>
					<td align="right" width="200px">地震保費比例：</td>
					<td align="left" ><s:textfield key="reinsInwardInsData.earthPremRate" id="insEarthPremRate" size="10" maxlength="10" />%</td>
				</tr>
				<tr>
					<td align="right" width="200px" >(非天災)自負額：</td>
					<td align="left" ><s:textfield key="reinsInwardInsData.deductibleNonnature" id="insDeductibleNonnature" size="30" maxlength="30" /></td>
					<td align="right" width="200px">(天災)自負額：</td>
					<td align="left" ><s:textfield key="reinsInwardInsData.deductibleNature" id="insDeductibleNature" size="30" maxlength="30"/></td>
				</tr>
				<tr>
					<td align="right" width="200px">佣金率：</td>
					<td align="left" colspan="3"><s:textfield key="reinsInwardInsData.commissionRate" id="insCommissionRate" size="10" maxlength="10" cssClass="replaceComa numberDot addComa"/>%</td>
				</tr>
			</table>
		<fieldset style="width:850px;">
	    <legend>原幣</legend>
			<table>
				<tr>
					<td align="right" width="200px">總保額：</td>
					<td align="left"><s:textfield key="reinsInwardInsData.oriCurrAmount" id="insOriCurrAmount" size="30" maxlength="30" cssClass="replaceComa numberDot addComa"/></td>
					<td align="right" width="200px">總保費：</td>
					<td align="left"><s:textfield key="reinsInwardInsData.oriCurrPrem" id="insOriCurrPrem" size="30" maxlength="30" cssClass="replaceComa numberDot addComa"/></td>
				</tr>
				<tr>
					<td align="right" width="200px">分進總保額：</td>
					<td align="left"><s:textfield key="reinsInwardInsData.oriCurrInwardAmount" id="insOriCurrInwardAmount" size="30" maxlength="30" cssClass="replaceComa numberDot addComa"/></td>
					<td align="right" width="200px">分進總保費：</td>
					<td align="left"><s:textfield key="reinsInwardInsData.oriCurrInwardPrem" id="insOriCurrInwardPrem" size="30" maxlength="30" cssClass="replaceComa numberDot addComa"/></td>
				</tr>
				<tr>
					<td align="right" width="200px">(原幣)佣金：</td>
					<td align="left"><s:textfield key="reinsInwardInsData.oriCurrCommission" id="insOriCurrCommission" size="30" maxlength="30" cssClass="replaceComa numberDot addComa"/></td>
					<td align="right" width="200px"></td>
					<td align="left"></td>
				</tr>
			</table>
		</fieldset>
		<fieldset style="width:850px;">
	    <legend>台幣</legend>
			<table>
				<tr>
					<td align="right" width="200px">總保額：</td>
					<td align="left"><s:textfield key="reinsInwardInsData.ntAmount" id="insNtAmount" size="30" maxlength="30" cssClass="replaceComa number addComa" tabindex="-1" /></td>
					<td align="right" width="200px">總保費：</td>
					<td align="left"><s:textfield key="reinsInwardInsData.ntPrem" id="insNtPrem" size="30" maxlength="30" cssClass="replaceComa number addComa" tabindex="-1" /></td>
				</tr>
				<tr>
					<td align="right" width="200px">分進總保額：</td>
					<td align="left"><s:textfield key="reinsInwardInsData.ntInwardAmount" id="insNtInwardAmount" size="30" maxlength="30" cssClass="replaceComa number addComa" tabindex="-1" /></td>
					<td align="right" width="200px">分進總保費：</td>
					<td align="left"><s:textfield key="reinsInwardInsData.ntInwardPrem" id="insNtInwardPrem" size="30" maxlength="30" cssClass="replaceComa number addComa" tabindex="-1" /></td>
				</tr>
				<tr>
					<td align="right" width="200px">佣金：</td>
					<td align="left"><s:textfield key="reinsInwardInsData.ntCommission" id="insNtCommission" size="30" maxlength="30" cssClass="replaceComa number addComa" tabindex="-1" /></td>
					<td align="right" width="200px"></td>
					<td align="left"></td>
				</tr>
			</table>
		</fieldset>
			<table width="970px" cellpadding="0" cellspacing="0" >
				<tr>
					<td align="center">
						<p><input type="button" value="儲存" id="insAdd"/>
					</td>
				</tr>
			</table>
	    </div>

	</fieldset>
	<fieldset style="width:800px;">
	    <legend>收費資訊</legend>
		<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="870px">
			<tr>
				<td align="right" width="200px"> </td>
				<td align="left"> </td>
				<td align="right" width="200px"> </td>
				<td align="left"> </td>
			</tr>
			<tr>
				<td align="right" width="200px">期數：</td>
				<td align="left" colspan="3"><s:textfield key="reinsInwardMainData.payNo" id="payNo" size="3" maxlength="3"/></td>
			</tr>

		</table>
	</fieldset>
	<fieldset style="width:800px;">
	    <legend>其他</legend>
		<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="870px">
			<tr>
				<td align="right" width="200px">備註：</td>
				<td align="left" colspan="3"><s:textarea key="reinsInwardMainData.comments" id="comments" rows="5" cols="50" /></td>
			</tr>
		</table>
	</fieldset>
	<table width="970px">
		<tr>
			<td align="center"><input value="存檔" type="button" onclick="javascript:form_submit('create');">&nbsp;&nbsp;&nbsp;&nbsp; <input value="取消" type="button" onclick="javascript:form_submit('clear');" /></td>
		</tr>
	</tr>
	</table>
</s:form>
</body>
</html>