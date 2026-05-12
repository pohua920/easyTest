function queryUser(field) {
	var fregistNo = fm.prpLregistExtRegistNo || fm.registno;
	var registNo = fregistNo.value;
	var btnName = field.name;
	var serialno = $(":input[name='" + btnName + "']").index($(field));
	var actionType = "queryUser";
	var friskCode = fm.riskcode || fm.riskCode;
	var riskcode = friskCode.value;
	var uniformNo = $(":input[name='prpLchargeUniformNo']").eq(serialno).val();
	var certificateCode = $(":input[name='prpLchargeCertificateCode']").eq(serialno).val();
	var accountCode= $(":input[name='prpLchargeAccountCode']").eq(serialno).val();
	var submitStr = "AccountCode.do?registNo=" + registNo + "&serialNo=" + serialno + "&actionType=" + actionType + "&certificateCode=" + certificateCode+ "&uniformNo=" + uniformNo+ "&accountCode=" + accountCode + "&riskCode="+riskcode;
	window.open(submitStr, "Print", "resizable=1,scrollbars=yes,overflow=scroll,width=980,height=600");
}
//由於特殊赔案部分没有客制化需求，不应改，故留下以前的js
//mantis：CLM0289，處理人員：DP0713，需求單編號：理賠DP自動化-開放總行代號及分行代號欄位
function BankEdit(field) {
	//var fregistNo = fm.prpLregistExtRegistNo || fm.registno;
	//var registNo = fregistNo.value;
	var btnName = field.name;
	var serialno = $(":input[name='" + btnName + "']").index($(field));
	var actionType = "BankEdit";//org:queryUser
	var accountCode= $(":input[name='prpLpayObjectInfoAccountCode']").eq(serialno).val();
	var bankCode= $(":input[name='prpLpayObjectInfoBankCode']").eq(serialno).val();
	var bankName= $(":input[name='prpLpayObjectInfoBankName']").eq(serialno).val();
	var customBankCode= $(":input[name='prpLpayObjectInfoCustomBankCode']").eq(serialno).val();
	var customBankName= $(":input[name='prpLpayObjectInfoCustomBankName']").eq(serialno).val();
	//var submitStr = "AccountCode.do?registNo=" + registNo + "&serialNo=" + serialno + "&actionType=" + actionType + "&certificateCode=" + certificateCode+ "&uniformNo=" + uniformNo+ "&accountCode=" + accountCode + "&riskCode="+riskcode;
	var submitStr = "AccountCode.do?serialNo=" + serialno + "&actionType=" + actionType 
	+ "&accountCode=" + accountCode 
	+ "&bankCode=" + bankCode
	+ "&bankName=" + bankName
	+ "&customBankCode=" + customBankCode
	+ "&customBankName=" + customBankName;
	window.open(submitStr, "Print", "resizable=1,scrollbars=yes,overflow=scroll,width=980,height=300");
}

function oldQueryUserCompensate() {
	var registnoList = document.getElementsByName("registno");
	var ownerNameList = document.getElementsByName("prpLCompensateOwnerName");
	var registNo = "";
	var ownerName = "";
	if (registnoList.length > 0) {
		registNo = registnoList[0].value;
	}
	if (registNo == "") {
		registNo = fm.prpLregistExtRegistNo.value;
	}
	if (ownerNameList.length > 0) {
		ownerName = ownerNameList[0].value;
	}

	var submitStr = "/claim/AccountCode.do?registNo=" + registNo + "&actionType=oldQueryUserCom&ownerName=" + ownerName;
	window.open(submitStr, "Print", "resizable=1,scrollbars=yes,overflow=scroll,width=980,height=600");
}

