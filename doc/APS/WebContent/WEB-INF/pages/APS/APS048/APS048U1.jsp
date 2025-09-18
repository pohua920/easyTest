<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "再保分進理賠維護作業";
	String image = path + "/" + "images/";
	String GAMID = "APS048U1";
	String mDescription = "再保分進理賠維護作業";
	String nameSpace = "/aps/048";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script language="JavaScript">
	
	
	//init start
	//存放險種明細用
	var insObj;
	var detail = '<c:out value="${reinsInwardDataStr}" escapeXml="false"/>';
	var claimDetail = '<c:out value="${reinsInwardClaimInsDataStr}" escapeXml="false"/>';
	
	//init end
	
	$(function() {
		
		if(detail != "" && detail != null){
			var insObjMain = JSON.parse(detail);
			var classCodeVal = insObjMain.classcode;
			$("#classcode1").val(classCodeVal);
			$("#classcode").val(classCodeVal);
			if( classCodeVal != "" && classCodeVal != null){
				if(classCodeVal == "A"){
					$("#classcodeDesc").val('任意車險');
				}
				if(classCodeVal == "B"){
					$("#classcodeDesc").val('強制險');
				}
				if(classCodeVal == "E"){
					$("#classcodeDesc").val('工程險');
				}
				if(classCodeVal == "F01"){
					$("#classcodeDesc").val('商業火險');
				}
				if(classCodeVal == "F02"){
					$("#classcodeDesc").val('住火險');
				}
				if(classCodeVal == "M"){
					$("#classcodeDesc").val('水險');
				}
				if(classCodeVal == "C"){
					$("#classcodeDesc").val('新種險');
				}
				if(classCodeVal == "C1"){
					$("#classcodeDesc").val('傷害險');
				}
			}
			$("#oidReinsInwardMainData").val(insObjMain.oid);
			$("#cmnpNo").val(insObjMain.cmnpNo);
			$("#broker").val(insObjMain.broker);
			$("#inwardNo").val(insObjMain.inwardNo);
			$("#applicantId").val(insObjMain.applicantId);
			$("#applicantName").val(insObjMain.applicantName);
			$("#insuredId").val(insObjMain.insuredId);
			$("#insuredName").val(insObjMain.insuredName);
			$("#endDate").val(insObjMain.endDate);
			$("#startDate").val(insObjMain.startDate);
			$("#endorseStartDate").val(insObjMain.endorseStartDate);
			$("#endorseEndDate").val(insObjMain.endorseEndDate);
			$("#objInfo").val(insObjMain.objInfo);
			
			insObj = insObjMain.insList;
			reBuildTable(insObj);
		}
		if(claimDetail != "" && claimDetail != null){
			claimDetail = JSON.parse(claimDetail);
			reBuildClaimTable('gridtablePre','prepare', claimDetail);
			reBuildClaimTable('gridtableCon','confirm', claimDetail);
		}
		
		//主檔總保額欄位內容異動時觸發
		$("#policyNo").change(function() {
			ajaxAction('findPolicy');
		});
		$("#endorseNo").change(function() {
			ajaxAction('findPolicy');
		});
		$("#preExchangeRate").change(function() {
			calculate('prepare');
		});
		$("#conExchangeRate").change(function() {
			calculate('confirm');
		});
		//日期檢核
		$("#damageDate").change(function() {
			checkDate("damageDate");
		});
		$("#claimDate").change(function() {
			checkDate("claimDate");
		});
		$("#prepareDate").change(function() {
			checkDate("prepareDate");
		});
		$("#confirmDate").change(function() {
			checkDate("confirmDate");
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
		
		$("#dialog").dialog({
			modal: true,
			autoOpen: false,
			height : "auto", 
			width : "auto",
			close:function(event, ui){ // 對話框關閉時觸發的方法
				clearInsData();
			}
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

	});
	

	
	function checkDate(id){
		
		var dateStr = $("#" + id ).val();
		if(dateStr == "" && dateStr == null){
			alert("請輸入正確日期");
			$("#" + id ).focus();
			return false;
		}
		
		var re = /^[0-9]*/;//判斷字串是否為數字//判斷正整數/[1−9] [0−9]∗]∗/ 
		if (!re.test(dateStr)) { 
			alert("請輸入數字");
			$("#" + id ).val("");
			$("#" + id ).focus();
			return false;
		}
		
		var year = dateStr.substr(0,4);
		var mm = dateStr.substr(4,2);
		var dd = dateStr.substr(6,2);
		year = parseInt(year,10) + 1911;
		if(!isExistDate(year + "/" + mm + "/" + dd)){
			alert("請輸入正確日期");
			$("#" + id ).val("");
			$("#" + id ).focus();
			return false;
		}
		return true;
	}
	
	function form_submit(method){
		 $("label").html('');
		 var roleType =  $("#roleType").val();
		 if(roleType == "" || roleType == null){
			 alert("無法取得角色資料，請重新登入");
			 return;
		 }
		
		 if('pass' == method){
			 
			 
			 if(!confirm("請確認是否『覆核確認』？")){
				 return false;
			 }
			 if(roleType == "CR002"){
				 $("#mainForm").attr("action","btnInsAudit.action");
				 $("#auditResult").val('0');
				 $("#mainForm").submit();
			 }
			 if(roleType == "CR003"){
				 if(checkDate("accountantDate")){
					 $("#mainForm").attr("action","btnReinsAudit.action");
					 $("#auditResult").val('0');
					 $("#mainForm").submit();
				 }
			 }
		 }
		 if('reject' == method){
			 if(!confirm("請確認是否『退回立案』？")){
				 return false;
			 }
			 if(roleType == "CR002"){
				 $("#mainForm").attr("action","btnInsAudit.action");
				 $("#auditResult").val('1');
			 }
			 if(roleType == "CR003"){
				 $("#mainForm").attr("action","btnReinsAudit.action");
				 $("#auditResult").val('1');
			 }
			 $("#mainForm").submit();
		 }
		 if('clear' == method){
				if (confirm("請確認是否返回查詢頁面？")==true){
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
	
	function formatNumber(num) { 
		if (!isNaN(num)) {
			if(num.indexOf('.') > 0){
				var num1 =  num.split('.')[0];
				num1 = ("" + num1).replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g, "$1,");
				return num1 + "." + num.split('.')[1];
			}else{
				return ("" + num).replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g, "$1,");	
			}
		} 
	}
	
	//險種檔datagrid
	function reBuildTable(insObj){
		if(insObj != null && insObj.length > 0){
			$('#gridtable tr').remove();
			$('#gridtable').append('<tr align="center" style="background-color: #87CEFA;border-color: #428ABD;">'
				+ '<th width="160px">險種名稱</th>'
				+ '<th width="60px">出單險種</th>'
				+ '<th width="60px">30險種</th>'
				+ '<th width="60px">財務險種</th>'
				+ '<th width="160px">(原幣)分進總保額</th>'
				+ '<th width="160px">分進比例(%)</th>'
				+ '<th width="160px">(原幣)總保額</th></tr>');
			
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
				+ '<td align="center">' + obj.oriCurrInwardAmount + '</td>'
				+ '<td align="center">' + obj.undertakingRate + '</td>'
				+ '<td align="center">' + obj.oriCurrAmount + '</td></tr>');
			}
		}
	}
	
	//險種檔datagrid，type分為 prepare或confirm
	function reBuildClaimTable(tableId, type, detailList){
		console.log(detailList);
		console.log(detailList.length);
		if(detailList != null && detailList.length > 0){
			$('#' + tableId + ' tr').remove();
			$('#' + tableId).append('<tr align="center" style="background-color: #87CEFA;border-color: #428ABD;">'
				+ '<th width="160px">險種名稱</th>'
				+ '<th width="160px">賠款(原幣)</th>'
				+ '<th width="160px">費用(原幣)</th>'
				+ '<th width="160px">賠款(台幣)</th>'
				+ '<th width="160px">費用(台幣)</th></tr>');
			
			for(var i = 0 ; i < detailList.length ; i++){
				var obj = detailList[i];
				if(obj == null){
					continue;
				}
				
				var kindlossO;
				var kindfeeO;
				var kindlossN;
				var kindfeeN;
				
				if(type == "prepare"){
					kindlossO = obj.prepareKindlossO;
					kindfeeO = obj.prepareKindfeeO;
					kindlossN = obj.prepareKindlossN;
					kindfeeN = obj.prepareKindfeeN;
					//給匯率值
					
				}
				if(type == "confirm"){
					kindlossO = obj.confirmKindlossO;
					kindfeeO = obj.confirmKindfeeO;
					kindlossN = obj.confirmKindlossN;
					kindfeeN = obj.confirmKindfeeN;
				}
				
				$('#' + tableId).append('<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF" >'
				+ '<td align="center">' + obj.poname + '</td>'
				+ '<td align="center">' + kindlossO + '</td>'
				+ '<td align="center">' + kindfeeO + '</td>'
				+ '<td align="center">' + kindlossN + '</td>'
				+ '<td align="center">' + kindfeeN + '</td></tr>');
				if(type == "prepare"){
					$('#' + tableId).append('<input type="hidden" name="reinsInwardClaimInsDataList[' + i + '].uwins" id="uwins" value= "' + obj.uwins + '"/>'
					+ '<input type="hidden" name="reinsInwardClaimInsDataList[' + i + '].poname" id="poname" value= "' + obj.poname + '" />'
					+ '<input type="hidden" name="reinsInwardClaimInsDataList[' + i + '].poins" id="poname" value= "' + obj.poins + '" />'
					+ '<input type="hidden" name="reinsInwardClaimInsDataList[' + i + '].inscode" value= "' + obj.inscode + '" />'
					+ '<input type="hidden" name="reinsInwardClaimInsDataList[' + i + '].actins" value= "' + obj.actins + '" />');
				}
			}
		}
	}
	
	function getValue(id){
		  return $("#" + id).val();
	}
	
	function setValue(id, value){
		  return $("#" + id).val(value);
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
	
	function onlyNumdot1(obj) {
		obj.value = obj.value.replace(/[^0-9\.]/g, '');
		
	}
	function replaceComa1(obj) {
		obj.value = obj.value.replace(/\,/g, '');
	}
	function addComa1(obj) {
		if(obj.value != "" && obj.value != null && obj.value.toString().indexOf(',') == -1){
			obj.value = formatNumber(obj.value);
		}
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
<s:form theme="simple" action="lnkGoQuery" namespace="/aps/048" id="clearForm" name="clearForm" />
<!-- form 開始 -->
<s:form theme="simple" namespace="/aps/048" id="mainForm" name="mainForm">
	
	<s:hidden name="roleType" id="roleType" />
	<s:hidden name="reinsInwardClaimData.oid" id="oid" />
	<s:hidden name="reinsInwardClaimData.type" id="type" />
	<s:hidden name="reinsInwardClaimData.status" id="status" />
	<s:hidden name="auditResult" id="auditResult" />

	<table id="table2" cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="MainTdColor" style="width: 200px" align="center"><span id="lbSearch"><b>新增作業</b></span></td>
			<td class="image" style="width: 20px"></td>
			<td colspan="2"></td>
		</tr>
	</table>
	<fieldset style="width:800px;">
	    <legend>保單內容</legend>
		<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="870px" border="0">
			<tr>
				<td align="right" width="200px">中信產險保單號碼：</td>
				<td align="left" width="200px" >
					<s:property value="reinsInwardClaimData.policyNo"/>
				</td>
				<td align="right" width="200px">中信產險批單號碼：</td>
				<td align="left">
					<s:property value="reinsInwardClaimData.endorseNo"/>
				</td>	
			</tr>
			<tr>
				<td align="right" width="200px">險種別：</td>
				<td align="left" width="200px">
					<s:select key="reinsInwardMainData.classcode" id="classcode1" theme="simple" tabindex="-1"
					list="#{'0':'請選擇', 'A':'任意車險', 'B':'強制險', 'E':'工程險', 'F01':'商業火險', 'F02':'住火險', 'M':'水險', 'C':'新種險', 'C1':'傷害險'}" />
				</td>
				<td align="right" width="200px"></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" width="200px">分出保險公司：</td>
				<td align="left" colspan="3"><s:select key="reinsInwardMainData.cmnpNo" id="cmnpNo" theme="simple" tabindex="-1"
					list="#{'':'', '01':'01-臺灣產物保險股份有限公司','02':'02-兆豐產物保險股份有限公司','05':'05-富邦產物保險股份有限公司','06':'06-和泰產物保險股份有限公司','07':'07-泰安產物保險股份有限公司','08':'08-明台產物保險股份有限公司',
					'09':'09-南山產物保險股份有限公司','10':'10-第一產物保險股份有限公司','12':'12-旺旺友聯產物保險股份有限公司','13':'13-新光產物保險股份有限公司','14':'14-華南產物保險股份有限公司','15':'15-國泰世紀產物保險股份有限公司',
					'17':'17-新安東京海上產物保險股份有限公司','28':'28-比利時商裕利安宜產物保險股份有限公司台灣分公司','29':'29-新加坡商美國國際產物保險股份有限公司台灣分公司','30':'30-法商科法斯產物保險股份有限公司台灣分公司',
					'32':'32-美商安達產物保險公司台北分公司','46':'46-法商法國巴黎產物保險股份有限公司台灣分公司'}" />
				
				</td>
			</tr>
			<tr>
				<td align="right" width="200px">分出保經代公司：</td>
				<td align="left" colspan="3">
					<s:property value="reinsInwardClaimData.broker"/>
				</td>
			</tr>
			<tr>
				<td align="right" width="200px">分進編號：</td>
				<td align="left"><s:property value="reinsInwardClaimData.inwardNo"/></td>
				<td align="right" width="200px"></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" width="200px">保單起迄日(YYYMMDD)：</td>
				<td align="left" >
					<s:property value="reinsInwardClaimData.startDate"/>-<s:property value="reinsInwardClaimData.endDate"/>
				</td>
				<td align="right" width="200px">批單起迄日：</td>
				<td align="left" >
					<s:property value="reinsInwardClaimData.endorseStartDate"/>-<s:property value="reinsInwardClaimData.endorseEndDate"/>
				</td>
			</tr>
			<tr>
				<td align="right" width="200px">要保人名稱：</td>
				<td align="left"><s:property value="reinsInwardClaimData.applicantName"/></td>
				<td align="right" width="200px">要保人ID：</td>
				<td align="left"><s:property value="reinsInwardClaimData.applicantId"/></td>
			</tr>
			<tr>
				<td align="right" width="200px">被保人名稱：</td>
				<td align="left"><s:property value="reinsInwardClaimData.insuredName"/></td>
				<td align="right" width="200px">被保人ID：</td>
				<td align="left"><s:property value="reinsInwardClaimData.insuredId"/></td>
			</tr>
			<tr>
				<td align="right" width="200px">標的物地址/主保單號：</td>
				<td align="left" colspan="3"><s:property value="reinsInwardClaimData.objInfo"/></td>
			</tr>
			
		</table>
		<fieldset style="width:800px;">
	    <legend>險種內容</legend>
			<table id="gridtable" border="1" class="main_table"  width="840px"></table>
		</fieldset>
	</fieldset>
	

	<fieldset style="width:800px;">
	    <legend>賠案資料</legend>
		<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="870px" border="0">
			<tr>
				<td align="right" width="200px" nowrap="nowrap">分出公司賠案號碼：</td>
				<td align="left" width="200px">
					<s:property value="reinsInwardClaimData.inwardClaimNo"/>
				</td>
				<td align="right" width="200px">出險保單之險種：</td>
				<td align="left" >
					<s:property value="reinsInwardClaimData.classcode"/>
					<input type="text" name="classcodeDesc" id="classcodeDesc" class="txtLabel" readonly tabindex="-1" size="10" maxlength="10"/>
				</td>				
			</tr>
			<tr>
				<td align="right" width="200px">中信產險賠案號碼：</td>
				<td align="left" width="200px">
					<s:property value="reinsInwardClaimData.claimNo"/>
				</td>
				<td align="right" width="200px">賠次：</td>
				<td align="left" ><s:property value="reinsInwardClaimData.ctime"/>				
			</tr>
			<tr>
				<td align="right" width="200px">暫存單號：</td>
				<td align="left" width="200px">
					<s:property value="reinsInwardClaimData.tmpclNo"/>
				</td>
				<td align="right" width="200px"></td>
				<td align="left" ></td>				
			</tr>
			<tr>
				<td align="right" width="200px">出險日期：</td>
				<td align="left" width="200px">
					<s:property value="reinsInwardClaimData.damageDate"/>
				</td>
				<td align="right" width="200px">受理日期：</td>
				<td align="left" >
					<s:property value="reinsInwardClaimData.claimDate"/>
				</td>				
			</tr>
			<tr>
				<td align="right" width="200px">出險原因：</td>
				<td align="left" colspan="3">
					<s:select key="reinsInwardClaimData.damageCode" theme="simple" id="damageCode"  list="damageCodeMap" />
				</td>				
			</tr>
		</table>
	</fieldset>
	<fieldset style="width:800px;">
	    <legend>預估賠款</legend>
		<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="870px" border="0">
			<tr>
				<td align="right" width="200px">預估日期：</td>
				<td align="left"><s:property value="reinsInwardClaimData.prepareDate"/></td>
				<td align="right" width="200px"></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" width="200px">幣別：</td>
				<td align="left" width="200px">
					<s:select key="reinsInwardClaimData.preCurrency" id="preCurrency" theme="simple" 
					list="#{'TWD':'台幣', 'USD':'USD-美金', 'GBP':'GBP-英鎊', 'EUR':'EUR-歐元', 'HKD':'HKD-港幣', 'CNY':'CNY-人民幣', 'SGD':'SGD-新加坡幣', 
					'JPY':'JPY-日圓', 'KRW':'KRW-韓元', 'AUD':'AUD-澳幣', 'NZD':'NZD-紐元', 'SEK':'SEK-瑞典幣', 'CHF':'CHF-瑞士法郎', 'CAD':'CAD-加拿大幣', 
					'PHP':'PHP-菲國比索', 'THB':'THB-泰幣', 'VND':'VND-越南幣', 'IDR':'IDR-印尼幣', 'MYR':'MYR-馬來幣', 'ZAR':'ZAR-南非幣'}" tabindex="-1" />
				</td>
				<td align="right" width="200px">匯率：</td>
				<td align="left" ><s:property value="reinsInwardClaimData.preExchangeRate"/></td>
			</tr>
			<tr>
				<td colspan="4">
					<fieldset style="width:800px;">
					<legend>預估險種</legend>
					<table id="gridtablePre" border="1" class="main_table"  width="840px"></table>
					</fieldset>
				</td>
			</tr>
			<tr>
				<td colspan="3"><br></td>
			</tr>
			<tr>
				<td align="right" width="200px">預估賠款合計(原幣)：</td>
				<td align="left"><s:property value="reinsInwardClaimData.prepareLossO"/></td>
				<td align="right" width="200px">預估賠款合計(台幣)：</td>
				<td align="left"><s:property value="reinsInwardClaimData.prepareLossNtd"/></td>
			</tr>
			<tr>
				<td align="right" width="200px">預估費用合計(原幣)：</td>
				<td align="left"><s:property value="reinsInwardClaimData.prepareFeeO"/></td>
				<td align="right" width="200px">預估費用合計(台幣)：</td>
				<td align="left"><s:property value="reinsInwardClaimData.prepareFeeNtd"/></td>
			</tr>
			<tr>
				<td align="right" width="200px">預估金額總額(原幣)：</td>
				<td align="left"><s:property value="reinsInwardClaimData.prepareSumO"/></td>
				<td align="right" width="200px">預估金額總額(台幣)：</td>
				<td align="left"><s:property value="reinsInwardClaimData.prepareSumNtd"/></td>
			</tr>
		</table>
	</fieldset>
	<fieldset style="width:800px;">
	    <legend>已決賠款</legend>
		<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="870px" border="0">
			<tr>
				<td align="right" width="200px">結案日期：</td>
				<td align="left"><s:property value="reinsInwardClaimData.confirmDate"/></td>
				<td align="right" width="200px"></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" width="200px">幣別：</td>
				<td align="left" width="200px">
					<s:select key="reinsInwardClaimData.conCurrency" id="conCurrency" theme="simple" 
					list="#{'TWD':'台幣', 'USD':'USD-美金', 'GBP':'GBP-英鎊', 'EUR':'EUR-歐元', 'HKD':'HKD-港幣', 'CNY':'CNY-人民幣', 'SGD':'SGD-新加坡幣', 
					'JPY':'JPY-日圓', 'KRW':'KRW-韓元', 'AUD':'AUD-澳幣', 'NZD':'NZD-紐元', 'SEK':'SEK-瑞典幣', 'CHF':'CHF-瑞士法郎', 'CAD':'CAD-加拿大幣', 
					'PHP':'PHP-菲國比索', 'THB':'THB-泰幣', 'VND':'VND-越南幣', 'IDR':'IDR-印尼幣', 'MYR':'MYR-馬來幣', 'ZAR':'ZAR-南非幣'}" />
				</td>
				<td align="right" width="200px">匯率：</td>
				<td align="left" ><s:property value="reinsInwardClaimData.conExchangeRate"/></td>
			</tr>
			<tr>
				<td colspan="4">
					<fieldset style="width:800px;">
					<legend>已決險種</legend>
					<table id="gridtableCon" border="1" class="main_table"  width="840px"></table>
					</fieldset>
				</td>
			</tr>
			<tr>
				<td colspan="3"><br></td>
			</tr>
			<tr>
				<td align="right" width="200px">已決賠款合計(原幣)：</td>
				<td align="left"><s:property value="reinsInwardClaimData.confirmLossO"/></td>
				<td align="right" width="200px">已決賠款合計(台幣)：</td>
				<td align="left"><s:property value="reinsInwardClaimData.confirmLossNtd"/></td>
			</tr>
			<tr>
				<td align="right" width="200px">已決費用合計(原幣)：</td>
				<td align="left"><s:property value="reinsInwardClaimData.confirmFeeO"/></td>
				<td align="right" width="200px">已決費用合計(台幣)：</td>
				<td align="left"><s:property value="reinsInwardClaimData.confirmFeeNtd"/></td>
			</tr>
			<tr>
				<td align="right" width="200px">已決金額總額(原幣)：</td>
				<td align="left"><s:property value="reinsInwardClaimData.confirmSumO"/></td>
				<td align="right" width="200px">已決金額總額(台幣)：</td>
				<td align="left"><s:property value="reinsInwardClaimData.confirmSumNtd"/></td>
			</tr>
			<tr>
				<td align="right" width="200px">付款對象：</td>
				<td align="left" colspan="3"><s:property value="reinsInwardClaimData.paymentCom"/></td>
			</tr>
			<tr>
				<td align="right" width="200px">賠案內容：</td>
				<td align="left" colspan="3">
					<s:property value="reinsInwardClaimData.claimContent"/>
				</td>
			</tr>
		</table>
	</fieldset>
	<fieldset style="width:800px;">
	    <legend>其他</legend>
		<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="870px">
			<tr>
				<td align="right" width="200px">財務付款日期：</td>
				<td align="left" colspan="3">
					<s:if test='roleType == "CR003" && reinsInwardClaimData.status == "2"'>
						<s:textfield key="reinsInwardClaimData.accountantDate" id="accountantDate" size="8" maxlength="8"/>(YYYYMMDD)
					</s:if>
					<s:else>
						<s:property value="reinsInwardClaimData.accountantDate" />
					</s:else>
				</td>
			</tr>
			<tr>
				<td align="right" width="200px">案件備註：</td>
				<td align="left" colspan="3"><s:textarea key="reinsInwardClaimData.comments" id="comments" rows="5" cols="50" /></td>
			</tr>
			<tr>
				<td align="right" width="200px">輸入人員：</td>
				<td align="left"><s:property value="reinsInwardClaimData.creater"  /></td>
				<td align="right" width="200px">輸入日期：</td>
				<td align="left"><s:property value="reinsInwardClaimData.createDate" /></td>
			</tr>
			<tr>
				<td align="right" width="200px">險部覆核人員：</td>
				<td align="left"><s:property value="reinsInwardClaimData.reviewer" /></td>
				<td align="right" width="200px">險部覆核日期：</td>
				<td align="left"><s:property value="reinsInwardClaimData.reviewDate" /></td>
			</tr>
			<tr>
				<td align="right" width="200px">再保覆核人員：</td>
				<td align="left"><s:property value="reinsInwardClaimData.reinsReviewer" /></td>
				<td align="right" width="200px">再保覆核日期：</td>
				<td align="left"><s:property value="reinsInwardClaimData.reinsReviewDate" /></td>
			</tr>
		</table>
	</fieldset>
	<table width="970px">
		<tr>
			<td align="center">
				<s:if test='(roleType == "CR002" && reinsInwardClaimData.status == "1") || (roleType == "CR003" && reinsInwardClaimData.status == "2")'>
					<input value="覆核確認" type="button" onclick="javascript:form_submit('pass');">&nbsp;&nbsp;&nbsp;&nbsp; 
					<input value="退回立案" type="button" onclick="javascript:form_submit('reject');" />
				</s:if>
				
			</td>
		</tr>
		<tr>
			<br>
		</tr>
	</table>
</s:form>
</body>
</html>