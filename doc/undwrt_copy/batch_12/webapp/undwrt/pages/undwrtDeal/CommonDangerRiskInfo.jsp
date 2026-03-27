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
   String classCode = riskCode.substring(0,1);
   Collection dangerRiskInfo =(Collection)request.getAttribute("dangerRiskInfo");
 %>
 <html>
  <head>
    <title><s:text name="undwrt.pages.undwrtDeal.CommonDangerRiskInfo"/></title>
    <meta http-equiv=Content-Type content="text/html; charset=gb2312">
    <link rel="stylesheet" type="text/css" href="/undwrt/css/Standard.css">
    <!-- 公用函数 -->
    <!-- 本页函数 -->
    <script src="/undwrt/common/js/CommonTaskDeal.js"></script>
    <script>
    function chooseRiskClass()
    {
      fm.riskKindText.value = fm.riskKind.options[fm.riskKind.selectedIndex].text;
      fm.method ="post"; 
      fm.action="/undwrt/DangerRiskEvaluateFacade.do";
      fm.submit();    
    }
    function setRiskKindText()
    {
      fm.riskKindText.value = fm.riskKind.options[fm.riskKind.selectedIndex].text;
    }
    //modify begin 20060531 by lihua Bug修改：风险评估选自留额的问题
    function showValue()
    {
        <%if(classCode.equals("09")||classCode.equals("10")||classCode.equals("02")||classCode.equals("27")||classCode.equals("13")||riskCode.equals("0109")){  %>
        <%}else
          { if(dangerRiskInfo!=null && dangerRiskInfo.size()!=0)
         {%>
   
           if(fm.riskClass.length!=0)
             {
	           for(var i=0;i<fm.riskKind.length;i++){
	             if (fm.riskKind[i].value==fm.riskClass[1].value){
	        	    fm.riskKind[i].selected=true;
	         	 }
               }
        }
    <%
     }
    }%>
         for(var i=0;i<fm.riskKind.length;i++){
            var obj = fm.riskClass[1];
	        if (fm.riskKind[i].value==obj.value){
	        	fm.riskKind[i].selected=true;
	        }
        }
    
    
    }
  //modify end 20060531 by lihua Bug修改：风险评估选自留额的问题 
    
    </script>
 </head>
 <!-- 滚动条样式定义 -->
<%@ include file="CommonStyle.html"%>
   	
   <body   onload="showValue();setRiskKindText();">
  
	<form name="fm" method="post">
	  <table class="common" cellpadding="5" cellspacing="1" align="center" border="0">
	    <tr class=listtitle>
	       <td colspan=5><s:text name="undwrt.pages.undwrtDeal.CommonDangerRiskInfo"/>
	       <input type="hidden" name="dangerNo"     value="<%=dangerNo%>">
	       <input type="hidden" name="businessNo"   value="<%=businessNo%>">
	       <input type="hidden" name="riskCode"     value="<%=riskCode%>">
	       <input type="hidden" name="businessType" value="<%=businessType%>">
	       <input type="hidden" name="FieldName"    value="<%=fieldName%>">
	       <input type="hidden" name="itemName">
	       <input type="hidden" name="itemCode">
	       <input type="hidden" name="itemValue">
	       <input type="hidden" name="hiItemValue">
	       <input type="hidden" name="riskKindText">
	       <input type="hidden" name="chooseFlag">
	       
	       
	      <input type="hidden" name="chooseFlag1">
          <input type="hidden" name="riskLevel">
          <input type="hidden" name="riskLevelDesc">
          <input type="hidden" name="currency">
          <input type="hidden" name="retentionValue">       
          <input type="hidden" name="lowRetentionValue">
          <input type="hidden" name="riskClass">
          <input type="hidden" name="riskClassDesc">
         	
	       </td>
	    </tr>
	    <tr class=common >
	        <td><s:text name="undwrt.pages.undwrtDeal.riskSort"/></td>
	         <td colspan=4>
	          <select name="riskKind" class=common style="width:200px" onchange="chooseRiskClass();setRiskKindText()">          
               <s:if test="#request.riskKindInfo!=null">
               <s:iterator id="riskKindInfo" status="statu" value="#request.riskKindInfo">
                  <option value="<s:property value="#riskKindInfo.riskKindCode" />">
                          <s:property value="#riskKindInfo.riskKindName" />
                  </option>
                </s:iterator>
               </s:if>
	         </select>
	       </td>      
	       
	   </tr>  

     
       </table>
      
      <table class=two>
      <s:if test="#request.RetenValueSet!=null">
       <tr class=listtitle >
        <td width="5%" align="center"></td>
         <td width="25%" align="center"><s:text name="undwrt.pages.undwrtDeal.riskGrade"/></td>
         <td width="25%" align="center"><s:text name="undwrt.pages.undwrtDeal.riskGradeDescribe"/></td>
         <td width="20%" align="center"><s:text name="undwrt.pages.undwrtDeal.retentionCurrency"/></td>
         <td width="25%" align="center"><s:text name="undwrt.pages.undwrtDeal.maxRetention"/></td>
      </tr>
      <s:iterator id="RetenValueSet" status="statu" value="#request.RetenValueSet">  
      <tr class=common >
      
         <td width="5%" align="center"><input type="radio" name="chooseFlag1" value="<s:property value="#RetenValueSet.riskLevel" />"></td>
         <td width="25%" align="center"><input class="common" name="riskLevel" readonly   style="width:100px"  value="<s:property value="#RetenValueSet.riskLevel" />"></td>
         <td width="30%" align="center"><input class="common" name="riskLevelDesc" readonly value="<s:property value="#RetenValueSet.riskLevelDesc" />"> </td>
         <td width="20%" align="center"><input class="common" name="currency" readonly  style="width:40px" value="<s:property value="#RetenValueSet.currency" />"> </td>
         <td width="25%" align="center"><input class="common" name="retentionValue" readonly style="width:100px" value="<fmt:formatNumber value="${retentionValue}" pattern="#,##0.00"/>"></td>        
         <input type ="hidden" class="common" name="lowRetentionValue" readonly  style="width:100px"  value="<s:property value="#RetenValueSet.lowRetentionValue" />">
         <input type ="hidden" class="common" name="riskClass" readonly   style="width:100px"  value="<s:property value="#RetenValueSet.id.riskClass" />">
         <input type ="hidden" class="common" name="riskClassDesc" readonly   style="width:100px"  value="<s:property value="#RetenValueSet.riskClassDesc" />">
         	
      </tr>                
       </s:iterator>                
       </s:if>   
     </table>
     <table class=two>
        <tr> 
         <td  align="center"><input type="button" class=button value="<s:text name='prompt.ok'/>" 
         <%if(fieldName.equals("riskLevel")) {%>
           onclick = "return reRetenValue2(<%=request.getParameter("num")%>);"
         <%} else if(fieldName.equals("allEvaluate")){%>
           onclick = "return reAllRetenValue();" 
         <%}%>
         ></td> 
          
         <td align="center"><Input class="button" type="button" alt="<s:text name='prompt.back'/>" value="<s:text name='prompt.back'/>" onclick="window.close();"></td>
      </tr>
      </table>
    </form>
   <body>
   <script type="text/javascript">
   function riskriskEvaluate(){
     fm.method ="post";
     fm.action="/undwrt/DangerRiskEvaluateFacade.do";
     fm.submit();
   }
   </script>
 </html>

