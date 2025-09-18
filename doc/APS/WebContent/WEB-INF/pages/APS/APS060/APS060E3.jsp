<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String title = "元大續保資料處理作業-明細資料";
	String image = path + "/" + "images/";
	String GAMID = "APS060E1";
	String mDescription = "元大續保資料處理作業";
	String nameSpace = "/aps/060";
%>
<!-- mantis：FIR0676，處理人員：DP0706，需求單編號：FIR0676_住火_元大續保作業_N+1比對擔保品檔案-->
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
	});

	function form_submit(type){
		if("btnDataImport" == type){
			$("#mainForm").attr("action","btnDataImport.action");
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
	
	function checkDataStatus(dataStatus){
		if(dataStatus === '0' || dataStatus === '1'){
			alert("轉檔異常，無法進入要保資料頁。");
			return false;
		}
		return true;
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
<s:form theme="simple" namespace="/aps/060" id="mainForm" name="mainForm" method="POST"  enctype="multipart/form-data">
	<table id="table2" cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="SelectTdColor" style="width: 200px" align="center">
				<span id="lbSearch"><a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoDetailQuery.action?batchNo=<s:property value="batchNo"/>&rnYyyymm=<s:property value="rnYyyymm"/>"><b>明細查詢作業</b></a></span>
			</td>
			
			<td class="MainTdColor" style="width: 200px" align="center">
				<span id="lbSearch"><b>N+1轉檔作業</b></span>
			</td>
			<td class="image" style="width:20px" colspan="3"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tr>
			<td width="200px" align="right">批次號碼：</td>
			<td width="285px" align="left">
				<s:property value="batchNo"/>
				<s:hidden name="batchNo" id="batchNo"/>
			</td>
			<td width="200px" align="right">續保年月：</td>
			<td width="285px" align="left">
				<s:property value="rnYyyymm"/>
			</td>
		</tr>
		<tr>
        	<td width="200px" align="right">檔案路徑：</td>
			<td width="500px" align="left">
				<s:file name="upload" id="upload" label="File"/>
				<input type="button" value="確定轉檔" onclick="javascript:form_submit('btnDataImport');"/>		
			</td>
			<td colspan="5"><!--mantis：FIR0684，處理人員：DP0706，需求單編號：FIR0684_住火_APS元大續保作業_N+1轉檔新增填寫出單業務員欄位-->
			業務員登錄證字號：<s:textfield key="handleridentifynumber" id="handleridentifynumber" />
			</td>
		</tr>
		<tr>
        	<td width="200px" align="right">作業說明：</td>
			<td width="800px" align="left">
				1.本作業為N+1上傳元大續保明細清單檔案<br>
				2.上檔案後會比對元大資料，並寫入擔保品編號，判斷是否續保後<br>鎖定或剔退
				3.若重複上傳檔案已鎖定、剔退資料不會再次處理，請手動調整資料<br>並進行資料鎖定<br>
				4.檔案轉入格式請參閱<a href="${pageContext.request.contextPath}<%=nameSpace%>/btnDowloadSample.action">SAMPLE</a>
			</td>
			<td colspan="2"></td>       	
		</tr>
	</table>
	<table width="970px" cellpadding="0" cellspacing="0">
		<tr>
			<td align="center">
				<a href="${pageContext.request.contextPath}<%=nameSpace%>/btnQuery.action?goBack=Y"><input type="button" value="回上頁"/></a>
			</td>
		</tr>
	</table>
	
	
</s:form>
</body>
</html>