<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>员工岗位配置</title>
<%@include file="/common/meta_css.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
</head>
<body>
<s:form name="fm" action="/saaUserGrade/updateUserGrade.do">
	<table class="fix_table" width="100%">
		<tr>
			<td colspan="4" align="center"><strong>员工岗位</strong></td>
		</tr>
		<tr>
			<td class="bgc_tt short">员工代码</td>
			<td class="long"><s:property value="saaUser.userCode" /><s:hidden
				name="userCode" value="${userCode }" id="userCode"/></td>
			<td class="bgc_tt short">员工姓名</td>
			<td class="long"><s:property value="saaUser.userName" /></td>
		</tr>
		<tr>
			<td colspan="4" align="center">
			<div><strong>岗位列表</strong></div>
			</td>
		</tr>
		<tr>
			<td align="center" class="bgc_tt_center short">岗位代码</td>
			<td align="center" class="bgc_tt_center short">岗位名称</td>
			<td align="center" class="bgc_tt_center short">是否属于此员工</td>
			<td align="left" class="bgc_tt_endcase short">岗位失效日期(留空表示无限期)</td>
		</tr>
		<s:iterator value="userGrades" status="stuts">
			<tr>
				<td align="center"><s:property
					value="%{userGrades[#stuts.index].gradeCode}" /><s:hidden
					name="%{'userGrades['+#stuts.index+'].gradeCode'}" /></td>
				<td align="center"><s:property
					value="%{userGrades[#stuts.index].gradeName}" /><s:hidden
					name="%{'userGrades['+#stuts.index+'].gradeName'}" /></td>
				<td align="center"><s:checkbox name="%{'userGrades['+#stuts.index+'].checked'}" /></td>
				<td align="left">
				  <s:textfield theme="simple" id="${stuts.index}endDateStr" name="%{'userGrades['+#stuts.index+'].endDateStr'}" 
				readonly="true" cssClass="input_w w_20 Wdate" onfocus="WdatePicker();" maxlength="20"/>
				</td>
		</tr>
		</s:iterator>
	</table>
	<table class="fix_table" width="100%">
		<tr class="top">
			<td align="center">
                <input type="button" value="保存" name="submitUserGrade" onclick="submitfm()"	class="button_ty">
                <input type="button" value="关闭" onclick="window.close();" class="button_ty">
            </td>
		</tr>		
	</table>
</s:form>
</body>
</html>
<%@include file="/common/meta_js.jsp"%>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script language="javascript"
	src="${ctx}/dwr/interface/dwrInvokeDataAction.js"></script>
<script language="javascript"
	src="${ctx}/widgets/yui/autocomplete/autocomplete-min.js"></script>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
<script type="text/javascript" src="${ctx}/pages/js/userGrade.js"></script>
<script type="text/javascript">
function submitfm(){
	
	fm.submitUserGrade.disabled=true;
	fm.submit();
}
</script>
