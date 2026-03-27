<!--***************************************************************************
* Description: 投保单/保单的危险单位子信息界面
* Author     : 项目组
* CreateDate : 2005-5-4 20:30
* UpdateLog  ：Name       Date            Reason/Contents
*              jiabeilei  20080428        雇主责任险增设最大可能损失填写栏目 
****************************************************************************-->
<%@ page language="java" %>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/i18njs.jsp"%>


<%@page import="com.sinosoft.sysframework.reference.AppConfig"%>
<%@page import="com.sinosoft.undwrt.undwrtBase.model.WfLog"%>
<%@page import="com.sinosoft.utility.SysConfig"%>
<%@page import="com.sinosoft.reins.utility.bl.facade.BLFhRiskItemKindFacade"%>
<%@page import="com.sinosoft.reins.utility.dto.domain.FhRiskItemKindDto"%>
<%@page import="com.sinosoft.platform.dto.domain.PrpDriskConfigDto"%>
<%@page import="com.sinosoft.platform.ui.control.action.UIPrpDriskConfigAction"%>
<%@page import="com.sinosoft.common.schema.model.PrpTmain"%>
<%@page import="com.sinosoft.undwrt.undwrtDeal.service.spring.PrpallServiceSpringImpl"%>
<%@ include file="CommonStyle.html"%>
<%@page import="java.util.*"%>


<html>
  <head>
   <title>
   <%--查看风险评估子信息 --%>
   <s:text name="undwrt.ShowDangerItem.showRiskAssessmentSonInfo"/>
   </title>
    <!-- 公用函数 -->
    <jsp:include page="/common/meta_css.jsp" />
	<jsp:include page="/common/meta_js.jsp" />
    <script src="/undwrt/common/js/Common.js"></script>
    <script src="/undwrt/common/js/Common_undwrt.js"></script>
    <script src="/undwrt/common/js/CommonTaskDeal.js"></script>
    <script src="/undwrt/common/BLcommonCommon.js"></script>
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

<body onload=initDangerUnitAtItem()>
<% 
   String businessType = request.getParameter("businessType");
   String businessNo   = request.getParameter("businessNo");
   String classCode    = request.getParameter("classCode");
   String dangerNo     = request.getParameter("dangerNo");
    String hiRiskCode     = request.getParameter("riskCode");
   String openerIndex  = request.getParameter("openerIndex");        //定位在主页面的行号
   String isNewDangerInfo = request.getParameter("NewDangerInfo");  //是否是新加的一个危险单位，是为1
   String editType     = request.getParameter("editType"); 
   String riskUnitFlag = request.getParameter("riskUnitFlag");
   String includeAccident = request.getParameter("includeAccident");
   WfLog wfLogDto = (WfLog)session.getAttribute("wfLogDto");
   int nodeNo = wfLogDto.getNodeNo();
   double FACLEVEL  =Double.parseDouble(SysConfig.getProperty("FACLEVEL"));
   String flag            = (String)request.getAttribute("flag");
   String modifyflag = "";
   if(editType.equals("query")||(!(nodeNo>7)))
   {
     modifyflag = "0";
   }else{
   	 modifyflag = "1";
   }
   PrpTmain  prpTmainDto =(PrpTmain) request.getAttribute("prpTmainDto");
     String orRiskCode = prpTmainDto.getRiskCode();
 %>
