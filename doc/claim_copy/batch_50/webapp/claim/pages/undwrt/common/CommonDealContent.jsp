<!--***************************************************************************
* Description: 公共处理任務主界面(包括详细信息、提交再保确认、保存、提交等。)
* Author     : 理赔组
* CreateDate : 2013-03-30 10:53
* UpdateLog  ：Name       Date            Reason/Contents
****************************************************************************-->
<%@ page contentType="text/html; charset=GBK"%>
<!-- 滚动条样式定义 -->
<%@ include file="/pages/undwrt/common/CommonStyle.html"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta_js.jsp"%>
<c:set var="handTitle" value="核賠" scope="page" />
<c:choose>
	<c:when test="${param.EditType=='deal'}">
		<c:set var="editTitle" value="處理" scope="page" />
	</c:when>
	<c:otherwise>
		<c:set var="editTitle" value="查詢" scope="page" />
	</c:otherwise>
</c:choose>
<html>
<head>
<title>核賠${pageScope.handTitle}<s:text name="title.undwrtBeforeEdit.Task" /></title>
<%-- 任務 --%>
<link href="${ctx }/pages/undwrt/css/KMessageBox.css" rel="stylesheet" rev="stylesheet" type="text/css" />
<!--通用函数-->
<script type="text/javascript" src="${ctx }/pages/undwrt/common/js/prototype.js"></script>
<script type="text/javascript" src="${ctx }/pages/undwrt/common/js/KMessageBox.js"></script>
<!--通用任務处理函数-->
<script src="${ctx }/pages/undwrt/common/js/CommonTaskDeal.js"></script>
<script src="${ctx }/pages/undwrt/common/js/WfLogQuery.js"></script>
<script type="text/javascript">
    function showPassDay() {
        var passDayList = document.getElementsByName("passDay");
        if (passDayList.length > 0 && passDayList[0] != null
                && passDayList[0].value != 0) {
            alert("收到客戶索賠申請已過" + passDayList[0].value + "天，請盡快處理！");
        }
    }
  //mantis：CLM0132，處理人員：CC009，需求單編號：核賠人員受款人ID檢核修改 START
    function showContent() {
    	var contentFlag = ${requestScope.contentFlag};
    	if(false == contentFlag){
    		alert("核賠經辦人員不可為受款人！");
    	}
    }
  //mantis：CLM0132，處理人員：CC009，需求單編號：核賠人員受款人ID檢核修改 END
  
  //mantis：CLM0150，處理人員：DP0706，需求單編號：新核心-車資費用人員階級管控 START
  //超過可申請限額
  function showCarFeeQuota(){
	var showCarFeeQuotaFlag = ${requestScope.showCarFeeQuotaFlag};
	if(true == showCarFeeQuotaFlag){
		var carFeeQuota = ${requestScope.carFeeQuota};
		alert("案件申請車資費用申請已超額："+carFeeQuota+" 元！");
  		document.getElementsByName("butSaveForm")[0].disabled=true;//暫存按鈕
  		document.getElementsByName("passBtn")[0].disabled=true;//審核通過按鈕
  	}
  }
//mantis：CLM0150，處理人員：DP0706，需求單編號：新核心-車資費用人員階級管控 END

  // mantis：CLM0216，處理人員：DP0714，新核心-新增車險醫詢費用提示檢核 -- start
  function checkChargeAmountMsg() {
	  var checkChargeAmountMsg = document.getElementsByName("checkChargeAmountMsg")[0].value;
	  if (checkChargeAmountMsg!='') {
	  	alert(checkChargeAmountMsg);
	  }
  }
  // mantis：CLM0216，處理人員：DP0714，新核心-新增車險醫詢費用提示檢核 -- end

