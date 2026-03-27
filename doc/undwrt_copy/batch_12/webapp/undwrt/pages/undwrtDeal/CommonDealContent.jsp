<!--***************************************************************************
* Description: 公共处理任务主界面(包括详细信息、提交再保确认、保存、提交等。)
* Author     :
* CreateDate :
* UpdateLog  ：Name       Date            Reason/Contents
****************************************************************************-->

<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/i18njs.jsp"%>

<%@page import="java.util.*"%>
<%@page import="com.sinosoft.platform.bl.facade.BLUtiUwLevelFacade"%>
<%@page import="com.sinosoft.utiall.dbsvr.DBPrpDrisk"%>
<%@page import="com.sinosoft.platform.dto.domain.UtiUwLevelDto"%>
<%@ page import="com.sinosoft.undwrt.undwrtBase.model.WfLog"%>
<!-- 滚动条样式定义 -->
<%@ include file="/common/CommonStyle.html"%>
<%@ page import="com.sinosoft.sysframework.reference.AppConfig"%>
<%@ taglib prefix="rc" uri="http://util.one.sinosoft.com/RCDate"%>

<!-- add by xuhuiling 回調函數導入的 begin 需求150 20160823 -->
<script language="javascript" src="/undwrt/e3/tree/yui/build/yahoo-dom-event/yahoo-dom-event.js"></script>
<script type='text/javascript' src="/undwrt/widgets/yui/connection/connection-min.js"></script>
<script type='text/javascript' src="/undwrt/widgets/yui2/json/json-min.js"></script>
<script type='text/javascript' src="/undwrt/widgets/yui/element/element-beta-min.js"></script>
<!-- add by xuhuiling 回調函數導入的 begin 需求150 20160823 -->

<%
  //禁止缓存，防止用户点击后退
  String handType = (String)session.getAttribute("handType");
  String editType = (String)session.getAttribute("editType");
  String handTitle = (String)session.getAttribute("handTitle");
  String editTitle = (String)session.getAttribute("editTitle");
  String riskCode = (String)session.getAttribute("riskCode");
  //add by xuhuiling 需求150 從session頁面獲取人工開關，拒限保，名單檢測，風險等級，作業狀態 begin
  String valueType = (String)session.getAttribute("valueType"); 
  String refuseLimiteInsurance = (String)session.getAttribute("refuseLimiteInsurance"); 
  String listDetection = (String)session.getAttribute("listDetection"); 
  String riskRating = (String)session.getAttribute("riskRating"); 
  String workStatus = (String)session.getAttribute("workStatus"); 
  //add by xuhuiling 需求150 從session頁面獲取人工開關，拒限保，名單檢測，風險等級，作業狀態 end
 
  String strResultCode= (String)request.getAttribute("ILogReusltCode");
  if(strResultCode==null)//add by 20130325 01险种报js错误，老系统为“”，现在为null
  {
	  strResultCode="";
  }
  String strMainPolicyNo= (String)request.getAttribute("MainPolicyNo");
  if(strMainPolicyNo==null){
     strMainPolicyNo="";
  }
  //add by xuning 添加对通用险种的转换
  DBPrpDrisk dbPrpDrisk = new DBPrpDrisk();
  int intFlag = dbPrpDrisk.getInfo(riskCode);
  String strCommonRisk = "";
  if(intFlag==100||intFlag==0&&dbPrpDrisk.getValidStatus().equals("0"))
  {
%>
<script language=javascript>
  alert("<s:text name='undwrt.CommonDealContent.riskNone'/>");
  return;
</script>
<%
  }
	if(dbPrpDrisk.getFlag().length()>=3)
	{
    	//通用险种（财产）：00Q1
    	if(dbPrpDrisk.getFlag().substring(1,3).equals("Q1")){
    		strCommonRisk = "00Q1";
    	}
    	//通用险种（责任1）：00Z1
    	else if(dbPrpDrisk.getFlag().substring(1,3).equals("Z1")){
    		strCommonRisk = "00Z1";
    	}
    	//通用险种（责任2）：00Z2
    	else if(dbPrpDrisk.getFlag().substring(1,3).equals("Z2")){
    		strCommonRisk = "00Z2";
    	}
    	//通用险种（货运1）：00Y1
	    else if(dbPrpDrisk.getFlag().substring(1,3).equals("Y1")){
	    	strCommonRisk = "00Y1";
	    }
	    //通用险种（货运2）：00Y2
	    else if(dbPrpDrisk.getFlag().substring(1,3).equals("Y2")){
	    	strCommonRisk = "00Y2";
	    }
	    //通用险种（船舶）：00C1
	    else if(dbPrpDrisk.getFlag().substring(1,3).equals("C1")){
	    	strCommonRisk = "00C1";
	    }
	    //通用险种（投资型）：00T1 added by LanNing 20080519
	    else if(dbPrpDrisk.getFlag().substring(1,3).equals("T1")){
	    	strCommonRisk = "00T1";
	    }
	}
        
  	String historyProposal = "";
  	String historyLoss = "";
  	//增加登陆机构传参 myComCode
 	String myComCode = (String)session.getAttribute("myComCode");
  	if(session.getAttribute("historyProposal")!=null)
    	historyProposal = (String)session.getAttribute("historyProposal");
  	if(session.getAttribute("historyLoss")!=null)
    	historyLoss = (String)session.getAttribute("historyLoss");
