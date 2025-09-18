<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
String path = request.getContextPath();
String title = "測試";
String image = path + "/" + "images/";
String GAMID = "APS999E0";
String mDescription = "測試";
String nameSpace = "/aps/999";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<script language="JavaScript">
	
			
	function form_submit(type){
		 //$("label").html('');
		if("query" == type){
			 $("#mainForm").attr("action","btnQuery.action");
			 $("#mainForm").submit();
		}
		if("query2" == type){
			 $("#mainForm").attr("action","btnQuery2.action");
			 $("#mainForm").submit();
		}
		if("query3" == type){
			 $("#mainForm").attr("action","btnQuery3.action");
			 $("#mainForm").submit();
		}
		if("query4" == type){
			 $("#mainForm").attr("action","btnQuery4.action");
			 $("#mainForm").submit();
		}
		
		if("query5" == type){
			 $("#mainForm").attr("action","btnQuery5.action");
			 $("#mainForm").submit();
		}
		
		if("query6" == type){
			 $("#mainForm").attr("action","btnQuery6.action");
			 $("#mainForm").submit();
		}
		
		if("query7" == type){
			 $("#mainForm").attr("action","btnQuery7.action");
			 $("#mainForm").submit();
		}
		
		if("query8" == type){
			 $("#mainForm").attr("action","btnQuery8.action");
			 $("#mainForm").submit();
		}
		
		if("query9" == type){
			 $("#mainForm").attr("action","btnQuery9.action");
			 $("#mainForm").submit();
		}
		
		if("query10" == type){
			 $("#mainForm").attr("action","btnQuery10.action");
			 $("#mainForm").submit();
		}
		
		if("query11" == type){
			 $("#mainForm").attr("action","btnQuery11.action");
			 $("#mainForm").submit();
		}
		
		if("query12" == type){
			 $("#mainForm").attr("action","btnQuery12.action");
			 $("#mainForm").submit();
		}
		
		if("query13" == type){
			 $("#mainForm").attr("action","btnQuery13.action");
			 $("#mainForm").submit();
		}
		
		if("query14" == type){
			 $("#mainForm").attr("action","btnQuery14.action");
			 $("#mainForm").submit();
		}
		
		if("query15" == type){
			 $("#mainForm").attr("action","btnQuery15.action");
			 $("#mainForm").submit();
		}
		
		if("query16" == type){
			 $("#mainForm").attr("action","btnQuery16.action");
			 $("#mainForm").submit();
		}
		
		if("query17" == type){
			 $("#mainForm").attr("action","btnQuery17.action");
			 $("#mainForm").submit();
		}
		
		if("query18" == type){
			 $("#mainForm").attr("action","btnQuery18.action");
			 $("#mainForm").submit();
		}
		
		if("query19" == type){
			 $("#mainForm").attr("action","btnQuery19.action");
			 $("#mainForm").submit();
		}
		
		if("query20" == type){
			 $("#mainForm").attr("action","btnQuery20.action");
			 $("#mainForm").submit();
		}
		
		if("query21" == type){
			 $("#mainForm").attr("action","btnQuery21.action");
			 $("#mainForm").submit();
		}
		
		if("query22" == type){
			 $("#mainForm").attr("action","btnQuery22.action");
			 $("#mainForm").submit();
		}
		// mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 出單流程 START 
		if("query23" == type){
			 $("#mainForm").attr("action","btnQuery23.action");
			 $("#mainForm").submit();
		}
		// mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 出單流程 END -->
		if("cancel" == type){
			$("#clearForm").submit();
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
	<tbody>
		   <tr>
			  <td width="485px">			      
				  <img src="${pageContext.request.contextPath}/images/Help_Icon.gif" border="0">
			  </td>
			  <td align="right" width="485px">PGMID：<%=GAMID%></td>
		   </tr>
		   <tr>
			   <td colspan="2" align="center" width="970px"><h3><%=title%></h3></td>
		   </tr>
	</tbody>		
</table>

<!-- clear form -->
<s:form theme="simple" action="btnQueryCancel" namespace="/aps/001" id="clearForm" name="clearForm"/>
<!--form 開始 -->
<s:form theme="simple" namespace="/aps/001" id="mainForm" name="mainForm">
<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
	<tbody>
		<tr bgcolor="white">
			<td class="MainTdColor" align="center" width="200px">
				<span id="lbSearch"><b>測試作業</b></span></td>
			<td colspan="5" class="image"></td>
		</tr>
		<tr>
			<td width="200px" align="right"></td>
			<td width="285px" align="left">
				<s:textfield key="textValue" id="textValue"  theme="simple" />
			</td>
			<td width="200px" align="right"></td>
			<td width="285px" align="left">
			<!--
				<s:textfield key="firPremcalcTmp.channelType" id="channelType" maxlength="2" size="2" theme="simple" />
				-->
			</td>			
		</tr>
	</tbody>
</table>
<table width="970px" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="center">
			<input type="button" value="中信新件匯入" onclick="javascript:form_submit('query');"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="中信新件產生回饋檔" onclick="javascript:form_submit('query2');"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="寵物險附件檔下載 " onclick="javascript:form_submit('query3');"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<br/>
			<input type="button" value="行動裝置險保批檔下載 " onclick="javascript:form_submit('query4');"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="行動裝置險銷帳檔下載 " onclick="javascript:form_submit('query5');"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="行動裝置險理賠檔下載 " onclick="javascript:form_submit('query6');"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="行動裝置險佣金檔下載 " onclick="javascript:form_submit('query7');"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="行動裝置險回饋檔上傳 " onclick="javascript:form_submit('query8');"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<br/>
			<input type="button" value="遠傳保批單檔API呼叫" onclick="javascript:form_submit('query9');"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="遠傳要保及批單資料作洗錢、利關人、黑名單檢核" onclick="javascript:form_submit('query10');"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="安達回傳保單及批單處理結果狀態更新" onclick="javascript:form_submit('query12');"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="全虹完修出件維修資料回饋-XML下載" onclick="javascript:form_submit('query11');"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<br/>
			<input type="button" value="寄送行動裝置險電子保單" onclick="javascript:form_submit('query13');"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="行動裝置險要保書檔下載" onclick="javascript:form_submit('query14');"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="行動裝置險資料轉入核心" onclick="javascript:form_submit('query15');"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="執行終止通知書排程" onclick="javascript:form_submit('query16');"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<br/>
			<input type="button" value="行動裝置險遠傳拆帳檔下載 " onclick="javascript:form_submit('query17');"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="安達批單檔下載 " onclick="javascript:form_submit('query18');"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="安達批單檔資料匯入線下批單資料表" onclick="javascript:form_submit('query19');"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<br/>
			<input type="button" value="發送申請補件通知" onclick="javascript:form_submit('query20');"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<!--  
			<input type="button" value="要保書不全取消保險名單資料產生" onclick="javascript:form_submit('query21');"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="上傳要保書不全取消保險名單" onclick="javascript:form_submit('query22');"/>&nbsp;&nbsp;&nbsp;&nbsp;
			-->
			<!-- mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 出單流程 START -->
			<br/><br/>
			<input type="button" value="回傳保單號給數開" onclick="javascript:form_submit('query23');"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<!-- mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 出單流程 END -->
			<input type="button" value="清除" onclick="javascript:form_submit('cancel');"/>
		</td>
	</tr>
</table>
<table width="970px" cellpadding="0" cellspacing="0" border="0">
	<tr>
		<td width="200px" align="right">回傳結果：</td>
		<td width="285px" align="left">
		<!--  
			<s:textfield key="firPremcalcTmp.returnType" id="returnType" maxlength="1" size="1" theme="simple" /><BR>
			-->
		</td>
		<td width="200px" align="right"></td>
		<td width="285px" align="left"></td>	
	</tr>
	<tr>
		<td width="200px" align="right">回傳訊息：</td>
		<td width="285px" align="left">
		<!--  
			<s:textfield key="firPremcalcTmp.returnMsg" id="returnMsg" maxlength="100" size="100" theme="simple" /><BR>
			-->
		</td>
		<td width="200px" align="right"></td>
		<td width="285px" align="left"></td>	
	</tr>
</table>

</s:form>
<!-- form結束 -->
</body>
</html>