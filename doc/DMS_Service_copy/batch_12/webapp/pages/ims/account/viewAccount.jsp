<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.IConstants"%>
<html>
<head>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/i18njs.jsp"%>
</head>
<body id="all_title">
<div id="wrapper">
<div id="container">

<s:form name="fm" action=""	target="accountTreeRight">
<s:hidden name="editType" id="editType" value="${editType}"></s:hidden>
<s:hidden name="userName" value="${utiIAccount.userName}"> </s:hidden>
<s:hidden name="userCode" value="${userCode}"> </s:hidden>
<!--<s:hidden name="faccCode" value="${utiIAccount.faccCode}"> </s:hidden>-->
<s:hidden name="svrCode" value="${svrCode}"> </s:hidden>
<s:hidden name="utiIAccount.validStatus" value="${utiIAccount.validStatus}"> </s:hidden>
<s:hidden name="utiIAccount.createDate" value="${utiIAccount.createDate}"> </s:hidden>
<s:hidden name="utiIAccount.creatorCode" value="${utiIAccount.creatorCode}"> </s:hidden>
<s:hidden name="accSort" id="accSort" value="${accSort }"/>
	<table width="100%" class="fix_table">
		<tr class="top">
            <div id="crash_menu">
            <h2 align="center">
              <s:if test="${editType=='update' }">账户修改</s:if>
              <s:if test="${editType=='view' }">账户查看</s:if>
            </h2>
            </div>	
		</tr>				
		<s:if test="${editType=='view' }">
			<tr>
				<td class="bgc_tt short">账号代码<font color="red">*</font></td>
				<td class="long"><s:textfield name="utiIAccount.accCode" value="${utiIAccount.accCode}"
					 cssClass='input_w w_30' maxlength="32" readonly="true" id="accCode"/></td>
				<td class="bgc_tt short">账号名称</td>
				<td class="long"><s:textfield name="utiIAccount.accName" value="${utiIAccount.accName}"
					 cssClass='input_w w_30' maxlength="30" disabled="true"/></td>				
			</tr>
			<tr>
				<td class="bgc_tt short">账号密码</td>
				<td class="long"><input type="password" name="utiIAccount.password " value="${utiIAccount.password }"
					class='input_w w_30' maxlength="10" readonly="true"/></td>			
				<td class="bgc_tt short">有效标示</td>
				<td class="long"><s:select name="utiIAccount.validStatus" value="${utiIAccount.validStatus}"
					 cssClass='input_w w_30'  disabled="true" list="#@java.util.HashMap@{'a':'请选择','0':'无效','1':'有效'}" /></td>	
			</tr>
			<tr>
				<td class="bgc_tt short">服务代码<font color="red">*</font></td>
				<td class="long"><s:textfield name="utiIAccount.utiIsvr.svrCode" value="${utiIAccount.utiISvr.svrCode}"
					 cssClass='input_w w_30' maxlength="10" disabled="true"/></td>			
				<td class="bgc_tt short">服务名称</td>
				<td class="long"><s:textfield name="utiIAccount.svrName" value="${utiIAccount.svrName}"
					 cssClass='input_w w_30' maxlength="30" disabled="true"/></td>	
			</tr>
			<tr>
				<td class="bgc_tt short">用户代码</td>
				<td class="long"><s:textfield name="utiIAccount.userCode" value="${utiIAccount.userCode}"
					 cssClass='input_w w_30' maxlength="40" disabled="true"/></td>			
				<td class="bgc_tt short">用户名称</td>
				<td class="long"><s:textfield name="userName" value="${utiIAccount.userName}"
					cssClass='input_w w_30' maxlength="30" disabled="true"/></td>	
			</tr>
			<tr>
				<td class="bgc_tt short">有效截止日期</td>
				<td class="long"><s:textfield name="utiIAccount.validendDate" value="${utiIAccount.validendDate}"
					 cssClass='input_w w_30' maxlength="20" readonly="true"/></td>			
				<td class="bgc_tt short">原系统账号代码</td>
				<td class="long"><s:textfield name="utiIAccount.faccCode" value="${utiIAccount.faccCode}"
					 cssClass='input_w w_30' maxlength="10" disabled="true"/></td>	
			</tr>
			${requestScope.table}
		</s:if>
		<s:elseif test="${editType=='update' }">
			<tr>
				<td class="bgc_tt short">账号代码<font color="red">*</font></td>
				<td class="long"><s:textfield name="utiIAccount.accCode" value="${utiIAccount.accCode}"
					 cssClass='input_w w_30' maxlength="32" readonly="true"/></td>
				<td class="bgc_tt short">账号名称</td>
				<td class="long"><s:textfield name="utiIAccount.accName" value="${utiIAccount.accName}" id="accName"
					 cssClass='input_w w_30' maxlength="30"/></td>				
				
			</tr>
			<tr>
				<td class="bgc_tt short">账号密码</td>
				<td class="long"><input type="password" name="utiIAccount.password" value="${utiIAccount.password}"
					class='input_w w_30' maxlength="10" readonly="true"/></td>
				<td class="bgc_tt short">新账号密码</td>
				<td class="long"><input type="password" name="newPassword" id="newPassword" value="${newPassword}"
					class='input_w w_30' maxlength="10"/></td>		
			</tr>
			<tr>
				<td class="bgc_tt short">服务代码<font color="red">*</font></td>
				<td class="long"><s:textfield name="utiIAccount.utiIsvr.svrCode" value="${utiIAccount.utiISvr.svrCode}"
					 cssClass='input_w w_30' maxlength="10" ondblclick="code_CodeQuery(this, 'SvrCode', '0,1', 'Y','')"
				onkeyup="code_CodeQuery(this, 'SvrCode', '0,1', 'Y','')"
				onchange="code_CodeChange(this, 'SvrCode', '0,1', 'Y','')" readonly="true"/>
				</td>
				<td class="bgc_tt short">服务名称</td>
				<td class="long"><s:textfield name="utiIAccount.svrName" value="${utiIAccount.svrName}"
					 cssClass='input_w w_30' maxlength="30" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="bgc_tt short">用户代码</td>
				<td class="long"><s:textfield name="utiIAccount.userCode" value="${utiIAccount.userCode}"
					 cssClass='input_w w_30' maxlength="40" readonly="true"/></td>			
				<td class="bgc_tt short">用户名称</td>
				<td class="long"><s:textfield name="utiIAccount.userName" value="${utiIAccount.userName}"
					cssClass='input_w w_30' maxlength="30" readonly="true"/></td>	
			</tr>
			<tr>
				<td class="bgc_tt short">有效截止日期</td>
			<td class="long"><input type="text" readonly name="utiIAccount.validendDate"  class='input_w w_30 Wdate' onFocus="WdatePicker()" id="validenddate" value="${utiIAccount.validendDate}">
			</td>			
				<td class="bgc_tt short">原系统账号代码</td>
				<td class="long"><s:textfield name="utiIAccount.faccCode" value="${utiIAccount.faccCode}"
					 cssClass='input_w w_30' maxlength="10" readonly="true"/></td>	
			</tr>
			${requestScope.table}
		</s:elseif>
        
	</table>
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr align="center" class="top">
			<s:if test="${editType=='view' }">
				<td><input type="button" value="返回" class="button_ty"
				onclick="goback()"></td>
			</s:if>
            <s:if test="${editType=='update' }">
                <td><input type="button" name="update" value="保存" class="button_ty"
				onclick="updateMethod()"></td>
				<td><input type="button" value="返回" class="button_ty"
				onclick="goback()"></td>
            </s:if>
		</tr>
	</table>