function queryUserCompensate(field) {
	var fregistNo = fm.prpLregistExtRegistNo || fm.registno;
	var registNo = fregistNo.value;
	var btnName = field.name;
	var serialno = $(":input[name='" + btnName + "']").index($(field));
	var friskCode = fm.riskcode || fm.riskCode;
	var riskcode = friskCode.value;
	var actionType = "queryUserCom";
	var uniformNo = $(":input[name='prpLpayObjectInfoUniformNo']").eq(serialno).val();
	var certificateCode = $(":input[name='prpLpayObjectInfoCertificateCode']").eq(serialno).val();
	var accountCode= $(":input[name='prpLpayObjectInfoAccountCode']").eq(serialno).val();
	var submitStr = "/claim/AccountCode.do?registNo=" + registNo + "&serialNo=" + serialno + "&actionType=" + actionType + "&certificateCode=" + certificateCode+ "&uniformNo=" + uniformNo+ "&accountCode=" + accountCode + "&riskCode="+riskcode;
	window.open(submitStr, "Print", "resizable=1,scrollbars=yes,overflow=scroll,width=980,height=600");
}

//控制页面不能录入信息
function inputLock(expname) {
	var inputs = fm.getElementsByTagName("input");
	for (var i = 0; i < inputs.length; i++) {
		if (inputs[i].name == expname) {
			continue;
		}
		inputs[i].disabled = true;
	}
	var selects = fm.getElementsByTagName("select");
	for (var i = 0; i < selects.length; i++) {
		if (inputs[i].name == expname) {
			continue;
		}
		selects[i].disabled = true;
	}

}
//控制页面为非只读
function inputUnLock(expname) {
	var inputs = fm.getElementsByTagName("input");
	for (var i = 0; i < inputs.length; i++) {
		if (inputs[i].name == expname) {
			continue;
		}
		inputs[i].disabled = false;
	}
	var selects = fm.getElementsByTagName("select");
	for (var i = 0; i < selects.length; i++) {
		if (inputs[i].name == expname) {
			continue;
		}
		selects[i].disabled = false;
	}

}

function checkAccountNo(accountNo,serialNo,registNo)
{
   if(accountNo==null || accountNo==""){
     alert("銀行帳號不能爲空!");
     window.location.reload();
     return;
   }
   //dwr
   uiAccountCodeAction.checkAccountNo(accountNo,serialNo,registNo,backCheckAccountNo);
}

