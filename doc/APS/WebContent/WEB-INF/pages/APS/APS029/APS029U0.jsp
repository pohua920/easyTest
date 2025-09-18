<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String title = "AS400外銀續保匯入查詢作業";
	String image = path + "/" + "images/";
	String GAMID = "APS029U0";
	String mDescription = "AS400外銀續保匯入查詢作業";
	String nameSpace = "/aps/029";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- mantis：FIR0388，處理人員：BJ085，需求單編號：FIR0388 AS400續保資料匯入  -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<script language="JavaScript">
	$(document).ready(function(){
		$("#mainForm").submit(function () {
			$.blockUI({
				//blockUI：設定頁面指定區域顯示執行中文字(如Loading...)並鎖定該區域限制輸入。
				border: 'none',
				padding: '15px',//padding：同時設定四個邊留白的捷徑屬性
				backgroundColor: '#000',//backgroundColor：訊息背景顏色
				color: '#fff',//color：訊息字樣顏色
				'-webkit-border-radius': '10px',//WebKit設定四邊的圓角為10px
				'-moz-border-radius': '10px',//Mozilla設定四邊的圓角為10px
				opacity: .5//opacity：指定表單和其控制項的透明度等級
			});
		});
	});
	
	function checkRnYyyymm(val){
		var regex =/^(20)+([0-9]\d{1}(0+[1-9]|1[012]))*$/;
		if(val!="" && !regex.test(val)){
			return false;
		}
		return true;
	}
	
	function form_submit(type){
		var msg = "確定轉入將刪除該業務來源"+$("#rnYyyymm").val()+"已轉入之資料，並以本次之資料匯入系統?"; 
		if("btnDataImport" == type && checkFile() && confirm(msg)==true){ 
				$("#mainForm").attr("action","btnDataImport.action");
				$("#mainForm").submit();
		}
		if (!$("#mainForm").valid()) {
			$.unblockUI();
		}
	}
	
	function checkFile(){
		var rnYyyymm = $("#rnYyyymm").val();
		if($("#businessnature").val()=="" || rnYyyymm==""){
			alert("請先輸入業務來源及續保年月。");
			return false;
		}
		
		if(!checkRnYyyymm(rnYyyymm)){
			alert("續保年月需為6碼數字且為合理日期(YYYYMM)。");
			return false;
		}
		
		var uploadFile = $("#upload").val();
		if(uploadFile == ""){
			alert("請先選擇檔案。");
			return false;
		}
		
		var pos = uploadFile.lastIndexOf(".");
		if(pos == -1 || uploadFile.substring(pos+1) !== "xlsx"){
			alert("上傳文件只允許.xlsx副檔名！");
			return false;
		}
		return true;
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
<!-- clear form -->
<!-- form 開始 -->
<s:form theme="simple" action="btnFileUpd" namespace="/aps/029" id="mainForm" name="mainForm" method="POST" enctype="multipart/form-data">
	<table id="table2"  cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="MainTdColor" style="width:200px" align="center">
				<span id="lbSearch"><b>轉入作業</b></span>
			</td>
			<td class="image" style="width:20px"></td>	
			<td class="SelectTdColor" style="width:200px" align="center">
				<span id="lbSearch"><a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoQuery.action"><b>查詢作業</b></a></span></td>
			<td colspan="3" class="imageGray"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tr>
        	<td width="200px" align="right">業務來源：</td>
			<td width="500px" align="left">
				<!-- mantis：FIR0619，處理人員：CD078，需求單編號：FIR0619 住火_臺銀續保作業_AS400資料匯入新增臺銀格式 -->
				<!-- mantis：FIR0666，處理人員：DP0714，住火_元大續保作業_AS400資料匯入新增元大格式 -->
				<s:select key="businessnature" id="businessnature" theme="simple" list="#{'':'','I99065':'I99065-板信','I99060':'I99060-日盛','I99004':'I99004-臺銀','I00006':'I00006-元大'}" />
			</td>
			<td colspan="5"></td>
		</tr>
		<tr>
        	<td width="200px" align="right">續保年月：</td>
			<td width="500px" align="left">
				<s:textfield key="rnYyyymm" id="rnYyyymm" theme="simple" size="6" maxlength="6"/>
				(西元年YYYYMM，例如：202110)
			</td>
			<td colspan="5"></td>
		</tr>
		<tr>
        	<td width="200px" align="right">檔案路徑：</td>
			<td width="500px" align="left">
			<s:file name="upload" id="upload" label="File"/>
				<input type="button" value="確定轉入" onclick="javascript:form_submit('btnDataImport');"/>			
			</td>
			<td colspan="5"></td>
		</tr>
		<tr>
        	<td width="200px" align="right">作業說明：</td>
			<td width="800px" align="left">
				1.本作業提供AS400外銀續保資料匯入。<br>
				2.每次上傳時會先刪除資料庫內相同「業務來源+續保年月」之資料。<br>
				3.本資料供AS400續保移回新核心過渡期間補資料使用。<br>
				<!-- mantis：FIR0619，處理人員：CD078，需求單編號：FIR0619 住火_臺銀續保作業_AS400資料匯入新增臺銀格式  Start -->
				4.業務來源I99065、I99060上傳格式為XLSX檔，匯入格式請參閱<a href="${pageContext.request.contextPath}<%=nameSpace%>/btnDowloadSample.action">SAMPLE</a>。<br>
				5.業務來源I99004上傳格式為XLSX檔，匯入檔案為AS400.16下載之檔案，匯入格式請參閱<a href="${pageContext.request.contextPath}<%=nameSpace%>/btnDowloadSample.action?businessnature=I99004">SAMPLE</a>。<br>
				<!-- mantis：FIR0619，處理人員：CD078，需求單編號：FIR0619 住火_臺銀續保作業_AS400資料匯入新增臺銀格式  End -->
                <!-- mantis：FIR0666，處理人員：DP0714，住火_元大續保作業_AS400資料匯入新增元大格式 start -->
				6.業務來源I00006上傳格式為XLSX檔，匯入檔案為AS400.16下載之檔案，匯入格式請參閱<a href="${pageContext.request.contextPath}<%=nameSpace%>/btnDowloadSample.action?businessnature=I00006">SAMPLE</a>。<br>
				7.僅「黃底」欄位會被匯入，其餘欄位不會儲存。
                <!-- mantis：FIR0666，處理人員：DP0714，住火_元大續保作業_AS400資料匯入新增元大格式 end -->
			</td>
			<td colspan="5"></td>       	
		</tr>
  	</table>
</s:form>
</body>
</html>