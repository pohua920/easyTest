<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<%@ page import="cn.com.sinosoft.saa.model.SaaTask"%>
<head>
<title>用户信息添加</title>
<%@ include file="/common/meta_css.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_js.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.*"%>
<script type="text/javascript" src="${ctx}/common/js/sinosoft.js"></script>
<script type="text/javascript" src="${ctx}/common/js/MulLine.js"></script>
<script type="text/javascript" src="${ctx}/common/js/Common.js"></script>
<script type="text/javascript" src="${ctx }/common/dtree/dtree.js"></script>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
</head>
<body id="all_title">
<div id="wrapper">
<div id="container">
<div id="crash_menu">
	<h2 align="center">用户信息添加</h2>
</div>
<s:form name="fm" action="" method="post">
	<table class="fix_table">
		<tr>
			<td class="bgc_tt short">用户代码</td>
			<td class="long"><s:textfield name="utiIUserUnit.userCode" value="${utiIUserUnit.userCode}"
					id="utiIUserUnit.userCode" cssClass='input_w w_15' maxlength="20" readonly="true" /></td>
			<td class="bgc_tt short">用户名称</td>
			<td class="long"><s:textfield name="utiIUserUnit.userName" value="${utiIUserUnit.userName}"
					id="utiIUserUnit.userName" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
		</tr>
		<tr>
			<td class="bgc_tt short">所属机构代码</td>
			<td class="long"><s:textfield name="utiIUserUnit.comCode" value="${utiIUserUnit.comCode}"
					id="utiIUserUnit.comCode" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
			<td class="bgc_tt short">最新用户代码</td>
			<td class="long"><s:textfield name="utiIUserUnit.newUserCode" 
					id="utiIUserUnit.newUserCode" cssClass='input_w w_15' maxlength="20" value="${utiIUserUnit.newUserCode }" />
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">登记机关</td>
			<td class="long"><s:textfield name="utiIUserUnit.registrationAgency" value="${utiIUserUnit.registrationAgency}"
					id="utiIUserUnit.registrationAgency" cssClass='input_w w_15' maxlength="20"/></td>
			<td class="bgc_tt short">登记注册号</td>
			<td class="long"><s:textfield name="utiIUserUnit.registrationNumber" value="${utiIUserUnit.registrationNumber}"
					id="utiIUserUnit.registrationNumber" cssClass='input_w w_15' maxlength="20"/>
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">组织机构代码</td>
			<td class="long"><s:textfield name="utiIUserUnit.organizationCode" value="${utiIUserUnit.organizationCode}"
					id="utiIUserUnit.organizationCode" cssClass='input_w w_15' maxlength="10"/></td>
			<td class="bgc_tt short">贷款卡编码</td>
			<td class="long"><s:textfield name="utiIUserUnit.loancardCode" 
					id="utiIUserUnit.loancardCode" cssClass='input_w w_15' maxlength="20" value="${utiIUserUnit.loancardCode}" />
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">税务登记证号码</td>
			<td class="long"><s:textfield name="utiIUserUnit.nationalTaxRegistNo" value="${utiIUserUnit.nationalTaxRegistNo}"
					id="utiIUserUnit.nationalTaxRegistNo" cssClass='input_w w_15' maxlength="20"/></td>
			<td class="bgc_tt short">基本账户行</td>
			<td class="long"><s:textfield name="utiIUserUnit.openBank" value="${utiIUserUnit.openBank}"
					id="utiIUserUnit.openBank" cssClass='input_w w_15' maxlength="20"/>
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">从业人数</td>
			<td class="long"><s:textfield name="utiIUserUnit.empNumber" value="${utiIUserUnit.empNumber}"
					id="utiIUserUnit.empNumber" cssClass='input_w w_15' maxlength="20" onblur="checkNum(this);"/></td>
			<td class="bgc_tt short">注册登记日期</td>
			<td class="long"><input type="text" readonly="true" name="utiIUserUnit.registrationDate" 
					id="utiIUserUnit.registrationDate" cssClass='input_w w_15' maxlength="20" value="${utiIUserUnit.registrationDate}"/>
				<img src="${ctx}/pages/image/time/date_icon.gif" alt="点击显示时间面板" id="imgBtn1" width="14" height="14" /> 
				<span class="calender-panel">
					<div id="calContainer1" style="position: absolute;"></div>
				</span>
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">注册资本</td>
			<td class="long"><s:textfield name="utiIUserUnit.registeredCapital" value="${utiIUserUnit.registeredCapital}"
					id="utiIUserUnit.registeredCapital" cssClass='input_w w_15' maxlength="20" onblur="checkNum(this);"/></td>
			<td class="bgc_tt short">营业执照到期日期</td>
			<td class="long"><input type="text" readonly="true" name="utiIUserUnit.licenseExpiringDate" 
					id="utiIUserUnit.licenseExpiringDate" cssClass='input_w w_15' maxlength="20" value="${utiIUserUnit.licenseExpiringDate}"/>
				<img src="${ctx}/pages/image/time/date_icon.gif" alt="点击显示时间面板" id="imgBtn2" width="14" height="14" /> 
				<span class="calender-panel">
					<div id="calContainer2" style="position: absolute;"></div>
				</span>
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">实收资本</td>
			<td class="long"><s:textfield name="utiIUserUnit.capital" value="${utiIUserUnit.capital}"
					id="utiIUserUnit.capital" cssClass='input_w w_15' maxlength="20" onblur="checkNum(this);"/></td>
			<td class="bgc_tt short">登记注册类型</td>
			<td class="long"><s:textfield name="utiIUserUnit.registrationType" value="${utiIUserUnit.registrationType}"
					id="utiIUserUnit.registrationType" cssClass='input_w w_15' maxlength="20"/>
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">经营范围</td>
			<td class="long"><s:textfield name="utiIUserUnit.businesssCope" value="${utiIUserUnit.businesssCope}"
					id="utiIUserUnit.businesssCope" cssClass='input_w w_15' maxlength="20"/></td>
			<td class="bgc_tt short">主营业务</td>
			<td class="long"><s:textfield name="utiIUserUnit.mainOperation" value="${utiIUserUnit.mainOperation}"
					id="utiIUserUnit.mainOperation" cssClass='input_w w_15' maxlength="20"/>
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">所属行业代码</td>
			<td class="long"><s:textfield name="utiIUserUnit.industryCode" value="${utiIUserUnit.industryCode}"
					id="utiIUserUnit.industryCode" cssClass='input_w w_15' maxlength="20"/></td>
			<td class="bgc_tt short">所属行业名称</td>
			<td class="long"><s:textfield name="utiIUserUnit.industryName" 
					id="utiIUserUnit.industryName" cssClass='input_w w_15' maxlength="20" value="${utiIUserUnit.industryName}" />
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">所属区域代码</td>
			<td class="long"><s:textfield name="utiIUserUnit.areaCode" 
					id="utiIUserUnit.areaCode" cssClass='input_w w_15' maxlength="20" value="${utiIUserUnit.areaCode }" />
			</td>
			<td class="bgc_tt short">所属区域名称</td>
			<td class="long"><s:textfield name="utiIUserUnit.areaName" value="${utiIUserUnit.areaName}"
					id="utiIUserUnit.areaName" cssClass='input_w w_15' maxlength="20"/></td>
		</tr>
		<tr>
			<td class="bgc_tt short">注册地址</td>
			<td class="long"><s:textfield name="utiIUserUnit.registeredAddress" value="${utiIUserUnit.registeredAddress}"
					id="utiIUserUnit.registeredAddress" cssClass='input_w w_15' maxlength="20"/></td>
			<td class="bgc_tt short">通讯地址</td>
			<td class="long"><s:textfield name="utiIUserUnit.mailingAddress" 
					id="utiIUserUnit.mailingAddress" cssClass='input_w w_15' maxlength="20" value="${utiIUserUnit.mailingAddress }" />
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">邮政编码</td>
			<td class="long"><s:textfield name="utiIUserUnit.postCode" value="${utiIUserUnit.postCode}"
					id="utiIUserUnit.postCode" cssClass='input_w w_15' maxlength="20"/></td>
			<td class="bgc_tt short">联系人</td>
			<td class="long"><s:textfield name="utiIUserUnit.contactPerson" 
					id="utiIUserUnit.contactPerson" cssClass='input_w w_15' maxlength="20" value="${utiIUserUnit.contactPerson }" />
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">联系电话</td>
			<td class="long"><s:textfield name="utiIUserUnit.fax" value="${utiIUserUnit.fax}"
					id="utiIUserUnit.fax" cssClass='input_w w_15' maxlength="20"/></td>
			<td class="bgc_tt short">传真电话</td>
			<td class="long"><s:textfield name="utiIUserUnit.mailingAddress" 
					id="utiIUserUnit.mailingAddress" cssClass='input_w w_15' maxlength="20" value="${utiIUserUnit.mailingAddress }" />
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">Email</td>
			<td class="long"><s:textfield name="utiIUserUnit.email" value="${utiIUserUnit.email}"
					id="utiIUserUnit.email" cssClass='input_w w_15' maxlength="20"/></td>
			<td class="bgc_tt short">网址</td>
			<td class="long"><s:textfield name="utiIUserUnit.website" 
					id="utiIUserUnit.website" cssClass='input_w w_15' maxlength="20" value="${utiIUserUnit.website }" />
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">信息修改人</td>
				<td class="long"><s:textfield name="utiIUserUnit.updaterCode" 
					id="utiIUserUnit.updaterCode" cssClass='input_w w_15' maxlength="3" value="${utiIUserUnit.updaterCode}" />
			</td>
			<td class="bgc_tt short">信息修改日期</td>
			<td class="long"><s:textfield name="utiIUserUnit.updateDate" 
					id="utiIUserUnit.updateDate" cssClass='input_w w_15' maxlength="3" value="${utiIUserUnit.updateDate}" />
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<center>
					<input type="button" name="save" class="button_ty" value="保存" onclick="return addMethod();"/>
					<input type="button" name="cancel" class="button_ty" value="取消" onclick="window.history.back(-1)"/>
				</center>
  			</td>
		</tr>
	</table>
