<%@ page contentType="text/html; charset=GBK" %>
<html>
<head>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta_js.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<script language="JavaScript">
	javascript:window.history.forward(1);
</script>
<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
<script src="${ctx}/pages/common/account/js/paymentAccount.js"></script>
<script src="${ctx}/pages/DAA/compensate/js/autoBank.js"></script>
<script src="${ctx}/claim/common/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
$(window).unload(function() {
 	var objAML = window.opener.fm.prpLpayObjectInfoAMLFlag ;
 	if (typeof objAML == "object") {
 		var serialNo = fm.serialNo.value;
		var ownerName = window.opener.fm.prpLpayObjectInfoOwnerName[serialNo].value ;
		var certificateCode = window.opener.fm.prpLpayObjectInfoUniformNo[serialNo].value ;
		var amlFlag = window.opener.fm.prpLpayObjectInfoAMLFlag[serialNo].value ;
	 	if(ownerName != "" && certificateCode != "" && amlFlag == "") {
			alert("請重新執行輸入費用支付帳戶訊息, 耐心等待洗錢狀態回覆");
		}
 	}	
});

 var index_obj = 0;

 function submitFormComsate(index) {
 	index_obj = index;
 	var accountCode = document.getElementsByName("prpdpaymentaccountAccountCode")[index]; //银行帐号
 	var ownerName = document.getElementsByName("prpdpaymentaccountOwnerName")[index]; //支付對象帳戶名稱
 	var certificateCode = document.getElementsByName("prpdpaymentaccountCertificateCode")[index]; //帳號歸屬人證件代碼
 	var ownerPhoneNo = document.getElementsByName("prpdpaymentaccountOwnerPhoneNo")[index]; //帳戶歸屬人聯系電話
 	var areaCode = document.getElementsByName("prpdpaymentaccountAreaCode")[index]; //郵遞區號
 	var courierAddress = document.getElementsByName("prpdpaymentaccountCourierAddress")[index]; //郵政地址
 	var accountBankName = document.getElementsByName("prpdpaymentaccountBankName")[index]; //分行名稱
 	var accountCustomBankCode = document.getElementsByName("prpdpaymentaccountCustomBankCode")[index]; //分行代號
 	var accountCompensateOwnerName = document.getElementsByName("prpdpaymentaccountCompensateOwnerName")[index]; //賠付對象
 	var uniformNo = document.getElementsByName("prpdpaymentaccountUniformNo")[index].value; //统一编号
 	var certificateType = document.getElementsByName("prpdpaymentaccountCertificateType")[index]; //帳號歸屬人證件類型
 	var type = certificateType.options[certificateType.selectedIndex].value;

 	if (accountCode == null || trim(accountCode.value) == "") {
 		alert("銀行帳號不能爲空！");
 		return false;
 	}
 	if (ownerName == null || trim(ownerName.value) == "") {
 		alert("支付對象帳戶名稱不能爲空！");
 		return false;
 	}
 	if (certificateCode == null || trim(certificateCode.value) == "") {
 		alert("帳號歸屬人證件代碼不能爲空！");
 		return false;
 	}
 	if (ownerPhoneNo == null || trim(ownerPhoneNo.value) == "") {
 		alert("帳戶歸屬人聯系電話不能爲空！");
 		return false;
 	}
 	if (areaCode == null || trim(areaCode.value) == "") {
 		alert("郵遞區號不能爲空！");
 		return false;
 	}
 	if (accountCustomBankCode == null || trim(accountCustomBankCode.value) == "") {
 		alert("分行代號不能爲空！");
 		return false;
 	}
 	if (accountBankName == null || trim(accountBankName.value) == "") {
 		alert("分行名稱不能爲空！");
 		return false;
 	}
 	if (courierAddress == null || trim(courierAddress.value) == "") {
 		alert("郵政地址不能爲空！");
 		return false;
 	}
 	//if(accountCompensateOwnerName == null || trim(accountCompensateOwnerName.value)==""){
 	//	alert("賠付對象不能爲空！");
 	//	return false;
 	//}
 	//if(trim(uniformNo)==""){
 	//	alert("統一編號不能爲空！");
 	//	return false;
 	//}
 	//校验统一编号
 	//if(!(checkIdentifyNumber(uniformNo,"9") || checkUniformNo(uniformNo))){
 	//	alert("請輸入正確的統一編號");
 	//	return false;
 	//}
 	//校验身份证
 	if (type == "01") {
 		if (!checkIdentifyNumber(certificateCode.value, "9")) {
 			alert("請輸入正確的身份證號");
 			return false;
 		}
 	}
 	if (type == "02") {
 		if (!checkUniformNo(certificateCode.value)) {
 			alert("請輸入正確的統一編號");
 			return false;
 		}
 	}
 	$("input[name='buttonsave']").attr('disabled', 'true');
 	var prpdpaymentaccountBankCode = document.getElementsByName("prpdpaymentaccountBankCode")[index];
 	var bankCode = document.getElementsByName("bankCode")[index].value //bankCode 在这是总行名称  
 	var inputArgs = {
 		AccountCode: document.getElementsByName("prpdpaymentaccountAccountCode")[index].value,
 		AccountCurrency: document.getElementsByName("prpdpaymentaccountAccountCurrency")[index].value,
 		AccountType: document.getElementsByName("prpdpaymentaccountAccountType")[index].value,
 		AccountName: document.getElementsByName("prpdpaymentaccountAccountName")[index].value,
 		CustomerCode: document.getElementsByName("prpdpaymentaccountCustomerCode")[index].value,
 		UserCode: document.getElementsByName("prpdpaymentaccountUserCode")[index].value,
 		OwnerType: document.getElementsByName("prpdpaymentaccountOwnerType")[index].value,
 		OwnerName: document.getElementsByName("prpdpaymentaccountOwnerName")[index].value,
 		CertificateType: document.getElementsByName("prpdpaymentaccountCertificateType")[index].value,
 		CertificateCode: document.getElementsByName("prpdpaymentaccountCertificateCode")[index].value,
 		OwnerPhoneNo: document.getElementsByName("prpdpaymentaccountOwnerPhoneNo")[index].value,
 		OperatorCode: document.getElementsByName("prpdpaymentaccountOperatorCode")[index].value,
 		OperatorComcode: document.getElementsByName("prpdpaymentaccountOperatorComcode")[index].value,
 		OperatorName: document.getElementsByName("prpdpaymentaccountOperatorName")[index].value,
 		OperateDate: document.getElementsByName("prpdpaymentaccountOperateDate")[index].value,
 		UpdateDate: document.getElementsByName("prpdpaymentaccountUpdateDate")[index].value,
 		ValidStatus: document.getElementsByName("prpdpaymentaccountValidStatus")[index].value,
 		Remark: document.getElementsByName("prpdpaymentaccountRemark")[index].value,
 		BankCode: document.getElementsByName("prpdpaymentaccountBankCode")[index].value,
 		BankName: bankCode,
 		RegistNo: fm.registNo.value,
 		serialNo: fm.serialNo.value,
 		AreaCode: document.getElementsByName("prpdpaymentaccountAreaCode")[index].value,
 		CompensateOwnerName: document.getElementsByName("prpdpaymentaccountCompensateOwnerName")[index].value,
 		CourierAddress: document.getElementsByName("prpdpaymentaccountCourierAddress")[index].value,
 		CustomBankName: document.getElementsByName("prpdpaymentaccountBankName")[index].value,
 		CustomBankCode: document.getElementsByName("prpdpaymentaccountCustomBankCode")[index].value,
 		UniformNo: document.getElementsByName("prpdpaymentaccountUniformNo")[index].value
 	};
 	var isPaymentUpdate = document.getElementsByName("isPaymentUpdate")[index];
 	if (isPaymentUpdate.value == "0") {
 		backSaveAccountCompen_s();
 	} else {
 		uiAccountCodeAction.saveAccount(inputArgs, backSaveAccountCompen_s);
 	}
 }

 function backSaveAccountCompen_s() {
 	try {
 		var index = index_obj;
 		var prpdpaymentaccountBankCode = document.getElementsByName("prpdpaymentaccountBankCode")[index];
 		var bankCode = document.getElementsByName("bankCode")[index].value
 		//根据客制化需求，需要得到序列 
 		var serialNo = fm.serialNo.value;
 		window.opener.fm.prpLpayObjectInfoBankCode[serialNo].value = document.getElementsByName("prpdpaymentaccountBankCode")[index].value; //總行代號
 		window.opener.fm.prpLpayObjectInfoBankName[serialNo].value = bankCode; //總行名稱
 		window.opener.fm.prpLpayObjectInfoAccountCode[serialNo].value = document.getElementsByName("prpdpaymentaccountAccountCode")[index].value; //匯款帳號
 		window.opener.fm.prpLpayObjectInfoCustomBankName[serialNo].value = document.getElementsByName("prpdpaymentaccountBankName")[index].value; //分行名稱
 		window.opener.fm.prpLpayObjectInfoBeneficiaryPhone[serialNo].value = document.getElementsByName("prpdpaymentaccountOwnerPhoneNo")[index].value; //受款人電話,市內電話
 		window.opener.fm.prpLpayObjectInfoCustomBankCode[serialNo].value = document.getElementsByName("prpdpaymentaccountCustomBankCode")[index].value; //分行代码
 		window.opener.fm.prpLpayObjectInfoOwnerName[serialNo].value = document.getElementsByName("prpdpaymentaccountOwnerName")[index].value; //赔付对象
 		window.opener.fm.prpLpayObjectInfoUniformNo[serialNo].value = document.getElementsByName("prpdpaymentaccountCertificateCode")[index].value; //统一编号
 		window.opener.fm.prpLpayObjectInfoCourierAddress[serialNo].value = document.getElementsByName("prpdpaymentaccountCourierAddress")[index].value; //邮政地址
 		window.opener.fm.prpLpayObjectInfoAreaCode[serialNo].value = document.getElementsByName("prpdpaymentaccountAreaCode")[index].value; //邮政区号
 		window.opener.fm.prpLpayObjectInfoCertificateCode[serialNo].value = document.getElementsByName("prpdpaymentaccountCertificateType")[index].value; //帳号归属人证件类型
 		if (typeof window.opener.fm.prpLpayObjectInfoAMLFlag[serialNo] == "object") {
 			window.opener.fm.prpLpayObjectInfoAMLFlag[serialNo].value = getAMLResponse(index) ; //洗錢回覆字串
 		}	
 	} catch (e) {}
 	window.close();
 	return;
 }

 function setPayment(field) {
 	var index = getElementOrder(field) - 1;
 	var isPaymentUpdate = document.getElementsByName("isPaymentUpdate")[index];
 	if (field.tagName == "select") {
 		isPaymentUpdate.value = 1;
 	} else if (isPaymentUpdate.value == "0") {
 		if (field.value != field.title) {
 			isPaymentUpdate.value = 1;
 		}
 	}
 }
 
 function getAMLResponse(index) {
	//mantis： CLM0118 ，處理人員：BK007 蘇哲，需求單編號：CLM0118.新核心-AML新增提示訊息 -start
	 var start = 0;
	 var end = 0;
	 start = new Date().getTime();
	var certificateType = document.getElementsByName("prpdpaymentaccountCertificateType")[index].value ;
	var certificateCode = document.getElementsByName("prpdpaymentaccountCertificateCode")[index].value ;
	var ownerName = document.getElementsByName("prpdpaymentaccountOwnerName")[index].value ;
	//mantis：CLM0062 ，處理人員：BK007 蘇哲，需求單編號：CLM0062.AML換新的理賠新核心
	var urlString = encodeURI("${ctx}/AMLQueryServlet?type="+certificateType+"&code="+certificateCode+"&name="+encodeURIComponent(ownerName)+"&riskCode=<c:out value='${param.riskCode}'/>&registNo=<c:out value='${param.registNo}'/>" ) ;
	var htmlobj=$.ajax({url:urlString,async:false});
	var response = htmlobj.responseText ;
	end = new Date().getTime();
	if (response=="Y") {
		alert("洗錢檢測狀態:命中_"+(end - start) / 1000 + "sec") ;
	}else if (response=="M") {
		alert("洗錢檢測狀態:手動_"+(end - start) / 1000 + "sec") ;
	}else{
		alert("洗錢檢測狀態:未命中_"+(end - start) / 1000 + "sec") ;
	}
	//mantis： CLM0118 ，處理人員：BK007 蘇哲，需求單編號：CLM0118.新核心-AML新增提示訊息 -end
	return response ;	 
 }
 </script>
