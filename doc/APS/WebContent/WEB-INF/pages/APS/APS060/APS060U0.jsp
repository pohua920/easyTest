<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String title = "元大續保資料處理作業";
	String image = path + "/" + "images/";
	String GAMID = "APS060U0";
	String mDescription = "元大續保資料處理作業";
	String nameSpace = "/aps/060";
%>
<!-- mantis：FIR0668_0217，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language="JavaScript">

var insObj = [];
var detail = '<c:out value="${insuredDataStr}" escapeXml="false"/>';

$(document).ready(function(){
	
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
	
	
	if(detail != "" && detail != null){
		insObj = JSON.parse(detail);
		for(var i = 0 ; i < insObj.length ; i++){
			insObj[i].birthday = null;
			insObj[i].dcreate = null;
			insObj[i].dupdate = null;
		}
		reBuildTable();
	}
	
	if($("#fixUser").val() != "" && $("#dataStatus").val() != "C"){
		$('#table3 input').attr("readonly",true).attr("class","txtLabel");
		$('#table3 select').attr("disabled",true).attr("class","txtLabel");
		$("#saveButton").css("display", "none");
		$("#lockingButton").css("display", "none");
		$("#checkButton").css("display", "none");
		$("#msgTable").css("display", "none");
	}

	var moneyArr = ['amountF','amountQ','premiumF','premiumQ', 'amountFLast',
		'amountQLast','premiumFLast','premiumQLast'];
	for(var i=0;i<moneyArr.length;i++){
		$("[id='"+moneyArr[i]+"']").val(transThousands($("[id='"+moneyArr[i]+"']").val()));
		$("[id='"+moneyArr[i]+"']").bind("change",function(){
			$(this).val(transThousands($(this).val()));
		});
	}
	
	$('#startdate').datepicker({dateFormat:"yyyy/mm/dd"});
	$('#startdate').bind("change",function(){
		var startdate = $(this).val();
		if(startdate == "" || !checkDate(startdate,"ad")){
			alertFunction($(this),"保單生效日必填且需為合理日期");
		}else{
			checkStructureLevel();
		}
	});
	
	$('#comcode').bind("blur",function(){
		var comcode = $(this).val();
		if(comcode == ""){
			alertFunction($(this),"歸屬單位必填");
		}
	});
	
	$('#handler1code').bind("blur",function(){
		var handler1code = $(this).val();
		if(handler1code == ""){
			alertFunction($(this),"服務人員必填");
		}
	});
	
	//mantis：FIR0668，處理人員：BJ085，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 20250310議題重啟 start
	/*
	$('#addresscode').bind("blur",function(){
		var addresscode = $(this).val();
		if(addresscode == ""){
			alertFunction($(this),"郵遞區號必填");
		}else{
	 		ajaxAction('addresscode',addresscode);
		}
	});
	*/
	//mantis：FIR0668，處理人員：BJ085，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 20250310議題重啟 end
	
	$('#wallmaterial').bind("blur",function(){
		var wallmaterial = $(this).val();
		if(wallmaterial == ""){
			alertFunction($(this),"外牆必填");
		}else{
	 		ajaxAction('wallmaterial',wallmaterial);
		}
	});
	
	$('#roofmaterial').bind("blur",function(){
		var roofmaterial = $(this).val();
		if(roofmaterial == ""){
			alertFunction($(this),"屋頂必填");
		}else{
	 		ajaxAction('roofmaterial',roofmaterial);
		}
	});
	
	$("#sumfloors").bind("blur",function(){
		var regex = /^[0-9]*$/;
		var sumfloors = $(this).val();
		if(sumfloors == "" || !regex.test(sumfloors) || parseInt(sumfloors) == 0){
			alertFunction($(this),"總樓層數需有內容值且應為正整數、數字應>0");
		}else if(parseInt(sumfloors) >= 25 ){
	 		$("#highrisefee").val(15);
	 		checkStructureLevel();
		}else if(parseInt(sumfloors) >= 15 ){
			$("#highrisefee").val(10);
			checkStructureLevel();
		}else{
			$("#highrisefee").val(0);
			checkStructureLevel();
		}
	});
	
	$("#buildyears").bind("blur",function(){
		var buildyears = $(this).val();
		var theDate = new Date();
		var year = theDate.getFullYear()-1911;
		var regex = /^[0-9]*$/;
		if(buildyears == "" || !regex.test(buildyears) || parseInt(buildyears) <1 || parseInt(buildyears) > year){
			alertFunction($(this),"建築年分必填且應介於1~今年之間");
		}
	});
	
	$("#identifynumber").bind("blur",function(){
		var identifynumber = $(this).val();
		if(identifynumber == ""){
			alertFunction($(this),"證號號碼必填");
		}else{
	 		ajaxAction('identifynumber',identifynumber);
		}
	});
	
	$('#postcode').bind("blur",function(){
		var postcode = $(this).val();
		if(postcode == ""){
			$('#postname').val("");
		}else{
	 		ajaxAction('postcode',postcode);			
		}
	});
	
	$('#birthday').datepicker({dateFormat:"yy/mm/dd"});
	$('#birthday').bind("change",function(){
		$('#birthday').val(transLen($(this).val()));
	});

	$('#handleridentifynumber').bind("blur",function(){
		if($(this).val() == ""){
			alertFunction($(this),"業務員登錄證號必填");
		}else{
			//將「業務員登錄證號」連動檢核移除
// 			ajaxAction('handleridentifynumber');
		}
	});
	
	$('#temp2').bind("blur",function(){
		if($(this).val() == ""){
			alertFunction($(this),"分行員編必填");
		}
	});
	
	$('#extracomcode').bind("blur",function(){
		if($(this).val() == ""){
			alertFunction($(this),"分行代號必填");
		}
	});

	$('#extracomname').bind("blur",function(){
		if($(this).val() == ""){
			alertFunction($(this),"分行名稱必填");
		}
	});
	
	$('#sfFlag').bind("blur",function(){
		if($(this).val() == "N"){
			$('#sfReason').val('');
		}
	});
	
	$('#insuredflag').bind("change",function(){
		var insuredflag = $(this).val();
		var maxInsuredSeqA = 0;
		var maxInsuredSeqI = 0;
		
		for(var i = 0 ; i < insObj.length ; i++){
			//取得要被保人目前最大序號
			if(insObj[i].insuredflag == "1" && insObj[i].insuredSeq > maxInsuredSeqI){
				maxInsuredSeqI = insObj[i].insuredSeq;
			}
			if(insObj[i].insuredflag == "2" && insObj[i].insuredSeq > maxInsuredSeqA){
				maxInsuredSeqA = insObj[i].insuredSeq;
			}
		}
		
		if("1"==insuredflag){
			$('#insuredSeq').val(parseInt(maxInsuredSeqI)+parseInt(1));
		}
		if("2"==insuredflag){
			$('#insuredSeq').val(parseInt(maxInsuredSeqA)+parseInt(1));
		}
		
		insuredidentityJudge(insuredflag);
	});
	
	//點新增要被保人觸發動作
	$("#insOpener").click(function() {
		clearInsuredData();
		
		$("#insuredflag").attr("disabled",false).attr("class","txtLabel");
		
		$("#dialog").dialog("open");
		$("#autoOpen").val(true);
	});
	
	//要被保人檔點選儲存觸發動作
	$("#insuredSave").click(function() {
		
		var ajaxReturnType = "Y";//Y 通過檢核; N 未通過
		
		var insuredSerial = $("#insuredSerial").val();
		var firAgtTocoreInsured = new Object();
		
		firAgtTocoreInsured.batchNo = $("#batchNo").val();
		firAgtTocoreInsured.batchSeq = $("#batchSeq").val();
		firAgtTocoreInsured.insuredSeq = $("#insuredSeq").val();
		firAgtTocoreInsured.insuredflag = $("#insuredflag").val();
		firAgtTocoreInsured.insuredname = $("#insuredname").val();
		firAgtTocoreInsured.identifynumber = $("#identifynumber").val();
		firAgtTocoreInsured.insurednature = $("#insurednature").val();
		firAgtTocoreInsured.identifytype = $("#identifytype").val();
		firAgtTocoreInsured.phonenumber = $("#phonenumber").val();
		firAgtTocoreInsured.mobile = $("#mobile").val();
		firAgtTocoreInsured.postcode = $("#postcode").val();
		firAgtTocoreInsured.postaddress = $("#postaddress").val();
		firAgtTocoreInsured.domicile = $("#domicile").val();
		firAgtTocoreInsured.countryename = $("#countryename").val();
		firAgtTocoreInsured.ishighdengeroccupation = $("#ishighdengeroccupation").val();
		firAgtTocoreInsured.headname = $("#headname").val();
		firAgtTocoreInsured.listedcabinetcompany = $("#listedcabinetcompany").val();
		firAgtTocoreInsured.strBirthday = $("#birthday").val();
		firAgtTocoreInsured.gender = $("#gender").val();
		firAgtTocoreInsured.insuredidentity = $("#insuredidentity").val();
		firAgtTocoreInsured.occupationcode = $("#occupationcode").val();
		
		if(firAgtTocoreInsured.insuredname == "" ||firAgtTocoreInsured.insuredname == null){
			alert("姓名必填");
			$("#insuredflag").focus();
			return false;
		}
		
		if(firAgtTocoreInsured.identifynumber == "" ||firAgtTocoreInsured.identifynumber == null){
			alert("證號號碼必填");
			$("#insuredflag").focus();
			return false;
		}else{
			ajaxAction('identifynumber',firAgtTocoreInsured.identifynumber);
		}
		
		/*mantis：FIR0668，處理人員：BJ085，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業  start */
// 		if(firAgtTocoreInsured.postcode == "" ||firAgtTocoreInsured.postcode == null){
// 			alert("郵遞區號必填");
// 			$("#postcode").focus();
// 			return false;
// 		}else{
// 			ajaxAction('postcode',firAgtTocoreInsured.postcode);
// 		}
		/*mantis：FIR0668，處理人員：BJ085，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業  */
		
		if(firAgtTocoreInsured.domicile == "" ||firAgtTocoreInsured.domicile == null){
			alert("居住地/註冊地必填");
			$("#domicile").focus();
			return false;
		}else{
			ajaxAction('domicile',firAgtTocoreInsured.domicile);
			ajaxReturnType = $("#ajaxReturnType").val();
			if("N" == ajaxReturnType){
				return false;
			}
		}
		
		if(firAgtTocoreInsured.countryename == "" ||firAgtTocoreInsured.countryename == null){
			alert("國籍必填");
			$("#countryename").focus();
			return false;
		}else{
			ajaxAction('countryename',firAgtTocoreInsured.countryename);
			ajaxReturnType = $("#ajaxReturnType").val();
			if("N" == ajaxReturnType){
				return false;
			}
		}
		
		if(firAgtTocoreInsured.ishighdengeroccupation == "" ||firAgtTocoreInsured.ishighdengeroccupation == null){
			alert("是否高危職業必填");
			$("#ishighdengeroccupation").focus();
			return false;
		}
		
		if(firAgtTocoreInsured.headname == "" && firAgtTocoreInsured.insurednature == "4"){
			alert("若客戶類型為法人時，法人代表人必需輸入");
			$("#headname").focus();
			return false;
		}
		
		if(firAgtTocoreInsured.listedcabinetcompany == "" && firAgtTocoreInsured.insurednature == "4"){
			alert("若客戶類型為法人時，上市櫃公司必需輸入");
			$("#listedcabinetcompany").focus();
			return false;
		}
		
		if(firAgtTocoreInsured.strBirthday == "" ||firAgtTocoreInsured.strBirthday == null || !checkDate(firAgtTocoreInsured.strBirthday,"roc")){
			alert("生日/註冊日必填且應為合理日期，格式YYY/MM/DD");
			$("#birthday").focus();
			return false;
		}
		
		if(firAgtTocoreInsured.mobile != "" && 
				(firAgtTocoreInsured.mobile.substr(0,2) !== "09" || firAgtTocoreInsured.mobile.length !== 10 || !checkIsnum(firAgtTocoreInsured.mobile))){
			alert("行動電話需為09開頭共10位數字");
			$("#mobile").focus();
			return false;
		}
		
		/*mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 start
		  20240813-台銀續保處理作業不做電話檢核*/
// 		if(firAgtTocoreInsured.mobile == "" && firAgtTocoreInsured.phonenumber == ""){
// 			alert("市話與行動電話需擇一輸入");
// 			$("#mobile").focus();
// 			$("#phonenumber").focus();
// 			return false;
// 		}
		/*mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 end*/
		
		if(firAgtTocoreInsured.postaddress == "" || firAgtTocoreInsured.postaddress == null){
			alert("通訊地址必填");
			$("#postaddress").focus();
			return false;
		}
		
		if(firAgtTocoreInsured.insuredflag == 2 && (firAgtTocoreInsured.insuredidentity == "" || firAgtTocoreInsured.insuredidentity == null)){
			alert("與被保人關係必填");
			$("#insuredidentity").focus();
			return false;
		}
		
		//insuredSerial若有值則帶回原本物件
		debugger;
		if(insuredSerial != null && insuredSerial != ""){
			insObj[insuredSerial] = firAgtTocoreInsured;
		}else{//若為空則為新增
			insObj[insObj.length] = firAgtTocoreInsured;
		}
		
		//清空輸入值
		clearInsuredData();
		$("#dialog").dialog("close");

		//建立grid
		reBuildTable();
	});
	
	$("#dialog").dialog({
		modal: true,
		autoOpen: false,
		height : "auto", 
		width : "auto",
		close:function(event, ui){ // 對話框關閉時觸發的方法
			clearInsuredData();
		}
	});
});

	function checkAjaxReturn(){
		ajaxReturnType = $("#ajaxReturnType").val();
		if("N" == $("#ajaxReturnType").val()){
			return false;
		}
	}

	//險種檔datagrid
	function reBuildTable(){
		
		$('#gridtable tr').remove();
		$('#gridtable').append('<tr align="center" style="background-color: #87CEFA;border-color: #428ABD;">'
			+ '<th width="20px">序號</th>'
			+ '<th width="50px">類型</th>'
			+ '<th width="60px">姓名</th>'
			+ '<th width="80px">證照號碼</th>'
			+ '<th width="80px">市話</th>'
			+ '<th width="80px">行動電話</th>'
			+ '<th width="80px">生日/註冊日</th>'
			+ '<th width="50px">郵遞區號</th>'
			+ '<th width="250px">通訊地址</th>'
			+ '<th width="60px"></th></tr>');
		
		var insuredflagStr = "";
		
		for(var i = 0 ; i < insObj.length ; i++){
			var obj = insObj[i];
			if(obj == null){
				continue;
			}
			
			if("1" == obj.insuredflag){
				insuredflagStr = "被保人";
			}else if("2" == obj.insuredflag){
				insuredflagStr = "要保人";
			}
			
			if($("#fixUser").val() != ""){
				$('#gridtable').append('<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF" >'
				+ '<td align="center">' + obj.insuredSeq + '</td>'
				+ '<td align="center">' + insuredflagStr + '</td>'
				+ '<td align="center">' + obj.insuredname + '</td>'
				+ '<td align="center">' + obj.identifynumber + '</td>'
				+ '<td align="center">' + obj.phonenumber + '</td>'
				+ '<td align="center">' + obj.mobile + '</td>'
				+ '<td align="center">' + obj.strBirthday + '</td>'
				+ '<td align="center">' + obj.postcode + '</td>'
				+ '<td align="center">' + obj.postaddress + '</td></tr>');
				
			} else {
				$('#gridtable').append('<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF" >'
				+ '<td align="center">' + obj.insuredSeq + '</td>'
				+ '<td align="center">' + insuredflagStr + '</td>'
				+ '<td align="center">' + obj.insuredname + '</td>'
				+ '<td align="center">' + obj.identifynumber + '</td>'
				+ '<td align="center">' + obj.phonenumber + '</td>'
				+ '<td align="center">' + obj.mobile + '</td>'
				+ '<td align="center">' + obj.strBirthday + '</td>'
				+ '<td align="center">' + obj.postcode + '</td>'
				+ '<td align="center">' + obj.postaddress + '</td>'
				+ '<td align="center"><a href="#" onClick="updateDetail(' + (i + 1) + ')">修改</a> &nbsp; <a href="#" onClick="delDetail(' + (i + 1) + ')">刪除</a></td></tr>');
			}
		}
		
	}
	
	//帶出要被保人資料
	function updateDetail(idx) {
		var obj = insObj[idx - 1];
		$("#dialog").dialog("open");
		$("#insuredSeq").val(obj.insuredSeq);
		$("#insuredflag").val(obj.insuredflag);
		$("#insuredSerial").val(idx - 1);
		$("#insuredname").val(obj.insuredname);
		$("#identifynumber").val(obj.identifynumber);
		$("#insurednature").val(obj.insurednature);
		$("#identifytype").val(obj.identifytype);
		$("#phonenumber").val(obj.phonenumber);
		$("#mobile").val(obj.mobile);
		$("#postcode").val(obj.postcode);
		$("#postname").val(obj.postname);
		$("#postaddress").val(obj.postaddress);
		$("#domicile").val(obj.domicile);
		$("#countryename").val(obj.countryename);
		$("#ishighdengeroccupation").val(obj.ishighdengeroccupation);
		$("#birthday").val(obj.strBirthday);
		$("#listedcabinetcompany").val(obj.listedcabinetcompany);
		$("#headname").val(obj.headname);
		$("#insurednature").attr("disabled",true).attr("class","txtLabel");
		$("#identifytype").attr("disabled",true).attr("class","txtLabel");
		$("#insuredflag").attr("disabled",true).attr("class","txtLabel");
		$("#gender").val(obj.gender);
		$("#insuredidentity").val(obj.insuredidentity);
		$("#occupationcode").val(obj.occupationcode);
		
		insurednatureJudge(obj.insurednature);
		insuredidentityJudge(obj.insuredflag);
		
	}
	
	//刪除要被保人資料
	function delDetail(idx) {
		if (confirm('請確認是否刪除？')){
			insObj.splice(idx-1, 1);
			
			//重新排序要被保人序號
			var insSeq = 1;
			var appSeq = 1;
			for(var i = 0 ; i < insObj.length; i++){
				var insuredflag = insObj[i].insuredflag;
				if(insuredflag == "1") {
					insObj[i].insuredSeq = insSeq;
					insSeq++;
				}
				if(insuredflag == "2") {
					insObj[i].insuredSeq = appSeq;
					appSeq++;
				}
			}
			
			reBuildTable();
		}
	}
	
	//清空險種暫存資料
	function clearInsuredData(){
		$("#insuredSerial").val("");
		$("#insuredSeq").val("");
		$("#insuredflag").val("");
		$("#insuredname").val("");
		$("#identifynumber").val("");
		$("#insurednature").val("");
		$("#identifytype").val("");
		$("#phonenumber").val("");
		$("#mobile").val("");
		$("#postcode").val("");
		$("#postname").val("");
		$("#postaddress").val("");
		$("#domicile").val("");
		$("#countryename").val("");
		$("#ishighdengeroccupation").val("");
		$("#birthday").val("");
		$("#listedcabinetcompany").val("");
		$("#headname").val("");
		$("#gender").val("");
		$("#insuredidentity").val("");
		$("#occupationcode").val("");
	}
	
	//將西元年轉為民國年
	function transLen(date){
		var dateAry = date.split('/');
		if(dateAry.length == 3){
			var year = dateAry[0];
			if(year.length == 1){
				year = "00"+year;
			}else if(year.length == 2){
				year = "0"+year;
			}
			date = year+"/"+dateAry[1]+"/"+dateAry[2]; 
		}
		return date;
	}

	function ajaxAction(action,value){
		//取出ajax xml相對路徑
		var contextPath = '<%=request.getScheme()%>' + '://<%=request.getServerName()+":"+request.getServerPort()+request.getContextPath()%>';
		var path = '';
		var	data = new FormData($('#mainForm')[0]);
		if(action == 'verifyBasicData'){
			$("#insuredDataStr").val(JSON.stringify(insObj));
			data.set('insuredDataStr', $("#insuredDataStr").val());
	    	path = contextPath + '/aps/ajax017/verifyYcbBasicData.action?checkType='+value;
		}
		var async = false;
		
		if(action == 'addresscode' || action == 'postcode'){
			path = contextPath + '/aps/ajax006/findPrpdNewCodeBycodetype.action?codetype=PostAddress&codecode='+value;
		}
		if(action == 'wallmaterial'){
			path = contextPath + '/aps/ajax006/findPrpdNewCodeBycodetype.action?codetype=WallMaterial&codecode='+value;
		}
		if(action == 'roofmaterial'){
			path = contextPath + '/aps/ajax006/findPrpdNewCodeBycodetype.action?codetype=RoofMaterial&codecode='+value;
		}
		if(action == 'identifynumber'){
			path = contextPath + '/aps/ajax006/verifyIdentifynumber.action?identifynumber='+value;
		}
		if(action == 'domicile' || action == 'countryename'){
			path = contextPath + '/aps/ajax006/findPrpdNewCodeBycodetype.action?codetype=countryCName&codecode='+value;
		}
		if(action == 'birthday'){
			path = contextPath + '/aps/ajax006/checkDateLessThanNewDate.action?date='+value;
		}
		if(action == 'structureLeval'){
			path = contextPath + '/aps/ajax017/checkStructureLeval.action';
		}
		if(action == 'handleridentifynumber'){
			path = contextPath + '/aps/ajax017/checkHandleridentifynumber.action';
		}
		
		//執行ajax
		$.ajax({
			url : path,
			type: 'POST',
			data: data,
			dataType: 'json',
			timeout: 10000,
			contentType: false,
			processData: false,
			async: async,
			cache: false,
			error: function (data, status, e){
				ajaxError(data, status);
			},
			success: function (data, status){
				ajaxSuccess(action, data);
			}
		});
	}
	
	function ajaxSuccess(action, data){
		//ajax回傳的data存在，將ajax回傳的data設定至頁面上
		
		if(action == 'handleridentifynumber'){
// 			if(data.extracomcode != null || data.extracomname != null){
// 				$("#extracomcode").val(data.extracomcode);
// 				$("#extracomname").val(data.extracomname);
// 			}
			
			if(data.orgicode != null || data.orgicode != null){
				$("#temp2").val(data.orgicode);
			}
			
			if(data.handler1code != null || data.handler1code != null){
				$("#handler1code").val(data.handler1code);
				$("#comcode").val(data.comcode);
			}
			
			if(data.errMsg != null || data.errMsg != null){
				alertFunction($("#handleridentifynumber"), data.errMsg);
			}
		}
		
		if(action == 'addresscode'){
			if(data.isExist){
				$("#addressname").val(data.codename);
			}else{
				alertFunction($("#addresscode"), "查無郵遞區號");
			}
		}
		
		if(action == 'wallmaterial'){
			if(data.isExist){
				$("#wallname").val(data.codename);
				checkStructureLevel();
			}else{
				alertFunction($("#wallmaterial"), "外牆輸入錯誤");
			}
		}
		
		if(action == 'roofmaterial'){
			if(data.isExist){
				$("#roofname").val(data.codename);
				checkStructureLevel();
			}else{
				alertFunction($("#roofmaterial"), "屋頂輸入錯誤");
			}
		}
		
		if(action == 'identifynumber'){
			if(data.errMsg != null){
				alertFunction($("[id='identifynumber']"), "證照號碼："+data.errMsg);
			}else{
				$("[id='insurednature']").val(data.insuredNature);
				$("[id='identifytype']").val(data.idType);
				$("[id='gender']").val(data.gender);
				insurednatureJudge(data.insuredNature);
			}
		}
		
		if(action == 'postcode'){
			if(data.isExist){
				$("[id='postname']").val(data.codename);
			}else{
				alertFunction($("[id='postcode']"),"查無郵遞區號");
			}
		}
		
		if(action == 'domicile' && !data.isExist){
			alertFunction($("[id='domicile']"), "查無居住地/住冊地");
			$("[id='ajaxReturnType']").val("N"); //N代表未通過檢核
		}
		
		if(action == 'countryename' && !data.isExist){
			alertFunction($("[id='countryename']"), "查無國籍");
			$("[id='ajaxReturnType']").val("N"); //N代表未通過檢核
		}
		
		if(action == 'birthday' && !data.isExist){
			alertFunction($("[id='birthday']"), data.errMsg);
			$("[id='ajaxReturnType']").val("N"); //N代表未通過檢核
		}
		
		if(action == 'verifyBasicData'){
			Object.keys(data).forEach(function(key){
				$("[id='"+key+"']").val(data[key]);
			});
			
			var insuredInfoList = [];
			insuredInfoList = data.insuredInfoList;
			
			if(insuredInfoList != null){
				for(var i = 0 ; i < insObj.length; i++){
					var insuredflag = insObj[i].insuredflag;
					var insuredSeq = insObj[i].insuredSeq;
					
					for(var j = 0 ; j < insuredInfoList.length; j++){
						if(insuredInfoList[j].insuredflag == insuredflag && insuredInfoList[j].insuredSeq == insuredSeq){
							insObj[i].insurednature = insuredInfoList[j].insurednature;
							insObj[i].idType = insuredInfoList[j].idType;
							insObj[i].gender = insuredInfoList[j].gender;
						}
					}
				}
			}
			
			if(data.checkMsg != null){
				alert(data.checkMsg);
			}
			
			if($("#errMsg").val()==""){
				alert("資料檢查完成!");
			}
		}
		
		if(action == 'structureLeval'){
			if(data.errMsg != null){
				alertFunction($("#structure"), data.errMsg);
			}else{
				$("#structure").val(data.structure);
				$("#structureText").val(data.structureText);
			}
		}
	}

	//ajax發生錯誤時會呼叫的method
	function ajaxError(data, status){
		alert("ajax驗證錯誤");
	}
	
	function checkStructureLevel(){
		if($("#startdate").val() != "" && $("#roofmaterial").val() != "" 
				&& $("#wallmaterial").val() != "" && $("#sumfloors").val() != ""){
			ajaxAction("structureLeval");
		}
	}
	
	function checkDate(date, type){
		var adRegex=/^([0-9]\d{3}\/(0?[1-9]|1[012])\/(0?[1-9]|[12][0-9]|3[01]))*$/;
		var rocRegex=/^([0-1]?\d{1,2}\/(0+[1-9]|1[012])\/(0+[1-9]|[12][0-9]|3[01]))*$/;
		var errMsg = "";
		if(type=="ad" && !adRegex.test(date)){
			return false;
		}
		if(type=="roc" && !rocRegex.test(date)){
			return false;
		}
		return true;
	}
	
	/*mantis：FIR0668，處理人員：BJ085，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 start */
	function saveData(type){
		var msg = "";
		if(type == "save"){
			msg = "是否儲存?";
		}else if(type == "lock"){
			msg = "請注意，鎖定後將無法再修改資料，請確認是否進行存檔及鎖定?";
		}
		
		if (confirm(msg)==false){
			return false;
		}
		
		var app = false;
		var ins = false;
		for(var i = 0 ; i < insObj.length; i++){
			var insuredflag = insObj[i].insuredflag;
			if(insuredflag == "1") ins = true;
			if(insuredflag == "2") app = true;
		}
		if(!ins){
			alert("至少需有一筆被保險人資料，請確認!");
			return false;
		}
		if(!app){
			alert("至少需有一筆要保人資料，請確認!");
			return false;
		}
		
		if(type == "save"){
			$("#locking").val("N");
		}else if(type == "lock"){
			$("#locking").val("Y");
		}
		
		if($("#sfFlag").val() == "Y" && $("#sfReason").val() == ""){
			alert("剔退原因必填。");
			return false;
		}else if(($("#sfFlag").val() == "Y")){
			form_submit("update");
			return true;
		}
		
		//基本資料檢查
		ajaxAction('verifyBasicData','save');
		if($("#errMsg").val()!=""){
			alert("「資料檢查結果」仍有異常，請先確認資料正確性。");
			return false;
		}
		
		form_submit("update");
	}
	/*mantis：FIR0668，處理人員：BJ085，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 end */
	
	function transThousands(num){
		if(num != ""){
			num = num.replace(",","");
			var regexZero = new RegExp("([0]*)([0-9]+)", "g");
			num = num.replace(regexZero,"$2");
			var regex = new RegExp("(\\d{1,3})(?=(\\d{3})+(?:$|\\D))", "g");
			num = num.replace(regex,"$1,");
		}
		return num;
	}
	
	function transNum(str){
		str = str.replace(",","");
		return str;
	}
	
	function checkIsnum(str){
		var regex = /^[0-9]+$/;
		if(regex.test(str)){
			return true;
		}
		return false;
	}

	function form_submit(type){
		if("update" == type){
			$("#insuredDataStr").val(JSON.stringify(insObj)); 
 			$("#mainForm").attr("action","btnUpdate.action");
 			$("#mainForm").submit();
		}
	}
	
	function showMsg(msg){
		alert(msg);
	}
	
	var msg = "<%=request.getAttribute("message")%>";
	if('' != msg && 'null' != msg){
		showMsg(msg);
	}
	
	function alertFunction(ele, errMsg){
		 var tmp = $('<input>');
		    tmp.css({"display":"inline-block","width":"0","height":"0","opacity":"0"});
		    ele.after(tmp);
		    tmp.focus();
		    tmp.remove();
		    alert(errMsg);
	}
	
	function toUpperCase(element){
		element.value = element.value.toUpperCase()
	}
	
	function insurednatureJudge(insurednature){
		if(insurednature == "3"){
			$("[id='listedcabinetcompany']").val("").attr("class","txtLabel").attr("disabled",true);
			$("[id='headname']").val("").attr("class","txtLabel").attr("readonly",true);
		}else if(insurednature == "4"){
			$("[id='listedcabinetcompany']").attr("disabled",false).removeAttr("class","");
			$("[id='headname']").attr("readonly",false).removeAttr("class","");
		}
	}
	
	function insuredidentityJudge(insuredflag){
		if("1"==insuredflag){
			$("[id='insuredidentity']").val("").attr("class","txtLabel").attr("disabled",true);
		}
		if("2"==insuredflag){
			$("[id='insuredidentity']").attr("disabled",false).removeAttr("class","");
		}
	}
	