</s:form>
</div>
</div>
</body>
</html>
<%@ include file="/common/meta_js.jsp"%>
<script language="javascript" src="${ctx}/common/js/newCalendar.js"></script>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script type="text/javascript">
	function checkNum(msg){
		var empNum  = document.getElementById("utiIUserUnit.empNumber").value;
		var capital = document.getElementById("utiIUserUnit.capital").value;
		var registeredCapital = document.getElementById("utiIUserUnit.registeredCapital").value;
		if(!empNum.match(/^[0-9]+$/)&&(msg.id)=="utiIUserUnit.empNumber") {
			alert("请输入从业人数");
			document.getElementById("utiIUserUnit.empNumber").value ="";
		}
		if(!capital.match(/^[0-9]+$/)&&(msg.id)=="utiIUserUnit.capital") {
			alert("请输入注册资本(数字)");
			document.getElementById("utiIUserUnit.capital").value ="";
		}
		if(!registeredCapital.match(/^[0-9]+$/)&&(msg.id)=="utiIUserUnit.registeredCapital") {
			alert("请输入实收资本(数字)");
			document.getElementById("utiIUserUnit.registeredCapital").value ="";
		}
	}
	function addMethod(){
		alert(document.getElementById("utiIUserUnit.organizationCode").value);
		fm.action="${ctx}/utiIUser/insertUserUnit.do";
		fm.submit();
		return true;
	}
	init_calendar("calContainer1","imgBtn1","utiIUserUnit.registrationDate");
	init_calendar("calContainer2","imgBtn2","utiIUserUnit.licenseExpiringDate");
</script>