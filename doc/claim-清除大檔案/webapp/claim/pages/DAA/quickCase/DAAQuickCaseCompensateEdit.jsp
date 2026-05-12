
  <jsp:include page="/behaviors/MpcStyle.jsp" />

  
    <input type="hidden" name="org.apache.struts.taglib.html.TOKEN" value="<%= session.getAttribute("org.apache.struts.action.TOKEN") %>">
    
    <DIV id="buttonLayer" style="position:absolute;top:2px;right:0px;z-index:1;">
    			<table>
    				<tr>
    					<td>
    			<%-- 8.保存通用按钮 --%>
<input type="hidden" class="bigbutton"  name="ManyCar" value="<s:text name="button.manyCalculations.value"/>" title="<s:text name="button.manyCalculations.value"/>" onclick="showManyCar()"><!-- 多车互碰理赔计算 --> 
            	        </td>
     </tr>
     </table>
     </DIV>       
            
        
        <%-- 3.赔付标的信息 --%>
					      <DIV style="width:100%;height:515px;background-color:#F7F7F7;overflow:scroll;">
					        <%-- 免赔条件的设置   
                            <%@include file="/DAA/quickCase/DAAQuickCaseCompensateDeductCondEdit.jsp"%>
					      	--%><%-- 3.赔付标的信息 --%>
				            <%@include file="/DAA/quickCase/DAAQuickCaseCompensateLlossEdit.jsp"%>
				            <%-- 5.不计免赔率信息 --%>
				            <%@include file="/DAA/quickCase/DAAQuickCaseCompensateExceptDeductibleRateEdit.jsp"%>
					        <%-- 6.赔款费用 --%>
				            <%@include file="/DAA/quickCase/DAAQuickCaseCompensateChargeEdit.jsp"%>
				            <%-- 支付帳户信息 --%>
                       		<%@include file="/common/compensate/EditPrpdpaymentaccountPage.jsp"%>
				            <%-- 7.赔款合计信息 --%>
				            <%@include file="/DAA/quickCase/DAAQuickCaseCompensateMainTailEdit.jsp" %>
					        <%-- 8.理算报告 --%>
					        <%@include file="/DAA/quickCase/DAAQuickCaseCompensateTextEdit.jsp"%>
					        <%@include file="/DAA/quickCase/DAAQuickCaseCompelCompensateTextEdit.jsp"%>
					      </DIV>
					<%--<mpc:page ID="tabMain" TABTITLE="危險單位訊息" TABTEXT="危險單位訊息">
					    <CENTER>
					      <DIV style="width:100%;height:515px;background-color:#F7F7F7;overflow:scroll;">
					      	 5.指定危险单位信息 
					        <%@include file="/common/claim/ClaimRiskUnit.jsp"%>
					      </DIV>
				    --%>
	<%--<%
      String strChiefFlag = "0";
      if(request.getAttribute("chiefFlag") != null){
      strChiefFlag = (String)request.getAttribute("chiefFlag");
     if("1".equals(strChiefFlag)){
    %>
     <%@include file="/common/compensate/CompensateCoinsEditFrame.jsp"%>
    <%
     }
     }
    %>
    <input type="hidden" name="chiefflag" value="<%=strChiefFlag%>">
--%>