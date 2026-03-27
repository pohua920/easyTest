<%--
****************************************************************************
* DESC       ：理算查询结果页面
* AUTHOR     ：理赔组
* CREATEDATE ：2013-02-01
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
								zhangshi		20130201			修改*操作符为=*将like '%%'改为左%右匹配查询
****************************************************************************/
--%>

<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="com.sinosoft.claim.dto.domain.PrpLcompensateDto" %>
<%@page import="com.sinosoft.sysframework.reference.*"%>
<%@ page import="com.sinosoft.claim.dto.custom.*" %>
<%@ page import="com.sinosoft.claim.dto.domain.*" %>
<%@ page import="com.sinosoft.sysframework.common.datatype.*" %>

<% 
  String riskType= request.getParameter("type");
  //System.out.println("----------------------riskType-----"+ riskType );
  if ((riskType == null)||riskType.equals("")){
   riskType = "notacci";
  }
%>
<html:html locale="true">
  <script language="javascript">
  <%--案件状态标志处理--%>
  function submitForm()
  {
    if((fm.InsuredNameSign.value=="="&&fm.InsuredName.value.length>0)
  	 ||(fm.LicenseNoSign.value=="="&&fm.LicenseNo.value.length>0)
  	 ||(fm.RegistNoSign.value=="="&&fm.RegistNo.value.length>0)
  	 ||(fm.PolicyNoSign.value=="="&&fm.PolicyNo.value.length>0)
  	 ||(fm.ClaimNoSign.value=="="&&fm.ClaimNo.value.length>0)
  	 ||(fm.CompensateNoSign.value=="="&&fm.CompensateNo.value.length>0)){
  	 //输入了一个条件，可以查
  	 }else if((fm.RegistNoSign.value=="=*"&&fm.RegistNo.value.length>8)
  	        ||(fm.PolicyNoSign.value=="=*"&&fm.PolicyNo.value.length>8)
  	        ||(fm.ClaimNoSign.value=="=*"&&fm.ClaimNo.value.length>8)
  	        ||(fm.CompensateNoSign.value=="=*"&&fm.CompensateNo.value.length>8)){
  	 	if("D"==getClassCodeType(fm.RegistNo.value.substr(1,2))
  	 	 ||"D"==getClassCodeType(fm.PolicyNo.value.substr(1,2))
  	 	 ||"D"==getClassCodeType(fm.ClaimNo.value.substr(1,2))
  	 	 ||"D"==getClassCodeType(fm.CompensateNo.value.substr(1,2))){
  	 		alert("车险必须精确查询！");
  	 		return false;
  	 	}else{
  	 		//非车险可以前9位模糊查询
  	 	}  		
  	}else{
  		alert("车险必须输入计算书号、立案号、报案号、保单号、车牌号、被保险人其中一项精确查询！\n 非车险可以用计算书号、立案号、报案号或者保单号的前9位进行模糊查询！");
  		return false;
  	}
    var ref1="";
    var ref2="";
    for(i=0;i<fm.status.length;i++){
      if(fm.status[i].checked==true){
        ref1 = ref1+fm.status[i].value+",";
      }
    }

    for(i=0;i<fm.UnderWriteFlag.length;i++){
      if(fm.UnderWriteFlag[i].checked==true){
        ref2 = ref2+fm.UnderWriteFlag[i].value+",";
      }
    }
	fm.searchFlag.value="true";
    fm.pageNo.value="1";//查询後页面设为1
    fm.caseFlag.value = ref1;
    fm.compensateFlag.value = ref2;
    fm.submit();//提交
  }
  //-->
  </script>
<head>
    <app:css />

  <title><s:text name="title.claimBeforeEdit.queryClaim"/></title>
<script src="/claim/common/js/showpage.js"> <
script>
  <link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
  <%@include file="/common/meta_js.jsp"%>
  <html:base/>
</head>

<body onload="initPage();" >
   <form name="fm" action="/claim/compensateQuery.do"  method="post" onSubmit="return validateForm(this);">

    <table  border="0" align="center" cellpadding="5" cellspacing="1"  class="common">
    <tr>
