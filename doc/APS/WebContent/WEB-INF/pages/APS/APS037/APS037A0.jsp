<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "保發中心-保單存摺PRODCEDURE轉中介Table排程維護";
	String image = path + "/" + "images/";
	String GAMID = "APS037A0";
	String mDescription = "保發中心-保單存摺PRODCEDURE轉中介Table排程維護";
	String nameSpace = "/aps/037";
%>
<!-- mantis：OTH0131，處理人員：BJ085，需求單編號：OTH0131 保發中心-保單存摺各險寫入中介Table作業 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<script language="JavaScript">
	$(document).ready(function(){
		<c:if test="${not empty errorMsg}">
			var msgg = "有錯誤\n";
			<c:forEach items="${errorMsg}" var="entry">
				$('input[id^=<c:out value="${entry.key}" /> ]').css('background-color','yellow');
				<c:if test="${entry.value != ''}">
					msgg = msgg + '<c:out value="${entry.value}" />' + "\n";
				</c:if>
			</c:forEach>
			alert(msgg);
		</c:if>
		
		$('#undate').datepicker({dateFormat:"yyyy/mm/dd"});
		
		$("#mainForm").validate({
			isShowLabel:false,
			isAlertLocalMsg:false,
			rules: {
				"type":{
					"required":true
				},
				"filename":{
					"checkFilename":""
				},
				"undate":{
					"checkUndate":""
				},
				"riskcode":{
					"checkRiskcode":""
				}
			},
			messages: {
				"type":{
					"required":"請選擇補跑方式"
				},
				"filename":{
					"checkFilename":"補跑方式為整批時，不得選擇執行排程"
				},
				"undate":{
					"checkUndate":"請輸入簽單日期"
				},
				"riskcode":{
					"checkRiskcode":"補跑方式為單筆時，請輸入險種別"
				}
			}
		});
	});

	$.validator.addMethod("checkRiskcode", function(value,element,param) { 
		return checkRiskcode();						
	},"");
	$.validator.addMethod("checkFilename", function(value,element,param) { 
		return checkFilename();						
	},"");
	$.validator.addMethod("checkUndate", function(value,element,param) { 
		return checkUndate();						
	},"");

	function checkRiskcode() {
		var check = true;
		var riskcode = $("#riskcode").val();
		var type = $("#type").val();
	    if(type=="P" && riskcode == ""){
	    	check = false;
	    }
		return check;
	}

	function checkFilename() {
		var check = true;
		var filename = $("#filename").val();
		var type = $("#type").val();
	    if(filename!="BLANK" && type != "P"){
	    	check = false;
	    }
		return check;
	}
	
	function checkUndate() {
		var check = true;
		var undate = $("#undate").val();
		var filename = $("#filename").val();
	    if(filename!="BLANK" && filename!="OTH_PASSBOOK_TO_400_A" && undate == ""){
	    	check = false;
	    }
		return check;
	}

	function form_submit(type){
		if("excute" == type){
			 $("#mainForm").attr("action","btnExecute.action");
			 $("#mainForm").submit();
		}
		if (!$("#mainForm").valid()) {
			$.unblockUI();
		}
	}

	function showMsg(msg){
		alert(msg);
	}

	var msg = "<%=request.getAttribute("message")%>";
	if('' != msg && 'null' != msg){
		showMsg(msg);
	}
</script>
</head>

<body style="margin:0;text-align:left">
<table cellspacing="1" cellpadding="1" width="970px" border="0">
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
<!-- form 開始 -->
<s:form theme="simple" namespace="/aps/037" id="mainForm" name="mainForm">
	<table id="table2"  cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="SelectTdColor" style="width:200px" align="center">
				<span id="lbSearch"><a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoQuery.action"><b>查詢作業</b></a></span>
			</td>
			<td class="imageGray" style="width:20px"></td>
			<td class="MainTdColor" style="width:200px" align="center">
				<span id="lbSearch"><b>手動執行作業</b></span></td>
			<td colspan="3" class="image"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tr>
        	<td width="200px" align="right"><font color="#FF0000">*</font>補跑方式：</td>
			<td width="500px" align="left">
				<s:select key="type" id="type" list="#{'P':'單筆','ALL':'整批'}" theme="simple"/>
			</td>
			<td colspan="5"></td>
			<td width="200px" align="right"><font color="#FF0000">*</font>執行排程：</td>
			<td width="500px" align="left">
				<s:select key="filename" id="filename" theme="simple" list="#{'BLANK':'',
				'SP_OTH_PASSBOOK_MAR_P':'SP_OTH_PASSBOOK_MAR_P','SP_OTH_PASSBOOK_MAR_E':'SP_OTH_PASSBOOK_MAR_E',
				'SP_OTH_PASSBOOK_FIR_P':'SP_OTH_PASSBOOK_FIR_P','SP_OTH_PASSBOOK_FIR_E':'SP_OTH_PASSBOOK_FIR_E',
				'SP_OTH_PASSBOOK_CAL_P':'SP_OTH_PASSBOOK_CAL_P','SP_OTH_PASSBOOK_CAL_E':'SP_OTH_PASSBOOK_CAL_E',
				'SP_OTH_PASSBOOK_CAR_P':'SP_OTH_PASSBOOK_CAR_P','SP_OTH_PASSBOOK_CAR_E':'SP_OTH_PASSBOOK_CAR_E',
				'SP_OTH_PASSBOOK_LOP_P':'SP_OTH_PASSBOOK_LOP_P','SP_OTH_PASSBOOK_LOP_E':'SP_OTH_PASSBOOK_LOP_E',
				'OTH_PASSBOOK_TO_400_A':'AS400_TO_PASSBOOK'}"/>
			</td>
			<td colspan="5"></td>
		</tr>
		<tr>
        	<td width="200px" align="right">險種別：</td>
			<td width="500px" align="left">
				<s:textfield key="riskcode" id="riskcode" theme="simple"/>
			</td>
			<td colspan="5"></td>
        	<td width="200px" align="right">保/批單號碼：</td>
			<td width="500px" align="left">
				<s:textfield key="policyno" id="policyno" theme="simple"/>
			</td>
			<td colspan="5"></td>
		</tr>
		<tr>
        	<td width="200px" align="right"><font color="#FF0000">*</font>簽單日期：</td>
			<td width="500px" align="left">
				<s:textfield key="undate" id="undate" theme="simple"/>
			</td>
			<td colspan="5"></td>
		</tr>
		<tr>
			<td width="200px" align="right"></td>
			<td width="500px" align="right">
				<input type="button" value="執行" onclick="javascript:form_submit('excute');" />
			</td>
			<td colspan="5"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tr>
        	<td align="left">
        	使用說明：
			此畫面提供單支及整批執行方式補跑<br>
			選擇[單支]請輸入險種別、保/批單號、執行排程、簽單日期<br>
			執行「AS400_TO_PASSBOOK」排程，險種別請輸入「AS400」，可不用輸入簽單日期<br>
			選擇[整批]請輸入簽單日期，其餘欄位空白即可。<br>
			※注意：<br>
			因PROCEDURE執行時資料會重複寫入，故執行補跑時請注意已存在OTH_BATCH_PASSBOOK_LIST的資料是否需要刪除等異常問題，再重新補跑。
        	</td>
		</tr>
	</table>
</s:form>

</body>
</html>