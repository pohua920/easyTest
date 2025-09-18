<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String title = "富邦續保資料處理作業";
	String image = path + "/" + "images/";
	String GAMID = "APS034U0";
	String mDescription = "富邦續保資料處理作業";
	String nameSpace = "/aps/034";
%>
<!-- mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業 -->
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
	
	if($("#fixUser").val() != ""){
		$('#table3 input').attr("readonly",true).attr("class","txtLabel");
		$('#table3 select').attr("disabled",true).attr("class","txtLabel");
		$("#saveButton").css("display", "none");
		$("#lockingButton").css("display", "none");
		$("#checkButton").css("display", "none");
		$("#msgTable").css("display", "none");
	}

	var moneyArr = ['amountF','amountQ','premiumF','premiumQ', 'amountFLast',
		'amountQLast','premiumFLast','premiumQLast','amountFAgt','amountQAgt','premiumFAgt','premiumQAgt'];
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
		$('#birthday2').val(transLen(birthday2));
		if(birthday2 == "" || !checkDate(birthday2,"roc")){
			alertFunction($(this),"要保人-生日/註冊日必填且應為合理日期，格式YYY/MM/DD");
		}else{
			ajaxAction("birthday2",birthday2);
		}
	});
	
	$('#birthday1').datepicker({dateFormat:"yy/mm/dd"});
	$('#birthday1').bind("change",function(){
		var birthday1 = $(this).val();
		$('#birthday1').val(transLen(birthday1));
		if(birthday1 == "" || !checkDate(birthday1,"roc")){
			alertFunction($(this),"被保險人-生日/註冊日必填且應為合理日期，格式YYY/MM/DD");
		}else{
			ajaxAction("birthday1",birthday1);
		}
	});
	
	$('#handleridentifynumber').bind("blur",function(){
		if($(this).val() == ""){
			alertFunction($(this),"業務員登錄證號必填");
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
	
	$('#sfFlag').bind("blur",function(){
		if($(this).val() == "N"){
			$('#sfReason').val('');
		}
	});
});

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
	    	path = contextPath + '/aps/ajax012/verifyBasicData.action?checkType='+value;
		}
		var async = false;
		
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
		if(action == 'structureLeval'){
			path = contextPath + '/aps/ajax012/checkStructureLeval.action';
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
		
		if(action == 'verifyBasicData'){
			Object.keys(data).forEach(function(key){
				$("[id='"+key+"']").val(data[key]);
			});
			if(data.insurednature2 != null){
				insurednatureJudge(data.insurednature2,"2");
			}
			if(data.insurednature1 != null){
				insurednatureJudge(data.insurednature1,"1");
			}
			if(data.checkMsg != null){
				alert(data.checkMsg);
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
	
	function saveData(){
		if (confirm("是否儲存?")==false){
			return false;
		}
		
		if($("#sfFlag").val() == "N"){
			ajaxAction('verifyBasicData','save');
			if($("#errMsg").val()!=""){
				alert("「資料檢查結果」仍有異常，請先確認資料正確性。");
				return false;
			}
		}
		
		if($("#sfFlag").val() == "Y" && $("#sfReason").val() == ""){
			alert("剔退原因必填。");
			return false;
		}
		
		form_submit("update");
		
	}
	
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
<s:form theme="simple" action="lnkGoQuery" namespace="/aps/034" id="clearForm" name="clearForm"/>
<!-- form 開始 -->
<s:form theme="simple" namespace="/aps/034" id="mainForm" name="mainForm">	
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
				<s:property value="fbDetailVo.batchNo"/>
				<s:hidden key="fbDetailVo.batchNo" id="batchNo"></s:hidden>
			</td>
			<td width="150px" align="right">序號：</td>
			<td width="200px" align="left">
				<s:property value="fbDetailVo.batchSeq"/>
				<s:hidden key="fbDetailVo.batchSeq" id="batchSeq"></s:hidden>
			</td>
		</tr>
		<tr><td width="150px" align="right"></td></tr>
		<tr>
			<td width="150px" align="right">轉檔異常訊息：</td>
			<td width="200px" align="left">
				<s:property value="fbDetailVo.checkErrMsg"/>
			</td>
		</tr>
		<tr><td width="150px" align="right"></td></tr>
		<tr>
			<td width="150px" align="right">轉檔提醒訊息：</td>
			<td width="200px" align="left">
				<s:property value="fbDetailVo.checkWarnMsg"/>
			</td>
		</tr>
		<tr><td width="150px" align="right"></td></tr>
		<tr>
			<td width="150px" align="right">差異註記：</td>
			<td width="200px" align="left">
				<s:property value="fbDetailVo.diffFlag"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">差異原因：</td>
			<td width="200px" align="left">
				<s:property value="fbDetailVo.diffReason"/>
			</td>
		</tr>
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
			<td width="150px" align="right">續保單號-住火：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.oldpolicyno" id="oldpolicyno" onkeyup="toUpperCase(this);" size="20" maxLength="40"/>
			</td>
			<td width="150px" align="right">業務員登錄證號：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.handleridentifynumber" id="handleridentifynumber" onkeyup="toUpperCase(this);" maxLength="20" size="20"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">服務人員：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.handler1code" id="handler1code" readonly="true" cssClass="txtLabel" maxLength="20" size="20"/>
			</td>
			<td width="150px" align="right">歸屬單位：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.comcode" id="comcode" readonly="true" cssClass="txtLabel" maxLength="10" size="10"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">保單生效日：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.startdate" id="startdate" maxLength="10" size="10"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">分行代號：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.extracomcode" id="extracomcode" maxLength="20" size="10" />
			</td>
			<td width="150px" align="right">分行名稱：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.extracomname" id="extracomname" maxLength="20" size="20"/>
			</td>
		</tr>
	</table>
	<span>=========================================================================抵押權人========================================================================</span>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">抵押權人代碼：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.mortgageepcode1" id="mortgageepcode1" readonly="true" cssClass="txtLabel" maxLength="20" size="20"/>
			</td>
			<td width="150px" align="right">授信號碼：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.creditnumber1" id="creditnumber1" maxLength="40" size="20"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">擔保品編號：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.collateralnumber1" id="collateralnumber1" maxLength="40" size="20"/>
			</td>
			<td width="150px" align="right">貸款編號：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.loansbehalfnumber1" id="loansbehalfnumber1" maxLength="40" size="20"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">放款帳號：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.loanaccount1" id="loanaccount1" maxLength="40" size="20"/>
			</td>
			<td width="150px" align="right">放款部門別：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.loansdepartment1" id="loansdepartment1" maxLength="40" size="20"/>
			</td>
		</tr>
	</table>
	<span>==========================================================================標的物=========================================================================</span>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">郵遞區號：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.addresscode" id="addresscode" maxLength="3" size="3"/>
			</td>
			<td width="150px" align="right">縣市行政區：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.addressname" id="addressname" readonly="true" cssClass="txtLabel" maxLength="40" size="40"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">標的物地址：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.addressdetailinfo" id="addressdetailinfo" maxLength="200" size="50"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">外牆：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.wallmaterial" id="wallmaterial" maxLength="5" size="5"/>
				<s:textfield key="fbDetailVo.wallname" id="wallname" value="" readonly="true" cssClass="txtLabel"></s:textfield>
			</td>
			<td width="150px" align="right">屋頂：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.roofmaterial" id="roofmaterial" maxLength="10" size="10"/>
				<s:textfield key="fbDetailVo.roofname" id="roofname" value="" readonly="true" cssClass="txtLabel"></s:textfield>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">使用面積(坪)：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.buildarea" id="buildarea" maxLength="10" size="10"/>
			</td>
			<td width="150px" align="right">總樓層數：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.sumfloors" id="sumfloors" maxLength="10" size="10"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">建築等級：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.structure" id="structure" readonly="true" cssClass="txtLabel" maxLength="5" size="5"/>
			</td>
			<td width="150px" align="right">建築年分：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.buildyears" id="buildyears" maxLength="3" size="3"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">建築等級說明：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.structureText" id="structureText" readonly="true" cssClass="txtLabel" maxLength="50" size="50"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">複保險序號：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.repeatpolicyno" id="repeatpolicyno" readonly="true" cssClass="txtLabel" maxLength="15" size="20"/>
			</td>
			<td width="150px" align="right">複保險結果：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.dquakeStatus" id="dquakeStatus" readonly="true" cssClass="txtLabel" maxLength="40" size="40"/>
			</td>
		</tr>
	</table>
	
	<span>=======================================================================承保內容-本期======================================================================</span>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">火險保額：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.amountF" id="amountF" maxLength="10" size="10"/>
			</td>
			<td width="150px" align="right">地震險保額：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.amountQ" id="amountQ" maxLength="10" size="10"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">火險保費：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.premiumF" id="premiumF" maxLength="10" size="10"/>
			</td>
			<td width="150px" align="right">地震險保費：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.premiumQ" id="premiumQ" maxLength="10" size="10"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">高樓加費：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.highrisefee" id="highrisefee" readonly="true" cssClass="txtLabel" maxLength="14" size="14"/>
			</td>
		</tr>
	</table>
	
	<span>=======================================================================承保內容-前期/保經代轉入============================================================</span>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">前期火險保額：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.amountFLast" id="amountFLast" readonly="true" cssClass="txtLabel" maxLength="12"/>
			</td>
			<td width="150px" align="right">前期地震保額：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.amountQLast" id="amountQLast" readonly="true" cssClass="txtLabel" maxLength="12"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">前期火險保費：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.premiumFLast" id="premiumFLast" readonly="true" cssClass="txtLabel" maxLength="12"/>
			</td>
			<td width="150px" align="right">前期地震保費：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.premiumQLast" id="premiumQLast" readonly="true" cssClass="txtLabel" maxLength="12"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">保經轉入火險保額：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.amountFAgt" id="amountFAgt" readonly="true" cssClass="txtLabel" maxLength="12"/>
			</td>
			<td width="150px" align="right">保經轉入地震保額：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.amountQAgt" id="amountQAgt" readonly="true" cssClass="txtLabel" maxLength="12"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">保經轉入火險保費：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.premiumFAgt" id="premiumFAgt" readonly="true" cssClass="txtLabel" maxLength="12"/>
			</td>
			<td width="150px" align="right">保經轉入地震保費：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.premiumQAgt" id="premiumQAgt" readonly="true" cssClass="txtLabel" maxLength="12"/>
			</td>
		</tr>
	</table>
	
	<s:iterator var="insured" value="fbDetailVo.insuredList" status="st">
	<s:if test="#st.First">
	<span>==========================================================================要保人=========================================================================</span>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">姓名：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.insuredList[%{#st.index}].insuredname" id="insuredname2" maxLength="300" size="20"/>
			</td>
			<td width="150px" align="right">證照號碼：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.insuredList[%{#st.index}].identifynumber" id="identifynumber2" onkeyup="toUpperCase(this);" maxLength="20" size="20"/>
				<s:hidden key="fbDetailVo.insuredList[%{#st.index}].insurednature" id="insurednature2" ></s:hidden>
				<s:hidden key="fbDetailVo.insuredList[%{#st.index}].identifytype" id="identifytype2" ></s:hidden>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">市話：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.insuredList[%{#st.index}].phonenumber" id="phonenumber2" maxLength="20" size="20"/>
			</td>
			<td width="150px" align="right">行動電話：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.insuredList[%{#st.index}].mobile" id="mobile2" maxLength="10" size="10"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">郵遞區號：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.insuredList[%{#st.index}].postcode" id="postcode2" maxLength="10" size="3"/>
				<s:textfield key="fbDetailVo.insuredList[%{#st.index}].postname" id="postname2" readonly="true" cssClass="txtLabel"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">通訊地址：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.insuredList[%{#st.index}].postaddress" id="postaddress2" maxLength="120" size="50"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">居住地/註冊地：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.insuredList[%{#st.index}].domicile" id="domicile2" onkeyup="toUpperCase(this);" maxLength="20" size="20"/>
			</td>
			<td width="150px" align="right">國籍：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.insuredList[%{#st.index}].countryename" id="countryename2" onkeyup="toUpperCase(this);" maxLength="20" size="20"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">是否高危職業：</td>
			<td width="200px" align="left">
				<s:select key="fbDetailVo.insuredList[%{#st.index}].ishighdengeroccupation" id="ishighdengeroccupation2" theme="simple" list="#{'':'', '0':'高風險', '1':'低風險'}"/>
			</td>
			<td width="150px" align="right">生日/註冊日(民國年YYY/MM/DD)：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.insuredList[%{#st.index}].strBirthday" id="birthday2" maxLength="9" size="9"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">上市櫃公司：</td>
			<td width="200px" align="left">
				<s:select key="fbDetailVo.insuredList[%{#st.index}].listedcabinetcompany" id="listedcabinetcompany2" theme="simple" list="#{'':'', '0':'上市櫃', '1':'非上市櫃'}"/>
			</td>
			<td width="150px" align="right">法人代表人：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.insuredList[%{#st.index}].headname" id="headname2" maxLength="300" size="20"/>
			</td>
		</tr>
	</table>
	<s:hidden key="fbDetailVo.insuredList[%{#st.index}].insuredSeq" id="insuredSeq2" value="1"/>
	<s:hidden key="fbDetailVo.insuredList[%{#st.index}].insuredflag" id="insuredflag2" value="2"/>
	</s:if>
	<s:if test="#st.Last">
	<span>=========================================================================被保險人========================================================================</span>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">姓名：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.insuredList[%{#st.index}].insuredname" id="insuredname1" maxLength="300" size="20"/>
				<input type="button" value="同要保人" onclick="sameWithGuarantor()">
			</td>
			<td width="150px" align="right">證照號碼：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.insuredList[%{#st.index}].identifynumber" id="identifynumber1" onkeyup="toUpperCase(this);" maxLength="20" size="20"/>
				<s:hidden key="fbDetailVo.insuredList[%{#st.index}].insurednature" id="insurednature1" ></s:hidden>
				<s:hidden key="fbDetailVo.insuredList[%{#st.index}].identifytype" id="identifytype1" ></s:hidden>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">市話：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.insuredList[%{#st.index}].phonenumber" id="phonenumber1" maxLength="20" size="20"/>
			</td>
			<td width="150px" align="right">行動電話：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.insuredList[%{#st.index}].mobile" id="mobile1" maxLength="10" size="10"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">郵遞區號：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.insuredList[%{#st.index}].postcode" id="postcode1" maxLength="10" size="3"/>
				<s:textfield key="fbDetailVo.insuredList[%{#st.index}].postname" id="postname1" readonly="true" cssClass="txtLabel"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">通訊地址：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.insuredList[%{#st.index}].postaddress" id="postaddress1" maxLength="120" size="50"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">居住地/註冊地：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.insuredList[%{#st.index}].domicile" id="domicile1" onkeyup="toUpperCase(this);" maxLength="20" size="20"/>
			</td>
			<td width="150px" align="right">國籍：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.insuredList[%{#st.index}].countryename" id="countryename1" onkeyup="toUpperCase(this);" maxLength="20" size="20"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">是否高危職業：</td>
			<td width="200px" align="left">
				<s:select key="fbDetailVo.insuredList[%{#st.index}].ishighdengeroccupation" id="ishighdengeroccupation1" theme="simple" list="#{'':'', '0':'高風險', '1':'低風險'}"/>
			</td>
			<td width="150px" align="right">生日/註冊日(民國年YYY/MM/DD)：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.insuredList[%{#st.index}].strBirthday" id="birthday1" maxLength="9" size="9"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">上市櫃公司：</td>
			<td width="200px" align="left">
				<s:select key="fbDetailVo.insuredList[%{#st.index}].listedcabinetcompany" id="listedcabinetcompany1" theme="simple" list="#{'':'', '0':'上市櫃', '1':'非上市櫃'}"/>
			</td>
			<td width="150px" align="right">法人代表人：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.insuredList[%{#st.index}].headname" id="headname1" maxLength="300" size="20"/>
			</td>
		</tr>
	</table>
	<s:hidden key="fbDetailVo.insuredList[%{#st.index}].insuredSeq" id="insuredSeq1" value="1"/>
	<s:hidden key="fbDetailVo.insuredList[%{#st.index}].insuredflag" id="insuredflag1" value="1"/>
	</s:if>
	</s:iterator>
	
	<span>=========================================================================異動資訊========================================================================</span>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">新增人員：</td>
			<td width="200px" align="left">
				<s:property value="fbDetailVo.icreate"/>
			</td>
			<td width="150px" align="right">新增時間：</td>
			<td width="200px" align="left">
				<fmt:formatDate value='${fbDetailVo.dcreate}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">最後修改人員：</td>
			<td width="200px" align="left">
				<s:property value="fbDetailVo.iupdate"/>
			</td>
			<td width="150px" align="right">最後修改時間：</td>
			<td width="200px" align="left">
				<fmt:formatDate value='${fbDetailVo.dupdate}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">剔退：</td>
			<td width="200px" align="left">
				<s:select key="fbDetailVo.sfFlag" id="sfFlag" theme="simple" list="#{'N':'N', 'Y':'Y'}"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">剔退原因：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.sfReason" id="sfReason" maxLength="300" size="20"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">剔退人員：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.sfUser" id="sfUser" readonly="true" cssClass="txtLabel" maxLength="20" size="20"/>
			</td>
			<td width="150px" align="right">剔退時間：</td>
			<td width="200px" align="left">
				<fmt:formatDate value='${fbDetailVo.sfDate}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">鎖定人員：</td>
			<td width="200px" align="left">
				<s:textfield key="fbDetailVo.fixUser" id="fixUser" readonly="true" cssClass="txtLabel" maxLength="20" size="20"/>
			</td>
			<td width="150px" align="right">鎖定時間：</td>
			<td width="200px" align="left">
				<fmt:formatDate value='${fbDetailVo.fixDate}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">轉核心狀態：</td>
			<td width="200px" align="left">
				<c:if test="${fbDetailVo.prpinsBatchStatus == 0}">未處理</c:if>
				<c:if test="${fbDetailVo.prpinsBatchStatus == 1}">執行中</c:if>
				<c:if test="${fbDetailVo.prpinsBatchStatus == 2}">完成</c:if>
			</td>
			<td width="150px" align="right">轉核心時間：</td>
			<td width="200px" align="left">
				<fmt:formatDate value='${fbDetailVo.transPpsTime}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">轉核心批號：</td>
			<td width="200px" align="left">
				<s:property value="fbDetailVo.prpinsBatchNo"/>
			</td>
			<td width="150px" align="right">核心要保書號：</td>
			<td width="200px" align="left">
				<s:property value="fbDetailVo.proposalno"/>
			</td>
		</tr>
		
		<s:hidden key="fbDetailVo.dquakeNo" id="dquakeNo" value=""></s:hidden>
		<s:hidden key="fbDetailVo.oidFirPremcalcTmp" id="oidFirPremcalcTmp" value="" ></s:hidden>
		<s:hidden key="fbDetailVo.wsFirAmt" id="wsFirAmt" value="" ></s:hidden>
		<s:hidden key="fbDetailVo.wsQuakeAmt" id="wsQuakeAmt" value="" ></s:hidden>
		<s:hidden key="fbDetailVo.famtStatus" id="famtStatus" value="" ></s:hidden>
		<s:hidden key="fbDetailVo.qamtStatus" id="qamtStatus" value="" ></s:hidden>
		<s:hidden key="fbDetailVo.addrStatus" id="addrStatus" value="" ></s:hidden>
		<s:hidden key="fbDetailVo.addrDetail" id="addrDetail" value="" ></s:hidden>
		<s:hidden key="fbDetailVo.oidFirPremcalcTmp2" id="oidFirPremcalcTmp2" value="" ></s:hidden>
		<s:hidden key="fbDetailVo.dataStatus" id="dataStatus" ></s:hidden>
	</table>
	
	<table width="970px">
		<tr>
			<td align="center">
				<input value="資料檢查" type="button" id="checkButton" onclick="ajaxAction('verifyBasicData','basic')">&nbsp;&nbsp;&nbsp;&nbsp;
			    <input value="儲存" id="saveButton" type="button" onclick="saveData()"/>&nbsp;&nbsp;&nbsp;&nbsp;
		    	<a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoDetailQuery.action?goBack=Y&fbDetailVo.batchNo=${fbDetailVo.batchNo}"><input type="button" value="回上頁"/></a>
		    </td>
		</tr>
	</table>
	<table class="MainBodyColor" id="msgTable" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">資料檢查結果：</td>
			<td width="200px" align="left">
				<s:textarea key="fbDetailVo.checkErrMsg" id="errMsg" readonly="true" value="" style="margin: 0px; width: 600px; height: 46px;"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">資料檢查提示：</td>
			<td width="200px" align="left">
				<s:textarea key="fbDetailVo.checkWarnMsg" id="warnMsg" readonly="true" value=""  style="margin: 0px; width: 600px; height: 46px;"/>
			</td>
		</tr>
	</table>
	
</s:form>
</body>
</html>