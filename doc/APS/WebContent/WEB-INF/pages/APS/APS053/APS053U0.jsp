<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String title = "車險廠牌車型代號外部資料單筆維護功能";
	String image = path + "/" + "images/";
	String GAMID = "APS053U0";
	String mDescription = "車險廠牌車型代號外部資料單筆維護功能";
	String nameSpace = "/aps/053";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- mantis：CAR0563，處理人員：CD078，需求單編號：CAR0563 廠牌車型代號外部資料單筆維護查詢作業   -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language="JavaScript">
$(document).ready(function(){
	//validate有錯誤時的訊息
	<c:if test="${not empty errorMsg}">
	var msgg = "有錯誤\n";
		<c:forEach items="${errorMsg}" var="entry">   
			$('input[id^=<c:out value="${entry.key}" /> ]').css('background-color','yellow');
			<c:if test="${entry.value != ''}">
				msgg = msgg + 	'<c:out value="${entry.value}" />' + "\n";
			</c:if>
		</c:forEach> 
		alert(msgg);
	</c:if>

	//validate
	$("#mainForm").validate({
		isShowLabel:false,
		isAlertLocalMsg:false,
		rules: {
			"prpdcarmodelext.carbrand":{
				"required":true,
			},
			"prpdcarmodelext.modelname":{
				"required":true
			},
			"prpdcarmodelext.countrycode":{
				"required":true
			},
			"prpdcarmodelext.carkind":{
				"required":true
			},
			"prpdcarmodelext.carkindext":{
				"required":true
			},
			"prpdcarmodelext.makedate":{
				"required":true
			},
			"prpdcarmodelext.validdate":{
				"required":true
			},
			"prpdcarmodelext.invaliddate":{
				"required":true
			}
		},
		messages: {
			"prpdcarmodelext.carbrand":{
				"required":"請輸入廠牌!",
			},
			"prpdcarmodelext.modelname":{
				"required":"請輸入名稱!"
			},
			"prpdcarmodelext.countrycode":{
				"required":"請輸入國產/進口!"
			},
			"prpdcarmodelext.carkind":{
				"required":"請選擇車種!"
			},
			"prpdcarmodelext.carkindext":{
				"required":"請選擇車種大類代號!"
			},
			"prpdcarmodelext.makedate":{
				"required":"請選擇製造日期!"
			},
			"prpdcarmodelext.validdate":{
				"required":"請選擇生效日期(起)!"
			},
			"prpdcarmodelext.invaliddate":{
				"required":"請選擇生效日期(迄)!"
			}
		}
	});
	$('#makedate').datepicker({dateFormat:"yyyy/mm/dd",changeMonth: true,monthNamesShort: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"], 
	      changeYear: true,showButtonPanel: true,showMonthAfterYear: true,});
	$('#validdate').datepicker({dateFormat:"yyyy/mm/dd",changeMonth: true,monthNamesShort: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"], 
	      changeYear: true,showButtonPanel: true,showMonthAfterYear: true});
	$('#invaliddate').datepicker({dateFormat:"yyyy/mm/dd",changeMonth: true,monthNamesShort: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"], 
	      changeYear: true,showButtonPanel: true,showMonthAfterYear: true});
});

	function form_submit(type){
	 	$("label").html('');
			if("update" == type){
				if (checkInputDate()){
			 		$("#mainForm").attr("action","btnUpdate.action");
			 		$("#mainForm").submit();
				}
		}
		if("clear" == type){
			if(confirm("請確認是否放棄修改並返回查詢頁面？")){
				$("#clearForm").submit();
			}
			return false;
		}
	}
	
	function checkInputDate(){
		var validdate = $('#validdate').val();
		var invaliddate = $('#invaliddate').val();
		
		if((validdate == '' && invaliddate != '') || (validdate != '' && invaliddate == '')){
			alert("生效起迄日須同時輸入");
			return false;
		}
		var validdateArr = validdate.split("/");
		var tValiddateDate = (parseInt(validdateArr[0])+parseInt(1911))+'/'+validdateArr[1]+'/'+validdateArr[2];
		var invaliddateArr = invaliddate.split("/");
		var tInvaliddateDate =  (parseInt(invaliddateArr[0])+parseInt(1911))+'/'+invaliddateArr[1]+'/'+invaliddateArr[2];
		if(((new Date(tInvaliddateDate) - new Date(tValiddateDate))/(1000 * 60 * 60 * 24)) < 0){
			alert("生效起日須<=迄日");
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

<body style="margin:0;text-align:left">
<table id="Table11" cellSpacing="1" cellPadding="1" width="970px" border="0">
		<tr>
		<td width="485px">
			<a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoCreate.action"><img src="${pageContext.request.contextPath}/images/New_Icon.gif" border="0"></a>
			<a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoQuery.action"><img src="${pageContext.request.contextPath}/images/Search_Icon.gif" border="0" width="48" height="26"></a>
			<img src="${pageContext.request.contextPath}/images/Help_Icon.gif" border="0" width="49" height="26">
		</td>
		<td align="right" width="485px">PGMID：<%=GAMID%></td>
	</tr>
	<tr>
		<td align="center" colSpan="2"><h3><%=title%></h3></td>
	</tr>
</table>

<!-- clear form -->
<s:form theme="simple" action="lnkGoQuery" namespace="/aps/053" id="clearForm" name="clearForm"/>
<!-- form 開始 -->
<s:form theme="simple" namespace="/aps/053" id="mainForm" name="mainForm">	
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tr bgcolor="white">
			<td class="MainTdColor" width="200px" align="center">
				<span id="lbSearch"><b>修改作業</b></span>
			</td>
			<td colspan="2" class="image"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table1" cellpadding="0" cellspacing="0" width="970px">
		<tr>
			<td width="200px" align="right">序號：</td>
			<td width="285px" align="left">
				<s:property value="prpdcarmodelext.oid"/>
				<s:hidden key="prpdcarmodelext.oid" id="oid"></s:hidden>
			</td>
		</tr>
		<tr>
        	<td width="200px" align="right">*廠牌：</td>
			<td width="285px" align="left">
				<s:textfield key="prpdcarmodelext.carbrand" id="carbrand"/>
			</td>
        	<td width="200px" align="right">廠牌別稱：</td>
			<td width="285px" align="left">
				<s:textfield key="prpdcarmodelext.carbrandext" id="carbrandext"/>
			</td>
		</tr>
		<tr>
        	<td width="200px" align="right">*名稱：</td>
			<td width="285px" align="left">
				<s:textfield key="prpdcarmodelext.modelname" id="modelname"/>
			</td>
        	<td width="200px" align="right">*國產/進口：</td>
			<td width="285px" align="left">
				<s:select key="prpdcarmodelext.countrycode" id="countrycode" theme="simple" list="#{'':'', '1':'國產', '2':'進口'}" />
			</td>
		</tr>
		<tr>
        	<td width="200px" align="right">*車種：</td>
			<td width="285px" align="left">
				<s:textfield key="prpdcarmodelext.carkind" id="carkind"/>
			</td>
        	<td width="200px" align="right">*車種大類代號：</td>
			<td width="285px" align="left">
				<s:textfield key="prpdcarmodelext.carkindext" id="carkindext"/>
			</td> 
		</tr>
		<tr>
        	<td width="200px" align="right">排氣量：</td>
			<td width="285px" align="left">
				<s:textfield key="prpdcarmodelext.exhaustscale" id="exhaustscale"/>
			</td> 
        	<td width="200px" align="right">噸數：</td>
			<td width="285px" align="left">
				<s:textfield key="prpdcarmodelext.tcount" id="tcount"/>
			</td> 
		</tr>
		<tr>
        	<td width="200px" align="right">乘載數量：</td>
			<td width="285px" align="left">
				<s:textfield key="prpdcarmodelext.seatcount" id="seatcount"/>
			</td>
        	<td width="200px" align="right">門數：</td>
			<td width="285px" align="left">
				<s:textfield key="prpdcarmodelext.doors" id="doors"/>
			</td>
		</tr>
		<tr>
        	<td width="200px" align="right">燃料：</td>
			<td width="285px" align="left">
				<s:textfield key="prpdcarmodelext.fueltype" id="fueltype"/>
			</td>
        	<td width="200px" align="right">排檔型式：</td>
			<td width="285px" align="left">
				<s:textfield key="prpdcarmodelext.geartype" id="geartype"/>
			</td>
		</tr>
		<tr>
        	<td width="200px" align="right">*製造日期：</td>
			<td width="285px" align="left">
				<s:textfield key="prpdcarmodelext.makedate" id="makedate"/>
			</td>
			<td width="200px" align="right">用途：</td>
			<td width="285px" align="left">
				<s:textfield key="prpdcarmodelext.application1" id="application1"/>
			</td>
        	
		</tr>
		<tr>
			<td width="200px" align="right">*生效日期(起)：</td>
			<td width="285px" align="left">
				<s:textfield key="prpdcarmodelext.validdate" id="validdate"/>
			</td>
        	<td width="200px" align="right">備註：</td>
			<td width="285px" align="left">
				<s:textfield key="prpdcarmodelext.mark" id="mark"/>
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">*生效日期(迄)：</td>
			<td width="285px" align="left">
				<s:textfield key="prpdcarmodelext.invaliddate" id="invaliddate"/>
			</td>
		</tr>
		<tr>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">建檔人員：</td>
			<td width="200px" align="left">
				<s:property value="prpdcarmodelext.icreate"/>
			</td>
			<td width="150px" align="right">建檔日期：</td>
			<td width="200px" align="left">
				<fmt:formatDate value='${prpdcarmodelext.dcreate}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">最後異動人員：</td>
			<td width="200px" align="left">
				<s:property value="prpdcarmodelext.iupdate"/>
			</td>
			<td width="150px" align="right">最後異動日期：</td>
			<td width="200px" align="left">
				<fmt:formatDate value='${prpdcarmodelext.dupdate}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
	</table>
	<table width="970px">
		<tr>
			<td align="center">
				<input value="存檔" type="button" onclick="javascript:form_submit('update');">&nbsp;&nbsp;&nbsp;&nbsp;
			    <input value="取消" type="button" onclick="javascript:form_submit('clear');"/>
		    </td>
		</tr>
		<tr>
			<td align="left">
				註1：請注意，本資料僅供廠牌車型代號外部資料使用。<br/>
		   	</td>
		</tr>
	</table>
</s:form>
</body>
</html>