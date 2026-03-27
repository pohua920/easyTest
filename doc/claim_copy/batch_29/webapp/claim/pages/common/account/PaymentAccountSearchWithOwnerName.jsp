<%@ page contentType="text/html; charset=GBK"%>
<%--
****************************************************************************
* DESC       ：查询机构内非车查勘、立案、理算、结案权限需调派人员界面
* AUTHOR     ：理赔组
* CREATEDATE ： 2013-03-02
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------

****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<script language="JavaScript">
	javascript:window.history.forward(1);
</script>
<%@ include file="/common/meta_js.jsp"%>
<html locale="true">
<head>
<title>支付對象查詢</title>
<script src="/claim/common/js/Common.js"></script>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<script src="${ctx}/pages/dwr/engine.js"></script>
<script src="${ctx}/pages/dwr/util.js"></script>
<script src="${ctx}/pages/dwr/interface/uiAccountCodeAction.js"></script>
<script src="${ctx}/pages/common/account/js/paymentAccount.js"></script>
<script language="javascript">
	function choose(accountCode){
		window.opener.checkAccountNo(accountCode,window.opener.fm.serialNo.value,window.opener.fm.registNo.value);
		window.close();
	}
	
	function search(){
		var certificateType = $("#certificateType").val();
		var certificateCode = $("#certificateCode").val();
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
		fm.action = "${ctx}/AccountCode.do?actionType=SearchWithOwnerName&certificateType=" + certificateType + "&certificateCode=" + certificateCode;
		fm.submit();
	}
</script>
<body>
	<form name="fm" action="" method="post" autocomplete="off">
		<table width="100%" border="0" align="center" cellpadding="4" cellspacing="1" class="common">
			<tr>
				<td colspan="4" class="formtitle">
					<s:text name="compensate.payNameObject" />
				</td>
				<%-- 支付对象名称 --%>
			</tr>
			<tr>
				<td width="20%" class="left">證件類型：</td>
				<td width="30%" class="right">
					<s:select name="certificateType" id="certificateType" class="input" value="#parameters.certificateType[0]" listKey="key" listValue="value" list="#request.prpdpaymentaccountCertificateTypeList" />
				</td>
				<td width="20%" class="left">證件代碼：</td>
				<td width="30%" class="right">
					<input type="text" class="input" name="certificateCode" id="certificateCode" maxlength="20" style="width:120px" value="${param.certificateCode}">
				</td>
			</tr>
		</table>
		<table width="100%" >
			<tr>
				<td class='button' align="center" colspan="4">
					<input id="button" type="button" class="bigbutton" value="<s:text name="prompt.query"/>" onClick="search();">
				</td>
			</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="4" cellspacing="1" class="common">
			<tr>
				<td colspan="8" class="formtitle">
					<s:text name="account.accountQueryResultList" />
				</td>
				<!-- 银行帳号查询结果列表 -->
			</tr>
			<tr class=common>
				<td class="centertitle" style="width: 20%;">
					<s:text name="compensate.bankAccount" />
				</td>
				<!-- 银行帳号 -->
				<td class="centertitle" style="width: 20%;">
					<s:text name="account.belongName" />
				</td>
				<!-- 归属人姓名 -->
				<td class="centertitle" style="width: 20%;">
					<s:text name="account.belongCardCode" />
				</td>
				<!-- 归属人证件代码 -->
				<td class="centertitle" style="width: 30%;">
					<s:text name="db.prpLregist.remark"/>
				</td>
				<td class="centertitle" style="width: 10%;">
					<s:text name="certify.operate" />
				</td>
				<!-- 操作 -->
			</tr>
			<c:if test="${not empty requestScope.PrpdPaymentAccountDtoList}">
				<c:forEach items="${requestScope.PrpdPaymentAccountDtoList}" var="PrpdPaymentAccountDto">
					<tr class=common align="center">
						<td>
							<input class="readonly" readonly="readonly" style="width: 130px" name="accountCode" value="<c:out value='${PrpdPaymentAccountDto.accountCode}'/>" />
						</td>
						<td>
							<c:out value="${PrpdPaymentAccountDto.ownerName}" />
						</td>
						<td>
							<c:out value="${PrpdPaymentAccountDto.certificateCode}" />
						</td>
						<td>
							<c:out value="${PrpdPaymentAccountDto.remark}" />
						</td>
						<td>
							<input id="button" type=button class='button' value="<s:text name="button.theSelected.value"/>" onClick="choose('<c:out value='${PrpdPaymentAccountDto.accountCode}'/>')">
						</td>
						<%-- 选定 --%>
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</form>
</body>
</html>
