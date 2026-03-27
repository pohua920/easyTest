<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<%@ page import="cn.com.sinosoft.saa.model.SaaTask"%>
<head>
<title>用户信息查看</title>
<%@ include file="/common/meta_css.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_js.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.*"%>
<%
	String sex = (String)request.getAttribute("sex");
	String flag = (String)request.getAttribute("flag");
%>
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
	<h2 align="center">用户信息查看</h2>
</div>
<s:form name="fm" action="" method="post">
	<table class="fix_table">
		<tr>
			<td class="bgc_tt short">用户代码</td>
			<td class="long"><s:textfield name="utiIUserIdv.userCode" value="${utiIUserIdv.userCode}"
					id="utiIUserIdv.userCode" cssClass='input_w w_15' maxlength="20" readonly="true" /></td>
			<td class="bgc_tt short">用户名称</td>
			<td class="long"><s:textfield name="utiIUserIdv.userName" value="${utiIUserIdv.userName}"
					id="utiIUserIdv.userName" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
		</tr>
		<tr>
			<td class="bgc_tt short">所属机构代码</td>
			<td class="long"><s:textfield name="utiIUserIdv.comCode" value="${utiIUserIdv.comCode}"
					id="utiIUserIdv.comCode" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
			<td class="bgc_tt short">最新用户代码</td>
			<td class="long"><s:textfield name="utiIUserIdv.newUserCode" 
					id="utiIUserIdv.newUserCode" cssClass='input_w w_15' maxlength="20" readonly="true" value="${utiIUserIdv.newUserCode }" />
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">年龄</td>
			<td class="long"><s:textfield name="utiIUserIdv.age" value="${utiIUserIdv.age}"
					id="utiIUserIdv.age" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
			<td class="bgc_tt short">出生日期</td>
			<td class="long"><s:textfield readonly="true" name="utiIUserIdv.birthDate" 
					id="utiIUserIdv.birthDate" cssClass='input_w w_15' maxlength="20" value="${utiIUserIdv.birthDate}" />
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">性别</td>
			<td class="long">
				<s:textfield name="utiIUserIdv.sex" value="<%=sex %>" cssClass='input_w w_15' id="utiIUserIdv.sex"  maxlength="20" readonly="true"/>
			</td>
			<td class="bgc_tt short">健康状况</td>
			<td class="long"><s:textfield name="utiIUserIdv.health" 
					id="utiIUserIdv.health" cssClass='input_w w_15' maxlength="20" value="${utiIUserIdv.health }" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">家庭电话</td>
			<td class="long"><s:textfield name="utiIUserIdv.homePhone" value="${utiIUserIdv.homePhone}"
					id="utiIUserIdv.homePhone" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
			<td class="bgc_tt short">办公电话</td>
			<td class="long"><s:textfield name="utiIUserIdv.officePhone" 
					id="utiIUserIdv.officePhone" cssClass='input_w w_15' maxlength="20" value="${utiIUserIdv.officePhone }" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">电子邮件</td>
			<td class="long"><s:textfield name="utiIUserIdv.email" value="${utiIUserIdv.email}"
					id="utiIUserIdv.email" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
			<td class="bgc_tt short">手机号码</td>
			<td class="long"><s:textfield name="utiIUserIdv.mobile" value="${utiIUserIdv.mobile}"
					id="utiIUserIdv.mobile" cssClass='input_w w_15' maxlength="20" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">邮件地址</td>
			<td class="long"><s:textfield name="utiIUserIdv.postAddress" value="${utiIUserIdv.postAddress}"
					id="utiIUserIdv.postAddress" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
			<td class="bgc_tt short">邮政编码</td>
			<td class="long"><s:textfield name="utiIUserIdv.postCode" value="${utiIUserIdv.postCode}"
					id="utiIUserIdv.postCode" cssClass='input_w w_15' maxlength="20" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">传真号码</td>
			<td class="long"><s:textfield name="utiIUserIdv.faxNumber" value="${utiIUserIdv.faxNumber}"
					id="utiIUserIdv.faxNumber" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
			<td class="bgc_tt short">身份证</td>
			<td class="long"><s:textfield name="utiIUserIdv.identifyNumber" value="${utiIUserIdv.identifyNumber}"
					id="utiIUserIdv.identifyNumber" cssClass='input_w w_15' maxlength="20" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">职业</td>
			<td class="long"><s:textfield name="utiIUserIdv.occupationCode" value="${utiIUserIdv.occupationCode}"
					id="utiIUserIdv.occupationCode" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
			<td class="bgc_tt short">学历</td>
			<td class="long"><s:textfield name="utiIUserIdv.educationCode" value="${utiIUserIdv.educationCode}"
					id="utiIUserIdv.educationCode" cssClass='input_w w_15' maxlength="20" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">工作单位</td>
			<td class="long"><s:textfield name="utiIUserIdv.unit" value="${utiIUserIdv.unit}"
					id="utiIUserIdv.unit" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
			<td class="bgc_tt short">单位地址</td>
			<td class="long"><s:textfield name="utiIUserIdv.unitAddress" value="${utiIUserIdv.unitAddress}"
					id="utiIUserIdv.unitAddress" cssClass='input_w w_15' maxlength="20" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">开户银行</td>
			<td class="long"><s:textfield name="utiIUserIdv.bank" value="${utiIUserIdv.bank}"
					id="utiIUserIdv.bank" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
			<td class="bgc_tt short">账号</td>
			<td class="long"><s:textfield name="utiIUserIdv.account" 
					id="utiIUserIdv.account" cssClass='input_w w_15' maxlength="20" value="${utiIUserIdv.account }" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">建立机构</td>
			<td class="long"><s:textfield name="utiIUserIdv.terminalCode" value="${utiIUserIdv.terminalCode}"
					id="utiIUserIdv.terminalCode" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
			<td class="bgc_tt short">用户来源</td>
			<td class="long"><s:textfield name="utiIUserIdv.source" 
					id="utiIUserIdv.source" cssClass='input_w w_15' maxlength="20" value="${utiIUserIdv.source }" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">客户标识</td>
			<td class="long">
	      		<s:textfield name="utiIUserIdv.customerFlag" value="<%=flag %>" cssClass='input_w w_15' id="utiIUserIdv.customerFlag"  maxlength="20" readonly="true"/>
			</td>
			<td class="bgc_tt short">标识字段</td>
			<td class="long"><s:textfield name="utiIUserIdv.flag" 
					id="utiIUserIdv.flag" cssClass='input_w w_15' maxlength="3" value="${utiIUserIdv.flag}" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">信息创建人</td>
			<td class="long">
				<s:textfield name="utiIUserIdv.creatorCode" 
					id="utiIUserIdv.creatorCode" cssClass='input_w w_15' maxlength="3" value="${utiIUserIdv.creatorCode}" readonly="true"/>
			</td>
			<td class="bgc_tt short">信息创建日期</td>
			<td class="long"><s:textfield name="utiIUserIdv.createDate" 
					id="utiIUserIdv.createDate" cssClass='input_w w_15' maxlength="3" value="${utiIUserIdv.createDate}" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">信息修改人</td>
			<td class="long">
				<s:textfield name="utiIUserIdv.updaterCode" 
					id="utiIUserIdv.updaterCode" cssClass='input_w w_15' maxlength="3" value="${utiIUserIdv.updaterCode}" readonly="true"/>
			</td>
			<td class="bgc_tt short">信息修改日期</td>
			<td class="long"><s:textfield name="utiIUserIdv.updateDate" 
					id="utiIUserIdv.updateDate" cssClass='input_w w_15' maxlength="3" value="${utiIUserIdv.updateDate}" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">印章</td>
			<td class="long"><s:textfield name="utiIUserIdv.seal" value="${utiIUserIdv.seal}"
					id="utiIUserIdv.seal" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
			<td class="bgc_tt short">备注</td>
			<td class="long"><s:textfield name="utiIUserIdv.remark" 
					id="utiIUserIdv.remark" cssClass='input_w w_15' maxlength="20" value="${utiIUserIdv.remark }" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<center>
					<input type="button" name="cancel" class="button_ty" value="确定" onclick="window.history.back(-1)"/>
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
	
</script>