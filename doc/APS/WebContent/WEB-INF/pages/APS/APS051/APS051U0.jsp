<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "電信詐騙盜打維護作業";
	String image = path + "/" + "images/";
	String GAMID = "APS051U0";
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
		 if('send' == method){
			 $("#mainForm").attr("action","btnSend.action");
			 $("#mainForm").submit();
		 }
		 if('upload' == method){
			 $("#mainForm").attr("action","btnUpload.action");
			 $("#mainForm").submit();
		 }
		 if('clear' == method){
				if (confirm("請確認是否放棄並返回查詢頁面？")==true){
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
<s:form theme="simple" action="lnkGoQuery" namespace="/aps/051" id="clearForm" name="clearForm" />
<!-- form 開始 -->
<s:form theme="simple" namespace="/aps/051" id="mainForm" name="mainForm">
	<s:hidden name="fetclaimTypecm.oid" id="oid" />
	<table id="table2" cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="MainTdColor" style="width: 200px" align="center"><span id="lbSearch"><b>發送API</b></span></td>
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
				<s:hidden name="fetclaimTypecm.mtnNo" id="mtnNo" />
				<s:property value="fetclaimTypecm.mtnNo"/>
			</td>
			<td align="right" width="200px">保險合約編號：</td>
			<td align="left">
				<s:if test='%{fetclaimTypecm.apiStatus == "2"}'>
					<s:property value="fetclaimTypecm.contractId"/>
				</s:if>
				<s:else>
					<s:textfield key="fetclaimTypecm.contractId" id="contractId" size="30" maxlength="30"/>				
				</s:else>
			</td>	
		</tr>
		<tr>
			<td align="right" width="200px">IMEI：</td>
			<td align="left" width="200px" >
				<s:if test='%{fetclaimTypecm.apiStatus == "2"}'>
					<s:property value="fetclaimTypecm.imei"/>
				</s:if>
				<s:else>
					<s:textfield key="fetclaimTypecm.imei" id="imei" size="15" maxlength="15"/>				
				</s:else>
			</td>
			<td align="right" width="200px"></td>
			<td align="left">
			</td>	
		</tr>
		<tr>
			<td align="right" width="200px">送修日期：</td>
			<td align="left" width="200px" colspan="3">
				<s:if test='%{fetclaimTypecm.apiStatus == "2"}'>
					<s:property value="fetclaimTypecm.notifDate"/>
				</s:if>
				<s:else>
					<s:textfield key="fetclaimTypecm.notifDate" id="notifDate" size="20" maxlength="20"/>格式：yyyy-MM-ddTHH:mm:ss				
				</s:else>
			</td>
		</tr>
		<tr>
			<td align="right" width="200px">報修料號代碼：</td>
			<td align="left" width="200px" colspan="3">
				<s:if test='%{fetclaimTypecm.apiStatus == "2"}'>
					<s:property value="fetclaimTypecm.matnrNo"/>
				</s:if>
				<s:else>
					<s:textfield key="fetclaimTypecm.matnrNo" id="matnrNo" size="90" maxlength="90"/>				
				</s:else>
			</td>
		</tr>
		<tr>
			<td align="right" width="200px">理賠類型：</td>
			<td align="left" width="200px" >
				<s:hidden name="fetclaimTypecm.claimType" id="claimType" />
				<s:if test='fetclaimTypecm.claimType == "M"'>盜打</s:if>
				<s:if test='fetclaimTypecm.claimType == "C"'>詐騙</s:if>
				<!-- mantis：MOB0021，處理人員：DP0714，新商品上線APS作業調整 -->
				<s:if test='fetclaimTypecm.claimType == "E"'>爆炸</s:if>
			</td>
			<td align="right" width="200px">授權階段代號：</td>
			<td align="left">
				<s:if test='%{fetclaimTypecm.apiStatus != "2"}'>
					<s:select key="fetclaimTypecm.finalAuth" id="finalAuth" theme="simple" list="#{'N':'API'}" />
				</s:if>
				<s:else>
					<s:select key="fetclaimTypecm.finalAuth" id="finalAuth" theme="simple" list="#{'Y':'XML'}" />
				</s:else>
			</td>	
		</tr>
		<tr>
			<td align="right" width="200px">事故日期：</td>
			<td align="left" width="200px" >
				<s:if test='%{fetclaimTypecm.apiStatus == "2"}'>
					<s:property value="fetclaimTypecm.eventDate"/>
				</s:if>
				<s:else>
					<s:textfield key="fetclaimTypecm.eventDate" id="eventDate" size="10" maxlength="10"/>格式：yyyy-MM-dd				
				</s:else>
			</td>
			<td align="right" width="200px">申請授權維修金額：</td>
			<td align="left">
				<s:textfield key="fetclaimTypecm.listprice1" id="listprice1" size="7" maxlength="7"/>
			</td>	
		</tr>
		<tr>
			<td align="right" width="200px">案件授權類別代號：</td>
			<td align="left" width="200px" >
				<s:if test='%{fetclaimTypecm.apiStatus == "2"}'>
					<s:if test='fetclaimTypecm.specialApproval == "N"'>一般送審件</s:if>
					<s:if test='fetclaimTypecm.specialApproval == "Y"'>特殊授權件</s:if>
				</s:if>
				<s:else>
					<s:select key="fetclaimTypecm.specialApproval" id="specialApproval" theme="simple" list="#{'':'', 'N':'一般送審件', 'Y':'特殊授權件'}" />				
				</s:else>
			</td>
			<td align="right" width="200px">申請授權維修金額：</td>
			<td align="left">
				<s:textfield key="fetclaimTypecm.asuClaimAmount" id="asuClaimAmount" size="7" maxlength="7"/>
			</td>	
		</tr>
		<tr>
			<td align="right" width="200px">保險單代碼：</td>
			<td align="left" width="200px" >
				<s:if test='%{fetclaimTypecm.apiStatus == "2"}'>
					<s:property value="fetclaimTypecm.policyNo"/>
				</s:if>
				<s:else>
					<s:textfield key="fetclaimTypecm.policyNo" id="policyNo" size="15" maxlength="15"/>				
				</s:else>
			</td>
			<td align="right" width="200px">賠款支付方式：</td>
			<td align="left">
				<s:if test='%{fetclaimTypecm.apiStatus == "2"}'>
					<s:if test='fetclaimTypecm.partDescription10 == "B"'>匯款</s:if>
					<s:if test='fetclaimTypecm.partDescription10 == "Q"'>支票</s:if>
				</s:if>
				<s:else>
					<s:select key="fetclaimTypecm.partDescription10" id="partDescription10" theme="simple" list="#{'':'', 'B':'匯款', 'Q':'支票'}" />				
				</s:else>
			</td>	
		</tr>
		<tr>
			<td align="right" width="200px">受款人姓名：</td>
			<td align="left" width="200px" >
				<s:if test='%{fetclaimTypecm.apiStatus == "2"}'>
					<s:property value="fetclaimTypecm.partDescription1"/>
				</s:if>
				<s:else>
					<s:textfield key="fetclaimTypecm.partDescription1" id="partDescription1" size="40" maxlength="40"/>				
				</s:else>
			</td>
			<td align="right" width="200px">受款人身分證號：</td>
			<td align="left">
				<s:if test='%{fetclaimTypecm.apiStatus == "2"}'>
					<s:property value="fetclaimTypecm.partDescription2"/>
				</s:if>
				<s:else>
					<s:textfield key="fetclaimTypecm.partDescription2" id="partDescription2" size="10" maxlength="10"/>				
				</s:else>
			</td>	
		</tr>
		<tr>
			<td align="right" width="200px">受款人銀行代碼：</td>
			<td align="left" width="200px" >
				<s:if test='%{fetclaimTypecm.apiStatus == "2"}'>
					<s:property value="fetclaimTypecm.partDescription3"/>
				</s:if>
				<s:else>
					<s:textfield key="fetclaimTypecm.partDescription3" id="partDescription3" size="30" maxlength="30"/>				
				</s:else>
			</td>
			<td align="right" width="200px">是否取消禁背：</td>
			<td align="left">
				<s:if test='%{fetclaimTypecm.apiStatus == "2"}'>
					<s:if test='fetclaimTypecm.partDescription9 == "Y"'>是-取消禁背</s:if>
					<s:if test='fetclaimTypecm.partDescription9 == "N"'>否-不取消禁背</s:if>
				</s:if>
				<s:else>
					<s:select key="fetclaimTypecm.partDescription9" id="partDescription9" theme="simple" list="#{'':'', 'Y':'是-取消禁背', 'N':'否-不取消禁背'}" />				
				</s:else>
			</td>	
		</tr>
		<tr>
			<td align="right" width="200px">受款人分行代碼：</td>
			<td align="left" width="200px" >
				<s:if test='%{fetclaimTypecm.apiStatus == "2"}'>
					<s:property value="fetclaimTypecm.partDescription4"/>
				</s:if>
				<s:else>
					<s:textfield key="fetclaimTypecm.partDescription4" id="partDescription4" size="30" maxlength="30"/>				
				</s:else>
			</td>
			<td align="right" width="200px">受款人銀行帳號：</td>
			<td align="left">
				<s:if test='%{fetclaimTypecm.apiStatus == "2"}'>
					<s:property value="fetclaimTypecm.partDescription5"/>
				</s:if>
				<s:else>
					<s:textfield key="fetclaimTypecm.partDescription5" id="partDescription5" size="40" maxlength="40"/>				
				</s:else>
			</td>	
		</tr>
		<tr>
			<td align="right" width="200px">受款人郵遞區號：</td>
			<td align="left" width="200px" >
				<s:if test='%{fetclaimTypecm.apiStatus == "2"}'>
					<s:property value="fetclaimTypecm.partDescription6"/>
				</s:if>
				<s:else>
					<s:textfield key="fetclaimTypecm.partDescription6" id="partDescription6" size="10" maxlength="10"/>				
				</s:else>
			</td>
			<td align="right" width="200px">受款人電話：</td>
			<td align="left">
				<s:if test='%{fetclaimTypecm.apiStatus == "2"}'>
					<s:property value="fetclaimTypecm.partDescription7"/>
				</s:if>
				<s:else>
					<s:textfield key="fetclaimTypecm.partDescription7" id="partDescription7" size="40" maxlength="40"/>				
				</s:else>
			</td>	
		</tr>
		<tr>
			<td align="right" width="200px">受款人地址：</td>
			<td align="left" width="200px" colspan="3">
				<s:if test='%{fetclaimTypecm.apiStatus == "2"}'>
					<s:property value="fetclaimTypecm.partDescription8"/>
				</s:if>
				<s:else>
					<s:textfield key="fetclaimTypecm.partDescription8" id="partDescription8" size="120" maxlength="120"/>				
				</s:else>
			</td>
		</tr>
		<tr>
			<td align="right" width="200px">事故毀損說明：</td>
			<td align="left" width="200px" colspan="3">
				<s:if test='%{fetclaimTypecm.apiStatus == "2"}'>
					<s:property value="fetclaimTypecm.eventCause"/>
				</s:if>
				<s:else>
					<s:textarea key="fetclaimTypecm.eventCause" id="eventCause" rows="5" cols="90"/>				
				</s:else>
			</td>
		</tr>
		<tr>
			<td align="right" width="200px">故障原因說明：</td>
			<td align="left" width="200px" colspan="3">
				<s:if test='%{fetclaimTypecm.apiStatus == "2"}'>
					<s:property value="fetclaimTypecm.causeDescription1"/>
				</s:if>
				<s:else>
					<s:textarea key="fetclaimTypecm.causeDescription1" id="causeDescription1" rows="5" cols="90"/>				
				</s:else>
			</td>
		</tr>
		<s:if test='%{fetclaimTypecm.apiStatus == "2"}'>
			<tr>
				<td align="right" width="200px">完修時間：</td>
				<td align="left" width="200px" >
					<s:textfield key="fetclaimTypecm.erDate" id="erDate" size="19" maxlength="30"/>
				</td>
				<td align="right" width="200px">維修站出件時間：</td>
				<td align="left">
					<s:textfield key="fetclaimTypecm.finishDate" id="finishDate" size="19" maxlength="30"/>
				</td>	
			</tr>	
		</s:if>
	</table>
	<table width="970px">
		<tr>
			<td align="center">
				<s:if test='%{fetclaimTypecm.apiStatus != "2"}'>
					<input value="API發送" type="button" onclick="javascript:form_submit('send');">&nbsp;&nbsp;&nbsp;&nbsp;
				</s:if>
				<s:if test='%{fetclaimTypecm.apiStatus == "2" && fetclaimTypecm.xmlStatus != "2"}'>
					<input value="XML上傳" type="button" onclick="javascript:form_submit('upload');">&nbsp;&nbsp;&nbsp;&nbsp;
				</s:if>
				<input value="取消" type="button" onclick="javascript:form_submit('clear');" />
			</td>
		</tr>
	</table>
</s:form>
</body>
</html>