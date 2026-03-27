<!--***************************************************************************
* Description: 批单处理任务主界面(包括详细信息、提交再保确认、保存、提交等。)
* Author     : LongYin
* CreateDate : 2005-5-23 10:53
* UpdateLog  ：Name       Date            Reason/Contents
*              yanglibo   20080826     2901险种核批时详细信息报404错误。增加通用险种
****************************************************************************-->
<%@ page contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<%@page import="com.sinosoft.utiall.dbsvr.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.platform.bl.facade.BLUtiUwLevelFacade"%>
<%@page import="com.sinosoft.platform.dto.domain.UtiUwLevelDto"%>
<%@ taglib uri="/WEB-INF/undwrt-app.tld" prefix="app"%>
<!-- 滚动条样式定义 -->
<%@ include file="/common/CommonStyle.html"%>
<%@ page import="com.sinosoft.sysframework.reference.AppConfig"%>
<%@ taglib prefix="rc" uri="http://util.one.sinosoft.com/RCDate"%>
<!-- add by xuhuiling 需求150囘調函用到的js 20160823 begin -->
<script language="javascript" src="/undwrt/e3/tree/yui/build/yahoo-dom-event/yahoo-dom-event.js"></script>
<script type='text/javascript' src="/undwrt/widgets/yui/connection/connection-min.js"></script>
<script type='text/javascript' src="/undwrt/widgets/yui2/json/json-min.js"></script>
<script type='text/javascript' src="/undwrt/widgets/yui/element/element-beta-min.js"></script>
<!-- add by xuhuiling 需求150囘調函用到的js 20160823 end -->
<%
	String handType = (String)session.getAttribute("HandType");
	String editType = (String)session.getAttribute("EditType");
	String handTitle = (String)session.getAttribute("HandTitle");
	String editTitle = (String)session.getAttribute("EditTitle");
	String riskCode = (String)session.getAttribute("riskCode");
  	//add by zhulei 20060426 增加登陆机构传参 myComCode
  	String myComCode = (String)session.getAttribute("myComCode");
    String strMainPolicyNo= (String)request.getAttribute("MainPolicyNo");
    //add by xuhuiling 需求150 獲取session作用域的四個值 begin
     String valueType = (String)session.getAttribute("valueType"); 
    String refuseLimiteInsurance = (String)session.getAttribute("refuseLimiteInsurance"); 
    String listDetection = (String)session.getAttribute("listDetection"); 
    String riskRating = (String)session.getAttribute("riskRating"); 
    String workStatus = (String)session.getAttribute("workStatus");
    //add by xuhuiling 需求150 獲取session作用域的四個值 begin
    if(strMainPolicyNo==null){
    	strMainPolicyNo="";
    }
    String strUserCode = (String)session.getAttribute("myUserCode");
	
    //add by xuning 添加对通用险种的转换
	DBPrpDrisk dbPrpDrisk = new DBPrpDrisk();
	int intFlag = dbPrpDrisk.getInfo(riskCode);
	String strCommonRisk = "";
	if(intFlag==100||intFlag==0&&dbPrpDrisk.getValidStatus().equals("0"))
	{
%>
	<script language=javascript>
		alert("险种不存在！");
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
      	//通用险种（投资型）：00T1 added by yanglibo 20080826
   		else if(dbPrpDrisk.getFlag().substring(1,3).equals("T1")){
     		strCommonRisk = "00T1";
     	}
        // add by yanglibo end
	}
    String historyProposal = "";
    String historyLoss = "";
    if(session.getAttribute("historyProposal")!=null)
		historyProposal = (String)session.getAttribute("historyProposal");
   	if(session.getAttribute("historyLoss")!=null)
		historyLoss = (String)session.getAttribute("historyLoss");