%>

<html>
	<head>
    	<title><%=editTitle%><%=handTitle%><s:text name="undwrt.EndorseDealContent.task"/></title>
    	<jsp:include page="/common/meta_css.jsp" />
		<jsp:include page="/common/meta_js.jsp" />
	    <!--通用函数-->
	    <script src="/undwrt/common/js/Common.js">--</script>
	    <script src="/undwrt/common/js/Common_undwrt.js"></script>
	    <script src="/undwrt/common/js/My97DatePicker/WdatePicker.js"></script>

	    <!--通用任务处理函数-->
	    <script src="/undwrt/common/js/CommonTaskDeal.js"></script>
	    <script src="/undwrt/common/js/WfLogQuery.js"></script>
	    <script src="/undwrt/pages/undwrtDeal/js/CommonModelZH.js"></script>
    </head>
    
  	<body onload="initDangerUnit();loadForm()">
	<form id="fm" name="fm" method="post">
    	<!--隐含域，数据提交-->
        <input type="hidden" name="DealType">
       	<input type="hidden" name="strMainPolicyNo" value="<%=strMainPolicyNo%>">
        <input type="hidden" name="editType" value="<%=editType%>">
        <input type="hidden" name="handType" value="<%=handType%>">
        <input type="hidden" name="MessageId" value=<%=request.getAttribute("messageId")%>>
        <input type="hidden" name="BusinessNo" value=<%=request.getParameter("iBusinessNo")%>>
        <input type="hidden" name="BusinessType" value=<%=request.getParameter("iBusinessType")%>>
        <input type="hidden" name="FlowId" value=<%=request.getParameter("iFlowID")%>>
        <input type="hidden" name="NodeNo" value=<%=request.getParameter("iNodeNo")%>>
        <input type="hidden" name="LogNo" value=<%=request.getParameter("iLogNo")%>>
        <input type="hidden" name="ModelNo" value=<%=request.getParameter("iModelNo")%>>
        <input type="hidden" name="ContractNo" value=<%=request.getParameter("iContractNo")%>>
        <input type="hidden" name="strRiskCode" value=<%=riskCode%>>
        <input type="hidden" name="CommonRisk" value=<%=strCommonRisk%>>
        <input type="hidden" name="classCode" value=<%=request.getParameter("iClassCode")%>>
        <input type="hidden" name="OperatorCode" value="<s:property value="dealInfo.operatorCode" />">
        <input type="hidden" name="OperatorName" value="<s:property value="dealInfo.operatorName" />">
        <input type="hidden" name="SubmitDirection" value="">
        <input type="hidden" name="selectNodeNo" value="<s:property value='swfPath.swfNodeByfkPathNode2.id.nodeNo' />">
        <input type="hidden" name="selectNodeName" value="<s:property value='swfPath.endNodeName' />">
        <input type="hidden" name="FlowStatus">
        <input type="hidden" name="Flag" value="1">
        <input type="hidden" name="submitTip">
        <!-- 增加登陆机构传参myComCode -->
        <input type="hidden" name="comCode" value="<%=myComCode%>">
        <!-- 增加规则引擎返回的核保级别 -->
        <input type="hidden" name="ResultCode" value="<%=strResultCode%>">
		<input type="hidden" name="iComCode" value="<%=request.getParameter("iComCode")%>">
		<input type="hidden" name="iPackageID" value="<%=request.getParameter("iPackageID")%>">
		<input type="hidden" name="iFlowStatus" value="<%=request.getParameter("iFlowStatus")%>">
		<input type="hidden" name="iDeptCode" value="<%=request.getParameter("iDeptCode")%>">
		<input type="hidden" name="iFlowInTime" value="<%=request.getParameter("iFlowInTime")%>">
		<input type="hidden" name="iNodeStatus" value="<%=request.getParameter("iNodeStatus")%>">
		<input type="hidden" name="iRiskCode" value="<s:property value='iRiskCode'/>">
		<input type="hidden" name="iNodeName" value="<%=request.getParameter("iNodeName")%>">
		<!-- add by xuhuiling 2016年8月21日 begin -->
		<input type="hidden" name="workStatus" value="<%=workStatus%>">
		<input type="hidden" name="valueType" value="<%=valueType%>">
		<!-- 拒限保 -->		
		<input type="hidden" name="refuseLimiteInsurance" value="<%=refuseLimiteInsurance%>">
		<!-- 名單檢測 -->
		<input type="hidden" name="listDetection" value="<%=listDetection%>">
		<!-- 風險評級 -->
		<input type="hidden" name="riskRating" value="<%=riskRating%>">	
		<!-- add by xuhuiling 2016年8月21日 end -->
		
		<input type="hidden" name="DefaultFlag" value="1">
		
		<!-- mantis： CAR0051 ，處理人員： David ，需求單編號： CAR0051   start 原因  業務人員失效減核問題-核保系統檢核 -->
		<input type="hidden" name="validIdentifyNumber" value="<%=request.getAttribute("validIdentifyNumber")%>">
		<input type="hidden" name="validstatusUsercode" value="<%=request.getAttribute("validstatusUsercode")%>">
		<!-- mantis： CAR0051 ，處理人員： David ，需求單編號： CAR0051   end -->
		
		<!-- mantis： FIR0351，處理人員：DP0713，需求單編號：保期起日新增檢核不得早於系統日六個月 STRT-->
		<input type="hidden" name="startDate" value="${PrpTmainDto.startDate}">
		<input type="hidden" name="endorType" value="${endorType}">
		<!-- mantis： FIR0351，處理人員：DP0713，需求單編號：保期起日新增檢核不得早於系統日六個月 END-->

      	<!-- mantis：EGN0104，處理人員：DP0714，調整工程險共保件保期檢核為6個月(含水險及責任險) start -->
      	<input type="hidden" name="coinsFlag" value="${PrpTmainDto.coinsFlag}">
      	<!-- mantis：EGN0104，處理人員：DP0714，調整工程險共保件保期檢核為6個月(含水險及責任險) end -->

      	<!-- mantis：LIA0262，處理人員：DP0713，需求單編號：LIA0262_EL新增檢核工程險保單號碼比對保期c -->
      	<input type="hidden" name="isSameTime" value="${isSameTime}">

		<!-- mantis：EGN0109，處理人員：DP0714，新增檢核保期起日不能超過三個月(含工程險、商火、水險) -->
		<input type="hidden" name="rationCode" value="${rationCode}">

      	<!-- mantis：LIA0348，處理人員：DP0706，需求單編號：LIA0348_稽核議題處理 -->
      	<input type="hidden" name="overThreeMonthMsg" value="${overThreeMonthMsg}">
      	
      	<table class="common" cellpadding="5" cellspacing="1" align="center" border="0">
        	<tr class=listtitle>
        		<%--处理核保任务 --%>
          		<td colspan="4">
          		<%=editTitle%><%=handTitle%><s:text name="undwrt.EndorseDealContent.task"/>
          		</td>
        	</tr>
        	<tr>
          		<td class=title4>
          			<%--处理部门 --%>
          			<s:text name="undwrt.EndorseDealContent.dealDepart"/>：
          		</td>
          		<td class=input4>
          			<input readonly class=readonly  type="text" name="DeptCode" 
          				value="<s:property value="dealInfo.deptName"/>">
          		</td>
          		<td class=title4>
          			<%--提交时间 --%>
          			<s:text name="undwrt.EndorseDealContent.submitTime"/>：
          		</td>
          		<td class=input4>
          			<input readonly class=readonly type="text" name="HandleTime" 
          				value="<rc:rcDate name = "dealInfo.flowInTime" format="yyyy-MM-dd HH:mm:ss"/>">
          		</td>
        	</tr>
        	
     		<!-- 危险单位 -->
        	<%@include file = "CommonDangerUnits.jsp"%>
 		</table>
        	
        	
        	<s:if test="${iClassCode =='C1'}">
        		<s:include value="CommonModelZH.jsp"/>
        	</s:if>
        	
		<table class="common" cellpadding="5" cellspacing="1" align="center" border="0">
        	<%--增加风险评估信息--%>
        	<%if(riskCode.equals("0906") || riskCode.equals("0907") || riskCode.equals("1001") || riskCode.equals("1002")){%>
	        <tr>
	            <td colspan="9" >
	            	<%@include file = "/common/CommonRiskValuat.jsp"%>
	            </td>
	        </tr>
        	<%}%>
        	<s:if test='iNodeStatus != "4" && iNodeStatus != "0"'>
        	<tr>
	            <td class="title4" colspan="4">
	            	<%--历次审核意见 --%>
	                <Input name="butViewTranceInfo" class="longbutton" type="button" 
	                	value="<s:text name='undwrt.CommonDealContent.historyApprovalAdvice'/>" onclick="viewTranceInfo()">
	            </td>
        	</tr>
        	
        	<tr class=listtitle>
            	<td colspan="9" >
            		<%--审批信息 --%>
            		<s:text name="undwrt.PolicyDealContent.approvalInfo"/>
            	</td>
        	</tr>
        	
        	<tr>
        		<td class=title4 >
          			<%--审批片语 --%>
          			<s:text name="undwrt.PolicyDealContent.approvalPhrase"/>：
          		</td>
	          	<td class=input4>
	          		<!-- mantis： CAR0051 ，處理人員： David ，需求單編號： CAR0051   原因  業務人員失效減核問題-核保系統檢核 -->
	          		<input type="hidden" name="notionFlag" value="">
	            	<select class=common name="notion" onchange="changeNotion1(this)"  <%=editType.equals("query")?"disabled":""%> >
	               		<option value="">----- <s:text name="undwrt.PolicyDealContent.pleaseChoose"/> -----</option>
	               		<!-- add by xuhuiling 需求150 當工作狀態是拒保 -->
	             		<%
	             			if(workStatus!=null && workStatus.equals("03")){
	             		%>
	             				<option name="notionOption" value='002'>
								拒絕承保
								</option>
	             				
	             		<%
	             			}
	             			if(workStatus==null||!"03".equals(workStatus)){
	             		%>
	             			<s:iterator value="notionCodeList" status="statu" id="prpDcode">
								<option name="notionOption" value='<s:property value="#prpDcode.id.codeCode"/>'>
									<s:property value="#prpDcode.id.codeCName"/>
								</option>
				 			</s:iterator>
	             		<%		
	             			}
	             		
	             		%>
	            	<!-- add by xuhuiling 需求150 20160820 -->
	            	</select>
	          	</td>
          		<td class=title4>
          			<%--签署审批意见 --%>
          			<s:text name="undwrt.PolicyDealContent.signApprovaleAdvice"/>：
          		</td>
          		<td class=input4>
            		<textarea class=big wrap="soft" name="HandleText" <%=editType.equals("query")?"readonly":""%>><s:iterator value="uwNotionList" status="statu" id="uwNotion"><s:property value='#uwNotion.handleText'/></s:iterator></textarea>
          		</td>
        	</tr>
        	</s:if>
        	
			<!-- 风险资料 -->
			<%--@include file = "/common/CommonRiskInfo.jsp"--%>
		      
			<!-- 单证信息 -->
			<%--@include file = "/common/CommonCertifyInfo.jsp"--%>
      	</table>
      	
   		<s:if test='iNodeStatus != "4" && iNodeStatus != "0"'>	
      	<table class=two>
        	<tr>
		<%
  		if(!editType.equals("query")){
		%>
			  	<td class="button">
			  		<%--暂存 --%>
			      	<Input name="butSaveForm" class="button" type="button" value=" <s:text name='undwrt.CommonDealContent.tempSave'/>" 
			      		onclick="return saveTask();">
			    </td>
			  	<td class="button">
			  		<%--放弃任务 --%>
			      	<Input name="butCancel" class="button" type="button" value="<s:text name='undwrt.CommonDealContent.giveUpTask'/>" 
			      		onclick="return cancelTask();">
			    </td>
			    <td class="button">
			  		<%--改派任务 --%>
			      	<Input name="butCancel" class="button" type="button" value="<s:text name='undwrt.CommonDealContent.sendTask'/>" 
			      		onclick="return sendTaskOne();">
			    </td>
  				<!--非车险权限岗位调整，增加核保初审岗-->
	    	<%
	  		if("<s:text name='undwrt.CommonDealContent.orderApprovalPassNode'/>".equals(request.getParameter("iNodeName"))){
	  		%>
			  	<td class="button">
			  		<%--审核通过 --%>
			    	<Input type="hidden" class="button" name="passBtn" value="<s:text name='undwrt.CommonDealContent.approvalPass'/>" 
			    		onclick="submitPass();" >
			  	</td>  	
  			<%
  			}else{
  			%>
		  		<td class="button">
		  			<%--审核通过 --%>
		    		<Input type="button" class="button" name="passBtn" value="<s:text name='undwrt.CommonDealContent.approvalPass'/>" 
		    			onclick="submitPass();">
		  		</td>
		  	<%
		  	}
		    %>  
			<s:if test="${userNodeNo }>=${nodeNomax } ">
				<td class="button">
			    	<%--提交上级 --%>
			    	<input type="hidden" class="button" name="submitSuperior" value="<s:text name='undwrt.CommonDealContent.submitUpLevel'/>"
			       		onclick="submitTaskBefore('SubmitSuperior');">
				</td>
			</s:if>
			<s:else>
				<td class="button">	
			    	<%--提交上级 --%>
			    	<input type="button" class="button" name="submitSuperior" value="<s:text name='undwrt.CommonDealContent.submitUpLevel'/>"
			       		onclick="submitTaskBefore('SubmitSuperior');">
			    </td>
			</s:else>    
    		
			  	<td class="button">
			  		<%--下发修改 --%>
			      	<input type="button" class="button" name="submitJunior" value="<s:text name='undwrt.CommonDealContent.downSendUpdate'/>"
			       		onclick="submitTaskBefore('SubmitJunior');">
			  	</td>
			<%
    	} else {
		%>
		      	<td  class=button width=20%>
		      		<%--返回 --%>
		       		<Input name="butQuery" class="button" type="button" alt="<s:text name='prompt.back'/>" value="<s:text name='prompt.back'/>" 
		       			onclick="history.back(-1);">
		      	</td>
		<%
		  }
		%>
			</tr>
		</table>
      	</s:if>
	</form>
	</body>
</html>