</script>
</head>
<link rel="stylesheet" type="text/css" href="${ctx}/pages/undwrt/css/Standard.css">
<!-- mantis：CLM0132，處理人員：CC009，需求單編號：核賠人員受款人ID檢核修改 -->
<!-- mantis：CLM0150，處理人員：DP0706，需求單編號：新核心-車資費用人員階級管控 -->
<!-- mantis：CLM0216，處理人員：DP0714，新核心-新增車險醫詢費用提示檢核 -->
<body onload="initDangerUnit();showPassDay();showContent();showCarFeeQuota();checkChargeAmountMsg();">
    <form name="fm" method="post">
    	<c:if test="${param.EditType=='deal'}">
    		<s:token></s:token>
    	</c:if>
        <!--隐含域，数据提交-->
        <input type="hidden" name="passDay" value="${requestScope.passDay}">
        <input type="hidden" name="DealType">
        <input type="hidden" name="EditType" value="${param.EditType}">
        <input type="hidden" name="HandType" value="${param.HandType}">
        <input type="hidden" name="MessageId" value="${wfLog.businessNo}">
        <input type="hidden" name="BusinessNo" value="${wfLog.businessNo}">
        <input type="hidden" name="BusinessType" value="${wfLog.businessType}">
        <input type="hidden" name="riskCategory" value="${wfLog.riskCategory}">
        <input type="hidden" name="FlowId" value="${wfLog.id.flowId}">
        <input type="hidden" name="swfLogFlowID" value="${wfLog.relateFlowId}">
        <input type="hidden" name="NodeNo" value="${wfLog.nodeNo}">
        <input type="hidden" name="LogNo" value="${wfLog.id.logNo}">
        <input type="hidden" name="ModelNo" value="${wfLog.modelNo}">
        <input type="hidden" name="ContractNo" value="${wfLog.contractNo}">
        <input type="hidden" name="strRiskCode" value="${wfLog.riskCode}">
        <input type="hidden" name="classCode" value="${wfLog.classCode}">
        <input type="hidden" name="OperatorCode" value="${wfLog.operatorCode}">
        <input type="hidden" name="OperatorName" value="${wfLog.operatorName}">
        <input type="hidden" name="SubmitDirection" value="">
        <input type="hidden" name="selectNodeNo" value='${swfPath.endNodeNo}'>
        <input type="hidden" name="selectNodeName" value='${swfPath.endNodeName}'>
        <input type="hidden" name="FlowStatus">
        <input type="hidden" name="Flag" value="1">
        <input type="hidden" name="submitTip">
        <!-- 例外事项，为空支付对象为被保险人，不为空支付对象不是被保险人,暂时对车险有用 start -->
        <input type="hidden" name="exceptions" value='${requestScope.exceptions}'>
        <!-- 增加登陆机构传参myComCode -->
        <input type="hidden" name="myComCode" value="${sessionScope.user.comCode}">
        <!-- 增加保费是否实收提示  -->
        <input type="hidden" name="payFlag" value="${requestScope.payFlag}" />
<!--         //mantis： CLM0092 ，處理人員：BK007 蘇哲，需求單編號：CLM0092.新核心-閉鎖期提醒 -start -->
        <!-- 是否為車險閉鎖期  -->
        <input type="hidden" name="isCloseBetween" value="${requestScope.isCloseBetween}" />
