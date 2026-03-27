<!--***************************************************************************
* Description: 授权查询结果页面
* Author     : yishengcheng
* CreateDate : 2011-11-04

****************************************************************************-->
<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="com.sinosoft.sysframework.reference.AppConfig"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<%@ taglib uri="/WEB-INF/undwrt-app.tld" prefix="app"%>
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

  <form name="fm" action="/undwrt/undwrtDeal/hebaoTaskDeal.do?actionType=queryContinue">
	<input type="hidden" name="pageNo"/>
	<input type="hidden" name="rowsCount"/>
	<input type="hidden" name="rowsPerPage"/>
	<input type="hidden" name="riskCategory" value="<s:property value="riskCategory"/>">
	  	<%
  	String[] nodeStatus=(String[])session.getAttribute("nodeStatusList");
  	for(int i=0;i<nodeStatus.length;i++){
  	%>
  	<input type="hidden" name="nodeStatus" value="<%=nodeStatus[i]%>" checked>
  	<%
  }  	
  	%>
	<input type="hidden" name="HandType" value="11">
	<input type="hidden" name="EditType" value="deal">
	<input type="hidden" name="checkboxSelect" value="0">
  <table class="common" cellpadding="5" cellspacing="1" align="center">
    <tr>
    	<td align="left" colspan="11">
    		<font color="red">
    		<s:if test="BatchSubmitSuperior != null">
    		   <s:property value="BatchSubmitSuperior" />
    		</s:if>
  <!--  <logic:present name="BatchSubmitSuperior"><bean:write name="BatchSubmitSuperior"/></logic:present> -->
    		</font>
    	</td>
    </tr>
    <tr class="listtitle">
	    <td colspan="12"><b><s:text name="undwrt.pages.undwrtDeal.AuthorizeControlQueryResult"/></b></td>
	</tr>
	<tr class=listtitle>
		<td><s:text name="undwrt.pages.undwrtDeal.certiNo"/></td>
		<td><s:text name="undwrt.pages.undwrtDeal.contractNo"/></td>
		<td>${showColumnName}</td>
		<td><s:text name="undwrt.pages.undwrtDeal.insuredName"/></td>
		<td><s:text name="riskName"/></td>
		<td><s:text name="comOfRemoteOrg"/></td>
		<td><s:text name="undwrt.pages.undwrtDeal.submitTime"/></td>
		<td><s:text name="undwrt.pages.undwrtDeal.rank"/></td>
		<td><s:text name="undwrt.pages.undwrtDeal.status"/></td>
		<td><s:text name="undwrt.pages.undwrtDeal.submitPerson"/></td>
		<td><s:text name="undwrt.pages.undwrtDeal.issuingStaff"/></td>
	</tr>
	<s:if test="#request.UndwrtTaskList!=null && !#request.UndwrtTaskList.isEmpty()">
	<s:iterator id="WflogList" status="statu" value="#request.UndwrtTaskList">
      <tr class=common>
		<td>
          <a class="check" href="#" onclick="checkAuthorizeTask(<s:property value='#statu.index'/>)">
			<s:property value="#WflogList.businessNo"/>
		  </a>
        </td>
		<%
		  //批量核保开关
		  if(AppConfig.get("sysconst.BATCHTASK")!=null && AppConfig.get("sysconst.BATCHTASK").equals("1")){
		%>        
		<td>
			<a class="check" href="#" onclick="checkBatchTask(<s:property value='#statu.index'/>)" >
			<s:property value="#WflogList.contractNo"/>
			</a>
		</td>
		<%}else{%>
		<td><s:property value="#WflogList.contractNo"/></td> 
		<%}%>
		<td>
		<s:if test='riskCategory == "D"'>
				    <s:property value="#WflogList.licenseNo"/>
				</s:if>
				<s:if test='riskCategory == "Y"'>
				    <s:property value="#WflogList.relateContractNo"/>
				</s:if>
	            <s:if test='riskCategory == "E"'>
					<s:property value="#WflogList.identifyNumber"/>
				</s:if>	
	            <s:if test='riskCategory == "Q"'>
	                &nbsp;
	            </s:if>
	            <s:if test='riskCategory == ""'>
	                &nbsp;
	            </s:if>
		</td>
         <td><s:property value="#WflogList.insuredName"/></td>
	        <td><s:property value="#WflogList.riskCode"/></td>
	        <td><s:property value="#WflogList.comCode"/></td>
	        <td><s:property value="#WflogList.flowInTime"/></td>
	        <td><s:property value="#WflogList.nodeName"/></td>
	        
		 <!--   <td><s:property value="#WflogList.nodestatusname"/>
	            <s:if test="#WflogList.reinsstatus == 2">
	                                           （提交再保）
	            </s:if>
	            <s:if test="#WflogList.reinsStatus == 3">
	                                           （再保反馈）
	            </s:if>
	            <s:if test="#WflogList.reinsStatus == 4">
	                                           （再保反馈）
	            </s:if>
	            <s:if test="#WflogList.reinsStatus == 5">
	                                           （再保询价）
	            </s:if>
	   <!--  </td> --> 
	   <!--  added by wangjun20130116 --> 
	        <td>
	        <s:if test="#WflogList.nodeStatus== 1">
                                           <s:text name="undwrt.pages.undwrtDeal.waitingDispose"/>
            </s:if>
            <s:if test="#WflogList.nodeStatus == 2">
                                           <s:text name="undwrt.pages.undwrtDeal.doingDispose"/>
            </s:if>
            <s:if test="#WflogList.nodeStatus == 3">
                                           <s:text name="undwrt.pages.undwrtDeal.disposeUncommitted"/>
            </s:if>
            <s:if test="#WflogList.nodeStatus == 4">
                                           <s:text name="undwrt.pages.undwrtDeal.submitted"/>
            </s:if>
            <s:if test="#WflogList.nodeStatus == 0">
                                           <s:text name="undwrt.pages.undwrtDeal.closed"/>
            </s:if>
	        </td>
	        <td><s:property value="#WflogList.operatorName"/></td>
			<!-- 增加出单员 -->
			<td><s:property value="#WflogList.singleMember"/></td>
      </tr>
	  <!--隐含域-->
       	<span style="display:none">
				<input name="BusinessNo"   value="<s:property value="#WflogList.businessNo"/>">
				<input name="BusinessType" value="<s:property value="#WflogList.businessType"/>">
				<input name="ComCode"      value="<s:property value="#WflogList.comCode"/>">
				<input name="ContractNo" value="<s:property value="#WflogList.contractNo"/>">
				<input name="FlowID"     value="<s:property value="#WflogList.id.flowId"/>">
				<input name="PackageID"  value="<s:property value="#WflogList.packageId"/>">
				<input name="LogNo"      value="<s:property value="#WflogList.id.logNo"/>">
				<input name="ModelNo"    value="<s:property value="#WflogList.modelNo"/>">
				<input name="NodeNo"     value="<s:property value="#WflogList.nodeNo"/>">
				<input name="FlowStatus" value="<s:property value="#WflogList.flowStatus"/>">
				<input name="DeptCode"   value="<s:property value="#WflogList.deptCode"/>">
				<input name="FlowInTime" value="<s:property value="#WflogList.flowInTime"/>">
				<input name="NodeStatus" value="<s:property value="#WflogList.nodeStatus"/>">	
				<input name="RiskCode"   value="<s:property value="#WflogList.riskCode"/>">
				<input name="ClassCode"  value="<s:property value="#WflogList.classCode"/>">
				<input name="NodeName" value="<s:property value="#WflogList.nodeName"/>">
		</span>
  </s:iterator> 
  </s:if>
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
			<input name="NodeName">
		</span>	 
   </table>
  </form>
  </body>
</html>