</s:form>
</div>
</div>
</body>
</html>
<%@ include file="/common/meta_js.jsp"%>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script type='text/javascript' src='/ims/dwr/interface/Ims.js'></script>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
<script language="javascript">
	var ok = "yes";
	//返回
	function goback(){
		fm.action="${ctx}/utiIAccount/userAndAccountQuery.do";
		fm.target="utiIUserQueryRight";
	    fm.submit();
	}
	//保存修改
	function updateMethod(){
		if(ok=="yes"){
			 var accName = document.getElementById("accName").value;
				var i = 0;
				for(var j=0;j<accName.length;j++){
					 if(accName.charAt(j)<='\255') {   
						 i++;
					  }else {   
						  i=i+3;
					 }	
				}
				if(i > 30){
					alert("账户名称过长，请检查！");	
					return false;
				}
			document.getElementById("svrCode").value = document.getElementById("utiIAccount.utiIsvr.svrCode").value;
			if(checkForm()){
				fm.action = "${ctx}/utiIAccount/updateAccount.do";
				fm.target="utiIUserQueryRight";
				fm.submit();
			}else {
				alert('页面输入有误，请检查！');
				return false;
			}
			
		}else{
			alert("密码输入有误");
			document.getElementById("newPassword").value = "";
		}
	}

	function checkForm(){
		return YAHOO.quote.data.datacheck('fm');
		}	
</script>