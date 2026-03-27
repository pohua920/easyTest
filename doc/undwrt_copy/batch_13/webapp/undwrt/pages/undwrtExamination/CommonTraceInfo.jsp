<!--***************************************************************************
* Description:  轨迹信息显示
* Author     :  Luyang
* CreateDate :  2005-1-18 9:59
* UpdateLog：   Name       Date            Reason/Contents
****************************************************************************-->
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<%@ taglib prefix="rc" uri="http://util.one.sinosoft.com/RCDate"%>
<!-- 滚动条样式定义 -->
<%@ include file="/pages/undwrtDeal/CommonStyle.html"%>
<html>
  <head>
  <script src="/undwrt/common/js/My97DatePicker/WdatePicker.js"></script>
    <title><s:text name="undwrt.pages.undwrtExamination.CommonTraceInfo"/></title>
    <link rel="stylesheet" type="text/css" href="/undwrt/css/Standard.css">
    <!--公用信息-->
    <script language='javascript'>
		function moreDetail(Field){
			var intIndex = getElementOrder(Field)-1;
			
			var intLength = fm.DetailMessage.length;
			
			if(intLength == undefined){
				var span = eval("ILog" );
				}else{
			
			var span = eval("ILog" + "(" + intIndex + ")");
	  	}
			if(span.style.display == ""){
				span.style.display ="none";
			}else{
				span.style.display ="";
			}
		}
    </script>
</head>

<body onload="">
  <form name="fm" action="">
    <table class="common" cellpadding="5" cellspacing="1" align="center" id="Tinsure">
      <tr class=listtitle>
        <td colspan="4"><s:text name="undwrt.pages.undwrtDeal.examineOpinion"/></td>
      </tr>
    </table>
   <s:if test="#request.TraceInfoList!=null">
   <s:iterator id="TraceInfoList" status="statu" value="#request.TraceInfoList">
   <table class="common" cellpadding="5" cellspacing="1" align="center" id="Tinsure"  style="border: 2px solid #FF0000;">
   <tr><td colspan=4></tr>
      <tr>
      	<input type="hidden" name="count">
        <td  class="title4"><s:text name="undwrt.pages.undwrtDeal.gradeName"/>：</td>
        <td  class="input4" colspan=3><s:property value="#TraceInfoList.nodeName"/></td>
      </tr>
      <tr>
        <td  class="title4"><s:text name="undwrt.pages.undwrtDeal.disposePerson"/>：</td>
        <td  class="input4"><s:property value="#TraceInfoList.operatorName"/></td>
        <td  class="title4"><s:text name="undwrt.pages.undwrtDeal.disposeCom"/>：</td>
        <td  class="input4"><s:property value="#TraceInfoList.deptCode"/></td>
      </tr>
      <tr>
         <s:if test='#TraceInfoList.nodeNo=="-1"'>
           <td  class="title4"><s:text name="undwrt.pages.undwrtDeal.quotationStates"/>：</td>
        </s:if>
        <s:if test='#TraceInfoList.nodeNo!="-1"'>
           <td  class="title4"><s:text name="undwrt.pages.undwrtDeal.gradeStates"/>：</td>
        </s:if>
        <td  class="input4"><s:property value="#TraceInfoList.nodeStatusName"/></td>
        <td  class="title4"><s:text name="undwrt.pages.undwrtDeal.flowDirection"/>：</td>
        <td  class="input4"><s:property value="#TraceInfoList.flowStatusName"/></td>
      </tr>
      <tr>
        <td  class="title4"><s:text name="undwrt.pages.undwrtDeal.submitTime"/>：</td>
        <td  class="input4"><rc:rcDate name = "#TraceInfoList.flowInTime" format="yyyy-MM-dd HH:mm:ss"/></td>
        <td  class="title4"><s:text name="undwrt.pages.undwrtDeal.disposeOkDate"/>：</td>
        <s:if test='#TraceInfoList.submitTime==""'>
        	<td  class="input4"></td>
        </s:if>
        <s:else>
        	<td  class="input4"><rc:rcDate name = "#TraceInfoList.submitTime" format="yyyy-MM-dd HH:mm:ss"/></td>
        </s:else>
      </tr>
      <tr>
        <td class=title4><s:text name="undwrt.pages.undwrtDeal.examineOpinion"/>：</td>
        <td readonly class=input4 colspan="3">
          <textarea class=common name="HandleTextMemo" rows="4" readonly ><s:property value="#TraceInfoList.handleText"/></textarea></td>
      </tr>
      <s:if test="#TraceInfoList.isILog==1">
	      <tr>
	        <td class=title4><s:text name="undwrt.pages.undwrtDeal.ruleDetailedFeedback"/>:</td>
	        <td readonly class=input4 >
		        <Input name = "DetailMessage" class="button" type="button"  value="<s:text name='undwrt.pages.undwrtDeal.detailedInformation'/>" onclick="moreDetail(this);">
			</td>
			<td readonly class=input4 colspan="2">
			<span id  = "ILog" style = "display:none" >
				<textarea class=common name="ILog"  readonly > <s:property value="#TraceInfoList.message"/></textarea>
	        </span>
	        </td>
	      </tr>
	      </s:if> 
  </table>
  <br>
   </s:iterator>
   </s:if>
    <table class=two>
      <tr>
        <td align="center">
          <Input class="button" name="buttonClose" type="button" alt="<s:text name='undwrt.close'/>" value="<s:text name='undwrt.close'/>" onclick="window.close();"></td>
      </tr>
    </table>
</form>
</body>
</html>