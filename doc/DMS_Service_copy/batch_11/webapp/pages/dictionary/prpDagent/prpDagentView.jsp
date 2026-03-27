<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<html>
<head>
<title>渠道代码</title>
</head>
<body id="all_title">
<div id="crash_menu">
<h2 align="center">渠道代码</h2>
</div>
<s:form action="/dictionary/updateprpDagent.do" name="frm">
	<s:hidden name="prpDagent.validStatus" id="prpDagentValidStatus" value='0' />
	<s:hidden name="prpDagent.agentCode"/>
	<table class="fix_table">
		<tr>
			<td class="bgc_tt short">渠道代码</td>
			<td class="long"><s:textfield name="prpDagent.agentCode"
				 cssClass='input_w w_30' readonly="true" /></td>
			<td class="bgc_tt short">渠道名称</td>
			<td class="long"><s:textfield name="prpDagent.agentName"
				cssClass='input_w w_30' readonly="true" /></td>
			<td class="bgc_tt short">渠道地址</td>
			<td class="long"><s:textfield name="prpDagent.addressName"
				cssClass='input_w w_30' readonly="true" /></td>
		</tr>
		<tr>
			<td class="bgc_tt short">邮政编码</td>
			<td class="long"><s:textfield name="prpDagent.postCode"
				 cssClass='input_w w_30' readonly="true" /></td>
			<td class="bgc_tt short">渠道类型</td>
			<td class="long"><s:textfield name="prpDagent.agentType"
				cssClass='input_w w_30' readonly="true" /></td>
			<td class="bgc_tt short">许可证号</td>
			<td class="long"><s:textfield name="prpDagent.permitNo"
				cssClass='input_w w_30' readonly="true" /></td>
		</tr>
		<tr>
			<td class="bgc_tt short">联系人</td>
			<td class="long"><s:textfield name="prpDagent.linkerName"
				 cssClass='input_w w_30' readonly="true" /></td>
			<td class="bgc_tt short">电话</td>
			<td class="long"><s:textfield name="prpDagent.phoneNumber"
				cssClass='input_w w_30' readonly="true" /></td>
			<td class="bgc_tt short">传真</td>
			<td class="long"><s:textfield name="prpDagent.faxNumber"
				cssClass='input_w w_30' readonly="true" /></td>
		</tr>
		<tr>
			<td class="bgc_tt short">归属机构代码</td>
			<td class="long"><s:textfield name="prpDagent.comCode"
				 cssClass='input_w w_30' readonly="true" /></td>
			<td class="bgc_tt short">上级代理人代码</td>
			<td class="long"><s:textfield name="prpDagent.upperAgentCode"
				cssClass='input_w w_30' readonly="true" /></td>
			<td class="bgc_tt short">新的代理人代码</td>
			<td class="long"><s:textfield name="prpDagent.newAgentCode"
				cssClass='input_w w_30' readonly="true" /></td>
		</tr>
		<tr>
			<td class="bgc_tt short">合同期</td>
			<td class="long"><s:textfield name="prpDagent.bargainDate"
				 cssClass='input_w w_30' readonly="true" /></td>
			<td class="bgc_tt short">专项代码</td>
			<td class="long"><s:textfield name="prpDagent.articleCode"
				cssClass='input_w w_30' readonly="true" /></td>
			<td class="bgc_tt short">标志字段</td>
			<td class="long"><s:textfield name="prpDagent.flag"
				cssClass='input_w w_30' readonly="true" /></td>
		</tr>
		<tr>
			<td class="bgc_tt short">有效状态</td>
			<td class="long"><s:textfield name="prpDagent.validStatus"
				 cssClass='input_w w_30' readonly="true" /></td>
			<td class="bgc_tt short">允许下级机构使用</td>
			<td class="long"><s:textfield name="prpDagent.agentNature"
				cssClass='input_w w_30' readonly="true" /></td>
		</tr>
		<tr>
			<td class="bgc_tt short" colspan="2" align="center"><input type="button" class="button_ty" value="收回" onclick="javascript:frm_Submit()"></td>					
			<td class="bgc_tt short" colspan="2" align="center"><input type="button" class="button_ty" value="确定"	onclick="javascript:window.close()"></td>
		</tr>		
</table>
</s:form>
</body>
</html>
<script language="javascript">
function frm_Submit(){
	if(confirm("确实要收回该权限")){
	frm.submit();
	}else{
	}
}
</script>
<%@ include file="/common/meta_js.jsp"%>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script language="javascript"
	src="${ctx}/widgets/yui/autocomplete/autocomplete-min.js"></script>

<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>



