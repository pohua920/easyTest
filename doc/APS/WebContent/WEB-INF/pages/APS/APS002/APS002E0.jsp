<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
String path = request.getContextPath();
String title = "中信新件轉入查詢作業";
String image = path + "/" + "images/";
String GAMID = "APS002E0";
String mDescription = "中信新件轉入查詢作業";
String nameSpace = "/aps/002";
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
	
		//加上小日曆
		//$('#sDate').datePicker({"startDate":"01/01/1911","dateType" :"roc","dateFormat":"yyy/mm/dd"});
		//$('#eDate').datePicker({"startDate":"01/01/1911","dateType" :"roc","dateFormat":"yyy/mm/dd"});
		//$('#sDate').datePicker({"startDate":"01/01/1911","dateType" :"ad","dateFormat":"yyyy/mm/dd"});
		//$('#eDate').datePicker({"startDate":"01/01/1911","dateType" :"ad","dateFormat":"yyyy/mm/dd"});
		//$.datepicker.setDefaults($.datepicker.regional['zh-TW']);
		$('#sDate').datepicker({dateFormat:"yy/mm/dd"});
		$('#eDate').datepicker({dateFormat:"yy/mm/dd"});
		
		//validate
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
<s:url action="lnkGoDetail?" namespace="/aps/002" var="goDetail"/>
<s:url action="default" namespace="/aps/999" var="go999"/>
<s:url action="default" namespace="/aps/003" var="go003"/>
<body style="margin:0;text-align:left">
<table cellspacing="1" cellpadding="1" width="970px" border="0">
	<tbody>
		   <tr>
			  <td width="485px">			      
				  <img src="${pageContext.request.contextPath}/images/Help_Icon.gif" border="0">
				  <s:if test="userInfo.userId == 'ce035' ">
				  	<a href='${go999}'>　</a>
				  </s:if>
			  </td>
			  <td align="right" width="485px">PGMID：<%=GAMID%></td>
		   </tr>
		   <tr>
			   <td colspan="2" align="center" width="970px"><h3><%=title%></h3></td>
		   </tr>
	</tbody>		
</table>

<!-- clear form -->
<s:form theme="simple" action="btnQueryCancel" namespace="/aps/002" id="clearForm" name="clearForm"/>
<!--form 開始 -->
<s:form theme="simple" namespace="/aps/002" id="mainForm" name="mainForm">
<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
	<tbody>
		<tr bgcolor="white">
			<td class="MainTdColor" align="center" width="200px">
				<span id="lbSearch"><b>查詢作業</b></span></td>
			<td colspan="5" class="image"></td>
		</tr>
		<tr>
			<td width="200px" align="right">執行日期：</td>
			<td width="285px" align="left">
				<table border="0" cellspacing="0" cellpadding="0" >
					<tr>
						<td><s:textfield key="filter.sDate" id="sDate" maxlength="10" size="10" theme="simple" />~</td>
						<td><s:textfield key="filter.eDate" id="eDate" maxlength="10" size="10" theme="simple" /></td>
					</tr>
				</table>
			</td>
			<td width="200px" align="right">檔案狀態：</td>
			<td width="285px" align="left">
				<s:select key="filter.fileStatus" id="fileStatus" list="fileStatusMap"/>
			</td>			
		</tr>
		<tr>
			<td width="200px" align="right">批次號碼：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.batchNo" id="batchNo" theme="simple" />
			</td>
			<td width="200px" align="right">批次序號：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.batchSeq" id="batchSeq"  theme="simple" />
			</td>			
		</tr>
		<tr>
			<td width="200px" align="right">ZIP檔名稱：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.filenameZip" id="filenameZip" theme="simple" />
			</td>
			<td width="200px" align="right">　</td>
			<td width="285px" align="left">　</td>			
		</tr>
		<tr>
			<td width="200px" align="right">受理編號：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.orderSeq" id="orderSeq" theme="simple" />
			</td>
			<td width="200px" align="right">保單號碼：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.policyno" id="policyno" theme="simple" />
			</td>			
		</tr>	
		<tr>
			<td align="center" colspan='4'>
				<h4><span>以下查詢條件僅限「受理編號清單查詢」使用</span></h4>
			</td>	
		</tr>	
		<tr>
			<td width="200px" align="right">受理編號狀態：</td>
			<td width="285px" align="left">
				<s:select key="filter.orderSeqStatus" id="orderSeqStatus" list="orderSeqStatusMap"/>
			</td>
			<td width="200px" align="right">下單狀態：</td>
			<td width="285px" align="left">
				<s:select key="filter.fkStatus" id="fkStatus" list="fkStatusMap"/>
			</td>			
		</tr>
		<tr>
			<td width="200px" align="right">送件類型：</td>
			<td width="285px" align="left">
				<s:select key="filter.sendType" id="sendType" list="sendTypeMap"/>
			</td>
			<td width="200px" align="right">服務人員代號：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.coreHandler1code" id="coreHandler1code" theme="simple" />
			</td>			
		</tr>
		<tr>
			<td width="200px" align="right">單位代號：</td>
			<td width="285px" align="left">
			    <!-- mantis：FIR0708，處理人員：DP0714，住火_APS_中信新件轉入查詢作業增加多個單位邏輯 -->
				<s:textfield key="filter.coreComcode" id="coreComcode" theme="simple" /> 多筆請以逗號 "," 分隔
			</td>
			<td width="200px" align="right"></td>
			<td width="285px" align="left"></td>			
		</tr>
	</tbody>
