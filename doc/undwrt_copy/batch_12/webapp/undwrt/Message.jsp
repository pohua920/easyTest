<%--
****************************************************************************
* DESC       ：操作成功提示页面
* AUTHOR     ：luyang
* CREATEDATE ：2004-12-27 11:56
* MODIFYLIST ：   id       Date            Reason/Contents
            zhangruifeng   2007-11-21      处理在风险评估成功后显示继续处理核保任务的bug
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<%@page import="java.text.DecimalFormat"%>
<!-- 滚动条样式定义 -->
<html locale="true">
<head>
<link href="/undwrt/css/Standard.css" rel="stylesheet" type="text/css">
</head>

<!--通用函数-->
<script src="/undwrt/common/js/Common.js"></script>
<script language="javascript">
  function goNextTask()
  {
    var handType = fm.handType.value;
    var editType = fm.editType.value;
    var url = "";
    url = "/undwrt/undwrtDeal/hebaoTaskDeal.do?actionType=queryContinue&HandType="+handType+"&EditType="+editType;      
    fm.action=url;
    fm.method="post";
    fm.submit();
  }
  
  function goNextAuthorizeTask()//授权js chengyisheng 2011-11-04
  {
    url = "/undwrt/undwrtDeal/hebaoTaskDeal.do?actionType=queryAuthorizeControl";      
    fm.action=url;
    fm.method="post";
    fm.submit();
  }
  
//锁定键盘后退与IE上后退的键
function onKeyDown() 
{ 
if ( (event.altKey) || ((event.keyCode == 8) && 
(event.srcElement.type != "text" && 
event.srcElement.type != "textarea" && 
event.srcElement.type != "password")) || 
((event.ctrlKey) && ((event.keyCode == 78) || (event.keyCode == 82)) ) || 
(event.keyCode == 116) ) { 
  event.keyCode = 0; 
  event.returnValue = false; 
  } 
 } 
document.onkeydown = onKeyDown; 
function stop(){   //这个是禁用鼠标右键 
return false; 
} 
document.oncontextmenu=stop; 

</script>
<body >

<form name ="fm">
  
  <table align="center" class=common >
    <tr class=common>
       <td align="center" height=70px>
          
           <img src="/undwrt/common/images/success.gif" align="absmiddle"><s:property value="content"/>          

       </td>
    </tr>
  </table>
  
 <s:if test="#request.Authorize != null">
  <table class=common>
    <tr>
      <td class="centertitle" align="center">
        <s:property value="#request.Authorize"/><br>
        <input type="button" value="<s:text name='prompt.goonDealAuthTask'/>" class="longbutton" onclick="goNextAuthorizeTask();">
      </td>
    </tr>
  </table>
 </s:if>
 <s:else>
 <s:if test='policyNoForT !=null && policyNoForT != ""'>
  <table class=common align="center">
    <tr>
      <td class="centertitle" align="left">
    　 <s:text name='prompt.createPolicyNo'/>：<s:property value="policyNoForT"/>

	 <s:if test='policyNoCI !=null && policyNoCI != ""'>
	   <s:text name='prompt.createRelatePowerPolicyNo'/>：<s:property value="policyNoCI"/>
	      </td>
	    </tr>
	 </s:if> 

  </table>  
 </s:if> 
  <s:if test='EnquiryNo !=null && EnquiryNo != ""'>
  <table class=common align="center">
    <tr>
      <td class="centertitle" align="center">
    　  <s:text name='undwrt.pages.undwrtDeal.inquiryListNo'/>：<s:property value="EnquiryNo"/>
      </td>
    </tr>
  </table>  
 </s:if>
 <s:if test='caseNo !=null && caseNo != ""'>
  <table class=common>
    <tr>
      <td class="centertitle" align="center">
    　  <s:text name='prompt.autoFinishCaseSuccessCreatePayCaseNo'/>：<s:property value="caseNo"/>
      </td>
    </tr>
  </table>  
 </s:if>
    <s:if test="dblExpenseBalance != null && dblExpenseBalance<0">
      <table class=common>
        <tr>
          <td class="centertitle" align="center">
    　      <s:text name='prompt.systemShow'/>：<br><s:text name='agentManage.comCode'/>：<s:property value="ComName"/> <s:text name='riskName'/>：<s:property value="RiskName"/> 的核定费用结余是<s:text name=''/>：<s:property value="dblExpenseBalance"/>已经小于0
          </td>
        </tr>
      </table>
    </s:if>

<!-- 风险单位序号  当进行风险评估时风险单位序号可以从前一个页面获得，当不是进行风险评估时hiDangerNo为空,以此来判断是否从风险评估页面流转到此页面，当是风险评估时不显示此按钮 -->
 <%
  String handTitle = (String)session.getAttribute("handTitle");
//add by zhaoyingyan 20100609 begin reason:
  if(handTitle==null){
  			handTitle="";
  }
//add by zhaoyingyan 20100609 end reason:
  String handType = (String)session.getAttribute("handType");
  String editType = (String)session.getAttribute("editType");
  String hiDangerNo = request.getParameter("hiDangerNo");
 if("".equals(hiDangerNo)||hiDangerNo==null){    //当进行风险评估时风险单位序号可以从前一个页面获得，当不是进行风险评估时hiDangerNo为空,以此来判断是否从风险评估页面流转到此页面，当是风险评估时不显示此按钮
%>
  <table class=common align="center" >
    <tr>
      <td class="centertitle" align="center">
        <input type="button" value="<s:text name='prompt.goonDeal'/><%=handTitle%><s:text name='undwrt.pages.undwrtDeal.task'/>"  class="longbutton" onclick="goNextTask();">
           <input type="hidden" name="handType" value="<%=handType%>"> 
           <input type="hidden" name="editType" value="<%=editType%>">
      </td>
    </tr>
  </table>
<%    
  }
%> 
  
<%
  //需要返回处理的功能都可用只要在session里面session.setAttribute("dealBack","ture")即可
  String dealBack = (String)session.getAttribute("dealBack"); 
  if(dealBack!=null && dealBack.equals("true"))
  {
%> 
  <table class=two>
    <tr>
      <td align="center">
    　  <Input name="buttonCancel" class="button" type="button" value="<s:text name='prompt.back'/>" alt="<s:text name='prompt.back'/>"  src="/undwrt/common/images/butReturn.gif" onclick="return preWindow();">
      </td>
    </tr>
  </table>
<%
    //销毁session变量
    session.removeAttribute("dealBack");
  }
%>

</s:else>

  </form>
</body>
</html>