function backCheckAccountNo(returnObject) {
	if (returnObject == null) {
		alert("該帳號在系統內不存在，請繼續輸入!");
		fm.prpdpaymentaccountOperatorComcode.value = fm.comCodeForAcc.value;
		fm.prpdpaymentaccountOperatorName.value = fm.userName.value;
		inputUnLock();
		fm.saveType.value = "new";
	//mantis：CLM0075 ，處理人員：BK007  蘇哲，需求單編號：CLM0075.理賠系統-修改或刪除已失效匯款帳戶 -start
	} else if(returnObject.validStatus == "0") {
		alert("此帳戶已失效，請洽維護人員確認及修改。");
		fm.prpdpaymentaccountAccountCode.value = "";
		fm.saveType.value = "";
		return;
	//mantis：CLM0075 ，處理人員：BK007  蘇哲，需求單編號：CLM0075.理賠系統-修改或刪除已失效匯款帳戶 -end
	} else {
		alert("您輸入的卡號在系統已經存在，系統將自動帶出！");
		fm.saveType.value = "old";
		inputUnLock();
		var prpdPaymentAccountDto = returnObject;
		var strAccountCode = prpdPaymentAccountDto.accountCode;
		try {
			fm.prpdpaymentaccountAccountCode.value = strAccountCode; //银行帳号
			fm.prpdpaymentaccountAccountCode.disabled = true;
			fm.prpdpaymentaccountAccountCurrency.value = prpdPaymentAccountDto.accountCurrency; //帳户币别     
			var objSelectAccountType = fm.prpdpaymentaccountAccountType; //帳户归属人属性
			for (var i = 0; i < objSelectAccountType.options.length; i++) {
				if (objSelectAccountType.options[i].value == prpdPaymentAccountDto.accountType) {
					objSelectAccountType.options[i].selected = true;
					fm.prpdpaymentaccountOwnerType.value = prpdPaymentAccountDto.accountType;
					break;
				}
			}
			fm.prpdpaymentaccountAccountName.value = prpdPaymentAccountDto.accountName; //帳户名称
			fm.prpdpaymentaccountCustomerCode.value = prpdPaymentAccountDto.customerCode; //客户代码
			fm.prpdpaymentaccountUserCode.value = prpdPaymentAccountDto.userCode //员工代码
			var objSelectOwnerType = fm.prpdpaymentaccountOwnerType; //帳户归属人属性
			for (var i = 0; i < objSelectOwnerType.options.length; i++) {
				if (objSelectOwnerType.options[i].value == prpdPaymentAccountDto.ownerType) {
					objSelectOwnerType.options[i].selected = true;
					fm.prpdpaymentaccountOwnerType.value = prpdPaymentAccountDto.ownerType;
					break;
				}
			}
			fm.prpdpaymentaccountOwnerName.value = prpdPaymentAccountDto.ownerName; //帳户归属人姓名
			var objSelectCertificateType = fm.prpdpaymentaccountCertificateType; //归属人证件类型
			for (var i = 0; i < objSelectCertificateType.options.length; i++) {
				if (objSelectCertificateType.options[i].value == prpdPaymentAccountDto.certificateType) {
					objSelectCertificateType.options[i].selected = true;
					fm.prpdpaymentaccountCertificateType.value = prpdPaymentAccountDto.certificateType;
					break;
				}
			}
			fm.prpdpaymentaccountCertificateCode.value = prpdPaymentAccountDto.certificateCode; //帳号归属人证件代码
			fm.prpdpaymentaccountOwnerPhoneNo.value = prpdPaymentAccountDto.ownerPhoneNo; //帳户归属人联系电话
			fm.prpdpaymentaccountOperatorCode.value = prpdPaymentAccountDto.operatorCode; //操作人员代码
			fm.prpdpaymentaccountOperatorComcode.value = prpdPaymentAccountDto.operatorComCode; //操作人员归属机构
			fm.prpdpaymentaccountOperatorName.value = prpdPaymentAccountDto.operatorName; //操作人员姓名
			var strOperateDate = DateUtil.Format("yyyy-MM-dd hh:mm:ss", prpdPaymentAccountDto.operateDate);
			fm.prpdpaymentaccountOperateDate.value = strOperateDate; //第一次采集时间
			var strUpdateDate = DateUtil.Format("yyyy-MM-dd hh:mm:ss", prpdPaymentAccountDto.updateDate);
			fm.prpdpaymentaccountUpdateDate.value = strUpdateDate; //更新日期
			fm.prpdpaymentaccountValidStatus.value = prpdPaymentAccountDto.validStatus;
			fm.prpdpaymentaccountRemark.value = prpdPaymentAccountDto.remark;
			fm.prpdpaymentaccountBankName.value = prpdPaymentAccountDto.bankName;
			fm.prpdpaymentaccountBankCode.value = prpdPaymentAccountDto.bankCode;
			fm.prpdpaymentaccountCustomBankName.value = prpdPaymentAccountDto.customBankName;
			fm.prpdpaymentaccountCustomBankCode.value = prpdPaymentAccountDto.customBankCode;
			fm.prpdpaymentaccountAreaCode.value = prpdPaymentAccountDto.areaCode;
			fm.prpdpaymentaccountCompensateOwnerName.value = prpdPaymentAccountDto.compensateOwnerName;
			fm.prpdpaymentaccountCourierAddress.value = prpdPaymentAccountDto.courierAddress;
			fm.prpdpaymentaccountUniformNo.value = prpdPaymentAccountDto.uniformNo;

			fm.buttonSubmit.disabled = false;
		} catch (e) {}
		return;
	}
}

function clickable() {
	fm.queryButton.disabled = false;
}

function LiWai() {
	if (confirm("该赔款计算书需经理赔部门负责人核签、分管副总或以上职务人员核批後方能生效!")) {
		fm.codeLevel.disabled = false;
		fm.prpdpaymentaccountOwnerName.readOnly = false;
		fm.prpdpaymentaccountOwnerName.className = "common";
		fm.prpdpaymentaccountGd2.readOnly = false;
		fm.prpdpaymentaccountGd2.className = "common";
	}
}
//新增帳户信息