</table>
<table width="970px" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="center">
			<input type="button" value="檔案清單查詢" onclick="javascript:form_submit('query');"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="受理編號清單查詢" onclick="javascript:form_submit('query2');"/>
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
				nameSpace="/aps/002"
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
		<th>批次號碼</th>	
		<th>批次序號</th>
		<th>執行時間</th>
		<th>檔案名稱_ZIP</th>
		<th>檔案狀態</th>
		<th>檔案名稱_STL</th>
		<th>筆數_STL</th>
		<th>刪除註記</th>
	</tr>
	<s:iterator var="row" value="devResults">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="left"><s:property value="batchNo"/></td>
		<td align="center"><s:property value="batchSeq"/></td>
		<td align="left"><fmt:formatDate value='${dcreate}' pattern='yyyy/MM/dd HH:mm:ss'/></td>
		<td align="left"><s:property value="filenameZip"/></td>
		<td align="left">
			<c:if test="${fileStatus == 'S'}">正常</c:if>
			<c:if test="${fileStatus == 'L'}">缺檔</c:if>
			<c:if test="${fileStatus == 'E'}">ZIP檔案異常</c:if>
			<c:if test="${fileStatus == 'A'}">新增錯誤</c:if>
			<c:if test="${fileStatus == 'Z'}">檔案無資料</c:if>
		</td>
		<td align="left"><s:property value="filenameStl"/></td>
		<td align="center"><s:property value="dataqtyStl"/></td>
		<td align="center"><s:property value="deleteFlag"/></td>
	</tr>
	</s:iterator>
</table>
</s:if>

