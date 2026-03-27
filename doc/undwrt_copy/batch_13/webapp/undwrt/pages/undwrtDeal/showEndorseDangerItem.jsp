<%@ page language="java" %>
<%@ page contentType="text/html; charset=GBK" %>
<%@ page language="java" %>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<%@ page import="com.sinosoft.sysframework.reference.AppConfig"%>
<%@page import="com.sinosoft.undwrt.undwrtBase.model.WfLog"%>
<%@ include file="CommonStyle.html"%>

<html>
  <head>
   <title>
   		<%--查看批单的划分风险评估的子信息 --%>
   		<s:text name="undwrt.showEndorseDangerItem.showAuthBillDivideRiskAssessmentSonInfo"/>
   </title>
    <!-- 公用函数 -->
    <jsp:include page="/common/meta_css.jsp" />
	<jsp:include page="/common/meta_js.jsp" />
    <script src="/undwrt/common/js/Common.js"></script>
    <script src="/undwrt/common/js/Common_undwrt.js"></script>
    <script src="/undwrt/common/js/CommonTaskDeal.js"></script>
    <script type="text/javascript" src="/undwrt/common/js/CodeSelect.js" ></script>
    <script type="text/javascript" src="/undwrt/common/jspl/Common.js" ></script>
    <script type="text/javascript" src="/undwrt/common/jspl/MulLine.js" ></script>
    <script type="text/javascript" src="/undwrt/common/jspl/Process.js" ></script>
    <script type="text/javascript" src="/undwrt/common/jspl/CustomDataDefine.js" ></script>
    <script type="text/javascript" src="/undwrt/common/jspl/PlatformDataDefine.js" ></script>
    <script type="text/javascript" src="/undwrt/common/jspl/Application.js" ></script>
    <script src="/undwrt/pages/undwrtDeal/js/ShowDangerItem.js"></script>

    <!-- 页面样式 -->
    <link rel="stylesheet" type="text/css" href="/undwrt/css/Standard.css">
  </head>
<body onload="initEndorseDangerUnitAtItem();">  
<% 
   String businessType  = request.getParameter("businessType");
   String businessNo    = request.getParameter("businessNo");
   String policyNo      = request.getParameter("policyNo");
   String dangerNo      = request.getParameter("hiDangerNo");
   //获得险种代码
   String riskCode     = request.getParameter("riskCode");
   String classCode     = request.getParameter("classCode");
   String openerIndex   = request.getParameter("openerIndex");    
   String isNewDangerInfo = request.getParameter("NewDangerInfo");  //是否是新加的一个危险单位，是为1
  
   String editType = request.getParameter("editType");
   String showDangerItemFlag = request.getParameter("showDangerItemFlag");
   String includeAccident = request.getParameter("includeAccident");
   WfLog wfLogDto = (WfLog)session.getAttribute("wfLogDto");
   String businessNature = request.getParameter("businessNature");
   String channelType = request.getParameter("channelType");
   String cartypeCode = request.getParameter("cartypeCode");
   String exchRateCNY = request.getParameter("exchRateCNY");

   
   int nodeNo = wfLogDto.getNodeNo();
   String flag            = (String)request.getAttribute("flag");
   String modifyflag = "";

   if(editType.equals("query")||(!(nodeNo>7)))
   {
     modifyflag = "0";
   }else{
   	 modifyflag = "1";
   }
 %>
