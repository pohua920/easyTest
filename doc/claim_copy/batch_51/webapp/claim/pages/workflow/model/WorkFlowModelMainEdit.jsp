<%--
****************************************************************************
* DESC       ：添加工作流模板信息子块界面页面
* AUTHOR     ： weishixin
* CREATEDATE ： 2004-8-10
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>

    
    <table  border="0" align="center" cellpadding="5" cellspacing="1"  class="common">
 
      <tr>
		<td class="title" colspan="4"><s:text name="workflow.oaModelInformation" />
			<%--工作流模板主信息 --%></td>
      </tr>
      <tr>
         
		<td class="title"><s:text name="workflow.modelNumber" />
			<%--模板编号 --%>：</td>
		<td class="input"><input type=text name="swfModelMainModelNo" class="readonly" readonly="true" value="<bean:write name='swfModelMainDto' property='modelNo' filter='true' />"><font color="#FF0000">*</font></td>
		<td class="title" style="valign: bottom"><s:text name="workflow.modelName" />
			<%--模板名称 --%>：</td>
		<td class="input" style="valign: middle"><input type=text name="swfModelMainModelName" class="common" value="<bean:write name='swfModelMainDto' property='modelName' filter='true' />"><font color="#FF0000">*</font></td>
      </tr>
      <tr>
		<td class="title"><s:text name="workflow.modelType" />
			<%--模板类型 --%>：</td>
		<td class="input"><html:select name="swfModelMainDto" property="modelType" styleClass="three">
				<html:option value="01">
					<s:text name="workflow.payModel" />
					<%--理赔模板 --%>
				</html:option>
			</html:select></td>
		<td class="title"><s:text name="workflow.modelState" />
			<%--模板状态 --%>：</td>
		<td class="input"><html:select name="swfModelMainDto" property="modelStatus" styleClass="three">
				<html:option value="0">
					<s:text name="workflow.notUsed" />
					<%--未使用 --%>
				</html:option>
				<html:option value="1">
					<s:text name="workflow.using" />
					<%--正在使用 --%>
				</html:option>
				<html:option value="2">
					<s:text name="workflow.cannotUsed" />
					<%--已停用 --%>
				</html:option>
				<html:option value="3">
					<s:text name="workflow.alreadyRegister" />
					<%--已作废 --%>
				</html:option>
				<html:option value="4">
					<s:text name="common.status.cancled" />
					<%--已注销 --%>
				</html:option>
			</html:select></td>
      </tr>
      <tr>
		<td class="title"><s:text name="workflow.createDate" />
			<%--创建日期 --%>：</td>
		<td class="input"><input type=text name="swfModelMainCreateDate" title="創建日期" class="readonly" readonly="true" value="<bean:write name='swfModelMainDto' property='createDate' filter='true' />"></td>
		<td class="title"><s:text name="workflow.modifyDate" />
			<%--修改日期 --%>：</td>
		<td class="input"><input type=text name="swfModelMainModifyDate" class="readonly" readonly="true" value="<bean:write name='swfModelMainDto' property='modifyDate' filter='true' />"></td>
      </tr>
       <input type=hidden name="swfModelMainRightID">
       <input type=hidden name="swfModelMainModelAttr">
       <input type=hidden name="swfModelMainCloseService">
       <input type=hidden name="swfModelMainActiveService">
       <input type=hidden name="swfModelMainFlag">
       <input type=hidden name="editType" value="<bean:write name='swfModelMainDto' property='editType' filter='true' />">

       
    </table>