<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String title = "板信續保資料處理作業";
	String image = path + "/" + "images/";
	String GAMID = "APS016U0";
	String mDescription = "板信續保資料處理作業";
	String nameSpace = "/aps/016";
%>
<!-- mantis：FIR0295，處理人員：BJ085，需求單編號：FIR0295 外銀板信續件移回新核心-維護作業 start -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language="JavaScript">
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

	insurednatureJudge($("#insurednature1").val(),"1");
	insurednatureJudge($("#insurednature2").val(),"2");
	
	//mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業
	if($("#fixUser").val() != ""){
		$('#table3 input').attr("readonly",true).attr("class","txtLabel");
		$('#table3 select').attr("disabled",true).attr("class","txtLabel");
		$("#saveButton").css("display", "none");
		$("#lockingButton").css("display", "none");
		$("#checkButton").css("display", "none");
		$("#msgTable").css("display", "none");
	}
	
	/*mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 start*/
	if($("#postcode2").val() != ""){
		ajaxAction('postcode2',$("#postcode2").val());
	}
	if($("#postcode1").val() != ""){
		ajaxAction('postcode1',$("#postcode1").val());
	}
	/*mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 end*/

	var moneyArr = ['amountF','amountQ','premiumF','premiumQ','premiumT','amountFLast',
		'amountQLast','premiumFLast','premiumQLast','amountFAgt','amountQAgt','premiumFAgt','premiumQAgt'];
	for(var i=0;i<moneyArr.length;i++){
		$("[id='"+moneyArr[i]+"']").val(transThousands($("[id='"+moneyArr[i]+"']").val()));
		$("[id='"+moneyArr[i]+"']").bind("change",function(){
			$(this).val(transThousands($(this).val()));
		});
	}
	
	$("#amountF").bind("blur",function(){
		var amountF = $(this).val();
		if(amountF == "" || !checkIsnum(transNum(amountF))){
			alertFunction($(this),"火險保額必填且需為整數");
		}
	});
	
	$("#amountQ").bind("blur",function(){
		var amountQ = $(this).val();
		if(amountQ == "" || !checkIsnum(transNum(amountQ)) 
				|| parseInt(transNum(amountQ)) > 1500000 || parseInt(transNum(amountQ)) < 10000){
			alertFunction($(this),"地震險保額必填且保額應在10000~1500000之間");
		}
	});
	
	$("#premiumF").bind("blur",function(){
		var premiumF = $(this).val();
		if(premiumF == "" || !checkIsnum(transNum(premiumF))){
			alertFunction($(this),"火險保費必填且需為整數");
		}
	});
	
	$("#premiumQ").bind("blur",function(){
		var premiumQ = $(this).val();
		if(premiumQ == "" || !checkIsnum(transNum(premiumQ))){
			alertFunction($(this),"地震險保費必填且需為整數");
		}
	});

	$('#startdate').datepicker({dateFormat:"yyyy/mm/dd"});

	$('#startdate').bind("change",function(){
		var startdate = $(this).val();
		if(startdate == "" || !checkDate(startdate,"ad")){
			alertFunction($(this),"保單生效日必填且需為合理日期");
		}else{
			checkStructureLevel();
		}
	});
	
	$('#extracomcode').bind("blur",function(){
		if($(this).val() == ""){
			alertFunction($(this),"分行代號必填");
		}else{
	 		ajaxAction('extracomcode');
		}
	});
	
	$('#addresscode').bind("blur",function(){
		var addresscode = $(this).val();
		if(addresscode == ""){
			alertFunction($(this),"郵遞區號必填");
		}else{
	 		ajaxAction('addresscode',addresscode);
		}
	});
	
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
	
	$("#identifynumber2").bind("blur",function(){
		var identifynumber2 = $(this).val();
		if(identifynumber2 == ""){
			alertFunction($(this),"要保人-證號號碼必填");
		}else{
	 		ajaxAction('identifynumber2',identifynumber2);
		}
	});
	
	$("#identifynumber1").bind("blur",function(){
		var identifynumber1 = $(this).val();
		if(identifynumber1 == ""){
			alertFunction($(this),"被保險人-證號號碼必填");
		}else{
	 		ajaxAction('identifynumber1',identifynumber1);
		}
	});
	
	$('#postcode2').bind("blur",function(){
		var postcode2 = $(this).val();
		if(postcode2 == ""){
			alertFunction($(this),"要保人-郵遞區號必填");
		}else{
	 		ajaxAction('postcode2',postcode2);			
		}
	});
	
	$('#postcode1').bind("blur",function(){
		var postcode1 = $(this).val();
		if(postcode1 == ""){
			alertFunction($(this),"被保險人-郵遞區號必填");
		}else{			
	 		ajaxAction('postcode1',postcode1);
		}
	});
	
	$('#domicile2').bind("blur",function(){
		var domicile2 = $(this).val();
		if(domicile2 == ""){
			alertFunction($(this),"要保人-居住地/住冊地必填");
		}else{
	 		ajaxAction('domicile2',domicile2);
		}
	});
	
	$('#domicile1').bind("blur",function(){
		var domicile1 = $(this).val();
		if(domicile1 == ""){
			alertFunction($(this),"被保險人-居住地/住冊地必填");
		}else{
	 		ajaxAction('domicile1',domicile1);
		}
	});
	
	$('#countryename2').bind("blur",function(){
		var countryename2 = $(this).val();
		if(countryename2 == ""){
			alertFunction($(this),"要保人-國籍必填");
		}else{
	 		ajaxAction('countryename2',countryename2);			
		}
	});
	
	$('#countryename1').bind("blur",function(){
		var countryename1 = $(this).val();
		if(countryename1 == ""){
			alertFunction($(this),"被保險人-國籍必填");
		}else{
	 		ajaxAction('countryename1',countryename1);			
		}
	});
	
	$('#ishighdengeroccupation2').bind("change",function(){
		if($(this).val() == ""){
			alertFunction($(this),"要保人-是否高危職業必填");
		}
	});
	
	$('#ishighdengeroccupation1').bind("change",function(){
		if($(this).val() == ""){
			alertFunction($(this),"被保險人-是否高危職業必填");
		}
	});
	
	$('#headname2').bind("blur",function(){
		if($(this).val() == "" && $('#insurednature2').val() == '4'){
			alertFunction($(this),"要保人-若客戶類型為法人時，法人代表人必需輸入");
		}else if($('#insurednature2').val() != '4'){
			$("#headname2").val("").attr("readonly",true).attr("class","txtLabel");
		}
	});
	
	$('#headname1').bind("blur",function(){
		if($(this).val() == "" && $('#insurednature1').val() == '4'){
			alertFunction($(this),"被保險人-若客戶類型為法人時，法人代表人必需輸入");
		}else if($('#insurednature1').val() != '4'){
			$("#headname1").val("").attr("readonly",true).attr("class","txtLabel");
		}
	});
	
	$('#listedcabinetcompany2').bind("blur",function(){
		if($(this).val() == "" && $('#insurednature2').val() == '4'){
			alertFunction($(this),"要保人-若客戶類型為法人時，上市櫃公司必需輸入");
		}else if($('#insurednature2').val() != '4'){
			$("#listedcabinetcompany2").val("").attr("disabled",true).attr("class","txtLabel");
		}
	});
	
	$('#listedcabinetcompany1').bind("blur",function(){
		if($(this).val() == "" && $('#insurednature1').val() == '4'){
			alertFunction($(this),"被保險人-若客戶類型為法人時，上市櫃公司必需輸入");
		}else if($('#insurednature1').val() != '4'){
			$("#listedcabinetcompany1").val("").attr("disabled",true).attr("class","txtLabel");
		}
	});

	$('#birthday2').datepicker({dateFormat:"yy/mm/dd"});
	$('#birthday2').bind("change",function(){
		var birthday2 = $(this).val();
		if(birthday2 == "" || !checkDate(birthday2,"roc")){
			alertFunction($(this),"要保人-生日/註冊日必填且應為合理日期，格式YYY/MM/DD");
		}else{
			ajaxAction("birthday2",birthday2);
		}
	});
	
	$('#birthday1').datepicker({dateFormat:"yy/mm/dd"});
	$('#birthday1').bind("change",function(){
		var birthday1 = $(this).val();
		if(birthday1 == "" || !checkDate(birthday1,"roc")){
			alertFunction($(this),"被保險人-生日/註冊日必填且應為合理日期，格式YYY/MM/DD");
		}else{
			ajaxAction("birthday1",birthday1);
		}
	});
	
	$('#handleridentifynumber').bind("blur",function(){
		if($(this).val() == ""){
			alertFunction($(this),"業務員登錄證號必填");
		}else{
			ajaxAction('handleridentifynumber');
		}
	});
	
	$('#mobile2').bind("blur",function(){
		var mobile = $(this).val();
		if(mobile !== "" && (mobile.substr(0,2) !== "09" || mobile.length !== 10 || !checkIsnum(mobile))){
			alertFunction($(this),"要保人-行動電話需為09開頭共10位數字");
		}
	});
	
	$('#mobile1').bind("blur",function(){
		var mobile = $(this).val();
		if(mobile !== "" && (mobile.substr(0,2) !== "09" || mobile.length !== 10 || !checkIsnum(mobile))){
			alertFunction($(this),"被保險人-行動電話需為09開頭共10位數字");
		}
	});
	
	$('#postaddress2').bind("blur",function(){
		if($(this).val() == ""){
			alertFunction($(this),"要保人-通訊地址必填");
		}
	});
	
	$('#postaddress1').bind("blur",function(){
		if($(this).val() == ""){
			alertFunction($(this),"被保險人-通訊地址必填");
		}
	});
});

	function ajaxAction(action,value){
		//取出ajax xml相對路徑
		var contextPath = '<%=request.getScheme()%>' + '://<%=request.getServerName()+":"+request.getServerPort()+request.getContextPath()%>';
		var path = '';
		var	data = new FormData($('#mainForm')[0]);
		if(action == 'renewalBasicData'){
			$("#errMsg").val("");
			$("#warnMsg").val("");
	    	path = contextPath + '/aps/ajax006/renewalBasicDataCheck.action';
		}
		var async = false;
		
		if(action == 'extracomcode'){
			path = contextPath + '/aps/ajax006/checkExtracomcode.action';
		}
		if(action == 'addresscode' || action == 'postcode2' || action == 'postcode1'){
			path = contextPath + '/aps/ajax006/findPrpdNewCodeBycodetype.action?codetype=PostAddress&codecode='+value;
		}
		if(action == 'wallmaterial'){
			path = contextPath + '/aps/ajax006/findPrpdNewCodeBycodetype.action?codetype=WallMaterial&codecode='+value;
		}
		if(action == 'roofmaterial'){
			path = contextPath + '/aps/ajax006/findPrpdNewCodeBycodetype.action?codetype=RoofMaterial&codecode='+value;
		}
		if(action == 'identifynumber2' || action == 'identifynumber1'){
			path = contextPath + '/aps/ajax006/verifyIdentifynumber.action?identifynumber='+value;
		}
		if(action == 'domicile2' || action == 'domicile1' || action == 'countryename2' || action == 'countryename1'){
			path = contextPath + '/aps/ajax006/findPrpdNewCodeBycodetype.action?codetype=countryCName&codecode='+value;
		}
		if(action == 'birthday2' || action == 'birthday1'){
			path = contextPath + '/aps/ajax006/checkDateLessThanNewDate.action?date='+value;
		}
		//mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業
		if(action == 'saveData'){
			path = contextPath + '/aps/ajax006/saveDataToCheck.action';
		}
		if(action == 'structureLeval'){
			path = contextPath + '/aps/ajax006/checkStructureLeval.action';
		}
		if(action == 'handleridentifynumber'){
			path = contextPath + '/aps/ajax006/checkHandleridentifynumber.action';
		}
		/*mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 start */
// 		if(action == 'findAS400Data'){
// 			path = contextPath + '/aps/ajax006/findAS400Data.action';
// 			async = true;
// 		}
		if(action == 'findCoreData'){
			path = contextPath + '/aps/ajax006/findCoreData.action';
			async = true;
		}
		/*mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 end*/
		
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
		if(action == 'extracomcode' || action == 'handleridentifynumber'){
			if(data.comcode != null){
				$("#comcode").val(data.comcode);
				if(data.handler1code != null || data.extracomname != null){
					$("#handler1code").val(data.handler1code);
					$("#extracomname").val(data.extracomname);
					if(data.extracomcode!=null){
					$("#extracomcode").val(data.extracomcode);						
					}
				}else{
					alertFunction($("#extracomcode"), data.errMsg);
				}
			}else{
				alertFunction($("#extracomcode"), data.errMsg);
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
		
		var insuredArr = ['被保險人-','要保人-'];
		var insuredCodeArr = ['1','2'];
		
		for(var i=0;i<insuredArr.length;i++){
			if(action == 'identifynumber'+insuredCodeArr[i]){
				if(data.errMsg != null){
					alertFunction($("[id='identifynumber"+insuredCodeArr[i]+"']"), insuredArr[i]+data.errMsg);
				}else{
					$("[id='insurednature"+insuredCodeArr[i]+"']").val(data.insuredNature);
					$("[id='identifytype"+insuredCodeArr[i]+"']").val(data.idType);
					insurednatureJudge(data.insuredNature,insuredCodeArr[i]);
				}
				break;
			}	
			if(action == 'postcode'+insuredCodeArr[i]){
				if(data.isExist){
					$("[id='postname"+insuredCodeArr[i]+"']").val(data.codename);
				}else{
					alertFunction($("[id='postcode"+insuredCodeArr[i]+"']"), insuredArr[i]+"查無郵遞區號");
				}
				break;
			}
			if(action == 'domicile'+insuredCodeArr[i] && !data.isExist){
				alertFunction($("[id='domicile"+insuredCodeArr[i]+"']"), insuredArr[i]+"查無居住地/住冊地");
			}
			if(action == 'countryename'+insuredCodeArr[i] && !data.isExist){
				alertFunction($("[id='countryename"+insuredCodeArr[i]+"']"), insuredArr[i]+"查無國籍");
			}
			if(action == 'birthday'+insuredCodeArr[i] && !data.isExist){
				alertFunction($("[id='birthday"+insuredCodeArr[i]+"']"), insuredArr[i] + data.errMsg);
			}
		}
		
		if(action == 'renewalBasicData'){
			Object.keys(data).forEach(function(key){
				$("[id='"+key+"']").val(data[key]);
			});
			if(data.insurednature2 != null){
				insurednatureJudge(data.insurednature2,"2");
			}
			if(data.insurednature1 != null){
				insurednatureJudge(data.insurednature1,"1");
			}
			/*mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 start */
			if($("#errMsg").val()==""){
				alert("資料檢查完成!");
			}
			/*mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 end */
		}
		
		if(action == 'saveData' && !data.isExist){
			$("#errMsg").val($("#errMsg").val()+data.errMsg);
		}
		
		if(action == 'structureLeval'){
			if(data.errMsg != null){
				alertFunction($("#structure"), data.errMsg);
			}else{
				$("#structure").val(data.structure);
				$("#structureText").val(data.structureText);
			}
		}
		
		/*mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 start*/
// 		if(action == 'findAS400Data'){
// 			if(data.errMsg != null){
// 				alert(data.errMsg);
// 			}else{
// 				if(confirm("已找到相關資料，是否將資料帶入頁面？(請注意，帶入之資料包含火險保費、地震險保額及保費。)")){
// 					$('#wallmaterial').val(data.AS400Data.wallmaterial);
// 					if($('#wallmaterial').val()!=""){
// 						ajaxAction('wallmaterial',$('#wallmaterial').val());
// 					}
// 					$('#roofmaterial').val(data.AS400Data.roofmaterial);
// 					if($('#roofmaterial').val()!=""){
// 						ajaxAction('roofmaterial',$('#roofmaterial').val());
// 					}
// 					$('#amountQ').val(transThousands(String(data.AS400Data.amountQ)));
// 					$('#premiumF').val(transThousands(String(data.AS400Data.premiumF)));
// 					$('#premiumQ').val(transThousands(String(data.AS400Data.premiumQ)));
// 					$('#amountFLast').val(transThousands(String(data.AS400Data.amountF)));
// 					$('#amountQLast').val(transThousands(String(data.AS400Data.amountQ)));
// 					$('#premiumFLast').val(transThousands(String(data.AS400Data.premiumF)));
// 					$('#premiumQLast').val(transThousands(String(data.AS400Data.premiumQ)));
// 					if($("#sumfloors").val() == "" || $("#sumfloors").val() == 0){
// 						$("#sumfloors").val(data.AS400Data.sumfloors);
// 					}
// 					if($("#buildarea").val() == "" || $("#buildarea").val() == 0){
// 						$("#buildarea").val(data.AS400Data.buildarea);
// 					}
// 					if($("#buildyears").val() == "" || $("#buildyears").val() == 0){
// 						$("#buildyears").val(data.AS400Data.buildyears);
// 					}
// 					$("#postcode2").val(data.AS400Data.postcodeA);
// 					$("#postcode1").val(data.AS400Data.postcodeI);
// 					checkStructureLevel();
// 					alert("資料已帶入。");
// 				}
// 			}
// 			$.unblockUI();
// 		}

		if(action == 'findCoreData'){
			if(data.errMsg != null){
				alert(data.errMsg);
			}else{
				//mantis：FIR0657，處理人員：BJ085，需求單編號：FIR0657 住火_配合造價表調整重算外銀續保保額
				if(confirm("已找到相關資料，是否將資料帶入頁面？(請注意，帶入之資料包含計算後火險保額、保費以及依造價表計算之地震險保額、保費。)")){
					$('#wallmaterial').val(data.coreData.wallmaterial);
					if($('#wallmaterial').val()!=""){
						ajaxAction('wallmaterial',$('#wallmaterial').val());
					}
					$('#roofmaterial').val(data.coreData.roofmaterial);
					if($('#roofmaterial').val()!=""){
						ajaxAction('roofmaterial',$('#roofmaterial').val());
					}
					$('#amountQ').val(transThousands(String(data.coreData.amtQ)));
					/*mantis：FIR0657，處理人員：BJ085，需求單編號：FIR0657 住火_配合造價表調整重算外銀續保保額 start*/
					$('#premiumF').val(transThousands(String(data.coreData.premF)));
					$('#premiumQ').val(transThousands(String(data.coreData.premQ)));
					$('#amountFLast').val(transThousands(String(data.coreData.amtFLast)));
					$('#amountF').val(transThousands(String(data.coreData.amtF)));
					$('#amountQLast').val(transThousands(String(data.coreData.amtQLast)));
					$('#premiumFLast').val(transThousands(String(data.coreData.premFLast)));
					$('#premiumQLast').val(transThousands(String(data.coreData.premQLast)));
					/*mantis：FIR0657，處理人員：BJ085，需求單編號：FIR0657 住火_配合造價表調整重算外銀續保保額 end*/
					//若板信無提供總樓層數、坪數、建築年分，則帶入核心資料，若有提供則與核心資料比對是否一致
					if($("#sumfloors").val() == "" || $("#sumfloors").val() == 0){
						$("#sumfloors").val(data.coreData.sumfloors);
						data.coreData.warnMsg = data.coreData.warnMsg + "畫面上無總樓層數資料，使用前一年保單資料;";
					}
					if($("#sumfloors").val() != data.coreData.sumfloors){
						data.coreData.warnMsg = data.coreData.warnMsg + "畫面上總樓層數與核心(" + data.coreData.sumfloors + ")資料不一致；";
					}
					if($("#buildarea").val() == "" || $("#buildarea").val() == 0){
						$("#buildarea").val(data.coreData.buildarea);
						data.coreData.warnMsg = data.coreData.warnMsg + "畫面上無提供坪數資料，使用前一年保單資料;";
					}
					if($("#buildarea").val() != data.coreData.buildarea){
						data.coreData.warnMsg = data.coreData.warnMsg + "畫面上坪數與核心(" + data.coreData.buildarea + ")資料不一致；";
					}
					
					if($("#buildyears").val() == "" || $("#buildyears").val() == 0){
						$("#buildyears").val(data.coreData.buildyears);
						data.coreData.warnMsg = data.coreData.warnMsg + "畫面上無建築物年份資料，使用前一年保單資料;";
					}
					if($("#buildyears").val() != data.coreData.buildyears){
						data.coreData.warnMsg = data.coreData.warnMsg + "畫面上建築物年份與核心(" + data.coreData.buildyears + ")資料不一致；";
					}
					
					$("#postcode2").val(data.coreData.postcode2);
					$("#postcode1").val(data.coreData.postcode1);
					$("#birthday2").val(data.coreData.birthday2);
					$("#birthday1").val(data.coreData.birthday1);
					var isAutoRenew = data.coreData.isAutoRenew;
					var clauseSendtype = "2";//預設條款交付方式為2 紙本保單
					$("#isAutoRenew").val(isAutoRenew);
					if("Y" == isAutoRenew){
						$("#epolicy").val(data.coreData.epolicy);
						clauseSendtype = data.coreData.clauseSendtype;
					}
					$("#clauseSendtype").val(clauseSendtype);
					$("#mortgageepcode2").val(data.coreData.mortgageepcode2);
					$("#serialno2").val(data.coreData.serialno2);
					
					$("#handleridentifynumber").val(data.coreData.handleridentifynumber);
					if($('#handleridentifynumber').val() != ""){
						ajaxAction('handleridentifynumber');
					}
					
					checkStructureLevel();
					if($("#structure").val() != data.coreData.structure){
						data.coreData.warnMsg = data.coreData.warnMsg + "WS計算建築等級結果與核心("+data.coreData.structure+")資料不一致;";
					}
					if(data.coreData.warnMsg != null){
						$("#warnMsg").val(data.coreData.warnMsg);
					}
					alert("資料已帶入。");
				}
			}
			$.unblockUI();
		}
		/*mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 end*/
		
	}

	//ajax發生錯誤時會呼叫的method
	function ajaxError(data, status){
		//mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業
		alert("執行Ajax發生錯誤，請聯絡資訊人員。");
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
	
	/*mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 start*/
	function saveData(type){
		
		if($("#sfFlag").val() == "Y" && $("#sfReason").val() == ""){
			alert("剔退案件請輸入剔退原因。");
			return false;
		}
		var msg = "";
		if(type == "save"){
			msg = "是否儲存?";
		}else if(type == "lock"){
			msg = "請注意，鎖定後將無法再修改資料，請確認是否進行存檔及鎖定?";
		}
		
		if (confirm(msg)==true){
			ajaxAction('renewalBasicData');
			ajaxAction('saveData');
			if(type == "save"){
				$("#locking").val("N");
			}else if(type == "lock"){
				$("#locking").val("Y");
			}
			if($("#errMsg").val()!=""){
				alert("「資料檢查結果」仍有異常，請先確認資料正確性。");
			}else{
				form_submit("update");
			}
		}
	}
	/*mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 end*/
	
	function transThousands(num){
		if(num != ""){
			num = num.replaceAll(",","");
			var regexZero = new RegExp("([0]*)([0-9]+)", "g");
			num = num.replace(regexZero,"$2");
			var regex = new RegExp("(\\d{1,3})(?=(\\d{3})+(?:$|\\D))", "g");
			num = num.replace(regex,"$1,");
		}
		return num;
	}
	
	function transNum(str){
		str = str.replaceAll(",","");
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
	
	function insurednatureJudge(insuredNature,type){
		if(insuredNature == "3"){
			$("[id='listedcabinetcompany"+type+"']").val("").attr("class","txtLabel").attr("disabled",true);
			$("[id='headname"+type+"']").val("").attr("class","txtLabel").attr("readonly",true);
		}else if(insuredNature == "4"){
			$("[id='listedcabinetcompany"+type+"']").attr("disabled",false).removeAttr("class","");
			$("[id='headname"+type+"']").attr("readonly",false).removeAttr("class","");
		}
	}
	
	function sameWithGuarantor(){
		var basicDataArr = ['insuredname','identifynumber','phonenumber','mobile','postcode','postname','postaddress','domicile',
			'countryename','ishighdengeroccupation','birthday','listedcabinetcompany','headname','insurednature','identifytype'];
		for(var i=0;i<basicDataArr.length;i++){
			$("[id='"+basicDataArr[i]+"1']").val($("[id='"+basicDataArr[i]+"2']").val());
		}
		insurednatureJudge($("#insurednature1").val(),"1");
	}
	
	/*mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 start*/
	function importCoreData(){
		if($("#oldpolicyno ").val()==""){
			alert("請先輸入續保單號。");
			return false;
		}
		//點擊帶入AS400資料按鈕先遮罩，資料帶入完成再解開
		$.blockUI({
			border: 'none',
			padding: '15px',//padding：同時設定四個邊留白的捷徑屬性
			backgroundColor: '#000',//backgroundColor：訊息背景顏色
			color: '#fff',//color：訊息字樣顏色
			'-webkit-border-radius': '10px',//WebKit設定四邊的圓角為10px
			'-moz-border-radius': '10px',//Mozilla設定四邊的圓角為10px
			opacity: .5//opacity：指定表單和其控制項的透明度等級
		});
// 		ajaxAction("findAS400Data","");
		ajaxAction("findCoreData","");
	}
	/*mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 end*/

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
<s:form theme="simple" action="lnkGoQuery" namespace="/aps/016" id="clearForm" name="clearForm"/>
<!-- form 開始 -->
<s:form theme="simple" namespace="/aps/016" id="mainForm" name="mainForm">	
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tr bgcolor="white">
			<td class="MainTdColor" width="200px" align="center">
				<span id="lbSearch"><b>修改作業</b></span>
			</td>
			<td colspan="2" class="image"></td>
		</tr>
	</table>
	<!-- mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 start-->
	<table class="MainBodyColor" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td align="right">
				<a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoDetailQuery.action?type=N&batchNo=${aps016DetailVo.batchNo}">
				<input type="button" value="回上頁"/></a>
			</td>
		</tr>
	</table>
	<!-- mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 end-->
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">批次號碼：</td>
			<td width="200px" align="left">
				<s:property value="aps016DetailVo.batchNo"/>
				<s:hidden key="aps016DetailVo.batchNo" id="batchNo"></s:hidden>
			</td>
			<td width="150px" align="right">序號：</td>
			<td width="200px" align="left">
				<s:property value="aps016DetailVo.batchSeq"/>
				<s:hidden key="aps016DetailVo.batchSeq" id="batchSeq"></s:hidden>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">轉檔異常訊息：</td>
			<td width="200px" align="left">
				<s:property value="aps016DetailVo.checkErrMsg"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">轉檔提醒訊息：</td>
			<td width="200px" align="left">
				<s:property value="aps016DetailVo.checkWarnMsg"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">說明：</td>
			<td width="800px" align="left">
				<span>「轉檔異常訊息、轉檔提醒訊息」僅為排程程式轉入時針對板信資料進行檢核的結果。若已在本作業進行資料修改，請使用按鈕「資料檢核」重新確認調整後的資料是否合理。</span>
			</td>
		</tr>
	</table>
	<span>=========================================================================基本資料========================================================================</span>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">續保單號：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.oldpolicyno" id="oldpolicyno" onkeyup="toUpperCase(this);" maxLength="40" size="20"/>
				<!-- mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 -->
				<input type="button" value="帶入核心資料" onclick="importCoreData()"/>
			</td>
			<td width="150px" align="right">受理編號：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.orderseq" id="orderseq" onkeyup="toUpperCase(this);" maxLength="40" size="20"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">服務人員：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.handler1code" id="handler1code" readonly="true" cssClass="txtLabel" maxLength="20" size="20"/>
			</td>
			<td width="150px" align="right">歸屬單位：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.comcode" id="comcode" readonly="true" cssClass="txtLabel" maxLength="10" size="10"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">保單生效日：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.startdateCheck" id="startdate"  maxLength="10" size="10"/>
			</td>
			<td width="150px" align="right">業務員登錄證號：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.handleridentifynumber" id="handleridentifynumber" onkeyup="toUpperCase(this);" maxLength="20" size="20"/>
				<!-- mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業 -->
				<s:hidden key="aps016DetailVo.businessnature"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">分行代號：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.extracomcode" id="extracomcode" maxLength="20" size="10" />
			</td>
			<td width="150px" align="right">分行名稱：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.extracomname" id="extracomname" maxLength="20" size="20" readonly="true" cssClass="txtLabel"/>
			</td>
		</tr>
	</table>
	<span>=========================================================================抵押權人========================================================================</span>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<!-- mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 start-->
		<tr>
			<td width="150px" align="right">抵押權人代碼1：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.mortgageepcode1" id="mortgageepcode1" readonly="true" cssClass="txtLabel" maxLength="20" size="20"/>
			</td>
			<td width="150px" align="right">抵押權人代碼2：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.mortgageepcode2" id="mortgageepcode2" readonly="true" cssClass="txtLabel" maxLength="20" size="20"/>
				<s:hidden key="aps016DetailVo.serialno2"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">授信號碼：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.creditnumber1" id="creditnumber1" maxLength="40" size="20"/>
			</td>
			<td width="150px" align="right">擔保品編號：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.collateralnumber1" id="collateralnumber1" maxLength="40" size="20"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">貸款編號：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.loansbehalfnumber1" id="loansbehalfnumber1" maxLength="40" size="20"/>
			</td>
			<td width="150px" align="right">放款帳號：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.loanaccount1" id="loanaccount1" maxLength="40" size="20"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">放款部門別：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.loansdepartment1" id="loansdepartment1" maxLength="40" size="20"/>
			</td>
			<td width="150px" align="right">保單序號：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.temp8" id="temp8" readonly="true" cssClass="txtLabel" maxLength="20" size="10"/>
			</td>
		</tr>
		<!-- mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 end-->
	</table>
	<span>==========================================================================標的物=========================================================================</span>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">郵遞區號：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.addresscode" id="addresscode" maxLength="3" size="3"/>
			</td>
			<td width="150px" align="right">縣市行政區：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.addressname" id="addressname" readonly="true" cssClass="txtLabel" maxLength="40" size="40"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">標的物地址：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.addressdetailinfo" id="addressdetailinfo" maxLength="200" size="50"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">外牆：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.wallmaterial" id="wallmaterial" maxLength="5" size="5"/>
				<s:textfield key="aps016DetailVo.wallname" id="wallname" value="" readonly="true" cssClass="txtLabel"></s:textfield>
			</td>
			<td width="150px" align="right">屋頂：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.roofmaterial" id="roofmaterial" maxLength="10" size="10"/>
				<s:textfield key="aps016DetailVo.roofname" id="roofname" value="" readonly="true" cssClass="txtLabel"></s:textfield>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">使用面積(坪)：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.buildarea" id="buildarea" maxLength="10" size="10"/>
			</td>
			<td width="150px" align="right">總樓層數：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.sumfloors" id="sumfloors" maxLength="10" size="10"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">建築等級：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.structure" id="structure" readonly="true" cssClass="txtLabel" maxLength="5" size="5"/>
			</td>
			<td width="150px" align="right">建築年分：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.buildyears" id="buildyears" maxLength="3" size="3"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">建築等級說明：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.structureText" id="structureText" readonly="true" cssClass="txtLabel" maxLength="50" size="50"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">複保險序號：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.repeatpolicyno" id="repeatpolicyno" readonly="true" cssClass="txtLabel" maxLength="15" size="20"/>
			</td>
			<td width="150px" align="right">複保險結果：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.dquakeStatus" id="dquakeStatus" readonly="true" cssClass="txtLabel" maxLength="40" size="40"/>
			</td>
		</tr>
	</table>
	
	<span>=======================================================================承保內容-本期======================================================================</span>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">火險保額：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.amountF" id="amountF" maxLength="10" size="10"/>
			</td>
			<td width="150px" align="right">地震險保額：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.amountQ" id="amountQ" maxLength="10" size="10"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">火險保費：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.premiumF" id="premiumF" maxLength="10" size="10"/>
			</td>
			<td width="150px" align="right">地震險保費：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.premiumQ" id="premiumQ" maxLength="10" size="10"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">板信轉入總保費：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.premiumT" id="premiumT" readonly="true" cssClass="txtLabel" maxLength="10" size="10"/>
			</td>
			<td width="150px" align="right">高樓加費：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.highrisefee" id="highrisefee" readonly="true" cssClass="txtLabel" maxLength="14" size="14"/>
			</td>
		</tr>
	</table>
	
	<span>=======================================================================承保內容-前期/保經代轉入============================================================</span>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">前期火險保額：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.amountFLast" id="amountFLast" readonly="true" cssClass="txtLabel" maxLength="12"/>
			</td>
			<td width="150px" align="right">前期地震保額：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.amountQLast" id="amountQLast" readonly="true" cssClass="txtLabel" maxLength="12"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">前期火險保費：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.premiumFLast" id="premiumFLast" readonly="true" cssClass="txtLabel" maxLength="12"/>
			</td>
			<td width="150px" align="right">前期地震保費：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.premiumQLast" id="premiumQLast" readonly="true" cssClass="txtLabel" maxLength="12"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">保經轉入火險保額：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.amountFAgt" id="amountFAgt" readonly="true" cssClass="txtLabel" maxLength="12"/>
			</td>
			<td width="150px" align="right">保經轉入地震保額：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.amountQAgt" id="amountQAgt" readonly="true" cssClass="txtLabel" maxLength="12"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">保經轉入火險保費：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.premiumFAgt" id="premiumFAgt" readonly="true" cssClass="txtLabel" maxLength="12"/>
			</td>
			<td width="150px" align="right">保經轉入地震保費：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.premiumQAgt" id="premiumQAgt" readonly="true" cssClass="txtLabel" maxLength="12"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">說明：</td>
			<!-- mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 -->
			<td width="200px" align="left">板信僅提供火險保額及總保費，前期保額保費來源為新核心。</td>
		</tr>
	</table>
	
	<span>==========================================================================要保人=========================================================================</span>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">姓名：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.insuredname2" id="insuredname2" maxLength="300" size="20"/>
			</td>
			<td width="150px" align="right">證照號碼：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.identifynumber2" id="identifynumber2" onkeyup="toUpperCase(this);" maxLength="20" size="20"/>
				<s:hidden key="aps016DetailVo.insurednature2" id="insurednature2" ></s:hidden>
				<s:hidden key="aps016DetailVo.identifytype2" id="identifytype2" ></s:hidden>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">市話：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.phonenumber2" id="phonenumber2" maxLength="20" size="20"/>
			</td>
			<td width="150px" align="right">行動電話：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.mobile2" id="mobile2" maxLength="10" size="10"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">郵遞區號：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.postcode2" id="postcode2" maxLength="10" size="3"/>
				<s:textfield key="aps016DetailVo.postname2" id="postname2" readonly="true" cssClass="txtLabel"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">通訊地址：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.postaddress2" id="postaddress2" maxLength="120" size="50"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">居住地/註冊地：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.domicile2" id="domicile2" onkeyup="toUpperCase(this);" maxLength="20" size="20"/>
			</td>
			<td width="150px" align="right">國籍：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.countryename2" id="countryename2" onkeyup="toUpperCase(this);" maxLength="20" size="20"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">是否高危職業：</td>
			<td width="200px" align="left">
				<s:select key="aps016DetailVo.ishighdengeroccupation2" id="ishighdengeroccupation2" theme="simple" list="#{'':'', '0':'高風險', '1':'低風險'}"/>
			</td>
			<td width="150px" align="right">生日/註冊日(民國年YYY/MM/DD)：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.birthday2Check" id="birthday2" maxLength="9" size="9"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">上市櫃公司：</td>
			<td width="200px" align="left">
				<s:select key="aps016DetailVo.listedcabinetcompany2" id="listedcabinetcompany2" theme="simple" list="#{'':'', '0':'上市櫃', '1':'非上市櫃'}"/>
			</td>
			<td width="150px" align="right">法人代表人：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.headname2" id="headname2" maxLength="300" size="20"/>
			</td>
		</tr>
	</table>
	
	<span>=========================================================================被保險人========================================================================</span>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">姓名：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.insuredname1" id="insuredname1" maxLength="300" size="20"/>
				<input type="button" value="同要保人" onclick="sameWithGuarantor()">
			</td>
			<td width="150px" align="right">證照號碼：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.identifynumber1" id="identifynumber1" onkeyup="toUpperCase(this);" maxLength="20" size="20"/>
				<s:hidden key="aps016DetailVo.insurednature1" id="insurednature1" ></s:hidden>
				<s:hidden key="aps016DetailVo.identifytype1" id="identifytype1" ></s:hidden>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">市話：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.phonenumber1" id="phonenumber1" maxLength="20" size="20"/>
			</td>
			<td width="150px" align="right">行動電話：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.mobile1" id="mobile1" maxLength="10" size="10"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">郵遞區號：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.postcode1" id="postcode1" maxLength="10" size="3"/>
				<s:textfield key="aps016DetailVo.postname1" id="postname1" readonly="true" cssClass="txtLabel"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">通訊地址：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.postaddress1" id="postaddress1" maxLength="120" size="50"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">居住地/註冊地：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.domicile1" id="domicile1" onkeyup="toUpperCase(this);" maxLength="20" size="20"/>
			</td>
			<td width="150px" align="right">國籍：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.countryename1" id="countryename1" onkeyup="toUpperCase(this);" maxLength="20" size="20"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">是否高危職業：</td>
			<td width="200px" align="left">
				<s:select key="aps016DetailVo.ishighdengeroccupation1" id="ishighdengeroccupation1" theme="simple" list="#{'':'', '0':'高風險', '1':'低風險'}"/>
			</td>
			<td width="150px" align="right">生日/註冊日(民國年YYY/MM/DD)：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.birthday1Check" id="birthday1" maxLength="9" size="9"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">上市櫃公司：</td>
			<td width="200px" align="left">
				<s:select key="aps016DetailVo.listedcabinetcompany1" id="listedcabinetcompany1" theme="simple" list="#{'':'', '0':'上市櫃', '1':'非上市櫃'}"/>
			</td>
			<td width="150px" align="right">法人代表人：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.headname1" id="headname1" maxLength="300" size="20"/>
			</td>
		</tr>
	</table>
	
	<span>=========================================================================異動資訊========================================================================</span>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">新增人員：</td>
			<td width="200px" align="left">
				<s:property value="aps016DetailVo.icreate"/>
			</td>
			<td width="150px" align="right">新增時間：</td>
			<td width="200px" align="left">
				<fmt:formatDate value='${aps016DetailVo.dcreate}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">最後修改人員：</td>
			<td width="200px" align="left">
				<s:property value="aps016DetailVo.iupdate"/>
			</td>
			<td width="150px" align="right">最後修改時間：</td>
			<td width="200px" align="left">
				<fmt:formatDate value='${aps016DetailVo.dupdate}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">鎖定人員：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.fixUser" id="fixUser" readonly="true" cssClass="txtLabel" maxLength="20" size="20"/>
			</td>
			<td width="150px" align="right">鎖定時間：</td>
			<td width="200px" align="left">
				<fmt:formatDate value='${aps016DetailVo.fixDate}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
		<!-- mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 start-->
		<tr>
			<td width="150px" align="right">轉核心狀態：</td>
			<td width="200px" align="left">
				<c:choose>
					<c:when test="${aps016DetailVo.prpinsBatchStatus == '0'}">未處理</c:when>
					<c:when test="${aps016DetailVo.prpinsBatchStatus == '1'}">執行中</c:when>
					<c:when test="${aps016DetailVo.prpinsBatchStatus == '2'}">完成</c:when>
					<c:otherwise>未定義</c:otherwise>
				</c:choose>
			</td>
			<td width="150px" align="right">轉核心時間：</td>
			<td width="200px" align="left">
				<s:property value="aps016DetailVo.transPpsTime"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">剔退：</td>
			<td width="200px" align="left">
				<s:select key="aps016DetailVo.sfFlag" id="sfFlag" theme="simple" list="#{'N':'N', 'Y':'Y'}"/>
			</td>
			<td width="150px" align="right">剔退原因：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.sfReason" id="sfReason" maxLength="300" size="20"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">剔退人員：</td>
			<td width="200px" align="left">
				<s:textfield key="aps016DetailVo.sfUser" id="sfUser" readonly="true" cssClass="txtLabel" maxLength="20" size="20"/>
			</td>
			<td width="150px" align="right">剔退時間：</td>
			<td width="200px" align="left">
				<fmt:formatDate value='${aps016DetailVo.sfDate}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
		<!-- mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 end-->
		
		<s:hidden key="aps016DetailVo.dquakeNo" id="dquakeNo" value="" ></s:hidden>
		<s:hidden key="aps016DetailVo.oidFirPremcalcTmp" id="oidFirPremcalcTmp" value="" ></s:hidden>
		<s:hidden key="aps016DetailVo.wsFirAmt" id="wsFirAmt" value="" ></s:hidden>
		<s:hidden key="aps016DetailVo.wsQuakeAmt" id="wsQuakeAmt" value="" ></s:hidden>
		<s:hidden key="aps016DetailVo.famtStatus" id="famtStatus" value="" ></s:hidden>
		<s:hidden key="aps016DetailVo.qamtStatus" id="qamtStatus" value="" ></s:hidden>
		<s:hidden key="aps016DetailVo.addrStatus" id="addrStatus" value="" ></s:hidden>
		<s:hidden key="aps016DetailVo.addrDetail" id="addrDetail" value="" ></s:hidden>
		<s:hidden key="aps016DetailVo.oidFirPremcalcTmp2" id="oidFirPremcalcTmp2" value="" ></s:hidden>
		<s:hidden key="aps016DetailVo.dataStatus" id="dataStatus" ></s:hidden>
		
		<!-- mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 start-->
		<s:hidden key="aps016DetailVo.epolicy" id="epolicy" ></s:hidden>
		<s:hidden key="aps016DetailVo.clauseSendtype" id="clauseSendtype" ></s:hidden>
		<s:hidden key="aps016DetailVo.isAutoRenew" id="isAutoRenew" ></s:hidden>
		<!-- mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 end-->
	</table>
	
	<table width="970px">
		<tr>
			<td align="center">
				<input value="資料檢查" type="button" id="checkButton" onclick="ajaxAction('renewalBasicData')">&nbsp;&nbsp;&nbsp;&nbsp;
			    <!-- mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 start-->
			    <input value="儲存" id="saveButton" type="button" onclick="saveData('save')"/>&nbsp;&nbsp;&nbsp;&nbsp;
				<input value="儲存及鎖定" id="lockingButton" type="button" onclick="saveData('lock')">&nbsp;&nbsp;&nbsp;&nbsp;
				<!-- mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 end-->
		    	<s:hidden key="aps016DetailVo.locking" id="locking" value="" ></s:hidden>
		    </td>
		</tr>
	</table>
	<table class="MainBodyColor" id="msgTable" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">資料檢查結果：</td>
			<td width="200px" align="left">
				<s:textarea key="aps016DetailVo.checkErrMsg" id="errMsg" readonly="true" value="" style="margin: 0px; width: 600px; height: 46px;"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">資料檢查提示：</td>
			<td width="200px" align="left">
				<s:textarea key="aps016DetailVo.checkWarnMsg" id="warnMsg" readonly="true" value=""  style="margin: 0px; width: 600px; height: 46px;"/>
			</td>
		</tr>
	</table>
	
</s:form>
</body>
</html>