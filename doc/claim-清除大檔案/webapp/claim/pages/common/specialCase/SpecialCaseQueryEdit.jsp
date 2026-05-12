<!--
****************************************************************************
* DESC       ：特殊赔案查询条件输入页面
* AUTHOR     ：理赔组
* CREATEDATE ：2013-02-01
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
							zhangshi		20130201			修改*操作符为=*将like '%%'改为左%右匹配查询
****************************************************************************/
-->

<%@ page contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<html locale="true">
<head>
  <title><s:text name="title.specialCaseBeforeEdit.SpecialClaimsInformation"/></title><%-- 查询特殊赔案信息 --%>
  <!-- 公用函数 -->
  <script src="${ctx}/common/js/Common.js"></script>
  <script language="javascript">
  <!--案件状态标志处理-->
  <!--
    function submitForm(field)
    {
      var ref="";
      for(i=0;i<fm.status.length;i++){
        if(fm.status[i].checked==true){
           ref = ref+fm.status[i].value+",";
        }
      }
      fm.caseFlag.value = ref;
  	  // reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
  	  field.disabled = true;
      fm.submit();//提交
    }
  //-->
  </script>

  <!-- 页面样式  -->
  <link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">

</head>
<body onload="initPage();">
	<form name="fm" action="${ctx}/wfLogQuery.do" method="post" onsubmit="return validateForm(this);">
		<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="specialCase.SpecialClaimsInformation" />
				</td>
			</tr>
			<%-- 查询特殊赔案信息  --%>
			<tr>
				<td class='title'>
					<s:text name="prpLregist.registNo" />
					:
				</td>
				<%-- 报案号 --%>
				<td class='input'>
					<select class=tag name="RegistNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="RegistNo" class="query">
				</td>
				<td class='title'>
					<s:text name="db.prpLregist.policyNo" />
					:
				</td>
				<td class='input'>
					<select class=tag name="PolicyNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="PolicyNo" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLclaimStatus.status" />
					:
				</td>
				<%-- 案件状态 --%>
				<td colspan="3" class='input'>
					<input type="hidden" name="caseFlag" value="">
					<input type="checkbox" name="status" value="1">
					<s:text name="common.status.untreated" />
					<%-- 未处理 --%>
					<input type="checkbox" name="status" value="2">
					<s:text name="common.status.intreating" />
					<%-- 正处理 --%>
					<input type="checkbox" name="status" value="3">
					<s:text name="specialCase.ReturningCompensation" />
					<%-- 核赔退回 --%>
					<input type="checkbox" name="status" value="4">
					<s:text name="common.status.submited" />
					<%-- 已提交 --%>
					<input type="checkbox" name="status" value="5">
					<s:text name="common.status.revoked" />
					<%-- 已撤消 --%>
				</td>
			</tr>
			<tr>
				<td class="title" style="color: red" colspan="4">
					<s:text name="prompt.schedule.query1" />
					<br>
					<%-- "="符号，必须精确查询。 --%>
					<s:text name="prompt.schedule.query2" />
					<%-- "=*"符号，前匹配後模糊的查询。 --%>
				</td>
			</tr>
			<tr>
				<td class='button' colspan="3">
					<input type=button class='button' value="<s:text name='button.query.value' />" onClick="submitForm(this);">
				</td>
			</tr>
		</table>
		<input type="hidden" name="editType" value="specialQuery">
		<input type="hidden" name="nodeType" value="speci">
	</form>
</body>
</html>

