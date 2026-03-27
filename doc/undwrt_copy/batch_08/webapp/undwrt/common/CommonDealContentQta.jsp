<%@ page contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/i18njs.jsp"%>

<%@page import="java.util.*"%>
<%@page import="com.sinosoft.utiall.dbsvr.DBPrpDrisk"%>
<%@page import="com.sinosoft.platform.dto.domain.PrpDuserDto"%>
<%@page import="com.sinosoft.prpall.blsvr.tb.BLPrpTmainSub"%>
<%@page import="com.sinosoft.utility.database.DbPool"%>
<%@page import="com.sinosoft.utility.SysConfig"%>
<%@page import="com.sinosoft.prpall.blsvr.tb.BLPrpTmain"%>

<!-- 滚动条样式定义 -->
<%@ include file="/common/CommonStyle.html"%>
<%@ page import="com.sinosoft.sysframework.reference.AppConfig"%>
<%@page import="com.sinosoft.prpall.dto.domain.*"%>
<%@page import="com.sinosoft.utility.string.*"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="rc" uri="http://util.one.sinosoft.com/RCDate"%>

<%
  String handType = (String)session.getAttribute("handType");
  String editType = (String)session.getAttribute("editType");
  String handTitle = (String)session.getAttribute("handTitle");
  String editTitle = (String)session.getAttribute("editTitle");
  String riskCode = (String)session.getAttribute("riskCode");
  //增加登陆机构传参 myComCode
  String myComCode = (String)session.getAttribute("myComCode");
  
  //add by xuhuiling 需求150 從session頁面獲取人工開關，拒限保，名單檢測，風險等級，作業狀態 begin
  String valueType = (String)session.getAttribute("valueType"); 
  String refuseLimiteInsurance = (String)session.getAttribute("refuseLimiteInsurance"); 
  String listDetection = (String)session.getAttribute("listDetection"); 
  String riskRating = (String)session.getAttribute("riskRating"); 
  String workStatus = (String)session.getAttribute("workStatus"); 
  //add by xuhuiling 需求150 從session頁面獲取人工開關，拒限保，名單檢測，風險等級，作業狀態 end
%>
<html>
  	<head>
    	<title>${editTitle}${handTitle}<s:text name="undwrt.CommonDealContentQta.task"/></title>
    	<jsp:include page="/common/meta_css.jsp" />
		<jsp:include page="/common/meta_js.jsp" />
    	<!--通用函数-->
    	<script src="/undwrt/common/js/Common.js"></script>
    	<!-- 需求150 回調函數導入   -->
		<script language="javascript" src="/undwrt/e3/tree/yui/build/yahoo-dom-event/yahoo-dom-event.js"></script>
		<script type='text/javascript' src="/undwrt/widgets/yui/connection/connection-min.js"></script>
		<script type='text/javascript' src="/undwrt/widgets/yui2/json/json-min.js"></script>
		<script type='text/javascript' src="/undwrt/widgets/yui/element/element-beta-min.js"></script>
		
    	<!--通用任务处理函数-->
    	<script src="/undwrt/common/js/CommonTaskDeal.js"></script>
	</head>
  	<body onload="">

    <form name="fm" method="post">
    	<!--隐含域，数据提交-->
        <input type="hidden" name="DealType">
        <input type="hidden" name="EditType" value="${editType}">
        <input type="hidden" name="HandType" value="${handType}">
        <input type="hidden" name="BusinessNo" value=<s:property value="iBusinessNo"/>>
        <input type="hidden" name="BusinessType" value=<s:property value="iBusinessType"/>>
        <input type="hidden" name="ContractNo" value=<s:property value="iContractNo"/>>
        <input type="hidden" name="strRiskCode" value="${riskCode}">
        <input type="hidden" name="ClassCode" value=<s:property value="iClassCode"/>>
        <input type="hidden" name="Flag" value="1">
        <%-- 增加登陆机构传参myComCode --%>
        <input type="hidden" name="comCode" value="${myComCode}">
        <%-- 增加规则引擎返回的核保级别 --%>
		<input type="hidden" name="iComCode" value="<s:property value="iComCode"/>">
		<input type="hidden" name="iRiskCode" value="<s:property value="iRiskCode"/>">
		<input type=hidden name="FlowId" value=<s:property value="iFlowID"/>>
		<input type="hidden" name="ModelNo" value=<s:property value="iModelNo"/>>
		<input type="hidden" name="NodeNo" value=<s:property value="iNodeNo"/>>
		<input type="hidden" name="LogNo" value=<s:property value="iLogNo"/>>
		<input type="hidden" name="selectNodeNo" value="<s:property value='swfPath.swfNodeByfkPathNode2.id.nodeNo' />">
		<input type="hidden" name="selectNodeName" value="<s:property value='swfPath.endNodeName' />">
		<input type="hidden" name="FlowStatus" value="<%=request.getParameter("iFlowStatus")%>">
		<input type="hidden" name="RiskCode" value="<s:property value="iRiskCode"/>">
		<input type="hidden" name="SubmitDirection" value="">
		<input type="hidden" name="DefaultFlag" value="1">
		<input type="hidden" name="workStatus" value="<%=workStatus%>">
		<input type="hidden" name="valueType" value="<%=valueType%>">
		
		<!-- mantis： CAR0051 ，處理人員： David ，需求單編號： CAR0051   start 原因  業務人員失效減核問題-核保系統檢核 -->
		<input type="hidden" name="validIdentifyNumber" value="<%=request.getAttribute("validIdentifyNumber")%>">
		<input type="hidden" name="validstatusUsercode" value="<%=request.getAttribute("validstatusUsercode")%>">
		<!-- mantis： CAR0051 ，處理人員： David ，需求單編號： CAR0051   end -->
      
	<table class="common" cellpadding="5" cellspacing="1" align="center" border="0">
        
