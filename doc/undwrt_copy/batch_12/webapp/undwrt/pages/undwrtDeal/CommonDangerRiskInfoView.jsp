<%--
***************************************************************************
* Description: 核保风险等级评估
* Author     : LongYin
* CreateDate:  2005-6-16 21:30
* UpdateLog：  Name       Date            Reason/Contents
****************************************************************************
--%>

<%@ page language="java" %>
<%@ page import="java.util.*"%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<% 
   String dangerNo   = request.getParameter("dangerNo");   
   String businessNo = request.getParameter("businessNo"); 
   String riskCode   = request.getParameter("riskCode"); 
   String businessType = request.getParameter("businessType");
   String fieldName = request.getParameter("FieldName");
   String classCode = request.getParameter("classCode"); 
  // String riskClassDesc = request.getAttribute("riskClassDesc");
 %>
 <html>
  <head>
    <title><s:text name="undwrt.pages.undwrtDeal.CommonDangerRiskInfoView"/></title>
    <meta http-equiv=Content-Type content="text/html; charset=gb2312">
    <link rel="stylesheet" type="text/css" href="/undwrt/css/Standard.css">
    <!-- 公用函数 -->
    <!-- 本页函数 -->
    <script src="/undwrt/common/js/CommonTaskDeal.js"></script>
 </head>
 <!-- 滚动条样式定义 -->
