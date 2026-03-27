 <%--
****************************************************************************
* DESC       ：工作流模板路径条件录入/修改页面
* AUTHOR     ：weishixin
* CREATEDATE ：2004-08-22
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app" %>

<html:html locale="true">
<head>
    <!--对title处理-->
<title><s:text name="title.workflow.oaModelWayConditionEditView" />
	<%--工作流模板路径条件编辑界面 --%></title>
  <app:css />
  <%-- 页面样式  --%>
  <link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
  <script src="/claim/workflow/model/js/WorkFlowModelConditionEdit.js"></script>
</head>

<body class="interface" onload="initPage();">
    <form name=fm action="/claim/swfConditionSave.do" method="post">

    <table  border="0" align="center" cellpadding="5" cellspacing="1"  class="common">
      <tr>
				<td class="title" colspan="4"><s:text name="workflow.oaModelWayCondition" />
					<%--工作流模板路径条件 --%></td>
      </tr>
      <tr>

				<td class="title"><s:text name="db.prpGnode.modelNo" />
					<%--模板号 --%>：</td>
				<td class="input"><input type=text name="swfConditionModelNo" class="readonly" readonly="true" value="<bean:write name='swfConditionDto' property='modelNo' />"></td>
				<td class="title" style="valign: bottom"><s:text name="workflow.wayNumber" />
					<%--路径号 --%>：</td>
				<td class="input" style="valign: middle"><input type=text name="swfConditionPathNo" class="readonly" readonly="true" value="<bean:write name='swfConditionDto' property='pathNo'/>"></td>
        </tr>
        <tr>
				<td class="title" style="valign: bottom"><s:text name="workflow.wayName" />
					<%--路径名 --%>：</td>
				<td class="input" style="valign: middle"><input type=text name="swfConditionPathNameNo" class="readonly" readonly="true" value="<bean:write name='swfConditionDto' property='pathName'/>"></td>
        <td class=title  colspan="2"></td>
      </tr>

    </table>



   <table class="common" align="center">
    <!--表示显示多行的-->
    <tr >
				<td class="subformtitle" colspan="4" cellspacing="5" cellpadding="1"><img style="cursor: hand;" src="/claim/images/butCollapseBlue.gif" name="PersonLossImg" onclick="showPage(this,spanWfCondition)">
				<s:text name="workflow.conditionInformation" />
					<%--条件信息 --%>
					<br> <span style="display: none">
          <table class="common" style="display:none" id="WorkFlowCondition_Data" cellspacing="5" cellpadding="1">
            <tbody>
           <tr>
          <td class="input" style="width:4%">
             <div align="center">
               <input class="readonlyNo" readonly name="swfConditionConditionNo" description="序号">
             </div>
         </td>
         <td class="subformtitle" style="width:92%">
         <table  cellpadding="5" cellspacing="1" class="common" style="width:100%">
            <tbody>
                <tr>
                  <input type="hidden" name="swfConditionSerialNo" value="1">
													<td class="title"><s:text name="workflow.configurationType" />
														<%--配置类型 --%>：</td>
													<td class="input"><html:select name="swfConditionDto" property="configType" styleClass="three">
															<html:option value="0">
																<s:text name="workflow.easyDescription" />
																<%--简单描述--%>
															</html:option>
															<html:option value="1">
																<s:text name="workflow.sqlDescription" />
																<%--SQL语句描述--%>
															</html:option>
															<html:option value="2">
																<s:text name="workflow.highestDescription" />
																<%--高级描述--%>
															</html:option>
														</html:select></td>
													<td class="title"><s:text name="workflow.validFlag" />
														<%--有效标志 --%>：</td>
													<td class="input"><html:select name="swfConditionDto" property="validStatus" styleClass="three">
															<html:option value="0">
																<s:text name="common.status.invalid" />
																<%--无效--%>
															</html:option>
															<html:option value="1">
																<s:text name="common.status.effective" />
																<%--有效--%>
															</html:option>
														</html:select></td>
                </tr>
                <tr>
													<td class="title"><s:text name="workflow.businesskeyValues" />
														<%--业务键值 --%>：</td>
													<td class="input"><input class='common' name="swfConditionBusinessKey" maxlength="18" description="业务键值"></td>
													<td class="title"><s:text name="db.prpDdbs.dbsName" />
														<%--数据库名 --%>：</td>
													<td class="input"><input class='common' name="swfConditionDBName" maxlength="20" description="資料庫名"></td>
                </tr>
                <tr>
													<td class="title"><s:text name="db.utiKey.tableName" />
														<%--表名 --%>：</td>
													<td class="input"><input class='common' name="swfConditionTableName" maxlength="20" description="表名"></td>
													<td class="title"><s:text name="workflow.fieldType" />
														<%--資料欄位类型 --%>：</td>
													<td class="input"><input class='common' name="swfConditionDataType" maxlength="255" description="資料欄位类型"></td>
                </tr>
                <tr>
													<td class='title'><s:text name="db.prpXPcol.ColName" />
														<%--資料欄位名 --%>：</td>
													<td class="input"><input class='common' name="swfConditionColumnName" maxlength="20" description="資料欄位名"></td>
													<td class='title'><s:text name="workflow.fieldDescription" />
														<%--資料欄位描述 --%>：</td>
													<td class='input'><input class='common' name="swfConditionColumnDesc" maxlength="60" description="資料欄位描述"></td>
                </tr>
                <tr>
													<td class='title'><s:text name="workflow.operator" />
														<%--运算符 --%>：</td>
													<td class='input'><html:select name="swfConditionDto" property="operator" styleClass="three">
			              <html:option value="=">=</html:option>
					      <html:option value="!=">!=</html:option>
					      <html:option value=">=">&gt;=</html:option>
					      <html:option value="<=">&lt;=</html:option>
					      <html:option value=">">&gt;</html:option>
					      <html:option value="<">&lt;</html:option>
					      <html:option value="in">in</html:option>
					      <html:option value="like">like</html:option>
														</html:select></td>
													<td class="title"><s:text name="workflow.comparingValues" />
														<%-- 比较值--%>：</td>
													<td class="input"><input class='common' name="swfConditionValue" maxlength="60" description="比较值"></td>
                </tr>

            </tbody>
           </table>
           </td>
          <td class="input" style='width:4%'>
          <div align="center">
            <input type=button name="buttonWorkFlowConditionDelete"  onclick="deleteRow(this,'WorkFlowCondition')" value="-" style="cursor: hand">
          </div>
          </td>

       </tr>



            </tbody>
          </table>
				</span> <span id="spanWfCondition" style="display: none"> <%-- 多行输入展现域 --%>
        <table id="WorkFlowCondition" class="common" align="center" cellspacing="1" cellpadding="5">
          <thead>
             <tr>
									<td class="title" style="width: 4%"><s:text name="db.utiPsele.orderNo" />
										<%-- 序号--%></td>
									<td class="title" style="width: 96%" colspan=2><s:text name="db.utiFile.text" />
										<%--内容 --%></td>
             </tr>
          </thead>
          <tfoot>
              <tr>
									<td class="title" colspan=2 style="width: 96%"><s:text name="workflow.query6" />
										<%-- (按"+"号键增加条件信息，按"-"号键删除条件信息)--%></td>
                 <td class="title" align="right" style="width:4%">
                 <div align="center">
                 <input type="button" value="+" onclick="insertRow('WorkFlowCondition')" name="buttonWorkFlowConditionInsert" style="cursor: hand">
                 </div>
                 </td>
              </tr>
           </tfoot>
          <tbody>
								<%
									int indexCondition = 0;
								%>
			<logic:notEmpty  name="swfConditionDto"  property="conditionList">
	        <logic:iterate id="swfCondition" name="swfConditionDto" property="conditionList">
              <tr>
		          <td class="input" style="width:4%">
		             <div align="center">
		               <input class="readonlyNo" readonly name="swfConditionConditionNo" description="序号" value=<bean:write name="swfCondition" property="conditionNo"/>>
		             </div>
		         </td>
		         <td class="subformtitle" style="width:92%">
		         <table  cellpadding="5" cellspacing="1" class="common" >
		            <tbody>
		                <tr>
		                  <input type="hidden" name="swfConditionSerialNo" value=<bean:write name="swfCondition" property="serialNo"/>>
															<td class="title"><s:text name="workflow.configurationType" />
																<%--配置类型 --%>：</td>
															<td class="input"><html:select name="swfCondition" property="configType" styleClass="three">
																	<html:option value="0">
																		<s:text name="workflow.easyDescription" />
																		<%--简单描述--%>
																	</html:option>
																	<html:option value="1">
																		<s:text name="workflow.sqlDescription" />
																		<%--SQL语句描述--%>
																	</html:option>
																	<html:option value="2">
																		<s:text name="workflow.highestDescription" />
																		<%--高级描述--%>
																	</html:option>
																</html:select></td>
															<td class="title"><s:text name="workflow.validFlag" />
																<%--有效标志 --%>：</td>
															<td class="input"><html:select name="swfCondition" property="validStatus" styleClass="three">
																	<html:option value="0">
																		<s:text name="common.status.invalid" />
																		<%--无效--%>
																	</html:option>
																	<html:option value="1">
																		<s:text name="common.status.effective" />
																		<%--有效--%>
																	</html:option>
																</html:select></td>
		                </tr>
		                <tr>
															<td class="title"><s:text name="workflow.businesskeyValues" />
																<%--业务键值 --%>：</td>
															<td class="input"><input class='common' name="swfConditionBusinessKey" maxlength="18" description="业务键值" value=<bean:write name="swfCondition" property="businessKey"/>></td>
															<td class="title"><s:text name="db.prpDdbs.dbsName " />
																<%--数据库名 --%>：</td>
															<td class="input"><input class='common' name="swfConditionDBName" maxlength="20" description="資料庫名" value=<bean:write name="swfCondition" property="DBName"/>></td>
		                </tr>
		                <tr>
															<td class="title"><s:text name="db.utiKey.tableName" />
																<%--表名 --%>：</td>
															<td class="input"><input class='common' name="swfConditionTableName" maxlength="20" description="表名" value=<bean:write name="swfCondition" property="tableName"/>></td>
															<td class="title"><s:text name="workflow.fieldType" />
																<%--資料欄位类型 --%>：</td>
															<td class="input"><input class='common' name="swfConditionDataType" maxlength="255" description="資料欄位类型" value=<bean:write name="swfCondition" property="dataType"/>></td>
		                </tr>
		                <tr>
															<td class='title'><s:text name="db.prpXPcol.ColName" />
																<%--資料欄位名 --%>：</td>
															<td class="input"><input class='common' name="swfConditionColumnName" maxlength="20" description="資料欄位名" value=<bean:write name="swfCondition" property="columnName"/>></td>
															<td class='title'><s:text name="workflow.fieldDescription" />
																<%--資料欄位描述 --%>：</td>
															<td class='input'><input class='common' name="swfConditionColumnDesc" maxlength="60" description="資料欄位描述" value=<bean:write name="swfCondition" property="columnDesc"/>></td>
		                </tr>
		                <tr>
															<td class='title'><s:text name="workflow.operator" />
																<%--运算符 --%>：</td>
															<td class='input'><html:select name="swfCondition" property="operator" styleClass="three">
					              <html:option value="=">=</html:option>
							      <html:option value="!=">!=</html:option>
							      <html:option value=">=">&gt;=</html:option>
							      <html:option value="<=">&lt;=</html:option>
							      <html:option value=">">&gt;</html:option>
							      <html:option value="<">&lt;</html:option>
							      <html:option value="in">in</html:option>
							      <html:option value="like">like</html:option>
																</html:select></td>
															<td class="title"><s:text name="workflow.comparingValues" />
																<%-- 比较值--%>：</td>
															<td class="input"><input class='common' name="swfConditionValue" maxlength="60" description="比较值" value=<bean:write name="swfCondition" property="value"/>></td>
		                </tr>

		            </tbody>
		           </table>
		           </td>
		          <td class="input" style='width:4%'>
		          <div align="center">
		            <input type=button name="buttonWorkFlowConditionDelete"  onclick="deleteRow(this,'WorkFlowCondition')" value="-" style="cursor: hand">
		          </div>
		          </td>

		       </tr>
										<%
											indexCondition++;
										%>
	        </logic:iterate>
	        </logic:notEmpty>

          </tbody>
        </table>
				</span></td>
    </tr>
  </table>

  <input type=hidden name="editType" value="<bean:write name='swfConditionDto' property='editType' filter='true' />">


          <table cellpadding="0" cellspacing="0" width="100%" style="display:">

            <tr>
              <td class=button style="width:50%" align="center">
					<!--保存按钮--> <input type="button" name=buttonSave class='button' value="<s:text name='button.save.value'/>" onclick="return submitForm();">
              </td>

             <td class=button style="width:50%" align="center">
					<!--取消按钮--> <input type=button name=buttonCancel class='button' value="<s:text name='button.cancel.value'/>" onclick="return resetForm();">
             </td>

        </tr>
      </table>
     </form>
</body>

<!--这个函数是调动所能用到的通用js的过程，一般包括最常用的js的函数声明都在meta_js.jsp中-->


</html:html>
