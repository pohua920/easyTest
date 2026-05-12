<%--
****************************************************************************
* DESC       ：查询费用支付对象银行帳号信息
* AUTHOR     ：刘伟
* CREATEDATE ： 2010-12-21
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------

****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
<html locale="true">
<%@ include file="/common/taglibs.jsp"%>
<script language="JavaScript">
	javascript:window.history.forward(1);
</script>
<%@ include file="/common/meta_js.jsp"%>
<head>
<title>需调派人员选择</title>
<%-- 公用函数 --%>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<c:if test="${empty requestScope.PrpdPaymentAccountDtoList}">
	<script language="javascript">window.close();</script>
</c:if>
<script language="javascript">
function choose(bankCode,bankName,accountCode,customBankCode,customBankName,courierAddress,areaCode,uniformNo,ownerName,compensateOwnerName){
	 try{
  	 	 var serialNo = fm.serialNo.value;
         window.opener.fm.prpLchargeBankCode[serialNo].value = bankCode;//總行代號
         window.opener.fm.prpLchargeBankName[serialNo].value = bankName;//總行名稱
         window.opener.fm.prpLchargeAccountCode[serialNo].value =  accountCode;//匯款帳號
         window.opener.fm.prpLchargeCustomBankName[serialNo].value = customBankName;//分行名稱
         //window.opener.fm.prpLchargeCustomBankName[serialNo].value = ownerPhoneNo;//受款人電話,市內電話
         window.opener.fm.prpLchargeCustomBankCode[serialNo].value =  customBankCode;//分行代码
         window.opener.fm.prpLchargeOwnerName[serialNo].value =  ownerName;//赔付对象
         window.opener.fm.prpLchargePayObjectName[serialNo].value =  ownerName;//赔付对象
         if(window.opener.fm.prpLchargeUniformNo[serialNo].value==''){
           window.opener.fm.prpLchargeUniformNo[serialNo].value =  uniformNo;//统一编号
         }
         if(window.opener.fm.prpLchargeCourierAddress[serialNo].value==''){
           window.opener.fm.prpLchargeCourierAddress[serialNo].value =  courierAddress;//邮政地址
         }
         if(window.opener.fm.prpLchargeAreaCode[serialNo].value==''){
           window.opener.fm.prpLchargeAreaCode[serialNo].value =  areaCode;//邮政区号
         }
     } catch(e){
     }
     window.close();
     return;
  }
</script>
<body>
	<form name="fm" action="" method="post" autocomplete="off">
		<input type="hidden" name="serialNo" value="<c:out value='${param.serialNo}'/>">
		<table width="100%" border="0" align="center" cellpadding="4" cellspacing="1" class="common">
			<tr>
				<td colspan="8" class="formtitle">
					<s:text name="account.accountQueryResultList" />
				</td>
				<!-- 银行帳号查询结果列表 -->
			</tr>
			<tr class=common>
				<td class="centertitle">
					<s:text name="compensate.bankAccount" />
				</td>
				<!-- 银行帳号 -->
				<td class="centertitle">
					<s:text name="account.belongName" />
				</td>
				<!-- 归属人姓名 -->
				<td class="centertitle">
					<s:text name="account.belongCardCode" />
				</td>
				<!-- 归属人证件代码 -->
				<td class="centertitle">
					<s:text name="certify.operate" />
				</td>
				<!-- 操作 -->
			</tr>
			<c:if test="${not empty requestScope.PrpdPaymentAccountDtoList}">
				<c:forEach items="${requestScope.PrpdPaymentAccountDtoList}" var="PrpdPaymentAccountDto">
					<tr class=common align="center">
						<td>
							<input class="readonly" readonly="readonly" style="width: 130px" name="accountCode" value="<c:out value="${PrpdPaymentAccountDto.accountCode}"/>">
						</td>
						<td>
							&nbsp;
							<c:out value="${PrpdPaymentAccountDto.ownerName}" />
						</td>
						<td>
							&nbsp;
							<c:out value="${PrpdPaymentAccountDto.certificateCode}" />
						</td>
						<td>
							<input id="button" type=button class='button' value="选定"
								onClick="choose('${PrpdPaymentAccountDto.bankCode}',
                                    '${PrpdPaymentAccountDto.bankName}',
                                    '${PrpdPaymentAccountDto.accountCode}',
                                    '${PrpdPaymentAccountDto.customBankCode}',
                                    '${PrpdPaymentAccountDto.customBankName}',
                                    '${PrpdPaymentAccountDto.courierAddress}',
                                    '${PrpdPaymentAccountDto.areaCode}',
                                    '${PrpdPaymentAccountDto.certificateCode}',
                                    '${PrpdPaymentAccountDto.ownerName}',
                                    '${PrpdPaymentAccountDto.compensateOwnerName}')">
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</form>
</body>
</html>