function paymentaccountAdd() {
	fm.actionType.value = "AccountAdd";
	fm.action = "/claim/AccountCode.do";
	fm.target = "_self";
	fm.submit();
}

function paymentaccountAddCompensate() {
	fm.actionType.value = "AccountAddCompensate";
	fm.action = "/claim/AccountCode.do";
	fm.target = "_self";
	fm.submit();
}
/**离开域时检查空**/
function hasValue(Field) {
	if (Field.value == "")
		return false;
	else
		return true;
}

function submitPaymentaccount() {
	if (!hasValue(fm.prpdpaymentaccountAccountCode)) {
		alert("銀行帳號不能爲空！");
		return false;
	}
	if (!hasValue(fm.prpdpaymentaccountBankName)) {
		alert("開戶銀行不能爲空！");
		return false;
	}
	if (!hasValue(fm.prpdpaymentaccountOwnerName)) {
		alert("支付對象帳戶名稱不能爲空！");
		return false;
	}
	if (!hasValue(fm.prpdpaymentaccountCertificateCode)) {
		alert("帳號歸屬人證件代碼不能爲空！");
		return false;
	}
	if (!hasValue(fm.prpdpaymentaccountOwnerPhoneNo)) {
		alert("帳戶歸屬人聯系電話不能爲空！");
		return false;
	}
	if (!hasValue(fm.prpdpaymentaccountAreaCode)) {
		alert("郵遞區號不能爲空！");
		return false;
	}
	if (!hasValue(fm.prpdpaymentaccountCourierAddress)) {
		alert("郵政地址不能爲空！");
		return false;
	}
	if (!hasValue(fm.prpdpaymentaccountCustomBankCode)) {
		alert("分行代號不能爲空！");
		return false;
	}
	if (!hasValue(fm.prpdpaymentaccountCustomBankName)) {
		alert("分行名稱不能爲空！");
		return false;
	}
	if (fm.prpdpaymentaccountCertificateType.options[fm.prpdpaymentaccountCertificateType.selectedIndex].value == "01") {
		if (!checkIdentifyNumber(fm.prpdpaymentaccountCertificateCode.value, "9")) {
			alert("請輸入正確的身份證號");
			return false;
		}
	}
	if (fm.prpdpaymentaccountCertificateType.options[fm.prpdpaymentaccountCertificateType.selectedIndex].value == "02") {
		if (!checkUniformNo(fm.prpdpaymentaccountCertificateCode.value)) {
			alert("請輸入正確的統一編號");
			return false;
		}
	}
	var comType = fm.comType.value;

	var serialNo = fm.serialNo.value;
	var inputArgs = {
		AccountCode: fm.prpdpaymentaccountAccountCode.value,
		AccountCurrency: fm.prpdpaymentaccountAccountCurrency.value,
		AccountType: fm.prpdpaymentaccountAccountType.value,
		AccountName: fm.prpdpaymentaccountAccountName.value,
		CustomerCode: fm.prpdpaymentaccountCustomerCode.value,
		UserCode: fm.prpdpaymentaccountUserCode.value,
		OwnerType: fm.prpdpaymentaccountOwnerType.value,
		OwnerName: fm.prpdpaymentaccountOwnerName.value,
		CertificateType: fm.prpdpaymentaccountCertificateType.value,
		CertificateCode: fm.prpdpaymentaccountCertificateCode.value,
		OwnerPhoneNo: fm.prpdpaymentaccountOwnerPhoneNo.value,
		OperatorCode: fm.prpdpaymentaccountOperatorCode.value,
		OperatorComcode: fm.prpdpaymentaccountOperatorComcode.value,
		OperatorName: fm.prpdpaymentaccountOperatorName.value,
		OperateDate: fm.prpdpaymentaccountOperateDate.value,
		UpdateDate: fm.prpdpaymentaccountUpdateDate.value,
		ValidStatus: fm.prpdpaymentaccountValidStatus.value,
		Remark: fm.prpdpaymentaccountRemark.value,
		BankCode: fm.prpdpaymentaccountBankCode.value,
		BankName: fm.prpdpaymentaccountBankName.value,
		RegistNo: fm.registNo.value,
		serialNo: fm.serialNo.value,
		AreaCode: fm.prpdpaymentaccountAreaCode.value,
		CompensateOwnerName: fm.prpdpaymentaccountCompensateOwnerName.value,
		CourierAddress: fm.prpdpaymentaccountCourierAddress.value,
		CustomBankName: fm.prpdpaymentaccountCustomBankName.value,
		CustomBankCode: fm.prpdpaymentaccountCustomBankCode.value,
		UniformNo: fm.prpdpaymentaccountUniformNo.value
	};
//	var param = DWRUtil.getValues(inputArgs);
	uiAccountCodeAction.saveAccount(inputArgs, backSaveAccount);
}

