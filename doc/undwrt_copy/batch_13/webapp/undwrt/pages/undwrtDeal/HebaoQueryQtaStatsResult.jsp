<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<%@ taglib prefix="rc" uri="http://util.one.sinosoft.com/RCDate"%>
<%--暂时使用原系统分页导航 --%>
<%@ taglib uri="/WEB-INF/undwrt-app.tld" prefix="app"%>
<%@ page import="com.sinosoft.sysframework.reference.AppConfig"%>
<%@page import="java.util.*"%>
<%@page import="java.lang.*"%>

<html>
	<head>
	  	<jsp:include page="/common/meta_css.jsp" />
		<jsp:include page="/common/meta_js.jsp" />
	    <script src="/undwrt/common/js/CommonTaskDeal.js"></script>
		<script src="/undwrt/common/js/WfLogQuery.js"></script>
  	</head>
  	<body >

  	<form id="fm" name="fm" action="${ctx}/undwrtDeal/hebaoTaskDeal.do?actionType=queryContinue">
	    <input type="hidden" name="pageNo" value="<s:property value="pageNo"/>"/>
		<input type="hidden" name="rowsCount" value="<s:property value="rowsCount"/>"/>
		<input type="hidden" name="rowsPerPage" value="<s:property value="rowsPerPage"/>"/>
		<input type="hidden" name="actionType" value="queryContinue"/>
		<input type="hidden" name="riskCategory" value="<s:property value="riskCategory"/>"/>
	<%
  		String[] nodeStatus=(String[])session.getAttribute("nodeStatusList");
  		for(int i=0;i<nodeStatus.length;i++){
  	%>
  		<input type="hidden" name="nodeStatus" value="<%=nodeStatus[i]%>" checked>
  	<%
  		}  	
  	%>
		<input type="hidden" name="handType" value="<s:property value="handType"/>"/>
		<input type="hidden" name="editType" value="<s:property value="editType"/>"/>
		<input type="hidden" name="checkboxSelect" value="0"/>
		&nbsp;&nbsp;
		
  		<table class="common" cellpadding="5" cellspacing="1" align="center">
    		<tr>
		    	<td align="left" colspan="11">
		    		<font color="red">
		    		<s:if test="BatchSubmitSuperior != null">
		    		   <s:property value="BatchSubmitSuperior" />
		    		</s:if>
		    		</font>
		    	</td>
    		</tr>
    		
		    <tr class="listtitle">
			    <td colspan="11"><b><s:text name="undwrt.HebaoQueryQtaStatsResult.priceBillStatusQueryResult"/></b></td>
			</tr>
		</table>
		
		<table style="display:none"  id="displayResultTable" class="common" cellpadding="5" cellspacing="1" align="center">
			<thead>
				<tr class=listtitle>
					<td><s:text name="undwrt.HebaoQueryQtaStatsResult.businessNo"/></td>
					<td style="display:none"><s:text name="undwrt.HebaoQueryQtaStatsResult.contractNo"/></td>
					<td width="100"><s:property value="showColumnName"/>
					<td><s:text name="undwrt.HebaoQueryQtaStatsResult.insueredName"/></td>
					<td style="display:none"><s:text name="undwrt.HebaoQueryQtaStatsResult.risk"/></td>
					<td><s:text name="undwrt.HebaoQueryQtaStatsResult.belongOrganization"/></td>
					<td><s:text name="undwrt.HebaoQueryQtaStatsResult.submitTime"/></td>
					<td style="display:none"><s:text name="undwrt.HebaoQueryQtaStatsResult.level"/></td>
					<td><s:text name="undwrt.HebaoQueryQtaStatsResult.status"/></td>
					<td><s:text name="undwrt.HebaoQueryQtaStatsResult.submiter"/></td>
				</tr>
			</thead>
			<tbody>
   				<s:iterator  value="displayUndwrtTaskList">
      				<tr class=common>
						<td>
							<s:property value="businessNo"/>
				        </td>
				<%
				  //批量核保开关
				  if(AppConfig.get("sysconst.BATCHTASK")!=null && AppConfig.get("sysconst.BATCHTASK").equals("1")){
				%>
						<td style="display:none">
							<a class="check" href="#" onclick="checkBatchTask()" >
								<s:property value="contractNo"/>
							</a>
						</td>
				<%}else{%>
						<td style="display:none">
							<s:property value="contractNo"/>
						</td> 
				<%}%>
						<td>
							<s:if test="${riskCategory=='D' }" >
								<s:property value="licenseNo"/>
							</s:if>
							<s:if test="${riskCategory=='Y'}"  >
								<s:property value="relateContractNo"/>
							</s:if>
							<s:if test="${riskCategory=='E' }" >
								<s:property value="identifyNumber"/>
							</s:if>
							<s:if test="${riskCategory=='Q' }" >&nbsp;</s:if>
							<s:if test="${riskCategory=='' }" >&nbsp;</s:if>
						</td>
        				<td><s:property value="insuredName"/></td>
						<td style="display:none"><s:property value="riskCode"/></td>
				        <td><s:property value="comName"/></td>
						<td><rc:rcDate name="flowInTime" format="yyyy-MM-dd"/></td>
						<td style="display:none"><s:property value="nodeName"/></td>
						<td><s:property value="qtaNodeStatusName"/>
							<s:if test="${reinsStatus=='2' }" ><s:text name="undwrt.HebaoQueryQtaStatsResult.submitRePolicy"/></s:if>
							<s:if test="${reinsStatus=='3' }" ><s:text name="undwrt.HebaoQueryQtaStatsResult.rePolicyFeedback"/></s:if>
							<s:if test="${reinsStatus=='4' }" ><s:text name="undwrt.HebaoQueryQtaStatsResult.rePolicyFeedback1"/></s:if>
							<s:if test="${reinsStatus=='5' }" ><s:text name="undwrt.HebaoQueryQtaStatsResult.rePolicyQueryPrice"/></s:if>
						</td>
						<td><s:property value="operatorName"/></td>
      				</tr>
	  				<!--隐含域-->
			       	<span style="display:none">
						<input name="BusinessNo"   value="<s:property value="businessNo"/>">
						<input name="BusinessType" value="<s:property value="businessType"/>">
						<input name="ComCode"      value='<s:property value="comCode"/>'>
						<input name="ContractNo" value="<s:property value="contractNo"/>">
						<input name="FlowID"     value="<s:property value="flowID"/>">
						<input name="PackageID"  value="<s:property value="packageID"/>">
						<input name="LogNo"      value="<s:property value="logNo"/>">
						<input name="ModelNo"    value="<s:property value="modelNo"/>">
						<input name="NodeNo"     value="<s:property value="nodeNo"/>">
						<input name="FlowStatus" value="<s:property value="flowStatus"/>">
						<input name="DeptCode"   value="<s:property value="deptCode"/>">
						<input name="FlowInTime" value="<s:property value="flowInTime"/>">
						<input name="NodeStatus" value="<s:property value="nodeStatus"/>">				
						<input name="RiskCode"   value="<s:property value="riskCode"/>">
						<input name="ClassCode"  value="<s:property value="classCode"/>">
						<input name="NodeName" value="<s:property value="nodeName"/>">
					</span>
   				</s:iterator>
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
					<input name="iNodeName">
				</span>
				<!--隐含域-->
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
					<input name="NodeName">
				</span>
			</tbody>
   		</table>
	
		<table id="hebaoResultTable" class="common" cellpadding="5" cellspacing="1" align="center">
			<thead>
				<tr class=listtitle>
					<td><s:text name="undwrt.HebaoQueryQtaStatsResult.businessNo1"/></td>
					<td style="display:none"><s:text name="undwrt.HebaoQueryQtaStatsResult.contractNo1"/></td>
					<td width="100"><s:property value="showColumnName"/></td>
					<td><s:text name="undwrt.HebaoQueryQtaStatsResult.insueredName1"/></td>
					<td style="display:none"><s:text name="undwrt.HebaoQueryQtaStatsResult.risk1"/></td>
					<td><s:text name="undwrt.HebaoQueryQtaStatsResult.belongOrganization1"/></td>
					<td><s:text name="undwrt.HebaoQueryQtaStatsResult.submitTime1"/></td>
					<td style="display:none"><s:text name="undwrt.HebaoQueryQtaStatsResult.level1"/></td>
					<td><s:text name="undwrt.HebaoQueryQtaStatsResult.status1"/></td>
					<td><s:text name="undwrt.HebaoQueryQtaStatsResult.submiter1"/></td>
				</tr>
			</thead>
			<tbody>
   				<s:iterator value="UndwrtTaskList">
      				<tr class=common>
						<td>
							<s:property value="businessNo"/>
				        </td>
					<%
						//批量核保开关
					  	if(AppConfig.get("sysconst.BATCHTASK")!=null && AppConfig.get("sysconst.BATCHTASK").equals("1")){
					%>        
						<td style="display:none">
							<a class="check" href="#" onclick="checkBatchTask()" ><s:property value="contractNo"/></a>
						</td>
					<%}else{%>
						<td style="display:none"><s:property value="contractNo"/></td> 
					<%}%>
						<td>
							<s:if test="${riskCategory=='D'}"><s:property value="licenseNo"/></s:if>
							<s:if test="${riskCategory=='Y' }"><s:property value="relateContractNo"/></s:if>
							<s:if test="${riskCategory=='E'}" ><s:property value="identifyNumber"/></s:if>
							<s:if test="${riskCategory=='Q' }" >&nbsp;</s:if>
							<s:if test="${riskCategory=='' }" >&nbsp;</s:if>
						</td>
				        <td><s:property value="insuredName"/></td>
						<td style="display:none"><s:property value="riskCode"/></td>
				        <td><s:property value="comName"/></td>
						<td><rc:rcDate name="flowInTime" format="yyyy-MM-dd"/></td>
						<td style="display:none"><s:property value="nodeName"/></td>
						<td><s:property value="qtaNodeStatusName"/>
							<s:if test="${reinsStatus=='2' }" ><s:text name="undwrt.HebaoQueryQtaStatsResult.submitRePolicy1"/></s:if>
							<s:if test="${reinsStatus=='3' }" ><s:text name="undwrt.HebaoQueryQtaStatsResult.rePolicyFeedback2"/></s:if>
							<s:if test="${reinsStatus=='4' }" ><s:text name="undwrt.HebaoQueryQtaStatsResult.rePolicyFeedback3"/></s:if>
							<s:if test="${reinsStatus=='5' }" ><s:text name="undwrt.HebaoQueryQtaStatsResult.rePolicyQueryPrice1"/></s:if>
						</td>
						<td><s:property value="operatorName"/></td>
      				</tr>
				  	<!--隐含域-->
			       	<span style="display:none">
						<input name="BusinessNo"   value="<s:property value="businessNo"/>">
						<input name="BusinessType" value="<s:property value="businessType"/>">
						<input name="ComCode"      value='<s:property value="comCode"/>'>
						<input name="ContractNo" value="<s:property value="contractNo"/>">
						<input name="FlowID"     value="<s:property value="flowID"/>">
						<input name="PackageID"  value="<s:property value="packageID"/>">
						<input name="LogNo"      value="<s:property value="logNo"/>">
						<input name="ModelNo"    value="<s:property value="modelNo"/>">
						<input name="NodeNo"     value="<s:property value="nodeNo"/>">
						<input name="FlowStatus" value="<s:property value="flowStatus"/>">
						<input name="DeptCode"   value="<s:property value="deptCode"/>">
						<input name="FlowInTime" value="<s:property value="flowInTime"/>">
						<input name="NodeStatus" value="<s:property value="nodeStatus"/>">				
						<input name="RiskCode"   value="<s:property value="riskCode"/>">
						<input name="ClassCode"  value="<s:property value="classCode"/>">
						<input name="NodeName" value="<s:property value="nodeName"/>">
					</span>
   				</s:iterator>
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
					<input name="iNodeName">
				</span>
				<!--隐含域-->
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
					<input name="NodeName">
				</span>
			</tbody> 
   		</table>
   		
	   	<table class=menu align="center">
			<tr>
				<td>
					<app:navigate name="fm" objectName="fm"/>
				</td>
			</tr>
			<tr>
			<td align="center">
				<Input name="butCancelForm" class="button" type="hidden" alt="<s:text name='undwrt.CommonDealContent.downSendUpdate'/>" value="<s:text name='undwrt.CommonDealContent.downSendUpdate'/>" onclick="alert('<s:text name="undwrt.thisFunctionNoneOpenThanks"/>');" >
			</td>
			</tr>
	   	</table>
   
		<s:if test="${EditType!='query'}" >

		<%--
	   	<table class="two">
	     	<tr>
		      	<td align="center">
					<input type="button" class="button" name="btn1" value="提交上级" 
					   	onclick="prepareBatchSubmit('hebao', 'prepareBatchSubmitSuperior');"
					   	<bean:write name="batchSuperiorButton"/>>&nbsp;&nbsp;
				  	<input type="button" class="button" name="btn2" value="下发修改" 
					   	onclick="prepareBatchSubmit('hebao', 'prepareBatchSubmitJunior');"
					   	<bean:write name="batchJuniorButton"/>&nbsp;&nbsp;
					<input type="button" class="button" name="btn3" value="撤回"
				       	onclick="prepareBatchSubmit('hebao', 'prepareBatchUndo');"
					   	<bean:write name="batchUndoButton"/>>
		      	</td>
	    	</tr>
	   	</table>
	   	--%>
		</s:if>
		
		<table width="100%" align="center" cellpadding="5" cellspacing="1">
        	<tr>
        		<td align="center">
            		<input class="button" type="hidden" alt=" <s:text name='undwrt.generateApprovalFile'/> " v<s:text name=''/>alue="<s:text name='undwrt.generateApprovalFile'/>" id="buttonExportDoc" >
          		</td>
	          	<td align="center">
	            	<input class="button" type="button" alt=" <s:text name='undwrt.outBill'/> " value="<s:text name='undwrt.outBill'/>" onclick="exportResultDataToExcel(displayResultTable)">
	          	</td>
          		<td align="center">
            		<input class="button" type="hidden" alt=" <s:text name='prompt.back'/>" value="<s:text name='prompt.back'/>" >
          		</td>
        	</tr>     
      	</table>
  	</form>
  </body>
</html>