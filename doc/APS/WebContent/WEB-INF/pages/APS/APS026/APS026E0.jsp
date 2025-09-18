<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "要被保人通訊資料比對確認作業";
	String image = path + "/" + "images/";
	String GAMID = "APS026E0";
	String mDescription = "要被保人通訊資料比對確認作業";
	String nameSpace = "/aps/026";
%>
<!-- mantis：OTH0106，處理人員：BJ085，需求單編號：OTH0106 要被保人通訊資料比對確認作業 -->
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
		
		$("#mainForm").validate({
			isShowLabel:false,
			isAlertLocalMsg:false,
			rules: {
				"filter.com":{
				"required":true
				},
				"filter.sDate":{
				"required":true
				},
				"filter.eDate":{
				"required":true
				}
			},
			messages: {
				"filter.com":{
				"required":"請輸入出單單位代號"
				},
				"filter.sDate":{
				"required":"請輸入起日"
				},
				"filter.eDate":{
				"required":"請輸入迄日"
				}
			}
		});
		
		$('#sDate').datepicker({dateFormat:"yyyy/mm/dd"});
		$('#eDate').datepicker({dateFormat:"yyyy/mm/dd"});
		setDefaultDate();
		
	});

	function setDefaultDate(){
		if($("#gridtable").length<1 && $("#sDate").val()=="" && $("#eDate").val()==""){
			var date = new Date();
			var eDate = formatDate(date);
			date.setMonth(date.getMonth() - 1);
			var sDate = formatDate(date);
	 		$("#sDate").val(sDate);
			$("#eDate").val(eDate);
		}
	}
	
	function formatDate(date){
		var month = String((date.getMonth()+1));
		if(month.length == 1){
			month = 0 + month;
		}
		return date.getFullYear() + "/"+ month + "/" + date.getDate();
	}
	
	function checkInputData(){
		var sDate = $('#sDate').val();
		var eDate = $('#eDate').val();
		var sDateArr = sDate.split("/");
		var tsDate = (parseInt(sDateArr[0]))+'/'+sDateArr[1]+'/'+sDateArr[2];
		var eDateArr = eDate.split("/");
		var teDate =  (parseInt(eDateArr[0]))+'/'+eDateArr[1]+'/'+eDateArr[2];
		if(((new Date(teDate) - new Date(tsDate))/(1000 * 60 * 60 * 24)) < 0){
			alert("報價日/要保日起日須<=迄日");
			return false;
		}else if(((new Date(teDate) - new Date(tsDate))/(1000 * 60 * 60 * 24)) > 365){
			alert("報價日/要保日區間不得大於1年");
			return false;
		}
		return true;
	}
	
	function form_submit(type){
		if("query" == type && checkInputData()){
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
<!--mantis：OTH0184，處理人員：DP0706，需求單編號：APS要被保人通訊地址比對作業增加資料來源(XCHG)-->
<!-- 整頁修改-->
<s:url action="lnkGoUpdate?source=prpins&prpcinsuredContactChk0.no=" namespace="/aps/026" var="goUpdate"/>
<s:url action="lnkGoUpdate?source=xchg&prpcinsuredContactChk0New.no=" namespace="/aps/026" var="goUpdate2"/>
<body style="margin:0;text-align:left">
<table cellspacing="1" cellpadding="1" width="970px" border="0">
	<tr>
		<td width="485px">
		<img src="${pageContext.request.contextPath}/images/Help_Icon.gif" border="0">
		</td>
		<td align="right" width="485px">PGMID：<%=GAMID%></td>
	</tr>
	<tr>
		<td align="center" colSpan="2"><h3><%=title%></h3></td>
	</tr>
</table>
<!-- form 開始 -->
<s:form theme="simple" namespace="/aps/026" id="mainForm" name="mainForm">
	<table id="table2" cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="MainTdColor" style="width:200px" align="center">
				<span id="lbSearch"><b>查詢作業</b></span></td>
			<td colspan="3" class="image"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tr>
			<td width="200px" align="right"><font color="#FF0000">*</font>出單單位代號：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.com" id="com" />
			</td> 
			<td width="200px" align="right">業務號：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.businessno" id="businessno" />
			</td> 
		</tr>
		
		<tr>
			<td width="200px" align="right">系統別：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.appcode" id="appcode" />
			</td> 
			<td width="200px" align="right">關係人ID：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.identifynumber" id="identifynumber" />
			</td> 
		</tr>
		
		<tr>
			<td width="200px" align="right">關係人姓名：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.insuredname" id="insuredname" />
			</td> 
		</tr>
		<tr>
			<td width="200px" align="right"><font color="#FF0000">*</font>報價/要保日：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.sDate" id="sDate"/>~<s:textfield key="filter.eDate" id="eDate"/>
			</td> 
			<td width="200px" align="right">是否通過：</td>
			<td width="285px" align="left">
				<s:select key="filter.isPass" id="isPass" list="#{'N':'否','Y':'是'}"/>
			</td> 
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
				nameSpace="/aps/026"
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
		<th>prpins</th>	
		<th>
			報價/要保日<br>
			<c:url value="/aps/026/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="INPUTDATE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/026/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="INPUTDATE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			出單單位<br>代號
		</th>
		<th>
			系統別<br>
			<c:url value="/aps/026/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="APPCODE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/026/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="APPCODE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			業務號<br>
			<c:url value="/aps/026/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="BUSINESSNO" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/026/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="BUSINESSNO" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			關係人<br>類型
		</th>
		<th>
			關係人ID<br>
			<c:url value="/aps/026/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="IDENTIFYNUMBER" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/026/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="IDENTIFYNUMBER" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			關係人<br>姓名<br>
			<c:url value="/aps/026/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="INSUREDNAME" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/026/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="INSUREDNAME" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>業務員/保經代/分支機構</th>
		<th>地址<br>相同</th>
		<th>手機<br>相同</th>
		<th>聯絡電話<br>相同</th>
		<th>Email<br>相同</th>
		<th>是否<br>通過</th>
		<th>備註</th>
	</tr>
	<s:iterator var="row" value="devResults">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="center" width="6%"><a href='${goUpdate}${row.no}'>審核作業</a></td>
		<td align="center"><fmt:formatDate value='${inputdate}' pattern='yyyy/MM/dd'/></td>
		<td align="center"><s:property value="com"/></td>
		<td align="center"><s:property value="appcode"/></td>
		<td align="center"><s:property value="businessno"/></td>
		<td align="center" width="4%">
			<c:choose>
				<c:when test="${insuredflag == '1'}">被</c:when>
				<c:when test="${insuredflag == '2'}">要</c:when>
				<c:otherwise></c:otherwise>
			</c:choose>
		<td align="center"><s:property value="identifynumber"/></td>
		<td align="center"><s:property value="insuredname"/></td>
		<td align="center"><s:property value="contactname"/></td>
		<td align="center" width="3%"><s:property value="c1"/></td>
		<td align="center" width="3%"><s:property value="c2"/></td>
		<td align="center" width="3%"><s:property value="c3"/></td>
		<td align="center" width="3%"><s:property value="c4"/></td>
		<td align="center" width="3%"><s:property value="isPass"/></td>
		<td align="center"><s:property value="remark"/></td>
	</tr>
	</s:iterator>
</table>
</s:if>
<br/>
<s:if test="devResults2 != null && 0 != devResults2.size">
<table cellspacing="1" cellpadding="1" border="0" width="970px">
	<tr>
		<td>
		<div align="right">
			<custom:ddlChangePage 
			    formId="mainForm"
				name="pageInfo2" 
				nameSpace="/aps/026"
				currentPage="${pageInfo2.currentPage}" 
				pageSize="${pageInfo2.pageSize}"   
				selectOnChange="ddlPageSizeChanged2" 
				textOnChange="txtChangePageIndex"  
				rowCount="${pageInfo2.rowCount}"
				pageCount="${pageInfo2.pageCount}"/>
		</div>
		</td>
	</tr>
</table>
<!--Grid Table-->
<table border="1" id="gridtable" width="970px" border="0" class="main_table">
	<tr align="center">
		<th>xchg</th>	
		<th>
			報價/要保日<br>
			<c:url value="/aps/026/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="INPUTDATE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/026/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="INPUTDATE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			出單單位<br>代號
		</th>
		<th>
			系統別<br>
			<c:url value="/aps/026/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="APPCODE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/026/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="APPCODE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			業務號<br>
			<c:url value="/aps/026/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="BUSINESSNO" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/026/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="BUSINESSNO" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			關係人<br>類型
		</th>
		<th>
			關係人ID<br>
			<c:url value="/aps/026/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="IDENTIFYNUMBER" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/026/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="IDENTIFYNUMBER" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			關係人<br>姓名<br>
			<c:url value="/aps/026/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="INSUREDNAME" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/026/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="INSUREDNAME" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>業務員/保經代/分支機構</th>
		<th>地址<br>相同</th>
		<th>手機<br>相同</th>
		<th>聯絡電話<br>相同</th>
		<th>Email<br>相同</th>
		<th>是否<br>通過</th>
		<th>備註</th>
	</tr>
	<s:iterator var="row" value="devResults2">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="center" width="6%"><a href='${goUpdate2}${row.no}'>審核作業</a></td>
		<td align="center"><fmt:formatDate value='${inputdate}' pattern='yyyy/MM/dd'/></td>
		<td align="center"><s:property value="com"/></td>
		<td align="center"><s:property value="appcode"/></td>
		<td align="center"><s:property value="businessno"/></td>
		<td align="center" width="4%">
			<c:choose>
				<c:when test="${insuredflag == '1'}">被</c:when>
				<c:when test="${insuredflag == '2'}">要</c:when>
				<c:otherwise></c:otherwise>
			</c:choose>
		<td align="center"><s:property value="identifynumber"/></td>
		<td align="center"><s:property value="insuredname"/></td>
		<td align="center"><s:property value="contactname"/></td>
		<td align="center" width="3%"><s:property value="c1"/></td>
		<td align="center" width="3%"><s:property value="c2"/></td>
		<td align="center" width="3%"><s:property value="c3"/></td>
		<td align="center" width="3%"><s:property value="c4"/></td>
		<td align="center" width="3%"><s:property value="isPass"/></td>
		<td align="center"><s:property value="remark"/></td>
	</tr>
	</s:iterator>
</table>
</s:if>
</s:form>


</body>
</html>