<%
  java.text.DecimalFormat decimalFormat1 = new java.text.DecimalFormat("#,##0.00");
  java.text.DecimalFormat decimalFormat= new java.text.DecimalFormat("0.00");
  java.text.DecimalFormat gradeFormat= new java.text.DecimalFormat("0.000");
  String strsumAmount="";
  String strsumPremium="";

  String businessNo     = request.getParameter("iBusinessNo");
  String businessType   = request.getParameter("iBusinessType");
  String comCode =   (String) session.getAttribute("myComCode");
  String userCode =  (String) session.getValue("myUserCode");
  String strComCode = "";
  double profitRate = 0.00;   //优惠
  double disRate = 0.00;      //手续费
  String strLanguage ="";  //取语言种类
  
  PrpDuserDto user = (PrpDuserDto) (session.getAttribute("user"));
   
%>
  
<tr class=common>
  <td colspan="4">
      <%--查看业务详细信息ip --%>
 	  <input type="hidden" name="PrpallIp" value="${iPrpallIp }">
      <input type=hidden name="hiBusinessNo" value="<%=businessNo %>">
      <input type=hidden name="hiBusinessType" value="${iBusinessType }">
      <input type=hidden name="CommonRisk" value="">
      <input type=hidden name="OperatorCode" value="">
      <!-- add by xuhuilin 需求150 2016年8月21日 begin -->
	  <input type="hidden" name="workStatus" value="<%=workStatus%>">
	  <input type="hidden" name="valueType" value="<%=valueType%>">
	  <!-- 拒限保 -->		
	  <input type="hidden" name="refuseLimiteInsurance" value="<%=refuseLimiteInsurance%>">
	  <!-- 名單檢測 -->
	  <input type="hidden" name="listDetection" value="<%=listDetection%>">
	  <!-- 風險評級 -->
	  <input type="hidden" name="riskRating" value="<%=riskRating%>">	
	  <!-- add by xuhuiling 需求150 2016年8月21日 end -->
      <%
        String strClassCode = "";
      
    	//提交分入确认
        String businessFlag = "";
        String verifyFlag  ="0";
        
      	//免导团单查看详细信息时，调用自己的页面
        String strPolicySort="";
      	
        if(request.getAttribute("PrpTmainDto")!=null)
        {
          	//增加千分位
          	PrpTmainDto prpMainDto = (PrpTmainDto)request.getAttribute("PrpTmainDto");
          	strClassCode=prpMainDto.getClassCode();
          	strsumAmount=prpMainDto.getSumAmount()+"";
          	strsumAmount=decimalFormat1.format(Double.parseDouble(ChgData.chgStrZero(strsumAmount)));
          	strsumPremium=prpMainDto.getSumPremium()+"";
          	strsumPremium=decimalFormat1.format(Double.parseDouble(ChgData.chgStrZero(strsumPremium)));

          	//免导团单查看详细信息时，调用自己的页面
          	strPolicySort=prpMainDto.getPolicySort();

          	strComCode=prpMainDto.getComCode();
          	strClassCode=prpMainDto.getClassCode();
          	profitRate = prpMainDto.getDiscount()*100;
          	disRate = prpMainDto.getDisRate();
           	businessFlag= prpMainDto.getBusinessFlag();
           	strLanguage =prpMainDto.getLanguage();
         }
		%>
    	<input type="hidden" name="businessFlag" value="${businessFlag}">
    
    	<span id="spanInfo" >
    	<table width=100%>
      		<!--报价单信息-->
      		<tr>
      			<td width="100%">
       			<table cellpadding="5" cellspacing="1" class="common" align="center" style="width:100%">
       			<s:if test='prpQmain!=null'>
          			<tr class=listtitle>
            			<td colspan="4" ><s:text name="undwrt.CommonDealContentQta.quotationSumInfo"/></td>
          			</tr>
		          	<tr>
		            	<td class=title4><s:text name="undwrt.CommonDealContentQta.risk"/>：</td>
		            	<td class=input4><s:property value="prpQmain.riskCode"/></td>
		            	<td class=title4><s:text name="undwrt.CommonDealContentQta.belongOrganization"/>：</td>
		            	<td class=input4><s:property value="prpQmain.comCode"/></td>
		          	</tr>
		          	<tr>
						<td  class=title4><s:text name="undwrt.CommonDealContentQta.quoteNo"/>：</td>
			            <td class=input4>
			            	<s:property value="prpQmain.proposalNo"/>
			            	<%--提交分入确认--%>
			            	<input type="hidden" name="proposalNo" description="<s:text name='undwrt.CommonDealContentQta.quoteNo'/>" 
			            		value="<s:property value="prpQmain.proposalNo"/>">
			            </td>
		            	<td class=title4><s:text name="undwrt.CommonDealContentQta.belongOrganizationName"/>：</td>
		            	<td class=input4><s:property value="comCName"/></td>
		            	<td class=title4>
			            	<input type="hidden" name="riskCode" description="<s:text name='undwrt.pages.undwrtDeal.riskCode'/>" 
			            		value="<s:property value="prpQmain.riskCode"/>">
			            	<input type="hidden" name="hiClassCode" description="<s:text name='undwrt.CommonDangerUnits.riskTypeCode'/>" 
			            		value="<s:property value="prpQmain.classCode"/>">
			            </td>
		          	</tr>
          			<tr>
          			<s:if test='strClassCode=="26" || strClassCode=="27"'>
             			<td class=title4><s:text name="undwrt.CommonDealContentQta.policyName"/>：</td>
            			<td class=input4><s:property value="prpQmain.appliName"/></td>
          			</s:if>
            		<s:else>
            			<td class=title4><s:text name="undwrt.CommonDealContentQta.insuredName"/>：</td>
            			<td class=input4><s:property value="prpQmain.insuredName"/></td>
            		</s:else>
            
            		<s:if test='"MC"==iRiskCode'>
            			<td class=title4>
            			   <%--水险的TB险别没有起航日期20140715 --%>
	        				<s:if test='"TB"!=rationCode'>
            					<s:text name="undwrt.pages.undwrtDeal.startDate"/>：
            				</s:if>
            			</td>
            			<td class=input4>
            				<s:if test='"TB"!=rationCode'>
            					<rc:rcDate value = "${prpQmain.startDate}" format="yyyy-MM-dd"/><s:text name="prompt.day"/>
            				</s:if>
            			</td>
            		</s:if>
            		<s:else>
            			<td class=title4><s:text name="undwrt.CommonDealContentQta.insureDuration"/>：</td>
            			<td class=input4><rc:rcDate value = "${prpQmain.startDate}" format="yyyy-MM-dd"/>&nbsp;<s:text name="prompt.day"/>${prpQmain.startHour}
            				<s:text name="prompt.hour"/><s:text name="prompt.start"/><s:text name="prompt.to"/>&nbsp;<rc:rcDate value = "${prpQmain.endDate}" format="yyyy-MM-dd"/>
            			<s:text name="prompt.day"/>${prpQmain.endHour}<s:text name="prompt.hour"/>
            			</td>
           			</s:else>
          			</tr>
		          	<tr>
		            <td class=title4><s:text name="undwrt.CommonDealContentQta.sumInsureAmount"/>：</td>
		            <td class=input4><s:property value="prpQmain.currency"/>&nbsp;
		            	<%-- 改为千分位 --%>
		            	<input type="text" class=readonly name="sumAmount" value="<fmt:formatNumber value="${prpQmain.sumAmount}" pattern="#,##0.00"/>"></td>
		            <td class=title4><s:text name="undwrt.CommonDealContentQta.sumInsureFee"/>：</td>
		            <td class=input4><s:property value="prpQmain.currency"/>&nbsp;
		            	<input type="hidden" name="TemCurrency" value="<s:property value="prpQmain.currency"/>"/>
		            	<input type="text" class=readonly name="sumAmount" value="<fmt:formatNumber value="${prpQmain.sumPremium}" pattern="#,##0.00"/>"></td>
		          </tr>
         	</s:if>
        </table>
       </td>
      </tr>

		<tr>
			<td>
   				<Input name="butDetail" class="button" type="button" value="<s:text name='undwrt.pages.undwrtDeal.detailedInformation'/>" 
   					onclick="showBusinessInfo('${strComCode}')">
   					<s:if test="AllowEdit">
   					<Input name="butDetail" class="longbutton" type="button" value="<s:text name='undwrt.CommonDealContentQta.editInsurence'/>" 
   					onclick="editBusinessInfo();">
   					</s:if>
        	</td>
      	</tr>
      	
    	<!-- 原始标的信息 -->
      <tr>
      <td width="100%">
     	<table cellpadding="5" cellspacing="1" class="common" align="center" style="width:100%">
       	<%
  		if(strClassCode.startsWith("26") || strClassCode.startsWith("27")){
		%>
   			<tr class=listtitle>
            	<td colspan="7" ><s:text name="undwrt.CommonDealContentQta.originalTargetInfo"/></td>
           	</tr>
           	<tr class=common>
            	<td><s:text name="undwrt.CommonDealContentQta.clauseName"/></td>
            	<td><s:text name="undwrt.CommonDealContentQta.riskDuty"/></td>
            
            	<s:if test='riskCode!="2727"'>
            	<td><s:text name="undwrt.CommonDealContentQta.rebate"/></td>
            	</s:if>
 
        		<s:if test='riskCode=="2703" || riskCode=="2708"'>           
            	<td><s:text name="undwrt.CommonDealContentQta.copies"/></td>
            	</s:if>
            
           		<s:if test='riskCode!="2727"'>
            	<td><s:text name="undwrt.CommonDealContentQta.peopleCount"/></td>
            	</s:if>
            	
            	<td><s:text name="undwrt.CommonDealContentQta.insureAmout"/></td>
            	<td><s:text name="undwrt.CommonDealContentQta.insureFee"/></td>
           	</tr>
           	<s:iterator id="ItemKind" status="statu" value="ItemKind">
           	<tr class=common>
            	<td><input class="formtitle1"  readonly  value="<s:property value="#ItemKind.kindName"/>" ></td>
            	<td><input class="formtitle1"  readonly  value="<s:property value="#ItemKind.itemDetailName"/>"></td>
            
            	<s:if test='riskCode!="2727"'>         
            	<td><input class="formtitle1"  readonly  value="<s:property value="#ItemKind.discount"/>"></td>
            	</s:if>
            
        		<s:if test='riskCode=="2703" || riskCode=="2708"'>        
            	<td><input class="formtitle1"  readonly  value="<s:property value="#ItemKind.value"/>"></td>
       			</s:if>
       
       			<s:if test='riskCode!="2727"'>    
            	<td><input class="formtitle1"  readonly  value="<s:property value="#ItemKind.quantity"/>"></td>
       			</s:if>    
            	<%-- 改为千分位 --%>
           	 	<td><input class="formtitle1"  readonly  value="<s:property value="#ItemKind.amount"/>"></td>
            	<td><input class="formtitle1"  readonly  value="<s:property value="#ItemKind.premium"/>"></td>
           	</tr>
          	</s:iterator>
			<%
  			}else{
			%>
			<tr class=listtitle>
            	<td colspan="10" ><s:text name="undwrt.CommonDealContentQta.originalTargetInfo1"/></td>
           	</tr>
           	<tr class=common>
            	<td><s:text name="undwrt.CommonDealContentQta.serialNo"/></td>
            	<td><s:text name="undwrt.CommonDealContentQta.kind"/></td>
            	<td><s:text name="undwrt.CommonDealContentQta.targetProject"/></td>
            	
            	<s:if test='strClassCode=="09" || strClassCode=="10"'>
            	<td><s:text name="undwrt.CommonDealContentQta.insuredObjectName"/></td>
            	</s:if>
            	<s:else>
            	<td><s:text name="undwrt.CommonDealContentQta.targetName"/></td>
            	</s:else>
            	<%--水险没有邮编区号20140702 --%>
				<s:if test='"MC"!=iRiskCode'>
            	<td><s:text name="undwrt.CommonDealContentQta.postcode"/></td>
            	<td><s:text name="undwrt.CommonDealContentQta.targetAdress"/></td>
            	</s:if>
            	<td><s:text name="undwrt.CommonDealContentQta.currency"/></td>
            	
             	<%--15险类特殊处理--%>
             	<%
            	if(strClassCode.startsWith("15")){
            	%>
           		<td><s:text name="undwrt.CommonDealContentQta.totalDutyLimitAmout"/></td>
            	<td><s:text name="undwrt.CommonDealContentQta.perEventDutyLimitAmout"/></td>
             	<%  
             	}
          		else
          		{
            	%>
             	<td><s:text name="undwrt.CommonDealContentQta.insureAmout"/></td>
             	<%
            	}
            	%>
             	
         		<td><s:text name="undwrt.CommonDealContentQta.insureFee"/></td>
          	</tr>
			<s:iterator id="ItemKind" status="statu" value="#request.ItemKind">
 			<tr class=common>
            	<td><input class="formtitle1" name="itemKindNo" readonly value="<s:property value="#ItemKind.itemKindNo"/>" ></td>
            	<td>
              		<input class="formtitle1"  readonly  value="<s:property value="#ItemKind.kindName"/>">
              		<input type=hidden value="<s:property value="#ItemKind.kindCode"/>" >
            	</td>
	            <td><input class="formtitle1" name=""  readonly value="<s:property value="#ItemKind.itemCode"/>" ></td>
	            <td><input class="formtitle1" name=""  readonly value="<s:property value="#ItemKind.itemDetailName"/>" ></td>
	             <%--水险没有邮编区号20140702 --%>
				<s:if test='"MC"!=iRiskCode'>
	            <td>
	            	<input class="formtitle1" name=""  readonly value="<s:property value="#ItemKind.addressCode"/>" >
	            </td>
	            <td>
	            	<input class="formtitle1" name=""  readonly value="<s:property value="#ItemKind.addressName"/>" >
	            </td>
	            </s:if>
	            <td><input class="formtitle1" name="iCurrency" readonly value="<s:property value="#ItemKind.currency"/>" ></td>
          		<%--15险类特殊处理--%>
           		<%
            	if(strClassCode.startsWith("15")){
            	%>
            	<td>
            		<input class="formtitle1" name="iAmount" readonly 
            			value="<fmt:formatNumber value="${amount }" pattern="#,##0.00"/>"/></td>
           		<%--每次事故赔偿限额--%>
            	<td>
            		<input class="formtitle1" name="limitFee" readonly 
            			value="<fmt:formatNumber value="${limitFee }" pattern="#,##0.00"/>"/></td>
          		<% }else{ %>
             	<%-- 改为千分位 --%>
            	<td>
            		<input class="formtitle1" name="iAmount" readonly 
            			value="<fmt:formatNumber value="${amount }" pattern="#,##0.00"/>"/></td>
             	<%
            	}
            	%> 
            	<td>
            		<input class="formtitle1" name="iPremium" readonly 
            			value="<fmt:formatNumber value="${premium }" pattern="#,##0.00"/>" >
              		<input type="hidden" name="calculateFlag" 
              			value="<s:property value="#ItemKind.calculateFlag"/>" >
             	</td>
          	</tr>
        	</s:iterator>
			<%
  			}
			%>
        </table>
	</td>
	</tr>
 </table>
 </span>
 </td>
