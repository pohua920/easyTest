<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
String path = request.getContextPath();
String title = "中信代理投保通知處理";
String image = path + "/" + "images/";
String GAMID = "APS012E1";
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
			 $("#mainForm").attr("action","btnQueryFix180.action");
			 $("#mainForm").submit();
		}
		if("lnkGoEditO180FixPage" == type){
			 $("#mainForm").attr("action","lnkGoEditO180FixPage.action");
			 $("#mainForm").submit();
		}
	}
	
	function update(oid, o180filename, linenum){
		$("#detailOid").val(oid);
		$("#detailO180filename").val(o180filename);
		$("#detailLinenum").val(linenum);
		form_submit("lnkGoEditO180FixPage");
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
<s:url action="default" namespace="/aps/012" var="goQuery"/>
<body style="margin:0;text-align:left">
<table cellspacing="1" cellpadding="1" width="970px" border="0">
	<tbody>
		   <tr>
			  <td width="485px">			      
				  <img src="${pageContext.request.contextPath}/images/Help_Icon.gif" border="0">
				  <a href='${goQuery}'><img src="${pageContext.request.contextPath}/images/Search_Icon.gif" border="0"></a>
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
<s:hidden name="filter.batchOid" id="batchOid"/>
<s:hidden name="filter.o180filename" id="o180filename"/>
<s:hidden name="aps012DetailVo.oid" id="detailOid"/>
<s:hidden name="aps012DetailVo.o180filename" id="detailO180filename"/>
<s:hidden name="aps012DetailVo.linenum" id="detailLinenum"/>
<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
	<tbody>
		<tr bgcolor="white">
			<td class="MainTdColor" align="center" width="200px">
				<span id="lbSearch"><b>查詢作業</b></span></td>
			<td colspan="5" class="image"></td>
		</tr>
		<tr>
			<td width="200px" align="right">檔案名稱：</td>
			<td width="285px" align="left">
				<s:property value="filter.o180filename" />
			</td>
			<td width="200px" align="right">批次號碼：</td>
			<td width="285px" align="left">
				<s:property value="filter.batchOid" />
			</td>			
		</tr>
		<tr>
			<td width="200px" align="right">保單號碼：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.policynumber" id="policynumber" theme="simple" />
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
<s:if test="devFix180Results != null && 0 != devFix180Results.size">
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
				selectOnChange="ddlPageSizeChangedFix180" 
				textOnChange="txtChangePageIndexFix180"  
				rowCount="${pageInfo.rowCount}"
				pageCount="${pageInfo.pageCount}"/>
		</div>
		</td>
	</tr>
</table>
<!--Grid Table-->
<table border="1" id="gridtable" width="970px" border="0" class="main_table">
	<tr align="center">
		<th>保單號碼</th>	
		<th>被保險人ID</th>
		<th>要保人ID</th>
		<th>火險總保額</th>
		<th>地震基本保額</th>
		<th>標的物地址</th>
		<th>異動</th>
		<th>異動時間</th>
	</tr>
	<s:iterator var="row" value="devFix180Results">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="left"><s:property value="policynumber"/></td>
		<td align="left"><s:property value="holderid"/></td>
		<td align="left"><s:property value="identifynumber"/></td>
		<td align="center"><s:property value="amount"/></td>
		<td align="center"><s:property value="amount1"/></td>
		<td align="left"><s:property value="address"/></td>
		<td align="center">
				<input type="button" value="異動" onclick="javascript:update('${row.batchOid}','${row.o180filename}','${row.linenum}');"/>
		</td>
		<td align="left"><s:property value="dupdate"/></td>
	</tr>
	</s:iterator>
</table>
</s:if>
</s:form>
<!-- form結束 -->
</body>
</html>