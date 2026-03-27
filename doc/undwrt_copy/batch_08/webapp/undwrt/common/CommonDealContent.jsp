<!--***************************************************************************
* Description: 公共处理任务主界面(包括详细信息、提交再保确认、保存、提交等。)
* Author     : luyang
* CreateDate : 2004-12-30 10:53
* UpdateLog  ：Name       Date            Reason/Contents
------------------------------------------------------------------------------
*              LanNing    20080519        增加投资型通用险种处理
****************************************************************************-->
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.platform.bl.facade.BLUtiUwLevelFacade"%>
<%@page import="com.sinosoft.utiall.dbsvr.DBPrpDrisk"%>
<%@page import="com.sinosoft.platform.dto.domain.UtiUwLevelDto"%>
<%@page import="com.sinosoft.prpall.blsvr.tb.*" %>


<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!-- 滚动条样式定义 -->
<%@ include file="/common/CommonStyle.html"%>
<%@ page import="com.sinosoft.sysframework.reference.AppConfig"%>

<!-- add by xuhuiling 回調函數導入的 begin 需求150 20160823 -->
<script language="javascript" src="/undwrt/e3/tree/yui/build/yahoo-dom-event/yahoo-dom-event.js"></script>
<script type='text/javascript' src="/undwrt/widgets/yui/connection/connection-min.js"></script>
<script type='text/javascript' src="/undwrt/widgets/yui2/json/json-min.js"></script>
<script type='text/javascript' src="/undwrt/widgets/yui/element/element-beta-min.js"></script>
<!-- add by xuhuiling 回調函數導入的 begin 需求150 20160823 -->
<%
  //禁止缓存，防止用户点击后退
  //response.setHeader("Cache-Control","no-store");
  //response.setHeader("Pragrma","no-cache");
  //response.setDateHeader("Expires",0);


  String riskCode = "0501";
  boolean isGuanLian = true;
  boolean isGuanLianCI = false;
  BLPrpTmain blprptmain = new BLPrpTmain();
  BLPrpTmainSub prptmainsub = new BLPrpTmainSub();
//   blprptmain = prptmainsub.checkPrptmainSub(iBusinessNo);
//   if("0507".equals(riskCode)){
  	
//    	if(blprptmain!=null&&blprptmain.getSize()>0){
  		isGuanLian = false;
//   	}
//   }else{
// 	  	if(blprptmain!=null&&blprptmain.getSize()>0){
// 	  		iBusinessNoCI = blprptmain.getArr(0).getProposalNo();
// 	  		isGuanLianCI = true;
// 	  	}
	  
//   }
  String strResultCode= (String)request.getAttribute("ILogReusltCode");
  String strMainPolicyNo= (String)request.getAttribute("MainPolicyNo");
  String strUserCode = (String)session.getAttribute("userCode");
//   int strUserCodeNodeNo = (Integer)session.getAttribute("myUserCodeNodeNo");
  String conditionslevel = "";
//   int nodeNo = 0;
//   boolean updateQx = false;
   BLUtiUwLevelFacade blUtiUwLevelFacade = new BLUtiUwLevelFacade();
//   UtiUwLevelDto utiUwLevelDto = new UtiUwLevelDto();
//   String conditionslevel = "UWTYPE = 'T' AND VALIDSTATUS  = '1' AND USERCODE = '"+ strUserCode +"'";
//   Collection UtiUwLevel = blUtiUwLevelFacade.findByConditions(conditionslevel);
//   for (Iterator iter = UtiUwLevel.iterator(); iter.hasNext();) {
//   	utiUwLevelDto = (UtiUwLevelDto) iter.next();
//   	nodeNo = utiUwLevelDto.getNodeNo();
//   	if(nodeNo>=8){
//   		updateQx = true;
//   		break;
//   	}
//   }
  conditionslevel = "USERCODE = '"+ strUserCode +"' AND COMCODE = '0000000000' AND VALIDSTATUS  = '1' AND UWTYPE = 'T'";
  int countNodeNo = blUtiUwLevelFacade.getCount(conditionslevel);
  
  String classCode = riskCode.substring(0,2);
     if(strMainPolicyNo==null){
     strMainPolicyNo="";
     }
  //add by xuning 添加对通用险种的转换
  DBPrpDrisk dbPrpDrisk = new DBPrpDrisk();
  //System.out.println("riskCode="+riskCode);
  int intFlag = dbPrpDrisk.getInfo(riskCode);
  String strCommonRisk = "";
  if(intFlag==100||intFlag==0&&dbPrpDrisk.getValidStatus().equals("0"))
        {
%>
<script language=javascript>
  alert("<s:text name='undwrt.CommonDealContent.riskNone'/>");
  return;
</script>
<%
        }
        if(dbPrpDrisk.getFlag().length()>=3)
        {
          //通用险种（财产）：00Q1
          if(dbPrpDrisk.getFlag().substring(1,3).equals("Q1")){
          strCommonRisk = "00Q1";}
          //通用险种（责任1）：00Z1
          else if(dbPrpDrisk.getFlag().substring(1,3).equals("Z1")){
          strCommonRisk = "00Z1";}
          //通用险种（责任2）：00Z2
          else if(dbPrpDrisk.getFlag().substring(1,3).equals("Z2")){
          strCommonRisk = "00Z2";}
          //通用险种（货运1）：00Y1
          else if(dbPrpDrisk.getFlag().substring(1,3).equals("Y1")){
          strCommonRisk = "00Y1";}
          //通用险种（货运2）：00Y2
          else if(dbPrpDrisk.getFlag().substring(1,3).equals("Y2")){
          strCommonRisk = "00Y2";}
          //通用险种（船舶）：00C1
          else if(dbPrpDrisk.getFlag().substring(1,3).equals("C1")){
          strCommonRisk = "00C1";}
          //通用险种（投资型）：00T1 added by LanNing 20080519
          else if(dbPrpDrisk.getFlag().substring(1,3).equals("T1")){
          strCommonRisk = "00T1";}
        }
        //end add by xuning gpic 20061102
