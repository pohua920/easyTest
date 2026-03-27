<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>账户信息同步</title>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/i18njs.jsp"%>
</head>
<body id="all_title">
<div id="wrapper">
<div id="container">
<s:form name="fm" action="">
<s:hidden name="userCode" value="${userCode}"></s:hidden>
<s:hidden name="svrCode" value="${svrCode}"></s:hidden>
<s:hidden name="svrName" value="${svrName}"></s:hidden>
<s:hidden name="accCode" value="${accCode}"></s:hidden>
<table class="fix_table">	
		<tr class="top">
            <div id="crash_menu">
            <h2 align="center">
              <s:if test="${userType=='01'}">个人用户信息同步</s:if>
              <s:if test="${userType=='02'}">企业用户信息同步</s:if>
            </h2>
            </div>	
		</tr>
		<s:if test="${userType=='01'}">
		<tr>
			<td><input type = "checkbox" name="checkboxs" value="Age"/></td>
			<td class="bgc_tt short">年龄</td>
			<td class="long"><s:textfield name="utiIUserIdv.age" value="${utiIUserIdv.age}"
					id="utiIUserIdv.age" cssClass='input_w w_30' maxlength="10" readonly="true"/></td>
			<td><input type = "checkbox" name="checkboxs" value="BirthDate"/></td>
			<td class="bgc_tt short">出生日期</td>
			<td class="long"><s:textfield readonly="true" name="utiIUserIdv.birthDate" 
					id="utiIUserIdv.birthDate" cssClass='input_w w_30' maxlength="20" value="${utiIUserIdv.birthDate}" />
			</td>
		</tr>
		<tr>
			<td><input type = "checkbox" name="checkboxs" value="Sex"/></td>
			<td class="bgc_tt short">性别</td>
			<td class="long">
				<s:textfield name="utiIUserIdv.sex" cssClass='input_w w_30' id="utiIUserIdv.sex"  maxlength="1" readonly="true"/>
			</td>
			<td><input type = "checkbox" name="checkboxs" value="Health"/></td>
			<td class="bgc_tt short">健康状况</td>
			<td class="long"><s:textfield name="utiIUserIdv.health" 
					id="utiIUserIdv.health" cssClass='input_w w_30' maxlength="1000" value="${utiIUserIdv.health }" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td><input type = "checkbox" name="checkboxs" value="HomePhone"/></td>
			<td class="bgc_tt short">家庭电话</td>
			<td class="long"><s:textfield name="utiIUserIdv.homePhone" value="${utiIUserIdv.homePhone}"
					id="utiIUserIdv.homePhone" cssClass='input_w w_30' maxlength="30" readonly="true"/></td>
			<td><input type = "checkbox" name="checkboxs" value="OfficePhone"/></td>
			<td class="bgc_tt short">办公电话</td>
			<td class="long"><s:textfield name="utiIUserIdv.officePhone" 
					id="utiIUserIdv.officePhone" cssClass='input_w w_30' maxlength="30" value="${utiIUserIdv.officePhone }" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td><input type = "checkbox" name="checkboxs" value="Email"/></td>
			<td class="bgc_tt short">电子邮件</td>
			<td class="long"><s:textfield name="utiIUserIdv.email" value="${utiIUserIdv.email}"
					id="utiIUserIdv.email" cssClass='input_w w_30' maxlength="100" readonly="true"/></td>
			<td><input type = "checkbox" name="checkboxs" value="Mobile"/></td>
			<td class="bgc_tt short">手机号码</td>
			<td class="long"><s:textfield name="utiIUserIdv.mobile" value="${utiIUserIdv.mobile}"
					id="utiIUserIdv.mobile" cssClass='input_w w_30' maxlength="30" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td><input type = "checkbox" name="checkboxs" value="PostAddress"/></td>
			<td class="bgc_tt short">邮寄地址</td>
			<td class="long"><s:textfield name="utiIUserIdv.postAddress" value="${utiIUserIdv.postAddress}"
					id="utiIUserIdv.postAddress" cssClass='input_w w_30' maxlength="1000" readonly="true"/></td>
			<td><input type = "checkbox" name="checkboxs" value="PostCode"/></td>
			<td class="bgc_tt short">邮政编码</td>
			<td class="long"><s:textfield name="utiIUserIdv.postCode" value="${utiIUserIdv.postCode}"
					id="utiIUserIdv.postCode" cssClass='input_w w_30' maxlength="10" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td><input type = "checkbox" name="checkboxs" value="FaxNumber"/></td>
			<td class="bgc_tt short">传真号码</td>
			<td class="long"><s:textfield name="utiIUserIdv.faxNumber" value="${utiIUserIdv.faxNumber}"
					id="utiIUserIdv.faxNumber" cssClass='input_w w_30' maxlength="30" readonly="true"/></td>
			<td><input type = "checkbox" name="checkboxs" value="IdentifyNumber"/></td>
			<td class="bgc_tt short">身份证</td>
			<td class="long"><s:textfield name="utiIUserIdv.identifyNumber" value="${utiIUserIdv.identifyNumber}"
					id="utiIUserIdv.identifyNumber" cssClass='input_w w_30' maxlength="1000" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td><input type = "checkbox" name="checkboxs" value="OccupationCode"/></td>
			<td class="bgc_tt short">职业</td>
			<td class="long"><s:textfield name="utiIUserIdv.occupationCode" value="${utiIUserIdv.occupationCode}"
					id="utiIUserIdv.occupationCode" cssClass='input_w w_30' maxlength="1000" readonly="true"/></td>
			<td><input type = "checkbox" name="checkboxs" value="EducationCode"/></td>
			<td class="bgc_tt short">学历</td>
			<td class="long"><s:textfield name="utiIUserIdv.educationCode" value="${utiIUserIdv.educationCode}"
					id="utiIUserIdv.educationCode" cssClass='input_w w_30' maxlength="1000" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td><input type = "checkbox" name="checkboxs" value="Unit"/></td>
			<td class="bgc_tt short">工作单位</td>
			<td class="long"><s:textfield name="utiIUserIdv.unit" value="${utiIUserIdv.unit}"
					id="utiIUserIdv.unit" cssClass='input_w w_30' maxlength="1000" readonly="true"/></td>
			<td><input type = "checkbox" name="checkboxs" value="UnitAddress"/></td>
			<td class="bgc_tt short">单位地址</td>
			<td class="long"><s:textfield name="utiIUserIdv.unitAddress" value="${utiIUserIdv.unitAddress}"
					id="utiIUserIdv.unitAddress" cssClass='input_w w_30' maxlength="1000" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td><input type = "checkbox" name="checkboxs" value="Bank"/></td>
			<td class="bgc_tt short">开户银行</td>
			<td class="long"><s:textfield name="utiIUserIdv.bank" value="${utiIUserIdv.bank}"
					id="utiIUserIdv.bank" cssClass='input_w w_30' maxlength="1000" readonly="true"/></td>
			<td><input type = "checkbox" name="checkboxs" value="Account"/></td>
			<td class="bgc_tt short">账号</td>
			<td class="long"><s:textfield name="utiIUserIdv.account" 
					id="utiIUserIdv.account" cssClass='input_w w_30' maxlength="1000" value="${utiIUserIdv.account }" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td><input type = "checkbox" name="checkboxs" value="TerminalCode"/></td>
			<td class="bgc_tt short">建立机构</td>
			<td class="long"><s:textfield name="utiIUserIdv.terminalCode" value="${utiIUserIdv.terminalCode}"
					id="utiIUserIdv.terminalCode" cssClass='input_w w_30' maxlength="10" readonly="true"/></td>
			<td><input type = "checkbox" name="checkboxs" value="Source"/></td>
			<td class="bgc_tt short">用户来源</td>
			<td class="long"><s:textfield name="utiIUserIdv.source" 
					id="utiIUserIdv.source" cssClass='input_w w_30' maxlength="1000" value="${utiIUserIdv.source }" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td><input type = "checkbox" name="checkboxs" value="CustomerFlag"/></td>
			<td class="bgc_tt short">客户标识</td>
			<td class="long">
	      		<s:textfield name="utiIUserIdv.customerFlag"  cssClass='input_w w_30' id="utiIUserIdv.customerFlag"  maxlength="1" readonly="true"/>
			</td>
			<td><input type = "checkbox" name="checkboxs" value="Flag"/></td>
			<td class="bgc_tt short">标识字段</td>
			<td class="long"><s:textfield name="utiIUserIdv.flag" 
					id="utiIUserIdv.flag" cssClass='input_w w_30' maxlength="3" value="${utiIUserIdv.flag}" readonly="true"/>
			</td>
		</tr>
		
		<tr>
			<td><input type = "checkbox" name="checkboxs" value="Seal"/></td>
			<td class="bgc_tt short">印章</td>
			<td class="long"><s:textfield name="utiIUserIdv.seal" value="${utiIUserIdv.seal}"
					id="utiIUserIdv.seal" cssClass='input_w w_30' maxlength="1000" readonly="true"/></td>
			<td><input type = "checkbox" name="checkboxs" value="Remark"/></td>
			<td class="bgc_tt short">备注</td>
			<td class="long"><s:textfield name="utiIUserIdv.remark" 
					id="utiIUserIdv.remark" cssClass='input_w w_30' maxlength="1000" value="${utiIUserIdv.remark }" readonly="true"/>
			</td>
		</tr>
		</s:if>
		
		
		 <s:if test="${userType=='02'}">
		<tr>
			<td><input type = "checkbox" name="checkboxs" value="ComCode"/></td>
			<td class="bgc_tt short">所属机构代码</td>
			<td class="long"><s:textfield name="utiIUserUnit.comCode" value="${utiIUserUnit.comCode}"
					id="utiIUserUnit.comCode" cssClass='input_w w_30' maxlength="10" readonly="true"/></td>
			<td><input type = "checkbox" name="checkboxs" value="NewUserCode"/></td>
			<td class="bgc_tt short">最新用户代码</td>
			<td class="long"><s:textfield name="utiIUserUnit.newUserCode" 
					id="utiIUserUnit.newUserCode" cssClass='input_w w_30' maxlength="10" readonly="true" value="${utiIUserUnit.newUserCode }" />
			</td>
		</tr>
		<tr>
			<td><input type = "checkbox" name="checkboxs" value="RegistrationAgency"/></td>
			<td class="bgc_tt short">登记机关</td>
			<td class="long"><s:textfield name="utiIUserUnit.registrationAgency" value="${utiIUserUnit.registrationAgency}"
					id="utiIUserUnit.registrationAgency" cssClass='input_w w_30' maxlength="80" readonly="true"/></td>
			<td><input type = "checkbox" name="checkboxs" value="RegistrationNumber"/></td>
			<td class="bgc_tt short">登记注册号</td>
			<td class="long"><s:textfield name="utiIUserUnit.registrationNumber" value="${utiIUserUnit.registrationNumber}"
					id="utiIUserUnit.registrationNumber" cssClass='input_w w_30' maxlength="40" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td><input type = "checkbox" name="checkboxs" value="OrganizationCode"/></td>
			<td class="bgc_tt short">组织机构代码</td>
			<td class="long"><s:textfield name="utiIUserUnit.organizationCode" value="${utiIUserUnit.organizationCode}"
					id="utiIUserUnit.organizationCode" cssClass='input_w w_30' maxlength="10" readonly="true"/></td>
			<td><input type = "checkbox" name="checkboxs" value="LoancardCode"/></td>
			<td class="bgc_tt short">贷款卡编码</td>
			<td class="long"><s:textfield name="utiIUserUnit.loancardCode" 
					id="utiIUserUnit.loancardCode" cssClass='input_w w_30' maxlength="16" value="${utiIUserUnit.loancardCode}" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td><input type = "checkbox" name="checkboxs" value="NationalTaxRegistNo"/></td>
			<td class="bgc_tt short">税务登记证号码</td>
			<td class="long"><s:textfield name="utiIUserUnit.nationalTaxRegistNo" value="${utiIUserUnit.nationalTaxRegistNo}"
					id="utiIUserUnit.nationalTaxRegistNo" cssClass='input_w w_30' maxlength="30" readonly="true"/></td>
			<td><input type = "checkbox" name="checkboxs" value="OpenBank"/></td>
			<td class="bgc_tt short">基本账户行</td>
			<td class="long"><s:textfield name="utiIUserUnit.openBank" value="${utiIUserUnit.openBank}"
					id="utiIUserUnit.openBank" cssClass='input_w w_30' maxlength="40" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td><input type = "checkbox" name="checkboxs" value="EmpNumber"/></td>
			<td class="bgc_tt short">从业人数</td>
			<td class="long"><s:textfield name="utiIUserUnit.empNumber" value="${utiIUserUnit.empNumber}"
					id="utiIUserUnit.empNumber" cssClass='input_w w_30' maxlength="20" onblur="checkNum(this);" readonly="true"/></td>
			<td><input type = "checkbox" name="checkboxs" value="RegistrationDate"/></td>
			<td class="bgc_tt short">注册登记日期</td>
			<td class="long"><s:textfield readonly="true" name="utiIUserUnit.registrationDate" 
					id="utiIUserUnit.registrationDate" cssClass='input_w w_30' maxlength="20" value="${utiIUserUnit.registrationDate}"/>
			</td>
		</tr>
		<tr>
			<td><input type = "checkbox" name="checkboxs" value="RegisteredCapital"/></td>
			<td class="bgc_tt short">注册资本</td>
			<td class="long"><s:textfield name="utiIUserUnit.registeredCapital" value="${utiIUserUnit.registeredCapital}"
					id="utiIUserUnit.registeredCapital" cssClass='input_w w_30' maxlength="19" onblur="checkNum(this);" readonly="true"/></td>
			<td><input type = "checkbox" name="checkboxs" value="LicenseExpiringDate"/></td>
			<td class="bgc_tt short">营业执照到期日期</td>
			<td class="long"><s:textfield readonly="true" name="utiIUserUnit.licenseExpiringDate" 
					id="utiIUserUnit.licenseExpiringDate" cssClass='input_w w_30' maxlength="20" value="${utiIUserUnit.licenseExpiringDate}"/>
			</td>
		</tr>
		<tr>
			<td><input type = "checkbox" name="checkboxs" value="Capital"/></td>
			<td class="bgc_tt short">实收资本</td>
			<td class="long"><s:textfield name="utiIUserUnit.capital" value="${utiIUserUnit.capital}"
					id="utiIUserUnit.capital" cssClass='input_w w_30' maxlength="19" onblur="checkNum(this);" readonly="true"/></td>
			<td><input type = "checkbox" name="checkboxs" value="RegistrationType"/></td>
			<td class="bgc_tt short">登记注册类型</td>
			<td class="long"><s:textfield name="utiIUserUnit.registrationType" value="${utiIUserUnit.registrationType}"
					id="utiIUserUnit.registrationType" cssClass='input_w w_30' maxlength="6" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td><input type = "checkbox" name="checkboxs" value="BusinesssCope"/></td>
			<td class="bgc_tt short">经营范围</td>
			<td class="long"><s:textfield name="utiIUserUnit.businesssCope" value="${utiIUserUnit.businesssCope}"
					id="utiIUserUnit.businesssCope" cssClass='input_w w_30' maxlength="2000" readonly="true"/></td>
			<td><input type = "checkbox" name="checkboxs" value="MainOperation"/></td>
			<td class="bgc_tt short">主营业务</td>
			<td class="long"><s:textfield name="utiIUserUnit.mainOperation" value="${utiIUserUnit.mainOperation}"
					id="utiIUserUnit.mainOperation" cssClass='input_w w_30' maxlength="2000" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td><input type = "checkbox" name="checkboxs" value="IndustryCode"/></td>
			<td class="bgc_tt short">所属行业代码</td>
			<td class="long"><s:textfield name="utiIUserUnit.industryCode" value="${utiIUserUnit.industryCode}"
					id="utiIUserUnit.industryCode" cssClass='input_w w_30' maxlength="20" readonly="true"/></td>
			<td><input type = "checkbox" name="checkboxs" value="IndustryName"/></td>
			<td class="bgc_tt short">所属行业名称</td>
			<td class="long"><s:textfield name="utiIUserUnit.industryName" 
					id="utiIUserUnit.industryName" cssClass='input_w w_30' maxlength="100" value="${utiIUserUnit.industryName}" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td><input type = "checkbox" name="checkboxs" value="AreaCode"/></td>
			<td class="bgc_tt short">所属区域代码</td>
			<td class="long"><s:textfield name="utiIUserUnit.areaCode" 
					id="utiIUserUnit.areaCode" cssClass='input_w w_30' maxlength="30" value="${utiIUserUnit.areaCode }" readonly="true"/>
			</td>
			<td><input type = "checkbox" name="checkboxs" value="AreaName"/></td>
			<td class="bgc_tt short">所属区域名称</td>
			<td class="long"><s:textfield name="utiIUserUnit.areaName" value="${utiIUserUnit.areaName}"
					id="utiIUserUnit.areaName" cssClass='input_w w_30' maxlength="100" readonly="true"/></td>
		</tr>
		<tr>
			<td><input type = "checkbox" name="checkboxs" value="RegisteredAddress"/></td>
			<td class="bgc_tt short">注册地址</td>
			<td class="long" colspan="3"><s:textfield name="utiIUserUnit.registeredAddress" value="${utiIUserUnit.registeredAddress}"
					id="utiIUserUnit.registeredAddress" cssClass='input_w w_120' maxlength="200" readonly="true"/></td>
			<td><input type = "checkbox" name="checkboxs" value="MailingAddress"/></td>
		</tr>
		<tr>
			<td class="bgc_tt short">通讯地址</td>
			<td class="long" colspan="3"><s:textfield name="utiIUserUnit.mailingAddress" 
					id="utiIUserUnit.mailingAddress" cssClass='input_w w_120' maxlength="200" value="${utiIUserUnit.mailingAddress }" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td><input type = "checkbox" name="checkboxs" value="PostCode"/></td>
			<td class="bgc_tt short">邮政编码</td>
			<td class="long"><s:textfield name="utiIUserUnit.postCode" value="${utiIUserUnit.postCode}"
					id="utiIUserUnit.postCode" cssClass='input_w w_30' maxlength="6" readonly="true"/></td>
			<td><input type = "checkbox" name="checkboxs" value="ContactPerson"/></td>
			<td class="bgc_tt short">联系人</td>
			<td class="long"><s:textfield name="utiIUserUnit.contactPerson" 
					id="utiIUserUnit.contactPerson" cssClass='input_w w_30' maxlength="200" value="${utiIUserUnit.contactPerson }" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td><input type = "checkbox" name="checkboxs" value="ContactTelephone"/></td>
			<td class="bgc_tt short">联系电话</td>
			<td class="long"><s:textfield name="utiIUserUnit.contactTelephone" value="${utiIUserUnit.contactTelephone}"
					id="utiIUserUnit.fax" cssClass='input_w w_30' maxlength="200" readonly="true"/></td>
			<td><input type = "checkbox" name="checkboxs" value="MailingAddress"/></td>
			<td class="bgc_tt short">传真电话</td>
			<td class="long"><s:textfield name="utiIUserUnit.fax" 
					id="utiIUserUnit.mailingAddress" cssClass='input_w w_30' maxlength="200" value="${utiIUserUnit.fax }" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td><input type = "checkbox" name="checkboxs" value="Email"/></td>
			<td class="bgc_tt short">Email</td>
			<td class="long"><s:textfield name="utiIUserUnit.email" value="${utiIUserUnit.email}"
					id="utiIUserUnit.email" cssClass='input_w w_30' maxlength="200" readonly="true"/></td>
			<td><input type = "checkbox" name="checkboxs" value="Website"/></td>
			<td class="bgc_tt short">网址</td>
			<td class="long"><s:textfield name="utiIUserUnit.website" 
					id="utiIUserUnit.website" cssClass='input_w w_30' maxlength="200" value="${utiIUserUnit.website }" readonly="true" />
			</td>
		</tr>
		</s:if>
		
		<tr>
			<td colspan="4">	
				<center>
					<input type="button" name="cancel" class="button_ty" value="确定" onclick="accInfoSynch()"/>
				</center>
  			</td>
		</tr>
		
		
</table>	
</s:form>
</div>
<div id="content_navigation" class="query" align="center"></div>
<div id="content" class="sort"></div>
<div id="content_navigation" class="query" align="center"></div>
</div>
</body>
</html>
<%@ include file="/common/meta_js.jsp"%>
<script type="text/javascript">
	function accInfoSynch(){
		if(checkLen()){
			fm.action="${ctx}/utiIAccount/accInfoSynch.do";
	        fm.submit();	
	    }
	}
</script>
