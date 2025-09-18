<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "傷害險產生電子保單手動執行作業";
	String image = path + "/" + "images/";
	String GAMID = "APS020E0";
	String mDescription = "傷害險產生電子保單手動執行作業";
	String nameSpace = "/aps/020";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<script language="JavaScript">
	//預設清空
	$("#type").val("");
	function form_submit(batchType,type){
		
		
		if("execute" == type){
			if("2" == batchType){
				var policyNo = $("#policyNo").val();
				if(policyNo == "" || policyNo == null){
					alert("請輸入保單號！");
					return;
				}
			}
			$("#type").val(batchType);
			$("#mainForm").attr("action","btnExecute.action");
			$("#mainForm").submit();
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
<s:form theme="simple" namespace="/aps/020" id="mainForm" name="mainForm">
	<s:hidden name="type" id="type" />
	<table id="table1"  cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="MainTdColor" style="width:200px" align="center">
				<span id="lbSearch"><b>執行作業</b></span></td>
			<td colspan="3" class="image"></td>
		</tr>
	</table>
	<table id="table2" cellpadding="0" cellspacing="0" width="970px">
		<tr>
			<td>
				<P>
					&nbsp;&nbsp;&nbsp;&nbsp;此作業為手動執行呼叫核心系統產生電子保單PDF檔，並不包含認證及寄送流程～
				<P>
			</td>
		</tr>
	</table>
	
  	<table id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tr>
			<td>
				<fieldset>
    				<legend>批次執行</legend>
    				<P>
    				&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="執行" onclick="javascript:form_submit('1', 'execute');"/>
    				<P>
  				</fieldset>
			</td>
		</tr>
		<!--沒有檢查收費所以不能用
		<tr>
			<td>
				<fieldset>
    				<legend>單筆保單執行</legend>
    				<P>
    				&nbsp;&nbsp;&nbsp;&nbsp;保單號：<s:textfield key="policyNo" id="policyNo" theme="simple" />
    				<input type="button" value="執行" onclick="javascript:form_submit('2', 'execute');"/>
    				<P>
  				</fieldset>
  			</td>
		</tr>
		-->
	</table>
	
</s:form>

</body>
</html>