</script>
</head>

<body style="margin:0;text-align:left">
<table id="Table11" cellSpacing="1" cellPadding="1" width="970px" border="0">
		<tr>
		<td width="485px">
			<img src="${pageContext.request.contextPath}/images/Help_Icon.gif" border="0" width="49" height="26">
		</td>
		<td align="right" width="485px">PGMID：<%=GAMID%></td>
	</tr>
	<tr>
		<td align="center" colSpan="2"><h3><%=title%></h3></td>
	</tr>
</table>

<!-- clear form -->
<s:form theme="simple" action="lnkGoQuery" namespace="/aps/060" id="clearForm" name="clearForm"/>
<!-- form 開始 -->
<s:form theme="simple" namespace="/aps/060" id="mainForm" name="mainForm">	
	<input type="hidden" name="insuredDataStr" id="insuredDataStr" />
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tr bgcolor="white">
			<td class="MainTdColor" width="200px" align="center">
				<span id="lbSearch"><b>修改作業</b></span>
			</td>
			<td colspan="2" class="image"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">批次號碼：</td>
			<td width="200px" align="left">
				<s:property value="ycbDetailVo.batchNo"/>
				<s:hidden key="ycbDetailVo.batchNo" id="batchNo"></s:hidden>
			</td>
			<td width="150px" align="right">序號：</td>
			<td width="200px" align="left">
				<s:property value="ycbDetailVo.batchSeq"/>
				<s:hidden key="ycbDetailVo.batchSeq" id="batchSeq"></s:hidden>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">資料狀態：</td>
			<td width="200px" align="left">
				<c:choose>
					<c:when test="${ycbDetailVo.dataStatus == '0'}">未處理</c:when>
					<c:when test="${ycbDetailVo.dataStatus == '1'}">APS暫存失敗</c:when>
					<c:when test="${ycbDetailVo.dataStatus == '2'}">APS暫存成功</c:when>
					<c:when test="${ycbDetailVo.dataStatus == '3'}">轉核心暫存成功</c:when>
					<c:when test="${ycbDetailVo.dataStatus == '4'}">轉核心暫存失敗</c:when>
					<c:when test="${ycbDetailVo.dataStatus == '5'}">轉核心要保成功</c:when>
					<c:when test="${ycbDetailVo.dataStatus == '6'}">轉核心要保失敗</c:when>
					<c:when test="${ycbDetailVo.dataStatus == '7'}">轉核心完成</c:when>
					<c:when test="${ycbDetailVo.dataStatus == '8'}">轉核心失敗-寫入收付失敗</c:when>
					<c:when test="${ycbDetailVo.dataStatus == 'A'}">人工判定不轉核心</c:when>
					<c:when test="${ycbDetailVo.dataStatus == 'B'}">臺銀已鎖定尚未比對簽署檔</c:when>
					<c:when test="${ycbDetailVo.dataStatus == 'C'}">臺銀比對簽署檔不一致</c:when>
					<c:otherwise>未定義</c:otherwise>
				</c:choose>
			</td>
			<td width="150px" align="right">續保約定：</td>
			<td width="200px" align="left">
				<s:property value="ycbDetailVo.isAutoRenew"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">轉檔異常訊息：</td>
			<td width="200px" align="left">
				<s:property value="ycbDetailVo.checkErrMsg"/>
			</td>
		</tr>
		<tr><td width="150px" align="right"></td></tr>
		<tr>
			<td width="150px" align="right">轉檔提醒訊息：</td>
			<td width="200px" align="left">
				<s:property value="ycbDetailVo.checkWarnMsg"/>
			</td>
		</tr>
		<tr><td width="150px" align="right"></td></tr>
		<tr>
			<td width="150px" align="right">說明：</td>
			<td width="800px" align="left">
				<span>「轉檔異常訊息、轉檔提醒訊息」僅為排程程式轉入時針對保代資料進行檢核的結果。若已在本作業進行資料修改，請使用按鈕「資料檢核」重新確認調整後的資料是否合理。</span>
			</td>
		</tr>
	</table>
	<span>=========================================================================基本資料========================================================================</span>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">續保單號：</td>
			<td width="200px" align="left">
				<s:textfield key="ycbDetailVo.oldpolicyno" id="oldpolicyno" onkeyup="toUpperCase(this);" size="20" maxLength="40"/>
			</td>
			<td width="150px" align="right"></td>
			<td width="200px" align="left">
			</td>
