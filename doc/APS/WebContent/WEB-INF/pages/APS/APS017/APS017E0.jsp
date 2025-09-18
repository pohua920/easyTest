<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
String path = request.getContextPath();
String title = "住火保經代分行服務人員對照表";
String image = path + "/" + "images/";
String GAMID = "APS017E0";
String mDescription = "住火保經代分行服務人員對照表";
String nameSpace = "/aps/017";
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
		if("query" == type){
			 $("#mainForm").attr("action","btnQuery.action");
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
<s:url action="lnkGoEdit?" namespace="/aps/017" var="goEdit"/>
<s:url action="lnkGoCreate" namespace="/aps/017" var="goCreate"/>
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
<s:form theme="simple" action="btnQueryCancel" namespace="/aps/017" id="clearForm" name="clearForm"/>
<!--form 開始 -->
<s:form theme="simple" namespace="/aps/017" id="mainForm" name="mainForm">
<s:hidden name="token" id="token"/>
<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
	<tbody>
		<tr bgcolor="white">
			<td class="MainTdColor" align="center" width="200px">
				<span id="lbSearch"><b>查詢作業</b></span></td>
			<td colspan="5" class="image"></td>
		</tr>
		<tr>
			<td width="200px" align="right">業務來源：</td>
			<td width="285px" align="left">
				<s:select key="filter.businessnature" id="businessnature" list="businessnatureMap"/>
			</td>
			<td width="200px" align="right"></td>
			<td width="285px" align="left"></td>			
		</tr>
		<tr>
			<td width="200px" align="right">分行代號：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.branchNo" id="branchNo"  theme="simple" />
			</td>
			<td width="200px" align="right">分行名稱：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.branchNameLike" id="branchNameLike"  theme="simple" />
			</td>			
		</tr>
		<tr>
			<td width="200px" align="right">服務人員：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.handler1code" id="handler1code"  theme="simple" />
			</td>
			<td width="200px" align="right">服務人員名稱：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.handler1nameLike" id="handler1nameLike"  theme="simple" />
			</td>			
		</tr>
		<tr>
			<td width="200px" align="right">歸屬單位：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.comcode" id="comcode"  theme="simple" />
			</td>
			<td width="200px" align="right">刪除註記：</td>
			<td width="285px" align="left">
				<s:select key="filter.deleteFlag" id="deleteFlag" list="#{'*':'全選','Y':'是','N':'否'}"/>
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
				nameSpace="/aps/017"
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
		<th></th>	
		<th>分行代號</th>
		<th>分行名稱</th>
		<th>服務人員</th>
		<th>服務人員名稱</th>
		<th>歸屬單位</th>
		<th>刪除註記</th>
	</tr>
	<s:iterator var="row" value="devResults">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="left"><a href='${goEdit}firAgtSalesMapping.oid=${row.oid}'>修改</a></td>
		<td align="left"><s:property value="branchNo"/></td>
		<td align="left"><s:property value="branchName"/></td>
		<td align="left"><s:property value="handler1code"/></td>
		<td align="left"><s:property value="handler1name"/></td>
		<td align="left"><s:property value="comcode"/></td>
		<td align="left">
			<c:if test="${deleteFlag == 'Y'}">是</c:if>
			<c:if test="${deleteFlag == 'N'}">否</c:if>
		</td>
	</tr>
	</s:iterator>
</table>
</s:if>
</s:form>
<!-- form結束 -->
</body>
</html>