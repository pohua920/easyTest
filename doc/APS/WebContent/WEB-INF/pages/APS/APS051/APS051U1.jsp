<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "電信詐騙盜打維護作業";
	String image = path + "/" + "images/";
	String GAMID = "APS051U1";
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
			 
			 //$("#reinsInwardInsDataStr").val(JSON.stringify(insObj)); 
			 
			 $("#mainForm").attr("action","btnSend.action");
			 $("#mainForm").submit();
		 }
		 if('clear' == method){
				if (confirm("請確認是否放棄新增並返回查詢頁面？")==true){
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
				<s:property value="fetclaimTypecm.mtnNo"/>
			</td>
			<td align="right" width="200px">保險合約編號：</td>
			<td align="left">
				<s:property value="fetclaimTypecm.contractId"/>
			</td>	
		</tr>
		<tr>
			<td align="right" width="200px">IMEI：</td>
			<td align="left" width="200px" >
				<s:property value="fetclaimTypecm.imei"/>
			</td>
			<td align="right" width="200px"></td>
			<td align="left">
			</td>	
		</tr>
		<tr>
			<td align="right" width="200px">送修日期：</td>
			<td align="left" width="200px" colspan="3">
				<s:property value="fetclaimTypecm.notifDate"/>
			</td>
		</tr>
		<tr>
			<td align="right" width="200px">報修料號代碼：</td>
			<td align="left" width="200px" colspan="3">
				<s:property value="fetclaimTypecm.matnrNo"/>
			</td>
		</tr>
		<tr>
			<td align="right" width="200px">理賠類型：</td>
			<td align="left" width="200px" >
				<s:if test='fetclaimTypecm.claimType == "M"'>盜打</s:if>
				<s:if test='fetclaimTypecm.claimType == "C"'>詐騙</s:if>
			</td>
			<td align="right" width="200px">授權階段代號：</td>
			<td align="left">
				<s:if test='fetclaimTypecm.finalAuth == "N"'>API</s:if>
				<s:if test='fetclaimTypecm.finalAuth == "Y"'>XML</s:if>
			</td>	
		</tr>
		<tr>
			<td align="right" width="200px">事故日期：</td>
			<td align="left" width="200px" >
				<s:property value="fetclaimTypecm.eventDate"/>
			</td>
			<td align="right" width="200px">申請授權維修金額：</td>
			<td align="left">
				<s:property value="fetclaimTypecm.listprice1"/>
			</td>	
		</tr>
		<tr>
			<td align="right" width="200px">案件授權類別代號：</td>
			<td align="left" width="200px" >
				<s:if test='fetclaimTypecm.specialApproval == "N"'>一般送審件</s:if>
				<s:if test='fetclaimTypecm.specialApproval == "Y"'>特殊授權件</s:if>
			</td>
			<td align="right" width="200px">申請授權維修金額：</td>
			<td align="left">
				<s:property value="fetclaimTypecm.asuClaimAmount"/>
			</td>	
		</tr>
		<tr>
			<td align="right" width="200px">保險單代碼：</td>
			<td align="left" width="200px" >
				<s:property value="fetclaimTypecm.policyNo"/>
			</td>
			<td align="right" width="200px">賠款支付方式：</td>
			<td align="left">
				<s:if test='fetclaimTypecm.specialApproval == "B"'>匯款</s:if>
				<s:if test='fetclaimTypecm.specialApproval == "Q"'>支票</s:if>
			</td>	
		</tr>
		<tr>
			<td align="right" width="200px">受款人姓名：</td>
			<td align="left" width="200px" >
				<s:property value="fetclaimTypecm.partDescription1"/>
			</td>
			<td align="right" width="200px">受款人身分證號：</td>
			<td align="left">
				<s:property value="fetclaimTypecm.partDescription2"/>
			</td>	
		</tr>
		<tr>
			<td align="right" width="200px">受款人銀行代碼：</td>
			<td align="left" width="200px" >
				<s:property value="fetclaimTypecm.partDescription3"/>
			</td>
			<td align="right" width="200px">是否取消禁背：</td>
			<td align="left">
				<s:if test='fetclaimTypecm.partDescription9 == "Y"'>是-取消禁背</s:if>
				<s:if test='fetclaimTypecm.partDescription9 == "N"'>否-不取消禁背</s:if>
			</td>	
		</tr>
		<tr>
			<td align="right" width="200px">受款人分行代碼：</td>
			<td align="left" width="200px" >
				<s:property value="fetclaimTypecm.partDescription4"/>
			</td>
			<td align="right" width="200px">受款人銀行帳號：</td>
			<td align="left">
				<s:property value="fetclaimTypecm.partDescription5"/>
			</td>	
		</tr>
		<tr>
			<td align="right" width="200px">受款人郵遞區號：</td>
			<td align="left" width="200px" >
				<s:property value="fetclaimTypecm.partDescription6"/>
			</td>
			<td align="right" width="200px">受款人電話：</td>
			<td align="left">
				<s:property value="fetclaimTypecm.partDescription7"/>
			</td>	
		</tr>
		<tr>
			<td align="right" width="200px">受款人地址：</td>
			<td align="left" width="200px" colspan="3">
				<s:property value="fetclaimTypecm.partDescription8"/>
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
		<s:if test='%{fetclaimTypecm.xmlStatus == "2"}'>
			<tr>
				<td align="right" width="200px">完修時間：</td>
				<td align="left" width="200px" >
					<s:property value="fetclaimTypecm.erDate"/>
				</td>
				<td align="right" width="200px">維修站出件時間：</td>
				<td align="left">
					<s:property value="fetclaimTypecm.finishDate"/>
				</td>	
			</tr>	
		</s:if>
	</table>
</s:form>
</body>
</html>