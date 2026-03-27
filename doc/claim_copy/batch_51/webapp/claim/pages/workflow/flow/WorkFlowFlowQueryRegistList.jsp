<%--
****************************************************************************
* DESC       ：工作流查询报案信息结果页面
* AUTHOR     ：weishixin
* CREATEDATE ：2004-03-01
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
							zhangshi		20080512				修改模糊查询为右模糊查询
****************************************************************************/
--%>

<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app" %>
<%@ page import="com.sinosoft.claim.dto.custom.*" %>
<%@ page import="com.sinosoft.claim.dto.domain.*" %>
<%@ page import="java.util.*" %>

<html:html locale="true">
<head>
    <app:css />
<STYLE>
BODY {
                 SCROLLBAR-FACE-COLOR:#EFFAFF;
                 SCROLLBAR-HIGHLIGHT-COLOR:#4D9AC4;
                 SCROLLBAR-SHADOW-COLOR:#4D9AC4;
                 SCROLLBAR-3DLIGHT-COLOR:#EFFAFF;
                 SCROLLBAR-ARROW-COLOR:#EFFAFF;
                 SCROLLBAR-TRACK-COLOR:#EFFAFF;
                 SCROLLBAR-DARKSHADOW-COLOR:#EFFAFF;
                }
                </STYLE>  
<title><s:text name="title.workflow.oaFlowQueryResultShow" />
	<%--工作流流程查询结果显示 --%></title>
  <script> 
  		function mysubmit(){
  			fm.pageNo.value = "1";
  			fm.submit();
  			
  		}
  </script>
         
  <html:base/> 
</head>

  
  <script src="/claim/workflow/flow/js/WorkFlowFlowBeforeQuery.js"></script>
  <link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">


<body class="interface" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="initPage();">
<form name="fm" action="/claim/swfFlowQuery.do"  method="post" onsubmit="return validateForm(this);">
<%
WorkFlowQueryDto workFlowQueryDto = new WorkFlowQueryDto();
workFlowQueryDto = (WorkFlowQueryDto)request.getAttribute("workFlowQueryDto");
%>
<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1"  class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="title.sendUndwrtBeforeEdit.QueryingIformation" />
					<%--工作流查询信息 --%>
				</td>
			</tr>
      <tr>
				<td class='title'>
					<s:text name="db.prpLregist.registNo" />：
				</td>
        <td class='input' >
          <select name="RegistNoSign" class=tag >
            <option value="=">=</option>
					</select>
					<input type=text name="prpLregistRegistNo" class="query" value="<%=workFlowQueryDto.getRegistNo()%>">
        </td>
				<td class='title'>
					<s:text name="db.prpLregist.policyNo" />：
				</td>
        <td class='input' >
        <select name="PolicyNoSign" class=tag >
            <option value="=">=</option>
            <!--  
             option value="*">*</option
             -->
					</select>
					<input type=text name="prpLregistPolicyNo" class="query" value="<%=workFlowQueryDto.getPolicyNo()%>">
        </td>

      </tr>

      <!--tr>
        <td class='title' > <s:text name="db.prpLregist.riskCode" />：</td>
        <td class='input' >
        <select class=tag name="RiskCodeSign" >
          <option value="=">=</option>
          <option value="*">*</option>
         
        </select> <input type=text name="prpLregistRiskCode" class="query"  value="<%=workFlowQueryDto.getRiskCode()%>" >
        </td>
        
         <td class='title' >险种名称：</td>
        <td class='input' >
        <select class=tag name="RiskCodeNameSign" >
         <option value="=">=</option>	
         <option value="*">*</option>
         
         
          
        </select> <input type=text name="prpLregistRiskCodeName" class="query"  value="<%=workFlowQueryDto.getRiskCodeName()%>" >
        </td>
        
         </tr-->  
         
           <tr>
				<!--新增赔案（立案）号查询条件start -->
				<td class='title'>
					<s:text name="db.prpLprop.claimNo" />：
				</td>
       		 <td class='input' >
          		<select name="ClaimNoSign" class=tag >
            		<option value="=">=</option>
            <%--
            <option value="*">*</option>
            --%>
					</select>
					<input type=text name="prpLregistClaimNo" class="query" value="<%=workFlowQueryDto.getClaimNo()%>">
        	</td>
				<!--新增赔案（立案）号查询条件end -->
				<td class='title'>
					<s:text name="db.prpCmain.insuredName" />:
					<%--被保险人名称 --%>
				</td>
        <td class='input'>
          <select class=tag name="InsuredNameSign">
            <option value="=">=</option>
            <option value="=*">=*</option>
					</select>
					<input type=text name="prpLregistInsuredName" class="query" value="<%=workFlowQueryDto.getInsuredName()%>">
        </td>
      </tr>
         <tr>                
				<td class='title'>
					<s:text name="db.prpLregist.licenseNo" />：
				</td>
        <td class='input' >
        <select name="LicenseNoSign" class=tag >
            <option value="=">=</option>
            <option value="=*">=*</option>
					</select>
					<input type=text name="prpLregistLicenseNo" class="query" value="<%=workFlowQueryDto.getLicenseNo()%>">
        </td>
				</td>
				<td class='title' style="width: 10%">
					<s:text name="workflow.oaFlowState" />:
					<%--流程流转状态 --%>
        </td>
        <td class='input' style="width:25%">
					<input type="radio" name="caseType" value="0" <%=workFlowQueryDto.getCaseType().equals("0") ? "checked" : ""%>>
					<s:text name="workflow.normalFlow" />
					<%--正常流转 --%>
					<input type="radio" name="caseType" value="1" <%=workFlowQueryDto.getCaseType().equals("1") ? "checked" : ""%>>
					<s:text name="workflow.endFlow" />
					<%--结束流转 --%>
        </td>  
       </tr>
			<tr>
				<td class='button' colspan="4">
          <input type=button class='button' value="<s:text name='button.query.value' />" onClick="submitForm();">
        </td>
        </tr>
        <tr>
    <td class="title" style="color:red" colspan="4">
					<s:text name="prompt.schedule.query1" />
					<%--"="符号，必须精确查询。 --%>
					<br>
					<s:text name="workflow.query4" />
					<%--"=*"符号，前匹配後模糊的查询、被保险人名称根据前2位名称模糊查询。 --%>
    </td>
  </tr>
    </table>
    <input type="hidden" name="editType" value="WorkFlow">
    <input type="hidden" name="taskCodeC" value="lplc">
 
