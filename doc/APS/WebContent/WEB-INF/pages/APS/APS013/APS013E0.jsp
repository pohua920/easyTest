<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "傷害險通報查詢作業";
	String image = path + "/" + "images/";
	String GAMID = "APS013E0";
	String mDescription = "傷害險通報查詢作業";
	String nameSpace = "/aps/013";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 
mantis：OTH0093，處理人員：BJ085，需求單編號：OTH0093 傷害險通報查詢、重送介面  start
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
		
		$("#sDate").datepicker({dateFormat:"yy/mm/dd"});
		$("#eDate").datepicker({dateFormat:"yy/mm/dd"});
	});

	function form_submit(type){
		if("query" == type && $("#rtncode").val() != "" &&
				($("#sDate").val() != "" || $("#eDate").val() != "")){
			if(checkInputDate()){
				$("#mainForm").attr("action","btnQuery.action");
				$("#mainForm").submit();				
			}
		}else{
			alert("請選擇傳送狀態、上傳日期!");
		}
	}
	
	function checkInputDate(){
		var sDate = $('#sDate').val();
		var eDate = $('#eDate').val();

		if((sDate == '' && eDate != '') || (sDate != '' && eDate == '')){
			alert("上傳日期起迄日須同時輸入");
			return false;
		}
		var sDateArr = sDate.split("/");
		var tsDate = (parseInt(sDateArr[0])+parseInt(1911))+'/'+sDateArr[1]+'/'+sDateArr[2];
		var eDateArr = eDate.split("/");
		var teDate =  (parseInt(eDateArr[0])+parseInt(1911))+'/'+eDateArr[1]+'/'+eDateArr[2];
		if(((new Date(teDate) - new Date(tsDate))/(1000 * 60 * 60 * 24)) < 0){
			alert("上傳日期起日須<=迄日");
			return false;
		}else if(((new Date(teDate) - new Date(tsDate))/(1000 * 60 * 60 * 24)) > 60){
			alert("上傳日期起訖日應介於60天內");
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
<s:url action="lnkGoDetail?" namespace="/aps/013" var="dtlQuery"/>
<s:url action="lnkQueryDefaultData?" namespace="/aps/013" var="addData"/>
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
<s:form theme="simple" namespace="/aps/013" id="mainForm" name="mainForm">
	<table id="table2"  cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="MainTdColor" style="width:200px" align="center">
				<span id="lbSearch"><b>查詢作業</b></span></td>
			<td colspan="3" class="image"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tr>
        	<td width="200px" align="right">通報類別：</td>
			<td width="285px" align="left">
				<s:select key="filter.case" id="case" theme="simple" 
 				list="#{'RCV':'收件通報', 'UNDWRT':'承保通報'}" />
			</td>
			<td colspan="5"></td>
		</tr>
		<tr>
        	<td width="200px" align="right">要保險人身分證字號：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.askidno" id="askidno"/>
			</td>
			<td colspan="5"></td>
		</tr>
		<tr>
        	<td width="200px" align="right">被保險人身分證字號：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.idno" id="idno"/>
			</td>
			<td colspan="5"></td>
		</tr>
		<tr>
        	<td width="200px" align="right">保單號：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.insno" id="insno"/>
			</td>
			<td colspan="5"></td>
		</tr>
		<tr>
        	<td width="200px" align="right">傳送狀態：</td>
			<td width="285px" align="left">
				<s:select key="filter.rtncode" id="rtncode" theme="simple" 
 				list="#{'':'','00':'成功', '01':'失敗'}" />
			</td>
			<td colspan="5"></td>
		</tr>
		<tr>
        	<td width="200px" align="right">上傳日期：</td>
			<td width="285px" align="left">
			<s:textfield key="filter.sDate" id="sDate"/>~<s:textfield key="filter.eDate" id="eDate"/>
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
<s:if test="devResults != null && 0 != devResults.size">
<table cellspacing="1" cellpadding="1" border="0" width="970px">
	<tr>
		<td>
		<div align="right">
			<custom:ddlChangePage 
			    formId="mainForm"
				name="pageInfo" 
				nameSpace="/aps/013"
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
		<th>
			保單號碼
			<c:url value="/aps/013/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="INSNO" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/013/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="INSNO" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			被保人險身分證字號
			<c:url value="/aps/013/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="IDNO" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/013/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="IDNO" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>被保險人姓名</th> 
		<th>
			上傳日
			<c:url value="/aps/013/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="SENDTIME" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/013/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="SENDTIME" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			批次號
			<c:url value="/aps/013/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="CHECKNO" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/013/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="CHECKNO" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th></th>
	</tr>
	<s:iterator var="row" value="devResults">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="center"><!-- 保單號碼 -->
			<s:property value="insno"/>
		</td>
		<td align="center"><!-- 被保人身分證字號 -->
			<s:property value="idno"/>
		</td>
		<td align="center"><!-- 被保人姓名 -->
			<s:property value="name"/>
		</td>
		<td align="center"><!-- 上傳日期 -->
			<fmt:formatDate value="${sendtime}" pattern="yyyy/MM/dd HH:mm:ss"/>
		</td>
		<td align="center"><!-- 批次號 -->
			<s:property value="checkno"/>
		</td>
		<td align="center">
			<a href='${dtlQuery}cwpAnnounceVo.idno=${row.idno}&cwpAnnounceVo.insno=${row.insno}
			&cwpAnnounceVo.checkno=${row.checkno}'>通報資料及結果</a><br>
			<a href='${addData}cwpAnnounceVo.idno=${row.idno}&cwpAnnounceVo.insno=${row.insno}
			&cwpAnnounceVo.checkno=${row.checkno}'>複製資料</a>
		</td>
	</tr>
	</s:iterator>
</table>
</s:if>
</s:form>
</body>
</html>