<%@ include file="CommonStyle.html"%>
   	
   <body>
	<form name="fm" method="post">
	  <table class="common" cellpadding="5" cellspacing="1" align="center" border="0">
	    <tr class=listtitle>
	      <td colspan=5><s:text name="undwrt.pages.undwrtDeal.LevelRiskAssessment"/>
	       <input type="hidden" name="dangerNo"     value="<%=dangerNo%>">
	       <input type="hidden" name="businessNo"   value="<%=businessNo%>">
	       <input type="hidden" name="riskCode"     value="<%=riskCode%>">
	       <input type="hidden" name="businessType" value="<%=businessType%>">  
	      </td>
	    </tr>
	    
	    <tr class=common >	        
	      <td><s:text name="undwrt.pages.undwrtDeal.riskSort"/></td>
	         <td colspan=2>
	         
	         <input class="common" readonly name="riskClassDesc" value="<s:property value="riskClassDesc" />"> 
	         <!-- 
	          <select name="riskKind" class=common style="width:200px"> 
	           <logic:notEmpty  name="PrpDangerRiskFirst"  >
                  <option value="<bean:write name="PrpDangerRiskFirst" property="riskClass"/>">
                     <bean:write name="PrpDangerRiskFirst" property="riskClassDesc" />
                  </option>
                </logic:notEmpty>
	         </select>
	          -->
	        </td>
	        <td> </td>        
	   </tr> 
	   <%if(classCode.equals("09")||classCode.equals("10")||classCode.equals("02")||classCode.equals("27")){%>
	   <table class=two>
	     <tr class=listtitle>
	       <td colspan=5><s:text name="undwrt.pages.undwrtDeal.retentionMessages"/>
	    </tr>
	    <tr class=common >
          <td width="5%"  align="center"></td>
          <td width="10%" align="center"><s:text name="undwrt.pages.undwrtDeal.riskGrade"/></td>
          <td width="40%" align="center"><s:text name="undwrt.pages.undwrtDeal.riskGradeDescribe"/></td>
          <td width="20%" align="center"><s:text name="undwrt.pages.undwrtDeal.retentionCurrency"/></td>
          <td width="25%" align="center"><s:text name="undwrt.pages.undwrtDeal.maxRetention"/></td>
        </tr>
        <s:if test="#request.PrpDangerUnit!=null">
        <s:iterator id="PrpDangerUnit" status="statu" value="#request.PrpDangerUnit">
        <tr class=common >
        <td width="5%"  align="center"></td>
         <td width="10%" align="center"><input class="common" name="riskLevel" readonly   style="width:100px"  value="<s:property value="#PrpDangerUnit.riskLevel" />"></td>
         <td width="40%" align="center"><input class="common" name="riskLevelDesc" readonly value="<s:property value="#PrpDangerUnit.riskLevelDesc" />"> </td>
         <!--modify by liuhaiqi 20070105 begin 最大自留额的币种为自留额币种,原程序为业务的支付币种-->
         <td width="20%" align="center"><input class="common" name="currency" readonly  style="width:40px" value="<s:property value="#PrpDangerUnit.retCurrency" />"> </td>
         <!--modify by liuhaiqi 20070105 end 最大自留额的币种为自留额币种,原程序为业务的支付币种-->
         <td width="25%" align="center"><input class="common" name="retentionValue"  readonly style="width:100px" value="<fmt:formatNumber value="${retentionValue}" pattern="0.00"/>"/></td>        
        </tr> 
        </s:iterator>
        </s:if>
	   <%}else{ %> 			  
       <tr class=common style="display: none">
        <td  width=25%></td>
        <td  width=20%>Excellent</td>
        <td  width=20%>Good</td>
        <td  width=20%>Marginal</td>
        <td  width=15%>Poor</td>
      </tr>
       <tr class=common style="display: none">       
        <td></td>
        <td >X ≥ 70</td>
        <td >70 > X ≥ 55</td>
        <td >55 > X ≥ 40</td>
        <td >X < 40</td>       
      </tr>
      <s:if test="#request.PrpDangerRiskAll!=null">
      <s:iterator id="PrpDangerRiskAll" status="statu" value="#request.PrpDangerRiskAll">
		 <tr class=common style="display: none">
		   <td >
		   <s:property value="#PrpDangerRiskAll.itemName" />
		   <input type="hidden" name="itemName" value="<s:property value="#PrpDangerRiskAll.itemName" />">
		   <input type="hidden" name="itemCode" value="<s:property value="#PrpDangerRiskAll.itemCode" />">
		   <input type="hidden" name="riskClassHidden" value="<s:property value="#PrpDangerRiskAll.riskClass" />">
		   <input type="hidden" name="riskClassDescHidden" value="<s:property value="#PrpDangerRiskAll.riskClassDesc" />">
		   </td> 
		   <td colspan=4 align="center"><input class="common" name="itemValue"  value="<s:property value="#PrpDangerRiskAll.itemValue" />"> </td>
             <input type="hidden" name="hiItemValue"> 
           </td>         
		</tr>	
       </s:iterator>
       </s:if>
       <table class=two>
       <s:if test="#request.PrpDangerUnit">
       <s:iterator id="PrpDangerUnit" status="statu" value="#request.PrpDangerUnit">
       <tr class=listtitle>
	       <td colspan=5><s:text name="undwrt.pages.undwrtDeal.levelAssessment"/>
	   </tr>               
       <tr class=common >        
         <td width="25%" align="center"><s:text name="undwrt.pages.undwrtDeal.riskGrade"/></td>
         <td width="30%" align="center"><s:text name="undwrt.pages.undwrtDeal.riskGradeDescribe"/></td>
         <td width="20%" align="center"><s:text name="undwrt.pages.undwrtDeal.retentionCurrency"/></td>
         <td width="25%" align="center"><s:text name="undwrt.pages.undwrtDeal.maxRetention"/></td>
      </tr>   
      <tr class=common >      
         <td width="25%" align="center"><input class="common" name="riskLevel" readonly   style="width:100px"  value="<s:property value="#PrpDangerUnit.riskLevel" />"></td>
         <td width="30%" align="center"><input class="common" name="riskLevelDesc" readonly value="<s:property value="#PrpDangerUnit.riskLevelDesc" />"> </td>
         <!--modify by liuhaiqi 20070105 begin 最大自留额的币种为自留额币种,原程序为业务的支付币种-->
         <td width="20%" align="center"><input class="common" name="currency" readonly  style="width:40px" value="<s:property value="#PrpDangerUnit.retCurrency" />"> </td>
         <!--modify by liuhaiqi 20070105 end 最大自留额的币种为自留额币种,原程序为业务的支付币种-->
         <td width="25%" align="center"><input class="common" name="retentionValue"  readonly style="width:100px" value="<fmt:formatNumber value="${retentionValue}" pattern="0.00"/>"></td>        
      </tr>  
      </s:iterator>                             
       </s:if>  
     </table>
      <%}%>

    </table>
   
      <table class=two>
        <tr>       
         <td align="center"><input type="button" class=button value="<s:text name='undwrt.close'/>" onclick="window.close();"></td>
      </tr>
    
      </table>
    </form>
   <body>
 </html>

