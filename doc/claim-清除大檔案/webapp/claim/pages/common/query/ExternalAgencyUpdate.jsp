<%--
****************************************************************************
* DESC       ：公估机构修改界面
* AUTHOR     ： weizeyu
* CREATEDATE ： 2009-03-06
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page import="com.sinosoft.claim.common.ConstantCodes" %>
<%@ page contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>

<html locale="true">
<head>
  <title>公估信息查询页面</title>
  <link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
  <%@ include file="/common/meta_js.jsp"%>  
<script src="${ctx}/pages/DAA/compensate/js/autoBank.js"></script>
  <script language="javascript">
  <%--案件状态标志处理--%>
  
  function submitForm(editType)
  {
	if(fm.ComCName.value == ""){
		alert("請輸入中文名稱！");
		return false;
	}
	if(fm.CreateTime.value == ""){
		alert("請輸入建檔時間！");
		return false;
	}
	if(fm.AccountCode.value == ""){
		alert("請輸入銀行帳號！");
		return false;
	}
	//if(fm.prpdpaymentaccountCustomBankCode.value ==""){
		//alert("请输入分行代號！");
		//return false;
	//}
	if(fm.OwnerName.value ==""){
		alert("請輸入帳戶名稱！");
		return false;
	}
	if(fm.countryType.value=="1"){
		if(fm.CertifiCateCode.value ==""){
			alert("請輸入帳戶統一編號！");
			return false;
		}
	}
    fm.action = "${ctx}/externalAgency/externalagency.do?editType="+editType;
    fm.submit();//提交
  }
