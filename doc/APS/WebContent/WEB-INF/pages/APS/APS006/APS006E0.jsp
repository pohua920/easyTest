<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "AML洗錢查詢作業";
	String image = path + "/" + "images/";
	String GAMID = "APS006E0";
	String mDescription = "AML洗錢查詢作業";
	String nameSpace = "/aps/006";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 
mantis：OTH0065，處理人員：BJ085，需求單編號：OTH0065 建置AML洗錢查詢畫面 start 
-->
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

<s:url action="lnkDtlGoQuery?amlQueryObjMain.oid=" namespace="/aps/006" var="dtlQuery"/>
<s:url action="lnkResultMainGoQuery?amlQueryObjMain.oid=" namespace="/aps/006" var="resultQuery"/>
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
<s:form theme="simple" namespace="/aps/006" id="mainForm" name="mainForm">
	<table id="table2"  cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="MainTdColor" style="width:200px" align="center">
				<span id="lbSearch"><b>查詢作業</b></span></td>
			<td colspan="3" class="image"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tr>
        	<td width="200px" align="right">業務號(報價單號、保單號、批單號)：</td>
			<td width="285px" align="left">
			<s:textfield key="filter.businessNo" id="classCode"/>
			</td>
			<td colspan="5"></td>
		</tr>
		<tr>
        	<td width="200px" align="right">險別：</td>
			<td width="285px" align="left">
			<s:select key="filter.classCode" id="classCode" theme="simple" 
			list="#{'All':'全部 ', 'A':'A-任意車險', 'B':'B-強制車險', 'C':'C-責任險', 'C1':'C1-傷害暨健康險','E ':'E-工程險', 'F':'F-火險', 'M':'M-水險','AB':'AB-任意+強制車險'}" />
			</td>
			<td colspan="5"></td>
		</tr>
		<tr>
        	<td width="200px" align="right">險種代碼：</td>
			<td width="285px" align="left">
			<s:textfield key="filter.riskCode" id="riskCode"/>
			</td>
			<td colspan="5"></td>
		</tr>

  	</table>
  	<table width="970px" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="center">
			<input type="button" value="查詢" onclick="javascript:form_submit('query');"/>
		</td>
	</tr>
</table>
<s:if test="queryMainDevResults != null && 0 != queryMainDevResults.size">
<table cellspacing="1" cellpadding="1" border="0" width="970px">
	<tr>
		<td>
		<div align="right">
			<custom:ddlChangePage 
			    formId="mainForm"
				name="pageInfo" 
				nameSpace="/aps/006"
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
		<th>險別</th> 
		<th>險種<br/>代碼</th>
		<th>業務號</th>
		<th>強+任<br/>(強制險要保號)</th>
		<th>系統代號</th>
		<th>登入<br/>ID</th>
		<th>登入<br/>姓名</th>
		<th>作業<br/>類型</th>
		<th>公司<br/>代號</th>
		<th>業務<br/>來源</th>
		<th>商品<br/>風險等級</th>
		<th>保費</th>
		<th>建立時間</th>
		<th></th>
	</tr>
	<s:iterator var="row" value="queryMainDevResults">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="center"><!-- 險別 -->
			<s:property value="classCode"/>
			<c:if test="${classCode == 'A'}">任意車險</c:if>
			<c:if test="${classCode == 'B'}">強制車險</c:if>
			<c:if test="${classCode == 'C'}">責任險</c:if>
			<c:if test="${classCode == 'C1'}">傷害暨健康險</c:if>
			<c:if test="${classCode == 'E'}">工程險</c:if>
			<c:if test="${classCode == 'F'}">火險</c:if>			
			<c:if test="${classCode == 'M'}">水險</c:if>			
			<c:if test="${classCode == 'AB'}">任意+強制車險</c:if>			
		</td>
		<td align="center"><!-- 險種代碼 -->
			<s:property value="riskCode"/>
		</td>
		<td align="center"><!-- 業務號 -->
			<s:property value="businessNo"/>
		</td>
		<td align="center"><!-- 強+任(強制險要保號) -->
			<s:property value="extraBusinessNo"/>
		</td>
		<td align="center"><!-- 系統代號 -->
			<s:property value="appCode"/>
		</td>
		<td align="center"><!-- 登入ID -->
			<s:property value="userId"/>
		</td>
		<td align="center"><!-- 登入姓名 -->
			<s:property value="userName"/>
		</td>
		<td align="center"><!-- 作業類型 -->
			<c:if test="${type == 'Q'}">報價</c:if>
			<c:if test="${type == 'T'}">要保</c:if>
			<c:if test="${type == 'E'}">批改</c:if>
			<c:if test="${type == 'C'}">理賠</c:if>
		</td>
		<td align="center"><!-- 公司代號 -->
			<s:property value="comCode"/>
		</td>
		<td align="center"><!-- 業務來源 -->
			<c:if test="${channelType == '10'}">業務員</c:if>
			<c:if test="${channelType == '20'}">保險經紀人</c:if>
			<c:if test="${channelType == '30'}">保險代理人</c:if>
			<c:if test="${channelType == '40'}">直接業務</c:if>			
		</td>
		<td align="center"><!-- 商品風險等級 -->
			<c:if test="${comLevel == 'H'}">高風險</c:if>
			<c:if test="${comLevel == 'M'}">中風險</c:if>
			<c:if test="${comLevel == 'L'}">低風險</c:if>		
		</td>
		<td align="center"><!-- 保費 -->
			<s:property value="prem"/>
		</td>
		<td align="center"><!-- 建立時間 -->
			<fmt:formatDate value='${createtime}' pattern='yyyy/MM/dd HH:mm:ss'/>
		</td>
		<td align="center">
			<a href='${dtlQuery}${row.oid}'>掃描對象明細</a>
		</td>
	</tr>
	</s:iterator>
</table>
</s:if>
</s:form>

</body>
</html>