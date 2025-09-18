<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String title = "車險再保註記設定維護作業";
	String image = path + "/" + "images/";
	String GAMID = "APS049U0";
	String mDescription = "車險再保註記設定維護作業";
	String nameSpace = "/aps/049";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 
mantis：CAR0553，處理人員：DP0706，需求單編號：CAR0553.APS-車險再保註記設定維護功能
-->
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
	//日期格式化:yyyy/mm/dd
	if($('#periodstartdate').val() != ''){
		var periodstartdateDate =  new Date($('#periodstartdate').val());
		$('#periodstartdate').val(periodstartdateDate.getFullYear() + "/" + String(periodstartdateDate.getMonth()+1).padStart(2, '0') + "/" + String(periodstartdateDate.getDate()).padStart(2, '0'));
		
	}
	if($('#periodend').val() != ''){
		var periodendDate =  new Date($('#periodend').val());
		$('#periodend').val(periodendDate.getFullYear() + "/" + String(periodendDate.getMonth()+1).padStart(2, '0') + "/" + String(periodendDate.getDate()).padStart(2, '0'));
	}
	$('#periodstartdate').datepicker({dateFormat:"yyyy/mm/dd"});
	$('#periodend').datepicker({dateFormat:"yyyy/mm/dd"});

	//validate
	$("#mainForm").validate({
		isShowLabel:false,
		isAlertLocalMsg:false,
		rules: {
			"carReinsurancePlan.branchName":{
				"required":true,
			},
			"carReinsurancePlan.receivedBranchName":{
				"required":true
			},
			"carReinsurancePlan.handler1code":{
				"required":true
			},
			"carReinsurancePlan.deleteFlag":{
				"required":true
			}
		},
		messages: {
			"carReinsurancePlan.branchName":{
				"required":"請輸入放款帳務行名稱!",
			},
			"carReinsurancePlan.receivedBranchName":{
				"required":"請輸入洽定文件歸屬行名稱!"
			},
			"carReinsurancePlan.handler1code":{
				"required":"請輸入服務人員代號!"
			},
			"carReinsurancePlan.deleteFlag":{
				"required":"請選擇刪除註記!"
			}
		}
	});

});

	function form_submit(type){
	 	$("label").html('');
		if("update" == type){
			if(validate()){
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
		if("genExcel" == type){
			$("#mainForm").attr("action","btnGenExcel.action");
			$("#mainForm").submit();
		}
	}

	function validate(){
		var message = "";
		var check = true;

		// 再保類型相關欄位為必填之檢查
		var reinsurancetype = $('#reinsurancetype').val();	
		if(reinsurancetype == "1"){// 再保類型為「合約=1」
			if($('#contractname').val() == ''){
				alert("再保類型為「合約」，請輸入合約名稱。");
				check = false;
			} else if($('#periodstartdate').val() == ''){
				alert("再保類型為「合約」，請輸入合約期限(起)。");
				check = false;
			}  else if($('#periodend').val() == ''){
				alert("再保類型為「合約」，請輸入合約期限(迄)。");
				check = false;
			} 
		} else {// 再保類型為「臨分=2」
			if($('#bidnotype').val() == ''){
				alert("再保類型為「臨分」，請輸入分出單號類別。");
				check = false;
			} else if($('#bidno').val() == ''){
				alert("再保類型為「臨分」，請輸入分出單號。");
				check = false;
			}

		}
		return check;
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
<s:form theme="simple" action="lnkGoQuery" namespace="/aps/049" id="clearForm" name="clearForm"/>
<!-- form 開始 -->
<s:form theme="simple" namespace="/aps/049" id="mainForm" name="mainForm">	
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tr bgcolor="white">
			<td class="MainTdColor" width="200px" align="center">
				<span id="lbSearch"><b>修改作業</b></span>
			</td>
			<td colspan="6" class="image"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table1" cellpadding="0" cellspacing="0" width="970px">
		<tr>
			<td width="200px" align="right">序號：</td>
			<td width="285px" align="left" colspan="7">
				<s:textfield key="carReinsurancePlan.oid" id="oid" readonly="true" cssClass="txtLabel"/>
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">再保類型：</td>
			<td width="285px" align="left">
				<s:hidden name="carReinsurancePlan.reinsurancetype" id="reinsurancetype"/>
				<c:if test="${carReinsurancePlan.reinsurancetype == '1'}">合約</c:if>
				<c:if test="${carReinsurancePlan.reinsurancetype == '2'}">臨分</c:if>
			</td>
			<td width="200px" align="right">合約名稱：</td>
			<td width="285px" align="left">
				<s:textfield key="carReinsurancePlan.contractname" id="contractname" size="20" maxlength="50" />
			</td>
			<td width="200px" align="right">合約期限(起)：</td>
			<td width="285px" align="left">
				<s:textfield key="carReinsurancePlan.periodstartdate" id="periodstartdate" size="10" maxlength="10" />
			</td>
			<td width="200px" align="right">合約期限(迄)：</td>
			<td width="285px" align="left">
				<s:textfield key="carReinsurancePlan.periodend" id="periodend" size="10" maxlength="10"/>
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">業務來源代號：</td>
			<td width="285px" align="left">
				<s:textfield key="carReinsurancePlan.bcode" id="bcode" size="20" maxlength="50"/>
			</td>
			<td width="200px" align="right">車種代號：</td>
			<td width="285px" align="left">
				<s:textfield key="carReinsurancePlan.carkindcode" id="carkindcode" size="20" maxlength="50" />
			</td>
			<td width="200px" align="right"></td>
			<td width="285px" align="left"></td>
			<td width="200px" align="right"></td>
			<td width="285px" align="left"></td>
		</tr>
		<tr>
			<td width="200px" align="right">險種代號：</td>
			<td width="285px" align="left">
				<s:textfield key="carReinsurancePlan.kindcode" id="kindcode" size="20" maxlength="50"/>
			</td>
			<td width="200px" align="right">保額(起)：</td>
			<td width="285px" align="left">
				<s:textfield key="carReinsurancePlan.amountS" id="amountS" size="20" maxlength="50" />
			</td>
			<td width="200px" align="right">保額(迄)：</td>
			<td width="285px" align="left">
				<s:textfield key="carReinsurancePlan.amountE" id="amountE" size="20" maxlength="50" />
			</td>
			<td width="200px" align="right"></td>
			<td width="285px" align="left"></td>
		</tr>
		<tr>
			<td width="200px" align="right">分出單號類別：</td>
			<td width="285px" align="left">
				<s:select key="carReinsurancePlan.bidnotype" id="bidnotype" theme="simple" list="#{'':'','1':'報價', '2':'要保', '3':'保單'}" />
			</td>
			<td width="200px" align="right">分出單號：</td>
			<td width="285px" align="left">
				<s:textfield key="carReinsurancePlan.bidno" id="bidno" size="20" maxlength="50" />
			</td>
			<td width="200px" align="right"></td>
			<td width="285px" align="left"></td>
			<td width="200px" align="right"></td>
			<td width="285px" align="left"></td>
		</tr>
		<tr>
			<td width="200px" align="right">車牌：</td>
			<td width="285px" align="left">
				<s:textfield key="carReinsurancePlan.licenseno" id="licenseno" size="20" maxlength="50"/>
			</td>
			<td width="200px" align="right">備註：</td>
			<td width="285px" align="left">
				<s:textfield key="carReinsurancePlan.mark" id="mark" size="20" maxlength="50" />
			</td>
			<td width="200px" align="right"></td>
			<td width="285px" align="left"></td>
			<td width="200px" align="right"></td>
			<td width="285px" align="left"></td>
		</tr>
		<tr>
			<td width="200px" align="right">效力註記：</td>
			<td width="285px" align="left" colspan="7">
				<s:select key="carReinsurancePlan.deleteFlag" id="deleteFlag" theme="simple" list="#{'N':'無效', 'Y':'有效'}" />
			</td>
		</tr>
		<tr>
        	<td width="200px" align="right">建檔人員：</td>
			<td width="285px" align="left">
				<s:textfield key="carReinsurancePlan.icreate" id="icreate" readonly="true" cssClass="txtLabel"/>
			</td>
        	<td width="200px" align="right">建檔日期：</td>
			<td width="285px" align="left">
				<fmt:formatDate value='${carReinsurancePlan.dcreate}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
		<tr>
        	<td width="200px" align="right">最後異動人員：</td>
			<td width="285px" align="left">
				<s:textfield key="carReinsurancePlan.iupdate" id="iupdate" readonly="true" cssClass="txtLabel"/>
			</td>
			<td width="200px" align="right">最後異動日期：</td>
			<td width="285px" align="left" colspan="3">
				<fmt:formatDate value='${carReinsurancePlan.dupdate}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
			<td width="200px" align="right"></td>
			<td width="285px" align="left"></td>
			<td width="200px" align="right"></td>
			<td width="285px" align="left"></td>
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
				註1：請注意，本資料僅供任意車險再保註記使用。
		    </td>
		</tr>
	</table>
</s:form>
</body>
</html>