%>
<html>
	<head>
		<jsp:include page="/common/meta_css.jsp" />
		<jsp:include page="/common/meta_js.jsp" />
		
    	<title>
	    	<s:property value="editTitle"/>
	    	<s:property value="handTitle"/>
	    	<s:text name="undwrt.EndorseDealContent.task"/>
    	</title>
		
	    <!--通用函数-->
	    <script src="/undwrt/common/js/Common.js"></script>
	    <script src="/undwrt/common/js/Common_undwrt.js"></script>
	    <!--通用任务处理函数-->
	    <script src="/undwrt/common/js/CommonTaskDeal.js"></script>
		<script src="/undwrt/common/js/WfLogQuery.js"></script>
		<script src="/undwrt/pages/undwrtDeal/js/CommonModelZH.js"></script>
		
		<script type="text/javascript">
			function comfirmRefuse()
			{
		  		if(checkTextArea()){
					if(confirm("<s:text name='undwrt.pages.undwrtDeal.reallyDeclinature'/>"))
					{
						submitRefuse();
					}
				}
			}
			
			function checkTextArea()
			{
				if(isEmptyField(fm.HandleText))
				{
					alert("<s:text name='undwrt.pages.undwrtDeal.declinatureApprovalOpinion'/>");
					return false;
				}
				return true;
			}
	  	</script>
	</head>
  	<body onload="initEndorseDangerUnit();loadForm()">
    <form name="fm" method="post">
		<input type="hidden" name="DealType">
	  	<input type="hidden" name="strMainPolicyNo" value="<s:property value="strMainPolicyNo"/>">
	  	<input type="hidden" name="EditType" value="<s:property value="editType"/>">
	  	<input type="hidden" name="MessageId" value="<s:property value="messageId"/>">
	  	<input type="hidden" name="BusinessNo" value="<s:property value="iBusinessNo"/>">
	 	<input type="hidden" name="BusinessType" value="<s:property value="iBusinessType"/>">
	   	<input type="hidden" name="strRiskCode" value="<s:property value="iRiskCode"/>">
	 	<input type="hidden" name="FlowId" value="<s:property value="iFlowID"/>">
	   	<input type="hidden" name="NodeNo" value="<s:property value="iNodeNo"/>">
	  	<input type="hidden" name="LogNo" value="<s:property value="iLogNo"/>">
	  	<input type="hidden" name="ModelNo" value="<s:property value="iModelNo"/>">
	 	<input type="hidden" name="ContractNo" value="<s:property value="iContractNo"/>">
	 	<%--added by LanNing begin 20061107 --%>
	  	<input type="hidden" name="CommonRisk" value=<s:property value="strCommonRisk"/>> 
	  	<%--added by LanNing end 20061107 --%>
	   	<input type="hidden" name="classCode" value=<s:property value="iClassCode"/>>        	
	  	<input type="hidden" name="OperatorCode" value="<s:property value="dealInfo.operatorCode" />">
	  	<input type="hidden" name="OperatorName" value="<s:property value="dealInfo.operatorName" />">
		<input type="hidden" name="SubmitDirection" value="">
		<input type="hidden" name="selectNodeNo" value='<s:property value="swfPath.swfNodeByfkPathNode2.id.nodeNo"/>'>
		<input type="hidden" name="selectNodeName" value='<s:property value='swfPath.endNodeName' />'>
		<input type="hidden" name="FlowStatus">
		<input type="hidden" name="Flag" value="1">
	  	<input type="hidden" name="submitTip">
		<input type="hidden"  name="handType" value="<s:property value="handType"/>">
	    <!-- add by zhulei 20060426 增加登陆机构传参myComCode -->
	    <input type="hidden" name="comCode" value="<%=myComCode%>">
	    <!-- add by zhaoning20090709 增加规则引擎返回的核保级别 -->
	    <input type="hidden" name="ResultCode" value="<s:property value="strResultCode"/>">
      	<input type="hidden" name="iComCode" value="<s:property value="iComCode"/>">
		<input type="hidden" name="iPackageID" value="<s:property value="iPackageID"/>">
		<input type="hidden" name="iFlowStatus" value="<s:property value="iFlowStatus"/>">
		<input type="hidden" name="iDeptCode" value="<s:property value="iDeptCode"/>">
		<input type="hidden" name="iFlowInTime" value="<s:property value="iFlowInTime"/>">
		<input type="hidden" name="iNodeStatus" value="<s:property value="iNodeStatus"/>">
		<input type="hidden" name="iRiskCode" value="<s:property value="iRiskCode"/>">
		<input type="hidden" name="iNodeName" value="<s:property value="iNodeName"/>">
	    <!-- add by xuhuiling 需求150 -->
		<input type="hidden" name="workStatus" value="<%=workStatus%>">
		<input type="hidden" name="valueType" value="<%=valueType%>">
	    <!-- add by xuhuiling 需求150 -->
	    
		<!-- mantis： FIR0351，處理人員：DP0713，需求單編號：保期起日新增檢核不得早於系統日六個月 START -->
		<input type="hidden" name="startDate" value="${PrpCmainDto.startDate}">
		<input type="hidden" name="endorType" value="${endorType}">
		<!-- mantis： FIR0351，處理人員：DP0713，需求單編號：保期起日新增檢核不得早於系統日六個月 END -->

      	<!-- mantis：EGN0104，處理人員：DP0714，調整工程險共保件保期檢核為6個月(含水險及責任險) start -->
      	<input type="hidden" name="coinsFlag" value="${PrpTmainDto.coinsFlag}">
      	<!-- mantis：EGN0104，處理人員：DP0714，調整工程險共保件保期檢核為6個月(含水險及責任險) end -->

		<!-- mantis：LIA0262，處理人員：DP0713，需求單編號：LIA0262_EL新增檢核工程險保單號碼比對保期e -->
		<input type="hidden" name="isSameTime" value="${isSameTime}">
		<!-- mantis：EGN0109，處理人員：DP0714，新增檢核保期起日不能超過三個月(含工程險、商火、水險) -->
		<input type="hidden" name="rationCode" value="${rationCode}">
		
      	<table class="common" cellpadding="5" cellspacing="1" align="center" border="0" width=100%>
        	<tr class=listtitle>
          		<td colspan="4">
          			<%--任务 --%>
          			${editTitle}${handTitle}<s:text name="undwrt.EndorseDealContent.task"/></td>  
        	</tr>
        	
        	<tr>
	  			<td class=title4>
	          		<%--处理部门 --%>
	          		<s:text name="undwrt.EndorseDealContent.dealDepart"/>：</td>
	          	<td class=input4>
		          	<input readonly class=readonly type="text" name="DeptCode" value="<s:property value="dealInfo.deptName"/>" style="width: 185px">
			 	</td>
			  	<td class=title4>
			  		<%--提交时间 --%>
			  		<s:text name="undwrt.EndorseDealContent.submitTime"/>：</td>
			  	<td class=input4>
			  		<input readonly class=readonly type="text" name="HandleTime" 
          				value="<rc:rcDate name = "dealInfo.flowInTime" format="yyyy-MM-dd HH:mm:ss"/>" style="width: 150px"/>
          			<input type="hidden" name="DefaultFlag" value="1"> 
          		</td>
        	</tr>
        	</table>
        	<!---批单危险单位 -->
        	<s:if test='riskCode =="9999" || riskCode=="9997"'>  
          		<%@include file = "/pages/undwrtDeal/EndorseCovernoteDangerUnits.jsp"%>
			</s:if>
       		<s:else>
          		<jsp:include page="/pages/undwrtDeal/EndorseDangerUnits.jsp"></jsp:include>
        	</s:else>
        
   			<s:if test='riskCode =="0906" || riskCode=="0907"|| riskCode=="1001"|| riskCode=="1002"'>
        	<tr>
        		<td colspan="4">
            		<%@include file = "EndorseRiskValuat.jsp"%>
        		</td>
        	</tr>
       		</s:if>
       		
       		<s:if test="${iClassCode =='C1'}">
        		<s:include value="CommonModelZH.jsp"/>
        	</s:if>
        	
        	<table class="common" cellpadding="5" cellspacing="1" align="center" border="0" width=100%>
       		<s:if test='iNodeStatus!="4" && iNodeStatus!="0"'>  
	        	<tr>
		    		<td class=input4 colspan="4">
		           		<%--历次审核意见 --%>
		                <Input name="butViewTranceInfo" class="longbutton" type="button" value="<s:text name='undwrt.CommonDealContent.historyApprovalAdvice'/>" 
		                	onclick="viewTranceInfo()"/>
					</td>
	        	</tr>
	        	
	        	<tr>
	        		<td class=title4 >
	          			<%--审批片语 --%>
	          			<s:text name="undwrt.EndorseDealContent.approvePhrase"/>：</td>
		          	<td class=input4>
		          		<select name="notion" class=common onchange="changeNotion2(this)" >
		             		<option value="">----- <s:text name="undwrt.pages.undwrtDeal.pleaseChoose"/> -----</option>
	              			<s:if test="notionCodeList!= null ">
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
				 			</s:if>
	            		</select>
	          		</td>
	         		<td class=title4>
	         			<%--签署审批意见 --%>
	         			<s:text name="undwrt.EndorseDealContent.signApprovalAdvice"/>：</td>
	         		<td class=input4>
	             		<textarea class=big wrap="soft" name="HandleText"><s:iterator value="uwNotionList" status="statu" id="uwNotion"><s:property value='#uwNotion.handleText'/></s:iterator></textarea>
	             	</td>
	        	</tr>
        	</s:if>
      	</table>
      	
		<s:if test= 'iNodeStatus!="4" && iNodeStatus!="0"'>
		<table class=two>
			<tr>
			<s:if test='editType != "query"'>
				<td class=button>
					<%--暂存 --%>
					<Input name="butSaveForm" class="button" type="button" 
						value=" <s:text name='undwrt.CommonDealContent.tempSave'/> " 
						onclick="return saveEndorseTask()"/></td>
				<td class="button">
					<%--放弃任务 --%>
					<Input name="butCancel" class="button" type="button" 
						value="<s:text name='undwrt.CommonDealContent.giveUpTask'/>" 
						onclick="return cancelTask();"/></td>
				<td class="button">
			  		<%--改派任务 --%>
			      	<Input name="butCancel" class="button" type="button" value="<s:text name='undwrt.CommonDealContent.sendTask'/>" 
			      		onclick="return sendTaskOne();">
			    </td>	
				<%--非车险权限岗位调整，增加核保初审岗--%>
				<s:if test='iNodeName == "預審核通過節點"'>
				<td class="button">
					<%--审核通过 --%>
					<Input type="hidden" class="button" name="passBtn" 
						value="<s:text name='undwrt.CommonDealContent.approvalPass'/>" 
						onclick="submitPass();"/></td>
				</s:if>
				<s:else>
				<td class="button">
					<%--审核通过 --%>
					<Input type="button" class="button" name="passBtn" 
						value="<s:text name='undwrt.CommonDealContent.approvalPass'/>" 
						onclick="submitPass();"/></td>
				</s:else>
				<s:if test="${userNodeNo }>=${nodeNomax } ">
				<td class="button">
					<%--提交上级 --%>
					<input type="hidden" class="button" name="submitSuperior" 
						value="<s:text name='undwrt.CommonDealContent.submitUpLevel'/>"
						onclick="submitEndorseTaskBefore('SubmitSuperior');"/></td>
				</s:if>
				<s:else>
				<td class="button">
					<%--提交上级 --%>
					<input type="button" class="button" name="submitSuperior" value="<s:text name='undwrt.CommonDealContent.submitUpLevel'/>"
						onclick="submitEndorseTaskBefore('SubmitSuperior');"/></td>
				</s:else>
						
				<td class="button">
					<%--下发修改 --%>
					<input type="button" class="button" name="submitJunior" value="<s:text name='undwrt.pages.undwrtDeal.issuedModify'/>"
						onclick="submitEndorseTaskBefore('SubmitJunior');"/></td>
				<s:if test='handType=="11" && 1==0'>
				<td class="button" width=20%>
					<%--拒保 --%>
					<input class=button type="button" style="cursor:hand" value="<s:text name='undwrt.declinature'/>" 
						onclick="comfirmRefuse();"/></td>
				</s:if>
			</s:if>
			<s:else>
				<td  class=button >
					<%--返回 --%>
					<Input name="butQuery" class="button" type="button" alt="<s:text name='prompt.back '/>"  
						value="<s:text name='prompt.back '/>" 
						onclick="history.back(-1);"/></td>
			</s:else>
			</tr>
		</table>
		</s:if>
    </form>
	</body>
</html>