</script>
</head>
<body  onload="initPage();">
<form name="fm" action="/claim/externalAgency/externalagency.do"  method="post" onsubmit="return validateForm(this);">
    <table  border="0" align="center" cellpadding="4" cellspacing="1"  class="common">
    <tr>
     <td class="title" align="right" style="width:15%">代理商NO：</td><%--代理商NO --%>
      <td class="input" style="width:35%">
      <input type=text name="agentNo" class="input" style="width:220px" maxlength="40"value="${prplexternalagency.agentNo}">
      </td>
     <td class="title" width="15%">
     <input type=hidden name="ComCode" value="${prplexternalagency.id.comCode}"></td>
     <td class="title" width="35%">
     <input type=hidden name="ComType" value="${prplexternalagency.id.comtype}"></td>
     </tr>
     <tr>
        <td class="title" align="right" style="width:15%"><s:text name="query.chineseName" />：</td><%-- 中文名称--%>
        <!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
        <td class="input" style="width:35%"><input type=text name="ComCName" class="input" 
        value="${prplexternalagency.comcname}" maxlength="100" style="width:220px">
        <img src="/claim/images/bgMarkMustInput.jpg" complete="complete"/>
        </td>
        <td class="title" align="right" style="width:15%"><s:text name="query.englishName" />：</td><%--英文名称--%>
        <td class="input" style="width:35%"><input type=text name="ComEName" class="input"
        value="${prplexternalagency.comename}" style="width:220px" maxlength="40">
        </td>
     </tr>
     <tr>
        <td class="title" align="right" style="width:15%"><s:text name="query.legalPerson" />：</td><%--法人--%>
        <td class="input" style="width:35%"><input type=text name="JuridicalPerson" class="input"
        value="${prplexternalagency.juridicalperson}"  maxlength="40" style="width:220px">
        </td>
        <td class="title" align="right" style="width:15%"><s:text name="db.prpCaddress.addressName" />：</td><%-- 地址--%>
        <td class="input" style="width:35%"><input type=text name="Address" class="input"
        value="${prplexternalagency.address}" style="width:220px" maxlength="40">
        </td>
     </tr>
     <tr>
        <td class="title" align="right" style="width:15%">PO BOX：</td><%--PO BOX --%>
        <td class="input" style="width:35%">
        <input type=text name="postCode" value="${prplexternalagency.postCode}" class="input"  maxlength="40" style="width:220px">
        </td>
        <td class="title" align="right" style="width:15%">地址2：</td><%--地址2 --%>
        <td class="input" style="width:35%">
        <input type=text name="address2" value="${prplexternalagency.address2}" class="input" style="width:220px" maxlength="40">
        </td>
     </tr>
     <tr>
        <td class="title" align="right" style="width:15%">歸屬公司：</td><%--歸屬公司 --%>
        <td class="input" style="width:35%">
        <input type=text name="vestingCom" value="${prplexternalagency.vestingCom}" class="input"  maxlength="40" style="width:220px">
        </td>
        <td class="title" align="right" style="width:15%">國別：</td><%--國別 --%>
        <td class="input" style="width:35%">
            
            <select name="countryType" class="common" style="width:220px">
                <option lable="本國" value="1">本國</option><%--本國 --%>
                <option lable="外國" value="2">外國</option><%--外國 --%>
            </select>
            <select name="countryType" class="common" style="width:220px">
              <option lable="本國" value="1" <c:if test="${prplexternalagency.countryType == '1'}">selected</c:if>>本國</option><%--本國--%>
              <option lable="外國" value="2" <c:if test="${prplexternalagency.countryType == '2'}">selected</c:if>>外國</option><%--外國--%>
            </select>
        </td>
     </tr>
     <tr>
        <td class="title" align="right" style="width:15%"><s:text name="db.prpLregist.phoneNumber" />：</td><%--联系电话--%>
        <td class="input" style="width:35%"><input type=text name="Telephone" class="input"
        value="${prplexternalagency.telephone}"  maxlength="25" style="width:220px">
        </td>
        <td class="title" align="right" style="width:15%"><s:text name="db.prpDcompany.faxNumber" />：</td><%--传真--%>
        <td class="input" style="width:35%"><input type=text name="FaxNo" class="input"
        value="${prplexternalagency.faxno}" style="width:220px" maxlength="25">
        </td>
     </tr>
     <tr>
        <td class="title" align="right" style="width:15%"><s:text name="query.companyWebSite" />：</td><%--公司网址--%>
        <td class="input" style="width:35%"><input type=text name="WebAddress"
        value="${prplexternalagency.webaddress}" class="input" style="width:220px">
        </td>
        <td class="title" align="right" style="width:15%"><s:text name="db.prpDagent.linkerName" />：</td><%-- 联系人--%>
        <td class="input" style="width:35%">
        <!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
        <input type=text name="LinkerName" class="input"
        value="${prplexternalagency.linkerName}" style="width:220px" maxlength="100">
        </td>
     </tr>
     <tr>
        <td class="title" align="right" style="width:15%"><s:text name="query.relativePhone" />：</td><%--联系人电话--%>
        <td class="input" style="width:35%"><input type=text name="LinkerNameTel"
        value="${prplexternalagency.linkernametel}" class="input"  maxlength="12" style="width:220px">
        </td>
        <td class="title" align="right" style="width:15%"><s:text name="query.relativeEmail" />：</td><%--联系人E-mail--%>
        <td class="input" style="width:35%"><input type=text name="LinkerEMail" class="input"
        value="${prplexternalagency.linkeremail}" style="width:220px">
        </td>
     </tr>
     <tr>
        <td class="title" align="right" style="width:15%"><s:text name="query.specialty" />：</td><%--专长--%>
        <td class="input" style="width:35%"><input type=text name="Specialty" class="input"
        value="${prplexternalagency.specialty}"  maxlength="12" style="width:220px">
        </td>
        <td class="title" align="right" style="width:15%"><s:text name="query.founder" />：</td><%-- 创建人--%>
        <td class="input" style="width:35%"><input type=text name="CreatorCode" class="input"
        value="${prplexternalagency.creatorcode}" style="width:220px" maxlength="12">
        </td>
     </tr>
     <tr>
        <td class="title" align="right" style="width:15%"><s:text name="query.bulidTime" />：</td><%--创建时间--%>
        <td class="input" style="width:35%"><input type=text name="CreateTime"
        value="<fmt:formatDate value='${prplexternalagency.createtime}' pattern='yyyy-MM-dd'/>" class="Wdate"  onClick="WdatePicker()" style="width:220px" maxlength="12">
        <img src="/claim/images/bgMarkMustInput.jpg" complete="complete"/>
        </td>
        <td class="title" align="right" style="width:15%"><s:text name="db.prpDcompany.remark" />：</td><%--备注--%>
        <td class="input" style="width:35%"><input type=text name="Remark" class="input"
        value="${prplexternalagency.remark}" style="width:220px">
        </td>
     </tr>
     <tr>
        <td class="title" align="right" style="width:15%"><s:text name="compensate.bankAccount" />：</td><%--银行帳号--%>
        <td class="input" style="width:35%"><input type=text name="AccountCode" class="input"
        value="${prplexternalagency.accountCode}" style="width:220px">
        <img src="/claim/images/bgMarkMustInput.jpg" complete="complete"/>
        </td>
        <td class="title" align="right" style="width:15%"><s:text name="query.accountBelongName" />：</td><%--帳号归属人名称--%>
        <td class="input" style="width:35%"><input type=text name="OwnerName" class="input"
        value="${prplexternalagency.ownerName}" style="width:220px">
        <img src="/claim/images/bgMarkMustInput.jpg" complete="complete"/>
        </td>
     </tr>
     <tr>
	    <td class="title" align="right" style="width:15%">總行代碼：</td>
	    <td class="input" style="width:35%">
	    	<input type="text" class="readonly" readonly name="prpdpaymentaccountBankCode" 
	    		   onkeyup="getBank(this,'codeCode','0,1','1');" 
	    		   onblur="isBank(this,'codeCode','1');" 
	    		    value="<c:out value='${prplexternalagency.bankCode}'/>"
	    		   />
	    </td>
        <td class="title" align="right" style="width:15%">總行名稱：</td>
	    <td class="input" style="width:35%">
	    	<input type="text" class="readonly" readonly name="prpdpaymentaccountBankName"
	    	       onkeyup="getBank(this,'codeName','-1,0','1');"
	    	       value="<c:out value='${prplexternalagency.bankName}'/>"
	    	       >
	    </td>
	  </tr>
	  <tr>
	  	<td class="title" align="right" style="width:15%">分行代號:</td><!-- 分行代號： -->
        <td class="input" style="width:35%">
          <input type="text" class="common" id="prpdpaymentaccountCustomBankCode" name="prpdpaymentaccountCustomBankCode"  maxlength="10"
                 onblur="isBank(this,'codeCode','2');"
                 value="<c:out value='${prplexternalagency.customBankCode}' />"
                 onkeyup="getBank(this,'codeCode','0,1,-2,-1','2');"
              >
        </td>
  	    <td class="title" align="right" style="width:15%">分行名稱：</td>
	    <td class="input" style="width:35%">
	      <input type="text" class="common"  maxlength="100"  id="prpdpaymentaccountCustomBankName" name="prpdpaymentaccountCustomBankName" 
	             onblur="isBank(this,'codeName','2');"
	             value="<c:out value='${prplexternalagency.customBankName}' />"
	             onkeyup="getBank(this,'codeName','-1,0,-3,-2','2');"
	           >
	    </td>
	  </tr>
     <tr>
        <td class="title" align="right" style="width:15%"><s:text name="query.accountBelongCardName" />：</td><%--帳号归属人证件号码--%>
        <td class="input" style="width:35%"><input type=text name="CertifiCateCode" class="input"
        value="${prplexternalagency.certifiCateCode}" style="width:220px">
        <img src="/claim/images/bgMarkMustInput.jpg" complete="complete"/>
        </td>
        <td class="title" align="right" style="width:15%"><s:text name="query.accountBelongTelphone" />：</td><%--帳号归属人联系电话--%>
        <td class="input" style="width:35%"><input type=text name="PhoneNo" class="input"
        value="${prplexternalagency.ownerPhoneNo}" style="width:220px">
        </td>
     </tr>
     <tr>
        <td class="title" align="right" style="width:15%"><s:text name="compensate.accountCurrencyType" />：</td><%--帳户类型--%>
        <td class="input" style="width:35%">
        	<select name="AccountType" class="common" style="width:220px">
              <option lable="存折" value="1" <c:if test="${prplexternalagency.accountType == '1'}">selected</c:if>><s:text name="compensate.passbook" /></option><%--存折--%>
              <option lable="信用卡" value="2" <c:if test="${prplexternalagency.accountType == '2'}">selected</c:if>><s:text name="compensate.creditCard" /></option><%--信用卡--%>
              <option lable="储值卡" value="3" <c:if test="${prplexternalagency.accountType == '3'}">selected</c:if>><s:text name="compensate.CARDS" /></option></option><%--储值卡--%>
              <option lable="其他" value="4" <c:if test="${prplexternalagency.accountType == '4'}">selected</c:if>><s:text name="regist.prpLregist.other" /></option><%--其他--%>
            </select>
        </td>
        <td class="title" align="right" style="width:15%">地區別：</td><%--地區別 --%>
        <td class="input" style="width:35%"><input type=text name="areaCode" value="${prplexternalagency.areaCode}" class="input" style="width:220px" maxlength="40">
        </td>
        <%--
        <td class="title" align="right" style="width:15%" style="display:none"><s:text name="compensate.accountCurrency" />：</td>--%><%--帳户币别--%>
        <%--<td class="input" style="width:35%" style="display:none"><input type=text name="AccountCurrency" class="input"
        value="${prplexternalagency.accountCurrency}" style="width:220px" value="<%=ConstantCodes.LOCAL_CURRENCY %>">
        </td>--%>
     </tr>
     <tr>
      <td class="title" align="right" style="width:15%" ><s:text name="db.prpUserGrade.flag" />：</td><%--标志位--%>
		<td class="input" style="width:35%" colspan="3">
		    <c:if test="${prplexternalagency.validStatus == '1'}">
		        <input type=radio name="Validstatus" value="1" checked><s:text name="query.flagTrue" /><%--有效--%>
			    <input type=radio name="Validstatus" value="0"><s:text name="query.flagFalse" /><%--无效--%>
			    </c:if>
		    <c:if test="${prplexternalagency.validStatus == '0'}">
		        <input type=radio name="Validstatus" value="1"><s:text name="query.flagTrue" /><%--有效--%>
			    <input type=radio name="Validstatus" value="0" checked><s:text name="query.flagFalse" /><%--无效--%>
			    </c:if>
	  </td>
     </tr>
    <tr>
    <td class="title" style="color:red" colspan="4">
     <%--注:创建时间为必录项!!!--%>
    </td>
  </tr>
     <tr>
        <td class=button style="width:40%" colspan="4" align="center">
          <input type="button" name=buttonSave class='button' value="<s:text name='form.save' />" onClick="submitForm('updateSave');"><%--保存--%>
        </td>
     </tr>
    </table>
    <div  id="bankList" style="background-color:FFFFFF;display: none;cursor:hand;position: absolute;width: 400px;" align="left"></div>
  </form>
</body>
</html>