function submitPaymentaccountCompen() {
	if (!hasValue(fm.prpdpaymentaccountAccountCode)) {
		alert("银行帐号不能为空！");
		return false;
	}
	if (!hasValue(fm.prpdpaymentaccountBankName)) {
		alert("開戶銀行不能爲空！");
		return false;
	}
	if (!hasValue(fm.prpdpaymentaccountOwnerName)) {
		alert("支付對象帳戶名稱不能爲空！");
		return false;
	}
	if (!hasValue(fm.prpdpaymentaccountCertificateCode)) {
		alert("帳號歸屬人證件代碼不能爲空！");
		return false;
	}
	if (!hasValue(fm.prpdpaymentaccountOwnerPhoneNo)) {
		alert("帳戶歸屬人聯系電話不能爲空！");
		return false;
	}
	if (!hasValue(fm.prpdpaymentaccountCustomBankName)) {
		alert("分行名稱不能爲空！");
		return false;
	}
	if (!hasValue(fm.prpdpaymentaccountCustomBankCode)) {
		alert("分行代號不能爲空！");
		return false;
	}
	if (!hasValue(fm.prpdpaymentaccountAreaCode)) {
		alert("郵遞區號不能爲空！");
		return false;
	}
	if (!hasValue(fm.prpdpaymentaccountCourierAddress)) {
		alert("郵政地址不能爲空！");
		return false;
	}
	if (fm.prpdpaymentaccountCertificateType.options[fm.prpdpaymentaccountCertificateType.selectedIndex].value == "01") {
		if (!checkIdentifyNumber(fm.prpdpaymentaccountCertificateCode.value, "9")) {
			alert("請輸入正確的身份證號");
			return false;
		}
	}
	if (fm.prpdpaymentaccountCertificateType.options[fm.prpdpaymentaccountCertificateType.selectedIndex].value == "02") {
		if (!checkUniformNo(fm.prpdpaymentaccountCertificateCode.value)) {
			alert("請輸入正確的統一編號");
			return false;
		}
	}
	var comType = fm.comType.value;

	var serialNo = fm.serialNo.value;
	var inputArgs = {
		AccountCode: fm.prpdpaymentaccountAccountCode.value,
		AccountCurrency: fm.prpdpaymentaccountAccountCurrency.value,
		AccountType: fm.prpdpaymentaccountAccountType.value,
		AccountName: fm.prpdpaymentaccountAccountName.value,
		CustomerCode: fm.prpdpaymentaccountCustomerCode.value,
		UserCode: fm.prpdpaymentaccountUserCode.value,
		OwnerType: fm.prpdpaymentaccountOwnerType.value,
		OwnerName: fm.prpdpaymentaccountOwnerName.value,
		CertificateType: fm.prpdpaymentaccountCertificateType.value,
		CertificateCode: fm.prpdpaymentaccountCertificateCode.value,
		OwnerPhoneNo: fm.prpdpaymentaccountOwnerPhoneNo.value,
		OperatorCode: fm.prpdpaymentaccountOperatorCode.value,
		OperatorComcode: fm.prpdpaymentaccountOperatorComcode.value,
		OperatorName: fm.prpdpaymentaccountOperatorName.value,
		OperateDate: fm.prpdpaymentaccountOperateDate.value,
		UpdateDate: fm.prpdpaymentaccountUpdateDate.value,
		ValidStatus: fm.prpdpaymentaccountValidStatus.value,
		Remark: fm.prpdpaymentaccountRemark.value,
		BankCode: fm.prpdpaymentaccountBankCode.value,
		BankName: fm.prpdpaymentaccountBankName.value,
		RegistNo: fm.registNo.value,
		serialNo: fm.serialNo.value,
		AreaCode: fm.prpdpaymentaccountAreaCode.value,
		CompensateOwnerName: fm.prpdpaymentaccountCompensateOwnerName.value,
		CourierAddress: fm.prpdpaymentaccountCourierAddress.value,
		CustomBankName: fm.prpdpaymentaccountCustomBankName.value,
		CustomBankCode: fm.prpdpaymentaccountCustomBankCode.value,
		UniformNo: fm.prpdpaymentaccountUniformNo.value
	};
	uiAccountCodeAction.saveAccount(inputArgs, backSaveAccountCompen);
}

