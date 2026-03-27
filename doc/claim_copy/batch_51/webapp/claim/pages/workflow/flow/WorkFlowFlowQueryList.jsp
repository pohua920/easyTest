<%--
****************************************************************************
* DESC       : 工作流流程列表
* AUTHOR     ：理赔组
* CREATEDATE ：2013-03-03
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@page import="com.sinosoft.sysframework.reference.AppConfig"%>
<%@page import="com.sinosoft.claim.schema.model.*"%>
<%@page import="com.sinosoft.claim.common.service.facade.CodeService"%>
<%@page import="java.util.*"%>
<%@page import="ins.framework.common.ServiceFactory"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.sinosoft.claim.common.ConstantCodes"%>
<% 
   CodeService codeService = (CodeService)ServiceFactory.getService("codeService");
   SimpleDateFormat formatter18 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
%>
<%
  int beforeLayerDeep = 0 ;    //前一层
  int currLayerDeep   = 0 ;    //当前层
  int posX            = 0 ;    //X坐标
  int posY            = 0 ;    //Y坐标
  String nodeName     = "" ;   //节点名称
  String deptName   = "" ;   //处理部门名称
  String nodeType     ="";     //节点的类型
  String nodeDispName ="";     //节点最终的显示内容
  String insureCarFlag="" ;    //是否是保单车辆，如果1，表示是主车，其它都为三者
  String typeFlag="";          //如果是特殊赔案，那么里面的三种情况的显示
  String nodeStatus   = "" ;   //节点状态
  int nodePosLayer    = 0  ;   //节点位置
  int nodeCount       = 0  ;   //层节点数
  int startPosX       = 0  ;   //横轴开始
  int endPosX         = 0  ;   //横轴结束
  int startPosY       = 0  ;   //纵轴开始
  int endPosY         = 0  ;   //纵轴结束
  int startNodeNo     = 0 ;    //路径始节点
  int endNodeNo       = 0 ;    //路径终节点
  String nodeColor    = "" ;   //节点颜色
  String nodeStatusColor = "" ;//节点状态颜色
  String nodeTitle    = "";    //节点title
  String licenseNo ="";        //车牌号码
  String riskCode ="";         //险种
  //工作流程说明
  String wfDetail1="";         //正常流转说明
  String wfDetail1Color="#FF8040"; //正常流转字颜色
  String wfDetail2="";         //是否包含特殊赔案说明
  String wfDetail3="";         //创建日期
  String wfDetail4="";         //关闭日期
  String wfDetail5="";         //简易赔案的状态
  String wfDetail6="";         //处理时间
  String wfDetail7="";         //处理时间
  //reason:流程显示-图中每个节点增加链接，链到此节点的详细信息
  String strInfoLink="";       //链接
  String flowStr="";           //工作流上的链接条件
  //工作流主信息对象
  SwfFlowMain swfFlowMainDto = (SwfFlowMain)request.getAttribute("swfFlowMain");
  String flowStatus = swfFlowMainDto.getFlowStatus();
  if(flowStatus!=null && !"".equals(flowStatus)){
      if (flowStatus.equals("0")) wfDetail1="流轉結束";
      if (flowStatus.equals("1")) wfDetail1="正常流轉";
      if (flowStatus.equals("9")) wfDetail1="流轉異常";      
  }
  String claimTypeFlag = swfFlowMainDto.getClaimTypeFlag();
  if(claimTypeFlag!=null && !"".equals(claimTypeFlag)){
      if (claimTypeFlag.equals("01")) wfDetail5="已轉為簡易賠案";
      if (claimTypeFlag.equals("02")) wfDetail5="簡易賠案已暫存";
      if (claimTypeFlag.equals("03")) wfDetail5="簡易賠案提交";
      if (claimTypeFlag.equals("04")) wfDetail5="簡易賠案完成";
      if (claimTypeFlag.equals("05")) wfDetail5="簡易賠案註銷";
  }
  wfDetail2="非特殊賠案";
  wfDetail3= formatter18.format(swfFlowMainDto.getCreatDate())+"創建";
  if (swfFlowMainDto.getFlowStatus().equals("0")){
      wfDetail4=formatter18.format(swfFlowMainDto.getCloseDate())+"關閉";
  }
  wfDetail6="處理時間:";
  wfDetail7=swfFlowMainDto.getSetStopTime();
  SwfLog swfLogTreeDto = (SwfLog)request.getAttribute("swfLog");
  List<SwfLog> treeSwfLogList = swfLogTreeDto.getSwfLogList();
  //路径信息
  List<SwfPathLog> treePathLogList = (List<SwfPathLog>)request.getAttribute("swfPathLogList");
  //得到节点状态列表
  List<PrpDcode> claimStatusList = (List<PrpDcode>)request.getAttribute("claimStatus");

