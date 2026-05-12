<%--
****************************************************************************
* DESC       ：调度查勘内容
* AUTHOR     ：
* CREATEDATE ：2004-08-04
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="com.sinosoft.claim.dto.domain.SwfLogDto" %>    
<%@ page import="com.sinosoft.claim.dto.domain.*" %>
<%@ page import = "java.util.Calendar"%>
<%@ page import="com.sinosoft.claim.dto.custom.UserDto" %>
<%@ page import = "com.sinosoft.sysframework.common.datatype.DateTime"%>

<%
  //得到本周周一与周日的日期
  //Date date = new Date();
  //String strMonday = ""; //date.getMondayOFWeek();
  //String strSunday = ""; //date.getSundayOFWeek();
  
  String strSunday =DateTime.current().toString();
  String strMonday = new DateTime(DateTime.current().toString(),DateTime.YEAR_TO_DAY).addDay(-7).toString() ;
  UserDto user   = (UserDto)request.getSession().getAttribute("user"); 
%>
<script>
function submitForm() {
	fm.searchFlag.value = "true";
	fm.pageNo.value = "1"; //查询後页面设为1
	fm.submit(); //提交
}

function document.onkeydown() {
	if (event.keyCode == 13) {
		document.getElementById("button").click();
		return false;
	}
}
</script>
<html:html locale="true">

<head>
    <app:css />
    <STYLE>BODY {
                 SCROLLBAR-FACE-COLOR:#EFFAFF;
                 SCROLLBAR-HIGHLIGHT-COLOR:#4D9AC4;
                 SCROLLBAR-SHADOW-COLOR:#4D9AC4;
                 SCROLLBAR-3DLIGHT-COLOR:#EFFAFF;
                 SCROLLBAR-ARROW-COLOR:#EFFAFF;
                 SCROLLBAR-TRACK-COLOR:#EFFAFF;
                 SCROLLBAR-DARKSHADOW-COLOR:#EFFAFF;
                }
                </STYLE>
  <title>
    <s:text name="title.scheduleBeforeEdit.surveyTaskList" /><%--查勘任務处理清单 --%>
  </title>
  <script src="/claim/common/js/showpage.js"> </script>

  </script>
  <link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
  <html:base/>
</head>