function backSaveAccount(returnObject) {
	try {
		var prpdPaymentAccountDto = returnObject;
		var serialNo = parseInt(prpdPaymentAccountDto.serialNo);
		window.opener.fm.all("prpLchargeAccountCode")[serialNo].value = prpdPaymentAccountDto.accountCode; //银行帳号
		window.opener.fm.all("prpLchargeBankCode")[serialNo].value = prpdPaymentAccountDto.bankCode; //总行代码
		window.opener.fm.all("prpLchargeBankName")[serialNo].value = prpdPaymentAccountDto.bankName; //总行名称
		window.opener.fm.all("prpLchargeAreaCode")[serialNo].value = prpdPaymentAccountDto.areaCode; //郵遞區號
		window.opener.fm.all("prpLchargeOwnerName")[serialNo].value = prpdPaymentAccountDto.ownerName; //賠付對象
		window.opener.fm.all("prpLchargeCourierAddress")[serialNo].value = prpdPaymentAccountDto.courierAddress; //郵遞地址
		window.opener.fm.all("prpLchargeCustomBankName")[serialNo].value = prpdPaymentAccountDto.customBankName; //分行名稱
		window.opener.fm.all("prpLchargeCustomBankCode")[serialNo].value = prpdPaymentAccountDto.customBankCode; //分行代號
		window.opener.fm.all("prpLchargeUniformNo")[serialNo].value = prpdPaymentAccountDto.certificateCode; //統一編號
		window.opener.fm.all("prpLchargeCertificateCode")[serialNo].value = prpdPaymentAccountDto.certificateType; //證件類型
	} catch (e) {}
	window.close();
	return;
}


function backSaveAccountCompen(returnObject) {
	try {
		var prpdPaymentAccountDto = returnObject;
		var serialNo = parseInt(prpdPaymentAccountDto.serialNo);
		window.opener.fm.all("prpLpayObjectInfoAccountCode")[serialNo].value = prpdPaymentAccountDto.accountCode;
		window.opener.fm.all("prpLpayObjectInfoBankCode")[serialNo].value = prpdPaymentAccountDto.bankCode;
		window.opener.fm.all("prpLpayObjectInfoBankName")[serialNo].value = prpdPaymentAccountDto.bankName;
		window.opener.fm.all("prpLpayObjectInfoCustomBankCode")[serialNo].value = prpdPaymentAccountDto.customBankCode;
		window.opener.fm.all("prpLpayObjectInfoCustomBankName")[serialNo].value = prpdPaymentAccountDto.customBankName;
		window.opener.fm.all("prpLpayObjectInfoUniformNo")[serialNo].value = prpdPaymentAccountDto.certificateCode;
		window.opener.fm.all("prpLpayObjectInfoAreaCode")[serialNo].value = prpdPaymentAccountDto.areaCode;
		window.opener.fm.all("prpLpayObjectInfoOwnerName")[serialNo].value = prpdPaymentAccountDto.ownerName;
		window.opener.fm.all("prpLpayObjectInfoCourierAddress")[serialNo].value = prpdPaymentAccountDto.courierAddress;
		window.opener.fm.all("prpLpayObjectInfoBeneficiaryPhone")[serialNo].value = prpdPaymentAccountDto.ownerPhoneNo;
		window.opener.fm.all("prpLpayObjectInfoCertificateCode")[serialNo].value = prpdPaymentAccountDto.certificateType; //證件類型
	} catch (e) {}
	window.close();
	return;
}