%>
<html xmlns:v="urn:schemas-microsoft-com:vml">
<head>
  <app:css />
  <%@ include file="/common/meta_js.jsp"%>
  <%@ include file="/common/taglibs.jsp"%>
  <title><s:text name="workflow.oaFlowList"/></title><%--工作流流程列表 --%>
  <STYLE>
    v\:* { BEHAVIOR: url(#default#VML) }
  </STYLE>
  <link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
  <script src="/claim/pages/workflow/flow/js/WorkFlowFlowShow.js"> </script>
  <html:base/>
</head>

<body  >
<form name="fm">
     <c:choose>
        <c:when test="${empty param.wfLogBusinessNo}">
            <input type="hidden" name="prpLregistRegistNo" value="<c:out value='${requestScope.wfLogBusinessNo}'/>">
        </c:when>
        <c:otherwise>
            <input type="hidden" name="prpLregistRegistNo" value="<c:out value='${param.wfLogBusinessNo}'/>">
        </c:otherwise>
     </c:choose>
      <input type="hidden" name="prpLregistPolicyNo" value="<c:out value='${param.policyNo}'/>">
      <input type="hidden" name="prpLregistRiskCode" value="<c:out value='${param.riskCode}'/>">
      <input type="hidden" name="prpLclaimClaimNo" value="">
</form>
  <table class=common >
  <tr>
      <td> 
          <input type="button" class=button name="messageView" value="<s:text name="button.viewMessage.value" />" <%--查看留言 --%> onclick="openWinQuery('registNo',fm.prpLregistRegistNo.value);">
          <%
          riskCode = (String) request.getAttribute("riskCode");
          String strRiskType1 = codeService.translateRiskCodetoRiskType(riskCode);
          if (!("D".equals(strRiskType1))) {
          %>
              <input type="button" class=button name="taskView" value="<s:text name="button.TaskQuery.value"/>"<%--任务查询 --%> onclick="openWinTask('<c:out value="${requestScope.swfFlowMain.flowID}"/>');">
          <%}%>
      </td> 
  </tr>
  <!-- 通赔信息提示-->
  <c:if test="${requestScope.prpLgeneralClaimTask!=null}">
        <td>
           <font color='red'><s:text name="modifySumClaim.query5" /><font color='Fuchsia'><%--重要提示：当前案件已经被操作员 --%>
             <u><c:out value="${requestScope.prpLgeneralClaimTask.giveoperatorcode}" />--<c:out value="${requestScope.prpLgeneralClaimTask.giveoperatorname}" /></u>
           </font><s:text name="modifySumClaim.query6" /><%--进行了通赔操作，委托给了 --%>
           <font color='Fuchsia'>
             <u><c:out value="${requestScope.prpLgeneralClaimTask.receivecomcode}" />--<c:out value="${requestScope.prpLgeneralClaimTask.receivecomname}" /></u>
             </font><s:text name="modifySumClaim.query7" /></font><%--，必须请该机构下的通赔操作人员进行接收後，才能进行後续处理! --%>
          </td>
  </c:if>
  </table>
  <table class=common border="0" cellpadding="5" cellspacing="1">
    <tr><td colspan=4 class="formtitle"><s:text name="workflow.flowchart" /></td></tr><%--工作流流程图 --%>
  </table>
  <v:group ID="workflowGroup" style="position:relative;WIDTH:8000px;HEIGHT:8500px;" coordsize = "21600,25600">
<%
	SwfLog swfLogNodeDto = null;
      for(int i=0;i<treeSwfLogList.size();i++){
            swfLogNodeDto = (SwfLog) treeSwfLogList.get(i);
            currLayerDeep = swfLogNodeDto.getTreeLayer();
            nodePosLayer = swfLogNodeDto.getNodePosLayer();
            // 名称的变化
            nodeName = swfLogNodeDto.getNodeName();
            deptName = swfLogNodeDto.getDeptName();
            nodeType = swfLogNodeDto.getNodeType();
            insureCarFlag = swfLogNodeDto.getInsureCarFlag();
            nodeDispName = nodeName; // 显示内容默认为节点的名称
            // 区分强三立案
            if ((swfLogNodeDto.getNodeType().equals("claim") || swfLogNodeDto.getNodeType().equals("compe") || swfLogNodeDto.getNodeType().equals("cance") || swfLogNodeDto.getNodeType().equals("endca")) && swfLogNodeDto.getRiskCode().equals(ConstantCodes.RISKCODE_DAZ)) {
                nodeDispName = nodeDispName + "[強制險]";
            }
            licenseNo = swfLogNodeDto.getLossItemName();
            riskCode = swfLogNodeDto.getRiskCode();
            typeFlag = swfLogNodeDto.getTypeFlag();
            // 流程显示-图中每个节点增加链接，链到此节点的详细信息
            // 根据节点不同，进行不同的联接
            flowStr = "&swfLogFlowID=" + swfLogNodeDto.getId().getFlowID() + "&swfLogLogNo=" + swfLogNodeDto.getId().getLogNo() + "&status=" + swfLogNodeDto.getNodeStatus() + "&riskCode=" + swfLogNodeDto.getRiskCode() + "&editType=SHOW" + "&nodeType="
                    + swfLogNodeDto.getNodeType() + "&businessNo=" + swfLogNodeDto.getBusinessNo() + "&policyNo=" + swfLogNodeDto.getPolicyNo() + "&modelNo=" + swfLogNodeDto.getModelNo() + "&nodeNo=" + swfLogNodeDto.getNodeNo();
            if (nodeType.equals("regis")) {
                strInfoLink = "/claim/registFinishQueryList.do?prpLregistRegistNo=" + swfLogNodeDto.getBusinessNo() + flowStr;
            } 
            if (nodeType.equals("check")) {// 查勘信息
                strInfoLink = "/claim/schedule/checkFinishQueryList.do?prpLcheckCheckNo=" + swfLogNodeDto.getKeyIn() + "&lossItemCode=" + swfLogNodeDto.getLossItemCode() + "&lossItemName=" + swfLogNodeDto.getLossItemName() + "&insureCarFlag="
                        + swfLogNodeDto.getInsureCarFlag() + flowStr;
            }
            if (nodeType.equals("certa")) {// 定损信息
                strInfoLink = "/claim/certainLoss/certainLossFinishQueryList.do?prpLverifyLossRegistNo=" + swfLogNodeDto.getKeyIn() + "&lossTypeFlag=" + swfLogNodeDto.getTypeFlag() + "&insureCarFlag=" + swfLogNodeDto.getInsureCarFlag()
                        + "&lossItemCode=" + swfLogNodeDto.getLossItemCode() + "&lossItemName=" + swfLogNodeDto.getLossItemName() + flowStr;
            }
            if (nodeType.equals("verip") || nodeType.equals("verpo")) {// 核价信息
                strInfoLink = "/claim/verifyPriceFinishQueryList.do?prpLverifyLossRegistNo=" + swfLogNodeDto.getKeyIn() + "&lossTypeFlag=" + swfLogNodeDto.getTypeFlag() + "&insureCarFlag=" + swfLogNodeDto.getInsureCarFlag() + "&lossItemCode="
                        + swfLogNodeDto.getLossItemCode() + "&lossItemName=" + swfLogNodeDto.getLossItemName() + flowStr;
            }
            if (nodeType.equals("verif")) {// 核损信息
                strInfoLink = "/claim/verifyLoss/verifyLossFinishQueryList.do?prpLverifyLossRegistNo=" + swfLogNodeDto.getKeyIn() + "&lossTypeFlag=" + swfLogNodeDto.getTypeFlag() + "&insureCarFlag=" + swfLogNodeDto.getInsureCarFlag() + "&lossItemCode="
                        + swfLogNodeDto.getLossItemCode() + "&lossItemName=" + swfLogNodeDto.getLossItemName() + flowStr;
            }
            if (nodeType.equals("claim")) {// 立案信息
                strInfoLink = "/claim/claimFinishQueryList.do?prpLclaimClaimNo=" + swfLogNodeDto.getKeyOut() + flowStr;
            }
            if (nodeType.equals("endca")) {// 结案信息
                strInfoLink = "/claim/endcase/endcaseFinishQueryList.do?prpLendcaseEndcaseNo=" + swfLogNodeDto.getKeyIn() + flowStr;
            }
            if (nodeType.equals("certi")) {// 单证信息
                strInfoLink = "/claim/certifyFinishQueryList.do?prpLcertifyCertifyNo=" + swfLogNodeDto.getKeyIn() + flowStr;
            }
            if (nodeType.equals("compe")) {
                strInfoLink = "javascript:alert('請查看具體的計算書訊息')";// 实赔信息
            }
            if (nodeType.equals("sched")) {// 调度信息
                strInfoLink = "/claim/schedule/scheduleFinishQueryList.do?prpLscheduleMainWFRegistNo=" + swfLogNodeDto.getBusinessNo() + "&prpLscheduleMainWFScheduleID=1&scheduleType=sched" + flowStr;
            }
            if (nodeType.equals("wound")) {// 人伤定损
                strInfoLink = "/claim/certainLoss/certainLossFinishQueryList.do?prpLverifyLossRegistNo=" + swfLogNodeDto.getKeyIn() + "&lossTypeFlag=0" + "&insureCarFlag=" + swfLogNodeDto.getInsureCarFlag() + "&lossItemCode="
                        + swfLogNodeDto.getLossItemCode() + "&lossItemName=" + swfLogNodeDto.getLossItemName()  + flowStr;
            }
            if (nodeType.equals("propc")) {// 财产定损信息
                strInfoLink = "/claim/certainLoss/certainLossFinishQueryList.do?prpLverifyLossRegistNo=" + swfLogNodeDto.getKeyIn() + "&lossTypeFlag=0" + "&insureCarFlag=" + swfLogNodeDto.getInsureCarFlag() + "&lossItemCode="
                        + swfLogNodeDto.getLossItemCode() + "&lossItemName=" + swfLogNodeDto.getLossItemName()  + flowStr;
            }
            if (nodeType.equals("propv")) {// 财产核损信息
                strInfoLink = "/claim/verifyLoss/verifyLossFinishQueryList.do?prpLverifyLossRegistNo=" + swfLogNodeDto.getKeyIn() + "&lossTypeFlag=0" + "&insureCarFlag=" + swfLogNodeDto.getInsureCarFlag() + "&lossItemCode="
                        + swfLogNodeDto.getLossItemCode() + "&lossItemName=" + swfLogNodeDto.getLossItemName() + flowStr;
            }
            if (nodeType.equals("cance")) {// 注销/拒赔信息
                strInfoLink = "/claim/claimFinishQueryList.do?prpLclaimClaimNo=" + swfLogNodeDto.getKeyIn() + flowStr;
            }
            if (nodeType.equals("veriw")) {// 人伤核损信息
                strInfoLink = "/claim/verifyLoss/verifyLossFinishQueryList.do?prpLverifyLossRegistNo=" + swfLogNodeDto.getKeyIn() + "&lossTypeFlag=" + swfLogNodeDto.getTypeFlag() + "&insureCarFlag=" + swfLogNodeDto.getInsureCarFlag() + "&lossItemCode="
                        + swfLogNodeDto.getLossItemCode() + "&lossItemName=" + swfLogNodeDto.getLossItemName() + "&flag=1" + flowStr;
            }
            if (nodeType.equals("compp")) {// 计算书信息
                strInfoLink = "/claim/compensate/compensateFinishQueryList.do?prpLcompensateCompensateNo=" + swfLogNodeDto.getKeyOut() + flowStr;
            }
            if("veric".equals(nodeType)){//核赔
            	strInfoLink = "/claim/CommonCheckTask.do?iFlowID=" + swfLogNodeDto.getiFlowID() + "&iLogNo=" + swfLogNodeDto.getiLogNo() + "&EditType=query&HandType=22&iRiskCode=" + swfLogNodeDto.getRiskCode() + "&BusinessNo=" + swfLogNodeDto.getBusinessNo()
            			+ "&iBusinessType=" + swfLogNodeDto.getBusinessType() + "&iBusinessNo=" + swfLogNodeDto.getiBusinessNo() + "&iModelNo=" + swfLogNodeDto.getiModelNo() + "&iNodeNo=" + swfLogNodeDto.getiNodeNo();
            }
            if (nodeType.equals("speci")) {// 特殊赔案信息
                if (typeFlag.equals("7") || typeFlag.equals("8") || typeFlag.equals("5")) {
                    strInfoLink = "/claim/prepayFinishQueryList.do?prpLprepayPrepayNo=" + swfLogNodeDto.getKeyOut() + "&caseType=" + typeFlag + flowStr;
                } else {
                    strInfoLink = "/claim/compensate/compensateFinishQueryList.do?prpLcompensateCompensateNo=" + swfLogNodeDto.getKeyOut() + "&caseType=" + typeFlag + flowStr;
                }
            }
            if (swfLogNodeDto.getNodeStatus().equals("0") || swfLogNodeDto.getNodeStatus().equals("5")) {
                strInfoLink = "javascript:alert('該節點目前沒有訊息')";
            }
            if (swfLogNodeDto.getNodeStatus().equals("6")
                    && (swfLogNodeDto.getNodeType().equals("check") || swfLogNodeDto.getNodeType().equals("wound") || swfLogNodeDto.getNodeType().equals("certa") || swfLogNodeDto.getNodeType().equals("propc") || swfLogNodeDto.getNodeType().equals(
                            "claim"))) {// 立案，注销後也不显示任何信息 (暂时什么都不显示)
                strInfoLink = "javascript:alert('該節點目前已被撤銷，沒有訊息')";
            }
            if (nodeType.equals("certa") || nodeType.equals("verip") || nodeType.equals("verpo")) {
                String strRiskType = codeService.translateRiskCodetoRiskType(swfLogNodeDto.getRiskCode());
                if (("D").equals(strRiskType)) {
                    if (insureCarFlag.equals("1")) {
                        // nodeDispName= licenseNo+"\n"+nodeDispName+"标的";
                        // modify by lixiang remark 20050309 start
                        // reason:流程图显示-查勘/定损显示车牌号，原显示主车改成标的车
                        if (nodeType.equals("verpo")) {
                            nodeDispName = nodeDispName + "(" + deptName + ") 標的:" + licenseNo;
                        } else {
                            nodeDispName = nodeDispName + " 標的:" + licenseNo;
                        }
                        // modify by lixiang remark 20050309 end
                    } else {
                        // modify by lixiang remark 20050309 start
                        // reason:流程图显示-查勘/定损显示车牌号，原显示主车改成标的车
                        // nodeDispName= licenseNo+"\n"+ nodeDispName+"(三者车)";
                        if (nodeType.equals("verpo")) {
                            nodeDispName = nodeDispName + "(" + deptName + ") 三者:" + licenseNo;
                        } else {
                            nodeDispName = nodeDispName + " 三者:" + licenseNo;
                        }
                    }
                }
            }else if("wound".equals(nodeType)){
            	nodeDispName = nodeDispName+":"+licenseNo;
            }else if (nodeType.equals("speci")) {
                wfDetail2 = "特殊賠案";
                if (typeFlag.equals("3")) {
                    nodeDispName = nodeDispName + "(通融)";
                }
                if (typeFlag.equals("4")) {
                    nodeDispName = nodeDispName + "(預付)";
                }
                if (typeFlag.equals("6")) {
                    nodeDispName = nodeDispName + "(其它)";
                }
                if (typeFlag.equals("5")) {
                    nodeDispName = nodeDispName + "(預賠)";
                }
                if (typeFlag.equals("7")) {
                    nodeDispName = nodeDispName + "(支付搶救費)";
                }
                if (typeFlag.equals("8")) {
                    nodeDispName = nodeDispName + "(墊付搶救費)";
                }
            }
            // reason:流程图显示BUG已分配人员的定损显示为未分配（BUG）应显示已分配
            if (swfLogNodeDto.getNodeStatus().equals("0") && swfLogNodeDto.getHandlerCode() != null && swfLogNodeDto.getHandlerCode().trim().length() > 0) {
                swfLogNodeDto.setNodeStatus("1");
            }
            swfLogNodeDto.setNodeStatusName(codeService.translateCodeCode("ClaimStatus", swfLogNodeDto.getNodeStatus(), true));
            nodeStatus = swfLogNodeDto.getNodeStatus();
            nodeCount = swfLogNodeDto.getCountNode();
            posY = currLayerDeep * 250;
            posX = nodePosLayer * 400;
            // 根据状态确定颜色
            nodeStatusColor = "sysconst.ClaimStatus" + nodeStatus;
            nodeColor = AppConfig.get(nodeStatusColor);
            nodeTitle = "業務號:" + swfLogNodeDto.getBusinessNo() + "\n處理人員:" + (swfLogNodeDto.getHandlerName()==null?"":swfLogNodeDto.getHandlerName()) + "\n流入時間:" +(swfLogNodeDto.getFlowInTime()==null?"":swfLogNodeDto.getFlowInTime()) + "\n處理時間:" + (swfLogNodeDto.getHandleTime()==null?"":swfLogNodeDto.getHandleTime()) + "\n流出時間:" + (swfLogNodeDto.getSubmitTime()==null?"":swfLogNodeDto.getSubmitTime());
            //根据节点信息画节点
            //显示流程图里的联接/人员处理时间显示得也不对
            if(strInfoLink ==null || strInfoLink.trim().length() == 0){
            	strInfoLink = "javascript:void(0);";
            }
          %>
          <v:rect style='position:relative;top:<%=posY %>;left:<%=posX %>;width:300;height:50;z-index:8;' fillcolor='<%=nodeColor%>' strokeColor='blue' title="<%=nodeTitle%>">
              <center><v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 85.875pt; TOP:auto; HEIGHT: 25.687pt' inset = '0.5pt,2.5pt,0.5pt,0.5pt'><a href=<%=strInfoLink%>><font color="#0000A0"><%=nodeDispName%></font></a></v:TextBox></center>
           </v:rect>
          <v:rect style='position:relative;top:<%=posY+45 %>;left:<%=posX %>;width:300;height:60;z-index:1;' strokeColor='blue' title="<%=nodeTitle%>">
              <center><v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 85.875pt; TOP:auto; HEIGHT: 25.687pt' inset = '0.5pt,2.5pt,0.5pt,0.5pt'><%=swfLogNodeDto.getHandlerName()==null?"":swfLogNodeDto.getHandlerName()%></v:TextBox></center>
           </v:rect>
           <v:rect style='position:relative;top:<%=posY+90 %>;left:<%=posX %>;width:300;height:60;z-index:1;' strokeColor='blue' title="<%=nodeTitle%>">
              <center><v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 85.875pt; TOP:auto; HEIGHT: 25.687pt' inset = '0.5pt,2.5pt,0.5pt,0.5pt'><%= swfLogNodeDto.getStopTimeDesc() %></v:TextBox></center>
           </v:rect>
         <%//去掉理算让人不理解的状态显示
          if (nodeType.equals("compe") || nodeType.equals("End")){//去掉状态显示
              swfLogNodeDto.setNodeStatusName("");
            }
          %>
          <v:rect style='position:relative;top:<%=posY+145 %>;left:<%=posX %>;width:300;height:50;z-index:8;' strokeColor='blue' title="<%=nodeTitle%>">
              <center><v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset = '0.5pt,2.5pt,0.5pt,0.5pt'><%= swfLogNodeDto.getNodeStatusName() %></v:TextBox></center>
           </v:rect>
    <%}%>
    <%
        int bigTreeLayer = 0; // 最大的层
        int bigNodePosLayer = 0; // 最大节点位置
        SwfPathLog swfPathLogNodeDto = null;
        SwfLog swfLogEndDto = null;
        SwfLog swfLogStartDto = null;
        for (int i = 0; i < treePathLogList.size(); i++) {
            // 得到路径
            swfPathLogNodeDto = treePathLogList.get(i);
            startNodeNo = swfPathLogNodeDto.getStartNodeNo();
            // 得到路径对应起节点
            for (int j = 0; j < treeSwfLogList.size(); j++) {
                swfLogStartDto = treeSwfLogList.get(j);
                if (startNodeNo == swfLogStartDto.getId().getLogNo()) {
                    currLayerDeep = swfLogStartDto.getTreeLayer();
                    nodePosLayer = swfLogStartDto.getNodePosLayer();
                    startPosY = currLayerDeep * 250 + 195;
                    startPosX = nodePosLayer * 400 + 150;
                    beforeLayerDeep = currLayerDeep;
                    break;
                }
            }
            // 得到路径对应终节点
            endNodeNo = swfPathLogNodeDto.getEndNodeNo();
            for (int k = 0; k < treeSwfLogList.size(); k++) {
                swfLogEndDto = treeSwfLogList.get(k);
                if (endNodeNo == swfLogEndDto.getId().getLogNo()) {
                    currLayerDeep = swfLogEndDto.getTreeLayer();
                    // 得到最大的层
                    if (currLayerDeep > bigTreeLayer) {
                        bigTreeLayer = currLayerDeep;
                    }
                    nodePosLayer = swfLogEndDto.getNodePosLayer();
                    // 得到最大的节点位置
                    if (nodePosLayer > bigNodePosLayer) {
                        bigNodePosLayer = nodePosLayer;
                    }
                    endPosY = currLayerDeep * 250;
                    if (currLayerDeep - beforeLayerDeep == 1) {
                        endPosX = nodePosLayer * 400 + 150;
                    } else {
                        // 如果是跨层的线易重叠,做了偏移
                        endPosX = nodePosLayer * 400 + 150 - 50;
                    }
                    break;
                }
            }
        //根据节点信息画线
    %>
           <v:line style="POSITION: relative;z-index:9"  from="<%=startPosX%>,<%=startPosY%>" to="<%=endPosX%>,<%=endPosY%>"><v:stroke endarrow = "classic"></v:stroke></v:line>
    <% }//节点标题颜色说明%>
    <%
        //计算最佳的展现尺寸
        if (bigTreeLayer > 2) {
            bigTreeLayer = bigTreeLayer - 2;
        } else {
            bigTreeLayer = 2;
        }
        if (bigNodePosLayer > 4) {
            bigNodePosLayer = bigNodePosLayer - 4;
        } else {
            bigNodePosLayer = 0;
        }
    %>
          <v:rect style='position:relative;top:50;left:<%=1550 + bigNodePosLayer*400%>;width:400;height:55;z-index:8;' fillcolor='#FFFFFF' strokeColor='green'>
              <v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 100.875pt; TOP:auto; HEIGHT: 25.687pt' inset = '13pt,2pt,0pt,0pt'>節點標題顏色說明</v:TextBox>
              <v:Extrusion backdepth='5pt' on='false'/>
          </v:rect>
          <v:rect style='position:relative;top:105;left:<%=1550 + bigNodePosLayer*400%>;width:400;height:<%=claimStatusList.size()*50+100%>;z-index:8;' fillcolor='#FFFFFF' strokeColor='green'>
              <v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset = '10pt,0pt,0pt,0pt'></v:TextBox>
              <v:Extrusion backdepth='5pt' on='false'/>
          </v:rect>

    <%
        for(int n=0;n<claimStatusList.size();n++){
           PrpDcode prpDcodeDto = (PrpDcode)claimStatusList.get(n);
           nodeStatusColor         = "sysconst.ClaimStatus" + prpDcodeDto.getId().getCodeCode();
           nodeColor               = AppConfig.get(nodeStatusColor);
           posX                    = 1580 + bigNodePosLayer * 400;
           posY                    = 150+n*50 ;
    %>
          <v:rect style='position:relative;top:<%=posY%>;left:<%=posX%>;width:120;height:45;z-index:8;' fillcolor='<%=nodeColor%>' strokeColor='blue'>
              <v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 20.687pt' inset = '40pt,0pt,0pt,0pt'><%=prpDcodeDto.getCodeCName()%></v:TextBox>
              <v:Extrusion backdepth='5pt' on='false'/>
          </v:rect>
    <%
        }
        int topWfDetail = 50;
        int hightWfDetail = 320;
        int rowindex = 1;// 显示序号
        if (!wfDetail4.equals("")){
            hightWfDetail = hightWfDetail + 55;
        }
        if (!wfDetail5.equals("")){
            hightWfDetail = hightWfDetail + 55;
        }
%>
        <%//工作流程相关说明%>
          <v:rect style='position:relative;top:<%=topWfDetail%>;left:20;width:350;height:55;z-index:8;' fillcolor='#FFFFFF' strokeColor='green'>
              <v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 100.875pt; TOP:auto; HEIGHT: 25.687pt' inset = '20pt,2pt,0pt,0pt'>工作流程說明</v:TextBox>
              <v:Extrusion backdepth='5pt' on='false'/>
          </v:rect>
          <v:rect style='position:relative;top:<%=topWfDetail+55%>;left:20;width:350;height:<%=hightWfDetail%>;z-index:8;' fillcolor='#FFFFFF' strokeColor='green'>
              <v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset = '5pt,2pt,0pt,0pt'></v:TextBox>
              <v:Extrusion backdepth='5pt' on='false'/>
          </v:rect>
          <v:rect style='position:relative;top:<%=topWfDetail+80%>;left:65;width:300;height:55;z-index:8;' fillcolor='#FFFFFF' strokeColor='#FFFFFF'>
              <v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt;COLOR:#804040' inset = '0pt,2pt,0pt,0pt'><%=(rowindex++)+"."+wfDetail1%></v:TextBox>
              <v:Extrusion backdepth='5pt' on='false'/>
          </v:rect>
          <v:rect style='position:relative;top:<%=topWfDetail+135%>;left:65;width:300;height:55;z-index:8;' fillcolor='#FFFFFF' strokeColor='#FFFFFF'>
              <v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt;COLOR:#804040' inset = '0pt,2pt,0pt,0pt'><%=(rowindex++)+"."+wfDetail2%></v:TextBox>
              <v:Extrusion backdepth='5pt' on='false'/>
          </v:rect>
       <% 
        topWfDetail=topWfDetail+135;
        if (!wfDetail5.equals("")) {
            topWfDetail=topWfDetail+55;
       %>
          <v:rect style='position:relative;top:<%=topWfDetail%>;left:65;width:300;height:55;z-index:8;' fillcolor='#FFFFFF' strokeColor='#FFFFFF'>
              <v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt;COLOR:#804040' inset = '0pt,2pt,0pt,0pt'><%=(rowindex++)+"."+wfDetail5%></v:TextBox>
              <v:Extrusion backdepth='5pt' on='false'/>
          </v:rect>
       <%}%>
          <v:rect style='position:relative;top:<%=topWfDetail+55%>;left:65;width:300;height:55;z-index:8;' fillcolor='#FFFFFF' strokeColor='#FFFFFF'>
            <v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt;COLOR:#804040' inset = '0pt,2pt,0pt,0pt'><%=(rowindex++)+"."+wfDetail6%></v:TextBox>
            <v:Extrusion backdepth='5pt' on='false'/>
          </v:rect>
       <%topWfDetail=topWfDetail+55;%>
        <v:rect style='position:relative;top:<%=topWfDetail+55%>;left:65;width:300;height:55;z-index:8;' fillcolor='#FFFFFF' strokeColor='#FFFFFF'>
          <v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt;COLOR:#804040' inset = '0pt,2pt,0pt,0pt'>&nbsp;&nbsp;<%=wfDetail7%></v:TextBox>
          <v:Extrusion backdepth='5pt' on='false'/>
        </v:rect>
       <%topWfDetail=topWfDetail+55;%>
        <v:rect style='position:relative;top:<%=topWfDetail+55%>;left:65;width:300;height:55;z-index:8;' fillcolor='#FFFFFF' strokeColor='#FFFFFF'>
          <v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt;COLOR:#804040' inset = '0pt,2pt,0pt,0pt'><%=(rowindex++)+"."+wfDetail3%></v:TextBox>
          <v:Extrusion backdepth='5pt' on='false'/>
        </v:rect>
    <% 
        topWfDetail=topWfDetail+50;
        if (!wfDetail4.equals("")) {
              topWfDetail=topWfDetail+55;
    %>
        <v:rect style='position:relative;top:<%=topWfDetail%>;left:65;width:300;height:55;z-index:8;' fillcolor='#FFFFFF' strokeColor='#FFFFFF'>
          <v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt;COLOR:#804040' inset = '0pt,2pt,0pt,0pt'><%=(rowindex++)+"."+wfDetail4%></v:TextBox>
          <v:Extrusion backdepth='5pt' on='false'/>
        </v:rect>
        <%}%>
        <v:rect style="WIDTH:<%=2000 + bigNodePosLayer*400%>px;HEIGHT:<%=bigTreeLayer*300+670%>px" fillcolor="#F4F9FF">
          <v:shadow on="t" type="single" color="silver" offset="5pt,5pt"></v:shadow>
        </v:rect>
  </v:group>
  <table  class="common" cellpadding="5" cellspacing="1" >
   <tr><td colspan=8 class="formtitle"><s:text name="workflow.oaFlowList" /></td></tr><%--工作流流程列表 --%>
     <tr class=listtitle>
        <td align="center"><s:text name="db.prpGnode.nodeNo"/><%--节点号 --%></td>
        <td align="center"><s:text name="db.prpGnode.nodeName"/><%--节点名称 --%></td>
        <td align="center"><s:text name="sendUndwrt.BusinessNumber"/><%--业务号 --%></td>
        <td align="center"><s:text name="workflow.dealPerson"/><%--处理人员 --%></td>
        <td align="center"><s:text name="claim.intoTime"/><%--流入时间 --%></td>
        <td align="center"><s:text name="guarantee.dealIime"/><%--处理时间 --%></td>
        <td align="center"><s:text name="workflow.flowTime"/><%--流出时间 --%></td>
        <td align="center"><s:text name="workflow.currentState"/><%--当前状态 --%></td>
     </tr>
     <c:forEach items="${requestScope.swfLog.swfLogList}" var="swfLog1" varStatus="stat">
        <c:if test="${stat.index%2==0}">
          <tr class="listodd"></c:if>
        <c:if test="${stat.index%2!=0}">
          <tr class="listeven"></c:if>
            <td align="center"><c:out value="${swfLog1.nodeNo}"/></td>
            <td align="center"><c:out value="${swfLog1.nodeName}"/></td>
            <td align="center"><c:out value="${swfLog1.keyOut}"/></td>
            <td align="center"><c:out value="${swfLog1.handlerName}"/></td>
            <td align="center"><%--<c:out value="${swfLog1.flowInTime}"/>--%>
            <rc:rcDate name="flowInTime" class="readonly" readonly="true" wdatePicker="false"  style="width:145px" value="${swfLog1.flowInTime}" /> 
            </td>
            <td align="center"><%--<c:out value="${swfLog1.handleTime}"/>--%>
            	<rc:rcDate name="handleTime" class="readonly" readonly="true" wdatePicker="false"  style="width:145px" value="${swfLog1.handleTime}" /> 
            </td>
            <td align="center"><%--<c:out value="${swfLog1.submitTime}"/>--%>
            <rc:rcDate name="submitTime" class="readonly" readonly="true" wdatePicker="false"  style="width:145px" value="${swfLog1.submitTime}" /> 
            </td>
            <td align="center"><c:out value="${swfLog1.nodeStatusName}"/></td>
          </tr>
     </c:forEach>
     <tr class="listtail">
        <td colspan="8"><s:text name="certainLoss.totalInquiries"/><%--共查询出--%><c:out value="${fn:length(requestScope.swfLog.swfLogList)}"/> <s:text name="certainLoss.meetRecord" /></td><%--条满足条件的记录 --%>
     </tr>
  </table>
   <!-- 核赔通过计算书赔付记录-->
  <table class="common" cellpadding="5" cellspacing="1" >
     <tr><td colspan=8 class="formtitle"><s:text name="workflow.payThroughBookCondition" /></td></tr><%--已核赔通过计算书赔付情况 --%>
     <tr class=listtitle>
        <td align="center"><s:text name="compensate.computeBookNum"/><%--计算书号 --%></td>
        <td align="center"><s:text name="workflow.payCosts"/><%--赔款/费用 --%></td>
        <td align="center"><s:text name="workflow.nowPayState"/><%--当前赔付状态 --%></td>
        <td align="center"><s:text name="workflow.realPayTime"/><%--实际赔付时间 --%></td>
     </tr>
     <c:forEach items="${requestScope.payRefRecList}" var="payRefRecDto" varStatus="stat">
        <c:if test="${stat.index%2==0}">
          <tr class="listodd"></c:if>
        <c:if test="${stat.index%2!=0}">
          <tr class="listeven"></c:if>
        <td align="center"><c:out value="${payRefRecDto.compensateNo}"/></td>
        <td align="center"><c:out value="${payRefRecDto.reasonName}"/></td>
        <td align="center"><c:out value="${payRefRecDto.status}"/></td>
        <td align="center"><%--<c:out value="${payRefRecDto.payDate}"/>--%>
        	<rc:rcDate name="payDate" class="readonly" readonly="true" wdatePicker="false"  style="width:80px" value="${payRefRecDto.payDate}" /> 
        </td>
     </c:forEach>
  </table>
  <%--/**通赔历史记录*/--%>
  <c:if test="${not empty requestScope.prpLgeneralClaimTaskLogList}">
    <table class="common" cellpadding="0" cellspacing="0">
        <tr>
            <td class="formtitle" align="left"><s:text name="workflow.commissionedHistoryTaskList" /></td><%--通赔历史任务列表 --%>
        </tr>
    </table>
    <TABLE cellpadding="3" cellspacing="0"  class="common" id=GeneralClaimResultTable>
      <THEAD>
        <TR class="formtitle">
          <TD width=5% class="formtitle"><s:text name="db.prpDrate.serialNo" /></TD><%--序号 --%>
          <TD width=10% class="formtitle"><s:text name="prompt.queRegist.RegistNo" /></TD><%--报案号 --%>
          <TD width=10% class="formtitle"><s:text name="workflow.commissionedParty" /></TD><%--委托机构--%>
          <TD width=10% class="formtitle"><s:text name="workflow.receivePatty" /></TD><%--接收机构 --%>
          <TD width=7% class="formtitle"><s:text name="certify.groupClient" /></TD><%--委托人--%>
          <TD width=10% class="formtitle"><s:text name="workflow.commissionedTime" /></TD><%--委托时间 --%>
          <TD width=7% class="formtitle"><s:text name="workflow.receivePerson" /></TD><%--接收人 --%>
          <TD width=10% class="formtitle"><s:text name="workflow.receivingTime" /></TD><%--接收时间--%>
          <TD width=10% class="formtitle"><s:text name="workflow.payPart" /></TD><%--通赔环节 --%>
          <TD width=16% class="formtitle"><s:text name="workflow.payReason" /></TD><%--通赔原因--%>
        </TR>
      </thead>
      <tbody>
        <c:forEach items="${requestScope.prpLgeneralClaimTaskLogList}" var="prpLgeneralClaimTaskLog" varStatus="stat">         
          <TR class=content>
              <TD align='center'><c:out value="${stat.count}"/></TD>
              <TD align='center'><c:out value="${prpLgeneralClaimTaskLog.registno}" /></TD>
              <TD align='center'><c:out value="${prpLgeneralClaimTaskLog.givecomname}" /></TD>
              <TD align='center'><c:out value="${prpLgeneralClaimTaskLog.receivecomname}" /></TD>
              <TD align='center'><c:out value="${prpLgeneralClaimTaskLog.giveoperatorname}" /></TD>
              <TD align='center'><c:out value="${prpLgeneralClaimTaskLog.givetime}" /></TD>
              <TD align='center'><c:out value="${prpLgeneralClaimTaskLog.receiveoperatorname}" /></TD>
              <TD align='center'><c:out value="${prpLgeneralClaimTaskLog.receivetime}" /></TD>
              <TD align='center'><c:out value="${prpLgeneralClaimTaskLog.currentnode}" /></TD>
              <TD align='center'><c:out value="${prpLgeneralClaimTaskLog.remark}" /></TD>
          </TR>
        </c:forEach>
      </tbody>
    </TABLE>
  </c:if>
  <script language="javascript">
     function openWinQuery1(receiveParam,nodeName){
        var win;
        var messagedo="/claim/messageQuery.do?"+receiveParam+"="+nodeName;
        win=window.showModalDialog(messagedo,"NewWindow","status=no,resizable=yes,scrollbars=yes,width=500,Height=400");
      }
   </script>
</body>
</html>