<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="com.sinosoft.sysframework.reference.AppConfig"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/undwrt-app.tld" prefix="app"%>
<html>
  <head>
   <app:css/>
    <!--通用函数-->
    <script src="/undwrt/common/js/Common.js"></script>
    <!--通用任务处理函数-->
    <script src="/undwrt/common/js/CommonTaskDeal.js"></script>
	<script src="/undwrt/common/js/WfLogQuery.js"></script>
  </head>
  <body >
  <html:form action="/hebaoTaskDeal.do?actionType=queryContinue">
	<html:hidden property="pageNo"/>
    <html:hidden property="rowsCount"/>
    <html:hidden property="rowsPerPage"/>
	<input type="hidden" name="riskCategory" value='<bean:write name="riskCategory"/>'>
	<input type="hidden" name="nodeStatus" value='<bean:write name="nodeStatus"/>'>
	<input type="hidden" name="HandType" value="11">
	<input type="hidden" name="EditType" value="deal">
	<input type="hidden" name="checkboxSelect" value="0">
	&nbsp;&nbsp;<font color="#D82626"><s:text name="undwrt.HebaoRetractQueryResult.clickContractNoProceedBatchUndwrt"/></font>
  <table class="common" cellpadding="5" cellspacing="1" align="center">
    <tr>
    	<td align="left" colspan="11">
    		<font color="red">
    		<logic:present name="BatchSubmitSuperior"><bean:write name="BatchSubmitSuperior"/></logic:present>
    		</font>
    	</td>
    </tr>
    <tr class="listtitle">
	    <td colspan="11"><b><s:text name="undwrt.HebaoRetractQueryResult.undwrtTaskQueryResult"/></b></td>
	</tr>
	<tr class=listtitle>
		<td>
			<logic:notEqual name="EditType" value="query">
				<input type="checkbox" name="selectButton" value="v" 
					   onpropertychange="boundCheckBox(this, fm.checkboxSelect);">
			</logic:notEqual>
			<logic:equal name="EditType" value="query">
				<input type="checkbox" name="selectButton" value="v" disabled>
			</logic:equal>
		</td>
		<td><s:text name="undwrt.HebaoRetractQueryResult.businessNo"/></td>
		<td><s:text name="undwrt.HebaoRetractQueryResult.contractNo"/></td>
		<td><bean:write name="showColumnName"/></td>
		<td><s:text name="undwrt.HebaoRetractQueryResult.insueredName"/></td>
		<td><s:text name="undwrt.HebaoRetractQueryResult.risk"/></td>
		<td><s:text name="undwrt.HebaoRetractQueryResult.belongOrganization"/></td>
		<td><s:text name="undwrt.HebaoRetractQueryResult.submitTime"/></td>
		<td><s:text name="undwrt.HebaoRetractQueryResult.level"/></td>
		<td><s:text name="undwrt.HebaoRetractQueryResult.status"/></td>
		<td><s:text name="undwrt.HebaoRetractQueryResult.submiter"/></td>
	</tr>
   <logic:present name="UndwrtTaskList">
   <logic:iterate indexId="index" id="WflogList"  name="UndwrtTaskList">
      <tr class=common>
        <td>
			<logic:notEqual name="EditType" value="query">
				<input type="checkbox" name="checkboxSelect" value="<%=index.intValue()%>">
			</logic:notEqual>
			<logic:equal name="EditType" value="query">
				<input type="checkbox" name="checkboxSelect" value="<%=index.intValue()%>" disabled>
			</logic:equal>
		</td>
		<td>
          <a class="check" href="#" onclick="checkTask(<%=index.intValue()%>)">
			<bean:write name="WflogList" property="businessNo"/>
		  </a>
        </td>
		<%
		  //批量核保开关
		  if(AppConfig.get("sysconst.BATCHTASK")!=null && AppConfig.get("sysconst.BATCHTASK").equals("1")){
		%>        
		<td>
			<a class="check" href="#" onclick="checkBatchTask(<%=index.intValue()%>)" ><bean:write name="WflogList" property="contractNo"/></a>
		</td>
		<%}else{%>
		<td><bean:write name="WflogList" property="contractNo"/></td> 
		<%}%>
		<td>
			<logic:equal name="riskCategory" value="D">
				<bean:write name="WflogList" property="licenseNo"/>
			</logic:equal>
			<logic:equal name="riskCategory" value="Y">
				<bean:write name="WflogList" property="relateContractNo"/>
			</logic:equal>
			<logic:equal name="riskCategory" value="E">
				<bean:write name="WflogList" property="identifyNumber"/>
			</logic:equal>
			<logic:equal name="riskCategory" value="Q">&nbsp;</logic:equal>
			<logic:equal name="riskCategory" value="">&nbsp;</logic:equal>
		</td>
        <td><bean:write name="WflogList" property="insuredName"/></td>
		<td><bean:write name="WflogList" property="riskCode"/></td>
        <td><bean:write name="WflogList" property="comName"/></td>
		<td><bean:write name="WflogList" property="flowInTime"/></td>
		<td><bean:write name="WflogList" property="nodeName"/></td>
		<td><bean:write name="WflogList" property="nodeStatusName"/>
			<logic:equal name="WflogList" property="reinsStatus" value="2"><s:text name="undwrt.HebaoRetractQueryResult.submitRePolicy"/></logic:equal>
			<logic:equal name="WflogList" property="reinsStatus" value="3"><s:text name="undwrt.HebaoRetractQueryResult.rePolicyFeedback"/></logic:equal>
			<logic:equal name="WflogList" property="reinsStatus" value="4"><s:text name="undwrt.HebaoRetractQueryResult.rePolicyFeedback1"/></logic:equal>
			<logic:equal name="WflogList" property="reinsStatus" value="5"><s:text name="undwrt.HebaoRetractQueryResult.rePolicyQueryPrice"/></logic:equal>
		</td>
		<td><bean:write name="WflogList" property="operatorName"/></td>
      </tr>
	  <!--隐含域-->
       	<span style="display:none">
			<input name="BusinessNo"   value="<bean:write name="WflogList" property="businessNo"/>">
			<input name="BusinessType" value="<bean:write name="WflogList" property="businessType"/>">
			<input name="ComCode"      value='<bean:write name="WflogList" property="comCode"/>'>
			<input name="ContractNo" value="<bean:write name="WflogList" property="contractNo"/>">
			<input name="FlowID"     value="<bean:write name="WflogList" property="flowID"/>">
			<input name="PackageID"  value="<bean:write name="WflogList" property="packageID"/>">
			<input name="LogNo"      value="<bean:write name="WflogList" property="logNo"/>">
			<input name="ModelNo"    value="<bean:write name="WflogList" property="modelNo"/>">
			<input name="NodeNo"     value="<bean:write name="WflogList" property="nodeNo"/>">
			<input name="FlowStatus" value="<bean:write name="WflogList" property="flowStatus"/>">
			<input name="DeptCode"   value="<bean:write name="WflogList" property="deptCode"/>">
			<input name="FlowInTime" value="<bean:write name="WflogList" property="flowInTime"/>">
			<input name="NodeStatus" value="<bean:write name="WflogList" property="nodeStatus"/>">
			<input name="RiskCode"   value="<bean:write name="WflogList" property="riskCode"/>">
			<input name="ClassCode"  value="<bean:write name="WflogList" property="classCode"/>">
		</span>
   </logic:iterate>
   </logic:present>
		<!--控制数组有效的隐含域-->
		<span style="display:none">
	 		<input name="iBusinessNo">
	 		<input name="iBusinessType">
			<input name="iComCode">
	 		<input name="iContractNo">
			<input name="iFlowID">
			<input name="iPackageID">
			<input name="iModelNo">
			<input name="iNodeNo">
			<input name="iFlowStatus">
			<input name="iDeptCode">
			<input name="iFlowInTime">
			<input name="iNodeStatus">
			<input name="iLogNo">
			<input name="iRiskCode">
			<input name="iClassCode">
		</span>
		<!--隐含域,-->
 		<span style="display:none">
			<input name="BusinessNo">
			<input name="BusinessType">
			<input name="ContractNo">
			<input name="FlowID">
			<input name="PackageID">
			<input name="LogNo">
			<input name="ModelNo">
			<input name="NodeNo">
			<input name="FlowStatus">
			<input name="DeptCode">
			<input name="FlowInTime">
			<input name="NodeStatus">
			<input name="RiskCode">			    	
			<input name="ClassCode">
			<input name="ComCode">
		</span>	 
   </table>
   <table class=menu align="center">
		<tr>
			<td>
				<app:navigate name="fm" objectName="fm"/>
			</td>
		</tr>
   </table>
   &nbsp;
<logic:notEqual name="EditType" value="query">
<%--
   <table class="two">
     <tr>
      <td align="center">
		<input type="button" class="button" name="btn1" value="提交上级" 
			   onclick="prepareBatchSubmit('hebao', 'prepareBatchSubmitSuperior');"
			   <bean:write name="batchSuperiorButton"/>>&nbsp;&nbsp;
		  input type="button" class="button" name="btn2" value="下发修改" 
			   onclick="prepareBatchSubmit('hebao', 'prepareBatchSubmitJunior');"
			   <bean:write name="batchJuniorButton"/>&nbsp;&nbsp;
		<input type="button" class="button" name="btn3" value="撤回"
		       onclick="prepareBatchSubmit('hebao', 'prepareBatchUndo');"
			   <bean:write name="batchUndoButton"/>>
      </td>
    </tr>
   </table>
   --%>
</logic:notEqual>
  </html:form>
  </body>
</html>