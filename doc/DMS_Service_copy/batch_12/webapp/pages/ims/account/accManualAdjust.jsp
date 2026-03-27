<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.IConstants"%>

<html>
<head>
<title>手工调整</title>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>

</head>
<body id="all_title">
<div id="wrapper">
<div id="container">
<div id="crash_menu">
<h2 align="center">手工调整</h2>
</div>
<s:form name="fm" action="" target="accountTreeRight">
	<input type="hidden" name="userCode" value="${userCode}"/>
	<input type="hidden" name="userName" value="${userName }"/>
	<input type="hidden" name="svrName" value="${svrName }"/>
	<s:hidden name="accSort" id="accSort" value="${accSort }"/>
	<table class="fix_table">	
		
         <tr>
            <td class="bgc_tt short">用户代码<font color="red">*</font></td>
            <td class="long"><input name="utiIAccount.userCode"
				id="utiIUser.userCode" class='input_y w_p20'
				ondblclick="code_CodeQuery(this, 'UserCode', '0,1', 'Y','')"
				onkeyup="code_CodeQuery(this, 'UserCode', '0,1', 'Y','')"
				onchange="code_CodeChange(this, 'UserCode', '0,1', 'Y','')"/>
                
            </td>
		</tr>
        <tr>	
			<td class="bgc_tt short">用户名称</td>
			<td class="long"><input name="utiIAccount.userName"
				id="comName" class='input_w w_15' readonly>
            </td>
		</tr>
		<tr>
			<td class="bgc_tt short">帐号代码</td>			
			<td class="long"><input type="text" name="utiIAccount.accCode"  class='input_w w_15 dc-chk' value="${utiIAccount.accCode}" readonly>
							<nobr id="pwdMsg"></nobr></td>
		</tr>
		<tr>
			<td class="bgc_tt short">帐号名称</td>			
			<td class="long"><input type="text" name="utiIAccount.accName"  class='input_w w_15'  value="${utiIAccount.accName}" readonly></td>
		</tr>
        <tr>
			<td class="bgc_tt short">服务代码</td>			
			<td class="long"><input type="text" name="utiIAccount.utiISvr.svrCode"  class='input_w w_15' value="${utiIAccount.utiISvr.svrCode }" readonly></td>
		</tr>
        <tr>
			<td class="bgc_tt short">服务名称</td>			
			<td class="long"><input type="text" name="utiIAccount.svrName"  class='input_w w_15' value="${utiIAccount.svrName }" readonly></td>
		</tr>		
	
		<tr align="center">
			<td align="center"  colspan="4">
                <input type="button" class="button_ty" value="确定" onclick="addMatch()">
            </td>
		</tr>
	</table>
</s:form></div>
<div id="content_navigation" class="query" align="center"></div>
<div id="content" class="sort"></div>
<div id="content_navigation" class="query" align="center"></div>
</div>
</body>
</html>
<%@ include file="/common/meta_js.jsp"%>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script type='text/javascript' src='/ims/dwr/interface/Ims.js'></script>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
<script type="text/javascript">

	function queryUser(){
		vURL='${ctx}/pages/ims/account/userMatchQueryUser.jsp';
		window.open(vURL,"","width=400,height=400,top=200,left=300,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=yes");
	}
	function insertMethod(){
		vURL='${ctx}/utiIUser/prepareSelectUserType.do?editType=insert&comCode=64000000';
		window.open(vURL,"","width=800,height=600,top=100,left=100,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=yes");
    }
    
	
	function addMatch(){
	
		fm.action = "${ctx}/utiIUser/manualMatch.do";
		fm.target="page";
		fm.submit();
	
	}
</script>