function DateUtil() {}
DateUtil.Format = function (fmtCode, date) {
	var result, d, arr_d;

	var patrn_now_1 = /^y{4}-M{2}-d{2}\sh{2}:m{2}:s{2}$/;
	var patrn_now_11 = /^y{4}-M{1,2}-d{1,2}\sh{1,2}:m{1,2}:s{1,2}$/;

	var patrn_now_2 = /^y{4}\/M{2}\/d{2}\sh{2}:m{2}:s{2}$/;
	var patrn_now_22 = /^y{4}\/M{1,2}\/d{1,2}\sh{1,2}:m{1,2}:s{1,2}$/;

	var patrn_now_3 = /^y{4}年M{2}月d{2}日\sh{2}时m{2}分s{2}秒$/;
	var patrn_now_33 = /^y{4}年M{1,2}月d{1,2}日\sh{1,2}时m{1,2}分s{1,2}秒$/;

	var patrn_date_1 = /^y{4}-M{2}-d{2}$/;
	var patrn_date_11 = /^y{4}-M{1,2}-d{1,2}$/;

	var patrn_date_2 = /^y{4}\/M{2}\/d{2}$/;
	var patrn_date_22 = /^y{4}\/M{1,2}\/d{1,2}$/;

	var patrn_date_3 = /^y{4}年M{2}月d{2}日$/;
	var patrn_date_33 = /^y{4}年M{1,2}月d{1,2}日$/;

	var patrn_time_1 = /^h{2}:m{2}:s{2}$/;
	var patrn_time_11 = /^h{1,2}:m{1,2}:s{1,2}$/;
	var patrn_time_2 = /^h{2}时m{2}分s{2}秒$/;
	var patrn_time_22 = /^h{1,2}时m{1,2}分s{1,2}秒$/;

	if (!fmtCode) {
		fmtCode = "yyyy/MM/dd hh:mm:ss";
	}
	if (date) {
		d = new Date(date);
		if (isNaN(d)) {
			msgBox("时间参数非法\n正确的时间示例:\nThu Nov 9 20:30:37 UTC+0800 2006\n或\n2006/       10/17");
			return;
		}
	} else {
		d = new Date();
	}
	if (patrn_now_1.test(fmtCode)) {
		arr_d = splitDate(d, true);
		result = arr_d.yyyy + "-" + arr_d.MM + "-" + arr_d.dd + " " + arr_d.hh + ":" + arr_d.mm + ":" + arr_d.ss;
	} else if (patrn_now_11.test(fmtCode)) {
		arr_d = splitDate(d);
		result = arr_d.yyyy + "-" + arr_d.MM + "-" + arr_d.dd + " " + arr_d.hh + ":" + arr_d.mm + ":" + arr_d.ss;
	} else if (patrn_now_2.test(fmtCode)) {
		arr_d = splitDate(d, true);
		result = arr_d.yyyy + "/" + arr_d.MM + "/" + arr_d.dd + " " + arr_d.hh + ":" + arr_d.mm + ":" + arr_d.ss;
	} else if (patrn_now_22.test(fmtCode)) {
		arr_d = splitDate(d);
		result = arr_d.yyyy + "/" + arr_d.MM + "/" + arr_d.dd + " " + arr_d.hh + ":" + arr_d.mm + ":" + arr_d.ss;
	} else if (patrn_now_3.test(fmtCode)) {
		arr_d = splitDate(d, true);
		result = arr_d.yyyy + "年" + arr_d.MM + "月" + arr_d.dd + "日" + " " + arr_d.hh + "时" + arr_d.mm + "分" + arr_d.ss + "秒";
	} else if (patrn_now_33.test(fmtCode)) {
		arr_d = splitDate(d);
		result = arr_d.yyyy + "年" + arr_d.MM + "月" + arr_d.dd + "日" + " " + arr_d.hh + "时" + arr_d.mm + "分" + arr_d.ss + "秒";
	} else if (patrn_date_1.test(fmtCode)) {
		arr_d = splitDate(d, true);
		result = arr_d.yyyy + "-" + arr_d.MM + "-" + arr_d.dd;
	} else if (patrn_date_11.test(fmtCode)) {
		arr_d = splitDate(d);
		result = arr_d.yyyy + "-" + arr_d.MM + "-" + arr_d.dd;
	} else if (patrn_date_2.test(fmtCode)) {
		arr_d = splitDate(d, true);
		result = arr_d.yyyy + "/" + arr_d.MM + "/" + arr_d.dd;
	} else if (patrn_date_22.test(fmtCode)) {
		arr_d = splitDate(d);
		result = arr_d.yyyy + "/" + arr_d.MM + "/" + arr_d.dd;
	} else if (patrn_date_3.test(fmtCode)) {
		arr_d = splitDate(d, true);
		result = arr_d.yyyy + "年" + arr_d.MM + "月" + arr_d.dd + "日";
	} else if (patrn_date_33.test(fmtCode)) {
		arr_d = splitDate(d);
		result = arr_d.yyyy + "年" + arr_d.MM + "月" + arr_d.dd + "日";
	} else if (patrn_time_1.test(fmtCode)) {
		arr_d = splitDate(d, true);
		result = arr_d.hh + ":" + arr_d.mm + ":" + arr_d.ss;
	} else if (patrn_time_11.test(fmtCode)) {
		arr_d = splitDate(d);
		result = arr_d.hh + ":" + arr_d.mm + ":" + arr_d.ss;
	} else if (patrn_time_2.test(fmtCode)) {
		arr_d = splitDate(d, true);
		result = arr_d.hh + "时" + arr_d.mm + "分" + arr_d.ss + "秒";
	} else if (patrn_time_22.test(fmtCode)) {
		arr_d = splitDate(d);
		result = arr_d.hh + "时" + arr_d.mm + "分" + arr_d.ss + "秒";
	} else {
		msgBox("没有匹配的时间格式!");
		return;
	}
	return result;
};