<form name="DangerItemForm" >
	<!--  危险单位主险信息 -->
	<table border= "0" width="100%">
	<tr>
	     <td colspan="9">
	        <input type="hidden" name="DealType">
	        <input type="hidden" name="hiRiskCode" value="<%=hiRiskCode%>"> 
	        <input type="hidden" name="orRiskCode" value="<%=orRiskCode%>"> 
            <input type="hidden" name="hiDangerNo" value="<%=dangerNo%>"> 
            <input type="hidden" name="businessNo" value="<%=businessNo%>">
            <input type="hidden" name="openerIndex" value="<%=openerIndex%>">
            <input type="hidden" name="isNewDangerInfo" value="<%=isNewDangerInfo%>">
            <input type="hidden" name="classCode" value="<%=classCode%>">
            <input type="hidden" name="riskUnitFlag" value="<%=riskUnitFlag%>">
            <input type="hidden" name="hiBusinessType" description="<s:text name='undwrt.pages.undwrtDeal.certiType'/>" value="<%=businessType%>">  
            <input type="hidden" name="includeAccident"  value="<%=includeAccident%>">  
            <input type="hidden" name="lowRetentionValue" value="<s:property value="#request.lowRetentionValue" />"> 
            <input type="hidden" name="heiRetentionValue" value="<s:property value="#request.heiRetentionValue" />">  
         </td>
	 </tr>
	 <tr>
		<td>
      	<span style="display:none">
        <table border="0" class="common" width="100%" style="display:none" id="DangerUnit_Data" cellspacing="1" cellpadding="0">
			<tbody><tr><td>
			<table class="common" style="width:99%" cellspacing="1" cellpadding="0">
			    <tr class="listtitle">
					<td width='5%'>
				  		<%--序号 --%>
				  		<s:text name="undwrt.ShowDangerItem.serialNo"/>
				  	</td>
				  	<td width='10%'>
						<%--险种 --%>
					  	<s:text name="undwrt.ShowDangerItem.risk"/>
					</td>
					<td width='10%'>
					  	<%--险种名称 --%>
					  	<s:text name="undwrt.ShowDangerItem.riskName"/>
					</td>
					<td width='10%'>
					  	<%--币别 --%>
					  	<s:text name="undwrt.ShowDangerItem.currency"/>
					</td>
					<td width='10%'>
					  	<%--保额 --%>
					  	<s:text name="undwrt.ShowDangerItem.protectAmount"/>
					</td>
					  	<td width='10%'>
					  	<%--保费 --%>
						<s:text name="undwrt.ShowDangerItem.protectFee"/>
					</td>
					<td width='10%'>
					  	<%--占比 --%>
					  	<s:text name="undwrt.ShowDangerItem.occupyScale"/>%
					</td>	
				</tr>
         		<tr class="common">
	  				<td rowspan="3">
		  				<input class="free" name="dangerNo" readonly description="<s:text name='undwrt.pages.undwrtDeal.serialNo'/>" >
		  				<input type="hidden" description="<s:text name='undwrt.commonPolicyScale'/>" name ="baseRate">
	  				</td>
	  				<td>
	  	 				<input class="free" name="riskCode" description="<s:text name='riskName'/>">
				  	</td>
		  	 	  	<td>
		  	  		 	<input class="free" name="riskName" description="<s:text name='undwrt.pages.undwrtDeal.riskcName'/>">
		  	 	  	</td>
			 	  	<td>
			 		  	<input class="free" name="currency" description="currency">
			 	  	</td>
			 	  	<td>
			  		  	<input class="free"  readonly name="amount" description="<s:text name='undwrt.pages.undwrtDeal.coverage'/>" 
			  		  		onblur="checkNumber(this)">
			  	 	</td>
			      	<td>
			  		   	<input class="free"  readonly name="premium" description="<s:text name='undwrt.pages.undwrtDeal.premium'/>" 
			  		   		onblur="checkNumber(this)">
			  	 	</td>
			  	 	<td>
			  		   	<input class="free"  readonly name="dangerShare" description="<s:text name='undwrt.pages.undwrtDeal.dutyCycle'/>" 
			  		   		onblur="checkNumber(this)">
			  	 	</td>
				</tr>
				
				<tr class=listtitle>
					<td width='10%'>
						<%--描述 --%>
						<s:text name="undwrt.ShowDangerItem.describe"/>
					</td>
				  	<td width='25%'>
				  		<%--地址 --%>
				 		<s:text name="undwrt.ShowDangerItem.address"/>
				  	</td>
				 	<td>
				 		<%--风险等级 --%>
				 		<s:text name="undwrt.ShowDangerItem.riskLevel"/>
				 	</td>
				 	<td>
				 	  	<%--风险名称 --%>
			 	  		<s:text name="undwrt.ShowDangerItem.riskName"/>
					</td>
				    <% if (classCode.equals("27") && !hiRiskCode.equals("2729")&& includeAccident.equals("Y")) { %>
				   	<td>
					   	<%--意健险PML值 --%>
					   	<s:text name="undwrt.ShowDangerItem.accidentHealthPMLValue"/>
				   	</td> 
				   	<%} %>
				   	<% if ((classCode.equals("15")||hiRiskCode.equals("1107")||hiRiskCode.equals("1001")||hiRiskCode.equals("2201")||hiRiskCode.equals("0109")||hiRiskCode.equals("0907")||hiRiskCode.equals("2729")) && includeAccident.equals("Y")) { %>
				   	<td>
				   		AOA/PML
				   	</td> 
				   	<%} %>
				   	<td>
						<%--自留额 --%>
					   	<s:text name="undwrt.ShowDangerItem.autoRemainAmount"/>
				   </td>
				   <td  colspan='2'>
					   <%--除外责任 --%>
					   <s:text name="undwrt.ShowDangerItem.exDuty"/>
				   </td>
			       <td>
				       <%--进合约 --%>
				       <s:text name="undwrt.ShowDangerItem.intoContract"/>
			       </td>	
				</tr>
				<tr class="common">
	 				<td>
	 					<input class="free" name="dangerDesc" description="<s:text name='undwrt.pages.undwrtDeal.describe'/>">
	 				</td>
	    			<% if (classCode.equals("27") && includeAccident.equals("Y")) {%>
	  				<td>
	    			<%}else if (hiRiskCode.equals("1516") && includeAccident.equals("Y")){%>
					<td>
					<%}else{%>
	    			<td>
	    			<%}%>
	    	 			<input class="free" name="dangerAddress" description="<s:text name='undwrt.page.addressDescribe'/>">
	    			</td>
			  		<td>
			  			<input class="free"   readonly name="riskClass" >
			  			<input class="free"   readonly name="riskClassDesc" >
			  			<input class="codestyle2"  name="riskLevel" description="<s:text name='undwrt.pages.undwrtDeal.riskGrade'/>" 
					   		ONDBLCLICK="openDangerRiskInfo(this)">
					</td>
			  		<td>
			  			<input class="free" readonly name="riskLevelDesc" description="<s:text name='undwrt.pages.undwrtDeal.riskName'/>" >
			  		</td>
			  		<%if(classCode.equals("27") && includeAccident.equals("Y")) {%>
 			     	<td>
 			     		<input class="free"  name="speCurrency" description="<s:text name='undwrt.CommonDangerUnits.PMLCurrencyKind'/>"  value="">
			        	<input class="free"  name="speValue" description="<s:text name='undwrt.PMLAmount'/>" value="" onblur="checkSpeValue(this)">
			     	</td>
			   		<%}%>
			     	<%if((classCode.equals("15")||hiRiskCode.equals("1107")||hiRiskCode.equals("1001")||hiRiskCode.equals("0907")||hiRiskCode.equals("2201")||hiRiskCode.equals("0109"))  && includeAccident.equals("Y")) {%>
				</tr>
				<tr>
 			  		<td>
 			     		<input class="free"  name="speCurrency" description="<s:text name='undwrt.CommonDangerUnits.PMLCurrencyKind'/>"  value="">
			     		<input class="free"  name="speValue" description="<s:text name='undwrt.PMLAmount'/>" value="" onblur="checkSpeValue(this)">
			     	</td>
		
			   		<%}%>
					<%if (classCode.equals("12")) {%>       
			     	<td>
						<input class="free"   style="width:20px" readonly name="retCurrency" description="<s:text name='undwrt.CommonDangerUnits.autoAmountCurrency'/>">                                                                                                         
						<input class="free"    style="width:80px" name="retentionValue" description="<s:text name='undwrt.pages.undwrtDeal.retentionMoney'/>">
						<input  type='hidden'    name="retentionValueHidden" description="<s:text name='undwrt.hideAutoRemainAmount'/>">
				 	</td>                                                                      
					<%}else {%>
			  		<td>
			  			<input class="free"   style="width:20px" readonly name="retCurrency"> 
			  			<input class="free" readonly style="width:80px" name="retentionValue" description="<s:text name='undwrt.pages.undwrtDeal.retentionMoney'/>">
			 	    	<input  type='hidden'    name="retentionValueHidden" description="<s:text name='undwrt.hideAutoRemainAmount'/>">
			  		</td>
			  		<%}%>
			  		<td  colspan='2'>
			 	  		<input type="hidden" name="dangerItemKind" value="">
			      		<select name="itemKind" class=common  onchange="javascript:checkDangerItemFlag()">
			       			<s:if test="#request.dangerExItemKind2!=null">
			           			<s:iterator id="dangerExItemKind2" status="statu" value="#request.dangerExItemKind2">
		               				<option value="<s:property value="#dangerExItemKind2.itemCode" />">
		                 				<s:property value="#dangerExItemKind2.itemName" />
		                		 	</option>
		               			</s:iterator>
		           			</s:if>
		          		</select>
	  				</td>
	  				<td>
	  			  		<input type="checkbox" align="center" name="dangerItemFlag" description="<s:text name='undwrt.pages.undwrtDeal.flagLocal'/>" value="">
                  		<input type="hidden" name="hiDangerItemFlag">
	                  	<input type="hidden" name="hiDangerCoinsFlag" value="">
	                  	<input type="hidden" name="hiDangerShareHolderFlag" value="">
	                  	<input type="hidden" name="hiDangerBusinessFlag" value="">
	                  	<input type="hidden" name="hiDangerBusinessNature" value="">
	                  	<input type="hidden" name="hiDangerChannelType" value="">
	                  	<input type="hidden" name="hiDangerCartypeCode" value="">
	                  	<input type="hidden" name="hiDangerExchRateCNY" value="">
          			</td>  
		   		</tr>	
		   	</table>
		   </td></tr></tbody>	
		</table>
  	  	</span>
   		<span  id="spanDangerUnit"  cellspacing="1" cellpadding="0">
	  	<table class="common" width="100%" cellpadding="5" cellspacing="1" align="center" id="DangerUnit" border="0" >
	   		<thead>
				<tr class=listtitle>
					<td>
						<%--風險評估子訊息 --%>
						<s:text name="undwrt.ShowDangerItem.riskAssessmentSonInfo"/>
					</td>
			  	</tr>
       		</thead>		 
	  		<tbody>
	  		<s:if test="#request.DangerDetail!=null">
	  		<s:iterator id="DangerDetail" status="statu" value="#request.DangerDetail"><tr><td>
        	<table class="common" style="width:100%" cellspacing="1" cellpadding="0">
         		<tr class="listtitle">
		       		<td width='5%'>
			       		<%--序号 --%>
			       		<s:text name="undwrt.ShowDangerItem.serialNo"/></td>
		       		<td width='10%'>
			       		<%--险种 --%>
			       		<s:text name="undwrt.ShowDangerItem.risk"/></td>
		       		<td width='10%'>
			       		<%--险种名称 --%>
			       		<s:text name="undwrt.ShowDangerItem.riskName"/></td>
		       		<td width='10%'>
			       		<%--描述 --%>
			       		<s:text name="undwrt.ShowDangerItem.describe"/></td>
		      		<td width='10%'>
			      		<%--地址 --%>
			      		<s:text name="undwrt.ShowDangerItem.address"/></td>
		       		<td width='8%'>
			       		<%--币别--%>
			       		<s:text name="undwrt.ShowDangerItem.currency"/></td>
		       		<td width='11%'>
			       		<%--保额 --%>
			       		<s:text name="undwrt.ShowDangerItem.protectAmount"/></td>
		        	<% if("F".equals(classCode) || "C1".equals(classCode)) { %>
		       		<td width='11%'>
			       		<%--保费--%> 
			       		<s:text name="undwrt.ShowDangerItem.protectFee"/></td>
		       		<%}%>
		       		<td width='10%' colspan="2">
			       		<%--保费占比 --%>
			       		<s:text name="undwrt.ShowDangerItem.occupyScale"/>%</td>	
         		</tr>
				<tr class=common>
					<td  rowspan ="3">
					  	<%--序号 --%>
					   	<input type="hidden" description="<s:text name='undwrt.commonPolicyScale'/>" name ="baseRate"
					      	value ="<s:property value="#DangerDetail.baseRate" />">
					   	<input class="free" readonly name="dangerNo" description="<s:text name='undwrt.pages.undwrtDeal.serialNo'/>" 
					      	value="<%=dangerNo%>">
					</td> 
					<td>
					 	<%--险种 --%>
						<input type="text" class="codestyle2" name="riskCode" 
							value ="<s:property value="#DangerDetail.riskCode" />"      
				      		ondblclick="code_CodeSelect(this,'RiskCode','0,1','Y','','');"
			          		onkeyup="code_CodeSelect(this,'RiskCode','0,1','Y','','');" 
			         		onblur="updateExItemKind(this);">
			       		<iframe name=CodeFrame src="/undwrt/common/QueryCodeInputOverview.jsp" 
			       			style='DISPLAY:none;Z-INDEX:100;POSITION:absolute' 
			       			marginwidth='0' marginheight='0' hspace='0' vspace='0' frameborder='0' scrolling='auto'></iframe>	
					</td>
					<td>
					 	<%--险种名称 --%>
					 	<input type="text"  name="riskName"   value ="<s:property value="#DangerDetail.riskName" />">
					</td>
					<td>
						<%--描述 --%>
						<input class="free" name="dangerDesc" description="<s:text name='undwrt.ShowDangerItem.riskAccessDescribe'/>" 
							value="<s:property value="#DangerDetail.dangerDesc" />" 
							<%=editType.equals("query")?"readonly":""%>>
					</td>
					<td>
						<%--地址 --%>
			     		<input class="free" name="dangerAddress" description="<s:text name='undwrt.ShowDangerItem.address'/>" 
			     			value="<s:property value="#DangerDetail.addressName" />"
							<%=editType.equals("query")?"readonly":""%>>
					</td>
					<td>
						<%--币别 --%>
						<input class="free" readonly name="currency" description="<s:text name='undwrt.ShowDangerItem.currency'/>" 
							value="<s:property value="#DangerDetail.currency" />">
					</td>
					<!--  拆分危险单位，对除1外的其它危险单位的处理。 -->
					<% if(dangerNo.equals("1")){ %>
					<td>
						<%--保额 --%>
						<input class="free" readonly name="amount"  
							description="<s:text name='undwrt.PolicyDangerUnits.protectAmount'/>" 
							value="<fmt:formatNumber value="${amount}" pattern="#,##0.00"/>" 
							onblur="checkNumber(this)">
					</td>
					<% if("F".equals(classCode) || "C1".equals(classCode)) { %>
					<td>
						<%--保费 --%>
						<input class="free"  readonly name="premium" readonly 
							description="<s:text name='undwrt.PolicyDangerUnits.protectFee'/>" 
							value="<fmt:formatNumber value="${premium}" pattern="#,##0.00"/>" 
							onblur="checkNumber(this)">
					</td>
					<% } %>
			       	<td>
			       		<%--占比% --%>
			      		<input class="free" readonly name="dangerShare" 
			      			description="<s:text name='undwrt.EndorseDangerUnits.occupyScale'/>" 
			      			value="<s:property value="#DangerDetail.dangerShare" />" 
			      			onblur="checkNumber(this)">
			       	</td>
					<% }else{ %>
				  	<td>
				 		<input class="free" readonly name="amount" value="0" 
				 			description="<s:text name='undwrt.PolicyDangerUnits.protectAmount'/>" 
				 			onblur="checkNumber(this);">
				  	</td>
				  	<td>
				 		<input class="free" readonly name="premium" readonly value="0" 
				 			description="<s:text name='undwrt.PolicyDangerUnits.protectFee'/>"
				 			onblur="checkNumber(this)">
				  	</td>
			      	<td>
			     		<input class="free" readonly name="dangerShare" value="0" 
			     			description="<s:text name='undwrt.EndorseDangerUnits.occupyScale'/>" 
			     			onblur="checkNumber(this)">
			      	</td>
			      	<%}%>  
				</tr>
				
		    	<tr class=listtitle>
		    		<% if("F".equals(classCode) || "C1".equals(classCode)) { %>
            		<td width='500px'>
            			<%--风险等级 --%>
            			<s:text name="undwrt.ShowDangerItem.riskLevel"/></td>
            		<td width='500px'>
            			<s:text name="undwrt.pages.undwrtDeal.riskGradeName"/></td>
            		<td width= '500px'>
            			<%--幣別 --%>
            			<s:text name="undwrt.ShowDangerItem.currency"/></td>
            		<td width='500px'>
            			<%--自留额 --%>
            			<s:text name="undwrt.ShowDangerItem.autoRemainAmount"/></td>
            		<td colspan='3' width = '50%'>
			 			<%--除外责任/申报业务 --%>
			 			<s:text name="undwrt.ShowDangerItem.exDutyApplyBusiness"/></td>
            		<% } else { %>
			 		<td colspan='2' width = '50%'>
			 			<%--除外责任/申报业务 --%>
			 			<s:text name="undwrt.ShowDangerItem.exDutyApplyBusiness"/></td>
			 		<% } %>
			 		<td width = '5%'>
				 		<%--进合约 --%>
				 		<s:text name="undwrt.ShowDangerItem.intoContract"/></td>	
				</tr>
				<tr  class=common>
					<% if("F".equals(classCode) || "C1".equals(classCode)) { %>
					<td >
						<%--風險類別 --%>
		             	<input type="hidden" name="riskClass" 
		             		description="<s:text name='undwrt.pages.undwrtDeal.riskSort'/>" 
		             		value="<s:property value="#ItemKind.riskClass" />">
		             	<%--風險類別描述 --%>
					 	<input type="hidden" name="riskClassDesc" 
					 		description="<s:text name='undwrt.pages.undwrtDeal.riskKindDescribe'/>" 
					 		value="<s:property value="#ItemKind.riskClassDesc" />">
					 	<%--風險等級 --%>
					 	<input class="codestyle2" name="riskLevel" 
					 		description="<s:text name='undwrt.pages.undwrtDeal.riskGrade'/>" 
				  			value="<s:property value="#DangerDetail.riskLevel" />" 
			    			ondblclick="openDangerRiskInfo(this,1)"></td>
					<td >
						<%--风险名称 --%>
						<input class="free" readonly name="riskLevelDesc" 
							description="<s:text name='undwrt.pages.undwrtDeal.riskGradeName'/>" 
							value="<s:property value="#DangerDetail.riskLevelDesc" />">
					</td>
					<td>
						<%--币别 --%>
						<input class="free" readonly name="retCurrency" <%=(modifyflag.equals("0"))?"readonly":""%>
							description="<s:text name='undwrt.CommonDangerUnits.autoAmountCurrency'/>"  
							value="<s:property value="#DangerDetail.currency" />">
					</td>
					<td >
						<%--自留额 --%>
						<input  class="free" name="retentionValue" 
							description="<s:text name='undwrt.pages.undwrtDeal.retentionMoney'/>" <%=(modifyflag.equals("0"))?"readonly":""%>
							value="<fmt:formatNumber value="${retentionValue}" pattern="#,##0.00"/>" 
							onblur="compareRetentionValue();">
						<input class="free" type ='hidden' name="retentionValueHidden" 
							description="<s:text name='undwrt.hideAutoRemainAmount'/>" 
							value="<fmt:formatNumber value="${retentionValue}" pattern="#,##0.00"/>">
					</td>
					<% } %>
					<td colspan='3' >
						<%--除外责任/申报业务 --%>
			  			<s:select id="mySelect" list="#request.dangerExItemKind" name="itemKind" listKey="itemCode" listValue="itemName"/>
          			</td> 
					<td>
						<%--进合约【复选框】 --%>
			  			<input type="checkbox" name="dangerItemFlag" description="<s:text name='undwrt.flagLocation'/>"
			 	 			<s:if test='#DangerDetail.flag=="10"'>checked</s:if>
			  				<s:if test='#DangerDetail.flag=="11"'>checked</s:if>
			      			value="<s:property value="#DangerDetail.flag" />">
					  	<input type="hidden" name="hiDangerItemFlag" value="00">
					  	<input type="hidden" name="hiDangerCoinsFlag" value="<s:property value="#DangerDetail.coinsFlag" />">
					  	<input type="hidden" name="hiDangerShareHolderFlag" value="<s:property value="#DangerDetail.shareHolderFlag" />">
					  	<input type="hidden" name="hiDangerBusinessFlag" value="<s:property value="#DangerDetail.businessFlag" />">
					  	<input type="hidden" name="hiDangerBusinessNature" value="<s:property value="#DangerDetail.businessNature" />">
					  	<input type="hidden" name="hiDangerChannelType" value="<s:property value="#DangerDetail.channelType" />">
					  	<input type="hidden" name="hiDangerCartypeCode" value="<s:property value="#DangerDetail.cartypeCode" />">
					  	<input type="hidden" name="hiDangerExchRateCNY" value="<s:property value="#DangerDetail.exchRateCNY" />">
			  		</td>
			 	</tr>
		 		<tr>
		   			<td colspan='10' align='center'>
		   	 			<%--风险评估信息【按钮】 --%>
		   	 			<Input name="butViewRiskForm" class="longbutton" type="button" alt="<s:text name='undwrt.pages.undwrtDeal.assessRiskMessages'/>" 
		   	 				value="<s:text name='undwrt.pages.undwrtDeal.assessRiskMessages'/>" 
		   	 				onclick="viewDangerRiskInfo();"/>
		   			</td>
		  		</tr> 
		 	</table></td></tr>
         	</s:iterator>        
        	</s:if>
	  		</tbody>
	  	</table>
		</span>
		</td>
	</tr>

	<!-- ------------------------------------原始标的信息------------------------------------ -->
	<tr><td>
		<span style="display:none">
       	<table class="common" style="display:none" id="PrpItemInfo_Data" cellspacing="1" cellpadding="0">
         	<tr class=common>
           		<td>
			     	<input type="hidden" readonly  name="checkDanger"  value=""> 
			     	<input type="hidden" name="dangerFlag" value="0">
             		<input class="formtitle1" readonly  name="itemKindNo" value="" >
           		</td>
           		<td>
            		<input class="formtitle1"  name="ItemKindName" readonly value="" >
            		<input type=hidden name="ItemKindCode" value="" >
            	</td>
            	<td>
            		<input class="formtitle1" readonly name="ItemCode" value="" >
            	</td>
            	<td>
            		<input class="formtitle1" readonly name="ItemDetailName" value="" >
          		</td>
            	<td>
           			<input class="formtitle1" readonly name="ItemPost"  value="" >
            	</td>
            	<td>
            		<input class="formtitle1" readonly name="ItemAddressName" value="" >
            		<input type="hidden" name="sameRiskNo" value="" >
            	</td>
            	<td>
           		 	<input class="formtitle1" readonly name="ItemCurrency" style="width:22px" value="" >
            	</td>
	            <td>
	            	<input class="free"  name="ItemAmount" value="0.00">
	            </td>
	            <td>
	            	<input class="free"  name="ItemPremium" value="0.00">
	            	<input type="hidden" name="itemKindFlag" value="" >	
	            	<input type="hidden" name="currency2" value="">
	            	<input type="hidden" name="exchangeRate" value="">
	            	<input type="hidden" name="tolAmount" value="">
	            	<input type="hidden" name="tolPremium" value="">
	         	</td>
	         	<td>
		    		<input type="checkbox" readonly  name="ItemcalculateFlag"  value="">  
		        	<input type="hidden"   name="hiItemcalculateFlag"  value="">  
	         	</td>
	          	<td  align="center">
					<div>
				   		<input type=button class=smallbutton name="ItembuttonDelete" onclick="deleteRow_new(this,'PrpItemInfo');calItemNumber();" value="-" style="cursor: hand">
				 	</div>
			  	</td>
	      	</tr> 
		</table>
      	</span>
   
	   	<span id="SpanPrpItemInfo"  cellspacing="1" cellpadding="0">
	  	<table  class="common" cellpadding="5" cellspacing="1" align="center" id="PrpItemInfo">
	  		<thead>
          		<tr class=listtitle>
            		<td colspan="17" >
            			<%--原始标的信息 --%>
            			<s:text name="undwrt.ShowDangerItem.originalObjectMessages"/>
            		</td>
          		</tr>
          		<tr class=listtitle>         	
		            <td colspan='2' width='500px'>
			      		<%--标的序号 --%>
			            <s:text name="undwrt.ShowDangerItem.objectSerialNo"/></td>
            		<td width='500px'>
            			<s:text name="undwrt.ShowDangerItem.kind"/></td>
		        	<td width='500px'>
			            <%--标的项目 --%>
			            <s:text name="undwrt.ShowDangerItem.objectProject"/></td>
		            <td width='500px'>
		            	<%--标的名称 --%>
		            	<s:text name="undwrt.ShowDangerItem.objectName"/></td>
		            <td width='500px'>
		            	<%--邮编 --%>
		            	<s:text name="undwrt.ShowDangerItem.postcode"/></td>
		            <td width='500px'>
		            	<%--标的地址 --%>
		            	<s:text name="undwrt.ShowDangerItem.objectAddress"/></td>
		            <td width='500px'>
		            	<%--币别 --%>
		            	<s:text name="undwrt.ShowDangerItem.currency"/></td>
		            <td width='500px'>
		            	<%--保额 --%>
		            	<s:text name="undwrt.ShowDangerItem.protectAmount"/></td>
		            <% if("F".equals(classCode) || "C1".equals(classCode)) { %>
		            <td width='500px'>
		            	<%--保费 --%>
		            	<s:text name="undwrt.ShowDangerItem.protectFee"/></td>
		            <% } %>
		            <td width='200px'>
		            	<%--计算保额 --%>
		            	<s:text name="undwrt.ShowDangerItem.countProtectAmount"/></td>
            		<% if("A".equals(classCode) || "B".equals(classCode)){ %>
            		<%--是否临分 --%>
             		<td width='10%' >
             			<%--是否臨分 --%>
             			<s:text name="undwrt.ShowDangerItem.isFacultative"/></td>
             		<td width='10%' >
             			<%--風險等級 --%>
             			<s:text name="undwrt.ShowDangerItem.riskLevel"/></td>
			 		<td width='10%' >
			 			<%--險種名稱 --%>
			 			<s:text name="undwrt.ShowDangerItem.riskName"/></td>
	             	<td width = '10%' >
	             		<s:text name="undwrt.ShowDangerItem.currency"/></td>
	             	<td width = '20%' >
	             		<s:text name="undwrt.ShowDangerItem.autoRemainAmount"/></td>
					<% } %>
          		</tr>
          	</thead>
          	<tbody>
          	<s:if test="#request.ItemKind!=null">
          		<s:iterator id="ItemKind" status="statu" value="#request.ItemKind">
          		<tr class=common>
            		<td>
            			<%--复选框 --%>
              			<input type="checkbox" name="checkDanger" value="<s:property value="#ItemKind.dangerNo" />" 
              				<s:if test="#ItemKind.dangerNo!=0">checked</s:if>
             				onclick = "<%=riskUnitFlag.equals("1")?"checkItemKind(this);":"" %>calDangerAmountPremium()">
	      				<input type="hidden" name ="dangerFlag">
            		</td>
            		<td>
            			<%--标的序号 --%>
            			<input class="formtitle1" readonly  name="itemKindNo" 
            				value="<s:property value="#ItemKind.itemKindNo" />" >
		            </td>
		            <td>
		            	<%--险种 --%>
		            	<input class="formtitle1"  name="ItemKindName" readonly value="<s:property value="#ItemKind.kindName" />" >
		            	<input type=hidden name="ItemKindCode" value="<s:property value="#ItemKind.kindCode" />" >
		            </td>
            		<td>
            			<%--标的项目 --%>
            			<input class="formtitle1" readonly name="ItemCode" value="<s:property value="#ItemKind.itemCode" />" >
            		</td>
            		<td>
            			<%--标的名称 --%>
            			<input class="formtitle1" readonly name="ItemDetailName" value="<s:property value="#ItemKind.itemDetailName" />" >
            		</td>
            		<td>
            			<%--邮编区号 --%>
            			<input class="formtitle1" readonly name="ItemPost"  value="<s:property value="#ItemKind.postCode" />" >
		            </td>
		            <td>
		            	<%--标的地址 --%>
		           		<input class="formtitle1" readonly name="ItemAddressName" value="<s:property value="#ItemKind.addressName" />" >
		           		<input type="hidden" name="sameRiskNo" value="<s:property value="#ItemKind.sameRiskNo" />">
		            </td>
		            <td >
		            	<%--币别 --%>
		            	<input  class="formtitle1" readonly name="ItemCurrency"  value="<s:property value="#ItemKind.currency" />" >
		            </td>
		            <td>
            			<%--保额 --%>
	           	 		<input class="free"  name="ItemAmount" 
	            	  		value="<fmt:formatNumber value="${amount}" pattern="#,##0.00"/>"  
	                		onchange="calItemNumber()" <%=editType.equals("query")||riskUnitFlag.equals("0")?"readonly":""%>>
            		</td>
            		<% if("F".equals(classCode) || "C1".equals(classCode)) { %>
             		<td>
	            		<%--保费 --%>
	            		<input  class="free"  name="ItemPremium" value="<fmt:formatNumber value="${premium}" pattern="#,##0.00"/>"
	            		 	onchange="calItemNumber()"
	               			<%=editType.equals("query")||riskUnitFlag.equals("0")?"readonly":""%>>
	                	<input type="hidden" name="exchangeRate" value="<s:property value="#ItemKind.exchangeRate" />">
	            		<input type="hidden" name="currency2" value="<s:property value="#ItemKind.currency2" />">
	            		<input type="hidden" name="tolAmount" value="<s:property value="#ItemKind.tolAmount" />">
	           			<input type="hidden" name="tolPremium" value="<s:property value="#ItemKind.tolPremium" />">
            		</td>
            		<% } %>
             		<td>
             			<%--计算保额 --%>
             			<input type="checkbox" name="ItemcalculateFlag" disabled value="<s:property value="#ItemKind.calculateFlag" />"
             				<s:if test='#ItemKind.calculateFlag=="Y"'>checked</s:if> >
            	 		<input type="hidden"   name="hiItemcalculateFlag"  value="<s:property value="#ItemKind.calculateFlag" />">     
             		</td>
             		<%--针对车险增加是否 临分考量标识，选中为1，不选为0，默认不选中20131211--%>
              		<td style="display:none">
	               	<s:if test='#ItemKind.isFacultative==null || #ItemKind.isFacultative=="0"'>
	               		<input type="checkbox"  name="facultativeFlag" value="<s:property value="#ItemKind.isFacultative" />" onclick="ChangeValue(DangerItemForm,<s:property value='#statu.index'/>)">
	               		<input type="hidden"  name="isFacultative" value="0" />
	               	</s:if>
	               	<s:elseif test='#ItemKind.isFacultative=="1"'>
	               		<input type="checkbox"  name="facultativeFlag" value="<s:property value="#ItemKind.isFacultative" />" checked onclick="ChangeValue(DangerItemForm,<s:property value='#statu.index'/>)">
	               		<input type="hidden"  name="isFacultative" value="1" >
	               	</s:elseif>
             		</td>
             
             		<% if("A".equals(classCode) || "B".equals(classCode)){ %>
             		<td >
             			<%--风险等级 --%>
		             	<input type="hidden" name="riskClass" description="<s:text name='undwrt.pages.undwrtDeal.riskSort'/>" value="<s:property value="#ItemKind.riskClass" />">
					 	<input type="hidden" name="riskClassDesc" description="<s:text name='undwrt.pages.undwrtDeal.riskKindDescribe'/>" value="<s:property value="#ItemKind.riskClassDesc" />">
					 	<input class="codestyle2" name="riskLevel" description="<s:text name='undwrt.pages.undwrtDeal.riskGrade'/>" 
				  			value="<s:property value="#ItemKind.riskLevel" />" 
			    			ondblclick="openDangerRiskInfo(this,<s:property value="#ItemKind.itemKindNo" />)"></td>
			 
					<td >
						<%--风险名称 --%>
						<input class="free"   readonly name="riskLevelDesc" description="<s:text name='風險名稱'/>" value="<s:property value="#ItemKind.riskLevelDesc" />">
					</td>
					<td >
						<%--币别 --%>
						<input class="free" readonly name="retCurrency" <%=(modifyflag.equals("0"))?"readonly":""%>
							description="<s:text name='undwrt.CommonDangerUnits.autoAmountCurrency'/>"  value="<s:property value="#DangerDetail.currency" />">
					</td>
					<td >
						<%--自留额 --%>
						<input  class="free" name="retentionValue" description="<s:text name='undwrt.pages.undwrtDeal.retentionMoney'/>" <%=(modifyflag.equals("0"))?"readonly":""%>
							value="<fmt:formatNumber value="${retentionValue}" pattern="#,##0.00"/>" onblur="compareRetentionValue();">
						<input class="free" type ='hidden'name="retentionValueHidden" description="<s:text name='undwrt.hideAutoRemainAmount'/>"
							value="<fmt:formatNumber value="${retentionValue}" pattern="#,##0.00"/>">
					</td>
					<% } %>
          		</tr>
        		</s:iterator>
        	</s:if>
     		</tbody>
		</table>
        </span>
        </td>
	</tr>
	</table>
 	<table border="0" width="100%">
   		<tr>
   			<%if (!editType.equals("query")){ %>
   		 	<td class=button colspan=2 width=33%>
   		 		<%--保存 --%>
            	<Input name="butSaveForm" class="button" type="button" alt="<s:text name='undwrt.save'/>" value="<s:text name='undwrt.save'/>" onclick="saveDangerItemTask();">
          	</td>
    		<td class=button colspan=2 width=33%>
    			<%--关闭 --%>
            	<Input name="butSaveForm" class="button" type="button" alt="<s:text name='undwrt.close'/>" value="<s:text name='undwrt.close'/>" onclick="tipBeforeClose();">
         	</td>
          	<td class=button width=34%>
          		<%--重置 --%>
            	<Input name="buttonReset" class="button" type="reset" alt="<s:text name='prompt.reset'/>"  value="<s:text name='prompt.reset'/>"  >
          	</td>
    
  			<%} else {%>
   			<td class=button>
   				<%--关闭 --%>
            	<Input name="butSaveForm" class="button" type="button" alt="<s:text name='undwrt.close'/>" value="<s:text name='undwrt.close'/>" onclick="window.close();">
   			</td>
  			<% }%>
 		</tr>
	</table>
</form>
<script type="text/javascript">
	var fm = DangerItemForm;
</script>
</body>
</html>