<body  onLoad="initPage();document.onkeydown();">
<form name="fm" action="/claim/scheduleCheckQuery.do"  method="post" onSubmit="return validateForm(this);">
<!--add by zhangqiming  20070913 start-->
    <table  border="0" align="center" cellpadding="5" cellspacing="1"  class="common">
    <tr>  <td colspan=4 class="formtitle"><s:text name="title.checkBeforeEdit.titleName" /></td></tr>
      <tr>
        <td class='title' ><s:text name="prpLregist.registNo" />:</td><%--报案号 --%>
        <td class='input' >
          <select class=tag name="RegistNoSign"  >
            <option value="=">=</option>
            <option value="=*">=*</option>
          </select> <input type=text name="registNo" class="input" style='width:70%'>        </td>
        <td class='title' style=""><s:text name="certainLoss.prpLscheduleMainWF.prpLscheduleMainWFOperatorCode" /></td><%--调度员: --%>
        <td class='input'>
        
        <input type=text name="handlerCode" class="codecode"  style="width:100px" title="分案員" value=""
                 ondblclick="code_CodeSelect(this, 'handerCode','0,1','Y');"
                 onkeyup= "code_CodeSelect(this, 'handerCode','0,1','Y');"      >
            <input type=text name="handlerName" readonly class="codecode"  style="width:30%" title="分案員" value=""
                 ondblclick="code_CodeSelect(this, 'handerCode','-1,0','Y','N');"
                 onkeyup= "code_CodeSelect(this, 'handerCode','-1,0','Y','N');">             </td>
      </tr>

      <tr>
      <td class='title' ><s:text name="manage.startTime" />:</td><%--开始时间 --%>
       <td class='input' >
        <input name="startDate" class="input" value=<%=strMonday%> >        </td>
      <td class='title' ><s:text name="manage.endTime" />: </td><%--结束时间 --%>
       <td class='input' >
        <input  name="endDate" class="input" value = <%=strSunday%>>        </td>
      </tr>
      <tr>
        <td class='title' ><s:text name="prpLcheck.checkUser" />:</td><%--查勘人 --%>
        <td class='input'>
          <input type=text name="NhandlerCode" class="codecode"   title="查勘人" value=""
            ondblclick="code_CodeSelect(this, 'handerCode','0,1','Y');"
            onkeyup= "code_CodeSelect(this, 'handerCode','0,1','Y');"
            style="width:30%">
            <input type=text name="NhandlerName" class="codename" title="查勘人" value="" style="width:55%"
            ondblclick="code_CodeSelect(this, 'handerCode','-1,0','Y','N');"
            onkeyup= "code_CodeSelect(this, 'handerCode','-1,0','Y','N');"
            >        </td>
        <td class='title' ><s:text name="certainLoss.prpLscheduleMainWF.caseState" /></td><%--案件状态: --%>
        <td class='input' ><input type=checkbox name="checkFlag0" >
          <s:text name="schedule.noMentioned" /><%--未查勘 --%>
          <input type=checkbox name="checkFlag2" >
          <s:text name="schedule.noMentioneding" /><%--正在查勘 --%>
          <input type=checkbox name="checkFlag4" >
          <s:text name="schedule.Mentioned" /> </td><%--已查勘 --%>
      </tr>
      
      <tr> <td class='button'  colspan=4>
          <input type=button class='button' id="button" value="<s:text name='button.query.value' />" onClick="submitForm();">
          <input type="hidden" name="nodeType2" value="<%= request.getParameter("nodeType") %>">
          <input type="hidden" name="editType2" value="QUERYCHECK">
          <input name="searchFlag" type="hidden" id="searchFlag"></td> 
      </tr>
    </table>
    <table cellpadding="5" cellspacing="1"  class="common" >
 <tr>

            <td colspan="8" class="formtitle"><bean:write name="swfLogDto" property="nodeName"/><s:text name="schedule.tasksDeal" /></td><%--任務处理清单 --%>

      </tr>
  
     <tr>

        <td class="centertitle"  style="width:6%"><s:text name="regist.prpLregist.serialNo" /></td><%--序号 --%>
        <td class="centertitle"  style="width:10%"><s:text name="schedule.rrocesseStatus" /></td><%--处理状态 --%>
        <td class="centertitle" style="width:18%"><s:text name="schedule.reportRegistrateNo" /></td><%--报案登记号 --%>
        <td class="centertitle" style="width:22%"><s:text name="certainLoss.prpLscheduleMainWF.attemperDate" /></td><%--调度时间 --%>
        <td class="centertitle" style="width:10%"><s:text name="certainLoss.prpLscheduleMainWF.prpLscheduleMainWFOperatorCode" /></td><%--调度员 --%>
        <td class="centertitle" style="width:10%"><s:text name="certainLoss.prpLscheduleMainWF.Handler" /></td><%--查勘人员 --%>
        <td class="centertitle" style="width:24%"><s:text name="schedule.schedulObjectName" /></td><%--调度对象名称--%>
        
   <%--     <td class="centertitle" style="width:20%">处理时间</td>--%>
     </tr>

  <%int index=0;%>
  <% String dealHref="";      //处理功能，按钮上的联接%>
  <%SwfLogDto swfLogDto = null;
  SwfLogDto swfLogDto1 = (SwfLogDto)request.getAttribute("swfLogDto");
  String flowStr="";
  %>  
  
     <c:if test="${swfLog.swfLogList != null}">
     <c:forEach var="prpLcheckTaskList" items="${swfLog.swfLogList}">
<%
          if(index %2== 0)
               out.print("<tr class=listodd>");
          else
               out.print("<tr class=listeven>");
