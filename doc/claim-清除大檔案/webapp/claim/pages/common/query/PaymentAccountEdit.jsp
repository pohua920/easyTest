<%--
****************************************************************************
* DESC       ：賠款帳戶查詢頁面
* mantis：CLM0075 ，處理人員：BK007  蘇哲，需求單編號：CLM0075.理賠系統-修改或刪除已失效匯款帳戶
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<head>
<title>檢視賠款帳戶</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
<script language="javascript">
	  <%--案件状态标志处理--%>
	  function submitForm(){
	    fm.action = "${ctx}/ctbcins/paymentAccount/paymentAccountSave.do?editType=a";
	    fm.submit();//提交
	  }
	</script>
<style type="text/css">
TD.input {
	FONT-SIZE: 11pt;
	COLOR: #000000;
	BACKGROUND-COLOR: #F7F7F7;
	width: 15%
}
TD.title {
	FONT-SIZE: 11pt;
	COLOR: #000000;
	BACKGROUND-COLOR: #F7F7F7;
	width: 10%
}
</style>
</head>
<body onload="initPage();" class="yui-skin-sam">
	<form name="fm" action="${ctx}/ctbcins/paymentAccount/paymentAccountSave.do" method="post" onsubmit="return validateForm(this);">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=6 class="formtitle">
					檢視賠款帳戶
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="account.accountOwnershipCertificateType" />
				</td>
				<td class='input'>
					<s:select name="paymentAccount.certificateType" cssClass="input"  listKey="key" listValue="value" list="@com.sinosoft.claim.common.ConstantsCollection@prpdpaymentaccountCertificateTypeList" />
				</td>
				<td class='title'>
					<s:text name="account.accountOwnershipPersonCode" />
				</td>
				<td class='input'>
					<s:textfield name="paymentAccount.certificateCode" readonly="true" cssClass="input"/>
				</td>
				<td class='title'>
					<s:text name="db.prpLcompensate.account" />
					:
				</td>
				<td class='input'>
					<s:textfield name="paymentAccount.accountCode" readonly="true" cssClass="readonly"/>
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="compensate.accountCurrency" />
				</td>
				<td class='input'>
					<s:textfield name="paymentAccount.accountCurrency"  cssClass="input"/>
				</td>
				<td class='title'>
					<s:text name="compensate.accountCurrencyType" />
				</td>
				<td class='input'>
					<s:select list="#{'1':getText('compensate.passbook'),'2':getText('compensate.creditCard'),'3':getText('compensate.CARDS'),'4':getText('regist.prpLregist.other')}" name="paymentAccount.accountType" />
				</td>
				<td class='title'>
					<s:text name="account.headquartersCode" />
				</td>
				<td class='input'>
					<s:textfield name="paymentAccount.bankCode"  cssClass="input"/>
				</td>
			</tr>
			<tr>
				<td class='title'>
					總行名稱
				</td>
				<td class='input'>
					<s:textfield name="paymentAccount.bankName"  cssClass="input"/>
				</td>
				<td class='title'>
					分行代號
				</td>
				<td class='input'>
					<s:textfield name="paymentAccount.customBankCode"  cssClass="input"/>
				</td>
				<td class='title'>
					分行名稱
				</td>
				<td class='input'>
					<s:textfield name="paymentAccount.customBankName"  cssClass="input"/>
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="account.accountName" />
				</td>
				<td class='input'>
					<s:textfield name="paymentAccount.accountName"  cssClass="input"/>
				</td>
				<td class='title'>
					<s:text name="account.accountOwnershipAttribute" />
				</td>
				<td class='input'>
					<s:select list="#{'1':getText('account.personal'),'2':getText('account.enterprise')}" name="paymentAccount.ownerType" />
				</td>
				<td class='title'>
					帳戶歸屬/支付對象名稱
				</td>
				<td class='input'>
					<s:textfield name="paymentAccount.ownerName"  cssClass="input"/>
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="account.accountOwnershipPhoneNumber" />
				</td>
				<td class='input'>
					<s:textfield name="paymentAccount.ownerPhoneNo"  cssClass="input"/>
				</td>
				<td class='title'>
					<s:text name="account.firstCollectionDate" />
				</td>
				<td class='input'>
					<s:textfield name="paymentAccount.operateDate" readonly="true" cssClass="readonly"/>
				</td>
				<td class='title'>
					<s:text name="account.updateDate" />
				</td>
				<td class='input'>
					<s:textfield name="paymentAccount.updateDate" readonly="true" cssClass="readonly"/>
				</td>
			</tr>
			<tr>
				<td class='title'>
					郵遞區號
				</td>
				<td class='input'>
					<s:textfield name="paymentAccount.areaCode"  cssClass="input"/>
				</td>
				<td class='title'>
					郵政地址
				</td>
				<td class='input'>
					<s:textfield name="paymentAccount.courierAddress"  cssClass="input"/>
				</td>
				<td class='title'>
					<s:text name="referlaw.validity" />
				</td>
				<td class='input'>
					<s:select list="#{'1':getText('query.flagTrue'),'0':getText('query.flagFalse')}" name="paymentAccount.validStatus" />
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpDcompany.remark" />
				</td>
				<td class='input' colspan="5" style="width: 85%">
					<s:textfield name="paymentAccount.remark"  cssClass="input"/>
				</td>
			</tr>
			<tr>
				<td class='button' colspan="6">
					<input type="button" name=buttonSave class='button' value="<s:text name='form.save' />" onClick="submitForm('updateSave');"><%--保存--%>
				</td>
			</tr>
		</table>
<%-- 					<s:text name="account.maintenanceUnitCode" /> --%>
					<s:hidden name="paymentAccount.vehicleComCode"  cssClass="input"/>
<%-- 					<s:text name="db.prpDcustomer_Unit.customerCode" /> --%>
					<s:hidden name="paymentAccount.customerCode"  cssClass="input"/>
<%-- 					<s:text name="db.prpUserGrade.userCode" /> --%>
					<s:hidden name="paymentAccount.userCode"  cssClass="input"/>
<%-- 					<s:text name="account.operatorCode" /> --%>
					<s:hidden name="paymentAccount.operatorCode"  cssClass="input"/>
<%-- 					<s:text name="account.operationsPeople" /> --%>
					<s:hidden name="paymentAccount.operatorComCode"  cssClass="input"/>
<%-- 					<s:text name="account.operationsPeopleName" /> --%>
					<s:hidden name="paymentAccount.operatorName"  cssClass="input"/>
<%-- 					<s:text name="account.theProcedure" /> --%>
					<s:hidden name="paymentAccount.operateSys"  cssClass="input"/>
<%-- 					<s:text name="account.whetherUsedPaid" /> --%>
					<s:hidden name="paymentAccount.usedOrNot"  cssClass="input"/>
<!-- 					補償所有者名稱 -->
					<s:hidden name="paymentAccount.compensateOwnerName"  cssClass="input"/>
<!-- 					是否單位 -->
					<s:hidden name="paymentAccount.uniformNo"  cssClass="input"/>
		<c:if test="${showflg=='true'}">
			<script type="text/javascript">
	executeQuery(1,10);//删除机构後的回显
	</script>
		</c:if>
	</form>
</body>
</html>