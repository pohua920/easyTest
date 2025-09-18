<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "電子保單檢核異常搜尋作業";
	String image = path + "/" + "images/";
	String GAMID = "APS054E0";
	String mDescription = "電子保單檢核異常搜尋作業";
	String nameSpace = "/aps/054";
%>
<!-- mantis：OTH0159，處理人員：CD094，需求單編號：OTH0159 電子保單系統條款檢核不通過資料通知(APS) -->
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

		$('#startDate').datepicker({dateFormat:"yyyy/mm/dd"});
		$('#endDate').datepicker({dateFormat:"yyyy/mm/dd"});

		ajaxAction('findRiskCode');
	});

	function form_submit(type){
		if("query" == type){
			 $("#mainForm").attr("action","btnQuery.action");
			 $("#mainForm").submit();
		}
	}
	function ajaxAction(action){
		//取出ajax xml相對路徑
		var contextPath = '<%=request.getScheme()%>' + '://<%=request.getServerName()+":"+request.getServerPort()+request.getContextPath()%>';
		var path = ''; 

		if(action == 'findRiskCode'){
	   		var classcode = $("#classcode").val()
	    	path = contextPath + '/aps/ajax003/findRiskCode.action?classcode='+classcode;
		}
		//執行ajax
		$.ajax({
			url : path,
			type: 'POST',
			data: {	},
			dataType: 'json',
			timeout: 10000,
			async: false,
			cache: false,
			error: function (data, status, e){
				ajaxError(data, status);
			},
			success: function (data, status){
				ajaxSuccess(action, data);
			}
		});
	}
	//ajax成功時會回來的method
		function ajaxSuccess(action, data){
			
		//ajax回傳的data存在，將ajax回傳的data設定至頁面上
		var riskcode = $("#riskcode");
		var classcode = $("#classcode");
		var lastRiskcode = $("#lastRiskcode").val();
		if(data.riskcodes != null&&data.riskcode!=''){
			if(action == 'findRiskCode'){
				riskcode.removeOption(/./);
				var da = data.riskcodes;
				for(var k in da){
					riskcode.addOption(k,da[k]);
				}
				riskcode.selectOptions(lastRiskcode);	
			}
		}else{//ajax回傳的data不存在
			if(action == 'findRiskCode'){
				riskcode.removeOption(/./);
				riskcode.addOption('*','');
				classcode.val('全部');
			}
		}		
	}

	//ajax發生錯誤時會呼叫的method
	function ajaxError(data, status){
		alert('無資料');
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

<body style="margin:0;text-align:left">
<s:url action="lnkGoDownload?" namespace="/aps/054" var="download"/>
<table cellspacing="1" cellpadding="1" width="970px" border="0">
	<tr>
		<td width="485px">
			<img src="${pageContext.request.contextPath}/images/Help_Icon.gif" border="0" width="49" height="26">
<%-- 			<a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoCreate.action"> --%>
<%-- 			<img src="${pageContext.request.contextPath}/images/New_Icon.gif" border="0"></a> --%>
		</td>
		<td align="right" width="485px">PGMID：<%=GAMID%></td>
	</tr>
	<tr>
		<td align="center" colSpan="2"><h3><%=title%></h3></td>
	</tr>
</table>
<!-- clear form -->
<!-- form 開始 -->
<s:form theme="simple" namespace="/aps/054" id="mainForm" name="mainForm">
	<s:hidden key="filter.classcodeMap"></s:hidden>
	<table id="table2"  cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="MainTdColor" style="width:200px" align="center">
				<span id="lbSearch"><b>查詢作業</b></span></td>
			<td colspan="3" class="image"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tr>
        	<td width="200px" align="right">險別代碼：</td>
			<td width="285px" align="left">
				<s:select list="#{'':'全部','A':'A 任意險類', 'B':'B 強制險類', 'AB':'AB 車險-強制險+任意險','C':'C 責任險', 'C1':'C1 傷害暨健康險', 'M':'M 水險', 'F':'F 火險', 'E':'E 工程險'}" key="filter.classcode" id="classcode" onchange="ajaxAction('findRiskCode');"/>
			</td>
			<td colspan="5"></td>
			<td width="200px" align="right">保單號碼：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.policyno" id="policyno"/>
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">險種代碼：</td>
			<td width="285px" align="left">
				<s:select list="riskcodeMap" key="filter.riskcode" id="riskcode" />
			</td>
			<td colspan="5"></td>
		</tr>	
		<tr>

		</tr>
		<tr>
        	<td width="200px" align="right">日期起日：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.startDate" id="startDate" maxlength="10" size="10" theme="simple" />
			</td>
		</tr>
		<tr>
        	<td width="200px" align="right">日期迄日：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.endDate" id="endDate" maxlength="10" size="10" theme="simple" />
			</td> 
			<td colspan="5"></td>       	
		</tr>
		
  	</table>
  	<table width="970px" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="center">
			<input type="button" value="查詢" onclick="javascript:form_submit('query');"/>
		</td>
		<input type="hidden" id="lastRiskcode" value="${pageInfo.filter.riskcode}"/>  <!-- 上一次的查詢險種代碼 -->
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
				nameSpace="/aps/054"
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
		<th width="40px">原始檔</th>
		<th width="60px">保單號碼</th>	
		<th width="60px">檔案名稱</th>
		<th width="60px">險種代碼</th>
		<th width="60px">結果代碼</th>
		<th width="60px">訊息</th>
		<th>錯誤訊息</th>
		<th width="60px">建立日期</th>
	</tr>
	<s:iterator var="row" value="devResults">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="center" width="50px">
			<a href="${download}newepolicyVo.policyno=${row.policyno}&&newepolicyVo.logfilename=${row.logfilename}">下載</a>
		</td>
		<td align="center"><s:property value="policyno"/></td>
		<td align="center"><s:property value="filename"/></td>
		<td align="center"><s:property value="riskcode"/></td>
		<td align="center"><s:property value="resultcode"/></td>
		<td align="center"><s:property value="resultmessage"/></td>
		<td align="left"><s:property value="errormessage"/></td>
		<td align="center"><s:property value="dcreate"/></td>
	</tr>
	</s:iterator>
</table>
</s:if>
</s:form>

</body>
</html>