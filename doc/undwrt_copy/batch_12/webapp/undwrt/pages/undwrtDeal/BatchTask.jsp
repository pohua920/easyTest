<!--***************************************************************************
* Description: 批量核保页面
* Author     : luyang
* CreateDate : 2005-1-25 18:07
* UpdateLog  ：Name       Date            Reason/Contents
****************************************************************************-->
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/common/i18njs.jsp"%>
<%@ taglib prefix="rc" uri="http://util.one.sinosoft.com/RCDate"%>
<!-- 滚动条样式定义 -->
<%@ include file="CommonStyle.html"%>
<%
	String handTitle = (String)session.getAttribute("handTitle");
	String editTitle = (String)session.getAttribute("editTitle");
%>
<html>
  <head>
    <jsp:include page="/common/meta_css.jsp" />
	<jsp:include page="/common/meta_js.jsp" />
    <title><s:text name="undwrt.pages.undwrtDeal.BatchTask"/></title>
    <!--通用任务处理函数-->
    <script src="/undwrt/common/js/CommonTaskDeal.js"></script>

<script src="/undwrt/pages/undwrtDeal/js/BatchTask.js"></script>

</head>
  <body class=interface>
    <form name="fm" method="post" >
  <table class="common" cellpadding="5" cellspacing="1" align="center">
    <tr>
    	<input type="hidden" name="selectNodeName">
	    <td class=formtitle colspan="9" align="left" ><s:text name="undwrt.pages.undwrtDeal.BatchTask"/>${editTitle}</td>
	  </tr>
    <tr>
	    <td class=formtitle colspan="9" ><font color="#FF0000"><s:text name="undwrt.notice"/>：<s:text name="undwrt.pages.undwrtDeal.BatchTaskNotice"/></font></td>
	  </tr>
    </table>
    <table class="common" cellpadding="5" cellspacing="1" align="center">
        <tr>
          <td class=title><s:text name="undwrt.pages.undwrtDeal.signApprovalOpinion"/>：</td>
          <td class=input>
            <textarea class=big wrap="soft" name="HandleText"><s:if test="notionContent!=null&&!notionContent.isEmpty()"><s:iterator value="notionContent" status="statu" id="uwNotion"><s:property value="#uwNotion.handleText"/></s:iterator></s:if></textarea>
          </td>
          <td class=title ><s:text name="undwrt.pages.undwrtDeal.approvalPhrase"/>：</td>
	          <td class=input>
	          <select name="notion" onchange="changeNotion(this)" >
	             <option value="">----- <s:text name="undwrt.pages.undwrtDeal.pleaseChoose"/> -----111</option>
	           <s:if test="notionCodeList!=null">
	           <s:iterator value="notionCodeList" status="statu" id="prpDcode">
	           <option name="notionOption" value='<s:property value="#prpDcode.id.codeCName"/>'><s:property value="#prpDcode.id.codeCName"/></option>
	           </s:iterator>
	           </s:if>  
            </select>
          </td>
        </tr>
        <tr>
          <td class=title><s:text name="undwrt.pages.undwrtDeal.chooseIssuedSuperior"/>：</td>
          <td class=input>
            <select name="selectNodeNo">
			<s:if test="colBackList!=null">
             <s:iterator value="colBackList" status="statu" id="colBackList">
             <option value='<s:property value="#colBackList.nodeNo"/>' >
             <s:property value="#colBackList.nodeNo"/>--<s:property value="#colBackList.nodeName"/>
               </option>
              </s:iterator>
               </s:if>
       
 
            </select>
          </td>
          <td class=title ></td>
	          <td class=input>
	          
          </td>
        </tr>
    </table>    
	  <br>
  <table class="common" cellpadding="5" cellspacing="1" align="center">
    <tr class=listtitle>
     
      <td><s:text name="undwrt.pages.undwrtDeal.contractNo"/></td>
      <td>
      	<s:if test='operateType=="proposal"'>
     	 <s:text name="undwrt.HebaoTaskDealQuery.businessNo"/>
     	 </s:if>
      	<s:else>
      		<s:text name="undwrt.CommonDealContentQta.quoteNo"/>
      	</s:else>
      </td>
      <td><s:text name="undwrt.pages.undwrtDeal.insuredName"/></td>
      <td><s:text name="undwrt.pages.undwrtDeal.submitTime"/></td>
      <td><s:text name="undwrt.pages.undwrtDeal.hierarchy"/></td>
      <td><s:text name="undwrt.pages.undwrtDeal.anyStatus"/></td>
      <td><s:text name="undwrt.pages.undwrtDeal.flowDirection"/></td>
       <td></td>
    </tr>
   <s:if test="batchTaskList!=null">
   <s:iterator value="batchTaskList" id="batchTaskList"  status="statu">
      <tr class=listeven>
        
        <td> <s:property value="#batchTaskList.contractNo"/></td>
        <td> <s:property value="#batchTaskList.businessNo"/></td>
        <td> <s:property value="#batchTaskList.insuredName"/></td>
        <td> <rc:rcDate name = "#batchTaskList.flowInTime" format="yyyy-MM-dd HH:mm:ss"/></td>
        <td> <s:property value="#batchTaskList.nodeName"/></td>
        	        <td>
	        <s:if test="#batchTaskList.nodeStatus== 1">
                                           <s:text name="undwrt.pages.undwrtDeal.waitingDispose"/>
            </s:if>
            <s:if test="#batchTaskList.nodeStatus == 2">
                                           <s:text name="undwrt.pages.undwrtDeal.doingDispose"/>
            </s:if>
            <s:if test="#batchTaskList.nodeStatus == 3">
                                           <s:text name="undwrt.pages.undwrtDeal.disposeUncommitted"/>
            </s:if>
            <s:if test="#batchTaskList.nodeStatus == 4">
                                           <s:text name="undwrt.pages.undwrtDeal.submitted"/>
            </s:if>
            <s:if test="#batchTaskList.nodeStatus == 0">
                                           <s:text name="undwrt.pages.undwrtDeal.closed"/>
            </s:if>
	        </td>
        <td> 
           <s:if test="#batchTaskList.flowStatus==0">
                                              <s:text name="undwrt.pages.undwrtDeal.normalFlow"/>
           </s:if>
           <s:if test="#batchTaskList.flowStatus==1">
                                                <s:text name="undwrt.pages.undwrtDeal.rollback"/>
           </s:if>  
        </td>
        <td><input type="checkbox" name="Delete" "
				disabled" checked style="display:none"></td>
        <!--隐含域-->
       	<span style="display:none" >
          <input name="operateFlag" size="6" value="Y" readonly>
			    <input name="businessNo" value="<s:property value="#batchTaskList.businessNo"/>">
			    <input name="businessType" value="<s:property value="#batchTaskList.businessType"/>">
                <input name="contractNo" value="<s:property value="#batchTaskList.contractNo"/>">
			    <input name="flowID"     value="<s:property value="#batchTaskList.id.flowId"/>">
			    <input name="packageID"  value="<s:property value="#batchTaskList.packageID"/>">
			    <input name="logNo"      value="<s:property value="#batchTaskList.id.logNo"/>">
			    <input name="modelNo"    value="<s:property value="#batchTaskList.modelNo"/>">
			    <input name="nodeNo"     value="<s:property value="#batchTaskList.nodeNo"/>">
			    <input name="insuredName"  value="<s:property value="#batchTaskList.insuredName"/>">
			    <input name="flowInTime" value="<s:property value="#batchTaskList.flowInTime"/>">
                <input name="nodeName"     value="<s:property value="#batchTaskList.nodeName"/>">			    
                <input name="timeLimit"     value="<s:property value="#batchTaskList.timeLimit"/>">			    
			    <input name="nodeStatusName" value="<s:property value="#batchTaskList.nodeStatusName"/>">
			    <input name="flowStatusName" value="<s:property value="#batchTaskList.flowStatusName"/>">
			    <input name="deptCode"   value="<s:property value="#batchTaskList.deptCode"/>">
			    <input name="nodeStatus" value="<s:property value="#batchTaskList.nodeStatus"/>">
			    <input name="operatorCode" value="<s:property value="#batchTaskList.operatorCode"/>">
			    <input name="flowStatus" value="<s:property value="#batchTaskList.flowStatus"/>">
			    <input name="comCode" value="<s:property value="#batchTaskList.comCode"/>">
			    <input name="riskCode" value="<s:property value="#batchTaskList.riskCode"/>">
			    <input name="resultCode" value="<s:property value="#batchTaskList.resultCode"/>">
			    <input name="handType" value="<s:property value="handType"/>">
			    <input name="editType" value="<s:property value="editType"/>">
			 	</span>
      </tr>
   </s:iterator>
   </s:if>  
   <!--控制数组有效的隐含域-->
   <span style="display:none">
      <input type="icheckbox" name="Delete" >
      <input name="ioperateFlag" value="N">
      <input name="icontractNo" >
      <input name="iflowID">
      <input name="ilogNo">
	 	  <input name="iBusinessNo" >
	 	  <input name="iBusinessType" >
	 	  <input name="iContractNo" >
			<input name="iFlowID" >
			<input name="iPackageID"  >
			<input name="iModelNo"    >
			<input name="iNodeNo"     >
			<input name="iFlowStatus" >
			<input name="iDeptCode" >
			<input name="iFlowInTime" >
			<input name="iNodeStatus" >
			<input name="iLogNo">
			<input name="iComCode" >
			<input name="iRiskCode" >
			<input name="iResultCode" >
	 </span>
  </table>
  &nbsp;
  <table class=sub>
    <tr>
    
    <s:if test='editType != "query"'>   
      <td class=button width=20%>
        <Input name="butCancelForm" class="button" type="button" alt="<s:text name='undwrt.abortMission'/>" value="<s:text name='undwrt.abortMission'/>" onclick="return cancelBatchTask();"></td>
      <td class=button width=20%>
      	<Input name="butSubmitForm" class="button" type="button" alt="<s:text name='undwrt.submitMission'/>" value="<s:text name='undwrt.submitMission'/>"  onclick="return submitCommonBatchTask();"></td>
      <td class=button width=20%>
        <Input name="buttonReset" class="button" type="button" alt="<s:text name='prompt.reset'/>"  value="<s:text name='prompt.reset'/>" onclick="reset()" ></td>
        <td  class=button width=20%>
        <Input name="butQuery" class="button" type="button" alt="<s:text name='prompt.back'/>"  value="<s:text name='prompt.back'/>" onclick="history.back(-1);"></td>
</s:if>         
    </tr>
  </table>
    </form>
  </body>
</html>