%>

        <td align="center">
        <%=index+1%>
        </td>

 <%
   //取得该行的DTO的数据 判断节点类型和状态，根据不同的状态，实现不同的按扭内容
      swfLogDto = (SwfLogDto) ((ArrayList)swfLogDto1.getSwfLogList()).get(index); 
       flowStr="&swfLogFlowID="+swfLogDto.getFlowID()+"&swfLogLogNo="+swfLogDto.getLogNo()+"&status="+swfLogDto.getNodeStatus()
              +"&riskCode="+swfLogDto.getRiskCode()+"&editType=SHOW"
              +"&nodeType="+swfLogDto.getNodeType()
              +"&businessNo="+swfLogDto.getBusinessNo()
              +"&policyNo="+swfLogDto.getPolicyNo()
              +"&modelNo="+swfLogDto.getModelNo()
              +"&nodeNo="+swfLogDto.getNodeNo();
 
 %>     
        <td >
        <logic:equal name="prpLcheckTaskList" property="nodeStatus" value='0' > <s:text name="common.status.newSchedule" /></logic:equal><%--新调度 --%>
        <logic:equal name="prpLcheckTaskList" property="nodeStatus" value='2' > <s:text name="common.status.intreating" /></logic:equal><%--正处理 --%>
        <logic:equal name="prpLcheckTaskList" property="nodeStatus" value='4' > <s:text name="common.status.submited" /></logic:equal><%--已提交 --%>
        </td>
        <% if (swfLogDto.getNodeStatus().equals("0")){
        dealHref ="javascript:alert('该任務目前还没有可以查看的"+swfLogDto.getNodeName() +"信息！');";
        }else{
            if (swfLogDto.getNodeType().equals("check")){
               dealHref ="/claim/checkFinishQueryList.do?prpLcheckCheckNo="+swfLogDto.getKeyIn()
                                    +"&lossItemCode="+swfLogDto.getLossItemCode()
                                    +"&lossItemName="+swfLogDto.getLossItemName()
                                    +"&insureCarFlag="+swfLogDto.getInsureCarFlag()
                                    +"&commiFlag=0"//+swfLogDto.getCommiFlag()    //add commiFlag by liyanjie 205-12-17
                                    +flowStr;
            }else{
               dealHref="/claim/certainLossFinishQueryList.do?prpLverifyLossRegistNo="+swfLogDto.getKeyIn()
                                      +"&lossTypeFlag="+swfLogDto.getTypeFlag()
                                      +"&insureCarFlag="+swfLogDto.getInsureCarFlag()
                                      +"&lossItemCode="+swfLogDto.getLossItemCode()
                                      +"&lossItemName="+swfLogDto.getLossItemName()
                                      +"&commiFlag=0"//+swfLogDto.getCommiFlag()    //add commiFlag by liyanjie 205-12-17
                                      +flowStr;
            
            }
        }
        %>
          <td ><a href="<%=dealHref%>"> <bean:write name="prpLcheckTaskList" property="keyIn"/>
        </a></td>
        <td ><bean:write name="prpLcheckTaskList" property="flowInTime"/></td>         <%// 是否被调度使用   %>
        <td ><bean:write name="prpLcheckTaskList" property="beforeHandlerName"/></td>            <%// 预约查勘(定损)   %>
        <td ><bean:write name="prpLcheckTaskList" property="handlerName"/></td>
        <td ><bean:write name="prpLcheckTaskList" property="lossItemName"/></td>
        <%--<td ><bean:write name="prpLcheckTaskList" property="handleTime"/></td>   --%>
   
       </tr>
<%        index++;%>
      </c:forEach>
	</c:if>
      <tr class="listtail">
	      <td colspan="15">
	       	          <table width="100%" class="common" align="center" cellpadding="0" cellspacing="0">
              <tr>  
                  <bean:define id="pageview" name="swfLogDto" property="turnPageDto"/>  
<%
 // SwfLogDto swfLogDto = (SwfLogDto)request.getAttribute("swfLogDto"); 
  int curPage = swfLogDto1.getTurnPageDto().getPageNo(); 
%>                  
                  <%@include file="/common/pub/TurnOverPage.jsp" %>   
              </tr> 
          </table>
	      </td>
      </tr>
    </table>
  </tr>

  <table class="common" cellpadding="4" cellspacing="20">
    <tr>
    </tr>
    <tr>

    </tr>
  <table>
<input type="hidden" name="editType" value="<bean:write name="swfLogDto" property="editType"/>"> 
<input type="hidden" name="nodeType" value="<bean:write name="swfLogDto" property="nodeType"/>"> 

  </table>
 </form>
 </body>


</html:html>