<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "傷害險通報手動執行作業";
	String image = path + "/" + "images/";
	String GAMID = "APS010E0";
	String mDescription = "400轉AML手動執行作業";
	String nameSpace = "/aps/010";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<script language="JavaScript">
	//預設清空
	$("#batchType").val("");
	
	function form_submit(batchType,type){
		if("execute" == type){
			$("#batchType").val(batchType);
			$("#mainForm").attr("action","btnExecute.action");
			$("#mainForm").submit();
		}
		if("btnFileUpd" == type){
			if (confirm("確定上傳檔案？")==true){ 
				$("#mainForm1").attr("action","btnFileUpd.action");
				$("#mainForm1").submit();
			} else return false; 
		}
	}

	$(function(){
		$("#mainForm").submit(function() {
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
		$("#mainForm1").submit(function() {
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
<s:form theme="simple" namespace="/aps/010" id="mainForm" name="mainForm">
	<s:hidden name="batchType" id="batchType" />
	<table id="table2"  cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="MainTdColor" style="width:200px" align="center">
				<span id="lbSearch"><b>執行作業</b></span></td>
			<td colspan="3" class="image"></td>
		</tr>
	</table>
  	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tr>
			<td align="center">
				AS400 COM051WA 承保通報資料轉LIA_UNDWRT_ANNOUNCE：<input type="button" value="執行" onclick="javascript:form_submit('1', 'execute');"/>
			</td>
		</tr>
		<tr>
			<td align="center">
				AS400 COM051WB 收件通報資料轉LIA_RCV_ANNOUNCE：<input type="button" value="執行" onclick="javascript:form_submit('2', 'execute');"/>
			</td>
		</tr>
		<tr>
			<td align="center">
				ASSOC_ANN_ASSUW 承保通報資料轉LIA_UNDWRT_ANNOUNCE：<input type="button" value="執行" onclick="javascript:form_submit('3', 'execute');"/>
			</td>
		</tr>
		<tr>
			<td align="center">
				ASSOC_RCV_ANCMT 收件通報資料轉LIA_RCV_ANNOUNCE：<input type="button" value="執行" onclick="javascript:form_submit('4', 'execute');"/>
			</td>
		</tr>
		<tr>
			<td align="center">
				LIA_UNDWRT_ANNOUNCE 承保通報執行CWP Web service送公會：<input type="button" value="執行" onclick="javascript:form_submit('5', 'execute');"/>
			</td>
		</tr>
		<tr>
			<td align="center">
				LIA_RCV_ANNOUNCE 收件通報執行CWP Web service送公會：<input type="button" value="執行" onclick="javascript:form_submit('6', 'execute');"/>
			</td>
		</tr>
		<tr>
			<td align="center">
				AS400 IPB902I 收件通報執行 CWP Web service送公會：<input type="button" value="執行" onclick="javascript:form_submit('7', 'execute');"/>
			</td>
		</tr>
		<tr>
			<td align="center">
				新核心 IPB902I 收件通報執行 CWP Web service送公會：<input type="button" value="執行" onclick="javascript:form_submit('8', 'execute');"/>
			</td>
		</tr>
	</table>
</s:form>
<s:form theme="simple" action="btnFileUpd" namespace="/aps/010" id="mainForm1" name="mainForm1" method="POST" enctype="multipart/form-data">
	
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tr>
        	<td width="200px" align="right">批次查詢上傳檔案：</td>
			<td width="500px" align="left"></td>
		</tr>
		<tr>
        	<td width="200px" align="right">查詢後預寄送檔案的email：</td>
			<td width="500px" align="left"><s:textfield key="email" id="email" maxLength="100" size="100" /></td>
		</tr>
		<tr>
        	<td width="200px" align="right">檔案路徑：</td>
			<td width="500px" align="left">
			<s:file name="upload" label="File"/>
				<input type="button" value="確定上傳並查詢" onclick="javascript:form_submit('', 'btnFileUpd');"/>			
			</td>
		</tr>
  	</table>

</s:form>
</body>
</html>