//   double Nownodeno = Double.parseDouble(request.getParameter("iNodeNo"));     //用来控制,只有总公司的核保人才能对临分意向进行操作.
  //System.out.println("-11--Nownodeno="+Nownodeno);
  String historyProposal = "";
  String historyLoss = "";
  //add by zhulei 20060426 增加登陆机构传参 myComCode
  String myComCode = (String)session.getAttribute("comCode");
  if(session.getAttribute("historyProposal")!=null)
    historyProposal = (String)session.getAttribute("historyProposal");
  if(session.getAttribute("historyLoss")!=null)
    historyLoss = (String)session.getAttribute("historyLoss");
%>
<html>
  <head>
   <link rel="stylesheet" type="text/css" href="/undwrt/css/Standard.css">

    <title><s:property value="editTitle"/><s:property value="handTitle"/><s:text name="undwrt.CommonDealContent.task"/></title>
    <!--通用函数-->
    <script src="/undwrt/common/js/Common.js"></script>

    <!--通用任务处理函数-->
    <script src="/undwrt/common/js/CommonTaskDeal.js"></script>
    <script src="/undwrt/common/js/WfLogQuery.js"></script>
    </head>
  <body onload="initDangerUnit();loadForm()">
    <form name="fm" method="post">
    <!--隐含域，数据提交-->
        <input type="hidden" name="DealType">
        <input type="hidden" name="strMainPolicyNo" value="<s:property value="strMainPolicyNo"/>">
        <input type="hidden" name="editType" value="<s:property value="editType"/>">
        <input type="hidden" name="handType" value="<s:property value="handType"/>">
        <input type="hidden" name="MessageId" value=<s:property value="messageId"/>>
        <input type="hidden" name="BusinessNo" value=<s:property value="iBusinessNo"/>>
        <input type="hidden" name="BusinessNoCI" value=<s:property value="iBusinessNoCI"/>>
        <input type="hidden" name="BusinessType" value=<s:property value="iBusinessType"/>>
        <input type="hidden" name="FlowId" value=<s:property value="iFlowID"/>>
        <input type="hidden" name="NodeNo" value=<s:property value="iNodeNo"/>>
        <input type="hidden" name="LogNo" value=<s:property value="iLogNo"/>>
        <input type="hidden" name="ModelNo" value=<s:property value="iModelNo"/>>
        <input type="hidden" name="ContractNo" value=<s:property value="iContractNo"/>>
        <input type="hidden" name="strRiskCode" value=<s:property value="iRiskCode"/>>
        <input type="hidden" name="CommonRisk" value=<s:property value="strCommonRisk"/>>
        <input type="hidden" name="classCode" value=<s:property value="strClassCode"/>>
        <input type="hidden" name="OperatorCode" value="<s:property value='dealInfo.operatorCode' />">
        <input type="hidden" name="OperatorName" value="<s:property value='dealInfo.operatorName' />">
        <input type="hidden" name="SubmitDirection" value="">
        <input type="hidden" name="selectNodeNo" value="<s:property value='swfPath.swfNodeByfkPathNode2.id.nodeNo' />">
        <input type="hidden" name="selectNodeName" value="<s:property value='swfPath.endNodeName' />">
        <input type="hidden" name="FlowStatus" value="<s:property value="iFlowStatus"/>">
        <input type="hidden" name="Flag" value="1">
        <input type="hidden" name="submitTip">
        <!-- add by zhulei 20060426 增加登陆机构传参myComCode -->
        <input type="hidden" name="myComCode" value="<%=myComCode%>">
        <!-- add by zhaoning20090709 增加规则引擎返回的核保级别 -->
        <input type="hidden" name="ResultCode" value="<s:property value="#dealInfo.resultCode"/>">
		<input type="hidden" name="comCode" value="<s:property value="comCode"/>">
		<input type="hidden" name="iPackageID" value="<s:property value="iPackageID"/>">
		<input type="hidden" name="iFlowStatus" value="<s:property value="iFlowStatus"/>">
		<input type="hidden" name="iDeptCode" value="<s:property value="iDeptCode"/>">
		<input type="hidden" name="iFlowInTime" value="<s:property value="iFlowInTime"/>">
		<input type="hidden" name="iNodeStatus" value="<s:property value="iNodeStatus"/>">
		<input type="hidden" name="iRiskCode" value="<s:property value="iRiskCode"/>">
		<input type="hidden" name="iNodeName" value="<s:property value="iNodeName"/>">
		
		<!-- mantis： CAR0051 ，處理人員： David ，需求單編號： CAR0051   start 原因  業務人員失效減核問題-核保系統檢核 -->
		<input type="hidden" name="validIdentifyNumber" value="<%=request.getAttribute("validIdentifyNumber")%>">
		<input type="hidden" name="validstatusUsercode" value="<%=request.getAttribute("validstatusUsercode")%>">
		<!-- mantis： CAR0051 ，處理人員： David ，需求單編號： CAR0051   end -->
		
      <table class="common" cellpadding="5" cellspacing="1" align="center" border="0">
        <tr class=listtitle>
        <td colspan="4">${editTitle}${handTitle}<s:text name="undwrt.CommonDealContent.task1"/></td>
         <!--  <td colspan="4"><s:property value="editTitle"/><s:property value="handTitle"/>任务</td> -->
        </tr>
      <br>
        <tr>
          <td class=title4><s:text name="undwrt.CommonDealContent.dealDept"/>：</td>
          <s:property value="dealInfo.deptname" />
          <td class=input4><input readonly class=readonly  type="text" name="DeptCode" value="<s:property value="dealInfo.deptName" />" ></td>
          <td class=title4><s:text name="undwrt.CommonDealContent.submitTime"/>：</td>
          <td class=input4><input readonly class=readonly type="text" name="HandleTime" value="<s:property value="dealInfo.flowInTime" />" ></td>
        <input type="hidden" name="DefaultFlag" value="1">
        
        </tr>
     <!-- 危险单位 -->
        <%@include file = "CommonDangerUnits.jsp"%>
        <%--add by zhangjunfang 20061209 增加风险评估信息--%>
        <s:if test='iRiskCode =="0906" || iRiskCode=="0907" || iRiskCode=="1001" || iRiskCode=="1002" ||("0101,0102,0104,0701,0702,1003".indexOf(iRiskCode)>-1)'>
        <tr>
            <td colspan="9" ><%@include file="CommonRiskValuat.jsp"%></td>
        </tr>
        </s:if>
        <%--add end--%>
        <s:if test='"4" != iNodeStatus && "0"!=iNodeStatus'>
        <tr>
            <td>
                <Input name="butViewTranceInfo" class="longbutton" type="button" value="<s:text name='undwrt.CommonDealContent.historyApprovalAdvice'/>" onclick="viewTranceInfo()">
            </td> 
		   <s:if test='("03"==strClassCode||"B"==strClassCode||"A"==strClassCode||("27"==strClassCode && "2729"!=iRiskCode)|| ("23"==strClassCode && "2301"!=iRiskCode &&"2315" != iRiskCode))'>
            <td>
                <Input name="otherFeesInfo" class="longbutton" type="button" value="<s:text name='undwrt.pages.undwrtDeal.applicationAwarenessCode'/>" onclick="openOtherFees();">
            </td>
            </s:if>
            <!-- added by yanglibo 20081027 begin 增加规则引擎详细信息 -->
            <td style="display:none">
            
                <Input name="butViewIlogInfo" class="longbutton" type="button" value="<s:text name='undwrt.pages.undwrtDeal.rulesMessages'/>" onclick="viewIlogInfo()">
            </td>
            <td style="display:none">
                 <Input name="butViewIlogMainSubInfo" class="longbutton" type="button" value="<s:text name='undwrt.pages.undwrtDeal.liaisonRulesMessages'/>" onclick="viewIlogMainSubInfo()">
             </td>
              <!-- added by yanglibo 20081027 end 增加规则引擎详细信息 -->
        </tr>
        <tr class=listtitle>
            <td colspan="9" ><s:text name="undwrt.CommonDealContent.approvalInfo"/></td>
        </tr>
        <tr>
          <td class=title4><s:text name="undwrt.CommonDealContent.signApprovalAdvice"/>：</td>
          <td class=input4>
            <textarea class=big wrap="soft" name="HandleText"  />
               <s:if test="notionContent != null ">
			      <s:iterator value="notionContent" status="statu" id="uwNotion">		      	 
					<s:property value='#uwNotion.handleText'/>				 
			      </s:iterator>
			   </s:if>
			   </textarea>
          </td>
          <td class=title4 ><s:text name="undwrt.CommonDealContent.approvalPhrase"/>：</td>
          <td class=input4>
            <!-- mantis： CAR0051 ，處理人員： David ，需求單編號： CAR0051   原因  業務人員失效減核問題-核保系統檢核 -->
	        <input type="hidden" name="notionFlag" value="">
            <select class=common name="notion" onchange="changeNotion1(this)" /> 
               <option value="">----- <s:text name="undwrt.CommonDealContent.pleaseChoose"/> -----</option>           
             <s:if test="notionCodeList != null ">
				 <s:iterator value="notionCodeList" status="statu" id="prpDcode">
					<option name="notionOption" value='<s:property value="#prpDcode.id.codeCode"/>'><s:property value="#prpDcode.id.codeCName"/></option>
				 </s:iterator>
			 </s:if>
            </select>
          </td>
        </tr>
        </s:if>       
     <!-- 风险资料 -->
     <%--@include file = "/common/CommonRiskInfo.jsp"--%>
      
     <!-- 单证信息 -->
     <%--@include file = "/common/CommonCertifyInfo.jsp"--%>

      </table>
