<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
String path = request.getContextPath();
String title = "難字案件維護";
String image = path + "/" + "images/";
String GAMID = "APS015E0";
String mDescription = "難字案件維護";
String nameSpace = "/aps/015";
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

	});
			
	function form_submit(type){
		 //$("label").html('');
		 //mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理
		if("query" == type && checkQueryInput()){
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
	}
	
	function redirect(oid, o180filename){
		$("#oid").val(oid);
		$("#o180filename").val(o180filename);
		form_submit("lnkGoEditO180Fix");
	}
	
	function cancelBatch(oid){
		$("#oid").val(oid);
		form_submit("btnCancelBatch");
	}
	
	function showMsg(msg){
		alert(msg);
	}
	
	var msg = "<%=request.getAttribute("message")%>";
	if('' != msg && 'null' != msg){
		showMsg(msg);
	}
	
	/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 start*/
	function checkQueryInput(){
		if($("#id").val().length>0 && $("#policyno").val().length>0){
			alert("請只輸入單一查詢條件。");
			return false;
		}
		return true;
	}
	/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 end*/
	
</script>
</head>
<s:url action="lnkGoEdit?" namespace="/aps/015" var="goEdit"/>
<s:url action="lnkGoCreate" namespace="/aps/015" var="goCreate"/>
<s:url action="lnkDelete?" namespace="/aps/015" var="lnkDelete"/>
<body style="margin:0;text-align:left">
<table cellspacing="1" cellpadding="1" width="970px" border="0">
	<tbody>
		   <tr>
			  <td width="485px">			      
				  <img src="${pageContext.request.contextPath}/images/Help_Icon.gif" border="0">
				  <a href='${goCreate}'><img src="${pageContext.request.contextPath}/images/New_Icon.gif" border="0"></a>
			  </td>
			  <td align="right" width="485px">PGMID：<%=GAMID%></td>
		   </tr>
		   <tr>
			   <td colspan="2" align="center" width="970px"><h3><%=title%></h3></td>
		   </tr>
	</tbody>		
</table>

<!-- clear form -->
<s:form theme="simple" action="btnQueryCancel" namespace="/aps/015" id="clearForm" name="clearForm"/>
<!--form 開始 -->
<s:form theme="simple" namespace="/aps/015" id="mainForm" name="mainForm">
<!--mantis：FIR0439，處理人員：BJ085，需求單編號：FIR0439 住火-APS中信代理投保難字匯入 start -->
<table id="table2" cellspacing="0" cellpadding="0" width="970px">
		<tr bgcolor="white">
			<td class="MainTdColor" align="center" width="200px">
				<span id="lbSearch"><b>查詢作業</b></span></td>
			<td class="image" style="width:20px"></td>	
			<td class="SelectTdColor" style="width:200px" align="center">
				<span id="lbSearch"><a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoImport.action"><b>匯入作業</b></a></span></td>
			<td colspan="3" class="imageGray"></td>
		</tr>
</table>
<!--mantis：FIR0439，處理人員：BJ085，需求單編號：FIR0439 住火-APS中信代理投保難字匯入 end -->
<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
	<tbody>
		<tr>
			<td width="200px" align="right">身份證字號：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.id" id="id"  theme="simple" />
			</td>
			<td width="200px" align="right">保單號碼：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.policyno" id="policyno"  theme="simple" />
			</td>			
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
				nameSpace="/aps/015"
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
		<th>資料型態</th>	
		<th>資料內容</th>
		<th>異動時間</th>
		<th>異動人員</th>
		<th></th>
	</tr>
	<s:iterator var="row" value="devResults">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="left">
			<c:if test="${datatype == '1'}">身份證字號</c:if>
			<c:if test="${datatype == '2'}">保單號碼</c:if>
		</td>
		<td align="left"><s:property value="ownerid"/><c:if test="${datatype == '1'}">/</c:if><s:property value="thename"/></td>
		<!-- mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理_查詢資料顯示時間 start-->
		<td align="left"><fmt:formatDate value='${dupdate}' pattern='yyyy/MM/dd HH:mm:ss'/></td>
		<td align="left"><s:property value="iupdate"/></td>
		<!-- mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理_查詢資料顯示時間 end-->
		<td align="center">
			<a href='${goEdit}firCtbcRewNoshowword.oid=${row.oid}'>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href='${lnkDelete}firCtbcRewNoshowword.oid=${row.oid}'>刪除</a>
		</td>
	</tr>
	</s:iterator>
</table>
</s:if>
</s:form>
<!-- form結束 -->
</body>
</html>