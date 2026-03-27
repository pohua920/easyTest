<%--
****************************************************************************
* DESC       ：公估机构查询详细信息界面
* AUTHOR     ： weizeyu
* CREATEDATE ： 2009-03-06
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>

<%@ page contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta_js.jsp"%>  

<html locale="true">
<head>
  <title>公估信息查询页面</title>
  <link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
  <script language="javascript">
  <%--案件状态标志处理--%>
  
  function submitForm(editType){
    fm.action = "/claim/externalAgency/externalagency.do?editType="+editType;
    fm.submit();//提交
  }
</script>
</head>
<body >
<form name="fm" action="/claim/externalAgency/externalagency.do?"  method="post" onsubmit="return validateForm(this);">
    <table  border="0" align="center" cellpadding="4" cellspacing="1"  class="common">
     <tr>
      <td class="title" align="right" style="width:15%">代理商NO：</td><%--代理商NO --%>
      <td class="input" style="width:35%">
        <input type=text name="agentNo" class="readonly" readonly style="width:220px" maxlength="40" value="${prplexternalagency.agentNo}">
      </td>
     </tr>
     <tr>
        <td class="title" align="right" style="width:15%"><s:text name="query.chineseName" />:</td><%-- 中文名称--%>
        <td class="input" style="width:35%" >
          <!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
          <input type=text name="ComCName" class="readonly" readonly maxlength="100" style="width:220px"
          value="${prplexternalagency.comcname}">
        </td>
        <td class="title" align="right" style="width:15%"><s:text name="query.englishName" />：</td><%--英文名称--%>
        <td class="input" style="width:35%" >
          <input type=text name="ComEName" class="readonly" readonly style="width:220px" maxlength="40"
          value="${prplexternalagency.comename}">
        </td>
     </tr>
     <tr>
        <td class="title" align="right" style="width:15%"><s:text name="query.legalPerson" />:</td><%--法人--%>
        <td class="input" style="width:35%" >
          <input type=text name="JuridicalPerson" class="readonly" readonly  maxlength="40" style="width:220px"
          value="${prplexternalagency.juridicalperson}">
        </td>
        <td class="title" align="right" style="width:15%"><s:text name="db.prpCaddress.addressName" />：</td><%--地址--%>
        <td class="input" style="width:35%" >
          <input type=text name="Address" class="readonly" readonly style="width:220px" maxlength="40"
          value="${prplexternalagency.address}">
        </td>
     </tr>
     <tr>
        <td class="title" align="right" style="width:15%">PO BOX：</td><%--PO BOX --%>
        <td class="input" style="width:35%"><input type=text name="postCode" value="${prplexternalagency.postCode}" class="readonly" readonly  maxlength="40" style="width:220px">
        </td>
        <td class="title" align="right" style="width:15%">地址2：</td><%--地址2 --%>
        <td class="input" style="width:35%"><input type=text name="address2" value="${prplexternalagency.address2}" class="readonly" readonly style="width:220px" maxlength="40">
        </td>
     </tr>
     <tr>
        <td class="title" align="right" style="width:15%">歸屬公司：</td><%--歸屬公司 --%>
        <td class="input" style="width:35%"><input type=text name="vestingCom" value="${prplexternalagency.vestingCom}" class="readonly" readonly  maxlength="40" style="width:220px">
        </td>
        <td class="title" align="right" style="width:15%">國別：</td><%--國別 --%>
        <td class="input" style="width:35%">
          <input type=text name="countryType" class="readonly" readonly style="width:220px"
          <c:if test="${prplexternalagency.countryType=='1'}">value="本國"</c:if><%--本國--%>
          <c:if test="${prplexternalagency.countryType=='2'}">value="外國"</c:if><%--外國--%>
        </td>
     </tr>
     <tr>
        <td class="title" align="right" style="width:15%"><s:text name="db.prpLregist.phoneNumber" />:</td><%--联系电话--%>
        <td class="input" style="width:35%" >
          <input type=text name="Telephone" class="readonly" readonly  maxlength="25" style="width:220px"
          value="${prplexternalagency.telephone}">
        </td>
        <td class="title" align="right" style="width:15%"><s:text name="db.prpDcompany.faxNumber" />：</td><%--传真--%>
        <td class="input" style="width:35%" >
          <input type=text name="FaxNo" class="readonly" readonly style="width:220px" maxlength="25"
          value="${prplexternalagency.faxno}">
        </td>
     </tr>
     <tr>
        <td class="title" align="right" style="width:15%"><s:text name="query.companyWebSite" />:</td><%--公司网址--%>
        <td class="input" style="width:35%" >
          <input type=text name="WebAddress" class="readonly" readonly  maxlength="12" style="width:220px"
          value="${prplexternalagency.webaddress}">
        </td>
        <td class="title" align="right" style="width:15%"><s:text name="db.prpDagent.linkerName" />：</td><%--联系人--%>
        <td class="input" style="width:35%" >
          <!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
          <input type=text name="LinkerName" class="readonly" readonly style="width:220px" maxlength="100"
          value="${prplexternalagency.linkerName}">
        </td>
     </tr>
     <tr>
        <td class="title" align="right" style="width:15%"><s:text name="query.relativePhone" />:</td><%--联系人电话--%>
        <td class="input" style="width:35%" >
          <input type=text name="LinkerNameTel" class="readonly" readonly  maxlength="12" style="width:220px"
          value="${prplexternalagency.linkernametel}">
        </td>
        <td class="title" align="right" style="width:15%"><s:text name="query.relativeEmail" />：</td><%--联系人E-mail--%>
        <td class="input" style="width:35%" >
          <input type=text name="LinkerEMail" class="readonly" readonly style="width:220px" maxlength="12"
          value="${prplexternalagency.linkeremail}">
        </td>
     </tr>
     <tr>
        <td class="title" align="right" style="width:15%"><s:text name="query.specialty" />:</td><%--专长--%>
        <td class="input" style="width:35%" >
          <input type=text name="Specialty" class="readonly" readonly  maxlength="12" style="width:220px"
          value="${prplexternalagency.specialty}">
        </td>
        <td class="title" align="right" style="width:15%"><s:text name="query.founder" />：</td><%--创建人--%>
        <td class="input" style="width:35%" >
          <input type=text name="CreatorCode" class="readonly" readonly style="width:220px" maxlength="12"
          value="${prplexternalagency.creatorcode}">
        </td>
     </tr>
     <tr>
        <td class="title" align="right" style="width:15%"><s:text name="query.bulidTime" />：</td><%--创建时间--%>
        <td class="input" style="width:35%" >
         <%--  <input type=text name="CreateTime" class="readonly" readonly style="width:220px"
          value="<fmt:formatDate value='${prplexternalagency.createtime}' pattern='yyyy-MM-dd'/>">--%>
          <rc:rcDate name="CreateTime" class="readonly" readonly="true" wdatePicker="false"  style="width:220px" value="${prplexternalagency.createtime}" /> 
        </td>
        <td class="title" align="right" style="width:15%"><s:text name="db.prpDcompany.remark" />：</td><%--备注--%>
        <td class="input" style="width:35%" >
          <input type=text name="Remark" class="readonly" readonly style="width:220px"
          value="${prplexternalagency.remark}">
        </td>
     </tr>
     <tr>
        <td class="title" align="right" style="width:15%"><s:text name="compensate.bankAccount" />：</td><%--银行帳号--%>
        <td class="input" style="width:35%" >
          <input type=text name="CreateTime" class="readonly" readonly style="width:220px"
          value="${prplexternalagency.accountCode}">
        </td>
        <td class="title" align="right" style="width:15%"><s:text name="query.accountBelongName" />：</td><%--帳号归属人名称--%>
        <td class="input" style="width:35%" >
          <input type=text name="Remark" class="readonly" readonly style="width:220px"
          value="${prplexternalagency.ownerName}">
        </td>
     </tr>
     <tr>
        <td class="title" align="right" style="width:15%"><s:text name="compensate.headquarterName" />：</td><%--总行名称--%>
        <td class="input" style="width:35%" >
          <input type=text name="CreateTime" class="readonly" readonly style="width:220px"
          value="${prplexternalagency.bankName}">
        </td>
        <td class="title" align="right" style="width:15%"><s:text name="compensate.bankNames" />：</td><%--开户银行名称--%>
        <td class="input" style="width:35%" >
          <input type=text name="Remark" class="readonly" readonly style="width:220px"
          value="${prplexternalagency.customBankName}">
        </td>
     </tr>
     <tr>
	    <td class="title" align="right" style="width:15%">總行代碼：</td>
	    <td class="input" style="width:35%">
	    	<input type="text" class="readonly" readonly name="prpdpaymentaccountBankCode" 
	    		    value="<c:out value='${prplexternalagency.bankCode}'/>"
	    		   />
	    </td>
        <td class="title" align="right" style="width:15%">總行名稱：</td>
	    <td class="input" style="width:35%">
	    	<input type="text" class="readonly" readonly name="prpdpaymentaccountBankName"
	    	       value="<c:out value='${prplexternalagency.bankName}'/>"
	    	       >
	    </td>
	  </tr>
	  <tr>
	  	<td class="title" align="right" style="width:15%">分行代號:</td><!-- 分行代號： -->
        <td class="input" style="width:35%">
          <input type="text" class="readonly" id="prpdpaymentaccountCustomBankCode" name="prpdpaymentaccountCustomBankCode"  maxlength="10"
                 value="<c:out value='${prplexternalagency.customBankCode}' />"
              >
        </td>
  	    <td class="title" align="right" style="width:15%">分行名稱：</td>
	    <td class="input" style="width:35%">
	      <input type="text" class="readonly"  maxlength="100"  id="prpdpaymentaccountCustomBankName" name="prpdpaymentaccountCustomBankName" 
	             value="<c:out value='${prplexternalagency.customBankName}' />"
	           >
	    </td>
	  </tr>
     <tr>
        <td class="title" align="right" style="width:15%"><s:text name="query.accountBelongCardName" />：</td><%--帳号归属人证件号码--%>
        <td class="input" style="width:35%" >
          <input type=text name="CreateTime" class="readonly" readonly style="width:220px"
          value="${prplexternalagency.certifiCateCode}">
        </td>
        <td class="title" align="right" style="width:15%"><s:text name="query.accountBelongTelphone" />：</td><%--帳号归属人联系电话--%>
        <td class="input" style="width:35%" >
          <input type=text name="Remark" class="readonly" readonly style="width:220px"
          value="${prplexternalagency.ownerPhoneNo}">
        </td>
     </tr>
     <tr>
        <td class="title" align="right" style="width:15%"><s:text name="compensate.accountCurrencyType" />：</td><%--帳户类型--%>
        <td class="input" style="width:35%" >
          <input type=text name="CreateTime" class="readonly" readonly style="width:220px"
          <c:if test="${prplexternalagency.accountType=='1'}">value="<s:text name="compensate.passbook" />"</c:if><%--存折--%>
          <c:if test="${prplexternalagency.accountType=='2'}">value="<s:text name="compensate.creditCard" />"</c:if><%--信用卡--%>
          <c:if test="${prplexternalagency.accountType=='3'}">value="<s:text name="compensate.CARDS" />"</c:if><%--储值卡--%>
          <c:if test="${prplexternalagency.accountType=='4'}">value="<s:text name="regist.prpLregist.other" />"</c:if>/><%--其他--%>
        </td>
        <td class="title" align="right" style="width:15%">地區別：</td><%--地區別 --%>
        <td class="input" style="width:35%"><input type=text name="areaCode" class="readonly" readonly value="${prplexternalagency.areaCode}" class="input" style="width:220px" maxlength="40">
        </td>
        <%--
        <td class="title" align="right" style="width:15%"><s:text name="compensate.accountCurrency" />：</td>--%><%--帳户币别--%>
        <%--<td class="input" > <input type=text name="Remark" class="readonly" readonly style="width:220px"
          value="${prplexternalagency.accountCurrency}"></td>--%>
     </tr>
     <tr>
        <td class=button style="width:33%" colspan="3">
              <input type=button name=buttonBack class='button' value="<s:text name='prompt.back' />" onclick="return history.back();" ><%--返回--%>
        </td>
     </tr>
    </table>
  </form>
</body>
</html>