<s:if test='"4"!="iNodeStatus" && "0"!="iNodeStatus"'>	
	 
      <table class=two>
        <tr>

    <s:if test='editType != "query"'>
  		<td class="button">
      		<Input name="butSaveForm" class="button" type="button" value=" <s:text name='undwrt.CommonDealContent.tempSave'/> " onclick="return saveTask();">
    	</td>
  		<td class="button">
      		<Input name="butCancel" class="button" type="button" value="<s:text name='undwrt.abortMission'/>" onclick="return cancelTask();">
    	</td>  
  		<!--modify by yanglibo 20090512 begin reason：非车险权限岗位调整，增加核保初审岗-->
        <s:if test='"<s:text name='undwrt.pages.undwrtDeal.beforehandApproved'/>" ==iNodeName'>
  				<td class="button">
    				<Input type="hidden" class="button" name="passBtn" value="<s:text name='undwrt.HebaoTaskDealQueryQta.auditPass'/>" onclick="submitPass();">
  				</td>  	
        </s:if>
        <s:else>
  				<td class="button">
    				<Input type="button" class="button" name="passBtn" value="<s:text name='undwrt.HebaoTaskDealQueryQta.auditPass'/>" onclick="submitPass();">
  				</td>  	
        </s:else>
    		<!--modify by yanglibo 20090512 end reason：非车险权限岗位调整，增加核保初审岗-->
        <s:if test='"<s:text name='undwrt.CommonDealContent.undwrtOneLevelAA'/>"==iNodeName'>
		    	<td class="button">
		    		<input type="hidden" class="button" name="submitSuperior" value="<s:text name='undwrt.CommonDealContent.submitUpLevel'/>"
		       		onclick="submitTaskBefore('SubmitSuperior');">
				</td>
        </s:if>
        <s:else> 
		    	<td class="button">
		    		<input type="button" class="button" name="submitSuperior" value="<s:text name='undwrt.CommonDealContent.submitUpLevel'/>"
		       		onclick="submitTaskBefore('SubmitSuperior');">
		    	</td>
        </s:else>
  			<td class="button">
      			<input type="button" class="button" name="submitJunior" value="<s:text name='undwrt.CommonDealContent.downSendUpdate'/>" onclick="submitTaskBefore('SubmitJunior');">
  			</td>
        <!--       		//modify by zhulei 20051213 暂时隐藏“拒保”按钮 -->
        <s:if test='handType=="11" && 1==0'>       <!-- 核保 -->

      			<td class="button" width=20%>
      				<input class=button type="button" value="<s:text name='undwrt.declinature'/> " alt="<s:text name='undwrt.declinature'/>" onclick="return checkTextArea();comfirmRefuse();"
         			style="cursor:hand">
      			</td>
        </s:if>
	</s:if>
	<s:else>
        <td  class=button width=20%>
         <Input name="butQuery" class="button" type="button" alt="<s:text name='prompt.back'/>" value="<s:text name='prompt.back'/> " onclick="history.back(-1);">
        </td>
    </s:else>
        </tr>
      </table>
</s:if>
    </form>
  </body>
</html>