<form name="DangerItemForm">
 <input type= "hidden" name="DealType">
 <input type= "hidden" name="hiBusinessType"  value="<%=businessType%>">
 <input type= "hidden" name="hiDangerNo"      value="<%=dangerNo%>"> 
 <input type= "hidden" name="businessType"    value="<%=businessType%>">
 <input type= "hidden" name="businessNo"      value="<%=businessNo%>">
 <input type= "hidden" name="policyNo"        value="<%=policyNo%>">
 <input type= "hidden" name="classCode"       value="<%=classCode%>">
 <input type= "hidden" name="openerIndex"     value="<%=openerIndex%>">
 <input type= "hidden" name="isNewDangerInfo" value="<%=isNewDangerInfo%>"> 
 <input type= "hidden" name="editType"        value="<%=editType%>">
 <input type= "hidden" name="showDangerItemFlag" value="<%=showDangerItemFlag%>">
 <input type="hidden" name="includeAccident"  value="<%=includeAccident%>">
 <input type="hidden" name="lowRetentionValue" value="<s:property value="lowRetentionValue" />"> 
 <input type="hidden" name="heiRetentionValue" value="<s:property value="heiRetentionValue" />"> 
          
  <table border="0" align="center" width="100%">
   <tr>
     <td>
	<!--  危险单位主险信息 -->
   	<span style="display:none">
       <table class="common"  style="display:none" id="DangerUnit_Data" cellspacing="1" cellpadding="0">
       <tbody>
           <td>
             <table class="common" style="width:100%" cellspacing="1" cellpadding="0">
             
             <tr class="listtitle">
			   				<td width='5%'>
			   					<%--序号 --%>
			   					<s:text name="undwrt.showEndorseDangerItem.serialNo"/>
			   				</td>
			   				<td width='10%'>
			   					<%--险种 --%>
			   					<s:text name="undwrt.showEndorseDangerItem.risk"/></td>
	  						<td width='10%'>
	  							<%--险种名称 --%>
	  							<s:text name="undwrt.showEndorseDangerItem.riskName"/>
	  						</td>
			   				<td width='10%'>
			   					<%--描述 --%>
			   					<s:text name="undwrt.showEndorseDangerItem.describe"/>
			   				</td>
			   				<td width='15%'>
			   					<%--地址 --%>
			   					<s:text name="undwrt.showEndorseDangerItem.address"/>
			   				</td>
			   				 <% if (classCode.equals("27") && !riskCode.equals("2729") && includeAccident.equals("Y")) {%>
								<td width='5%' colspan='2'>
									<%--意健险PML值 --%>
									<s:text name="undwrt.showEndorseDangerItem.accidentHealthPMLValue"/>
								</td>
								<% } %>
			   				 <% if ((classCode.equals("15")||riskCode.equals("1107")||riskCode.equals("1001")||riskCode.equals("2201")||riskCode.equals("0109")||riskCode.equals("0907")||riskCode.equals("2729")) && includeAccident.equals("Y")) {%>
								<td width='5%' colspan='2'>AOA/PML</td>
								<% } %>								
								<td width='5%'>
									<%--币别 --%>
									<s:text name="undwrt.showEndorseDangerItem.currency"/>
								</td>
								<td width='8%'>
									<%--原始保额--%>
									<s:text name="undwrt.showEndorseDangerItem.originalProtectAmount"/>
								</td>
								<td width='8%'>
									<div style="display:none">
									<%--变化保额 --%>
									<s:text name="undwrt.showEndorseDangerItem.changeProtectAmount"/>
									</div>&nbsp;
								</td>
								<td width='8%'>
									<%--原始保费 --%>
									<s:text name="undwrt.showEndorseDangerItem.originalProtectFee"/>
								</td>
								<td width='8%'>
									<div style="display:none">
									<%--变化保费--%>
									<s:text name="undwrt.showEndorseDangerItem.changeProtectFee"/>
									</div>&nbsp;
								</td>
								<td width='5%'>
									<%--占比 --%>
									<s:text name="undwrt.showEndorseDangerItem.occupyScale"/>%
								</td>	
		     </tr>
		    <tr class="common">
			  		<td rowspan="3">
			  			<input type="hidden" description="<s:text name='undwrt.commonPolicyScale'/>" name ="baseRate">
			  			<input class="free" name="dangerNo" readonly description="<s:text name='undwrt.pages.undwrtDeal.serialNo'/>" >
			  		</td>
			  		<td>
			  			<input class="free" name="riskCode" >
			  		</td>
			  		<td>
			  			<input class="free" name="riskName" >
			  		</td>
			  		<td >
			  			<input class="free" name="dangerDesc" description="<s:text name='undwrt.page.riskUnitDescribe'/>">
			  		</td>
		      		<td>
		      			<input class="free" name="dangerAddress" description="<s:text name='undwrt.page.addressDescribe'/>">
		      		</td>
		      			<% if (classCode.equals("27") && !riskCode.equals("2729")&& includeAccident.equals("Y")) {%>
					<td>
						<input class="free" name="speCurrency" description="<s:text name='undwrt.PMLCurrencyKind'/>" onblur="checkNumber(this)">
					</td>
					<td>
						<input class="free" name="speValue" description="<s:text name='undwrt.CommonDangerUnits.PMLValue'/>" onblur="checkNumber(this);" >
					</td>
					<% }%>
					 <% if ((classCode.equals("15")||riskCode.equals("1107")||riskCode.equals("0907")||riskCode.equals("1001")||riskCode.equals("2201")||riskCode.equals("0109")||riskCode.equals("2729")) && includeAccident.equals("Y") ) {%>
				<td>
					<input class="free" name="speCurrency" description="<s:text name='undwrt.PMLCurrencyKind'/>" onblur="checkNumber(this)">
				</td>
				<td>
					<input class="free" name="speValue" description="<s:text name='undwrt.CommonDangerUnits.PMLValue'/>" onblur="checkNumber(this);" >
				</td>
					<% }%>
			  <td>   
			  	<input class="free" name="currency" readonly description="<s:text name='undwrt.pages.undwrtDeal.Currency'/>" >
			</td>			 
			<td>
				<input class="free"  readonly name="amount" description="<s:text name='undwrt.pages.undwrtDeal.coverage'/>" onblur="checkNumber(this)">
			</td>
			<td style="display: none">
				<input class="free" name="chgAmount" description="<s:text name='undwrt.showEndorseDangerItem.changeProtectAmount'/>" onblur="checkNumber(this)">
			</td>
			<td style="display: none">
				<input class="free"  readonly name="premium" description="<s:text name='undwrt.PolicyDangerUnits.protectFee'/>" onblur="checkNumber(this)">
			</td>
			<td style="display: none">
				<input class="free" name="chgPremium" description="<s:text name='undwrt.EndorseDangerUnits.changeProtectFee'/>" onblur="checkNumber(this)">
			</td>
	      	<td>
	      		<input class="free"  readonly name="dangerShare" description="<s:text name='undwrt.EndorseDangerUnits.occupyScale'/>" onblur="checkNumber(this)">
	      	</td>
		  </tr> 
			<tr class=listtitle>
			 <td>
			 	<%--风险等级 --%>
			 	<s:text name="undwrt.showEndorseDangerItem.riskLevel"/>
			 </td>
			 <td colspan='2'>
			 	<%--风险名称 --%>
			 	<s:text name="undwrt.showEndorseDangerItem.riskName"/>
			 </td>
			 <td>
			 	<%--自留额 --%>
			 	<s:text name="undwrt.showEndorseDangerItem.autoRemainAmount"/>
			 </td>
			 <td colspan ='3'>
			 	<%--除外责任/申报业务 --%>
			 	<s:text name="undwrt.showEndorseDangerItem.exDutyApplyBusiness"/>
			 </td> 
             <td>
             	<%--进合约 --%>
             	<s:text name="undwrt.showEndorseDangerItem.intoContract"/>
             </td>	
			</tr>
			<tr class="common">
				
			  <td>
				  <input class="codestyle2"  name="riskLevel" description="<s:text name='undwrt.EndorseDangerUnits.riskLevel'/>"  <%=editType.equals("query")?"disabled":""%>
						   ONDBLCLICK="openDangerRiskInfo(this)">
			  </td>
			  
			  <td colspan='2'>
				  <input class="free" readonly name="riskLevelDesc" description="<s:text name='undwrt.CommonDangerUnits.dangerName'/>" >
				  <input class="free" name="riskClass">
				  <input class="free" name="riskClassDesc">
			  </td>
			   <td>
				   <input class="free" type='hidden' readonly name="retCurrency"
				   	 description="<s:text name='undwrt.CommonDangerUnits.autoAmountCurrency'/>">
					<%
				  if (classCode.equals("12")) {
				  %>     
					<input class="free" readonly  name="retentionValue" description="<s:text name='undwrt.pages.undwrtDeal.retentionMoney'/>">
					<input class="free" readonly  name="retentionValueHidden" description="<s:text name='undwrt.hideAutoRemainAmount'/>">
			  </td>
				  <%
				   }else 
				   {
				  %>
				  <input class="free"   name="retentionValue"  description="<s:text name='undwrt.pages.undwrtDeal.retentionMoney'/>">
				  <input class="free" readonly  name="retentionValueHidden" description="<s:text name='undwrt.hideAutoRemainAmount'/>">
			   </td>
			  <%
			   }
			  %>
	      <td colspan ='3'>
        	   <input type="hidden" name="dangerItemKind" value="">
	          	 <select name="itemKind" class=common  onchange="javascript:checkDangerItemFlag()">
               		<s:if test="#request.dangerExItemKind2!=null">
               		<s:iterator id="dangerExItemKind2" status="statu" value="#request.dangerExItemKind2">                                                  
               			<option value="<s:property value="#dangerExItemKind2.itemCode" />">
                			<s:property value="dangerExItemKind2.itemName" />
               			</option>
               		</s:iterator>
            		</s:if>
           		</select>
        		
        		
	          </td>
              <td>
              	<input type="checkbox" align="center" name="dangerItemFlag" description="<s:text name='undwrt.pages.undwrtDeal.flagLocal'/>" value="" >
                  <input type="hidden" name="hiDangerItemFlag">
                  <input type="hidden" name="hiDangerCoinsFlag">
                  <input type="hidden" name="hiDangerShareHolderFlag" value="">
                  <input type="hidden" name="hiDangerBusinessFlag" value="">
				  <input type="hidden" name="hiDangerBusinessNature" value="">
				  <input type="hidden" name="hiDangerChannelType" value="">
				  <input type="hidden" name="hiDangerCartypeCode" value="">
				  <input type="hidden" name="hiDangerExchRateCNY" value="">
              </td>  
				</tr>	
		   </table>
		   </td> 
		</tbody>	
	</table>
  </span>
  <span  id="spanDangerUnit" style="display:" cellspacing="1" cellpadding="0">
	<table border="0" class="sub" align="center" width="100%" id="DangerUnit"  >
		<thead>
		  <tr>
			<td colspan="14" class=listtitle>
				<%--批单划分风险评估信息 --%>
				<s:text name="undwrt.showEndorseDangerItem.authBillDivideRiskAssessmentInfo"/>
			</td>
		  </tr>	  
	   </thead>
       <tbody>
        <s:if test="#request.DangerDetail!=null">
        <s:iterator id="DangerDetail" status="statu" value="#request.DangerDetail">
           <td>
             <table class="common" style="width:99%" cellspacing="1" cellpadding="0">
             	<tr class="listtitle">
					<td  width='5%'>
			   			<%--序号 --%>
			   			<s:text name="undwrt.showEndorseDangerItem.serialNo"/>
			   		</td>
			  		<td width='10%'>
	   					<%--险种 --%>
	   					<s:text name="undwrt.showEndorseDangerItem.risk"/>
	   				</td>
 					<td width='10%'>
 						<%--险种名称 --%>
 						<s:text name="undwrt.showEndorseDangerItem.riskName"/>
 					</td>
	   				<td  width='10%'>
	   					<%--描述--%>
	   					<s:text name="undwrt.showEndorseDangerItem.describe"/>
	   				</td>
	   				<td  width='15%'>
	   					<%--地址 --%>
	   					<s:text name="undwrt.showEndorseDangerItem.address"/>
	   				</td>  
			   			 <% if (classCode.equals("27") && !riskCode.equals("2729") 
			   					 && includeAccident.equals("Y")) { %>
	          		       <td  width='5%' colspan='2'>
	          		       		<%--意健险PML值 --%>
	          					<s:text name="undwrt.showEndorseDangerItem.accidentHealthPMLValue"/>
	          			   </td>
	         			<% } %>
			  			<% if ((classCode.equals("15")||riskCode.equals("1107")||riskCode.equals("1001")
			  					||riskCode.equals("0907")||riskCode.equals("2201")||riskCode.equals("0109")
			  					||riskCode.equals("2729")) && includeAccident.equals("Y")) { %>
	          			<td  width='5%' colspan='2'>AOA/PML</td>
	         			<% } %>	         			
			 			<td  width='5%'>
			 				<%--币别 --%>
			 				<s:text name="undwrt.showEndorseDangerItem.currency"/>
			 			</td>
			 			<td  width='8%'>
			 				<%--原始保额 --%>
			 				<s:text name="undwrt.showEndorseDangerItem.originalProtectAmount"/>
			 			</td>
			 			<% if(!"A".equals(classCode) && !"B".equals(classCode)){ %>
			 			<td width='8%'>
			 				<%--变化保额--%>
			 				<s:text name="undwrt.showEndorseDangerItem.changeProtectAmount"/>
			 			</td>
			 			<td width='8%'>
			 				<%--原始保费--%>
			 				<s:text name="undwrt.showEndorseDangerItem.originalProtectFee"/>
			 			</td>
			 			<td width='8%'>
			 				<%--变化保费 --%>
			 				<s:text name="undwrt.showEndorseDangerItem.changeProtectFee"/>
			 			</td>
			 			<% } %>
			 			<td width='5%'>
			 				<%--占比 --%>
			 				<s:text name="undwrt.showEndorseDangerItem.occupyScale"/>%
			 			</td>			
		    		</tr>
		    		<tr class=common>
		    			<td rowspan="3">
		    				<input type="hidden" description="<s:text name='undwrt.commonPolicyScale'/>" name ="baseRate"
		      					value ="<s:property value="#DangerDetail.baseRate" />">
		    				<input class="free" readonly heigth="100px" name="dangerNo" 
		    					description="<s:text name='undwrt.pages.undwrtDeal.serialNo'/>" value="<%=dangerNo%>">
			    	   	</td> 
			    		<td>
						    <input type="text" class="codestyle2" name="riskCode"        
					       		ondblclick="code_CodeSelect(this,'RiskCode','0,1','Y','','');"
				          		onkeyup="code_CodeSelect(this,'RiskCode','0,1','Y','','');"
				          		onblur="updateExItemKind(this);"
				           		value ="<s:property value="#DangerDetail.riskCode" />"/>
		            		<iframe name=CodeFrame src="/undwrt/common/QueryCodeInputOverview.jsp" 
		            			style='DISPLAY:none;Z-INDEX:100;POSITION:absolute' marginwidth='0' 
		            			marginheight='0' hspace='0' vspace='0' frameborder='0' scrolling='auto'>
	            		</iframe>	
						</td>
		 				<td>
		 					<input type="text"  name="riskName"  value ="<s:property value="#DangerDetail.riskName" />">
		 				</td>
					  	<td>
						  	<input class="free" name="dangerDesc" description="<s:text name='undwrt.page.riskUnitDescribe'/>" 
								value="<s:property value="#DangerDetail.dangerDesc" />">
				 	 	</td>
			 	 		<td>
			  				<input class="free" name="dangerAddress" description="<s:text name='undwrt.EndorseDangerUnits.address1'/>" 
			  					value="<s:property value="#DangerDetail.addressName" />">
			 		 	</td>
						<% if(classCode.equals("27") && !riskCode.equals("2729") 
								&& includeAccident.equals("Y")) { %>
			 			<td>
			 				<input class="free" name="speCurrency" description="<s:text name='undwrt.PMLCurrencyKind'/>" 
			 					value="<s:property value="#DangerDetail.speCurrency" />">
						</td>
						<td>
						 	<input class="free" name="speValue" description="<s:text name='undwrt.CommonDangerUnits.PMLValue'/>" 
						 		value="<fmt:formatNumber value="${speValue}" pattern="0.00"/>" >
						</td>
						<% } %>
						<%--added by jiabeilei begin 2008-05-05   雇主责任险增设最大可能损失填写栏目--%>	
						<% if((classCode.equals("15")||riskCode.equals("1107")||riskCode.equals("1001")
								||riskCode.equals("0907")||riskCode.equals("2201")||riskCode.equals("0109")
								||riskCode.equals("2729")) && includeAccident.equals("Y")) { %>
					 	<td>
					 		<input class="free" name="speCurrency" description="<s:text name='undwrt.maxMaybeLossCurrency'/>" 
					 			value="<s:property value="#DangerDetail.speCurrency" />">
					 	</td>
					 	<td>
					 		<input class="free" name="speValue" description="<s:text name='undwrt.maxMaybeLossValue'/>" 
					 			value="<fmt:formatNumber value="${speValue}" pattern="0.00"/>" >
					 	</td>
						<% } %>		
	   					<%--added by jiabeilei end 2008-05-05   雇主责任险增设最大可能损失填写栏目--%>	
						<td>
							<input class="free" readonly name="currency" description="<s:text name='undwrt.pages.undwrtDeal.Currency'/>" 
								value="<s:property value="#DangerDetail.currency" />">
						</td>
						<td>
							<input class="free"  name="amount"  description="<s:text name='undwrt.pages.undwrtDeal.coverage'/>" 
								value="<fmt:formatNumber value="${amount}" pattern="0.00"/>" 
								onblur="checkNumber(this)">
						</td>
						<% if(!"A".equals(classCode) && !"B".equals(classCode)){ %>
						<td>
							<input class="free" readonly name="chgAmount"  description="<s:text name='undwrt.showEndorseDangerItem.changeProtectAmount'/>" 
								value="<fmt:formatNumber value="${chgAmount}" pattern="0.00"/>" >
						</td>
						<td>
							<input class="free"  name="premium" readonly description="<s:text name='undwrt.PolicyDangerUnits.protectFee'/>" 
								value="<fmt:formatNumber value="${premium}" pattern="0.00"/>" 
								onblur="checkNumber(this)">
						</td>
					 	<td>
					 		 <input class="free" readonly name="chgPremium" readonly description="<s:text name='undwrt.EndorseDangerUnits.changeProtectFee'/>" 
					 		 	value="<fmt:formatNumber value="${chgPremium}" pattern="0.00"/>">
					  	</td>
					  	<% } %>
			      		<td>
			      			<input class="free" readonly name="dangerShare" description="<s:text name='undwrt.CommonDangerUnits.occupyScale1'/>" 
			      				value="<s:property value="#DangerDetail.dangerShare" />" 
			      				onblur="checkNumber(this)">
			      		</td>
		    		</tr>
		    		
					<tr class=listtitle>
						<% if(!"A".equals(classCode) && !"B".equals(classCode)){ %>
						<td>
            				<s:text name="undwrt.showEndorseDangerItem.riskLevel"/></td>
						<td colspan='2'>
							<s:text name="undwrt.showEndorseDangerItem.riskDescribe"/></td>
						<td colspan='2'>
							<s:text name="undwrt.showEndorseDangerItem.autoRemainAmount"/></td>
						<% } %>
			 			<td colspan='4'>
			 				<s:text name="undwrt.showEndorseDangerItem.exDutyApplyBusiness"/></td>
            			<td>
            				<s:text name="undwrt.showEndorseDangerItem.intoContract"/></td>
					</tr>
					<tr class=common>
						<% if(!"A".equals(classCode) && !"B".equals(classCode)){ %>
						<td>
            				<input class="codestyle2"  name="riskLevel" description="<s:text name='undwrt.CommonDangerUnits.dangerLevel'/>"
							 	value="<s:property value="#ItemKind.riskLevel" />"  
							 	ondblclick="openDangerRiskInfo(this,1)">
						</td>
						<td colspan='2'>
							<input class="free"   readonly name="riskLevelDesc" description="<s:text name='undwrt.pages.undwrtDeal.riskGradeDescribe'/>" 
								value="<s:property value="#ItemKind.riskLevelDesc" />">
							<input type="hidden" name="riskClass" value="<s:property value="#ItemKind.riskClass" />">
							<input type="hidden" name="riskClassDesc" value="<s:property value="#ItemKind.riskClassDesc" />">
						</td>
						<td colspan='2'>
						  	<input class="free" type="hidden" readonly name="retCurrency" value="<s:property value="#ItemKind.retCurrency" />" 
						  		description="<s:text name='undwrt.CommonDangerUnits.autoAmountCurrency'/>"/>
						  	<input class="free" name="retentionValue" description="<s:text name='undwrt.pages.undwrtDeal.retentionMoney'/>" 
						  		onblur="compareRetentionValue();"
						  		value="<fmt:formatNumber value="${retentionValue}" pattern="0.00"/>"<%=(modifyflag.equals("0"))?"readonly":""%>/>
						    <input type="hidden" name="retentionValueHidden" description="<s:text name='undwrt.pages.undwrtDeal.retentionMoney'/>" 
						  		value="<fmt:formatNumber value="${retentionValue}" pattern="0.00"/>"/>
					  	</td>
						<% } %>
				  		<td colspan='4'>
			 	 			<input type="hidden" name="dangerItemKind" value="<s:property value="#DangerDetail.itemKind" />"/>
	       	  				<s:select id="mySelect" list="#request.dangerExItemKind" name="itemKind" 
	       	  					listKey="itemCode" listValue="itemName"/>    
        				</td> 
			  			<td>
				  			<input type="checkbox" name="dangerItemFlag" description="<s:text name='undwrt.ShowDangerItem.intoContract'/>" 
				  				<s:if test='#DangerDetail.flag=="10"'>checked</s:if>
				  				<s:if test='#DangerDetail.flag=="11"'>checked</s:if>
			      				value="<s:property value="#DangerDetail.flag" />">
				 			<input type="hidden" name="hiDangerItemFlag" value="00">
				 			<input type="hidden" name="hiDangerCoinsFlag" value="<s:property value="#DangerDetail.coinsFlag" />">
				 			<input type="hidden" name="endorseTimes" value="<s:property value="#DangerDetail.endorseTimes" />">
			  				<input type="hidden" name="hiDangerShareHolderFlag" value="<s:property value="#DangerDetail.shareHolderFlag" />">
			  				<input type="hidden" name="hiDangerBusinessFlag" value="<s:property value="#DangerDetail.businessFlag" />">
							<!-- modify by subeite, reason:rebuild sap, start 20081005-->
						  	<input type="hidden" name="hiDangerBusinessNature" value="<s:property value="#DangerDetail.businessNature" />">
						  	<input type="hidden" name="hiDangerChannelType" value="<s:property value="#DangerDetail.channelType" />">
						  	<input type="hidden" name="hiDangerCartypeCode" value="<s:property value="#DangerDetail.cartypeCode" />">
						  	<input type="hidden" name="hiDangerExchRateCNY" value="<s:property value="#DangerDetail.exchRateCNY" />">
							<!-- modify by subeite, reason:rebuild sap, end 20081005-->
			  			</td>
		   			</tr>
		   			
		   			<tr>
		   				<td colspan="4" align="center">
		   				<s:if test='#request.haveClaim=="N"'>
		   					<!-- ever was type="button",feel no necessary now.modify 20130516 by wangjun-->	
		      				<Input name="butViewRiskForm" class="longbutton" type="hidden" alt="s:text name='undwrt.ShowDangerItem.reNewRiskAccess'/>" 
		      					value="<s:text name='undwrt.ShowDangerItem.reNewRiskAccess'/>" onclick="openDangerRiskInfo(DangerItemForm.riskLevel[1]);">
		    			</s:if>
		    			</td>
		    			<td colspan="3" align="center">
		   	  				<%--查看风险评估 --%>
		   	 				<Input name="butViewRiskForm" class="longbutton" type="button" alt="<s:text name='undwrt.ShowDangerItem.showRiskAssessment'/>" 
		   	 					value="<s:text name='undwrt.ShowDangerItem.showRiskAssessment'/>" onclick="viewDangerRiskInfo();">
		    			</td>
			 		</tr>
		    	</table>
		    </td>
        </s:iterator>
        </s:if>
		</tbody>
	</table>
	</td></tr>
   	</table>
   
	<span style="display:none">
       <table class="common" style="display:none" id="PrpItemInfo_Data" cellspacing="1" cellpadding="0">
         <tr class ="common" backgroundColor="red">
		    <td>
				<input type="hidden" readonly  name="checkDanger"  value="">
				<input type="hidden" name="dangerFlag" value="0">
		    </td>
		    <td>
				<input class="free"  name="itemKindNo" value="">
		    </td>
            <td>
            	<input class="free"  name="ItemKindName"  value="" >
            	<input type=hidden name="ItemKindCode" value="" >
            </td>
            <td>
            	<input class="free"  name="ItemCode" value="" >
            </td>
            <td>
            	<input class="free"  name="ItemDetailName" value="" >
            </td>
            <td>
            	<input class="free"  name="ItemPost"  value="" >
            </td>
            <td>
            	<input class="free"  name="ItemAddressName" value="" >
            	<input type="hidden" name="sameRiskNo" value="" >
            </td>
            <td>
            	<input class="free"  name="ItemCurrency" style="width:22px" value="" >
            </td>
            <td>
            	<input class="free"  name="ItemAmount" value="0.00" readonly>
            </td>
            <td>
            	<input class="free"  name="ItemchgAmount" value="0.00">
            </td>
            <td>
            	<input class="free"  name="ItemPremium" value="0.00"  readonly>
            	<input type="hidden" name="itemKindFlag" value="" >	
            	<input type="hidden" name="currency2" value="">
            	<input type="hidden" name="exchangeRate" value="">
            	<input type="hidden" name="tolPremium" value="">
            </td>
            <td>
            	<input class="free" name="ItemchgPremium" value="0.00">
            </td> 
            <td>
               <input type="checkbox"   name="ItemcalculateFlag"  value="">  
               <input type="hidden"   name="hiItemcalculateFlag"  value="N">  
               <input type="hidden"   name="hiPdangerItemFlag"  value="">  
               <input type="hidden" name="tolAmount" value="">
            </td>
	     </tr>  
        </table>
  	</span>
	
	<span id="SpanPrpItemInfo" style="display:" cellspacing="1" cellpadding="0">
	<table class="common" cellpadding="5" cellspacing="1" align="center" id="PrpItemInfo">
	  	<thead>
          	<tr class=listtitle>
            	<td colspan="17" >
            		<%--原始标的信息 --%>
            		<s:text name="undwrt.showEndorseDangerItem.originalObjectMessages"/>
            	</td>
          	</tr>
          	<tr class=listtitle>
            	<td colspan='2'>
            		<%--序号 --%>
            		<s:text name="undwrt.showEndorseDangerItem.serialNo"/>
            	</td>
            	<td>
            		<%--险别 --%>
            		<s:text name="undwrt.showEndorseDangerItem.kind"/>
            	</td>
            	<td>
            		<%--标的项目 --%>
            		<s:text name="undwrt.showEndorseDangerItem.objectItem"/>
            	</td>
            	<td>
	            	<%--标的名称 --%>
	            	<s:text name="undwrt.showEndorseDangerItem.objectName"/>
            	</td>
            	<td>
	            	<%--邮编 --%>
	            	<s:text name="undwrt.showEndorseDangerItem.postcode"/>
            	</td>
            	<td>
	            	<%--标的地址--%>
	            	<s:text name="undwrt.showEndorseDangerItem.objectAddress"/>
            	</td>
            	<td>
	            	<%--币别 --%>
	            	<s:text name="undwrt.showEndorseDangerItem.currency"/>
	            </td>
	            <td>
		            <%--原始保额 --%>
		            <s:text name="undwrt.showEndorseDangerItem.originalProtectAmount"/>
	            </td>
	            <td style="display:none">
		      		<%--变化保额 --%>
		            <s:text name="undwrt.showEndorseDangerItem.changeProtectAmount"/>
	            </td>
	            <td style="display:none">
		            <%--原始保费 --%>
		            <s:text name="undwrt.showEndorseDangerItem.originalProtectFee"/>
	            </td>
	            <td style="display:none">
		     		<%--变化保费 --%>
		            <s:text name="undwrt.showEndorseDangerItem.changeProtectFee"/>
	            </td>
	            <td>
		            <%--计算保额 --%>
		            <s:text name="undwrt.showEndorseDangerItem.countProtectAmount"/>
	            </td>
            	<% if("A".equals(classCode) || "B".equals(classCode)){ %>
           		<%--是否临分 --%>
            	<td width='10%'>
            		<s:text name="undwrt.ShowDangerItem.isFacultative"/></td>
            	<td>
	            	<%--状态 --%>
	            	<s:text name="undwrt.showEndorseDangerItem.status"/>
            	</td>
            	<td>
            		<s:text name="undwrt.showEndorseDangerItem.riskLevel"/></td>
				<td colspan='2'>
					<s:text name="undwrt.showEndorseDangerItem.riskDescribe"/></td>
				<td colspan='2'>
					<s:text name="undwrt.showEndorseDangerItem.autoRemainAmount"/></td>
				<% } %>
          	</tr>
		</thead>
		<tbody>
		<s:if test="#request.ItemKind!=null">
		<s:iterator id="ItemKind" status="statu" value="#request.ItemKind">
    		<tr class=common>
       			<td>
          			<input type="checkbox" name="checkDanger" value="<s:property value="#ItemKind.dangerNo" />" 
             			<s:if test="#ItemKind.dangerNo!=0">
             				checked
             			</s:if>
           				onclick = "calPDangerAmountPremium()"/>
           			<input type="hidden" name ="dangerFlag">
           		</td>
            	<td>
            		<input class="formtitle1" readonly  name="itemKindNo" 
            			value="<fmt:formatNumber value="${itemKindNo}" pattern="0"/>">
            	</td>
            	<td>
            		<input class="formtitle1"  name="ItemKindName" readonly value="<s:property value="#ItemKind.kindName" />" >
            		<input type=hidden name="ItemKindCode" value="<s:property value="#ItemKind.kindCode" />" >
            	</td>
 				<td>
            		<input class="formtitle1" readonly name="ItemCode" value="<s:property value="#ItemKind.itemCode" />" >
            	</td>
            	<td>
            		<input class="formtitle1" readonly name="ItemDetailName" value="<s:property value="#ItemKind.itemDetailName" />" >
            	</td>
	            <td>
	            	<input class="formtitle1" readonly name="ItemPost"  value="<s:property value="#ItemKind.postCode" />" >
	            </td>
	            <td>
	           	 	<input class="formtitle1" readonly name="ItemAddressName" value="<s:property value="#ItemKind.addressName" />" >
	           	 	<input type="hidden" name="sameRiskNo" value="<s:property value="#ItemKind.sameRiskNo" />">
	            </td>
            	<td>
            		<input class="formtitle1" readonly name="ItemCurrency"  value="<s:property value="#ItemKind.currency" />" >
            	</td>
            	<td>
           		 	<input class="free"  name="ItemAmount" value="<fmt:formatNumber value="${amount}" pattern="0.00"/>" readonly >
            	</td>
	            <td style="display: none">
	            	<input class="free"  name="ItemchgAmount" value="<fmt:formatNumber value="${chgAmount}" pattern="0.00"/>" >
	            </td>
	            <td  style="display: none">
	            	<input class="free"  name="ItemPremium" value="<fmt:formatNumber value="${premium}" pattern="0.00"/>" readonly>
	            	<input type="hidden" name="currency2" value="<s:property value="#ItemKind.currency2" />">
	            	<input type="hidden" name="exchangeRate" value="<fmt:formatNumber value="${exchangeRate}" pattern="0.000000"/>">
	            	<input type="hidden" name="tolPremium" value="<fmt:formatNumber value="${tolPremium}" pattern="0.00"/>">
	            	<input type="hidden" name="tolAmount" value="<s:property value="#ItemKind.tolAmount" />">
	            </td>
	         	<td style="display: none">
	             	<input class="free"  name="ItemchgPremium" value="<fmt:formatNumber value="${chgPremium}" pattern="0.00"/>" >
	        	</td>
	        	<td>
	                <input type="checkbox" readonly  name="ItemcalculateFlag"  disabled value="<s:property value="#ItemKind.calculateFlag" />"  
	                		<s:if test='#ItemKind.calculateFlag=="Y"'>checked</s:if> >  
	                <input type="hidden"   name="hiItemcalculateFlag"  value="<s:property value="#ItemKind.calculateFlag" />">     
	                <input type="hidden"   name="hiPdangerItemFlag"  value="<s:property value="#ItemKind.flag" />">     
	         	</td>
                <%--针对车险增加是否 临分考量标识，选中为1，不选为0，默认不选中20131211--%>
                
                <% if("A".equals(classCode) || "B".equals(classCode)){ %>
              	<td>
             	<s:if test='#ItemKind.isFacultative==null || #ItemKind.isFacultative=="0"'>
              		<input type="checkbox"  name="facultativeFlag" value="<s:property value="#ItemKind.isFacultative" />" 
              			onclick="ChangeValue(DangerItemForm,<s:property value='#statu.index'/>)">
               		<input type="hidden"  name="isFacultative" value="0" />
               	</s:if>
               	<s:elseif test='#ItemKind.isFacultative=="1"'>
              		<input type="checkbox"  name="facultativeFlag" value="<s:property value="#ItemKind.isFacultative" />" checked 
              			onclick="ChangeValue(DangerItemForm,<s:property value='#statu.index'/>)">
               		<input type="hidden"  name="isFacultative" value="1" >
               	</s:elseif>
             	</td>
            	<td>
              	<s:if test='#ItemKind.flag=="U"'>
                	<%--修改 --%>
               		<s:text name="undwrt.showEndorseDangerItem.update"/>
             	</s:if>
            	<s:if test='#ItemKind.flag=="D"'>
                	<%--删除 --%>
                 	<s:text name="undwrt.showEndorseDangerItem.delete"/>
              	</s:if>
              	<s:if test='#ItemKind.flag=="B"'>
                	<%--删除 --%>
                	<s:text name="undwrt.showEndorseDangerItem.delete"/>
              	</s:if>
              	<s:if test='#ItemKind.flag=="I"'>
                 	<%--增加 --%>
               		<s:text name="undwrt.showEndorseDangerItem.add"/>
              	</s:if>
            	</td>
            	<td>
            		<input class="codestyle2"  name="riskLevel" description="<s:text name='undwrt.CommonDangerUnits.dangerLevel'/>"
				 	value="<s:property value="#ItemKind.riskLevel" />"  
				 	ondblclick="openDangerRiskInfo(this,<s:property value="#ItemKind.itemKindNo" />)"></td>
				<td colspan='2'>
					<input class="free"   readonly name="riskLevelDesc" description="<s:text name='undwrt.pages.undwrtDeal.riskGradeDescribe'/>" 
						value="<s:property value="#ItemKind.riskLevelDesc" />">
					<input type="hidden" name="riskClass" value="<s:property value="#ItemKind.riskClass" />">
					<input type="hidden" name="riskClassDesc" value="<s:property value="#ItemKind.riskClassDesc" />">
				</td>
				<td colspan='2'>
				  	<input class="free" type="hidden" readonly name="retCurrency" value="<s:property value="#ItemKind.retCurrency" />" 
				  		description="<s:text name='undwrt.CommonDangerUnits.autoAmountCurrency'/>"/>
				  	<input class="free" name="retentionValue" description="<s:text name='undwrt.pages.undwrtDeal.retentionMoney'/>" 
				  		onblur="compareRetentionValue();"
				  		value="<fmt:formatNumber value="${retentionValue}" pattern="0.00"/>"<%=(modifyflag.equals("0"))?"readonly":""%>/>
				    <input type="hidden" name="retentionValueHidden" description="<s:text name='undwrt.pages.undwrtDeal.retentionMoney'/>" 
				  		value="<fmt:formatNumber value="${retentionValue}" pattern="0.00"/>"/>
			  	</td>
				  <% } %>		
			</tr>
        </s:iterator>
        </s:if>
     	</tbody>
	</table>
	</span>

	<table border="0" width="100%">
		<tr>
	   		<% if (!editType.equals("query")){ %>
	    	<td class=button colspan=2 width=33%>
	      		<Input name="butSaveForm" class="button" type="button" alt="<s:text name='undwrt.save'/>" value="<s:text name='undwrt.save'/>" onclick="saveEndorseDangerItemTask();">
	    	</td>
	    	<td class=button colspan=2 width=33%>
	      		<Input name="butSaveForm" class="button" type="button" alt="<s:text name='undwrt.close'/>" value="<s:text name='undwrt.close'/>" onclick="endorseDangerItemTipBeforeClose();">
	     	</td>
	     	<td class=button width=34%>          
	          	<Input name="buttonReset" class="button" type="reset" alt="<s:text name='prompt.reset'/>"  value="<s:text name='prompt.reset'/>"  >
	      	</td>
	  		<% } else { %>
   			<td class=button>
          		<Input name="butSaveForm" class="button" type="button" alt="<s:text name='undwrt.close'/>" value="<s:text name='undwrt.close'/>" onclick="window.close();">
   			</td>
	  		<% } %>
	 	</tr>       
 	</table>
</form>
<script type="text/javascript">
	var fm = DangerItemForm;
  	checkDangerItemFlag();
</script>
</body>
</html>