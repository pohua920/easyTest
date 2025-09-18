<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "電信詐騙盜打維護作業";
	String image = path + "/" + "images/";
	String GAMID = "APS051A0";
	String mDescription = "電信詐騙盜打維護作業";
	String nameSpace = "/aps/051";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script language="JavaScript">

	function form_submit(method){
		 $("label").html('');
		 if('create' == method){
			 $("#mainForm").attr("action","btnCreate.action");
			 $("#mainForm").submit();
		 }
		 if('clear' == method){
				if (confirm("請確認是否放棄新增？")==true){
					$("#clearForm").submit();
				}
			return false;
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
<body style="margin: 0; text-align: left">
<table id="table1" cellSpacing="1" cellPadding="1" width="970px" border="0">
	<tr>
		<td width="485px">
		<a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoQuery.action">
		<img src="${pageContext.request.contextPath}/images/Search_Icon.gif" border="0" width="48" height="26"></a> 
		<img src="${pageContext.request.contextPath}/images/Help_Icon.gif" border="0" width="49" height="26"></td>
		<td align="right" width="485px">PGMID：<%=GAMID%></td>
	</tr>
	<tr>
		<td align="center" colSpan="2" width="970px"><h3><%=title%></h3></td>
	</tr>
</table>

<!-- clear form -->
<s:form theme="simple" action="lnkGoCreate" namespace="/aps/051" id="clearForm" name="clearForm" />
<!-- form 開始 -->
<s:form theme="simple" namespace="/aps/051" id="mainForm" name="mainForm">

	<table id="table2" cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="MainTdColor" style="width: 200px" align="center"><span id="lbSearch"><b>新增API</b></span></td>
			<td class="image" style="width: 20px"></td>
			<td colspan="2"></td>
		</tr>
	</table>
	<table width="870px" cellpadding="0" cellspacing="0" border="0" >

	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="870px" border="0">
		<tr>
			<td align="right" width="200px">維修單號：</td>
			<td align="left" width="200px" >
				<s:textfield key="fetclaimTypecm.mtnNo" id="mtnNo" size="12" maxlength="12"  cssClass="txtLabel" readonly="true" tabindex="-1"/>
			</td>
			<td align="right" width="200px">保險合約編號：</td>
			<td align="left"><s:textfield key="fetclaimTypecm.contractId" id="contractId" size="30" maxlength="30"/></td>	
		</tr>
		<tr>
			<td align="right" width="200px">IMEI：</td>
			<td align="left" width="200px" >
				<s:textfield key="fetclaimTypecm.imei" id="imei" size="15" maxlength="15"/>
			</td>
			<td align="right" width="200px"></td>
			<td align="left">
			</td>	
		</tr>
		<tr>
			<td align="right" width="200px">送修日期：</td>
			<td align="left" width="200px" colspan="3">
				<s:textfield key="fetclaimTypecm.notifDate" id="notifDate" size="20" maxlength="20"/>格式：yyyy-MM-ddTHH:mm:ss
			</td>
		</tr>
		<tr>
			<td align="right" width="200px">報修料號代碼：</td>
			<td align="left" width="200px" colspan="3">
				<s:textfield key="fetclaimTypecm.matnrNo" id="matnrNo" size="90" maxlength="90"/>
			</td>
		</tr>
		<tr>
			<td align="right" width="200px">理賠類型：</td>
			<td align="left" width="200px" >
                <!-- mantis：MOB0021，處理人員：DP0714，新商品上線APS作業調整 -->
				<s:select key="fetclaimTypecm.claimType" id="claimType" theme="simple" list="#{'':'', 'M':'盜打', 'C':'詐騙', 'E':'爆炸'}" />
			</td>
			<td align="right" width="200px">授權階段代號：</td>
			<td align="left">
				<s:select key="fetclaimTypecm.finalAuth" id="finalAuth" theme="simple" list="#{'N':'API'}" />
			</td>	
		</tr>
		<tr>
			<td align="right" width="200px">事故日期：</td>
			<td align="left" width="200px" >
				<s:textfield key="fetclaimTypecm.eventDate" id="eventDate" size="10" maxlength="10"/>格式：yyyy-MM-dd
			</td>
			<td align="right" width="200px">申請授權維修金額：</td>
			<td align="left">
				<s:textfield key="fetclaimTypecm.listprice1" id="listprice1" size="7" maxlength="7"/>
			</td>	
		</tr>
		<tr>
			<td align="right" width="200px">案件授權類別代號：</td>
			<td align="left" width="200px" >
				<s:select key="fetclaimTypecm.specialApproval" id="specialApproval" theme="simple" list="#{'':'', 'N':'一般送審件', 'Y':'特殊授權件'}" />
			</td>
			<td align="right" width="200px">申請授權維修金額：</td>
			<td align="left">
				<s:textfield key="fetclaimTypecm.asuClaimAmount" id="asuClaimAmount" size="7" maxlength="7"/>
			</td>	
		</tr>
		<tr>
			<td align="right" width="200px">保險單代碼：</td>
			<td align="left" width="200px" >
				<s:textfield key="fetclaimTypecm.policyNo" id="policyNo" size="15" maxlength="15"/>
			</td>
			<td align="right" width="200px">賠款支付方式：</td>
			<td align="left">
				<s:select key="fetclaimTypecm.partDescription10" id="partDescription10" theme="simple" list="#{'':'', 'B':'匯款', 'Q':'支票'}" />
			</td>	
		</tr>
		<tr>
			<td align="right" width="200px">受款人姓名：</td>
			<td align="left" width="200px" >
				<s:textfield key="fetclaimTypecm.partDescription1" id="partDescription1" size="40" maxlength="40"/>
			</td>
			<td align="right" width="200px">受款人身分證號：</td>
			<td align="left"><s:textfield key="fetclaimTypecm.partDescription2" id="partDescription2" size="10" maxlength="10"/></td>	
		</tr>
		<tr>
			<td align="right" width="200px">受款人銀行代碼：</td>
			<td align="left" width="200px" >
				<s:textfield key="fetclaimTypecm.partDescription3" id="partDescription3" size="30" maxlength="30"/>
			</td>
			<td align="right" width="200px">是否取消禁背：</td>
			<td align="left">
				<s:select key="fetclaimTypecm.partDescription9" id="partDescription9" theme="simple" list="#{'':'', 'Y':'是-取消禁背', 'N':'否-不取消禁背'}" />
			</td>
		</tr>
		<tr>
			<td align="right" width="200px">受款人分行代碼：</td>
			<td align="left" width="200px" >
				<s:textfield key="fetclaimTypecm.partDescription4" id="partDescription4" size="30" maxlength="30"/>
			</td>
			<td align="right" width="200px">受款人銀行帳號：</td>
			<td align="left"><s:textfield key="fetclaimTypecm.partDescription5" id="partDescription5" size="40" maxlength="40"/></td>	
		</tr>
		<tr>
			<td align="right" width="200px">受款人郵遞區號：</td>
			<td align="left" width="200px" >
				<s:textfield key="fetclaimTypecm.partDescription6" id="partDescription6" size="10" maxlength="10"/>
			</td>
			<td align="right" width="200px">受款人電話：</td>
			<td align="left"><s:textfield key="fetclaimTypecm.partDescription7" id="partDescription7" size="40" maxlength="40"/></td>	
		</tr>
		<tr>
			<td align="right" width="200px">受款人地址：</td>
			<td align="left" width="200px" colspan="3">
				<s:textfield key="fetclaimTypecm.partDescription8" id="partDescription8" size="120" maxlength="120"/>
			</td>
		</tr>
		<tr>
			<td align="right" width="200px">事故毀損說明：</td>
			<td align="left" width="200px" colspan="3">
				<s:textarea key="fetclaimTypecm.eventCause" id="eventCause" rows="5" cols="90"/>
			</td>
		</tr>
		<tr>
			<td align="right" width="200px">故障原因說明：</td>
			<td align="left" width="200px" colspan="3">
				<s:textarea key="fetclaimTypecm.causeDescription1" id="causeDescription1" rows="5" cols="90"/>
			</td>
		</tr>
	</table>
	<table width="970px">
		<tr>
			<td align="center">
				<input value="存檔" type="button" onclick="javascript:form_submit('create');">&nbsp;&nbsp;&nbsp;&nbsp;
				<input value="取消" type="button" onclick="javascript:form_submit('clear');" /></td>
		</tr>
	</table>
</s:form>
</body>
</html>