<input type="hidden" name="pageFlag">
  <table bgcolor="#2D8EE1" class="common" cellpadding="4" cellspacing="1" >
     <tr>
				<td class="centertitle">
					<s:text name="db.prpLregist.registNo" />
					<%--报案号 --%>
				</td>
				<td class="centertitle" style="width: 5%">
					<s:text name="prompt.queRegist.PolicyNo" />
					<%--保单号 --%>
				</td>
				<td class="centertitle">
					<s:text name="workflow.applicantName" />
					<%--被保人名称 --%>
				</td>
				<td class="centertitle">
					<s:text name="compensate.underly" />
					<%--标的 --%>
				</td>
				<td class="centertitle">
					<s:text name="workflow.dealPerson" />
					<%--处理人员 --%>
				</td>
				<td class="centertitle">
					<s:text name="db.prpLregist.reportDate" />
					<%--报案日期 --%>
				</td>
				<td class="centertitle" style="display: none">
					<s:text name="workflow.flowNumber" />
					<%--流程编号 --%>
				</td>
				<td class="centertitle">
					<s:text name="query.xianzhongName" />
					<%--险种名称 --%>
				</td>
				<td class="centertitle">
					<s:text name="workflow.registerState" />
					<%--注销状态 --%>
				</td>
     </tr>
			<%
				int index = 0;
			%>
     <logic:notEmpty  name="swfLogDto"  property="swfLogList"> 
     <logic:iterate id="swfLogDtoList1"  name="swfLogDto"  property="swfLogList">  
<%
          if(index %2== 0)
               out.print("<tr class=listodd>");
          else
               out.print("<tr class=listeven>");
%>
					<td align="center">
						<a href="/claim/swfFlowBeforeQuery.do?swfLogFlowID=<bean:write name='swfLogDtoList1' property='flowID'/>"><bean:write name="swfLogDtoList1" property="businessNo" /></a>
					</td>
        <td align="center">
        <logic:iterate id="relatePolicyList1"  name="swfLogDtoList1"  property="relatePolicyList">  
        <bean:write name="relatePolicyList1" property="policyNo"/>
        </logic:iterate>
         </td>  
					<td align="center">
						<bean:write name="swfLogDtoList1" property="insuredName" />
					</td>
					<td align="center">
						<bean:write name="swfLogDtoList1" property="lossItemName" />
					</td>
					<td align="center">
						<bean:write name="swfLogDtoList1" property="handlerName" />
					</td>
					<td align="center">
						<bean:write name="swfLogDtoList1" property="submitTime" />
					</td>
					<td align="center" style="display: none">
						<bean:write name="swfLogDtoList1" property="flowID" />
					</td>
					<td align="center">
						<bean:write name="swfLogDtoList1" property="riskCodeName" />
					</td>
					<td align="center">
						<bean:write name="swfLogDtoList1" property="otherFlag" />
					</td>
      </tr>
					<%
						index++;
					%>
      </logic:iterate>
      </logic:notEmpty>      
      <tr class="listtail">
        <td colspan="7">
          <table width="100%" class="common" align="center" cellpadding="0" cellspacing="0">
              <tr>  
                  <bean:define id="pageview" name="swfLogDto" property="turnPageDto"/>  
<%
  SwfLogDto swfLogDto = (SwfLogDto)request.getAttribute("swfLogDto"); 
  int curPage = swfLogDto.getTurnPageDto().getPageNo(); 
  String taskcode =(String)request.getParameter("taskCodeC"); 
%>                  
                  <%@include file="/common/pub/TurnOverPage.jsp" %>   
              </tr> 
          </table>
        </td> 
      </tr>      
  </table>
    </table>
    </tr>
  </table> 
  
    <input type="hidden" name="editType" value="EDIT">
    <input type="hidden" name="taskCodeC" value="<%=taskcode%>">

    

</form>
</body>
</html:html>
