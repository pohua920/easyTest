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
	    <!--通用函数-->
	    <script src="/undwrt/common/js/Common.js"></script>
	    <!--通用任务处理函数-->
	    <script src="/undwrt/common/js/CommonTaskDeal.js"></script>
		<script src="/undwrt/common/js/WfLogQuery.js"></script>
  	</head>
  	<body >
    	<form id="fm" name="fm" action="${ctx}/undwrtDeal/hebaoTaskDeal.do?actionType=queryContinue">
	    	<input type="hidden" name="pageNo" value="<s:property value="pageNo"/>"/>
	    	<input type="hidden" name="rowsCount" value="<s:property value="rowsCount"/>"/>
	    	<input type="hidden" name="rowsPerPage" value="<s:property value="rowsPerPage"/>"/>
	    	<input type="hidden" name="actionType" value="queryContinue"/>
	    	<input type="hidden" name="riskCategory" value=<s:property value="riskCategory"/>/>
    
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
			&nbsp;&nbsp;<font color="#D82626"><s:text name='undwrt.HebaoTaskDealQueryResult.hitContractNoBatchDownSendAlterDeal'/></font>
			
  			<table class="common" cellpadding="5" cellspacing="1" align="center">
			    <tr>
			    	<td align="left" colspan="11">
			    		<font color="red">
				    		<s:if test="%{BatchSubmitSuperior!=null}" >
				    			<s:property value="BatchSubmitSuperior"/>
				    		</s:if>
			    		</font>
			    	</td>
			    </tr>
			    
			    <tr class="listtitle">
				    <td colspan="11">
				    	<b><s:text name='undwrt.approvalTaskQueryResult'/></b>
				    </td>
				</tr>
				
				<tr class=listtitle>
					<td>
						<s:if test="editType!='query'">
							<input type="checkbox" name="selectButton" value="v" 
								   onclick="boundCheckBox(this, fm.checkboxSelect);">
						</s:if>
						<s:if test="editType=='query'">
							<input type="checkbox" name="selectButton" value="v" disabled>
						</s:if>
					</td>
					<td><s:text name='undwrt.pages.undwrtDeal.certiNo'/></td>
					<td style="display:none""><s:text name='undwrt.pages.undwrtDeal.contractNo'/></td>
					<td><s:text name='undwrt.pages.undwrtDeal.licenseNo'/></td>
					<td><s:text name='undwrt.pages.undwrtDeal.insuredName'/></td>
					<td style="display:none""><s:text name='riskName'/></td>
					<td><s:text name='comOfRemoteOrg'/></td>
					<td><s:text name='undwrt.pages.undwrtDeal.submitTime'/></td>
					<td style="display:none""><s:text name='undwrt.pages.undwrtDeal.rank'/></td>
					<td><s:text name='undwrt.pages.undwrtDeal.status'/></td>
					<td><s:text name='undwrt.pages.undwrtDeal.submitPerson'/></td>
					<td><s:text name='undwrt.firstLevel'/></td>
				</tr>
				
				<s:iterator value="undwrtTaskList" status="statu">
      				<tr class=common>
        				<td>
			        		<s:if test='EditType!="query"'>
								<input type="checkbox" name="checkboxSelect" value="<s:property value="#statu.index"/>" 
								onclick="checkRelBusiness(<s:property value="#statu.index"/>);">
							</s:if>
							<s:if test='EditType=="query"'>
								<input type="checkbox" name="checkboxSelect" value="<s:property value="#statu.index"/>" disabled>
							</s:if>
						</td>
						<td>
				          	<a class="check" href="#" onclick="checkTask(<s:property value="#statu.index"/>)">
								<s:property value="businessNo"/>
						  	</a>
				        </td>
					<%
						//批量核保开关
					  	if(AppConfig.get("sysconst.BATCHTASK")!=null && AppConfig.get("sysconst.BATCHTASK").equals("1")){
					%>
						<td style="display:none"">
							<a class="check" href="#" onclick="checkBatchTask(<s:property value="#statu.index"/>)">
								<s:property value="contractNo"/>
							</a>
						</td>
					<%}else{%>
						<td style="display:none""><s:property value="contractNo"/></td> 
					<%}%>
						<td>
							<s:if test='${riskCategory == "D"}'>
				    			<s:property value="licenseNo"/>
							</s:if>
							<s:if test='${riskCategory == "Y"}'>
							    <s:property value="relateContractNo"/>
							</s:if>
				            <s:if test='${riskCategory == "E"}'>
								<s:property value="identifyNumber"/>
							</s:if>
				            <s:if test='${riskCategory == "Q"}'>
				                &nbsp;
				            </s:if>
				            <s:if test='${riskCategory == ""}'>
				                &nbsp;
				            </s:if>
						</td>
           				<td><s:property value="insuredName"/></td>
				        <td style="display:none"><s:property value="riskCode"/></td>
				        <td><s:property value="comCode"/></td>
				        <td><rc:rcDate name="flowInTime" format="yyyy-MM-dd"/></td>
				        <td style="display:none"><s:property value="nodeName"/></td>
		 				<td><s:property value="qtaNodeStatusName"/>
				            <s:if test="reinsstatus == 2">
				                                           <s:text name='undwrt.HebaoQueryQtaStatsResult.submitRePolicy'/>
				            </s:if>
				            <s:if test="reinsStatus == 3">
				                                           <s:text name='undwrt.HebaoQueryQtaStatsResult.rePolicyFeedback'/>
				            </s:if>
				            <s:if test="reinsStatus == 4">
				                                           <s:text name='undwrt.HebaoQueryQtaStatsResult.rePolicyFeedback1'/>
				            </s:if>
				            <s:if test="reinsStatus == 5">
				                                         <s:text name='undwrt.HebaoQueryQtaStatsResult.rePolicyQueryPrice'/>
				            </s:if>
	   					</td>
	        			<td><s:property value="operatorName"/></td>
						<td><s:property value="priorType"/></td>
	      			</tr>
	  				<!--隐含域-->
			       	<span style="display:none">
						<input name="BusinessNo"   value="<s:property value="businessNo"/>">
						<input name="BusinessType" value="<s:property value="businessType"/>">
						<input name="ComCode"      value="<s:property value="comCode"/>">
						<input name="ContractNo" value="<s:property value="contractNo"/>">
						<input name="FlowID"     value="<s:property value="id.flowId"/>">
						<input name="PackageID"  value="<s:property value="packageId"/>">
						<input name="LogNo"      value="<s:property value="id.logNo"/>">
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
   			</table>
		   	<table class=menu align="center">
				<tr>
					<td>
						<app:navigate name="fm" objectName="fm"/>
					</td>
				</tr>
				<tr>
					<td align="center">
						<Input name="butCancelForm" class="button" type="button" alt="<s:text name='undwrt.CommonDealContent.downSendUpdate'/>" value="<s:text name='undwrt.CommonDealContent.downSendUpdate'/>" 
							onclick='alert("<s:text name="undwrt.thisFunctionNoneOpenThanks"/>");' >
					</td>
				</tr>
		   	</table>
   			&nbsp;
  		</form>
  	</body>
</html>