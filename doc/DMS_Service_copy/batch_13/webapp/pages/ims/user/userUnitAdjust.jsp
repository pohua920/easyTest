<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.IConstants"%>
<html>
<head>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.*"%>

<script language="javascript" src="${ctx}/common/js/newCalendar.js"></script>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script type="text/javascript" src="${ctx}/common/js/sinosoft.js"></script>
<script type="text/javascript" src="${ctx}/common/js/MulLine.js"></script>
<script type="text/javascript" src="${ctx}/common/js/Common.js"></script>
<script type="text/javascript" src="${ctx }/common/dtree/dtree.js"></script>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
</head>
<body id="all_title">
<div id="wrapper">
<div id="container">

<s:form name="fm" action=""	>
<s:hidden name="editType" id="editType" value="${editType}"></s:hidden>
<s:hidden name="userType" id="userType1" value="${userType}"></s:hidden>
	<table width="100%" class="fix_table">
		<tr class="top">
            <div id="crash_menu">
            <h2 align="center">
              	用户机构调整
            </h2>
            </div>	
		</tr>				
<!----------- 查看 -------------->		
		<s:hidden name="utiIUser.userCode "/>
        <s:hidden name="utiIUser.userSort " value="${utiIUser.userSort}"/>
			<tr>
				<td class="bgc_tt short">用户代码</td>
				<td class="long"><s:textfield name="utiIUser.userCode" value="${utiIUser.userCode}"
					id="userCode" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
				<td class="bgc_tt short">用户名称</td>
				<td class="long"><s:textfield name="utiIUser.userName" value="${utiIUser.userName}"
					id="userName" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>				
				
			</tr>
			<tr>
				<td class="bgc_tt short">用户类型</td>
				<td class="long">
					    <div id="userTypeDiv1" class="selectui-indiv">
					        <div class="selectConfig">
					        <div class="codeType">StaticSelect</div>
					        </div>
					        <c:set var="checked" value="${utiIUser.userType}" />
					        <ce:select name="utiIUser.userType"  cssClass="selectui-input" disabled="true" value="${checked}" 
					        list="#@java.util.HashMap@{'01':'员工用户','02':'业务员用户','03':'虚拟用户','04':'合作伙伴用户','06':'企业用户','07':'个人用户','98':'临时用户','99':'其他用户'}" />
					    </div>
			    </td>			
				<td class="bgc_tt short">原归属机构</td>
				<td class="long"><s:textfield name="utiIUser.comCode" value="${utiIUser.comCode}"
					id="comcode" cssClass='input_w w_15' maxlength="20" disabled="true"/></td>	
			</tr>
      <!-- 新归属机构 -->
            <tr>
                <td class="bgc_tt short">新归属机构</td>
                <td class="long" colspan="3">
	                <div id="gradeTrees" align="left"></div>
						${treeScript}
	                </div>
                </td>
            </tr>
            <!-- <tr>
			<td class="bgc_tt short">登记机关</td>
			<td class="long"><s:textfield name="utiIUserUnit.registrationAgency" value="${utiIUserUnit.registrationAgency}"
					id="utiIUserUnit.registrationAgency" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
			<td class="bgc_tt short">登记注册号</td>
			<td class="long"><s:textfield name="utiIUserUnit.registrationNumber" value="${utiIUserUnit.registrationNumber}"
					id="utiIUserUnit.registrationNumber" cssClass='input_w w_15' maxlength="20" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">组织机构代码</td>
			<td class="long"><s:textfield name="utiIUserUnit.organizationCode" value="${utiIUserUnit.organizationCode}"
					id="utiIUserUnit.organizationCode" cssClass='input_w w_15' maxlength="10" readonly="true"/></td>
			<td class="bgc_tt short">贷款卡编码</td>
			<td class="long"><s:textfield name="utiIUserUnit.loancardCode" 
					id="utiIUserUnit.loancardCode" cssClass='input_w w_15' maxlength="20" value="${utiIUserUnit.loancardCode}" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">税务登记证号码</td>
			<td class="long"><s:textfield name="utiIUserUnit.nationalTaxRegistNo" value="${utiIUserUnit.nationalTaxRegistNo}"
					id="utiIUserUnit.nationalTaxRegistNo" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
			<td class="bgc_tt short">基本账户行</td>
			<td class="long"><s:textfield name="utiIUserUnit.openBank" value="${utiIUserUnit.openBank}"
					id="utiIUserUnit.openBank" cssClass='input_w w_15' maxlength="20" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">从业人数</td>
			<td class="long"><s:textfield name="utiIUserUnit.empNumber" value="${utiIUserUnit.empNumber}"
					id="utiIUserUnit.empNumber" cssClass='input_w w_15' maxlength="20" onblur="checkNum(this);" readonly="true"/></td>
			<td class="bgc_tt short">注册登记日期</td>
			<td class="long"><s:textfield readonly="true" name="utiIUserUnit.registrationDate" 
					id="utiIUserUnit.registrationDate" cssClass='input_w w_15' maxlength="20" value="${utiIUserUnit.registrationDate}"/>
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">注册资本</td>
			<td class="long"><s:textfield name="utiIUserUnit.registeredCapital" value="${utiIUserUnit.registeredCapital}"
					id="utiIUserUnit.registeredCapital" cssClass='input_w w_15' maxlength="20" onblur="checkNum(this);" readonly="true"/></td>
			<td class="bgc_tt short">营业执照到期日期</td>
			<td class="long"><s:textfield readonly="true" name="utiIUserUnit.licenseExpiringDate" 
					id="utiIUserUnit.licenseExpiringDate" cssClass='input_w w_15' maxlength="20" value="${utiIUserUnit.licenseExpiringDate}"/>
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">实收资本</td>
			<td class="long"><s:textfield name="utiIUserUnit.capital" value="${utiIUserUnit.capital}"
					id="utiIUserUnit.capital" cssClass='input_w w_15' maxlength="20" onblur="checkNum(this);" readonly="true"/></td>
			<td class="bgc_tt short">登记注册类型</td>
			<td class="long"><s:textfield name="utiIUserUnit.registrationType" value="${utiIUserUnit.registrationType}"
					id="utiIUserUnit.registrationType" cssClass='input_w w_15' maxlength="20" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">经营范围</td>
			<td class="long"><s:textfield name="utiIUserUnit.businesssCope" value="${utiIUserUnit.businesssCope}"
					id="utiIUserUnit.businesssCope" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
			<td class="bgc_tt short">主营业务</td>
			<td class="long"><s:textfield name="utiIUserUnit.mainOperation" value="${utiIUserUnit.mainOperation}"
					id="utiIUserUnit.mainOperation" cssClass='input_w w_15' maxlength="20" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">所属行业代码</td>
			<td class="long"><s:textfield name="utiIUserUnit.industryCode" value="${utiIUserUnit.industryCode}"
					id="utiIUserUnit.industryCode" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
			<td class="bgc_tt short">所属行业名称</td>
			<td class="long"><s:textfield name="utiIUserUnit.industryName" 
					id="utiIUserUnit.industryName" cssClass='input_w w_15' maxlength="20" value="${utiIUserUnit.industryName}" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">所属区域代码</td>
			<td class="long"><s:textfield name="utiIUserUnit.areaCode" 
					id="utiIUserUnit.areaCode" cssClass='input_w w_15' maxlength="20" value="${utiIUserUnit.areaCode }" readonly="true"/>
			</td>
			<td class="bgc_tt short">所属区域名称</td>
			<td class="long"><s:textfield name="utiIUserUnit.areaName" value="${utiIUserUnit.areaName}"
					id="utiIUserUnit.areaName" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
		</tr>
		<tr>
			<td class="bgc_tt short">注册地址</td>
			<td class="long"><s:textfield name="utiIUserUnit.registeredAddress" value="${utiIUserUnit.registeredAddress}"
					id="utiIUserUnit.registeredAddress" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
			<td class="bgc_tt short">通讯地址</td>
			<td class="long"><s:textfield name="utiIUserUnit.mailingAddress" 
					id="utiIUserUnit.mailingAddress" cssClass='input_w w_15' maxlength="20" value="${utiIUserUnit.mailingAddress }" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">邮政编码</td>
			<td class="long"><s:textfield name="utiIUserUnit.postCode" value="${utiIUserUnit.postCode}"
					id="utiIUserUnit.postCode" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
			<td class="bgc_tt short">联系人</td>
			<td class="long"><s:textfield name="utiIUserUnit.contactPerson" 
					id="utiIUserUnit.contactPerson" cssClass='input_w w_15' maxlength="20" value="${utiIUserUnit.contactPerson }" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">联系电话</td>
			<td class="long"><s:textfield name="utiIUserUnit.contactTelephone" value="${utiIUserUnit.contactTelephone}"
					id="utiIUserUnit.contactTelephone" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
			<td class="bgc_tt short">传真电话</td>
			<td class="long"><s:textfield name="utiIUserUnit.fax" 
					id="utiIUserUnit.fax" cssClass='input_w w_15' maxlength="20" value="${utiIUserUnit.fax }" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">Email</td>
			<td class="long"><s:textfield name="utiIUserUnit.email" value="${utiIUserUnit.email}"
					id="utiIUserUnit.email" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
			<td class="bgc_tt short">网址</td>
			<td class="long"><s:textfield name="utiIUserUnit.website" 
					id="utiIUserUnit.website" cssClass='input_w w_15' maxlength="20" value="${utiIUserUnit.website }" readonly="true" />
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">信息修改人</td>
				<td class="long"><s:textfield name="utiIUserUnit.updaterCode" 
					id="utiIUserUnit.updaterCode" cssClass='input_w w_15' maxlength="3" value="${utiIUserUnit.updaterCode}" readonly="true"/>
			</td>
			<td class="bgc_tt short">信息修改日期</td>
			<td class="long"><s:textfield name="utiIUserUnit.updateDate" 
					id="utiIUserUnit.updateDate" cssClass='input_w w_15' maxlength="3" value="${utiIUserUnit.updateDate}" readonly="true"/>
			</td>
		</tr>
        <tr>
			<td class="bgc_tt short">最新用户代码</td>
			<td class="long" colspan="3"><s:textfield name="utiIUserUnit.newUserCode" 
					id="utiIUserUnit.newUserCode" cssClass='input_w w_15' maxlength="20" readonly="true" value="${utiIUserUnit.newUserCode }" />
			</td>
		</tr>     -->
        
		
        
	</table>
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr align="center" class="top">
			<td><input type="button" value="保存" class="button_ty"
			onclick="return updateMethod()"></td>
           
		</tr>
	</table>
</s:form>
</div>
</div>
</body>
</html>
<script language="javascript">
var tabView = new YAHOO.widget.TabView('tabdemo');
var tabFlag = new Array();
tabFlag.push("taskIframe1");


  function updateMethod(){
	  		 var comCode = getCheckValue();
	  		 if(comCode == null){
		  		alert('请选择一个机构！'); 
		  		return false;
		  	 }else {
				 fm.action="${ctx}/utiIUser/adjustUserOrg.do?comCode=" + comCode;
				 target="pages";
				 fm.submit();
		         return true;
			  }

  }

  
</script>