</tr>
<!-- add by wangcan 2015/11/30 如果已经核保通过，不能再次进行核保-->
<s:if test='iNodeStatus != "4" && iNodeStatus != "0"'>
			<tr>
	            <td class="title4" colspan="4">
	            	<%--历次审核意见 --%>
	                <Input name="butViewTranceInfo" class="longbutton" type="button" 
	                	value="<s:text name='undwrt.CommonDealContent.historyApprovalAdvice'/>" onclick="viewTranceInfo()">
	            </td>
        	</tr>
      	<tr class=listtitle>
       		<td colspan="9" ><s:text name="undwrt.CommonDealContentQta.approvalInfo"/></td>
        </tr>
        <tr>
        	<td class=title4 ><s:text name="undwrt.CommonDealContentQta.approvalPhrase"/>：</td>
          	<td class=input4>
          		<!-- mantis： CAR0051 ，處理人員： David ，需求單編號： CAR0051   原因  業務人員失效減核問題-核保系統檢核 -->
	            <input type="hidden" name="notionFlag" value="">
            	<select class=common name="notion" onchange="changeNotion1(this)" >
               		<option value="">----- <s:text name="undwrt.CommonDealContentQta.pleaseChoose"/> -----</option>
               		<!-- add by xuhuiling 需求150 當工作狀態是拒保 -->
             		<%
             			if(workStatus!=null && workStatus.equals("03")){
             		%>
             				<option  value='002'>
							拒絕承保
							</option>
             		<%
             			}
             			if(workStatus==null||!"03".equals(workStatus)){
             		%>
			        <s:iterator value="notionCodeList" status="statu" id="prpDcode">
						<option  value="<s:property value="#prpDcode.id.codeCode"/>">
							<s:property value="#prpDcode.id.codeCName"/>
						</option>
				 	</s:iterator>
             		<%		
             			}
             		
             		%>
	            	<!-- add by xuhuiling 需求150 20160820 -->
				 	
            	</select>
          	</td>
          	<td class=title4><s:text name="undwrt.CommonDealContentQta.signApprovalAdvice"/>：</td>
          	<td class=input4>
         		<textarea class=big wrap="soft" name="HandleText"><s:iterator value="uwNotionList" status="statu" id="uwNotion"><s:property value='#uwNotion.handleText'/></s:iterator></textarea>
          	</td>
        </tr>
        </s:if>
	</table>
	<table class=two cellpadding="5" cellspacing="1" class="common" align="center" style="width:100%">
	<s:if test='iNodeStatus != "4" && iNodeStatus != "0"'>	 	
		<tr>
		<%
  			if(!editType.equals("query")){
		%>
			<td class="button">
       			<input type="hidden" name="taskCode" value="">
              	<Input name="butSaveForm" class="button" type="button" value="<s:text name='undwrt.CommonDealContent.tempSave'/> " onclick="return saveQtaTask();">
           	</td>
           	<td class="button">
             	<Input name="butCancel" class="button" type="button" value="<s:text name='undwrt.CommonDealContent.giveUpTask'/>" onclick="return cancelTask();">
           	</td>
           	<td class="button">
			  	<%--改派任务 --%>
			    <Input name="butCancel" class="button" type="button" value="<s:text name='undwrt.CommonDealContent.sendTask'/>" 
			      		onclick="return sendTaskOne();">
			</td>  
  	       	<td class="button">
  	       	<s:if test='isUndwrtFlag!="1"'>
              	<Input type="button" class="button" name="passBtn" value="<s:text name='undwrt.CommonDealContent.approvalPass'/>" onclick="submitTaskQta(1);">
            </s:if>
           	</td>
           	<s:if test="${userNodeNo }==${nodeNomax } ">
           	<td class="button">
		    	<input type="hidden" class="button" name="submitSuperior" value="<s:text name='undwrt.pages.undwrtDeal.submitSuperior'/>"
		    		onclick="submitModifyTask('SubmitSuperior','3');">
			</td>
			</s:if>
			<s:else>
			<td class="button">
		    	<input type="button" class="button" name="submitSuperior" value="<s:text name='undwrt.pages.undwrtDeal.submitSuperior'/>"
		    		onclick="submitModifyTask('SubmitSuperior','3');">
			</td>
			</s:else>
			
           	<td class="button">
              	<input type="button" class="button" name="submitJunior" value="<s:text name='prompt.messages.sendUpdate'/>"
              		onclick="submitModifyTask('SubmitJunior','2');">
           	</td>
       	<%
    		} else {
		%>
           	<td class="button">
              	<Input type="hidden" class="button" name="butQuery" alt="<s:text name='prompt.back'/>" value="<s:text name='prompt.back'/> " onclick="history.back(-1);">
           	</td>
       	<%
			}
		%>
		</tr>
		<!-- add by wangcan 2015/11/30 如果已经核保通过，不能再次进行核保-->
	</s:if>
		<s:else>
			<tr>
				<td style="text-align:center">
					<s:if test='iNodeStatus == "4" || iNodeStatus == "0"'>
					<!-- 返回繼續處理 -->
					<input type="button" class="longbutton"
					value="<s:text name='undwrt.pages.undwrtDeal.backContinueDispose'/>" onclick="history.back();" >
					</s:if>
				</td>
			</tr>
		</s:else>
	</table>
    </form>
  </body>
</html>