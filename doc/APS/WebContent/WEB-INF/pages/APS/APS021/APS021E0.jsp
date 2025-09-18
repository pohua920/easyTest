<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
String path = request.getContextPath();
String title = "高風險地區維護管理";
String image = path + "/" + "images/";
String GAMID = "APS021E0";
String mDescription = "高風險地區維護管理";
String nameSpace = "/aps/021";
%>
<!-- mantis：MAR0037，處理人員：BJ085，需求單編號：MAR0037 高風險地區新增維護檔 start -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<script language="JavaScript">
	$(document).ready(function(){
		var ststus = $('input[name="filter.status"]:checked').val();
		if( ststus == undefined || ststus == "Y"){
			$('input[name="filter.status"][value="Y"]').attr('checked', true);
		}else{
			$('input[name="filter.status"][value="N"]').attr('checked', true);
		}
        // mantis：MAR0079，處理人員：DP0714，APS高風險地區新增經濟制裁註記及核心檢核 -- start
		var sanctionsStatus = $('input[name="filter.sanctionsStatus"]:checked').val();
        if (sanctionsStatus == undefined) {
        	$('input[name="filter.sanctionsStatus"][value="N"]').attr('checked', true);
        } else if(sanctionsStatus == "Y"){
			$('input[name="filter.sanctionsStatus"][value="Y"]').attr('checked', true);
		}else{
			$('input[name="filter.sanctionsStatus"][value="N"]').attr('checked', true);
		}
        // mantis：MAR0079，處理人員：DP0714，APS高風險地區新增經濟制裁註記及核心檢核 -- end
		$("#startdate").datepicker({
			changeMonth: true,  
			changeYear: true,
			dateFormat:'yyyy/mm/dd'
		});
		
		$("#enddate").datepicker({
			changeMonth: true,  
			changeYear: true,
			dateFormat:'yyyy/mm/dd'
		});
	});
			
	function form_submit(type){
		if("query" == type){
			 $("#mainForm").attr("action","btnQuery.action");
			 $("#mainForm").submit();
		}
		if("lnkGoEdit" == type){
			 $("#mainForm").attr("action","lnkGoEdit.action");
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
<s:url action="lnkGoEdit?" namespace="/aps/021" var="goEdit"/>
<s:url action="lnkGoCreate" namespace="/aps/021" var="goCreate"/>
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
<s:form theme="simple" action="btnQueryCancel" namespace="/aps/021" id="clearForm" name="clearForm"/>
<!--form 開始 -->
<s:form theme="simple" namespace="/aps/021" id="mainForm" name="mainForm">
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tbody>
			<tr bgcolor="white">
				<td class="MainTdColor" align="center" width="200px">
					<span id="lbSearch"><b>查詢作業</b></span></td>
				<td colspan="5" class="image"></td>
			</tr>
			<tr>
				<td width="200px" align="right">地區別：</td>
				<td width="285px" align="left">
					<s:textfield key="filter.countycode" id="countycode" size="10" maxlength="10" theme="simple" />
				</td>
				<td width="200px" align="right">國家簡碼：</td>
				<td width="285px" align="left">
					<s:textfield key="filter.shortcode" id="shortcode" size="10" maxlength="20" theme="simple" />
				</td>
				<!-- mantis：MAR0079，處理人員：DP0714，APS高風險地區新增經濟制裁註記及核心檢核 start -->
				<td width="200px" align="right">高風險地區：</td>
				<td width="285px" align="left">
					<s:radio key="filter.status" id="status" list="#{'Y':'是','N':'否'}" theme="simple" />
				</td>
				<!-- mantis：MAR0079，處理人員：DP0714，APS高風險地區新增經濟制裁註記及核心檢核 end -->
			</tr>
			<tr>
				<td width="200px" align="right">國家中文名稱：</td>
				<td width="285px" align="left">
					<s:textfield key="filter.countycname" id="countycname" size="20" maxlength="60" theme="simple" />
				</td>
				<td width="200px" align="right">國家英文名稱：</td>
				<td width="285px" align="left">
					<s:textfield key="filter.countyename" id="countyename" size="20" maxlength="200" theme="simple" />
				</td>	
                <!-- mantis：MAR0079，處理人員：DP0714，APS高風險地區新增經濟制裁註記及核心檢核 start -->
				<td width="200px" align="right">經濟制裁國家：</td>
				<td width="285px" align="left">
					<s:radio key="filter.sanctionsStatus" id="sanctionsStatus" list="#{'Y':'是','N':'否'}" theme="simple" />
				</td>
				<!-- mantis：MAR0079，處理人員：DP0714，APS高風險地區新增經濟制裁註記及核心檢核 end -->
			</tr>
			<tr>
				<td width="200px" align="right">啟用日期：</td>
				<td width="285px" align="left">
					<s:textfield key="filter.startdate" id="startdate" size="10" maxlength="10" theme="simple" />
				</td>
				<td width="200px" align="right">停用日期：</td>
				<td width="285px" align="left">
					<s:textfield key="filter.enddate" id="enddate" size="10" maxlength="10" theme="simple" />
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
						nameSpace="/aps/021"
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
				<th>地區別
					<c:url value="/aps/021/lnkSortQuery.action" var="sortURL">
						<c:param name="filter.sortBy" value="COUNTYCODE" />
						<c:param name="filter.sortType" value="ASC" />
					</c:url>
					<a href='<c:out value="${sortURL}" />'>▲</a>
					<c:url value="/aps/021/lnkSortQuery.action" var="sortURL">
						<c:param name="filter.sortBy" value="COUNTYCODE" />
						<c:param name="filter.sortType" value="DESC" />
					</c:url>
					<a href='<c:out value="${sortURL}" />'>▼</a>
				</th>
				<th>國家簡碼
					<c:url value="/aps/021/lnkSortQuery.action" var="sortURL">
						<c:param name="filter.sortBy" value="SHORTCODE" />
						<c:param name="filter.sortType" value="ASC" />
					</c:url>
					<a href='<c:out value="${sortURL}" />'>▲</a>
					<c:url value="/aps/021/lnkSortQuery.action" var="sortURL">
						<c:param name="filter.sortBy" value="SHORTCODE" />
						<c:param name="filter.sortType" value="DESC" />
					</c:url>
					<a href='<c:out value="${sortURL}" />'>▼</a>
				</th>
				<th>國家英文名稱
					<c:url value="/aps/021/lnkSortQuery.action" var="sortURL">
						<c:param name="filter.sortBy" value="COUNTYENAME" />
						<c:param name="filter.sortType" value="ASC" />
					</c:url>
					<a href='<c:out value="${sortURL}" />'>▲</a>
					<c:url value="/aps/021/lnkSortQuery.action" var="sortURL">
						<c:param name="filter.sortBy" value="COUNTYENAME" />
						<c:param name="filter.sortType" value="DESC" />
					</c:url>
					<a href='<c:out value="${sortURL}" />'>▼</a>
				</th>
				<th>國家中文名稱
					<c:url value="/aps/021/lnkSortQuery.action" var="sortURL">
						<c:param name="filter.sortBy" value="COUNTYCNAME" />
						<c:param name="filter.sortType" value="ASC" />
					</c:url>
					<a href='<c:out value="${sortURL}" />'>▲</a>
					<c:url value="/aps/021/lnkSortQuery.action" var="sortURL">
						<c:param name="filter.sortBy" value="COUNTYCNAME" />
						<c:param name="filter.sortType" value="DESC" />
					</c:url>
					<a href='<c:out value="${sortURL}" />'>▼</a>
				</th>
				<!-- mantis：MAR0079，處理人員：DP0714，APS高風險地區新增經濟制裁註記及核心檢核 start -->
				<th>高風險地區
					<c:url value="/aps/021/lnkSortQuery.action" var="sortURL">
						<c:param name="filter.sortBy" value="STATUS" />
						<c:param name="filter.sortType" value="ASC" />
					</c:url>
					<a href='<c:out value="${sortURL}" />'>▲</a>
					<c:url value="/aps/021/lnkSortQuery.action" var="sortURL">
						<c:param name="filter.sortBy" value="STATUS" />
						<c:param name="filter.sortType" value="DESC" />
					</c:url>
					<a href='<c:out value="${sortURL}" />'>▼</a>
				</th>
				<!-- mantis：MAR0079，處理人員：DP0714，APS高風險地區新增經濟制裁註記及核心檢核 start -->
				<th>經濟制裁國家
					<c:url value="/aps/021/lnkSortQuery.action" var="sortURL">
						<c:param name="filter.sortBy" value="SANCTIONS_STATUS" />
						<c:param name="filter.sortType" value="ASC" />
					</c:url>
					<a href='<c:out value="${sortURL}" />'>▲</a>
					<c:url value="/aps/021/lnkSortQuery.action" var="sortURL">
						<c:param name="filter.sortBy" value="SANCTIONS_STATUS" />
						<c:param name="filter.sortType" value="DESC" />
					</c:url>
					<a href='<c:out value="${sortURL}" />'>▼</a>
				</th>
                <!-- mantis：MAR0079，處理人員：DP0714，APS高風險地區新增經濟制裁註記及核心檢核 end -->
				<th>啟用日期
					<c:url value="/aps/021/lnkSortQuery.action" var="sortURL">
						<c:param name="filter.sortBy" value="STARTDATE" />
						<c:param name="filter.sortType" value="ASC" />
					</c:url>
					<a href='<c:out value="${sortURL}" />'>▲</a>
					<c:url value="/aps/021/lnkSortQuery.action" var="sortURL">
						<c:param name="filter.sortBy" value="STARTDATE" />
						<c:param name="filter.sortType" value="DESC" />
					</c:url>
					<a href='<c:out value="${sortURL}" />'>▼</a>
				</th>
				<th>停用日期
					<c:url value="/aps/021/lnkSortQuery.action" var="sortURL">
						<c:param name="filter.sortBy" value="ENDDATE" />
						<c:param name="filter.sortType" value="ASC" />
					</c:url>
					<a href='<c:out value="${sortURL}" />'>▲</a>
					<c:url value="/aps/021/lnkSortQuery.action" var="sortURL">
						<c:param name="filter.sortBy" value="ENDDATE" />
						<c:param name="filter.sortType" value="DESC" />
					</c:url>
					<a href='<c:out value="${sortURL}" />'>▼</a>
				</th>
			</tr>
			<s:iterator var="row" value="devResults">
			<tr align="center" onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
				<td>
				<a href='${goEdit}prpdhighareaMc.countycode=${row.countycode}&prpdhighareaMc.shortcode=${row.shortcode}'>修改</a>
				</td>
				<td><s:property value="countycode"/></td>
				<td><s:property value="shortcode"/></td>
				<td><s:property value="countyename"/></td>
				<td><s:property value="countycname"/></td>
				<td><s:property value="status"/></td>
				<!-- mantis：MAR0079，處理人員：DP0714，APS高風險地區新增經濟制裁註記及核心檢核 start -->
				<td><s:property value="sanctionsStatus"/></td>
				<!-- mantis：MAR0079，處理人員：DP0714，APS高風險地區新增經濟制裁註記及核心檢核 end -->
				<td><s:property value="startdate"/></td>
				<td><s:property value="enddate"/></td>
			</tr>
			</s:iterator>
		</table>
	</s:if>
</s:form>
<!-- form結束 -->
</body>
</html>