<!-- 			<td width="150px" align="right">受理編號：</td> -->
<!-- 			<td width="200px" align="left"> -->
<%-- 				<s:textfield key="ycbDetailVo.orderseq" id="orderseq" maxLength="20" size="20"/> --%>
<!-- 			</td> -->
		</tr>
		<tr>
			<td width="150px" align="right">歸屬單位：</td>
			<td width="200px" align="left">
<%-- 				<s:textfield key="ycbDetailVo.comcode" id="comcode" readonly="true" cssClass="txtLabel" maxLength="10" size="10"/> --%>
				<s:textfield key="ycbDetailVo.comcode" id="comcode" maxLength="10" size="10"/>
			</td>
			<td width="150px" align="right">服務人員：</td>
			<td width="200px" align="left">
<%-- 				<s:textfield key="ycbDetailVo.handler1code" id="handler1code" readonly="true" cssClass="txtLabel" maxLength="20" size="20"/> --%>
				<s:textfield key="ycbDetailVo.handler1code" id="handler1code" maxLength="20" size="20"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">保單生效日：</td>
			<td width="200px" align="left">
				<s:textfield key="ycbDetailVo.startdate" id="startdate" maxLength="10" size="10"/>
			</td>
			<td width="150px" align="right">業務員登錄證號：</td>
			<td width="200px" align="left">
				<s:textfield key="ycbDetailVo.handleridentifynumber" id="handleridentifynumber" onkeyup="toUpperCase(this);" maxLength="20" size="20"/>
			</td>
			<td width="150px" align="right"></td>
		</tr>