function splitDate(d, isZero) {
	var yyyy, MM, dd, hh, mm, ss;
	if (isZero) {
		yyyy = d.getYear();
		MM = (d.getMonth() + 1) < 10 ? "0" + (d.getMonth() + 1) : d.getMonth() + 1;
		dd = d.getDate() < 10 ? "0" + d.getDate() : d.getDate();
		hh = d.getHours() < 10 ? "0" + d.getHours() : d.getHours();
		mm = d.getMinutes() < 10 ? "0" + d.getMinutes() : d.getMinutes();
		ss = d.getSeconds() < 10 ? "0" + d.getSeconds() : d.getSeconds();
	} else {
		yyyy = d.getYear();
		MM = d.getMonth() + 1;
		dd = d.getDate();
		hh = d.getHours();
		mm = d.getMinutes();
		ss = d.getSeconds();
	}
	return {
		"yyyy": yyyy,
		"MM": MM,
		"dd": dd,
		"hh": hh,
		"mm": mm,
		"ss": ss
	};
}

function msgBox(msg) {
	window.alert(msg);
}

function queryByCertificateCode(){
	var certificateType = $("#prpdpaymentaccountCertificateType").val();
	var certificateCode = $("#prpdpaymentaccountCertificateCode").val();
	if($.trim(certificateCode).length == 0){
		alert("查詢請輸入帳號歸屬人證件代碼！");
		return false;
	}
	if(certificateType == "01" ){//身份證號碼
		if(!checkIdentifyNumber(certificateCode , "9")){
			alert("請輸入正確的身份證號！");
			return false;
		}
	} else if(certificateType == "02") {//統一編號
		if(!checkUniformNo(certificateCode)){
			alert("請輸入正確的統一編號！");
			return false;
		}
	}
	window.open("AccountCode.do?actionType=SearchWithOwnerName&certificateType=" + certificateType + "&certificateCode=" + certificateCode ,"Print2","resizable=1,scrollbars=yes,overflow=scroll,width=600,height=600");
	return true;
}