<% if (riskType.equals("acci")){  %>
				<td colspan=4 class="formtitle">
					<s:text name="compensate.queryCheckedInformation" />
				</td>
				<!-- 查询审核信息 -->
				<%} else {%>
				<td colspan=4 class="formtitle">
					<s:text name="title.compensate.queryAdjustInformation" />
				</td>
				<!-- 查询理算信息 -->
<% }  %>
   </tr>
      <tr>
				<td class='title'>
					<s:text name="db.prpLcfee.compensateNo" />
					：
				</td>
				<!-- 赔款计算书号 -->
        <td class='input'> 
          <select class=tag name="CompensateNoSign">
            <option value="=" <c:if test="${compensateNoSign=='='}"> selected</c:if>>=</option>
            <option value="=*"<c:if test="${compensateNoSign=='=*'}">selected</c:if>>=*</option>
					</select>
					<input type=text name="CompensateNo" value="<bean:write name='compensateNo'/>" class="query">
				</td>
				<td class='title'>
					<s:text name="check.claimNum" />
					：
				</td>
				<!-- 赔案号 -->
        <td class='input'>
          <select class=tag name="ClaimNoSign"> 
            <option value="=" <logic:equal name="claimNoSign" value="=">selected</logic:equal>>=</option>
            <option value="=*" <logic:equal name="claimNoSign" value="=*">selected</logic:equal>>=*</option>
        </select>
        <input type=text name="ClaimNo" value="<bean:write name='claimNo'/>" class="query"> 
        </td>
      </tr>
      <tr>
				<td class='title'>
					<s:text name="prompt.queRegist.PolicyNo" />
					：
				</td>
				<!-- 保单号 -->
        <td class='input'>
          <select class=tag name="PolicyNoSign"> 
            <option value="=" <logic:equal name="policyNoSign" value="=">selected</logic:equal>>=</option>
            <option value="=*" <logic:equal name="policyNoSign" value="=*">selected</logic:equal>>=*</option>
					</select>
					<input type=text name="PolicyNo" value="<bean:write name='policyNo'/>" class="query">
				</td>
				<td class='title'>
					<s:text name="db.prpLclaimStatus.operatedate" />
					：
				</td>
				<!-- 操作时间 -->
        <td class='input'>
          <select class=tag name="OperateDateSign">
	          <option value="=" <logic:equal name="operateDateSign" value="=">selected</logic:equal>>=&nbsp;</option>
	          <option value=">" <logic:equal name="operateDateSign" value=">">selected</logic:equal>>&gt;&nbsp;</option>
						<option
							value="<" <logic:equal name="operateDateSign" value="<">selected</logic:equal>>&lt;&nbsp;</option>
	          <option value=">=" <logic:equal name="operateDateSign" value=">=">selected</logic:equal>>&gt;=</option>
	          <option value="<=" <logic:equal name="operateDateSign" value="<=">selected</logic:equal>>&lt;=</option>
          </select> <input type=text name="OperateDate" value="<bean:write name='operateDate'/>" class="query" >
                   <img align="absmiddle" style='cursor: hand' src="/claim/images/bgcalendar.gif" onClick="TogglePopupCalendarWindow('document.fm.OperateDate', '<%=new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY)
						.getYear() - 15%>', '<%=new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY)
						.getYear() + 2%>')">        </td>
      </tr>
      <tr>
        <td class='title'><s:text name="db.prpLregist.licenseNo" />：</td>
        <td class='input'>
        <select class=tag name="LicenseNoSign"> 
            <option value="=" <logic:equal name="licenseNoSign" value="=">selected</logic:equal>>=</option>
            <option value="=*" <logic:equal name="licenseNoSign" value="=*">selected</logic:equal>>=*</option>
          </select> <input type=text name="LicenseNo" value="<bean:write name='licenseNo'/>"  class="query">        </td>
         <%--报案查询增加被保险人查询条件--%>
       <td class='title' ><s:text name="db.prpLCMain.insuredName" />:</td><!-- 被保险人名称 -->
        <td class='input' >
          <select class=tag name="InsuredNameSign" > 
            <option value="=" <logic:equal name="insuredNameSign" value="=">selected</logic:equal>>=</option>
            <option value="=*" <logic:equal name="insuredNameSign" value="=*">selected</logic:equal>>=*</option>
          </select> <input type=text name="InsuredName" value="<bean:write name='insuredName'/>"  class="query" >        </td>
      </tr>
      <tr>
        <td class='title'><s:text name="db.prpLclaimStatus.status" />：</td><!-- 案件状态 -->
        <% String status = (String)request.getAttribute("status");
				String underWriteFlag = (String) request
						.getAttribute("underWriteFlag");%>
        <td class='input' colspan=3>
        <input type="hidden" name="caseFlag" value="<bean:write name='status'/>">
        <input type="checkbox" name="status" value="1" <%if(status.indexOf("1")>=0){%>checked<%} %>><s:text name="common.status.untreated" /><!-- 未处理 -->
        <input type="checkbox" name="status" value="2"  <%if(status.indexOf("2")>=0){%>checked<%} %>><s:text name="common.status.intreating" /><!-- 正处理 -->
        <%--没有此种案件状态 2005-07-28<input type="checkbox" name="status" value="3">已处理--%>
        <input type="checkbox" name="status" value="4"  <%if(status.indexOf("4")>=0){%>checked<%} %>><s:text name="common.status.submited" /><!-- 已提交 -->
        <input type="checkbox" name="status" value="5"  <%if(status.indexOf("5")>=0){%>checked<%} %>><s:text name="common.status.revoked" /><!-- 已撤消 -->        </td>
     </tr>
     <tr>
        <td class='title'><s:text name="db.prpLprepay.underWriteFlag" />：</td><!-- 核赔标志 -->
        <td class='input' colspan=3>
          <input type="hidden" name="compensateFlag">
          <input type="checkbox" name="UnderWriteFlag" value="0" <%if(underWriteFlag.indexOf("0")>=0){%>checked<%} %>><s:text name="compensate.initValue" /><!-- 初始值 -->
          <input type="checkbox" name="UnderWriteFlag" value="1" <%if(underWriteFlag.indexOf("1")>=0){%>checked<%} %>><s:text name="compensate.pass" /><!-- 通过 -->
          <input type="checkbox" name="UnderWriteFlag" value="2" <%if(underWriteFlag.indexOf("2")>=0){%>checked<%} %>><s:text name="compensate.notPass" /><!-- 不通过 -->
          <input type="checkbox" name="UnderWriteFlag" value="3" <%if(underWriteFlag.indexOf("3")>=0){%>checked<%} %>><s:text name="compensate.withoutHePei" /><!-- 无需核赔 -->
          <input type="checkbox" name="UnderWriteFlag" value="9" <%if(underWriteFlag.indexOf("9")>=0){%>checked<%} %>><s:text name="compensate.stayHePei" />       </td><!-- 待核赔  -->
      </tr>
			<tr>
				<td class='title' ><s:text name="prompt.queRegist.RegistNo" />：</td><!-- 报案号 -->
      	<td class='input' >
      		<select class=tag name="RegistNoSign" >
            <option value="=" <logic:equal name="registNoSign" value="=">selected</logic:equal>>=</option>
            <option value="=*" <logic:equal name="registNoSign" value="=*">selected</logic:equal>>=*</option>
          </select> <input type=text name="RegistNo" value="<bean:write name='registNo'/>"  class="query" >
      	</td>	
			</tr>
			<tr>
    <td class="title" style="color:red" colspan="4">
     <s:text name="prompt.schedule.query1" /><br><!-- "="符号，必须精确查询。 -->
							<s:text name="prompt.schedule.query2" />
							<br>
							<!-- "=*"符号，前匹配後模糊的查询。 -->
							<s:text name="compensate.queryResult1" />
							<br>
							<!-- 车险必须输入计算书号、立案号、报案号、保单号、车牌号、被保险人其中一项精确查询！ -->
							<s:text name="compensate.queryResult2" />
							<!-- 非车险可以用计算书号、立案号、报案号或者保单号的前9位进行模糊查询！ -->
    </td>
  	</tr>
  	<tr>
       <td class='button'  colspan="4">
          <input type=button class='button' value="<s:text name='button.query.value' />" onClick="submitForm();">
          <input type="hidden" name="nodeType" value="<%= request.getParameter("nodeType") %>">
          <input type="hidden" name="editType2" value="SHOW">
          <input type="hidden" name="riskType" value="<%=riskType %>">
					<input name="searchFlag" type="hidden" id="searchFlag">
				</td>
    </tr>
    </table>
  <table  class="common" cellpadding="5" cellspacing="1" >
			<tr>
				<td colspan=5 class="formtitle">
					<s:text name="title.compensate.queryAdjustInformation" />
				</td>
			</tr>
			<!-- 查询理算信息 -->
     <tr>
				<td class="centertitle">
					<s:text name="db.prpLclaimStatus.status" />
				</td>
				<!-- 案件状态 -->
				<td class="centertitle">
					<s:text name="db.prpLcfee.compensateNo" />
				</td>
				<!-- 赔款计算书号 -->
				<td class="centertitle">
					<s:text name="check.claimNum" />
				</td>
				<!-- 赔案号 -->
				<td class="centertitle">
					<s:text name="prompt.queRegist.PolicyNo" />
				</td>
				<!-- 保单号 -->
				<td class="centertitle">
					<s:text name="compensate.sumPayMoney" />
				</td>
				<!-- 总赔付金额(折TWD) -->
     </tr>
     <%int index=0;%>
     <logic:notEmpty  name="prpLcompensateDto"  property="compensateList">
     <logic:iterate id="prpLcompensate1"  name="prpLcompensateDto"  property="compensateList">
					<%if (index % 2 == 0)
               out.print("<tr class=listodd>");
          else
					out.print("<tr class=listeven>");%>
					<tr class=common>
						<td align="center">
        <logic:equal name="prpLcompensate1" property="status" value='1' >
								<s:text name="common.status.untreated" />
								<!-- 未处理 -->
        </logic:equal>
        <logic:equal name="prpLcompensate1" property="status" value='2' >
								<s:text name="common.status.intreating" />
								<!-- 正处理 -->
        </logic:equal>
        <logic:equal name="prpLcompensate1" property="status" value='3' >
								<s:text name="common.status.treated" />
								<!-- 已处理 -->
        </logic:equal>
        <logic:equal name="prpLcompensate1" property="status" value='4' >
								<s:text name="common.status.submited" />
								<!-- 已提交 -->
        </logic:equal>
        <logic:equal name="prpLcompensate1" property="status" value='5' >
								<s:text name="common.status.revoked" />
								<!-- 已撤消 -->
        </logic:equal>
      </td>
						<td align="center">
							<a
								href="/claim/compensateFinishQueryList.do?prpLcompensateCompensateNo=<bean:write name='prpLcompensate1' property='compensateNo'/>&editType=<bean:write name='prpLcompensateDto' property='editType'/>&riskCode=<bean:write name="prpLcompensate1" property="riskCode"/>">
								<bean:write name="prpLcompensate1" property="compensateNo" />
							</a>
						</td>
						<td align="center">
							<bean:write name="prpLcompensate1" property="claimNo" />
						</td>
						<td align="center">
							<bean:write name="prpLcompensate1" property="policyNo" />
						</td>
						<td align="center">
							<bean:write name="prpLcompensate1" property="sumPaid" />
						</td>
      </tr>
<%        index++;%>
      </logic:iterate>
      </logic:notEmpty>
   <tr class="listtail">
     <td colspan="5">
      <table width="100%" class="common" align="center" cellpadding="0" cellspacing="0">
              <tr>   
                  <bean:define id="pageview" name="prpLcompensateDto" property="turnPageDto"/>  
							<%PrpLcompensateDto prpLcompensateDto = (PrpLcompensateDto) request
						.getAttribute("prpLcompensateDto");
				int curPage = prpLcompensateDto.getTurnPageDto().getPageNo();%>
                  <%@include file="/common/pub/TurnOverPage.jsp" %>   
              </tr> 
          </table>
     </td>
   </tr>
    </table>
    </tr>
  </table>
<input type="hidden" name="editType" value="<%=request.getParameter("editType")%>">
</form>

</body>
</html:html>