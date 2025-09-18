<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String title = "元大續保資料處理作業-明細資料";
	String image = path + "/" + "images/";
	String GAMID = "APS060E1";
	String mDescription = "元大續保資料處理作業";
	String nameSpace = "/aps/060";
%>
<!-- mantis：FIR0668_0217，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
		if("dtlQuery" == type){
			 $("#mainForm").attr("action","btnDtlQuery.action");
			 $("#mainForm").submit();
		}
		
		if("btnDownloadXls" == type){
			$("#mainForm").attr("action","btnDownloadXls.action");
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
	
	function checkDataStatus(dataStatus){
		if(dataStatus === '0' || dataStatus === '1'){
			alert("轉檔異常，無法進入要保資料頁。");
			return false;
		}
		return true;
	}
</script>
</head>

<s:url action="lnkGoUpdate?" namespace="/aps/060" var="goUpdate"/>
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
<s:form theme="simple" namespace="/aps/060" id="mainForm" name="mainForm">
	<table id="table2" cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="MainTdColor" style="width: 200px" align="center">
				<span id="lbSearch"><b>明細查詢作業</b></span>
			</td>
			<!--mantis：FIR0676，處理人員：DP0706，需求單編號：FIR0676_住火_元大續保作業_N+1比對擔保品檔案-->
			<td class="SelectTdColor" style="width: 200px" align="center">
				<span id="lbSearch"><a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoN1Upload.action?batchNo=<s:property value="batchNo"/>&rnYyyymm=<s:property value="rnYyyymm"/>"><b>N+1轉檔作業</b></a></span>
			</td>
			<td class="imageGray" style="width:20px" colspan="3"></td>
			</tr>
			<!--mantis：FIR0676，處理人員：DP0706，需求單編號：FIR0676_住火_元大續保作業_N+1比對擔保品檔案END-->
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tr>
			<td width="200px" align="right">批次號碼：</td>
			<td width="285px" align="left">
				<s:property value="batchNo"/>
				<s:hidden name="batchNo" id="batchNo"/>
			</td>
			<td width="200px" align="right">續保單號：</td>
			<td width="285px" align="left">
				<s:textfield key="dFilter.oldpolicyno" id="oldpolicyno" />
			</td>
		</tr>
<!-- 		<tr> -->
<!-- 			<td width="200px" align="right">歸屬單位：</td> -->
<!-- 			<td width="285px" align="left"> -->
<%-- 				<s:textfield key="dFilter.comcode" id="comcode" /> --%>
<!-- 			</td> -->
<!-- 			<td width="200px" align="right">服務人員代號：</td> -->
<!-- 			<td width="285px" align="left"> -->
<%-- 				<s:textfield key="dFilter.handler1code" id="handler1code" /> --%>
<!-- 			</td> -->
<!-- 		</tr> -->
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tr>
			<td width="145px" align="right">說明：</td>
			<td width="550px" align="left">
			<span>1.點選「序號」可進入對應資料明細。</span><br>
			<span>2.N+1鎖定、非剔退資料才會轉入核心。</span><br>
		</tr>
	</table>
	<table width="970px" cellpadding="0" cellspacing="0">
		<tr>
			<td align="center">
				<input type="button" value="查詢" onclick="javascript:form_submit('dtlQuery');" />
				<input type="button" value="下載xls" onclick="javascript:form_submit('btnDownloadXls');" />
				<a href="${pageContext.request.contextPath}<%=nameSpace%>/btnQuery.action?goBack=Y"><input type="button" value="回上頁"/></a>
			</td>
		</tr>
	</table>
	
	<s:if test="dtlDevResults != null && 0 != dtlDevResults.size">
		<table cellspacing="1" cellpadding="1" border="0" width="970px">
			<tr>
				<td>
				<div align="right">
					<custom:ddlChangePage 
					    formId="mainForm"
						name="dPageInfo" 
						nameSpace="/aps/060"
						currentPage="${dPageInfo.currentPage}" 
						pageSize="${dPageInfo.pageSize}"   
						selectOnChange="ddlDtlPageSizeChanged" 
						textOnChange="txtDtlChangePageIndex"  
						rowCount="${dPageInfo.rowCount}"
						pageCount="${dPageInfo.pageCount}"/>
				</div>
				</td>
			</tr>
		</table>
		<!--Grid Table-->
		<table border="1" id="gridtable" width="970px" border="0" class="main_table">
			<tr align="center">
				<th width="30px">序號</th>
				<th width="90px">
					資料狀態
					<c:url value="/aps/060/lnkSortDtlQuery.action" var="sortURL">
						<c:param name="dPageInfo.filter.sortBy" value="DATA_STATUS" />
						<c:param name="dPageInfo.filter.sortType" value="ASC" />
					</c:url>
					<a href='<c:out value="${sortURL}" />'>▲</a>
					<c:url value="/aps/060/lnkSortDtlQuery.action" var="sortURL">
						<c:param name="dPageInfo.filter.sortBy" value="DATA_STATUS" />
						<c:param name="dPageInfo.filter.sortType" value="DESC" />
					</c:url>
					<a href='<c:out value="${sortURL}" />'>▼</a>
				</th>	
				<th width="100px">
					續保單號
					<c:url value="/aps/060/lnkSortDtlQuery.action" var="sortURL">
						<c:param name="dPageInfo.filter.sortBy" value="OLDPOLICYNO" />
						<c:param name="dPageInfo.filter.sortType" value="ASC" />
					</c:url>
					<a href='<c:out value="${sortURL}" />'>▲</a>
					<c:url value="/aps/060/lnkSortDtlQuery.action" var="sortURL">
						<c:param name="dPageInfo.filter.sortBy" value="OLDPOLICYNO" />
						<c:param name="dPageInfo.filter.sortType" value="DESC" />
					</c:url>
					<a href='<c:out value="${sortURL}" />'>▼</a>
				</th>
<!-- 				<th width="30px"> -->
<!-- 					單位 -->
<%-- 					<c:url value="/aps/060/lnkSortDtlQuery.action" var="sortURL"> --%>
<%-- 						<c:param name="dPageInfo.filter.sortBy" value="COMCODE" /> --%>
<%-- 						<c:param name="dPageInfo.filter.sortType" value="ASC" /> --%>
<%-- 					</c:url> --%>
<%-- 					<a href='<c:out value="${sortURL}" />'>▲</a> --%>
<%-- 					<c:url value="/aps/060/lnkSortDtlQuery.action" var="sortURL"> --%>
<%-- 						<c:param name="dPageInfo.filter.sortBy" value="COMCODE" /> --%>
<%-- 						<c:param name="dPageInfo.filter.sortType" value="DESC" /> --%>
<%-- 					</c:url> --%>
<%-- 					<a href='<c:out value="${sortURL}" />'>▼</a> --%>
<!-- 				</th> -->
<!-- 				<th width="30px"> -->
<!-- 					服務<br>人員<br> -->
<%-- 					<c:url value="/aps/060/lnkSortDtlQuery.action" var="sortURL"> --%>
<%-- 						<c:param name="dPageInfo.filter.sortBy" value="HANDLER1CODE" /> --%>
<%-- 						<c:param name="dPageInfo.filter.sortType" value="ASC" /> --%>
<%-- 					</c:url> --%>
<%-- 					<a href='<c:out value="${sortURL}" />'>▲</a> --%>
<%-- 					<c:url value="/aps/060/lnkSortDtlQuery.action" var="sortURL"> --%>
<%-- 						<c:param name="dPageInfo.filter.sortBy" value="HANDLER1CODE" /> --%>
<%-- 						<c:param name="dPageInfo.filter.sortType" value="DESC" /> --%>
<%-- 					</c:url> --%>
<%-- 					<a href='<c:out value="${sortURL}" />'>▼</a> --%>
<!-- 				</th> -->
				<th width="30px">
					N+1鎖定
					<c:url value="/aps/060/lnkSortDtlQuery.action" var="sortURL">
						<c:param name="dPageInfo.filter.sortBy" value="FIX_USER" />
						<c:param name="dPageInfo.filter.sortType" value="ASC" />
					</c:url>
					<a href='<c:out value="${sortURL}" />'>▲</a>
					<c:url value="/aps/060/lnkSortDtlQuery.action" var="sortURL">
						<c:param name="dPageInfo.filter.sortBy" value="FIX_USER" />
						<c:param name="dPageInfo.filter.sortType" value="DESC" />
					</c:url>
					<a href='<c:out value="${sortURL}" />'>▼</a>
				</th>
				<th width="30px">
					剔退
					<c:url value="/aps/060/lnkSortDtlQuery.action" var="sortURL">
						<c:param name="dPageInfo.filter.sortBy" value="SF_FLAG" />
						<c:param name="dPageInfo.filter.sortType" value="ASC" />
					</c:url>
					<a href='<c:out value="${sortURL}" />'>▲</a>
					<c:url value="/aps/060/lnkSortDtlQuery.action" var="sortURL">
						<c:param name="dPageInfo.filter.sortBy" value="SF_FLAG" />
						<c:param name="dPageInfo.filter.sortType" value="DESC" />
					</c:url>
					<a href='<c:out value="${sortURL}" />'>▼</a>
				</th>
				<th width="50px">
					轉核心
					<c:url value="/aps/060/lnkSortDtlQuery.action" var="sortURL">
						<c:param name="dPageInfo.filter.sortBy" value="PRPINS_BATCH_STATUS" />
						<c:param name="dPageInfo.filter.sortType" value="ASC" />
					</c:url>
					<a href='<c:out value="${sortURL}" />'>▲</a>
					<c:url value="/aps/060/lnkSortDtlQuery.action" var="sortURL">
						<c:param name="dPageInfo.filter.sortBy" value="PRPINS_BATCH_STATUS" />
						<c:param name="dPageInfo.filter.sortType" value="DESC" />
					</c:url>
					<a href='<c:out value="${sortURL}" />'>▼</a>
				</th>
				<th width="50px">
					續保<br>約定<br>
					<c:url value="/aps/060/lnkSortDtlQuery.action" var="sortURL">
						<c:param name="dPageInfo.filter.sortBy" value="IS_AUTO_RENEW" />
						<c:param name="dPageInfo.filter.sortType" value="ASC" />
					</c:url>
					<a href='<c:out value="${sortURL}" />'>▲</a>
					<c:url value="/aps/060/lnkSortDtlQuery.action" var="sortURL">
						<c:param name="dPageInfo.filter.sortBy" value="IS_AUTO_RENEW" />
						<c:param name="dPageInfo.filter.sortType" value="DESC" />
					</c:url>
					<a href='<c:out value="${sortURL}" />'>▼</a>
				</th>
				<th width="50px">
					要保人
					<c:url value="/aps/060/lnkSortDtlQuery.action" var="sortURL">
						<c:param name="dPageInfo.filter.sortBy" value="NAME_A" />
						<c:param name="dPageInfo.filter.sortType" value="ASC" />
					</c:url>
					<a href='<c:out value="${sortURL}" />'>▲</a>
					<c:url value="/aps/060/lnkSortDtlQuery.action" var="sortURL">
						<c:param name="dPageInfo.filter.sortBy" value="NAME_A" />
						<c:param name="dPageInfo.filter.sortType" value="DESC" />
					</c:url>
					<a href='<c:out value="${sortURL}" />'>▼</a>
				</th>
				<th width="50px">
					被保人
					<c:url value="/aps/060/lnkSortDtlQuery.action" var="sortURL">
						<c:param name="dPageInfo.filter.sortBy" value="NAME_I" />
						<c:param name="dPageInfo.filter.sortType" value="ASC" />
					</c:url>
					<a href='<c:out value="${sortURL}" />'>▲</a>
					<c:url value="/aps/060/lnkSortDtlQuery.action" var="sortURL">
						<c:param name="dPageInfo.filter.sortBy" value="NAME_I" />
						<c:param name="dPageInfo.filter.sortType" value="DESC" />
					</c:url>
					<a href='<c:out value="${sortURL}" />'>▼</a>
				</th>
<!-- 				<th width="50px"> -->
<!-- 					已修改<br> -->
<%-- 					<c:url value="/aps/060/lnkSortDtlQuery.action" var="sortURL"> --%>
<%-- 						<c:param name="dPageInfo.filter.sortBy" value="ISUPDATE" /> --%>
<%-- 						<c:param name="dPageInfo.filter.sortType" value="ASC" /> --%>
<%-- 					</c:url> --%>
<%-- 					<a href='<c:out value="${sortURL}" />'>▲</a> --%>
<%-- 					<c:url value="/aps/060/lnkSortDtlQuery.action" var="sortURL"> --%>
<%-- 						<c:param name="dPageInfo.filter.sortBy" value="ISUPDATE" /> --%>
<%-- 						<c:param name="dPageInfo.filter.sortType" value="DESC" /> --%>
<%-- 					</c:url> --%>
<%-- 					<a href='<c:out value="${sortURL}" />'>▼</a> --%>
<!-- 				</th> -->
				<th width="250px">異常訊息</th>
				<th>提醒訊息</th>
			</tr>
			<s:iterator var="row" value="dtlDevResults">
			<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
				<td align="center">
					<a href="${goUpdate}batchNo=${row.batchNo}&batchSeq=${row.batchSeq}"
						onclick="return checkDataStatus('${row.dataStatus}');"><s:property value="batchSeq"/></a>
				</td>
				<td align="center">
					<c:choose>
						<c:when test="${dataStatus == '0'}">未處理</c:when>
						<c:when test="${dataStatus == '1'}">APS暫存失敗</c:when>
						<c:when test="${dataStatus == '2'}">APS暫存成功</c:when>
						<c:when test="${dataStatus == '3'}">轉核心暫存成功</c:when>
						<c:when test="${dataStatus == '4'}">轉核心暫存失敗</c:when>
						<c:when test="${dataStatus == '5'}">轉核心要保成功</c:when>
						<c:when test="${dataStatus == '6'}">轉核心要保失敗</c:when>
						<c:when test="${dataStatus == '7'}">轉核心完成</c:when>
						<c:when test="${dataStatus == '8'}">轉核心失敗-寫入收付失敗</c:when>
						<c:when test="${dataStatus == 'A'}">人工判定不轉核心</c:when>
						<c:otherwise>未定義</c:otherwise>
					</c:choose>
				</td>
				<td align="center" width="100px"><s:property value="oldpolicyno"/></td><!-- 長度不要折行 -->
<%-- 				<td align="center"><s:property value="comcode"/></td> --%>
<%-- 				<td align="center" width="50px"><s:property value="handler1code"/></td> --%>
				<!-- mantis：FIR0668，處理人員：BJ085，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 -->
				<td align="center"><c:if test="${fixUser != null}">Y</c:if>
				<td align="center"><s:property value="sfFlag"/></td>
				<td align="center">
					<c:if test="${prpinsBatchStatus == '0'}">未處理</c:if>
					<c:if test="${prpinsBatchStatus == '1'}">執行中</c:if>
					<c:if test="${prpinsBatchStatus == '2'}">完成</c:if>
				</td>
				<td align="center"><s:property value="isAutoRenew"/></td>
				<td align="center"><s:property value="nameA"/></td>
				<td align="center"><s:property value="nameI"/></td>
<%-- 				<td align="center"><s:property value="isupdate"/></td> --%>
				<td align="center"><s:property value="checkErrMsg"/></td>
				<td align="center"><s:property value="checkWarnMsg"/></td>
			</tr>
			</s:iterator>
		</table>
		<br/>
		<table width="970px" cellpadding="0" cellspacing="0" >
			<tr>
				<td align="center">
					<a href="${pageContext.request.contextPath}<%=nameSpace%>/btnQuery.action?goBack=Y"><input type="button" value="回上頁"/></a>
				</td>
			</tr>
		</table>
	</s:if>
</s:form>
</body>
</html>