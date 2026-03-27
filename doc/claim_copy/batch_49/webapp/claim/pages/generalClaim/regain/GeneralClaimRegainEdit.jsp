<%--
****************************************************************************
* DESC       ：通赔接收页面
* AUTHOR     ：中科软
* CREATEDATE ： 2013-03-19
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>

<html xmlns:mpc >
<head>
    <!--对title处理-->
    <title>
    	<s:text name="general.claimRegain"/>
    </title><%--通赔收回处理 --%>
  <%-- 页面样式  --%>
  <link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
  <%-- 标签页样式 --%>
  <jsp:include page="/behaviors/MpcStyle.jsp" />
  
  <script language="Javascript" src="/claim/pages/generalClaim/js/GeneralClaimEdit.js"></script>
</head>
 <body>
 <form name="fm" action=""  method="post" >
 	<div style="width:100%;height:320px;background-color:#ffffff;overflow:auto;">
         <table class="common" style="width:100%" cellspacing="1" cellpadding="5">
         	<thead>
                <tr>
                   <td class="subformtitle" colspan="7"><s:text name="excludeClaim.CaseInformation"/></td><%--案件信息 --%>
                </tr>
                <tr>
       	  		   <td class="centertitle" ><s:text name="prompt.queRegist.RegistNo"/></td><%--报案号 --%>
       	  		   <td class="centertitle" ><s:text name="prompt.queRegist.PolicyNo"/></td><%--保单号 --%>
       	  		   <td class="centertitle" ><s:text name="db.prpDdbs.riskCode"/></td><%--险种 --%>
       	  		   <td class="centertitle" ><s:text name="db.prpLregist.insuredName"/></td><%--被保险人 --%>
       	  		   <td class="centertitle" ><s:text name="regist.prpLregist.damageTime"/></td><%--出险时间 --%>
       	  		   <td class="centertitle" ><s:text name="regist.prpLregist.registTime"/></td><%--报案时间 --%>
       	  		   <td class="centertitle" ><s:text name="compensate.insuranceComCode"/></td><%--承保机构代码 --%>
                 </tr>  
           </thead>
           <tbody> 
              <tr> 
              	<td class="input" align="center" style="width:15%">
              	<input class='input' type='hidden' name='registNo' value="${prpLregist.registNo}">
                   ${prpLregist.registNo}
                </td>
                <td class="input" align="center" style="width:15%">
                   ${prpLregist.policyNo}
                </td>
                <td class="input" align="center" style="width:10%">
                   ${prpLregist.riskCode}
                </td>
                <td class="input" align="center" style="width:15%">
                   ${prpLregist.insuredName}
                </td>
                <td class="input" align="center" style="width:15%">
                   ${prpLregist.damageStartDate}
                </td>
                <td class="input" align="center" style="width:15%">
                   ${prpLregist.reportDate}
                </td>
                <td class="input" align="center" style="width:15%">
                   ${prpLregist.comCode}
                </td>
              </tr>
          </tbody>
  	   </table>
  	   <TABLE class="common"  cellpadding="3" cellspacing="1" >
  	   		<tr>
                   <td class="subformtitle" colspan="6"><s:text name="regist.prpLregist.registMain"/></td><%--基本信息 --%>
            </tr>
		  <TR>
		    <TD class='left'><s:text name="regist.prpLregist.damageTime"/>：</td><%--出险时间 --%>
		    <TD class='right'>
		    	${prpLregist.damageStartDate}
		    </TD>
		    <TD class='left'><s:text name="regist.prpLregist.damageCode"/>：</td><%--出险原因 --%>
		    <TD class='right'>
		    	${prpLregist.damageName}
		    </td>
		    <TD class='left'><s:text name="db.prpLregist.damageTypeCode"/>：</td><%--事故原因 --%>
		    <TD class='right'>
		    	${prpLregist.damageTypeName}
		    </td>
		  </TR>
		  <TR>
		    <TD class='left'><s:text name="db.prpLregist.linkerName"/>：</td><%--联系人 --%>
		    <TD class='right' >
		    	${prpLregist.linkerName}
		    </td>
		    <TD class='left'><s:text name="db.prpLregist.damageAddress"/>：</td><%--出险地点 --%>
		    <TD class='right' colspan="3">
		    	${prpLregist.damageAddress}
		    </td>
		  </TR>
	   </TABLE>
  	  <div style="width:100%;height:160px;background-color:#ffffff;overflow:auto;">
		<TABLE cellpadding="3" cellspacing="0"   class="common" id=GeneralClaimTaskTable>
			<thead>
				<tr>
      			 	<TD class=formtitle><s:text name="db.prpLregist.remark"/></td><%--备注 --%>
      			 </tr>
			</thead>
    		<tbody>
  				<TR align=center>
      				<TD>
      	 				<textarea readonly type="text" name="remark" style="width:400;height:130">${prplgeneralclaimtask.remark}</textarea>      	
     				</TD>
    			</TR>
    		</tbody>
  		</table>
  		</div>
 </div>
		<table class="common" style="width:100%" cellspacing="1" cellpadding="5">
         	<thead>
                <tr>
                   <td class="subformtitle" colspan="8"><s:text name="general.scheduling"/></td><%--任務调度 --%>
                </tr>
                <tr>
       	  		   <td class="centertitle" ><s:text name="db.prpLdriver.serialNo"/></td><%--序号 --%>
       	  		   <td class="centertitle" ><s:text name="general.nodeName"/></td><%--当前环节 --%>
       	  		   <td class="centertitle" ><s:text name="general.receiveComCode"/></td><%--处理机构代码 --%>
       	  		   <td class="centertitle" ><s:text name="general.receiveComcodeName"/></td><%--处理机构名称 --%>
       	  		   <td class="centertitle" ><s:text name="db.prpLprepay.operatorCode"/></td><%--操作员代码 --%>
       	  		   <td class="centertitle" ><s:text name="guarantee.operateName"/></td><%--操作员名称 --%>
       	  		   <td class="centertitle" ><s:text name="undwrt.TaskStatus"/></td><%--任務状态 --%>
       	  		   <td class="centertitle" ><s:text name="claim.intoTime"/></td><%--流入时间 --%>
                 </tr>  
           </thead>
           <c:forEach items="${requestScope.swflogDtoList}" var="swflogDto" varStatus="stat">
           <tbody> 
              <tr>
              	<td class="input" align="center" style="width:5%">
              	<input class='input' type='hidden' name='flowid' value="<c:out value='${swflogDto.flowID}'/>">
              	<input class='input' type='hidden' name='logno' value="<c:out value='${swflogDto.logNo}'/>">
                   <c:out value="${stat.count}"/>
                </td>
                <td class="input" align="center" style="width:5%">
                   <c:out value='${swflogDto.nodeName}'/>
                </td>
                <td class="input" align="center" style="width:15%">
                	<input  class='codecode' type='hidden' name='comcode<c:out value="${stat.count}"/>' value="" >
    			   <input  title='选择处理机构' class='codecode' type='text' name='comcode' value="" 
          				  ondblclick="code_CodeSelect(this,'queryProvince','-1,0,1','Y','','${prplgeneralclaimtask.givecomcode}');" 
    	  				  onchange="code_CodeSelect(this,'queryProvince','-1,0,1','Y','','${prplgeneralclaimtask.givecomcode}');">
    	  			<img src="/claim/images/bgMarkMustInput.jpg">
                </td>
                <td class="input" align="center" style="width:15%">
                <input  title='选择处理机构' class='codecode' type='text' name='comname' value="" 
          				  ondblclick="code_CodeSelect(this,'queryProvince','-2,-1,0','Y','','${prplgeneralclaimtask.givecomcode}');" 
    	  				  onchange="code_CodeSelect(this,'queryProvince','-2,-1,0','Y','','${prplgeneralclaimtask.givecomcode}');">
    	  			<img src="/claim/images/bgMarkMustInput.jpg">
                </td>
                <td class="input" align="center" style="width:15%">
    			 	 <input title='选择具有处理本任務权限的人员'  class='codecode' type='text' name='handlercode' value="" 
          				  ondblclick="return checkComCode(this,1,fm.comcode<c:out value="${stat.count}"/>.value,'<c:out value='${swflogDto.nodeName}'/>');" 
    	  				  onchange="return checkComCode(this,1,fm.comcode<c:out value="${stat.count}"/>.value,'<c:out value='${swflogDto.nodeName}'/>');">
    	  			<img src="/claim/images/bgMarkMustInput.jpg"> 
                </td>
                <td class="input" align="center" style="width:15%">
                    <input title='选择具有处理本任務权限的人员' class='codecode' type='text' name='handlername' value="" 
          				  ondblclick="return checkComCode(this,2,fm.comcode<c:out value="${stat.count}"/>.value,'<c:out value='${swflogDto.nodeName}'/>');"  
    	  				  onchange="return checkComCode(this,2,fm.comcode<c:out value="${stat.count}"/>.value,'<c:out value='${swflogDto.nodeName}'/>');">
    	  			<img src="/claim/images/bgMarkMustInput.jpg">
                </td>
                <td class="input" align="center" style="width:15%">
                   <input class='input' type='hidden' name='nodeStatus' value="<c:out value='${swflogDto.nodeStatus}'/>">
                   <c:if test="${swflogDto.nodeStatus==9}"><s:text name="general.toBeReceive"/></c:if><%--通赔待接收 --%>
                </td>
                <td class="input" align="center" style="width:20%">
                   <c:out value='${swflogDto.flowInTime}'/>
                </td>
             </tr>
          </tbody>
         </c:forEach>
  	   </table>
  	   <div style="width:100%;background-color:#ffffff;overflow:no;">
				<div style="width:100%;height:0px;background-color:#ffffff;overflow:no;">
				  <%@include file="/pages/generalClaim/give/GeneralClaimGiveSave.jsp" %>
				</div>
	   </div>
  </form>
  </body>
</html>


  
  