<s:if test="devResults2 != null && 0 != devResults2.size">
<table cellspacing="1" cellpadding="1" border="0" width="970px">
	<tr>
		<td>
		<div align="right">
			<custom:ddlChangePage 
			    formId="mainForm"
				name="pageInfo" 
				nameSpace="/aps/002"
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
		<th>批次號碼-序號</th>	
		<th>受理編號</th>
		<th>下單狀態</th>
		<th>受理編號狀態</th>
		<th>保單號碼</th>
		<!-- mantis：FIR0181，處理人員：BJ085，需求單編號：FIR0181 中信新件流程-排程查詢作業(含產生查詢及回饋查詢) start
		受理編號清單頁將「複保險」改成顯示「單位」-->
		<th>單位</th>
		<th>地址</th>
		<th>TIFF</th>
		<th>異常訊息</th>
		<th>提示訊息</th>
		<th>核心</th>
		<th>撤單</th>
		<th>回饋</th>
	</tr>
	<s:iterator var="row" value="devResults2">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="left">
			<s:property value="batchNo"/>-<s:property value="batchSeq"/>
		</td>
		<td align="left" width="100">
			<!-- mantis：FIR0181，處理人員：BJ085，需求單編號：FIR0181 中信新件流程-排程查詢作業(含產生查詢及回饋查詢)
				 調整角色權限控管功能，後USER決定取消此功能 --> 
			<a href='${goDetail}aps002DetailVo.batchNo=${row.batchNo}&aps002DetailVo.batchSeq=${row.batchSeq}&aps002DetailVo.fkOrderSeq=${row.fkOrderSeq}'><s:property value="fkOrderSeq"/></a>
		</td>
		<td align="center">
			<c:if test="${fkStatus == '01'}">下單完成</c:if>
			<c:if test="${fkStatus == '02'}">暫存</c:if>
			<c:if test="${fkStatus == '09'}">取消</c:if>
			<!-- mantis：OTH0078，處理人員：BJ085，需求單編號：OTH0078 更名相關-APS、CWP、SYS系統中有台壽保產險、tlg、網域名稱更為新名稱 -->
			<c:if test="${fkStatus == 'XX'}">中國信託產險檢核異常</c:if>
		</td>
		<td align="center">
			<c:if test="${orderSeqStatus == '0'}">未處理</c:if>
			<c:if test="${orderSeqStatus == '1'}">資料驗證失敗</c:if>
			<c:if test="${orderSeqStatus == '2'}">寫入中信下單檔成功</c:if>
			<c:if test="${orderSeqStatus == '3'}">轉核心暫存檔成功</c:if>
			<c:if test="${orderSeqStatus == '4'}">轉核心暫存檔失敗</c:if>
			<c:if test="${orderSeqStatus == '5'}">轉核心要保檔成功</c:if>
			<c:if test="${orderSeqStatus == '6'}">轉核心要保檔失敗</c:if>
			<c:if test="${orderSeqStatus == 'A'}">出單完成產生投保證明</c:if>
			<c:if test="${orderSeqStatus == 'B'}">產生回饋檔成功</c:if>
			<c:if test="${orderSeqStatus == 'C'}">產生回饋檔失敗</c:if>
			<c:if test="${orderSeqStatus == 'D'}">中信暫存件不處理</c:if>
			<c:if test="${orderSeqStatus == 'E'}">中信取消件不處理</c:if>
			<c:if test="${orderSeqStatus == 'F'}">未簽署件不處理</c:if>
			<c:if test="${orderSeqStatus == 'G'}">內部程式異常</c:if>
			<c:if test="${orderSeqStatus == 'H'}">產生投保證明失敗-SP</c:if>
			<c:if test="${orderSeqStatus == 'I'}">產生投保證明失敗-PDF</c:if>
		</td>
		<!-- mantis：FIR0181，處理人員：BJ085，需求單編號：FIR0181 中信新件流程-排程查詢作業(含產生查詢及回饋查詢) start
		受理編號清單頁將「複保險」改成顯示「單位」-->
		<td align="left" width="100"><s:property value="policyno"/></td>
		<td align="left">
			<s:property value="coreComcode"/>
		<!--  
			<c:if test="${dquakeStatus == 'S'}">正常</c:if>
			<c:if test="${dquakeStatus == 'D'}">複保險</c:if>
			<c:if test="${dquakeStatus == 'E'}">地震基金服務異常</c:if>
			-->
		<!-- mantis：FIR0181，處理人員：BJ085，需求單編號：FIR0181 中信新件流程-排程查詢作業(含產生查詢及回饋查詢) end-->
		</td>
		<td align="center">
			<c:if test="${addrStatus == 'S'}">通過</c:if>
			<c:if test="${addrStatus == 'F'}">不通過</c:if>
			<c:if test="${addrStatus == 'E'}">異常</c:if>
		</td>
		<td align="left"><s:property value="isTiff"/></td>
		<td align="left"><s:property value="checkErrMsg"/></td>
		<td align="left"><s:property value="checkWarnMsg"/></td>
		<td align="center">
			<c:if test="${transPpsTime != null}">Y</c:if>
			<c:if test="${transPpsTime == null}">N</c:if>
		</td>
		<td align="center">
			<c:if test="${coreCancelTime != null}">Y</c:if>
			<c:if test="${coreCancelTime == null}">N</c:if>
		</td>
		<td align="center">
			<c:if test="${bkTime != null}">Y</c:if>
			<c:if test="${bkTime == null}">N</c:if>
		</td>
	</tr>
	</s:iterator>
</table>
</s:if>

</s:form>
<!-- form結束 -->
</body>
</html>