<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String title = "微型電動二輪車【已領牌&未領牌】轉入作業";
	String image = path + "/" + "images/";
	String GAMID = "APS040U0";
	String mDescription = "微型電動二輪車【已領牌&未領牌】轉入作業";
	String nameSpace = "/aps/040";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- mantis：CAR0504，處理人員：CC009，需求單編號：CAR0504 微型電動二輪車【已領牌&未領牌】資料交換作業-->
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
		if("btnDataImport" == type && checkFile() && confirm("是否轉入資料?")==true){ 
				$("#mainForm").attr("action","btnDataImport.action");
				$("#mainForm").submit();
		}
		if (!$("#mainForm").valid()) {
			$.unblockUI();
		}
	}
	
	function checkFile(){
		var ultype = $("#ultype").val();
		if('0' == ultype){
			alert("請先選擇上傳類型。");
			return false;
		}
		var uploadFile = $("#upload").val();
		if(uploadFile == ""){
			alert("請先選擇檔案。");
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
<s:form theme="simple" action="btnFileUpd" namespace="/aps/040" id="mainForm" name="mainForm" method="POST" enctype="multipart/form-data">
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
        	<td width="200px" align="right">檔案路徑：</td>
			<td width="500px" align="left">
			<s:file name="upload" id="upload" label="選擇檔案"/>
			</td>
			<td colspan="5"></td>
		</tr>
		<tr>
        	<td width="200px" align="right">檔案類型：</td>
			<td width="500px" align="left">
				<s:select key="ultype" id="ultype" theme="simple" list="#{'0':'請選擇','1':'已領牌','2':'未領牌'}" />
				<input type="button" value="確定轉入" onclick="javascript:form_submit('btnDataImport');"/>			
			</td>
			<td colspan="5"></td>
		</tr>
		<tr>
        	<td width="200px" align="right">作業說明：</td>
			<td width="800px" align="left">
				1.本作業提供微型電動二輪車【已領牌&未領牌】(TVMETP&30NOMETP)資料匯入，每次匯入採全增全刪方式進行。<br>
				2.上傳成功僅代表檔案已寫入資料庫。<br>
			</td>
			<td colspan="5"></td>       	
		</tr>
  	</table>
</s:form>
</body>
</html>