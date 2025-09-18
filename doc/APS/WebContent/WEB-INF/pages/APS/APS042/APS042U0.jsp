<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "複保險通知轉檔作業";
	String image = path + "/" + "images/";
	String GAMID = "APS042U0";
	String mDescription = "複保險通知轉檔作業";
	String nameSpace = "/aps/042";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- mantis：FIR0565，處理人員：CC009，需求單編號：FIR0565 住火_複保險通知轉檔作業 -->
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
	});
	
	function checkRpYyyymm(val){
		var regex =/^(20)+([0-9]\d{1}(0+[1-9]|1[012]))*$/;
		if(val!="" && !regex.test(val)){
			return false;
		}
		return true;
	}
	
	function form_submit(type){
		var msg = "轉入作業會將已存在通知年月資料刪除，並重新匯入資料，確定執行?"; 
		if("btnDataImport" == type && checkFile() && confirm(msg)==true){
				$("#mainForm").attr("action","btnDataImport.action");
				$("#mainForm").submit();
		}
		if (!$("#mainForm").valid()) {
			$.unblockUI();
		}
	}
	
	function checkFile(){
		var rpYyyymm = $("#rpYyyymm").val();
		if(!checkRpYyyymm(rpYyyymm)){
			alert("通知年月需為合理日期(YYYYMM)。");
			return false;
		}
		
		var uploadFile = $("#upload").val();
		if(uploadFile == ""){
			alert("請選擇xlsx檔案。");
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
	msg = msg.replace(';', ';\n');
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
<s:form theme="simple" action="btnDataImport" namespace="/aps/042" id="mainForm" name="mainForm" method="POST" enctype="multipart/form-data">
	<table id="table2"  cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="MainTdColor" style="width:200px" align="center">
				<span id="lbSearch"><b>轉檔作業</b></a></span>
			</td>
			<td class="image" style="width:20px"></td>
			<td class="SelectTdColor" style="width:200px" align="center">
				<span id="lbSearch"><a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoQuery.action"><b>查詢作業</b></span></td>
			<td class="imageGray" style="width:20px"></td>
			<td colspan="5"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tr>
        	<td width="120px" align="right">通知年月(YYYYMM)：</td>
			<td width="500px" align="left">
			<s:textfield key="rpYyyymm" id="rpYyyymm" theme="simple" size="6" maxlength="6"/>
			</td>
			<td colspan="5"></td>
		</tr>
		<tr>
        	<td width="120px" align="right">檔案路徑：</td>
			<td width="500px" align="left">
			<s:file name="upload" id="upload" label="File"/>
				<input type="button" value="確定轉入" onclick="javascript:form_submit('btnDataImport');"/>			
			</td>
			<td colspan="5"></td>
		</tr>
		<tr>
        	<td width="200px" align="right">說明：</td>
			<td width="800px" align="left">
				1.本作業提供每月複保險通知轉檔。<br>
				2.本作業會先刪除已存在相同通知年月之資料。<br>
				3.檔案轉入格式請參閱<a href="${pageContext.request.contextPath}<%=nameSpace%>/btnDowloadSample.action">SAMPLE</a><br>
				4.轉入動作完成後可以至查詢功能查詢批號並下載excel檔案。<br>
			</td>
			<td colspan="5"></td>       	
		</tr>
  	</table>
  	<table width="970px" cellpadding="0" cellspacing="0" >
</table>
</s:form>

</body>
</html>