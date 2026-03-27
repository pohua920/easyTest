<%@ page contentType="text/html; charset=GBK"%>
<html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="com.sinosoft.claim.common.ConstantCodes"%>
<!-- mantis：CLM0289，處理人員：DP0713，需求單編號：理賠DP自動化-開放總行代號及分行代號欄位 -->
<script language="JavaScript">
	javascript:window.history.forward(1);
	
function submitBank() {
	if (!hasValue(fm.prpdpaymentaccountBankName)) {
		alert("總行名稱不能爲空！");
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
	
	backSaveBankInfo();
}

function backSaveBankInfo() {
	try {
		var serialNo = fm.serialNo.value;
		//alert(fm.prpLpayObjectInfoCustomBankCode.value);
		if(undefined!=window.opener.fm.all("prpLpayObjectInfoCustomBankCode")[serialNo]){
			//alert(window.opener.fm.all("prpLpayObjectInfoCustomBankCode")[serialNo].value);
			window.opener.fm.all("prpLpayObjectInfoBankCode")[serialNo].value = fm.prpdpaymentaccountBankCode.value; //总行代码
			window.opener.fm.all("prpLpayObjectInfoBankName")[serialNo].value = fm.prpdpaymentaccountBankName.value; //总行名称
			window.opener.fm.all("prpLpayObjectInfoCustomBankName")[serialNo].value = fm.prpdpaymentaccountCustomBankName.value; //分行名稱
			window.opener.fm.all("prpLpayObjectInfoCustomBankCode")[serialNo].value = fm.prpdpaymentaccountCustomBankCode.value; //分行代號
		}else{
			//alert(window.opener.fm.all("prpLpayObjectInfoCustomBankCode").value);
			window.opener.fm.all("prpLpayObjectInfoBankCode").value = fm.prpdpaymentaccountBankCode.value; //总行代码
			window.opener.fm.all("prpLpayObjectInfoBankName").value = fm.prpdpaymentaccountBankName.value; //总行名称
			window.opener.fm.all("prpLpayObjectInfoCustomBankName").value = fm.prpdpaymentaccountCustomBankName.value; //分行名稱
			window.opener.fm.all("prpLpayObjectInfoCustomBankCode").value = fm.prpdpaymentaccountCustomBankCode.value; //分行代號
		}
	} catch (e) {}
	window.close();
	return;
}
</script>
<%@ include file="/common/meta_js.jsp"%>
<script>
	</script>
<head>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script src="${ctx}/pages/common/account/js/paymentAccount.js"></script>
<script src="${ctx}/pages/DAA/compensate/js/autoBank.js"></script>
<title><s:text name="account.payObject" /></title>
<!-- 支付对象 -->
</head>
<body style="overflow: hidden">
	<form name="fm" method="post" autocomplete="off">
		<input type="hidden" name="actionType" value="<c:out value='${param.actionType}'/>">
		<input type="hidden" name="serialNo" value="<c:out value='${param.serialNo}'/>">
		<table border="0" cellpadding="5" cellspacing="1" class="subtable">
			<tr>
				<td width="20%" class="left">
					<s:text name="account.headquartersCode" />
					<!-- 总行代码： -->
				</td>
				<td width="30%" class="right">
					<input name="prpdpaymentaccountBankCode" type="text" class="readonly" readonly onkeyup="getBank(this,'codeCode','0,1','1');" onblur="isBank(this,'codeCode','1');"
						value='${param.bankCode}' />
					<img src="/claim/images/imgMustInput.gif" />
				</td>
				<td width="20%" class="left">總行名稱：</td>
				<td width="30%" class="right">
					<input type="text" class="readonly" readonly name="prpdpaymentaccountBankName" onkeyup="getBank(this,'codeName','-1,0','1');" value='${param.bankName}' />
					<img src="/claim/images/imgMustInput.gif" />
				</td>
			</tr>
			<tr>
				<td width="20%" class="left">分行代號:</td>
				<!-- 分行代號： -->
				<td width="30%" class="right">
					<input type="text" class="input" id="prpdpaymentaccountCustomBankCode" name="prpdpaymentaccountCustomBankCode" maxlength="10" onkeyup="getBank(this,'codeCode','0,1,-2,-1','2');"
						onblur="isBank(this,'codeCode','2');" value="<c:out value='${param.customBankCode}' />">
					<img src="/claim/images/imgMustInput.gif" />
				</td>
				<td width="20%" class="left">分行名稱:</td>
				<!-- 分行名稱： -->
				<td width="30%" class="right">
					<input type="text" class="input" id="prpdpaymentaccountCustomBankName" name="prpdpaymentaccountCustomBankName" maxlength="100" onblur="isBank(this,'codeName','2');"
						onkeyup="getBank(this,'codeName','-1,0,-3,-2','2');" value="<c:out value='${param.customBankName}' />">
					<img src="/claim/images/imgMustInput.gif" />
				</td>
			</tr>
			
		</table>
		<br />
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr align="center">
				<td>
					<input type="button" class="button" name="buttonSubmit" value="<s:text name="button.submit.value"/>" onClick="submitBank();">
				</td>
			</tr>
		</table>
		<div id="bankList" style="margin:0; padding:5px;border: #acacac 1px solid;background-color: FFFFFF; display: none; cursor: hand; position: absolute; width: 400px;overflow: auto;" align="left"></div>
	</form>
</body>
</html>