<!--         //mantis： CLM0092 ，處理人員：BK007 蘇哲，需求單編號：CLM0092.新核心-閉鎖期提醒 -end -->
        <!-- 增加核心地址 -->
        <input type="hidden" name="damageDate" value="<fmt:formatDate pattern='yyyy-MM-dd' value='${prpLclaim.damageStartDate}'/>">
        <input type="hidden" name="prpallUrl" value="${prpallUrl}">
        <!-- mantis：CLM0216，處理人員：DP0714，新核心-新增車險醫詢費用提示檢核 -->
        <input type="hidden" name="checkChargeAmountMsg" value="${checkChargeAmountMsg}">

        <table class="common" cellpadding="5" cellspacing="1" align="center">
            <tr class=listtitle>
                <td colspan="4">
                    ${pageScope.editTitle}${pageScope.handTitle}<s:text name="task" /><%--處理/查詢 核賠任務 --%>
                </td>
                <%-- 任務 --%>
            </tr>
            <tr>
                <c:if test="${param.HandType=='22'}">
                    <input type="hidden" name="ClaimNo" value='${prpLclaim.claimNo}'>
                    <input type="hidden" name="RegistNo" value='${prpLclaim.registNo}'>
                    <input type="hidden" name="PolicyNo" value='${prpLclaim.policyNo}'>
                    <input type="button" class="button" name="claimInfo" value="<s:text name='button.ClaimsInformation.value'/>" onclick="showWorkFlowerByClaimNo('${prpLclaim.claimNo}');">
                    <%-- 理赔信息 --%>
                </c:if>
                <input name="butViewTranceInfo" class="longbutton" type="button" value="<s:text name='button.PreviousAudit.value'/>" onclick="viewTranceInfo()">
                <%-- 历次审核意见 --%>
                <c:if test="${historyProposal=='true'}">
                    <input type="button" class="longbutton" value="<s:text name='button.HistoryInformation.value'/>" name="BusinessTotalInfo" onclick="showBusinessTotalInfo('${wfLog.businessNo}');">
                    <%-- 历史承保信息 --%>
                </c:if>
                <c:if test="${historyLoss=='true'}">
                    <input type="button" class="longbutton" value="<s:text name='button.PaymentInformation.value'/>" name="HistoryLossInfo" onclick="showHistoryLossInfo('${wfLog.businessNo}');">
                    <%-- 历史赔付信息 --%>
                </c:if>
                <input type="button" class="button" name="eCertify" value="<s:text name='button.electronicDocuments.value'/>" onClick="openCertify('certifyFinishQueryList','prpLcertifyCertifyNo','${prpLclaim.registNo}','veric','${wfLog.riskCode}');">
                <%-- 电子单证 --%>
                <c:if test="${param.HandType=='22'}">
                    <input type=button class="longbutton" name="policyBackWard" value="<s:text name='regist.prpLregist.registPolicy'/>" onclick="backWardPolicy('','${wfLog.policyNo}','${wfLog.riskCode}',fm.damageDate.value);">
                    <%-- 保单信息 --%>
                </c:if>
                <c:if test="${param.HandType=='11'}">
                    <input name="buttonMessage1" class="longbutton" type="button" value="<s:text name='button.claimsProcessingRecords.value'/>" onclick="openWinQuery();">
                    <%-- 赔案处理记录 --%>
                </c:if>
                <c:if test="${param.HandType=='22'}">
                    <input name="buttonMessage1" class="longbutton" type="button" value="<s:text name='button.claimsProcessingRecords.value'/>" onclick="openWinQuery();">
                    <%-- 赔案处理记录 --%>
                </c:if>
                <input name="ReportPrint" ${requestScope.reportPrintDisabledFlag} class="longbutton" type="button" value="<s:text name='button.checkPolicy.value'/>" onclick="policyPrint();">
                <%-- 查看保单正本 --%>
                <c:if test="${param.HandType=='22'&& wfLog.riskCategory=='D'}">
                    <input type="button" class="longbutton" name="claimInfo" value="<s:text name='button.AccidentInformation.value'/>" <%-- 历次出险信息 --%>
                         onclick="buttonOnClick('perilInfoShow','${prpLclaim.policyNo}','${prpLclaim.registNo}');">
                </c:if>
                <c:if test="${wfLog.riskCategory!='D'}">
                    <input type="button" class="longbutton" name="taskView" value="<s:text name='button.TaskQuery.value'/>" onclick="openWinTask('${wfLog.relateFlowId}');">
                    <%-- 任務查询 --%>
                </c:if>
            </tr>
            <br>
            <tr>
                <td class=title4>
                    <s:text name="check.proDepartment" />：<%-- 处理部门 --%>
                </td>
                <td class=input4>
                    <input readonly class=readonly type="text" name="DeptCode" value="${wfLog.deptName}">
                </td>
                <td class=title4><s:text name="undwrt.SubmissionTime" />：<%-- 提交时间 --%></td>
                <td class=input4>
                    <rc:rcDate name="HandleTime" class="readonly" readonly="true" wdatePicker="false" style="width:220px" value="${wfLog.flowInTime}" />
                </td>
                <input type="hidden" name="DefaultFlag" value="1">
            </tr>
            <%@include file="/pages/undwrt/common/CommonDangerUnits.jsp"%><%-- 保單摘要和賠付摘要訊息  --%>
            <tr class="listtitle">
                <td colspan="9">
                    <s:text name="undwrt.ApprovalInformation" /><%-- 审批信息 --%>
                </td>
            </tr>
            <tr>
                <td class=title4>
                    <s:text name="undwrt.SignedComments" /><%-- 签署审批意见 --%>
                </td>
                
                <td class=input4><%--textarea 不要换行，否则内容里面会有空格 --%>
                    <textarea class=big wrap="soft" name="HandleText" <c:if test="${param.EditType=='query' }">readonly</c:if> ><c:forEach items="${requestScope.notionContent}" var="content">${content.handleText }</c:forEach></textarea>
                </td>
                <td class=title4>
                    <s:text name="undwrt.ApprovalPhrases" /><%-- 审批片语 --%>
                </td>
                <td class=input4>
                    <select class=common name="notion" onchange="changeNotion1(this)" <c:if test="${param.EditType=='query' }">disabled</c:if>>
                        <option value="">-----<s:text name="undwrt.PleaseSelect" />-----</option>
                    <c:forEach items="${requestScope.notionCode}" var="notion_Code">
                        <option value="${notion_Code.codeCName}">${notion_Code.codeCName}</option>
                    </c:forEach>
                    </select>
                </td>
            </tr>
            <%-- 风险资料 --%>
            <%--@include file = "/common/CommonRiskInfo.jsp"--%>
            <%-- 单证信息 --%>
            <%--@include file = "/common/CommonCertifyInfo.jsp"--%>
            <%-- 4.联共保赔款费用分摊信息 --%>
            <c:if test="${coinsFlag=='1'||coinsFlag=='3'||coinsFlag=='2'}">
                <%@include file="/pages/undwrt/common/ForUndwrtCoinsEditFrame.jsp"%>
            </c:if>
            <!-- 巨灾代码 -->
            <%@include file="/pages/undwrt/common/undwrtKelpInfo.jsp"%>
        </table>
        <table class=two>
            <tr>
                <c:choose>
                    <c:when test="${param.EditType!='query'}">
                        <td class="button"><%-- 暂 存 --%>
                            <Input name="butSaveForm" class="button" type="button" value="<s:text name='button.save.value'/>  " onclick="return saveTask(this);">
                        </td>
                        <td class="button"><%-- 审核通过 --%>
                        	<c:if test="${submitPass}">
                        		<!-- mantis：CLM0132，處理人員：CC009，需求單編號：核賠人員受款人ID檢核修改 -->
                            	<Input type="button" class="button" name="passBtn" value="<s:text name='button.checkPass.value'/>" onclick="submitPass(this);" 
                            		<c:if test="${chuShenGangFlag=='1' }">disabled="true"</c:if><c:if test="${contentFlag==false }">disabled="true"</c:if>>
                            </c:if>
                        </td>
                        <td class="button"><%-- 提交上级 --%>
                        	<c:if test="${submitPass!=true}">
                            	<input type="button" class="button" name="submitSuperior" value="<s:text name='button.submitUp.value'/>" onclick="submitTaskBefore('SubmitSuperior',this);">
                            </c:if>
                        </td>
                        <td class="button"><%-- 下发修改 --%>
                            <input type="button" class="button" name="submitJunior" value="<s:text name='button.IssuedModified.value'/>" onclick="submitTaskBefore('SubmitJunior',this);">
                        </td>
                        <td class="button"><%-- 放弃任務 --%>
                            <Input type="button" class="button" name="passBtn" value="<s:text name='button.giveUpTask.value'/>" onclick="undoTask(this);">
                        </td>
                    </c:when>
                    <c:otherwise>
                        <td class=button width=20%><%--返 回  --%>
                            <Input name="butQuery" class="button" type="button" alt="返回" value=" <s:text name='button.return.value'/> " onclick="history.back(-1);">
                        </td>
                    </c:otherwise>
                </c:choose>
            </tr>
        </table>
    </form>
</body>
</html>
