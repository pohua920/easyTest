<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
String path = request.getContextPath();
String title = "中信代理投保通知處理";
String image = path + "/" + "images/";
String GAMID = "APS012E0";
String mDescription = "中信代理投保通知處理";
String nameSpace = "/aps/012";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<style>
h4 {
   width: 100%; 
   text-align: center; 
   border-bottom: 1px solid #000; 
   line-height: 0.1em;
   margin: 10px 0 20px; 
} 

h4 span { 
    background:#efefef;; 
    padding:0 10px; 
}
</style>
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
		 //$("label").html('');
		if("query" == type){
			 $("#mainForm").attr("action","btnQuery.action");
			 $("#mainForm").submit();
		}
		if("query2" == type){
			 $("#mainForm").attr("action","btnQuery2.action");
			 $("#mainForm").submit();
		}	
		if("lnkGoEditO180Fix" == type){
			 $("#mainForm").attr("action","lnkGoEditO180Fix.action");
			 $("#mainForm").submit();
		}	
		
		if("btnCancelBatch" == type){
			 $("#mainForm").attr("action","btnCancelBatch.action");
			 $("#mainForm").submit();
		}
		
		if("btnGenCSV" == type){
			var token = new Date().getTime();
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

		    var pollDownload = setInterval(function() {
		        if (document.cookie.indexOf("aps012Download=" + token) > -1) {
		            document.cookie = "aps012Download=" + token + "; expires=" + new Date(0).toGMTString() + "; path=/";
		            $.unblockUI();
		            clearInterval(pollDownload);
		        }
		    }, 500);
		    $("#token").val(token);
			 $("#mainForm").attr("action","btnGenCSV.action");
			 $("#mainForm").submit();
		}
		
		if("btnGenExcel" == type){
			var token = new Date().getTime();
		    //var wait = document.getElementById("wait");
		    //wait.style.display = "block";
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

		    var pollDownload = setInterval(function() {
		        if (document.cookie.indexOf("aps012Download=" + token) > -1) {
		            document.cookie = "aps012Download=" + token + "; expires=" + new Date(0).toGMTString() + "; path=/";
		            //wait.style.display = "none";
		            $.unblockUI();
		            clearInterval(pollDownload);
		        }
		    }, 500);
		    $("#token").val(token);
			 $("#mainForm").attr("action","btnGenExcel.action");
			 $("#mainForm").submit();
		}
		
		if("btnCompareData" == type){
			 $("#mainForm").attr("action","btnCompareData.action");
			 $("#mainForm").submit();
		}
		
		if("btnDownloadCompareData" == type){
			 $("#mainForm").attr("action","btnDownloadCompareData.action");
			 $("#mainForm").submit();
		}
		
		if("btnDownloadCompareData2" == type){
			 $("#mainForm").attr("action","btnDownloadCompareData2.action");
			 $("#mainForm").submit();
		}
	}
	
	function redirect(oid, o180filename){
		$("#oid").val(oid);
		$("#o180filename").val(o180filename);
		form_submit("lnkGoEditO180Fix");
	}
	
	function cancelBatch(oid){
		if(confirm("確認是否作廢?")){
			$("#oid").val(oid);
			form_submit("btnCancelBatch");
		}
	}
	
	function genCSV(oid){
		$("#oid").val(oid);
		form_submit("btnGenCSV");
	}
	
	function genExcel(oid, o180filename, excelType){
		$("#oid").val(oid);
		$("#o180filename").val(o180filename);
		$("#excelType").val(excelType);
		form_submit("btnGenExcel");
	}
	
	function compareData(oid){
		$("#oid").val(oid);
		form_submit("btnCompareData");
	}
	
	function genExcelDiff(matchcoreoid){
		$("#matchcoreoid").val(matchcoreoid);
		form_submit("btnDownloadCompareData");
	}
	
	function genExcelDiff2(matchcoreoid){
		$("#matchcoreoid").val(matchcoreoid);
		form_submit("btnDownloadCompareData2");
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
<s:url action="lnkGoEditO180Fix?" namespace="/aps/012" var="goEditO180Fix"/>
<s:url action="lnkGoExecute" namespace="/aps/012" var="goExecute"/>
<s:url action="btnGenCSV" namespace="/aps/012" var="genCVS"/>
<s:url action="default" namespace="/aps/999" var="go999"/>
<s:url action="default" namespace="/aps/003" var="go003"/>
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
<s:form theme="simple" action="btnQueryCancel" namespace="/aps/012" id="clearForm" name="clearForm"/>
<!--form 開始 -->
<s:form theme="simple" namespace="/aps/012" id="mainForm" name="mainForm">
<s:hidden name="aps012DetailVo.oid" id="oid"/>
<s:hidden name="aps012DetailVo.o180filename" id="o180filename"/>
<s:hidden name="aps012DetailVo.excelType" id="excelType"/>
<s:hidden name="aps012DetailVo.matchcoreoid" id="matchcoreoid"/>
<s:hidden name="token" id="token"/>
<table id="table2"  cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="MainTdColor" style="width:200px" align="center">
				<span id="lbSearch"><b>查詢作業</b></span>
			</td>
			<td class="image" style="width:20px"></td>
			<td class="SelectTdColor" style="width:200px" align="center">
				<span id="lbSearch"><a href='${goExecute}'><b>執行作業</b></a></span>
			</td>
			<td colspan="3" class="imageGray" ></td>
		</tr>
</table>
<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
	<tbody>
		<tr>
			<td width="200px" align="right">民國年：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.year" id="sYear" maxlength="3" size="10" theme="simple" />
			</td>
			<td width="200px" align="right">月：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.month" id="sMonth" maxlength="2" size="10" theme="simple" />
			</td>			
		</tr>
		<tr>
			<td width="200px" align="right">作廢註記：</td>
			<td width="285px" align="left">
				<s:select key="filter.invalidflag" id="invalidflag" list="#{'*':'全選','Y':'已作廢','N':'未作廢'}"/>
			</td>
			<td width="200px" align="right"></td>
			<td width="285px" align="left"></td>			
		</tr>
	</tbody>
</table>
<table width="970px" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="center">
			<input type="button" value="查詢" onclick="javascript:form_submit('query');"/>
		</td>
	</tr>
</table>
<s:if test="devResults != null && 0 != devResults.size">
<table cellspacing="1" cellpadding="1" border="0" width="970px">
	<tr>
		<td>
		<div align="right">
			<custom:ddlChangePage 
			    formId="mainForm"
				name="pageInfo" 
				nameSpace="/aps/012"
				currentPage="${pageInfo.currentPage}" 
				pageSize="${pageInfo.pageSize}"   
				selectOnChange="ddlPageSizeChanged" 
				textOnChange="txtChangePageIndex"  
				rowCount="${pageInfo.rowCount}"
				pageCount="${pageInfo.pageCount}"/>
		</div>
		</td>
	</tr>
</table>
<!--Grid Table-->
<table border="1" id="gridtable" width="970px" border="0" class="main_table">
	<tr align="center">
		<th>年月日</th>	
		<th>批次號碼</th>
		<th>個金筆數</th>
		<th>法金筆數</th>
		<th>不續保通知筆數</th>
		<th>是否轉檔中</th>
		<th>核心比對</th>
		<th>異動</th>
		<th>下載相關檔案</th>
		<th>匯入時間</th>
		<th>匯入人員</th>
	</tr>
	<s:iterator var="row" value="devResults">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="left"><s:property value="ctbcno"/></td>
		<td align="center">
			<c:if test="${invalidflag == 'Y'}"><font color="red"><del></c:if><s:property value="oid"/><c:if test="${invalidflag == 'Y'}"></del></font></c:if>
		</td>
		<td align="left"><s:property value="p180datarow"/></td>
		<td align="left"><s:property value="c180datarow"/></td>
		<td align="left"><s:property value="dontnoticedatarow"/></td>
		<td align="left">
			<c:if test="${transing == 'Y'}">轉檔中</c:if>
			<c:if test="${transing == 'N'}">轉檔成功</c:if>
			<c:if test="${transing == 'E'}">轉檔失敗</c:if>
		</td>
		<td align="left">
			<c:if test="${matchcoreing == 'Y'}">比對中</c:if>
			<c:if test="${transing == 'N' && invalidflag != 'Y'}"><input type="button" value="進行比對" onclick="javascript:compareData('${row.oid}');"/></c:if>
			<c:if test="${matchcoreing == 'N'}">
			比對結束
				<c:if test="${invalidflag != 'Y'}">
					<!-- mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 start-->
					<input type="button" value="下載比對結果難字更換" onclick="javascript:genExcelDiff2('${row.matchcoreoid}');"/>
					<input type="button" value="下載比對結果" onclick="javascript:genExcelDiff('${row.matchcoreoid}');"/>
					<!-- mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 end-->
				</c:if>
			</c:if>
		</td>
		<td align="center">
			<c:if test="${transing == 'N'}">
				<c:if test="${invalidflag != 'Y'}">
					<input type="button" value="更新個金資料" onclick="javascript:redirect('${row.oid}','${row.p180filename}');"/>
					<input type="button" value="更新法金資料" onclick="javascript:redirect('${row.oid}','${row.c180filename}');"/>
					<input type="button" value="作廢" onclick="javascript:cancelBatch('${row.oid}');"/>
				</c:if>
			</c:if>
		</td>
		<td align="center">
			<c:if test="${transing == 'N' && invalidflag != 'Y'}">
				<input type="button" value="要保書資料CSV" onclick="javascript:genCSV('${row.oid}');"/>
				<input type="button" value="個金續保通知excel" onclick="javascript:genExcel('${row.oid}','${row.p180filename}','P');"/>
				<input type="button" value="法金續保通知excel" onclick="javascript:genExcel('${row.oid}','${row.c180filename}','C');"/>
			</c:if>
		</td>
		<td align="left"><s:property value="dcreate"/></td>
		<td align="left"><s:property value="icreate"/></td>
	</tr>
	</s:iterator>
</table>
</s:if>
</s:form>
<!-- form結束 -->
</body>
</html>