<title><s:text name="title.account.accountInformationList" /></title>
<!-- 帳户信息列表 -->
</head>
<body style="overflow: scroll">
	<form name="fm" action="${ctx}/AccountCode.do" method="post" autocomplete="off">
		<input type="hidden" name="actionType" value="<c:out value='${param.actionType}'/>">
		<input type="hidden" name="businessType" value="<c:out value='${param.businessType}'/>">
		<input type="hidden" name="registNo" value="<c:out value='${param.registNo}'/>">
		<input type="hidden" name="serialNo" value="<c:out value='${param.serialNo}'/>">
		<input type="hidden" name="comCodeForAcc" value="<c:out value='${sessionScope.user.comCode}'/>">
		<input type="hidden" name="userName" value="<c:out value='${sessionScope.user.userName}'/>">
		<input type="hidden" name="buttonAccCompensateIndex" value="<c:out value='${requestScope.buttonAccCompensateIndex}'/>">
		<input type="hidden" name="certificateCode" value="<c:out value='${param.certificateCode}'/>">
		<input type="hidden" name="uniformNo" value="<c:out value='${param.uniformNo}'/>">
		<input type="hidden" name="accountCode" value="<c:out value='${param.accountCode}'/>">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr align="center">
				<td>
					<input type="button" class="bigbutton" name="button" value="<s:text name="button.account.addAccountInformation"/>"
						onClick="paymentaccountAddCompensate();">
				</td>
				<!-- 新增帳户信息 -->
			</tr>
			<tr>
				<td bgcolor="black"></td>
			</tr>
		</table>
		<c:forEach items="${requestScope.PaymentAccounList}" var="prpdpaymentaccountDto" varStatus="stat">
			<input type="hidden" value="0" name="isPaymentUpdate">
			<table border="0" cellpadding="5" cellspacing="1" class="subtable">
				<tr>
					<td colspan="4" align="center" class="common">
						<strong><s:text name="account.accountInformation" />
							<!-- 帳户信息 --> <c:out value='${stat.index+1}' /> <s:text name="account.accountName" />
							<!--帳户名称：--> <c:out value="${prpdpaymentaccountDto.accountName}" /></strong>
					</td>
				</tr>
				<tr>
					<td width="20%" class="left">
						<s:text name="db.prpLcompensate.account" />
						:
					</td>
					<!-- 银行帐号： -->
					<td width="30%" class="right">
						<input type="text" class="readonly" onblur="setPayment(this);" name="prpdpaymentaccountAccountCode" title="${prpdpaymentaccountDto.accountCode}"
							value="<c:out value="${prpdpaymentaccountDto.accountCode}" />" readonly="readonly">
						<img src="/claim/images/imgMustInput.gif" />
						<input type="text" class="common" onblur="setPayment(this);" name="prpdpaymentaccountAccountCurrency" title="${prpdpaymentaccountDto.accountCurrency}"
							value="<c:out value="${prpdpaymentaccountDto.accountCurrency}" />">
					</td>
					<td width="20%" class="left">
						<s:text name="compensate.accountCurrencyType" />
						:
					</td>
					<!-- 帳户类型： -->
					<td width="30%" class="right">
						<select name="prpdpaymentaccountAccountType" class="common" onchange="setPayment(this);">
							<option value="1" <c:if test="${prpdpaymentaccountDto.accountType=='1'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.passbook" />
							</option>
							<!-- 存折 -->
							<option value="2" <c:if test="${prpdpaymentaccountDto.accountType=='2'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.creditCard" />
							</option>
							<!-- 信用卡 -->
							<option value="3" <c:if test="${prpdpaymentaccountDto.accountType=='3'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.CARDS" />
							</option>
							<!-- 储值卡 -->
							<option value="4" <c:if test="${prpdpaymentaccountDto.accountType=='4'}"><c:out value="selected"/></c:if>>
								<s:text name="regist.prpLregist.other" />
							</option>
							<!-- 其他 -->
						</select>
					</td>
				</tr>
				<tr>
					<td width="20%" class="left">
						<s:text name="account.headquartersCode" />
						<!-- 总行代码：  -->
					</td>
					<td width="30%" class="right">
						<input type="text" class="readonly" readonly name="prpdpaymentaccountBankCode" onkeyup="getBank(this,'codeCode','0,1','1');" onblur="setPayment(this);isBank(this,'codeCode','1');"
							value="${prpdpaymentaccountDto.bankCode}" />
					</td>
					<td width="20%" class="left">總行名稱：</td>
					<td width="30%" class="right">
						<input type="text" class="readonly" readonly name="bankCode" onchange="setPayment(this);" onblur="setPayment(this);" onkeyup="getBank(this,'codeName','-1,0','1');"
							title="${prpdpaymentaccountDto.bankName}" value="${prpdpaymentaccountDto.bankName}">
						<img src="/claim/images/imgMustInput.gif" />
					</td>
				</tr>
				<tr>
					<td width="20%" class="left">分行代號:</td>
					<!-- 分行代號： -->
					<td width="30%" class="right">
						<input type="text" class="common" id="prpdpaymentaccountCustomBankCode" name="prpdpaymentaccountCustomBankCode" onchange="setPayment(this);" maxlength="50"
							title="${prpdpaymentaccountDto.customBankCode}" value="<c:out value="${prpdpaymentaccountDto.customBankCode}" />" onkeyup="getBank(this,'codeCode','0,1,-2,-1','2');"
							onblur="isBank(this,'codeCode','2');">
						<img src="/claim/images/imgMustInput.gif" />
					</td>
					<td width="20%" class="left">分行名稱：</td>
					<!-- 分行名稱： -->
					<td width="30%" class="right">
						<input type="text" class="common" id="prpdpaymentaccountBankName" onchange="setPayment(this);" title="${prpdpaymentaccountDto.customBankName}" name="prpdpaymentaccountBankName" maxlength="50"
							onblur="isBank(this,'codeName','2');" value="<c:out value="${prpdpaymentaccountDto.customBankName}" />" onkeyup="getBank(this,'codeName','-1,0,-3,-2','2');">
						<img src="/claim/images/imgMustInput.gif" />
					</td>
				</tr>
				<tr>
					<td width="20%" class="left">
						<s:text name="account.accountName" />
					</td>
					<!-- 帳户名称： -->
					<td width="30%" class="right">
						<input type="text" class="common" name="prpdpaymentaccountAccountName" onblur="setPayment(this);" title="${prpdpaymentaccountDto.accountName}"
							value="<c:out value="${prpdpaymentaccountDto.accountName}" />">
					</td>
					<td width="20%" class="left">
						<s:text name="db.prpDcustomer_Unit.customerCode" />
						:
					</td>
					<!-- 客户代码： -->
					<td width="30%" class="right">
						<input type="text" class="common" name="prpdpaymentaccountCustomerCode" onblur="setPayment(this);" title="${prpdpaymentaccountDto.customerCode}"
							value="<c:out value="${prpdpaymentaccountDto.customerCode}" />">
					</td>
				</tr>
				<tr style='display: none'>
					<td width="20%" class="left">
						<s:text name="db.prpUserGrade.userCode" />
						:
					</td>
					<!-- 员工代码： -->
					<td width="30%" class="right">
						<input type="text" class="common" name="prpdpaymentaccountUserCode" onblur="setPayment(this);" title="${prpdpaymentaccountDto.userCode}"
							value="<c:out value="${prpdpaymentaccountDto.userCode}" />">
					</td>
					<td width="20%" class="left">
						<s:text name="account.maintenanceUnitCode" />
					</td>
					<!-- 维修单位代码： -->
					<td width="30%" class="right">
						<input type="text" class="common" name="prpdpaymentaccountVehicleComCode" onblur="setPayment(this);" title="${prpdpaymentaccountDto.vehicleComCode}"
							value="<c:out value="${prpdpaymentaccountDto.vehicleComCode}" />">
					</td>
				</tr>
				<tr>
					<td width="20%" class="left">
						<s:text name="account.accountOwnershipAttribute" />
					</td>
					<!-- 帳户归属人属性 ： -->
					<td width="30%" class="right">
						<select name="prpdpaymentaccountOwnerType" onchange="setPayment(this);" class="common">
							<option value="1" <c:if test="${prpdpaymentaccountDto.ownerType=='1'}"><c:out value="selected"/></c:if>>
								<s:text name="account.personal" />
							</option>
							<!-- 个人 -->
							<option value="2" <c:if test="${prpdpaymentaccountDto.ownerType=='2'}"><c:out value="selected"/></c:if>>
								<s:text name="account.enterprise" />
							</option>
							<!-- 企业 -->
						</select>
					</td>
					<td width="20%" class="left">
						<s:text name="account.accountOwnershipPersonName" />
					</td>
					<!-- 帳户归属人姓名(支付对象帳户名称)： -->
					<td width="30%" class="right">
						<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
						<input type="text" class="common" name="prpdpaymentaccountOwnerName" maxlength="100" onblur="setPayment(this);" title="${prpdpaymentaccountDto.ownerName}"
							value="<c:out value="${prpdpaymentaccountDto.ownerName}" />">
						<img src="/claim/images/imgMustInput.gif" />
					</td>
				</tr>
				<tr>
					<td width="20%" class="left">
						<s:text name="account.accountOwnershipCertificateType" />
					</td>
					<!-- 帳号归属人证件类型： -->
					<td width="30%" class="right">
						<s:select name="prpdpaymentaccountCertificateType" onchange="setPayment(this);" class="common" value="#attr.prpdpaymentaccountDto.certificateType" listKey="key" listValue="value"
							list="#request.prpdpaymentaccountCertificateTypeList" />
					</td>
					<td width="20%" class="left">
						<s:text name="account.accountOwnershipPersonCode" />
					</td>
					<!-- 帳号归属人证件代码： -->
					<td width="30%" class="right">
						<input type="text" class="common" name="prpdpaymentaccountCertificateCode" onblur="setPayment(this);" title="${prpdpaymentaccountDto.certificateCode}"
							value="<c:out value="${prpdpaymentaccountDto.certificateCode}" />">
						<img src="/claim/images/imgMustInput.gif" />
					</td>
				</tr>
				<tr>
					<td width="20%" class="left">
						<s:text name="account.accountOwnershipPhoneNumber" />
					</td>
					<!-- 帳户归属人联系电话： -->
					<td width="30%" class="right">
						<input type="text" class="common" name="prpdpaymentaccountOwnerPhoneNo" onblur="setPayment(this);" onblur="setPayment(this);" title="${prpdpaymentaccountDto.ownerPhoneNo}"
							value="<c:out value="${prpdpaymentaccountDto.ownerPhoneNo}" />">
						<img src="/claim/images/imgMustInput.gif" />
					</td>
					<td width="20%" class="left">
						<s:text name="account.operatorCode" />
					</td>
					<!-- 操作人员代码： -->
					<td width="30%" class="right">
						<input type="text" class="readonly" readonly name="prpdpaymentaccountOperatorCode" onblur="setPayment(this);" title="${prpdpaymentaccountDto.operatorCode}"
							value="<c:out value="${prpdpaymentaccountDto.operatorCode}" />">
					</td>
				</tr>
				<tr>
					<td width="20%" class="left">
						<s:text name="account.operationsPeople" />
					</td>
					<!-- 操作人归属机构： -->
					<td width="30%" class="right">
						<input type="text" class="readonly" readonly name="prpdpaymentaccountOperatorComcode" onblur="setPayment(this);" title="${prpdpaymentaccountDto.operatorComCode}"
							value="<c:out value="${prpdpaymentaccountDto.operatorComCode}" />">
					</td>
					<td width="20%" class="left">
						<s:text name="account.operationsPeopleName" />
					</td>
					<!-- 操作人员姓名： -->
					<td width="30%" class="right">
						<input type="text" class="readonly" readonly name="prpdpaymentaccountOperatorName" onblur="setPayment(this);" title="${prpdpaymentaccountDto.operatorName}"
							value="<c:out value="${prpdpaymentaccountDto.operatorName}" />">
					</td>
				</tr>
				<tr>
					<td width="20%" class="left">
						<s:text name="account.firstCollectionDate" />
					</td>
					<!-- 第一次采集日期： -->
					<td width="30%" class="right">
						<rc:rcDate class="readonly" wdatePicker="false" readonly="readonly" name="prpdpaymentaccountOperateDate" onblur="setPayment(this);" value="${prpdpaymentaccountDto.operateDate}"/>
					</td>
					<td width="20%" class="left">
						<s:text name="account.updateDate" />
					</td>
					<!-- 更新日期： -->
					<td width="30%" class="right">
	       				<rc:rcDate class="readonly" wdatePicker="false" readonly="readonly" name="prpdpaymentaccountUpdateDate" onblur="setPayment(this);" value="${prpdpaymentaccountDto.updateDate}"/>
					</td>
				</tr>
				<tr style='display: none'>
					<td width="20%" class="left">
						<s:text name="account.theProcedure" />
						<br>
						<br>
					</td>
					<!-- 采集环节： -->
					<td width="30%" class="right">
						<input type="text" class="hidden" name="prpdpaymentaccountOperateSys" onblur="setPayment(this);" title="${prpdpaymentaccountDto.operateSys}"
							value="<c:out value="${prpdpaymentaccountDto.operateSys}" />">
						<br>
						<br>
					</td>
					<td width="20%" class="left">
						<s:text name="account.whetherUsedPaid" />
						<br>
						<br>
					</td>
					<!-- 是否已经用於实收实付： -->
					<td width="30%" class="right">
						<input type="text" class="hidden" name="prpdpaymentaccountUsedOrNot" onblur="setPayment(this);" title="${prpdpaymentaccountDto.usedOrNot}"
							value="<c:out value="${prpdpaymentaccountDto.usedOrNot}" />">
						<br>
						<br>
					</td>
				</tr>
				<!-- <tr>
	  	    <td width="20%" class="left">賠付對象</td> -->
				<!-- 赔付对象： -->
				<!--<td width="30%" class="right"> -->
				<input type="hidden" class="common" name="prpdpaymentaccountCompensateOwnerName" onblur="setPayment(this);" title="${prpdpaymentaccountDto.compensateOwnerName}"
					value="<c:out value='${prpdpaymentaccountDto.compensateOwnerName}'/>">
				<!--<img src="/claim/images/imgMustInput.gif"/>
	    </td>
	    <td width="20%" class="left">統一編號:</td>  -->
				<!-- 统一编号 -->
				<!--<td width="30%" class="right">  -->
				<input type="hidden" class="common" name="prpdpaymentaccountUniformNo" onblur="setPayment(this);" title="${prpdpaymentaccountDto.uniformNo}"
					value="<c:out value='${prpdpaymentaccountDto.uniformNo}'/>">
				<!--<img src="/claim/images/imgMustInput.gif"/>
	    </td>
	  </tr>  -->
				<tr>
					<td width="20%" class="left">郵遞區號:</td>
					<!-- 邮政区号 -->
					<td width="30%" class="right">
						<input type="text" class='common' name="prpdpaymentaccountAreaCode" onblur="setPayment(this);" title="${prpdpaymentaccountDto.areaCode}"
							value="<c:out value='${prpdpaymentaccountDto.areaCode}'/>">
						<img src="/claim/images/imgMustInput.gif" />
					</td>
					<td width="20%" class="left">郵政地址:</td>
					<!-- 邮政地址 -->
					<td width="30%" class="right">
						<input type="text" class='common' name="prpdpaymentaccountCourierAddress" onblur="setPayment(this);" title="${prpdpaymentaccountDto.courierAddress}"
							value="<c:out value='${prpdpaymentaccountDto.courierAddress}'/>">
						<img src="/claim/images/imgMustInput.gif" />
					</td>
				</tr>
				<tr>
					<td width="20%" class="left">
						<s:text name="db.prpDcompany.remark" />
						:
					</td>
					<!-- 备注： -->
					<td width="80%" class="right" colspan="3">
						<input type="text" class="common" name="prpdpaymentaccountRemark" onblur="setPayment(this);" title="${prpdpaymentaccountDto.remark}" value="<c:out value="${prpdpaymentaccountDto.remark}" />">
						<input type="hidden" name="prpdpaymentaccountValidStatus" value="<c:out value="${prpdpaymentaccountDto.validStatus}" />">
					</td>
				</tr>
				<tr>
					<td align="center" class="common" colspan="4">
						<input type="button" class="bigbutton" name="buttonsave" value="確定" onClick="submitFormComsate('<c:out value='${stat.index}'/>')" ;/>
					</td>
				</tr>
				<tr>
					<td bgcolor="black" colspan="4"></td>
				</tr>
			</table>
			<br>
		</c:forEach>
		<div id="bankList" style="margin:0; padding:5px;border: #acacac 1px solid;background-color: FFFFFF;display: none; cursor: hand; position: absolute; width: 400px;overflow: auto" align="left"></div>
	</form>
</body>
</html>