<!-- 		<tr> -->
			
<!-- 			<td width="200px" align="left"> -->
<!-- 			</td> -->
<!-- 			<td width="150px" align="right">分行員編：</td> -->
<!-- 			<td width="200px" align="left"> -->
<%-- 				<s:textfield key="ycbDetailVo.temp2" id="temp2" maxLength="10" size="10"/> --%>
<!-- 			</td> -->
<!-- 		</tr> -->
		<tr>
			<td width="150px" align="right">分行代號：</td>
			<td width="200px" align="left">
				<s:textfield key="ycbDetailVo.extracomcode" id="extracomcode" maxLength="20" size="10" />
			</td>
			<td width="150px" align="right">分行名稱：</td>
			<td width="200px" align="left">
				<s:textfield key="ycbDetailVo.extracomname" id="extracomname" maxLength="20" size="20"/>
			</td>
		</tr>
	</table>
	<span>=========================================================================抵押權人========================================================================</span>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">抵押權人代碼：</td>
			<td width="200px" align="left">
				<s:textfield key="ycbDetailVo.mortgageepcode1" id="mortgageepcode1" readonly="true" cssClass="txtLabel" maxLength="20" size="20"/>
			</td>
			<td width="150px" align="right">授信號碼：</td>
			<td width="200px" align="left">
				<s:textfield key="ycbDetailVo.creditnumber1" id="creditnumber1" maxLength="40" size="20"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">擔保品編號：</td>
			<td width="200px" align="left">
				<s:textfield key="ycbDetailVo.collateralnumber1" id="collateralnumber1" maxLength="40" size="20"/>
			</td>
			<td width="150px" align="right">貸款編號：</td>
			<td width="200px" align="left">
				<s:textfield key="ycbDetailVo.loansbehalfnumber1" id="loansbehalfnumber1" maxLength="40" size="20"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">放款帳號：</td>
			<td width="200px" align="left">
				<s:textfield key="ycbDetailVo.loanaccount1" id="loanaccount1" maxLength="40" size="20"/>
			</td>
			<td width="150px" align="right">放款部門別：</td>
			<td width="200px" align="left">
				<s:textfield key="ycbDetailVo.loansdepartment1" id="loansdepartment1" maxLength="40" size="20"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">扣款帳號：</td>
			<td width="200px" align="left">
						<!-- mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 第二階段 -->
				<s:textfield key="ycbDetailVo.accountno" id="accountno" maxLength="40" size="40"/>
			</td>
		</tr>
	</table>
	<span>==========================================================================標的物=========================================================================</span>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">郵遞區號：</td>
			<td width="200px" align="left">
				<s:textfield key="ycbDetailVo.addresscode" id="addresscode" maxLength="3" size="3"/>
			</td>
			<td width="150px" align="right">縣市行政區：</td>
			<td width="200px" align="left">
				<s:textfield key="ycbDetailVo.addressname" id="addressname" readonly="true" cssClass="txtLabel" maxLength="40" size="40"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">標的物地址：</td>
			<td width="200px" align="left">
				<s:textfield key="ycbDetailVo.addressdetailinfo" id="addressdetailinfo" maxLength="200" size="50"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">外牆：</td>
			<td width="200px" align="left">
				<s:textfield key="ycbDetailVo.wallmaterial" id="wallmaterial" maxLength="5" size="5"/>
				<s:textfield key="ycbDetailVo.wallname" id="wallname" value="" readonly="true" cssClass="txtLabel"></s:textfield>
			</td>
			<td width="150px" align="right">屋頂：</td>
			<td width="200px" align="left">
				<s:textfield key="ycbDetailVo.roofmaterial" id="roofmaterial" maxLength="10" size="10"/>
				<s:textfield key="ycbDetailVo.roofname" id="roofname" value="" readonly="true" cssClass="txtLabel"></s:textfield>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">使用面積(坪)：</td>
			<td width="200px" align="left">
				<s:textfield key="ycbDetailVo.buildarea" id="buildarea" maxLength="10" size="10"/>
			</td>
			<td width="150px" align="right">總樓層數：</td>
			<td width="200px" align="left">
				<s:textfield key="ycbDetailVo.sumfloors" id="sumfloors" maxLength="10" size="10"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">建築等級：</td>
			<td width="200px" align="left">
				<s:textfield key="ycbDetailVo.structure" id="structure" readonly="true" cssClass="txtLabel" maxLength="5" size="5"/>
			</td>
			<td width="150px" align="right">建築年分：</td>
			<td width="200px" align="left">
				<s:textfield key="ycbDetailVo.buildyears" id="buildyears" maxLength="3" size="3"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">建築等級說明：</td>
			<td width="200px" align="left">
				<s:textfield key="ycbDetailVo.structureText" id="structureText" readonly="true" cssClass="txtLabel" maxLength="50" size="50"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">複保險序號：</td>
			<td width="200px" align="left">
				<s:textfield key="ycbDetailVo.repeatpolicyno" id="repeatpolicyno" readonly="true" cssClass="txtLabel" maxLength="15" size="20"/>
			</td>
			<td width="150px" align="right">複保險結果：</td>
			<td width="200px" align="left">
				<s:textfield key="ycbDetailVo.dquakeStatus" id="dquakeStatus" readonly="true" cssClass="txtLabel" maxLength="40" size="40"/>
			</td>
		</tr>
	</table>
	
	<span>=======================================================================承保內容-本期======================================================================</span>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">火險保額：</td>
			<td width="200px" align="left">
				<s:textfield key="ycbDetailVo.amountF" id="amountF" maxLength="10" size="10"/>
			</td>
			<td width="150px" align="right">地震險保額：</td>
			<td width="200px" align="left">
				<s:textfield key="ycbDetailVo.amountQ" id="amountQ" maxLength="10" size="10"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">火險保費：</td>
			<td width="200px" align="left">
				<s:textfield key="ycbDetailVo.premiumF" id="premiumF" maxLength="10" size="10"/>
			</td>
			<td width="150px" align="right">地震險保費：</td>
			<td width="200px" align="left">
				<s:textfield key="ycbDetailVo.premiumQ" id="premiumQ" maxLength="10" size="10"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">高樓加費：</td>
			<td width="200px" align="left">
				<s:textfield key="ycbDetailVo.highrisefee" id="highrisefee" readonly="true" cssClass="txtLabel" maxLength="14" size="14"/>
			</td>
		</tr>
	</table>
	
	<span>=======================================================================承保內容-前期======================================================================</span>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">前期火險保額：</td>
			<td width="200px" align="left">
				<s:textfield key="ycbDetailVo.amountFLast" id="amountFLast" readonly="true" cssClass="txtLabel" maxLength="12"/>
			</td>
			<td width="150px" align="right">前期地震保額：</td>
			<td width="200px" align="left">
				<s:textfield key="ycbDetailVo.amountQLast" id="amountQLast" readonly="true" cssClass="txtLabel" maxLength="12"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">前期火險保費：</td>
			<td width="200px" align="left">
				<s:textfield key="ycbDetailVo.premiumFLast" id="premiumFLast" readonly="true" cssClass="txtLabel" maxLength="12"/>
			</td>
			<td width="150px" align="right">前期地震保費：</td>
			<td width="200px" align="left">
				<s:textfield key="ycbDetailVo.premiumQLast" id="premiumQLast" readonly="true" cssClass="txtLabel" maxLength="12"/>
			</td>
		</tr>
	</table>
	<span>=======================================================================要被保人資料=======================================================================</span>	
	<fieldset style="width:800px;">
		<table id="gridtable" border="1" class="main_table"  width="870px"></table>
  		<table width="870px" cellpadding="0" cellspacing="0" >
			<tr>
				<td align="center">
					<p><input type="button" value="新增要被保人資料" id="insOpener"/>
				</td>
			</tr>
		</table>
		<div id="dialog" title="修改要被保人資料">
			<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="870px" border="0">
				<tr>
					<td align="right" width="250px">要/被保人：</td>
					<td align="left">
						<s:select key="firAgtTocoreInsured.insuredflag" id="insuredflag" theme="simple" list="#{'1':'被保險人','2':'要保人'}"/>
					</td>
					<s:hidden key="insuredSerial" id="insuredSerial" />
					<td align="right" width="250px">序號：</td>
					<td align="left" class="insuredSeq">
						<s:textfield key="firAgtTocoreInsured.insuredSeq" id="insuredSeq" size="1" maxlength="1" class="txtLabel" readonly="true" />
					</td>
				</tr>
				<tr>
					<td align="right" width="250px">姓名：</td>
					<td align="left"><s:textfield key="firAgtTocoreInsured.insuredname" id="insuredname" size="10" maxlength="100"/></td>
					<td align="right" width="250px">證照號碼：</td>
					<td align="left"><s:textfield key="firAgtTocoreInsured.identifynumber" id="identifynumber" size="10" maxlength="10" /></td>
				</tr>
				<tr>
					<td align="right" width="250px">與被保人關係：</td>
					<td align="left">
						<s:select key="firAgtTocoreInsured.insuredidentity" id="insuredidentity" theme="simple" list="#{'01':'本人', '02':'配偶', '03':'法人', '04':'父母', '05':'其他', '08':'負責人'}"/>
					</td>
				</tr>
				<tr>
					<td align="right" width="250px">客戶類型：</td>
					<td align="left" width="250px" >
						<s:select key="firAgtTocoreInsured.insurednature" id="insurednature" theme="simple" list="#{'3':'個人','4':'法人'}"/>
					</td>
					<td align="right" width="250px">證照類型：</td>
					<td align="left" width="250px" >
						<s:select key="firAgtTocoreInsured.identifytype" id="identifytype" theme="simple" list="#{'01':'身份證','04':'護照','05':'居留證','60':'統編'}"/>
						<s:hidden key="firAgtTocoreInsured.gender" id="gender" />
					</td>
				</tr>
				<tr>
					<td align="right" width="200px">市話：</td>
					<td align="left" ><s:textfield key="firAgtTocoreInsured.phonenumber" id="phonenumber" size="10" maxlength="20"/></td>
					<td align="right" width="200px">行動電話：</td>
					<td align="left" ><s:textfield key="firAgtTocoreInsured.mobile" id="mobile" size="10" maxlength="10" /></td>
				</tr>
				<tr>
					<td align="right" width="200px">郵遞區號：</td>
					<td align="left" ><s:textfield key="firAgtTocoreInsured.postcode" id="postcode" size="3" maxlength="3"/>
					<s:textfield key="postname" id="postname" readonly="true" cssClass="txtLabel"/></td>
				</tr>
				<tr>
					<td align="right" width="200px" >通訊地址：</td>
					<td align="left" ><s:textfield key="firAgtTocoreInsured.postaddress" id="postaddress" size="50" maxlength="120" /></td>
				</tr>
				<tr>
					<td align="right" width="200px">居住地/註冊地：</td>
					<td align="left"><s:textfield key="firAgtTocoreInsured.domicile" id="domicile" size="20" maxlength="20"/></td>
					<td align="right" width="200px">國籍：</td>
					<td align="left"><s:textfield key="firAgtTocoreInsured.countryename" id="countryename" size="20" maxlength="20"/></td>
				</tr>
				<tr>
					<td align="right" width="250px">行職業別：</td>
					<td align="left" width="250px" ><s:textfield key="firAgtTocoreInsured.occupationcode" id="occupationcode" size="20" maxlength="20"/></td>
				<tr>
					<td align="right" width="250px">是否高危職業：</td>
					<td align="left" width="250px" ><s:select key="firAgtTocoreInsured.ishighdengeroccupation" id="ishighdengeroccupation" theme="simple" list="#{'':'', '0':'高風險', '1':'低風險'}" /></td>
					<td align="right" width="200px">生日/註冊日：</td>
					<td align="left"><s:textfield key="firAgtTocoreInsured.strBirthday" id="birthday" size="20" maxlength="20"/></td>
				</tr>
				<tr>
					<td align="right" width="250px">上市櫃公司：</td>
					<td align="left" width="250px" ><s:select key="firAgtTocoreInsured.listedcabinetcompany" id="listedcabinetcompany" theme="simple" list="#{'':'', '0':'上市櫃', '1':'非上市櫃'}" /></td>
					<td align="right" width="200px">法人代表人：</td>
					<td align="left"><s:textfield key="firAgtTocoreInsured.headname" id="headname" size="20" maxlength="100" /></td>
				</tr>
			</table>
			<input type="hidden" name="ajaxReturnType" id="ajaxReturnType" />
			<table width="970px" cellpadding="0" cellspacing="0" >
				<tr>
					<td align="center">
						<p><input type="button" value="儲存" id="insuredSave"/>
					</td>
				</tr>
			</table>
	    </div>
	</fieldset>
	
	<span>=========================================================================異動資訊========================================================================</span>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">新增人員：</td>
			<td width="200px" align="left">
				<s:property value="ycbDetailVo.icreate"/>
			</td>
			<td width="150px" align="right">新增時間：</td>
			<td width="200px" align="left">
				<fmt:formatDate value='${ycbDetailVo.dcreate}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">最後修改人員：</td>
			<td width="200px" align="left">
				<s:property value="ycbDetailVo.iupdate"/>
			</td>
			<td width="150px" align="right">最後修改時間：</td>
			<td width="200px" align="left">
				<fmt:formatDate value='${ycbDetailVo.dupdate}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">剔退：</td>
			<td width="200px" align="left">
				<s:select key="ycbDetailVo.sfFlag" id="sfFlag" theme="simple" list="#{'N':'N', 'Y':'Y'}"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">剔退原因：</td>
			<td width="200px" align="left">
				<s:textfield key="ycbDetailVo.sfReason" id="sfReason" maxLength="300" size="20"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">剔退人員：</td>
			<td width="200px" align="left">
				<s:textfield key="ycbDetailVo.sfUser" id="sfUser" readonly="true" cssClass="txtLabel" maxLength="20" size="20"/>
			</td>
			<td width="150px" align="right">剔退時間：</td>
			<td width="200px" align="left">
				<fmt:formatDate value='${ycbDetailVo.sfDate}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">鎖定人員：</td>
			<td width="200px" align="left">
				<s:textfield key="ycbDetailVo.fixUser" id="fixUser" readonly="true" cssClass="txtLabel" maxLength="20" size="20"/>
			</td>
			<td width="150px" align="right">鎖定時間：</td>
			<td width="200px" align="left">
				<fmt:formatDate value='${ycbDetailVo.fixDate}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">轉核心狀態：</td>
			<td width="200px" align="left">
				<c:if test="${ycbDetailVo.prpinsBatchStatus == 0}">未處理</c:if>
				<c:if test="${ycbDetailVo.prpinsBatchStatus == 1}">執行中</c:if>
				<c:if test="${ycbDetailVo.prpinsBatchStatus == 2}">完成</c:if>
			</td>
			<td width="150px" align="right">轉核心時間：</td>
			<td width="200px" align="left">
				<fmt:formatDate value='${ycbDetailVo.transPpsTime}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">轉核心批號：</td>
			<td width="200px" align="left">
				<s:property value="ycbDetailVo.prpinsBatchNo"/>
			</td>
			<td width="150px" align="right">核心要保書號：</td>
			<td width="200px" align="left">
				<s:property value="ycbDetailVo.proposalno"/>
			</td>
		</tr>
		
		<s:hidden key="ycbDetailVo.dquakeNo" id="dquakeNo" value=""></s:hidden>
		<s:hidden key="ycbDetailVo.oidFirPremcalcTmp" id="oidFirPremcalcTmp" value="" ></s:hidden>
		<s:hidden key="ycbDetailVo.wsFirAmt" id="wsFirAmt" value="" ></s:hidden>
		<s:hidden key="ycbDetailVo.wsQuakeAmt" id="wsQuakeAmt" value="" ></s:hidden>
		<s:hidden key="ycbDetailVo.famtStatus" id="famtStatus" value="" ></s:hidden>
		<s:hidden key="ycbDetailVo.qamtStatus" id="qamtStatus" value="" ></s:hidden>
		<s:hidden key="ycbDetailVo.addrStatus" id="addrStatus" value="" ></s:hidden>
		<s:hidden key="ycbDetailVo.addrDetail" id="addrDetail" value="" ></s:hidden>
		<s:hidden key="ycbDetailVo.oidFirPremcalcTmp2" id="oidFirPremcalcTmp2" value="" ></s:hidden>
		<s:hidden key="ycbDetailVo.dataStatus" id="dataStatus" ></s:hidden>
	</table>
	
	<table width="970px">
		<tr>
			<td align="center">
				<input value="資料檢查" type="button" id="checkButton" onclick="ajaxAction('verifyBasicData','basic')"/>&nbsp;&nbsp;&nbsp;&nbsp;
				<!--mantis：FIR0668，處理人員：BJ085，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 start -->
				<input value="儲存" id="saveButton" type="button" onclick="saveData('save')"/>&nbsp;&nbsp;&nbsp;&nbsp;
		    	<input value="儲存及鎖定" id="lockingButton" type="button" onclick="saveData('lock')">&nbsp;&nbsp;&nbsp;&nbsp;
		    	<s:hidden key="ycbDetailVo.locking" id="locking" value="" ></s:hidden>
		    	<!--mantis：FIR0668，處理人員：BJ085，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 end -->
		    	<a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoDetailQuery.action?goBack=Y&batchNo=${ycbDetailVo.batchNo}"><input type="button" value="回上頁"/></a>
		    </td>
		</tr>
	</table>
	<table class="MainBodyColor" id="msgTable" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">資料檢查結果：</td>
			<td width="200px" align="left">
				<s:textarea key="ycbDetailVo.checkErrMsg" id="errMsg" readonly="true" value="" style="margin: 0px; width: 600px; height: 46px;"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">資料檢查提示：</td>
			<td width="200px" align="left">
				<s:textarea key="ycbDetailVo.checkWarnMsg" id="warnMsg" readonly="true" value=""  style="margin: 0px; width: 600px; height: 46px;"/>
			</td>
		</tr>
	</table>
	
</s:form>
</body>
</html>